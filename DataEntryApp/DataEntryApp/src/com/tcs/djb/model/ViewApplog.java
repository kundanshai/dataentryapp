/************************************************************************************************************
 * @(#) ViewApplog.java 22 Apr 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.util.FileUtil;

/**
 * <p>
 * Container for viewing Application Log.
 * </p>
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class ViewApplog {
	/**
	 * <p>
	 * This method is used the list of files to view Application Log.
	 * </p>
	 * 
	 * @param path
	 * @param appLogName
	 * @return
	 */
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
