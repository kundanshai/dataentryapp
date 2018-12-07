/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * This is the Utility class for input field validation used to validate various
 * input parameters on the basis of regular expression written in the property
 * file.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 * @see Matcher
 * @see Pattern
 */

public class DJBFieldValidatorUtil {
	/**
	 * <p>
	 * Value of static variable <code>UPDATABLE_CONS_PRE_BILL_STAT_IDS</code> is
	 * retrieved from Property File <code>djb_rms_web_services.properties</code>
	 * for the list of non updatable cons_pre_bill_stat_id. By default value is
	 * "15,20,30,40,50,60,90" or value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static final String UPDATABLE_CONS_PRE_BILL_STAT_IDS = null != PropertyUtil
			.getProperty("UPDATABLE_CONS_PRE_BILL_STAT_IDS") ? PropertyUtil
			.getProperty("UPDATABLE_CONS_PRE_BILL_STAT_IDS")
			: "15,20,30,40,50,60,90";

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Allowed Special
	 * Characters of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAllowedSpecialCharRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkAllowedSpecialChar(String paramName,
			String paramValue, int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAllowedSpecialCharRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Allowed Special
	 * Characters of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAllowedSpecialCharRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkAllowedSpecialChar(String paramName,
			String paramValue, int maxLength, boolean isNulable,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAllowedSpecialCharRegex"),
							sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Alphabet of
	 * maximum length maxLength on the basis of regular expression written in
	 * the property file with key <code>checkAlphabetRegex</code>.
	 * </p>
	 * to check if the paramValue contains only Alphabet of maximum length
	 * maxLength
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkAlphabet(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAlphabetRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end();
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end();
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Alphabet of
	 * maximum length maxLength on the basis of regular expression written in
	 * the property file with key <code>checkAlphabetRegex</code>.
	 * </p>
	 * to check if the paramValue contains only Alphabet of maximum length
	 * maxLength
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkAlphabet(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAlphabetRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Alpha Numeric
	 * Characters of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAlphaNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkAlphaNumeric(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAlphaNumericRegex"))) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end();
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end();
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end();
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Alpha Numeric
	 * Characters of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAlphaNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkAlphaNumeric(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAlphaNumericRegex"),
							sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end(sessionDetails);
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Amount
	 * format of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAmountRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkAmount(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAmountRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Amount
	 * format of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkAmountRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkAmount(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkAmountRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Bill Cycle
	 * of maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkBillCycleRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkBillCycle(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkBillCycleRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}

				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end();
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end();
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Bill Cycle
	 * of maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkBillCycleRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkBillCycle(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkBillCycleRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}

				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if ("string".equalsIgnoreCase(paramValue)
						|| "?".equalsIgnoreCase(paramName)) {
					errMsg = "Invalid " + paramName + ".";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of Consumer Status loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param validConsumerStatusCodeList
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkConsumerStatus(String paramName,
			String paramValue, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog.debugInfo("validConsumerStatusCodeList::"
							+ UPDATABLE_CONS_PRE_BILL_STAT_IDS
							+ "::paramValue::" + paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							UPDATABLE_CONS_PRE_BILL_STAT_IDS, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of Consumer Status loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param validConsumerStatusCodeList
	 * @param sessionDetails
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkConsumerStatus(String paramName,
			String paramValue, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog.debugInfo(sessionDetails,
							"validConsumerStatusCodeList::"
									+ UPDATABLE_CONS_PRE_BILL_STAT_IDS
									+ "::paramValue::" + paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							UPDATABLE_CONS_PRE_BILL_STAT_IDS, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Date in
	 * dd/MM/yyyy format on the basis of regular expression written in the
	 * property file with key <code>checkDateRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkDate(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.trim().length() != 0) {
					if (!validateDateDDMMYYYY(paramValue, PropertyUtil
							.getProperty("checkDateRegex"))) {
						errMsg = paramName
								+ " is Invalid Date Format, Please Enter in dd/MM/yyyy format.(eg. 01/01/2013)";
					}
				}
				if (paramValue.trim().length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
				if (paramValue.trim().length() > 0
						&& paramValue.trim().length() < 10) {
					errMsg = paramName
							+ " is Invalid Date Format, Please Enter in dd/MM/yyyy format.(eg. 01/01/2013).";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Date in
	 * dd/MM/yyyy format on the basis of regular expression written in the
	 * property file with key <code>checkDateRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkDate(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.trim().length() != 0) {
					if (!validateDateDDMMYYYY(paramValue, PropertyUtil
							.getProperty("checkDateRegex"))) {
						errMsg = paramName
								+ " is Invalid Date Format, Please Enter in dd/MM/yyyy format.(eg. 01/01/2013)";
					}
				}
				if (paramValue.trim().length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
				if (paramValue.trim().length() > 0
						&& paramValue.trim().length() < 10) {
					errMsg = paramName
							+ " is Invalid Date Format, Please Enter in dd/MM/yyyy format.(eg. 01/01/2013).";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Email
	 * Address of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkEmailRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkEmail(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkEmailRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid Email
	 * Address of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkEmailRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkEmail(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkEmailRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue for file name contains Only
	 * Valid Name format of maximum length less than maxLength on the basis of
	 * regular expression written in the property file with key
	 * <code>checkFileNameRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkFileName(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkFileNameRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue for file name contains Only
	 * Valid Name format of maximum length less than maxLength on the basis of
	 * regular expression written in the property file with key
	 * <code>checkFileNameRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkFileName(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkFileNameRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid
	 * HTTPParameterValue of maximum length less than maxLength on the basis of
	 * regular expression written in the property file with key
	 * <code>checkHTTPParameterValueRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkHTTPParameterValue(String paramName,
			String paramValue, int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					// String getValidInpuStr =
					// validator.getValidInput(paramName,
					// paramValue, "VALID_HTTPParameterValue", maxLength,
					// isNulable);
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkHTTPParameterValueRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only Valid
	 * HTTPParameterValue of maximum length less than maxLength on the basis of
	 * regular expression written in the property file with key
	 * <code>checkHTTPParameterValueRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkHTTPParameterValue(String paramName,
			String paramValue, int maxLength, boolean isNulable,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					// String getValidInpuStr =
					// validator.getValidInput(paramName,
					// paramValue, "VALID_HTTPParameterValue", maxLength,
					// isNulable);
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkHTTPParameterValueRegex"),
							sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of open bill round loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param openBillRoundList
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkIfInOpenBillRound(String paramName,
			String paramValue, boolean isNulable, String openBillRoundList) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog.debugInfo("openBillRoundList::" + openBillRoundList
							+ "::paramValue::" + paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							openBillRoundList, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			AppLog.debugInfo("openBillRoundList::" + openBillRoundList
					+ "::paramValue::" + paramValue);
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of open bill round loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param openBillRoundList
	 * @param sessionDetails
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkIfInOpenBillRound(String paramName,
			String paramValue, boolean isNulable, String openBillRoundList,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog
							.debugInfo(sessionDetails, "openBillRoundList::"
									+ openBillRoundList + "::paramValue::"
									+ paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							openBillRoundList, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			AppLog.debugInfo(sessionDetails, "openBillRoundList::"
					+ openBillRoundList + "::paramValue::" + paramValue);
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Meter Read
	 * values of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkMeterRead(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkMeterReadRegex"))) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end();
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Digits.";
					AppLog.end();
					return errMsg;
				}
				if (null != paramName
						&& (paramName.toUpperCase()).indexOf("KNO") > -1) {
					if (paramValue.length() != maxLength) {
						errMsg = paramName + " Should be of " + maxLength
								+ " Digits.";
						AppLog.end();
						return errMsg;
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Meter Read
	 * values of maximum length less than maxLength on the basis of regular
	 * expression written in the property file with key
	 * <code>checkNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkMeterRead(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkMeterReadRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end(sessionDetails);
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Digits.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (null != paramName
						&& (paramName.toUpperCase()).indexOf("KNO") > -1) {
					if (paramValue.length() != maxLength) {
						errMsg = paramName + " Should be of " + maxLength
								+ " Digits.";
						AppLog.end(sessionDetails);
						return errMsg;
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of Meter Read Remark loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param meterReadTypeCodeList
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkMeterReadRemark(String paramName,
			String paramValue, boolean isNulable, String meterReadTypeCodeList) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog.debugInfo("meterReadTypeCodeList::"
							+ meterReadTypeCodeList + "::paramValue::"
							+ paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							meterReadTypeCodeList, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.debugInfo("meterReadTypeCodeList>>" + meterReadTypeCodeList);
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains only in a valid list
	 * of Meter Read Remark loaded to application context.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param meterReadTypeCodeList
	 * @param sessionDetails
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String checkMeterReadRemark(String paramName,
			String paramValue, boolean isNulable, String meterReadTypeCodeList,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					AppLog.debugInfo(sessionDetails, "meterReadTypeCodeList::"
							+ meterReadTypeCodeList + "::paramValue::"
							+ paramValue);
					if (!UtilityForAll.isFoundInDelimitedString(
							meterReadTypeCodeList, ",", paramValue)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.debugInfo(sessionDetails, "meterReadTypeCodeList>>"
					+ meterReadTypeCodeList);
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Name format
	 * of maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkNameRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkName(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkNameRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid Name format
	 * of maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkNameRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkName(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkNameRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Numeric values of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkNumeric(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkNumericRegex"))) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end();
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end();
					return errMsg;
				}
				if (null != paramName
						&& (paramName.toUpperCase()).indexOf("KNO") > -1) {
					if (paramValue.length() != maxLength) {
						errMsg = paramName + " Should be of " + maxLength
								+ " Digits.";
						AppLog.end();
						return errMsg;
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Numeric values of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkNumericRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkNumeric(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {

					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkNumericRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
						AppLog.end(sessionDetails);
						return errMsg;
					}
				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (null != paramName
						&& (paramName.toUpperCase()).indexOf("KNO") > -1) {
					if (paramValue.length() != maxLength) {
						errMsg = paramName + " Should be of " + maxLength
								+ " Digits.";
						AppLog.end(sessionDetails);
						return errMsg;
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}

		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the occurrence of particular character present
	 * or not on the basis of regular expression passed.
	 * </p>
	 * 
	 * @param inputParam
	 * @param regexExp
	 * @return true/false
	 */
	public static final boolean checkPresenceOfParticularChar(
			String inputParam, String regexExp) {
		AppLog.begin();
		try {
			boolean isFound = false;
			Pattern p = Pattern.compile(regexExp, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(inputParam);
			isFound = m.find();
			if (isFound) {
				AppLog.end();
				return true;
			}
		} catch (Exception ex) {
			AppLog.error(ex);
		}
		AppLog.end();
		return false;
	}

	/**
	 * <p>
	 * This method is to check if the occurrence of particular character present
	 * or not on the basis of regular expression passed.
	 * </p>
	 * 
	 * @param inputParam
	 * @param regexExp
	 * @param sessionDetails
	 * @return true/false
	 */
	public static final boolean checkPresenceOfParticularChar(
			String inputParam, String regexExp, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		try {
			boolean isFound = false;
			Pattern p = Pattern.compile(regexExp, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(inputParam);
			isFound = m.find();
			if (isFound) {
				AppLog.end(sessionDetails);
				return true;
			}
		} catch (Exception ex) {
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return false;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid URL of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkURLRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkURL(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkURLRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid URL of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkURLRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkURL(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkURLRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid UserId of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkUserIdRegex</code>.
	 * </p>
	 * 
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 */
	public static String checkUserId(String paramName, String paramValue,
			int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkUserIdRegex"))) {
						errMsg = "Invalid " + paramName + ".";
					}

				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Valid UserId of
	 * maximum length less than maxLength on the basis of regular expression
	 * written in the property file with key <code>checkUserIdRegex</code>.
	 * </p>
	 * 
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 */
	public static String checkUserId(String paramName, String paramValue,
			int maxLength, boolean isNulable, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				if (paramValue.length() != 0) {
					if (!checkPresenceOfParticularChar(paramValue, PropertyUtil
							.getProperty("checkUserIdRegex"), sessionDetails)) {
						errMsg = "Invalid " + paramName + ".";
					}

				}
				if (paramValue.length() > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " Characters.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Numeric
	 * Characters separated by comma of maximum length less than maxLength on
	 * the basis of regular expression written in the property file with key
	 * <code>checkAllowedSpecialCharRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 27-02-2015
	 */
	public static String checkValidCommaSepartedNumericValues(String paramName,
			String paramValue, int maxLength, boolean isNulable) {
		AppLog.begin();
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
				String[] paramValueArr = paramValue.split(",");
				if (paramValueArr.length != 0) {
					for (int i = 0; i < paramValueArr.length; i++) {
						String paramValueTemp = paramValueArr[i];
						if (null != paramValueTemp) {
							if (!checkPresenceOfParticularChar(paramValueTemp
									.trim(), PropertyUtil
									.getProperty("checkNumericRegex"))) {
								errMsg = "Invalid " + paramName + ".";
								AppLog.end();
								return errMsg;
							}
						}
					}
				}
				if (paramValueArr.length > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " values separated by comma.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end();
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(ex);
		}
		AppLog.end();
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if the paramValue contains Only Numeric
	 * Characters separated by comma of maximum length less than maxLength on
	 * the basis of regular expression written in the property file with key
	 * <code>checkAllowedSpecialCharRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param maxLength
	 * @param isNulable
	 * @param sessionDetails
	 * @return errMsg
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 27-02-2015
	 */
	public static String checkValidCommaSepartedNumericValues(String paramName,
			String paramValue, int maxLength, boolean isNulable,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = null;
		try {
			if (null != paramValue) {
				if (!isNulable && paramValue.trim().length() == 0) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
				String[] paramValueArr = paramValue.split(",");
				if (paramValueArr.length != 0) {
					for (int i = 0; i < paramValueArr.length; i++) {
						String paramValueTemp = paramValueArr[i];
						if (null != paramValueTemp) {
							if (!checkPresenceOfParticularChar(paramValueTemp
									.trim(), PropertyUtil
									.getProperty("checkNumericRegex"),
									sessionDetails)) {
								errMsg = "Invalid " + paramName + ".";
								AppLog.end(sessionDetails);
								return errMsg;
							}
						}
					}
				}
				if (paramValueArr.length > maxLength) {
					errMsg = paramName + " Should not Contain More than "
							+ maxLength + " values separated by comma.";
				}
			} else {
				if (!isNulable) {
					errMsg = paramName + " is Mandatory.";
					AppLog.end(sessionDetails);
					return errMsg;
				}
			}
		} catch (Exception ex) {
			errMsg = "Invalid " + paramName + ".";
			AppLog.error(sessionDetails, ex);
		}
		AppLog.end(sessionDetails);
		return errMsg;
	}

	/**
	 * <p>
	 * This method is to check if a file being uploaded is valid on the basis of
	 * regular expression written in the property file with key
	 * <code>checkUserIdRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param fileType
	 * @param fileSize
	 * @return
	 */
	public static String checkValidFile(String paramName, String fileType,
			long fileSize) {
		AppLog.begin();
		String errMsg = "";
		String errMsgSize = "";
		String errMsgType = "";
		boolean isValid = false;
		long maxFileSize = 1 * 1024 * 1024;
		try {
			if (null != paramName) {
				if (paramName.indexOf("\\") != -1) {
					paramName = paramName
							.substring(paramName.lastIndexOf("\\"));
				}
				if (paramName.indexOf("/") != -1) {
					paramName = paramName.substring(paramName.lastIndexOf("/"));
				}

			}
			if (null != fileType) {

				List<String> allowdFileTypeList = new ArrayList<String>(Arrays
						.asList(PropertyUtil.getProperty("allowdFileType")
								.toLowerCase().split(",")));

				for (int i = 0; i < allowdFileTypeList.size(); i++) {
					String token = (String) allowdFileTypeList.get(i);
					if (fileType.toLowerCase().indexOf(token.trim()) != -1) {
						isValid = true;
					}

				}
				if (!isValid) {
					errMsgSize = PropertyUtil
							.getProperty("allowdFileTypeErrMsg");
				}
				if (null != PropertyUtil.getProperty("allowdFileSize")
						&& !"".equals(PropertyUtil
								.getProperty("allowdFileSize"))) {
					maxFileSize = Integer.parseInt(PropertyUtil
							.getProperty("allowdFileSize")) * 1024 * 1024;
				}
				if (fileSize > maxFileSize) {
					errMsgType = "The Uploaded file " + paramName + " "
							+ PropertyUtil.getProperty("allowdFileSizeErrMsg");
				}
			}
		} catch (Exception ex) {
			errMsg = "An Error Occurred While Uploading The file" + paramName
					+ ".";
			AppLog.error(ex);
		}
		if (!"".equals(errMsgSize) && !"".equals(errMsgType)) {
			errMsg = "1." + errMsgSize + " 2." + errMsgType;
		} else {
			errMsg = errMsgSize + errMsgType;
		}
		AppLog.end();
		return errMsg.trim();
	}

	/**
	 * <p>
	 * This method is to check if a file being uploaded is valid on the basis of
	 * regular expression written in the property file with key
	 * <code>checkUserIdRegex</code>.
	 * </p>
	 * 
	 * @param paramName
	 * @param fileType
	 * @param fileSize
	 * @param sessionDetails
	 * @return
	 */
	public static String checkValidFile(String paramName, String fileType,
			long fileSize, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String errMsg = "";
		String errMsgSize = "";
		String errMsgType = "";
		boolean isValid = false;
		long maxFileSize = 1 * 1024 * 1024;
		try {
			if (null != paramName) {
				if (paramName.indexOf("\\") != -1) {
					paramName = paramName
							.substring(paramName.lastIndexOf("\\"));
				}
				if (paramName.indexOf("/") != -1) {
					paramName = paramName.substring(paramName.lastIndexOf("/"));
				}

			}
			if (null != fileType) {

				List<String> allowdFileTypeList = new ArrayList<String>(Arrays
						.asList(PropertyUtil.getProperty("allowdFileType")
								.toLowerCase().split(",")));

				for (int i = 0; i < allowdFileTypeList.size(); i++) {
					String token = (String) allowdFileTypeList.get(i);
					if (fileType.toLowerCase().indexOf(token.trim()) != -1) {
						isValid = true;
					}

				}
				if (!isValid) {
					errMsgSize = PropertyUtil
							.getProperty("allowdFileTypeErrMsg");
				}
				if (null != PropertyUtil.getProperty("allowdFileSize")
						&& !"".equals(PropertyUtil
								.getProperty("allowdFileSize"))) {
					maxFileSize = Integer.parseInt(PropertyUtil
							.getProperty("allowdFileSize")) * 1024 * 1024;
				}
				if (fileSize > maxFileSize) {
					errMsgType = "The Uploaded file " + paramName + " "
							+ PropertyUtil.getProperty("allowdFileSizeErrMsg");
				}
			}
		} catch (Exception ex) {
			errMsg = "An Error Occurred While Uploading The file" + paramName
					+ ".";
			AppLog.error(sessionDetails, ex);
		}
		if (!"".equals(errMsgSize) && !"".equals(errMsgType)) {
			errMsg = "1." + errMsgSize + " 2." + errMsgType;
		} else {
			errMsg = errMsgSize + errMsgType;
		}
		AppLog.end(sessionDetails);
		return errMsg.trim();
	}

	/**
	 * <p>
	 * This method is to validate payment amount for the Payment Posting web
	 * service.It returns true, in case of valid payment amount.Otherwise,
	 * false.
	 * </p>
	 * 
	 * @param payAmount
	 * @return
	 * @since 08-01-2014
	 * @author 556885
	 */
	public static boolean isValidPayAmount(String payAmount, String minAmount) {
		AppLog.begin();
		boolean isValid = false;
		if (null != payAmount && !"".equalsIgnoreCase(payAmount.trim())) {
			try {
				payAmount = payAmount.trim().replaceAll("^0+", "");
				if (null != payAmount && !"".equalsIgnoreCase(payAmount.trim())) {

					Pattern amountPattern = Pattern.compile(PropertyUtil
							.getProperty("CHECK_PAYMENT_AMOUNT_REGEX"));
					Matcher amountMatcher = amountPattern.matcher(payAmount
							.trim());
					if (amountMatcher.find()) {
						if (null != minAmount
								&& Double.parseDouble(payAmount.trim()) >= Double
										.parseDouble(minAmount.trim())) {
							isValid = true;
						}
					}
				}
			} catch (Exception e) {
				AppLog.fatal(e);
			}
		}
		AppLog.debugInfo("Valid Pay Amount : " + isValid);
		AppLog.end();
		return isValid;
	}

	/**
	 * <p>
	 * This method is to validate payment date for Payment Posting web service
	 * for third party vendors.Payment Date should be in format dd-MMM-yyyy.
	 * </p>
	 * 
	 * @param payDate
	 * @return
	 * @author 556885
	 * @since 08-01-2014
	 */
	public static boolean isValidPayDate(String payDate) {
		AppLog.begin();
		boolean isValidPayDate = false;
		Date parsedPayDate;
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		if (null != payDate && !"".equalsIgnoreCase(payDate.trim())) {
			try {
				format.setLenient(false);
				parsedPayDate = format.parse(payDate.trim());
				AppLog.debugInfo("Pay Date:" + payDate.trim()
						+ ":Parsed Pay Date:" + parsedPayDate);
				/**
				 * to check if payment date is less than future date
				 */
				if ((parsedPayDate.compareTo(currentDate) <= 0)) {
					isValidPayDate = true;
				}
			} catch (ParseException e) {
				AppLog.fatal(e);
			}
		}
		AppLog.end();
		return isValidPayDate;
	}

	/**
	 * <p>
	 * This method is to check Validate date format with regular expression on
	 * the basis of regular expression written in the property file with key
	 * <code>checkDateRegex</code>.
	 * </p>
	 * 
	 * 
	 * @param inputParam
	 * @param regexExp
	 * @return true valid date format, false invalid date format
	 */
	public static boolean validateDateDDMMYYYY(String inputParam,
			String regexExp) {
		Pattern pattern = Pattern.compile(regexExp);

		Matcher matcher = pattern.matcher(inputParam);

		if (matcher.matches()) {

			matcher.reset();

			if (matcher.find()) {

				String day = matcher.group(1);
				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(3));

				if ("31".equals(day)
						&& ("4".equals(month) || "6".equals(month)
								|| "9".equals(month) || "11".equals(month)
								|| "04".equals(month) || "06".equals(month) || "09"
								.equals(month))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
				} else if ("2".equals(month) || "02".equals(month)) {
					// leap year
					if (year % 4 == 0) {
						if ("30".equals(day) || "31".equals(day)) {
							return false;
						} else {
							return true;
						}
					} else {
						if ("29".equals(day) || "30".equals(day)
								|| "31".equals(day)) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
