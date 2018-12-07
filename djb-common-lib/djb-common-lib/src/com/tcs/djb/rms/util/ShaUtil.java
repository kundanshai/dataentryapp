/************************************************************************************************************
 * @(#) ShaUtil.java 1.0 13 July 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.security.MessageDigest;

/**
 * <p>
 * Encryption Utility class to generate encrypted values. It constructs the Hash
 * value to the inputs.
 * </p>
 * <p>
 * <b>It contains:</b>
 * <ul>
 * <li>{@link #getHmacMD5(String, String)}.</li>
 * <li>{@link #getSha256(String, String)}.</li>
 * <li>{@link #getHmacSha384(String, String)}.</li>
 * <li>{@link #getHmacSHA512(String, String)}.</li>
 * </ul>
 * <p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 13-07-2015
 */
public class ShaUtil {
	/**
	 * <p>
	 * Generate binary to hex value
	 * </p>
	 * 
	 * @param ba
	 * @return
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	private static String binaryToHex(byte[] ba) throws Exception {
		if ((ba == null) || (ba.length == 0)) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);

		for (int x = 0; x < ba.length; x++) {
			String hexNumber = "0" + Integer.toHexString(0xFF & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * Get Sha1 for an input String.
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getSha1(String str) throws Exception {
		byte[] binary = (byte[]) null;
		MessageDigest sh = MessageDigest.getInstance("SHA-1");
		sh.update(str.getBytes("UTF-8"));
		binary = sh.digest();
		return binaryToHex(binary);
	}

	/**
	 * <p>
	 * Get Sha256 for an input String.
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getSha256(String str) throws Exception {
		byte[] binary = (byte[]) null;
		MessageDigest sh = MessageDigest.getInstance("SHA-256");
		sh.update(str.getBytes("UTF-8"));
		binary = sh.digest();
		return binaryToHex(binary);
	}

	/**
	 * <p>
	 * Get Sha384 for an input String.
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getSha384(String str) throws Exception {
		byte[] binary = (byte[]) null;
		MessageDigest sh = MessageDigest.getInstance("SHA-384");
		sh.update(str.getBytes("UTF-8"));
		binary = sh.digest();
		return binaryToHex(binary);
	}

	/**
	 * <p>
	 * Get Sha512 for an input String.
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getSha512(String str) throws Exception {
		byte[] binary = (byte[]) null;
		MessageDigest sh = MessageDigest.getInstance("SHA-512");
		sh.update(str.getBytes("UTF-8"));
		binary = sh.digest();
		return binaryToHex(binary);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("1.Sha1>>>>>>>>>>>>>>>"
				+ getSha1("899562648456|2015012014|E0348L13|djbhhd357m"));
		System.out.println("2.Sha256>>>>>>>>>>>>>>>"
				+ getSha256("899562648456|2015012014|E0348L13|djbhhd357m"));
		System.out.println("3.Sha384>>>>>>>>>>>>>>>"
				+ getSha384("899562648456|2015012014|E0348L13|djbhhd357m"));
		System.out.println("4.Sha512>>>>>>>>>>>>>>>"
				+ getSha512("899562648456|2015012014|E0348L13|djbhhd357m"));

	}
}