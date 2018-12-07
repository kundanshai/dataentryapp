/************************************************************************************************************
 * @(#) MtrReadImgAuditAction.java   25 Jan 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReadImgAuditDAO;
import com.tcs.djb.model.ImageAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action Class for Meter Read Image Audit Screen.
 * </p>
 * 
 * @author Atanu Mandal(Tata Consultancy Services)
 * @since 25-01-2016
 */

@SuppressWarnings("deprecation")
public class MtrReadImgAuditAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "40";

	private String data;

	private String hidAction;

	ImageAuditSearchCriteria imageAuditSearchCriteria;

	private InputStream inputStream;

	private String kno;

	private String maxRecordPerPage;

	List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList;
	private String pageNo;

	List<String> pageNoDropdownList;

	private HttpServletResponse response;

	private String searchBillRound;

	private String searchZROCode;

	private String selectedArea;

	private String selectedAuditStatus;

	private String selectedBillRound;

	private String selectedMRNo;

	private String selectedZone;

	private String selectedZROCode;
	private int totalRecords;
	private String selectedBillGenSource;
	private String selectedMtrRdrEmpId;
	private String selectedBillGenToDate;
	private String selectedBillGenFromDate;
	private String searchMeterRdrEmpId;
	private String searchdBillGenSource;
	private String billRoundStartDate;
	private String billRoundEndDate;

	/**
	 * @return the selectedBillGenSource
	 */
	public String getSelectedBillGenSource() {
		return selectedBillGenSource;
	}

	/**
	 * @param selectedBillGenSource
	 */
	public void setSelectedBillGenSource(String selectedBillGenSource) {
		this.selectedBillGenSource = selectedBillGenSource;
	}

	/**
	 * @return the selectedMtrRdrEmpId
	 */
	public String getSelectedMtrRdrEmpId() {
		return selectedMtrRdrEmpId;
	}

	/**
	 * @param selectedMtrRdrEmpId
	 */
	public void setSelectedMtrRdrEmpId(String selectedMtrRdrEmpId) {
		this.selectedMtrRdrEmpId = selectedMtrRdrEmpId;
	}

	/**
	 * @return the billRoundStartDate
	 */
	public String getBillRoundStartDate() {
		return billRoundStartDate;
	}

	/**
	 * @param billRoundStartDate
	 */
	public void setBillRoundStartDate(String billRoundStartDate) {
		this.billRoundStartDate = billRoundStartDate;
	}

	/**
	 * @return the billRoundEndDate
	 */
	public String getBillRoundEndDate() {
		return billRoundEndDate;
	}

	/**
	 * @param billRoundEndDate
	 */
	public void setBillRoundEndDate(String billRoundEndDate) {
		this.billRoundEndDate = billRoundEndDate;
	}

	/**
	 * @return the searchdBillGenSource
	 */
	public String getSearchdBillGenSource() {
		return searchdBillGenSource;
	}

	/**
	 * @param searchdBillGenSource
	 */
	public void setSearchdBillGenSource(String searchdBillGenSource) {
		this.searchdBillGenSource = searchdBillGenSource;
	}

	/**
	 * @return the searchMeterRdrEmpId
	 */
	public String getSearchMeterRdrEmpId() {
		return searchMeterRdrEmpId;
	}

	/**
	 * @param searchMeterRdrEmpId
	 */
	public void setSearchMeterRdrEmpId(String searchMeterRdrEmpId) {
		this.searchMeterRdrEmpId = searchMeterRdrEmpId;
	}

	/**
	 * @return the selectedMtrRdrEmpId
	 */
	public String getselectedMtrRdrEmpId() {
		return selectedMtrRdrEmpId;
	}

	/**
	 * @param selectedMtrRdrEmpId
	 */
	public void setselectedMtrRdrEmpId(String selectedMtrRdrEmpId) {
		this.selectedMtrRdrEmpId = selectedMtrRdrEmpId;
	}

	/**
	 * @return the selectedBillGenToDate
	 */
	public String getSelectedBillGenToDate() {
		return selectedBillGenToDate;
	}

	/**
	 * @param selectedBillGenToDate
	 */
	public void setSelectedBillGenToDate(String selectedBillGenToDate) {
		this.selectedBillGenToDate = selectedBillGenToDate;
	}

	/**
	 * @return the selectedBillGenFromDate
	 */
	public String getSelectedBillGenFromDate() {
		return selectedBillGenFromDate;
	}

	/**
	 * @param selectedBillGenFromDate
	 */
	public void setSelectedBillGenFromDate(String selectedBillGenFromDate) {
		this.selectedBillGenFromDate = selectedBillGenFromDate;
	}

	/**
	 * For all ajax call in Meter Read Image Audit screen
	 * 
	 * @return string
	 */
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
			if ("updateAuditStatus".equalsIgnoreCase(hidAction)) {
				String updateResponse = updateImageAuditStatus();
				inputStream = new StringBufferInputStream(updateResponse);
			}

			/*
			 * Start :-Added by Madhuri on 31st Aug 2017 as per Jtrac Id-
			 * Android-388
			 */
			if ("getBillRoundStartEndDate".equalsIgnoreCase(hidAction)) {
				if (null != selectedBillRound
						&& !"".equalsIgnoreCase(selectedBillRound)) {
					Map<String, String> startDateAndEndDate = (HashMap<String, String>) MeterReadImgAuditDAO
							.getBillRoundStartAndEndDate(selectedBillRound);
					String startDate = (String) startDateAndEndDate
							.get("billRoundStartDate");
					String enDate = (String) startDateAndEndDate
							.get("billRoundEndDate");
					inputStream = new StringBufferInputStream("startDate-"
							+ startDate + "-EndDate-" + enDate);
				}

			}
			/*
			 * Start :-Added by Madhuri on 31st Aug 2017 as per Jtrac Id-
			 * Android-388
			 */

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;

	}

	/**
	 * (non-Javadoc)
	 * 
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
				AppLog.end();
				return "login";
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			/** Load Default Values. */
			loadDefault();
			if ("search".equalsIgnoreCase(hidAction)
					|| "Next".equalsIgnoreCase(hidAction)
					|| "Prev".equalsIgnoreCase(hidAction)
					|| "refresh".equalsIgnoreCase(hidAction)) {
				AppLog
						.info("Inside Execute Method for Search with \n hidAction : "
								+ hidAction
								+ "\n ZONE : "
								+ selectedZone
								+ "\n MRNO : "
								+ selectedMRNo
								+ "\n AREA : "
								+ selectedArea
								+ "\nZROCD : "
								+ searchZROCode
								+ "\n kno : "
								+ kno
								+ "\n BillRound : "
								+ searchBillRound
								+ "\n AuditStatus : "
								+ selectedAuditStatus
								+ "\n Bill Source:"
								+ searchdBillGenSource
								+ "\n Bill From Date:"
								+ selectedBillGenFromDate
								+ "\n Bill To Date:"
								+ selectedBillGenToDate
								+ "\n Meter Reader ID:"
								+ searchMeterRdrEmpId
								+ "\n selectedMtrRdrEmpId:"
								+ selectedMtrRdrEmpId);
				imageAuditSearchCriteria = new ImageAuditSearchCriteria();
				if (null != selectedZone && !"".equalsIgnoreCase(selectedZone)) {
					imageAuditSearchCriteria.setSearchZone(selectedZone);
				} else {
					imageAuditSearchCriteria.setSearchZone("");
				}
				if (null != selectedMRNo && !"".equalsIgnoreCase(selectedMRNo)) {
					imageAuditSearchCriteria.setSearchMRNo(selectedMRNo);
				} else {
					imageAuditSearchCriteria.setSearchMRNo("");
				}
				if (null != selectedArea && !"".equalsIgnoreCase(selectedArea)) {
					imageAuditSearchCriteria.setSearchArea(selectedArea);
				} else {
					imageAuditSearchCriteria.setSearchArea("");
				}
				if (null != kno && !"".equalsIgnoreCase(kno)) {
					imageAuditSearchCriteria.setSearchKno(kno);
				} else {
					imageAuditSearchCriteria.setSearchKno("");
				}
				if (null != searchBillRound
						&& !"".equalsIgnoreCase(searchBillRound)) {
					imageAuditSearchCriteria
							.setSearchBillRound(searchBillRound);
				} else {
					imageAuditSearchCriteria.setSearchBillRound("");
				}
				if (null != searchZROCode
						&& !"".equalsIgnoreCase(searchZROCode)) {
					imageAuditSearchCriteria.setSearchZROCode(searchZROCode);
					/*
					 * Added by Madhuri on 25th Jul 2017 as per Jtrac Id-
					 * Android-389
					 */
					this.selectedZROCode = searchZROCode;
				} else {
					imageAuditSearchCriteria.setSearchZROCode("");
				}
				if (null != selectedAuditStatus
						&& !"".equalsIgnoreCase(selectedAuditStatus)
						&& null != kno && !"".equalsIgnoreCase(kno)) {
					AppLog
							.info("For KNO Search, Setting selectedAuditStatus= ALL");
					imageAuditSearchCriteria.setSearchAuditStatus("ALL");
				} else {
					imageAuditSearchCriteria
							.setSearchAuditStatus(selectedAuditStatus);
				}
				if (null == this.pageNo || "".equals(this.pageNo)) {
					imageAuditSearchCriteria.setPageNo("1");
				} else {
					imageAuditSearchCriteria.setPageNo(pageNo);
				}
				if (null == this.maxRecordPerPage
						|| "".equals(this.maxRecordPerPage)) {
					maxRecordPerPage = null != PropertyUtil
							.getProperty("AUDIT_RECORDS_PER_PAGE") ? PropertyUtil
							.getProperty("AUDIT_RECORDS_PER_PAGE")
							: "5";
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				} else {
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				}
				/*
				 * Start :-Added by Madhuri on 31st Aug 2017 as per Jtrac Id-
				 * Android-388
				 */

				if (null != searchdBillGenSource
						&& !"".equalsIgnoreCase(searchdBillGenSource)) {
					imageAuditSearchCriteria
							.setSearchdBillGenSource(searchdBillGenSource);
				} else {
					imageAuditSearchCriteria.setSearchdBillGenSource("");
				}

				if (null != selectedBillGenToDate
						&& !"".equalsIgnoreCase(selectedBillGenToDate)) {
					imageAuditSearchCriteria
							.setSearchBillToDate(selectedBillGenToDate);

				} else {
					imageAuditSearchCriteria.setSearchBillToDate("");
				}
				if (null != selectedBillGenFromDate
						&& !"".equalsIgnoreCase(selectedBillGenFromDate)) {
					imageAuditSearchCriteria
							.setSearchBillFromDate(selectedBillGenFromDate);

				} else {
					imageAuditSearchCriteria.setSearchBillFromDate("");
				}
				if (null != searchMeterRdrEmpId
						&& !"".equalsIgnoreCase(searchMeterRdrEmpId)) {
					if (searchMeterRdrEmpId.contains("(")) {
						String tempId = searchMeterRdrEmpId.substring(
								searchMeterRdrEmpId.indexOf("(") + 1,
								searchMeterRdrEmpId.indexOf(")"));
						AppLog.info("tempId >>>" + tempId + "<<");
						imageAuditSearchCriteria.setsearchMeterRdrEmpId(tempId);
						this.selectedMtrRdrEmpId = searchMeterRdrEmpId;
						AppLog.info("searchMeterRdrEmpId >>>"
								+ searchMeterRdrEmpId + "<<");

					}
				}
				/*
				 * End :-Added by Madhuri on 31st Aug 2017 as per Jtrac Id-
				 * Android-388
				 */

				result = searchAuditRecords(imageAuditSearchCriteria);
				session.remove("SERVER_MESSAGE");

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
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the imageAuditSearchCriteria
	 */
	public ImageAuditSearchCriteria getImageAuditSearchCriteria() {
		return imageAuditSearchCriteria;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the maxRecordPerPage
	 */
	public String getMaxRecordPerPage() {
		return maxRecordPerPage;
	}

	/**
	 * @return the meterReadImgAuditDetailsList
	 */
	public List<MeterReadImgAuditDetails> getMeterReadImgAuditDetailsList() {
		return meterReadImgAuditDetailsList;
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
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the searchBillRound
	 */
	public String getSearchBillRound() {
		return searchBillRound;
	}

	/**
	 * @return the searchZROCode
	 */
	public String getSearchZROCode() {
		return searchZROCode;
	}

	/**
	 * @return the selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
	}

	/**
	 * @return the selectedAuditStatus
	 */
	public String getSelectedAuditStatus() {
		return selectedAuditStatus;
	}

	/**
	 * @return the selectedBillRound
	 */
	public String getSelectedBillRound() {
		return selectedBillRound;
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
	 * <p>
	 * Populate default values required for Meter Read Image Audit Screen.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "AUDIT");
			session.remove("SERVER_MESSAGE");
			session.remove("AJAX_MESSAGE");
			if (null == session.get("ZoneListMap")) {
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
			session.put("ZROListMap", GetterDAO.getZROListMap());
			/* Added By Madhuri on 1st Sept 2017 as per Jtrac Android-388 */
			if (null == session.get("meterReaderNameList")) {
				session.put("meterReaderNameList", MeterReadImgAuditDAO
						.getGetMeterReadName());

			}
			String srchStatusList = DJBConstants.SEARCH_AUDIT_STATUS;
			List<String> searchAuditStatusList = Arrays.asList(srchStatusList
					.split("\\s*,\\s*"));
			String updtStatusList = DJBConstants.UPDATE_STATUS_LIST;
			List<String> updtAuditStatusValues = Arrays.asList(updtStatusList
					.split("\\s*,\\s*"));
			Map<String, String> updtAuditStatusList = new LinkedHashMap<String, String>();
			for (String str : updtAuditStatusValues) {
				updtAuditStatusList.put(str, str);
			}
			if (null == session.get("searchAuditStatusList")) {
				session.put("searchAuditStatusList", searchAuditStatusList);
			}
			if (null == session.get("selectedBillRoundList")) {
				session.put("selectedBillRoundList", MeterReadImgAuditDAO
						.getPreviousBillRounds()); /*
													 * Modified by Madhuri On
													 * 25th July 2017 as per
													 * Jtrac Id- Android-389
													 */
			}
			if (null == session.get("updtAuditStatusList")) {
				session.put("updtAuditStatusList", updtAuditStatusList);
			}
			if (null == selectedAuditStatus) {
				selectedAuditStatus = DJBConstants.SEARCH_AUDIT_STATUS_DEFAULT;
			}
			/*
			 * Added by Madhuri On 31st Aug 2017 as per Jtrac Id- Android-388
			 */
			String srchBillGenSourceList = DJBConstants.BILL_GEN_SOURCE;
			List<String> searchBillGenSourceList = Arrays
					.asList(srchBillGenSourceList.split("\\s*,\\s*"));

			if (null == session.get("searchBillGenSourceList")) {
				session.put("searchBillGenSourceList", searchBillGenSourceList);
			}

		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to Search Records For Meter Read Image Audit.
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public String searchAuditRecords(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		String result = "success";
		try {
			if (null == this.pageNo || "".equals(this.pageNo)) {
				this.pageNo = "1";
				this.maxRecordPerPage = null != PropertyUtil
						.getProperty("AUDIT_RECORDS_PER_PAGE") ? PropertyUtil
						.getProperty("AUDIT_RECORDS_PER_PAGE") : "5";
			}
			this.totalRecords = MeterReadImgAuditDAO
					.getTotalCountOfAuditDetailsList(imageAuditSearchCriteria);
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
				this.meterReadImgAuditDetailsList = (ArrayList<MeterReadImgAuditDetails>) MeterReadImgAuditDAO
						.getConsumerDetailsListForImgAudit(imageAuditSearchCriteria);
				// AppLog.info("meterReadImgAuditDetailsList***** "+imageAuditSearchCriteria.getsearchMeterRdrEmpId());
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
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @param hidAction
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param imageAuditSearchCriteria
	 *            the imageAuditSearchCriteria to set
	 */
	public void setImageAuditSearchCriteria(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		this.imageAuditSearchCriteria = imageAuditSearchCriteria;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param kno
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param meterReadImgAuditDetailsList
	 *            the meterReadImgAuditDetailsList to set
	 */
	public void setMeterReadImgAuditDetailsList(
			List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList) {
		this.meterReadImgAuditDetailsList = meterReadImgAuditDetailsList;
	}

	/**
	 * @param pageNo
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
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param searchBillRound
	 */
	public void setSearchBillRound(String searchBillRound) {
		this.searchBillRound = searchBillRound;
	}

	/**
	 * @param searchZROCode
	 */
	public void setSearchZROCode(String searchZROCode) {
		this.searchZROCode = searchZROCode;
	}

	/**
	 * @param selectedArea
	 */
	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

	/**
	 * @param selectedAuditStatus
	 */
	public void setSelectedAuditStatus(String selectedAuditStatus) {
		this.selectedAuditStatus = selectedAuditStatus;
	}

	/**
	 * @param selectedBillRound
	 */
	public void setSelectedBillRound(String selectedBillRound) {
		this.selectedBillRound = selectedBillRound;
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

	/**
	 * @param selectedZROCode
	 *            the selectedZROCode to set
	 */
	public void setSelectedZROCode(String selectedZROCode) {
		this.selectedZROCode = selectedZROCode;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * <p>
	 * This method is used to update records for Meter Read Image Audit Screen
	 * </p>
	 * 
	 * @return
	 */
	public String updateImageAuditStatus() {
		String updateAuditResponse = "SUCCESS";
		int updatedRow = 0;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String input;
			List<MeterReadImgAuditDetails> meterReadImgAuditDetailsList = new ArrayList<MeterReadImgAuditDetails>();
			JSONObject jsonObj = new JSONObject(data);
			input = jsonObj.getString("JsonList");
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<MeterReadImgAuditDetails>>() {
			}.getType();
			ArrayList<MeterReadImgAuditDetails> auditList = gson.fromJson(
					input, collectionType);
			if (null != auditList && auditList.size() > 0) {
				for (MeterReadImgAuditDetails obj : auditList) {
					obj.setImgAuditBy(userId);
					meterReadImgAuditDetailsList.add(obj);
				}
			}
			if (null != meterReadImgAuditDetailsList
					&& meterReadImgAuditDetailsList.size() > 0) {
				for (MeterReadImgAuditDetails meterReadImgAuditDetails : meterReadImgAuditDetailsList) {
					AppLog.info("Updating Image Audit For Bill Round"
							+ meterReadImgAuditDetails.getBillRound());
					updatedRow += MeterReadImgAuditDAO
							.updateImageAuditStatus(meterReadImgAuditDetails);
				}

			}
			if (updatedRow > 0) {
				session.put("SERVER_MESSAGE", "<font color='#33CC33'><b>Total "
						+ updatedRow + " Record(s)"
						+ " Updated Successfully.</b></font>");
				updateAuditResponse = "Total " + updatedRow + " Record(s)"
						+ " Updated Successfully.";
			} else {
				updateAuditResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			}
			session.remove("AreaListMap");
			session.remove("MRNoListMap");
		} catch (Exception e) {
			updateAuditResponse = "ERROR:";
			AppLog.error(e);
		}
		AppLog.end();
		return updateAuditResponse;

	}

}
