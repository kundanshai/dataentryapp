/************************************************************************************************************
 * @(#) OperationMRKeyReviewAction.java   01 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.OperationMRKeyReviewDAO;
import com.tcs.djb.model.OperationMRKeyReviewDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Operation MRKey Review screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 01-03-2013
 * @history 05-03-2013 Matiur Rahman changed as per Updated JTrac DJB-1204.
 *          Added variable fromMRKeyStatusCode.
 * @history 26-07-2013 Matiur Rahman added mrKeyListForSearch as search
 *          criteria.
 * @history 06-08-2013 Matiur Rahman added new method
 *          {@link #removeLastComma(String)} to remove the last comma.
 * @history 30-09-2013 Matiur Rahman Added
 *          fromDate,toDate,orderBy,orderByTo,functionality as per DJB-1970.
 */
@SuppressWarnings("deprecation")
public class OperationMRKeyReviewAction extends ActionSupport implements
		ServletResponseAware {
	private static final String SCREEN_ID = "16";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** From date for record to display */
	private String fromDate;
	/**
	 * From MR Key Status Code to be updated.
	 */
	private String fromMRKeyStatusCode;
	/**
	 * hidden Action Set.
	 */
	private String hidAction;
	/**
	 * hidden MRKey Status Code
	 */
	private String hidMRKeyStatusCode;

	/**
	 * hidden Process Counter.
	 */
	private String hidProcessCounter;

	/**
	 * InputStream required for AJax call.
	 */
	private InputStream inputStream;

	/**
	 * maximum No. of Records Per Page.
	 */
	private String maxRecordPerPage;
	/**
	 * List of MRKeys .
	 */
	private String mrKeyList;

	/**
	 * List of MRKeys for Search.
	 */
	private String mrKeyListForSearch;

	/**
	 * MRKey Status Code.
	 */
	private String mrKeyStatusCode;

	/**
	 * new MRKey Status Code that to be updated.
	 */
	private String newMRKeyStatusCode;

	/**
	 * operation MRKey Review Details List for search result.
	 */
	List<OperationMRKeyReviewDetails> operationMRKeyReviewDetailsList;

	/** Order By for record to display */
	private String orderBy;

	/** Order By To Ascending or Descending for record to display */
	private String orderByTo;

	/**
	 * Current Page no.
	 */
	private String pageNo;
	/**
	 * page No Drop down List.
	 */
	List<String> pageNoDropdownList;
	/**
	 * process Counter of the MRD.
	 */
	private String processCounter;

	/**
	 * HttpServlet Response required for AJax call.
	 */
	private HttpServletResponse response;

	private String selectedBillRound;

	/** To date for record to display */
	private String toDate;

	/**
	 * total no of Records returned by the search query.
	 */
	private int totalRecords;

	/**
	 * <p>
	 * For all ajax call in Data Entry Search screen
	 * </p>
	 * 
	 * @return String
	 */
	public String ajaxMethod() {
		AppLog.begin();
		// System.out.println("hidAction::" + hidAction);
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
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}
			if ("countNoOfAccountsToBeUpdated".equalsIgnoreCase(hidAction)) {
				if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
					mrKeyList = mrKeyList.replaceFirst(",", "");
					// System.out.println("mrKeyList::" + mrKeyList
					// + "::hidMRKeyStatusCode::" + hidMRKeyStatusCode);
					int totalNoOfAccountsToBeUpdated = OperationMRKeyReviewDAO
							.getNoOfAccountsToBeUpdated(mrKeyList,
									hidMRKeyStatusCode, hidProcessCounter);
					inputStream = (new StringBufferInputStream(Integer
							.toString(totalNoOfAccountsToBeUpdated)));
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red' size='2'><b>Please Select atleast one MRKey to update.</b></font>"));
				}
			}
			if ("updateMRKeyStatusCode".equalsIgnoreCase(hidAction)) {
				if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
					mrKeyList = mrKeyList.replaceFirst(",", "");
					int totalNoOfAccountsUpdated = OperationMRKeyReviewDAO
							.updateMRKeyStatusCode(mrKeyList,
									hidMRKeyStatusCode, hidProcessCounter,
									newMRKeyStatusCode, userId);
					if (totalNoOfAccountsUpdated > 0) {
						session
								.put(
										"AJAX_MESSAGE",
										"<font color='#33CC33'><b> Total "
												+ totalNoOfAccountsUpdated
												+ " Records were Successfully Updated.</b></font>");
						inputStream = (new StringBufferInputStream(Integer
								.toString(totalNoOfAccountsUpdated)));
					} else {
						session
								.put(
										"AJAX_MESSAGE",
										"<font color='red'><b>Records could not be Updated Successfully. Please Retry.</b></font>");
						inputStream = (new StringBufferInputStream(
								"<font color='red'><b>Records could not be Updated Successfully. Please Retry.</b></font>"));
					}
				} else {
					inputStream = (new StringBufferInputStream(
							"<font color='red' size='2'><b>Please Select atleast one MRKey to update.</b></font>"));
				}
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
			// e.printStackTrace();
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @return String
	 */
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		String result = "error";
		try {
			// System.out.println("hidAction::" + hidAction);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "OPERATION");
			if (null == hidAction || "".equalsIgnoreCase(hidAction)) {
				session.put("SERVER_MESSAGE", "");
				session.put("AJAX_MESSAGE", "");
				this.pageNo = "1";
				this.maxRecordPerPage = PropertyUtil
						.getProperty("RECORDS_PER_PAGE");
				session.put("consPreBillStatLookupMap", OperationMRKeyReviewDAO
						.getConsPreBillStatLookupList());
				/**
				 * Start:Comented as per Updated JTrac DJB-1204 on 05-03-2013 by
				 * Matiur Rahman
				 */
				// session.put("consPreBillValidStatLookupMap",
				// OperationMRKeyReviewDAO
				// .getConsPreBillValidChangeStatusList());
				/**
				 * End:Comented as per Updated JTrac DJB-1204 on 05-03-2013 by
				 * Matiur Rahman
				 */
				this.mrKeyStatusCode = "70";
				this.processCounter = "0";
				result = "success";
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "countNoOfAccountsToBeUpdated"
							.equalsIgnoreCase(hidAction)
					|| "updateMRKeyStatusCode".equalsIgnoreCase(hidAction)
					|| "searchAfterUpdate".equalsIgnoreCase(hidAction)) {
				if (null != this.mrKeyStatusCode
						&& !"".equals(this.mrKeyStatusCode.trim())
						&& null != this.processCounter
						&& !"".equals(this.processCounter.trim())) {
					if (null != mrKeyListForSearch) {
						mrKeyListForSearch = mrKeyListForSearch.trim();
						mrKeyListForSearch = mrKeyListForSearch.replaceAll(
								"\\r|\\n", "");
						mrKeyListForSearch = removeLastComma(mrKeyListForSearch);
					}
					/**
					 * Start:Added as per Updated JTrac DJB-1204 on 05-03-2013
					 * by Matiur Rahman
					 */
					if (!(this.mrKeyStatusCode.trim())
							.equalsIgnoreCase(this.hidMRKeyStatusCode)) {
						// System.out.println("Calling validFromStatLookupMap");
						session
								.put(
										"validFromStatLookupMap",
										OperationMRKeyReviewDAO
												.getFromConsPreBillValidStatLookupListQuery(this.mrKeyStatusCode
														.trim()));
						session
								.put(
										"validToStatLookupMap",
										OperationMRKeyReviewDAO
												.getToConsPreBillValidStatLookupList(this.mrKeyStatusCode
														.trim()));
					}
					this.fromMRKeyStatusCode = this.mrKeyStatusCode.trim();
					/**
					 * End:Added as per Updated JTrac DJB-1204 on 05-03-2013 by
					 * Matiur Rahman
					 */
					this.hidMRKeyStatusCode = this.mrKeyStatusCode.trim();
					this.hidProcessCounter = this.processCounter.trim();
					if (null == this.pageNo || "".equals(this.pageNo)) {
						this.pageNo = "1";
						this.maxRecordPerPage = PropertyUtil
								.getProperty("RECORDS_PER_PAGE");
					}
					/* Start:Commented as per JTrac DJB-1970 */
					// this.totalRecords = OperationMRKeyReviewDAO
					// .getTotalCountOfOperationMRKeyReviewRecords(
					// mrKeyStatusCode, processCounter,
					// mrKeyListForSearch);
					/* End:Commented as per JTrac DJB-1970 */
					/* Start:Added as per JTrac DJB-1970 */
					this.totalRecords = OperationMRKeyReviewDAO
							.getTotalCountOfOperationMRKeyReviewRecords(
									mrKeyStatusCode, processCounter,
									mrKeyListForSearch, fromDate, toDate,
									orderBy, selectedBillRound);
					/* End:Added as per JTrac DJB-1970 */
					List<OperationMRKeyReviewDetails> operationMRKeyReviewDetailsList = null;
					if (totalRecords > 0) {
						if (Integer.parseInt(maxRecordPerPage)
								* Integer.parseInt(pageNo) > totalRecords) {
							this.pageNo = Integer.toString((int) totalRecords
									/ Integer.parseInt(maxRecordPerPage) + 1);
						}
						// System.out.println("::search::pageNo::" + pageNo
						// + "::maxRecordPerPage::" + maxRecordPerPage
						// + "::totalRecords::" + totalRecords);
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
						/* Start:Commented as per JTrac DJB-1970 */
						// operationMRKeyReviewDetailsList =
						// (ArrayList<OperationMRKeyReviewDetails>)
						// OperationMRKeyReviewDAO
						// .getOperationMRKeyReviewDetailsList(
						// this.mrKeyStatusCode.trim(),
						// this.processCounter.trim(),
						// mrKeyListForSearch, this.pageNo,
						// this.maxRecordPerPage);
						/* End:Commented as per JTrac DJB-1970 */
						/* Start:Added as per JTrac DJB-1970 */
						operationMRKeyReviewDetailsList = (ArrayList<OperationMRKeyReviewDetails>) OperationMRKeyReviewDAO
								.getOperationMRKeyReviewDetailsList(
										this.mrKeyStatusCode.trim(),
										this.processCounter.trim(),
										mrKeyListForSearch, this.pageNo,
										this.maxRecordPerPage, this.fromDate,
										this.toDate, this.orderBy,
										this.orderByTo, selectedBillRound);
						/* End:Added as per JTrac DJB-1970 */
					}
					if (null != operationMRKeyReviewDetailsList
							&& operationMRKeyReviewDetailsList.size() > 0) {
						if (!"searchAfterUpdate".equalsIgnoreCase(hidAction)) {
							session.put("AJAX_MESSAGE", "");
						}
						session.put("SERVER_MESSAGE", "");
						this.operationMRKeyReviewDetailsList = operationMRKeyReviewDetailsList;
					} else {
						if ("searchAfterUpdate".equalsIgnoreCase(hidAction)) {
							this.hidAction = "search";
							if (Integer.parseInt(this.pageNo) > 1) {
								this.pageNo = Integer.toString(Integer
										.parseInt(this.pageNo) - 1);
							}
							// System.out.println("hidAction::" + hidAction
							// + "::totalRecords::" + totalRecords);
							if (totalRecords > 0) {
								// System.out
								// .println("::searchAfterUpdate::pageNo::"
								// + pageNo
								// + "::maxRecordPerPage::"
								// + maxRecordPerPage
								// + "::totalRecords::"
								// + totalRecords);
								List<String> pageNoList = new ArrayList<String>();
								List<String> maxPageNoList = new ArrayList<String>();
								for (int i = 0, j = 1; i < totalRecords; i++) {
									if (i % Integer.parseInt(maxRecordPerPage) == 0) {
										pageNoList.add(Integer.toString(j++));
										if (i != 0 && i <= 200) {
											maxPageNoList.add(Integer
													.toString(i));
										}
									}
								}
								this.pageNoDropdownList = pageNoList;
								/* Start:Commented as per JTrac DJB-1970 */
								// operationMRKeyReviewDetailsList =
								// (ArrayList<OperationMRKeyReviewDetails>)
								// OperationMRKeyReviewDAO
								// .getOperationMRKeyReviewDetailsList(
								// this.mrKeyStatusCode.trim(),
								// this.processCounter.trim(),
								// mrKeyListForSearch,
								// this.pageNo,
								// this.maxRecordPerPage);
								/* End:Commented as per JTrac DJB-1970 */
								/* Start:Added as per JTrac DJB-1970 */
								operationMRKeyReviewDetailsList = (ArrayList<OperationMRKeyReviewDetails>) OperationMRKeyReviewDAO
										.getOperationMRKeyReviewDetailsList(
												this.mrKeyStatusCode.trim(),
												this.processCounter.trim(),
												mrKeyListForSearch,
												this.pageNo,
												this.maxRecordPerPage,
												this.fromDate, this.toDate,
												this.orderBy, this.orderByTo,
												selectedBillRound);
								/* End:Added as per JTrac DJB-1970 */
							}
							if (null != operationMRKeyReviewDetailsList
									&& operationMRKeyReviewDetailsList.size() > 0) {
								this.operationMRKeyReviewDetailsList = operationMRKeyReviewDetailsList;
							} else {
								session
										.put(
												"SERVER_MESSAGE",
												"<font color='red'>For Page No. : "
														+ this.pageNo
														+ " No more records left to Display. Refresh the Search Criteia.</font>");
							}
						} else {
							session.put("AJAX_MESSAGE", "");
							session
									.put("SERVER_MESSAGE",
											"<font color='red'>No records found to Display for the Search Criteria.</font>");
						}
					}
				} else {
					addActionError("Please Enter Mandatory Fields");
				}
				/* Start:Added as per JTrac DJB-1970 */
				String fromDateTemp = fromDate;
				if (null != fromDateTemp && !"".equals(fromDateTemp)) {
					String dd = fromDateTemp.substring(0, 2);
					String mm = fromDateTemp.substring(3, 5);
					String yyyy = fromDateTemp.substring(6);
					fromDate = mm + "/" + dd + "/" + yyyy;
				}
				String toDateTemp = toDate;
				if (null != toDateTemp && !"".equals(toDateTemp)) {
					String dd = toDateTemp.substring(0, 2);
					String mm = toDateTemp.substring(3, 5);
					String yyyy = toDateTemp.substring(6);
					toDate = mm + "/" + dd + "/" + yyyy;
				}
				/* End:Added as per JTrac DJB-1970 */
				result = "success";
			}
		} catch (Exception e) {
			addActionError("There was a problem while Processing your Request. Please Try Again.");
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @return the fromMRKeyStatusCode
	 */
	public String getFromMRKeyStatusCode() {
		return fromMRKeyStatusCode;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the hidMRKeyStatusCode
	 */
	public String getHidMRKeyStatusCode() {
		return hidMRKeyStatusCode;
	}

	/**
	 * @return the hidProcessCounter
	 */
	public String getHidProcessCounter() {
		return hidProcessCounter;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the mrKeyList
	 */
	public String getMrKeyList() {
		return mrKeyList;
	}

	/**
	 * @return the mrKeyListForSearch
	 */
	public String getMrKeyListForSearch() {
		return mrKeyListForSearch;
	}

	/**
	 * @return the mrKeyStatusCode
	 */
	public String getMrKeyStatusCode() {
		return mrKeyStatusCode;
	}

	/**
	 * @return the newMRKeyStatusCode
	 */
	public String getNewMRKeyStatusCode() {
		return newMRKeyStatusCode;
	}

	/**
	 * @return the operationMRKeyReviewDetailsList
	 */
	public List<OperationMRKeyReviewDetails> getOperationMRKeyReviewDetailsList() {
		return operationMRKeyReviewDetailsList;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @return the orderByTo
	 */
	public String getOrderByTo() {
		return orderByTo;
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
	 * @return the processCounter
	 */
	public String getProcessCounter() {
		return processCounter;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedBillRound
	 */
	public String getSelectedBillRound() {
		return selectedBillRound;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * <p>
	 * This method is used to remove Last comma.
	 * </p>
	 * 
	 * @param str
	 * @return String
	 */
	public String removeLastComma(String str) {
		if (null != str && str.length() != 0
				&& str.lastIndexOf(',') + 1 == str.length()) {
			return removeLastComma(str.substring(0, str.lastIndexOf(',')));
		} else {
			return str;
		}
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @param fromMRKeyStatusCode
	 *            the fromMRKeyStatusCode to set
	 */
	public void setFromMRKeyStatusCode(String fromMRKeyStatusCode) {
		this.fromMRKeyStatusCode = fromMRKeyStatusCode;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param hidMRKeyStatusCode
	 *            the hidMRKeyStatusCode to set
	 */
	public void setHidMRKeyStatusCode(String hidMRKeyStatusCode) {
		this.hidMRKeyStatusCode = hidMRKeyStatusCode;
	}

	/**
	 * @param hidProcessCounter
	 *            the hidProcessCounter to set
	 */
	public void setHidProcessCounter(String hidProcessCounter) {
		this.hidProcessCounter = hidProcessCounter;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param mrKeyList
	 *            the mrKeyList to set
	 */
	public void setMrKeyList(String mrKeyList) {
		this.mrKeyList = mrKeyList;
	}

	/**
	 * @param mrKeyListForSearch
	 *            the mrKeyListForSearch to set
	 */
	public void setMrKeyListForSearch(String mrKeyListForSearch) {
		this.mrKeyListForSearch = mrKeyListForSearch;
	}

	/**
	 * @param mrKeyStatusCode
	 *            the mrKeyStatusCode to set
	 */
	public void setMrKeyStatusCode(String mrKeyStatusCode) {
		this.mrKeyStatusCode = mrKeyStatusCode;
	}

	/**
	 * @param newMRKeyStatusCode
	 *            the newMRKeyStatusCode to set
	 */
	public void setNewMRKeyStatusCode(String newMRKeyStatusCode) {
		this.newMRKeyStatusCode = newMRKeyStatusCode;
	}

	/**
	 * @param operationMRKeyReviewDetailsList
	 *            the operationMRKeyReviewDetailsList to set
	 */
	public void setOperationMRKeyReviewDetailsList(
			List<OperationMRKeyReviewDetails> operationMRKeyReviewDetailsList) {
		this.operationMRKeyReviewDetailsList = operationMRKeyReviewDetailsList;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @param orderByTo
	 *            the orderByTo to set
	 */
	public void setOrderByTo(String orderByTo) {
		this.orderByTo = orderByTo;
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
	 * @param processCounter
	 *            the processCounter to set
	 */
	public void setProcessCounter(String processCounter) {
		this.processCounter = processCounter;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedBillRound
	 *            the selectedBillRound to set
	 */
	public void setSelectedBillRound(String selectedBillRound) {
		this.selectedBillRound = selectedBillRound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
	 * (javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
