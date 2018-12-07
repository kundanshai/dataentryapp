/************************************************************************************************************
 * @(#) AuthCookieGeneratorUtil.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.MRDFileUploadDAO;

/**
 * <p>
 * This is the Utility class for generating Auth Cookie.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 */
public class AuthCookieGeneratorUtil {

	/**
	 * <p>
	 * This method is used to generate Auth Cookie
	 * </p>
	 * 
	 * @param userId
	 * @param hhdID
	 * @param userDetailsMap
	 * @param version
	 * @param serviceName
	 * @return
	 */
	public static String generateAuthCookie(String userId,String hhdID, Map<String, String> userDetailsMap, String version, String serviceName) {
		
		String authCookieString = createOutputString(hhdID, userDetailsMap
				.get("USER_PASSWORD"), userDetailsMap.get("CURRENT_DATE"),
				version, serviceName);
		return authCookieString;
	}

	/**
	 * <p>
	 * This method is used to create Output String
	 * </p>
	 * 
	 * @param userId
	 * @param passwordFromDatabase
	 * @param currentDate
	 * @param currentVersion
	 * @param serviceName
	 * @return
	 */
	private static String createOutputString(String userId,
			String passwordFromDatabase, String currentDate,
			String currentVersion, String serviceName) {
		String messageString = "";
		String inputString = "";
		String saltedInputString = "";
		String outputString = "";
		if (DJBConstants.AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE
				.equalsIgnoreCase(serviceName)) {
			if (null != userId && null != passwordFromDatabase
					&& null != currentDate && null != currentVersion) {
				messageString = userId.trim() + passwordFromDatabase.trim();
				inputString = messageString + DJBConstants.SALT_KEY;

				if ("Y"
						.equals(DJBConstants.VERSION_CONSIDERATION_WHILE_CREATING_AUTH_KEY_FLAG)) {
					saltedInputString = getMD5HashCode(inputString)
							+ passwordFromDatabase.trim() + currentDate.trim()
							+ userId.trim() + currentVersion.trim()
							+ DJBConstants.SALT_KEY;
				} else {
					saltedInputString = getMD5HashCode(inputString)
							+ passwordFromDatabase.trim() + currentDate.trim()
							+ userId.trim() + DJBConstants.SALT_KEY;
				}
				AppLog.info("saltedInputString>>>>"+saltedInputString);
				outputString = getMD5HashCode(saltedInputString);
				AppLog.info("userId>>" + userId + ">>serviceName>>"
						+ serviceName + ">>password>>" + passwordFromDatabase
						+ ">>currentDate>>" + currentDate + ">>outputString>>"
						+ outputString);
			}
		}

		return outputString;
	}

	/**
	 * <p>
	 * This method is used to get MD5 Hash code
	 * </p>
	 * 
	 * @param inputString
	 * @return
	 */
	private static String getMD5HashCode(String inputString) {
		String outputString = null;
		StringBuffer hexString = new StringBuffer();
		try {
			if (null != inputString && !"".equalsIgnoreCase(inputString)) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(inputString.getBytes());
				byte[] byteData = md.digest();
				for (int i = 0; i < byteData.length; i++) {
					String hex = Integer.toHexString(0xFF & byteData[i]);
					if (hex.length() == 1)
						hexString.append('0');
					hexString.append(hex);
				}
			}
		} catch (Exception ex) {
			AppLog.error(ex);
		}
		outputString = hexString.toString();
		// AppLog.info(sessionDetails,"inputString :" + inputString);
		return outputString;
	}

}
