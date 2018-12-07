/************************************************************************************************************
 * @(#) PropertyUtil.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

/**
 * <P>
 * This Utility class used to load the property file and read property file
 * entry on the basis of particular key written in the property file.
 * </P>
 * 
 * @see Properties
 * @see FileInputStream
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 */
public final class PropertyUtil {
	private static FileInputStream fIn = null;
	private static boolean loadSucces = false;
	private static Properties properties = null;
	private static String propertyFilePath = null;
	private static PropertyUtil propertyUtil = new PropertyUtil();

	/**
	 * <p>
	 * This method reads the particular value against a key value passed which
	 * is written in the property file
	 * </p>
	 * 
	 * @param propName
	 *            name of the key written in the property file for which value
	 *            need to read
	 * @return property name/key written in the property file
	 */
	public static String getProperty(String propertiesName) {
		return properties.getProperty(propertiesName);
	}

	/**
	 * @return object of <code>PropertyUtil</code>
	 */
	public static PropertyUtil getPropertyUtil() {
		return propertyUtil;
	}

	/**
	 * Name of the property file
	 */
	private String PROP_FILE_NAME = "";

	/**
	 * Path of the property file
	 */
	private String PROP_FILE_PATH = "";

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * <b>Search preference:</b><br>
	 * 1.System.getProperty("propertyFile"): Load from a location defined as
	 * system variable named propertyFile written in the weblogic server starter
	 * file.<br>
	 * 2.System.getProperty("weblogic.domainDir"):Load from a location defined
	 * as system variable named DOMAIN_HOME i.e the Weblogic home directory
	 * written in the weblogic server starter file.<br>
	 * 3.System.getProperty("user.dir"):Load from current working Directory.<br>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 */
	private PropertyUtil() {
		try {
			properties = new Properties();
			loadPropertyFileDetails();
			if (!loadSucces) {
				getPropertyFileFromSystemVariable();
			}
			if (!loadSucces) {
				getPropertyFileFromWeblogicDomainDir();
			}
			if (!loadSucces) {
				getPropertyFileFromUserDir();
			}
			if (!loadSucces) {
				getPropertyFileFromCustomLocation();
			}
			if (loadSucces) {
				AppLog.info("PROPERTY FILE LOADED FROM THE LOCATION::"
						+ propertyFilePath);
				System.out.println("PROPERTY FILE LOADED FROM THE LOCATION::"
						+ propertyFilePath);
			} else {
				AppLog
						.info("FATAL::ERROR IN READING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT ANY OF THE DEFINED LOCATION::");
				System.out
						.println("FATAL::ERROR IN READING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT THE LOCATION::"
								+ propertyFilePath);
				AppLog.fatal(new FileNotFoundException());
			}
		} catch (Exception e) {
			AppLog
					.info("FATAL::ERROR IN LOADING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT PROPER THE LOCATION::");
			System.out
					.println("FATAL::ERROR IN LOADING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT PROPER THE LOCATION::");
			AppLog.fatal(e);
		}
	}

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * Load the Property file from Custom Location Defined in
	 * <code>application.properties</code> i.e.
	 * <code>/home/user/djb_portal_properties</code>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromCustomLocation() {
		try {
			propertyFilePath = PROP_FILE_PATH;
			String propertyFile = propertyFilePath + "/" + PROP_FILE_NAME;
			fIn = new FileInputStream(propertyFile);
			properties.load(fIn);
			fIn.close();

			loadSucces = true;
		} catch (FileNotFoundException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (IOException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (Exception e) {
			loadSucces = false;
			// AppLog.fatal(e);
		}
	}

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * Load the Property file from a location defined as system variable named
	 * <code>propertyFile</code> written in the weblogic server starter file.<br>
	 * i.e. retrieved using <br>
	 * <code>System.getProperty("propertyFile")</code>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 * @see application.properties
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromSystemVariable() {
		try {
			propertyFilePath = System.getProperty("propertyFile");
			String propertyFile = propertyFilePath + "/" + PROP_FILE_NAME;
			fIn = new FileInputStream(propertyFile);
			properties.load(fIn);
			fIn.close();
			loadSucces = true;
		} catch (FileNotFoundException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (IOException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (Exception e) {
			loadSucces = false;
			// AppLog.fatal(e);
		}
	}

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * Load the Property file from current working Directory. i.e. retrieved
	 * using <br>
	 * <code>System.getProperty("user.dir")</code>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 * @see application.properties
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromUserDir() {
		try {
			propertyFilePath = System.getProperty("user.dir");
			String propertyFile = propertyFilePath + "/" + PROP_FILE_NAME;
			fIn = new FileInputStream(propertyFile);
			properties.load(fIn);
			fIn.close();
			loadSucces = true;
		} catch (FileNotFoundException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (IOException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (Exception e) {
			loadSucces = false;
			// AppLog.fatal(e);
		}
	}

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * Load the Property file from a location defined as system variable named
	 * <code>DOMAIN_HOME</code> i.e the Weblogic home directory written in the
	 * weblogic server starter file.<br>
	 * i.e. retrieved using <br>
	 * <code>System.getProperty("weblogic.domainDir")</code>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 * @see application.properties
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromWeblogicDomainDir() {
		try {
			propertyFilePath = System.getProperty("weblogic.domainDir");
			String propertyFile = propertyFilePath + "/" + PROP_FILE_NAME;
			fIn = new FileInputStream(propertyFile);
			properties.load(fIn);
			fIn.close();
			loadSucces = true;
		} catch (FileNotFoundException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (IOException e) {
			loadSucces = false;
			// AppLog.fatal(e);
		} catch (Exception e) {
			loadSucces = false;
			// AppLog.fatal(e);
		}
	}

	/**
	 * <p>
	 * Load property file details from <code>application.properties</code>
	 * placed in src folder of the application where the file name
	 * <code> PROP_FILE_NAME</code> and file path <code>PROP_FILE_PATH</code> is
	 * written.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 27-08-2014
	 * @see Properties
	 * @see PropertyConfigurator
	 */
	private void loadPropertyFileDetails() {
		try {
			Properties prop = new Properties();
			try {
				prop.load(this.getClass().getClassLoader().getResourceAsStream(
						"application.properties"));
				PropertyConfigurator.configure(properties);
				PROP_FILE_NAME = prop.getProperty("PROP_FILE_NAME");
				PROP_FILE_PATH = prop.getProperty("PROP_FILE_PATH");

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

	}
}
