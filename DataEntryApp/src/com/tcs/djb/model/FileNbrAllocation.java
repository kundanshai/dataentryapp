/************************************************************************************************************
 * @(#) FileNbrAllocation.java   12 Apr 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * FileNbrAllocation class contains all the detail fields for File number
 * allocation screen as per jtrac DJB-4442 and OpenProject-CODE DEVELOPMENT
 * #867.
 * </p>
 * 
 * @since 12-04-2016
 * @author Lovely (Tata Consultancy Services)
 * @history: Lovely edited the code on 27-09-2016 as per jTrac
 *           DJB-4571,Openproject- #1492
 */
public class FileNbrAllocation {
	/**
	 * maximum range value.
	 */
	private String maxRange;

	/**
	 * minimum range value.
	 */
	private String minRange;

	/**
	 * ZRO code.
	 */
	private String selectedZROLocation;

	public String getMaxRange() {
		return maxRange;
	}

	public String getMinRange() {
		return minRange;
	}

	/**
	 * @return the selectedZROCode
	 */

	// Start:Added By Lovely on 27-09-2016 as per jTrac DJB-4571,Openproject-
	// #1492.
	/**
	 * Maximum range.
	 */
	private String maxFileRange;

	/**
	 * Minimum Range .
	 */
	private String minFileRange;
	/**
	 *UserId for tagging.
	 */
	private String UserIdTag;

	/**
	 *UserId for tagging.
	 */
	private String userIdUpdate;

	/**
	 * @return the maxFileRange
	 */
	public String getMaxFileRange() {
		return maxFileRange;
	}

	/**
	 * @param maxFileRange
	 *            the maxFileRange to set
	 */
	public void setMaxFileRange(String maxFileRange) {
		this.maxFileRange = maxFileRange;
	}

	/**
	 * @return the minFileRange
	 */
	public String getMinFileRange() {
		return minFileRange;
	}

	/**
	 * @param minFileRange
	 *            the minFileRange to set
	 */
	public void setMinFileRange(String minFileRange) {
		this.minFileRange = minFileRange;
	}

	/**
	 * @return the userIdTag
	 */
	public String getUserIdTag() {
		return UserIdTag;
	}

	/**
	 * @param userIdTag
	 *            the userIdTag to set
	 */
	public void setUserIdTag(String userIdTag) {
		UserIdTag = userIdTag;
	}

	/**
	 * @return the userIdUpdate
	 */
	public String getUserIdUpdate() {
		return userIdUpdate;
	}

	/**
	 * @param userIdUpdate
	 *            the userIdUpdate to set
	 */
	public void setUserIdUpdate(String userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	// End:Added By Lovely on 27-09-2016 as per jTrac DJB-4571,Openproject-
	// #1492.
	public String getSelectedZROCode() {
		return selectedZROLocation;
	}

	public String getSelectedZROLocation() {
		return selectedZROLocation;
	}

	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}

	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */

	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROLocation = selectedZROCode;
	}

	public void setSelectedZROLocation(String selectedZROLocation) {
		this.selectedZROLocation = selectedZROLocation;
	}

}
