/************************************************************************************************************
 * @(#) LoginAction.java   03 Sep 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.LOV;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.LoginDAO;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.dao.RoleScreenMapDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;

/**
 * <p>
 * Action class for Login Screen
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 03-09-2012 Matiur put mrdReadTypeLookup to session for validation of
 *          read type.
 * @history 18-04-2013 Matiur Rahman Added method populateLOV() for populating
 *          LOV to application context.
 * @history 21-08-2013 Matiur Rahman Added method password change feature called
 *          from Employee Portal.
 * @history 03-01-2014 Rajib Hazarika Added code to retrieve vendor Name user
 *          Table
 * @history 10-01-2014 Rajib Hazarika changed code to keep authCookie in session
 *          on login for every user
 * @history 24-09-2015 Aniket Chatterjee changed code to authenticate the page
 *          twice.
 * @history ON 27-JAN-2016 Added by Rajib to populate DE Access ZRO_CD for
 *          logged in USER as per JTrac DJB-4313
 * @history ON 15-FEB-2016 Added by Rajib to populate drop down values as per
 *          JTrac DJB-4313
 * @history ON 02-JUNE-2016 Added by Madhuri to get tagged mrkey of AMR user
 *          from session as per JTrac DJB-4464 & Open Project ids -1203
 */
