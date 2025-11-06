package com.ez.taxform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class AuthRequest {
	// @NotBlank(message = "Full name is required")
	private String fullName;

	// @NotBlank(message = "Email is required")
	private String email;

	// @NotBlank(message = "Username is required")
	private String username;

	// @NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "กรุณาระบุชื่อบริษัท")
	private String sellerNameTh;

	private String sellerNameEn;
	private String sellerTypeTax;

	// @NotBlank(message = "Seller tax id is required")
	// @Size(min = 13, max = 13, message = "Seller tax id must be 13 characters")
	private String sellerTaxId;

	// @NotBlank(message = "Branch id is required")
	// @Size(min = 5, max = 5, message = "Branch id must be 5 characters")
	private String branchCode;

	private String sellerPhoneNumber;
	private String logo;

	private String branchNameTh;
	private String branchNameEn;

	@NotBlank(message = "กรุณาระบุเลขที่อาคาร")
	@Size(min = 1, max = 16, message = "เลขที่อาคารต้องมีความยาวไม่เกิน 16 ตัวอักษร")
	private String buildingNo;

	@Size(min = 1, max = 255, message = "รายละเอียดที่อยู่ (ภาษาไทย) ต้องมีความยาวไม่เกิน 255 ตัวอักษร")
	private String addressDetailTh;

	@Size(min = 1, max = 255, message = "รายละเอียดที่อยู่ (ภาษาอังกฤษ) ต้องมีความยาวไม่เกิน 255 ตัวอักษร")
	private String addressDetailEn;

	@NotBlank(message = "กรุณาระบุเขต/ตำบล")
	private String subdistrictId;

	@NotBlank(message = "กรุณาระบุแขวง/อำเภอ")
	private String districtId;

	@NotBlank(message = "กรุณาระบุจังหวัด")
	private String provinceId;

	@NotBlank(message = "กรุณาระบุรหัสไปรษณีย์")
	private String zipCode;

	private String countryId;

	private String createBy;
	private LocalDateTime createDate;

	private String updateBy;
	private LocalDateTime updateDate;

	// Getter / Setter
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSellerNameTh() {
		return sellerNameTh;
	}

	public void setSellerNameTh(String sellerNameTh) {
		this.sellerNameTh = sellerNameTh;
	}

	public String getSellerNameEn() {
		return sellerNameEn;
	}

	public void setSellerNameEn(String sellerNameEn) {
		this.sellerNameEn = sellerNameEn;
	}

	public String getSellerTypeTax() {
		return sellerTypeTax;
	}

	public void setSellerTypeTax(String sellerTypeTax) {
		this.sellerTypeTax = sellerTypeTax;
	}

	public String getSellerTaxId() {
		return sellerTaxId;
	}

	public void setSellerTaxId(String sellerTaxId) {
		this.sellerTaxId = sellerTaxId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getSellerPhoneNumber() {
		return sellerPhoneNumber;
	}

	public void setSellerPhoneNumber(String sellerPhoneNumber) {
		this.sellerPhoneNumber = sellerPhoneNumber;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBranchNameTh() {
		return branchNameTh;
	}

	public void setBranchNameTh(String branchNameTh) {
		this.branchNameTh = branchNameTh;
	}

	public String getBranchNameEn() {
		return branchNameEn;
	}

	public void setBranchNameEn(String branchNameEn) {
		this.branchNameEn = branchNameEn;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getAddressDetailTh() {
		return addressDetailTh;
	}

	public void setAddressDetailTh(String addressDetailTh) {
		this.addressDetailTh = addressDetailTh;
	}

	public String getAddressDetailEn() {
		return addressDetailEn;
	}

	public void setAddressDetailEn(String addressDetailEn) {
		this.addressDetailEn = addressDetailEn;
	}

	public String getSubdistrictId() {
		return subdistrictId;
	}

	public void setSubdistrictId(String subdistrictId) {
		this.subdistrictId = subdistrictId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
}
