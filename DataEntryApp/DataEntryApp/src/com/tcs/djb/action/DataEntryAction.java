/************************************************************************************************************
 * @(#) DataEntryAction.java   19-Dec-2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReplacementDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;
import com.tcs.djb.model.*;

/**
 * <p>
 * This is the Action class for Freeze/Review MRD screen
 * </p>
 * 
 * @author Matiur Rahman
 * @history Added defaultBillGenDate by Matiur Rahman on 19-12-2012 as per JTrac
 *          DJB-830
 * @history Added functionality to refresh Freeze/Review MRD screen by Matiur
 *          Rahman on 21-02-2013
 * @history 19-03-2013 Matiur Rahman modified execute method adding
 *          ZoneListMap,MRNoListMap,AreaListMap to session.
 * @history 17-11-2016 Sanjay added HighLowValCnt in session and took value from
 *          DJBConstant into highLowStatus as per jtrac-4583 and Open
 *          Project-1595.
 */
public class DataEntryAction extends ActionSupport implements
		ServletResponseAware {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	private String area;
	private String billRound;
	private String buttonClicked;
	private String callFromLinkName;
	private String defaultBillGenDate;
	private String hidAction;

	private String hidBillRound;
	private InputStream inputStream;
	private String lastPage;
	private String mrNo;
	private HttpServletResponse response;
	private String searchFor;
	private String selectedArea;
	private String selectedAreaDesc;
	private String selectedBillWindow;

	private String selectedMRNo;

	private String selectedZone;

	private String startPage;

	private String zone;

	/**
	 * For all ajax call in Data Entry Search screen
	 * 
	 * @return string
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			// System.out.println("hidAction>>" + hidAction + ">>>zone>>"
			// + selectedZone + ">>mrNo>>>" + selectedMRNo + ">>area>>>"
			// + selectedArea + ">>>billWindow>>>" + billRound
			// + ">>>defaultBillGenDate>>>" + defaultBillGenDate);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.login.expired"));
				AppLog.end();
				return "success";
			}
			if ("populateMRNo".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(selectedZone);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedMRNo\" id=\"selectedMRNo\" class=\"selectbox-long\" onfocus=\"fnCheckZone();\" onchange=\"fnGetArea();\">");
				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(entry.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(entry.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("MRNoListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
			}
			if ("populateArea".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getAreaListMap(selectedZone, selectedMRNo);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedArea\" id=\"selectedArea\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo();\" onchange=\"fnGetOpenBillRound();\">");

				dropDownSB.append("<option value=''>Please Select</option>");
				if (null != returnMap && !returnMap.isEmpty()) {
					for (Entry<String, String> entry : returnMap.entrySet()) {
						dropDownSB.append(optionTagBeginPart1);
						dropDownSB.append(entry.getKey());
						dropDownSB.append(optionTagBeginPart2);
						dropDownSB.append(entry.getValue());
						dropDownSB.append(optionTagEnd);
					}
				}
				dropDownSB.append(selectTagEnd);
				session.put("AreaListMap", returnMap);
				inputStream = new StringBufferInputStream(dropDownSB.toString());
			}
			if ("populateOpenBillRound".equalsIgnoreCase(hidAction)) {
				String billRoundId = "";
				List<String> billRoundList = (ArrayList<String>) GetterDAO
						.getOpenBillRound(selectedZone, selectedMRNo,
								selectedArea, "OPEN");
				if (null != billRoundList && billRoundList.size() > 0) {
					if (billRoundList.size() == 1) {
						billRoundId = billRoundList.get(0);
						this.selectedBillWindow = billRoundId;
					}
					inputStream = new StringBufferInputStream(billRoundId);
				} else {
					billRoundList = (ArrayList<String>) GetterDAO
							.getOpenBillRound(selectedZone, selectedMRNo,
									selectedArea, "PROCESSED");
					if (null != billRoundList && billRoundList.size() > 0) {
						inputStream = new StringBufferInputStream(
								"<font color='orange' size='2'><b>WARNING: MRD has been already Processed and No new Bill Round is Opened yet, Please choose a different Combination.</b></font>");
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><b>WARNING: MRD has not been Checked Out Yet, Please Contact System Administrator to Check Out the MRD.</b></font>");
					}
				}
			}
			session.remove("DataSaved");
			if ("saveUnmeteredConsumerData".equalsIgnoreCase(hidAction)) {
				if (null == defaultBillGenDate
						|| "".equalsIgnoreCase(defaultBillGenDate.trim())
						|| null == this.selectedArea
						|| "".equalsIgnoreCase(this.selectedArea.trim())) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>Please Enter mandatory Fields to Proceed.</b></span></li></font>");
				} else {
					// String searchString = (this.area).substring((this.area)
					// .indexOf("-(") + 2, (this.area).length() - 1);
					// String searchZone = searchString.substring(searchString
					// .lastIndexOf('-') + 1);
					// String searchMRNo = searchString.substring(searchString
					// .indexOf('-') + 1, searchString.lastIndexOf('-'));
					// String searchArea = searchString.substring(0,
					// searchString
					// .indexOf('-'));
					Map<String, String> inputMap = new HashMap<String, String>();
					inputMap.put("zone", selectedZone);
					inputMap.put("mrNo", selectedMRNo);
					inputMap.put("area", selectedArea);
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
			String highLowStatus = DJBConstants.HIGH_LOW_STATUS;
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			/** Load Default Values. */
			loadDefault();
			if ("refreshMRDReview".equalsIgnoreCase(hidAction)) {
				return "gotofreeze";
			}
			if (null == callFromLinkName || "".equals(callFromLinkName.trim())) {
				session.put("LAST_SUBMIT", "home");
				session.put("LAST_ACTION_FOR_MRD", searchFor);
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
			// System.out.println("hidAction>>" + hidAction + ">>>zone>>"
			// + selectedZone + ">>mrNo>>>" + selectedMRNo + ">>area>>>"
			// + selectedArea + ">>>billWindow>>>" + selectedBillWindow
			// + ">>>defaultBillGenDate>>>" + defaultBillGenDate
			// + "::hidBillRound::" + hidBillRound);
			if (null == this.selectedZone
					|| "".equalsIgnoreCase(this.selectedZone)) {
				if (null != hidAction) {
					addActionError(getText("error.all.mandatory"));
				}
				AppLog.end();
				return "error";
			}
			if (null == this.selectedMRNo
					|| "".equalsIgnoreCase(this.selectedMRNo)) {
				if (null != hidAction) {
					addActionError(getText("error.all.mandatory"));
				}
				AppLog.end();
				return "error";
			}
			if (null == this.selectedArea
					|| "".equalsIgnoreCase(this.selectedArea)) {
				if (null != hidAction) {
					addActionError(getText("error.all.mandatory"));
				}
				AppLog.end();
				return "error";
			}
			if (null == this.selectedBillWindow
					|| "".equalsIgnoreCase(this.selectedBillWindow)) {
				this.selectedBillWindow = this.hidBillRound;
			}
			if (null == this.selectedBillWindow
					|| "".equalsIgnoreCase(this.selectedBillWindow)) {
				if (null != hidAction) {
					addActionError(getText("error.all.mandatory"));
				}
				AppLog.end();
				return "error";
			} else {
				// String searchString = (this.selectedArea).substring(
				// (this.selectedArea).indexOf("-(") + 2,
				// (this.selectedArea).length() - 1);
				// String searchZone = searchString.substring(searchString
				// .lastIndexOf('-') + 1);
				// String searchMRNo = searchString.substring(searchString
				// .indexOf('-') + 1, searchString.lastIndexOf('-'));
				// String searchArea = searchString.substring(0, searchString
				// .indexOf('-'));
				// System.out.println("searchString>>>>" + searchString
				// + ">>>searchZone>>>>" + searchZone
				// + ">>>searchMRNo>>>>" + searchMRNo
				// + ">>>searchArea>>>>" + searchArea + ">>searchFor>>"
				// + searchFor);
				session.put("searchZoneCode", this.selectedZone);
				session.put("searchZone", this.selectedZone);
				session.put("searchMRNo", this.selectedMRNo);
				session.put("searchAreaCode", this.selectedArea);
				session.put("searchBillWindow", selectedBillWindow);
				session.put("searchArea", this.selectedAreaDesc);
				MRDContainer mrdContainer = new MRDContainer(selectedZone,
						selectedMRNo, selectedArea, selectedBillWindow);
				GetterDAO getterDAO = new GetterDAO();
				mrdContainer = (MRDContainer) getterDAO.getMRD(mrdContainer);
				/*
				 * Start : Sanjay on 17-11-2016 added the below line to set Hi
				 * Low Flag Value to defalut 0, Open Project Id: 1595, DJB-4583
				 */
				session.put("HighLowValCnt", "0");
				// session.remove("mrdContainer");
				if (null != mrdContainer
						&& null != mrdContainer.getConsumerList()
						&& mrdContainer.getConsumerList().size() > 0) {

					ArrayList<ConsumerDetail> consumerList = (ArrayList<ConsumerDetail>) mrdContainer
							.getConsumerList();
					for (int i = 0; i < consumerList.size(); i++) {
						ConsumerDetail consumerDetail = new ConsumerDetail();
						consumerDetail = consumerList.get(i);
						if (null != consumerDetail
								&& null != consumerDetail.getHiLowFlag()
								&& !highLowStatus
										.equalsIgnoreCase(consumerDetail
												.getHiLowFlag())) {
							session.put("HighLowValCnt", "1");
							break;
						}
					}
					/*
					 * End : Sanjay on 17-11-2016 added the below line to set Hi
					 * Low Flag Value to defalut 0 and 1 on getting High Low
					 * case, Open Project Id: 1595, DJB-4583
					 */
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
						session.remove("MR_kno");
						session.remove("MR_mrKeyList");
						session.remove("MR_zoneMR");
						session.remove("MR_mrNoMR");
						session.remove("MR_areaMR");
						session.remove("MR_maxRecordPerPage");
						session.remove("MR_pageNo");
						session.remove("MR_searchFor");
						session.remove("MRReview");
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
			// addActionError(getText("error.research"));
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

	/**
	 * @return buttonClicked
	 */
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
	 * @return the hidBillRound
	 */
	public String getHidBillRound() {
		return hidBillRound;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return lastPage
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
	 * @return searchFor
	 */
	public String getSearchFor() {
		return searchFor;
	}

	/**
	 * @return selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
	}

	/**
	 * @return the selectedAreaDesc
	 */
	public String getSelectedAreaDesc() {
		return selectedAreaDesc;
	}

	/**
	 * @return the selectedBillWindow
	 */
	public String getSelectedBillWindow() {
		return selectedBillWindow;
	}

	/**
	 * @return selectedMRNo
	 */
	public String getSelectedMRNo() {
		return selectedMRNo;
	}

	/**
	 * @return selectedZone
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
	 * <p>
	 * This method is used to Populate default values required for Search Screen.
	 * </p>
	 */
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "MRD");
			if (null == session.get("ZoneListMap")
					|| ((HashMap<String, String>) session.get("ZoneListMap"))
							.isEmpty()) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
			}
			if (null == session.get("MRNoListMap")
					|| ((HashMap<String, String>) session.get("MRNoListMap"))
							.isEmpty()) {
				if (null != selectedZone && !"".equals(selectedZone)) {
					session.put("MRNoListMap",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(selectedZone));
				} else {
					session.put("MRNoListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("AreaListMap")
					|| ((HashMap<String, String>) session.get("AreaListMap"))
							.isEmpty()) {
				if (null != selectedZone && !"".equals(selectedZone)
						&& null != selectedMRNo && !"".equals(selectedMRNo)) {
					session.put("AreaListMap",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									selectedZone, selectedMRNo));
				} else {
					session.put("AreaListMap", new HashMap<String, String>());
				}
			}
			if (null == session.get("oldMeterRemarkCodeList")) {
				session.put("oldMeterRemarkCodeList", MeterReplacementDAO
						.getOldMeterRemarkCodeList());
			}
			session.remove("LAST_SUBMIT");
			session.remove("LAST_ACTION_FOR_MRD");
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
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
	 * @param hidBillRound
	 *            the hidBillRound to set
	 */
	public void setHidBillRound(String hidBillRound) {
		this.hidBillRound = hidBillRound;
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
	 * @param selectedAreaDesc
	 *            the selectedAreaDesc to set
	 */
	public void setSelectedAreaDesc(String selectedAreaDesc) {
		this.selectedAreaDesc = selectedAreaDesc;
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
