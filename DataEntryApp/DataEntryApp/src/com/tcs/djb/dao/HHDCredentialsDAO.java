/************************************************************************************************************
 * @(#) HHDCredentialsDAO.java   05 Dec 2013
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

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.HHDCredentialsDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for HHD Credentials.
 * </p>
 * 
 * @author Karthick K(Tata Consultancy Services) Since 05-DEC-2013
 * 
 */
public class HHDCredentialsDAO {
	/**
	 * <p>
	 * This method is used to delete HHDCredentials Details.
	 * </p>
	 * 
	 * @param deleteHHDiD
	 * @return
	 */
	public static int deleteHHDCredentialsAJaxDetails(String deleteHHDiD) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {
			String query = QueryContants.deleteHHDCredentailsDetailsQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, deleteHHDiD);
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
	 * This method is used to retrieve HHD Details List for HHD Credentials Screen.
	 * </p>
	 * 
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static List<HHDCredentialsDetails> getHHDCredentialsDetailsList(
			String pageNo, String recordPerPage) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HHDCredentialsDetails hhdCredentialsDetails = null;
		List<HHDCredentialsDetails> hhdCredentialsDetailsList = new ArrayList<HHDCredentialsDetails>();
		try {
			String query = QueryContants.getHHDCredentialsDetailsListQuery(
					pageNo, recordPerPage);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				hhdCredentialsDetails = new HHDCredentialsDetails();
				hhdCredentialsDetails.setSlNo(rs.getString("SEQ_NO"));
				hhdCredentialsDetails.setHhdId(rs.getString("USER_ID"));
				hhdCredentialsDetails
						.setPassword(rs.getString("USER_PASSWORD"));
				hhdCredentialsDetails.setEffFromDate(rs
						.getString("EFF_FROM_DATE"));
				hhdCredentialsDetails.setEffToDate(rs.getString("EFF_TO_DATE"));
				hhdCredentialsDetails.setLastUpdatedBy(rs
						.getString("LAST_UPDT_BY"));
				hhdCredentialsDetails.setLastUpdatedOn(rs
						.getString("LAST_UPDT_DTTM"));
				hhdCredentialsDetailsList.add(hhdCredentialsDetails);
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
		return hhdCredentialsDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get the total number of records for HHD Credentials Screen in the
	 * database.
	 * </p>
	 * 
	 * @return
	 */
	public static int getTotalCountOfHHDCredentialsRecords() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfHHDCredentialsRecordsQuery();
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
	 * This method is used to insert HHDCredentials Details.
	 * </p>
	 * 
	 * @param addHHDid
	 * @param addPassword
	 * @param userName
	 * @return
	 */
	public static int insertHHDCredentialsAJaxDetails(String addHHDid,
			String addPassword, String userName) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {

			String query = QueryContants.insertHHDCredentialsDetailsQuery(
					addHHDid, addPassword, userName);
			conn = DBConnector.getConnection();

			if (null != addHHDid) {
				if (null != ps) {
					ps.close();
				}
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, addHHDid);
				ps.setString(++i, addPassword);
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

	/**
	 * <p>
	 * This method is used to update HHDCredentials Details.
	 * </p>
	 * 
	 * @param prevValueHhdId
	 * @param toHHDiD
	 * @param toPassword
	 * @param userName
	 * @return
	 */
	public static int updateHHDCredentialsAJaxDetails(String prevValueHhdId,
			String toHHDiD, String toPassword, String userName) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int recordInserted = 0;
		try {
			String query = QueryContants.updateHHDCredentailsDetailsQuery(
					prevValueHhdId, toHHDiD, toPassword, userName);
			// AppLog.info("updateHHDMeterReaderMappingDetailsQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);

			int i = 0;

			ps.setString(++i, toHHDiD);
			ps.setString(++i, toPassword);
			ps.setString(++i, userName);
			ps.setString(++i, prevValueHhdId);

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

}
