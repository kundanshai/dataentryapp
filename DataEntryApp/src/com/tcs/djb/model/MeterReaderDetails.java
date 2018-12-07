/************************************************************************************************************
 * @(#) MeterReaderDetails.java   26 Feb 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains details of Meter Reader.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @version 1.0
 * @since 26-FEB-2013
 * @history 23-09-2013 Matiur Rahman Added meterReaderDesignation,
 *          meterReaderEmail, meterReaderEmployeeID, meterReaderMobileNo and
 *          meterReaderZone as per HHD-48.
 * @history: 26-SEP-2015 Rajib changed this file as per JTrac DJB-3871 to add
 *           new feature for meter Reader Profiling
 * 
 */
public class MeterReaderDetails {
	/**
	 * Meter Reader Active Inactive Remark
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String activeInactiveRemark;
	/**
	 * Meter Reader bypass From date
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String bypassFromdate;
	/**
	 * Meter Reader bypass To date
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */

	private String bypassTodate;
	/**
	 * Meter Reader createdBy
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */

	private String createdBy;
	/**
	 * Meter Reader created On
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String createdOn;
	/**
	 * Meter Reader eff From date
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String effFromdate;
	/**
	 * Meter Reader eff To date
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String effTodate;

	/**
	 * Meter Reader hhdId
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String hhdId;
	/**
	 * Designation of the Meter Reader Name
	 */
	private String meterReaderDesignation;

	/**
	 * Email ID of the Meter Reader Name
	 */
	private String meterReaderEmail;
	/**
	 * Employee ID of the Meter Reader.
	 */
	private String meterReaderEmployeeID;
	/**
	 * Meter Reader ID
	 */
	private String meterReaderID;
	/**
	 * Mobile No of the Meter Reader Name
	 */
	private String meterReaderMobileNo;

	/**
	 * Meter Reader Name
	 */
	private String meterReaderName;

	/**
	 * Meter Reader Status
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String meterReaderStatus;
	/**
	 * Zone which the Meter Reader Belong to.
	 */
	private String meterReaderZone;
	/**
	 * Meter Reader ZRO Location Code
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String meterReaderZroCd;
	/**
	 * Meter Reader Suopervisor Employee ID
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String supervisorEmpId;
	private String updatedBy;
	private String updatedOn;
	/**
	 * Create or Update done by.
	 */
	private String userId;

	public MeterReaderDetails() {
	}

	public MeterReaderDetails(String meterReaderZroCd) {
		super();
		this.meterReaderZroCd = meterReaderZroCd;
	}

	/**
	 * Constructor
	 * 
	 * @param meterReaderID
	 * @param meterReaderName
	 */
	public MeterReaderDetails(String meterReaderID, String meterReaderName) {
		this.meterReaderID = meterReaderID;
		this.meterReaderName = meterReaderName;
	}

	public String getActiveInactiveRemark() {
		return activeInactiveRemark;
	}

	public String getBypassFromdate() {
		return bypassFromdate;
	}

