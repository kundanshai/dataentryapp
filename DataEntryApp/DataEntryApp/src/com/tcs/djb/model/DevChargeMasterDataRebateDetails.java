/************************************************************************************************************
 * @(#) DevChargeMasterDataRebateDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.Date;

/**
 * <p>
 * Module Class for Dev Charge Master Data Screen's Rebate Details
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 */
public class DevChargeMasterDataRebateDetails {

	private String rebateType;

	private String rebatePercentage;

	private String rebateFlatRate;

	private String rebateStartDt;

	private String rebateEndDt;

	private String rebateDocProof;

	/**
	 * @return the rebateType
	 */
	public String getRebateType() {
		return rebateType;
	}

	/**
	 * @param rebateType
	 *            the rebateType to set
	 */
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	/**
	 * @return the rebatePercentage
	 */
	public String getRebatePercentage() {
		return rebatePercentage;
	}

	/**
	 * @param rebatePercentage
	 *            the rebatePercentage to set
	 */
	public void setRebatePercentage(String rebatePercentage) {
		this.rebatePercentage = rebatePercentage;
	}

	/**
	 * @return the rebateFlatRate
	 */
	public String getRebateFlatRate() {
		return rebateFlatRate;
	}

	/**
	 * @param rebateFlatRate
	 *            the rebateFlatRate to set
	 */
	public void setRebateFlatRate(String rebateFlatRate) {
		this.rebateFlatRate = rebateFlatRate;
	}

	/**
	 * @return the rebateStartDt
	 */
	public String getRebateStartDt() {
		return rebateStartDt;
	}

	/**
	 * @param rebateStartDt
	 *            the rebateStartDt to set
	 */
	public void setRebateStartDt(String rebateStartDt) {
		this.rebateStartDt = rebateStartDt;
	}

	/**
	 * @return the rebateEndDt
	 */
	public String getRebateEndDt() {
		return rebateEndDt;
	}

	/**
	 * @param rebateEndDt
	 *            the rebateEndDt to set
	 */
	public void setRebateEndDt(String rebateEndDt) {
		this.rebateEndDt = rebateEndDt;
	}

	/**
	 * @return the rebateDocProof
	 */
	public String getRebateDocProof() {
		return rebateDocProof;
	}

	/**
	 * @param rebateDocProof
	 *            the rebateDocProof to set
	 */
	public void setRebateDocProof(String rebateDocProof) {
		this.rebateDocProof = rebateDocProof;
	}

}
