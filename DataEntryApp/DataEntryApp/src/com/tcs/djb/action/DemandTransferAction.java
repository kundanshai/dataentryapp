/************************************************************************************************************
 * @(#) DemandTransferAction.java   08-May-2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.DemandTransferDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.ProcessDemandTransferDAO;
import com.tcs.djb.model.DemandTransferDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Demand Transfer screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 08-05-2013
 * @history 21-06-2013 Matiur Rahman changes made as per Updated JTrac
 *          #DJB-1535. When any MRD is present in CM_MRD_PROCESSING_STAT in
 *          status 10 then it will not be available for Demand Transfer (Neither
 *          in old MRD list nor in New MRD list.
 * @history 14-DEC-2015 Aniket Chatterjee(Tata Consultancy Services) modified
 *          method {@link #transferDemand} Aniket Chatterjee modified this to
 *          fix the issue while transferring a KNO from one ZRO location to
 *          another ZRO location that doesn't contain any active meter reader
 *          named 'DJB' Or Contains More Than One Active Meter Reader Named
 *          'DJB'. Aniket Chatterjee modified this as per JTrac DJB-4185 &
 *          DJB-4280.
 * 
 */
@SuppressWarnings("deprecation")
public class DemandTransferAction extends ActionSupport implements
		ServletResponseAware {

	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";

	private static final String optionTagEnd = "</option>";

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "23";

	private static final String selectTagEnd = "</select>";

	private static final long serialVersionUID = 1L;
	/**
	 * demand Transfer Details List.
	 */
	List<DemandTransferDetails> demandTransferDetailsList;
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
	 * open Bill Round For New MRKEY
	 */
	private String openBillRoundForMRKEYNew;
	/**
	 * open Bill Round For Old MRKEY
	 */
	private String openBillRoundForMRKEYOld;

	/**
	 * Current Page no.
	 */
	private String pageNo;

	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;

	/**
	 * KNO removed.
	 */
	private String removedKNO;

	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	/**
	 * Area Drop down.
	 */
	private String selectedArea;

	private String selectedAreaNew;

	private String selectedKNO;

	List<String> selectedKNOList;

	/**
	 * MRKEY Drop down.
	 */
	private String selectedMRKEY;

	private String selectedMRKEYNew;

	/**
	 * MR No Drop down.
	 */
	private String selectedMRNo;

	private String selectedMRNoNew;

	/**
	 * Zone No Drop down.
	 */
	private String selectedZone;

	private String selectedZoneNew;

	private String selectedZROCode;
	private String selectedZROCodeNew;

	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;

	private String totalRecordsSelected;
	private String transferedBy;

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
			// if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
			// addActionError(getText("error.access.denied"));
			// inputStream = ScreenAccessValidator
			// .ajaxResponse(getText("error.access.denied"));
			// AppLog.end();
			// return "success";
			// }
			if ("populateMRNo".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(selectedZone);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedMRNo\" id=\"selectedMRNo\" class=\"selectbox\" onfocus=\"fnCheckZone();\" onchange=\"fnGetArea();\">");
				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(null == entry.getKey() ? "" : entry
								.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(null == entry.getValue() ? "" : entry
								.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("MRNoListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("populateMRNoNew".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(selectedZoneNew);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedMRNoNew\" id=\"selectedMRNoNew\" class=\"selectbox\" onfocus=\"fnCheckZoneNew();\" onchange=\"fnGetAreaNew();\">");
				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(null == entry.getKey() ? "" : entry
								.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(null == entry.getValue() ? "" : entry
								.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("MRNoListMapNew", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("populateArea".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getAreaListMap(selectedZone, selectedMRNo);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedArea\" id=\"selectedArea\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo();\" onchange=\"fnGetMRKEY();\">");

				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(null == entry.getKey() ? "" : entry
								.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(null == entry.getValue() ? "" : entry
								.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("AreaListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("populateAreaNew".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getAreaListMap(selectedZoneNew, selectedMRNoNew);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedAreaNew\" id=\"selectedAreaNew\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNoNew();\" onchange=\"fnGetMRKEYNew();\">");

				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(null == entry.getKey() ? "" : entry
								.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(null == entry.getValue() ? "" : entry
								.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("AreaListMapNew", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("populateMRKEY".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRKEYByZoneMRArea(selectedZone, selectedMRNo,
								selectedArea);
				StringBuffer responseSB = new StringBuffer(512);
				if (null != returnMap) {
					responseSB.append("<MRKE>");
					responseSB.append(returnMap.get("mrKey"));
					responseSB.append("</MRKE>");
					Map<String, String> zroListMap = GetterDAO.getZRO(
							selectedZone, selectedMRNo, selectedArea, null);
					StringBuffer dropDownSB = new StringBuffer(512);
					responseSB.append("<ZROC>");
					dropDownSB
							.append("<select name=\"selectedZROCode\" id=\"selectedZROCode\" class=\"selectbox-long\" disabled=\"true\">");

					if (null != zroListMap && !zroListMap.isEmpty()) {
						for (Entry<String, String> entry : zroListMap
								.entrySet()) {
							dropDownSB.append(optionTagBeginPart1);
							dropDownSB.append(null == entry.getKey() ? ""
									: entry.getKey());
							dropDownSB.append(optionTagBeginPart2);
							dropDownSB.append(null == entry.getValue() ? ""
									: entry.getValue());
							dropDownSB.append(optionTagEnd);
						}
					}
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</ZROC>");
					responseSB.append("<OPBR>");
					responseSB
							.append(GetterDAO
									.getOpenBillRoundForAnMRKey(returnMap
											.get("mrKey")));
					responseSB.append("</OPBR>");
					session.put("ZROListMap", zroListMap);
					inputStream = new StringBufferInputStream(responseSB
							.toString());
				} else {
					inputStream = new StringBufferInputStream("ERROR:");
				}
				AppLog.end();
				return SUCCESS;
			}
			if ("populateMRKEYNew".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRKEYByZoneMRArea(selectedZoneNew, selectedMRNoNew,
								selectedAreaNew);
				StringBuffer responseSB = new StringBuffer(512);
				if (null != returnMap) {
					responseSB.append("<MRKE>");
					responseSB.append(returnMap.get("mrKey"));
					responseSB.append("</MRKE>");
					Map<String, String> zroListMap = GetterDAO.getZRO(
							selectedZoneNew, selectedMRNoNew, selectedAreaNew,
							null);
					StringBuffer dropDownSB = new StringBuffer(512);
					responseSB.append("<ZROC>");
					dropDownSB
							.append("<select name=\"selectedZROCodeNew\" id=\"selectedZROCodeNew\" class=\"selectbox-long\" disabled=\"true\">");

					if (null != zroListMap && !zroListMap.isEmpty()) {
						for (Entry<String, String> entry : zroListMap
								.entrySet()) {
							dropDownSB.append(optionTagBeginPart1);
							dropDownSB.append(null == entry.getKey() ? ""
									: entry.getKey());
							dropDownSB.append(optionTagBeginPart2);
							dropDownSB.append(null == entry.getValue() ? ""
									: entry.getValue());
							dropDownSB.append(optionTagEnd);
						}
					}
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</ZROC>");
					responseSB.append("<OPBR>");
					responseSB
							.append(GetterDAO
									.getOpenBillRoundForAnMRKey(returnMap
											.get("mrKey")));
					responseSB.append("</OPBR>");
					session.put("ZROListMapNew", zroListMap);
					inputStream = new StringBufferInputStream(responseSB
							.toString());
				} else {
					inputStream = new StringBufferInputStream("ERROR:");
				}
				AppLog.end();
				return SUCCESS;
			}
			if ("populateZoneMRAreaByMRKEY".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getZoneMRAreaByMRKEY(selectedMRKEY);
				StringBuffer responseSB = new StringBuffer(512);
				if (null != returnMap) {
					responseSB.append("<ZONE>");
					responseSB.append((String) returnMap.get("zone"));
					responseSB.append("</ZONE>");
					StringBuffer dropDownSB = new StringBuffer(512);
					responseSB.append("<MRNO>");
					dropDownSB
							.append("<select name=\"selectedMRNo\" id=\"selectedMRNo\" class=\"selectbox\" onfocus=\"fnCheckZone();\" onchange=\"fnGetArea();\">");
					dropDownSB.append("<option value='"
							+ (String) returnMap.get("mrNo") + "'>"
							+ (String) returnMap.get("mrNo") + "</option>");
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</MRNO>");
					Map<String, String> mrNoListMap = new HashMap<String, String>();
					mrNoListMap.put((String) returnMap.get("mrNo"),
							(String) returnMap.get("mrNo"));
					session.put("MRNoListMap", mrNoListMap);
					dropDownSB = new StringBuffer(512);
					responseSB.append("<AREA>");
					dropDownSB
							.append("<select name=\"selectedArea\" id=\"selectedArea\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo();\" >");

					dropDownSB.append("<option value='"
							+ (String) returnMap.get("area") + "'>"
							+ (String) returnMap.get("areaDesc") + "</option>");
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</AREA>");
					Map<String, String> areaListMap = new HashMap<String, String>();
					areaListMap.put((String) returnMap.get("area"),
							(String) returnMap.get("areaDesc"));
					session.put("AreaListMap", areaListMap);
					Map<String, String> zroListMap = GetterDAO.getZRO(null,
							null, null, selectedMRKEY);
					dropDownSB = new StringBuffer(512);
					responseSB.append("<ZROC>");
					dropDownSB
							.append("<select name=\"selectedZROCode\" id=\"selectedZROCode\" class=\"selectbox-long\" disabled=\"true\">");

					if (null != zroListMap && !zroListMap.isEmpty()) {
						for (Entry<String, String> entry : zroListMap
								.entrySet()) {
							dropDownSB.append(optionTagBeginPart1);
							dropDownSB.append(null == entry.getKey() ? ""
									: entry.getKey());
							dropDownSB.append(optionTagBeginPart2);
							dropDownSB.append(null == entry.getValue() ? ""
									: entry.getValue());
							dropDownSB.append(optionTagEnd);
						}
					}
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</ZROC>");
					responseSB.append("<OPBR>");
					responseSB.append(GetterDAO
							.getOpenBillRoundForAnMRKey(selectedMRKEY));
					responseSB.append("</OPBR>");
					session.put("ZROListMap", zroListMap);

				} else {
					responseSB.append("ERROR:");
				}
				inputStream = new StringBufferInputStream(responseSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("populateZoneMRAreaByMRKEYNew".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getZoneMRAreaByMRKEY(selectedMRKEYNew);
				StringBuffer responseSB = new StringBuffer(512);
				if (null != returnMap) {
					responseSB.append("<ZONE>");
					responseSB.append((String) returnMap.get("zone"));
					responseSB.append("</ZONE>");
					StringBuffer dropDownSB = new StringBuffer(512);
					responseSB.append("<MRNO>");
					dropDownSB
							.append("<select name=\"selectedMRNoNew\" id=\"selectedMRNoNew\" class=\"selectbox\" onfocus=\"fnCheckZoneNew();\" onchange=\"fnGetAreaNew();\">");
					dropDownSB.append("<option value='"
							+ (String) returnMap.get("mrNo") + "'>"
							+ (String) returnMap.get("mrNo") + "</option>");
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</MRNO>");
					Map<String, String> mrNoListMap = new HashMap<String, String>();
					mrNoListMap.put((String) returnMap.get("mrNo"),
							(String) returnMap.get("mrNo"));
					// session.put("MRNoListMap", mrNoListMap);
					dropDownSB = new StringBuffer(512);
					responseSB.append("<AREA>");
					dropDownSB
							.append("<select name=\"selectedAreaNew\" id=\"selectedAreaNew\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNoNew();\" >");

					dropDownSB.append("<option value='"
							+ (String) returnMap.get("area") + "'>"
							+ (String) returnMap.get("areaDesc") + "</option>");
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</AREA>");
					Map<String, String> areaListMap = new HashMap<String, String>();
					areaListMap.put((String) returnMap.get("area"),
							(String) returnMap.get("areaDesc"));
					// session.put("AreaListMap", areaListMap);
					Map<String, String> zroListMap = GetterDAO.getZRO(null,
							null, null, selectedMRKEYNew);
					dropDownSB = new StringBuffer(512);
					responseSB.append("<ZROC>");
					dropDownSB
							.append("<select name=\"selectedZROCodeNew\" id=\"selectedZROCodeNew\" class=\"selectbox-long\" disabled=\"true\">");
					if (null != zroListMap && !zroListMap.isEmpty()) {
						for (Entry<String, String> entry : zroListMap
								.entrySet()) {
							dropDownSB.append(optionTagBeginPart1);
							dropDownSB.append(null == entry.getKey() ? ""
									: entry.getKey());
							dropDownSB.append(optionTagBeginPart2);
							dropDownSB.append(null == entry.getValue() ? ""
									: entry.getValue());
							dropDownSB.append(optionTagEnd);
						}
					}
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					responseSB.append("</ZROC>");
					responseSB.append("<OPBR>");
					responseSB.append(GetterDAO
							.getOpenBillRoundForAnMRKey(selectedMRKEYNew));
					responseSB.append("</OPBR>");
					session.put("ZROListMapNew", zroListMap);
				} else {
					responseSB.append("ERROR:");
				}
				inputStream = new StringBufferInputStream(responseSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}
			if ("updateSelectedKNOList".equalsIgnoreCase(hidAction)) {
				updateSelectedKNOList();
				inputStream = new StringBufferInputStream("SUCCESS");
			}
			if ("transferDemand".equalsIgnoreCase(hidAction)) {
				updateSelectedKNOList();
				String transferDemandResponse = transferDemand();
				inputStream = new StringBufferInputStream(
						transferDemandResponse);
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
			/** Load Default Values. */
			loadDefault();
			// System.out.println("hidAction::" + hidAction +
			// "::selectedMRKEY::"
			// + selectedMRKEY + "::selectedZone::" + selectedZone
			// + "::selectedMRNo::" + selectedMRNo + "::selectedArea::"
			// + selectedArea + "::selectedZROCode::" + selectedZROCode);
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
				session.remove("selectedKNOMap");
				selectedKNOList = null;
				this.selectedKNO = "";
				this.removedKNO = "";
				session.put("ZROListMap", new HashMap<String, String>());
				session.put("ZROListMapNew", new HashMap<String, String>());
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "Next".equalsIgnoreCase(hidAction)
					|| "Prev".equalsIgnoreCase(hidAction)
					|| "refresh".equalsIgnoreCase(hidAction)) {
				if ("search".equalsIgnoreCase(hidAction)) {
					session.remove("selectedKNOMap");
					session
							.put("selectedKNOMap",
									new HashMap<String, String>());
					session.put("ZROListMapNew", new HashMap<String, String>());
					selectedKNOList = null;
					this.selectedKNO = "";
					this.removedKNO = "";
					session.remove("SERVER_MESSAGE");
				} else {
					updateSelectedKNOList();
				}
				/** Search Demand. */
				result = searchDemand();
			}
			if ("searchTransfered".equalsIgnoreCase(hidAction)
					|| "TransferedNext".equalsIgnoreCase(hidAction)
					|| "TransferedPrev".equalsIgnoreCase(hidAction)) {
				result = searchTransferedDemand();
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
	 * @return the demandTransferDetailsList
	 */
	public List<DemandTransferDetails> getDemandTransferDetailsList() {
		return demandTransferDetailsList;
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
	 * @return the openBillRoundForMRKEYNew
	 */
	public String getOpenBillRoundForMRKEYNew() {
		return openBillRoundForMRKEYNew;
	}

	/**
	 * @return the openBillRoundForMRKEYOld
	 */
	public String getOpenBillRoundForMRKEYOld() {
		return openBillRoundForMRKEYOld;
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
	 * @return the removedKNO
	 */
	public String getRemovedKNO() {
		return removedKNO;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
	}

	/**
	 * @return the selectedAreaNew
	 */
	public String getSelectedAreaNew() {
		return selectedAreaNew;
	}

	/**
	 * @return the selectedKNO
	 */
	public String getSelectedKNO() {
		return selectedKNO;
	}

	/**
	 * @return the selectedKNOList
	 */
	public List<String> getSelectedKNOList() {
		return selectedKNOList;
	}

	/**
	 * @return the selectedMRKEY
	 */
	public String getSelectedMRKEY() {
		return selectedMRKEY;
	}

	/**
	 * @return the selectedMRKEYNew
	 */
	public String getSelectedMRKEYNew() {
		return selectedMRKEYNew;
	}

	/**
	 * @return the selectedMRNo
	 */
	public String getSelectedMRNo() {
		return selectedMRNo;
	}

	/**
	 * @return the selectedMRNoNew
	 */
	public String getSelectedMRNoNew() {
		return selectedMRNoNew;
	}

	/**
	 * @return the selectedZone
	 */
	public String getSelectedZone() {
		return selectedZone;
	}

	/**
	 * @return the selectedZoneNew
	 */
	public String getSelectedZoneNew() {
		return selectedZoneNew;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * @return the selectedZROCodeNew
	 */
	public String getSelectedZROCodeNew() {
		return selectedZROCodeNew;
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
	 * @return the transferedBy
	 */
	public String getTransferedBy() {
		return transferedBy;
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
			session.put("CURR_TAB", "MRD");
			if (null == session.get("MRKEYListMap")) {
				session.put("MRKEYListMap", GetterDAO.getMRKEYListMap());
			}
			if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			/** For Old MRD Details. */
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
							.isEmpty()) {
				session.put("ZROListMap", new HashMap<String, String>());
			}
			/** For New MRD Details. */
			if (null == session.get("MRNoListMapNew")
					|| ((HashMap<String, String>) session.get("MRNoListMapNew"))
							.isEmpty()) {
				if (null != selectedZoneNew && !"".equals(selectedZoneNew)) {
					session.put("MRNoListMapNew",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(selectedZoneNew));
				} else {
					session
							.put("MRNoListMapNew",
									new HashMap<String, String>());
				}
			}
			if (null == session.get("AreaListMapNew")
					|| ((HashMap<String, String>) session.get("AreaListMapNew"))
							.isEmpty()) {
				if (null != selectedZoneNew && !"".equals(selectedZoneNew)
						&& null != selectedMRNoNew
						&& !"".equals(selectedMRNoNew)) {
					session.put("AreaListMapNew",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									selectedZoneNew, selectedMRNoNew));
				} else {
					session
							.put("AreaListMapNew",
									new HashMap<String, String>());
				}
			}
			if (null == session.get("ZROListMapNew")
					|| ((HashMap<String, String>) session.get("ZROListMapNew"))
							.isEmpty()) {
				session.put("ZROListMapNew", new HashMap<String, String>());
			}
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Run transfer Demand Procedure in Thread.
	 * <p>
	 * 
	 */
	public void runProcedure(List<String> sortedKNOList) {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			// Map<String, String> selectedKNOMap = (HashMap<String, String>)
			// session
			// .get("selectedKNOMap");
			// List<String> selectedKNOList = DemandTransferDAO
			// .getSortedKNOListForDemandTransfer(selectedKNOMap);
			if (null != sortedKNOList && sortedKNOList.size() > 0) {
				/** Calling PROC_DMND_TRNSFR in New Thread. */
				new ProcessDemandTransferDAO(sortedKNOList, selectedMRKEY,
						selectedZone, selectedMRNo, selectedArea,
						selectedMRKEYNew, selectedZoneNew, selectedMRNoNew,
						selectedAreaNew, selectedZROCodeNew, userId);
			}
			this.selectedKNO = "";
			this.removedKNO = "";
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Search Demands For Demand Transfer.
	 * </p>
	 * 
	 * @return
	 */
	public String searchDemand() {
		String result = "success";
		try {
			if (null == this.pageNo || "".equals(this.pageNo)) {
				this.pageNo = "1";
				this.maxRecordPerPage = PropertyUtil
						.getProperty("RECORDS_PER_PAGE");
			}
			this.totalRecords = DemandTransferDAO
					.getTotalCountOfConsumerDetailsListForDemandTransfer(
							selectedZone, selectedMRNo, selectedArea,
							selectedMRKEY);
			// System.out.println("selectedZone::" + selectedZone
			// + "::selectedMRNo::" + selectedMRNo + "::selectedArea::"
			// + selectedArea + "::selectedZROCode::" + selectedZROCode
			// + "::totalRecords::" + totalRecords + "::pageNo::" + pageNo
			// + "::maxRecordPerPage::" + maxRecordPerPage);
			if (totalRecords > 0) {
				if (Integer.parseInt(maxRecordPerPage)
						* Integer.parseInt(pageNo) > totalRecords) {
					this.pageNo = Integer.toString((int) totalRecords
							/ Integer.parseInt(maxRecordPerPage) + 1);
				}
				List<String> pageNoList = new ArrayList<String>();
				List<String> maxPageNoList = new ArrayList<String>();
				for (int i = 0, j = 1; i < totalRecords; i++) {
					if (i % Integer.parseInt(maxRecordPerPage) == 0) {
						pageNoList.add(Integer.toString(j++));
						if (i != 0 && i <= 200) {
							maxPageNoList.add(Integer.toString(i));
						}
					}
				}
				this.pageNoDropdownList = pageNoList;
				this.demandTransferDetailsList = (ArrayList<DemandTransferDetails>) DemandTransferDAO
						.getConsumerDetailsListForDemandTransfer(selectedZone,
								selectedMRNo, selectedArea, selectedMRKEY,
								pageNo, maxRecordPerPage);
				result = "success";
			}
		} catch (Exception e) {
			AppLog.error(e);
			addActionError(getText("error.login.expired"));
			return "login";
		}
		AppLog.end();
		return result;
	}

	/**
	 * <p>
	 * Search for Transfered Demand.
	 * </p>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String searchTransferedDemand() {
		String result = "success";
		try {
			if ("searchTransfered".equalsIgnoreCase(hidAction)) {
				this.pageNo = "1";
			}
			if (null == this.maxRecordPerPage
					|| "".equals(this.maxRecordPerPage)) {
				this.maxRecordPerPage = PropertyUtil
						.getProperty("RECORDS_PER_PAGE");
			}
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = null;
			if (null == transferedBy
					|| "View Transfered By Me".equalsIgnoreCase(transferedBy)) {
				userId = (String) session.get("userId");
			}
			Map<String, String> selectedKNOMap = null;
			if (!"View All".equalsIgnoreCase(transferedBy)) {
				selectedKNOMap = (HashMap<String, String>) session
						.get("selectedKNOMap");
			}
			// if (null != selectedKNOMap && !selectedKNOMap.isEmpty()) {
			this.totalRecords = DemandTransferDAO
					.getTotalCountOfDemandTransfered(selectedKNOMap, userId);
			if (totalRecords > 0) {
				if (Integer.parseInt(maxRecordPerPage)
						* Integer.parseInt(pageNo) > totalRecords) {
					this.pageNo = Integer.toString((int) totalRecords
							/ Integer.parseInt(maxRecordPerPage) + 1);
				}
				List<String> pageNoList = new ArrayList<String>();
				List<String> maxPageNoList = new ArrayList<String>();
				for (int i = 0, j = 1; i < totalRecords; i++) {
					if (i % Integer.parseInt(maxRecordPerPage) == 0) {
						pageNoList.add(Integer.toString(j++));
						if (i != 0 && i <= 200) {
							maxPageNoList.add(Integer.toString(i));
						}
					}
				}
				this.pageNoDropdownList = pageNoList;
				this.demandTransferDetailsList = (ArrayList<DemandTransferDetails>) DemandTransferDAO
						.getConsumerDetailsListForDemandTransfered(
								selectedKNOMap, userId, pageNo,
								maxRecordPerPage);
				result = "success";
			}
			// }
		} catch (Exception e) {
			AppLog.error(e);
			addActionError(getText("error.login.expired"));
			return "login";
		}
		AppLog.end();
		return result;
	}

	/**
	 * @param demandTransferDetailsList
	 *            the demandTransferDetailsList to set
	 */
	public void setDemandTransferDetailsList(
			List<DemandTransferDetails> demandTransferDetailsList) {
		this.demandTransferDetailsList = demandTransferDetailsList;
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
	 * @param openBillRoundForMRKEYNew
	 *            the openBillRoundForMRKEYNew to set
	 */
	public void setOpenBillRoundForMRKEYNew(String openBillRoundForMRKEYNew) {
		this.openBillRoundForMRKEYNew = openBillRoundForMRKEYNew;
	}

	/**
	 * @param openBillRoundForMRKEYOld
	 *            the openBillRoundForMRKEYOld to set
	 */
	public void setOpenBillRoundForMRKEYOld(String openBillRoundForMRKEYOld) {
		this.openBillRoundForMRKEYOld = openBillRoundForMRKEYOld;
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
	 * @param removedKNO
	 *            the removedKNO to set
	 */
	public void setRemovedKNO(String removedKNO) {
		this.removedKNO = removedKNO;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedArea
	 *            the selectedArea to set
	 */
	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

	/**
	 * @param selectedAreaNew
	 *            the selectedAreaNew to set
	 */
	public void setSelectedAreaNew(String selectedAreaNew) {
		this.selectedAreaNew = selectedAreaNew;
	}

	/**
	 * @param selectedKNO
	 *            the selectedKNO to set
	 */
	public void setSelectedKNO(String selectedKNO) {
		this.selectedKNO = selectedKNO;
	}

	/**
	 * @param selectedKNOList
	 *            the selectedKNOList to set
	 */
	public void setSelectedKNOList(List<String> selectedKNOList) {
		this.selectedKNOList = selectedKNOList;
	}

	/**
	 * @param selectedMRKEY
	 *            the selectedMRKEY to set
	 */
	public void setSelectedMRKEY(String selectedMRKEY) {
		this.selectedMRKEY = selectedMRKEY;
	}

	/**
	 * @param selectedMRKEYNew
	 *            the selectedMRKEYNew to set
	 */
	public void setSelectedMRKEYNew(String selectedMRKEYNew) {
		this.selectedMRKEYNew = selectedMRKEYNew;
	}

	/**
	 * @param selectedMRNo
	 *            the selectedMRNo to set
	 */
	public void setSelectedMRNo(String selectedMRNo) {
		this.selectedMRNo = selectedMRNo;
	}

	/**
	 * @param selectedMRNoNew
	 *            the selectedMRNoNew to set
	 */
	public void setSelectedMRNoNew(String selectedMRNoNew) {
		this.selectedMRNoNew = selectedMRNoNew;
	}

	/**
	 * @param selectedZone
	 *            the selectedZone to set
	 */
	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
	}

	/**
	 * @param selectedZoneNew
	 *            the selectedZoneNew to set
	 */
	public void setSelectedZoneNew(String selectedZoneNew) {
		this.selectedZoneNew = selectedZoneNew;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/**
	 * @param selectedZROCodeNew
	 *            the selectedZROCodeNew to set
	 */
	public void setSelectedZROCodeNew(String selectedZROCodeNew) {
		this.selectedZROCodeNew = selectedZROCodeNew;
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
	 * @param transferedBy
	 *            the transferedBy to set
	 */
	public void setTransferedBy(String transferedBy) {
		this.transferedBy = transferedBy;
	}

	/**
	 * <p>
	 * Initiate transfer Demand.
	 * <p>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String transferDemand() {
		String transferDemandResponse = "SUCCESS";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			Map<String, String> selectedKNOMap = (HashMap<String, String>) session
					.get("selectedKNOMap");
			// Change Started By Aniket Chatterjee.For More Please Refer To
			// History.
			int getRow = GetterDAO.getUserExistenceCount(selectedZROCodeNew);
			AppLog.info("No.Of 'DJB' named meter reader:: " + getRow);
			if (getRow > 0) {
				if (getRow == 1) {
					List<String> sortedKNOList = DemandTransferDAO
							.getSortedKNOListForDemandTransfer(selectedKNOMap);
					if (null != selectedKNOMap && null != sortedKNOList
							&& sortedKNOList.size() != selectedKNOMap.size()) {
						transferDemandResponse = "WARNING: Out of "
								+ selectedKNOMap.size()
								+ " selected Demand(s) "
								+ (selectedKNOMap.size() - sortedKNOList.size())
								+ " Demand(s) are already in Process, Please Search again and Retry or Click View Log/History for All for details.";
					} else {
						int rowsInserted = 0;
						if (null != sortedKNOList && sortedKNOList.size() > 0) {
							for (int i = 0; i < sortedKNOList.size(); i++) {
								String kno = sortedKNOList.get(i);
								if (null != kno && kno.trim().length() == 10) {
									rowsInserted += DemandTransferDAO
											.initiateTransferDemand(kno,
													selectedMRKEYNew, userId);
								}
							}
						}
						if (rowsInserted > 0) {
							session
									.put(
											"SERVER_MESSAGE",
											"<font color='#33CC33'><b>Selected Demand(s) Transfer Process initiated Successfully. Click Refresh for Final Status.</b></font>");
							transferDemandResponse = "<font color='#33CC33'><b>Selected Demand(s) Transfer Process initiated Successfully. Click Refresh for Final Status.</b></font>";
							runProcedure(sortedKNOList);
						} else {
							transferDemandResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
						}
					}
					session.remove("selectedKNOMap");
					this.selectedKNO = "";
					this.removedKNO = "";
					this.selectedKNOList = null;
					this.totalRecordsSelected = "0";
				} else {
					transferDemandResponse = "<font color='red' size='2'><span><b>ERROR:MORETHANONEDJBMTRRDR.</b></font>";
				}
			} else {
				transferDemandResponse = "<font color='red' size='2'><span><b>ERROR:NODJBMTRRDR.</b></font>";
			}// Change Ended By Aniket Chatterjee.For More Please Refer To
				// History.
		} catch (Exception e) {
			transferDemandResponse = "ERROR:";
			AppLog.error(e);
		}
		AppLog.end();
		return transferDemandResponse;
	}

	/**
	 * <p>
	 * Update Selected KNO List for Demand Transfer.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void updateSelectedKNOList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Map<String, String> selectedKNOMap = (HashMap<String, String>) session
					.get("selectedKNOMap");
			if (null == selectedKNOMap) {
				selectedKNOMap = new HashMap<String, String>();
			}
			if (null != selectedKNO && !"".equals(selectedKNO.trim())) {
				String selectedKNOArray[] = selectedKNO.split(",");
				for (int i = 0; i < selectedKNOArray.length; i++) {
					String tempString = selectedKNOArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						selectedKNOMap.put(tempString, tempString);
					}
				}
				this.selectedKNO = "";
			}
			if (null != removedKNO && !"".equals(removedKNO.trim())) {
				String removedKNOArray[] = removedKNO.split(",");
				for (int i = 0; i < removedKNOArray.length; i++) {
					String tempString = removedKNOArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						selectedKNOMap.remove(tempString);
					}
				}
				this.removedKNO = "";
			}
			if (null != selectedKNOMap && !selectedKNOMap.isEmpty()
					&& selectedKNOMap.size() > 0
					&& !"search".equalsIgnoreCase(hidAction)) {
				selectedKNOList = new ArrayList<String>();
				for (Entry<String, String> entry : selectedKNOMap.entrySet()) {
					selectedKNOList.add(entry.getKey());
				}
			} else {
				selectedKNOList = null;
				this.selectedKNO = "";
				this.removedKNO = "";
			}
			if (null != selectedKNOList && selectedKNOList.size() > 0) {
				totalRecordsSelected = Integer.toString(selectedKNOList.size());
			} else {
				this.totalRecordsSelected = "0";
			}
			if (null != hidAction && !"".equalsIgnoreCase(hidAction.trim())
					&& !"search".equalsIgnoreCase(hidAction)) {
				session.put("selectedKNOMap", selectedKNOMap);
			} else {
				session.remove("selectedKNOMap");
			}
			// System.out.println("hidAction::" + hidAction
			// + "::totalRecordsSelected::" + totalRecordsSelected);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

}
