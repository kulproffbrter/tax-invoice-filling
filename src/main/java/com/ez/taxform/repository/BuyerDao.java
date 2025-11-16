package com.ez.taxform.repository;

import com.ez.taxform.dto.BuyerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public class BuyerDao {
    private final JdbcTemplate jdbcTemplate;

    public BuyerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<BuyerDto> rowMapper = (rs, rowNum) -> {
        BuyerDto b = new BuyerDto();
        b.setBuyerId(UUID.fromString(rs.getString("buyer_id")));
        b.setBuyerCode(rs.getString("buyer_code"));
        b.setBuyerNameTh(rs.getString("buyer_name_th"));
        b.setBuyerNameEn(rs.getString("buyer_name_en"));
        b.setBuyerTypeTax(rs.getString("buyer_type_tax"));
        b.setBuyerTaxId(rs.getString("buyer_tax_id"));
        b.setBuyerBranchCode(rs.getString("buyer_branch_code"));
        b.setBuyerBranchNameTh(rs.getString("buyer_branch_name_th"));
        b.setBuyerBranchNameEn(rs.getString("buyer_branch_name_en"));
        b.setBuyerAddressTh(rs.getString("buyer_address_th"));
        b.setBuyerAddressEn(rs.getString("buyer_address_en"));
        b.setBuyerZipCode(rs.getString("buyer_zip_code"));
        b.setBuyerCountryId(rs.getString("buyer_country_id"));
        b.setBuyerEmail(rs.getString("buyer_email"));
        b.setBuyerPhoneNumber(rs.getString("buyer_phone_number"));
        b.setEnableFlag(rs.getString("enable_flag"));
        b.setSellerId(UUID.fromString(rs.getString("seller_id"))); // เพิ่ม sellerId
        b.setCreateBy(rs.getString("create_by"));
        b.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        b.setUpdateBy(rs.getString("update_by"));
        if (rs.getTimestamp("update_date") != null)
            b.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
        return b;
    };

    public BuyerDto findById(UUID buyerId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM buyer WHERE buyer_id = ?",
                rowMapper, buyerId
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public BuyerDto findByBuyerCode(String buyerCode) {
        List<BuyerDto> list = jdbcTemplate.query(
            "SELECT * FROM buyer WHERE buyer_code = ?",
            rowMapper, buyerCode
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveOrUpdate(BuyerDto buyer) {
        if (buyer.getBuyerId() != null && findById(buyer.getBuyerId()) != null) {
            return jdbcTemplate.update("""
                UPDATE buyer SET buyer_name_th = ?, buyer_name_en = ?, buyer_type_tax = ?, buyer_tax_id = ?,
                    buyer_branch_code = ?, buyer_branch_name_th = ?, buyer_branch_name_en = ?, buyer_address_th = ?,
                    buyer_address_en = ?, buyer_zip_code = ?, buyer_country_id = ?, buyer_email = ?, buyer_phone_number = ?,
                    enable_flag = ?, seller_id = ?, update_by = ?, update_date = ?
                WHERE buyer_id = ?
            """, buyer.getBuyerNameTh(), buyer.getBuyerNameEn(), buyer.getBuyerTypeTax(),
                buyer.getBuyerTaxId(), buyer.getBuyerBranchCode(), buyer.getBuyerBranchNameTh(),
                buyer.getBuyerBranchNameEn(), buyer.getBuyerAddressTh(), buyer.getBuyerAddressEn(),
                buyer.getBuyerZipCode(), buyer.getBuyerCountryId(), buyer.getBuyerEmail(),
                buyer.getBuyerPhoneNumber(), buyer.getEnableFlag(), buyer.getSellerId(), // เพิ่ม sellerId
                buyer.getUpdateBy(), buyer.getUpdateDate(), buyer.getBuyerId()
            );
        } else {
            buyer.setBuyerId(UUID.randomUUID());
            return jdbcTemplate.update("""
                INSERT INTO buyer (buyer_id, buyer_code, buyer_name_th, buyer_name_en, buyer_type_tax, buyer_tax_id,
                    buyer_branch_code, buyer_branch_name_th, buyer_branch_name_en, buyer_address_th, buyer_address_en,
                    buyer_zip_code, buyer_country_id, buyer_email, buyer_phone_number, enable_flag, seller_id, create_by, create_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, buyer.getBuyerId(), buyer.getBuyerCode(), buyer.getBuyerNameTh(), buyer.getBuyerNameEn(),
                buyer.getBuyerTypeTax(), buyer.getBuyerTaxId(), buyer.getBuyerBranchCode(),
                buyer.getBuyerBranchNameTh(), buyer.getBuyerBranchNameEn(), buyer.getBuyerAddressTh(),
                buyer.getBuyerAddressEn(), buyer.getBuyerZipCode(), buyer.getBuyerCountryId(),
                buyer.getBuyerEmail(), buyer.getBuyerPhoneNumber(), buyer.getEnableFlag(),
                buyer.getSellerId(), // เพิ่ม sellerId
                buyer.getCreateBy(), buyer.getCreateDate()
            );
        }
    }

    public int deleteBuyer(UUID buyerId) {
        return jdbcTemplate.update("DELETE FROM buyer WHERE buyer_id = ?", buyerId);
    }
}
