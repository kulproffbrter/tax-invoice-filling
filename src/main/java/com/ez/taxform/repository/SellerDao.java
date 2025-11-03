package com.ez.taxform.repository;

import com.ez.taxform.dto.SellerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public class SellerDao {
	private final JdbcTemplate jdbcTemplate;

    public SellerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public boolean existsById(UUID sellerId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM seller WHERE seller_id = ?",
            Integer.class,
            sellerId
        );
        return count != null && count > 0;
    }
    
    // ตรวจสอบ sellerTaxId ซ้ำ
    public boolean existsBySellerTaxId(String sellerTaxId) {
        String sql = "SELECT COUNT(*) FROM seller WHERE seller_tax_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sellerTaxId);
        return count != null && count > 0;
    }

    public int saveOrUpdate(SellerDto seller) {
    	
    	// ตรวจสอบว่ามี sellerId อยู่ใน DB หรือไม่
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM seller WHERE seller_id = ?", Integer.class, seller.getSellerId()
        );

        if (count != null && count > 0) {
            // Update
            String updateSql = """
                UPDATE seller
                SET seller_name_th = ?, seller_name_en = ?, seller_type_tax = ?, seller_tax_id = ?,
                    branch_id = ?, seller_phone_number = ?, logo = ?, update_by = ?, update_date = ?
                WHERE seller_id = ?
            """;
            return jdbcTemplate.update(updateSql,
                    seller.getSellerNameTh(),
                    seller.getSellerNameEn(),
                    seller.getSellerTypeTax(),
                    seller.getSellerTaxId(),
                    seller.getBranchId(),
                    seller.getSellerPhoneNumber(),
                    seller.getLogo(),
            		seller.getUpdateBy(),
            		seller.getUpdateDate(),
            		seller.getSellerId());
        } else {
    	
    	//Insert
        String sql = """
            INSERT INTO seller (seller_id, seller_name_th, seller_name_en, seller_type_tax, seller_tax_id,
                                 branch_id, seller_phone_number, logo, create_by, create_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // ถ้า sellerId ยังไม่มีค่า ให้สร้าง UUID ใหม่
        if (seller.getSellerId() == null) {
            seller.setSellerId(UUID.randomUUID());
        }
        
        if (seller.getSellerTypeTax() == null || seller.getSellerTypeTax().isBlank()) {
            seller.setSellerTypeTax("TXID");
        }

        return jdbcTemplate.update(sql,
                seller.getSellerId(),
                seller.getSellerNameTh(),
                seller.getSellerNameEn(),
                seller.getSellerTypeTax(),
                seller.getSellerTaxId(),
                seller.getBranchId(),
                seller.getSellerPhoneNumber(),
                seller.getLogo(),
                seller.getCreateBy(),
                seller.getCreateDate());
        }
    }
}
