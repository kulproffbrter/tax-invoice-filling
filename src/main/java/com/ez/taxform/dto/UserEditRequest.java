package com.ez.taxform.dto;

import java.util.UUID;

public class UserEditRequest {
    private UUID userId; // required for edit
    private String fullName;
    private String email;
    private String username; // optional
    private String userPassword; // optional - if supplied will be hashed by service
    private String updateBy;
    // getters/setters...
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
}
