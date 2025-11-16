package com.ez.taxform.service;

import com.ez.taxform.dto.BranchDto;
import com.ez.taxform.repository.BranchDao;

import com.ez.taxform.dto.BuyerDto;
import com.ez.taxform.repository.BuyerDao;

import com.ez.taxform.dto.ProductDto;
import com.ez.taxform.repository.ProductDao;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MasterDataService {

    private final BranchDao branchDao;
    private final BuyerDao buyerDao;
    private final ProductDao productDao;
    
    public MasterDataService(BranchDao branchDao, BuyerDao buyerDao, ProductDao productDao) {
        this.branchDao = branchDao;
        this.buyerDao = buyerDao;
        this.productDao = productDao;
    }

    // ─────────────────────────────
    // ✅ Add Branch
    // ─────────────────────────────
    public UUID addBranch(BranchDto branch, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();

        // Validate Branch Code
        String branchCode = branch.getBranchCode();
        if (branchCode == null || !branchCode.trim().matches("^[0-9]{5}$")) {
            errors.put("branchCode", "รหัสสาขาต้องเป็นตัวเลข (0–9) จำนวน 5 หลักเท่านั้น");
        }

        if (branch.getSellerId() == null)
            errors.put("sellerId", "sellerId is required");

        // ตรวจสอบ branchCode ซ้ำ
        if (branchDao.existsByBranchCode(branchCode)) {
            errors.put("branchCode", "รหัสสาขานี้มีอยู่แล้วในระบบ");
        }

        if (!errors.isEmpty())
            throw new ServiceValidationException(errors);

        // Set create fields
        branch.setBranchId(UUID.randomUUID());
        branch.setCreateBy(operator);
        branch.setCreateDate(LocalDateTime.now());

        branchDao.saveOrUpdate(branch);
        return branch.getBranchId();
    }

    // ─────────────────────────────
    // ✅ Edit Branch
    // ─────────────────────────────
    public UUID editBranch(BranchDto branch, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();

        // Validate input
        if (branch.getBranchCode() == null || !branch.getBranchCode().trim().matches("^[0-9]{5}$")) {
            errors.put("branchCode", "รหัสสาขาต้องเป็นตัวเลข (0–9) จำนวน 5 หลักเท่านั้น");
        }
        if (branch.getSellerId() == null) {
            errors.put("sellerId", "sellerId is required");
        }
        if (!errors.isEmpty()) {
            throw new ServiceValidationException(errors);
        }

        // เช็ค branchCode + sellerId ใน DB
        BranchDto existingBranch = branchDao.findByBranchCodeAndSeller(branch.getBranchCode(), branch.getSellerId());
        if (existingBranch == null) {
            errors.put("branchCode", "ไม่พบสาขา " + branch.getBranchCode() + " กรุณาตรวจสอบรหัสสาขาอีกครั้ง");
            throw new ServiceValidationException(errors);
        }

        // Set branchId ของสาขาที่เจอเพื่ออัปเดต
        branch.setBranchId(existingBranch.getBranchId());
        branch.setUpdateBy(operator);
        branch.setUpdateDate(LocalDateTime.now());

        branchDao.saveOrUpdate(branch);
        return branch.getBranchId();
    }

    public BranchDto getBranchById(UUID branchId) {
        BranchDto branch = branchDao.findById(branchId);
        if (branch == null) {
            throw new RuntimeException("Branch not found");
        }
        return branch;
    }

    // ─────────────────────────────
    // ✅ Delete Branch
    // ─────────────────────────────
    public void deleteBranch(UUID branchId) {
        Map<String, String> errors = new LinkedHashMap<>();

        BranchDto branch = branchDao.findById(branchId);
        if (branch == null) {
            errors.put("branchId", "Branch not found");
            throw new ServiceValidationException(errors);
        }

        int deleted = branchDao.deleteBranch(branchId);
        if (deleted == 0) {
            errors.put("branchId", "Branch could not be deleted");
            throw new ServiceValidationException(errors);
        }
    }

    // ─────────────────────────────
    // ✅ Add Buyer
    // ─────────────────────────────
    public UUID addBuyer(BuyerDto buyer, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();
        
        // ตรวจสอบ sellerId
        if (buyer.getSellerId() == null) {
            errors.put("sellerId", "sellerId is required");
        }
        
        
        // ตรวจสอบข้อมูลซ้ำ
        if (buyerDao.findByBuyerCode(buyer.getBuyerCode()) != null) {
            errors.put("buyerCode", "รหัสลูกค้านี้มีอยู่แล้ว");
        }
        
        // ตรวจสอบ email format (ถ้ามีค่า)
        if (buyer.getBuyerEmail() != null && !buyer.getBuyerEmail().isBlank()) {
            String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
            if (!buyer.getBuyerEmail().matches(emailRegex)) {
                errors.put("buyerEmail", "รูปแบบ email ไม่ถูกต้อง");
            }
        }

        if (!errors.isEmpty()) {
            throw new ServiceValidationException(errors);
        }

        // Set create info
        buyer.setBuyerId(UUID.randomUUID());
        buyer.setCreateBy(operator);
        buyer.setCreateDate(LocalDateTime.now());

        buyerDao.saveOrUpdate(buyer);
        return buyer.getBuyerId();
    }

    // ─────────────────────────────
    // ✅ Edit Buyer
    // ─────────────────────────────
    public UUID editBuyer(BuyerDto buyer, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();
        
        // ตรวจสอบ sellerId
        if (buyer.getSellerId() == null) {
            errors.put("sellerId", "sellerId is required");
        }
        
        BuyerDto existingBuyer = buyerDao.findByBuyerCode(buyer.getBuyerCode());
        if (existingBuyer == null) {
            errors.put("buyerCode", "ไม่พบข้อมูลลูกค้าในระบบ");
            throw new ServiceValidationException(errors);
        }
        
        // ❗ Validate Email Format (เฉพาะกรณีใส่ email ใหม่หรือแก้ไข)
        if (buyer.getBuyerEmail() != null && !buyer.getBuyerEmail().isBlank()) {
            String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
            if (!buyer.getBuyerEmail().matches(emailRegex)) {
                errors.put("buyerEmail", "รูปแบบ email ไม่ถูกต้อง");
            }
        }

        buyer.setBuyerId(existingBuyer.getBuyerId());
        buyer.setUpdateBy(operator);
        buyer.setUpdateDate(LocalDateTime.now());

        buyerDao.saveOrUpdate(buyer);
        return buyer.getBuyerId();
    }

    // ─────────────────────────────
    // ✅ Delete Buyer
    // ─────────────────────────────
    public void deleteBuyer(UUID buyerId) {
        if (buyerDao.findById(buyerId) == null) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("buyerId", "Buyer not found");
            throw new ServiceValidationException(errors);
        }

        buyerDao.deleteBuyer(buyerId);
    }
    
 // ─────────────────────────────
    // ✅ Add Product
    // ─────────────────────────────
    public UUID addProduct(ProductDto product, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();

        // Validate productCode unique
        if (productDao.findByProductCode(product.getProductCode()) != null) {
            errors.put("productCode", "รหัสสินค้านี้มีอยู่แล้ว");
        }

        // Validate sellerId
        if (product.getSellerId() == null) {
            errors.put("sellerId", "sellerId is required");
        }
        
        // Validate taxCalFlag
        if (product.getTaxCalFlag() == null ||
            !(product.getTaxCalFlag().equalsIgnoreCase("Incl") || product.getTaxCalFlag().equalsIgnoreCase("Excl"))) {
            errors.put("taxCalFlag", "taxCalFlag ต้องเป็นค่า 'Incl' หรือ 'Excl' เท่านั้น");
        }

        if (!errors.isEmpty()) throw new ServiceValidationException(errors);

        product.setProductId(UUID.randomUUID());
        product.setCreateBy(operator);
        product.setCreateDate(LocalDateTime.now());
        productDao.saveOrUpdate(product);

        return product.getProductId();
    }

    // ─────────────────────────────
    // ✅ Edit Product
    // ─────────────────────────────
    public UUID editProduct(ProductDto product, String operator) {
        Map<String, String> errors = new LinkedHashMap<>();

        ProductDto existing = productDao.findByProductCode(product.getProductCode());
        if (existing == null) {
            errors.put("productCode", "ไม่พบข้อมูลสินค้านี้ในระบบ");
            throw new ServiceValidationException(errors);
        }

        if (product.getSellerId() == null) {
            errors.put("sellerId", "sellerId is required");
        }
        
        // Validate taxCalFlag
        if (product.getTaxCalFlag() == null ||
            !(product.getTaxCalFlag().equalsIgnoreCase("Incl") || product.getTaxCalFlag().equalsIgnoreCase("Excl"))) {
            errors.put("taxCalFlag", "taxCalFlag ต้องเป็นค่า 'Incl' หรือ 'Excl' เท่านั้น");
        }

        if (!errors.isEmpty()) throw new ServiceValidationException(errors);

        product.setProductId(existing.getProductId());
        product.setUpdateBy(operator);
        product.setUpdateDate(LocalDateTime.now());
        productDao.saveOrUpdate(product);

        return product.getProductId();
    }

    // ─────────────────────────────
    // ✅ Delete Product
    // ─────────────────────────────
    public void deleteProduct(UUID productId) {
        ProductDto existing = productDao.findById(productId);
        if (existing == null) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("productId", "Product not found");
            throw new ServiceValidationException(errors);
        }

        productDao.deleteProduct(productId);
    }

}
