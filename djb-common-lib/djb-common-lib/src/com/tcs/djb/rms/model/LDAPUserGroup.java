/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * <p>
 * Details of User Groups Created in LDAP.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * @since 29-05-2013
 * 
 */
public class LDAPUserGroup {
	private String applicationName ;
	private String cnValue ;
	private String description ;
	private String displayName ;
	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * @return the cnValue
	 */
	public String getCnValue() {
		return cnValue;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	/**
	 * @param cnValue the cnValue to set
	 */
	public void setCnValue(String cnValue) {
		this.cnValue = cnValue;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
