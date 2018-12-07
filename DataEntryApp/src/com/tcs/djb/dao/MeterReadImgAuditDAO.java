/************************************************************************************************************
 * @(#) MeterReadImgAuditDAO.java   09 Oct 2014
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.ImageAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Meter Read Image Audit Screen.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 11-01-2016
 * 
 * @history Sanjay Kumar Das on 31-08-2016 modified the logic for fetching the
 *          IMG_LNK from UCM as per JTrac DJB-4547, Open project Id #1462
 * @history Madhuri Added on 25th-Jul-2017 to fetch list of normal bill rounds
 *          as per JTrac Android-389.
 * @history Madhuri Added on 1st-Sept-2017 to fetch list of normal bill rounds
 *          as per JTrac Android-388.
 */
public class MeterReadImgAuditDAO {

	/**
	 * <p>
	 * This method is used to get Bill Round Start and End Date
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return bill round start date and end date
	 * @author Madhuri
	 * @since 01-09-2017
	 */
	public static Map<String, String> getBillRoundStartAndEndDate(
			String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants
					.getBillRoundStartAndEndDateQuery(billRound);
			AppLog.info("getBillRoundStartAndEndDateQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("Bill_Round_Start_Date :: "
						+ rs.getString("START_DATE")
						+ "Bill_Round_End_Date :: " + rs.getString("END_DATE"));

				retrunMap.put("billRoundStartDate", rs.getString("START_DATE")
						.trim());
				retrunMap.put("billRoundEndDate", rs.getString("END_DATE")
						.trim());

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

	/**
	 * <p>
	 * This Method returns ArrayList of MeterReadImgAuditDetails based on the
	 * imageAuditSearchCriteria
	 * </p>
	 * 
	 * @author Atanu Mondal (Tata Consultancy Services)
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static ArrayList<MeterReadImgAuditDetails> getConsumerDetailsListForImgAudit(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {

		AppLog.begin();
		/*
		 * Start: Added by Sanjay on 31-08-16 as per jtrac -4547 and
		 * openproject-1462
		 */
		String ucmAccessPath = DJBConstants.UCM_LOAD_BALANCER_PATH;
		AppLog.info("Image UCM Access Path>>>>>>>>>>>>>" + ucmAccessPath);
		/*
		 * End: Added by Sanjay on 31-08-16 as per jtrac -4547 and
		 * openproject-1462
		 */
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		MeterReadImgAuditDetails meterReadImgAuditDetails = null;
		List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList = new ArrayList<MeterReadImgAuditDetails>();
		try {
			String query = QueryContants
					.getRecordDetailsListForImgAudit(imageAuditSearchCriteria);
			AppLog.info("getRecordDetailsListForImgAuditMainQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != imageAuditSearchCriteria.getSearchZone()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchZone())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchZone());
			}
			if (null != imageAuditSearchCriteria.getSearchMRNo()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchMRNo())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchMRNo());
			}
			if (null != imageAuditSearchCriteria.getSearchArea()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchArea())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchArea());
			}
			if (null != imageAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchKno())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchKno());
			}
			if (null != imageAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchBillRound())) {
				ps
						.setString(++i, imageAuditSearchCriteria
								.getSearchBillRound());
			}
			if (null != imageAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchZROCode());
			}
			if (null != imageAuditSearchCriteria.getSearchAuditStatus()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchAuditStatus())
					&& !"ALL".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchAuditStatus())) {
				if ("AUDITED".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchAuditStatus())) {
					ps.setString(++i, "Y");
					// System.out.println("Setting IMG_ADT_FLG=Y");
				} else {
					ps.setString(++i, "N");
					// System.out.println("Setting IMG_ADT_FLG=N");
				}
			}

			// Start :- Added By Madhuri on 30th Aug 2017 as per Jtrac
			// Android-388
			if (null != imageAuditSearchCriteria.getsearchMeterRdrEmpId()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getsearchMeterRdrEmpId())
					&& !"NA".equalsIgnoreCase(imageAuditSearchCriteria
							.getsearchMeterRdrEmpId())) {

				ps.setString(++i, imageAuditSearchCriteria
						.getsearchMeterRdrEmpId());
			}
			if (null != imageAuditSearchCriteria.getSearchdBillGenSource()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchdBillGenSource())) {
				ps.setString(++i, imageAuditSearchCriteria
						.getSearchdBillGenSource());
			}
			// End :- Added By Madhuri on 30th Aug 2017 as per Jtrac Android-388

			rs = ps.executeQuery();
			while (rs.next()) {
				meterReadImgAuditDetails = new MeterReadImgAuditDetails();
				meterReadImgAuditDetails.setSeqNo(rs.getString("SEQ_NO"));
				meterReadImgAuditDetails.setZone(rs.getString("ZN"));
				meterReadImgAuditDetails.setMr(rs.getString("MR_NO"));
				meterReadImgAuditDetails.setArea(rs.getString("AREA"));
				meterReadImgAuditDetails.setKno(rs.getString("KNO"));
				meterReadImgAuditDetails.setBillDt(rs.getString("BILL_DT"));
				meterReadImgAuditDetails.setBillAmt(rs.getString("BILL_AMT"));
				meterReadImgAuditDetails.setZroLocation(rs
						.getString("ZRO_LOCATION"));
				meterReadImgAuditDetails.setMtrRead(rs.getString("MTR_READ"));
				// Start :- Added By Madhuri on 30th Aug 2017 as per Jtrac
				// Android-388
				meterReadImgAuditDetails.setBillBasis(rs
						.getString("BILL_BASIS"));
				meterReadImgAuditDetails.setBillGenSource(rs
						.getString("BILL_GEN_BY"));
				meterReadImgAuditDetails.setMtrRdrNameEmpId(rs
						.getString("MTR_RDR_NAME_ID"));
				meterReadImgAuditDetails.setMtrRdStatus(rs
						.getString("MTR_READ_STATUS"));
				// End :- Added By Madhuri on 30th Aug 2017 as per Jtrac
				// Android-388
				/*
				 * Start: Added by Sanjay on 31-08-16 as per jtrac -4547 and
				 * openproject-1462
				 */
				if (null != rs.getString("IMG_LNK")
						&& !"".equalsIgnoreCase(rs.getString("IMG_LNK").trim())
						&& null != ucmAccessPath
						&& !"".equalsIgnoreCase(ucmAccessPath.trim())) {
					meterReadImgAuditDetails.setImgLnk(ucmAccessPath
							+ rs.getString("IMG_LNK"));
				}
				/*
				 * End: Added by Sanjay on 31-08-16 as per jtrac -4547 and
				 * openproject-1462
				 */
				meterReadImgAuditDetails.setImgAuditStatus(rs
						.getString("IMG_AUDIT_STATUS"));
				meterReadImgAuditDetails.setImgAuditFailReason(rs

				.getString("AUDIT_FAIL_REASON"));
				meterReadImgAuditDetails.setImgAuditRemrk(rs
						.getString("AUDIT_REMRK"));
				meterReadImgAuditDetails.setImgAuditFlg(rs
						.getString("AUDIT_FLG"));

				meterReadImgAuditDetailsList.add(meterReadImgAuditDetails);
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
		return (ArrayList<MeterReadImgAuditDetails>) meterReadImgAuditDetailsList;

	}

	/**
	 * <p>
	 * This method is used to get Meter Reader Name
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return return meter reader name with emp id
	 * @author Madhuri
	 * @since 09-03-2016
	 */
	public static ArrayList<String> getGetMeterReadName() {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> meterReaderNameList = new ArrayList<String>();
		try {
			String query = QueryContants.getMeterReaderNameForAuditQuery();
			AppLog.info("getMeterReaderListQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String meterReaderName = rs.getString("MTR_RDR_NAME_ID");
				meterReaderNameList.add(meterReaderName);
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
		return (ArrayList<String>) meterReaderNameList;
	}

	/**
	 * <p>
	 * This Method returns Currently Closed Normal Bill Round
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @return
	 */
	public static String getPreviousBillRound() {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String previousBillRound = "";
		try {
			String query = QueryContants.getPreviousBillRoundQuery();
			AppLog.info("getPreviousBillRoundQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				previousBillRound = rs.getString("PREV_BILL_ROUND");
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
		return previousBillRound;
	}

	/**
	 * <p>
	 * This Method returns Currently Closed Normal Bill Round
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @return
	 */
	public static Map<String, String> getPreviousBillRounds() {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getBillRoundsQuery();
			AppLog.info("getPreviousBillRoundQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (null != rs.getString("BILL_ROUNDS")) {
					retrunMap.put(rs.getString("BILL_ROUNDS").trim(), rs
							.getString("BILL_ROUNDS"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This Method returns number of records based on the
	 * imageAuditSearchCriteria
	 * </p>
	 * 
	 * @author Atanu Mondal (Tata Consultancy Services)
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static int getTotalCountOfAuditDetailsList(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfAuditDetailsListQuery(imageAuditSearchCriteria);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != imageAuditSearchCriteria.getSearchZone()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchZone())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchZone());
			}
			if (null != imageAuditSearchCriteria.getSearchMRNo()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchMRNo())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchMRNo());
			}
			if (null != imageAuditSearchCriteria.getSearchArea()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchArea())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchArea());
			}
			if (null != imageAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchKno())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchKno());
			}
			if (null != imageAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchBillRound())) {
				ps
						.setString(++i, imageAuditSearchCriteria
								.getSearchBillRound());
			}
			if (null != imageAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, imageAuditSearchCriteria.getSearchZROCode());
			}
			if (null != imageAuditSearchCriteria.getSearchAuditStatus()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchAuditStatus())
					&& !"ALL".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchAuditStatus())) {
				if ("AUDITED".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchAuditStatus())) {
					ps.setString(++i, "Y");
					// System.out.println("Setting IMG_ADT_FLG=Y");
				} else {
					ps.setString(++i, "N");
					// System.out.println("Setting IMG_ADT_FLG=N");
				}
			}
			// Start :- Added By Madhuri on 30th Aug 2017 as per Jtrac
			// Android-388
			if (null != imageAuditSearchCriteria.getsearchMeterRdrEmpId()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getsearchMeterRdrEmpId())
					&& !"NA".equalsIgnoreCase(imageAuditSearchCriteria
							.getsearchMeterRdrEmpId())) {
				ps.setString(++i, imageAuditSearchCriteria
						.getsearchMeterRdrEmpId());
			}
			if (null != imageAuditSearchCriteria.getSearchdBillGenSource()
					&& !"".equalsIgnoreCase(imageAuditSearchCriteria
							.getSearchdBillGenSource())) {
				ps.setString(++i, imageAuditSearchCriteria
						.getSearchdBillGenSource());
			}
			AppLog.info("Bind Val::" + i);
			/*
			 * if (null != imageAuditSearchCriteria.getSearchBillFromDate() &&
			 * !"".equalsIgnoreCase(imageAuditSearchCriteria
			 * .getSearchBillFromDate())) { ps.setString(++i,
			 * imageAuditSearchCriteria .getSearchBillFromDate()); } if (null !=
			 * imageAuditSearchCriteria.getSearchBillToDate() &&
			 * !"".equalsIgnoreCase(imageAuditSearchCriteria
			 * .getSearchBillToDate())) { ps.setString(++i,
			 * imageAuditSearchCriteria .getSearchBillToDate()); }
			 */
			// End :- Added By Madhuri on 30th Aug 2017 as per Jtrac Android-388

			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
			}
			AppLog.info("Found Records For Image Audit::" + totalRecords);
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
	 * This Method returns number of update done in database after audit.
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @param meterReadImgAuditDetails
	 * @return
	 */
	public static int updateImageAuditStatus(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getUpdateRecordQuery(meterReadImgAuditDetails);
			AppLog.info("getUpdateRecordQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReadImgAuditDetails.getImgAuditStatus()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getImgAuditStatus())) {
				ps.setString(++i, meterReadImgAuditDetails.getImgAuditStatus());
				AppLog.info("meterReadImgAuditDetailsObj.getImgAuditStatus()"
						+ meterReadImgAuditDetails.getImgAuditStatus());
			}
			if (null != meterReadImgAuditDetails.getImgAuditFailReason()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getImgAuditFailReason())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getImgAuditFailReason());
				AppLog
						.info("meterReadImgAuditDetailsObj.getImgAuditFailReason()"
								+ meterReadImgAuditDetails
										.getImgAuditFailReason());
			}
			if (null != meterReadImgAuditDetails.getImgAuditRemrk()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getImgAuditRemrk())) {
				ps.setString(++i, meterReadImgAuditDetails.getImgAuditRemrk());
				AppLog.info("meterReadImgAuditDetailsObj.getImgAuditRemrk()"
						+ meterReadImgAuditDetails.getImgAuditRemrk());
			}
			if (null != meterReadImgAuditDetails.getImgAuditBy()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getImgAuditBy())) {
				ps.setString(++i, meterReadImgAuditDetails.getImgAuditBy());
				AppLog.info("meterReadImgAuditDetailsObj.getImgAuditBy()"
						+ meterReadImgAuditDetails.getImgAuditBy());
			}
			ps.setString(++i, "Y");
			if (null != meterReadImgAuditDetails.getImgAuditBy()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getImgAuditBy())) {
				ps.setString(++i, meterReadImgAuditDetails.getImgAuditBy());
				AppLog.info("Last Updated By"
						+ meterReadImgAuditDetails.getImgAuditBy());
			}
			if (null != meterReadImgAuditDetails.getKno()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails.getKno())
					&& null != meterReadImgAuditDetails.getBillRound()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getBillRound())) {
				ps.setString(++i, meterReadImgAuditDetails.getKno());
				ps.setString(++i, meterReadImgAuditDetails.getBillRound());
				AppLog.info("meterReadImgAuditDetailsObj.getKno()"
						+ meterReadImgAuditDetails.getKno());
			}

			AppLog.info("Applying Update for KNO "
					+ meterReadImgAuditDetails.getKno());
			updatedRow += ps.executeUpdate();
			AppLog.info("updatedRow" + updatedRow);

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
}
