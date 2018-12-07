/************************************************************************************************************
 * @(#) ExcelToDBTableResponse.java   02 Jun 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for capturing Excel to Database Table response details
 * </p>
 * 
 * @author Atanu Mondal (Tata Consultancy Services)
 * @since 02-06-2016 as per open project 1202,jTrac : DJB-4464
 */
public class ExcelToDBTableResponse {
	private String msg;
	private int noOfRecord;
	private Throwable throwable;

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the noOfRecord
	 */
	public int getNoOfRecord() {
		return noOfRecord;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @param noOfRecord
	 *            the noOfRecord to set
	 */
	public void setNoOfRecord(int noOfRecord) {
		this.noOfRecord = noOfRecord;
	}

	/**
	 * @param throwable
	 *            the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExcelToDBTableResponse [msg=");
		builder.append(msg);
		builder.append(", noOfRecord=");
		builder.append(noOfRecord);
		builder.append(", throwable=");
		builder.append(throwable);
		builder.append("]");
		return builder.toString();
	}

}
