package com.ez.taxform.repository;

import com.ez.taxform.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<UserDto> {
    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDto user = new UserDto();
        user.setUserId((UUID) rs.getObject("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setUserPassword(rs.getString("user_password"));
        user.setBranchId((UUID) rs.getObject("branch_id"));
        user.setSellerId((UUID) rs.getObject("seller_id"));
        user.setEnableFlag(rs.getString("enable_flag"));
        user.setCreateBy(rs.getString("create_by"));
        user.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        user.setUpdateBy(rs.getString("update_by"));
        if (rs.getTimestamp("update_date") != null) {
            user.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
        }
        return user;
    }
}
