/************************************************************************************************************
 * @(#) UcmDocUploadUtil.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.serialize.HdaBinderSerializer;
import oracle.stellent.ridc.protocol.ServiceResponse;

/**
 * <p>
 * This util class is used for uploading documents to UCM
 * </p>
 * 
 * @author Aniket Chatterjee (Tata Consultancy Services)
 */
public class UcmDocUploadUtil {
	private static String UCM_ACCESS_PATH = PropertyUtil
			.getProperty("UCM_ACCESS_PATH");

	/**
	 * Class variables.
	 */

	IdcClientManager manager = null;
	IdcClient<?, ?, ?> idcClient = null;
	HdaBinderSerializer serializer = null;
	ServiceResponse response = null;
	IdcContext userContext = null;
	DataBinder dataBinder = null;
	DataBinder responseData = null;

	/**
	 * <p>
	 * Method to checkin content to UCM.
	 * </p>
	 * 
	 * During actual implementation some of the configuration information can be
	 * passed as Parameters using a POJO or individually. Modified by Matiur
	 * Rahman on 02-07-2012
	 * 
	 */

	public File checkInContent(String path,
			HashMap<String, String> contentsMap, String sessionId) {
		AppLog.begin();
		// String msgPath = "NA";
		File outputFile = null;

		try {
			outputFile = new File(PropertyUtil.getProperty("UCMtempFile")
					+ sessionId);
			manager = new IdcClientManager();

			String uplodingFile = path;
			// Required for IDC connection.

			// idcClient = manager.createClient("idc://172.18.90.15:4444");

			idcClient = manager.createClient(PropertyUtil
					.getProperty("UCMserverAddress"));
			// idcClient = manager.createClient("idc://172.21.129.101:4444");
			// idcClient = manager.createClient("idc://172.18.90.15:4444");

			idcClient.getConfig().setConnectionWaitTime(10000);

			// Create new context using the 'weblogic' user.
			// Password is not required if it is a Intradoc connection.

			userContext = new IdcContext(PropertyUtil
					.getProperty("UCM_USERNAME"), PropertyUtil
					.getProperty("UCM_PASSWRD"));

			// Create an HdaBinderSerializer; this is not necessary, but it
			// allows us to serialize
			// the request and response data binders

			serializer = new HdaBinderSerializer("UTF-8", idcClient
					.getDataFactory());

			// Databinder for checkin request

			dataBinder = idcClient.createBinder();

			dataBinder.putLocal("IdcService", "CHECKIN_NEW");
			// ===========================================
			// Start: Added By Matiur Rahman on 02-07-2012
			// ===========================================
			String dDocTitle = (String) contentsMap.get("dDocTitle");
			if (null != dDocTitle && !"".equalsIgnoreCase(dDocTitle)) {
				dataBinder.putLocal("dDocTitle", dDocTitle);
			}/*
			 * else { dataBinder.putLocal("dDocTitle", "Untitled Document"); }
			 */
			String xTypeOfDocument = (String) contentsMap
					.get("xTypeOfDocument");
			if (null != xTypeOfDocument
					&& !"".equalsIgnoreCase(xTypeOfDocument)) {
				dataBinder.putLocal("xTypeOfDocument", xTypeOfDocument);
			}
			// ===========================================
			// End: Added By Matiur Rahman.
			// ===========================================
			dataBinder.putLocal("dDocType", "DOCUMENT");

			dataBinder.putLocal("dSecurityGroup", "Public");

			dataBinder.putLocal("xTitle", "Mr");
			dataBinder.putLocal("dDocAuthor", "weblogic");// Added By Matiur
			// Rahman on
			// 12-07-2012
			dataBinder.putLocal("xDocumentNo", contentsMap.get("DocumentNo"));// Added
			// By
			// Matiur
			// Rahman
			// on
			// 12-07-2012

			dataBinder.putLocal("xFirst_Name", contentsMap.get("FirstName"));
			dataBinder.putLocal("xLast_Name", contentsMap.get("LastName"));
			dataBinder.putLocal("xKNo", contentsMap.get("Kno"));
			dataBinder.putLocal("xAppRefNo", contentsMap.get("AppRefNo"));
			AppLog.info("::Uploded Document To UCM Against::KNO::"
					+ contentsMap.get("Kno") + "::ARN::"
					+ contentsMap.get("AppRefNo"));
			// dataBinder.putLocal("xZone", "South");
			dataBinder.putLocal("xZone", contentsMap.get("Zone"));
			dataBinder.putLocal("xSub_Area", contentsMap.get("SubArea"));

			dataBinder.putLocal("xMR_CODE", contentsMap.get("MRCode"));
			dataBinder.putLocal("xWater_Connection_Number", contentsMap
					.get("ConnNo"));

			dataBinder.addFile("primaryFile", new File(uplodingFile));

			dataBinder.putLocal("doFileCopy", "0");

			// Write the data binder for the request to stdout
			serializer.serializeBinder(System.out, dataBinder);
			// Send the request to Content Server
			response = idcClient.sendRequest(userContext, dataBinder);
			// Get the data binder for the response from Content Server
			responseData = response.getResponseAsBinder();
			// Write the response data binder to stdout
			serializer.serializeBinder(System.out, responseData);
			// System.out.println("---------------------" + responseData);

			// ////////////////////////

			FileOutputStream fos = new FileOutputStream(outputFile);
			serializer.serializeBinder(fos, responseData);
			fos.close();

		} catch (IdcClientException idcClientExp) {
			AppLog.error("IdcClientException:" + idcClientExp);
			// AppLog.error(null, "::IdcClientException::", idcClientExp);
			// idcClientExp.printStackTrace();
		} catch (IOException ioExp) {
			// AppLog.error(null, "::IOException::", ioExp);
			AppLog.error("IOException:" + ioExp);
			// ioExp.printStackTrace();
		}
		AppLog.end();
		return outputFile;
	}

