/************************************************************************************************************
 * @(#) ChangePasswordAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.ChangePasswordDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is an Action class for all the ajax calls made while changing password
 * </p>
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
@SuppressWarnings("deprecation")
public class ChangePasswordAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * <p>
	 * Screen Id for Set Role screen.
	 * </p>
	 */
	private static final String SCREEN_ID = "7";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hidAction;
	private InputStream inputStream;
	private String oldPassword;
	private String resetPassword;
	private String resetUserId;
	private HttpServletResponse response;
	private String userType;

	/**
	 * <p>
	 * method for all ajax call.
	 * </p>
	 * 
	 * @return
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			String userTypeInSession = (String) session.get("userType");
			if (null == userIdSession || "".equals(userIdSession)) {
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.login.expired"));
				AppLog.end();
				return "success";
			}
			if (!"SELF".equals(userType)
					&& !ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}
			if ("updatePassword".equalsIgnoreCase(hidAction)) {
				if (null == resetUserId
						|| "".equalsIgnoreCase(resetUserId.trim())) {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>"
									+ getText("error.all.mandatory")
									+ "</b></font>"));
				}
				if ("SELF".equals(userType)) {
					if (null == oldPassword
							|| "".equalsIgnoreCase(oldPassword.trim())) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>"
										+ getText("error.all.mandatory")
										+ "</b></font>"));
					}
				}
				if (null == resetPassword
						|| "".equalsIgnoreCase(resetPassword.trim())) {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>"
									+ getText("error.all.mandatory")
									+ "</b></font>"));
				}
				HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("passwordChangeType", userType);
				inputMap.put("resetUserId", resetUserId);
				inputMap.put("userType", userTypeInSession);
				inputMap.put("oldPassword", oldPassword);
				inputMap.put("resetPassword", resetPassword);
				inputMap.put("updatedBy", userIdSession);
				inputMap = (HashMap<String, String>) ChangePasswordDAO
						.changePassword(inputMap);
				String isSuccess = (String) inputMap.get("isSuccess");
				if ("YES".equalsIgnoreCase(isSuccess)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>Password has been Successfully Updated.</b></font>"));
				} else {
					if ("SELF".equals(userType)) {
						String returnMessage = (String) inputMap
								.get("returnMessage");
						if (null != returnMessage) {
							inputStream = (new StringBufferInputStream(
									"<font color='red'><b>" + returnMessage
											+ "</b></font>"));
						} else {
							inputStream = (new StringBufferInputStream(
									"<font color='red'><b>"
											+ getText("error.invalid.credential")
											+ "</b></font>"));
						}
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>"
										+ getText("error.invalid.userid")
										+ "</b></font>"));
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
			String userTypeInSession = (String) session.get("userType");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "ADMIN");
			if (!"SELF".equals(userType)) {
				if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
					addActionError(getText("error.access.denied"));
					AppLog.end();
					return "login";
				}
				// String loggedInRoleId = (String) session.get("userRoleId");
				// if (null == loggedInRoleId || "".equals(userName)
				// || !"1".equals(loggedInRoleId)) {
				// addActionError(getText("error.access.denied"));
				// AppLog.end();
				// return "login";
				// }
			}
			// System.out.println("hidAction::" + hidAction + "::userType::"
			// + userType + "::resetUserId::" + resetUserId
			// + "::oldPassword::" + oldPassword + "::resetPassword::"
			// + resetPassword);
			session.put("passwordChangeType", userType);
			session.remove("passwordChangeSuccess");
			if ("updatePassword".equalsIgnoreCase(hidAction)) {
				if (null == resetUserId
						|| "".equalsIgnoreCase(resetUserId.trim())) {
					addActionError(getText("error.all.mandatory"));
					return "error";
				}
				if ("SELF".equals(userType)) {
					if (null == oldPassword
							|| "".equalsIgnoreCase(oldPassword.trim())) {
						addActionError(getText("error.all.mandatory"));
						return "error";
					}
				}
				if (null == resetPassword
						|| "".equalsIgnoreCase(resetPassword.trim())) {
					addActionError(getText("error.all.mandatory"));
					return "error";
				}
				HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("passwordChangeType", userType);
				inputMap.put("resetUserId", resetUserId);
				inputMap.put("userType", userTypeInSession);
				inputMap.put("oldPassword", oldPassword);
				inputMap.put("resetPassword", resetPassword);
				inputMap.put("updatedBy", userName);
				inputMap = (HashMap<String, String>) ChangePasswordDAO
						.changePassword(inputMap);
				String isSuccess = (String) inputMap.get("isSuccess");
				if ("YES".equalsIgnoreCase(isSuccess)) {
					if ("SELF".equals(userType)) {
						session.remove("passwordChangeSuccess");
						addActionError(getText("message.success.pasword.change"));
						return "login";
					} else {
						session.put("passwordChangeSuccess", "Y");
					}
				} else {
					if ("SELF".equals(userType)) {
						addActionError(getText("error.invalid.credential"));
						return "error";
					} else {
						session.remove("passwordChangeSuccess");
						addActionError(getText("error.invalid.userid"));
						return "error";
					}

				}
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
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @return the resetPassword
	 */
	public String getResetPassword() {
		return resetPassword;
	}

	/**
	 * @return the resetUserId
	 */
	public String getResetUserId() {
		return resetUserId;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
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
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @param resetPassword
	 *            the resetPassword to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @param resetUserId
	 *            the resetUserId to set
	 */
	public void setResetUserId(String resetUserId) {
		this.resetUserId = resetUserId;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
	 * (javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
