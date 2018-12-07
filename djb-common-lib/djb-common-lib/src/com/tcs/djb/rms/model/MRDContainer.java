/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * <code>MRDContainer</code> contains all the Details of an MRD for downloading
 * meter reading to the Database. It contains <code>consumer</code>,
 * <code>mrKey</code>, <code>billRound</code>, <code>zoneNo</code>,
 * <code>mrNo</code> and <code>areaNo</code>.
 * </p>
 * 
 * @see ConsumerDetail
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class MRDContainer {
	/**
	 * area No(Area Code).
	 */
	private String areaNo;
	/**
	 * bill Round Id.
	 */
	private String billRound;
	/**
	 * Consumer Detail.
	 */
	private ConsumerDetail[] consumer;
	/**
	 * MR Key.
	 */
	private String mrKey;
	/**
	 * MR No.
	 */
	private String mrNo;
	/**
	 * zone No(Zone Code).
	 */
	private String zoneNo;

	/**
	 * @return the areaNo
	 */
	public String getAreaNo() {
		return areaNo;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the consumer
	 */
	public ConsumerDetail[] getConsumer() {
		return consumer;
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
	 * @return the zoneNo
	 */
	public String getZoneNo() {
		return zoneNo;
	}

	/**
	 * @param areaNo
	 *            the areaNo to set
	 */
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumer
	 *            the consumer to set
	 */
	public void setConsumer(ConsumerDetail[] consumer) {
		this.consumer = consumer;
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
	 * @param zoneNo
	 *            the zoneNo to set
	 */
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
}
