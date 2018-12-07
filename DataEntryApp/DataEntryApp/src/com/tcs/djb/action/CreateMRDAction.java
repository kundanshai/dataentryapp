/************************************************************************************************************
 * @(#) CreateMRDAction.java   21-05-2013
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
import com.tcs.djb.dao.CreateMRDDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 *<p>
 * Action class for Create New MRD.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 21-05-2013
 * @history 14-DEC-2015 Aniket Chatterjee(Tata Consultancy Services) modified
 *          method {@link #createNewMRD} Aniket Chatterjee modified this to fix
 *          the issue while creating new mrkey for a ZRO location that doesn't
 *          contain any active meter reader named 'DJB' Or Contains More Than
 *          One Active Meter Reader Named 'DJB'. Aniket Chatterjee modified this
 *          as per JTrac DJB-4185 & DJB-4280.
 * @history 07-JAN-2016 Arnab Nandy(Tata Consultancy Services) modified
 *          method {@link #createNewMRD} Arnab Nandy modified this to add
 *          the newColCat field. Arnab Nandy modified this
 *          as per JTrac DJB-4304.
 */
@SuppressWarnings("deprecation")
public class CreateMRDAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "25";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Hidden action set.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;
	/**
	 * To check if call is for a pop up.
	 */
	private String isPopUp;
	/**
	 * Area Code.
	 */
	private String newAreaCode;

	/**
	 * Area Description.
	 */
	private String newAreaDesc;
	
	/**
	 * MRD Type.
	 */
	private String newMRDType;

	/**
	 * MRKEY is the combination of Zone, MR NO and Area.
	 */
	private String newMRKey;
	/**
	 * MR No.
	 */
	private String newMRNo;
	/**
	 * Colony Category.
	 */
	private String newColCat;
	/**
	 * Zone No.
	 */
	private String newZone;

	/**
	 * ZRO code.
	 */
	private String newZROCode;

	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	/**
	 * For all ajax call in Data Entry Search screen
	 * 
	 * @return string
	 */
	public String ajaxMethod() {
		AppLog.begin();
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
			// System.out.println("hidAction::" + hidAction + "::newZone::"
			// + newZone + "::newMRNo::" + newMRNo + "::newAreaCode::"
			// + newAreaCode + "::newAreaDesc::" + newAreaDesc
			// + "::newZROCode::" + newZROCode);
			if ("validateCreateNewMRD".equalsIgnoreCase(hidAction)) {
				inputStream = new StringBufferInputStream(
						validateCreateNewMRD());
			}
			if ("createNewMRD".equalsIgnoreCase(hidAction)) {
				inputStream = new StringBufferInputStream(createNewMRD());
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * Create a new MRD with the new combination of Zone, MR No and Area Code.
	 * </p>
	 * 
	 * @return responsString
	 */
	private String createNewMRD() {
		AppLog.begin();
		String responsString = "<font color='red' size='2'><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			// Change Started By Aniket Chatterjee.For More Please Refer To
			// History.
			int getRow = GetterDAO.getUserExistenceCount(newZROCode);
			AppLog.info("No.Of 'DJB' named meter reader:: " + getRow);
			if (getRow > 0) {
				if (getRow == 1) {
					int procReturnCode = CreateMRDDAO.createNewMRD(newZone,
							newMRNo, newAreaCode, newAreaDesc, newZROCode,
							newMRDType, newColCat, userId);
					if (procReturnCode > 0) {
						Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
								.getMRKEYByZoneMRArea(newZone, newMRNo,
										newAreaCode);
						if (null != returnMap) {
							String mrKey = returnMap.get("mrKey");
							if (null != mrKey) {
								responsString = mrKey;
								session.remove("MRKEYListMap");
								session.put("MRKEYListMap", GetterDAO
										.getMRKEYListMap());
							} else {
								responsString = "<font color='red' size='2'><span><b>Sorry! There was problem while Creating new MRD, Please retry.</b></font>";
							}
						}
					}
				} else {
					responsString = "<font color='red' size='2'><span><b>ERROR:MORETHANONEDJBMTRRDR.</b></font>";
				}
			} else {
				responsString = "<font color='red' size='2'><span><b>ERROR:NODJBMTRRDR.</b></font>";
			}// //Change Ended By Aniket Chatterjee.For More Please Refer To
				// History.
		} catch (Exception e) {
			responsString = "<font color='red' size='2'><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);

		}
		AppLog.end();
		return responsString;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "success";
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
			/** Load Default Values. */
			loadDefault();
			// System.out.println("selectedZROCode::" + selectedZROCode);
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
			}
		} catch (ClassCastException e) {
			addActionError(getText("error.login.expired"));
			AppLog.end();
			return "login";
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			addActionError(getText("error.login.expired"));
			return "login";
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
	 * @return the isPopUp
	 */
	public String getIsPopUp() {
		return isPopUp;
	}

	/**
	 * @return the newAreaCode
	 */
	public String getNewAreaCode() {
		return newAreaCode;
	}

	/**
	 * @return the newAreaDesc
	 */
	public String getNewAreaDesc() {
		return newAreaDesc;
	}

	/**
	 * @return the newMRDType
	 */
	public String getNewMRDType() {
		return newMRDType;
	}

	/**
	 * @return the newMRKey
	 */
	public String getNewMRKey() {
		return newMRKey;
	}

	/**
	 * @return the newMRNo
	 */
	public String getNewMRNo() {
		return newMRNo;
	}

	/**
	 * @return the newZone
	 */
	public String getNewZone() {
		return newZone;
	}

	/**
	 * @return the newZROCode
	 */
	public String getNewZROCode() {
		return newZROCode;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * <p>
	 * Populate default values required for Demand Transfer Screen.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "MASTER");
			if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			// if (null == session.get("ZROListMap")
			// || ((HashMap<String, String>) session.get("ZROListMap"))
			// .isEmpty()) {
			Map<String, String> zroListMap = GetterDAO.getAllZRO();
			session.put("ZROListMap", zroListMap);
			// }
			if (null == session.get("MRDTypeListMap")
					|| ((HashMap<String, String>) session.get("MRDTypeListMap"))
							.isEmpty()) {
				Map<String, String> mrdTypeListMap = GetterDAO.getMRDTypeMap();
				session.put("MRDTypeListMap", mrdTypeListMap);
			}

		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
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
	 * @param isPopUp
	 *            the isPopUp to set
	 */
	public void setIsPopUp(String isPopUp) {
		this.isPopUp = isPopUp;
	}

	/**
	 * @param newAreaCode
	 *            the newAreaCode to set
	 */
	public void setNewAreaCode(String newAreaCode) {
		this.newAreaCode = newAreaCode;
	}

	/**
	 * @param newAreaDesc
	 *            the newAreaDesc to set
	 */
	public void setNewAreaDesc(String newAreaDesc) {
		this.newAreaDesc = newAreaDesc;
	}

	/**
	 * @param newMRDType
	 *            the newMRDType to set
	 */
	public void setNewMRDType(String newMRDType) {
		this.newMRDType = newMRDType;
	}

	/**
	 * @param newMRKey
	 *            the newMRKey to set
	 */
	public void setNewMRKey(String newMRKey) {
		this.newMRKey = newMRKey;
	}

	/**
	 * @param newMRNo
	 *            the newMRNo to set
	 */
	public void setNewMRNo(String newMRNo) {
		this.newMRNo = newMRNo;
	}

	/**
	 * @param newZone
	 *            the newZone to set
	 */
	public void setNewZone(String newZone) {
		this.newZone = newZone;
	}

	/**
	 * @param newZROCode
	 *            the newZROCode to set
	 */
	public void setNewZROCode(String newZROCode) {
		this.newZROCode = newZROCode;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * <p>
	 * This method is to validate MRD while New MRD creation.
	 * </p>
	 * 
	 * @return responsString
	 */
	private String validateCreateNewMRD() {
		String responsString = "VALID";
		try {
			Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
					.getMRKEYByZoneMRArea(newZone, newMRNo, newAreaCode);
			if (null != returnMap) {
				String mrKey = returnMap.get("mrKey");
				if (null != mrKey) {
					responsString = "<font color='red' size='2'><b>An MRD with MRKEY "
							+ mrKey
							+ " already exist in the System for the Zone, MR No and Area Code. Please Choose a Different Combination.</b></font>";
				}
			}
		} catch (Exception e) {
			responsString = "<font color='red' size='2'><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);

		}
		AppLog.end();
		return responsString;
	}

	/**
	 * @return the newColCat
	 */
	public String getNewColCat() {
		return newColCat;
	}

	/**
	 * @param newColCat the newColCat to set
	 */
	public void setNewColCat(String newColCat) {
		this.newColCat = newColCat;
	}

}
