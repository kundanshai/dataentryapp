/************************************************************************************************************
 * @(#) FileUtil.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tcs.djb.rms.model.FileDetails;

/**
 * <p>
 * Utility Class for files.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 */
public class FileUtil {
	/**
	 * <p>
	 * Delete a directory. If it is not a directory it will ignore and return
	 * false.
	 * </p>
	 * 
	 * @param path
	 * @return true if deleted else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * <p>
	 * Delete a directory in the provide path. Path should be fully qualified.
	 * It should be a directory path. If path is a of a file it will ignore and
	 * return false.
	 * </p>
	 * 
	 * @param path
	 * @return true if deleted else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static boolean deleteDir(String path) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			return deleteDir(dir);
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * Delete a file in the provide path. Path should be fully qualified. It
	 * must contain the extension as well. If no extension is found it will
	 * ignore and return false.
	 * </p>
	 * 
	 * @param path
	 * @return true if deleted else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static boolean deleteFile(String path) {
		boolean isSuccess = false;
		try {
			File fileToBeDeleted = new File(path);
			if (fileToBeDeleted.isFile()) {
				isSuccess = fileToBeDeleted.delete();
			}
		} catch (Exception e) {
			// Ignored
		}
		return isSuccess;
	}

	/**
	 * <p>
	 * Get Directory List.
	 * </p>
	 * 
	 * @param path
	 * @return array of directory
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
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
	 * Get no of days of file creation before current date.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static int getFileCreatedBeforeDays(String path) {
		int noOfDays = 0;
		try {
			javaxt.io.File file = new javaxt.io.File(path);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String createDate = simpleDateFormat.format(file.getCreationTime());
			// System.out.println("" + createDate + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(createDate));
			noOfDays = (int) ((UtilityForAll.getTimeElapsedSeconds(createDate)) / (24 * 60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get no of hours of file creation before current time.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static long getFileCreatedBeforeHrs(String path) {
		long noOfDays = 0;
		try {
			javaxt.io.File file = new javaxt.io.File(path);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String createDate = simpleDateFormat.format(file.getCreationTime());
			// System.out.println("" + createDate + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(createDate));
			noOfDays = (long) ((UtilityForAll.getTimeElapsedSeconds(createDate)) / (60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get File Details.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static FileDetails getFileDetails(String path) {
		FileDetails fileDetails = null;
		try {
			javaxt.io.File file = new javaxt.io.File(path);
			fileDetails = new FileDetails();
			fileDetails.setFileName(file.getName());
			fileDetails.setFileType(file.getContentType());
			fileDetails.setFileCreateDate(file.getCreationTime().toString());
			fileDetails.setFileLastAccessedDate(file.getLastAccessTime()
					.toString());
			fileDetails.setFileSize(file.getSize());
			fileDetails.setFileSizeinKB((double) file.getSize() / 1024);
			fileDetails.setFilePath(file.getPath());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm:ss");
			String lastModified = simpleDateFormat.format(
					(new File(path)).lastModified()).toString();
			fileDetails.setFileLastModifiedDate(lastModified);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return fileDetails;
	}

	/**
	 * <p>
	 * Get File Details List.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
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
					file = new File(path + File.separator + fileName);
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

	/**
	 * <p>
	 * Get no of days of file creation before current date.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static int getFileLastAccesedBeforeDays(String path) {
		int noOfDays = 0;
		try {
			javaxt.io.File file = new javaxt.io.File(path);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String accessDate = simpleDateFormat.format(file
					.getLastAccessTime());
			// System.out.println("" + accessDate + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(accessDate));
			noOfDays = (int) ((UtilityForAll.getTimeElapsedSeconds(accessDate)) / (24 * 60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get no of hours of file creation before current time.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static long getFileLastAccesedBeforeHrs(String path) {
		long noOfDays = 0;
		try {
			javaxt.io.File file = new javaxt.io.File(path);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String accessDate = simpleDateFormat.format(file
					.getLastAccessTime());
			// System.out.println("" + accessDate + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(accessDate));
			noOfDays = (long) ((UtilityForAll.getTimeElapsedSeconds(accessDate)) / (60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get no of days of file last modified before from current date.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static int getFileLastModifiedBeforeDays(String path) {
		int noOfDays = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String lastModified = simpleDateFormat.format(
					(new File(path)).lastModified()).toString();
			// System.out.println(lastModified + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(lastModified));
			noOfDays = (int) ((UtilityForAll
					.getTimeElapsedSeconds(lastModified)) / (24 * 60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get no of hours of file last modified before from current time.
	 * </p>
	 * 
	 * @param path
	 * @return List of FileDetails
	 * @see FileDetails
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static long getFileLastModifiedBeforeHrs(String path) {
		long noOfDays = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String lastModified = simpleDateFormat.format(
					(new File(path)).lastModified()).toString();
			// System.out.println(lastModified + ">>"
			// + UtilityForAll.getTimeElapsedSeconds(lastModified));
			noOfDays = (long) ((UtilityForAll
					.getTimeElapsedSeconds(lastModified)) / (60 * 60));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return noOfDays;
	}

	/**
	 * <p>
	 * Get File List.
	 * </p>
	 * 
	 * @param path
	 * @return array of directory
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
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
	 * Get content of the file.
	 * </p>
	 * 
	 * @param path
	 * @return content of the file
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-09-2014
	 */
	public static String getFileText(String path) {
		String fileText = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;
			StringBuffer fileTextSB = new StringBuffer();
			fileTextSB.append("");
			while ((line = reader.readLine()) != null) {
				fileTextSB.append(line);
			}
			fileText = fileTextSB.toString();
			// AppLog.debugInfo("File Content::" + fileText);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return fileText;
	}

