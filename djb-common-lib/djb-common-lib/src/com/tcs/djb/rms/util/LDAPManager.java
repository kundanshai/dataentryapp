/************************************************************************************************************
 * @(#) LDAPManager.java 1.0 09 Oct 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.NoSuchAttributeException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.tcs.djb.rms.model.LDAPUserGroup;
import com.tcs.djb.rms.model.LDAPUserGroupList;
import com.tcs.djb.rms.model.UserDetails;

/**
 * <p>
 * Class to Interact with LDAP( OID ).
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 22-04-2013
 * 
 */
public class LDAPManager {
	private static String GROUP_CONTAINER = PropertyUtil
			.getProperty("GROUP_CONTAINER");
	private static String GROUP_SEARCH_BASE = PropertyUtil
			.getProperty("GROUP_SEARCH_BASE");
	private static String PWD_POLICY_CONTAINER = PropertyUtil
			.getProperty("PWD_POLICY_CONTAINER");
	private static String USER_ID = "";
	private static String USER_PASSWORD = "";
	private static String USER_SEARCH_BASE = PropertyUtil
			.getProperty("USER_SEARCH_BASE");
	private static String OB_PASSWORD_CHANGE_FLAG_VALUE = "1";

	/**
	 * <p>
	 * This method enables a user ID in LDAP.It removes the attribute
	 * <code>orclIsEnabled</code> to enable the user.
	 * <p>
	 * 
	 * @param userId
	 * @return
	 */
	public static String activateUser(String userId) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray = new ModificationItem[1];
			/* Update orclIsEnabled enable the user */
			Attribute orclIsEnabled = new BasicAttribute("orclIsEnabled");
			modificationItemArray[0] = new ModificationItem(
					DirContext.REMOVE_ATTRIBUTE, orclIsEnabled);
			ldapContext.modifyAttributes(getUserDN(userId),
					modificationItemArray);
			returnMessage = "SUCCESS";
		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;

	}

	/**
	 * <p>
	 * Add a User To LDAP. It Creates new Entry for the User.
	 * </p>
	 * 
	 * @param userDetails
	 * @return
	 */
	public static String addUser(UserDetails userDetails) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		Context result = null;
		try {
			/* Create a container set of attributes */
			Attributes container = new BasicAttributes();

			/* Create the object class to add */
			Attribute objClasses = new BasicAttribute("objectClass");
			objClasses.add("top");
			objClasses.add("person");
			objClasses.add("organizationalPerson");
			objClasses.add("inetOrgPerson");
			objClasses.add("orclUser");
			objClasses.add("orclUserV2");
			objClasses.add("oblixPersonPwdPolicy");
			/* Assign the user name */
			String userName = new StringBuffer(userDetails.getFirstName())
					.append(" ").append(userDetails.getLastName()).toString();
			Attribute cn = new BasicAttribute("cn", userName);
			Attribute displayName = new BasicAttribute("displayName",
					userName.toUpperCase());
			Attribute givenName = new BasicAttribute("givenName",
					userDetails.getFirstName());
			/* Add Surname */
			Attribute sn = new BasicAttribute("sn", userDetails.getLastName());
			/* Add user Id */
			Attribute uid = new BasicAttribute("uid", userDetails.getUserId());

			/* Add password */
			Attribute userPassword = new BasicAttribute("userpassword",
					userDetails.getPassword());
			/* Add address */
			Attribute homePostalAddress = new BasicAttribute(
					"homePostalAddress", userDetails.getUserAddress());
			/* Add email id */
			Attribute mail = new BasicAttribute("mail",
					userDetails.getEmailId());
			/* Add mobile no */
			Attribute mobile = new BasicAttribute("mobile",
					userDetails.getMobileNo());

			/* Add these attributes to the container */
			container.put(objClasses);
			container.put(cn);
			container.put(sn);
			container.put(givenName);
			container.put(uid);
			container.put(userPassword);
			container.put(displayName);
			container.put(homePostalAddress);
			container.put(mail);
			container.put(mobile);
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();

			/* Create the entry */
			result = ldapContext.createSubcontext(
					getUserDN(userDetails.getUserId()), container);
			returnMessage = "USER CREATED";
			/* Assign Group to the user */
			String[] groups = userDetails.getGroups();
			if (null != groups && groups.length > 0) {
				String groupName = "";
				// System.out.println("Added group::" + groups);
				for (int i = 0; i < groups.length; i++) {
					groupName = groups[i];
					if (null != groupName && !"".equalsIgnoreCase(groupName)) {
						/* Assign Group */
						assignUser(ldapContext, userDetails.getUserId(),
								groupName);
					}
				}
			}
			returnMessage = "SUCCESS";
		} catch (NameAlreadyBoundException e) {
			// AppLog.error(e);
			returnMessage = "User Already Exists.";
		} catch (NamingException e) {
			AppLog.error(e);
			returnMessage = "There was problem, Please Contact Administrator.";
		} catch (Exception ex) {
			AppLog.error(ex);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
				if (null != result) {
					result.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
			enableObPasswordChangeFlag(userDetails.getUserId());
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Assign a User to a group. User is mapped with the group.
	 * </p>
	 * 
	 * @param ldapContext
	 * @param username
	 * @param groupName
	 * @throws NamingException
	 */
	private static String assignUser(LdapContext ldapContext, String username,
			String groupName) throws NamingException {
		AppLog.begin();
		String returnMessage = "";
		try {
			ModificationItem[] mods = new ModificationItem[1];

			Attribute mod = new BasicAttribute("uniqueMember",
					getUserDN(username));
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
			ldapContext.modifyAttributes(getGroupDN(groupName), mods);
			returnMessage = "SUCCESS";
		} catch (AttributeInUseException e) {
			// If user is already added, ignore exception
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Validate LDAP User corresponding to User Id and Password provided and
	 * Populates User Details if validated by User Id and Password.
	 * </p>
	 * 
	 * @param userId
	 * @param password
	 * @return UserDetails with details if user is authorized, else sets Status
	 *         as Failure.
	 */
	public static UserDetails authenticateLDAPUser(String userId,
			String password) {
		AppLog.begin();
		UserDetails userDetails = new UserDetails();
		USER_ID = userId;
		USER_PASSWORD = password;
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					PropertyUtil.getProperty("INITIAL_CONTEXT_FACTORY"));
			env.put(Context.SECURITY_AUTHENTICATION,
					PropertyUtil.getProperty("SECURITY_AUTHENTICATION"));
			env.put(Context.SECURITY_PRINCIPAL, "uid=" + USER_ID + ","
					+ PropertyUtil.getProperty("USER_SEARCH_BASE"));
			env.put(Context.SECURITY_CREDENTIALS, USER_PASSWORD);
			env.put(Context.PROVIDER_URL,
					PropertyUtil.getProperty("PROVIDER_URL"));
			ctx = new InitialLdapContext(env, null);
			if (null != ctx) {
				userDetails.setAuthenticatedUser(true);
				AppLog.end();
				return populateLDAPUser(ctx, userDetails, userId, password);
			}
		} catch (OperationNotSupportedException ex) {
			String exceptionCause = ex.getMessage();
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCOUNTLOCKED") > -1) {
				userDetails
						.setStatus("Your account is locked due to multiple login attempt with wrong password. Please contact your Administrator to Unlock.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCTDISABLED") > -1) {
				userDetails
						.setStatus("Your Account has been disabled. Please contact your Administrator.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("PWDEXPIRED") > -1) {
				userDetails
						.setStatus("Your Password has expired. Please contact your Administrator to change your password.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("PWDMUSTCHANGE") > -1) {
				userDetails
						.setStatus("Your are a new User or Password has been reset. You must change your password before performing other operations.");
			}
			// System.out.println("::::Message::::" + userDetails.getStatus());
			// AppLog.error("::LDAP ERROR::", ex);
		} catch (NamingException e) {
			userDetails.setAuthenticatedUser(false);
			userDetails
					.setStatus("Invalid User Name/Password. Please try again.");
		} catch (Exception e) {
			userDetails.setAuthenticatedUser(false);
			userDetails
					.setStatus("Invalid User Name/Password. Please try again.");
			// e.printStackTrace();
			AppLog.error(e);
		} finally {
			try {
				if (null != ctx) {
					ctx.close();
				}
			} catch (Exception ex) {
				// AppLog.error("::LDAP ERROR::", ex);
			}
		}
		AppLog.end();
		return userDetails;
	}

	/**
	 * <p>
	 * This method disables a user ID in LDAP.It Updates
	 * <code>orclIsEnabled</code> to <code>DISABLED</code> to disable the user.
	 * <p>
	 * 
	 * @param userId
	 * @return
	 */
	public static String deactivateUser(String userId) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray = new ModificationItem[1];
			/* Update orclIsEnabled to DISABLED to disable the user */
			Attribute mobile = new BasicAttribute("orclIsEnabled", "DISABLED");
			modificationItemArray[0] = new ModificationItem(
					DirContext.ADD_ATTRIBUTE, mobile);
			ldapContext.modifyAttributes(getUserDN(userId),
					modificationItemArray);
			// System.out.println("Deactivated");
			returnMessage = "SUCCESS";
		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;

	}

	/***
	 * <p>
	 * Delete a User from LDAP. It also removes the user from the associated
	 * group.
	 * </p>
	 * 
	 * @see #removeUserFromGroup()
	 * @see #getGroups()
	 * @param userId
	 * @return
	 */
	public static String deletUser(String userId) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ldapContext.destroySubcontext(getUserDN(userId));
			// System.out.println("User Deleted");
			AppLog.debugInfo("User Id ::" + userId
					+ " Successfully Deleted from LDAP.");
			List<String> groups = getGroups(ldapContext, userId);
			for (int i = 0; null != groups && i < groups.size(); i++) {
				removeUserFromGroup(ldapContext, userId, (String) groups.get(i));
			}
			returnMessage = "SUCCESS";
		} catch (NameNotFoundException e) {
			returnMessage = "User Id Does not Exist.";
			// System.out.println("NameNotFound");
			// If the user is not found, ignore the error
		} catch (NamingException e) {
			// If the user is not found, ignore the error
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Exception while Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Display Win 32 Date.
	 * </p>
	 * 
	 * @param Win32FileTime
	 * @return
	 */
	static String DisplayWin32Date(String win32FileTime) {
		AppLog.begin();
		String win32Date = null;
		try {
			GregorianCalendar win32Epoch = new GregorianCalendar(1601,
					Calendar.JANUARY, 1);
			Long lWin32Epoch = win32Epoch.getTimeInMillis();
			Long lWin32FileTime = new Long(win32FileTime);
			win32Date = (DateFormat.getDateTimeInstance(DateFormat.FULL,
					DateFormat.FULL).format((lWin32FileTime / 10000)
					+ lWin32Epoch));
		} catch (Exception e) {
			// Ignore Exception
		}
		AppLog.end();
		return win32Date;
	}

	/**
	 * <p>
	 * Enable Ob Password Change Flag in LDAP. If value set 1 it will force user
	 * to change password at first login.
	 * </p>
	 * 
	 * @param userDetails
	 * @return
	 */
	public static boolean enableObPasswordChangeFlag(String userID) {
		AppLog.begin();
		LdapContext ldapContext = null;
		boolean updateSuccess = false;
		try {
			// System.out.println("ADMIN RESET");
			AppLog.debugInfo("Password has been Reset by Admin User for User ID::"
					+ userID);
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray = new ModificationItem[1];
			int i = 0;
			Attribute obpasswordchangeflag = new BasicAttribute(
					"obpasswordchangeflag", OB_PASSWORD_CHANGE_FLAG_VALUE);
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, obpasswordchangeflag);
			ldapContext.modifyAttributes(getUserDN(userID),
					modificationItemArray);
			updateSuccess = true;

		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return updateSuccess;
	}

	/**
	 * <p>
	 * Get all the Groups exists in LDAP.
	 * </p>
	 * 
	 * @param userId
	 * @return getGroups
	 * @throws NamingException
	 */
	public static List<LDAPUserGroupList> getAllGroups() throws NamingException {
		AppLog.begin();
		LdapContext ldapContext = null;
		List<LDAPUserGroupList> ldapUserGroupListOfList = new ArrayList<LDAPUserGroupList>();
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			SearchControls ctls = new SearchControls();
			String[] attrIDs = { "cn", "displayName", "description", "ou" };
			ctls.setReturningAttributes(attrIDs);
			ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			LDAPUserGroupList ldapUserGroupList = null;
			LDAPUserGroup ldapUserGroup = null;
			List<LDAPUserGroup> groupList = null;
			NamingEnumeration<?> answer = null;
			String[] objectClass = PropertyUtil
					.getProperty("LDAP_OBJECT_CLASS").split(",");
			String groupName = "";
			String groupDisplayName = "";
			String groupDescription = "";
			String applicationName = "";
			for (int i = 0; null != objectClass && i < objectClass.length; i++) {
				ldapUserGroupList = new LDAPUserGroupList();
				groupList = new ArrayList<LDAPUserGroup>();
				answer = ldapContext.search(GROUP_CONTAINER, "(objectclass="
						+ objectClass[i] + ")", ctls);
				while (answer.hasMore()) {
					groupName = "";
					groupDisplayName = "";
					groupDescription = "";
					applicationName = "";
					SearchResult rslt = (SearchResult) answer.next();
					Attributes attrs = rslt.getAttributes();
					ldapUserGroup = new LDAPUserGroup();
					groupName = (null != attrs.get("cn")) ? (attrs.get("cn")
							.toString()) : "";
					if (!"".equals(groupName)) {
						groupName = (groupName
								.substring(groupName.indexOf(':') + 1)).trim();
					}
					groupDisplayName = (null != attrs.get("displayName")) ? attrs
							.get("displayName").toString() : "";
					if (!"".equals(groupDisplayName)) {
						groupDisplayName = (groupDisplayName
								.substring(groupDisplayName.indexOf(':') + 1))
								.trim();
					}
					applicationName = (null != attrs.get("ou")) ? attrs.get(
							"ou").toString() : "";
					if (!"".equals(applicationName)) {
						applicationName = (applicationName
								.substring(applicationName.indexOf(':') + 1))
								.trim();
					}
					groupDescription = null != attrs.get("description") ? attrs
							.get("description").toString() : "";
					// System.out.println("objectClass::" + objectClass[i]
					// + "::Group::" + groupName + "::" + groupDisplayName
					// + "::" + groupDescription + "::" + attrs.get("ou"));
					if (null != groupName && !"".equals(groupName)
							&& null != groupDisplayName
							&& !"".equals(groupDisplayName)) {
						ldapUserGroup.setCnValue(groupName);
						ldapUserGroup.setDisplayName(groupDisplayName);
						ldapUserGroup.setDescription(groupDescription);
						ldapUserGroup.setApplicationName(applicationName);
						groupList.add(ldapUserGroup);
					}
				}
				ldapUserGroupList.setObjectClass(objectClass[i]);
				ldapUserGroupList.setGroupList(groupList);
				ldapUserGroupListOfList.add(ldapUserGroupList);
			}

		} catch (AuthenticationException ae) {
			AppLog.error(ae);
		} catch (NamingException nex) {
			AppLog.error(nex);
		} catch (Exception ex) {
			AppLog.error(ex);
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception ex) {
				AppLog.error(ex);
			}
		}
		AppLog.end();
		return ldapUserGroupListOfList;
	}

	/**
	 * <p>
	 * Retrieve all the user created in LDAP with the object class
	 * <code>orclUser</code>.
	 * </p>
	 * 
	 * @return
	 * @throws NamingException
	 */
	public static List<UserDetails> getAllUser() throws NamingException {
		List<UserDetails> userList = new ArrayList<UserDetails>();
		LdapContext ldapContext = null;
		UserDetails userDetails = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			// Set up search constraints
			SearchControls ctls = new SearchControls();
			String[] attrIDs = { "cn", "uid", "homePostalAddress", "mail",
					"mobile", "orclIsEnabled" };
			ctls.setReturningAttributes(attrIDs);
			ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			// Set up criteria to search on
			String filter = new StringBuffer().append("(&")
					.append("(objectClass=orclUser)").append(")").toString();

			NamingEnumeration<?> results = ldapContext.search(USER_SEARCH_BASE,
					filter, ctls);
			while (results.hasMore()) {
				SearchResult result = (SearchResult) results.next();
				Attributes attrs = result.getAttributes();
				// users.add(result.getName(""));
				if (null != attrs.get("homePostalAddress")) {
					userDetails = new UserDetails();
					// System.out.println("UID::" + attrs.get("uid") + "::CN::"
					// + attrs.get("cn") + "::Address::"
					// + attrs.get("homePostalAddress") + "::Email::"
					// + attrs.get("mail"));
					String userId = attrs.get("uid").get().toString();
					// userId = userId.substring(userId.indexOf(':') +
					// 1).trim();

					String userName = attrs.get("cn").get().toString();
					// userName = userName.substring(userName.indexOf(':') + 1)
					// .trim();

					String userAddress = attrs.get("homePostalAddress").get()
							.toString();
					// userAddress = userAddress.substring(
					// userAddress.indexOf(':') + 1).trim();

					String emailId = null != attrs.get("mail") ? attrs
							.get("mail").get().toString() : "";
					// emailId = emailId.substring(emailId.indexOf(':') + 1)
					// .trim();
					String mobile = null != attrs.get("mobile") ? attrs
							.get("mobile").get().toString() : "";
					String isActive = (null != attrs.get("orclIsEnabled") && ("DISABLED")
							.equalsIgnoreCase(attrs.get("orclIsEnabled").get()
									.toString())) ? "Disabled" : "Enabled";
					userDetails.setUserId(userId);
					userDetails.setUserName(userName);
					userDetails.setUserAddress(userAddress);
					userDetails.setEmailId(emailId);
					userDetails.setMobileNo(mobile);
					userDetails.setIsActive(isActive);
					userList.add(userDetails);
				}
			}
		} catch (NamingException e) {
			// If the user is not found, ignore the error
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Exception for Closing Contexts
			}
		}
		AppLog.end();
		return userList;
	}

	/**
	 * <p>
	 * get Group CN for a group DN.
	 * </p>
	 * 
	 * @param groupDN
	 * @return
	 */
	public static String getGroupCN(String groupDN) {
		int start = groupDN.indexOf("=");
		int end = groupDN.indexOf(",");

		if (end == -1) {
			end = groupDN.length();
		}
		return groupDN.substring(start + 1, end);
	}

	/**
	 * <p>
	 * get Group DN for a user.
	 * </p>
	 * 
	 * @param name
	 * @return
	 */
	private static String getGroupDN(String name) {
		AppLog.begin();
		try {
			AppLog.end();
			return new StringBuffer().append("cn=").append(name).append(",")
					.append(GROUP_SEARCH_BASE).toString();
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return null;
	}

	/**
	 * <p>
	 * get Group List of a user by userId.
	 * </p>
	 * 
	 * @param ctx
	 *            LdapContext
	 * @param userId
	 * @return
	 * @throws NamingException
	 */
	private static List<String> getGroups(LdapContext ctx, String userId)
			throws NamingException {
		List<String> groupList = new ArrayList<String>();
		// Set up criteria to search on
		String filter = new StringBuffer().append("(&")
				.append("(objectClass=orclGroup)").append("(uniqueMember=")
				.append(getUserDN(userId)).append(")").append(")").toString();

		// Set up search constraints
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.ONELEVEL_SCOPE);

		NamingEnumeration<?> results = ctx.search(GROUP_SEARCH_BASE, filter,
				cons);

		while (results.hasMore()) {
			SearchResult result = (SearchResult) results.next();
			groupList.add(getGroupCN(result.getName()));
			// System.out.println("Groups::" + getGroupCN(result.getName()));
		}

		return groupList;
	}

	/**
	 * <p>
	 * This method creates the connection with the LDAP for a Administrator.
	 * </p>
	 * 
	 * @return LdapContext
	 */
	private static LdapContext getLdapContextForAdmin() {
		AppLog.begin();
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					PropertyUtil.getProperty("INITIAL_CONTEXT_FACTORY"));
			env.put(Context.SECURITY_AUTHENTICATION,
					PropertyUtil.getProperty("SECURITY_AUTHENTICATION"));
			env.put(Context.SECURITY_PRINCIPAL,
					PropertyUtil.getProperty("SECURITY_PRINCIPAL"));
			env.put(Context.SECURITY_CREDENTIALS,
					PropertyUtil.getProperty("SECURITY_CREDENTIALS"));
			env.put(Context.PROVIDER_URL,
					PropertyUtil.getProperty("PROVIDER_URL"));
			ctx = new InitialLdapContext(env, null);
		} catch (AuthenticationException ae) {
			AppLog.error(ae);
			// ae.printStackTrace();
		} catch (NamingException nex) {
			AppLog.error(nex);
			// nex.printStackTrace();
		} catch (Exception ex) {
			AppLog.error(ex);
			// ex.printStackTrace();
		}
		AppLog.end();
		return ctx;
	}

	/**
	 * <p>
	 * Retrieve user details created in LDAP.
	 * </p>
	 * 
	 * @history 24-01-2014 Matiur Rahman Added feature to Check OAM lock.
	 * @param userId
	 * @return
	 * @throws NamingException
	 */
	public static UserDetails getUserDetails(String userId)
			throws NamingException {
		AppLog.begin();
		LdapContext ldapContext = null;
		UserDetails userDetails = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			// Set up search constraints
			/*
			 * Added oblockouttime in the search criteria By Matiur Rahman on
			 * 24-01-2014
			 */
			SearchControls ctls = new SearchControls();
			String[] attrIDs = { "cn", "uid", "givenName", "sn",
					"homePostalAddress", "mail", "mobile", "orclIsEnabled",
					"userPassword", "oblockouttime" };
			ctls.setReturningAttributes(attrIDs);
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// Set up criteria to search on
			String filter = new StringBuffer().append("uid=" + userId)
					.toString();

			NamingEnumeration<?> results = ldapContext.search(USER_SEARCH_BASE,
					filter, ctls);
			while (results.hasMore()) {
				SearchResult result = (SearchResult) results.next();
				Attributes attrs = result.getAttributes();
				// users.add(result.getName(""));
				if (null != attrs.get("homePostalAddress")) {
					userDetails = new UserDetails();
					// System.out.println("UID::" + attrs.get("uid") + "::CN::"
					// + attrs.get("cn") + "::Address::"
					// + attrs.get("homePostalAddress") + "::Email::"
					// + attrs.get("mail") + "::Mobile::"
					// + attrs.get("mobile") + "::Passwd::"
					// + attrs.get("userPassword") + "::"
					// + attrs.get("authpassword;oid") + "::"
					// + attrs.get("authpassword;orclcommonpwd"));
					userId = attrs.get("uid").get().toString();

					// String userName = attrs.get("cn").get().toString();

					String firstName = attrs.get("givenName").get().toString();
					String lastName = attrs.get("sn").get().toString();
					String userAddress = null != attrs.get("homePostalAddress")
							.get() ? attrs.get("homePostalAddress").get()
							.toString() : "";

					String emailId = null != attrs.get("mail") ? attrs
							.get("mail").get().toString() : "";
					String mobileNo = null != attrs.get("mobile") ? attrs
							.get("mobile").get().toString() : "";
					String isActive = (null != attrs.get("orclIsEnabled") && ("DISABLED")
							.equalsIgnoreCase(attrs.get("orclIsEnabled").get()
									.toString())) ? "NO" : "YES";
					/* Added to check OAM Lock out */
					String oamLockoutFlag = (null != attrs.get("oblockouttime")) ? "YES"
							: "NO";
					userDetails.setUserId(userId);
					userDetails.setFirstName(firstName);
					userDetails.setLastName(lastName);
					userDetails.setUserAddress(userAddress);
					userDetails.setEmailId(emailId);
					userDetails.setMobileNo(mobileNo);
					userDetails.setIsActive(isActive);
					userDetails.setIsLocked(oamLockoutFlag);
				}
				if (null != userDetails.getUserId()
						&& !"".equals(userDetails.getUserId())) {
					if (null != userDetails.getIsLocked()
							&& !"YES".equals(userDetails.getIsLocked())) {
						userDetails.setIsLocked(isLockedLDAPUser(ldapContext,
								userDetails.getUserId()));
					}
					List<String> groupList = getGroups(ldapContext,
							userDetails.getUserId());
					if (null != groupList && groupList.size() > 0) {
						String[] groups = new String[groupList.size()];
						for (int i = 0; i < groupList.size(); i++) {
							groups[i] = groupList.get(i);
						}
						userDetails.setGroups(groups);
					}
				}
			}
		} catch (NamingException e) {
			// If the user is not found, ignore the error
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Exception for Closing Contexts
			}
		}
		AppLog.end();
		return userDetails;
	}

	/**
	 * <p>
	 * get User DN for a user.
	 * </p>
	 * 
	 * @param username
	 * @return
	 */
	private static String getUserDN(String username) {
		AppLog.begin();
		try {
			AppLog.end();
			return new StringBuffer().append("uid=").append(username)
					.append(",").append(USER_SEARCH_BASE).toString();
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return null;
	}

	/**
	 * <p>
	 * Check if a User already exists.
	 * </p>
	 * 
	 * @param userId
	 * @return EXISTS if exists, NOT_EXISTS if does not exist, ERROR if there is
	 *         any problem or Exception while connecting LDAP.
	 * @throws NamingException
	 */
	public static String isLDAPUser(String userId) throws NamingException {
		AppLog.begin();
		String returnMessage = "INVALID";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			if (null != ldapContext) {
				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String[] attrIDs = { "cn" };
				constraints.setReturningAttributes(attrIDs);

				NamingEnumeration<?> answer = ldapContext.search(
						USER_SEARCH_BASE, "uid=" + userId, constraints);
				String fName[];
				if (answer.hasMore()) {
					Attributes attrs = ((SearchResult) answer.next())
							.getAttributes();
					String Name = attrs.get("cn").toString();
					fName = Name.split("cn:");
					if (null != fName[1] && !"".equals(fName[1])) {
						returnMessage = "EXISTS";
					} else {
						returnMessage = "NOT_EXISTS";
					}
				} else {
					returnMessage = "NOT_EXISTS";
				}
			} else {
				returnMessage = "ERROR";
			}
		} catch (AuthenticationException ae) {
			AppLog.error(ae);
			returnMessage = "ERROR";
		} catch (NamingException nex) {
			AppLog.error(nex);
			returnMessage = "ERROR";
		} catch (Exception ex) {
			AppLog.error(ex);
			returnMessage = "ERROR";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception ex) {
				AppLog.error(ex);
			}
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Check if a User is Locked. Account is locked out for attempting to login
	 * with wrong password.
	 * </p>
	 * 
	 * @param userId
	 * @return NO if ACTIVE, YES if LOCKED.
	 * @throws NamingException
	 */
	public static String isLockedLDAPUser(LdapContext ldapContext, String userId)
			throws NamingException {
		AppLog.begin();
		String isLocked = "NO";
		try {
			/* get the domain lockout duration policy */
			Attributes attrs = ldapContext.getAttributes(PWD_POLICY_CONTAINER);
			// System.out.println("Duration: "
			// + attrs.get("pwdLockoutDuration").get());
			// System.out.println("Threshold: " + attrs.get("pwdMaxAge").get());
			Long lockoutDuration = Long.parseLong(attrs
					.get("pwdLockoutDuration").get().toString());

			/* Create the search controls */
			SearchControls searchCtls = new SearchControls();

			/* Specify the attributes to return */
			String returnedAttributes[] = { "uid", "givenName",
					"pwdaccountlockedtime" };
			searchCtls.setReturningAttributes(returnedAttributes);

			/* Specify the search scope */
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			/*
			 * Create the correct LDAP search filter
			 */
			/*
			 * Win32 file time is based from 1/1/1601 Java date/time is based
			 * from 1/1/1970
			 */
			GregorianCalendar win32Epoch = new GregorianCalendar(1601,
					Calendar.JANUARY, 1);
			GregorianCalendar today = new GregorianCalendar();
			Long win32Date = win32Epoch.getTimeInMillis();
			Long todaysDate = today.getTimeInMillis();
			Long timeSinceWin32Epoch = todaysDate - win32Date;

			Long lockoutDate = (timeSinceWin32Epoch * 10000) + lockoutDuration;
			// System.out.println("Lockout (Long): " + lockoutDate.toString());
			// System.out.println("Lockout (Date): "
			// + DisplayWin32Date(lockoutDate.toString()));
			String searchFilter = "(&(objectClass=orclUser)(pwdaccountlockedtime>="
					+ lockoutDate + ")(uid=" + userId + "))";
			/* Search for objects using the filter */
			NamingEnumeration<?> answer = ldapContext.search(USER_SEARCH_BASE,
					searchFilter, searchCtls);
			/* Loop through the search results */
			if (answer.hasMoreElements()) {
				isLocked = "YES";
			}
			// System.out.println("isLocked:: " + isLocked);

		} catch (NamingException e) {
			// If the user is not found, ignore the error
		}
		AppLog.end();
		return isLocked;
	}

	/**
	 * <p>
	 * Populate LDAP User Details corresponding to User Id and Password provided
	 * and Populates User Details if validated by User Id and Password.
	 * </p>
	 * 
	 * @param userId
	 * @param password
	 * @return UserDetails with details if user is authorized, else sets Status
	 *         as Failure.
	 */
	public static UserDetails populateLDAPUser(LdapContext ctx,
			UserDetails userDetails, String userId, String password) {
		AppLog.begin();
		if (null == userDetails) {
			userDetails = new UserDetails();
		}
		try {
			if (null != ctx) {
				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String[] attrIDs = { "cn", "sn", "mail", "mobile",
						"departmentNumber", "businessCategory",
						"physicalDeliveryOfficeName", "employeeType" };
				constraints.setReturningAttributes(attrIDs);

				NamingEnumeration<?> answer = ctx.search(USER_SEARCH_BASE,
						"uid=" + userId, constraints);
				String fName[];
				String lName[];
				if (answer.hasMore()) {
					Attributes attrs = ((SearchResult) answer.next())
							.getAttributes();
					String Name = attrs.get("cn").toString();
					fName = Name.split("cn:");
					userDetails.setFirstName(fName[1]);

					String LastName = attrs.get("sn").toString();
					lName = LastName.split("sn:");

					userDetails.setLastName(lName[1]);
					if (null != attrs.get("mail")) {
						userDetails.setEmailId(attrs.get("mail").toString());
					} else {
						userDetails.setEmailId("");
					}
					if (null != attrs.get("mobile")) {
						userDetails.setMobileNo(attrs.get("mobile").toString());
					} else {
						userDetails.setMobileNo("");
					}

					if (null != attrs.get("businessCategory")) {
						userDetails.setUserGroup(attrs.get("businessCategory")
								.toString());
					} else {
						userDetails.setUserGroup("");
					}
					if (null != attrs.get("physicalDeliveryOfficeName")) {
						userDetails.setZone(attrs
								.get("physicalDeliveryOfficeName").toString()
								.split(":")[1].trim());
					} else {
						userDetails.setZone("");
					}

					if (null != attrs.get("employeeType")) {
						userDetails.setUserRole(attrs.get("employeeType")
								.toString());
					}
					userDetails.setStatus("Success");
				} else {
					userDetails.setStatus("Failure");
				}
			} else {
				userDetails
						.setStatus("There was problem while communicating with User Directory Server, Please Contact Administrator.");
			}
		} catch (OperationNotSupportedException ex) {
			String exceptionCause = ex.getMessage();
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCOUNTLOCKED") > -1) {
				userDetails.setAuthenticationErrorCode("ACCOUNTLOCKED");
				userDetails
						.setStatus("Your account is locked due to multiple login attempt with wrong password. Please contact your Administrator to Unlock.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCTDISABLED") > -1) {
				userDetails.setAuthenticationErrorCode("ACCTDISABLED");
				userDetails
						.setStatus("Your Account has been disabled. Please contact your Administrator.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("PWDEXPIRED") > -1) {
				userDetails.setAuthenticationErrorCode("PWDEXPIRED");
				userDetails
						.setStatus("Your Password has expired. Please contact your Administrator to change your password.");
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("PWDMUSTCHANGE") > -1) {
				userDetails.setAuthenticationErrorCode("PWDMUSTCHANGE");
				userDetails
						.setStatus("Your are a new User or Password has been reset. You must change your password before performing other operations.");
			}
			// System.out.println("::::Message::::" + userDetails.getStatus());
			// AppLog.error("::LDAP ERROR::", ex);
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
		} finally {
			try {
				if (null != ctx) {
					ctx.close();
				}
			} catch (Exception ex) {
				// AppLog.error("::LDAP ERROR::", ex);
			}
		}
		AppLog.end();
		return userDetails;
	}

	/***
	 * <p>
	 * Remove User from a group by userId.
	 * </p>
	 * 
	 * @param ctx
	 * @param userId
	 * @param groupName
	 * @throws NamingException
	 */
	private static String removeUserFromGroup(LdapContext ctx, String userId,
			String groupName) throws NamingException {
		AppLog.begin();
		String returnMessage = "";
		try {
			ModificationItem[] mods = new ModificationItem[1];

			Attribute mod = new BasicAttribute("uniqueMember",
					getUserDN(userId));
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod);
			ctx.modifyAttributes(getGroupDN(groupName), mods);
			// System.out.println("Removed ::" + userId + " from " + groupName);
			AppLog.debugInfo("User Id ::" + userId
					+ " Successfully Removed from ::" + groupName);
			returnMessage = "SUCCESS";
		} catch (NoSuchAttributeException e) {
			// If user is not assigned, ignore the error
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <P>
	 * Default Password set on the page. This is for Updating user Profile. If
	 * password is found to be equal to DEFAULT_PASSWORD value, the password
	 * will not be updated.
	 * </p>
	 */
	public static final String DEFAULT_PASSWORD = "Def@ultpassw0rd";

	/**
	 * <p>
	 * Reset self Password. Reset user Password modifies the existing
	 * <code>userpassword</code> attributes in LDAP after validating old
	 * password.
	 * </p>
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public static String resetSelfPassword(String userId, String oldPassword,
			String newPassword) {
		AppLog.begin();
		String returnMessage = "FAILED";
		LdapContext ldapContext = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					PropertyUtil.getProperty("INITIAL_CONTEXT_FACTORY"));
			env.put(Context.SECURITY_AUTHENTICATION,
					PropertyUtil.getProperty("SECURITY_AUTHENTICATION"));
			env.put(Context.SECURITY_PRINCIPAL, "uid=" + userId + ","
					+ PropertyUtil.getProperty("USER_SEARCH_BASE"));
			env.put(Context.SECURITY_CREDENTIALS, oldPassword);
			env.put(Context.PROVIDER_URL,
					PropertyUtil.getProperty("PROVIDER_URL"));
			ldapContext = new InitialLdapContext(env, null);
			if (null != ldapContext) {
				ModificationItem[] modificationItemArray;
				modificationItemArray = new ModificationItem[1];
				int i = 0;

				/* Update password */
				if (null != newPassword && !"".equals(newPassword)
						&& !(DEFAULT_PASSWORD).equals(newPassword)) {
					Attribute userpassword = new BasicAttribute("userpassword",
							newPassword);
					modificationItemArray[i++] = new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, userpassword);
					ldapContext.modifyAttributes(getUserDN(userId),
							modificationItemArray);
					returnMessage = "SUCCESS";
				}
			}
		} catch (OperationNotSupportedException ex) {
			String exceptionCause = ex.getMessage();
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCOUNTLOCKED") > -1) {
				returnMessage = "Your account is locked due to multiple invalid attempt with wrong password. Please contact your Administrator to Unlock.";
			}
			if (null != exceptionCause
					&& exceptionCause.indexOf("ACCTDISABLED") > -1) {
				returnMessage = "Your Account has been disabled. Please contact your Administrator.";
			}
			// AppLog.error("::LDAP ERROR::", ex);
		} catch (NamingException e) {
			returnMessage = "Invalid Old Password. Please try again with your Correct Old Password. <br/><font color='blue'>Note:Your account may be locked due to multiple invalid attempt with wrong password.</font>";
			// AppLog.error(e);
			// e.printStackTrace();
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Reset a User Password. Reset user Password modifies the existing
	 * <code>userpassword</code> attributes in LDAP with an admin user.
	 * </p>
	 * 
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	public static String resetUserPassword(String userId, String newPassword) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray;

			modificationItemArray = new ModificationItem[1];

			int i = 0;

			/* Update password */
			if (null != newPassword && !"".equals(newPassword)
					&& !(DEFAULT_PASSWORD).equals(newPassword)) {
				Attribute userpassword = new BasicAttribute("userpassword",
						newPassword);
				modificationItemArray[i++] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE, userpassword);
			}
			ldapContext.modifyAttributes(getUserDN(userId),
					modificationItemArray);
			returnMessage = "SUCCESS";
		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Unlock the user ID in LDAP those are locked due to attempting with wrong
	 * password.It Update <code>orclpwdaccountunlock</code> attribute to 1 to
	 * unlock the user.
	 * <p>
	 * 
	 * @history 24-01-2014 Matiur Rahman Added feature to OAM unlock.
	 * @param userId
	 * @return
	 */
	public static String unlockUser(String userId) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray = new ModificationItem[2];
			/* Update orclpwdaccountunlock to 1 to unlock the user */
			Attribute orclpwdaccountunlock = new BasicAttribute(
					"orclpwdaccountunlock", "1");
			/* Update oblockouttime to blank to unlock the user for OAM lockout */
			Attribute oblockouttime = new BasicAttribute("oblockouttime", "");
			modificationItemArray[0] = new ModificationItem(
					DirContext.ADD_ATTRIBUTE, orclpwdaccountunlock);
			modificationItemArray[1] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, oblockouttime);
			ldapContext.modifyAttributes(getUserDN(userId),
					modificationItemArray);
			returnMessage = "SUCCESS";
		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;

	}

	/**
	 * <p>
	 * Update User Details in LDAP. Update user details modifies the existing
	 * attributes in LDAP.
	 * </p>
	 * 
	 * @param userDetails
	 * @return
	 */
	public static String updateUser(UserDetails userDetails) {
		AppLog.begin();
		String returnMessage = "";
		LdapContext ldapContext = null;
		try {
			/* Create Ldap Context for Administrator */
			ldapContext = getLdapContextForAdmin();
			ModificationItem[] modificationItemArray;
			if (null != userDetails.getPassword()
					&& !"".equals(userDetails.getPassword())
					&& !(DEFAULT_PASSWORD).equals(userDetails.getPassword())) {
				modificationItemArray = new ModificationItem[8];
			} else {
				modificationItemArray = new ModificationItem[7];
			}
			int i = 0;

			/* Update the user name. */
			String userName = new StringBuffer(userDetails.getFirstName())
					.append(" ").append(userDetails.getLastName()).toString();
			Attribute cn = new BasicAttribute("cn", userName);
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, cn);
			Attribute displayName = new BasicAttribute("displayName",
					userName.toUpperCase());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, displayName);
			Attribute givenName = new BasicAttribute("givenName",
					userDetails.getFirstName());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, givenName);
			/* Update Surname */
			Attribute sn = new BasicAttribute("sn", userDetails.getLastName());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, sn);
			/* Update password */
			if (null != userDetails.getPassword()
					&& !"".equals(userDetails.getPassword())
					&& !(DEFAULT_PASSWORD).equals(userDetails.getPassword())) {
				Attribute userPassword = new BasicAttribute("userpassword",
						userDetails.getPassword());
				modificationItemArray[i++] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE, userPassword);
			}
			/* Update address */
			Attribute homePostalAddress = new BasicAttribute(
					"homePostalAddress", userDetails.getUserAddress());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, homePostalAddress);
			/* Update email id */
			Attribute mail = new BasicAttribute("mail",
					userDetails.getEmailId());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, mail);
			/* Update mobile no */
			Attribute mobile = new BasicAttribute("mobile",
					userDetails.getMobileNo());
			modificationItemArray[i++] = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, mobile);
			ldapContext.modifyAttributes(getUserDN(userDetails.getUserId()),
					modificationItemArray);
			returnMessage = "USER DETAILS UPDATE SUCCESS";
			/* Update access to groups */
			returnMessage = updateUserGroups(ldapContext, userDetails);

		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
			if (null != userDetails.getPassword()
					&& !"".equals(userDetails.getPassword())
					&& !(DEFAULT_PASSWORD).equals(userDetails.getPassword())) {
				enableObPasswordChangeFlag(userDetails.getUserId());
			}

		}
		AppLog.end();
		return returnMessage;
	}

	/**
	 * <p>
	 * Update User Groups for a User. It finds the existing groups mapped and
	 * then add or removes the mapping according to the selected groups.
	 * </p>
	 * 
	 * @param ldapContext
	 * @param userDetails
	 * @return
	 */
	private static String updateUserGroups(LdapContext ldapContext,
			UserDetails userDetails) {
		AppLog.begin();
		String returnMessage = "";
		try {
			/* Get existing Groups */
			String[] existingGroupsArray = null;
			String existingGroups = "";
			String addedGroups = "";
			String removedGroups = "";
			if (null != userDetails.getUserId()
					&& !"".equals(userDetails.getUserId())) {
				/* Retrieving existing groups. */
				List<String> groupList = getGroups(ldapContext,
						userDetails.getUserId());
				if (null != groupList && groupList.size() > 0) {
					existingGroupsArray = new String[groupList.size()];
					for (int c = 0; c < groupList.size(); c++) {
						existingGroupsArray[c] = groupList.get(c);
						existingGroups = existingGroups + groupList.get(c)
								+ ",";
					}
				}
			}
			String[] selectedGroupsArray = userDetails.getGroups();
			String selectedGroups = "";
			if (null != selectedGroupsArray && selectedGroupsArray.length > 0) {
				for (int j = 0; j < selectedGroupsArray.length; j++) {
					if (null != selectedGroupsArray[j]
							&& !"".equalsIgnoreCase(selectedGroupsArray[j])) {
						if ("".equals(existingGroups.trim())
								|| existingGroups
										.indexOf(selectedGroupsArray[j]) == -1) {
							addedGroups = addedGroups + selectedGroupsArray[j]
									+ ",";
						}
					}
					selectedGroups = selectedGroups + selectedGroupsArray[j]
							+ ",";
				}
			}
			// System.out.println("selectedGroups::" + selectedGroups
			// + "\nexistingGroups::" + existingGroups);
			if (null != existingGroupsArray && existingGroupsArray.length > 0) {
				for (int j = 0; j < existingGroupsArray.length; j++) {
					if (null != existingGroupsArray[j]
							&& !"".equalsIgnoreCase(existingGroupsArray[j])) {
						if ("".equals(selectedGroups.trim())
								|| selectedGroups
										.indexOf(existingGroupsArray[j]) == -1) {
							removedGroups = removedGroups
									+ existingGroupsArray[j] + ",";
						}
					}
				}
			}
			// System.out.println("Added Groups::" + addedGroups
			// + "\nRemoved Groups::" + removedGroups);
			String[] addedGroupsArray = addedGroups.split(",");
			String[] removedGroupsArray = removedGroups.split(",");

			/* Assign Group to the user */
			if (null != addedGroupsArray && addedGroupsArray.length > 0) {
				String groupName = "";
				// System.out.println("Added group::" + groups);
				for (int j = 0; j < addedGroupsArray.length; j++) {
					groupName = addedGroupsArray[j];

					if (null != groupName && !"".equalsIgnoreCase(groupName)) {
						/* Assign Group */
						assignUser(ldapContext, userDetails.getUserId(),
								groupName);
					}
				}
			}
			/* Removing Group from the user */
			if (null != removedGroupsArray && removedGroupsArray.length > 0) {
				String groupName = "";
				// System.out.println("Added group::" + groups);
				for (int j = 0; j < removedGroupsArray.length; j++) {
					groupName = removedGroupsArray[j];

					if (null != groupName && !"".equalsIgnoreCase(groupName)) {
						/* Remove the User from the Group */
						removeUserFromGroup(ldapContext,
								userDetails.getUserId(), groupName);
					}
				}
			}
			returnMessage = "SUCCESS";
		} catch (Exception e) {
			AppLog.error(e);
			returnMessage = "There was problem while communicating with User Directory Server, Please Contact Administrator.";
		} finally {
			try {
				if (null != ldapContext) {
					ldapContext.close();
				}
			} catch (Exception e) {
				// ignoring Closing Contexts
			}
		}
		AppLog.end();
		return returnMessage;

	}
}
