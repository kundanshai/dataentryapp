/************************************************************************************************************
 * @(#) LogoutAction.java   28 Jul 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * Action class for Logout purpose
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 28-07-2012
 */
public class LogoutAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String url;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		AppLog.begin();
		try {
			Map session = ActionContext.getContext().getSession();
			ArrayList<String> userRoleList = (ArrayList<String>) session
					.get("userRoleList");
			String lastLoginType = (String) session.get("LOGIN_TYPE");
			String lastCallFrom = (String) session.get("LOGIN_CALL_FROM");
			if (null != lastCallFrom && !"".equals(lastCallFrom.trim())) {
				url = lastCallFrom;
				AppLog.end();
				return "redirect";
			}
			session.clear();
			if (session instanceof org.apache.struts2.dispatcher.SessionMap) {

				((org.apache.struts2.dispatcher.SessionMap) session)
						.invalidate();
				// session = ActionContext.getContext().getSession();
				session.put("userRoleList", userRoleList);
				session.put("LOGIN_TYPE", lastLoginType);
			}
			if (null != type) {
				addActionError(type);
			}
		} catch (IllegalStateException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
