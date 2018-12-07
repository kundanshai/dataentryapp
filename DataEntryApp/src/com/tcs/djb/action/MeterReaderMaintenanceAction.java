/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java   09 Oct 2014
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MRDTaggingDAO;
import com.tcs.djb.dao.MeterReaderMaintenanceDAO;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Meter Reader Maintenance screen developed as per JTrac
 * HHD-48.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 20-09-2013
 * @history: 26-SEP-2015 Rajib changed this file as per JTrac DJB-3871 to add
 *           new feature for meter Reader Profiling
 * @history: 13-OCT-2015 Rajib Added parameter for HHD ID on
 *           {@link #searchMeterReader} as per Jtrac DJB-4199
 * 
 * @history Arnab Nandy (1227971) on 08-06-2016 edited {@link #ajaxMethod()} for
 *          Modifying HHD ID Drop Down and related designs in Pop-up, according
 *          to JTrac DJB-4484 and OpenProject#1230
 */
@SuppressWarnings("deprecation")
public class MeterReaderMaintenanceAction extends ActionSupport implements
		ServletResponseAware {
	private static final String DEFAULT_METER_READER = "DJB";
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private static final String optionTagBeginPart1 = "<option value='";
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private static final String optionTagBeginPart2 = "'>";
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private static final String optionTagEnd = "</option>";
	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "33";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String activeInactiveRemark;
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String bypassFromdate;
	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String bypassTodate;

	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String hhdId;
	/**
	 * Hidden action.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;

	/**
	 * Maximum Number of Record Displayed Per Page of search screen.
	 */
	private String maxRecordPerPage;

	/**
	 * Designation of the Meter Reader Name
	 */
	private String meterReaderDesignation;

	/**
	 * Email ID of the Meter Reader Name
	 */
	private String meterReaderEmail;

	/**
	 * Employee ID of the Meter Reader.
	 */
	private String meterReaderEmployeeID;

	/**
	 * Meter Reader ID
	 */
	private String meterReaderID;

	/**
	 * Mobile No of the Meter Reader Name
	 */
	private String meterReaderMobileNo;
	/**
	 * Meter Reader Name
	 */
	private String meterReaderName;

	/**
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String meterReaderStatus;
	/**
	 * Zone which the Meter Reader Belong to.
	 */
	private String meterReaderZone;
	/**
	 * Zro Location which the Meter Reader Belong to.
	 * 
	 * @author Rajib Hazarika
	 * @since 26-09-2015
	 */
	private String meterReaderZroCd;

	private String page;

	/**
	 * Current Page no.
	 */
	private String pageNo;

	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;
	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;
	private String selectedMRKeys;

	/**
	 * Zone No Drop down.
	 */
	private String selectedZone;

	private String supervisorEmpId;

	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;

	private String totalRecordsSelected;

	/**
	 * <p>
	 * Add the details of a meter reader.
	 * </p>
	 * 
	 * @return String
	 */
	public String addMeterReader() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderZone(meterReaderZone);
			meterReaderDetails.setMeterReaderID(meterReaderID);
			meterReaderDetails.setMeterReaderName(meterReaderName);
			meterReaderDetails.setMeterReaderEmployeeID(meterReaderEmployeeID);
			meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
			meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
			meterReaderDetails
					.setMeterReaderDesignation(meterReaderDesignation);
			meterReaderDetails.setUserId(userId);
			// Added by Rajib as pwer JTrac DJB-3871 on 26-SEP-2015
			meterReaderDetails.setMeterReaderZroCd(meterReaderZroCd);
			meterReaderDetails.setActiveInactiveRemark(activeInactiveRemark);
			meterReaderDetails.setHhdId(hhdId);
			meterReaderDetails.setSupervisorEmpId(supervisorEmpId);
			meterReaderDetails.setMeterReaderStatus(meterReaderStatus);

			AppLog.info(">>userId>>" + userId + ">>meterReaderZone>>"
					+ meterReaderZone + ">>meterReaderID>>" + meterReaderID
					+ ">>meterReaderName>>" + meterReaderName
					+ ">>meterReaderEmployeeID" + meterReaderEmployeeID
					+ ">>meterReaderMobileNo>>" + ">>meterReaderEmail>>"
					+ meterReaderEmail + ">>meterReaderDesignation>>"
					+ meterReaderDesignation + ">>meterReaderZroCd>"
					+ meterReaderZroCd + ">>activeInactiveRemark>>"
					+ activeInactiveRemark + ">>hhdId>>" + hhdId
					+ ">>supervisorEmpId>>" + supervisorEmpId
					+ ">>meterReaderStatus>>" + meterReaderStatus);
			if (!DJBConstants.FLAG_Y.equals(meterReaderDetails
					.getMeterReaderStatus())) {
				meterReaderDetails.setHhdId("");
			}
			if (MeterReaderMaintenanceDAO
					.addMeterReaderDetails(meterReaderDetails)) {
				List<MeterReaderDetails> meterReaderDetailsList = MeterReaderMaintenanceDAO
						.getAllMeterReaderDetailsListMap(meterReaderDetails);
				if (null != meterReaderDetailsList
						&& meterReaderDetailsList.size() > 0) {
					meterReaderID = meterReaderDetailsList.get(0)
							.getMeterReaderID();
				}
			}
			StringBuffer resonseSB = new StringBuffer(512);
			if (null == meterReaderID || "".equals(meterReaderID)) {
				resonseSB.append("ERROR:");
			} else {
				resonseSB.append(meterReaderID);
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * For all ajax call in Data Entry Search screen
	 * 
	 * @return string
	 * 
	 * @history Arnab Nandy (1227971) on 08-06-2016 edited {@link #ajaxMethod()}
	 *          for Modifying HHD ID Drop Down and related designs in Pop-up,
	 *          according to JTrac DJB-4484 and OpenProject#1230
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
			if ("addMeterReader".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return addMeterReader();
			}
			if ("editMeterReader".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return updateMeterReader();
			}
			if ("deleteMeterReader".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return deleteMeterReader();
			}
			// START: Added by Rajib as per Jtrac DJB-3871 on 26-SEP-2015
			if ("bypassMeterReader".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return bypassMeterReader();
			}
			// END: Added by Rajib as per Jtrac DJB-3871 on 26-SEP-2015
			if ("search".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				searchMeterReader();
			}
			if ("searchHistory".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				searchMeterReaderHistory();
			}
			if ("validateMeterReader".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				validateMeterReader();
			}
			// START: Added by Rajib as per Jtrac DJB-3871 on 26-SEP-2015
			if ("populateSupervisorEmpList".equalsIgnoreCase(hidAction)) {

				Map<String, String> returnMap = (HashMap<String, String>) MeterReaderMaintenanceDAO
						.getSupervisorEmpIdListMap(meterReaderZroCd,
								meterReaderID);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB.append("<SUP>");
				dropDownSB
						.append("<select name=\"supervisorEmpId\" id=\"supervisorEmpId\" class=\"selectbox-long\" >");
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
				dropDownSB.append("</SUP>");
				// if (null != hhdId && !"".equals(hhdId)) {
				returnMap = (HashMap<String, String>) MeterReaderMaintenanceDAO
						.getActiveHhdListMap(hhdId);
				dropDownSB.append("<HHD>");
				/*
				 * Start : Edited by Arnab Nandy (1227971) on 08-06-2016 for
				 * Modifying HHD ID Drop Down and related designs in Pop-up,
				 * according to JTrac DJB-4484 and OpenProject#1230 dropDownSB
				 * .append
				 * ("<select name=\"hhdId\" id=\"hhdId\" class=\"selectbox\" >"
				 * );
				 */
				dropDownSB
						.append("<select name=\"hhdId\" id=\"hhdId\" class=\"selectbox-long\" >");
				/*
				 * End : Edited by Arnab Nandy (1227971) on 08-06-2016 for
				 * Modifying HHD ID Drop Down and related designs in Pop-up,
				 * according to JTrac DJB-4484 and OpenProject#1230
				 */
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
				dropDownSB.append("</HHD>");
				// }
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */

			}
			// END: Added by Rajib as per Jtrac DJB-3871 on 26-SEP-2015

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * Rajib added this method on 26-SEP-2015 as per JTrac DJB-3871 to bypass
	 * Meter Reader Details from no. of records validation
	 * 
	 * @return String
	 */
	public String bypassMeterReader() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderID(meterReaderID);
			meterReaderDetails.setBypassFromdate(bypassFromdate);
			meterReaderDetails.setBypassTodate(bypassTodate);
			meterReaderDetails.setUpdatedBy(userId);
			StringBuffer resonseSB = new StringBuffer(512);
			if (MeterReaderMaintenanceDAO
					.bypassMeterReaderDetails(meterReaderDetails)) {
				resonseSB
						.append("<font color='green' size='2'><li><span><b>SUCCESS:Meter Reader is Successfully Bypassed.</b></font>");
			} else {
				resonseSB
						.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Bypassing Meter Reader.</b></font>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
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
	 * Delete the details of a meter reader.It creates a history first then
	 * delete the record.
	 * </p>
	 * 
	 * @return String
	 */
	public String deleteMeterReader() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			StringBuffer resonseSB = new StringBuffer(512);
			/* Un-tag the meter reader if tagged with Some MRD */
			if (unTagMRD()) {
				MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
				meterReaderDetails.setMeterReaderZone(meterReaderZone);
				meterReaderDetails.setMeterReaderID(meterReaderID);
				meterReaderDetails.setMeterReaderName(meterReaderName);
				meterReaderDetails
						.setMeterReaderEmployeeID(meterReaderEmployeeID);
				meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
				meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
				meterReaderDetails
						.setMeterReaderDesignation(meterReaderDesignation);
				meterReaderDetails.setUserId(userId);
				if (MeterReaderMaintenanceDAO
						.createMeterReaderDetailsHistory(meterReaderDetails)) {
					if (MeterReaderMaintenanceDAO
							.deleteMeterReaderDetails(meterReaderDetails)) {
						if (null != selectedMRKeys
								&& !"".equals(selectedMRKeys)) {
							String listOfMRKeys = "";
							if (null != selectedMRKeys) {
								listOfMRKeys = selectedMRKeys.replaceFirst(",",
										"");
							}
							resonseSB
									.append("<font color='green' size='2'><b>Deleted Successfully and MRD(s) "
											+ listOfMRKeys
											+ " tagged to "
											+ DEFAULT_METER_READER
											+ "</b></font>.");
						} else {
							resonseSB.append("SUCCESS");
						}
					} else {
						resonseSB
								.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Deleting the Details.</b></font>");
					}
				} else {
					resonseSB
							.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Creating History.</b></font>");
				}
			} else {
				resonseSB
						.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Untagging the Meter Reader from the Tagged MRD(s).</b></font>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
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
	 * @return the activeInactiveRemark
	 */
	public String getActiveInactiveRemark() {
		return activeInactiveRemark;
	}

	/**
	 * @return the bypassFromdate
	 */
	public String getBypassFromdate() {
		return bypassFromdate;
	}

	/**
	 * @return the bypassTodate
	 */
	public String getBypassTodate() {
		return bypassTodate;
	}

	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
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
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the meterReaderDesignation
	 */
	public String getMeterReaderDesignation() {
		return meterReaderDesignation;
	}

	/**
	 * @return the meterReaderEmail
	 */
	public String getMeterReaderEmail() {
		return meterReaderEmail;
	}

	/**
	 * @return the meterReaderEmployeeID
	 */
	public String getMeterReaderEmployeeID() {
		return meterReaderEmployeeID;
	}

	/**
	 * @return the meterReaderID
	 */
	public String getMeterReaderID() {
		return meterReaderID;
	}

	/**
	 * @return the meterReaderMobileNo
	 */
	public String getMeterReaderMobileNo() {
		return meterReaderMobileNo;
	}

	/**
	 * @return the meterReaderName
	 */
	public String getMeterReaderName() {
		return meterReaderName;
	}

	/**
	 * @return the meterReaderStatus
	 */
	public String getMeterReaderStatus() {
		return meterReaderStatus;
	}

	/**
	 * @return the meterReaderZone
	 */
	public String getMeterReaderZone() {
		return meterReaderZone;
	}

	/**
	 * @return the meterReaderZroCd
	 */
	public String getMeterReaderZroCd() {
		return meterReaderZroCd;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @return the pageNoDropdownList
	 */
	public List<String> getPageNoDropdownList() {
		return pageNoDropdownList;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedMRKeys
	 */
	public String getSelectedMRKeys() {
		return selectedMRKeys;
	}

	/**
	 * @return the selectedZone
	 */
	public String getSelectedZone() {
		return selectedZone;
	}

	/**
	 * @return the supervisorEmpId
	 */
	public String getSupervisorEmpId() {
		return supervisorEmpId;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return the totalRecordsSelected
	 */
	public String getTotalRecordsSelected() {
		return totalRecordsSelected;
	}

	/**
	 * <p>
	 * Populate default values required for Demand Transfer Screen.
	 * </p>
	 */
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "MASTER");
			if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			if (null == session.get("MeterReaderDesignationListMap")) {
				session.put("MeterReaderDesignationListMap", GetterDAO
						.getMeterReaderDesignationListMap());
			}
			session.put("ZROListMap", GetterDAO.getZROListMap());
			if (null == session.get("MeterReaderStatusListMap")) {
				session
						.put("MeterReaderStatusListMap",
								MeterReaderMaintenanceDAO
										.getMeterReaderStatusListMap());
			}
			if (null == session.get("ActiveInactiveListMap")) {
				session.put("ActiveInactiveListMap", MeterReaderMaintenanceDAO
						.getActiveInactiveMapList());
			}
			if (null == session.get("SupervisorEmpIdListMap")) {
				session.put("SupervisorEmpIdListMap",
						new HashMap<String, String>());
			}
			session.put("HhdIdListMap", MeterReaderMaintenanceDAO
					.getActiveHhdListMap(null));
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Search of a meter reader details.
	 * </p>
	 * 
	 * @return
	 * @history: On 26-SEP-2015 rajib changed this function as per JTrac
	 *           DJB-3871
	 */
	public String searchMeterReader() {
		AppLog.begin();
		try {
			StringBuffer resonseSB = new StringBuffer(512);
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderZroCd(meterReaderZroCd);
			meterReaderDetails.setMeterReaderStatus(meterReaderStatus);
			AppLog.info(">>meterReaderStatus>>" + meterReaderStatus
					+ ">>meterReaderStatus>>" + meterReaderStatus
					+ ">>meterReaderEmployeeID>>" + meterReaderEmployeeID);
			// meterReaderDetails.setMeterReaderZone(selectedZone);
			// meterReaderDetails.setMeterReaderID(meterReaderID);
			// meterReaderDetails.setMeterReaderName(meterReaderName);
			meterReaderDetails.setMeterReaderEmployeeID(meterReaderEmployeeID);
			// Start: Added by Rajib as per JTrac DJB-4199 on 13-OCT-2015
			meterReaderDetails.setHhdId(hhdId);
			// END: Added by Rajib as per JTrac DJB-4199 on 13-OCT-2015
			// meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
			// meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
			// meterReaderDetails
			// .setMeterReaderDesignation(meterReaderDesignation);
			// meterReaderDetails.setUserId(userId);
			List<MeterReaderDetails> meterReaderDetailsList = MeterReaderMaintenanceDAO
					.getAllMeterReaderDetailsListMap(meterReaderDetails);
			if (null != meterReaderDetailsList
					&& meterReaderDetailsList.size() > 0) {
				resonseSB.append("<table class=\"table-grid\">");
				resonseSB.append("<tr>");
				resonseSB
						.append("<th align=\"center\" colspan=\"16\">Current Meter Reader Details</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\">ZONE</th>");
				resonseSB.append("<th width=\"5%\">ZRO LOCATION</th>");
				resonseSB.append("<th width=\"10%\">NAME</th>");
				resonseSB.append("<th width=\"5%\">EMP ID</th>");
				resonseSB.append("<th width=\"5%\">METER READER CODE</th>");
				resonseSB.append("<th width=\"5%\">MOBILE</th>");
				resonseSB.append("<th width=\"10%\">E-MAIL</th>");
				resonseSB.append("<th width=\"5%\">DESIGNATION</th>");

				resonseSB.append("<th width=\"5%\">ACTIVE/INACTIVE</th>");
				resonseSB
						.append("<th width=\"5%\">ACTIVE INACTIVE REASON</th>");
				resonseSB.append("<th width=\"5%\">EFFECTIVE FROM</th>");
				resonseSB.append("<th width=\"5%\">EFFECTIVE TO</th>");
				resonseSB.append("<th width=\"5%\">HHD ID</th>");

				resonseSB.append("<th width=\"20%\" colspan=\"3\">Action</th>");
				resonseSB.append("</tr>");
				for (int i = 0; i < meterReaderDetailsList.size(); i++) {
					meterReaderDetails = meterReaderDetailsList.get(i);
					resonseSB
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					// resonseSB.append("<td align=\"center\">" + (i + 1)
					// + "</td>");

					resonseSB
							.append("<td align=\"center\">"
									+ meterReaderDetails.getMeterReaderZone()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderZroCd()
							+ "</td>");
					resonseSB
							.append("<td align=\"center\" nowrap>"
									+ meterReaderDetails.getMeterReaderName()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmployeeID()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderID() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderMobileNo()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmail()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderDesignation()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderStatus()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getActiveInactiveRemark()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getEffFromdate() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getEffTodate() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getHhdId() + "</td>");
					resonseSB
							.append("<td align=\"center\"><a href=\"#\" onclick=\"javascript:fnEdit('"
									+ meterReaderDetails.getMeterReaderID()
									+ "','"
									+ meterReaderDetails
											.getMeterReaderEmployeeID()
									+ "','"
									+ meterReaderDetails.getMeterReaderName()
									+ "','"
									+ meterReaderDetails
											.getMeterReaderMobileNo()
									+ "','"
									+ meterReaderDetails.getMeterReaderEmail()
									+ "','"
									+ meterReaderDetails
											.getMeterReaderDesignation()
									+ "','editMeterReader"

									+ "','"
									+ meterReaderDetails.getSupervisorEmpId()
									+ "','"
									+ meterReaderDetails.getHhdId()
									+ "','"
									+ meterReaderDetails
											.getActiveInactiveRemark()
									+ "','"
									+ meterReaderDetails.getMeterReaderStatus()
									+ "','"
									+ meterReaderDetails.getMeterReaderZroCd()
									+ "','"
									+ meterReaderDetails.getHhdId()
									+ "' " + ");\">Update</a></td>");
					if (!"DJB".equals(meterReaderDetails.getMeterReaderName())) {

						resonseSB
								.append("<td align=\"center\"><a href=\"#\" onclick=\"javascript:fnSearchHistory('"
										+ meterReaderDetails.getMeterReaderID()
										+ "');\">History</a></td>");
					} else {
						resonseSB.append("<td align=\"center\">&nbsp;</td>");
					}
					// meterReaderID,meterReaderEmployeeID,meterReaderName,meterReaderMobileNo,meterReaderEmail,meterReaderDesignation,
					// popUpPurpose,supervisorEmpId,hhdId,activeInactiveRemark,MeterReaderStatus,meterReaderZroCd
					if (DJBConstants.FLAG_Y.equals(meterReaderDetails
							.getMeterReaderStatus())
							&& (null != meterReaderDetails.getHhdId() && !""
									.equalsIgnoreCase(meterReaderDetails
											.getHhdId()))) {
						resonseSB
								.append("<td align=\"center\"><a href=\"#\" onclick=\"javascript:fnEdit('"
										+ meterReaderDetails.getMeterReaderID()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderEmployeeID()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderName()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderMobileNo()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderEmail()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderDesignation()
										+ "','bypassMeterReader"

										+ "','"
										+ meterReaderDetails
												.getSupervisorEmpId()
										+ "','"
										+ meterReaderDetails.getHhdId()
										+ "','"
										+ meterReaderDetails
												.getActiveInactiveRemark()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderStatus()
										+ "','"
										+ meterReaderDetails
												.getMeterReaderZroCd()
										+ "','"
										+ meterReaderDetails.getHhdId()
										+ "' "
										+ ");\">Bypass Validation</a></td>");
					} else {
						resonseSB
								.append("<td align=\"center\" title=\"To Bypass, Meter Reader must be Active and Tagged to a HHD Device\" >&nbsp;</td>");
					}
					resonseSB.append("</tr>");
				}
				resonseSB.append("</table><br/>");
			} else {
				resonseSB
						.append("<font color='red'><b>No records Found to Display!</b></font><br/>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());

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
	 * Search of a meter reader details from History table.
	 * </p>
	 * 
	 * @history: On 26-SEP-2015 rajib changed this function as per JTrac
	 *           DJB-3871
	 * @return
	 */
	public String searchMeterReaderHistory() {
		AppLog.begin();
		try {
			StringBuffer resonseSB = new StringBuffer(512);
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			// meterReaderDetails.setMeterReaderZone(selectedZone);
			meterReaderDetails.setMeterReaderID(meterReaderID);
			// meterReaderDetails.setMeterReaderID(meterReaderID);
			// meterReaderDetails.setMeterReaderName(meterReaderName);
			// meterReaderDetails.setMeterReaderEmployeeID(meterReaderEmployeeID);
			// meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
			// meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
			// meterReaderDetails
			// .setMeterReaderDesignation(meterReaderDesignation);
			// meterReaderDetails.setUserId(userId);
			List<MeterReaderDetails> meterReaderDetailsList = MeterReaderMaintenanceDAO
					.getAllMeterReaderDetailsHistoryListMap(meterReaderDetails);
			if (null != meterReaderDetailsList
					&& meterReaderDetailsList.size() > 0) {
				resonseSB
						.append("<table class=\"table-grid\" title=\"History Log of Meter Reader(s) Updated or Deleted.\">");
				resonseSB.append("<tr>");
				resonseSB
						.append("<th align=\"center\" colspan=\"17\"> Meter Reader History Details</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\">ZONE</th>");
				resonseSB.append("<th width=\"5%\">ZRO LOCATION</th>");
				resonseSB.append("<th width=\"10%\">NAME</th>");
				resonseSB.append("<th width=\"5%\">EMP ID</th>");
				resonseSB.append("<th width=\"5%\">METER READER CODE</th>");
				resonseSB.append("<th width=\"5%\">MOBILE</th>");
				resonseSB.append("<th width=\"10%\">E-MAIL</th>");
				resonseSB.append("<th width=\"5%\">DESIGNATION</th>");
				resonseSB.append("<th width=\"5%\">ACTIVE/INACTIVE</th>");
				resonseSB
						.append("<th width=\"5%\">ACTIVE INACTIVE REASON</th>");
				resonseSB.append("<th width=\"5%\">EFFECTIVE FROM</th>");
				resonseSB.append("<th width=\"5%\">EFFECTIVE TO</th>");
				resonseSB.append("<th width=\"5%\">HHD ID</th>");
				resonseSB.append("<th width=\"5%\">Created By</th>");
				resonseSB.append("<th width=\"5%\">Created On</th>");
				resonseSB.append("<th width=\"5%\">Updated By</th>");
				resonseSB.append("<th width=\"5%\">Updated On</th>");
				resonseSB.append("</tr>");
				for (int i = 0; i < meterReaderDetailsList.size(); i++) {
					meterReaderDetails = meterReaderDetailsList.get(i);
					resonseSB
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					resonseSB
							.append("<td align=\"center\">"
									+ meterReaderDetails.getMeterReaderZone()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderZroCd()
							+ "</td>");
					resonseSB
							.append("<td align=\"center\" nowrap>"
									+ meterReaderDetails.getMeterReaderName()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmployeeID()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderID() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderMobileNo()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmail()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderDesignation()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderStatus()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getActiveInactiveRemark()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getEffFromdate() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getEffTodate() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getHhdId() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getCreatedBy() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getCreatedOn() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getUpdatedBy() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getUpdatedOn() + "</td>");

					resonseSB.append("</tr>");
				}
				resonseSB
						.append("<tr> <td align=\"center\" colspan=\"17\"><input type=\"button\" name=\"btnClose\"	id=\"btnClose\" value=\" Close History \" class=\"groovybutton\" onclick=\"closeHistoryDetails();\" />  </td> </tr>");
				resonseSB.append("</table><br/>");

			} else {
				resonseSB
						.append("<font color='red'><b>No records Found to Display!</b></font><br/><br/>");
				resonseSB
						.append("<input type=\"button\" name=\"btnClose\"	id=\"btnClose\" value=\" Close History \" class=\"groovybutton\" onclick=\"closeHistoryDetails();\" /> ");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @param activeInactiveRemark
	 */
	public void setActiveInactiveRemark(String activeInactiveRemark) {
		this.activeInactiveRemark = activeInactiveRemark;
	}

	/**
	 * @param bypassFromdate
	 */
	public void setBypassFromdate(String bypassFromdate) {
		this.bypassFromdate = bypassFromdate;
	}

	/**
	 * @param bypassTodate
	 */
	public void setBypassTodate(String bypassTodate) {
		this.bypassTodate = bypassTodate;
	}

	/**
	 * @param hhdId
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
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
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param meterReaderDesignation
	 *            the meterReaderDesignation to set
	 */
	public void setMeterReaderDesignation(String meterReaderDesignation) {
		this.meterReaderDesignation = meterReaderDesignation;
	}

	/**
	 * @param meterReaderEmail
	 *            the meterReaderEmail to set
	 */
	public void setMeterReaderEmail(String meterReaderEmail) {
		this.meterReaderEmail = meterReaderEmail;
	}

	/**
	 * @param meterReaderEmployeeID
	 *            the meterReaderEmployeeID to set
	 */
	public void setMeterReaderEmployeeID(String meterReaderEmployeeID) {
		this.meterReaderEmployeeID = meterReaderEmployeeID;
	}

	/**
	 * @param meterReaderID
	 *            the meterReaderID to set
	 */
	public void setMeterReaderID(String meterReaderID) {
		this.meterReaderID = meterReaderID;
	}

	/**
	 * @param meterReaderMobileNo
	 *            the meterReaderMobileNo to set
	 */
	public void setMeterReaderMobileNo(String meterReaderMobileNo) {
		this.meterReaderMobileNo = meterReaderMobileNo;
	}

	/**
	 * @param meterReaderName
	 *            the meterReaderName to set
	 */
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}

	public void setMeterReaderStatus(String meterReaderStatus) {
		this.meterReaderStatus = meterReaderStatus;
	}

	/**
	 * @param meterReaderZone
	 *            the meterReaderZone to set
	 */
	public void setMeterReaderZone(String meterReaderZone) {
		this.meterReaderZone = meterReaderZone;
	}

	/**
	 * @param meterReaderZroCd
	 */
	public void setMeterReaderZroCd(String meterReaderZroCd) {
		this.meterReaderZroCd = meterReaderZroCd;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @param pageNoDropdownList
	 *            the pageNoDropdownList to set
	 */
	public void setPageNoDropdownList(List<String> pageNoDropdownList) {
		this.pageNoDropdownList = pageNoDropdownList;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedMRKeys
	 *            the selectedMRKeys to set
	 */
	public void setSelectedMRKeys(String selectedMRKeys) {
		this.selectedMRKeys = selectedMRKeys;
	}

	/**
	 * @param selectedZone
	 *            the selectedZone to set
	 */
	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
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
	 * @param supervisorEmpId
	 */
	public void setSupervisorEmpId(String supervisorEmpId) {
		this.supervisorEmpId = supervisorEmpId;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param totalRecordsSelected
	 *            the totalRecordsSelected to set
	 */
	public void setTotalRecordsSelected(String totalRecordsSelected) {
		this.totalRecordsSelected = totalRecordsSelected;
	}

	/**
	 * <p>
	 * Tag MRDs to default meter reader for the selected MRDs while un-tagged.
	 * </p>
	 * 
	 * @return
	 */
	public boolean tagMRDToDeafault() {
		AppLog.begin();
		boolean isSuccess = true;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String listOfMRKeys = "";
			if (null != selectedMRKeys) {
				listOfMRKeys = selectedMRKeys.replaceFirst(",", "");
			}
			String mrKeyArray[] = listOfMRKeys.split(",");
			String insertSuccess = "";
			for (int i = 0; i < mrKeyArray.length; i++) {
				String mrKey = mrKeyArray[i];
				if (MRDTaggingDAO.tagMRDToDeafault(mrKey, DEFAULT_METER_READER,
						meterReaderZone, userId)) {
					insertSuccess += "," + mrKey;
				}
			}
			if (!selectedMRKeys.equals(insertSuccess)) {
				selectedMRKeys = insertSuccess;
			}
		} catch (Exception e) {
			isSuccess = false;
			AppLog.error(e);
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * Un tag MRDs from current meter reader and tag to Default Meter Reader
	 * DJB.It creates a history first then delete the record.
	 * </p>
	 * 
	 * @return
	 */
	public boolean unTagMRD() {
		AppLog.begin();
		boolean isSuccess = true;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			selectedMRKeys = MRDTaggingDAO
					.getAllCurrentlyTaggedMRKeyList(meterReaderID);
			if (null != selectedMRKeys && !"".equals(selectedMRKeys)) {
				String listOfMRKeys = "";
				if (null != selectedMRKeys) {
					listOfMRKeys = selectedMRKeys.replaceFirst(",", "");
				}
				/* Create history of the current tagging details */
				MRDTaggingDAO.createMRDTaggingDetailsHistory(listOfMRKeys,
						userId);
				if (MRDTaggingDAO.deleteMRDTaggingDetails(listOfMRKeys)) {
					/* After un-tagging tag the MRD to default Meter Reader */
					AppLog.end();
					return tagMRDToDeafault();
				}
			}
		} catch (Exception e) {
			isSuccess = false;
			AppLog.error(e);
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * Update the details of a meter reader.It creates a history first then
	 * delete the record.
	 * </p>
	 * 
	 * @return
	 */
	public String updateMeterReader() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			// meterReaderDetails.setMeterReaderZone(meterReaderZone);
			meterReaderDetails.setMeterReaderID(meterReaderID);
			meterReaderDetails.setMeterReaderName(meterReaderName);
			meterReaderDetails.setMeterReaderEmployeeID(meterReaderEmployeeID);
			meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
			meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
			meterReaderDetails
					.setMeterReaderDesignation(meterReaderDesignation);
			meterReaderDetails.setHhdId(hhdId);
			meterReaderDetails.setSupervisorEmpId(supervisorEmpId);
			meterReaderDetails.setActiveInactiveRemark(activeInactiveRemark);
			meterReaderDetails.setMeterReaderZroCd(meterReaderZroCd);
			meterReaderDetails.setMeterReaderStatus(meterReaderStatus);
			meterReaderDetails.setUserId(userId);
			StringBuffer resonseSB = new StringBuffer(512);
			if (MeterReaderMaintenanceDAO
					.createMeterReaderDetailsHistory(meterReaderDetails)) {
				AppLog.info("Created Meter Reader Details History");
				if (MeterReaderMaintenanceDAO
						.deleteMeterReaderDetails(meterReaderDetails)) {
					AppLog
							.info("Delete Meter Reader Details to Create new Meter Reader Details");
					if (!DJBConstants.FLAG_Y.equals(meterReaderDetails
							.getMeterReaderStatus())) {
						meterReaderDetails.setHhdId("");
					}
					if (MeterReaderMaintenanceDAO
							.addMeterReaderDetails(meterReaderDetails)) {
						AppLog.info("Created new Meter Reader Details");
						resonseSB.append("SUCCESS");
					} else {
						AppLog
								.info("Unable to Create new Meter Reader Details");
						resonseSB
								.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Updating the Details.</b></font>");
					}
				} else {
					AppLog
							.info("Unable to Delete Meter Reader Details to Create new Meter Reader Details");
					resonseSB
							.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Updating the Details.</b></font>");
				}
			} else {
				AppLog.info("Unable to Create Meter Reader Details History");
				resonseSB
						.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Creating History.</b></font>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
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
	 * Validate Meter Reader EmployeeID of a meter reader if exists in the
	 * system.
	 * </p>
	 * 
	 * @history: On 26-SEP-2015 Rajib changed this method as per JTrac DJB-3871
	 * @return
	 */
	public String validateMeterReader() {
		AppLog.begin();
		try {
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			// meterReaderDetails.setMeterReaderZone(meterReaderZone);
			// meterReaderDetails.setMeterReaderID(meterReaderID);
			meterReaderDetails.setMeterReaderEmployeeID(meterReaderEmployeeID);
			if (null == meterReaderEmployeeID
					|| "".equals(meterReaderEmployeeID)) {
				meterReaderDetails.setMeterReaderName(meterReaderName);
			}
			if (DEFAULT_METER_READER.equals(meterReaderName)) {
				meterReaderDetails.setMeterReaderEmployeeID(null);
				meterReaderDetails.setMeterReaderName(meterReaderName);
				// meterReaderDetails.setMeterReaderZone(meterReaderZone);
				meterReaderDetails.setMeterReaderZroCd(meterReaderZroCd);
			}
			// meterReaderDetails.setMeterReaderMobileNo(meterReaderMobileNo);
			// meterReaderDetails.setMeterReaderEmail(meterReaderEmail);
			// meterReaderDetails
			// .setMeterReaderDesignation(meterReaderDesignation);
			StringBuffer resonseSB = new StringBuffer(512);
			List<MeterReaderDetails> meterReaderDetailsList = MeterReaderMaintenanceDAO
					.getAllMeterReaderDetailsListMap(meterReaderDetails);
			if (null != meterReaderDetailsList
					&& meterReaderDetailsList.size() > 0) {
				meterReaderDetails = meterReaderDetailsList.get(0);
				if (null == meterReaderDetails.getMeterReaderName()
						|| "".equals(meterReaderDetails.getMeterReaderName())) {
					resonseSB.append("VALID");
				} else {
					resonseSB.append("<ZONE>"
							+ meterReaderDetails.getMeterReaderZroCd()
							+ "</ZONE>");
					resonseSB.append("<NAME>"
							+ meterReaderDetails.getMeterReaderName()
							+ "</NAME>");
					resonseSB
							.append("<MRCD>"
									+ meterReaderDetails.getMeterReaderID()
									+ "</MRCD>");
					resonseSB.append("<MOBL>"
							+ (null == meterReaderDetails
									.getMeterReaderMobileNo()
									|| "NA".equals(meterReaderDetails
											.getMeterReaderMobileNo()) ? ""
									: meterReaderDetails
											.getMeterReaderMobileNo())
							+ "</MOBL>");
					resonseSB.append("<MAIL>"
							+ (null == meterReaderDetails.getMeterReaderEmail()
									|| "NA".equals(meterReaderDetails
											.getMeterReaderEmail()) ? ""
									: meterReaderDetails.getMeterReaderEmail())
							+ "</MAIL>");
					resonseSB.append("<DESI>"
							+ (null == meterReaderDetails
									.getMeterReaderDesignation()
									|| "NA".equals(meterReaderDetails
											.getMeterReaderDesignation()) ? ""
									: meterReaderDetails
											.getMeterReaderDesignation())
							+ "</DESI>");
					resonseSB
							.append("<HHD>"
									+ (null == meterReaderDetails.getHhdId()
											|| "NA".equals(meterReaderDetails
													.getHhdId()) ? ""
											: meterReaderDetails.getHhdId())
									+ "</HHD>");
					resonseSB
							.append("<STAT>"
									+ (null == meterReaderDetails
											.getMeterReaderStatus()
											|| "NA".equals(meterReaderDetails
													.getMeterReaderStatus()) ? ""
											: meterReaderDetails
													.getMeterReaderStatus())
									+ "</STAT>");
					resonseSB.append("<REM>"
							+ (null == meterReaderDetails
									.getActiveInactiveRemark()
									|| "NA".equals(meterReaderDetails
											.getActiveInactiveRemark()) ? ""
									: meterReaderDetails
											.getActiveInactiveRemark())
							+ "</REM>");
					resonseSB.append("<SUP>"
							+ (null == meterReaderDetails.getSupervisorEmpId()
									|| "NA".equals(meterReaderDetails
											.getSupervisorEmpId()) ? ""
									: meterReaderDetails.getSupervisorEmpId())
							+ "</SUP>");
				}
			} else {
				resonseSB.append("VALID");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}
}
