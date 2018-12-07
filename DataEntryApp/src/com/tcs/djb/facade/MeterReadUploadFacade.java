/************************************************************************************************************
 * @(#) MeterReadUploadFacade.java   14 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.interfaces.MeterReadUploadInterface;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReadTypeLookUp;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.MeterReadUploadErrorLogDetails;
import com.tcs.djb.model.MeterReadUploadReply;
import com.tcs.djb.model.MeterReadUploadRequest;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DJBFieldValidatorUtil;
import com.tcs.djb.validator.MeterReadValidator;

/**
 * <P>
 * This class includes all the business logic to Upload Meter Read Details by
 * calling Validator Classes or DAO classes. It implements
 * <code>MeterReadUploadInterface</code>.
 * </P>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 * @see AppLog
 * @see MeterReadUploadInterface
 * @see SetterDAO
 * @see GetterDAO
 * @see ConsumerDetail
 * @see ErrorDetails
 * @see MeterReadTypeLookUp
 * @see MeterReadUploadDetails
 * @see MeterReadUploadErrorLogDetails
 * @see MeterReadUploadReply
 * @see MeterReadUploadRequest
 * @see DJBFieldValidatorUtil
 * @see MeterReadValidator
 * @history 22-03-2013 Matiur Rahamn Switching off Meter read Validation as per
 *          updated JTrac DJB-1280
 */
public class MeterReadUploadFacade implements MeterReadUploadInterface {
	/**
	 * <p>
	 * For operation failed to Save or Update.
	 * </p>
	 */
	private static String OPERATION_FAILED = "Save or Update Error";

	/**
	 * <p>
	 * For Success Save.
	 * </p>
	 */
	private static String SUCCESS_SAVE = "Successfully Saved.";

