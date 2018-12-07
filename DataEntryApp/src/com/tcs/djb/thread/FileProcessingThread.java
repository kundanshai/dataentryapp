/************************************************************************************************************
 * @(#) FileProcessingThread.java   20 Apr 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.thread;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;

import com.tcs.djb.rms.util.AppLog;
import com.tcs.djb.util.DBConnector;

import com.tcs.djb.util.ExcelFileParser;

/**
 * <p>
 * This thread is for initiating data insertion in table Data upload Screen as
 * JTrac DJB-4465 and OpenProject-918.
 * </p>
 * 
 * @author Lovely(Tata Consultancy Services)
 * @since 20-04-2016
 * 
 * @history Arnab Nandy (1227971) on 31-05-2016 added {@link #zroCode} and
 *          edited {@link #FileProcessingThread(String, String, String)} and
 *          {@link #callProcedure()} according to JTrac DJB-4465 and
 *          OpenProject-918.
 */
public class FileProcessingThread implements Runnable {
	/**
	 * 
	 */
	public String filePath;
	public String sourceFileName;
	public String zroCode;

	/**
	 * @history Arnab Nandy (1227971) updated constructor definition with a new
	 *          parameter for ZRO code according to JTrac DJB-4465 and
	 *          OpenProject-918.
	 */
	public FileProcessingThread(String filePath, String sourceFileName,
			String zroCode) {
		AppLog.begin();
		this.filePath = filePath;
		this.sourceFileName = sourceFileName;
		this.zroCode = zroCode;
		new Thread(this).start();
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used call procedure CM_DWARKA_DATA_MIGRATION to start
	 * thread
	 * </p>
	 * 
	 * @history Arnab Nandy (1227971) on 31-05-2016 added new parameter to
	 *          procedure call according to JTrac DJB-4465 and OpenProject-918.
	 */
	public void callProcedure() {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		int i = 0;
		try {
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(QueryContants.PROCQUERY);
			cs.setString(++i, sourceFileName);
			cs.setString(++i, zroCode);
			cs.setString(++i, DJBConstants.DB_SERVER_IPADDR);
			cs.setString(++i, DJBConstants.DB_SERVER_IPADDRR);
			AppLog
					.info("########  PROCEDURE CALL CM_DWARKA_DATA_MIGRATION THREAD STARTED #####");
			int procReturnCode = cs.executeUpdate();
			AppLog
					.info("######## PROCEDURE CM_DWARKA_DATA_MIGRATION CALL EXECUTION COMPLETED ##### procReturnCode>>"
							+ procReturnCode);
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		try {
			int rowInsertCount = ExcelFileParser.parse(new File(filePath),
					DJBConstants.ROWS_TO_EXEMPT, sourceFileName);
			AppLog.debug("rowInsertCount>>>" + rowInsertCount);
			if (rowInsertCount > 0) {
				AppLog
						.info(" #### PROCEDURE CM_DWARKA_DATA_MIGRATION CALLED ::######");
				callProcedure();
			}
		}

		catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} catch (Throwable e) {
			AppLog.error(e);
		}
		AppLog.end();
	}
}