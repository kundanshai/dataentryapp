/************************************************************************************************************
 * @(#) FileNumberSearchDetails.java   19 Jan 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * FileNumberSearchScreenDetails class contains all the detail fields for File
 * Number Search screen.
 * </p>
 * 
 * @author Arnab Nandy(Tata Consultancy Services)
 * @since 19-01-2016
 */

public class FileNumberSearchDetails {

	/**
	 * ZRO code.
	 */
	private String selectedZROCode;

	/**
	 * Serial Number.
	 */
	private String slNo;

	/**
	 * Application Reference Number for consumer.
	 */
	private String arn;

	/**
	 * File Number of the consumer.
	 */
	private String fileNo;

	/**
	 * Applicant Name.
	 */
	private String consumerName;

	/**
	 * Status of the file.
	 */
	private String statusCD;

	/**
	 * Logged by user id.
	 */
	private String loggedBy;

	/**
	 * Log Date of file
	 */
	private String loggedDate;

	/**
	 * Lot Number of the file.
	 */
	private String lotNo;

	/**
	 * @return the arn
	 */
	public String getArn() {
		return arn;
	}

	/**
	 * @param arn
	 *            the arn to set
	 */
	public void setArn(String arn) {
		this.arn = arn;
	}

	/**
	 * @return the fileNo
	 */
	public String getFileNo() {
		return fileNo;
	}

	/**
	 * @param fileNo
	 *            the fileNo to set
	 */
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @param consumerName
	 *            the consumerName to set
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @return the statusCD
	 */
	public String getStatusCD() {
		return statusCD;
	}

	/**
	 * @param statusCD
	 *            the statusCD to set
	 */
	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	/**
	 * @return the loggedBy
	 */
	public String getLoggedBy() {
		return loggedBy;
	}

	/**
	 * @param loggedBy
	 *            the loggedBy to set
	 */
	public void setLoggedBy(String loggedBy) {
		this.loggedBy = loggedBy;
	}

	/**
	 * @return the loggedDate
	 */
	public String getLoggedDate() {
		return loggedDate;
	}

	/**
	 * @param loggedDate
	 *            the loggedDate to set
	 */
	public void setLoggedDate(String loggedDate) {
		this.loggedDate = loggedDate;
	}

	/**
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * @param lotNo
	 *            the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

}
