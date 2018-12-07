/************************************************************************************************************
 * @(#) BillingFacade.java
 *
 *************************************************************************************************************/
package com.tcs.djb.facade;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map; //import com.tcs.djb.rms.constant.DJBConstants;
//import com.tcs.djb.rms.dao.BillGenerationDAO;
//import com.tcs.djb.rms.dao.MeterReadDAO;
import com.tcs.djb.rms.model.BillGenerationDetails; //import com.tcs.djb.rms.model.BillGenerationFunctionResponse;
//import com.tcs.djb.rms.model.BillGenerationRequest;
//import com.tcs.djb.rms.model.BillGenerationResponse;
//import com.tcs.djb.rms.model.BillGenerationXAIServiceResponse;
//import com.tcs.djb.rms.model.BillViewDetails;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MeterReadType;
import com.tcs.djb.model.MeterReadUploadErrorLogDetails; //import com.tcs.djb.rms.model.ErrorDetails;
import com.tcs.djb.model.MeterReadTypeLookUp;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReadDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.MeterReadUploadDetails; //import com.tcs.djb.rms.model.MeterReadUploadErrorLogDetails;
//import com.tcs.djb.rms.model.WebServiceUserDetails;
//import com.tcs.djb.security.BillingWebService;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;
import com.tcs.djb.validator.MeterReadValidator;

//import com.tcs.djb.rms.service.ccb.BillingXAIService;
//import com.tcs.djb.rms.service.ccb.BillingXAIServiceImpl;
//import com.tcs.djb.rms.service.web.meterread.MeterReadValidator;
//import com.tcs.djb.rms.util.AppLog;
//import com.tcs.djb.rms.util.AppLog;

/**
 * <p>
 * This class includes all the business logic used in the DJBRMSWebService
 * Application by calling Validator Classes or DAO classes. It implements
 * <code>BillingWebService</code>.
 * </p>
 */
public class BillingFacade {

	/**
	 * <p>
	 * Error Message to be returned as Bill Generation Response to HHD in case
	 * of failure.
	 * </p>
	 */
	private static String FAILURE_MESSAGE = "Bill Generation Process Failed";

	/**
	 * <p>
	 * Method returned as Bill Generation Details Response
	 * getDeatilsBillGenerationDetails
	 * </p>
	 * 
	 * @param billgenDetails
	 * @param billRoundID
	 * @param acctNo
	 * @return
	 */
	public BillGenerationDetails getDeatilsBillGenerationDetails(
			BillGenerationDetails billgenDetails, String billRoundID,
			String acctNo) {
		BillGenerationDetails populateBillGenerationDetails = MeterReadDAO
				.getfinalBillGenerationDetailsfromDB(billgenDetails,
						billRoundID, acctNo);
		return populateBillGenerationDetails;
	}

	/**
	 * <p>
	 * This method is used to validate meter read while uploading as per
	 * business process
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public Map<Object, Object> validateMeterReadUploadAsPerBusinessProcess(
			Map<Object, Object> inputMap) {

		// System.out.println("1111inside validateMeterReadUploadAsPerBusinessProcess");
		// System.out.println("inside validateMeterReadUploadAsPerBusinessProcess33333");
		List<BillGenerationDetails> toUploadMeterReadDetailsList = (ArrayList<BillGenerationDetails>) inputMap
				.get("toUploadMeterReadDetailsList");
		List<ConsumerDetail> consumerDetailList = MeterReadDAO
				.getConsumerDetailByAccountIDAndBillRound2((ArrayList<BillGenerationDetails>) toUploadMeterReadDetailsList);
		inputMap.put("consumerDetailList", consumerDetailList);

		/**************************************************************************************************************************/
		// 8th July code needs to be modified
		Iterator toUploadMeterReadDetailsItr = toUploadMeterReadDetailsList
				.iterator();
		MeterReadUploadDetails meterUploadBean = new MeterReadUploadDetails();

