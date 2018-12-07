/************************************************************************************************************
 * @(#) HHDMaintenanceAction.java   23-Jul-2013
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

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.HHDMaintenanceDAO;
import com.tcs.djb.model.HHDMappingDetails;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This class contains all the functionality required for Hand Held Device(HHD)
 * Maintenance.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 23-07-2013 Matiur Rahman Added hhdId as new search criteria.
 * 
 */
@SuppressWarnings("deprecation")
public class HHDMaintenanceAction extends ActionSupport implements
		ServletResponseAware {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	private static final String SCREEN_ID = "15";
	private static final String selectTagEnd = "</select>";
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 9169433976728316650L;
	private Map<String, String> billRoundListMap;
	private String hhdId;
	List<HHDMappingDetails> hhdMappingDetailsList;
	private String hidAction;
	private InputStream inputStream;

	private String maxRecordPerPage;

	List<String> maxRecordPerPageDropdownList;
	private String mrKeyList;
	private String pageNo;
	List<String> pageNoDropdownList;
	private String previousHHDId;

	private String previousMeterReader;
	private HttpServletResponse response;
	private String status;

	private int totalRecords;

	private String toupdateHHDId;

	private String toupdateMeterReader;

	private String toupdateMRKey;

	private String toupdateZone;

	private String zoneCode;

	/**
	 * method for all ajax call
	 * 
	 * @return
	 */
	public String ajaxMethod() {
		AppLog.begin();
		// System.out.println("hidAction::" + hidAction + "::zoneCode::"
		// + zoneCode);
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
			if ("populateMeterReaderList".equalsIgnoreCase(hidAction)) {
				List<MeterReaderDetails> meterReaderList = (ArrayList<MeterReaderDetails>) HHDMaintenanceDAO
						.getMeterReaderList(zoneCode);
				MeterReaderDetails meterReaderDetails = null;
				StringBuffer meterReaderDropdown = new StringBuffer();
				meterReaderDropdown
						.append("<select name=\"meterReaderNamePopUp\" id=\"meterReaderNamePopUp\" class=\"selectbox-long\" onchange=\"resetMRMsg(this);\">");
				meterReaderDropdown
						.append("<option value=''>Please Select</option>");
				if (null != meterReaderList && meterReaderList.size() > 0) {
					for (int i = 0; i < meterReaderList.size(); i++) {
						meterReaderDetails = (MeterReaderDetails) meterReaderList
								.get(i);
						meterReaderDropdown.append(optionTagBeginPart1);
						meterReaderDropdown.append(meterReaderDetails
								.getMeterReaderID());
						meterReaderDropdown.append(optionTagBeginPart2);
						meterReaderDropdown.append(meterReaderDetails
								.getMeterReaderName());
						meterReaderDropdown.append(optionTagEnd);
					}
				}
				meterReaderDropdown.append(selectTagEnd);
				inputStream = (new StringBufferInputStream(meterReaderDropdown
						.toString()));
			}
			if ("updateMeterReaderMappingDetails".equalsIgnoreCase(hidAction)) {
				int recordUpdated = HHDMaintenanceDAO
						.updateHHDMeterReaderMappingDetails(toupdateMRKey,
								toupdateMeterReader, toupdateHHDId,
								previousMeterReader, previousHHDId, userId);
				if (recordUpdated > 0) {
					session
							.put("SERVER_MESSAGE",
									"<font color='#33CC33'><b>Last Record was Successfully Updated.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
				} else if (null != toupdateHHDId
						&& !"".equalsIgnoreCase(toupdateHHDId.trim())
						&& null != toupdateMeterReader
						&& !"".equalsIgnoreCase(toupdateMeterReader.trim())) {
					session.put("SERVER_MESSAGE", "");
					inputStream = (new StringBufferInputStream(
							"<font color='red'><b>Last Record could not be Updated.</b></font>"));
				} else {
					session
							.put(
									"SERVER_MESSAGE",
									"<font color='red'><b>The Mapping of the HHD ID and Meter Reader is Removed.</b></font>");
					inputStream = (new StringBufferInputStream("SUCCESS"));
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

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		AppLog.begin();
		String result = "error";
		try {
			// System.out.println("hidAction::" + hidAction);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userName");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "HHD");
			if (null == session.get("ZoneListMap")
					|| ((HashMap<String, String>) session.get("ZoneListMap"))
							.isEmpty()) {
				session.put("ZoneListMap", GetterDAO.getZoneListMap());
				// System.out.println("ZoneListMap Populated");
			}
			if (null == session.get("HHDListMap")
					|| ((HashMap<String, String>) session.get("HHDListMap"))
							.isEmpty()) {
				session.put("HHDListMap", HHDMaintenanceDAO.getHHDListMap());
				// System.out.println("ZoneListMap Populated");
			}
			if (null == session.get("AllMeterReaderListMap")
					|| ((HashMap<String, String>) session
							.get("AllMeterReaderListMap")).isEmpty()) {
				session.put("AllMeterReaderListMap", GetterDAO
						.getAllMeterReaderListMap());
				// System.out.println("AllMeterReaderListMap Populated");
			}
			if (null == hidAction || "".equalsIgnoreCase(hidAction)
					|| "search".equalsIgnoreCase(hidAction)
					|| "searchAfterSave".equalsIgnoreCase(hidAction)) {
				if (!"searchAfterSave".equalsIgnoreCase(hidAction)) {
					session.put("SERVER_MESSAGE", "");
				} else {
					if ("Inactive".equalsIgnoreCase(status)) {
						this.status = "Active";
					}
				}
				this.hidAction = "search";
				if (null == this.pageNo || "".equals(this.pageNo)) {
					this.pageNo = "1";
					this.maxRecordPerPage = PropertyUtil
							.getProperty("RECORDS_PER_PAGE");
				}
				if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
					String mrKeyArray[] = mrKeyList.split(",");
					String tempMRKeyList = "";
					for (int i = 0; i < mrKeyArray.length; i++) {
						String tempString = mrKeyArray[i];
						if (null != tempString
								&& !"".equalsIgnoreCase(tempString.trim())
								&& UtilityForAll.isNumeric(tempString.trim())) {
							tempMRKeyList += "," + tempString;
						}
					}
					if (tempMRKeyList.indexOf(',') == 0) {
						tempMRKeyList = tempMRKeyList.substring(1);
					}
					mrKeyList = tempMRKeyList;
				}
				if (null != mrKeyList && !"".equals(mrKeyList)) {
					this.status = "All";
				}
				// System.out.println("::search::pageNo::" + pageNo
				// + "::maxRecordPerPage::" + maxRecordPerPage
				// + "::status::" + status + "::zoneCode::" + zoneCode);
				this.totalRecords = HHDMaintenanceDAO
						.getTotalCountOfHHDMappingRecords(status, zoneCode,
								hhdId, mrKeyList);
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
					this.hhdMappingDetailsList = (ArrayList<HHDMappingDetails>) HHDMaintenanceDAO
							.getHHDMappingDetailsList(status, zoneCode, hhdId,
									mrKeyList, pageNo, maxRecordPerPage);
					// System.out.println("hhdMappingDetailsList Size::"
					// + hhdMappingDetailsList.size());
					result = "success";
				}
			}
			// if ("Next".equalsIgnoreCase(hidAction)) {
			// System.out.println("::NEXT::pageNo::" + pageNo
			// + "::maxRecordPerPage::" + maxRecordPerPage);
			// this.pageNo = Integer
			// .toString(Integer.parseInt(this.pageNo) + 1);
			// System.out.println("::NEXT::pageNo::" + pageNo
			// + "::maxRecordPerPage::" + maxRecordPerPage);
			// this.hhdMappingDetailsList = (ArrayList<HHDMappingDetails>)
			// HHDMaintenanceDAO
			// .getHHDMappingDetailsList(billRound, subZoneCode,
			// pageNo, maxRecordPerPage);
			// List<String> pageNoList = new ArrayList<String>();
			// List<String> maxPageNoList = new ArrayList<String>();
			// maxPageNoList.add("10");
			// maxPageNoList.add("20");
			// for (int i = 0, j = 1; i < totalRecords; i++) {
			// if (i % Integer.parseInt(maxRecordPerPage) == 0) {
			// pageNoList.add(Integer.toString(j++));
			// if (i != 0 && i <= 200) {
			// maxPageNoList.add(Integer.toString(i));
			// }
			// }
			// }
			// this.pageNoDropdownList = pageNoList;
			// this.maxRecordPerPageDropdownList = maxPageNoList;
			// result = "success";
			// }
			// if ("Previous".equalsIgnoreCase(hidAction)) {
			// System.out.println("::NEXT::pageNo::" + pageNo
			// + "::maxRecordPerPage::" + maxRecordPerPage);
			// this.pageNo = Integer
			// .toString(Integer.parseInt(this.pageNo) - 1);
			// this.hhdMappingDetailsList = (ArrayList<HHDMappingDetails>)
			// HHDMaintenanceDAO
			// .getHHDMappingDetailsList(billRound, subZoneCode,
			// pageNo, maxRecordPerPage);
			// List<String> pageNoList = new ArrayList<String>();
			// List<String> maxPageNoList = new ArrayList<String>();
			// maxPageNoList.add("10");
			// maxPageNoList.add("20");
			// for (int i = 0, j = 1; i < totalRecords; i++) {
			// if (i % Integer.parseInt(maxRecordPerPage) == 0) {
			// pageNoList.add(Integer.toString(j++));
			// if (i != 0 && i <= 200) {
			// maxPageNoList.add(Integer.toString(i));
			// }
			// }
			// }
			// this.pageNoDropdownList = pageNoList;
			// this.maxRecordPerPageDropdownList = maxPageNoList;
			// result = "success";
			// }
			// if ("gotoPageNo".equalsIgnoreCase(hidAction)) {
			// System.out.println("::NEXT::pageNo::" + pageNo
			// + "::maxRecordPerPage::" + maxRecordPerPage);
			// this.hhdMappingDetailsList = (ArrayList<HHDMappingDetails>)
			// HHDMaintenanceDAO
			// .getHHDMappingDetailsList(billRound, subZoneCode,
			// pageNo, maxRecordPerPage);
			// List<String> pageNoList = new ArrayList<String>();
			// List<String> maxPageNoList = new ArrayList<String>();
			// maxPageNoList.add("10");
			// maxPageNoList.add("20");
			// for (int i = 0, j = 1; i < totalRecords; i++) {
			// if (i % Integer.parseInt(maxRecordPerPage) == 0) {
			// pageNoList.add(Integer.toString(j++));
			// if (i != 0 && i <= 200) {
			// maxPageNoList.add(Integer.toString(i));
			// }
			// }
			// }
			// this.pageNoDropdownList = pageNoList;
			// this.maxRecordPerPageDropdownList = maxPageNoList;
			// result = "success";
			// }
		} catch (Exception e) {
			addActionError("There was a problem while Processing your Request. Please Try Again.");
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the billRoundListMap
	 */
	public Map<String, String> getBillRoundListMap() {
		return billRoundListMap;
	}

	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @return the hhdMappingDetailsList
	 */
	public List<HHDMappingDetails> getHhdMappingDetailsList() {
		return hhdMappingDetailsList;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
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
	 * @return the maxRecordPerPageDropdownList
	 */
	public List<String> getMaxRecordPerPageDropdownList() {
		return maxRecordPerPageDropdownList;
	}

	/**
	 * @return the mrKeyList
	 */
	public String getMrKeyList() {
		return mrKeyList;
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
	 * @return the previousHHDId
	 */
	public String getPreviousHHDId() {
		return previousHHDId;
	}

	/**
	 * @return the previousMeterReader
	 */
	public String getPreviousMeterReader() {
		return previousMeterReader;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return the toupdateHHDId
	 */
	public String getToupdateHHDId() {
		return toupdateHHDId;
	}

	/**
	 * @return the toupdateMeterReader
	 */
	public String getToupdateMeterReader() {
		return toupdateMeterReader;
	}

	/**
	 * @return the toupdateMRKey
	 */
	public String getToupdateMRKey() {
		return toupdateMRKey;
	}

	/**
	 * @return the toupdateZone
	 */
	public String getToupdateZone() {
		return toupdateZone;
	}

	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * @param billRoundListMap
	 *            the billRoundListMap to set
	 */
	public void setBillRoundListMap(Map<String, String> billRoundListMap) {
		this.billRoundListMap = billRoundListMap;
	}

	/**
	 * @param hhdId
	 *            the hhdId to set
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @param hhdMappingDetailsList
	 *            the hhdMappingDetailsList to set
	 */
	public void setHhdMappingDetailsList(
			List<HHDMappingDetails> hhdMappingDetailsList) {
		this.hhdMappingDetailsList = hhdMappingDetailsList;
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
	 * @param maxRecordPerPage
	 *            the maxRecordPerPage to set
	 */
	public void setMaxRecordPerPage(String maxRecordPerPage) {
		this.maxRecordPerPage = maxRecordPerPage;
	}

	/**
	 * @param maxRecordPerPageDropdownList
	 *            the maxRecordPerPageDropdownList to set
	 */
	public void setMaxRecordPerPageDropdownList(
			List<String> maxRecordPerPageDropdownList) {
		this.maxRecordPerPageDropdownList = maxRecordPerPageDropdownList;
	}

	/**
	 * @param mrKeyList
	 *            the mrKeyList to set
	 */
	public void setMrKeyList(String mrKeyList) {
		this.mrKeyList = mrKeyList;
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
	 * @param previousHHDId
	 *            the previousHHDId to set
	 */
	public void setPreviousHHDId(String previousHHDId) {
		this.previousHHDId = previousHHDId;
	}

	/**
	 * @param previousMeterReader
	 *            the previousMeterReader to set
	 */
	public void setPreviousMeterReader(String previousMeterReader) {
		this.previousMeterReader = previousMeterReader;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param toupdateHHDId
	 *            the toupdateHHDId to set
	 */
	public void setToupdateHHDId(String toupdateHHDId) {
		this.toupdateHHDId = toupdateHHDId;
	}

	/**
	 * @param toupdateMeterReader
	 *            the toupdateMeterReader to set
	 */
	public void setToupdateMeterReader(String toupdateMeterReader) {
		this.toupdateMeterReader = toupdateMeterReader;
	}

	/**
	 * @param toupdateMRKey
	 *            the toupdateMRKey to set
	 */
	public void setToupdateMRKey(String toupdateMRKey) {
		this.toupdateMRKey = toupdateMRKey;
	}

	/**
	 * @param toupdateZone
	 *            the toupdateZone to set
	 */
	public void setToupdateZone(String toupdateZone) {
		this.toupdateZone = toupdateZone;
	}

	/**
	 * @param zoneCode
	 *            the zoneCode to set
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

}
