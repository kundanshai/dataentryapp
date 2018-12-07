/************************************************************************************************************
 * @(#) DbLogger.java 1.1 09 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * Utility class for logging the request in database table.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-06-2015
 */
public class DbLogger {
	/**
	 * 
	 */
	public static String FLAG_ENABLE_DB_LOGGING = null != PropertyUtil
			.getProperty("FLAG_ENABLE_DB_LOGGING") ? PropertyUtil.getProperty(
			"FLAG_ENABLE_DB_LOGGING").trim() : "Y";
	/**
	 * <p>
	 * Global Flag Y value.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-12-2014
	 */
	public static final String FLAG_Y = "Y";
	/**
	 * <p>
	 * This is the login JNDI Data source used in the Application. For login
	 * related database activity this data source is used. By default this uses
	 * the common data source if not mentioned in the property file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-12-2014
	 */
	public static final String JNDI_DS_REQUEST_LOGGING = null != PropertyUtil
			.getProperty("JNDI_DS_REQUEST_LOGGING") ? PropertyUtil
			.getProperty("JNDI_DS_REQUEST_LOGGING") : PropertyUtil
			.getProperty("JNDI_DS");
	/**
	 * <p>
	 * This is the Login JNDI PROVIDER used in the Application. For login
	 * related database activity this data source may be used. By default this
	 * uses the common data source provider if not mentioned in the property
	 * file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-12-2014
	 */
	public static final String JNDI_PROVIDER_REQUEST_LOGGING = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_REQUEST_LOGGING") ? PropertyUtil
			.getProperty("JNDI_PROVIDER_REQUEST_LOGGING").trim() : PropertyUtil
			.getProperty("JNDI_PROVIDER");

	/**
	 * <p>
	 * Save Request details to Database.
	 * </p>
	 * 
	 * @param clientId
	 * @param clientSrc
	 * @param initTimeJava
	 * @param macList
	 * @param ipList
	 * @param servicePath
	 * @param proxyIp
	 * @param server
	 * @param sessionDetails
	 * @return
	 */
	public static int createDbLog(String clientId, String clientSrc,
			String initTimeJava, String macList, String ipList,
			String servicePath, String proxyIp, String server,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			String query = createDbLogQuery(clientId, clientSrc, initTimeJava,
					macList, ipList, sessionDetails);
			AppLog.dbQuery(sessionDetails, query);
			AppLog.debugInfo(sessionDetails, "Connecting "
					+ JNDI_DS_REQUEST_LOGGING + "@"
					+ JNDI_PROVIDER_REQUEST_LOGGING);
			conn = DBConnector.getConnection(JNDI_DS_REQUEST_LOGGING,
					JNDI_PROVIDER_REQUEST_LOGGING);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, sessionDetails.getReqId());
			ps.setString(++i, servicePath);
			ps.setString(++i, sessionDetails.getStartTime());
			ps.setString(++i, sessionDetails.getUserId());
			ps.setString(++i, proxyIp);
			ps.setString(++i, server);
			ps.setString(++i, sessionDetails.getReqId());
			if (!UtilityForAll.isEmptyString(clientId)) {
				ps.setString(++i, clientId);
			}
			if (!UtilityForAll.isEmptyString(clientSrc)) {
				ps.setString(++i, clientSrc);
			}
			if (!UtilityForAll.isEmptyString(initTimeJava)) {
				ps.setString(++i, initTimeJava);
			}
			if (!UtilityForAll.isEmptyString(macList)) {
				ps.setString(++i, macList);
			}
			if (!UtilityForAll.isEmptyString(ipList)) {
				ps.setString(++i, ipList);
			}

