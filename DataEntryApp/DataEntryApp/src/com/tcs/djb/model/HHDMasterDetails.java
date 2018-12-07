/************************************************************************************************************
 * @(#) HHDMasterDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * HHD Master Details for HHD Master Screen.
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 * 
 */
public class HHDMasterDetails {

	/**
	 * hhdId for HHD
	 */
	private String hhdId;
	/**
	 * blocked for HHD
	 */
	private String blocked;
	/**
	 * simId for HHD
	 */
	private String simId;
	/**
	 * simNo for HHD
	 */
	private String simNo;
	/**
	 * Serial No.
	 */
	private String slNo;

	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @param hhdId
	 *            the hhdId to set
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @return the blocked
	 */
	public String getBlocked() {
		return blocked;
	}

	/**
	 * @param blocked
	 *            the blocked to set
	 */
	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	/**
	 * @return the simId
	 */
	public String getSimId() {
		return simId;
	}

	/**
	 * @param simId
	 *            the simId to set
	 */
	public void setSimId(String simId) {
		this.simId = simId;
	}

	/**
	 * @return the simNo
	 */
	public String getSimNo() {
		return simNo;
	}

	/**
	 * @param simNo
	 *            the simNo to set
	 */
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

}
