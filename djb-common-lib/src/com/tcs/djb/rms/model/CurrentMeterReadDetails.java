/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 *<p>
 * CurrentMeterReadDetails to store the Current Meter Read Details of a
 * consumer.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * 
 */
public class CurrentMeterReadDetails {
	/**
	 * Average Consumption.
	 */
	private String averageConsumption;
	/**
	 * Meter Read.
	 */
	private String meterRead;
	/**
	 * Meter Read Date.
	 */
	private String meterReadDate;
	/**
	 * Meter Read Status.
	 */
	private String meterReadStatus;
	/**
	 * No Of Floors
	 */
	private String noOfFloors;

	/**
	 * @return the averageConsumption
	 */
	public String getAverageConsumption() {
		return averageConsumption;
	}

	/**
	 * @return the meterRead
	 */
	public String getMeterRead() {
		return meterRead;
	}

	/**
	 * @return the meterReadDate
	 */
	public String getMeterReadDate() {
		return meterReadDate;
	}

	/**
	 * @return the meterReadStatus
	 */
	public String getMeterReadStatus() {
		return meterReadStatus;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @param averageConsumption
	 *            the averageConsumption to set
	 */
	public void setAverageConsumption(String averageConsumption) {
		this.averageConsumption = averageConsumption;
	}

	/**
	 * @param meterRead
	 *            the meterRead to set
	 */
	public void setMeterRead(String meterRead) {
		this.meterRead = meterRead;
	}

	/**
	 * @param meterReadDate
	 *            the meterReadDate to set
	 */
	public void setMeterReadDate(String meterReadDate) {
		this.meterReadDate = meterReadDate;
	}

	/**
	 * @param meterReadStatus
	 *            the meterReadStatus to set
	 */
	public void setMeterReadStatus(String meterReadStatus) {
		this.meterReadStatus = meterReadStatus;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

}
