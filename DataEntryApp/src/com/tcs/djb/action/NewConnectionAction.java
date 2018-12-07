/************************************************************************************************************
 * @(#) NewConnectionAction.java   18 Dec 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.NewConnectionDAO;
import com.tcs.djb.interfaceImpls.NewConnectionInterfaceImpl;
import com.tcs.djb.interfaces.NewConnectionInterface;
import com.tcs.djb.model.MeterTypeLookup;
import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for New Connection DAF Entry Screen
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 * @Action class for New connection DAF Details Data Entry
 * @history Matiur Rahman Removed All hard Coded value by database value.
 * @history Matiur Rahman Added mallCineplex on 18-12-2012
 * @history 13-06-2013 Matiur Rahman Changes has been made as per JTrac
 *          #DJB-1574. New method {@link #saveNewConnectionDAFDetails()} added
 *          for saving DAF details. Details saved via AJax call now changed from
 *          form submit.<br/>
 *          Two validation Added:<br/>
 *          <li>1.In case a DAF new connection has ZONE, MR, Area and WC No. -
 *          We must validate that no account exists in the system with the same
 *          combination of zone, MR, Area and WC No entered in DAF before
 *          request submission. If an account exists, an error message will be
 *          displayed during New Connection DAF request submission. The error
 *          message will display the account ID that exists in system for the
 *          Zone,MR,Area, WC No combination. This validation will be kept at RMS
 *          Portal end.(Hard Validation) * Hard Validation : System state will
 *          not change.</li><li>2. In case a DAF new connection has ZONE, MR,
 *          Area but WC No. is NULL - We must validate whether any DAF request
 *          with this ZONE, MR, Area,Consumer Name and Father Name has been
 *          submitted before, if yes a warning should be given with other
 *          details of the request. In case DAF user wants to go ahead, he
 *          should be allowed. (Soft Validation) * Soft Validation : System
 *          state will not change unless and untill it is overridden by
 *          consumer.</li>
 * @history 27-11-2013 Matiur Rahman Commented the part for Success Message for
 *          creating V as Per JTrac DJB-2123.
 * @history 28-11-2013 Matiur Rahman Implemented Restriction for data Entry as
 *          ZRO location for Hand Holding Staff as Per JTrac DJB-2123.
 * @history 05-12-2013 Matiur Rahman Implemented Restriction for Processing as
 *          ZRO location for Hand Holding Staff as Per JTrac DJB-2123.
 * @history 08-04-2016 Diksha Mukherjee the part of Ground Water Billing as per
 *          JTrac DJB-4211
 * 
 */
