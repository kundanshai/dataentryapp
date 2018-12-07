/************************************************************************************************************
 * @(#) UserRole.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for capturing User Role details.
 * </p>
 */
public class UserRole {
	private String userRoleDesc;

	private String userRoleId;

	public UserRole() {
	}
	public UserRole(String userRoleId, String userRoleDesc) {
		this.userRoleId = userRoleId;
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @return the userRoleDesc
	 */
	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	/**
	 * @return the userRoleId
	 */
	public String getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleDesc
	 *            the userRoleDesc to set
	 */
	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @param userRoleId
	 *            the userRoleId to set
	 */
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}
}
