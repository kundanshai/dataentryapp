/************************************************************************************************************
 * @(#) SetRoleAccessAction.java   22 Apr 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.SetRoleAccessDAO;
import com.tcs.djb.model.UserRole;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Set User Role Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 * 
 */
public class SetRoleAccessAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * Screen Id for Set Role screen.
	 * </p>
	 */
	private static final String SCREEN_ID = "8";
	private String hidAction;
	private String userId;
	private String userRole;

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
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "ADMIN");
			// if (null == loggedInRoleId || "".equals(userName)
			// || !"1".equals(loggedInRoleId)) {
			// addActionError(getText("error.access.denied"));
			// AppLog.end();
			// return "login";
			// }
			// System.out.println("hidAction::" + hidAction + "::userId::"
			// + userId + "::userRole::" + userRole);
			session.remove("roleChangeSuccess");
			if ("updateUserRole".equalsIgnoreCase(hidAction)) {
				if (null == userId || "".equalsIgnoreCase(userId.trim())) {
					addActionError(getText("error.all.mandatory"));
					return "error";
				}
				if (null == userRole || "".equalsIgnoreCase(userRole.trim())) {
					addActionError(getText("error.all.mandatory"));
					return "error";
				}
				HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("userId", userId);
				inputMap.put("userRole", userRole);
				inputMap.put("updatedBy", userName);
				boolean isSuccess = SetRoleAccessDAO.updateUserRole(inputMap);
				if (isSuccess) {
					session.put("roleChangeSuccess", "Y");
				} else {
					session.remove("roleChangeSuccess");
					addActionError(getText("error.invalid.userid"));
					return "error";
				}
			} else {
				ArrayList<UserRole> userRoleList = (ArrayList<UserRole>) session
						.get("userRoleList");
				if (null == userRoleList || userRoleList.size() == 0) {
					userRoleList = (ArrayList<UserRole>) GetterDAO
							.getUserRoleList();
					if (null != userRoleList && userRoleList.size() > 0) {
						session.put("userRoleList", userRoleList);
					}
				} /*
				 * else { // System.out.println("userRoleList.size()::" // +
				 * userRoleList.size()); }
				 */
			}
			this.hidAction = "";
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
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
