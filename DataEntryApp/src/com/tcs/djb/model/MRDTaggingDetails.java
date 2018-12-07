/************************************************************************************************************
 * @(#) MRDTaggingDetails.java   26 Sep 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains details of Meter Reader and MRD tagging Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @version 1.0
 * @since 26-09-2013
 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871 added
 *          zone,mr,area,type
 */
public class MRDTaggingDetails {
	private String area;
	private String createdBy;
	private String createdOn;
	private String effectiveFrom;
	private String effectiveTo;
	private MeterReaderDetails meterReaderDetails;
	private String mr;
	private String mrkey;
	private String type;
	private String updatedBy;
	private String updatedOn;
	private String zone;

	public MRDTaggingDetails() {
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
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

	/**
	 * @return the effectiveFrom
	 */
	public String getEffectiveFrom() {
		return effectiveFrom;
	}

	/**
	 * @return the effectiveTo
	 */
	public String getEffectiveTo() {
		return effectiveTo;
	}

	/**
	 * @return the meterReaderDetails
	 */
	public MeterReaderDetails getMeterReaderDetails() {
		return meterReaderDetails;
	}

	/**
	 * @return the mr
	 */
	public String getMr() {
		return mr;
	}

	/**
	 * @return the mrkey
	 */
	public String getMrkey() {
		return mrkey;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
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

	/**
	 * @param effectiveFrom
	 *            the effectiveFrom to set
	 */
	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	/**
	 * @param effectiveTo
	 *            the effectiveTo to set
	 */
	public void setEffectiveTo(String effectiveTo) {
		this.effectiveTo = effectiveTo;
	}

	/**
	 * @param meterReaderDetails
	 *            the meterReaderDetails to set
	 */
	public void setMeterReaderDetails(MeterReaderDetails meterReaderDetails) {
		this.meterReaderDetails = meterReaderDetails;
	}

	/**
	 * @param mr
	 *            the mr to set
	 */
	public void setMr(String mr) {
		this.mr = mr;
	}

	/**
	 * @param mrkey
	 *            the mrkey to set
	 */
	public void setMrkey(String mrkey) {
		this.mrkey = mrkey;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
