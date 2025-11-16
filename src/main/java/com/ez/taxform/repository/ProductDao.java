package com.ez.taxform.repository;

import com.ez.taxform.dto.ProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ProductDto> rowMapper = (rs, rowNum) -> {
        ProductDto p = new ProductDto();
        p.setProductId(UUID.fromString(rs.getString("product_id")));
        p.setProductCode(rs.getString("product_code"));
        p.setProductName(rs.getString("product_name"));
        p.setProductDescription(rs.getString("product_description"));
        p.setTaxCalFlag(rs.getString("tax_cal_flag"));
        p.setProductChargeamt(rs.getDouble("product_chargeamt"));
        p.setProductTaxTypeCode(rs.getString("product_tax_type_code"));
        p.setProductTaxCalrate(rs.getInt("product_tax_calrate"));
        p.setProductUnit(rs.getString("product_unit"));
        p.setEnableFlag(rs.getString("enable_flag"));
        p.setCreateBy(rs.getString("create_by"));
        if (rs.getTimestamp("create_date") != null)
            p.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        p.setUpdateBy(rs.getString("update_by"));
        if (rs.getTimestamp("update_date") != null)
            p.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
        p.setSellerId(UUID.fromString(rs.getString("seller_id")));
        return p;
    };

    public ProductDto findById(UUID productId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM product WHERE product_id = ?",
                    rowMapper, productId
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public ProductDto findByProductCode(String productCode) {
        List<ProductDto> list = jdbcTemplate.query(
                "SELECT * FROM product WHERE product_code = ?",
                rowMapper, productCode
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveOrUpdate(ProductDto product) {
        if (product.getProductId() != null && findById(product.getProductId()) != null) {
            // update
            return jdbcTemplate.update("""
                    UPDATE product SET product_name = ?, product_description = ?, tax_cal_flag = ?, 
                        product_chargeamt = ?, product_tax_type_code = ?, product_tax_calrate = ?, product_unit = ?, 
                        enable_flag = ?, update_by = ?, update_date = ?, seller_id = ?
                    WHERE product_id = ?
                    """,
                    product.getProductName(), product.getProductDescription(), product.getTaxCalFlag(),
                    product.getProductChargeamt(), product.getProductTaxTypeCode(), product.getProductTaxCalrate(),
                    product.getProductUnit(), product.getEnableFlag(), product.getUpdateBy(), product.getUpdateDate(),
                    product.getSellerId(), product.getProductId()
            );
        } else {
            // insert
            product.setProductId(UUID.randomUUID());
            return jdbcTemplate.update("""
                    INSERT INTO product (product_id, product_code, product_name, product_description, tax_cal_flag, 
                        product_chargeamt, product_tax_type_code, product_tax_calrate, product_unit, enable_flag, 
                        create_by, create_date, seller_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """,
                    product.getProductId(), product.getProductCode(), product.getProductName(),
                    product.getProductDescription(), product.getTaxCalFlag(), product.getProductChargeamt(),
                    product.getProductTaxTypeCode(), product.getProductTaxCalrate(), product.getProductUnit(),
                    product.getEnableFlag(), product.getCreateBy(), product.getCreateDate(), product.getSellerId()
            );
        }
    }

    public int deleteProduct(UUID productId) {
        return jdbcTemplate.update("DELETE FROM product WHERE product_id = ?", productId);
    }
}
