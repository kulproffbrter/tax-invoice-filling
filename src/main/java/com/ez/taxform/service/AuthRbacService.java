package com.ez.taxform.service;

import com.ez.taxform.dto.*;
import com.ez.taxform.repository.*;
import com.ez.taxform.service.CloudinaryService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthRbacService {
	private final UserDao userDao;
	private final SellerDao sellerDao;
	private final BranchDao branchDao;

	@Autowired
	private CloudinaryService cloudinaryService; // ✅ Inject CloudinaryService

	public AuthRbacService(UserDao userDao, SellerDao sellerDao, BranchDao branchDao) {
		this.userDao = userDao;
		this.sellerDao = sellerDao;
		this.branchDao = branchDao;
	}

	@Transactional
    public String register(AuthRequest request) {
    	
    	validateInput(request);
    	
    	UUID sellerId = UUID.randomUUID();
    	UUID branchId = UUID.randomUUID();
    	UUID userId = UUID.randomUUID();
    	String username = "";
    	
    	// ตัดค่า email ก่อน @ มาใส่ username  	
    	String email = request.getEmail();
    	if (email != null && email.contains("@")) {
    		username = (email.substring(0, email.indexOf("@")));
    	} else {
    	    // กรณี email ไม่ถูกต้อง fallback ไปใช้ userName ที่ส่งมา
    		username = (request.getUsername());
    	}

    	// 1. Insert Seller
    	SellerDto seller = new SellerDto();
    	seller.setSellerId(sellerId);
    	seller.setSellerNameTh(request.getSellerNameTh());
    	seller.setSellerNameEn(request.getSellerNameEn());
    	seller.setSellerTypeTax(request.getSellerTypeTax());
    	seller.setSellerTaxId(request.getSellerTaxId());
    	seller.setBranchId(request.getBranchCode());
    	seller.setSellerPhoneNumber(request.getSellerPhoneNumber());
    	seller.setLogo(request.getLogo());
    	seller.setCreateBy(username);
    	seller.setCreateDate(LocalDateTime.now());
    	seller.setUpdateBy(username);
    	seller.setUpdateDate(LocalDateTime.now());
    	sellerDao.saveOrUpdate(seller);

    	// 2. Insert Branch
    	BranchDto branch = new BranchDto();
    	branch.setBranchId(branchId);
    	branch.setBranchCode(request.getBranchCode());
    	branch.setBranchNameTh(request.getBranchNameTh());
    	branch.setBranchNameEn(request.getBranchNameEn());
    	branch.setBuildingNo(request.getBuildingNo());
    	branch.setAddressDetailTh(request.getAddressDetailTh());
    	branch.setAddressDetailEn(request.getAddressDetailEn());
    	branch.setSubdistrictId(request.getSubdistrictId());
    	branch.setDistrictId(request.getDistrictId());
    	branch.setProvinceId(request.getProvinceId());
    	branch.setZipCode(request.getZipCode());
    	branch.setCountryId(request.getCountryId());
    	branch.setSellerId(sellerId);
    	branch.setCreateBy(username);
    	branch.setCreateDate(LocalDateTime.now());
    	branch.setUpdateBy(username);
    	branch.setUpdateDate(LocalDateTime.now());
    	branch.setEnableFlag("E");
    	branchDao.saveOrUpdate(branch);

    	// 3. Insert User
    	UserDto user = new UserDto();
    	user.setUserId(userId);
    	user.setFullName(request.getFullName());
    	user.setEmail(request.getEmail());
    	user.setUsername(username);
    	
    	// 🔒 เข้ารหัสรหัสผ่านก่อนบันทึก
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        
    	user.setBranchId(branchId);
    	user.setSellerId(sellerId);
    	user.setEnableFlag("E");
    	user.setCreateBy(username);
    	user.setCreateDate(LocalDateTime.now());
    	user.setUpdateBy(username);
    	user.setUpdateDate(LocalDateTime.now());
    	userDao.saveOrUpdate(user);
    	
    	return username;
    }
	
	// upload logo แยกเส้น API
	public String uploadSellerLogo(UUID sellerId, MultipartFile file) {
	    String logoUrl = cloudinaryService.uploadImage(file);
	    sellerDao.updateLogo(sellerId, logoUrl);
	    return logoUrl;
	}
	
	public LoginResponse login(String username, String password) {
		Map<String, String> errors = new LinkedHashMap<>();

		if (username == null || username.isBlank()) {
			errors.put("username", "กรุณากรอกชื่อผู้ใช้");
		}
		if (password == null || password.isBlank()) {
			errors.put("UserPassword", "กรุณากรอกรหัสผ่าน");
		}

		if (!errors.isEmpty()) {
			throw new ServiceValidationException(errors);
		}

		UserDto user = userDao.findByUsername(username);

		if (user == null) {
			errors.put("username", "ไม่พบบัญชีผู้ใช้นี้ในระบบ");
			throw new ServiceValidationException(errors);
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password, user.getPassword())) {
			errors.put("UserPassword", "รหัสผ่านไม่ถูกต้อง");
			throw new ServiceValidationException(errors);
		}

		if (!"E".equals(user.getEnableFlag())) {
			errors.put("username", "บัญชีนี้ถูกระงับการใช้งาน");
			throw new ServiceValidationException(errors);
		}

		LoginResponse response = new LoginResponse();
		response.setUserId(user.getUserId());
		response.setUsername(user.getUsername());
		response.setFullName(user.getFullName());
		response.setEmail(user.getEmail());
		response.setSellerId(user.getSellerId());
		response.setBranchId(user.getBranchId());
		response.setMessage("เข้าสู่ระบบสำเร็จ");

		return response;
	}

	private void validateInput(AuthRequest request) { // Logic Validate มีเขียนเพิ่มเติมใน AuthRequest [DTO]
		Map<String, String> errors = new LinkedHashMap<>();

		// Validate Full Name
		String fullName = request.getFullName();
		if (fullName == null || !fullName.trim().matches("^[ก-๙A-Za-z\\s]{1,100}$")) {
			errors.put("fullName", "ชื่อ-นามสกุลต้องเป็นภาษาไทยหรืออังกฤษ และมีความยาวไม่เกิน 100 ตัวอักษร");
		}

		// Validate Email
		String email = request.getEmail();
		if (email == null || !email.trim().matches("^[A-Za-z0-9!@._-]{1,50}$")) {
			errors.put("email", "อีเมลต้องประกอบด้วยตัวอักษรอังกฤษ ตัวเลข หรืออักขระพิเศษ (!@._-) ไม่เกิน 50 ตัวอักษร");
		} else {
			email = email.trim();
			String emailFormatRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			if (!Pattern.matches(emailFormatRegex, email)) {
				errors.put("email", "รูปแบบอีเมลไม่ถูกต้องกรุณาแก้ไขอีกรอบ ตัวอย่างเช่น name@example.com");
			}
		}

		// Validate Password
		String password = request.getPassword();
		if (!StringUtils.hasText(password)) {
			errors.put("Password", "กรุณาระบุรหัสผ่าน");
		} else {
			password = password.trim();
			String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@._\\-#$*&])[A-Za-z\\d!@._\\-#$*&]{8}$";
			if (!Pattern.matches(passwordRegex, password)) {
				errors.put("Password",
						"รหัสผ่านต้องมีตัวอักษรพิมพ์ใหญ่, ตัวอักษรพิมพ์เล็ก, ตัวเลข, อักขระพิเศษ (!@._-#$*&) และยาว 8 ตัวอักษร");
			}
		}

		// Validate Seller Tax ID
		String sellerTaxId = request.getSellerTaxId();
		if (sellerTaxId == null || !sellerTaxId.trim().matches("^[0-9]{13}$")) {
			errors.put("sellerTaxId", "เลขประจำตัวผู้เสียภาษีต้องเป็นตัวเลข (0–9) จำนวน 13 หลักเท่านั้น");
		}

		if (sellerTaxId != null && sellerTaxId.trim().matches("^[0-9]{13}$")) {
			boolean exists = sellerDao.existsBySellerTaxId(sellerTaxId);
			if (exists) {
				errors.put("sellerTaxId", "เลขประจำตัวผู้เสียภาษีนี้มีอยู่แล้วในระบบ ไม่สามารถลงทะเบียนซ้ำได้");
			}
		}

		// Validate Branch Code
		String branchCode = request.getBranchCode();
		if (branchCode == null || !branchCode.trim().matches("^[0-9]{5}$")) {
			errors.put("branchCode", "รหัสสาขาต้องเป็นตัวเลข (0–9) จำนวน 5 หลักเท่านั้น");
		}

		// เพิ่ม validation ฟิลด์อื่น ๆ ได้ที่นี่...

		// -----

		if (!errors.isEmpty()) {
			throw new ServiceValidationException(errors);
		}

	}
}
