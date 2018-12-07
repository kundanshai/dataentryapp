/************************************************************************************************************
 * @(#) HHDCredentialsDetails.java   28 Nov 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains all the variables required for HHD Credentials details.
 * </p>
 * 
 * @author Karthick K(Tata Consultancy Services)
 * @version 1.0
 * @since 28-NOV-2013
 * 
 */
public class HHDCredentialsDetails {

	private String addHHDiD;
	private String addPassword;

	/**
	 * effective From Date
	 */
	private String effFromDate;
	/**
	 * effective To Date
	 */
	private String effToDate;

	private String hhdId;

	private String lastUpdatedBy;

	/**
	 * Last Updated Date
	 */
	private String lastUpdatedOn;

	private String password;

	/**
	 * Serial No.
	 */
	private String slNo;

	public String getAddHHDiD() {
		return addHHDiD;
	}

	public String getAddPassword() {
		return addPassword;
	}

	/**
	 * @return the effFromDate
	 */
	public String getEffFromDate() {
		return effFromDate;
	}

	/**
	 * @return the effToDate
	 */
	public String getEffToDate() {
		return effToDate;
	}

	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @return the lastUpdatedOn
	 */
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	public void setAddHHDiD(String addHHDiD) {
		this.addHHDiD = addHHDiD;
	}

	public void setAddPassword(String addPassword) {
		this.addPassword = addPassword;
	}

	/**
	 * @param effFromDate
	 *            the effFromDate to set
	 */
	public void setEffFromDate(String effFromDate) {
		this.effFromDate = effFromDate;
	}

	/**
	 * @param effToDate
	 *            the effToDate to set
	 */
	public void setEffToDate(String effToDate) {
		this.effToDate = effToDate;
	}

	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @param lastUpdatedBy
	 *            the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedOn
	 *            the lastUpdatedOn to set
	 */
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

}
