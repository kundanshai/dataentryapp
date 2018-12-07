/************************************************************************************************************
 * @(#) PremiseDetails.java   10 Oct 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for Premise Details.
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @Date 10-10-2012
 * 
 */
public class PremiseDetails {
	private String premiseID;
	private String unocDwelUnits;
	private String ocuDwelUnits;
	private String totlDewlUnits;
	private String noOfFloor;
	private String noOfFuncSitesBedsRooms;

	/**
	 * @return the noOfFloor
	 */
	public String getNoOfFloor() {
		return noOfFloor;
	}

	/**
	 * @return the noOfFuncSitesBedsRooms
	 */
	public String getNoOfFuncSitesBedsRooms() {
		return noOfFuncSitesBedsRooms;
	}

	/**
	 * @return the ocuDwelUnits
	 */
	public String getOcuDwelUnits() {
		return ocuDwelUnits;
	}

	/**
	 * @return the premiseID
	 */
	public String getPremiseID() {
		return premiseID;
	}

	/**
	 * @return the totlDewlUnits
	 */
	public String getTotlDewlUnits() {
		return totlDewlUnits;
	}

	/**
	 * @return the unocDwelUnits
	 */
	public String getUnocDwelUnits() {
		return unocDwelUnits;
	}

	/**
	 * @param noOfFloor
	 *            the noOfFloor to set
	 */
	public void setNoOfFloor(String noOfFloor) {
		this.noOfFloor = noOfFloor;
	}

	/**
	 * @param noOfFuncSitesBedsRooms
	 *            the noOfFuncSitesBedsRooms to set
	 */
	public void setNoOfFuncSitesBedsRooms(String noOfFuncSitesBedsRooms) {
		this.noOfFuncSitesBedsRooms = noOfFuncSitesBedsRooms;
	}

	/**
	 * @param ocuDwelUnits
	 *            the ocuDwelUnits to set
	 */
	public void setOcuDwelUnits(String ocuDwelUnits) {
		this.ocuDwelUnits = ocuDwelUnits;
	}

	/**
	 * @param premiseID
	 *            the premiseID to set
	 */
	public void setPremiseID(String premiseID) {
		this.premiseID = premiseID;
	}

	/**
	 * @param totlDewlUnits
	 *            the totlDewlUnits to set
	 */
	public void setTotlDewlUnits(String totlDewlUnits) {
		this.totlDewlUnits = totlDewlUnits;
	}

	/**
	 * @param unocDwelUnits
	 *            the unocDwelUnits to set
	 */
	public void setUnocDwelUnits(String unocDwelUnits) {
		this.unocDwelUnits = unocDwelUnits;
	}

}
