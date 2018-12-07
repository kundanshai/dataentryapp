/************************************************************************************************************
 * @(#) FileDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for containing File Details
 * </p>
 * 
 * @author Matiur Rahman
 */
public class FileDetails {
	private String fileCreateDate;
	private String fileLastModifiedDate;
	private String fileName;
	private String filePath;
	private long fileSize;
	private String fileType;

	/**
	 * @return the fileCreateDate
	 */
	public String getFileCreateDate() {
		return fileCreateDate;
	}

	/**
	 * @return the fileLastModifiedDate
	 */
	public String getFileLastModifiedDate() {
		return fileLastModifiedDate;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileCreateDate
	 *            the fileCreateDate to set
	 */
	public void setFileCreateDate(String fileCreateDate) {
		this.fileCreateDate = fileCreateDate;
	}

	/**
	 * @param fileLastModifiedDate
	 *            the fileLastModifiedDate to set
	 */
	public void setFileLastModifiedDate(String fileLastModifiedDate) {
		this.fileLastModifiedDate = fileLastModifiedDate;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
