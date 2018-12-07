/************************************************************************************************************
 * @(#) DisconnectionDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.io.Serializable;

/**
 * <p>
 * Container for Capturing Disconnection Details
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 */
public class DisconnectionDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3701888721097975266L;
	private String area;
	private String billRound;
	private String consumerCategory;
	private String consumerType;
	private String createDate;
	private String createdByID;
	private String currentMeterReadDate;
	private String currentMeterReadRemarkCode;
	private double currentMeterRegisterRead;
	private String kno;
	private String lastUpdateDate;
	private String lastUpdatedByID;
	private String mrNo;
	private String name;
	private double newAverageConsumption;
	private String seqNo;
	private String wcNo;
	private String zone;

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the consumerCategory
	 */
	public String getConsumerCategory() {
		return consumerCategory;
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @return the createdByID
	 */
	public String getCreatedByID() {
		return createdByID;
	}

	/**
	 * @return the currentMeterReadDate
	 */
	public String getCurrentMeterReadDate() {
		return currentMeterReadDate;
	}

	/**
	 * @return the currentMeterReadRemarkCode
	 */
	public String getCurrentMeterReadRemarkCode() {
		return currentMeterReadRemarkCode;
	}

	/**
	 * @return the currentMeterRegisterRead
	 */
	public double getCurrentMeterRegisterRead() {
		return currentMeterRegisterRead;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return the lastUpdatedByID
	 */
	public String getLastUpdatedByID() {
		return lastUpdatedByID;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the newAverageConsumption
	 */
	public double getNewAverageConsumption() {
		return newAverageConsumption;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumerCategory
	 *            the consumerCategory to set
	 */
	public void setConsumerCategory(String consumerCategory) {
		this.consumerCategory = consumerCategory;
	}

	/**
	 * @param consumerType
	 *            the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param createdByID
	 *            the createdByID to set
	 */
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}

	/**
	 * @param currentMeterReadDate
	 *            the currentMeterReadDate to set
	 */
	public void setCurrentMeterReadDate(String currentMeterReadDate) {
		this.currentMeterReadDate = currentMeterReadDate;
	}

	/**
	 * @param currentMeterReadRemarkCode
	 *            the currentMeterReadRemarkCode to set
	 */
	public void setCurrentMeterReadRemarkCode(String currentMeterReadRemarkCode) {
		this.currentMeterReadRemarkCode = currentMeterReadRemarkCode;
	}

	/**
	 * @param currentMeterRegisterRead
	 *            the currentMeterRegisterRead to set
	 */
	public void setCurrentMeterRegisterRead(double currentMeterRegisterRead) {
		this.currentMeterRegisterRead = currentMeterRegisterRead;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @param lastUpdatedByID
	 *            the lastUpdatedByID to set
	 */
	public void setLastUpdatedByID(String lastUpdatedByID) {
		this.lastUpdatedByID = lastUpdatedByID;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param newAverageConsumption
	 *            the newAverageConsumption to set
	 */
	public void setNewAverageConsumption(double newAverageConsumption) {
		this.newAverageConsumption = newAverageConsumption;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
