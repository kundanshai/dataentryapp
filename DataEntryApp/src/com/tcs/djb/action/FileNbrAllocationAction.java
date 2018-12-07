/************************************************************************************************************
 * @(#) FileNbrAllocationAction.java   12-Apr-2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.FileNbrAllocationDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * This is an action Class for File Number Allocation Screen
 * This class contains all the action performed as per JTrac DJB-4442 and
 * OpenProject-CODE DEVELOPMENT #867
 * </p>
 * 
 * @author Lovely(Tata Consultancy Services)
 * @since 12-04-2016
 */

@SuppressWarnings( { "deprecation", "serial" })
public class FileNbrAllocationAction extends ActionSupport implements
		ServletResponseAware {
	private int count;
	/**
	 * Hidden action.
	 */
	private String hidAction;

	private InputStream inputStream;
	/**
	 * Maximum range.
	 */
	private String maxRange;

	/**
	 * Minimum Range .
	 */
	private String minRange;
	/**
	 * Zone location Drop down.
	 */
	private String selectedZROLocation;

	/**
	 * For all ajax call in File Number Allocation screen
	 * 
	 * @return string
	 * @author Lovely (Tata Consultancy Services)
	 * @since 12-04-2016
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session;
			String userId = null;
			if (null != ActionContext.getContext()
					&& !"".equalsIgnoreCase(ActionContext.getContext()
							.toString())
					&& null != ActionContext.getContext().getSession()
					&& !"".equalsIgnoreCase(ActionContext.getContext()
							.getSession().toString())) {
				session = ActionContext.getContext().getSession();
				userId = (String) session.get("userId");
				if (null == userId || "".equals(userId)) {
					inputStream = ScreenAccessValidator
							.ajaxResponse(getText("error.login.expired"));
					return "success";
				}
			}
			if (null != hidAction && !"".equalsIgnoreCase(hidAction)) {
				if ("rangeValidate".equalsIgnoreCase(hidAction)) {
					String rangeValidateResponse = validateBookletRange();
					inputStream = new StringBufferInputStream(
							rangeValidateResponse);
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream("");
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
		int noOfRecords = 0;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				return "login";
			}
			AppLog.info("Value of hidAction " + hidAction);
			if ("submit".equalsIgnoreCase(hidAction)) {
				if (null == selectedZROLocation
						|| "".equalsIgnoreCase(selectedZROLocation)) {
					addActionError(DJBConstants.ZRO_MESSAGE);
				} else {
					String rangeValidateResponse = validateBookletRange();
					inputStream = new StringBufferInputStream(
							rangeValidateResponse);
					if (!rangeValidateResponse.contains("ERROR")) {

						noOfRecords = FileNbrAllocationDAO.insertOnSubmit(
								selectedZROLocation, Long.parseLong(minRange),
								Long.parseLong(maxRange), userId);
						if (noOfRecords > 0) {
							addActionMessage(DJBConstants.DATA_SAVED_MESSAGE);
						} else {
							addActionError(DJBConstants.DATA_UNSUCCESS_MESSAGE);
						}
					}
				}
				AppLog.end();
				return SUCCESS;
			}
			/** Load Default Values. */
			loadDefault();
			if (null == hidAction) {
				session.remove("SERVER_MESSAGE");
				session.remove("AJAX_MESSAGE");
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
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return maxRange
	 */
	public String getMaxRange() {
		return maxRange;
	}

	/**
	 * @return minRange
	 */
	public String getMinRange() {
		return minRange;
	}

	/**
	 * @return selectedZROLocation
	 */
	public String getSelectedZROLocation() {
		return selectedZROLocation;
	}

	/**
	 * <p>
	 * This method is for loading the zro list for a particular User Id
	 * </p>
	 */
	private void loadDefault() {
		AppLog.begin();
		addActionError("");
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			String userZROlist = NewConnFileEntryDAO
					.getDeZroAccessForUserId(userId);
			session.put("CURR_TAB", "MRD");
			if (null == userZROlist) {
				session.put("ZROList", "");
			} else {
				session.put("ZROList", GetterDAO.getAllZRO());
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @param hidAction
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param maxRange
	 */
	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}

	/**
	 * @param minRange
	 */
	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}

	/**
	 * @param selectedZROLocation
	 */
	public void setSelectedZROLocation(String selectedZROLocation) {
		this.selectedZROLocation = selectedZROLocation;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {

	}

	/**
	 * <p>
	 * This method is to validate a file number whether it already assigned to some zone.
	 * </p>
	 * 
	 * @return
	 */
	private String validateBookletRange() {
		AppLog.begin();
		String result = "SUCCESS";
		try {
			if (Long.parseLong(minRange) > 0) {
				count = count + FileNbrAllocationDAO.checkFileNbr(minRange);
			} else if (Long.parseLong(maxRange) > 0) {
				count = count + FileNbrAllocationDAO.checkFileNbr(maxRange);
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.info(minRange + " " + maxRange + " " + count);
		if (count > 0) {
			result = "<font color='red' size='2'><span><b>ERROR:Sorry, These file numbers are already assigned to some zone.</b></font>";
		}
		AppLog.end();
		return result;
	}
}