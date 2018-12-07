/************************************************************************************************************
 * @(#) MRDScheduleDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.List;

/**
 * <p>
 * Container for MRD Schedule Details.
 * </p>
 */
public class MRDScheduleDetails {
	private String _areaDesc;
	private String _areaNo;
	private String _billRoundId;
	private String _endDate;
	private String _meterReadDiaryCode;

	private String _meterReader;

	private List<MRDContainer> _mrdContainerList;

	private String _mrkey;
	private String _mrNo;
	private String _route;
	private String _scheduleDownloadDate;
	private String _serviceCycle;
	private String _startDate;
	private String _zone;

	private String _zroCode;

	/**
	 * @return the areaDesc
	 */
	public String getAreaDesc() {
		return _areaDesc;
	}

	public String getAreaNo() {
		return _areaNo;
	}

	public String getBillRoundId() {
		return _billRoundId;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return _endDate;
	}

	/**
	 * @return the meterReadDiaryCode
	 */
	public String getMeterReadDiaryCode() {
		return _meterReadDiaryCode;
	}

	/**
	 * @return the meterReader
	 */
	public String getMeterReader() {
		return _meterReader;
	}

	public List<MRDContainer> getMrdContainerList() {
		return _mrdContainerList;
	}

	public String getMrkey() {
		return _mrkey;
	}

	public String getMrNo() {
		return _mrNo;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return _route;
	}

	public String getScheduleDownloadDate() {
		return _scheduleDownloadDate;
	}

	/**
	 * @return the serviceCycle
	 */
	public String getServiceCycle() {
		return _serviceCycle;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return _startDate;
	}

	public String getZone() {
		return _zone;
	}

	/**
	 * @return the zroCode
	 */
	public String getZroCode() {
		return _zroCode;
	}

	/**
	 * @param areaDesc
	 *            the areaDesc to set
	 */
	public void setAreaDesc(String areaDesc) {
		_areaDesc = areaDesc;
	}

	public void setAreaNo(String areaNo) {
		_areaNo = areaNo;
	}

	public void setBillRoundId(String billRoundId) {
		_billRoundId = billRoundId;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		_endDate = endDate;
	}

	/**
	 * @param meterReadDiaryCode
	 *            the meterReadDiaryCode to set
	 */
	public void setMeterReadDiaryCode(String meterReadDiaryCode) {
		_meterReadDiaryCode = meterReadDiaryCode;
	}

	/**
	 * @param meterReader
	 *            the meterReader to set
	 */
	public void setMeterReader(String meterReader) {
		_meterReader = meterReader;
	}

	public void setMrdContainerList(List<MRDContainer> mrdContainerList) {
		_mrdContainerList = mrdContainerList;
	}

	public void setMrkey(String mrkey) {
		_mrkey = mrkey;
	}

	public void setMrNo(String mrNo) {
		_mrNo = mrNo;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(String route) {
		_route = route;
	}

	public void setScheduleDownloadDate(String scheduleDownloadDate) {
		_scheduleDownloadDate = scheduleDownloadDate;
	}

	/**
	 * @param serviceCycle
	 *            the serviceCycle to set
	 */
	public void setServiceCycle(String serviceCycle) {
		_serviceCycle = serviceCycle;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		_startDate = startDate;
	}

	public void setZone(String zone) {
		_zone = zone;
	}

	/**
	 * @param zroCode
	 *            the zroCode to set
	 */
	public void setZroCode(String zroCode) {
		_zroCode = zroCode;
	}

}
