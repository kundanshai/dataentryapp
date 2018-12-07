/************************************************************************************************************
 * @(#) HmacUtil.java 1.0 13 July 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
public class HmacUtil {

	/**
	 * <p>
	 * Get HmacMD5 for an input String.
	 * </p>
	 * 
	 * @param input
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getHmacMD5(String input, String keyString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String digest = null;
		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
				"HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(key);
		byte[] bytes = mac.doFinal(input.getBytes("ASCII"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		digest = hash.toString();
		return digest;
	}

	/**
	 * <p>
	 * Get HmacSHA1 for an input String and key.
	 * </p>
	 * 
	 * @param msg
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getHmacSHA1(String msg, String keyString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String digest = null;

		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
				"HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		digest = hash.toString();

		return digest;
	}

	/**
	 * <p>
	 * Get HmacSHA256 for an input String and key.
	 * </p>
	 * 
	 * @param msg
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getHmacSHA256(String msg, String keyString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String digest = null;

		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
				"HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		digest = hash.toString();

		return digest;
	}

	/**
	 * <p>
	 * Get HmacSHA384 for an input String and key.
	 * </p>
	 * 
	 * @param msg
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getHmacSHA384(String msg, String keyString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String digest = null;

		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
				"HmacSHA384");
		Mac mac = Mac.getInstance("HmacSHA384");
		mac.init(key);
		byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		digest = hash.toString();

		return digest;
	}

	/**
	 * <p>
	 * Get HmacSHA512 for an input String and key.
	 * </p>
	 * 
	 * @param msg
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static String getHmacSHA512(String msg, String keyString)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException {
		String digest = null;
		SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
				"HmacSHA512");
		Mac mac = Mac.getInstance("HmacSHA512");
		mac.init(key);
		byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		digest = hash.toString();
		return digest;
	}
}