package com.ez.taxform.service;

import com.ez.taxform.dto.*;
import com.ez.taxform.repository.*;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class RbacManageService {

	private final RoleDao roleDao;
	private final MenuDao menuDao;
	private final PermissionDao permissionDao;
	private final UserRolePermissionDao userRolePermissionDao;
	private final UserDao userDao;
	private final SellerDao sellerDao;

	public RbacManageService(RoleDao roleDao, MenuDao menuDao, PermissionDao permissionDao,
			UserRolePermissionDao userRolePermissionDao, UserDao userDao, SellerDao sellerDao) {
		this.roleDao = roleDao;
		this.menuDao = menuDao;
		this.permissionDao = permissionDao;
		this.userRolePermissionDao = userRolePermissionDao;
		this.userDao = userDao;
		this.sellerDao = sellerDao;
	}

	// ─────────────────────────────────────────────
	// ROLE add/edit
	// ─────────────────────────────────────────────
	public UUID saveOrUpdateRole(RoleRequest req, String operator) {
		Map<String, String> errors = new LinkedHashMap<>();

		// Validate roleCode
		if (req.getRoleCode() == null || req.getRoleCode().isBlank() || req.getRoleCode().length() > 35
				|| Pattern.matches(".*[ก-๙].*", req.getRoleCode())) {
			errors.put("roleCode", "รหัสบทบาทห้ามเป็นภาษาไทย และต้องมีความยาวไม่เกิน 35 ตัวอักษร");
		}

		// Validate roleName
		if (req.getRoleName() == null || req.getRoleName().isBlank() || req.getRoleName().length() > 50
				|| Pattern.matches(".*[ก-๙].*", req.getRoleName())) {
			errors.put("roleName", "ชื่อบทบาทห้ามเป็นภาษาไทย และต้องไม่เกิน 50 ตัวอักษร");
		}

		// Role Level
		if (req.getRoleLevel() == null
				|| !(req.getRoleLevel().equals("HQ_ADMIN") || req.getRoleLevel().equals("BRANCH_ADMIN")
						|| req.getRoleLevel().equals("STAFF") || req.getRoleLevel().equals("SYSTEM_ADMIN"))) {
			errors.put("roleLevel", "roleLevel ต้องเป็น HQ_ADMIN, BRANCH_ADMIN, STAFF, SYSTEM_ADMIN");
		}

		if (!errors.isEmpty()) {
			throw new ServiceValidationException(errors);
		}

		// ✅ INSERT Role
		if (req.getRoleId() == null) {

			// ✅ เช็ค roleCode ซ้ำก่อน insert
			if (roleDao.existsByRoleCode(req.getRoleCode())) {
				errors.put("roleCode", "roleCode นี้มีอยู่แล้วในระบบ");
				throw new ServiceValidationException(errors);
			}

			// สร้างใหม่
			req.setRoleId(UUID.randomUUID());
			req.setCreateBy(operator);
			req.setUpdateBy(operator);
			roleDao.saveOrUpdate(req);

			return req.getRoleId();
		}

		// ✅ UPDATE Role → ไม่เช็ค roleCode ซ้ำของตัวเอง
		req.setUpdateBy(operator);
		roleDao.saveOrUpdate(req);
		return req.getRoleId();
	}
	
	// ✅ Delete Role
    public void deleteRole(UUID roleId) {
        if (roleId == null)
            throw new IllegalArgumentException("roleId is required");

        int deleted = roleDao.deleteRole(roleId);
        if (deleted == 0) {
            throw new RuntimeException("Role not found or already deleted");
        }
    }

	// ─────────────────────────────────────────────
	// MENU add/edit
	// ─────────────────────────────────────────────
	public UUID saveOrUpdateMenu(MenuRequest req) {
		Map<String, String> errors = new LinkedHashMap<>();

		if (req.getMenuCode() == null || req.getMenuCode().isBlank()
				|| !Pattern.matches("^[A-Za-z0-9_\\-]{1,5}$", req.getMenuCode())) {
			errors.put("menuCode", "menuCode ต้องเป็น A-Za-z0-9 _- และไม่เกิน 5 ตัวอักษร");
		}

		if (!errors.isEmpty())
			throw new ServiceValidationException(errors);

		// ✅ INSERT MODE
		if (req.getMenuId() == null) {

			// เช็คซ้ำก่อน insert
			if (menuDao.existsByMenuCode(req.getMenuCode())) {
				errors.put("menuCode", "menuCode นี้มีอยู่แล้ว");
				throw new ServiceValidationException(errors);
			}

			req.setMenuId(UUID.randomUUID());
			menuDao.saveOrUpdate(req);
			return req.getMenuId();
		}

		// ✅ UPDATE MODE → ห้ามเช็คซ้ำ code ของตัวเอง
		menuDao.saveOrUpdate(req);
		return req.getMenuId();
	}

	// ─────────────────────────────────────────────
	// PERMISSION add/edit (✅ เวอร์ชันแก้ใหม่)
	// ─────────────────────────────────────────────
	public UUID saveOrUpdatePermission(PermissionRequest req) {
		Map<String, String> errors = new LinkedHashMap<>();

		if (req.getPermissionCode() == null || req.getPermissionCode().isBlank()
				|| !Pattern.matches("^[A-Za-z0-9_\\-]{1,10}$", req.getPermissionCode())) {
			errors.put("permissionCode", "permissionCode ต้องเป็น A-Za-z0-9 _- และไม่เกิน 10 ตัวอักษร");
		}

		if (!errors.isEmpty())
			throw new ServiceValidationException(errors);

		// ✅ INSERT MODE
		if (req.getPermissionId() == null) {

			if (permissionDao.existsByPermissionCode(req.getPermissionCode())) {
				errors.put("permissionCode", "permissionCode นี้มีอยู่แล้ว");
				throw new ServiceValidationException(errors);
			}

			req.setPermissionId(UUID.randomUUID());
			permissionDao.saveOrUpdate(req);
			return req.getPermissionId();
		}

		// ✅ UPDATE MODE → ไม่ต้องเช็ค code ของตัวเอง
		permissionDao.saveOrUpdate(req);
		return req.getPermissionId();
	}

	// ─────────────────────────────────────────────
	// ROLE PERMISSION add/edit
	// ─────────────────────────────────────────────
	public void saveOrUpdateRolePermission(UserRolePermissionRequest req, String operator) {
		Map<String, String> errors = new LinkedHashMap<>();

		if (req.getRoleId() == null)
			errors.put("roleId", "roleId required");
		if (req.getPermissionId() == null)
			errors.put("permissionId", "permissionId required");
		if (req.getSellerId() == null)
			errors.put("sellerId", "sellerId required");

		if (!errors.isEmpty())
			throw new ServiceValidationException(errors);

		// ✅ INSERT MODE
		if (req.getRolePermissionId() == null) {

			// ✅ เช็คซ้ำก่อน insert
			if (userRolePermissionDao.existsRolePermission(req.getRoleId(), req.getPermissionId(), req.getSellerId())) {
				errors.put("duplicate", "Permission นี้ถูก assign ให้ Role นี้แล้ว");
				throw new ServiceValidationException(errors);
			}

			req.setRolePermissionId(UUID.randomUUID());
			req.setCreateBy(operator);
			req.setUpdateBy(operator);
			userRolePermissionDao.saveOrUpdateRolePermission(req);
			return;
		}

		// ✅ UPDATE MODE
		req.setUpdateBy(operator);
		userRolePermissionDao.saveOrUpdateRolePermission(req);
	}

	// ─────────────────────────────────────────────
	// USER ROLE add/edit
	// ─────────────────────────────────────────────
	public void saveOrUpdateUserRole(UserRolePermissionRequest req, String operator) {
		Map<String, String> errors = new LinkedHashMap<>();

		if (req.getUserId() == null)
			errors.put("userId", "userId required");
		if (req.getRoleId() == null)
			errors.put("roleId", "roleId required");
		if (req.getSellerId() == null)
			errors.put("sellerId", "sellerId required");

		if (!errors.isEmpty())
			throw new ServiceValidationException(errors);

		// ✅ ตรวจ roleId ว่ามีใน DB
		if (!roleDao.existsById(req.getRoleId())) {
			errors.put("roleId", "Role นี้ไม่มีอยู่ในระบบ");
			throw new ServiceValidationException(errors);
		}

		// ✅ ตรวจ userId ว่ามีใน DB
		if (!userDao.existsById(req.getUserId())) {
			errors.put("userId", "ผู้ใช้งานนี้ไม่มีอยู่ในระบบ");
			throw new ServiceValidationException(errors);
		}

		// ✅ ตรวจ sellerId ว่ามีใน DB
		if (!sellerDao.existsById(req.getSellerId())) {
			errors.put("sellerId", "Seller นี้ไม่มีอยู่ในระบบ");
			throw new ServiceValidationException(errors);
		}

		// ✅ INSERT MODE
		if (!userRolePermissionDao.existsUserRole(req.getUserId(), req.getRoleId(), req.getSellerId())) {
			req.setCreateBy(operator);
			req.setUpdateBy(operator);
			userRolePermissionDao.saveOrUpdateUserRole(req);
			return;
		}

		// ✅ UPDATE MODE
		req.setUpdateBy(operator);
		userRolePermissionDao.saveOrUpdateUserRole(req);
	}
}