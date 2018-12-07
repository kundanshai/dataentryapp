/************************************************************************************************************
 * @(#) UcmDocUploadUtil.java 1.0 15 Dec 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.serialize.HdaBinderSerializer;
import oracle.stellent.ridc.protocol.ServiceResponse;

import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * Utility class to Upload file to UCM. The parameter of the file is defined in
 * property file with property <code>UCM_ACCESS_PATH</code>,
 * <code>UCMtempFile</code>,<code>UCMserverAddress</code>,
 * <code>UCM_USERNAME</code>,<code>UCM_PASSWRD</code>.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-12-2015
 */
public class UcmDocUploadUtil {
	private static String UCM_ACCESS_PATH = PropertyUtil
			.getProperty("UCM_ACCESS_PATH");

	private static String UCM_PASSWRD = PropertyUtil.getProperty("UCM_PASSWRD");

	private static String UCM_SERVER_URL = PropertyUtil
			.getProperty("UCM_SERVER_URL");

	private static String UCM_TEMP_FILE = PropertyUtil
			.getProperty("UCM_TEMP_FILE_PATH");

	private static String UCM_USERNAME = PropertyUtil
			.getProperty("UCM_USERNAME");

	/**
	 * <p>
	 * Get UCM Access Path.
	 * </p>
	 * 
	 * @param op
	 * @return
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static String getUcmAccessPath(File op, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String ucmAccessPath = "";
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(op));
			String mes = prop.getProperty("WebfilePath");
			AppLog.debugInfo(sessionDetails, "UCM WEB PATH ::" + mes);
			ucmAccessPath = UCM_ACCESS_PATH
					+ mes.substring(mes.indexOf("groups/"));
		} catch (FileNotFoundException e) {
			AppLog.error(sessionDetails, e);
		} catch (IOException e) {
			AppLog.error(sessionDetails, e);
		}
		AppLog.debugInfo(sessionDetails, "UCM ACEESS PATH ::" + ucmAccessPath);
		AppLog.end(sessionDetails);
		return ucmAccessPath;

	}

	/**
	 * <p>
	 * Get UCM Access Url
	 * </p>
	 * 
	 * @param str
	 * @param sessionDetails
	 * @return
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public static String getUcmAccessUrl(String str,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String ucmAccessUrl = null;
		try {
			String[] tmp = str.split("/");
			String s = (tmp[tmp.length - 1]);
			StringBuffer sbr = new StringBuffer(s);
			sbr = sbr.delete(sbr.indexOf("~"), sbr.indexOf("."));
			ucmAccessUrl = tmp[0] + "//";
			for (int k = 2; k <= tmp.length - 2; k++) {
				ucmAccessUrl = ucmAccessUrl + tmp[k] + "/";
			}
			ucmAccessUrl = ucmAccessUrl + sbr.toString();
		} catch (Exception e) {
			AppLog.error(sessionDetails, e);
		}
		AppLog.debugInfo(sessionDetails, "URL::" + ucmAccessUrl);
		AppLog.end(sessionDetails);
		return ucmAccessUrl;

	}

	public static void main(String[] args) {
		try {

			Map<String, String> contentMap = new HashMap<String, String>();
			SessionDetails sessionDetails = new SessionDetails();
			contentMap.put("xTitle", "Mr");
			contentMap.put("dDocTitle", "Test Upload");
			contentMap.put("xTypeOfDocument", "Bill Information");
			contentMap.put("xDocumentNo", "123");
			contentMap.put("xKNo", "1234567890");
			AppLog.info("::Uploded Document To UCM Against::KNO::"
					+ contentMap.get("xKNo") + "::AppRefNo::"
					+ contentMap.get("AppRefNo"));
			File op;

			UcmDocUploadUtil ucmDocUploadUtil = new UcmDocUploadUtil();
			op = ucmDocUploadUtil.checkInContent("/home/user/test.png", "test.png",
					contentMap, 60 * 1000, sessionDetails);
			String msgPath = UcmDocUploadUtil.getUcmAccessPath(op,
					sessionDetails);
			String ucmPath = UcmDocUploadUtil.getUcmAccessUrl(msgPath,
					sessionDetails);
			System.out.println("msgPath>>>" + msgPath + ">>>ucmPath>>>"
					+ ucmPath);
		} catch (Exception e) {
			AppLog.error(e);

		}
	}

	DataBinder dataBinder = null;
	IdcClient<?, ?, ?> idcClient = null;
	IdcClientManager manager = null;
	ServiceResponse response = null;

	DataBinder responseData = null;

	HdaBinderSerializer serializer = null;

	IdcContext userContext = null;

	/**
	 * <p>
	 * Method to check in content to UCM. During actual implementation some of
	 * the configuration information can be passed as Parameters using a POJO or
	 * individually.
	 * </p>
	 * 
	 * @param path
	 * @param fileName
	 * @param contentMap
	 * @param connTimeOut
	 * @param sessionDetails
	 * @return
	 * @throws IdcClientException
	 * @throws IOException
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public File checkInContent(String filePath, String fileName,
			Map<String, String> contentMap, int connTimeOut,
			SessionDetails sessionDetails) throws IdcClientException,
			IOException {
		AppLog.begin(sessionDetails);
		File outputFile = new File(UCM_TEMP_FILE + fileName);
		manager = new IdcClientManager();
		// Required for IDC connection.
		idcClient = manager.createClient(UCM_SERVER_URL);
		idcClient.getConfig().setConnectionWaitTime(connTimeOut);
		userContext = new IdcContext(UCM_USERNAME, UCM_PASSWRD);

		/*
		 * Create an HdaBinderSerializer; this is not necessary, but it allows
		 * us to serialize the request and response data binders
		 */

		serializer = new HdaBinderSerializer("UTF-8",
				idcClient.getDataFactory());

		// Databinder for checkin request

		dataBinder = idcClient.createBinder();

		dataBinder.putLocal("IdcService", "CHECKIN_NEW");
		dataBinder.putLocal("dDocType", "DOCUMENT");
		dataBinder.putLocal("dSecurityGroup", "Public");
		dataBinder.putLocal("dDocAuthor", UCM_USERNAME);
		for (Map.Entry<String, String> entry : contentMap.entrySet()) {
			dataBinder.putLocal(entry.getKey(), entry.getValue());
		}

		dataBinder.addFile("primaryFile", new File(filePath));

		dataBinder.putLocal("doFileCopy", "0");
		// Write the data binder for the request to stdout
		serializer.serializeBinder(System.out, dataBinder);
		// Send the request to Content Server
		response = idcClient.sendRequest(userContext, dataBinder);
		// Get the data binder for the response from Content Server
		responseData = response.getResponseAsBinder();
		// Write the response data binder to stdout
		serializer.serializeBinder(System.out, responseData);

		FileOutputStream fos = new FileOutputStream(outputFile);
		serializer.serializeBinder(fos, responseData);
		fos.close();
		AppLog.end(sessionDetails);
		return outputFile;
	}
}
