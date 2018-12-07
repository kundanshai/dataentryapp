/************************************************************************************************************
 * @(#) MeterReplacementReviewAction.java   04 Feb 2014
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

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReplacementPreProcessor;
import com.tcs.djb.dao.MeterReplacementReviewDAO;
import com.tcs.djb.dao.NewConnectionDAO;
import com.tcs.djb.model.MeterReplacementReviewDetails;
import com.tcs.djb.services.MeterReplacementProcessService;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This Class is used for freezing of meterReplacement data entered by the Data
 * Entry Operators. various search criteria are available to find the records
 * </p>
 * 
 * @author Rajib Hazarika,
 * @history Rajib added method
 * @history 04-02-2014 Matiur Rahman Added functionality to display generic
 *          message in case of undefined error message as per JTrac ID DJB-2024
 *          updated on 04-02-2014 as per telephonic conversation with Amit Jain.
 *          There are modifications in the previous logic of sending the
 *          response for the AJax call. Earlier response was sent as comma
 *          separated value and parsed in JavaScript, now the response is set as
 *          the response to be sent to avoid JavaScript parsing, So that there
 *          is only one point of changing if required in future.
 * @history 14-02-2014 Matiur Rahman Passing User ID for Updating who columns in
 *          the freezing Process.
 * @history 10-03-2014 Matiur Rahman Implemented functionality as a part of
 *          changes as perJTrac ID# DJB-2024.
 */
