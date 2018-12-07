/************************************************************************************************************
 * @(#) AppNameLoader.java 1.1 31 Mar 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tcs.djb.rms.model.CommonStore;

/**
 * Application Name loader to load on application startup.
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 31-03-2014
 * 
 * @see ServletContextListener
 */
public class AppNameLoader implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public AppNameLoader() {
		// TODO Auto-generated constructor stub
	}

	String appContext;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			appContext = servletContextEvent.getServletContext()
					.getContextPath();
			CommonStore.set("APP_CONTEXT", appContext);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		AppLog.begin();
		AppLog.info("Application with Context " + appContext
				+ " is up. Up Time:: " + (new java.util.Date()));
		AppLog.end();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		AppLog.begin();
		AppLog.info("Application with Context " + appContext
				+ " is down. Down Time:: " + (new java.util.Date()));
		AppLog.end();
	}

}
