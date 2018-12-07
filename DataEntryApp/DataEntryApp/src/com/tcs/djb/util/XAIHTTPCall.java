/************************************************************************************************************
 * @(#) XAIHTTPCall.java   22 Mar 2002
 *
 *************************************************************************************************************/

package com.tcs.djb.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;

import com.tcs.djb.constants.DJBConstants;

/**
 * <p>
 * This class is used while for making ccb xai service calls.
 * </p>
 * 
 * @author Matiur Rahman
 * @createdDate 04-12-2012
 * @history: Rajib Hazarika added parameterised constructor to the class having
 *           authCookie as parameter as per JTrac DJB-2024
 * @history: Atanu commented the authCookie on 10-03-2016 and added it from Property file as per jTrac ANDROID-293        
 */
public class XAIHTTPCall {

	private String authCookie;
	private URL url;
	
	/**
	 * <p>
	 * Creates a new instance of XAIHTTPCall
	 * </p>
	 */
	public XAIHTTPCall() {
		AppLog.begin();
		try {
			String ccbServerAddress = DJBConstants.CCB_SERVICE_PROVIDER_URL;
			AppLog.info("::CC&B Server Address::" + ccbServerAddress);
			url = new URL(ccbServerAddress);
			/*Start : Atanu commented the authCookie on 10-03-2016 and added it from Property file*/
			//authCookie = "V0VCOnNlbGZzZXJ2aWNl";
			authCookie = DJBConstants.CCB_CONNECT_DEFAULT_AUTHCOOKIE;
		} catch (MalformedURLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * constructor with dynamicAuthCookie for calling ccb xai service.
	 * </p>
	 * 
	 * @history 17-01-2014 Rajib Hazarika Added Overloaded constructor for
	 *          XaiHttpCall. Parameter is dynamic authentic cookie)
	 * @param authCookie
	 */
	public XAIHTTPCall(String dynamicAuthCookie) {
		// super();
		if (null != dynamicAuthCookie
				&& !"".equalsIgnoreCase(dynamicAuthCookie.trim())) {
			this.authCookie = dynamicAuthCookie.trim();
		}
		try {
			String serverAddress = DJBConstants.CCB_SERVICE_PROVIDER_URL;
			AppLog.info("::Connecting CC&B at::" + serverAddress
					+ ":dynamicAuthCookie:" + dynamicAuthCookie);
			url = new URL(serverAddress);
		} catch (MalformedURLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		}
	}

	/**
	 * <p>
	 * Method for calling CC&B XAI Service. Calls the service creating http url
	 * connection passing the credential in HTTP Header.
	 * </p>
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public String callXAIServer(String xml) throws IOException {
		AppLog.begin();
		StringWriter stringWriter = new StringWriter(8192);
		String responseXML = "";
		try {
			String responseCharset;
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("WWW-Authenticate", "Basic "
					+ authCookie + " realm=myrealm");
			connection.setRequestProperty("Authorization", "Basic "
					+ authCookie);
			connection.setRequestProperty("Content-Type",
					"text/xml;charset=UTF-8");

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(connection.getOutputStream(),
							"UTF-8")));
			out.println(xml);
			out.close();
			responseCharset = connection.getContentType();
			if (null != responseCharset) {
				int i = responseCharset.indexOf("charset=");
				if (i != -1) {
					responseCharset = responseCharset.substring(i + 8)
							.toUpperCase();
				} else {
					responseCharset = null;
				}
			}

			BufferedReader in;
			if (null == responseCharset) {
				in = new BufferedReader(new InputStreamReader(connection
						.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(connection
						.getInputStream(), responseCharset));
			}

			int c;
			while ((c = in.read()) != -1) {
				stringWriter.write(c);
			}
			responseXML = stringWriter.toString();
		} catch (RemoteException e) {
			AppLog.error(e);
		} catch (ConnectException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return responseXML;
	}
}
