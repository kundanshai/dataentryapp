/************************************************************************************************************
 * @(#) ErrorDetails.java   14 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * ErrorDetails to store the error details occurred during any webservice call
 * or any operation.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 */
public class ErrorDetails {

	/**
	 * Error Description
	 */
	private String errorDescription;
	/**
	 * Error Type
	 */
	private String errorType;

	/**
	 * 
	 */
	public ErrorDetails() {

	}

	/**
	 * @param errorDescription
	 * @param errorType
	 */
	public ErrorDetails(String errorDescription, String errorType) {
		this.errorDescription = errorDescription;
		this.errorType = errorType;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorDescription
	 *            the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @param errorType
	 *            the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

}
