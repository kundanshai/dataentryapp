/************************************************************************************************************
 * @(#) UtilityForAll.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * This is the Utility class that contain all the common method for various task
 * which includes:<br>
 * Check a Future Date future date i.e. check if the passed date is a future
 * date using {@link#checkFutureDate}<br>
 * Compares to date string using {@link#compareDates}<br>
 * Format a date string in dd/MM/yyyy format using {@link#formatDate}<br>
 * Get current financial year using {@link#getCurrentFinYear}<br>
 * Get Financial Year From Date using {@link #getFinYearFromDate(String)}<br>
 * Get Formatted Decimal String from Double using
 * {@link #getFormattedDecimalString(double)} <br>
 * Get Formatted Decimal String from String using
 * {@link #getFormattedDecimalString(String)} <br>
 * Get Full Name using {@link #getFullName(String, String, String)}<br>
 * Get IP From Request using {@link #getIpFromRequest(HttpServletRequest)}<br>
 * Get Random Token of desired length using {@link #getRandomToken(int)}<br>
 * Get Relative Financial Year using {@link #getRelativeFinYear(String, int)}<br>
 * Get Request Id of desired length using {@link #getReqId(String, int)}<br>
 * Get SQL Date from String using {@link #getSqlDate(String)}<br>
 * Get Todays Date in dd/MM/yyyy format using {@link #getTodaysDate()}<br>
 * Get URL from HTTP Request using {@link #getUrl(HttpServletRequest)}<br>
 * Check id a string is Empty String using {@link #isEmptyString(String)}<br>
 * Check if a String is Found In Delimited String using
 * {@link #isFoundInDelimitedString(String, String, String)}<br>
 * Check if SQL In Parameter list is Greater Than 1000 using
 * {@link #isInParameterGreaterThan1000(String)}<br>
 * Construct Valid SQL IN Parameter using {@link #makeValidINParameter(String)}<br>
 * COnstruct Valid In Parameter where list is Greater Than 1000 using
 * {@link #makeValidInParameterGreaterThan1000(String, String)}<br>
 * Parse a Date in dd/MM/yyyy format using {@link #parseDate(String)}<br>
 * Strips quotes from any string using {@link #stripQuotes(String)}<br>
 * To trips any sort of alpha-numeric characters except alphabets using
 * {@link #stripString(String)}<br>
 * Validate Date in dd/mm/yyyy format using {@link #validateDate(String)}
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-09-2014
 */
public class UtilityForAll {

	/**
	 * 
	 */
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	/**
	 * 
	 */
	public static final Pattern pattern = Pattern.compile("^(?:" + _255
			+ "\\.){3}" + _255 + "$");