	/**
	 * <p>
	 * List all the files under a directory.
	 * </p>
	 * 
	 * @param directoryPath
	 *            to be listed
	 * @return ArrayList of File name
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static List<String> listFiles(String directoryPath) {
		List<String> list = new ArrayList<String>();
		File directory = new File(directoryPath);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				list.add(file.getName());
				// System.out.println(file.getName());
			}
		}
		return list;
	}

	/**
	 * List all files from a directory and its sub directories
	 * 
	 * @param directoryPath
	 *            to be listed
	 * @return ArrayList of ArrayList of file name
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static List<Object> listFilesAndFilesSubDirectories(
			String directoryPath) {
		List<Object> list = new ArrayList<Object>();
		File directory = new File(directoryPath);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				list.add(file.getName());
				// System.out.println(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				list.add(listFilesAndFilesSubDirectories(file.getAbsolutePath()));
			}
		}
		return list;
	}

	/**
	 * List all the files and folders from a directory
	 * 
	 * @param directoryPath
	 *            to be listed
	 * @return ArrayList of file name
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static List<String> listFilesAndFolders(String directoryPath) {
		List<String> list = new ArrayList<String>();
		File directory = new File(directoryPath);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			list.add(file.getName());
			// System.out.println(file.getName());
		}
		return list;
	}

	/**
	 * List all the folder under a directory
	 * 
	 * @param directoryPath
	 *            to be listed *
	 * @return ArrayList of Folder name
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static List<String> listFolders(String directoryPath) {
		List<String> list = new ArrayList<String>();
		File directory = new File(directoryPath);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isDirectory()) {
				list.add(file.getName());
				// System.out.println(file.getName());
			}
		}
		return list;
	}

	public static void main(String[] args) {
		final String directory = "/home/user";
		final String path = "/home/user/test.png";
		// System.out.println("listFiles>>>" + FileUtil.listFiles(directory));
		// System.out.println("getFileDetails>>>" + getFileDetails(path));
		System.out.println("\ngetFileDetailsList>>>"
				+ getFileDetailsList(directory));
		System.out.println("\ngetFileCreatedBefore>>"
				+ getFileCreatedBeforeHrs(path));
		System.out.println("\ngetFileLastModifiedBefore>>"
				+ getFileLastModifiedBeforeHrs(path));
		System.out.println("\ngetFileLastAccesedBefore>>"
				+ getFileLastAccesedBeforeHrs(path));

	}
}
