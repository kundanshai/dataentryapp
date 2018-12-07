/************************************************************************************************************
 * @(#) ChangePasswordDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.LDAPManager;

/**
 * <p>
 * DAO class for Changing Password.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 */
public class ChangePasswordDAO {

	/**
	 * <p>
	 * This method is used to Change Password
	 * </p>
	 * 
	 * @param inputMap
	 * @return inputMap
	 */
	public static Map<String, String> changePassword(
			Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		String isSuccess = "NO";
		try {
			String passwordChangeType = (String) inputMap
					.get("passwordChangeType");
			String userType = (String) inputMap.get("userType");
			String resetUserId = (String) inputMap.get("resetUserId");
			String oldPassword = (String) inputMap.get("oldPassword");
			String resetPassword = (String) inputMap.get("resetPassword");
			String updatedBy = (String) inputMap.get("updatedBy");
			// System.out.println("userType::" + userType
			// + "::passwordChangeType::" + passwordChangeType
			// + "::resetUserId::" + resetUserId + "::oldPassword::"
			// + oldPassword + "::resetPassword::" + resetPassword
			// + "::updatedBy::" + updatedBy);
			if ("SELF".equalsIgnoreCase(passwordChangeType)) {
				if (!"EMPLOYEE".equalsIgnoreCase(userType)) {
					String query = "update djb_data_entry_users t set t.user_password=?, t.modified_id=?,t.modified_date=sysdate where upper(t.user_id)=upper(?) and t.user_password=? ";
					conn = DBConnector.getConnection();
					ps = conn.prepareStatement(query);
					ps.setString(1, resetPassword);
					ps.setString(2, updatedBy);
					ps.setString(3, resetUserId);
					ps.setString(4, oldPassword);
					int rowUpdated = ps.executeUpdate();
					if (rowUpdated > 0) {
						isSuccess = "YES";
					}
				} else {
					String returnMessage = LDAPManager.resetSelfPassword(
							resetUserId, oldPassword, resetPassword);
					if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
						isSuccess = "YES";
					}
					inputMap.put("returnMessage", returnMessage);
				}
			} else if ("ADMIN".equalsIgnoreCase(passwordChangeType)) {
				if (!"EMPLOYEE".equalsIgnoreCase(userType)) {
					String query = "update djb_data_entry_users t set t.user_password=?, t.modified_id=?,t.modified_date=sysdate where upper(t.user_id)=upper(?) and t.user_password is not null ";
					conn = DBConnector.getConnection();
					ps = conn.prepareStatement(query);
					ps.setString(1, resetPassword);
					ps.setString(2, updatedBy);
					ps.setString(3, resetUserId);
					int rowUpdated = ps.executeUpdate();
					if (rowUpdated > 0) {
						isSuccess = "YES";
					} else {
						String returnMessage = LDAPManager.resetUserPassword(
								resetUserId, resetPassword);
						if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
							isSuccess = "YES";
						}
						inputMap.put("returnMessage", returnMessage);
					}
				} else {
					String returnMessage = LDAPManager.resetUserPassword(
							resetUserId, resetPassword);
					if ("SUCCESS".equalsIgnoreCase(returnMessage)) {
						isSuccess = "YES";
					}
					inputMap.put("returnMessage", returnMessage);
				}
			}
			inputMap.put("isSuccess", isSuccess);
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
		return inputMap;
	}
}
