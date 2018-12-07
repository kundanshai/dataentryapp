/************************************************************************************************************
 * @(#) ViewAppLogAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.model.FileDetails;
import com.tcs.djb.model.ViewApplog;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This action class is used to view App Log
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * 
 */
public class ViewAppLogAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appLogName;
	private String appName;
	private String hidAction;
	private final String LOG_FILE_LOCATION = "/home/user/djb_portal_logs/";

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		try {
			// System.out.println("In Time::" + System.currentTimeMillis());
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "ADMIN");
			// System.out.println("hidAction::" + hidAction + "::appName::"
			// + appName);
			session.put("APP_LOG_APLICATION_NAME", appName);
			// String appLog = "";
			if ("Consumer Portal".equals(appName)) {
				appLogName = "consumer_portal";
			} else if ("Employee Portal".equals(appName)) {
				appLogName = "employee_bp_web_portal";
			} else if ("RMS Web Service".equals(appName)) {
				appLogName = "web_service_call";
			} else if ("Online Batch Billing".equals(appName)) {
				appLogName = "online_batch_billing";
			} else {
				appLogName = "mrd_data_entry";
			}
			ArrayList<FileDetails> fileDetailsList = (ArrayList<FileDetails>) ViewApplog
					.getFileList(LOG_FILE_LOCATION, appLogName);
			session.remove("fileDetailsList");
			if (null != fileDetailsList && fileDetailsList.size() > 0) {
				session.put("fileDetailsList", fileDetailsList);
				// System.out.println("fileDetailsList Size::"
				// + fileDetailsList.size());

			}

		} catch (ClassCastException e) {
			AppLog.error(e);
			AppLog.end();
			return "login";
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			AppLog.end();
			return "login";
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the appLogName
	 */
	public String getAppLogName() {
		return appLogName;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @param appLogName
	 *            the appLogName to set
	 */
	public void setAppLogName(String appLogName) {
		this.appLogName = appLogName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

}
