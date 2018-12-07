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
public class CaptchaDetails {
	private String captcha;

	private String captcha_token;

	private long created_at;
	private long expires_in;
	private String id;

	private Boolean isUsed;

	/**
	 * Message to be sent.
	 */
	private String msg;
	/**
	 * Status of the transaction which may be S=SUCCESS,F=FAIL,E=ERROR,EXCEPTION
	 */
	private String status;

	/**
	 * @param captcha
	 */
	public CaptchaDetails(String captcha) {
		this.captcha = captcha;
	}

	/**
	 * @param captcha_token
	 * @param created_at
	 * @param expires_in
	 * @param id
	 */
	public CaptchaDetails(String captcha_token, long expires_in, String id) {
		this.captcha_token = captcha_token;
		this.expires_in = expires_in;
		this.id = id;
	}

	/**
	 * @param msg
	 * @param status
	 */
	public CaptchaDetails(String msg, String status) {
		this.msg = msg;
		this.status = status;
	}

	/**
	 * @param captcha_token
	 * @param client_id
	 * @param created_at
	 * @param expires_in
	 * @param isUsed
	 */
	public CaptchaDetails(String captcha_token, String id, long created_at,
			long expires_in, boolean isUsed) {
		this.captcha_token = captcha_token;
		this.id = id;
		this.created_at = created_at;
		this.expires_in = expires_in;
		this.isUsed = isUsed;
	}

	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @return the captcha_token
	 */
	public String getCaptchaToken() {
		return captcha_token;
	}

	/**
	 * @return the created_at
	 */
	public long getCreatedAt() {
		return created_at;
	}

	/**
	 * @return the expires_in
	 */
	public long getExpiresIn() {
		return expires_in;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the isUsed
	 */
	public Boolean getIsUsed() {
		return isUsed;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param captcha
	 *            the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	/**
	 * @param captcha_token
	 *            the captcha_token to set
	 */
	public void setCaptchaToken(String captcha_token) {
		this.captcha_token = captcha_token;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreatedAt(long created_at) {
		this.created_at = created_at;
	}

	/**
	 * @param expires_in
	 *            the expires_in to set
	 */
	public void setExpiresIn(long expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param isUsed
	 *            the isUsed to set
	 */
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
