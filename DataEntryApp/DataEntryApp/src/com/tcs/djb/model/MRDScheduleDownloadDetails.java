/************************************************************************************************************
 * @(#) MRDScheduleDownloadDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for MRD Schedule Download Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class MRDScheduleDownloadDetails {
	private String billWindow;
	private String createDate;
	private String createdBy;
	private String downloadedFlag;
	private String fileName;
	private String percentageOfCompletion;
	private String searchCriteria;
	private String status;

	/**
	 * @return the billWindow
	 */
	public String getBillWindow() {
		return billWindow;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @return the createdBby
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the downloadedFlag
	 */
	public String getDownloadedFlag() {
		return downloadedFlag;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the percentageOfCompletion
	 */
	public String getPercentageOfCompletion() {
		return percentageOfCompletion;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param billWindow
	 *            the billWindow to set
	 */
	public void setBillWindow(String billWindow) {
		this.billWindow = billWindow;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param createdBby
	 *            the createdBby to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param downloadedFlag
	 *            the downloadedFlag to set
	 */
	public void setDownloadedFlag(String downloadedFlag) {
		this.downloadedFlag = downloadedFlag;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param percentageOfCompletion the percentageOfCompletion to set
	 */
	public void setPercentageOfCompletion(String percentageOfCompletion) {
		this.percentageOfCompletion = percentageOfCompletion;
	}

	/**
	 * @param searchCriteria
	 *            the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
