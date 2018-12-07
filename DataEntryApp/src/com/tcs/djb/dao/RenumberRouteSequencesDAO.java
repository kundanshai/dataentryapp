/************************************************************************************************************
 * @(#) RenumberRouteSequencesDAO.java   24 May 2013
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
import com.tcs.djb.model.RenumberRouteSequencesDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Renumber Route Sequences screen.
 * </p>
 * 
 * @author Reshma Srivastava (Tata Consultancy Services)
 * @history on 24-05-2013 Reshma Edited service cycle and service route has been
 *          filtered out from queries.
 * @history on 12-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequences and
 *          getTotalCountOfConsumerDetailsListForRenumberRouteSequences to add
 *          sequenceRangeFrom and sequenceRangeTo while searching concumerList
 *          details.
 * @history on 21-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequences and
 *          getTotalCountOfConsumerDetailsListForRenumberRouteSequences to
 *          comment sequence range limits functionality .
 * 
 */
public class RenumberRouteSequencesDAO {

	/**
	 * <p>
	 * This method is used to get Consumer Details List For route Sequences
	 * updated.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 * @return
	 */
	public static List<RenumberRouteSequencesDetails> getConsumerDetailsListForNewRouteSequences(
			Map<String, String> selectedSPIDMap
	// , String pageNo,String recordPerPage
	) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		RenumberRouteSequencesDetails renumberRouteSequencesDetails = null;
		List<RenumberRouteSequencesDetails> renumberRouteSequencesDetailsList = new ArrayList<RenumberRouteSequencesDetails>();
		try {
			String spIDList = "''";
			if (null != selectedSPIDMap && !selectedSPIDMap.isEmpty()) {
				for (Entry<String, String> entry : selectedSPIDMap.entrySet()) {
					String[] parts = entry.getValue().split("-", 2);
					String spId = parts[0];
					// String newSequence = parts[1];
					spIDList += ",'" + spId + "'";
				}
			}
			String query = QueryContants
					.getConsumerDetailsListForNewRouteSequencesQuery(spIDList
					// ,pageNo, recordPerPage
					);
			// System.out.println("getTotalCountOfNewRouteSequences query::"
			// + query + "\nspIDList::" + spIDList);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				renumberRouteSequencesDetails = new RenumberRouteSequencesDetails();
				renumberRouteSequencesDetails.setKno(rs.getString("KNO"));
				renumberRouteSequencesDetails.setWcNo(rs.getString("WCNO"));
				renumberRouteSequencesDetails.setName(rs.getString("CONSNAME"));
				renumberRouteSequencesDetails.setAddress(rs.getString("ADDR"));
				renumberRouteSequencesDetails.setCategory(rs.getString("CAT"));
				renumberRouteSequencesDetails.setNewRouteSequences(rs
						.getString("RSEQ"));
				renumberRouteSequencesDetailsList
						.add(renumberRouteSequencesDetails);
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
		return renumberRouteSequencesDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get Consumer Details List For Renumber Route
	 * Sequences Screen.
	 * </p>
	 * 
	 * @param mrKey
	 * @param serviceCycle
	 * @param serviceRoute
	 * @return
	 */
	public static List<RenumberRouteSequencesDetails> getConsumerDetailsListForRenumberRouteSequences(
			String mrKey
	// , String serviceCycle, String serviceRoute
	// ,String pageNo,String recordPerPage
	// , String sequenceRangeFrom, String sequenceRangeTo
	) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		RenumberRouteSequencesDetails renumberRouteSequencesDetails = null;
		List<RenumberRouteSequencesDetails> renumberRouteSequencesDetailsList = new ArrayList<RenumberRouteSequencesDetails>();
		try {
			String query = QueryContants
					.getConsumerDetailsListForRenumberRouteSequencesQuery(mrKey
					// ,serviceCycle, serviceRoute
					// ,pageNo, recordPerPage
					// , sequenceRangeFrom, sequenceRangeTo
					);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				renumberRouteSequencesDetails = new RenumberRouteSequencesDetails();
				// renumberRouteSequencesDetails.setSlNo(rs.getString("SEQ_NO"));
				renumberRouteSequencesDetails.setOldRouteSequences(rs
						.getString("RSEQ"));
				renumberRouteSequencesDetails.setKno(rs.getString("KNO"));
				renumberRouteSequencesDetails.setWcNo(rs.getString("WCNO"));
				renumberRouteSequencesDetails.setName(rs.getString("CONSNAME"));
				renumberRouteSequencesDetails.setAddress(rs.getString("ADDR"));
				renumberRouteSequencesDetails.setCategory(rs.getString("CAT"));
				renumberRouteSequencesDetails.setSpID(rs.getString("SPID"));
				renumberRouteSequencesDetails.setNewRouteSequences(rs
						.getString("RSEQ"));
				renumberRouteSequencesDetailsList
						.add(renumberRouteSequencesDetails);
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
		return renumberRouteSequencesDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get Total Count Of Consumer Details List For
	 * Renumber Route Sequences Screen in the database.
	 * </p>
	 * 
	 * @param sequenceRangeTo
	 * @param sequenceRangeFrom
	 * @param mrKey
	 * @return
	 */
	public static int getTotalCountOfConsumerDetailsListForRenumberRouteSequences(
			String mrKey
	// , String serviceCycle, String serviceRoute
	// , String sequenceRangeFrom, String sequenceRangeTo
	) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfConsumerDetailsListForRenumberRouteSequencesQuery(mrKey
					// , serviceCycle, serviceRoute
					// , sequenceRangeFrom, sequenceRangeTo
					);
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
	 * This method is used to get the total number of route Sequences updated
	 * from database.
	 * </p>
	 * 
	 * @param selectedSPIDMap
	 * @return
	 */
	public static int getTotalCountOfNewRouteSequences(
			Map<String, String> selectedSPIDMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String spIDList = "''";
			if (null != selectedSPIDMap && !selectedSPIDMap.isEmpty()) {
				for (Entry<String, String> entry : selectedSPIDMap.entrySet()) {
					String[] parts = entry.getValue().split("-", 2);
					String spId = parts[0];
					// String newSequence = parts[1];
					spIDList += ",'" + spId + "'";
				}
			}
			String query = QueryContants
					.getTotalCountOfNewRouteSequencesQuery(spIDList);
			// System.out.println("getTotalCountOfNewRouteSequences query::"
			// + query + "\nSPIDList::" + spIDList);
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
	 * This method is used to update new route sequences into database.
	 * </p>
	 * 
	 * @param spID
	 * @param newRouteSequences
	 * @return
	 */
	public static int updateRouteSequences(String spID, String newRouteSequences) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int recordUpdated = 0;
		try {
			String query = QueryContants.renumberRouteSequencesUpdateQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != newRouteSequences
					&& !"".equalsIgnoreCase(newRouteSequences.trim())) {
				ps.setString(++i, newRouteSequences.trim());
			} else {
				ps.setString(++i, null);
			}
			ps.setString(++i, spID);
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				recordUpdated++;
			}
			if (null != ps) {
				ps.close();
				ps = null;
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
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		// AppLog.info("RECORD UPDATED:: " + recordUpdated);
		AppLog.end();
		return recordUpdated;
	}

}
