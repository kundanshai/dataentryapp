/************************************************************************************************************
 * @(#) DataEntryAppStartUpConstant.java
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.UserRole;

/**
 * <p>
 * This is the Utility class for loading Constant values on DataEntryApp startup
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class DataEntryAppStartUpConstant {
	/**
	 * <p>
	 * This method is used to get list of User Roles
	 * </p>
	 * 
	 * @return
	 */
	public static List<UserRole> getUserRoleList() {
		ArrayList<UserRole> userRoleList = (ArrayList<UserRole>) GetterDAO
				.getUserRoleList();
		// ArrayList<String> userRoleList=new ArrayList<String>();
		// userRoleList.add("Data Entry User");
		// userRoleList.add("DJB Employee");
		return userRoleList;
	}
}
