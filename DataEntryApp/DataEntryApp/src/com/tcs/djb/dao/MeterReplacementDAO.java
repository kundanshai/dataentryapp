/************************************************************************************************************
 * @(#) MeterReplacementDAO.java   11 Mar 2013
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

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.SADetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for MeterReplacement Screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 11-03-2013
 * @history 15-12-2012 Matiur Rahman added method saveMeterReplacementDetail
 * @history 15-12-2012 Matiur Rahman added method insertMeterReplacementDetails
 * @history 15-12-2012 Matiur Rahman added method updateMeterReplacementDetail
 * @history 15-12-2012 Matiur Rahman added method resetMeterReplacementDetail
 * @history 21-03-2013 Matiur Rahman modified method
 *          insertMeterReplacementDetail for populating Operation Area Code
 * @history 21-03-2013 Matiur Rahman modified method
 *          updateMeterReplacementDetail for populating Operation Area Code
 * @history 21-03-2013 Matiur Rahman modified method
 *          {@link #getConsumerDetailsForMRByKNO()} as per JTrac ID DJB-1871.
 * 
 * @history 03-01-2014 Rajib Hazarika modified method
 *          {@link #getMeterReplacementDetailsForConsumer()} as per JTrac ID
 *          DJB-2024.
 */
public class MeterReplacementDAO {

