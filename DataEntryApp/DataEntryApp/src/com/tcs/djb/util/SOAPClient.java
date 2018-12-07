/************************************************************************************************************
 * @(#) SOAPClient.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * <p>
 * Util class to connect to SOAP client.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * 
 */
public class SOAPClient {
	/**
	 * @return the requestPropertyMap
	 */
	public Map<String, String> getRequestPropertyMap() {
		return requestPropertyMap;
	}

	/**
	 * @param requestPropertyMap the requestPropertyMap to set
	 */
	public void setRequestPropertyMap(Map<String, String> requestPropertyMap) {
		this.requestPropertyMap = requestPropertyMap;
	}

Map<String, String> requestPropertyMap;

	public SOAPClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param requestPropertyMap
	 */
	public SOAPClient(Map<String, String> requestPropertyMap) {
		super();
		this.requestPropertyMap = requestPropertyMap;
	}

	/**
	 * <p>
	 * This method is used to post request to a SOAP client
	 * </p>
	 * 
	 * @param xml
	 * @param server
	 * @param soapAction
	 * @return
	 * @throws Exception
	 */
	public String call(String xml, String server, String soapAction)
			throws Exception {
		AppLog.begin();
		String response = null;
		InputStream inputStream = null;
		URLConnection uc = null;
		HttpURLConnection connection = null;
		try {
			URL u = new URL(server);
			uc = u.openConnection();
			connection = (HttpURLConnection) uc;

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("SOAPAction", soapAction);
			connection.setRequestProperty("Content-Type",
					"text/xml;charset=UTF-8");
			if(null!=requestPropertyMap&&!requestPropertyMap.isEmpty()){
				
			}
			OutputStream out = connection.getOutputStream();
			Writer wout = new OutputStreamWriter(out);

			wout.write("<?xml version='1.0'?>\r\n");
			wout.write(xml);
			wout.flush();
			wout.close();
			AppLog.info("SOAP called>>>");
			StringWriter stringWriter = new StringWriter(8192);
			inputStream = connection.getInputStream();
			int c;
			while ((c = inputStream.read()) != -1) {
				stringWriter.write(c);
			}
			response = stringWriter.toString();
			AppLog.info("WebServiceCall responseXML >>>" + response);

		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				AppLog.end();
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return response;
	}

}
