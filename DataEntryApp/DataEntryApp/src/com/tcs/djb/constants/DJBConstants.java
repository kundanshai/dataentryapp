/************************************************************************************************************
 * @(#) DJBConstants.java   23 May 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.constants;

import com.tcs.djb.util.PropertyUtil;

/**
 * <P>
 * This interface includes all the constants parameter used in the Data Entry
 * Application.
 * </P>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * 
 * @see PropertyUtil
 * @history Added Reshma on 23-05-2013 service cycle & service route values from
 *          property file.
 * @history 02-07-2013 Matiur Rahman Added
 *          BILL_GENERATION_SOURCE,METER_READ_SOURCE, values to be read from
 *          property file.
 * @history 08-08-2013 Reshma Added variables FTP_HOST_DIR and
 *          FTP_UPLOAD_FILE_EXT for values to be read from property file,JTrac
 *          HHD-24.
 * @history 09-01-2014 Matiur Rahman Added
 *          {@link #OB_PASSWORD_CHANGE_FLAG_VALUE}.
 * @history 04-02-2014 Matiur Rahman Added {@link #CCB_MTR_RPLC_SERVICE_ERR_MSG}
 *          ,{@link #CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE} as per JTrac ID
 *          DJB-2024 updated on 04-02-2014 as per telephonic conversation with
 *          Amit Jain, Priyanka.
 * @history 04-02-2014 Matiur Rahman Added constants
 *          {@link #DISCONNECTED_OR_SA_START_DATE_BEFORE_JAN_2010_ERROR_MSG},
 *          {@link #METER_INSTALLATION_DATE_BEFORE_JAN_2010_ERROR_MSG},
 *          {@link #METER_INSTALLATION_DATE_SAME_AS_OLD_METER_INSTALL_DATE_ERROR_MSG}
 *          ,{@link #METER_REPLACEMENT_PARK_STATUS_CODE},
 *          {@link #DATA_ENTRY_PARK_STATUS_CODE} as a part of changes as
 *          perJTrac ID# DJB-2024.
 * @history 23-09-2015 Aniket Chatterjee Added constants {@link #DOC_TYPE_RWH},
 *          {@link #WASTE_WAT_CHAR_TYPE}, {@link #UCM_UPLOAD},
 *          {@link #CUST_CL_CD_FOR_WWT}, {@link #DOC_TYPE_RWH} as a part of
 *          changes as perJTrac ID# DJB-4037.
 * @history 24-09-2015 Rajib Hazarika Added {@link #ASSET_RECORD_TYPE_HANDHELD},
 *          {@link #ASSET_ACTIVE_STATUS} as per JTRac DJB-3871
 * @history Reshma on 13-10-2015 added constants
 *          {@link #FIELD_NO_OF_ADULT_MAX_LENGTH},
 *          {@link #FIELD_NO_OF_CHILD_MAX_LENGTH} defining the max length of
 *          fields as in New Connection Screen, JTrac DJB-4195.
 * @history Atanu on 11-01-2016 added constants {@link #SEARCH_AUDIT_STATUS}
 *          ,defining the image audit status {@link #UPDATE_STATUS_LIST}
 *          ,defining the update status list
 *          {@link #SEARCH_AUDIT_STATUS_DEFAULT} defining default value of image
 *          audit status, Atanu on 25-01-2016 added constants
 *          {@link #RANGE_ANNUAL_AVERAGE_CONSUMPTION},
 *          {@link #RANGE_VARIATION_PREVIOUS_ROUND}
 *          {@link #CONSUMPTION_VARIATION_AUDIT_STATUS}
 *          {@link #KNO_AUDIT_OK_REMARK} {@link #KNO_AUDIT_NO_REMARK}
 *          {@link #KNO_AUDIT_COMPLETE_STATUS}
 *          {@link #KNO_AUDIT_INPROGRESS_STATUS}
 *          {@link #KNO_AUDIT_SELF_ASSIGNTO} {@link #KNO_AUDIT_MR_ASSIGNTO}
 *          JTrac ANDROID-293.
 * 
 * @history Rajib On 14-JAN onwards added below constants as per Jtrac DJB-4313.
 *          {@link #FILE_ENTRY_INITIAL_STATUS}
 *          {@link #NEW_CONN_TYPE_OF_APP_VALUES}, {@link #PROOF_OF_ID_DOC_TYPE},
 *          {@link #PROOF_OF_RES_DOC_TYPE}, {@link #PROPERTY_OWNERSHIP_DOC_TYPE}
 *          {@link #FIELD_MAX_LEN_FILE_NO}, {@link #FIELD_MAX_LEN_FIRST_NAME},
 *          {@link #FIELD_MAX_LEN_MIDDLE_NAME}, {@link #FIELD_MAX_LEN_LAST_NAME}
 *          , {@link #FIELD_MAX_LEN_CONTACT_NO}, {@link #FIELD_MAX_LEN_EMAIL},
 *          {@link #FIELD_MAX_LEN_PIN_CODE}, {@link #FIELD_MAX_LEN_HOUSE_NO},
 *          {@link #FIELD_MAX_LEN_ROAD_NO}, {@link #FIELD_MAX_LEN_SUBLOC1},
 *          {@link #FIELD_MAX_LEN_SUBLOC2}, {@link #FIELD_MAX_LEN_SUBCOLONY},
 *          {@link #FIELD_MAX_LEN_VILLAGE}, {@link #FILE_NO_VALIDATION_FLG},
 *          {@link #HQ_VALIDATION_FLG}, {@link #FILE_VALIDATION_BYPASSED_ZROCD}
 * @history 06-01-2016 Arnab Nandy Added {@link #LIST_COL_CAT},
 *          {@link #LIST_COL_CAT} as per JTRac DJB-4304
 * @history on 28-01-2016 Arnab Nandy (1227971) added constants
 *          {@link #LOT_NO_MAX_LENGTH} ,{@link #ZRO_SEARCH} defining the Status
 *          JTrac DJB-4326.
 * @history On 12-FEB-2016 Rajib Hazarika Added constants
 *          {@link #CHAR_TYPE_CD_YES_NO} {@link #CHAR_TYPE_CD_CM_UNUSE}
 *          {@link #CHAR_TYPE_CD_PREFMOP} {@link #CHAR_TYPE_CD_WATFEAS}
 *          {@link #CHAR_TYPE_CD_SEWFEAS} {@link #CHAR_TYPE_CD_DOCVAR}
 *          {@link #CHAR_TYPE_CD_DJB_EMP} {@link #CHAR_TYPE_CD_WCONSIZE}
 *          {@link #CHAR_TYPE_CD_OSAPP} {@link #CHAR_TYPE_CD_APLY_SWR}
 *          {@link #CHAR_TYPE_CD_CM_SW_CL} {@link #CHAR_TYPE_CD_APLY_WTR}
 *          {@link #CHAR_TYPE_CD_CM_WT_CL} {@link #CHAR_TYPE_CD_ZRO_LOC}
 * @history On 16-FEB-2016 Rajib Hazarika Added below constants as per JTrac
 *          DJB-4313 {@link #TO_DO_FOR_NEW_CON_FORM},
 *          {@link #CUST_COL_FOR_DEV_CHRG_NOT_USED},
 *          {@link #STATUS_CD_FOR_NEW_CON_ACCT_GEN},
 *          {@link #STATUS_CD_FOR_NEW_CON_BILL_GEN},
 *          {@link #SEW_DEV_CHRG_COL_NOTIFYDT_NA},{@link #FALSE_KNO_VALUE},
 *          {@link #WAT_DEV_CHRG_COL_NOTIFYDT_NA},
 *          {@link #FIELD_NOT_TO_BE_USED_VAL},
 *          {@link #FLAG_DYNAMIC_AUTHCOOKIE_FOR_BILL_GEN},
 *          {@link #FLAG_DYNAMIC_AUTHCOOKIE_FOR_KNO_GEN},
 *          {@link #ALLOWED_STATUS_CD_FOR_KNO_GEN},{@link #SALT_PARAM_KEY}
 * @history On 10-03-2016 Atanu Added below constants as per jTrac ANDROID-293
 *          and changed the value of ALLOWED_STATUS_CD_FOR_BILL_GEN to 89 as
 *          confirmed by Arnab and Reshma Di
 *          {@link #ABNORMAL_CONSUMPTION_REASON},
 *          {@link #DISABLE_SELF_BILLING_MESSAGE_TEXT},
 *          {@link #FINAL_AUDIT_ACTION}, {@link #NON_SATISFACTORY_REASON},
 *          {@link #SATISFACTORY_REASON}, {@link #SMS_SENT_FAILURE_RESPONSE},
 *          {@link #STATUS_FAILED_CODE}, {@link #STATUS_SUCCESS_CODE},
 *          {@link #SUGGESTED_AUDIT_ACTION}, {@link #WARNING_ALERT_MESSAGE},
 *          {@link #WARNING_ALERT_MESSAGE_TEXT}
 * @history on 17-03-2016 Sanjay (1033846) added constants
 *          {@link #CHECK_DIGIT_WITH_DOT_OPTNL_REGEX defining the Status JTrac
 *          DJB-4418.
 *          
 * @history on 22-04-2016 Diksha for adding a validation on opening balance field  asper Jtrac DJB-4211
 * 			{@link #CHECK_AMOUNT_OPTIONAL_NEGATV_OR_DOT_REGEX_JS 
 * @history on 05-05-2016 Atanu added the below field as Jtrac DJB-4211
 * 			{@link #CATIIIA_CATAGORY_CODE  },
 * 			{@link #CATIIIB_CATAGORY_CODE  },
 * 			{@link #MAX_NO_OF_BOREWELLS}
 * @history on 07-06-2016 Lovely (986018) added constant as
 *          per jtrac-DJB 4482 and openProject-1206 {@link #MRKEY_TAGGED_MSG}
 * @history on 07-06-2016 Madhuri (735689) added constant as
 *          per jtrac-DJB 4464 and openProject-1203 {@link #AMR_ROLE}
 *          
 * @history on 31-05-2016 Arnab Nandy (1227971) added constants {@link #DATA_MIGRATION_ZRO_LIST} defining 
 * 			the ZRO list,{@link #DATA_MIGRATION_REPORT_URL_PART1}, {@link #DATA_MIGRATION_REPORT_URL_PART2} and
 *          {@link #DB_SERVER_IPADDR} for listing for data migration, as per JTrac DJB-4465 and OpenProject-918.
 *  
 * @history 27-05-2016 Arnab Nandy Added {@link #BYPASS_FP_EXCEPTION_ROLE} to Bypass fingerprint exception 
 * 			user role and {@link #RESTRICT_REG_FP_FOR_USER_ROLE}, {@link #USER_ID_REGEX_JS}, 
 * 			{@link #INVALID_USER_ID_MESSAGE} as per JTRac DJB-4464 and OpenProject#1151.
 * 
 * @history 20-06-2016 Atanu Mandal Added {@link #AMR_AREA_HEADER}, 
 * 			{@link #AMR_END_DATE_HEADER}, {@link #AMR_FILE_EXTENSION}, 
 * 			{@link #AMR_HEADER_COLUMN_COUNT},
 * 			{@link #AMR_HEADER_ROW_NUMBER}, {@link #AMR_MAX_NO_OF_RECORDS_FOR_UPLOAD_SERVICE},
 * 			{@link #AMR_MAX_THREAD_COUNT}, {@link #AMR_METER_READ_DIARY_CODE_HEADER},
 * 			{@link #AMR_METER_READER_HEADER}, {@link #AMR_MR_HEADER},
 * 			{@link #AMR_UPLOAD_SUCCESS_MSG}, {@link #AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE},
 * 			{@link #CODE_FOR_INVALID_DATA_IN_CONS_PRE_BILL_STAT_LOOKUP}, {@link #MAP_KEY_IS_FORBIDDEN_TIME},
 * 			{@link #ROWS_EXEMPT_AMR_EXCEL}, {@link #SOAP_ACTION},
 * 			{@link #WEB_SERVICE_PROVIDER_URL}, as per JTRac DJB-4464 and OpenProject#1202.         
* @history on 20-05-2016 Lovely added the below field as per JTrac DJB-4465 and OpenProject-918.
 * 			{@link #UPLOAD_SUCCESS_MSG  },
 *          {@link #ROWS_TO_EXEMPT  },
 *          {@link #FILE_ERROR_MSG  },
 *          {@link #FILE_SIZE_MSG  },
 *          {@link #FILE_UPLOAD_MSG  },
 *          {@link # FILE_FORMAT_MSG  }, 
 *          {@link # FILE_EXTENSION  }, 
 *          {@link # MAP_KEY }, 
 *           {@link # FILE_SIZE_LIMIT }, 
 *          {@link # UCM_DOCUMENT }, 
 *          {@link # MAP_KEY_VALUE  }, 
 *          {@link # DECLARATION_ALERT_MSG }, 
 *          {@link #DECLARATION_MSG  }  ,  
 *          {@link #UPLOAD_FAILURE_MSG  },
 *          {@link #MAX_COLUMN_COUNT },
 *          {@link #DB_SERVER_IPADDRR   } AND
 *          {@link # INVALID_FILE_TYPE_MSG    } 
 *@history Lovely on 14-07-2016 added the below fields as JTrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867
 *          {@link #DATA_SAVED_MESSAGE  },
 *          {@link #DATA_UNSUCCESS_MESSAGE  },
 *          {@link #NUMBER_ALLOWED_MSG  },
 *          {@link #MANDATORY_FIELD_MSG  },
 *          {@link #BOOKLET_MSG  },
 *          {@link #BOOKLET_RANGE_MSG  },
 *          {@link #ZRO_MESSAGE}
 *          
 *@history Madhuri on 11-08-2016 :- Added the below fields as JTrac DJB-4538 and OpenProject-CODE DEVELOPMENT #1429
 *			{@link #SEPERATOR}    
 *@history  Lovely on 24-08-2016 :- Added the below fields as JTrac DJB-4541 and OpenProject-CODE DEVELOPMENT 1443 #
             {@link #STATUS_CD}    
 *@history: Rajib Hazarika on 29-08-2016 : Added field {@link #DYNAMIC_AUTH_COOKIE_FOR_RAYB}, {@link #ROCKY_YAMUNA_CHAR_TYPE} as per JTrac DJB-4537, Open project Id #1442
 *@history: Sanjay Kumar Das on 30-08-2016 : Added field {@link #CONSUMER_STATUS_HHD} as per JTrac DJB-4553, Open project Id #1459
 *@history: Sanjay Kumar Das on 31-08-2016 : Added field {@link #UCM_LOAD_BALANCER_PATH} as per JTrac DJB-4547, Open project Id #1462
 *@history: Rajib Hazarika(682667,Rajib hazarika) on 16-SEP-2016 Added field {@link #FILE_NO_TAGGED_TO_EMP_MSG} as per JTrac ANDROMR-7 for Quick new Connection CR
 *@history  Lovely on 27-09-2016 :- Added  {@link #FILE_RANGE_MSG},{@link #ASSIGN_SUCCESS_MSG },{@link #USED_FILE_MSG },{@link #ALREADY_ASSIGNED_MSG },{@link #FILE_NOT_ZRO_MSG },
 *            {@link #FILE_NBR_SELECT_MSG },{@link #WRONG_ZRO_OF_MTRRDR },{@link #CANNOT_DELETE_MSG },{@link #DELETE_FILE_MSG },{@link #WRONG_ZRO_OF_MTRRDR_SELECTED },
 *            {@link #UPDATE_SUCCESS_MSG} and {@link #UPDATE_FAIL_MSG} as per jTrac DJB-4571,Openproject- #1492. 
 *@history Madhuri on 06-09-2016 :- Added the below fields as JTrac DJB-2200 and Openproject- #1540. 
 *			{@link #SCREEN_ID_MRD_TRANSFER},
 *			{@#MAX_INPROGRESS_COUNT},{@#MRD_TRANSFER_SUCCESS},
 *			{@#MRD_TRANSFER_INPROGRESS_MSG},
 *			{@#MRD_TRANSFER_MANDATORY_MSG},
 *			{@#MRD_TRANSFER_INITIATED_MSG}, 
 *   		{@#ZRO_TRANSFER_INITIATED_MSG}
 *@history Madhuri on 23-11-2016:- Added the below fields as per JTrac DJB-4604. 
 *   		{@link #SELF_BILLING_STATUS_CD},
 *   		{@link#CHAR_VAL_FOR_SELF_BILL}
 *   		{@#ZRO_TRANSFER_INITIATED_MSG}   
 *@history Sanjay on 17-11-2016 :- Added the below fields as JTrac DJB-4583 and Openproject- #1595.  
 *          {@link #HIGH_LOW_STATUS}    
 *@history Sanjay on 17-11-2016 :- Added the below fields as JTrac DJB-4653.  
 *          {@link #IS_SELF} 
*@history  Madhuri on 04-09-2017 :- Added the below fields as JTrac Android-388
 *          {@link #BILL_GEN_SOURCE}                   
 *@history  Suraj on 12-09-2017 :- Added the below fields as JTrac DJB-4735
 *          {@link #BILL_ID_NULL_MSG},
 *   		{@link #DB_SESSION_BUSY_ERROR_MSG},
 *          {@link #DB_SESSION_ALLOWED_CD},
 *          {@link #BILL_GEN_SLEEP_TIME},  
 *          {@link #DB_SESSION_COUNT_CHECK_FLAG},   
 *          {@link #DB_SESSION_QUERY_TO_CHECK}        
 */
