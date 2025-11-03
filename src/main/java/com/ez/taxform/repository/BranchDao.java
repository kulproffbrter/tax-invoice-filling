package com.ez.taxform.repository;

import com.ez.taxform.dto.BranchDto;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BranchDao {
	private final JdbcTemplate jdbcTemplate;

    public BranchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveOrUpdate(BranchDto branch) {
        
        // ตรวจสอบว่ามี branchId อยู่ใน DB หรือไม่
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM branch WHERE branch_id = ?", Integer.class, branch.getBranchId()
        );

        if (count != null && count > 0) {
            // Update
            String updateSql = """
                UPDATE branch
                SET branch_code = ?, branch_name_th = ?, branch_name_en = ?, building_no = ?, address_detail_th = ?,
                    address_detail_en = ?, subdistrict_id = ?, district_id = ?, province_id = ?, zip_code = ?,
                    country_id = ?, seller_id = ?, update_by = ?, update_date = ?, enable_flag = ?
                WHERE branch_id = ?
            """;
            return jdbcTemplate.update(updateSql,
            	branch.getBranchCode(),
                branch.getBranchNameTh(),
                branch.getBranchNameEn(),
                branch.getBuildingNo(),
                branch.getAddressDetailTh(),
                branch.getAddressDetailEn(),
                branch.getSubdistrictId(),
                branch.getDistrictId(),
                branch.getProvinceId(),
                branch.getZipCode(),
                branch.getCountryId(),
                branch.getSellerId(),
                branch.getUpdateBy(),
                branch.getUpdateDate(),
                branch.getEnableFlag(),
                branch.getBranchId()
            );
        } else {
        
        //Insert
        String sql = """
            INSERT INTO branch (branch_id, branch_code, branch_name_th, branch_name_en, building_no, address_detail_th,
                                  address_detail_en, subdistrict_id, district_id, province_id, zip_code,
                                  country_id, seller_id, create_by, create_date, enable_flag)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        // ถ้า sellerId ยังไม่มีค่า ให้สร้าง UUID ใหม่
        if (branch.getBranchId() == null) {
        	branch.setBranchId(UUID.randomUUID());
        }

        return jdbcTemplate.update(sql,
                branch.getBranchId(),
                branch.getBranchCode(),
                branch.getBranchNameTh(),
                branch.getBranchNameEn(),
                branch.getBuildingNo(),
                branch.getAddressDetailTh(),
                branch.getAddressDetailEn(),
                branch.getSubdistrictId(),
                branch.getDistrictId(),
                branch.getProvinceId(),
                branch.getZipCode(),
                branch.getCountryId(),
                branch.getSellerId(),
                branch.getCreateBy(),
                branch.getCreateDate(),
                branch.getEnableFlag());
        }
    }
}
