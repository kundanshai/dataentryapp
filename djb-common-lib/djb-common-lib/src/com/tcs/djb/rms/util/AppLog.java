/************************************************************************************************************
 * @(#) AppLog.java 1.1 31 Mar 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import com.tcs.djb.rms.model.CommonStore;
import com.tcs.djb.rms.model.SessionDetails;

/**
 * <P>
 * This Utility class used to print application log to a log file. This Utility
 * is used throughout the application for logging.
 * </P>
 * 
 * @see Properties
 * @see FileInputStream
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 31-03-2014
 * @version 1.1
 * 
 * @history 11-06-2014 Matiur Rahman Updated for Appending User Id and Session
 *          Id with each log line and log redirecting to custom location with
 *          size and number defined in property file.
 */
public class AppLog {
	/**
	 * Logger
	 */
	public final static Logger appLog;
	/**
	 * For enabling or disabling method trace.
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-06-2014
	 */
	public static String DEBUG_ENABLE = "N";
	private static String fileNameExtra;
	private static String appName;

	/**
	 * path of log4j property file
	 */
	public static final String LOG4J_PROPERTIES = "log4j.properties";

	static {
		try {
			Properties prop = new Properties();
			try {
				AppLog obj = new AppLog();
				prop.load(obj.getClass().getClassLoader()
						.getResourceAsStream(LOG4J_PROPERTIES));
				PropertyConfigurator.configure(prop);
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			fileNameExtra = null != System.getProperty("weblogic.Name") ? System
					.getProperty("weblogic.Name") : fileNameExtra;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			appName = null != CommonStore.get("APP_CONTEXT") ? (String) CommonStore
					.get("APP_CONTEXT") : null;
			System.out.println("In AppLog APP_CONTEXT=" + appName);
			if (null != appName && appName.contains("/")) {
				appName = appName.replaceFirst("/", "").replaceAll("/", "_");
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		try {
			// System.out.println("3.fileNameExtra>>" + fileNameExtra + ">>"
			// + appName);
			fileNameExtra = null != appName ? appName + "_" + fileNameExtra
					: fileNameExtra;
		} catch (Exception e1) {
			// e1.printStackTrace();
		}
		appLog = Logger.getLogger("APPLogger");
		String logFilePath = null;
		/*
		 * If log file path is defined in property file log will be written to
		 * the defined path and file
		 */
		try {
			logFilePath = PropertyUtil.getProperty("APP_LOGGER");
			if (null != logFilePath) {
				RollingFileAppender appnder = (RollingFileAppender) appLog
						.getAppender("APP");
				if (null != fileNameExtra && !"".equals(fileNameExtra)) {
					String tempPath = logFilePath.substring(0,
							logFilePath.lastIndexOf("/") + 1);
					String tempFile = logFilePath.substring(logFilePath
							.lastIndexOf("/") + 1);
					logFilePath = tempPath + fileNameExtra.replaceAll(" ", "_")
							+ "_" + tempFile;
					// System.out.println("tempPath>>" + tempPath +
					// "::tempFile>>"
					// + tempFile + "::logFilePath>>" + logFilePath);
				}
				appnder.setFile(logFilePath);
				appnder.activateOptions();
				System.out.println("Log file rotated to -->> " + logFilePath);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		try {
			/*
			 * If DEBUG_ENABLE is Y for every method a trace will be written as
			 * begin and end
			 */
			DEBUG_ENABLE = null != PropertyUtil.getProperty("DEBUG_ENABLE") ? PropertyUtil
					.getProperty("DEBUG_ENABLE") : DEBUG_ENABLE;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		String maxBackups = null;
		/*
		 * If log file max Backups index is defined in property file log will be
		 * written to the defined index
		 */
		try {
			maxBackups = null != PropertyUtil
					.getProperty("APP_LOG_FILE_MAX_BACKUP_INDEX")
					&& !"".equals(PropertyUtil.getProperty(
							"APP_LOG_FILE_MAX_BACKUP_INDEX").trim()) ? PropertyUtil
					.getProperty("APP_LOG_FILE_MAX_BACKUP_INDEX").trim() : "0";
			if (null != maxBackups && !"0".equals(maxBackups)) {
				RollingFileAppender appnder = (RollingFileAppender) appLog
						.getAppender("APP");
				appnder.setMaxBackupIndex(Integer.parseInt(maxBackups));
				appnder.activateOptions();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		String maxFileSize = null;
		/*
		 * If log file max size is defined in property file log will be written
		 * to the defined size
		 */
		try {
			maxFileSize = null != PropertyUtil
					.getProperty("APP_LOG_FILE_MAX_SIZE")
					&& !"".equals(PropertyUtil.getProperty(
							"APP_LOG_FILE_MAX_SIZE").trim()) ? PropertyUtil
					.getProperty("APP_LOG_FILE_MAX_SIZE").trim() : "0";
			if (null != maxFileSize && !"0".equals(maxFileSize)) {
				RollingFileAppender appnder = (RollingFileAppender) appLog
						.getAppender("APP");
				appnder.setMaxFileSize(maxFileSize);
				appnder.activateOptions();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Append at beginning of a method to print in the application log to ensure
	 * method invoked
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void begin() {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(null, className, methodName,
					lineNumber) + " " + " :: BEGIN :: ");
		}
	}

	/**
	 * <p>
	 * Append at beginning of a method to print in the application log to ensure
	 * method invoked
	 * </p>
	 * 
	 * @param sessionDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void begin(SessionDetails sessionDetails) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(sessionDetails, className,
					methodName, lineNumber) + " " + " :: BEGIN :: ");
		}
	}

	/**
	 * <p>
	 * Append anywhere of a method to print the database queries in the
	 * application log.
	 * </p>
	 * 
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void dbQuery(Object message) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(null, className, methodName,
					lineNumber) + " ::Query::" + message);
		}

	}

	/**
	 * <p>
	 * Append anywhere of a method to print the database queries in the
	 * application log.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void dbQuery(SessionDetails sessionDetails, Object message) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(sessionDetails, className,
					methodName, lineNumber) + " ::Query::" + message);
		}

	}

	/**
	 * <p>
	 * Append at beginning of a method to print in the application log to ensure
	 * method invoked
	 * </p>
	 * 
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void debug(Object message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.debug(prepareCallerInfoStr(null, className, methodName,
				lineNumber) + " " + " :: " + message);
	}

	/**
	 * <p>
	 * Append anywhere of a method to print the extra info for debugging in the
	 * application log.
	 * </p>
	 * 
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void debugInfo(Object message) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(null, className, methodName,
					lineNumber) + " :: " + message);
		}

	}

	/**
	 * <p>
	 * Append anywhere of a method to print the extra info for debugging in the
	 * application log.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void debugInfo(SessionDetails sessionDetails, Object message) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(sessionDetails, className,
					methodName, lineNumber) + " :: " + message);
		}

	}

	/**
	 * <p>
	 * Append at end of a method to print in the application log to ensure
	 * method invoked
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void end() {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(null, className, methodName,
					lineNumber) + " " + " :: END :: ");
		}

	}

	/**
	 * <p>
	 * Append at end of a method to print in the application log to ensure
	 * method invoked
	 * </p>
	 * 
	 * @param sessionDetails
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void end(SessionDetails sessionDetails) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.debug(prepareCallerInfoStr(sessionDetails, className,
					methodName, lineNumber) + " " + " :: END :: ");
		}

	}

	/**
	 * <p>
	 * Append in the catch block of a method to print in the application log for
	 * any exception.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param t
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void error(SessionDetails sessionDetails, Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.error(
				prepareCallerInfoStr(sessionDetails, className, methodName,
						lineNumber), t);

	}

	/**
	 * <p>
	 * Append in the catch block of a method to print in the application log for
	 * any exception.
	 * </p>
	 * 
	 * @param message
	 * @param t
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void error(Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.error(
				prepareCallerInfoStr(null, className, methodName, lineNumber),
				t);

	}

	/**
	 * <p>
	 * Append in the catch block of a method to print in the application log for
	 * any exception.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param t
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void fatal(SessionDetails sessionDetails, Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.fatal(
				prepareCallerInfoStr(sessionDetails, className, methodName,
						lineNumber), t);

	}

	/**
	 * <p>
	 * Append in the catch block of a method to print in the application log for
	 * any exception.
	 * </p>
	 * 
	 * @param message
	 * @param t
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void fatal(Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.fatal(
				prepareCallerInfoStr(null, className, methodName, lineNumber),
				t);

	}

	/**
	 * <p>
	 * Append anywhere of a method to print the relevant information in the
	 * application log.
	 * </p>
	 * 
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void info(Object message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.info(prepareCallerInfoStr(null, className, methodName,
				lineNumber) + " :: " + message);

	}

	/**
	 * <p>
	 * Append anywhere of a method to print the relevant information in the
	 * application log.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param message
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	public static void info(SessionDetails sessionDetails, Object message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.info(prepareCallerInfoStr(sessionDetails, className, methodName,
				lineNumber) + " :: " + message);

	}

	/**
	 * <p>
	 * Prepare Caller Information String.
	 * </p>
	 * 
	 * @param className
	 * @param methodName
	 * @param lineNumber
	 * @return
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 31-03-2014
	 */
	private static String prepareCallerInfoStr(SessionDetails sessionDetails,
			String className, String methodName, int lineNumber) {
		String sessionInfo = "@NA::NA::";
		if (null != sessionDetails) {
			sessionInfo = "@"
					+ (null != sessionDetails.getUserId()
							&& !"?".equals(sessionDetails.getUserId()) ? sessionDetails
							.getUserId() : "NA")
					+ "::"
					+ (null == sessionDetails.getReqId() ? (null != sessionDetails
							.getSessionId() ? sessionDetails.getSessionId()
							: "NA") : sessionDetails.getReqId()) + "::";
		}
		String str = sessionInfo + className + "::" + methodName + "::"
				+ lineNumber;
		return str;
	}

}