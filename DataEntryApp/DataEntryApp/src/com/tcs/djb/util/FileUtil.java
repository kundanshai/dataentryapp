/************************************************************************************************************
 * @(#) FileUtil.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tcs.djb.model.FileDetails;

/**
 * <p>
 * This is an Util class for fetching file details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class FileUtil {
	/**
	 * <p>
	 * This method is used to get list of directory
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	public static String[] getDirectoryList(String path) {
		String[] dirListing = null;
		File dir = new File(path);
		dirListing = dir.list();

		Arrays.sort(dirListing, 0, dirListing.length);
		return dirListing;
	}

	/**
	 * <p>
	 * This method is used to get list of files
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	public static String[] getFileList(String path) {
		String[] fileList = null;
		File file = new File(path);
		fileList = file.list();
		Arrays.sort(fileList, 0, fileList.length);
		return fileList;
	}

	/**
	 * <p>
	 * This method is used to get list of file details
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	public static List<FileDetails> getFileDetailsList(String path) {
		String[] fileList = null;
		List<FileDetails> fileDetailsList = new ArrayList<FileDetails>();
		FileDetails fileDetails = null;
		try {
			File file = new File(path);
			fileList = file.list();
			Arrays.sort(fileList, 0, fileList.length);
			if (null != fileList && fileList.length > 0) {
				for (int i = 0; i < fileList.length; i++) {
					String fileName = fileList[i];
					file = new File(path + "\\" + fileName);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					String lastModified = simpleDateFormat.format(
							file.lastModified()).toString();
					long fileSize = file.length();
					long fileSizeInKB = fileSize / 1024;
					// System.out.println(fileName + "::" + lastModified + "::"
					// + fileSizeInKB);
					fileDetails = new FileDetails();
					fileDetails.setFileName(fileName);
					fileDetails.setFileLastModifiedDate(lastModified);
					fileDetails.setFileSize(fileSizeInKB);
					fileDetailsList.add(fileDetails);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return fileDetailsList;
	}
}
