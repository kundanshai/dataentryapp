/************************************************************************************************************
 * @(#) DataUploadDAO.java   20 Apr 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Data upload Screen as per JTrac DJB-4465 and OpenProject-918.
 * </p>
 * 
 * @author Lovely(986018)
 * @since 20-04-2016
 */

public class DataUploadDAO {
	/**
	 * <p>
	 * This query is for inserting data in database. as per JTrac DJB-4465 and
	 * OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */
	public static int insertData(String[] dataArray, String sourceFileName,
			int nbrOfRows) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsInserted = 0;
		int i = 0;
		int j = 0;
		try {

			String query = QueryContants.insertDataUploadQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, (dataArray[++j]));
			ps.setString(++i, sourceFileName);
			ps.setInt(++i, nbrOfRows);
			AppLog.info(query);
			rowsInserted = ps.executeUpdate();
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
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
		AppLog.end();
		return rowsInserted;
	}

	/**
	 * <p>
	 * This query is for fetching number of columns of table from database. as
	 * per JTrac DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */
	public static int isValidateHeaderColumnCount(String[] headerArray) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsCount = 0;
		int i = 0;
		try {
			String query = QueryContants.validateFileQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, DJBConstants.MAP_KEY);
			rs = ps.executeQuery();
			while (rs.next()) {
				rowsCount = rs.getInt("CNT");
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
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
		return rowsCount;
	}

	/**
	 * <p>
	 * This query is for fetching column of table from database. as per JTrac
	 * DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */
	public static ArrayList<String> isValidateHeaderColumnName(
			String[] headerArray) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		String columnName = null;
		ArrayList<String> columnList = new ArrayList<String>();
		try {
			String query = QueryContants.validateFileHeaderQuery();

			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, DJBConstants.MAP_KEY_VALUE);
			rs = ps.executeQuery();
			while (rs.next()) {
				columnName = rs.getString("COL_VAL");
				columnList.add(columnName);
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
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
		return columnList;
	}
}