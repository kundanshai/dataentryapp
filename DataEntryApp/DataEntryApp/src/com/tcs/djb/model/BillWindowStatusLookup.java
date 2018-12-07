/************************************************************************************************************
 * @(#) BillWindowStatusLookup.java   27 Jul 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * BillWindowStatusLookup is used for status lookup for Bill Window. Valid
 * Consumer status is defined as <code>OPEN</code> <code>CLOSED</code>
 * 
 * @author 374204 (Tata Consultancy Services)
 * @history 27/07/2012 apurb.sinha created the file
 */
public class BillWindowStatusLookup {
	public static final BillWindowStatusLookup OPEN = new BillWindowStatusLookup(10, "OPEN");
	public static final BillWindowStatusLookup CLOSED = new BillWindowStatusLookup(20, "CLOSED");
	private int _statusCode;
	private String _statusDescr;

	/**
	 * Default constructor for BillWindowStatusLookup
	 * 
	 * @param statusCode
	 *            - status code
	 * @param statusDescr
	 *            - description for the status
	 */
	public BillWindowStatusLookup(int statusCode, String statusDescr) {
		super();
		_statusCode = statusCode;
		_statusDescr = statusDescr;
	}

	/**
	 * @return the _statusCode
	 */
	public int getStatusCode() {
		return _statusCode;
	}

	/**
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		_statusCode = statusCode;
	}

	/**
	 * @return the _statusDescr
	 */
	public String getStatusDescr() {
		return _statusDescr;
	}

	/**
	 * @param statusDescr
	 */
	public void setStatusDescr(String statusDescr) {
		_statusDescr = statusDescr;
	}
	
	
}
