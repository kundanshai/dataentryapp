/************************************************************************************************************
 * @(#) MeterReplacementReviewDAO.java   06 May 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.interfaces.MeterReplacementInterface;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.MeterReplacementReviewDetails;
import com.tcs.djb.model.MeterReplacementUploadReply;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for Meter Replacement Review Screen.
 * </p>
 * 
 * @author Tuhin Jana.
 * @history 06-05-2013 Matiur Rahman Shifted query of
 *          getMeterReplacementDetailList &
 *          getTotalCountOfMeterReplacementDetailRecords to Query Constant.
 * @history 06-05-2013 Matiur Rahman Modified query of
 *          freezeMeterReplacementPreProcessing
 * 
 * @history 03-01-2014 Rajib hazarika Modified query of
 *          #getTotalCountOfMeterReplacementDetailRecords,
 *          #getMeterReplacementDetailList
 * @history 04-02-2014 Matiur Rahman Added {@link #updateProcessingErrorMessage}
 *          ,{@link #retrievProcessingErrorMessage} as per JTrac ID DJB-2024
 *          updated on 04-02-2014.
 * @history 14-02-2014 Matiur Rahman Added feature to update the who columns and
 *          new method {@link #rollbackFreezeMeterReplacementPreProcessing} as
 *          per JTrac ID#2024.
 * @history 14-02-2014 Matiur Rahman Added new method
 *          {@link #updateMeterReplacementProcessingDetails} to update the who
 *          columns for Processing as per JTrac ID#2024.
 * 
 */
