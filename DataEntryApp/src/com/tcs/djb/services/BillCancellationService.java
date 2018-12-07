/************************************************************************************************************
 * @(#) BillCancellationService.java   30 Jul 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to generate Bill Generation CCB service soap xml by
 * providing the required parameters for the service
 * </p>
 * 
 * @author Rajib Hazarika (Tata Consultancy Services)
 * @since 30-JUL-2014
 */
public class BillCancellationService implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This function will return the xml for CCB Bill Cancel Service which will
	 * cancel the latest Bill of the kno
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 30-JUL-2014
	 * @param kno
	 * @param authCookie
	 * @return
	 */

	public static String cancelBill(String kno, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String cancelStatus = "FAILURE";
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_ConsumerBillCancellation.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM_ConsumerBillCancellation>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageHeader>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:acctId>" + kno + "</cm:acctId>");
			xml.append("</cm:pageHeader>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageBody>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:output></cm:output>");
			xml.append("</cm:pageBody>");
			xml.append("</cm:CM_ConsumerBillCancellation>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			String encodedXML = UtilityForAll.encodeString(xml.toString());
			AppLog.info("XAIHTTP Request xml::\n" + xml.toString());
			AppLog.info("XAIHTTP Encoded Request xml::\n" + encodedXML);
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall(authCookie);
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);
			if (null != xaiHTTPCallResponse
					&& !"".equalsIgnoreCase(xaiHTTPCallResponse)) {
				if (xaiHTTPCallResponse.indexOf("<output>") > -1) {
					cancelStatus = xaiHTTPCallResponse.substring(
							xaiHTTPCallResponse.indexOf("<output>")
									+ "<output>".length(), xaiHTTPCallResponse
									.indexOf("</output>"));
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return cancelStatus;
	}
}