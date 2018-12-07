/************************************************************************************************************
 * @(#) XAIHTTPCall.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * Utility class to invoke XAIHTTP services of CC&B.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 04-10-2014.
 * 
 */
public class XAIHTTPCall {
	/**
	 * 
	 */
	private String authCookie;

	/**
	 * 
	 */
	private URL url;

	/**
	 * Default Constructor.
	 */
	public XAIHTTPCall() throws MalformedURLException, Exception {
		AppLog.begin();
		String serverAddress = PropertyUtil.getProperty("CCBserverAddress");
		AppLog.debugInfo("::Connecting CC&B at::" + serverAddress);
		url = new URL(serverAddress);
		authCookie = null != PropertyUtil
				.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE") ? PropertyUtil
				.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE")
				: "V0VCOnNlbGZzZXJ2aWNl";
		AppLog.end();
	}

	/**
	 * @param serverAddress
	 */
	public XAIHTTPCall(String serverAddress) throws MalformedURLException,
			Exception {
		AppLog.begin();
		AppLog.debugInfo("::Connecting CC&B at::" + serverAddress);
		url = new URL(serverAddress);
		authCookie = null != PropertyUtil
				.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE") ? PropertyUtil
				.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE")
				: "V0VCOnNlbGZzZXJ2aWNl";
		AppLog.end();
	}

