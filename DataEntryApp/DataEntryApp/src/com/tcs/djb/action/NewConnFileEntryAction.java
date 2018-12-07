/************************************************************************************************************
 * @(#) NewConnFileEntryAction.java   12 Jan 2015
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.model.NewConnFileEntryDetails;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * Action class for New Connection File Entry Screen
 * </p>
 * 
 * @author Rajib Hazarika(Tata Consultancy Services)
 * @since 12-JAN-2015
 * @history: On 16-SEP-2016 Rajib Hazarika (682667) added extra logic to prevent
 *           file entry of those files which are tagged with specific employeeId
 *           as per JTrac ANDROMR-7 for Quick New Connection CR changes
 */
@SuppressWarnings("deprecation")
public class NewConnFileEntryAction extends ActionSupport {
	private static final long serialVersionUID = -2961753266773964519L;
	/**
	 * variable to Store data entered by.
	 */
	private String createdBy;
	/*
	 * variable to store email Id
	 */
	private String emailId;
	/*
	 * variable to store file Name
	 */
	private String fileName;

	/*
	 * variable to store first Name
	 */
	private String firstName;
	/**
	 * variable to Store hidAction.
	 */
	private String hidAction;
	/**
	 * variable to Store House No.
	 */
	private String hnoAdd;

	/**
	 * variable to Store proof of identity type CD.
	 */
	private String identityProof;

	/**
	 * variable to Store inputStream.
	 */
	private InputStream inputStream;

	/**
	 * variable to Store JJR colony Flg.
	 */
	private String jjr;
	/*
	 * variable to store last Name
	 */
	private String lastName;
	/**
	 * variable to Store Locality.
	 */
	private String locaAdd;
	/*
	 * variable to store middle Name
	 */
	private String middleName;

	/**
	 * variable to Store phone.
	 */
	private String phone;

	/**
	 * variable to Store pin code.
	 */
	private String pinAdd;

	/*
	 * variable to store proof of identity doc
	 */
	private String proofOfIdn;

	/*
	 * variable to store List map values for Identity proof Type
	 */
	private Map<String, String> proofOfIdTypeListMap;

	/*
	 * variable to store proof of Residence
	 */
	private String proofOfRes;
	/*
	 * variable to store List map values for Identity proof Type
	 */
	private Map<String, String> proofOfResListMap;

	/**
	 * variable to Store Property Type Doc CD.
	 */
	private String propertyDoc;

	/*
	 * variable to store List map values for Identity proof Type
	 */
	private Map<String, String> propertyOwnDocListMap;

	/**
	 * variable to Store response.
	 */
	private HttpServletResponse response;

	/**
	 * variable to Store File No.
	 */
	private String roadNo;

	/**
	 * variable to Store Logged Status.
	 */
	private String status;

	/**
	 * variable to Store Sub Colony .
	 */
	private String subColAdd;

	/**
	 * variable to Store Sub Loc1.
	 */
	private String subLoc1Add;

	/**
	 * variable to Store Sub Loc2.
	 */
	private String subLoc2Add;

	/**
	 * variable to Store Sub Locality.
	 */
	private String subLocAdd;

	/**
	 * variable to Store type Of App.
	 */
	private String typeOfApp;
	/*
	 * variable to store List map values for Identity proof Type
	 */
	private Map<String, String> typeOfAppListMap;

	/**
	 * variable to Store Village Address.
	 */
	private String vilAdd;