	public String getBypassTodate() {
		return bypassTodate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	public String getEffFromdate() {
		return effFromdate;
	}

	public String getEffTodate() {
		return effTodate;
	}

	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @return the meterReaderDesignation
	 */
	public String getMeterReaderDesignation() {
		return meterReaderDesignation;
	}

	/**
	 * @return the meterReaderEmail
	 */
	public String getMeterReaderEmail() {
		return meterReaderEmail;
	}

	/**
	 * @return the meterReaderEmployeeID
	 */
	public String getMeterReaderEmployeeID() {
		return meterReaderEmployeeID;
	}

	/**
	 * @return the meterReaderID
	 */
	public String getMeterReaderID() {
		return meterReaderID;
	}

	/**
	 * @return the meterReaderMobileNo
	 */
	public String getMeterReaderMobileNo() {
		return meterReaderMobileNo;
	}

	/**
	 * @return the meterReaderName
	 */
	public String getMeterReaderName() {
		return meterReaderName;
	}

	public String getMeterReaderStatus() {
		return meterReaderStatus;
	}

	/**
	 * @return the meterReaderZone
	 */
	public String getMeterReaderZone() {
		return meterReaderZone;
	}

	public String getMeterReaderZroCd() {
		return meterReaderZroCd;
	}

	public String getSupervisorEmpId() {
		return supervisorEmpId;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @return the updatedOn
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	public void setActiveInactiveRemark(String activeInactiveRemark) {
		this.activeInactiveRemark = activeInactiveRemark;
	}

	public void setBypassFromdate(String bypassFromdate) {
		this.bypassFromdate = bypassFromdate;
	}

	public void setBypassTodate(String bypassTodate) {
		this.bypassTodate = bypassTodate;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public void setEffFromdate(String effFromdate) {
		this.effFromdate = effFromdate;
	}

	public void setEffTodate(String effTodate) {
		this.effTodate = effTodate;
	}

	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @param meterReaderDesignation
	 *            the meterReaderDesignation to set
	 */
	public void setMeterReaderDesignation(String meterReaderDesignation) {
		this.meterReaderDesignation = meterReaderDesignation;
	}

	/**
	 * @param meterReaderEmail
	 *            the meterReaderEmail to set
	 */
	public void setMeterReaderEmail(String meterReaderEmail) {
		this.meterReaderEmail = meterReaderEmail;
	}

	/**
	 * @param meterReaderEmployeeID
	 *            the meterReaderEmployeeID to set
	 */
	public void setMeterReaderEmployeeID(String meterReaderEmployeeID) {
		this.meterReaderEmployeeID = meterReaderEmployeeID;
	}

	/**
	 * @param meterReaderID
	 *            the meterReaderID to set
	 */
	public void setMeterReaderID(String meterReaderID) {
		this.meterReaderID = meterReaderID;
	}

	/**
	 * @param meterReaderMobileNo
	 *            the meterReaderMobileNo to set
	 */
	public void setMeterReaderMobileNo(String meterReaderMobileNo) {
		this.meterReaderMobileNo = meterReaderMobileNo;
	}

	/**
	 * @param meterReaderName
	 *            the meterReaderName to set
	 */
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}

	public void setMeterReaderStatus(String meterReaderStatus) {
		this.meterReaderStatus = meterReaderStatus;
	}

	/**
	 * @param meterReaderZone
	 *            the meterReaderZone to set
	 */
	public void setMeterReaderZone(String meterReaderZone) {
		this.meterReaderZone = meterReaderZone;
	}

	public void setMeterReaderZroCd(String meterReaderZroCd) {
		this.meterReaderZroCd = meterReaderZroCd;
	}

	public void setSupervisorEmpId(String supervisorEmpId) {
		this.supervisorEmpId = supervisorEmpId;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @param updatedOn
	 *            the updatedOn to set
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeterReaderDetails [MeterReaderStatus=");
		builder.append(meterReaderStatus);
		builder.append(", activeInactiveRemark=");
		builder.append(activeInactiveRemark);
		builder.append(", bypassFromdate=");
		builder.append(bypassFromdate);
		builder.append(", bypassTodate=");
		builder.append(bypassTodate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", effFromdate=");
		builder.append(effFromdate);
		builder.append(", effTodate=");
		builder.append(effTodate);
		builder.append(", hhdId=");
		builder.append(hhdId);
		builder.append(", meterReaderDesignation=");
		builder.append(meterReaderDesignation);
		builder.append(", meterReaderEmail=");
		builder.append(meterReaderEmail);
		builder.append(", meterReaderEmployeeID=");
		builder.append(meterReaderEmployeeID);
		builder.append(", meterReaderID=");
		builder.append(meterReaderID);
		builder.append(", meterReaderMobileNo=");
		builder.append(meterReaderMobileNo);
		builder.append(", meterReaderName=");
		builder.append(meterReaderName);
		builder.append(", meterReaderZone=");
		builder.append(meterReaderZone);
		builder.append(", meterReaderZroCd=");
		builder.append(meterReaderZroCd);
		builder.append(", supervisorEmpId=");
		builder.append(supervisorEmpId);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", updatedOn=");
		builder.append(updatedOn);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}
