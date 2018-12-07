/************************************************************************************************************
 * @(#) DjbErrorCode.java 1.1 23 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.tcs.djb.rms.util.AppLog;
import com.tcs.djb.rms.util.DBConnector;
import com.tcs.djb.rms.util.PropertyUtil;
import com.tcs.djb.rms.util.UtilityForAll;

/**
 * <p>
 * Utility class for saving and fetching custom error code.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-06-2015
 */
public class DjbErrorCode {
	public static final String ERR_TYPE_BUSINESS = "Business";
	public static final String ERR_TYPE_CODING = "Coding";
	public static final String ERR_TYPE_SECURITY = "Security";
	private static String errCode;
	public static Map<String, String> errMap;
	/**
	 * <p>
	 * This is the login JNDI Data source used in the Application. For error
	 * code related database activity this data source is used. By default this
	 * uses the common data source if not mentioned in the property file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-06-2015
	 */
	public static final String JNDI_DS_ERROR_CODE = null != PropertyUtil
			.getProperty("JNDI_DS_ERROR_CODE") ? PropertyUtil
			.getProperty("JNDI_DS_ERROR_CODE") : PropertyUtil
			.getProperty("JNDI_DS");
	/**
	 * <p>
	 * This is the CAPTCHA JNDI PROVIDER used in the Application. For error code
	 * related database activity this data source may be used. By default this
	 * uses the common data source provider if not mentioned in the property
	 * file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-06-2015
	 */
	public static final String JNDI_PROVIDER_ERROR_CODE = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_ERROR_CODE") ? PropertyUtil
			.getProperty("JNDI_PROVIDER_ERROR_CODE").trim() : PropertyUtil
			.getProperty("JNDI_PROVIDER").trim();
	private static final String QUERY_INSERT = "insert into djb_err_lookup (err_cd, err_desc, err_type, create_id) values((select nvl(max(e.err_cd),0)+10 from djb_err_lookup e),?,?,?)";
	private static final String QUERY_SELECT = "select err_cd, err_desc, err_type, create_dttm, create_id, last_upd_dttm, last_upd_id, version from djb_err_lookup";

	/***
	 * <p>
	 * Get Error map for particular error description fetching error code. For
	 * New Error description it will save to database and than fetch the same.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-06-2015
	 * @param errDesc
	 * @param errType
	 * @return error code
	 */
	public static String getErrCode(String errDesc, String errType) {
		if (null == errMap || errMap.isEmpty()) {
			getErrMap();
		}
		errCode = errMap.get(errDesc);
		AppLog.debugInfo("errCode::" + errCode + "::errDesc::" + errDesc);
		if (UtilityForAll.isEmptyString(errCode)) {
			AppLog.info("Saving new Error...");
			saveErr(errDesc, errType);
			AppLog.info("Fetching new Error...");
			getErrMap();
			return getErrCode(errDesc, errType);
		} else {
			errCode = "ERR[" + errCode + "]:";
		}
		return errCode;
	}

