/************************************************************************************************************
 * @(#) OnlineBatchBillingDetails.java   01 Jul 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Model container class for Online Batch Billing Details. It contains all the
 * batch billing details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 01-07-2013
 */
public class OnlineBatchBillingDetails {
	/**
	 * Area Code/Description.
	 */
	private String area;
	/**
	 * Id after bill is generated.
	 */
	private String billId;
	/**
	 * Billing Error Message.
	 */
	private String billingError;
	/**
	 * Billing Remarks.
	 */
	private String billingRemarks;
	/**
	 * bill Round.
	 */
	private String billRound;

	/**
	 * Billing completed On Time Stamp.
	 */
	private String completedOn;

	/**
	 * Name of the consumer.
	 */
	private String consumerName;

	/**
	 * Billing Initiated By.
	 */
	private String initiatedBy;
	/**
	 * Billing Initiated On Time Stamp.
	 */
	private String initiatedOn;
	/**
	 * 10 digits Account ID of the consumer.
	 */
	private String kno;

	/**
	 * MRD code.
	 */
	private String mrKey;

	/**
	 * Meter Reader No.
	 */
	private String mrNo;

	/**
	 * MRD Process Counter.
	 */
	private String processCounter;
	/**
	 * records Completed status.
	 */
	private String recordsCompleted;
	/**
	 * records In Process .
	 */
	private String recordsInProcess;
	/**
	 * Service Cycle route Sequence.
	 */
	private String serviceSeq;
	/**
	 * Serial No.
	 */
	private String slNo;
	/**
	 * Billing Started On Time Stamp.
	 */
	private String startedOn;
	/**
	 * CONS_PRE_BILL_STAT_ID in the CM_CONS_PRE_BILL_PROC table.
	 */
	private String statusCode;
	/**
	 * Description of CONS_PRE_BILL_STAT_ID in the CONS_PRE_BILL_STAT_LOOKUP
	 * table.
	 */
	private String statusDesc;
	/**
	 * Total No of Records.
	 */
	private String totalRecords;

	/**
	 * Total time taken to complete the batch.
	 */
	private String totalTimeTaken;
	/**
	 * Zone code of the MRKEY.
	 */
	private String zone;
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @return the billId
	 */
	public String getBillId() {
		return billId;
	}

	/**
	 * @return the billingError
	 */
	public String getBillingError() {
		return billingError;
	}

	/**
	 * @return the billingRemarks
	 */
	public String getBillingRemarks() {
		return billingRemarks;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the completedOn
	 */
	public String getCompletedOn() {
		return completedOn;
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @return the initiatedBy
	 */
	public String getInitiatedBy() {
		return initiatedBy;
	}

	/**
	 * @return the initiatedOn
	 */
	public String getInitiatedOn() {
		return initiatedOn;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the mrKey
	 */
	public String getMrKey() {
		return mrKey;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return the processCounter
	 */
	public String getProcessCounter() {
		return processCounter;
	}

	/**
	 * @return the recordsCompleted
	 */
	public String getRecordsCompleted() {
		return recordsCompleted;
	}

	/**
	 * @return the recordsInProcess
	 */
	public String getRecordsInProcess() {
		return recordsInProcess;
	}

	/**
	 * @return the serviceSeq
	 */
	public String getServiceSeq() {
		return serviceSeq;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @return the startedOn
	 */
	public String getStartedOn() {
		return startedOn;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @return the totalRecords
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return the totalTimeTaken
	 */
	public String getTotalTimeTaken() {
		return totalTimeTaken;
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
	 * @param billId the billId to set
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * @param billingError
	 *            the billingError to set
	 */
	public void setBillingError(String billingError) {
		this.billingError = billingError;
	}

	/**
	 * @param billingRemarks
	 *            the billingRemarks to set
	 */
	public void setBillingRemarks(String billingRemarks) {
		this.billingRemarks = billingRemarks;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param completedOn
	 *            the completedOn to set
	 */
	public void setCompletedOn(String completedOn) {
		this.completedOn = completedOn;
	}

	/**
	 * @param consumerName
	 *            the consumerName to set
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @param initiatedBy
	 *            the initiatedBy to set
	 */
	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}

	/**
	 * @param initiatedOn
	 *            the initiatedOn to set
	 */
	public void setInitiatedOn(String initiatedOn) {
		this.initiatedOn = initiatedOn;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param mrKey
	 *            the mrKey to set
	 */
	public void setMrKey(String mrKey) {
		this.mrKey = mrKey;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param processCounter
	 *            the processCounter to set
	 */
	public void setProcessCounter(String processCounter) {
		this.processCounter = processCounter;
	}

	/**
	 * @param recordsCompleted
	 *            the recordsCompleted to set
	 */
	public void setRecordsCompleted(String recordsCompleted) {
		this.recordsCompleted = recordsCompleted;
	}

	/**
	 * @param recordsInProcess
	 *            the recordsInProcess to set
	 */
	public void setRecordsInProcess(String recordsInProcess) {
		this.recordsInProcess = recordsInProcess;
	}

	/**
	 * @param serviceSeq
	 *            the serviceSeq to set
	 */
	public void setServiceSeq(String serviceSeq) {
		this.serviceSeq = serviceSeq;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	/**
	 * @param startedOn
	 *            the startedOn to set
	 */
	public void setStartedOn(String startedOn) {
		this.startedOn = startedOn;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param statusDesc
	 *            the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param totalTimeTaken
	 *            the totalTimeTaken to set
	 */
	public void setTotalTimeTaken(String totalTimeTaken) {
		this.totalTimeTaken = totalTimeTaken;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