		if (toUploadMeterReadDetailsItr.hasNext()) {
			BillGenerationDetails billgenDeatils = (BillGenerationDetails) toUploadMeterReadDetailsItr
					.next();
			meterUploadBean.setAverageConsumption(billgenDeatils
					.getAverageConsumption());
			meterUploadBean.setBillRound(billgenDeatils.getBillRoundId());
			meterUploadBean.setConsumerStatus(billgenDeatils
					.getConsumerStatus());
			meterUploadBean.setKno(billgenDeatils.getAcctId());
			meterUploadBean.setMeterRead(billgenDeatils.getRegRead());
			meterUploadBean.setMeterReadDate(billgenDeatils.getReadDate());
			meterUploadBean
					.setMeterReadRemark(billgenDeatils.getReaderRemark());
			meterUploadBean.setNoOfFloors(billgenDeatils.getNoOfFloors());

			/************ DJB-1625 code is implemented to handel Meter read type from DB *****************************************/
			MeterReadTypeLookUp meterReadTypeLookUp = GetterDAO
					.getMeterReadRemarkCorrespondingToReadType();
			inputMap.put("meterReadTypeLookUp", meterReadTypeLookUp);
			/*********************************************************/

			/******** DJB-1625 code has been commented due to production issue *******************************************************/
			// MeterReadTypeLookUp mrdTypelookup=new MeterReadTypeLookUp();
			// mrdTypelookup.setRegularReadType(new
			// MeterReadType("20",billgenDeatils.getReaderRemark()));
			// inputMap.put("meterReadTypeLookUp", mrdTypelookup);
			/********************************************************************/
			// System.out.println("2222inside validateMeterReadUploadAsPerBusinessProcess"+"ConsumerStatus:"+billgenDeatils.getConsumerStatus()+"ReaderRemark:::"+billgenDeatils.getReaderRemark());
		}

		if (meterUploadBean != null && meterUploadBean.getKno() != null) {
			List<MeterReadUploadDetails> toUploadMeterReadDetailsList2 = new ArrayList<MeterReadUploadDetails>();
			toUploadMeterReadDetailsList2.add(meterUploadBean);

			if (toUploadMeterReadDetailsList2.iterator().hasNext()) {
				inputMap.remove("toUploadMeterReadDetailsList");
				inputMap.put("toUploadMeterReadDetailsList",
						toUploadMeterReadDetailsList2);

			}
		}

		HashMap<Object, Object> newinputMap = (HashMap) inputMap;
		Map<Object, Object> returnMap = MeterReadValidator
				.validateMeterReadUploadAsPerBusinessProcess(newinputMap);
		if (returnMap != null) {

			// System.out.println("33333---------returnMap");

			ArrayList<MeterReadUploadDetails> toUploadMeterReadDetailsListValidated = (ArrayList<MeterReadUploadDetails>) returnMap
					.get("toUploadMeterReadDetailsList");

			if (toUploadMeterReadDetailsListValidated.iterator().hasNext()) {
				BillGenerationDetails toUpdatevalidated = new BillGenerationDetails();
				MeterReadUploadDetails meterUploadBeanvalidated = (MeterReadUploadDetails) toUploadMeterReadDetailsListValidated
						.iterator().next();

				if (meterUploadBeanvalidated != null) {
					toUpdatevalidated.setAcctId(meterUploadBeanvalidated
							.getKno());
					toUpdatevalidated
							.setAverageConsumption(meterUploadBeanvalidated
									.getAverageConsumption());
					toUpdatevalidated
							.setConsumerStatus(meterUploadBeanvalidated
									.getConsumerStatus());
					toUpdatevalidated.setBillRoundId(meterUploadBeanvalidated
							.getBillRound());
					toUpdatevalidated.setNoOfFloors(meterUploadBeanvalidated
							.getNoOfFloors());
					toUpdatevalidated.setReadDate(meterUploadBeanvalidated
							.getMeterReadDate());
					toUpdatevalidated.setReaderRemark(meterUploadBeanvalidated
							.getMeterReadRemark());
					toUpdatevalidated.setRegRead(meterUploadBeanvalidated
							.getMeterRead());

					// System.out.println("444444---------consumer status::"+meterUploadBeanvalidated.getConsumerStatus()
					// +"ReaderRemark::::"+meterUploadBeanvalidated.getMeterReadRemark());

				}

				if (meterUploadBeanvalidated != null
						&& toUpdatevalidated != null) {
					List<BillGenerationDetails> toBillGenerationDetailsList = new ArrayList<BillGenerationDetails>();
					toBillGenerationDetailsList.add(toUpdatevalidated);
					if (toBillGenerationDetailsList.iterator().hasNext()) {

						// System.out.println("5555555---------meterUploadBeanvalidated::");
						returnMap.remove("toUploadMeterReadDetailsList");
						returnMap.put("toUploadMeterReadDetailsList",
								toBillGenerationDetailsList);
						returnMap.put("consumerDetailList", consumerDetailList);
						inputMap = returnMap;
						// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Times List");

					}

				}

			}

		}

