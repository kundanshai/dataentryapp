/************************************************************************************************************
 * @(#) MRDTaggingAction.java   20 Sep 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MRDTaggingDAO;
import com.tcs.djb.model.MRDTaggingDetails;
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
 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871 to
 *          add ZRO Location and MrKey Checks
 */
@SuppressWarnings("deprecation")
public class MRDTaggingAction extends ActionSupport implements
		ServletResponseAware {
	private static final String DEFAULT_METER_READER = "DJB";
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "34";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	/**
	 * Hidden action.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;

	/**
	 * Meter Reader ID
	 */
	private String meterReaderID;
	/**
	 * Meter Reader Employee Code
	 * 
	 * @author Rajib Hazarika
	 * @since 06-10-2015
	 */
	private String mrEmpId;

	/**
	 * Meter Reader HHD Id
	 * 
	 * @author Rajib Hazarika
	 * @since 06-10-2015
	 */
	private String mrHhdId;

	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	private String selectedMRKeys;

	/**
	 * @author Matiur Rahman
	 * @since 30-09-2015
	 */
	private String selectedMRNo;

	/**
	 * Zone No Drop down.
	 */
	private String selectedZone;
	/**
	 * @author Matiur Rahman
	 * @since 30-09-2015
	 */
	private String selectedZROCode;

	/**
	 * <p>
	 * For all ajax call in Data Entry Search screen
	 * </p>
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
			
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			if (null != selectedMRKeys && selectedMRKeys.contains(",")) {
				String[] selectedMRKeysArr = selectedMRKeys.split(",");
				selectedMRKeys = "";
				for (int i = 0; i < selectedMRKeysArr.length; i++) {
					if (null != selectedMRKeysArr[i]
							&& !"".equals(selectedMRKeysArr[i])) {
						selectedMRKeys += "," + selectedMRKeysArr[i];
					}
				}
				if (selectedMRKeys.indexOf(",") == 0) {
					selectedMRKeys = selectedMRKeys.substring(1);
				}
			}
			/* End: Added by Matiur Rahman on 30-09-2015 */
			if ("tagMRD".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return tagMRD();
			}
			if ("unTagMRD".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return unTagMRD();
			}
			if ("search".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				searchMeterReaderMRDMapping();
			}
			if ("searchHistory".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				searchMeterReaderMRDMappingHistory();
			}
			if ("populateMtrRdr".equalsIgnoreCase(hidAction)) {
				AppLog.end();
				return populateMtrRdr();
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
	 * @return the meterReaderID
	 */
	public String getMeterReaderID() {
		return meterReaderID;
	}

	/**
	 * @return the mrEmpId
	 */
	public String getMrEmpId() {
		return mrEmpId;
	}

	/**
	 * @return the mrHhdId
	 */
	public String getMrHhdId() {
		return mrHhdId;
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
	 * <p>
	 * Add the details of a meter reader.
	 * </p>
	 * 
	 * @return
	 */

	/**
	 * @return the selectedZone
	 */
	public String getSelectedZone() {
		return selectedZone;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * <p>
	 * Populate default values required for MRD Tagging Screen.
	 * </p>
	 * 
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	@SuppressWarnings("unchecked")
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "MRD");
			if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			if (null == session.get("MRKEYListMap")) {
				session.put("MRKEYListMap", GetterDAO.getMRKEYListMap());
			}
			if (null == session.get("MRNoListMap")
					|| ((HashMap<String, String>) session.get("MRNoListMap"))
							.isEmpty()) {
				if (null != selectedZone && !"".equals(selectedZone)) {
					session.put("MRNoListMap",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(selectedZone));
				} else {
					session.put("MRNoListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("AreaListMap")
					|| ((HashMap<String, String>) session.get("AreaListMap"))
							.isEmpty()) {
				if (null != selectedZone && !"".equals(selectedZone)
						&& null != selectedMRNo && !"".equals(selectedMRNo)) {
					session.put("AreaListMap",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									selectedZone, selectedMRNo));
				} else {
					session.put("AreaListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("ZROListMap")
					|| ((HashMap<String, String>) session.get("ZROListMap"))
							.isEmpty()
					|| ((HashMap<String, String>) session.get("ZROListMap"))
							.size() == 1) {
				session.put("ZROListMap", (HashMap<String, String>) GetterDAO
						.getAllZRO());
			}
			/* End: Added by Matiur Rahman on 30-09-2015 */
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to populate details of meter reader.
	 * </p>
	 * 
	 * @return
	 */
	private String populateMtrRdr() {
		AppLog.begin();
		try {
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderEmployeeID(meterReaderID);
			List<MeterReaderDetails> meterReaderList = (ArrayList<MeterReaderDetails>) MRDTaggingDAO
					.getMeterReaderList(meterReaderDetails);
			StringBuffer meterReaderDropdown = new StringBuffer();
			meterReaderDropdown
					.append("<select name=\"meterReaderID\" id=\"meterReaderID\" class=\"selectbox-long\">");
			meterReaderDropdown.append("");
			meterReaderDetails = null;
			if (null != meterReaderList && meterReaderList.size() > 0) {
				for (int i = 0; i < meterReaderList.size(); i++) {
					meterReaderDetails = (MeterReaderDetails) meterReaderList
							.get(i);
					meterReaderDropdown.append(optionTagBeginPart1);
					meterReaderDropdown.append(meterReaderDetails
							.getMeterReaderID());
					meterReaderDropdown.append(optionTagBeginPart2);
					meterReaderDropdown.append(meterReaderDetails
							.getMeterReaderName());
					meterReaderDropdown.append(optionTagEnd);
				}
			}
			meterReaderDropdown.append(selectTagEnd);
			inputStream = new StringBufferInputStream(meterReaderDropdown
					.toString());
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
	 * Search of a meter reader and MRD mapping details.
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	public String searchMeterReaderMRDMapping() {
		AppLog.begin();
		try {
			StringBuffer resonseSB = new StringBuffer(512);
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderZone(selectedZone);
			/* Start: Commented by Matiur Rahman on 30-09-2015 */
			// List<MRDTaggingDetails> mrdTaggingDetailsList = MRDTaggingDAO
			// .getAllCurrentTaggingList(selectedZone);
			/* End: Commented by Matiur Rahman on 30-09-2015 */
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			List<MRDTaggingDetails> mrdTaggingDetailsList = MRDTaggingDAO
					.getAllCurrentTaggingList(selectedZone, selectedZROCode,
							selectedMRKeys,mrEmpId,mrHhdId);
			AppLog.debug("" + selectedZone + ">>" + selectedZROCode + ">>>"
					+ selectedMRKeys);
			/* End: Added by Matiur Rahman on 30-09-2015 */
			MRDTaggingDetails mrdTaggingDetails = null;
			if (null != mrdTaggingDetailsList
					&& mrdTaggingDetailsList.size() > 0) {
				resonseSB.append("<table class=\"table-grid\">");
				resonseSB.append("<tr>");
				resonseSB
						.append("<th align=\"center\" colspan=\"15\">Current MRD Tagging Status</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\">SL</th>");
				/* Start: Added by Matiur Rahman on 30-09-2015 */
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >ZONE</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >MR</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >Area</th>");
				resonseSB
						.append("<th width=\"5%\" rowspan=\"2\" >MRD Type</th>");
				/* End: Added by Matiur Rahman on 30-09-2015 */
				resonseSB
						.append("<th width=\"5%\" rowspan=\"2\" colspan=\"2\">MRD CODE</th>");
				resonseSB
						.append("<th width=\"50%\" rowspan=\"1\" colspan=\"3\">METER READER</th>");
				resonseSB
						.append("<th width=\"15%\" rowspan=\"1\" colspan=\"2\">TAGGED</th>");
				resonseSB
						.append("<th width=\"20%\" rowspan=\"1\" colspan=\"2\">EFFECTIVE</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\" rowspan=\"1\">ID</th>");
				resonseSB.append("<th width=\"15%\" rowspan=\"1\">NAME</th>");
				resonseSB
						.append("<th width=\"5%\" rowspan=\"1\">EMPLOYEE ID</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"1\">BY</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">ON</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">FROM</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">TO</th>");
				resonseSB.append("</tr>");
				for (int i = 0; i < mrdTaggingDetailsList.size(); i++) {
					mrdTaggingDetails = mrdTaggingDetailsList.get(i);
					meterReaderDetails = mrdTaggingDetails
							.getMeterReaderDetails();
					resonseSB
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					resonseSB.append("<td align=\"center\">" + (i + 1)
							+ "</td>");
					/* Start: Added by Matiur Rahman on 30-09-2015 */
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getZone() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getMr() + "</td>");
					resonseSB.append("<td align=\"left\" nowrap>"
							+ mrdTaggingDetails.getArea() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getType() + "</td>");
					/* End: Added by Matiur Rahman on 30-09-2015 */
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getMrkey() + "</td>");
					resonseSB
							.append("<td align=\"center\" width=\"2%\"><input type=\"checkbox\" id=\""
									+ mrdTaggingDetails.getMrkey()
									+ "\" class=\"case\" name=\"case\"></td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderID() + "</td>");
					resonseSB
							.append("<td align=\"left\">"
									+ meterReaderDetails.getMeterReaderName()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmployeeID()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getCreatedBy() + "</td>");
					resonseSB.append("<td align=\"center\" nowrap>"
							+ mrdTaggingDetails.getCreatedOn() + "</td>");
					resonseSB.append("<td align=\"center\" nowrap>"
							+ mrdTaggingDetails.getEffectiveFrom() + "</td>");
					resonseSB.append("<td align=\"center\" nowrap>"
							+ mrdTaggingDetails.getEffectiveTo() + "</td>");
					resonseSB.append("</tr>");
				}
				resonseSB.append("</table><br/>");
				resonseSB.append("<table>");
				resonseSB.append("<tr>");
				resonseSB
						.append("<td width=\"15%\" align=\"right\"><b>Meter Reader</b></td>");
				/* Start: Commented by Matiur Rahman on 30-09-2015 */
				// List<MeterReaderDetails> meterReaderList =
				// (ArrayList<MeterReaderDetails>) HHDMaintenanceDAO
				// .getMeterReaderList(selectedZone);
				/* End: Commented by Matiur Rahman on 30-09-2015 */
				/* Start: Added by Matiur Rahman on 30-09-2015 */
				List<MeterReaderDetails> meterReaderList = (ArrayList<MeterReaderDetails>) MRDTaggingDAO
						.getMeterReaderList(new MeterReaderDetails(
								selectedZROCode));
				/* End: Added by Matiur Rahman on 30-09-2015 */
				StringBuffer meterReaderDropdown = new StringBuffer();
				meterReaderDropdown
						.append("<select name=\"meterReaderID\" id=\"meterReaderID\" class=\"selectbox-long\">");
				meterReaderDropdown
						.append("<option value=''>Please Select</option>");
				if (null != meterReaderList && meterReaderList.size() > 0) {
					for (int i = 0; i < meterReaderList.size(); i++) {
						meterReaderDetails = (MeterReaderDetails) meterReaderList
								.get(i);
						meterReaderDropdown.append(optionTagBeginPart1);
						meterReaderDropdown.append(meterReaderDetails
								.getMeterReaderID());
						meterReaderDropdown.append(optionTagBeginPart2);
						meterReaderDropdown.append(meterReaderDetails
								.getMeterReaderName());
						meterReaderDropdown.append(optionTagEnd);
					}
				}
				meterReaderDropdown.append(selectTagEnd);
				resonseSB.append("<td width=\"10%\" id='mtrRdrTD'>"
						+ meterReaderDropdown.toString() + "</td>");
				resonseSB
						.append("<td width=\"2%\"><img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Processing\" style=\"display: none;\" id=\"imgMtrRdrSrc\" /></td>");
				resonseSB
						.append("<td width=\"20%\" ><input type=\"text\" name=\"mtrRdsSrc\" id=\"mtrRdsSrc\" size='10' class=\"textbox\" title='Enter Employee Id of the Meter Reader'/><input type=\"button\" name=\"btnGetMtrRdr\" id=\"btnGetMtrRdr\" value=\"GO\" class=\"smallbutton\" onclick=\"fnGetMtrRdr();\" /></td>");
				resonseSB
						.append("<td align=\"left\" ><input type=\"button\" name=\"btnTag\"	id=\"btnTag\" value=\" Tag Selected MRD(s) \" class=\"groovybutton\" onclick=\"fnTagMRD();\" /></td>");
				resonseSB
						.append("<td align=\"left\" ><input type=\"button\" name=\"btnUnTag\"	id=\"btnUnTag\" value=\" UnTag Selected MRD(s) \" class=\"groovybutton\" onclick=\"fnUnTagMRD();\" /></td>");
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
	 * Search of a meter reader and MRD mapping details from History table.
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	public String searchMeterReaderMRDMappingHistory() {
		AppLog.begin();
		try {
			StringBuffer resonseSB = new StringBuffer(512);
			MeterReaderDetails meterReaderDetails = new MeterReaderDetails();
			meterReaderDetails.setMeterReaderZone(selectedZone);
			/* Start: Commented by Matiur Rahman on 30-09-2015 */
			// List<MRDTaggingDetails> mrdTaggingDetailsList = MRDTaggingDAO
			// .getAllTaggingListHistory(selectedZone);
			/* End: Commented by Matiur Rahman on 30-09-2015 */
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			AppLog.debug("selectedZone>>" + selectedZone
					+ ">>selectedZROCode>>" + selectedZROCode
					+ ">>selectedMRKeys>>" + selectedMRKeys);
			List<MRDTaggingDetails> mrdTaggingDetailsList = MRDTaggingDAO
					.getAllTaggingListHistory(selectedZone, selectedZROCode,
							selectedMRKeys, mrEmpId, mrHhdId);
			/* End: Added by Matiur Rahman on 30-09-2015 */
			MRDTaggingDetails mrdTaggingDetails = null;
			if (null != mrdTaggingDetailsList
					&& mrdTaggingDetailsList.size() > 0) {
				resonseSB.append("<table class=\"table-grid\">");
				resonseSB.append("<tr>");
				resonseSB
						.append("<th align=\"center\" colspan=\"15\">MRD Tagging History</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\">SL</th>");
				/* Start: Added by Matiur Rahman on 30-09-2015 */
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >ZONE</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >MR</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"2\" >Area</th>");
				resonseSB
						.append("<th width=\"5%\" rowspan=\"2\" >MRD Type</th>");
				/* End: Added by Matiur Rahman on 30-09-2015 */
				resonseSB
						.append("<th width=\"5%\" rowspan=\"2\">MRD CODE</th>");
				resonseSB
						.append("<th width=\"30%\" rowspan=\"1\" colspan=\"3\">METER READER</th>");
				resonseSB
						.append("<th width=\"15%\" rowspan=\"1\" colspan=\"2\">TAGGED</th>");
				resonseSB
						.append("<th width=\"15%\" rowspan=\"1\" colspan=\"2\">UN TAGGED</th>");
				resonseSB
						.append("<th width=\"15%\" rowspan=\"1\" colspan=\"2\">EFFECTIVE</th>");
				resonseSB.append("</tr>");
				resonseSB.append("<tr>");
				resonseSB.append("<th width=\"5%\" rowspan=\"1\">ID</th>");
				resonseSB.append("<th width=\"20%\" rowspan=\"1\">NAME</th>");
				resonseSB
						.append("<th width=\"5%\" rowspan=\"1\">EMPLOYEE ID</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"1\">BY</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">ON</th>");
				resonseSB.append("<th width=\"5%\" rowspan=\"1\">BY</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">ON</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">FROM</th>");
				resonseSB.append("<th width=\"10%\" rowspan=\"1\">TO</th>");
				resonseSB.append("</tr>");
				for (int i = 0; i < mrdTaggingDetailsList.size(); i++) {
					mrdTaggingDetails = mrdTaggingDetailsList.get(i);
					meterReaderDetails = mrdTaggingDetails
							.getMeterReaderDetails();
					resonseSB
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					resonseSB.append("<td align=\"center\">" + (i + 1)
							+ "</td>");
					/* Start: Added by Matiur Rahman on 30-09-2015 */
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getZone() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getMr() + "</td>");
					resonseSB.append("<td align=\"left\" nowrap>"
							+ mrdTaggingDetails.getArea() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getType() + "</td>");
					/* End: Added by Matiur Rahman on 30-09-2015 */
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getMrkey() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderID() + "</td>");
					resonseSB
							.append("<td align=\"left\">"
									+ meterReaderDetails.getMeterReaderName()
									+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ meterReaderDetails.getMeterReaderEmployeeID()
							+ "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getCreatedBy() + "</td>");
					resonseSB.append("<td align=\"center\" >"
							+ mrdTaggingDetails.getCreatedOn() + "</td>");
					resonseSB.append("<td align=\"center\">"
							+ mrdTaggingDetails.getUpdatedBy() + "</td>");
					resonseSB.append("<td align=\"center\" >"
							+ mrdTaggingDetails.getUpdatedOn() + "</td>");
					resonseSB.append("<td align=\"center\" >"
							+ mrdTaggingDetails.getEffectiveFrom() + "</td>");
					resonseSB.append("<td align=\"center\" >"
							+ mrdTaggingDetails.getEffectiveTo() + "</td>");
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
	 * @param meterReaderID
	 *            the meterReaderID to set
	 */
	public void setMeterReaderID(String meterReaderID) {
		this.meterReaderID = meterReaderID;
	}

	/**
	 * @param mrEmpId
	 */
	public void setMrEmpId(String mrEmpId) {
		this.mrEmpId = mrEmpId;
	}

	/**
	 * @param mrHhdId
	 */
	public void setMrHhdId(String mrHhdId) {
		this.mrHhdId = mrHhdId;
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
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
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
	 * Tag meter reader with the selected MRDs.It creates a history first then
	 * tags meter reader with the selected MRDs.
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	public String tagMRD() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			/* Start:Comented by Matiur Rahman on 30-09-2015 */
			// String listOfMRKeys = "";
			// if (null != selectedMRKeys) {
			// listOfMRKeys = selectedMRKeys.replaceFirst(",", "");
			// }
			/* End:Comented by Matiur Rahman on 30-09-2015 */
			/* Create history of the current tagging details */
			MRDTaggingDAO
					.createMRDTaggingDetailsHistory(selectedMRKeys, userId);
			/* Remove current tagging */
			MRDTaggingDAO.deleteMRDTaggingDetails(selectedMRKeys);
			String mrKeyArray[] = selectedMRKeys.split(",");
			String insertSuccess = "";
			for (int i = 0; i < mrKeyArray.length; i++) {
				String mrKey = mrKeyArray[i];
				if (MRDTaggingDAO.tagMRD(mrKey, meterReaderID, userId)) {
					insertSuccess += "," + mrKey;
				}
			}
			StringBuffer resonseSB = new StringBuffer(512);
			if (("," + selectedMRKeys).equals(insertSuccess)) {
				resonseSB
						.append("<font color='green' size='2'><b>Selected MRD(s) successfully Tagged with the Selected Meter Reader.</b></font>");
			} else {
				resonseSB
						.append("<font color='orange' size='2'><b>Some of the Selected MRD(s) could not be Tagged with the Selected Meter Reader.</b></font>");
			}
			if ("".equals(insertSuccess)) {
				resonseSB
						.append("<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Tagging with the Selected Meter Reader.</b></font>");
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
	 * Tag MRDs to default meter reader for the selected MRDs while un-tagged.
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	public String tagMRDToDeafault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			// String listOfMRKeys = "";
			// if (null != selectedMRKeys) {
			// listOfMRKeys = selectedMRKeys.replaceFirst(",", "");
			// }
			String mrKeyArray[] = selectedMRKeys.split(",");
			String insertSuccess = "";
			for (int i = 0; i < mrKeyArray.length; i++) {
				String mrKey = mrKeyArray[i];
				if (MRDTaggingDAO.tagMRDToDeafault(mrKey, DEFAULT_METER_READER,
						selectedZROCode, userId)) {
					insertSuccess += "," + mrKey;
				}
			}
			StringBuffer resonseSB = new StringBuffer(512);
			if (("," + selectedMRKeys).equals(insertSuccess)) {
				resonseSB
						.append("<font color='green' size='2'><b>Selected MRD(s) successfully Untagged and Tagged with default Meter Reader DJB.</b></font>");
			} else {
				resonseSB
						.append("<font color='orange' size='2'><b>Some of the Selected MRD(s) could not be Tagged with the Selected Meter Reader.</b></font>");
			}
			if ("".equals(insertSuccess)) {
				resonseSB
						.append("<font color='red' size='2'><li><span><b>Sorry, There was problem while Tagging with the Selected Meter Reader.</b></font>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
		} catch (SQLIntegrityConstraintViolationException e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>Sorry, There was problem while Tagging with the Default Meter Reader as there is No Default Meter Reader named "
							+ DEFAULT_METER_READER
							+ " for the selected ZRO Location "
							+ selectedZROCode + "</b></font>");
			AppLog.error(e);
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
	 * Un tag MRDs from current meter reader and tag to Defualt Meter Reader
	 * DJB.It creates a history first then delete the record.
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 */
	public String unTagMRD() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			// String listOfMRKeys = "";
			// if (null != selectedMRKeys) {
			// listOfMRKeys = selectedMRKeys.replaceFirst(",", "");
			// }
			/* Create history of the current tagging details */

			MeterReaderDetails meterReaderDetails = new MeterReaderDetails(
					selectedZROCode);
			meterReaderDetails.setMeterReaderName(DEFAULT_METER_READER);
			List<MeterReaderDetails> meterReaderList = (ArrayList<MeterReaderDetails>) MRDTaggingDAO
					.getMeterReaderList(meterReaderDetails);
			StringBuffer resonseSB = new StringBuffer(512);
			if (null != meterReaderList && meterReaderList.size() > 0) {
				AppLog.info("Creating MRD Tagging Details History");
				MRDTaggingDAO.createMRDTaggingDetailsHistory(selectedMRKeys,
						userId);
				AppLog.info("Deleting MRD Tagging Details");
				if (MRDTaggingDAO.deleteMRDTaggingDetails(selectedMRKeys)) {
					resonseSB
							.append("<font color='green' size='2'><b>Selected MRD(s) Successfully Untagged from the Meter Reader.</b></font>");
					/* After un-tagging tag the MRD to default Meter Reader */
					AppLog.end();
					return tagMRDToDeafault();
				} else {
					AppLog.info("Nothing Deleted from MRD Tagging Details");
					resonseSB
							.append("<font color='red' size='2'><b>Selected MRD(s) could not be Untagged from the Meter Reader.</b></font>");
				}
			} else {
				resonseSB
						.append("<font color='red' size='2'><li><span><b>Selected MRD(s) could not be Untagged from the Meter Reader as there is No Default Meter Reader named "
								+ DEFAULT_METER_READER
								+ " for the selected ZRO Location "
								+ selectedZROCode
								+ ", Please add a Meter Reader Named "
								+ DEFAULT_METER_READER
								+ " for the selected ZRO Location "
								+ selectedZROCode
								+ " first and proceed. </b></font>");
			}
			inputStream = new StringBufferInputStream(resonseSB.toString());
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}
}
