/************************************************************************************************************
 * @(#) ImageAuditSearchCriteria.java   09 Mar 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.ArrayList;

/**
 * <p>
 * Model class for Image audit action screen
 * </p>
 * 
 * @author Atanu(Tata Consultancy Services)
 * @history 09-03-2016 Atanu Added new variable and its getter and setter as per
 *          jTrac ANDROID-293 {@link #loggedInUserId}
 **/
public class ImageAuditSearchCriteria {

	/**
	 * Atanu Added the member variable on 09-03-2016 for audit action screen
	 */
	private String loggedInUserId;
	/**
	 * Atanu Added the member variable on 09-03-2016 for audit action screen
	 */
	private ArrayList<String> loggedInUserZroCdList;
	private String pageNo;
	private String recordPerPage;
	private String searchArea;
	private String searchAuditStatus;
	/**
	 * Madhuri Added the member variable on 04-09-2017 for Image audit action screen
	 */
	private String searchBillFromDate;
	private String searchBillRound;
	private String searchBillToDate;
	private String searchdBillGenSource;
	/**
	 * Madhuri Added the member variable on 04-09-2017 for Image audit action screen
	 */
	private String searchKno;
	private String searchMeterRdrEmpId;
	private String searchMRNo;
	private String searchZone;

	private String searchZROCode;

	/**
	 * @return the loggedInUserId
	 */
	public String getLoggedInUserId() {
		return loggedInUserId;
	}

	/**
	 * @return the loggedInUserZroCdList
	 */
	public ArrayList<String> getLoggedInUserZroCdList() {
		return loggedInUserZroCdList;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @return the recordPerPage
	 */
	public String getRecordPerPage() {
		return recordPerPage;
	}

	/**
	 * @return the searchArea
	 */
	public String getSearchArea() {
		return searchArea;
	}

	/**
	 * @return the searchAuditStatus
	 */
	public String getSearchAuditStatus() {
		return searchAuditStatus;
	}

	public String getSearchBillFromDate() {
		return searchBillFromDate;
	}

	/**
	 * @return the searchBillRound
	 */
	public String getSearchBillRound() {
		return searchBillRound;
	}

	public String getSearchBillToDate() {
		return searchBillToDate;
	}

	public String getSearchdBillGenSource() {
		return searchdBillGenSource;
	}

	/**
	 * @return the searchKno
	 */
	public String getSearchKno() {
		return searchKno;
	}

	public String getsearchMeterRdrEmpId() {
		return searchMeterRdrEmpId;
	}

	/**
	 * @return the searchMRNo
	 */
	public String getSearchMRNo() {
		return searchMRNo;
	}

	/**
	 * @return the searchZone
	 */
	public String getSearchZone() {
		return searchZone;
	}

	/**
	 * @return the searchZROCode
	 */
	public String getSearchZROCode() {
		return searchZROCode;
	}

	/**
	 * @param loggedInUserId
	 *            the loggedInUserId to set
	 */
	public void setLoggedInUserId(String loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	/**
	 * @param loggedInUserZroCdList
	 *            the loggedInUserZroCdList to set
	 */
	public void setLoggedInUserZroCdList(ArrayList<String> loggedInUserZroCdList) {
		this.loggedInUserZroCdList = loggedInUserZroCdList;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @param recordPerPage
	 *            the recordPerPage to set
	 */
	public void setRecordPerPage(String recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	/**
	 * @param searchArea
	 *            the searchArea to set
	 */
	public void setSearchArea(String searchArea) {
		this.searchArea = searchArea;
	}

	/**
	 * @param searchAuditStatus
	 *            the searchAuditStatus to set
	 */
	public void setSearchAuditStatus(String searchAuditStatus) {
		this.searchAuditStatus = searchAuditStatus;
	}

	public void setSearchBillFromDate(String searchBillFromDate) {
		this.searchBillFromDate = searchBillFromDate;
	}

	/**
	 * @param searchBillRound
	 *            the searchBillRound to set
	 */
	public void setSearchBillRound(String searchBillRound) {
		this.searchBillRound = searchBillRound;
	}

	public void setSearchBillToDate(String searchBillToDate) {
		this.searchBillToDate = searchBillToDate;
	}

	public void setSearchdBillGenSource(String searchdBillGenSource) {
		this.searchdBillGenSource = searchdBillGenSource;
	}

	/**
	 * @param searchKno
	 *            the searchKno to set
	 */
	public void setSearchKno(String searchKno) {
		this.searchKno = searchKno;
	}

	public void setsearchMeterRdrEmpId(String searchMeterRdrEmpId) {
		this.searchMeterRdrEmpId = searchMeterRdrEmpId;
	}

	/**
	 * @param searchMRNo
	 *            the searchMRNo to set
	 */
	public void setSearchMRNo(String searchMRNo) {
		this.searchMRNo = searchMRNo;
	}

	/**
	 * @param searchZone
	 *            the searchZone to set
	 */
	public void setSearchZone(String searchZone) {
		this.searchZone = searchZone;
	}

	/**
	 * @param searchZROCode
	 *            the searchZROCode to set
	 */
	public void setSearchZROCode(String searchZROCode) {
		this.searchZROCode = searchZROCode;
	}

}
