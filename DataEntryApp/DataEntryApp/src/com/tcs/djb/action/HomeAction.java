/************************************************************************************************************
 * @(#) HomeAction.java   19-Dec-2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.DiconnectionDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.DisconnectionDetails;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * This is an action class to save unmetered consumers data
 * </p>
 * 
 * @author Matiur Rahman
 * @history Added defaultBillGenDate by Matiur Rahman on 19-12-2012 as per JTrac
 *          DJB-830
 * @history Added functionality to refresh Freeze/Review MRD screen by Matiur
 *          Rahman on 21-02-2013
 * @history 18-03-2013 Matiur Rahman changed userName to userId.
 * 
 */
public class HomeAction extends ActionSupport implements ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private String area;
	private String billRound;
	private String buttonClicked;
	private String callFromLinkName;
	private String defaultBillGenDate;
	private String hidAction;

	private InputStream inputStream;
	private String lastPage;
	private String mrNo;
	private HttpServletResponse response;
	private String searchFor;
	private String selectedArea;
	private String selectedBillWindow;
	private String selectedMRNo;
	private String selectedZone;
	private String startPage;
	private String zone;

	/**
	 * For all ajax call in Disconnection screen
	 * 
	 * @return string
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			// System.out.println("hidAction>>" + hidAction + ">>>zone>>" + zone
			// + ">>mrNo>>>" + mrNo + ">>area>>>" + area
			// + ">>>billWindow>>>" + billRound
			// + ">>>defaultBillGenDate>>>" + defaultBillGenDate);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				inputStream = new StringBufferInputStream(
						"ERROR:Sorry, Your Session Has Expired, Please Login and Try Again.");
				AppLog.end();
				return "login";
			}
			session.remove("DataSaved");
			if (null == defaultBillGenDate
					|| "".equalsIgnoreCase(defaultBillGenDate.trim())
					|| null == this.area
					|| "".equalsIgnoreCase(this.area.trim())) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>Please Enter mandatory Fields to Proceed.</b></span></li></font>");
			} else {
				if ("saveUnmeteredConsumerData".equalsIgnoreCase(hidAction)) {

					String searchString = (this.area).substring((this.area)
							.indexOf("-(") + 2, (this.area).length() - 1);
					String searchZone = searchString.substring(searchString
							.lastIndexOf('-') + 1);
					String searchMRNo = searchString.substring(searchString
							.indexOf('-') + 1, searchString.lastIndexOf('-'));
					String searchArea = searchString.substring(0, searchString
							.indexOf('-'));
					Map<String, String> inputMap = new HashMap<String, String>();
					inputMap.put("zone", searchZone);
					inputMap.put("mrNo", searchMRNo);
					inputMap.put("area", searchArea);
					inputMap.put("billRound", billRound);
					inputMap.put("billGenDate", defaultBillGenDate);
					inputMap.put("updatedBy", userName);
					SetterDAO setterDAO = new SetterDAO();
					Map<String, String> returnMap = (HashMap<String, String>) setterDAO
							.updateMeterReadForUnmeteredConsumerForAnMRD(inputMap);
					String noOfUnprocessedRecords = (String) returnMap
							.get("noOfUnprocessedRecords");

					if ("0".equalsIgnoreCase(noOfUnprocessedRecords)) {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><li><span><b>No records were found to be Updated.</b></span></li></font>");
					} else {
						String recordUpdated = (String) returnMap
								.get("recordUpdated");
						if ("0".equalsIgnoreCase(recordUpdated)) {
							inputStream = new StringBufferInputStream(
									"<font color='red' size='2'><li><span><b>Sorry No records were Updated.</b></span></li></font>");
						} else {
							inputStream = new StringBufferInputStream(
									"<font color='green' size='3'><li><span><b>Total No. of Records Processed :: "
											+ recordUpdated
											+ "</b></span></li></font>");
						}
					}
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
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
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			String userRoleId = (String) session.get("userRoleId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "HOME");
			if (null == hidAction
					|| (null != hidAction && "".equalsIgnoreCase(hidAction
							.trim()))) {
				// if ("2".equalsIgnoreCase(userRoleId)) {
				// return "dataEntry";
				// } else if ("3".equalsIgnoreCase(userRoleId)) {
				// return "dataEntry";
				// } else {
				return "success";
				// }
			}
			if ("refreshMRDReview".equalsIgnoreCase(hidAction)) {
				return "gotofreeze";
			}
			session.remove("LAST_SUBMIT");
			session.remove("LAST_ACTION_FOR_MRD");
			if (null == callFromLinkName || "".equals(callFromLinkName.trim())) {
				session.put("LAST_SUBMIT", "home");
			} else {
				session.put("LAST_SUBMIT", callFromLinkName);
				session.put("LAST_ACTION_FOR_MRD", callFromLinkName);
			}
			MRDReadTypeLookup mrdReadTypeLookup = (MRDReadTypeLookup) session
					.get("mrdReadTypeLookup");
			if (null == mrdReadTypeLookup) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			// System.out.println("selectedArea>>>" + this.selectedArea
			// + ">>>startPage>>" + this.startPage + ">>>>>lastPage>>>>>"
			// + this.lastPage+">>buttonClicked>>"+this.buttonClicked);
			if (null != this.startPage && !"".equals(this.startPage)) {
				session.put("startPage", this.startPage);
				session.put("lastPage", this.lastPage);
				session.put("buttonClicked", this.buttonClicked);
				String isFrozen = (String) session.get("isFrozen");
				/*
				 * String maxRecordPerPage = PropertyUtil
				 * .getProperty("RECORDS_PER_PAGE"); int
				 * lastPageNo=Integer.parseInt
				 * (this.startPage)+Integer.parseInt(maxRecordPerPage)-1;
				 * session.put("startPage", this.startPage);
				 * session.put("lastPage", Integer.toString(lastPageNo));
				 * session.put("buttonClicked", this.buttonClicked);
				 * this.lastPage=Integer.toString(lastPageNo);
				 */
				if (null != isFrozen && "Y".equals(isFrozen)) {
					session.put("freezeSuccess", "The MRD is Already Frozen.");
					return "gotofreeze";
				}
				MRDContainer mrdContainer = (MRDContainer) session
						.get("mrdContainer");
				if (null != mrdContainer) {
					session.remove("mrdContainer");
					session.put("mrdContainer", mrdContainer);
					// System.out.println("::mrdContainer is not null:::");
				}/*
				 * else { // System.out.println("::mrdContainer is null:::>>");
				 * }
				 */
				AppLog.end();
				return "reload";
			}
			if (null == this.selectedArea
					|| "".equalsIgnoreCase(this.selectedArea)) {
				addActionError(getText("error.all.mandatory"));
				AppLog.end();
				return "error";
			}
			if (null == this.selectedBillWindow
					|| "".equalsIgnoreCase(this.selectedBillWindow)) {
				addActionError(getText("error.all.mandatory"));
				AppLog.end();
				return "error";
			} else {
				String searchString = (this.selectedArea).substring(
						(this.selectedArea).indexOf("-(") + 2,
						(this.selectedArea).length() - 1);
				String searchZone = searchString.substring(searchString
						.lastIndexOf('-') + 1);
				String searchMRNo = searchString.substring(searchString
						.indexOf('-') + 1, searchString.lastIndexOf('-'));
				String searchArea = searchString.substring(0, searchString
						.indexOf('-'));
				// System.out.println("searchString>>>>" + searchString
				// + ">>>searchZone>>>>" + searchZone
				// + ">>>searchMRNo>>>>" + searchMRNo
				// + ">>>searchArea>>>>" + searchArea + ">>searchFor>>"
				// + searchFor);
				session.put("searchZoneCode", searchZone);
				session.put("searchZone", this.selectedZone);
				session.put("searchMRNo", searchMRNo);
				session.put("searchAreaCode", searchArea);
				session.put("searchBillWindow", selectedBillWindow);
				session.put("searchArea", (this.selectedArea).substring(0,
						(this.selectedArea).indexOf("-(")));
				MRDContainer mrdContainer = new MRDContainer(searchZone,
						searchMRNo, searchArea, selectedBillWindow);
				GetterDAO getterDAO = new GetterDAO();
				mrdContainer = (MRDContainer) getterDAO.getMRD(mrdContainer);
				// session.remove("mrdContainer");
				if (null != mrdContainer
						&& null != mrdContainer.getConsumerList()
						&& mrdContainer.getConsumerList().size() > 0) {
					session.remove("mrdContainer");
					session.remove("DataSaved");
					session.put("mrdContainer", mrdContainer);
					String lastPageNo = PropertyUtil
							.getProperty("RECORDS_PER_PAGE");
					if (Integer.parseInt(lastPageNo) > mrdContainer
							.getConsumerList().size()) {
						lastPageNo = Integer.toString(mrdContainer
								.getConsumerList().size());
					}
					session.put("startPage", "1");
					session.put("lastPage", lastPageNo);
					session.remove("buttonClicked");
					session.remove("freezeSuccess");
					session.remove("isFrozen");
					if ("Review".equalsIgnoreCase(searchFor)) {
						return "gotofreeze";
					}
				} else {
					addActionError(getText("error.search.norecords"));
					session.remove("mrdContainer");
					AppLog.end();
					return "error";
				}

			}
			/*
			 * if (null == session.get("mrdContainer")) { //
			 * AppLog.info("::mrdContainer is null:::"); }
			 */
		} catch (ClassCastException e) {
			addActionError(getText("error.research"));
			AppLog.end();
			return "error";
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			return "login";
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	public String getButtonClicked() {
		return buttonClicked;
	}

	/**
	 * @return the callFromLinkName
	 */
	public String getCallFromLinkName() {
		return callFromLinkName;
	}

	/**
	 * @return the defaultBillGenDate
	 */
	public String getDefaultBillGenDate() {
		return defaultBillGenDate;
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
	 * @return the lastPage
	 */
	public String getLastPage() {
		return lastPage;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the searchFor
	 */
	public String getSearchFor() {
		return searchFor;
	}

	/**
	 * @return the selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
	}

	/**
	 * @return the selectedBillWindow
	 */
	public String getSelectedBillWindow() {
		return selectedBillWindow;
	}

	/**
	 * @return the selectedMRNo
	 */
	public String getSelectedMRNo() {
		return selectedMRNo;
	}

	/**
	 * @return the selectedZone
	 */
	public String getSelectedZone() {
		return selectedZone;
	}

	/**
	 * @return startPage
	 */
	public String getStartPage() {
		return startPage;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param buttonClicked
	 */
	public void setButtonClicked(String buttonClicked) {
		this.buttonClicked = buttonClicked;
	}

	/**
	 * @param callFromLinkName
	 *            the callFromLinkName to set
	 */
	public void setCallFromLinkName(String callFromLinkName) {
		this.callFromLinkName = callFromLinkName;
	}

	/**
	 * @param defaultBillGenDate
	 *            the defaultBillGenDate to set
	 */
	public void setDefaultBillGenDate(String defaultBillGenDate) {
		this.defaultBillGenDate = defaultBillGenDate;
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
	 * @param lastPage
	 */
	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param searchFor
	 */
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	/**
	 * @param selectedArea
	 */
	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

	/**
	 * @param selectedBillWindow
	 *            the selectedBillWindow to set
	 */
	public void setSelectedBillWindow(String selectedBillWindow) {
		this.selectedBillWindow = selectedBillWindow;
	}

	/**
	 * @param selectedMRNo
	 */
	public void setSelectedMRNo(String selectedMRNo) {
		this.selectedMRNo = selectedMRNo;
	}

	/**
	 * @param selectedZone
	 */
	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param startPage
	 */
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

}
