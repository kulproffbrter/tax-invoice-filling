package com.ez.taxform.dto;

import java.util.UUID;

public class LoginResponse {
    private UUID userId;
    private String username;
    private String fullName;
    private String email;
    private UUID sellerId;
    private UUID branchId;
    private String message;

    // Getter / Setter
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UUID getSellerId() { return sellerId; }
    public void setSellerId(UUID sellerId) { this.sellerId = sellerId; }

    public UUID getBranchId() { return branchId; }
    public void setBranchId(UUID branchId) { this.branchId = branchId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
