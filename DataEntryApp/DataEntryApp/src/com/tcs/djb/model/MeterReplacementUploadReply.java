/************************************************************************************************************
 * @(#) MeterReplacementUploadReply.java   14 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * <code>MeterReplacementUploadReply</code> contains all the response for
 * uploading meter replacement details to the Database. It contains
 * <code>responseStatus</code> and <code>errorDetails</code>
 * </p>
 * 
 * @see ErrorDetails
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 */
public class MeterReplacementUploadReply {
	/**
	 * Error Details.
	 */
	private ErrorDetails[] errorDetails;
	/**
	 * response Status.
	 */
	private String responseStatus;

	/**
	 * @return the errorDetails
	 */
	public ErrorDetails[] getErrorDetails() {
		return errorDetails;
	}

	/**
	 * @return the responseStatus
	 */
	public String getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @param errorDetails
	 *            the errorDetails to set
	 */
	public void setErrorDetails(ErrorDetails[] errorDetails) {
		this.errorDetails = errorDetails;
	}

	/**
	 * @param responseStatus
	 *            the responseStatus to set
	 */
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
}
