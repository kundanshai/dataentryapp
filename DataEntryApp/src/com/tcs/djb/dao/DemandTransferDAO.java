/************************************************************************************************************
 * @(#) DemandTransferDAO.java   08 May 2013
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
import java.util.Map;
import java.util.Map.Entry;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.DemandTransferDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Demand Transfer screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 08-05-2013
 * @history 21-05-2013 Matiur Rahman Added method
 *          getSortedKNOListForDemandTransfer.
 */
public class DemandTransferDAO {

	/**
	 * <p>
	 * Get Consumer Details List For Demand Transfer.
	 * </p>
	 * 
	 * @param mrKey
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static List<DemandTransferDetails> getConsumerDetailsListForDemandTransfer(
			String zone, String mrNo, String area, String mrKey, String pageNo,
			String recordPerPage) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DemandTransferDetails demandTransferDetails = null;
		List<DemandTransferDetails> demandTransferDetailsList = new ArrayList<DemandTransferDetails>();
		try {
			String query = QueryContants
					.getConsumerDetailsListForDemandTransferQuery(mrKey,
							pageNo, recordPerPage);
			AppLog.info("getConsumerDetailsListForDemandTransfer::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, mrKey);
			ps.setString(++i, mrKey);
			rs = ps.executeQuery();
			while (rs.next()) {
				demandTransferDetails = new DemandTransferDetails();
				demandTransferDetails.setSlNo(rs.getString("SEQ_NO"));
				demandTransferDetails.setKno(rs.getString("ACCT_ID"));
				demandTransferDetails.setWcNo(rs.getString("WCNO"));
				demandTransferDetails.setName(rs.getString("CONSUMER_NAME"));
				demandTransferDetails.setAddress(rs.getString("ADDRESS"));
				demandTransferDetails.setCategory(rs.getString("CATEGORY"));
				demandTransferDetails.setOldServiceCycleRouteSequence(rs
						.getString("ROUTESEQ"));
				demandTransferDetailsList.add(demandTransferDetails);
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
		return demandTransferDetailsList;
	}

	/**
	 * <p>
	 * get Consumer Details List For Demand Transfer initiated.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static List<DemandTransferDetails> getConsumerDetailsListForDemandTransfered(
			Map<String, String> selectedKNOMap, String userId, String pageNo,
			String recordPerPage) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DemandTransferDetails demandTransferDetails = null;
		List<DemandTransferDetails> demandTransferDetailsList = new ArrayList<DemandTransferDetails>();
		try {
			String knoList = "''";
			if (null != selectedKNOMap && !selectedKNOMap.isEmpty()) {
				for (Entry<String, String> entry : selectedKNOMap.entrySet()) {
					knoList += ",'" + entry.getValue() + "'";
				}
			}
			String query = QueryContants
					.getConsumerDetailsListForDemandTransferedQuery(knoList,
							userId, pageNo, recordPerPage);
			AppLog.info("getConsumerDetailsListForDemandTransfered::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				demandTransferDetails = new DemandTransferDetails();
				demandTransferDetails.setSlNo(rs.getString("SEQ_NO"));
				demandTransferDetails.setKno(rs.getString("ACCT_ID"));
				demandTransferDetails.setWcNo(rs.getString("WCNO"));
				demandTransferDetails.setName(rs.getString("CUST_NAME"));
				demandTransferDetails.setAddress(rs.getString("ADDRESS"));
				demandTransferDetails
						.setCategory(rs.getString("CUST_CATEGORY"));
				demandTransferDetails.setOldServiceCycleRouteSequence(rs
						.getString("OLD_RTE_SEQ"));
				demandTransferDetails.setOldMRKey(rs.getString("OLD_MRKEY"));
				demandTransferDetails.setStatus(rs.getString("STAT_FLG"));
				demandTransferDetails.setNewMRKey(rs.getString("NEW_MRKEY"));
				demandTransferDetails.setNewServiceCycleRouteSequence(rs
						.getString("NEW_RTE_SEQ"));
				demandTransferDetails.setRemarks(rs.getString("ERROR_MSG"));
				demandTransferDetails.setUserId(rs.getString("CREATED_BY")
						.toLowerCase());
				demandTransferDetailsList.add(demandTransferDetails);
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
		return demandTransferDetailsList;
	}

	/**
	 * <p>
	 * Get Sorted KNO List For Demand Transfer.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 * @return
	 */
	public static List<String> getSortedKNOListForDemandTransfer(
			Map<String, String> selectedKNOMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> sortedKNOList = new ArrayList<String>();
		try {
			String knoList = "''";
			if (null != selectedKNOMap && !selectedKNOMap.isEmpty()) {
				for (Entry<String, String> entry : selectedKNOMap.entrySet()) {
					knoList += ",'" + entry.getValue() + "'";
				}
			}
			String query = QueryContants
					.getSortedKNOListForDemandTransferQuery(knoList);
			// AppLog.info("getSortedKNOListForDemandTransferQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				sortedKNOList.add(rs.getString("ACCT_ID").trim());
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
		return sortedKNOList;
	}

	/**
	 * <p>
	 * get Total Count Of Consumer Details List For Demand Transfer Screen in
	 * the database.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKey
	 * @return
	 */
	public static int getTotalCountOfConsumerDetailsListForDemandTransfer(
			String zone, String mrNo, String area, String mrKey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfConsumerDetailsListForDemandTransferQuery(mrKey);
			// AppLog.info("getTotalCountOfConsumerDetailsListForDemandTransferQuery::"
			// + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, mrKey);
			ps.setString(++i, mrKey);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
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
		return totalRecords;
	}

	/**
	 * <p>
	 * To get the total number of records for HHD Mapping Screen in the
	 * database.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 * @param userId
	 * @return
	 */
	public static int getTotalCountOfDemandTransfered(
			Map<String, String> selectedKNOMap, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String knoList = "''";
			if (null != selectedKNOMap && !selectedKNOMap.isEmpty()) {
				for (Entry<String, String> entry : selectedKNOMap.entrySet()) {
					knoList += ",'" + entry.getValue() + "'";
				}
			}
			String query = QueryContants.getTotalCountOfDemandTransferedQuery(
					knoList, userId);
			// AppLog.info("getTotalCountOfDemandTransferedQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
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
		return totalRecords;
	}

	/**
	 * <p>
	 * Method to initiate Transfer Demand Process.
	 * </p>
	 * 
	 * @param kno
	 * @param newMRKey
	 * @param userId
	 * @return
	 */
	public static int initiateTransferDemand(String kno, String newMRKey,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsInserted = 0;
		try {
			String query = QueryContants.initiateTransferDemandQuery();
			AppLog.info("KNO::" + kno + "::New MRKEY::" + newMRKey
					+ "\ninitiateTransferDemandQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, newMRKey);
			ps.setString(++i, userId);
			ps.setString(++i, kno);
			rowsInserted = ps.executeUpdate();

		} catch (SQLException e) {
			// System.out.println("::ErrorCode::" + e.getErrorCode());
			if (e.getErrorCode() == 1) {
				rowsInserted = 1;
			} else {
				AppLog.error(e);
			}
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
		return rowsInserted;
	}
}
