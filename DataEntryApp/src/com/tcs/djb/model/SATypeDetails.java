/************************************************************************************************************
 * @(#) SATypeDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * SA(Service Agreement) Type details for MeterReplacement Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class SATypeDetails {
	private String saTypeCode;
	private String saTypeDesc;

	public SATypeDetails(String saTypeCode, String saTypeDesc) {
		this.saTypeCode = saTypeCode;
		this.saTypeDesc = saTypeDesc;
	}

	/**
	 * @return the saTypeCode
	 */
	public String getSaTypeCode() {
		return saTypeCode;
	}

	/**
	 * @return the saTypeDesc
	 */
	public String getSaTypeDesc() {
		return saTypeDesc;
	}

	/**
	 * @param saTypeCode
	 *            the saTypeCode to set
	 */
	public void setSaTypeCode(String saTypeCode) {
		this.saTypeCode = saTypeCode;
	}

	/**
	 * @param saTypeDesc
	 *            the saTypeDesc to set
	 */
	public void setSaTypeDesc(String saTypeDesc) {
		this.saTypeDesc = saTypeDesc;
	}
}
