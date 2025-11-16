package com.ez.taxform.controller;

import com.ez.taxform.dto.BranchDto;
import com.ez.taxform.dto.BuyerDto;
import com.ez.taxform.dto.ProductDto;

import com.ez.taxform.service.MasterDataService;
import com.ez.taxform.service.ServiceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/eztax/masterdata")
public class MasterDataController {

    private final MasterDataService masterDataService;

    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    // ─────────────────────────────
    // ✅ Add Branch
    // ─────────────────────────────
    @PostMapping("/add/branch")
    public ResponseEntity<Map<String, Object>> addBranch(
    		@Valid @RequestBody BranchDto branch,
            @RequestHeader(value = "X-Operator", required = false) String operator) {

        if (operator == null || operator.isBlank()) {
            operator = "system";
        }

        UUID branchId = masterDataService.addBranch(branch, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "เพิ่มข้อมูลสาขาสำเร็จ");
        response.put("branchId", branchId);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────
    // ✅ Edit Branch
    // ─────────────────────────────
    @PostMapping("/edit/branch")
    public ResponseEntity<Map<String, Object>> editBranch(
    		@Valid @RequestBody BranchDto branch,
            @RequestHeader(value = "X-Operator", required = false) String operator) {

        if (operator == null || operator.isBlank()) {
            operator = "system";
        }

        UUID branchId = masterDataService.editBranch(branch, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "แก้ไขข้อมูลสาขาสำเร็จ");
        response.put("branchId", branchId);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────
    // ✅ Delete Branch
    // ─────────────────────────────
    @DeleteMapping("/delete/branch/{branchId}")
    public ResponseEntity<Map<String, Object>> deleteBranch(@PathVariable(required = false) String branchId) {
        Map<String, String> errors = new LinkedHashMap<>();

        UUID uuid;
        try {
            uuid = UUID.fromString(branchId);
        } catch (IllegalArgumentException e) {
            errors.put("branchId", "Invalid UUID format");
            throw new ServiceValidationException(errors);
        }

        masterDataService.deleteBranch(uuid);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "ลบข้อมูลสาขาเรียบร้อยแล้ว");
        response.put("branchId", uuid);

        return ResponseEntity.ok(response);
    }
    
    // ─────────────────────────────
    // ✅ Add Buyer
    // ─────────────────────────────
    
    @PostMapping("/add/buyer")
    public ResponseEntity<Map<String, Object>> addBuyer(
    		@Valid @RequestBody BuyerDto buyer,
            @RequestHeader(value = "X-Operator", required = false) String operator) {

        if (operator == null || operator.isBlank()) {
            operator = "system";
        }

        UUID buyerId = masterDataService.addBuyer(buyer, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "เพิ่มข้อมูลลูกค้าสำเร็จ");
        response.put("buyerId", buyerId);

        return ResponseEntity.ok(response);
    }

    
    // ─────────────────────────────
    // ✅ Edit Buyer
    // ─────────────────────────────
    
    @PostMapping("/edit/buyer")
    public ResponseEntity<Map<String, Object>> editBuyer(
    		@Valid @RequestBody BuyerDto buyer,
            @RequestHeader(value = "X-Operator", required = false) String operator) {

        if (operator == null || operator.isBlank()) {
            operator = "system";
        }

        UUID buyerId = masterDataService.editBuyer(buyer, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "แก้ไขข้อมูลลูกค้าสำเร็จ");
        response.put("buyerId", buyerId);

        return ResponseEntity.ok(response);
    }
    
    // ─────────────────────────────
    // ✅ Delete Buyer
    // ─────────────────────────────
    
    @DeleteMapping("/delete/buyer/{buyerId}")
    public ResponseEntity<Map<String, Object>> deleteBuyer(@PathVariable String buyerId) {
        Map<String, String> errors = new LinkedHashMap<>();

        UUID uuid;
        try {
            uuid = UUID.fromString(buyerId);
        } catch (IllegalArgumentException e) {
            errors.put("buyerId", "Invalid UUID format");
            throw new ServiceValidationException(errors);
        }

        masterDataService.deleteBuyer(uuid);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "ลบข้อมูลลูกค้าสำเร็จ");
        response.put("buyerId", uuid);

        return ResponseEntity.ok(response);
    }
    
    // ─────────────────────────────
    // ✅ Add Product
    // ─────────────────────────────
    
    @PostMapping("/add/product")
    public ResponseEntity<Map<String, Object>> addProduct(@Valid @RequestBody ProductDto product,
                                                          @RequestHeader(value = "X-Operator", required = false) String operator) {
        if (operator == null || operator.isBlank()) operator = "system";

        UUID productId = masterDataService.addProduct(product, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "เพิ่มสินค้าสำเร็จ");
        response.put("productId", productId);

        return ResponseEntity.ok(response);
    }
    
    // ─────────────────────────────
    // ✅ Edit Product
    // ─────────────────────────────
    
    @PostMapping("/edit/product")
    public ResponseEntity<Map<String, Object>> editProduct(@Valid @RequestBody ProductDto product,
                                                           @RequestHeader(value = "X-Operator", required = false) String operator) {
        if (operator == null || operator.isBlank()) operator = "system";

        UUID productId = masterDataService.editProduct(product, operator);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "แก้ไขสินค้าสำเร็จ");
        response.put("productId", productId);

        return ResponseEntity.ok(response);
    }
    
    // ─────────────────────────────
    // ✅ Delete Product
    // ─────────────────────────────
    
    @DeleteMapping("/delete/product/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable String productId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(productId);
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("productId", "Invalid UUID format");
            throw new ServiceValidationException(errors);
        }

        masterDataService.deleteProduct(uuid);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "ลบสินค้าสำเร็จ");
        response.put("productId", uuid);

        return ResponseEntity.ok(response);
    }
}
