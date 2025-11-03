package com.ez.taxform.repository;

import com.ez.taxform.dto.RoleRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RoleDao {

    private final JdbcTemplate jdbc;

    public RoleDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public boolean existsById(UUID roleId) {
        Integer count = jdbc.queryForObject(
            "SELECT COUNT(*) FROM roles WHERE role_id = ?",
            Integer.class,
            roleId
        );
        return count != null && count > 0;
    }
    
    public boolean existsByRoleCode(String roleCode) {
        Integer c = jdbc.queryForObject(
                "SELECT COUNT(*) FROM roles WHERE role_code = ?",
                Integer.class,
                roleCode
        );
        return c != null && c > 0;
    }


    // -------------------------------
    // ✅ Save or Update Role
    // -------------------------------
    public int saveOrUpdate(RoleRequest r) {

        // ตรวจสอบว่ามี role_id อยู่ในระบบหรือไม่
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM roles WHERE role_id = ?",
                Integer.class,
                r.getRoleId()
        );

        boolean exists = count != null && count > 0;

        if (exists) {

            // -------------------------------
            // ✅ UPDATE
            // -------------------------------
            String updateSql = """
                UPDATE roles
                SET role_code = ?,
                    role_name = ?,
                    role_level = ?,
                    seller_id = ?,
                    enable_flag = ?,
                    update_by = ?,
                    update_date = ?
                WHERE role_id = ?
            """;

            return jdbc.update(updateSql,
                    r.getRoleCode(),
                    r.getRoleName(),
                    r.getRoleLevel(),
                    r.getSellerId(),
                    r.getEnableFlag(),
                    r.getUpdateBy(),
                    r.getUpdateDate(),
                    r.getRoleId()
            );

        } else {

            // -------------------------------
            // ✅ INSERT
            // -------------------------------
            if (r.getRoleId() == null) {
                r.setRoleId(UUID.randomUUID());
            }

            String insertSql = """
                INSERT INTO roles
                (role_id, role_code, role_name, role_level, seller_id,
                 enable_flag, create_by, create_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

            return jdbc.update(insertSql,
                    r.getRoleId(),
                    r.getRoleCode(),
                    r.getRoleName(),
                    r.getRoleLevel(),
                    r.getSellerId(),
                    r.getEnableFlag(),
                    r.getCreateBy()
            );
        }
    }
}
