/************************************************************************************************************
 * @(#) MeterReaderMaintenanceDAO.java   29 Sep 2013
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

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Meter Reader Maintenance screen developed as per JTrac HHD-48.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 23-09-2013
 * @history: 01-OCT-2015 Rajib changed this file as per JTrac DJB-3871 to add
 *           new feature for meter Reader Profiling. Changed methods are:
 *           {@link #addMeterReaderDetails()}, {@link #createMeterReaderDetailsHistory()},
 *           {@link #getAllMeterReaderDetailsHistoryListMap()}, 
 *           {@link #getAllMeterReaderDetailsListMap()},
 *           new methods are: 
 *           {@link #bypassMeterReaderDetails()}, {@link #getActiveHhdListMap()},
 *           {@link #getActiveInactiveMapList()}, {@link #getMeterReaderStatusListMap()},
 *           {@link #getSupervisorEmpIdListMap()}
 * @history: @history 14-OCT-2015 Rajib Added parameter for HHD ID on {@link #getAllMeterReaderDetailsListMap} as per Jtrac DJB-4199 
 */
public class MeterReaderMaintenanceDAO {
	/**
	 * <p>
	 * Method to add meter reader details to the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return true if success else false.
	 */
	public static boolean addMeterReaderDetails(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into djb_mtr_rdr(subzone_cd,mtr_rdr_cd,mtr_rdr_name,emp_id,mobile,email,mtr_rdr_typ,created_by,created_date,supervisor_cd,hhd_id,active_inactive_reason,is_active,zro_cd,eff_to_date) ");
			querySB
					.append(" values((select z.subzone_cd from djb_zro z where z.zro_cd=?),");
			if (null != meterReaderDetails.getMeterReaderID()
					&& !"".equals(meterReaderDetails.getMeterReaderID().trim())) {
				querySB.append(meterReaderDetails.getMeterReaderID().trim());
			} else {
				querySB
						.append(" (select max(mtr_rdr_cd)+1 from (select to_number(mtr_rdr_cd) mtr_rdr_cd from djb_mtr_rdr union select to_number(mtr_rdr_cd) from djb_mtr_rdr_hist))");
			}
			querySB.append(",?,?,?,?,?,?,sysdate,?,?,?,?,?");
			if (!DJBConstants.FLAG_Y.equals(meterReaderDetails
					.getMeterReaderStatus())) {
				querySB.append(",sysdate)");
			} else {
				querySB.append(",'')");
			}
			String query = querySB.toString();
			AppLog.info("Query>>" + query + ">>"
					+ meterReaderDetails.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// ps.setString(++i, meterReaderDetails.getMeterReaderZone());
			ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			ps.setString(++i, meterReaderDetails.getMeterReaderName());
			ps.setString(++i, meterReaderDetails.getMeterReaderEmployeeID());
			ps.setString(++i, meterReaderDetails.getMeterReaderMobileNo());
			ps.setString(++i, meterReaderDetails.getMeterReaderEmail());
			ps.setString(++i, meterReaderDetails.getMeterReaderDesignation());
			ps.setString(++i, meterReaderDetails.getUserId());
			// Added by Rajib as per JTrac
			ps.setString(++i, meterReaderDetails.getSupervisorEmpId());
			ps.setString(++i, meterReaderDetails.getHhdId());
			ps.setString(++i, meterReaderDetails.getActiveInactiveRemark());
			ps.setString(++i, meterReaderDetails.getMeterReaderStatus());
			ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			// Change End by Rajib as per JTrac
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
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
		return isSuccess;
	}

	/**
	 * <p>
	 * On 26-SEP-2015 Rajib Added this method to bypass meter reader for
	 * exception details as per JTRac DJB-3871
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return
	 */

	public static boolean bypassMeterReaderDetails(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into DJB_MRD_DWNLD_EXCP_DTL(MTR_RDR_CD,EFF_FROM_DATE,EFF_TO_DATE,CREATED_BY,CREATED_DATE) VALUES(?,TO_DATE(?,'dd/mm/yyyy hh24:mi'),TO_DATE(?,'dd/mm/yyyy hh24:mi'),?,SYSDATE) ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// ps.setString(++i, meterReaderDetails.getMeterReaderZone());
			ps.setString(++i, meterReaderDetails.getMeterReaderID());
			ps.setString(++i, meterReaderDetails.getBypassFromdate());
			ps
					.setString(++i, null != meterReaderDetails
							.getBypassTodate() ? (meterReaderDetails
							.getBypassTodate() + " 23:59") : meterReaderDetails
							.getBypassTodate());
			ps.setString(++i, meterReaderDetails.getUpdatedBy());
			// Change End by Rajib as per JTrac
			AppLog.info("Query>>" + query);
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
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
		return isSuccess;
	}

	/**
	 * <p>
	 * Method to create the history of meter reader details to the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return true if success else false.
	 */
	public static boolean createMeterReaderDetailsHistory(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			/*
			 * querySB.append(
			 * " insert into djb_mtr_rdr_hist(subzone_cd,mtr_rdr_cd,mtr_rdr_name,emp_id,mobile,email,mtr_rdr_typ,created_by,created_date,updated_by,updated_date) "
			 * );querySB.append(
			 * " (select subzone_cd,mtr_rdr_cd,mtr_rdr_name,emp_id,mobile,email,mtr_rdr_typ,created_by,created_date,? updated_by,sysdate from djb_mtr_rdr where mtr_rdr_cd=?)"
			 * );
			 */
			querySB
					.append("insert into djb_mtr_rdr_hist(subzone_cd,mtr_rdr_cd,mtr_rdr_name,emp_id,is_rms,mobile,email,mtr_rdr_typ,created_by,created_date,updated_by,updated_date,zro_cd,hhd_id,supervisor_cd,is_active,active_inactive_reason,eff_from_date, eff_to_date )");
			querySB
					.append(" (select subzone_cd,mtr_rdr_cd,mtr_rdr_name,emp_id,is_rms,mobile,email,mtr_rdr_typ,created_by,created_date,?,sysdate,zro_cd,hhd_id,supervisor_cd,is_active,active_inactive_reason,eff_from_date, sysdate-1/(24*60) from djb_mtr_rdr where mtr_rdr_cd=?)");
			String query = querySB.toString();
			AppLog.info(">>query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, meterReaderDetails.getUserId());
			ps.setString(++i, meterReaderDetails.getMeterReaderID());
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
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
		return isSuccess;
	}

	/**
	 * <p>
	 * Method to delete meter reader details to the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return true if success else false.
	 */
	public static boolean deleteMeterReaderDetails(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" delete from djb_mtr_rdr t ");
			querySB.append(" where t.mtr_rdr_cd=? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, meterReaderDetails.getMeterReaderID());
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
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
		return isSuccess;
	}

	/**
	 * <p>
	 * This method returns list of Active HHD Id's which are available to tagged
	 * with meter Readers
	 * </p>
	 * 
	 * @author 682667
	 * @param currentHhdId
	 * @return
	 */
	public static Map<String, String> getActiveHhdListMap(String currentHhdId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getActiveHhdListMapQuery(currentHhdId);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			// ps.setString(1, mrkey);
			AppLog.appLog.info(">>query>>" + query + ">>currentHhdId>>"
					+ currentHhdId);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("ASSET_ID").trim(), rs.getString(
						"ASSET_ID").trim());
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
	 * This function will return Maplist containing value Active/InActive
	 * </p>
	 * 
	 * @author 682667
	 * @since 29-SEP-2015
	 * @return
	 */
	public static Map<String, String> getActiveInactiveMapList() {
		AppLog.begin();
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			retrunMap.put("Y", "Yes");
			retrunMap.put("N", "No");
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return retrunMap;
	}

	/**
	 * <p>
	 * Get all the details of meter readers from the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return Meter Reader Details List
	 */
	public static List<MeterReaderDetails> getAllMeterReaderDetailsHistoryListMap(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MeterReaderDetails> meterReaderDetailsList = new ArrayList<MeterReaderDetails>();
		try {
			String query = QueryContants
					.getMeterReaderDetailsHistoryListQuery(meterReaderDetails);
			conn = DBConnector.getConnection();
			AppLog
					.debug("query>>" + query + ""
							+ meterReaderDetails.toString());
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReaderDetails.getMeterReaderZone()
					&& !"".equals(meterReaderDetails.getMeterReaderZone()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderZone());
			}
			if (null != meterReaderDetails.getMeterReaderID()
					&& !"".equals(meterReaderDetails.getMeterReaderID().trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderID());
			}
			rs = ps.executeQuery();
			while (rs.next()) {

				meterReaderDetails = new MeterReaderDetails();
				meterReaderDetails.setMeterReaderZone(rs
						.getString("subzone_cd"));
				meterReaderDetails.setMeterReaderID(null != rs
						.getString("mtr_rdr_cd") ? rs.getString("mtr_rdr_cd")
						.trim() : "NA");
				meterReaderDetails.setMeterReaderName(null != rs
						.getString("mtr_rdr_name") ? rs.getString(
						"mtr_rdr_name").trim() : "NA");
				meterReaderDetails.setMeterReaderEmployeeID(null != rs
						.getString("emp_id") ? rs.getString("emp_id").trim()
						: "NA");
				meterReaderDetails.setMeterReaderMobileNo(null != rs
						.getString("mobile") ? rs.getString("mobile").trim()
						: "NA");
				meterReaderDetails.setMeterReaderEmail(null != rs
						.getString("email") ? rs.getString("email").trim()
						: "NA");
				meterReaderDetails.setMeterReaderDesignation(null != rs
						.getString("mtr_rdr_typ") ? rs.getString("mtr_rdr_typ")
						.trim() : "NA");
				meterReaderDetails
						.setUserId(null != rs.getString("created_by") ? rs
								.getString("created_by").trim() : "NA");
				meterReaderDetails.setSupervisorEmpId(null != rs
						.getString("SUPERVISOR_CD") ? rs.getString(
						"SUPERVISOR_CD").trim() : "NA");
				meterReaderDetails.setHhdId(null != rs.getString("HHD_ID") ? rs
						.getString("HHD_ID").trim() : "NA");
				meterReaderDetails.setActiveInactiveRemark(null != rs
						.getString("ACTIVE_INACTIVE_REASON") ? rs.getString(
						"ACTIVE_INACTIVE_REASON").trim() : "NA");
				meterReaderDetails.setMeterReaderStatus(null != rs
						.getString("IS_ACTIVE") ? rs.getString("IS_ACTIVE")
						.trim() : "NA");
				meterReaderDetails.setMeterReaderZroCd(null != rs
						.getString("ZRO_CD") ? rs.getString("ZRO_CD").trim()
						: "");
				// EFF_FROM_DATE EFF_TO_DATE
				meterReaderDetails.setEffFromdate(null != rs
						.getString("EFF_FROM_DATE") ? rs.getString(
						"EFF_FROM_DATE").trim() : "");
				meterReaderDetails.setEffTodate(null != rs
						.getString("EFF_TO_DATE") ? rs.getString("EFF_TO_DATE")
						.trim() : "");
				meterReaderDetails.setCreatedBy(null != rs
						.getString("CREATED_BY") ? rs.getString("CREATED_BY")
						.trim() : "");
				meterReaderDetails.setCreatedOn(null != rs
						.getString("CREATED_DATE") ? rs.getString(
						"CREATED_DATE").trim() : "");
				meterReaderDetails.setUpdatedBy(null != rs
						.getString("UPDATED_BY") ? rs.getString("UPDATED_BY")
						.trim() : "");
				meterReaderDetails.setUpdatedOn(null != rs
						.getString("UPDATED_DATE") ? rs.getString(
						"UPDATED_DATE").trim() : "");

				meterReaderDetailsList.add(meterReaderDetails);

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
		return meterReaderDetailsList;
	}

	/**
	 * <p>
	 * Get all the details of meter readers from the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return Meter Reader Details List
	 */
	public static List<MeterReaderDetails> getAllMeterReaderDetailsListMap(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MeterReaderDetails> meterReaderDetailsList = new ArrayList<MeterReaderDetails>();
		try {
			String query = QueryContants
					.getMeterReaderDetailsListQuery(meterReaderDetails);
			conn = DBConnector.getConnection();
			// System.out.println("query>>" + query);
			AppLog.info("getAllMeterReaderDetailsListMap query>>" + query
					+ "::" + meterReaderDetails.toString());
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReaderDetails.getMeterReaderZroCd()
					&& !"".equals(meterReaderDetails.getMeterReaderZroCd()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			}

			if (null != meterReaderDetails.getMeterReaderZone()
					&& !"".equals(meterReaderDetails.getMeterReaderZone()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderZone());
			}
			if (null != meterReaderDetails.getMeterReaderName()
					&& !"".equals(meterReaderDetails.getMeterReaderName()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderName());
			}
			if (null != meterReaderDetails.getMeterReaderEmployeeID()
					&& !"".equals(meterReaderDetails.getMeterReaderEmployeeID()
							.trim())) {
				ps.setString(++i, meterReaderDetails
								.getMeterReaderEmployeeID());
			}
			if (null != meterReaderDetails.getMeterReaderMobileNo()
					&& !"".equals(meterReaderDetails.getMeterReaderMobileNo()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderMobileNo());
			}
			if (null != meterReaderDetails.getUserId()
					&& !"".equals(meterReaderDetails.getUserId().trim())) {
				ps.setString(++i, meterReaderDetails.getUserId());
			}
			if (null != meterReaderDetails.getMeterReaderStatus()
					&& !"".equals(meterReaderDetails.getMeterReaderStatus()
							.trim())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderStatus());
			}
			//START: Added by Rajib as per Jtrac DJB-4199 on 14-OCT-2015
			if (null != meterReaderDetails.getHhdId()
					&& !"".equals(meterReaderDetails.getHhdId().trim())) {
				ps.setString(++i, meterReaderDetails.getHhdId());
			}
			//START: Added by Rajib as per Jtrac DJB-4199 on 14-OCT-2015
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReaderDetails = new MeterReaderDetails();
				meterReaderDetails.setMeterReaderZone(rs
						.getString("subzone_cd"));
				meterReaderDetails.setMeterReaderID(null != rs
						.getString("mtr_rdr_cd") ? rs.getString("mtr_rdr_cd")
						.trim() : "NA");
				meterReaderDetails.setMeterReaderName(null != rs
						.getString("mtr_rdr_name") ? rs.getString(
						"mtr_rdr_name").trim() : "NA");
				meterReaderDetails.setMeterReaderEmployeeID(null != rs
						.getString("emp_id") ? rs.getString("emp_id").trim()
						: "NA");
				meterReaderDetails.setMeterReaderMobileNo(null != rs
						.getString("mobile") ? rs.getString("mobile").trim()
						: "NA");
				meterReaderDetails.setMeterReaderEmail(null != rs
						.getString("email") ? rs.getString("email").trim()
						: "NA");
				meterReaderDetails.setMeterReaderDesignation(null != rs
						.getString("mtr_rdr_typ") ? rs.getString("mtr_rdr_typ")
						.trim() : "NA");
				meterReaderDetails
						.setUserId(null != rs.getString("created_by") ? rs
								.getString("created_by").trim() : "NA");
				meterReaderDetails.setSupervisorEmpId(null != rs
						.getString("SUPERVISOR_CD") ? rs.getString(
						"SUPERVISOR_CD").trim() : "NA");
				meterReaderDetails.setHhdId(null != rs.getString("HHD_ID") ? rs
						.getString("HHD_ID").trim() : "NA");
				meterReaderDetails.setActiveInactiveRemark(null != rs
						.getString("ACTIVE_INACTIVE_REASON") ? rs.getString(
						"ACTIVE_INACTIVE_REASON").trim() : "NA");
				meterReaderDetails.setMeterReaderStatus(null != rs
						.getString("IS_ACTIVE") ? rs.getString("IS_ACTIVE")
						.trim() : "NA");
				meterReaderDetails.setMeterReaderZroCd(null != rs
						.getString("ZRO_CD") ? rs.getString("ZRO_CD").trim()
						: "");
				// EFF_FROM_DATE EFF_TO_DATE
				meterReaderDetails.setEffFromdate(null != rs
						.getString("EFF_FROM_DATE") ? rs.getString(
						"EFF_FROM_DATE").trim() : "");
				meterReaderDetails.setEffTodate(null != rs
						.getString("EFF_TO_DATE") ? rs.getString("EFF_TO_DATE")
						.trim() : "");
				meterReaderDetails.setCreatedBy(null != rs
						.getString("CREATED_BY") ? rs.getString("CREATED_BY")
						.trim() : "");
				meterReaderDetails.setCreatedOn(null != rs
						.getString("CREATED_DATE") ? rs.getString(
						"CREATED_DATE").trim() : "");
				meterReaderDetailsList.add(meterReaderDetails);
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
		return meterReaderDetailsList;
	}

	/**
	 * Retrieve Meter Reader Status list to populate Active /InActive Remark for
	 * Meter Reader.
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getMeterReaderStatusListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getMeterReaderStatusListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (null != rs.getString("STATUS_CD")) {
					retrunMap.put(rs.getString("STATUS_CD").trim(), rs
							.getString("STATUS_DESC"));
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
	 * This method is used to list of Employee Id's of Supervisors
	 * </p>
	 * 
	 * @param zroLocation
	 * @param meterReaderId
	 * @return
	 */
	public static Map<String, String> getSupervisorEmpIdListMap(
			String zroLocation, String meterReaderId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants
					.getSupervisorEmpIdListMapQuery(meterReaderId);
			AppLog.debug("query>>" + query + ">>zroLocation>>" + zroLocation
					+ ">>meterReaderId>>" + meterReaderId);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, zroLocation);
			if (null != meterReaderId && !"".equals(meterReaderId.trim())) {
				ps.setString(++i, meterReaderId.trim());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("MTR_RDR_CD").trim(), rs
						.getString("MTR_RDR_NAME")
						+ "(" + rs.getString("EMP_ID") + ")");
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
	 * Method to Update meter reader details to the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return true if success else false.
	 */
	public static boolean updateMeterReaderDetails(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" update djb_mtr_rdr t set t.subzone_cd=(select subzone_cd from djb_zro z where z.zro_cd=?),t.mtr_rdr_name=?,t.emp_id=?,t.mobile=?,t.email=?,t.mtr_rdr_typ=?,t.updated_by=?,t.updated_date=sysdate ");
			querySB
					.append(" ,t.zro_cd=?,t.hhd_id=?,t.supervisor_cd=?,t.is_active=?,t.active_inactive_reason=? ");
			querySB.append(" where t.mtr_rdr_cd=? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			ps.setString(++i, meterReaderDetails.getMeterReaderName());
			ps.setString(++i, meterReaderDetails.getMeterReaderEmployeeID());
			ps.setString(++i, meterReaderDetails.getMeterReaderMobileNo());
			ps.setString(++i, meterReaderDetails.getMeterReaderEmail());
			ps.setString(++i, meterReaderDetails.getMeterReaderDesignation());
			ps.setString(++i, meterReaderDetails.getUserId());
			ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			ps.setString(++i, meterReaderDetails.getHhdId());
			ps.setString(++i, meterReaderDetails.getSupervisorEmpId());
			ps.setString(++i, meterReaderDetails.getMeterReaderStatus());
			ps.setString(++i, meterReaderDetails.getActiveInactiveRemark());
			ps.setString(++i, meterReaderDetails.getMeterReaderID());
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
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
		return isSuccess;
	}
}