public interface DJBConstants {
	/**
	 * 
	 */
	public static final String _JNDI_DS_PORTAL = PropertyUtil
			.getProperty("JNDI_DS_PORTAL");
	/**
	 * <p>
	 * Specifies the abnormal consumption reason as NON-SATISFACTORY or SATISFACTORY
	 * </p>
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static final String ABNORMAL_CONSUMPTION_REASON = (null != PropertyUtil
			.getProperty("ABNORMAL_CONSUMPTION_REASON") ? PropertyUtil
			.getProperty("ABNORMAL_CONSUMPTION_REASON")
			: "NON-SATISFACTORY,SATISFACTORY");
	/**
	 * <p>
	 * Specifies flag for code value equals to <code>A</code>, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String ACTIVE_FLG = "A";
	/**
	 * <p>
	 * Specifies the allowed status type code for KNO Generation as per JTrac
	 * DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static final String ALLOWED_STATUS_CD_FOR_BILL_GEN = (null != PropertyUtil
			.getProperty("ALLOWED_STATUS_CD_FOR_BILL_GEN") ? PropertyUtil
			.getProperty("ALLOWED_STATUS_CD_FOR_BILL_GEN") : "80,89");
	/**
	 * <p>
	 * Specifies the allowed status type code for KNO Generation as per JTrac
	 * DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static final String ALLOWED_STATUS_CD_FOR_KNO_GEN = (null != PropertyUtil
			.getProperty("ALLOWED_STATUS_CD_FOR_KNO_GEN") ? PropertyUtil
			.getProperty("ALLOWED_STATUS_CD_FOR_KNO_GEN") : "70,79");

	/**
	 * <p>
	 * Already assigned msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String ALREADY_ASSIGNED_MSG = null != PropertyUtil
			.getProperty("ALREADY_ASSIGNED_MSG") ? PropertyUtil
			.getProperty("ALREADY_ASSIGNED_MSG")
			: "Sorry, these set of file numbers have already been assigned to a meter reader.";
	/**
	 * <p>
	 * Specifies the user to enable ActiveX controls and plug-ins.And then
	 * Refresh the page and try again.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_ACTIVEX_ENABLE_MSG = null != PropertyUtil
			.getProperty("AMR_ACTIVEX_ENABLE_MSG") ? PropertyUtil
			.getProperty("AMR_ACTIVEX_ENABLE_MSG")
			: "Please Enable the ActiveX controls and plug-ins.And then Refresh the page and try again.To Enable go to the below path :: IE Settings --> Internet Options --> Security(tab)--> Local intranet --> Custom level";

	/**
	 * <p>
	 * Specifies the AMR area header as AREA
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_AREA_HEADER = null != PropertyUtil
			.getProperty("AMR_AREA_HEADER") ? PropertyUtil
			.getProperty("AMR_AREA_HEADER") : "AREA";
	/**
	 * <p>
	 * Specifies the AMR end date as END DATE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_END_DATE_HEADER = null != PropertyUtil
			.getProperty("AMR_END_DATE_HEADER") ? PropertyUtil
			.getProperty("AMR_END_DATE_HEADER") : "END DATE";
	/**
	 * <p>
	 * Specifies the AMR file extension as xls
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_FILE_EXTENSION = null != PropertyUtil
			.getProperty("AMR_FILE_EXTENSION") ? PropertyUtil
			.getProperty("AMR_FILE_EXTENSION") : "xls";
	/**
	 * <p>
	 * Specifies the AMR file size as Maximum file size 2*1024*1024 bytes
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_FILE_SIZE = null != PropertyUtil
			.getProperty("AMR_FILE_SIZE") ? PropertyUtil
			.getProperty("AMR_FILE_SIZE") : "2097152";
	/**
	 * <p>
	 * Specifies the AMR file size message as The file size is more than 2 MB,
	 * please retry with a lower file size. bytes
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_FILE_SIZE_MSG = null != PropertyUtil
			.getProperty("AMR_FILE_SIZE_MSG") ? PropertyUtil
			.getProperty("AMR_FILE_SIZE_MSG")
			: "The file size is more than 2 MB, please retry with a lower file size.";

	/**
	 * <p>
	 * Specifies the AMR file upload failure message as There was a problem
	 * while uploading the file. Please retry.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_FILE_UPLOAD_FAILURE_MSG = null != PropertyUtil
			.getProperty("AMR_FILE_UPLOAD_FAILURE_MSG") ? PropertyUtil
			.getProperty("AMR_FILE_UPLOAD_FAILURE_MSG")
			: "There was a problem while uploading the file. Please retry.";
	/**
	 * <p>
	 * Specifies the AMR header column count as 23
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_HEADER_COLUMN_COUNT = null != PropertyUtil
			.getProperty("AMR_HEADER_COLUMN_COUNT") ? PropertyUtil
			.getProperty("AMR_HEADER_COLUMN_COUNT") : "23";
	/**
	 * <p>
	 * Specifies the AMR header row number as 5
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_HEADER_ROW_NUMBER = null != PropertyUtil
			.getProperty("AMR_HEADER_ROW_NUMBER") ? PropertyUtil
			.getProperty("AMR_HEADER_ROW_NUMBER") : "5";

	/**
	 * <p>
	 * Specifies the AMR validation failure message as Actual Column Count
	 * Doesn't Match With the Original Downloaded File
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_HEADER_VALIDATION_FAILURE_MSG = null != PropertyUtil
			.getProperty("AMR_HEADER_VALIDATION_FAILURE_MSG") ? PropertyUtil
			.getProperty("AMR_HEADER_VALIDATION_FAILURE_MSG")
			: "Actual Column Count Doesn't Match With the Original Downloaded File";
	/**
	 * <p>
	 * Specifies the AMR maximum number of records for upload as 2
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_MAX_NO_OF_RECORDS_FOR_UPLOAD_SERVICE = null != PropertyUtil
			.getProperty("AMR_MAX_NO_OF_RECORDS_FOR_UPLOAD_SERVICE") ? PropertyUtil
			.getProperty("AMR_MAX_NO_OF_RECORDS_FOR_UPLOAD_SERVICE")
			: "2";
	/**
	 * <p>
	 * Specifies the AMR maximum thread count as 3
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final int AMR_MAX_THREAD_COUNT = Integer
			.parseInt(null != PropertyUtil.getProperty("AMR_MAX_THREAD_COUNT") ? PropertyUtil
					.getProperty("AMR_MAX_THREAD_COUNT")
					: "3");
	/**
	 * <p>
	 * Specifies the AMR meter read diary code header as METER READ DIARY CODE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_METER_READ_DIARY_CODE_HEADER = null != PropertyUtil
			.getProperty("AMR_METER_READ_DIARY_CODE_HEADER") ? PropertyUtil
			.getProperty("AMR_METER_READ_DIARY_CODE_HEADER")
			: "METER READ DIARY CODE";
	/**
	 * <p>
	 * Specifies the AMR meter reader header as METER READER
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_METER_READER_HEADER = null != PropertyUtil
			.getProperty("AMR_METER_READER_HEADER") ? PropertyUtil
			.getProperty("AMR_METER_READER_HEADER") : "METER READER";
	/**
	 * <p>
	 * Specifies the AMR MR header as MR
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_MR_HEADER = null != PropertyUtil
			.getProperty("AMR_MR_HEADER") ? PropertyUtil
			.getProperty("AMR_MR_HEADER") : "MR";

	/**
	 * <p>
	 * Specifies the AMR mrkey mismatch message as Tagged MRKEY Doesn't Match
	 * With The ZONE,MR,AREA Combination.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_MRKEY_MISMATCH_MSG = null != PropertyUtil
			.getProperty("AMR_MRKEY_MISMATCH_MSG") ? PropertyUtil
			.getProperty("AMR_MRKEY_MISMATCH_MSG")
			: "Tagged MRKEY Doesn't Match With The ZONE,MR,AREA Combination.";

	/**
	 * <p>
	 * Specifies the AMR partial data upload message as Data Upload Process
	 * initiated Successfully For Partial Data.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_PARTIAL_DATA_UPLOAD_MSG = null != PropertyUtil
			.getProperty("AMR_PARTIAL_DATA_UPLOAD_MSG") ? PropertyUtil
			.getProperty("AMR_PARTIAL_DATA_UPLOAD_MSG")
			: "Data Upload Process initiated Successfully For Partial Data.";

	/**
	 * <p>
	 * Search AMR_ROLE
	 * </p>
	 * 
	 * @author Madhuri (735689)
	 * @since
	 */

	public static final String AMR_ROLE = null != PropertyUtil
			.getProperty("AMR_ROLE") ? PropertyUtil.getProperty("AMR_ROLE")
			: "10";
	/**
	 * <p>
	 * Specifies the AMR route header as ROUTE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_ROUTE_HEADER = null != PropertyUtil
			.getProperty("AMR_ROUTE_HEADER") ? PropertyUtil
			.getProperty("AMR_ROUTE_HEADER") : "ROUTE";
	/**
	 * <p>
	 * Specifies the AMR service call freeze flag as N
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_SERVICE_CALL_FREEZE_FLAG = null != PropertyUtil
			.getProperty("AMR_SERVICE_CALL_FREEZE_FLAG") ? PropertyUtil
			.getProperty("AMR_SERVICE_CALL_FREEZE_FLAG") : "N";
	/**
	 * <p>
	 * Specifies the AMR service call header as SERVICE CYCLE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_SERVICE_CYCLE_HEADER = null != PropertyUtil
			.getProperty("AMR_SERVICE_CYCLE_HEADER") ? PropertyUtil
			.getProperty("AMR_SERVICE_CYCLE_HEADER") : "SERVICE CYCLE";
	/**
	 * <p>
	 * Specifies the AMR start date header as START DATE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_START_DATE_HEADER = null != PropertyUtil
			.getProperty("AMR_START_DATE_HEADER") ? PropertyUtil
			.getProperty("AMR_START_DATE_HEADER") : "START DATE";
	/**
	 * <p>
	 * Specifies the AMR upload success message as Your File Has Been uploaded
	 * successfully and Process has been Initiated.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_UPLOAD_SUCCESS_MSG = null != PropertyUtil
			.getProperty("AMR_UPLOAD_SUCCESS_MSG") ? PropertyUtil
			.getProperty("AMR_UPLOAD_SUCCESS_MSG")
			: "Your File Has Been uploaded successfully and Process has been Initiated.";

	/**
	 * <p>
	 * Specifies the AMR zone header as ZONE
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_ZONE_HEADER = null != PropertyUtil
			.getProperty("AMR_ZONE_HEADER") ? PropertyUtil
			.getProperty("AMR_ZONE_HEADER") : "ZONE";
	/**
	 * <p>
	 * Specifies the AMR ZRO header as ZRO
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AMR_ZRO_HEADER = null != PropertyUtil
			.getProperty("AMR_ZRO_HEADER") ? PropertyUtil
			.getProperty("AMR_ZRO_HEADER") : "ZRO";

	/**
	 * <p>
	 * value of Active Status Flag for HandHeld, Added as per JTrac DJB-3871
	 * </p>
	 * 
	 * @author 682667
	 * @since 09-24-2015
	 */
	public static final String ASSET_ACTIVE_STATUS = null != PropertyUtil
			.getProperty("ASSET_ACTIVE_STATUS") ? PropertyUtil
			.getProperty("ASSET_ACTIVE_STATUS") : "ACTIVE";
	/**
	 * <p>
	 * value of Asset Record Type for Handheld
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */
	public static final String ASSET_RECORD_TYPE_HANDHELD = null != PropertyUtil
			.getProperty("ASSET_RECORD_TYPE_HANDHELD") ? PropertyUtil
			.getProperty("ASSET_RECORD_TYPE_HANDHELD") : "H";
	/**
	 * <p>
	 * file number successfully assign msg to meter reader
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String ASSIGN_SUCCESS_MSG = null != PropertyUtil
			.getProperty("ASSIGN_SUCCESS_MSG") ? PropertyUtil
			.getProperty("ASSIGN_SUCCESS_MSG")
			: "File numbers have been successfully assigned to the user id  ";
	/**
	 * <p>
	 * Specifies the Authkey generation service parameter for meter read upload
	 * web service as Meter Read Upload
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE = null != PropertyUtil
			.getProperty("AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE") ? PropertyUtil
			.getProperty("AUTHKEY_GEN_SERVICE_PARAM_FOR_METER_READ_UPLOAD_WEB_SERVICE")
			: "Meter Read Upload";

	/**
	 * <p>
	 * Single KNO Bill Generation Process sleep time.
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static int BILL_GEN_SLEEP_TIME = null != PropertyUtil
			.getProperty("BILL_GEN_SLEEP_TIME") ? Integer.parseInt(PropertyUtil
			.getProperty("BILL_GEN_SLEEP_TIME")) : 30000;
	/**
	 * <p>
	 * BILL_GEN_SOURCE for Meter Reader Image audit screen
	 * </p>
	 * 
	 * @author 735689
	 * @since 01-09-2017 as per JTrac Android-388
	 */
	public static final String BILL_GEN_SOURCE = null != PropertyUtil
			.getProperty("BILL_GEN_SOURCE") ? PropertyUtil
			.getProperty("BILL_GEN_SOURCE") : "mSeva,MR Android App";
	/**
	 * <p>
	 * Value of static variable <code>BILL_GENERATION_SOURCE</code> is retrieved
	 * from Property File <code>djb_portal_data_entry.properties</code> for
	 * CM_OnlineBillGenerationService CC&B service used for bill id Generation.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 */
	public static final String BILL_GENERATION_SOURCE = PropertyUtil
			.getProperty("BILL_GENERATION_SOURCE");
	/**
	 * <p>
	 * Status code to send error msg for bill not genereted.
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static String BILL_ID_NULL_MSG = null != PropertyUtil
			.getProperty("BILL_ID_NULL_MSG") ? (PropertyUtil
			.getProperty("BILL_ID_NULL_MSG"))
			: "Bill Id could not be generated";
	/**
	 * <p>
	 * Specifies BillId Download Url Part1 <code>FIELD_MAX_LEN_ARN_NO</code> for
	 * New Con File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 20-FEB-2016
	 */
	public static final String BILL_ID_REPORT_URL_PART1 = (null == PropertyUtil
			.getProperty("BILL_ID_REPORT_URL_PART1") ? "http://10.18.21.146:7003/DJBPortal/portals/docServlet?billId="
			: PropertyUtil.getProperty("BILL_ID_REPORT_URL_PART1").trim());
	/**
	 * <p>
	 * Specifies BillId Download Url Part2 <code>BILL_ID_REPORT_URL_PART2</code>
	 * for New Con File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 20-FEB-2016
	 */
	public static final String BILL_ID_REPORT_URL_PART2 = (null == PropertyUtil
			.getProperty("BILL_ID_REPORT_URL_PART2") ? "&page=billreport&DocType=DJBBill.pdf&caller=DJBCustomer.portal&token="
			: PropertyUtil.getProperty("BILL_ID_REPORT_URL_PART2").trim());
	/**
	 * <p>
	 * Ensuring valid booklet range.
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String BOOKLET_MSG = null != PropertyUtil
			.getProperty("BOOKLET_MSG") ? PropertyUtil
			.getProperty("BOOKLET_MSG") : "Please set valid booklet range.";
	/**
	 * <p>
	 * Ensuring valid booklet range.
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String BOOKLET_RANGE_MSG = null != PropertyUtil
			.getProperty("BOOKLET_RANGE_MSG") ? PropertyUtil
			.getProperty("BOOKLET_RANGE_MSG")
			: "Please set from range less than to range.";
	/**
	 * <p>
	 * Bypass fingerprint exception user role.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 27-05-2016 JTrac DJB-4464 and OpenProject#1151
	 */

