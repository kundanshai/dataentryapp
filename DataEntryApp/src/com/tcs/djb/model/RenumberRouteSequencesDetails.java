/************************************************************************************************************
 * @(#) RenumberRouteSequencesDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * RenumberRouteSequencesDetails to store the renumber route sequence details of
 * a consumer.
 * </p>
 * 
 * @author Reshma Srivastava (Tata Consultancy Services)
 * 
 */
public class RenumberRouteSequencesDetails {
	
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
	 * Remarks of the Processing .
	 */
	private String remarks;
	/**
	 * SP ID of records.
	 */
	private String spID;

	/**
	 * Status of the Renumber Route Sequence.
	 */
	private String status;
	/**
	 * Serial No of the records.
	 */
	private String slNo;

	/**
	 * Water Connection Number of the consumer.This for Old consumer.
	 */
	private String wcNo;
	
	private String oldRouteSequences;
	
	private String newRouteSequences;

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
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @return the oldRouteSequences
	 */
	public String getOldRouteSequences() {
		return oldRouteSequences;
	}

	/**
	 * @return the newRouteSequences
	 */
	public String getNewRouteSequences() {
		return newRouteSequences;
	}

	/**
	 * @return the spID
	 */
	public String getSpID() {
		return spID;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @param slNo the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	/**
	 * @param spID the spID to set
	 */
	public void setSpID(String spID) {
		this.spID = spID;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param kno the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param wcNo the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}

	/**
	 * @param oldRouteSequences the oldRouteSequences to set
	 */
	public void setOldRouteSequences(String oldRouteSequences) {
		this.oldRouteSequences = oldRouteSequences;
	}

	/**
	 * @param newRouteSequences the newRouteSequences to set
	 */
	public void setNewRouteSequences(String newRouteSequences) {
		this.newRouteSequences = newRouteSequences;
	}

	
	
}
