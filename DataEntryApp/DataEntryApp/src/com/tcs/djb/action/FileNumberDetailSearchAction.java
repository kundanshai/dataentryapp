/************************************************************************************************************
 * @(#) FileNumberDetailSearchAction.java   19-Jan-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.FileNumberSearchDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.model.FileNumberSearchCrieteria;
import com.tcs.djb.model.FileNumberSearchDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for File Number Search screen.
 * </p>
 * 
 * @author Arnab Nandy (Tata Consultancy Services)
 * @since 19-01-2016
 * @history 12-Feb-2016 Arnab edited in {@link #updateSelectedFNOList()} for
 *          File Number Search Screen as per Jtrac DJB-4359.
 */

public class FileNumberDetailSearchAction extends ActionSupport implements
		ServletResponseAware {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";

	private static final String optionTagEnd = "</option>";

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "43";

	private static final String selectTagEnd = "</select>";

	private static final long serialVersionUID = 1L;
	/**
	 * Action status
	 */
	private String actionStatus;
	/**
	 * File Number.
	 */
	private String fileNo;
	/**
	 * File Number Search Criteria class object.
	 */
	FileNumberSearchCrieteria fileNumberSearchCrieteria;
	/**
	 * File Number Search Details List.
	 */
	List<FileNumberSearchDetails> fileNumberSearchDetailsList;
	/**
	 * File search status.
	 */
	private String fileSearchStatus;

	/**
	 * Hidden action.
	 */
	private String hidAction;
	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;
	/**
	 * Logged From Date.
	 */
	private String loggedFromDate;
	/**
	 * Logged To Date.
	 */
	private String loggedToDate;

	/**
	 * File lot number.
	 */
	private String lotNo;

	/**
	 * Maximum Number of Record Displayed Per Page of search screen.
	 */
	private String maxRecordPerPage;

	/**
	 * Current Page no.
	 */
	private String pageNo;

	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;

	/**
	 * Removed File Numbers
	 */
	private String removedFileNo;

	/**
	 * HttpServletResponse used for AJax call.
	 */
	private HttpServletResponse response;

	/**
	 * Selected File Numbers
	 */
	private String selectedFileNo;

	List<String> selectedFNOList;

	/**
	 * User List Drop down.
	 */
	private String selectedUser;

	/**
	 * Zone No Drop down.
	 */
	private String selectedZROCode;

	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;

	/**
	 * Total no of Records selected by user.
	 */
	private String totalRecordsSelected;

	/**
	 * For all ajax call in Data Entry Search screen
	 * 
	 * @return string
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	@SuppressWarnings("deprecation")
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.login.expired"));
				AppLog.end();
				return "success";
			}
			if ("saveStatus".equalsIgnoreCase(hidAction)) {
				String fileSearchResponse = updateSelectedFNOList();
				inputStream = new StringBufferInputStream(fileSearchResponse);
				AppLog.end();
				return SUCCESS;
			}
			if ("generateLot".equalsIgnoreCase(hidAction)) {
				String fileSearchResponse = updateGenerateLot();
				inputStream = new StringBufferInputStream(fileSearchResponse);
				AppLog.end();
				return SUCCESS;
			}
			if ("checkZRO".equalsIgnoreCase(hidAction)) {
				// String fileSearchResponse = chceckZROforUser();
				// inputStream = new
				// StringBufferInputStream(fileSearchResponse);

				Map<String, String> returnMap = FileNumberSearchDAO
						.checkZRO(selectedZROCode);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedUser\" id=\"selectedUser\" class=\"selectbox\" >");
				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(null == entry.getKey() ? "" : entry
								.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(null == entry.getValue() ? "" : entry
								.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("userList", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
				AppLog.end();
				return SUCCESS;
			}
			if ("checkFNO".equalsIgnoreCase(hidAction)) {
				String fileSearchResponse = checkFNOforZRO();
				inputStream = new StringBufferInputStream(fileSearchResponse);
				AppLog.end();
				return SUCCESS;
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * Validate ZRO locations for files.
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 02-02-2016
	 * 
	 */
	public String checkFNOforZRO() {
		String result = "SUCCESS";
		Map<String, Object> session = ActionContext.getContext().getSession();
		try {
			String userZroCd = (String) session.get("");
			int Valid = FileNumberSearchDAO.validateFNO(fileNo, userZroCd);
			if (0 != Valid) {
				result = "SUCCESS";
			} else {
				result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, file is not accessible for current user.</b></font>";
			}
		} catch (Exception e) {
			AppLog.error(e);
			result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
		}
		AppLog.end();
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
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
				AppLog.end();
				return "login";
			}
			/** Load Default Values. */
			loadDefault();
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "Next".equalsIgnoreCase(hidAction)
					|| "Prev".equalsIgnoreCase(hidAction)
					|| "refresh".equalsIgnoreCase(hidAction)) {
				AppLog.info("selectedZROCode:" + selectedZROCode
						+ ":loggedFromDate:" + loggedFromDate
						+ ":loggedToDate:" + loggedToDate
						+ ":selectedLoggedBy:" + selectedUser
						+ ":loggedFileNo:" + fileNo + ":selectedStatus:"
						+ fileSearchStatus + ":loggedLotNo:" + lotNo);
				fileNumberSearchCrieteria = new FileNumberSearchCrieteria();
				if (null != selectedZROCode
						&& !"".equalsIgnoreCase(selectedZROCode)) {
					fileNumberSearchCrieteria.setZone(selectedZROCode);
				} else {
					fileNumberSearchCrieteria.setZone("");
				}
				if (null != loggedFromDate
						&& !"".equalsIgnoreCase(loggedFromDate.trim())) {
					fileNumberSearchCrieteria.setLoggedFromDate(loggedFromDate
							.trim());
				} else {
					fileNumberSearchCrieteria.setLoggedFromDate("");
				}
				if (null != loggedToDate
						&& !"".equalsIgnoreCase(loggedToDate.trim())) {
					fileNumberSearchCrieteria.setLoggedToDate(loggedToDate
							.trim());
				} else {
					fileNumberSearchCrieteria.setLoggedToDate("");
				}
				if (null != selectedUser && !"".equalsIgnoreCase(selectedUser)) {
					fileNumberSearchCrieteria.setuserList(selectedUser);
				} else {
					fileNumberSearchCrieteria.setuserList("");
				}
				if (null != fileNo && !"".equalsIgnoreCase(fileNo.trim())) {
					fileNumberSearchCrieteria.setFileNo(fileNo.trim());
				} else {
					fileNumberSearchCrieteria.setFileNo("");
				}
				if (null != fileSearchStatus
						&& !"".equalsIgnoreCase(fileSearchStatus)) {
					fileNumberSearchCrieteria.setStatusCD(fileSearchStatus);
				} else {
					fileNumberSearchCrieteria.setStatusCD("");
				}
				if (null != lotNo && !"".equalsIgnoreCase(lotNo.trim())) {
					fileNumberSearchCrieteria.setLotNo(lotNo.trim());
				} else {
					fileNumberSearchCrieteria.setLotNo("");
				}

				/** Search File Number Detail */
				result = searchFileNumberDetail();

			}

		} catch (ClassCastException e) {
			addActionError(getText("error.login.expired"));
			AppLog.end();
			return "login";
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			addActionError(getText("error.login.expired"));
			return "login";
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the actionStatus
	 */
	public String getActionStatus() {
		return actionStatus;
	}

	/**
	 * @return the fileNo
	 */
	public String getFileNo() {
		return fileNo;
	}

	/**
	 * @return the fileNumberSearchDetailsList
	 */
	public List<FileNumberSearchDetails> getFileNumberSearchDetailsList() {
		return fileNumberSearchDetailsList;
	}

	/**
	 * @return the fileSearchStatus
	 */
	public String getFileSearchStatus() {
		return fileSearchStatus;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the loggedFromDate
	 */
	public String getLoggedFromDate() {
		return loggedFromDate;
	}

	/**
	 * @return the loggedToDate
	 */
	public String getLoggedToDate() {
		return loggedToDate;
	}

	/**
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @return the pageNoDropdownList
	 */
	public List<String> getPageNoDropdownList() {
		return pageNoDropdownList;
	}

	/**
	 * @return the removedFileNo
	 */
	public String getRemovedFileNo() {
		return removedFileNo;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedFileNo
	 */
	public String getSelectedFileNo() {
		return selectedFileNo;
	}

	/**
	 * @return the selectedFNOList
	 */
	public List<String> getSelectedFNOList() {
		return selectedFNOList;
	}

	/**
	 * @return
	 */
	public String getSelectedUser() {
		return selectedUser;
	}

	/**
	 * @return the selectedZROCode
	 */
	public String getSelectedZROCode() {
		return selectedZROCode;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return the totalRecordsSelected
	 */
	public String getTotalRecordsSelected() {
		return totalRecordsSelected;
	}

	/**
	 * <p>
	 * Populate default values required for File Number Search Screen.
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	@SuppressWarnings("unchecked")
	public void loadDefault() {
		String searchcontext = DJBConstants.ZRO_SEARCH;
		int flag = 0;
		List<String> items = Arrays.asList(searchcontext.split("\\s*,\\s*"));
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String userZROlist = NewConnFileEntryDAO
					.getDeZroAccessForUserId(userId);
			session.put("CURR_TAB", "MRD");
			if (null == userZROlist) {
				session.put("ZROListMap", "");
			} else {
				for (int i = 0; i < items.size(); i++) {
					if (userZROlist.contains(items.get(i))) {
						if (null == session.get("ZROListMap")) {
							session.put("ZROListMap", GetterDAO.getAllZRO());
						}
						flag = 1;
					}
				}
				if (flag == 0) {
					if (null == session.get("ZROListMap")) {
						session.put("ZROListMap", GetterDAO
								.getZROList(userZROlist));
					}
				}
			}
			if (null == session.get("fileSearchStatusListMap")) {
				session.put("fileSearchStatusListMap", GetterDAO
						.getStatusListMap());
			}
			if (null == session.get("fileSearchSaveStatusListMap")) {
				session.put("fileSearchSaveStatusListMap", GetterDAO
						.getSaveStatusListMap());
			}
			if (null == session.get("userList")) {
				session.put("userList", "");
			}
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * Search Files For File Number Search Screen.
	 * </p>
	 * 
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 */
	public String searchFileNumberDetail() {
		String result = "success";
		try {
			if (null == this.pageNo || "".equals(this.pageNo)) {
				this.pageNo = "1";
				this.maxRecordPerPage = PropertyUtil
						.getProperty("RECORDS_PER_PAGE");
			}
			AppLog.info("pageNo:" + pageNo + ":maxRecordPerPage:"
					+ maxRecordPerPage);
			this.totalRecords = FileNumberSearchDAO
					.getTotalCountOfFileDetailsListForSearchQuery(fileNumberSearchCrieteria);
			if (totalRecords > 0) {
				if (Integer.parseInt(maxRecordPerPage)
						* Integer.parseInt(pageNo) > totalRecords) {
					this.pageNo = Integer.toString((int) totalRecords
							/ Integer.parseInt(maxRecordPerPage) + 1);
				}
				List<String> pageNoList = new ArrayList<String>();
				List<String> maxPageNoList = new ArrayList<String>();
				for (int i = 0, j = 1; i < totalRecords; i++) {
					if (i % Integer.parseInt(maxRecordPerPage) == 0) {
						pageNoList.add(Integer.toString(j++));
						if (i != 0 && i <= 200) {
							maxPageNoList.add(Integer.toString(i));
						}
					}
				}
				this.pageNoDropdownList = pageNoList;
				Map<String, Object> session = ActionContext.getContext()
						.getSession();
				HashMap<String, String> fileSearchStatusListMap = (HashMap<String, String>) session
						.get("fileSearchStatusListMap");
				this.fileNumberSearchDetailsList = (ArrayList<FileNumberSearchDetails>) FileNumberSearchDAO
						.getFileNumberSearchDetailList(
								fileNumberSearchCrieteria, pageNo,
								maxRecordPerPage, fileSearchStatusListMap);
				result = "success";
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
	 * @param actionStatus
	 *            the actionStatus to set
	 */
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	/**
	 * @param fileNo
	 *            the fileNo to set
	 */
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	/**
	 * @param fileNumberSearchDetailsList
	 *            the fileNumberSearchDetailsList to set
	 */
	public void setFileNumberSearchDetailsList(
			List<FileNumberSearchDetails> fileNumberSearchDetailsList) {
		this.fileNumberSearchDetailsList = fileNumberSearchDetailsList;
	}

	/**
	 * @param fileSearchStatus
	 *            the fileSearchStatus to set
	 */
	public void setFileSearchStatus(String fileSearchStatus) {
		this.fileSearchStatus = fileSearchStatus;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param loggedFromDate
	 *            the loggedFromDate to set
	 */
	public void setLoggedFromDate(String loggedFromDate) {
		this.loggedFromDate = loggedFromDate;
	}

	/**
	 * @param loggedToDate
	 *            the loggedToDate to set
	 */
	public void setLoggedToDate(String loggedToDate) {
		this.loggedToDate = loggedToDate;
	}

	/**
	 * @param lotNo
	 *            the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @param pageNoDropdownList
	 *            the pageNoDropdownList to set
	 */
	public void setPageNoDropdownList(List<String> pageNoDropdownList) {
		this.pageNoDropdownList = pageNoDropdownList;
	}

	/**
	 * @param removedFileNo
	 *            the removedFileNo to set
	 */
	public void setRemovedFileNo(String removedFileNo) {
		this.removedFileNo = removedFileNo;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedFileNo
	 *            the selectedFileNo to set
	 */
	public void setSelectedFileNo(String selectedFileNo) {
		this.selectedFileNo = selectedFileNo;
	}

	/**
	 * @param selectedFNOList
	 *            the selectedFNOList to set
	 */
	public void setSelectedFNOList(List<String> selectedFNOList) {
		this.selectedFNOList = selectedFNOList;
	}

	/**
	 * @param selectedUser
	 */
	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param totalRecordsSelected
	 *            the totalRecordsSelected to set
	 */
	public void setTotalRecordsSelected(String totalRecordsSelected) {
		this.totalRecordsSelected = totalRecordsSelected;
	}

	/**
	 * <p>
	 * Update Selected File List for Lot No.
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String updateGenerateLot() {
		String result = null;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			int existingLots = FileNumberSearchDAO
					.getExistingLotCount(selectedFileNo);
			AppLog.info("existingLots:" + existingLots);
			if (existingLots > 0) {
				result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, Selected "
						+ existingLots
						+ " file(s) already have Lot No.</b></font>";
			} else {
				HashMap<String, String> returnMap = FileNumberSearchDAO
						.updateLotNumber(selectedFileNo);
				if (null != returnMap && null != returnMap.get("lotNo")
						&& returnMap.get("lotNo").trim().length() > 0
						&& null != returnMap.get("count")) {
					session.put("SERVER_MESSAGE",
							"<font color='#33CC33'><b>Lot No:"
									+ returnMap.get("lotNo")
									+ ": Generated Successfully.</b></font>");
					result = "<font color='#33CC33'><b>Lot No:"
							+ returnMap.get("lotNo")
							+ ": Generated Successfully.</b></font>";
				} else {
					result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, Lot No. Generation Process Failed.Please try again.</b></font>";
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
			result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
		}
		AppLog.end();
		// selectedFileNo = "";
		// actionStatus = "";
		return result;
	}

	/**
	 * <p>
	 * Update Selected File List for Action Status.
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 19-01-2016
	 * @as per Jtrac DJB-4359 and DJB-4365
	 *     </p>
	 */
	@SuppressWarnings("unchecked")
	public String updateSelectedFNOList() {
		AppLog.begin();
		String result = null;
		// Start : Edited by Arnab Nandy 15-02-2015 as per Jtrac DJB-4359.
		// Changed logic in order to avoid multiple entry at File Number Status
		// Tracking table for ineligible files which are not updated as per
		// criteria.
		int recordsUpdated = 0;
		int recordsInserted = 0;
		int recordsChecked = 0;
		int countFlg = 0;
		String fileNo = "";
		try {
			String selectedFNOArray[] = selectedFileNo.split(",");
			Map<String, Object> session = (null != ActionContext.getContext()) ? ActionContext
					.getContext().getSession()
					: null;
			String userId = (null != session) ? (String) session.get("userId")
					: null;
			if (null != userId) {
				int missingLotCount = FileNumberSearchDAO
						.getMissingLotCount(selectedFileNo);
				if (missingLotCount > 0) {
					result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, Selected files must contain Lot No.</b></font>";
				} else if (missingLotCount == 0) {

					/*
					 * int recordsUpdated =
					 * FileNumberSearchDAO.updateActionStatus( selectedFileNo,
					 * actionStatus); if (recordsUpdated > 0) {
					 * FileNumberSearchDAO.saveFileStatusTrack(selectedFileNo,
					 * actionStatus);
					 */
					for (int i = 0; i < selectedFNOArray.length; i++) {
						fileNo = selectedFNOArray[i];
						if (null != fileNo
								&& !"".equalsIgnoreCase(fileNo.trim())
								&& null != actionStatus
								&& !"".equalsIgnoreCase(actionStatus.trim())) {
							fileNo = fileNo.trim();
							actionStatus = actionStatus.trim();
							recordsChecked = FileNumberSearchDAO
									.checkActionStatus(fileNo, actionStatus);
							if (recordsChecked > 0) {
								AppLog
										.info("recordsChecked::"
												+ recordsChecked);
								// recordsInserted = FileNumberSearchDAO
								// .saveFileStatusTrack(fileNo,
								// actionStatus);
								recordsInserted = FileNumberSearchDAO
										.saveFileStatusTrack(fileNo,
												actionStatus, userId);
								AppLog.info("recordsInserted::"
										+ recordsInserted);
								if (recordsInserted > 0) {
									// recordsUpdated = FileNumberSearchDAO
									// .updateActionStatus(fileNo,
									// actionStatus);
									recordsUpdated = FileNumberSearchDAO
											.updateActionStatus(fileNo,
													actionStatus, userId);
									AppLog.info("recordsUpdated::"
											+ recordsUpdated);
									countFlg = countFlg + recordsUpdated;
								}
							}
						}
						recordsChecked = 0;
						recordsInserted = 0;
					}
					AppLog.info("No of Files::" + selectedFNOArray.length);
					if (countFlg > 0) {

						/*
						 * session .put( "SERVER_MESSAGE",
						 * "<font color='#33CC33'><b>" + recordsUpdated +
						 * " File(s) Status updated Successfully.</b></font>");
						 * result = "<font color='#33CC33'><b>" + recordsUpdated
						 * + " File(s) Status updated Successfully.</b></font>";
						 */

						session
								.put(
										"SERVER_MESSAGE",
										"<font color='#33CC33'><b>"
												+ countFlg
												+ " File(s) Status updated Successfully.</b></font>");
						result = "<font color='#33CC33'><b>"
								+ countFlg
								+ " File(s) Status updated Successfully.</b></font>";

						if (countFlg < selectedFNOArray.length) {
							int temp = selectedFNOArray.length - countFlg;
							session
									.put(
											"SERVER_MESSAGE",
											"<font color='red'><b> "
													+ temp
													+ " file(s) status updatation failed. Only "
													+ countFlg
													+ " File(s) Status updated Successfully.</b></font>");
							result = "<font color='red'><b> "
									+ temp
									+ " file(s) status updatation failed. Only "
									+ countFlg
									+ " File(s) Status updated Successfully.</b></font>";
						}
						/*
						 * AppLog.info("countFlg::"+countFlg);
						 * AppLog.info("cntBlankFlg::"+cntBlankFlg);
						 * AppLog.info("result::"+result);
						 * AppLog.info("selectedFileNo::"+selectedFileNo);
						 */

					} else {
						result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, Selected Files Status Updation Failed.</b></font>";
					}

					// End : Modified by Arnab Nandy 15-02-2015 as per Jtrac
					// DJB-4359.

				}
			}
		} catch (Exception e) {
			result = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		// selectedFileNo = "";
		// actionStatus = "";
		AppLog.info("File Updation Status::" + result);
		return result;
	}
}