	public static final String BYPASS_FP_EXCEPTION_ROLE = null != PropertyUtil
			.getProperty("BYPASS_FP_EXCEPTION_ROLE") ? PropertyUtil
			.getProperty("BYPASS_FP_EXCEPTION_ROLE") : "1,10";
	/**
	 * <p>
	 * Bypass ZRO Code For Mseva Audit Action Screen
	 * </p>
	 * 
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static final Object BYPASS_ZRO_CODE_FOR_AUDIT_ACTION = null != PropertyUtil
			.getProperty("BYPASS_ZRO_CODE_FOR_AUDIT_ACTION") ? PropertyUtil
			.getProperty("BYPASS_ZRO_CODE_FOR_AUDIT_ACTION") : "HQ";
	/**
	 * <p>
	 * Specifies bill cancel message as CANCEL THE BILL
	 * </p>
	 */
	public static final String CANCEL_BILL_MESSAGE = (null != PropertyUtil
			.getProperty("CANCEL_BILL_MESSAGE") ? PropertyUtil
			.getProperty("CANCEL_BILL_MESSAGE") : "CANCEL THE BILL");
	/**
	 * <p>
	 * file number range updation failure msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String CANNOT_DELETE_MSG = null != PropertyUtil
			.getProperty("CANNOT_DELETE_MSG") ? PropertyUtil
			.getProperty("CANNOT_DELETE_MSG")
			: "Could not delete as File numbers in the range have already been used.";

	/**
	 * <p>
	 * Specifies constant for parameter name equals to
	 * <code>CAT_CLASS_TYP</code> showing category class type for dev charge,
	 * JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String CAT_CLASS_TYP_PARAM = (null == PropertyUtil
			.getProperty("CAT_CLASS_TYP_PARAM") ? "CAT_CLASS_TYP"
			: PropertyUtil.getProperty("CAT_CLASS_TYP_PARAM").trim());

	/**
	 * <p>
	 * Specifies the CAT IIIA Catagory code as CATIIIA
	 * </p>
	 * 
	 * @author 830700
	 * @since 05-05-2016 JTrac DJB-4211
	 */
	public static final String CATIIIA_CATAGORY_CODE = null != PropertyUtil
			.getProperty("CATIIIA_CATAGORY_CODE") ? PropertyUtil
			.getProperty("CATIIIA_CATAGORY_CODE") : "CATIIIA";
			
	/**
	 * <p>
	 * Specifies the CAT IIIA Catagory code as CATIIIB
	 * </p>
	 * 
	 * @author 830700
	 * @since 05-05-2016 JTrac DJB-4211
	 */
	public static final String CATIIIB_CATAGORY_CODE = null != PropertyUtil
			.getProperty("CATIIIB_CATAGORY_CODE") ? PropertyUtil
			.getProperty("CATIIIB_CATAGORY_CODE") : "CATIIIB";

	/**
	 * <p>
	 * Specifies the CCB Connect Default Authcookie as V0VCOnNlbGZzZXJ2aWNl
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 * 
	 */
	public static final String CCB_CONNECT_DEFAULT_AUTHCOOKIE = null != PropertyUtil
			.getProperty("CCB_CONNECT_DEFAULT_AUTHCOOKIE") ? PropertyUtil
			.getProperty("CCB_CONNECT_DEFAULT_AUTHCOOKIE")
			: "V0VCOnNlbGZzZXJ2aWNl";

	/**
	 * <p>
	 * Value of static variable <code>CCB_MTR_RPLC_SERVICE_ERR_MSG</code> is
	 * retrieved from Property File
	 * <code>djb_portal_data_entry.properties</code> for the Generic error
	 * message to be shown in case of un handled errors when the service returns
	 * blank space as error message.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @since 04-02-2014
	 */
	public static final String CCB_MTR_RPLC_SERVICE_ERR_MSG = PropertyUtil
			.getProperty("CCB_MTR_RPLC_SERVICE_ERR_MSG");

	/**
	 * <p>
	 * Value of static variable
	 * <code>CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE</code> is retrieved from
	 * Property File <code>djb_portal_data_entry.properties</code> for the
	 * Generic error status code in case of un handled errors when the service
	 * returns blank space as error message.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @since 04-02-2014
	 */
	public static final String CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE = PropertyUtil
			.getProperty("CCB_MTR_RPLC_SERVICE_ERR_STATUS_CODE");
	/**
	 * <p>
	 * Specifies the CCB Service provider url
	 * </p>
	 */
	public static final String CCB_SERVICE_PROVIDER_URL = PropertyUtil
			.getProperty("CCMBserverAddress");
	/**
	 * <p>
	 * Value of APLY_SWR for Char Type Code used fro Drop Down Field 'Sewer
	 * Development Charge Applicability' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_APLY_SWR = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_APLY_SWR") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_APLY_SWR") : "APLY_SWR";

	/**
	 * <p>
	 * Value of APLY_WTR for Char Type Code used fro Drop Down Field 'Water
	 * Development Charge Applicability' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_APLY_WTR = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_APLY_WTR") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_APLY_WTR") : "APLY_WTR";

	/**
	 * <p>
	 * Value of CM_SW_CL for Char Type Code used fro Drop Down Field
	 * 'Development Charge Colony for Sewer' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_CM_SW_CL = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_SW_CL") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_SW_CL") : "CM_SW_CL";
	/**
	 * <p>
	 * Value of CM_UNUSE for Char Type Code used fro Drop Down Field
	 * 'Unauthorized usage detected at premise' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_CM_UNUSE = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_UNUSE") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_UNUSE") : "CM_UNUSE";

	/**
	 * <p>
	 * Value of CM_WT_CL for Char Type Code used fro Drop Down Field
	 * 'Development Charge Colony for Water' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_CM_WT_CL = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_WT_CL") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_CM_WT_CL") : "CM_WT_CL";
	/**
	 * <p>
	 * Value of DJB_EMP for Char Type Code used fro Drop Down Field 'DJB
	 * Employee Rebate Applicable' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_DJB_EMP = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_DJB_EMP") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_DJB_EMP") : "DJB_EMP";
	/**
	 * <p>
	 * Value of DOCVAR for Char Type Code used fro Drop Down Field 'If all the
	 * documents facilitated by user are verified' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_DOCVAR = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_DOCVAR") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_DOCVAR") : "DOCVAR";
	/**
	 * <p>
	 * Value of OSAPP for Char Type Code used fro Drop Down Field 'Is Occupier
	 * Security Applicable' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_OSAPP = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_OSAPP") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_OSAPP") : "OSAPP";

	/**
	 * <p>
	 * Value of PREFMOP for Char Type Code used fro Drop Down Field 'Preferred
	 * mode of payment' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_PREFMOP = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_PREFMOP") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_PREFMOP") : "PREFMOP";

	/**
	 * <p>
	 * Value of SEWFEAS for Char Type Code used fro Drop Down Field 'Sewerage
	 * Technical Feasibility' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_SEWFEAS = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_SEWFEAS") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_SEWFEAS") : "SEWFEAS";

	/**
	 * <p>
	 * Value of WATFEAS for Char Type Code used fro Drop Down Field 'Water
	 * Technical Feasibility' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_WATFEAS = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_WATFEAS") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_WATFEAS") : "WATFEAS";

	/**
	 * <p>
	 * Value of WCONSIZE for Char Type Code used fro Drop Down Field 'Size of
	 * Meter' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_WCONSIZE = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_WCONSIZE") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_WCONSIZE") : "WCONSIZE";

	/**
	 * <p>
	 * Value of YES_NO Char Type Code used fro Drop Down Field 'Is water being
	 * used in civil construction for the premises' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_YES_NO = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_YES_NO") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_YES_NO") : "YESNO";

	/**
	 * <p>
	 * Value of ZRO_LOC for Char Type Code used fro Drop Down Field 'ZRO
	 * Location' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-FEB-2016
	 */
	public static final String CHAR_TYPE_CD_ZRO_LOC = null != PropertyUtil
			.getProperty("CHAR_TYPE_CD_ZRO_LOC") ? PropertyUtil
			.getProperty("CHAR_TYPE_CD_ZRO_LOC") : "ZRO_LOC";

	/**
	 * <p>
	 * Char val of Self billing
	 * </p>
	 * 
	 * @author 735689
	 * @since 23-11-2016 as per JTrac DJB-4604.
	 * 
	 */
	public static final String CHAR_VAL_FOR_SELF_BILL = null != PropertyUtil
			.getProperty("CHAR_VAL_FOR_SELF_BILL") ? PropertyUtil
			.getProperty("CHAR_VAL_FOR_SELF_BILL") : "SELF_BIL";

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>CHARG_TYP</code>
	 * showing charge type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String CHARG_TYP_PARAM = (null == PropertyUtil
			.getProperty("CHARG_TYP_PARAM") ? "CHARG_TYP" : PropertyUtil
			.getProperty("CHARG_TYP_PARAM").trim());

	/**
	 * @author Diksha Mukherjee
	 * @since 22-04-2016 JTrac DJB-4211
	 */
	public static final String CHECK_AMOUNT_OPTIONAL_NEGATV_OR_DOT_REGEX_JS = (null != PropertyUtil
			.getProperty("CHECK_AMOUNT_OPTIONAL_NEGATV_OR_DOT_REGEX_JS") ? PropertyUtil
			.getProperty("CHECK_AMOUNT_OPTIONAL_NEGATV_OR_DOT_REGEX_JS")
			: "/(^(-?\\d{1,13}(\\.\\d{0,2})?)$)/");