		/****************************************************************************************************************************/

		return inputMap;
	}

	/**
	 * <p>
	 * This method is used to validate the details received from HHD as in
	 * defined format after logging their request to database.
	 * </p>
	 * </p>
	 * 
	 * @param billGenerationDetails
	 * @return Map<Object, Object>
	 */
	// @Override
	public Map<Object, Object> validateMeterReadUploadFields(
			BillGenerationDetails billGenerationDetails) {

		Map<Object, Object> returnMap = (HashMap<Object, Object>) MeterReadValidator
				.validateMeterReadUploadFieldsForSingleBillGeneration(billGenerationDetails);
		return returnMap;
	}
	
	/**
	 * <p>
	 * Method returned call webservice and get response back Modified : Edited
	 * by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604 :- Users to be
	 * restricted as per access groups in Single Consumer Bill Generation
	 * screen.
	 * </p>
	 * 
	 * @param billGenerationDetails
	 * @param authCookie
	 * @return
	 */
	/*
	 * Commented by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to be
	 * restricted as per access groups in Single Consumer Bill.
	 */
	/*
	 * public String finalgenerateBillInBillingFacade(BillGenerationDetails
	 * billGenerationDetails)
	 */
	public String finalgenerateBillInBillingFacade(
			BillGenerationDetails billGenerationDetails, String authCookie) {

		String billId = null;
		boolean isBillGenarated = false;
		StringBuffer xmlSB = new StringBuffer(512);

		xmlSB
				.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_OnlineBillGenerationService.xsd'>");
		xmlSB.append("<soapenv:Header />");
		xmlSB.append("<soapenv:Body>");
		xmlSB.append("<cm:CM_OnlineBillGenerationService>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:acctId>");
		xmlSB.append(billGenerationDetails.getAcctId());
		xmlSB.append("</cm:acctId>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:billRoundId>");
		xmlSB.append(billGenerationDetails.getBillRoundId());
		xmlSB.append("</cm:billRoundId>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:regRead>");
		xmlSB.append((billGenerationDetails.getRegRead() == null) ? ""
				: billGenerationDetails.getRegRead());
		xmlSB.append("</cm:regRead>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:readType>");
		xmlSB.append((billGenerationDetails.getReadType() == null) ? ""
				: billGenerationDetails.getReadType());
		xmlSB.append("</cm:readType>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:readerRemark>");
		xmlSB.append((billGenerationDetails.getReaderRemark() == null) ? ""
				: billGenerationDetails.getReaderRemark());
		xmlSB.append("</cm:readerRemark>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:readDate>");
		xmlSB.append(billGenerationDetails.getReadDate());
		xmlSB.append("</cm:readDate>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:meterReaderName>");
		xmlSB.append((billGenerationDetails.getMeterReaderName() == null) ? ""
				: billGenerationDetails.getMeterReaderName());
		xmlSB.append("</cm:meterReaderName>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:averageConsumption>");
		xmlSB
				.append((billGenerationDetails.getAverageConsumption() == null) ? ""
						: billGenerationDetails.getAverageConsumption());
		xmlSB.append("</cm:averageConsumption>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:meterReadSource>");
		xmlSB.append("READER");
		xmlSB.append("</cm:meterReadSource>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:billGenerationSource>");
		xmlSB.append("DEAPP_SINGLE");
		xmlSB.append("</cm:billGenerationSource>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:noOfFloors>");
		xmlSB.append((billGenerationDetails.getNoOfFloors() == null) ? ""
				: billGenerationDetails.getNoOfFloors());
		xmlSB.append("</cm:noOfFloors>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:meterReadId>");
		xmlSB.append("</cm:meterReadId>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:billId>");
		xmlSB.append("</cm:billId>");
		xmlSB.append("<!-- Optional:-->");
		xmlSB.append("<cm:error>");
		xmlSB.append("</cm:error>");
		xmlSB.append("</cm:CM_OnlineBillGenerationService>");
		xmlSB.append("</soapenv:Body>");
		xmlSB.append("</soapenv:Envelope>");
		// System.out.println("Bill Generation CCB Service Request XML::\n"
		// + xmlSB.toString());
		String encodedXML = UtilityForAll.encodeString(xmlSB.toString());
		String xaiHTTPCallResponse;

		try {
			/*
			 * Commented by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users to
			 * be restricted as per access groups in Single Consumer Bill.
			 */
			// XAIHTTPCall xaiHTTPCall = new XAIHTTPCall();

			/*
			 * Start: Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users
			 * to be restricted as per access groups in Single Consumer Bill.
			 */
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall(authCookie);
			/*
			 * End: Added by Madhuri 22-11-2015 as per Jtrac DJB-4604 :- Users
			 * to be restricted as per access groups in Single Consumer Bill.
			 */

			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			// System.out.println("XAIHTTP Call Response::\n"
			// + xaiHTTPCallResponse);

			if (xaiHTTPCallResponse.contains("<meterReadId>")) {
				int beginIndexMeterReadId = xaiHTTPCallResponse
						.indexOf("<meterReadId>")
						+ "<meterReadId>".length();
				int endIndexMeterReadId = xaiHTTPCallResponse
						.indexOf("</meterReadId>");
				String meterReadId = xaiHTTPCallResponse.substring(
						beginIndexMeterReadId, endIndexMeterReadId);
				AppLog.info("meterReadId genarated " + meterReadId);
				// System.out.println("meterReadId genarated " + meterReadId);

			}
			if (xaiHTTPCallResponse.contains("<billId>")) {
				int beginIndexBillId = xaiHTTPCallResponse.indexOf("<billId>")
						+ "<billId>".length();
				int endIndexBillId = xaiHTTPCallResponse.indexOf("</billId>");
				billId = xaiHTTPCallResponse.substring(beginIndexBillId,
						endIndexBillId);
				if (billId != null) {
					isBillGenarated = true;
				}
				AppLog.info("billId generated " + billId);
				// System.out.println("billId generated " + billId);
			}
			if (xaiHTTPCallResponse.contains("<error>")) {
				int beginIndexError = xaiHTTPCallResponse.indexOf("<error>")
						+ "<error>".length();
				int endIndexError = xaiHTTPCallResponse.indexOf("</error>");
				String Error = xaiHTTPCallResponse.substring(beginIndexError,
						endIndexError);
				AppLog.info("Error generated " + Error);
				// System.out.println("Error generated " + Error);

				if (Error != null) {
					MeterReadDAO.updateWebServiceInBillingRemark(
							billGenerationDetails.getAcctId(),
							billGenerationDetails.getBillRoundId(), Error);
				}

				return "<error>" + Error + "</error>";

			}
		}

		catch (Exception e) {
			// e.printStackTrace();

			AppLog.info(e);
		}

		return billId;

	}
	
	/**
	 * <p>
	 * Method used to validate and save records before saving and even bill
	 * generations
	 * </p>
	 * 
	 * @param billGenerationDetails
	 * @return
	 */
	public HashMap<String, String> saveBill(
			BillGenerationDetails billGenerationDetails) {

		HashMap<String, String> returnMap = null;

		Map<Object, Object> inputMap = validateMeterReadUploadFields(billGenerationDetails);
		List<BillGenerationDetails> billGenDetailsList = (List<BillGenerationDetails>) inputMap
				.get("toUploadMeterReadDetailsList");

		List<ErrorDetails> billGenDetailsErrorList = (List<ErrorDetails>) inputMap
				.get("meterReadErrorList");

		List<MeterReadUploadErrorLogDetails> meterReadUploadErrorLogDetailsList = null;
		List<ErrorDetails> meterReadErrorList = null;

		if (billGenDetailsList.iterator().hasNext()) {

			Map<Object, Object> returnMapWithConsumerDetailsMap = validateMeterReadUploadAsPerBusinessProcess(inputMap);

			List<ConsumerDetail> consumerDetailList = (List<ConsumerDetail>) returnMapWithConsumerDetailsMap
					.get("consumerDetailList");

			meterReadUploadErrorLogDetailsList = (List<MeterReadUploadErrorLogDetails>) returnMapWithConsumerDetailsMap
					.get("meterReadUploadErrorLogDetailsList");

			meterReadErrorList = (List<ErrorDetails>) returnMapWithConsumerDetailsMap
					.get("meterReadErrorList");

			if (consumerDetailList.iterator().hasNext()) {

				// System.out.println("billingfacade meterReadUploadDetails.iterator()-------->");

				BillGenerationDetails billGenDeatils = (BillGenerationDetails) billGenDetailsList
						.iterator().next();

				ConsumerDetail consumerDetail = (ConsumerDetail) consumerDetailList
						.iterator().next();
				HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String, ConsumerDetail>();
				toUpdateMap.put("consumerDetail", consumerDetail);
				SetterDAO setterDAO = new SetterDAO();

				if (null == consumerDetail.getConsumerStatus()
						|| "".equals(consumerDetail.getConsumerStatus())
						|| "60".equals(consumerDetail.getConsumerStatus())) {
					returnMap = (HashMap<String, String>) setterDAO
							.saveMeterRead2(toUpdateMap, billGenerationDetails);
				} else {
					returnMap = (HashMap<String, String>) setterDAO
							.flagConsumerStatusBasedOnKNO(toUpdateMap,
									billGenerationDetails);
				}
				String isSuccess = "N";
				String recordUpdatedMsg = null;
				if (null != returnMap) {
					isSuccess = (String) returnMap.get("isSuccess");
					recordUpdatedMsg = (String) returnMap
							.get("recordUpdatedMsg");
				}
				if ("Y".equals(isSuccess)) {

					// System.out.println("isSuccessisSuccessisSuccessisSuccessisSuccessisSuccessisSuccess");
					// inputStream = new
					// StringBufferInputStream("<div class='search-option' title='Server Message'><font color='green' size='2'><b>Last Action-><font color='blue'>Record No: "
					// + (seqNoInt + 1)
					// + ", Service Cycle Route Sequence No: "
					// + consumerDetail.getServiceCycleRouteSeq()
					// + "</font>&nbsp;(KNO: "
					// + kno
					// + ") Saved Successfully.</b></font></div>");
					// returnMessage="sucess";
					// System.out.println("Data for KNO: " + kno
					// + " was Saved Successfully.");
				} else {
					if ("Frozen".equals(recordUpdatedMsg)) {
						// inputStream = new
						// StringBufferInputStream("<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action-> <font color='blue'>Record No: "
						// + (seqNoInt + 1)
						// + ", Service Cycle Route Sequence No: "
						// + consumerDetail
						// .getServiceCycleRouteSeq()
						// + "</font>&nbsp;(KNO: " + kno);
						// returnMessage="sucess";

					} else {
						// inputStream = new
						// StringBufferInputStream("<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action-> Record No: "
						// + (seqNoInt + 1)
						// + ", Service Cycle Route Sequence No: "
						// + consumerDetail
						// .getServiceCycleRouteSeq()
						// + "&nbsp;(KNO: "
						// + kno
						// +
						// ")  could not be Saved Successfully. Please Retry.</b></font></div>");
						// returnMessage="sucess";

					}
				}

			}// end of if case
		}

		if (billGenDetailsErrorList.iterator().hasNext()) {
			returnMap = new HashMap<String, String>();
			ErrorDetails errDetails = (ErrorDetails) billGenDetailsErrorList
					.iterator().next();
			// System.out.println("Error Deatils:::::::::::6th July"+errDetails.getErrorDescription());

			if (errDetails != null)
				returnMap.put("getValidatorError", errDetails
						.getErrorDescription());

		}

		if (meterReadUploadErrorLogDetailsList != null) {

			if (meterReadUploadErrorLogDetailsList.iterator().hasNext()) {
				// System.out.println("22222222222222222222222222222222");
				MeterReadUploadErrorLogDetails mrdErrorLog = (MeterReadUploadErrorLogDetails) meterReadUploadErrorLogDetailsList
						.iterator().next();

				returnMap = new HashMap<String, String>();
				ErrorDetails errDetailsMRD = (ErrorDetails) mrdErrorLog
						.getErrorDetails();
				// System.out.println("Error Deatils:::::::::::6th July"+errDetails.getErrorDescription());

				if (errDetailsMRD != null)
					returnMap.put("getValidatorErrorMRD", errDetailsMRD
							.getErrorDescription());
			}

		}

		if (meterReadErrorList != null) {

			if (meterReadErrorList.iterator().hasNext()) {
				// System.out.println("22222222222222222222222222222222");
				returnMap = new HashMap<String, String>();
				ErrorDetails errDetails = (ErrorDetails) meterReadErrorList
						.iterator().next();
				// System.out.println("Error Deatils:::::::::::6th July"+errDetails.getErrorDescription());

				if (errDetails != null)
					returnMap.put("getValidatorErrorFiltered", errDetails
							.getErrorDescription());
			}

		}

		return returnMap;// "sucess";

	}

}
