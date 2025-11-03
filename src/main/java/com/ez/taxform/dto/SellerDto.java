package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SellerDto {
	private UUID sellerId;
    private String sellerNameTh;       // รับค่า Input จาก FE
    private String sellerNameEn;       // รับค่า Input จาก FE
    private String sellerTypeTax;      
    private String sellerTaxId;        // รับค่า Input จาก FE
    private String branchId;
    private String sellerPhoneNumber;  // รับค่า Input จาก FE
    private String logo;               // รับค่า Input จาก FE
    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;

    // ---------- Getter / Setter ----------
    public UUID getSellerId() { return sellerId; }
    public void setSellerId(UUID sellerId) { this.sellerId = sellerId; }
    public String getSellerNameTh() { return sellerNameTh; }
    public void setSellerNameTh(String sellerNameTh) { this.sellerNameTh = sellerNameTh; }
    public String getSellerNameEn() { return sellerNameEn; }
    public void setSellerNameEn(String sellerNameEn) { this.sellerNameEn = sellerNameEn; }
    public String getSellerTypeTax() { return sellerTypeTax; }
    public void setSellerTypeTax(String sellerTypeTax) { this.sellerTypeTax = sellerTypeTax; }
    public String getSellerTaxId() { return sellerTaxId; }
    public void setSellerTaxId(String sellerTaxId) { this.sellerTaxId = sellerTaxId; }
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }
    public String getSellerPhoneNumber() { return sellerPhoneNumber; }
    public void setSellerPhoneNumber(String sellerPhoneNumber) { this.sellerPhoneNumber = sellerPhoneNumber; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }
}
