/************************************************************************************************************
 * @(#) MeterReplacementValidator.java   15 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.MeterReadUploadErrorLogDetails;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DJBFieldValidatorUtil;
import com.tcs.djb.util.UtilityForAll;

/**
 * <P>
 * This class includes all the business validation logic used for Meter
 * Replacement.
 * </P>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-03-2013
 * @see AppLog
 * @history 30-03-2013 Matiur Rahman Commented validation for
 *          METER_INSTALLATION_DATE_VS_OLD_METER_INSTAL_DATE as per discussion
 *          with Apurb Sinha on 30-03-2013.
 * @history 30-03-2013 Matiur Rahman Updated validations and Messages as per
 *          Changes in Meter Replacement Process as per JTrac ID#2024.
 */
public class MeterReplacementValidator {
	/**
	 * <p>
	 * For any business or logical validation Error
	 * </p>
	 */
	// private static String BUSINESS_VALIDATION_ERROR =
	// "Business Validation Error";
	/**
	 * <p>
	 * If Meter Installation date before 01 Jan 2010 - System should show a soft
	 * warning stating 'Meter Installation date can not be before 1 Jan 2010'.
	 * In this scenario Data should be saved in system with status in Meter
	 * Replacement staging table for the record as 915.
	 * </p>
	 */
	private static String EARLIER_METER_INSTALLATION_DATE_BOUNDARY = "01/01/2010";
	/**
	 * <p>
	 * If Meter Installation date before 01 Jan 2010 - System should show a soft
	 * warning stating 'Meter Installation date can not be before 1 Jan 2010'.
	 * In this scenario Data should be saved in system with status in Meter
	 * Replacement staging table for the record as 915.
	 * </p>
	 */
	/*
	 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in
	 * Use Now.
	 */
	// private static String EARLIER_METER_INSTALLATION_DATE_STATUS_CODE =
	// "915";
	/*
	 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in Use
	 * Now.
	 */
	/**
	 * <p>
	 * If Old SA Start date before 01 Jan 2010 - Data should be saved in system
	 * with status in Meter Replacement staging table for the record as 915.
	 * </p>
	 */
	private static String EARLIER_OLD_SA_DATE_BOUNDARY = "01/01/2010";
	/**
	 * <p>
	 * If Old SA Start date before 01 Jan 2010 or If the consumer is
	 * disconnected - Data should be saved in system with status in Meter
	 * Replacement staging table for the record as 915.
	 * </p>
	 */
	/*
	 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in
	 * Use Now.
	 */
	// private static String EARLIER_OLD_SA_STATUS_CODE = "915";
	/*
	 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in Use
	 * Now.
	 */
	/**
	 * <p>
	 * For Invalid input parameter validation Error
	 * </p>
	 */
	private static String FIELD_VALIDATION_ERROR = "Field Validation Error";

	/**
	 * <p>
	 * If meter installation date in future- System should show a soft warning
	 * stating the 'Meter Installation Date can not be in future' and status for
	 * the record should be updated to 1200 in Meter Replacement staging table.
	 * Data should be saved in system.
	 * </p>
	 */
	/*
	 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in
	 * Use Now.
	 */
	// private static String FUTURE_METER_INSTALLATION_DATE_STATUS_CODE =
	// "1200";
	/*
	 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in Use
	 * Now.
	 */
	/**
	 * <p>
	 * 1.System should show a soft warning stating the record will not be
	 * processed as Meter Installation date is same as Old Meter Installation
	 * Date. In this scenario the status in Meter Replacement staging table for
	 * the record should be updated to 960.
	 * </p>
	 * <p>
	 * 2.System should show a soft warning stating the record will not be
	 * processed as Meter Installation date lies within previous one month of
	 * Old Meter Installation Date.. In this scenario the status in Meter
	 * Replacement staging table for the record should be updated to 960.
	 * </p>
	 * <p>
	 * 3.System should show a soft warning stating the record will not be
	 * processed as Meter Installation Date lies within next one month of Old
	 * Meter Installation Date. In this scenario the status in Meter Replacement
	 * staging table for the record should be updated to 960.
	 * </p>
	 * <p>
	 * 4.System should show a soft warning stating the record will not be
	 * processed as Meter Installation Date lies beyond previous one month of
	 * Old Meter Installation Date. In this scenario the status in Meter
	 * Replacement staging table for the record should be updated to 960.
	 * </p>
	 * <b>Point 2&4 merged to summarize as Meter Installation Date should not be
	 * earlier than Last Active Meter Install date. In this scenario the status
	 * in Meter Replacement staging table for the record should be updated to
	 * 960.</b>
	 */
	/*
	 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in
	 * Use Now.
	 */
	// private static String
	// METER_INSTALLATION_DATE_VS_OLD_METER_INSTAL_DATE_STATUS_CODE = "960";
	/*
	 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman as not in Use
	 * Now.
	 */

