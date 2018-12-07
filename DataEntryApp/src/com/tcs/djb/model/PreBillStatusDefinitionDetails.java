/************************************************************************************************************
 * @(#) PreBillStatusDefinitionDetails.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Contains all the variables required for Pre Bill Status Definition details.
 * </p>
 * 
 */
public class PreBillStatusDefinitionDetails {

	private String description;
	private String lastUpdatedBy;
	private String lastUpdatedOn;
	private String preBillStatusId;

	public PreBillStatusDefinitionDetails(String preBillStatusId,
			String description,String lastUpdatedBy,String lastUpdatedOn) {
		this.description = description;
		this.preBillStatusId = preBillStatusId;
		this.lastUpdatedBy=lastUpdatedBy;
		this.lastUpdatedOn=lastUpdatedOn;
	}

	public String getDescription() {
		return description;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public String getPreBillStatusId() {
		return preBillStatusId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setPreBillStatusId(String preBillStatusId) {
		this.preBillStatusId = preBillStatusId;
	}
}
