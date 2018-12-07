/************************************************************************************************************
 * @(#) FileNbrAllocationUserWiseAction.java   19-Sep-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.FileNbrAllocationUserWiseDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.model.FileNbrAllocation;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is a action class for User Wise File Allocation Screen
 * This class contains all the action performed as per jTrac
 * DJB-4571,Openproject- #1492
 * </p>
 * 
 * @author Lovely(Tata Consultancy Services)
 * @since 19-09-2016
 */
@SuppressWarnings("serial")
public class FileNbrAllocationUserWiseAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * allocated file number to userid List.
	 */
	public List<FileNbrAllocation> fileNbrAllocationList = null;
	public static List<FileNbrAllocation> originalFileNbrAllocationList = null;

	/**
	 * Hidden action.
	 */
	private String hidAction;
	private InputStream inputStream;
	/**
	 * Maximum range.
	 */
	private String maxFileRange;
	/**
	 * Minimum Range .
	 */
	private String minFileRange;

	/**
	 * Zone location Drop down.
	 */
	private String selectedZROLocation;

	/**
	 *UserId for tagging.
	 */
	private String userIdTag;

	/**
	 *UserId for updating.
	 */
	private String userIdUpdate;
	/**
	 *UserId for Max Range temp variable.
	 */
	private String maxRangeTemp;

	/**
	 *UserId for updating.
	 */
	private int rowCount;

	private final String ZRO_LOCATION_ACCESS_SEPERATOR = ",";

	/**
	 * For all ajax call in File Number Allocation screen
	 * 
	 * @return string
	 * @author Lovely (Tata Consultancy Services)
	 * @since 12-04-2016
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session;
			String userId = null;
			if (null != ActionContext.getContext()
					&& !"".equalsIgnoreCase(ActionContext.getContext()
							.toString())
					&& null != ActionContext.getContext().getSession()
					&& !"".equalsIgnoreCase(ActionContext.getContext()
							.getSession().toString())) {
				session = ActionContext.getContext().getSession();
				userId = (String) session.get("userId");
				if (null == userId || "".equals(userId)) {
					inputStream = ScreenAccessValidator
							.ajaxResponse(getText("error.login.expired"));
					return "success";
				}
			}
			AppLog.info("Value of hidAction " + hidAction);
			if ("updateFileNbr".equalsIgnoreCase(hidAction)) {
				if (null != minFileRange && !"".equals(minFileRange)
						&& null != selectedZROLocation
						&& !"".equals(selectedZROLocation)
						&& null != userIdUpdate && !"".equals(userIdUpdate)) {
					AppLog.info("rowCount*************   " + rowCount);
					maxRangeTemp = originalFileNbrAllocationList.get(
							rowCount - 1).getMaxFileRange();
					AppLog.info("<<<<<<<<MaxRangeTemp>>>>>   " + maxRangeTemp);
					int countFileValid = FileNbrAllocationUserWiseDAO
							.isExistFileNbr(maxFileRange, userIdUpdate);
					int count = FileNbrAllocationUserWiseDAO
							.isValideAllocatedFileToUser(minFileRange,
									maxFileRange, selectedZROLocation);
					int countExist = FileNbrAllocationUserWiseDAO
							.isUsedFileNbr(minFileRange, maxRangeTemp);
					int countExistForUpdate = FileNbrAllocationUserWiseDAO
							.isUsedFileNbr(minFileRange, maxFileRange);
					AppLog
							.info("*****count******  "
									+ count
									+ "******* countExist ******   "
									+ countExist
									+ "****** countFileValid ******  "
									+ countFileValid
									+ "  min File Range and max File Range to be updated>>>  "
									+ minFileRange + " TO  " + maxFileRange
									+ "PREVIOUS MAX RANGE   " + maxRangeTemp);
					if (count == 1) {
						if (countExist == 0) {
							if (countExistForUpdate == 0) {
							if (countFileValid == 0) {
									int totalRecordsUpdated = FileNbrAllocationUserWiseDAO
											.updateAllocatedFileToUser(
													userIdUpdate, maxFileRange,
													userId, selectedZROLocation);
									AppLog.info("totalRecordsUpdated>>>>    "
											+ totalRecordsUpdated);
									if (totalRecordsUpdated > 0) {
										inputStream = new StringBufferInputStream(
												DJBConstants.UPDATE_SUCCESS_MSG);
										AppLog.end();
										return SUCCESS;
									} else {
										inputStream = new StringBufferInputStream(
												"ERROR:"
														+ DJBConstants.UPDATE_FAIL_MSG);
										AppLog.end();
										return SUCCESS;
									}
								} else {
									inputStream = new StringBufferInputStream(
											"ERROR:"
													+ DJBConstants.ALREADY_ASSIGNED_MSG);
									AppLog.end();
									return SUCCESS;
								}
							} else {
								inputStream = new StringBufferInputStream(
										"ERROR:" + DJBConstants.USED_FILE_MSG);
								AppLog.end();
								return SUCCESS;
							}
						} else {
							inputStream = new StringBufferInputStream("ERROR:"
									+ DJBConstants.USED_FILE_MSG);
							AppLog.end();
							return SUCCESS;
						}
					} else {
						inputStream = new StringBufferInputStream("ERROR:"
								+ DJBConstants.WRONG_ZRO_OF_MTRRDR_SELECTED);
						AppLog.end();
						return SUCCESS;
					}
				}
			}
			if ("deleteFileNbr".equalsIgnoreCase(hidAction)) {
				if (null != minFileRange && !"".equals(minFileRange)
						&& null != maxFileRange && !"".equals(maxFileRange)
						&& null != userIdUpdate && !"".equals(userIdUpdate)) {
					int countFileExist = FileNbrAllocationUserWiseDAO
							.isUsedFileNbr(minFileRange, maxFileRange);
					AppLog.info("*****countFileExist******  " + countFileExist
							+ "    Delete file range >>>>   " + minFileRange
							+ "  TO " + maxFileRange);
					if (countFileExist == 0) {
						int totalRecordsDeleted = FileNbrAllocationUserWiseDAO
								.deleteAllocatedFileToUser(userIdUpdate,
										minFileRange, maxFileRange,
										selectedZROLocation);
						AppLog.info("totalRecordsDeleted >>>>  "
								+ totalRecordsDeleted);
						if (totalRecordsDeleted > 0) {
							inputStream = new StringBufferInputStream(
									DJBConstants.DELETE_FILE_MSG);

							AppLog.end();
							return SUCCESS;
						}
					} else {

						inputStream = new StringBufferInputStream("ERROR:"
								+ DJBConstants.CANNOT_DELETE_MSG);
						AppLog.end();
						return SUCCESS;
					}
				}
			}
			if ("userAllocationSubmit".equalsIgnoreCase(hidAction)) {
				if (null != minFileRange && !"".equals(minFileRange)
						&& null != maxFileRange && !"".equals(maxFileRange)
						&& null != userIdTag && !"".equals(userIdTag)) {
					int countExist = validateBookletRange(minFileRange,
							maxFileRange, userIdTag);
					String zroLocation = FileNbrAllocationUserWiseDAO
							.checkTaggedZROLocation(minFileRange, maxFileRange);
					int countFileExist = FileNbrAllocationUserWiseDAO
							.isUsedFileNbr(minFileRange, maxFileRange);
					boolean isValidateUserId = isValidateUserId(userId,
							userIdTag);
					AppLog.info("TAGGED ZRO LOCATION  zroLocation "
							+ zroLocation);
					if (isValidateUserId) {
						if (null != zroLocation && !"".equals(zroLocation)) {
							String ZroLocationAccess = FileNbrAllocationUserWiseDAO
									.checkZROLocationAccess(userIdTag);
							List zroLocationAccessList = new ArrayList<String>();
							AppLog
									.info("*****countExist******  "
											+ countExist
											+ "    Tagged ZRO location of the entered file range  >>>>>>  "
											+ zroLocation
											+ "    Tagged ZRO location of the user(ZroLocationAccess)>>>>   "
											+ ZroLocationAccess
											+ "      minFileRange >>>>   "
											+ minFileRange
											+ "    maxFileRange >>>>>  "
											+ maxFileRange
											+ "countFileExist>>>>  "
											+ countFileExist);
							if (ZroLocationAccess
									.contains(ZRO_LOCATION_ACCESS_SEPERATOR)) {
								zroLocationAccessList = Arrays
										.asList(ZroLocationAccess
												.split("\\s*,\\s*"));
							} else {
								zroLocationAccessList = Arrays
										.asList(ZroLocationAccess);
							}
							if (zroLocationAccessList.contains(zroLocation)) {
								if (countExist == 0) {
									if (countFileExist == 0) {
										int totalRecordsInserted = FileNbrAllocationUserWiseDAO
												.insertAllocatedFileToUser(
														userIdTag, zroLocation,
														minFileRange,
														maxFileRange, userId);
										AppLog
												.info("totalRecordsInserted>>>>>>"
														+ totalRecordsInserted);
										if (totalRecordsInserted > 0) {
											inputStream = new StringBufferInputStream(
													DJBConstants.ASSIGN_SUCCESS_MSG
															+ userIdTag);
											AppLog.end();
											return SUCCESS;
										}
									} else {
										inputStream = new StringBufferInputStream(
												"ERROR:"
														+ DJBConstants.USED_FILE_MSG);
										AppLog.end();
										return SUCCESS;
									}
								} else {
									inputStream = new StringBufferInputStream(
											"ERROR:"
													+ DJBConstants.ALREADY_ASSIGNED_MSG);
									AppLog.end();
									return SUCCESS;
								}
							} else {
								inputStream = new StringBufferInputStream(
										"ERROR:"
												+ DJBConstants.FILE_NOT_ZRO_MSG);
								AppLog.end();
								return SUCCESS;
							}
						} else {
							inputStream = new StringBufferInputStream("ERROR:"
									+ DJBConstants.FILE_NBR_SELECT_MSG
									+ userIdTag);
							AppLog.end();
							return SUCCESS;
						}
					} else {
						inputStream = new StringBufferInputStream("ERROR:"
								+ DJBConstants.WRONG_ZRO_OF_MTRRDR);
						AppLog.end();
						return SUCCESS;
					}
					AppLog.end();
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				return "login";
			}
			AppLog.info("Value of hidAction " + hidAction);
			if ("searchFileNbrList".equalsIgnoreCase(hidAction)) {
				if (null != userIdUpdate && !"".equals(userIdUpdate)) {
					boolean isValidateUserId = isValidateUserId(userId,
							userIdUpdate);
					if (isValidateUserId) {
						fileNbrAllocationList = (ArrayList<FileNbrAllocation>) FileNbrAllocationUserWiseDAO
								.getUserFileNbrAllocationList(userIdUpdate);
						originalFileNbrAllocationList = fileNbrAllocationList;
						AppLog.info("fileNbrAllocationList.size()>>>"
								+ fileNbrAllocationList.size()
								+ " ::originalFileNbrAllocationList.size()>>"
								+ originalFileNbrAllocationList.size());
						AppLog.end();
						return SUCCESS;
					} else {
						addActionError(DJBConstants.WRONG_ZRO_OF_MTRRDR);
						AppLog.end();
						return SUCCESS;
					}
				}
			}
			/** Load Default Values. */
			loadDefault();
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
				session.remove("AJAX_MESSAGE");
			}
		} catch (Exception e) {
			AppLog.error(e);
			addActionError(getText("error.login.expired"));
			return "login";
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the maxFileRange
	 */
	public String getMaxFileRange() {
		return maxFileRange;
	}

	/**
	 * @return the fileNbrAllocationList
	 */
	public List<FileNbrAllocation> getFileNbrAllocationList() {
		return fileNbrAllocationList;
	}

	/**
	 * @return hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the minFileRange
	 */
	public String getMinFileRange() {
		return minFileRange;
	}

	/**
	 * @return selectedZROLocation
	 */
	public String getSelectedZROLocation() {
		return selectedZROLocation;
	}

	/**
	 * @return the userIdUpdate
	 */
	public String getUserIdUpdate() {
		return userIdUpdate;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the originalFileNbrAllocationList
	 */
	public List<FileNbrAllocation> getOriginalFileNbrAllocationList() {
		return originalFileNbrAllocationList;
	}

	/**
	 * @return the userIdTag
	 */
	public String getUserIdTag() {
		return userIdTag;
	}

	/**
	 * @param userIdTag
	 *            the userIdTag to set
	 */
	public void setUserIdTag(String userIdTag) {
		this.userIdTag = userIdTag;
	}

	/**
	 * @param originalFileNbrAllocationList
	 *            the originalFileNbrAllocationList to set
	 */
	@SuppressWarnings("static-access")
	public void setOriginalFileNbrAllocationList(
			List<FileNbrAllocation> originalFileNbrAllocationList) {
		this.originalFileNbrAllocationList = originalFileNbrAllocationList;
	}

	/**
	 * <p>
	 * This method is used to validate User Id
	 * </p>
	 * 
	 * @param userId
	 * @param userIdTag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean isValidateUserId(String userId, String userIdTag) {
		AppLog.begin();

		boolean isValidateUserId = false;
		try {
			// Fetching logged in user zro location access.
			List zroLoggedInUserList = new ArrayList<String>();
			String ZroLoggedInUser = FileNbrAllocationUserWiseDAO
					.getAssignedZROList(userId);
			if (ZroLoggedInUser.contains(ZRO_LOCATION_ACCESS_SEPERATOR)) {
				zroLoggedInUserList = Arrays.asList(ZroLoggedInUser
						.split("\\s*,\\s*"));
			} else {
				zroLoggedInUserList = Arrays.asList(ZroLoggedInUser);
			}
			AppLog
					.info("    LOGGED IN USER ZRO LOCATION  >>>     ZroLoggedInUserList>>>   "
							+ zroLoggedInUserList);

			// Fetching entered user id zro location access.
			String ZroCdAccess = FileNbrAllocationUserWiseDAO
					.checkZROLocationAccess(userIdTag);
			List zroLocationAccessList = new ArrayList<String>();
			if (ZroCdAccess.contains(ZRO_LOCATION_ACCESS_SEPERATOR)) {
				zroLocationAccessList = Arrays.asList(ZroCdAccess
						.split("\\s*,\\s*"));
			} else {
				zroLocationAccessList = Arrays.asList(ZroCdAccess);
			}
			AppLog
					.info("   ENTRED USER ZRO LOCATION ACCESS ZroLocationAccessList >>>   "
							+ zroLocationAccessList);
			if (zroLoggedInUserList.containsAll(zroLocationAccessList)) {
				isValidateUserId = true;
			}

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.info("isValidateUserId >>>>>>" + isValidateUserId
				+ "    logged in user id >>>>>  " + userId
				+ "    entered user id >>>>  " + userIdTag);
		AppLog.end();
		return isValidateUserId;
	}

	/**
	 * <p>
	 * This method is used to load ZRO list for a particular User Id
	 * </p>
	 */
	private void loadDefault() {
		AppLog.begin();
		addActionError("");
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String userZROlist = NewConnFileEntryDAO
					.getDeZroAccessForUserId(userId);
			session.put("CURR_TAB", "MASTER");
			if (null == userZROlist) {
				session.put("ZROList", "");
			} else {
				session.put("ZROList", GetterDAO.getAllZRO());
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * @param fileNbrAllocationList
	 *            the fileNbrAllocationList to set
	 */
	public void setFileNbrAllocationList(
			List<FileNbrAllocation> fileNbrAllocationList) {
		this.fileNbrAllocationList = fileNbrAllocationList;
	}

	/**
	 * @param hidAction
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param maxFileRange
	 *            the maxFileRange to set
	 */
	public void setMaxFileRange(String maxFileRange) {
		this.maxFileRange = maxFileRange;
	}

	/**
	 * @param minFileRange
	 *            the minFileRange to set
	 */
	public void setMinFileRange(String minFileRange) {
		this.minFileRange = minFileRange;
	}

	/**
	 * @param selectedZROLocation
	 */
	public void setSelectedZROLocation(String selectedZROLocation) {
		this.selectedZROLocation = selectedZROLocation;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {

	}

	/**
	 * @param userIdUpdate
	 *            the userIdUpdate to set
	 */
	public void setUserIdUpdate(String userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	/**
	 * <p>
	 * This method is used to validate maximum and minimum range for a booklet
	 * </p>
	 * 
	 * @param minRange
	 * @param maxRange
	 * @param userId
	 * @return
	 */
	private int validateBookletRange(String minRange, String maxRange,
			String userId) {
		AppLog.begin();
		int count = 0;
		try {
			if (Long.parseLong(minRange) > 0) {
				count = count
						+ FileNbrAllocationUserWiseDAO.isExistFileNbr(minRange,
								userId);
				AppLog.info("count for min ------>>>>  " + count + "  user id "
						+ userId);
			}

			if (Long.parseLong(maxRange) > 0) {
				count = count
						+ FileNbrAllocationUserWiseDAO.isExistFileNbr(maxRange,
								userId);
				AppLog.info("count for max ------>>>>  " + count + "  user id "
						+ userId);
			}
			AppLog.info("minRange ---->" + minRange + "maxRange ---->"
					+ maxRange + " count ---->" + count);

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return count;
	}
}
