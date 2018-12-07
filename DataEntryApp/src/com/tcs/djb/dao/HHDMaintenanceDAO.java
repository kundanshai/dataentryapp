/************************************************************************************************************
 * @(#) HHDMaintenanceDAO.java   23 Jul 2013
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

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.HHDMappingDetails;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * DAO class for HHD Maintenance.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 23-07-2013 Matiur Rahman Added method {@link #getHHDListMap}.
 * @history 23-07-2013 Matiur Rahman added new parameter hhdId to the method
 *          {@link #getHHDMappingDetailsList} and
 *          {@link #getTotalCountOfHHDMappingRecords}.
 * @history 23-07-2013 Matiur Rahman modified method {@link #getMeterReaderList}
 *          to Append Employee ID with Name.
 * 
 */
public class HHDMaintenanceDAO {
	/**
	 * <p>
	 * This method is used to get default effective to date value for HHD and
	 * Meter Reader Mapping.
	 * </p>
	 * 
	 * @return
	 */
	private static String getDefaultEffectiveTodateValue() {
		try {
			String HHD_MAINTENANCE_EFF_TO_DATE_INIT_VALUE = PropertyUtil
					.getProperty("HHD_MAINTENANCE_EFF_TO_DATE_INIT_VALUE");
			return ("".equalsIgnoreCase(HHD_MAINTENANCE_EFF_TO_DATE_INIT_VALUE
					.trim()) || "null"
					.equalsIgnoreCase(HHD_MAINTENANCE_EFF_TO_DATE_INIT_VALUE
							.trim())) ? null
					: HHD_MAINTENANCE_EFF_TO_DATE_INIT_VALUE;
		} catch (Exception e) {
			AppLog.error(e);
		}
		return null;
	}

	/**
	 * <p>
	 * This method is used to retrieve HHD ID list to populate HHD ID Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getHHDListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT T.USER_ID HHD_ID FROM DJB_WEB_SERVICE_USERS T ORDER BY T.USER_ID ASC ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("HHD_ID").trim(), rs
						.getString("HHD_ID"));
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
	 * This method is used to retrieve HHD and Meter Reader Mapping Details List
	 * for HHD Maintenance Screen.
	 * </p>
	 * 
	 * @param billRound
	 * @param zoneCode
	 * @param pageNo
	 * @param recordPerPage
	 * @return HHD Mapping Details List
	 */
	public static List<HHDMappingDetails> getHHDMappingDetailsList(
			String status, String zoneCode, String hhdId, String mrKeyList,
			String pageNo, String recordPerPage) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HHDMappingDetails hhdMappingDetails = null;
		List<HHDMappingDetails> hhdMappingDetailsList = new ArrayList<HHDMappingDetails>();
		try {
			String query = QueryContants.getHHDMappingDetailsListQuery(status,
					zoneCode, hhdId, mrKeyList, pageNo, recordPerPage);
			// AppLog.info("getHHDMappingDetailsListQuery::" + query);
			// System.out.println("getHHDMappingDetailsList query::" + query
			// + "\nstatus::" + status + "\nzoneCode::" + zoneCode
			// + "\nhhdId::" + hhdId + "\nmrKeyList::" + mrKeyList
			// + "\npageNo::" + pageNo + "\nrecordPerPage::"
			// + recordPerPage);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				hhdMappingDetails = new HHDMappingDetails();
				hhdMappingDetails.setSlNo(rs.getString("SEQ_NO"));
				hhdMappingDetails.setZoneCode(rs.getString("SUBZONE_CD"));
				hhdMappingDetails.setMrNo(rs.getString("MR_CD"));
				hhdMappingDetails.setAreaCode(rs.getString("AREA_CD"));
				hhdMappingDetails.setAreaDesc(rs.getString("AREA_DESC"));
				hhdMappingDetails.setMrkey(rs.getString("MRD_CD"));
				hhdMappingDetails
						.setMeterReaderCode(rs.getString("MTR_RDR_CD"));
				hhdMappingDetails.setHhdID(rs.getString("HHD_ID"));
				hhdMappingDetails.setEffFromDate(rs.getString("EFF_FROM_DATE"));
				hhdMappingDetails.setEffToDate(rs.getString("EFF_TO_DATE"));
				hhdMappingDetails
						.setLastUpdatedBy(rs.getString("LAST_UPDT_BY"));
				hhdMappingDetails.setLastUpdatedOn(rs
						.getString("LAST_UPDT_DTTM"));
				hhdMappingDetailsList.add(hhdMappingDetails);
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
		return hhdMappingDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get List of Meter Reader List for drop down HHD
	 * Maintenance Screen.
	 * </p>
	 * 
	 * @param zoneCode
	 * @return MeterReader Details list
	 */
	public static List<MeterReaderDetails> getMeterReaderList(String zoneCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MeterReaderDetails> dataList = new ArrayList<MeterReaderDetails>();
		String meterReaderName = "";
		try {
			String query = QueryContants.getMeterReaderListQuery();
			// AppLog.info("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, zoneCode);
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
	 * This method is used to get the total number of records for HHD Mapping
	 * Screen in the database.
	 * </p>
	 * 
	 * @param billRound
	 * @return
	 */
	public static int getTotalCountOfHHDMappingRecords(String status,
			String zoneCode, String hhdId, String mrKeyList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants.getTotalCountOfHHDMappingRecordsQuery(
					status, zoneCode, hhdId, mrKeyList);
			// AppLog.info("getTotalCountOfHHDMappingRecordsQuery::" + query);
			// System.out
			// .println("getTotalCountOfHHDMappingRecordsQuery query::"
			// + query + "\nstatus::" + status + "\nzoneCode::"
			// + zoneCode);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
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
	 * This method is used to update HHD Meter Reader Mapping Details.
	 * </p>
	 * 
	 * @param toupdateMRKey
	 * @param toupdateMeterReader
	 * @param toupdateHHDId
	 * @param previousMeterReader
	 * @param previousHHDId
	 * @param userName
	 * @return
	 */
	public static int updateHHDMeterReaderMappingDetails(String toupdateMRKey,
			String toupdateMeterReader, String toupdateHHDId,
			String previousMeterReader, String previousHHDId, String userName) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {
			String query1 = QueryContants
					.updateHHDMeterReaderMappingDetailsQuery(toupdateMRKey,
							previousMeterReader, previousHHDId, userName);
			String query2 = QueryContants
					.insertHHDMeterReaderMappingDetailsQuery(getDefaultEffectiveTodateValue());
			// AppLog.info("updateHHDMeterReaderMappingDetailsQuery::" +
			// query1);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query1);
			/* int k = */ps.executeUpdate();
			// System.out.println("executeUpdate::" + k);
			if (null != toupdateHHDId
					&& !"".equalsIgnoreCase(toupdateHHDId.trim())
					&& null != toupdateMeterReader
					&& !"".equalsIgnoreCase(toupdateMeterReader.trim())) {
				if (null != ps) {
					ps.close();
				}
				// AppLog.info("insertHHDMeterReaderMappingDetailsQuery::"
				// + query2);
				ps = conn.prepareStatement(query2);
				int i = 0;
				ps.setString(++i, toupdateMRKey);
				ps.setString(++i, toupdateMeterReader);
				ps.setString(++i, toupdateHHDId);
				ps.setString(++i, userName);
				recordInserted = ps.executeUpdate();
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
		return recordInserted;
	}
}
