/************************************************************************************************************
 * @(#) JsonList.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.List;

/**
 * <p>
 * Container for containing the Json List
 * </p>
 * 
 * @author Atanu Mondal (Tata Consultancy Services)
 */
public class JsonList {
	private MeterReadImgAuditDetails data;

	public JsonList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JsonList(MeterReadImgAuditDetails data) {
		super();
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public MeterReadImgAuditDetails getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(MeterReadImgAuditDetails data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonList [data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}
