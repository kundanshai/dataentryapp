/************************************************************************************************************
 * @(#) HHDCredentialsAction.java   12-DEC-2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.HHDCredentialsDAO;
import com.tcs.djb.model.HHDCredentialsDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This class contains all the functionality required for Hand Held Device(HHD)
 * Credentials.
 * </p>
 * 
 * @author Karthick K(Tata Consultancy Services) since: 12-DEC-2013
 * 
 */
@SuppressWarnings("deprecation")
public class HHDCredentialsAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "35";
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 9169433976728316650L;
	private String addHHDiD;
	private String addPassword;
	private String deleteHHDiD;
	List<HHDCredentialsDetails> hhdCredentialsDetailsList;
	private String hhdId;
	private String hidAction;
	private InputStream inputStream;
	private String maxRecordPerPage;
	List<String> maxRecordPerPageDropdownList;
	private String pageNo;
	List<String> pageNoDropdownList;
	private String previousHHDId;
	private String previousMeterReader;
	private String prevValueHhdId;
	private HttpServletResponse response;
	private String status;
	private String toHHDiD;
	private String toPassword;
	private int totalRecords;

	/**
	 * method for all ajax call
	 * 
	 * @return
	 */
	public String ajaxMethod() {
		AppLog.begin();

		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.login.expired"));
				AppLog.end();
				return "success";
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}

			if ("addHHDCredentialsDetails".equalsIgnoreCase(hidAction)) {

				int recordinserted = HHDCredentialsDAO
						.insertHHDCredentialsAJaxDetails(addHHDiD, addPassword,
								userId);
				if (recordinserted > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Inserted.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
				} else {
					session
							.put(
									"SERVER_MESSAGE",
									"<font color='red'><b>Problem in inserting last record. Credentials May already exist.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
				}
			}

			if ("updateHHDCredentialsDetails".equalsIgnoreCase(hidAction)) {

				int recordUpdated = HHDCredentialsDAO
						.updateHHDCredentialsAJaxDetails(prevValueHhdId,
								toHHDiD, toPassword, userId);
				if (recordUpdated > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
				} else {
					session
							.put("SERVER_MESSAGE",
									"<font color='red'><b>Problem in updating HHD credentials.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
				}
			}

			if ("deleteHHDCredentialsDetails".equalsIgnoreCase(hidAction)) {

				if (null != deleteHHDiD || "" != deleteHHDiD) {
					int recordDeleted = HHDCredentialsDAO
							.deleteHHDCredentialsAJaxDetails(deleteHHDiD);
					if (recordDeleted > 0) {
						session
								.put("SERVER_MESSAGE",
										"<font color='#33CC33'><b>Last Record was Successfully Deleted.</b></font>");
						inputStream = (new StringBufferInputStream("SUCCESS"));
					} else {
						session
								.put("SERVER_MESSAGE",
										"<font color='red'><b>Problem in deleting HHD credentials.</b></font>");
						inputStream = (new StringBufferInputStream("SUCCESS"));
					}
				}

			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */

	public String execute() {
		AppLog.begin();
		String result = "error";
		try {

			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userName");
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
			session.put("CURR_TAB", "HHD");

			if (null == hidAction || "".equalsIgnoreCase(hidAction)
					|| "search".equalsIgnoreCase(hidAction)
					|| "searchAfterSave".equalsIgnoreCase(hidAction)) {
				if (!"searchAfterSave".equalsIgnoreCase(hidAction)) {
					session.put("SERVER_MESSAGE", "");
				} else {
					if ("Inactive".equalsIgnoreCase(status)) {
						this.status = "Active";
					}
				}
				this.hidAction = "search";
				if (null == this.pageNo || "".equals(this.pageNo)) {
					this.pageNo = "1";
					this.maxRecordPerPage = PropertyUtil
							.getProperty("RECORDS_PER_PAGE");
				}

				this.totalRecords = HHDCredentialsDAO
						.getTotalCountOfHHDCredentialsRecords();

				if (totalRecords > 0) {
					if (Integer.parseInt(maxRecordPerPage)
							* Integer.parseInt(pageNo) > totalRecords) {
						this.pageNo = Integer.toString((int) totalRecords
								/ Integer.parseInt(maxRecordPerPage) + 1);
					}
					List<String> pageNoList = new ArrayList<String>();
					List<String> maxPageNoList = new ArrayList<String>();
					for (int i = 0, j = 1; i < totalRecords; i++) {
						if (i % Integer.parseInt(maxRecordPerPage) == 0) {
							pageNoList.add(Integer.toString(j++));
							if (i != 0 && i <= 200) {
								maxPageNoList.add(Integer.toString(i));
							}
						}
					}
					this.pageNoDropdownList = pageNoList;
					this.hhdCredentialsDetailsList = (ArrayList<HHDCredentialsDetails>) HHDCredentialsDAO
							.getHHDCredentialsDetailsList(pageNo,
									maxRecordPerPage);
					result = "success";
				}
			}

		} catch (Exception e) {
			addActionError("There was a problem while Processing your Request. Please Try Again.");
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the addHHDiD
	 */
	public String getAddHHDiD() {
		return addHHDiD;
	}

	/**
	 * @return the addPassword
	 */
	public String getAddPassword() {
		return addPassword;
	}

	/**
	 * @return the deleteHHDiD
	 */
	public String getDeleteHHDiD() {
		return deleteHHDiD;
	}

	/**
	 * @return the hhdCredentialsDetailsList
	 */
	public List<HHDCredentialsDetails> getHhdCredentialsDetailsList() {
		return hhdCredentialsDetailsList;
	}

	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the maxRecordPerPageDropdownList
	 */
	public List<String> getMaxRecordPerPageDropdownList() {
		return maxRecordPerPageDropdownList;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @return the pageNoDropdownList
	 */
	public List<String> getPageNoDropdownList() {
		return pageNoDropdownList;
	}

	/**
	 * @return the previousHHDId
	 */
	public String getPreviousHHDId() {
		return previousHHDId;
	}

	/**
	 * @return the previousMeterReader
	 */
	public String getPreviousMeterReader() {
		return previousMeterReader;
	}

	/**
	 * @return the prevValueHhdId
	 */
	public String getPrevValueHhdId() {
		return prevValueHhdId;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the toHHDiD
	 */
	public String getToHHDiD() {
		return toHHDiD;
	}

	/**
	 * @return the toPassword
	 */
	public String getToPassword() {
		return toPassword;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param addHHDiD
	 *            the addHHDiD to set
	 */
	public void setAddHHDiD(String addHHDiD) {
		this.addHHDiD = addHHDiD;
	}

	/**
	 * @param addPassword
	 *            the addPassword to set
	 */
	public void setAddPassword(String addPassword) {
		this.addPassword = addPassword;
	}

	/**
	 * @param deleteHHDiD
	 *            the deleteHHDiD to set
	 */
	public void setDeleteHHDiD(String deleteHHDiD) {
		this.deleteHHDiD = deleteHHDiD;
	}

	/**
	 * @param hhdCredentialsDetailsList
	 */
	public void setHhdCredentialsDetailsList(
			List<HHDCredentialsDetails> hhdCredentialsDetailsList) {
		this.hhdCredentialsDetailsList = hhdCredentialsDetailsList;
	}

	/**
	 * @param mrKeyList
	 *            the mrKeyList to set
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param maxRecordPerPageDropdownList
	 *            the maxRecordPerPageDropdownList to set
	 */
	public void setMaxRecordPerPageDropdownList(
			List<String> maxRecordPerPageDropdownList) {
		this.maxRecordPerPageDropdownList = maxRecordPerPageDropdownList;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @param pageNoDropdownList
	 *            the pageNoDropdownList to set
	 */
	public void setPageNoDropdownList(List<String> pageNoDropdownList) {
		this.pageNoDropdownList = pageNoDropdownList;
	}

	/**
	 * @param previousHHDId
	 *            the previousHHDId to set
	 */
	public void setPreviousHHDId(String previousHHDId) {
		this.previousHHDId = previousHHDId;
	}

	/**
	 * @param previousMeterReader
	 *            the previousMeterReader to set
	 */
	public void setPreviousMeterReader(String previousMeterReader) {
		this.previousMeterReader = previousMeterReader;
	}

	/**
	 * @param prevValueHhdId
	 */
	public void setPrevValueHhdId(String prevValueHhdId) {
		this.prevValueHhdId = prevValueHhdId;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param toHHDiD
	 */
	public void setToHHDiD(String toHHDiD) {
		this.toHHDiD = toHHDiD;
	}

	/**
	 * @param toPassword
	 */
	public void setToPassword(String toPassword) {
		this.toPassword = toPassword;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
