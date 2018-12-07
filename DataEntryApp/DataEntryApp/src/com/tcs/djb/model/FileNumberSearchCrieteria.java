/************************************************************************************************************
 * @(#) FileNumberSearchCrieteria.java   19 Jan 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * FileNumberSearchScreenCriteria class contains all the search fields for File
 * Number Search screen.
 * </p>
 * 
 * @author Arnab Nandy (1227971) (Tata Consultancy Services)
 * @since 19-01-2016
 **/

public class FileNumberSearchCrieteria {
	private String pageNo;
	private String recordPerPage;
	private String zone;

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
	private String userList;

	/**
	 * Log From Date of file
	 */
	private String loggedFromDate;

	/**
	 * Log From Date of file
	 */
	private String loggedToDate;

	/**
	 * Lot Number of the file.
	 */
	private String lotNo;

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the recordPerPage
	 */
	public String getRecordPerPage() {
		return recordPerPage;
	}

	/**
	 * @param recordPerPage
	 *            the recordPerPage to set
	 */
	public void setRecordPerPage(String recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
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
	 * @return the userList
	 */
	public String getuserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setuserList(String userList) {
		this.userList = userList;
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
	 * @return the loggedToDate
	 */
	public String getLoggedToDate() {
		return loggedToDate;
	}

	/**
	 * @param loggedToDate
	 *            the loggedToDate to set
	 */
	public void setLoggedToDate(String loggedToDate) {
		this.loggedToDate = loggedToDate;
	}

	/**
	 * @return the loggedFromDate
	 */
	public String getLoggedFromDate() {
		return loggedFromDate;
	}

	/**
	 * @param loggedFromDate
	 *            the loggedFromDate to set
	 */
	public void setLoggedFromDate(String loggedFromDate) {
		this.loggedFromDate = loggedFromDate;
	}

}
