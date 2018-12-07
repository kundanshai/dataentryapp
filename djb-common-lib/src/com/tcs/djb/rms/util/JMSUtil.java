/************************************************************************************************************
 * @(#) JMSUtil.java 1.1 09 Jan 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.Properties;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import weblogic.jms.extensions.JMSRuntimeHelper;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;

/**
 * <p>
 * Utility class for JMS.
 * </p>
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-01-2015
 * 
 * @see ServletContextListener
 */
public class JMSUtil {
	/**
	 * 
	 */
	private static JMSDestinationRuntimeMBean destMBean;

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
	 * Get Consumers Current Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getConsumersCurrentCount() {
		return destMBean.getConsumersCurrentCount();
	}

	/**
	 * <p>
	 * Get Consumers High Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getConsumersHighCount() {
		return destMBean.getConsumersHighCount();
	}

	/**
	 * <p>
	 * Get Consumers Total Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getConsumersTotalCount() {
		return destMBean.getConsumersTotalCount();
	}

	/**
	 * <p>
	 * Get Destination Type for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public String getDestinationType() {
		return destMBean.getDestinationType();
	}

	/**
	 * <p>
	 * Get Messages Current Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getMessagesCurrentCount() {
		return destMBean.getMessagesCurrentCount();
	}

	/**
	 * <p>
	 * Get Messages High Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getMessagesHighCount() {
		return destMBean.getMessagesHighCount();
	}

	/**
	 * <p>
	 * Get Messages Pending Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getMessagesPendingCount() {
		return destMBean.getMessagesPendingCount();
	}

	/**
	 * <p>
	 * Get Messages Received Count for the given Queue Name Provider
	 * </p>
	 * 
	 * @return
	 */
	public long getMessagesReceivedCount() {
		return destMBean.getMessagesReceivedCount();
	}

	/**
	 * <p>
	 * Default Constructor
	 * </p>
	 * 
	 * @param providerUrl
	 * @param queueName
	 * @param userId
	 * @param password
	 * @throws NamingException
	 * @throws JMSException
	 * @throws Exception
	 */
	public JMSUtil(String providerUrl, String queueName, String userId,
			String password) throws NamingException, JMSException, Exception {
		Properties env = new Properties();
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.SECURITY_PRINCIPAL, userId);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		InitialContext ctx = new InitialContext(env);
		Destination queue = (Destination) ctx.lookup(queueName);
		destMBean = JMSRuntimeHelper.getJMSDestinationRuntimeMBean(ctx, queue);
	}
}
