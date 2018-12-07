/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * ConsumerDetail to store the details of a consumer required for meter read
 * down load by HHD.
 * </p>
 * 
 * @see CurrentMeterReadDetails
 * @see PreviousMeterReadDetails
 * @author Matiur Rahman(Tata Consultancy Services).
 * @author Mrityunjoy Misra(HHD-34)21-11-2013
 * 
 * @history 25-08-2014 Matiur Rahman Added {@link #lastUpdatedBy},{@link #mrkey}
 *          as per JTrac DJB-2024 and sorted the method
 */
public class ConsumerDetail {
	/**
	 * bill Round Id.
	 */
	private String billRound;
	/**
	 * category of the Consumer(CAT I, CAT IA,CAT II,CAT IIA)
	 */
	private String category;
	/**
	 * Address of the Consumer.
	 */
	private String consumerAddress;
	/**
	 * Status of the Consumer.
	 */
	private String consumerStatus;
	/**
	 * Type of the Consumer(60=Record Entered,20=Disconnected,30=Meter
	 * Installed,40=Reopening,50=Change of Category).
	 */
	private String consumerType;

	/**
	 * Current Meter Read Details.
	 */
	private CurrentMeterReadDetails currentMeterReadDetails;

	/**
	 * Consumer No(Account ID).
	 */
	private String kno;
	/**
	 * Last Bill Generation Date.
	 */
	private String lastBillGenerationDate;
	/**
	 * meter Read last updated By.
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services).
	 * @since 25-08-2014
	 */
	private String lastUpdatedBy;
	/**
	 * Meter Number(Meter Badge Number).
	 */
	private String meterNumber;

	/**
	 * The MRKey
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services).
	 * @since 25-08-2014
	 */
	private String mrkey;

	/**
	 * Name of the Consumer.
	 */
	private String name;

	/**
	 * Previous Meter Read Details.
	 */
	private PreviousMeterReadDetails previousMeterReadDetails;
	/**
	 * Service Route Sequence Number.
	 */
	private String routeSequenceNumber;
	/**
	 * Water Connection Number.
	 */
	private String wcno;
	/*
	 * HHD-34 email and mobile
	 */
	private String xmobileNoofConsumer;

	private String zemailofConsumer;

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the consumerAddress
	 */
	public String getConsumerAddress() {
		return consumerAddress;
	}

	/**
	 * @return the consumerStatus
	 */
	public String getConsumerStatus() {
		return consumerStatus;
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * @return the currentMeterReadDetails
	 */
	public CurrentMeterReadDetails getCurrentMeterReadDetails() {
		return currentMeterReadDetails;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastBillGenerationDate
	 */
	public String getLastBillGenerationDate() {
		return lastBillGenerationDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @return the meterNumber
	 */
	public String getMeterNumber() {
		return meterNumber;
	}

	public String getMrkey() {
		return mrkey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the previousMeterReadDetails
	 */
	public PreviousMeterReadDetails getPreviousMeterReadDetails() {
		return previousMeterReadDetails;
	}

	/**
	 * @return the routeSequenceNumber
	 */
	public String getRouteSequenceNumber() {
		return routeSequenceNumber;
	}

	/**
	 * @return the wcno
	 */
	public String getWcno() {
		return wcno;
	}

	public String getXmobileNoofConsumer() {
		return xmobileNoofConsumer;
	}

	public String getZemailofConsumer() {
		return zemailofConsumer;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param consumerAddress
	 *            the consumerAddress to set
	 */
	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	/**
	 * @param consumerStatus
	 *            the consumerStatus to set
	 */
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	/**
	 * @param consumerType
	 *            the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * @param currentMeterReadDetails
	 *            the currentMeterReadDetails to set
	 */
	public void setCurrentMeterReadDetails(
			CurrentMeterReadDetails currentMeterReadDetails) {
		this.currentMeterReadDetails = currentMeterReadDetails;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastBillGenerationDate
	 *            the lastBillGenerationDate to set
	 */
	public void setLastBillGenerationDate(String lastBillGenerationDate) {
		this.lastBillGenerationDate = lastBillGenerationDate;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @param meterNumber
	 *            the meterNumber to set
	 */
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public void setMrkey(String mrkey) {
		this.mrkey = mrkey;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param previousMeterReadDetails
	 *            the previousMeterReadDetails to set
	 */
	public void setPreviousMeterReadDetails(
			PreviousMeterReadDetails previousMeterReadDetails) {
		this.previousMeterReadDetails = previousMeterReadDetails;
	}

	/**
	 * @param routeSequenceNumber
	 *            the routeSequenceNumber to set
	 */
	public void setRouteSequenceNumber(String routeSequenceNumber) {
		this.routeSequenceNumber = routeSequenceNumber;
	}

	/**
	 * @param wcno
	 *            the wcno to set
	 */
	public void setWcno(String wcno) {
		this.wcno = wcno;
	}

	public void setXmobileNoofConsumer(String xmobileNoofConsumer) {
		this.xmobileNoofConsumer = xmobileNoofConsumer;
	}

	public void setZemailofConsumer(String zemailofConsumer) {
		this.zemailofConsumer = zemailofConsumer;
	}

}
