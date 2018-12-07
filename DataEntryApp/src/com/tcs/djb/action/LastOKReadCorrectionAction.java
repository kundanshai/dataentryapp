/************************************************************************************************************
 * @(#) LastOKReadCorrectionAction.java   18 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.LastOKReadCorrectionDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for processing last OK read of a KNO for Correction
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @history 18-03-2013 Matiur Rahman changed userName to userId.
 * 
 */
@SuppressWarnings( { "deprecation"})
public class LastOKReadCorrectionAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "14";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String consumerName;
	private String hidAction;

	private String hidUserId;
	private InputStream inputStream;

	private String kno;

	private String lastOKRead;
	private String lastOKReadDate;
	private String lastOKReadStatus;
	private HttpServletResponse response;

	/**
	 * <p>
	 * For all ajax call of last OK read Correction of a KNO
	 * </p>
	 * 
	 * @return string
	 */

	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
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
			if (null != hidAction
					&& "validateKNO".equalsIgnoreCase(hidAction.trim())) {
				if (null == kno || "".equalsIgnoreCase(kno.trim())) {
					inputStream = new StringBufferInputStream(
							"<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: KNO is Mandatory </b></font></div>");
				} else {
					GetterDAO getterDAO = new GetterDAO();
					String consumerNameCCB = getterDAO.getConsumerDetails(kno);
					if (null != consumerNameCCB && !"".equals(consumerNameCCB)) {
						inputStream = new StringBufferInputStream(
								consumerNameCCB);

					} else {
						inputStream = new StringBufferInputStream(
								"<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: Invalid KNO: "
										+ kno + "</b></font></div>");
					}
				}
			}
			if (null != hidAction
					&& "processData".equalsIgnoreCase(hidAction.trim())) {
				if (null == kno || "".equalsIgnoreCase(kno.trim())) {
					inputStream = new StringBufferInputStream(
							"<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: KNO is Mandatory </b></font></div>");
				} else {
					GetterDAO getterDAO = new GetterDAO();
					String lastOKReadStatusCode = getterDAO
							.getLastOKReadStatusCode(lastOKReadStatus);
					if (null != lastOKReadStatusCode
							&& !"".equalsIgnoreCase(lastOKReadStatusCode)) {
						LastOKReadCorrectionDAO lastOKReadCorrectionDAO = new LastOKReadCorrectionDAO(
								kno, lastOKReadDate, lastOKRead,
								lastOKReadStatusCode, userName);
						inputStream = new StringBufferInputStream(
								"<div class='search-option' title='Server Message'><font color='green' size='2'><b> Process Initiated Successfully</b></font></div>");

					} else {
						inputStream = new StringBufferInputStream(
								"<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: There was problem while processing as Invalid Last Read Status (LSTTS)</b></font></div>");

					}
				}
			}

			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: There was problem while processing.</b></font></div>");

			// e.printStackTrace();
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
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
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
			session.put("CURR_TAB", "MRD");
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			return "login";
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the hidUserId
	 */
	public String getHidUserId() {
		return hidUserId;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastOKRead
	 */
	public String getLastOKRead() {
		return lastOKRead;
	}

	/**
	 * @return the lastOKReadDate
	 */
	public String getLastOKReadDate() {
		return lastOKReadDate;
	}

	/**
	 * @return the lastOKReadStatus
	 */
	public String getLastOKReadStatus() {
		return lastOKReadStatus;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param consumerName
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @param hidAction
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param hidUserId
	 */
	public void setHidUserId(String hidUserId) {
		this.hidUserId = hidUserId;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param kno
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastOKRead
	 */
	public void setLastOKRead(String lastOKRead) {
		this.lastOKRead = lastOKRead;
	}

	/**
	 * @param lastOKReadDate
	 */
	public void setLastOKReadDate(String lastOKReadDate) {
		this.lastOKReadDate = lastOKReadDate;
	}

	/**
	 * @param lastOKReadStatus
	 */
	public void setLastOKReadStatus(String lastOKReadStatus) {
		this.lastOKReadStatus = lastOKReadStatus;
	}

	/**
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}
