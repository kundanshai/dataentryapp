/************************************************************************************************************
 * @(#) OtpGenerator.java 1.0 09 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.Random;
import java.util.regex.Matcher;

/**
 * <p>
 * Utility Class to generate OTP.
 * </p>
 * 
 * @see Matcher
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-06-2015
 */
public class OtpGenerator {
	private final static char[] num = "0123456789".toCharArray();

	/**
	 * <p>
	 * Method for generating OTP of length 6.
	 * </p>
	 * 
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 * 
	 * @param maxLength
	 * @return OTP
	 * @throws Exception
	 */
	public static String generate() throws Exception {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)));
		for (int i = 0; i < 6; i++) {
			sb.append(num[rnd.nextInt(num.length)]);
		}
		String otp = sb.toString();
		return otp;
	}

	/**
	 * <p>
	 * Method for generating OTP of desired length sent as parameter.
	 * </p>
	 * 
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 09-06-2015
	 * 
	 * @param length
	 * @return OTP
	 * @throws Exception
	 */
	public static String generate(int length) throws Exception {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)));
		for (int i = 0; i < length; i++) {
			sb.append(num[rnd.nextInt(num.length)]);
		}
		String otp = sb.toString();
		otp = otp.substring(0, length);
		return otp;
	}

	public static void main(String[] args) {
		try {
			System.out.println("1>>" + OtpGenerator.generate());
			System.out.println("2>>" + OtpGenerator.generate(6));
			System.out.println("3>>" + OtpGenerator.generate(32));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
