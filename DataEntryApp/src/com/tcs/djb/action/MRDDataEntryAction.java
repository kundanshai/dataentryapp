/************************************************************************************************************
 * @(#) MRDDataEntryAction.java   18 Dec 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This is an action class for saving meter read by separating saving and
 * flagging methods
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 18-12-2012 Matiur Rahman changed the logic for saving meter read by
 *          separating saving and flagging methods
 */
public class MRDDataEntryAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MRDContainer mrdContainer = new MRDContainer(null, null, null);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");
		if (null == userId || "".equals(userId)) {
			addActionError(getText("error.login.expired"));
			AppLog.end();
			return "login";
		}
		HashMap<String, ConsumerDetail> toUpdateMap = (HashMap<String, ConsumerDetail>) session
				.get("toUpdateMap");
		if (null != toUpdateMap && !toUpdateMap.isEmpty()) {
			SetterDAO setterDAO = new SetterDAO();
			/**
			 * 8-12-2012 Matiur Rahman changed the logic for saving meter read
			 * by separating saving and flagging methods.
			 */
			// HashMap<String, String> returnMap = (HashMap<String, String>)
			// setterDAO
			// .saveMeterRead(toUpdateMap);
			// String recordUpdated = (String) returnMap.get("recordUpdated");
			/**
			 * 8-12-2012 Matiur Rahman added the logic for saving meter read by
			 * separating saving and flagging methods.
			 */
			ConsumerDetail consumerDetail = (ConsumerDetail) toUpdateMap
					.get("consumerDetail");
			HashMap<String, String> returnMap = null;
			if (null == consumerDetail.getConsumerStatus()
					|| "".equals(consumerDetail.getConsumerStatus())
					|| "60".equals(consumerDetail.getConsumerStatus())) {
				returnMap = (HashMap<String, String>) setterDAO
						.saveMeterRead(toUpdateMap);
			} else {
				returnMap = (HashMap<String, String>) setterDAO
						.flagConsumerStatus(toUpdateMap);
			}
			String recordUpdated = null;
			if (null != returnMap) {
				recordUpdated = (String) returnMap.get("recordUpdated");
			}
			if (null != recordUpdated && !"".endsWith(recordUpdated)) {
				addActionError(recordUpdated + " Records Updated Successfully.");
				// System.out.println(recordUpdated
				// + " Records Updated Successfully.");
				AppLog.end();
				return "error";
			} else {
				addActionError("No Records Updated !");
				// System.out.println("No Records Updated !");
				AppLog.end();
				return "error";
			}
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the mrdContainer
	 */
	public MRDContainer getMrdContainer() {
		return mrdContainer;
	}

	/**
	 * @param mrdContainer
	 */
	public void setMrdContainer(MRDContainer mrdContainer) {
		this.mrdContainer = mrdContainer;
	}

}
