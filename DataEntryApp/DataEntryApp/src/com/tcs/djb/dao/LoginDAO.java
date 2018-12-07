/************************************************************************************************************
 * @(#) LoginDAO.java   22 Jul 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.tcs.djb.model.UserDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.LDAPManager;

/**
 * <p>
 * DAO Class for Login process.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-07-2012
 *  @history 03-01-2014 Rajib Hazarika Modified for retrieval of vendor name from database
 */
public class LoginDAO {

	/**
	 * <p>
	 * This method is used to validate entries for Login process
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static Map<String, String> validateLogin(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Map<String, String> returnMap = new HashMap<String, String>();
		try {
			String userId = (String) inputMap.get("userId");
			String password = (String) inputMap.get("password");
			String userRole = (String) inputMap.get("userRole");
			if (null != userRole
					&& !"EMPLOYEE".equalsIgnoreCase(userRole.trim())) {
				String userName = null;
				// System.out.println("userId::" + userId + "::password::" +
				// password);
				//String query = "select U.USER_ID,U.USER_PASSWORD,UPPER(U.USER_NAME) USER_NAME,U.USER_ADDRESS,U.USER_ACTIVE,to_char(U.USER_LOGIN_TIME,'dd/mm/yyyy hh:mi am')USER_LOGIN_TIME,U.USER_ROLE,R.USER_ROLE_DESC from DJB_DATA_ENTRY_USERS U, DJB_DATA_ENTRY_USERS_ROLE R where U.USER_ROLE=R.USER_ROLE AND UPPER(USER_ID)=UPPER(?) and USER_PASSWORD=?";
				//query changed by Rajib as per DJB-2024 on 02-01-2014
				String query = "select U.USER_ID,U.USER_PASSWORD,UPPER(U.USER_NAME) USER_NAME,U.USER_ADDRESS,U.TP_VENDOR_NAME,U.USER_ACTIVE,to_char(U.USER_LOGIN_TIME,'dd/mm/yyyy hh:mi am')USER_LOGIN_TIME,U.USER_ROLE,R.USER_ROLE_DESC from DJB_DATA_ENTRY_USERS U, DJB_DATA_ENTRY_USERS_ROLE R where U.USER_ROLE=R.USER_ROLE AND UPPER(USER_ID)=UPPER(?) and USER_PASSWORD=?";
				// AppLog.info("query>>" + query);
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				ps.setString(2, password);
				rs = ps.executeQuery();
				while (rs.next()) {
					userName = rs.getString("USER_NAME");
					inputMap.put("userId", rs.getString("USER_ID"));
					inputMap.put("userName", userName);
					inputMap.put("userRoleId", rs.getString("USER_ROLE"));
					inputMap
							.put("userRoleDesc", rs.getString("USER_ROLE_DESC"));
					inputMap.put("userAddress", rs.getString("USER_ADDRESS"));
					inputMap.put("lastLoginTime", rs
							.getString("USER_LOGIN_TIME"));
					//Added By Rajib as per Jtrac DJB-2024 on 02-01-2014
					inputMap.put("vendorName", rs.getString("TP_VENDOR_NAME"));
					//Change finish by Rajib
					String isActive = rs.getString("USER_ACTIVE");
					
					if ("Y".equalsIgnoreCase(isActive)) {
						inputMap.put("status", "Success");
					} else {
						inputMap
								.put("status",
										"Your Account has been disabled. Please contact your Administrator.");
					}
				}
				if (null != ps) {
					ps.close();
				}
				if (null != userName) {
					query = "update DJB_DATA_ENTRY_USERS set USER_LOGIN_TIME=sysdate where UPPER(USER_ID)=UPPER(?) and USER_PASSWORD=?";
					// AppLog.info("query>>" + query);
					ps = conn.prepareStatement(query);
					ps.setString(1, userId);
					ps.setString(2, password);
					ps.executeUpdate();
				}
			} else {
				// LdapLoginModule to be Analyzed
				UserDetails userDetails = LDAPManager.authenticateLDAPUser(
						userId, password);
				// System.out.println("In Login DAO userId :: "+userId+" :: password "+password);
				if (null != userDetails && userDetails.isAuthenticatedUser()) {
					if ("Success".equals(userDetails.getStatus())) {
						inputMap.put("userName", userDetails.getFirstName()
								+ " " + userDetails.getLastName());
						AppLog.info("LDAP USER NAME::"
								+ userDetails.getFirstName() + " "
								+ userDetails.getLastName());
						// if (null != employeeBean.getUserRole()
						// && employeeBean.getUserRole().indexOf(
						// "ADMINISTRATOR") != -1) {
						// userRole = "ADMINISTRATOR";
						// inputMap.put("userRoleId", "3");
						// inputMap.put("userRoleDesc", userRole);
						// } else {
						// userRole = "EMPLOYEE";
						// inputMap.put("userRoleId", "2");
						// inputMap.put("userRoleDesc", userRole);
						// }
						inputMap.put("userRoleId", "0");
						inputMap.put("userRoleDesc", "NO ROLE");
						inputMap.put("userAddress", "");
						inputMap.put("lastLoginTime", "NA");
						inputMap = getLastLoginInfoForEmployee(inputMap);

					} else {
						inputMap.put("status", userDetails.getStatus());
						inputMap.put("statusCode", userDetails
								.getAuthenticationErrorCode());
					}
				} else {
					inputMap.put("status", userDetails.getStatus());
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
		return inputMap;
	}

	/**
	 * <p>
	 * This method is used to get last login information for an employee
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static Map<String, String> getLastLoginInfoForEmployee(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String userId = (String) inputMap.get("userId");
			String password = (String) inputMap.get("password");
			String userNameLDAP = (String) inputMap.get("userName");
			String userRoleId = (String) inputMap.get("userRoleId");
			String userRoleDesc = (String) inputMap.get("userRoleDesc");
			String userName = null;
			// System.out.println("userId::" + userId + "::password::" +
			// password);
			String query = "select U.USER_ID,U.USER_PASSWORD,UPPER(U.USER_NAME) USER_NAME,U.USER_ADDRESS,USER_ACTIVE,to_char(U.USER_LOGIN_TIME,'dd/mm/yyyy hh:mi am')USER_LOGIN_TIME,U.USER_ROLE,R.USER_ROLE_DESC from DJB_DATA_ENTRY_USERS U, DJB_DATA_ENTRY_USERS_ROLE R where U.USER_ROLE=R.USER_ROLE AND UPPER(USER_ID)=UPPER(?)";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userName = rs.getString("USER_NAME");
				inputMap.put("userId", rs.getString("USER_ID"));
				inputMap.put("userName", userName);
				inputMap.put("userRoleId", rs.getString("USER_ROLE"));
				inputMap.put("userRoleDesc", rs.getString("USER_ROLE_DESC"));
				inputMap.put("userAddress", rs.getString("USER_ADDRESS"));
				inputMap.put("lastLoginTime", rs.getString("USER_LOGIN_TIME"));
				String isActive = rs.getString("USER_ACTIVE");
				if ("Y".equalsIgnoreCase(isActive)) {
					inputMap.put("status", "Success");
				} else {
					inputMap
							.put(
									"status",
									"You do not have Sufficient Privilege to use this Application. Please Contact your Administrator.");
				}
				// System.out
				// .println("userName::" + userName + "::lastLoginTime::"
				// + rs.getString("USER_LOGIN_TIME"));
			}
			if (null != ps) {
				ps.close();
			}
			if (null != userName) {
				query = "update DJB_DATA_ENTRY_USERS set USER_LOGIN_TIME=sysdate where UPPER(USER_ID)=UPPER(?) ";
				// AppLog.info("query>>" + query);
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				/* int rowsUpdated = */
				ps.executeUpdate();
			} else {
				inputMap.put("userName", userNameLDAP);
				inputMap.put("userRoleId", userRoleId);
				inputMap.put("userRoleDesc", userRoleDesc);
				inputMap.put("userAddress", "NA");
				inputMap.put("lastLoginTime", "NA");
				// System.out.println("Insert userId::" + userId +
				// "::password::"
				// + password + "::userNameLDAP::" + userNameLDAP
				// + "::userRoleId::" + userRoleId);
				query = " insert into DJB_DATA_ENTRY_USERS (USER_ID, USER_PASSWORD, USER_NAME, USER_ADDRESS, CREATE_ID,USER_ROLE) values (?, ?, ?, 'DJB', 'SYSTEM',?)";
				// AppLog.info("query>>" + query);
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				ps.setString(2, password);
				ps.setString(3, userNameLDAP);
				ps.setInt(4, Integer.parseInt((null == userRoleId || ""
						.equals(userRoleId.trim())) ? "0" : userRoleId));
				/* int rowsUpdated = */
				ps.executeUpdate();
				// System.out.println("rowsUpdated::" + rowsUpdated);
			}

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
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
		return inputMap;
	}

}
