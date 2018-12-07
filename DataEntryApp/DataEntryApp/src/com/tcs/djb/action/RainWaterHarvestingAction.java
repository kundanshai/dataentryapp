/************************************************************************************************************
 * @(#) RainWaterHarvestingAction.java   09 Oct 2014
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.LoginDAO;
import com.tcs.djb.dao.RainWaterHarvestingDAO;
import com.tcs.djb.services.KNOValidationService;
import com.tcs.djb.services.PremiseCharUpdateService;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;
import com.tcs.djb.util.UcmDocUploadUtil;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Rain Water Harvesting Screen.
 * </p>
 * 
 * @author Aniket Chatterjee (Tata Consultancy Services)
 * 
 * @history: Rajib Hazarika Added password variable to store the password
 *           entered by the user on Re-Confirm password Screen as per Jtrac
 *           DJB-4037
 * @history: Lovely edited the code as per Jtrac DJB-4561 and OpenProject-CODE
 *           DEVELOPMENT #1489.
 */
@SuppressWarnings("deprecation")
public class RainWaterHarvestingAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Variable to store approved by details of the Tagging Details
	 */
	private String approvedBy;
	/**
	 * Variable to store comments input from approver
	 */
	private String comments;
	// private String currentRainwaterHarvestingFlagInSystem;
	private String currentRWHF;
	private String currentWWHF;
	/**
	 * Variable to store Customer Class of the kno
	 */
	String custClassCode = "";

	private String diaryDate;

	private String diaryNo;

	// private String docNo;
	private String documentNo;

	private File documentOfProof;

	private String documentOfProofContentType;

	private String documentOfProofFileName;

	private String documentProofFilePath;
	/**
	 * Hidden action.
	 */
	private String hidAction;

	/**
	 * InputStream used for AJax call.
	 */
	private InputStream inputStream;

	private String isRainWaterHarvestInstalled;

	private String isWasteWaterTreatmentInstalled;
	private String kno;

	// private String nameOfConsumer;
	private String nameOfConsumer;

	/**
	 * Variable to store password entered by user on Re-Confirm Passsword Screen
	 * 
	 */
	private String password;

	private String photocopy;

	private HttpServletResponse response;

	// private String rainWaterHarvestInstallDate;
	private String rwhImpdate;

	/**
	 * Variable to store UCM path
	 */
	String ucmPath = "";

	/**
	 * Variable to store UCM path for Rain Water
	 */
	String ucmPathRwh = "";

	/**
	 * Variable to store UCM path for Waste Water
	 */
	String ucmPathWwt = "";

	private File wasteWaterdocumentOfProof;

	private String wwhImpdate;

	/**
	 * <p>
	 * For all ajax call in Rain Water Harvesting Screen.
	 * </p>
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String ajaxMethod() {
		AppLog.begin();
		try {
			// System.out.println(">>>>>>>>>>ajaxMethod>>>>>>>>>>");
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			AppLog.info("Inside Action Class:" + "::userId:" + userId);
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				inputStream = new StringBufferInputStream("ERROR:"
						+ getText("error.login.expired")
						+ ", Please re login  and try Again!");
				AppLog.end();
				return "login";
			}
			// System.out.println("hidAction>>>>" + hidAction + "::userId"
			// + userId);
			// session.remove("DataSaved");
			if ("checkKNO".equalsIgnoreCase(hidAction)) {
				// System.out.println("kno>>>>" + kno);
				int chkAccess = 0;
				Map<String, String> knoDetails = null;
				StringBuffer responseSB = new StringBuffer();
				if (null != kno && !"".equalsIgnoreCase(kno)) {
					// Calling KNO validation Service for validating KNO
					String isValidKNO = KNOValidationService.validateKNO(kno);
					// System.out.println("isValid::" + isValidKNO);
					AppLog.info("response::" + isValidKNO);
					if ("VALID".equalsIgnoreCase(isValidKNO)) {

						chkAccess = RainWaterHarvestingDAO.getAccesCount(kno,
								userId.trim());
						// System.out.println(">>>chkAccess" + chkAccess);
						AppLog.info("chkAccess::" + chkAccess);
						if (chkAccess > 0) {
							knoDetails = RainWaterHarvestingDAO
									.getKnoDetails(kno);
							if (null != knoDetails) {
								// System.out.println(">>>knoDetails is not null");
								AppLog.info(">>>knoDetails is not null");
								responseSB.append("<CONSNAME>"
										+ knoDetails.get("CONSNAME")
										+ "</CONSNAME>");
								responseSB.append("<RWH_STATUS>"
										+ knoDetails.get("RWH_STATUS")
										+ "</RWH_STATUS>");
								responseSB.append("<WWT_STATUS>"
										+ knoDetails.get("WWT_STATUS")
										+ "</WWT_STATUS>");
							} else {
								// System.out.println(">>>knoDetails is null");
								AppLog.info(">>>knoDetails is not null");
								responseSB
										.append("<ERROR> There is some problem in fetching KNO details, please contact System Administrator</ERROR>");
							}
						} else {
							// System.out.println(">>>kno not Valid");
							AppLog.info(">>>kno not Valid");
							responseSB
									.append("<ERROR> You do not have sufficient priviledge to the KNO </ERROR>");
							responseSB.append("<KNOSTATUS>VALID</KNOSTATUS>");
						}
					} else {
						if ("INVALID".equalsIgnoreCase(isValidKNO)) {
							AppLog.info("<ERROR> KNO is not Valid </ERROR>");
							responseSB
									.append("<ERROR> KNO is not Valid </ERROR>");
						} else {
							AppLog
									.info(">><ERROR> There is something unexpected happens during request processing, Please Try Again  </ERROR>>>");
							responseSB
									.append("<ERROR> There is something unexpected happens during request processing, Please Try Again  </ERROR>");
						}
					}
				}
				inputStream = new StringBufferInputStream(responseSB.toString());
				AppLog.end();
				return "success";
			} else if ("custClass".equalsIgnoreCase(hidAction)) {
				// System.out.println(">>inside custClass hidAction");
				AppLog.info(">>inside custClass hidAction");
				// StringBuffer responseSB = new StringBuffer();
				// System.out.println("kno>>>>" + kno);
				// String custClassCode = null;
				String custClassCode1 = null;
				custClassCode = RainWaterHarvestingDAO.getCustomerClass(kno);
				// System.out.println("custClassCode>>>>" + custClassCode);
				AppLog.info("custClassCode::" + custClassCode);
				custClassCode1 = DJBConstants.CUST_CL_CD_FOR_WWT;
				// System.out.println("custClassCode1>>>>" + custClassCode1);
				// Start : Commented by Lovely on 12-09-2016 as per jTrac-
				// DJB-4561
				/*
				 * if (DJBConstants.CUST_CL_CD_FOR_WWT
				 * .equalsIgnoreCase(custClassCode)) { custClassCode =
				 * "CAT IIA"; } else { custClassCode = "Others"; }
				 */

				inputStream = new StringBufferInputStream(custClassCode);
				AppLog.end();
				return "success";
			}
			// End : Commented by Lovely on 12-09-2016 as per jTrac- DJB-4561
			else {
				// START: Added by Rajib as per JTrac DJB-4037 on 20-OCT-2015
				if ("reConfirmPassword".equalsIgnoreCase(hidAction)) {
					AppLog.info(">>inside reConfirmpassword hidAction>>");
					if (null != password && !"".equalsIgnoreCase(password)) {
						String userName = (String) session.get("userId");
						String userRole = (String) session.get("userType");
						StringBuffer responseSB = new StringBuffer();
						HashMap<String, String> inputMap = new HashMap<String, String>();
						HashMap<String, String> returnMap = null;
						inputMap.put("userId", userName);
						inputMap.put("password", password);
						inputMap.put("userRole", userRole);
						AppLog.info("userName>>" + userName
								+ ">>password >> not null>>userRole>>"
								+ userRole);
						returnMap = (HashMap<String, String>) LoginDAO
								.validateLogin(inputMap);
						if (null != returnMap) {
							String status = (String) returnMap.get("status");
							if (null != status
									&& "Success".equalsIgnoreCase(status)) {
								AppLog.info(">>status>>" + status);
								responseSB.append("SUCCESS");
								inputStream = new StringBufferInputStream(
										responseSB.toString());
								AppLog.end();
								return "success";

							} else {
								responseSB.append("INVALID");
								inputStream = new StringBufferInputStream(
										responseSB.toString());
								AppLog.end();
								return "success";
							}
						}
					} else {
						inputStream = new StringBufferInputStream("INVALID");
						AppLog.end();
						return "success";
					}
				}
				// END: Added by Rajib as per JTrac DJB-4037 on 20-OCT-2015
				inputStream = new StringBufferInputStream("INVALID");
				AppLog.end();
				return "success";
			}
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"ERROR:Rain Water Harvesting Details for " + kno
							+ " Could not be Saved Successfully.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);
		}
		// if ("custClass".equalsIgnoreCase(hidAction)){
		// System.out.println(">>inside custClass hidAction");
		// //StringBuffer responseSB = new StringBuffer();
		// System.out.println("kno>>>>" + kno);
		// String custClassCode = null;
		// custClassCode = RainWaterHarvestingDAO.getCustomerClass(kno);
		// System.out.println("custClassCode>>>>" + custClassCode);
		// if(custClassCode == DJBConstants.CUST_CL_CD_FOR_WWT){
		// custClassCode = "IIA";
		// }
		// else{
		// custClassCode = "Others";
		// }
		// inputStream = new StringBufferInputStream(custClassCode);
		// AppLog.end();
		// return "success";
		//		
		// }
		// System.out.println("<<<<<<<<<<<ajaxMethod<<<<<<<<<<<");
		// System.out.println("inputStream:3:" + inputStream.toString());
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		try {
			String userName = null;
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			if (null != session) {
				userName = (String) session.get("userId");
				if (null == userName || "".equals(userName)) {
					addActionError(getText("error.login.expired"));
					AppLog.end();
					return "login";
				}
			}
			session.remove("SERVER_MSG");
			// System.out.println(">>>>>>>>>>executeMethod>>>>>>>>>>" +
			// hidAction
			// + ">>kno>>" + kno);
			AppLog.info(">>>>>>>>>>executeMethod>>>>>>>>>>" + hidAction
					+ ">>kno>>" + kno);
			// System.out.println(">>kothaiache>>" + documentProofFilePath);
			// System.out.println(">>Chole Aye Bhai>>" + custClassCode);
			if ("processRequest".equalsIgnoreCase(hidAction)) {
				session.put("isPopUp", false); // Added by Rajib for popup of
				// Re-Confirm Password Page
				int rowsInserted = 0;
				rowsInserted = RainWaterHarvestingDAO.insertPremCharDetails(
						kno, isRainWaterHarvestInstalled, rwhImpdate,
						isWasteWaterTreatmentInstalled, wwhImpdate, documentNo,
						diaryNo, userName, userName,
						DJBConstants.INITIAL_STATUS_FLG_RWH, diaryDate);
				AppLog.info("rowsInserted" + rowsInserted);
				if (rowsInserted > 0) {
					ucmUpload("photocopy_of_rain_water_harvesting_",
							documentOfProof, documentOfProofFileName,
							documentProofFilePath, documentNo,
							documentOfProofContentType,
							"Photocopy of Rain Water Harvesting",
							DJBConstants.DOC_TYPE_RWH);
					ucmPathRwh = ucmPath;
					// System.out.println(">>ucmPathRwh>>" + ucmPathRwh);
					AppLog.info("ucmPathRwh::" + ucmPathRwh);

					// Start : Commented by Lovely on 12-09-2016 as per Jtrac
					// DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
					/*
					 * if (DJBConstants.CUST_CL_CD_FOR_WWT
					 * .equalsIgnoreCase(RainWaterHarvestingDAO
					 * .getCustomerClass(kno))) {
					 */
					if (null != isWasteWaterTreatmentInstalled
							&& !""
									.equals(isWasteWaterTreatmentInstalled
											.trim())) {
						AppLog
								.info("**********isWasteWaterTreatmentInstalled value*****"
										+ isWasteWaterTreatmentInstalled);
						// System.out.println("calling UCM For 2nd time");
						AppLog.info("calling UCM For 2nd time");
						ucmUpload("photocopy_of_waste_water_treatment_",
								wasteWaterdocumentOfProof,
								documentOfProofFileName, documentProofFilePath,
								diaryNo, documentOfProofContentType,
								"Photocopy of Waste Water Treatment",
								DJBConstants.DOC_TYPE_RWH);
						ucmPathWwt = ucmPath;
						// }
					}
					// End : Commented by Lovely on 12-09-2016 as per Jtrac
					// DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
					// ucmPathWwt = ucmPath;
					// System.out.println(">>ucmPathWwt>>" + ucmPathWwt);
					AppLog.info("ucmPathWwt::" + ucmPathWwt);
					// System.out.println("inside process request");

					// System.out.println(">>kno>>" + kno);
					AppLog.info("kno::" + kno);
					// System.out.println(">>name>>" + nameOfConsumer);
					// System.out.println(">>Date>>" + rwhImpdate);
					AppLog.info("RWHDate::" + rwhImpdate);
					// System.out.println(">>UcmPath>>" + ucmPath);

					// System.out.println(">>approvedBy>>" + approvedBy);
					AppLog.info("approvedBy::" + approvedBy);
					// System.out.println(">>isRainWaterHarvestInstalled>>"
					// + isRainWaterHarvestInstalled);
					AppLog.info("isRainWaterHarvestInstalled::"
							+ isRainWaterHarvestInstalled);
					// System.out.println(">>isWasteWaterTreatmentInstalled>>"
					// + isWasteWaterTreatmentInstalled);
					AppLog.info("isWasteWaterTreatmentInstalled::"
							+ isWasteWaterTreatmentInstalled);
					// System.out.println(">>Waste Date>>" + wwhImpdate);
					AppLog.info("Waste Date::" + wwhImpdate);
					// System.out.println(">>Diary No>>" + diaryNo);
					AppLog.info("Diary No::" + diaryNo);
					AppLog.info("Document No::" + documentNo);
					String premiseId = null;
					// System.out.println(">>premiseId>>" + premiseId);
					premiseId = RainWaterHarvestingDAO.getPremiseId(kno);
					// System.out.println(">>premiseId>>" + premiseId);
					AppLog.info("premiseId::" + premiseId);
					String authCookie = null;
					if (null != session && null != session.get("CCB_CRED")) {
						authCookie = (String) session.get("CCB_CRED");
					} else {
						addActionError(getText("error.access.denied"));
						inputStream = ScreenAccessValidator
								.ajaxResponse(getText("error.access.denied"));
						AppLog.end();
						return "success";
					}
					if (DJBConstants.DYNAMIC_AUTH_COOKIE_FOR_RWH
							.equalsIgnoreCase(DJBConstants.FLAG_Y)) {
						authCookie = null;
						// If CCB service call willnot be invoked from logged in
						// user id, then it will be envoked using WEB user
						// credentials
					}
					String status = PremiseCharUpdateService
							.updatePremiseCharacteristics(premiseId,
									isRainWaterHarvestInstalled, userName,
									ucmPathRwh, rwhImpdate,
									isWasteWaterTreatmentInstalled, ucmPathWwt,
									wwhImpdate, authCookie);
					// System.out.println("status" + status);
					if ("SUCCESS".equals(status)) {
						session.put("SERVER_MSG", "Successfully Saved");
						int rowsUpdated = 0;
						rowsUpdated = RainWaterHarvestingDAO
								.updatePremCharStatus(
										DJBConstants.SUCCESS_STATUS_FLG_RWH,
										userName, kno,
										DJBConstants.INITIAL_STATUS_FLG_RWH);
						session.put("SERVER_MSG", "Details Saved Successfully");

					} else {
						session.put("SERVER_MSG",
								"Could not Saved Successfully");
					}
				} else {
					// can't insert Prem Char details into staging table
					session.put("SERVER_MSG", "Could not Saved Successfully");
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			ucmPath = null;
			ucmPathWwt = null;
			custClassCode = null;
			comments = null;
			approvedBy = null;
			currentRWHF = null;
			documentNo = null;
			diaryNo = null;
			wasteWaterdocumentOfProof = null;
			documentOfProof = null;
			documentOfProofContentType = null;
			documentOfProofFileName = null;
			documentProofFilePath = null;
			hidAction = null;
			isRainWaterHarvestInstalled = null;
			inputStream = null;
			isWasteWaterTreatmentInstalled = null;
			kno = null;
			currentWWHF = null;
			wwhImpdate = null;
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @return the currentRWHF
	 */
	public String getCurrentRWHF() {
		return currentRWHF;
	}

	/**
	 * @return the currentWWHF
	 */
	public String getCurrentWWHF() {
		return currentWWHF;
	}

	/**
	 * @return the diaryDate
	 */
	public String getDiaryDate() {
		return diaryDate;
	}

	/**
	 * @return the diaryNo
	 */
	public String getDiaryNo() {
		return diaryNo;
	}

	/**
	 * @return the documentNo
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * @return the documentOfProof
	 */
	public File getDocumentOfProof() {
		return documentOfProof;
	}

	/**
	 * @return the documentOfProofContentType
	 */
	public String getDocumentOfProofContentType() {
		return documentOfProofContentType;
	}

	/**
	 * @return the documentOfProofFileName
	 */
	public String getDocumentOfProofFileName() {
		return documentOfProofFileName;
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
	 * @return the isRainWaterHarvestInstalled
	 */
	public String getIsRainWaterHarvestInstalled() {
		return isRainWaterHarvestInstalled;
	}

	/**
	 * @return the isWasteWaterTreatmentInstalled
	 */
	public String getIsWasteWaterTreatmentInstalled() {
		return isWasteWaterTreatmentInstalled;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the nameOfConsumer
	 */
	public String getNameOfConsumer() {
		return nameOfConsumer;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the photocopy
	 */
	public String getPhotocopy() {
		return photocopy;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the rwhImpdate
	 */
	public String getRwhImpdate() {
		return rwhImpdate;
	}

	/**
	 * @return the wasteWaterdocumentOfProof
	 */
	public File getWasteWaterdocumentOfProof() {
		return wasteWaterdocumentOfProof;
	}

	/**
	 * @return the wwhImpdate
	 */
	public String getWwhImpdate() {
		return wwhImpdate;
	}

	/**
	 * @param approvedBy
	 *            the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param currentRWHF
	 *            the currentRWHF to set
	 */
	public void setCurrentRWHF(String currentRWHF) {
		this.currentRWHF = currentRWHF;
	}

	/**
	 * @param currentWWHF
	 *            the currentWWHF to set
	 */
	public void setCurrentWWHF(String currentWWHF) {
		this.currentWWHF = currentWWHF;
	}

	/**
	 * @param diaryDate
	 *            the diaryDate to set
	 */
	public void setDiaryDate(String diaryDate) {
		this.diaryDate = diaryDate;
	}

	/**
	 * @param diaryNo
	 *            the diaryNo to set
	 */
	public void setDiaryNo(String diaryNo) {
		this.diaryNo = diaryNo;
	}

	/**
	 * @param documentNo
	 *            the documentNo to set
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	// /**
	// * @return the commentsOfuser
	// */
	// public String getCommentsOfuser() {
	// return commentsOfuser;
	// }

	// /**
	// * @return the currentRainwaterHarvestingFlagInSystem
	// */
	// public String getCurrentRainwaterHarvestingFlagInSystem() {
	// return currentRainwaterHarvestingFlagInSystem;
	// }

	// /**
	// * @return the docNo
	// */
	// public String getDocNo() {
	// return docNo;
	// }
	/**
	 * @param documentOfProof
	 *            the documentOfProof to set
	 */
	public void setDocumentOfProof(File documentOfProof) {
		this.documentOfProof = documentOfProof;
	}

	/*
	 * public String getDocumentProofFilePath() { return documentProofFilePath;
	 * }
	 */

	/**
	 * @param documentOfProofContentType
	 *            the documentOfProofContentType to set
	 */
	public void setDocumentOfProofContentType(String documentOfProofContentType) {
		this.documentOfProofContentType = documentOfProofContentType;
	}

	/**
	 * @param documentOfProofFileName
	 *            the documentOfProofFileName to set
	 */
	public void setDocumentOfProofFileName(String documentOfProofFileName) {
		this.documentOfProofFileName = documentOfProofFileName;
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

	// /**
	// * @return the name
	// */
	// public String getName() {
	// return nameOfConsumer;
	// }

	/**
	 * @param isRainWaterHarvestInstalled
	 *            the isRainWaterHarvestInstalled to set
	 */
	public void setIsRainWaterHarvestInstalled(
			String isRainWaterHarvestInstalled) {
		this.isRainWaterHarvestInstalled = isRainWaterHarvestInstalled;
	}

	// /**
	// * @return the rainWaterHarvestInstallDate
	// */
	// public String getRainWaterHarvestInstallDate() {
	// return rainWaterHarvestInstallDate;
	// }

	/**
	 * @param isWasteWaterTreatmentInstalled
	 *            the isWasteWaterTreatmentInstalled to set
	 */
	public void setIsWasteWaterTreatmentInstalled(
			String isWasteWaterTreatmentInstalled) {
		this.isWasteWaterTreatmentInstalled = isWasteWaterTreatmentInstalled;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	// /**
	// * @param commentsOfuser
	// * the commentsOfuser to set
	// */
	// public void setCommentsOfuser(String commentsOfuser) {
	// this.commentsOfuser = commentsOfuser;
	// }

	// /**
	// * @param currentRainwaterHarvestingFlagInSystem
	// * the currentRainwaterHarvestingFlagInSystem to set
	// */
	// public void setCurrentRainwaterHarvestingFlagInSystem(
	// String currentRainwaterHarvestingFlagInSystem) {
	// this.currentRainwaterHarvestingFlagInSystem =
	// currentRainwaterHarvestingFlagInSystem;
	// }
	// /**
	// * @param docNo
	// * the docNo to set
	// */
	// public void setDocNo(String docNo) {
	// this.docNo = docNo;
	// }
	/**
	 * @param nameOfConsumer
	 *            the nameOfConsumer to set
	 */
	public void setNameOfConsumer(String nameOfConsumer) {
		this.nameOfConsumer = nameOfConsumer;
	}

	/*
	 * public void setDocumentProofFilePath(String documentProofFilePath) {
	 * this.documentProofFilePath = documentProofFilePath; }
	 */

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param rwhImpdate
	 *            the rwhImpdate to set
	 */
	public void setRwhImpdate(String rwhImpdate) {
		this.rwhImpdate = rwhImpdate;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	// /**
	// * @param name
	// * the name to set
	// */
	// public void setName(String nameOfConsumer) {
	// this.nameOfConsumer = nameOfConsumer;
	// }

	// /**
	// * @param photocopy
	// * the photocopy to set
	// */
	// public void setPhotocopy(String photocopy) {
	// this.photocopy = photocopy;
	// }

	// /**
	// * @param rainWaterHarvestInstallDate
	// * the rainWaterHarvestInstallDate to set
	// */
	// public void setRainWaterHarvestInstallDate(
	// String rainWaterHarvestInstallDate) {
	// this.rainWaterHarvestInstallDate = rainWaterHarvestInstallDate;
	// }
	/**
	 * @param wasteWaterdocumentOfProof
	 *            the wasteWaterdocumentOfProof to set
	 */
	public void setWasteWaterdocumentOfProof(File wasteWaterdocumentOfProof) {
		this.wasteWaterdocumentOfProof = wasteWaterdocumentOfProof;
	}

	/**
	 * @param wwhImpdate
	 *            the wwhImpdate to set
	 */
	public void setWwhImpdate(String wwhImpdate) {
		this.wwhImpdate = wwhImpdate;
	}

	/**
	 * <p>
	 * This method is used to upload document in UCM. Jtrac DJB-4037
	 * </p>
	 * 
	 * @param fileName
	 * @param docProof
	 * @param docProofFileName
	 * @param docProofFilePath
	 * @param docNo
	 * @param docProofContentTyp
	 * @param docTitle
	 * @param typeOfDocument
	 * @return
	 * @throws Exception
	 */
	public String ucmUpload(String fileName, File docProof,
			String docProofFileName, String docProofFilePath, String docNo,
			String docProofContentTyp, String docTitle, String typeOfDocument)
			throws Exception {

		String Success = null;
		try {
			// System.out.println("path>>>" + docProofFilePath);
			if (null != DJBConstants.UCM_UPLOAD
					&& !"".equals(DJBConstants.UCM_UPLOAD.trim())) {
				String TMP_DIR_PATH = PropertyUtil
						.getProperty("UCMdocumentUpload");
				// System.out.println("TMP_DIR_PATH::" + TMP_DIR_PATH);
				AppLog.info("TMP_DIR_PATH::" + TMP_DIR_PATH);
				System.out
						.println("documentOfProof>>" + docProof
								+ ">>documentOfProofFileName>>"
								+ docProofFileName
								+ ">>documentOfProofContentType>>"
								+ docProofContentTyp);

				AppLog
						.info("documentOfProof::" + docProof
								+ ">>documentOfProofFileName>>"
								+ docProofFileName
								+ ">>documentOfProofContentType>>"
								+ docProofContentTyp);

				String temFileName = fileName
						+ kno
						+ docProofFileName.substring(docProofFileName
								.indexOf('.'));
				File fileToCreate = new File(docProofFilePath, temFileName);

				FileUtils.copyFile(docProof, fileToCreate);
				// System.out.println("path>>>" + docProofFilePath
				// + "::filename::" + docProofFileName
				// + ":::file.length::" + fileToCreate.length());
				HashMap<String, String> Contents = null;
				UcmDocUploadUtil ucmDocUploadUtil = new UcmDocUploadUtil();
				Contents = new HashMap<String, String>();
				String msgPath = "NA";
				// String ucmPath = "";
				// Contents.put("FirstName", DataVO.getName());
				// Contents.put("LastName", DataVO.getLast());
				//
				// Contents.put("AppRefNo", DataVO.getAplicationRefNo());
				Contents.put("Kno", kno);

				/******** File Parsing... ************/

				Contents.put("dDocTitle", docTitle);// Take
				// from
				// constant
				// file
				// via
				// property file
				Contents.put("xTypeOfDocument", typeOfDocument);
				Contents.put("DocumentNo", docNo);
				File op;
				try {
					op = ucmDocUploadUtil.checkInContent(docProof.toString(),
							Contents, temFileName);
					msgPath = UcmDocUploadUtil.readFileParse(op);
					ucmPath = UcmDocUploadUtil.ucmPathParse(msgPath);
					// System.out.println("msgPath>>>" + msgPath +
					// ">>>ucmPath>>>"
					// + ucmPath);
					AppLog.info("msgPath>>>" + msgPath + ">>>ucmPath>>>"
							+ ucmPath);
				} catch (Exception e) {
					AppLog.error(e);
				}
			}
			if (null != docProofFilePath) {
				Success = docProofFilePath;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Success;
	}
}