	/**
	 *<p>
	 * Specifies Plotarea to be upto 2 decimal of places as per jTrac DJB-4418
	 *</p>
	 * 
	 * @author 1033846
	 * @since 23-03-16
	 */
	public static final String CHECK_DIGIT_WITH_DOT_OPTNL_REGEX = (null == PropertyUtil
			.getProperty("CHECK_DIGIT_WITH_DOT_OPTNL_REGEX") ? "/(^(\\d{1,13}(\\.\\d{0,2})?)$)/"
			: PropertyUtil.getProperty("CHECK_DIGIT_WITH_DOT_OPTNL_REGEX")
					.trim());

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows A-Z a-z 0-9-_(,).@:;' /" .
	 * This is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkAllowedSpecialCharRegex = PropertyUtil
			.getProperty("checkAllowedSpecialCharRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows Alphabets A-Z and a-z. This
	 * is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkAlphabetRegex = PropertyUtil
			.getProperty("checkAlphabetRegex");
	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows only Alphabets A-Z and a-z
	 * and Numbers 0-9. This is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkAlphaNumericRegex = PropertyUtil
			.getProperty("checkAlphaNumericRegex");
	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows 0-9 and ., .This is used by
	 * the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkAmountRegex = PropertyUtil
			.getProperty("checkAmountRegex");
	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows Valid Bill Cycle which
	 * includes A-Z a-z 0-9-(,%):& . This is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkBillCycleRegex = PropertyUtil
			.getProperty("checkBillCycleRegex");
	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only allows valid date. This is used by
	 * the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkDateRegex = PropertyUtil
			.getProperty("checkDateRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex validates a valid email id. This is used
	 * by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkEmailRegex = PropertyUtil
			.getProperty("checkEmailRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex allows only valid File Name/Path in
	 * (jpg|jpeg|gif|png|pdf|doc|docx|xls|xlsx|tiff|odt|) format . This is used
	 * by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkFileNameRegex = PropertyUtil
			.getProperty("checkFileNameRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex only valid HTTP Parameter Value. This is
	 * used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkHTTPParameterValueRegex = PropertyUtil
			.getProperty("checkHTTPParameterValueRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex allows only Alphabets a-zA-Z characters
	 * ,.-_ . This is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkNameRegex = PropertyUtil
			.getProperty("checkNameRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex allows only Numeric characters 0-9. This
	 * is used by the utility class
	 * <code>DJBFieldValidatorUtil.checkPresenceOfParticularChar</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkNumericRegex = PropertyUtil
			.getProperty("checkNumericRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex validates a URL with the pattern. This
	 * is used by the method <code>checkPresenceOfParticularChar</code> of the
	 * utility class <code>DJBFieldValidatorUtil</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkURLRegex = PropertyUtil
			.getProperty("checkURLRegex");

	/**
	 * <p>
	 * This reads the regular expression (Regex) for Allowed Characters written
	 * in the property file. This Regex allows only User Id format which
	 * contains only A-Z,a-z,_ and @. This is used by the method
	 * <code>checkPresenceOfParticularChar</code> of the utility class
	 * <code>DJBFieldValidatorUtil</code>.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 * @see DJBFieldValidatorUtil#checkPresenceOfParticularChar
	 * @see Pattern
	 * @see Matcher
	 */
	public static final String checkUserIdRegex = PropertyUtil
			.getProperty("checkUserIdRegex");

	/**
	 * <p>
	 * Specifies the Code for invalid data in CONS_PRE_BILL_STAT_LOOKUP as 15
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String CODE_FOR_INVALID_DATA_IN_CONS_PRE_BILL_STAT_LOOKUP = null != PropertyUtil
			.getProperty("CODE_FOR_INVALID_DATA_IN_CONS_PRE_BILL_STAT_LOOKUP") ? PropertyUtil
			.getProperty("CODE_FOR_INVALID_DATA_IN_CONS_PRE_BILL_STAT_LOOKUP")
			: "15";

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>COLONY_TYP</code>
	 * showing colony type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String COLONY_TYP_PARAM = (null == PropertyUtil
			.getProperty("COLONY_TYP_PARAM") ? "COLONY_TYP" : PropertyUtil
			.getProperty("COLONY_TYP_PARAM").trim());

	/**
	 * <p>
	 * Value of Consumer Status
	 * </p>
	 * 
	 * @author Sanjay Kumar Das(1033846)
	 * @since 08-30-2016 JTrac DJB-4553
	 */

	public static final String CONSUMER_STATUS_HHD = null != PropertyUtil
			.getProperty("CONSUMER_STATUS_HHD") ? PropertyUtil
			.getProperty("CONSUMER_STATUS_HHD") : "60";
	/**
	 * <p>
	 * Specifies the Consumption variation in audit status as ALL,COMPLETED,IN PROGRESS,PENDING
	 * </p>
	 * 
	 * @author 837535
	 */
	public static final String CONSUMPTION_VARIATION_AUDIT_STATUS = (null != PropertyUtil
			.getProperty("CONSUMPTION_VARIATION_AUDIT_STATUS") ? PropertyUtil
			.getProperty("CONSUMPTION_VARIATION_AUDIT_STATUS")
			: "ALL,COMPLETED,IN PROGRESS,PENDING");

	/**
	 * <p>
	 * Specifies msg for create mrd and demand transfer issue, JTrac DJB-4185.
	 * </p>
	 * 
	 * @author 837535(Aniket Chatterjee)
	 * @since 17-12-2015
	 */

	public static final String CRTMRD_DMND_TRNSFR_MSG = null != PropertyUtil
			.getProperty("CRTMRD_DMND_TRNSFR_MSG") ? PropertyUtil
			.getProperty("CRTMRD_DMND_TRNSFR_MSG")
			: "Sorry!! There Was An Error[Error Code : DJB-4185]. Please Contact System Administrator.";

	/**
	 * <p>
	 * value of Customer Class For CAT IIA
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String CUST_CL_CD_FOR_WWT = null != PropertyUtil
			.getProperty("CUST_CL_CD_FOR_WWT") ? PropertyUtil
			.getProperty("CUST_CL_CD_FOR_WWT") : "CAT IIA";

	/**
	 * <p>
	 * Specifies the Customer Colony which are not in used now, hence putting
	 * value X as the defaultu as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String CUST_COL_FOR_DEV_CHRG_NOT_USED = (null != PropertyUtil
			.getProperty("CUST_COL_FOR_DEV_CHRG_NOT_USED") ? PropertyUtil
			.getProperty("CUST_COL_FOR_DEV_CHRG_NOT_USED") : "X");

	/**
	 * <p>
	 * The Status code for Data Entry parked status which is 69.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 */
	public static int DATA_ENTRY_PARK_STATUS_CODE = 69;

	/**
	 * <p>
	 * Specifies ZRO in Download Url Part1 for Data Migration, JTrac DJB-4425
	 * and OpenProject-1217.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 01-06-2016
	 */
	public static final String DATA_MIGRATION_REPORT_URL_PART1 = (null == PropertyUtil
			.getProperty("DATA_MIGRATION_REPORT_URL_PART1") ? "http://10.248.136.62/DJBPortal/portals/docServlet?DATA_SOURCE="
			: PropertyUtil.getProperty("DATA_MIGRATION_REPORT_URL_PART1")
					.trim());
	/**
	 * <p>
	 * Specifies File Name Download Url Part2
	 * <code>BILL_ID_REPORT_URL_PART2</code> for Data Migration, JTrac DJB-4425
	 * and OpenProject-1217.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 01-06-2016
	 */
	public static final String DATA_MIGRATION_REPORT_URL_PART2 = (null == PropertyUtil
			.getProperty("DATA_MIGRATION_REPORT_URL_PART2") ? "&page=migrationReport&DocType=MigrationReport.pdf&caller=DJBCustomer.portal&FILE_NAME="
			: PropertyUtil.getProperty("DATA_MIGRATION_REPORT_URL_PART2")
					.trim());
	/**
	 * <p>
	 * Defining ZRO list for data migration via procedure.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 01-06-2016 JTrac DJB-4465
	 */

	public static final String DATA_MIGRATION_ZRO_LIST = null != PropertyUtil
			.getProperty("DATA_MIGRATION_ZRO_LIST") ? PropertyUtil
			.getProperty("DATA_MIGRATION_ZRO_LIST") : "DW,";

	/**
	 * <p>
	 * data saved successfully message
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String DATA_SAVED_MESSAGE = null != PropertyUtil
			.getProperty("DATA_SAVED_MESSAGE") ? PropertyUtil
			.getProperty("DATA_SAVED_MESSAGE")
			: "The range of file numbers will get assigned to the selected zones.";

	/**
	 * <p>
	 * data unsuccessfull message
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String DATA_UNSUCCESS_MESSAGE = null != PropertyUtil
			.getProperty("DATA_UNSUCCESS_MESSAGE") ? PropertyUtil
			.getProperty("DATA_UNSUCCESS_MESSAGE")
			: "Data could not be inserted, Plz try again.";

	/**
	 * <p>
	 * Specifies the database server IP address for CCBPROD, as per JTrac
	 * DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971) (Tata Consultancy Services)
	 * @since 02-JUN-2016
	 */
	public static final String DB_SERVER_IPADDR = (null != PropertyUtil
			.getProperty("DB_SERVER_IPADDR") ? PropertyUtil
			.getProperty("DB_SERVER_IPADDR") : "10.248.136.106");

	/**
	 * <p>
	 * Specifies the database server IP address for CCBPROD, as per JTrac
	 * DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author LOVELY (986018) (Tata Consultancy Services)
	 * @since 02-JUN-2016
	 */
	public static final String DB_SERVER_IPADDRR = (null != PropertyUtil
			.getProperty("DB_SERVER_IPADDRR") ? PropertyUtil
			.getProperty("DB_SERVER_IPADDRR") : "10.248.136.107");

	/**
	 * <p>
	 * Allowed DB Session couunt above which billing cannot be done.
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static int DB_SESSION_ALLOWED_CD = null != PropertyUtil
			.getProperty("DB_SESSION_ALLOWED_CD") ? Integer
			.parseInt(PropertyUtil.getProperty("DB_SESSION_ALLOWED_CD")) : 70;

	/**
	 * <p>
	 * Error Msg when DB session count exceeds a threshold limit.
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static String DB_SESSION_BUSY_ERROR_MSG = null != PropertyUtil
			.getProperty("DB_SESSION_BUSY_ERROR_MSG") ? (PropertyUtil
			.getProperty("DB_SESSION_BUSY_ERROR_MSG"))
			: "Please try after few minutes. Last Record was Saved Succesfully. But Bill Not Generated.";

	/**
	 * <p>
	 * Flag whether to check or not DB Session Count
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static String DB_SESSION_COUNT_CHECK_FLAG = null != PropertyUtil
			.getProperty("DB_SESSION_COUNT_CHECK_FLAG") ? (PropertyUtil
			.getProperty("DB_SESSION_COUNT_CHECK_FLAG")) : "Y";

	/**
	 * <p>
	 * DB Session Query to be checked.
	 * </p>
	 * 
	 * @author Suraj(1241359)
	 * @since 12th Sep 2017 as per JTRAC-> DJB-4735.
	 */
	public static String DB_SESSION_QUERY_TO_CHECK = null != PropertyUtil
			.getProperty("DB_SESSION_QUERY_TO_CHECK") ? (PropertyUtil
			.getProperty("DB_SESSION_QUERY_TO_CHECK"))
			: "UPDATE CI_SEQ SET SEQ_NBR = SEQ_NBR + 1 WHERE SEQ_NAME = :1";

	/**
	 * <p>
	 * Declaration alert message
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 06-06-2016
	 */

	public static final String DECLARATION_ALERT_MSG = null != PropertyUtil
			.getProperty("DECLARATION_ALERT_MSG") ? PropertyUtil
			.getProperty("DECLARATION_ALERT_MSG")
			: "Please Agree with Terms and Conditions.";

	/**
	 * <p>
	 * Declaration message for data upload screen.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 06-06-2016
	 */

	public static final String DECLARATION_MSG = null != PropertyUtil
			.getProperty("DECLARATION_MSG") ? PropertyUtil
			.getProperty("DECLARATION_MSG")
			: "I agree with all RMS - Terms and Conditions<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. I confirm that the uploaded file follows all the validations as mentioned in the Sample Data Migration sheet.";

	/**
	 * <P>
	 * Default Password set on the page. This is for Updating user Profile. If
	 * password is found to be equal to DEFAULT_PASSWORD value, the password
	 * will not be updated.
	 * </p>
	 */
	public static final String DEFAULT_PASSWORD = "Def@ultpassw0rd";

	/**
	 * <p>
	 * file number range delete success msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String DELETE_FILE_MSG = null != PropertyUtil
			.getProperty("DELETE_FILE_MSG") ? PropertyUtil
			.getProperty("DELETE_FILE_MSG")
			: "File number range deleted successfully.";

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>90</code> showing
	 * interval in days for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_DATE_INTERVAL_IN_DAYS = (null == PropertyUtil
			.getProperty("DEV_CHRG_DATE_INTERVAL_IN_DAYS") ? "90"
			: PropertyUtil.getProperty("DEV_CHRG_DATE_INTERVAL_IN_DAYS").trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>365</code> showing year
	 * interval in days for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_DATE_INTERVAL_IN_YEAR = (null == PropertyUtil
			.getProperty("DEV_CHRG_DATE_INTERVAL_IN_YEAR") ? "365"
			: PropertyUtil.getProperty("DEV_CHRG_DATE_INTERVAL_IN_YEAR").trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>F</code> showing flat
	 * rebate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_FLAT_REBATE_CD = (null == PropertyUtil
			.getProperty("DEV_CHRG_FLAT_REBATE_CD") ? "F" : PropertyUtil
			.getProperty("DEV_CHRG_FLAT_REBATE_CD").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>Flat</code> showing
	 * flat rebate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_FLAT_REBATE_PARAM = (null == PropertyUtil
			.getProperty("DEV_CHRG_FLAT_REBATE_PARAM") ? "Flat" : PropertyUtil
			.getProperty("DEV_CHRG_FLAT_REBATE_PARAM").trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>P</code> showing
	 * percentage rebate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_PERCENTAGE_REBATE_CD = (null == PropertyUtil
			.getProperty("DEV_CHRG_PERCENTAGE_REBATE_CD") ? "P" : PropertyUtil
			.getProperty("DEV_CHRG_PERCENTAGE_REBATE_CD").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>Percentage</code>
	 * showing percentage rebate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_PERCENTAGE_REBATE_PARAM = (null == PropertyUtil
			.getProperty("DEV_CHRG_PERCENTAGE_REBATE_PARAM") ? "Percentage"
			: PropertyUtil.getProperty("DEV_CHRG_PERCENTAGE_REBATE_PARAM")
					.trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>R</code> showing
	 * regular rate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_RATE_TYP_REGULAR_CD = (null == PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_REGULAR_CD") ? "R" : PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_REGULAR_CD").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>Regular</code>
	 * showing regular rate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_RATE_TYP_REGULAR_PARAM = (null == PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_REGULAR_PARAM") ? "Regular"
			: PropertyUtil.getProperty("DEV_CHRG_RATE_TYP_REGULAR_PARAM")
					.trim());
	/**
	 * <p>
	 * Specifies constant for code value equals to <code>S</code> showing scheme
	 * rate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_RATE_TYP_SCHEME_CD = (null == PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_SCHEME_CD") ? "S" : PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_SCHEME_CD").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>Scheme</code>
	 * showing scheme rate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_RATE_TYP_SCHEME_PARAM = (null == PropertyUtil
			.getProperty("DEV_CHRG_RATE_TYP_SCHEME_PARAM") ? "Scheme"
			: PropertyUtil.getProperty("DEV_CHRG_RATE_TYP_SCHEME_PARAM").trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>31/12/4000</code>
	 * showing default end date for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_ROW_DEFAULT_END_DATE = (null == PropertyUtil
			.getProperty("DEV_CHRG_ROW_DEFAULT_END_DATE") ? "31/12/4000"
			: PropertyUtil.getProperty("DEV_CHRG_ROW_DEFAULT_END_DATE").trim());

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>20/86400</code> showing
	 * 20sec. in days for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String DEV_CHRG_ROW_FREEZE_INTERVAL_MIN = (null == PropertyUtil
			.getProperty("DEV_CHRG_ROW_FREEZE_INTERVAL_MIN") ? "20/86400"
			: PropertyUtil.getProperty("DEV_CHRG_ROW_FREEZE_INTERVAL_MIN")
					.trim());
	/**
	 * <p>
	 * Specifies disable self billing message text as Dear Consumer,\n As per
	 * the audit findings, Self-Billing has been disabled for your KNO.
	 * </p>
	 */
	public static final String DISABLE_SELF_BILLING_MESSAGE_TEXT = (null != PropertyUtil
			.getProperty("DISABLE_SELF_BILLING_MESSAGE_TEXT") ? PropertyUtil
			.getProperty("DISABLE_SELF_BILLING_MESSAGE_TEXT")
			: "Dear Consumer,\n As per the audit findings, Self-Billing has been disabled for your KNO.");
	/**
	 * <p>
	 * Specifies disable self billing reason as CANCEL THE BILL AND DISABLE
	 * SELF-BILLING
	 * </p>
	 */
	public static final String DISABLE_SELF_BILLING_REASON = (null != PropertyUtil
			.getProperty("DISABLE_SELF_BILLING_REASON") ? PropertyUtil
			.getProperty("DISABLE_SELF_BILLING_REASON")
			: "CANCEL THE BILL AND DISABLE SELF-BILLING");
	/**
	 * <p>
	 * Message to be displayed if If Old SA Start date before 01 Jan 2010 or the
	 * consumer is a disconnected consumer.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 * 
	 */
	public static String DISCONNECTED_OR_SA_START_DATE_BEFORE_JAN_2010_ERROR_MSG = PropertyUtil
			.getProperty("DISCONNECTED_OR_SA_START_DATE_BEFORE_JAN_2010_ERROR_MSG");
	/**
	 * <p>
	 * This the name of the property file used for Data Entry Application. All
	 * constant parameters are placed in the property file.
	 * </p>
	 * 
	 * @see PropertyUtil
	 */
	public static final String DJB_PROP_FILE_NAME = "djb_portal_data_entry.properties";
	/**
	 * <p>
	 * This is the path of the property file location used for Data Entry
	 * Application.
	 * </p>
	 * 
	 * @see PropertyUtil
	 */
	public static final String DJB_PROP_FILE_PATH = "/home/user/djb_portal_properties";
	/**
	 * <p>
	 * Type of document to be uploded.
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String DOC_TYPE_RWH = null != PropertyUtil
			.getProperty("DOC_TYPE_RWH") ? PropertyUtil
			.getProperty("DOC_TYPE_RWH") : "Proof Of Identity";

	/**
	 * <p>
	 * Flag for authcookie used in Updation of Rocky Area/On Banks of Yamuna
	 * Screen . JTrac DJB-4537 Open Project id #1442
	 * </p>
	 * 
	 * @author 682667 (Rajib Hazarika)
	 * @since 29-08-2016
	 */

	public static final String DYNAMIC_AUTH_COOKIE_FOR_RAYB = null != PropertyUtil
			.getProperty("DYNAMIC_AUTH_COOKIE_FOR_RAYB") ? PropertyUtil
			.getProperty("DYNAMIC_AUTH_COOKIE_FOR_RAYB") : "N";
	/**
	 * <p>
	 * Flag for authcookie.
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String DYNAMIC_AUTH_COOKIE_FOR_RWH = null != PropertyUtil
			.getProperty("DYNAMIC_AUTH_COOKIE_FOR_RWH") ? PropertyUtil
			.getProperty("DYNAMIC_AUTH_COOKIE_FOR_RWH") : "N";
	/**
	 * <p>
	 * Specifies Empty Authkey as Empty Authkey.
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String EMPTY_AUTHKEY = null != PropertyUtil
			.getProperty("EMPTY_AUTHKEY") ? PropertyUtil
			.getProperty("EMPTY_AUTHKEY") : "Empty Authkey.";

	/**
	 * <p>
	 * Error message for Single Kno bill generation screen.
	 * </p>
	 * 
	 * @author 735689
	 * @since 23-11-2016 as per JTrac DJB-4604.
	 * 
	 */
	public static final String ERR_MSG_FOR_BILL_GEN = null != PropertyUtil
			.getProperty("ERR_MSG_FOR_BILL_GEN") ? PropertyUtil
			.getProperty("ERR_MSG_FOR_BILL_GEN")
			: "Either KNO is Invalid or You do not have security rights for this action. Please contact your System Administrator.";

	/**
	 * <p>
	 * Specifies flag for code value equals to <code>ERROR</code>, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String ERROR_FLG = "ERROR";

	/**
	 * <p>
	 * Values of status flag where "20" for error
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String FAIL_STATUS_FLG_RWH = "20";
	/**
	 * <p>
	 * Specifies the FALSE_KNO_VALUE used for New Conn KNO Generation as when
	 * KNO is not generated this value is returned from the CC&B service as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String FALSE_KNO_VALUE = (null != PropertyUtil
			.getProperty("FALSE_KNO_VALUE") ? PropertyUtil
			.getProperty("FALSE_KNO_VALUE") : "0000000000");
	/**
	 * <p>
	 * Specifies Max length for <code>FIELD_MAX_LEN_ARN_NO</code> for New Con
	 * File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 20-FEB-2016
	 */
	public static final String FIELD_MAX_LEN_ARN_NO = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_ARN_NO") ? "12" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_ARN_NO").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>CONTACT NO.</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_CONTACT_NO = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_CONTACT_NO") ? "10" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_CONTACT_NO").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>MAX LENGTH</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_EMAIL = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_EMAIL") ? "70" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_EMAIL").trim());

	/**
	 * <p>
	 * Specifies Max length for <code>FILE_NO</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_FILE_NO = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_FILE_NO") ? "12" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_FILE_NO").trim());

	/**
	 * <p>
	 * Specifies Max length for <code>FIRST NAME</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_FIRST_NAME = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_FIRST_NAME") ? "80" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_FIRST_NAME").trim());

	/**
	 * <p>
	 * Specifies Max length for <code>HOUSE NO</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_HOUSE_NO = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_HOUSE_NO") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_HOUSE_NO").trim());

	/**
	 * <p>
	 * Specifies Max length for <code>MIDDLE NAME</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_LAST_NAME = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_LAST_NAME") ? "80" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_LAST_NAME").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>MIDDLE NAME</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_MIDDLE_NAME = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_MIDDLE_NAME") ? "80" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_MIDDLE_NAME").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>PIN CODE</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_PIN_CODE = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_PIN_CODE") ? "6" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_PIN_CODE").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>ROAD NO</code> for New Con File Entry,
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_ROAD_NO = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_ROAD_NO") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_ROAD_NO").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>FIELD_MAX_LEN_SUBLOC2</code> for New Con
	 * File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_SUBCOLONY = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBCOLONY") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBCOLONY").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>FIELD_MAX_LEN_SUBLOC1</code> for New Con
	 * File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_SUBLOC1 = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBLOC1") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBLOC1").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>FIELD_MAX_LEN_SUBLOC2</code> for New Con
	 * File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_SUBLOC2 = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBLOC2") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_SUBLOC2").trim());
	/**
	 * <p>
	 * Specifies Max length for <code>FIELD_MAX_LEN_VILLAGE</code> for New Con
	 * File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FIELD_MAX_LEN_VILLAGE = (null == PropertyUtil
			.getProperty("FIELD_MAX_LEN_VILLAGE") ? "200" : PropertyUtil
			.getProperty("FIELD_MAX_LEN_VILLAGE").trim());

	/**
	 * <p>
	 * Specifies the max length for the field <code>No of Adult</code> in New
	 * Connection Screen, JTrac DJB-4195.
	 * </p>
	 * 
	 * @author 556885
	 * @since 13-10-2015
	 */
	public static final String FIELD_NO_OF_ADULT_MAX_LENGTH = (null == PropertyUtil
			.getProperty("FIELD_NO_OF_ADULT_MAX_LENGTH") ? "5" : PropertyUtil
			.getProperty("FIELD_NO_OF_ADULT_MAX_LENGTH").trim());
	/**
	 * <p>
	 * Specifies the max length for the field <code>No of Child</code> in New
	 * Connection Screen, JTrac DJB-4195.
	 * </p>
	 * 
	 * @author 556885
	 * @since 13-10-2015
	 */
	public static final String FIELD_NO_OF_CHILD_MAX_LENGTH = (null == PropertyUtil
			.getProperty("FIELD_NO_OF_CHILD_MAX_LENGTH") ? "5" : PropertyUtil
			.getProperty("FIELD_NO_OF_CHILD_MAX_LENGTH").trim());
	/**
	 * <p>
	 * Specifies the FILED_NOT_TO_BE_USED_VAL used for New Conn KNO Generation
	 * using user authCookie to call CCB Service or to use DEFAULT user's
	 * authCookie as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String FIELD_NOT_TO_BE_USED_VAL = (null != PropertyUtil
			.getProperty("FIELD_NOT_TO_BE_USED_VAL") ? PropertyUtil
			.getProperty("FIELD_NOT_TO_BE_USED_VAL") : "0");

	/**
	 * <p>
	 * Specifies code value equals to <code>FILE_ENTRY_INITIAL_STATUS</code> for
	 * New Connection File Entry, as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 21-JAN-2016
	 */
	public static final String FILE_ENTRY_INITIAL_STATUS = null != PropertyUtil
			.getProperty("FILE_ENTRY_INITIAL_STATUS") ? PropertyUtil
			.getProperty("FILE_ENTRY_INITIAL_STATUS") : "10";

	/**
	 * <p>
	 * failure message for wrong file extension .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String FILE_ERROR_MSG = null != PropertyUtil
			.getProperty("FILE_ERROR_MSG") ? PropertyUtil
			.getProperty("FILE_ERROR_MSG")
			: "Invalid file type. Please upload a file with xls extension type.";

	/**
	 * <p>
	 * file extention for data upload .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String FILE_EXTENSION = null != PropertyUtil
			.getProperty("FILE_EXTENSION") ? PropertyUtil
			.getProperty("FILE_EXTENSION") : "xls";

	/**
	 * <p>
	 * error message for wrong format of file during file uploading .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String FILE_FORMAT_MSG = null != PropertyUtil
			.getProperty("FILE_FORMAT_MSG") ? PropertyUtil
			.getProperty("FILE_FORMAT_MSG")
			: "Invalid file format,please retry.";

	/**
	 * <p>
	 * file
	 * </p>
	 * Message for in correct ZRO location tagged .
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String FILE_NBR_SELECT_MSG = null != PropertyUtil
			.getProperty("FILE_NBR_SELECT_MSG") ? PropertyUtil
			.getProperty("FILE_NBR_SELECT_MSG")
			: "Please enter file number range of ZRO location tagged with the user id  ";

	/**
	 * <p>
	 * Specifies for whether a file no. should be validated whether it is tagged
	 * to any Employee or not <code>FILE_NO_EMP_TAG_VALIDATION_FLG</code> for
	 * Quick New Con File Entry, JTrac ANDROMR-7.
	 * </p>
	 * 
	 * @author 682667(Rajib Hazarika)
	 * @since 16-SEP-2016
	 */
	public static final String FILE_NO_EMP_TAG_VALIDATION_FLG = (null == PropertyUtil
			.getProperty("FILE_NO_EMP_TAG_VALIDATION_FLG") ? "Y" : PropertyUtil
			.getProperty("FILE_NO_EMP_TAG_VALIDATION_FLG").trim());

	/**
	 * <p>
	 * Specifies Message used to show to user if the file is tagged to some
	 * specific Employee/Meter Reader <code>FILE_NO_TAGGED_TO_EMP_MSG</code> for
	 * Quick New Con File Entry, JTrac ANDROMR-7.
	 * </p>
	 * 
	 * @author 682667(Rajib Hazarika)
	 * @since 16-SEP-2016
	 */
	public static final String FILE_NO_TAGGED_TO_EMP_MSG = (null == PropertyUtil
			.getProperty("FILE_NO_TAGGED_TO_EMP_MSG") ? "File number has been assigned to the meter reader."
			: PropertyUtil.getProperty("FILE_NO_TAGGED_TO_EMP_MSG").trim());

	/**
	 * <p>
	 * Specifies for whether a file no. should be validated or not
	 * <code>FILE_NO_VALIDATION_FLG</code> for New Con File Entry, JTrac
	 * DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 25-JAN-2016
	 */
	public static final String FILE_NO_VALIDATION_FLG = (null == PropertyUtil
			.getProperty("FILE_NO_VALIDATION_FLG") ? "Y" : PropertyUtil
			.getProperty("FILE_NO_VALIDATION_FLG").trim());

	/**
	 * <p>
	 * File number not belong to a particular zro location msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String FILE_NOT_ZRO_MSG = null != PropertyUtil
			.getProperty("FILE_NOT_ZRO_MSG") ? PropertyUtil
			.getProperty("FILE_NOT_ZRO_MSG")
			: "Sorry, file numbers does not belong your ZRO location.";
	/**
	 * <p>
	 * Ensuring valid file number range allocation range.
	 * </p>
	 * 
	 * @author 986018
	 * @since 27-09-2016 as per jTrac DJB-4571,Openproject- #1492
	 */
	public static final String FILE_RANGE_MSG = null != PropertyUtil
			.getProperty("BOOKLET_RANGE_MSG") ? PropertyUtil
			.getProperty("BOOKLET_RANGE_MSG")
			: "Please set From range less than or equal to TO range.";
	/**
	 * <p>
	 * file size limit for uploaded data .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static String FILE_SIZE_LIMIT = null != PropertyUtil
			.getProperty("FILE_SIZE_LIMIT") ? (PropertyUtil
			.getProperty("FILE_SIZE_LIMIT")) : "2097152";
	/**
	 * <p>
	 * message for file size exceeding limit.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String FILE_SIZE_MSG = null != PropertyUtil
			.getProperty("FILE_SIZE_MSG") ? PropertyUtil
			.getProperty("FILE_SIZE_MSG")
			: "The file size is more than 2 MB, please retry with a lower file size.";
	/**
	 * <p>
	 * error message for file uploading .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String FILE_UPLOAD_MSG = null != PropertyUtil
			.getProperty("FILE_UPLOAD_MSG") ? PropertyUtil
			.getProperty("FILE_UPLOAD_MSG")
			: "Please select mandatory fields to proceed.";
	/**
	 * <p>
	 * Specifies ZRO Codes for <code>FILE_VALIDATION_BYPASSED_ZROCD</code> for
	 * New Con File Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 27-JAN-2016
	 */
	public static final String FILE_VALIDATION_BYPASSED_ZROCD = (null == PropertyUtil
			.getProperty("FILE_VALIDATION_BYPASSED_ZROCD") ? "HQ"
			: PropertyUtil.getProperty("FILE_VALIDATION_BYPASSED_ZROCD").trim());
	/**
	 * <p>
	 * Specifies Final Audit Action as CANCEL THE BILL,CANCEL THE BILL AND
	 * DISABLE SELF-BILLING,NO ACTION REQUIRED,WARNING/ALERT MESSAGE.
	 * </p>
	 */
	public static final String FINAL_AUDIT_ACTION = (null != PropertyUtil
			.getProperty("FINAL_AUDIT_ACTION") ? PropertyUtil
			.getProperty("FINAL_AUDIT_ACTION")
			: "CANCEL THE BILL,CANCEL THE BILL AND DISABLE SELF-BILLING,NO ACTION REQUIRED,WARNING/ALERT MESSAGE");
	/**
	 * <p>
	 * Specifies the FLAG_DYNAMIC_AUTHCOOKIE_FOR_BILL_GEN for using user
	 * authCookie to call CCB Service or to use DEFAULT user's authCookie as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static final String FLAG_DYNAMIC_AUTHCOOKIE_FOR_BILL_GEN = (null != PropertyUtil
			.getProperty("FLAG_DYNAMIC_AUTHCOOKIE_FOR_BILL_GEN") ? PropertyUtil
			.getProperty("FLAG_DYNAMIC_AUTHCOOKIE_FOR_BILL_GEN") : "N");
	/**
	 * <p>
	 * Specifies the FLAG_DYNAMIC_AUTHCOOKIE_FOR_KNO_GEN for using user'S
	 * authCookie to call CCB Service or to use DEFAULT user's authCookie as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static final String FLAG_DYNAMIC_AUTHCOOKIE_FOR_KNO_GEN = (null != PropertyUtil
			.getProperty("FLAG_DYNAMIC_AUTHCOOKIE_FOR_KNO_GEN") ? PropertyUtil
			.getProperty("FLAG_DYNAMIC_AUTHCOOKIE_FOR_KNO_GEN") : "N");

	/**
	 * <p>
	 * Specifies flag for code value equals to <code>N</code>, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String FLAG_N = "N";

	/**
	 * <p>
	 * Specifies flag for code value equals to <code>Y</code>, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String FLAG_Y = "Y";
	/**
	 * <p>
	 * Value of static variable <code>FTP_HOST_DIR</code> is retrieved from
	 * Property File <code>djb_portal_data_entry.properties</code> as path for
	 * host directory at FTP Server to save tar file for HHD Version Master
	 * Screen
	 * </p>
	 */
	public static final String FTP_HOST_DIR = PropertyUtil
			.getProperty("FTP_HOST_DIR");

	/**
	 * <p>
	 * Value of static variable <code>FTP_HOST_DIR</code> is retrieved from
	 * Property File <code>djb_portal_data_entry.properties</code> for extension
	 * of file being uploaded at FTP Server for HHD Version Master Screen
	 * </p>
	 */
	public static final String FTP_UPLOAD_FILE_EXT = PropertyUtil
			.getProperty("FTP_UPLOAD_FILE_EXT");

	/**
	 * <p>
	 * Message to be displayed if meter installation date in future.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 */
	public static String FUTURE_INSTALLATION_DATE_ERROR_MSG = PropertyUtil
			.getProperty("FUTURE_INSTALLATION_DATE_ERROR_MSG");
	/**
	 * <p>
	 * header row number used for validation.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */

	public static final int HEADER_ROW_NUMBER = null != PropertyUtil
			.getProperty("HEADER_ROW_NUMBER") ? Integer.parseInt(PropertyUtil
			.getProperty("HEADER_ROW_NUMBER")) : 1;

	/**
	 * <p>
	 * Value of Consumer Status
	 * </p>
	 * 
	 * @author Sanjay Kumar Das(1033846)
	 * @since 17-11-2016 JTrac DJB-4583 and Open Project Id-1595
	 */

	public static final String HIGH_LOW_STATUS = null != PropertyUtil
			.getProperty("HIGH_LOW_STATUS") ? PropertyUtil
			.getProperty("HIGH_LOW_STATUS") : "NO";

	/**
	 * <p>
	 * Specifies FLAG value for <code>HQ_VALIDATION_FLG</code> for New Con File
	 * Entry, JTrac DJB-4313.
	 * </p>
	 * 
	 * @author 682667
	 * @since 27-JAN-2016
	 */
	public static final String HQ_VALIDATION_FLG = (null == PropertyUtil
			.getProperty("HQ_VALIDATION_FLG") ? "N" : PropertyUtil.getProperty(
			"HQ_VALIDATION_FLG").trim());

	/**
	 * <p>
	 * Values of status flag where "10" for initial
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String INITIAL_STATUS_FLG_RWH = "10";
	/**
	 * <p>
	 * Specifies constant for parameter name equals to
	 * <code>INTRST_ELIGIBILIIY</code> showing interest eligibility for dev
	 * charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String INTRST_ELIGIBILIIY_PARAM = (null == PropertyUtil
			.getProperty("INTRST_ELIGIBILIIY_PARAM") ? "INTRST_ELIGIBILIIY"
			: PropertyUtil.getProperty("INTRST_ELIGIBILIIY_PARAM").trim());

	/**
	 * <p>
	 * Declaration alert message
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 06-06-2016
	 */

	public static final String INVALID_FILE_TYPE_MSG = null != PropertyUtil
			.getProperty("INVALID_FILE_TYPE_MSG") ? PropertyUtil
			.getProperty("INVALID_FILE_TYPE_MSG")
			: "Invalid file type.Please save the file as Microsoft Excel 97/2000/XP(.xls) and upload it again. ";

	/**
	 *<p>
	 * Specifies message for invalid user id of data entry users and all
	 * application users as per JTRac DJB-4464 and OpenProject#1151.
	 *</p>
	 * 
	 * @author Arnab Nandy
	 * @since 14-06-2016
	 */
	public static final String INVALID_USER_ID_MESSAGE = (null != PropertyUtil
			.getProperty("INVALID_USER_ID_MESSAGE") ? PropertyUtil
			.getProperty("INVALID_USER_ID_MESSAGE")
			: "The User ID provided is incorrect. Please Retry.");

	/**
	 * <p>
	 * Value of IS_SELF for cm_cons_pre_bill_proc table
	 * </p>
	 * 
	 * @author Sanjay Kumar Das(1033846)
	 * @since 02-08-2016 JTrac DJB-4653
	 */

	public static final String IS_SELF = null != PropertyUtil
			.getProperty("IS_SELF") ? PropertyUtil.getProperty("IS_SELF") : "Y";

	/**
	 * <p>
	 * Specifies JNDI_DS as JNDI_DS
	 * </p>
	 */
	public static final String JNDI_DS = null != PropertyUtil
			.getProperty("JNDI_DS") ? PropertyUtil.getProperty("JNDI_DS")
			.trim() : "JNDI_DS";

	/**
	 * <p>
	 * This is the New Connection File Entry Portal JNDI Data source used in the
	 * Application. For Portal database related activity this data source may be
	 * used. For some other specific data base activity, the specified data
	 * source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 27-JAN-2016
	 */
	public static final String JNDI_DS_MRD_SCHEDULE = null != PropertyUtil
			.getProperty("JNDI_DS_MRD_SCHEDULE") ? PropertyUtil.getProperty(
			"JNDI_DS_MRD_SCHEDULE").trim() : JNDI_DS;

	/**
	 * <p>
	 * This is the New Connection File Entry Portal JNDI Data source used in the
	 * Application. For Portal database related activity this data source may be
	 * used. For some other specific data base activity, the specified data
	 * source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 28-JAN-2016
	 */
	public static final String JNDI_DS_NEWCONN_KNO_GENERATION = null != PropertyUtil
			.getProperty("JNDI_DS_NEWCONN_KNO_GENERATION") ? PropertyUtil
			.getProperty("JNDI_DS_NEWCONN_KNO_GENERATION").trim() : JNDI_DS;

	/**
	 * <p>
	 * This is the New Connection File Entry Portal JNDI Data source used in the
	 * Application. For Portal database related activity this data source may be
	 * used. For some other specific data base activity, the specified data
	 * source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 28-JAN-2016
	 */
	// public static final String JNDI_DS_NEWCONN_PORTAL = null != PropertyUtil
	// .getProperty("DJBConstants") ? PropertyUtil.getProperty(
	// "DJBConstants").trim() : _JNDI_DS_PORTAL;

	public static final String JNDI_DS_NEWCONN_PORTAL = null != PropertyUtil
			.getProperty("JNDI_DS_NEWCONN_PORTAL") ? PropertyUtil.getProperty(
			"JNDI_DS_NEWCONN_PORTAL").trim() : _JNDI_DS_PORTAL;
	/**
	 * <p>
	 * This is the common JNDI PROVIDER used in the Application. For common
	 * database related activity this data source may be used. For some other
	 * specific data base activity, the specified data source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 09-06-2015
	 */
	public static final String JNDI_PROVIDER = PropertyUtil
			.getProperty("JNDI_PROVIDER");

	/**
	 * <p>
	 * This is the New Connection File Entry Portal JNDI Data source used in the
	 * Application. For Portal database related activity this data source may be
	 * used. For some other specific data base activity, the specified data
	 * source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 27-JAN-2016
	 */

	public static final String JNDI_PROVIDER_MRD_SCHEDULE = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_MRD_SCHEDULE") ? PropertyUtil
			.getProperty("JNDI_PROVIDER_MRD_SCHEDULE").trim() : JNDI_PROVIDER;

	/**
	 * <p>
	 * Specifies JNDI provider for new connection KNO generation as
	 * JNDI_PROVIDER
	 * </p>
	 */
	public static final String JNDI_PROVIDER_NEWCONN_KNO_GENERATION = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_NEWCONN_KNO_GENERATION") ? PropertyUtil
			.getProperty("JNDI_PROVIDER_NEWCONN_KNO_GENERATION").trim()
			: JNDI_PROVIDER;

	/**
	 * <p>
	 * Specifies JNDI provider new connection portal as JNDI_PROVIDER
	 * </p>
	 */
	public static final String JNDI_PROVIDER_NEWCONN_PORTAL = null != PropertyUtil
			.getProperty("JNDI_PROVIDER_NEWCONN_PORTAL") ? PropertyUtil
			.getProperty("JNDI_PROVIDER_NEWCONN_PORTAL").trim() : JNDI_PROVIDER;

	/**
	 * <p>
	 * Specifies Kno Audit Complete Status as COMPLETED
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_COMPLETE_STATUS = "COMPLETED";
	/**
	 * <p>
	 * Specifies Kno Audit Details row count as 4
	 * </p>
	 * 
	 * @author 830700
	 * @since 25-01-2015
	 * 
	 */
	public static final String KNO_AUDIT_DETAILS_ROW_COUNT = null != PropertyUtil
			.getProperty("KNO_AUDIT_DETAILS_ROW_COUNT") ? PropertyUtil
			.getProperty("KNO_AUDIT_DETAILS_ROW_COUNT") : "4";

	/**
	 * <p>
	 * Kno Audit InProgress Status as last audit status.
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_INPROGRESS_STATUS = "IN PROGRESS";

	/**
	 * <p>
	 * Kno Audit Meter Reader Assign to
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_MR_ASSIGNTO = "MTRREADER";

	/**
	 * <p>
	 * Last KNO Detail Audit NO Remark
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_NO_REMARK = "NO";

	/**
	 * <p>
	 * Last KNO Detail Audit Ok Remark
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_OK_REMARK = "OK";

	/**
	 * <p>
	 * Last KNO Audit serach hist count as 20
	 * </p>
	 * 
	 * @author 830700
	 * @since 25-01-2015
	 */
	public static final String KNO_AUDIT_SEARCH_HIST_COUNT = null != PropertyUtil
			.getProperty("KNO_AUDIT_SEARCH_HIST_COUNT") ? PropertyUtil
			.getProperty("KNO_AUDIT_SEARCH_HIST_COUNT") : "20";
	/**
	 * <p>
	 * Kno Audit self assign to
	 * </p>
	 * 
	 * @author 830700
	 */
	public static final String KNO_AUDIT_SELF_ASSIGNTO = "SELF";
	/**
	 * <p>
	 * Populates Colony Category at Create MRD page, JTrac DJB-4304.
	 * </p>
	 * 
	 * @author 1227971(Arnab Nandy)
	 * @since 06-01-2016
	 */
	public static final String LIST_COL_CAT = null != PropertyUtil
			.getProperty("LIST_COL_CAT") ? PropertyUtil
			.getProperty("LIST_COL_CAT") : "Please Select,A,B,C,D,E,F,G,H";
	/**
	 * <p>
	 * Value of Max Length of Lot Number Text field
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 01-28-2016 JTrac DJB-4326
	 */

	public static final String LOT_NO_MAX_LENGTH = null != PropertyUtil
			.getProperty("LOT_NO_MAX_LENGTH") ? PropertyUtil
			.getProperty("LOT_NO_MAX_LENGTH") : "14";

	/**
	 * <p>
	 * Ensuring mandatory fields not to be blank.
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String MANDATORY_FIELD_MSG = null != PropertyUtil
			.getProperty("MANDATORY_FIELD_MSG") ? PropertyUtil
			.getProperty("MANDATORY_FIELD_MSG")
			: "Mandatory Fields should not be blank.";
	/**
	 * <p>
	 * MAP_KEY value for data upload .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTrac DJB-4465 and OpenProject-918.
	 */

	public static final String MAP_KEY = null != PropertyUtil
			.getProperty("MAP_KEY") ? PropertyUtil.getProperty("MAP_KEY")
			: "TOTAL_VALID_COLUMN";
	/**
	 * <p>
	 * Specifies Map key is forbidden time as IS_FORBIDDEN_TIME
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String MAP_KEY_IS_FORBIDDEN_TIME = "IS_FORBIDDEN_TIME";
	/**
	 * <p>
	 * MAP_KEY value for data upload .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTrac DJB-4465 and OpenProject-918.
	 */

	public static final String MAP_KEY_VALUE = null != PropertyUtil
			.getProperty("MAP_KEY_VALUE") ? PropertyUtil
			.getProperty("MAP_KEY_VALUE") : "DATA_UPLOAD";

	/**
	 * <p>
	 * constant number of column to be read .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final int MAX_COLUMN_COUNT = null != PropertyUtil
			.getProperty("MAX_COLUMN_COUNT") ? Integer.parseInt(PropertyUtil
			.getProperty("MAX_COLUMN_COUNT")) : 43;

	/**
	 * <p>
	 * Value of MAX_INPROGRESS_COUNT for Mrd transfer Screen
	 * </p>
	 * 
	 * @author Madhuri Singh(735689)
	 * @since 20-Sep-2016 JTrac DJB-2200 & open Project id-1540
	 */

	public static final int MAX_INPROGRESS_COUNT = Integer
			.parseInt(null != PropertyUtil.getProperty("MAX_INPROGRESS_COUNT") ? PropertyUtil
					.getProperty("MAX_INPROGRESS_COUNT")
					: "5");

	/**
	 * <p>
	 * Atanu added the below constant to restrict the max number of borewells.
	 * As per jTrac DJB-4211
	 * </p>
	 * 
	 * @author 830700
	 * 
	 */
	public static final String MAX_NO_OF_BOREWELLS = null != PropertyUtil
			.getProperty("MAX_NO_OF_BOREWELLS") ? PropertyUtil
			.getProperty("MAX_NO_OF_BOREWELLS") : "10";

	/**
	 * <p>
	 * Message to be displayed if Meter Installation date before 01 Jan 2010.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 */
	public static String METER_INSTALLATION_DATE_BEFORE_JAN_2010_ERROR_MSG = PropertyUtil
			.getProperty("METER_INSTALLATION_DATE_BEFORE_JAN_2010_ERROR_MSG");

	/**
	 * <p>
	 * Message to be displayed if Meter Installation date is same as Old Meter
	 * Installation Date.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 */
	public static String METER_INSTALLATION_DATE_SAME_AS_OLD_METER_INSTALL_DATE_ERROR_MSG = PropertyUtil
			.getProperty("METER_INSTALLATION_DATE_SAME_AS_OLD_METER_INSTALL_DATE_ERROR_MSG");
	/**
	 * <p>
	 * Value of static variable <code>METER_READ_SOURCE</code> is retrieved from
	 * Property File <code>djb_portal_data_entry.properties</code> for
	 * CM_OnlineBillGenerationService CC&B service used for bill id Generation.
	 * </p>
	 * 
	 * @see djb_portal_data_entry.properties
	 */
	public static final String METER_READ_SOURCE = PropertyUtil
			.getProperty("METER_READ_SOURCE");
	/**
	 * <p>
	 * The Status code for meter replacement parked status which is 67.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 07-03-2014
	 */
	public static int METER_REPLACEMENT_PARK_STATUS_CODE = 67;
	/**
	 * <p>
	 * Ensuring that Mrd transfer has been initiated.
	 * </p>
	 * 
	 * @author 735689
	 * @since 19-10-2016 as per JTrac DJB-2200 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #1540
	 */
	public static final String MRD_TRANSFER_INITIATED_MSG = null != PropertyUtil
			.getProperty("MRD_TRANSFER_INITIATED_MSG") ? PropertyUtil
			.getProperty("MRD_TRANSFER_INITIATED_MSG")
			: "Successful: Mrd Transfer has been initiated for Zro Location.";

	/**
	 * <p>
	 * Ensuring that Mrd transfer has been in progress.
	 * </p>
	 * 
	 * @author 735689
	 * @since 19-10-2016 as per JTrac DJB-2200 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #1540
	 */
	public static final String MRD_TRANSFER_INPROGRESS_MSG = null != PropertyUtil
			.getProperty("MRD_TRANSFER_INPROGRESS_MSG") ? PropertyUtil
			.getProperty("MRD_TRANSFER_INPROGRESS_MSG")
			: "ERROR: Selected MRD Transfer process is in progress, Please try later.";
	/**
	 * <p>
	 * Ensuring that Mandatory inputs must be selected.
	 * </p>
	 * 
	 * @author 735689
	 * @since 19-10-2016 as per JTrac DJB-2200 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #1540
	 */
	public static final String MRD_TRANSFER_MANDATORY_MSG = null != PropertyUtil
			.getProperty("MRD_TRANSFER_MANDATORY_MSG") ? PropertyUtil
			.getProperty("MRD_TRANSFER_MANDATORY_MSG")
			: "ERROR: Please Select The Mandatory Fields.";
	/**
	 * <p>
	 * Ensuring that Mrd transfer has been done successfuly.
	 * </p>
	 * 
	 * @author 735689
	 * @since 19-10-2016 as per JTrac DJB-2200 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #1540
	 */
	public static final String MRD_TRANSFER_SUCCESS = null != PropertyUtil
			.getProperty("MRD_TRANSFER_SUCCESS") ? PropertyUtil
			.getProperty("MRD_TRANSFER_SUCCESS")
			: "The MRD has been transferred successfully to ZRO location ";

	/**
	 * <p>
	 * message for markey tagging
	 * </p>
	 * 
	 * @author Lovely 986018
	 * @since 07-06-2016
	 */
	public static final String MRKEY_TAGGED_MSG = null != PropertyUtil
			.getProperty("MRKEY_TAGGED_MSG") ? PropertyUtil
			.getProperty("MRKEY_TAGGED_MSG")
			: "Sorry this mrkey is not tagged to You.Try with another mrkey.";
	/**
	 * <p>
	 * Specifies param name for code value equals to
	 * <code>NEW_CONN_TYPE_OF_APP_VALUES</code> for New Connection File Entry,
	 * as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-JAN-2015
	 */
	public static final String NEW_CONN_TYPE_OF_APP_VALUES = null != PropertyUtil
			.getProperty("NEW_CONN_TYPE_OF_APP_VALUES") ? PropertyUtil
			.getProperty("NEW_CONN_TYPE_OF_APP_VALUES")
			: "PERMCONN:Water Connection,SEWCONN:Sewerage Connection,WATERSEW:Water and Sewerage Connection,REGULARIZATION:Regularization";

	/** <p>
	 * Specifies param name for code value equals to
	 * <code>NON_SATISFACTORY_REASON</code>
	 * </p>
	 * @author 830700
	 * @since 09-03-2016
	 */
	public static final String NON_SATISFACTORY_REASON = (null != PropertyUtil
			.getProperty("NON_SATISFACTORY_REASON") ? PropertyUtil
			.getProperty("NON_SATISFACTORY_REASON")
			: "ACTUAL METER READING VARIES PROPORTIONALLY,ADDITIONAL UNAUTHORIZED CONNECTION DETECTED,FUNCTIONAL METER NOT FOUND,METER BY-PASSES AND DIRECT WITHDRAWL OF WATER,OTHERS");

	/**
	 * <p>
	 * Validating other than numbers
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String NUMBER_ALLOWED_MSG = null != PropertyUtil
			.getProperty("NUMBER_ALLOWED_MSG") ? PropertyUtil
			.getProperty("NUMBER_ALLOWED_MSG") : "Only numbers are allowed.";

	/**
	 * <p>
	 * Value of to be set to enforce user to Reset his own password.
	 * </p>
	 */
	public static final Object OB_PASSWORD_CHANGE_FLAG_VALUE = "1";

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>No</code>, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String PARAM_NO = "No";

	/**
	 * <p>
	 * Specifies constant for code value equals to <code>Yes</code>, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String PARAM_YES = "Yes";

	/**
	 * <p>
	 * Specifies param name for code value equals to
	 * <code>PROOF_OF_IDN_DOC_TYPE</code> for New Connection File Entry, as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-JAN-2015
	 */
	public static final String PROOF_OF_ID_DOC_TYPE = null != PropertyUtil
			.getProperty("PROOF_OF_ID_DOC_TYPE") ? PropertyUtil
			.getProperty("PROOF_OF_ID_DOC_TYPE") : "POID";

	/**
	 * <p>
	 * Specifies param name for code value equals to
	 * <code>PROOF_OF_RES_DOC_TYPE</code> for New Connection File Entry, as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-JAN-2015
	 */
	public static final String PROOF_OF_RES_DOC_TYPE = null != PropertyUtil
			.getProperty("PROOF_OF_RES_DOC_TYPE") ? PropertyUtil
			.getProperty("PROOF_OF_RES_DOC_TYPE") : "POR";

	/**
	 * <p>
	 * Specifies param name for code value equals to
	 * <code>PROPERTY_OWNERSHIP_DOC_TYPE</code> for New Connection File Entry,
	 * as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 13-JAN-2015
	 */
	public static final String PROPERTY_OWNERSHIP_DOC_TYPE = null != PropertyUtil
			.getProperty("PROPERTY_OWNERSHIP_DOC_TYPE") ? PropertyUtil
			.getProperty("PROPERTY_OWNERSHIP_DOC_TYPE") : "POW";

	/**
	 * <p>
	 * value of Rain Water Characteristics Type
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */
	public static final String RAIN_WAT_CHAR_TYPE = null != PropertyUtil
			.getProperty("RAIN_WAT_CHAR_TYPE") ? PropertyUtil
			.getProperty("RAIN_WAT_CHAR_TYPE") : "WHWWR";

	public static final String RANGE_ABOVE_200_CONSUMPTION = (null != PropertyUtil
			.getProperty("RANGE_ABOVE_200_CONSUMPTION") ? PropertyUtil
			.getProperty("RANGE_ABOVE_200_CONSUMPTION") : "ABOVE 200");

	/**
	 * Loading the value of % Variation from Annual Average Consumption of
	 * Consumption Variation Audit page
	 * 
	 * @author 830700
	 * @since 18-01-2016
	 */
	public static final String RANGE_ANNUAL_AVERAGE_CONSUMPTION = (null != PropertyUtil
			.getProperty("RANGE_ANNUAL_AVERAGE_CONSUMPTION") ? PropertyUtil
			.getProperty("RANGE_ANNUAL_AVERAGE_CONSUMPTION")
			: "0-50,50-75,75-100,100-150,150-200,ABOVE 200");

	/**
	 * <p>
	 * Specifies param name for code value equals to
	 * <code>RANGE_VARIATION_PREVIOUS_ROUND</code>
	 * </p>
	 */
	public static final String RANGE_VARIATION_PREVIOUS_ROUND = (null != PropertyUtil
			.getProperty("RANGE_VARIATION_PREVIOUS_ROUND") ? PropertyUtil
			.getProperty("RANGE_VARIATION_PREVIOUS_ROUND")
			: "0-50,50-75,75-100,100-150,150-200,ABOVE 200");

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>RATE_TYP</code>
	 * showing rate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String RATE_TYP_PARAM = (null == PropertyUtil
			.getProperty("RATE_TYP_PARAM") ? "RATE_TYP" : PropertyUtil
			.getProperty("RATE_TYP_PARAM").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to
	 * <code>REBATE_ELIGIBILIIY</code> showing rebate eligibility for dev
	 * charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String REBATE_ELIGIBILIIY_PARAM = (null == PropertyUtil
			.getProperty("REBATE_ELIGIBILIIY_PARAM") ? "REBATE_ELIGIBILIIY"
			: PropertyUtil.getProperty("REBATE_ELIGIBILIIY_PARAM").trim());

	/**
	 * <p>
	 * Specifies constant for parameter name equals to <code>REBATE_TYP</code>
	 * showing rebate type for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String REBATE_TYP_PARAM = (null == PropertyUtil
			.getProperty("REBATE_TYP_PARAM") ? "REBATE_TYP" : PropertyUtil
			.getProperty("REBATE_TYP_PARAM").trim());

	/**
	 * <p>
	 * Specifies the regex expression which allows alphabets, numbers & space
	 * only, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String REGEX_ALPHANUMERIC_WITH_SPACE = (null == PropertyUtil
			.getProperty("REGEX_ALPHANUMERIC_WITH_SPACE") ? "" : PropertyUtil
			.getProperty("REGEX_ALPHANUMERIC_WITH_SPACE").trim());

	/**
	 * <p>
	 * This is the New Connection File Entry Portal JNDI Data source used in the
	 * Application. For Portal database related activity this data source may be
	 * used. For some other specific data base activity, the specified data
	 * source should be used.
	 * <p>
	 * 
	 * @see application.properties
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 28-JAN-2016
	 */
	public static final String REGEX_EMAIL = null != PropertyUtil
			.getProperty("checkEmailRegex") ? PropertyUtil.getProperty(
			"checkEmailRegex").trim() : "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.]+$";

	/**
	 * <p>
	 * Specifies the regex expression which allows numbers along with 2 digits
	 * optional after the decimal point.Decimal point is also optional, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String REGEX_NUMERIC_WITH_DOT = (null == PropertyUtil
			.getProperty("REGEX_NUMERIC_WITH_DOT") ? "" : PropertyUtil
			.getProperty("REGEX_NUMERIC_WITH_DOT").trim());

	/**
	 * <p>
	 * To restrict fingerprint in time of registration for user roles.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 27-05-2016 JTrac DJB-4464 and OpenProject#1151
	 */

	public static final String RESTRICT_REG_FP_FOR_USER_ROLE = null != PropertyUtil
			.getProperty("RESTRICT_REG_FP_FOR_USER_ROLE") ? PropertyUtil
			.getProperty("RESTRICT_REG_FP_FOR_USER_ROLE") : "10";

	/**
	 * <p>
	 * value of Characteristics Type For Rocky Area/On Bank of River Yamuna
	 * </p>
	 * 
	 * JTrac ID: DJB-4537, Open project Id#1442
	 * 
	 * @author 682667 (Rajib Hazarika)
	 * @since 29-08-2016
	 */
	public static final String ROCKY_YAMUNA_CHAR_TYPE = null != PropertyUtil
			.getProperty("ROCKY_YAMUNA_CHAR_TYPE") ? PropertyUtil
			.getProperty("ROCKY_YAMUNA_CHAR_TYPE") : "RAYB";

	/**
	 * <p>
	 * Specifies the rows exempt amr excel as 6
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String ROWS_EXEMPT_AMR_EXCEL = null != PropertyUtil
			.getProperty("ROWS_EXEMPT_AMR_EXCEL") ? PropertyUtil
			.getProperty("ROWS_EXEMPT_AMR_EXCEL") : "6";
	/**
	 * <p>
	 * constant number of rows to exempt during parsing .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 16th may 2015 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final int ROWS_TO_EXEMPT = null != PropertyUtil
			.getProperty("ROWS_TO_EXEMPT") ? Integer.parseInt(PropertyUtil
			.getProperty("ROWS_TO_EXEMPT")) : 1;

	/**
	 * <p>
	 * Specifies the salt key
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String SALT_KEY = PropertyUtil.getProperty("SALT_KEY");

	/**
	 * <p>
	 * Specifies the salt key used for encrypting bill id which is getting
	 * downloading,as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 16-FEB-2016
	 */
	public static final String SALT_PARAM_KEY = (null == PropertyUtil
			.getProperty("SALT_PARAM_KEY") ? "DGHUK13456VHJ" : PropertyUtil
			.getProperty("SALT_PARAM_KEY").trim());
	/**
	 * <p>
	 * Specifies the satisfactory reason as VARIATION DUE TO
	 * FUNCTIONS/OCCASIONS,VARIATIONS DUE TO SEASONAL CHANGES,OTHERS
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 * 
	 */
	public static final String SATISFACTORY_REASON = (null != PropertyUtil
			.getProperty("SATISFACTORY_REASON") ? PropertyUtil
			.getProperty("SATISFACTORY_REASON")
			: "VARIATION DUE TO FUNCTIONS/OCCASIONS,VARIATIONS DUE TO SEASONAL CHANGES,OTHERS");

	/**
	 * <p>
	 * Value of Screen ID for Mrd transfer Screen
	 * </p>
	 * 
	 * @author Madhuri Singh(735689)
	 * @since 20-Sep-2016 JTrac DJB-2200 & open Project id-1540
	 */
	public static final String SCREEN_ID_MRD_TRANSFER = null != PropertyUtil
			.getProperty("SCREEN_ID_MRD_TRANSFER") ? PropertyUtil
			.getProperty("SCREEN_ID_MRD_TRANSFER") : "52";
	/**
	 * Loading the value of audit status drop down field in the search table of
	 * image audit page
	 * 
	 * @author 830700
	 * @since 11-01-2016
	 * 
	 */
	public static final String SEARCH_AUDIT_STATUS = (null != PropertyUtil
			.getProperty("SEARCH_AUDIT_STATUS") ? PropertyUtil
			.getProperty("SEARCH_AUDIT_STATUS") : "ALL,AUDITED,PENDING");
	/**
	 * Loading the default value of audit status drop down field in the search
	 * table of image audit page
	 * 
	 * @author 830700
	 * @since 11-01-2016
	 */
	public static final String SEARCH_AUDIT_STATUS_DEFAULT = (null != PropertyUtil
			.getProperty("SEARCH_AUDIT_STATUS_DEFAULT") ? PropertyUtil
			.getProperty("SEARCH_AUDIT_STATUS_DEFAULT") : "PENDING");
	/**
	 * <p>
	 * Status code to check self billing is enabled or disabled.
	 * </p>
	 * 
	 * @author Madhuri(735689)
	 * @since 23rd Nov 2016 JTRAC-> DJB-4604.
	 */
	public static String SELF_BILLING_STATUS_CD = null != PropertyUtil
			.getProperty("SELF_BILLING_STATUS_CD") ? (PropertyUtil
			.getProperty("SELF_BILLING_STATUS_CD")) : "Y";

	/**
	 * <p>
	 * Value of search criteria' seperator
	 * </p>
	 * 
	 * @author Madhuri Singh (735689)
	 * @since 08-11-2016 Jtrac id -4538
	 */
	public static final String SEPERATOR_MRD_SEARCH_CRITERIA = null != PropertyUtil
			.getProperty("SEPERATOR_MRD_SEARCH_CRITERIA") ? PropertyUtil
			.getProperty("SEPERATOR_MRD_SEARCH_CRITERIA") : "-";

	/**
	 * <p>
	 * Specifies the server node value from which the request has been received
	 * from for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String SERVER_NODE = (null == PropertyUtil
			.getProperty("SERVER_NODE") ? "" : PropertyUtil.getProperty(
			"SERVER_NODE").trim());

	/**
	 * <p>
	 * value of service cycle which is being updated in CI_SP table of database
	 * while Renumbering Route Sequences.
	 * </p>
	 */
	public static final String SERVICE_CYCLE = PropertyUtil
			.getProperty("SERVICE_CYCLE");

	/**
	 * <p>
	 * value of service route which is being updated in CI_SP table of database
	 * while Renumbering Route Sequences
	 * </p>
	 */
	public static final String SERVICE_ROUTE = PropertyUtil
			.getProperty("SERVICE_ROUTE");

	/**
	 * <p>
	 * Specifies the SEW_DEV_CHRG_COL_NOTIFYDT_NA used for New Conn KNO
	 * Generation field Notification Date Water Development Charges NA as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String SEW_DEV_CHRG_COL_NOTIFYDT_NA = (null != PropertyUtil
			.getProperty("SEW_DEV_CHRG_COL_NOTIFYDT_NA") ? PropertyUtil
			.getProperty("SEW_DEV_CHRG_COL_NOTIFYDT_NA") : "01-JAN-0001");

	/**
	 * <p>
	 * Specifies the sms sent failure response as FAILURE
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 * 
	 */
	public static final String SMS_SENT_FAILURE_RESPONSE = null != PropertyUtil
			.getProperty("SMS_SENT_FAILURE_RESPONSE") ? PropertyUtil
			.getProperty("SMS_SENT_FAILURE_RESPONSE") : "FAILURE";
	/**
	 * <p>
	 * Specifies the sms sent success response as SUCCESS
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static final String SMS_SENT_SUCCESS_RESPONSE = null != PropertyUtil
			.getProperty("SMS_SENT_SUCCESS_RESPONSE") ? PropertyUtil
			.getProperty("SMS_SENT_SUCCESS_RESPONSE") : "SUCCESS";
	/**
	 * <p>
	 * Specifies the SOAP action as
	 * http://meterread.web.service.rms.djb.tcs.com/meterReadUpload
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String SOAP_ACTION = null != PropertyUtil
			.getProperty("SOAP_ACTION") ? PropertyUtil
			.getProperty("SOAP_ACTION")
			: "http://meterread.web.service.rms.djb.tcs.com/meterReadUpload";
	/**
	 * <p>
	 * Status code of online users .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 24th aug 2016 JTRAC-DJB-4541 and OpenProject 1443.
	 */
	public static String STATUS_CD = null != PropertyUtil
			.getProperty("STATUS_CD") ? (PropertyUtil.getProperty("STATUS_CD"))
			: "70";
	/**
	 * <p>
	 * Specifies the STATUS_CD_FOR_NEW_CON_ACCT_GEN used for New Conn Bill
	 * generation as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String STATUS_CD_FOR_NEW_CON_ACCT_GEN = (null != PropertyUtil
			.getProperty("STATUS_CD_FOR_NEW_CON_ACCT_GEN") ? PropertyUtil
			.getProperty("STATUS_CD_FOR_NEW_CON_ACCT_GEN") : "80");

	/**
	 * <p>
	 * Specifies the STATUS_CD_FOR_NEW_CON_BILL_GEN used for New Conn Bill
	 * generation as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String STATUS_CD_FOR_NEW_CON_BILL_GEN = (null != PropertyUtil
			.getProperty("STATUS_CD_FOR_NEW_CON_BILL_GEN") ? PropertyUtil
			.getProperty("STATUS_CD_FOR_NEW_CON_BILL_GEN") : "90");
	/**
	 * <p>
	 * Value of STATUS_CD for File Entry Status 'Received for KNO/Bill
	 * generation' as per JTrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (682667)
	 * @since 11-FEB-2016
	 */
	public static final String STATUS_CD_KNO_BILL_GENERATION = null != PropertyUtil
			.getProperty("STATUS_CD_KNO_BILL_GENERATION") ? PropertyUtil
			.getProperty("STATUS_CD_KNO_BILL_GENERATION") : "40";

	/**
	 * <p>
	 * Specifies the Status failed code as F
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 * 
	 */
	public static final String STATUS_FAILED_CODE = "F";
	/**
	 * <p>
	 * Specifies the status success code as S
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static final String STATUS_SUCCESS_CODE = "S";
	/**
	 * <p>
	 * Values of status flag where "30" for final
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */
	public static final String SUCCESS_STATUS_FLG_RWH = "30";
	/**
	 * <p>
	 * Specifies the suggested audit action as MARK BILL FOR CANCELLATION,MARK
	 * BILL FOR CANCELLATION AND DISABLE SELF-BILLING,NO ACTION REQUIRED,TO BE
	 * DECIDED BY CONCERNED ZRO/DD/JD,WARNING/ALERT MESSAGE,WARNING NOTICE
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static final String SUGGESTED_AUDIT_ACTION = (null != PropertyUtil
			.getProperty("SUGGESTED_AUDIT_ACTION") ? PropertyUtil
			.getProperty("SUGGESTED_AUDIT_ACTION")
			: "MARK BILL FOR CANCELLATION,MARK BILL FOR CANCELLATION AND DISABLE SELF-BILLING,NO ACTION REQUIRED,TO BE DECIDED BY CONCERNED ZRO/DD/JD,WARNING/ALERT MESSAGE,WARNING NOTICE");
	/**
	 * <p>
	 * Specifies the TO DO FOR NEW CON FORM which are not in used now, hence
	 * putting EMPTY value as the default as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String TO_DO_FOR_NEW_CON_FORM = (null != PropertyUtil
			.getProperty("TO_DO_FOR_NEW_CON_FORM") ? PropertyUtil
			.getProperty("TO_DO_FOR_NEW_CON_FORM") : "");
	/**
	 * <p>
	 * value of UCM Document.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTrac DJB-4465 and OpenProject-918.
	 */

	public static final String UCM_DOCUMENT = null != PropertyUtil
			.getProperty("UCM_DOCUMENT") ? PropertyUtil
			.getProperty("UCM_DOCUMENT") : "UCMdocumentUpload";

	/**
	 * <p>
	 * Value of Consumer Status
	 * </p>
	 * 
	 * @author Sanjay Kumar Das(1033846)
	 * @since 08-31-2016 JTrac DJB-4547
	 */
	public static final String UCM_LOAD_BALANCER_PATH = PropertyUtil
			.getProperty("UCM_LOAD_BALANCER_PATH");
	/**
	 * <p>
	 * value of UCM Path
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String UCM_UPLOAD = null != PropertyUtil
			.getProperty("UCM_UPLOAD") ? PropertyUtil.getProperty("UCM_UPLOAD")
			: "";
	/**
	 * <p>
	 * file number range updation failure msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String UPDATE_FAIL_MSG = null != PropertyUtil
			.getProperty("UPDATE_FAIL_MSG") ? PropertyUtil
			.getProperty("UPDATE_FAIL_MSG")
			: "There was some problem in processing so could not update.";
	/**
	 * Gatting the IMAGE_AUDIT_STATUS values from property file.
	 * 
	 * @author 830700
	 * @since 11-01-2016
	 */
	public static final String UPDATE_STATUS_LIST = (null != PropertyUtil
			.getProperty("IMAGE_AUDIT_STATUS") ? PropertyUtil
			.getProperty("IMAGE_AUDIT_STATUS") : "FAIL,PASS");
	/**
	 * <p>
	 * file number range updation success msg.
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String UPDATE_SUCCESS_MSG = null != PropertyUtil
			.getProperty("UPDATE_SUCCESS_MSG") ? PropertyUtil
			.getProperty("UPDATE_SUCCESS_MSG")
			: "File number range updated successfully.";
	/**
	 * <p>
	 * failure message for data uploded .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTRAC-DJB-4465 and OpenProject-918.
	 */
	public static final String UPLOAD_FAILURE_MSG = null != PropertyUtil
			.getProperty("UPLOAD_FAILURE_MSG") ? PropertyUtil
			.getProperty("UPLOAD_FAILURE_MSG")
			: "The file could not be uploaded. Please retry.";
	/**
	 * <p>
	 * success message for data uploded .
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 05-16-2016 JTrac DJB-4465 and OpenProject-918.
	 */

	public static final String UPLOAD_SUCCESS_MSG = null != PropertyUtil
			.getProperty("UPLOAD_SUCCESS_MSG") ? PropertyUtil
			.getProperty("UPLOAD_SUCCESS_MSG")
			: "File uploaded successfully. Process initiated successfully.";
	/**
	 * <p>
	 * Used file number used msg for file entry
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String USED_FILE_MSG = null != PropertyUtil
			.getProperty("USED_FILE_MSG") ? PropertyUtil
			.getProperty("USED_FILE_MSG")
			: "Sorry, entry for the set of file numbers have already been made.";
	/**
	 *<p>
	 * Specifies Regular expression for user id of data entry users and all
	 * application users as per JTRac DJB-4464 and OpenProject#1151.
	 *</p>
	 * 
	 * @author Arnab Nandy
	 * @since 14-06-2016
	 */
	public static final String USER_ID_REGEX_JS = (null != PropertyUtil
			.getProperty("USER_ID_REGEX_JS") ? PropertyUtil
			.getProperty("USER_ID_REGEX_JS") : "^[a-zA-Z0-9_.]{6,20}$");
	/**
	 *<p>
	 * Specifies Version consideration while creating auth key flag
	 *</p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String VERSION_CONSIDERATION_WHILE_CREATING_AUTH_KEY_FLAG = PropertyUtil
			.getProperty("VERSION_CONSIDERATION_WHILE_CREATING_AUTH_KEY_FLAG");
	/**
	 * <p>
	 * Specifies warning alert message as WARNING/ALERT MESSAGE
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static final String WARNING_ALERT_MESSAGE = (null != PropertyUtil
			.getProperty("WARNING_ALERT_MESSAGE") ? PropertyUtil
			.getProperty("WARNING_ALERT_MESSAGE") : "WARNING/ALERT MESSAGE");

	/**
	 * <p>
	 * Specifies warning alert message text as ALERT: Dear Consumer, the bill
	 * generated by you has some discrepencies, please provide correct meter
	 * reading from the next bill round.
	 * </p>
	 * 
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static final String WARNING_ALERT_MESSAGE_TEXT = (null != PropertyUtil
			.getProperty("WARNING_ALERT_MESSAGE_TEXT") ? PropertyUtil
			.getProperty("WARNING_ALERT_MESSAGE_TEXT")
			: "ALERT: Dear Consumer, the bill generated by you has some discrepencies, please provide correct meter reading from the next bill round.");

	/**
	 * <p>
	 * value of Waste Water Characteristics Type
	 * </p>
	 * 
	 * @author 837535
	 * @since 09-24-2015 JTrac DJB-4037
	 */

	public static final String WASTE_WAT_CHAR_TYPE = null != PropertyUtil
			.getProperty("WASTE_WAT_CHAR_TYPE") ? PropertyUtil
			.getProperty("WASTE_WAT_CHAR_TYPE") : "WHWWT";

	/**
	 * <p>
	 * Specifies the WAT_DEV_CHRG_COL_NOTIFYDT_NA used for New Conn KNO
	 * Generation field Notification Date Water Development Charges NA as per
	 * JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 17-FEB-2016
	 */
	public static final String WAT_DEV_CHRG_COL_NOTIFYDT_NA = (null != PropertyUtil
			.getProperty("WAT_DEV_CHRG_COL_NOTIFYDT_NA") ? PropertyUtil
			.getProperty("WAT_DEV_CHRG_COL_NOTIFYDT_NA") : "01-JAN-0001");

	/**
	 * <p>
	 * Specifies the WEB_SERVER_CONNECT_DEFAULT_AUTHCOOKIE as
	 * V0VCOnNlbGZzZXJ2aWNl
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String WEB_SERVER_CONNECT_DEFAULT_AUTHCOOKIE = null != PropertyUtil
			.getProperty("WEB_SERVER_CONNECT_DEFAULT_AUTHCOOKIE") ? PropertyUtil
			.getProperty("WEB_SERVER_CONNECT_DEFAULT_AUTHCOOKIE")
			: "V0VCOnNlbGZzZXJ2aWNl";

	/**
	 * <p>
	 * Specifies the WEB_SERVICE_PROVIDER_URL
	 * </p>
	 * 
	 * @author 830700
	 * @since 02-06-2016 as per open project 1202
	 */
	public static final String WEB_SERVICE_PROVIDER_URL = PropertyUtil
			.getProperty("WEBserverAddress");

	/**
	 * <p>
	 * Message for wrong meter id entered for a particular zro location
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String WRONG_ZRO_OF_MTRRDR = null != PropertyUtil
			.getProperty("WRONG_ZRO_OF_MTRRDR") ? PropertyUtil
			.getProperty("WRONG_ZRO_OF_MTRRDR")
			: "Meter reader does not belong to your ZRO location.";

	/**
	 * <p>
	 * Wrong file range for a particular zro location msg
	 * </p>
	 * 
	 * @author Lovely (986018)
	 * @since 04-10-2016 as per jTrac DJB-4571,Openproject- #1492
	 */

	public static final String WRONG_ZRO_OF_MTRRDR_SELECTED = null != PropertyUtil
			.getProperty("WRONG_ZRO_OF_MTRRDR_SELECTED") ? PropertyUtil
			.getProperty("WRONG_ZRO_OF_MTRRDR_SELECTED")
			: "Sorry this range do not belong to the Zro location selected.";
	/**
	 * <p>
	 * Specifies param name for code value equals to <code>YEAR_INTERVAL</code>
	 * for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author 556885
	 * @since 03-08-2015
	 */
	public static final String YEAR_INTERVAL_PARAM = (null == PropertyUtil
			.getProperty("YEAR_INTERVAL_PARAM") ? "YEAR_INTERVAL"
			: PropertyUtil.getProperty("YEAR_INTERVAL_PARAM").trim());

	/**
	 * <p>
	 * Ensuring that ZRO Location should be selected from DropDown.
	 * </p>
	 * 
	 * @author 1033846
	 * @since 13-07-2016 as per JTrac DJB-4442 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #867
	 */
	public static final String ZRO_MESSAGE = null != PropertyUtil
			.getProperty("ZRO_MESSAGE") ? PropertyUtil
			.getProperty("ZRO_MESSAGE") : "No ZRO Location is selected.";

	/**
	 * <p>
	 * Value of ZRO Search Validator
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 01-28-2016 JTrac DJB-4326
	 */

	public static final String ZRO_SEARCH = null != PropertyUtil
			.getProperty("ZRO_SEARCH") ? PropertyUtil.getProperty("ZRO_SEARCH")
			: "HQ,";

	/**
	 * <p>
	 * Ensuring that ZRo wise Mrd transfer has been initiated.
	 * </p>
	 * 
	 * @author 735689
	 * @since 19-10-2016 as per JTrac DJB-2200 and OpenProject-OpenProject-CODE
	 *        DEVELOPMENT #1540
	 */
	public static final String ZRO_TRANSFER_INITIATED_MSG = null != PropertyUtil
			.getProperty("ZRO_TRANSFER_INITIATED_MSG") ? PropertyUtil
			.getProperty("ZRO_TRANSFER_INITIATED_MSG")
			: "Successful: ZRO wise Mrd Transfer has been initited for Zro Location.";
}
