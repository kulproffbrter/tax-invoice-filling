package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRolePermissionRequest {

	// ---------- user_role section ----------
	private UUID userId; // required for user_role

	// ---------- role_permissions section ----------
	private UUID rolePermissionId; // optional for update
	private UUID roleId; // required for both user_role & role_permission
	private UUID permissionId; // required only for role_permission

	private UUID sellerId;
	private String createBy;
	private LocalDateTime createDate;
	private String updateBy;
	private LocalDateTime updateDate;

	// getters/setters

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(UUID rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public UUID getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
	}

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
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
