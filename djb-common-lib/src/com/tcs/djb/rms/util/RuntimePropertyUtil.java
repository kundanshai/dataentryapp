/************************************************************************************************************
 * @(#) RuntimePropertyUtil.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * <P>
 * This Utility class used to load the property file at runtime and read
 * property file entry on the basis of particular key written in the property
 * file.
 * </P>
 * 
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 */
public class RuntimePropertyUtil {

	/**
	 * <p>
	 * Get property value from a property file defined in the application
	 * property file. Property file is read at runtime and get instant value.
	 * Generally values are loaded at system start up, so if we want to load the
	 * at runtime this might be used.
	 * </p>
	 * 
	 * @param propFilePath
	 * @param propName
	 * @param propDelimiter
	 * @return property value if found else null.
	 */
	public static String getPropertyValue(String propFilePath, String propName,
			String propDelimiter) {
		String propValue = null;
		String propFileContent = null;
		try {
			propFileContent = FileUtil.getFileText(propFilePath);
			if (!UtilityForAll.isEmptyString(propFileContent)
					&& !UtilityForAll.isEmptyString(propName)) {
				String[] properties = propFileContent.split(propDelimiter);
				String tempString = "";
				if (null != properties && properties.length > 0) {
					for (int i = 0; i < properties.length; i++) {
						tempString = properties[i];
						if (null != tempString && tempString.contains(propName)
								&& !tempString.contains("#")) {
							String tempStringValue = tempString
									.substring(tempString.indexOf('=') + 1);
							if (null != tempStringValue) {
								propValue = tempStringValue;
							}
						}
					}
				}

			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		return propValue;
	}

	/**
	 * <p>
	 * Get property value from a property file defined in the application
	 * property file. Property file is read at runtime and get instant value.
	 * Generally values are loaded at system start up, so if we want to load the
	 * at runtime this might be used.
	 * </p>
	 * 
	 * @param propFilePath
	 * @param propName
	 * @param propDelimiter
	 * @return property value if found else null.
	 */
	public static boolean setPropertyValue(String propFilePath,
			String oldValue, String newValue) {
		boolean isSuccess = false;
		try {
			File file = new File(propFilePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			// replace a word in a file
			String newtext = oldtext.replaceAll(oldValue, newValue);

			FileWriter writer = new FileWriter(propFilePath);
			writer.write(newtext);
			writer.close();
			isSuccess = true;
		} catch (Exception e) {
			AppLog.error(e);
		}
		return isSuccess;
	}
}
