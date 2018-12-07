/************************************************************************************************************
 * @(#) SessionDetails.java 1.0 27 Aug 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.model;

/**
 * <p>
 * This is class holds the session details of a request and to be used
 * throughout the Application.
 * </p>
 * 
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-06-2014
 * 
 */
public class SessionDetails {
	/**
	 * remarks
	 */
	private String remarks;
	/**
	 * Request Id of the Current request
	 */
	private String reqId;
	/**
	 * Session Id of the Current Session
	 */
	private String sessionId;
	/**
	 * Session start time
	 */
	private String startTime;

	/**
	 * User Id of the Web service caller.
	 */
	private String userId;

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the reqId
	 */
	public String getReqId() {
		return reqId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param reqId
	 *            the reqId to set
	 */
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
