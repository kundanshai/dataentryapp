/************************************************************************************************************
 * @(#) CreateUserDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.model.UserDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Creating User.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 * @history 03-09-2013 Matiur Rahman Added functionality for Biometric
 *          Enrollment. Modified the method {@link #createUser} and
 *          {@link #updateUser}.
 * 
 * @history 30-05-2016 Arnab Nandy added functionality for bypassing Biometric
 *          authentication enable and disable by adding
 *          {@link #validateUserRoleFingerPrint(String)},
 *          {@link #validateUserIdInException(String)},
 *          {@link #enableBypassFP(String, String)},
 *          {@link #disableBypassFP(String, String)}
 * @history 01-09-2013 Suraj Tiwari Added functionality for ZRO Location tagging
 *          of Data Entry User, according to JTrac DJB-4549
 */
public class CreateUserDAO {

	/**
	 * <p>
	 * Activate User for DE users. Activate user actually updates USER_ACTIVE
	 * flag to Y from N.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean activateUser(HashMap<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String updatedBy = (String) inputMap.get("updatedBy");
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" update djb_data_entry_users t set t.user_active='Y',t.modified_id=?,t.modified_date=sysdate ");
			querySB.append(" where t.user_id=?  and t.user_active='N'");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, updatedBy);
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
	 * Insert user details into database for creating DE users.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean createUser(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String userRole = (String) inputMap.get("userRole");
			String createdBy = (String) inputMap.get("createdBy");
			String password = (String) inputMap.get("password");
			String userName = (String) inputMap.get("userName");
			String userAddress = (String) inputMap.get("userAddress");
			String FIRTextData = (String) inputMap.get("FIRTextData");
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			String zroLocation = (String) inputMap.get("zroLocation");
			AppLog
					.info("zroLocations createUser in createUserDao >>>>>>>>>>>>>>>>>>"
							+ zroLocation);

			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */

