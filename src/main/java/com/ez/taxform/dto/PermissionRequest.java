package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PermissionRequest {
	private UUID permissionId;
	private String permissionCode;
	private String permissionName;
	private UUID menuId;
	private String enableFlag;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	// getters/setters...
	public UUID getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public UUID getMenuId() {
		return menuId;
	}

	public void setMenuId(UUID menuId) {
		this.menuId = menuId;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
}
