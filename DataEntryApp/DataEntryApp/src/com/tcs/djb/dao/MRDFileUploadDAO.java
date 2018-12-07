/************************************************************************************************************
 * @(#) MRDFileUploadDAO.java   02 June 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.AMRHeaderDetails;
import com.tcs.djb.model.AMRRecordDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for MRD File Upload through DataEntryApp during AMR Billing Process.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 02-06-2016 as per open project 1202,jTrac : DJB-4464
 * 
 * @history Sanjay Kumar Das on 31-08-2016 added AppLog for a query as per JTrac DJB-4553, Open project Id #1459
 */
public class MRDFileUploadDAO {

	/**
	 * <p>
	 * This method is used to get AMR records with specific Header Id's
	 * </p>
	 * 
	 * @param headerID
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static List<AMRRecordDetail> getAMRRecordsOfSpecificHeaderID(
			int headerID) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AMRRecordDetail> aMRRecordDetailList = new ArrayList<AMRRecordDetail>();
		try {
			String query = QueryContants.getAMRRecordsOfSpecificHeaderIdQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, headerID);
			rs = ps.executeQuery();
			while (rs.next()) {
				AMRRecordDetail aMRRecordDetail = new AMRRecordDetail();
				aMRRecordDetail.setHeaderId(rs.getInt("HEADER_ID"));
				aMRRecordDetail.setSeq(rs.getString("SEQ"));
				aMRRecordDetail.setKno(rs.getString("KNO"));
				aMRRecordDetail.setConsumerName(rs.getString("CONSUMER_NAME"));
				aMRRecordDetail.setConsumerAddress(rs
						.getString("CONSUMER_ADDRESS"));
				aMRRecordDetail.setConsumerCategory(rs
						.getString("CONSUMER_CATEGORY"));
				aMRRecordDetail.setUnocDwelUnits(rs
						.getString("UNOC_DWEL_UNITS"));
				aMRRecordDetail.setOcuDwelUnits(rs.getString("OCU_DWEL_UNITS"));
				aMRRecordDetail.setTotlDewlUnits(rs
						.getString("TOTL_DEWL_UNITS"));
				aMRRecordDetail.setNoOfFloor(rs.getString("NO_OF_FLOOR"));
				aMRRecordDetail.setNoOfFuncSitesBedsRooms(rs
						.getString("NO_OF_FUNC_SITES_BEDS_ROOMS"));
				aMRRecordDetail.setMtrNumber(rs.getString("MTR_NUMBER"));
				aMRRecordDetail.setMtrReadDate(rs.getString("MTR_READ_DATE"));
				aMRRecordDetail.setMtrStatusCode(rs
						.getString("MTR_STATUS_CODE"));
				aMRRecordDetail.setMtrRead(rs.getString("MTR_READ"));
				aMRRecordDetail.setCons(rs.getString("CONS"));
				aMRRecordDetail.setEffecNoOfDays(rs
						.getString("EFFEC_NO_OF_DAYS"));
				aMRRecordDetail.setAvgCons(rs.getString("AVG_CONS"));
				aMRRecordDetail.setCurrMtrReadDate(rs
						.getString("CURR_MTR_READ_DATE"));
				aMRRecordDetail.setMtrReadType(rs.getString("MTR_READ_TYPE"));
				aMRRecordDetail.setCurrentAvgCons(rs
						.getString("CURRENT_AVG_CONS"));
				aMRRecordDetail.setCurrentMtrReading(rs
						.getString("CURRENT_MTR_READING"));
				aMRRecordDetail.setCurrentMtrStatusCode(rs
						.getString("CURRENT_MTR_STATUS_CODE"));
				aMRRecordDetail.setCurrentNoOfFloors(rs
						.getString("CURRENT_NO_OF_FLOORS"));
				aMRRecordDetail.setBillRoundId(rs.getString("BILL_ROUND_ID"));
				aMRRecordDetail.setMrkey(rs.getString("MRKEY"));
				aMRRecordDetail.setConsumerStatus(rs
						.getString("CONSUMER_STATUS"));
				aMRRecordDetailList.add(aMRRecordDetail);
				/*Start: Added by Sanjay on 31-08-16 as per jtrac-4553 and Open project Id #1459 */
				AppLog.info(query);
				/*End: Added by Sanjay on 31-08-16 as per jtrac-4553 and Open project Id #1459 */
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return aMRRecordDetailList;
	}

	/**
	 * <p>
	 * This method is used to get MRD Code
	 * </p>
	 * 
	 * @param zone
	 * @param mr
	 * @param areaCd
	 * @return
	 */
	public static String getMrdCode(String zone, String mr, String areaCd) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String validMrkey = null;
		try {
			String query = QueryContants.getMrdCode();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, zone);
			ps.setString(++i, mr);
			ps.setString(++i, areaCd);
			rs = ps.executeQuery();
			while (rs.next()) {
				validMrkey = rs.getString("MRKEY");
				AppLog.info("tagged mrkey" + rs.getString("MRKEY"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return validMrkey;
	}

	/**
	 * <p>
	 * This method is used to get Open Bill Round of MRD
	 * </p>
	 * 
	 * @param headerID
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static Map<String, String> getOpenBillRoundOfMRD(Integer headerID) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String billRound = "";
		String mrkey = "";
		try {
			String query = QueryContants.getOpenBillRoundOfMRDQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, headerID);
			rs = ps.executeQuery();
			while (rs.next()) {
				billRound = rs.getString("BILLROUND");
				mrkey = rs.getString("MRKEY");
				returnMap.put("BILLROUND", billRound);
				returnMap.put("MRKEY", mrkey);
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to save AMR excel data
	 * </p>
	 * 
	 * @param dataArray
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static Integer saveAMRExcelData(String[] dataArray) {
		AppLog.begin();
		int insertedRow = 0;
		AppLog.info("IN MRDFileUploadDAO saveAMRExcelData:::");
		AppLog.info("DataArray:::" + Arrays.toString(dataArray));
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBConnector.getConnection();
			String insertQuery = QueryContants.saveAMRExcelDataQuery();
			AppLog.info("DBConn is closed>>>" + conn.isClosed());
			AppLog.info("\n##############getAMRRecordDetailInsertQuery::"
					+ insertQuery);
			ps = conn.prepareStatement(insertQuery);
			int inOutParameterCount = StringUtils
					.countMatches(insertQuery, "?");
			AppLog.info("inOutParameterCount>>" + inOutParameterCount
					+ ">>dataArray.length>>" + dataArray.length);
			String kno = dataArray[2];
			if (inOutParameterCount == dataArray.length) {
				for (int i = 1; i <= dataArray.length; i++) {
					if (i == 1) {
						ps.setInt(i, Integer.parseInt(dataArray[i - 1]));
					} else {
						ps.setString(i, dataArray[i - 1]);
					}
				}
				insertedRow = ps.executeUpdate();
				AppLog.info("inseertedRow" + insertedRow);
			} else {
				throw new RuntimeException("For kno:" + kno
						+ " No of columns not as per expectation !!");
			}
		} catch (Exception e) {
			AppLog.error(e);
			throw new RuntimeException(e);
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
		return insertedRow;
	}

	/**
	 * <p>
	 * This method is used to update AMR record details
	 * </p>
	 * 
	 * @param kno
	 * @param remarks
	 * @param billRound
	 */
	public static void updateAmrRecordDetails(String kno, String remarks,
			String billRound) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.updateAmrRecordDetailsQuery();
			AppLog.info("updateAMRStagingTableQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, remarks);
			ps.setString(++i, kno);
			ps.setString(++i, billRound);
			updatedRow += ps.executeUpdate();
			AppLog.info("AMRStagingTable updatedRow >>" + updatedRow);

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
	}

	/**
	 * <p>
	 * This method is used to update AMR Staging table
	 * </p>
	 * 
	 * @param headerID
	 * @param remarks
	 * @return
	 */
	public static int updateAMRStagingTable(int headerID, String remarks) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.updateAMRStagingTableQuery();
			AppLog.info("updateAMRStagingTableQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, remarks);
			ps.setInt(++i, headerID);
			updatedRow += ps.executeUpdate();
			AppLog.info("AMRStagingTable updatedRow >>" + updatedRow);

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
		return updatedRow;
	}

	/**
	 * <p>
	 * This method is used to update CM_CONS_PRE_BILL_PROC table
	 * </p>
	 * 
	 * @param kno
	 * @param remarks
	 * @param consPreBillStatId
	 * @param billRound
	 * @param loggedInId
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static int updateConsPreBillProc(String kno, String remarks,
			int consPreBillStatId, String billRound, String loggedInId) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.updateConsPreBillProcQuery();
			AppLog.info("updateConsPreBillProcQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, consPreBillStatId);
			ps.setString(++i, remarks);
			ps.setString(++i, loggedInId);
			ps.setString(++i, kno);
			ps.setString(++i, billRound);
			AppLog.info("Applying Update for KNO " + kno + " For Bill Round "
					+ billRound);
			updatedRow += ps.executeUpdate();
			AppLog.info("updatedRow>>" + updatedRow);

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
		return updatedRow;
	}

	/**
	 * <p>
	 * This method is used to get Area Code
	 * </p>
	 * 
	 * @param areaDesc
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public String getAreaCode(String areaDesc) {
		AppLog.begin();
		String zroCd = "";
		AppLog.info("IN MRDFileUploadDAO getNextHeaderId:::");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getAREACodeQuery();
			AppLog.info("\n##############getgetNextHeaderIdQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, areaDesc);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroCd = rs.getString("AREACD");
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
		return zroCd;
	}

	/**
	 * <p>
	 * This method is used to get Current Version
	 * </p>
	 * 
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public String getCurrentVersion() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String currentVersion = "";
		try {
			String query = QueryContants.getCurrentVersionQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				currentVersion = rs.getString("VERSION_NO");
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return currentVersion;
	}

	/**
	 * <p>
	 * This method is used to get HHD ID of User's
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public String getHHDIdOfUser(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String hhdId = "";
		try {
			String query = QueryContants.getHHDIdOfUserQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				hhdId = rs.getString("HHDID");
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return hhdId;
	}

	/**
	 * <p>
	 * This method is used to get next Header ID
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public int getNextHeaderId() {
		AppLog.begin();
		int nextHeaderId = 0;
		AppLog.info("IN MRDFileUploadDAO getNextHeaderId:::");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getNextHeaderIdQuery();
			AppLog.info("\n##############getgetNextHeaderIdQuery::" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				nextHeaderId = rs.getInt("NXTID");
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
		return nextHeaderId;
	}

	/**
	 * <p>
	 * This method is used to get user details
	 * </p>
	 * 
	 * @param hhdID
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public Map<String, String> getUserDetails(String hhdID) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			String query = QueryContants.getUserDetailsQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, hhdID);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put("USER_PASSWORD", rs.getString("USER_PASSWORD"));
				returnMap.put("CURRENT_DATE", rs.getString("CURRENT_DATE"));
				returnMap.put("VALID_SERVICE_LIST", rs
						.getString("VALID_SERVICE_LIST"));
				returnMap.put("VALID_IP_LIST", rs.getString("VALID_IP_LIST"));
				returnMap.put("VALID_HOST_LIST", rs
						.getString("VALID_HOST_LIST"));
				returnMap.put("VALID_ACCESS_FROM", rs
						.getString("VALID_ACCESS_FROM"));
				returnMap.put("VALID_ACCESS_TO", rs
						.getString("VALID_ACCESS_TO"));
				returnMap.put("IS_FORBIDDEN_TIME", rs
						.getString("IS_FORBIDDEN_TIME"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to get ZRO Code
	 * </p>
	 * 
	 * @param zRODesc
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public String getZROCode(String zRODesc) {
		AppLog.begin();
		String zroCd = "";
		AppLog.info("IN MRDFileUploadDAO getNextHeaderId:::");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getZROCodeQuery();
			AppLog.info("\n##############getgetNextHeaderIdQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, zRODesc);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroCd = rs.getString("ZRO");
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
		return zroCd;
	}

	/**
	 * <p>
	 * This method is used to insert Header Data
	 * </p>
	 * 
	 * @param aMRHeaderDetails
	 * @return
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public int insertHeaderData(AMRHeaderDetails aMRHeaderDetails) {
		AppLog.begin();
		int insertedRow = 0;
		AppLog.info("IN MRDFileUploadDAO insertHeaderData:::");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			String insertQuery = QueryContants
					.insertHeaderDataQuery(aMRHeaderDetails);
			AppLog.info("\n##############getAMRHeaderDataInsertQuery::"
					+ insertQuery);
			int i = 0;
			ps = conn.prepareStatement(insertQuery);
			if (null != aMRHeaderDetails && 0 != aMRHeaderDetails.getHeaderID()) {
				ps.setInt(++i, aMRHeaderDetails.getHeaderID());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getUcmFilePath()) {
				ps.setString(++i, aMRHeaderDetails.getUcmFilePath());

			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getMeterReaderName()) {
				ps.setString(++i, aMRHeaderDetails.getMeterReaderName());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getMeterReadDiaryCode()) {
				ps.setString(++i, aMRHeaderDetails.getMeterReadDiaryCode());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getServiceCycle()) {
				ps.setString(++i, aMRHeaderDetails.getServiceCycle());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getStartDate()) {
				ps.setString(++i, aMRHeaderDetails.getStartDate());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getEndDate()) {
				ps.setString(++i, aMRHeaderDetails.getEndDate());
			}
			if (null != aMRHeaderDetails && null != aMRHeaderDetails.getRoute()) {
				ps.setString(++i, aMRHeaderDetails.getRoute());
			}
			if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZroCd()) {
				ps.setString(++i, aMRHeaderDetails.getZroCd());

			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getZroDesc()) {
				ps.setString(++i, aMRHeaderDetails.getZroDesc());

			}
			if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZone()) {
				ps.setString(++i, aMRHeaderDetails.getZone());
			}
			if (null != aMRHeaderDetails && null != aMRHeaderDetails.getMr()) {
				ps.setString(++i, aMRHeaderDetails.getMr());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getAreaCd()) {
				ps.setString(++i, aMRHeaderDetails.getAreaCd());
			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getAreaDesc()) {
				ps.setString(++i, aMRHeaderDetails.getAreaDesc());

			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getCreatedBy()) {
				ps.setString(++i, aMRHeaderDetails.getCreatedBy());

			}
			if (null != aMRHeaderDetails
					&& null != aMRHeaderDetails.getLastUpdatedBy()) {
				ps.setString(++i, aMRHeaderDetails.getLastUpdatedBy());
			}
			insertedRow = ps.executeUpdate();
			AppLog.info("inseertedRow" + insertedRow);

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
		return insertedRow;
	}

}
