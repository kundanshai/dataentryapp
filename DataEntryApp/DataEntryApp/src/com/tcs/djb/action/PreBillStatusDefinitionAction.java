/************************************************************************************************************
 * @(#) PreBillStatusDefinitionAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.PreBillStatusDefinitionDAO;
import com.tcs.djb.model.PreBillStatusDefinitionDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is an action class for Pre Bill Status Definition Screen
 * </p>
 * 
 */
public class PreBillStatusDefinitionAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "18";
	private static final long serialVersionUID = 1L;

	/**
	 * hidden Action Set.
	 */
	private String hidAction;

	/**
	 * InputStream required for AJax call.
	 */
	private InputStream inputStream;

	/**
	 * record last Updated By.
	 */
	private String lastUpdatedBy;

	/**
	 * record lastUpdatedOn.
	 */
	private String lastUpdatedOn;

	/**
	 * New PreBillStatusId to be Added.
	 */
	/**
	 * Pre Bill Status Definition Details List
	 */
	List<PreBillStatusDefinitionDetails> preBillStatusDefinitionDetailsList;
	/**
	 * pre Bill Status description.
	 */
	private String preBillStatusDescription;
	/**
	 * pre Bill Status Id.
	 */
	private String preBillStatusId;
	private HttpServletResponse response;

	/**
	 * <p>
	 * For all ajax call in Data Entry Search screen
	 * </p>
	 * 
	 * @return
	 */
	public String ajaxMethod() {
		AppLog.begin();
		// System.out.println("hidAction::" + hidAction);
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
			if ("validatePreBillStatusId".equalsIgnoreCase(hidAction)) {
				if (PreBillStatusDefinitionDAO
						.validatePreBillStatusId(preBillStatusId)) {
					inputStream = (new StringBufferInputStream("Y"));
				} else {
					inputStream = (new StringBufferInputStream("N"));
				}
			}
			if ("update".equalsIgnoreCase(hidAction)) {
				if (null != preBillStatusId && !"".equals(preBillStatusId)
						&& null != preBillStatusDescription
						&& !"".equals(preBillStatusDescription)) {
					this.lastUpdatedBy = userId;
					int totalRecords = PreBillStatusDefinitionDAO
							.updatePreBillStatusDefinitionList(preBillStatusId,
									preBillStatusDescription, lastUpdatedBy);
					if (totalRecords > 0) {
						session.put("DATA_SAVED", totalRecords
								+ " Record Updated Successfully");
						inputStream = (new StringBufferInputStream("Y"));
					} else {
						session.remove("DATA_SAVED");
						inputStream = (new StringBufferInputStream("N"));
					}
				} else {
					session.remove("DATA_SAVED");
					inputStream = (new StringBufferInputStream("N"));
				}
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
			// e.printStackTrace();
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "error";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
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
			session.put("CURR_TAB", "MASTER");
			this.preBillStatusId = "";
			this.preBillStatusDescription = "";
			if (null == hidAction || "".equals(hidAction)) {
				session.remove("DATA_SAVED");
			}
			List<PreBillStatusDefinitionDetails> preBillStatusDefinitionDetailsList = null;
			preBillStatusDefinitionDetailsList = PreBillStatusDefinitionDAO
					.getPreBillStatusDefinitionList();
			if (preBillStatusDefinitionDetailsList.size() > 0) {
				this.preBillStatusDefinitionDetailsList = preBillStatusDefinitionDetailsList;
			}
			result = "success";

		} catch (Exception e) {
			addActionError("There was a problem while Processing your Request. Please Try Again.");
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @return the lastUpdatedOn
	 */
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	/**
	 * @return the preBillStatusDefinitionDetailsList
	 */
	public List<PreBillStatusDefinitionDetails> getPreBillStatusDefinitionDetailsList() {
		return preBillStatusDefinitionDetailsList;
	}

	/**
	 * @return the preBillStatusDescription
	 */
	public String getPreBillStatusDescription() {
		return preBillStatusDescription;
	}

	/**
	 * @return the preBillStatusId
	 */
	public String getPreBillStatusId() {
		return preBillStatusId;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
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
	 * @param lastUpdatedBy
	 *            the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedOn
	 *            the lastUpdatedOn to set
	 */
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	/**
	 * @param preBillStatusDefinitionDetailsList
	 *            the preBillStatusDefinitionDetailsList to set
	 */
	public void setPreBillStatusDefinitionDetailsList(
			List<PreBillStatusDefinitionDetails> preBillStatusDefinitionDetailsList) {
		this.preBillStatusDefinitionDetailsList = preBillStatusDefinitionDetailsList;
	}

	/**
	 * @param preBillStatusDescription
	 *            the preBillStatusDescription to set
	 */
	public void setPreBillStatusDescription(String preBillStatusDescription) {
		this.preBillStatusDescription = preBillStatusDescription;
	}

	/**
	 * @param preBillStatusId
	 *            the preBillStatusId to set
	 */
	public void setPreBillStatusId(String preBillStatusId) {
		this.preBillStatusId = preBillStatusId;
	}

	/**
	 * @param response
	 *            the response to set
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
