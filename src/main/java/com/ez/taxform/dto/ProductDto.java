package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;

public class ProductDto {
    private UUID productId;

    @NotBlank(message = "กรุณาระบุรหัสสินค้า")
    @Size(max = 35, message = "รหัสสินค้าต้องมีความยาวไม่เกิน 35 ตัวอักษร")
    private String productCode;

    @NotBlank(message = "กรุณาระบุชื่อสินค้า")
    @Size(max = 35, message = "ชื่อสินค้าต้องมีความยาวไม่เกิน 35 ตัวอักษร")
    private String productName;

    @Size(max = 255, message = "คำอธิบายสินค้าต้องมีความยาวไม่เกิน 255 ตัวอักษร")
    private String productDescription;

    @NotBlank(message = "กรุณาระบุประเภทการคำนวณภาษี")
    private String taxCalFlag;

    @NotNull(message = "กรุณาระบุจำนวนเงินสินค้า")
    @DecimalMin(value = "0.0", inclusive = false, message = "จำนวนเงินต้องมากกว่า 0")
    private Double productChargeamt;

    private String productTaxTypeCode; // เช่น VAT
    private Integer productTaxCalrate; // เช่น 7

    @Size(max = 50, message = "หน่วยสินค้าต้องมีความยาวไม่เกิน 50 ตัวอักษร")
    private String productUnit;

    private String enableFlag;

    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;

    @NotNull(message = "sellerId is required")
    private UUID sellerId;

    // ---------- Getter / Setter ----------
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public String getTaxCalFlag() { return taxCalFlag; }
    public void setTaxCalFlag(String taxCalFlag) { this.taxCalFlag = taxCalFlag; }

    public Double getProductChargeamt() { return productChargeamt; }
    public void setProductChargeamt(Double productChargeamt) { this.productChargeamt = productChargeamt; }

    public String getProductTaxTypeCode() { return productTaxTypeCode; }
    public void setProductTaxTypeCode(String productTaxTypeCode) { this.productTaxTypeCode = productTaxTypeCode; }

    public Integer getProductTaxCalrate() { return productTaxCalrate; }
    public void setProductTaxCalrate(Integer productTaxCalrate) { this.productTaxCalrate = productTaxCalrate; }

    public String getProductUnit() { return productUnit; }
    public void setProductUnit(String productUnit) { this.productUnit = productUnit; }

    public String getEnableFlag() { return enableFlag; }
    public void setEnableFlag(String enableFlag) { this.enableFlag = enableFlag; }

    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }

    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }

    public UUID getSellerId() { return sellerId; }
    public void setSellerId(UUID sellerId) { this.sellerId = sellerId; }
}
