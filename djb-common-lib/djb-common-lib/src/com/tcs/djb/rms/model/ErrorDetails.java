
package com.tcs.djb.rms.model;

/**
 * <p>
 * ErrorDetails to store the error details occurred during any webservice call
 * or any operation.
 * </p>
 * 
 * @author 488273
 * 
 * @history 07-03-2014 Mrityunjoy HHD-114 new acct id has been added  so that in the 
 *          response account id send along with error code and error description       
 *          {@link #setKno()} 
 *          {@link #getKno()} 
 * 
 * 
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
	 * @author 488273
	 * @since 25-03-2014
	 * kno has been added 07-03-2014 Mrityunjoy HHD-114
	 **/
	private String kno;

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
	 * @author 488273
	 * @since 19-03-2014
	 * @param errorDescription
	 * @param errorType
	 * Mrityunjoy HHD-114 new KNO has been added  so that in the 
     * response account id send along with error code and error description
     * updated on 19th March 2014
	 */
	public ErrorDetails(String kno,String errorDescription, String errorType) {
		this.kno=kno;
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
	 * @author 488273
	 * @since 19-03-2014
	 * Mrityunjoy HHD-114 new KNO has been added  so that in the 
     * response account id send along with error code and error description
     * @return the kno
     * */
	public String getKno() {
		return kno;
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


	/**
	 * @author 488273
	 * @since 19-03-2014
	 * Mrityunjoy HHD-114 new KNO has been added  so that in the 
     * response account id send along with error code and error description
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

}
