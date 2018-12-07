/************************************************************************************************************
 * @(#) CcbException.java 1.0 11 Dec 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.exception;

/**
 * <p>
 * Custom Exception handler Class.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 21-12-2015.
 * 
 * @see RuntimeException
 */
public class CcbException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Throwable cause;
	/**
	 * 
	 */
	private String message = null;

	/**
	 * @param message
	 */
	public CcbException() {
		super("CcbException");
		this.message = "CcbException, See CCB Log for more details.";
	}

	/**
	 * @param message
	 */
	public CcbException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * @param cause
	 */
	public CcbException(Throwable cause) {
		super(cause.getMessage());
		this.cause = cause;
		this.message = cause.getMessage();
	}

	@Override
	public Throwable getCause() {
		return this.cause;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
}
