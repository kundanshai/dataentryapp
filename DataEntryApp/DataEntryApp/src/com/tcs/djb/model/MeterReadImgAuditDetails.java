/************************************************************************************************************
 * @(#) MeterReadImgAuditDetails.java   09 Mar 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains details of Meter Read Image Audit Screen.
 * </p>
 * 
 * @author 830700
 * @history 09-03-2016 Atanu Added new variable and its getter and setter as per jTrac ANDROID-293
 *          {@link #annualAvgConsumption}
 */
public class MeterReadImgAuditDetails {

	private String annualAvgConsumption;
	private String area;
	private String assignTo;
	private String assignToFlg;
	private String auditorName;
	private String billAmt;
	/**
	 * Madhuri Added the member variable on 04-09-2017 for Image audit action screen
	 */
	private String billBasis;
	private String billDt;
	private String billGeneratedBy;
	/**
	 * Madhuri Added the member variable on 04-09-2017 for Image audit action screen
	 */
	private String billGenSource;
	private String billId;
	private String billingPeriod;
	private String billRound;
	private String consumption;
	private String consumptnVariatnReasn;
	private String createdBy;
	private String createDttm;
	private String currMtrReadng;
	private String finalAuditAction;
	private String finalAuditBy;
	private String finalAuditOn;
	private String finalAuditStatus;
	private String imgAuditBy;
	private String imgAuditFailReason;
	private String imgAuditFlg;
	private String imgAuditOn;
	private String imgAuditRemrk;
	private String imgAuditStatus;
	private String imgLnk;
	private String isLocked;
	private String kno;
	private String lastAuditDate;
	private String lastAuditRmrk;
	private String lastAuditStatus;
	private String lastMtrReadng;
	private String lastUpdatedBy;
	private String lastUpdateDttm;
	private String markdForAudit;
	private String markdForAuditBy;
	private String markdForAuditOn;
	private String meterReadRemark;
	private String mr;
	private String mtrNo;
	/**
	 * Madhuri Added the member variable on 04-09-2017 for Image audit action screen
	 */
	private String mtrRdrNameEmpId;
	private String mtrRdStatus;
	private String mtrRead;
	private String nonSatsfctryReadngReasn;
	private String paymentAmount;
	private String perVariationAvgCnsumptn;
	private String perVariationPrevusReadng;
	private String satsfctryReadngReasn;
	private String searchKey;
	private String seqNo;
	private String siteVistGivenBy;
	private String siteVistGivenOn;
	private String siteVistRequrd;
	private String sugstdAuditAction;
	private String userId;
	private String variationPrevusReadng;
	private String zone;
	private String zroLocation;

