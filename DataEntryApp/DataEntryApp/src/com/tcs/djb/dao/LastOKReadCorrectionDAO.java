/************************************************************************************************************
 * @(#) LastOKReadCorrectionDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO Class for Last OK Read Correction Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class LastOKReadCorrectionDAO implements Runnable {

	private String kno;
	private String consumerName;
	private String lastOKReadDate;
	private String lastOKReadStatus;
	private String lastOKRead;
	private String userId;

	private boolean isSuccess = false;
	private final String storedProcQyery = "{call SQL_LOKR_PROC_SCR(?,to_date(?,'DD-MON-YYYY'),?,?,?)}";

	/**
	 * <p>
	 * This is a constructor for LastOKReadCorrectionDAO class
	 * </p>
	 * 
	 * @param zoneCode
	 * @param mrCode
	 * @param areaCode
	 */
	public LastOKReadCorrectionDAO(String kno, String lastOKReadDate,
			String lastOKRead, String lastOKReadStatus, String userId) {
		AppLog.begin();
		// System.out.println("IN LastOKReadCorrectionDAO");
		this.kno = kno;
		this.lastOKReadDate = lastOKReadDate;
		this.lastOKRead = lastOKRead;
		this.lastOKReadStatus = lastOKReadStatus;
		this.userId = userId;
		new Thread(this).start();
		// System.out.println("OUT LastOKReadCorrectionDAO");
		AppLog.end();

	}

	/**
	 * <p>
	 * This method is used to process last OK read for correction
	 * </p>
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void processLastOKReadCorrection() {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			AppLog.begin();
			// System.out.println("IN processLastOKReadCorrection");
			AppLog.info("IN LastOKReadCorrection process::kno::" + kno
					+ "::lastOKReadDate::" + lastOKReadDate + "::lastOKRead::"
					+ lastOKRead + "::lastOKReadStatus::" + lastOKReadStatus
					+ "::userId::" + userId);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQyery);
			int i = 0;
			cs.setString(++i, kno.trim());
			cs.setString(++i, lastOKReadDate.trim());
			cs.setInt(++i, Integer.parseInt(lastOKRead.trim()));
			cs.setInt(++i, Integer.parseInt(lastOKReadStatus.trim()));
			cs.setString(++i, userId.trim());
			cs.executeUpdate();
			AppLog
					.info("::IN LastOKReadCorrection process INITIATED::Procedure Name::SQL_LOKR_PROC_SCR");
			isSuccess = true;
			// System.out.println("OUT processLastOKReadCorrection");
			AppLog.end();
		} catch (SQLException e) {
			// e.printStackTrace();
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
			} catch (Exception e) {

			}
		}
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastOKRead
	 */
	public String getLastOKRead() {
		return lastOKRead;
	}

	/**
	 * @return the lastOKReadDate
	 */
	public String getLastOKReadDate() {
		return lastOKReadDate;
	}

	/**
	 * @return the lastOKReadStatus
	 */
	public String getLastOKReadStatus() {
		return lastOKReadStatus;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		// TODO Auto-generated method stub
		// System.out.println("IN run");
		try {
			processLastOKReadCorrection();
			AppLog.end();
		} catch (Throwable t) {
			AppLog.error(t);
		}
		// System.out.println("OUT run");
		AppLog.end();
	}

	/**
	 * @param consumerName
	 *            the consumerName to set
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastOKRead
	 *            the lastOKRead to set
	 */
	public void setLastOKRead(String lastOKRead) {
		this.lastOKRead = lastOKRead;
	}

	/**
	 * @param lastOKReadDate
	 *            the lastOKReadDate to set
	 */
	public void setLastOKReadDate(String lastOKReadDate) {
		this.lastOKReadDate = lastOKReadDate;
	}

	/**
	 * @param lastOKReadStatus
	 *            the lastOKReadStatus to set
	 */
	public void setLastOKReadStatus(String lastOKReadStatus) {
		this.lastOKReadStatus = lastOKReadStatus;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
