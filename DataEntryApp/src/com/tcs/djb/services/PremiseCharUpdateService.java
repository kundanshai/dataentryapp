/************************************************************************************************************
 * @(#) PremiseCharUpdateService.java   29 Aug 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call CCB service for updating premise characteristics
 * by providing the required parameters for the service
 * </p>
 * 
 * @author Aniket Chatterjee (Tata Consultancy Services)
 * @history:On 29-Aug-2016 682667(Rajib Hazarika) Added method
 *             {@link #updateRaybPremiseCharacteristics(String, String, String, String, String, String)}
 *             as per JTrac ID DJB-4537,Open project Id: 1441
 * @history: Lovely edited the code as per Jtrac DJB-4561 and OpenProject-CODE
 *           DEVELOPMENT #1489.
 */
public class PremiseCharUpdateService implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This function is used to update Premise Characteristics through CCB
	 * Service Call.
	 * </p>
	 * 
	 * @param KNO
	 * @return status
	 */
	public static String updatePremiseCharacteristics(String premiseId,
			String rainWaterhEligibilty, String approvedBy,
			String rainWaterhDocProof, String rainWaterhEffDt,
			String wasteWatertEligibilty, String wasteWatertDocProof,
			String wasteWatertEffDt, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String status = "INVALID";
		XAIHTTPCall xaiHTTPCall = null;
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_PREM_CHAR_RWH_UPDT.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM_PREM_CHAR_RWH_UPDT>");
			xml.append("<cm:premiseId>" + premiseId + "</cm:premiseId>");
			xml.append("<cm:RWHEligibilty>" + rainWaterhEligibilty
					+ "</cm:RWHEligibilty>");
			xml.append("<cm:RwhApprvdBy>" + approvedBy + "</cm:RwhApprvdBy>");
			xml.append("<cm:RwhDocProof>" + rainWaterhDocProof
					+ "</cm:RwhDocProof>");
			xml.append("<cm:RwhEffDt>" + rainWaterhEffDt + "</cm:RwhEffDt>");
			// Start : Edited by Lovely on 12-09-2016 as per jTrac- DJB-4561
			if (null != wasteWatertEligibilty
					&& !"".equals(wasteWatertEligibilty.trim())
					&& null != approvedBy && null != wasteWatertDocProof
					&& !"".equals(wasteWatertDocProof.trim())
					&& null != wasteWatertEffDt
					&& !"".equals(wasteWatertEffDt.trim())) {
				// End : Edited by Lovely on 12-09-2016 as per jTrac- DJB-4561
				xml.append("<cm:WWTEligibilty>" + wasteWatertEligibilty
						+ "</cm:WWTEligibilty>");
				xml.append("<cm:WwtApprvdBy>" + approvedBy
						+ "</cm:WwtApprvdBy>");
				xml.append("<cm:WwtDocProof>" + wasteWatertDocProof
						+ "</cm:WwtDocProof>");
				xml.append("<cm:WwtEffDt>" + wasteWatertEffDt
						+ "</cm:WwtEffDt>");
			}
			xml.append("<cm:Result>?</cm:Result>");
			xml.append("</cm:CM_PREM_CHAR_RWH_UPDT>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			AppLog.info("XAIHTTP Request xml::" + xml.toString());
			if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
				xaiHTTPCall = new XAIHTTPCall(authCookie);
			} else {
				xaiHTTPCall = new XAIHTTPCall();
			}
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(xml.toString());
			AppLog.info("XAIHTTP Call Response::" + xaiHTTPCallResponse);
			// Document document =
			// DocumentHelper.parseText(xaiHTTPCallResponse);
			// Element root = document.getRootElement();
			// for (Iterator i = root.elementIterator(); i.hasNext();) {
			// Element element = (Element) i.next();
			// for (Iterator i2 = element.elementIterator(); i2.hasNext();) {
			// Element elementchild2 = (Element) i2.next();
			// for (Iterator i3 = elementchild2.elementIterator(); i3
			// .hasNext();) {
			// Element elementchild3 = (Element) i3.next();
			// status = elementchild3.getStringValue();
			// }
			// }
			// }
			if (null != xaiHTTPCallResponse
					&& !xaiHTTPCallResponse.contains("Error")) {
				status = "SUCCESS";
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return status;
	}

	/**
	 * <p>
	 * This method will call the CC&B service to update RAYB Premise
	 * Characteristics Value JTrac ID#4537 Open Project Id#1441
	 * </p>
	 * 
	 * @Author Rajib Hazarika (Tata Consultancy Services)
	 * @since 29-AUG-2016
	 * @return status
	 */
	public static String updateRaybPremiseCharacteristics(String premiseId,
			String rainWaterhEligibilty, String approvedBy,
			String rainWaterhDocProof, String rainWaterhEffDt, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String status = "INVALID";
		XAIHTTPCall xaiHTTPCall = null;
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_PREM_CHAR_RAYB_UPDT.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM_PREM_CHAR_RAYB_UPDT>");
			xml.append("<cm:premiseId>" + premiseId + "</cm:premiseId>");
			xml.append("<cm:RWHEligibilty>" + rainWaterhEligibilty
					+ "</cm:RWHEligibilty>");
			xml.append("<cm:RwhApprvdBy>" + approvedBy + "</cm:RwhApprvdBy>");
			xml.append("<cm:RwhDocProof>" + rainWaterhDocProof
					+ "</cm:RwhDocProof>");
			xml.append("<cm:RwhEffDt>" + rainWaterhEffDt + "</cm:RwhEffDt>");
			xml.append("</cm:CM_PREM_CHAR_RAYB_UPDT>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			AppLog.info("XAIHTTP Request xml::" + xml.toString());
			if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
				xaiHTTPCall = new XAIHTTPCall(authCookie);
			} else {
				xaiHTTPCall = new XAIHTTPCall();
			}
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(xml.toString());
			AppLog.info("XAIHTTP Call Response::" + xaiHTTPCallResponse);
			if (null != xaiHTTPCallResponse
					&& !xaiHTTPCallResponse.toUpperCase().contains("ERROR")) {
				status = "SUCCESS";
				AppLog.info(">>status>>" + status);
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return status;
	}
}
