/************************************************************************************************************
 * @(#) BillGenerationDetails.java   26 Jun 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.io.Serializable;

/**
 * <p>
 * Model Class for Bill Generation Details. It contains all the Details for Bill
 * Generation.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 26-06-2013
 * 
 */
public class BillGenerationDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * average Consumption.
	 */
	private String averageConsumption;
	/**
	 * bill Round Id.
	 */
	private String billRound;
	/**
	 * Status of the Consumer.
	 */
	private String consumerStatus;
	/**
	 * Consumer No(Account ID).
	 */
	private String kno;

	/**
	 * meter Read
	 */
	private String meterRead;

	/**
	 * meter Read Date.
	 */
	private String meterReadDate;
	/**
	 * meter Reader Name.
	 */
	private String meterReaderName;
	/**
	 * meter Read Remark
	 */
	private String meterReadRemark;
	/**
	 * MRD code.
	 */
	private String mrKey;
	/**
	 * no. Of Floor
	 */
	private String noOfFloors;
	/**
	 * read Type.
	 */
	private String readType;

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
	 * @return the meterReaderName
	 */
	public String getMeterReaderName() {
		return meterReaderName;
	}

	/**
	 * @return the meterReadRemark
	 */
	public String getMeterReadRemark() {
		return meterReadRemark;
	}

	/**
	 * @return the mrKey
	 */
	public String getMrKey() {
		return mrKey;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @return the readType
	 */
	public String getReadType() {
		return readType;
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
	 * @param meterReaderName
	 *            the meterReaderName to set
	 */
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}

	/**
	 * @param meterReadRemark
	 *            the meterReadRemark to set
	 */
	public void setMeterReadRemark(String meterReadRemark) {
		this.meterReadRemark = meterReadRemark;
	}

	/**
	 * @param mrKey the mrKey to set
	 */
	public void setMrKey(String mrKey) {
		this.mrKey = mrKey;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	/**
	 * @param readType
	 *            the readType to set
	 */
	public void setReadType(String readType) {
		this.readType = readType;
	}

}
