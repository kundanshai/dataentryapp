/************************************************************************************************************
 * @(#) MeterReplacementInterface.java   15 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.interfaces;

import com.tcs.djb.model.MeterReadUploadReply;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.MeterReplacementUploadReply;

/**
 * <p>
 * Interface for Meter Replacement Detail Upload.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 15-03-2013
 * @see MeterReadUploadReply
 * @see MeterReplacementDetail
 */
public interface MeterReplacementInterface {
	/**
	 * <p>
	 * This method used to update Meter Replacement Detail details .
	 * </p>
	 * 
	 * @see MeterReadUploadReply
	 * @see MeterReplacementDetail
	 * @param meterReplacementDetail
	 *            of Meter Replacement Detail to be updated
	 * @return meterReadUploadReply is the reply to to the
	 *         meterReadUploadRequest
	 */
	public MeterReplacementUploadReply meterReplacementUpload(
			MeterReplacementDetail meterReplacementDetail);

	/**
	 * <p>
	 * This method used to Reset Meter Replacement Detail details .
	 * </p>
	 * 
	 * @see MeterReplacementDetail
	 * @param meterReplacementDetail
	 *            of Meter Replacement Detail to be updated
	 * @return true if success else false
	 */
	public boolean resetMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail);
}
