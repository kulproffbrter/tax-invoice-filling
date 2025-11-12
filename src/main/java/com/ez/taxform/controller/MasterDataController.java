package com.ez.taxform.controller;

import com.ez.taxform.dto.BranchDto;
import com.ez.taxform.service.MasterDataService;
import com.ez.taxform.service.ServiceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody BranchDto branch,
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
            @RequestBody BranchDto branch,
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


}
