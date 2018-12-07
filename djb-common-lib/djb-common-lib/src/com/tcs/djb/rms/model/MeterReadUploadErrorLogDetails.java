/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * <code>MeterReadUploadErrorLogDetails</code> is to Log Error Details with
 * input data to store the error occurred during any Meter Read Upload
 * webservice call.
 * </p>
 * 
 * @see ErrorDetails
 * @see MeterReadUploadDetails
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class MeterReadUploadErrorLogDetails {
	/**
	 * <p>
	 * ErrorDetails to store the error details occurred during any web service
	 * call or any operation.
	 * </p>
	 */
	private ErrorDetails errorDetails;
	/**
	 * <p>
	 * freezeFlag is to store the Freeze flag during upload meter read web
	 * service call or any operation.
	 * </p>
	 */
	private String freezeFlag;
	/**
	 * <p>
	 * Details of the Meter Read Upload web service
	 * </p>
	 */
	private MeterReadUploadDetails meterReadUploadDetails;

	/**
	 * <p>
	 * mrKey is to store the MR Key sent for Freezing during upload meter read
	 * web service call or any operation.
	 * </p>
	 */
	private String mrKey;

	/**
	 * constructor
	 * 
	 * @param errorDetails
	 * @param meterReadUploadDetails
	 */
	public MeterReadUploadErrorLogDetails(ErrorDetails errorDetails,
			MeterReadUploadDetails meterReadUploadDetails) {
		this.errorDetails = errorDetails;
		this.meterReadUploadDetails = meterReadUploadDetails;
	}

	public MeterReadUploadErrorLogDetails(ErrorDetails errorDetails,
			MeterReadUploadDetails meterReadUploadDetails, String freezeFlag,
			String mrKey) {
		this.freezeFlag = freezeFlag;
		this.mrKey = mrKey;
		this.errorDetails = errorDetails;
		this.meterReadUploadDetails = meterReadUploadDetails;
	}

	/**
	 * @return the errorDetails
	 */
	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}

	/**
	 * @return the freezeFlag
	 */
	public String getFreezeFlag() {
		return freezeFlag;
	}

	/**
	 * @return the meterReadUploadDetails
	 */
	public MeterReadUploadDetails getMeterReadUploadDetails() {
		return meterReadUploadDetails;
	}

	/**
	 * @return the mrKey
	 */
	public String getMrKey() {
		return mrKey;
	}

	/**
	 * @param errorDetails
	 *            the errorDetails to set
	 */
	public void setErrorDetails(ErrorDetails errorDetails) {
		this.errorDetails = errorDetails;
	}

	/**
	 * @param freezeFlag
	 *            the freezeFlag to set
	 */
	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	/**
	 * @param meterReadUploadDetails
	 *            the meterReadUploadDetails to set
	 */
	public void setMeterReadUploadDetails(
			MeterReadUploadDetails meterReadUploadDetails) {
		this.meterReadUploadDetails = meterReadUploadDetails;
	}

	/**
	 * @param mrKey
	 *            the mrKey to set
	 */
	public void setMrKey(String mrKey) {
		this.mrKey = mrKey;
	}

}