			// System.out.println("userId::" + userId + "::userRole::" +
			// userRole
			// + "::updatedBy::" + updatedBy);
			StringBuffer querySB = new StringBuffer();
			/*
			 * querySB.append(
			 * " insert into djb_data_entry_users (USER_ID, USER_PASSWORD, USER_NAME, USER_ADDRESS, CREATE_ID, CREATE_DATE,"
			 * );
			 */
			querySB
					.append(" insert into djb_data_entry_users (USER_ID, USER_PASSWORD, USER_NAME, USER_ADDRESS, CREATE_ID, CREATE_DATE,USER_ZRO_CD,");
			if (null != FIRTextData && !"".equals(FIRTextData.trim())) {
				querySB.append("USER_FP1, ");
			}
			querySB.append(" USER_ROLE)");
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			// querySB.append(" values (?, ?, ?, ?, ?, sysdate,");
			querySB.append(" values (?, ?, ?, ?, ?, sysdate,?,");
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			if (null != FIRTextData && !"".equals(FIRTextData.trim())) {
				querySB.append("?,");
			}
			querySB.append("?)");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, password);
			ps.setString(++i, userName);
			ps.setString(++i, userAddress);
			ps.setString(++i, createdBy);
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			ps.setString(++i, zroLocation);
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			if (null != FIRTextData && !"".equals(FIRTextData.trim())) {
				ps.setString(++i, FIRTextData);
			}
			ps.setString(++i, userRole);
			int rowUpdated = ps.executeUpdate();
			// System.out.println("In CreateUserDAO :: "+query);
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
	 * Disable user from database for all application for bypassing fingerprint
	 * privilege. Disable user actually updates user into exception.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac DJB-4464 and OpenProject#1151
	 * @since 30-05-2016
	 * @param String
	 * @return
	 */
	public static boolean disableBypassFP(String userToBeDisable, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {

			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" update DJB_BIOMETRIC_EXCP_LIST t set t.ACTIVE_FLAG=?,t.LAST_UPDATED_BY=?,t.LAST_UPDATE_DATE=sysdate ");
			querySB.append(" where t.user_id=? and t.ACTIVE_FLAG=? ");

			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, DJBConstants.FLAG_N.trim());
			ps.setString(++i, userId);
			ps.setString(++i, userToBeDisable);
			ps.setString(++i, DJBConstants.FLAG_Y.trim());
			int rowUpdated = ps.executeUpdate();
			AppLog.info(rowUpdated + " " + userToBeDisable + " " + userId + " "
					+ query);
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
	 * Disable user from database for DE users. Delete user actually updates
	 * USER_ACTIVE flag to N from Y.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean disableUser(HashMap<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String updatedBy = (String) inputMap.get("updatedBy");
			String userType = (String) inputMap.get("userType");
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" update djb_data_entry_users t set t.user_active='N',t.modified_id=?,t.modified_date=sysdate ");
			querySB.append(" where t.user_id=? ");
			if ("ALL".equalsIgnoreCase(userType)) {
				querySB.append(" and t.user_password is null ");
			}
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, updatedBy);
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
	 * Enable user from database for all application for bypassing fingerprint
	 * privilege. Enable user actually inserts user into exception.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac DJB-4464 and OpenProject#1151
	 * @since 30-05-2016
	 * @param String
	 * @return
	 */
	public static boolean enableBypassFP(String userToBeEnable, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {

			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" insert into DJB_BIOMETRIC_EXCP_LIST values (?,sysdate,'',?,sysdate,?,'','') ");

			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userToBeEnable);
			ps.setString(++i, DJBConstants.FLAG_Y.trim());
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
	 * Enable user from database for DE users. Enable user actually updates
	 * USER_ACTIVE flag to Y from N.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean enableUser(HashMap<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String updatedBy = (String) inputMap.get("updatedBy");
			String userType = (String) inputMap.get("userType");
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" update djb_data_entry_users t set t.user_active='Y',t.modified_id=?,t.modified_date=sysdate ");
			querySB.append(" where t.user_id=? ");
			if ("ALL".equalsIgnoreCase(userType)) {
				querySB.append(" and t.user_password is null ");
			}
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, updatedBy);
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
	 * This method is used to get List of all Users In DE Application.
	 * </p>
	 * 
	 * @return
	 */
	public static List<UserDetails> getAllDEUserDetailsList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDetails userDetails = null;
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		try {
			String query = "select U.USER_ID,U.USER_NAME,U.USER_ADDRESS,U.USER_ACTIVE from DJB_DATA_ENTRY_USERS U where U.USER_PASSWORD is not null order by U.USER_NAME asc";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				userDetails = new UserDetails();
				userDetails.setUserId(rs.getString("USER_ID"));
				userDetails.setUserName(rs.getString("USER_NAME"));
				userDetails.setUserAddress(rs.getString("USER_ADDRESS"));
				userDetails.setMobileNo("NA");
				userDetails.setEmailId("NA");
				userDetails
						.setIsActive("Y".equals(rs.getString("USER_ACTIVE")) ? "Enabled"
								: "Disabled");
				userDetailsList.add(userDetails);
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
		return userDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get DE User Details By user Id.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public static UserDetails getDEUserDetails(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDetails userDetails = null;
		try {
			/*
			 * String query =
			 * "select U.USER_ID,U.USER_PASSWORD,U.USER_NAME,U.USER_ADDRESS,U.USER_ROLE,U.USER_ACTIVE from DJB_DATA_ENTRY_USERS U where UPPER(USER_ID)=UPPER(?)"
			 * ;
			 */
			String query = "select U.USER_ID,U.USER_PASSWORD,U.USER_NAME,U.USER_ADDRESS,U.USER_ROLE,U.USER_ACTIVE,U.USER_ZRO_CD from DJB_DATA_ENTRY_USERS U where UPPER(USER_ID)=UPPER(?)";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				userDetails = new UserDetails();
				userDetails.setUserId(rs.getString("USER_ID"));
				userDetails.setFirstName(rs.getString("USER_NAME"));
				userDetails.setUserAddress(rs.getString("USER_ADDRESS"));
				userDetails.setUserRole(rs.getString("USER_ROLE"));
				userDetails.setIsActive(rs.getString("USER_ACTIVE"));
				/*
				 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-45498
				 */
				userDetails.setZroLocation(rs.getString("USER_ZRO_CD"));
				AppLog.info("zroLoction deatils from dao :"
						+ userDetails.getZroLocation() + " for ID : "
						+ userDetails.getUserId());
				/*
				 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
				 * Location tagging of Data Entry Users, according to JTrac
				 * DJB-45498
				 */
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
		return userDetails;
	}

	/**
	 * <p>
	 * This method is used to Update user details into database for DE users.
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean updateUser(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String userRole = (String) inputMap.get("userRole");
			String updatedBy = (String) inputMap.get("updatedBy");
			String password = (String) inputMap.get("password");
			String userName = (String) inputMap.get("userName");
			String userAddress = (String) inputMap.get("userAddress");
			String isActive = (String) inputMap.get("isActive");
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			String zroLocation = (String) inputMap.get("zroLocation");
			AppLog.info("Inside dao update zroLocatios are : " + zroLocation
					+ " with id : " + userId);
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			String FIRTextData = (String) inputMap.get("FIRTextData");

			// System.out.println("userId::" + userId + "::userRole::" +
			// userRole
			// + "::updatedBy::" + updatedBy);
			StringBuffer querySB = new StringBuffer();
			querySB.append(" update djb_data_entry_users t ");
			querySB.append(" set t.user_name=?, ");
			querySB.append(" t.user_address=?, ");
			if (!(DJBConstants.DEFAULT_PASSWORD).equalsIgnoreCase(password)) {
				querySB.append(" t.user_password=?, ");
			}
			querySB.append(" t.user_role=?, ");
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			querySB.append(" t.USER_ZRO_CD=?, ");
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			if (null != isActive && !"".equals(isActive.trim())) {
				querySB.append(" t.user_active=?, ");
			}
			if (null != FIRTextData && !"".equals(FIRTextData.trim())) {
				querySB.append(" t.USER_FP1=?, ");
			}
			querySB.append(" t.modified_id=?,t.modified_date=sysdate ");
			querySB.append(" where t.user_id=? ");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userName);
			ps.setString(++i, userAddress);
			if (!(DJBConstants.DEFAULT_PASSWORD).equalsIgnoreCase(password)) {
				ps.setString(++i, password);
			}
			ps.setString(++i, userRole);
			/*
			 * Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			ps.setString(++i, zroLocation);
			/*
			 * END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO
			 * Location tagging of Data Entry Users, according to JTrac DJB-4549
			 */
			if (null != isActive && !"".equals(isActive.trim())) {
				ps.setString(++i, isActive);
			}
			if (null != FIRTextData && !"".equals(FIRTextData.trim())) {
				ps.setString(++i, FIRTextData);
			}
			ps.setString(++i, updatedBy);
			ps.setString(++i, userId);
			int rowUpdated = ps.executeUpdate();
			// System.out.println("In CreateUserDAO :: "+query);
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
	 * This method is used to Validate a user Id.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean validateUserId(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isValid = false;
		try {
			String query = "select t.user_id from  djb_data_entry_users t where UPPER(t.user_id)=UPPER(?) ";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				isValid = true;
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
		return isValid;
	}

	/**
	 * <p>
	 * Validate a user Id for if already bypassed.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac no DJB-4464 and OpenProject#1151
	 * @param userId
	 * @return
	 */
	public static boolean validateUserIdInException(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isValid = false;
		StringBuffer querySB = new StringBuffer();
		String query = null;
		try {
			querySB.append("select l.user_id from DJB_BIOMETRIC_EXCP_LIST l ");
			querySB.append("where upper(l.user_id)= '");
			querySB.append(userId.toUpperCase().trim());
			querySB.append("' and l.active_flag='");
			querySB.append(DJBConstants.FLAG_Y);
			querySB.append("'");
			query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				isValid = true;
			}
			AppLog.info(isValid);
			AppLog.info(query);
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
		return isValid;
	}

	/**
	 * <p>
	 * Validate a user Id for Bypass Finger print.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @jtrac no DJB-4464 and OpenProject#1151
	 * @param userId
	 * @return
	 */
	public static boolean validateUserRoleFingerPrint(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean usrRole = false;
		StringBuffer querySB = new StringBuffer();
		try {
			querySB
					.append("select t.user_role from  djb_data_entry_users t where UPPER(t.user_id)=UPPER(?) ");
			querySB.append("and t.user_role in (");
			querySB.append(DJBConstants.BYPASS_FP_EXCEPTION_ROLE.trim());
			querySB.append(")");
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				usrRole = true;
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
		return usrRole;
	}
}
