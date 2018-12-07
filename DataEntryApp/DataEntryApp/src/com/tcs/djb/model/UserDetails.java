/************************************************************************************************************
 * @(#) UserDetails.java   28 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.List;

/**
 * <p>
 * Details of User Created in LDAP.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * @since 28-05-2013
 * @history 01-09-2013 Suraj Tiwari Added functionality for ZRO Location tagging
 *          of Data Entry User, according to JTrac DJB-4549
 */
public class UserDetails {

	private String authenticationErrorCode;
	private String emailId;
	private String firstName;
	private String[] groups;
	private String isActive;
	private boolean isAuthenticatedUser;
	private String isLocked;
	private String lastloginTime;
	private String lastName;
	private String mobileNo;
	private String page;
	private String password;
	private String status;
	private String uniqueMember;
	private List<String> uniqueMemberList;
	private String userAddress;
	private String userGroup;
	private String userGroupVal;
	private String userId;
	private String userName;
	private String userRole;
	private String userRoleDesc;
	private String zone;
	private String zroLocation;

	/**
	 * @return the authenticationErrorCode
	 */
	public String getAuthenticationErrorCode() {
		return authenticationErrorCode;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the groups
	 */
	public String[] getGroups() {
		return groups;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @return the isLocked
	 */
	public String getIsLocked() {
		return isLocked;
	}

	/**
	 * @return the lastloginTime
	 */
	public String getLastloginTime() {
		return lastloginTime;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the uniqueMember
	 */
	public String getUniqueMember() {
		return uniqueMember;
	}

	/**
	 * @return the uniqueMemberList
	 */
	public List<String> getUniqueMemberList() {
		return uniqueMemberList;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @return the userGroup
	 */
	public String getUserGroup() {
		return userGroup;
	}

	/**
	 * @return the userGroupVal
	 */
	public String getUserGroupVal() {
		return userGroupVal;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @return the userRoleDesc
	 */
	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * @param zroLocation the zroLocation to set
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}

	/**
	 * @return the isAuthenticatedUser
	 */
	public boolean isAuthenticatedUser() {
		return isAuthenticatedUser;
	}

	/**
	 * @param isAuthenticatedUser
	 *            the isAuthenticatedUser to set
	 */
	public void setAuthenticatedUser(boolean isAuthenticatedUser) {
		this.isAuthenticatedUser = isAuthenticatedUser;
	}

	/**
	 * @param authenticationErrorCode the authenticationErrorCode to set
	 */
	public void setAuthenticationErrorCode(String authenticationErrorCode) {
		this.authenticationErrorCode = authenticationErrorCode;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param isLocked
	 *            the isLocked to set
	 */
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @param lastloginTime
	 *            the lastloginTime to set
	 */
	public void setLastloginTime(String lastloginTime) {
		this.lastloginTime = lastloginTime;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param uniqueMember
	 *            the uniqueMember to set
	 */
	public void setUniqueMember(String uniqueMember) {
		this.uniqueMember = uniqueMember;
	}

	/**
	 * @param uniqueMemberList
	 *            the uniqueMemberList to set
	 */
	public void setUniqueMemberList(List<String> uniqueMemberList) {
		this.uniqueMemberList = uniqueMemberList;
	}

	/**
	 * @param userAddress
	 *            the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @param userGroup
	 *            the userGroup to set
	 */
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @param userGroupVal
	 *            the userGroupVal to set
	 */
	public void setUserGroupVal(String userGroupVal) {
		this.userGroupVal = userGroupVal;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @param userRoleDesc
	 *            the userRoleDesc to set
	 */
	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