public class MeterReplacementReviewDAO implements MeterReplacementInterface {
	/**
	 * <p>
	 * This method is used to freeze Meter Replacement records from Meter Replacement Review Screen.
	 * </p>
	 * 
	 * @history 14-02-2014 Matiur Rahman Added feature to update the who columns
	 *          as per JTrac ID#2024.
	 * @param inputMap
	 * @return
	 */
	public static int freezeMeterReplacement(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			String knoList = (String) inputMap.get("knoList");
			String billRoundList = (String) inputMap.get("billRoundList");
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			String userId = (String) inputMap.get("userId");
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			StringBuffer querySB = new StringBuffer();
			querySB.append(" UPDATE CM_CONS_PRE_BILL_PROC ");
			querySB.append(" SET CONS_PRE_BILL_STAT_ID=65 ");
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			querySB.append(" ,LAST_UPDT_BY=?,LAST_UPDT_DTTM=SYSTIMESTAMP ");
			querySB.append(" ,DATA_FROZEN_BY=?,DATA_FROZEN_DTTM=SYSTIMESTAMP ");
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			querySB.append(" WHERE CONS_PRE_BILL_STAT_ID =64 ");
			querySB.append(" AND ACCT_ID = ? ");
			querySB.append(" AND BILL_ROUND_ID = ? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			ps.setString(++i, userId);
			ps.setString(++i, userId);
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			// if (null != knoList && !"".equalsIgnoreCase(knoList.trim())) {
			ps.setString(++i, knoList);
			// }
			// if (null != billRoundList &&
			// !"".equalsIgnoreCase(billRoundList.trim())) {
			ps.setString(++i, billRoundList);
			// }
			totalRecords = ps.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (IOException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (SystemException e) {
			// e.printStackTrace();
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
		return totalRecords;
	}

	/**
	 * <p>
	 * Pre Processing of records sent for freeze Meter Replacement records from
	 * Meter Replacement Review Screen.
	 * </p>
	 * 
	 * @history 14-02-2014 Matiur Rahman Added feature to update the who columns
	 *          as per JTrac ID#2024.
	 * 
	 * @param inputMap
	 * @return
	 */
	public static int freezeMeterReplacementPreProcessing(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			String knoList = (String) inputMap.get("knoList");
			String billRoundList = (String) inputMap.get("billRoundList");
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			String userId = (String) inputMap.get("userId");
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			StringBuffer querySB = new StringBuffer();
			/** Start:06-05-2013 Commented by Matiur Rahman */
			// querySB.append(" UPDATE CM_CONS_PRE_BILL_PROC ");
			// querySB.append(" SET CONS_PRE_BILL_STAT_ID=64 ");
			// querySB.append(" WHERE CONS_PRE_BILL_STAT_ID =30 ");
			// querySB.append(" AND ACCT_ID = ? ");
			// querySB.append(" AND BILL_ROUND_ID = ? ");
			/** End:06-05-2013 Commented by Matiur Rahman */
			/** Start:06-05-2013 Added by Matiur Rahman */
			querySB.append(" UPDATE CM_CONS_PRE_BILL_PROC T");
			querySB.append(" SET T.CONS_PRE_BILL_STAT_ID=64 ");
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			querySB.append(" ,T.LAST_UPDT_BY=?,T.LAST_UPDT_DTTM=SYSTIMESTAMP ");
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			querySB.append(" WHERE T.CONS_PRE_BILL_STAT_ID =30 ");
			querySB
					.append(" AND T.ACCT_ID = (SELECT M.ACCT_ID FROM CM_MTR_RPLC_STAGE M  WHERE M.MTR_RPLC_STAGE_ID = 350 AND M.ACCT_ID= T.ACCT_ID AND M.BILL_ROUND_ID = T.BILL_ROUND_ID ) ");
			querySB.append(" AND T.ACCT_ID = ? ");
			querySB.append(" AND T.BILL_ROUND_ID = ? ");
			/** End:06-05-2013 Commented by Matiur Rahman */
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			/*
			 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			ps.setString(++i, userId);
			/*
			 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to
			 * update the who columns as per JTrac ID#2024.
			 */
			// if (null != knoList && !"".equalsIgnoreCase(knoList.trim())) {
			ps.setString(++i, knoList);
			// }
			// if (null != billRoundList &&
			// !"".equalsIgnoreCase(billRoundList.trim())) {
			ps.setString(++i, billRoundList);
			// }
			totalRecords = ps.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (IOException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (SystemException e) {
			// e.printStackTrace();
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
		return totalRecords;
	}

	/**
	 * <p>
	 * This method is used to get Meter Replacement Detail List for Meter Replacement Review Screen.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static List<MeterReplacementReviewDetails> getMeterReplacementDetailList(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MeterReplacementReviewDetails> meterReplacementReviewDetails = new ArrayList<MeterReplacementReviewDetails>();
		try {
			String kno = (String) inputMap.get("kno");
			String mrKeyList = (String) inputMap.get("mrKeyList");
			String zone = (String) inputMap.get("zone");
			String mrNo = (String) inputMap.get("mrNo");
			String area = (String) inputMap.get("area");
			String searchFor = (String) inputMap.get("searchFor");
			String pageNo = (String) inputMap.get("pageNo");
			String recordPerPage = (String) inputMap.get("recordPerPage");
			// Chnaged by Rajib as per Jtrac DJB-2024 on 03-01-2014
			String vendorName = null;
			if (null != inputMap.get("vendorName")) {
				vendorName = (String) inputMap.get("vendorName");
			}
			// Chnage End for Jtrac DJB-2024 on 03-01-2014
			MeterReplacementReviewDetails meterReplacementDetail = null;
			/** Query shifted to Query Constant */
			// StringBuffer querySB = new StringBuffer();
			// /** Start:For Pagination part 1 */
			// querySB.append(" SELECT T2.* ");
			// querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
			// /** End:For Pagination part 1 */
			// querySB.append(" SELECT T.MRKEY,");
			// querySB.append(" T.SVC_CYC_RTE_SEQ, ");
			// querySB.append(" T.ACCT_ID, ");
			// querySB.append(" T.CONSUMER_NAME, ");
			// querySB.append(" T.WCNO, ");
			// querySB.append(" T.SA_TYPE_CD, ");
			// querySB.append(" T.BILL_ROUND_ID, ");
			// querySB.append(" M.MTR_RPLC_STAGE_ID ,");
			// querySB
			// .append("to_char(T.BILL_GEN_DATE,'dd/mm/yyyy') BILL_GEN_DATE ,");
			// // querySB.append(" T.BILL_GEN_DATE, ");
			// querySB.append(" T.READER_REM_CD, ");
			// querySB.append(" T.REG_READING, ");
			// querySB.append(" T.NEW_AVG_READ, ");
			// querySB.append(" T.NEW_NO_OF_FLOORS ");
			// querySB.append(" FROM  ");
			// querySB.append(" CM_CONS_PRE_BILL_PROC  T, ");
			// querySB.append(" DJB_ZN_MR_AR_MRD       Z, ");
			// querySB.append(" CM_MRD_PROCESSING_STAT P, ");
			// querySB.append(" CM_MTR_RPLC_STAGE M ");
			// querySB.append(" WHERE 1=1 ");
			// querySB.append(" AND T.MRKEY = Z.MRD_CD ");
			// querySB.append(" AND T.MRKEY = P.MRKEY ");
			// querySB.append(" AND T.BILL_ROUND_ID = P.BILL_ROUND_ID ");
			// querySB.append(" AND Z.MRD_CD = P.MRKEY ");
			// querySB.append(" AND P.MRD_STATS_ID = 10 ");
			// querySB.append(" AND T.ACCT_ID = M.ACCT_ID");
			// querySB.append(" AND T.BILL_ROUND_ID = M.BILL_ROUND_ID ");
			// if (null != searchFor && "Frozen".equalsIgnoreCase(searchFor)) {
			// querySB.append(" AND T.CONS_PRE_BILL_STAT_ID in (64,65) ");
			// } else {
			// querySB.append(" AND T.CONS_PRE_BILL_STAT_ID = 30 ");
			// }
			// if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
			// querySB.append(" AND Z.SUBZONE_CD = ? ");
			// }
			// if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
			// querySB.append(" AND Z.MR_CD = ? ");
			// }
			// if (null != area && !"".equalsIgnoreCase(area.trim())) {
			// querySB.append(" AND Z.AREA_CD = ? ");
			// }
			// if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim()))
			// {
			// querySB.append(" AND T.MRKEY IN ( " + mrKeyList + " )");
			// }
			// if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
			// querySB.append(" AND T.ACCT_ID = ? ");
			// }
			// querySB
			// .append(" ORDER BY TO_NUMBER(T.MRKEY), TO_NUMBER(T.SVC_CYC_RTE_SEQ) ");
			// /** Start:For Pagination part 2 */
			// querySB.append(" ) T1 ");
			// querySB.append(" WHERE ROWNUM < ((" + pageNo + " * "
			// + recordPerPage + ") + 1)) T2 ");
			// querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
			// + recordPerPage + ") + 1)");
			// /** End:For Pagination part 2 */
			String query = QueryContants.getMeterReplacementDetailListQuery(
					searchFor, zone, mrNo, area, mrKeyList, kno, recordPerPage,
					pageNo, vendorName);
			conn = DBConnector.getConnection();
			// System.out.println("Query for meterreplacementDetails:"+query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
				ps.setString(++i, zone);
			}
			if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
				ps.setString(++i, mrNo);
			}
			if (null != area && !"".equalsIgnoreCase(area.trim())) {
				ps.setString(++i, area);
			}
			// if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim()))
			// {
			// ps.setString(++i, mrKeyList);
			// }
			if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
				ps.setString(++i, kno);
			}

			rs = ps.executeQuery();
			AppLog.info("Query:: " + query);
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementReviewDetails();

				meterReplacementDetail.setSeqNo(rs.getString("SEQ_NO"));
				meterReplacementDetail.setServiceSeqNo(rs
						.getString("SVC_CYC_RTE_SEQ"));
				meterReplacementDetail.setKno(rs.getString("ACCT_ID"));
				meterReplacementDetail.setZone(rs.getString("SUBZONE_CD"));
				// meterReplacementDetail.setBillRound(rs
				// .getString("BILL_ROUND_ID"));
				// meterReplacementDetail.setZone(rs.getString("subzone_cd")
				// .trim());
				// meterReplacementDetail.setMrNo(rs.getString("mr_cd"));
				// meterReplacementDetail.setArea(rs.getString("area_cd"));
				meterReplacementDetail.setName(rs.getString("CONSUMER_NAME")
						.trim());
				meterReplacementDetail.setWcNo(rs.getString("WCNO"));
				meterReplacementDetail.setSaType(rs.getString("SA_TYPE_CD")
						.trim());
				String consumerType = rs.getString("SA_TYPE_CD");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setCurrentMeterReadDate(rs
						.getString("BILL_GEN_DATE"));
				meterReplacementDetail.setMeterReplaceStageID(rs
						.getString("MTR_RPLC_STAGE_ID"));
				meterReplacementDetail.setCurrentMeterRegisterRead(rs
						.getString("REG_READING"));
				meterReplacementDetail.setCurrentMeterReadRemarkCode(rs
						.getString("READER_REM_CD"));
				meterReplacementDetail.setNewAverageConsumption(rs
						.getString("NEW_AVG_READ"));
				meterReplacementDetail.setNoOfFloors(rs
						.getString("NEW_NO_OF_FLOORS"));
				meterReplacementDetail.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				// added by Raji as per Jtrac DJB-2024
				meterReplacementDetail.setLastErrorMsg((null != rs
						.getString("ERROR_STATUS") ? rs
						.getString("ERROR_STATUS") : "")
						+ (null != rs.getString("ERROR_MSG") ? (" - " + rs
								.getString("ERROR_MSG")) : ""));
				meterReplacementDetail.setPreBillStatID(rs
						.getString("CONS_PRE_BILL_STAT_ID"));
				// change by Rajib finish
				meterReplacementReviewDetails.add(meterReplacementDetail);
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

		return meterReplacementReviewDetails;

	}

	/**
	 * <p>
	 * This method is used to retrieve MRKey List corresponding to userId for Meter Replacement Review
	 * Screen checking the count in the MRKey access table.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 17-12-2013
	 * @param UserId
	 * @return List Of MrKeys corresponding to userId
	 */
	public static int getMRKeyCountForUser(String userId, String mrKey) {
		AppLog.begin();
		int mrKeyCount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = QueryContants.getListOfMrKeyQuery(userId, mrKey);
			conn = DBConnector.getConnection();
			AppLog.info("query::" + query);
			// System.out.println("query::" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				mrKeyCount = rs.getInt("MRKEYCOUNT");
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
		return mrKeyCount;
	}

	/**
	 * <p>
	 * This method is used to get No Of Successful Records Freezed.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static int getNoOfSuccessfulRecordsFreezed(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String knoList = (String) inputMap.get("knoList");
			String billRoundList = (String) inputMap.get("billRoundList");
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT count(*) total_records ");
			querySB.append(" FROM  ");
			querySB.append(" CM_CONS_PRE_BILL_PROC  T, ");
			querySB.append(" WHERE 1=1 ");
			querySB.append(" CONS_PRE_BILL_STAT_ID =65 ");
			querySB.append(" AND ACCT_ID IN (?) ");
			querySB.append(" AND BILL_ROUND_ID IN (?) ");

			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// if (null != knoList && !"".equalsIgnoreCase(knoList.trim())) {
			ps.setString(++i, knoList);
			// }
			// if (null != billRoundList &&
			// !"".equalsIgnoreCase(billRoundList.trim())) {
			ps.setString(++i, billRoundList);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("total_records");
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
	 * This method is used to get Total Count Of Meter Replacement Detail Records for Meter Replacement
	 * Review Screen.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static int getTotalCountOfMeterReplacementDetailRecords(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String kno = (String) inputMap.get("kno");
			String mrKeyList = (String) inputMap.get("mrKeyList");
			String zone = (String) inputMap.get("zone");
			String mrNo = (String) inputMap.get("mrNo");
			String area = (String) inputMap.get("area");
			String searchFor = (String) inputMap.get("searchFor");
			// Chnaged by Rajib as per Jtrac DJB-2024 on 03-01-2014
			String vendorName = null;
			if (null != inputMap.get("vendorName")) {
				vendorName = (String) inputMap.get("vendorName");
			}
			// System.out.println("Company name:"+vendorName);
			// Chnage End for Jtrac DJB-2024 on 03-01-2014
			/** Query shifted to Query Constant */
			// StringBuffer querySB = new StringBuffer();
			// querySB.append(" SELECT count(*) total_records ");
			// querySB.append(" FROM  ");
			// querySB.append(" CM_CONS_PRE_BILL_PROC  T, ");
			// querySB.append(" DJB_ZN_MR_AR_MRD       Z, ");
			// querySB.append(" CM_MRD_PROCESSING_STAT P ");
			// querySB.append(" WHERE 1=1 ");
			// querySB.append(" AND T.MRKEY = Z.MRD_CD ");
			// querySB.append(" AND T.MRKEY = P.MRKEY ");
			// querySB.append(" AND T.BILL_ROUND_ID = P.BILL_ROUND_ID ");
			// querySB.append(" AND Z.MRD_CD = P.MRKEY ");
			// querySB.append(" AND P.MRD_STATS_ID = 10 ");
			// if (null != searchFor && "Frozen".equalsIgnoreCase(searchFor)) {
			// querySB.append(" AND T.CONS_PRE_BILL_STAT_ID in (64,65) ");
			// } else {
			// querySB.append(" AND T.CONS_PRE_BILL_STAT_ID = 30 ");
			// }
			// if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
			// querySB.append(" AND Z.SUBZONE_CD = ? ");
			// }
			// if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
			// querySB.append(" AND Z.MR_CD = ? ");
			// }
			// if (null != area && !"".equalsIgnoreCase(area.trim())) {
			// querySB.append(" AND Z.AREA_CD = ? ");
			// }
			// if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim()))
			// {
			// querySB.append(" AND T.MRKEY IN ( " + mrKeyList + " )");
			// }
			// if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
			// querySB.append(" AND T.ACCT_ID = ? ");
			// }
			String query = QueryContants
					.getTotalCountOfMeterReplacementDetailRecordsQuery(
							searchFor, zone, mrNo, area, mrKeyList, kno,
							vendorName);
			// System.out.println("Query for getTotalCountOfMeterReplacementDetailRecordsQuery: "+query);
			AppLog.info("Search Parameter::KNO" + kno + "::MRKey List::"
					+ mrKeyList + "::Zone::" + zone + "::MR No::" + mrNo
					+ "::Area::" + area + "::Search For::" + searchFor
					+ "::vendorName::" + vendorName + "\nQuery :: " + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
				ps.setString(++i, zone);
			}
			if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
				ps.setString(++i, mrNo);
			}
			if (null != area && !"".equalsIgnoreCase(area.trim())) {
				ps.setString(++i, area);
			}
			// if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim()))
			// {
			// ps.setString(++i, mrKeyList);
			// }
			if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
				ps.setString(++i, kno);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("total_records");
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
	 * This method is used to retrieve Processing Error Message from <code>CM_MTR_RPLC_STAGE</code>
	 * returned by the CC&B service for Meter replacement process.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 04-02-2014
	 * @param inputMap
	 * @return number of rows updated
	 */
	public static Map<String, String> retrievProcessingErrorMessage(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			String kno = (String) inputMap.get("kno");
			String billRoundId = (String) inputMap.get("billRoundId");
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT ");
			querySB.append(" T.ERROR_MSG ");
			querySB.append(" ,ERROR_STATUS ");
			querySB.append(" FROM CM_MTR_RPLC_STAGE T ");
			querySB.append(" WHERE T.ACCT_ID = ? ");
			querySB.append(" AND T.BILL_ROUND_ID = ? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, kno);
			ps.setString(++i, billRoundId);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put("errorMsg", rs.getString("ERROR_MSG"));
				returnMap.put("errorStatus", rs.getString("ERROR_STATUS"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (SystemException e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != conn && !conn.isClosed()) {
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
	 * Roll back Pre Processing status of records sent for freeze Meter
	 * Replacement records from Meter Replacement Review Screen in case any
	 * exception occurred.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 14-02-2014
	 * @param inputMap
	 * @return
	 */
	public static int rollbackFreezeMeterReplacementPreProcessing(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			String kno = (String) inputMap.get("kno");
			String billRoundId = (String) inputMap.get("billRoundId");
			String userId = (String) inputMap.get("userId");
			StringBuffer querySB = new StringBuffer();
			querySB.append(" UPDATE CM_CONS_PRE_BILL_PROC T");
			querySB.append(" SET T.CONS_PRE_BILL_STAT_ID=30 ");
			querySB.append(" ,T.LAST_UPDT_BY=?,T.LAST_UPDT_DTTM=SYSTIMESTAMP ");
			querySB.append(" WHERE T.CONS_PRE_BILL_STAT_ID =64 ");
			querySB
					.append(" AND T.ACCT_ID = (SELECT M.ACCT_ID FROM CM_MTR_RPLC_STAGE M  WHERE M.MTR_RPLC_STAGE_ID = 350 AND M.ACCT_ID= T.ACCT_ID AND M.BILL_ROUND_ID = T.BILL_ROUND_ID ) ");
			querySB.append(" AND T.ACCT_ID = ? ");
			querySB.append(" AND T.BILL_ROUND_ID = ? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, kno);
			ps.setString(++i, billRoundId);
			totalRecords = ps.executeUpdate();

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (SystemException e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != conn && !conn.isClosed()) {
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
	 * This method is used to update Processing Details to <code>CM_MTR_RPLC_STAGE</code> while doing
	 * Meter replacement process.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-03-2014
	 * @param inputMap
	 * @return number of rows updated
	 */
	public static int updateMeterReplacementProcessingDetails(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			String kno = (String) inputMap.get("kno");
			String billRoundId = (String) inputMap.get("billRoundId");
			String userId = (String) inputMap.get("userId");
			String query = QueryContants
					.updateMeterReplacementProcessingDetailsQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, userId);
			ps.setString(++i, kno);
			ps.setString(++i, billRoundId);
			totalRecords = ps.executeUpdate();
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (SystemException e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != conn && !conn.isClosed()) {
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
	 * This method is used to update Processing Error Message in <code>CM_MTR_RPLC_STAGE</code>
	 * returned by the CC&B service for Meter replacement process. The message
	 * is kept in property file.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 04-02-2014
	 * @param inputMap
	 * @return number of rows updated
	 */
	public static int updateProcessingErrorMessage(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int totalRecords = 0;
		try {
			String kno = (String) inputMap.get("kno");
			String billRoundId = (String) inputMap.get("billRoundId");
			String errorMessage = (String) inputMap.get("errorMessage");
			String errorStatusCode = (String) inputMap.get("errorStatusCode");
			String userId = (String) inputMap.get("userId");
			StringBuffer querySB = new StringBuffer();
			querySB.append(" UPDATE CM_MTR_RPLC_STAGE T");
			querySB.append(" SET T.ERROR_MSG=? ");
			querySB.append(" ,T.ERROR_STATUS=? ");
			querySB.append(" ,T.LAST_UPDT_DTTM=SYSTIMESTAMP,T.LAST_UPDT_BY=? ");
			querySB.append(" WHERE T.ACCT_ID = ? ");
			querySB.append(" AND T.BILL_ROUND_ID = ? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, errorMessage);
			ps.setString(++i, errorStatusCode);
			ps.setString(++i, userId);
			ps.setString(++i, kno);
			ps.setString(++i, billRoundId);
			totalRecords = ps.executeUpdate();
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (SystemException e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return totalRecords;
	}

	/* (non-Javadoc)
	 * @see com.tcs.djb.interfaces.MeterReplacementInterface#meterReplacementUpload(com.tcs.djb.model.MeterReplacementDetail)
	 */
	@Override
	public MeterReplacementUploadReply meterReplacementUpload(
			MeterReplacementDetail meterReplacementDetail) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tcs.djb.interfaces.MeterReplacementInterface#resetMeterReplacementDetail(com.tcs.djb.model.MeterReplacementDetail)
	 */
	@Override
	public boolean resetMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		return false;
	}
}
