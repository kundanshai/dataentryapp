/************************************************************************************************************
 * @(#) JMSQueueBrowser.java 1.1 09 Jan 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * <p>
 * Utility class for Browsing JMS Queue for getting message count for the given
 * Queue and Provider.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-01-2015.
 */
public class JMSQueueBrowser {
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
	 * initial context. This property is specified in the system property file .
	 * If it is not specified in any of these sources,
	 * <tt>NoInitialContextException</tt> is thrown when an initial context is
	 * required to complete an operation.
	 * 
	 * <p>
	 * The value of this constant is "java.naming.factory.initial".
	 * </p>
	 */
	public static final String INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	/**
	 * <p>
	 * Constant that holds the name of the environment property for specifying
	 * the Queue Connection Factory to use. The value of the property should be
	 * the fully qualified class name of the factory class that will create an
	 * initial context. This property is specified in the system property file .
	 * If it is not specified in any of these sources,
	 * <tt>ClassCastException</tt> is thrown when an initial context is required
	 * to complete an operation.
	 * 
	 * </p>
	 */
	public static final String QUEUE_CONNECTION_FACTORY = "javax.jms.QueueConnectionFactory";

	/**
	 * <p>
	 * Get Initial Context for the JMS Queue.
	 * </p>
	 * 
	 * @return
	 * @throws NamingException
	 */
	private static InitialContext getInitialContext(String providerUrl)
			throws RuntimeException, NamingException, Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		if (!UtilityForAll.isEmptyString(providerUrl)) {
			env.put(Context.PROVIDER_URL, providerUrl);
		} else {
			throw new RuntimeException("No Provider Found !");
		}
		return new InitialContext(env);
	}

	/**
	 * @param providerUrl
	 * @param queueName
	 * @return
	 */
	@SuppressWarnings({ "unused" })
	public static int getJmsMessageCount(String providerUrl, String queueName) {
		int numMsgs = 0;
		try {
			// get the initial context
			InitialContext ctx = getInitialContext(providerUrl);

			// lookup the queue object
			Queue queue = (Queue) ctx.lookup(queueName);

			// lookup the queue connection factory
			QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx
					.lookup(QUEUE_CONNECTION_FACTORY);

			// create a queue connection
			QueueConnection queueConn = connFactory.createQueueConnection();

			// create a queue session
			QueueSession queueSession = queueConn.createQueueSession(false,
					QueueSession.AUTO_ACKNOWLEDGE);

			// create a queue browser
			QueueBrowser queueBrowser = queueSession.createBrowser(queue);

			// start the connection
			queueConn.start();

			// browse the messages
			@SuppressWarnings("rawtypes")
			Enumeration e = queueBrowser.getEnumeration();

			// count number of messages
			while (e.hasMoreElements()) {
				Message message = (Message) e.nextElement();
				numMsgs++;
			}

			// System.out.println(queue + " has " + numMsgs + " messages");

			// close the queue connection
			queueConn.close();
		} catch (Exception e) {
			AppLog.error(e);
		}
		return numMsgs;
	}
}
