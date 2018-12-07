/************************************************************************************************************
 * @(#) MeterReadType.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains meter read read type code i.e. the Meter Read status code
 * corresponding to read type and its description.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * 
 */
public class MeterReadType {
	/**
	 * Read Type Code.
	 */
	private String readTypeCode;
	/**
	 * Read Type Description.
	 */
	private String readTypeDesc;

	/**
	 * @param readTypeDesc
	 * @param readTypeCode
	 */
	public MeterReadType(String readTypeDesc, String readTypeCode) {
		this.readTypeDesc = readTypeDesc;
		this.readTypeCode = readTypeCode;
	}

	/**
	 * @return the readTypeCode
	 */
	public String getReadTypeCode() {
		return readTypeCode;
	}

	/**
	 * @return the readTypeDesc
	 */
	public String getReadTypeDesc() {
		return readTypeDesc;
	}

	/**
	 * @param readTypeCode
	 *            the readTypeCode to set
	 */
	public void setReadTypeCode(String readTypeCode) {
		this.readTypeCode = readTypeCode;
	}

	/**
	 * @param readTypeDesc
	 *            the readTypeDesc to set
	 */
	public void setReadTypeDesc(String readTypeDesc) {
		this.readTypeDesc = readTypeDesc;
	}
}
