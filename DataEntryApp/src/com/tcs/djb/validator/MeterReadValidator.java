/************************************************************************************************************
 * @(#) DJBFieldValidatorUtil.java   14 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MeterReadTypeLookUp;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.MeterReadUploadErrorLogDetails;
import com.tcs.djb.rms.model.BillGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DJBFieldValidatorUtil;
import com.tcs.djb.util.UtilityForAll;

/**
 * <p>
 * This contains all the validation methods used in the Meter Read Web service.
 * ErrorDetails contains all the error details found during the validation.
 * </p>
 * 
 * @see ErrorDetails
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 * @history 11-07-2013 Matiur Rahman modified method
 *          {@link #validateMeterReadUploadFields()} to by pass Mandatory filed
 *          validation for "Meter Read Remark" and "Meter Read" for Unmetered
 *          consumer.
 * @history 11-07-2013 Matiur Rahman Edited checking meterRead and
 *          meterReadRemark as mandatory fields only for <code>METERED</code>
 *          consumers. This mandatory validation is checked now in
 *          {@link #validateMeterReadUploadAsPerBusinessProcess()} instead of
 *          {@link #validateMeterReadUploadFields()}.
 * @history 11-07-2013 Matiur Rahman Edited method
 *          {@link #validatePointNo0708()} .This validation should be only for
 *          current meter Read Remark OK and PUMP.
 * 
 */
public class MeterReadValidator {
	/**
	 * <p>
	 * For any business or logical validation Error
	 * </p>
	 */

	private static String BUSINESS_VALIDATION_ERROR = "Business Validation Error";
	/**
	 * <p>
	 * For Invalid input parameter validation Error
	 * </p>
	 */
	private static String FIELD_VALIDATION_ERROR = "Field Validation Error";
	/**
	 * <p>
	 * SA_TYPE FOR <code>METERED</code> consumers.
	 * </p>
	 */
	private static String METERED_CONSUMER_SA_TYPE = "METERED";