	/**
	 * <p>
	 * This method is used for parsing UCM path.
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String ucmPathParse(String str) {
		AppLog.begin();
		String finString = null;
		try {
			String[] tmp1 = str.split("/");
			// System.out.println("Hii"+tmp1.length);
			// System.out.println("Now the last block---"+tmp1[tmp1.length-1]);

			String s = (tmp1[tmp1.length - 1]);
			StringBuffer sbr = new StringBuffer(s);
			sbr = sbr.delete(sbr.indexOf("~"), sbr.indexOf("."));
			finString = tmp1[0] + "//";
			// System.out.println("Now the String First:::" + finString);
			for (int k = 2; k <= tmp1.length - 2; k++) {
				finString = finString + tmp1[k] + "/";
			}
			finString = finString + sbr.toString();
			// System.out.println(finString + sbr.toString());
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return finString;

	}

	/**
	 * <p>
	 * This method is used for reading the parsed file.
	 * </p>
	 * 
	 * @param op
	 * @return
	 */
	public static String readFileParse(File op) {
		AppLog.begin();
		String msgPath = "";
		Properties prop = new Properties();
		try {
			AppLog.info("op ::" + op);
			prop.load(new FileInputStream(op));
			String mes = prop.getProperty("WebfilePath");
			// Added on 28-11-2012 by Matiur Rahman
			AppLog.info("::UCM WEB PATH ::" + mes);
			String UCM_ACCESS_PATH_URL = UCM_ACCESS_PATH
					+ mes.substring(mes.indexOf("groups/"));
			//System.out.println("UCM_ACCESS_PATH_URL::" + UCM_ACCESS_PATH_URL);
			msgPath = UCM_ACCESS_PATH_URL;
			AppLog.info("::UCM PATH ::" + msgPath);
		} catch (FileNotFoundException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		}
		AppLog.end();
		return msgPath;

	}

}
