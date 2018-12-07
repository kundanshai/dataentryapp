/************************************************************************************************************
 * @(#) RoleScreenMappingAction.java   22 Apr 2013
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
import com.tcs.djb.dao.RoleScreenMapDAO;
import com.tcs.djb.model.RoleScreenMappingDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action Class for Set Screen Access to User/Role
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 * 
 */
public class RoleScreenMappingAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String effectiveToDate;
	private static final String SCREEN_ID = "6";
	private String hidAction;

	private InputStream inputStream;
	private HttpServletResponse response;
	List<RoleScreenMappingDetails> roleScreenMappingDetailsList;
	private String screenId;

	private String toUpdateScreenIdList;

	private String userIdList;
	private String userRole;

	/**
	 * <p>
	 * For all ajax call Set Screen Access to User/Role
	 * </p>
	 * 
	 * @return
	 */
	public String ajaxMethod() {
		AppLog.begin();
		// System.out.println("hidAction::" + hidAction + "::zoneCode::"
		// + zoneCode);
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
			if (null != userIdList && !"".equals(userIdList.trim())) {
				String userIdArray[] = userIdList.split(",");
				String tempUserIdList = "";
				for (int i = 0; i < userIdArray.length; i++) {
					String tempString = userIdArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						tempUserIdList += "," + tempString;
					}
				}
				if (tempUserIdList.indexOf(',') == 0) {
					tempUserIdList = tempUserIdList.substring(1);
				}
				userIdList = tempUserIdList;
			}
			if ("provideAccess".equalsIgnoreCase(hidAction)) {
				int recordUpdated = 0;
				if (null != userRole && !"".equals(userRole.trim())) {
					recordUpdated = RoleScreenMapDAO.provideAccessToRole(
							userRole, screenId, userId);
				} else {
					if (RoleScreenMapDAO.isValidUserId(userIdList)) {
						recordUpdated = RoleScreenMapDAO.provideAccessToUser(
								userIdList, screenId, userId);
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>Invalid User ID.</b></font>"));
						AppLog.end();
						return SUCCESS;
					}
				}
				if (recordUpdated > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>");
					inputStream = (new StringBufferInputStream(
							"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>"));
				} else {
					session.put("SERVER_MESSAGE", "");
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>ERROR: Last Record could not be Updated.</b></font>"));
				}
			}
			if ("revokeAccess".equalsIgnoreCase(hidAction)) {
				int recordUpdated = 0;
				if (null != userRole && !"".equals(userRole.trim())) {
					recordUpdated = RoleScreenMapDAO.revokeAccessToRole(
							userRole, screenId, userId);
				} else {
					if (RoleScreenMapDAO.isValidUserId(userIdList)) {
						recordUpdated = RoleScreenMapDAO.revokeAccessToUser(
								userIdList, screenId, userId);
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>Invalid User ID.</b></font>"));
						AppLog.end();
						return SUCCESS;
					}
				}
				if (recordUpdated > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>");
					inputStream = (new StringBufferInputStream(
							"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>"));
				} else {
					session.put("SERVER_MESSAGE", "");
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>ERROR: Last Record could not be Updated.</b></font>"));
				}
			}
			if ("updateEffectiveToDate".equalsIgnoreCase(hidAction)) {
				int recordUpdated = 0;
				if (null != userRole && !"".equals(userRole.trim())) {
					recordUpdated = RoleScreenMapDAO
							.updateEffectiveToDateToRole(userRole, screenId,
									effectiveToDate, userId);
				} else {
					recordUpdated = RoleScreenMapDAO
							.updateEffectiveToDateToUserId(userIdList,
									screenId, effectiveToDate, userId);
				}
				if (recordUpdated > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>");
					inputStream = (new StringBufferInputStream(
							"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>"));
				} else {
					session.put("SERVER_MESSAGE", "");
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>ERROR: Last Record could not be Updated.</b></font>"));
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

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			if (null == userIdSession || "".equals(userIdSession)) {
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
			if (null == hidAction || "".equalsIgnoreCase(hidAction)) {
				session.put("roleListMap", RoleScreenMapDAO.getRoleList());
				result = "success";
			}
			if (null != userIdList && !"".equals(userIdList.trim())) {
				String userIdArray[] = userIdList.split(",");
				String tempUserIdList = "";
				for (int i = 0; i < userIdArray.length; i++) {
					String tempString = userIdArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						tempUserIdList += "," + tempString;
					}
				}
				if (tempUserIdList.indexOf(',') == 0) {
					tempUserIdList = tempUserIdList.substring(1);
				}
				userIdList = tempUserIdList;
			}
			if ("search".equalsIgnoreCase(hidAction)) {
				if (null != userRole && !"".equals(userRole.trim())) {
					roleScreenMappingDetailsList = RoleScreenMapDAO
							.getRoleScreenMappingDetailsListByRole(userRole);
				} else {
					if (RoleScreenMapDAO.isValidUserId(userIdList)) {
						roleScreenMappingDetailsList = RoleScreenMapDAO
								.getRoleScreenMappingDetailsListByUserId(userIdList);
					}
				}
				result = "success";
			}
			return result;
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the effectiveToDate
	 */
	public String getEffectiveToDate() {
		return effectiveToDate;
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
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the roleScreenMappingDetailsList
	 */
	public List<RoleScreenMappingDetails> getRoleScreenMappingDetailsList() {
		return roleScreenMappingDetailsList;
	}

	/**
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * @return the toUpdateScreenIdList
	 */
	public String getToUpdateScreenIdList() {
		return toUpdateScreenIdList;
	}

	/**
	 * @return the userIdList
	 */
	public String getUserIdList() {
		return userIdList;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param effectiveToDate
	 *            the effectiveToDate to set
	 */
	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
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
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param roleScreenMappingDetailsList
	 *            the roleScreenMappingDetailsList to set
	 */
	public void setRoleScreenMappingDetailsList(
			List<RoleScreenMappingDetails> roleScreenMappingDetailsList) {
		this.roleScreenMappingDetailsList = roleScreenMappingDetailsList;
	}

	/**
	 * @param screenId
	 *            the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param toUpdateScreenIdList
	 *            the toUpdateScreenIdList to set
	 */
	public void setToUpdateScreenIdList(String toUpdateScreenIdList) {
		this.toUpdateScreenIdList = toUpdateScreenIdList;
	}

	/**
	 * @param userIdList
	 *            the userIdList to set
	 */
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
