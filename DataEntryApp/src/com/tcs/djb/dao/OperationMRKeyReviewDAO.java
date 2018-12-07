/************************************************************************************************************
 * @(#) OperationMRKeyReviewDAO.java   01 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.OperationMRKeyReviewDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for HHD OperationMRKeyReview.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 01-03-2013
 * @history 05-03-2013 Matiur Rahman changed method name
 *          getConsPreBillValidChangeStatusList to
 *          getFromConsPreBillValidStatLookupList.
 * @history 05-03-2013 Matiur Rahman Added method
 *          getToConsPreBillValidStatLookupListQuery.
 * @history 26-07-2013 Matiur Rahman added new parameter to
 *          {@link #getOperationMRKeyReviewDetailsList},
 *          {@link #getTotalCountOfOperationMRKeyReviewRecords}
 * @history 30-09-2013 Matiur Rahman Added functionality as per DJB-1970.
 */
public class OperationMRKeyReviewDAO {
	/**
	 * <p>
	 * Query to get Cons PreBill Status Lookup List Query.
	 * </p>
	 * 
	 * @return Cons PreBill Status Lookup List Map
	 */
	public static Map<String, String> getConsPreBillStatLookupList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new LinkedHashMap<String, String>(0);
		try {
			String query = QueryContants.getConsPreBillStatLookupListQuery();
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out.println("getConsPreBillStatLookupListQuery::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put(rs.getString("CONS_PRE_BILL_STAT_ID").trim(), rs
						.getString("DESCR").trim());
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to get valid Cons PreBill Status Lookup List for
	 * valid from cases.
	 * </p>
	 * 
	 * @param statusCode
	 * @return Cons PreBill Status Lookup List Map
	 */
	public static Map<String, String> getFromConsPreBillValidStatLookupListQuery(
			String statusCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new LinkedHashMap<String, String>(0);
		try {
			String query = QueryContants
					.getFromConsPreBillValidStatLookupListQuery();
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out.println("getConsPreBillStatLookupListQuery::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, statusCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put(rs.getString("CONS_PRE_BILL_STAT_ID").trim(), rs
						.getString("DESCR").trim());
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to get the Count Of Accounts To Be Updated from the
	 * Operation MR Key Review screen.
	 * </p>
	 * 
	 * @param mrKeyList
	 * @param mrKeyStatusCode
	 * @return
	 */
	public static int getNoOfAccountsToBeUpdated(String mrKeyList,
			String mrKeyStatusCode, String processCounter) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants.getNoOfAccountsToBeUpdatedQuery(
					mrKeyList, mrKeyStatusCode, processCounter);
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out.println("getNoOfAccountsToBeUpdatedQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_ACCOUNT");
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
	 * This method is used to retrieve Operation MR Key Review Details List from
	 * the database as per search criteria.
	 * </p>
	 * 
	 * @history 30-09-2013 Matiur Rahman added fromDate, toDate, orderBy,
	 *          orderByTo as per DJB-1970.
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param mrKeyListforSearch
	 * @param pageNo
	 * @param recordPerPage
	 * @param fromDate
	 * @param toDate
	 * @param orderBy
	 * @param orderByTo
	 * @param billRound
	 * @return operationMRKeyReviewDetailsList
	 */
	public static List<OperationMRKeyReviewDetails> getOperationMRKeyReviewDetailsList(
			String mrKeyStatusCode, String processCounter,
			String mrKeyListforSearch, String pageNo, String recordPerPage,
			String fromDate, String toDate, String orderBy, String orderByTo,
			String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OperationMRKeyReviewDetails operationMRKeyReviewDetails = null;
		List<OperationMRKeyReviewDetails> operationMRKeyReviewDetailsList = new ArrayList<OperationMRKeyReviewDetails>();
		try {
			String query = QueryContants
					.getOperationMRKeyReviewDetailsListQuery(mrKeyStatusCode,
							processCounter, mrKeyListforSearch, pageNo,
							recordPerPage, fromDate, toDate, orderBy,
							orderByTo, billRound);
			// AppLog.info("getOperationMRKeyReviewDetailsListQuery::" + query);
			// System.out.println("getOperationMRKeyReviewDetailsListQuery::"
			// + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			// int i = 0;
			// ps.setString(++i, mrKeyStatusCode);
			// ps.setString(++i, processCounter);
			rs = ps.executeQuery();
			while (rs.next()) {
				operationMRKeyReviewDetails = new OperationMRKeyReviewDetails();
				operationMRKeyReviewDetails.setSlNo(rs.getString("SEQ_NO"));
				operationMRKeyReviewDetails.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				operationMRKeyReviewDetails.setMrKey(rs.getString("MRKEY"));
				operationMRKeyReviewDetails.setTotalNoOfAccount(rs
						.getString("TOTAL_ACCOUNT"));
				operationMRKeyReviewDetails.setMrKeyStatusCode(rs
						.getString("CONS_PRE_BILL_STAT_ID"));
				operationMRKeyReviewDetails.setProcessCounter(rs
						.getString("PROCESS_COUNTER"));
				/*
				 * Start:30-09-2013 Matiur Rahman added as per DJB-1970.
				 */
				if ("Frozen Date".equalsIgnoreCase(orderBy)
						|| "Update Date".equalsIgnoreCase(orderBy)
						|| "Entry Date".equalsIgnoreCase(orderBy)
						|| "Park Date".equalsIgnoreCase(orderBy)
						|| "Bill Gen Date".equalsIgnoreCase(orderBy)) {
					operationMRKeyReviewDetails.setOrderBy(null != rs
							.getString("ORDER_BY") ? rs.getString("ORDER_BY")
							: "  ");
				}
				/*
				 * Start:30-09-2013 Matiur Rahman added as per DJB-1970.
				 */
				operationMRKeyReviewDetailsList
						.add(operationMRKeyReviewDetails);
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
		return operationMRKeyReviewDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get valid Cons PreBill Status Lookup List for
	 * valid to cases.
	 * </p>
	 * 
	 * @return Cons PreBill Status Lookup List Map
	 */
	public static Map<String, String> getToConsPreBillValidStatLookupList(
			String statusCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new LinkedHashMap<String, String>(0);
		try {
			String query = QueryContants
					.getToConsPreBillValidStatLookupListQuery();
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out.println("getConsPreBillStatLookupListQuery::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, statusCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put(rs.getString("CONS_PRE_BILL_STAT_ID").trim(), rs
						.getString("DESCR").trim());
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to get the total number of records for Operation MR
	 * Key Review Screen in the database.
	 * </p>
	 * 
	 * @history 30-09-2013 Matiur Rahman added fromDate, toDate, orderBy as per
	 *          DJB-1970.
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param mrKeyListforSearch
	 * @param fromDate
	 * @param toDate
	 * @param orderBy
	 * @param billRound
	 * @return TotalCountOfOperationMRKeyReviewRecords
	 */
	public static int getTotalCountOfOperationMRKeyReviewRecords(
			String mrKeyStatusCode, String processCounter,
			String mrKeyListforSearch, String fromDate, String toDate,
			String orderBy, String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfOperationMRKeyReviewRecordsQuery(
							mrKeyStatusCode, processCounter,
							mrKeyListforSearch, fromDate, toDate, orderBy,
							billRound);
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out
			// .println("getTotalCountOfOperationMRKeyReviewRecordsQuery query::"
			// + query);
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
	 * This method is used to update MRKey Status Code with the
	 * newMRKeyStatusCode updated from the Operation MR Key Review screen.
	 * </p>
	 * 
	 * @param mrKeyList
	 * @param hidMRKeyStatusCode
	 * @param hidProcessCounter
	 * @param newMRKeyStatusCode
	 * @return
	 */
	public static int updateMRKeyStatusCode(String mrKeyList,
			String hidMRKeyStatusCode, String hidProcessCounter,
			String newMRKeyStatusCode, String userName) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants.updateMRKeyStatusCodeQuery(mrKeyList);
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery query::" +
			// query);
			// System.out.println("updateMRKeyStatusCodeQuery::" + query
			// + "\nnewMRKeyStatusCode::" + newMRKeyStatusCode
			// + "::userName::" + userName + "::hidProcessCounter::"
			// + hidProcessCounter + "::hidMRKeyStatusCode::"
			// + hidMRKeyStatusCode);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, newMRKeyStatusCode);
			ps.setString(++i, userName);
			ps.setString(++i, hidProcessCounter);
			ps.setString(++i, hidMRKeyStatusCode);
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
}
