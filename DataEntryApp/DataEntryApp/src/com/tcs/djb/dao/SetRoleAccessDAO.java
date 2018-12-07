/************************************************************************************************************
 * @(#) SetRoleAccessDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Set User Role Screen.
 * </p>
 *
 */
public class SetRoleAccessDAO {

	/**
	 * <p>
	 * This method is used to update User Role
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean updateUserRole(Map<String, String> inputMap) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		try {
			String userId = (String) inputMap.get("userId");
			String userRole = (String) inputMap.get("userRole");
			String updatedBy = (String) inputMap.get("updatedBy");
			// System.out.println("userId::" + userId + "::userRole::" +
			// userRole
			// + "::updatedBy::" + updatedBy);
			String query = "update djb_data_entry_users t set t.user_role=?, t.modified_id=?,t.modified_date=sysdate where t.user_id=? ";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userRole);
			ps.setString(2, updatedBy);
			ps.setString(3, userId);
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
}
