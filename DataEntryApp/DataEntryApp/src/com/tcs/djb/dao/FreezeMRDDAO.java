/************************************************************************************************************
 * @(#) FreezeMRDDAO.java
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
 * DAO Class for MRD Entry Freeze Screen
 * </p>
 *
 */
public class FreezeMRDDAO implements Runnable {

	private String areaCode;

	private boolean isSuccess = false;
	private String mrCode;
	private final String storedProcQyery = "{call SQL_CNSM_NEW(?,?,?)}";
	// String storedProcQyeryTest = "{call MATIUR_TEST_INSERT_EMP(?)}";
	private String zoneCode;

	/**
	 * <p>
	 * Constructor for FreezeMRDDAO class
	 * </p>
	 * 
	 * @param zoneCode
	 * @param mrCode
	 * @param areaCode
	 */
	public FreezeMRDDAO(String zoneCode, String mrCode, String areaCode) {
		AppLog.begin();
		// System.out.println("IN FreezeMRDDAO");
		this.zoneCode = zoneCode;
		this.mrCode = mrCode;
		this.areaCode = areaCode;
		new Thread(this).start();
		// System.out.println("OUT FreezeMRDDAO");
		AppLog.end();

	}

	/**
	 * <p>
	 * This method is used to freeze MRD
	 * </p>
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void freezeMRD() {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			AppLog.begin();
			AppLog.info("IN FreezeMRD::zoneCode::" + zoneCode + "::mrCode::"
					+ mrCode + "::areaCode::" + areaCode);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQyery);
			int i = 0;
			cs.setString(++i, zoneCode.trim());
			cs.setString(++i, mrCode.trim());
			cs.setString(++i, areaCode.trim());
			cs.executeUpdate();
			AppLog.info("::FREEZE MRD INITIATED::FOR::ZONE::" + zoneCode
					+ "::MR CD::" + mrCode + "::AREA CODE::" + areaCode);
			isSuccess = true;

			// System.out.println("OUT FreezeMRD");
			AppLog.end();
		} catch (SQLException e) {
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
				AppLog.error(e);
			}
		}
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getMrCode() {
		return mrCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

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
		// System.out.println("IN run");
		try {

			freezeMRD();
			AppLog.end();
		} catch (Throwable t) {
			AppLog.error(t);
		}
		// System.out.println("OUT run");
		AppLog.end();
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setMrCode(String mrCode) {
		this.mrCode = mrCode;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

}
