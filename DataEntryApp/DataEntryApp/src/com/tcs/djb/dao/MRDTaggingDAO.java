/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java   09 Oct 2014
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

import javax.transaction.SystemException;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.MRDTaggingDetails;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Meter Reader and MRD tagging screen developed as per JTrac
 * HHD-48.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 23-09-2013
 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
 */
public class MRDTaggingDAO {
	/**
	 * <p>
	 * This method is used to get List of Meter Reader List for drop down HHD Maintenance Screen.
	 * </p>
	 * 
	 * @param zoneCode
	 * @return MeterReader Details list
	 */
	public static List<MeterReaderDetails> getMeterReaderList(
			MeterReaderDetails meterReaderDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MeterReaderDetails> dataList = new ArrayList<MeterReaderDetails>();
		String meterReaderName = "";
		try {
			String query = QueryContants
					.getMeterReaderListQuery(meterReaderDetails);
			AppLog.debug("query::" + query + ">>"
					+ meterReaderDetails.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReaderDetails.getMeterReaderZroCd()
					&& !"".equals(meterReaderDetails.getMeterReaderZroCd())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderZroCd());
			}
			if (null != meterReaderDetails.getMeterReaderEmployeeID()
					&& !""
							.equals(meterReaderDetails
									.getMeterReaderEmployeeID())) {
				ps
						.setString(++i, meterReaderDetails
								.getMeterReaderEmployeeID());
			}
			if (null != meterReaderDetails.getMeterReaderName()
					&& !"".equals(meterReaderDetails.getMeterReaderName())) {
				ps.setString(++i, meterReaderDetails.getMeterReaderName());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReaderName = rs.getString("MTR_RDR_NAME");

				if (null != rs.getString("EMP_ID")
						&& !"".equals(rs.getString("EMP_ID"))) {
					meterReaderName = meterReaderName + "("
							+ rs.getString("EMP_ID") + ")";
				}
				dataList.add(new MeterReaderDetails(rs.getString("MTR_RDR_CD"),
						meterReaderName));
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to create the history of meter reader and MRD tagging details in
	 * the database.
	 * </p>
	 * 
	 * @param selectedMRKeys
	 * @param userId
	 * @history 30-09-2015 Matiur Rahman Modified code as per changes DJB-3871
	 *          changed eff_to_date value
	 */
	public static void createMRDTaggingDetailsHistory(String selectedMRKeys,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into djb_mtr_rdr_mrd_hist( mtr_rdr_cd,mrd_cd,eff_from_date,eff_to_date,created_by,created_date,updated_by,updated_date) ");
			querySB
					.append(" (select m.mtr_rdr_cd,m.mrd_cd,m.eff_from_date,sysdate -(1/(24*60)),m.created_by,m.created_date,?,sysdate from djb_mtr_rdr_mrd m where m.mrd_cd in ("
							+ selectedMRKeys + "))");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			// ps.setString(++i, selectedMRKeys);
			ps.executeUpdate();
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
	}

	/**
	 * <p>
	 * This method is used to delete meter reader and MRD tagging details from the database.
	 * </p>
	 * 
	 * @param mrKeys
	 * @return true if success else false.
	 */
	public static boolean deleteMRDTaggingDetails(String mrKeys) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" delete from djb_mtr_rdr_mrd t ");
			querySB.append(" where t.mrd_cd in (" + mrKeys + ") ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
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
	 * This method is used to get all the MRDs tagged details of meter readers from the database.
	 * </p>
	 * 
	 * @param zone
	 * @return
	 */
	public static String getAllCurrentlyTaggedMRKeyList(String meterReaderCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String taggedMRKeys = "";
		try {
			String query = QueryContants
					.getAllCurrentlyTaggedMRKeyListQuery(meterReaderCode);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReaderCode && !"".equals(meterReaderCode.trim())) {
				ps.setString(++i, meterReaderCode);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				taggedMRKeys = taggedMRKeys + "," + rs.getString("mrd_cd");
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
		return taggedMRKeys;
	}

	/**
	 * <p>
	 * This method is used to get all the tagging details of meter readers and MRDs from the database.
	 * </p>
	 * 
	 * @param zone
	 * @param zroLoc
	 * @param mrKeys
	 * @return
	 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
	 *          Modified Query
	 */
	// public static String getAllCurrentTaggingListQuery(String zone) {
	public static List<MRDTaggingDetails> getAllCurrentTaggingList(String zone,
			String zroLoc, String mrKeys, String mrEmpId, String mrHhdId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MRDTaggingDetails> mrdTaggingDetailsList = new ArrayList<MRDTaggingDetails>();
		MeterReaderDetails meterReaderDetails = null;
		MRDTaggingDetails mrdTaggingDetails = null;
		try {
			/* Start: Commented by Matiur Rahman on 30-09-2015 */
			// String query = QueryContants.getAllCurrentTaggingListQuery(zone);
			/* End: Commented by Matiur Rahman on 30-09-2015 */
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			String query = QueryContants.getAllCurrentTaggingListQuery(zone,
					zroLoc, mrKeys, mrEmpId, mrHhdId);
			/* End: Added by Matiur Rahman on 30-09-2015 */
			conn = DBConnector.getConnection();
			AppLog.debug("query>>" + query + "::zone::" + zone + "::zroLoc::"
					+ zroLoc + "::mrKeys::" + mrKeys);
			ps = conn.prepareStatement(query);
			int i = 0;
			if ((null != zone && !"".equals(zone.trim()))
					&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
							.equals(mrHhdId.trim())))) {
				ps.setString(++i, zone);
			}
			if ((null != zroLoc && !"".equals(zroLoc.trim()))
					&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
							.equals(mrHhdId.trim())))) {
				ps.setString(++i, zroLoc);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				mrdTaggingDetails = new MRDTaggingDetails();
				meterReaderDetails = new MeterReaderDetails();
				mrdTaggingDetails.setMrkey(rs.getString("mrd_cd"));
				/* Start: Added by Matiur Rahman on 30-09-2015 */
				mrdTaggingDetails.setZone(rs.getString("subzone_cd"));
				mrdTaggingDetails.setMr(rs.getString("mr_cd"));
				mrdTaggingDetails.setArea(rs.getString("area_desc") + "("
						+ rs.getString("area_cd") + ")");
				mrdTaggingDetails.setType(rs.getString("old_mrd_typ"));
				/* End: Added by Matiur Rahman on 30-09-2015 */
				meterReaderDetails.setMeterReaderID(null != rs
						.getString("mtr_rdr_cd") ? rs.getString("mtr_rdr_cd")
						.trim() : "&nbsp;");
				meterReaderDetails.setMeterReaderName(null != rs
						.getString("mtr_rdr_name") ? rs.getString(
						"mtr_rdr_name").trim() : "&nbsp;");
				meterReaderDetails.setMeterReaderEmployeeID(null != rs
						.getString("emp_id") ? rs.getString("emp_id").trim()
						: "&nbsp;");
				mrdTaggingDetails.setCreatedBy(null != rs
						.getString("created_by") ? rs.getString("created_by")
						.trim() : "&nbsp;");
				mrdTaggingDetails.setCreatedOn(null != rs
						.getString("created_date") ? rs.getString(
						"created_date").trim() : "&nbsp;");
				mrdTaggingDetails.setEffectiveFrom(null != rs
						.getString("eff_from_date") ? rs.getString(
						"eff_from_date").trim() : "&nbsp;");
				mrdTaggingDetails.setEffectiveTo(null != rs
						.getString("eff_to_date") ? rs.getString("eff_to_date")
						.trim() : "&nbsp;");
				mrdTaggingDetails.setMeterReaderDetails(meterReaderDetails);
				mrdTaggingDetailsList.add(mrdTaggingDetails);
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
		return mrdTaggingDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get all the tagging details of meter readers and MRD from history table
	 * from the database.
	 * </p>
	 * 
	 * @param zone
	 * @param zroLoc
	 * @param mrKeys
	 * @return
	 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
	 *          Modified Query
	 */
	// public static String getAllTaggingListHistoryQuery(String zone) {
	public static List<MRDTaggingDetails> getAllTaggingListHistory(String zone,
			String zroLoc, String mrKeys, String mrEmpId, String mrHhdId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MRDTaggingDetails> mrdTaggingDetailsList = new ArrayList<MRDTaggingDetails>();
		MeterReaderDetails meterReaderDetails = null;
		MRDTaggingDetails mrdTaggingDetails = null;
		try {
			/* Start: Commented by Matiur Rahman on 30-09-2015 */
			// String query = QueryContants.getAllTaggingListHistoryQuery(zone);
			/* End: Commented by Matiur Rahman on 30-09-2015 */
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			String query = QueryContants.getAllTaggingListHistoryQuery(zone,
					zroLoc, mrKeys, mrEmpId, mrHhdId);
			/* End: Added by Matiur Rahman on 30-09-2015 */
			conn = DBConnector.getConnection();
			AppLog.debug("query>>" + query + ">>zone>>" + zone + ">>zroLoc>>"
					+ zroLoc + ">>mrKeys>>" + mrKeys);
			ps = conn.prepareStatement(query);
			int i = 0;
			if ((null != zone && !"".equals(zone.trim()))
					&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
							.equals(mrHhdId.trim())))) {
				ps.setString(++i, zone);
			}
			if ((null != zroLoc && !"".equals(zroLoc.trim()))
					&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
							.equals(mrHhdId.trim())))) {
				ps.setString(++i, zroLoc);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				mrdTaggingDetails = new MRDTaggingDetails();
				meterReaderDetails = new MeterReaderDetails();
				mrdTaggingDetails.setMrkey(rs.getString("mrd_cd"));
				/* Start: Added by Matiur Rahman on 30-09-2015 */
				mrdTaggingDetails.setZone(rs.getString("subzone_cd"));
				mrdTaggingDetails.setMr(rs.getString("mr_cd"));
				mrdTaggingDetails.setArea(rs.getString("area_desc") + "("
						+ rs.getString("area_cd") + ")");
				mrdTaggingDetails.setType(rs.getString("old_mrd_typ"));
				/* End: Added by Matiur Rahman on 30-09-2015 */
				meterReaderDetails.setMeterReaderID(null != rs
						.getString("mtr_rdr_cd") ? rs.getString("mtr_rdr_cd")
						.trim() : "&nbsp;");
				meterReaderDetails.setMeterReaderName(null != rs
						.getString("mtr_rdr_name") ? rs.getString(
						"mtr_rdr_name").trim() : "&nbsp;");
				meterReaderDetails.setMeterReaderEmployeeID(null != rs
						.getString("emp_id") ? rs.getString("emp_id").trim()
						: "&nbsp;");
				mrdTaggingDetails.setCreatedBy(null != rs
						.getString("created_by") ? rs.getString("created_by")
						.trim() : "&nbsp;");
				mrdTaggingDetails.setCreatedOn(null != rs
						.getString("created_date") ? rs.getString(
						"created_date").trim() : "&nbsp;");
				mrdTaggingDetails.setUpdatedBy(null != rs
						.getString("updated_by") ? rs.getString("updated_by")
						.trim() : "&nbsp;");
				mrdTaggingDetails.setUpdatedOn(null != rs
						.getString("updated_date") ? rs.getString(
						"updated_date").trim() : "&nbsp;");
				mrdTaggingDetails.setEffectiveFrom(null != rs
						.getString("eff_from_date") ? rs.getString(
						"eff_from_date").trim() : "&nbsp;");
				mrdTaggingDetails.setEffectiveTo(null != rs
						.getString("eff_to_date") ? rs.getString("eff_to_date")
						.trim() : "&nbsp;");
				mrdTaggingDetails.setMeterReaderDetails(meterReaderDetails);
				mrdTaggingDetailsList.add(mrdTaggingDetails);
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
		return mrdTaggingDetailsList;
	}

