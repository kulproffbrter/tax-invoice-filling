package com.ez.taxform.repository;

import com.ez.taxform.dto.MenuRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class MenuDao {

    private final JdbcTemplate jdbc;

    public MenuDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ✅ ใช้เช็คก่อน insert ว่า menuCode ซ้ำไหม
    public boolean existsByMenuCode(String menuCode) {
        Integer c = jdbc.queryForObject(
                "SELECT COUNT(*) FROM menu WHERE menu_code = ?",
                Integer.class,
                menuCode
        );
        return c != null && c > 0;
    }

    // ✅ Save or Update Menu
    public int saveOrUpdate(MenuRequest m) {

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM menu WHERE menu_id = ?",
                Integer.class,
                m.getMenuId()
        );

        boolean exists = count != null && count > 0;

        if (exists) {
            // ✅ UPDATE
            String updateSql = """
                UPDATE menu
                SET menu_name_th = ?,
                    menu_name_en = ?,
                    menu_url = ?,
                    menu_icon = ?,
                    parent_menu_id = ?,
                    enable_flag = ?,
                    menu_code = ?,
                    update_date = CURRENT_TIMESTAMP
                WHERE menu_id = ?
            """;

            return jdbc.update(updateSql,
                    m.getMenuNameTh(),
                    m.getMenuNameEn(),
                    m.getMenuUrl(),
                    m.getMenuIcon(),
                    m.getParentMenuId(),
                    m.getEnableFlag(),
                    m.getMenuCode(),
                    m.getMenuId()
            );

        } else {
            // ✅ INSERT
            if (m.getMenuId() == null) {
                m.setMenuId(UUID.randomUUID());
            }

            String insertSql = """
                INSERT INTO menu
                (menu_id, menu_name_th, menu_name_en, menu_url, menu_icon,
                 parent_menu_id, enable_flag, create_date, menu_code)
                VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
            """;

            return jdbc.update(insertSql,
                    m.getMenuId(),
                    m.getMenuNameTh(),
                    m.getMenuNameEn(),
                    m.getMenuUrl(),
                    m.getMenuIcon(),
                    m.getParentMenuId(),
                    m.getEnableFlag(),
                    m.getMenuCode()
            );
        }
    }
}
