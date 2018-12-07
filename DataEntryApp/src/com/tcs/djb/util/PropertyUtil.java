/************************************************************************************************************
 * @(#) PropertyUtil.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.tcs.djb.constants.DJBConstants;

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
	 * @see DJBConstants#DJB_PROP_FILE_NAME
	 */
	private PropertyUtil() {
		try {
			properties = new Properties();
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
				AppLog.error(new FileNotFoundException());
			}
		} catch (Exception e) {
			AppLog
					.info("FATAL::ERROR IN LOADING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT PROPER THE LOCATION::");
			System.out
					.println("FATAL::ERROR IN LOADING PROPERTY FILE::PLEASE PUT THE PROPERTY FILE AT PROPER THE LOCATION::");
			AppLog.error(e);
		}
	}

	/**
	 * <p>
	 * Loads property file using <code>FileInputStream</code>.
	 * </p>
	 * <p>
	 * Load the Property file from Custom Location Defined in
	 * <code>DJBConstants.DJB_PROP_FILE_PATH</code> i.e.
	 * <code>/home/user/djb_portal_properties</code>
	 * </p>
	 * 
	 * @exception FileNotFoundException
	 *                if the file is not found in the passed location
	 * @exception IOException
	 *                if there is read/write or I/O error occurred
	 * @exception Exception
	 *                if any kind of general error occurred
	 * @see DJBConstants#DJB_PROP_FILE_NAME
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromCustomLocation() {
		try {
			propertyFilePath = DJBConstants.DJB_PROP_FILE_PATH;
			String propertyFile = propertyFilePath + "/"
					+ DJBConstants.DJB_PROP_FILE_NAME;
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
	 * @see DJBConstants#DJB_PROP_FILE_NAME
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromSystemVariable() {
		try {
			propertyFilePath = System.getProperty("propertyFile");
			String propertyFile = propertyFilePath + "/"
					+ DJBConstants.DJB_PROP_FILE_NAME;
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
	 * @see DJBConstants#DJB_PROP_FILE_NAME
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromUserDir() {
		try {
			propertyFilePath = System.getProperty("user.dir");
			String propertyFile = propertyFilePath + "/"
					+ DJBConstants.DJB_PROP_FILE_NAME;
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
	 * @see DJBConstants#DJB_PROP_FILE_NAME
	 * @see FileInputStream
	 * @see System#getProperty
	 */
	public void getPropertyFileFromWeblogicDomainDir() {
		try {
			propertyFilePath = System.getProperty("weblogic.domainDir");
			String propertyFile = propertyFilePath + "/"
					+ DJBConstants.DJB_PROP_FILE_NAME;
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
}

///**
// * @author 340132
// * 
// */
/*
 * public final class PropertyUtil { public static final String DJB_PROP_FILE =
 * "/home/user/djb_portal_properties/djb_portal_data_entry.properties"; private
 * static Properties properties = null; private static FileInputStream fIn =
 * null; private static PropertyUtil propertyUtil = new PropertyUtil();
 * 
 * public String getProperty(String propertiesName) { return
 * properties.getProperty(propertiesName); }
 * 
 * private PropertyUtil() { AppLog.begin(); try { properties = new Properties();
 * fIn = new FileInputStream(DJB_PROP_FILE); properties.load(fIn); fIn.close();
 * 
 * } catch (FileNotFoundException e) { AppLog.error(e); } catch (IOException e)
 * { AppLog.error(e); } catch (Exception e) { AppLog.error(e); } /* finally { //
 * fIn.close(); }
 */
/*
 * AppLog.end();
 * 
 * }
 * 
 * public static PropertyUtil getPropertyUtil() { return propertyUtil; }
 * 
 * }
 */
