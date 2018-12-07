/************************************************************************************************************
 * @(#) ModelDetailsLookup.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for Meter Details Lookup Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class ModelDetailsLookup {

	private String modelCode;

	private String modelDesc;

	/**
	 * 
	 */
	public ModelDetailsLookup() {
		// TODO Auto-generated constructor stub
	}
	public ModelDetailsLookup(String modelCode, String modelDesc) {
		this.modelCode = modelCode;
		this.modelDesc = modelDesc;
	}

	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @return the modelDesc
	 */
	public String getModelDesc() {
		return modelDesc;
	}

	/**
	 * @param modelCode
	 *            the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * @param modelDesc
	 *            the modelDesc to set
	 */
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

}