	/**
	 * String used to create a random string
	 */
	private final static String RANDOM_TOKEN = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	/**
	 * <p>
	 * Checks if a date is a future date by comparing with system date. The date
	 * parameter must be in dd/MM/yyyy format
	 * </p>
	 * 
	 * @param date
	 *            string to be compared
	 * @return -1 if date is before todays date, 0 if its todays date, 1 if
	 *         after todays date
	 */
	public static int checkFutureDate(String date) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String formatCurrDate = sdf.format(new java.util.Date());

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		/* Deducting 1 from month as month start from 0 in Calendar API */
		c1.set(Integer.parseInt(formatCurrDate.substring(6, 10)), Integer
				.parseInt(formatCurrDate.substring(3, 5)) - 1, Integer
				.parseInt(formatCurrDate.substring(0, 2)));
		c2.set(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date
				.substring(3, 5)) - 1, Integer.parseInt(date.substring(0, 2)));
		AppLog.debugInfo("@Param::" + date + "::Current Date Time::"
				+ c1.getTime());
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
	 * Checks if a date is a future date by comparing with system date. The date
	 * parameter must be in dd/MM/yyyy format
	 * </p>
	 * 
	 * @param date
	 *            string to be compared
	 * @param sessionDetails
	 * @return -1 if date is before todays date, 0 if its todays date, 1 if
	 *         after todays date
	 */
	public static int checkFutureDate(String date, SessionDetails sessionDetails) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String formatCurrDate = sdf.format(new java.util.Date());

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		/* Deducting 1 from month as month start from 0 in Calendar API */
		c1.set(Integer.parseInt(formatCurrDate.substring(6, 10)), Integer
				.parseInt(formatCurrDate.substring(3, 5)) - 1, Integer
				.parseInt(formatCurrDate.substring(0, 2)));
		c2.set(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date
				.substring(3, 5)) - 1, Integer.parseInt(date.substring(0, 2)));
		AppLog.debugInfo(sessionDetails, "@Param::" + date
				+ "::Current Date Time::" + c1.getTime());
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
	 * Compares to date string. The date parameter must be in dd/MM/yyyy format
	 * </p>
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return -1 if <code>firstDate</code> is earlier than
	 *         <code>secondDate</code> Returns +1 if <code>firstDate</code> is
	 *         later than <code>secondDate</code> Returns 0 if both dates are
	 *         same
	 */
	public static int compareDates(String firstDate, String secondDate) {
		if (firstDate.length() != 10 || secondDate.length() != 10) {
			return 2;
		}
		int firstDay = Integer.parseInt(firstDate.substring(0, 2)); // day
		/* Deducting 1 as month start from 0 in Calendar API */
		int firstMonth = Integer.parseInt(firstDate.substring(3, 5)) - 1; // month
		int firstYear = Integer.parseInt(firstDate.substring(6, 10)); // year

		int secondDay = Integer.parseInt(secondDate.substring(0, 2)); // day
		/* Deducting 1 as month start from 0 in Calendar API */
		int secondMonth = Integer.parseInt(secondDate.substring(3, 5)) - 1; // month
		int second_year = Integer.parseInt(secondDate.substring(6, 10)); // year

		GregorianCalendar gdateFirst = new GregorianCalendar(firstYear,
				firstMonth, firstDay);
		GregorianCalendar gdateSecond = new GregorianCalendar(second_year,
				secondMonth, secondDay);

		Date dateFirst = gdateFirst.getTime();
		Date dateSecond = gdateSecond.getTime();
		AppLog.debugInfo("@Param::firstDate" + firstDate + "::secondDate::"
				+ secondDate + "::dateFirst::" + dateFirst + ":;dateSecond::"
				+ dateSecond);
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
	 * Compares to date string. The date parameter must be in dd/MM/yyyy format
	 * </p>
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @param sessionDetails
	 * @return -1 if <code>firstDate</code> is earlier than
	 *         <code>secondDate</code> Returns +1 if <code>firstDate</code> is
	 *         later than <code>secondDate</code> Returns 0 if both dates are
	 *         same
	 */
	public static int compareDates(String firstDate, String secondDate,
			SessionDetails sessionDetails) {
		if (firstDate.length() != 10 || secondDate.length() != 10) {
			return 2;
		}
		int firstDay = Integer.parseInt(firstDate.substring(0, 2)); // day
		/* Deducting 1 as month start from 0 in Calendar API */
		int firstMonth = Integer.parseInt(firstDate.substring(3, 5)) - 1; // month
		int firstYear = Integer.parseInt(firstDate.substring(6, 10)); // year

		int secondDay = Integer.parseInt(secondDate.substring(0, 2)); // day
		/* Deducting 1 as month start from 0 in Calendar API */
		int secondMonth = Integer.parseInt(secondDate.substring(3, 5)) - 1; // month
		int second_year = Integer.parseInt(secondDate.substring(6, 10)); // year

		GregorianCalendar gdateFirst = new GregorianCalendar(firstYear,
				firstMonth, firstDay);
		GregorianCalendar gdateSecond = new GregorianCalendar(second_year,
				secondMonth, secondDay);

		Date dateFirst = gdateFirst.getTime();
		Date dateSecond = gdateSecond.getTime();
		AppLog.debugInfo(sessionDetails, "@Param::firstDate" + firstDate
				+ "::secondDate::" + secondDate + "::dateFirst::" + dateFirst
				+ "::dateSecond::" + dateSecond);
		if (dateFirst.compareTo(dateSecond) == 0) {
			return 0;
		} else if (dateFirst.compareTo(dateSecond) < 0) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * compare Heads
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
	 * Format a date in dd/MM/yyyy format
	 * </p>
	 * 
	 * @see Date
	 * @param date
	 * @return formated date
	 */
	public static String formatDate(Date date) {
		if (date != null) {
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			String formattedDate = sdf.format(date);
			return formattedDate;
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Check if Current Day in SUN,MON format.
	 * </p>
	 * 
	 * 
	 * @return DAY in String format
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 19-01-2015
	 * 
	 */
	public static String getCurrentDay() {
		String day = null;
		try {
			Date now = new Date();
			// EEE gives short day names, EEEE would be full length.
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale
					.getDefault());
			day = dateFormat.format(now);

		} catch (Exception e) {
			AppLog.error(e);
		}
		return day;
	}

	/**
	 * <p>
	 * Check if Current Day in SUN,MON format.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @return DAY in String format
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 19-01-2015
	 * 
	 */
	public static String getCurrentDay(SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String day = null;
		try {
			Date now = new Date();
			// EEE gives short day names, EEEE would be full length.
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale
					.getDefault());
			day = dateFormat.format(now);
			AppLog.debugInfo(sessionDetails, "dateFormat::" + dateFormat
					+ "::CurrentDay::" + day);
		} catch (Exception e) {
			AppLog.error(sessionDetails, e);
		}
		AppLog.end();
		return day;
	}

	/**
	 * <p>
	 * This method returns the current financial year
	 * </p>
	 * 
	 * @return current financial year
	 * 
	 */
	public static String getCurrentFinYear() {
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(c
				.getTime()));
		int month = Integer.parseInt(new SimpleDateFormat("M").format(c
				.getTime()));
		String finYear = null;
		if (month < 4)
			finYear = new Integer(year - 1).toString() + "-"
					+ new Integer(year).toString();
		if (month > 3)
			finYear = new Integer(year).toString() + "-"
					+ new Integer(year + 1).toString();

		return finYear;
	}

	/**
	 * <p>
	 * This method returns the financial year of a particular date
	 * </p>
	 * 
	 * @param date
	 * @return financial year
	 * 
	 */
	public static String getFinYearFromDate(String date) {
		if (date == null || date.length() < 10)
			return getFinYearFromDate(getTodaysDate());

		int year = Integer.parseInt(date.substring(6, 10));
		int month = Integer.parseInt(date.substring(3, 5));
		String finYear = null;
		if (month < 4)
			finYear = new Integer(year - 1).toString() + "-"
					+ new Integer(year).toString();
		if (month > 3)
			finYear = new Integer(year).toString() + "-"
					+ new Integer(year + 1).toString();

		return finYear;
	}

	/**
	 * Get Formatted Decimal String.
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
	 * get Formatted Decimal String.
	 * 
	 * @param dValue
	 * @return
	 */
	public static String getFormattedDecimalString(String dValue) {
		if (dValue == null || dValue.length() < 1)
			return dValue;
		double d = Double.parseDouble(dValue);
		return getFormattedDecimalString(d);
	}

	/**
	 * This method construct full name from firstName, midleName,lastName
	 * 
	 * @param String
	 *            firstName, String midleName,String lastName
	 * @return String
	 * 
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
	 * Get Ip From Request
	 * </p>
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIpFromRequest(HttpServletRequest request) {
		String ip = "Could not Parse.";
		String logMsg = "";
		try {
			boolean found = false;
			if (null != request) {
				ip = request.getHeader("x-forwarded-for");
				logMsg = "x-forwarded-for::" + ip;
				if (null == ip) {
					ip = request.getHeader("X-FORWARDED-FOR");
					logMsg += "::X-FORWARDED-FOR::" + ip;
				}
				if (null == ip) {
					ip = request.getHeader("x-Forwarded-For");
					logMsg += "::x-Forwarded-For::" + ip;
				}
				if (null == ip) {
					ip = request.getHeader("X-Forwarded-For");
					logMsg += "::X-Forwarded-For::" + ip;
				}
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
					logMsg += "::request.getRemoteAddr::" + ip;
				}
			}
			AppLog.debugInfo(logMsg);
		} catch (Exception e) {
			// AppLog.fatal(e);
		}
		return ip;
	}

	/**
	 * <p>
	 * Get Ip From Request
	 * </p>
	 * 
	 * @param request
	 * @param sessionDetails
	 * @return ip
	 */
	public static String getIpFromRequest(HttpServletRequest request,
			SessionDetails sessionDetails) {
		String ip = "Could not Parse.";
		String logMsg = "";
		try {
			boolean found = false;
			if (null != request) {
				ip = request.getHeader("x-forwarded-for");
				logMsg = "x-forwarded-for::" + ip;
				if (null == ip) {
					ip = request.getHeader("X-FORWARDED-FOR");
					logMsg += "::X-FORWARDED-FOR::" + ip;
				}
				if (null == ip) {
					ip = request.getHeader("x-Forwarded-For");
					logMsg += "::x-Forwarded-For::" + ip;
				}
				if (null == ip) {
					ip = request.getHeader("X-Forwarded-For");
					logMsg += "::X-Forwarded-For::" + ip;
				}
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
					logMsg += "::request.getRemoteAddr::" + ip;
				}
			}
			AppLog.debugInfo(sessionDetails, logMsg);
		} catch (Exception e) {
			// AppLog.fatal(e);
		}
		return ip;
	}

	/**
	 * <p>
	 * Get Random Token of desired length.
	 * </p>
	 * 
	 * @param tokenLength
	 * @return
	 */
	public static String getRandomToken(int tokenLength) {
		String rString = "";
		String noString = "";
		try {

			String str = new String(RANDOM_TOKEN);
			StringBuffer sb = new StringBuffer();
			Random r = new Random();
			int te = 0;
			for (int i = 1; i <= tokenLength; i++) {
				long randomNumber = (long) Math
						.floor(Math.random() * 9000000000L) + 1000000000L;
				noString = Long.toString(randomNumber).substring(5);
				te = r.nextInt(62);
				sb.append(str.charAt(te));
				sb.append(noString);
			}
			rString = sb.toString();
			if (!isEmptyString(rString) && tokenLength > 0) {
				rString = rString.substring(0, tokenLength);
			}
			AppLog.debugInfo("tokenLength::" + tokenLength + "::RandomToken::"
					+ rString);
		} catch (Exception e) {
			AppLog.fatal(e);
		}
		return rString;
	}

	/**
	 * <p>
	 * Get Random Token of desired length.
	 * </p>
	 * 
	 * @param tokenLength
	 * @return
	 */
	public static String getRandomToken(int tokenLength,
			SessionDetails sessionDetails) {
		String rString = "";
		String noString = "";
		try {

			String str = new String(RANDOM_TOKEN);
			StringBuffer sb = new StringBuffer();
			Random r = new Random();
			int te = 0;
			for (int i = 1; i <= tokenLength; i++) {
				long randomNumber = (long) Math
						.floor(Math.random() * 9000000000L) + 1000000000L;
				noString = Long.toString(randomNumber).substring(5);
				te = r.nextInt(62);
				sb.append(str.charAt(te));
				sb.append(noString);
			}
			rString = sb.toString();
			if (!isEmptyString(rString) && tokenLength > 0) {
				rString = rString.substring(0, tokenLength);
			}
			AppLog.debugInfo(sessionDetails, "tokenLength::" + tokenLength
					+ "::RandomToken::" + rString);
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		return rString;
	}

	/**
	 * <p>
	 * This function returns relative Financial Years. If negative count is sent
	 * as a parameter then previous financial years are returned and if positive
	 * count is sent as a parameter then proceeding financial years are
	 * returned.
	 * </p>
	 */
	public static String getRelativeFinYear(String finYear, int count) {
		String startingCurrentFinYear = finYear.substring(0, 4);
		String endingCurrentFinYear = finYear.substring(5);

		int newStartingFinYear = Integer.parseInt(startingCurrentFinYear)
				+ count;
		int newEndingFinYear = Integer.parseInt(endingCurrentFinYear) + count;

		String newFinYear = String.valueOf(newStartingFinYear) + "-"
				+ String.valueOf(newEndingFinYear);

		return newFinYear;
	}

	/**
	 * @param tokenLength
	 * @return
	 */
	public static String getReqId(String inputString, int reqIdLength) {
		StringBuffer reqIdSB = new StringBuffer();
		String reqId = "";
		try {
			if (!isEmptyString(inputString)) {
				reqIdSB.append((inputString.replaceAll("-", "")));
			}
			reqIdSB.append(getRandomToken(reqIdLength));
			reqId = reqIdSB.toString().substring(0, reqIdLength);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqId;
	}

	/**
	 * This method returns a date in java.sql.Date format taking the
	 * parameterized date in String format
	 * 
	 * @param strDate
	 * @return
	 * 
	 */
	public static java.sql.Date getSqlDate(String strDate) {
		java.sql.Date sqlDate = null;
		try {
			if (strDate != null && strDate.length() > 0)
				sqlDate = new java.sql.Date((parseDate(strDate)).getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sqlDate;
	}

	/**
	 * Get Time Difference In Seconds between two date time in yyyy/MM/dd
	 * HH:mm:ss format.
	 * 
	 * @param startDttm
	 * @param endDttm
	 * @return difference in seconds
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 03-03-2015
	 */
	public static long getTimeDiffInSeconds(String startDttm, String endDttm) {
		long diffSeconds = 0;
		if (!isEmptyString(startDttm) && !isEmptyString(endDttm)) {
			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = format.parse(startDttm);
				d2 = format.parse(endDttm);
				// in milliseconds
				long diff = d2.getTime() - d1.getTime();
				diffSeconds = diff / 1000;
			} catch (Exception e) {
				AppLog.fatal(e);
			}
		}
		return diffSeconds;
	}

	/**
	 * Get time elapse from a time in yyyy/MM/dd HH:mm:ss format.
	 * 
	 * @param startDttm
	 * @param endDttm
	 * @return difference in seconds
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 03-03-2015
	 */
	public static long getTimeElapsedSeconds(String startDttm) {
		long diffSeconds = 0;
		if (!isEmptyString(startDttm)) {
			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String endDttm = dateFormat.format(date);
			Date d1 = null;
			Date d2 = null;

			try {
				d1 = format.parse(startDttm);
				d2 = format.parse(endDttm);
				// in milliseconds
				long diff = d2.getTime() - d1.getTime();
				diffSeconds = diff / 1000;

			} catch (Exception e) {
				AppLog.fatal(e);
			}
		}
		return diffSeconds;
	}

	/**
	 * This method returns the current date in String format
	 * 
	 * @return
	 * 
	 */
	public static String getTodaysDate() {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String todaysDate = sdf.format(new java.util.Date());
		return todaysDate;
	}

	/**
	 * <p>
	 * This method construct the url from hhtp request.
	 * </p>
	 * 
	 * @param req
	 * @return
	 */
	public static String getUrl(HttpServletRequest req) {
		String url = null;
		try {
			String scheme = req.getScheme(); // http
			String serverName = req.getServerName(); // hostname.com
			int serverPort = req.getServerPort(); // 80
			String contextPath = req.getContextPath(); // /mywebapp
			String servletPath = req.getServletPath(); // /servlet/MyServlet
			String pathInfo = req.getPathInfo(); // /a/b;c=123
			String queryString = req.getQueryString(); // d=789

			// Reconstruct original requesting URL
			url = scheme + "://" + serverName + ":" + serverPort + contextPath
					+ servletPath;
			if (pathInfo != null) {
				url += pathInfo;
			}
			if (queryString != null) {
				url += "?" + queryString;
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		return url;
	}

	/**
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
	 * Check if Current Time is Between Tow Time Range irrespective of date. If
	 * to time greater than from time then it checks if the current time is
	 * after from time and before to time else 1day is added to the to time and
	 * then checked if the current time is after from time and before to time.
	 * </p>
	 * 
	 * @param fromTime
	 *            in HH:mm:ss format
	 * @param toTime
	 *            in HH:mm:ss format
	 * @return true if it lies between the fromTime and toTime else false.
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 19-01-2015
	 * 
	 */
	public static boolean isCurrTimeBetweenTowTimeRange(String fromTime,
			String toTime) {
		AppLog.begin();
		boolean isCurrTimeBetweenTowTimeRange = false;
		try {
			// DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			// Date date = new Date();
			// String currDate = dateFormat.format(date);
			//
			// Date timeFrom = new SimpleDateFormat("HH:mm:ss").parse(fromTime);
			// Date timeTo = new SimpleDateFormat("HH:mm:ss").parse(toTime);
			// Date timeCurr = new SimpleDateFormat("HH:mm:ss").parse(currDate);
			//
			// if (timeTo.after(timeFrom)) {
			// // System.out.println("timeCurr>>" + timeCurr + ">>timeFrom>>"
			// // + timeFrom + ">>timeTo>>" + timeTo);
			// if (timeCurr.after(timeFrom) && timeCurr.before(timeTo)) {
			// isCurrTimeBetweenTowTimeRange = true;
			// }
			// } else {
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(timeTo);
			// calendar.add(Calendar.DATE, 1);
			// Date timeToOnNextDay = calendar.getTime();
			// // System.out.println("timeCurr>>" + timeCurr + ">>timeFrom>>"
			// // + timeFrom + ">>timeTo>>" + timeTo
			// // + ">>timeToOnNextDay>>" + timeToOnNextDay);
			// if (timeCurr.after(timeFrom)
			// && timeCurr.before(timeToOnNextDay)) {
			// isCurrTimeBetweenTowTimeRange = true;
			// }
			// }
			// String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
			// if (fromTime.matches(reg) && toTime.matches(reg)) {
			if (!isEmptyString(fromTime) && !isEmptyString(toTime)) {
				if ((fromTime.length() - fromTime.replaceAll(":", "").length()) == 1) {
					fromTime = fromTime + ":00";
				}
				if ((fromTime.length() - fromTime.replaceAll(":", "").length()) == 0) {
					fromTime = fromTime + ":00:00";
				}
				if ((toTime.length() - toTime.replaceAll(":", "").length()) == 1) {
					toTime = toTime + ":00";
				}
				if ((toTime.length() - toTime.replaceAll(":", "").length()) == 0) {
					toTime = toTime + ":00:00";
				}
				int from = Integer.parseInt(fromTime.replaceAll(":", ""));
				int to = Integer.parseInt(toTime.replaceAll(":", ""));
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int t = c.get(Calendar.HOUR_OF_DAY) * 10000
						+ c.get(Calendar.MINUTE) * 100 + c.get(Calendar.SECOND);
				// t = 23 * 10000 + 3 * 100;
				// System.out.println("From>>" + from + ">>>To>>>" + to
				// + ">>Current>>" + t);
				isCurrTimeBetweenTowTimeRange = to > from && t >= from
						&& t <= to || to < from && (t >= from || t <= to);
				AppLog.debugInfo("@Param::fromTime::" + fromTime + "::toTime::"
						+ toTime + "::from::" + from + "::to::" + to
						+ "::now::" + t + "::Is Between::"
						+ isCurrTimeBetweenTowTimeRange);
			}
			// }else{
			// throw new
			// IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
			// }
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return isCurrTimeBetweenTowTimeRange;
	}

	/**
	 * <p>
	 * Check if Current Time is Between Tow Time Range irrespective of date. If
	 * to time greater than from time then it checks if the current time is
	 * after from time and before to time else 1day is added to the to time and
	 * then checked if the current time is after from time and before to time.
	 * </p>
	 * 
	 * @param fromTime
	 *            in HH:mm:ss format
	 * @param toTime
	 *            in HH:mm:ss format
	 * @param sessionDetails
	 * @return true if it lies between the fromTime and toTime else false.
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 19-01-2015
	 * 
	 */
	public static boolean isCurrTimeBetweenTowTimeRange(String fromTime,
			String toTime, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		boolean isCurrTimeBetweenTowTimeRange = false;
		try {
			if (!isEmptyString(fromTime) && !isEmptyString(toTime)) {
				if ((fromTime.length() - fromTime.replaceAll(":", "").length()) == 1) {
					fromTime = fromTime + ":00";
				}
				if ((fromTime.length() - fromTime.replaceAll(":", "").length()) == 0) {
					fromTime = fromTime + ":00:00";
				}
				if ((toTime.length() - toTime.replaceAll(":", "").length()) == 1) {
					toTime = toTime + ":00";
				}
				if ((toTime.length() - toTime.replaceAll(":", "").length()) == 0) {
					toTime = toTime + ":00:00";
				}
				int from = Integer.parseInt(fromTime.replaceAll(":", ""));
				int to = Integer.parseInt(toTime.replaceAll(":", ""));
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int t = c.get(Calendar.HOUR_OF_DAY) * 10000
						+ c.get(Calendar.MINUTE) * 100 + c.get(Calendar.SECOND);
				// t = 23 * 10000 + 3 * 100;
				// System.out.println("From>>" + from + ">>>To>>>" + to
				// + ">>Current>>" + t);
				isCurrTimeBetweenTowTimeRange = to > from && t >= from
						&& t <= to || to < from && (t >= from || t <= to);
				AppLog.debugInfo(sessionDetails, "@Param::fromTime::"
						+ fromTime + "::toTime::" + toTime + "::from::" + from
						+ "::to::" + to + "::now::" + t + "::Is Between::"
						+ isCurrTimeBetweenTowTimeRange);
			}
		} catch (Exception e) {
			AppLog.error(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
		return isCurrTimeBetweenTowTimeRange;
	}

	/**
	 * <p>
	 * Method is to check if a string is empty or null;
	 * </p>
	 * 
	 * @param inputString
	 * @return true if null or empty else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static boolean isEmptyString(String inputString) {
		boolean isEmpty = true;
		try {
			if (null != inputString && !"".equals(inputString.trim())) {
				isEmpty = false;
			}
		} catch (Exception e) {
			// Ignoring Exception
		}
		return isEmpty;
	}

	/**
	 * <p>
	 * Method is to search a key in a delimited input string. The delimited
	 * string is splited to array on the basis of delimiter passed as parameter.
	 * If found returns true else false.
	 * </p>
	 * 
	 * @param inputString
	 *            delimited string
	 * @param delimiter
	 *            delimiter used to delimit the string
	 * @param key
	 *            value to be matched
	 * @return true if found else false.
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static boolean isFoundInDelimitedString(String inputString,
			String delimiter, String key) {
		boolean isFound = false;
		try {
			if (null != inputString) {
				String tempArray[] = inputString.split(delimiter);
				for (int i = 0; i < tempArray.length; i++) {
					if (null != key && key.equals(tempArray[i])) {
						isFound = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			// Ignoring Exception
		}
		return isFound;
	}

	/**
	 * <p>
	 * Method is to check if comma separated string is greater than 1000 which
	 * is the maximum for oracle IN parameter.
	 * </p>
	 * 
	 * @param inputString
	 * @return formated output String
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static boolean isInParameterGreaterThan1000(String inputString) {
		boolean isInParameterGreaterThan1000 = false;
		try {
			if (null != inputString) {
				String tempArray[] = inputString.split(",");
				if (null != tempArray && tempArray.length > 1000) {
					isInParameterGreaterThan1000 = true;
				}
			}
		} catch (Exception e) {
			// Ignoring Exception
		}
		return isInParameterGreaterThan1000;
	}

	/**
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
	 * @param ip
	 * @return
	 */
	public static boolean isIPv4Valid(String ip) {
		return pattern.matcher(ip).matches();
	}

	/**
	 * <p>
	 * This is the method to compare the system time stamp with a provided
	 * interval time, JTrac DJB-3367.
	 * </p>
	 * 
	 * @param dateTimeFormat
	 * @param startTime
	 * @param endTime
	 * @return
	 * @since 12-08-2014
	 * @author 556885
	 * @history Reshma on 26-08-2014 edited the method to check the availability
	 *          of Bill Generation web service for HHD, JTrac DJB-3399.
	 */
	@SuppressWarnings("deprecation")
	public static boolean isWebServiceAvailable(String dateTimeFormat,
			String startTime, String endTime) {
		AppLog.begin();
		boolean isAvailable = false;
		Date endDateTime = null;
		Date startDateTime = null;
		Date currentDateTime = new Date();
		if (null != dateTimeFormat
				&& !"".equalsIgnoreCase(dateTimeFormat.trim())
				&& null != startTime && !"".equalsIgnoreCase(startTime.trim())
				&& null != endTime && !"".equalsIgnoreCase(endTime.trim())) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					dateTimeFormat.trim());
			try {
				startDateTime = simpleDateFormat.parse(startTime.trim());
				startDateTime.setDate(currentDateTime.getDate());
				startDateTime.setMonth(currentDateTime.getMonth());
				startDateTime.setYear(currentDateTime.getYear());
				endDateTime = simpleDateFormat.parse(endTime.trim());
				endDateTime.setDate(currentDateTime.getDate());
				endDateTime.setMonth(currentDateTime.getMonth());
				endDateTime.setYear(currentDateTime.getYear());
				/**
				 * Start : Reshma on 26-08-2014 edited this to check the
				 * availability of Bill Generation web service for HHD, JTrac
				 * DJB-3399.
				 */
				if (startDateTime.after(endDateTime)) {
					if (currentDateTime.after(startDateTime)
							|| currentDateTime.before(endDateTime)) {
						isAvailable = true;
					}
				} else {
					if (currentDateTime.after(startDateTime)
							&& currentDateTime.before(endDateTime)) {
						isAvailable = true;
					}
				}
				/**
				 * End : Reshma on 26-08-2014 edited this to check the
				 * availability of Bill Generation web service for HHD, JTrac
				 * DJB-3399.
				 */
			} catch (ParseException e) {
				AppLog.fatal(e);
			} catch (Exception e) {
				AppLog.fatal(e);
			}
		}
		AppLog.debugInfo("Web Service Availabilty:" + isAvailable
				+ ":currentDateTime:" + currentDateTime + ":startDateTime:"
				+ startDateTime + ":endDateTime:" + endDateTime);
		AppLog.end();
		return isAvailable;
	}

	/**
	 * <p>
	 * This is the method to compare the system time stamp with a provided
	 * interval time, JTrac DJB-3367.
	 * </p>
	 * 
	 * @param dateTimeFormat
	 * @param startTime
	 * @param endTime
	 * @param sessionDetails
	 * @return
	 * @since 10-03-2015
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	@SuppressWarnings("deprecation")
	public static boolean isWebServiceAvailable(String dateTimeFormat,
			String startTime, String endTime, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		boolean isAvailable = false;
		Date endDateTime = null;
		Date startDateTime = null;
		Date currentDateTime = new Date();
		if (null != dateTimeFormat
				&& !"".equalsIgnoreCase(dateTimeFormat.trim())
				&& null != startTime && !"".equalsIgnoreCase(startTime.trim())
				&& null != endTime && !"".equalsIgnoreCase(endTime.trim())) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					dateTimeFormat.trim());
			try {
				startDateTime = simpleDateFormat.parse(startTime.trim());
				startDateTime.setDate(currentDateTime.getDate());
				startDateTime.setMonth(currentDateTime.getMonth());
				startDateTime.setYear(currentDateTime.getYear());
				endDateTime = simpleDateFormat.parse(endTime.trim());
				endDateTime.setDate(currentDateTime.getDate());
				endDateTime.setMonth(currentDateTime.getMonth());
				endDateTime.setYear(currentDateTime.getYear());
				if (startDateTime.after(endDateTime)) {
					if (currentDateTime.after(startDateTime)
							|| currentDateTime.before(endDateTime)) {
						isAvailable = true;
					}
				} else {
					if (currentDateTime.after(startDateTime)
							&& currentDateTime.before(endDateTime)) {
						isAvailable = true;
					}
				}
			} catch (ParseException e) {
				AppLog.fatal(e);
			} catch (Exception e) {
				AppLog.fatal(e);
			}
		}
		AppLog.debugInfo(sessionDetails, "Web Service Availabilty:"
				+ isAvailable + ":currentDateTime:" + currentDateTime
				+ ":startDateTime:" + startDateTime + ":endDateTime:"
				+ endDateTime);
		AppLog.end(sessionDetails);
		return isAvailable;
	}

	/**
	 * @param longIp
	 * @return
	 */
	public static String longToIpV4(long longIp) {
		int octet3 = (int) ((longIp >> 24) % 256);
		int octet2 = (int) ((longIp >> 16) % 256);
		int octet1 = (int) ((longIp >> 8) % 256);
		int octet0 = (int) ((longIp) % 256);
		return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}

	/**
	 * @param subject
	 * @param lengthDesired
	 * @param c
	 * @return
	 */
	public static String lpad(String subject, int lengthDesired, char c) {
		int stringLength = subject.length();
		for (int i = 0; i < lengthDesired - stringLength; i++) {
			subject = c + subject;
		}
		return subject;
	}

	/**
	 * <p>
	 * Method is to make a valid oracle IN parameter of comma separated string.
	 * </p>
	 * 
	 * @param inputString
	 * @return formated output String
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static String makeValidINParameter(String inputString) {
		String outputString = "''";
		try {
			if (null != inputString) {
				String tempArray[] = inputString.split(",");
				for (int i = 0; i < tempArray.length; i++) {
					if (null != tempArray[i] && !"".equals(tempArray[i].trim())) {
						outputString = outputString + ",'"
								+ tempArray[i].trim() + "'";
					}
				}
			}
			outputString = outputString.replaceAll("'',", "");
		} catch (Exception e) {
			// Ignoring Exception
		}
		return outputString;
	}

	/**
	 * <p>
	 * Method is to Make valid in parameter if comma separated string is greater
	 * than 1000 which is the maximum for oracle IN parameter.
	 * </p>
	 * 
	 * @param inputString
	 * @return formated output String
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static String makeValidInParameterGreaterThan1000(String inSQLParam,
			String inputString) {
		String tempString = "-";
		StringBuffer outputStringSB = new StringBuffer();
		try {
			if (null != inputString) {
				outputStringSB.append(" ( ");
				int j = 0;
				String tempArray[] = inputString.split(",");
				for (int i = 0; i < tempArray.length; i++) {
					if (null != tempArray[i] && !"".equals(tempArray[i].trim())) {
						tempString = tempString + "," + tempArray[i];
						if (i != 0 && (i + 1) % 1000 == 0) {
							if (j > 0) {
								outputStringSB.append(" OR ");
							}
							outputStringSB.append(inSQLParam);
							outputStringSB.append(" in (");
							outputStringSB.append(tempString.replaceAll("-,",
									""));
							outputStringSB.append(" )");
							tempString = "-";
							j++;
						}
					}
				}
				if (!"-".equals(tempString)) {
					if (j > 0) {
						outputStringSB.append(" OR ");
					}
					outputStringSB.append(inSQLParam);
					outputStringSB.append(" in (");
					outputStringSB.append(tempString.replaceAll("-,", ""));
					outputStringSB.append(" )");
				}
				outputStringSB.append(" ) ");
			}
		} catch (Exception e) {
			// Ignoring Exception
		}
		return outputStringSB.toString();
	}

	/**
	 * parseDate in dd/MM/yyyy format.
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String date) throws Exception {
		if (date != null && date.length() > 0) {
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date parsedDate = sdf.parse(date);
			return parsedDate;
		} else
			return null;
	}

	/**
	 * This method strips any string of the quotes
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
	 * This method strips any sort of alpha-numeric characters except alphabets.
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
	 * validate Date in dd/mm/yyyy format
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
}