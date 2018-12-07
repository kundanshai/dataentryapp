/************************************************************************************************************
 * @(#) PostToQueueUtil.java 1.0 27 Aug 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * <p>
 * Utility class for submitting Object to a JMS Queue. The records are posted to
 * JMS queue as message from where a Listening Application picks up the message
 * and for processing.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 26-06-2013
 * 
 */
public class PostToQueueUtil {

	/**
	 * <p>
	 * Initial Context Factory for the JMS Queue lookup.
	 * <code>weblogic.jar</code> must be in class path for Initial Context
	 * Factory <code>WLInitialContextFactory</code>.
	 * </p>
	 * <p>
	 * Constant that holds the name of the environment property for specifying
	 * the initial context factory to use. The value of the property should be
	 * the fully qualified class name of the factory class that will create an
	 * initial context. This property is specified in the system property file
	 * <code>online_batch_billing.properties</code>. If it is not specified in
	 * any of these sources, <tt>NoInitialContextException</tt> is thrown when
	 * an initial context is required to complete an operation.
	 * 
	 * <p>
	 * The value of this constant is "java.naming.factory.initial".
	 * </p>
	 */
	public static final String INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
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
	 * Provider URL for the JMS Queue lookup. Constant that holds the name of
	 * the environment property for specifying configuration information for the
	 * service provider to use. The value of the property should contain a URL
	 * string (e.g. "protocol://somehost:port"). This property is specified in
	 * the system property file <code>online_batch_billing.properties</code>.
	 * 
	 * <p>
	 * The value of this constant is "java.naming.provider.url".
	 * </p>
	 * 
	 */
	public static final String PROVIDER_URL = PropertyUtil
			.getProperty("JMS_PROVIDER_URL");

	/**
	 * <p>
	 * Create Initial Context for the JMS Queue.
	 * </p>
	 * 
	 * @return
	 * @throws NamingException
	 */
	private static InitialContext getInitialContext(String providerUrl)
			throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		if (UtilityForAll.isEmptyString(providerUrl)) {
			env.put(Context.PROVIDER_URL, PROVIDER_URL);
		} else {
			env.put(Context.PROVIDER_URL, providerUrl);
		}
		return new InitialContext(env);
	}

	/**
	 * <p>
	 * Method for posting message to a Queue. The Queue name and message is
	 * passed a parameter which is sent to the JMS Queue. From the JMS Queue a
	 * listening application picks up the message and processes further.
	 * </p>
	 * <p>
	 * <b>Note:</b>Exception is not handled here, it should be handled by the
	 * caller method to process accordingly.
	 * </p>
	 * 
	 * @see QueueConnectionFactory
	 * @see QueueConnection
	 * @see QueueSession
	 * @see QueueSender
	 * @see Queue
	 * @see TextMessage
	 * 
	 * @param messageString
	 *            The message to be posted in queue
	 * @param jmsQueueName
	 *            JNDI Name of the JMS Queue
	 * @return true if successfully posted else exception thrown and must be
	 *         caught in the caller method.
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 08-09-2014
	 * 
	 */
	public static boolean postToQueue(String messageString, String jmsQueueName)
			throws Exception {
		AppLog.begin();
		QueueConnectionFactory queueConnectionFactory;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		Queue queue;
		TextMessage textMessage;
		/* Create the context. */
		InitialContext context = getInitialContext(null);

		queueConnectionFactory = (QueueConnectionFactory) context
				.lookup(JMS_FACTORY);
		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) context.lookup(jmsQueueName);

		queueSender = queueSession.createSender(queue);
		textMessage = queueSession.createTextMessage();
		queueConnection.start();
		textMessage.setText(messageString);
		/* Send the textMessage to the JMS Queue. */
		queueSender.send(textMessage);
		AppLog.debugInfo("Sent to Queue::" + jmsQueueName + "::Message::"
				+ messageString);
		AppLog.end();
		return true;
	}

	/**
	 * <p>
	 * Method for posting message to a Queue. The Queue name and message is
	 * passed a parameter which is sent to the JMS Queue. From the JMS Queue a
	 * listening application picks up the message and processes further.
	 * </p>
	 * <p>
	 * <b>Note:</b>Exception is not handled here, it should be handled by the
	 * caller method to process accordingly.
	 * </p>
	 * 
	 * @see QueueConnectionFactory
	 * @see QueueConnection
	 * @see QueueSession
	 * @see QueueSender
	 * @see Queue
	 * @see TextMessage
	 * 
	 * @param messageString
	 *            The message to be posted in queue
	 * @param jmsQueueName
	 *            JNDI Name of the JMS Queue
	 * @param jmsQueueProviderUrl
	 *            JMS Queue provider URL
	 * @return true if successfully posted else exception thrown and must be
	 *         caught in the caller method.
	 * @throws Exception
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-11-2014
	 * 
	 */
	public static boolean postToQueue(String messageString,
			String jmsQueueName, String jmsQueueProviderUrl) throws Exception {
		AppLog.begin();
		QueueConnectionFactory queueConnectionFactory;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		Queue queue;
		TextMessage textMessage;
		/* Create the context. */
		InitialContext context = getInitialContext(jmsQueueProviderUrl);
		queueConnectionFactory = (QueueConnectionFactory) context
				.lookup(JMS_FACTORY);
		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) context.lookup(jmsQueueName);

		queueSender = queueSession.createSender(queue);
		textMessage = queueSession.createTextMessage();
		queueConnection.start();
		textMessage.setText(messageString);
		/* Send the textMessage to the JMS Queue. */
		queueSender.send(textMessage);
		AppLog.debugInfo("Sent to Queue::" + jmsQueueName + "::Message::"
				+ messageString);
		AppLog.end();
		return true;
	}
}
