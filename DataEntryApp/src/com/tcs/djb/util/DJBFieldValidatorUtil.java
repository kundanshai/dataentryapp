/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java   09 Oct 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tcs.djb.constants.DJBConstants;

/**
 * <p>
 * This is the Utility class for input field validation used to validate various
 * input parameters on the basis of regular expression written in the property
 * file.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @see Matcher
 * @see Pattern
 */

public class DJBFieldValidatorUtil {

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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkAllowedSpecialCharRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkAlphabetRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkAlphaNumericRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkAmountRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkBillCycleRegex)) {
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
					if (!validateDateDDMMYYYY(paramValue,
							DJBConstants.checkDateRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkEmailRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkFileNameRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkHTTPParameterValueRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkNameRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkNumericRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkURLRegex)) {
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
					if (!checkPresenceOfParticularChar(paramValue,
							DJBConstants.checkUserIdRegex)) {
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

	// public static String checkValidFile(String paramName,
	// String fileType, long fileSize) {
	// AppLog.begin();
	// String errMsg = "";
	// String errMsgSize = "";
	// String errMsgType = "";
	// boolean isValid = false;
	// long maxFileSize = 1 * 1024 * 1024;
	// try {
	// if (null != paramName) {
	// if (paramName.indexOf("\\") != -1) {
	// paramName = paramName
	// .substring(paramName.lastIndexOf("\\"));
	// }
	// if (paramName.indexOf("/") != -1) {
	// paramName = paramName.substring(paramName.lastIndexOf("/"));
	// }
	//
	// }
	// if (null != fileType) {
	//
	// List<String> allowdFileTypeList = new ArrayList<String>(Arrays
	// .asList(DJBConstants.allowdFileType.toLowerCase().split(",")));
	//
	// for (int i = 0; i < allowdFileTypeList.size(); i++) {
	// String token = (String) allowdFileTypeList.get(i);
	// // System.out.println("allowdFileTypeTemp::"
	// // + allowdFileTypeTemp + "token::" + token
	// // + "::fileType::" + fileType);
	// if (fileType.toLowerCase().indexOf(token.trim()) != -1) {
	// isValid = true;
	// }
	//
	// }
	// if (!isValid) {
	// errMsgSize = DJBConstants.allowdFileTypeErrMsg;
	// // System.out.println(errMsg);
	// // return errMsg;
	// }
	// if (null != DJBConstants.allowdFileSize &&
	// !"".equals(DJBConstants.allowdFileSize)) {
	// maxFileSize = Integer.parseInt(DJBConstants.allowdFileSize) * 1024 *
	// 1024;
	// }
	// if (fileSize > maxFileSize) {
	// errMsgType = "The Uploaded file " + paramName + " "
	// + DJBConstants.allowdFileSizeErrMsg;
	// // System.out.println(errMsg);
	// // return errMsg;
	// }
	// }
	// // System.out.println("File Name::" + paramName + "::File Type::"
	// // + fileType + "::fileSize::" + fileSize + "::Allowed::"
	// // + maxFileSize);
	// } catch (Exception ex) {
	// errMsg = "An Error Occurred While Uploading The file" + paramName
	// + ".";
	// // System.out.println(errMsg);
	// AppLog.error(ex);
	// // ex.printStackTrace();
	// }
	// if (!"".equals(errMsgSize) && !"".equals(errMsgType)) {
	// errMsg = "1." + errMsgSize + " 2." + errMsgType;
	// } else {
	// errMsg = errMsgSize + errMsgType;
	// }
	// AppLog.end();
	// return errMsg.trim();
	// }

}
