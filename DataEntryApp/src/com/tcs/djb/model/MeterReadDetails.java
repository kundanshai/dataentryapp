/************************************************************************************************************
 * @(#) MeterReadDetails.java   27 Jul 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * MeterReadDetails store the details of a Meter Read
 * </p>
 * 
 * @author Apurb Sinha (Tata Consultancy Services)
 * @history 27-07-2012 apurb.sinha created the class
 * @history 10-10-2012 Matiur Rahman added new fields
 *          _meterNumber,_cons,_effecNoOfDays
 * @history 15-11-2012 Matiur Rahman added new fields _noOfFloor
 * @history 17-11-2012 Tuhin added new field _remarks
 */
public class MeterReadDetails {
	private String _avgRead;
	private String _meterReadDate;
	private String _meterReaderName;
	private String _meterStatus;
	private String _readType;
	private String _meterNumber;
	private String _regRead;
	private String _cons;
	private String _effecNoOfDays;
	private String _noOfFloor;//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
	private String _remarks;//Added for Remarks on 16-11-2012 by Tuhin 
	public String getAvgRead() {
		return _avgRead;
	}
	
	/**
	 * @return the _cons
	 */
	public String getCons() {
		return _cons;
	}

	/**
	 * @return the _effecNoOfDays
	 */
	public String getEffecNoOfDays() {
		return _effecNoOfDays;
	}

	/**
	 * @return the _meterNumber
	 */
	public String getMeterNumber() {
		return _meterNumber;
	}

	public String getMeterReadDate() {
		return _meterReadDate;
	}

	public String getMeterReaderName() {
		return _meterReaderName;
	}

	public String getMeterStatus() {
		return _meterStatus;
	}

	/**
	 * @return the noOfFloor
	 */
	public String getNoOfFloor() {
		return _noOfFloor;
	}

	public String getReadType() {
		return _readType;
	}

	public String getRegRead() {
		return _regRead;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return _remarks;
	}

	public void setAvgRead(String avgRead) {
		_avgRead = avgRead;
	}

	/**
	 * @param cons
	 *            the _cons to set
	 */
	public void setCons(String cons) {
		_cons = cons;
	}

	/**
	 * @param effecNoOfDays
	 *            the _effecNoOfDays to set
	 */
	public void setEffecNoOfDays(String effecNoOfDays) {
		_effecNoOfDays = effecNoOfDays;
	}

	/**
	 * @param meterNumber
	 *            the _meterNumber to set
	 */
	public void setMeterNumber(String meterNumber) {
		_meterNumber = meterNumber;
	}

	public void setMeterReadDate(String meterReadDate) {
		_meterReadDate = meterReadDate;
	}

	public void setMeterReaderName(String meterReaderName) {
		_meterReaderName = meterReaderName;
	}

	public void setMeterStatus(String meterStatus) {
		_meterStatus = meterStatus;
	}

	/**
	 * @param noOfFloor the noOfFloor to set
	 */
	public void setNoOfFloor(String noOfFloor) {
		_noOfFloor = noOfFloor;
	}

	public void setReadType(String readType) {
		_readType = readType;
	}

	public void setRegRead(String regRead) {
		_regRead = regRead;
	}

	public void setRemarks(String remarks) {
		_remarks = remarks;
	}
}
