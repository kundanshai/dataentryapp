/************************************************************************************************************
 * @(#) DevChargeMasterDataRateDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.Date;

/**
 * <p>
 * Module Class for Dev Charge Master Data Screen's Rate Details
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 * 
 */
public class DevChargeMasterDataRateDetails {

	private String applicableRate;

	private String rebateEligibility;

	private String rateStartDt;

	private String rateEndDt;

	private String rateDocProof;

	private String rateMinPlotArea;

	private String rateMaxPlotArea;

	private String rateTypeCode;

	private String rateInterestEligibity;

	private String customerClassCode;

	/**
	 * @return the rateInterestEligibity
	 */
	public String getRateInterestEligibity() {
		return rateInterestEligibity;
	}

	/**
	 * @param rateInterestEligibity
	 *            the rateInterestEligibity to set
	 */
	public void setRateInterestEligibity(String rateInterestEligibity) {
		this.rateInterestEligibity = rateInterestEligibity;
	}

	/**
	 * @return the rateMinPlotArea
	 */
	public String getRateMinPlotArea() {
		return rateMinPlotArea;
	}

	/**
	 * @param rateMinPlotArea
	 *            the rateMinPlotArea to set
	 */
	public void setRateMinPlotArea(String rateMinPlotArea) {
		this.rateMinPlotArea = rateMinPlotArea;
	}

	/**
	 * @return the rateMaxPlotArea
	 */
	public String getRateMaxPlotArea() {
		return rateMaxPlotArea;
	}

	/**
	 * @param rateMaxPlotArea
	 *            the rateMaxPlotArea to set
	 */
	public void setRateMaxPlotArea(String rateMaxPlotArea) {
		this.rateMaxPlotArea = rateMaxPlotArea;
	}

	/**
	 * @return the rateTypeCode
	 */
	public String getRateTypeCode() {
		return rateTypeCode;
	}

	/**
	 * @param rateTypeCode
	 *            the rateTypeCode to set
	 */
	public void setRateTypeCode(String rateTypeCode) {
		this.rateTypeCode = rateTypeCode;
	}

	/**
	 * @return the customerClassCode
	 */
	public String getCustomerClassCode() {
		return customerClassCode;
	}

	/**
	 * @param customerClassCode
	 *            the customerClassCode to set
	 */
	public void setCustomerClassCode(String customerClassCode) {
		this.customerClassCode = customerClassCode;
	}

	/**
	 * @return the applicableRate
	 */
	public String getApplicableRate() {
		return applicableRate;
	}

	/**
	 * @param applicableRate
	 *            the applicableRate to set
	 */
	public void setApplicableRate(String applicableRate) {
		this.applicableRate = applicableRate;
	}

	/**
	 * @return the rebateEligibility
	 */
	public String getRebateEligibility() {
		return rebateEligibility;
	}

	/**
	 * @param rebateEligibility
	 *            the rebateEligibility to set
	 */
	public void setRebateEligibility(String rebateEligibility) {
		this.rebateEligibility = rebateEligibility;
	}

	/**
	 * @return the rateStartDt
	 */
	public String getRateStartDt() {
		return rateStartDt;
	}

	/**
	 * @param rateStartDt
	 *            the rateStartDt to set
	 */
	public void setRateStartDt(String rateStartDt) {
		this.rateStartDt = rateStartDt;
	}

	/**
	 * @return the rateEndDt
	 */
	public String getRateEndDt() {
		return rateEndDt;
	}

	/**
	 * @param rateEndDt
	 *            the rateEndDt to set
	 */
	public void setRateEndDt(String rateEndDt) {
		this.rateEndDt = rateEndDt;
	}

	/**
	 * @return the rateDocProof
	 */
	public String getRateDocProof() {
		return rateDocProof;
	}

	/**
	 * @param rateDocProof
	 *            the rateDocProof to set
	 */
	public void setRateDocProof(String rateDocProof) {
		this.rateDocProof = rateDocProof;
	}

}
