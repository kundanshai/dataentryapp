/************************************************************************************************************
 * @(#) CaptchaGenerator.java 1.0 23 May 2015
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

import javax.servlet.http.HttpSession;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.sun.jersey.core.util.Base64;
import com.tcs.djb.rms.model.CaptchaDetails;
import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * Utility class for generating captcha.
 * </p>
 * 
 * @see Cage
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 23-05-2015
 */
public class CaptchaGenerator {

	/**
	 * 
	 */
	private static final Cage cage = new GCage();
	/**
	 * <p>
	 * This is the login EXP TIME IN MIN for CAPTCHA used in the Application. By
	 * default this "30" if not mentioned in the property file.
	 * <p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-06-2014
	 * 
	 */
	public static final long EXP_TIME_IN_MIN_TOKEN = Long
			.parseLong((null != PropertyUtil
					.getProperty("EXP_TIME_IN_MIN_TOKEN")) ? PropertyUtil
					.getProperty("EXP_TIME_IN_MIN_TOKEN").trim() : "30");
	/**
	 * <p>
	 * This is the login JNDI Data source used in the Application. For captcha
	 * related database activity this data source is used. By default this uses
	 * the common data source if not mentioned in the property file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-12-2014
	 */
	public static final String JNDI_DS_CAPTCHA = null != PropertyUtil
			.getProperty("JNDI_DS_CAPTCHA") ? PropertyUtil
			.getProperty("JNDI_DS_CAPTCHA") : PropertyUtil
			.getProperty("JNDI_DS");
	/**
	 * <p>
	 * This is the CAPTCHA JNDI PROVIDER used in the Application. For captcha
	 * related database activity this data source may be used. By default this
	 * uses the common data source provider if not mentioned in the property
	 * file.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-06-2014
	 */
	public static final String JNDI_PROVIDER_CAPTCHA = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_CAPTCHA") ? PropertyUtil.getProperty(
			"JNDI_PROVIDER_CAPTCHA").trim() : PropertyUtil.getProperty(
			"JNDI_PROVIDER").trim();
	/**
	 * <p>
	 * This is the login MAX LENGTH for CAPTCHA used in the Application. By
	 * default this "6" if not mentioned in the property file.
	 * <p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-06-2014
	 * 
	 */
	public static int MAX_LENGTH_CAPTCHA = Integer
			.parseInt(null != PropertyUtil.getProperty("MAX_LENGTH_CAPTCHA") ? PropertyUtil
					.getProperty("MAX_LENGTH_CAPTCHA").trim() : "6");

	/**
	 * <p>
	 * Generates a captcha token and stores it in the CaptchaCache.captchaMap.
	 * </p>
	 * 
	 * @param id
	 * @param sessionDetails
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static String captchaAsString(String id,
			SessionDetails sessionDetails) throws IOException, SQLException {
		String token = cage.getTokenGenerator().next();
		if (null != token && token.length() > MAX_LENGTH_CAPTCHA) {
			token = token.substring(0, MAX_LENGTH_CAPTCHA);
		}
		CaptchaDetails captchaDetails = new CaptchaDetails(token, id,
				System.currentTimeMillis(), System.currentTimeMillis()
						+ EXP_TIME_IN_MIN_TOKEN * 60 * 1000, false);
		int rowsAffected = updateCaptchaDetails(captchaDetails, sessionDetails);
		if (rowsAffected == 0) {
			rowsAffected = saveCaptchaDetails(captchaDetails, sessionDetails);
		}
		CaptchaCache.refresh(sessionDetails);
		byte[] imageByte = cage.draw(token);
		byte[] encoded = Base64.encode(imageByte);
		String encodedString = new String(encoded);
		return encodedString;
	}

	/**
	 * <p>
	 * Expire Captcha Details.
	 * </p>
	 * 
	 * @param captchaDetails
	 * @param sessionDetails
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int expireCaptchaDetails(CaptchaDetails captchaDetails,
			SessionDetails sessionDetails) throws SQLException, IOException {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			if (null != captchaDetails) {
				String query = expireCaptchaDetailsQuery(sessionDetails);
				AppLog.dbQuery(sessionDetails, query + "::CaptchaDetails::"
						+ captchaDetails);
				conn = DBConnector.getConnection(JNDI_DS_CAPTCHA,
						JNDI_PROVIDER_CAPTCHA);
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, sessionDetails.getUserId());
				ps.setString(++i, captchaDetails.getId());
				rowsAffected = ps.executeUpdate();
				AppLog.debugInfo(sessionDetails, "No of Rows Affected::"
						+ rowsAffected);
			}
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				// AppLog.error(e);
			}
		}
		AppLog.end(sessionDetails);
		return rowsAffected;
	}

	/**
	 * <p>
	 * Expire Captcha Details Query.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return
	 */
	private static String expireCaptchaDetailsQuery(
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update rs_captcha_dtl ");
		querySB.append(" set last_upd_dttm =sysdate");
		querySB.append(" ,token_exp=0");
		querySB.append(" ,token_exp_dttm=sysdate");
		querySB.append(" ,last_upd_id = ?");
		querySB.append(" ,version=version+1");
		querySB.append(" where token_exp_dttm > sysdate");
		querySB.append(" and client_id=? ");

		AppLog.end(sessionDetails);
		return querySB.toString();
	}

	/**
	 * <p>
	 * Generate Base64 encoded string for the captcha image.
	 * </p>
	 * 
	 * @param cage
	 * @param text
	 * @return
	 * @throws IOException
	 */
	protected static String generate(Cage cage, String text) throws IOException {
		String encodedString = null;
		byte[] imageByte = cage.draw(text);
		byte[] encoded = Base64.encode(imageByte);
		encodedString = new String(encoded);
		return encodedString;
	}

	/**
	 * Generates a captcha token and stores it in the session.
	 * 
	 * @param session
	 *            where to store the captcha.
	 */
	public static void generateToken(HttpSession session) {
		String token = cage.getTokenGenerator().next();
		session.setAttribute("captchaToken", token);
		markTokenUsed(session, false);
	}

	/**
	 * Used to retrieve previously stored captcha token from session.
	 * 
	 * @param session
	 *            where the token is possibly stored.
	 * @return token or null if there was none
	 */
	public static String getToken(HttpSession session) {
		Object val = session.getAttribute("captchaToken");
		return val != null ? val.toString() : null;
	}

	/**
	 * Used to retrieve previously stored captcha token from session.
	 * 
	 * @param id
	 * @return token or null if there was none
	 */
	public static CaptchaDetails getToken(String id,
			SessionDetails sessionDetails) {
		CaptchaDetails captchaDetails = CaptchaCache.captchaMap.get(id);
		if (null != captchaDetails) {
			AppLog.info(sessionDetails, "Captcha Token Loaded from Cache");
			markTokenUsed(captchaDetails, sessionDetails);
		} else {
			AppLog.info(sessionDetails,
					"Captcha Token not found, Refreshing Cache");
			CaptchaCache.refresh(sessionDetails);
			captchaDetails = CaptchaCache.captchaMap.get(id);
			if (null != captchaDetails) {
				markTokenUsed(captchaDetails, sessionDetails);
			}
		}
		return captchaDetails != null ? captchaDetails : null;
	}

	/**
	 * Checks if the token was used/unused for image generation.
	 * 
	 * @param session
	 *            where the token usage flag is possibly stored.
	 * @return true if the token was marked as unused in the session
	 */
	protected static boolean isTokenUsed(HttpSession session) {
		return !Boolean.FALSE.equals(session.getAttribute("captchaTokenUsed"));
	}

	/**
	 * Marks token as used/unused for image generation.
	 * 
	 * @param session
	 *            where the token usage flag is possibly stored.
	 * @param used
	 *            false if the token is not yet used for image generation
	 */
	protected static void markTokenUsed(CaptchaDetails captchaDetails,
			SessionDetails sessionDetails) {
		try {
			expireCaptchaDetails(captchaDetails, sessionDetails);
			CaptchaCache.refresh(sessionDetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * Marks token as used/unused for image generation.
	 * 
	 * @param session
	 *            where the token usage flag is possibly stored.
	 * @param used
	 *            false if the token is not yet used for image generation
	 */
	protected static void markTokenUsed(HttpSession session, boolean used) {
		session.removeAttribute("captchaToken");
	}

	/**
	 * <p>
	 * Refresh Captcha Details.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015.
	 */
	public static Map<String, CaptchaDetails> refreshCaptchaDetails(
			SessionDetails sessionDetails) throws SQLException, IOException {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, CaptchaDetails> captchaDetailsMap = null;
		try {
			String query = refreshCaptchaDetailsQuery(sessionDetails);
			AppLog.dbQuery(sessionDetails, query);
			conn = DBConnector.getConnection(JNDI_DS_CAPTCHA,
					JNDI_PROVIDER_CAPTCHA);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			captchaDetailsMap = new HashMap<String, CaptchaDetails>();
			while (rs.next()) {
				captchaDetailsMap.put(
						rs.getString("client_id"),
						new CaptchaDetails(rs.getString("token"), rs
								.getLong("token_exp"), rs
								.getString("client_id")));
			}
			if (null != captchaDetailsMap) {
				AppLog.debugInfo(sessionDetails, "No of Valid Tokens::"
						+ captchaDetailsMap.size());
			}
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				// AppLog.error(sessionDetails, e);
			}
		}
		AppLog.end(sessionDetails);
		return captchaDetailsMap;
	}

	/**
	 * <p>
	 * Refresh Captcha Details Query.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return
	 */
	private static String refreshCaptchaDetailsQuery(
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" select a.client_id, a.token, a.token_exp,to_number(to_char(a.create_dttm, 'yyyymmddhh24miss'))create_dttm from rs_captcha_dtl a ");
		querySB.append(" where a.token_exp_dttm > sysdate ");
		querySB.append(" and a.token_exp<>0");
		AppLog.end(sessionDetails);
		return querySB.toString();
	}

	/**
	 * <p>
	 * Save Captcha Details.
	 * </p>
	 * 
	 * @param captchaDetails
	 * @param sessionDetails
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015.
	 */
	public static int saveCaptchaDetails(CaptchaDetails captchaDetails,
			SessionDetails sessionDetails) throws SQLException, IOException {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			if (null != captchaDetails) {
				String query = saveCaptchaDetailsQuery(sessionDetails);
				AppLog.dbQuery(sessionDetails, query + "::CaptchaDetails::"
						+ captchaDetails);
				conn = DBConnector.getConnection(JNDI_DS_CAPTCHA,
						JNDI_PROVIDER_CAPTCHA);
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, captchaDetails.getId());
				ps.setString(++i, captchaDetails.getCaptchaToken());
				ps.setLong(++i, captchaDetails.getExpiresIn());
				ps.setString(++i, sessionDetails.getUserId());
				rowsAffected = ps.executeUpdate();
				AppLog.debugInfo(sessionDetails, "No of Rows Affected::"
						+ rowsAffected);
			}
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				// AppLog.error(e);
			}
		}
		AppLog.end(sessionDetails);
		return rowsAffected;
	}

	/**
	 * <p>
	 * Save Captcha Details Query.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return
	 */
	public static String saveCaptchaDetailsQuery(SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" insert into rs_captcha_dtl(client_id, token, token_exp,token_exp_dttm, create_dttm, create_id) ");
		querySB.append(" values(?, ?, ?, sysdate+" + EXP_TIME_IN_MIN_TOKEN
				+ "/(24 * 60),sysdate, ?)");
		AppLog.end(sessionDetails);
		return querySB.toString();
	}

	/**
	 * <p>
	 * Update Captcha Details.
	 * </p>
	 * 
	 * @param captchaDetails
	 * @param sessionDetails
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015.
	 */
	public static int updateCaptchaDetails(CaptchaDetails captchaDetails,
			SessionDetails sessionDetails) throws SQLException, IOException {
		AppLog.begin(sessionDetails);
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsAffected = 0;
		try {
			if (null != captchaDetails) {
				String query = updateCaptchaDetailsQuery(sessionDetails);
				AppLog.dbQuery(sessionDetails, query + "::CaptchaDetails::"
						+ captchaDetails);
				conn = DBConnector.getConnection(JNDI_DS_CAPTCHA,
						JNDI_PROVIDER_CAPTCHA);
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, captchaDetails.getCaptchaToken());
				ps.setLong(++i, captchaDetails.getExpiresIn());
				ps.setString(++i, sessionDetails.getUserId());
				ps.setString(++i, captchaDetails.getId());
				rowsAffected = ps.executeUpdate();
				AppLog.debugInfo(sessionDetails, "No of Rows Affected::"
						+ rowsAffected);
			}
		} finally {
			try {
				DBConnector.closeConnection(conn, ps);
			} catch (Exception e) {
				// AppLog.error(e);
			}
		}
		AppLog.end(sessionDetails);
		return rowsAffected;
	}

	/**
	 * <p>
	 * Update Captcha Details Query.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return
	 */
	public static String updateCaptchaDetailsQuery(SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update rs_captcha_dtl ");
		querySB.append(" set last_upd_dttm =sysdate");
		querySB.append(" ,token=?");
		querySB.append(" ,token_exp=?");
		querySB.append(" ,token_exp_dttm= sysdate+" + EXP_TIME_IN_MIN_TOKEN
				+ "/(24 * 60)");
		querySB.append(" ,last_upd_id = ?");
		querySB.append(" ,version=version+1");
		querySB.append(" where client_id=? ");
		AppLog.end(sessionDetails);
		return querySB.toString();
	}
}
