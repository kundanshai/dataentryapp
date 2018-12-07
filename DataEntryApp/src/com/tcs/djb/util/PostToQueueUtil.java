/************************************************************************************************************
 * @(#) PostToQueueUtil.java   26 Jun 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.model.BillGenerationDetails;

/**
 * <p>
 * Utility class for submitting Object to a JMS Queue. The records are posted to
 * JMS queue as message from where the Billing Application picks up the message
 * and process for billing.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 26-06-2013
 * 
 */
public class PostToQueueUtil {
	/**
	 * <p>
	 * JMS Queue JNDI name for Bill generation. Value of static variable
	 * <code>JMS_QUEUE_JNDI_NAME_BILL_GEN</code> is retrieved from Property File
	 * <code>djb_portal_data_entry.properties</code> for Online Bill Generation.
	 * </p>
	 */
	private final static String JMS_QUEUE_JNDI_NAME_BILL_GEN = PropertyUtil
			.getProperty("JMS_QUEUE_JNDI_NAME_BILL_GEN");
	/**
	 * <p>
	 * Value of static variable <code>JMS_FACTORY</code> holds the JMS Queue
	 * Connection Factory.
	 * </p>
	 * 
	 * @see QueueConnectionFactory
	 */
	private final static String JMS_FACTORY = "javax.jms.QueueConnectionFactory";

	/**
	 * <p>
	 * Post to Bill Generation Details to the Queue. The Queue name is taken
	 * from Property file with key <code>JMS_QUEUE_JNDI_NAME_BILL_GEN</code>. it
	 * creates a TestMessage in form of soap message and sends to the JMS Queue.
	 * From the JMS Queue a billing application picks up the message and call
	 * the CC&B service for Bill Generation.
	 * </p>
	 * 
	 * @param billGenerationDetails
	 *            details required for bill generation.
	 * @return true or false as success
	 * @see QueueConnectionFactory
	 * @see QueueConnection
	 * @see QueueSession
	 * @see QueueSender
	 * @see Queue
	 * @see TextMessage
	 */
	public static boolean postToQueue(
			BillGenerationDetails billGenerationDetails) {
		AppLog.begin();
		QueueConnectionFactory queueConnectionFactory;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		Queue queue;
		TextMessage textMessage;
		try {
			/* Create the context. */
			InitialContext context = new InitialContext();

			queueConnectionFactory = (QueueConnectionFactory) context
					.lookup(JMS_FACTORY);
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueSession = queueConnection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queue = (Queue) context.lookup(JMS_QUEUE_JNDI_NAME_BILL_GEN);

			queueSender = queueSession.createSender(queue);
			textMessage = queueSession.createTextMessage();
			queueConnection.start();
			String messageString = createBillGenerationMessage(billGenerationDetails);
			textMessage.setText(messageString);
			/* Send the textMessage to the JMS Queue. */
			queueSender.send(textMessage);
			AppLog.info("The Bill Generation Details sent to "
					+ JMS_QUEUE_JNDI_NAME_BILL_GEN + "\nMessage::"
					+ messageString);
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			return false;
		} finally {
			try {
				if (null != queueSender) {
					queueSender.close();
				}
				if (null != queueSession) {
					queueSession.close();
				}
				if (null != queueConnection) {
					queueConnection.close();
				}
			} catch (Exception e) {
				// Ignore Exception
			}
		}
		AppLog.end();
		return true;
	}

	/**
	 * <p>
	 * Create the soap message for Online Billing Message Queue. This soap
	 * message is sent to the JMS Queue from where a Billing application picks
	 * up the message and calls the CC&B Service for Bill Generation.
	 * </p>
	 * 
	 * @param billGenerationDetails
	 * @return
	 */
	private static String createBillGenerationMessage(
			BillGenerationDetails billGenerationDetails) {
		StringBuffer xmlSB = new StringBuffer(512);
		try {
			String meterReadDateString = billGenerationDetails
					.getMeterReadDate();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date meterReadDate = null;
			if (null != meterReadDateString
					&& !"".equals(meterReadDateString.trim())) {
				meterReadDate = new java.sql.Date(formatter.parse(
						meterReadDateString).getTime());
			}
			xmlSB
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_OnlineBillGenerationService.xsd'>");
			xmlSB.append("<soapenv:Header />");
			xmlSB.append("<soapenv:Body>");
			xmlSB.append("<cm:CM_OnlineBillGenerationService>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:acctId>");
			xmlSB
					.append(null != billGenerationDetails.getKno() ? billGenerationDetails
							.getKno()
							: "");
			xmlSB.append("</cm:acctId>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:billRoundId>");
			xmlSB
					.append(null != billGenerationDetails.getBillRound() ? billGenerationDetails
							.getBillRound()
							: "");
			xmlSB.append("</cm:billRoundId>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:regRead>");
			xmlSB
					.append(null != billGenerationDetails.getMeterRead() ? billGenerationDetails
							.getMeterRead()
							: "");
			xmlSB.append("</cm:regRead>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:readType>");
			xmlSB
					.append(null != billGenerationDetails.getReadType() ? billGenerationDetails
							.getReadType()
							: "");
			xmlSB.append("</cm:readType>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:readerRemark>");
			xmlSB
					.append(null != billGenerationDetails.getMeterReadRemark() ? billGenerationDetails
							.getMeterReadRemark()
							: "");
			xmlSB.append("</cm:readerRemark>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:readDate>");
			xmlSB.append(null != meterReadDate ? meterReadDate : "");
			xmlSB.append("</cm:readDate>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:meterReaderName>");
			xmlSB
					.append(null != billGenerationDetails.getMeterReaderName() ? billGenerationDetails
							.getMeterReaderName()
							: "");
			xmlSB.append("</cm:meterReaderName>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:averageConsumption>");
			xmlSB
					.append(null != billGenerationDetails
							.getAverageConsumption() ? billGenerationDetails
							.getAverageConsumption() : "");
			xmlSB.append("</cm:averageConsumption>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:meterReadSource>");
			xmlSB.append(DJBConstants.METER_READ_SOURCE);
			xmlSB.append("</cm:meterReadSource>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:billGenerationSource>");
			xmlSB.append(DJBConstants.BILL_GENERATION_SOURCE);
			xmlSB.append("</cm:billGenerationSource>");
			xmlSB.append("<!-- Optional:-->");
			xmlSB.append("<cm:noOfFloors>");
			xmlSB
					.append(null != billGenerationDetails.getNoOfFloors() ? billGenerationDetails
							.getNoOfFloors()
							: "");
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
			// AppLog.info("Bill Generation CCB Service Request XML::\n"
			// + xmlSB.toString());
		} catch (Exception e) {
			AppLog.error(e);
		}
		return xmlSB.toString();
	}

}