public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4893469569455479948L;
	private String _x;
	private String callFrom;
	private LOV lov;
	private String password;
	private String resetUserId;
	private String username;
	private String userRole;
	private String userType;
	

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "error";
		try {
			/** Populating LOV. */
			populateLOV();
			// System.out.println("password::" + this.password);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String lastLoginType = this.userRole;
			if (null != this.username && !"".equals(this.username)
					&& null != this.password && !"".equals(this.password)) {
				HashMap<String, String> inputMap = new HashMap<String, String>();
				HashMap<String, String> returnMap = null;
				inputMap.put("userId", this.username);
				inputMap.put("password", this.password);
				//inputMap.put("userRole", this.userRole);
				inputMap.put("userRole", "1");
				/*
				 * inputMap.put("userName", "Test");
				 * inputMap.put("userAddress","USER_ADDRESS");
				 * inputMap.put("lastLoginTime","USER_LOGIN_TIME"); returnMap =
				 * inputMap;
				 */
				if ("confirm".equals((String) session.get("callType"))) {
//					System.out.println("userRole>>"
//							+ (String) session.get("userType"));
					inputMap.put("userRole", (String) session.get("userType"));
				}
				returnMap = (HashMap<String, String>) LoginDAO
						.validateLogin(inputMap);
				if (null != returnMap) {
					String status = (String) returnMap.get("status");
					// Changed by rajib as per Jtrac DJB-2024
					String vendorName = null;
					if (null != returnMap.get("vendorName")) {
						vendorName = (String) returnMap.get("vendorName");
						// System.out.println("Vendor Name:"+vendorName+"\n");
					}
					// Change complete by rajib as per Jtrac DJB-2024
					String callType = (String) session.get("callType");
					if (null != status && !"Success".equalsIgnoreCase(status)) {
						addActionError(status);
						String statusCode = (String) returnMap
								.get("statusCode");
						if ("PWDEXPIRED".equalsIgnoreCase(statusCode)
								|| "PWDMUSTCHANGE".equalsIgnoreCase(statusCode)) {
							result = "changePassword";
							this.userType = "EMPLOYEE";
							this.resetUserId = this.username;
							session.remove("userId");
							session.put("userId", this.username);
							session.put("passwordChangeType", "SELF");
							session.put("userType", "EMPLOYEE");

						} else {
							result = "error";
						}
						AppLog.end();
						return result;
					}
//					System.out.println("callType>>" + callType + "status>>"
//							+ status);
					if (null != status && "Success".equalsIgnoreCase(status)
							&& "confirm".equals(callType)) {
						String loginCallFrom = (String) session
								.get("LOGIN_CALL_FROM");
						System.out.println("LOGIN_CALL_FROM" + loginCallFrom);
						result = loginCallFrom;
						AppLog.end();
						return result;
					}
					/*
					 * Start:21-08-2013 Matiur Rahman Added method password
					 * change feature called from Employee Portal.
					 */
					if (null != status && "Success".equalsIgnoreCase(status)
							&& "SPC".equalsIgnoreCase(callType)) {
						result = "changePassword";
						this.userType = "EMPLOYEE";
						this.resetUserId = this.username;
						session.remove("userId");
						session.put("userId", this.username);
						session.put("passwordChangeType", "SELF");
						AppLog.end();
						return result;
					}
					/*
					 * End:21-08-2013 Matiur Rahman Added method password change
					 * feature called from Employee Portal.
					 */
					String userId = (String) returnMap.get("userId");
					String userName = (String) returnMap.get("userName");
					String userRoleId = (String) returnMap.get("userRoleId");
					String userRoleDesc = (String) returnMap
							.get("userRoleDesc");
					String userAddress = (String) returnMap.get("userAddress");
					String lastLoginTime = (String) returnMap
							.get("lastLoginTime");
					// System.out.println("userName::" + userName
					// + "::userRoleId::" + userRoleId + "::userRoleDesc:"
					// + userRoleDesc);
					AppLog.info("Logged In By " + userName + "(" + userId + ")"
							+ "  with Role " + userRoleDesc + "(" + userRoleId
							+ ")");
					if (null != userName && !"".equals(userName)) {
						invalidateSession();
						session.put("sessionId", ServletActionContext
								.getRequest().getSession().getId());
						// Commented out by Rajib as per Jtrac DJB-2024
						// if ("Employee".equalsIgnoreCase(this.userRole)) {
						String authCookie = UtilityForAll.generateAuthCookie(
								userId, password);
						session.put("CCB_CRED", authCookie);
						// }
						// System.out.println("AuthCookie:"+authCookie);
						session.put("userId", userId);
						session.put("userName", userName);
						session.put("userType", this.userRole);
						session.put("userRoleId", userRoleId);
						session.put("userRoleDesc", userRoleDesc);
						session.put("userAddress", userAddress);
						session.put("lastLoginTime", lastLoginTime);
						session.put("lastLoginTime", lastLoginTime);
						/** Start: Populate role Screen Mapping List */
						session.put("roleScreenMap", RoleScreenMapDAO
								.getRoleScreenMappingList(userRoleId));
						session.put("userScreenMap", RoleScreenMapDAO
								.getUserScreenMappingList(this.username));
					
						/** End: Populate role Screen Mapping List */
						
						/** Start Madhuri on 02-Jun-2016: Get Tagged mrkeys of emp id  */
						
						session.put("taggedMrkey", GetterDAO.getTaggedMrkeysEmp(userId));
						AppLog.info("Emp Id of loggedIn user "+userId);
						
						/** End Madhuri on 02-Jun-2016: Get Tagged mrkeys of emp id  */
						
						/*
						 * Populate company name for user Change by Rajib as per
						 * Jtrac- DJB-2024
						 */
						session.put("vendorName", vendorName);
						// System.out.println("Session vendorName:"+
						// session.get("vendorName"));
						// Chnage by Rajib for vendorName complete
						//START:ON 27-JAN-2016 Added by Rajib to populate DE Access ZRO_CD for logged in USER as per JTrac DJB-4313
						session.put("deZroCd", NewConnFileEntryDAO.getDeZroAccessForUserId(userId));
						//END:ON 27-JAN-2016 Added by Rajib to populate DE Access ZRO_CD for logged in USER as per JTrac DJB-4313
						session
								.put("TodaysDate", UtilityForAll
										.getTodaysDate());
						if (null != lov) {
							session.put("ZoneListMap", lov.getZoneListMap());
							session.put("MRNoListMap",
									new HashMap<String, String>());
							session.put("AreaListMap",
									new HashMap<String, String>());
							session.put("zoneList", lov.getZoneList());
							session.put("mrNoList", lov.getMrNoList());
							session.put("areaList", lov.getAreaList());
							session.put("areaDetails", lov.getAreaDetails());
							session.put("billWindowList", lov
									.getBillWindowList());
							session.put("meterReadStatusList", lov
									.getMeterReadStatusList());
							session.put("mrdReadTypeLookup", lov
									.getMrdReadTypeLookup());
							session.put("waterConnectionSizeList", lov
									.getWaterConnectionSizeList());
							session.put("waterConnectionUseList", lov
									.getWaterConnectionUseList());
							session.put("saTypeList", lov.getSaTypeList());
							session.put("DJBMeterTypeList", lov
									.getDJBMeterTypeList());
							session.put("manufacturerDetailsList", lov
									.getManufacturerDetailsList());
							session.put("modelDetailsList", lov
									.getModelDetailsList());
							session.put("LOGIN_TYPE", lastLoginType);
							session.put("LOGIN_CALL_FROM", callFrom);
							// START: Added by Rajib as per Jtrac DJB-4313 on
							// 13-FEB-2016
							session.put("yesNoCharValListMap", lov
									.getYesNoCharValListMap());
							session.put("unAuthUsgCharValListMap", lov
									.getUnAuthUsgCharValListMap());
							session.put("prefModeOfPmntCharValListMap", lov
									.getPrefModeOfPmntCharValListMap());
							session.put("watFeasCharValListMap", lov
									.getWatFeasCharValListMap());
							session.put("sewFeasCharValListMap", lov
									.getSewFeasCharValListMap());
							session.put("isDocVerifiedCharValListMap", lov
									.getIsDocVerifiedCharValListMap());
							session.put("djbEmpCharValListMap", lov
									.getDjbEmpCharValListMap());
							session.put("wconSizeCharValListMap", lov
									.getWconSizeCharValListMap());
							session.put("occupSecurityCharValListMap", lov
									.getOccupSecurityCharValListMap());
							session.put("sewDevChrgApplCharValListMap", lov
									.getSewDevChrgApplCharValListMap());
							session.put("sewDevChrgColCharValListMap", lov
									.getSewDevChrgColCharValListMap());
							session.put("watDevChrgColCharValListMap", lov
									.getWatDevChrgColCharValListMap());
							session.put("watDevChrgApplCharValListMap", lov
									.getWatDevChrgApplCharValListMap());
							session.put("zroLocCharValListMap", lov
									.getZroLocCharValListMap());
							// END: Added by Rajib as per Jtrac DJB-4313 on
							// 13-FEB-2016
							// Added by Madhuri as per JTrac DJB-4464 on 14-JUN-2016
							session.put("openBillWindowList", lov.getOpenBillWindowList());
							
							AppLog.end();
							return "success";
						} else {
							session.put("LOGIN_TYPE", lastLoginType);
							session.put("LOGIN_CALL_FROM", callFrom);
							AppLog.end();
							return "populatelov";
						}
					} else {
						session.put("LOGIN_TYPE", lastLoginType);
						session.put("LOGIN_CALL_FROM", callFrom);
						addActionError(getText("error.login"));
						result = "error";
					}
				}
			} else if ("rainWater".equals(callFrom)) {
				// session.put("LOGIN_TYPE", lastLoginType);
//				System.out.println("rainWater>>callFrom>>" + callFrom
//						+ ">>callType>>" + _x);
				session.put("LOGIN_CALL_FROM", callFrom);
				session.put("callType", _x);
				addActionError("Please Confirm Your Credentials");
				result = "error";
			} else {
				session.put("LOGIN_TYPE", lastLoginType);
				session.put("LOGIN_CALL_FROM", callFrom);
				session.put("callType", _x);
				addActionError(getText("error.login.mandatory"));
				result = "error";
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the _x
	 */
	public String get_x() {
		return _x;
	}

	/**
	 * @return the callFrom
	 */
	public String getCallFrom() {
		return callFrom;
	}

	/**
	 * @return the lov
	 */
	public LOV getLov() {
		return lov;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the resetUserId
	 */
	public String getResetUserId() {
		return resetUserId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	
	

	/**
	 * <p>
	 * Invalidate Current session.
	 * </p>
	 */
	@SuppressWarnings( { "unchecked" })
	public void invalidateSession() {
		Map session = ActionContext.getContext().getSession();
		if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
			try {
				((org.apache.struts2.dispatcher.SessionMap) session)
						.invalidate();
			} catch (IllegalStateException e) {

			}
		}
	}

	/**
	 * <p>
	 * Populate LOV.
	 * </p>
	 */
	public void populateLOV() {
		try {
			Map<String, Object> applicationContext = ActionContext.getContext()
					.getApplication();
			// if (null != applicationContext
			// && null == applicationContext.get("LOV")) {
			// applicationContext.put("LOV", new LOV());
			// }
			this.lov = (LOV) applicationContext.get("LOV");
		} catch (Exception e) {
			AppLog.error(e);
		}
	}

	/**
	 * @param x
	 *            the _x to set
	 */
	public void set_x(String x) {
		_x = x;
	}

	/**
	 * @param callFrom
	 *            the callFrom to set
	 */
	public void setCallFrom(String callFrom) {
		this.callFrom = callFrom;
	}

	/**
	 * @param lov
	 *            the lov to set
	 */
	public void setLov(LOV lov) {
		this.lov = lov;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param resetUserId
	 *            the resetUserId to set
	 */
	public void setResetUserId(String resetUserId) {
		this.resetUserId = resetUserId;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param userRole
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
