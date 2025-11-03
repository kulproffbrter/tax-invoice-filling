package com.ez.taxform.repository;

import com.ez.taxform.dto.PermissionRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PermissionDao {

    private final JdbcTemplate jdbc;

    public PermissionDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
 // ✅ เช็คว่ามี permission_code ซ้ำหรือไม่
    public boolean existsByPermissionCode(String permissionCode) {
        Integer c = jdbc.queryForObject(
                "SELECT COUNT(*) FROM permissions WHERE permission_code = ?",
                Integer.class,
                permissionCode
        );
        return c != null && c > 0;
    }

    // -------------------------------
    // ✅ Save or Update Permission
    // -------------------------------
    public int saveOrUpdate(PermissionRequest p) {

        // ตรวจสอบว่า permission_id มีอยู่ใน DB หรือไม่
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM permissions WHERE permission_id = ?",
                Integer.class,
                p.getPermissionId()
        );

        boolean exists = count != null && count > 0;

        if (exists) {

            // -------------------------------
            // ✅ UPDATE
            // -------------------------------
            String updateSql = """
                UPDATE permissions
                SET permission_name = ?,
                    menu_id = ?,
                    enable_flag = ?,
                    permission_code = ?,
                    update_date = ?
                WHERE permission_id = ?
            """;

            return jdbc.update(updateSql,
                    p.getPermissionName(),
                    p.getMenuId(),
                    p.getEnableFlag(),
                    p.getPermissionCode(),
                    p.getUpdateDate(),
                    p.getPermissionId()
            );

        } else {

            // -------------------------------
            // ✅ INSERT
            // -------------------------------
            if (p.getPermissionId() == null) {
                p.setPermissionId(UUID.randomUUID());
            }

            String insertSql = """
                INSERT INTO permissions
                (permission_id, permission_name, menu_id, enable_flag, create_date, permission_code)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
            """;

            return jdbc.update(insertSql,
                    p.getPermissionId(),
                    p.getPermissionName(),
                    p.getMenuId(),
                    p.getEnableFlag(),
                    p.getPermissionCode()
            );
        }
    }
}
