package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDto {
	private UUID userId;
    private String fullName;       // รับค่า Input จาก FE
    private String email;          // รับค่า Input จาก FE
    private String username;       // รับค่า Input จาก FE
    private String userPassword;   // รับค่า Input จาก FE
    private UUID branchId;
    private UUID sellerId;
    private String enableFlag;     // เช่น "Y" หรือ "N"
    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;

    // ---------- Getter / Setter ----------
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
    public UUID getBranchId() { return branchId; }
    public void setBranchId(UUID branchId) { this.branchId = branchId; }
    public UUID getSellerId() { return sellerId; }
    public void setSellerId(UUID sellerId) { this.sellerId = sellerId; }
    public String getEnableFlag() { return enableFlag; }
    public void setEnableFlag(String enableFlag) { this.enableFlag = enableFlag; }
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }
}
