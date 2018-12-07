/************************************************************************************************************
 * @(#) MeterTypeLookup.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for Meter Type Lookup Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class MeterTypeLookup {

	private String meterTypeCode;
	private String meterTypeDesc;

	public MeterTypeLookup() {
	}

	/**
	 * @param meterTypeCode
	 * @param meterTypeDesc
	 */
	public MeterTypeLookup(String meterTypeCode, String meterTypeDesc) {
		this.meterTypeCode = meterTypeCode;
		this.meterTypeDesc = meterTypeDesc;
	}

	/**
	 * @return the meterTypeCode
	 */
	public String getMeterTypeCode() {
		return meterTypeCode;
	}

	/**
	 * @return the meterTypeDesc
	 */
	public String getMeterTypeDesc() {
		return meterTypeDesc;
	}

	/**
	 * @param meterTypeCode
	 */
	public void setMeterTypeCode(String meterTypeCode) {
		this.meterTypeCode = meterTypeCode;
	}

	/**
	 * @param meterTypeDesc
	 */
	public void setMeterTypeDesc(String meterTypeDesc) {
		this.meterTypeDesc = meterTypeDesc;
	}
}
