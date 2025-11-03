package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BranchDto {
	private UUID branchId;
	private String branchCode; // รับค่า Input จาก FE
	private String branchNameTh; // รับค่า Input จาก FE
	private String branchNameEn; // รับค่า Input จาก FE
	private String buildingNo; // รับค่า Input จาก FE
	private String addressDetailTh; // รับค่า Input จาก FE
	private String addressDetailEn; // รับค่า Input จาก FE
	private String subdistrictId; // รับค่า Input จาก FE
	private String districtId; // รับค่า Input จาก FE
	private String provinceId; // รับค่า Input จาก FE
	private String zipCode; // รับค่า Input จาก FE
	private String countryId;
	private UUID sellerId;
	private String createBy;
	private LocalDateTime createDate;
	private String updateBy;
	private LocalDateTime updateDate;
	private String enableFlag;

	// ---------- Getter / Setter ----------

	public String getBranchNameTh() {
		return branchNameTh;
	}

	public UUID getBranchId() {
		return branchId;
	}

	public void setBranchId(UUID branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
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

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
}