	/**
	 * <p>
	 * Retrieve Consumer Details For Meter Replacement By KNO.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param kno
	 * @param billRound
	 * @return
	 */
	public static MeterReplacementDetail getConsumerDetailsForMRByKNO(
			String kno, String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReplacementDetail meterReplacementDetail = null;
		try {
			String query = QueryContants.getConsumerDetailsForMRByKNOQuery();
			// AppLog.info("getConsumerDetailsForMRByKNO query::" + query);
			// System.out.println("getConsumerDetailsForMRByKNO query::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, kno);
			// ps.setString(++i, billRound);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setKno(rs.getString("acct_id"));
				meterReplacementDetail.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				meterReplacementDetail.setZone(rs.getString("subzone_cd")
						.trim());
				meterReplacementDetail.setMrNo(rs.getString("mr_cd"));
				meterReplacementDetail.setArea(rs.getString("area_cd"));
				meterReplacementDetail.setWcNo(rs.getString("wcno"));
				meterReplacementDetail.setName(rs.getString("consumer_name")
						.trim());
				meterReplacementDetail.setSaType(rs.getString("sa_type_cd")
						.trim());
				String consumerType = rs.getString("sa_type_cd");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setConsumerCategory(rs.getString(
						"cust_cl_cd").trim());
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("avg_read"));

				meterReplacementDetail.setPreBillStatID(rs
						.getString("cons_pre_bill_stat_id"));
				meterReplacementDetail.setCurrentMeterReadDate(rs
						.getString("bill_gen_date"));
				meterReplacementDetail.setCurrentMeterRegisterRead(rs
						.getDouble("reg_reading"));
				meterReplacementDetail.setCurrentMeterReadRemarkCode(rs
						.getString("reader_rem_cd"));
				meterReplacementDetail.setNewAverageConsumption(rs
						.getDouble("new_avg_read"));
				meterReplacementDetail.setNoOfFloors(rs
						.getString("new_no_of_floors"));
				meterReplacementDetail.setCurrentAverageConsumption(rs
						.getString("avg_read"));
				/*
				 * Start:18-09-2013 Changed by Matiur Rahman as per JTrac
				 * DJB-1871
				 */
				String lastUpdatedBy = rs.getString("data_parked_by");
				String lastUpdatedOn = rs.getString("data_parked_dttm");
				if (null == lastUpdatedBy
						|| (null != lastUpdatedBy && "".equals(lastUpdatedBy
								.trim()))) {
					lastUpdatedBy = rs.getString("data_frozen_by");
					lastUpdatedOn = rs.getString("data_frozen_dttm");
				}
				if (null == lastUpdatedBy
						|| (null != lastUpdatedBy && "".equals(lastUpdatedBy
								.trim()))) {
					lastUpdatedBy = rs.getString("data_entry_by");
					lastUpdatedOn = rs.getString("data_entry_dttm");
				}
				if (null == lastUpdatedBy
						|| (null != lastUpdatedBy && "".equals(lastUpdatedBy
								.trim()))) {
					lastUpdatedBy = rs.getString("last_updt_by");
					lastUpdatedOn = rs.getString("last_updt_dttm");
				}
				/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
				meterReplacementDetail.setLastUpdatedByID(lastUpdatedBy);
				meterReplacementDetail.setLastUpdateDate(lastUpdatedOn);
				meterReplacementDetail.setMeterReplacementDetailRemark(rs
						.getString("remarks"));
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
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * Retrieve Consumer Details For Meter Replacement By Zone MR Area WC No.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param zone
	 * @param mrNo
	 * @param areaCode
	 * @param wcNo
	 * @return
	 */
	public static MeterReplacementDetail getConsumerDetailsForMRByZMAW(
			String billRound, String zone, String mrNo, String areaCode,
			String wcNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReplacementDetail meterReplacementDetail = null;
		try {
			String query = QueryContants.getConsumerDetailsForMRByZMAWQuery();
			// AppLog.info("getConsumerDetailsForMRByZMAW query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// ps.setString(++i, billRound);
			ps.setString(++i, zone);
			ps.setString(++i, mrNo);
			ps.setString(++i, areaCode);
			ps.setString(++i, wcNo);

			rs = ps.executeQuery();
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setKno(rs.getString("acct_id"));
				meterReplacementDetail.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				meterReplacementDetail.setZone(rs.getString("subzone_cd")
						.trim());
				meterReplacementDetail.setMrNo(rs.getString("mr_cd"));
				meterReplacementDetail.setArea(rs.getString("area_cd"));
				meterReplacementDetail.setWcNo(rs.getString("wcno"));
				meterReplacementDetail.setName(rs.getString("consumer_name"));
				meterReplacementDetail.setSaType(rs.getString("sa_type_cd")
						.trim());
				String consumerType = rs.getString("sa_type_cd");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setConsumerCategory(rs.getString(
						"cust_cl_cd").trim());
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("avg_read"));
				meterReplacementDetail.setPreBillStatID(rs
						.getString("cons_pre_bill_stat_id"));
				meterReplacementDetail.setCurrentMeterReadDate(rs
						.getString("bill_gen_date"));
				meterReplacementDetail.setCurrentMeterRegisterRead(rs
						.getDouble("reg_reading"));
				meterReplacementDetail.setCurrentMeterReadRemarkCode(rs
						.getString("reader_rem_cd"));
				meterReplacementDetail.setNewAverageConsumption(rs
						.getDouble("new_avg_read"));
				meterReplacementDetail.setNoOfFloors(rs
						.getString("new_no_of_floors"));
				meterReplacementDetail.setCurrentAverageConsumption(rs
						.getString("avg_read"));
				meterReplacementDetail.setLastUpdatedByID(rs
						.getString("last_updt_by"));
				meterReplacementDetail.setLastUpdateDate(rs
						.getString("last_updt_dttm"));
				meterReplacementDetail.setMeterReplacementDetailRemark(rs
						.getString("remarks"));
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
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * Retrieve Last Active Meter Details corresponding to kno.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param kno
	 * @return Last Active Meter Details corresponding to kno
	 */
	public static Map<String, String> getLastActiveMeterDetails(String kno) {
		AppLog.begin();
		Map<String, String> returnMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = QueryContants.getLastActiveMeterDetailsQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query::" + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, kno);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put("READ_DTTM",
						(null == rs.getString("READ_DTTM") ? "NA" : rs
								.getString("READ_DTTM")));
				returnMap.put("REG_READING", (null == rs
						.getString("REG_READING") ? "NA" : rs
						.getString("REG_READING")));
				returnMap.put("READER_REM_CD", (null == rs
						.getString("READER_REM_CD") ? " " : rs
						.getString("READER_REM_CD")));
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
	 * Retrieve Current/ Existing Meter Read Details for a consumer.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean save Meter Replacement Detail
	 */
	public static MeterReplacementDetail getMeterReadDetailsForConsumer(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants
					.getCurrentMeterReadDetailsQuery());
			int i = 0;
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			rs = ps.executeQuery();
			if (rs.next()) {
				meterReplacementDetail.setPreBillStatID(rs
						.getString("cons_pre_bill_stat_id"));
				meterReplacementDetail.setCurrentMeterReadDate(rs
						.getString("bill_gen_date"));
				meterReplacementDetail.setCurrentMeterRegisterRead(rs
						.getDouble("reg_reading"));
				meterReplacementDetail.setConsumerType(rs
						.getString("READ_TYPE_FLG"));
				meterReplacementDetail.setCurrentMeterReadRemarkCode(rs
						.getString("reader_rem_cd"));
				meterReplacementDetail.setNewAverageConsumption(rs
						.getDouble("new_avg_read"));
				meterReplacementDetail.setNoOfFloors(rs
						.getString("new_no_of_floors"));
				meterReplacementDetail.setCurrentAverageConsumption(rs
						.getString("avg_read"));
				meterReplacementDetail.setLastUpdatedByID(rs
						.getString("last_updt_by"));
				meterReplacementDetail.setLastUpdateDate(rs
						.getString("last_updt_dttm"));
				meterReplacementDetail.setMeterReplacementDetailRemark(rs
						.getString("remarks"));
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
		AppLog.end();
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * Retrieve Current/ Existing Meter Replacement Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean save Meter Replacement Detail
	 */
	public static MeterReplacementDetail getMeterReplacementDetailsForConsumer(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants
					.getCurrentMeterReplacementDetailQuery());
			int i = 0;
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			rs = ps.executeQuery();
			if (rs.next()) {
				meterReplacementDetail.setWaterConnectionUse(rs
						.getString("WCONUSE"));
				meterReplacementDetail.setWaterConnectionSize(rs
						.getString("WCONSIZE"));
				meterReplacementDetail.setManufacturer(rs.getString("MFG_CD"));
				meterReplacementDetail.setModel(rs.getString("MODEL_CD"));
				meterReplacementDetail.setMeterType(rs.getString("MTRTYP"));
				meterReplacementDetail.setBadgeNo(rs.getString("BADGE_NBR"));
				meterReplacementDetail.setSaType(rs.getString("SA_TYPE_CD"));
				meterReplacementDetail.setMeterReaderName(rs
						.getString("MTR_READER_ID"));
				meterReplacementDetail.setMeterInstalDate(rs
						.getString("MTR_INSTALL_DATE"));
				meterReplacementDetail.setZeroReadRemarkCode(rs
						.getString("MTR_START_READ_REMARK"));
				meterReplacementDetail.setMeterReplaceStageID(rs
						.getString("MTR_RPLC_STAGE_ID"));
				meterReplacementDetail.setZeroRead(rs
						.getDouble("MTR_START_READ"));
				meterReplacementDetail.setOldSAType(rs
						.getString("OLD_SA_TYPE_CD"));
				meterReplacementDetail.setOldSAStartDate(rs
						.getString("OLD_SA_START_DATE"));
				meterReplacementDetail.setOldMeterReadRemarkCode(rs
						.getString("READER_REM_CD"));
				meterReplacementDetail.setOldMeterReadDate(rs
						.getString("OLD_MTR_READ_DATE"));
				meterReplacementDetail.setOldMeterRegisterRead(rs
						.getDouble("OLD_MTR_READ"));
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("OLDAVGCONS"));
				meterReplacementDetail.setCreatedByID(rs
						.getString("CREATED_BY"));
				meterReplacementDetail.setCreateDate(rs
						.getString("CREATE_DTTM"));
				meterReplacementDetail.setLastUpdatedByID(rs
						.getString("LAST_UPDT_BY"));
				meterReplacementDetail.setLastUpdateDate(rs
						.getString("LAST_UPDT_DTTM"));
				// Chnage by Rajib as per Jtrac DJB-2024 for coloumn
				// TPVENDOR_name on 03-01-2014
				meterReplacementDetail.setVendorName(rs
						.getString("TP_VENDOR_NAME"));
				// Chnage End by Rajib as per Jtrac DJB-2024 for coloumn
				// TPVENDOR_name on 03-01-2014
				meterReplacementDetail
						.setMeterReplacementDetailRemark(" by DE Code version "
								+ rs.getString("DE_CODE_VRSN"));
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
		AppLog.end();
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * Retrieve Model corresponding to manufacturer for drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param manufacturer
	 * @return Model Map corresponding to manufacturer
	 */
	public static Map<String, String> getModel(String manufacturer) {
		AppLog.begin();
		Map<String, String> modelMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = QueryContants.getMeterModelQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query::" + query);
			// System.out.println("query::" + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, manufacturer);
			rs = ps.executeQuery();
			while (rs.next()) {
				modelMap.put(rs.getString("MODEL_CD").trim(), rs.getString(
						"DESCR").trim());
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
		return modelMap;
	}

	/**
	 * <p>
	 * Retrieve Old Meter Remark Code List for drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param
	 * @return Old Meter Remark Code List
	 */
	public static List<String> getOldMeterRemarkCodeList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> returnList = new ArrayList<String>();
		try {

			String query = QueryContants.getOldMeterRemarkCodeListQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query::" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnList.add(rs.getString("MTR_STATS_CD"));
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
		return returnList;
	}

	// /**
	// * <p>
	// * reset Meter Read Detail in the Data base.
	// * </p>
	// *
	// * @author Matiur Rahman(Tata Consultancy Services)
	// * @since 15-03-2013
	// * @param meterReplacementDetail
	// * @return boolean Reset Meter Replacement Detail
	// */
	// public static boolean resetMeterReadDetail(
	// MeterReplacementDetail meterReplacementDetail) {
	// AppLog.begin();
	// boolean isSuccess = false;
	// try {
	// ConsumerDetail consumerDetail = new ConsumerDetail();
	// consumerDetail.setUpdatedBy(meterReplacementDetail
	// .getLastUpdatedByID());
	// consumerDetail.setKno(meterReplacementDetail.getKno());
	// consumerDetail.setBillRound(meterReplacementDetail.getBillRound());
	// MeterReadDetails currentMeterReadDetails = new MeterReadDetails();
	// currentMeterReadDetails.setMeterReadDate("");
	// currentMeterReadDetails.setMeterStatus("");
	// currentMeterReadDetails.setReadType(null);
	// currentMeterReadDetails.setRegRead("");
	// currentMeterReadDetails.setAvgRead("");
	// consumerDetail.setCurrentMeterRead(currentMeterReadDetails);
	// HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String,
	// ConsumerDetail>();
	// toUpdateMap.put("consumerDetail", consumerDetail);
	// SetterDAO setterDAO = new SetterDAO();
	// HashMap<String, String> returnMap = (HashMap<String, String>) setterDAO
	// .resetMeterRead(toUpdateMap);
	// String isSuccessReset = (String) returnMap.get("isSuccess");
	// // String recordUpdatedMsg = (String) returnMap
	// // .get("recordUpdatedMsg");
	// // System.out.println("isSuccessReset::" + isSuccessReset
	// // + "::recordUpdatedMsg::" + recordUpdatedMsg);
	// if ("Y".equals(isSuccessReset)) {
	// isSuccess = true;
	// }
	// } catch (Exception e) {
	// AppLog.error(e);
	// }
	// AppLog.end();
	// return isSuccess;
	// }

	/**
	 * <p>
	 * Retrieve Old SA Type Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 13-03-2013
	 * @param kno
	 * @return SA Details
	 */
	public static SADetails getOldSATypeDetails(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SADetails saDetails = new SADetails();
		try {

			String query = QueryContants.getOldSATypeDetailsQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, kno);
			rs = ps.executeQuery();
			while (rs.next()) {
				saDetails.setSaTypeCode(rs.getString("SA_TYPE_CD").trim());
				saDetails.setSaStartDate(rs.getString("START_DT").trim());
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
		return saDetails;
	}

	/**
	 * <p>
	 * Insert Meter Replacement Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean save Meter Replacement Detail
	 */
	public static boolean insertMeterReplacementDetails(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordUpdated = 0;
		try {
			// AppLog.info("In insertMeterReplacementDetail::MeterReaderName::"
			// + meterReplacementDetail.getMeterReaderName()
			// + "Bill Round::" + meterReplacementDetail.getBillRound()
			// + "::Zone::" + meterReplacementDetail.getZone()
			// + "::MrNo::" + meterReplacementDetail.getMrNo()
			// + "::Area::" + meterReplacementDetail.getArea() + "::KNO::"
			// + meterReplacementDetail.getKno() + "::Name::"
			// + meterReplacementDetail.getName() + "::consumerCategory::"
			// + meterReplacementDetail.getConsumerCategory()
			// + "::meterReplaceStageID::"
			// + meterReplacementDetail.getMeterReplaceStageID()
			// + "::WaterConnectionUse::"
			// + meterReplacementDetail.getWaterConnectionUse()
			// + "::WaterConnectionSize::"
			// + meterReplacementDetail.getWaterConnectionSize()
			// + "::LastActiveMeterInstalDate::"
			// + meterReplacementDetail.getLastActiveMeterInstalDate()
			// + "::Manufacturer::"
			// + meterReplacementDetail.getManufacturer() + "::Model::"
			// + meterReplacementDetail.getModel() + "::MeterType::"
			// + meterReplacementDetail.getMeterType() + "::BadgeNo::"
			// + meterReplacementDetail.getBadgeNo() + "::SaType::"
			// + meterReplacementDetail.getSaType()
			// + "::MeterInstalDate::"
			// + meterReplacementDetail.getMeterInstalDate()
			// + "::ZeroReadRemarkCode::"
			// + meterReplacementDetail.getZeroReadRemarkCode()
			// + "::ZeroRead::" + meterReplacementDetail.getZeroRead()
			// + "::OldSAType::" + meterReplacementDetail.getOldSAType()
			// + "::OldSAStartDate::"
			// + meterReplacementDetail.getOldSAStartDate()
			// + "::OldMeterReadRemarkCode::"
			// + meterReplacementDetail.getOldMeterReadRemarkCode()
			// + "::OldMeterReadDate::"
			// + meterReplacementDetail.getOldMeterReadDate()
			// + "::OldMeterRegisterRead::"
			// + meterReplacementDetail.getOldMeterRegisterRead()
			// + "::OldMeterAverageRead::"
			// + meterReplacementDetail.getOldMeterAverageRead());

			conn = DBConnector.getConnection();
			int i = 0;
			// System.out.println("::Insert Query::"
			// + QueryContants.insertMeterReplacementDetailQuery());
			ps = conn.prepareStatement(QueryContants
					.insertMeterReplacementDetailQuery());
			ps.setString(++i, meterReplacementDetail.getZone());
			ps.setString(++i, meterReplacementDetail.getMrNo());
			ps.setString(++i, meterReplacementDetail.getArea());

			ps.setString(++i, meterReplacementDetail.getZone());
			ps.setString(++i, meterReplacementDetail.getMrNo());
			ps.setString(++i, meterReplacementDetail.getArea());
			ps.setString(++i, meterReplacementDetail.getWcNo());
			ps.setString(++i, meterReplacementDetail.getName());
			ps.setString(++i, meterReplacementDetail.getConsumerCategory());
			ps.setString(++i, meterReplacementDetail.getMeterReplaceStageID());
			ps.setString(++i, meterReplacementDetail.getWaterConnectionSize());
			ps.setString(++i, meterReplacementDetail.getWaterConnectionUse());
			ps.setString(++i, meterReplacementDetail.getSaType());
			ps.setString(++i, meterReplacementDetail.getMeterReaderName());
			ps.setString(++i, meterReplacementDetail.getMeterType());
			ps.setString(++i, meterReplacementDetail.getMeterInstalDate());
			ps.setString(++i, meterReplacementDetail
					.getOldMeterReadRemarkCode());
			ps.setString(++i, meterReplacementDetail.getBadgeNo());
			ps.setString(++i, meterReplacementDetail.getManufacturer());
			ps.setString(++i, meterReplacementDetail.getModel());
			ps.setDouble(++i, meterReplacementDetail.getZeroRead());
			ps.setString(++i, meterReplacementDetail.getZeroReadRemarkCode());
			ps.setString(++i, meterReplacementDetail.getIsLNTServiceProvider());
			ps.setString(++i, meterReplacementDetail.getOldSAType());
			ps.setString(++i, meterReplacementDetail.getOldSAStartDate());
			ps.setString(++i, meterReplacementDetail.getOldMeterReadDate());
			ps.setDouble(++i, meterReplacementDetail.getOldMeterRegisterRead());
			ps.setDouble(++i, meterReplacementDetail.getOldMeterAverageRead());
			ps.setString(++i, meterReplacementDetail.getLastUpdatedByID());
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			// System.out.println("Inside DAO,Badge No:"+meterReplacementDetail.getBadgeNo());
			/**
			 * Start:21-03-2013 Matiur Rahman Added for populating Operation
			 * Area Code OP_AREA_CD
			 */
			ps.setString(++i, meterReplacementDetail.getKno());
			/**
			 * End:21-03-2013 Matiur Rahman Added for populating Operation Area
			 * Code OP_AREA_CD
			 */
			/**
			 * Start:03-01-2014 Rajib Hazarika Added for populating Company Name
			 * Company name code "TP_VENDOR_NAME"
			 */
			ps.setString(++i, meterReplacementDetail.getVendorName());
			/**
			 * End:03-01-2014 Rajib Hazarika Added for populating Company Name
			 * Code TP_VENDOR_NAME
			 */
			// System.out.println("Inside DAO,Company Name:"+meterReplacementDetail.getVendorName());
			recordUpdated = ps.executeUpdate();
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
		return (recordUpdated > 0);
	}

	/**
	 *<p>
	 * This method is used to reset Meter Replacement Detail in the Data base.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean Reset Meter Replacement Detail
	 */
	public static boolean resetMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		try {
			conn = DBConnector.getConnection();
			// int recordUpdated = 0;
			ps = conn.prepareStatement(QueryContants
					.resetMeterReplacementDetailQuery());
			int i = 0;
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			/* recordUpdated = */ps.executeUpdate();
			// System.out.println("record deleted ::" + recordUpdated);
			// if (recordUpdated > 0) {
			isSuccess = true;
			// }
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
	 * This method is used to save Meter Replacement Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean save Meter Replacement Detail
	 */
	public static boolean saveMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordUpdated = 0;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants
					.getCurrentMeterReplacementDetailQuery());
			int i = 0;
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());

			// System.out.println("INSide SavemeterRepalcement:Badge No:"+meterReplacementDetail.getBadgeNo());
			rs = ps.executeQuery();
			if (rs.next()) {
				return updateMeterReplacementDetail(meterReplacementDetail);
			} else {
				return insertMeterReplacementDetails(meterReplacementDetail);

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
		AppLog.end();
		return (recordUpdated > 0);
	}

	/**
	 * <p>
	 * This method is used to update Meter Replacement Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * 
	 * @param meterReplacementDetail
	 * @return boolean save Meter Replacement Detail
	 */
	public static boolean updateMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordUpdated = 0;
		try {
			// AppLog.info("In updateMeterReplacementDetail::MeterReaderName::"
			// + meterReplacementDetail.getMeterReaderName()
			// + "Bill Round::" + meterReplacementDetail.getBillRound()
			// + "::Zone::" + meterReplacementDetail.getZone()
			// + "::MrNo::" + meterReplacementDetail.getMrNo()
			// + "::Area::" + meterReplacementDetail.getArea() + "::KNO::"
			// + meterReplacementDetail.getKno() + "::Name::"
			// + meterReplacementDetail.getName() + "::consumerCategory::"
			// + meterReplacementDetail.getConsumerCategory()
			// + "::meterReplaceStageID::"
			// + meterReplacementDetail.getMeterReplaceStageID()
			// + "::WaterConnectionUse::"
			// + meterReplacementDetail.getWaterConnectionUse()
			// + "::WaterConnectionSize::"
			// + meterReplacementDetail.getWaterConnectionSize()
			// + "::LastActiveMeterInstalDate::"
			// + meterReplacementDetail.getLastActiveMeterInstalDate()
			// + "::Manufacturer::"
			// + meterReplacementDetail.getManufacturer() + "::Model::"
			// + meterReplacementDetail.getModel() + "::MeterType::"
			// + meterReplacementDetail.getMeterType() + "::BadgeNo::"
			// + meterReplacementDetail.getBadgeNo() + "::SaType::"
			// + meterReplacementDetail.getSaType()
			// + "::MeterInstalDate::"
			// + meterReplacementDetail.getMeterInstalDate()
			// + "::ZeroReadRemarkCode::"
			// + meterReplacementDetail.getZeroReadRemarkCode()
			// + "::ZeroRead::" + meterReplacementDetail.getZeroRead()
			// + "::OldSAType::" + meterReplacementDetail.getOldSAType()
			// + "::OldSAStartDate::"
			// + meterReplacementDetail.getOldSAStartDate()
			// + "::OldMeterReadRemarkCode::"
			// + meterReplacementDetail.getOldMeterReadRemarkCode()
			// + "::OldMeterReadDate::"
			// + meterReplacementDetail.getOldMeterReadDate()
			// + "::OldMeterRegisterRead::"
			// + meterReplacementDetail.getOldMeterRegisterRead()
			// + "::OldMeterAverageRead::"
			// + meterReplacementDetail.getOldMeterAverageRead());

			conn = DBConnector.getConnection();
			int i = 0;
			ps = conn.prepareStatement(QueryContants
					.updateMeterReplacementDetailQuery());
			ps.setString(++i, meterReplacementDetail.getZone());
			ps.setString(++i, meterReplacementDetail.getMrNo());
			ps.setString(++i, meterReplacementDetail.getArea());
			ps.setString(++i, meterReplacementDetail.getWcNo());
			ps.setString(++i, meterReplacementDetail.getName());
			ps.setString(++i, meterReplacementDetail.getConsumerCategory());
			ps.setString(++i, meterReplacementDetail.getMeterReplaceStageID());
			ps.setString(++i, meterReplacementDetail.getWaterConnectionSize());
			ps.setString(++i, meterReplacementDetail.getWaterConnectionUse());
			ps.setString(++i, meterReplacementDetail.getSaType());
			ps.setString(++i, meterReplacementDetail.getMeterReaderName());
			ps.setString(++i, meterReplacementDetail.getMeterType());
			ps.setString(++i, meterReplacementDetail.getMeterInstalDate());
			ps.setString(++i, meterReplacementDetail
					.getOldMeterReadRemarkCode());
			ps.setString(++i, meterReplacementDetail.getBadgeNo());
			ps.setString(++i, meterReplacementDetail.getManufacturer());
			ps.setString(++i, meterReplacementDetail.getModel());
			ps.setDouble(++i, meterReplacementDetail.getZeroRead());
			ps.setString(++i, meterReplacementDetail.getZeroReadRemarkCode());
			ps.setString(++i, meterReplacementDetail.getIsLNTServiceProvider());
			ps.setString(++i, meterReplacementDetail.getOldSAType());
			ps.setString(++i, meterReplacementDetail.getOldSAStartDate());
			ps.setString(++i, meterReplacementDetail.getOldMeterReadDate());
			ps.setDouble(++i, meterReplacementDetail.getOldMeterRegisterRead());
			ps.setDouble(++i, meterReplacementDetail.getOldMeterAverageRead());
			ps.setString(++i, meterReplacementDetail.getLastUpdatedByID());
			/**
			 * Start:21-03-2013 Matiur Rahman Added for populating Operation
			 * Area Code OP_AREA_CD
			 */
			ps.setString(++i, meterReplacementDetail.getKno());
			/**
			 * End:21-03-2013 Matiur Rahman Added for populating Operation Area
			 * Code OP_AREA_CD
			 */
			ps.setString(++i, meterReplacementDetail.getKno());
			ps.setString(++i, meterReplacementDetail.getBillRound());
			recordUpdated = ps.executeUpdate();
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
		return (recordUpdated > 0);
	}
}
