/************************************************************************************************************
 * @(#) MeterReplacementDetail.java   12 Feb 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains details of Meter Replacement.
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @history Matiur Rahman Added oldMeterAverageRead on 11-12-2012
 * @history Matiur Rahman Added currentAverageConsumption,oldSAType on
 *          12-02-2013
 * @history 19-03-2013 Matiur Rahman Added
 *          lastActiveMeterInstalDate,lastActiveMeterRead
 *          ,lastActiveMeterRemarkCode,meterReplacementDetailRemark,
 *          nextValidMeterInstalldate
 *          ,noOfFloors,oldMeterReadDate,oldSAStartDate,preBillStatID
 * @history 21-03-2013 Tuhin Jana Added seqNo
 * @history 28-03-2013 Matiur Rahman Added serviceSeqNo
 * @history 03-01-2014 Rajib Hazarika Added vendorName
 */
public class MeterReplacementDetail {
	private String area;
	private String badgeNo;
	private String billRound;
	private String consumerCategory;
	private String consumerType;
	private String createDate;
	private String createdByID;
	private String currentAverageConsumption;
	private String currentMeterReadDate;
	private String currentMeterReadRemarkCode;
	private double currentMeterRegisterRead;
	private String isLNTServiceProvider;
	private String kno;
	private String lastActiveMeterInstalDate;
	private String lastActiveMeterRead;
	private String lastActiveMeterRemarkCode;
	private String lastUpdateDate;
	private String lastUpdatedByID;
	private String manufacturer;
	private String meterInstalDate;
	private String meterReaderName;
	private String meterReplacementDetailRemark;
	private String meterReplaceStageID;
	private String meterType;
	private String model;
	private String mrNo;
	private String name;
	private double newAverageConsumption;
	private String nextValidMeterInstalldate;
	private String noOfFloors;
	private double oldMeterAverageRead;
	private String oldMeterReadDate;
	private String oldMeterReadRemarkCode;
	private double oldMeterRegisterRead;
	private String oldSAStartDate;
	private String oldSAType;
	private String preBillStatID;
	private String saType;
	private String seqNo;
	private String serviceSeqNo;
	private String waterConnectionSize;
	private String waterConnectionUse;
	private String wcNo;
	private double zeroRead;
	private String zeroReadRemarkCode;
	private String zone;
	private String vendorName;
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}
	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	/**
	 * @return the badgeNo
	 */
	public String getBadgeNo() {
		return badgeNo;
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
	 * @return the currentAverageConsumption
	 */
	public String getCurrentAverageConsumption() {
		return currentAverageConsumption;
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
	 * @return the isLNTServiceProvider
	 */
	public String getIsLNTServiceProvider() {
		return isLNTServiceProvider;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastActiveMeterInstalDate
	 */
	public String getLastActiveMeterInstalDate() {
		return lastActiveMeterInstalDate;
	}

	/**
	 * @return the lastActiveMeterRead
	 */
	public String getLastActiveMeterRead() {
		return lastActiveMeterRead;
	}

	/**
	 * @return the lastActiveMeterRemarkCode
	 */
	public String getLastActiveMeterRemarkCode() {
		return lastActiveMeterRemarkCode;
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
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @return the meterInstalDate
	 */
	public String getMeterInstalDate() {
		return meterInstalDate;
	}

	/**
	 * @return the meterReaderName
	 */
	public String getMeterReaderName() {
		return meterReaderName;
	}

	/**
	 * @return the meterReplacementDetailRemark
	 */
	public String getMeterReplacementDetailRemark() {
		return meterReplacementDetailRemark;
	}

	/**
	 * @return the meterReplaceStageID
	 */
	public String getMeterReplaceStageID() {
		return meterReplaceStageID;
	}

	/**
	 * @return the meterType
	 */
	public String getMeterType() {
		return meterType;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
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
	 * @return the nextValidMeterInstalldate
	 */
	public String getNextValidMeterInstalldate() {
		return nextValidMeterInstalldate;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @return the oldMeterAverageRead
	 */
	public double getOldMeterAverageRead() {
		return oldMeterAverageRead;
	}

	/**
	 * @return the oldMeterReadDate
	 */
	public String getOldMeterReadDate() {
		return oldMeterReadDate;
	}

	/**
	 * @return the oldMeterReadRemarkCode
	 */
	public String getOldMeterReadRemarkCode() {
		return oldMeterReadRemarkCode;
	}

	/**
	 * @return the oldMeterRegisterRead
	 */
	public double getOldMeterRegisterRead() {
		return oldMeterRegisterRead;
	}

	/**
	 * @return the oldSAStartDate
	 */
	public String getOldSAStartDate() {
		return oldSAStartDate;
	}

	/**
	 * @return the oldSAType
	 */
	public String getOldSAType() {
		return oldSAType;
	}

	/**
	 * @return the preBillStatID
	 */
	public String getPreBillStatID() {
		return preBillStatID;
	}

	/**
	 * @return the saType
	 */
	public String getSaType() {
		return saType;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return the serviceSeqNo
	 */
	public String getServiceSeqNo() {
		return serviceSeqNo;
	}

	/**
	 * @return the waterConnectionSize
	 */
	public String getWaterConnectionSize() {
		return waterConnectionSize;
	}

	/**
	 * @return the waterConnectionUse
	 */
	public String getWaterConnectionUse() {
		return waterConnectionUse;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @return the zeroRead
	 */
	public double getZeroRead() {
		return zeroRead;
	}

	/**
	 * @return the zeroReadRemarkCode
	 */
	public String getZeroReadRemarkCode() {
		return zeroReadRemarkCode;
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
	 * @param badgeNo
	 *            the badgeNo to set
	 */
	public void setBadgeNo(String badgeNo) {
		this.badgeNo = badgeNo;
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
	 * @param currentAverageConsumption
	 *            the currentAverageConsumption to set
	 */
	public void setCurrentAverageConsumption(String currentAverageConsumption) {
		this.currentAverageConsumption = currentAverageConsumption;
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
	 * @param isLNTServiceProvider
	 *            the isLNTServiceProvider to set
	 */
	public void setIsLNTServiceProvider(String isLNTServiceProvider) {
		this.isLNTServiceProvider = isLNTServiceProvider;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastActiveMeterInstalDate
	 *            the lastActiveMeterInstalDate to set
	 */
	public void setLastActiveMeterInstalDate(String lastActiveMeterInstalDate) {
		this.lastActiveMeterInstalDate = lastActiveMeterInstalDate;
	}

	/**
	 * @param lastActiveMeterRead
	 *            the lastActiveMeterRead to set
	 */
	public void setLastActiveMeterRead(String lastActiveMeterRead) {
		this.lastActiveMeterRead = lastActiveMeterRead;
	}

	/**
	 * @param lastActiveMeterRemarkCode
	 *            the lastActiveMeterRemarkCode to set
	 */
	public void setLastActiveMeterRemarkCode(String lastActiveMeterRemarkCode) {
		this.lastActiveMeterRemarkCode = lastActiveMeterRemarkCode;
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
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @param meterInstalDate
	 *            the meterInstalDate to set
	 */
	public void setMeterInstalDate(String meterInstalDate) {
		this.meterInstalDate = meterInstalDate;
	}

	/**
	 * @param meterReaderName
	 *            the meterReaderName to set
	 */
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}

	/**
	 * @param meterReplacementDetailRemark
	 *            the meterReplacementDetailRemark to set
	 */
	public void setMeterReplacementDetailRemark(
			String meterReplacementDetailRemark) {
		this.meterReplacementDetailRemark = meterReplacementDetailRemark;
	}

	/**
	 * @param meterReplaceStageID
	 *            the meterReplaceStageID to set
	 */
	public void setMeterReplaceStageID(String meterReplaceStageID) {
		this.meterReplaceStageID = meterReplaceStageID;
	}

	/**
	 * @param meterType
	 *            the meterType to set
	 */
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * @param nextValidMeterInstalldate
	 *            the nextValidMeterInstalldate to set
	 */
	public void setNextValidMeterInstalldate(String nextValidMeterInstalldate) {
		this.nextValidMeterInstalldate = nextValidMeterInstalldate;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	/**
	 * @param oldMeterAverageRead
	 *            the oldMeterAverageRead to set
	 */
	public void setOldMeterAverageRead(double oldMeterAverageRead) {
		this.oldMeterAverageRead = oldMeterAverageRead;
	}

	/**
	 * @param oldMeterReadDate
	 *            the oldMeterReadDate to set
	 */
	public void setOldMeterReadDate(String oldMeterReadDate) {
		this.oldMeterReadDate = oldMeterReadDate;
	}

	/**
	 * @param oldMeterReadRemarkCode
	 *            the oldMeterReadRemarkCode to set
	 */
	public void setOldMeterReadRemarkCode(String oldMeterReadRemarkCode) {
		this.oldMeterReadRemarkCode = oldMeterReadRemarkCode;
	}

	/**
	 * @param oldMeterRegisterRead
	 *            the oldMeterRegisterRead to set
	 */
	public void setOldMeterRegisterRead(double oldMeterRegisterRead) {
		this.oldMeterRegisterRead = oldMeterRegisterRead;
	}

	/**
	 * @param oldSAStartDate
	 *            the oldSAStartDate to set
	 */
	public void setOldSAStartDate(String oldSAStartDate) {
		this.oldSAStartDate = oldSAStartDate;
	}

	/**
	 * @param oldSAType
	 *            the oldSAType to set
	 */
	public void setOldSAType(String oldSAType) {
		this.oldSAType = oldSAType;
	}

	/**
	 * @param preBillStatID
	 *            the preBillStatID to set
	 */
	public void setPreBillStatID(String preBillStatID) {
		this.preBillStatID = preBillStatID;
	}

	/**
	 * @param saType
	 *            the saType to set
	 */
	public void setSaType(String saType) {
		this.saType = saType;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @param serviceSeqNo the serviceSeqNo to set
	 */
	public void setServiceSeqNo(String serviceSeqNo) {
		this.serviceSeqNo = serviceSeqNo;
	}

	/**
	 * @param waterConnectionSize
	 *            the waterConnectionSize to set
	 */
	public void setWaterConnectionSize(String waterConnectionSize) {
		this.waterConnectionSize = waterConnectionSize;
	}

	/**
	 * @param waterConnectionUse
	 *            the waterConnectionUse to set
	 */
	public void setWaterConnectionUse(String waterConnectionUse) {
		this.waterConnectionUse = waterConnectionUse;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}

	/**
	 * @param zeroRead
	 *            the zeroRead to set
	 */
	public void setZeroRead(double zeroRead) {
		this.zeroRead = zeroRead;
	}

	/**
	 * @param zeroReadRemarkCode
	 *            the zeroReadRemarkCode to set
	 */
	public void setZeroReadRemarkCode(String zeroReadRemarkCode) {
		this.zeroReadRemarkCode = zeroReadRemarkCode;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

}
