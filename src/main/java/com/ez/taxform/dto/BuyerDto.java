package com.ez.taxform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public class BuyerDto {
    private UUID buyerId;

    @NotBlank(message = "กรุณาระบุรหัสลูกค้า")
    @Size(max = 35, message = "รหัสลูกค้าต้องมีความยาวไม่เกิน 35 ตัวอักษร")
    private String buyerCode;

    @NotBlank(message = "กรุณาระบุชื่อลูกค้า (ภาษาไทย)")
    @Size(max = 100, message = "ชื่อลูกค้า (ภาษาไทย) ต้องมีความยาวไม่เกิน 100 ตัวอักษร")
    private String buyerNameTh;

    @Size(max = 100, message = "ชื่อลูกค้า (ภาษาอังกฤษ) ต้องมีความยาวไม่เกิน 100 ตัวอักษร")
    private String buyerNameEn;

    private String buyerTypeTax = "TXID";

    @NotBlank(message = "กรุณาระบุเลขประจำตัวผู้เสียภาษี")
    @Size(min = 13, max = 13, message = "เลขประจำตัวผู้เสียภาษีต้องมี 13 ตัวอักษรเท่านั้น")
    private String buyerTaxId;

    @Size(min = 5, max = 5, message = "รหัสสาขาต้องมี 5 ตัวอักษรเท่านั้น")
    private String buyerBranchCode;

    @NotBlank(message = "กรุณาระบุชื่อสาขา (ภาษาไทย)")
    @Size(max = 35, message = "ชื่อสาขา (ภาษาไทย) ต้องมีความยาวไม่เกิน 35 ตัวอักษร")
    private String buyerBranchNameTh;

    @Size(max = 35, message = "ชื่อสาขา (ภาษาอังกฤษ) ต้องมีความยาวไม่เกิน 35 ตัวอักษร")
    private String buyerBranchNameEn;

    @NotBlank(message = "กรุณาระบุที่อยู่ลูกค้า (ภาษาไทย)")
    @Size(max = 255, message = "ที่อยู่ลูกค้า (ภาษาไทย) ต้องมีความยาวไม่เกิน 255 ตัวอักษร")
    private String buyerAddressTh;

    @Size(max = 255, message = "ที่อยู่ลูกค้า (ภาษาอังกฤษ) ต้องมีความยาวไม่เกิน 255 ตัวอักษร")
    private String buyerAddressEn;

    @NotBlank(message = "กรุณาระบุรหัสไปรษณีย์")
    @Size(min = 5, max = 5, message = "รหัสไปรษณีย์ต้องมี 5 ตัวอักษรเท่านั้น")
    private String buyerZipCode;

    private String buyerCountryId = "TH";

    private String buyerEmail;
    
    @Size(min = 0, max = 10, message = "เบอร์โทรต้องมีความยาวไม่เกิน 10 ตัวอักษร")
    private String buyerPhoneNumber;

    private String enableFlag = "E";
    
    private UUID sellerId;
    
    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;
	public UUID getBuyerId() {
		return buyerId;
	}
	
	
	// Getter / Setter
	
	
	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerCode() {
		return buyerCode;
	}
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}
	public String getBuyerNameTh() {
		return buyerNameTh;
	}
	public void setBuyerNameTh(String buyerNameTh) {
		this.buyerNameTh = buyerNameTh;
	}
	public String getBuyerNameEn() {
		return buyerNameEn;
	}
	public void setBuyerNameEn(String buyerNameEn) {
		this.buyerNameEn = buyerNameEn;
	}
	public String getBuyerTypeTax() {
		return buyerTypeTax;
	}
	public void setBuyerTypeTax(String buyerTypeTax) {
		this.buyerTypeTax = buyerTypeTax;
	}
	public String getBuyerTaxId() {
		return buyerTaxId;
	}
	public void setBuyerTaxId(String buyerTaxId) {
		this.buyerTaxId = buyerTaxId;
	}
	public String getBuyerBranchCode() {
		return buyerBranchCode;
	}
	public void setBuyerBranchCode(String buyerBranchCode) {
		this.buyerBranchCode = buyerBranchCode;
	}
	public String getBuyerBranchNameTh() {
		return buyerBranchNameTh;
	}
	public void setBuyerBranchNameTh(String buyerBranchNameTh) {
		this.buyerBranchNameTh = buyerBranchNameTh;
	}
	public String getBuyerBranchNameEn() {
		return buyerBranchNameEn;
	}
	public void setBuyerBranchNameEn(String buyerBranchNameEn) {
		this.buyerBranchNameEn = buyerBranchNameEn;
	}
	public String getBuyerAddressTh() {
		return buyerAddressTh;
	}
	public void setBuyerAddressTh(String buyerAddressTh) {
		this.buyerAddressTh = buyerAddressTh;
	}
	public String getBuyerAddressEn() {
		return buyerAddressEn;
	}
	public void setBuyerAddressEn(String buyerAddressEn) {
		this.buyerAddressEn = buyerAddressEn;
	}
	public String getBuyerZipCode() {
		return buyerZipCode;
	}
	public void setBuyerZipCode(String buyerZipCode) {
		this.buyerZipCode = buyerZipCode;
	}
	public String getBuyerCountryId() {
		return buyerCountryId;
	}
	public void setBuyerCountryId(String buyerCountryId) {
		this.buyerCountryId = buyerCountryId;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}
	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
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

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}

}
