/************************************************************************************************************
 * @(#) MRDFileUploadAction.java   02 Jun 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.MRDFileUploadDAO;
import com.tcs.djb.model.AMRHeaderDetails;
import com.tcs.djb.model.AMRRecordDetail;
import com.tcs.djb.model.ExcelToDBTableResponse;
import com.tcs.djb.thread.MRDFileDataProcessThread;
import com.tcs.djb.util.AMRExcelFileValidator;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.AuthCookieGeneratorUtil;
import com.tcs.djb.util.ExcelToDBTable;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UcmDocUploadUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
  * Action class for MRD File Upload through DataEntryApp during AMR Billing Process
  * </p>
 * @author Atanu Mondal (Tata Consultancy Services)
 * @since 02-06-2016 as per open project 1202,jTrac : DJB-4464
 * @history : Atanu on 05-07-2016 modified the logic to read the cell upto specified column count as per open project 1202,jTrac : DJB-4464.
 */
@SuppressWarnings("serial")
public class MRDFileUploadAction extends ActionSupport implements
		ServletResponseAware {

	private static final String SCREEN_ID = "48";
	private final String DAO_CLASS_NAME = "com.tcs.djb.facade.AMRFileUploadFacade";
	private final String DAO_METHOD_NAME = "saveAMRExcelData";
	private File dataSheetCopied;
	private File dataSheetUploaded;
	private String dataSheetUploadedContentType;
	private String dataSheetUploadedFileName;
	private String dataSheetUploadedFilePath;
	private String fileName = null;
	private String hidAction;
	private File output;
	private HttpServletResponse response;
	private HttpServletRequest servletRequest;
	private String sourcefileName = null;
	private String ucmFileName = null;
	private String ucmPath = "";
	private String ucmPathDsht = "";

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		AppLog.begin();
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = null;
			Map<String, String> mrkyFromSession = (Map<String, String>) session
					.get("taggedMrkey");
			String validMrkey = "";
			AppLog.info("EXECUTE hidAction :: " + hidAction);
			if (null != session) {
				userId = (String) session.get("userId");
				AppLog.info("LoggedIn UserId ::" + userId);
				session.put("CURR_TAB", "MRD");
				if (null == userId || "".equals(userId)) {
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
				session.remove("SERVER_MESSAGE");
				if ("processRequest".equalsIgnoreCase(hidAction)) {
					AppLog
							.info("inside processRequest hid action ucmUpload fun call "
									+ dataSheetUploaded);
					AppLog.info("file name " + dataSheetUploadedFileName);
					long actualFileSize = 0l;
					long validFileSize = 0l;
					try {
						actualFileSize = dataSheetUploaded.length();
						validFileSize = Long
								.parseLong(DJBConstants.AMR_FILE_SIZE);
					} catch (Exception e) {
						AppLog.error(e);
					}
					if (null != dataSheetUploaded) {
						if (actualFileSize <= validFileSize) {

							String fileDir = PropertyUtil
									.getProperty("UCMdocumentUpload");
							fileName = fileDir + dataSheetUploadedFileName;
							sourcefileName = dataSheetUploadedFileName;
							dataSheetCopied = new File(fileName);

							int dotIndex = dataSheetUploadedFileName
									.lastIndexOf('.');
							String dataSheetUploadedFileNameUcm = dataSheetUploadedFileName
									.substring(0, dotIndex);
							ucmUpload(dataSheetUploadedFileName,
									dataSheetUploaded,
									dataSheetUploadedFileName,
									dataSheetUploadedFilePath, "",
									dataSheetUploadedContentType,
									dataSheetUploadedFileNameUcm,
									DJBConstants.DOC_TYPE_RWH);
							ucmPathDsht = ucmPath;
							AppLog.info("ucmPathDataSheet::" + ucmPathDsht);
							if (null != ucmPathDsht
									&& !("".equalsIgnoreCase(fileName))) {
								FileUtils.copyFile(dataSheetUploaded,
										dataSheetCopied);
								if (!AMRExcelFileValidator.isHeaderValid(
										new File(dataSheetCopied.getPath()),
										DJBConstants.AMR_HEADER_ROW_NUMBER,
										DJBConstants.AMR_HEADER_COLUMN_COUNT)) {
									addActionError(DJBConstants.AMR_HEADER_VALIDATION_FAILURE_MSG);
									return ERROR;
								} else {
									MRDFileUploadDAO mRDFileUploadDAO = new MRDFileUploadDAO();
									AMRHeaderDetails aMRHeaderDetails = AMRExcelFileValidator
											.getHeaderData(
													new File(dataSheetCopied
															.getPath()),
													DJBConstants.AMR_HEADER_ROW_NUMBER);
									aMRHeaderDetails
											.setUcmFilePath(ucmPathDsht);
									aMRHeaderDetails.setCreatedBy(userId);
									aMRHeaderDetails.setLastUpdatedBy(userId);
									if (null != aMRHeaderDetails
											&& null != aMRHeaderDetails
													.getZroDesc()) {
										aMRHeaderDetails
												.setZroCd(mRDFileUploadDAO
														.getZROCode(aMRHeaderDetails
																.getZroDesc()));
									}
									if (null != aMRHeaderDetails
											&& null != aMRHeaderDetails
													.getAreaDesc()) {
										if (null == aMRHeaderDetails
												.getAreaCd()
												|| ""
														.equalsIgnoreCase(aMRHeaderDetails
																.getAreaCd()
																.trim())) {
											aMRHeaderDetails
													.setAreaCd(mRDFileUploadDAO
															.getAreaCode(aMRHeaderDetails
																	.getAreaDesc()));
										}
									}
									int nextHeaderId = mRDFileUploadDAO
											.getNextHeaderId();
									aMRHeaderDetails.setHeaderID(nextHeaderId);

									if (null != aMRHeaderDetails.getZone()
											&& null != aMRHeaderDetails.getMr()
											&& null != aMRHeaderDetails
													.getAreaCd()) {
										validMrkey = MRDFileUploadDAO
												.getMrdCode(aMRHeaderDetails
														.getZone(),
														aMRHeaderDetails
																.getMr(),
														aMRHeaderDetails
																.getAreaCd());
									}
									if (null != validMrkey
											&& null != mrkyFromSession
											&& mrkyFromSession
													.containsValue(validMrkey)) {
										int headerDataCount = mRDFileUploadDAO
												.insertHeaderData(aMRHeaderDetails);
										if (headerDataCount > 0) {
											ExcelToDBTableResponse excelToDBTableResponse = ExcelToDBTable
													.parse(
															dataSheetCopied,
															Integer.parseInt(DJBConstants.ROWS_EXEMPT_AMR_EXCEL),
															mRDFileUploadDAO,
															DAO_CLASS_NAME,
															DAO_METHOD_NAME,
															aMRHeaderDetails.getHeaderID(),
															Integer.parseInt(DJBConstants.AMR_HEADER_COLUMN_COUNT));
											/*
											 * AppLog
											 * .debug(excelToDBTableResponse);
											 */
											if (null != excelToDBTableResponse) {

												if (excelToDBTableResponse
														.getNoOfRecord() > 0
														&& aMRHeaderDetails
																.getHeaderID() > 0) {
													runServiceCallThread(aMRHeaderDetails
															.getHeaderID());
												}
												if (null == excelToDBTableResponse
														.getMsg()) {
													session
															.put(
																	"SERVER_MESSAGE",
																	"<font color='#33CC33'><b>Data Upload Process initiated Successfully.</b></font>");

												} else {
													List<String> errList = new ArrayList<String>();
													if (null != excelToDBTableResponse
															.getMsg()
															&& !""
																	.equalsIgnoreCase(excelToDBTableResponse
																			.getMsg()
																			.trim())
															&& excelToDBTableResponse
																	.getMsg()
																	.contains(
																			"java.lang.RuntimeException:")) {
														errList = Arrays
																.asList(excelToDBTableResponse
																		.getMsg()
																		.split(
																				"\\s*java.lang.RuntimeException:\\s*"));
													}
													addActionError(DJBConstants.AMR_PARTIAL_DATA_UPLOAD_MSG);
													if (null != errList
															&& errList.size() > 0) {
														for (String str : errList) {
															addActionError(str);
														}
													}
												}
												AppLog.end();
												return result;
											}

										} else {
											session
													.put(
															"SERVER_MESSAGE",
															"<font color='#red' size='2'><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
										}
									} else {
										throw new RuntimeException(
												DJBConstants.AMR_MRKEY_MISMATCH_MSG);
									}

								}
							} else {
								throw new RuntimeException(
										DJBConstants.AMR_FILE_UPLOAD_FAILURE_MSG);
							}
						} else {
							throw new RuntimeException(
									DJBConstants.AMR_FILE_SIZE_MSG);
						}
					}
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
			addActionError("ERROR:Sorry, There was problem while Processing Last Operation");
			result = ERROR;
		} finally {
			dataSheetUploaded = null;
			dataSheetUploadedContentType = null;
			dataSheetUploadedFileName = null;
			dataSheetUploadedFilePath = null;
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the dataSheetCopied
	 */
	public File getDataSheetCopied() {
		return dataSheetCopied;
	}

	/**
	 * @return the dataSheetUploaded
	 */
	public File getDataSheetUploaded() {
		return dataSheetUploaded;
	}

	/**
	 * @return the dataSheetUploadedContentType
	 */
	public String getDataSheetUploadedContentType() {
		return dataSheetUploadedContentType;
	}

	/**
	 * @return the dataSheetUploadedFileName
	 */
	public String getDataSheetUploadedFileName() {
		return dataSheetUploadedFileName;
	}

	/**
	 * @return the dataSheetUploadedFilePath
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
	/**
	 * @return
	 */
	public File getOutput() {
		return output;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
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
	 * @return the ucmPath
	 */
	public String getUcmPath() {
		return ucmPath;
	}

	/**
	 * @return the ucmPathDsht
	 */
	public String getUcmPathDsht() {
		return ucmPathDsht;
	}

	/**
	 * <p>
	 * This method is used to process the records in threads for upload
	 * </p>
	 * 
	 * @param headerID
	 * @throws Exception
	 */
	private void runServiceCallThread(int headerID) throws Exception {
		AppLog.begin();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");
		List<AMRRecordDetail> aMRRecordDetailList = null;

		MRDFileUploadDAO mrdFileUploadDAO = new MRDFileUploadDAO();
		aMRRecordDetailList = MRDFileUploadDAO
				.getAMRRecordsOfSpecificHeaderID(headerID);
		if (null != aMRRecordDetailList && aMRRecordDetailList.size() > 0) {
			String hhdID = mrdFileUploadDAO.getHHDIdOfUser(userId);
			Map<String, String> userDetailsMap = new HashMap<String, String>();
			userDetailsMap = mrdFileUploadDAO.getUserDetails(hhdID);
			String version = mrdFileUploadDAO.getCurrentVersion();
			String authCookie = AuthCookieGeneratorUtil
					.generateAuthCookie(
							userId,
							hhdID,
							userDetailsMap,
							version,
							DJBConstants.AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE);

			if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {

				int maxThreadCount = DJBConstants.AMR_MAX_THREAD_COUNT;
				int noOfrecInChunk = aMRRecordDetailList.size()
						/ maxThreadCount;
				// for (int i = 0; i < maxThreadCount; i++) {
				// if (aMRRecordDetailList.size() > noOfrecInChunk) {
				// tempList = aMRRecordDetailList.subList(0,
				// noOfrecInChunk * (i + 1));
				// new MRDFileDataProcessThread(tempList, userId,
				// authCookie, hhdID);
				// aMRRecordDetailList=aMRRecordDetailList.r
				// }
				// }
				for (int start = 0; start < aMRRecordDetailList.size(); start += noOfrecInChunk) {
					int end = Math.min(start + noOfrecInChunk,
							aMRRecordDetailList.size());
					List<AMRRecordDetail> sublist = aMRRecordDetailList
							.subList(start, end);
					new MRDFileDataProcessThread(sublist, userId, authCookie,
							hhdID);
				}
			} else {
				AppLog.info("****" + DJBConstants.EMPTY_AUTHKEY
						+ " for User>>>" + userId);
				int updatedRow = MRDFileUploadDAO.updateAMRStagingTable(
						headerID, DJBConstants.EMPTY_AUTHKEY);
				AppLog.info("Total " + updatedRow
						+ " records updated in CM_AMR_RECORD_DETAILS.");
				throw new Exception(DJBConstants.EMPTY_AUTHKEY);
			}

		}
		AppLog.end();
	}

	/**
	 * @param dataSheetCopied
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
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param hidAction
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
	 * @param servletRequest
	 */
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
	 * (javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param sourcefileName
	 */
	public void setSourcefileName(String sourcefileName) {
		this.sourcefileName = sourcefileName;
	}

	/**
	 * @param ucmFileName
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
	 * <p>
	 * This method is used to Upload the file in UCM
	 * </p>
	 * 
	 * @param fileName
	 * @param docProof
	 * @param docProofFileName
	 * @param docProofFilePath
	 * @param docNo
	 * @param docProofContentType
	 * @param docTitle
	 * @param typeOfDocument
	 * @return
	 * @throws Exception
	 */
	public String ucmUpload(String fileName, File docProof,
			String docProofFileName, String docProofFilePath, String docNo,
			String docProofContentType, String docTitle, String typeOfDocument)
			throws Exception {
		AppLog.begin();
		String successFilePath = null;
		try {

			if (null != DJBConstants.UCM_UPLOAD
					&& !"".equals(DJBConstants.UCM_UPLOAD.trim())) {
				String TMP_DIR_PATH = PropertyUtil
						.getProperty("UCMdocumentUpload");
				AppLog
						.info("documentOfProof::" + docProof
								+ ">>documentOfProofFileName>>"
								+ docProofFileName
								+ ">>documentOfProofContentType>>"
								+ docProofContentType + "TMP_DIR_PATH::"
								+ TMP_DIR_PATH);

				AppLog.info("docProofFilePath ::" + docProofFilePath);
				AppLog.info("fileName ::" + getSourcefileName());
				ucmFileName = fileName;
				AppLog.info("ucm file name ::" + ucmFileName);
				HashMap<String, String> contentsMap = null;
				AppLog.info("before uploading ::");
				UcmDocUploadUtil ucmDocUploadUtil = new UcmDocUploadUtil();
				contentsMap = new HashMap<String, String>();
				String msgPath = "NA";
				contentsMap.put("AMR", "");
				/******** File Parsing... ************/
				contentsMap.put("dDocTitle", docTitle);

				contentsMap.put("xTypeOfDocument", typeOfDocument);
				contentsMap.put("DocumentNo", docNo);
				AppLog.info("value of docProofFilePath" + docProofFilePath
						+ "and docProofFileName ::" + docProofFileName);
				try {
					output = ucmDocUploadUtil.checkInContent(docProof
							.toString(), contentsMap, ucmFileName);
					msgPath = UcmDocUploadUtil.readFileParse(output);
					ucmPath = UcmDocUploadUtil.ucmPathParse(msgPath);

					AppLog.info("msgPath>>>" + msgPath + ">>>ucmPath>>>"
							+ ucmPath);
				} catch (Exception e) {
					AppLog.error(e);
				}
			}
			if (null != docProofFilePath) {
				successFilePath = docProofFilePath;
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return successFilePath;
	}

}
