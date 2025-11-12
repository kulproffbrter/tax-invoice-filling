package com.ez.taxform.service;

import com.ez.taxform.dto.BranchDto;
import com.ez.taxform.repository.BranchDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MasterDataService {

    private final BranchDao branchDao;

    public MasterDataService(BranchDao branchDao) {
        this.branchDao = branchDao;
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
        
     // ✅ ตรวจสอบ branchCode ซ้ำ
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


}
