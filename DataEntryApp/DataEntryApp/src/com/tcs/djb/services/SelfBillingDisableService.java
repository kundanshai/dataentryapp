/************************************************************************************************************
 * @(#) SelfBillingDisableService.java   09 Mar 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.io.IOException;
import java.io.Serializable;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.rms.model.SessionDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call CCB service to disable self billing in system by
 * providing the required parameters for the service
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 09-03-2016 as per jTrac ANDROID-293
 */
public class SelfBillingDisableService implements Serializable {

		/**
		 * <p>
		 * This is to call CCB Service to disable self billing in system. It calls a
		 * CCB Zone based XAI Web service <code>CM_SwitchSelfBilling</code> to
		 * update account characteristic for the account(kno) passed as parameter.
		 * </p>
		 * 
		 * @param sessionDetails
		 * @param kno
		 * @param authCookie
		 * @param serverAddress
		 * @return list of Bill Details
		 * @throws Exception
		 * @throws IOException
		 */
		public String disableSelfBilling(SessionDetails sessionDetails, String kno,
				String authCookie, String serverAddress) throws IOException,
				Exception {
			AppLog.begin();
			StringBuffer xml = new StringBuffer(512);
			String xaiHTTPCallResponse;
			String response = DJBConstants.STATUS_FAILED_CODE;
			XAIHTTPCall xaiHTTPCall = null;
			xml.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_SwitchSelfBilling.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM_SwitchSelfBilling faultStyle=\"wsdl\">");
			xml.append("<cm:accountId>");
			xml.append(kno);
			xml.append("</cm:accountId>");
			xml.append("<cm:ConsumerSelfBilling>N</cm:ConsumerSelfBilling>");
			xml.append("</cm:CM_SwitchSelfBilling>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			AppLog.info("XAIHTTP Request xml::" + xml.toString());
			//String requestXml = xml.toString();
			if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
				xaiHTTPCall = new XAIHTTPCall(authCookie);
			} else {
				xaiHTTPCall = new XAIHTTPCall();
			}
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(xml.toString());
			AppLog.info("\n#################xaiHTTPCallResponse::\n"+xaiHTTPCallResponse);
			if(null!=xaiHTTPCallResponse && !"".equalsIgnoreCase(xaiHTTPCallResponse.trim())){
				if (xaiHTTPCallResponse.indexOf("ConsumerSelfBilling") > -1) {
					response = DJBConstants.STATUS_SUCCESS_CODE;
				} else if (xaiHTTPCallResponse.indexOf("Text:") > -1
						&& xaiHTTPCallResponse.indexOf("Table:") > -1) {
					response = xaiHTTPCallResponse.substring(xaiHTTPCallResponse.indexOf("Text:"),
							xaiHTTPCallResponse.indexOf("Table:"));
				} else if (xaiHTTPCallResponse.indexOf("<ResponseText>") > -1
						&& xaiHTTPCallResponse.indexOf("</ResponseText>") > -1) {
					response = xaiHTTPCallResponse.substring(
							xaiHTTPCallResponse.indexOf("<ResponseText>")
									+ "<ResponseText>".length(),
									xaiHTTPCallResponse.indexOf("</ResponseText>"));
				} else if (xaiHTTPCallResponse.indexOf("<faultstring>") > -1
						&& xaiHTTPCallResponse.indexOf("</faultstring>") > -1) {
					response = xaiHTTPCallResponse.substring(
							xaiHTTPCallResponse.indexOf("<faultstring>")
									+ "<faultstring>".length(),
									xaiHTTPCallResponse.indexOf("</faultstring>"));
				}
				
			}
			AppLog.end();
			return response;
		}

}
