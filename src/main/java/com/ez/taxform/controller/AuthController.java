package com.ez.taxform.controller;

import jakarta.validation.Valid;
import com.ez.taxform.dto.AuthRequest;
import com.ez.taxform.dto.LoginRequest;
import com.ez.taxform.dto.LoginResponse;
import com.ez.taxform.service.AuthRbacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;



@RestController
@RequestMapping("/api/eztax")
public class AuthController {
	private final AuthRbacService authService;

	public AuthController(AuthRbacService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody @Valid AuthRequest request) {
		String username = authService.register(request);

		Map<String, String> response = new LinkedHashMap<>();
		response.put("message", "ลงทะเบียนสำเร็จ");
		response.put("username", "ชื่อผู้ใช้ของคุณคือ '" + username + "' กรุณาใช้ข้อมูลนี้เพื่อเข้าสู่ระบบ");

		return ResponseEntity.ok(response);
	}

	/*
	 * @PostMapping("/register") public
	 * ResponseEntity<String>register(@RequestBody @Valid AuthRequest request)
	 * {authService.register(request); return
	 * ResponseEntity.ok("Register successful"); }
	 * 
	 * 
	 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody
	 * LoginRequest request) { LoginResponse response =
	 * authService.login(request.getUsername(), request.getUserPassword()); return
	 * ResponseEntity.ok(response); }
	 * 
	 */

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
		authService.login(request.getUsername(), request.getPassword());
		return ResponseEntity.ok("เข้าสู่ระบบสำเร็จ");
	}

	// ✅ เพิ่ม API Upload Logo
	@PostMapping("/register/{sellerId}/upload-logo")
	public ResponseEntity<Map<String, String>> uploadLogo(@PathVariable UUID sellerId,
			@RequestParam("file") MultipartFile file) {

		String logoUrl = authService.uploadSellerLogo(sellerId, file);

		Map<String, String> response = new LinkedHashMap<>();
		response.put("message", "อัปโหลดโลโก้สำเร็จ");
		response.put("logoUrl", logoUrl);

		return ResponseEntity.ok(response);
	}
}
