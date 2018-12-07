/************************************************************************************************************
 * @(#) DevChargeMasterDataColonyDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;


/**
 * <p>
 * Module Class for Dev Charge Master Data Screen
 * </p>
 * @author Reshma (Tata Consultancy Services)
 * 
 */
public class DevChargeMasterDataColonyDetails {

	private String selectedZRO;

	private String colonyId;

	private String colonyName;

	private String chargeType;

	private String category;

	private String status;

	private String notificationDate;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the selectedZRO
	 */
	public String getSelectedZRO() {
		return selectedZRO;
	}

	/**
	 * @param selectedZRO
	 *            the selectedZRO to set
	 */
	public void setSelectedZRO(String selectedZRO) {
		this.selectedZRO = selectedZRO;
	}

	/**
	 * @return the colonyId
	 */
	public String getColonyId() {
		return colonyId;
	}

	/**
	 * @param colonyId
	 *            the colonyId to set
	 */
	public void setColonyId(String colonyId) {
		this.colonyId = colonyId;
	}

	/**
	 * @return the colonyName
	 */
	public String getColonyName() {
		return colonyName;
	}

	/**
	 * @param colonyName
	 *            the colonyName to set
	 */
	public void setColonyName(String colonyName) {
		this.colonyName = colonyName;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType
	 *            the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the notificationDate
	 */
	public String getNotificationDate() {
		return notificationDate;
	}

	/**
	 * @param notificationDate
	 *            the notificationDate to set
	 */
	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

}
