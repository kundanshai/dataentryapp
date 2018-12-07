/**
 * 
 */
package com.tcs.djb.rms.model;

import java.util.List;

/**
 * <p>
 * List of User Groups Created in LDAP.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * @since 29-05-2013
 * 
 */
public class LDAPUserGroupList {
	private List<LDAPUserGroup> groupList;
	private String objectClass;

	/**
	 * @return the groupList
	 */
	public List<LDAPUserGroup> getGroupList() {
		return groupList;
	}

	/**
	 * @return the objectClass
	 */
	public String getObjectClass() {
		return objectClass;
	}

	/**
	 * @param groupList
	 *            the groupList to set
	 */
	public void setGroupList(List<LDAPUserGroup> groupList) {
		this.groupList = groupList;
	}

	/**
	 * @param objectClass
	 *            the objectClass to set
	 */
	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}
}
