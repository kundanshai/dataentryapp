/************************************************************************************************************
 * @(#) DBConnector.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * <p>
 * This is the Utility class that creates Database Connection to perform various
 * database related operations.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-10-2014
 * 
 */
@SuppressWarnings("unchecked")
public class DBConnector {
	/**
	 * <p>
	 * Loads environment variables into <code>Hashtable</code>
	 * </p>
	 * 
	 * @see Hashtable
	 */
	@SuppressWarnings("rawtypes")
	private static final Hashtable envParams = new Hashtable();
	/**
	 * <p>
	 * Loads Web Logic Initial Context Factory
	 * <code>WLInitialContextFactory</code>
	 * </p>
	 * 
	 * @see WLInitialContextFactory
	 */
	private static final String wlInitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
	static {
		envParams.put(Context.INITIAL_CONTEXT_FACTORY, wlInitialContextFactory);
		envParams.put(Context.PROVIDER_URL,
				PropertyUtil.getProperty("JNDI_PROVIDER"));
		envParams.put("weblogic.jndi.connectTimeout", new Long(15000));
		envParams.put("weblogic.jndi.responseReadTimeout", new Long(15000));
	}

	/**
	 * <p>
	 * Close database connection which is used in the <code>getConnection</code>
	 * method creates Database Connection to perform various database related
	 * operations with two parameter data source name and JNDI Provider URL.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 03-02-2015
	 * 
	 * @param conn
	 * 
	 * @throws IOException
	 * @see Connection
	 */
	public static void closeConnection(Connection conn) throws IOException {
		AppLog.begin();
		try {
			if (null != conn) {
				conn.close();
				AppLog.debugInfo("Connection Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Close database connection which is used in the <code>getConnection</code>
	 * method creates Database Connection to perform various database related
	 * operations with two parameter data source name and JNDI Provider URL.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 16-07-2015
	 * 
	 * @param conn
	 * @param ps
	 * 
	 * @throws IOException
	 * @see Connection
	 * @see CallableStatement
	 */
	public static void closeConnection(Connection conn, CallableStatement cs)
			throws IOException {
		AppLog.begin();
		try {
			if (null != conn) {
				conn.close();
				AppLog.debugInfo("Connection Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		try {
			if (null != cs) {
				cs.close();
				AppLog.debugInfo("Statment Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Close database connection which is used in the <code>getConnection</code>
	 * method creates Database Connection to perform various database related
	 * operations with two parameter data source name and JNDI Provider URL.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 03-02-2015
	 * 
	 * @param conn
	 * @param ps
	 * 
	 * @throws IOException
	 * @see Connection
	 * @see PreparedStatement
	 */
	public static void closeConnection(Connection conn, PreparedStatement ps)
			throws IOException {
		AppLog.begin();
		try {
			if (null != conn) {
				conn.close();
				AppLog.debugInfo("Connection Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		try {
			if (null != ps) {
				ps.close();
				AppLog.debugInfo("Statment Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Close database connection which is used in the <code>getConnection</code>
	 * method creates Database Connection to perform various database related
	 * operations with two parameter data source name and JNDI Provider URL.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 03-02-2015
	 * 
	 * @param conn
	 * @param ps
	 * @param rs
	 * 
	 * @throws IOException
	 * @see Connection
	 * @see PreparedStatement
	 * @see ResultSet
	 */
	public static void closeConnection(Connection conn, PreparedStatement ps,
			ResultSet rs) throws IOException {
		AppLog.begin();
		try {
			if (null != conn) {
				conn.close();
				AppLog.debugInfo("Connection Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		try {
			if (null != ps) {
				ps.close();
				AppLog.debugInfo("Statment Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		try {
			if (null != rs) {
				rs.close();
				AppLog.debugInfo("Resultset Closed");
			}
		} catch (Exception e) {
			// ignore;
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * <code>getConnection</code> method creates Database Connection to perform
	 * various database related operations.
	 * </p>
	 * 
	 * @return connection is the database connection
	 * @throws IOException
	 * @see Connection
	 * @see Context
	 * @see DataSource
	 * @see InitialContext
	 */
	public static Connection getConnection() throws IOException {
		AppLog.begin();
		Connection connection = null;
		Context context = null;
		String dataSourceReferenceName = null;
		DataSource ds = null;
		try {
			context = new InitialContext(envParams);
			dataSourceReferenceName = PropertyUtil.getProperty("JNDI_DS");
			ds = (DataSource) context.lookup(dataSourceReferenceName);
			connection = ds.getConnection();
		} catch (NamingException e) {
			AppLog.fatal(e);
		} catch (SQLException e) {
			AppLog.fatal(e);
		} catch (Exception e) {
			AppLog.fatal(e);
		}
		AppLog.end();
		return connection;
	}

	/**
	 * <p>
	 * Create database connection. The <code>getConnection</code> method creates
	 * Database Connection to perform various database related operations with
	 * two parameter data source name and JNDI Provider URL.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 27-08-2014
	 * 
	 * @param dataSourceName
	 *            JNDI Data Source Name
	 * @param providerURL
	 *            JNDI Provider URL
	 * @return connection is the database connection
	 * @throws IOException
	 * @see Connection
	 * @see Context
	 * @see DataSource
	 * @see InitialContext
	 */
	public static Connection getConnection(String dataSourceName,
			String providerURL) throws IOException {
		AppLog.begin();
		Connection connection = null;
		Context context = null;
		DataSource ds = null;
		Hashtable<String, String> contextParams = null;
		try {
			contextParams = new Hashtable<String, String>();
			contextParams.put(Context.INITIAL_CONTEXT_FACTORY,
					wlInitialContextFactory);
			contextParams.put(Context.PROVIDER_URL, providerURL.trim());
			context = new InitialContext(contextParams);
			ds = (DataSource) context.lookup(dataSourceName.trim());
			connection = ds.getConnection();
		} catch (NamingException e) {
			AppLog.fatal(e);
		} catch (SQLException e) {
			AppLog.fatal(e);
		} catch (Exception e) {
			AppLog.fatal(e);
		}
		AppLog.end();
		return connection;
	}
}
