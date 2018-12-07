/************************************************************************************************************
 * @(#) UcmFile.java 1.0 27 Aug 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.model;

import java.util.Map;

/**
 * <p>
 * File details bean. It contains {@link #contentMap}, {@link #createDate},
 * {@link #docAuthor},{@link #docLastModifier}, {@link #docName},
 * {@link #docTitle}, {@link #docType},{@link #documentNo},{@link #fileSize},
 * {@link #originalName},{@link #primaryFile},{@link #revisionID},
 * {@link #statusMessage},{@link #vaultfilePath}.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 18-12-2015
 * 
 */
public class UcmFile {
	Map<String, String> contentMap;
	private String createDate;
	private String docAuthor;
	private String docID;
	private String docLastModifier;
	private String docName;
	private String docTitle;
	private String docType;
	private String fileSize;
	private String originalName;
	private String primaryFile;
	private String revisionID;
	private String statusMessage;
	private String vaultfilePath;
	private String webExtension;
	private String webfilePath;
	private String webUrl;
	/**
	 * @return the contentMap
	 */
	public Map<String, String> getContentMap() {
		return contentMap;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @return the docAuthor
	 */
	public String getDocAuthor() {
		return docAuthor;
	}
	/**
	 * @return the docID
	 */
	public String getDocID() {
		return docID;
	}
	/**
	 * @return the docLastModifier
	 */
	public String getDocLastModifier() {
		return docLastModifier;
	}
	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}
	/**
	 * @return the docTitle
	 */
	public String getDocTitle() {
		return docTitle;
	}
	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * @return the originalName
	 */
	public String getOriginalName() {
		return originalName;
	}
	/**
	 * @return the primaryFile
	 */
	public String getPrimaryFile() {
		return primaryFile;
	}
	/**
	 * @return the revisionID
	 */
	public String getRevisionID() {
		return revisionID;
	}
	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @return the vaultfilePath
	 */
	public String getVaultfilePath() {
		return vaultfilePath;
	}
	/**
	 * @return the webExtension
	 */
	public String getWebExtension() {
		return webExtension;
	}
	/**
	 * @return the webfilePath
	 */
	public String getWebfilePath() {
		return webfilePath;
	}
	/**
	 * @return the webUrl
	 */
	public String getWebUrl() {
		return webUrl;
	}
	/**
	 * @param contentMap the contentMap to set
	 */
	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @param docAuthor the docAuthor to set
	 */
	public void setDocAuthor(String docAuthor) {
		this.docAuthor = docAuthor;
	}
	/**
	 * @param docID the docID to set
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}
	/**
	 * @param docLastModifier the docLastModifier to set
	 */
	public void setDocLastModifier(String docLastModifier) {
		this.docLastModifier = docLastModifier;
	}
	/**
	 * @param docName the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}
	/**
	 * @param docTitle the docTitle to set
	 */
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @param originalName the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	/**
	 * @param primaryFile the primaryFile to set
	 */
	public void setPrimaryFile(String primaryFile) {
		this.primaryFile = primaryFile;
	}
	/**
	 * @param revisionID the revisionID to set
	 */
	public void setRevisionID(String revisionID) {
		this.revisionID = revisionID;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	/**
	 * @param vaultfilePath the vaultfilePath to set
	 */
	public void setVaultfilePath(String vaultfilePath) {
		this.vaultfilePath = vaultfilePath;
	}
	/**
	 * @param webExtension the webExtension to set
	 */
	public void setWebExtension(String webExtension) {
		this.webExtension = webExtension;
	}
	/**
	 * @param webfilePath the webfilePath to set
	 */
	public void setWebfilePath(String webfilePath) {
		this.webfilePath = webfilePath;
	}
	/**
	 * @param webUrl the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UcmFile [contentMap=");
		builder.append(contentMap);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", docAuthor=");
		builder.append(docAuthor);
		builder.append(", docID=");
		builder.append(docID);
		builder.append(", docLastModifier=");
		builder.append(docLastModifier);
		builder.append(", docName=");
		builder.append(docName);
		builder.append(", docTitle=");
		builder.append(docTitle);
		builder.append(", docType=");
		builder.append(docType);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", originalName=");
		builder.append(originalName);
		builder.append(", primaryFile=");
		builder.append(primaryFile);
		builder.append(", revisionID=");
		builder.append(revisionID);
		builder.append(", statusMessage=");
		builder.append(statusMessage);
		builder.append(", vaultfilePath=");
		builder.append(vaultfilePath);
		builder.append(", webExtension=");
		builder.append(webExtension);
		builder.append(", webfilePath=");
		builder.append(webfilePath);
		builder.append(", webUrl=");
		builder.append(webUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
