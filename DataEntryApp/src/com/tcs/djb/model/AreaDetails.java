/************************************************************************************************************
 * @(#) AreaDetails.java   20 Jan 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Model Class for Grievance Status Details Screen.
 * </p>
 * 
 * @author Rajib Hazarika(Tata Consultancy Services)
 * @since 20-JAN-2016
 * 
 */
public class AreaDetails {

	/**
	 * Area Description
	 */
	private String areaCd;

	/**
	 * Area Code.
	 */
	private String areaDesc;

	/**
	 * Message to be sent.
	 */
	private String msg;

	/**
	 * PIN Code of the area
	 */
	private String pin;

	/**
	 * Status of the transaction which may be SUCCESS,FAIL,ERROR,EXCEPTION
	 */
	private String status;

	/**
	 * Sub Area Code.
	 */
	private String subAreaCd;

	/**
	 * Sub Area Description
	 */

	private String subAreaName;

	/**
	 * 
	 */
	public AreaDetails() {
		super();
	}

	/**
	 * @param msg
	 * @param status
	 */
	public AreaDetails(String msg, String status) {
		super();
		this.msg = msg;
		this.status = status;
	}

	/**
	 * @return the areaCd
	 */
	public String getAreaCd() {
		return areaCd;
	}

	/**
	 * @return the areaDesc
	 */
	public String getAreaDesc() {
		return areaDesc;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the subAreaCd
	 */
	public String getSubAreaCd() {
		return subAreaCd;
	}

	/**
	 * @return the subAreaName
	 */
	public String getSubAreaName() {
		return subAreaName;
	}

	/**
	 * @param areaCd
	 *            the areaCd to set
	 */
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	/**
	 * @param areaDesc
	 *            the areaDesc to set
	 */
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @param pin
	 *            the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param subAreaCd
	 *            the subAreaCd to set
	 */
	public void setSubAreaCd(String subAreaCd) {
		this.subAreaCd = subAreaCd;
	}

	/**
	 * @param subAreaName
	 *            the subAreaName to set
	 */
	public void setSubAreaName(String subAreaName) {
		this.subAreaName = subAreaName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AreaDetails [");
		if (areaDesc != null) {
			builder.append("area_desc=");
			builder.append(areaDesc);
			builder.append(", ");
		}
		if (areaCd != null) {
			builder.append("area_Cd=");
			builder.append(areaCd);
			builder.append(", ");
		}
		if (subAreaCd != null) {
			builder.append("subAreaCd=");
			builder.append(subAreaCd);
			builder.append(", ");
		}
		if (subAreaName != null) {
			builder.append("subAreaname=");
			builder.append(subAreaName);
			builder.append(", ");
		}
		if (pin != null) {
			builder.append("pin=");
			builder.append(pin);
			builder.append(", ");
		}
		if (msg != null) {
			builder.append("msg=");
			builder.append(msg);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
		}
		builder.append("]");
		return builder.toString();
	}

}