@SuppressWarnings("deprecation")
public class NewConnectionAction extends ActionSupport implements
		ServletResponseAware {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	private static final String savedNewConnectionDAFDetailsFlagConstant = "savedNewConnectionDAFDetailsFlag";
	private static final String SCREEN_ID = "21";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = 1L;
	private String accountId;
	private String applicationPurpose;
	private Map<String, String> applicationPurposeMap;
	private String areaNo;
	private String averageConsumption;
	private String builtUpArea;
	private String dafId;
	private String dafSequence;
	private String entityName;
	private String fatherHusbandName;
	private String functionSite;
	private String hidAction;
	private String houseNumber;
	private BigDecimal initialRegisetrRead;
	private String initialRegisterReadDate;
	private String initialRegisterReadRemark;
	private InputStream inputStream;
	private String isDJB;
	private Map<String, String> isDJBEmployeeMap;
	private String jjrColony;
	private String jjrHidden;
	private String khashraNumber;
	private String locality;
	private String mallCineplex;
	private Map<String, String> meterReaderRemarkCodeListMap;
	private Map<String, String> meterReadRemarkCodeMap;
	private Map<String, String> meterTypeListMap;
	private String mrNo;
	private String noOfAdults;

	private String noOfBeds;
	private String noOfChildren;
	private String noOfFloors;
	// Started: Added By Diksha Mukherjee as per Jtrac 4211
	private String noOfBorewells;
	// Ended: Added By Diksha Mukherjee as per Jtrac 4211
	private String noOfOccDwellUnits;
	private String noOfRooms;

	private String noOfUnoccDwellUnits;

	private BigDecimal openingBalance;

	private String personIdNumber;
	private String personIdType;
	private Map<String, String> personIdTypeListMap;

	private String pinCode;

	private String plotArea;

	private String propertyType;

	private Map<String, String> propertyTypeMap;

	private HttpServletResponse response;

	private String roadNumber;
	private String saTypeCode;
	private String sizeOfMeter;
	private String societyName;
	private String subColony;
	private String subLocality;
	private String sublocality1;
	private String sublocality2;
	private String typeOfMeter;
	private String urban;
	private String urbanHidden;
	private String village;
	private Map<String, String> waterConnectionSizeListMap;
	private String waterConnectionType;

	private Map<String, String> waterConnectionTypeMap;

	private String waterConnectionUse;
	private Map<String, String> waterConnectionUseListMap;
	private String wcNo;
	private Map<String, String> zoneCodeListMap;
	private String zoneNo;
	private String zroLocation;

	/**
	 * <p>
	 * For all ajax call in New Connection DAF Entry screen
	 * </p>
	 * 
	 * @return
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
			// System.out.println("hidAction::" + hidAction);
			if ("populateLocality".equalsIgnoreCase(hidAction)) {
				if (null == pinCode || pinCode.length() != 6) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><b>ERROR: Please provide a valid pincode </b></font>");
				} else {
					NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
					Map<String, String> localityMap = (HashMap<String, String>) newConnectionDAO
							.getLocality(pinCode);
					StringBuffer localityDropDown = new StringBuffer(512);
					localityDropDown
							.append("<select name=\"locality\" id=\"locality\" class=\"selectbox-long\" onchange=\"fnSetSubLocality();\">");
					localityDropDown
							.append("<option value=''>Locality</option>");
					if (null != localityMap && !localityMap.isEmpty()) {
						for (Entry<String, String> entry : localityMap
								.entrySet()) {
							localityDropDown.append(optionTagBeginPart1);
							localityDropDown.append(entry.getKey());
							localityDropDown.append(optionTagBeginPart2);
							localityDropDown.append(entry.getValue());
							localityDropDown.append(optionTagEnd);
						}
						localityDropDown.append(selectTagEnd);
						session.put("localityMap", localityMap);
						inputStream = new StringBufferInputStream(
								localityDropDown.toString());
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><b>ERROR: No Locality found corresponding to the PIN code. </b></font>");
					}
				}
			}

			if ("populateSubLocality".equalsIgnoreCase(hidAction)) {
				if (null != locality
						&& (!"Please Select".equalsIgnoreCase(locality.trim()))) {
					NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
					Map<String, String> subLocalityMap = (HashMap<String, String>) newConnectionDAO
							.getSubLocality(locality.trim());
					StringBuffer subLocalityDropDown = new StringBuffer(512);
					subLocalityDropDown
							.append("<select name=\"subLocality\" id=\"subLocality\" class=\"selectbox-long\" >");
					subLocalityDropDown
							.append("<option value=''>Sub Locality</option>");
					if (null != subLocalityMap && !subLocalityMap.isEmpty()) {
						for (Entry<String, String> entry : subLocalityMap
								.entrySet()) {
							subLocalityDropDown.append(optionTagBeginPart1);
							subLocalityDropDown.append(entry.getKey());
							subLocalityDropDown.append(optionTagBeginPart2);
							subLocalityDropDown.append(entry.getValue());
							subLocalityDropDown.append(optionTagEnd);
						}
						subLocalityDropDown.append(selectTagEnd);
						session.put("localityMap", subLocalityMap);
						inputStream = new StringBufferInputStream(
								subLocalityDropDown.toString());
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><b>ERROR: No Sub Locality found corresponding to the Locality </b></font>");
					}
				}
			}

			if ("populateMrNo".equalsIgnoreCase(hidAction)) {
				if (null != zoneNo && (!"".equalsIgnoreCase(zoneNo.trim()))) {
					zroLocation = (String) session.get("zroLocation");
					NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
					/*
					 * Start:Commented By Matiur Rahman as per New Process as
					 * Per JTrac DJB-2123 on 27/11/2013
					 */
					// ArrayList<String> mrNoList = (ArrayList<String>)
					// newConnectionDAO
					// .getMrNo(zoneNo.trim());
					/*
					 * End:Commented By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					/*
					 * Start:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					ArrayList<String> mrNoList = (ArrayList<String>) newConnectionDAO
							.getMrNo(zoneNo.trim(), zroLocation);
					/*
					 * End:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					StringBuffer mrNoDropDown = new StringBuffer(512);
					if (null != mrNoList && mrNoList.size() > 0) {
						mrNoDropDown
								.append("<select name=\"mrNo\" id=\"mrNo\" class=\"selectbox\" onchange=\"fnSetAreaNo();\">");
						mrNoDropDown
								.append("<option value=''>MR No *</option>");
						for (int i = 0; i < mrNoList.size(); i++) {
							mrNoDropDown.append(optionTagBeginPart1);
							mrNoDropDown.append(mrNoList.get(i));
							mrNoDropDown.append(optionTagBeginPart2);
							mrNoDropDown.append(mrNoList.get(i));
							mrNoDropDown.append(optionTagEnd);
						}
						mrNoDropDown.append(selectTagEnd);
						inputStream = new StringBufferInputStream(mrNoDropDown
								.toString());
						// System.out.println(mrNoDropDown.toString());
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><b>ERROR: No MR No found corresponding to Zone </b></font>");
					}
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><b>ERROR: Please select a Zone </b></font>");
				}
			}

			if ("populateAreaNo".equalsIgnoreCase(hidAction)) {
				if (null != zoneNo && (!"".equalsIgnoreCase(zoneNo.trim()))
						&& null != mrNo && (!"".equalsIgnoreCase(mrNo.trim()))) {
					zroLocation = (String) session.get("zroLocation");
					NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
					/*
					 * Start:Commented By Matiur Rahman as per New Process as
					 * Per JTrac DJB-2123 on 27/11/2013
					 */
					// Map<String, String> areaMap = (HashMap<String, String>)
					// newConnectionDAO
					// .getAreaNo(zoneNo.trim(), mrNo.trim());
					/*
					 * End:Commented By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					/*
					 * Start:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					Map<String, String> areaMap = (HashMap<String, String>) newConnectionDAO
							.getAreaNo(zoneNo.trim(), mrNo.trim(), zroLocation);
					/*
					 * End:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					StringBuffer areaNoDropDown = new StringBuffer(512);
					areaNoDropDown
							.append("<select name=\"areaNo\" id=\"areaNo\" class=\"selectbox-long\" onchange=\"fnValidateNewDAFForZoneMRAreaWCNo();\">");
					areaNoDropDown.append("<option value=''>Area *</option>");
					if (null != areaMap && !areaMap.isEmpty()) {
						for (Entry<String, String> entry : areaMap.entrySet()) {
							areaNoDropDown.append(optionTagBeginPart1
									+ entry.getKey() + optionTagBeginPart2
									+ entry.getValue() + optionTagEnd);
						}
						areaNoDropDown.append(selectTagEnd);
						inputStream = new StringBufferInputStream(
								areaNoDropDown.toString());
					} else {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><b>ERROR: No Area found corresponding to MR No </b></font>");
					}
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><b>ERROR: Please select a Zone and MR No.</b></font>");
				}
			}
			// Start-Added by Matiur Rahman on 18-12-2012
			if ("populateMallCineplexOption".equalsIgnoreCase(hidAction)) {

				NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
				Map<String, String> areaMap = (HashMap<String, String>) newConnectionDAO
						.getMallCineplexOption();
				StringBuffer mallCineplexOptionDropDown = new StringBuffer(512);
				mallCineplexOptionDropDown
						.append("<select name=\"mallCineplex\" id=\"mallCineplex\" class=\"selectbox\" >");
				if (null != areaMap && !areaMap.isEmpty()) {
					for (Entry<String, String> entry : areaMap.entrySet()) {
						mallCineplexOptionDropDown.append(optionTagBeginPart1);
						mallCineplexOptionDropDown.append(entry.getKey());
						mallCineplexOptionDropDown.append(optionTagBeginPart2);
						mallCineplexOptionDropDown.append(entry.getValue());
						mallCineplexOptionDropDown.append(optionTagEnd);
					}
					mallCineplexOptionDropDown.append(selectTagEnd);
					inputStream = new StringBufferInputStream(
							mallCineplexOptionDropDown.toString());
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><b>ERROR: No Options found corresponding to Property type Mall/Cineplex, Seting Default Option </b></font>");
				}
			}
			// End-Added by Matiur Rahman on 18-12-2012
			// Start-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
			if ("validateNewDAFForZoneMRAreaWCNo".equalsIgnoreCase(hidAction)) {
				String kno = NewConnectionDAO.getKNOByZoneMRAreaWCNo(zoneNo,
						mrNo, areaNo, wcNo);
				if (null == kno || "".equals(kno)) {
					inputStream = new StringBufferInputStream("VALID");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><b>Acoount Id: "
									+ kno
									+ " already exists with the combination Zone: "
									+ zoneNo
									+ " MR No: "
									+ mrNo
									+ " Area No: "
									+ areaNo
									+ " WC No: "
									+ wcNo
									+ ".<br/>Please select different combination to continue.</b></font>");
				}
			}
			if ("validateNewDAFForZoneMRAreaConsumerNameFatherName"
					.equalsIgnoreCase(hidAction)) {
				boolean exists = NewConnectionDAO
						.getKNOByZoneMRAreaNameFatherName(zoneNo, mrNo, areaNo,
								entityName, fatherHusbandName);
				if (exists) {
					inputStream = new StringBufferInputStream("INVALID");
				} else {
					inputStream = new StringBufferInputStream("VALID");
				}
			}
			/* save New Connection DAF Details */
			if ("saveNewConnectionDAFDetails".equalsIgnoreCase(hidAction)) {
				String responseMessage = saveNewConnectionDAFDetails();
				inputStream = new StringBufferInputStream(responseMessage);
			}
			if ("updateNewConnectionDAFDetails".equalsIgnoreCase(hidAction)) {
				String responseMessage = updateNewConnectionDAFDetails();
				inputStream = new StringBufferInputStream(responseMessage);
			}
			// End-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
			/* Process New Connection DAF Details */
			if ("processNewConnectionDAFDetails".equalsIgnoreCase(hidAction)) {
				String responseMessage = processNewConnectionDAFDetails();
				inputStream = new StringBufferInputStream(responseMessage);
			}
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
				session.remove(savedNewConnectionDAFDetailsFlagConstant);
				result = "success";
			} else if ("saveNewConnectionDAFDetails".equals(hidAction)) {
				// System.out.println("entityName::" + entityName
				// + "::fatherHusbandName::" + fatherHusbandName
				// + "::noOfOccDwellUnits::" + noOfOccDwellUnits
				// + "::noOfOccDwellUnits::" + noOfUnoccDwellUnits
				// + "::noOfRooms::" + noOfRooms);
				String savedNewConnectionDAFDetailsFlag = (String) session
						.get(savedNewConnectionDAFDetailsFlagConstant);
				if (!"Y".equalsIgnoreCase(savedNewConnectionDAFDetailsFlag)) {
					NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = new NewConnectionDAFDetailsContainer();

					newConnectionDAFDetailsContainer
							.setDafId(null == dafId ? "" : dafId);
					newConnectionDAFDetailsContainer
							.setEntityName(null == entityName ? "" : entityName);
					newConnectionDAFDetailsContainer
							.setFatherHusbandName(null == fatherHusbandName ? ""
									: fatherHusbandName);
					newConnectionDAFDetailsContainer
							.setPersonIdType((null == personIdType || ""
									.equalsIgnoreCase(personIdType.trim())) ? "DL"
									: personIdType);
					newConnectionDAFDetailsContainer
							.setPersonIdNumber((null == personIdNumber || ""
									.equalsIgnoreCase(personIdNumber.trim())) ? "NA"
									: personIdNumber);

					newConnectionDAFDetailsContainer
							.setHouseNumber((null == houseNumber || ""
									.equalsIgnoreCase(houseNumber.trim())) ? "NA"
									: houseNumber);
					newConnectionDAFDetailsContainer
							.setRoadNumber(null == roadNumber ? "" : roadNumber);
					newConnectionDAFDetailsContainer
							.setSublocality1(null == sublocality1 ? ""
									: sublocality1);
					newConnectionDAFDetailsContainer
							.setSublocality2(null == sublocality2 ? ""
									: sublocality2);
					newConnectionDAFDetailsContainer
							.setSubColony(null == subColony ? "" : subColony);
					newConnectionDAFDetailsContainer
							.setVillage(null == village ? "" : village);
					newConnectionDAFDetailsContainer
							.setKhashraNumber(null == khashraNumber ? ""
									: khashraNumber);
					newConnectionDAFDetailsContainer
							.setSocietyName(null == societyName ? ""
									: societyName);
					newConnectionDAFDetailsContainer
							.setPinCode(null == pinCode ? "" : pinCode);
					newConnectionDAFDetailsContainer
							.setLocality(null == locality ? "" : locality);
					newConnectionDAFDetailsContainer
							.setSubLocality(null == subLocality ? ""
									: subLocality);
					newConnectionDAFDetailsContainer.setJjrColony("on"
							.equalsIgnoreCase(jjrColony) ? "Y" : "N");
					newConnectionDAFDetailsContainer
							.setPropertyType(null == propertyType ? ""
									: propertyType);
					/* Started: Added By Diksha Mukherjee as per JTrac DJB-4211 */
					/*newConnectionDAFDetailsContainer
								.setNoOfFloors((null == noOfFloors || ""
										.equalsIgnoreCase(noOfFloors.trim())) ? "1"
										: noOfFloors);
					newConnectionDAFDetailsContainer
								.setNoOfBorewells((null == noOfBorewells || ""
										.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
										: noOfBorewells);
										*/
					if (null != waterConnectionType	&& !"".equalsIgnoreCase(waterConnectionType.trim())	&& !DJBConstants.CATIIIA_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim()) && !DJBConstants.CATIIIB_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim())) {
						newConnectionDAFDetailsContainer
								.setNoOfFloors((null == noOfFloors || ""
										.equalsIgnoreCase(noOfFloors.trim())) ? "1"
										: noOfFloors);
						newConnectionDAFDetailsContainer
								.setNoOfBorewells((null == noOfBorewells || ""
										.equalsIgnoreCase(noOfBorewells.trim())) ? ""
										: noOfBorewells);
					} else {
						newConnectionDAFDetailsContainer
								.setNoOfFloors((null == noOfFloors || ""
										.equalsIgnoreCase(noOfFloors.trim())) ? ""
										: noOfFloors);
						newConnectionDAFDetailsContainer
								.setNoOfBorewells((null == noOfBorewells || ""
										.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
										: noOfBorewells);
					}

				
					/* Ended: Added By Diksha Mukherjee as per JTrac DJB-4211 */
					// Setting No Of Rooms,No Of Beds,Function Site as per
					// property Type
					if ("Hotel/Guest Houses".equalsIgnoreCase(propertyType)
							|| "Dharamshalas/Hostels"
									.equalsIgnoreCase(propertyType)) {
						newConnectionDAFDetailsContainer
								.setNoOfRooms((null == noOfRooms || ""
										.equalsIgnoreCase(noOfRooms.trim())) ? ""
										: noOfRooms);
						newConnectionDAFDetailsContainer.setNoOfBeds("");
						newConnectionDAFDetailsContainer.setFunctionSite("");
						newConnectionDAFDetailsContainer.setMallCineplex("");
					} else if ("Hospital/Nursing Home"
							.equalsIgnoreCase(propertyType)) {
						newConnectionDAFDetailsContainer.setNoOfRooms("");
						newConnectionDAFDetailsContainer
								.setNoOfBeds((null == noOfBeds || ""
										.equalsIgnoreCase(noOfBeds.trim())) ? ""
										: noOfBeds);
						newConnectionDAFDetailsContainer.setFunctionSite("");
						newConnectionDAFDetailsContainer.setMallCineplex("");
					} else if ("Banquet Hall".equalsIgnoreCase(propertyType)) {
						newConnectionDAFDetailsContainer.setNoOfRooms("");
						newConnectionDAFDetailsContainer.setNoOfBeds("");
						newConnectionDAFDetailsContainer
								.setFunctionSite((null == functionSite || ""
										.equalsIgnoreCase(functionSite.trim())) ? ""
										: functionSite);
						newConnectionDAFDetailsContainer.setMallCineplex("");
					} else if ("Mall/Cineplex".equalsIgnoreCase(propertyType)) {
						newConnectionDAFDetailsContainer.setNoOfRooms("");
						newConnectionDAFDetailsContainer.setNoOfBeds("");
						newConnectionDAFDetailsContainer.setFunctionSite("");
						newConnectionDAFDetailsContainer
								.setMallCineplex((null == mallCineplex || ""
										.equalsIgnoreCase(mallCineplex.trim())) ? "1"
										: mallCineplex);
					} else {
						newConnectionDAFDetailsContainer.setNoOfRooms("");
						newConnectionDAFDetailsContainer.setNoOfBeds("");
						newConnectionDAFDetailsContainer.setFunctionSite("");
						newConnectionDAFDetailsContainer.setMallCineplex("");
					}
					newConnectionDAFDetailsContainer.setUrban("on"
							.equalsIgnoreCase(urban) ? "YES" : "NO");
					newConnectionDAFDetailsContainer
							.setPlotArea(null == plotArea ? "" : plotArea);
					newConnectionDAFDetailsContainer
							.setBuiltUpArea(null == builtUpArea ? ""
									: builtUpArea);
					newConnectionDAFDetailsContainer
							.setWaterConnectionType(null == waterConnectionType ? ""
									: waterConnectionType);
					newConnectionDAFDetailsContainer
							.setWaterConnectionUse(null == waterConnectionUse ? ""
									: waterConnectionUse);
					newConnectionDAFDetailsContainer
							.setNoOfOccDwellUnits((null == noOfOccDwellUnits || ""
									.equalsIgnoreCase(noOfOccDwellUnits.trim())) ? ""
									: noOfOccDwellUnits);
					newConnectionDAFDetailsContainer
							.setNoOfUnoccDwellUnits((null == noOfUnoccDwellUnits || ""
									.equalsIgnoreCase(noOfUnoccDwellUnits
											.trim())) ? ""
									: noOfUnoccDwellUnits);
					newConnectionDAFDetailsContainer
							.setNoOfChildren((null == noOfChildren || ""
									.equalsIgnoreCase(noOfChildren.trim())) ? "0"
									: noOfChildren);
					newConnectionDAFDetailsContainer
							.setNoOfAdults((null == noOfAdults || ""
									.equalsIgnoreCase(noOfAdults.trim())) ? "1"
									: noOfAdults);
					newConnectionDAFDetailsContainer
							.setIsDJB((null == isDJB || ""
									.equalsIgnoreCase(isDJB.trim())) ? "NOTDJB"
									: isDJB);
					newConnectionDAFDetailsContainer
							.setSizeOfMeter((null == sizeOfMeter || ""
									.equalsIgnoreCase(sizeOfMeter.trim())) ? "15"
									: sizeOfMeter);
					newConnectionDAFDetailsContainer
							.setTypeOfMeter(null == typeOfMeter ? ""
									: typeOfMeter);
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadDate(null == initialRegisterReadDate ? ""
									: initialRegisterReadDate);
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadRemark(null == initialRegisterReadRemark ? ""
									: initialRegisterReadRemark);

					newConnectionDAFDetailsContainer
							.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
									: initialRegisetrRead);
					newConnectionDAFDetailsContainer
							.setAverageConsumption(null == averageConsumption ? ""
									: averageConsumption);
					newConnectionDAFDetailsContainer
							.setZoneNo(null == zoneNo ? "" : zoneNo);
					newConnectionDAFDetailsContainer.setMrNo(null == mrNo ? ""
							: mrNo);
					newConnectionDAFDetailsContainer
							.setAreaNo(null == areaNo ? "" : areaNo);
					newConnectionDAFDetailsContainer
							.setOpeningBalance(null == openingBalance ? BigDecimal.ZERO
									: openingBalance);
					if ("SAWATR".equalsIgnoreCase(applicationPurpose)) {
						newConnectionDAFDetailsContainer
								.setApplicationPurpose("PERMCONN");
					}/*Start: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
					else if("GWNPSA".equalsIgnoreCase(applicationPurpose)){
						newConnectionDAFDetailsContainer.setApplicationPurpose("GWNPSA");
					}else if("GWPSA".equalsIgnoreCase(applicationPurpose)){
						newConnectionDAFDetailsContainer.setApplicationPurpose("GWPSA");
					}
					/*End: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
					 else {
						newConnectionDAFDetailsContainer
								.setApplicationPurpose("WATERSEW");
					}
					newConnectionDAFDetailsContainer
							.setSaTypeCode(applicationPurpose);
					newConnectionDAFDetailsContainer.setCreatedBy(userName);
					newConnectionDAFDetailsContainer.setWcNo(null == wcNo ? ""
							: wcNo);

					NewConnectionInterface newConnInterface = new NewConnectionInterfaceImpl();
					AppLog.info("\n##########Action Class Floor Num== "+newConnectionDAFDetailsContainer.getNoOfFloors());
					HashMap<Object, Object> returnmap = (HashMap<Object, Object>) newConnInterface
							.saveNewConnectionDAFDetails(newConnectionDAFDetailsContainer);
					String errorMessage = (String) returnmap
							.get("errorMessage");
					if (!"DataBaseError".equals(errorMessage)) {
						String accountID = (String) returnmap.get("accountID");
						// System.out.println("accountID::" + accountID);
						AppLog
								.info("System Generated Account ID::"
										+ accountID);
						if (null != accountID
								&& !"".equalsIgnoreCase(accountID)) {
							session.put(
									savedNewConnectionDAFDetailsFlagConstant,
									"Y");
							session
									.put(
											"ccbMessage",
											"<br/><br/><br/><br/><br/><center><font color=\"green\" size=\"6\">Please note System Generated Account ID :<br/><br/>"
													+ accountID
													+ "</font></center><br/><br/><br/><br/><br/>");

						} else {
							session.put(
									savedNewConnectionDAFDetailsFlagConstant,
									"Y");
							session
									.put(
											"ccbMessage",
											"<br/><br/><br/><br/><br/><center><font color=\"red\" size=\"5\">There was Problem while Creating Account in the System.</font></center><br/><br/><br/><br/><br/>");
						}
						result = "success";
					} else {
						session
								.remove(savedNewConnectionDAFDetailsFlagConstant);
						addActionError("There was a problem while Processing your Request.Data Couldn't be Saved.");
						result = "error";
					}
				}
				loadInitValues();
			} else if ("populateNewConnectionDAFDetails".equals(hidAction)) {
				loadInitValues();
				session.remove(savedNewConnectionDAFDetailsFlagConstant);
				AppLog.end();
				return populateNewConnectionDAFDetails();
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
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	/**
	 * @return the applicationPurpose
	 */
	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	/**
	 * @return the applicationPurposeMap
	 */
	public Map<String, String> getApplicationPurposeMap() {
		return applicationPurposeMap;
	}

	/**
	 * @return the areaNo
	 */
	public String getAreaNo() {
		return areaNo;
	}

	/**
	 * @return the averageConsumption
	 */
	public String getAverageConsumption() {
		return averageConsumption;
	}

	/**
	 * @return the builtUpArea
	 */
	public String getBuiltUpArea() {
		return builtUpArea;
	}

	/**
	 * @return the dafId
	 */
	public String getDafId() {
		return dafId;
	}

	/**
	 * @return the dafSequence
	 */
	public String getDafSequence() {
		return dafSequence;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @return the fatherHusbandName
	 */
	public String getFatherHusbandName() {
		return fatherHusbandName;
	}

	/**
	 * @return the functionSite
	 */
	public String getFunctionSite() {
		return functionSite;
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
	 * @return the initialRegisetrRead
	 */
	public BigDecimal getInitialRegisetrRead() {
		return initialRegisetrRead;
	}

	/**
	 * @return the initialRegisterReadDate
	 */
	public String getInitialRegisterReadDate() {
		return initialRegisterReadDate;
	}

	/**
	 * @return the initialRegisterReadRemark
	 */
	public String getInitialRegisterReadRemark() {
		return initialRegisterReadRemark;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the isDJB
	 */
	public String getIsDJB() {
		return isDJB;
	}

	/**
	 * @return the isDJBEmployeeMap
	 */
	public Map<String, String> getIsDJBEmployeeMap() {
		return isDJBEmployeeMap;
	}

	/**
	 * @return the jjrColony
	 */
	public String getJjrColony() {
		return jjrColony;
	}

	/**
	 * @return the jjrHidden
	 */
	public String getJjrHidden() {
		return jjrHidden;
	}

	/**
	 * @return the khashraNumber
	 */
	public String getKhashraNumber() {
		return khashraNumber;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @return the mallCineplex
	 */
	public String getMallCineplex() {
		return mallCineplex;
	}

	/**
	 * @return the meterReaderRemarkCodeListMap
	 */
	public Map<String, String> getMeterReaderRemarkCodeListMap() {
		return meterReaderRemarkCodeListMap;
	}

	/**
	 * @return the meterReadRemarkCodeMap
	 */
	public Map<String, String> getMeterReadRemarkCodeMap() {
		return meterReadRemarkCodeMap;
	}

	/**
	 * @return the meterTypeListMap
	 */
	public Map<String, String> getMeterTypeListMap() {
		return meterTypeListMap;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return the noOfAdults
	 */
	public String getNoOfAdults() {
		return noOfAdults;
	}

	/**
	 * @return the noOfBeds
	 */
	public String getNoOfBeds() {
		return noOfBeds;
	}
	// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	/**
	 * 
	 * @return the noOfBorewells
	 */
	
	public String getNoOfBorewells() {
		return noOfBorewells;
	}
	// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	/**
	 * @return the noOfChildren
	 */
	public String getNoOfChildren() {
		return noOfChildren;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @return the noOfOccDwellUnits
	 */
	public String getNoOfOccDwellUnits() {
		return noOfOccDwellUnits;
	}

	/**
	 * @return the noOfRooms
	 */
	public String getNoOfRooms() {
		return noOfRooms;
	}

	/**
	 * @return the noOfUnoccDwellUnits
	 */
	public String getNoOfUnoccDwellUnits() {
		return noOfUnoccDwellUnits;
	}

	/**
	 * @return the openingBalance
	 */
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @return the personIdNumber
	 */
	public String getPersonIdNumber() {
		return personIdNumber;
	}

	/**
	 * @return the personIdType
	 */
	public String getPersonIdType() {
		return personIdType;
	}

	/**
	 * @return the personIdTypeListMap
	 */
	public Map<String, String> getPersonIdTypeListMap() {
		return personIdTypeListMap;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @return the plotArea
	 */
	public String getPlotArea() {
		return plotArea;
	}

	/**
	 * @return the propertyType
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * @return the propertyTypeMap
	 */
	public Map<String, String> getPropertyTypeMap() {
		return propertyTypeMap;
	}

	/**
	 * @return the roadNumber
	 */
	public String getRoadNumber() {
		return roadNumber;
	}

	/**
	 * @return the saTypeCode
	 */
	public String getSaTypeCode() {
		return saTypeCode;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}

	/**
	 * @return the sizeOfMeter
	 */
	public String getSizeOfMeter() {
		return sizeOfMeter;
	}

	/**
	 * @return the societyName
	 */
	public String getSocietyName() {
		return societyName;
	}

	/**
	 * @return the subColony
	 */
	public String getSubColony() {
		return subColony;
	}

	/**
	 * @return the subLocality
	 */
	public String getSubLocality() {
		return subLocality;
	}

	/**
	 * @return the sublocality1
	 */
	public String getSublocality1() {
		return sublocality1;
	}

	/**
	 * @return the sublocality2
	 */
	public String getSublocality2() {
		return sublocality2;
	}

	/**
	 * @return the typeOfMeter
	 */
	public String getTypeOfMeter() {
		return typeOfMeter;
	}

	/**
	 * @return the urban
	 */
	public String getUrban() {
		return urban;
	}

	/**
	 * @return the urbanHidden
	 */
	public String getUrbanHidden() {
		return urbanHidden;
	}

	/**
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}

	/**
	 * @return the waterConnectionSizeListMap
	 */
	public Map<String, String> getWaterConnectionSizeListMap() {
		return waterConnectionSizeListMap;
	}

	/**
	 * @return the waterConnectionType
	 */
	public String getWaterConnectionType() {
		return waterConnectionType;
	}

	/**
	 * @return the waterConnectionTypeMap
	 */
	public Map<String, String> getWaterConnectionTypeMap() {
		return waterConnectionTypeMap;
	}

	/**
	 * @return the waterConnectionUse
	 */
	public String getWaterConnectionUse() {
		return waterConnectionUse;
	}

	/**
	 * @return the waterConnectionUseListMap
	 */
	public Map<String, String> getWaterConnectionUseListMap() {
		return waterConnectionUseListMap;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
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
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * <p>
	 * This method is used to load all drop down values
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void loadInitValues() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			NewConnectionDAO newConnectionDAO = new NewConnectionDAO();

			String maxFloorValue = (String) session.get("maxFloorValue");
			String userId = (String) session.get("userId");
			if (null == maxFloorValue
					|| "".equalsIgnoreCase(maxFloorValue.trim())) {
				maxFloorValue = NewConnectionDAO.getMaxFloorValue();
				session.put("maxFloorValue", maxFloorValue);
			}
			personIdTypeListMap = (HashMap<String, String>) session
					.get("personIdTypeListMap");
			if (null == personIdTypeListMap || personIdTypeListMap.isEmpty()) {
				personIdTypeListMap = (HashMap<String, String>) newConnectionDAO
						.getIdTypeListMap();
				session.put("personIdTypeListMap", personIdTypeListMap);
			}
			/*
			 * Start:Commented By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
			// zoneCodeListMap = (HashMap<String, String>) session
			// .get("zoneCodeListMap");
			// if (null == zoneCodeListMap || zoneCodeListMap.isEmpty()) {
			// zoneCodeListMap = (HashMap<String, String>) newConnectionDAO
			// .getZoneCodeListMap();
			// session.put("zoneCodeListMap", zoneCodeListMap);
			// }
			/*
			 * End:Commented By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
			/*
			 * Start:Added By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
			zroLocation = NewConnectionDAO.getZROLocationByUserID(userId);
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
			session.put("areaListMap", new HashMap<String, String>());
			session.put("localityMap", new HashMap<String, String>());
			session.put("subLocalityMap", new HashMap<String, String>());
			/*
			 * End:Added By Matiur Rahman as per New Process as Per JTrac
			 * DJB-2123 on 27/11/2013
			 */
			// meterReadRemarkCodeMap = (HashMap<String, String>)
			// newConnectionDAO
			// .getMeterReadRemarkCodeMap();
			applicationPurposeMap = (HashMap<String, String>) session
					.get("applicationPurposeMap");
			if (null == applicationPurposeMap
					|| applicationPurposeMap.isEmpty()) {
				applicationPurposeMap = (HashMap<String, String>) newConnectionDAO
						.getApplicationPurposeMap();
				session.put("applicationPurposeMap", applicationPurposeMap);
			}
			isDJBEmployeeMap = (HashMap<String, String>) session
					.get("isDJBEmployeeMap");
			if (null == isDJBEmployeeMap || isDJBEmployeeMap.isEmpty()) {
				isDJBEmployeeMap = (HashMap<String, String>) newConnectionDAO
						.getIsDJBEmployeeMap();
				session.put("isDJBEmployeeMap", isDJBEmployeeMap);
			}
			waterConnectionTypeMap = (HashMap<String, String>) session
					.get("waterConnectionTypeMap");
			if (null == waterConnectionTypeMap
					|| waterConnectionTypeMap.isEmpty()) {
				waterConnectionTypeMap = (HashMap<String, String>) newConnectionDAO
						.getWaterConnectionTypeMap();
				session.put("waterConnectionTypeMap", waterConnectionTypeMap);
			}
			propertyTypeMap = (HashMap<String, String>) session
					.get("propertyTypeMap");
			if (null == propertyTypeMap || propertyTypeMap.isEmpty()) {
				propertyTypeMap = (HashMap<String, String>) newConnectionDAO
						.getPropertyTypeMap();
				session.put("propertyTypeMap", propertyTypeMap);
			}
			ArrayList waterConnectionUseList = (ArrayList) session
					.get("waterConnectionUseList");
			if (null == waterConnectionUseList
					|| waterConnectionUseList.size() == 0) {
				waterConnectionUseList = (ArrayList<String>) GetterDAO
						.getWaterConnectionUseList();
				waterConnectionUseListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < waterConnectionUseList.size(); i++) {
					waterConnectionUseListMap.put(waterConnectionUseList.get(i)
							.toString(), waterConnectionUseList.get(i)
							.toString());
				}
				session.put("waterConnectionUseList", waterConnectionUseList);
			} else {
				// System.out.println("waterConnectionUseList::"
				// + waterConnectionUseList.size());
				waterConnectionUseListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < waterConnectionUseList.size(); i++) {
					waterConnectionUseListMap.put(waterConnectionUseList.get(i)
							.toString(), waterConnectionUseList.get(i)
							.toString());
				}
				session.put("waterConnectionUseList", waterConnectionUseList);
			}
			ArrayList<String> waterConnectionSizeList = (ArrayList<String>) session
					.get("newConnectionWaterConnectionSizeList");
			if (null == waterConnectionSizeList
					|| waterConnectionSizeList.size() == 0) {
				waterConnectionSizeList = (ArrayList<String>) NewConnectionDAO
						.getWaterConnectionList();
				session.put("newConnectionWaterConnectionSizeList",
						waterConnectionSizeList);
			}
			if (null != waterConnectionSizeList
					&& waterConnectionSizeList.size() > 0) {
				// System.out.println("waterConnectionSizeList::"
				// + waterConnectionSizeList.size());
				waterConnectionSizeListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < waterConnectionSizeList.size(); i++) {
					waterConnectionSizeListMap.put(waterConnectionSizeList.get(
							i).toString(), waterConnectionSizeList.get(i)
							.toString());
				}
			}
			ArrayList<MeterTypeLookup> meterTypeList = (ArrayList<MeterTypeLookup>) session
					.get("DJBMeterTypeList");
			MeterTypeLookup meterTypeLookup = null;
			if (null != meterTypeList && meterTypeList.size() > 0) {
				meterTypeListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < meterTypeList.size(); i++) {
					try {
						meterTypeLookup = (MeterTypeLookup) meterTypeList
								.get(i);
						meterTypeListMap.put(
								meterTypeLookup.getMeterTypeCode(),
								meterTypeLookup.getMeterTypeDesc());
					} catch (Exception e) {
						// e.printStackTrace();
						meterTypeList = (ArrayList<MeterTypeLookup>) GetterDAO
								.getDJBMeterTypeList();
						session.put("DJBMeterTypeList", meterTypeList);
						meterTypeListMap = new LinkedHashMap<String, String>();
						for (int j = 0; j < meterTypeList.size(); j++) {
							meterTypeLookup = (MeterTypeLookup) meterTypeList
									.get(j);
							meterTypeListMap.put(meterTypeLookup
									.getMeterTypeCode(), meterTypeLookup
									.getMeterTypeDesc());
						}
					}
				}
			} else {
				meterTypeList = (ArrayList<MeterTypeLookup>) GetterDAO
						.getDJBMeterTypeList();
				session.put("DJBMeterTypeList", meterTypeList);
				meterTypeListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < meterTypeList.size(); i++) {
					meterTypeLookup = (MeterTypeLookup) meterTypeList.get(i);
					meterTypeListMap.put(meterTypeLookup.getMeterTypeCode(),
							meterTypeLookup.getMeterTypeDesc());
				}
			}
			ArrayList<String> meterReaderRemarkCodeList = (ArrayList<String>) session
					.get("newConnectionMeterReadStatusList");
			if (null != meterReaderRemarkCodeList
					&& meterReaderRemarkCodeList.size() > 0) {
				meterReaderRemarkCodeListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < meterReaderRemarkCodeList.size(); i++) {
					meterReaderRemarkCodeListMap.put(meterReaderRemarkCodeList
							.get(i).toString(), meterReaderRemarkCodeList
							.get(i).toString());
				}
				session.put("newConnectionMeterReadStatusList",
						meterReaderRemarkCodeList);
			} else {
				meterReaderRemarkCodeList = (ArrayList<String>) NewConnectionDAO
						.getMeterReadRemarkList();
				meterReaderRemarkCodeListMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < meterReaderRemarkCodeList.size(); i++) {
					meterReaderRemarkCodeListMap.put(meterReaderRemarkCodeList
							.get(i).toString(), meterReaderRemarkCodeList
							.get(i).toString());
				}
				session.put("newConnectionMeterReadStatusList",
						meterReaderRemarkCodeList);
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	/**
	 * <p>
	 * This method is used to populate details on New Connection DAF Entry
	 * Screen
	 * </p>
	 * 
	 * @return String
	 */
	public String populateNewConnectionDAFDetails() {
		try {
			// System.out.println("entityName::" + entityName
			// + "::fatherHusbandName::" + fatherHusbandName
			// + "::noOfOccDwellUnits::" + noOfOccDwellUnits
			// + "::noOfOccDwellUnits::" + noOfUnoccDwellUnits
			// + "::noOfRooms::" + noOfRooms);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = newConnectionDAO
					.populateNewConnectionDAFDetails(dafSequence);
			if (null != newConnectionDAFDetailsContainer) {
				dafId = (null == newConnectionDAFDetailsContainer.getDafId() ? ""
						: newConnectionDAFDetailsContainer.getDafId().trim());
				entityName = (null == newConnectionDAFDetailsContainer
						.getEntityName() ? ""
						: newConnectionDAFDetailsContainer.getEntityName()
								.trim());
				fatherHusbandName = (null == newConnectionDAFDetailsContainer
						.getFatherHusbandName() ? ""
						: newConnectionDAFDetailsContainer
								.getFatherHusbandName().trim());
				personIdType = (null == newConnectionDAFDetailsContainer
						.getPersonIdType() ? ""
						: newConnectionDAFDetailsContainer.getPersonIdType()
								.trim());
				personIdNumber = (null == newConnectionDAFDetailsContainer
						.getPersonIdNumber() ? ""
						: newConnectionDAFDetailsContainer.getPersonIdNumber()
								.trim());
				houseNumber = (null == newConnectionDAFDetailsContainer
						.getHouseNumber() ? ""
						: newConnectionDAFDetailsContainer.getHouseNumber()
								.trim());
				roadNumber = (null == newConnectionDAFDetailsContainer
						.getRoadNumber() ? ""
						: newConnectionDAFDetailsContainer.getRoadNumber()
								.trim());
				sublocality1 = (null == newConnectionDAFDetailsContainer
						.getSublocality1() ? ""
						: newConnectionDAFDetailsContainer.getSublocality1()
								.trim());
				sublocality2 = (null == newConnectionDAFDetailsContainer
						.getSublocality2() ? ""
						: newConnectionDAFDetailsContainer.getSublocality2()
								.trim());
				subColony = (null == newConnectionDAFDetailsContainer
						.getSubColony() ? "" : newConnectionDAFDetailsContainer
						.getSubColony().trim());
				village = (null == newConnectionDAFDetailsContainer
						.getVillage() ? "" : newConnectionDAFDetailsContainer
						.getVillage().trim());
				khashraNumber = (null == newConnectionDAFDetailsContainer
						.getKhashraNumber() ? ""
						: newConnectionDAFDetailsContainer.getKhashraNumber()
								.trim());
				societyName = (null == newConnectionDAFDetailsContainer
						.getSocietyName() ? ""
						: newConnectionDAFDetailsContainer.getSocietyName()
								.trim());
				pinCode = (null == newConnectionDAFDetailsContainer
						.getPinCode() ? "" : newConnectionDAFDetailsContainer
						.getPinCode().trim());
				if (null != pinCode && !"".equals(pinCode)) {
					Map<String, String> localityMap = (HashMap<String, String>) newConnectionDAO
							.getLocality(pinCode.trim());
					session.put("localityMap", localityMap);
				}
				locality = (null == newConnectionDAFDetailsContainer
						.getLocality() ? "" : newConnectionDAFDetailsContainer
						.getLocality().trim());
				if (null != locality && !"".equals(locality)) {
					Map<String, String> subLocalityMap = (HashMap<String, String>) newConnectionDAO
							.getSubLocality(locality.trim());
					session.put("subLocalityMap", subLocalityMap);
				}
				subLocality = (null == newConnectionDAFDetailsContainer
						.getSubLocality() ? ""
						: newConnectionDAFDetailsContainer.getSubLocality()
								.trim());
				jjrHidden = ((null != newConnectionDAFDetailsContainer
						.getJjrColony() && "Y"
						.equalsIgnoreCase(newConnectionDAFDetailsContainer
								.getJjrColony().trim())) ? newConnectionDAFDetailsContainer
						.getJjrColony().trim()
						: "");
				propertyType = (null == newConnectionDAFDetailsContainer
						.getPropertyType() ? ""
						: newConnectionDAFDetailsContainer.getPropertyType()
								.trim());
				noOfFloors = (null == newConnectionDAFDetailsContainer
						.getNoOfFloors() ? ""
						: newConnectionDAFDetailsContainer.getNoOfFloors()
								.trim());
				/* Start: Added By Diksha Mukherjee as per DJB-4211 */
				noOfBorewells = (null == newConnectionDAFDetailsContainer
						.getNoOfBorewells() ? ""
						: newConnectionDAFDetailsContainer.getNoOfBorewells()
								.trim());
				/* End: Added By Diksha Mukherjee as per DJB-4211 */
				noOfRooms = (null == newConnectionDAFDetailsContainer
						.getNoOfRooms() ? "" : newConnectionDAFDetailsContainer
						.getNoOfRooms().trim());
				noOfBeds = (null == newConnectionDAFDetailsContainer
						.getNoOfBeds() ? "" : newConnectionDAFDetailsContainer
						.getNoOfBeds().trim());
				functionSite = (null == newConnectionDAFDetailsContainer
						.getFunctionSite() ? ""
						: newConnectionDAFDetailsContainer.getFunctionSite()
								.trim());
				mallCineplex = (null == newConnectionDAFDetailsContainer
						.getMallCineplex() ? ""
						: newConnectionDAFDetailsContainer.getMallCineplex()
								.trim());
				urbanHidden = newConnectionDAFDetailsContainer.getUrban();
				plotArea = (null == newConnectionDAFDetailsContainer
						.getPlotArea() ? "" : newConnectionDAFDetailsContainer
						.getPlotArea().trim());
				builtUpArea = (null == newConnectionDAFDetailsContainer
						.getBuiltUpArea() ? ""
						: newConnectionDAFDetailsContainer.getBuiltUpArea()
								.trim());
				waterConnectionType = (null == newConnectionDAFDetailsContainer
						.getWaterConnectionType() ? ""
						: newConnectionDAFDetailsContainer
								.getWaterConnectionType().trim());
				AppLog.info("\n####################WaterConnectionType is ::::"
						+ waterConnectionType);
				waterConnectionUse = (null == newConnectionDAFDetailsContainer
						.getWaterConnectionUse() ? ""
						: newConnectionDAFDetailsContainer
								.getWaterConnectionUse().trim());
				noOfOccDwellUnits = (null == newConnectionDAFDetailsContainer
						.getNoOfOccDwellUnits() ? ""
						: newConnectionDAFDetailsContainer
								.getNoOfOccDwellUnits().trim());
				noOfUnoccDwellUnits = (null == newConnectionDAFDetailsContainer
						.getNoOfUnoccDwellUnits() ? ""
						: newConnectionDAFDetailsContainer
								.getNoOfUnoccDwellUnits().trim());
				noOfChildren = (null == newConnectionDAFDetailsContainer
						.getNoOfChildren() ? ""
						: newConnectionDAFDetailsContainer.getNoOfChildren()
								.trim());
				noOfAdults = (null == newConnectionDAFDetailsContainer
						.getNoOfAdults() ? ""
						: newConnectionDAFDetailsContainer.getNoOfAdults()
								.trim());
				isDJB = (null == newConnectionDAFDetailsContainer.getIsDJB() ? ""
						: newConnectionDAFDetailsContainer.getIsDJB().trim());
				sizeOfMeter = (null == newConnectionDAFDetailsContainer
						.getSizeOfMeter() ? ""
						: newConnectionDAFDetailsContainer.getSizeOfMeter()
								.trim());
				typeOfMeter = (null == newConnectionDAFDetailsContainer
						.getTypeOfMeter() ? ""
						: newConnectionDAFDetailsContainer.getTypeOfMeter()
								.trim());
				initialRegisterReadDate = (null == newConnectionDAFDetailsContainer
						.getInitialRegisterReadDate() ? ""
						: newConnectionDAFDetailsContainer
								.getInitialRegisterReadDate().trim());
				initialRegisterReadRemark = (null == newConnectionDAFDetailsContainer
						.getInitialRegisterReadRemark() ? ""
						: newConnectionDAFDetailsContainer
								.getInitialRegisterReadRemark().trim());
				initialRegisetrRead = newConnectionDAFDetailsContainer
						.getInitialRegisetrRead();
				averageConsumption = (null == newConnectionDAFDetailsContainer
						.getAverageConsumption() ? ""
						: newConnectionDAFDetailsContainer
								.getAverageConsumption().trim());
				zoneNo = (null == newConnectionDAFDetailsContainer.getZoneNo() ? ""
						: newConnectionDAFDetailsContainer.getZoneNo().trim());
				ArrayList<String> mrNoList = (ArrayList<String>) newConnectionDAO
						.getMrNo(zoneNo.trim(), zroLocation);
				session.put("mrNoList", mrNoList);
				mrNo = (null == newConnectionDAFDetailsContainer.getMrNo() ? ""
						: newConnectionDAFDetailsContainer.getMrNo().trim());
				Map<String, String> areaMap = (HashMap<String, String>) newConnectionDAO
						.getAreaNo(zoneNo.trim(), mrNo.trim(), zroLocation);
				session.put("areaListMap", areaMap);
				areaNo = (null == newConnectionDAFDetailsContainer.getAreaNo() ? ""
						: newConnectionDAFDetailsContainer.getAreaNo().trim());
				openingBalance = newConnectionDAFDetailsContainer
						.getOpeningBalance();
				// applicationPurpose = (null ==
				// newConnectionDAFDetailsContainer.getApplicationPurpose() ? ""
				// : newConnectionDAFDetailsContainer.getApplicationPurpose());
				// if ("SAWATR".equalsIgnoreCase(applicationPurpose)) {
				// newConnectionDAFDetailsContainer
				// .setApplicationPurpose("PERMCONN");
				// } else {
				// newConnectionDAFDetailsContainer
				// .setApplicationPurpose("WATERSEW");
				// }
				applicationPurpose = (null == newConnectionDAFDetailsContainer
						.getSaTypeCode() ? ""
						: newConnectionDAFDetailsContainer.getSaTypeCode()
								.trim());
				wcNo = (null == newConnectionDAFDetailsContainer.getWcNo() ? ""
						: newConnectionDAFDetailsContainer.getWcNo().trim());
			}
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
		}
		AppLog.end();
		return "success";
	}
	
	/**
	 * <p>
	 * This method is used to Process New Connection DAF Details
	 * </p>
	 * 
	 * @return String
	 */
	public String processNewConnectionDAFDetails() {
		String responseMessage = "ERROR:";
		try {
			// System.out.println("entityName::" + entityName
			// + "::fatherHusbandName::" + fatherHusbandName
			// + "::noOfOccDwellUnits::" + noOfOccDwellUnits
			// + "::noOfOccDwellUnits::" + noOfUnoccDwellUnits
			// + "::noOfRooms::" + noOfRooms);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userIdSession = (String) session.get("userId");
			String authCookie = (String) (null != session.get("X-CCB") ? ""
					: session.get("CCB_CRED"));
			if (null != userIdSession && !"".equals(userIdSession.trim())) {
				String dafSequenceArray[] = dafSequence.split(",");
				NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
				if (null != dafSequenceArray && dafSequenceArray.length > 0) {
					for (int i = 0; i < dafSequenceArray.length; i++) {
						dafSequence = dafSequenceArray[i];
						NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = newConnectionDAO
								.populateNewConnectionDAFDetails(dafSequence);
						if (null != newConnectionDAFDetailsContainer) {
							newConnectionDAFDetailsContainer
									.setCreatedBy(userIdSession);
							if (null != newConnectionDAFDetailsContainer
									.getDafId()
									&& !""
											.equals(newConnectionDAFDetailsContainer
													.getDafId().trim())) {
								NewConnectionInterface newConnInterface = new NewConnectionInterfaceImpl();
								HashMap<Object, Object> returnmap = (HashMap<Object, Object>) newConnInterface
										.processNewConnectionDAFDetails(
												newConnectionDAFDetailsContainer,
												authCookie);
								String accountID = (String) returnmap
										.get("accountID");
								AppLog.info("System Generated Account ID::"
										+ accountID);
								if (null != accountID
										&& !"".equalsIgnoreCase(accountID)) {
									newConnectionDAO = new NewConnectionDAO();
									newConnectionDAO
											.updateNewConnectionDAFStatus(
													dafSequence, accountID,
													userIdSession);
									responseMessage = "<br/><center><font color=\"green\" size=\"6\">New Connection Processed Successfully.Please note System Generated Account ID :<br/>"
											+ accountID
											+ "</font></center><br/>";

								} else {
									responseMessage = "<br/><center><font color=\"red\" size=\"5\">There was Problem while Creating Account in the System.</font></center><br/>";
								}

							} else {
								responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
							}
						}
					}
				} else {
					responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
				}
			} else {
				responseMessage = "<font color='red' size='2'><b>SORRY ! Your are Not Authorised to Perform This Action, Please Contact System Administrator.</b></font>";
			}
		} catch (Exception e) {
			responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		return responseMessage;
	}
	
	/**
	 * <p>
	 * This method is used to save New Connection DAF Details to the database.
	 * </p>
	 * 
	 * @return String
	 */
	public String saveNewConnectionDAFDetails() {
		String responseMessage = "ERROR:";
		try {
			// System.out.println("entityName::" + entityName
			// + "::fatherHusbandName::" + fatherHusbandName
			// + "::noOfOccDwellUnits::" + noOfOccDwellUnits
			// + "::noOfOccDwellUnits::" + noOfUnoccDwellUnits
			// + "::noOfRooms::" + noOfRooms);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String createdBy = (String) session.get("userId");
			zroLocation = (String) session.get("zroLocation");
			String savedNewConnectionDAFDetailsFlag = (String) session
					.get(savedNewConnectionDAFDetailsFlagConstant);
			if (!"Y".equalsIgnoreCase(savedNewConnectionDAFDetailsFlag)) {
				NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = new NewConnectionDAFDetailsContainer();

				newConnectionDAFDetailsContainer.setDafId(null == dafId ? ""
						: dafId);
				newConnectionDAFDetailsContainer
						.setEntityName(null == entityName ? "" : entityName);
				newConnectionDAFDetailsContainer
						.setFatherHusbandName(null == fatherHusbandName ? ""
								: fatherHusbandName);
				newConnectionDAFDetailsContainer
						.setPersonIdType((null == personIdType || ""
								.equalsIgnoreCase(personIdType.trim())) ? "DL"
								: personIdType);
				newConnectionDAFDetailsContainer
						.setPersonIdNumber((null == personIdNumber || ""
								.equalsIgnoreCase(personIdNumber.trim())) ? "NA"
								: personIdNumber);

				newConnectionDAFDetailsContainer
						.setHouseNumber((null == houseNumber || ""
								.equalsIgnoreCase(houseNumber.trim())) ? "NA"
								: houseNumber);
				newConnectionDAFDetailsContainer
						.setRoadNumber(null == roadNumber ? "" : roadNumber);
				newConnectionDAFDetailsContainer
						.setSublocality1(null == sublocality1 ? ""
								: sublocality1);
				newConnectionDAFDetailsContainer
						.setSublocality2(null == sublocality2 ? ""
								: sublocality2);
				newConnectionDAFDetailsContainer
						.setSubColony(null == subColony ? "" : subColony);
				newConnectionDAFDetailsContainer
						.setVillage(null == village ? "" : village);
				newConnectionDAFDetailsContainer
						.setKhashraNumber(null == khashraNumber ? ""
								: khashraNumber);
				newConnectionDAFDetailsContainer
						.setSocietyName(null == societyName ? "" : societyName);
				newConnectionDAFDetailsContainer
						.setPinCode(null == pinCode ? "" : pinCode);
				newConnectionDAFDetailsContainer
						.setLocality(null == locality ? "" : locality);
				newConnectionDAFDetailsContainer
						.setSubLocality(null == subLocality ? "" : subLocality);
				newConnectionDAFDetailsContainer.setJjrColony("true"
						.equalsIgnoreCase(jjrColony) ? "Y" : "N");
				newConnectionDAFDetailsContainer
						.setPropertyType(null == propertyType ? ""
								: propertyType);
				
				/* Start: Added By Diksha Mukherjee as per DJB-4211 */
				/*
				 *newConnectionDAFDetailsContainer
					.setNoOfFloors((null == noOfFloors || ""
							.equalsIgnoreCase(noOfFloors.trim())) ? "1"
							: noOfFloors);
				 *  newConnectionDAFDetailsContainer
					.setNoOfBorewells((null == noOfBorewells || ""
							.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
							: noOfBorewells);
				 */
				if(null != waterConnectionType && !"".equalsIgnoreCase(waterConnectionType.trim()) && !DJBConstants.CATIIIA_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim()) && !DJBConstants.CATIIIB_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim())){
					newConnectionDAFDetailsContainer
					.setNoOfFloors((null == noOfFloors || ""
							.equalsIgnoreCase(noOfFloors.trim())) ? "1"
							: noOfFloors);
					newConnectionDAFDetailsContainer
					.setNoOfBorewells((null == noOfBorewells || ""
							.equalsIgnoreCase(noOfBorewells.trim())) ? ""
							: noOfBorewells);
				}else{
					newConnectionDAFDetailsContainer
					.setNoOfFloors((null == noOfFloors || ""
							.equalsIgnoreCase(noOfFloors.trim())) ? ""
							: noOfFloors);
					newConnectionDAFDetailsContainer
					.setNoOfBorewells((null == noOfBorewells || ""
							.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
							: noOfBorewells);
				}
			
				
				/* Ended: Added By Diksha Mukherjee as per DJB-4211 */
				// Setting No Of Rooms,No Of Beds,Function Site as per
				// property Type
				if ("Hotel/Guest Houses".equalsIgnoreCase(propertyType)
						|| "Dharamshalas/Hostels"
								.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer
							.setNoOfRooms((null == noOfRooms || ""
									.equalsIgnoreCase(noOfRooms.trim())) ? ""
									: noOfRooms);
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Hospital/Nursing Home"
						.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer
							.setNoOfBeds((null == noOfBeds || ""
									.equalsIgnoreCase(noOfBeds.trim())) ? ""
									: noOfBeds);
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Banquet Hall".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer
							.setFunctionSite((null == functionSite || ""
									.equalsIgnoreCase(functionSite.trim())) ? ""
									: functionSite);
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Mall/Cineplex".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer
							.setMallCineplex((null == mallCineplex || ""
									.equalsIgnoreCase(mallCineplex.trim())) ? "1"
									: mallCineplex);
				} else {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				}
				newConnectionDAFDetailsContainer.setUrban("true"
						.equalsIgnoreCase(urban) ? "YES" : "NO");
				newConnectionDAFDetailsContainer
						.setPlotArea(null == plotArea ? "" : plotArea);
				newConnectionDAFDetailsContainer
						.setBuiltUpArea(null == builtUpArea ? "" : builtUpArea);
				newConnectionDAFDetailsContainer
						.setWaterConnectionType(null == waterConnectionType ? ""
								: waterConnectionType);
				newConnectionDAFDetailsContainer
						.setWaterConnectionUse(null == waterConnectionUse ? ""
								: waterConnectionUse);
				newConnectionDAFDetailsContainer
						.setNoOfOccDwellUnits((null == noOfOccDwellUnits
								|| ""
										.equalsIgnoreCase(noOfOccDwellUnits
												.trim()) || "NA"
								.equalsIgnoreCase(noOfOccDwellUnits.trim())) ? ""
								: noOfOccDwellUnits);
				newConnectionDAFDetailsContainer
						.setNoOfUnoccDwellUnits((null == noOfUnoccDwellUnits
								|| "".equalsIgnoreCase(noOfUnoccDwellUnits
										.trim()) || "NA"
								.equalsIgnoreCase(noOfUnoccDwellUnits.trim())) ? ""
								: noOfUnoccDwellUnits);
				newConnectionDAFDetailsContainer
						.setNoOfChildren((null == noOfChildren || ""
								.equalsIgnoreCase(noOfChildren.trim())) ? "0"
								: noOfChildren);
				newConnectionDAFDetailsContainer
						.setNoOfAdults((null == noOfAdults || ""
								.equalsIgnoreCase(noOfAdults.trim())) ? "1"
								: noOfAdults);
				newConnectionDAFDetailsContainer.setIsDJB((null == isDJB || ""
						.equalsIgnoreCase(isDJB.trim())) ? "NOTDJB" : isDJB);
				// Start: Changed By Diksha Mukherjee as per Jtrac DJB-4211 on
				// 8th Apr 2016
				/*
				 * newConnectionDAFDetailsContainer .setSizeOfMeter((null ==
				 * sizeOfMeter || "" .equalsIgnoreCase(sizeOfMeter.trim())) ?
				 * "15" : sizeOfMeter); newConnectionDAFDetailsContainer
				 * .setTypeOfMeter(null == typeOfMeter ? "" : typeOfMeter);
				 * newConnectionDAFDetailsContainer
				 * .setInitialRegisterReadDate(null == initialRegisterReadDate ?
				 * "" : initialRegisterReadDate);
				 * newConnectionDAFDetailsContainer
				 * .setInitialRegisterReadRemark(null ==
				 * initialRegisterReadRemark ? "" : initialRegisterReadRemark);
				 */
			
				if (DJBConstants.CATIIIA_CATAGORY_CODE.equalsIgnoreCase(newConnectionDAFDetailsContainer
						.getWaterConnectionType().trim())
						|| DJBConstants.CATIIIB_CATAGORY_CODE
								.equalsIgnoreCase(newConnectionDAFDetailsContainer
										.getWaterConnectionType().trim())) {
					newConnectionDAFDetailsContainer.setSizeOfMeter("");
					newConnectionDAFDetailsContainer.setTypeOfMeter("");
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadDate("");
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadRemark("");
					newConnectionDAFDetailsContainer
							.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
									: initialRegisetrRead);
					// Ended: Changed By Diksha Mukherjee as per Jtrac DJB-4211 on
					// 8th Apr 2016
				} else {
					newConnectionDAFDetailsContainer
							.setSizeOfMeter((null == sizeOfMeter || ""
									.equalsIgnoreCase(sizeOfMeter.trim())) ? "15"
									: sizeOfMeter);
					newConnectionDAFDetailsContainer
							.setTypeOfMeter(null == typeOfMeter ? ""
									: typeOfMeter);
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadDate(null == initialRegisterReadDate ? ""
									: initialRegisterReadDate);
					// Started: Changed By Diksha Mukherjee as per Jtrac DJB-4211 on
					// 8th Apr 2016
					if (null != initialRegisterReadRemark) {
						if (initialRegisterReadRemark.contains(",")) {
							List<String> initialRegisterReadRemarkList = Arrays
									.asList(initialRegisterReadRemark
											.split("\\s*,\\s*"));
							AppLog.info("ArrayList::"
									+ initialRegisterReadRemarkList.toString());
							newConnectionDAFDetailsContainer
									.setInitialRegisterReadRemark(initialRegisterReadRemarkList
											.get(0));
						}else{
							newConnectionDAFDetailsContainer
							.setInitialRegisterReadRemark(initialRegisterReadRemark);
						}
					}
					// Ended: Changed By Diksha Mukherjee as per Jtrac DJB-4211 on
					// 8th Apr 2016
					newConnectionDAFDetailsContainer
							.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
									: initialRegisetrRead);
				}
				AppLog
						.info("\n###############InitialRegisterReadRemark of Action::"
								+ initialRegisterReadRemark);
				AppLog.info("\n###############InitialRegisterReadRemark ::"
						+ newConnectionDAFDetailsContainer
								.getInitialRegisterReadRemark());
				newConnectionDAFDetailsContainer
						.setAverageConsumption(null == averageConsumption ? ""
								: averageConsumption);
				newConnectionDAFDetailsContainer.setZoneNo(null == zoneNo ? ""
						: zoneNo);
				newConnectionDAFDetailsContainer.setMrNo(null == mrNo ? ""
						: mrNo);
				newConnectionDAFDetailsContainer.setAreaNo(null == areaNo ? ""
						: areaNo);
				newConnectionDAFDetailsContainer
						.setOpeningBalance(null == openingBalance ? BigDecimal.ZERO
								: openingBalance);
				if ("SAWATR".equalsIgnoreCase(applicationPurpose)) {
					newConnectionDAFDetailsContainer
							.setApplicationPurpose("PERMCONN");
				}/*Start: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
				else if("GWNPSA".equalsIgnoreCase(applicationPurpose)){
					newConnectionDAFDetailsContainer.setApplicationPurpose("GWNPSA");
				}else if("GWPSA".equalsIgnoreCase(applicationPurpose)){
					newConnectionDAFDetailsContainer.setApplicationPurpose("GWPSA");
				}
				/*End: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
				else {
					newConnectionDAFDetailsContainer
							.setApplicationPurpose("WATERSEW");
				}
				newConnectionDAFDetailsContainer
						.setSaTypeCode(applicationPurpose);
				newConnectionDAFDetailsContainer.setCreatedBy(createdBy);
				newConnectionDAFDetailsContainer.setZroLocation(zroLocation);
				newConnectionDAFDetailsContainer.setWcNo(null == wcNo ? ""
						: wcNo);
				NewConnectionInterface newConnInterface = new NewConnectionInterfaceImpl();
				HashMap<Object, Object> returnmap = (HashMap<Object, Object>) newConnInterface
						.saveNewConnectionDAFDetails(newConnectionDAFDetailsContainer);
				String errorMessage = (String) returnmap.get("errorMessage");
				if (!"DataBaseError".equals(errorMessage)) {
					/*
					 * Start:Commented By Matiur Rahman as per New Process as
					 * Per JTrac DJB-2123 on 27/11/2013
					 */
					// String accountID = (String) returnmap.get("accountID");
					// System.out.println("accountID::" + accountID);
					// AppLog.info("System Generated Account ID::" + accountID);
					// if (null != accountID && !"".equalsIgnoreCase(accountID))
					// {
					// // session.put(savedNewConnectionDAFDetailsFlagConstant,
					// // "Y");
					// responseMessage =
					// "<br/><center><font color=\"green\" size=\"6\">Please note System Generated Account ID :<br/>"
					// + accountID + "</font></center><br/>";
					//
					// } else {
					// // session.put(savedNewConnectionDAFDetailsFlagConstant,
					// // "Y");
					// responseMessage =
					// "<br/><center><font color=\"red\" size=\"5\">There was Problem while Creating Account in the System.</font></center><br/>";
					// }
					/*
					 * End:Commented By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					/*
					 * Start:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
					responseMessage = "<br/><center><font color=\"green\" size=\"3\"><b>New Connection Details Saved Successfully.</b></font></center><br/>";
					/*
					 * End:Added By Matiur Rahman as per New Process as Per
					 * JTrac DJB-2123 on 27/11/2013
					 */
				} else {
					responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
				}
			}
		} catch (Exception e) {
			responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
			// e.printStackTrace();
			AppLog.error(e);
		}
		AppLog.end();
		return responseMessage;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @param applicationPurpose
	 *            the applicationPurpose to set
	 */
	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	/**
	 * @param applicationPurposeMap
	 *            the applicationPurposeMap to set
	 */
	public void setApplicationPurposeMap(
			Map<String, String> applicationPurposeMap) {
		this.applicationPurposeMap = applicationPurposeMap;
	}

	/**
	 * @param areaNo
	 *            the areaNo to set
	 */
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * @param averageConsumption
	 *            the averageConsumption to set
	 */
	public void setAverageConsumption(String averageConsumption) {
		this.averageConsumption = averageConsumption;
	}

	/**
	 * @param builtUpArea
	 *            the builtUpArea to set
	 */
	public void setBuiltUpArea(String builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	/**
	 * @param dafId
	 *            the dafId to set
	 */
	public void setDafId(String dafId) {
		this.dafId = dafId;
	}

	/**
	 * @param dafSequence
	 *            the dafSequence to set
	 */
	public void setDafSequence(String dafSequence) {
		this.dafSequence = dafSequence;
	}

	/**
	 * @param entityName
	 *            the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @param fatherHusbandName
	 *            the fatherHusbandName to set
	 */
	public void setFatherHusbandName(String fatherHusbandName) {
		this.fatherHusbandName = fatherHusbandName;
	}

	/**
	 * @param functionSite
	 *            the functionSite to set
	 */
	public void setFunctionSite(String functionSite) {
		this.functionSite = functionSite;
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
	 * @param initialRegisetrRead
	 *            the initialRegisetrRead to set
	 */
	public void setInitialRegisetrRead(BigDecimal initialRegisetrRead) {
		this.initialRegisetrRead = initialRegisetrRead;
	}

	/**
	 * @param initialRegisterReadDate
	 *            the initialRegisterReadDate to set
	 */
	public void setInitialRegisterReadDate(String initialRegisterReadDate) {
		this.initialRegisterReadDate = initialRegisterReadDate;
	}

	/**
	 * @param initialRegisterReadRemark
	 *            the initialRegisterReadRemark to set
	 */
	public void setInitialRegisterReadRemark(String initialRegisterReadRemark) {
		this.initialRegisterReadRemark = initialRegisterReadRemark;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param isDJB
	 *            the isDJB to set
	 */
	public void setIsDJB(String isDJB) {
		this.isDJB = isDJB;
	}

	/**
	 * @param isDJBEmployeeMap
	 *            the isDJBEmployeeMap to set
	 */
	public void setIsDJBEmployeeMap(Map<String, String> isDJBEmployeeMap) {
		this.isDJBEmployeeMap = isDJBEmployeeMap;
	}

	/**
	 * @param jjrColony
	 *            the jjrColony to set
	 */
	public void setJjrColony(String jjrColony) {
		this.jjrColony = jjrColony;
	}

	/**
	 * @param jjrHidden
	 *            the jjrHidden to set
	 */
	public void setJjrHidden(String jjrHidden) {
		this.jjrHidden = jjrHidden;
	}

	/**
	 * @param khashraNumber
	 *            the khashraNumber to set
	 */
	public void setKhashraNumber(String khashraNumber) {
		this.khashraNumber = khashraNumber;
	}

	/**
	 * @param locality
	 *            the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @param mallCineplex
	 *            the mallCineplex to set
	 */
	public void setMallCineplex(String mallCineplex) {
		this.mallCineplex = mallCineplex;
	}

	/**
	 * @param meterReaderRemarkCodeListMap
	 *            the meterReaderRemarkCodeListMap to set
	 */
	public void setMeterReaderRemarkCodeListMap(
			Map<String, String> meterReaderRemarkCodeListMap) {
		this.meterReaderRemarkCodeListMap = meterReaderRemarkCodeListMap;
	}

	/**
	 * @param meterReadRemarkCodeMap
	 *            the meterReadRemarkCodeMap to set
	 */
	public void setMeterReadRemarkCodeMap(
			Map<String, String> meterReadRemarkCodeMap) {
		this.meterReadRemarkCodeMap = meterReadRemarkCodeMap;
	}

	/**
	 * @param meterTypeListMap
	 *            the meterTypeListMap to set
	 */
	public void setMeterTypeListMap(Map<String, String> meterTypeListMap) {
		this.meterTypeListMap = meterTypeListMap;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param noOfAdults
	 *            the noOfAdults to set
	 */
	public void setNoOfAdults(String noOfAdults) {
		this.noOfAdults = noOfAdults;
	}

	/**
	 * @param noOfBeds
	 *            the noOfBeds to set
	 */
	public void setNoOfBeds(String noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	// Started:By Diksha Mukherjee as per Jtrac DJB-4211 on
	// 8th Apr 2016
	/**
	 * 
	 * @param noOfBorewells
	 */

	public void setNoOfBorewells(String noOfBorewells) {
		this.noOfBorewells = noOfBorewells;
	}
	// Ended: By Diksha Mukherjee as per Jtrac DJB-4211 on
	// 8th Apr 2016
	/**
	 * @param noOfChildren
	 *            the noOfChildren to set
	 */
	public void setNoOfChildren(String noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	/**
	 * @param noOfOccDwellUnits
	 *            the noOfOccDwellUnits to set
	 */
	public void setNoOfOccDwellUnits(String noOfOccDwellUnits) {
		this.noOfOccDwellUnits = noOfOccDwellUnits;
	}

	/**
	 * @param noOfRooms
	 *            the noOfRooms to set
	 */
	public void setNoOfRooms(String noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	/**
	 * @param noOfUnoccDwellUnits
	 *            the noOfUnoccDwellUnits to set
	 */
	public void setNoOfUnoccDwellUnits(String noOfUnoccDwellUnits) {
		this.noOfUnoccDwellUnits = noOfUnoccDwellUnits;
	}

	/**
	 * @param openingBalance
	 *            the openingBalance to set
	 */
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @param personIdNumber
	 *            the personIdNumber to set
	 */
	public void setPersonIdNumber(String personIdNumber) {
		this.personIdNumber = personIdNumber;
	}

	/**
	 * @param personIdType
	 *            the personIdType to set
	 */
	public void setPersonIdType(String personIdType) {
		this.personIdType = personIdType;
	}

	/**
	 * @param personIdTypeListMap
	 *            the personIdTypeListMap to set
	 */
	public void setPersonIdTypeListMap(Map<String, String> personIdTypeListMap) {
		this.personIdTypeListMap = personIdTypeListMap;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @param plotArea
	 *            the plotArea to set
	 */
	public void setPlotArea(String plotArea) {
		this.plotArea = plotArea;
	}

	/**
	 * @param propertyType
	 *            the propertyType to set
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * @param propertyTypeMap
	 *            the propertyTypeMap to set
	 */
	public void setPropertyTypeMap(Map<String, String> propertyTypeMap) {
		this.propertyTypeMap = propertyTypeMap;
	}

	/**
	 * @param roadNumber
	 *            the roadNumber to set
	 */
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	/**
	 * @param saTypeCode
	 *            the saTypeCode to set
	 */
	public void setSaTypeCode(String saTypeCode) {
		this.saTypeCode = saTypeCode;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param sizeOfMeter
	 *            the sizeOfMeter to set
	 */
	public void setSizeOfMeter(String sizeOfMeter) {
		this.sizeOfMeter = sizeOfMeter;
	}

	/**
	 * @param societyName
	 *            the societyName to set
	 */
	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	/**
	 * @param subColony
	 *            the subColony to set
	 */
	public void setSubColony(String subColony) {
		this.subColony = subColony;
	}

	/**
	 * @param subLocality
	 *            the subLocality to set
	 */
	public void setSubLocality(String subLocality) {
		this.subLocality = subLocality;
	}

	/**
	 * @param sublocality1
	 *            the sublocality1 to set
	 */
	public void setSublocality1(String sublocality1) {
		this.sublocality1 = sublocality1;
	}

	/**
	 * @param sublocality2
	 *            the sublocality2 to set
	 */
	public void setSublocality2(String sublocality2) {
		this.sublocality2 = sublocality2;
	}

	/**
	 * @param typeOfMeter
	 *            the typeOfMeter to set
	 */
	public void setTypeOfMeter(String typeOfMeter) {
		this.typeOfMeter = typeOfMeter;
	}

	/**
	 * @param urban
	 *            the urban to set
	 */
	public void setUrban(String urban) {
		this.urban = urban;
	}

	/**
	 * @param urbanHidden
	 *            the urbanHidden to set
	 */
	public void setUrbanHidden(String urbanHidden) {
		this.urbanHidden = urbanHidden;
	}

	/**
	 * @param village
	 *            the village to set
	 */
	public void setVillage(String village) {
		this.village = village;
	}

	/**
	 * @param waterConnectionSizeListMap
	 *            the waterConnectionSizeListMap to set
	 */
	public void setWaterConnectionSizeListMap(
			Map<String, String> waterConnectionSizeListMap) {
		this.waterConnectionSizeListMap = waterConnectionSizeListMap;
	}

	/**
	 * @param waterConnectionType
	 *            the waterConnectionType to set
	 */
	public void setWaterConnectionType(String waterConnectionType) {
		this.waterConnectionType = waterConnectionType;
	}

	/**
	 * @param waterConnectionTypeMap
	 *            the waterConnectionTypeMap to set
	 */
	public void setWaterConnectionTypeMap(
			Map<String, String> waterConnectionTypeMap) {
		this.waterConnectionTypeMap = waterConnectionTypeMap;
	}

	/**
	 * @param waterConnectionUse
	 *            the waterConnectionUse to set
	 */
	public void setWaterConnectionUse(String waterConnectionUse) {
		this.waterConnectionUse = waterConnectionUse;
	}

	/**
	 * @param waterConnectionUseListMap
	 *            the waterConnectionUseListMap to set
	 */
	public void setWaterConnectionUseListMap(
			Map<String, String> waterConnectionUseListMap) {
		this.waterConnectionUseListMap = waterConnectionUseListMap;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
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

	/**
	 * @param zroLocation
	 *            the zroLocation to set
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}

	/**
	 * <p>
	 * This method is used to update New Connection DAF Details to the database.
	 * </p>
	 * 
	 * @return String
	 */
	public String updateNewConnectionDAFDetails() {
		String responseMessage = "ERROR:";
		try {
			// System.out.println("entityName::" + entityName
			// + "::fatherHusbandName::" + fatherHusbandName
			// + "::noOfOccDwellUnits::" + noOfOccDwellUnits
			// + "::noOfOccDwellUnits::" + noOfUnoccDwellUnits
			// + "::noOfRooms::" + noOfRooms + "::jjrColony::" + jjrColony
			// + "::urban::" + urban);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String createdBy = (String) session.get("userId");
			zroLocation = (String) session.get("zroLocation");
			String savedNewConnectionDAFDetailsFlag = (String) session
					.get(savedNewConnectionDAFDetailsFlagConstant);
			if (!"Y".equalsIgnoreCase(savedNewConnectionDAFDetailsFlag)) {
				NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer = new NewConnectionDAFDetailsContainer();
				newConnectionDAFDetailsContainer.setDafSequence(dafSequence);
				newConnectionDAFDetailsContainer.setDafId(null == dafId ? ""
						: dafId);
				newConnectionDAFDetailsContainer
						.setEntityName(null == entityName ? "" : entityName);
				newConnectionDAFDetailsContainer
						.setFatherHusbandName(null == fatherHusbandName ? ""
								: fatherHusbandName);
				newConnectionDAFDetailsContainer
						.setPersonIdType((null == personIdType || ""
								.equalsIgnoreCase(personIdType.trim())) ? "DL"
								: personIdType);
				newConnectionDAFDetailsContainer
						.setPersonIdNumber((null == personIdNumber || ""
								.equalsIgnoreCase(personIdNumber.trim())) ? "NA"
								: personIdNumber);

				newConnectionDAFDetailsContainer
						.setHouseNumber((null == houseNumber || ""
								.equalsIgnoreCase(houseNumber.trim())) ? "NA"
								: houseNumber);
				newConnectionDAFDetailsContainer
						.setRoadNumber(null == roadNumber ? "" : roadNumber);
				newConnectionDAFDetailsContainer
						.setSublocality1(null == sublocality1 ? ""
								: sublocality1);
				newConnectionDAFDetailsContainer
						.setSublocality2(null == sublocality2 ? ""
								: sublocality2);
				newConnectionDAFDetailsContainer
						.setSubColony(null == subColony ? "" : subColony);
				newConnectionDAFDetailsContainer
						.setVillage(null == village ? "" : village);
				newConnectionDAFDetailsContainer
						.setKhashraNumber(null == khashraNumber ? ""
								: khashraNumber);
				newConnectionDAFDetailsContainer
						.setSocietyName(null == societyName ? "" : societyName);
				newConnectionDAFDetailsContainer
						.setPinCode(null == pinCode ? "" : pinCode);
				newConnectionDAFDetailsContainer
						.setLocality(null == locality ? "" : locality);
				newConnectionDAFDetailsContainer
						.setSubLocality(null == subLocality ? "" : subLocality);
				newConnectionDAFDetailsContainer.setJjrColony("true"
						.equalsIgnoreCase(jjrColony) ? "Y" : "N");
				newConnectionDAFDetailsContainer
						.setPropertyType(null == propertyType ? ""
								: propertyType);
				// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
				// Apr 2016
				/*newConnectionDAFDetailsContainer
				.setNoOfFloors((null == noOfFloors || ""
						.equalsIgnoreCase(noOfFloors.trim())) ? "1"
						: noOfFloors);
		
		newConnectionDAFDetailsContainer
				.setNoOfBorewells((null == noOfBorewells || ""
						.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
						: noOfBorewells);*/
				if(null != waterConnectionType && !"".equalsIgnoreCase(waterConnectionType.trim()) && !DJBConstants.CATIIIA_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim()) && !DJBConstants.CATIIIB_CATAGORY_CODE.equalsIgnoreCase(waterConnectionType.trim())){
					newConnectionDAFDetailsContainer
					.setNoOfFloors((null == noOfFloors || ""
							.equalsIgnoreCase(noOfFloors.trim())) ? "1"
							: noOfFloors);
					newConnectionDAFDetailsContainer
					.setNoOfBorewells((null == noOfBorewells || ""
							.equalsIgnoreCase(noOfBorewells.trim())) ? ""
							: noOfBorewells);
				}else{
					newConnectionDAFDetailsContainer
					.setNoOfFloors((null == noOfFloors || ""
							.equalsIgnoreCase(noOfFloors.trim())) ? ""
							: noOfFloors);
					newConnectionDAFDetailsContainer
					.setNoOfBorewells((null == noOfBorewells || ""
							.equalsIgnoreCase(noOfBorewells.trim())) ? "1"
							: noOfBorewells);
				}
				// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
				// Apr 2016
				// Setting No Of Rooms,No Of Beds,Function Site as per
				// property Type
				if ("Hotel/Guest Houses".equalsIgnoreCase(propertyType)
						|| "Dharamshalas/Hostels"
								.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer
							.setNoOfRooms((null == noOfRooms || ""
									.equalsIgnoreCase(noOfRooms.trim())) ? ""
									: noOfRooms);
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Hospital/Nursing Home"
						.equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer
							.setNoOfBeds((null == noOfBeds || ""
									.equalsIgnoreCase(noOfBeds.trim())) ? ""
									: noOfBeds);
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Banquet Hall".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer
							.setFunctionSite((null == functionSite || ""
									.equalsIgnoreCase(functionSite.trim())) ? ""
									: functionSite);
					newConnectionDAFDetailsContainer.setMallCineplex("");
				} else if ("Mall/Cineplex".equalsIgnoreCase(propertyType)) {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer
							.setMallCineplex((null == mallCineplex || ""
									.equalsIgnoreCase(mallCineplex.trim())) ? "1"
									: mallCineplex);
				} else {
					newConnectionDAFDetailsContainer.setNoOfRooms("");
					newConnectionDAFDetailsContainer.setNoOfBeds("");
					newConnectionDAFDetailsContainer.setFunctionSite("");
					newConnectionDAFDetailsContainer.setMallCineplex("");
				}
				newConnectionDAFDetailsContainer.setUrban("true"
						.equalsIgnoreCase(urban) ? "YES" : "NO");
				newConnectionDAFDetailsContainer
						.setPlotArea(null == plotArea ? "" : plotArea);
				newConnectionDAFDetailsContainer
						.setBuiltUpArea(null == builtUpArea ? "" : builtUpArea);
				newConnectionDAFDetailsContainer
						.setWaterConnectionType(null == waterConnectionType ? ""
								: waterConnectionType);
				newConnectionDAFDetailsContainer
						.setWaterConnectionUse(null == waterConnectionUse ? ""
								: waterConnectionUse);
				newConnectionDAFDetailsContainer
						.setNoOfOccDwellUnits((null == noOfOccDwellUnits
								|| ""
										.equalsIgnoreCase(noOfOccDwellUnits
												.trim()) || "NA"
								.equalsIgnoreCase(noOfOccDwellUnits.trim())) ? ""
								: noOfOccDwellUnits);
				newConnectionDAFDetailsContainer
						.setNoOfUnoccDwellUnits((null == noOfUnoccDwellUnits
								|| "".equalsIgnoreCase(noOfUnoccDwellUnits
										.trim()) || "NA"
								.equalsIgnoreCase(noOfUnoccDwellUnits.trim())) ? ""
								: noOfUnoccDwellUnits);
				newConnectionDAFDetailsContainer
						.setNoOfChildren((null == noOfChildren || ""
								.equalsIgnoreCase(noOfChildren.trim())) ? "0"
								: noOfChildren);
				newConnectionDAFDetailsContainer
						.setNoOfAdults((null == noOfAdults || ""
								.equalsIgnoreCase(noOfAdults.trim())) ? "1"
								: noOfAdults);
				newConnectionDAFDetailsContainer.setIsDJB((null == isDJB || ""
						.equalsIgnoreCase(isDJB.trim())) ? "NOTDJB" : isDJB);
				/*
				 * newConnectionDAFDetailsContainer .setSizeOfMeter((null ==
				 * sizeOfMeter || "" .equalsIgnoreCase(sizeOfMeter.trim())) ?
				 * "15" : sizeOfMeter); newConnectionDAFDetailsContainer
				 * .setTypeOfMeter(null == typeOfMeter ? "" : typeOfMeter);
				 * newConnectionDAFDetailsContainer
				 * .setInitialRegisterReadDate(null == initialRegisterReadDate ?
				 * "" : initialRegisterReadDate);
				 * newConnectionDAFDetailsContainer
				 * .setInitialRegisterReadRemark(null ==
				 * initialRegisterReadRemark ? "" : initialRegisterReadRemark);
				 */

				// Added By Diksha Mukherjee on 08-04-2016 as per Jtrac DJB-4211
				if (DJBConstants.CATIIIA_CATAGORY_CODE.equalsIgnoreCase(newConnectionDAFDetailsContainer
						.getWaterConnectionType().trim())
						|| DJBConstants.CATIIIB_CATAGORY_CODE
								.equalsIgnoreCase(newConnectionDAFDetailsContainer
										.getWaterConnectionType().trim())) {
					newConnectionDAFDetailsContainer.setSizeOfMeter("");
					newConnectionDAFDetailsContainer.setTypeOfMeter("");
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadDate("");
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadRemark("");
					newConnectionDAFDetailsContainer
							.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
									: initialRegisetrRead);
					// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
					// Apr 2016
				} else {
					newConnectionDAFDetailsContainer
							.setSizeOfMeter((null == sizeOfMeter || ""
									.equalsIgnoreCase(sizeOfMeter.trim())) ? "15"
									: sizeOfMeter);
					newConnectionDAFDetailsContainer
							.setTypeOfMeter(null == typeOfMeter ? ""
									: typeOfMeter);
					newConnectionDAFDetailsContainer
							.setInitialRegisterReadDate(null == initialRegisterReadDate ? ""
									: initialRegisterReadDate);
					if (null != initialRegisterReadRemark) {
						if (initialRegisterReadRemark.contains(",")) {
							List<String> initialRegisterReadRemarkList = Arrays
									.asList(initialRegisterReadRemark
											.split("\\s*,\\s*"));
							AppLog.info("ArrayList::"
									+ initialRegisterReadRemarkList.toString());
							newConnectionDAFDetailsContainer
									.setInitialRegisterReadRemark(initialRegisterReadRemarkList
											.get(0));
						}else{
							newConnectionDAFDetailsContainer
							.setInitialRegisterReadRemark(initialRegisterReadRemark);
						}
					}
					newConnectionDAFDetailsContainer
							.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
									: initialRegisetrRead);
				}
				AppLog
						.info("\n###############InitialRegisterReadRemark of Action::"
								+ initialRegisterReadRemark);
				AppLog.info("\n###############InitialRegisterReadRemark ::"
						+ newConnectionDAFDetailsContainer.getInitialRegisterReadRemark());
								// newConnectionDAFDetailsContainer
				// .setInitialRegisetrRead(null == initialRegisetrRead ?
				// BigDecimal.ZERO
				// : initialRegisetrRead);

				newConnectionDAFDetailsContainer
						.setInitialRegisetrRead(null == initialRegisetrRead ? BigDecimal.ZERO
								: initialRegisetrRead);
				newConnectionDAFDetailsContainer
						.setAverageConsumption(null == averageConsumption ? ""
								: averageConsumption);
				newConnectionDAFDetailsContainer.setZoneNo(null == zoneNo ? ""
						: zoneNo);
				newConnectionDAFDetailsContainer.setMrNo(null == mrNo ? ""
						: mrNo);
				newConnectionDAFDetailsContainer.setAreaNo(null == areaNo ? ""
						: areaNo);
				newConnectionDAFDetailsContainer
						.setOpeningBalance(null == openingBalance ? BigDecimal.ZERO
								: openingBalance);
				if ("SAWATR".equalsIgnoreCase(applicationPurpose)) {
					newConnectionDAFDetailsContainer
							.setApplicationPurpose("PERMCONN");
				}/*Start: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
				else if("GWNPSA".equalsIgnoreCase(applicationPurpose)){
					newConnectionDAFDetailsContainer.setApplicationPurpose("GWNPSA");
				}else if("GWPSA".equalsIgnoreCase(applicationPurpose)){
					newConnectionDAFDetailsContainer.setApplicationPurpose("GWPSA");
				}
				/*End: Atanu Added the Below code for Ground Water Packaging and Non Packaging as Per jTrac : DJB-4211*/
				else {
					newConnectionDAFDetailsContainer
							.setApplicationPurpose("WATERSEW");
				}
				newConnectionDAFDetailsContainer
						.setSaTypeCode(applicationPurpose);
				newConnectionDAFDetailsContainer.setCreatedBy(createdBy);
				newConnectionDAFDetailsContainer.setZroLocation(zroLocation);
				newConnectionDAFDetailsContainer.setWcNo(null == wcNo ? ""
						: wcNo);
				NewConnectionDAO newConnectionDAO = new NewConnectionDAO();
				int rowsUpdated = newConnectionDAO
						.updateNewConnectionDAFDetails(newConnectionDAFDetailsContainer);
				if (rowsUpdated > 0) {
					responseMessage = "<font color=\"green\"><b>New Connection Details Updated Successfully.</b></font>";
				} else {
					responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
				}
			}
		} catch (Exception e) {
			responseMessage = "<font color='red' size='2'><b>ERROR: There was problem while processing.</b></font>";
			AppLog.error(e);
		}
		AppLog.end();
		return responseMessage;
	}

}
