/************************************************************************************************************
 * @(#) MRDScheduleDownloadJobSubmitAction.java   26 Apr 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MRDScheduleDownloadDAO;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * Action class for MRD Schedule Download Job Submit screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 26-04-2013
 * @history : Madhuri on 06-06-2016 as per Jtrac -Djb:- 4464 & Open Project id
 *          1203 to match mrkey tagged with AMR user & The mrkey selected by
 *          User for new job submit.
 */

public class MRDScheduleDownloadJobSubmitAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	private String hidAction;
	private InputStream inputStream;
	private String page;
	private HttpServletResponse response;
	private String selectedArea;
	private String selectedBillWindow;
	private String selectedMRNo;
	private String selectedZone;

	/**
	 * <p>
	 * For all ajax call in meter replacement screen
	 * </p>
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
				addActionError(getText("error.login.expired"));
				inputStream = new StringBufferInputStream("ERROR:"
						+ getText("error.login.expired")
						+ ", Please re login  and try Again!");
				AppLog.end();
				return "login";
			}
			if ("populateMRNo".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(selectedZone);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"selectedMRNo\" id=\"selectedMRNo\" class=\"selectbox\" onfocus=\"fnCheckZone();\" onchange=\"fnGetArea();\">");
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
						.append("<select name=\"selectedArea\" id=\"selectedArea\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo();\" >");

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

			/**
			 * @Added by Madhuri as per Jtrac -Djb:- 4464 & Open Project id 1203 to match mrkey tagged with AMR user & The mrkey selected by User for new job submit
			 * @return
			 * @author 735689
			 * @since 03-06-2016
			 */

			if ("validateMrkey".equalsIgnoreCase(hidAction)) {
				
				Map<String, String> mrkyFromSession = (Map<String, String>) session.get("taggedMrkey");
				String validMrkey = MRDScheduleDownloadDAO.getMrdCode(
						selectedZone, selectedMRNo, selectedArea);
				
				AppLog.info("Mrkeys Tagged to Logged In users is/Are" +mrkyFromSession.size());
				
				try {
				if(null != validMrkey && null != mrkyFromSession && mrkyFromSession.containsValue(validMrkey)){
					inputStream = new StringBufferInputStream("validMrkey");
					
					
				}
				else{
					inputStream = new StringBufferInputStream("ERROR:You do not have security rights for this action. Please select the input fields correctly.");
					
					
				}
				}
				catch(Exception e){
					inputStream = new StringBufferInputStream("ERROR:");
					AppLog.error(e);
					
				}
				 /*@Ended by Madhuri as per Jtrac -Djb:- 4464 & Open Project id 1203 to match mrkey tagged with AMR user & The mrkey selected by User for new job submit */
			}

			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"ERROR:There was an error at Server end.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
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
			session.put("CURR_TAB", "MRD");
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
		} catch (Exception e) {
			AppLog.error(e);
		}
		return result;
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
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
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
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedArea
	 *            the selectedArea to set
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
	 *            the selectedMRNo to set
	 */
	public void setSelectedMRNo(String selectedMRNo) {
		this.selectedMRNo = selectedMRNo;
	}

	/**
	 * @param selectedZone
	 *            the selectedZone to set
	 */
	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
	}

	@Override
	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
