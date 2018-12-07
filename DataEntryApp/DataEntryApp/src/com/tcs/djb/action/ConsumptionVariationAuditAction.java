/************************************************************************************************************
 * @(#) ConsumptionVariationAuditAction.java   09-03-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.ConsumptionVariationAuditDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.ConsumptionAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is the Action class for ajax calls made saving Audit Findings
 * </p>
 * 
 * @author Atanu Mondal (Tata Consultancy Services)
 * @history 09-03-2016 Atanu added method {@link #saveAuditFindings()} to save
 *          Audit Findings
 * 
 */
@SuppressWarnings("deprecation")
public class ConsumptionVariationAuditAction extends ActionSupport implements
		ServletResponseAware {

	private static final String SCREEN_ID = "42";
	private String hidAction;
	private String selectedZROCode;
	private String kno;
	private String selectedVarAnualAvgConsumption;
	private String selectedBillRound;
	private String selectedSavedResult;
	private String selectedVarPrevReading;
	private String selectedLastAuditedBeforeDate;
	private String selectedConsumptionVariationAuditStatus;
	private String searchZROCode;
	ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria;
	private String pageNo;
	private String maxRecordPerPage;
	private int totalRecords;
	List<String> pageNoDropdownList;
	private HttpServletResponse response;
	List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList;
	private InputStream inputStream;
	private String data;

	/**
	 * method for all ajax call while saving Audit Findings
	 * 
	 * @return
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
			if ("populateKnoAuditDetail".equalsIgnoreCase(hidAction)) {
				List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList = new ArrayList<MeterReadImgAuditDetails>();
				meterReadImgAuditDetailsList = ConsumptionVariationAuditDAO
						.getKnoAuditDetailRecord(kno);
				MeterReadImgAuditDetails meterReadImgAuditDetails = null;
				StringBuffer knoDetailTable = new StringBuffer();
				knoDetailTable
						.append("<table class=\"table-grid\" align=\"center\" width=\"100%\"><tr><td align=\"center\"><strong>Bill Round</strong></td><td align=\"center\"><strong>Bill Period</strong></td><td align=\"center\"><strong>Bill Gen. By</strong></td><td align=\"center\" ><strong>Bill Date</strong></td><td align=\"center\"><strong>Meter Read Remark</strong></td><td align=\"center\" ><strong>Consumption</strong></td><td align=\"center\" ><strong>Variation From Prev Round</strong></td><td align=\"center\" ><strong>Variation From Avg. Consumption</strong></td><td align=\"center\" ><strong>Bill Amount</strong></td><td align=\"center\" ><strong>Payment Amount</strong></td></tr>");
				if (null != meterReadImgAuditDetailsList
						&& meterReadImgAuditDetailsList.size() > 0) {
					for (int i = 0; i < meterReadImgAuditDetailsList.size(); i++) {
						meterReadImgAuditDetails = (MeterReadImgAuditDetails) meterReadImgAuditDetailsList
								.get(i);
						knoDetailTable.append("<tr>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getBillRound()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getBillingPeriod()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getBillGeneratedBy()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getBillDt()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getMeterReadRemark()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getConsumption()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails
										.getVariationPrevusReadng() + "</td>");
						knoDetailTable
								.append("<td align=\"center\">"
										+ meterReadImgAuditDetails
												.getPerVariationAvgCnsumptn()
										+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getBillAmt()
								+ "</td>");
						knoDetailTable.append("<td align=\"center\">"
								+ meterReadImgAuditDetails.getPaymentAmount()
								+ "</td>");
						knoDetailTable.append("</tr>");
					}

				}
				knoDetailTable.append("</table>");
				inputStream = (new StringBufferInputStream(knoDetailTable
						.toString()));
			}
			if ("updateKnoAuditDetail".equalsIgnoreCase(hidAction)) {
				String updateResponse = updateKnoAuditDetails();
				inputStream = new StringBufferInputStream(updateResponse);

			}
			/*
			 * Start : Atanu on 09-03-2016 Added saveAuditFindings to save audit
			 * findings.
			 */
			if ("saveAuditFindings".equalsIgnoreCase(hidAction)) {
				String saveAuditFindingsResponse = saveAuditFindings();
				inputStream = new StringBufferInputStream(
						saveAuditFindingsResponse);

			}
			/*
			 * End : Atanu on 09-03-2016 Added saveAuditFindings to save audit
			 * findings.
			 */

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
	 * This method is to check is used to save audit findings
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal
	 * @since 09-03-2016
	 * 
	 */
	private String saveAuditFindings() {
		String saveAuditFindingsResponse = "SUCCESS";
		int updatedRow = 0;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String input;
			JSONObject jsonObj = new JSONObject(data);
			input = jsonObj.getString("JsonList");
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<MeterReadImgAuditDetails>>() {
			}.getType();
			ArrayList<MeterReadImgAuditDetails> auditList = gson.fromJson(
					input, collectionType);
			AppLog.info("auditList.size() " + auditList.size());
			if (null != auditList && auditList.size() > 0) {
				for (MeterReadImgAuditDetails obj : auditList) {
					obj.setFinalAuditBy(userId);
					obj.setLastUpdatedBy(userId);
					obj
							.setLastAuditStatus(DJBConstants.KNO_AUDIT_COMPLETE_STATUS);
					AppLog.info("\n obj.getKno() " + obj.getKno()
							+ "\n obj.getBillRound() " + obj.getBillRound()
							+ "\n obj.getConsumptnVariatnReasn() "
							+ obj.getConsumptnVariatnReasn()
							+ "\n obj.getSatsfctryReadngReasn() "
							+ obj.getSatsfctryReadngReasn()
							+ "\n obj.getNonSatsfctryReadngReasn() "
							+ obj.getNonSatsfctryReadngReasn()
							+ "\n obj.getSugstdAuditAction() "
							+ obj.getSugstdAuditAction()
							+ "\n obj.getFinalAuditBy() "
							+ obj.getFinalAuditBy()
							+ "\n obj.getLastUpdatedBy() "
							+ obj.getLastUpdatedBy());
					updatedRow += ConsumptionVariationAuditDAO
							.saveAuditFindings(obj);

				}
			}

			if (updatedRow > 0) {
				session
						.put("SERVER_MESSAGE",
								"<font color='#33CC33'><b>Audit Findings Saved Successfully</b></font>");
				saveAuditFindingsResponse = "<font color='#33CC33'><b>Audit Findings Saved Successfully</b></font>";
			} else {
				saveAuditFindingsResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			}
		} catch (Exception e) {
			saveAuditFindingsResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		return saveAuditFindingsResponse;
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
			if (null == hidAction) {
				loadDefault();
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "Next".equalsIgnoreCase(hidAction)
					|| "Prev".equalsIgnoreCase(hidAction)
					|| "refresh".equalsIgnoreCase(hidAction)) {
				AppLog
						.info("Inside Execute Method for consumption variation Search with \n hidAction : "
								+ hidAction
								+ "\n selectedLastAuditedBeforeDate : "
								+ selectedLastAuditedBeforeDate
								+ "\n selectedVarAnualAvgConsumption : "
								+ selectedVarAnualAvgConsumption
								+ "\n selectedVarPrevReading : "
								+ selectedVarPrevReading
								+ "\nsearchZROCode : "
								+ searchZROCode
								+ "\n kno : "
								+ kno
								+ "\n selectedBillRound : "
								+ selectedBillRound
								+ "\n selectedConsumptionVariationAuditStatus : "
								+ this.selectedConsumptionVariationAuditStatus);
				consumptionAuditSearchCriteria = new ConsumptionAuditSearchCriteria();
				if (null != selectedSavedResult
						&& !"".equalsIgnoreCase(selectedSavedResult)) {
					/* Set Search criteria */
					AppLog.info("\n####################Searching From Saved Result ::"+selectedSavedResult);
					Map<String,String> savedResuldSelectedMap = new HashMap<String, String>();
					String keyValueWithBracket = selectedSavedResult.substring(2, selectedSavedResult.length()-2);
					String replacedString = keyValueWithBracket.replace("][", "~");
					String[] keyValuePair =  replacedString.split("~");
					for (int i=0;i<keyValuePair.length;i++) {
					    String pair = keyValuePair[i];
					    String[] keyValue = pair.split(":");
					    savedResuldSelectedMap.put(keyValue[0], keyValue[1]);
					}
					for (Map.Entry<String, String> entry : savedResuldSelectedMap.entrySet())
					{
						if("AUDIT_STATUS".equalsIgnoreCase(entry.getKey())){
							selectedConsumptionVariationAuditStatus = entry.getValue();	
							AppLog.info("\n####################Searching From Saved Result selectedConsumptionVariationAuditStatus::"+selectedConsumptionVariationAuditStatus);
						}
						if("VARIATION_PREVIOUS_ROUND".equalsIgnoreCase(entry.getKey())){
							selectedVarPrevReading = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result selectedVarPrevReading::"+selectedVarPrevReading);
						}
						if("AVG_CONSUMPTION".equalsIgnoreCase(entry.getKey())){
							selectedVarAnualAvgConsumption = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result selectedVarAnualAvgConsumption::"+selectedVarAnualAvgConsumption);
						}
						if("LAST_AUDITED".equalsIgnoreCase(entry.getKey())){
							selectedLastAuditedBeforeDate = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result selectedLastAuditedBeforeDate::"+selectedLastAuditedBeforeDate);
						}
						if("KNO".equalsIgnoreCase(entry.getKey())){
							kno = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result kno::"+kno);
						}
						if("BILL_ROUND".equalsIgnoreCase(entry.getKey())){
							selectedBillRound = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result selectedBillRound::"+selectedBillRound);
						}
						if("ZRO".equalsIgnoreCase(entry.getKey())){
							searchZROCode = entry.getValue();
							selectedZROCode = entry.getValue();
							AppLog.info("\n####################Searching From Saved Result searchZROCode::"+searchZROCode);
						}
					}
					AppLog.info("\n####################selectedConsumptionVariationAuditStatus ############# "+selectedConsumptionVariationAuditStatus);

				}
				AppLog.info("\n###########searchZROCode is "+searchZROCode+"\n###########selectedZROCode is "+selectedZROCode);
				if (null != searchZROCode
						&& !"".equalsIgnoreCase(searchZROCode)) {
					/* Set Search criteria */
					AppLog.info("\n###########selectedZROCode is not null");
					AppLog.info("\n###########searchZROCode is ::"+searchZROCode);
					consumptionAuditSearchCriteria
							.setSearchZROCode(searchZROCode);

				}
				if (null != kno && !"".equalsIgnoreCase(kno)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria.setSearchKno(kno);
				}
				if (null != selectedBillRound
						&& !"".equalsIgnoreCase(selectedBillRound)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria
							.setSearchBillRound(selectedBillRound);
				}
				if (null != selectedVarAnualAvgConsumption
						&& !"".equalsIgnoreCase(selectedVarAnualAvgConsumption)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria
							.setSearchVarAnualAvgConsumption(selectedVarAnualAvgConsumption);
				}
				if (null != selectedVarPrevReading
						&& !"".equalsIgnoreCase(selectedVarPrevReading)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria
							.setSearchVarPrevReading(selectedVarPrevReading);
				}
				if (null != selectedLastAuditedBeforeDate
						&& !"".equalsIgnoreCase(selectedLastAuditedBeforeDate)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria
							.setSearchLastAuditedBeforeDate(selectedLastAuditedBeforeDate);
				}
				if (null != selectedConsumptionVariationAuditStatus
						&& !""
								.equalsIgnoreCase(selectedConsumptionVariationAuditStatus)
						&& !"ALL"
								.equalsIgnoreCase(selectedConsumptionVariationAuditStatus)) {
					/* Set Search criteria */
					consumptionAuditSearchCriteria
							.setSearchConsumptionVariationAuditStatus(selectedConsumptionVariationAuditStatus);
				}
				if (null == this.pageNo || "".equals(this.pageNo)) {
					consumptionAuditSearchCriteria.setPageNo("1");
				} else {
					consumptionAuditSearchCriteria.setPageNo(pageNo);
				}
				if (null == this.maxRecordPerPage
						|| "".equals(this.maxRecordPerPage)) {
					maxRecordPerPage = null != PropertyUtil
							.getProperty("AUDIT_RECORDS_PER_PAGE") ? PropertyUtil
							.getProperty("AUDIT_RECORDS_PER_PAGE")
							: "5";
					consumptionAuditSearchCriteria
							.setRecordPerPage(maxRecordPerPage);
				} else {
					consumptionAuditSearchCriteria
							.setRecordPerPage(maxRecordPerPage);
				}
				if (null != userId || "".equalsIgnoreCase(userId)) {
					consumptionAuditSearchCriteria.setUserId(userId);
				}
				AppLog
						.info("Inside Execute Method for consumption variation Search with \n hidAction : "
								+ hidAction
								+ "\n selectedLastAuditedBeforeDate : "
								+ selectedLastAuditedBeforeDate
								+ "\n selectedVarAnualAvgConsumption : "
								+ selectedVarAnualAvgConsumption
								+ "\n selectedVarPrevReading : "
								+ selectedVarPrevReading
								+ "\nsearchZROCode : "
								+ searchZROCode
								+ "\n kno : "
								+ kno
								+ "\n selectedBillRound : "
								+ selectedBillRound
								+ "\n selectedConsumptionVariationAuditStatus : "
								+ selectedConsumptionVariationAuditStatus);

				result = searchConsumptionVaritionAuditRecords(consumptionAuditSearchCriteria);
				session.remove("SERVER_MESSAGE");
				session.remove("AJAX_MESSAGE");
				session.put("savedResultList", ConsumptionVariationAuditDAO
						.getSearchedResultList(userId));
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
	 * @return the consumptionAuditSearchCriteria
	 */
	public ConsumptionAuditSearchCriteria getConsumptionAuditSearchCriteria() {
		return consumptionAuditSearchCriteria;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
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
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the meterReadImgAuditDetailsList
	 */
	public List<MeterReadImgAuditDetails> getMeterReadImgAuditDetailsList() {
		return meterReadImgAuditDetailsList;
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
	 * @return the searchZROCode
	 */
	public String getSearchZROCode() {
		return searchZROCode;
	}

	/**
	 * @return the selectedBillRound
	 */
	public String getSelectedBillRound() {
		return selectedBillRound;
	}

	/**
	 * @return the selectedConsumptionVariationAuditStatus
	 */
	public String getSelectedConsumptionVariationAuditStatus() {
		return selectedConsumptionVariationAuditStatus;
	}

	/**
	 * @return the selectedLastAuditedBeforeDate
	 */
	public String getSelectedLastAuditedBeforeDate() {
		return selectedLastAuditedBeforeDate;
	}

	/**
	 * @return the selectedSavedResult
	 */
	public String getSelectedSavedResult() {
		return selectedSavedResult;
	}

	/**
	 * @return the selectedVarAnualAvgConsumption
	 */
	public String getSelectedVarAnualAvgConsumption() {
		return selectedVarAnualAvgConsumption;
	}

	/**
	 * @return the selectedVarPrevReading
	 */
	public String getSelectedVarPrevReading() {
		return selectedVarPrevReading;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * <p>
	 * Populate default values required for Consumption Variation Audit Screen.
	 * </p>
	 */
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.remove("SERVER_MESSAGE");
			session.remove("AJAX_MESSAGE");
			session.put("CURR_TAB", "AUDIT");
			session.put("ZROListMap", GetterDAO.getZROListMap());
			if (null == session.get("selectedBillRoundList")) {
				session.put("selectedBillRoundList",
						ConsumptionVariationAuditDAO.getPreviousBillRounds());
			}
			String perVarAnualAvgConsumption = DJBConstants.RANGE_ANNUAL_AVERAGE_CONSUMPTION;
			List<String> perVarAnualAvgConsumptionList = Arrays
					.asList(perVarAnualAvgConsumption.split("\\s*,\\s*"));
			if (null == session.get("perVarAnualAvgConsumptionList")) {
				session.put("perVarAnualAvgConsumptionList",
						perVarAnualAvgConsumptionList);
			}
			String perVarPreviousRound = DJBConstants.RANGE_VARIATION_PREVIOUS_ROUND;
			List<String> perVarPreviousRoundList = Arrays
					.asList(perVarPreviousRound.split("\\s*,\\s*"));
			if (null == session.get("perVarPreviousRoundList")) {
				session.put("perVarPreviousRoundList", perVarPreviousRoundList);
			}
			String consumptionVariationAuditStatus = DJBConstants.CONSUMPTION_VARIATION_AUDIT_STATUS;
			List<String> consumptionVariationAuditStatusList = Arrays
					.asList(consumptionVariationAuditStatus.split("\\s*,\\s*"));
			if (null == session.get("consumptionVariationAuditStatusList")) {
				session.put("consumptionVariationAuditStatusList",
						consumptionVariationAuditStatusList);
			}
			/*
			 * Start : Atanu on 04-03-2016 added the below code for Audit
			 * findings screen
			 */
			String abnormalConsumptionReason = DJBConstants.ABNORMAL_CONSUMPTION_REASON;
			List<String> abnormalConsumptionReasonList = Arrays
					.asList(abnormalConsumptionReason.split("\\s*,\\s*"));
			if (null == session.get("abnormalConsumptionReasonList")) {
				session.put("abnormalConsumptionReasonList",
						abnormalConsumptionReasonList);
			}
			String nonSatisfactoryReason = DJBConstants.NON_SATISFACTORY_REASON;
			List<String> nonSatisfactoryReasonList = Arrays
					.asList(nonSatisfactoryReason.split("\\s*,\\s*"));
			if (null == session.get("nonSatisfactoryReasonList")) {
				session.put("nonSatisfactoryReasonList",
						nonSatisfactoryReasonList);
			}
			String satisfactoryReason = DJBConstants.SATISFACTORY_REASON;
			List<String> satisfactoryReasonList = Arrays
					.asList(satisfactoryReason.split("\\s*,\\s*"));
			if (null == session.get("satisfactoryReasonList")) {
				session.put("satisfactoryReasonList", satisfactoryReasonList);
			}
			String suggestedAction = DJBConstants.SUGGESTED_AUDIT_ACTION;
			List<String> suggestedActionList = Arrays.asList(suggestedAction
					.split("\\s*,\\s*"));
			if (null == session.get("suggestedActionList")) {
				session.put("suggestedActionList", suggestedActionList);
			}
			String auditUserID = (String) session.get("userId");
			if (null == session.get("auditUserID")) {
				session.put("auditUserID", auditUserID);
			}
			/*
			 * End : Atanu on 04-03-2016 added the below code for Audit findings
			 * screen
			 */

			if (null == selectedConsumptionVariationAuditStatus) {
				selectedConsumptionVariationAuditStatus = DJBConstants.SEARCH_AUDIT_STATUS_DEFAULT;
			}
			if (null == session.get("savedResultList")) {
				String loggedInId = (String) session.get("userId");
				List<String> searchedResultList = new ArrayList<String>();
				searchedResultList = ConsumptionVariationAuditDAO
						.getSearchedResultList(loggedInId);
				session.put("savedResultList", searchedResultList);
			}

		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * This Method returns the records to be audited for consumption varition
	 * 
	 * @author 830700
	 * @since 18-01-2016
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	private String searchConsumptionVaritionAuditRecords(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		AppLog.begin();
		String result = "success";
		int searchHistRecord = 0;
		try {
			if (null == this.pageNo || "".equals(this.pageNo)) {
				this.pageNo = "1";
				this.maxRecordPerPage = null != PropertyUtil
						.getProperty("AUDIT_RECORDS_PER_PAGE") ? PropertyUtil
						.getProperty("AUDIT_RECORDS_PER_PAGE") : "5";
			}
			searchHistRecord = ConsumptionVariationAuditDAO
					.getSearchRecordCount(consumptionAuditSearchCriteria);
			if (searchHistRecord < 1) {
				ConsumptionVariationAuditDAO
						.insertSearchRecord(consumptionAuditSearchCriteria);
			}
			this.totalRecords = ConsumptionVariationAuditDAO
					.getTotalCountOfAuditDetailsList(consumptionAuditSearchCriteria);
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
				this.meterReadImgAuditDetailsList = (ArrayList<MeterReadImgAuditDetails>) ConsumptionVariationAuditDAO
						.getConsumerDetailsListForConsumptionAudit(consumptionAuditSearchCriteria);
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
	 * @param consumptionAuditSearchCriteria
	 *            the consumptionAuditSearchCriteria to set
	 */
	public void setConsumptionAuditSearchCriteria(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		this.consumptionAuditSearchCriteria = consumptionAuditSearchCriteria;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
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
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param meterReadImgAuditDetailsList
	 *            the meterReadImgAuditDetailsList to set
	 */
	public void setMeterReadImgAuditDetailsList(
			List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList) {
		this.meterReadImgAuditDetailsList = meterReadImgAuditDetailsList;
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
	 * @param searchZROCode
	 *            the searchZROCode to set
	 */
	public void setSearchZROCode(String searchZROCode) {
		this.searchZROCode = searchZROCode;
	}

	/**
	 * @param selectedBillRound
	 *            the selectedBillRound to set
	 */
	public void setSelectedBillRound(String selectedBillRound) {
		this.selectedBillRound = selectedBillRound;
	}

	/**
	 * @param selectedConsumptionVariationAuditStatus
	 *            the selectedConsumptionVariationAuditStatus to set
	 */
	public void setSelectedConsumptionVariationAuditStatus(
			String selectedConsumptionVariationAuditStatus) {
		this.selectedConsumptionVariationAuditStatus = selectedConsumptionVariationAuditStatus;
	}

	/**
	 * @param selectedLastAuditedBeforeDate
	 *            the selectedLastAuditedBeforeDate to set
	 */
	public void setSelectedLastAuditedBeforeDate(
			String selectedLastAuditedBeforeDate) {
		this.selectedLastAuditedBeforeDate = selectedLastAuditedBeforeDate;
	}

	/**
	 * @param selectedSavedResult
	 *            the selectedSavedResult to set
	 */
	public void setSelectedSavedResult(String selectedSavedResult) {
		this.selectedSavedResult = selectedSavedResult;
	}

	/**
	 * @param selectedVarAnualAvgConsumption
	 *            the selectedVarAnualAvgConsumption to set
	 */
	public void setSelectedVarAnualAvgConsumption(
			String selectedVarAnualAvgConsumption) {
		this.selectedVarAnualAvgConsumption = selectedVarAnualAvgConsumption;
	}

	/**
	 * @param selectedVarPrevReading
	 *            the selectedVarPrevReading to set
	 */
	public void setSelectedVarPrevReading(String selectedVarPrevReading) {
		this.selectedVarPrevReading = selectedVarPrevReading;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * <p>
	 * This method is used to update KNO Audit Details
	 * </p>
	 * 
	 * @return updateAuditResponse
	 */
	public String updateKnoAuditDetails() {
		AppLog.begin();
		String updateAuditResponse = "SUCCESS";
		int updatedRow = 0;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String input;
			JSONObject jsonObj = new JSONObject(data);
			input = jsonObj.getString("JsonList");
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<MeterReadImgAuditDetails>>() {
			}.getType();
			ArrayList<MeterReadImgAuditDetails> auditList = gson.fromJson(
					input, collectionType);
			AppLog.info("auditList.size() " + auditList.size());
			if (null != auditList && auditList.size() > 0) {
				for (MeterReadImgAuditDetails obj : auditList) {
					obj.setSiteVistGivenBy(userId);
					AppLog.info(" obj.getAssignTo() " + obj.getAssignTo()
							+ " obj.getSiteVistRequrd() "
							+ obj.getSiteVistRequrd());
					if (DJBConstants.KNO_AUDIT_SELF_ASSIGNTO
							.equalsIgnoreCase(obj.getAssignTo())) {
						obj.setAssignTo(userId);
						obj.setAssignToFlg("S");
					} else if (DJBConstants.KNO_AUDIT_MR_ASSIGNTO
							.equalsIgnoreCase(obj.getAssignTo())) {
						obj.setAssignTo(DJBConstants.KNO_AUDIT_MR_ASSIGNTO);
						obj.setAssignToFlg("M");
					} else {
						obj.setAssignTo(DJBConstants.KNO_AUDIT_MR_ASSIGNTO);
						obj.setAssignToFlg("NA");
					}
					if (null != obj.getSiteVistRequrd()
							&& "YES".equalsIgnoreCase(obj.getSiteVistRequrd())) {
						AppLog.info("Setting SiteVistRequrd Y");
						obj.setSiteVistRequrd("Y");
						obj
								.setLastAuditStatus(DJBConstants.KNO_AUDIT_INPROGRESS_STATUS);
						obj.setLastAuditRmrk(DJBConstants.KNO_AUDIT_NO_REMARK);
					} else {
						AppLog.info("Setting SiteVistRequrd N");
						obj.setSiteVistRequrd("N");
						obj
								.setLastAuditStatus(DJBConstants.KNO_AUDIT_COMPLETE_STATUS);
						obj.setLastAuditRmrk(DJBConstants.KNO_AUDIT_OK_REMARK);
					}
					obj.setLastUpdatedBy(userId);
					updatedRow += ConsumptionVariationAuditDAO
							.updateKnoDetailAuditStatus(obj);

				}
			}

			if (updatedRow > 0) {
				session
						.put("SERVER_MESSAGE",
								"<font color='#33CC33'><b>Audit Record Updated Successfully</b></font>");
				updateAuditResponse = "<font color='#33CC33'><b>Audit Record Updated Successfully</b></font>";
			} else {
				updateAuditResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			}
		} catch (Exception e) {
			updateAuditResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		return updateAuditResponse;

	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

}
