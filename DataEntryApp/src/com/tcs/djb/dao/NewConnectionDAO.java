/************************************************************************************************************
 * @(#) NewConnectionDAO.java   30 Nov 2012
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import com.tcs.djb.interfaces.NewConnectionInterface;
import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for New Connection DAF process.
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 * @since 30-11-2012
 * @history 13-06-2013 Matiur Rahman Changes has been made as per JTrac
 *          #DJB-1574. New method {@link #getKNOByZoneMRAreaNameFatherName()}
 *          and {@link #getKNOByZoneMRAreaWCNo()} added for validation.<br/>
 *          Two validation Added:<br/>
 *          <li>1.In case a DAF new connection has ZONE, MR, Area and WC No. -
 *          We must validate that no account exists in the system with the same
 *          combination of zone, MR, Area and WC No entered in DAF before
 *          request submission. If an account exists, an error message will be
 *          displayed during New Connection DAF request submission. The error
 *          message will display the account ID that exists in system for the
 *          Zone,MR,Area, WC No combination. This validation will be kept at RMS
 *          Portal end.(Hard Validation) * Hard Validation : System state will
 *          not change.</li><li>2. In case a DAF new connection has ZONE, MR,
 *          Area but WC No. is NULL - We must validate whether any DAF request
 *          with this ZONE, MR, Area,Consumer Name and Father Name has been
 *          submitted before, if yes a warning should be given with other
 *          details of the request. In case DAF user wants to go ahead, he
 *          should be allowed. (Soft Validation) * Soft Validation : System
 *          state will not change unless and until it is overridden by consumer.
 *          </li>
 * @history 28-11-2013 Matiur Rahman Added parameter zroLocation and modified
 *          Query as part of Implemented Restriction for data Entry as ZRO
 *          location for Hand Holding Staff as Per JTrac DJB-2013.
 * @history 28-11-2013 Matiur Rahman Added method {@link #getZoneByZROLocation}
 *          and {@link #populateNewConnectionDAFDetails} as Per JTrac DJB-2013.
 * @history 03-12-2013 Matiur Rahman Added method {@link #getZoneByZROLocation},
 *          {@link #getMrNo} and {@link #getAreaNo} as Per JTrac DJB-2013.
 * @history 05-12-2013 Matiur Rahman Added method
 *          {@link #updateNewConnectionDAFDetails} and
 *          {@link #updateNewConnectionDAFStatus} as Per JTrac DJB-2013.
 * @history 04-02-2014 Matiur Rahman Removed System.out.println and Unused
 *          Imports.
 * @history Reshma on 28-04-2016 edited the method
 *          {@link #populateNewConnectionDAFDetails(String)} to fetch data
 *          source from the database along with other details , JTrac DJB-4425.
 * @histroy 08-04-2016 Diksha Mukherjee Added Ground Water Billing
 *          Chracteristics as per requirements in DJB-4211         
 */
public class NewConnectionDAO implements NewConnectionInterface {

	/**
	 * <p>
	 * This method is used to get KNO By Zone MR Area Name Father Name.
	 * </p>
	 * 
	 * @param zoneNo
	 * @param mrNo
	 * @param areaNo
	 * @param entityName
	 * @param fatherHusbandName
	 * @return
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	public static boolean getKNOByZoneMRAreaNameFatherName(String zoneNo,
			String mrNo, String areaNo, String entityName,
			String fatherHusbandName) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT COUNT(1) TOTAL_RECORD  FROM CM_NEW_CONN_STG T WHERE  T.ZONE_NO = ?  AND T.MR_NO = ?  AND T.AREA_NO=?  AND UPPER(T.ENTITY_NAME) = UPPER(?)  AND UPPER(T.FATHER_HUS_NAME) = UPPER(?) ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, zoneNo);
			ps.setString(++i, mrNo);
			ps.setString(++i, areaNo);
			ps.setString(++i, entityName);
			ps.setString(++i, fatherHusbandName);
			rs = ps.executeQuery();
			int totalRecord = 0;
			if (rs.next()) {
				totalRecord = rs.getInt("TOTAL_RECORD");
			}
			if (totalRecord > 0) {
				exists = true;
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
		return exists;
	}

	/**
	 * <p>
	 * This method is used to get New Connection DAF Details.
	 * </p>
	 * 
	 * @param newConnectionDAFDetailsContainer
	 * @return newConnectionDAFDetails list
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	public static String getKNOByZoneMRAreaWCNo(String zoneNo, String mrNo,
			String areaNo, String wcNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String kno = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" select cm_get_actual_kno(cm_get_actual_mrkey(?,?,?,to_char(sysdate,'dd-MM-yyyy'),'dd-MM-yyyy'),?,to_char(sysdate,'dd-MM-yyyy'),'dd-MM-yyyy') AS ACCT_ID from dual");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, zoneNo);
			ps.setString(++i, mrNo);
			ps.setString(++i, areaNo);
			ps.setString(++i, wcNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				kno = rs.getString("ACCT_ID");
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
		return kno;
	}

	/**
	 * <p>
	 * This method is used to get Maximum Floor Value
	 * </p>
	 * 
	 * @return String
	 */
	public static String getMaxFloorValue() {
		AppLog.begin();
		String maxFloorValue = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT MAX(TO_NUMBER(CHAR_VAL))+1 MAX_FLOOR_VAL FROM CI_CHAR_VAL WHERE CHAR_TYPE_CD = 'FLOOR'");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				maxFloorValue = rs.getString("MAX_FLOOR_VAL");
			} else {
				maxFloorValue = "1";
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
		return maxFloorValue;
	}

