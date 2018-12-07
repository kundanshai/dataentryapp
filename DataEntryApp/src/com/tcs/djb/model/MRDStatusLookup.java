/************************************************************************************************************
 * @(#) MRDStatusLookup.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * MRDStatusLookup is a lookup class which contains different possible status of
 * an MRD. The default value for MRD Status will be Open.
 * </p>
 * 
 * @author 374204
 * 
 */
public class MRDStatusLookup {
	public static final MRDStatusLookup CLOSED = new MRDStatusLookup(20, "Closed");
	public static final MRDStatusLookup LOCKED = new MRDStatusLookup(30, "Locked");
	public static final MRDStatusLookup OPEN = new MRDStatusLookup(10, "Open");
	private int _statusCode;
	private String _statusDescr;
	/**
	 * Default constructor for MRD Status
	 * @param statusCode - status code 
	 * @param statusDescr - description for the status
	 */
	public MRDStatusLookup(int statusCode, String statusDescr) {
		super();
		_statusCode = statusCode;
		_statusDescr = statusDescr;
	}
	public int getStatusCode() {
		return _statusCode;
	}
	
	public String getStatusDescr() {
		return _statusDescr;
	}
	public void setStatusCode(int statusCode) {
		_statusCode = statusCode;
	}
	public void setStatusDescr(String statusDescr) {
		_statusDescr = statusDescr;
	}
	
}
