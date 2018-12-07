/************************************************************************************************************
 * @(#) MeterReplacementProcessService.java   04 Feb 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call Meter Replacement CCB service providing the
 * required parameters for the service
 * </p>
 * 
 * @author Rajib Hazarika
 * @history 04-02-2014 Matiur Rahman Changed the method return type for the
 *          changes of message in case of undefined error message as per JTrac
 *          ID DJB-2024 updated on 04-02-2014 as per telephonic conversation
 *          with Amit Jain.
 */
public class MeterReplacementProcessService implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This function is used to process the KNO's for meter replacement through
	 * CCB Service Call.
	 * </p>
	 * 
	 * @param KNO
	 * @param billRounId
	 * @return output, errorCode and ErrorStatus as comma seperated String value
	 * @history 04-02-2014 Matiur Rahman Changed the method return type from
	 *          output, errorCode and ErrorStatus as comma seperated String
	 *          value to a Map for the changes of message in case of undefined
	 *          error message as per JTrac ID DJB-2024 updated on 04-02-2014 as
	 *          per telephonic conversation with Amit Jain.
	 */
	public static Map<String, String> processKno(String kno,
			String billRoundId, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		Map<String, String> returnMap = new HashMap<String, String>();
		String status = "FAILURE";
		String errorMsg = "";
		String errorStatus = "";
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/Cm_MtrRplcMt.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:Cm_MtrRplcMt>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:acctId>" + kno + "</cm:acctId>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:billRoundId>" + billRoundId + "</cm:billRoundId>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:saShifted> </cm:saShifted>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:processflg> </cm:processflg>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:consPreBillStatId> </cm:consPreBillStatId>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:mtrRplcStageId> </cm:mtrRplcStageId>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:mtrRplcStageFlg> </cm:mtrRplcStageFlg>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:hiLoFlag> </cm:hiLoFlag>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:error> </cm:error>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:errorStatus> </cm:errorStatus>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:output> </cm:output>");
			xml.append("</cm:Cm_MtrRplcMt>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			String encodedXML = UtilityForAll.encodeString(xml.toString());
			// AppLog.info("XAIHTTP Request xml::\n" + xml.toString());
			AppLog.info("XAIHTTP Encoded Request xml::\n" + encodedXML);
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall(authCookie);
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);
			if (null != xaiHTTPCallResponse
					&& !"".equalsIgnoreCase(xaiHTTPCallResponse)) {
				if (xaiHTTPCallResponse.indexOf("<output>") > -1) {
					status = xaiHTTPCallResponse.substring(xaiHTTPCallResponse
							.indexOf("<output>")
							+ "<output>".length(), xaiHTTPCallResponse
							.indexOf("</output>"));
				}
				if ((xaiHTTPCallResponse.indexOf("<error>") > -1)
						&& (xaiHTTPCallResponse.indexOf("<errorStatus>") > -1)) {
					errorMsg = errorMsg
							+ xaiHTTPCallResponse.substring(xaiHTTPCallResponse
									.indexOf("<error>")
									+ "<error>".length(), xaiHTTPCallResponse
									.indexOf("</error>"));
					errorStatus = xaiHTTPCallResponse.substring(
							xaiHTTPCallResponse.indexOf("<errorStatus>")
									+ "<errorStatus>".length(),
							xaiHTTPCallResponse.indexOf("</errorStatus>"));
				}
			}
			returnMap.put("status", status);
			returnMap.put("errorMsg", errorMsg);
			returnMap.put("errorStatus", errorStatus);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnMap;
	}
}