	/**
	 * <p>
	 * Call CC&B XAI Service with the authcookie passed on the basis of request
	 * xml.
	 * </p>
	 * 
	 * @param xml
	 *            request xml
	 * @param authCookie
	 *            Authorization cookie
	 * @return response xml
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-10-2014.
	 */
	public static String callXAIServer(String xml, String authCookie)
			throws IOException, Exception {
		AppLog.begin();
		AppLog.debugInfo("Request XML::" + xml);
		StringWriter s = new StringWriter(8192);
		String ResponseCharset;
		String serverAddress = PropertyUtil.getProperty("CCB_XAI_URL");
		int requestTimeout = null != PropertyUtil
				.getProperty("REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS") ? Integer
				.parseInt(PropertyUtil.getProperty(
						"REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS").trim()) * 1000
				: 0;
		if (null == authCookie) {
			authCookie = PropertyUtil
					.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE");// "V0VCOnNlbGZzZXJ2aWNl";
			AppLog.debugInfo("::Default User Connecting CC&B at::"
					+ serverAddress + "::With Request Timeout::"
					+ requestTimeout);
		} else {
			AppLog.debugInfo("AuthCookie::" + authCookie
					+ "::Connecting CC&B at::" + serverAddress
					+ "::With Request Timeout::" + requestTimeout);
		}
		URL url = new URL(serverAddress);
		URLConnection connection = url.openConnection();
		if (requestTimeout != 0) {
			connection.setConnectTimeout(requestTimeout);
			connection.setReadTimeout(requestTimeout);
		}
		connection.setDoOutput(true);
		connection.setRequestProperty("WWW-Authenticate", "Basic " + authCookie
				+ " realm=myrealm");
		connection.setRequestProperty("Authorization", "Basic " + authCookie);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), "UTF-8")));
		out.println(xml);
		out.close();
		ResponseCharset = connection.getContentType();
		if (ResponseCharset != null) {
			int i = ResponseCharset.indexOf("charset=");
			if (i != -1) {
				ResponseCharset = ResponseCharset.substring(i + 8)
						.toUpperCase();
			} else {
				ResponseCharset = null;
			}
		}
		BufferedReader in;
		if (ResponseCharset == null) {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ResponseCharset));
		}
		int c;
		while ((c = in.read()) != -1) {
			s.write(c);
		}
		AppLog.debugInfo("Response XML::" + s.toString());
		AppLog.end();
		return s.toString();
	}

	/**
	 * <p>
	 * Call CC&B XAI Service with the authcookie passed on the basis of request
	 * xml.
	 * </p>
	 * 
	 * @param xml
	 *            request xml
	 * @param authCookie
	 *            Authorization cookie
	 * 
	 * @param sessionDetails
	 * @throws IOException
	 * @throws Exception
	 * @return response xml
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-10-2014.
	 */
	public static String callXAIServer(String xml, String authCookie,
			SessionDetails sessionDetails) throws IOException, Exception {
		AppLog.begin(sessionDetails);
		AppLog.debugInfo(sessionDetails, "Request XML::" + xml);
		StringWriter s = new StringWriter(8192);
		String ResponseCharset;
		String serverAddress = PropertyUtil.getProperty("CCB_XAI_URL");
		int requestTimeout = null != PropertyUtil
				.getProperty("REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS") ? Integer
				.parseInt(PropertyUtil.getProperty(
						"REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS").trim()) * 1000
				: 0;
		if (null == authCookie) {
			authCookie = PropertyUtil
					.getProperty("CCB_XAI_DEFAULT_AUTH_COOKIE");// "V0VCOnNlbGZzZXJ2aWNl";
			AppLog.debugInfo(sessionDetails,
					"::Default User Connecting CC&B at::" + serverAddress);
		} else {
			AppLog.debugInfo(sessionDetails, "AuthCookie::" + authCookie
					+ "::Connecting CC&B at::" + serverAddress
					+ "::With Request Timeout::" + requestTimeout);
		}
		URL url = new URL(serverAddress);
		URLConnection connection = url.openConnection();
		if (requestTimeout != 0) {
			connection.setConnectTimeout(requestTimeout);
			connection.setReadTimeout(requestTimeout);
		}
		connection.setDoOutput(true);
		connection.setRequestProperty("WWW-Authenticate", "Basic " + authCookie
				+ " realm=myrealm");
		connection.setRequestProperty("Authorization", "Basic " + authCookie);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), "UTF-8")));
		out.println(xml);
		out.close();
		ResponseCharset = connection.getContentType();
		if (ResponseCharset != null) {
			int i = ResponseCharset.indexOf("charset=");
			if (i != -1) {
				ResponseCharset = ResponseCharset.substring(i + 8)
						.toUpperCase();
			} else {
				ResponseCharset = null;
			}
		}
		BufferedReader in;
		if (ResponseCharset == null) {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ResponseCharset));
		}
		int c;
		while ((c = in.read()) != -1) {
			s.write(c);
		}
		AppLog.debugInfo(sessionDetails, "Response XML::" + s.toString());
		AppLog.end(sessionDetails);
		return s.toString();
	}

	/**
	 * <p>
	 * Call CC&B XAI Service with the authcookie passed on the basis of request
	 * xml and server Address.
	 * </p>
	 * 
	 * @param xml
	 *            request xml
	 * @param authCookie
	 *            Authorization cookie
	 * @param serverAddress
	 *            URL of the server
	 * @return response xml
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-10-2014.
	 */
	public static String callXAIServer(String xml, String authCookie,
			String serverAddress) throws IOException, Exception {
		AppLog.begin();
		AppLog.debugInfo("Request XML::" + xml);
		StringWriter s = new StringWriter(8192);
		String ResponseCharset;
		int requestTimeout = null != PropertyUtil
				.getProperty("REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS") ? Integer
				.parseInt(PropertyUtil.getProperty(
						"REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS").trim()) * 1000
				: 0;
		AppLog.debugInfo("AuthCookie::" + authCookie + "::Connecting CC&B at::"
				+ serverAddress + "::With Request Timeout::" + requestTimeout);
		URL url = new URL(serverAddress);
		URLConnection connection = url.openConnection();
		if (requestTimeout != 0) {
			connection.setConnectTimeout(requestTimeout);
			connection.setReadTimeout(requestTimeout);
		}
		connection.setDoOutput(true);
		connection.setRequestProperty("WWW-Authenticate", "Basic " + authCookie
				+ " realm=myrealm");
		connection.setRequestProperty("Authorization", "Basic " + authCookie);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), "UTF-8")));
		out.println(xml);
		out.close();
		ResponseCharset = connection.getContentType();
		if (ResponseCharset != null) {
			int i = ResponseCharset.indexOf("charset=");
			if (i != -1) {
				ResponseCharset = ResponseCharset.substring(i + 8)
						.toUpperCase();
			} else {
				ResponseCharset = null;
			}
		}
		BufferedReader in;
		if (ResponseCharset == null) {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ResponseCharset));
		}
		int c;
		while ((c = in.read()) != -1) {
			s.write(c);
		}
		AppLog.debugInfo("Response XML::" + s.toString());
		AppLog.end();
		return s.toString();
	}

	/**
	 * <p>
	 * Call CC&B XAI Service with the authcookie passed on the basis of request
	 * xml and server Address.
	 * </p>
	 * 
	 * @param xml
	 *            request xml
	 * @param authCookie
	 *            Authorization cookie
	 * @param serverAddress
	 *            URL of the server
	 * @param sessionDetails
	 * 
	 * @return response xml
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-10-2014.
	 */
	public static String callXAIServer(String xml, String authCookie,
			String serverAddress, SessionDetails sessionDetails)
			throws IOException, Exception {
		AppLog.begin(sessionDetails);
		AppLog.debugInfo(sessionDetails, "Request XML::" + xml);
		StringWriter s = new StringWriter(8192);
		String ResponseCharset;
		int requestTimeout = null != PropertyUtil
				.getProperty("REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS") ? Integer
				.parseInt(PropertyUtil.getProperty(
						"REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS").trim()) * 1000
				: 0;
		AppLog.debugInfo(sessionDetails, "AuthCookie::" + authCookie
				+ "::Connecting CC&B at::" + serverAddress
				+ "::With Request Timeout::" + requestTimeout);
		URL url = new URL(serverAddress);
		URLConnection connection = url.openConnection();
		if (requestTimeout != 0) {
			connection.setConnectTimeout(requestTimeout);
			connection.setReadTimeout(requestTimeout);
		}
		connection.setDoOutput(true);
		connection.setRequestProperty("WWW-Authenticate", "Basic " + authCookie
				+ " realm=myrealm");
		connection.setRequestProperty("Authorization", "Basic " + authCookie);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), "UTF-8")));
		out.println(xml);
		out.close();
		ResponseCharset = connection.getContentType();
		if (ResponseCharset != null) {
			int i = ResponseCharset.indexOf("charset=");
			if (i != -1) {
				ResponseCharset = ResponseCharset.substring(i + 8)
						.toUpperCase();
			} else {
				ResponseCharset = null;
			}
		}
		BufferedReader in;
		if (ResponseCharset == null) {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ResponseCharset));
		}
		int c;
		while ((c = in.read()) != -1) {
			s.write(c);
		}
		AppLog.debugInfo(sessionDetails, "Response XML::" + s.toString());
		AppLog.end(sessionDetails);
		return s.toString();
	}

	/**
	 * <p>
	 * Call CC&B XAI Service with default authcookie on the basis of request
	 * xml.
	 * </p>
	 * 
	 * @param xml
	 * @return response xml
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-10-2014.
	 */
	public String callXAIServer(String xml) throws IOException, Exception {
		AppLog.begin();
		StringWriter s = new StringWriter(8192);
		String ResponseCharset;
		URLConnection connection = url.openConnection();
		int requestTimeout = null != PropertyUtil
				.getProperty("REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS") ? Integer
				.parseInt(PropertyUtil.getProperty(
						"REQUEST_TIMEOUT_FOR_REMOTE_URL_IN_SECONDS").trim()) * 1000
				: 0;
		AppLog.debugInfo("AuthCookie::" + authCookie + "::Connecting CC&B at::"
				+ url + "::With Request Timeout::" + requestTimeout);
		if (requestTimeout != 0) {
			connection.setConnectTimeout(requestTimeout);
			connection.setReadTimeout(requestTimeout);
		}
		connection.setDoOutput(true);
		connection.setRequestProperty("WWW-Authenticate", "Basic " + authCookie
				+ " realm=myrealm");
		connection.setRequestProperty("Authorization", "Basic " + authCookie);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), "UTF-8")));
		out.println(xml);
		out.close();
		ResponseCharset = connection.getContentType();
		if (ResponseCharset != null) {
			int i = ResponseCharset.indexOf("charset=");
			if (i != -1)
				ResponseCharset = ResponseCharset.substring(i + 8)
						.toUpperCase();
			else
				ResponseCharset = null;
		}
		BufferedReader in;
		if (ResponseCharset == null) {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ResponseCharset));
		}
		int c;

		while ((c = in.read()) != -1) {
			s.write(c);
		}
		AppLog.end();
		return s.toString();
	}
}
