package com.ez.taxform.repository;

import com.ez.taxform.dto.BranchDto;

import java.util.UUID;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class BranchDao {
	private final JdbcTemplate jdbcTemplate;

    public BranchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public boolean existsByBranchCode(String branchCode) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM branch WHERE branch_code = ?",
            Integer.class,
            branchCode
        );
        return count != null && count > 0;
    }
    
    public BranchDto findById(UUID branchId) {
        String sql = "SELECT * FROM branch WHERE branch_id = ?";

        RowMapper<BranchDto> rowMapper = (rs, rowNum) -> {
            BranchDto b = new BranchDto();
            b.setBranchId(UUID.fromString(rs.getString("branch_id")));
            b.setBranchCode(rs.getString("branch_code"));
            b.setBranchNameTh(rs.getString("branch_name_th"));
            b.setBranchNameEn(rs.getString("branch_name_en"));
            b.setBuildingNo(rs.getString("building_no"));
            b.setAddressDetailTh(rs.getString("address_detail_th"));
            b.setAddressDetailEn(rs.getString("address_detail_en"));
            b.setSubdistrictId(rs.getString("subdistrict_id"));
            b.setDistrictId(rs.getString("district_id"));
            b.setProvinceId(rs.getString("province_id"));
            b.setZipCode(rs.getString("zip_code"));
            b.setCountryId(rs.getString("country_id"));
            b.setSellerId(UUID.fromString(rs.getString("seller_id")));
            b.setCreateBy(rs.getString("create_by"));
            b.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            b.setUpdateBy(rs.getString("update_by"));
            if (rs.getTimestamp("update_date") != null)
                b.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
            b.setEnableFlag(rs.getString("enable_flag"));
            return b;
        };

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, branchId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null; // ถ้าไม่เจอ branch ให้ return null
        }
    }

    
    public BranchDto findByBranchCode(String branchCode) {
        String sql = "SELECT * FROM branch WHERE branch_code = ?";
        List<BranchDto> list = jdbcTemplate.query(sql, new Object[]{branchCode}, (rs, rowNum) -> {
            BranchDto branch = new BranchDto();
            branch.setBranchId(UUID.fromString(rs.getString("branch_id")));
            branch.setBranchCode(rs.getString("branch_code"));
            branch.setBranchNameTh(rs.getString("branch_name_th"));
            branch.setBranchNameEn(rs.getString("branch_name_en"));
            branch.setBuildingNo(rs.getString("building_no"));
            branch.setAddressDetailTh(rs.getString("address_detail_th"));
            branch.setAddressDetailEn(rs.getString("address_detail_en"));
            branch.setSubdistrictId(rs.getString("subdistrict_id"));
            branch.setDistrictId(rs.getString("district_id"));
            branch.setProvinceId(rs.getString("province_id"));
            branch.setZipCode(rs.getString("zip_code"));
            branch.setCountryId(rs.getString("country_id"));
            branch.setSellerId(UUID.fromString(rs.getString("seller_id")));
            branch.setCreateBy(rs.getString("create_by"));
            branch.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            branch.setUpdateBy(rs.getString("update_by"));
            if (rs.getTimestamp("update_date") != null)
                branch.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
            branch.setEnableFlag(rs.getString("enable_flag"));
            return branch;
        });

        if (list.isEmpty()) {
            return null; // หรือ throw custom exception
        }

        return list.get(0);
    }
    
    public BranchDto findByBranchCodeAndSeller(String branchCode, UUID sellerId) {
        String sql = "SELECT * FROM branch WHERE branch_code = ? AND seller_id = ?";
        List<BranchDto> list = jdbcTemplate.query(sql, new Object[]{branchCode, sellerId}, (rs, rowNum) -> {
            BranchDto branch = new BranchDto();
            branch.setBranchId(UUID.fromString(rs.getString("branch_id")));
            branch.setBranchCode(rs.getString("branch_code"));
            branch.setBranchNameTh(rs.getString("branch_name_th"));
            branch.setBranchNameEn(rs.getString("branch_name_en"));
            branch.setBuildingNo(rs.getString("building_no"));
            branch.setAddressDetailTh(rs.getString("address_detail_th"));
            branch.setAddressDetailEn(rs.getString("address_detail_en"));
            branch.setSubdistrictId(rs.getString("subdistrict_id"));
            branch.setDistrictId(rs.getString("district_id"));
            branch.setProvinceId(rs.getString("province_id"));
            branch.setZipCode(rs.getString("zip_code"));
            branch.setCountryId(rs.getString("country_id"));
            branch.setSellerId(UUID.fromString(rs.getString("seller_id")));
            branch.setCreateBy(rs.getString("create_by"));
            branch.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            branch.setUpdateBy(rs.getString("update_by"));
            if (rs.getTimestamp("update_date") != null)
                branch.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
            branch.setEnableFlag(rs.getString("enable_flag"));
            return branch;
        });

        return list.isEmpty() ? null : list.get(0);
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
    
    public int deleteBranch(UUID branchId) {
        String sql = "DELETE FROM branch WHERE branch_id = ?";
        return jdbcTemplate.update(sql, branchId);
    }

}
