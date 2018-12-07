/************************************************************************************************************
 * @(#) MeterReadValidator.java 1.1 9 Jan 2013
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.rms.model.ConsumerDetail;
import com.tcs.djb.rms.model.ConsumerStatusLookup;
import com.tcs.djb.rms.model.ErrorDetails;
import com.tcs.djb.rms.model.MRDContainer;
import com.tcs.djb.rms.model.MeterReadTypeLookUp;
import com.tcs.djb.rms.model.MeterReadUploadDetails;
import com.tcs.djb.rms.model.MeterReadUploadErrorLogDetails;
import com.tcs.djb.rms.model.SessionDetails;

/**
 * <p>
 * This contains all the validation methods used in the Meter Read Validation.
 * ErrorDetails contains all the error details found during the validation.
 * </p>
 * 
 * @see ErrorDetails
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 09-01-2013
 * @history 06-03-2013 Matiur Rahman modified validation criteria for MRD
 *          upload. By passing mandatory validation when consumer type is other
 *          than regular type.
 * @history 06-03-2013 Matiur Rahman Error Description Changed.
 * @history 06-03-2013 Matiur Rahman Error Description appended to App Log.
 * @history 08-05-2013 Reshma Edited checking meterRead and meterReadRemark as
 *          mandatory fields only for <code>METERED</code> consumers. This
 *          mandatory validation is checked now in
 *          validateMeterReadUploadAsPerBusinessProcess instead of
 *          validateMeterReadUploadFields.
 * @history 21-05-2013 Reshma Edited method validatePointNo0708 .This validation
 *          should be only for current meter Read Remark OK and PUMP.
 * @history 07-03-2014 Mrityunjoy HHD-114 and HHD-113 new acct id has been added
 *          so that in the response account id send along with error code and
 *          error description
 *          {@link #validateMeterReadUploadAsPerBusinessProcess()}
 * @history 28-03-2014 Mrityunjoy DJB-2024:Change in Meter Replacement Process
 *          Validation message based on the CM_CONS_PRE_BILL_PROC and
 *          CONS_PRE_BILL_STAT_LOOKUP table will be thrown based on the
 *          account_id and bill_round_id from the lookup table description of
 *          the cons_pre_bill_stat_id when data is uploaded via HHD
 *          {@link #validateMeterReadUploadFields()}
 * @history 27-08-2014 Matiur Rahman Modified logic changed by Mrityunjay as per
 *          JTrac DJB-2024 and DJB-2364, Constant variable moved to
 *          {@link DJBConstants},new validation Updated as per JTrac DJB-3001
 * 
 * @history 22-09-2014 Updated by Matiur Rahman Changed code as per changes in
 *          JTrac ID #DJB-3463.
 * 
 * @history Matiur Rahman 23-02-2015 Edited logic for checking meter read
 *          remarks code as per DJB-DJB-3807.
 */
public class MeterReadValidator {
	/**
	 * <p>
	 * Value of static variable <code>ACCESS_DENIED_MSG</code> is retrieved from
	 * Property File <code>djb_rms_web_services.properties</code> for the
	 * message when a user Id has no sufficient privilege to update the details
	 * for the KNO passed. By default value is
	 * "Do not have sufficient Privilege." or Message in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 16-06-2014
	 */
	public static final String ACCESS_DENIED_MSG = null != PropertyUtil
			.getProperty("ACCESS_DENIED_MSG") ? PropertyUtil
			.getProperty("ACCESS_DENIED_MSG")
			: "Do not have sufficient Privilege.";
	/**
	 * <p>
	 * Value of static variable <code>BUSINESS_VALIDATION_ERROR</code> is
	 * retrieved from Property File <code>djb_rms_web_services.properties</code>
	 * for any business or logical validation Error. By default value is
	 * "Business Validation Error" or value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static String BUSINESS_VALIDATION_ERROR = null != PropertyUtil
			.getProperty("BUSINESS_VALIDATION_ERROR") ? PropertyUtil
			.getProperty("BUSINESS_VALIDATION_ERROR").trim()
			: "Business Validation Error";
	/**
	 * <p>
	 * Value of static variable
	 * <code>CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY</code> is
	 * retrieved from Property File <code>djb_rms_web_services.properties</code>
	 * for the status code for meter replacement. By default value is "30" or
	 * value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static final String CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY = null != PropertyUtil
			.getProperty("CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY") ? PropertyUtil
			.getProperty(
					"CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY")
			.trim()
			: "30";
	/**
	 * <p>
	 * Value of static variable <code>FIELD_VALIDATION_ERROR</code> is retrieved
	 * from Property File <code>djb_rms_web_services.properties</code> for any
	 * Invalid input parameter validation Error. By default value is
	 * "Field Validation Error" or value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static String FIELD_VALIDATION_ERROR = null != PropertyUtil
			.getProperty("FIELD_VALIDATION_ERROR") ? PropertyUtil.getProperty(
			"FIELD_VALIDATION_ERROR").trim() : "Field Validation Error";

	/**
	 * <p>
	 * Value of static variable <code>FLAGGING_OFF_CONSUMER_STATUS_CODE</code>
	 * is retrieved from Property File
	 * <code>djb_rms_web_services.properties</code> for the status code for
	 * flagging off consumer from billing. By default value is "15" or value in
	 * property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static String FLAGGING_OFF_CONSUMER_STATUS_CODE = null != PropertyUtil
			.getProperty("FLAGGING_OFF_CONSUMER_STATUS_CODE") ? PropertyUtil
			.getProperty("FLAGGING_OFF_CONSUMER_STATUS_CODE").trim() : "15";
	/**
	 * <p>
	 * Value of static variable <code>FREEZE_STATUS_ID</code> is retrieved from
	 * Property File <code>djb_rms_web_services.properties</code> ,used to
	 * restrict updation of cm_cons_pre_bill_proc table of database with the
	 * bill generation request details if its value is greater than 70.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 */
	public static final String FREEZE_STATUS_ID = null != PropertyUtil
			.getProperty("FREEZE_STATUS_ID") ? PropertyUtil.getProperty(
			"FREEZE_STATUS_ID").trim() : "70";

	/**
	 * <p>
	 * SA_TYPE FOR <code>METERED</code> consumers.
	 * </p>
	 */
	public static String METERED_CONSUMER_SA_TYPE = "METERED";

	/**
	 * <p>
	 * Value of static variable is retrieved from database and set on
	 * application start up or on application context refresh.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String meterReadTypeCodeList = "";

	/**
	 * <p>
	 * Value of static variable
	 * <code>NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS</code> is retrieved from
	 * Property File <code>djb_rms_web_services.properties</code> for the list
	 * of non updatable cons_pre_bill_stat_id. By default value is
	 * "65,67,69,70,120,130" or value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static final String NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS = null != PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS") ? PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS")
			: "65,67,69,70,120,130";

	/**
	 * <p>
	 * Value of static variable
	 * <code>NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE</code> is
	 * retrieved from Property File <code>djb_rms_web_services.properties</code>
	 * for the message if the cons_pre_bill_stat_id is in the list of non
	 * updatable cons_pre_bill_stat_id. By default value is
	 * "Update is not allowed as it is already Updated from other sources" or
	 * value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static final String NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE = null != PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE") ? PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE")
			: "Update is not allowed as it is already Updated from other sources";

	/**
	 * <p>
	 * Value of static variable
	 * <code>NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE</code> is
	 * retrieved from Property File <code>djb_rms_web_services.properties</code>
	 * for the message if the cons_pre_bill_stat_id is in the list of non
	 * updatable cons_pre_bill_stat_id. By default value is
	 * "Update is not allowed as it is already Updated from other sources" or
	 * value in property file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static final String NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE_MTR_RPLC = null != PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE_MTR_RPLC") ? PropertyUtil
			.getProperty("NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE_MTR_RPLC")
			: "Meter replacement has happend, So, billing is not allowed for this account.";

	/**
	 * <p>
	 * Value of static variable is retrieved from database and set on
	 * application start up or on application context refresh.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-02-2015
	 */
	public static String openBillRoundList = "";

	/**
	 * <p>
	 * Value of static variable <code>REGULAR_TYPE_CONSUMER_STATUS_CODE</code>
	 * is retrieved from Property File
	 * <code>djb_rms_web_services.properties</code> for the status code for
	 * regular type consumer. By default value is "60" or value in property
	 * file.
	 * </p>
	 * 
	 * @see djb_rms_web_services.properties
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 10-06-2014
	 */
	public static String REGULAR_TYPE_CONSUMER_STATUS_CODE = null != PropertyUtil
			.getProperty("REGULAR_TYPE_CONSUMER_STATUS_CODE") ? PropertyUtil
			.getProperty("REGULAR_TYPE_CONSUMER_STATUS_CODE").trim() : "60";