	/**
	 * @return the annualAvgConsumption
	 */
	public String getAnnualAvgConsumption() {
		return annualAvgConsumption;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return the assignTo
	 */
	public String getAssignTo() {
		return assignTo;
	}

	/**
	 * @return the assignToFlg
	 */
	public String getAssignToFlg() {
		return assignToFlg;
	}

	/**
	 * @return the auditorName
	 */
	public String getAuditorName() {
		return auditorName;
	}

	/**
	 * @return the billAmt
	 */
	public String getBillAmt() {
		return billAmt;
	}

	public String getBillBasis() {
		return billBasis;
	}

	/**
	 * @return the billDt
	 */
	public String getBillDt() {
		return billDt;
	}

	/**
	 * @return the billGeneratedBy
	 */
	public String getBillGeneratedBy() {
		return billGeneratedBy;
	}

	public String getBillGenSource() {
		return billGenSource;
	}

	/**
	 * @return the billId
	 */
	public String getBillId() {
		return billId;
	}

	/**
	 * @return the billingPeriod
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the consumption
	 */
	public String getConsumption() {
		return consumption;
	}

	/**
	 * @return the consumptnVariatnReasn
	 */
	public String getConsumptnVariatnReasn() {
		return consumptnVariatnReasn;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createDttm
	 */
	public String getCreateDttm() {
		return createDttm;
	}

	/**
	 * @return the currMtrReadng
	 */
	public String getCurrMtrReadng() {
		return currMtrReadng;
	}

	/**
	 * @return the finalAuditAction
	 */
	public String getFinalAuditAction() {
		return finalAuditAction;
	}

	/**
	 * @return the finalAuditBy
	 */
	public String getFinalAuditBy() {
		return finalAuditBy;
	}

	/**
	 * @return the finalAuditOn
	 */
	public String getFinalAuditOn() {
		return finalAuditOn;
	}

	/**
	 * @return the finalAuditStatus
	 */
	public String getFinalAuditStatus() {
		return finalAuditStatus;
	}

	/**
	 * @return the imgAuditBy
	 */
	public String getImgAuditBy() {
		return imgAuditBy;
	}

	/**
	 * @return the imgAuditFailReason
	 */
	public String getImgAuditFailReason() {
		return imgAuditFailReason;
	}

	/**
	 * @return the imgAuditFlg
	 */
	public String getImgAuditFlg() {
		return imgAuditFlg;
	}

	/**
	 * @return the imgAuditOn
	 */
	public String getImgAuditOn() {
		return imgAuditOn;
	}

	/**
	 * @return the imgAuditRemrk
	 */
	public String getImgAuditRemrk() {
		return imgAuditRemrk;
	}

	/**
	 * @return the imgAuditStatus
	 */
	public String getImgAuditStatus() {
		return imgAuditStatus;
	}

	/**
	 * @return the imgLnk
	 */
	public String getImgLnk() {
		return imgLnk;
	}

	/**
	 * @return the isLocked
	 */
	public String getIsLocked() {
		return isLocked;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastAuditDate
	 */
	public String getLastAuditDate() {
		return lastAuditDate;
	}

	/**
	 * @return the lastAuditRmrk
	 */
	public String getLastAuditRmrk() {
		return lastAuditRmrk;
	}

	/**
	 * @return the lastAuditStatus
	 */
	public String getLastAuditStatus() {
		return lastAuditStatus;
	}

	/**
	 * @return the lastMtrReadng
	 */
	public String getLastMtrReadng() {
		return lastMtrReadng;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @return the lastUpdateDttm
	 */
	public String getLastUpdateDttm() {
		return lastUpdateDttm;
	}

	/**
	 * @return the markdForAudit
	 */
	public String getMarkdForAudit() {
		return markdForAudit;
	}

	/**
	 * @return the markdForAuditBy
	 */
	public String getMarkdForAuditBy() {
		return markdForAuditBy;
	}

	/**
	 * @return the markdForAuditOn
	 */
	public String getMarkdForAuditOn() {
		return markdForAuditOn;
	}

	/**
	 * @return the meterReadRemark
	 */
	public String getMeterReadRemark() {
		return meterReadRemark;
	}

	/**
	 * @return the mr
	 */
	public String getMr() {
		return mr;
	}

	/**
	 * @return the mtrNo
	 */
	public String getMtrNo() {
		return mtrNo;
	}

	public String getMtrRdrNameEmpId() {
		return mtrRdrNameEmpId;
	}

	public String getMtrRdStatus() {
		return mtrRdStatus;
	}

	/**
	 * @return the mtrRead
	 */
	public String getMtrRead() {
		return mtrRead;
	}

	/**
	 * @return the nonSatsfctryReadngReasn
	 */
	public String getNonSatsfctryReadngReasn() {
		return nonSatsfctryReadngReasn;
	}

	/**
	 * @return the paymentAmount
	 */
	public String getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @return the perVariationAvgCnsumptn
	 */
	public String getPerVariationAvgCnsumptn() {
		return perVariationAvgCnsumptn;
	}

	/**
	 * @return the perVariationPrevusReadng
	 */
	public String getPerVariationPrevusReadng() {
		return perVariationPrevusReadng;
	}

	/**
	 * @return the satsfctryReadngReasn
	 */
	public String getSatsfctryReadngReasn() {
		return satsfctryReadngReasn;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return the siteVistGivenBy
	 */
	public String getSiteVistGivenBy() {
		return siteVistGivenBy;
	}

	/**
	 * @return the siteVistGivenOn
	 */
	public String getSiteVistGivenOn() {
		return siteVistGivenOn;
	}

	/**
	 * @return the siteVistRequrd
	 */
	public String getSiteVistRequrd() {
		return siteVistRequrd;
	}

	/**
	 * @return the sugstdAuditAction
	 */
	public String getSugstdAuditAction() {
		return sugstdAuditAction;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the variationPrevusReadng
	 */
	public String getVariationPrevusReadng() {
		return variationPrevusReadng;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * @param annualAvgConsumption
	 *            the annualAvgConsumption to set
	 */
	public void setAnnualAvgConsumption(String annualAvgConsumption) {
		this.annualAvgConsumption = annualAvgConsumption;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @param assignTo
	 *            the assignTo to set
	 */
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	/**
	 * @param assignToFlg
	 *            the assignToFlg to set
	 */
	public void setAssignToFlg(String assignToFlg) {
		this.assignToFlg = assignToFlg;
	}

	/**
	 * @param auditorName
	 *            the auditorName to set
	 */
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 * @param billAmt
	 *            the billAmt to set
	 */
	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}

	public void setBillBasis(String billBasis) {
		this.billBasis = billBasis;
	}

	/**
	 * @param billDt
	 *            the billDt to set
	 */
	public void setBillDt(String billDt) {
		this.billDt = billDt;
	}

	/**
	 * @param billGeneratedBy
	 *            the billGeneratedBy to set
	 */
	public void setBillGeneratedBy(String billGeneratedBy) {
		this.billGeneratedBy = billGeneratedBy;
	}

	public void setBillGenSource(String billGenSource) {
		this.billGenSource = billGenSource;
	}

	/**
	 * @param billId
	 *            the billId to set
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * @param billingPeriod
	 *            the billingPeriod to set
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumption
	 *            the consumption to set
	 */
	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	/**
	 * @param consumptnVariatnReasn
	 *            the consumptnVariatnReasn to set
	 */
	public void setConsumptnVariatnReasn(String consumptnVariatnReasn) {
		this.consumptnVariatnReasn = consumptnVariatnReasn;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createDttm
	 *            the createDttm to set
	 */
	public void setCreateDttm(String createDttm) {
		this.createDttm = createDttm;
	}

	/**
	 * @param currMtrReadng
	 *            the currMtrReadng to set
	 */
	public void setCurrMtrReadng(String currMtrReadng) {
		this.currMtrReadng = currMtrReadng;
	}

	/**
	 * @param finalAuditAction
	 *            the finalAuditAction to set
	 */
	public void setFinalAuditAction(String finalAuditAction) {
		this.finalAuditAction = finalAuditAction;
	}

	/**
	 * @param finalAuditBy
	 *            the finalAuditBy to set
	 */
	public void setFinalAuditBy(String finalAuditBy) {
		this.finalAuditBy = finalAuditBy;
	}

	/**
	 * @param finalAuditOn
	 *            the finalAuditOn to set
	 */
	public void setFinalAuditOn(String finalAuditOn) {
		this.finalAuditOn = finalAuditOn;
	}

	/**
	 * @param finalAuditStatus
	 *            the finalAuditStatus to set
	 */
	public void setFinalAuditStatus(String finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}

	/**
	 * @param imgAuditBy
	 *            the imgAuditBy to set
	 */
	public void setImgAuditBy(String imgAuditBy) {
		this.imgAuditBy = imgAuditBy;
	}

	/**
	 * @param imgAuditFailReason
	 *            the imgAuditFailReason to set
	 */
	public void setImgAuditFailReason(String imgAuditFailReason) {
		this.imgAuditFailReason = imgAuditFailReason;
	}

	/**
	 * @param imgAuditFlg
	 *            the imgAuditFlg to set
	 */
	public void setImgAuditFlg(String imgAuditFlg) {
		this.imgAuditFlg = imgAuditFlg;
	}

	/**
	 * @param imgAuditOn
	 *            the imgAuditOn to set
	 */
	public void setImgAuditOn(String imgAuditOn) {
		this.imgAuditOn = imgAuditOn;
	}

	/**
	 * @param imgAuditRemrk
	 *            the imgAuditRemrk to set
	 */
	public void setImgAuditRemrk(String imgAuditRemrk) {
		this.imgAuditRemrk = imgAuditRemrk;
	}

	/**
	 * @param imgAuditStatus
	 *            the imgAuditStatus to set
	 */
	public void setImgAuditStatus(String imgAuditStatus) {
		this.imgAuditStatus = imgAuditStatus;
	}

	/**
	 * @param imgLnk
	 *            the imgLnk to set
	 */
	public void setImgLnk(String imgLnk) {
		this.imgLnk = imgLnk;
	}

	/**
	 * @param isLocked
	 *            the isLocked to set
	 */
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastAuditDate
	 *            the lastAuditDate to set
	 */
	public void setLastAuditDate(String lastAuditDate) {
		this.lastAuditDate = lastAuditDate;
	}

	/**
	 * @param lastAuditRmrk
	 *            the lastAuditRmrk to set
	 */
	public void setLastAuditRmrk(String lastAuditRmrk) {
		this.lastAuditRmrk = lastAuditRmrk;
	}

	/**
	 * @param lastAuditStatus
	 *            the lastAuditStatus to set
	 */
	public void setLastAuditStatus(String lastAuditStatus) {
		this.lastAuditStatus = lastAuditStatus;
	}

	/**
	 * @param lastMtrReadng
	 *            the lastMtrReadng to set
	 */
	public void setLastMtrReadng(String lastMtrReadng) {
		this.lastMtrReadng = lastMtrReadng;
	}

	/**
	 * @param lastUpdatedBy
	 *            the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @param lastUpdateDttm
	 *            the lastUpdateDttm to set
	 */
	public void setLastUpdateDttm(String lastUpdateDttm) {
		this.lastUpdateDttm = lastUpdateDttm;
	}

	/**
	 * @param markdForAudit
	 *            the markdForAudit to set
	 */
	public void setMarkdForAudit(String markdForAudit) {
		this.markdForAudit = markdForAudit;
	}

	/**
	 * @param markdForAuditBy
	 *            the markdForAuditBy to set
	 */
	public void setMarkdForAuditBy(String markdForAuditBy) {
		this.markdForAuditBy = markdForAuditBy;
	}

	/**
	 * @param markdForAuditOn
	 *            the markdForAuditOn to set
	 */
	public void setMarkdForAuditOn(String markdForAuditOn) {
		this.markdForAuditOn = markdForAuditOn;
	}

	/**
	 * @param meterReadRemark
	 *            the meterReadRemark to set
	 */
	public void setMeterReadRemark(String meterReadRemark) {
		this.meterReadRemark = meterReadRemark;
	}

	/**
	 * @param mr
	 *            the mr to set
	 */
	public void setMr(String mr) {
		this.mr = mr;
	}

	/**
	 * @param mtrNo
	 *            the mtrNo to set
	 */
	public void setMtrNo(String mtrNo) {
		this.mtrNo = mtrNo;
	}

	public void setMtrRdrNameEmpId(String mtrRdrNameEmpId) {
		this.mtrRdrNameEmpId = mtrRdrNameEmpId;
	}

	public void setMtrRdStatus(String mtrRdStatus) {
		this.mtrRdStatus = mtrRdStatus;
	}

	/**
	 * @param mtrRead
	 *            the mtrRead to set
	 */
	public void setMtrRead(String mtrRead) {
		this.mtrRead = mtrRead;
	}

	/**
	 * @param nonSatsfctryReadngReasn
	 *            the nonSatsfctryReadngReasn to set
	 */
	public void setNonSatsfctryReadngReasn(String nonSatsfctryReadngReasn) {
		this.nonSatsfctryReadngReasn = nonSatsfctryReadngReasn;
	}

	/**
	 * @param paymentAmount
	 *            the paymentAmount to set
	 */
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @param perVariationAvgCnsumptn
	 *            the perVariationAvgCnsumptn to set
	 */
	public void setPerVariationAvgCnsumptn(String perVariationAvgCnsumptn) {
		this.perVariationAvgCnsumptn = perVariationAvgCnsumptn;
	}

	/**
	 * @param perVariationPrevusReadng
	 *            the perVariationPrevusReadng to set
	 */
	public void setPerVariationPrevusReadng(String perVariationPrevusReadng) {
		this.perVariationPrevusReadng = perVariationPrevusReadng;
	}

	/**
	 * @param satsfctryReadngReasn
	 *            the satsfctryReadngReasn to set
	 */
	public void setSatsfctryReadngReasn(String satsfctryReadngReasn) {
		this.satsfctryReadngReasn = satsfctryReadngReasn;
	}

	/**
	 * @param searchKey
	 *            the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @param siteVistGivenBy
	 *            the siteVistGivenBy to set
	 */
	public void setSiteVistGivenBy(String siteVistGivenBy) {
		this.siteVistGivenBy = siteVistGivenBy;
	}

	/**
	 * @param siteVistGivenOn
	 *            the siteVistGivenOn to set
	 */
	public void setSiteVistGivenOn(String siteVistGivenOn) {
		this.siteVistGivenOn = siteVistGivenOn;
	}

	/**
	 * @param siteVistRequrd
	 *            the siteVistRequrd to set
	 */
	public void setSiteVistRequrd(String siteVistRequrd) {
		this.siteVistRequrd = siteVistRequrd;
	}

	/**
	 * @param sugstdAuditAction
	 *            the sugstdAuditAction to set
	 */
	public void setSugstdAuditAction(String sugstdAuditAction) {
		this.sugstdAuditAction = sugstdAuditAction;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param variationPrevusReadng
	 *            the variationPrevusReadng to set
	 */
	public void setVariationPrevusReadng(String variationPrevusReadng) {
		this.variationPrevusReadng = variationPrevusReadng;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
	 * @param zroLocation
	 *            the zroLocation to set
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}

}
