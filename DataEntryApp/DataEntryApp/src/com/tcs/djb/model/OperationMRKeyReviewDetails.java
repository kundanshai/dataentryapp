/************************************************************************************************************
 * @(#) OperationMRKeyReviewDetails.java   01 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * This is the container class that contains all the details for Operation MRKey
 * Review screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 01-03-2013
 * @history 21-03-2013 Matiur Rahman modified method orderBy as per JTrac ID
 *          DJB-1970.
 */
public class OperationMRKeyReviewDetails {
	/**
	 * Bill Round ID.
	 */
	private String billRound;
	/**
	 * MR Key of the MRD.
	 */
	private String mrKey;
	/**
	 * MR Key Status Code.
	 */
	private String mrKeyStatusCode;
	private String orderBy;

	/**
	 * Process Counter of the MR Key.
	 */
	private String processCounter;

	/**
	 * Serial Number of the record.
	 */
	private String slNo;
	/**
	 * Total Number of Account in each MR key.
	 */
	private String totalNoOfAccount;

	/**
	 * Default Constructor.
	 */
	public OperationMRKeyReviewDetails() {
	}

	/**
	 * Parameterized Constructor.
	 * 
	 * @param mrKey
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param slNo
	 * @param totalNoOfAccount
	 */
	public OperationMRKeyReviewDetails(String billRound, String mrKey,
			String mrKeyStatusCode, String processCounter, String slNo,
			String totalNoOfAccount) {
		this.billRound = billRound;
		this.mrKey = mrKey;
		this.mrKeyStatusCode = mrKeyStatusCode;
		this.processCounter = processCounter;
		this.slNo = slNo;
		this.totalNoOfAccount = totalNoOfAccount;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the mrKey
	 */
	public String getMrKey() {
		return mrKey;
	}

	/**
	 * @return the mrKeyStatusCode
	 */
	public String getMrKeyStatusCode() {
		return mrKeyStatusCode;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @return the processCounter
	 */
	public String getProcessCounter() {
		return processCounter;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @return the totalNoOfAccount
	 */
	public String getTotalNoOfAccount() {
		return totalNoOfAccount;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param mrKey
	 *            the mrKey to set
	 */
	public void setMrKey(String mrKey) {
		this.mrKey = mrKey;
	}

	/**
	 * @param mrKeyStatusCode
	 *            the mrKeyStatusCode to set
	 */
	public void setMrKeyStatusCode(String mrKeyStatusCode) {
		this.mrKeyStatusCode = mrKeyStatusCode;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @param processCounter
	 *            the processCounter to set
	 */
	public void setProcessCounter(String processCounter) {
		this.processCounter = processCounter;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	/**
	 * @param totalNoOfAccount
	 *            the totalNoOfAccount to set
	 */
	public void setTotalNoOfAccount(String totalNoOfAccount) {
		this.totalNoOfAccount = totalNoOfAccount;
	}

}