	/**
	 * <p>
	 * Status code for regular type consumer.
	 * </p>
	 */
	// private static String REGULAR_TYPE_CONSUMER_STATUS_CODE = "60";

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
	 */
	public static ErrorDetails[] validateMeterReadDownload(
			MRDContainer mrdContainer) {
		AppLog.begin();
		ErrorDetails[] errorDetailsArray = null;
		try {
			AppLog.info("Zone" + mrdContainer.getZone() + "::MR No::"
					+ mrdContainer.getMrNo() + "::Area::"
					+ mrdContainer.getAreaNo() + "::Bill Round::"
					+ mrdContainer.getBillRoundId());
			ErrorDetails meterReadError = null;
			List<ErrorDetails> meterReadErrorList = new ArrayList<ErrorDetails>();
			if (null == mrdContainer.getZone()
					|| "".equals(mrdContainer.getZone().trim())
					|| "?".equals(mrdContainer.getZone().trim())) {
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
			if (null == mrdContainer.getBillRoundId()
					|| "".equals(mrdContainer.getBillRoundId().trim())
					|| "?".equals(mrdContainer.getBillRoundId().trim())) {
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
			AppLog.error(e);
		}
		AppLog.end();
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
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> validateMeterReadUploadAsPerBusinessProcess(
			HashMap<Object, Object> inputMap) {
		AppLog.begin();
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
					// if (REGULAR_TYPE_CONSUMER_STATUS_CODE
					// .equalsIgnoreCase(toUpdateMeterReadUploadDetails
					// .getConsumerStatus())) {

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
							ErrorDetails errorDetails = null;
							int errorDetected = 0;
							/**
							 * Added by Matiur Rahman To validate
							 * meterReadRemark and meterRead fields as mandatory
							 * for <code>METERED</code> consumers.
							 */
							if (METERED_CONSUMER_SA_TYPE
									.equalsIgnoreCase(toValidateConsumerDetail
											.getConsumerType())) {
								String validationMessage = null;
								String errorMessage = "";
								validationMessage = DJBFieldValidatorUtil
										.checkAlphabet("Meter Read Remark",
												toUpdateMeterReadUploadDetails
														.getMeterReadRemark(),
												5, false);
								if (null != validationMessage) {
									errorMessage = errorMessage + "\n"
											+ validationMessage;
									errorDetails = new ErrorDetails("For KNO "
											+ toUpdateMeterReadUploadDetails
													.getKno() + errorMessage,
											FIELD_VALIDATION_ERROR);
								}
								validationMessage = DJBFieldValidatorUtil
										.checkAmount("Meter Read",
												toUpdateMeterReadUploadDetails
														.getMeterRead(), 10,
												false);
								if (null != validationMessage) {
									errorMessage = errorMessage + "\n"
											+ validationMessage;
									errorDetails = new ErrorDetails("For KNO "
											+ toUpdateMeterReadUploadDetails
													.getKno() + errorMessage,
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
							// Validate Point No.1
							errorDetails = validatePointNo01(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.4
							errorDetails = validatePointNo04(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
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
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.9
							errorDetails = validatePointNo09(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.10
							errorDetails = validatePointNo10(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.11
							errorDetails = validatePointNo11(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.12
							errorDetails = validatePointNo12(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.13
							errorDetails = validatePointNo13(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.14
							errorDetails = validatePointNo14(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.19,20 and 21
							errorDetails = validatePointNo192021(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.22,23 and 24
							errorDetails = validatePointNo222324(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
							}
							// Validate Point No.25
							errorDetails = validatePointNo25(
									toUpdateMeterReadUploadDetails,
									toValidateConsumerDetail,
									meterReadTypeLookUp);
							if (null != errorDetails) {
								meterReadErrorList.add(errorDetails);
								meterReadUploadErrorLogDetailsList
										.add(new MeterReadUploadErrorLogDetails(
												errorDetails,
												toUpdateMeterReadUploadDetails));
								errorDetected++;
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
					// } else {
					// filteredToUploadMeterReadDetailsList
					// .add(toUpdateMeterReadUploadDetails);
					// }
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
			AppLog.error(e);
		}
		AppLog.end();
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
	 * @return returnMap which contains meterReadErrorList and
	 *         toUploadMeterReadDetailsList
	 * @see ErrorDetails
	 * @see MeterReadUploadDetails
	 * @see MeterReadUploadErrorLogDetails
	 */
	public static Map<Object, Object> validateMeterReadUploadFields(
			MeterReadUploadDetails[] meterReadUploadDetailsArray) {
		AppLog.begin();
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
				validationMessage = DJBFieldValidatorUtil.checkBillCycle(
						"Bill Round", meterReadUploadDetails.getBillRound(),
						10, false);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkNumeric("KNO",
						meterReadUploadDetails.getKno(), 10, false);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkNumeric(
						"Consumer Status", meterReadUploadDetails
								.getConsumerStatus(), 3, false);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkDate(
						"Meter Read Date", meterReadUploadDetails
								.getMeterReadDate(), 10, false);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				} else if (UtilityForAll.checkFutureDate(meterReadUploadDetails
						.getMeterReadDate()) > 0) {
					validSet = false;
					errorMessage = errorMessage
							+ "\nMeter Read Date Should not be a Future Date.";
				}
				/**
				 * Only considering the rest of the validation for Regular Type
				 * consumer.
				 */
				// if (REGULAR_TYPE_CONSUMER_STATUS_CODE
				// .equalsIgnoreCase(meterReadUploadDetails
				// .getConsumerStatus())) {
				validationMessage = DJBFieldValidatorUtil.checkAlphabet(
						"Meter Read Remark", meterReadUploadDetails
								.getMeterReadRemark(), 5, true);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkAmount(
						"Meter Read", meterReadUploadDetails.getMeterRead(),
						10, true);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkAmount(
						"Average Consumption", meterReadUploadDetails
								.getAverageConsumption(), 10, true);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				validationMessage = DJBFieldValidatorUtil.checkNumeric(
						"No Of Floors", meterReadUploadDetails.getNoOfFloors(),
						3, true);
				if (null != validationMessage) {
					validSet = false;
					errorMessage = errorMessage + "\n" + validationMessage;
				}
				// }
				if (validSet) {
					toUploadMeterReadDetailsList.add(meterReadUploadDetails);
				} else {
					if (null != meterReadUploadDetails.getKno()
							&& !"".equalsIgnoreCase(meterReadUploadDetails
									.getKno().trim())) {
						meterReadError = new ErrorDetails("For KNO "
								+ meterReadUploadDetails.getKno()
								+ errorMessage, FIELD_VALIDATION_ERROR);
					} else {
						meterReadError = new ErrorDetails("Record No "
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
			AppLog.error(e);
		}
		AppLog.end();
		return returnMap;
	}

	/**
	 * <p>
	 * validateMeterReadUploadFieldsForSingleBillGeneration used to validated
	 * the data from input
	 * </p>
	 */

	public static Map<Object, Object> validateMeterReadUploadFieldsForSingleBillGeneration(
			BillGenerationDetails billGenerationDetails) {
		AppLog.begin();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		ErrorDetails meterReadError = null;
		List<ErrorDetails> billGenerationErrorList = new ArrayList<ErrorDetails>();
		List<BillGenerationDetails> billGenerationMeterReadDetailsList = new ArrayList<BillGenerationDetails>();
		/* Commented by Matiur Rahman As not being used. */
		// List<MeterReadUploadErrorLogDetails>
		// billGenerationErrorLogDetailsList = new
		// ArrayList<MeterReadUploadErrorLogDetails>();
		try {

			boolean validSet = true;

			String validationMessage = null;
			String errorMessage = "";

			validationMessage = DJBFieldValidatorUtil.checkBillCycle(
					"Bill Round", billGenerationDetails.getBillRoundId(), 10,
					false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			validationMessage = DJBFieldValidatorUtil.checkNumeric("KNO",
					billGenerationDetails.getAcctId(), 10, false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			validationMessage = DJBFieldValidatorUtil.checkNumeric(
					"Consumer Status", billGenerationDetails.getConsumerStatus(), 3,
					false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			validationMessage = DJBFieldValidatorUtil.checkDate(
					"Meter Read Date", billGenerationDetails.getReadDate(), 10,
					false);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			} else if (UtilityForAll.checkFutureDate(billGenerationDetails
					.getReadDate()) > 0) {
				validSet = false;
				errorMessage = errorMessage
						+ "\nMeter Read Date Should not be a Future Date.";
			}
			/**
			 * Only considering the rest of the validation for Regular Type
			 * consumer.
			 */

			validationMessage = DJBFieldValidatorUtil.checkAlphabet(
					"Meter Read Remark", billGenerationDetails
							.getReaderRemark(), 5, true);

			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}
			validationMessage = DJBFieldValidatorUtil.checkAmount("Meter Read",
					billGenerationDetails.getRegRead(), 10, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			validationMessage = DJBFieldValidatorUtil.checkAmount(
					"Average Consumption", billGenerationDetails
							.getAverageConsumption(), 10, true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			validationMessage = DJBFieldValidatorUtil.checkNumeric(
					"No Of Floors", billGenerationDetails.getNoOfFloors(), 3,
					true);
			if (null != validationMessage) {
				validSet = false;
				errorMessage = errorMessage + "\n" + validationMessage;
			}

			if (validSet) {
				billGenerationMeterReadDetailsList.add(billGenerationDetails);
			} else {
				if (null != billGenerationDetails.getAcctId()
						&& !"".equalsIgnoreCase(billGenerationDetails
								.getAcctId().trim())) {
					meterReadError = new ErrorDetails("For KNO "
							+ billGenerationDetails.getAcctId() + errorMessage,
							FIELD_VALIDATION_ERROR);
				} else {
					meterReadError = new ErrorDetails("Record No " + (0 + 1)
							+ ", " + errorMessage, FIELD_VALIDATION_ERROR);
				}
				billGenerationErrorList.add(meterReadError);

			}

			returnMap.put("meterReadErrorList", billGenerationErrorList);

			returnMap.put("toUploadMeterReadDetailsList",
					billGenerationMeterReadDetailsList);

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
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
			ConsumerDetail toValidateConsumerDetail) {
		AppLog.begin();
		ErrorDetails errorDetails = null;
		try {
			if (null != toValidateConsumerDetail.getPreviousMeterReadDetails()
					&& !"NA".equalsIgnoreCase(toValidateConsumerDetail
							.getPreviousMeterReadDetails()
							.getPreviousMeterReadDate())
					&& !(Integer.toString(ConsumerStatusLookup.METER_INSTALLED
							.getStatusCode())
							.equalsIgnoreCase(meterReadUploadDetails
									.getConsumerStatus()))) {
				int dayDiffer = UtilityForAll.compareDates(
						meterReadUploadDetails.getMeterReadDate(),
						toValidateConsumerDetail.getPreviousMeterReadDetails()
								.getPreviousMeterReadDate());
				// System.out.println("dayDiffer::" + dayDiffer);
				if (dayDiffer == -1) {
					errorDetails = new ErrorDetails(
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
							.info(BUSINESS_VALIDATION_ERROR
									+ "::Current Meter Reading Date "
									+ meterReadUploadDetails.getMeterReadDate()
									+ " should be greater than Previous Meter Read Date "
									+ toValidateConsumerDetail
											.getPreviousMeterReadDetails()
											.getPreviousMeterReadDate()
									+ "::Current Meter Reading Date::"
									+ meterReadUploadDetails.getMeterReadDate()
									+ "::Previous Meter Read Date::"
									+ toValidateConsumerDetail
											.getPreviousMeterReadDetails()
											.getPreviousMeterReadDate());
				}

			}
		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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

				if (null != currentMeterRead
						&& Double.parseDouble(currentMeterRead.trim()) > 0) {
					errorDetails = new ErrorDetails(
							"For KNO "
									+ meterReadUploadDetails.getKno()
									+ ",\nCurrent Meter Reading should always be zero for Remark Code "
									+ currentMeterReadRemark + ".",
							BUSINESS_VALIDATION_ERROR);
					AppLog
							.info(BUSINESS_VALIDATION_ERROR
									+ "::Current Meter Reading should always be zero for Remark Code "
									+ currentMeterReadRemark
									+ "::currentMeterReadRemark::"
									+ currentMeterReadRemark
									+ "::currentMeterRead::" + currentMeterRead);
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
			if (null != currentMeterReadRemark
					&& null != meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode()
					&& (meterReadTypeLookUp.getRegularReadType()
							.getReadTypeCode().indexOf(
									currentMeterReadRemark.trim()) > -1)
					&& !("OK".equalsIgnoreCase(currentMeterReadRemark.trim()) || "PUMP"
							.equalsIgnoreCase(currentMeterReadRemark.trim()))
					&& (null != currentMeterRead && Double
							.parseDouble(currentMeterRead.trim()) > 0)) {
				errorDetails = new ErrorDetails(
						"For KNO "
								+ meterReadUploadDetails.getKno()
								+ ",\nCurrent Meter Reading field should only be Entered for Regular Read Remark OK and PUMP.",
						BUSINESS_VALIDATION_ERROR);
				AppLog
						.info(BUSINESS_VALIDATION_ERROR
								+ "::Current Meter Reading field should only be Entered for Regular Read Remark OK and PUMP::currentMeterReadRemark::"
								+ currentMeterReadRemark
								+ "::currentMeterRead::" + currentMeterRead);
			}
		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
						&& Double.parseDouble(currentMeterRead.trim()) < Double
								.parseDouble(previousMeterRead.trim())) {

					/**
					 * this will be case of meter replacement.
					 */
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.METER_INSTALLED
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis is a Meter Replacement case,Current Meter Read Should be Greater than "
										+ previousMeterRead
										+ " Or Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.METER_INSTALLED
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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
						if (null != consumerStatus
								&& !Integer
										.toString(
												ConsumerStatusLookup.METER_INSTALLED
														.getStatusCode())
										.equalsIgnoreCase(consumerStatus.trim())) {
							errorDetails = new ErrorDetails(
									"For KNO "
											+ meterReadUploadDetails.getKno()
											+ ",\nThis is a Meter Replacement case as Change in Remark Code from "
											+ previousMeterReadRemark
											+ " to "
											+ currentMeterReadRemark
											+ ", Consumer Status Code Should be equal to "
											+ Integer
													.toString(ConsumerStatusLookup.METER_INSTALLED
															.getStatusCode()),
									BUSINESS_VALIDATION_ERROR);
							AppLog
									.info(BUSINESS_VALIDATION_ERROR
											+ "::This is a Meter Replacement case as Change in Remark Code from "
											+ previousMeterReadRemark + " to "
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
			// System.out.println("::"
			// + meterReadUploadDetails.getConsumerStatus());
			if (!(Integer.toString(ConsumerStatusLookup.METER_INSTALLED
					.getStatusCode())).equalsIgnoreCase(meterReadUploadDetails
					.getConsumerStatus())
					&& null != previousMeterReadRemark
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
				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Double.parseDouble(currentMeterRead.trim()) < Double
							.parseDouble(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent meter reading should be greater than Previous Registered Reading "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
				if (null != previousActualMeterRead
						&& !"".equalsIgnoreCase(previousActualMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousActualMeterRead
								.trim()) && null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Double.parseDouble(currentMeterRead.trim()) < Double
							.parseDouble(previousActualMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent meter reading should be greater than Previous Actual Meter Read "
										+ previousActualMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Double.parseDouble(currentMeterRead.trim()) != Double
							.parseDouble(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Meter Read "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
				if (null != previousMeterRead
						&& !"".equalsIgnoreCase(previousMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousMeterRead.trim())
						&& null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Double.parseDouble(currentMeterRead.trim()) != Double
							.parseDouble(previousMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Meter Read "
										+ previousMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
				if (null != previousActualMeterRead
						&& !"".equalsIgnoreCase(previousActualMeterRead.trim())
						&& !"NA".equalsIgnoreCase(previousActualMeterRead
								.trim()) && null != currentMeterRead
						&& !"".equalsIgnoreCase(currentMeterRead.trim())) {
					if (Double.parseDouble(currentMeterRead.trim()) != Double
							.parseDouble(previousActualMeterRead.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nCurrent Meter Reading should be equal to Previous Actual Meter Read "
										+ previousActualMeterRead + ".",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
				if (null != previousMeterReadRemark
						&& !"".equalsIgnoreCase(previousMeterReadRemark.trim())
						&& !"NA".equalsIgnoreCase(previousMeterReadRemark
								.trim())
						&& null != currentMeterReadRemark
						&& !(previousMeterReadRemark.trim())
								.equalsIgnoreCase(currentMeterReadRemark.trim())) {
					errorDetails = new ErrorDetails(
							"For KNO "
									+ meterReadUploadDetails.getKno()
									+ ",\nCurrent Meter Reading Remark Should be same as Previous Meter Reading Remark "
									+ previousMeterReadRemark + ".",
							BUSINESS_VALIDATION_ERROR);
					AppLog
							.info(BUSINESS_VALIDATION_ERROR
									+ "::Current Meter Reading Remark Should be same as Previous Meter Reading Remark "
									+ previousMeterReadRemark
									+ "::previousMeterReadRemark::"
									+ previousMeterReadRemark
									+ "::currentMeterReadRemark::"
									+ currentMeterReadRemark);
				}
			}

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.DISCONNECTED
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis is a disconnection case as Read Type Changed from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.DISCONNECTED
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
										+ "::This is a disconnection case as Read Type Changed from "
										+ previousMeterReadRemark + " to "
										+ currentMeterReadRemark
										+ "::previousMeterReadRemark::"
										+ previousMeterReadRemark
										+ "::currentMeterReadRemark::"
										+ currentMeterReadRemark
										+ "::consumerStatus::" + consumerStatus);
					}
				}
			}

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", Consumer Status Code Should be equal to "
										+ ConsumerStatusLookup.REOPENING
												.getStatusCode(),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
										+ "::This is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark + " to "
										+ currentMeterReadRemark
										+ "::previousMeterReadRemark::"
										+ previousMeterReadRemark
										+ "::currentMeterReadRemark::"
										+ currentMeterReadRemark
										+ "::consumerStatus::" + consumerStatus);
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
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.REOPENING
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
										+ "::This is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark + " to "
										+ currentMeterReadRemark
										+ "::previousMeterReadRemark::"
										+ previousMeterReadRemark
										+ "::currentMeterReadRemark::"
										+ currentMeterReadRemark
										+ "::consumerStatus::" + consumerStatus);
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
					if (null != consumerStatus
							&& !Integer.toString(
									ConsumerStatusLookup.REOPENING
											.getStatusCode()).equalsIgnoreCase(
									consumerStatus.trim())) {
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nThis is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark
										+ " to "
										+ currentMeterReadRemark
										+ ", Consumer Status Code Should be equal to "
										+ Integer
												.toString(ConsumerStatusLookup.REOPENING
														.getStatusCode()),
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
										+ "::This is a Re-opening case as Change in Remark Code from "
										+ previousMeterReadRemark + " to "
										+ currentMeterReadRemark
										+ "::previousMeterReadRemark::"
										+ previousMeterReadRemark
										+ "::currentMeterReadRemark::"
										+ currentMeterReadRemark
										+ "::consumerStatus::" + consumerStatus);
					}
				}
			}

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
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
			MeterReadTypeLookUp meterReadTypeLookUp) {
		AppLog.begin();
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
						errorDetails = new ErrorDetails(
								"For KNO "
										+ meterReadUploadDetails.getKno()
										+ ",\nNew Average Consumption is mandatory for Non Domestic Consumer("
										+ consumerCategory + ").",
								BUSINESS_VALIDATION_ERROR);
						AppLog
								.info(BUSINESS_VALIDATION_ERROR
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

		} catch (Exception e) {
			AppLog.error(e);
			errorDetails = null;
		}
		AppLog.end();
		return errorDetails;
	}
}
