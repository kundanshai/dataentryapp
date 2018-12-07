/************************************************************************************************************
 * @(#) KNOValidationService.java
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call KNO Validation CCB service providing the required
 * parameters for the service
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class KNOValidationService implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This function will return the status of a KNO validating through CCB
	 * Service Call.
	 * </p>
	 * 
	 * @param KNO
	 * @return status
	 */
	public static String validateKNO(String kno) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String status = "INVALID";
		try {
			xml.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM-CheckKNO.xsd'>");
			xml.append("<soapenv:Header />");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM-CheckKNO>");
			xml.append("<cm:KNO>" + kno + "</cm:KNO>");
			xml.append("<cm:Result>?</cm:Result>");
			xml.append("</cm:CM-CheckKNO>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			AppLog.info("XAIHTTP Request xml::\n" + xml.toString());
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall();
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(xml.toString());
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);
			Document document = DocumentHelper.parseText(xaiHTTPCallResponse);
			Element root = document.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				for (Iterator i2 = element.elementIterator(); i2.hasNext();) {
					Element elementchild2 = (Element) i2.next();
					for (Iterator i3 = elementchild2.elementIterator(); i3
							.hasNext();) {
						Element elementchild3 = (Element) i3.next();
						status = elementchild3.getStringValue();
					}
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return status;
	}
}