@SuppressWarnings("deprecation")
public class MeterReplacementReviewAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "20";
	private static final long serialVersionUID = 1L;
	private String areaMR;
	private String billRoundId;
	private String billRoundList;
	private String hidAction;
	private InputStream inputStream;
	private String kno;
	private String knoList;
	private String knoMR;

	/**
	 * maximum No. of Records Per Page.
	 */
	private String maxRecordPerPage;
	private List<MeterReplacementReviewDetails> meterReplacementReviewDetailList;
	String meterReplacementStageId = PropertyUtil
			.getProperty("METER_REPLACEMENT_STAGE_ID");
	private String mrKeyList;
	private Map<String, String> mrKeyListMap;
	private String mrNoMR;
	/**
	 * Current Page no.
	 */
	private String pageNo;
	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;
	private HttpServletResponse response;
	private String searchFor;
	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;
	private Map<String, String> zoneListMap;
	private String zoneMR;
	private String zroLocation;
	/**
	 * <p>
	 * For all ajax call in meter replacement freeze screen
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
			// session.remove("DataSaved");
			if ("freezeMRD".equalsIgnoreCase(hidAction)) {
				if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
					addActionError(getText("error.access.denied"));
					inputStream = ScreenAccessValidator
							.ajaxResponse(getText("error.access.denied"));
					AppLog.end();
					return "success";
				}
				String[] knoListArray = knoList.substring(0,
						knoList.lastIndexOf(",")).split(",");
				String[] billRoundListArray = billRoundList.substring(0,
						billRoundList.lastIndexOf(",")).split(",");
				int noOfSuccessfulFreezedRecords = 0;
				for (int i = 0; i < knoListArray.length; i++) { // Call
					// ShiftSAForMeterReplacement
					// Class Here
					noOfSuccessfulFreezedRecords++;
					try {
						/*
						 * Start:Matiur Rahman on 14-02-2014 Added userId as
						 * parameter to update the who columns as per JTrac
						 * ID#2024.
						 */
						MeterReplacementPreProcessor mtrRplcPreProcessorObj = new MeterReplacementPreProcessor(
								knoListArray[i], meterReplacementStageId,
								billRoundListArray[i], userId);
						/*
						 * End:Matiur Rahman on 14-02-2014 Added userId as
						 * parameter to update the who columns as per JTrac
						 * ID#2024.
						 */
						Thread meterReplacementPreProcessor = new Thread(
								mtrRplcPreProcessorObj);
						meterReplacementPreProcessor.start();
						// meterReplacementPreProcessor.join();
					} catch (Exception e) {
						noOfSuccessfulFreezedRecords--;
					}
				}
				if (noOfSuccessfulFreezedRecords > 0) {
					session
							.put(
									"AJAX_MESSAGE",
									"<font color='#33CC33'><b>Last Action: For "
											+ noOfSuccessfulFreezedRecords
											+ " Records Freeze Process Initiated Successfully. Refresh/Re-search for Updates.</b></font>");
					inputStream = new StringBufferInputStream("For "
							+ noOfSuccessfulFreezedRecords
							+ " Records Freeze Process Initiated Successfully.");
				} else {
					session
							.put(
									"AJAX_MESSAGE",
									"<font color='red'><b>Records could not be Freezed Successfully. Please Retry.</b></font>");
					inputStream = (new StringBufferInputStream("ERROR"));
				}
			}
			// Start:Added by Rajib as per Jtrac DJB-2024
			if ("processMRD".equalsIgnoreCase(hidAction)) {
				if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
					addActionError(getText("error.access.denied"));
					inputStream = ScreenAccessValidator
							.ajaxResponse(getText("error.access.denied"));
					AppLog.end();
					return "success";
				}
				// changed for adding of Cookie for CCB authentication
				String authCookie = null;
				if (null != session.get("CCB_CRED")) {
					authCookie = (String) session.get("CCB_CRED");
				} else {
					addActionError(getText("error.access.denied"));
					inputStream = ScreenAccessValidator
							.ajaxResponse(getText("error.access.denied"));
					AppLog.end();
					return "success";
				}
				/*
				 * Start:11-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * track Processing.
				 */
				Map<String, String> inputMapToUpdateProcessingDetails = new HashMap<String, String>();
				// Account ID
				inputMapToUpdateProcessingDetails.put("kno", kno);
				// bill Round Id
				inputMapToUpdateProcessingDetails.put("billRoundId",
						billRoundId);
				// User Id of the User Logged in
				inputMapToUpdateProcessingDetails.put("userId", userId);
				/*
				 * Update the Processing details who columns.
				 */
				MeterReplacementReviewDAO
						.updateMeterReplacementProcessingDetails(inputMapToUpdateProcessingDetails);
				/*
				 * End:11-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * track Processing.
				 */
				Map<String, String> returnMap = MeterReplacementProcessService
						.processKno(kno, billRoundId, authCookie);
				if (null != returnMap) {
					String status = (String) (null != returnMap.get("status") ? returnMap
							.get("status")
							: "");
					String errorMsg = (String) (null != returnMap
							.get("errorMsg") ? returnMap.get("errorMsg") : "");
					String errorStatus = (String) (null != returnMap
							.get("errorStatus") ? returnMap.get("errorStatus")
							: "");
					if (null != status && "SUCCESS".equalsIgnoreCase(status)) {
						session
								.put(
										"AJAX_MESSAGE",
										"<font color='#33CC33'><b>Meter Repalcement Details for Kno: "
												+ kno
												+ " Processed Successfully. Refresh/Re-search for Updates.</b></font>");
						inputStream = new StringBufferInputStream(
								"<font color='#33CC33'><b>Meter Repalcement Details for Kno: "
										+ kno
										+ " Processed Successfully. Refresh/Re-search for Updates.</b></font>");

					} else {/*
							 * Start:04-02-2014 Matiur Rahman Added
							 * functionality to display generic message in case
							 * of undefined error message as per JTrac ID
							 * DJB-2024 updated on 04-02-2014
							 */
						if (null != errorMsg && !"".equals(errorMsg.trim())) {
							String ccbErrorMsg = errorMsg;
							String ccbErrorStatus = errorStatus;
							Map<String, String> inputMap = new HashMap<String, String>();
							// Account ID
							inputMap.put("kno", kno);
							// bill Round Id
							inputMap.put("billRoundId", billRoundId);
							// Retrieve the Error message set by CCB Service
							returnMap = MeterReplacementReviewDAO
									.retrievProcessingErrorMessage(inputMap);
							if (null != returnMap) {
								errorMsg = (String) (null != returnMap
										.get("errorMsg") ? returnMap
										.get("errorMsg") : "");
								errorStatus = (String) (null != returnMap
										.get("errorStatus") ? returnMap
										.get("errorStatus") : "");
							}
							if (null != errorMsg && !"".equals(errorMsg.trim())) {
								session
										.put(
												"AJAX_MESSAGE",
												"<font color='red'><b>Meter Repalcement Details for Kno:"
														+ kno
														+ " could not be processed Successfully.</b></font>");
								inputStream = new StringBufferInputStream(
										"Meter Repalcement Details for Kno:"
												+ kno
												+ " could not be processed Successfully. ERROR("
												+ errorStatus + "):" + errorMsg);
							} else {
								session
										.put(
												"AJAX_MESSAGE",
												"<font color='red'><b>Meter Repalcement Details for Kno:"
														+ kno
														+ " could not be processed Successfully.</b></font>");
								inputStream = new StringBufferInputStream(
										"Meter Repalcement Details for Kno:"
												+ kno
												+ " could not be processed Successfully. ERROR("
												+ ccbErrorStatus + "):"
												+ ccbErrorMsg);
							}

						} /*
						 * End:04-02-2014 Matiur Rahman Added functionality to
						 * display generic message in case of undefined error
						 * message as per JTrac ID DJB-2024 updated on
						 * 04-02-2014
						 */
						else {
							Map<String, String> inputMap = new HashMap<String, String>();
							// Error message to be set
							inputMap.put("errorMessage",
									DJBConstants.CCB_MTR_RPLC_SERVICE_ERR_MSG);
							inputMap
									.put(
											"errorStatusCode",
											DJBConstants.CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE);
							// Account ID
							inputMap.put("kno", kno);
							// bill Round Id
							inputMap.put("billRoundId", billRoundId);
							// User Id of the User Logged in
							inputMap.put("userId", userId);
							/*
							 * Update the Error message if any error occurs and
							 * CCB Service is unable to handle or sent as a
							 * response.
							 */
							MeterReplacementReviewDAO
									.updateProcessingErrorMessage(inputMap);
							session
									.put(
											"AJAX_MESSAGE",
											"<font color='red'><b>Meter Replacement Details for KNO: "
													+ (null == kno ? "" : kno)
													+ "Could not be Processed Successfully.</b></font>");
							inputStream = (new StringBufferInputStream(
									"Meter Repalcement could not be processed Successfully. ERROR("
											+ DJBConstants.CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE
											+ "):"
											+ DJBConstants.CCB_MTR_RPLC_SERVICE_ERR_MSG));
						}
					}
				} else {
					inputStream = new StringBufferInputStream(
							"Meter Replacement Details for KNO: "
									+ (null == kno ? "" : kno)
									+ "Could not be Processed Successfully.ERROR():CCB Connection.");
				}
			}
			// End:Added by Rajib as per Jtrac DJB-2024
		} catch (Exception e) {
			// e.printStackTrace();
			inputStream = new StringBufferInputStream(
					"ERROR:Meter Replacement Details for KNO: "
							+ (null == kno ? "" : kno)
							+ "Could not be Processed Successfully.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		AppLog.begin();
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String thirdPartyRole = PropertyUtil
					.getProperty("THIRD_PARTY_ROLE");
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			// Change start as per DJB-2024 by Rajib
			String userId = (String) session.get("userId");
			String userRole = (String) session.get("userRoleId");
			// System.out.println("Userid:"+session.get("userId").toString());
			/*
			 * mrKeyListMap= MeterReplacementReviewDAO.getMrKeyList(userId);
			 * System
			 * .out.println(":userId:"+userId+"mrkeyListMap size:"+mrKeyListMap
			 * .size()); if (null != mrKeyListMap){ session.put("MrKeyListMap",
			 * mrKeyListMap); } else{ session.put("MrKeyListMap", null); }
			 */
			zroLocation = NewConnectionDAO.getZROLocationByUserID(userId);
			// if (thirdPartyRole.equalsIgnoreCase(userRole)) {
			if (userRole.equalsIgnoreCase(thirdPartyRole)) {
				// if (null == session.get("ZoneListMap")) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
				// }
			} else {
				if (null != zroLocation && !"ABSENT".equals(zroLocation)) {
					this.zoneListMap = NewConnectionDAO
							.getZoneByZROLocation(zroLocation);
					session.remove("zroLocation");
					session.put("zroLocation", zroLocation);
					session.remove("ZoneListMap");
					session.put("ZoneListMap", zoneListMap);
					// System.out.println("Inside If,zrolocation is:"+zroLocation);
				} else {
					session.put("zroLocation", "ABSENT");
					this.zoneListMap = new HashMap<String, String>();
					// session.put("ZoneListMap", GetterDAO.getZoneListMap());
					session.remove("ZoneListMap");
					session.put("ZoneListMap", new HashMap<String, String>());
					// System.out.println("Inside Else,zrolocation is:"+zroLocation);
				}
			}
			// change finish by Rajib for DJB-2024
			// session.put("searchForMR", searchFor);
			session.put("MRReview", "Y");
			// if (null == session.get("ZoneListMap")) {
			// session.put("ZoneListMap", GetterDAO.getZoneListMap());
			// }
			if (null == session.get("MRNoListMap")
					|| ((HashMap<String, String>) session.get("MRNoListMap"))
							.isEmpty()) {
				if (null != zoneMR && !"".equals(zoneMR)) {
					session.put("MRNoListMap",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(zoneMR));
				} else {
					session.put("MRNoListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("AreaListMap")
					|| ((HashMap<String, String>) session.get("AreaListMap"))
							.isEmpty()) {
				if (null != zoneMR && !"".equals(zoneMR) && null != mrNoMR
						&& !"".equals(mrNoMR)) {
					session.put("AreaListMap",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									zoneMR, mrNoMR));
				} else {
					session.put("AreaListMap", new HashMap<String, String>());
				}
			}
			if (null == hidAction || "".equals(hidAction)) {
				session.put("SERVER_MESSAGE", "");
				session.put("AJAX_MESSAGE", "");
				this.pageNo = "1";
				this.maxRecordPerPage = PropertyUtil
						.getProperty("RECORDS_PER_PAGE");
			}
			// System.out.println("hidAction::" + hidAction);
			if (null == searchFor || "".equalsIgnoreCase(searchFor)) {
				session.put("SERVER_MESSAGE", "");
				session.put("AJAX_MESSAGE", "");
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "searchAfterFreeze".equalsIgnoreCase(hidAction)) {
				if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
					String mrKeyArray[] = mrKeyList.split(",");
					String tempMRKeyList = "";
					for (int i = 0; i < mrKeyArray.length; i++) {
						String tempString = mrKeyArray[i];
						if (null != tempString
								&& !"".equalsIgnoreCase(tempString.trim())
								&& UtilityForAll.isNumeric(tempString.trim())) {
							tempMRKeyList += "," + tempString;
						}
					}
					if (tempMRKeyList.indexOf(',') == 0) {
						tempMRKeyList = tempMRKeyList.substring(1);
					}
					mrKeyList = tempMRKeyList;
				}
				/*
				 * Start: Added by Rajib as per DJB-2024 for check mrkey entered
				 * by user in review Screen on 11-01-2014
				 */
				boolean hasAccess = true;
				if (!userRole.equalsIgnoreCase(thirdPartyRole)) {
					if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
						/*
						 * For mrkey Access check for the list of MRKeys
						 * provided and the in the screen checking the count of
						 * the MRKeys in the Access Table
						 */
						String mrKeyArray[] = mrKeyList.split(",");
						if (null != mrKeyArray
								&& mrKeyArray.length != MeterReplacementReviewDAO
										.getMRKeyCountForUser(userId, mrKeyList)) {
							hasAccess = false;
						}
					}
				}
				if (!hasAccess) {
					session
							.put(
									"SERVER_MESSAGE",
									"<font color=\"red\">Invalid MRKey, You mayn't have access to all the MRKeys that You have entered.</font>");
					session.put("AJAX_MESSAGE", "");
					return "success";
				} else {
					/*
					 * End: Added by Rajib as per DJB-2024 for check mrkey
					 * 11-01-2014
					 */
					if ((null == knoMR || "".equalsIgnoreCase(knoMR))
							&& (null == mrKeyList || ""
									.equalsIgnoreCase(mrKeyList))
							&& (null == zoneMR || "".equalsIgnoreCase(zoneMR))) {
						addActionError(getText("error.invalid.search.criteria"));
						AppLog.end();
						return "error";
					}
					if (null != knoMR && !"".equalsIgnoreCase(knoMR.trim())
							&& knoMR.length() != 10) {
						addActionError(getText("error.invalid.kno"));
						AppLog.end();
						return "error";
					}
					if ((null != zoneMR && !"".equalsIgnoreCase(zoneMR))
							&& ((null == mrNoMR || "".equalsIgnoreCase(mrNoMR)) || (null == areaMR || ""
									.equalsIgnoreCase(areaMR)))) {
						addActionError(getText("error.invalid.search.criteria"));
						AppLog.end();
						return "error";
					}
					if (null == this.pageNo || "".equals(this.pageNo)) {
						this.pageNo = "1";
						this.maxRecordPerPage = PropertyUtil
								.getProperty("RECORDS_PER_PAGE");
					}
					searchMeterReplacementDetails();
					// Map<String, String> inputMap = new HashMap<String,
					// String>();
					// inputMap.put("kno", knoMR);
					// inputMap.put("mrKeyList", mrKeyList);
					// inputMap.put("zone", zoneMR);
					// inputMap.put("mrNo", mrNoMR);
					// inputMap.put("area", areaMR);
					// inputMap.put("recordPerPage", maxRecordPerPage);
					// inputMap.put("pageNo", pageNo);
					// /*
					// * System.out.println("::search::knoMR::" + knoMR +
					// * "::mrKeyList::" + mrKeyList + "::zoneMR::" + zoneMR +
					// * "::mrNoMR::" + mrNoMR + "::areaMR::" + areaMR);
					// */
					// totalRecords = MeterReplacementReviewDAO
					// .getTotalCountOfMeterReplacementDetailRecords(inputMap);
					// if (totalRecords > 0) {
					// if (Integer.parseInt(maxRecordPerPage)
					// * Integer.parseInt(pageNo) > totalRecords) {
					// this.pageNo = Integer.toString((int) totalRecords
					// / Integer.parseInt(maxRecordPerPage) + 1);
					// }
					// /*
					// * System.out.println("::search::pageNo::" + pageNo +
					// * "::maxRecordPerPage::" + maxRecordPerPage +
					// * "::totalRecords::" + totalRecords);
					// */
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
					// this.meterReplacementReviewDetailList =
					// MeterReplacementReviewDAO
					// .getMeterReplacementDetailList(inputMap);
					// //
					// System.out.println(this.meterReplacementReviewDetailList.size());
					// } else {
					//
					// session
					// .put("SERVER_MESSAGE",
					// "<font color='red'><b>No Records Found for the search criteria.</b></font>");
					// }
				}
			}
			if ("freezeMRD".equalsIgnoreCase(hidAction)) {
				freezeMeterReplacementDetails();
				// System.out.println("knoList::" + knoList);
				// String[] knoListArray = knoList.substring(0,
				// knoList.lastIndexOf(",")).split(",");
				// String[] billRoundListArray = billRoundList.substring(0,
				// billRoundList.lastIndexOf(",")).split(",");
				// int noOfSuccessfulFreezedRecords = 0;
				//
				// for (int i = 0; i < knoListArray.length; i++) { // Call
				// // ShiftSAForMeterReplacement
				// // Class Here
				// noOfSuccessfulFreezedRecords++;
				// try {
				// MeterReplacementPreProcessor mtrRplcPreProcessorObj = new
				// MeterReplacementPreProcessor(
				// knoListArray[i], meterReplacementStageId,
				// billRoundListArray[i]);
				// Thread meterReplacementPreProcessor = new Thread(
				// mtrRplcPreProcessorObj);
				// meterReplacementPreProcessor.start();
				// // meterReplacementPreProcessor.join();
				// } catch (Exception e) {
				// noOfSuccessfulFreezedRecords--;
				//
				// }
				// }
				// if (noOfSuccessfulFreezedRecords > 0) {
				// session
				// .put(
				// "AJAX_MESSAGE",
				// "<font color='#33CC33'><b> Total "
				// + noOfSuccessfulFreezedRecords
				// + " Records Freezed Successfully.</b></font>");
				// searchMeterReplacementDetails();
				// // inputStream = new StringBufferInputStream(
				// // "SUCCESS:No Of Records Freezed "
				// // + noOfSuccessfulFreezedRecords);
				// } else {
				// session
				// .put(
				// "AJAX_MESSAGE",
				// "<font color='red'><b>Records could not be Freezed Successfully. Please Retry.</b></font>");
				// // inputStream = (new StringBufferInputStream(
				// //
				// "<font color='red'><b>Records could not be Freezed Successfully. Please Retry.</b></font>"));
				// }
			}
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Freeze Meter Replacement Details for the selected KNO.
	 * </p>
	 */
	public void freezeMeterReplacementDetails() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			// System.out.println("knoList::" + knoList);
			/*
			 * Start:Added by Matiur Rahman on 14-02-2014 to pass the user id to
			 * update the who columns as per JTrac ID#2024.
			 */
			String userId = (String) session.get("userId");
			/*
			 * End:Added by Matiur Rahman on 14-02-2014 to pass the user id to
			 * update the who columns as per JTrac ID#2024.
			 */
			String[] knoListArray = knoList.substring(0,
					knoList.lastIndexOf(",")).split(",");
			String[] billRoundListArray = billRoundList.substring(0,
					billRoundList.lastIndexOf(",")).split(",");
			int noOfSuccessfulFreezedRecords = 0;
			for (int i = 0; i < knoListArray.length; i++) { // Call
				// ShiftSAForMeterReplacement
				// Class Here
				noOfSuccessfulFreezedRecords++;
				try {
					/*
					 * Start:Matiur Rahman on 14-02-2014 Added userId as
					 * parameter to update the who columns as per JTrac ID#2024.
					 */
					MeterReplacementPreProcessor mtrRplcPreProcessorObj = new MeterReplacementPreProcessor(
							knoListArray[i], meterReplacementStageId,
							billRoundListArray[i], userId);
					/*
					 * End:Matiur Rahman on 14-02-2014 Added userId as parameter
					 * to update the who columns as per JTrac ID#2024.
					 */
					Thread meterReplacementPreProcessor = new Thread(
							mtrRplcPreProcessorObj);
					meterReplacementPreProcessor.start();
					meterReplacementPreProcessor.join();
				} catch (Exception e) {
					noOfSuccessfulFreezedRecords--;
				}
			}
			if (noOfSuccessfulFreezedRecords > 0) {
				session.put("AJAX_MESSAGE", "<font color='#33CC33'><b> Total "
						+ noOfSuccessfulFreezedRecords
						+ " Records Freezed Successfully.</b></font>");
				searchMeterReplacementDetails();
			} else {
				session
						.put(
								"AJAX_MESSAGE",
								"<font color='red'><b>Records could not be Freezed Successfully. Please Retry.</b></font>");
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
	}

	/**
	 * @return the areaMR
	 */
	public String getAreaMR() {
		return areaMR;
	}

	/**
	 * @return the billRoundId
	 */
	public String getBillRoundId() {
		return billRoundId;
	}

	/**
	 * @return the billRoundList
	 */
	public String getBillRoundList() {
		return billRoundList;
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
	 * @return the knoList
	 */
	public String getKnoList() {
		return knoList;
	}

	/**
	 * @return the knoMR
	 */
	public String getKnoMR() {
		return knoMR;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */

	/**
	 * @return the meterReplacementReviewDetailList
	 */
	public List<MeterReplacementReviewDetails> getMeterReplacementDetailList() {
		return meterReplacementReviewDetailList;
	}

	/**
	 * @return the mrKeyList
	 */
	public String getMrKeyList() {
		return mrKeyList;
	}

	/**
	 * @return the mrKeyListMap
	 */
	public Map<String, String> getMrKeyListMap() {
		return mrKeyListMap;
	}

	/**
	 * @return the mrNoMR
	 */
	public String getMrNoMR() {
		return mrNoMR;
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
	 * @return the searchFor
	 */
	public String getSearchFor() {
		return searchFor;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return the zoneListMap
	 */
	public Map<String, String> getZoneListMap() {
		return zoneListMap;
	}

	/**
	 * @return the zoneMR
	 */
	public String getZoneMR() {
		return zoneMR;
	}

	/**
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * <p>
	 * Search Meter Replacement Details for Freeze/review screen
	 * </p>
	 */
	public void searchMeterReplacementDetails() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("kno", knoMR);
			inputMap.put("mrKeyList", mrKeyList);
			inputMap.put("zone", zoneMR);
			inputMap.put("mrNo", mrNoMR);
			inputMap.put("area", areaMR);
			inputMap.put("recordPerPage", maxRecordPerPage);
			inputMap.put("pageNo", pageNo);
			inputMap.put("searchFor", searchFor);
			// Change by Rajib as per Jtrac DJB-2024 for Company Name condition
			// & to populate mrkeyList values without single quotes
			if (null != session.get("vendorName")) {
				inputMap.put("vendorName", (String) session.get("vendorName"));

			} else {
				inputMap.put("vendorName", null);
			}
			// Change finish by Rajib as per Jtrac DJB-2024 for Company Name
			// condition

			session.put("MR_kno", knoMR);
			session.put("MR_mrKeyList", mrKeyList);
			session.put("MR_zoneMR", zoneMR);
			session.put("MR_mrNoMR", mrNoMR);
			session.put("MR_areaMR", areaMR);
			session.put("MR_maxRecordPerPage", maxRecordPerPage);
			session.put("MR_pageNo", pageNo);
			session.put("MR_searchFor", searchFor);
			// System.out.println("::search::knoMR::" + knoMR + "::mrKeyList::"
			// + mrKeyList + "::zoneMR::" + zoneMR + "::mrNoMR::" + mrNoMR
			// + "::areaMR::" + areaMR + "::searchFor::" + searchFor);

			totalRecords = MeterReplacementReviewDAO
					.getTotalCountOfMeterReplacementDetailRecords(inputMap);
			// System.out.println("totalRecords::" + totalRecords);
			if (totalRecords > 0) {
				if (Integer.parseInt(maxRecordPerPage)
						* Integer.parseInt(pageNo) > totalRecords) {
					this.pageNo = Integer.toString((int) totalRecords
							/ Integer.parseInt(maxRecordPerPage) + 1);
				}

				// System.out.println("::search::pageNo::" + pageNo
				// + "::maxRecordPerPage::" + maxRecordPerPage
				// + "::totalRecords::" + totalRecords);

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
				this.meterReplacementReviewDetailList = MeterReplacementReviewDAO
						.getMeterReplacementDetailList(inputMap);
				this.hidAction = "search";
				session.put("SERVER_MESSAGE", "");
				// System.out.println(this.meterReplacementReviewDetailList.size());
			} else {

				session
						.put("SERVER_MESSAGE",
								"<font color='red'><b>No Records Found for the search criteria.</b></font>");
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
	}

	/**
	 * @param areaMR
	 *            the areaMR to set
	 */
	public void setAreaMR(String areaMR) {
		this.areaMR = areaMR;
	}

	/**
	 * @param billRoundId
	 *            the billRoundId to set
	 */
	public void setBillRoundId(String billRoundId) {
		this.billRoundId = billRoundId;
	}

	/**
	 * @param billRoundList
	 *            the billRoundList to set
	 */
	public void setBillRoundList(String billRoundList) {
		this.billRoundList = billRoundList;
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
	 * @param knoList
	 *            the knoList to set
	 */
	public void setKnoList(String knoList) {
		this.knoList = knoList;
	}

	/**
	 * @param knoMR
	 *            the knoMR to set
	 */
	public void setKnoMR(String knoMR) {
		this.knoMR = knoMR;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param meterReplacementReviewDetailList
	 *            the meterReplacementReviewDetailList to set
	 */
	public void setMeterReplacementDetailList(
			List<MeterReplacementReviewDetails> meterReplacementReviewDetailList) {
		this.meterReplacementReviewDetailList = meterReplacementReviewDetailList;
	}

	/**
	 * @param mrKeyList
	 *            the mrKeyList to set
	 */
	public void setMrKeyList(String mrKeyList) {
		this.mrKeyList = mrKeyList;
	}

	/**
	 * @param mrKeyListMap
	 */
	public void setMrKeyListMap(Map<String, String> mrKeyListMap) {
		this.mrKeyListMap = mrKeyListMap;
	}

	/**
	 * @param mrNoMR
	 *            the mrNoMR to set
	 */
	public void setMrNoMR(String mrNoMR) {
		this.mrNoMR = mrNoMR;
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
	 * @param searchFor
	 *            the searchFor to set
	 */
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param zoneListMap
	 */
	public void setZoneListMap(Map<String, String> zoneListMap) {
		this.zoneListMap = zoneListMap;
	}

	/**
	 * @param zoneMR
	 *            the zoneMR to set
	 */
	public void setZoneMR(String zoneMR) {
		this.zoneMR = zoneMR;
	}

	/**
	 * @param zroLocation
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}
}
