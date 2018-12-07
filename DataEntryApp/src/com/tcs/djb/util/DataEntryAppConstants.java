/************************************************************************************************************
 * @(#) DataEntryAppConstants.java
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//import com.tcs.djb.rms.dao.MeterReadDAO;
//import com.tcs.djb.rms.util.AppLog;

/**
 * <p>
 * DataEntryAppConstants holds all the constants used across the application.
 * </p>
 * 
 * @author 374204 (Tata Consultancy Services)
 */
public interface DataEntryAppConstants {
	public static boolean ENABLE_MRD_STAT_VALIDATION = false;
	public static Map<String, String> MTR_READ_TYPE_LOOKUP_BY_MR_STAT_CD = Collections
			.synchronizedMap(new HashMap<String, String>());
	

	




}
