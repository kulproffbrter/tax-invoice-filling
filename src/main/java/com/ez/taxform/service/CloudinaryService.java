package com.ez.taxform.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

	@Autowired
	private Cloudinary cloudinary;

	public String uploadImage(MultipartFile file) {
		try {
			Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			return uploadResult.get("secure_url").toString();
		} catch (Exception e) {
			throw new RuntimeException("Upload error: " + e.getMessage());
		}
	}

	// สำหรับอัปโหลดจาก URL หรือ Base64 String
	public String uploadImageFromString(String fileString) {
		try {
			Map<?, ?> uploadResult = cloudinary.uploader().upload(fileString, // URL หรือ Base64
					ObjectUtils.emptyMap());
			return uploadResult.get("secure_url").toString();
		} catch (Exception e) {
			throw new RuntimeException("Upload error: " + e.getMessage());
		}
	}
}
