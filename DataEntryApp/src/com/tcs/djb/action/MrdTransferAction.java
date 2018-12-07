/************************************************************************************************************
 * @(#) MrdTransferAction.java   26 Sep 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MrdTransferDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Mrd Transfer as per JTrac DJB-2200.
 * </p>
 * 
 * @author Madhuri Singh(Tata Consultancy Services)
 * @since 26-09-2016
 */
public class MrdTransferAction extends ActionSupport implements
		ServletResponseAware {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = DJBConstants.SCREEN_ID_MRD_TRANSFER;
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	/**
	 * Area Code.
	 */
	private String areaCode;
	/**
	 * Hidden action.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;
	/**
	 * MR NO.
	 */
	private String mrNoList;
	/**
	 * New Zone.
	 */
	private String newZone;
	/**
	 * New Zro Location.
	 */
	private String newZRO;
	/**
	 * old zone.
	 */
	private String oldZone;
	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	/**
	 * <p>
	 * For all ajax call in Data Entry Search screen
	 * </p>
	 * 
	 * @return string
	 */
	@SuppressWarnings("deprecation")
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
			if ("populateMRNOList".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(oldZone);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"mrNo\" id=\"mrNo\" class=\"selectbox-long\" onfocus=\"fnCheckZone();\" onchange=\"fnGetAreaList();\">");
				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(entry.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(entry.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("MRNOListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
			}
			if ("populateAreaList".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getAreaListMap(oldZone, mrNoList);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"areaCode\" id=\"areaCode\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo();\" >");

				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(entry.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(entry.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("AreaListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
			}
			if ("populateZroCode".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getZRO(newZone, null, null, null);
				AppLog.info("returnMap***" + returnMap);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"newZROCode\" id=\"newZROCode\" class=\"selectbox-long\">");

				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(entry.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(entry.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				// session.put("ZROListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
			}

			if ("getStatus".equalsIgnoreCase(hidAction)) {
				AppLog.info("areaCode****" + areaCode + "mrNoList***"
						+ mrNoList + "newZone***" + newZone);
				if (null == areaCode || "".equalsIgnoreCase(areaCode)
						|| null == mrNoList || "".equalsIgnoreCase(mrNoList)
						|| null == newZRO || "".equalsIgnoreCase(newZRO)) {
					inputStream = new StringBufferInputStream(
							DJBConstants.MRD_TRANSFER_MANDATORY_MSG);
					AppLog.end();
					return SUCCESS;
				}

				else {
					if (null != newZone && !"".equalsIgnoreCase(newZone)) {
						String zroCode = GetterDAO.getZroCodeByZoneMrArea(
								areaCode, mrNoList, newZone);
						AppLog.info("***ZRO Code for Transferred Mrkey is***"
								+ zroCode);
						AppLog.info("selected New zro location is **" + newZRO);
						if (null != zroCode
								&& (newZRO.equalsIgnoreCase(zroCode))) {
							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_SUCCESS + zroCode);
							AppLog.end();
							return SUCCESS;

						} else {
							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_INPROGRESS_MSG);
							AppLog.end();
							return SUCCESS;

						}
					} else {
						String zroCode = GetterDAO.getZroCodeByZoneMrArea(
								areaCode, mrNoList, oldZone);
						AppLog.info("***ZRO Code for Transferred Mrkey is***"
								+ zroCode);
						AppLog.info("Old zro location is **" + newZRO);
						if (null != zroCode
								&& (newZRO.equalsIgnoreCase(zroCode))) {
							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_SUCCESS + zroCode);
							AppLog.end();
							return SUCCESS;

						} else {
							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_INPROGRESS_MSG);
							AppLog.end();
							return SUCCESS;
						}
					}
				}

			}

			if ("mrdTransferProcExe".equalsIgnoreCase(hidAction)) {
				if (null != oldZone && !"".equalsIgnoreCase(oldZone)
						&& null != newZone && !"".equalsIgnoreCase(newZone)
						&& null != mrNoList && !"".equalsIgnoreCase(mrNoList)
						&& null != areaCode && !"".equalsIgnoreCase(areaCode)
						&& null != newZRO && !"".equalsIgnoreCase(newZRO)) {
					Map<String, String> returnMrkey = (HashMap<String, String>) GetterDAO
							.getMRKEYByZoneMRArea(oldZone, mrNoList, areaCode);
					AppLog.info("return mrkey ***" + returnMrkey);
					if (null != returnMrkey) {
						String mrkey = returnMrkey.get("mrKey");
						AppLog
								.info("Mrkey of selected Zone,MR No, Area No is **"
										+ mrkey);

						int pendingOrInprogressStatus = MrdTransferDAO
								.getCntOfInProgressForMrdTrnsfr(mrkey);
						AppLog
								.info("Count of Pending or in progress status for the selected mrkey "
										+ pendingOrInprogressStatus);
						if (pendingOrInprogressStatus <= 0) {
							inputStream = new StringBufferInputStream(
									MRDTransfer(mrkey));
						} else {

							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_INPROGRESS_MSG);
							AppLog.end();
							return SUCCESS;

						}
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing You Request.</b></font>");
						AppLog.end();
						return SUCCESS;
					}

				} else {
					inputStream = new StringBufferInputStream(
							DJBConstants.MRD_TRANSFER_MANDATORY_MSG);
					AppLog.end();
					return SUCCESS;

				}
			}
			if ("zroWiseMrdTransfer".equalsIgnoreCase(hidAction)) {
				if (null != oldZone && !"".equalsIgnoreCase(oldZone)
						&& (null == newZone || "".equalsIgnoreCase(newZone))
						&& null != mrNoList && !"".equalsIgnoreCase(mrNoList)
						&& null != areaCode && !"".equalsIgnoreCase(areaCode)
						&& null != newZRO && !"".equalsIgnoreCase(newZRO)) {
					Map<String, String> returnMrkey = (HashMap<String, String>) GetterDAO
							.getMRKEYByZoneMRArea(oldZone, mrNoList, areaCode);
					AppLog.info("return mrkey ***" + returnMrkey);
					if (null != returnMrkey) {
						String mrkey = returnMrkey.get("mrKey");
						AppLog
								.info("Mrkey of selected Zone,MR No, Area No is **"
										+ mrkey);

						int pendingOrInprogressStatus = MrdTransferDAO
								.getCntOfInProgressForMrdTrnsfr(mrkey);
						AppLog
								.info("Count of Pending or in progress status for the selected mrkey "
										+ pendingOrInprogressStatus);
						if (pendingOrInprogressStatus <= 0) {
							inputStream = new StringBufferInputStream(
									ZroWiseMRDTransfer(mrkey));
						} else {
							inputStream = new StringBufferInputStream(
									DJBConstants.MRD_TRANSFER_INPROGRESS_MSG);
							AppLog.end();
							return SUCCESS;

						}
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing You Request.</b></font>");
						AppLog.end();
						return SUCCESS;

					}

				} else {

					inputStream = new StringBufferInputStream(
							DJBConstants.MRD_TRANSFER_MANDATORY_MSG);
					AppLog.end();
					return SUCCESS;
				}

			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing You Request.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
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
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
			}
			if (null == session.get("MRNOListMap")
					|| ((HashMap<String, String>) session.get("MRNOListMap"))
							.isEmpty()) {
				if (null != oldZone && !"".equals(oldZone)) {
					session.put("MRNOListMap",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(oldZone));
				} else {
					session.put("MRNOListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("AreaListMap")
					|| ((HashMap<String, String>) session.get("AreaListMap"))
							.isEmpty()) {
				if (null != oldZone && !"".equals(oldZone) && null != mrNoList
						&& !"".equals(mrNoList)) {
					session.put("AreaListMap",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									oldZone, mrNoList));
				} else {
					session.put("AreaListMap", new HashMap<String, String>());
				}
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
	 * @param getAreaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param getHidAction
	 * 
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @param getInputStream
	 * 
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param getMrNoList
	 * 
	 */
	public String getMrNoList() {
		return mrNoList;
	}

	/**
	 * @param newZone
	 */
	public String getNewZone() {
		return newZone;
	}

	/**
	 * @param getNewZRO
	 * 
	 */
	public String getNewZRO() {
		return newZRO;
	}

	/**
	 * @param getOldZone
	 * 
	 */
	public String getOldZone() {
		return oldZone;
	}

	/**
	 * @param getResponse
	 * 
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * <p>
	 * This method is used to load ZRO list
	 * </p>
	 */
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			if (null == session.get("ZROListMap")) {
				Map<String, String> zroListMap = GetterDAO.getAllZRO();
				session.put("ZROListMap", zroListMap);
				AppLog.info("ZROListMap" + zroListMap);
			}

		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to check all the conditions and validate parameters
	 * to call procedure PROC_MRD_TRNSFR
	 * </p>
	 * 
	 * @return responsString
	 */
	private String MRDTransfer(String mrkey) {

		AppLog.begin();
		String responsString = "<font color='red' size='2'><span><b>ERROR:Sorry, There was problem while Processing Your Request.</b></font>";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String[] newZROcd = newZRO.split(",");
			int rowsInserted = 0;
			int getRow = GetterDAO.getUserExistenceCount(newZROcd[0]);
			AppLog.info("No.Of 'DJB' named meter reader:: " + getRow);
			String oldZroCode = GetterDAO.getZroCodeByZoneMrArea(areaCode,
					mrNoList, oldZone);
			AppLog.info("Old zro location is **" + oldZroCode);
			if (getRow > 0) {
				if (getRow == 1) {

					int inProgress = MrdTransferDAO
							.getCntOfInProgressForMrdTrnsfr(null);
					AppLog
							.info("Count of InProgress status in Stg table in Action** "
									+ inProgress);
					if (inProgress != DJBConstants.MAX_INPROGRESS_COUNT
							|| inProgress < DJBConstants.MAX_INPROGRESS_COUNT) {
						if (null != mrkey && null != newZone
								&& null != mrNoList && null != areaCode
								&& null != userId) {
							rowsInserted += MrdTransferDAO.InitiateMrdTransfer(
									mrkey, oldZone, oldZroCode, newZRO,
									mrNoList, areaCode, userId);
						}
					} else {
						AppLog.info("Mrd transfr Are already in ***");
						responsString = "ERROR:Mrd Transfer are already in Process, Please Retry Later.";
					}

					if (rowsInserted > 0) {
						runProcedureMrdTransfer(mrkey);
						responsString = DJBConstants.MRD_TRANSFER_INITIATED_MSG;
					}
				} else {
					responsString = "ERROR:Sorry,There are more than one DJB meter reader..";

				}
			} else {
				responsString = "ERROR:Sorry,There is no djb meter reader.";
			}
		} catch (Exception e) {
			responsString = "ERROR:Sorry, There was problem while Processing Last Operation.";
			AppLog.error(e);
		}
		AppLog.end();
		return responsString;
	}

	/**
	 * <p>
	 * This method is used to call the procedure in threads
	 * </p>
	 * 
	 * @param mrkey
	 */
	public void runProcedureMrdTransfer(String mrkey) {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null != mrkey) {
				/** Calling PROC_MRD_TRNSFR in New Thread. */
				new MrdTransferDAO(oldZone, mrNoList, areaCode, newZone,
						newZRO, mrkey, userId);
			}

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * @param setAreaCode
	 *            the setAreaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @param setHidAction
	 *            the setHidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param setInputStream
	 *            the setInputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param setMrNoList
	 *            the setMrNoList to set
	 */
	public void setMrNoList(String mrNoList) {
		this.mrNoList = mrNoList;
	}

	/**
	 * @param setNewZone
	 *            the setNewZone to set
	 */
	public void setNewZone(String newZone) {
		this.newZone = newZone;
	}

	/**
	 * @param newZRO
	 *            the newZRO to set
	 */
	public void setNewZRO(String newZRO) {
		this.newZRO = newZRO;
	}

	/**
	 * @param setOldZone
	 *            the setOldZone to set
	 */
	public void setOldZone(String oldZone) {
		this.oldZone = oldZone;
	}

	/**
	 * @param setResponse
	 *            the setResponse to set
	 */

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * <p>
	 * This method is used to check all the conditions and validate parameters
	 * to call procedure ZroWiseMRDTransfer
	 * </p>
	 * 
	 * @return responsString
	 */
	private String ZroWiseMRDTransfer(String mrkey) {

		AppLog.begin();
		String responsString = "<font color='red' size='2'><span><b>ERROR:Sorry, There was problem while Processing Your Request.</b></font>";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			int rowsInserted = 0;
			String oldZroCode = GetterDAO.getZroCodeByZoneMrArea(areaCode,
					mrNoList, oldZone);
			AppLog.info("Old zro location is **" + oldZroCode);
			int inProgress = MrdTransferDAO
					.getCntOfInProgressForMrdTrnsfr(null);
			AppLog.info("Count of InProgress status in Stg table in Action** "
					+ inProgress);
			if (inProgress != DJBConstants.MAX_INPROGRESS_COUNT
					|| inProgress < DJBConstants.MAX_INPROGRESS_COUNT) {
				AppLog.info("inprogress ***");
				if (null != mrkey && null != oldZone && null != mrNoList
						&& null != areaCode && null != userId) {
					rowsInserted += MrdTransferDAO.InitiateMrdTransfer(mrkey,
							oldZone, oldZroCode, newZRO, mrNoList, areaCode,
							userId);
				}
			} else {
				AppLog.info("ZRO wise Mrd transfr Are already in ***");
				responsString = "ERROR: ZRO wise Mrd Transfer are already in Process, Please Retry Later.";
			}

			if (rowsInserted > 0) {
				AppLog.info("rowsInserted ***");
				runProcedureMrdTransfer(mrkey);
				responsString = DJBConstants.ZRO_TRANSFER_INITIATED_MSG;
			}

		} catch (Exception e) {
			responsString = "ERROR:Sorry, There was problem while Processing Last Operation.";
			AppLog.info("55555" + responsString);
			AppLog.error(e);
		}
		AppLog.end();
		return responsString;
	}

}
