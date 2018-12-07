/************************************************************************************************************
 * @(#) FileNumberSearchDAO.java   19 Jan 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.FileNumberSearchCrieteria;
import com.tcs.djb.model.FileNumberSearchDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for File Search Screen as per jtrac DJB-4326.
 * </p>
 * 
 * @author Arnab Nandy (Tata Consultancy Services)
 * @since 19-01-2016
 * @history 12-Feb-2016 Arnab edited in {@link #saveFileStatusTrack())} and
 *          added {@link #checkActionStatus())} for File Number Search Screen as
 *          per Jtrac DJB-4359 and and DJB-4365.
 */

public class FileNumberSearchDAO {
	/**
	 * <p>
	 * Checks action status Of File for target action status eligibility.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @param actionStatus
	 * @return
	 * @author Arnab Nandy
	 * @since 17-02-2016
	 * @as per Jtrac DJB-4359 and and DJB-4365.
	 */
	public static int checkActionStatus(String selectedFileNo,
			String actionStatus) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int statusFlg = 0;
		try {
			String query = QueryContants.checkActionStatusEligibility(
					selectedFileNo, actionStatus);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				statusFlg = rs.getInt("FOUND");
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
		AppLog.end();
		return statusFlg;
	}

	/**
	 * <p>
	 * Check ZRO for Users in the database.
	 * </p>
	 * 
	 * @param selectedZROCode
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static Map<String, String> checkZRO(String selectedZROCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.checkZRO(selectedZROCode);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("USER_ID>>" + rs.getString("USER_ID"));
				retrunMap.put(rs.getString("USER_ID").trim(), rs
						.getString("USER_ID"));
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

	/**
	 * <p>
	 * Search lot no Of File Details List For Search in the database.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static int getExistingLotCount(String selectedFileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int existingLotCount = 0;
		String lotNo = null;
		try {
			String query = QueryContants
					.getExistingLotCountQuery(selectedFileNo);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				existingLotCount = rs.getInt("existingLot");
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
		AppLog.end();
		return existingLotCount;
	}

	/**
	 * <p>
	 * Get File Details List For File Number Search Screen.
	 * </p>
	 * 
	 * @param fileNumberSearchCrieteria
	 * @param pageNo
	 * @param recordPerPage
	 * @param fileSearchStatusListMap
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static List<FileNumberSearchDetails> getFileNumberSearchDetailList(
			FileNumberSearchCrieteria fileNumberSearchCrieteria, String pageNo,
			String recordPerPage,
			HashMap<String, String> fileSearchStatusListMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FileNumberSearchDetails fileNumberSearchScreenDetails = null;
		List<FileNumberSearchDetails> fileNumberSearchScreenDetailsList = new ArrayList<FileNumberSearchDetails>();
		try {
			String query = QueryContants.getFileNumberSearchDetailListQuery(
					fileNumberSearchCrieteria, pageNo, recordPerPage);
			if (null != fileNumberSearchCrieteria) {
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					fileNumberSearchScreenDetails = new FileNumberSearchDetails();
					fileNumberSearchScreenDetails.setSlNo(rs
							.getString("SEQ_NO"));
					fileNumberSearchScreenDetails
							.setArn(rs.getString("ARN_NO"));
					fileNumberSearchScreenDetails.setFileNo(rs
							.getString("FILE_NO"));
					fileNumberSearchScreenDetails.setConsumerName(rs
							.getString("CONSUMER_NAME"));
					fileNumberSearchScreenDetails
							.setStatusCD(fileSearchStatusListMap.get(rs
									.getString("STATUS_CD")));
					fileNumberSearchScreenDetails.setLoggedBy(rs
							.getString("LOGGED_BY"));
					fileNumberSearchScreenDetails.setLoggedDate(rs
							.getString("LOGDATE"));
					fileNumberSearchScreenDetails.setLotNo(rs
							.getString("LOT_NO"));
					fileNumberSearchScreenDetailsList
							.add(fileNumberSearchScreenDetails);
				}
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
		return fileNumberSearchScreenDetailsList;
	}

	/**
	 * <p>
	 * Search lot no Of File Details List For files in the database.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static int getMissingLotCount(String selectedFileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int missingLotCount = 0;
		try {
			String query = QueryContants
					.getMissingLotCountQuery(selectedFileNo);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				missingLotCount = rs.getInt("existingLot");
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
		AppLog.end();
		return missingLotCount;
	}

	/**
	 * <p>
	 * get Total Count Of File Details List For Search Screen in the database.
	 * </p>
	 * 
	 * @param fileNumberSearchCrieteria
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static int getTotalCountOfFileDetailsListForSearchQuery(
			FileNumberSearchCrieteria fileNumberSearchCrieteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfFileDetailsListForSearchQueryQC(fileNumberSearchCrieteria);
			// AppLog.info("getTotalCountOfConsumerDetailsListForDemandTransferQuery::"
			// + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
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
	 * To save file status detail in case of status update.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @param actionStatus
	 * @return
	 * @author Arnab Nandy
	 * @since 02-02-2016
	 * @as per Jtrac DJB-4359 and DJB-4365
	 */
	// public static int saveFileStatusTrack(String selectedFileNo,
	// String actionStatus) {
	public static int saveFileStatusTrack(String selectedFileNo,
			String actionStatus, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int recordInserted = 0;
		// String selectedFNOArray[] = selectedFileNo.split(",");
		try {

			/*
			 * for (int i = 0; i < selectedFNOArray.length; i++) { String FileNo
			 * = selectedFNOArray[i]; String query =
			 * QueryContants.setFileStatusTracker(FileNo, actionStatus);
			 */

			// Start : Added by Arnab Nandy 15-02-2015 as per Jtrac DJB-4359.
			// Earlier we were sending list of files, now we had modified such
			// only one file will be used per call.
			String query = QueryContants.setFileStatusTracker(selectedFileNo,
					actionStatus, userId);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			recordInserted = ps.executeUpdate();
			AppLog.info(query);
			// End : Added by Arnab Nandy 15-02-2015 as per Jtrac DJB-4359.

			// }
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
		AppLog.info("File Status Tracking Insertion::" + recordInserted);
		AppLog.end();
		return recordInserted;
	}

	/**
	 * <p>
	 * Update action status Of File Details List For Search in the database.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @param actionStatus
	 * @return
	 * @author Arnab Nandy
	 * @since 02-02-2016
	 * @as per Jtrac DJB-4359 and DJB-4365
	 */
	// public static int updateActionStatus(String selectedFileNo,
	// String actionStatus) {
	public static int updateActionStatus(String selectedFileNo,
			String actionStatus, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int recordUpdated = 0;
		try {
			// String query = QueryContants.updateActionStatusforFiles(
			// selectedFileNo, actionStatus);
			String query = QueryContants.updateActionStatusforFiles(
					selectedFileNo, actionStatus, userId);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			recordUpdated = ps.executeUpdate();
			AppLog.info(recordUpdated + " records updated.");
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
		return recordUpdated;
	}

	/**
	 * <p>
	 * Update lot no Of File Details List For Search in the database.
	 * </p>
	 * 
	 * @param selectedFileNo
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static HashMap<String, String> updateLotNumber(String selectedFileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordUpdated = 0;
		int max_length = 0;
		String lotNo = null;
		HashMap<String, String> returnMap = new HashMap<String, String>();
		try {
			String query2 = QueryContants.getLotNo();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query2);
			rs = ps.executeQuery();
			while (rs.next()) {
				lotNo = rs.getString("LOT_NO");
			}
			if (null != ps) {
				ps.close();
			}
			if (null != conn) {
				conn.close();
			}
			// Date date = new Date();
			// SimpleDateFormat df = new SimpleDateFormat();
			// df.applyPattern("ddMMyyhhmmss");
			// lotNo = lotNo + df.format(date);
			// max_length = Integer.parseInt(DJBConstants.LOT_NO_MAX_LENGTH);
			// if (lotNo.length() > max_length) {
			// lotNo = lotNo.substring(1, max_length + 1);
			// }
			if (null != lotNo && lotNo.trim().length() > 0) {
				String query = QueryContants.updateLotNumberforFiles(
						selectedFileNo, lotNo);
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				recordUpdated = ps.executeUpdate();
				returnMap.put("lotNo", lotNo);
				returnMap.put("count", String.valueOf(recordUpdated));
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
		AppLog.info("Lot No.>>" + returnMap.get("lotNo") + ">>Updation Count>>"
				+ returnMap.get("count"));
		AppLog.end();
		return returnMap;
	}

	/**
	 * <p>
	 * Validate Files for ZRO locations in the database.
	 * </p>
	 * 
	 * @param fileNo
	 * @param deZroCd
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public static int validateFNO(String fileNo, String deZroCd) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int validStatus = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.validateFNO(fileNo, deZroCd);
			ps = conn.prepareStatement(query);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("validStatus>>" + validStatus);
				validStatus = rs.getInt("validstatus");
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
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return validStatus;
	}

}
