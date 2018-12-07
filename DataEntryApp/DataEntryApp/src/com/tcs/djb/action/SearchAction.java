/************************************************************************************************************
 * @(#) SearchAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This is an action class used to search MRD Read Type
 * </p>
 * 
 */
@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		AppLog.begin();
		try {
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "error";
			}
			try {
				MRDReadTypeLookup mrdReadTypeLookup = (MRDReadTypeLookup) session
						.get("mrdReadTypeLookup");
				// if (null == mrdReadTypeLookup) {
				// addActionError(getText("error.login.expired"));
				// AppLog.end();
				// return "error";
				// }
				if (null == mrdReadTypeLookup) {
					session.put("mrdReadTypeLookup", GetterDAO
							.getMRDReadTypeList());
				}
			} catch (Exception e) {
				session
						.put("mrdReadTypeLookup", GetterDAO
								.getMRDReadTypeList());
			}
			session.remove("isFrozen");
			session.remove("freezeSuccess");
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			AppLog.end();
			return "error";
		}
		AppLog.end();
		return "success";
	}

}
