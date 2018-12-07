/************************************************************************************************************
 * @(#) DevChargeMasterDataDAO.java   03 Aug 2015
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.DevChargeMasterDataColonyDetails;
import com.tcs.djb.model.DevChargeMasterDataInterestDetails;
import com.tcs.djb.model.DevChargeMasterDataRateDetails;
import com.tcs.djb.model.DevChargeMasterDataRebateDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.UtilityForAll;

/**
 * <p>
 * DAO class for Dev Charge Master Data Screen, Jtrac DJB-3994.
 * </p>
 * 
 * @author Reshma Srivastava (Tata Consultancy Services)
 * @since 03-08-2015
 * 
 */
public class DevChargeMasterDataDAO {

	/**
	 * <p>
	 * Method to activate colony for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param category
	 * @param userId
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static void activateColony(String colonyId, String category,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			AppLog.info("Colony Activate Procedure::colonyId::" + colonyId
					+ "::category::" + category + "::userId::" + userId);
			if (null != colonyId && !UtilityForAll.isEmpty(colonyId)
					&& null != category && !UtilityForAll.isEmpty(category)
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				conn = DBConnector.getConnection();
				cs = conn.prepareCall(QueryContants.ACTIVATE_COLONY_PROC);
				int i = 0;
				cs.setString(++i, colonyId.trim());
				cs.setString(++i, category.trim());
				cs.setString(++i, userId.trim());
				cs.executeUpdate();
				AppLog
						.info("::Colony Activate Procedure INITIATED::Procedure Name::ACTIVATE_COLONY_PROC");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Method to get the row count if the colony has been activated successfully
	 * or not for Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param userId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static boolean getActivatedColonyRowCount(String colonyId,
			String userId) {
		AppLog.begin();
		int savedRow = 0;
		boolean isSaved = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != colonyId && !UtilityForAll.isEmpty(colonyId)
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				String query = QueryContants.getActivatedColonyRowCountQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				ps.setString(++i, userId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					savedRow = rs.getInt("COUNT");
					if (savedRow > 0) {
						isSaved = true;
					}
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
		return isSaved;
	}

	/**
	 * <p>
	 * Method to get the list of matching colonies for Dev Charge Master Data
	 * Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyName
	 * @param zro
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static ArrayList<DevChargeMasterDataColonyDetails> getColonyList(
			String colonyName, String zro) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails = null;
		ArrayList<DevChargeMasterDataColonyDetails> devChargeMasterDataColonyDetailList = new ArrayList<DevChargeMasterDataColonyDetails>();
		try {
			if (null != colonyName && !UtilityForAll.isEmpty(colonyName)
					&& null != zro && !UtilityForAll.isEmpty(zro)) {
				String query = QueryContants.getColonyListQuery(colonyName
						.trim(), zro.trim());
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					devChargeMasterDataColonyDetails = new DevChargeMasterDataColonyDetails();
					devChargeMasterDataColonyDetails.setColonyId(rs
							.getString("COLONY_ID") == null ? "" : rs
							.getString("COLONY_ID").trim());
					devChargeMasterDataColonyDetails.setColonyName(rs
							.getString("COLONY_NAME") == null ? "" : rs
							.getString("COLONY_NAME").trim());
					devChargeMasterDataColonyDetailList
							.add(devChargeMasterDataColonyDetails);
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
		return devChargeMasterDataColonyDetailList;
	}

	/**
	 * <p>
	 * Method to get list of paramters in key value pair for Dev Charge Master
	 * Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param paramType
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static Map<String, String> getDevChargeListMap(String paramType) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getDevChargeListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, paramType);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("PARAM_KEY").trim(), rs
						.getString("PARAM_VALUE")
						+ "(" + rs.getString("PARAM_KEY") + ")");
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
	 * Method to get the year interval from database for Dev Charge Master Data
	 * Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param paramType
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static String getDevChargeYearInterval(String paramType) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		String yearInterval = null;
		ResultSet rs = null;
		try {
			String query = QueryContants.getDevChargeYearIntervalQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, paramType);
			rs = ps.executeQuery();
			while (rs.next()) {
				yearInterval = rs.getString("PARAM_KEY");
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
		return yearInterval;
	}

	/**
	 * <p>
	 * Method to get the error message from database in case of proc call for
	 * Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param userId
	 * @param procName
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static String getErrorMessage(String colonyId, String userId,
			String procName) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		String errorMessage = null;
		ResultSet rs = null;
		try {
			if (!UtilityForAll.isEmpty(colonyId)
					&& !UtilityForAll.isEmpty(userId)
					&& !UtilityForAll.isEmpty(procName)) {
				String query = QueryContants.getErrorMessageQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				ps.setString(++i, userId.trim());
				ps.setString(++i, procName.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					errorMessage = rs.getString("ERROR_MESSAGE");
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
		AppLog.info("Error Message:::" + errorMessage);
		AppLog.end();
		return errorMessage;
	}

	/**
	 * <p>
	 * Method to get colony details for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataColonyDetails
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static DevChargeMasterDataColonyDetails getMasterDataColonyDetails(
			DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != devChargeMasterDataColonyDetails
					&& !UtilityForAll.isEmpty(devChargeMasterDataColonyDetails
							.getColonyId())) {
				String query = QueryContants
						.getMasterDataColonyDetailsQuery(devChargeMasterDataColonyDetails
								.getColonyId().trim());
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					devChargeMasterDataColonyDetails.setColonyName(rs
							.getString("COLONY_NAME") == null ? "" : rs
							.getString("COLONY_NAME").trim());
					devChargeMasterDataColonyDetails.setCategory(rs
							.getString("COL_CAT_CD") == null ? "" : rs
							.getString("COL_CAT_CD").trim());
					devChargeMasterDataColonyDetails.setChargeType(rs
							.getString("COL_CHRG_TYP") == null ? "" : rs
							.getString("COL_CHRG_TYP").trim());
					// devChargeMasterDataColonyDetails.setNotificationDate(rs
					// .getDate("COL_NOTIFY_DT"));
					devChargeMasterDataColonyDetails
							.setNotificationDate((null == rs
									.getString("COL_NOTIFY_DT")) ? "" : rs
									.getString("COL_NOTIFY_DT").trim());
					devChargeMasterDataColonyDetails.setStatus((null == rs
							.getString("STATUS_FLG")) ? "" : rs.getString(
							"STATUS_FLG").trim());
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
		return devChargeMasterDataColonyDetails;
	}

	/**
	 * <p>
	 * Method to get interest details for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static List<DevChargeMasterDataInterestDetails> getMasterDataInterestDetails(
			String colonyId) {
		AppLog.begin();
		AppLog.info("Colony ID:" + colonyId);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DevChargeMasterDataInterestDetails> devChargeMasterDataInterestDetailsList = new ArrayList<DevChargeMasterDataInterestDetails>();
		try {
			if (!UtilityForAll.isEmpty(colonyId)) {
				String query = QueryContants
						.getMasterDataInterestDetailsQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails = new DevChargeMasterDataInterestDetails();
					devChargeMasterDataInterestDetails.setInterestDocProof(rs
							.getString("INTRST_DOC_PROOF") == null ? "" : rs
							.getString("INTRST_DOC_PROOF").trim());
					// devChargeMasterDataInterestDetails.setInterestEndDt(rs
					// .getDate("INTRST_END_DT"));
					devChargeMasterDataInterestDetails
							.setInterestEndDt((null == rs
									.getString("INTRST_END_DT")) ? "" : rs
									.getString("INTRST_END_DT"));
					devChargeMasterDataInterestDetails.setInterestPercentage(rs
							.getString("INTRST_PRCNTG") == null ? "" : rs
							.getString("INTRST_PRCNTG").trim());
					// devChargeMasterDataInterestDetails.setInterestStartDt(rs
					// .getDate("INTRST_STRT_DT"));
					devChargeMasterDataInterestDetails
							.setInterestStartDt((null == rs
									.getString("INTRST_STRT_DT")) ? "" : rs
									.getString("INTRST_STRT_DT"));
					devChargeMasterDataInterestDetailsList
							.add(devChargeMasterDataInterestDetails);
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
		return devChargeMasterDataInterestDetailsList;
	}

	/**
	 * <p>
	 * Method to get rate details for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static List<DevChargeMasterDataRateDetails> getMasterDataRateDetails(
			String colonyId) {
		AppLog.begin();
		AppLog.info("Colony ID:" + colonyId);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DevChargeMasterDataRateDetails> devChargeMasterDataRateDetailsList = new ArrayList<DevChargeMasterDataRateDetails>();
		try {
			if (!UtilityForAll.isEmpty(colonyId)) {
				String query = QueryContants.getMasterDataRateDetailsQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					DevChargeMasterDataRateDetails devChargeMasterDataRateDetails = new DevChargeMasterDataRateDetails();
					devChargeMasterDataRateDetails.setApplicableRate(rs
							.getString("RATE_PER_SQMT") == null ? "" : rs
							.getString("RATE_PER_SQMT").trim());
					devChargeMasterDataRateDetails.setRateDocProof(rs
							.getString("RATE_DOC_PROOF") == null ? "" : rs
							.getString("RATE_DOC_PROOF").trim());
					// devChargeMasterDataRateDetails.setRateEndDt(rs
					// .getDate("RATE_END_DT"));
					// devChargeMasterDataRateDetails.setRateStartDt(rs
					// .getDate("RATE_STRT_DT"));
					devChargeMasterDataRateDetails.setRateEndDt((null == rs
							.getString("RATE_END_DT")) ? "" : rs
							.getString("RATE_END_DT"));
					devChargeMasterDataRateDetails.setRateStartDt((null == rs
							.getString("RATE_STRT_DT")) ? "" : rs
							.getString("RATE_STRT_DT"));
					devChargeMasterDataRateDetails.setRebateEligibility(rs
							.getString("REBATE_ELIGIBLE_FLG") == null ? "" : rs
							.getString("REBATE_ELIGIBLE_FLG").trim());
					devChargeMasterDataRateDetails.setRateInterestEligibity(rs
							.getString("INTEREST_ELIGIBLE_FLG") == null ? ""
							: rs.getString("INTEREST_ELIGIBLE_FLG").trim());
					devChargeMasterDataRateDetails.setRateTypeCode(rs
							.getString("RATE_TYPE_CD") == null ? "" : rs
							.getString("RATE_TYPE_CD").trim());
					devChargeMasterDataRateDetails.setCustomerClassCode(rs
							.getString("CUST_CL_CD") == null ? "" : rs
							.getString("CUST_CL_CD").trim());
					devChargeMasterDataRateDetails.setRateMinPlotArea(rs
							.getString("MIN_PLOT_AREA") == null ? "" : rs
							.getString("MIN_PLOT_AREA").trim());
					devChargeMasterDataRateDetails.setRateMaxPlotArea(rs
							.getString("MAX_PLOT_AREA") == null ? "" : rs
							.getString("MAX_PLOT_AREA").trim());
					devChargeMasterDataRateDetailsList
							.add(devChargeMasterDataRateDetails);
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
		return devChargeMasterDataRateDetailsList;
	}

	/**
	 * <p>
	 * Method to get rebate details for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static List<DevChargeMasterDataRebateDetails> getMasterDataRebateDetails(
			String colonyId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DevChargeMasterDataRebateDetails> devChargeMasterDataRebateDetailsList = new ArrayList<DevChargeMasterDataRebateDetails>();
		try {
			if (!UtilityForAll.isEmpty(colonyId)) {
				String query = QueryContants.getMasterDataRebateDetailsQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails = new DevChargeMasterDataRebateDetails();
					// devChargeMasterDataRebateDetails.setRebateEndDt(rs
					// .getDate("REBATE_END_DT"));
					devChargeMasterDataRebateDetails.setRebateEndDt((null == rs
							.getString("REBATE_END_DT")) ? "" : rs
							.getString("REBATE_END_DT"));
					devChargeMasterDataRebateDetails.setRebateFlatRate(rs
							.getString("REBATE_FLAT_RATE") == null ? "" : rs
							.getString("REBATE_FLAT_RATE").trim());
					devChargeMasterDataRebateDetails.setRebatePercentage(rs
							.getString("REBATE_PRCNTG") == null ? "" : rs
							.getString("REBATE_PRCNTG").trim());
					// devChargeMasterDataRebateDetails.setRebateStartDt(rs
					// .getDate("REBATE_STRT_DT"));
					devChargeMasterDataRebateDetails
							.setRebateStartDt((null == rs
									.getString("REBATE_STRT_DT")) ? "" : rs
									.getString("REBATE_STRT_DT"));
					devChargeMasterDataRebateDetails.setRebateType(rs
							.getString("REBATE_TYP") == null ? "" : rs
							.getString("REBATE_TYP").trim());
					devChargeMasterDataRebateDetails.setRebateDocProof(rs
							.getString("REBATE_DOC_PROOF") == null ? "" : rs
							.getString("REBATE_DOC_PROOF").trim());
					devChargeMasterDataRebateDetailsList
							.add(devChargeMasterDataRebateDetails);
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
		return devChargeMasterDataRebateDetailsList;
	}

	/**
	 * <p>
	 * Method to get the row count if interest row has been saved successfully
	 * or not for Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param userId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static boolean getSavedInterestRowCount(String colonyId,
			String userId) {
		AppLog.begin();
		int savedRow = 0;
		boolean isSaved = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != colonyId && !UtilityForAll.isEmpty(colonyId)
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				String query = QueryContants.getSavedInterestRowCountQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				ps.setString(++i, userId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					savedRow = rs.getInt("COUNT");
					if (savedRow > 0) {
						isSaved = true;
					}
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
		return isSaved;
	}

	/**
	 * <p>
	 * Method to get the row count if the new colony has been saved successfully
	 * or not for Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataColonyDetails
	 * @param userId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static DevChargeMasterDataColonyDetails getSavedNewColonyId(
			DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != devChargeMasterDataColonyDetails && null != userId
					&& !UtilityForAll.isEmpty(userId)) {
				String query = QueryContants.getSavedNewColonyIdQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, userId.trim());
				ps.setString(++i, (null == devChargeMasterDataColonyDetails
						.getColonyName()) ? ""
						: devChargeMasterDataColonyDetails.getColonyName()
								.trim());
				ps.setString(++i, (null == devChargeMasterDataColonyDetails
						.getSelectedZRO()) ? ""
						: devChargeMasterDataColonyDetails.getSelectedZRO()
								.trim());
				ps.setString(++i, (null == devChargeMasterDataColonyDetails
						.getChargeType()) ? ""
						: devChargeMasterDataColonyDetails.getChargeType()
								.trim());
				ps.setString(++i, (null == devChargeMasterDataColonyDetails
						.getNotificationDate()) ? ""
						: devChargeMasterDataColonyDetails
								.getNotificationDate().trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					devChargeMasterDataColonyDetails.setColonyId(rs
							.getString("COLONY_ID"));
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
		return devChargeMasterDataColonyDetails;
	}

	/**
	 * <p>
	 * Method to get the row count if rate row has been saved successfully or
	 * not for Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param userId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static boolean getSavedRateRowCount(String colonyId, String userId) {
		AppLog.begin();
		int savedRow = 0;
		boolean isSaved = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != colonyId && !UtilityForAll.isEmpty(colonyId)
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				String query = QueryContants.getSavedRateRowCountQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				ps.setString(++i, userId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					savedRow = rs.getInt("COUNT");
					if (savedRow > 0) {
						isSaved = true;
					}
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
		return isSaved;
	}

	/**
	 * <p>
	 * Method to get the row count if rebate row has been saved successfully or
	 * not for Dev Charge Master Data Screen, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param colonyId
	 * @param userId
	 * @return
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static boolean getSavedRebateRowCount(String colonyId, String userId) {
		AppLog.begin();
		int savedRow = 0;
		boolean isSaved = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != colonyId && !UtilityForAll.isEmpty(colonyId)
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				String query = QueryContants.getSavedRebateRowCountQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, colonyId.trim());
				ps.setString(++i, userId.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					savedRow = rs.getInt("COUNT");
					if (savedRow > 0) {
						isSaved = true;
					}
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
		return isSaved;
	}

	/**
	 * <p>
	 * Method to save interest detail row for Dev Charge Master Data Screen,
	 * Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataInterestDetails
	 * @param colonyId
	 * @param userId
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static void saveInterestDetailRow(
			DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails,
			String colonyId, String userId) {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			if (null != devChargeMasterDataInterestDetails && null != userId
					&& !UtilityForAll.isEmpty(userId) && null != colonyId
					&& !UtilityForAll.isEmpty(colonyId)) {
				AppLog
						.info("colonyId:"
								+ colonyId
								+ "\nInterestPercentage:"
								+ devChargeMasterDataInterestDetails
										.getInterestPercentage()
								+ "\nInterestStartDt:"
								+ devChargeMasterDataInterestDetails
										.getInterestStartDt()
								+ "\nInterestEndDt:"
								+ devChargeMasterDataInterestDetails
										.getInterestEndDt());
				conn = DBConnector.getConnection();
				cs = conn.prepareCall(QueryContants.INTEREST_ROW_SAVE_PROC);
				int i = 0;
				cs.setString(++i, colonyId.trim());
				if (null != devChargeMasterDataInterestDetails
						.getInterestPercentage()
						&& devChargeMasterDataInterestDetails
								.getInterestPercentage().trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataInterestDetails
									.getInterestPercentage().trim()));
				}
				cs.setString(++i, (null == devChargeMasterDataInterestDetails
						.getInterestStartDt()) ? ""
						: devChargeMasterDataInterestDetails
								.getInterestStartDt().trim());
				cs.setString(++i, (null == devChargeMasterDataInterestDetails
						.getInterestEndDt()) ? ""
						: devChargeMasterDataInterestDetails.getInterestEndDt()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataInterestDetails
						.getInterestDocProof()) ? ""
						: devChargeMasterDataInterestDetails
								.getInterestDocProof().trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, DJBConstants.SERVER_NODE);
				cs.setString(++i, null);
				cs.setString(++i, DJBConstants.FLAG_Y);
				cs.executeUpdate();
				AppLog
						.info("::Interest Row Detail Freeze Procedure INITIATED::Procedure Name::INTEREST_ROW_SAVE_PROC");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Method to save new colony for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataColonyDetails
	 * @param devChargeMasterDataRateDetails
	 * @param devChargeMasterDataInterestDetails
	 * @param devChargeMasterDataRebateDetails
	 * @param userId
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static void saveNewColonyDetail(
			DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails,
			DevChargeMasterDataRateDetails devChargeMasterDataRateDetails,
			DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails,
			DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			if (null != devChargeMasterDataColonyDetails
					&& null != devChargeMasterDataRateDetails
					&& null != devChargeMasterDataInterestDetails
					&& null != userId && !UtilityForAll.isEmpty(userId)) {
				conn = DBConnector.getConnection();
				cs = conn
						.prepareCall(QueryContants.NEW_COLONY_DETAILS_SAVE_PROC);
				int i = 0;
				cs.setString(++i, (null == devChargeMasterDataColonyDetails
						.getSelectedZRO()) ? ""
						: devChargeMasterDataColonyDetails.getSelectedZRO()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataColonyDetails
						.getColonyName()) ? ""
						: devChargeMasterDataColonyDetails.getColonyName()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataColonyDetails
						.getChargeType()) ? ""
						: devChargeMasterDataColonyDetails.getChargeType()
								.trim());

				cs.setString(++i, (null == devChargeMasterDataColonyDetails
						.getNotificationDate()) ? ""
						: devChargeMasterDataColonyDetails
								.getNotificationDate().trim());
				cs.setString(++i, (null == devChargeMasterDataColonyDetails
						.getCategory()) ? "" : devChargeMasterDataColonyDetails
						.getCategory().trim());
				if (null != devChargeMasterDataRateDetails
						&& null != devChargeMasterDataRateDetails
						&& DJBConstants.FLAG_Y
								.equalsIgnoreCase(devChargeMasterDataRateDetails
										.getRateInterestEligibity().trim())) {
					if (null != devChargeMasterDataInterestDetails
							.getInterestPercentage()
							&& devChargeMasterDataInterestDetails
									.getInterestPercentage().trim().length() > 0) {
						cs.setDouble(++i, Double
								.parseDouble(devChargeMasterDataInterestDetails
										.getInterestPercentage().trim()));
					}
					cs.setString(++i,
							(null == devChargeMasterDataInterestDetails
									.getInterestStartDt()) ? ""
									: devChargeMasterDataInterestDetails
											.getInterestStartDt().trim());
					cs.setString(++i,
							(null == devChargeMasterDataInterestDetails
									.getInterestEndDt()) ? ""
									: devChargeMasterDataInterestDetails
											.getInterestEndDt().trim());
					cs.setString(++i,
							(null == devChargeMasterDataInterestDetails
									.getInterestDocProof()) ? ""
									: devChargeMasterDataInterestDetails
											.getInterestDocProof().trim());
				} else {
					cs.setString(++i, null);
					cs.setString(++i, null);
					cs.setString(++i, null);
					cs.setString(++i, null);
				}
				cs.setString(++i, DJBConstants.FLAG_N);
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateTypeCode()) ? ""
						: devChargeMasterDataRateDetails.getRateTypeCode()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getCustomerClassCode()) ? ""
						: devChargeMasterDataRateDetails.getCustomerClassCode()
								.trim());
				if (null != devChargeMasterDataRateDetails.getRateMinPlotArea()
						&& devChargeMasterDataRateDetails.getRateMinPlotArea()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getRateMinPlotArea().trim()));
				} else {
					cs.setString(++i, "");
				}
				if (null != devChargeMasterDataRateDetails.getRateMaxPlotArea()
						&& devChargeMasterDataRateDetails.getRateMaxPlotArea()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getRateMaxPlotArea().trim()));
				} else {
					cs.setString(++i, "");
				}
				if (null != devChargeMasterDataRateDetails.getApplicableRate()
						&& devChargeMasterDataRateDetails.getApplicableRate()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getApplicableRate().trim()));
				}
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateInterestEligibity()) ? ""
						: devChargeMasterDataRateDetails
								.getRateInterestEligibity().trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRebateEligibility()) ? ""
						: devChargeMasterDataRateDetails.getRebateEligibility()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateStartDt()) ? ""
						: devChargeMasterDataRateDetails.getRateStartDt()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateEndDt()) ? "" : devChargeMasterDataRateDetails
						.getRateEndDt().trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateDocProof()) ? ""
						: devChargeMasterDataRateDetails.getRateDocProof()
								.trim());
				if (null != devChargeMasterDataRateDetails
						&& null != devChargeMasterDataRateDetails
								.getRebateEligibility()
						&& DJBConstants.FLAG_Y
								.equalsIgnoreCase(devChargeMasterDataRateDetails
										.getRebateEligibility().trim())) {
					cs.setString(++i, (null == devChargeMasterDataRebateDetails
							.getRebateType() ? ""
							: devChargeMasterDataRebateDetails.getRebateType()
									.trim()));
					if (null != devChargeMasterDataRebateDetails
							.getRebatePercentage()
							&& devChargeMasterDataRebateDetails
									.getRebatePercentage().trim().length() > 0) {
						cs.setDouble(++i, Double
								.parseDouble(devChargeMasterDataRebateDetails
										.getRebatePercentage().trim()));
					} else {
						cs.setString(++i, "");
					}
					if (null != devChargeMasterDataRebateDetails
							.getRebateFlatRate()
							&& devChargeMasterDataRebateDetails
									.getRebateFlatRate().trim().length() > 0) {
						cs.setDouble(++i, Double
								.parseDouble(devChargeMasterDataRebateDetails
										.getRebateFlatRate().trim()));
					} else {
						cs.setString(++i, "");
					}
					if (null != devChargeMasterDataRebateDetails
							.getRebateStartDt()
							&& devChargeMasterDataRebateDetails
									.getRebateStartDt().trim().length() > 0) {
						cs.setString(++i,
								(null == devChargeMasterDataRebateDetails
										.getRebateStartDt()) ? ""
										: devChargeMasterDataRebateDetails
												.getRebateStartDt().trim());
					}
					if (null != devChargeMasterDataRebateDetails
							.getRebateEndDt()
							&& devChargeMasterDataRebateDetails
									.getRebateEndDt().trim().length() > 0) {
						cs.setString(++i,
								(null == devChargeMasterDataRebateDetails
										.getRebateEndDt()) ? ""
										: devChargeMasterDataRebateDetails
												.getRebateEndDt().trim());
					}
					cs.setString(++i, (null == devChargeMasterDataRebateDetails
							.getRebateDocProof()) ? ""
							: devChargeMasterDataRebateDetails
									.getRebateDocProof().trim());
				} else {
					cs.setString(++i, null);
					cs.setString(++i, "");
					cs.setString(++i, "");
					cs.setString(++i, null);
					cs.setString(++i, null);
					cs.setString(++i, null);
				}
				cs.setString(++i, null);
				cs.setString(++i, null);
				cs.setString(++i, userId.trim());
				cs.setString(++i, null);
				cs.setString(++i, null);
				cs.setString(++i, DJBConstants.SERVER_NODE);
				cs.setString(++i, null);
				cs.executeUpdate();
				AppLog
						.info("::New Colony Detail Save Procedure INITIATED::Procedure Name::CM_CREATE_MASTER_DATA");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Method to save rate detail row for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataRateDetails
	 * @param colonyId
	 * @param userId
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static void saveRateDetailRow(
			DevChargeMasterDataRateDetails devChargeMasterDataRateDetails,
			String colonyId, String userId) {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			if (null != devChargeMasterDataRateDetails && null != userId
					&& !UtilityForAll.isEmpty(userId) && null != colonyId
					&& !UtilityForAll.isEmpty(colonyId)) {
				conn = DBConnector.getConnection();
				cs = conn.prepareCall(QueryContants.RATE_ROW_SAVE_PROC);
				int i = 0;
				cs.setString(++i, colonyId.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateTypeCode()) ? ""
						: devChargeMasterDataRateDetails.getRateTypeCode()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getCustomerClassCode()) ? ""
						: devChargeMasterDataRateDetails.getCustomerClassCode()
								.trim());
				if (null != devChargeMasterDataRateDetails.getRateMinPlotArea()
						&& devChargeMasterDataRateDetails.getRateMinPlotArea()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getRateMinPlotArea().trim()));
				} else {
					cs.setString(++i, "");
				}
				if (null != devChargeMasterDataRateDetails.getRateMaxPlotArea()
						&& devChargeMasterDataRateDetails.getRateMaxPlotArea()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getRateMaxPlotArea().trim()));
				} else {
					cs.setString(++i, "");
				}
				if (null != devChargeMasterDataRateDetails.getApplicableRate()
						&& devChargeMasterDataRateDetails.getApplicableRate()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRateDetails
									.getApplicableRate().trim()));
				}
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateInterestEligibity()) ? ""
						: devChargeMasterDataRateDetails
								.getRateInterestEligibity().trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRebateEligibility()) ? ""
						: devChargeMasterDataRateDetails.getRebateEligibility()
								.trim());

				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateStartDt()) ? ""
						: devChargeMasterDataRateDetails.getRateStartDt()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateEndDt()) ? "" : devChargeMasterDataRateDetails
						.getRateEndDt().trim());
				cs.setString(++i, (null == devChargeMasterDataRateDetails
						.getRateDocProof()) ? ""
						: devChargeMasterDataRateDetails.getRateDocProof()
								.trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, DJBConstants.SERVER_NODE);
				cs.setString(++i, null);
				cs.setString(++i, DJBConstants.FLAG_Y);
				cs.executeUpdate();
				AppLog
						.info("::RATE Row Detail Freeze Procedure INITIATED::Procedure Name::CM_CREATE_RATE");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Method to save rebate detail row for Dev Charge Master Data Screen, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param devChargeMasterDataRebateDetails
	 * @param colonyId
	 * @param userId
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static void saveRebateDetailRow(
			DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails,
			String colonyId, String userId) {
		AppLog.begin();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			if (null != devChargeMasterDataRebateDetails && null != userId
					&& !UtilityForAll.isEmpty(userId) && null != colonyId
					&& !UtilityForAll.isEmpty(colonyId)) {
				conn = DBConnector.getConnection();
				cs = conn.prepareCall(QueryContants.REBATE_ROW_SAVE_PROC);
				int i = 0;
				cs.setString(++i, colonyId.trim());
				cs.setString(++i, (null == devChargeMasterDataRebateDetails
						.getRebateType()) ? ""
						: devChargeMasterDataRebateDetails.getRebateType()
								.trim());
				if (null != devChargeMasterDataRebateDetails
						.getRebatePercentage()
						&& devChargeMasterDataRebateDetails
								.getRebatePercentage().trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRebateDetails
									.getRebatePercentage().trim()));
				} else {
					cs.setString(++i, "");
				}
				if (null != devChargeMasterDataRebateDetails
						.getRebateFlatRate()
						&& devChargeMasterDataRebateDetails.getRebateFlatRate()
								.trim().length() > 0) {
					cs.setDouble(++i, Double
							.parseDouble(devChargeMasterDataRebateDetails
									.getRebateFlatRate().trim()));
				} else {
					cs.setString(++i, "");
				}
				cs.setString(++i, (null == devChargeMasterDataRebateDetails
						.getRebateStartDt()) ? ""
						: devChargeMasterDataRebateDetails.getRebateStartDt()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRebateDetails
						.getRebateEndDt()) ? ""
						: devChargeMasterDataRebateDetails.getRebateEndDt()
								.trim());
				cs.setString(++i, (null == devChargeMasterDataRebateDetails
						.getRebateDocProof()) ? ""
						: devChargeMasterDataRebateDetails.getRebateDocProof()
								.trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, userId.trim());
				cs.setString(++i, null);
				cs.setString(++i, null);
				cs.setString(++i, DJBConstants.SERVER_NODE);
				cs.setString(++i, null);
				cs.setString(++i, DJBConstants.FLAG_Y);
				cs.executeUpdate();

				AppLog
						.info("::Rebate Row Detail Freeze Procedure INITIATED::Procedure Name::CM_CREATE_REBATE");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
	}
}
