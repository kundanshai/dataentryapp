/************************************************************************************************************
 * @(#) AMRDataUploadService.java
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.MRDFileUploadDAO;
import com.tcs.djb.model.AMRRecordDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.SOAPClient;
import com.tcs.djb.util.UtilityForAll;

/**
 * <p>
 * This class is used to upload AMR Data via CCB service soap xml by providing
 * the required parameters for the service
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * 
 */
public class AMRDataUploadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This method is used to make CCB Service call to upload AMR data
	 * </p>
	 * 
	 * @param aMRRecordDetailList
	 * @param authKey
	 * @param userID
	 * @param hhdId
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String aMRDataUpload(
			List<AMRRecordDetail> aMRRecordDetailList, String authKey,
			String userID, String hhdId) throws IOException, Exception {
		AppLog.begin();
		StringBuffer xmlSB = new StringBuffer(1024);
		String webServiceCallResponse = "";
		String mrkey = aMRRecordDetailList.get(0).getMrkey();
		String billRound = "";
		try {
//			URL url;
			String webServerAddress = DJBConstants.WEB_SERVICE_PROVIDER_URL;
			AppLog.info("::WEB Server Address::" + webServerAddress);
//			url = new URL(webServerAddress);
			String soapAction = DJBConstants.SOAP_ACTION;
			xmlSB
					.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:met=\"http://meterread.web.service.rms.djb.tcs.com/\">");
			xmlSB.append("<soapenv:Header/>");
			xmlSB.append("<soapenv:Body>");
			xmlSB.append("<met:meterReadUpload>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<arg0>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<freezeFlag>");
			xmlSB.append(DJBConstants.AMR_SERVICE_CALL_FREEZE_FLAG);
			xmlSB.append("</freezeFlag>");
			xmlSB.append("<!--Zero or more repetitions:-->");
			for (AMRRecordDetail aAMRRecordDetail : aMRRecordDetailList) {
				xmlSB.append("<meterReadUploadDetails>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<averageConsumption>");
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getCurrentAvgCons()) {
					xmlSB.append(aAMRRecordDetail.getCurrentAvgCons());
				}
				xmlSB.append("</averageConsumption>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<billRound>");
				// billRound value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getBillRoundId()) {
					xmlSB.append(aAMRRecordDetail.getBillRoundId());
					billRound = aAMRRecordDetail.getBillRoundId();
				}
				xmlSB.append("</billRound>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<consumerStatus>");
				// consumerStatus value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getConsumerStatus()) {
					xmlSB.append(aAMRRecordDetail.getConsumerStatus());
				}
				xmlSB.append("</consumerStatus>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<kno>");
				// kno value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getKno()) {
					xmlSB.append(aAMRRecordDetail.getKno());
				}
				xmlSB.append("</kno>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<meterRead>");
				// meterRead value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getCurrentMtrReading()) {
					xmlSB.append(aAMRRecordDetail.getCurrentMtrReading());
				}
				xmlSB.append("</meterRead>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<meterReadDate>");
				// meterReadDate value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getCurrMtrReadDate()) {
					xmlSB.append(aAMRRecordDetail.getCurrMtrReadDate());
				}
				xmlSB.append("</meterReadDate>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<meterReadRemark>");
				// meterReadRemark value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getCurrentMtrStatusCode()) {
					xmlSB.append(aAMRRecordDetail.getCurrentMtrStatusCode());
				}
				xmlSB.append("</meterReadRemark>");
				xmlSB.append("<!--Optional:-->");
				xmlSB.append("<noOfFloors>");
				// noOfFloors value
				if (null != aAMRRecordDetail
						&& null != aAMRRecordDetail.getCurrentNoOfFloors()) {
					xmlSB.append(aAMRRecordDetail.getCurrentNoOfFloors());
				}
				xmlSB.append("</noOfFloors>");
				xmlSB.append("</meterReadUploadDetails>");

			}
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<mrkey>");
			if (null != mrkey) {
				xmlSB.append(mrkey);
			}
			xmlSB.append("</mrkey>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<webServiceUserDetails>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<authKey>");
			if (null != authKey && !"".equalsIgnoreCase(authKey.trim())) {
				xmlSB.append(authKey);
			}
			xmlSB.append("</authKey>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<hhdID>");
			// hhdID value
			if (null != hhdId) {
				xmlSB.append(hhdId);
			}
			xmlSB.append("</hhdID>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<id>");
			// id value
			if (null != userID) {
				xmlSB.append(userID);
			}
			xmlSB.append("</id>");
			xmlSB.append("<!--Optional:-->");
			xmlSB.append("<src>");
			// src value
			xmlSB.append("</src>");
			xmlSB.append("</webServiceUserDetails>");
			xmlSB.append("</arg0>");
			xmlSB.append("</met:meterReadUpload>");
			xmlSB.append("</soapenv:Body>");
			xmlSB.append("</soapenv:Envelope>");

			String encodedXML = UtilityForAll.encodeString(xmlSB.toString());
			AppLog.info("***\nRMS WEB Service Request xml::\n" + encodedXML);
			SOAPClient aSOAPClient = new SOAPClient();
			webServiceCallResponse = aSOAPClient.call(encodedXML,
					webServerAddress, soapAction);
			AppLog
					.info("WebService Call Response::\n"
							+ webServiceCallResponse);
			Map<String, HashMap<String, String>> resultDetailMap = parseResponseXML(webServiceCallResponse);
			if (null != resultDetailMap && !resultDetailMap.isEmpty()) {
				if (null != resultDetailMap.get("KNO_ERROR_MAP")
						&& !resultDetailMap.get("KNO_ERROR_MAP").isEmpty()) {
					HashMap<String, String> knoErrorMap = resultDetailMap
							.get("KNO_ERROR_MAP");
					int updatedrow = 0;
					int consPreBillStatId = 0;
					try {
						consPreBillStatId = Integer
								.parseInt(DJBConstants.CODE_FOR_INVALID_DATA_IN_CONS_PRE_BILL_STAT_LOOKUP);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						AppLog.error(e);
					}
					Iterator<String> iterator = knoErrorMap.keySet().iterator();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						/*
						 * String remarks = "";
						 * if(knoErrorMap.get(key).contains(
						 * DJBConstants.CONS_PRE_BILL_REMARKS_OLD_CHAR)){
						 * 
						 * }
						 */
						/*
						 * Start : added if part and else part was normal
						 * 25-06-2016
						 */
						if (null == key || "".equalsIgnoreCase(key.trim())) {
							AppLog
									.info("KNO Not Found In Response :: Updating "
											+ knoErrorMap.get(key)
											+ " remarks for all the kno of service call ");
							for (AMRRecordDetail aAMRRecordDetail : aMRRecordDetailList) {
								updatedrow += MRDFileUploadDAO
										.updateConsPreBillProc(aAMRRecordDetail
												.getKno(),
												knoErrorMap.get(key),
												consPreBillStatId, billRound,
												userID);
								MRDFileUploadDAO.updateAmrRecordDetails(
										aAMRRecordDetail.getKno(), knoErrorMap
												.get(key), billRound);
							}
						}
						/*
						 * End : added if part and else part was normal
						 * 25-06-2016
						 */
						else {
							updatedrow += MRDFileUploadDAO
									.updateConsPreBillProc(key, knoErrorMap
											.get(key), consPreBillStatId,
											billRound, userID);
							MRDFileUploadDAO.updateAmrRecordDetails(key,
									knoErrorMap.get(key), billRound);
						}
					}
				}
			}

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return webServiceCallResponse;
	}

	/**
	 * <p>
	 * This method is used to make CCB Service call to parse response XML
	 * </p>
	 * 
	 * @param responseXML
	 * @return
	 */
	private static Map<String, HashMap<String, String>> parseResponseXML(
			String responseXML) {
		AppLog.begin();
		HashMap<String, HashMap<String, String>> returnMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> recordMap = new HashMap<String, String>();
		HashMap<String, String> knoErrorMap = new HashMap<String, String>();
		if (null != responseXML && !"".equalsIgnoreCase(responseXML.trim())) {
			/* Get Record Count */
			if (responseXML.indexOf("<responseStatus>") > -1) {
				String responseStatus = responseXML.substring(responseXML
						.indexOf("<responseStatus>") + 16, responseXML
						.indexOf("</responseStatus>"));
				AppLog.info("responseStatus::" + responseStatus);
				if (responseStatus.indexOf("record(s)") > -1) {
					String updatedRecord = responseStatus.substring(6,
							responseStatus.indexOf("record(s)")).trim();
					if (responseStatus.indexOf("Out of") > -1) {
						String totalRecord = responseStatus.substring(
								responseStatus.indexOf("Out of") + 6).trim();
						recordMap.put("TOTAL_RECORD", totalRecord);
						recordMap.put("UPDATED_RECORD", updatedRecord);
						AppLog.info("updatedRecord>>"
								+ recordMap.get("UPDATED_RECORD"));
						AppLog.info("totalRecord>>"
								+ recordMap.get("TOTAL_RECORD"));
					}
				}
			}
			/* Get KNO(s) and errors */
			if (responseXML.indexOf("<errorDescription>") > -1) {
				int errorDescriptionCount = StringUtils.countMatches(
						responseXML, "<errorDescription>");
				AppLog.info("errorDescriptionCount>>" + errorDescriptionCount);
				String errorDescriptionString = responseXML.substring(
						responseXML.indexOf("<errorDescription>"), responseXML
								.lastIndexOf("</errorDetails>") + 15);
				int lengthOfTotalErrorString = errorDescriptionString.length();
				AppLog.info("lengthOfTotalErrorString"
						+ lengthOfTotalErrorString);
				String nextString = errorDescriptionString;
				while (nextString.length() > 0) {
					if (nextString.indexOf("<errorDescription>") > -1
							&& nextString.indexOf("</errorDescription>") > -1) {
						String xmlString = nextString.substring(nextString
								.indexOf("<errorDescription>"), nextString
								.indexOf("</errorDescription>") + 19);
						String kno = "";
						if (nextString.indexOf("<kno>") > -1) {
							kno = nextString.substring(nextString
									.indexOf("<kno>") + 5, nextString
									.indexOf("</kno>"));
						}
						String errorDetails = xmlString.substring(xmlString
								.indexOf("<errorDescription>") + 18, xmlString
								.indexOf("</errorDescription>"));
						knoErrorMap.put(kno, errorDetails);
						AppLog.info("kno>>" + kno + "::" + errorDetails);
					}

					nextString = nextString.substring(nextString
							.indexOf("</errorDetails>") + 15);
				}
			}
			returnMap.put("RECORD_MAP", recordMap);
			returnMap.put("KNO_ERROR_MAP", knoErrorMap);
		}
		AppLog.end();
		return returnMap;
	}

}
