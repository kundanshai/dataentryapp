/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * MeterReadDowloadDetails to store Meter Read Download Details required to
 * download data via web service call.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class MeterReadDowloadDetails {
	/**
	 * Area No.
	 */
	private String areaNo;
	/**
	 * Bill Round Id.
	 */
	private String billRoundId;
	/**
	 * MR Key
	 */
	private String mrKey;

	/**
	 * MR No.
	 */
	private String mrNo;

	/**
	 * Zone Code.
	 */
	private String zone;

	/**
	 * @return the areaNo
	 */
	public String getAreaNo() {
		return areaNo;
	}

	/**
	 * @return the billRoundId
	 */
	public String getBillRoundId() {
		return billRoundId;
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
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param areaNo
	 *            the areaNo to set
	 */
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * @param billRoundId
	 *            the billRoundId to set
	 */
	public void setBillRoundId(String billRoundId) {
		this.billRoundId = billRoundId;
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
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
