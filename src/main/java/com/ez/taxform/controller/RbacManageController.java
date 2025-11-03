package com.ez.taxform.controller;

import com.ez.taxform.dto.*;
import com.ez.taxform.service.RbacManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RbacManageController {

    private final RbacManageService service;

    public RbacManageController(RbacManageService service) {
        this.service = service;
    }

    // ─────────────────────────────────────────────
    // ROLE
    // ─────────────────────────────────────────────
    @PostMapping("/role")
    public ResponseEntity<?> saveRole(@RequestBody RoleRequest req) {
        String operator = "system"; // default operator
        if (req.getCreateBy() != null && !req.getCreateBy().isBlank()) {
            operator = req.getCreateBy();
        }

        UUID roleId = service.saveOrUpdateRole(req, operator);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("message", "Role saved successfully");
        resp.put("roleId", roleId);

        return ResponseEntity.ok(resp);
    }

    // ─────────────────────────────────────────────
    // MENU
    // ─────────────────────────────────────────────
    @PostMapping("/menu")
    public ResponseEntity<?> saveMenu(@RequestBody MenuRequest req) {
        //String operator = req.getCreateBy() != null ? req.getCreateBy() : "system";

        UUID menuId = service.saveOrUpdateMenu(req);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("message", "Menu saved successfully");
        resp.put("menuId", menuId);

        return ResponseEntity.ok(resp);
    }

    // ─────────────────────────────────────────────
    // PERMISSION
    // ─────────────────────────────────────────────
    @PostMapping("/permission")
    public ResponseEntity<?> savePermission(@RequestBody PermissionRequest req) {
        //String operator = req.getCreateBy() != null ? req.getCreateBy() : "system";

        UUID permissionId = service.saveOrUpdatePermission(req);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("message", "Permission saved successfully");
        resp.put("permissionId", permissionId);

        return ResponseEntity.ok(resp);
    }

    // ─────────────────────────────────────────────
    // ROLE PERMISSION
    // ─────────────────────────────────────────────
    @PostMapping("/role-permission")
    public ResponseEntity<?> addRolePermission(@RequestBody UserRolePermissionRequest req) {
        String operator = req.getCreateBy() != null && !req.getCreateBy().isBlank() ? req.getCreateBy() : "system";

        service.saveOrUpdateRolePermission(req, operator);  // <-- เรียกแบบ 2 parameter

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("message", "RolePermission saved successfully");
        return ResponseEntity.ok(resp);
    }

    // ─────────────────────────────────────────────
    // USER ROLE
    // ─────────────────────────────────────────────
    @PostMapping("/user-role")
    public ResponseEntity<?> addUserRole(@RequestBody UserRolePermissionRequest req) {
        String operator = req.getCreateBy() != null && !req.getCreateBy().isBlank() ? req.getCreateBy() : "system";

        service.saveOrUpdateUserRole(req, operator);  // <-- เรียกแบบ 2 parameter

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("message", "UserRole saved successfully");
        return ResponseEntity.ok(resp);
    }
}
