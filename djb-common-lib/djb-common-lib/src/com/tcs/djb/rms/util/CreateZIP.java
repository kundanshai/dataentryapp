/************************************************************************************************************
 * @(#) CreateZIP.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * Utility Class to create Zip file.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-09-2014
 */
public class CreateZIP {
	/**
	 * <p>
	 * Copy file from source to destination.
	 * </p>
	 * 
	 * @param srcFilePath
	 * @param dstFilePath
	 */
	public static void copyfile(String srcFilePath, String dstFilePath) {
		try {
			File srcFile = new File(srcFilePath);
			File dstFile = new File(dstFilePath);
			InputStream in = new FileInputStream(srcFile);
			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);
			// For Overwrite the file.
			OutputStream out = new FileOutputStream(dstFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			// System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			AppLog.error(ex);
			// ex.printStackTrace();
			// System.out
			// .println(ex.getMessage() + " in the specified directory.");
			// System.exit(0);
		} catch (IOException e) {
			AppLog.error(e);
			// e.printStackTrace();
			// System.out.println(e.getMessage());
		}
	}

	/**
	 * <p>
	 * Create Zip file of the list of files passed.
	 * </p>
	 * 
	 * @param filenames
	 * @param zipFileName
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public boolean createZIP(String[] filenames, String zipFileName,
			String userID) throws IOException {
		// Create a buffer for reading the files
		byte[] buf = new byte[4096];

		try {
			String templetZipPath = PropertyUtil.getProperty("ZIP_FILE_PATH");
			String zipFilePath = PropertyUtil.getProperty("UCMdocumentUpload")
					+ "/" + userID + "/" + zipFileName;
			// System.out.println("templetZipPath::" + templetZipPath
			// + "::zipFileName::" + zipFilePath);
			copyfile(templetZipPath, zipFilePath);

			File zipFile = new File(zipFilePath);

			// Create the ZIP file
			// String outFilename = "outfile.zip";
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					new FileOutputStream(zipFile));

			// Compress the files
			for (int i = 0; i < filenames.length; i++) {
				// System.out.println("filenames[i]:" + filenames[i]);
				if (null != filenames[i]) {
					File inFilename = new File(PropertyUtil
							.getProperty("UCMdocumentUpload")
							+ "/" + userID + "/" + filenames[i]);
					FileInputStream in = new FileInputStream(inFilename);
					// System.out.println("inFilename::" + inFilename);
					// Add ZIP entry to output stream.
					zipOutputStream.putNextEntry(new ZipEntry(filenames[i]));

					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						zipOutputStream.write(buf, 0, len);
					}
					// Complete the entry
					zipOutputStream.closeEntry();
					in.close();
				}
			}
			// Complete the ZIP file
			zipOutputStream.close();
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Create Zip file of the list of files passed.
	 * </p>
	 * 
	 * 
	 * @param filenames
	 * @param zipFileName
	 * @param sourcePath
	 * @param destPath
	 * @return
	 * @throws IOException
	 */
	public boolean createZIP(String[] filenames, String zipFileName,
			String sourcePath, String destPath) throws IOException {
		// Create a buffer for reading the files
		byte[] buf = new byte[4096];

		try {
			String templetZipPath = sourcePath;
			String zipFilePath = destPath + "/" + zipFileName;
			// System.out.println("templetZipPath::" + templetZipPath
			// + "::zipFileName::" + zipFilePath);
			copyfile(templetZipPath, zipFilePath);

			File zipFile = new File(zipFilePath);

			// Create the ZIP file
			// String outFilename = "outfile.zip";
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					new FileOutputStream(zipFile));

			// Compress the files
			for (int i = 0; i < filenames.length; i++) {
				// System.out.println("filenames[i]:" + filenames[i]);
				if (null != filenames[i]) {
					File inFilename = new File(destPath + "/" + filenames[i]);
					FileInputStream in = new FileInputStream(inFilename);
					// System.out.println("inFilename::" + inFilename);
					// Add ZIP entry to output stream.
					zipOutputStream.putNextEntry(new ZipEntry(filenames[i]));

					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						zipOutputStream.write(buf, 0, len);
					}
					// Complete the entry
					zipOutputStream.closeEntry();
					in.close();
				}
			}
			// Complete the ZIP file
			zipOutputStream.close();
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * delete Included Files In ZIP.
	 * </p>
	 * 
	 * @param userID
	 * @param filenames
	 * @return true if success else false
	 * @throws IOException
	 */
	public boolean deleteIncludedFilesInZIP(String userID, String[] filenames)
			throws IOException {
		boolean deleteSuccess = false;
		try {
			// Delete the files
			for (int i = 0; i < filenames.length; i++) {
				// System.out.println("filenames[i]:" + filenames[i]);
				File toDeleteFiles = new File(PropertyUtil
						.getProperty("UCMdocumentUpload")
						+ "/" + userID + "/" + filenames[i]);
				if (toDeleteFiles.exists()) {
					if (toDeleteFiles.delete()) {
						deleteSuccess = true;
					}
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);

		}
		return deleteSuccess;
	}

	/**
	 * <p>
	 * delete Included Files In ZIP.
	 * </p>
	 * 
	 * @param filenames
	 * @param path
	 * @return true if success else false
	 * @throws IOException
	 */
	public boolean deleteIncludedFilesInZIP(String[] filenames, String path)
			throws IOException {
		boolean deleteSuccess = false;
		try {
			// Delete the files
			for (int i = 0; i < filenames.length; i++) {
				// System.out.println("filenames[i]:" + filenames[i]);
				File toDeleteFiles = new File(path + "/" + filenames[i]);
				if (toDeleteFiles.exists()) {
					if (toDeleteFiles.delete()) {
						deleteSuccess = true;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
		}
		return deleteSuccess;
	}
}
