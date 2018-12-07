/************************************************************************************************************
 * @(#) MeterReplacementFacade.java   14 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.facade;

import java.util.Map;

import com.tcs.djb.dao.MeterReplacementDAO;
import com.tcs.djb.interfaces.MeterReplacementInterface;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MeterReadUploadReply;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.MeterReplacementUploadReply;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.MeterReplacementValidator;

/**
 *<P>
 * This class includes all the business logic to Upload Meter Replacement
 * Details by calling Validator Classes or DAO classes. It implements
 * <code>MeterReplacementInterface</code>.
 * </P>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 * @see AppLog
 * @see MeterReadUploadReply
 * 
 */
public class MeterReplacementFacade implements MeterReplacementInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.djb.interfaces.MeterReplacementInterface#meterReplacementUpload
	 * (com.tcs.djb.model.MeterReplacementDetail)
	 */
	@Override
	public MeterReplacementUploadReply meterReplacementUpload(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		MeterReplacementUploadReply meterReplacementUploadReply = new MeterReplacementUploadReply();
		try {
			meterReplacementDetail = MeterReplacementValidator
					.setDefaultValues(meterReplacementDetail);
			Map<Object, Object> returnMap = MeterReplacementValidator
					.validateMeterReplacementUploadFields(meterReplacementDetail);
			Object meterReplacementErrorObj = returnMap
					.get("meterReplacementError");
			if (null != meterReplacementErrorObj) {
				ErrorDetails meterReplacementError = (ErrorDetails) meterReplacementErrorObj;
				if (null != meterReplacementError) {
					ErrorDetails[] errorDetailsArray = new ErrorDetails[1];
					errorDetailsArray[0] = meterReplacementError;
					meterReplacementUploadReply
							.setErrorDetails(errorDetailsArray);
					meterReplacementUploadReply
							.setResponseStatus("Meter Replacement Details could not be Saved.");
					AppLog.end();
					return meterReplacementUploadReply;
				}
			}
			Object meterReplacementDetailObj = returnMap
					.get("meterReplacementDetail");
			if (null != meterReplacementDetailObj) {
				meterReplacementDetail = (MeterReplacementDetail) meterReplacementDetailObj;
				if (null != meterReplacementDetail) {
					boolean isSuccess = MeterReplacementDAO
							.saveMeterReplacementDetail(meterReplacementDetail);
					if (isSuccess) {
						meterReplacementUploadReply
								.setResponseStatus("Successfully Saved.");
					} else {
						meterReplacementUploadReply
								.setResponseStatus("Meter Replacement Details could not be Saved.");
					}
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return meterReplacementUploadReply;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.djb.interfaces.MeterReplacementInterface#resetMeterReplacementDetail
	 * (com.tcs.djb.model.MeterReplacementDetail)
	 */
	@Override
	public boolean resetMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();

		AppLog.end();
		return MeterReplacementDAO
				.resetMeterReplacementDetail(meterReplacementDetail);
	}

}
