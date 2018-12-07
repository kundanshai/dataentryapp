/************************************************************************************************************
 * @(#) Base64StringToFile.java 1.0 19 May 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.FileDataSource;

import com.sun.jersey.core.util.Base64;

/**
 * <p>
 * Base64 Converter utility class to convert Base64 String to file.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 19-05-2015
 */
public class Base64StringToFile {
	/**
	 * <p>
	 * Convert a Base64 string and create a file and store in the given path.
	 * </p>
	 * 
	 * @param fileString
	 * @param fileName
	 * @param filePath
	 * @throws IOException
	 */
	public static void convert(String fileString, String fileName,
			String filePath) throws IOException {
		byte[] bytes = Base64.decode(fileString);
		File file = new File(filePath + fileName);
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(bytes);
		fop.flush();
		fop.close();
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param fileString
	 * @return size in bytes
	 * @throws IOException
	 */
	public static long size(String fileString) throws IOException {
		byte[] bytes = Base64.decode(fileString);
		return bytes.length;
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param fileString
	 * @return size in KB
	 * @throws IOException
	 */
	public static long sizeInKb(String fileString) throws IOException {
		return size(fileString) / 1024;
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param fileString
	 * @return size in KB
	 * @throws IOException
	 */
	public static long sizeInMb(String fileString) throws IOException {
		return size(fileString) / (1024 * 1024);
	}

	public static String contentType(String filePath) throws IOException {
		File file = new File(filePath);
		FileDataSource fds = new FileDataSource(file);
		return fds.getContentType();
	}

	public static void main(String[] args) {
		try {
			convert(Base64FileToString.convert(args[0]), args[1], args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
