/************************************************************************************************************
 * @(#) FreezeAction.java   18-Mar-2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.MRDContainer; //import com.tcs.djb.util.AppLog;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * This is an action class for freezing an MRD.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 18-03-2013 Matiur Rahman changed userName to userId.
 * 
 */
@SuppressWarnings("serial")
public class FreezeAction extends ActionSupport {
	private String freezeData = null;
	private String lastPage = null;
	private String selectedArea = null;
	private String selectedMRNo = null;
	private String selectedZone = null;
	private String startPage = null;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		AppLog.begin();
		try {
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			MRDReadTypeLookup mrdReadTypeLookup = (MRDReadTypeLookup) session
					.get("mrdReadTypeLookup");
			if (null == mrdReadTypeLookup) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.remove("MR_kno");
			session.remove("MR_mrKeyList");
			session.remove("MR_zoneMR");
			session.remove("MR_mrNoMR");
			session.remove("MR_areaMR");
			session.remove("MR_maxRecordPerPage");
			session.remove("MR_pageNo");
			session.remove("MR_searchFor");
			// String userRoleId = (String) session.get("userRoleId");
			// if (!"3".equals(userRoleId)) {
			// addActionError(getText("error.access.denied"));
			// session.remove("mrdContainer");
			// AppLog.end();
			// return "home";
			// }
			String searchZone = (String) session.get("searchZoneCode");
			String searchMRNo = (String) session.get("searchMRNo");
			String searchAreaCode = (String) session.get("searchAreaCode");
			String searchBillWindow = (String) session.get("searchBillWindow");
			String isFrozen = (String) session.get("isFrozen");
			if (null == isFrozen) {
				if ((null == this.freezeData || "".equals(this.freezeData))) {

					MRDContainer mrdContainer = new MRDContainer(searchZone,
							searchMRNo, searchAreaCode, searchBillWindow);
					mrdContainer.setLastUpdatedBy(userName);
					GetterDAO getterDAO = new GetterDAO();
					mrdContainer = (MRDContainer) getterDAO
							.getMRD(mrdContainer);
					session.remove("mrdContainer");
					session.remove("freezeSuccess");
					session.remove("isFrozen");
					session.remove("DataSaved");
					if (null != mrdContainer
							&& null != mrdContainer.getConsumerList()
							&& mrdContainer.getConsumerList().size() > 0) {
						session.put("mrdContainer", mrdContainer);
						String lastPageNo = PropertyUtil
								.getProperty("RECORDS_PER_PAGE");
						if (Integer.parseInt(lastPageNo) > mrdContainer
								.getConsumerList().size()) {
							lastPageNo = Integer.toString(mrdContainer
									.getConsumerList().size());
						}
					}
				} else {
					MRDContainer mrdContainer = (MRDContainer) session
							.get("mrdContainer");
					if (null != mrdContainer) {
						mrdContainer.setZone(searchZone);
						mrdContainer.setMrNo(searchMRNo);
						mrdContainer.setAreaNo(searchAreaCode);
						mrdContainer.setLastUpdatedBy(userName);
						if (mrdContainer.freezeMRD()) {
							mrdContainer = new MRDContainer(searchZone,
									searchMRNo, searchAreaCode,
									searchBillWindow);
							// mrdContainer.setLastUpdatedBy(userName);
							GetterDAO getterDAO = new GetterDAO();
							mrdContainer = (MRDContainer) getterDAO
									.getMRD(mrdContainer);
							session.remove("mrdContainer");
							session.remove("freezeSuccess");
							session.remove("isFrozen");
							if (null != mrdContainer
									&& null != mrdContainer.getConsumerList()
									&& mrdContainer.getConsumerList().size() > 0) {
								session.put("mrdContainer", mrdContainer);
								String lastPageNo = PropertyUtil
										.getProperty("RECORDS_PER_PAGE");
								if (Integer.parseInt(lastPageNo) > mrdContainer
										.getConsumerList().size()) {
									lastPageNo = Integer.toString(mrdContainer
											.getConsumerList().size());
								}
							}
							session.put("freezeSuccess",
									"The MRD Has been Frozen Successfully.");
							session.put("isFrozen", "Y");
							// } else {
							// session
							// .put("freezeSuccess",
							// "There was a problem while Freezing the MRD, Please Try again.");
							//
						}
					}
				}
			} else {
				session.put("isFrozen", "Y");
				session.put("freezeSuccess", "The MRD is Already Frozen.");
			}
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
			return "login";
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return freezeData
	 */
	public String getFreezeData() {
		return freezeData;
	}

	/**
	 * @return lastPage
	 */
	public String getLastPage() {
		return lastPage;
	}

	/**
	 * @return selectedArea
	 */
	public String getSelectedArea() {
		return selectedArea;
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
	 * @param freezeData
	 */
	public void setFreezeData(String freezeData) {
		this.freezeData = freezeData;
	}

	/**
	 * @param lastPage
	 */
	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @param selectedArea
	 */
	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
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
	 * @param startPage
	 */
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

}
