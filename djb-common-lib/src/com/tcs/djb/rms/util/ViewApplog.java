/************************************************************************************************************
 * @(#) ViewApplog.java 1.1 31 Mar 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.rms.model.FileDetails;

/**
 * Utility class to view application logs.
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 31-03-2014
 * 
 * @see ServletContextListener
 */
public class ViewApplog {
	public static List<FileDetails> getFileList(String path, String appLogName) {
		List<FileDetails> fileDetailsList = new ArrayList<FileDetails>();
		List<FileDetails> tempFileDetailsList = null;
		try {
			FileDetails fileDetails = null;
			tempFileDetailsList = (ArrayList<FileDetails>) FileUtil
					.getFileDetailsList(path);
			if (null != tempFileDetailsList && tempFileDetailsList.size() > 0) {
				for (int i = 0; i < tempFileDetailsList.size(); i++) {
					fileDetails = (FileDetails) tempFileDetailsList.get(i);
					if (null != fileDetails
							&& (fileDetails.getFileName())
									.startsWith(appLogName)) {
						fileDetailsList.add(fileDetails);
					}
				}
			}
		} catch (Exception e) {

		}
		return fileDetailsList;
	}
}
