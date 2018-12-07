/************************************************************************************************************
 * @(#) Base64StringToFile.java 1.0 19 May 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.FileDataSource;

import com.sun.jersey.core.util.Base64;

/**
 * <p>
 * Base64 Converter utility class to convert file to String.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 19-05-2015
 */
public class Base64FileToString {
	/**
	 * <p>
	 * Convert a File to Base64 string.
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String convert(String filePath) throws IOException {
		File file = new File(filePath);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encode(bytes);
		String encodedString = new String(encoded);
		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
			// throw new IOException("File is too large....");
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		return bytes;
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param filePath
	 * @return size in bytes
	 * @throws IOException
	 */
	public static long size(String filePath) throws IOException {
		File file = new File(filePath);
		return file.length();
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param filePath
	 * @return size in KB
	 * @throws IOException
	 */
	public static long sizeInKb(String filePath) throws IOException {
		return size(filePath) / 1024;
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param filePath
	 * @return size in MB
	 * @throws IOException
	 */
	public static long sizeInMb(String filePath) throws IOException {
		return size(filePath) / (1024 * 1024);
	}

	/**
	 * <p>
	 * Get size of a Base64 string of a file.
	 * </p>
	 * 
	 * @param filePath
	 * @return size in MB
	 * @throws IOException
	 */
	public static String contentType(String filePath) throws IOException {
		File file = new File(filePath);
		FileDataSource fds = new FileDataSource(file);
		return fds.getContentType();
	}

	public static void main(String[] args) {
		try {
			String str = convert(args[0]);
			System.out.println("str::" + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