			rowsAffected = ps.executeUpdate();
			AppLog.debugInfo(sessionDetails, "No of Rows Affected::"
					+ rowsAffected);

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end(sessionDetails);
		return rowsAffected;
	}

	/**
	 * @param clientId
	 * @param clientSrc
	 * @param initTimeJava
	 * @param macList
	 * @param ipList
	 * @param sessionDetails
	 * @return
	 */
	private static String createDbLogQuery(String clientId, String clientSrc,
			String initTimeJava, String macList, String ipList,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" insert into rs_req_log (req_id, service_path, reach_time_java,user_id, ip_proxy, server,create_id");
		if (!UtilityForAll.isEmptyString(clientId)) {
			querySB.append(" ,client_id");
		}
		if (!UtilityForAll.isEmptyString(clientSrc)) {
			querySB.append(" , client_src");
		}
		if (!UtilityForAll.isEmptyString(initTimeJava)) {
			querySB.append(" , init_time_java");
		}
		if (!UtilityForAll.isEmptyString(macList)) {
			querySB.append(" , mac_list");
		}
		if (!UtilityForAll.isEmptyString(ipList)) {
			querySB.append(" , ip_list");
		}
		querySB.append(" )");
		querySB.append(" values (?,?,?,?,?,?,?");
		if (!UtilityForAll.isEmptyString(clientId)) {
			querySB.append(" ,?");
		}
		if (!UtilityForAll.isEmptyString(clientSrc)) {
			querySB.append(" , ?");
		}
		if (!UtilityForAll.isEmptyString(initTimeJava)) {
			querySB.append(" , ?");
		}
		if (!UtilityForAll.isEmptyString(macList)) {
			querySB.append(" , ?");
		}
		if (!UtilityForAll.isEmptyString(ipList)) {
			querySB.append(" , ?");
		}
		querySB.append(" )");
		AppLog.end(sessionDetails);
		return querySB.toString();
	}

	/**
	 * <p>
	 * Log IP address and other details to the application log and if Database
	 * logging is on it logs to Database.
	 * </p>
	 * 
	 * @param request
	 * @param sessionDetails
	 */
	public static void exit(String clientId, String clientSrc,
			String initTimeJava, String macList, String ipList,
			SessionDetails sessionDetails, long reqNo) {
		long elapsedTime = UtilityForAll.getTimeElapsedSeconds(sessionDetails
				.getStartTime());
		sessionDetails.setRemarks("Time Taken>>"
				+ (null != sessionDetails.getRemarks() ? sessionDetails
						.getRemarks() : "") + ">>Total>>" + elapsedTime
				+ " secs.");
		if (FLAG_Y.equals(FLAG_ENABLE_DB_LOGGING)) {
			AppLog.info(sessionDetails, "Logging request Into Table...");
			updateDbLog(clientId, clientSrc, initTimeJava, macList, ipList,
					sessionDetails, reqNo);
		}
		AppLog.info(sessionDetails, "Request No::" + (reqNo + 1) + "::"
				+ sessionDetails.getRemarks());
	}

	/**
	 * <p>
	 * Log IP address and other details to the application log and if Database
	 * logging is on it logs to Database.
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param clientId
	 * @param clientSrc
	 * @param initTimeJava
	 * @param macList
	 * @param ipList
	 * @param servicePath
	 * @param sessionDetails
	 */
	public static void log(HttpServletRequest request, String clientId,
			String clientSrc, String initTimeJava, String macList,
			String ipList, String servicePath, SessionDetails sessionDetails) {
		String proxyIp = request.getRemoteAddr();
		String ipAddress = UtilityForAll.getIpFromRequest(request,
				sessionDetails);
		String hostName = null != request ? request.getLocalName() : "NA";
		if (FLAG_Y.equals(FLAG_ENABLE_DB_LOGGING)) {
			AppLog.info(sessionDetails, "Logging request Into Table...");
			createDbLog(clientId, clientSrc, initTimeJava, macList, ipList,
					servicePath, proxyIp, hostName, sessionDetails);
		}
		AppLog.info(sessionDetails, "::CALL FROM IP::" + ipAddress
				+ "::PROXY::" + proxyIp + "::HOST NAME::" + hostName);
	}

	/**
	 * <p>
	 * Update Request details to Database.
	 * </p>
	 * 
	 * @param clientId
	 * @param clientSrc
	 * @param initTimeJava
	 * @param macList
	 * @param ipList
	 * @param sessionDetails
	 * @param reqNo
	 * @return
	 */
	public static int updateDbLog(String clientId, String clientSrc,
			String initTimeJava, String macList, String ipList,
			SessionDetails sessionDetails, long reqNo) {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			String id = "";
			if (null != sessionDetails.getUserId()
					&& sessionDetails.getUserId().length() == 10) {
				id = sessionDetails.getReqId().replaceFirst(
						sessionDetails.getUserId(), "");
			}
			String query = updateDbLogQuery(clientId, clientSrc, initTimeJava,
					macList, ipList, sessionDetails);
			AppLog.dbQuery(sessionDetails, query + "::" + id + "::"
					+ sessionDetails.getStartTime());
			AppLog.debugInfo(sessionDetails, "Connecting "
					+ JNDI_DS_REQUEST_LOGGING + "@"
					+ JNDI_PROVIDER_REQUEST_LOGGING);
			conn = DBConnector.getConnection(JNDI_DS_REQUEST_LOGGING,
					JNDI_PROVIDER_REQUEST_LOGGING);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (!UtilityForAll.isEmptyString(sessionDetails.getUserId())) {
				ps.setString(++i, sessionDetails.getUserId());
			}
			if (!UtilityForAll.isEmptyString(clientId)) {
				ps.setString(++i, clientId);
			}
			if (!UtilityForAll.isEmptyString(clientSrc)) {
				ps.setString(++i, clientSrc);
			}
			if (!UtilityForAll.isEmptyString(initTimeJava)) {
				ps.setString(++i, initTimeJava);
			}
			if (!UtilityForAll.isEmptyString(macList)) {
				ps.setString(++i, macList);
			}
			if (!UtilityForAll.isEmptyString(ipList)) {
				ps.setString(++i, ipList);
			}
			ps.setString(++i, new Date().toString());
			ps.setString(++i, sessionDetails.getRemarks());
			ps.setString(++i, (reqNo + 1) + "");
			ps.setString(++i, sessionDetails.getUserId());
			ps.setString(++i, id + "%");
			ps.setString(++i, sessionDetails.getStartTime());
			rowsAffected = ps.executeUpdate();
			AppLog.debugInfo(sessionDetails, "No of Rows Affected::"
					+ rowsAffected);

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end(sessionDetails);
		return rowsAffected;
	}

	private static String updateDbLogQuery(String clientId, String clientSrc,
			String initTimeJava, String macList, String ipList,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update rs_req_log ");
		querySB.append(" set last_upd_dttm = sysdate, version = version+1, ");
		if (!UtilityForAll.isEmptyString(sessionDetails.getUserId())) {
			querySB.append(" user_id=?,");
		}
		if (!UtilityForAll.isEmptyString(clientId)) {
			querySB.append(" client_id=?, ");
		}
		if (!UtilityForAll.isEmptyString(clientSrc)) {
			querySB.append(" client_src=?, ");
		}
		if (!UtilityForAll.isEmptyString(initTimeJava)) {
			querySB.append(" init_time_java=?, ");
		}
		if (!UtilityForAll.isEmptyString(macList)) {
			querySB.append(" mac_list=?, ");
		}
		if (!UtilityForAll.isEmptyString(ipList)) {
			querySB.append(" ip_list=?, ");
		}
		querySB.append(" out_time_java=?, ");
		querySB.append(" remarks=?,");
		querySB.append(" req_no=?,");
		querySB.append(" last_upd_id=?, ");
		querySB.append(" out_time_db=sysdate");
		querySB.append(" where req_id like ?");
		querySB.append(" and reach_time_java = ?");
		AppLog.end(sessionDetails);
		return querySB.toString();
	}
}
