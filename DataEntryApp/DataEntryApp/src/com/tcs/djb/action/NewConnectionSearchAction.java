/************************************************************************************************************
 * @(#) NewConnectionSearchAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.NewConnectionDAO;
import com.tcs.djb.dao.NewConnectionSearchDAO;
import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for New Connection DAF Search
 * </p>
 * @author Rajib Hazarika (Tata Consultancy Services)
 */
@SuppressWarnings("deprecation")
public class NewConnectionSearchAction extends ActionSupport implements
		ServletResponseAware {
	private static final String savedNewConnectionDAFDetailsFlagConstant = "savedNewConnectionDAFDetailsFlag";
	private static final String SCREEN_ID = "36";
	private static final long serialVersionUID = 1L;

	private String hidAction;
	private String houseNumber;
	private InputStream inputStream;
	private String locality;
	private String location;
	private Map<String, String> locationListMap;
	private String pinCode;
	private HttpServletResponse response;
	private String roadNumber;
	private Map<String, String> zoneCodeListMap;
	private String zoneNo;

	/**
	 * <p>
	 * For all ajax call in New Connection DAF Search
	 * </p>
	 * 
	 * @return String
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			if (null == userIdSession || "".equals(userIdSession)) {
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
			if ("getSearchResult".equals(hidAction)) {
				session.remove("searcZone");
				session.remove("searcLocation");
				session.put("searcZone", zoneNo);
				session.put("searcLocation", location);
				inputStream = new StringBufferInputStream(getSearchResult());
			}
			// System.out.println("hidAction::" + hidAction);
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>");
			// e.printStackTrace();
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
		String result = "error";
		try {
			// System.out.println("hidAction::" + hidAction);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				session.remove(savedNewConnectionDAFDetailsFlagConstant);
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			if (null == hidAction || "".equals(hidAction)) {
				loadInitValues();
				// session.remove(savedNewConnectionDAFDetailsFlagConstant);
				result = "success";
			}
			if ("back".equalsIgnoreCase(hidAction)) {
				loadInitValues();
				zoneNo = (String) (null == session.get("searcZone") ? ""
						: session.get("searcZone"));
				location = (String) (null == session.get("searcLocation") ? ""
						: session.get("searcLocation"));
			} else {
				session.remove("searcZone");
				session.remove("searcLocation");
			}

		} catch (Exception e) {
			loadInitValues();
			addActionError("There was a problem while Processing your Request. Please Try Again.");
			AppLog.error(e);
		}
		AppLog.end();
		return result;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */

	/**
	 * @return the locationListMap
	 */
	public Map<String, String> getLocationListMap() {
		return locationListMap;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @return the roadNumber
	 */
	public String getRoadNumber() {
		return roadNumber;
	}

	/**
	 * <p>
	 * This method is used to get search results
	 * </p>
	 * 
	 * @return String
	 */

	public String getSearchResult() {
		StringBuffer htmlCodeSB = new StringBuffer();
		try {
			// htmlCodeSB.append("<div class='foreign-content-div'>");
			htmlCodeSB.append("<fieldset><legend>Search Result</legend><br/>");
			List<NewConnectionDAFDetailsContainer> searchResultDetailsList = NewConnectionSearchDAO
					.getSearchResultDetails(zoneNo, location);
			NewConnectionDAFDetailsContainer searchResultDetails = null;
			int i = 0;
			if (null != searchResultDetailsList
					&& searchResultDetailsList.size() > 0) {
				htmlCodeSB
						.append("<table class='table-grid' id='searchResultTable' width='98%'>");
				htmlCodeSB.append("	<tr>");
				htmlCodeSB.append("<th align='center'>Name</th>");
				htmlCodeSB.append("<th align='center'>House No.</th>");
				htmlCodeSB.append("<th align='center'>Road No.</th>");
				htmlCodeSB.append("<th align='center'>Locality</th>");
				htmlCodeSB.append("<th align='center'>Sub Locality</th>");
				htmlCodeSB.append("<th align='center'>PIN Code</th>");
				htmlCodeSB.append("<th align='center'>Application Status</th>");
				htmlCodeSB.append("<th align='center'>To be Processed</th>");
				htmlCodeSB.append("<th align='center'>&nbsp;</th>");
				htmlCodeSB.append("	</tr>");
				for (; i < searchResultDetailsList.size(); i++) {
					searchResultDetails = searchResultDetailsList.get(i);
					htmlCodeSB.append("	<tr onMouseOver=\"this.bgColor='yellow';\"	onMouseOut=\"this.bgColor='white';\">");
					htmlCodeSB.append("<td align='center'>"
							+ (null == searchResultDetails.getEntityName() ? ""
									: searchResultDetails.getEntityName())

							+ " </td>");
					htmlCodeSB
							.append("<td align='center'>"
									+ (null == searchResultDetails
											.getHouseNumber() ? ""
											: searchResultDetails
													.getHouseNumber())
									+ "</td>");
					htmlCodeSB.append("<td align='center'>"
							+ (null == searchResultDetails.getRoadNumber() ? ""
									: searchResultDetails.getRoadNumber())

							+ "</td>");
					htmlCodeSB.append("<td align='center'>"
							+ (null == searchResultDetails.getLocality() ? ""
									: searchResultDetails.getLocality())
							+ "</td>");
					htmlCodeSB
							.append("<td align='center'>"
									+ (null == searchResultDetails
											.getSubLocality() ? ""
											: searchResultDetails
													.getSubLocality())
									+ "</td>");
					htmlCodeSB.append("<td align='center'>"
							+ (null == searchResultDetails.getPinCode() ? ""
									: searchResultDetails.getPinCode())

							+ "</td>");
					htmlCodeSB.append("<td align='center'>" + "E" + "</td>");
					htmlCodeSB
							.append("<td align='center'><img src=\"/DataEntryApp/images/valid.gif\" title='Ready to be Processed.' height='20px'/></td>");

					htmlCodeSB.append("<td align='center'>"
							+ "<a href=\"#\" onclick=\"fnGetDetails('"
							+ searchResultDetails.getSequenceNo()
							+ "');\">Details</a>" + "");
					htmlCodeSB.append("<input type='hidden' name='seqNo" + i
							+ "' id='seqNo" + i + "' value='"
							+ searchResultDetails.getSequenceNo() + "'/>");

					htmlCodeSB.append("</td>");
					htmlCodeSB.append("	</tr>");
				}
			} else {
				htmlCodeSB
						.append("<table width='98%'>");
				htmlCodeSB.append("	<tr >");
				htmlCodeSB
						.append("<td align='center'><font color=\"red\"> No records Found to Display for the Seach Criteria.</font></td>");
				htmlCodeSB.append("	</tr>");
			}
			htmlCodeSB.append("</table><br/>");
			htmlCodeSB.append("</fieldset>");

		} catch (Exception e) {
			AppLog.error(e);
		}
		return htmlCodeSB.toString();
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}

	/**
	 * @return the zoneCodeListMap
	 */
	public Map<String, String> getZoneCodeListMap() {
		return zoneCodeListMap;
	}

	/**
	 * @return the zoneNo
	 */
	public String getZoneNo() {
		return zoneNo;
	}

	/**
	 * <p>
	 * This method is used to load all drop down values
	 * </p>
	 */
	public void loadInitValues() {
		AppLog.begin();
		// System.out.println("Loding Initial values ");
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			NewConnectionSearchDAO newConnectionSearchDAO = new NewConnectionSearchDAO();
			String userId = (String) session.get("userId");
			String zroLocation = NewConnectionDAO
					.getZROLocationByUserID(userId);
			if (null != zroLocation && !"ABSENT".equals(zroLocation)) {
				this.zoneCodeListMap = NewConnectionDAO
						.getZoneByZROLocation(zroLocation);
				session.remove("zroLocation");
				session.put("zroLocation", zroLocation);
			} else {
				session.put("zroLocation", "ABSENT");
				this.zoneCodeListMap = new HashMap<String, String>();
				session.put("zoneCodeListMap", zoneCodeListMap);
			}

			if (null != zroLocation && !"ABSENT".equals(zroLocation)) {
				this.locationListMap = newConnectionSearchDAO
						.getLocationListMap(zroLocation);
			} else {
				session.put("zroLocation", "ABSENT");
				this.locationListMap = new HashMap<String, String>();
				session.put("locationListMap", locationListMap);
			}

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param houseNumber
	 *            the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param locality
	 *            the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param locationListMap
	 */
	public void setLocationListMap(Map<String, String> locationListMap) {
		this.locationListMap = locationListMap;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @param roadNumber
	 *            the roadNumber to set
	 */
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param zoneCodeListMap
	 *            the zoneCodeListMap to set
	 */
	public void setZoneCodeListMap(Map<String, String> zoneCodeListMap) {
		this.zoneCodeListMap = zoneCodeListMap;
	}

	/**
	 * @param zoneNo
	 *            the zoneNo to set
	 */
	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

}