	/**
	 * <p>
	 * This method used to update meter read details .
	 * </p>
	 * 
	 * @see MeterReadUploadReply
	 * @see MeterReadUploadRequest
	 * @param meterReadUploadRequest
	 *            of Meter Read Details to be updated
	 * @return meterReadUploadReply is the reply to to the
	 *         meterReadUploadRequest
	 */
	@Override
	public MeterReadUploadReply meterReadUpload(
			MeterReadUploadRequest meterReadUploadRequest) {
		AppLog.begin();
		MeterReadUploadReply meterReadUploadReply = null;
		try {
			// int totalRecordReceived = meterReadUploadRequest
			// .getMeterReadUploadDetails().length;
			// AppLog.info("Total Record Received::" + totalRecordReceived);

			// System.out.println("In Facade::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getBillRound()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getKno()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getConsumerStatus()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterReadDate()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterReadRemark()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterRead()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getAverageConsumption()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getNoOfFloors());
			String checkBillCycleValidationMessage = DJBFieldValidatorUtil
					.checkBillCycle("Bill Round", meterReadUploadRequest
							.getMeterReadUploadDetails()[0].getBillRound(), 10,
							false);
			String checkKNOValidationMessage = DJBFieldValidatorUtil
					.checkNumeric("KNO", meterReadUploadRequest
							.getMeterReadUploadDetails()[0].getKno(), 10, false);
			if (null == checkBillCycleValidationMessage
					&& null == checkKNOValidationMessage) {
				return saveMeterRead(meterReadUploadRequest);
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return meterReadUploadReply;
	}

	/**
	 * <p>
	 * This method is used to reset Meter Read Detail in the Data base.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean Reset Meter Replacement Detail
	 */
	@Override
	public boolean resetMeterReadDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		boolean isSuccess = false;
		try {
			ConsumerDetail consumerDetail = new ConsumerDetail();
			consumerDetail.setUpdatedBy(meterReplacementDetail
					.getLastUpdatedByID());
			consumerDetail.setKno(meterReplacementDetail.getKno());
			consumerDetail.setBillRound(meterReplacementDetail.getBillRound());
			MeterReadDetails currentMeterReadDetails = new MeterReadDetails();
			currentMeterReadDetails.setMeterReadDate("");
			currentMeterReadDetails.setMeterStatus("");
			currentMeterReadDetails.setReadType(null);
			currentMeterReadDetails.setRegRead("");
			currentMeterReadDetails.setAvgRead("");
			consumerDetail.setCurrentMeterRead(currentMeterReadDetails);
			HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String, ConsumerDetail>();
			toUpdateMap.put("consumerDetail", consumerDetail);
			SetterDAO setterDAO = new SetterDAO();
			HashMap<String, String> returnMap = (HashMap<String, String>) setterDAO
					.resetMeterRead(toUpdateMap);
			String isSuccessReset = (String) returnMap.get("isSuccess");
			if ("Y".equals(isSuccessReset)) {
				isSuccess = true;
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * This method saves meter read details to database by calling the
	 * <code>saveMeterRead</code> method of the <code>MeterReadDAO</code> class
	 * after validating various parameters by calling
	 * <code>validateMeterReadUpload</code>method of
	 * <code>MeterReadValidator</code> class.
	 * </p>
	 * 
	 * @see MeterReadWebService#meterReadUpload
	 * @see MeterReadValidator#validateMeterReadUpload
	 * @see MeterReadTypeLookUp
	 * @see MeterReadDAO#saveMeterRead
	 * @param meterReadUploadRequest
	 * @return Meter Read Upload Reply
	 */
	public MeterReadUploadReply saveMeterRead(
			MeterReadUploadRequest meterReadUploadRequest) {
		AppLog.begin();
		MeterReadUploadReply meterReadUploadReply = new MeterReadUploadReply();
		try {
			String responseStatus = SUCCESS_SAVE;
			int totalRecordReceived = meterReadUploadRequest
					.getMeterReadUploadDetails().length;
			// System.out.println("In Facade::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getBillRound()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getKno()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getConsumerStatus()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterReadDate()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterReadRemark()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getMeterRead()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getAverageConsumption()
			// + "::"
			// + meterReadUploadRequest.getMeterReadUploadDetails()[0]
			// .getNoOfFloors());
			int recordUpdated = 0;
			int errorLoged = 0;
			List<ErrorDetails> meterReadErrorList = null;
			List<MeterReadUploadDetails> toUploadMeterReadDetailsList = null;
			Map<Object, Object> returnMap = (HashMap<Object, Object>) MeterReadValidator
					.validateMeterReadUploadFields(meterReadUploadRequest
							.getMeterReadUploadDetails());
			if (null != returnMap) {
				toUploadMeterReadDetailsList = (ArrayList<MeterReadUploadDetails>) returnMap
						.get("toUploadMeterReadDetailsList");
				if (null != toUploadMeterReadDetailsList
						&& toUploadMeterReadDetailsList.size() > 0) {
					/**
					 * Retrieve records from the Database for meter Read Type
					 * Look Up.
					 */
					MeterReadTypeLookUp meterReadTypeLookUp = GetterDAO
							.getMeterReadRemarkCorrespondingToReadType();
					returnMap.put("meterReadTypeLookUp", meterReadTypeLookUp);
					/**
					 * Switching off Meter read Validation as per updated JTrac
					 * DJB-1280
					 */
					/**
					 * Retrieve records from the Database for those account to
					 * update records.
					 */
					// List<ConsumerDetail> consumerDetailList = GetterDAO
					// .getConsumerDetailByAccountIDAndBillRound((ArrayList<MeterReadUploadDetails>)
					// toUploadMeterReadDetailsList);
					// returnMap.put("consumerDetailList", consumerDetailList);
					// returnMap = (HashMap<Object, Object>) MeterReadValidator
					// .validateMeterReadUploadAsPerBusinessProcess((HashMap<Object,
					// Object>) returnMap);
					// toUploadMeterReadDetailsList =
					// (ArrayList<MeterReadUploadDetails>) returnMap
					// .get("toUploadMeterReadDetailsList");
				}
				if (null != toUploadMeterReadDetailsList
						&& toUploadMeterReadDetailsList.size() > 0) {
					/**
					 * Save MeterRead records to the Database for those account
					 * to update records.
					 */
					for (int i = 0; i < toUploadMeterReadDetailsList.size(); i++) {
						Map<String, ConsumerDetail> inputMap = new HashMap<String, ConsumerDetail>();
						MeterReadUploadDetails meterReadUploadDetails = (MeterReadUploadDetails) toUploadMeterReadDetailsList
								.get(i);
						ConsumerDetail consumerDetail = new ConsumerDetail();
						consumerDetail.setConsumerStatus(meterReadUploadDetails
								.getConsumerStatus());
						consumerDetail.setKno(meterReadUploadDetails.getKno());
						consumerDetail.setBillRound(meterReadUploadDetails
								.getBillRound());
						MeterReadDetails currentMeterRead = new MeterReadDetails();
						currentMeterRead
								.setMeterReadDate(meterReadUploadDetails
										.getMeterReadDate());
						currentMeterRead.setRegRead(meterReadUploadDetails
								.getMeterRead());
						currentMeterRead.setMeterStatus(meterReadUploadDetails
								.getMeterReadRemark());
						currentMeterRead.setAvgRead(meterReadUploadDetails
								.getAverageConsumption());
						currentMeterRead.setNoOfFloor(meterReadUploadDetails
								.getNoOfFloors());
						consumerDetail.setCurrentMeterRead(currentMeterRead);
						consumerDetail.setUpdatedBy(meterReadUploadRequest
								.getUserId());
						inputMap.put("consumerDetail", consumerDetail);
						SetterDAO setterDAO = new SetterDAO();
						Map<String, String> meterReadSaveReturnMap = setterDAO
								.saveMeterRead(inputMap);
						String isSuccess = (String) meterReadSaveReturnMap
								.get("isSuccess");
						if ("Y".equalsIgnoreCase(isSuccess)) {
							recordUpdated = 1;
						} else {
							recordUpdated = 0;
						}
					}
				}
				/**
				 * Logging error to the database encountered during Upload
				 * MeterRead records to the Database.
				 */
				meterReadErrorList = (ArrayList<ErrorDetails>) returnMap
						.get("meterReadErrorList");
				if (null != meterReadErrorList && meterReadErrorList.size() > 0) {
					ErrorDetails[] meterReadErrorArray = new ErrorDetails[meterReadErrorList
							.size()];
					for (int e = 0; e < meterReadErrorList.size(); e++) {
						ErrorDetails meterReadError = (ErrorDetails) meterReadErrorList
								.get(e);
						meterReadErrorArray[e] = meterReadError;
					}
					meterReadUploadReply.setErrorDetails(meterReadErrorArray);
				}
			}
			if (totalRecordReceived != recordUpdated) {
				if (totalRecordReceived == 1) {
					responseStatus = "Meter read Details could not be Saved.";
				} else {
					responseStatus = "Total " + recordUpdated
							+ " record(s) Updated Out of "
							+ totalRecordReceived;
				}
				if (recordUpdated != 0) {
					responseStatus = responseStatus
							+ "\nPlease See Error Details.";
				}
			}
			/**
			 * Check If MRD is already Frozen
			 */
			if (recordUpdated == 0) {
				if (GetterDAO
						.checkIfMRDIsFrozenByAccountIDAndBillRound((ArrayList<MeterReadUploadDetails>) toUploadMeterReadDetailsList)) {
					responseStatus = "Record could not be Saved.";
					ErrorDetails[] errorDetailsArray = new ErrorDetails[1];
					errorDetailsArray[0] = new ErrorDetails(
							"MRD has been already processed for the selected Bill Round.",
							OPERATION_FAILED);
					meterReadUploadReply.setErrorDetails(errorDetailsArray);
				}
			}
			meterReadUploadReply.setResponseStatus(responseStatus);
			// System.out.println("Total Record Received::" +
			// totalRecordReceived
			// + "::Record Updated::" + recordUpdated + "::Error Loged::"
			// + errorLoged + "::Response Status::" + responseStatus);
			AppLog.info("Total Record Received::" + totalRecordReceived
					+ "::Record Updated::" + recordUpdated + "::Error Loged::"
					+ errorLoged + "::Response Status::" + responseStatus);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return meterReadUploadReply;
	}
}