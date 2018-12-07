/************************************************************************************************************
 * @(#) FileDownloaderAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.MRDScheduleDownloadDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * Action class for MRD File Download Screen.
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @history ON 02-JUNE-2016 Added by Madhuri to get tagged mrkey of AMR user
 *          from session as per JTrac DJB-4464 & Open Project ids -1203
 * @history ON 10-AUG-2016 Added by Madhuri to update details of downloaded file
 *          in STG table as per JTrac DJB-4538 & Open Project ids -1429
 */
public class FileDownloaderAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InputStream fileInputStream;
	private String fileName;
	private String fileType;
	
   /*Added by Madhuri on 6th June 2016*/
	private String billWindow;
	private String searchCriteria;

	private HttpServletResponse response;
	private final String logFileLocation = "/home/user/djb_portal_logs/";
	private String userId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		String forward = "error";
		try {
			/*
			 * Added by Madhuri on 6th June 2016 as per Jtrac -4464
			 */
			AppLog.info("<<<<fileName>>>>>>>>>" + fileName);
			AppLog.info("<<<<userId>>>>>>>>>" + userId);
			AppLog.info("<<<<searchCriteria>>>>>>>>>" + searchCriteria);
			AppLog.info("<<<<billWindow>>>>>>>>>" + billWindow);
			if (null != billWindow && null != searchCriteria) {

				String searchCriteriaVal = searchCriteria;
				String billWindowVal = billWindow;

				String[] searchCriteriaValNew = null;
				/* Added by Madhuri on 10th Aug 2016 as per Jtrac -4538 */
				if (searchCriteriaVal
						.contains(DJBConstants.SEPERATOR_MRD_SEARCH_CRITERIA)) {
					searchCriteriaValNew = searchCriteriaVal
							.split(DJBConstants.SEPERATOR_MRD_SEARCH_CRITERIA);

					AppLog
							.info("<<length of array list for search criteria >>>>>"
									+ searchCriteriaValNew.length);
					AppLog.info("<< print val of searchCriteriaValNew>>>>>"
							+ searchCriteriaValNew);
					if (searchCriteriaValNew.length > 1) {
						AppLog.info("<<<<searchCriteriaValNew>>>>>>>>>"
								+ searchCriteriaValNew[0]
								+ searchCriteriaValNew[1]
								+ searchCriteriaValNew[2]);

						MRDScheduleDownloadDAO
								.setDownloadedFileDetailsForAllUsr(
										billWindowVal, searchCriteriaValNew[0],
										searchCriteriaValNew[1],
										searchCriteriaValNew[2]);
					} else {
						AppLog
								.info("<< Length of search criteria val is less than one >>"
										+ searchCriteriaValNew.length);
					}
				} else {
					AppLog.info("<<<<searchCriteriaValNew>>>>>>>>>"
							+ searchCriteriaVal);
					MRDScheduleDownloadDAO.setDownloadedFileDetailsForAllUsr(
							billWindowVal, searchCriteriaVal, null, null);

				}
			}

			/* Ended by Madhuri on 10th Aug 2016 as per Jtrac -4538 */
			/*
			 * 
			 * Commented by Madhuri on 10th Aug 2016 as below code only update
			 * details of files having zone ,mr and area
			 */
			/*
			 * String mrkey = MRDScheduleDownloadDAO.getMrdCode(
			 * searchCriteriaValNew[0], searchCriteriaValNew[1],
			 * searchCriteriaValNew[2]);
			 */

			/*
			 * if (mrkey != null) {
			 * MRDScheduleDownloadDAO.setDownloadedFileDetails(mrkey,
			 * billWindowVal);
			 * 
			 * }
			 */

			/* Ended by Madhuri on 6th June 2016 as per Jtrac -4464 */

			if (null == this.fileType || "".equalsIgnoreCase(this.fileType)) {
				// System.out.println("File Name::" + fileName);
				String filePath = PropertyUtil.getProperty("UCMdocumentUpload")
						+ "/" + userId + "/" + fileName;
				// System.out.println("filePath::"+filePath);
				fileInputStream = new FileInputStream(new File(filePath));
				String fileTypeTemp = getType("file:" + filePath);
				this.fileType = fileTypeTemp;
				// System.out.println("Content type::" + fileType);
			} else {
				if ("log".equals(this.fileType)) {
					String filePath = logFileLocation + "/" + fileName;
					// System.out.println("filePath::"+filePath);
					fileInputStream = new FileInputStream(new File(filePath));
				}

			}
			// System.out.println("fileType::"+fileType);
			if ("application/zip".equalsIgnoreCase(fileType)) {
				forward = "zip";
			}
			if ("application/octet-stream".equalsIgnoreCase(fileType)) {
				forward = "xls";
			}
			if ("log".equalsIgnoreCase(fileType)) {
				forward = "txt";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			addActionError("There was a problem while processing your request.");
			AppLog.error(e);
			AppLog.end();
			return "error";

		}
		return forward;
	}

	/**
	 * @return fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @return response
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}

	/**
	 * <p>
	 * This method is used to get Content type from the file url
	 * </p>
	 * 
	 * @param fileUrl
	 * @return
	 */
	public String getType(String fileUrl) {
		String type = null;
		try {
			URL u = new URL(fileUrl);
			URLConnection uc = u.openConnection();
			type = uc.getContentType();
		} catch (Exception e) {
			AppLog.error(e);
		}
		return type;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
   /* Added by Madhuri on 6th June 2016 as per Jtrac -4464*/
    /**
     * @return billWindow
     */
    public String getbillWindow(){
		
		return billWindow;
	}
	/**
	 * @return searchCriteria
	 */
	public String getSearchCriteria(){
		
		return searchCriteria;
	}
/* Ended by Madhuri on 6th June 2016 as per Jtrac -4464
	 */
	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return billWindow
	 */
	public String getBillWindow() {
		return billWindow;
	}
	/* Added by Madhuri on 6th June 2016 as per Jtrac -4464
	 */
	/**
	 * @param billWindow
	 */
	public void setBillWindow(String billWindow) {
		this.billWindow = billWindow;
	}

	/**
	 * @param searchCriteria
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	/* Ended by Madhuri on 6th June 2016 as per Jtrac -4464
	 */

}
