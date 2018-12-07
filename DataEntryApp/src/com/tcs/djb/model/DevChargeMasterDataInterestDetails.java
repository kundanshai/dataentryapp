/************************************************************************************************************
 * @(#) DevChargeMasterDataInterestDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.Date;

/**
 * <p>
 * Module Class for Dev Charge Master Data Screen's Interest Details
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 * 
 */
public class DevChargeMasterDataInterestDetails {

	private String interestPercentage;

	private String interestStartDt;

	private String interestEndDt;

	private String interestDocProof;

	/**
	 * @return the interestPercentage
	 */
	public String getInterestPercentage() {
		return interestPercentage;
	}

	/**
	 * @param interestPercentage
	 *            the interestPercentage to set
	 */
	public void setInterestPercentage(String interestPercentage) {
		this.interestPercentage = interestPercentage;
	}

	/**
	 * @return the interestStartDt
	 */
	public String getInterestStartDt() {
		return interestStartDt;
	}

	/**
	 * @param interestStartDt
	 *            the interestStartDt to set
	 */
	public void setInterestStartDt(String interestStartDt) {
		this.interestStartDt = interestStartDt;
	}

	/**
	 * @return the interestEndDt
	 */
	public String getInterestEndDt() {
		return interestEndDt;
	}

	/**
	 * @param interestEndDt
	 *            the interestEndDt to set
	 */
	public void setInterestEndDt(String interestEndDt) {
		this.interestEndDt = interestEndDt;
	}

	/**
	 * @return the interestDocProof
	 */
	public String getInterestDocProof() {
		return interestDocProof;
	}

	/**
	 * @param interestDocProof
	 *            the interestDocProof to set
	 */
	public void setInterestDocProof(String interestDocProof) {
		this.interestDocProof = interestDocProof;
	}

}