	/**
	 * *
	 * <p>
	 * Get error code details from database.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-06-2015
	 */
	private static void getErrMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			AppLog.dbQuery(QUERY_SELECT);
			AppLog.debugInfo("Connecting " + JNDI_DS_ERROR_CODE + "@"
					+ JNDI_PROVIDER_ERROR_CODE);
			conn = DBConnector.getConnection(JNDI_DS_ERROR_CODE,
					JNDI_PROVIDER_ERROR_CODE);
			ps = conn.prepareStatement(QUERY_SELECT);
			rs = ps.executeQuery();
			if (null == errMap) {
				AppLog.debugInfo("Creating new errMap");
				errMap = new HashMap<String, String>();
			} else {
				AppLog.debugInfo("Clearing errMap of size::" + errMap.size());
				errMap.clear();
			}
			while (rs.next()) {
				errMap.put(rs.getString("err_desc"), rs.getString("err_cd"));
			}
			AppLog.debugInfo("errMap.size::" + errMap.size());
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				DBConnector.closeConnection(conn, ps, rs);
			} catch (Exception e) {
				// AppLog.error(e);
			}
		}
		AppLog.end();
	}

	/***
	 * <p>
	 * Save error details in database.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-06-2015
	 * 
	 * @param errDesc
	 * @param errType
	 */
	private static void saveErr(String errDesc, String errType) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			AppLog.dbQuery(QUERY_INSERT);
			AppLog.debugInfo("Connecting " + JNDI_DS_ERROR_CODE + "@"
					+ JNDI_PROVIDER_ERROR_CODE);
			conn = DBConnector.getConnection(JNDI_DS_ERROR_CODE,
					JNDI_PROVIDER_ERROR_CODE);
			ps = conn.prepareStatement(QUERY_INSERT);
			int i = 0;
			ps.setString(++i, errDesc);
			ps.setString(++i, errType);
			ps.setString(++i, PropertyUtil.getProperty("NODE_NAME"));
			rowsAffected = ps.executeUpdate();
			AppLog.debugInfo("No of Rows Affected::" + rowsAffected);
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
				// AppLog.error(e);
			}
		}
		AppLog.end();
	}

	private String errDesc;

	private String errType;

	/**
	 * @param errCode
	 * @param errDesc
	 * @param errType
	 */
	public DjbErrorCode(String errCode, String errDesc, String errType) {
		DjbErrorCode.errCode = errCode;
		this.errDesc = errDesc;
		this.errType = errType;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @return the errDesc
	 */
	public String getErrDesc() {
		return errDesc;
	}

	/**
	 * @return the errType
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * @param errCode
	 *            the errCode to set
	 */
	public void setErrCode(String errCode) {
		DjbErrorCode.errCode = errCode;
	}

	/**
	 * @param errDesc
	 *            the errDesc to set
	 */
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	/**
	 * @param errType
	 *            the errType to set
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	// public static final String _B_ERR = "2";
	// public static final String _J_EXCEPTION = "0";
	// public static final String _S_ERR = "1";
	// public static final String
	// B_ERR_BILL_ALLREADY_GENERATED_FOR_THIS_BILL_ROUND = _B_ERR
	// + "x010";
	// public static final String B_ERR_BILL_GENERATION_FAILURE = _B_ERR +
	// "x020";
	// public static final String
	// B_ERR_BILL_GENERATION_FAILURE_WHILE_POSTING_TO_JMS = _B_ERR
	// + "x030";
	// public static final String B_ERR_BILL_POSTING_TO_JMS_FAILED = _B_ERR +
	// "x040";
	// public static final String B_ERR_BILL_REQ_POST_TO_QUEUE_RETRY_ERR =
	// _B_ERR
	// + "x050";
	// public static final String B_ERR_BILLING_DONE_FOR_THIS_BILL_ROUND =
	// _B_ERR
	// + "x060";
	// public static final String B_ERR_BILLING_NOT_ALOWED_FOR_THIS_BILL_ROUND =
	// _B_ERR
	// + "x070";
	// public static final String B_ERR_CONSUMER_PORTAL_REG_FAIL = _B_ERR +
	// "x080";
	// public static final String B_ERR_CONSUMER_PORTAL_REG_SUCCESS = _B_ERR +
	// "x090";
	// public static final String B_ERR_CONTACT_DJB = _B_ERR + "x100";
	// public static final String B_ERR_INVALID_FILE = _B_ERR + "x";
	// public static final String B_ERR_INVALID_INPUT = _B_ERR + "x";
	// public static final String B_ERR_INVALID_INPUT_DTL = _B_ERR + "x";
	// public static final String B_ERR_INVALID_JSON = _B_ERR + "x";
	// public static final String B_ERR_MAX_FILE_SIZE_EXCEEDED = _B_ERR + "x";
	// public static final String B_ERR_MISSING_MANDATORY_INPUT = _B_ERR + "x";
	// public static final String B_ERR_MISSING_MANDATORY_INPUT_FILE = _B_ERR +
	// "x";
	// public static final String B_ERR_NO_DETAILS_FOUND = _B_ERR + "x";
	// public static final String B_ERR_NO_METER_READ_DETAILS = _B_ERR + "x";
	// public static final String B_ERR_OTP_VERIFICATION_PENDING = _B_ERR + "x";
	// public static final String B_ERR_SERVER_BUSY = _B_ERR + "x";
	// public static final String B_ERR_SYSTEM_EXCEPTION = _B_ERR + "x";
	// public static final String B_ERR_UPDATE_FAIL = _B_ERR + "x";
	// public static final String S_ERR_INVALID_APP_VERSION = _S_ERR + "x010";
	// public static final String S_ERR_INVALID_CAPTCHA = _S_ERR + "x020";
	// public static final String S_ERR_INVALID_CREDENTIAL = _S_ERR + "x030";
	// public static final String S_ERR_INVALID_DETAILS = _S_ERR + "x040";
	// public static final String S_ERR_INVALID_SESSION = _S_ERR + "x050";
	// public static final String S_ERR_OTP_EXPIRED = _S_ERR + "x060";
	// public static final String S_ERR_PASSWORD_EXPIRED = _S_ERR + "x060";
}
