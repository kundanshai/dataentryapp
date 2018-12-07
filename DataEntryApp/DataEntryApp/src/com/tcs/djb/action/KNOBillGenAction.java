/************************************************************************************************************
 * @(#) KNOBillGenAction.java   26 Jun 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.facade.BillingFacade; //import com.tcs.djb.model.BillGenerationResponse;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.rms.model.BillGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action Class for Single KNO Bill Generation Screen.
 * </p>
 * 
 * @author Mrityunjoy Misra(Tata Consultancy Services)
 * @since 26-06-2013
 * 
 */

public class KNOBillGenAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private InputStream inputStream;
	private String billRound = null;
	private String consumerStatus = null;
	private String currentMeterRead = null;
	// private String freezeData = null;
	private String hidAction;
	private InputStream inputStream;
	private String kno = null;
	private String knoMR;
	private String meterReadDate = null;
	private String meterReadStatus = null;
	private String newAvgConsumption = null;
	private String newNoOfFloor = null;
	private String remarks = null;
	private String resetFlag = null;
	private HttpServletResponse response;
	private String searchDeatils;

	private String seqNo = null;

	/**
	 * Will be invoked while Search, Save and Bill Genaration
	 */

	public String ajaxMethod() {

		AppLog.begin();
		try {

			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			// changed for adding of Cookie for CCB authentication
			// Start :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604

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
			// End :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604
			/********
			 * This will be used to populate results based on the ACCT_ID or KNO
			 *******/
			if ("populateMRDDetails".equalsIgnoreCase(hidAction)) {
				String recordStatusAndErrorDesc = "";
				AppLog.info("KNO::" + kno);
				if (kno != null) {
					/*
					 * Start :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604
					 * :- The user should not be allowed to save/generate bill
					 * for Kno having self billing is enabled.
					 */
					String checkSelfBillingStatus = null;
					checkSelfBillingStatus = GetterDAO
							.checkSelfBillingStatus(kno);
					AppLog.info("Check Self Billing Status is >> "
							+ checkSelfBillingStatus);
					AppLog.info("Self billing Code>>"
							+ DJBConstants.SELF_BILLING_STATUS_CD);
					if (null != checkSelfBillingStatus
							&& !"".equalsIgnoreCase(checkSelfBillingStatus)
							&& DJBConstants.SELF_BILLING_STATUS_CD.trim()
									.equalsIgnoreCase(
											checkSelfBillingStatus.trim())) {
						inputStream = new StringBufferInputStream(
								"ERRORINVALIDKNO:");
						return SUCCESS;

					} /*
					 * End :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :-
					 * The user should not be allowed to save/generate bill for
					 * such KNOs.
					 */else {
						/***
						 * This will be used to retrive records based on
						 * consumer Status less than 65
						 ****/
						MRDContainer mrdContainer = new MRDContainer(kno);
						/***
						 * This will be used to retrive records based on
						 * consumer Status greater than 65
						 ****/
						MRDContainer mrdContainerOther = new MRDContainer(kno);

						// MRDContainer mrdRecordStatusAndErrorDesc = new
						// MRDContainer(kno);
						/*
						 * Start :Modified by Madhuri 22-11-2015 as per Jtrac
						 * DJB-4604 :- Users to be restricted as per access
						 * groups in Single Consumer Bill.
						 */
						mrdContainerOther.setLastUpdatedBy(userName);
						mrdContainer.setLastUpdatedBy(userName);
						/*
						 * End :Modified by Madhuri 22-11-2015 as per Jtrac
						 * DJB-4604 :- Users to be restricted as per access
						 * groups in Single Consumer Bill.
						 */
						// mrdRecordStatusAndErrorDesc.setLastUpdatedBy(userName);

						GetterDAO getterDAO = new GetterDAO();
						mrdContainer = (MRDContainer) getterDAO.getMRD(
								mrdContainer, kno, userName);
						// System.out.println("::::::::::::::::::::::::::::::"
						// + mrdContainer.getConsumerList().size());

						mrdContainerOther = (MRDContainer) getterDAO
								.getMRDOther(mrdContainerOther, kno, userName);

						AppLog
								.info("Size of records based on consumer Status less than 65>>"
										+ mrdContainer.getConsumerList().size());
						AppLog
								.info("Size of records based on consumer Status greater than 65 >>"
										+ mrdContainerOther.getConsumerList()
												.size());

						if (null != mrdContainer
								&& null != mrdContainer.getConsumerList()
								&& mrdContainer.getConsumerList().size() > 0) {

							StringBuffer responseSB = new StringBuffer();
							Iterator itr = mrdContainer.getConsumerList()
									.iterator();
							if (itr.hasNext()) {
								ConsumerDetail consumerDetail = (ConsumerDetail) itr
										.next();

								/******************************************************/
								/**
								 * recordStatusAndErrorDesc variable is used to
								 * get the consumer status and error description
								 * */

								recordStatusAndErrorDesc = getterDAO
										.getRecordStatusAndErrorDesc(consumerDetail);

								/*******************************************************/

								MeterReadDetails prevMeterRead = consumerDetail
										.getPrevMeterRead();
								MeterReadDetails prevActualMeterRead = consumerDetail
										.getPrevActualMeterRead();
								MeterReadDetails currMeterRead = consumerDetail
										.getCurrentMeterRead();
								String statusCode = consumerDetail
										.getConsumerStatus().trim();
								// String statusDesc = "Regular";
								// String statusDescription = "";

								session.put("searchBillWindow", consumerDetail
										.getBillRound());

								responseSB.append("<SERVCYCSEQ>"
										+ consumerDetail
												.getServiceCycleRouteSeq()
										+ "</SERVCYCSEQ>");

								responseSB.append("<BILLROUNDID>"
										+ consumerDetail.getBillRound()
										+ "</BILLROUNDID>");
								responseSB.append("<KNO>"
										+ consumerDetail.getKno() + "</KNO>");

								responseSB.append("<ZONE>"
										+ consumerDetail.getZone() + "</ZONE>");

								responseSB.append("<AREA>"
										+ consumerDetail.getArea() + "</AREA>");

								// System.out.println("consumerDetail.getConsPreBillStatID()::::::"+consumerDetail.getConsPreBillStatID());

								responseSB.append("<CONSPREBILLSTATID>"
										+ consumerDetail.getConsPreBillStatID()
										+ "</CONSPREBILLSTATID>");

								responseSB.append("<RECSTATSERRORDESC>"
										+ recordStatusAndErrorDesc
										+ "</RECSTATSERRORDESC>");

								responseSB.append("<BILLID>"
										+ consumerDetail.getBillId()
										+ "</BILLID>");

								responseSB.append("<NAME>"
										+ consumerDetail.getName() + "</NAME>");
								responseSB.append("<WCNO>"
										+ consumerDetail.getWcNo() + "</WCNO>");

								responseSB.append("<CATEGORY>"
										+ consumerDetail.getCat()
										+ "</CATEGORY>");
								responseSB.append("<SERVICETYPE>"
										+ consumerDetail.getServiceType()
										+ "</SERVICETYPE>");
								responseSB.append("<LASTBILLGENERATIONDATE>"
										+ consumerDetail
												.getLastBillGenerationDate()
										+ "</LASTBILLGENERATIONDATE>");
								responseSB.append("<PREVMETERREADDATE>"
										+ prevMeterRead.getMeterReadDate()
										+ "</PREVMETERREADDATE>");
								responseSB.append("<PREVMETERREADSTATUS>"
										+ prevMeterRead.getMeterStatus()
										+ "</PREVMETERREADSTATUS>");
								responseSB.append("<PREVMETERREGREAD>"
										+ prevMeterRead.getRegRead()
										+ "</PREVMETERREGREAD>");
								responseSB.append("<STATUS>"
										+ consumerDetail.getConsumerStatus()
										+ "</STATUS>");

								responseSB.append("<CURRMETERREADDATE>"
										+ currMeterRead.getMeterReadDate()
										+ "</CURRMETERREADDATE>");
								responseSB.append("<CURRMETERREADSTATUS>"
										+ currMeterRead.getMeterStatus()
										+ "</CURRMETERREADSTATUS>");
								responseSB.append("<CURRMETERREGREAD>"
										+ currMeterRead.getRegRead()
										+ "</CURRMETERREGREAD>");
								responseSB.append("<CURAVGCONSUMPTION>"
										+ consumerDetail
												.getCurrentAvgConsumption()
										+ "</CURAVGCONSUMPTION>");
								responseSB.append("<CURAVGREAD>"
										+ currMeterRead.getAvgRead()
										+ "</CURAVGREAD>");
								responseSB.append("<CURRMETERREADFLOOR>"
										+ currMeterRead.getNoOfFloor()
										+ "</CURRMETERREADFLOOR>");

								responseSB.append("<CONSUMERSERVICETYPE>"
										+ consumerDetail.getServiceType()
										+ "</CONSUMERSERVICETYPE>");

								responseSB.append("<PREVACTUALMETERREADDATE>"
										+ prevActualMeterRead
												.getMeterReadDate()
										+ "</PREVACTUALMETERREADDATE>");

								responseSB.append("<PREVACTUALMETERREAD>"
										+ prevActualMeterRead.getRegRead()
										+ "</PREVACTUALMETERREAD>");
								responseSB.append("<PREVMETERNOFLOOR>"
										+ prevMeterRead.getNoOfFloor()
										+ "</PREVMETERNOFLOOR>");
								responseSB.append("<REMARK>"
										+ currMeterRead.getRemarks()
										+ "</REMARK>");
								session.put("mrdContainer", mrdContainer);

							}
							inputStream = new StringBufferInputStream(
									responseSB.toString());
						}

						else if (null != mrdContainerOther
								&& null != mrdContainerOther.getConsumerList()
								&& mrdContainerOther.getConsumerList().size() > 0) {

							/***
							 * This will be used to retrive records based on
							 * consumer Status greater than 65
							 ****/
							StringBuffer responseSB1 = new StringBuffer();
							AppLog.info("responseSB1>>>>" + responseSB1);
							Iterator itr = mrdContainerOther.getConsumerList()
									.iterator();
							if (itr.hasNext()) {
								ConsumerDetail consumerDetail = (ConsumerDetail) itr
										.next();

								/******************************************************/
								/**
								 * recordStatusAndErrorDesc variable is used to
								 * get the consumer status and error description
								 * */

								/******************************************************/
								recordStatusAndErrorDesc = getterDAO
										.getRecordStatusAndErrorDesc(consumerDetail);

								/*******************************************************/

								MeterReadDetails prevMeterRead = consumerDetail
										.getPrevMeterRead();
								MeterReadDetails prevActualMeterRead = consumerDetail
										.getPrevActualMeterRead();
								MeterReadDetails currMeterRead = consumerDetail
										.getCurrentMeterRead();
								String statusCode = consumerDetail
										.getConsumerStatus().trim();
								// String statusDesc = "Regular";
								// String statusDescription = "";

								session.put("searchBillWindow", consumerDetail
										.getBillRound());

								responseSB1.append("<SERVCYCSEQ>"
										+ consumerDetail
												.getServiceCycleRouteSeq()
										+ "</SERVCYCSEQ>");

								// System.out.println("consumerDetail.getBillRound():::::"+consumerDetail.getBillRound());
								responseSB1.append("<BILLROUNDID>"
										+ consumerDetail.getBillRound()
										+ "</BILLROUNDID>");
								responseSB1.append("<KNO>"
										+ consumerDetail.getKno() + "</KNO>");

								responseSB1.append("<ZONE>"
										+ consumerDetail.getZone() + "</ZONE>");

								responseSB1.append("<AREA>"
										+ consumerDetail.getArea() + "</AREA>");

								// System.out.println("consumerDetail.getConsPreBillStatID()::::::"+consumerDetail.getConsPreBillStatID());

								responseSB1.append("<CONSPREBILLSTATID>"
										+ consumerDetail.getConsPreBillStatID()
										+ "</CONSPREBILLSTATID>");

								responseSB1.append("<RECSTATSERRORDESC>"
										+ recordStatusAndErrorDesc
										+ "</RECSTATSERRORDESC>");

								responseSB1.append("<BILLID>"
										+ consumerDetail.getBillId()
										+ "</BILLID>");

								responseSB1.append("<NAME>"
										+ consumerDetail.getName() + "</NAME>");
								responseSB1.append("<WCNO>"
										+ consumerDetail.getWcNo() + "</WCNO>");

								responseSB1.append("<CATEGORY>"
										+ consumerDetail.getCat()
										+ "</CATEGORY>");
								responseSB1.append("<SERVICETYPE>"
										+ consumerDetail.getServiceType()
										+ "</SERVICETYPE>");
								responseSB1.append("<LASTBILLGENERATIONDATE>"
										+ consumerDetail
												.getLastBillGenerationDate()
										+ "</LASTBILLGENERATIONDATE>");
								responseSB1.append("<PREVMETERREADDATE>"
										+ prevMeterRead.getMeterReadDate()
										+ "</PREVMETERREADDATE>");
								responseSB1.append("<PREVMETERREADSTATUS>"
										+ prevMeterRead.getMeterStatus()
										+ "</PREVMETERREADSTATUS>");
								responseSB1.append("<PREVMETERREGREAD>"
										+ prevMeterRead.getRegRead()
										+ "</PREVMETERREGREAD>");
								responseSB1.append("<STATUS>"
										+ consumerDetail.getConsumerStatus()
										+ "</STATUS>");

								responseSB1.append("<CURRMETERREADDATE>"
										+ currMeterRead.getMeterReadDate()
										+ "</CURRMETERREADDATE>");
								responseSB1.append("<CURRMETERREADSTATUS>"
										+ currMeterRead.getMeterStatus()
										+ "</CURRMETERREADSTATUS>");
								responseSB1.append("<CURRMETERREGREAD>"
										+ currMeterRead.getRegRead()
										+ "</CURRMETERREGREAD>");
								responseSB1.append("<CURAVGCONSUMPTION>"
										+ consumerDetail
												.getCurrentAvgConsumption()
										+ "</CURAVGCONSUMPTION>");
								responseSB1.append("<CURAVGREAD>"
										+ currMeterRead.getAvgRead()
										+ "</CURAVGREAD>");
								responseSB1.append("<CURRMETERREADFLOOR>"
										+ currMeterRead.getNoOfFloor()
										+ "</CURRMETERREADFLOOR>");

								responseSB1.append("<CONSUMERSERVICETYPE>"
										+ consumerDetail.getServiceType()
										+ "</CONSUMERSERVICETYPE>");

								responseSB1.append("<PREVACTUALMETERREADDATE>"
										+ prevActualMeterRead
												.getMeterReadDate()
										+ "</PREVACTUALMETERREADDATE>");

								responseSB1.append("<PREVACTUALMETERREAD>"
										+ prevActualMeterRead.getRegRead()
										+ "</PREVACTUALMETERREAD>");
								responseSB1.append("<PREVMETERNOFLOOR>"
										+ prevMeterRead.getNoOfFloor()
										+ "</PREVMETERNOFLOOR>");
								responseSB1.append("<REMARK>"
										+ currMeterRead.getRemarks()
										+ "</REMARK>");
								session.put("mrdContainer", mrdContainerOther);

							}
							inputStream = new StringBufferInputStream(
									responseSB1.toString());

						} else {
							// System.out.println("RECORD NOT FOUND");
							// String
							// errorKNOMissing="<div class='search-option' title='Server Message'><font color='green' size='2'><b>"+"Please Enter The KNO"+"</b></font></div>";

							// String
							// showAjaxoutput="<div class='search-option' title='Server Message'><font color='green' size='2'><b>Please enter a invalid KNO</b></font></div>";
							/*
							 * StringBuffer responseSB2 = new StringBuffer();
							 * 
							 * 
							 * responseSB2.append("<SERVCYCSEQ>"+""
							 * 
							 * + "</SERVCYCSEQ>");
							 * 
							 * 
							 * responseSB2.append("<BILLROUNDID>"+""
							 * 
							 * + "</BILLROUNDID>");
							 * responseSB2.append("<KNO>"+"" + "</KNO>");
							 * 
							 * 
							 * responseSB2.append("<ZONE>" +"" + "</ZONE>");
							 * 
							 * responseSB2.append("<AREA>"+"" + "</AREA>");
							 * 
							 * //System.out.println(
							 * "consumerDetail.getConsPreBillStatID()::::::"
							 * +consumerDetail.getConsPreBillStatID());
							 * 
							 * responseSB2.append("<CONSPREBILLSTATID>" +"" +
							 * "</CONSPREBILLSTATID>");
							 * 
							 * responseSB2.append("<RECSTATSERRORDESC>" +"" +
							 * "</RECSTATSERRORDESC>");
							 * 
							 * responseSB2.append("<BILLID>" +"" + "</BILLID>");
							 * 
							 * 
							 * responseSB2.append("<NAME>"+"" + "</NAME>");
							 * responseSB2.append("<WCNO>"+"" + "</WCNO>");
							 * 
							 * responseSB2.append("<CATEGORY>"+"" +
							 * "</CATEGORY>");
							 * responseSB2.append("<SERVICETYPE>"+""
							 * 
							 * + "</SERVICETYPE>");
							 * responseSB2.append("<LASTBILLGENERATIONDATE>"+""
							 * 
							 * + "</LASTBILLGENERATIONDATE>");
							 * responseSB2.append("<PREVMETERREADDATE>"+""
							 * 
							 * + "</PREVMETERREADDATE>");
							 * responseSB2.append("<PREVMETERREADSTATUS>"+""
							 * 
							 * + "</PREVMETERREADSTATUS>");
							 * responseSB2.append("<PREVMETERREGREAD>"+""
							 * 
							 * + "</PREVMETERREGREAD>");
							 * responseSB2.append("<STATUS>"+""
							 * 
							 * + "</STATUS>");
							 * 
							 * responseSB2.append("<CURRMETERREADDATE>"+""
							 * 
							 * + "</CURRMETERREADDATE>");
							 * responseSB2.append("<CURRMETERREADSTATUS>"+""
							 * 
							 * + "</CURRMETERREADSTATUS>");
							 * responseSB2.append("<CURRMETERREGREAD>"+""
							 * 
							 * + "</CURRMETERREGREAD>");
							 * responseSB2.append("<CURAVGCONSUMPTION>"+""
							 * 
							 * + "</CURAVGCONSUMPTION>");
							 * responseSB2.append("<CURAVGREAD>"+""
							 * 
							 * + "</CURAVGREAD>");
							 * responseSB2.append("<CURRMETERREADFLOOR>"+""
							 * 
							 * + "</CURRMETERREADFLOOR>");
							 * 
							 * responseSB2.append("<CONSUMERSERVICETYPE>"+""
							 * 
							 * + "</CONSUMERSERVICETYPE>");
							 * 
							 * responseSB2.append("<PREVACTUALMETERREADDATE>"+""
							 * 
							 * + "</PREVACTUALMETERREADDATE>");
							 * 
							 * responseSB2.append("<PREVACTUALMETERREAD>"+""
							 * 
							 * + "</PREVACTUALMETERREAD>");
							 * responseSB2.append("<PREVMETERNOFLOOR>"+""
							 * 
							 * + "</PREVMETERNOFLOOR>");
							 * responseSB2.append("<REMARK>"+"" + "</REMARK>");
							 */

							inputStream = new StringBufferInputStream(
									"ERRORINVALIDKNO:");
							// return "errorMSG";
							return SUCCESS;
						}
					}
				}

			}
			/******** This will be used to save on the ACCT_ID or KNO *******/
			if ("saveCurrentMeterData".equalsIgnoreCase(hidAction)) {
				// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>inside saveCurrentMeterData"+hidAction);
				HashMap<String, String> returnMap = (HashMap<String, String>) saveMeterRead();

				String errorDetails = returnMap.get("getValidatorError");
				String getValidatorErrorMRD = returnMap
						.get("getValidatorErrorMRD");
				String getValidatorErrorMRDFiltered = returnMap
						.get("getValidatorErrorFiltered");
				if (getValidatorErrorMRDFiltered != null) {
					String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
							+ getValidatorErrorMRDFiltered
							+ "</b></font></div>";
					inputStream = new StringBufferInputStream(errorAjaxoutput);
					return SUCCESS;
				}

				if (getValidatorErrorMRD != null) {
					String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
							+ getValidatorErrorMRD + "</b></font></div>";
					inputStream = new StringBufferInputStream(errorAjaxoutput);
					return SUCCESS;
				}

				if (errorDetails != null) {
					String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
							+ errorDetails + "</b></font></div>";
					inputStream = new StringBufferInputStream(errorAjaxoutput);
					return SUCCESS;
				}

				String isSucess = returnMap.get("isSuccess");
				if (isSucess != null) {
					if (isSucess.equals("Y")) {
						String ajaxResponse = returnMap.get("showAjaxoutput");
						inputStream = new StringBufferInputStream(ajaxResponse);
						// System.out.println("ajaxResponse>>>>>>>>>>>>>>>"
						// + ajaxResponse);
						return SUCCESS;
					} else {
						return "error";
					}
				} else {
					return "error";
				}
			}
			/*
			 * if ("freezeCurrentMeterData".equalsIgnoreCase(hidAction)) {
			 * System.out.println(
			 * ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inside freezeCurrentMeterData"
			 * +hidAction); HashMap<String, String> returnMap=(HashMap<String,
			 * String>)saveMeterRead();
			 * 
			 * String errorDetails=returnMap.get("getValidatorError");
			 * 
			 * if(errorDetails!=null) { StringerrorAjaxoutput=
			 * "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
			 * +errorDetails+"</b></font></div>"; inputStream = new
			 * StringBufferInputStream(errorAjaxoutput); return SUCCESS; }
			 * String isSucess=returnMap.get("isSuccess");
			 * 
			 * if(isSucess !=null) { if(isSucess.equals("Y")) {
			 * 
			 * System.out.println("inside freezeCurrentMeterData save sucess");
			 * 
			 * boolean sucess=freezeMeterRead(kno,userName); if(sucess) {
			 * 
			 * StringshowAjaxoutput=
			 * "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Record "
			 * + " freezed Successfully.</b></font></div>"; inputStream = new
			 * StringBufferInputStream(showAjaxoutput);
			 * System.out.println("Record has been sucessfully freezed"); return
			 * SUCCESS; } else { return "error"; } } else { return "error"; } }
			 * else { return "error"; }
			 * 
			 * }
			 */

			/******** This will be used to save on the Screen Freeze and generate bill *******/
			if ("genarateBill".equalsIgnoreCase(hidAction)) {
				/*
				 * Start :Modified by Suraj Tiwari 12-09-2017 as per Jtrac
				 * DJB-4735 :- Users to be restricted billing as per DB Session
				 * Count
				 */
				if (dbSessionCount()) {
					AppLog.info("Before  Sleep >> " + new Date());
					Thread.sleep(DJBConstants.BILL_GEN_SLEEP_TIME);
					AppLog.info("After  Sleep >> " + new Date());
					if (dbSessionCount()) {

						HashMap<String, String> returnMap = (HashMap<String, String>) saveMeterRead();
						String errorDetails = returnMap
								.get("getValidatorError");
						String getValidatorErrorMRD = returnMap
								.get("getValidatorErrorMRD");
						String getValidatorErrorMRDFiltered = returnMap
								.get("getValidatorErrorFiltered");
						if (getValidatorErrorMRDFiltered != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ getValidatorErrorMRDFiltered
									+ "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						if (getValidatorErrorMRD != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ getValidatorErrorMRD
									+ "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						if (errorDetails != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ errorDetails + "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						String isSucess = returnMap.get("isSuccess");
						if (isSucess != null) {
							if (isSucess.equals("Y")) {
								String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='red' size='2'><b>"
										+ DJBConstants.DB_SESSION_BUSY_ERROR_MSG
										+ "</b></font></div>";
								inputStream = new StringBufferInputStream(
										errorAjaxoutput);
								return SUCCESS;
							} else {
								return "error";
							}
						} else {
							return "error";
						}

					} else {

						HashMap<String, String> returnMap = (HashMap<String, String>) saveMeterRead();
						String errorDetails = returnMap
								.get("getValidatorError");
						String getValidatorErrorMRD = returnMap
								.get("getValidatorErrorMRD");
						String getValidatorErrorMRDFiltered = returnMap
								.get("getValidatorErrorFiltered");
						if (getValidatorErrorMRDFiltered != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ getValidatorErrorMRDFiltered
									+ "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						if (getValidatorErrorMRD != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ getValidatorErrorMRD
									+ "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						if (errorDetails != null) {
							String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
									+ errorDetails + "</b></font></div>";
							inputStream = new StringBufferInputStream(
									errorAjaxoutput);
							return SUCCESS;
						}
						String isSucess = returnMap.get("isSuccess");
						if (isSucess != null) {
							if (isSucess.equals("Y")) {
								boolean sucess = freezeMeterRead(kno, userName);
								if (sucess) {
									BillingFacade billingFacade = new BillingFacade();
									BillGenerationDetails finalBeanforBillGenerationDetails;
									finalBeanforBillGenerationDetails = billingFacade
											.getDeatilsBillGenerationDetails(
													new BillGenerationDetails(),
													billRound, kno);
									finalBeanforBillGenerationDetails
											.setMeterReadSource("READER");
									finalBeanforBillGenerationDetails
											.setBillGenerationSource("PORTAL");
									SetterDAO setterDAO = new SetterDAO();
									boolean sourceUpdated = setterDAO
											.updateOnlineSABillingSource(
													finalBeanforBillGenerationDetails
															.getAcctId(),
													finalBeanforBillGenerationDetails
															.getBillRoundId(),
													"ONLINE_SA_BILL_APP");
									String billID = finalgenerateBill(finalBeanforBillGenerationDetails);
									if (billID != null) {
										if (billID.startsWith("<error>")) {
											int beginIndexError = billID
													.indexOf("<error>")
													+ "<error>".length();
											int endIndexError = billID
													.indexOf("</error>");
											String Error = billID.substring(
													beginIndexError,
													endIndexError);
											String showAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
													+ Error
													+ "</b></font></div>";
											inputStream = new StringBufferInputStream(
													showAjaxoutput);
											return SUCCESS;
										} else if (billID
												.equalsIgnoreCase(DJBConstants.BILL_ID_NULL_MSG)) {
											String showAjaxoutputBill = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
													+ DJBConstants.BILL_ID_NULL_MSG
													+ "</b></font></div>";
											inputStream = new StringBufferInputStream(
													showAjaxoutputBill);
											return SUCCESS;
										} else {
											String showAjaxoutputBill = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Bill ID "
													+ billID
													+ " generated Successfully.</b></font></div>";
											inputStream = new StringBufferInputStream(
													showAjaxoutputBill);
											return SUCCESS;
										}
									} else
										return "error";

								} else {
									return "error";
								}
							} else {
								return "error";
							}
						} else {
							return "error";
						}
					}
				} else {
					HashMap<String, String> returnMap = (HashMap<String, String>) saveMeterRead();
					String errorDetails = returnMap.get("getValidatorError");
					String getValidatorErrorMRD = returnMap
							.get("getValidatorErrorMRD");
					String getValidatorErrorMRDFiltered = returnMap
							.get("getValidatorErrorFiltered");
					if (getValidatorErrorMRDFiltered != null) {
						String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
								+ getValidatorErrorMRDFiltered
								+ "</b></font></div>";
						inputStream = new StringBufferInputStream(
								errorAjaxoutput);
						return SUCCESS;
					}
					if (getValidatorErrorMRD != null) {
						String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
								+ getValidatorErrorMRD + "</b></font></div>";
						inputStream = new StringBufferInputStream(
								errorAjaxoutput);
						return SUCCESS;
					}
					if (errorDetails != null) {
						String errorAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
								+ errorDetails + "</b></font></div>";
						inputStream = new StringBufferInputStream(
								errorAjaxoutput);
						return SUCCESS;
					}
					String isSucess = returnMap.get("isSuccess");
					if (isSucess != null) {
						if (isSucess.equals("Y")) {
							boolean sucess = freezeMeterRead(kno, userName);
							if (sucess) {
								BillingFacade billingFacade = new BillingFacade();
								BillGenerationDetails finalBeanforBillGenerationDetails;
								finalBeanforBillGenerationDetails = billingFacade
										.getDeatilsBillGenerationDetails(
												new BillGenerationDetails(),
												billRound, kno);
								finalBeanforBillGenerationDetails
										.setMeterReadSource("READER");
								finalBeanforBillGenerationDetails
										.setBillGenerationSource("PORTAL");
								SetterDAO setterDAO = new SetterDAO();
								boolean sourceUpdated = setterDAO
										.updateOnlineSABillingSource(
												finalBeanforBillGenerationDetails
														.getAcctId(),
												finalBeanforBillGenerationDetails
														.getBillRoundId(),
												"ONLINE_SA_BILL_APP");
								String billID = finalgenerateBill(finalBeanforBillGenerationDetails);
								if (billID != null) {
									if (billID.startsWith("<error>")) {
										int beginIndexError = billID
												.indexOf("<error>")
												+ "<error>".length();
										int endIndexError = billID
												.indexOf("</error>");
										String Error = billID.substring(
												beginIndexError, endIndexError);
										String showAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
												+ Error + "</b></font></div>";
										inputStream = new StringBufferInputStream(
												showAjaxoutput);
										return SUCCESS;
									} else if (billID
											.equalsIgnoreCase(DJBConstants.BILL_ID_NULL_MSG)) {
										String showAjaxoutputBill = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>"
												+ DJBConstants.BILL_ID_NULL_MSG
												+ "</b></font></div>";
										inputStream = new StringBufferInputStream(
												showAjaxoutputBill);
										return SUCCESS;
									} else {
										String showAjaxoutputBill = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Bill ID "
												+ billID
												+ " generated Successfully.</b></font></div>";
										inputStream = new StringBufferInputStream(
												showAjaxoutputBill);
										return SUCCESS;
									}
								} else
									return "error";
							} else {
								return "error";
							}
						} else {
							return "error";
						}
					} else {
						return "error";
					}
				}
				/*
				 * End :Modified by Suraj Tiwari 12-09-2017 as per Jtrac
				 * DJB-4735 :- Users to be restricted billing as per DB Session
				 * Count
				 */
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		try {

			// System.out.println("HiiiiiiiiBB222today");
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}

			String knoMR = this.getKnoMR();
			GetterDAO getterDAOnew = new GetterDAO();
			ConsumerStatusLookup cnoStatLookup = (ConsumerStatusLookup) getterDAOnew
					.getConsumerStatus();

			session.put("sessionConsumerList", cnoStatLookup
					.getConsumerStatusLookupList());

			if (knoMR != null) {
				MRDContainer mrdContainer = new MRDContainer("knoMR");
				mrdContainer.setLastUpdatedBy(userName);
				GetterDAO getterDAO = new GetterDAO();
				mrdContainer = (MRDContainer) getterDAO.getMRD(mrdContainer,
						knoMR, userName);

				if (null != mrdContainer
						&& null != mrdContainer.getConsumerList()
						&& mrdContainer.getConsumerList().size() > 0) {
					session.put("mrdContainer", mrdContainer);

				}
				session.put("freezeSuccess",
						"The MRD Has been Frozen Successfully.");
				session.put("isFrozen", "Y");

			}

		} catch (Exception e) {
			// e.printStackTrace();
		}

		return "success";
	}

	/*
	 * Start :Modified by Suraj Tiwari 12-09-2017 as per Jtrac DJB-4735 :- Users
	 * to be restricted billing as per DB Session Count
	 */
	/**
	 * <p>
	 * This method is used to restrict billing as per DB Session Count
	 * </p>
	 * 
	 * @return
	 */
	public boolean dbSessionCount() {
		if (!DJBConstants.DB_SESSION_COUNT_CHECK_FLAG
				.equalsIgnoreCase(DJBConstants.FLAG_Y)) {
			return false;
		}
		int checkDbSessionCount = 0;
		String checkDbSessionInString = null;
		checkDbSessionInString = GetterDAO.checkDbSessionCount();
		if (null != checkDbSessionInString
				&& !"".equalsIgnoreCase(checkDbSessionInString)) {
			checkDbSessionCount = Integer.parseInt(checkDbSessionInString);
		}
		AppLog.info("DB Session Count is  >> " + checkDbSessionCount);
		AppLog.info("Allowed DB Session Count >>"
				+ DJBConstants.DB_SESSION_ALLOWED_CD);
		if (checkDbSessionCount > DJBConstants.DB_SESSION_ALLOWED_CD) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * End :Modified by Suraj Tiwari 12-09-2017 as per Jtrac DJB-4735 :- Users
	 * to be restricted billing as per DB Session Count
	 */
	/**
	 * <p>
	 * This method is used to generate bills
	 * </p>
	 * 
	 * @param billDetails
	 * @return
	 */
	public String finalgenerateBill(BillGenerationDetails billDetails) {
		// call generate bill XAI service via faced
		/*
		 * Start :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to
		 * be restricted as per access groups in Single Consumer Bill.
		 */
		Map<String, Object> session = ActionContext.getContext().getSession();
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
		 * End :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to be
		 * restricted as per access groups in Single Consumer Bill.
		 */
		boolean isBillGenerate = false;
		BillingFacade billFacade = new BillingFacade();
		/*
		 * Commented by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to be
		 * restricted as per access groups in Single Consumer Bill.
		 */
		// String
		// billID=billFacade.finalgenerateBillInBillingFacade(billDetails);

		/*
		 * Start :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to
		 * be restricted as per access groups in Single Consumer Bill.
		 */
		String billID = billFacade.finalgenerateBillInBillingFacade(
				billDetails, authCookie);
		AppLog.info("billID is >>" + billID);
		/*
		 * End :Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to be
		 * restricted as per access groups in Single Consumer Bill.
		 */
		/*
		 * Start :Modified by Suraj 12-09-2017 as per Jtrac DJB-4735 :- To
		 * handel error msg when billId is null
		 */
		if (null != billID) {
			return billID;
		} else {
			return DJBConstants.BILL_ID_NULL_MSG;
		}
		/*
		 * End :Modified by Suraj 12-09-2017 as per Jtrac DJB-4735 :- To handel
		 * error msg when billId is null
		 */
	}

	/**************************************************************************************************************************/

	/**
	 * <p>
	 * This method will be invoked when any account needs to be freezed
	 * </p>
	 * 
	 * @param acctNo
	 * @param userName
	 * @return
	 */
	public boolean freezeMeterRead(String acctNo, String userName) {
		// call DAO via faced
		boolean result = false;
		MRDContainer mrdContainer = new MRDContainer(acctNo);
		mrdContainer.setLastUpdatedBy(userName);
		result = mrdContainer.freezeMRDBasedOnKNO();
		return result;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/******************************************************************************************/

	/**
	 * @return the consumerStatus
	 */
	public String getConsumerStatus() {
		return consumerStatus;
	}

	/**
	 * @return the currentMeterRead
	 */
	public String getCurrentMeterRead() {
		return currentMeterRead;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the freezeData
	 */
	// public String getFreezeData() {
	// return freezeData;
	// }

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
	 * @return the knoMR
	 */
	public String getKnoMR() {
		return knoMR;
	}

	/**
	 * @return the meterReadDate
	 */
	public String getMeterReadDate() {
		return meterReadDate;
	}

	/**
	 * @return the meterReadStatus
	 */
	public String getMeterReadStatus() {
		return meterReadStatus;
	}

	/**
	 * @return the newAvgConsumption
	 */
	public String getNewAvgConsumption() {
		return newAvgConsumption;
	}

	/**
	 * @return the newNoOfFloor
	 */
	public String getNewNoOfFloor() {
		return newNoOfFloor;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the resetFlag
	 */
	public String getResetFlag() {
		return resetFlag;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the searchDeatils
	 */
	public String getSearchDeatils() {
		return searchDeatils;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/************************************************************************************************/
	
	/**
	 * <p>
	 * This method will be invoked when save button is clicked from the
	 * screen.When clicked on the invoke the freeze and generate bill as well
	 * </p>
	 * 
	 * @return
	 */
	HashMap<String, String> saveMeterRead() {
		AppLog.info("Inside saveMeter Read");
		HashMap<String, String> returnMap = new HashMap<String, String>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userName = (String) session.get("userId");
		String returnMessage = "error";
		String msg = null;
		// System.out.println("inside saveCurrentMeterDatasaveCurrentMeterDatasaveCurrentMeterDatasaveCurrentMeterData");
		if (!"Y".equals(resetFlag)) {
			int seqNoInt = 0;
			if (null != seqNo && !"".equals(seqNo)) {
				seqNoInt = Integer.parseInt(seqNo);
			}
			/***************************************************************************************************************/
			BillGenerationDetails billGenerationDetails = new BillGenerationDetails();
			billGenerationDetails.setUserName(userName);
			AppLog.info("userName" + userName);

			billGenerationDetails.setAcctId(kno);
			AppLog.info("kno" + kno);

			billGenerationDetails.setBillRoundId(billRound);
			AppLog.info("billRound" + billRound);

			billGenerationDetails.setRegRead(currentMeterRead);
			AppLog.info("currentMeterRead" + currentMeterRead);

			billGenerationDetails.setConsumerStatus(consumerStatus);
			AppLog.info("consumerStatus" + consumerStatus);

			if (null != meterReadStatus) {
				AppLog.info("meterReadStatus>>>" + meterReadStatus);
				billGenerationDetails.setReadType(GetterDAO
						.readReadTypeByMeterReadREmark(meterReadStatus.trim()));// 60
			} else {
				billGenerationDetails.setReadType(null);// 60
				AppLog.info("else meterReadStatus>>>" + meterReadStatus);
			}

			billGenerationDetails.setReadDate(meterReadDate);
			AppLog.info(" meterReadDate>>>" + meterReadDate);

			billGenerationDetails.setMeterReaderName(userName);
			AppLog.info(" userName>>>" + userName);

			billGenerationDetails.setAverageConsumption(newAvgConsumption);
			AppLog.info(" newAvgConsumption>>>" + newAvgConsumption);

			billGenerationDetails.setMeterReadSource("READER");

			billGenerationDetails.setBillGenerationSource("PORTAL");
			billGenerationDetails.setNoOfFloors(newNoOfFloor);
			AppLog.info(" newNoOfFloor>>>" + newNoOfFloor);

			billGenerationDetails.setReaderRemark(meterReadStatus);
			AppLog.info(" meterReadStatus>>>" + meterReadStatus);
			// readerRemark
			BillingFacade billFacade = new BillingFacade();

			String isSuccess = null;
			String recordUpdatedMsg = null;
			returnMap = (HashMap<String, String>) billFacade
					.saveBill(billGenerationDetails);
			if (null != returnMap) {

				isSuccess = (String) returnMap.get("isSuccess");
				recordUpdatedMsg = (String) returnMap.get("recordUpdatedMsg");
			}

			/*****************************************************************************************************************/

		}
		return returnMap;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumerStatus
	 *            the consumerStatus to set
	 */
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	/**
	 * @param currentMeterRead
	 *            the currentMeterRead to set
	 */
	public void setCurrentMeterRead(String currentMeterRead) {
		this.currentMeterRead = currentMeterRead;
	}

	/**
	 * @param freezeData
	 *            the freezeData to set
	 */
	// public void setFreezeData(String freezeData) {
	// this.freezeData = freezeData;
	// }

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
	 * @param knoMR
	 *            the knoMR to set
	 */
	public void setKnoMR(String knoMR) {
		this.knoMR = knoMR;
	}

	/**
	 * @param meterReadDate
	 *            the meterReadDate to set
	 */
	public void setMeterReadDate(String meterReadDate) {
		this.meterReadDate = meterReadDate;
	}

	/**
	 * @param meterReadStatus
	 *            the meterReadStatus to set
	 */
	public void setMeterReadStatus(String meterReadStatus) {
		this.meterReadStatus = meterReadStatus;
	}

	/**
	 * @param newAvgConsumption
	 *            the newAvgConsumption to set
	 */
	public void setNewAvgConsumption(String newAvgConsumption) {
		this.newAvgConsumption = newAvgConsumption;
	}

	/**
	 * @param newNoOfFloor
	 *            the newNoOfFloor to set
	 */
	public void setNewNoOfFloor(String newNoOfFloor) {
		this.newNoOfFloor = newNoOfFloor;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param resetFlag
	 *            the resetFlag to set
	 */
	public void setResetFlag(String resetFlag) {
		this.resetFlag = resetFlag;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param searchDeatils
	 *            the searchDeatils to set
	 */
	public void setSearchDeatils(String searchDeatils) {
		this.searchDeatils = searchDeatils;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

}
