/************************************************************************************************************
 * @(#) UtilityForAll.java   30 Jul 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.rms.util.EncUtil;

import Decoder.BASE64Encoder;

/**
 * <p>
 * Utility class for Data Entry Application.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 30-07-2012
 * @history 16-02-2016 Rajib Hazarika(Tata Consultancy Services) added method
 *          {@link #generateToken} as per JTrac DJB-4313
 * 
 */
public class UtilityForAll {

	/* Start:: Added by Karthick K on 13-12-2013 on J-track id:: DJB-2095 */

	/**
	 * 
	 */
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	/**
	 * 
	 */
	public static final Pattern pattern = Pattern.compile("^(?:" + _255
			+ "\\.){3}" + _255 + "$");

	/* End:: Added by Karthick K on 13-12-2013 on J-track id:: DJB-2095 */

	/**
	 * <p>
	 * This method is used to check if a date is a Future Date passed in
	 * dd/MM/yyyy format.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static int checkFutureDate(String date) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String formatCurrDate = sdf.format(new java.util.Date());

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.set(Integer.parseInt(formatCurrDate.substring(6, 10)), Integer
				.parseInt(formatCurrDate.substring(3, 5)), Integer
				.parseInt(formatCurrDate.substring(0, 2)));
		c2.set(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date
				.substring(3, 5)), Integer.parseInt(date.substring(0, 2)));

		if (c2.before(c1)) {
			return -1;
		} else if (c2.after(c1)) {
			return 1;
		} else {
			return 0;
		}
	}

	
	/**
	 * <p>
	 * This method is used to compare dates. Returns -1 if "firstDate" is
	 * earlier than "secondDate" Returns +1 if "firstDate" is later than
	 * "secondDate" Returns 0 if both dates are same
	 * </p>
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int compareDates(String firstDate, String secondDate) {
		if (firstDate.length() != 10 || secondDate.length() != 10) {
			return 2;
		}
		int firstDay = Integer.parseInt(firstDate.substring(0, 2)); // day
		int firstMon = Integer.parseInt(firstDate.substring(3, 5)); // month
		int firstYear = Integer.parseInt(firstDate.substring(6, 10)); // year

		int secondDay = Integer.parseInt(secondDate.substring(0, 2)); // day
		int secondMon = Integer.parseInt(secondDate.substring(3, 5)); // month
		int secondYear = Integer.parseInt(secondDate.substring(6, 10)); // year

		GregorianCalendar gDateFirst = new GregorianCalendar(firstYear,
				firstMon, firstDay);
		GregorianCalendar gDateSecond = new GregorianCalendar(secondYear,
				secondMon, secondDay);

		Date dateFirst = gDateFirst.getTime();
		Date dateSecond = gDateSecond.getTime();

		if (dateFirst.compareTo(dateSecond) == 0) {
			return 0;
		} else if (dateFirst.compareTo(dateSecond) < 0) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * <p>
	 * This method is used to compare two heads
	 * </p>
	 * 
	 * @param firstHead
	 * @param secondHead
	 * @return
	 */
	public static boolean compareHeads(String firstHead, String secondHead) {
		if (firstHead.compareTo(secondHead) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * This method replaces special characters like & to html encoded form.
	 * </p>
	 * 
	 * @param inputString
	 * @return
	 */
	public static String encodeString(String inputString) {
		String newString = null;
		if (null != inputString) {
			newString = inputString.replaceAll("&", "&amp;");
		}
		return newString;
	}

	/**
	 * <p>
	 * This method is used to format a Date to dd/MM/yyyy format.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		String formattedDate = null;
		if (date != null) {
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			formattedDate = sdf.format(date);
		}
		return formattedDate;
	}

	/**
	 * <p>
	 * This method is used to generate Auth Cookie with Base 64.
	 * </p>
	 * 
	 * @param userId
	 * @param password
	 * @return authCookie
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 04-12-2013
	 */
	public static String generateAuthCookie(String userId, String password) {
		AppLog.begin();
		String authCookie = null;
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			if (null != userId && !"".equalsIgnoreCase(userId.trim())
					&& null != password
					&& !"".equalsIgnoreCase(password.trim())) {
				try {
					String inputString = userId.toUpperCase() + ":"
							+ password.trim();
					authCookie = encoder.encodeBuffer(inputString.getBytes());
				} catch (Exception e) {
					AppLog.error(e);
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return authCookie;
	}

	/**
	 * <p>
	 * This method is used to get the token to be used for authentication of
	 * request to download bill using consumer portal view anonymous bill
	 * section as per JTrac DJB-4313
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static String generateToken(String id) {
		AppLog.begin();
		String tokenGenerated = null;
		try {
			if (null != id && !"".equalsIgnoreCase(id.trim())) {
				try {
					tokenGenerated = EncUtil.getSha512(id.trim()
							+ DJBConstants.SALT_PARAM_KEY
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()).toString()));
				} catch (Exception e) {
					AppLog.error(e);
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return tokenGenerated;
	}

	/**
	 * <p>
	 * This method returns the current financial year
	 * </p>
	 * 
	 * @return
	 */
	public static String getCurrentFinYear() {
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(c
				.getTime()));
		int month = Integer.parseInt(new SimpleDateFormat("M").format(c
				.getTime()));
		String finYear = null;
		if (month < 4) {
			finYear = Integer.valueOf(year - 1).toString() + "-"
					+ Integer.valueOf(year).toString();
		}
		if (month > 3) {
			finYear = Integer.valueOf(year).toString() + "-"
					+ Integer.valueOf(year + 1).toString();
		}
		return finYear;
	}

	/**
	 * <p>
	 * This method returns the current year in String format
	 * </p>
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		String currentYear = "";
		try {
			String todaysDate = null;
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			todaysDate = sdf.format(new java.util.Date());
			currentYear = todaysDate.substring(6);
		} catch (Exception e) {

		}
		return currentYear;
	}

	/**
	 * <p>
	 * This method returns the financial year of a particular date
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String getFinYearFromDate(String date) {
		if (date == null || date.length() < 10) {
			return getFinYearFromDate(getTodaysDate());
		}
		int year = Integer.parseInt(date.substring(6, 10));
		int month = Integer.parseInt(date.substring(3, 5));
		String finYear = null;
		if (month < 4) {
			finYear = Integer.valueOf(year - 1).toString() + "-"
					+ Integer.valueOf(year).toString();
		}
		if (month > 3) {
			finYear = Integer.valueOf(year).toString() + "-"
					+ Integer.valueOf(year + 1).toString();
		}

		return finYear;
	}

	/**
	 * <p>
	 * This method is used to get formatted decimal strings.  
	 * </p>
	 * 
	 * @param dValue
	 * @return
	 */
	public static String getFormattedDecimalString(double dValue) {
		// double d = Double.parseDouble(dValue);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);
		return nf.format(dValue);
	}

	/**
	 * <p>
	 * This method is used to get formatted decimal strings.  
	 * </p>
	 * 
	 * @param dValue
	 * @return
	 */
	public static String getFormattedDecimalString(String dValue) {
		if (dValue == null || dValue.length() < 1) {
			return dValue;
		}
		double d = Double.parseDouble(dValue);
		return getFormattedDecimalString(d);
	}

	/**
	 * <p>
	 * This method construct full name from firstName, midleName,lastName
	 * </p>
	 * 
	 * @param String
	 *            firstName, String midleName,String lastName
	 * @return String
	 */
	public static String getFullName(String firstName, String midleName,
			String lastName) {
		String fullName = "";
		if (null != firstName && !"".equals(firstName)) {
			fullName = firstName.trim();
		}
		if (null != midleName && !"".equals(midleName)) {
			fullName = fullName + " " + midleName.trim();
		}
		if (null != lastName && !"".equals(lastName)) {
			fullName = fullName + " " + lastName.trim();
		}
		return fullName;
	}

	/**
	 * <p>
	 * This method is used to get integer value of a given string.
	 * </p>
	 * 
	 * @param strValue
	 * @return
	 */
	public static int getIntValueOfString(String strValue) {
		int intValue = -1;
		try {
			intValue = Integer.parseInt(strValue);
		} catch (Exception e) {
			intValue = -1;
		}
		return intValue;
	}

	/**
	 * <p>
	 * This method is used to get IP address from a given request.
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpFromRequest(HttpServletRequest request) {
		System.out.println("ip?");

		String ip = "Could not Parse.";
		try {
			boolean found = false;
			if (null != request) {
				ip = request.getHeader("x-forwarded-for");
				if (null == ip) {
					ip = request.getHeader("X-FORWARDED-FOR");
				}
				if (null == ip) {
					ip = request.getHeader("X-Forwarded-For");
				}
				System.out.println("" + ip + request.toString()
						+ "\nRemoteUser::" + request.getRemoteUser()
						+ "\nLocalAddr::" + request.getLocalAddr()
						+ "\nQueryString::" + request.getQueryString()
						+ "\nRemoteHost::" + request.getRemoteHost());

				if (null != ip) {
					StringTokenizer tokenizer = new StringTokenizer(ip);
					while (null != tokenizer
							&& null != tokenizer.nextToken(",")) {
						ip = tokenizer.nextToken().trim();
						if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					ip = request.getRemoteAddr();
				}

			}
		} catch (Exception e) {
			// AppLog.fatal(e);
		}
		return ip;
	}
	
	/**
	 * <p>
	 * This function returns relative Financial Years. If negative count is sent
	 * as a parameter then previous financial years are returned and if positive
	 * count is sent as a parameter then proceeding financial years are
	 * returned.
	 * </p>
	 * 
	 * @param finYear
	 * @param count
	 * @return
	 */
	public static String getRelativeFinYear(String finYear, int count) {
		String newFinYear = null;
		try {
			String startingCurrentFinYear = finYear.substring(0, 4);
			String endingCurrentFinYear = finYear.substring(5);

			int newStartingFinYear = Integer.parseInt(startingCurrentFinYear)
					+ count;
			int newEndingFinYear = Integer.parseInt(endingCurrentFinYear)
					+ count;

			newFinYear = String.valueOf(newStartingFinYear) + "-"
					+ String.valueOf(newEndingFinYear);
		} catch (Exception e) {

		}
		return newFinYear;
	}

	/**
	 * <p>
	 * This method returns a date in java.sql.Date format taking the
	 * parameterized date in String format.
	 * </p>
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date getSqlDate(String strDate) {
		java.sql.Date sqlDate = null;
		try {
			if (strDate != null && strDate.length() > 0) {
				sqlDate = new java.sql.Date((parseDate(strDate)).getTime());
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return sqlDate;
	}

	/**
	 * <p>
	 * This method checks if the input string is neither null nor blank.
	 * </p>
	 * 
	 * @param inputValue
	 * @return
	 */
	public static boolean isEmpty(String inputValue) {
		boolean isEmpty = true;
		try {
			if (null != inputValue && inputValue.trim().length() > 0) {
				isEmpty = false;
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return isEmpty;
	}

	/**
	 * <p>
	 * This method is to change date format. Input parameter is expected to be
	 * received in input date format. The output string gets changed to output
	 * date format.
	 * </p>
	 * 
	 * @param inputValue
	 * @param inputDateFormat
	 * @param outputDateFormat
	 * @return
	 */
	public static String changeDateFormat(String inputValue,
			String inputDateFormat, String outputDateFormat) {
		String dateString = DJBConstants.ERROR_FLG;
		Date date;
		DateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
		DateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
		try {
			if (!isEmpty(inputValue) && !isEmpty(inputDateFormat)
					&& !isEmpty(outputDateFormat)) {
				date = inputFormat.parse(inputValue.trim());
				dateString = outputFormat.format(date);
			}
		} catch (ParseException ex) {
			// ex.printStackTrace();
		}
		return dateString;
	}

	/**
	 * <p>
	 * This method returns the current date in dd/MM/yyyy format.
	 * </p>
	 * 
	 * @return todaysDate in String format
	 * 
	 */
	public static String getTodaysDate() {
		String todaysDate = null;
		try {
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			todaysDate = sdf.format(new java.util.Date());
		} catch (Exception e) {

		}
		return todaysDate;
	}

	/**
	 * <p>
	 * This method is used to parse ipV4 to long data type.
	 * </p>
	 * 
	 * @param ip
	 * @return
	 */
	public static long ipV4ToLong(String ip) {
		String[] octets = ip.split("\\.");
		return (Long.parseLong(octets[0]) << 24)
				+ (Integer.parseInt(octets[1]) << 16)
				+ (Integer.parseInt(octets[2]) << 8)
				+ Integer.parseInt(octets[3]);
	}

	/**
	 * <p>
	 * This method is used to check if IPv4 is private by checking its range
	 * between particular ranges of IPv4.
	 * </p>
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isIPv4Private(String ip) {
		long longIp = ipV4ToLong(ip);
		return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
				|| (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
				|| longIp >= ipV4ToLong("192.168.0.0")
				&& longIp <= ipV4ToLong("192.168.255.255");
	}

	/**
	 * <p>
	 * This method is used to check if IPv4 is valid.
	 * </p>
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isIPv4Valid(String ip) {
		return pattern.matcher(ip).matches();
	}

	/**
	 * <p>
	 * This method is used to Check if a input String is numeric or not.
	 * </p>
	 * 
	 * @param inputString
	 * @return true or false
	 */
	public static boolean isNumeric(String inputString) {
		try {
			Integer.parseInt(inputString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <p>
	 * This method is used to add lpad to a string.
	 * </p>
	 * 
	 * @param subject
	 * @param lengthDesired
	 * @param c
	 * @return
	 */
	public static String lpad(String subject, int lengthDesired, char c) {
		int stringLength = subject.length();
		String returnSubject = null;
		for (int i = 0; i < lengthDesired - stringLength; i++) {
			returnSubject = Character.toString(c) + subject;
		}
		return returnSubject;
	}

	/**
	 * <p>
	 * This method is used to parse Date in dd/MM/yyyy format.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		Date parsedDate = null;
		try {
			if (date != null && date.length() > 0) {
				String pattern = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				parsedDate = sdf.parse(date);
			}
		} catch (Exception e) {

		}
		return parsedDate;
	}

	/**
	 * <p>
	 * This method is used to parse Date in YYYY-MM-DD format.
	 * </p>
	 * 
	 * @param date
	 * @return date in YYYY-MM-DD format
	 */
	public static String parseDateYYYYMMDD(String dateString) {
		String dateInYYYYMMDD = null;
		try {
			if (null != dateString) {
				String yyyy = dateString
						.substring(dateString.lastIndexOf('/') + 1);
				dateString = dateString.substring(0, dateString
						.lastIndexOf('/'));
				String mm = dateString
						.substring(dateString.lastIndexOf('/') + 1);
				dateString = dateString.substring(0, dateString
						.lastIndexOf('/'));
				String dd = dateString;
				dateInYYYYMMDD = yyyy + "-" + mm + "-" + dd;
			}
		} catch (Exception e) {

		}
		return dateInYYYYMMDD;
	}

	/**
	 * <p>
	 * Shift Date String in dd/mm/yyyy format to no Of Day to Shift.
	 * </p>
	 * 
	 * @param date
	 * @param noOfDaytoShift
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String shiftDate(String dateSring, int noOfDaytoShift) {
		String shiftedDate = null;
		try {
			String txtDate = dateSring;
			if (null != dateSring && dateSring.length() == 10) {
				String dd = txtDate.substring(0, 2);
				String mm = txtDate.substring(3, 5);
				String yyyy = txtDate.substring(6, 10);
				long mSeconds = (new Date(Integer.parseInt(yyyy), Integer
						.parseInt(mm) - 1, Integer.parseInt(dd))).getTime();
				Date objDate = new Date();
				long mSecondsShifted = mSeconds + 86400000 * noOfDaytoShift;
				System.out.println(mSeconds + "+" + 86400000 * noOfDaytoShift
						+ "=" + mSecondsShifted);
				objDate.setTime(mSecondsShifted);
				int mmInt = objDate.getMonth() + 1;
				int ddInt = objDate.getDate();
				if (mmInt < 10) {
					mm = "0" + mmInt;
				}
				if (ddInt < 10) {
					dd = "0" + ddInt;
				}
				txtDate = dd + '/' + mm + '/' + yyyy;
				shiftedDate = txtDate;
			}
		} catch (Exception e) {

		}
		return shiftedDate;
	}

	/**
	 * <p>
	 * This method strips any string of the quotes
	 * </p>
	 * 
	 * @param subject
	 * @return
	 * 
	 */
	public static String stripQuotes(String subject) {
		StringBuffer newSubject = new StringBuffer();
		if (subject != null) {
			int c1;
			char c2;
			for (int i = 0; i < subject.length(); i++) {
				c1 = subject.charAt(i);
				c2 = subject.charAt(i);
				if ((c1 > 64 && c1 < 91) || (c1 == 32) || (c1 > 47 && c1 < 58)
						|| (c1 > 96 && c1 < 123)) {
					newSubject.append(c2);
				}
			}
		}
		return (newSubject.toString());
	}

	/**
	 * <p>
	 * This method strips any sort of alpha-numeric characters except alphabets.
	 * </p>
	 * 
	 * @param subject
	 * @return
	 * 
	 */
	public static String stripString(String subject) {
		StringBuffer newSubject = new StringBuffer();
		if (subject != null) {
			int c1;
			char c2;
			for (int i = 0; i < subject.length(); i++) {
				c1 = subject.charAt(i);
				c2 = subject.charAt(i);
				if ((c1 > 64 && c1 < 91) || (c1 > 47 && c1 < 58)
						|| (c1 > 96 && c1 < 123)) {
					newSubject.append(c2);
				}
			}
		}
		return (newSubject.toString());
	}

	/**
	 * <p>
	 * This method converts string to date format dd/MM/yyyy .
	 * </p>
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date convertStringToDate(String dateString) {
		Date date = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = df.parse(dateString);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return date;
	}

	/**
	 * <p>
	 * This method is used to validate Date String in dd/mm/yyyy format.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date) {
		if (date.length() != 10) {
			return false;
		}

		int day = Integer.parseInt(date.substring(0, 2)); // day
		int mon = Integer.parseInt(date.substring(3, 5)); // month
		int year = Integer.parseInt(date.substring(6, 10)); // year

		if (mon < 1 || mon > 12 || day < 1 || day > 31 || year < 1000) {
			return false;
		}

		if ((mon == 4 || mon == 6 || mon == 9 || mon == 11) && day == 31) {
			return false;
		}

		if ((day > 29 || (day == 29 && (year % 4 == 0))) && (mon == 2)) {
			return false;
		}
		return true;
	}
	/* Start:: Added by Karthick K on 13-12-2013 on J-track id:: DJB-2095 */

	/* End:: Added by Karthick K on 13-12-2013 on J-track id:: DJB-2095 */
}