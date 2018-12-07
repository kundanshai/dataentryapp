/************************************************************************************************************
 * @(#) DataMigrationReportAction.java   01-Jun-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.DataMigrationReportDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Data Migration Report Generation Screen as per JTrac
 * DJB-4425 and OpenProject-1217.
 * </p>
 * 
 * @author Arnab Nandy (1227971)
 * @since 01-06-2016
 * 
 */
public class DataMigrationReportAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "50";
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String action;

	private File dataSheetCopied;

	private String hidAction;
	private String selectedZROCode = "";

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		action = getHidAction();
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
					AppLog.info("SERVER_MESSAGE");
					session.remove("SERVER_MESSAGE");
				}
			}
			loadDefault();
			session.remove("SERVER_MSG");
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return the dataSheetCopied
	 */
	public File getDataSheetCopied() {
		return dataSheetCopied;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * This method is used to set ZROListMap and FileListMap
	 */
	public void loadDefault() {
		AppLog.begin();
		String searchcontext = DJBConstants.DATA_MIGRATION_ZRO_LIST;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			session.put("ZROListMap", GetterDAO.getZROList(searchcontext));
			session.put("FileListMap", DataMigrationReportDAO.getFileList());

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

}