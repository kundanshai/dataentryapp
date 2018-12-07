/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * File details bean. It contains {@link #fileCreateDate},
 * {@link #fileLastModifiedDate},{@link #fileName},{@link #filePath},
 * {@link #fileLastAccessedDate},{@link #fileSize}, {@link #fileSizeinKB},
 * {@link #fileType}.
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 * 
 */
public class FileDetails {
	private String fileCreateDate;

	private String fileLastAccessedDate;

	private String fileLastModifiedDate;

	private String fileName;
	private String filePath;
	private long fileSize;
	private double fileSizeinKB;
	private String fileType;
	/**
	 * @return the fileCreateDate
	 */
	public String getFileCreateDate() {
		return fileCreateDate;
	}
	/**
	 * @return the fileLastAccessedDate
	 */
	public String getFileLastAccessedDate() {
		return fileLastAccessedDate;
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
	 * @return the fileSizeinKB
	 */
	public double getFileSizeinKB() {
		return fileSizeinKB;
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
	 * @param fileLastAccessedDate
	 *            the fileLastAccessedDate to set
	 */
	public void setFileLastAccessedDate(String fileLastAccessedDate) {
		this.fileLastAccessedDate = fileLastAccessedDate;
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
	 * @param fileSizeinKB
	 *            the fileSizeinKB to set
	 */
	public void setFileSizeinKB(double fileSizeinKB) {
		this.fileSizeinKB = fileSizeinKB;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileDetails [fileCreateDate=");
		builder.append(fileCreateDate);
		builder.append(", fileLastModifiedDate=");
		builder.append(fileLastModifiedDate);
		builder.append(", fileLastAccessedDate=");
		builder.append(fileLastAccessedDate);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", filePath=");
		builder.append(filePath);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", fileSizeinKB=");
		builder.append(fileSizeinKB);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append("]");
		return builder.toString();
	}

}
