/************************************************************************************************************
 * @(#) FileNbrAllocationUserWiseDAO.java   19 Sep 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.FileNbrAllocation;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for File number allocation User Wise Screen as per jTrac
 * DJB-4571,Openproject- #1492
 * </p>
 * 
 * @author Lovely (Tata Consultancy Services)
 * @since 19-09-2016
 */
public class FileNbrAllocationUserWiseDAO {
	/**
	 * <p>
	 * Checking valid Zro location Tagged file number as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static String checkTaggedZROLocation(String minRange, String maxRange) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroLocation = "";
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.tagFileZroLocation();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, minRange);
			ps.setString(++i, maxRange);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroLocation = rs.getString("ZRO_CD");
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
		return zroLocation;
	}

	/**
	 * <p>
	 * Checking valid Zro location Tagged to the userId as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */
	public static String checkZROLocationAccess(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroLocation = "";
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.checkZROLocationAcessQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroLocation = rs.getString("user_zro_cd");
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
		return zroLocation;
	}

	/**
	 * <p>
	 * method for deleting allocated file range from table CM_FILE_RANGE_MAP_EMP
	 * as per jTrac DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static int deleteAllocatedFileToUser(String userIdUpdate,
			String minRange, String maxRange, String selectedZROLocation) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecordsDeleted = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.deleteAllocatedFileToUserQuery();
			ps = conn.prepareStatement(query);
			int i = 0;

			ps.setString(++i, userIdUpdate);
			ps.setString(++i, selectedZROLocation);
			ps.setString(++i, minRange);
			ps.setString(++i, maxRange);
			AppLog.info(query);
			totalRecordsDeleted = ps.executeUpdate();

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
		return totalRecordsDeleted;
	}

	/**
	 * <p>
	 * method for fetching ZRO location assign to a Logged in user as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */
	public static String getAssignedZROList(String userId) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroLoggedInUser = "";
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getAssignedZROListQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroLoggedInUser = rs.getString("ZRO_CD");
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
		return zroLoggedInUser;
	}

	/**
	 * <p>
	 * method for fetching details of file allocated to a user as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */
	public static List<FileNbrAllocation> getUserFileNbrAllocationList(
			String UserIdTag) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FileNbrAllocation fileNbrAllocation = null;
		List<FileNbrAllocation> fileNbrAllocationList = new ArrayList<FileNbrAllocation>();
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getUserFileNbrAllocationQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, UserIdTag);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				fileNbrAllocation = new FileNbrAllocation();
				fileNbrAllocation.setSelectedZROCode(rs.getString("ZRO_CD"));
				fileNbrAllocation.setMinFileRange(rs.getString("MIN_VAL"));
				fileNbrAllocation.setMaxFileRange(rs.getString("MAX_VAL"));
				fileNbrAllocationList.add(fileNbrAllocation);
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
		return fileNbrAllocationList;
	}

	/**
	 * <p>
	 * method for inserting into table CM_FILE_RANGE_MAP_EMP as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */
	public static int insertAllocatedFileToUser(String UserIdTag,
			String ZroLocation, String minRange, String maxRange, String userid) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecordsInserted = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.insertAllocatedFileToUserQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, UserIdTag);
			ps.setString(++i, ZroLocation);
			ps.setString(++i, minRange);
			ps.setString(++i, maxRange);
			ps.setString(++i, userid);
			AppLog.info(query);
			totalRecordsInserted = ps.executeUpdate();

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
		return totalRecordsInserted;
	}

	/**
	 * <p>
	 * method for deleting allocated file range from table CM_FILE_RANGE_MAP_EMP
	 * as per jTrac DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static int isExistFileNbr(String fileRange, String userIdUpdate) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countExist = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.isExistFileNbrQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, fileRange);
			ps.setString(++i, fileRange);
			ps.setString(++i, userIdUpdate);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				countExist = rs.getInt("CNT");
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
		return countExist;
	}

	/**
	 * <p>
	 * method for deleting allocated file range from table CM_FILE_RANGE_MAP_EMP
	 * as per jTrac DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static int isUsedFileNbr(String minRange, String maxRange) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countExist = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.isUsedFileNbrQuery();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, minRange);
			ps.setString(++i, maxRange);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				countExist = rs.getInt("CNT");
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
		return countExist;
	}

	/**
	 * <p>
	 * method for deleting allocated file range from table CM_FILE_RANGE_MAP_EMP
	 * as per jTrac DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static int isValideAllocatedFileToUser(String minRange,
			String maxRange, String selectedZROLocation) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.isvalideAllocatedFileToUserQuery();
			ps = conn.prepareStatement(query);
			int i = 0;

			ps.setString(++i, selectedZROLocation);
			ps.setString(++i, minRange);
			ps.setString(++i, maxRange);
			AppLog.info(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
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
		return count;
	}

	/**
	 * <p>
	 * method for inserting into table CM_FILE_RANGE_MAP_EMP as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 19-09-2016
	 */

	public static int updateAllocatedFileToUser(String userIdUpdate,
			String maxRange, String userid, String selectedZROLocation) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecordsUpdated = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.updateAllocatedFileToUserQuery();
			ps = conn.prepareStatement(query);
			int i = 0;

			ps.setString(++i, maxRange);
			ps.setString(++i, userid);
			ps.setString(++i, userIdUpdate);
			ps.setString(++i, selectedZROLocation);
			AppLog.info(query);
			totalRecordsUpdated = ps.executeUpdate();

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
		return totalRecordsUpdated;
	}
}