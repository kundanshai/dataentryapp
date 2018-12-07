/************************************************************************************************************
 * @(#) SADetails.java   11 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * SA(Service Agreement) details for MeterReplacement Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-03-2013
 * 
 * 
 */
public class SADetails {

	/**
	 * Service Agreement Start Date.
	 */
	private String saStartDate;
	/**
	 * Service Agreement Type.
	 */
	private String saTypeCode;

	/**
	 * Default Constructor.
	 */
	public SADetails() {
	}

	/**
	 * @return the saStartDate
	 */
	public String getSaStartDate() {
		return saStartDate;
	}

	/**
	 * @return the saTypeCode
	 */
	public String getSaTypeCode() {
		return saTypeCode;
	}

	/**
	 * @param saStartDate
	 *            the saStartDate to set
	 */
	public void setSaStartDate(String saStartDate) {
		this.saStartDate = saStartDate;
	}

	/**
	 * @param saTypeCode
	 *            the saTypeCode to set
	 */
	public void setSaTypeCode(String saTypeCode) {
		this.saTypeCode = saTypeCode;
	}
}