	/**
	 * <p>
	 * Check if the user has access to the particular MRD or account belonging
	 * to the MRD.
	 * </p>
	 * 
	 * @param consumerDetail
	 *            contains details of consumer from database.
	 * @param meterReadUploadDetails
	 *            contains meter read details from web service.
	 * @return true if update allowed else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static boolean hasAccessToMRD(String taggedMRKeyList, String mrKey,
			SessionDetails sessionDetails) {
		boolean hasAccess = false;
		try {
			if (null != mrKey
					&& UtilityForAll.isFoundInDelimitedString(taggedMRKeyList,
							",", mrKey)) {
				hasAccess = true;
			}
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		return hasAccess;
	}

	/**
	 * <p>
	 * Check if update is allowed for a particular records for a particular
	 * user. Update is not allowed if the current cons_pre_bill_stat_id is in
	 * the NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS or cons_pre_bill_stat_id is 30
	 * i.e. meter replacement data is entered or flagged off for meter
	 * replacement by other user.
	 * </p>
	 * 
	 * @param consumerDetail
	 *            contains details of consumer from database.
	 * @param meterReadUploadDetails
	 *            contains meter read details from web service.
	 * @return true if update allowed else false
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static boolean isUpdateAllowed(ConsumerDetail consumerDetail,
			MeterReadUploadDetails meterReadUploadDetails, String userId,
			SessionDetails sessionDetails) {
		boolean isAllowed = true;
		try {
			String consPreBillStatId = consumerDetail.getConsumerStatus();
			String lastUpdatedBy = consumerDetail.getLastUpdatedBy();
			if (null != consPreBillStatId) {
				if (UtilityForAll.isFoundInDelimitedString(
						NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS, ",",
						consPreBillStatId)) {
					isAllowed = false;
				} else if (consPreBillStatId
						.equalsIgnoreCase(CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY)
						&& null != lastUpdatedBy
						&& !lastUpdatedBy.equalsIgnoreCase(userId)) {
					isAllowed = false;
				}
			}
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		return isAllowed;
	}

	/**
	 * <p>
	 * Check if update is allowed for a particular records for a particular
	 * user. Update is not allowed if the current cons_pre_bill_stat_id is in
	 * the NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS or cons_pre_bill_stat_id is 30
	 * i.e. meter replacement data is entered or flagged off for meter
	 * replacement by other user.
	 * </p>
	 * 
	 * @param consumerDetail
	 *            contains details of consumer from database.
	 * @param meterReadUploadDetails
	 *            contains meter read details from web service.
	 * @return message if update allowed else null
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 24-02-2015
	 */
	public static String msgIsUpdateAllowed(ConsumerDetail consumerDetail,
			MeterReadUploadDetails meterReadUploadDetails, String userId,
			String meterReaderId, SessionDetails sessionDetails) {
		String errorMsg = null;
		try {
			String consPreBillStatId = consumerDetail.getConsumerStatus();
			String lastUpdatedBy = consumerDetail.getLastUpdatedBy();
			if (null != consPreBillStatId) {
				if (consPreBillStatId.equalsIgnoreCase(FREEZE_STATUS_ID)
						&& null != lastUpdatedBy
						&& (lastUpdatedBy.equalsIgnoreCase(userId) || lastUpdatedBy
								.equalsIgnoreCase(meterReaderId))) {
					errorMsg = null;
				} else if (UtilityForAll.isFoundInDelimitedString(
						NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS, ",",
						consPreBillStatId)) {
					errorMsg = NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE;
				} else if (consPreBillStatId
						.equalsIgnoreCase(CONS_PRE_BILL_STAT_ID_FOR_METER_REPLACEMENT_DATA_ENTRY)
						&& null != lastUpdatedBy
						&& !lastUpdatedBy.equalsIgnoreCase(userId)) {
					errorMsg = NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE_MTR_RPLC;
				}
			}
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		return errorMsg;
	}

	/*
	 * Start:28-08-2014 Commented by Matiur Rahman as per JTrac DJB-2024
	 */
	// /**
	// * <p>
	// * For any business or logical validation Error
	// * </p>
	// */
	// private static String BUSINESS_VALIDATION_ERROR =
	// "Business Validation Error";
	// /**
	// * <p>
	// * For Invalid input parameter validation Error
	// * </p>
	// */
	// private static String FIELD_VALIDATION_ERROR =
	// "Field Validation Error";
	// /**
	// * <p>
	// * SA_TYPE FOR <code>METERED</code> consumers.
	// * </p>
	// */
	// private static String METERED_CONSUMER_SA_TYPE = "METERED";
	// /**
	// * <p>
	// * Status code for regular type consumer.
	// * </p>
	// */
	// private static String REGULAR_TYPE_CONSUMER_STATUS_CODE =
	// "60";
	/*
	 * End:28-08-2014 Commented by Matiur Rahman as per JTrac DJB-2024
	 */
	/**
	 * <p>
	 * This method validates all the parameter required for Meter read Down load
	 * Process via the Web service. ErrorDetails contains all the error details
	 * found during the validation.
	 * </p>
	 * 
	 * @param mrdContainer
	 *            details of the MRD that need to be down loaded
	 * @return errorDetailsArray which is the array of <code>ErrorDetails</code>
	 *         object.
	 * @see ErrorDetails
	 * @see MRDContainer
	 * @history 27-08-2014 Matiur Rahman Replaced Constant variable with the
	 *          constant in {@link DJBConstants}.
	 */
	public static ErrorDetails[] validateMeterReadDownload(
			MRDContainer mrdContainer, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails[] errorDetailsArray = null;
		try {
			AppLog.debugInfo(sessionDetails, "Zone" + mrdContainer.getZoneNo()
					+ "::MR No::" + mrdContainer.getMrNo() + "::Area::"
					+ mrdContainer.getAreaNo() + "::Bill Round::"
					+ mrdContainer.getBillRound());
			ErrorDetails meterReadError = null;
			List<ErrorDetails> meterReadErrorList = new ArrayList<ErrorDetails>();
			if (null == mrdContainer.getZoneNo()
					|| "".equals(mrdContainer.getZoneNo().trim())
					|| "?".equals(mrdContainer.getZoneNo().trim())) {
				meterReadError = new ErrorDetails("Zone is Mandatory",
						FIELD_VALIDATION_ERROR);
				meterReadErrorList.add(meterReadError);
			}
			if (null == mrdContainer.getMrNo()
					|| "".equals(mrdContainer.getMrNo().trim())
					|| "?".equals(mrdContainer.getMrNo().trim())) {
				meterReadError = new ErrorDetails("MR No is Mandatory",
						FIELD_VALIDATION_ERROR);
				meterReadErrorList.add(meterReadError);
			}
			if (null == mrdContainer.getAreaNo()
					|| "".equals(mrdContainer.getAreaNo().trim())
					|| "?".equals(mrdContainer.getAreaNo().trim())) {
				meterReadError = new ErrorDetails("Area is Mandatory",
						FIELD_VALIDATION_ERROR);
				meterReadErrorList.add(meterReadError);
			}
			if (null == mrdContainer.getBillRound()
					|| "".equals(mrdContainer.getBillRound().trim())
					|| "?".equals(mrdContainer.getBillRound().trim())) {
				meterReadError = new ErrorDetails("Bill Round is Mandatory",
						FIELD_VALIDATION_ERROR);
				meterReadErrorList.add(meterReadError);
			}

			if (meterReadErrorList.size() > 0) {
				errorDetailsArray = new ErrorDetails[meterReadErrorList.size()];
				for (int i = 0; i < meterReadErrorList.size(); i++) {
					errorDetailsArray[i] = (ErrorDetails) meterReadErrorList
							.get(i);
				}
			}
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
		return errorDetailsArray;
	}

	/**
	 * <p>
	 * Meter Read Upload As Per Business Process validation. This method
	 * validate the business logic that imposed for data entry of MRD for
	 * billing.First it retrieves all the records corresponding to the account
	 * id and bill round id sent by the HHD to update then it creates a list of
	 * similar object. Each object of the list is compared with the object of
	 * the list sent for updation.
	 * </p>
	 * <p>
	 * <b>The business validation are:</b>(These are with reference to
	 * <code>02 Meter Reading
	 * Validations.odt</code> provided by Amit Jain on 25-01-2013)
	 * </p>
	 * <p>
	 * 1.Current Meter Reading Date should always be greater than Last Meter
	 * Read Date.
	 * </p>
	 * <p>
	 * 2.In case Current Meter Read Remark is any of Regular Read Type (OK,
	 * PUMP, NUW, PLUG, DEM) the Meter Read Type will be Regular.
	 * </p>
	 * <p>
	 * 3.In case Current Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS), Average Read
	 * Type (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS), No Billing Read Type
	 * (CUT, NTRC, NOWC) the Meter Read Type will be No Read.
	 * </p>
	 * <p>
	 * 4.In case Current Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS), Average Read
	 * Type (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS), No Billing Read Type
	 * (CUT, NTRC, NOWC) the Current Meter Reading should always be zero.
	 * </p>
	 * <p>
	 * 5.Current Meter Reading field should only be enabled for Regular Read
	 * Remark (OK, PUMP).
	 * </p>
	 * <p>
	 * 6.In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP), Current Meter Reading should always be greater than Actual Last
	 * Registered Reading.
	 * </p>
	 * <p>
	 * 7.In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and current Meter Reading is less than Actual Last Registered
	 * Reading then this will be case of meter replacement.
	 * </p>
	 * <p>
	 * 8.In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Average Read Type (TEST,
	 * DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then this will be case of meter
	 * replacement.
	 * </p>
	 * <p>
	 * 9.In case of Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP, NUW, PLUG, DEM) - this is allowed, current meter reading should be
	 * greater than Last Registered Reading i.e. Previous Meter Read.
	 * </p>
	 * <p>
	 * 10.In case of Current Meter Read Remark is any of Regular Read Remark
	 * (OK, PUMP) and Previous Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) - this is
	 * allowed, current meter reading should be greater than Actual Last
	 * Registered Reading.
	 * </p>
	 * <p>
	 * 11.In case of Current Meter Read Remark is any of Regular Read Remark
	 * (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Average Read
	 * Remark (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then Current Meter
	 * Reading should always be equal to Last Registered Reading i.e. Previous
	 * Meter Read.
	 * </p>
	 * <p>
	 * 12.In case of Current Meter Read Remark is any of Regular Read Remark
	 * (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Regular Read
	 * Remark (OK, PUMP, NUW, PLUG, DEM) then Current Meter Reading should
	 * always be equal to Last Registered Reading i.e. Previous Meter Read.
	 * </p>
	 * <p>
	 * 13.In case of Current Meter Read Remark is any of Regular Read Remark
	 * (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Provisional
	 * Read Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then
	 * Current Meter Reading should always be equal to Actual Last Registered
	 * Reading.
	 * </p>
	 * <p>
	 * 14.In case Current Meter Reading Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) and Previous
	 * Meter Read Remark is any of Average Read Type (TEST, DFMT, STP, MTTM,
	 * MREV, REP, UNMT, BYPS) - this should not be allowed - Current Meter
	 * Reading Remark will be same as Previous Meter Reading Remark.
	 * </p>
	 * <p>
	 * 15.In case Current Meter Reading Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) and Previous
	 * Meter Read Remark is any of Regular Read Remark (OK, PUMP, NUW, PLUG,
	 * DEM) - this is allowed.
	 * </p>
	 * <p>
	 * 16.In case Current Meter Reading Remark is any of Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and Previous Meter Read
	 * Remark is any of Provisional Read Type (PLOC, NRES, MLOC, MBUR, VMMT,
	 * DUST, BJAM, ADF, RDDT, NS) - this is allowed.
	 * </p>
	 * <p>
	 * 17.In case Current Meter Reading Remark is any of Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and Previous Meter Read
	 * Remark is any of Regular Read Remark (OK, PUMP,NUW, PLUG, DEM) - this is
	 * allowed.
	 * </p>
	 * <p>
	 * 18.In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Read Remark is any of No Billing Read Type
	 * (CUT, NTRC, NOWC) then no billing.
	 * </p>
	 * <p>
	 * 19.In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Read Remark is any of Regular Read Remark
	 * (OK, PUMP, NUW, PLUG, DEM) then no billing – disconnection case.
	 * </p>
	 * <p>
	 * 20.In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Reading Remark is any of Provisional Read
	 * Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then no
	 * billing – disconnection case.
	 * </p>
	 * <p>
	 * 21.In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Reading Remark is any of Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then no billing –
	 * disconnection case.
	 * </p>
	 * <p>
	 * 22.In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP, NUW, PLUG, DEM) and Previous Meter Read Remark is any of No Billing
	 * Read Type (CUT, NTRC, NOWC) then – reopening case.
	 * </p>
	 * <p>
	 * 23.In case Current Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) and Previous
	 * Meter Reading Remark is any of No Billing Read Type (CUT, NTRC, NOWC)
	 * then – reopening case.
	 * </p>
	 * <p>
	 * 24.In case Current Meter Read Remark is any of Average Read Type (TEST,
	 * DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and Previous Meter Reading Remark
	 * is any of No Billing Read Type (CUT, NTRC, NOWC) then – reopening case.
	 * </p>
	 * <p>
	 * 25.In case Current Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) or Average Read
	 * Type (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and customer category
	 * is Cat IA , Cat II or Cat IIA and Previous Average Consumption is Zero or
	 * null , new Average Consumption is mandatory and should be filled.
	 * </p>
	 * 
	 * @param inputMap
	 *            it contains both error details list as well as the list of
	 *            record those to be updated
	 * @return returnMap it contains the error list as well as the record list
	 *         those passed the business validation
	 * @history modified by mrityunjoy added new param KNO HHD-114:Upload
	 *          service 7th March 2014 ErrorDetails constructor getKno has been
	 *          added at the first.
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> validateMeterReadUploadAsPerBusinessProcess(
			HashMap<Object, Object> inputMap, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		if (UtilityForAll.isEmptyString(meterReadTypeCodeList)) {
			throw new RuntimeException(
					"Error in codding, Valid Meter Read Type Code List is not set with values, Contact Developer... ");
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		try {
			ArrayList<MeterReadUploadDetails> filteredToUploadMeterReadDetailsList = new ArrayList<MeterReadUploadDetails>();
			ArrayList<ErrorDetails> meterReadErrorList = (ArrayList<ErrorDetails>) inputMap
					.get("meterReadErrorList");
			List<MeterReadUploadErrorLogDetails> meterReadUploadErrorLogDetailsList = (ArrayList<MeterReadUploadErrorLogDetails>) inputMap
					.get("meterReadUploadErrorLogDetailsList");
			ArrayList<MeterReadUploadDetails> toUploadMeterReadDetailsList = (ArrayList<MeterReadUploadDetails>) inputMap
					.get("toUploadMeterReadDetailsList");
			List<ConsumerDetail> consumerDetailList = (ArrayList<ConsumerDetail>) inputMap
					.get("consumerDetailList");
			MeterReadTypeLookUp meterReadTypeLookUp = (MeterReadTypeLookUp) inputMap
					.get("meterReadTypeLookUp");
			/*
			 * Start:28-08-2014 Added by Matiur Rahman as per JTrac DJB-3001
			 */
			String taggedMRKeyList = (String) (null != inputMap
					.get("taggedMRKeyList") ? inputMap.get("taggedMRKeyList")
					: "");
			String userId = (String) (null != inputMap.get("userId") ? inputMap
					.get("userId") : "");
			String validateAccess = (String) (null != inputMap
					.get("validateAccess") ? inputMap.get("validateAccess")
					: "");
			/*
			 * End:28-08-2014 Added by Matiur Rahman as per JTrac DJB-3001
			 */
			/*
			 * Start:24-02-2015 Added by Matiur Rahman
			 */
			String meterReaderId = (String) (null != inputMap
					.get("meterReaderId") ? inputMap.get("meterReaderId") : "");
			/*
			 * End:24-02-2015 Added by Matiur Rahman
			 */
			// System.out.println("toUploadMeterReadDetailsList:::"
			// + toUploadMeterReadDetailsList.size()
			// + "::consumerDetailList::" + consumerDetailList.size());
			if (null != consumerDetailList && consumerDetailList.size() > 0
					&& null != toUploadMeterReadDetailsList
					&& toUploadMeterReadDetailsList.size() > 0) {
				ConsumerDetail toValidateConsumerDetail = null;
				MeterReadUploadDetails toUpdateMeterReadUploadDetails = null;
				if (null == meterReadUploadErrorLogDetailsList) {
					meterReadUploadErrorLogDetailsList = new ArrayList<MeterReadUploadErrorLogDetails>();
				}
				if (null == meterReadErrorList) {
					meterReadErrorList = new ArrayList<ErrorDetails>();
				}
				for (int i = 0; i < toUploadMeterReadDetailsList.size(); i++) {
					toUpdateMeterReadUploadDetails = (MeterReadUploadDetails) toUploadMeterReadDetailsList
							.get(i);
					// System.out.println("toUpdateMeterReadUploadDetails::"
					// + toUpdateMeterReadUploadDetails.getKno() + "::"
					// + toUpdateMeterReadUploadDetails.getBillRound());
					/**
					 * Only considering the validation for Regular Type
					 * consumer.
					 */
					/*
					 * Start:28-08-2014 Comented by Matiur Rahman as per JTrac
					 * DJB-3001,JTrac DJB-2024 and DJB-2364
					 */
					// if (REGULAR_TYPE_CONSUMER_STATUS_CODE
					// .equalsIgnoreCase(toUpdateMeterReadUploadDetails
					// .getConsumerStatus())) {
					/*
					 * End:28-08-2014 Comented by Matiur Rahman as per JTrac
					 * DJB-3001,JTrac DJB-2024 and DJB-2364
					 */
					for (int k = 0; k < consumerDetailList.size(); k++) {
						toValidateConsumerDetail = (ConsumerDetail) consumerDetailList
								.get(k);
						// System.out.println("Validating with::"
						// + toValidateConsumerDetail.getKno() + "::"
						// + toValidateConsumerDetail.getBillRound());
						if (null != toValidateConsumerDetail.getKno()
								&& (toValidateConsumerDetail.getKno())
										.equals(toUpdateMeterReadUploadDetails
												.getKno())
								&& null != toValidateConsumerDetail
										.getBillRound()
								&& (toValidateConsumerDetail.getBillRound())
										.equals(toUpdateMeterReadUploadDetails
												.getBillRound())) {
							ErrorDetails errorDetails = null;
							int errorDetected = 0;
							/*
							 * Start:28-08-2014 Added by Matiur Rahman as per
							 * JTrac DJB-3001,JTrac DJB-2024 and DJB-2364
							 */
							if ("Y".equals(validateAccess)) {
								errorDetails = validateUpdatePermission(
										toValidateConsumerDetail,
										toUpdateMeterReadUploadDetails, userId,
										taggedMRKeyList, meterReaderId,
										sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
							}
							/*
							 * End:28-08-2014 Added by Matiur Rahman as per
							 * JTrac DJB-3001,JTrac DJB-2024 and DJB-2364
							 */
							if (REGULAR_TYPE_CONSUMER_STATUS_CODE
									.equalsIgnoreCase(toUpdateMeterReadUploadDetails
											.getConsumerStatus())) {
								/**
								 * To validate meterReadRemark and meterRead
								 * fields as mandatory for <code>METERED</code>
								 * consumers.
								 */
								if (METERED_CONSUMER_SA_TYPE
										.equalsIgnoreCase(toValidateConsumerDetail
												.getConsumerType())) {
									String validationMessage = null;
									String errorMessage = "";
									// validationMessage = DJBFieldValidatorUtil
									// .checkAlphabet(
									// "Meter Read Remark",
									// toUpdateMeterReadUploadDetails
									// .getMeterReadRemark(),
									// 5, false);
									validationMessage = DJBFieldValidatorUtil
											.checkMeterReadRemark(
													"Meter Read Remark",
													toUpdateMeterReadUploadDetails
															.getMeterReadRemark(),
													false,
													meterReadTypeCodeList,
													sessionDetails);
									/**
									 * modified by mrityunjoy added new param
									 * KNO HHD-114:Upload service 7th March 2014
									 * ErrorDetails constructor getKno has been
									 * added at the first
									 */

									if (null != validationMessage) {
										errorMessage = errorMessage + "\n"
												+ validationMessage;
										errorDetails = new ErrorDetails(
												toUpdateMeterReadUploadDetails
														.getKno(),
												"For KNO "
														+ toUpdateMeterReadUploadDetails
																.getKno()
														+ errorMessage,
												FIELD_VALIDATION_ERROR);
									}
									// validationMessage = DJBFieldValidatorUtil
									// .checkAmount("Meter Read",
									// toUpdateMeterReadUploadDetails
									// .getMeterRead(),
									// 10, false);
									validationMessage = DJBFieldValidatorUtil
											.checkMeterRead("Meter Read",
													toUpdateMeterReadUploadDetails
															.getMeterRead(),
													10, false, sessionDetails);

									/**
									 * modified by mrityunjoy added new param
									 * KNO HHD-114:Upload service 7th March 2014
									 * ErrorDetails constructor getKno has been
									 * added at the first
									 */

									if (null != validationMessage) {
										errorMessage = errorMessage + "\n"
												+ validationMessage;
										errorDetails = new ErrorDetails(
												toUpdateMeterReadUploadDetails
														.getKno(),
												"For KNO "
														+ toUpdateMeterReadUploadDetails
																.getKno()
														+ errorMessage,
												FIELD_VALIDATION_ERROR);
									}
									if (null != errorDetails) {
										meterReadErrorList.add(errorDetails);
										meterReadUploadErrorLogDetailsList
												.add(new MeterReadUploadErrorLogDetails(
														errorDetails,
														toUpdateMeterReadUploadDetails));
										errorDetected++;
									}
								}

								// System.out.println("Validate Success::"
								// + toValidateConsumerDetail.getKno()
								// + "::"
								// + toValidateConsumerDetail.getKno()
								// + "::"
								// + toValidateConsumerDetail
								// .getBillRound()
								// + "::"
								// + toUpdateMeterReadUploadDetails
								// .getBillRound());

								// Validate Point No.1
								errorDetails = validatePointNo01(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.4
								errorDetails = validatePointNo04(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.5
								// errorDetails = validatePointNo05(
								// toUpdateMeterReadUploadDetails,
								// toValidateConsumerDetail,
								// meterReadTypeLookUp);
								// if (null != errorDetails) {
								// meterReadErrorList.add(errorDetails);
								// meterReadUploadErrorLogDetailsList
								// .add(new MeterReadUploadErrorLogDetails(
								// errorDetails,
								// toUpdateMeterReadUploadDetails));
								// errorDetected++;
								// }
								// Validate Point No.7 and 8
								errorDetails = validatePointNo0708(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.9
								errorDetails = validatePointNo09(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.10
								errorDetails = validatePointNo10(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.11
								errorDetails = validatePointNo11(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.12
								errorDetails = validatePointNo12(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.13
								errorDetails = validatePointNo13(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.14
								errorDetails = validatePointNo14(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.19,20 and 21
								errorDetails = validatePointNo192021(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.22,23 and 24
								errorDetails = validatePointNo222324(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
								// Validate Point No.25
								errorDetails = validatePointNo25(
										toUpdateMeterReadUploadDetails,
										toValidateConsumerDetail,
										meterReadTypeLookUp, sessionDetails);
								if (null != errorDetails) {
									meterReadErrorList.add(errorDetails);
									meterReadUploadErrorLogDetailsList
											.add(new MeterReadUploadErrorLogDetails(
													errorDetails,
													toUpdateMeterReadUploadDetails));
									errorDetected++;
									continue;
								}
							}
							/**
							 * If No Business Process validation Error.
							 */
							if (errorDetected == 0) {
								filteredToUploadMeterReadDetailsList
										.add(toUpdateMeterReadUploadDetails);
								// System.out
								// .println("toUpdateMeterReadUploadDetails::"
								// + toUpdateMeterReadUploadDetails
								// .getKno()
								// + "::"
								// + toUpdateMeterReadUploadDetails
								// .getBillRound()
								// + "::"
								// + toUpdateMeterReadUploadDetails
								// .getConsumerStatus());
							}
						}
					}
					/*
					 * Start:28-08-2014 Comented by Matiur Rahman as per JTrac
					 * DJB-3001,JTrac DJB-2024 and DJB-2364
					 */
					// } else {
					// filteredToUploadMeterReadDetailsList
					// .add(toUpdateMeterReadUploadDetails);
					// }
					/*
					 * End:28-08-2014 Comented by Matiur Rahman as per JTrac
					 * DJB-3001,JTrac DJB-2024 and DJB-2364
					 */
				}
			}
			// System.out.println("meterReadErrorList::"
			// + meterReadErrorList.size()
			// + "::filteredToUploadMeterReadDetailsList::"
			// + filteredToUploadMeterReadDetailsList.size());
			returnMap.put("toUploadMeterReadDetailsList",
					filteredToUploadMeterReadDetailsList);
			returnMap.put("meterReadErrorList", meterReadErrorList);
			returnMap.put("meterReadUploadErrorLogDetailsList",
					meterReadUploadErrorLogDetailsList);
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
		return returnMap;

	}

	/**
	 * <p>
	 * This method validates all the parameter of the Meter read Upload Process
	 * via the Web service. meterReadUploadRequest contains all the details need
	 * to Upload.
	 * </p>
	 * 
	 * @param meterReadUploadRequest
	 *            contains details of the MRD that need to be uploaded
	 * @param userId
	 *            user id by which meter read to be uploaded
	 * @return returnMap which contains meterReadErrorList and
	 *         toUploadMeterReadDetailsList
	 * @see ErrorDetails
	 * @see MeterReadUploadDetails
	 * @see MeterReadUploadErrorLogDetails <p>
	 *      DJB-2024:Change in Meter Replacement Process Validation message
	 *      based on the CM_CONS_PRE_BILL_PROC and CONS_PRE_BILL_STAT_LOOKUP
	 *      table will be thrown based on the account_id and bill_round_id from
	 *      the lookup table description of the cons_pre_bill_stat_id when data
	 *      is uploaded via HHD
	 *      </p>
	 * @author mrityunjoy
	 * @since 28-03-2014
	 * 
	 * @history 27-08-2014 Matiur Rahman added new parameter userId.
	 */
	public static Map<Object, Object> validateMeterReadUploadFields(
			MeterReadUploadDetails[] meterReadUploadDetailsArray,
			String userId, SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		if (UtilityForAll.isEmptyString(meterReadTypeCodeList)) {
			throw new RuntimeException(
					"Error in codding, Valid Meter Read Type Code List is not set with values, Contact Developer... ");
		}
		if (UtilityForAll.isEmptyString(openBillRoundList)) {
			throw new RuntimeException(
					"Error in codding, Open Bill Round List is not set with values, Contact Developer... ");
		}
		/**
		 * returnResultMap has been added to handle response coming from the
		 * CONS_PRE_BILL_STAT_LOOKUP DJB-2024:Change in Meter Replacement
		 * Process
		 * 
		 * @author mrityunjoy
		 * @since 28-03-2014 Change starts from here
		 * */
		/* Start: Commented by Matiur Rahman on 11-06-2014 as this not required */
		// Map<String, String> returnResultMap = new HashMap<String, String>();
		/* End: Commented by Matiur Rahman on 11-06-2014 as this not required */
		/**
		 * returnResultMap has been added to handle response coming from the
		 * CONS_PRE_BILL_STAT_LOOKUP DJB-2024:Change in Meter Replacement
		 * Process
		 * 
		 * @author mrityunjoy
		 * @since 28-03-2014 Change ends from here
		 * */

		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		ErrorDetails meterReadError = null;
		List<ErrorDetails> meterReadErrorList = new ArrayList<ErrorDetails>();
		List<MeterReadUploadDetails> toUploadMeterReadDetailsList = new ArrayList<MeterReadUploadDetails>();
		List<MeterReadUploadErrorLogDetails> meterReadUploadErrorLogDetailsList = new ArrayList<MeterReadUploadErrorLogDetails>();
		try {
			MeterReadUploadDetails meterReadUploadDetails = null;
			for (int i = 0; i < meterReadUploadDetailsArray.length; i++) {
				boolean validSet = true;
				meterReadUploadDetails = meterReadUploadDetailsArray[i];
				// System.out.println("Bill Round::"
				// + meterReadUploadDetails.getBillRound() + "::KNO::"
				// + meterReadUploadDetails.getKno()
				// + "::Consumer Status::"
				// + meterReadUploadDetails.getConsumerStatus()
				// + "::Meter Read Date::"
				// + meterReadUploadDetails.getMeterReadDate()
				// + "::MeterReadRemark::"
				// + meterReadUploadDetails.getMeterReadRemark()
				// + "::MeterRead::"
				// + meterReadUploadDetails.getMeterRead()
				// + "::Average Consumption::"
				// + meterReadUploadDetails.getAverageConsumption()
				// + "::No Of Floors::"
				// + meterReadUploadDetails.getNoOfFloors());
				String validationMessage = null;
				String errorMessage = "";
				/*
				 * Start:Commented by Matiur Rahman on 23-02-2015 for the change
				 * for server side validation as per DJB-DJB-3807.
				 */
				// validationMessage = DJBFieldValidatorUtil.checkBillCycle(
				// "Bill Round", meterReadUploadDetails.getBillRound(),
				// 10, false);
				/*
				 * End:Commented by Matiur Rahman on 23-02-2015 for the change
				 * for server side validation as per DJB-DJB-3807.
				 */
				/*
				 * Start:Added by Matiur Rahman on 23-02-2015 for the change for
				 * server side validation as per DJB-DJB-3807.
				 */
				validationMessage = DJBFieldValidatorUtil
						.checkIfInOpenBillRound("Bill Round",
								meterReadUploadDetails.getBillRound(), false,
								openBillRoundList, sessionDetails);
				/*
				 * End:Added by Matiur Rahman on 23-02-2015 for the change for
				 * server side validation as per DJB-DJB-3807.
				 */
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkNumeric("KNO",
						meterReadUploadDetails.getKno(), 10, false,
						sessionDetails);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				/*
				 * Start:Commented by Matiur Rahman as found to be wrong
				 * position of validation where extra database call has been
				 * made which might be with existing data set that is populated
				 * for existing validation.
				 */
				/**
				 * <p>
				 * DJB-2024:Change in Meter Replacement Process Validation
				 * message based on the CM_CONS_PRE_BILL_PROC and
				 * CONS_PRE_BILL_STAT_LOOKUP table will be thrown based on the
				 * account_id and bill_round_id from the lookup table
				 * description of the cons_pre_bill_stat_id when data is
				 * uploaded via HHD
				 * </p>
				 * 
				 * @author mrityunjoy
				 * @since 28-03-2014 Change starts from here
				 * */

				// returnResultMap = BillGenerationDAO
				// .getStatusAndConsPreBillStatIdBasedOnAcctIdAndBillRound(
				// meterReadUploadDetails.getBillRound(),
				// meterReadUploadDetails.getKno());
				//
				// if (null != returnResultMap) {
				// for (Map.Entry<String, String> entry : returnResultMap
				// .entrySet()) {
				//
				// if ((entry.getKey()
				// .equals(DJBQueryConstants.RECORD_FREEZED_PARKED_FOR_BILLING))
				// || (entry.getKey()
				// .equals(DJBQueryConstants.RECORDS_FROZEN_PARKED_FOR_METER_REPLACEMENT))
				// || (entry.getKey()
				// .equals(DJBQueryConstants.METERS_REPLACEMENT_FROZEN_FOR_BILLING)))
				// {
				// validationMessage = entry.getValue();
				//
				// }
				// }
				// }
				//
				// if (null != validationMessage) {
				// validSet = false;
				// errorMessage = errorMessage + "\n" + validationMessage;
				// }
				/**
				 * DJB-2024:Change in Meter Replacement Process Change ends here
				 * by mrityunjoy 28th March 2014
				 */
				/*
				 * End:Commented by Matiur Rahman as found to be wrong position
				 * of validation where extra database call has been made which
				 * might be with existing data set that is populated for
				 * existing validation.
				 */
				/*
				 * Start:Commented by Matiur Rahman on 23-02-2015 for the change
				 * for server side validation as per DJB-DJB-3807.
				 */
				// validationMessage = DJBFieldValidatorUtil.checkNumeric(
				// "Consumer Status", meterReadUploadDetails
				// .getConsumerStatus(), 3, false);
				/*
				 * End:Commented by Matiur Rahman on 23-02-2015 for the change
				 * for server side validation as per DJB-DJB-3807.
				 */
				/*
				 * Start:Added by Matiur Rahman on 23-02-2015 for the change for
				 * server side validation as per DJB-DJB-3807.
				 */
				validationMessage = DJBFieldValidatorUtil.checkConsumerStatus(
						"Consumer Status", meterReadUploadDetails
								.getConsumerStatus(), false, sessionDetails);
				/*
				 * End:Added by Matiur Rahman on 23-02-2015 for the change for
				 * server side validation as per DJB-DJB-3807.
				 */
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				/**
				 * For consumer status of invalid data, meter read date not is
				 * mandatory, else mandatory
				 */
				if (!FLAGGING_OFF_CONSUMER_STATUS_CODE
						.equalsIgnoreCase(meterReadUploadDetails
								.getConsumerStatus())) {
					validationMessage = DJBFieldValidatorUtil.checkDate(
							"Meter Read Date", meterReadUploadDetails
									.getMeterReadDate(), 10, false,
							sessionDetails);
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					} else if (UtilityForAll.checkFutureDate(
							meterReadUploadDetails.getMeterReadDate(),
							sessionDetails) > 0) {
						validSet = false;
						errorMessage = errorMessage
								+ "\nMeter Read Date Should not be a Future Date.";
					}
				} else {
					validationMessage = DJBFieldValidatorUtil.checkDate(
							"Meter Read Date", meterReadUploadDetails
									.getMeterReadDate(), 10, true,
							sessionDetails);
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					} else if (null != meterReadUploadDetails
							.getMeterReadDate()
							&& UtilityForAll.checkFutureDate(
									meterReadUploadDetails.getMeterReadDate(),
									sessionDetails) > 0) {
						validSet = false;
						errorMessage = errorMessage
								+ "\nMeter Read Date Should not be a Future Date.";
					}
				}
				/**
				 * Only considering the rest of the validation for Regular Type
				 * consumer.
				 */
				if (REGULAR_TYPE_CONSUMER_STATUS_CODE
						.equalsIgnoreCase(meterReadUploadDetails
								.getConsumerStatus())) {
					/*
					 * Start:Commented by Matiur Rahman on 23-02-2015 for the
					 * change for checking meter read remarks code as per
					 * DJB-DJB-3807.
					 */
					// validationMessage = DJBFieldValidatorUtil.checkAlphabet(
					// "Meter Read Remark", meterReadUploadDetails
					// .getMeterReadRemark(), 5, true);
					/*
					 * End:Commented by Matiur Rahman on 23-02-2015 for the
					 * change for checking meter read remarks code as per
					 * DJB-DJB-3807.
					 */
					/*
					 * Start:Addded by Matiur Rahman on 23-02-2015 for the
					 * change for checking meter read remarks code as per
					 * DJB-DJB-3807.
					 */
					validationMessage = DJBFieldValidatorUtil
							.checkMeterReadRemark(
									"Meter Read Remark",
									meterReadUploadDetails.getMeterReadRemark(),
									true, meterReadTypeCodeList, sessionDetails);
					/*
					 * End:Addded by Matiur Rahman on 23-02-2015 for the change
					 * for checking meter read remarks code as per DJB-DJB-3807.
					 */
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					}
					/*
					 * Start:19-06-2014 Commented by Matiur Rahman as per
					 * changes in JTrac ID #DJB-3028.
					 */
					// validationMessage = DJBFieldValidatorUtil.checkAmount(
					// "Meter Read",
					// meterReadUploadDetails.getMeterRead(), 10, true);
					/*
					 * End:19-06-2014 Commented by Matiur Rahman as per changes
					 * in JTrac ID #DJB-3028.
					 */
					/*
					 * Start:19-06-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3028.
					 */
					/*
					 * Start:22-09-2014 Commented by Matiur Rahman as per
					 * changes in JTrac ID #DJB-3463.
					 */
					// validationMessage = DJBFieldValidatorUtil.checkNumeric(
					// "Meter Read",
					// meterReadUploadDetails.getMeterRead(), 10, false);
					/*
					 * End:22-09-2014 Commented by Matiur Rahman as per changes
					 * in JTrac ID #DJB-3463.
					 */
					/*
					 * Start:22-09-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3463.
					 */
					/*
					 * Start:Commented by Matiur Rahman on 23-02-2015 for the
					 * change for server side validation as per DJB-DJB-3807.
					 */
					// validationMessage = DJBFieldValidatorUtil.checkNumeric(
					// "Meter Read",
					// meterReadUploadDetails.getMeterRead(), 10, true);
					/*
					 * End:Commented by Matiur Rahman on 23-02-2015 for the
					 * change for server side validation as per DJB-DJB-3807.
					 */
					/*
					 * Start:Added by Matiur Rahman on 23-02-2015 for the change
					 * for server side validation as per DJB-DJB-3807.
					 */
					validationMessage = DJBFieldValidatorUtil.checkMeterRead(
							"Meter Read",
							meterReadUploadDetails.getMeterRead(), 10, true,
							sessionDetails);
					/*
					 * End:Added by Matiur Rahman on 23-02-2015 for the change
					 * for server side validation as per DJB-DJB-3807.
					 */
					/*
					 * End:22-09-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3463.
					 */
					/*
					 * Start:19-06-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3028.
					 */
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					}
					/*
					 * Start:19-06-2014 Commented by Matiur Rahman as per
					 * changes in JTrac ID #DJB-3028.
					 */
					// validationMessage = DJBFieldValidatorUtil.checkAmount(
					// "Average Consumption", meterReadUploadDetails
					// .getAverageConsumption(), 10, true);
					/*
					 * End:19-06-2014 Commented by Matiur Rahman as per changes
					 * in JTrac ID #DJB-3028.
					 */
					/*
					 * Start:19-06-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3028.
					 */
					validationMessage = DJBFieldValidatorUtil.checkNumeric(
							"Average Consumption", meterReadUploadDetails
									.getAverageConsumption(), 10, true,
							sessionDetails);
					/*
					 * End:19-06-2014 Added by Matiur Rahman as per changes in
					 * JTrac ID #DJB-3028.
					 */
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					}
					validationMessage = DJBFieldValidatorUtil.checkNumeric(
							"No Of Floors", meterReadUploadDetails
									.getNoOfFloors(), 3, true, sessionDetails);
					if (null != validationMessage) {
						validSet = false;
						errorMessage = errorMessage + "\n" + validationMessage;
					}
				}
				if (validSet) {
					toUploadMeterReadDetailsList.add(meterReadUploadDetails);
				} else {
					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */
					AppLog.debugInfo(sessionDetails, "Error For KNO "
							+ meterReadUploadDetails.getKno() + errorMessage);
					if (null != meterReadUploadDetails.getKno()
							&& !"".equalsIgnoreCase(meterReadUploadDetails
									.getKno().trim())) {
						meterReadError = new ErrorDetails(
								meterReadUploadDetails.getKno(), "For KNO "
										+ meterReadUploadDetails.getKno()
										+ errorMessage, FIELD_VALIDATION_ERROR);
					} else { /*
							 * modified by mrityunjoy added new param KNO
							 * HHD-114:Upload service 7th March 2014
							 */
						meterReadError = new ErrorDetails(
								meterReadUploadDetails.getKno(), "Record No "
										+ (i + 1) + ", " + errorMessage,
								FIELD_VALIDATION_ERROR);
					}
					meterReadErrorList.add(meterReadError);
					meterReadUploadErrorLogDetailsList
							.add(new MeterReadUploadErrorLogDetails(
									meterReadError, meterReadUploadDetails));
				}
			}
			// System.out.println("meterReadErrorList::"
			// + meterReadErrorList.size()
			// + "::toUploadMeterReadDetailsList::"
			// + toUploadMeterReadDetailsList.size());
			returnMap.put("meterReadErrorList", meterReadErrorList);
			returnMap.put("meterReadUploadErrorLogDetailsList",
					meterReadUploadErrorLogDetailsList);
			returnMap.put("toUploadMeterReadDetailsList",
					toUploadMeterReadDetailsList);

		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
		return returnMap;
	}

	/**
	 * <p>
	 * <b>Validation for point 1.</b><br>
	 * Current Meter Reading Date should always be greater than Last Meter Read
	 * Date.
	 * </p>
	 * 
	 * @see UtilityForAll#compareDates
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @return ErrorDetails reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo01(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			if (null != toValidateConsumerDetail.getPreviousMeterReadDetails()
					&& !"NA".equalsIgnoreCase(toValidateConsumerDetail
							.getPreviousMeterReadDetails()
							.getPreviousMeterReadDate())) {
				int dayDiffer = UtilityForAll.compareDates(
						meterReadUploadDetails.getMeterReadDate(),
						toValidateConsumerDetail.getPreviousMeterReadDetails()
								.getPreviousMeterReadDate());
				// System.out.println("dayDiffer::" + dayDiffer);
				/*
				 * Start: 23-06-2014 Matiur Rahman commented as per JTrac
				 * ID#DJB-3001
				 */
				// if (dayDiffer == -1) {
				/*
				 * End: 23-06-2014 Matiur Rahman commented as per JTrac
				 * ID#DJB-3001
				 */
				/*
				 * Start: 23-06-2014 Matiur Rahman Added as per JTrac
				 * ID#DJB-3001
				 */
				if (dayDiffer < 1) {
					/*
					 * End: 23-06-2014 Matiur Rahman Added as per JTrac
					 * ID#DJB-3001
					 */
					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */
					errorDetails = new ErrorDetails(
							meterReadUploadDetails.getKno(),
							"For KNO "
									+ meterReadUploadDetails.getKno()
									+ ",\nCurrent Meter Reading Date "
									+ meterReadUploadDetails.getMeterReadDate()
									+ " should be greater than Previous Meter Read Date "
									+ toValidateConsumerDetail
											.getPreviousMeterReadDetails()
											.getPreviousMeterReadDate(),
							BUSINESS_VALIDATION_ERROR);
					AppLog
							.info(
									sessionDetails,
									BUSINESS_VALIDATION_ERROR
											+ "::Current Meter Reading Date "
											+ meterReadUploadDetails
													.getMeterReadDate()
											+ " should be greater than Previous Meter Read Date "
											+ toValidateConsumerDetail
													.getPreviousMeterReadDetails()
													.getPreviousMeterReadDate()
											+ "::Current Meter Reading Date::"
											+ meterReadUploadDetails
													.getMeterReadDate()
											+ "::Previous Meter Read Date::"
											+ toValidateConsumerDetail
													.getPreviousMeterReadDetails()
													.getPreviousMeterReadDate());
				}

			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 *<p>
	 * <b>Validation for point 4.</b><br>
	 * In case Current Meter Read Remark is any of Provisional Read Type (PLOC,
	 * NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS), Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS), No Billing Read Type
	 * (CUT, NTRC, NOWC) the Current Meter Reading should always be zero.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo04(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			/**
			 * In case Current Meter Read Remark is any of Provisional Read Type
			 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS),
			 * Average Read Type (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS),
			 * No Billing Read Type (CUT, NTRC, NOWC) i.e. not a Regular type
			 */
			if (null != currentMeterReadRemark
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& !(meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * the Current Meter Reading should always be zero.
				 */
				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */
				if (null != currentMeterRead
						&& Integer.parseInt(currentMeterRead.trim()) > 0) {
					errorDetails = new ErrorDetails(
							meterReadUploadDetails.getKno(),
							"For KNO "
									+ meterReadUploadDetails.getKno()
									+ ",\nCurrent Meter Reading should always be zero for Remark Code "
									+ currentMeterReadRemark + ".",
							BUSINESS_VALIDATION_ERROR);
					AppLog
							.info(
									sessionDetails,
									BUSINESS_VALIDATION_ERROR
											+ "::Current Meter Reading should always be zero for Remark Code "
											+ currentMeterReadRemark
											+ "::currentMeterReadRemark::"
											+ currentMeterReadRemark
											+ "::currentMeterRead::"
											+ currentMeterRead);
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 *<p>
	 * <b>Validation for point 5.</b><br>
	 * Current Meter Reading field should only be enabled for Regular Read
	 * Remark (OK, PUMP).
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo05(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			/**
			 * Current Meter Reading field should only be enabled for Regular
			 * Read Remark (OK, PUMP).
			 */
			// System.out.println((null != currentMeterRead && !""
			// .equalsIgnoreCase(currentMeterRead.trim())));

			/**
			 * modified by mrityunjoy added new param KNO HHD-114:Upload service
			 * 7th March 2014 ErrorDetails constructor getKno has been added at
			 * the first
			 */

			if (null != currentMeterReadRemark
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)
					&& !("OK".equalsIgnoreCase(currentMeterReadRemark.trim()) || "PUMP"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))
					&& (null != currentMeterRead && Integer
							.parseInt(currentMeterRead.trim()) > 0)) {
				errorDetails = new ErrorDetails(
						meterReadUploadDetails.getKno(),
						"For KNO "
								+ meterReadUploadDetails.getKno()
								+ ",\nCurrent Meter Reading field should only be Entered for Regular Read Remark OK and PUMP.",
						BUSINESS_VALIDATION_ERROR);
				AppLog
						.info(
								sessionDetails,
								BUSINESS_VALIDATION_ERROR
										+ "::Current Meter Reading field should only be Entered for Regular Read Remark OK and PUMP::currentMeterReadRemark::"
										+ currentMeterReadRemark
										+ "::currentMeterRead::"
										+ currentMeterRead);
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for Meter Replacement Cases</b>
	 * </p>
	 * <p>
	 * <b>Validation for point 7.</b><br>
	 * In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and current Meter Reading is less than Actual Last Registered
	 * Reading then this will be case of meter replacement.
	 * </p>
	 * <p>
	 * <b>Validation for point 8.</b><br>
	 * In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Average Read Type (TEST,
	 * DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then this will be case of meter
	 * replacement.
	 * </p>
	 * 
	 * @exception Exception
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo0708(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String consumerStatus = meterReadUploadDetails.getConsumerStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String previousMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousActualMeterRead();
			/**
			 * In case Current Meter Read Remark is any of Regular Read Remark
			 * (OK, PUMP) and Previous Meter Read Remark is any of Regular Read
			 * Remark (OK, PUMP)
			 */
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark);
			if (null != currentMeterReadRemark
					&& currentMeterReadRemark
							.equalsIgnoreCase(previousMeterReadRemark)
					&& ("OK".equalsIgnoreCase(currentMeterReadRemark) || "PUMP"
							.equalsIgnoreCase(currentMeterReadRemark))) {
				/**
				 * current Meter Reading is less than Actual Last Registered
				 * Reading
				 */

				if (null != previousMeterRead
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& !"".equalsIgnoreCase(previousMeterRead)
						&& Integer.parseInt(currentMeterRead.trim()) < Integer
								.parseInt(previousMeterRead.trim())) {

					/**
					 * this will be case of meter replacement.
					 */

					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */

					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.METER_INSTALLED
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nThis is a Meter Replacement case,Current Meter Read Should be Greater than "
						// + previousMeterRead
						// + " Or Consumer Status Code Should be equal to "
						// + Integer
						// .toString(ConsumerStatusLookup.METER_INSTALLED
						// .getStatusCode()),
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Read Should be Greater than "
										+ previousMeterRead
										+ " Or If this is a Meter Replacement case, Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.METER_INSTALLED
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::This is a Meter Replacement case,Current Meter Read Should be Greater than "
												+ previousMeterRead
												+ " Or Consumer Status Code Should be equal to "
												+ ConsumerStatusLookup.METER_INSTALLED
														.getStatusCode()
												+ "::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousMeterRead::"
												+ previousMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			} else {
				/**
				 * In case Current Meter Read Remark is any of Regular Read
				 * Remark (OK, PUMP)
				 */
				if (null != currentMeterReadRemark
						&& ("OK"
								.equalsIgnoreCase(currentMeterReadRemark.trim()) || "PUMP"
								.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
					/**
					 * Previous Meter Read Remark is any of Average Read Type
					 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS)
					 */
					if (null != previousMeterReadRemark
							&& !"NA".equalsIgnoreCase(previousMeterReadRemark
									.trim())
							&& ((null != meterReadTypeLookUp
									.getAverageReadType().getReadTypeCode() && (meterReadTypeLookUp
									.getAverageReadType().getReadTypeCode()
									.indexOf(previousMeterReadRemark.trim()) > -1)))) {
						/**
						 * this will be case of meter replacement.
						 */

						/**
						 * modified by mrityunjoy added new param KNO
						 * HHD-114:Upload service 7th March 2014 ErrorDetails
						 * constructor getKno has been added at the first
						 */
						if (null != consumerStatus
								&& !Integer
										.toString(
												ConsumerStatusLookup.METER_INSTALLED
														.getStatusCode())
										.equalsIgnoreCase(consumerStatus.trim())) {
							// errorDetails = new ErrorDetails(
							// meterReadUploadDetails.getKno(),
							// "For KNO "
							// + meterReadUploadDetails.getKno()
							// +
							// ",\nThis is a Meter Replacement case as Change in Remark Code from "
							// + previousMeterReadRemark
							// + " to "
							// + currentMeterReadRemark
							// + ", Consumer Status Code Should be equal to "
							// + Integer
							// .toString(ConsumerStatusLookup.METER_INSTALLED
							// .getStatusCode()),
							// BUSINESS_VALIDATION_ERROR);
							errorDetails = new ErrorDetails(
									meterReadUploadDetails.getKno(),
									"For KNO "
											+ meterReadUploadDetails.getKno()
											+ ",\nThis seems to be a Meter Replacement case as Change in Remark Code from "
											+ previousMeterReadRemark
											+ " to "
											+ currentMeterReadRemark
											+ ", So, Consumer Status Code Should be equal to "
											+ Integer
													.toString(ConsumerStatusLookup.METER_INSTALLED
															.getStatusCode()),
									BUSINESS_VALIDATION_ERROR);
							AppLog
									.info(
											sessionDetails,
											BUSINESS_VALIDATION_ERROR
													+ "::This is a Meter Replacement case as Change in Remark Code from "
													+ previousMeterReadRemark
													+ " to "
													+ currentMeterReadRemark
													+ "::previousMeterReadRemark::"
													+ previousMeterReadRemark
													+ "::currentMeterReadRemark::"
													+ currentMeterReadRemark
													+ "::previousMeterRead::"
													+ previousMeterRead
													+ "::currentMeterRead::"
													+ currentMeterRead);
						}
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 *<p>
	 * <b>Validation for point 9.</b><br>
	 * In case of Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP, NUW, PLUG, DEM) - this is allowed, current meter reading should be
	 * greater than Last Registered Reading i.e. Previous Meter Read.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo09(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String previousMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterRead();
			/**
			 * In case of Current Meter Read Remark is any of Regular Read
			 * Remark (OK, PUMP) and Previous Meter Read Remark is any of
			 * Regular Read Remark (OK, PUMP, NUW, PLUG, DEM)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& ("OK".equalsIgnoreCase(currentMeterReadRemark.trim()) || "PUMP"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
				/**
				 * current meter reading should be greater than Last Registered
				 * Reading.
				 */
				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */

				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Integer.parseInt(currentMeterRead.trim()) < Integer
							.parseInt(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent meter reading should be greater than Previous Registered Reading "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::Current meter reading should be greater than Previous Registered Reading::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousMeterRead::"
												+ previousMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 10.</b><br>
	 * In case of Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP) and Previous Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) - this is
	 * allowed, current meter reading should be greater than Actual Last
	 * Registered Reading.
	 * </p>
	 * <p>
	 * 6.In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP), Current Meter Reading should always be greater than Actual Last
	 * Registered Reading.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo10(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String previousActualMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousActualMeterRead();
			/**
			 *In case of Current Meter Read Remark is any of Regular Read
			 * Remark (OK, PUMP) and Previous Meter Read Remark is any of
			 * Provisional Read Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM,
			 * ADF, RDDT, NS)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& ("OK".equalsIgnoreCase(currentMeterReadRemark.trim()) || "PUMP"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
				/**
				 * current meter reading should be greater than Actual Last
				 * Registered Reading.
				 */

				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */

				if (null != previousActualMeterRead
						&& !"".equalsIgnoreCase(previousActualMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousActualMeterRead
								.trim()) && null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Integer.parseInt(currentMeterRead.trim()) < Integer
							.parseInt(previousActualMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent meter reading should be greater than Previous Actual Meter Read "
										+ previousActualMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::Current meter reading should be greater than Previous Actual Meter Read::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousActualMeterRead::"
												+ previousActualMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 11.</b><br>
	 * In case of Current Meter Read Remark is any of Regular Read Remark (NUW,
	 * PLUG, DEM) and Previous Meter Read Remark is any of Average Read Remark
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then Current Meter Reading
	 * should always be equal to Last Registered Reading i.e. Previous Meter
	 * Read.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo11(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String previousMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterRead();
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark + "::currentMeterRead::"
			// + currentMeterRead + "::previousMeterRead::"
			// + previousMeterRead);
			/**
			 * In case of Current Meter Read Remark is any of Regular Read
			 * Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of
			 * Average Read Remark (TEST, DFMT, STP, MTTM, MREV, REP, UNMT,
			 * BYPS)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& ("NUW".equalsIgnoreCase(currentMeterReadRemark.trim())
							|| "PLUG".equalsIgnoreCase(currentMeterReadRemark
									.trim()) || "DEM"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
				/**
				 * Current Meter Reading should always be equal to Last
				 * Registered Reading.
				 */

				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */

				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Integer.parseInt(currentMeterRead.trim()) != Integer
							.parseInt(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Meter Read "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::Current Meter Reading should be equal to Previous Meter Read::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousMeterRead::"
												+ previousMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 12.</b><br>
	 * In case of Current Meter Read Remark is any of Regular Read Remark (NUW,
	 * PLUG, DEM) and Previous Meter Read Remark is any of Regular Read Remark
	 * (OK, PUMP, NUW, PLUG, DEM) then Current Meter Reading should always be
	 * equal to Last Registered Reading i.e. Previous Meter Read.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo12(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String previousMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterRead();
			/**
			 * In case of Current Meter Read Remark is any of Regular Read
			 * Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of
			 * Regular Read Remark (OK, PUMP, NUW, PLUG, DEM)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& ("NUW".equalsIgnoreCase(currentMeterReadRemark.trim())
							|| "PLUG".equalsIgnoreCase(currentMeterReadRemark
									.trim()) || "DEM"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
				/**
				 * Current Meter Reading should always be equal to Last
				 * Registered Reading.
				 */
				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */
				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Integer.parseInt(currentMeterRead.trim()) != Integer
							.parseInt(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Meter Read "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::Current Meter Reading should be equal to Previous Meter Read::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousMeterRead::"
												+ previousMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 13.</b><br>
	 * In case of Current Meter Read Remark is any of Regular Read Remark (NUW,
	 * PLUG, DEM) and Previous Meter Read Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then Current
	 * Meter Reading should always be equal to Actual Last Registered Reading.
	 * </p>
	 * 
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo13(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			String currentMeterRead = meterReadUploadDetails.getMeterRead();
			String previousActualMeterRead = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousActualMeterRead();
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark + "::currentMeterRead::"
			// + currentMeterRead + "::previousActualMeterRead::"
			// + previousActualMeterRead);
			/**
			 * In case of Current Meter Read Remark is any of Regular Read
			 * Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of
			 * Provisional Read Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM,
			 * ADF, RDDT, NS)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& ("NUW".equalsIgnoreCase(currentMeterReadRemark.trim())
							|| "PLUG".equalsIgnoreCase(currentMeterReadRemark
									.trim()) || "DEM"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))) {
				/**
				 * Current Meter Reading should always be equal to Actual Last
				 * Registered Reading.
				 */

				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */

				if (null != previousActualMeterRead
						&& !"".equalsIgnoreCase(previousActualMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousActualMeterRead
								.trim()) && null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Integer.parseInt(currentMeterRead.trim()) != Integer
							.parseInt(previousActualMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Actual Meter Read "
										+ previousActualMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::Current Meter Reading should be equal to Previous Actual Meter Read::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousActualMeterRead::"
												+ previousActualMeterRead
												+ "::currentMeterRead::"
												+ currentMeterRead);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 14.</b><br>
	 * In case Current Meter Reading Remark is any of Provisional Read Type
	 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) and Previous
	 * Meter Read Remark is any of Average Read Type (TEST, DFMT, STP, MTTM,
	 * MREV, REP, UNMT, BYPS) - this should not be allowed - Current Meter
	 * Reading Remark will be same as Previous Meter Reading Remark.
	 * </p>
	 * 
	 * @exception Exception
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo14(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark);
			/**
			 * In case Current Meter Reading Remark is any of Provisional Read
			 * Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS)
			 * and Previous Meter Read Remark is any of Average Read Type (TEST,
			 * DFMT, STP, MTTM, MREV, REP, UNMT, BYPS)
			 */
			if (null != previousMeterReadRemark
					&& !"NA".equalsIgnoreCase(previousMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode().indexOf(
									previousMeterReadRemark.trim()) > -1)
					&& null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * Current Meter Reading Remark will be same as Previous Meter
				 * Reading Remark.
				 */
				/**
				 * modified by mrityunjoy added new param KNO HHD-114:Upload
				 * service 7th March 2014 ErrorDetails constructor getKno has
				 * been added at the first
				 */
				if (null != previousMeterReadRemark
						&& !"".equalsIgnoreCase(previousMeterReadRemark.trim())
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& null != currentMeterReadRemark
						&& !(previousMeterReadRemark.trim())
								.equalsIgnoreCase(currentMeterReadRemark.trim())) {
					errorDetails = new ErrorDetails(
							meterReadUploadDetails.getKno(),
							"For KNO "
									+ meterReadUploadDetails.getKno()
									+ ",\nCurrent Meter Reading Remark Should be same as Previous Meter Reading Remark "
									+ previousMeterReadRemark + ".",
							BUSINESS_VALIDATION_ERROR);
					AppLog
							.info(
									sessionDetails,
									BUSINESS_VALIDATION_ERROR
											+ "::Current Meter Reading Remark Should be same as Previous Meter Reading Remark "
											+ previousMeterReadRemark
											+ "::previousMeterReadRemark::"
											+ previousMeterReadRemark
											+ "::currentMeterReadRemark::"
											+ currentMeterReadRemark);
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for Disconnection Cases</b>
	 * </p>
	 * <p>
	 * <b>Validation for point 19.</b><br>
	 * In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Read Remark is any of Regular Read Remark
	 * (OK, PUMP, NUW, PLUG, DEM) then no billing – disconnection case.
	 * </p>
	 * <p>
	 * <b>Validation for point 20.</b><br>
	 * In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Reading Remark is any of Provisional Read
	 * Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then no
	 * billing – disconnection case.
	 * </p>
	 * <p>
	 * <b>Validation for point 21.</b><br>
	 * In case Current Meter Read Remark is any of No Billing Read Type (CUT,
	 * NTRC, NOWC) and Previous Meter Reading Remark is any of Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then no billing –
	 * disconnection case.
	 * </p>
	 * 
	 * @exception Exception
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo192021(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String consumerStatus = meterReadUploadDetails.getConsumerStatus();
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			/**
			 *In case Current Meter Read Remark is any of No Billing Read Type
			 * (CUT, NTRC, NOWC)
			 */
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark);
			if (null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getNoBillingReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getNoBillingReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * Previous Meter Read Remark is any of Regular Read Remark (OK,
				 * PUMP, NUW, PLUG, DEM)<br>
				 * or<br>
				 * Previous Meter Reading Remark is any of Provisional Read Type
				 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS)<br>
				 * or<br>
				 * Previous Meter Reading Remark is any of Average Read Type
				 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS)
				 */

				if (null != previousMeterReadRemark
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& ((null != meterReadTypeLookUp.getRegularReadType()
								.getReadTypeCode() && (meterReadTypeLookUp
								.getRegularReadType().getReadTypeCode()
								.indexOf(previousMeterReadRemark.trim()) > -1))
								|| (null != meterReadTypeLookUp
										.getProvisionalReadType()
										.getReadTypeCode() && (meterReadTypeLookUp
										.getProvisionalReadType()
										.getReadTypeCode().indexOf(
												previousMeterReadRemark.trim()) > -1)) || (null != meterReadTypeLookUp
								.getAverageReadType().getReadTypeCode() && (meterReadTypeLookUp
								.getAverageReadType().getReadTypeCode()
								.indexOf(previousMeterReadRemark.trim()) > -1)))) {
					/**
					 * no billing – disconnection case.
					 */
					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */

					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.DISCONNECTED
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nThis is a disconnection case as Read Type Changed from "
						// + previousMeterReadRemark
						// + " to "
						// + currentMeterReadRemark
						// + ", Consumer Status Code Should be equal to "
						// + Integer
						// .toString(ConsumerStatusLookup.DISCONNECTED
						// .getStatusCode()),
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis seems to be a disconnection case as Read Type Changed from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", So, Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.DISCONNECTED
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::This is a disconnection case as Read Type Changed from "
												+ previousMeterReadRemark
												+ " to "
												+ currentMeterReadRemark
												+ "::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::consumerStatus::"
												+ consumerStatus);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for Re-Opening Cases</b>
	 * </p>
	 * <p>
	 * <b>Validation for point 22.</b><br>
	 * In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP, NUW, PLUG, DEM) and Previous Meter Read Remark is any of No Billing
	 * Read Type (CUT, NTRC, NOWC) then – reopening case.
	 * </p>
	 * <p>
	 * <b>Validation for point 23.</b><br>
	 * In case Current Meter Read Remark is any of Provisional Read Type (PLOC,
	 * NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) and Previous Meter
	 * Reading Remark is any of No Billing Read Type (CUT, NTRC, NOWC) then –
	 * reopening case.
	 * </p>
	 * <p>
	 * <b>Validation for point 24.</b><br>
	 * In case Current Meter Read Remark is any of Average Read Type (TEST,
	 * DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and Previous Meter Reading Remark
	 * is any of No Billing Read Type (CUT, NTRC, NOWC) then – reopening case.
	 * </p>
	 * 
	 * @exception Exception
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo222324(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String consumerStatus = meterReadUploadDetails.getConsumerStatus();
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String previousMeterReadRemark = toValidateConsumerDetail
					.getPreviousMeterReadDetails().getPreviousMeterReadStatus();
			// System.out.println("currentMeterReadRemark::"
			// + currentMeterReadRemark + "::previousMeterReadRemark::"
			// + previousMeterReadRemark);
			/**
			 *In case Current Meter Read Remark is any of Regular Read Remark
			 * (OK, PUMP, NUW, PLUG, DEM)
			 */

			if (null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * and Previous Meter Read Remark is any of No Billing Read Type
				 * (CUT, NTRC, NOWC)
				 */

				if (null != previousMeterReadRemark
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& (null != meterReadTypeLookUp.getNoBillingReadType()
								.getReadTypeCode() && (meterReadTypeLookUp
								.getNoBillingReadType().getReadTypeCode()
								.indexOf(previousMeterReadRemark.trim()) > -1))) {
					/**
					 * then – reopening case.
					 */

					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nThis is a Re-opening case as Change in Remark Code from "
						// + previousMeterReadRemark
						// + " to "
						// + currentMeterReadRemark
						// + ", Consumer Status Code Should be equal to "
						// + ConsumerStatusLookup.REOPENING
						// .getStatusCode(),
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis seems to be a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ",So, Consumer Status Code Should be equal to "
										+ ConsumerStatusLookup.REOPENING
												.getStatusCode(),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::This is a Re-opening case as Change in Remark Code from "
												+ previousMeterReadRemark
												+ " to "
												+ currentMeterReadRemark
												+ "::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::consumerStatus::"
												+ consumerStatus);
					}
				}
			}
			/**
			 *In case Current Meter Read Remark is any of Provisional Read Type
			 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS)
			 */

			if (null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * Previous Meter Read Remark is any of No Billing Read Type
				 * (CUT, NTRC, NOWC)
				 */

				if (null != previousMeterReadRemark
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& (null != meterReadTypeLookUp.getNoBillingReadType()
								.getReadTypeCode() && (meterReadTypeLookUp
								.getNoBillingReadType().getReadTypeCode()
								.indexOf(previousMeterReadRemark.trim()) > -1))) {
					/**
					 * then – reopening case.
					 */

					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */

					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nThis is a Re-opening case as Change in Remark Code from "
						// + previousMeterReadRemark
						// + " to "
						// + currentMeterReadRemark
						// + ", Consumer Status Code Should be equal to "
						// + Integer
						// .toString(ConsumerStatusLookup.REOPENING
						// .getStatusCode()),
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis seems to be a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ",So, Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.REOPENING
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::This is a Re-opening case as Change in Remark Code from "
												+ previousMeterReadRemark
												+ " to "
												+ currentMeterReadRemark
												+ "::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::consumerStatus::"
												+ consumerStatus);
					}
				}
			}
			/**
			 *In case Current Meter Read Remark is any of Average Read Type
			 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS)
			 */

			if (null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& null != meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getAverageReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)) {
				/**
				 * Previous Meter Reading Remark is any of No Billing Read Type
				 * (CUT, NTRC, NOWC)
				 */

				if (null != previousMeterReadRemark
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& (null != meterReadTypeLookUp.getNoBillingReadType()
								.getReadTypeCode() && (meterReadTypeLookUp
								.getNoBillingReadType().getReadTypeCode()
								.indexOf(previousMeterReadRemark.trim()) > -1))) {
					/**
					 * then – reopening case.
					 */

					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nThis is a Re-opening case as Change in Remark Code from "
						// + previousMeterReadRemark
						// + " to "
						// + currentMeterReadRemark
						// + ", Consumer Status Code Should be equal to "
						// + Integer
						// .toString(ConsumerStatusLookup.REOPENING
						// .getStatusCode()),
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis seems to be a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", So, Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.REOPENING
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::This is a Re-opening case as Change in Remark Code from "
												+ previousMeterReadRemark
												+ " to "
												+ currentMeterReadRemark
												+ "::previousMeterReadRemark::"
												+ previousMeterReadRemark
												+ "::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::consumerStatus::"
												+ consumerStatus);
					}
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * <b>Validation for point 25.</b><br>
	 * 
	 * In case Current Meter Read Remark is any of Provisional Read Type (PLOC,
	 * NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) or Average Read Type
	 * (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) and customer category is
	 * Cat IA , Cat II or Cat IIA and Previous Average Consumption is Zero or
	 * null , new Average Consumption is mandatory and should be filled.
	 * </p>
	 * 
	 * @exception Exception
	 * @see MeterReadUploadDetails
	 * @see ConsumerDetail
	 * @see MeterReadTypeLookUp
	 * @param meterReadUploadDetails
	 * @param toValidateConsumerDetail
	 * @param meterReadTypeLookUp
	 * @return reason for failure if failed else null
	 */
	public static ErrorDetails validatePointNo25(
			MeterReadUploadDetails meterReadUploadDetails,
			ConsumerDetail toValidateConsumerDetail,
			MeterReadTypeLookUp meterReadTypeLookUp,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String currentMeterReadRemark = meterReadUploadDetails
					.getMeterReadRemark();
			String currentAverageConsumption = meterReadUploadDetails
					.getAverageConsumption();
			String consumerCategory = toValidateConsumerDetail.getCategory();
			String previousAverageConsumption = toValidateConsumerDetail
					.getPreviousMeterReadDetails()
					.getPreviousAverageConsumption();
			/**
			 *In case Current Meter Read Remark is any of Provisional Read Type
			 * (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) or
			 * Average Read Type (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS)
			 */
			if (null != currentMeterReadRemark
					&& !"".equalsIgnoreCase(currentMeterReadRemark.trim())
					&& ((null != meterReadTypeLookUp.getProvisionalReadType()
							.getReadTypeCode() && (meterReadTypeLookUp
							.getProvisionalReadType().getReadTypeCode()
							.indexOf(currentMeterReadRemark.trim()) > -1)) || (null != meterReadTypeLookUp
							.getAverageReadType().getReadTypeCode() && (meterReadTypeLookUp
							.getAverageReadType().getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)))) {
				/**
				 * customer category is Cat IA , Cat II or Cat IIA
				 */
				if (null != consumerCategory
						&& !"".equalsIgnoreCase(consumerCategory)
						&& !"NA".equalsIgnoreCase(consumerCategory)
						&& ("CAT IA".equalsIgnoreCase(consumerCategory.trim())
								|| "CAT II".equalsIgnoreCase(consumerCategory
										.trim()) || "CAT IIA"
								.equalsIgnoreCase(consumerCategory.trim())))
					/**
					 * Previous Average Consumption is Zero or null , new
					 * Average Consumption is mandatory and should be filled.
					 */

					/**
					 * modified by mrityunjoy added new param KNO HHD-114:Upload
					 * service 7th March 2014 ErrorDetails constructor getKno
					 * has been added at the first
					 */
					if ((null == previousAverageConsumption
							|| "".equalsIgnoreCase(previousAverageConsumption)
							|| "NA"
									.equalsIgnoreCase(previousAverageConsumption) || "0"
							.equalsIgnoreCase(previousAverageConsumption))
							&& (null == currentAverageConsumption
									|| ""
											.equalsIgnoreCase(currentAverageConsumption)
									|| "NA"
											.equalsIgnoreCase(currentAverageConsumption) || "0"
									.equalsIgnoreCase(currentAverageConsumption))) {
						// errorDetails = new ErrorDetails(
						// meterReadUploadDetails.getKno(),
						// "For KNO "
						// + meterReadUploadDetails.getKno()
						// +
						// ",\nNew Average Consumption is mandatory for Non Domestic Consumer("
						// + consumerCategory + ").",
						// BUSINESS_VALIDATION_ERROR);
						errorDetails = new ErrorDetails(
								meterReadUploadDetails.getKno(),
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nNew Average Consumption is mandatory as this is Non Domestic Consumer("
										+ consumerCategory + ").",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(
										sessionDetails,
										BUSINESS_VALIDATION_ERROR
												+ "::New Average Consumption is mandatory for Non Domestic Consumer("
												+ consumerCategory
												+ ")::currentMeterReadRemark::"
												+ currentMeterReadRemark
												+ "::previousAverageConsumption::"
												+ previousAverageConsumption
												+ "::currentAverageConsumption::"
												+ currentAverageConsumption);
					}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
			errorDetails = null;
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}

	/**
	 * <p>
	 * Check if update is allowed to the details for the particular account of
	 * the consumer details.
	 * </p>
	 * 
	 * @param toValidateConsumerDetail
	 * @param toUpdateMeterReadUploadDetails
	 * @param userId
	 * @param taggedMRKeyList
	 * @return ErrorDetails if Permission denied else null
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-06-2014
	 */
	public static ErrorDetails validateUpdatePermission(
			ConsumerDetail toValidateConsumerDetail,
			MeterReadUploadDetails toUpdateMeterReadUploadDetails,
			String userId, String taggedMRKeyList, String meterReaderId,
			SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		ErrorDetails errorDetails = null;
		try {
			String mrkey = null != toValidateConsumerDetail ? toValidateConsumerDetail
					.getMrkey()
					: null;
			if (!hasAccessToMRD(taggedMRKeyList, mrkey, sessionDetails)) {
				String validationMessage = ACCESS_DENIED_MSG;
				if (null != validationMessage) {

					AppLog.info(sessionDetails, "For KNO "
							+ toUpdateMeterReadUploadDetails.getKno() + " "
							+ validationMessage + "TAGGED MRKEY::"
							+ taggedMRKeyList + "::MRKEY::" + mrkey);
					errorDetails = new ErrorDetails(
							toUpdateMeterReadUploadDetails.getKno(), "For KNO "
									+ toUpdateMeterReadUploadDetails.getKno()
									+ "\n" + validationMessage,
							BUSINESS_VALIDATION_ERROR);
				}
			} /*
			 * else if (!isUpdateAllowed(toValidateConsumerDetail,
			 * toUpdateMeterReadUploadDetails, userId)) { String
			 * validationMessage =
			 * NON_UPDATABLE_CONS_PRE_BILL_STAT_IDS_VALIDATION_MESSAGE ; if
			 * (null != validationMessage) { errorDetails = new ErrorDetails(
			 * toUpdateMeterReadUploadDetails.getKno(), "For KNO " +
			 * toUpdateMeterReadUploadDetails.getKno() + "\n" +
			 * validationMessage, BUSINESS_VALIDATION_ERROR); } }
			 */
			else {
				String validationMessage = msgIsUpdateAllowed(
						toValidateConsumerDetail,
						toUpdateMeterReadUploadDetails, userId, meterReaderId,
						sessionDetails);
				if (null != validationMessage) {
					AppLog.info(sessionDetails, "For KNO "
							+ toUpdateMeterReadUploadDetails.getKno() + " "
							+ validationMessage);
					errorDetails = new ErrorDetails(
							toUpdateMeterReadUploadDetails.getKno(), "For KNO "
									+ toUpdateMeterReadUploadDetails.getKno()
									+ "\n" + validationMessage,
							BUSINESS_VALIDATION_ERROR);
				}
			}
			AppLog.debugInfo(sessionDetails, null != errorDetails ? "FAILED"
					: "PASSED");
		} catch (Exception e) {
			AppLog.error(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
		return errorDetails;
	}
}
