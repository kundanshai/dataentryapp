/************************************************************************************************************
 * @(#) RainWaterHarvestingDAO.java   29 Aug 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Rain Water Harvesting.
 * </p>
 * 
 * @author Aniket Chatterjee (Tata Consultancy Services)
 * @history On 29-AUG-2016 Rajib Hazaraika Added method
 *          {@link #getKnoDetailsForRaybFlag(String)},
 *          {@link #insertRaybCharDetails(String, String, String, String, String, String, String)}
 *          to insert Rocky Area/banks of Yamuna Flag updation as per JTrac
 *          ID#DJB-4537 Open project Id#1442
 */
public class RainWaterHarvestingDAO {
	/**
	 * <p>
	 * This method used to check access of the kno to userId Jtrac DJB-4037
	 * </p>
	 * 
	 * @param kno
	 * @author 837535
	 * @param userId
	 * @return
	 */
	public static int getAccesCount(String kno, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int accessCnt = 0, i = 0;
		try {
			String query = QueryContants.checkAccessQuery();
			// System.out.println("Query::" + query);
			// System.out.println("kno::" + kno + "::userId::" + userId);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != kno && !"".equals(kno.trim())) {
				ps.setString(++i, kno);
				// System.out.println("Inside kno if::i" + i);
			}
			if (null != userId && !"".equals(userId.trim())) {
				ps.setString(++i, userId);
				// System.out.println("Inside userId if::" + i);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				// System.out.println("ACCESS_CNT::" + rs.getInt("ACCESS_CNT"));
				accessCnt = rs.getInt("ACCESS_CNT");
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
		return accessCnt;
	}

	/**
	 * <p>
	 * This method used to get customer class Jtrac DJB-4037
	 * </p>
	 * 
	 * @author 837535
	 * @param kno
	 * @return
	 */

	public static String getCustomerClass(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String custClsCd = null;
		int i = 0;
		try {
			if (null != kno && !"".equals(kno.trim())) {
				String query = QueryContants.getCustomerClassQuery();
				// System.out.println("Query::" + query);
				// System.out.println("kno::" + kno);
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);

				ps.setString(++i, kno);
				// System.out.println("Inside kno if::i" + i);

				rs = ps.executeQuery();
				while (rs.next()) {
					// System.out.println("CUSTOMER_CLS_CD::" +
					// rs.getString("CUSTOMER_CLS_CD"));
					custClsCd = rs.getString("CUSTOMER_CLS_CD");
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
		return custClsCd;
	}

	/**
	 * <p>
	 * This method used to check access of the kno to userId
	 * </p>
	 * 
	 * @param kno
	 * @param userId
	 *            Jtrac DJB-4037
	 * @author 837535
	 * @return
	 */
	public static Map<String, String> getKnoDetails(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		int i = 0;
		try {
			String query = QueryContants.getConsNameNRwhStatusQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != kno && !"".equals(kno.trim())) {
				ps.setString(++i, kno);
				ps.setString(++i, kno);
			}
			if (null != DJBConstants.RAIN_WAT_CHAR_TYPE
					&& !"".equals(DJBConstants.RAIN_WAT_CHAR_TYPE.trim())) {
				ps.setString(++i, DJBConstants.RAIN_WAT_CHAR_TYPE);
			}
			if (null != DJBConstants.WASTE_WAT_CHAR_TYPE
					&& !"".equals(DJBConstants.WASTE_WAT_CHAR_TYPE.trim())) {
				ps.setString(++i, kno);
				ps.setString(++i, DJBConstants.WASTE_WAT_CHAR_TYPE);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put("CONSNAME", rs.getString("CONS_NAME"));
				retrunMap.put("RWH_STATUS", rs.getString("RWH_STATUS"));
				retrunMap.put("WWT_STATUS", rs.getString("WWT_STATUS"));
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
	 * This method used to check access of the kno to userId and to retrieve current
	 * RAYB Flag Status
	 * </p>
	 * 
	 * @param kno
	 * @param userId
	 *            Jtrac DJB-4537
	 * @author 682667 (Rajib Hazarika)
	 * @return
	 */
	public static Map<String, String> getKnoDetailsForRaybFlag(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		int i = 0;
		try {
			String query = QueryContants.getConsNameNRaybStatusQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			AppLog.info(">>query>>"+query);
			if (null != kno && !"".equals(kno.trim())) {
				ps.setString(++i, kno);
				ps.setString(++i, kno);
			}
			if (null != DJBConstants.ROCKY_YAMUNA_CHAR_TYPE
					&& !"".equals(DJBConstants.ROCKY_YAMUNA_CHAR_TYPE.trim())) {
				ps.setString(++i, DJBConstants.ROCKY_YAMUNA_CHAR_TYPE.trim());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put("CONSNAME", rs.getString("CONS_NAME"));
				retrunMap.put("RAYB_STATUS", rs.getString("RAYB_STATUS"));
				AppLog.info(">>CONSNAME>>"+rs.getString("CONS_NAME")+">>RAYB_STATUS>>"+rs.getString("RAYB_STATUS"));
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
	 * This method used to get premise id Jtrac DJB-4037
	 * </p>
	 * 
	 * @author 837535
	 * @param kno
	 * @return
	 */

	public static String getPremiseId(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String premiseId = null;
		int i = 0;
		try {
			if (null != kno && !"".equals(kno.trim())) {
				String query = QueryContants.getPremiseIdQuery();
				// System.out.println("Query::" + query);
				// System.out.println("kno::" + kno);
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);

				ps.setString(++i, kno);
				// System.out.println("Inside kno if::i" + i);

				rs = ps.executeQuery();
				while (rs.next()) {
					// System.out.println("PREMISE_ID::" +
					// rs.getString("PREMISE_ID"));
					premiseId = rs.getString("PREMISE_ID");// rs.getInt("PREMISE_ID");
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
		return premiseId;
	}

	/**
	 * <p>
	 * This method used to insert data into staging table
	 * </p>
	 * 
	 * @param acct_id
	 * @param rwh_flg
	 * @param rwh_effdt
	 * @param wwt_flg
	 * @param wwt_effdt
	 * @param document_no
	 * @param diary_no
	 * @param approved_by
	 * @param created_by
	 * @param status_flg
	 *            Jtrac DJB-4037
	 * @author 837535
	 * @return
	 */

	public static int insertPremCharDetails(String acct_id, String rwh_flg,
			String rwh_effdt, String wwt_flg, String wwt_effdt,
			String document_no, String diary_no, String approved_by,
			String created_by, String status_flg, String diary_date) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {

			String query = QueryContants.insertPremCharDetailsQuery();
			conn = DBConnector.getConnection();

			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, acct_id);
			ps.setString(++i, rwh_flg);
			ps.setString(++i, rwh_effdt);
			ps.setString(++i, wwt_flg);
			ps.setString(++i, wwt_effdt);
			ps.setString(++i, document_no);
			ps.setString(++i, diary_no);
			ps.setString(++i, approved_by);
			ps.setString(++i, created_by);
			ps.setString(++i, status_flg);
			ps.setString(++i, diary_date);

			recordInserted = ps.executeUpdate();

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

	/**
	 * <p>
	 * This method used to insert data into staging table
	 * </p>
	 * 
	 * @param acct_id
	 * @param rwh_flg
	 * @param rwh_effdt
	 * @param wwt_flg
	 * @param wwt_effdt
	 * @param document_no
	 * @param diary_no
	 * @param approved_by
	 * @param created_by
	 * @param status_flg
	 *            Jtrac DJB-4537 Open project Id#1442
	 * @author 682667 (Rajib Hazarika)
	 * @since 29-AUG-2016
	 * @return
	 */

	public static int insertRaybCharDetails(String acct_id, String rwh_flg,
			String rwh_effdt, String document_no, String approved_by,
			String created_by, String status_flg) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {
			String query = QueryContants.insertRaybCharDetailsQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, acct_id);
			ps.setString(++i, rwh_flg);
			ps.setString(++i, rwh_effdt);
			ps.setString(++i, document_no);
			ps.setString(++i, approved_by);
			ps.setString(++i, created_by);
			ps.setString(++i, status_flg);
			recordInserted = ps.executeUpdate();
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

	/**
	 * <p>
	 * This method used to update data in staging table
	 * </p>
	 * 
	 * @param acct_id
	 * @param rwh_flg
	 * @param rwh_effdt
	 * @param wwt_flg
	 * @param wwt_effdt
	 * @param document_no
	 * @param diary_no
	 * @param approved_by
	 * @param created_by
	 * @param status_flg
	 *            Jtrac DJB-4037
	 * @author 837535
	 * @return
	 */

	public static int updatePremCharStatus(String status_flg,
			String last_updt_by, String acct_id, String initial_status_flg) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int recordUpdated = 0;
		try {
			String query = QueryContants.updatePremCharDetailsQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;

			ps.setString(++i, status_flg);

			ps.setString(++i, last_updt_by);
			ps.setString(++i, acct_id);
			ps.setString(++i, initial_status_flg);
			int rowUpdated = ps.executeUpdate();
			if (rowUpdated > 0) {
				recordUpdated++;
			}
			if (null != ps) {
				ps.close();
				ps = null;
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
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		// AppLog.info("RECORD UPDATED:: " + recordUpdated);
		AppLog.end();
		return recordUpdated;
	}
}