	/**
	 * <p>
	 * This method is used to populate Meter Read Status List as drop down at data entry page.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static List<String> getMeterReadRemarkList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = "select distinct MTR_READ_TYPE_CD,MTR_STATS_CD from CM_MTR_STATS_LOOKUP where mtr_read_type_cd = '60' order by MTR_STATS_CD asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("MTR_STATS_CD").trim());
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
	 * This method is used to populate Water Connection Size List
	 * </p>
	 * 
	 * @return
	 */
	public static List<String> getWaterConnectionList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = "select t.char_val from ci_char_val t  where trim(char_type_cd) = 'WCONSIZE' and t.char_val <> 'N/A' ORDER BY to_number(t.char_val) asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// dataList.add("N/A");
			while (rs.next()) {
				dataList.add(rs.getString("char_val").trim());
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
	 * This method is used to get Zone By ZRO Location New Connection DAF Screen as part of as Per
	 * JTrac DJB-2013.
	 * </p>
	 * 
	 * @param sequenceNo
	 * @return ArrayList
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 28-11-2013
	 * @history 03-12-2013 Matiur Rahman modified Query as part of Implemented
	 *          Restriction for data Entry as ZRO location for DJB User as Per
	 *          JTrac DJB-2177.
	 */
	public static Map<String, String> getZoneByZROLocation(String zroLocation) {
		AppLog.begin();
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("  select distinct m.subzone_cd,s.subzone_name from djb_zn_mr_ar_mrd m, djb_mrd z,DJB_SUBZONE s where m.mrd_cd=z.mrd_cd and m.subzone_cd=s.subzone_cd ");

			/*
			 * Start: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			if (null != zroLocation && !"".equals(zroLocation)) {
				/*
				 * zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
				 * zroLocation = zroLocation.replaceAll("',''", "").replaceAll(
				 * "'',", "");
				 */
				String locArray[] = zroLocation.split(",");
				String tempLocList = "";
				for (int i = 0; i < locArray.length; i++) {
					String tempString = locArray[i];
					if (null != tempString
							&& !"".equalsIgnoreCase(tempString.trim())) {
						tempLocList += "," + tempString;
					}
				}
				if (tempLocList.indexOf(',') == 0) {
					tempLocList = tempLocList.substring(1);
				}
				zroLocation = tempLocList;
				zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
				zroLocation = zroLocation.replaceAll("',''", "").replaceAll(
						"'',", "");

				querySB.append(" and z.zro_cd in (" + zroLocation + " ) ");
			}
			/*
			 * End: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			querySB.append(" order by s.subzone_name ");
			// System.out.println("Query::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			/*
			 * Start: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			// ps.setString(1, zroLocation);
			/*
			 * End: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */

			rs = ps.executeQuery();
			while (rs.next()) {
				returnMap.put(rs.getString("subzone_cd").trim(), rs
						.getString("subzone_name")
						+ "(" + rs.getString("subzone_cd") + ")");
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
		return returnMap;
	}

	/**
	 * <p>
	 * This method is used to get ZRO LocationBy User ID New Connection DAF Screen as part of as Per
	 * JTrac DJB-2013.
	 * </p>
	 * 
	 * @param sequenceNo
	 * @return ArrayList
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 28-11-2013
	 */
	public static String getZROLocationByUserID(String userId) {
		AppLog.begin();
		String zroLocation = "ABSENT";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("  SELECT GET_ZRO_LOCATION_BY_USER(?)ZRO_LOCATION FROM DUAL ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroLocation = rs.getString("ZRO_LOCATION");
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
		return zroLocation;
	}

	/**
	 * <p>
	 * This method is used to get Application Purpose Drop down List
	 * </p>
	 * 
	 * @return Map
	 */
	public Map<String, String> getApplicationPurposeMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> applicationPurposeMap = new LinkedHashMap<String, String>();

		try {
			StringBuffer querySB = new StringBuffer();

			/*
			 * querySB.append(
			 * "SELECT SA_TYPE_CD,DESCR FROM CI_SA_TYPE_L WHERE (SA_TYPE_CD = 'SAWATR' OR SA_TYPE_CD = 'SAWATSEW') ORDER BY DESCR"
			 * );
			 */
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			querySB
					.append("SELECT SA_TYPE_CD,DESCR FROM CI_SA_TYPE_L WHERE (SA_TYPE_CD = 'SAWATR' OR SA_TYPE_CD = 'SAWATSEW' OR SA_TYPE_CD = 'GWNPSA' OR SA_TYPE_CD='GWPSA') ORDER BY DESCR");
			// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				applicationPurposeMap.put(rs.getString("SA_TYPE_CD").trim(), rs
						.getString("DESCR").trim());
			}

		} catch (SystemException e) {
			AppLog.error(e);

		} catch (IOException e) {
			AppLog.error(e);

		} catch (SQLException e) {
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

		return applicationPurposeMap;
	}

	/**
	 * <p>
	 * This method is used to get Area No for Zone MR Combination
	 * </p>
	 * 
	 * @param zoneNo
	 * @param mrNo
	 * @return Map
	 * @history 03-12-2013 Matiur Rahman modified Query as part of Implemented
	 *          Restriction for data Entry as ZRO location for DJB User as Per
	 *          JTrac DJB-2177.
	 */
	public Map<String, String> getAreaNo(String zoneNo, String mrNo,
			String zroLocation) {
		AppLog.begin();
		Map<String, String> areaNoMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" select distinct t.area_cd,t.area_desc from djb_zn_mr_ar_mrd t, djb_mrd z where t.mrd_cd=z.mrd_cd and (SYSDATE BETWEEN t.EFF_FROM_DATE AND NVL(t.EFF_TO_DATE, SYSDATE)) and t.subzone_cd=? and t.mr_cd=? ");
			/*
			 * Start: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			// if (null != zroLocation && !"".equals(zroLocation)) {
			// querySB.append(" and z.zro_cd=? ");
			// }
			/*
			 * End: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			/*
			 * Start: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			if (null != zroLocation && !"".equals(zroLocation)) {
				zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
				zroLocation = zroLocation.replaceAll("',''", "").replaceAll(
						"'',", "");
				querySB.append(" and z.zro_cd in (" + zroLocation + ")");
			}
			/*
			 * End: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			querySB.append(" order by t.area_desc asc ");
			// System.out.println("Query::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, zoneNo);
			ps.setString(2, mrNo);
			// if (null != zroLocation && !"".equals(zroLocation)) {
			// ps.setString(3, zroLocation);
			// }
			rs = ps.executeQuery();
			while (rs.next()) {
				areaNoMap.put(rs.getString("area_cd").trim(), rs.getString(
						"area_desc").trim());
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
		return areaNoMap;
	}

	/**
	 * <p>
	 * This method is used to get Id Type Drop Down List
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getIdTypeListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> personIdTypeListMap = new LinkedHashMap<String, String>();

		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("SELECT ID_TYPE_CD,DESCR FROM CI_ID_TYPE_L ORDER BY DESCR");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				personIdTypeListMap.put(rs.getString("ID_TYPE_CD").trim(), rs
						.getString("DESCR").trim());
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
		return personIdTypeListMap;
	}

	/**
	 * <p>
	 * This method is used to get Is DJBEmployee Drop down List
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getIsDJBEmployeeMap() {
		AppLog.begin();
		Map<String, String> isDJBEmployeeMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("SELECT CHAR_VAL,DESCR FROM CI_CHAR_VAL_L WHERE CHAR_TYPE_CD = 'DJB_EMP' ORDER BY DESCR desc");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				isDJBEmployeeMap.put(rs.getString("CHAR_VAL").trim(), rs
						.getString("DESCR").trim());
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
		return isDJBEmployeeMap;
	}

	/**
	 * <p>
	 * This method is used to get Locality Drop down list as per pinCode
	 * </p>
	 * 
	 * @param pinCode
	 * @return Map
	 */
	public Map<String, String> getLocality(String pinCode) {
		AppLog.begin();
		Map<String, String> areaNoMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT AREA_CODE, AREA_DESC FROM AREA INNER JOIN CI_STATE ON TRIM(CI_STATE.STATE) = TRIM(AREA.AREA_CODE) WHERE PIN_CODE = ? ORDER BY AREA_DESC ASC ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, pinCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				areaNoMap.put(rs.getString("AREA_CODE").trim(), rs.getString(
						"AREA_DESC").trim());
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
		return areaNoMap;
	}

