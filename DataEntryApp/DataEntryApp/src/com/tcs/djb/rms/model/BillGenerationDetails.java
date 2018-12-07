/************************************************************************************************************
 * @(#) BillGenerationDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.model;

import java.util.List;

import com.tcs.djb.model.ConsumerDetail;

/**
 * <p>
 * BillGenerationDetails to save requests details received from DataEntryApp based on single Account.
 * </p>
 * 
 * @author Mrityunjoy Misra (Tata Consultancy Services)
 * 
 */
public class BillGenerationDetails {

	
	private String userName;
	
	/**
	 * Consumer No(Account ID).
	 */
	private String acctId;
	/**
	 * bill Round Id.
	 */
	private String billRoundId;
	/**
	 * Status of the Consumer.
	 */
	private String regRead;
	/**
	 * meter Read
	 */
	private String readType;
	/**
	 * meter Read Date.
	 */
	private String readerRemark;
	/**
	 * meter Read Remark
	 */
	private String readDate;
	
	private String meterReaderName;
	
	private String averageConsumption;
	
	private String meterReadSource;
	
	private String billGenerationSource;
	/**
	 * no. Of Floor
	 */
	private String noOfFloors;
	/**
	 * average Consumption.
	 */
	private String billId;
	
	private String consumerStatus;
	
	List<ConsumerDetail> consumerDetailList;
	
	
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public String getBillRoundId() {
		return billRoundId;
	}
	public void setBillRoundId(String billRoundId) {
		this.billRoundId = billRoundId;
	}
	public String getRegRead() {
		return regRead;
	}
	public void setRegRead(String regRead) {
		this.regRead = regRead;
	}
	public String getReadType() {
		return readType;
	}
	public void setReadType(String readType) {
		this.readType = readType;
	}
	public String getReaderRemark() {
		return readerRemark;
	}
	public void setReaderRemark(String readerRemark) {
		this.readerRemark = readerRemark;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	public String getMeterReaderName() {
		return meterReaderName;
	}
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}
	public String getAverageConsumption() {
		return averageConsumption;
	}
	public void setAverageConsumption(String averageConsumption) {
		this.averageConsumption = averageConsumption;
	}
	public String getMeterReadSource() {
		return meterReadSource;
	}
	public void setMeterReadSource(String meterReadSource) {
		this.meterReadSource = meterReadSource;
	}
	public String getBillGenerationSource() {
		return billGenerationSource;
	}
	public void setBillGenerationSource(String billGenerationSource) {
		this.billGenerationSource = billGenerationSource;
	}
	public String getNoOfFloors() {
		return noOfFloors;
	}
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getConsumerStatus() {
		return consumerStatus;
	}
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}
	public List<ConsumerDetail> getConsumerDetailList() {
		return consumerDetailList;
	}
	public void setConsumerDetailList(List<ConsumerDetail> consumerDetailList) {
		this.consumerDetailList = consumerDetailList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
