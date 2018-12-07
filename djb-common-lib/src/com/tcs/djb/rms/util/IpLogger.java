/************************************************************************************************************
 * @(#) IpLogger.java 1.1 09 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import javax.servlet.http.HttpServletRequest;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * Utility class for logging IP address from the request.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-06-2015
 */
public class IpLogger {
	/**
	 * <p>
	 * Log IP address to the application log if debug mode in on.
	 * </p>
	 * 
	 * @param request
	 * @param sessionDetails
	 */
	public static void debug(HttpServletRequest request,
			SessionDetails sessionDetails) {
		String proxyIP = request.getRemoteAddr();
		String ipAddress = UtilityForAll.getIpFromRequest(request,
				sessionDetails);
		String hostName = null != request ? request.getLocalName() : "NA";
		AppLog.debugInfo(sessionDetails, "::CALL FROM IP::" + ipAddress
				+ "::PROXY::" + proxyIP + "::HOST NAME::" + hostName);
	}

	/**
	 * <p>
	 * Log IP address to the application log.
	 * </p>
	 * 
	 * @param request
	 * @param sessionDetails
	 */
	public static void log(HttpServletRequest request,
			SessionDetails sessionDetails) {
		String proxyIP = request.getRemoteAddr();
		String ipAddress = UtilityForAll.getIpFromRequest(request,
				sessionDetails);
		String hostName = null != request ? request.getLocalName() : "NA";
		AppLog.info(sessionDetails, "::CALL FROM IP::" + ipAddress
				+ "::PROXY::" + proxyIP + "::HOST NAME::" + hostName);
	}
}
