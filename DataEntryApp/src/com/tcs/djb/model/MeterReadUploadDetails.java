/************************************************************************************************************
 * @(#) MeterReadUploadDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * <code>MeterReadUploadDetails</code> contains all there details of a consumer
 * for uploading records via a web service called by the Hand Held Device(HHD)
 * for uploading meter reading to the Database.
 * </p>
 * 
 * @see MeterReadUploadRequest
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 */
public class MeterReadUploadDetails {

	/**
	 * average Consumption.
	 */
	private String averageConsumption;
	/**
	 * bill Round Id.
	 */
	private String billRound;
	/**
	 * consumer Status.
	 */
	private String consumerStatus;

	/**
	 * Consumer No.
	 */
	private String kno;

	/**
	 * meter Read.
	 */
	private String meterRead;

	/**
	 * meter Read Date.
	 */
	private String meterReadDate;

	/**
	 * meter Reader Remark Code.
	 */
	private String meterReadRemark;

	/**
	 * No Of Floors.
	 */
	private String noOfFloors;

	/**
	 * 
	 */
	public MeterReadUploadDetails() {

	}

	/**
	 * @return the averageConsumption
	 */
	public String getAverageConsumption() {
		return averageConsumption;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the consumerStatus
	 */
	public String getConsumerStatus() {
		return consumerStatus;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the meterRead
	 */
	public String getMeterRead() {
		return meterRead;
	}

	/**
	 * @return the meterReadDate
	 */
	public String getMeterReadDate() {
		return meterReadDate;
	}

	/**
	 * @return the meterReadRemark
	 */
	public String getMeterReadRemark() {
		return meterReadRemark;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @param averageConsumption
	 *            the averageConsumption to set
	 */
	public void setAverageConsumption(String averageConsumption) {
		this.averageConsumption = averageConsumption;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumerStatus
	 *            the consumerStatus to set
	 */
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param meterRead
	 *            the meterRead to set
	 */
	public void setMeterRead(String meterRead) {
		this.meterRead = meterRead;
	}

	/**
	 * @param meterReadDate
	 *            the meterReadDate to set
	 */
	public void setMeterReadDate(String meterReadDate) {
		this.meterReadDate = meterReadDate;
	}

	/**
	 * @param meterReadRemark
	 *            the meterReadRemark to set
	 */
	public void setMeterReadRemark(String meterReadRemark) {
		this.meterReadRemark = meterReadRemark;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

}
