/************************************************************************************************************
 * @(#) DataMigrationReportDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Data Migration Report Generation Screen as per JTrac DJB-4465
 * and OpenProject-918.
 * </p>
 * 
 * @author Arnab Nandy (1227971)
 * @since 01-06-2016
 */

public class DataMigrationReportDAO {
	/**
	 *<p>
	 * Retrieve File list to populate File Drop down, as per JTrac DJB-4465 and
	 * OpenProject-918.
	 * </p>
	 * 
	 * @return
	 * @author Arnab Nandy
	 * @since 01-06-2016
	 */
	public static Map<String, String> getFileList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getFilesQuery();
			conn = DBConnector.getConnection();
			AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("source_file_name").trim(), rs
						.getString("source_file_name").trim());
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
		return retrunMap;
	}
}