/************************************************************************************************************
 * @(#) UcmClient.java 1.0 15 Dec 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;

import com.tcs.djb.rms.model.SessionDetails;
import com.tcs.djb.rms.model.UcmFile;

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
public class UcmClient {

	/**
	 * PropertyUtil .getProperty("UCM_ACCESS_URL");
	 */
	public static final String UCM_ACCESS_URL = PropertyUtil
			.getProperty("UCM_ACCESS_URL");
	/**
	 * 
	 */
	public static final String UCM_ADD_FILE_PRIMARY = "primaryFile";
	/**
	 * 
	 */
	public static final String UCM_DOC_AUTHOR = "dDocAuthor";
	/**
	 * 
	 */
	public static final String UCM_DOC_TITLE = "dDocTitle";
	/**
	 * 
	 */
	public static final String UCM_DOC_TYPE = "dDocType";
	/**
	 * 
	 */
	public static final String UCM_DOC_TYPE_DOCUMENT = "DOCUMENT";
	/**
	 * 
	 */
	public static final String UCM_DOCUMENT_NO = "xDocumentNo";
	/**
	 * 
	 */
	public static final String UCM_IDC_SERVICE = "IdcService";
	/**
	 * 
	 */
	public static final String UCM_IDC_SERVICE_CHECKIN_UNIVERSAL = "CHECKIN_UNIVERSAL";
	/**
	 * PropertyUtil .getProperty("UCM_PASSWRD");
	 */
	public static final String UCM_PASSWRD = PropertyUtil
			.getProperty("UCM_PASSWRD");
	/**
	 * 
	 */
	public static final String UCM_SECURITY_GROUP = "dSecurityGroup";
	/**
	 * 
	 */
	public static final String UCM_SECURITY_GROUP_PUBLIC = "Public";
	/**
	 * PropertyUtil .getProperty("UCM_SERVER_URL")
	 */
	public static final String UCM_SERVER_URL = PropertyUtil
			.getProperty("UCM_SERVER_URL");
	/**
	 * 
	 */
	public static final String UCM_TYPE_OF_DOCUMENT = "xTypeOfDocument";
	/**
	 * PropertyUtil .getProperty("UCM_USERNAME");
	 */
	public static final String UCM_USERNAME = PropertyUtil
			.getProperty("UCM_USERNAME");

	public static void main(String[] args) {
		try {

			Map<String, String> contentMap = new HashMap<String, String>();
			SessionDetails sessionDetails = new SessionDetails();
			contentMap.put(UCM_SECURITY_GROUP, UCM_SECURITY_GROUP_PUBLIC);
			contentMap.put(UCM_DOC_TYPE, UCM_DOC_TYPE_DOCUMENT);
			contentMap.put(UCM_DOC_TITLE, "Test Upload 1234567892");
			contentMap.put(UCM_TYPE_OF_DOCUMENT, "Bill Information");
			contentMap.put(UCM_DOCUMENT_NO, "12345");
			contentMap.put("xKNo", "1234567892");
			new UcmClient().checkInNewContent("/home/user/", "test.png",
					contentMap, 60 * 1000, sessionDetails);
			new UcmClient().checkInNewContent("/home/user/", "test.png",
					contentMap, 60 * 1000, UCM_SERVER_URL, UCM_USERNAME,
					UCM_PASSWRD, UCM_ACCESS_URL, sessionDetails);
		} catch (Exception e) {
			AppLog.error(e);

		}
	}

	/**
	 * 
	 */
	private final String FILE_SEPARATOR = null != PropertyUtil
			.getProperty("FILE_SEPARATOR") ? PropertyUtil
			.getProperty("FILE_SEPARATOR") : "//";
	IdcClient<?, ?, ?> idcClient = null;

	IdcClientManager idcClientManager = null;

	IdcContext idcContext = null;
	/**
	 * 
	 */
	private final String PATH_SEPARATOR = null != PropertyUtil
			.getProperty("PATH_SEPARATOR") ? PropertyUtil
			.getProperty("PATH_SEPARATOR") : "/";
	DataBinder requestDataBinder = null;
	/**
	 * 
	 */
	private final String UCM_ACCESS_URL_INDEX_KEY = null != PropertyUtil
			.getProperty("UCM_ACCESS_URL_INDEX_KEY") ? PropertyUtil
			.getProperty("UCM_ACCESS_URL_INDEX_KEY") : "groups/";

	/**
	 * <p>
	 * Method to check in content to UCM with default parameter. All UCM related
	 * parameter must be set in the parameter contentMap.
	 * </p>
	 * <p>
	 * <b>Sample code:</b><br>
	 * {<br>
	 * Map<String, String> contentMap = new HashMap<String, String>();<br>
	 * contentMap.put(UCM_SECURITY_GROUP, UCM_SECURITY_GROUP_PUBLIC);<br>
	 * contentMap.put(UCM_DOC_TYPE, UCM_DOC_TYPE_DOCUMENT);<br>
	 * contentMap.put(UCM_DOC_TITLE, "Test Upload 1234567892");<br>
	 * contentMap.put(UCM_TYPE_OF_DOCUMENT, "Bill Information");<br>
	 * contentMap.put(UCM_DOCUMENT_NO, "12345");<br>
	 * contentMap.put("xKNo", "1234567892");<br>
	 * new UcmClient().checkInNewContent("/home/user/", "test.png", contentMap,
	 * 60 * 1000, sessionDetails);<br>
	 * }<br>
	 * </p>
	 * 
	 * @param filePath
	 * @param fileName
	 * @param contentMap
	 * @param connTimeOut
	 * @param sessionDetails
	 * @return file URL to access from UCM
	 * @throws IdcClientException
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public UcmFile checkInNewContent(String filePath, String fileName,
			Map<String, String> contentMap, int connTimeOut,
			SessionDetails sessionDetails) throws IdcClientException,
			IOException, Exception {
		AppLog.begin(sessionDetails);
		ServiceResponse serviceResponse = null;
		InputStream fileStream = null;
		UcmFile ucmFile = null;
		try {
			idcClientManager = new IdcClientManager();
			idcClient = idcClientManager.createClient(UCM_SERVER_URL);
			idcClient.getConfig().setConnectionWaitTime(connTimeOut);
			idcContext = new IdcContext(UCM_USERNAME, UCM_PASSWRD);
			fileStream = new FileInputStream(filePath + fileName);
			long fileLength = new File(filePath + fileName).length();
			requestDataBinder = idcClient.createBinder();
			requestDataBinder.putLocal(UCM_IDC_SERVICE,
					UCM_IDC_SERVICE_CHECKIN_UNIVERSAL);
			requestDataBinder.putLocal(UCM_DOC_TYPE, UCM_DOC_TYPE_DOCUMENT);
			// Title of the Uploaded file
			// myRequestDataBinder.putLocal(UCM_DOC_TITLE, myFileTitle);
			// Name of Author
			requestDataBinder.putLocal(UCM_DOC_AUTHOR, UCM_USERNAME);
			// Security for the content (Group and Account)
			requestDataBinder.putLocal(UCM_SECURITY_GROUP,
					UCM_SECURITY_GROUP_PUBLIC);
			// requestDataBinder.putLocal("dDocAccount", "");
			// requestDataBinder.putLocal("doFileCopy", "0");
			// myRequestDataBinder.putLocal("dFormat", "text/html");
			// myRequestDataBinder.putLocal("xCollectionID", Long
			// .toString(getFolderIdFromPath("/Contribution Folders/")));
			for (Map.Entry<String, String> entry : contentMap.entrySet()) {
				requestDataBinder.putLocal(entry.getKey(), entry.getValue());
			}
			if (null != contentMap
					&& null == contentMap.get(UCM_SECURITY_GROUP)) {
				requestDataBinder.putLocal(UCM_SECURITY_GROUP,
						UCM_SECURITY_GROUP_PUBLIC);
			}
			if (null != contentMap && null == contentMap.get(UCM_DOC_TYPE)) {
				requestDataBinder.putLocal(UCM_DOC_TYPE, UCM_DOC_TYPE_DOCUMENT);
			}
			requestDataBinder.addFile(UCM_ADD_FILE_PRIMARY, new TransferFile(
					fileStream, fileName, fileLength));
			serviceResponse = idcClient.sendRequest(idcContext,
					requestDataBinder);
			if (null != serviceResponse) {
				// InputStream myInputStream =
				// myServiceResponse.getResponseStream();
				AppLog.debugInfo(sessionDetails, "Uploaded file details: \n"
						+ serviceResponse.getResponseAsString());
				ucmFile = new UcmFile();
				ucmFile.setContentMap(contentMap);
				ucmFile.setCreateDate(serviceResponse.getResponseAsBinder()
						.getLocal("dCreateDate"));
				ucmFile.setDocAuthor(serviceResponse.getResponseAsBinder()
						.getLocal("dDocAuthor"));
				ucmFile.setDocID(serviceResponse.getResponseAsBinder()
						.getLocal("dDocID"));
				ucmFile.setDocLastModifier(serviceResponse
						.getResponseAsBinder().getLocal("dDocLastModifier"));
				ucmFile.setDocName(serviceResponse.getResponseAsBinder()
						.getLocal("dDocName"));
				ucmFile.setDocTitle(serviceResponse.getResponseAsBinder()
						.getLocal("dDocTitle"));
				ucmFile.setDocType(serviceResponse.getResponseAsBinder()
						.getLocal("dDocType"));
				ucmFile.setFileSize(serviceResponse.getResponseAsBinder()
						.getLocal("dFileSize"));
				ucmFile.setOriginalName(serviceResponse.getResponseAsBinder()
						.getLocal("dOriginalName"));
				ucmFile.setPrimaryFile(serviceResponse.getResponseAsBinder()
						.getLocal("primaryFile"));
				ucmFile.setRevisionID(serviceResponse.getResponseAsBinder()
						.getLocal("dRevisionID"));
				ucmFile.setStatusMessage(serviceResponse.getResponseAsBinder()
						.getLocal("StatusMessage"));
				ucmFile.setVaultfilePath(serviceResponse.getResponseAsBinder()
						.getLocal("VaultfilePath"));
				ucmFile.setWebExtension(serviceResponse.getResponseAsBinder()
						.getLocal("dWebExtension"));
				ucmFile.setWebfilePath(serviceResponse.getResponseAsBinder()
						.getLocal("WebfilePath"));
				ucmFile.setWebUrl(getUcmAccessUrl(ucmFile.getWebfilePath(),
						UCM_ACCESS_URL, sessionDetails));
				AppLog.debugInfo(sessionDetails, "" + ucmFile);
			}
		} finally {
			if (serviceResponse != null) {
				serviceResponse.close();
			}
			if (fileStream != null) {
				try {
					fileStream.close();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
		AppLog.end(sessionDetails);
		return ucmFile;
	}

	/**
	 * <p>
	 * Method to check in content to UCM. All UCM related parameter must be set
	 * in the parameter contentMap.
	 * </p>
	 * <p>
	 * <b>Sample code:</b><br>
	 * {<br>
	 * Map<String, String> contentMap = new HashMap<String, String>();<br>
	 * contentMap.put(UCM_SECURITY_GROUP, UCM_SECURITY_GROUP_PUBLIC);<br>
	 * contentMap.put(UCM_DOC_TYPE, UCM_DOC_TYPE_DOCUMENT);<br>
	 * contentMap.put(UCM_DOC_TITLE, "Test Upload 1234567892");<br>
	 * contentMap.put(UCM_TYPE_OF_DOCUMENT, "Bill Information");<br>
	 * contentMap.put(UCM_DOCUMENT_NO, "12345");<br>
	 * contentMap.put("xKNo", "1234567892");<br>
	 * new UcmClient().checkInNewContent("/home/user/", "test.png", contentMap,
	 * 60 * 1000, UCM_SERVER_URL, UCM_USERNAME, UCM_PASSWRD, UCM_ACCESS_URL,
	 * sessionDetails);<br>
	 * }<br>
	 * </p>
	 * 
	 * @param filePath
	 * @param fileName
	 * @param contentMap
	 * @param connTimeOut
	 * @param serverUrl
	 * @param userName
	 * @param password
	 * @param accessUrl
	 * @param sessionDetails
	 * @return
	 * @throws IdcClientException
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public UcmFile checkInNewContent(String filePath, String fileName,
			Map<String, String> contentMap, int connTimeOut, String serverUrl,
			String userName, String password, String accessUrl,
			SessionDetails sessionDetails) throws IdcClientException,
			IOException, Exception {
		AppLog.begin(sessionDetails);
		ServiceResponse serviceResponse = null;
		InputStream fileStream = null;
		UcmFile ucmFile = null;
		try {
			idcClientManager = new IdcClientManager();
			idcClient = idcClientManager.createClient(serverUrl);
			idcClient.getConfig().setConnectionWaitTime(connTimeOut);
			idcContext = new IdcContext(userName, password);
			fileStream = new FileInputStream(filePath + fileName);
			long fileLength = new File(filePath + fileName).length();
			requestDataBinder = idcClient.createBinder();
			requestDataBinder.putLocal(UCM_IDC_SERVICE,
					UCM_IDC_SERVICE_CHECKIN_UNIVERSAL);
			requestDataBinder.putLocal(UCM_DOC_AUTHOR, userName);
			for (Map.Entry<String, String> entry : contentMap.entrySet()) {
				requestDataBinder.putLocal(entry.getKey(), entry.getValue());
			}
			requestDataBinder.addFile(UCM_ADD_FILE_PRIMARY, new TransferFile(
					fileStream, fileName, fileLength));
			serviceResponse = idcClient.sendRequest(idcContext,
					requestDataBinder);
			if (null != serviceResponse) {
				AppLog.debugInfo(sessionDetails, "Uploaded file details: \n"
						+ serviceResponse.getResponseAsString());
				ucmFile = new UcmFile();
				ucmFile.setContentMap(contentMap);
				ucmFile.setCreateDate(serviceResponse.getResponseAsBinder()
						.getLocal("dCreateDate"));
				ucmFile.setDocAuthor(serviceResponse.getResponseAsBinder()
						.getLocal("dDocAuthor"));
				ucmFile.setDocID(serviceResponse.getResponseAsBinder()
						.getLocal("dDocID"));
				ucmFile.setDocLastModifier(serviceResponse
						.getResponseAsBinder().getLocal("dDocLastModifier"));
				ucmFile.setDocName(serviceResponse.getResponseAsBinder()
						.getLocal("dDocName"));
				ucmFile.setDocTitle(serviceResponse.getResponseAsBinder()
						.getLocal("dDocTitle"));
				ucmFile.setDocType(serviceResponse.getResponseAsBinder()
						.getLocal("dDocType"));
				ucmFile.setFileSize(serviceResponse.getResponseAsBinder()
						.getLocal("dFileSize"));
				ucmFile.setOriginalName(serviceResponse.getResponseAsBinder()
						.getLocal("dOriginalName"));
				ucmFile.setPrimaryFile(serviceResponse.getResponseAsBinder()
						.getLocal("primaryFile"));
				ucmFile.setRevisionID(serviceResponse.getResponseAsBinder()
						.getLocal("dRevisionID"));
				ucmFile.setStatusMessage(serviceResponse.getResponseAsBinder()
						.getLocal("StatusMessage"));
				ucmFile.setVaultfilePath(serviceResponse.getResponseAsBinder()
						.getLocal("VaultfilePath"));
				ucmFile.setWebExtension(serviceResponse.getResponseAsBinder()
						.getLocal("dWebExtension"));
				ucmFile.setWebfilePath(serviceResponse.getResponseAsBinder()
						.getLocal("WebfilePath"));
				ucmFile.setWebUrl(getUcmAccessUrl(ucmFile.getWebfilePath(),
						UCM_ACCESS_URL, sessionDetails));
				AppLog.debugInfo(sessionDetails, "" + ucmFile);
			}
		} finally {
			if (serviceResponse != null) {
				serviceResponse.close();
			}
			if (fileStream != null) {
				try {
					fileStream.close();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
		AppLog.end(sessionDetails);
		return ucmFile;
	}

	/**
	 * <p>
	 * Get UCM Access URL for the Uploaded file.
	 * </p>
	 * 
	 * @param webfilePath
	 * @param ucmAccessUrl
	 * @param sessionDetails
	 * @return
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-12-2015
	 */
	public String getUcmAccessUrl(String webfilePath, String ucmAccessUrl,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		String fileAccessUrl = null;
		String ucmAccessPath = ucmAccessUrl
				+ webfilePath.substring(webfilePath
						.indexOf(UCM_ACCESS_URL_INDEX_KEY));
		String[] tmp = ucmAccessPath.split(PATH_SEPARATOR);
		String s = (tmp[tmp.length - 1]);
		StringBuffer sbr = new StringBuffer(s);
		sbr = sbr.delete(sbr.indexOf("~"), sbr.indexOf("."));
		fileAccessUrl = tmp[0] + FILE_SEPARATOR;
		for (int k = 2; k <= tmp.length - 2; k++) {
			fileAccessUrl = fileAccessUrl + tmp[k] + PATH_SEPARATOR;
		}
		fileAccessUrl = fileAccessUrl + sbr.toString();
		// AppLog.debugInfo(sessionDetails, "Access URL::" + fileAccessUrl);
		AppLog.end(sessionDetails);
		return fileAccessUrl;

	}
}
