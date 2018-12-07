/************************************************************************************************************
 * @(#) FileNbrAllocationDAO.java   12 Apr 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for File Number Allocation Screen as per jtrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867.
 * </p>
 * 
 * @author Lovely (Tata Consultancy Services)
 * @since 12-04-2016
 */

public class FileNbrAllocationDAO {

	/**
	 * <p>
	 * Checking valid file number allocation as per jtrac DJB-4442 and
	 * OpenProject-CODE DEVELOPMENT #867.
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 12-04-2016
	 */

	public static int checkFileNbr(String fileNbr) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.checkFileNbrQuery());
			int i = 0;
			ps.setString(++i, fileNbr);
			ps.setString(++i, fileNbr);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != rs) {
					rs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return count;
	}

	/**
	 * <p>
	 * function for inserting values in db for file number allocation screen as
	 * per jtrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867.
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 12-04-2016
	 */
	public static int insertOnSubmit(String selectedZROLocation,
		Long minRange, Long maxRange, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.setNewBookletQuery());
			AppLog.info(">>Query" + QueryContants.setNewBookletQuery());
			int i = 0;
			ps.setString(++i, selectedZROLocation);
			ps.setLong(++i, minRange);
			ps.setLong(++i, maxRange);
			ps.setString(++i, userId);
			totalRecords = ps.executeUpdate();
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.info("totalRecords>> " + totalRecords);
		AppLog.end();
		return totalRecords;
	}
}
