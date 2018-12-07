/************************************************************************************************************
 * @(#) MeterReadUploadInterface.java   14 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.interfaces;

import java.util.HashMap;

import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReadUploadReply;
import com.tcs.djb.model.MeterReadUploadRequest;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * Interface for Meter Read Details Upload.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 14-03-2013
 * 
 */
public interface MeterReadUploadInterface {
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
	 * 
	 * 
	 */
	public MeterReadUploadReply meterReadUpload(
			MeterReadUploadRequest meterReadUploadRequest);

	/**
	 * <p>
	 * reset Meter Read Detail in the Data base.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @param meterReplacementDetail
	 * @return boolean Reset Meter Replacement Detail
	 */
	public boolean resetMeterReadDetail(
			MeterReplacementDetail meterReplacementDetail);
}