	/**
	 * <p>
	 * This method validates all the parameter of the Meter read Upload Process
	 * via the Web service. meterReadUploadRequest contains all the details need
	 * to Upload.
	 * </p>
	 * 
	 * @param meterReadUploadRequest
	 *            contains details of the MRD that need to be uploaded
	 * @return returnMap which contains meterReadErrorList and
	 *         toUploadMeterReadDetailsList
	 * @see ErrorDetails
	 * @see MeterReadUploadDetails
	 * @see MeterReadUploadErrorLogDetails
	 */
	public static Map<Object, Object> validateMeterReplacementUploadFields(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		ErrorDetails meterReplacementError = null;
		List<ErrorDetails> meterReplacementErrorList = new ArrayList<ErrorDetails>();
		try {
			boolean validSet = true;
			// System.out.println("::MeterReaderName::"
			// + meterReplacementDetail.getMeterReaderName()
			// + "Bill Round::" + meterReplacementDetail.getBillRound()
			// + "::Zone::" + meterReplacementDetail.getZone()
			// + "::MrNo::" + meterReplacementDetail.getMrNo()
			// + "::Area::" + meterReplacementDetail.getArea() + "::KNO::"
			// + meterReplacementDetail.getKno() + "::Name::"
			// + meterReplacementDetail.getName() + "::consumerCategory::"
			// + meterReplacementDetail.getConsumerCategory()
			// + "::meterReplaceStageID::"
			// + meterReplacementDetail.getMeterReplaceStageID()
			// + "::WaterConnectionUse::"
			// + meterReplacementDetail.getWaterConnectionUse()
			// + "::WaterConnectionSize::"
			// + meterReplacementDetail.getWaterConnectionSize()
			// + "::LastActiveMeterInstalDate::"
			// + meterReplacementDetail.getLastActiveMeterInstalDate()
			// + "::NextValidMeterInstalldate::"
			// + meterReplacementDetail.getNextValidMeterInstalldate()
			// + "::Manufacturer::"
			// + meterReplacementDetail.getManufacturer() + "::Model::"
			// + meterReplacementDetail.getModel() + "::MeterType::"
			// + meterReplacementDetail.getMeterType() + "::BadgeNo::"
			// + meterReplacementDetail.getBadgeNo() + "::SaType::"
			// + meterReplacementDetail.getSaType()
			// + "::MeterInstalDate::"
			// + meterReplacementDetail.getMeterInstalDate()
			// + "::ZeroReadRemarkCode::"
			// + meterReplacementDetail.getZeroReadRemarkCode()
			// + "::ZeroRead::" + meterReplacementDetail.getZeroRead()
			// + "::OldSAType::" + meterReplacementDetail.getOldSAType()
			// + "::OldSAStartDate::"
			// + meterReplacementDetail.getOldSAStartDate()
			// + "::OldMeterReadRemarkCode::"
			// + meterReplacementDetail.getOldMeterReadRemarkCode()
			// + "::OldMeterReadDate::"
			// + meterReplacementDetail.getOldMeterReadDate()
			// + "::OldMeterRegisterRead::"
			// + meterReplacementDetail.getOldMeterRegisterRead()
			// + "::OldMeterAverageRead::"
			// + meterReplacementDetail.getOldMeterAverageRead());
			String validationMessage = null;
			String errorMessage = "";

			// ==================================================================
			// Not Inserted/Updated in CM_MTR_RPLC_STAGE. Updated in
			// CM_CONS_PRE_BILL_PROC.
			// =============================Start================================
			// private String consumerType;
			// private String currentAverageConsumption;
			// private String currentMeterReadDate;
			// private String currentMeterReadRemarkCode;
			// private double currentMeterRegisterRead;
			// private double newAverageConsumption
			// private String noOfFloors;
			// =============================End=================================
			// =================================================================
			// Inserted/Updated in CM_MTR_RPLC_STAGE
			// =============================Start===============================
			// MRKEY VARCHAR2(254) by default populated by zone mr area
			// combination ,
			// area AREANO CHAR(10),;
			// badgeNo BADGE_NBR CHAR(30),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Badge No", meterReplacementDetail.getBadgeNo(), 30, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// billRound BILL_ROUND_ID VARCHAR2(10) not null,
			validationMessage = DJBFieldValidatorUtil.checkBillCycle(
					"Bill Round", meterReplacementDetail.getBillRound(), 10,
					false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// consumerCategory CUST_CL_CD CHAR(8),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Consumer Category", meterReplacementDetail
							.getConsumerCategory(), 8, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// createDate CREATE_DTTM TIMESTAMP(6) default SYSDATE,
			// createdByID CREATED_BY VARCHAR2(254) default 'DBA',
			// isLNTServiceProvider IS_LNT_SRVC_PRVDR CHAR(10),
			// kno ACCT_ID CHAR(10) not null,
			validationMessage = DJBFieldValidatorUtil.checkNumeric("KNO",
					meterReplacementDetail.getKno(), 10, false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// lastUpdateDate LAST_UPDT_DTTM TIMESTAMP(6),
			// lastUpdatedByID LAST_UPDT_BY VARCHAR2(254),
			// manufacturer MFG_CD VARCHAR2(50),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Manufacturer", meterReplacementDetail.getManufacturer(),
					50, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// System.out.println("OldSAStartDate::"
			// + meterReplacementDetail.getOldSAStartDate());
			// meterInstalDate MTR_INSTALL_DATE DATE not null,
			validationMessage = DJBFieldValidatorUtil.checkDate(
					"Meter Instal Date", meterReplacementDetail
							.getMeterInstalDate(), 10, false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			} else if (UtilityForAll.checkFutureDate(meterReplacementDetail
					.getMeterInstalDate()) > 0) {
				/** Meter Read Date Should not be a Future Date. */
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(FUTURE_METER_INSTALLATION_DATE_STATUS_CODE);
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				validSet = false;
				errorMessage = errorMessage + "\n"
						+ DJBConstants.FUTURE_INSTALLATION_DATE_ERROR_MSG;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				// System.out
				// .println("Meter Read Date Should not be a Future Date");

			} else if (UtilityForAll.compareDates(meterReplacementDetail
					.getMeterInstalDate(),
					EARLIER_METER_INSTALLATION_DATE_BOUNDARY) == -1) {
				/**
				 * <p>
				 * If Meter Installation date before 01 Jan 2010 - System should
				 * show a soft warning stating 'Meter Installation date can not
				 * be before 1 Jan 2010'. In this scenario Data should be saved
				 * in system with status in Meter Replacement staging table for
				 * the record as 915.
				 * </p>
				 */
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(EARLIER_METER_INSTALLATION_DATE_STATUS_CODE);
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				validSet = false;
				errorMessage = errorMessage
						+ "\n"
						+ DJBConstants.METER_INSTALLATION_DATE_BEFORE_JAN_2010_ERROR_MSG;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				// System.out
				// .println("Meter Read Date Should not be before 01 Jan 2010 or the consumer is a disconnected consumer");
			} else if (null != meterReplacementDetail
					.getLastActiveMeterInstalDate()
					&& meterReplacementDetail.getLastActiveMeterInstalDate()
							.trim().length() == 10
					&& null != meterReplacementDetail.getMeterInstalDate()
					&& meterReplacementDetail.getMeterInstalDate().length() == 10
					&& UtilityForAll.compareDates(meterReplacementDetail
							.getMeterInstalDate(), meterReplacementDetail
							.getLastActiveMeterInstalDate()) == -1) {
				/**
				 * <p>
				 * 2.System should show a soft warning stating the record will
				 * not be processed as Meter Installation date lies within
				 * previous one month of Old Meter Installation Date.. In this
				 * scenario the status in Meter Replacement staging table for
				 * the record should be updated to 960.
				 * </p>
				 * <p>
				 * 4.System should show a soft warning stating the record will
				 * not be processed as Meter Installation Date lies beyond
				 * previous one month of Old Meter Installation Date. In this
				 * scenario the status in Meter Replacement staging table for
				 * the record should be updated to 960.
				 * </p>
				 * <b>This two point merged to summarize as Meter Installation
				 * Date should not be earlier than Last Active Meter Install
				 * date. In this scenario the status in Meter Replacement
				 * staging table for the record should be updated to 960.</b>
				 */
				/**
				 * Commented as to ignore this validation as per discussion with
				 * Apurb Sinha on 30-03-2013.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(METER_INSTALLATION_DATE_VS_OLD_METER_INSTAL_DATE_STATUS_CODE);
			} else if (null != meterReplacementDetail.getMeterInstalDate()
					&& meterReplacementDetail.getMeterInstalDate().trim()
							.length() == 10
					&& meterReplacementDetail.getMeterInstalDate()
							.equalsIgnoreCase(
									meterReplacementDetail
											.getLastActiveMeterInstalDate())) {
				/**
				 * <p>
				 * 1.System should show a soft warning stating the record will
				 * not be processed as Meter Installation date is same as Old
				 * Meter Installation Date. In this scenario the status in Meter
				 * Replacement staging table for the record should be updated to
				 * 960.
				 * </p>
				 */
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(METER_INSTALLATION_DATE_VS_OLD_METER_INSTAL_DATE_STATUS_CODE);
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				validSet = false;
				errorMessage = errorMessage
						+ "\n"
						+ DJBConstants.METER_INSTALLATION_DATE_SAME_AS_OLD_METER_INSTALL_DATE_ERROR_MSG;
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				// System.out
				// .println("Meter Read Date Should not be same as Old Meter Installation Date.");
			} else if (null != meterReplacementDetail.getMeterInstalDate()
					&& meterReplacementDetail.getMeterInstalDate().trim()
							.length() == 10
					&& null != meterReplacementDetail
							.getNextValidMeterInstalldate()
					&& meterReplacementDetail.getNextValidMeterInstalldate()
							.length() == 10
					&& UtilityForAll.compareDates(meterReplacementDetail
							.getMeterInstalDate(), meterReplacementDetail
							.getNextValidMeterInstalldate()) == -1) {
				/**
				 * <p>
				 * 3.System should show a soft warning stating the record will
				 * not be processed as Meter Installation Date lies within next
				 * one month of Old Meter Installation Date. In this scenario
				 * the status in Meter Replacement staging table for the record
				 * should be updated to 960.
				 * </p>
				 */
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(METER_INSTALLATION_DATE_VS_OLD_METER_INSTAL_DATE_STATUS_CODE);
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// System.out
				// .println("Meter Read Date Should not lie within next one month of Old Meter Installation Date.");
			}
			if (null == meterReplacementDetail.getOldSAType()
					|| meterReplacementDetail.getOldSAType().length() == 0
					|| null == meterReplacementDetail.getOldSAStartDate()
					|| meterReplacementDetail.getOldSAStartDate().length() != 10
					|| UtilityForAll.compareDates(meterReplacementDetail
							.getOldSAStartDate(), EARLIER_OLD_SA_DATE_BOUNDARY) == -1) {
				/**
				 * <p>
				 * If Old SA Start date before 01 Jan 2010 or the consumer is a
				 * disconnected consumer Data should be saved in system with
				 * status in Meter Replacement staging table for the record as
				 * 915.
				 * </p>
				 */
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				// meterReplacementDetail
				// .setMeterReplaceStageID(EARLIER_OLD_SA_STATUS_CODE);
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Commented by Matiur Rahman
				 * for the new validation.
				 */
				/*
				 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */
				validSet = false;
				errorMessage = errorMessage
						+ "\n"
						+ DJBConstants.DISCONNECTED_OR_SA_START_DATE_BEFORE_JAN_2010_ERROR_MSG;
				/*
				 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for
				 * the new validation.
				 */

			}
			// meterReaderName MTR_READER_ID VARCHAR2(254),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Meter Reader Name", meterReplacementDetail
							.getMeterReaderName(), 254, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// meterReplaceStageID MTR_RPLC_STAGE_ID NUMBER(8),
			// meterType MTRTYP CHAR(16),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Meter Type", meterReplacementDetail.getMeterType(), 16,
					true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// model MODEL_CD CHAR(8),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Model", meterReplacementDetail.getModel(), 8, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// mrNo MRNO CHAR(25),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"MR No", meterReplacementDetail.getMrNo(), 25, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// name CONSUMER_NAME VARCHAR2(254),
			// oldMeterAverageRead OLDAVGCONS VARCHAR2(254) default 0,
			validationMessage = DJBFieldValidatorUtil.checkAmount(
					"Old Meter Average Read", Double
							.toString(meterReplacementDetail
									.getOldMeterAverageRead()), 22, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// oldMeterReadDate OLD_MTR_READ_DATE DATE,
			validationMessage = DJBFieldValidatorUtil.checkDate(
					"Old Meter Read Date", meterReplacementDetail
							.getOldMeterReadDate(), 10, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// oldMeterReadRemarkCode READER_REM_CD CHAR(4),
			validationMessage = DJBFieldValidatorUtil.checkAlphabet(
					"Old Meter Read Remark Code", meterReplacementDetail
							.getOldMeterReadRemarkCode(), 4, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// oldMeterRegisterRead OLD_MTR_READ NUMBER(15,6) default 0.00,
			validationMessage = DJBFieldValidatorUtil.checkAmount(
					"Old Meter Register Read", Double
							.toString(meterReplacementDetail
									.getOldMeterRegisterRead()), 22, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// else {
			// if (meterReplacementDetail.getOldMeterRegisterRead() == 0.00) {
			// meterReplacementDetail.setOldMeterReadRemarkCode("DFMT");
			// }
			// }
			// oldSAStartDate OLD_SA_START_DATE DATE,
			// oldSAType OLD_SA_TYPE_CD CHAR(8),
			// saType SA_TYPE_CD CHAR(8),;
			// waterConnectionSize WCONSIZE CHAR(16),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Water Connection Size", meterReplacementDetail
							.getWaterConnectionSize(), 16, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// waterConnectionUse WCONUSE VARCHAR2(254),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Water Connection Use", meterReplacementDetail
							.getWaterConnectionUse(), 254, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// wcNo WCNO VARCHAR2(25),
			validationMessage = DJBFieldValidatorUtil.checkAllowedSpecialChar(
					"Water Connection No", meterReplacementDetail.getWcNo(),
					25, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// zeroRead MTR_START_READ NUMBER(15,6) default 0.00,
			validationMessage = DJBFieldValidatorUtil.checkAmount("Zero Read",
					Double.toString(meterReplacementDetail.getZeroRead()), 22,
					true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// zeroReadRemarkCode MTR_START_READ_REMARK CHAR(4),
			validationMessage = DJBFieldValidatorUtil.checkAlphabet(
					"Zero Read Remark Code", meterReplacementDetail
							.getZeroReadRemarkCode(), 4, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			// zone ZONECD CHAR(25),
			// DE_CODE_VRSN VARCHAR2(20),
			// =============================End=================================
			// =================================================================
			// Not Inserted/Updated in CM_MTR_RPLC_STAGE
			// =============================Start===============================

			// ADDRESS1 VARCHAR2(254),
			// ADDRESS2 VARCHAR2(254),
			// MTR_LOC_CD CHAR(20),
			// NO_OF_DGTS_LEFT NUMBER(2) default 0,
			// NO_OF_DGTS_RIGHT NUMBER(2) default 0,
			// MTR_FULL_SCALE NUMBER(18,7) default 0,
			// SERIAL_NBR VARCHAR2(16),
			// OP_AREA_CD CHAR(20),
			// STK_LOC_CD CHAR(12),
			// REG_CONST NUMBER(12,6),
			// REPLACE_DTTM TIMESTAMP(6),
			// LAST_OK_DT DATE,
			// LAST_OK_READ NUMBER(10),
			// LAST_STATUS NUMBER(5),
			// TBP NUMBER(10,2),
			// PYMNT NUMBER(10,2),
			// ADJ_DEL NUMBER(10,2),
			// OLD_ZONECD CHAR(25),
			// OLD_MRNO CHAR(25),
			// OLD_AREANO CHAR(10),
			// OLD_MRKEY VARCHAR2(254),
			// VRSN VARCHAR2(20),
			// ADJ_FA NUMBER(10,2),
			// ADJ_FIN NUMBER(10,2)
			// =============================End=================================
			if (validSet) {
				returnMap.put("meterReplacementDetail", meterReplacementDetail);
			} else {
				if (null != meterReplacementDetail.getKno()
						&& !"".equalsIgnoreCase(meterReplacementDetail.getKno()
								.trim())) {
					meterReplacementError = new ErrorDetails("For KNO "
							+ meterReplacementDetail.getKno() + errorMessage,
							FIELD_VALIDATION_ERROR);
				} else {
					meterReplacementError = new ErrorDetails(errorMessage,
							FIELD_VALIDATION_ERROR);
				}
				meterReplacementErrorList.add(meterReplacementError);
				// meterReadUploadErrorLogDetailsList
				// .add(new MeterReadUploadErrorLogDetails(meterReadError,
				// meterReadUploadDetails));
			}
			// System.out.println("meterReadErrorList::"
			// + meterReadErrorList.size()
			// + "::toUploadMeterReadDetailsList::"
			// + toUploadMeterReadDetailsList.size());
			// returnMap.put("meterReplacementDetail", meterReplacementDetail);
			returnMap.put("meterReplacementErrorList",
					meterReplacementErrorList);
			/*
			 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the
			 * new validation.
			 */
			returnMap.put("meterReplacementError", meterReplacementError);
			/*
			 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the
			 * new validation.
			 */
			// returnMap.put("toUploadMeterReadDetailsList",
			// toUploadMeterReadDetailsList);

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnMap;
	}

	/**
	 * <p>
	 * Set the default values for meter Replacement Detail.
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return meterReplacementDetail after setting default values.
	 */
	public static MeterReplacementDetail setDefaultValues(
			MeterReplacementDetail meterReplacementDetail) {
		// meterReplacementDetail.setMeterReaderName(meterReaderName);
		// meterReplacementDetail.setBillRound(billRound);
		// meterReplacementDetail.setZone(zone);
		// meterReplacementDetail.setMrNo(mrNo);
		// meterReplacementDetail.setArea(area);
		// meterReplacementDetail.setWcNo(wcNo);
		// meterReplacementDetail.setKno(kno);
		// meterReplacementDetail.setName(name);
		// meterReplacementDetail.setConsumerType(consumerType);
		// meterReplacementDetail.setConsumerCategory(consumerCategory);
		// meterReplacementDetail.setWaterConnectionSize(waterConnectionSize);
		// meterReplacementDetail.setWaterConnectionUse(waterConnectionUse);
		// meterReplacementDetail.setMeterType(meterType);
		if (null != meterReplacementDetail.getBadgeNo()
				&& !"".equalsIgnoreCase(meterReplacementDetail.getBadgeNo()
						.trim())
				&& !"NA".equalsIgnoreCase(meterReplacementDetail.getBadgeNo()
						.trim())) {
			meterReplacementDetail.setBadgeNo(""
					+ meterReplacementDetail.getBadgeNo().trim());
		} else {
			meterReplacementDetail.setBadgeNo("M"
					+ meterReplacementDetail.getKno().trim());
		}
		// meterReplacementDetail.setManufacturer(manufacturer);
		// meterReplacementDetail.setModel(model);
		// meterReplacementDetail.setSaType(saType);
		// meterReplacementDetail.setIsLNTServiceProvider(isLNTServiceProvider);
		// meterReplacementDetail.setMeterInstalDate(meterInstalDate);
		// meterReplacementDetail.setZeroRead(zeroRead);
		// meterReplacementDetail.setCurrentMeterReadDate(currentMeterReadDate);
		// meterReplacementDetail
		// .setCurrentMeterReadRemarkCode(currentMeterReadRemarkCode);
		// meterReplacementDetail
		// .setCurrentMeterRegisterRead(currentMeterRegisterRead);
		// meterReplacementDetail.setNewAverageConsumption(newAverageConsumption);
		// meterReplacementDetail.setOldMeterRegisterRead((null==meterReplacementDetail.getOldMeterRegisterRead()||"".equalsIgnoreCase(meterReplacementDetail.getOldMeterRegisterRead())?"":meterReplacementDetail.getOldMeterRegisterRead());
		meterReplacementDetail
				.setOldMeterReadRemarkCode((null == meterReplacementDetail
						.getOldMeterReadRemarkCode() || ""
						.equalsIgnoreCase(meterReplacementDetail
								.getOldMeterReadRemarkCode().trim())) ? "DFMT"
						: meterReplacementDetail.getOldMeterReadRemarkCode()
								.trim());
		// Added By matiur Rahman 11-12-2012
		// meterReplacementDetail.setOldMeterAverageRead(oldMeterAverageRead);
		// ==================================
		// meterReplacementDetail.setCreatedByID(userId);
		// Setting default values
		// if (null != meterReplacementDetail.getConsumerType()
		// && "METERED".equals(meterReplacementDetail.getConsumerType()
		// .trim())) {
		meterReplacementDetail.setMeterReplaceStageID("350");
		// } else if (null != meterReplacementDetail.getConsumerType()
		// && "UNMETERED".equals(meterReplacementDetail.getConsumerType()
		// .trim())) {
		// meterReplacementDetail.setMeterReplaceStageID("10");
		// }
		if (null == meterReplacementDetail.getSaType()
				|| "NA".equals(meterReplacementDetail.getSaType().trim())) {
			if (null != meterReplacementDetail.getConsumerType()
					&& "UNMETERED".equals(meterReplacementDetail
							.getConsumerType().trim())) {
				meterReplacementDetail.setSaType("SAWATSEW");
			} else {
				meterReplacementDetail.setSaType("NA");
			}
		}
		if (null == meterReplacementDetail.getWaterConnectionSize()
				|| "".equals(meterReplacementDetail.getWaterConnectionSize()
						.trim())) {
			meterReplacementDetail.setWaterConnectionSize("15");
		}
		if (null == meterReplacementDetail.getManufacturer()
				|| "".equals(meterReplacementDetail.getManufacturer().trim())) {
			meterReplacementDetail.setManufacturer("N/A");
		}
		if (null == meterReplacementDetail.getModel()
				|| "".equals(meterReplacementDetail.getModel().trim())) {
			meterReplacementDetail.setModel("N/A W");
		}
		if (null == meterReplacementDetail.getIsLNTServiceProvider()
				|| "".equals(meterReplacementDetail.getIsLNTServiceProvider()
						.trim())) {
			meterReplacementDetail.setIsLNTServiceProvider("NA");
		}
		return meterReplacementDetail;
	}
}
