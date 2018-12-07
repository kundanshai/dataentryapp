/************************************************************************************************************
 * @(#) OnlineBatchBillingAction.java   28 Jun 2013
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
import com.tcs.djb.dao.OnlineBatchBillingDAO;
import com.tcs.djb.model.OnlineBatchBillingDetails;
import com.tcs.djb.thread.OnlineBatchBillingThread;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Online Batch Billing screen. It processes all the requests
 * from the Online Batch billing Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 28-06-2013
 * @history 15-07-2013 Matiur Rahman Added method
 *          {@link #populateCurrentlyRunningJobs}.
 * @history 07-08-2013 Matiur Rahman Implemented date range for Job Summary.
 * @history 08-08-2013 Matiur Rahman Implemented Provision for Entering List of
 *          MR Keys separated by Comma as per JTrac DJB-1754.
 */
@SuppressWarnings("deprecation")
public class OnlineBatchBillingAction extends ActionSupport implements
		ServletResponseAware {

	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";

	private static final String optionTagEnd = "</option>";
	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "28";

	private static final String selectTagEnd = "</select>";

	private static final long serialVersionUID = 1L;

	private String fromDate;

	/**
	 * Hidden action.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;
	private String mrKeyListForSearch;
	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	private String searchBy;

	/**
	 * Area Drop down.
	 */
	private String selectedArea;

	/**
	 * Bill Round Drop down.
	 */
	private String selectedBillRound;

	private String selectedErrorCode;
	/**
	 * MRKEY Drop down.
	 */
	private String selectedMRKEY;
	/**
	 * MR No Drop down.
	 */
	private String selectedMRNo;
	private String selectedProcessCounter;
	private String selectedStatusCode;
	/**
	 * Zone No Drop down.
	 */
	private String selectedZone;
	/**
	 * ZRO Code Drop down.
	 */
	private String selectedZROCode;

	private String summaryFor;

	private String toDate;
	private int totalRecordsBillable;

	private int totalRecordsBilled;

	private String userId;

	/**
	 * <p>
	 * For all aJax call in Data Entry Search screen.
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
			// System.out.println("hidAction::" + hidAction);
			this.userId = userId;
			if (null != mrKeyListForSearch) {
				mrKeyListForSearch = mrKeyListForSearch.trim();
				mrKeyListForSearch = mrKeyListForSearch.replaceAll("\\r|\\n",
						"");
				mrKeyListForSearch = removeLastComma(mrKeyListForSearch);
			}
			// System.out.println("searchBy::" + searchBy);
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
			}
			if ("retrieveSummary".equalsIgnoreCase(hidAction)) {
				session.remove("totalBillableRecords");
				session.remove("totalBilledRecords");
				session.remove("totalRecordsBilled");
				session.remove("recordsInitiated");
				List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = null;
				if ("ListOfMRKeys".equals(searchBy)) {
					onlineBatchBillingDetailsList = OnlineBatchBillingDAO
							.getSummaryForOnlineBatchBilling(selectedBillRound,
									null, mrKeyListForSearch);
				} else {
					onlineBatchBillingDetailsList = OnlineBatchBillingDAO
							.getSummaryForOnlineBatchBilling(selectedBillRound,
									selectedMRKEY, null);
				}
				inputStream = new StringBufferInputStream(
						formSummaryResponse(onlineBatchBillingDetailsList));
			}
			if ("generateBill".equalsIgnoreCase(hidAction)) {
				int totalBillableRecords = null != session
						.get("totalRecordsBillable") ? (Integer) session
						.get("totalRecordsBillable") : 0;
				initiateBatchBilling();
				// System.out.println(totalBillableRecords + "::"
				// + totalRecordsBilled);
				if (totalRecordsBilled > 0) {
					inputStream = new StringBufferInputStream(
							generateProgressBar());
				} else if (totalBillableRecords != totalRecordsBilled) {
					inputStream = new StringBufferInputStream(
							"<font color=\"red\"><b>There was a missmatch in total Billable Records on Screen and System. Please Refresh your Search for the latest Summary.");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color=\"red\"><b>No Account Found for Bill Generation for the Bill Round"
									+ selectedBillRound
									+ ". Please Refresh your Search for the latest Summary.");
				}
			}
			if ("refreshGenerateBill".equalsIgnoreCase(hidAction)) {
				StringBuffer htmlCodeSB = new StringBuffer(512);
				// responseSB.append("<summary>");
				htmlCodeSB.append(generateSummaryForRefresh());
				htmlCodeSB.append("<table width=\"98%\" border=\"0\">");
				htmlCodeSB.append("<tr>");
				htmlCodeSB
						.append("<td align=\"center\" valign=\"top\" id=\"idProgressBar\">");
				htmlCodeSB.append(generateProgressBar());
				htmlCodeSB.append("</td>");
				htmlCodeSB.append("<tr>");
				htmlCodeSB.append("<table>");
				inputStream = new StringBufferInputStream(htmlCodeSB.toString());
			}
			if ("refreshProgressBar".equalsIgnoreCase(hidAction)) {
				StringBuffer responseSB = new StringBuffer(512);
				// responseSB.append(generateProgressBar());
				responseSB.append(percentageOfProgress());
				inputStream = new StringBufferInputStream(responseSB.toString());
			}
			if ("populateDashBoard".equalsIgnoreCase(hidAction)) {
				StringBuffer responseSB = new StringBuffer(512);
				responseSB.append(generateDashBoard());
				inputStream = new StringBufferInputStream(responseSB.toString());
			}
			if ("populateBillingSummary".equalsIgnoreCase(hidAction)) {
				StringBuffer responseSB = new StringBuffer(512);
				responseSB.append(generateBillingSummary());
				inputStream = new StringBufferInputStream(responseSB.toString());
			}
			if ("populateCurrentlyRunningJobs".equalsIgnoreCase(hidAction)) {
				StringBuffer responseSB = new StringBuffer(512);
				responseSB.append(populateCurrentlyRunningJobs());
				inputStream = new StringBufferInputStream(responseSB.toString());
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
			session.remove("SERVER_MESSAGE");
			session.remove("AJAX_MESSAGE");
			if (null == hidAction) {
				session.put("ZROListMap", new HashMap<String, String>());
				session.put("ZROListMapNew", new HashMap<String, String>());
			}
			session.remove("MY_JOB_LIST_MRKEY");
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
	 * <p>
	 * This method forms the Summary Response HTML code.
	 * </p>
	 * 
	 * @param onlineBatchBillingDetailsList
	 * @return
	 * @throws Exception
	 */
	private String formSummaryResponse(
			List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList)
			throws Exception {
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		StringBuffer htmlCodeSB = new StringBuffer();
		if (null != onlineBatchBillingDetailsList
				&& onlineBatchBillingDetailsList.size() > 0) {
			htmlCodeSB
					.append("<table id=\"searchResult\" class=\"table-grid\">");
			htmlCodeSB.append("<tr><th align=\"center\" width=\"5%\">SL</th>");
			htmlCodeSB
					.append("<th align=\"center\" width=\"15%\">STATUS CODE</th>");
			htmlCodeSB
					.append("<th align=\"left\" width=\"50%\">DESCRIPTION</th>");
			htmlCodeSB
					.append("<th align=\"center\" width=\"15%\" nowrap>PROCESS COUNTER</th>");
			htmlCodeSB
					.append("<th align=\"right\" width=\"15%\">TOTAL RECORDS</th></tr>");
			int totalRecords = 0;
			int totalBillableRecords = 0;
			// int totalBilledRecords = 63;
			for (int i = 0; i < onlineBatchBillingDetailsList.size(); i++) {
				onlineBatchBillingDetails = (OnlineBatchBillingDetails) onlineBatchBillingDetailsList
						.get(i);
				htmlCodeSB
						.append("<tr onclick=\"fnGetBillingSummary('"
								+ onlineBatchBillingDetails.getProcessCounter()
								+ "','"
								+ onlineBatchBillingDetails.getStatusCode()
								+ "');\" bgcolor=\""
								+ (null != onlineBatchBillingDetails
										.getStatusCode()
										&& "70"
												.equals(onlineBatchBillingDetails
														.getStatusCode())
										&& null != onlineBatchBillingDetails
												.getProcessCounter()
										&& "0".equals(onlineBatchBillingDetails
												.getProcessCounter()) ? "#29A329"
										: (i > 15 ? setColorCode(i % 15)
												: setColorCode(i)))
								+ "\" title=\"Total "
								+ onlineBatchBillingDetails.getTotalRecords()
								+ " record(s) in "
								+ onlineBatchBillingDetails.getStatusDesc()
								+ " status "
								+ (null != onlineBatchBillingDetails
										.getStatusCode()
										&& "70"
												.equals(onlineBatchBillingDetails
														.getStatusCode())
										&& null != onlineBatchBillingDetails
												.getProcessCounter()
										&& "0".equals(onlineBatchBillingDetails
												.getProcessCounter()) ? "and it is Billable Online."
										: ".")
								+ "\nPlease Click for Details. \" >");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getSlNo() + "</td>");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getStatusCode() + "</td>");
				htmlCodeSB.append("<td align=\"left\">"
						+ onlineBatchBillingDetails.getStatusDesc() + "</td>");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getProcessCounter()
						+ "</td>");
				if (null != onlineBatchBillingDetails.getStatusCode()
						&& "70".equals(onlineBatchBillingDetails
								.getStatusCode())
						&& null != onlineBatchBillingDetails
								.getProcessCounter()
						&& "0".equals(onlineBatchBillingDetails
								.getProcessCounter())) {
					htmlCodeSB
							.append("<td id=\"totalBilableRecordsTD\" align=\"right\">"
									+ onlineBatchBillingDetails
											.getTotalRecords() + "</td>");
				} else {
					htmlCodeSB.append("<td align=\"right\">"
							+ onlineBatchBillingDetails.getTotalRecords()
							+ "</td>");
				}
				htmlCodeSB.append("</tr>");
				totalRecords += Integer
						.parseInt(null != onlineBatchBillingDetails
								.getTotalRecords()
								&& !"".equals(onlineBatchBillingDetails
										.getTotalRecords().trim()) ? onlineBatchBillingDetails
								.getTotalRecords()
								: "0");
				if (null != onlineBatchBillingDetails.getTotalRecords()
						&& !"".equals(onlineBatchBillingDetails
								.getTotalRecords().trim())
						&& null != onlineBatchBillingDetails.getStatusCode()
						&& "70".equals(onlineBatchBillingDetails
								.getStatusCode())
						&& null != onlineBatchBillingDetails
								.getProcessCounter()
						&& "0".equals(onlineBatchBillingDetails
								.getProcessCounter())) {
					totalBillableRecords += Integer
							.parseInt(onlineBatchBillingDetails
									.getTotalRecords().trim());
				}
			}
			this.totalRecordsBillable = totalBillableRecords;
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("totalRecordsBillable", totalRecordsBillable);
			htmlCodeSB
					.append("<tr><th align=\"right\" colspan=\"4\"><b>Total Records</b></th>");
			htmlCodeSB.append("<th align=\"right\">" + totalRecords
					+ "</th></tr>");
			htmlCodeSB.append("</table>");
			// System.out.println("totalBillableRecords::" +
			// totalBillableRecords);
			if (totalBillableRecords > 0) {
				htmlCodeSB.append(generateBillGenerator());
			}

		} else {
			htmlCodeSB
					.append("<font color=\"red\"> No records Found to Display for the Seach Criteria.</font>");
		}
		// System.out.println("::" + htmlCodeSB.toString());
		return htmlCodeSB.toString();

	}

	/**
	 * <p>
	 * This method is used to generate html code for Bill Generation.
	 * </p>
	 * 
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String generateBillGenerator() throws Exception {
		StringBuffer htmlCodeSB = new StringBuffer();
		Map<String, Object> session = ActionContext.getContext().getSession();
		Map<String, String> mrkeyMap = (HashMap<String, String>) session
				.get("MY_JOB_LIST_MRKEY");
		String mrKeyOnJobList = null;
		if (null != mrkeyMap) {
			mrKeyOnJobList = (String) mrkeyMap.get(selectedMRKEY);
		}
		int noOfRecordsInProcess = 0;
		if (null != mrKeyOnJobList && !"".equals(mrKeyOnJobList)) {
			if ("ListOfMRKeys".equals(searchBy)) {
				noOfRecordsInProcess = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound, null,
								mrKeyListForSearch, userId);
			} else {
				noOfRecordsInProcess = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound,
								selectedMRKEY, null, userId);
			}
		} else {
			if ("ListOfMRKeys".equals(searchBy)) {
				noOfRecordsInProcess = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound, null,
								mrKeyListForSearch, null);
			} else {
				noOfRecordsInProcess = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound,
								selectedMRKEY, null, null);
			}
		}
		// System.out.println("noOfRecordsInProcess::" + noOfRecordsInProcess);
		if (noOfRecordsInProcess > 0) {
			session.put("totalRecordsBilled", noOfRecordsInProcess);
			htmlCodeSB.append("<table width=\"98%\" border=\"0\">");
			htmlCodeSB.append("<tr>");
			htmlCodeSB
					.append("<td align=\"center\" valign=\"top\" id=\"idProgressBar\">");
			htmlCodeSB.append(generateProgressBar());
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("<tr>");
			htmlCodeSB.append("<table>");
		} else {
			htmlCodeSB.append("<table width=\"98%\" border=\"0\">");
			htmlCodeSB.append("<tr>");
			htmlCodeSB
					.append("<td align=\"center\" valign=\"top\" id=\"idProgressBar\"><input type=\"button\" id=\"btnGenerateBill\" value=\" Generate Bill \" class=\"groovybutton\" onclick=\"fnGenerateBill();\" />");
			// htmlCodeSB.append(getProgressReport(totalBillableRecords,
			// totalBilledRecords));
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("<tr>");
			htmlCodeSB.append("<table>");
		}
		// System.out.println("::" + htmlCodeSB.toString());
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to generate Billing Summary after Billing Process is
	 * complete.
	 * </p>
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	private Object generateBillingSummary() {
		StringBuffer htmlCodeSB = new StringBuffer();
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<String> openBillRoundList = (ArrayList<String>) session
				.get("OpenBillRoundListMap");
		if (null != openBillRoundList && openBillRoundList.size() > 0) {
			List<OnlineBatchBillingDetails> currentonlineBatchBillingDetailsList = null;
			if ("ListOfMRKeys".equals(searchBy)) {
				currentonlineBatchBillingDetailsList = OnlineBatchBillingDAO
						.getBillingSummary(selectedBillRound, null,
								mrKeyListForSearch, selectedProcessCounter,
								selectedStatusCode);
			} else {
				currentonlineBatchBillingDetailsList = OnlineBatchBillingDAO
						.getBillingSummary(selectedBillRound, selectedMRKEY,
								null, selectedProcessCounter,
								selectedStatusCode);
			}
			OnlineBatchBillingDetails onlineBatchBillingDetails = null;
			if (null != currentonlineBatchBillingDetailsList
					&& currentonlineBatchBillingDetailsList.size() > 0) {
				htmlCodeSB
						.append("<div style=\"color: blue; align: center;width:935px;height:150px;overflow:auto;\">");
				htmlCodeSB
						.append("<table class=\"table-grid\" width=\"100%\">");
				htmlCodeSB.append("<tr>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"2%\"><font size=\"1\">SL</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"3%\"><font size=\"1\">SVC SEQ</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"10%\"><font size=\"1\">KNO & NAME</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"14%\"><font size=\"1\">NAME</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"> <font size=\"1\">SUMBITED BY</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">SUBMITED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">BILING STARTED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">BILING COMPLETED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"25%\"><font size=\"1\">REMARKS</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"5%\"><font size=\"1\">STATUS</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">BILL ID</font></th>");
				htmlCodeSB.append("</tr>");
				for (int i = 0; i < currentonlineBatchBillingDetailsList.size(); i++) {
					onlineBatchBillingDetails = (OnlineBatchBillingDetails) currentonlineBatchBillingDetailsList
							.get(i);
					String remarks = null != onlineBatchBillingDetails
							.getBillingError()
							&& "NA".equals(onlineBatchBillingDetails
									.getBillingError()) ? onlineBatchBillingDetails
							.getBillingRemarks()
							: onlineBatchBillingDetails.getBillingError();
					remarks = remarks.replaceAll("&", " ");
					remarks = remarks.replaceAll("'", " ");
					remarks = remarks.replaceAll("\"", " ");
					htmlCodeSB
							.append("<tr onMouseOver=\"this.bgColor='yellow';\"	onMouseOut=\"this.bgColor='white';\" title=\"Service Sequence No: "
									+ onlineBatchBillingDetails.getServiceSeq()
									+ "\nKNO: "
									+ onlineBatchBillingDetails.getKno()
									+ "\nName: "
									+ onlineBatchBillingDetails
											.getConsumerName() + "\nBill ID: "
									// + onlineBatchBillingDetails.getBillId()
									// + "\nCurrent Status: "
									// +
									// onlineBatchBillingDetails.getStatusDesc()
									// + "\nSubmitted By: "
									// + onlineBatchBillingDetails
									// .getInitiatedBy()
									// + "\nSubmitted on: "
									// + onlineBatchBillingDetails
									// .getInitiatedOn()
									// + "\nBilling Started on: "
									// +
									// onlineBatchBillingDetails.getStartedOn()
									// + "\nBill Generated on: "
									// + onlineBatchBillingDetails
									// .getCompletedOn()
									+ "\nRemaks:\n" + remarks + "\">");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getSlNo() + "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getServiceSeq()
							+ "</td>");
					htmlCodeSB.append("<td align=\"left\">"
							+ onlineBatchBillingDetails.getKno() + "<br/>"
							+ onlineBatchBillingDetails.getConsumerName()
							+ "</td>");
					// htmlCodeSB.append("<td align=\"left\">"
					// + onlineBatchBillingDetails.getConsumerName()
					// + "</td>");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getInitiatedBy()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getInitiatedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getStartedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getCompletedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"left\">"
							+ onlineBatchBillingDetails.getBillingRemarks()
							+ "</td>");
					// htmlCodeSB.append("<td align=\"center\">"
					// + onlineBatchBillingDetails.getStatusDesc()
					// + "</td>");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getBillId() + "</td>");
					htmlCodeSB.append("</tr>");
				}
				htmlCodeSB.append("</table>");
				htmlCodeSB.append("</div>");

			} else {
				htmlCodeSB
						.append("<br/><font color=\"red\">No Job found to Display for you.</font>");
			}
			htmlCodeSB
					.append("<input type=\"button\" id=\"btnClose\" value=\" Close \" class=\"groovybutton\" onclick=\"fnCloseBillingSummary();\"  title=\"Click to close.\"/>");
		}
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to generate DashBoard with the details of Job
	 * submitted by the User Logged in.
	 * </p>
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	private Object generateDashBoard() {
		StringBuffer htmlCodeSB = new StringBuffer();
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<String> openBillRoundList = (ArrayList<String>) session
				.get("OpenBillRoundListMap");
		if (null != openBillRoundList && openBillRoundList.size() > 0) {
			String openBillRound = "''";
			for (int i = 0; i < openBillRoundList.size(); i++) {
				openBillRound += ",'" + openBillRoundList.get(i) + "'";
			}
			List<OnlineBatchBillingDetails> currentonlineBatchBillingDetailsList = null;
			if ("ALL".equalsIgnoreCase(summaryFor)) {
				currentonlineBatchBillingDetailsList = OnlineBatchBillingDAO
						.getCurrentSummaryForUser(openBillRound, null,
								this.fromDate, this.toDate);
			} else {
				currentonlineBatchBillingDetailsList = OnlineBatchBillingDAO
						.getCurrentSummaryForUser(openBillRound, this.userId,
								this.fromDate, this.toDate);
			}
			OnlineBatchBillingDetails onlineBatchBillingDetails = null;
			htmlCodeSB
					.append("<a href=\"#\" onclick=\"fnPopulateDashBoard();\"><img src=\"/DataEntryApp/images/refresh-animated_orange.gif\" border=\"0\" title=\"Click to Refresh Summary.\"/></a>");
			if (null != currentonlineBatchBillingDetailsList
					&& currentonlineBatchBillingDetailsList.size() > 0) {
				Map<String, String> mrkeyMap = new HashMap<String, String>();
				for (int i = 0; i < currentonlineBatchBillingDetailsList.size(); i++) {
					onlineBatchBillingDetails = (OnlineBatchBillingDetails) currentonlineBatchBillingDetailsList
							.get(i);
					if (null != onlineBatchBillingDetails.getInitiatedBy()
							&& onlineBatchBillingDetails.getInitiatedBy()
									.equals(userId)) {
						mrkeyMap.put(onlineBatchBillingDetails.getMrKey(),
								onlineBatchBillingDetails.getMrKey());
					}
				}
				session.put("MY_JOB_LIST_MRKEY", mrkeyMap);
				htmlCodeSB
						.append("<div style=\"color: blue; align: center;width:100%;height:150px;overflow:auto;\">");
				htmlCodeSB
						.append("<table id=\"dashBoard\" class=\"table-grid\" width=\"100%\">");
				htmlCodeSB.append("<tr>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">SL</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">BILL ROUND</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">MRKEY</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"5%\"><font size=\"1\">ZONE</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"5%\"><font size=\"1\">MR NO</font></th>");
				// htmlCodeSB
				// .append("<th align=\"left\" width=\"20%\"><font size=\"1\">AREA</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"> <font size=\"1\">RECORDS<br/>SUMBITED</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">RECORDS<br/>PENDING</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">COMPLETE %</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">LAST SUBMITED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">FIRST BILING STARTED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">LAST BILL GENERATED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\" title=\"(TIME ELAPSED) = (BILING STARTED ON) - (LAST BILL GENERATED ON)\"><font size=\"1\">TIME ELAPSED<br/>(hh:mi:ss)</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\" title=\"Please Click details for Latest Summary\"><font size=\"1\">ACTION</font></th>");
				if ("ALL".equalsIgnoreCase(summaryFor)) {
					htmlCodeSB
							.append("<th align=\"center\" width=\"5%\"><font size=\"1\">SUBMITTED BY</font></th>");
				}
				htmlCodeSB.append("</tr>");
				int totalRecordsSubmited = 0;
				int totalRecordsPending = 0;
				int totalRecordsPendingPC = 0;
				int recordsSubmited = 0;
				int recordsPending = 0;
				int recordsPendingPC = 0;
				for (int i = 0; i < currentonlineBatchBillingDetailsList.size(); i++) {
					recordsSubmited = 0;
					recordsPending = 0;
					recordsPendingPC = 0;
					onlineBatchBillingDetails = (OnlineBatchBillingDetails) currentonlineBatchBillingDetailsList
							.get(i);
					recordsSubmited = (null != onlineBatchBillingDetails
							.getTotalRecords() && !""
							.equals(onlineBatchBillingDetails.getTotalRecords())) ? Integer
							.parseInt(onlineBatchBillingDetails
									.getTotalRecords())
							: 0;
					recordsPending = (null != onlineBatchBillingDetails
							.getRecordsInProcess() && !""
							.equals(onlineBatchBillingDetails
									.getRecordsInProcess())) ? Integer
							.parseInt(onlineBatchBillingDetails
									.getRecordsInProcess()) : 0;
					if (recordsSubmited > 0) {
						recordsPendingPC = ((recordsPending * 100) / recordsSubmited);
					}
					htmlCodeSB
							.append("<tr onclick=\"fnShowDetails('"
									+ onlineBatchBillingDetails.getMrKey()
									+ "');\" onMouseOver=\"this.bgColor='yellow';\"	onMouseOut=\"this.bgColor='white';\" title=\"MRKEY: "
									+ onlineBatchBillingDetails.getMrKey()
									+ "\nZone: "
									+ onlineBatchBillingDetails.getZone()
									+ "\nMR No: "
									+ onlineBatchBillingDetails.getMrNo()
									+ "\nArea: "
									+ onlineBatchBillingDetails.getArea()
									+ "\nSubmitted By: "
									+ onlineBatchBillingDetails
											.getInitiatedBy()
									+ "\nSubmitted on: "
									+ onlineBatchBillingDetails
											.getInitiatedOn()
									+ "\nBilling Started on: "
									+ onlineBatchBillingDetails.getStartedOn()
									+ "\nLast Bill Generated on: "
									+ onlineBatchBillingDetails
											.getCompletedOn()
									+ "\nTime Elapsed: "
									+ onlineBatchBillingDetails
											.getTotalTimeTaken()
									+ "\nSubmitted Records: "
									+ recordsSubmited
									+ "\nPending Records: "
									+ recordsPending
									+ "\nCurrent Status: Completed "
									+ (100 - recordsPendingPC)
									+ "%"
									+ "\n\nPlease Click for Latest Summary.\t\t\t\t\">");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getSlNo() + "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getBillRound()
							+ "</td>");
					htmlCodeSB.append("<td align=\"left\">"
							+ onlineBatchBillingDetails.getMrKey() + "</td>");
					// htmlCodeSB.append("<td align=\"center\">"
					// + onlineBatchBillingDetails.getZone() + "</td>");
					// htmlCodeSB.append("<td align=\"center\">"
					// + onlineBatchBillingDetails.getMrNo() + "</td>");
					// htmlCodeSB.append("<td align=\"left\">"
					// + onlineBatchBillingDetails.getArea() + "</td>");
					htmlCodeSB.append("<td align=\"right\">" + recordsSubmited
							+ "</td>");
					htmlCodeSB.append("<td align=\"right\">"
							+ onlineBatchBillingDetails.getRecordsInProcess()
							+ "</td>");
					// System.out.println("recordsSubmited::" + recordsSubmited
					// + "::recordsPending::" + recordsPending
					// + "::totalRecordsPendingPC::" + recordsPendingPC);
					htmlCodeSB.append("<td align=\"right\">"
							+ (100 - recordsPendingPC) + "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getInitiatedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getStartedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getCompletedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getTotalTimeTaken()
							+ "</td>");
					htmlCodeSB
							.append("<td align=\"center\"><a href=\"#\">Details</a></td>");
					if ("ALL".equalsIgnoreCase(summaryFor)) {
						htmlCodeSB.append("<td align=\"left\" nowrap>"
								+ onlineBatchBillingDetails.getInitiatedBy()
								+ "</td>");
					}
					htmlCodeSB.append("</tr>");
					totalRecordsSubmited += Integer
							.parseInt(null != onlineBatchBillingDetails
									.getTotalRecords()
									&& !"".equals(onlineBatchBillingDetails
											.getTotalRecords().trim()) ? onlineBatchBillingDetails
									.getTotalRecords()
									: "0");
					totalRecordsPending += Integer
							.parseInt(null != onlineBatchBillingDetails
									.getRecordsInProcess()
									&& !"".equals(onlineBatchBillingDetails
											.getRecordsInProcess().trim()) ? onlineBatchBillingDetails
									.getRecordsInProcess()
									: "0");
				}
				if (totalRecordsSubmited > 0) {
					totalRecordsPendingPC = ((totalRecordsPending * 100) / totalRecordsSubmited);
				}
				// System.out.println("totalRecordsSubmited::"
				// + totalRecordsSubmited + "::totalRecordsPending::"
				// + totalRecordsPending + "::totalRecordsPendingPC::"
				// + totalRecordsPendingPC);
				htmlCodeSB
						.append("<tr><th align=\"right\" colspan=\"3\"><b>Total</b></th>");
				htmlCodeSB
						.append("<th align=\"right\" title=\"Overall Record(s)Submitted "
								+ totalRecordsSubmited
								+ "\">"
								+ totalRecordsSubmited
								+ "</th><th align=\"right\" title=\"Overall Record(s) Pending "
								+ totalRecordsPending
								+ "\">"
								+ totalRecordsPending
								+ "</th><th align=\"right\" title=\"Overall Completion "
								+ (100 - totalRecordsPendingPC)
								+ "%\">"
								+ (100 - totalRecordsPendingPC)
								+ "</th><th align=\"right\" colspan=\"7\">&nbsp;</th></tr>");
				htmlCodeSB.append("</table>");
				htmlCodeSB.append("</div>");
			} else {
				htmlCodeSB
						.append("<br/><font color=\"red\">No Job found to Display for you");
				if (null != fromDate && fromDate.length() == 10) {
					htmlCodeSB.append(" from " + fromDate);
				}
				if (null != toDate && toDate.length() == 10) {
					htmlCodeSB.append(" to " + toDate);
				}
				htmlCodeSB.append(".</font>");
			}
		}
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to generate Progress Bar on the Online batch Billing
	 * screen.
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String generateProgressBar() throws Exception {
		StringBuffer htmlCodeSB = new StringBuffer();
		Map<String, Object> session = ActionContext.getContext().getSession();
		Map<String, String> mrkeyMap = (HashMap<String, String>) session
				.get("MY_JOB_LIST_MRKEY");
		String mrKeyOnJobList = null;
		if (null != mrkeyMap) {
			mrKeyOnJobList = (String) mrkeyMap.get(selectedMRKEY);
		}
		int totalBilledRecords = 0;
		int remainingRecords = 0;
		if (null != mrKeyOnJobList && !"".equals(mrKeyOnJobList)) {
			// int totalBillableRecords = (Integer) session
			// .get("totalRecordsBillable");
			totalBilledRecords = null != session.get("totalRecordsBilled") ? (Integer) session
					.get("totalRecordsBilled")
					: 0;
			remainingRecords = totalBilledRecords;
			if (!"generateBill".equalsIgnoreCase(hidAction)) {
				// System.out.println("generateProgressBar::" +
				// totalBilledRecords);
				String recordsInitiatedSession = (String) session
						.get("recordsInitiated");
				int recordsInitiated = 0;
				if (null == recordsInitiatedSession) {
					if ("ListOfMRKeys".equals(searchBy)) {
						recordsInitiated = OnlineBatchBillingDAO
								.getNoOfRecordsInitiated(selectedBillRound,
										null, mrKeyListForSearch, userId);
					} else {
						recordsInitiated = OnlineBatchBillingDAO
								.getNoOfRecordsInitiated(selectedBillRound,
										selectedMRKEY, null, userId);
					}
					// System.out.println("generateProgressBar::totalBilledRecords::"
					// + totalBilledRecords + "::recordsInitiated::"
					// + recordsInitiated);
					// if (totalBilledRecords == 0
					// || totalBilledRecords != recordsInitiated) {
					if (totalBilledRecords == 0
							|| totalBilledRecords < recordsInitiated) {
						totalBilledRecords = recordsInitiated;
						session.put("totalRecordsBilled", totalBilledRecords);
					} else {
						session.put("recordsInitiated", "recordsInitiated:"
								+ recordsInitiated);

					}
				}
				if ("ListOfMRKeys".equals(searchBy)) {
					remainingRecords = OnlineBatchBillingDAO
							.getNoOfRecordsInProcess(selectedBillRound, null,
									mrKeyListForSearch, userId);
				} else {
					remainingRecords = OnlineBatchBillingDAO
							.getNoOfRecordsInProcess(selectedBillRound,
									selectedMRKEY, null, userId);
				}
				// System.out.println("totalBilledRecords::" +
				// totalBilledRecords
				// + "::remainingRecords::" + remainingRecords
				// + "::recordsInitiated::" + recordsInitiated);
			}
		} else {
			totalBilledRecords = null != session.get("totalRecordsBilled") ? (Integer) session
					.get("totalRecordsBilled")
					: 0;
			remainingRecords = totalBilledRecords;
			if (!"generateBill".equalsIgnoreCase(hidAction)) {
				// System.out.println("generateProgressBar::" +
				// totalBilledRecords);
				String recordsInitiatedSession = (String) session
						.get("recordsInitiated");
				int recordsInitiated = 0;
				if (null == recordsInitiatedSession) {
					if ("ListOfMRKeys".equals(searchBy)) {
						recordsInitiated = OnlineBatchBillingDAO
								.getNoOfRecordsInitiated(selectedBillRound,
										null, mrKeyListForSearch, null);
					} else {
						recordsInitiated = OnlineBatchBillingDAO
								.getNoOfRecordsInitiated(selectedBillRound,
										selectedMRKEY, null, null);
					}
					// System.out.println("generateProgressBar::totalBilledRecords::"
					// + totalBilledRecords + "::recordsInitiated::"
					// + recordsInitiated);
					// if (totalBilledRecords == 0
					// || totalBilledRecords != recordsInitiated) {
					if (totalBilledRecords == 0
							|| totalBilledRecords < recordsInitiated) {
						totalBilledRecords = recordsInitiated;
						session.put("totalRecordsBilled", totalBilledRecords);
					} else {
						session.put("recordsInitiated", "recordsInitiated:"
								+ recordsInitiated);

					}
				}
				if ("ListOfMRKeys".equals(searchBy)) {
					remainingRecords = OnlineBatchBillingDAO
							.getNoOfRecordsInProcess(selectedBillRound, null,
									mrKeyListForSearch, null);
				} else {
					remainingRecords = OnlineBatchBillingDAO
							.getNoOfRecordsInProcess(selectedBillRound,
									selectedMRKEY, null, null);
				}
				// System.out.println("totalBilledRecords::" +
				// totalBilledRecords
				// + "::remainingRecords::" + remainingRecords
				// + "::recordsInitiated::" + recordsInitiated);
			}
		}
		if (totalBilledRecords > 0) {
			int remainingRecordsPC = remainingRecords * 100
					/ totalBilledRecords;
			int totalProgress = 100 - remainingRecordsPC;
			htmlCodeSB
					.append("<table border=0><tr><td align=\"left\" valign=\"middle\">");
			if (remainingRecordsPC > 0) {
				htmlCodeSB
						.append("<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Billing in Progress.\nClick Refresh for the latest update.\" style=\"display: block;\" id=\"imgProgress\" />");

			}
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("<td align=\"left\" title=\"Progress "
					+ totalProgress + "%\">");
			htmlCodeSB
					.append("<div id=\"progressBar\" class=\"jquery-ui-like\"><div></div></div>");
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("<td align=\"left\">");
			htmlCodeSB
					.append("<input type=\"button\" id=\"btnRefresh\" value=\" Refresh \" class=\"groovybutton\" onclick=\"fnRefreshGenerateBill();\"  title=\"Click for the latest update.\"/>");
			htmlCodeSB
					.append("<input type=\"checkbox\" name=\"autoRefreshCheckBox\" id=\"autoRefreshCheckBox\" onclick=\"fnSwitchAutoRefresh();\"/>Auto Refresh");
			htmlCodeSB
					.append("<input type=\"hidden\" name=\"totalProgress\" id=\"totalProgress\" value=\""
							+ totalProgress + "\"/>");
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("</tr></table>");
		} else {
			// System.out.println("totalBilledRecords::" + totalBilledRecords
			// + "::remainingRecords::" + remainingRecords);
			htmlCodeSB
					.append("<table border=0><tr><td align=\"center\" valign=\"middle\">");
			htmlCodeSB
					.append("<font color=\"red\">Billing is in Progress submited by another User. Please wiat till it is completed.</font>");
			htmlCodeSB
					.append("<input type=\"button\" id=\"btnRefresh\" value=\" Refresh \" class=\"groovybutton\" onclick=\"fnRefreshGenerateBill();\"  title=\"Click for the latest update.\"/>");
			htmlCodeSB
					.append("<input type=\"checkbox\" name=\"autoRefreshCheckBox\" id=\"autoRefreshCheckBox\" onclick=\"fnSwitchAutoRefresh();\"/>Auto Refresh");
			htmlCodeSB.append("</td>");
			htmlCodeSB.append("</tr></table>");
		}
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to form the Summary Response HTML code.
	 * </p>
	 * 
	 * @param onlineBatchBillingDetailsList
	 * @return String
	 * @throws Exception
	 */
	private String generateSummaryForRefresh() throws Exception {
		List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = null;
		if ("ListOfMRKeys".equals(searchBy)) {
			onlineBatchBillingDetailsList = OnlineBatchBillingDAO
					.getSummaryForOnlineBatchBilling(selectedBillRound, null,
							mrKeyListForSearch);
		} else {
			onlineBatchBillingDetailsList = OnlineBatchBillingDAO
					.getSummaryForOnlineBatchBilling(selectedBillRound,
							selectedMRKEY, null);
		}
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		StringBuffer htmlCodeSB = new StringBuffer();
		if (null != onlineBatchBillingDetailsList
				&& onlineBatchBillingDetailsList.size() > 0) {
			htmlCodeSB
					.append("<table id=\"searchResult\" class=\"table-grid\">");
			htmlCodeSB.append("<tr><th align=\"center\" width=\"5%\">SL</th>");
			htmlCodeSB
					.append("<th align=\"center\" width=\"15%\">STATUS CODE</th>");
			htmlCodeSB
					.append("<th align=\"left\" width=\"50%\">DESCRIPTION</th>");
			htmlCodeSB
					.append("<th align=\"center\" width=\"15%\" nowrap>PROCESS COUNTER</th>");
			htmlCodeSB
					.append("<th align=\"right\" width=\"15%\">TOTAL RECORDS</th></tr>");
			int totalRecords = 0;
			int totalBillableRecords = 0;
			// int totalBilledRecords = 63;
			for (int i = 0; i < onlineBatchBillingDetailsList.size(); i++) {
				onlineBatchBillingDetails = (OnlineBatchBillingDetails) onlineBatchBillingDetailsList
						.get(i);
				htmlCodeSB
						.append("<tr onclick=\"fnGetBillingSummary('"
								+ onlineBatchBillingDetails.getProcessCounter()
								+ "','"
								+ onlineBatchBillingDetails.getStatusCode()
								+ "');\" bgcolor=\""
								+ (null != onlineBatchBillingDetails
										.getStatusCode()
										&& "70"
												.equals(onlineBatchBillingDetails
														.getStatusCode())
										&& null != onlineBatchBillingDetails
												.getProcessCounter()
										&& "0".equals(onlineBatchBillingDetails
												.getProcessCounter()) ? "#29A329"
										: (i > 15 ? setColorCode(i % 15)
												: setColorCode(i)))
								+ "\" title=\"Total "
								+ onlineBatchBillingDetails.getTotalRecords()
								+ " record(s) in "
								+ onlineBatchBillingDetails.getStatusDesc()
								+ " status "
								+ (null != onlineBatchBillingDetails
										.getStatusCode()
										&& "70"
												.equals(onlineBatchBillingDetails
														.getStatusCode())
										&& null != onlineBatchBillingDetails
												.getProcessCounter()
										&& "0".equals(onlineBatchBillingDetails
												.getProcessCounter()) ? "and it is Billable Online."
										: ".")
								+ "\nPlease Click for Details. \" >");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getSlNo() + "</td>");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getStatusCode() + "</td>");
				htmlCodeSB.append("<td align=\"left\">"
						+ onlineBatchBillingDetails.getStatusDesc() + "</td>");
				htmlCodeSB.append("<td align=\"center\">"
						+ onlineBatchBillingDetails.getProcessCounter()
						+ "</td>");
				htmlCodeSB
						.append("<td align=\"right\">"
								+ onlineBatchBillingDetails.getTotalRecords()
								+ "</td>");
				htmlCodeSB.append("</tr>");
				totalRecords += Integer
						.parseInt(null != onlineBatchBillingDetails
								.getTotalRecords()
								&& !"".equals(onlineBatchBillingDetails
										.getTotalRecords().trim()) ? onlineBatchBillingDetails
								.getTotalRecords()
								: "0");
				if (null != onlineBatchBillingDetails.getTotalRecords()
						&& !"".equals(onlineBatchBillingDetails
								.getTotalRecords().trim())
						&& null != onlineBatchBillingDetails.getStatusCode()
						&& "70".equals(onlineBatchBillingDetails
								.getStatusCode())
						&& null != onlineBatchBillingDetails
								.getProcessCounter()
						&& "0".equals(onlineBatchBillingDetails
								.getProcessCounter())) {
					totalBillableRecords += Integer
							.parseInt(onlineBatchBillingDetails
									.getTotalRecords().trim());
				}
			}
			htmlCodeSB
					.append("<tr><th align=\"right\" colspan=\"4\"><b>Total Records</b></th>");
			htmlCodeSB.append("<th align=\"right\">" + totalRecords
					+ "</th></tr>");
			htmlCodeSB.append("</table>");

		}
		// System.out.println("::" + htmlCodeSB.toString());
		return htmlCodeSB.toString();
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
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
	 * @return the mrKeyListForSearch
	 */
	public String getMrKeyListForSearch() {
		return mrKeyListForSearch;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the searchBy
	 */
	public String getSearchBy() {
		return searchBy;
	}

	/**
	 * @return the selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
	}

	/**
	 * @return the selectedBillRound
	 */
	public String getSelectedBillRound() {
		return selectedBillRound;
	}

	/**
	 * @return the selectedErrorCode
	 */
	public String getSelectedErrorCode() {
		return selectedErrorCode;
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
	 * @return the selectedProcessCounter
	 */
	public String getSelectedProcessCounter() {
		return selectedProcessCounter;
	}

	/**
	 * @return the selectedStatusCode
	 */
	public String getSelectedStatusCode() {
		return selectedStatusCode;
	}

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
	 * @return the summaryFor
	 */
	public String getSummaryFor() {
		return summaryFor;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @return the totalRecordsBillable
	 */
	public int getTotalRecordsBillable() {
		return totalRecordsBillable;
	}

	/**
	 * @return the totalRecordsBilled
	 */
	public int getTotalRecordsBilled() {
		return totalRecordsBilled;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p>
	 * This method is used to initiate Online Batch Billing Process. This
	 * process is run in a separate thread.
	 * </p>
	 * 
	 * @see OnlineBatchBillingThread
	 */
	private void initiateBatchBilling() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			// System.out.println("selectedBillRound::" + selectedBillRound
			// + "::selectedMRKEY::" + selectedMRKEY);
			// List<BillGenerationDetails> billGenerationDetailsList =
			// OnlineBatchBillingDAO
			// .getOnlineBatchBillGenerationDetails(selectedBillRound,
			// selectedMRKEY, selectedZone, selectedMRNo,
			// selectedArea, selectedStatusCode, selectedErrorCode);
			int rowsUpdated = 0;
			if ("ListOfMRKeys".equals(searchBy)) {
				rowsUpdated = OnlineBatchBillingDAO
						.initiateBatchBillingByUserMRKeyBillRound(null,
								mrKeyListForSearch, selectedBillRound, userId);
			} else {
				rowsUpdated = OnlineBatchBillingDAO
						.initiateBatchBillingByUserMRKeyBillRound(
								selectedMRKEY, null, selectedBillRound, userId);
			}
			int totalBillableRecords = (Integer) session
					.get("totalRecordsBillable");
			// System.out.println("totalBillableRecords::" +
			// totalBillableRecords
			// + "::rowsUpdated::" + rowsUpdated);
			if (rowsUpdated > 0 && totalBillableRecords == rowsUpdated) {
				this.totalRecordsBilled = rowsUpdated;
				session.remove("totalRecordsBilled");
				session.put("totalRecordsBilled", totalRecordsBilled);
				session.remove("recordsInitiated");
				/* Initiate Batch Billing in a separate Thread. */
				if ("ListOfMRKeys".equals(searchBy)) {
					new OnlineBatchBillingThread(null, mrKeyListForSearch,
							selectedBillRound, this.userId);
				} else {
					new OnlineBatchBillingThread(selectedMRKEY, null,
							selectedBillRound, this.userId);
				}
			} else {
				this.totalRecordsBilled = 0;
				if ("ListOfMRKeys".equals(searchBy)) {
					OnlineBatchBillingDAO
							.completeBatchBillingByUserMRKeyBillRound(null,
									mrKeyListForSearch, selectedBillRound,
									userId);
				} else {
					OnlineBatchBillingDAO
							.completeBatchBillingByUserMRKeyBillRound(
									selectedMRKEY, null, selectedBillRound,
									userId);
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
	}

	/**
	 * <p>
	 * This method is used to populate default values required for Demand
	 * Transfer Screen.
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
			if (null == session.get("OpenBillRoundListMap")
					|| ((ArrayList<String>) session.get("OpenBillRoundListMap"))
							.size() == 0) {
				session.put("OpenBillRoundListMap", OnlineBatchBillingDAO
						.getOpenBillRound(null));
			}
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to get the percentage Of Progress for Progress Bar on
	 * the Online batch Billing screen.
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String percentageOfProgress() throws Exception {
		StringBuffer htmlCodeSB = new StringBuffer();
		int totalProgress = 0;
		Map<String, Object> session = ActionContext.getContext().getSession();
		Map<String, String> mrkeyMap = (HashMap<String, String>) session
				.get("MY_JOB_LIST_MRKEY");
		String mrKeyOnJobList = null;
		if (null != mrkeyMap) {
			mrKeyOnJobList = (String) mrkeyMap.get(selectedMRKEY);
		}
		if (null != mrKeyOnJobList && !"".equals(mrKeyOnJobList)) {
			/* for the job submitted by the user logged in */
			int totalBilledRecords = null != session.get("totalRecordsBilled") ? (Integer) session
					.get("totalRecordsBilled")
					: 0;
			String recordsInitiatedSession = (String) session
					.get("recordsInitiated");
			int remainingRecords = totalBilledRecords;
			int recordsInitiated = 0;
			if (null == recordsInitiatedSession) {
				if ("ListOfMRKeys".equals(searchBy)) {
					recordsInitiated = OnlineBatchBillingDAO
							.getNoOfRecordsInitiated(selectedBillRound, null,
									mrKeyListForSearch, userId);
				} else {
					recordsInitiated = OnlineBatchBillingDAO
							.getNoOfRecordsInitiated(selectedBillRound,
									selectedMRKEY, null, userId);
				}
				// System.out.println("percentageOfProgress::totalBilledRecords::"
				// + totalBilledRecords + "::recordsInitiated::"
				// + recordsInitiated);
				if (totalBilledRecords == 0
						|| totalBilledRecords < recordsInitiated) {
					totalBilledRecords = recordsInitiated;
					session.put("totalRecordsBilled", totalBilledRecords);
				} else {
					session.put("recordsInitiated", "recordsInitiated:"
							+ recordsInitiated);
				}
			}
			if ("ListOfMRKeys".equals(searchBy)) {
				remainingRecords = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound, null,
								mrKeyListForSearch, userId);
			} else {
				remainingRecords = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound,
								selectedMRKEY, null, userId);
			}
			// System.out.println("totalBilledRecords::" + totalBilledRecords
			// + "::remainingRecords::" + remainingRecords
			// + "::recordsInitiated::" + recordsInitiated);
			int remainingRecordsPC = 0;
			if (totalBilledRecords > 0) {
				remainingRecordsPC = remainingRecords * 100
						/ totalBilledRecords;
			}
			totalProgress = 100 - remainingRecordsPC;
		} else {
			/* for the job submitted by the other user */
			int totalBilledRecords = null != session.get("totalRecordsBilled") ? (Integer) session
					.get("totalRecordsBilled")
					: 0;
			String recordsInitiatedSession = (String) session
					.get("recordsInitiated");
			int remainingRecords = totalBilledRecords;
			int recordsInitiated = 0;
			if (null == recordsInitiatedSession) {
				if ("ListOfMRKeys".equals(searchBy)) {
					recordsInitiated = OnlineBatchBillingDAO
							.getNoOfRecordsInitiated(selectedBillRound, null,
									mrKeyListForSearch, null);
				} else {
					recordsInitiated = OnlineBatchBillingDAO
							.getNoOfRecordsInitiated(selectedBillRound,
									selectedMRKEY, null, null);
				}
				// System.out.println("percentageOfProgress::totalBilledRecords::"
				// + totalBilledRecords + "::recordsInitiated::"
				// + recordsInitiated);
				if (totalBilledRecords == 0
						|| totalBilledRecords < recordsInitiated) {
					totalBilledRecords = recordsInitiated;
					session.put("totalRecordsBilled", totalBilledRecords);
				} else {
					session.put("recordsInitiated", "recordsInitiated:"
							+ recordsInitiated);
				}
			}
			if ("ListOfMRKeys".equals(searchBy)) {
				remainingRecords = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound, null,
								mrKeyListForSearch, null);
			} else {
				remainingRecords = OnlineBatchBillingDAO
						.getNoOfRecordsInProcess(selectedBillRound,
								selectedMRKEY, null, null);
			}
			// System.out.println("totalBilledRecords::" + totalBilledRecords
			// + "::remainingRecords::" + remainingRecords
			// + "::recordsInitiated::" + recordsInitiated);
			int remainingRecordsPC = 0;
			if (totalBilledRecords > 0) {
				remainingRecordsPC = remainingRecords * 100
						/ totalBilledRecords;
			}
			totalProgress = 100 - remainingRecordsPC;
		}
		htmlCodeSB.append(totalProgress);
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to populate Currently Running Jobs List.
	 * </p>
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	private Object populateCurrentlyRunningJobs() {
		StringBuffer htmlCodeSB = new StringBuffer();
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<String> openBillRoundList = (ArrayList<String>) session
				.get("OpenBillRoundListMap");
		if (null != openBillRoundList && openBillRoundList.size() > 0) {
			String openBillRound = "''";
			for (int i = 0; i < openBillRoundList.size(); i++) {
				openBillRound += ",'" + openBillRoundList.get(i) + "'";
			}
			List<OnlineBatchBillingDetails> currentlyRunningJobsList = OnlineBatchBillingDAO
					.getCurrentlyRunningJobSummary(openBillRound, this.userId);
			OnlineBatchBillingDetails onlineBatchBillingDetails = null;
			if (null != currentlyRunningJobsList
					&& currentlyRunningJobsList.size() > 0) {
				htmlCodeSB
						.append("<font color=\"blue\"><b>Currently Submitted Job(s)</b></font>");
				htmlCodeSB
						.append("<div style=\"color: blue; align: center;width:100%;height:150px;overflow:auto;\">");
				htmlCodeSB
						.append("<table class=\"table-grid\" width=\"100%\">");
				htmlCodeSB.append("<tr>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"5%\"><font size=\"1\">SL</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">BILL ROUND</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"10%\"><font size=\"1\">MRKEY</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"5%\"><font size=\"1\">ZONE</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"5%\"><font size=\"1\">MR NO</font></th>");
				// htmlCodeSB
				// .append("<th align=\"left\" width=\"20%\"><font size=\"1\">AREA</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"> <font size=\"1\">SUMBITED BY</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">SUBMITED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\"><font size=\"1\">BILING STARTED ON</font></th>");
				// htmlCodeSB
				// .append("<th align=\"center\" width=\"15%\"><font size=\"1\">LAST BILL GENERATED ON</font></th>");
				htmlCodeSB
						.append("<th align=\"center\" width=\"15%\" title=\"Please Click details for Latest Summary\"><font size=\"1\">ACTION</font></th>");
				htmlCodeSB.append("</tr>");
				for (int i = 0; i < currentlyRunningJobsList.size(); i++) {
					onlineBatchBillingDetails = (OnlineBatchBillingDetails) currentlyRunningJobsList
							.get(i);
					htmlCodeSB
							.append("<tr onclick=\"fnShowDetails('"
									+ onlineBatchBillingDetails.getMrKey()
									+ "');\" onMouseOver=\"this.bgColor='yellow';\"	onMouseOut=\"this.bgColor='white';\" title=\"MRKEY: "
									+ onlineBatchBillingDetails.getMrKey()
									+ "\nZone: "
									+ onlineBatchBillingDetails.getZone()
									+ "\nMR No: "
									+ onlineBatchBillingDetails.getMrNo()
									+ "\nArea: "
									+ onlineBatchBillingDetails.getArea()
									+ "\nSubmitted By: "
									+ onlineBatchBillingDetails
											.getInitiatedBy()
									+ "\nSubmitted on: "
									+ onlineBatchBillingDetails
											.getInitiatedOn()
									+ "\nBilling Started on: "
									+ onlineBatchBillingDetails.getStartedOn()
									+ "\n\nPlease Click for Latest Summary.\t\t\t\t\">");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getSlNo() + "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getBillRound()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\">"
							+ onlineBatchBillingDetails.getMrKey() + "</td>");
					// htmlCodeSB.append("<td align=\"center\">"
					// + onlineBatchBillingDetails.getZone() + "</td>");
					// htmlCodeSB.append("<td align=\"center\">"
					// + onlineBatchBillingDetails.getMrNo() + "</td>");
					// htmlCodeSB.append("<td align=\"left\">"
					// + onlineBatchBillingDetails.getArea() + "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap><b>"
							+ onlineBatchBillingDetails.getInitiatedBy()
							+ "</b></td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getInitiatedOn()
							+ "</td>");
					htmlCodeSB.append("<td align=\"center\" nowrap>"
							+ onlineBatchBillingDetails.getStartedOn()
							+ "</td>");
					htmlCodeSB
							.append("<td align=\"center\"><a href=\"#\">Details</a></td>");
					htmlCodeSB.append("</tr>");
				}
				htmlCodeSB.append("</table>");
				htmlCodeSB.append("</div>");
			} else {
				htmlCodeSB
						.append("<br/><font color=\"red\"><b>Currently no Job is Running or Submitted by any User.</b></font>");
			}
		}
		return htmlCodeSB.toString();
	}

	/**
	 * <p>
	 * This method is used to remove Last comma.
	 * </p>
	 * 
	 * @param str
	 * @return String
	 */
	public String removeLastComma(String str) {
		if (null != str && str.length() != 0
				&& str.lastIndexOf(',') + 1 == str.length()) {
			return removeLastComma(str.substring(0, str.lastIndexOf(',')));
		} else {
			return str;
		}
	}

	/**
	 * <p>
	 * This method is used to setColorCode for table row.
	 * </p>
	 * 
	 * @param c
	 * @return String
	 */
	private String setColorCode(int c) {
		String colorCode = "#fff";
		switch (c) {
		case 0:
			colorCode = "#E3E3E3";
			break;
		case 1:
			colorCode = "#CDCDCD";
			break;
		case 2:
			colorCode = "#8A8A8A";
			break;
		case 3:
			colorCode = "#F6EED4";
			break;
		case 4:
			colorCode = "#EAD699";
			break;
		case 5:
			colorCode = "#D6AD33";
			break;
		case 6:
			colorCode = "#E6E6FA";
			break;
		case 7:
			colorCode = "#B3B3F0";
			break;
		case 8:
			colorCode = "#8080E6";
			break;
		case 9:
			colorCode = "#C2F0F0";
			break;
		case 10:
			colorCode = "#85E0E0";
			break;
		case 11:
			colorCode = "#00E6E6";
			break;
		case 12:
			colorCode = "#C2F0C2";
			break;
		case 13:
			colorCode = "#99E699";
			break;
		case 14:
			colorCode = "#C2AD99";
			break;
		case 15:
			colorCode = "#855C33";
			break;
		default:
			colorCode = "#fff";
			break;
		}
		return colorCode;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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
	 * @param mrKeyListForSearch
	 *            the mrKeyListForSearch to set
	 */
	public void setMrKeyListForSearch(String mrKeyListForSearch) {
		this.mrKeyListForSearch = mrKeyListForSearch;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param searchBy
	 *            the searchBy to set
	 */
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	/**
	 * @param selectedArea
	 *            the selectedArea to set
	 */
	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

	/**
	 * @param selectedBillRound
	 *            the selectedBillRound to set
	 */
	public void setSelectedBillRound(String selectedBillRound) {
		this.selectedBillRound = selectedBillRound;
	}

	/**
	 * @param selectedErrorCode
	 *            the selectedErrorCode to set
	 */
	public void setSelectedErrorCode(String selectedErrorCode) {
		this.selectedErrorCode = selectedErrorCode;
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
	 * @param selectedProcessCounter
	 *            the selectedProcessCounter to set
	 */
	public void setSelectedProcessCounter(String selectedProcessCounter) {
		this.selectedProcessCounter = selectedProcessCounter;
	}

	/**
	 * @param selectedStatusCode
	 *            the selectedStatusCode to set
	 */
	public void setSelectedStatusCode(String selectedStatusCode) {
		this.selectedStatusCode = selectedStatusCode;
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
	 * @param summaryFor
	 *            the summaryFor to set
	 */
	public void setSummaryFor(String summaryFor) {
		this.summaryFor = summaryFor;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param totalRecordsBillable
	 *            the totalRecordsBillable to set
	 */
	public void setTotalRecordsBillable(int totalRecordsBillable) {
		this.totalRecordsBillable = totalRecordsBillable;
	}

	/**
	 * @param totalRecordsBilled
	 *            the totalRecordsBilled to set
	 */
	public void setTotalRecordsBilled(int totalRecordsBilled) {
		this.totalRecordsBilled = totalRecordsBilled;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
