package com.ez.taxform.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MenuRequest {
	private UUID menuId;
	private String menuCode;
	private String menuNameTh;
	private String menuNameEn;
	private String menuUrl;
	private String menuIcon;
	private UUID parentMenuId;
	private String enableFlag;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	// getters/setters...
	public UUID getMenuId() {
		return menuId;
	}

	public void setMenuId(UUID menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuNameTh() {
		return menuNameTh;
	}

	public void setMenuNameTh(String menuNameTh) {
		this.menuNameTh = menuNameTh;
	}

	public String getMenuNameEn() {
		return menuNameEn;
	}

	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public UUID getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(UUID parentMenuId) {
		this.parentMenuId = parentMenuId;
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