	/**
	 * <p>
	 * This method is used to POPULATE Mall Cineplex Options Added by Matiur
	 * Rahman on 18-12-2012
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getMallCineplexOption() {
		AppLog.begin();
		Map<String, String> mallCineplexOptionMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT CHAR_VAL,DESCR FROM CI_CHAR_VAL_L WHERE CHAR_TYPE_CD = 'MALLCINX' ORDER BY DESCR ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mallCineplexOptionMap.put(rs.getString("CHAR_VAL").trim(), rs
						.getString("DESCR").trim());
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
		return mallCineplexOptionMap;
	}

	/**
	 * <p>
	 * This method is used to get Mr No list as per Zone
	 * </p>
	 * 
	 * @param zoneNo
	 * @return ArrayList
	 * @history 28-11-2013 Matiur Rahman Added parameter zroLocation and
	 *          modified Query as part of Implemented Restriction for data Entry
	 *          as ZRO location for Hand Holding Staff as Per JTrac DJB-2013.
	 * @history 03-12-2013 Matiur Rahman modified Query as part of Implemented
	 *          Restriction for data Entry as ZRO location for DJB User as Per
	 *          JTrac DJB-2177.
	 */
	public List<String> getMrNo(String zoneNo, String zroLocation) {
		AppLog.begin();
		List<String> mrNoList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			// querySB
			// .append(" select distinct t.mr_cd from djb_zn_mr_ar_mrd t where (SYSDATE BETWEEN EFF_FROM_DATE AND NVL(EFF_TO_DATE, SYSDATE)) and TRIM(t.subzone_cd)=? order by to_number(t.mr_cd) asc ");
			querySB
					.append(" select distinct t.mr_cd from djb_zn_mr_ar_mrd t, djb_mrd z where t.mrd_cd=z.mrd_cd and (SYSDATE BETWEEN t.EFF_FROM_DATE AND NVL(t.EFF_TO_DATE, SYSDATE)) and TRIM(t.subzone_cd)=? ");
			/*
			 * Start: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			// if (null != zroLocation && !"".equals(zroLocation)) {
			// querySB.append(" and z.zro_cd=? ");
			// }
			/*
			 * End: Commenting 03-12-2013 Matiur Rahman modified Query as part
			 * of Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			/*
			 * Start: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			if (null != zroLocation && !"".equals(zroLocation)) {
				zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
				zroLocation = zroLocation.replaceAll("',''", "").replaceAll(
						"'',", "");
				querySB.append(" and z.zro_cd in (" + zroLocation + ")");
			}
			/*
			 * End: Adding 03-12-2013 Matiur Rahman modified Query as part of
			 * Implemented Restriction for data Entry as ZRO location for DJB
			 * User as Per JTrac DJB-2177.
			 */
			querySB.append(" order by to_number(t.mr_cd) asc");
			// System.out.println("Query::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, zoneNo);
			// if (null != zroLocation && !"".equals(zroLocation)) {
			// zroLocation = "'" + zroLocation.replaceAll(",", "','") + "'";
			// ps.setString(2, zroLocation);
			// }
			rs = ps.executeQuery();
			while (rs.next()) {
				mrNoList.add(rs.getString("mr_cd").trim());
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
		return mrNoList;
	}

	/**
	 * <p>
	 * This method is used to get Property type drop down list
	 * </p>
	 * 
	 * @return Map
	 */
	public Map<String, String> getPropertyTypeMap() {
		AppLog.begin();
		Map<String, String> propertyTypeMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT PRPT_CODE,PRPT_VALUE FROM CM_PROPERTY_TYPE WHERE NEW_CONNECTION_FLAG = 'Y' ORDER BY PRPT_CODE ASC ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				propertyTypeMap.put(rs.getString("PRPT_CODE").trim(), rs
						.getString("PRPT_VALUE").trim());
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
		return propertyTypeMap;
	}

	/**
	 * <p>
	 * This method is used to get Sub Locality list as per locality
	 * </p>
	 * 
	 * @param locality
	 * @return
	 */
	public Map<String, String> getSubLocality(String locality) {
		AppLog.begin();
		Map<String, String> subAreaNoMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT SUB_AREA_CODE, SUB_AREA_NAME FROM SUB_AREA WHERE AREA_CODE = ? ORDER BY SUB_AREA_NAME ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, locality);
			rs = ps.executeQuery();
			while (rs.next()) {
				subAreaNoMap.put(rs.getString("SUB_AREA_CODE").trim(), rs
						.getString("SUB_AREA_NAME").trim());
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
		return subAreaNoMap;
	}

	/**
	 * <p>
	 * This method is used to get Water Connection Type Drop down list
	 * </p>
	 * 
	 * @return Map
	 */
	public Map<String, String> getWaterConnectionTypeMap() {
		AppLog.begin();
		Map<String, String> waterConnectionTypeMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT CUST_CL_CD,DESCR FROM CI_CUST_CL_L WHERE CUST_CL_CD <> 'N' ORDER BY DESCR");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				waterConnectionTypeMap.put(rs.getString("CUST_CL_CD").trim(),
						rs.getString("DESCR").trim());
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
		return waterConnectionTypeMap;
	}

