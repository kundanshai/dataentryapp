/************************************************************************************************************
 * @(#) EmployeeLdapUtil.java 22 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import com.tcs.djb.model.EmployeeBean;

/**
 * <p>
 * This is an util class for populating employee details.
 * </p>
 *
 */
public class EmployeeLdapUtil {
	private static String USER_SEARCH_BASE = PropertyUtil
			.getProperty("USER_SEARCH_BASE");
	private static String USER_ID = "";
	private static String USER_PASSWORD = "";

	/**
	 * <p>
	 * This method creates the connection with the LDAP
	 * </p>
	 * 
	 * @method getLdapContext
	 * @return LdapContext
	 */

	@SuppressWarnings("unchecked")
	public static LdapContext getLdapContext() {
		AppLog.begin();
		LdapContext ctx = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, PropertyUtil
					.getProperty("INITIAL_CONTEXT_FACTORY"));
			env.put(Context.SECURITY_AUTHENTICATION, PropertyUtil
					.getProperty("SECURITY_AUTHENTICATION"));
			env.put(Context.SECURITY_PRINCIPAL, "uid=" + USER_ID + ","
					+ PropertyUtil.getProperty("USER_SEARCH_BASE"));
			env.put(Context.SECURITY_CREDENTIALS, USER_PASSWORD);
			// env.put(Context.SECURITY_PRINCIPAL,
			// PropertyUtil
			// .getProperty("SECURITY_PRINCIPAL"));
			// env.put(Context.SECURITY_CREDENTIALS, PropertyUtil
			// .getProperty("SECURITY_CREDENTIALS"));
			env.put(Context.PROVIDER_URL, PropertyUtil
					.getProperty("PROVIDER_URL"));
			ctx = new InitialLdapContext(env, null);
			// System.out.println(":::::::Connection Successful.:::::::");
		} catch (AuthenticationException ae) {
			AppLog.error("LDAP ERROR::INVALID CREDENTIAL::");
		} catch (NamingException nex) {
			AppLog.error("::LDAP ERROR::", nex);
		} catch (Exception ex) {
			AppLog.error("::LDAP ERROR::", ex);
		}
		AppLog.end();
		return ctx;
	}

	/**
	 * <p>
	 * This method is used populate employee details
	 * </p>
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static EmployeeBean populateEmployeeBean(String userId,
			String password) {
		AppLog.begin();
		EmployeeBean employeeBean = new EmployeeBean();
		USER_ID = userId;
		USER_PASSWORD = password;
		LdapContext ctx = getLdapContext();
		try {
			if (null != ctx) {
				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String[] attrIDs = { "cn", "sn", "mail", "mobile",
						"departmentNumber", "businessCategory",
						"physicalDeliveryOfficeName", "employeeType" };
				constraints.setReturningAttributes(attrIDs);

				NamingEnumeration answer = ctx.search(USER_SEARCH_BASE, "uid="
						+ userId, constraints);
				String fName[];
				String lName[];
				if (answer.hasMore()) {
					Attributes attrs = ((SearchResult) answer.next())
							.getAttributes();
					String Name = attrs.get("cn").toString();
					fName = Name.split("cn:");
					employeeBean.setfName(fName[1]);

					String LastName = attrs.get("sn").toString();
					lName = LastName.split("sn:");

					employeeBean.setlName(lName[1]);

					if (null != attrs.get("mail")) {
						employeeBean.setEmailId(attrs.get("mail").toString());
					} else {
						employeeBean.setEmailId("");
					}
					if (null != attrs.get("mobile")) {
						employeeBean
								.setMobileNo(attrs.get("mobile").toString());
					} else {
						employeeBean.setMobileNo("");
					}

					if (null != attrs.get("businessCategory")) {
						employeeBean.setUserGroup(attrs.get("businessCategory")
								.toString());
					} else {
						employeeBean.setUserGroup("");
					}
					if (null != attrs.get("physicalDeliveryOfficeName")) {
						employeeBean.setZone(attrs.get(
								"physicalDeliveryOfficeName").toString().split(
								":")[1].trim());
					} else {
						employeeBean.setZone("");
					}

					if (null != attrs.get("employeeType")) {
						employeeBean.setUserRole(attrs.get("employeeType")
								.toString());
					}
					employeeBean.setStatus("Success");
				} else {
					employeeBean.setStatus("Failure");
				}
			} else {
				employeeBean.setStatus("Failure");
			}
		} catch (Exception ex) {
			AppLog.error("::LDAP ERROR::", ex);
			employeeBean.setStatus("Failure");
		} finally {
			try {
				if (null != ctx) {
					ctx.close();
				}
			} catch (Exception ex) {
				AppLog.error("::LDAP ERROR::", ex);
			}
		}
		AppLog.end();
		return employeeBean;
	}

}
