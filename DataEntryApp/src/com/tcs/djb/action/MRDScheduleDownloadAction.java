/************************************************************************************************************
 * @(#) MRDScheduleDownloadAction.java   11 Sep 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MRDScheduleDownloadDAO;
import com.tcs.djb.model.MRDScheduleDownloadDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * Action Class for Mrd Schedule Download Screen
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services) 11-09-2012
 * @since 11-09-2012
 * @history : Madhuri Singh (Tata Consultancy Services) on 14th-06-2016 made
 *          changes in mrd schedule download screen as per Jtrac -DJB:-4464 &
 *          Open Project Id -1208 to check user's role
 * 
 */
public class MRDScheduleDownloadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hidAction = null;
	// int i = 0;
	private final String MAX_MRD_SCHEDULE_SUBMISSION = PropertyUtil
			.getPropertyUtil().getProperty("MAX_MRD_SCHEDULE_SUBMISSION");
	private String page = null;
	private String selectedArea = null;
	private String selectedAreaDesc = null;
	private String selectedBillWindow = null;

	private String selectedMRNo = null;

	private String selectedZone = null;

	private String showAll = null;

	private String showCompleted = null;

	private String showInProgress = null;

	private String submittedBy = null;


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		AppLog.begin();
		try {
			// System.out.println("In Time::" + System.currentTimeMillis());
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("LAST_SUBMIT", "scheduleDownload");
			String userName = (String) session.get("userId");
			//Added by Madhuri on 15th Jun 2016
			String isAmrRole = DJBConstants.AMR_ROLE;
			String userRole = (String)session.get("userRoleId");
			//Ended by Madhuri on 15th Jun 2016
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "MRD");
			// System.out.println("selectedArea>>>" + selectedArea
			// + ">>selectedMRNo>>" + selectedMRNo + ">>selectedZone>>"
			// + selectedZone + ">>selectedBillWindow>>"
			// + selectedBillWindow);

			// System.out.println("hidAction:::" + hidAction + ":::page::" +
			// page
			// + "::submittedBy::" + submittedBy + "::showAll::" + showAll
			// + "::showCompleted::" + showCompleted
			// + "::showInProgress::" + showInProgress);
			String sessionID = ServletActionContext.getRequest().getSession()
					.getId();
			GetterDAO getterDAO = new GetterDAO();
			if ("Refresh".equals(hidAction)) {

				HashMap<String, Object> inputMap = new HashMap<String, Object>();
				//Added by Madhuri on 15th Jun 2016
				if (null != isAmrRole && null != userRole
						&& isAmrRole.equalsIgnoreCase(userRole)) {
					if ("ME".equalsIgnoreCase(submittedBy)) {
						inputMap.put("userID", userName);
					} 

				} else {
					if ("ME".equalsIgnoreCase(submittedBy)) {
						inputMap.put("userID", userName);
						this.showAll = "";
					} else {
						this.showAll = "Y";
					}
				}
				//Ended by Madhuri on 15th Jun 2016
				String status = "";
				if ("Y".equalsIgnoreCase(showCompleted)) {
					status = "Completed";
				}
				if ("Y".equalsIgnoreCase(showInProgress)) {
					status = "In Progress";
				}
				if ("Y".equalsIgnoreCase(showInProgress)
						&& "Y".equalsIgnoreCase(showCompleted)) {
					status = "In Progress','Completed";
				}
				inputMap.put("status", status);
				HashMap<String, Object> returnMap = (HashMap<String, Object>) GetterDAO
						.getMRDScheduleJobList(inputMap);

				ArrayList<MRDScheduleDownloadDetails> dataList = (ArrayList<MRDScheduleDownloadDetails>) returnMap
						.get("dataList");
				// System.out.println("dataList::::" + dataList.size());
				session.remove("MRDScheduleJobList");
				if (dataList.size() > 0) {
					session.put("MRDScheduleJobList", dataList);
					session.put("job_status", "");
				}

			} else if ("prepareDownload".equals(hidAction)) {

				// String searchZoneCode = "";
				// if (null != selectedZone && !"".equals(selectedZone)) {
				// // AppLog.info("inside search zone");
				// searchZoneCode = selectedZone.substring(selectedZone
				// .lastIndexOf("(") + 1, selectedZone
				// .lastIndexOf(")"));
				// }
				// String searchMRNo = "";
				// if (null != selectedMRNo && !"".equals(selectedMRNo)) {
				// // AppLog.info("inside search mr no");
				// searchMRNo = selectedMRNo.substring(0, selectedMRNo
				// .indexOf("-"));
				// }
				// // String searchArea = searcString.substring(0, searcString
				// // .indexOf("-"));
				// String searchAreaCode = "";
				// String searchArea = "";
				// String searcString = "";
				// if (null != selectedArea && !"".equals(selectedArea)) {
				// searcString = (this.selectedArea).substring(
				// (this.selectedArea).indexOf("-(") + 2,
				// (this.selectedArea).length() - 1);
				// searchAreaCode = searcString.substring(0, searcString
				// .indexOf("-"));
				// searchArea = (this.selectedArea).substring(0,
				// (this.selectedArea).indexOf("-("));
				// }
				// System.out.println("selectedZone>>>>" + selectedZone
				// + ">>>searchMRNo>>>>" + selectedMRNo
				// + ">>>searchAreaCode>>>>" +
				// selectedArea+">>selectedAreaDesc>>"+selectedAreaDesc
				// + ">>selectedBillWindow>>" + selectedBillWindow);
				if ((null == selectedZone || "".equalsIgnoreCase(selectedZone))
						&& (null == selectedBillWindow || ""
								.equalsIgnoreCase(selectedBillWindow))) {
					addActionError(getText("label.zone") + "and "
							+ getText("label.billwindow")
							+ " both are mandatory.");
					AppLog.end();
					return "mandatory";
				}
				if (null == selectedZone || "".equalsIgnoreCase(selectedZone)) {
					addActionError(getText("label.zone") + " is mandatory.");
					AppLog.end();
					return "mandatory";
				}
				if (null == selectedBillWindow
						|| "".equalsIgnoreCase(selectedBillWindow)) {
					AppLog.info("inside search billwndw");
					addActionError(getText("label.billwindow")
							+ " is mandatory.");
					AppLog.end();
					return "mandatory";
				}
				String searchCriteria = selectedZone;
				if (null != selectedMRNo && !"".equals(selectedMRNo)) {
					searchCriteria = searchCriteria + "-" + selectedMRNo;
				}
				if (null != selectedArea && !"".equals(selectedArea)) {
					searchCriteria = searchCriteria + "-" + selectedArea;
				}
				
				
				
				
				
				
				String searchCriteriaInSession = (String) session
						.get("searchCriteria");
				// System.out.println("sessipnID::" + sessionID);
				// session.put("searchCriteria", searchCriteria);
				if (!searchCriteria.equalsIgnoreCase(searchCriteriaInSession)) {

					int jobInProgress = getterDAO
							.getCountOfMRDScheduleJobListInProgress();
					int maxJob = 10;
					if (null != MAX_MRD_SCHEDULE_SUBMISSION
							&& !""
									.equalsIgnoreCase(MAX_MRD_SCHEDULE_SUBMISSION)) {
						maxJob = Integer.parseInt(MAX_MRD_SCHEDULE_SUBMISSION);
					}
					// System.out.println("jobInProgress::" + jobInProgress
					// + "::maxJob::" + maxJob);
					if (jobInProgress >= maxJob) {
						addActionError("Exceeded Maximum No of Job Submission. Please wait for sometime to complete..");
						HashMap<String, Object> inputMap = new HashMap<String, Object>();
						inputMap.put("status", "In Progress");
						HashMap<String, Object> returnMap = (HashMap<String, Object>) getterDAO
								.getMRDScheduleJobList(inputMap);
						ArrayList<MRDScheduleDownloadDetails> dataList = (ArrayList<MRDScheduleDownloadDetails>) returnMap
								.get("dataList");
						session.remove("MRDScheduleJobList");
						if (dataList.size() > 0) {
							session.put("MRDScheduleJobList", dataList);
							this.showAll = "Y";
							this.submittedBy = ""; 
							this.hidAction = "Refresh";
							this.showInProgress = "Y";
						}
						AppLog.end();
						return "error";
					}

					HashMap<String, Object> inputMap = new HashMap<String, Object>();
					inputMap.put("userID", userName);
					inputMap.put("searchCriteria", searchCriteria);
					// inputMap.put("status", "In Progress','Submitted");
					inputMap.put("zoneCode", selectedZone);
					inputMap.put("mrNo", selectedMRNo);
					inputMap.put("areaCode", selectedArea);
					inputMap.put("billWindow", selectedBillWindow);
					// inputMap.put("status", "In Progress");
					// inputMap.put("forToday", "Y");
					HashMap<String, Object> returnMap = (HashMap<String, Object>) getterDAO
							.checkDuplicateMRDScheduleJobList(inputMap);
					boolean isValid = (Boolean) returnMap.get("isValid");
					if (!isValid) {
						addActionError("This Job Already Submited.");
						ArrayList<MRDScheduleDownloadDetails> dataList = (ArrayList<MRDScheduleDownloadDetails>) returnMap
								.get("dataList");
						session.remove("MRDScheduleJobList");
						if (dataList.size() > 0) {
							session.put("MRDScheduleJobList", dataList);
						}
						this.hidAction = "Refresh";
						this.showAll = "Y";
						AppLog.end();
						return "success";
					}
					returnMap = (HashMap<String, Object>) getterDAO
							.checkSimilarMRDScheduleJobList(inputMap);
					isValid = (Boolean) returnMap.get("isValid");
					if (!isValid) {
						addActionError("A Similar Job is already Submited/In Progress, Please wait for completion of the Job.");
						ArrayList<MRDScheduleDownloadDetails> dataList = (ArrayList<MRDScheduleDownloadDetails>) returnMap
								.get("dataList");
						session.remove("MRDScheduleJobList");
						if (dataList.size() > 0) {
							session.put("MRDScheduleJobList", dataList);
						}
						this.submittedBy = "ME";
						this.hidAction = "Refresh";
						this.showInProgress = "Y";
						this.showAll = "";
						AppLog.end();
						return "success";
					}

					try {
						// Create a new directory with user ID
						boolean direcotyCreated = false;
						File userDirectory = new File(PropertyUtil
								.getPropertyUtil().getProperty(
										"UCMdocumentUpload")
								+ "/" + userName);
						if (!userDirectory.exists()) {
							direcotyCreated = userDirectory.mkdirs();
							if (!direcotyCreated) {
								// System.out
								// .println("Sorry could not create directory");
								AppLog.error(new Throwable(
										"UNABLE TO CREATE DIRECTORY FOR THE USER::"
												+ userName));
							}
						}
						if (userDirectory.exists()) {
							// System.out.println("userDirectory Exists");
							// System.out.println("New JOB SUBMITTED");
							MRDScheduleDownloadDAO mrdScheduleDownloadDAO = new MRDScheduleDownloadDAO(
									selectedZone, selectedMRNo, selectedArea,
									selectedBillWindow, userName, sessionID,
									searchCriteria);
							session
									.put("job_status",
											"New Job Submitted Successfully. Click Refresh to get Current Status.");
							this.submittedBy = "ME";
						} else {
							addActionError("There was a problem while creating Files");
							AppLog.end();
							return "mandatory";
						}
					} catch (Exception e) {
						addActionError("There was a problem while creating Files");
						AppLog.error(e);
						AppLog.end();
						return "mandatory";
					}

				} else {
					this.submittedBy = "ME";
					this.hidAction = "Refresh";
					session
							.put("job_status",
									"Job In Progress. Click Refresh to get Current Status.");
					addActionError("Job Already Submitted. Click Refresh to get Current Status.");
				}

				session.put("searchCriteria", searchCriteria);
				session.put("searchZoneCode", selectedZone);
				session.put("searchArea", selectedAreaDesc);
				session.put("searchZone", this.selectedZone);
				session.put("searchBillWindow", this.selectedBillWindow);
				session.put("searchMRNo", selectedMRNo);
				session.put("searchAreaCode", selectedArea);

			} else {
				this.submittedBy = "ME";
				HashMap<String, Object> inputMap = new HashMap<String, Object>();
				inputMap.put("userID", userName);
				// inputMap.put("sessionID", sessionID);
				HashMap<String, Object> returnMap = (HashMap<String, Object>) getterDAO
						.getMRDScheduleJobList(inputMap);
				ArrayList<MRDScheduleDownloadDetails> dataList = (ArrayList<MRDScheduleDownloadDetails>) returnMap
						.get("dataList");
				session.remove("MRDScheduleJobList");
				if (dataList.size() > 0) {
					session.put("MRDScheduleJobList", dataList);
					session.put("job_status", "");
				}
			}
			session.put("lastAction", hidAction);
			// System.out.println("Out Time::" + System.currentTimeMillis()
			// + "::lastAction::" + hidAction);

		} catch (ClassCastException e) {
			addActionError(getText("error.research"));
			AppLog.error(e);
			AppLog.end();
			return "login";
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			AppLog.end();
			return "login";
		}
		AppLog.end();
		return "success";
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the selectedArea
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
	 * @return the showAll
	 */
	public String getShowAll() {
		return showAll;
	}

	/**
	 * @return the showCompleted
	 */
	public String getShowCompleted() {
		return showCompleted;
	}

	/**
	 * @return the showInProgress
	 */
	public String getShowInProgress() {
		return showInProgress;
	}

	/**
	 * @return the submittedBy
	 */
	public String getSubmittedBy() {
		return submittedBy;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param page
	 */
	public void setPage(String page) {
		this.page = page;
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

	/**
	 * @param showAll
	 *            the showAll to set
	 */
	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	/**
	 * @param showCompleted
	 *            the showCompleted to set
	 */
	public void setShowCompleted(String showCompleted) {
		this.showCompleted = showCompleted;
	}

	/**
	 * @param showInProgress
	 *            the showInProgress to set
	 */
	public void setShowInProgress(String showInProgress) {
		this.showInProgress = showInProgress;
	}

	/**
	 * @param submittedBy
	 *            the submittedBy to set
	 */
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	
}