	/**
	 * <p>
	 * This method is used to tag meter reader with MRD in the database.
	 * </p>
	 * 
	 * @param meterReaderDetails
	 * @return true if success else false.
	 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
	 *          Modified Query
	 */
	public static boolean tagMRD(String mrKey, String meterReaderID,
			String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into djb_mtr_rdr_mrd( mtr_rdr_cd,mrd_cd,eff_from_date,created_by,created_date) ");
			/* Start: Commented by Matiur Rahman on 30-09-2015 */
			// querySB
			// .append(" values((select (max(to_number(t.mtr_rdr_mrd_id))+1) from djb_mtr_rdr_mrd t),?,?,sysdate,?,sysdate)");
			/* End: Commented by Matiur Rahman on 30-09-2015 */
			/* Start: Added by Matiur Rahman on 30-09-2015 */
			querySB.append(" values(?,?,sysdate,?,sysdate)");
			/* End: Added by Matiur Rahman on 30-09-2015 */
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, meterReaderID);
			ps.setString(++i, mrKey);
			ps.setString(++i, userId);
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
	 * This method is used to tag default meter reader with MRD in the database.
	 * </p>
	 * 
	 * @param mrKey
	 * @param meterReaderName
	 * @param zone
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws SystemException
	 */
	public static boolean tagMRDToDeafault(String mrKey,
			String meterReaderName, String zone, String userId)
			throws SQLException, SystemException, IOException {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into djb_mtr_rdr_mrd( mtr_rdr_cd,mrd_cd,eff_from_date,created_by,created_date) ");
			// querySB
			// .append(" insert into djb_mtr_rdr_mrd( mtr_rdr_mrd_id,mtr_rdr_cd,mrd_cd,eff_from_date,created_by,created_date) ");
			// querySB
			// .append(" values((select (max(to_number(t.mtr_rdr_mrd_id))+1) from djb_mtr_rdr_mrd t),(select r.mtr_rdr_cd from djb_mtr_rdr r where r.mtr_rdr_name=? and r.subzone_cd=?),?,sysdate,?,sysdate)");
			// querySB
			// .append(" values((select (nvl(max(to_number(t.mtr_rdr_mrd_id)),0)+1) from djb_mtr_rdr_mrd t),(select r.mtr_rdr_cd from djb_mtr_rdr r where r.mtr_rdr_name=? and r.zro_cd=?),?,sysdate,?,sysdate)");
			querySB
					.append(" values((select r.mtr_rdr_cd from djb_mtr_rdr r where r.mtr_rdr_name=? and r.zro_cd=?),?,sysdate,?,sysdate)");
			String query = querySB.toString();
			AppLog.debug("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, meterReaderName);
			ps.setString(++i, zone);
			ps.setString(++i, mrKey);
			ps.setString(++i, userId);
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
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
