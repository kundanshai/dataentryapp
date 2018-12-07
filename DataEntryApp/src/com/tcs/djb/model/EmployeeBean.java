/************************************************************************************************************
 * @(#) EmployeeBean.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.List;

/**
 * <p>
 * Container for capturing employee details
 * </p>
 */
public class EmployeeBean {

	private String emailId = null;
	private String extSrcId = null;
	private String flag = null;
	private String fName = null;
	private List<String> listUniqueMem = null;
	private String lName = null;
	private String mobileNo = null;
	private String page = null;
	private String password = null;
	private String status = null;
	private String uniqueMem = null;
	private String userGroup = null;
	private String userGroupVal = null;
	private String userName = null;
	private String userRole = null;

	private String zone;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @return the extSrcId
	 */
	public String getExtSrcId() {
		return extSrcId;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @return the listUniqueMem
	 */
	public List<String> getListUniqueMem() {
		return listUniqueMem;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
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
	 * @return the uniqueMem
	 */
	public String getUniqueMem() {
		return uniqueMem;
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
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @param extSrcId
	 */
	public void setExtSrcId(String extSrcId) {
		this.extSrcId = extSrcId;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @param fName
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @param listUniqueMem
	 */
	public void setListUniqueMem(List<String> listUniqueMem) {
		this.listUniqueMem = listUniqueMem;
	}

	/**
	 * @param lName
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @param mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @param page
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param uniqueMem
	 */
	public void setUniqueMem(String uniqueMem) {
		this.uniqueMem = uniqueMem;
	}

	/**
	 * @param userGroup
	 */
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @param userGroupVal
	 */
	public void setUserGroupVal(String userGroupVal) {
		this.userGroupVal = userGroupVal;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param userRole
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @param zone
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

}
