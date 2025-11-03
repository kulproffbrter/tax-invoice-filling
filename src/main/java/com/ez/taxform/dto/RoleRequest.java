package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RoleRequest {
	private UUID roleId;
	private String roleCode;
	private String roleName;
	private String roleLevel;
	private UUID sellerId;
	private String enableFlag;
	private String createBy;
	private LocalDateTime createDate;
	private String updateBy;
	private LocalDateTime updateDate;

	// getters / setters
	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
}
