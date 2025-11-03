package com.ez.taxform.repository;

import com.ez.taxform.dto.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


@Repository
public class UserDao {
	private final JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public boolean existsById(UUID userId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM users WHERE user_id = ?",
            Integer.class,
            userId
        );
        return count != null && count > 0;
    }
    
    public UserDto findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<UserDto> results = jdbcTemplate.query(sql, new UserRowMapper(), username);
        return results.isEmpty() ? null : results.get(0);
    }


    public int saveOrUpdate(UserDto user) {
    	
    	// ตรวจสอบว่ามี UserId อยู่ใน DB หรือไม่
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM users WHERE user_id = ?", Integer.class, user.getUserId()
        );

        if (count != null && count > 0) {
            // Update
            String updateSql = """
                UPDATE users
                SET full_name = ?, email = ?, username = ?, user_password = ?,
                    branch_id = ?, seller_id = ?, enable_flag = ?, update_by = ?, update_date = ?
                WHERE user_id = ?
            """;
            return jdbcTemplate.update(updateSql,
                    user.getFullName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getUserPassword(),
                    user.getBranchId(),
                    user.getSellerId(),
                    user.getEnableFlag(),
                    user.getUpdateBy(),
                    user.getUpdateDate(),
                    user.getUserId());
        } else {
        
    	logger.info("Inserting user: {}", user);
    	
    	//Insert
        String sql = """
            INSERT INTO users (user_id, full_name, email, username, user_password, branch_id, seller_id, enable_flag, create_by, create_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        // ถ้า UserId ยังไม่มีค่า ให้สร้าง UUID ใหม่
        if (user.getUserId() == null) {
        	user.setUserId(UUID.randomUUID());
        }
        
        return jdbcTemplate.update(sql,
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getUserPassword(),
                user.getBranchId(),
                user.getSellerId(),
                user.getEnableFlag(),
                user.getCreateBy(),
                user.getCreateDate());
        }
    }
}
