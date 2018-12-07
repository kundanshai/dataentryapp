/************************************************************************************************************
 * @(#) RoleScreenMapDAO.java   09 Oct 2014
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
import java.util.List;
import java.util.Map;

import com.tcs.djb.model.RoleScreenMappingDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for Role Screen Map Maintenance.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 */
public class RoleScreenMapDAO {

	/**
	 * <p>
	 * This method is used to get Role list for Drop down list.
	 * </p>
	 * 
	 * @return
	 */
	public static Map<String, String> getRoleList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> roleMap = new HashMap<String, String>();
		try {
			String query = "SELECT T.USER_ROLE,T.USER_ROLE_DESC FROM DJB_DATA_ENTRY_USERS_ROLE T ORDER BY T.USER_ROLE_DESC";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleMap.put(rs.getString("USER_ROLE"), rs
						.getString("USER_ROLE_DESC"));
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
		return roleMap;
	}

	/**
	 * <p>
	 * This method is used to get Role Screen Mapping Details List By a Role.
	 * </p>
	 * 
	 * @param userRole
	 * @return
	 */
	public static List<RoleScreenMappingDetails> getRoleScreenMappingDetailsListByRole(
			String userRole) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<RoleScreenMappingDetails> roleScreenMappingDetailsList = new ArrayList<RoleScreenMappingDetails>();
		RoleScreenMappingDetails roleScreenMappingDetails = null;
		int slNo = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT DISTINCT T.SCREEN_ID, T.SCREEN_DESC, TO_CHAR(T.EFF_FROM_DATE,'DD/MM/YYYY HH:MI AM')EFF_FROM_DATE, TO_CHAR(T.EFF_TO_DATE,'DD/MM/YYYY HH:MI AM')EFF_TO_DATE, ");
			querySB.append(" T.HAS_ACCESS, ");
			querySB.append(" T.ACCESS_BY ");
			querySB
					.append(" FROM (SELECT S.SCREEN_ID, S.SCREEN_DESC, M.EFF_FROM_DATE, M.EFF_TO_DATE, ");
			querySB
					.append(" (case when ((M.EFF_FROM_DATE is not null and M.EFF_TO_DATE is null) or sysdate between M.EFF_FROM_DATE and M.EFF_TO_DATE) then 'true' else 'false' end) HAS_ACCESS, ");
			querySB.append(" 'ROLE' as ACCESS_BY ");
			querySB
					.append(" FROM DJB_DE_SCREEN_MST S, DJB_DE_ROLE_SCREEN_MAP M ");
			querySB.append(" WHERE S.SCREEN_ID = M.SCREEN_ID ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE) ");
			querySB.append(" AND M.ROLE_ID = ? ");
			querySB.append(" UNION ");
			querySB.append(" SELECT S.SCREEN_ID, ");
			querySB.append(" S.SCREEN_DESC,	");
			querySB.append(" NULL AS EFF_FROM_DATE,	");
			querySB.append(" NULL AS EFF_TO_DATE, ");
			querySB.append(" 'false' HAS_ACCESS, ");
			querySB.append(" 'ROLE' as ACCESS_BY ");
			querySB.append(" FROM DJB_DE_SCREEN_MST S ");
			querySB.append(" WHERE S.SCREEN_ID NOT IN (SELECT M.SCREEN_ID ");
			querySB.append(" FROM DJB_DE_ROLE_SCREEN_MAP M ");
			querySB.append(" WHERE 1=1 ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE)");
			querySB.append(" AND M.ROLE_ID = ?)) T ");
			querySB.append(" ORDER BY T.HAS_ACCESS DESC,T.SCREEN_DESC ASC ");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userRole);
			ps.setString(++i, userRole);
			rs = ps.executeQuery();
			while (rs.next()) {
				slNo++;
				roleScreenMappingDetails = new RoleScreenMappingDetails();
				roleScreenMappingDetails.setSlNo(Integer.toString(slNo));
				roleScreenMappingDetails.setScreenId(rs.getString("SCREEN_ID"));
				roleScreenMappingDetails.setScreenDesc(rs
						.getString("SCREEN_DESC"));
				roleScreenMappingDetails.setEffectiveFromDate(rs
						.getString("EFF_FROM_DATE"));
				roleScreenMappingDetails.setEffectiveToDate(rs
						.getString("EFF_TO_DATE"));
				roleScreenMappingDetails.setHasAccess(rs
						.getString("HAS_ACCESS"));
				roleScreenMappingDetails.setAccessBy(rs.getString("ACCESS_BY"));
				roleScreenMappingDetailsList.add(roleScreenMappingDetails);
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
		return roleScreenMappingDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get Role Screen Mapping Details List By a User Id.
	 * </p>
	 * 
	 * @param userIdList
	 * @return
	 */
	public static List<RoleScreenMappingDetails> getRoleScreenMappingDetailsListByUserId(
			String userIdList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<RoleScreenMappingDetails> roleScreenMappingDetailsList = new ArrayList<RoleScreenMappingDetails>();
		RoleScreenMappingDetails roleScreenMappingDetails = null;
		int slNo = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT DISTINCT T.SCREEN_ID,T.SCREEN_DESC,TO_CHAR(T.EFF_FROM_DATE, 'DD/MM/YYYY HH:MI AM') EFF_FROM_DATE,TO_CHAR(T.EFF_TO_DATE, 'DD/MM/YYYY HH:MI AM') EFF_TO_DATE,T.HAS_ACCESS,T.ACCESS_BY	");
			querySB.append(" FROM ( ");
			querySB
					.append(" SELECT S.SCREEN_ID,	S.SCREEN_DESC,M.EFF_FROM_DATE,M.EFF_TO_DATE, ");
			querySB
					.append(" (case when ((M.EFF_FROM_DATE is not null and M.EFF_TO_DATE is null) or sysdate between M.EFF_FROM_DATE and M.EFF_TO_DATE) then 'true' else 'false' end) has_access, ");
			querySB.append(" 'ROLE' as ACCESS_BY ");
			querySB
					.append(" FROM DJB_DE_SCREEN_MST S, DJB_DE_ROLE_SCREEN_MAP M ");
			querySB.append(" WHERE S.SCREEN_ID = M.SCREEN_ID ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE) ");
			querySB
					.append(" AND M.ROLE_ID IN(SELECT U.USER_ROLE FROM DJB_DATA_ENTRY_USERS U WHERE U.USER_ID IN (?)) ");
			querySB.append(" UNION	");
			querySB
					.append(" SELECT S.SCREEN_ID,S.SCREEN_DESC,M.EFF_FROM_DATE,M.EFF_TO_DATE, ");
			querySB
					.append(" (case when ((M.EFF_FROM_DATE is not null and M.EFF_TO_DATE is null) or sysdate between M.EFF_FROM_DATE and M.EFF_TO_DATE) then 'true' else 'false' end) has_access, ");
			querySB.append(" 'USER' as ACCESS_BY	");
			querySB
					.append(" FROM DJB_DE_SCREEN_MST S, DJB_DE_USER_SCREEN_MAP M ");
			querySB.append(" WHERE S.SCREEN_ID = M.SCREEN_ID ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE) ");
			querySB.append(" AND M.USER_ID IN (?) ");
			querySB.append(" UNION ");
			querySB
					.append(" SELECT S.SCREEN_ID,S.SCREEN_DESC,NULL AS EFF_FROM_DATE,NULL AS EFF_TO_DATE,'false' AS has_access,'USER' as ACCESS_BY ");
			querySB.append(" FROM DJB_DE_SCREEN_MST S ");
			querySB.append(" WHERE S.SCREEN_ID NOT IN ");
			querySB.append(" (SELECT S.SCREEN_ID ");
			querySB
					.append(" FROM DJB_DE_SCREEN_MST S, DJB_DE_ROLE_SCREEN_MAP M ");
			querySB.append(" WHERE S.SCREEN_ID = M.SCREEN_ID ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE) ");
			querySB
					.append(" AND M.ROLE_ID IN (SELECT U.USER_ROLE FROM DJB_DATA_ENTRY_USERS U WHERE U.USER_ID IN (?)) ");
			querySB.append(" UNION	");
			querySB.append(" SELECT S.SCREEN_ID	");
			querySB
					.append(" FROM DJB_DE_SCREEN_MST S, DJB_DE_USER_SCREEN_MAP M ");
			querySB.append(" WHERE S.SCREEN_ID = M.SCREEN_ID ");
			querySB
					.append(" AND (M.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN M.EFF_FROM_DATE AND M.EFF_TO_DATE) ");
			querySB.append(" AND M.USER_ID in (?)) ");
			querySB.append(" ) T ");
			querySB
					.append(" ORDER BY T.ACCESS_BY ASC,T.HAS_ACCESS DESC,T.SCREEN_DESC ASC ");

			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userIdList);
			ps.setString(++i, userIdList);
			ps.setString(++i, userIdList);
			ps.setString(++i, userIdList);
			rs = ps.executeQuery();
			while (rs.next()) {
				slNo++;
				roleScreenMappingDetails = new RoleScreenMappingDetails();
				roleScreenMappingDetails.setSlNo(Integer.toString(slNo));
				roleScreenMappingDetails.setScreenId(rs.getString("SCREEN_ID"));
				roleScreenMappingDetails.setScreenDesc(rs
						.getString("SCREEN_DESC"));
				roleScreenMappingDetails.setEffectiveFromDate(rs
						.getString("EFF_FROM_DATE"));
				roleScreenMappingDetails.setEffectiveToDate(rs
						.getString("EFF_TO_DATE"));
				roleScreenMappingDetails.setHasAccess(rs
						.getString("HAS_ACCESS"));
				roleScreenMappingDetails.setAccessBy(rs.getString("ACCESS_BY"));
				roleScreenMappingDetailsList.add(roleScreenMappingDetails);
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
		return roleScreenMappingDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get Role Screen Mapping for a particular role ID.
	 * </p>
	 * 
	 * @param roleId
	 * @return list of screen IDs mapped with the particular role ID
	 */
	public static Map<String, String> getRoleScreenMappingList(String roleId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> roleScreenMap = new HashMap<String, String>();
		try {
			String query = "SELECT T.SCREEN_ID FROM DJB_DE_ROLE_SCREEN_MAP T WHERE (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE) AND T.ROLE_ID=?";
			int i = 0;
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, roleId);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleScreenMap.put(rs.getString("screen_id"), roleId);
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
		return roleScreenMap;
	}

	/**
	 * <p>
	 * This method is used to get Role Screen Mapping for a particular User ID.
	 * </p>
	 * 
	 * @param roleId
	 * @return list of screen IDs mapped with the particular User ID
	 */
	public static Map<String, String> getUserScreenMappingList(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> userScreenMap = new HashMap<String, String>();
		try {
			String query = "SELECT T.SCREEN_ID FROM DJB_DE_USER_SCREEN_MAP T WHERE (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE) AND UPPER(T.USER_ID)=UPPER(?)";
			int i = 0;
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userScreenMap.put(rs.getString("screen_id"), userId);
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
		return userScreenMap;
	}

	/**
	 * <p>
	 * This method is used to validate user id.
	 * </p>
	 * 
	 * @param userId
	 * @return true or false.
	 */
	public static boolean isValidUserId(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isValid = false;
		try {
			String query = "SELECT COUNT(1)COUNT_USER FROM DJB_DATA_ENTRY_USERS T WHERE T.USER_ID=?";
			int i = 0;
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(++i, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				isValid = rs.getInt("COUNT_USER") > 0;
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
		return isValid;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to provide
	 * Access To Role.
	 * </p>
	 * 
	 * @param userRole
	 * @param screenId
	 * @param createdBy
	 * @return
	 */
	public static int provideAccessToRole(String userRole, String screenId,
			String createdBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowInserted = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into DJB_DE_ROLE_SCREEN_MAP (ROLE_ID, SCREEN_ID,CREATED_BY) ");
			querySB.append(" values (?, ?, ?) ");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userRole);
			ps.setString(++i, screenId);
			ps.setString(++i, createdBy);
			rowInserted = ps.executeUpdate();
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
		return rowInserted;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to provide
	 * Access To a User.
	 * </p>
	 * 
	 * @param userIdList
	 * @param screenId
	 * @param createdBy
	 * @return
	 */
	public static int provideAccessToUser(String userIdList, String screenId,
			String createdBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowInserted = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into DJB_DE_USER_SCREEN_MAP (USER_ID, SCREEN_ID,CREATED_BY) ");
			querySB.append(" values (?, ?, ?) ");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userIdList);
			ps.setString(++i, screenId);
			ps.setString(++i, createdBy);
			rowInserted = ps.executeUpdate();
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
		return rowInserted;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to revoke
	 * Access To a Role.
	 * </p>
	 * 
	 * @param userRole
	 * @param screenId
	 * @param updatedBy
	 * @return
	 */
	public static int revokeAccessToRole(String userRole, String screenId,
			String updatedBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" update DJB_DE_ROLE_SCREEN_MAP T ");
			querySB.append(" set  T.EFF_TO_DATE=sysdate-1 ");
			querySB.append(" ,T.LAST_UPDATED_BY=? ");
			querySB.append(" WHERE T.SCREEN_ID =? ");
			querySB.append(" AND T.ROLE_ID = ? ");
			querySB
					.append(" AND (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE)");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, updatedBy);
			ps.setString(++i, screenId);
			ps.setString(++i, userRole);
			rowsUpdated = ps.executeUpdate();
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
		return rowsUpdated;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to revoke
	 * Access To a User.
	 * </p>
	 * 
	 * @param userRole
	 * @param screenId
	 * @param updatedBy
	 * @return
	 */
	public static int revokeAccessToUser(String userRole, String screenId,
			String updatedBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" update DJB_DE_USER_SCREEN_MAP T ");
			querySB.append(" set  T.EFF_TO_DATE=sysdate-1 ");
			querySB.append(" ,T.LAST_UPDATED_BY=? ");
			querySB.append(" WHERE T.SCREEN_ID =? ");
			querySB.append(" AND T.USER_ID = ? ");
			querySB
					.append(" AND (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE)");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, updatedBy);
			ps.setString(++i, screenId);
			ps.setString(++i, userRole);
			rowsUpdated = ps.executeUpdate();
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
		return rowsUpdated;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to update
	 * Effective To Date To Role.
	 * </p>
	 * 
	 * @param userRole
	 * @param screenId
	 * @param effectiveToDate
	 * @param updatedBy
	 * @return
	 */
	public static int updateEffectiveToDateToRole(String userRole,
			String screenId, String effectiveToDate, String updatedBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" update DJB_DE_ROLE_SCREEN_MAP T ");
			querySB.append(" set  T.EFF_TO_DATE=to_date(?,'dd/mm/yyyy') ");
			querySB.append(" ,T.LAST_UPDATED_BY=? ");
			querySB.append(" WHERE T.SCREEN_ID =? ");
			querySB.append(" AND T.ROLE_ID = ? ");
			querySB
					.append(" AND (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE)");
			String query = querySB.toString();
			// System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, effectiveToDate);
			ps.setString(++i, updatedBy);
			ps.setString(++i, screenId);
			ps.setString(++i, userRole);
			rowsUpdated = ps.executeUpdate();
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
		return rowsUpdated;
	}

	/**
	 * <p>
	 * This method is used to update Role Screen Mapping Details to update
	 * Effective To Date To User.
	 * </p>
	 * 
	 * @param userIdList
	 * @param screenId
	 * @param effectiveToDate
	 * @param updatedBy
	 * @return
	 */
	public static int updateEffectiveToDateToUserId(String userIdList,
			String screenId, String effectiveToDate, String updatedBy) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" update DJB_DE_USER_SCREEN_MAP T ");
			querySB.append(" set  T.EFF_TO_DATE=to_date(?,'dd/mm/yyyy') ");
			querySB.append(" ,T.LAST_UPDATED_BY=? ");
			querySB.append(" WHERE T.SCREEN_ID =? ");
			querySB.append(" AND T.USER_ID = ? ");
			querySB
					.append(" AND (T.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN T.EFF_FROM_DATE AND T.EFF_TO_DATE)");
			String query = querySB.toString();
			System.out.println("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, effectiveToDate);
			ps.setString(++i, updatedBy);
			ps.setString(++i, screenId);
			ps.setString(++i, userIdList);
			rowsUpdated = ps.executeUpdate();
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
		return rowsUpdated;
	}
}
