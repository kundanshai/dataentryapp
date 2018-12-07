/************************************************************************************************************
 * @(#) SendSMSService.java   09 Mar 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.io.Serializable;
import java.io.IOException;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.rms.model.SessionDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call CCB service to send SMS to mobile numbers by
 * providing the required parameters for the service
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 09-03-2016 for mseva audit action as per jTrac ANDROID-293
 * 
 */
public class SendSMSService implements Serializable {


	/**
	 * <p>
	 * This method calls the web service to send SMS to a mobile number. If
	 * mobile number is passed SMS sent directly to the mobile number and if KNO
	 * is passed SMS is sent to the registered mobile number.
	 * </p>
	 * 
	 * @param sessionDetails
	 * @param kno
	 * @param mobNo
	 * @param msgText
	 * @param authCookie
	 * @param serverAddress
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public String send(SessionDetails sessionDetails, String kno, String mobNo,
			String msgText, String authCookie, String serverAddress)
			throws IOException, Exception {
		AppLog.begin(sessionDetails);
		String xaiHTTPCallResponse;
		XAIHTTPCall xaiHTTPCall = null;
		StringBuffer xmlSB = new StringBuffer(512);
		xmlSB.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_SEND_SMS_XAI.xsd'>");
		xmlSB.append("<soapenv:Header />");
		xmlSB.append("<soapenv:Body>");
		xmlSB.append("<cm:CM_SEND_SMS_XAI faultStyle='wsdl'>");
		xmlSB.append("<cm:pageHeader>");
		xmlSB.append("<cm:accountId>");
		xmlSB.append((null == kno ? "" : kno.trim()));
		xmlSB.append("</cm:accountId>");
		xmlSB.append("<cm:messageText>");
		xmlSB.append((null == msgText ? "" : msgText.trim()));
		xmlSB.append("</cm:messageText>");
		xmlSB.append("<cm:mobileNumber>");
		xmlSB.append((null == mobNo ? "" : mobNo.trim()));
		xmlSB.append("</cm:mobileNumber>");
		xmlSB.append("</cm:pageHeader>");
		xmlSB.append("<cm:pageBody>");
		xmlSB.append("<cm:output></cm:output>");
		xmlSB.append("</cm:pageBody>");
		xmlSB.append("</cm:CM_SEND_SMS_XAI>");
		xmlSB.append("</soapenv:Body>");
		xmlSB.append("</soapenv:Envelope>");
		String requestXml = xmlSB.toString();
		if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
			xaiHTTPCall = new XAIHTTPCall(authCookie);
		} else {
			xaiHTTPCall = new XAIHTTPCall();
		}
		xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(requestXml);
		String messageSent = DJBConstants.SMS_SENT_FAILURE_RESPONSE;
		if (xaiHTTPCallResponse.contains("<output>")) {
			int beginIndexResult = xaiHTTPCallResponse.indexOf("<output>")
					+ "<output>".length();
			int endIndexResult = xaiHTTPCallResponse.indexOf("</output>");
			messageSent = xaiHTTPCallResponse.substring(beginIndexResult,
					endIndexResult);
		}
		
		AppLog.end();
		return messageSent;
	}
}
