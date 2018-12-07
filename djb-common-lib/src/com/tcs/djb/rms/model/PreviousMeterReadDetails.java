/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * PreviousMeterReadDetails to store the Previous Meter Read Details of a
 * consumer.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history Added consumptionPerDay by Matiur Rahman on 23-02-2013
 */
public class PreviousMeterReadDetails {
	/**
	 * consumption Per Day of the consumer
	 */
	private String consumptionPerDay;
	/**
	 * previous Actual MeterRead.
	 */
	private String previousActualMeterRead;
	/**
	 * previous Actual MeterReadDate.
	 */
	private String previousActualMeterReadDate;
	/**
	 * previous Actual Meter Read Status.
	 */
	private String previousActualMeterReadStatus;
	/**
	 * previous Average Consumption.
	 */
	private String previousAverageConsumption;
	/**
	 * previous Meter Read.
	 */
	private String previousMeterRead;
	/**
	 * previous Meter Read Date.
	 */
	private String previousMeterReadDate;
	/**
	 * previous Meter Read Status.
	 */
	private String previousMeterReadStatus;
	/**
	 * previous No Of Floors.
	 */
	private String previousNoOfFloors;

	/**
	 * @return the consumptionPerDay
	 */
	public String getConsumptionPerDay() {
		return consumptionPerDay;
	}

	/**
	 * @return the previousActualMeterRead
	 */
	public String getPreviousActualMeterRead() {
		return previousActualMeterRead;
	}

	/**
	 * @return the previousActualMeterReadDate
	 */
	public String getPreviousActualMeterReadDate() {
		return previousActualMeterReadDate;
	}

	/**
	 * @return the previousActualMeterReadStatus
	 */
	public String getPreviousActualMeterReadStatus() {
		return previousActualMeterReadStatus;
	}

	/**
	 * @return the previousAverageConsumption
	 */
	public String getPreviousAverageConsumption() {
		return previousAverageConsumption;
	}

	/**
	 * @return the previousMeterRead
	 */
	public String getPreviousMeterRead() {
		return previousMeterRead;
	}

	/**
	 * @return the previousMeterReadDate
	 */
	public String getPreviousMeterReadDate() {
		return previousMeterReadDate;
	}

	/**
	 * @return the previousMeterReadStatus
	 */
	public String getPreviousMeterReadStatus() {
		return previousMeterReadStatus;
	}

	/**
	 * @return the previousNoOfFloors
	 */
	public String getPreviousNoOfFloors() {
		return previousNoOfFloors;
	}

	/**
	 * @param consumptionPerDay
	 *            the consumptionPerDay to set
	 */
	public void setConsumptionPerDay(String consumptionPerDay) {
		this.consumptionPerDay = consumptionPerDay;
	}

	/**
	 * @param previousActualMeterRead
	 *            the previousActualMeterRead to set
	 */
	public void setPreviousActualMeterRead(String previousActualMeterRead) {
		this.previousActualMeterRead = previousActualMeterRead;
	}

	/**
	 * @param previousActualMeterReadDate
	 *            the previousActualMeterReadDate to set
	 */
	public void setPreviousActualMeterReadDate(
			String previousActualMeterReadDate) {
		this.previousActualMeterReadDate = previousActualMeterReadDate;
	}

	/**
	 * @param previousActualMeterReadStatus
	 *            the previousActualMeterReadStatus to set
	 */
	public void setPreviousActualMeterReadStatus(
			String previousActualMeterReadStatus) {
		this.previousActualMeterReadStatus = previousActualMeterReadStatus;
	}

	/**
	 * @param previousAverageConsumption
	 *            the previousAverageConsumption to set
	 */
	public void setPreviousAverageConsumption(String previousAverageConsumption) {
		this.previousAverageConsumption = previousAverageConsumption;
	}

	/**
	 * @param previousMeterRead
	 *            the previousMeterRead to set
	 */
	public void setPreviousMeterRead(String previousMeterRead) {
		this.previousMeterRead = previousMeterRead;
	}

	/**
	 * @param previousMeterReadDate
	 *            the previousMeterReadDate to set
	 */
	public void setPreviousMeterReadDate(String previousMeterReadDate) {
		this.previousMeterReadDate = previousMeterReadDate;
	}

	/**
	 * @param previousMeterReadStatus
	 *            the previousMeterReadStatus to set
	 */
	public void setPreviousMeterReadStatus(String previousMeterReadStatus) {
		this.previousMeterReadStatus = previousMeterReadStatus;
	}

	/**
	 * @param previousNoOfFloors
	 *            the previousNoOfFloors to set
	 */
	public void setPreviousNoOfFloors(String previousNoOfFloors) {
		this.previousNoOfFloors = previousNoOfFloors;
	}

}
