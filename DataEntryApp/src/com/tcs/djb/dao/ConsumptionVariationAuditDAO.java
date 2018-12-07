/************************************************************************************************************
 * @(#) ConsumptionVariationAuditDAO.java   09 Mar 2016
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
import java.util.List;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.ConsumptionAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Consumption Variation Audit.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @history 09-03-2016 Atanu Added new method as per jTrac ANDROID-293
 *          {@link #saveAuditFindings()}
 * 
 * 
 */
public class ConsumptionVariationAuditDAO {

	/**
	 * <p>
	 * This method is used to get Consumer Details for consumption audit.
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	public static ArrayList<MeterReadImgAuditDetails> getConsumerDetailsListForConsumptionAudit(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		MeterReadImgAuditDetails meterReadImgAuditDetails = null;
		List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList = new ArrayList<MeterReadImgAuditDetails>();
		try {
			String query = QueryContants
					.getRecordDetailsListForConsumptionAudit(consumptionAuditSearchCriteria);
			AppLog.info("getRecordDetailsListForConsumptionAuditQuery::"
					+ query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != consumptionAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchZROCode());

			}
			if (null != consumptionAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchKno())) {
				ps
						.setString(++i, consumptionAuditSearchCriteria
								.getSearchKno());
			}
			if (null != consumptionAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchBillRound())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchBillRound());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarAnualAvgConsumption()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarAnualAvgConsumption())) {
				String searchVarAnualAvgConsumption = consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption();
				/* ABOVE 200 Logic Here */
				if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
						.equalsIgnoreCase(searchVarAnualAvgConsumption)) {
					ps.setString(++i, "200");

				} else {
					List<String> consumptionVariationAuditStatusList = Arrays
							.asList(searchVarAnualAvgConsumption
									.split("\\s*-\\s*"));
					AppLog.info("consumptionVariationAuditStatusList.get(0)"
							+ consumptionVariationAuditStatusList.get(0));
					AppLog.info("\nconsumptionVariationAuditStatusList.get(1)"
							+ consumptionVariationAuditStatusList.get(1));
					ps.setString(++i, consumptionVariationAuditStatusList
							.get(0));
					ps.setString(++i, consumptionVariationAuditStatusList
							.get(1));
				}

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarPrevReading()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarPrevReading())) {
				String searchVarPrevReading = consumptionAuditSearchCriteria
						.getSearchVarPrevReading();
				if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
						.equalsIgnoreCase(searchVarPrevReading)) {
					ps.setString(++i, "200");
				} else {
					List<String> consumptionVarPrevReadingsList = Arrays
							.asList(searchVarPrevReading.split("\\s*-\\s*"));
					AppLog.info("consumptionVarPrevReadingsList.get(0)"
							+ consumptionVarPrevReadingsList.get(0));
					AppLog.info("\nconsumptionVarPrevReadingsList.get(1)"
							+ consumptionVarPrevReadingsList.get(1));
					ps.setString(++i, consumptionVarPrevReadingsList.get(0));
					ps.setString(++i, consumptionVarPrevReadingsList.get(1));
				}

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchLastAuditedBeforeDate()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchLastAuditedBeforeDate())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchConsumptionVariationAuditStatus()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())
					&& !"ALL".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReadImgAuditDetails = new MeterReadImgAuditDetails();
				meterReadImgAuditDetails.setSeqNo(rs.getString("SEQ_NO"));
				meterReadImgAuditDetails.setKno(rs.getString("KNO"));
				meterReadImgAuditDetails.setBillRound(rs
						.getString("BILL_ROUND"));
				meterReadImgAuditDetails.setMtrNo(rs.getString("MTR_NO"));
				meterReadImgAuditDetails.setPerVariationAvgCnsumptn(rs
						.getString("ANUAL_VARIATION"));
				meterReadImgAuditDetails.setPerVariationPrevusReadng(rs
						.getString("PREV_VARIATION"));
				meterReadImgAuditDetails.setLastMtrReadng(rs
						.getString("LAST_MTR_READ"));
				meterReadImgAuditDetails.setCurrMtrReadng(rs
						.getString("CURR_MTR_READ"));
				meterReadImgAuditDetails.setVariationPrevusReadng(rs
						.getString("VARIATION_PREV_READ"));
				meterReadImgAuditDetails.setLastAuditDate(rs
						.getString("LAST_AUDIT_DT"));
				meterReadImgAuditDetails.setLastAuditStatus(rs
						.getString("LAST_AUDIT_STATUS"));
				meterReadImgAuditDetails.setLastAuditRmrk(rs
						.getString("LAST_AUDIT_RMRK"));
				meterReadImgAuditDetails.setSiteVistRequrd(rs
						.getString("SITE_VIST_REQURD"));
				meterReadImgAuditDetails.setAnnualAvgConsumption(rs
						.getString("ANUAL_AVG_CNSUMPTN"));
				meterReadImgAuditDetails.setAssignTo(rs.getString("ASSIGN_TO"));
				if (null != rs.getString("ASSIGN_TO_FLG")) {
					meterReadImgAuditDetails.setAssignToFlg(rs
							.getString("ASSIGN_TO_FLG"));
				} else {
					meterReadImgAuditDetails.setAssignToFlg("NA");
				}

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
	 * This method is used to get detailed records for Kno Audit
	 * </p>
	 * 
	 * @param kno
	 * @return
	 */
	public static List<MeterReadImgAuditDetails> getKnoAuditDetailRecord(
			String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		MeterReadImgAuditDetails aMeterReadImgAuditDetailsObj = null;
		List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList = new ArrayList<MeterReadImgAuditDetails>();
		try {
			String query = QueryContants
					.getKnoAuditDetailListForConsumptionAudit();
			AppLog.info("getKnoAuditDetailListForConsumptionAuditQuery::"
					+ query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != kno && !"".equalsIgnoreCase(kno)) {
				AppLog.info("Setting Kno in Query : " + kno);
				ps.setString(++i, kno);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				aMeterReadImgAuditDetailsObj = new MeterReadImgAuditDetails();
				aMeterReadImgAuditDetailsObj.setBillRound((null != rs
						.getString("BILL_ROUND") ? rs.getString("BILL_ROUND")
						: "NA"));
				aMeterReadImgAuditDetailsObj.setBillingPeriod(null != rs
						.getString("BILLNG_PERIOD") ? rs
						.getString("BILLNG_PERIOD") : "NA");
				aMeterReadImgAuditDetailsObj.setBillGeneratedBy(null != rs
						.getString("BILL_GENRATD_BY") ? rs
						.getString("BILL_GENRATD_BY") : "NA");
				aMeterReadImgAuditDetailsObj.setBillDt(null != rs
						.getString("BILL_DT") ? rs.getString("BILL_DT") : "NA");
				aMeterReadImgAuditDetailsObj.setMeterReadRemark(null != rs
						.getString("MTR_READ_REMRK") ? rs
						.getString("MTR_READ_REMRK") : "NA");
				aMeterReadImgAuditDetailsObj.setConsumption(null != rs
						.getString("CONSUMPTION") ? rs.getString("CONSUMPTION")
						: "NA");
				aMeterReadImgAuditDetailsObj
						.setVariationPrevusReadng(null != rs
								.getString("VARIATION_PREVUS_READNG") ? rs
								.getString("VARIATION_PREVUS_READNG") : "NA");
				aMeterReadImgAuditDetailsObj
						.setPerVariationAvgCnsumptn(null != rs
								.getString("ANUAL_VARIATION_AVG_CNSUMPTN") ? rs
								.getString("ANUAL_VARIATION_AVG_CNSUMPTN")
								: "NA");
				aMeterReadImgAuditDetailsObj.setBillAmt(null != rs
						.getString("BILL_AMOUNT") ? rs.getString("BILL_AMOUNT")
						: "NA");
				aMeterReadImgAuditDetailsObj.setPaymentAmount(null != rs
						.getString("PAYMENT_AMOUNT") ? rs
						.getString("PAYMENT_AMOUNT") : "NA");

				meterReadImgAuditDetailsList.add(aMeterReadImgAuditDetailsObj);
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
	 * This method is used to get previous bill rounds
	 * </p>
	 * 
	 * @return
	 */
	public static ArrayList<String> getPreviousBillRounds() {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> previousBillRoundList = new ArrayList<String>();
		try {
			String query = QueryContants.getPreviousBillRoundQuery();
			AppLog.info("getPreviousBillRoundsQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String previousBillRound = rs.getString("PREV_BILL_ROUND");
				previousBillRoundList.add(previousBillRound);
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
		return (ArrayList<String>) previousBillRoundList;
	}

	/**
	 * <p>
	 * This method is used to get Searched result list
	 * </p>
	 * 
	 * @param loggedInId
	 * @return
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static List<String> getSearchedResultList(String loggedInId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		List<String> searchedResultList = new ArrayList<String>();

		try {
			String query = QueryContants.getSearchedResultListQuery();
			AppLog.info("getSearchedResultListQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != loggedInId && !"".equalsIgnoreCase(loggedInId)) {
				AppLog.info("Setting loggedInId in Query : " + loggedInId);
				ps.setString(++i, loggedInId);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				String result = "{";
				if (null != rs.getString("KNO")) {
					result += "[KNO:" + rs.getString("KNO") + "]";
				}
				if (null != rs.getString("BILL_ROUND")) {
					result += "[BILL_ROUND:" + rs.getString("BILL_ROUND") + "]";
				}
				if (null != rs.getString("ZRO_LOCATION")) {
					result += "[ZRO:" + rs.getString("ZRO_LOCATION") + "]";
				}
				if (null != rs.getString("PER_VARIATION_AVG_CNSUMPTN")) {
					result += "[AVG_CONSUMPTION:"
							+ rs.getString("PER_VARIATION_AVG_CNSUMPTN") + "]";
				}
				if (null != rs.getString("PER_VARIATION_PREVUS_READNG")) {
					result += "[VARIATION_PREVIOUS_ROUND:"
							+ rs.getString("PER_VARIATION_PREVUS_READNG") + "]";
				}
				if (null != rs.getString("LAST_AUDIT_DATE")) {
					result += "[LAST_AUDITED:"
							+ rs.getString("LAST_AUDIT_DATE") + "]";
				}
				if (null != rs.getString("AUDIT_STATUS")) {
					result += "[AUDIT_STATUS:" + rs.getString("AUDIT_STATUS")
							+ "]";
				}
				result += "}";
				AppLog.info(result);
				searchedResultList.add(result);
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
		return searchedResultList;
	}

	/**
	 * <p>
	 * This method is used to get Searched record count
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static int getSearchRecordCount(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfSearchHistoryRecords(consumptionAuditSearchCriteria);
			AppLog.info("Query For Consumption Variation Audit Records ::"
					+ query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != consumptionAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchKno())) {
				ps
						.setString(++i, consumptionAuditSearchCriteria
								.getSearchKno());
			}
			if (null != consumptionAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchBillRound())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchBillRound());
			}
			if (null != consumptionAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchZROCode());

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarAnualAvgConsumption()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarAnualAvgConsumption())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarPrevReading()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarPrevReading())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchVarPrevReading());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchLastAuditedBeforeDate()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchLastAuditedBeforeDate())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchConsumptionVariationAuditStatus()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus());
			}
			if (null != consumptionAuditSearchCriteria.getUserId()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getUserId())) {
				ps.setString(++i, consumptionAuditSearchCriteria.getUserId());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
			}
			AppLog.info("Found Records For Consumption Variation Audit::"
					+ totalRecords);
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
	 * This method is used to get total count of Audit Details Lists
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static int getTotalCountOfAuditDetailsList(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfConsumptionVariationAuditDetailsListQuery(consumptionAuditSearchCriteria);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);

			if (null != consumptionAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchZROCode());

			}
			if (null != consumptionAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchKno())) {
				ps
						.setString(++i, consumptionAuditSearchCriteria
								.getSearchKno());
			}
			if (null != consumptionAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchBillRound())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchBillRound());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarAnualAvgConsumption()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarAnualAvgConsumption())) {
				String searchVarAnualAvgConsumption = consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption();
				/* ABOVE 200 Logic Here */
				if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
						.equalsIgnoreCase(searchVarAnualAvgConsumption)) {
					ps.setString(++i, "200");

				} else {
					List<String> consumptionVariationAuditStatusList = Arrays
							.asList(searchVarAnualAvgConsumption
									.split("\\s*-\\s*"));
					AppLog.info("consumptionVariationAuditStatusList.get(0)"
							+ consumptionVariationAuditStatusList.get(0));
					AppLog.info("\nconsumptionVariationAuditStatusList.get(1)"
							+ consumptionVariationAuditStatusList.get(1));
					ps.setString(++i, consumptionVariationAuditStatusList
							.get(0));
					ps.setString(++i, consumptionVariationAuditStatusList
							.get(1));
				}

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarPrevReading()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarPrevReading())) {
				String searchVarPrevReading = consumptionAuditSearchCriteria
						.getSearchVarPrevReading();
				if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
						.equalsIgnoreCase(searchVarPrevReading)) {
					ps.setString(++i, "200");
				} else {
					List<String> consumptionVarPrevReadingsList = Arrays
							.asList(searchVarPrevReading.split("\\s*-\\s*"));
					AppLog.info("consumptionVarPrevReadingsList.get(0)"
							+ consumptionVarPrevReadingsList.get(0));
					AppLog.info("\nconsumptionVarPrevReadingsList.get(1)"
							+ consumptionVarPrevReadingsList.get(1));
					ps.setString(++i, consumptionVarPrevReadingsList.get(0));
					ps.setString(++i, consumptionVarPrevReadingsList.get(1));
				}

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchLastAuditedBeforeDate()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchLastAuditedBeforeDate())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchConsumptionVariationAuditStatus()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())
					&& !"ALL".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
			}
			AppLog.info("Found Records For Consumption Audit::" + totalRecords);
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
	 * This method is used to insert searched records
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static void insertSearchRecord(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String query = QueryContants
					.insertSearchHistoryRecords(consumptionAuditSearchCriteria);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != consumptionAuditSearchCriteria.getSearchKno()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchKno())) {
				ps
						.setString(++i, consumptionAuditSearchCriteria
								.getSearchKno());
			}
			if (null != consumptionAuditSearchCriteria.getSearchBillRound()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchBillRound())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchBillRound());
			}
			if (null != consumptionAuditSearchCriteria.getSearchZROCode()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchZROCode())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchZROCode());

			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarAnualAvgConsumption()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarAnualAvgConsumption())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchVarPrevReading()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarPrevReading())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchVarPrevReading());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchLastAuditedBeforeDate()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchLastAuditedBeforeDate())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate());
			}
			if (null != consumptionAuditSearchCriteria
					.getSearchConsumptionVariationAuditStatus()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchConsumptionVariationAuditStatus())) {
				ps.setString(++i, consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus());
			}
			if (null != consumptionAuditSearchCriteria.getUserId()
					&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
							.getUserId())) {
				ps.setString(++i, consumptionAuditSearchCriteria.getUserId());
			}
			ps.executeUpdate();
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
	 * This method is used to save audit findings
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static int saveAuditFindings(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getUpdateAuditFindingsQuery(meterReadImgAuditDetails);
			AppLog.info("getUpdateAuditFindingsQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReadImgAuditDetails.getNonSatsfctryReadngReasn()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getNonSatsfctryReadngReasn().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getNonSatsfctryReadngReasn());
			}
			if (null != meterReadImgAuditDetails.getSatsfctryReadngReasn()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getSatsfctryReadngReasn().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getSatsfctryReadngReasn());
			}
			if (null != meterReadImgAuditDetails.getSugstdAuditAction()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getSugstdAuditAction().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getSugstdAuditAction());
			}
			if (null != meterReadImgAuditDetails.getLastAuditStatus()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getLastAuditStatus().trim())) {
				ps
						.setString(++i, meterReadImgAuditDetails
								.getLastAuditStatus());
			}
			if (null != meterReadImgAuditDetails.getLastUpdatedBy()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getLastUpdatedBy().trim())) {
				ps.setString(++i, meterReadImgAuditDetails.getLastUpdatedBy());
			}
			if (null != meterReadImgAuditDetails.getConsumptnVariatnReasn()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getConsumptnVariatnReasn().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getConsumptnVariatnReasn());
			}
			if (null != meterReadImgAuditDetails.getAuditorName()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getAuditorName().trim())) {
				ps.setString(++i, meterReadImgAuditDetails.getAuditorName());
			}
			ps.setString(++i, meterReadImgAuditDetails.getKno());
			ps.setString(++i, meterReadImgAuditDetails.getBillRound());

			AppLog.info("Applying Audit Finding Update for KNO "
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

	/**
	 * <p>
	 * This method is used to update Kno Audit details status
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static int updateKnoDetailAuditStatus(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getUpdateKnoDetailAuditQuery(meterReadImgAuditDetails);
			AppLog.info("getUpdateKnoDetailAuditQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReadImgAuditDetails.getKno()
					&& null != meterReadImgAuditDetails.getBillRound()) {
				if (null != meterReadImgAuditDetails.getSiteVistGivenBy()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getSiteVistGivenBy())) {
					ps.setString(++i, meterReadImgAuditDetails
							.getSiteVistGivenBy());
				}
				if (null != meterReadImgAuditDetails.getAssignTo()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getAssignTo())
						&& !DJBConstants.KNO_AUDIT_MR_ASSIGNTO
								.equalsIgnoreCase(meterReadImgAuditDetails
										.getAssignTo())) {
					ps.setString(++i, meterReadImgAuditDetails.getAssignTo());
				}
				if (null != meterReadImgAuditDetails.getSiteVistRequrd()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getSiteVistRequrd())) {
					ps.setString(++i, meterReadImgAuditDetails
							.getSiteVistRequrd());
				}
				if (null != meterReadImgAuditDetails.getLastAuditStatus()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getLastAuditStatus())) {
					ps.setString(++i, meterReadImgAuditDetails
							.getLastAuditStatus());
				}
				if (null != meterReadImgAuditDetails.getLastAuditRmrk()
						&& !(DJBConstants.KNO_AUDIT_NO_REMARK
								.equalsIgnoreCase(meterReadImgAuditDetails
										.getLastAuditRmrk()))) {
					ps.setString(++i, meterReadImgAuditDetails
							.getLastAuditRmrk());
				}
				if (null != meterReadImgAuditDetails.getAssignToFlg()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getAssignToFlg())
						&& !"NA".equalsIgnoreCase(meterReadImgAuditDetails
								.getAssignToFlg())) {
					ps
							.setString(++i, meterReadImgAuditDetails
									.getAssignToFlg());
				}
				if (null != meterReadImgAuditDetails.getLastUpdatedBy()
						&& !"".equalsIgnoreCase(meterReadImgAuditDetails
								.getLastUpdatedBy())) {
					ps.setString(++i, meterReadImgAuditDetails
							.getLastUpdatedBy());
				}
				ps.setString(++i, meterReadImgAuditDetails.getKno());
				ps.setString(++i, meterReadImgAuditDetails.getBillRound());
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
