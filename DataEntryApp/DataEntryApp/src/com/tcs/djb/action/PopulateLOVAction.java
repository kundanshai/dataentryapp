/************************************************************************************************************
 * @(#) PopulateLOVAction.java   23 Apr 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.LOV;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This is an action class to populate the LOV.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 23-04-2013
 * @history: On 22-JAN-2016 Rajib added lines to populate {@link #areaDetails},
 *           {@link #proofOfIdTypeListMap}, {@link #propertyOwnDocListMap},
 *           {@link #proofOfResListMap} as per JTrac DJB-4313
 * @history: On 13-FEB-2016 Rajib Hazarika Added drop down values as per JTrac
 *           DJB-4313 to populate session values
 * @history: On 14-JUN-2016 Madhuri Singh Added drop down values as per JTrac
 *           DJB-4464 & Open Project Id - 1208 to populate only open bill round id.
 */
@SuppressWarnings("deprecation")
public class PopulateLOVAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hidAction;
	private InputStream inputStream;
	private HttpServletResponse response;

	/**
	 * <p>
	 * For all ajax call in Data Entry Search screen
	 * </p>
	 * 
	 * @return String
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			Map<String, Object> applicationContext = ActionContext.getContext()
					.getApplication();
			if (null != applicationContext
					&& null == applicationContext.get("LOV")) {
				LOV lov = new LOV();
				applicationContext.put("LOV", lov);
				session.put("ZoneListMap", lov.getZoneListMap());
				session.put("MRNoListMap", new HashMap<String, String>());
				session.put("AreaListMap", new HashMap<String, String>());
				session.put("zoneList", lov.getZoneList());
				session.put("mrNoList", lov.getMrNoList());
				session.put("areaList", lov.getAreaList());
				session.put("billWindowList", lov.getBillWindowList());
				session
						.put("meterReadStatusList", lov
								.getMeterReadStatusList());
				session.put("mrdReadTypeLookup", lov.getMrdReadTypeLookup());
				session.put("waterConnectionSizeList", lov
						.getWaterConnectionSizeList());
				session.put("waterConnectionUseList", lov
						.getWaterConnectionUseList());
				session.put("saTypeList", lov.getSaTypeList());
				session.put("DJBMeterTypeList", lov.getDJBMeterTypeList());
				session.put("manufacturerDetailsList", lov
						.getManufacturerDetailsList());
				session.put("modelDetailsList", lov.getModelDetailsList());
				// START:Added by Rajib as per JTrac DJB-4313 on 22-JAN-2016
				session.put("areaDetails", lov.getAreaDetails());
				session.put("proofOfIdTypeListMap", lov
						.getProofOfIdTypeListMap());
				session.put("propertyOwnDocListMap", lov
						.getPropertyOwnDocListMap());
				session.put("proofOfResListMap", lov.getProofOfResListMap());
				// this.deZroCd=
				// NewConnFileEntryDAO.getDeZroAccessForUserId(userId);
				// END:Added by Rajib as per JTrac DJB-4313 on 22-JAN-2016
				// START: Added by Rajib as per JTrac DJB-4313 on 13-FEB-2016
				session
						.put("yesNoCharValListMap", lov
								.getYesNoCharValListMap());
				session.put("unAuthUsgCharValListMap", lov
						.getUnAuthUsgCharValListMap());
				session.put("prefModeOfPmntCharValListMap", lov
						.getPrefModeOfPmntCharValListMap());
				session.put("watFeasCharValListMap", lov
						.getWatFeasCharValListMap());
				session.put("sewFeasCharValListMap", lov
						.getSewFeasCharValListMap());
				session.put("isDocVerifiedCharValListMap", lov
						.getIsDocVerifiedCharValListMap());
				session.put("djbEmpCharValListMap", lov
						.getDjbEmpCharValListMap());
				session.put("wconSizeCharValListMap", lov
						.getWconSizeCharValListMap());
				session.put("occupSecurityCharValListMap", lov
						.getOccupSecurityCharValListMap());
				session.put("sewDevChrgApplCharValListMap", lov
						.getSewDevChrgApplCharValListMap());
				session.put("sewDevChrgColCharValListMap", lov
						.getSewDevChrgColCharValListMap());
				session.put("watDevChrgApplCharValListMap", lov
						.getWatDevChrgApplCharValListMap());
				session.put("watDevChrgColCharValListMap", lov
						.getWatDevChrgColCharValListMap());
				session.put("zroLocCharValListMap", lov
						.getZroLocCharValListMap());
				// END: Added by Rajib as per JTrac DJB-4313 on 13-FEB-2016
				// Added by Madhuri as per JTrac DJB-4464 on 14-JUN-2016
				session.put("openBillWindowList", lov.getOpenBillWindowList());
			}
			inputStream = new StringBufferInputStream("SUCCESS");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"There was an error at Server Side, Please Login again.......");
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
			String userRoleId = (String) session.get("userRoleId");
			if (null == userRoleId || "".equals(userRoleId)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "HOME");
			if ("refresh".equalsIgnoreCase(hidAction)) {
				Map<String, Object> applicationContext = ActionContext
						.getContext().getApplication();
				if (null != applicationContext) {
					applicationContext.remove("LOV");
				}
				result = "populatelov";
			}
		} catch (Exception e) {
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
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
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
}
