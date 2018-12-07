/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java   09 Oct 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;

import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.rms.model.BillGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for saving records to Database.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history Matiur Rahman 01/09/2012 Added TRIM to Update query as some value @Database
 *          is having spaces. @Bug reported: For some entry READ_TYPE_FLG was
 *          not populated.
 * @history Matiur Rahman 06/09/2012 Added method to reset current meter read
 *          details.
 * @history 15-11-2012 Matiur Rahman added no Of Floor
 * 
 * @history Matiur Rahman Added oldMeterAverageRead in
 *          saveMeterReplacementDetail on 11-12-2012
 * @history Matiur Rahman added resetMeterReplacementDetail on 12-12-2012
 * @history Matiur Rahman added Meter Replacement record Freeze option on
 *          12-12-2012
 * @history 15-12-2012 Matiur Rahman commented freezing of Meter Replacement
 *          record in Freeze option on. See {@link #freezeMRD}
 * @history 15-12-2012 Matiur Rahman commented saveMeterReplacementDetail as
 *          moved to {@link MeterReplacementDAO}
 * @history 18-12-2012 Matiur Rahman added flagConsumerStatus
 * @history 29-03-2012 Matiur Rahman modified method resetMeterRead to add reset
 *          meter replacement details as well.
 * @history 18-04-2013 Matiur Rahman Bug Resolved as reported in JTrac# DJB-1413
 * @history 05-07-2013 Mrityunjoy Misra has added methods for single consumer
 *          bill generation
 * @history 18-09-2013 Matiur Rahman modified Query for {@link #freezeMRD}and
 *          {@link #freezeMRDBasedOnKNO},{@link #resetMeterRead},
 *          {@link #updateMeterReadForUnmeteredConsumerForAnMRD} as per
 *          DJB-1871.
 * @history 10-03-2014 Matiur Rahman Modified method {@link #resetMeterRead} to
 *          put restriction that it cannot be updated after it is parked for
 *          processing or billing as a part of changes as perJTrac ID# DJB-2024.
 * @history 08-02-2017 Sanjay modified {@link #freezeMRD} by  as per JTrac DJB-4653 to 
 *          restrict AMR billing for those whose self bill is enabled.         
 */
public class SetterDAO {

	/**
	 * <p>
	 * Flag Status for Meter Read details to cm_cons_pre_bill_proc table for
	 * processing by billing batch.
	 * </p>
	 * 
	 * @param inputMap
	 * @return HashMap
	 */
	public Map<String, String> flagConsumerStatus(
			Map<String, ConsumerDetail> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String isSuccess = "N";
		try {
			ConsumerDetail consumerDetail = (ConsumerDetail) inputMap
					.get("consumerDetail");
			if (null != consumerDetail) {
				MeterReadDetails currentMeterRead = consumerDetail
						.getCurrentMeterRead();
				// System.out.println("In flagConsumerStatus ConsumerStatus::"
				// + consumerDetail.getConsumerStatus()
				// + ">>MeterReadDate>>"
				// + currentMeterRead.getMeterReadDate()
				// + ">>MeterStatus>>" + currentMeterRead.getMeterStatus()
				// + ">>RegRead>>" + currentMeterRead.getRegRead()
				// + ">>AvgRead>>" + currentMeterRead.getAvgRead()
				// + ">>No OF Floor>>" + currentMeterRead.getNoOfFloor()
				// + ">>UpdatedBy>>" + consumerDetail.getUpdatedBy()
				// + ">>>Kno>>" + consumerDetail.getKno()
				// + ">>BillRound>>" + consumerDetail.getBillRound());
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(QueryContants
						.flagConsumerStatusQuery());
				int i = 0;
				ps.setString(++i, consumerDetail.getConsumerStatus());
				if (null != currentMeterRead.getMeterReadDate()
						&& currentMeterRead.getMeterReadDate().trim().length() == 10) {
					ps.setString(++i, currentMeterRead.getMeterReadDate());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getRegRead()
						&& !"".equals(currentMeterRead.getRegRead().trim())) {
					ps.setString(++i, currentMeterRead.getRegRead().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getMeterStatus()
						&& !"".equals(currentMeterRead.getMeterStatus().trim())) {
					ps.setString(++i, currentMeterRead.getMeterStatus());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getAvgRead()
						&& !"".equals(currentMeterRead.getAvgRead().trim())) {
					ps.setString(++i, currentMeterRead.getAvgRead().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getNoOfFloor()
						&& !"".equals(currentMeterRead.getNoOfFloor().trim())) {

					ps.setString(++i, currentMeterRead.getNoOfFloor().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getRemarks()
						&& !"".equals(currentMeterRead.getRemarks().trim())) {
					ps.setString(++i, currentMeterRead.getRemarks().trim());
				} else {
					ps.setString(++i, null);
				}
				/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, consumerDetail.getUpdatedBy());
				/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, consumerDetail.getUpdatedBy());
				ps.setString(++i, consumerDetail.getKno());
				ps.setString(++i, consumerDetail.getBillRound());
				int recordUpdated = ps.executeUpdate();
				if (recordUpdated > 0) {
					isSuccess = "Y";
					returnMap.put("recordUpdatedMsg", Integer
							.toString(recordUpdated)
							+ " record Updated Successfully");
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			// e.printStackTrace();
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
		returnMap.put("isSuccess", isSuccess);
		AppLog.end();
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to freeze MRD
	 * </p>
	 * 
	 * @param mrKey
	 * @param billRoundId
	 * @param lastUpdatedBy
	 * @param zoneCode
	 * @param mrCode
	 * @param areaCode
	 * @return boolean Freeze MRDs wrt Zone MR code and Area
	 */
	public boolean freezeMRD(String mrKey, String billRoundId,
			String lastUpdatedBy, String zoneCode, String mrCode,
			String areaCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		int recordUpdated = -1;
		int mrRecordUpdated = -1;
		try {
			conn = DBConnector.getConnection();
			// AppLog.info(">>mrKey>>" + mrKey + ">>billRoundId>>" +
			// billRoundId);
			StringBuffer querySB1 = new StringBuffer(512);
			querySB1.append(" update cm_cons_pre_bill_proc ");
			querySB1.append(" set cons_pre_bill_stat_id = ?, ");
			/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
			// querySB1.append(" last_updt_dttm = sysdate, last_updt_by = ? ");
			querySB1.append(" data_frozen_dttm=systimestamp,");
			querySB1.append(" data_frozen_by=?");
			/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
			querySB1.append(" where MRKEY = ? and BILL_ROUND_ID = ? ");
			/* Start:08-02-2017 Changed by Sanjay as per JTrac DJB-4653 */
			querySB1.append(" and (is_self is  null  ");
			querySB1.append(" or trim(is_self) <> ? )");
			/* End:08-02-2017 Changed by Sanjay as per JTrac DJB-4653 */
			querySB1.append(" and cons_pre_bill_stat_id = ");
			querySB1
					.append(ConsumerStatusLookup.RECORD_ENTERED.getStatusCode());
			// AppLog.info("querySB1>>" + querySB1.toString());
			AppLog.info("freezeMRD Query>>>"+querySB1.toString());
			ps = conn.prepareStatement(querySB1.toString());
			int i = 0;
			ps.setInt(++i, ConsumerStatusLookup.RECORD_FROZEN.getStatusCode());
			ps.setString(++i, lastUpdatedBy);
			ps.setString(++i, mrKey);
			ps.setString(++i, billRoundId);
			ps.setString(++i,DJBConstants.IS_SELF);//Start:08-02-2017 Changed by Sanjay as per JTrac DJB-4653
			recordUpdated = ps.executeUpdate();
			// Start:5-12-2012 Matiur Rahman commented as per JTrac DJB-1280
			// if (null != ps) {
			// ps.close();
			// }
			// // Added for meter Replacement cases to freeze MRD
			// StringBuffer querySB2 = new StringBuffer(512);
			// querySB2.append(" update cm_cons_pre_bill_proc ");
			// querySB2.append(" set cons_pre_bill_stat_id = ?, ");
			// querySB2.append(" last_updt_dttm = sysdate, last_updt_by = ? ");
			// querySB2.append(" where MRKEY = ? and BILL_ROUND_ID = ? ");
			// querySB2.append(" and cons_pre_bill_stat_id = ");
			// querySB2.append(ConsumerStatusLookup.METER_INSTALLED
			// .getStatusCode());
			// AppLog.info("querySB2>>" + querySB2.toString());
			// ps = conn.prepareStatement(querySB2.toString());
			// i = 0;
			// ps.setInt(++i,
			// ConsumerStatusLookup.METER_REPLACEMENT_RECORD_FROZEN
			// .getStatusCode());
			// ps.setString(++i, lastUpdatedBy);
			// ps.setString(++i, mrKey);
			// ps.setString(++i, billRoundId);
			// mrRecordUpdated = ps.executeUpdate();
			// End:5-12-2012 Matiur Rahman commented as per JTrac DJB-1280
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
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

		if (recordUpdated > 0 || mrRecordUpdated > 0) {
			isSuccess = true;
			/* FreezeMRDDAO freezeMRDDAO = */
			new FreezeMRDDAO(zoneCode, mrCode, areaCode);
			// Thread t = new Thread(freezeMRDDAO);
			// t.start();
		} else {
			isSuccess = false;
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * This method is used to reset Meter Read Details
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public Map<String, String> resetMeterRead(
			Map<String, ConsumerDetail> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String isSuccess = "N";
		try {
			conn = DBConnector.getConnection();
			ConsumerDetail consumerDetail = (ConsumerDetail) inputMap
					.get("consumerDetail");
			if (null != consumerDetail) {
				// MeterReadDetails currentMeterRead = consumerDetail
				// .getCurrentMeterRead();
				// System.out.println("ConsumerStatus()>>"
				// + consumerDetail.getConsumerStatus());
				// System.out.println(">>MeterReadDate>>"
				// + currentMeterRead.getMeterReadDate()
				// + ">>MeterStatus>>" + currentMeterRead.getMeterStatus()
				// + ">>RegRead>>" + currentMeterRead.getRegRead()
				// + ">>AvgRead>>" + currentMeterRead.getAvgRead()
				// + ">>>Kno>>" + consumerDetail.getKno()
				// + ">>BillRound>>" + consumerDetail.getBillRound());
				StringBuffer querySB = new StringBuffer(512);

				// System.out.println("::IN UPDATE Data::");

				querySB.append(" update cm_cons_pre_bill_proc t set ");
				querySB.append(" t.cons_pre_bill_stat_id=?, ");
				querySB.append(" t.bill_gen_date=to_date(null,'dd/mm/yyyy'),");
				querySB.append(" t.reg_reading=null,");
				querySB.append(" t.READ_TYPE_FLG=null,");
				querySB.append(" t.reader_rem_cd=null,");
				querySB.append(" t.new_avg_read=null,");
				querySB.append(" t.new_no_of_floors=null,");
				querySB.append(" t.remarks=null,");
				/*
				 * Start:18-09-2013 Changed by Matiur Rahman as per JTrac
				 * DJB-1871
				 */
				// querySB.append(" t.last_updt_dttm=sysdate,");
				// querySB.append(" t.last_updt_by=?");
				querySB.append(" t.data_reset_dttm=systimestamp,");
				querySB.append(" t.data_reset_by=?");
				/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
				querySB.append(" where 1=1");
				querySB.append(" AND  ACCT_ID=?");
				querySB.append(" AND  BILL_ROUND_ID=?");
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation to prevent update if the records is
				 * parked for processing.
				 */
				// querySB
				// .append(" AND  cons_pre_bill_stat_id not in (65,70,130)");
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation to prevent update if the records is
				 * parked for processing.
				 */
				querySB
						.append(" AND  cons_pre_bill_stat_id not in (65,67,69,70,130)");
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation to prevent update if the records is parked
				 * for processing.
				 */
				// AppLog.info("query Reset Data::" + querySB.toString());
				// System.out.println("query UPDATE Data::" + querySB.toString()
				// + "<<>>" + consumerDetail.getUpdatedBy() + ">>Kno>>"
				// + consumerDetail.getKno() + ">>BillRound>>"
				// + consumerDetail.getBillRound());
				ps = conn.prepareStatement(querySB.toString());
				int i = 0;
				ps.setInt(++i, ConsumerStatusLookup.INITIATED.getStatusCode());
				ps.setString(++i, consumerDetail.getUpdatedBy());
				ps.setString(++i, consumerDetail.getKno().trim());
				ps.setString(++i, consumerDetail.getBillRound().trim());
				int recordUpdated = ps.executeUpdate();
				if (recordUpdated > 0) {
					isSuccess = "Y";
					returnMap.put("recordUpdatedMsg", Integer
							.toString(recordUpdated)
							+ " record Updated Successfully");
					MeterReplacementDetail meterReplacementDetail = new MeterReplacementDetail();
					meterReplacementDetail.setKno(consumerDetail.getKno()
							.trim());
					meterReplacementDetail.setBillRound(consumerDetail
							.getBillRound().trim());
					MeterReplacementDAO
							.resetMeterReplacementDetail(meterReplacementDetail);
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			// e.printStackTrace();
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
		returnMap.put("isSuccess", isSuccess);
		AppLog.end();
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to reset Meter Replacement Detail in the Database.
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return boolean Reset Meter Replacement Detail
	 */
	public boolean resetMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		try {
			// System.out.println(">>CreatedByID()>>>"
			// + meterReplacementDetail.getCreatedByID()
			// + ">>billRound>>>>" + meterReplacementDetail.getBillRound()
			// + ">>>kno>>>" + meterReplacementDetail.getKno());
			conn = DBConnector.getConnection();
			// int recordUpdated = 0;
			StringBuffer querySelectSB = new StringBuffer(512);
			querySelectSB
					.append("  delete from CM_MTR_RPLC_STAGE where acct_id=? and bill_round_id=? ");
			// System.out.println("::Query::" + querySelectSB.toString());
			ps = conn.prepareStatement(querySelectSB.toString());
			int i = 0;
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			// recordUpdated =
			ps.executeUpdate();
			if (null != ps) {
				ps.close();
			}
			ConsumerDetail consumerDetail = new ConsumerDetail();
			consumerDetail
					.setUpdatedBy(meterReplacementDetail.getCreatedByID());
			consumerDetail.setKno(meterReplacementDetail.getKno());
			consumerDetail.setBillRound(meterReplacementDetail.getBillRound());
			MeterReadDetails currentMeterReadDetails = new MeterReadDetails();
			currentMeterReadDetails.setMeterReadDate("");
			currentMeterReadDetails.setMeterStatus("");
			currentMeterReadDetails.setReadType(null);
			currentMeterReadDetails.setRegRead("");
			currentMeterReadDetails.setAvgRead("");
			consumerDetail.setCurrentMeterRead(currentMeterReadDetails);
			HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String, ConsumerDetail>();
			toUpdateMap.put("consumerDetail", consumerDetail);
			HashMap<String, String> returnMap = (HashMap<String, String>) resetMeterRead(toUpdateMap);
			String isSuccessReset = (String) returnMap.get("isSuccess");
			// String recordUpdatedMsg = (String) returnMap
			// .get("recordUpdatedMsg");
			// System.out.println("isSuccessReset::" + isSuccessReset
			// + "::recordUpdatedMsg::" + recordUpdatedMsg);
			if ("Y".equals(isSuccessReset)) {
				isSuccess = true;
			}
		} catch (SQLException e) {
			isSuccess = false;
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
		return isSuccess;
	}

	/**
	 * <p>
	 * This method is used to save Meter Read details to cm_cons_pre_bill_proc
	 * table for processing by billing batch.
	 * </p>
	 * 
	 * @param inputMap
	 * @return HashMap
	 */
	public Map<String, String> saveMeterRead(
			Map<String, ConsumerDetail> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String isSuccess = "N";
		try {
			ConsumerDetail consumerDetail = (ConsumerDetail) inputMap
					.get("consumerDetail");
			if (null != consumerDetail) {
				MeterReadDetails currentMeterRead = consumerDetail
						.getCurrentMeterRead();
				// System.out.println("In saveMeterRead ConsumerStatus()::"
				// + consumerDetail.getConsumerStatus()
				// + ">>MeterReadDate>>"
				// + currentMeterRead.getMeterReadDate()
				// + ">>MeterStatus>>" + currentMeterRead.getMeterStatus()
				// + ">>RegRead>>" + currentMeterRead.getRegRead()
				// + ">>AvgRead>>" + currentMeterRead.getAvgRead()
				// + ">>No OF Floor>>" + currentMeterRead.getNoOfFloor()
				// + ">>Remarks>>" + currentMeterRead.getRemarks()
				// + ">>>Kno>>" + consumerDetail.getKno()
				// + ">>BillRound>>" + consumerDetail.getBillRound()
				// + "\nQuery::"
				// + QueryContants.updateMeterReadDetailsQuery());
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(QueryContants
						.updateMeterReadDetailsQuery());
				int i = 0;
				// ps.setInt(++i, ConsumerStatusLookup.RECORD_ENTERED
				// .getStatusCode());
				ps.setString(++i, consumerDetail.getConsumerStatus());
				if (null != currentMeterRead.getMeterReadDate()
						&& currentMeterRead.getMeterReadDate().trim().length() == 10) {
					ps.setString(++i, currentMeterRead.getMeterReadDate()
							.trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getRegRead()
						&& !"".equals(currentMeterRead.getRegRead().trim())) {
					ps.setString(++i, currentMeterRead.getRegRead().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getMeterStatus()
						&& !"".equals(currentMeterRead.getMeterStatus().trim())) {
					ps.setString(++i, currentMeterRead.getMeterStatus().trim());
					ps.setString(++i, currentMeterRead.getMeterStatus().trim());
				} else {
					ps.setString(++i, null);
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getAvgRead()
						&& !"".equals(currentMeterRead.getAvgRead().trim())) {
					ps.setString(++i, currentMeterRead.getAvgRead().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getNoOfFloor()
						&& !"".equals(currentMeterRead.getNoOfFloor().trim())) {

					ps.setString(++i, currentMeterRead.getNoOfFloor().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != currentMeterRead.getRemarks()
						&& !"".equals(currentMeterRead.getRemarks().trim())) {
					ps.setString(++i, currentMeterRead.getRemarks().trim());
				} else {
					ps.setString(++i, null);
				}
				ps.setString(++i, consumerDetail.getUpdatedBy());
				/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, consumerDetail.getUpdatedBy());
				/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, consumerDetail.getKno());
				ps.setString(++i, consumerDetail.getBillRound());
			}
			int recordUpdated = ps.executeUpdate();
			if (recordUpdated > 0) {
				isSuccess = "Y";
				returnMap.put("recordUpdatedMsg", Integer
						.toString(recordUpdated)
						+ " record Updated Successfully");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			// e.printStackTrace();
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
		returnMap.put("isSuccess", isSuccess);
		AppLog.end();
		return returnMap;
	}

	// /**
	// * @param meterReplacementDetail
	// * @return boolean save Meter Replacement Detail
	// */
	// public boolean saveMeterReplacementDetail(
	// MeterReplacementDetail meterReplacementDetail) {
	// AppLog.begin();
	// Connection conn = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// boolean isSuccess = false;
	// try {
	// // System.out.println(">>meterReaderName>>>"
	// // + meterReplacementDetail.getMeterReaderName()
	// // + ">>billRound>>>>" + meterReplacementDetail.getBillRound()
	// // + ">>>zone>>" + meterReplacementDetail.getZone()
	// // + ">>mrNo>>>" + meterReplacementDetail.getMrNo()
	// // + ">>area>>>" + meterReplacementDetail.getArea()
	// // + ">>>wcNo>>>" + meterReplacementDetail.getWcNo()
	// // + ">>>kno>>>" + meterReplacementDetail.getKno()
	// // + ">>name>>" + meterReplacementDetail.getName()
	// // + ">>category>>"
	// // + meterReplacementDetail.getConsumerCategory()
	// // + ">>ConsumetType>>"
	// // + meterReplacementDetail.getConsumerType()
	// // + ">>waterConnectionSize>>"
	// // + meterReplacementDetail.getWaterConnectionSize()
	// // + ">>waterConnectionUse>>"
	// // + meterReplacementDetail.getWaterConnectionUse()
	// // + ">>meterType>>" + meterReplacementDetail.getMeterType()
	// // + ">>badgeNo>>" + meterReplacementDetail.getBadgeNo()
	// // + ">>manufacturer>>"
	// // + meterReplacementDetail.getManufacturer() + ">>model>>"
	// // + meterReplacementDetail.getModel() + ">>saType>>"
	// // + meterReplacementDetail.getSaType()
	// // + ">>>isLNTServiceProvider>>>"
	// // + meterReplacementDetail.getIsLNTServiceProvider()
	// // + ">>>meterInstalDate>>>"
	// // + meterReplacementDetail.getMeterInstalDate()
	// // + ">>>zeroRead>>>" + meterReplacementDetail.getZeroRead()
	// // + ">>>currentMeterReadDate>>>"
	// // + meterReplacementDetail.getCurrentMeterReadDate()
	// // + ">>>currentMeterRegisterRead>>>"
	// // + meterReplacementDetail.getCurrentMeterRegisterRead()
	// // + ">>currentMeterReadRemarkCode>>"
	// // + meterReplacementDetail.getCurrentMeterReadRemarkCode()
	// // + ">>newAverageConsumption>>"
	// // + meterReplacementDetail.getNewAverageConsumption()
	// // + ">>oldMeterRegisterRead>>"
	// // + meterReplacementDetail.getOldMeterRegisterRead()
	// // + ">>oldMeterReadRemarkCode>>"
	// // + meterReplacementDetail.getOldMeterReadRemarkCode()
	// // + ">>OldMeterAverageRead>>"
	// // + meterReplacementDetail.getOldMeterAverageRead());
	// /* Commented as this part of code is moved to action class */
	// // if (null != meterReplacementDetail.getConsumerType()
	// // && "METERED".equals(meterReplacementDetail
	// // .getConsumerType().trim())) {
	// // meterReplacementDetail.setMeterReplaceStageID("350");
	// // } else {
	// // meterReplacementDetail.setMeterReplaceStageID("10");
	// // }
	// // if (null == meterReplacementDetail.getWaterConnectionSize()
	// // || "".equals(meterReplacementDetail
	// // .getWaterConnectionSize().trim())) {
	// // meterReplacementDetail.setWaterConnectionSize("15");
	// // }
	// // if (null == meterReplacementDetail.getBadgeNo()
	// // || "".equals(meterReplacementDetail.getBadgeNo().trim())) {
	// // meterReplacementDetail.setBadgeNo("NA");
	// // }
	// // if (null == meterReplacementDetail.getManufacturer()
	// // || "".equals(meterReplacementDetail.getManufacturer()
	// // .trim())) {
	// // meterReplacementDetail.setManufacturer("NA");
	// // }
	// // if (null == meterReplacementDetail.getModel()
	// // || "".equals(meterReplacementDetail.getModel().trim())) {
	// // meterReplacementDetail.setModel("N/A W");
	// // }
	// // if (null == meterReplacementDetail.getIsLNTServiceProvider()
	// // || "".equals(meterReplacementDetail
	// // .getIsLNTServiceProvider().trim())) {
	// // meterReplacementDetail.setIsLNTServiceProvider("NA");
	// // }
	// conn = DBConnector.getConnection();
	// int recordUpdated = 0;
	// StringBuffer querySelectSB = new StringBuffer(512);
	// querySelectSB
	// .append("  select acct_id,bill_round_id from CM_MTR_RPLC_STAGE where acct_id=? and bill_round_id=? ");
	// // System.out.println("::Query::" + querySelectSB.toString());
	// ps = conn.prepareStatement(querySelectSB.toString());
	// int i = 0;
	// ps.setString(++i, meterReplacementDetail.getKno());
	// ps.setString(++i, meterReplacementDetail.getBillRound());
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// StringBuffer querySB = new StringBuffer(512);
	// // added oldavgcons by matiur Rahman
	// querySB
	// .append(" update CM_MTR_RPLC_STAGE set zonecd=?,mrno=?,areano=?,wcno=?,mtr_install_date=to_date(?,'DD/MM/YYYY'),consumer_name=?,sa_type_cd=?,mtrtyp=?,reader_rem_cd=?,mtr_reader_id=?,badge_nbr=?,mfg_cd=?,model_cd=?,mtr_start_read=?,old_mtr_read=?,wconsize=?,wconuse=?,is_lnt_srvc_prvdr=?,cust_cl_cd=?,mtr_rplc_stage_id=?,last_updt_dttm=sysdate,last_updt_by=?,oldavgcons=? ");
	// querySB.append(" where acct_id=? and bill_round_id=? ");
	//
	// // System.out.println("::Query::" + querySB.toString());
	// if (null != ps) {
	// ps.close();
	// }
	// ps = conn.prepareStatement(querySB.toString());
	// i = 0;
	// ps.setString(++i, meterReplacementDetail.getZone());
	// ps.setString(++i, meterReplacementDetail.getMrNo());
	// ps.setString(++i, meterReplacementDetail.getArea());
	// ps.setString(++i, meterReplacementDetail.getWcNo());
	// ps.setString(++i, meterReplacementDetail.getMeterInstalDate());
	// ps.setString(++i, meterReplacementDetail.getName());
	// ps.setString(++i, meterReplacementDetail.getSaType());
	// ps.setString(++i, meterReplacementDetail.getMeterType());
	// ps.setString(++i, meterReplacementDetail
	// .getCurrentMeterReadRemarkCode());
	// ps.setString(++i, meterReplacementDetail.getMeterReaderName());
	// ps.setString(++i, meterReplacementDetail.getBadgeNo());
	// ps.setString(++i, meterReplacementDetail.getManufacturer());
	// ps.setString(++i, meterReplacementDetail.getModel());
	// ps.setDouble(++i, meterReplacementDetail.getZeroRead());
	// ps.setDouble(++i, meterReplacementDetail
	// .getOldMeterRegisterRead());
	// ps.setString(++i, meterReplacementDetail
	// .getWaterConnectionSize());
	// ps.setString(++i, meterReplacementDetail
	// .getWaterConnectionUse());
	// ps.setString(++i, meterReplacementDetail
	// .getIsLNTServiceProvider());
	// ps.setString(++i, meterReplacementDetail.getConsumerCategory());
	// ps.setString(++i, meterReplacementDetail
	// .getMeterReplaceStageID());
	// ps.setString(++i, meterReplacementDetail.getCreatedByID());
	// // Added By matiur Rahman on 11-12-2012
	// ps.setDouble(++i, meterReplacementDetail
	// .getOldMeterAverageRead());
	// // ====================================
	// ps.setString(++i, meterReplacementDetail.getKno());
	// ps.setString(++i, meterReplacementDetail.getBillRound());
	//
	// recordUpdated = ps.executeUpdate();
	// } else {
	// StringBuffer querySB = new StringBuffer(512);
	// querySB.append(" insert into cm_mtr_rplc_stage ");
	// querySB
	// .append(" (zonecd,mrno,areano,wcno,acct_id,mtr_install_date,bill_round_id,mrkey,consumer_name,sa_type_cd,mtrtyp,reader_rem_cd,mtr_reader_id,badge_nbr,mfg_cd,model_cd,mtr_start_read,old_mtr_read,wconsize,wconuse,is_lnt_srvc_prvdr,cust_cl_cd,mtr_rplc_stage_id, ");
	// // added oldavgcons by matiur Rahman
	// querySB.append(" create_dttm,created_by,oldavgcons )");
	// querySB
	// .append(" values(?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,(select z.mrd_cd from djb_zn_mr_ar_mrd z where z.subzone_cd=? and z.mr_cd=? and z.area_cd=?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)");
	//
	// // System.out.println("::Query::" + querySB.toString());
	// if (null != ps) {
	// ps.close();
	// }
	// ps = conn.prepareStatement(querySB.toString());
	// i = 0;
	// ps.setString(++i, meterReplacementDetail.getZone());
	// ps.setString(++i, meterReplacementDetail.getMrNo());
	// ps.setString(++i, meterReplacementDetail.getArea());
	// ps.setString(++i, meterReplacementDetail.getWcNo());
	// ps.setString(++i, meterReplacementDetail.getKno());
	// ps.setString(++i, meterReplacementDetail.getMeterInstalDate());
	// ps.setString(++i, meterReplacementDetail.getBillRound());
	// ps.setString(++i, meterReplacementDetail.getZone());
	// ps.setString(++i, meterReplacementDetail.getMrNo());
	// ps.setString(++i, meterReplacementDetail.getArea());
	// ps.setString(++i, meterReplacementDetail.getName());
	// ps.setString(++i, meterReplacementDetail.getSaType());
	// ps.setString(++i, meterReplacementDetail.getMeterType());
	// ps.setString(++i, meterReplacementDetail
	// .getCurrentMeterReadRemarkCode());
	// ps.setString(++i, meterReplacementDetail.getMeterReaderName());
	// ps.setString(++i, meterReplacementDetail.getBadgeNo());
	// ps.setString(++i, meterReplacementDetail.getManufacturer());
	// ps.setString(++i, meterReplacementDetail.getModel());
	// ps.setDouble(++i, meterReplacementDetail.getZeroRead());
	// ps.setDouble(++i, meterReplacementDetail
	// .getOldMeterRegisterRead());
	// ps.setString(++i, meterReplacementDetail
	// .getWaterConnectionSize());
	// ps.setString(++i, meterReplacementDetail
	// .getWaterConnectionUse());
	// ps.setString(++i, meterReplacementDetail
	// .getIsLNTServiceProvider());
	// ps.setString(++i, meterReplacementDetail.getConsumerCategory());
	// ps.setString(++i, meterReplacementDetail
	// .getMeterReplaceStageID());
	// ps.setString(++i, meterReplacementDetail.getCreatedByID());
	// // Added By matiur Rahman on 11-12-2012
	// ps.setDouble(++i, meterReplacementDetail
	// .getOldMeterAverageRead());
	// // ====================================
	// recordUpdated = ps.executeUpdate();
	//
	// }
	// if (recordUpdated > 0) {
	// if (null != ps) {
	// ps.close();
	// }
	// StringBuffer querySB = new StringBuffer(512);
	// querySB.append(" update cm_cons_pre_bill_proc t set ");
	// querySB.append(" t.cons_pre_bill_stat_id='"
	// + ConsumerStatusLookup.METER_INSTALLED.getStatusCode()
	// + "', ");
	// querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
	// querySB.append(" t.reg_reading=?,");
	// querySB
	// .append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
	// querySB.append(" t.reader_rem_cd=?,");
	// querySB.append(" t.new_avg_read=?,");
	// querySB.append(" t.last_updt_dttm=sysdate,");
	// querySB.append(" t.last_updt_by=?");
	// querySB.append(" where 1=1");
	// querySB.append(" AND  ACCT_ID=?");
	// querySB.append(" AND  BILL_ROUND_ID=?");
	// // StringBuffer queryConStatSB = new StringBuffer(512);
	// // queryConStatSB.append(" update cm_cons_pre_bill_proc t set ");
	// // queryConStatSB.append(" t.cons_pre_bill_stat_id='"
	// // + ConsumerStatusLookup.METER_INSTALLED.getStatusCode()
	// // + "', ");
	// // queryConStatSB.append(" t.last_updt_dttm=sysdate,");
	// // queryConStatSB.append(" t.last_updt_by=?");
	// // queryConStatSB.append(" where 1=1");
	// // queryConStatSB.append(" AND  ACCT_ID=?");
	// // queryConStatSB.append(" AND  BILL_ROUND_ID=?");
	// // System.out.println("::UPDATE Status::"
	// // + queryConStatSB.toString());
	// ps = conn.prepareStatement(querySB.toString());
	// i = 0;
	// ps.setString(++i, meterReplacementDetail
	// .getCurrentMeterReadDate());
	// ps.setDouble(++i, meterReplacementDetail
	// .getCurrentMeterRegisterRead());
	// ps.setString(++i, meterReplacementDetail
	// .getCurrentMeterReadRemarkCode());
	// ps.setString(++i, meterReplacementDetail
	// .getCurrentMeterReadRemarkCode());
	// ps.setDouble(++i, meterReplacementDetail
	// .getNewAverageConsumption());
	// ps.setString(++i, meterReplacementDetail.getCreatedByID());
	// ps.setString(++i, meterReplacementDetail.getKno());
	// ps.setString(++i, meterReplacementDetail.getBillRound());
	// recordUpdated = ps.executeUpdate();
	// if (recordUpdated > 0) {
	// isSuccess = true;
	// }
	// // System.out.println("isSuccess::" + isSuccess);
	// }
	//
	// } catch (SQLException e) {
	// isSuccess = false;
	// AppLog.error(e);
	//
	// } catch (Exception e) {
	// AppLog.error(e);
	// } finally {
	// try {
	// if (null != ps) {
	// ps.close();
	// }
	// if (null != rs) {
	// rs.close();
	// }
	// if (null != conn) {
	// conn.close();
	// }
	// } catch (Exception e) {
	// AppLog.error(e);
	// }
	// }
	// AppLog.end();
	// return isSuccess;
	// }

	/**
	 * <p>
	 * This method is used to save Meter Read details for Unmetered Consumer to
	 * cm_cons_pre_bill_proc table for processing by billing batch.
	 * </p>
	 * 
	 * @param inputMap
	 * @return HashMap
	 */
	public Map<String, String> updateMeterReadForUnmeteredConsumerForAnMRD(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		int noOfUnprocessedRecords = 0;
		int recordUpdated = 0;
		try {
			conn = DBConnector.getConnection();
			String zone = (String) inputMap.get("zone");
			String mrNo = (String) inputMap.get("mrNo");
			String area = (String) inputMap.get("area");
			String billRound = (String) inputMap.get("billRound");
			String billGenDate = (String) inputMap.get("billGenDate");
			String updatedBy = (String) inputMap.get("updatedBy");

			// System.out.println(">>billRound>>" + billRound + ">>zone>>" +
			// zone
			// + ">>mrNo>>" + mrNo + ">>area>>" + area + ">>billGenDate>>"
			// + billGenDate);
			StringBuffer querySB = new StringBuffer(512);
			StringBuffer queryCountUnprocessedRecordsSB = new StringBuffer(512);
			queryCountUnprocessedRecordsSB
					.append(" select count(x.cons_pre_bill_stat_id) no_of_un_proc_records ");
			queryCountUnprocessedRecordsSB
					.append(" from cm_cons_pre_bill_proc x, djb_zn_mr_ar_mrd z ");
			queryCountUnprocessedRecordsSB.append(" where x.mrkey = z.mrd_cd ");
			queryCountUnprocessedRecordsSB
					.append(" and x.cons_pre_bill_stat_id not in (70, 130) ");
			queryCountUnprocessedRecordsSB
					.append(" and trim(x.sa_type_cd) not in ('SAWATSEW', 'SAWATR') ");
			queryCountUnprocessedRecordsSB.append(" and x.bill_round_id = ? ");
			queryCountUnprocessedRecordsSB.append(" and z.subzone_cd = ? ");
			queryCountUnprocessedRecordsSB.append(" and z.mr_cd = ? ");
			queryCountUnprocessedRecordsSB.append(" and z.area_cd = ? ");
			// System.out.println("queryCountUnprocessedRecordsSB::"
			// + queryCountUnprocessedRecordsSB.toString());
			ps = conn.prepareStatement(queryCountUnprocessedRecordsSB
					.toString());
			int c = 0;
			ps.setString(++c, billRound);
			ps.setString(++c, zone);
			ps.setString(++c, mrNo);
			ps.setString(++c, area);
			rs = ps.executeQuery();
			while (rs.next()) {
				noOfUnprocessedRecords = rs.getInt("no_of_un_proc_records");
			}
			// System.out.println("noOfUnprocessedRecords::"
			// + noOfUnprocessedRecords);
			if (noOfUnprocessedRecords > 0) {
				if (null != ps) {
					ps.close();
				}
				if (null != rs) {
					rs.close();
				}
				querySB.append(" update cm_cons_pre_bill_proc t	");
				querySB.append(" set t.cons_pre_bill_stat_id = ?,");
				querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
				querySB
						.append(" t.reader_rem_cd=(Cm_get_prev_meter_read(t.acct_id).remark),");
				/*
				 * Start:18-09-2013 Changed by Matiur Rahman as per JTrac
				 * DJB-1871
				 */
				// querySB.append(" t.last_updt_dttm=sysdate,");
				// querySB.append(" t.last_updt_by=?");
				querySB.append(" t.data_entry_dttm=systimestamp,");
				querySB.append(" t.data_entry_by =? ");
				/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
				querySB
						.append(" where 1 = 1 and t.cons_pre_bill_stat_id not in (70, 130) and trim(t.sa_type_cd) not in ('SAWATSEW', 'SAWATR') AND t.BILL_ROUND_ID = ? ");
				querySB.append(" AND t.mrkey = (select z.mrd_cd");
				querySB.append(" from djb_zn_mr_ar_mrd z");
				querySB.append(" where z.subzone_cd = ?");
				querySB.append(" and z.mr_cd = ?");
				querySB.append(" and z.area_cd = ?)");
				// AppLog.info("query UPDATE Data::" + querySB.toString());
				// System.out.println("query UPDATE Data::" +
				// querySB.toString());
				ps = conn.prepareStatement(querySB.toString());
				int i = 0;
				ps.setInt(++i, ConsumerStatusLookup.RECORD_ENTERED
						.getStatusCode());
				ps.setString(++i, billGenDate);
				ps.setString(++i, updatedBy);
				ps.setString(++i, billRound);
				ps.setString(++i, zone);
				ps.setString(++i, mrNo);
				ps.setString(++i, area);
				recordUpdated = ps.executeUpdate();

			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			// e.printStackTrace();
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
		returnMap.put("noOfUnprocessedRecords", Integer
				.toString(noOfUnprocessedRecords));
		returnMap.put("recordUpdated", Integer.toString(recordUpdated));
		AppLog.end();
		return returnMap;
	}

	/********************************************************************************************************************************************/
	/**
	 * <p>
	 * This method is used to save Meter Read details to cm_cons_pre_bill_proc
	 * table for processing by billing batch.
	 * </p>
	 * 
	 * @param inputMap
	 * @return HashMap
	 */
	public Map<String, String> saveMeterRead2(
			Map<String, ConsumerDetail> inputMap,
			BillGenerationDetails billGendetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String isSuccess = "N";
		int seqNoInt = 0;
		try {
			ConsumerDetail consumerDetail = (ConsumerDetail) inputMap
					.get("consumerDetail");
			if (null != consumerDetail) {

				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(QueryContants
						.updateMeterReadDetailsQueryBasedOnKNO());
				int i = 0;

				ps.setString(++i, billGendetails.getConsumerStatus());
				if (null != billGendetails.getReadDate()
						&& billGendetails.getReadDate().trim().length() == 10) {
					ps.setString(++i, billGendetails.getReadDate().trim());
				} else {
					ps.setString(++i, null);
				}

				if (null != billGendetails.getRegRead()
						&& !"".equals(billGendetails.getRegRead().trim())) {
					ps.setString(++i, billGendetails.getRegRead().trim());
				} else {
					ps.setString(++i, null);
				}

				if (null != billGendetails.getReaderRemark()
						&& !"".equals(billGendetails.getReaderRemark().trim())) {
					ps.setString(++i, billGendetails.getReaderRemark().trim());
					ps.setString(++i, billGendetails.getReaderRemark().trim());
				} else {
					ps.setString(++i, null);
					ps.setString(++i, null);
				}

				if (null != billGendetails.getAverageConsumption()
						&& !"".equals(billGendetails.getAverageConsumption()
								.trim())) {
					ps.setString(++i, billGendetails.getAverageConsumption()
							.trim());
				} else {
					ps.setString(++i, null);
				}

				if (null != billGendetails.getNoOfFloors()
						&& !"".equals(billGendetails.getNoOfFloors().trim())) {

					ps.setString(++i, billGendetails.getNoOfFloors().trim());
				} else {
					ps.setString(++i, null);
				}

				if (null != billGendetails.getReaderRemark()
						&& !"".equals(billGendetails.getReaderRemark().trim())) {
					ps.setString(++i, billGendetails.getReaderRemark().trim());
				} else {
					ps.setString(++i, null);
				}
				/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, billGendetails.getUserName());
				/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, billGendetails.getUserName());

				ps.setString(++i, billGendetails.getAcctId());

				ps.setString(++i, billGendetails.getBillRoundId());
			}
			int recordUpdated = ps.executeUpdate();
			if (recordUpdated > 0) {

				// System.out.println("RECORD HAS BEEN SUCESSFULLY UPDATED");
				isSuccess = "Y";

				String showAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Last Action-><font color='blue'>Record No: "
						+ (seqNoInt + 1)
						+ "</font>&nbsp;(KNO: "
						+ billGendetails.getAcctId()
						+ ") Saved Successfully.</b></font></div>";

				returnMap.put("isSuccess", isSuccess);
				returnMap.put("showAjaxoutput", showAjaxoutput);
				returnMap.put("recordUpdatedMsg", Integer
						.toString(recordUpdated));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
				AppLog.error(e);
			}
		}
		returnMap.put("isSuccess", isSuccess);
		AppLog.end();
		return returnMap;
	}
	
	/**
	 * <p>
	 * flagConsumerStatusBasedOnKNO is used to update records for consumer pre
	 * bill proc status is not equals 60
	 * </p>
	 * 
	 * @param inputMap
	 * @param billGendetails
	 * @return
	 */
	public Map<String, String> flagConsumerStatusBasedOnKNO(
			Map<String, ConsumerDetail> inputMap,
			BillGenerationDetails billGendetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		String isSuccess = "N";
		int seqNoInt = 0;
		try {
			ConsumerDetail consumerDetail = (ConsumerDetail) inputMap
					.get("consumerDetail");
			if (null != consumerDetail) {
				MeterReadDetails currentMeterRead = consumerDetail
						.getCurrentMeterRead();

				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(QueryContants
						.flagConsumerStatusQuery());
				int i = 0;

				ps.setString(++i, billGendetails.getConsumerStatus());

				if (null != billGendetails.getReadDate()
						&& billGendetails.getReadDate().trim().length() == 10) {
					ps.setString(++i, billGendetails.getReadDate());
				} else {
					ps.setString(++i, null);
				}
				if (null != billGendetails.getRegRead()
						&& !"".equals(billGendetails.getRegRead().trim())) {
					ps.setString(++i, billGendetails.getRegRead().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != billGendetails.getReaderRemark()
						&& !"".equals(billGendetails.getReaderRemark().trim())) {
					ps.setString(++i, billGendetails.getReaderRemark());
				} else {
					ps.setString(++i, null);
				}
				if (null != billGendetails.getAverageConsumption()
						&& !"".equals(billGendetails.getAverageConsumption()
								.trim())) {
					ps.setString(++i, billGendetails.getAverageConsumption()
							.trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != billGendetails.getNoOfFloors()
						&& !"".equals(billGendetails.getNoOfFloors().trim())) {

					ps.setString(++i, billGendetails.getNoOfFloors().trim());
				} else {
					ps.setString(++i, null);
				}
				if (null != billGendetails.getReaderRemark()
						&& !"".equals(billGendetails.getReaderRemark().trim())) {
					ps.setString(++i, billGendetails.getReaderRemark().trim());
				} else {
					ps.setString(++i, null);
				}
				/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, billGendetails.getUserName());
				/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
				ps.setString(++i, billGendetails.getUserName());
				ps.setString(++i, billGendetails.getAcctId());
				ps.setString(++i, billGendetails.getBillRoundId());
				int recordUpdated = ps.executeUpdate();
				// if (recordUpdated > 0) {

				if (recordUpdated > 0) {
					// System.out.println("RECORD HAS BEEN SUCESSFULLY UPDATED");
					isSuccess = "Y";

					String showAjaxoutput = "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Last Action-><font color='blue'>Record No: "
							+ (seqNoInt + 1)
							+ ", Service Cycle Route Sequence No: "
							+ consumerDetail.getServiceCycleRouteSeq()
							+ "</font>&nbsp;(KNO: "
							+ billGendetails.getAcctId()
							+ ") Saved Successfully.</b></font></div>";

					returnMap.put("isSuccess", isSuccess);
					returnMap.put("showAjaxoutput", showAjaxoutput);
					returnMap.put("recordUpdatedMsg", Integer
							.toString(recordUpdated));
				}

				// isSuccess = "Y";
				// returnMap.put("recordUpdatedMsg", Integer
				// .toString(recordUpdated)
				// + " record Updated Successfully");
				// }
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			// e.printStackTrace();
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
		returnMap.put("isSuccess", isSuccess);
		AppLog.end();
		return returnMap;
	}
	
	/**
	 * <p>
	 * freezeMRDBasedOnKNO is used to freeze consumer based on the acct_ID
	 * </p>
	 * 
	 * @param acctKNO
	 * @param lastUpdatedBy
	 * @return
	 */
	public boolean freezeMRDBasedOnKNO(String acctKNO, String lastUpdatedBy) {

		// System.out.println("SETTERDAO acctKNO:"+acctKNO);
		// System.out.println("SETTERDAO lastUpdatedBy:"+lastUpdatedBy);
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		int recordUpdated = -1;
		int mrRecordUpdated = -1;
		try {
			conn = DBConnector.getConnection();
			// AppLog.info(">>mrKey>>" + mrKey + ">>billRoundId>>" +
			// billRoundId);
			StringBuffer querySB1 = new StringBuffer(512);
			querySB1.append(" update cm_cons_pre_bill_proc ");
			querySB1.append(" set cons_pre_bill_stat_id = ?, ");
			/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
			// querySB1.append(" last_updt_dttm = sysdate, last_updt_by = ? ");
			querySB1.append(" data_frozen_dttm=systimestamp,");
			querySB1.append(" data_frozen_by=?");
			/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
			querySB1.append(" where ACCT_ID = ? ");
			querySB1.append(" and cons_pre_bill_stat_id = ");
			querySB1
					.append(ConsumerStatusLookup.RECORD_ENTERED.getStatusCode());
			// AppLog.info("querySB1>>" + querySB1.toString());
			ps = conn.prepareStatement(querySB1.toString());
			int i = 0;
			ps.setInt(++i, ConsumerStatusLookup.RECORD_FROZEN.getStatusCode());
			ps.setString(++i, lastUpdatedBy);
			ps.setString(++i, acctKNO);
			// ps.setString(++i, billRoundId);
			recordUpdated = ps.executeUpdate();

			if (recordUpdated > 0) {
				// System.out.println("Record has been freezed sucessfully");

			}

			// Start:5-12-2012 Matiur Rahman commented as per JTrac DJB-1280
			// if (null != ps) {
			// ps.close();
			// }
			// // Added for meter Replacement cases to freeze MRD
			// StringBuffer querySB2 = new StringBuffer(512);
			// querySB2.append(" update cm_cons_pre_bill_proc ");
			// querySB2.append(" set cons_pre_bill_stat_id = ?, ");
			// querySB2.append(" last_updt_dttm = sysdate, last_updt_by = ? ");
			// querySB2.append(" where MRKEY = ? and BILL_ROUND_ID = ? ");
			// querySB2.append(" and cons_pre_bill_stat_id = ");
			// querySB2.append(ConsumerStatusLookup.METER_INSTALLED
			// .getStatusCode());
			// AppLog.info("querySB2>>" + querySB2.toString());
			// ps = conn.prepareStatement(querySB2.toString());
			// i = 0;
			// ps.setInt(++i,
			// ConsumerStatusLookup.METER_REPLACEMENT_RECORD_FROZEN
			// .getStatusCode());
			// ps.setString(++i, lastUpdatedBy);
			// ps.setString(++i, mrKey);
			// ps.setString(++i, billRoundId);
			// mrRecordUpdated = ps.executeUpdate();
			// End:5-12-2012 Matiur Rahman commented as per JTrac DJB-1280
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
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

		if (recordUpdated > 0 || mrRecordUpdated > 0) {
			isSuccess = true;
			/* FreezeMRDDAO freezeMRDDAO = */

			try {
				conn = DBConnector.getConnection();
				StringBuffer querySB1 = new StringBuffer(512);
				querySB1
						.append("SELECT ZONENO,MRNO,AREANO, WCNO,LAST_OK_DT  from DJB_MAIN_LC_NEW where acct_id = ?");
				ps = conn.prepareStatement(querySB1.toString());
				int i = 0;
				ps.setString(++i, acctKNO);
				rs = ps.executeQuery();
				while (rs.next()) {

					// System.out.println("ZONENO::::"+rs.getString("ZONENO"));
					// System.out.println("MRNO::::"+rs.getString("MRNO"));
					// System.out.println("AREANO::::"+rs.getString("AREANO"));
					new FreezeMRDDAO(rs.getString("ZONENO"), rs
							.getString("MRNO"), rs.getString("AREANO"));

				}
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

			// new FreezeMRDDAO(zoneCode, mrCode, areaCode);
			// Thread t = new Thread(freezeMRDDAO);
			// t.start();
		} else {
			isSuccess = false;
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * This method is used to update Onilne SA Billing source
	 * </p>
	 * 
	 * @param acctNo
	 * @param billRoundID
	 * @param billingSource
	 * @return
	 */
	public boolean updateOnlineSABillingSource(String acctNo,
			String billRoundID, String billingSource) {
		boolean isBillingSourceUpdated = false;
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			StringBuffer querySB = new StringBuffer(512);
			querySB
					.append("update cm_cons_pre_bill_proc set billing_started_by = ? where bill_round_id=? and acct_id = ?");
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, billingSource);
			ps.setString(++i, billRoundID);
			ps.setString(++i, acctNo);
			int result = ps.executeUpdate();
			if (result > 0) {
				isBillingSourceUpdated = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			AppLog.error(e);

		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
				AppLog.error(e);
			}
		}

		return isBillingSourceUpdated;

	}

	/*****************************************************************************************************************************************/

}
