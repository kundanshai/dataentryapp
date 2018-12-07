/************************************************************************************************************
 * @(#) MeterReadUploadRequest.java   14 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * This is the main web service parameter sent by the Hand Held Device(HHD) for
 * uploading meter reading to the Database. <code>MeterReadUploadRequest</code>
 * contains the web service credential and meter Read Upload Details.
 * </p>
 * 
 * @see MeterReadUploadDetails
 * @see WebServiceUserDetails
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * @since 14-03-2013
 * 
 */
public class MeterReadUploadRequest {
	/**
	 * To validate whether the records will be frozen for bill generation or
	 * not.<br/>
	 * if Y means MRD to be frozen<br/>
	 * else no freezing.
	 * 
	 */
	private String freezeFlag;
	/**
	 * Meter Read Upload Details.
	 */
	private MeterReadUploadDetails[] meterReadUploadDetails;

	/**
	 * MR key of the uploaded MRD
	 */
	private String mrkey;

	private String userId;

	/**
	 * 
	 */
	public MeterReadUploadRequest() {
	}

	/**
	 * @return the freezeFlag
	 */
	public String getFreezeFlag() {
		return freezeFlag;
	}

	/**
	 * @return the meterReadUploadDetails
	 */
	public MeterReadUploadDetails[] getMeterReadUploadDetails() {
		return meterReadUploadDetails;
	}

	/**
	 * @return the mrkey
	 */
	public String getMrkey() {
		return mrkey;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param freezeFlag
	 *            the freezeFlag to set
	 */
	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	/**
	 * @param meterReadUploadDetails
	 *            the meterReadUploadDetails to set
	 */
	public void setMeterReadUploadDetails(
			MeterReadUploadDetails[] meterReadUploadDetails) {
		this.meterReadUploadDetails = meterReadUploadDetails;
	}

	/**
	 * @param mrkey
	 *            the mrkey to set
	 */
	public void setMrkey(String mrkey) {
		this.mrkey = mrkey;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
