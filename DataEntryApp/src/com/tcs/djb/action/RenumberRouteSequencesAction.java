/************************************************************************************************************
 * @(#) RenumberRouteSequencesAction.java   23 May 2013
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
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.RenumberRouteSequencesDAO;
import com.tcs.djb.model.RenumberRouteSequencesDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Renumber Route Sequences Screen.
 * </p>
 * 
 * @author Reshma (Tata Consultancy Services)
 * @history Reshma on 23-05-2013 Removed service Cycle and Service Route
 *          Functionality from front end.
 * @history on 12-06-2013 Reshma Edited searchRouteSequences Added
 *          sequenceRangeFrom and sequenceRangeTo parameters while searching for
 *          the consumerList details.
 * @history on 21-06-2013 Reshma commented service cycle and service route as
 *          well as sequence range from and sequence range to functionality and
 *          made processRouteSequence to work on submitting the page instead of
 *          ajax call.
 * 
 */
public class RenumberRouteSequencesAction extends ActionSupport implements
		ServletResponseAware {

	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "24";
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
	 * Maximum Number of Record Displayed Per Page of search screen.
	 */
	private String maxRecordPerPage;
	/**
	 * Current Page no.
	 */
	private String pageNo;
	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;
	/**
	 * Renumber Route Sequences Details List.
	 */
	List<RenumberRouteSequencesDetails> renumberRouteSequencesDetailsList;
	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;
	/**
	 * Area Drop down.
	 */
	private String selectedArea;
	/**
	 * MRKEY Drop down.
	 */
	private String selectedMRKEY;
	/**
	 * MR No Drop down.
	 */
	private String selectedMRNo;
	/**
	 * SERVICE CYCLE Drop down.
	 */
	private String selectedServiceCycle;
	/**
	 * SERVICE ROUTE Drop down.
	 */
	private String selectedServiceRoute;
	/**
	 * selected SPID .
	 */
	private String selectedSPID;
	/**
	 * selected SPID List.
	 */
	List<String> selectedSPIDList;
	/**
	 * Zone No Drop down.
	 */
	private String selectedZone;
	/**
	 * route sequence starting from
	 */
	private String sequenceRangeFrom;
	/**
	 * route sequence ended upto
	 */
	private String sequenceRangeTo;
	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;
	/**
	 * total no of Records selected.
	 */
	private String totalRecordsSelected;

	/**
	 * For all ajax call in Renumber Route Sequences Screen.
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
			}
			if ("populateMRKEY".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRKEYByZoneMRArea(selectedZone, selectedMRNo,
								selectedArea);
				StringBuffer responseSB = new StringBuffer(512);
				if (null != returnMap) {
					// responseSB.append("<MRKE>");
					responseSB.append(returnMap.get("mrKey"));
					// responseSB.append("<MRKE>");
					StringBuffer dropDownSB = new StringBuffer(512);
					// dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
					inputStream = new StringBufferInputStream(responseSB
							.toString());
				} else {
					inputStream = new StringBufferInputStream("ERROR:");
				}
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
					dropDownSB.append(selectTagEnd);
					responseSB.append(dropDownSB.toString());
				} else {
					responseSB.append("ERROR:");
				}
				inputStream = new StringBufferInputStream(responseSB.toString());
			}
			// if ("populateServiceCycle".equalsIgnoreCase(hidAction)) {
			// Map<String, String> returnMap = (HashMap<String, String>)
			// GetterDAO
			// .getServiceCycleListMap(selectedMRKEY);
			// StringBuffer dropDownSB = new StringBuffer(512);
			// dropDownSB
			// .append("<select name=\"selectedServiceCycle\" id=\"selectedServiceCycle\" class=\"selectbox-long\" onfocus=\"fnCheckMRKEY();\" onchange=\"fnGetServiceRoute();\">");
			// dropDownSB.append("<option value=''>Please Select</option>");
			// if (null != returnMap && !returnMap.isEmpty()) {
			// for (Entry<String, String> entry : returnMap.entrySet()) {
			// dropDownSB.append(optionTagBeginPart1);
			// dropDownSB.append(null == entry.getKey() ? "" : entry
			// .getKey());
			// dropDownSB.append(optionTagBeginPart2);
			// dropDownSB.append(null == entry.getValue() ? "" : entry
			// .getValue());
			// dropDownSB.append(optionTagEnd);
			// }
			// }
			// dropDownSB.append(selectTagEnd);
			// session.put("ServiceCycleListMap", returnMap);
			// inputStream = new StringBufferInputStream(dropDownSB.toString());
			// }
			// if ("populateServiceRoute".equalsIgnoreCase(hidAction)) {
			// Map<String, String> returnMap = (HashMap<String, String>)
			// GetterDAO
			// .getServiceRouteListMap(selectedMRKEY,
			// selectedServiceCycle);
			// StringBuffer dropDownSB = new StringBuffer(512);
			// dropDownSB
			// .append("<select name=\"selectedServiceRoute\" id=\"selectedServiceRoute\" class=\"selectbox\" onfocus=\"fnCheckMRKEYServiceCycle();\" >");
			// dropDownSB.append("<option value=''>Please Select</option>");
			// if (null != returnMap && !returnMap.isEmpty()) {
			// for (Entry<String, String> entry : returnMap.entrySet()) {
			// dropDownSB.append(optionTagBeginPart1);
			// dropDownSB.append(null == entry.getKey() ? "" : entry
			// .getKey());
			// dropDownSB.append(optionTagBeginPart2);
			// dropDownSB.append(null == entry.getValue() ? "" : entry
			// .getValue());
			// dropDownSB.append(optionTagEnd);
			// }
			// }
			// dropDownSB.append(selectTagEnd);
			// session.put("ServiceRouteListMap", returnMap);
			// inputStream = new StringBufferInputStream(dropDownSB.toString());
			// }

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
			if (null == hidAction) {
				session.remove("selectedSPIDMap");
			}
			if ("search".equalsIgnoreCase(hidAction)) {
				session.remove("selectedSPIDMap");
				session.put("selectedSPIDMap", new HashMap<String, String>());
				selectedSPIDList = new ArrayList<String>();
				session.remove("SERVER_MESSAGE");
				/** Search Details for Renumbering Route Sequences. */
				result = searchRouteSequences();
			}
			if ("procesRouteSequences".equalsIgnoreCase(hidAction)) {
				AppLog.info("selectedSPID" + selectedSPID);
				updateSelectedSPIDList();
				String renumberRouteSequencesResponse = processRouteSequences();
				inputStream = new StringBufferInputStream(
						renumberRouteSequencesResponse);
			}
			// if ("searchNewRouteSequences".equalsIgnoreCase(hidAction)) {
			// result = searchNewRouteSequencesDetails();
			// }
		} catch (ClassCastException e) {
			AppLog.error(e);
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
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
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
	 * @return the renumberRouteSequencesDetailsList
	 */
	public List<RenumberRouteSequencesDetails> getRenumberRouteSequencesDetailsList() {
		return renumberRouteSequencesDetailsList;
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
	 * @return the selectedMRKEY
	 */
	public String getSelectedMRKEY() {
		return selectedMRKEY;
	}

	/**
	 * @return the selectedMRNo
	 */
	public String getSelectedMRNo() {
		return selectedMRNo;
	}

	/**
	 * @return the selectedServiceCycle
	 */
	public String getSelectedServiceCycle() {
		return selectedServiceCycle;
	}

	/**
	 * @return the selectedServiceRoute
	 */
	public String getSelectedServiceRoute() {
		return selectedServiceRoute;
	}

	/**
	 * @return the selectedSPID
	 */
	public String getSelectedSPID() {
		return selectedSPID;
	}

	/**
	 * @return the selectedSPIDList
	 */
	public List<String> getSelectedSPIDList() {
		return selectedSPIDList;
	}

	/**
	 * @return the selectedZone
	 */
	public String getSelectedZone() {
		return selectedZone;
	}

	/**
	 * @return the sequenceRangeFrom
	 */
	public String getSequenceRangeFrom() {
		return sequenceRangeFrom;
	}

	/**
	 * @return the sequenceRangeTo
	 */
	public String getSequenceRangeTo() {
		return sequenceRangeTo;
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
	 * Populate default values required for Renumbering Route Sequences Screen.
	 * </p>
	 */
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
			// if (null == session.get("ServiceCycleListMap")
			// || ((HashMap<String, String>) session
			// .get("ServiceCycleListMap")).isEmpty()) {
			// if (null != selectedMRKEY && !"".equals(selectedMRKEY)) {
			// session.put("ServiceCycleListMap",
			// (HashMap<String, String>) GetterDAO
			// .getServiceCycleListMap(selectedMRKEY));
			// } else {
			// session.put("ServiceCycleListMap",
			// new HashMap<String, String>());
			// }
			// }
			// if (null == session.get("ServiceRouteListMap")
			// || ((HashMap<String, String>) session
			// .get("ServiceCycleListMap")).isEmpty()) {
			// if (null != selectedMRKEY && !"".equals(selectedMRKEY)
			// && null != selectedServiceCycle
			// && !"".equals(selectedServiceCycle)) {
			// session.put("ServiceRouteListMap",
			// (HashMap<String, String>) GetterDAO
			// .getServiceRouteListMap(selectedMRKEY,
			// selectedServiceCycle));
			// } else {
			// session.put("ServiceRouteListMap",
			// new HashMap<String, String>());
			// }
			// }
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to process the updated route sequences.
	 * </p>
	 * 
	 * @return String
	 */
	public String processRouteSequences() {
		String renumberRouteSequencesResponse = "SUCCESS";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			Map<String, String> selectedSPIDMap = (HashMap<String, String>) session
					.get("selectedSPIDMap");
			int rowsUpdated = 0;
			if (null != selectedSPIDMap && !selectedSPIDMap.isEmpty()) {
				for (Entry<String, String> entry : selectedSPIDMap.entrySet()) {
					// System.out.println(entry.getKey() + "<process>"
					// + entry.getValue());
					String spidAndSequence = entry.getValue();
					String[] parts = spidAndSequence.split("-", 2);
					String spId = parts[0];
					String routeSequence = parts[1];
					rowsUpdated += RenumberRouteSequencesDAO
							.updateRouteSequences(spId, routeSequence);
				}
			}
			AppLog.info("rowsUpdated" + rowsUpdated);
			if (rowsUpdated > 0) {
				session
						.put(
								"SERVER_MESSAGE",
								"<font color='#33CC33'><b> "
										+ rowsUpdated
										+ " Route Sequence(s) Updated Successfully.Please Search Again Before Any Further Processing.</b></font>");
				renumberRouteSequencesResponse = "<font color='#33CC33'><b> "
						+ rowsUpdated
						+ " Route Sequence(s) Updated Successfully.Please Search Again Before Any Further Processing.</b></font>";
			} else {
				renumberRouteSequencesResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			}
			this.selectedSPID = "";
		} catch (Exception e) {
			renumberRouteSequencesResponse = "ERROR:";
			AppLog.error(e);
		}
		AppLog.end();
		return renumberRouteSequencesResponse;
	}

	/**
	 * <p>
	 * This method is used to search for New Route Sequences Details.
	 * </p>
	 * 
	 * @return String
	 */
	public String searchNewRouteSequencesDetails() {
		String result = "success";
		try {
			// if ("searchTransfered".equalsIgnoreCase(hidAction)) {
			// this.pageNo = "1";
			// }
			// if (null == this.maxRecordPerPage
			// || "".equals(this.maxRecordPerPage)) {
			// this.maxRecordPerPage = PropertyUtil
			// .getProperty("RECORDS_PER_PAGE");
			// }
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Map<String, String> selectedSPIDMap = (HashMap<String, String>) session
					.get("selectedSPIDMap");
			if (null != selectedSPIDMap && !selectedSPIDMap.isEmpty()) {
				this.totalRecords = RenumberRouteSequencesDAO
						.getTotalCountOfNewRouteSequences(selectedSPIDMap);
				maxRecordPerPage = Integer.toString(totalRecords);
				if (totalRecords > 0) {
					// if (Integer.parseInt(maxRecordPerPage)
					// * Integer.parseInt(pageNo) > totalRecords) {
					// this.pageNo = Integer.toString((int) totalRecords
					// / Integer.parseInt(maxRecordPerPage) + 1);
					// }
					// List<String> pageNoList = new ArrayList<String>();
					// List<String> maxPageNoList = new ArrayList<String>();
					// for (int i = 0, j = 1; i < totalRecords; i++) {
					// if (i % Integer.parseInt(maxRecordPerPage) == 0) {
					// pageNoList.add(Integer.toString(j++));
					// if (i != 0 && i <= 200) {
					// maxPageNoList.add(Integer.toString(i));
					// }
					// }
					// }
					// this.pageNoDropdownList = pageNoList;
					this.renumberRouteSequencesDetailsList = (ArrayList<RenumberRouteSequencesDetails>) RenumberRouteSequencesDAO
							.getConsumerDetailsListForNewRouteSequences(selectedSPIDMap
							// , pageNo, maxRecordPerPage
							);
					// System.out.println("hhdMappingDetailsList Size::"
					// + hhdMappingDetailsList.size());
					result = "success";
				}
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
	 * This method is used to search Route Sequences for Renumbering.
	 * </p>
	 * 
	 * @return
	 */
	public String searchRouteSequences() {
		String result = "success";
		try {
			// if (null == this.pageNo || "".equals(this.pageNo)) {
			// this.pageNo = "1";
			// this.maxRecordPerPage = PropertyUtil
			// .getProperty("RECORDS_PER_PAGE");
			// }
			this.totalRecords = RenumberRouteSequencesDAO
					.getTotalCountOfConsumerDetailsListForRenumberRouteSequences(
							selectedMRKEY
							// , selectedServiceCycle,
							// selectedServiceRoute
							//, sequenceRangeFrom, sequenceRangeTo
							);
			maxRecordPerPage = Integer.toString(totalRecords);
			if (totalRecords >= 0) {
				// if (Integer.parseInt(maxRecordPerPage)
				// * Integer.parseInt(pageNo) > totalRecords) {
				// this.pageNo = Integer.toString((int) totalRecords
				// / Integer.parseInt(maxRecordPerPage) + 1);
				// AppLog.info("pageNo > " + pageNo);
				// }
				// List<String> pageNoList = new ArrayList<String>();
				// List<String> maxPageNoList = new ArrayList<String>();
				// for (int i = 0, j = 1; i < totalRecords; i++) {
				// if (i % Integer.parseInt(maxRecordPerPage) == 0) {
				// pageNoList.add(Integer.toString(j++));
				// if (i != 0 && i <= 200) {
				// maxPageNoList.add(Integer.toString(i));
				// }
				// }
				// }
				// this.pageNoDropdownList = pageNoList;
				this.renumberRouteSequencesDetailsList = (ArrayList<RenumberRouteSequencesDetails>) RenumberRouteSequencesDAO
						.getConsumerDetailsListForRenumberRouteSequences(
								selectedMRKEY
								// , selectedServiceCycle,
								// selectedServiceRoute
								// ,pageNo, maxRecordPerPage
								//, sequenceRangeFrom, sequenceRangeTo
								);
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
	 * @param renumberRouteSequencesDetailsList
	 *            the renumberRouteSequencesDetailsList to set
	 */
	public void setRenumberRouteSequencesDetailsList(
			List<RenumberRouteSequencesDetails> renumberRouteSequencesDetailsList) {
		this.renumberRouteSequencesDetailsList = renumberRouteSequencesDetailsList;
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
	 * @param selectedMRKEY
	 *            the selectedMRKEY to set
	 */
	public void setSelectedMRKEY(String selectedMRKEY) {
		this.selectedMRKEY = selectedMRKEY;
	}

	/**
	 * @param selectedMRNo
	 *            the selectedMRNo to set
	 */
	public void setSelectedMRNo(String selectedMRNo) {
		this.selectedMRNo = selectedMRNo;
	}

	/**
	 * @param selectedServiceCycle
	 *            the selectedServiceCycle to set
	 */
	public void setSelectedServiceCycle(String selectedServiceCycle) {
		this.selectedServiceCycle = selectedServiceCycle;
	}

	/**
	 * @param selectedServiceRoute
	 *            the selectedServiceRoute to set
	 */
	public void setSelectedServiceRoute(String selectedServiceRoute) {
		this.selectedServiceRoute = selectedServiceRoute;
	}

	/**
	 * @param selectedSPID
	 *            the selectedSPID to set
	 */
	public void setSelectedSPID(String selectedSPID) {
		this.selectedSPID = selectedSPID;
	}

	/**
	 * @param selectedSPIDList
	 *            the selectedSPIDList to set
	 */
	public void setSelectedSPIDList(List<String> selectedSPIDList) {
		this.selectedSPIDList = selectedSPIDList;
	}

	/**
	 * @param selectedZone
	 *            the selectedZone to set
	 */
	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
	}

	/**
	 * @param sequenceRangeFrom
	 *            the sequenceRangeFrom to set
	 */
	public void setSequenceRangeFrom(String sequenceRangeFrom) {
		this.sequenceRangeFrom = sequenceRangeFrom;
	}

	/**
	 * @param sequenceRangeTo
	 *            the sequenceRangeTo to set
	 */
	public void setSequenceRangeTo(String sequenceRangeTo) {
		this.sequenceRangeTo = sequenceRangeTo;
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
	 * <p>
	 * This method is used to update Selected SPID List for Renumbering Route Sequences.
	 * </p>
	 */
	public void updateSelectedSPIDList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Map<String, String> selectedSPIDMap = (HashMap<String, String>) session
					.get("selectedSPIDMap");
			if (null == selectedSPIDMap) {
				selectedSPIDMap = new HashMap<String, String>();
			}
			if (null != selectedSPID && !"".equals(selectedSPID.trim())) {
				String selectedSPIDArray[] = selectedSPID.split(",");
				// System.out.println(selectedSPID +
				// "\nselectedSPIDArray Size::"
				// + selectedSPIDArray.length);
				for (int i = 0; i < selectedSPIDArray.length; i++) {
					String tempString = selectedSPIDArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						selectedSPIDMap.put(tempString, tempString);
					}
				}
				this.selectedSPID = "";
			}
			if (null != selectedSPIDMap && !selectedSPIDMap.isEmpty()) {
				selectedSPIDList = new ArrayList<String>();
				for (Entry<String, String> entry : selectedSPIDMap.entrySet()) {
					selectedSPIDList.add(entry.getKey());
				}
			}
			if (null != selectedSPIDList) {
				totalRecordsSelected = Integer
						.toString(selectedSPIDList.size());
			}
			session.put("selectedSPIDMap", selectedSPIDMap);
			// System.out.println("selectedSPIDMap Size::"
			// + selectedSPIDMap.size());
			// System.out.println("selectedSPIDList Size::"
			// + selectedSPIDList.size());
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

}
