/************************************************************************************************************
 * @(#) AppLog.java   10 Oct 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

/**
 * <p>
 * This class is used to view application log
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @history begin & end commented on 10-10-2012
 * @history begin & end made flag based on 11-12-2013
 */
public class AppLog {
	public static String DEBUG_ENABLE = "N";
	public final static Logger appLog;

	public static final String LOG4J_PROPERTIES = "/src/log4j.properties";

	static {
		try {
			Properties prop = new Properties();
			try {
				AppLog obj = new AppLog();

				prop.load(obj.getClass().getClassLoader().getResourceAsStream(
						LOG4J_PROPERTIES));
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
		appLog = Logger.getLogger("APPLogger");
		String logFilePath = null;
		try {
			logFilePath = PropertyUtil.getProperty("APP_LOGGER");
			if (null != logFilePath) {
				RollingFileAppender appnder = (RollingFileAppender) appLog
						.getAppender("APP");
				appnder.setFile(logFilePath);
				appnder.activateOptions();
			}
			DEBUG_ENABLE = PropertyUtil.getProperty("DEBUG_ENABLE");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * This method is used to begin debug mode
	 * </p>
	 */
	public static void begin() {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.info(prepareCallerInfoStr(className, methodName, lineNumber)
					+ " " + " :: BEGIN :: ");
		}
	}

	/**
	 * <p>
	 * This method is used to begin debug mode
	 * </p>
	 * 
	 * @param message
	 */
	public static void begin(Object message) {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.info(prepareCallerInfoStr(className, methodName, lineNumber)
					+ " " + " :: BEGIN :: " + message);
		}
	}

	/**
	 * <p>
	 * This method is used to debug logs
	 * </p>
	 * @param message
	 */
	public static void debug(Object message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.debug(prepareCallerInfoStr(className, methodName, lineNumber)
				+ " " + " :: " + message);

	}

	/**
	 * <p>
	 * This method is used to end debug mode
	 * </p>
	 */
	public static void end() {
		if ("Y".equalsIgnoreCase(DEBUG_ENABLE)) {
			StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
			String className = myCaller.getClassName();
			String methodName = myCaller.getMethodName();
			int lineNumber = myCaller.getLineNumber();
			appLog.info(prepareCallerInfoStr(className, methodName, lineNumber)
					+ " " + " :: END :: ");
		}
	}

	/**
	 * <p>
	 * This method is used to end debug mode
	 * </p>
	 * 
	 * @param message
	 * @param t
	 */
	public static void error(Object message, Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();

		appLog.error(prepareCallerInfoStr(className, methodName, lineNumber)
				+ " " + " ::FATAL ERROR:: " + message, t);

	}

	/**
	 * <p>
	 * This method is used to write error in logs
	 * </p>
	 * 
	 * @param message
	 */
	public static void error(String message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();

		appLog.error(prepareCallerInfoStr(className, methodName, lineNumber)
				+ " " + " ::FATAL ERROR:: " + message);

	}

	/**
	 * <p>
	 * This method is used to write error in logs
	 * </p>
	 * 
	 * @param t
	 */
	public static void error(Throwable t) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();
		appLog.error(prepareCallerInfoStr(className, methodName, lineNumber)
				+ " " + " ::FATAL ERROR:: ", t);

	}

	/**
	 * <p>
	 * This method is used to write info in logs
	 * </p>
	 * 
	 * @param message
	 */
	public static void info(Object message) {
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
		String className = myCaller.getClassName();
		String methodName = myCaller.getMethodName();
		int lineNumber = myCaller.getLineNumber();

		appLog.info(prepareCallerInfoStr(className, methodName, lineNumber)
				+ " " + " :: " + message);

	}

	/**
	 * <p>
	 * This method is used to prepare caller information
	 * </p>
	 * 
	 * @param className
	 * @param methodName
	 * @param lineNumber
	 * @return
	 */
	private static String prepareCallerInfoStr(String className,
			String methodName, int lineNumber) {
		return className + ":" + methodName + " " + lineNumber;
	}

}
