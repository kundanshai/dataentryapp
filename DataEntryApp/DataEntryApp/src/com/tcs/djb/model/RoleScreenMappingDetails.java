/************************************************************************************************************
 * @(#) RoleScreenMappingDetails.java   22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Model class for Role Screen Mapping Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 */
public class RoleScreenMappingDetails {
	private String accessBy;
	private String effectiveFromDate;
	private String effectiveToDate;
	private String hasAccess;
	private String mappedId;
	private String screenDesc;
	private String screenId;
	private String slNo;

	/**
	 * @return the accessBy
	 */
	public String getAccessBy() {
		return accessBy;
	}

	/**
	 * @return the effectiveFromDate
	 */
	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}

	/**
	 * @return the effectiveToDate
	 */
	public String getEffectiveToDate() {
		return effectiveToDate;
	}

	/**
	 * @return the hasAccess
	 */
	public String getHasAccess() {
		return hasAccess;
	}

	/**
	 * @return the mappedId
	 */
	public String getMappedId() {
		return mappedId;
	}

	/**
	 * @return the screenDesc
	 */
	public String getScreenDesc() {
		return screenDesc;
	}

	/**
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * @return the slNo
	 */
	public String getSlNo() {
		return slNo;
	}

	/**
	 * @param accessBy
	 *            the accessBy to set
	 */
	public void setAccessBy(String accessBy) {
		this.accessBy = accessBy;
	}

	/**
	 * @param effectiveFromDate
	 *            the effectiveFromDate to set
	 */
	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}

	/**
	 * @param effectiveToDate
	 *            the effectiveToDate to set
	 */
	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}

	/**
	 * @param hasAccess
	 *            the hasAccess to set
	 */
	public void setHasAccess(String hasAccess) {
		this.hasAccess = hasAccess;
	}

	/**
	 * @param mappedId
	 *            the mappedId to set
	 */
	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}

	/**
	 * @param screenDesc
	 *            the screenDesc to set
	 */
	public void setScreenDesc(String screenDesc) {
		this.screenDesc = screenDesc;
	}

	/**
	 * @param screenId
	 *            the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
}
