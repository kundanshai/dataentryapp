/************************************************************************************************************
 * @(#) CaptchaDetails.java 1.0 19 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.model;

/**
 * <p>
 * Class for Captcha Details. It contains all the field used for captcha
 * validation.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 19-06-2015
 * 
 */
public class ChartDetails {
	private double value;

	private String xAxis;

	private String yAxis;

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value
	 * @param xAxis
	 * @param yAxis
	 */
	public ChartDetails(double value, String xAxis, String yAxis) {
		this.value = value;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/**
	 * @return the xAxis
	 */
	public String getxAxis() {
		return xAxis;
	}

	/**
	 * @return the yAxis
	 */
	public String getyAxis() {
		return yAxis;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @param xAxis the xAxis to set
	 */
	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	/**
	 * @param yAxis the yAxis to set
	 */
	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}
}
