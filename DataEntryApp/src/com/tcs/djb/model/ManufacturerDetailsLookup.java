/************************************************************************************************************
 * @(#) ManufacturerDetailsLookup.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for Capturing Manufacturer Details
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 */
public class ManufacturerDetailsLookup {
	private String manufacturerCode;
	private String manufacturerDesc;

	public ManufacturerDetailsLookup() {

	}

	/**
	 * @param manufacturerCode
	 * @param manufacturerDesc
	 */
	public ManufacturerDetailsLookup(String manufacturerCode,
			String manufacturerDesc) {
		this.manufacturerCode = manufacturerCode;
		this.manufacturerDesc = manufacturerDesc;
	}

	/**
	 * @return the manufacturerCode
	 */
	public String getManufacturerCode() {
		return manufacturerCode;
	}

	/**
	 * @return the manufacturerDesc
	 */
	public String getManufacturerDesc() {
		return manufacturerDesc;
	}

	/**
	 * @param manufacturerCode
	 *            the manufacturerCode to set
	 */
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	/**
	 * @param manufacturerDesc
	 *            the manufacturerDesc to set
	 */
	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}
}
