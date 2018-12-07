/************************************************************************************************************
 * @(#) PreBillStatusDefinitionDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.PreBillStatusDefinitionDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Pre Bill Status Definition Screen.
 * </p>
 */
public class PreBillStatusDefinitionDAO {
	/**
	 * <p>
	 * This method is used to get all the records form CONS_PRE_BILL_STAT_LOOKUP
	 * Table.
	 * </p>
	 * 
	 * @param
	 * @return List<PreBillStatusDefinitionDetails>
	 */
	public static List<PreBillStatusDefinitionDetails> getPreBillStatusDefinitionList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PreBillStatusDefinitionDetails> preBillStatusDefinitionDetailsList = new ArrayList<PreBillStatusDefinitionDetails>();
		try {
			String query = QueryContants.getPreBillStatusDefinitionListQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				preBillStatusDefinitionDetailsList
						.add(new PreBillStatusDefinitionDetails(rs
								.getString("CONS_PRE_BILL_STAT_ID"), rs
								.getString("DESCR"), rs
								.getString("LAST_UPDATED_BY"), rs
								.getString("LAST_UPDATED_ON")));
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
		return preBillStatusDefinitionDetailsList;
	}

	/**
	 * <p>
	 * This method is used to add new record to CONS_PRE_BILL_STAT_LOOKUP Table.
	 * </p>
	 * 
	 * @param preBillStatusId
	 * @param preBillStatusDescription
	 * @param lastUpdatedBy
	 * @return totalRecords
	 */
	public static int updatePreBillStatusDefinitionList(String preBillStatusId,
			String preBillStatusDescription, String lastUpdatedBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.updatePreBillStatusDefinitionListQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, preBillStatusId);
			ps.setString(++i, preBillStatusDescription);
			ps.setString(++i, lastUpdatedBy);
			// ps.setString(++i, lastUpdatedOnAdd);
			totalRecords = ps.executeUpdate();
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
		return totalRecords;
	}

	/**
	 * <p>
	 * This method is used to validate the PreBillStatusId is already in
	 * CONS_PRE_BILL_STAT_LOOKUP Table or not.
	 * </p>
	 * 
	 * @param preBillStatusId
	 * @return boolean
	 */
	public static boolean validatePreBillStatusId(String preBillStatusId) {
		AppLog.begin();
		boolean isValid = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants.validatePreBillStatusIdQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, preBillStatusId);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("COUNT_CONS_PRE_BILL_STAT_ID");
			}
			if (totalRecords == 0) {
				isValid = true;
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
		return isValid;
	}
}
