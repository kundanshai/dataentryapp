/************************************************************************************************************
 * @(#) CreateUserAction.java   22-Apr-2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.CreateUserDAO;
import com.tcs.djb.dao.RoleScreenMapDAO;
import com.tcs.djb.model.LDAPUserGroup;
import com.tcs.djb.model.LDAPUserGroupList;
import com.tcs.djb.model.UserDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.LDAPManager;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is the Action class for User Creation
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 * @history 03-09-2013 Matiur Rahman Added functionality for Biometric
 *          Enrollment.
 * @history 30-05-2016 Arnab Nandy added functionality for bypassing Biometric
 *          authentication enable and disable by editing {@link #ajaxMethod()}
 *          and adding {@link #enableUserForBypassingFP(String)} and
 *          {@link #disableUserForBypassingFP(String)}, according to JTrac
 *          DJB-4464 and OpenProject#1151.
 * @history 01-09-2013 Suraj Tiwari Added functionality for ZRO Location tagging
 *          of Data Entry User, according to JTrac DJB-4549
 */
@SuppressWarnings("deprecation")
public class CreateUserAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "5";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String checkBtnFP;
	private String confirmPasswordDE;
	private String confirmPasswordLDAP;
	private String currentTab;

	private String defaultValue;

	private String deUserRole;
	private String emailId;
	private String emailLDAP;
	private String firstName;
	private String firstNameLDAP;

	private String FIRTextData;
	private String[] groupsLDAP;
	private String hidAction;
	private InputStream inputStream;
	private String isActiveUser;
	private String isActiveUserDE;
	private String isActiveUserLDAP;
	private String isLockedUser;
	private String isLockedUserLDAP;
	private String lastName;

	private String lastNameLDAP;

	private String mobileNo;

	private String mobileNoLDAP;

	private String pageMode;

	private String password;

	private String passwordDE;

	private String passwordLDAP;
	private HttpServletResponse response;
	private String reSubmit;
	private String selectedGroupsLDAP;
	private String userAddress;

	private String userAddressDE;
	private String userAddressLDAP;
	private String userId;
	private String userIdDE;
	List<UserDetails> userList;

	private String userName;

	private String userNameDE;

	private String userRole;

	private String userRoleDE;

	private String zroLocation;

	private String zroLocationDE;

	private String zroLocationLDAP;

	private String zroLocationUpdateLDAP;

	private HashMap<String, String> zroLocationUpdateListDE;

	/**
	 * <p>
	 * Activate User For Data Entry Application. Users are created in Database.
	 * Activate user actually updates a ACTIVE flag to Y from N.
	 * </p>
	 * 
	 * @return
	 */
	public String activateUserForDEApplication() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("userId", userId);
			inputMap.put("updatedBy", userIdSession);
			boolean isSuccess = CreateUserDAO.activateUser(inputMap);
			if (null != currentTab && currentTab.indexOf("Data") > -1) {
				if (isSuccess) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Activated Successfully.</b></font>"));
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>User Could not Be Activated. Please Retry.</b></font>"));
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
			if (null == userIdSession || "".equals(userIdSession)) {
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
			if ("validateUserId".equalsIgnoreCase(hidAction)) {
				// System.out.println("hidAction::" + hidAction +
				// "::currentTab::"
				// + currentTab);
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					String returnMessage = LDAPManager.isLDAPUser(userId);
					if ("EXISTS".equalsIgnoreCase(returnMessage)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else if ("NOT_EXISTS".equalsIgnoreCase(returnMessage)) {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: User Created is for Application User(Employee Portal, CC&B, BI Publisher, UCM).</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='Red'><b>There was a Problem while Connecting to the User Directory. There may be a Problem while Creating a New User. Retry after Sometime or Contact System Administrator.</b></font>"));
					}
				} else if (currentTab.indexOf("Data") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Data Entry Application Only.</b></font>"));
					}
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
					/*
					 * Start : Added by Arnab Nandy (1227971) on 30-05-2016 for
					 * Two-factor authentication of Data Entry Users, according
					 * to JTrac DJB-4464 and OpenProject#1151
					 */
				} else if (currentTab.indexOf("Bypass") > -1) {
					if (userIdSession.equalsIgnoreCase(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID : User can't bypass their own fingerprint.</b></font>"));
					} else if (!CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID : User is not registered.</b></font>"));
					} else if (CreateUserDAO
							.validateUserRoleFingerPrint(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID : Invalid user role.</b></font>"));
					} else if (CreateUserDAO.validateUserIdInException(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>INVALID : User ID is already bypassed.</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Please update user's bypass flag for fingerprints.</b></font>"));
					}
				}
				/*
				 * End : Added by Arnab Nandy (1227971) on 30-05-2016 for
				 * Two-factor authentication of Data Entry Users, according to
				 * JTrac DJB-4464 and OpenProject#1151
				 */
			}
			if ("createUserId".equalsIgnoreCase(hidAction)) {
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					AppLog.end();
					return createUserForAllApplication();
				} else if (currentTab.indexOf("Data") > -1) {
					AppLog.end();
					return createUserForDEApplication();
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
				}
			}
			if ("updateUser".equalsIgnoreCase(hidAction)) {
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					AppLog.end();
					return updateUserForAllApplication();
				} else if (currentTab.indexOf("Data") > -1) {
					AppLog.end();
					return updateUserForDEApplication();
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
				}
			}
			if ("unlockUser".equalsIgnoreCase(hidAction)) {
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					AppLog.end();
					return unlockUserForAllApplication();
				} else if (currentTab.indexOf("Data") > -1) {
					AppLog.end();
					return updateUserForDEApplication();
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
				}
			}
			if ("disableUser".equalsIgnoreCase(hidAction)) {
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					AppLog.end();
					return disableUserForAllApplication();
				} else if (currentTab.indexOf("Data") > -1) {
					AppLog.end();
					return disableUserForDEApplication("DE");
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
				}
			}
			if ("enableUser".equalsIgnoreCase(hidAction)) {
				if (null == currentTab || currentTab.indexOf("All") > -1) {
					AppLog.end();
					return enableUserForAllApplication();
				} else if (currentTab.indexOf("Data") > -1) {
					AppLog.end();
					return enableUserForDEApplication("DE");
				} else if (currentTab.indexOf("Web") > -1) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
					}
				}
			}
			/*
			 * Start : Added by Arnab Nandy (1227971) on 30-05-2016 for
			 * Two-factor authentication of Data Entry Users, according to JTrac
			 * DJB-4464 and OpenProject#1151
			 */
			if ("bypassFP".equalsIgnoreCase(hidAction)) {
				if (currentTab.indexOf("Bypass") > -1) {
					if (enableUserForBypassingFP(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Enabling of fingerprint bypassing for the user is succeded.</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					}
				}
			}
			if ("bypassFPDisable".equalsIgnoreCase(hidAction)) {
				if (currentTab.indexOf("Bypass") > -1) {
					if (disableUserForBypassingFP(userId)) {
						inputStream = (new StringBufferInputStream(
								"<font color='blue'><b>Note: Disabling of fingerprint bypassing for the user is succeded.</b></font>"));
					} else {
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>INVALID</b></font>"));
					}
				}
			}
			/*
			 * Start : Added by Arnab Nandy (1227971) on 30-05-2016 for
			 * Two-factor authentication of Data Entry Users, according to JTrac
			 * DJB-4464 and OpenProject#1151
			 */
			// if ("deleteUser".equalsIgnoreCase(hidAction)) {
			// if (null == currentTab || currentTab.indexOf("All") > -1) {
			// AppLog.end();
			// return deleteUserForAllApplication();
			// } else if (currentTab.indexOf("Data") > -1) {
			// AppLog.end();
			// return disableUserForDEApplication("DE");
			// } else if (currentTab.indexOf("Web") > -1) {
			// if (CreateUserDAO.validateUserId(userId)) {
			// inputStream = (new StringBufferInputStream(
			// "<font color='red'><b>INVALID</b></font>"));
			// } else {
			// inputStream = (new StringBufferInputStream(
			// "<font color='blue'><b>Note: Users Created is for Web Service Application Only.</b></font>"));
			// }
			// }
			// }
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

	/**
	 * <p>
	 * Create User For All Application. Users are created in LDAP.
	 * </p>
	 * 
	 * @return
	 */
	public String createUserForAllApplication() {
		AppLog.begin();
		try {
			// Map<String, Object> session = ActionContext.getContext()
			// .getSession();
			if (null == currentTab || currentTab.indexOf("All") > -1) {
				if (null != selectedGroupsLDAP
						&& selectedGroupsLDAP.indexOf("de_portal") > -1
						&& !"Y".equals(reSubmit)) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"WARNING:The User Id exist in Data Entry Application."));
						return SUCCESS;
					}
				}
				UserDetails ldapUserDetails = new UserDetails();
				ldapUserDetails.setUserId(userId);
				ldapUserDetails.setPassword(password);
				ldapUserDetails.setFirstName(firstName);
				ldapUserDetails.setLastName(lastName);
				ldapUserDetails.setUserAddress(userAddress);
				ldapUserDetails.setEmailId(emailId);
				ldapUserDetails.setMobileNo(mobileNo);
				/*
				 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-4549
				 */
				ldapUserDetails.setZroLocation(zroLocation);
				/*
				 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-4549
				 */
				String[] groups = selectedGroupsLDAP.split(",");
				ldapUserDetails.setGroups(groups);

				String returnMessage = LDAPManager.addUser(ldapUserDetails);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User was Created Successfuly.</b></font>"));
					Map<String, Object> session = ActionContext.getContext()
							.getSession();
					String userIdSession = (String) session.get("userId");
					AppLog.info("User Id ::" + userId
							+ "::Successfully Created BY::" + userIdSession);
				} else if ("USER CREATED".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='orange'><b>Note: User Created Successfuly, But there was problem while assigning Access. Please provide Acess Using Update User Option.</b></font>"));
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>" + returnMessage
									+ "</b></font>"));
				}
				/*
				 * If user created successfully and selected DE Application for
				 * access user need to be created in DE also for screen access.
				 * Now Creating User in DE Application also.
				 */
				if ("SUCCESS".equalsIgnoreCase(returnMessage)
						|| "USER CREATED".equalsIgnoreCase(returnMessage)) {
					if (null != selectedGroupsLDAP
							&& selectedGroupsLDAP.indexOf("de_portal") > -1) {
						this.userRole = this.deUserRole;
					} else {
						this.userRole = "0";
					}
					this.userName = this.firstName + " " + this.lastName;
					this.password = "";
					AppLog.end();
					return createUserForDEApplication();

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

	/**
	 * *
	 * <p>
	 * Create User For Data Entry Application. Users are created in Database.
	 * </p>
	 * 
	 * @return
	 */
	public String createUserForDEApplication() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("userId", userId);
			inputMap.put("userRole", userRole);
			inputMap.put("createdBy", userIdSession);
			inputMap.put("password", password);
			inputMap.put("userName", userName);
			inputMap.put("userAddress", userAddress);
			inputMap.put("FIRTextData", FIRTextData);
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			inputMap.put("zroLocation", zroLocation);
			AppLog.debug("createUserAction method zroLocation>>>>>>>>"
					+ zroLocation);

			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			boolean isSuccess = CreateUserDAO.createUser(inputMap);
			if (null != currentTab && currentTab.indexOf("Data") > -1) {
				if (isSuccess) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Id Successfully Created.</b></font>"));
					AppLog.info("User Id ::" + userId
							+ "::Successfully Created BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>User Id Could not Be created. Please Retry.</b></font>"));
				}
			} else {
				if (!isSuccess
						&& (null == password || "".equalsIgnoreCase(password))) {
					activateUserForDEApplication();
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

	/**
	 * *
	 * <p>
	 * Delete User For All Application. Users are created in LDAP.
	 * </p>
	 * 
	 * @return
	 */
	public String deleteUserForAllApplication() {
		AppLog.begin();
		try {
			// Map<String, Object> session = ActionContext.getContext()
			// .getSession();
			if (null == currentTab || currentTab.indexOf("All") > -1) {
				if (null != selectedGroupsLDAP
						&& selectedGroupsLDAP.indexOf("de_portal") > -1
						&& !"Y".equals(reSubmit)) {
					if (CreateUserDAO.validateUserId(userId)) {
						inputStream = (new StringBufferInputStream(
								"WARNING:The User Id exist in Data Entry Application."));
						return SUCCESS;
					}
				}
				UserDetails ldapUserDetails = new UserDetails();
				ldapUserDetails.setUserId(userId);
				ldapUserDetails.setPassword(password);
				ldapUserDetails.setFirstName(firstName);
				ldapUserDetails.setLastName(lastName);
				ldapUserDetails.setUserAddress(userAddress);
				ldapUserDetails.setEmailId(emailId);
				ldapUserDetails.setMobileNo(mobileNo);
				String[] groups = selectedGroupsLDAP.split(",");
				ldapUserDetails.setGroups(groups);

				String returnMessage = LDAPManager.addUser(ldapUserDetails);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User was Created Successfuly.</b></font>"));
				} else if ("USER CREATED".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='orange'><b>Note: User Created Successfuly, But there was problem while assigning Access. Please provide Acess Using Update User Option.</b></font>"));
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>" + returnMessage
									+ "</b></font>"));
				}
				/*
				 * If user created successfully and selected DE Application for
				 * access user need to be created in DE also for screen access.
				 * Now Creating User in DE Application also.
				 */
				if (("SUCCESS".equalsIgnoreCase(returnMessage) || "USER CREATED"
						.equalsIgnoreCase(returnMessage))
						&& null != selectedGroupsLDAP
						&& selectedGroupsLDAP.indexOf("de_portal") > -1) {
					this.userName = this.firstName + " " + this.lastName;
					this.userRole = this.deUserRole;
					AppLog.end();
					return createUserForDEApplication();
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

	/**
	 * <p>
	 * This method is used to disable User For All Application.
	 * </p>
	 * 
	 * @return
	 */
	private String disableUserForAllApplication() {
		AppLog.begin();
		try {
			if (null == currentTab || currentTab.indexOf("All") > -1) {
				String returnMessage = LDAPManager.deactivateUser(userId);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User is Successfuly Disabled.</b></font>"));
					Map<String, Object> session = ActionContext.getContext()
							.getSession();
					String userIdSession = (String) session.get("userId");
					AppLog.info("User Id ::" + userId
							+ "::Successfuly Disabled BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>ERROR:" + returnMessage
									+ "</b></font>"));
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

	/**
	 * *
	 * <p>
	 * This method is used to disable User For Bypassing Finger Print.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac DJB-4464 and OpenProject#1151
	 * @since 30-05-2016
	 * @return
	 */
	public boolean disableUserForBypassingFP(String userToBeBpFp) {
		AppLog.begin();
		boolean isSuccess = false;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();

			isSuccess = CreateUserDAO.disableBypassFP(userToBeBpFp,
					userIdSession);

			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
			AppLog.error(e);
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * Disable User For Data Entry Application. Users are created in Database.
	 * Delete user actually updates a ACTIVE flag to N from Y.
	 * </p>
	 * 
	 * @return
	 */
	public String disableUserForDEApplication(String userType) {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("userId", userId);
			inputMap.put("updatedBy", userIdSession);
			inputMap.put("userType", userType);
			boolean isSuccess = CreateUserDAO.disableUser(inputMap);
			if (null != currentTab && currentTab.indexOf("Data") > -1) {
				if (isSuccess) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Id Successfully Disabled.</b></font>"));
					AppLog.info("User Id ::" + userId
							+ "::Successfully Disabled BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>User Id Could not Be Disabled. Please Retry.</b></font>"));
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

	/**
	 * <p>
	 * This method is used to enable User For All Application.
	 * </p>
	 * 
	 * @return
	 */
	private String enableUserForAllApplication() {
		AppLog.begin();
		try {
			if (null == currentTab || currentTab.indexOf("All") > -1) {
				String returnMessage = LDAPManager.activateUser(userId);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User is Successfuly Enabled.</b></font>"));
					Map<String, Object> session = ActionContext.getContext()
							.getSession();
					String userIdSession = (String) session.get("userId");
					AppLog.info("User id ::" + userId
							+ "::Successfuly Enabled By ::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>ERROR:" + returnMessage
									+ "</b></font>"));
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

	/**
	 * *
	 * <p>
	 * This method is used for Enabling User For Bypassing Finger Print.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac DJB-4464 and OpenProject#1151
	 * @since 30-05-2016
	 * @return
	 */
	public boolean enableUserForBypassingFP(String userToBeBpFp) {
		AppLog.begin();
		boolean isSuccess = false;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");

			isSuccess = CreateUserDAO.enableBypassFP(userToBeBpFp,
					userIdSession);

			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
			AppLog.error(e);
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * Enable User For Data Entry Application. Users are created in Database.
	 * Enable user actually updates a ACTIVE flag to Y from N.
	 * </p>
	 * 
	 * @return
	 */
	public String enableUserForDEApplication(String userType) {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("userId", userId);
			inputMap.put("updatedBy", userIdSession);
			inputMap.put("userType", userType);
			boolean isSuccess = CreateUserDAO.enableUser(inputMap);
			if (null != currentTab && currentTab.indexOf("Data") > -1) {
				if (isSuccess) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Id Successfully Enabled.</b></font>"));
					AppLog.info("User Id ::" + userId
							+ "::Successfully Enabled BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>User Id Could not Be Enabled. Please Retry.</b></font>"));
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
			// System.out.println("hidAction::" + hidAction + "::currentTab::"
			// + currentTab);
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
			if ("search".equalsIgnoreCase(hidAction)
					|| "back".equalsIgnoreCase(hidAction)) {
				// System.out.println("hidAction::" + hidAction
				// + ":2:currentTab::" + currentTab);
				if (null == currentTab || "".equals(currentTab)) {
					List<UserDetails> deUserDetailsList = CreateUserDAO
							.getAllDEUserDetailsList();
					if (null != deUserDetailsList
							&& deUserDetailsList.size() > 0) {
						userList = deUserDetailsList;
						session.remove("userList");
						session.put("userList", userList);
					}
				} else if (currentTab.indexOf("All") > -1) {
					List<UserDetails> ldapUserDetailsList = LDAPManager
							.getAllUser();
					if (null != ldapUserDetailsList
							&& ldapUserDetailsList.size() > 0) {
						userList = ldapUserDetailsList;
						session.remove("userList");
						session.put("userList", userList);
					}
				} else if (currentTab.indexOf("Data") > -1) {
					List<UserDetails> deUserDetailsList = CreateUserDAO
							.getAllDEUserDetailsList();
					if (null != deUserDetailsList
							&& deUserDetailsList.size() > 0) {
						userList = deUserDetailsList;
						session.remove("userList");
						session.put("userList", userList);
					}
				} else if (currentTab.indexOf("Web") > -1) {
					List<UserDetails> ldapUserDetailsList = LDAPManager
							.getAllUser();
					if (null != ldapUserDetailsList
							&& ldapUserDetailsList.size() > 0) {
						userList = ldapUserDetailsList;
						session.remove("userList");
						session.put("userList", userList);
					}
				}

				AppLog.end();
				return "search";
			}
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac
			 * DJB-45498
			 */
			UserDetails deUserDetails = CreateUserDAO.getDEUserDetails(userId);
			if (null != deUserDetails && null != deUserDetails.getZroLocation()
					&& deUserDetails.getZroLocation().trim().length() > 0) {
				this.zroLocationDE = deUserDetails.getZroLocation();
			}
			AppLog.info("Action view zro " + zroLocationDE + " ID " + userId);
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac
			 * DJB-45498
			 */
			// if ("back".equalsIgnoreCase(hidAction)) {
			// Object sessionObject = session.get("userList");
			// if (null != sessionObject) {
			// userList = (List<UserDetails>) sessionObject;
			// AppLog.end();
			// return "search";
			// } else {
			// List<UserDetails> ldapUserDetailsList = LDAPManager
			// .getAllUser();
			// if (null != ldapUserDetailsList
			// && ldapUserDetailsList.size() > 0) {
			// userList = ldapUserDetailsList;
			// session.remove("userList");
			// session.put("userList", userList);
			// AppLog.end();
			// return "search";
			// }
			// }
			// }
			/** Load Default values. */
			loadDefault();
			session.remove("roleChangeSuccess");
			if ("createUser".equalsIgnoreCase(hidAction)) {
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
				inputMap.put("createdBy", userIdSession);
				inputMap.put("password", password);
				inputMap.put("userName", userName);
				inputMap.put("userAddress", userAddress);
				boolean isSuccess = CreateUserDAO.createUser(inputMap);
				if (isSuccess) {
					session.put("roleChangeSuccess", "Y");
				} else {
					session.remove("roleChangeSuccess");
					addActionError(getText("error.invalid.userid"));
					return "error";
				}
			}
			if ("viewDetails".equalsIgnoreCase(hidAction)) {
				// System.out.println("User Id::" + userId + "::currentTab::"
				// + currentTab + "::pageMode::" + pageMode);
				if (null == currentTab || "".equals(currentTab)) {
					// UserDetails deUserDetails = CreateUserDAO
					// .getDEUserDetails(userId);
					if (null != deUserDetails) {
						this.userId = deUserDetails.getUserId();
						this.userNameDE = deUserDetails.getFirstName();
						this.userAddressDE = deUserDetails.getUserAddress();
						this.userRoleDE = deUserDetails.getUserRole();
						this.defaultValue = DJBConstants.DEFAULT_PASSWORD;
						this.isActiveUserDE = deUserDetails.getIsActive();
						/*
						 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016
						 * for ZRO Location tagging of Data Entry Users,
						 * according to JTrac DJB-45498
						 */
						this.zroLocationDE = deUserDetails.getZroLocation();
						/*
						 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016
						 * for ZRO Location tagging of Data Entry Users,
						 * according to JTrac DJB-45498
						 */
					}
				} else if (null == currentTab || currentTab.indexOf("All") > -1) {
					UserDetails userDetails = LDAPManager
							.getUserDetails(userId);
					if (null != userDetails) {
						this.isLockedUserLDAP = userDetails.getIsLocked();
						this.isActiveUserLDAP = userDetails.getIsActive();
						this.firstNameLDAP = userDetails.getFirstName();
						this.lastNameLDAP = userDetails.getLastName();
						this.userAddressLDAP = userDetails.getUserAddress();
						this.emailLDAP = userDetails.getEmailId();
						this.mobileNoLDAP = userDetails.getMobileNo();
						this.defaultValue = DJBConstants.DEFAULT_PASSWORD;
						this.groupsLDAP = userDetails.getGroups();
						if (null != groupsLDAP && groupsLDAP.length > 0) {
							for (int i = 0; i < groupsLDAP.length; i++) {
								if ("de_portal".equalsIgnoreCase(groupsLDAP[i])) {
									UserDetails userDetailsDE = CreateUserDAO
											.getDEUserDetails(userId);
									if (null != userDetailsDE) {
										this.deUserRole = userDetailsDE
												.getUserRole();
									}
									break;
								}
							}
						}

					}
				} else if (currentTab.indexOf("Data") > -1) {
					// UserDetails deUserDetails = CreateUserDAO
					// .getDEUserDetails(userId);
					if (null != deUserDetails) {
						this.userId = deUserDetails.getUserId();
						this.userNameDE = deUserDetails.getFirstName();
						this.userAddressDE = deUserDetails.getUserAddress();
						this.userRoleDE = deUserDetails.getUserRole();
						this.defaultValue = DJBConstants.DEFAULT_PASSWORD;
						this.isActiveUserDE = deUserDetails.getIsActive();
					}
				}
				AppLog.end();
				return "success";
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
	 * @return the checkBtnFP
	 */
	public String getCheckBtnFP() {
		return checkBtnFP;
	}

	/**
	 * @return the confirmPasswordDE
	 */
	public String getConfirmPasswordDE() {
		return confirmPasswordDE;
	}

	/**
	 * @return the confirmPasswordLDAP
	 */
	public String getConfirmPasswordLDAP() {
		return confirmPasswordLDAP;
	}

	/**
	 * @return the currentTab
	 */
	public String getCurrentTab() {
		return currentTab;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @return the deUserRole
	 */
	public String getDeUserRole() {
		return deUserRole;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @return the emailLDAP
	 */
	public String getEmailLDAP() {
		return emailLDAP;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the firstNameLDAP
	 */
	public String getFirstNameLDAP() {
		return firstNameLDAP;
	}

	/**
	 * @return the fIRTextData
	 */
	public String getFIRTextData() {
		return FIRTextData;
	}

	/**
	 * @return the groupsLDAP
	 */
	public String[] getGroupsLDAP() {
		return groupsLDAP;
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
	 * @return the isActiveUser
	 */
	public String getIsActiveUser() {
		return isActiveUser;
	}

	/**
	 * @return the isActiveUserDE
	 */
	public String getIsActiveUserDE() {
		return isActiveUserDE;
	}

	/**
	 * @return the isActiveUserLDAP
	 */
	public String getIsActiveUserLDAP() {
		return isActiveUserLDAP;
	}

	/**
	 * @return the isLockedUser
	 */
	public String getIsLockedUser() {
		return isLockedUser;
	}

	/**
	 * @return the isLockedUserLDAP
	 */
	public String getIsLockedUserLDAP() {
		return isLockedUserLDAP;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the lastNameLDAP
	 */
	public String getLastNameLDAP() {
		return lastNameLDAP;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @return the mobileNoLDAP
	 */
	public String getMobileNoLDAP() {
		return mobileNoLDAP;
	}

	/**
	 * @return the pageMode
	 */
	public String getPageMode() {
		return pageMode;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the passwordDE
	 */
	public String getPasswordDE() {
		return passwordDE;
	}

	/**
	 * @return the passwordLDAP
	 */
	public String getPasswordLDAP() {
		return passwordLDAP;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the reSubmit
	 */
	public String getReSubmit() {
		return reSubmit;
	}

	/**
	 * @return the selectedGroupsLDAP
	 */
	public String getSelectedGroupsLDAP() {
		return selectedGroupsLDAP;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @return the userAddressDE
	 */
	public String getUserAddressDE() {
		return userAddressDE;
	}

	/**
	 * @return the userAddressLDAP
	 */
	public String getUserAddressLDAP() {
		return userAddressLDAP;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the userIdDE
	 */
	public String getUserIdDE() {
		return userIdDE;
	}

	/**
	 * @return the userList
	 */
	public List<UserDetails> getUserList() {
		return userList;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the userNameDE
	 */
	public String getUserNameDE() {
		return userNameDE;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @return the userRoleDE
	 */
	public String getUserRoleDE() {
		return userRoleDE;
	}

	/**
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * @return the zroLocationDE
	 */
	public String getZroLocationDE() {
		return zroLocationDE;
	}

	/**
	 * @return the zroLocationLDAP
	 */
	public String getZroLocationLDAP() {
		return zroLocationLDAP;
	}

	/**
	 * @return the zroLocationUpdateLDAP
	 */
	public String getZroLocationUpdateLDAP() {
		return zroLocationUpdateLDAP;
	}

	/**
	 * @return the zroLocationUpdateListDE
	 */
	public HashMap<String, String> getZroLocationUpdateListDE() {
		return zroLocationUpdateListDE;
	}

	/**
	 * <p>
	 * Populate default values required for Create User Screen.
	 * </p>
	 */
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "ADMIN");
			if (null == session.get("roleListMap")) {
				session.put("roleListMap", RoleScreenMapDAO.getRoleList());
			}
			List<LDAPUserGroupList> ldapUserGroupListOfList = LDAPManager
					.getAllGroups();
			List<LDAPUserGroup> ldapUserGroups = null;
			LDAPUserGroupList ldapUserGroupList = null;
			if (null != ldapUserGroupListOfList
					&& ldapUserGroupListOfList.size() > 0) {
				for (int i = 0; i < ldapUserGroupListOfList.size(); i++) {
					ldapUserGroupList = ldapUserGroupListOfList.get(i);
					if (null != ldapUserGroupList) {
						String objectClass = ldapUserGroupList.getObjectClass();
						ldapUserGroups = ldapUserGroupList.getGroupList();
						session.put(objectClass, ldapUserGroups);
					}
				}
			}
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac
			 * DJB-45498
			 */
			HashMap<String, String> zroLocationListRightUpdateDE = new HashMap<String, String>();
			if (null != zroLocationDE && zroLocationDE.trim().length() > 0) {
				String[] zroList = zroLocationDE.split(",");
				HashMap<String, String> zroLocationListMapDE = new HashMap<String, String>();
				zroLocationListMapDE = (HashMap<String, String>) session
						.get("zroLocCharValListMap");
				for (int i = 0; i < zroList.length; i++) {
					zroLocationListRightUpdateDE.put(zroList[i],
							zroLocationListMapDE.get(zroList[i]));
				}
			}
			AppLog.info("length " + zroLocationListRightUpdateDE);
			this.zroLocationUpdateListDE = zroLocationListRightUpdateDE;
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac
			 * DJB-45498
			 */
		} catch (Exception e) {
			AppLog.error(e);
			e.printStackTrace();
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * @param checkBtnFP
	 *            the checkBtnFP to set
	 */
	public void setCheckBtnFP(String checkBtnFP) {
		this.checkBtnFP = checkBtnFP;
	}

	/**
	 * @param confirmPasswordDE
	 *            the confirmPasswordDE to set
	 */
	public void setConfirmPasswordDE(String confirmPasswordDE) {
		this.confirmPasswordDE = confirmPasswordDE;
	}

	/**
	 * @param confirmPasswordLDAP
	 *            the confirmPasswordLDAP to set
	 */
	public void setConfirmPasswordLDAP(String confirmPasswordLDAP) {
		this.confirmPasswordLDAP = confirmPasswordLDAP;
	}

	/**
	 * @param currentTab
	 *            the currentTab to set
	 */
	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @param deUserRole
	 *            the deUserRole to set
	 */
	public void setDeUserRole(String deUserRole) {
		this.deUserRole = deUserRole;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @param emailLDAP
	 *            the emailLDAP to set
	 */
	public void setEmailLDAP(String emailLDAP) {
		this.emailLDAP = emailLDAP;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param firstNameLDAP
	 *            the firstNameLDAP to set
	 */
	public void setFirstNameLDAP(String firstNameLDAP) {
		this.firstNameLDAP = firstNameLDAP;
	}

	/**
	 * @param fIRTextData
	 *            the fIRTextData to set
	 */
	public void setFIRTextData(String fIRTextData) {
		FIRTextData = fIRTextData;
	}

	/**
	 * @param groupsLDAP
	 *            the groupsLDAP to set
	 */
	public void setGroupsLDAP(String[] groupsLDAP) {
		this.groupsLDAP = groupsLDAP;
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
	 * @param isActiveUser
	 *            the isActiveUser to set
	 */
	public void setIsActiveUser(String isActiveUser) {
		this.isActiveUser = isActiveUser;
	}

	/**
	 * @param isActiveUserDE
	 *            the isActiveUserDE to set
	 */
	public void setIsActiveUserDE(String isActiveUserDE) {
		this.isActiveUserDE = isActiveUserDE;
	}

	/**
	 * @param isActiveUserLDAP
	 *            the isActiveUserLDAP to set
	 */
	public void setIsActiveUserLDAP(String isActiveUserLDAP) {
		this.isActiveUserLDAP = isActiveUserLDAP;
	}

	/**
	 * @param isLockedUser
	 *            the isLockedUser to set
	 */
	public void setIsLockedUser(String isLockedUser) {
		this.isLockedUser = isLockedUser;
	}

	/**
	 * @param isLockedUserLDAP
	 *            the isLockedUserLDAP to set
	 */
	public void setIsLockedUserLDAP(String isLockedUserLDAP) {
		this.isLockedUserLDAP = isLockedUserLDAP;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param lastNameLDAP
	 *            the lastNameLDAP to set
	 */
	public void setLastNameLDAP(String lastNameLDAP) {
		this.lastNameLDAP = lastNameLDAP;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @param mobileNoLDAP
	 *            the mobileNoLDAP to set
	 */
	public void setMobileNoLDAP(String mobileNoLDAP) {
		this.mobileNoLDAP = mobileNoLDAP;
	}

	/**
	 * @param pageMode
	 *            the pageMode to set
	 */
	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param passwordDE
	 *            the passwordDE to set
	 */
	public void setPasswordDE(String passwordDE) {
		this.passwordDE = passwordDE;
	}

	/**
	 * @param passwordLDAP
	 *            the passwordLDAP to set
	 */
	public void setPasswordLDAP(String passwordLDAP) {
		this.passwordLDAP = passwordLDAP;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param reSubmit
	 *            the reSubmit to set
	 */
	public void setReSubmit(String reSubmit) {
		this.reSubmit = reSubmit;
	}

	/**
	 * @param selectedGroupsLDAP
	 *            the selectedGroupsLDAP to set
	 */
	public void setSelectedGroupsLDAP(String selectedGroupsLDAP) {
		this.selectedGroupsLDAP = selectedGroupsLDAP;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param userAddress
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @param userAddressDE
	 *            the userAddressDE to set
	 */
	public void setUserAddressDE(String userAddressDE) {
		this.userAddressDE = userAddressDE;
	}

	/**
	 * @param userAddressLDAP
	 *            the userAddressLDAP to set
	 */
	public void setUserAddressLDAP(String userAddressLDAP) {
		this.userAddressLDAP = userAddressLDAP;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param userIdDE
	 *            the userIdDE to set
	 */
	public void setUserIdDE(String userIdDE) {
		this.userIdDE = userIdDE;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<UserDetails> userList) {
		this.userList = userList;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param userNameDE
	 *            the userNameDE to set
	 */
	public void setUserNameDE(String userNameDE) {
		this.userNameDE = userNameDE;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @param userRoleDE
	 *            the userRoleDE to set
	 */
	public void setUserRoleDE(String userRoleDE) {
		this.userRoleDE = userRoleDE;
	}

	/**
	 * @param zroLocation
	 *            the zroLocation to set
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}

	/**
	 * @param zroLocationDE
	 *            the zroLocationDE to set
	 */
	public void setZroLocationDE(String zroLocationDE) {
		this.zroLocationDE = zroLocationDE;
	}

	/**
	 * @param zroLocationLDAP
	 *            the zroLocationLDAP to set
	 */
	public void setZroLocationLDAP(String zroLocationLDAP) {
		this.zroLocationLDAP = zroLocationLDAP;
	}

	/**
	 * @param zroLocationUpdateLDAP
	 *            the zroLocationUpdateLDAP to set
	 */
	public void setZroLocationUpdateLDAP(String zroLocationUpdateLDAP) {
		this.zroLocationUpdateLDAP = zroLocationUpdateLDAP;
	}

	/**
	 * @param zroLocationUpdateListDE
	 *            the zroLocationUpdateListDE to set
	 */
	public void setZroLocationUpdateListDE(
			HashMap<String, String> zroLocationUpdateListDE) {
		this.zroLocationUpdateListDE = zroLocationUpdateListDE;
	}

	/**
	 * <p>
	 * This method is used to Unlock User For All Application.
	 * </p>
	 * 
	 * @return
	 */
	private String unlockUserForAllApplication() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			if (null == currentTab || currentTab.indexOf("All") > -1) {
				String returnMessage = LDAPManager.unlockUser(userId);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User is Successfuly Unlocked.</b></font>"));
					AppLog.info("User Id ::" + userId
							+ "::Successfuly Unlocked BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>ERROR:" + returnMessage
									+ "</b></font>"));
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

	/**
	 * <p>
	 * This method is used to Update User For All Application. Users are created in LDAP.
	 * </p>
	 * 
	 * @return
	 */
	public String updateUserForAllApplication() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			if (null == currentTab || currentTab.indexOf("All") > -1) {

				UserDetails ldapUserDetails = new UserDetails();
				ldapUserDetails.setUserId(userId);
				ldapUserDetails.setPassword(password);
				ldapUserDetails.setFirstName(firstName);
				ldapUserDetails.setLastName(lastName);
				ldapUserDetails.setUserAddress(userAddress);
				ldapUserDetails.setEmailId(emailId);
				ldapUserDetails.setMobileNo(mobileNo);
				/*
				 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-4549
				 */
				ldapUserDetails.setZroLocation(zroLocation);
				AppLog.info("Inside Update action zroLocations : "
						+ ldapUserDetails.getZroLocation());
				/*
				 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-4549
				 */
				String[] groups = selectedGroupsLDAP.split(",");
				ldapUserDetails.setGroups(groups);
				String returnMessage = LDAPManager.updateUser(ldapUserDetails);
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Details was Updated Successfuly.</b></font>"));
					AppLog.info("User Details for ::" + userId
							+ "::Updated Successfuly BY::" + userIdSession);
				} else if ("USER DETAILS UPDATE SUCCESS"
						.equalsIgnoreCase(returnMessage)) {
					inputStream = (new StringBufferInputStream(
							"<font color='orange'><b>Note: User details Updated Successfuly, But there was problem while Updating Access. Please retry.</b></font>"));
					AppLog.info("User Details for ::" + userId
							+ "::Updated Successfuly BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='Red'><b>" + returnMessage
									+ "</b></font>"));
				}
				/*
				 * If user details updated successfully and selected DE
				 * Application for access user need to be updated in DE also for
				 * screen access. Now Updating User details in DE Application
				 * also.
				 */
				if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
					/*
					 * If has access to DE Application Update or Create the user
					 * for DE also else disable the user from DE application.
					 */
					if (null != selectedGroupsLDAP
							&& selectedGroupsLDAP.indexOf("de_portal") > -1) {
						this.userRole = this.deUserRole;
					} else {
						this.userRole = "0";
					}
					this.userName = this.firstName + " " + this.lastName;

					this.password = "";
					/*
					 * If the user already exists in DE Application then Update
					 * else Create the user for DE also.
					 */
					if (CreateUserDAO.validateUserId(userId)) {
						this.isActiveUserDE = "Y";
						AppLog.end();
						return updateUserForDEApplication();
					} else {
						AppLog.end();
						return createUserForDEApplication();
					}
					// }
					// else {
					// AppLog.end();
					// return disableUserForDEApplication("ALL");
					// }
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

	/**
	 * *
	 * <p>
	 * This method is used to Update User For Data Entry Application. Users are created in Database.
	 * </p>
	 * 
	 * @return
	 */
	public String updateUserForDEApplication() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			HashMap<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("userId", userId);
			inputMap.put("userRole", userRole);
			inputMap.put("updatedBy", userIdSession);
			inputMap.put("password", password);
			inputMap.put("userName", userName);
			inputMap.put("userAddress", userAddress);
			inputMap.put("isActive", isActiveUserDE);
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			inputMap.put("zroLocation", zroLocation);
			AppLog.info("Inside Update Action zroLocations : " + zroLocation);
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			inputMap.put("FIRTextData", FIRTextData);
			boolean isSuccess = CreateUserDAO.updateUser(inputMap);
			if (null != currentTab && currentTab.indexOf("Data") > -1) {
				if (isSuccess) {
					inputStream = (new StringBufferInputStream(
							"<font color='green'><b>User Id Successfully Updated.</b></font>"));
					AppLog.info("User Id ::" + userId
							+ "::Successfully Updated BY::" + userIdSession);
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>User Id Could not Be Updated. Please Retry.</b></font>"));
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

}
