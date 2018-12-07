/************************************************************************************************************
 * @(#) FinalmSevaAuditAction.java   07-Mar-2016
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
import org.codehaus.jettison.json.JSONObject;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.FinalmSevaAuditDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.ImageAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.services.SelfBillingDisableService;
import com.tcs.djb.services.SendSMSService;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is an action class for Consumer Variation Audit Screen
 * </p>
 * 
 * @author 830700
 * @since 07-03-2016 as per jTrac ANDROID-293
 * 
 */
@SuppressWarnings("deprecation")
public class FinalmSevaAuditAction extends ActionSupport implements
		ServletResponseAware {

	private static final String SCREEN_ID = "45";
	List<MeterReadImgAuditDetails> auditActionRecordsList;
	private String data;
	private String hidAction;
	ImageAuditSearchCriteria imageAuditSearchCriteria;
	private InputStream inputStream;
	private String maxRecordPerPage;
	private String pageNo;
	List<String> pageNoDropdownList;
	private HttpServletResponse response;

	private String searchZROCode;
	private int totalRecords;
	private ArrayList<String> userZroCdList;

	/**
	 * method for all ajax call
	 * 
	 * @return
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
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}
			if ("saveFinalAuditAction".equalsIgnoreCase(hidAction)) {
				String saveFinalAuditActionResponse = saveFinalAuditAction();
				inputStream = new StringBufferInputStream(
						saveFinalAuditActionResponse);

			}

		} catch (Exception e) {
			setInputStream(new StringBufferInputStream(
					"<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>"));
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
			}else{
				userZroCdList = FinalmSevaAuditDAO.getLoggedInUserZroList(userId);
			}
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				AppLog.end();
				return "login";
			}
			/** Load Default Values. */
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
				session.remove("AJAX_MESSAGE");
				AppLog
						.info("\n####################hidAction value is null and populating the record List####################");
				imageAuditSearchCriteria = new ImageAuditSearchCriteria();

				if (null != userId && !"".equalsIgnoreCase(userId)) {
					imageAuditSearchCriteria.setLoggedInUserId(userId);
				} else {
					imageAuditSearchCriteria.setLoggedInUserId("");
				}
				if (null == this.pageNo || "".equals(this.pageNo)) {
					imageAuditSearchCriteria.setPageNo("1");
				} else {
					imageAuditSearchCriteria.setPageNo(pageNo);
				}
				if (null == this.maxRecordPerPage
						|| "".equals(this.maxRecordPerPage)) {
					maxRecordPerPage = null != PropertyUtil
							.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE") ? PropertyUtil
							.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE")
							: "20";
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				} else {
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				}
				/*Setting Logged In User ZRO Code*/
				if (null != userZroCdList && !userZroCdList.isEmpty()) {
					if(userZroCdList.contains(DJBConstants.BYPASS_ZRO_CODE_FOR_AUDIT_ACTION)){
						AppLog.info("\n##############Current Logged In User Has Access of All the ZRO Location");
					}else{
						AppLog.info("\n##################Current Logged In User Has Access of ::"+userZroCdList.toString()+"ZRO Location(s)");
						imageAuditSearchCriteria.setLoggedInUserZroCdList(userZroCdList);
					}
				}
				result = searchAuditActionRecords(imageAuditSearchCriteria);
				loadDefault();
			}
			if ("search".equalsIgnoreCase(hidAction)
					|| "Next".equalsIgnoreCase(hidAction)
					|| "Prev".equalsIgnoreCase(hidAction)
					|| "refresh".equalsIgnoreCase(hidAction)) {

				imageAuditSearchCriteria = new ImageAuditSearchCriteria();

				if (null != userId && !"".equalsIgnoreCase(userId)) {
					imageAuditSearchCriteria.setLoggedInUserId(userId);
				} else {
					imageAuditSearchCriteria.setLoggedInUserId("");
				}
				if (null == this.pageNo || "".equals(this.pageNo)) {
					imageAuditSearchCriteria.setPageNo("1");
				} else {
					imageAuditSearchCriteria.setPageNo(pageNo);
				}
				if (null == this.maxRecordPerPage
						|| "".equals(this.maxRecordPerPage)) {
					maxRecordPerPage = null != PropertyUtil
							.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE") ? PropertyUtil
							.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE")
							: "20";
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				} else {
					imageAuditSearchCriteria.setRecordPerPage(maxRecordPerPage);
				}
				result = searchAuditActionRecords(imageAuditSearchCriteria);
				session.remove("SERVER_MESSAGE");
				session.remove("AJAX_MESSAGE");

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
	 * @return the auditActionRecordsList
	 */
	public List<MeterReadImgAuditDetails> getAuditActionRecordsList() {
		return auditActionRecordsList;
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
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the searchZROCode
	 */
	public String getSearchZROCode() {
		return searchZROCode;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * <p>
	 * This method is used to set Current Tab as AUDIT and ZROListMap as ZRO
	 * list obtained from database in session object
	 * </p>
	 */
	private void loadDefault() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("CURR_TAB", "AUDIT");
			session.put("ZROListMap", GetterDAO.getZROListMap());
			String finalActions = DJBConstants.FINAL_AUDIT_ACTION;
			List<String> finalAuditActionList = Arrays.asList(finalActions
					.split("\\s*,\\s*"));
			if (null == session.get("finalAuditActionList")) {
				session.put("finalAuditActionList", finalAuditActionList);
			}

		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to save Audit Action
	 * </p>
	 * 
	 * @return
	 */
	private String saveFinalAuditAction() {
		String saveAuditActionResponse = "SUCCESS";
		int updatedRow = 0;
		boolean selfBillDisabled = false;
		boolean warningMsgSent = false;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String authCookie = "";
			if (null != session.get("CCB_CRED")) {
				authCookie = (String) session.get("CCB_CRED");
			} else {
				authCookie = DJBConstants.CCB_CONNECT_DEFAULT_AUTHCOOKIE;
			}
			String input;
			JSONObject jsonObj = new JSONObject(data);
			input = jsonObj.getString("JsonList");
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<MeterReadImgAuditDetails>>() {
			}.getType();
			ArrayList<MeterReadImgAuditDetails> auditList = gson.fromJson(
					input, collectionType);
			AppLog
					.info("\n##############Audit Action List is size ############:: "
							+ auditList.size());
			if (null != auditList && auditList.size() > 0) {
				for (MeterReadImgAuditDetails obj : auditList) {
					obj.setFinalAuditBy(userId);
					obj.setLastUpdatedBy(userId);
					obj
							.setFinalAuditStatus(DJBConstants.KNO_AUDIT_COMPLETE_STATUS);
					String consumerMobileNumber = "0000000000";
					consumerMobileNumber = FinalmSevaAuditDAO
							.getConsumerMobileNumber(obj.getKno());

					AppLog.info("\n KNO: " + obj.getKno() + "\n BillRound: "
							+ obj.getBillRound() + "\n FinalAuditBy: "
							+ obj.getFinalAuditBy() + "\n LastUpdatedBy: "
							+ obj.getLastUpdatedBy());
					AppLog
							.info("\n##################Consumer Mobile Number is ::"
									+ consumerMobileNumber);
					if (DJBConstants.DISABLE_SELF_BILLING_REASON
							.equalsIgnoreCase(obj.getFinalAuditAction().trim())) {
						String xaiResponse = new SelfBillingDisableService()
								.disableSelfBilling(null, obj.getKno(),
										authCookie,
										DJBConstants.CCB_SERVICE_PROVIDER_URL);
						if (null != xaiResponse
								&& DJBConstants.STATUS_SUCCESS_CODE
										.equalsIgnoreCase(xaiResponse)) {
							AppLog
									.info("\n###############Self Disabled Service Response#######"
											+ xaiResponse);
							String smsXaiResponse = new SendSMSService()
									.send(
											null,
											obj.getKno(),
											consumerMobileNumber,
											DJBConstants.DISABLE_SELF_BILLING_MESSAGE_TEXT,
											authCookie,
											DJBConstants.CCB_SERVICE_PROVIDER_URL);
							updatedRow += FinalmSevaAuditDAO
									.saveFinalAuditAction(obj);
							selfBillDisabled = true;
							if (null != smsXaiResponse
									&& DJBConstants.SMS_SENT_SUCCESS_RESPONSE
											.equalsIgnoreCase(smsXaiResponse)) {
								AppLog
										.info("\n###############SMS Sent to User#######"
												+ smsXaiResponse);

							}

						}
					} else if (DJBConstants.WARNING_ALERT_MESSAGE
							.equalsIgnoreCase(obj.getFinalAuditAction().trim())) {

						String smsXaiResponse = new SendSMSService().send(null,
								obj.getKno(), consumerMobileNumber,
								DJBConstants.WARNING_ALERT_MESSAGE_TEXT,
								authCookie,
								DJBConstants.CCB_SERVICE_PROVIDER_URL);
						if (null != smsXaiResponse
								&& DJBConstants.SMS_SENT_SUCCESS_RESPONSE
										.equalsIgnoreCase(smsXaiResponse)) {
							AppLog
									.info("\n###############SMS Sent to User#######"
											+ smsXaiResponse);
							updatedRow += FinalmSevaAuditDAO
									.saveFinalAuditAction(obj);
							warningMsgSent = true;
						}

					} else if (DJBConstants.CANCEL_BILL_MESSAGE
							.equalsIgnoreCase(obj.getFinalAuditAction().trim())) {
						/* Bill Cancel Service here */
						/*
						 * String smsXaiResponse = new
						 * SendSMSService().send(null,obj.getKno(),
						 * consumerMobileNumber,
						 * DJBConstants.CANCEL_BILL_MESSAGE_TEXT,authCookie,
						 * DJBConstants.CCB_SERVICE_PROVIDER_URL); if (null !=
						 * smsXaiResponse &&
						 * "SUCCESS".equalsIgnoreCase(smsXaiResponse)) {
						 * AppLog.info
						 * ("\n###############SMS Sent to User#######"+
						 * smsXaiResponse); updatedRow +=
						 * FinalmSevaAuditDAO.saveFinalAuditAction(obj); }
						 */
						updatedRow += FinalmSevaAuditDAO
								.saveFinalAuditAction(obj);
					} else {
						updatedRow += FinalmSevaAuditDAO
								.saveFinalAuditAction(obj);
					}
				}
			}

			if (updatedRow > 0) {
				session
						.put("SERVER_MESSAGE",
								"<font color='#33CC33'><b>Audit Action Saved Successfully</b></font>");
				saveAuditActionResponse = "<font color='#33CC33'><b>Audit Action Saved Successfully</b></font>";
			} else {
				saveAuditActionResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			}
		} catch (Exception e) {
			saveAuditActionResponse = "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		return saveAuditActionResponse;
	}

	/**
	 * <p>
	 * This method is used to search Audit Action Records
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	private String searchAuditActionRecords(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if (null == this.pageNo || "".equals(this.pageNo)) {
				this.pageNo = "1";
				this.maxRecordPerPage = null != PropertyUtil
						.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE") ? PropertyUtil
						.getProperty("AUDIT_ACTION_RECORDS_PER_PAGE")
						: "20";
			}
			this.totalRecords = FinalmSevaAuditDAO
					.getTotalCountOfAuditActionRecords(imageAuditSearchCriteria);
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
				this.auditActionRecordsList = (ArrayList<MeterReadImgAuditDetails>) FinalmSevaAuditDAO
						.getRecordListForAuditAction(imageAuditSearchCriteria);
				/*
				 * this.auditActionRecordsList =
				 * (ArrayList<MeterReadImgAuditDetails>) MeterReadImgAuditDAO
				 * .getConsumerDetailsListForImgAudit(imageAuditSearchCriteria);
				 */
				result = "success";
			}else{
				session.put("SERVER_MESSAGE","<font color='#33CC33'><b>After Audit of Last Bill Round No KNO With Non-Satisfactory Reason Has Been Found for Your ZRO Location.</b></font>");
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
	 * @param auditActionRecordsList
	 *            the auditActionRecordsList to set
	 */
	public void setAuditActionRecordsList(
			List<MeterReadImgAuditDetails> auditActionRecordsList) {
		this.auditActionRecordsList = auditActionRecordsList;
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
	 *            the hidAction to set
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
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param searchZROCode
	 *            the searchZROCode to set
	 */
	public void setSearchZROCode(String searchZROCode) {
		this.searchZROCode = searchZROCode;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

}
