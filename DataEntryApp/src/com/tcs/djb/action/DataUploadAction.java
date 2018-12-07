/************************************************************************************************************
 * @(#) DataUploadAction.java   20-Apr-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;

import com.tcs.djb.thread.FileProcessingThread;
import com.tcs.djb.util.AppLog;

import com.tcs.djb.util.ExcelFileValidator;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UcmDocUploadUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Data upload Screen as per JTrac DJB-4465 and
 * OpenProject-918.
 * </p>
 * 
 * @author Lovely(986018)
 * @since 20-04-2016
 * 
 * @history Arnab Nandy (1227971) on 31-05-2016 added {@link #loadDefault()} for
 *          loading default values into data upload screen and
 *          {@link #execute()} to change thread calling with an parameter of ZRO
 *          code, according to JTrac DJB-4465 and OpenProject-918.
 */
@SuppressWarnings("serial")
public class DataUploadAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "47";
	private File dataSheetCopied;

	private File dataSheetUploaded;
	private String dataSheetUploadedContentType;
	private String dataSheetUploadedFileName;

	private String dataSheetUploadedFilePath;
	private String fileName = null;
	private String hidAction;

	private File output;

	private HttpServletResponse response;
	private String selectedZROCode = "";
	private HttpServletRequest servletRequest;
	private String sourcefileName = null;

	private String ucmFileName = null;

	private String ucmPath = "";

	private String ucmPathDsht = "";

	/**
	 * @history Arnab Nandy (1227971) edited {@link #execute()} on 31-05-2016
	 *          according to JTrac DJB-4465 and OpenProject-918.
	 */
	public String execute() {
		AppLog.begin();
		try {
			String userName = null;
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			if (null != session) {
				userName = (String) session.get("userId");
				if (null == userName || "".equals(userName)) {
					addActionError(getText("error.login.expired"));
					AppLog.end();
					return "login";
				}
				if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
					addActionError(getText("error.access.denied"));
					AppLog.end();
					return "login";
				}
				if (null == hidAction) {
					session.remove("SERVER_MESSAGE");
				}
			}
			/*
			 * Start : Arnab Nandy (1227971) on 31-05-2016 added Load Default
			 * Values
			 */
			loadDefault();
			/*
			 * End : Arnab Nandy (1227971) on 31-05-2016 added Load Default
			 * Values
			 */
			session.remove("SERVER_MSG");
			AppLog.info("value of hidaction :: " + hidAction);
			AppLog.info("Content type = " + dataSheetUploadedContentType);
			if ("processRequest".equalsIgnoreCase(hidAction)) {
				long actualFileSize = 0l;
				long validFileSize = 0l;
				try {
					actualFileSize = dataSheetUploaded.length();
					validFileSize = Long
							.parseLong(DJBConstants.FILE_SIZE_LIMIT);
				} catch (Exception e) {
					AppLog.error(e);
				}
				if (actualFileSize <= validFileSize) {
					AppLog.info("file name " + dataSheetUploadedFileName
							+ "FILE SIZE " + dataSheetUploaded.length());
					if (null != dataSheetUploaded) {
						String fileDir = PropertyUtil
								.getProperty("UCMdocumentUpload");
						fileName = fileDir + dataSheetUploadedFileName;
						sourcefileName = dataSheetUploadedFileName;
						dataSheetCopied = new File(fileName);
						int dotIndex = dataSheetUploadedFileName
								.lastIndexOf('.');
						String dataSheetUploadedFileNameUcm = dataSheetUploadedFileName
								.substring(0, dotIndex);
						ucmPathDsht = ucmUpload(dataSheetUploadedFileName,
								dataSheetUploaded, dataSheetUploadedFileName,
								dataSheetUploadedFilePath, "",
								dataSheetUploadedContentType,
								dataSheetUploadedFileNameUcm,
								DJBConstants.DOC_TYPE_RWH);
						AppLog
								.info(">>>>>>>>UCM data sheet uploaded path >>>>>>>>::"
										+ ucmPathDsht);
						if (null != ucmPathDsht && ucmPathDsht.length() > 0) {
							FileUtils.copyFile(dataSheetUploaded,
									dataSheetCopied);
							AppLog
									.info(">>>>>>>>>data sheet copied to server >>>>>>"
											+ FileUtils.contentEquals(
													dataSheetUploaded,
													dataSheetCopied));
							if (FileUtils.contentEquals(dataSheetUploaded,
									dataSheetCopied)) {

								if (!ExcelFileValidator.isHeaderValid(new File(
										dataSheetCopied.getPath()),
										DJBConstants.HEADER_ROW_NUMBER)) {
									session.put("SERVER_MESSAGE", "");
									addActionError(DJBConstants.FILE_FORMAT_MSG);
									AppLog.end();
									return ERROR;
								} else {
									session.put("SERVER_MESSAGE", "");
									addActionMessage(DJBConstants.UPLOAD_SUCCESS_MSG);
									/*
									 * Start : Arnab Nandy (1227971) on
									 * 31-05-2016 edited thread parameter.
									 */
									new FileProcessingThread(dataSheetCopied
											.getPath(), sourcefileName,
											selectedZROCode);
									/*
									 * End : Arnab Nandy (1227971) on 31-05-2016
									 * edited thread parameter.
									 */
									AppLog.end();
									return SUCCESS;
								}
							} else {
								session.put("SERVER_MESSAGE", "");
								addActionError(DJBConstants.UPLOAD_FAILURE_MSG);
								AppLog.end();
								return ERROR;
							}
						} else {
							session.put("SERVER_MESSAGE", "");
							addActionError(DJBConstants.UPLOAD_FAILURE_MSG);
							AppLog.end();
							return ERROR;
						}
					}
				} else {
					session.put("SERVER_MESSAGE", "");
					addActionError(DJBConstants.FILE_SIZE_MSG);
					AppLog.end();
					return ERROR;
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			dataSheetUploaded = null;
			dataSheetUploadedContentType = null;
			dataSheetUploadedFileName = null;
			dataSheetUploadedFilePath = null;
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @return the dataSheetCopied
	 */
	public File getDataSheetCopied() {
		return dataSheetCopied;
	}

	/**
	 * @return dataSheetUploaded
	 */
	public File getDataSheetUploaded() {
		return dataSheetUploaded;
	}

	/**
	 * @return dataSheetUploadedContentType
	 */
	public String getDataSheetUploadedContentType() {
		return dataSheetUploadedContentType;
	}

	/**
	 * @return dataSheetUploadedFileName
	 */
	public String getDataSheetUploadedFileName() {
		return dataSheetUploadedFileName;
	}

	/**
	 * @return dataSheetUploadedFilePath
	 */
	public String getDataSheetUploadedFilePath() {
		return dataSheetUploadedFilePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the output
	 */
	public File getOutput() {
		return output;
	}

	/**
	 * @return response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * @return the servletRequest
	 */
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	/**
	 * @return the sourcefileName
	 */
	public String getSourcefileName() {
		return sourcefileName;
	}

	/**
	 * @return the ucmFileName
	 */
	public String getUcmFileName() {
		return ucmFileName;
	}

	/**
	 * @return ucmPath
	 */
	public String getUcmPath() {
		return ucmPath;
	}

	/**
	 * @return ucmPathDsht
	 */
	public String getUcmPathDsht() {
		return ucmPathDsht;
	}

	/**
	 * <p>
	 * Populate default values required for Data Upload Screen.
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 31-05-2016
	 */

	public void loadDefault() {
		AppLog.begin();
		String searchcontext = DJBConstants.DATA_MIGRATION_ZRO_LIST;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("ZROListMap", GetterDAO.getZROList(searchcontext));
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * @param dataSheetCopied
	 *            the dataSheetCopied to set
	 */
	public void setDataSheetCopied(File dataSheetCopied) {
		this.dataSheetCopied = dataSheetCopied;
	}

	/**
	 * @param dataSheetUploaded
	 */
	public void setDataSheetUploaded(File dataSheetUploaded) {
		this.dataSheetUploaded = dataSheetUploaded;
	}

	/**
	 * @param dataSheetUploadedContentType
	 */
	public void setDataSheetUploadedContentType(
			String dataSheetUploadedContentType) {
		this.dataSheetUploadedContentType = dataSheetUploadedContentType;
	}

	/**
	 * @param dataSheetUploadedFileName
	 */
	public void setDataSheetUploadedFileName(String dataSheetUploadedFileName) {
		this.dataSheetUploadedFileName = dataSheetUploadedFileName;
	}

	/**
	 * @param dataSheetUploadedFilePath
	 */
	public void setDataSheetUploadedFilePath(String dataSheetUploadedFilePath) {
		this.dataSheetUploadedFilePath = dataSheetUploadedFilePath;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param output
	 *            the output to set
	 */
	public void setOutput(File output) {
		this.output = output;
	}

	/**
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/**
	 * @param servletRequest
	 *            the servletRequest to set
	 */
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param sourcefileName
	 *            the sourcefileName to set
	 */
	public void setSourcefileName(String sourcefileName) {
		this.sourcefileName = sourcefileName;
	}

	/**
	 * @param ucmFileName
	 *            the ucmFileName to set
	 */
	public void setUcmFileName(String ucmFileName) {
		this.ucmFileName = ucmFileName;
	}

	/**
	 * @param ucmPath
	 */
	public void setUcmPath(String ucmPath) {
		this.ucmPath = ucmPath;
	}

	/**
	 * @param ucmPathDsht
	 */
	public void setUcmPathDsht(String ucmPathDsht) {
		this.ucmPathDsht = ucmPathDsht;
	}

	/**
	 * This function is used to upload document in UCM. Jtrac DJB 4465
	 */
	public String ucmUpload(String fileName, File docProof,
			String docProofFileName, String docProofFilePath, String docNo,
			String docProofContentType, String docTitle, String typeOfDocument)
			throws Exception {
		AppLog.begin();
		String successUCMFilePath = null;
		try {
			if (null != DJBConstants.UCM_UPLOAD
					&& !"".equals(DJBConstants.UCM_UPLOAD.trim())) {
				String TMP_DIR_PATH = PropertyUtil
						.getProperty("UCMdocumentUpload");
				AppLog.info("documentOfProof::" + docProof
						+ ">>documentOfProofFileName>>" + docProofFileName
						+ ">>documentOfProofContentType>>"
						+ docProofContentType + "TMP_DIR_PATH::" + TMP_DIR_PATH
						+ "docProofFilePath ::" + docProofFilePath
						+ "fileName ::" + getSourcefileName());
				ucmFileName = fileName;
				HashMap<String, String> contentsMap = null;
				UcmDocUploadUtil ucmDocUploadUtil = new UcmDocUploadUtil();
				contentsMap = new HashMap<String, String>();
				String msgPath = "NA";
				contentsMap.put("DW", "");
				/******** File Parsing... ************/
				contentsMap.put("dDocTitle", docTitle);
				contentsMap.put("xTypeOfDocument", typeOfDocument);
				contentsMap.put("DocumentNo", docNo);
				try {
					output = ucmDocUploadUtil.checkInContent(docProof
							.toString(), contentsMap, ucmFileName);
					msgPath = UcmDocUploadUtil.readFileParse(output);
					ucmPath = UcmDocUploadUtil.ucmPathParse(msgPath);

					AppLog.info(">>>>>ucm file name >>>>>>>>::" + ucmFileName
							+ ">>>>>>>>msgPath>>>" + msgPath + ">>>ucmPath>>>"
							+ ucmPath);
					successUCMFilePath = ucmPath;
				} catch (Exception e) {
					AppLog.error(e);
				}
			}
		}

		catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return successUCMFilePath;
	}
}