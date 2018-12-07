/************************************************************************************************************
 * @(#) HHDVersionMasterDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * HHD Version Master Details for HHD Version Master Screen.
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 */
public class HHDVersionMasterDetails {
	
	/**
	 * Serial No.
	 */
	private String slNo;
	/**
	 * Version No.
	 */
	private String versionNo;
	/**
	 * FTP Location
	 */
	private String ftpLocation;
	/**
	 * FTP UserName
	 */
	private String ftpUserName;
	/**
	 * FTP Password
	 */
	private String ftpPassword;
	/**
	 * browse file path
	 */
	private String browseFilePath;
	
	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}
	/**
	 * @param slNo the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	/**
	 * @return the versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return the ftpLocation
	 */
	public String getFtpLocation() {
		return ftpLocation;
	}
	/**
	 * @param ftpLocation the ftpLocation to set
	 */
	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	}
	/**
	 * @return the ftpUserName
	 */
	public String getFtpUserName() {
		return ftpUserName;
	}
	/**
	 * @param ftpUserName the ftpUserName to set
	 */
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	/**
	 * @return the ftpPassword
	 */
	public String getFtpPassword() {
		return ftpPassword;
	}
	/**
	 * @param ftpPassword the ftpPassword to set
	 */
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	/**
	 * @return the browseFilePath
	 */
	public String getBrowseFilePath() {
		return browseFilePath;
	}
	/**
	 * @param browseFilePath the browseFilePath to set
	 */
	public void setBrowseFilePath(String browseFilePath) {
		this.browseFilePath = browseFilePath;
	}

	
}
