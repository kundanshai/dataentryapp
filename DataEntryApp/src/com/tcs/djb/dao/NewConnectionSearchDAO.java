/************************************************************************************************************
 * @(#) NewConnectionSearchDAO.java   02 Dec 2013
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

import javax.transaction.SystemException;

import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for New Connection DAF Search process.
 * </p>
 * 
 * @author Rajib Hazarika
 * @since 02-12-2013
 */
public class NewConnectionSearchDAO {

	/**
	 * <p>
	 * This method is used to get Zone Code Drop down List
	 * </p>
	 * 
	 * @return
	 */
	// public Map<String, String> getLocationListMap(String zroLocation) {
	public Map<String, String> getLocationListMap(String zroLocation) {
		AppLog.begin();
		Map<String, String> locationListMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append("  select t.zro_desc,t.zro_cd from djb_zro t ");
			if (null != zroLocation && !"".equals(zroLocation)) {
				zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
				zroLocation = zroLocation.replaceAll("',''", "").replaceAll(
						"'',", "");
				querySB.append("where t.zro_cd in (" + zroLocation + ")");
			}

			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				locationListMap.put(rs.getString("zro_cd").trim(), rs
						.getString("zro_desc").trim());
			}
		} catch (SystemException e) {
			AppLog.error(e);

		} catch (IOException e) {
			AppLog.error(e);

		} catch (SQLException e) {
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
		return locationListMap;
	}

	/**
	 * <p>
	 * This method is used to get Search Result Details corresponding to search
	 * criteria.
	 * </p>
	 * 
	 * @param zoneNo
	 * @param location
	 * @return
	 */
	public static List<NewConnectionDAFDetailsContainer> getSearchResultDetails(
			String zoneNo, String location) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<NewConnectionDAFDetailsContainer> searchResultDetailsList = null;
		NewConnectionDAFDetailsContainer searchResultDetails = null;
		try {
			searchResultDetailsList = new ArrayList<NewConnectionDAFDetailsContainer>();
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("select distinct  t.seq,t.entity_name,t.house_no,t.road_no, a.area_desc locality, sa.sub_area_name sub_locality,");
			querySB
					.append(" t.pin_code, t.app_status from cm_new_conn_stg t, djb_zn_mr_ar_mrd m, djb_mrd z, DJB_SUBZONE s,area a,sub_area sa ");
			querySB
					.append(" where t.zone_no = m.subzone_cd and t.mr_no = m.mr_cd and t.area_no = m.area_cd and m.mrd_cd = z.mrd_cd ");
			querySB
					.append(" and t.zro_location=z.zro_cd and t.app_status='E' ");
			querySB.append(" AND t.ACCT_ID IS NULL ");
			querySB
					.append(" AND trim(t.locality)=trim(a.area_code(+)) and t.sub_locality=sa.sub_area_code(+) ");
			if (null != zoneNo && !"".equals(zoneNo.trim())) {
				querySB.append(" and m.subzone_cd =? ");
			}
			if (null != location && !"".equals(location.trim())) {
				querySB.append(" and z.zro_cd=? ");
			}
			// System.out.println("query::" + querySB);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			if (null != zoneNo && !"".equals(zoneNo.trim())) {
				ps.setString(++i, zoneNo.trim());
			}
			if (null != location && !"".equals(location.trim())) {
				ps.setString(++i, location.trim());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				searchResultDetails = new NewConnectionDAFDetailsContainer();
				searchResultDetails.setSequenceNo(rs.getString("SEQ"));
				searchResultDetails.setEntityName(rs.getString("ENTITY_NAME"));
				searchResultDetails.setHouseNumber(rs.getString("HOUSE_NO"));
				searchResultDetails.setRoadNumber(rs.getString("ROAD_NO"));
				searchResultDetails.setLocality(rs.getString("LOCALITY"));
				searchResultDetails
						.setSubLocality(rs.getString("SUB_LOCALITY"));
				searchResultDetails.setPinCode(rs.getString("PIN_CODE"));
				searchResultDetails.setApplicationPurpose(rs
						.getString("APP_STATUS"));
				searchResultDetailsList.add(searchResultDetails);
			}
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return searchResultDetailsList;
	}
}
