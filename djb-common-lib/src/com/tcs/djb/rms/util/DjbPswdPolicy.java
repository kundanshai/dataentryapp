/************************************************************************************************************
 * @(#) DjbPswdPolicy.java 1.0 09 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Utility Class to check DJB Password Policy as per Regular expression.
 * </p>
 * 
 * @see Matcher
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-06-2015
 */
public class DjbPswdPolicy {
	/**
	 * <p>
	 * Password Should Contain at least 6 Alpha Numeric Characters which should:
	 * <br/>
	 * 1.Contain at least one Uppercase Letter. <br/>
	 * 2.Contain at least one Lowercase Letter. <br/>
	 * 3.Contain at least one Numeric Character. <br/>
	 * 4.Contain at least one Special Character other than [' " & ; : %] and
	 * blank space.
	 */
	private final static String REGEX_DJB_PASSWORD_POLICY = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$^])[A-Za-z0-9~!@#$^]{6,}$";

	/**
	 * <p>
	 * Check if a input string matches the policy with default Regular
	 * Expression.
	 * </p>
	 * 
	 * @param inputSting
	 *            to check
	 * @return true if matched else false
	 * @throws Exception
	 */
	public static boolean check(String inputSting) throws Exception {
		return match(inputSting, REGEX_DJB_PASSWORD_POLICY);
	}

	/**
	 * <p>
	 * Check if a input string matches the policy with Regular Expression passed
	 * as parameter.
	 * </p>
	 * 
	 * @param inputSting
	 *            to check
	 * @param regexExp
	 *            Regular expression for to match with
	 * @return true if matched else false
	 * @throws Exception
	 */
	public static boolean check(String inputSting, String regexExp)
			throws Exception {
		return match(inputSting, regexExp);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("abc@123::Result>>" + check("abc@123"));
			System.out
					.println("Absssssc@123::Result>>" + check("Absssssc@123"));
			System.out.println("Abc123::Result>>" + check("Abc123"));
			System.out.println("Abcdef::Result>>" + check("Abcdef"));
			System.out.println("@1abcdef::Result>>" + check("@1abcdef"));
			System.out.println("@1Abcdef::Result>>" + check("@1Abcdef"));
			System.out.println("#1Abcdef::Result>>" + check("#1Abcdef"));
			System.out.println("!1Abcdef::Result>>" + check("!1Abcdef"));
			System.out.println("~1Abcdef::Result>>" + check("~1Abcdef"));
			System.out.println("^1Abcdef::Result>>" + check("^1Abcdef"));

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * <p>
	 * Check presence of particular character in the string using
	 * {@link #Pattern.matcher}
	 * </p>
	 * 
	 * @param inputParam
	 * @param regexExp
	 * @return true if matched else false
	 * @throws Exception
	 */
	private static final boolean match(String inputParam, String regexExp)
			throws Exception {
		boolean isFound = false;
		Pattern p = Pattern.compile(regexExp);
		Matcher m = p.matcher(inputParam);
		isFound = m.find();
		if (isFound) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public DjbPswdPolicy() {

	}
}
