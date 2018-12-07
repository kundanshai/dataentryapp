/************************************************************************************************************
 * @(#) HHDUserMasterDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * HHD User Master Details for HHD User Master Screen.
 * </p>
 * @author Reshma (Tata Consultancy Services)
 *
 */
public class HHDUserMasterDetails {
	
	/**
	 * hhdId for HHD User
	 */
	private String hhdId;
	/**
	 * Serial No.
	 */
	private String slNo;
	/**
	 * userMasterId for HHD User
	 */
	private String userMasterId;
	/**
	 * password for HHD User
	 */
	private String password;
	/**
	 * userLoginCount for HHD User
	 */
	private String userLoginCount;
	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
	}
	/**
	 * @param hhdId the hhdId to set
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}
	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}
	/**
	 * @param slNo the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	/**
	 * @return the userId
	 */
	public String getUserMasterId() {
		return userMasterId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserMasterId(String userMasterId) {
		this.userMasterId = userMasterId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the userLoginCount
	 */
	public String getUserLoginCount() {
		return userLoginCount;
	}
	/**
	 * @param userLoginCount the userLoginCount to set
	 */
	public void setUserLoginCount(String userLoginCount) {
		this.userLoginCount = userLoginCount;
	}
	

}