	/**
	 * <p>
	 * For all ajax call in New Connection File Entry Screen
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
			// session.remove("DataSaved");
			if ("submitDetails".equalsIgnoreCase(hidAction)) {
				NewConnFileEntryDetails newConnFileEntryDetails = new NewConnFileEntryDetails();
				AppLog.info(">>fileName>>" + fileName + ">>typeOfApp>>"
						+ typeOfApp + ">>firstName>>" + firstName
						+ ">>middleName>>" + middleName + ">>lastName>>"
						+ lastName + ">>phone>>" + phone + ">>emailId>>"
						+ emailId + ">>pinAdd>>" + pinAdd + ">>pinAdd>>"
						+ pinAdd + ">>locaAdd>>" + locaAdd + ">>subLocAdd>>"
						+ subLocAdd + ">>hNoAdd>>" + hnoAdd + ">>roadNoAdd>>"
						+ roadNo + ">>subLoc1Add>>" + subLoc1Add
						+ ">>subLoc2Add>>" + subLoc2Add + ">>subColAdd>>"
						+ subColAdd + ">>vilAdd>>" + vilAdd
						+ ">>identityProof>>" + identityProof
						+ ">>propertyDoc>>" + propertyDoc + ">>proofOfRes>>"
						+ proofOfRes + ">>jjr>>" + jjr);
				boolean mandatoryFlgMissing = true;
				if (null != fileName && !"".equalsIgnoreCase(fileName)) {
					newConnFileEntryDetails.setFileName(fileName.trim());
					mandatoryFlgMissing = false;
				}
				if (null != typeOfApp && !"".equalsIgnoreCase(typeOfApp)) {
					newConnFileEntryDetails.setTypeOfApp(typeOfApp.trim());
					mandatoryFlgMissing = false;
				}
				if (null != firstName && !"".equalsIgnoreCase(firstName)) {
					newConnFileEntryDetails.setFirstName(firstName.trim());
					mandatoryFlgMissing = false;
				}
				if ((null != pinAdd && !"".equalsIgnoreCase(pinAdd))) {
					newConnFileEntryDetails.setPinAdd(pinAdd.trim());
					mandatoryFlgMissing = false;
				}
				if ((null != locaAdd && !"".equalsIgnoreCase(locaAdd))) {
					newConnFileEntryDetails.setLocaAdd(locaAdd.trim());
					mandatoryFlgMissing = false;
				}
				if (null != subLocAdd && !"".equalsIgnoreCase(subLocAdd.trim())) {
					newConnFileEntryDetails.setSubLocAdd(subLocAdd.trim());
					mandatoryFlgMissing = false;
				}
				if (null != hnoAdd && !"".equalsIgnoreCase(hnoAdd.trim())) {
					newConnFileEntryDetails.setHnoAdd(hnoAdd.trim());
					mandatoryFlgMissing = false;
				}
				if (null != identityProof
						&& !"".equalsIgnoreCase(identityProof)) {
					newConnFileEntryDetails.setIdentityProof(identityProof
							.trim());
					mandatoryFlgMissing = false;
				}
				if (null != propertyDoc && !"".equalsIgnoreCase(propertyDoc)) {
					newConnFileEntryDetails.setPropertyDoc(propertyDoc.trim());
					mandatoryFlgMissing = false;
				}

				newConnFileEntryDetails.setCreatedBy(userId.trim());
				newConnFileEntryDetails
						.setStatus(DJBConstants.FILE_ENTRY_INITIAL_STATUS);
				// newConnFileEntryDetails.sethNoAdd("1");
				AppLog.info(">> newConnFileEntryDetails >>"
						+ newConnFileEntryDetails);

				if (mandatoryFlgMissing) {
					AppLog.info(">>Inside If>>");
					inputStream = new StringBufferInputStream(
							"ERROR:Missing Mandatory Details");
					AppLog.end();
					return SUCCESS;
				}

				if (null != middleName
						&& !"".equalsIgnoreCase(middleName.trim())) {
					newConnFileEntryDetails.setMiddleName(middleName.trim());
				}
				if (null != lastName && !"".equalsIgnoreCase(lastName.trim())) {
					newConnFileEntryDetails.setLastName(lastName.trim());
				}
				if (null != phone && !"".equalsIgnoreCase(phone.trim())) {
					newConnFileEntryDetails.setPhone(phone.trim());
				}
				if (null != emailId && !"".equalsIgnoreCase(emailId.trim())) {
					newConnFileEntryDetails.setEmailId(emailId.trim());
				}
				if (null != jjr && !"".equalsIgnoreCase(jjr)) {
					newConnFileEntryDetails.setJjr(jjr);
				}
				if (null != roadNo && !"".equalsIgnoreCase(roadNo.trim())) {
					newConnFileEntryDetails.setRoadNo(roadNo.trim());
				}
				if (null != subLoc1Add
						&& !"".equalsIgnoreCase(subLoc1Add.trim())) {
					newConnFileEntryDetails.setSubLoc1Add(subLoc1Add.trim());
				}
				if (null != subLoc2Add
						&& !"".equalsIgnoreCase(subLoc2Add.trim())) {
					newConnFileEntryDetails.setSubLoc2Add(subLoc2Add.trim());
				}
				if (null != subColAdd && !"".equalsIgnoreCase(subColAdd.trim())) {
					newConnFileEntryDetails.setSubColAdd(subColAdd.trim());
				}
				if (null != vilAdd && !"".equalsIgnoreCase(vilAdd.trim())) {
					newConnFileEntryDetails.setVilAdd(vilAdd.trim());
				}
				if (null != proofOfRes
						&& !"".equalsIgnoreCase(proofOfRes.trim())) {
					newConnFileEntryDetails.setProofOfRes(proofOfRes.trim());
				}
				// START:On 16-SEP-2016 Added by Rajib as per JTrac ANDROMR-7
				// for Quick New Connection CR changes
				if (null != DJBConstants.FILE_NO_EMP_TAG_VALIDATION_FLG
						&& DJBConstants.FLAG_Y
								.equalsIgnoreCase(DJBConstants.FILE_NO_EMP_TAG_VALIDATION_FLG)) {
					int checkIsTaggedToEmp = NewConnFileEntryDAO
							.isFileTaggedToEmp(fileName);
					AppLog.info(">>checkIsTaggedToEmp>>" + checkIsTaggedToEmp);
					if (checkIsTaggedToEmp > 0) {
						inputStream = new StringBufferInputStream(
								"ERROR: for File number "
										+ fileName
										+ ":: "
										+ DJBConstants.FILE_NO_TAGGED_TO_EMP_MSG);
						AppLog.end();
						return SUCCESS;
					}
				}
				// END:On 16-SEP-2016 Added by Rajib as per JTrac ANDROMR-7 for
				// Quick New Connection CR changes
				// START:calling DAO method to Save File Details
				int rowsInserted = 0;
				rowsInserted = NewConnFileEntryDAO
						.saveNewConnFileDetails(newConnFileEntryDetails);
				if (rowsInserted > 0) {
					inputStream = new StringBufferInputStream(
							"SUCCESS: File Details for file No. " + fileName
									+ " is saved Successfully.");
					AppLog.end();
					return SUCCESS;
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR: File Details for file No. " + fileName
									+ " could not be Saved Successfully");
					AppLog.end();
					return SUCCESS;
				}
			}
			// Code to Validate file No from Master Table
			if ("validateFileNo".equalsIgnoreCase(hidAction)) {
				if (null == fileName && "".equalsIgnoreCase(fileName)) {
					AppLog.info(">>fileName is Null>>");
					inputStream = new StringBufferInputStream(
							"ERROR:Missing Mandatory Details");
					AppLog.end();
					return SUCCESS;
				}
				// START:On 16-SEP-2016 Added by Rajib as per JTrac ANDROMR-7
				// for Quick New Connection CR changes
				if (null != DJBConstants.FILE_NO_EMP_TAG_VALIDATION_FLG
						&& DJBConstants.FLAG_Y
								.equalsIgnoreCase(DJBConstants.FILE_NO_EMP_TAG_VALIDATION_FLG)) {
					int checkIsTaggedToEmp = NewConnFileEntryDAO
							.isFileTaggedToEmp(fileName);
					AppLog.info(">>checkIsTaggedToEmp>>" + checkIsTaggedToEmp);
					if (checkIsTaggedToEmp > 0) {
						inputStream = new StringBufferInputStream(
								"ERROR: for File number "
										+ fileName
										+ ":: "
										+ DJBConstants.FILE_NO_TAGGED_TO_EMP_MSG);
						AppLog.end();
						return SUCCESS;
					}
				}
				// END:On 16-SEP-2016 Added by Rajib as per JTrac ANDROMR-7 for
				// Quick New Connection CR changes
				if (null != DJBConstants.FILE_NO_VALIDATION_FLG
						&& DJBConstants.FLAG_Y
								.equalsIgnoreCase(DJBConstants.FILE_NO_VALIDATION_FLG)) {
					String userZroCd = (String) session.get("deZroCd");
					if (null != userZroCd && !"".equalsIgnoreCase(userZroCd)) {
						int fileCnt = 0;
						fileCnt = NewConnFileEntryDAO.getFileCount(fileName);
						if (fileCnt > 0) {
							inputStream = new StringBufferInputStream(
									"ERROR: File number " + fileName
											+ " already exists in the System");
							AppLog.end();
							return SUCCESS;
						}

						if (null != DJBConstants.HQ_VALIDATION_FLG
								&& DJBConstants.FLAG_N
										.equalsIgnoreCase(DJBConstants.HQ_VALIDATION_FLG)) {
							if (null != DJBConstants.FILE_VALIDATION_BYPASSED_ZROCD
									&& userZroCd
											.contains(DJBConstants.FILE_VALIDATION_BYPASSED_ZROCD)) {
								AppLog
										.info(">>User belongs to Bypassed ZRO locations:>>UerZROCd>>"
												+ userZroCd
												+ ">>Bypassed ZRO CD>>"
												+ DJBConstants.FILE_VALIDATION_BYPASSED_ZROCD);
								inputStream = new StringBufferInputStream(
										"SUCCESS");
								AppLog.end();
								return SUCCESS;
							} else {
								// Check ZRO cd for the file No. entered from
								// Master Data and validate it for User ZRO cd
								String fileZroCd = null;
								fileZroCd = NewConnFileEntryDAO
										.getFileLocationZroCd(fileName);
								if (null == fileZroCd) {
									AppLog.info(">>file Zro Cd is null >>");
									inputStream = new StringBufferInputStream(
											"ERROR: Couldn't retrieve ZRO Location for the file :"
													+ fileName
													+ " , Please Contact System Administrator!");
									AppLog.end();
									return SUCCESS;
								}
								AppLog.info("userZroCd>>" + userZroCd
										+ ">>fileZroCd>>" + fileZroCd);
								if (userZroCd.trim().contains(fileZroCd.trim())) {
									AppLog.info(">>User has Access to file>>");
									inputStream = new StringBufferInputStream(
											"SUCCESS");
									AppLog.end();
									return SUCCESS;
								} else {
									AppLog
											.info("userZroCd>>"
													+ userZroCd
													+ ">>fileZroCd>>"
													+ fileZroCd
													+ ">>User don't have Access to file>>");
									inputStream = new StringBufferInputStream(
											"ERROR: You do not have access to the file number "
													+ fileName);
									AppLog.end();
									return SUCCESS;
								}

							}
						}
					} else {
						AppLog
								.info(">> No ZRO Location is tagged to the user for DEApp >>");
						inputStream = new StringBufferInputStream(
								"ERROR: No ZRO Location is tagged to the user for DEApp, Please contact System Administrator!");
						AppLog.end();
						return SUCCESS;
					}
				}

			}

		} catch (Exception e) {
			AppLog.info(">>Inside catch part>>");
			inputStream = new StringBufferInputStream(
					"ERROR: There is some problem occured in validating file Number: "
							+ fileName + " , Please Try Again!");
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
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			createdBy = (String) session.get("userId");
			// String userRole = (String) session.get("userRoleId");
			if (null == createdBy || "".equals(createdBy)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			proofOfIdTypeListMap = (HashMap<String, String>) session
					.get("proofOfIdTypeListMap");
			propertyOwnDocListMap = (HashMap<String, String>) session
					.get("propertyOwnDocListMap");
			proofOfResListMap = (HashMap<String, String>) session
					.get("proofOfResListMap");
			typeOfAppListMap = (HashMap<String, String>) session
					.get("typeOfAppListMap");
			if (null == proofOfIdTypeListMap || proofOfIdTypeListMap.isEmpty()) {
				proofOfIdTypeListMap = (HashMap<String, String>) NewConnFileEntryDAO
						.getDocAttchList(DJBConstants.PROOF_OF_ID_DOC_TYPE);
				session.put("proofOfIdTypeListMap", proofOfIdTypeListMap);
			}
			if (null == propertyOwnDocListMap
					|| propertyOwnDocListMap.isEmpty()) {
				propertyOwnDocListMap = (HashMap<String, String>) NewConnFileEntryDAO
						.getDocAttchList(DJBConstants.PROPERTY_OWNERSHIP_DOC_TYPE);
				session.put("propertyOwnDocListMap", propertyOwnDocListMap);
			}
			if (null == proofOfResListMap || proofOfResListMap.isEmpty()) {
				proofOfResListMap = (HashMap<String, String>) NewConnFileEntryDAO
						.getDocAttchList(DJBConstants.PROOF_OF_RES_DOC_TYPE);
				session.put("proofOfResListMap", proofOfResListMap);
			}
			List<String> typeOfAppValues = Arrays
					.asList(DJBConstants.NEW_CONN_TYPE_OF_APP_VALUES
							.split("\\s*,\\s*"));
			if (null == typeOfAppListMap || typeOfAppListMap.isEmpty()) {
				typeOfAppListMap = new LinkedHashMap<String, String>();
				for (String str : typeOfAppValues) {
					typeOfAppListMap.put(str.substring(0, str.indexOf(':')),
							str.substring(str.indexOf(':') + 1));
					AppLog.info(">>str>" + str.substring(str.indexOf(':') + 1)
							+ ">>str.substring(0)>>"
							+ str.substring(0, str.indexOf(':')));
				}
				session.put("typeOfAppListMap", typeOfAppListMap);
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		return "success";
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the hnoAdd
	 */
	public String getHnoAdd() {
		return hnoAdd;
	}

