/************************************************************************************************************
 * @(#) NewConnFileEntryDAO.java   12 Jan 2015
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
import com.tcs.djb.model.NewConnFileEntryDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for New Connection File Entry process.
 * </p>
 * 
 * @author Rajib Hazarika (Tata Consultancy Services)
 * @since 12-JAN-2015
 * @history: On 16-SEP-2016 Rajib Hazarika (682667) added extra logic to prevent
 *           file entry of those files which are tagged with specific employeeId
 *           as per JTrac ANDROMR-7 for Quick New Connection CR changes
 * 
 */
public class NewConnFileEntryDAO {

	/**
	 * <p>
	 * This method is used to get ZRO access for user's for DataEntryApp
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public static String getDeZroAccessForUserId(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		String zroCd = null;
		try {
			String query = QueryContants.getDeZroAccessForUserIdQuery();
			conn = DBConnector.getConnection();
			AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			ps.setString(++i, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroCd = rs.getString("USER_ZRO_CD");
				if (null != zroCd) {
					AppLog.info(">>zroCd>>" + zroCd);
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
		return zroCd;
	}

	/**
	 * <p>
	 * This method is used to get Doc Attach list to populate dropdown values in
	 * NewCon File Entry Screen as per Jtrac DJB-4313 It connects the table
	 * through {@link # connect_to_portal}
	 * </p>
	 * 
	 * @param docType
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 12-JAN-2015
	 * @return
	 */
	public static Map<String, String> getDocAttchList(String docType) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getDocAttchListMapQuery();
			// conn = DBConnector.getConnection();
			conn = DBConnector.getConnection(
					DJBConstants.JNDI_DS_NEWCONN_PORTAL,
					DJBConstants.JNDI_PROVIDER_NEWCONN_PORTAL);
			AppLog.info("query to get DocAttchList >>docType>>" + docType
					+ ">>Query>>" + query + ">> i>>" + i);
			ps = conn.prepareStatement(query);
			ps.setString(++i, docType);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("DOC_VALUE"), rs.getString(
						"DOC_TEXT").trim());
				AppLog.info("DOC_VALUE>>" + rs.getString("DOC_VALUE")
						+ ">>DOC_TEXT>>" + rs.getString("DOC_TEXT"));
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
	 * This method is used to get File count
	 * </p>
	 * 
	 * @param fileNo
	 * @return
	 */
	public static int getFileCount(String fileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int fileCount = 0, i = 0;
		try {
			String query = QueryContants.getFileCountQuery();
			conn = DBConnector.getConnection();
			AppLog.info("query to get File Count in Staging table" + fileNo);
			ps = conn.prepareStatement(query);
			ps.setString(++i, fileNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				fileCount = rs.getInt("CNT");
				AppLog.info(">>fileCount>>" + fileCount);
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
		return fileCount;
	}

	/**
	 * <p>
	 * This method is used to get File location of ZRO Code
	 * </p>
	 * 
	 * @param fileNo
	 * @return
	 */
	public static String getFileLocationZroCd(String fileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroCd = null;
		try {
			String query = QueryContants.getFileLocationZroCdQuery(fileNo);
			conn = DBConnector.getConnection();
			AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroCd = rs.getString("ZRO_CD");
				if (null != zroCd) {
					AppLog.info(">>zroCd>>" + zroCd);
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
		return zroCd;
	}

	/**
	 * <p>
	 * This function will return 1 if the specific file is tagged to any
	 * specific EmployeeId <code>CM_FILE_RANGE_MAP_EMP</code>,otherwise it will
	 * return the actual count of those file n that table or 0 JTracID#
	 * ANDROMR-7
	 * </p>
	 * 
	 * @param fileNo
	 * @return
	 * @author 682667(Rajib Hazarika)
	 * @since 16-SEP-2016
	 */
	public static int isFileTaggedToEmp(String fileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int fileCount = 1;
		try {
			String query = QueryContants.isFileTaggedToEmpQuery(fileNo);
			conn = DBConnector.getConnection();
			AppLog.info("isFileTaggedToEmpQuery>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				fileCount = rs.getInt("CNT");
				AppLog.info(">>fileCount>>" + fileCount);
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
		return fileCount;
	}

	/**
	 * <p>
	 * This method is used to get save New Connection File Details
	 * </p>
	 * 
	 * @param newConnFileEntryDetails
	 * @return
	 */
	public static int saveNewConnFileDetails(
			NewConnFileEntryDetails newConnFileEntryDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		int rowsInserted = 0;
		try {
			String query = QueryContants
					.saveNewConnFileDetailsQuery(newConnFileEntryDetails);
			AppLog.info("query>>" + query);
			AppLog.info("newConnFileEntryDetails>>" + newConnFileEntryDetails);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, newConnFileEntryDetails.getFileName());
			ps.setString(++i, newConnFileEntryDetails.getTypeOfApp());
			ps.setString(++i, newConnFileEntryDetails.getFirstName());
			ps.setString(++i, newConnFileEntryDetails.getIdentityProof());
			ps.setString(++i, newConnFileEntryDetails.getPropertyDoc());
			ps.setString(++i, newConnFileEntryDetails.getPinAdd());
			ps.setString(++i, newConnFileEntryDetails.getLocaAdd());
			ps.setString(++i, newConnFileEntryDetails.getSubLocAdd());
			ps.setString(++i, newConnFileEntryDetails.getHnoAdd());
			ps.setString(++i, newConnFileEntryDetails.getStatus());
			ps.setString(++i, newConnFileEntryDetails.getCreatedBy());

			if (null != newConnFileEntryDetails.getMiddleName()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getMiddleName())) {
				ps.setString(++i, newConnFileEntryDetails.getMiddleName());
			}

			if (null != newConnFileEntryDetails.getLastName()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getLastName())) {
				ps.setString(++i, newConnFileEntryDetails.getLastName());
			}
			if (null != newConnFileEntryDetails.getPhone()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails.getPhone())) {
				ps.setString(++i, newConnFileEntryDetails.getPhone());
			}
			if (null != newConnFileEntryDetails.getEmailId()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getEmailId())) {
				ps.setString(++i, newConnFileEntryDetails.getEmailId());
			}
			if (null != newConnFileEntryDetails.getProofOfRes()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getProofOfRes())) {
				ps.setString(++i, newConnFileEntryDetails.getProofOfRes());
			}
			if (null != newConnFileEntryDetails.getJjr()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails.getJjr())) {
				ps.setString(++i, newConnFileEntryDetails.getJjr());
			}
			if (null != newConnFileEntryDetails.getRoadNo()
					&& !""
							.equalsIgnoreCase(newConnFileEntryDetails
									.getRoadNo())) {
				ps.setString(++i, newConnFileEntryDetails.getRoadNo());
			}
			if (null != newConnFileEntryDetails.getSubLoc1Add()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getSubLoc1Add())) {
				ps.setString(++i, newConnFileEntryDetails.getSubLoc1Add());
			}
			if (null != newConnFileEntryDetails.getSubLoc2Add()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getSubLoc2Add())) {
				ps.setString(++i, newConnFileEntryDetails.getSubLoc2Add());
			}
			if (null != newConnFileEntryDetails.getSubColAdd()
					&& !"".equalsIgnoreCase(newConnFileEntryDetails
							.getSubColAdd())) {
				ps.setString(++i, newConnFileEntryDetails.getSubColAdd());
			}
			if (null != newConnFileEntryDetails.getVilAdd()
					&& !""
							.equalsIgnoreCase(newConnFileEntryDetails
									.getVilAdd())) {
				ps.setString(++i, newConnFileEntryDetails.getVilAdd());
			}
			rowsInserted = ps.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				rowsInserted = 1;
			} else {
				AppLog.error(e);
			}
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
		AppLog.end();
		return rowsInserted;
	}
}
