/************************************************************************************************************
 * @(#) DemandTransferDetails.java   08 May 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * DemandTransferDetails class contains all the detail fields forDemand Transfer
 * screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 08-05-2013
 * 
 */
public class DemandTransferDetails {
	/**
	 * Address of the consumer.
	 */
	private String address;
	/**
	 * Category of the consumer.
	 */
	private String category;
	/**
	 * Account Number of the consumer.
	 */
	private String kno;
	/**
	 * Name of the consumer.
	 */
	private String name;

	/**
	 * New MRKey of the consumer.
	 */
	private String newMRKey;
	/**
	 * New Service Cycle Route Sequence of the consumer.This is the sequence in
	 * which the meter reader used to visit the house of the consumer.
	 */
	private String newServiceCycleRouteSequence;
	/**
	 * Old MRKey of the consumer.
	 */
	private String oldMRKey;

	/**
	 * old Service Cycle Route Sequence of the consumer.This is the sequence in
	 * which the meter reader used to visit the house of the consumer.
	 */
	private String oldServiceCycleRouteSequence;

	/**
	 * Remarks of the Processing .
	 */
	private String remarks;
	/**
	 * Serial No of the records.
	 */
	private String slNo;
	/**
	 * Status of the Demand Transfer.
	 */
	private String status;

	private String userId;

	/**
	 * Water Connection Number of the consumer.This for Old consumer.
	 */
	private String wcNo;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the newMRKey
	 */
	public String getNewMRKey() {
		return newMRKey;
	}

	/**
	 * @return the newServiceCycleRouteSequence
	 */
	public String getNewServiceCycleRouteSequence() {
		return newServiceCycleRouteSequence;
	}

	/**
	 * @return the oldMRKey
	 */
	public String getOldMRKey() {
		return oldMRKey;
	}

	/**
	 * @return the oldServiceCycleRouteSequence
	 */
	public String getOldServiceCycleRouteSequence() {
		return oldServiceCycleRouteSequence;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param newMRKey
	 *            the newMRKey to set
	 */
	public void setNewMRKey(String newMRKey) {
		this.newMRKey = newMRKey;
	}

	/**
	 * @param newServiceCycleRouteSequence
	 *            the newServiceCycleRouteSequence to set
	 */
	public void setNewServiceCycleRouteSequence(
			String newServiceCycleRouteSequence) {
		this.newServiceCycleRouteSequence = newServiceCycleRouteSequence;
	}

	/**
	 * @param oldMRKey
	 *            the oldMRKey to set
	 */
	public void setOldMRKey(String oldMRKey) {
		this.oldMRKey = oldMRKey;
	}

	/**
	 * @param oldServiceCycleRouteSequence
	 *            the oldServiceCycleRouteSequence to set
	 */
	public void setOldServiceCycleRouteSequence(
			String oldServiceCycleRouteSequence) {
		this.oldServiceCycleRouteSequence = oldServiceCycleRouteSequence;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}
}
