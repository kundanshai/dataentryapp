/************************************************************************************************************
 * @(#) ZROPaymentDayEndServices.java
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call CCB service to process ZRO payments by day end by
 * providing the required parameters for the service
 * </p>
 * 
 * @author Karthick K(Tata Consultancy Services)
 * 
 */
public class ZROPaymentDayEndServices implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This method calls the web service to send SMS to process payment by day
	 * end.
	 * </p>
	 * 
	 * @param
	 * @return status
	 */
	public static String processPaymentDayEnd(String tenderConID, String usID,
			String deviceID, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String status = "";
		String responseErrMsg = "FAIL";

		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_UpdateTenderControlStatusXAI.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml
					.append("<cm:CM_UpdateTenderControlStatusXAI faultStyle='wsdl'>");
			xml.append("<cm:pageHeader>");
			xml.append("<cm:tndrCtlId2>");
			xml.append(tenderConID);
			xml.append("</cm:tndrCtlId2>");
			xml.append("<cm:hhdId>");
			xml.append(deviceID);
			xml.append("</cm:hhdId>");
			xml.append("<cm:userId3>");
			xml.append(usID);
			xml.append("</cm:userId3>");
			xml.append("</cm:pageHeader>");
			xml.append("<cm:pageBody>");
			xml.append("<cm:tndrCtlId>");
			xml.append("</cm:tndrCtlId>");
			xml.append("<cm:tndrCtlStFlg>");
			xml.append("</cm:tndrCtlStFlg>");
			xml.append("<cm:totTndrCnt>");
			xml.append("</cm:totTndrCnt>");
			xml.append("<cm:expectedEndBalance>");
			xml.append("</cm:expectedEndBalance>");
			xml.append("<cm:tndrCtlUser>");
			xml.append("</cm:tndrCtlUser>");
			xml.append("<cm:depCtlUser>");
			xml.append("</cm:depCtlUser>");
			xml.append("<cm:errMessage>");
			xml.append("</cm:errMessage>");
			xml.append("</cm:pageBody>");
			xml.append("</cm:CM_UpdateTenderControlStatusXAI>");
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

				if ((xaiHTTPCallResponse.indexOf("<errMessage>") > -1)) {
					responseErrMsg = xaiHTTPCallResponse.substring(
							xaiHTTPCallResponse.indexOf("<errMessage>")
									+ "<errMessage>".length(),
							xaiHTTPCallResponse.indexOf("</errMessage>"));
				}
				if (responseErrMsg.equalsIgnoreCase("FAIL")) {
					
					System.out.println("responseErrMsg faild ??::"+responseErrMsg);
					
					if (xaiHTTPCallResponse.indexOf("<tndrCtlId>") > -1) {
						status = status
								+ "Tender Control ID is:"
								+ xaiHTTPCallResponse.substring(
										xaiHTTPCallResponse
												.indexOf("<tndrCtlId>")
												+ "<tndrCtlId>".length(),
										xaiHTTPCallResponse
												.indexOf("</tndrCtlId>")) + ","
								+ "\n";
					}

					if (xaiHTTPCallResponse.indexOf("<tndrCtlStFlg>") > -1) {
						status = status
								+ "Tender Status Flag is:"
								+ xaiHTTPCallResponse.substring(
										xaiHTTPCallResponse
												.indexOf("<tndrCtlStFlg>")
												+ "<tndrCtlStFlg>".length(),
										xaiHTTPCallResponse
												.indexOf("</tndrCtlStFlg>"))
								+ "," + "\n";
					}

					if (xaiHTTPCallResponse.indexOf("<totTndrCnt>") > -1) {
						status = status
								+ "Tender Control Count is:"
								+ xaiHTTPCallResponse.substring(
										xaiHTTPCallResponse
												.indexOf("<totTndrCnt>")
												+ "<totTndrCnt>".length(),
										xaiHTTPCallResponse
												.indexOf("</totTndrCnt>"))
								+ "," + "\n";
					}

					if (xaiHTTPCallResponse.indexOf("<expectedEndBalance>") > -1) {
						status = status
								+ "Expected End Balance is:"
								+ xaiHTTPCallResponse
										.substring(
												xaiHTTPCallResponse
														.indexOf("<expectedEndBalance>")
														+ "<expectedEndBalance>"
																.length(),
												xaiHTTPCallResponse
														.indexOf("</expectedEndBalance>"))
								+ "," + "\n";
					}

					if (xaiHTTPCallResponse.indexOf("<tndrCtlUser>") > -1) {
						status = status
								+ "Tender Control User is:"
								+ xaiHTTPCallResponse.substring(
										xaiHTTPCallResponse
												.indexOf("<tndrCtlUser>")
												+ "<tndrCtlUser>".length(),
										xaiHTTPCallResponse
												.indexOf("</tndrCtlUser>"))
								+ "," + "\n";
					}

					if (xaiHTTPCallResponse.indexOf("<depCtlUser>") > -1) {
						status = status
								+ "Deposite Control User is:"
								+ xaiHTTPCallResponse.substring(
										xaiHTTPCallResponse
												.indexOf("<depCtlUser>")
												+ "<depCtlUser>".length(),
										xaiHTTPCallResponse
												.indexOf("</depCtlUser>"))
								+ "," + "\n";
					}

				} else {
					status = "FAIL";

				}
System.out.println("status is ::"+status);
			}

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return status;
	}
}
