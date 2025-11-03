package com.ez.taxform.repository;

import com.ez.taxform.dto.UserRolePermissionRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRolePermissionDao {

    private final JdbcTemplate jdbc;

    public UserRolePermissionDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public boolean existsRolePermission(UUID roleId, UUID permissionId, UUID sellerId) {
        Integer c = jdbc.queryForObject(
            "SELECT COUNT(*) FROM role_permissions WHERE role_id = ? AND permission_id = ? AND seller_id = ?",
            Integer.class,
            roleId, permissionId, sellerId
        );
        return c != null && c > 0;
    }
    
    public boolean existsUserRole(UUID userId, UUID roleId, UUID sellerId) {
        Integer c = jdbc.queryForObject(
            "SELECT COUNT(*) FROM users_role WHERE user_id = ? AND role_id = ? AND seller_id = ?",
            Integer.class,
            userId, roleId, sellerId
        );
        return c != null && c > 0;
    }



    // ─────────────────────────────────────────────
    // ✅ ROLE PERMISSION (INSERT / UPDATE)
    // ─────────────────────────────────────────────
    public int saveOrUpdateRolePermission(UserRolePermissionRequest urp) {

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM role_permissions WHERE role_permission_id = ?",
                Integer.class,
                urp.getRolePermissionId()
        );

        boolean exists = count != null && count > 0;

        if (exists) {
            // ----------------- UPDATE -----------------
            String sql = """
                UPDATE role_permissions
                SET role_id = ?, 
                    permission_id = ?, 
                    seller_id = ?, 
                    update_by = ?,
                    update_date = ?
                WHERE role_permission_id = ?
            """;

            return jdbc.update(sql,
            		urp.getRoleId(),
            		urp.getPermissionId(),
            		urp.getSellerId(),
            		urp.getUpdateBy(),
            		urp.getUpdateDate(),
            		urp.getRolePermissionId()
            );
        } else {
            // ----------------- INSERT -----------------
            if (urp.getRolePermissionId() == null) {
            	urp.setRolePermissionId(UUID.randomUUID());
            }

            String sql = """
                INSERT INTO role_permissions
                (role_permission_id, role_id, permission_id, seller_id, create_by, create_date)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

            return jdbc.update(sql,
            		urp.getRolePermissionId(),
            		urp.getRoleId(),
            		urp.getPermissionId(),
            		urp.getSellerId(),
            		urp.getCreateBy()
            );
        }
    }

    // ─────────────────────────────────────────────
    // ✅ USER ROLE (INSERT / UPDATE)
    // ─────────────────────────────────────────────
    public int saveOrUpdateUserRole(UserRolePermissionRequest ur) {

        // ✅ INSERT MODE (ใช้ Composite PK)
        if (!existsUserRole(ur.getUserId(), ur.getRoleId(), ur.getSellerId())) {

            String sql = """
                INSERT INTO users_role
                (user_id, role_id, seller_id, create_by, create_date)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

            return jdbc.update(sql,
                ur.getUserId(),
                ur.getRoleId(),
                ur.getSellerId(),
                ur.getCreateBy()
            );
        }

        // ✅ UPDATE MODE
        String sql = """
            UPDATE users_role
            SET update_by = ?, 
                update_date = CURRENT_TIMESTAMP
            WHERE user_id = ? AND role_id = ?
        """;

        return jdbc.update(sql,
            ur.getUpdateBy(),
            ur.getUserId(),
            ur.getRoleId()
        );
    }
}