	/**
	 * <p>
	 * This method is used to get Zone Code Drop down List
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getZoneCodeListMap() {
		AppLog.begin();
		Map<String, String> zoneCodeListMap = new LinkedHashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append("  select t.subzone_cd,t.subzone_name from djb_subzone t order by t.subzone_name asc ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				zoneCodeListMap.put(rs.getString("subzone_cd").trim(), rs
						.getString("subzone_name").trim());
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
		return zoneCodeListMap;
	}

	/**
	 * <p>
	 * This method is used to populate New Connection DAF Details as part of as
	 * Per JTrac DJB-2013.
	 * </p>
	 * 
	 * @param sequenceNo
	 * @return ArrayList
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 28-11-2013
	 * @history Reshma on 28-04-2016 edited the method
	 *          {@link #populateNewConnectionDAFDetails(String)} to fetch data
	 *          source from the database along with other details , JTrac
	 *          DJB-4425.
	 */
	public NewConnectionDAFDetailsContainer populateNewConnectionDAFDetails(
			String sequenceNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = null;
		try {
			StringBuffer querySB = new StringBuffer(512);
			querySB
					.append(" SELECT SEQ,ENTITY_NAME,FATHER_HUS_NAME,ID_TYPE_CD ,PER_ID_NBR ,HOUSE_NO,ROAD_NO,SUBLOCALITY_1,");

			querySB
					.append(" SUBLOCALITY_2 ,SUB_COLONY ,VILLAGE,KHASRA_NO ,SOCIETY_NAME,PIN_CODE ,LOCALITY , SUB_LOCALITY ,JJR_COLONY ,PROP_TYPE,URBAN ,");
			querySB
					.append(" NO_OF_FLR ,NO_OF_ROOMS_FS_BEDS ,PLOT_AREA,BUILT_UP_AREA,WCON_TYPE ,WCON_USE,NO_OF_OCC_DWELL_UNITS,NO_OF_UNOCC_DWELL_UNITS,");
			querySB
					.append(" NO_OF_CHILDREN,NO_OF_ADULTS,IS_DJB ,MTR_SIZE ,MTR_TYPE ,TO_CHAR(INI_REG_READ_DATE,'DD/MM/YYYY')INI_REG_READ_DATE ,INI_REG_READ_REMK ,INI_REG_READ,");
			querySB
					.append(" AVG_CONS,OPENING_BALANCE ,APP_PURPOSE ,ZONE_NO ,MR_NO ,AREA_NO,CREATE_BY,WCNO,SA_TYPE,DAF_ID ");
			/**
			 * Start : Reshma on 28-04-2016 added the below to fetch data source
			 * from the database along with other details , JTrac DJB-4425.
			 */
			querySB.append(" ,DATA_SOURCE ");
			/**
			 * End : Reshma on 28-04-2016 added the below to fetch data source
			 * from the database along with other details , JTrac DJB-4425.
			 */
			/**
			 * Start : Atanu on 05-05-2016 added the below to fetch data source
			 * from the database along with other details , JTrac DJB-4211.
			 */
			querySB.append(" ,NO_OF_BOREWELLS ");
			/**
			 * End : Atanu on 05-05-2016 added the below to fetch data source
			 * from the database along with other details , JTrac DJB-4211.
			 */
			querySB.append(" FROM CM_NEW_CONN_STG ");
			querySB.append(" WHERE 1=1 ");
			querySB.append(" AND ACCT_ID IS NULL ");
			querySB.append(" AND SEQ=? ");
			// System.out.println("querySB::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, sequenceNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				newConnectionDAFDetailsContainer = new NewConnectionDAFDetailsContainer();
				newConnectionDAFDetailsContainer.setSequenceNo(null == rs
						.getString("SEQ") ? "" : rs.getString("SEQ").trim());
				newConnectionDAFDetailsContainer.setEntityName(null == rs
						.getString("ENTITY_NAME") ? "" : rs.getString(
						"ENTITY_NAME").trim());
				newConnectionDAFDetailsContainer
						.setFatherHusbandName(null == rs
								.getString("FATHER_HUS_NAME") ? "" : rs
								.getString("FATHER_HUS_NAME").trim());
				newConnectionDAFDetailsContainer.setPersonIdType(null == rs
						.getString("ID_TYPE_CD") ? "" : rs.getString(
						"ID_TYPE_CD").trim());
				newConnectionDAFDetailsContainer.setPersonIdNumber(null == rs
						.getString("PER_ID_NBR") ? "" : rs.getString(
						"PER_ID_NBR").trim());
				newConnectionDAFDetailsContainer.setHouseNumber(null == rs
						.getString("HOUSE_NO") ? "" : rs.getString("HOUSE_NO")
						.trim());
				newConnectionDAFDetailsContainer.setRoadNumber(null == rs
						.getString("ROAD_NO") ? "" : rs.getString("ROAD_NO")
						.trim());
				newConnectionDAFDetailsContainer.setSublocality1(null == rs
						.getString("SUBLOCALITY_1") ? "" : rs.getString(
						"SUBLOCALITY_1").trim());
				newConnectionDAFDetailsContainer.setSublocality2(null == rs
						.getString("SUBLOCALITY_2") ? "" : rs.getString(
						"SUBLOCALITY_2").trim());
				newConnectionDAFDetailsContainer.setSubColony(null == rs
						.getString("SUB_COLONY") ? "" : rs.getString(
						"SUB_COLONY").trim());
				newConnectionDAFDetailsContainer.setVillage(null == rs
						.getString("VILLAGE") ? "" : rs.getString("VILLAGE")
						.trim());
				newConnectionDAFDetailsContainer.setKhashraNumber(null == rs
						.getString("KHASRA_NO") ? "" : rs
						.getString("KHASRA_NO").trim());
				newConnectionDAFDetailsContainer.setSocietyName(null == rs
						.getString("SOCIETY_NAME") ? "" : rs.getString(
						"SOCIETY_NAME").trim());
				newConnectionDAFDetailsContainer.setPinCode(null == rs
						.getString("PIN_CODE") ? "" : rs.getString("PIN_CODE")
						.trim());
				newConnectionDAFDetailsContainer.setLocality(null == rs
						.getString("LOCALITY") ? "" : rs.getString("LOCALITY")
						.trim());
				newConnectionDAFDetailsContainer.setSubLocality(null == rs
						.getString("SUB_LOCALITY") ? "" : rs.getString(
						"SUB_LOCALITY").trim());
				newConnectionDAFDetailsContainer.setJjrColony(null == rs
						.getString("JJR_COLONY") ? "" : rs.getString(
						"JJR_COLONY").trim());
				newConnectionDAFDetailsContainer.setPropertyType(null == rs
						.getString("PROP_TYPE") ? "" : rs
						.getString("PROP_TYPE").trim());
				newConnectionDAFDetailsContainer
						.setUrban(null == rs.getString("URBAN") ? "" : rs
								.getString("URBAN").trim());
				String propertyType = (null == rs.getString("PROP_TYPE") ? ""
						: rs.getString("PROP_TYPE").trim());
				if ("Hotel/Guest Houses".equalsIgnoreCase(propertyType)
						|| "Dharamshalas/Hostels"
								.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms(null == rs
							.getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs
							.getString("NO_OF_ROOMS_FS_BEDS").trim());
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Hospital/Nursing Home"
						.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds(null == rs
							.getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs
							.getString("NO_OF_ROOMS_FS_BEDS").trim());
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Banquet Hall".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite(null == rs
							.getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs
							.getString("NO_OF_ROOMS_FS_BEDS").trim());
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Mall/Cineplex".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex(null == rs
							.getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs
							.getString("NO_OF_ROOMS_FS_BEDS").trim());
				} else {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				}
				newConnectionDAFDetailsContainer.setNoOfFloors(null == rs
						.getString("NO_OF_FLR") ? "" : rs
						.getString("NO_OF_FLR").trim());
				// newConnectionDAFDetailsContainer.setNoOfRooms(null == rs
				// .getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs.getString(
				// "NO_OF_ROOMS_FS_BEDS").trim());
				// newConnectionDAFDetailsContainer.setNoOfBeds(null == rs
				// .getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs.getString(
				// "NO_OF_ROOMS_FS_BEDS").trim());
				// newConnectionDAFDetailsContainer.setFunctionSite(null == rs
				// .getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs.getString(
				// "NO_OF_ROOMS_FS_BEDS").trim());
				// newConnectionDAFDetailsContainer.setMallCineplex(null == rs
				// .getString("NO_OF_ROOMS_FS_BEDS") ? "" : rs.getString(
				// "NO_OF_ROOMS_FS_BEDS").trim());
				newConnectionDAFDetailsContainer.setPlotArea(null == rs
						.getString("PLOT_AREA") ? "" : rs
						.getString("PLOT_AREA").trim());
				newConnectionDAFDetailsContainer.setBuiltUpArea(null == rs
						.getString("BUILT_UP_AREA") ? "" : rs.getString(
						"BUILT_UP_AREA").trim());
				newConnectionDAFDetailsContainer
						.setWaterConnectionType(null == rs
								.getString("WCON_TYPE") ? "" : rs.getString(
								"WCON_TYPE").trim());
				newConnectionDAFDetailsContainer
						.setWaterConnectionUse(null == rs.getString("WCON_USE") ? ""
								: rs.getString("WCON_USE").trim());
				newConnectionDAFDetailsContainer
						.setNoOfOccDwellUnits(null == rs
								.getString("NO_OF_OCC_DWELL_UNITS") ? "" : rs
								.getString("NO_OF_OCC_DWELL_UNITS").trim());
				newConnectionDAFDetailsContainer
						.setNoOfUnoccDwellUnits(null == rs
								.getString("NO_OF_UNOCC_DWELL_UNITS") ? "" : rs
								.getString("NO_OF_UNOCC_DWELL_UNITS").trim());
				newConnectionDAFDetailsContainer.setNoOfChildren(null == rs
						.getString("NO_OF_CHILDREN") ? "" : rs.getString(
						"NO_OF_CHILDREN").trim());
				newConnectionDAFDetailsContainer.setNoOfAdults(null == rs
						.getString("NO_OF_ADULTS") ? "" : rs.getString(
						"NO_OF_ADULTS").trim());
				newConnectionDAFDetailsContainer.setIsDJB(null == rs
						.getString("IS_DJB") ? "" : rs.getString("IS_DJB")
						.trim());
				newConnectionDAFDetailsContainer.setSizeOfMeter(null == rs
						.getString("MTR_SIZE") ? "" : rs.getString("MTR_SIZE")
						.trim());
				newConnectionDAFDetailsContainer.setTypeOfMeter(null == rs
						.getString("MTR_TYPE") ? "" : rs.getString("MTR_TYPE")
						.trim());
				newConnectionDAFDetailsContainer
						.setInitialRegisterReadDate(null == rs
								.getString("INI_REG_READ_DATE") ? "" : rs
								.getString("INI_REG_READ_DATE").trim());
				newConnectionDAFDetailsContainer
						.setInitialRegisterReadRemark(null == rs
								.getString("INI_REG_READ_REMK") ? "" : rs
								.getString("INI_REG_READ_REMK").trim());
				newConnectionDAFDetailsContainer.setInitialRegisetrRead(rs
						.getBigDecimal("INI_REG_READ"));
				newConnectionDAFDetailsContainer
						.setAverageConsumption(null == rs.getString("AVG_CONS") ? ""
								: rs.getString("AVG_CONS").trim());
				newConnectionDAFDetailsContainer.setOpeningBalance(rs
						.getBigDecimal("OPENING_BALANCE"));
				newConnectionDAFDetailsContainer
						.setApplicationPurpose(null == rs
								.getString("APP_PURPOSE") ? "" : rs.getString(
								"APP_PURPOSE").trim());
				newConnectionDAFDetailsContainer.setZoneNo(null == rs
						.getString("ZONE_NO") ? "" : rs.getString("ZONE_NO")
						.trim());
				newConnectionDAFDetailsContainer
						.setMrNo(null == rs.getString("MR_NO") ? "" : rs
								.getString("MR_NO").trim());
				newConnectionDAFDetailsContainer.setAreaNo(null == rs
						.getString("AREA_NO") ? "" : rs.getString("AREA_NO")
						.trim());
				newConnectionDAFDetailsContainer.setWcNo(null == rs
						.getString("WCNO") ? "" : rs.getString("WCNO").trim());
				newConnectionDAFDetailsContainer.setSaTypeCode(null == rs
						.getString("SA_TYPE") ? "" : rs.getString("SA_TYPE")
						.trim());
				newConnectionDAFDetailsContainer.setDafId(null == rs
						.getString("DAF_ID") ? "" : rs.getString("DAF_ID")
						.trim());
				/**
				 * Start : Reshma on 28-04-2016 added the below to fetch data
				 * source from the database along with other details , JTrac
				 * DJB-4425.
				 */
				newConnectionDAFDetailsContainer.setDataSource(null == rs
						.getString("DATA_SOURCE") ? "" : rs.getString(
						"DATA_SOURCE").trim());
				/**
				 * End : Reshma on 28-04-2016 added the below to fetch data
				 * source from the database along with other details , JTrac
				 * DJB-4425.
				 */
				// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
				// Apr 2016
				newConnectionDAFDetailsContainer.setNoOfBorewells(null == rs
						.getString("NO_OF_BOREWELLS") ? "" : rs.getString(
						"NO_OF_BOREWELLS").trim());
				// End Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
				// Apr 2016
			}
		} catch (SQLException e) {
			AppLog.error(e);
			// e.printStackTrace();
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
		AppLog.end();
		return newConnectionDAFDetailsContainer;

	}

	/* (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#processNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer, java.lang.String)
	 */
	@Override
	public Map<Object, Object> processNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer,
			String authCookie) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * This method is used to save New Connection DAF Details to Database
	 * </p>
	 * 
	 * @history 28-11-2013 Matiur Rahman Added new column insert for APP_STATUS
	 *          as default value E as Per JTrac DJB-2013.
	 */
	@Override
	public Map<Object, Object> saveNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnContainer) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sequenceNo = "";
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		try {
			StringBuffer seqQuery = new StringBuffer(512);
			StringBuffer insertQuery = new StringBuffer(512);
			seqQuery
					.append(" select SEQ_NEW_CONN.nextval SEQ_NEW_CONN from dual");
			insertQuery
					.append(" INSERT INTO CM_NEW_CONN_STG( SEQ,ENTITY_NAME,FATHER_HUS_NAME,ID_TYPE_CD ,PER_ID_NBR ,HOUSE_NO,ROAD_NO,SUBLOCALITY_1,");

			insertQuery
					.append(" SUBLOCALITY_2 ,SUB_COLONY ,VILLAGE,KHASRA_NO ,SOCIETY_NAME,PIN_CODE ,LOCALITY , SUB_LOCALITY ,JJR_COLONY ,PROP_TYPE,URBAN ,");
			insertQuery
					.append(" NO_OF_FLR ,NO_OF_ROOMS_FS_BEDS ,PLOT_AREA,BUILT_UP_AREA,WCON_TYPE ,WCON_USE,NO_OF_OCC_DWELL_UNITS,NO_OF_UNOCC_DWELL_UNITS,");
			insertQuery
					.append(" NO_OF_CHILDREN,NO_OF_ADULTS,IS_DJB ,MTR_SIZE ,MTR_TYPE ,INI_REG_READ_DATE ,INI_REG_READ_REMK ,INI_REG_READ,");
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th adding a new column no of borewells
			// Apr 2016
			/*insertQuery
			.append(" AVG_CONS,OPENING_BALANCE ,APP_PURPOSE ,ZONE_NO ,MR_NO ,AREA_NO,CREATE_BY,WCNO,SA_TYPE,DAF_ID,APP_STATUS,ZRO_LOCATION)");
			insertQuery
			.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,'E',(select distinct z.zro_cd from djb_zn_mr_ar_mrd m, djb_mrd z,DJB_SUBZONE s where m.mrd_cd=z.mrd_cd and m.subzone_cd=? and m.mr_cd=? and m.area_cd=?))");*/
			insertQuery
					.append(" AVG_CONS,OPENING_BALANCE ,APP_PURPOSE ,ZONE_NO ,MR_NO ,AREA_NO,CREATE_BY,WCNO,SA_TYPE,DAF_ID,APP_STATUS,ZRO_LOCATION,NO_OF_BOREWELLS)");
			insertQuery
					.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,'E',(select distinct z.zro_cd from djb_zn_mr_ar_mrd m, djb_mrd z,DJB_SUBZONE s where m.mrd_cd=z.mrd_cd and m.subzone_cd=? and m.mr_cd=? and m.area_cd=?),?)");
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			AppLog.info("\n############Insert Query:: "
					+ insertQuery.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(seqQuery.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				sequenceNo = rs.getString(1);
			}
			// System.out.println("sequenceNo::" + sequenceNo);
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(insertQuery.toString());
			int i = 0;
			ps.setString(++i, sequenceNo);

			AppLog.info("\n############I :: " + i + " :::: Value::"
					+ sequenceNo);
			ps.setString(++i, newConnContainer.getEntityName());
			ps.setString(++i, newConnContainer.getFatherHusbandName());
			ps.setString(++i, newConnContainer.getPersonIdType());
			ps.setString(++i, newConnContainer.getPersonIdNumber());
			ps.setString(++i, newConnContainer.getHouseNumber());
			ps.setString(++i, newConnContainer.getRoadNumber());
			ps.setString(++i, newConnContainer.getSublocality1());
			ps.setString(++i, newConnContainer.getSublocality2());
			ps.setString(++i, newConnContainer.getSubColony());
			ps.setString(++i, newConnContainer.getVillage());
			ps.setString(++i, newConnContainer.getKhashraNumber());
			ps.setString(++i, newConnContainer.getSocietyName());
			ps.setString(++i, newConnContainer.getPinCode());
			ps.setString(++i, newConnContainer.getLocality());
			ps.setString(++i, newConnContainer.getSubLocality());
			ps.setString(++i, newConnContainer.getJjrColony());
			ps.setString(++i, newConnContainer.getPropertyType());
			ps.setString(++i, newConnContainer.getUrban());
			ps.setString(++i, newConnContainer.getNoOfFloors());
			// Start- Modified by matiur Rahman on 18-12-2012
			// ps.setString(++i, newConnContainer.getNoOfRooms());
			if (null != newConnContainer.getNoOfRooms()
					&& !"".equalsIgnoreCase(newConnContainer.getNoOfRooms()
							.trim())) {
				ps.setString(++i, newConnContainer.getNoOfRooms());
			} else if (null != newConnContainer.getNoOfBeds()
					&& !"".equalsIgnoreCase(newConnContainer.getNoOfBeds()
							.trim())) {
				ps.setString(++i, newConnContainer.getNoOfBeds());
			} else if (null != newConnContainer.getFunctionSite()
					&& !"".equalsIgnoreCase(newConnContainer.getFunctionSite()
							.trim())) {
				ps.setString(++i, newConnContainer.getFunctionSite());
			} else if (null != newConnContainer.getMallCineplex()
					&& !"".equalsIgnoreCase(newConnContainer.getMallCineplex()
							.trim())) {
				ps.setString(++i, newConnContainer.getMallCineplex());
			} else {
				ps.setString(++i, null);
			}
			// End- Modified by matiur Rahman on 18-12-2012
			ps.setString(++i, newConnContainer.getPlotArea());
			ps.setString(++i, newConnContainer.getBuiltUpArea());
			ps.setString(++i, newConnContainer.getWaterConnectionType());
			ps.setString(++i, newConnContainer.getWaterConnectionUse());
			ps.setString(++i, newConnContainer.getNoOfOccDwellUnits());
			ps.setString(++i, newConnContainer.getNoOfUnoccDwellUnits());
			ps.setString(++i, newConnContainer.getNoOfChildren());
			ps.setString(++i, newConnContainer.getNoOfAdults());
			ps.setString(++i, newConnContainer.getIsDJB());
			ps.setString(++i, newConnContainer.getSizeOfMeter());
			ps.setString(++i, newConnContainer.getTypeOfMeter());
			ps.setString(++i, newConnContainer.getInitialRegisterReadDate());
			ps.setString(++i, newConnContainer.getInitialRegisterReadRemark());
			ps.setBigDecimal(++i, newConnContainer.getInitialRegisetrRead());
			ps.setString(++i, newConnContainer.getAverageConsumption());
			ps.setBigDecimal(++i, newConnContainer.getOpeningBalance());
			ps.setString(++i, newConnContainer.getApplicationPurpose());
			ps.setString(++i, newConnContainer.getZoneNo());
			ps.setString(++i, newConnContainer.getMrNo());
			ps.setString(++i, newConnContainer.getAreaNo());
			ps.setString(++i, newConnContainer.getCreatedBy());
			ps.setString(++i, newConnContainer.getWcNo());
			ps.setString(++i, newConnContainer.getSaTypeCode());
			ps.setString(++i, newConnContainer.getDafId());
			// ps.setString(++i, newConnContainer.getZroLocation());
			ps.setString(++i, newConnContainer.getZoneNo());
			ps.setString(++i, newConnContainer.getMrNo());
			ps.setString(++i, newConnContainer.getAreaNo());
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016	
			ps.setString(++i, newConnContainer.getNoOfBorewells());
			// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			int rowInserted = ps.executeUpdate();
			// System.out.println("::rowInseted::" + rowInserted);

			if (!(rowInserted > 0)) {
				sequenceNo = "";
			}
			returnMap.put("sequenceNo", sequenceNo);
		} catch (SystemException e) {
			AppLog.error(e);
			returnMap.put("sequenceNo", null);
			// e.printStackTrace();
		} catch (IOException e) {
			AppLog.error(e);
			returnMap.put("sequenceNo", null);
			// e.printStackTrace();
		} catch (SQLException e) {
			AppLog.error(e);
			returnMap.put("sequenceNo", null);
			// e.printStackTrace();
		} catch (Exception e) {
			AppLog.error(e);
			returnMap.put("sequenceNo", null);
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
		AppLog.end();
		return returnMap;

	}

	/**
	 * <p>
	 * This method is used to update New Connection DAF Details to Database
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.djb.interfaces.NewConnectionInterface#updateNewConnectionDAFDetails
	 * (com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	public int updateNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnContainer) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer(512);
			querySB
					.append(" UPDATE CM_NEW_CONN_STG SET ENTITY_NAME=?,FATHER_HUS_NAME=?,ID_TYPE_CD =?,PER_ID_NBR =?,HOUSE_NO=?,ROAD_NO=?,SUBLOCALITY_1=?,");

			querySB
					.append(" SUBLOCALITY_2 =?,SUB_COLONY =?,VILLAGE=?,KHASRA_NO =?,SOCIETY_NAME=?,PIN_CODE =?,LOCALITY =?, SUB_LOCALITY =?,JJR_COLONY =?,PROP_TYPE=?,URBAN =?,");
			querySB
					.append(" NO_OF_FLR =?,NO_OF_ROOMS_FS_BEDS =?,PLOT_AREA=?,BUILT_UP_AREA=?,WCON_TYPE =?,WCON_USE=?,NO_OF_OCC_DWELL_UNITS=?,NO_OF_UNOCC_DWELL_UNITS=?,");
			querySB
					.append(" NO_OF_CHILDREN=?,NO_OF_ADULTS=?,IS_DJB =?,MTR_SIZE =?,MTR_TYPE =?,INI_REG_READ_DATE =to_date(?,'dd/mm/yyyy'),INI_REG_READ_REMK =?,INI_REG_READ=?,");
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			/*querySB
					.append(" AVG_CONS=?,OPENING_BALANCE =?,APP_PURPOSE =?,ZONE_NO =?,MR_NO =?,AREA_NO=?,DATA_UPDT_BY=?,DATA_UPDT_DTTM=SYSTIMESTAMP,LAST_UPDT_BY=?,LAST_UPDT_DTTM=SYSTIMESTAMP,WCNO=?,SA_TYPE=?,DAF_ID=?,ZRO_LOCATION=(select distinct z.zro_cd from djb_zn_mr_ar_mrd m, djb_mrd z,DJB_SUBZONE s where m.mrd_cd=z.mrd_cd and m.subzone_cd=? and m.mr_cd=? and m.area_cd=?) ");*/
			querySB
					.append(" AVG_CONS=?,OPENING_BALANCE =?,APP_PURPOSE =?,ZONE_NO =?,MR_NO =?,AREA_NO=?,DATA_UPDT_BY=?,DATA_UPDT_DTTM=SYSTIMESTAMP,LAST_UPDT_BY=?,LAST_UPDT_DTTM=SYSTIMESTAMP,WCNO=?,SA_TYPE=?,DAF_ID=?,ZRO_LOCATION=(select distinct z.zro_cd from djb_zn_mr_ar_mrd m, djb_mrd z,DJB_SUBZONE s where m.mrd_cd=z.mrd_cd and m.subzone_cd=? and m.mr_cd=? and m.area_cd=?),NO_OF_BOREWELLS=? ");
			// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			querySB.append(" WHERE APP_STATUS='E'");
			querySB.append(" AND ACCT_ID IS NULL ");
			querySB.append(" AND SEQ=? ");
			AppLog.info("##############NewConnection Details Update Query is::\n"+querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, newConnContainer.getEntityName());
			ps.setString(++i, newConnContainer.getFatherHusbandName());
			ps.setString(++i, newConnContainer.getPersonIdType());
			ps.setString(++i, newConnContainer.getPersonIdNumber());
			ps.setString(++i, newConnContainer.getHouseNumber());
			ps.setString(++i, newConnContainer.getRoadNumber());
			ps.setString(++i, newConnContainer.getSublocality1());
			ps.setString(++i, newConnContainer.getSublocality2());
			ps.setString(++i, newConnContainer.getSubColony());
			ps.setString(++i, newConnContainer.getVillage());
			ps.setString(++i, newConnContainer.getKhashraNumber());
			ps.setString(++i, newConnContainer.getSocietyName());
			ps.setString(++i, newConnContainer.getPinCode());
			ps.setString(++i, newConnContainer.getLocality());
			ps.setString(++i, newConnContainer.getSubLocality());
			ps.setString(++i, newConnContainer.getJjrColony());
			ps.setString(++i, newConnContainer.getPropertyType());
			ps.setString(++i, newConnContainer.getUrban());
			ps.setString(++i, newConnContainer.getNoOfFloors());
			if (null != newConnContainer.getNoOfRooms()
					&& !"".equalsIgnoreCase(newConnContainer.getNoOfRooms()
							.trim())) {
				ps.setString(++i, newConnContainer.getNoOfRooms());
			} else if (null != newConnContainer.getNoOfBeds()
					&& !"".equalsIgnoreCase(newConnContainer.getNoOfBeds()
							.trim())) {
				ps.setString(++i, newConnContainer.getNoOfBeds());
			} else if (null != newConnContainer.getFunctionSite()
					&& !"".equalsIgnoreCase(newConnContainer.getFunctionSite()
							.trim())) {
				ps.setString(++i, newConnContainer.getFunctionSite());
			} else if (null != newConnContainer.getMallCineplex()
					&& !"".equalsIgnoreCase(newConnContainer.getMallCineplex()
							.trim())) {
				ps.setString(++i, newConnContainer.getMallCineplex());
			} else {
				ps.setString(++i, null);
			}
			ps.setString(++i, newConnContainer.getPlotArea());
			ps.setString(++i, newConnContainer.getBuiltUpArea());
			ps.setString(++i, newConnContainer.getWaterConnectionType());
			ps.setString(++i, newConnContainer.getWaterConnectionUse());
			ps.setString(++i, newConnContainer.getNoOfOccDwellUnits());
			ps.setString(++i, newConnContainer.getNoOfUnoccDwellUnits());
			ps.setString(++i, newConnContainer.getNoOfChildren());
			ps.setString(++i, newConnContainer.getNoOfAdults());
			ps.setString(++i, newConnContainer.getIsDJB());
			ps.setString(++i, newConnContainer.getSizeOfMeter());
			ps.setString(++i, newConnContainer.getTypeOfMeter());
			ps.setString(++i, newConnContainer.getInitialRegisterReadDate());
			ps.setString(++i, newConnContainer.getInitialRegisterReadRemark());
			ps.setBigDecimal(++i, newConnContainer.getInitialRegisetrRead());
			ps.setString(++i, newConnContainer.getAverageConsumption());
			ps.setBigDecimal(++i, newConnContainer.getOpeningBalance());
			ps.setString(++i, newConnContainer.getApplicationPurpose());
			ps.setString(++i, newConnContainer.getZoneNo());
			ps.setString(++i, newConnContainer.getMrNo());
			ps.setString(++i, newConnContainer.getAreaNo());
			ps.setString(++i, newConnContainer.getCreatedBy());
			ps.setString(++i, newConnContainer.getCreatedBy());
			ps.setString(++i, newConnContainer.getWcNo());
			ps.setString(++i, newConnContainer.getSaTypeCode());
			ps.setString(++i, newConnContainer.getDafId());
			ps.setString(++i, newConnContainer.getZoneNo());
			ps.setString(++i, newConnContainer.getMrNo());
			ps.setString(++i, newConnContainer.getAreaNo());
			// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			ps.setString(++i, newConnContainer.getNoOfBorewells());
			// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
			// Apr 2016
			ps.setString(++i, newConnContainer.getDafSequence());
			AppLog.info(">>bind Values...i="+i);
			rowUpdated = ps.executeUpdate();
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
		return rowUpdated;
	}

	/**
	 * <p>
	 * This method is used to update New Connection DAF Status to Database.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.djb.interfaces.NewConnectionInterface#updateNewConnectionDAFDetails
	 * (com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	public int updateNewConnectionDAFStatus(String dafSequence,
			String accountId, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer(512);
			querySB
					.append(" UPDATE CM_NEW_CONN_STG SET APP_STATUS='C',PROCESS_BY=?,PROCESS_DTTM=SYSTIMESTAMP,LAST_UPDT_BY=?,LAST_UPDT_DTTM=SYSTIMESTAMP ");
			querySB.append(" WHERE APP_STATUS='E'");
			querySB.append(" AND ACCT_ID =? ");
			querySB.append(" AND SEQ=? ");
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, userId);
			ps.setString(++i, accountId);
			ps.setString(++i, dafSequence);
			rowUpdated = ps.executeUpdate();
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
		return rowUpdated;
	}
}
