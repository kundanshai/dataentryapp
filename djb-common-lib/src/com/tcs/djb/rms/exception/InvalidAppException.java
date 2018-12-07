/************************************************************************************************************
 * @(#) InvalidAppException.java 1.0 11 May 2015
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
 * @since 11-05-2015.
 * 
 * @see RuntimeException
 */
public class InvalidAppException extends RuntimeException {

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
	public InvalidAppException() {
		super("InvalidAppException");
		this.message = "InvalidAppException";
	}

	/**
	 * @param message
	 */
	public InvalidAppException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * @param cause
	 */
	public InvalidAppException(Throwable cause) {
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