	/**
	 * @return the identityProof
	 */
	public String getIdentityProof() {
		return identityProof;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the jjr
	 */
	public String getJjr() {
		return jjr;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the locaAdd
	 */
	public String getLocaAdd() {
		return locaAdd;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the pinAdd
	 */
	public String getPinAdd() {
		return pinAdd;
	}

	/**
	 * @return the proofOfIdn
	 */
	public String getProofOfIdn() {
		return proofOfIdn;
	}

	/**
	 * @return the proofOfIdTypeListMap
	 */
	public Map<String, String> getProofOfIdTypeListMap() {
		return proofOfIdTypeListMap;
	}

	/**
	 * @return the proofOfRes
	 */
	public String getProofOfRes() {
		return proofOfRes;
	}

	/**
	 * @return the proofOfResListMap
	 */
	public Map<String, String> getProofOfResListMap() {
		return proofOfResListMap;
	}

	/**
	 * @return the propertyDoc
	 */
	public String getPropertyDoc() {
		return propertyDoc;
	}

	/**
	 * @return the propertyOwnDocListMap
	 */
	public Map<String, String> getPropertyOwnDocListMap() {
		return propertyOwnDocListMap;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the roadNo
	 */
	public String getRoadNo() {
		return roadNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the subColAdd
	 */
	public String getSubColAdd() {
		return subColAdd;
	}

	/**
	 * @return the subLoc1Add
	 */
	public String getSubLoc1Add() {
		return subLoc1Add;
	}

	/**
	 * @return the subLoc2Add
	 */
	public String getSubLoc2Add() {
		return subLoc2Add;
	}

	/**
	 * @return the subLocAdd
	 */
	public String getSubLocAdd() {
		return subLocAdd;
	}

	/**
	 * @return the typeOfApp
	 */
	public String getTypeOfApp() {
		return typeOfApp;
	}

	/**
	 * @return the typeOfAppListMap
	 */
	public Map<String, String> getTypeOfAppListMap() {
		return typeOfAppListMap;
	}

	/**
	 * @return the vilAdd
	 */
	public String getVilAdd() {
		return vilAdd;
	}

	/**
	 * @param createdBy
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param hnoAdd
	 */
	public void setHnoAdd(String hnoAdd) {
		this.hnoAdd = hnoAdd;
	}

	/**
	 * @param identityProof
	 */
	public void setIdentityProof(String identityProof) {
		this.identityProof = identityProof;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param jjr
	 */
	public void setJjr(String jjr) {
		this.jjr = jjr;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param locaAdd
	 */
	public void setLocaAdd(String locaAdd) {
		this.locaAdd = locaAdd;
	}

	/**
	 * @param middleName
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param pinAdd
	 */
	public void setPinAdd(String pinAdd) {
		this.pinAdd = pinAdd;
	}

	/**
	 * @param proofOfIdn
	 */
	public void setProofOfIdn(String proofOfIdn) {
		this.proofOfIdn = proofOfIdn;
	}

	/**
	 * @param proofOfIdTypeListMap
	 */
	public void setProofOfIdTypeListMap(Map<String, String> proofOfIdTypeListMap) {
		this.proofOfIdTypeListMap = proofOfIdTypeListMap;
	}

	/**
	 * @param proofOfRes
	 */
	public void setProofOfRes(String proofOfRes) {
		this.proofOfRes = proofOfRes;
	}

	/**
	 * @param proofOfResListMap
	 */
	public void setProofOfResListMap(Map<String, String> proofOfResListMap) {
		this.proofOfResListMap = proofOfResListMap;
	}

	/**
	 * @param propertyDoc
	 */
	public void setPropertyDoc(String propertyDoc) {
		this.propertyDoc = propertyDoc;
	}

	/**
	 * @param propertyOwnDocListMap
	 */
	public void setPropertyOwnDocListMap(
			Map<String, String> propertyOwnDocListMap) {
		this.propertyOwnDocListMap = propertyOwnDocListMap;
	}

	/**
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param roadNo
	 */
	public void setRoadNo(String roadNo) {
		this.roadNo = roadNo;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param subColAdd
	 */
	public void setSubColAdd(String subColAdd) {
		this.subColAdd = subColAdd;
	}

	/**
	 * @param subLoc1Add
	 */
	public void setSubLoc1Add(String subLoc1Add) {
		this.subLoc1Add = subLoc1Add;
	}

	/**
	 * @param subLoc2Add
	 */
	public void setSubLoc2Add(String subLoc2Add) {
		this.subLoc2Add = subLoc2Add;
	}

	/**
	 * @param subLocAdd
	 */
	public void setSubLocAdd(String subLocAdd) {
		this.subLocAdd = subLocAdd;
	}

	/**
	 * @param typeOfApp
	 */
	public void setTypeOfApp(String typeOfApp) {
		this.typeOfApp = typeOfApp;
	}

	/**
	 * @param typeOfAppListMap
	 */
	public void setTypeOfAppListMap(Map<String, String> typeOfAppListMap) {
		this.typeOfAppListMap = typeOfAppListMap;
	}

	/**
	 * @param vilAdd
	 */
	public void setVilAdd(String vilAdd) {
		this.vilAdd = vilAdd;
	}
}
