/************************************************************************************************************
 * @(#) MeterReplacementAction.java   11 Dec 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReplacementDAO;
import com.tcs.djb.dao.NewConnectionDAO;
import com.tcs.djb.facade.MeterReadUploadFacade;
import com.tcs.djb.facade.MeterReplacementFacade;
import com.tcs.djb.interfaces.MeterReadUploadInterface;
import com.tcs.djb.interfaces.MeterReplacementInterface;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.ErrorDetails;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.MeterReadUploadReply;
import com.tcs.djb.model.MeterReadUploadRequest;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.MeterReplacementUploadReply;
import com.tcs.djb.model.SADetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * Action class for MRD Meter Replacement Screen
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 * @history Matiur Rahman Added oldMeterAverageRead on 11-12-2012
 * @history Appended M with kno if badgeNo is NA By Matiur Rahman as per JTrac
 *          #DJB-979 on 22-01-2013
 * @history Modified Meter Replacement Details save functionality on 21-02-2013
 *          by Matiur Rahman for Updating Current Meter Reading contained by the
 *          MRD Container by Meter Replacement Pop Up screen.
 * @history Added currentAverageConsumption,oldSAType by Matiur Rahman on
 *          21-02-2013
 * @history 19-03-2013 Matiur Rahman Added method
 *          getCurrentMeterReadDetailsForConsumer
 *          ,getCurrentMeterReplacementDetailsForConsumer
 *          ,populateMeterReplacementDetail
 *          ,refreshMRDContainer,resetMRDContainer,saveMeterReadDetails,
 * @history 19-03-2013 Matiur Rahman Added variable lastActiveMeterInstalDate
 *          ,lastActiveMeterRead ,lastActiveMeterRemarkCode
 *          ,nextValidMeterInstalldate
 *          ,noOfFloors,oldMeterReadDate,oldSAStartDate,zeroReadRemarkCode
 * @history 19-03-2013 Matiur Rahman modified method ajaxMethod ,execute
 * @history 19-03-2013 Matiur Rahman Added method getPrevMeterReadDetailsForMR
 *          ,getPrevActualMeterReadDetailsForMR
 * @history 17-12-2013 Rajib Hazarika modified the class for Acces Related issue
 *          as per JTrac DJB-2024
 * @history 10-03-2014 Matiur Rahman Implemented functionality as a part of
 *          changes as perJTrac ID# DJB-2024.
 */
@SuppressWarnings("deprecation")
public class MeterReplacementAction extends ActionSupport {
	private static final String optionTagBeginPart1 = "<option value='";
	private static final String optionTagBeginPart2 = "'>";
	private static final String optionTagEnd = "</option>";
	private static final String selectTagEnd = "</select>";
	private static final long serialVersionUID = -2961753266773964519L;
	private String area;
	private String badgeNo;
	private String billRound;

	private String consumerCategory;

	private String consumerType;

	private String createDate;
	private String createdByID;
	private String currentAverageConsumption;
	private String currentMeterReadDate;
	private String currentMeterReadRemarkCode;
	private double currentMeterRegisterRead;
	private String hidAction;
	private InputStream inputStream;
	private String isLNTServiceProvider;
	private String kno;
	private String lastActiveMeterInstalDate;
	private String lastActiveMeterRead;
	private String lastActiveMeterRemarkCode;
	private String lastUpdateDate;
	private String lastUpdatedByID;
	private String manufacturer;
	private String meterInstalDate;
	private String meterReaderName;
	private String meterType;
	private String model;
	private String mrNo;
	private String name;
	private double newAverageConsumption;
	private String nextValidMeterInstalldate;
	private String noOfFloors;
	private double oldMeterAverageRead;
	private String oldMeterReadDate;
	private String oldMeterReadRemarkCode;
	private double oldMeterRegisterRead;
	private String oldSAStartDate;
	private String oldSAType;
	private HttpServletResponse response;
	private String saType;
	private String searchForMR;
	private String seqNo;
	private String vendorName;
	private String waterConnectionSize;
	private String waterConnectionUse;
	private String wcNo;
	private double zeroRead;
	private String zeroReadRemarkCode;
	private String zone;
	private Map<String, String> zoneListMap;
	private String zroLocation;

	/**
	 * <p>
	 * For all ajax call in meter replacement screen
	 * </p>
	 * 
	 * @return string
	 */
	@SuppressWarnings("unchecked")
	public String ajaxMethod() {
		AppLog.begin();
		try {
			// String zoneCode = null;
			// if (null != zone && !"".equals(zone.trim())) {
			// zoneCode = zone.substring(zone.lastIndexOf('(') + 1, zone
			// .lastIndexOf(')'));
			// }
			/*
			 * System.out.println("hidAction>>" + hidAction + ">>seqNo>>" +
			 * seqNo + ">>meterReaderName>>>" + meterReaderName +
			 * ">>billRound>>>>" + billRound + ">>>zone>>" + zone + ">>mrNo>>>"
			 * + mrNo + ">>area>>>" + area + ">>>wcNo>>>" + wcNo + ">>>kno>>>" +
			 * kno + ">>name>>" + name + ">>consumerType>>" + consumerType +
			 * ">>waterConnectionSize>>" + waterConnectionSize +
			 * ">>waterConnectionUse>>" + waterConnectionUse +
			 * ">>lastActiveMeterInstalDate>>" + lastActiveMeterInstalDate +
			 * ">>lastActiveMeterRead>>" + lastActiveMeterRead +
			 * ">>lastActiveMeterRemarkCode>>" + lastActiveMeterRemarkCode +
			 * ">>meterType>>" + meterType + ">>badgeNo>>" + badgeNo +
			 * ">>manufacturer>>" + manufacturer + ">>model>>" + model +
			 * ">>saType>>" + saType + ">>>isLNTServiceProvider>>>" +
			 * isLNTServiceProvider + ">>>meterInstalDate>>>" + meterInstalDate
			 * + ">>zeroReadRemarkCode>>" + zeroReadRemarkCode +
			 * ">>>zeroRead>>>" + zeroRead + ">>>currentMeterReadDate>>>" +
			 * currentMeterReadDate + ">>>currentMeterRegisterRead>>>" +
			 * currentMeterRegisterRead + ">>currentMeterReadRemarkCode>>" +
			 * currentMeterReadRemarkCode + ">>newAverageConsumption>>" +
			 * newAverageConsumption + ">>noOfFloors>>" + noOfFloors +
			 * ">>oldSAType>>" + oldSAType + ">>oldSAStartDate>>" +
			 * oldSAStartDate + ">>oldMeterRegisterRead>>" +
			 * oldMeterRegisterRead + ">>oldMeterReadRemarkCode>>" +
			 * oldMeterReadRemarkCode + ">>oldMeterReadDate>>" +
			 * oldMeterReadDate + ">>oldMeterAverageRead>>" +
			 * oldMeterAverageRead);
			 */
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String thirdPartyRole = PropertyUtil
					.getProperty("THIRD_PARTY_ROLE");
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				inputStream = new StringBufferInputStream("ERROR:"
						+ getText("error.login.expired")
						+ ", Please re login  and try Again!");
				AppLog.end();
				return "login";
			}
			session.remove("DataSaved");
			if ("getConsumerDetailsByKNO".equalsIgnoreCase(hidAction)) {
				// System.out.println("In getConsumerDetailsByKNO");
				// GetterDAO getterDAO = new GetterDAO();
				MeterReplacementDetail meterReplacementDetail = MeterReplacementDAO
						.getConsumerDetailsForMRByKNO(kno, billRound);
				// System.out.println("LastUpdatedByID>>>"
				// + meterReplacementDetail.getLastUpdatedByID());
				// change added by rajib as per the Jtrac DJB-2024
				int flag = 0, flagVendorname = 0;
				/*
				 * System.out.println("Current zone:"+meterReplacementDetail.getZone
				 * ().toUpperCase()); if(null != zoneListMap && "9" != ((String)
				 * session.get("userRoleId"))){
				 * System.out.println("Contains zone:"
				 * +zoneListMap.containsValue(
				 * meterReplacementDetail.getZone().toUpperCase()));
				 * //if(!zoneListMap
				 * .containsValue(meterReplacementDetail.getZone
				 * ().toUpperCase())){ if(null !=
				 * meterReplacementDetail.getZone() ||
				 * "".equalsIgnoreCase(meterReplacementDetail.getZone()))
				 * if(!zoneListMap
				 * .containsKey(meterReplacementDetail.getZone().toUpperCase
				 * ())){ flag=1;
				 * 
				 * } }
				 */
				if (null != meterReplacementDetail) {
					// change added by rajib as per the Jtrac DJB-2024
					// System.out.println("Current zone:"+
					// meterReplacementDetail.getZone().toUpperCase());
					zoneListMap = (Map<String, String>) session
							.get("ZoneListMap");
					// if (null != zoneListMap
					// && "9" != ((String) session.get("userRoleId"))) {
					// if (null != zoneListMap
					// && thirdPartyRole != ((String) session
					// .get("userRoleId"))) {
					if (null != zoneListMap
							&& ((String) session.get("userRoleId") != thirdPartyRole)) {
						// if(!zoneListMap.containsValue(meterReplacementDetail.getZone().toUpperCase())){
						if (null != meterReplacementDetail.getZone()
								|| "".equalsIgnoreCase(meterReplacementDetail
										.getZone())) {
							// System.out.println("Contains zone:" +
							// zoneListMap.containsValue(meterReplacementDetail.getZone().toUpperCase()));
							// System.out.println("true/False:"
							// + zoneListMap
							// .containsKey(meterReplacementDetail
							// .getZone()));
							// if
							// (!zoneListMap.containsKey(meterReplacementDetail.getZone().toUpperCase()))
							// {
							if (!zoneListMap.containsKey(meterReplacementDetail
									.getZone())) {
								flag = 1;
							}
						}
					}
					if (flag == 0) {
						SADetails oldSADetails = MeterReplacementDAO
								.getOldSATypeDetails(kno);
						Map<String, String> returnMap = MeterReplacementDAO
								.getLastActiveMeterDetails(kno);
						StringBuffer responseSB = new StringBuffer();
						responseSB.append("<CKNO>"
								+ meterReplacementDetail.getKno() + "</CKNO>");
						responseSB.append("<CBIR>"
								+ meterReplacementDetail.getBillRound()
								+ "</CBIR>");
						responseSB.append("<ZONE>"
								+ meterReplacementDetail.getZone() + "</ZONE>");
						responseSB.append("<MRNO>"
								+ meterReplacementDetail.getMrNo() + "</MRNO>");
						responseSB.append("<AREA>"
								+ meterReplacementDetail.getArea() + "</AREA>");
						responseSB.append("<WCNO>"
								+ meterReplacementDetail.getWcNo() + "</WCNO>");
						responseSB.append("<NAME>"
								+ meterReplacementDetail.getName() + "</NAME>");
						responseSB.append("<SATP>"
								+ meterReplacementDetail.getSaType()
								+ "</SATP>");
						responseSB.append("<CTYP>"
								+ meterReplacementDetail.getConsumerType()
								+ "</CTYP>");
						responseSB.append("<CCAT>"
								+ meterReplacementDetail.getConsumerCategory()
								+ "</CCAT>");
						responseSB.append("<OAVG>"
								+ meterReplacementDetail
										.getOldMeterAverageRead() + "</OAVG>");
						responseSB.append("<OSAT>"
								+ oldSADetails.getSaTypeCode() + "</OSAT>");
						responseSB.append("<OSAD>"
								+ oldSADetails.getSaStartDate() + "</OSAD>");
						responseSB.append("<LAMD>"
								+ (null != returnMap ? (String) returnMap
										.get("READ_DTTM") : "") + "</LAMD>");
						responseSB.append("<LAMR>"
								+ (null != returnMap ? (String) returnMap
										.get("REG_READING") : "") + "</LAMR>");
						responseSB
								.append("<LAMS>"
										+ (null != returnMap ? (String) returnMap
												.get("READER_REM_CD")
												: "") + "</LAMS>");
						String meterReadDetailsString = getCurrentMeterReadDetailsForConsumer(meterReplacementDetail);
						String meterReplacementDetailString = getCurrentMeterReplacementDetailsForConsumer(meterReplacementDetail);
						// System.out.println("meterReadDetailsString::"
						// + meterReadDetailsString
						// + "\nmeterReplacementDetailString::"
						// + meterReplacementDetailString);
						// Change by Rajib as per Jtrac DJB-2024 on 03-01-2014
						// System.out.println("meterReplacementDetailString:"+
						// meterReplacementDetailString);
						if ("error"
								.equalsIgnoreCase(meterReplacementDetailString)) {
							// System.out.println("Inside If");
							inputStream = new StringBufferInputStream(
									"ERROR:Access Denied");
							AppLog.end();
							return SUCCESS;
						}
						// Change finish by Rajib as per Jtrac DJB-2024 on
						// 03-01-2014
						responseSB.append("<MRDT>"
								+ meterReplacementDetailString + "</MRDT>");
						responseSB.append("<CRDT>" + meterReadDetailsString
								+ "</CRDT>");
						if (flagVendorname == 0) {
							// System.out.println("Flag vendor name:"+
							// flagVendorname);
							inputStream = new StringBufferInputStream(
									responseSB.toString());
							/*
							 * Start:03-03-2014 JTrac ID#DJB-2024 Added by
							 * Matiur Rahman to return from the block and
							 * prevent further execution.
							 */
							AppLog.end();
							return SUCCESS;
							/*
							 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur
							 * Rahman to return from the block and prevent
							 * further execution.
							 */
						} /*
						 * Start:03-03-2014 JTrac ID#DJB-2024 Commented by
						 * Matiur Rahman to Remove Empty else block
						 */
						// else {
						// // System.out.println("Flag vendor name:"
						// // + flagVendorname);
						//
						// }
						/*
						 * End:03-03-2014 JTrac ID#DJB-2024 Commented by Matiur
						 * Rahman to Remove Empty else block
						 */
					} else {

						inputStream = new StringBufferInputStream(
								"Zone Access Denied");
						/*
						 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur
						 * Rahman to return from the block and prevent further
						 * execution.
						 */
						AppLog.end();
						return SUCCESS;
						/*
						 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur
						 * Rahman to return from the block and prevent further
						 * execution.
						 */
					}
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:Invalid KNO");
					/*
					 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
					AppLog.end();
					return SUCCESS;
					/*
					 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
				}
			}
			if ("getConsumerDetailsByZMAW".equalsIgnoreCase(hidAction)) {
				MeterReplacementDetail meterReplacementDetail = MeterReplacementDAO
						.getConsumerDetailsForMRByZMAW(billRound, zone, mrNo,
								area, wcNo);
				if (null != meterReplacementDetail) {
					SADetails oldSADetails = MeterReplacementDAO
							.getOldSATypeDetails(meterReplacementDetail
									.getKno());
					// System.out.println("KNO:"+meterReplacementDetail.getKno()+"OldSAType:"+oldSADetails.getSaTypeCode()+":SAStartDate:"+oldSADetails.getSaStartDate());
					Map<String, String> returnMap = MeterReplacementDAO
							.getLastActiveMeterDetails(meterReplacementDetail
									.getKno());
					StringBuffer responseSB = new StringBuffer();
					responseSB.append("<CKNO>"
							+ meterReplacementDetail.getKno() + "</CKNO>");
					responseSB
							.append("<CBIR>"
									+ meterReplacementDetail.getBillRound()
									+ "</CBIR>");
					responseSB.append("<ZONE>"
							+ meterReplacementDetail.getZone() + "</ZONE>");
					responseSB.append("<MRNO>"
							+ meterReplacementDetail.getMrNo() + "</MRNO>");
					responseSB.append("<AREA>"
							+ meterReplacementDetail.getArea() + "</AREA>");
					responseSB.append("<WCNO>"
							+ meterReplacementDetail.getWcNo() + "</WCNO>");
					responseSB.append("<NAME>"
							+ meterReplacementDetail.getName() + "</NAME>");
					responseSB.append("<SATP>"
							+ meterReplacementDetail.getSaType() + "</SATP>");
					responseSB.append("<CTYP>"
							+ meterReplacementDetail.getConsumerType()
							+ "</CTYP>");
					responseSB.append("<CCAT>"
							+ meterReplacementDetail.getConsumerCategory()
							+ "</CCAT>");
					responseSB.append("<OAVG>"
							+ meterReplacementDetail.getOldMeterAverageRead()
							+ "</OAVG>");
					responseSB.append("<OSAT>" + oldSADetails.getSaTypeCode()
							+ "</OSAT>");
					responseSB.append("<OSAD>" + oldSADetails.getSaStartDate()
							+ "</OSAD>");
					responseSB.append("<LAMD>"
							+ (null != returnMap ? (String) returnMap
									.get("READ_DTTM") : "") + "</LAMD>");
					responseSB.append("<LAMR>"
							+ (null != returnMap ? (String) returnMap
									.get("REG_READING") : "") + "</LAMR>");
					responseSB.append("<LAMS>"
							+ (null != returnMap ? (String) returnMap
									.get("READER_REM_CD") : "") + "</LAMS>");
					String meterReadDetailsString = getCurrentMeterReadDetailsForConsumer(meterReplacementDetail);
					String meterReplacementDetailString = getCurrentMeterReplacementDetailsForConsumer(meterReplacementDetail);
					// System.out.println("meterReadDetailsString::"
					// + meterReadDetailsString
					// + "\nmeterReplacementDetailString::"
					// + meterReplacementDetailString);
					responseSB.append("<MRDT>" + meterReplacementDetailString
							+ "</MRDT>");
					responseSB.append("<CRDT>" + meterReadDetailsString
							+ "</CRDT>");
					inputStream = new StringBufferInputStream(responseSB
							.toString());
					/*
					 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
					AppLog.end();
					return SUCCESS;
					/*
					 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:Invalid Conbination");
					/*
					 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
					AppLog.end();
					return SUCCESS;
					/*
					 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
				}
			}
			if ("getModel".equalsIgnoreCase(hidAction)) {
				Map<String, String> modelMap = (HashMap<String, String>) MeterReplacementDAO
						.getModel(manufacturer);
				StringBuffer modelDropDown = new StringBuffer(512);
				modelDropDown
						.append("<select name=\"modelMR\" id=\"modelMR\" class=\"selectbox\" onchange=\"fnEnableSaveButton();\" onfocus=\"fnCheckManufacturer();\">");
				// System.out.println("size" + modelMap.size());
				if (null != modelMap && modelMap.size() != 1) {
					modelDropDown
							.append("<option value=''>Please Select</option>");
				}
				if (null != modelMap && !modelMap.isEmpty()) {
					for (Entry<String, String> entry : modelMap.entrySet()) {
						modelDropDown.append(optionTagBeginPart1);
						modelDropDown.append(entry.getKey());
						modelDropDown.append(optionTagBeginPart2);
						modelDropDown.append(entry.getValue());
						modelDropDown.append(optionTagEnd);
					}
				}
				modelDropDown.append(selectTagEnd);
				// session.put("modelMap", modelMap);
				inputStream = new StringBufferInputStream(modelDropDown
						.toString());
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
			}
			if ("populateMRNo".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getMRNoListMap(zone);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"mrNoMR\" id=\"mrNoMR\" class=\"selectbox\" onfocus=\"fnCheckZone(this);\" onchange=\"fnGetArea();fnEnableSaveButton();\">");
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
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
			}
			if ("populateArea".equalsIgnoreCase(hidAction)) {
				Map<String, String> returnMap = (HashMap<String, String>) GetterDAO
						.getAreaListMap(zone, mrNo);
				StringBuffer dropDownSB = new StringBuffer(512);
				dropDownSB
						.append("<select name=\"areaMR\" id=\"areaMR\" class=\"selectbox-long\" onfocus=\"fnCheckZoneMRNo(this);\" onchange=\"fnGetConsumerDetailsByZMAW();fnEnableSaveButton();\">");
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
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
			}
			/** Start:01-04-2013 Matiur Rahman Added */
			if ("populatePreviousMeterReadDetails".equalsIgnoreCase(hidAction)) {
				if (null != kno && !"".equals(kno.trim())
						&& null != meterInstalDate
						&& (meterInstalDate.trim()).length() == 10
						&& null != oldSAStartDate
						&& (oldSAStartDate.trim()).length() == 10) {
					StringBuffer responseSB = new StringBuffer();

					String prevMeterReadDetails = getPrevMeterReadDetailsForMR(
							kno.trim(), meterInstalDate.trim(), oldSAStartDate
									.trim());
					responseSB.append("<PMRD>" + prevMeterReadDetails
							+ "</PMRD>");
					String prevActualMeterReadDetails = getPrevActualMeterReadDetailsForMR(
							kno.trim(), meterInstalDate.trim(), oldSAStartDate
									.trim());
					responseSB.append("<PAMR>" + prevActualMeterReadDetails
							+ "</PAMR>");

					inputStream = new StringBufferInputStream(responseSB
							.toString());
					/*
					 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
					AppLog.end();
					return SUCCESS;
					/*
					 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:Invalid KNO");
					/*
					 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
					AppLog.end();
					return SUCCESS;
					/*
					 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman
					 * to return from the block and prevent further execution.
					 */
				}
			}
			/** End:01-04-2013 Matiur Rahman Added */
			if ("saveDetails".equalsIgnoreCase(hidAction)) {
				String finalResponseString = "ERROR:There was a problem at the server end, Please try Again!";
				/**
				 * populate Meter Replacement Details in the
				 * MeterReplacementDetail container.
				 */
				MeterReplacementDetail meterReplacementDetail = populateMeterReplacementDetail();
				// System.out.println("BadgeNo:"+meterReplacementDetail.getBadgeNo());
				/** Save Meter Read Details */
				MeterReadUploadReply meterReadUploadReply = saveMeterReadDetails(meterReplacementDetail);
				// System.out.println("meterReadUploadReply::"
				// + meterReadUploadReply.getResponseStatus());
				/**
				 * If Meter Read Details Successfully Saved save Meter
				 * Replacement Details
				 */
				if (null != meterReadUploadReply
						&& "Successfully Saved."
								.equalsIgnoreCase(meterReadUploadReply
										.getResponseStatus())) {
					if (null != seqNo && !"".equals(seqNo)) {
						refreshMRDContainer(meterReplacementDetail);
					}
					finalResponseString = "<font color='green' size='2'><b>Meter Read Details for KNO: "
							+ kno + " Saved Successfully.</b></font>";
					/** Save Meter Replacement Details */
					MeterReplacementUploadReply meterReplacementUploadReply = saveMeterReplacementDetail(meterReplacementDetail);
					// System.out.println("meterReplacementUploadReply::"
					// + meterReplacementUploadReply.getResponseStatus());
					if (null != meterReplacementUploadReply
							&& "Successfully Saved."
									.equalsIgnoreCase(meterReplacementUploadReply
											.getResponseStatus())) {
						finalResponseString += "<br/><font color='green' size='2'><b>Meter Replacement Details for KNO: "
								+ kno + " Saved Successfully.</b></font>";
						// inputStream = new StringBufferInputStream(
						// "<font color='green' size='2'><b>Meter Replacement Details for KNO: "
						// + kno
						// + " Saved Successfully.</b></font>");
					} else {
						// Reset Meter Read Details
						// HashMap<String, ConsumerDetail> toUpdateMap = new
						// HashMap<String, ConsumerDetail>();
						// ConsumerDetail consumerDetail = new ConsumerDetail();
						// consumerDetail.setKno(meterReplacementDetail.getKno());
						// consumerDetail.setBillRound(meterReplacementDetail
						// .getBillRound());
						// consumerDetail.setUpdatedBy(userId);
						// toUpdateMap.put("consumerDetail", consumerDetail);
						// SetterDAO setterDAO = new SetterDAO();
						// HashMap<String, String> returnMap = (HashMap<String,
						// String>) setterDAO
						// .resetMeterRead(toUpdateMap);
						// String isSuccess = (String)
						// returnMap.get("isSuccess");
						// if ("Y".equalsIgnoreCase(isSuccess)) {
						// AppLog
						// .info("Reset Meter Read details as Meter Replacement Could not be Saved for the Consumer submitted by : "
						// + userId);
						// if (null != seqNo && !"".equals(seqNo)) {
						// resetMRDContainer();
						// }
						// }
						StringBuffer errorMessageSB = new StringBuffer();
						// errorMessageSB
						// .append("<font color='red' size='2'><b>ERROR:"
						// + meterReplacementUploadReply
						// .getResponseStatus() + "</b>");
						errorMessageSB
								.append("<br/><font color='red' size='2'><b>"
										+ meterReplacementUploadReply
												.getResponseStatus() + "</b>");
						ErrorDetails[] errorDetailsArray = meterReadUploadReply
								.getErrorDetails();
						if (null != errorDetailsArray
								&& errorDetailsArray.length > 0) {
							errorMessageSB.append("<ul><b>Reason:</b>");
							for (int i = 0; i < errorDetailsArray.length; i++) {
								errorMessageSB.append("<li>"
										+ errorDetailsArray[i]
												.getErrorDescription()
										+ "</li>");
							}
							errorMessageSB.append("</ul>");
						}
						errorMessageSB.append("</font>");
						finalResponseString += errorMessageSB.toString();
						// inputStream = new StringBufferInputStream(
						// errorMessageSB.toString());
					}
				} else if (null != meterReadUploadReply
						&& !"Successfully Saved."
								.equalsIgnoreCase(meterReadUploadReply
										.getResponseStatus())) {
					StringBuffer errorMessageSB = new StringBuffer();
					errorMessageSB
							.append("<font color='red' size='2'><b>ERROR:"
									+ meterReadUploadReply.getResponseStatus()
									+ "</b>");
					ErrorDetails[] errorDetailsArray = meterReadUploadReply
							.getErrorDetails();
					if (null != errorDetailsArray
							&& errorDetailsArray.length > 0) {
						errorMessageSB.append("<ul><b>Reason:</b>");
						for (int i = 0; i < errorDetailsArray.length; i++) {
							errorMessageSB.append("<li>"
									+ errorDetailsArray[i]
											.getErrorDescription() + "</li>");
						}
						errorMessageSB.append("</ul>");
					}
					errorMessageSB.append("</font>");
					finalResponseString = errorMessageSB.toString();
					// inputStream = new StringBufferInputStream(errorMessageSB
					// .toString());
				}
				// else {
				// inputStream = new StringBufferInputStream(
				// "ERROR:There was a problem at the server end, Please try Again!");
				// }
				inputStream = new StringBufferInputStream(finalResponseString);
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
			}
			if ("resetMRDetails".equalsIgnoreCase(hidAction)) {
				String finalResponseString = "ERROR:There was a problem at the server end, Please try Again!";
				MeterReplacementDetail meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setBillRound(billRound);
				meterReplacementDetail.setWcNo(wcNo);
				meterReplacementDetail.setKno(kno);
				meterReplacementDetail.setName(name);
				meterReplacementDetail.setLastUpdatedByID(userId);
				MeterReadUploadInterface meterReadUploadInterface = new MeterReadUploadFacade();
				if (meterReadUploadInterface
						.resetMeterReadDetail(meterReplacementDetail)) {
					finalResponseString = "<font color='green' size='2'><b>Meter Read Details for KNO: "
							+ kno
							+ " <font color='red'>Deleted Successfully.</font></b></font>";
					if (null != seqNo && !"".equals(seqNo)) {
						resetMRDContainer();
					}
					MeterReplacementInterface meterReplacementInterface = new MeterReplacementFacade();
					if (meterReplacementInterface
							.resetMeterReplacementDetail(meterReplacementDetail)) {
						finalResponseString += "<br/><font color='green' size='2'><b>Meter Replacement Details for KNO: "
								+ kno
								+ " <font color='red'>Deleted Successfully.</font></b></font>";
					} else {
						finalResponseString += "<font color='red' size='2'><b>Meter Replacement Details for KNO: "
								+ kno
								+ " Could not be delete Successfully.</b></font>";
					}
				} else {
					finalResponseString = "<br/><font color='red' size='2'><b>Meter Read Details for KNO: "
							+ kno
							+ " Could not be delete Successfully.</b></font>";
				}
				// MeterReplacementInterface meterReplacementInterface = new
				// MeterReplacementFacade();
				// if (meterReplacementInterface
				// .resetMeterReplacementDetail(meterReplacementDetail)) {
				// finalResponseString +=
				// "<br/><font color='green' size='2'><b>Meter Replacement Details for KNO: "
				// + kno
				// + " <font color='red'>Reset Successfully.</font></b></font>";
				//
				// } else {
				// finalResponseString +=
				// "<font color='red' size='2'><b>Meter Replacement Details for KNO: "
				// + kno
				// + " Could not be reset Successfully.</b></font>";
				// }
				inputStream = new StringBufferInputStream(finalResponseString);
				/*
				 * Start:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
				AppLog.end();
				return SUCCESS;
				/*
				 * End:03-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman to
				 * return from the block and prevent further execution.
				 */
			}
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"ERROR:Meter Replacement Details for KNO: " + kno
							+ "Could not be Saved Successfully.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);
		}
		// System.out.println("inputStream:3:" + inputStream.toString());
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		try {
			/**
			 * Start:Added by Matiur Rahman on 19-03-2013
			 */
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userId");
			String userRole = (String) session.get("userRoleId");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			if (null == session.get("DJBMeterTypeList")) {
				session
						.put("DJBMeterTypeList", GetterDAO
								.getDJBMeterTypeList());
			}
			String thirdPartyRole = PropertyUtil
					.getProperty("THIRD_PARTY_ROLE");
			// System.out.println("Third Party Role:" + thirdPartyRole);

			/*
			 * if (null == session.get("ZoneListMap")) {
			 * session.put("ZoneListMap", GetterDAO.getZoneListMap()); }
			 */
			// Change as per DJB-2024 by Rajib
			// System.out.println("Userid:"+session.get("userId").toString());
			zroLocation = NewConnectionDAO.getZROLocationByUserID(userName);
			// if ("9".equalsIgnoreCase(userRole)) {
			// if (thirdPartyRole.equalsIgnoreCase(userRole)) {
			if (userRole.equalsIgnoreCase(thirdPartyRole)) {
				if (null == session.get("ZoneListMap")) {
					session.put("ZoneListMap", GetterDAO.getZoneListMap());
				}
			} else {
				if (null != zroLocation && !"ABSENT".equals(zroLocation)) {
					this.zoneListMap = NewConnectionDAO
							.getZoneByZROLocation(zroLocation);
					session.remove("zroLocation");
					session.put("zroLocation", zroLocation);
					session.remove("ZoneListMap");
					session.put("ZoneListMap", zoneListMap);
					// System.out.println("Inside If,zrolocation is:"+zroLocation);
				} else {
					session.put("zroLocation", "ABSENT");
					this.zoneListMap = new HashMap<String, String>();
					// session.put("ZoneListMap", GetterDAO.getZoneListMap());
					session.remove("ZoneListMap");
					session.put("ZoneListMap", new HashMap<String, String>());
					// System.out.println("Inside Else,zrolocation is:"+zroLocation);
				}
			}
			if (null == session.get("MRNoListMap")
					|| ((HashMap<String, String>) session.get("MRNoListMap"))
							.isEmpty()) {
				if (null != zone && !"".equals(zone)) {
					session.put("MRNoListMap",
							(HashMap<String, String>) GetterDAO
									.getMRNoListMap(zone));
				} else {
					session.put("MRNoListMap", new HashMap<String, String>());
				}
			}
			// System.out.println("After zroLocation,MRNoListMap"+GetterDAO.getMRNoListMap(zone).size());
			if (null == session.get("AreaListMap")
					|| ((HashMap<String, String>) session.get("AreaListMap"))
							.isEmpty()) {
				if (null != zone && !"".equals(zone) && null != mrNo
						&& !"".equals(mrNo)) {
					session.put("AreaListMap",
							(HashMap<String, String>) GetterDAO.getAreaListMap(
									zone, mrNo));
				} else {
					session.put("AreaListMap", new HashMap<String, String>());
				}
			}
			// if (null == session.get("oldMeterRemarkCodeMap")) {
			session.put("oldMeterRemarkCodeList", MeterReplacementDAO
					.getOldMeterRemarkCodeList());
			// }
			if (null == session.get("MTR_INST_DT_RANGE")) {
				session.put("MTR_INST_DT_RANGE", GetterDAO
						.getDJBParamValue("MTR_INST_DT_RANGE"));
			}
			session.remove("searchForMR");
			// session.remove("MRReview");
			/**
			 * End:Added by Matiur Rahman on 19-03-2013
			 */
			/**
			 * Start:Commented by Matiur Rahman on 19-03-2013
			 */
			/**
			 * Start:Added by Matiur Rahman on 12-02-2013 as per JTrac #DJB-1011
			 */
			// System.out.println("hidAction::" + hidAction + "::kno::" + kno
			// + "::" + billRound);
			// if ("getDetailsForPOPUP".equalsIgnoreCase(hidAction)) {
			// // System.out.println("In getConsumerDetailsByKNO");
			// GetterDAO getterDAO = new GetterDAO();
			// MeterReplacementDetail meterReplacementDetail = getterDAO
			// .getMeterReplacementDetails(kno, billRound);
			//
			// // System.out.println("isSuccess>>>" + isSuccess);
			// if (null != meterReplacementDetail) {
			// this.meterReaderName = meterReplacementDetail
			// .getMeterReaderName();
			// this.zone = meterReplacementDetail.getZone();
			// this.mrNo = meterReplacementDetail.getMrNo();
			// this.area = meterReplacementDetail.getArea();
			// this.wcNo = meterReplacementDetail.getWcNo();
			// this.name = meterReplacementDetail.getName();
			// this.waterConnectionUse = meterReplacementDetail
			// .getWaterConnectionUse();
			// this.waterConnectionSize = meterReplacementDetail
			// .getWaterConnectionSize();
			// this.meterType = meterReplacementDetail.getMeterType();
			// this.badgeNo = meterReplacementDetail.getBadgeNo();
			// this.manufacturer = meterReplacementDetail
			// .getManufacturer();
			// this.model = meterReplacementDetail.getModel();
			// this.saType = meterReplacementDetail.getSaType();
			// this.meterInstalDate = meterReplacementDetail
			// .getMeterInstalDate();
			// this.zeroRead = meterReplacementDetail.getZeroRead();
			// this.currentMeterReadDate = meterReplacementDetail
			// .getCurrentMeterReadDate();
			// this.currentMeterReadRemarkCode = meterReplacementDetail
			// .getCurrentMeterReadRemarkCode();
			// this.currentMeterRegisterRead = meterReplacementDetail
			// .getCurrentMeterRegisterRead();
			// this.currentAverageConsumption = meterReplacementDetail
			// .getCurrentAverageConsumption();
			// this.newAverageConsumption = meterReplacementDetail
			// .getNewAverageConsumption();
			// this.oldMeterReadRemarkCode = meterReplacementDetail
			// .getOldMeterReadRemarkCode();
			// this.oldSAType = meterReplacementDetail.getOldSAType();
			// this.oldMeterRegisterRead = meterReplacementDetail
			// .getOldMeterRegisterRead();
			// this.oldMeterAverageRead = meterReplacementDetail
			// .getOldMeterAverageRead();
			// this.consumerType = meterReplacementDetail
			// .getConsumerType();
			// this.consumerCategory = meterReplacementDetail
			// .getConsumerCategory();
			//
			// }
			// // System.out.println("hidAction>>" + hidAction + ">>seqNo>>"
			// // + seqNo + ">>meterReaderName>>>" + meterReaderName
			// // + ">>billRound>>>>" + billRound + ">>>zone>>" + zone
			// // + ">>mrNo>>>" + mrNo + ">>area>>>" + area
			// // + ">>>wcNo>>>" + wcNo + ">>>kno>>>" + kno + ">>name>>"
			// // + name + ">>consumerType>>" + consumerType
			// // + ">>waterConnectionSize>>" + waterConnectionSize
			// // + ">>waterConnectionUse>>" + waterConnectionUse
			// // + ">>meterType>>" + meterType + ">>badgeNo>>" + badgeNo
			// // + ">>manufacturer>>" + manufacturer + ">>model>>"
			// // + model + ">>saType>>" + saType
			// // + ">>>isLNTServiceProvider>>>" + isLNTServiceProvider
			// // + ">>>meterInstalDate>>>" + meterInstalDate
			// // + ">>>zeroRead>>>" + zeroRead
			// // + ">>>currentMeterReadDate>>>" + currentMeterReadDate
			// // + ">>>currentMeterRegisterRead>>>"
			// // + currentMeterRegisterRead
			// // + ">>currentAverageConsumption>>"
			// // + currentAverageConsumption
			// // + ">>currentMeterReadRemarkCode>>"
			// // + currentMeterReadRemarkCode
			// // + ">>newAverageConsumption>>" + newAverageConsumption
			// // + ">>oldMeterRegisterRead>>" + oldMeterRegisterRead
			// // + ">>oldMeterReadRemarkCode>>" + oldMeterReadRemarkCode
			// // + ">>oldMeterAverageRead>>" + oldMeterAverageRead);
			// }
			/**
			 * End:Added by Matiur Rahman on 12-02-2013 as per JTrac #DJB-1011
			 */
			/**
			 * End:Commented by Matiur Rahman on 19-03-2013
			 */
		} catch (Exception e) {
			AppLog.error(e);
		}
		return "success";
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return the badgeNo
	 */
	public String getBadgeNo() {
		return badgeNo;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the consumerCategory
	 */
	public String getConsumerCategory() {
		return consumerCategory;
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @return the createdByID
	 */
	public String getCreatedByID() {
		return createdByID;
	}

	/**
	 * @return the currentAverageConsumption
	 */
	public String getCurrentAverageConsumption() {
		return currentAverageConsumption;
	}

	/**
	 * @return the currentMeterReadDate
	 */
	public String getCurrentMeterReadDate() {
		return currentMeterReadDate;
	}

	/**
	 * <p>
	 * get Current Meter Read Details For Consumer.
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return
	 */
	public String getCurrentMeterReadDetailsForConsumer(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		StringBuffer returnStringSB = new StringBuffer();
		try {
			// meterReplacementDetail = MeterReplacementDAO
			// .getMeterReadDetailsForConsumer(meterReplacementDetail);
			if (null != meterReplacementDetail
					&& null != meterReplacementDetail.getPreBillStatID()
					&& !"".equalsIgnoreCase(meterReplacementDetail
							.getPreBillStatID())) {
				returnStringSB.append(meterReplacementDetail.getPreBillStatID()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getCurrentMeterReadDate()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getCurrentMeterReadRemarkCode()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getCurrentMeterRegisterRead()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getCurrentAverageConsumption()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getNewAverageConsumption()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getNoOfFloors()
						+ "|");
				// if (null != meterReplacementDetail.getLastUpdatedByID()
				// && "".equalsIgnoreCase(meterReplacementDetail
				// .getLastUpdatedByID())) {
				returnStringSB.append(" by "
						+ meterReplacementDetail.getLastUpdatedByID());
				returnStringSB.append(" on "
						+ meterReplacementDetail.getLastUpdateDate() + "|");
				// }
				returnStringSB.append(meterReplacementDetail
						.getMeterReplacementDetailRemark()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getConsumerType()
						+ "|");
				// System.out.println("returnStringSB::" +
				// returnStringSB.toString());
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnStringSB.toString();
	}

	/**
	 * @return the currentMeterReadRemarkCode
	 */
	public String getCurrentMeterReadRemarkCode() {
		return currentMeterReadRemarkCode;
	}

	/**
	 * @return the currentMeterRegisterRead
	 */
	public double getCurrentMeterRegisterRead() {
		return currentMeterRegisterRead;
	}

	/**
	 * <p>
	 * get Meter Replacement Details For Consumer
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return
	 */
	public String getCurrentMeterReplacementDetailsForConsumer(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		StringBuffer returnStringSB = new StringBuffer();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			meterReplacementDetail = MeterReplacementDAO
					.getMeterReplacementDetailsForConsumer(meterReplacementDetail);
			if (null != meterReplacementDetail
					&& null != meterReplacementDetail.getCreateDate()
					&& !"".equalsIgnoreCase(meterReplacementDetail
							.getCreateDate())) {
				// Change Start by Rajib as per Jtrac DJB-2024 on 03-01-2014
				// System.out.println("Session vendorName:"+
				// session.get("vendorName"));
				String vendorName = null;
				String sessionVendorName = null;
				if (null != meterReplacementDetail.getVendorName()) {
					vendorName = meterReplacementDetail.getVendorName()
							.toString();
				}
				if (null != session.get("vendorName")) {
					sessionVendorName = (String) session.get("vendorName");
				}
				// System.out.println("vendorname"+vendorName+"SessionvendorName"+sessionVendorName);
				if (null == sessionVendorName) {
					// System.out.println("Inside check if null");
					if (null != vendorName) {
						// System.out.println("Error: for Invalid access to kno:");
						return "error";
						// meterReplacementDetail.getvendorName()
					}
				} else {
					if (!sessionVendorName.equalsIgnoreCase(vendorName)) {
						// System.out.println("Errro: for Invalid access to kno:");
						return "error";
					}
				}
				// meterReplacementDetail.getvendorName()
				// Change finish By Rajib as per Jtrac DJB-2024 on 03-01-2014
				returnStringSB.append(meterReplacementDetail
						.getWaterConnectionUse()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getWaterConnectionSize()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getManufacturer()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getModel() + "|");
				returnStringSB.append(meterReplacementDetail.getMeterType()
						+ "|");
				returnStringSB
						.append(meterReplacementDetail.getBadgeNo() + "|");
				returnStringSB.append(meterReplacementDetail.getSaType() + "|");
				returnStringSB.append(meterReplacementDetail
						.getMeterReaderName()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getMeterInstalDate()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getZeroReadRemarkCode()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getMeterReplaceStageID()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getZeroRead()
						+ "|");
				returnStringSB.append(meterReplacementDetail.getOldSAType()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getOldSAStartDate()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getOldMeterReadRemarkCode()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getOldMeterReadDate()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getOldMeterRegisterRead()
						+ "|");
				returnStringSB.append(meterReplacementDetail
						.getOldMeterAverageRead()
						+ "|");
				returnStringSB.append("Created by "
						+ meterReplacementDetail.getCreatedByID() + " on ");
				returnStringSB.append(meterReplacementDetail.getCreateDate());
				if (null != meterReplacementDetail.getLastUpdatedByID()
						&& "".equalsIgnoreCase(meterReplacementDetail
								.getLastUpdatedByID())) {
					returnStringSB.append(" Updated by "
							+ meterReplacementDetail.getLastUpdatedByID());
					returnStringSB.append(" on "
							+ meterReplacementDetail.getLastUpdateDate());
				}
				returnStringSB.append(meterReplacementDetail
						.getMeterReplacementDetailRemark());
				// System.out.println("CurrentMeterReplacementDetailsForConsumer returnStringSB::"
				// + returnStringSB.toString());
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnStringSB.toString();
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
	 * @return the isLNTServiceProvider
	 */
	public String getIsLNTServiceProvider() {
		return isLNTServiceProvider;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the lastActiveMeterInstalDate
	 */
	public String getLastActiveMeterInstalDate() {
		return lastActiveMeterInstalDate;
	}

	/**
	 * @return the lastActiveMeterRead
	 */
	public String getLastActiveMeterRead() {
		return lastActiveMeterRead;
	}

	/**
	 * @return the lastActiveMeterRemarkCode
	 */
	public String getLastActiveMeterRemarkCode() {
		return lastActiveMeterRemarkCode;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return the lastUpdatedByID
	 */
	public String getLastUpdatedByID() {
		return lastUpdatedByID;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @return the meterInstalDate
	 */
	public String getMeterInstalDate() {
		return meterInstalDate;
	}

	/**
	 * @return the meterReaderName
	 */
	public String getMeterReaderName() {
		return meterReaderName;
	}

	/**
	 * @return the meterType
	 */
	public String getMeterType() {
		return meterType;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the newAverageConsumption
	 */
	public double getNewAverageConsumption() {
		return newAverageConsumption;
	}

	/**
	 * @return the nextValidMeterInstalldate
	 */
	public String getNextValidMeterInstalldate() {
		return nextValidMeterInstalldate;
	}

	/**
	 * @return the noOfFloors
	 */
	public String getNoOfFloors() {
		return noOfFloors;
	}

	/**
	 * @return the oldMeterAverageRead
	 */
	public double getOldMeterAverageRead() {
		return oldMeterAverageRead;
	}

	/**
	 * @return the oldMeterReadDate
	 */
	public String getOldMeterReadDate() {
		return oldMeterReadDate;
	}

	/**
	 * @return the oldMeterReadRemarkCode
	 */
	public String getOldMeterReadRemarkCode() {
		return oldMeterReadRemarkCode;
	}

	/**
	 * @return the oldMeterRegisterRead
	 */
	public double getOldMeterRegisterRead() {
		return oldMeterRegisterRead;
	}

	/**
	 * @return the oldSAStartDate
	 */
	public String getOldSAStartDate() {
		return oldSAStartDate;
	}

	/**
	 * @return the oldSAType
	 */
	public String getOldSAType() {
		return oldSAType;
	}

	/**
	 * <p>
	 * Get Previous Actual Meter Read Details For Consumer.
	 * </p>
	 * 
	 * @param kno
	 * @param meterInstallDate
	 * @param oldSAStartDate
	 * @return
	 */
	public String getPrevActualMeterReadDetailsForMR(String kno,
			String meterInstallDate, String oldSAStartDate) {
		AppLog.begin();
		StringBuffer returnStringSB = new StringBuffer();
		try {
			MeterReadDetails meterReadDetails = GetterDAO
					.getPrevActualMeterReadDetailsForMR(kno, meterInstallDate,
							oldSAStartDate);
			returnStringSB.append(kno + "|");
			returnStringSB.append(meterReadDetails.getMeterReadDate() + "|");
			returnStringSB.append(meterReadDetails.getRegRead() + "|");
			returnStringSB.append(meterReadDetails.getReadType() + "|");
			returnStringSB.append(meterReadDetails.getMeterStatus() + "|");
			// System.out.println("PrevActualMeterReadDetailsForConsumer::"
			// + returnStringSB.toString());
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnStringSB.toString();
	}

	/**
	 * <p>
	 * Get Previous Meter Read Details For Consumer.
	 * </p>
	 * 
	 * @param kno
	 * @param meterInstallDate
	 * @param oldSAStartDate
	 * @return
	 */
	public String getPrevMeterReadDetailsForMR(String kno,
			String meterInstallDate, String oldSAStartDate) {
		AppLog.begin();
		StringBuffer returnStringSB = new StringBuffer();
		try {
			MeterReadDetails meterReadDetails = GetterDAO
					.getPrevMeterReadDetailsForMR(kno, meterInstallDate,
							oldSAStartDate);
			returnStringSB.append(kno + "|");
			returnStringSB.append(meterReadDetails.getMeterReadDate() + "|");
			returnStringSB.append(meterReadDetails.getRegRead() + "|");
			returnStringSB.append(meterReadDetails.getReadType() + "|");
			returnStringSB.append(meterReadDetails.getMeterStatus() + "|");
			// System.out.println("PrevMeterReadDetailsForConsumer::"
			// + returnStringSB.toString());

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnStringSB.toString();
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the saType
	 */
	public String getSaType() {
		return saType;
	}

	/**
	 * @return the searchForMR
	 */
	public String getSearchForMR() {
		return searchForMR;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @return the waterConnectionSize
	 */
	public String getWaterConnectionSize() {
		return waterConnectionSize;
	}

	/**
	 * @return the waterConnectionUse
	 */
	public String getWaterConnectionUse() {
		return waterConnectionUse;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @return the zeroRead
	 */
	public double getZeroRead() {
		return zeroRead;
	}

	/**
	 * @return the zeroReadRemarkCode
	 */
	public String getZeroReadRemarkCode() {
		return zeroReadRemarkCode;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @return the zoneListMap
	 */
	public Map<String, String> getZoneListMap() {
		return zoneListMap;
	}

	/**
	 * @return the zroLocation
	 */
	public String getZroLocation() {
		return zroLocation;
	}

	/**
	 * <p>
	 * Populate Meter Replacement Detail.
	 * </p>
	 * 
	 * @return populated MeterReplacementDetail
	 */
	public MeterReplacementDetail populateMeterReplacementDetail() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");
		MeterReplacementDetail meterReplacementDetail = new MeterReplacementDetail();
		meterReplacementDetail.setMeterReaderName(meterReaderName);
		// meterReplacementDetail.setvendorName(vendorName);
		meterReplacementDetail.setBillRound(billRound);
		meterReplacementDetail.setZone(zone);
		meterReplacementDetail.setMrNo(mrNo);
		meterReplacementDetail.setArea(area);
		meterReplacementDetail.setWcNo(wcNo);
		meterReplacementDetail.setKno(kno);
		meterReplacementDetail.setName(name);
		meterReplacementDetail.setConsumerType(consumerType);
		meterReplacementDetail.setConsumerCategory(consumerCategory);
		meterReplacementDetail.setWaterConnectionSize(waterConnectionSize);
		meterReplacementDetail.setWaterConnectionUse(waterConnectionUse);
		meterReplacementDetail
				.setLastActiveMeterInstalDate(lastActiveMeterInstalDate);
		meterReplacementDetail
				.setLastActiveMeterInstalDate(lastActiveMeterInstalDate);
		meterReplacementDetail
				.setNextValidMeterInstalldate(nextValidMeterInstalldate);
		meterReplacementDetail
				.setLastActiveMeterRemarkCode(lastActiveMeterRemarkCode);
		meterReplacementDetail.setManufacturer(manufacturer);
		meterReplacementDetail.setModel(model);
		meterReplacementDetail.setMeterType(meterType);
		meterReplacementDetail.setBadgeNo(badgeNo);
		meterReplacementDetail.setSaType(saType);
		meterReplacementDetail.setIsLNTServiceProvider(isLNTServiceProvider);
		meterReplacementDetail.setMeterInstalDate(meterInstalDate);
		meterReplacementDetail.setZeroReadRemarkCode(zeroReadRemarkCode);
		meterReplacementDetail.setZeroRead(zeroRead);
		meterReplacementDetail.setCurrentMeterReadDate(currentMeterReadDate);
		meterReplacementDetail
				.setCurrentMeterReadRemarkCode(currentMeterReadRemarkCode);
		meterReplacementDetail
				.setCurrentMeterRegisterRead(currentMeterRegisterRead);
		meterReplacementDetail.setNewAverageConsumption(newAverageConsumption);
		meterReplacementDetail.setNoOfFloors(noOfFloors);
		meterReplacementDetail.setOldSAType(oldSAType);
		meterReplacementDetail.setOldSAStartDate(oldSAStartDate);
		meterReplacementDetail
				.setOldMeterReadRemarkCode(oldMeterReadRemarkCode);

		meterReplacementDetail.setOldMeterReadDate(oldMeterReadDate);
		meterReplacementDetail.setOldMeterRegisterRead(oldMeterRegisterRead);
		meterReplacementDetail.setOldMeterAverageRead(oldMeterAverageRead);
		meterReplacementDetail.setCreatedByID(userId);
		meterReplacementDetail.setLastUpdatedByID(userId);
		// Start chnages by Rajib for setting of vendorName as per Jtrac
		// DJB-2024 on 03-01-2014
		if (null != session.get("userId")) {
			meterReplacementDetail.setVendorName((String) session
					.get("vendorName"));
		} else {
			meterReplacementDetail.setVendorName(null);
		}
		// Change by Rajib Finish for setting of vendorName as per Jtrac
		// DJB-2024 on 03-01-2014
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * Refresh MRDContainer stored in the current session.
	 * </p>
	 */
	public void refreshMRDContainer(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");
		// System.out.println("seqNo::" + seqNo);
		MRDContainer mrdContainer = (MRDContainer) session.get("mrdContainer");
		ArrayList<ConsumerDetail> consumerList = null;
		ConsumerDetail consumerDetail = null;
		if (null != mrdContainer) {
			consumerList = (ArrayList<ConsumerDetail>) mrdContainer
					.getConsumerList();
			if (null != consumerList && consumerList.size() > 0) {
				consumerDetail = (ConsumerDetail) consumerList.get(Integer
						.parseInt(seqNo));
				consumerDetail.setUpdatedBy(userId);
				consumerDetail.setKno(kno);
				consumerDetail.setBillRound(billRound);
				consumerDetail.setConsumerStatus(Integer
						.toString(ConsumerStatusLookup.METER_INSTALLED
								.getStatusCode()));
				/**
				 * Start:Added on 21-02-2013 by Matiur Rahman for Updating
				 * Current Meter Reading by Meter Replacement Pop Up screen.
				 */
				consumerDetail.getCurrentMeterRead().setRegRead(
						Double.toString(meterReplacementDetail
								.getCurrentMeterRegisterRead()));
				consumerDetail.getCurrentMeterRead().setMeterReadDate(
						meterReplacementDetail.getCurrentMeterReadDate());
				consumerDetail.getCurrentMeterRead().setMeterStatus(
						meterReplacementDetail.getCurrentMeterReadRemarkCode());
				consumerDetail.getCurrentMeterRead().setAvgRead(
						Double.toString(meterReplacementDetail
								.getNewAverageConsumption()));
				consumerDetail.getCurrentMeterRead().setNoOfFloor(
						meterReplacementDetail.getNoOfFloors());
				/**
				 * End:Added on 21-02-2013 by Matiur Rahman
				 */
				consumerList.set(Integer.parseInt(seqNo), consumerDetail);
				mrdContainer.setConsumerList(consumerList);
				session.remove("mrdContainer");
				session.put("mrdContainer", mrdContainer);
				// inputStream = new StringBufferInputStream(
				// "<div class='search-option' title='Server Message'><font color='green' size='2'><b>Meter Replacement Details for KNO: "
				// + kno + " Saved Successfully.</b></font></div>");
			}
		}
		AppLog.end();
		// return SUCCESS;
	}

	/**
	 * <p>
	 * Reset MRDContainer stored in the current session.
	 * </p>
	 */
	public void resetMRDContainer() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");
		MRDContainer mrdContainer = (MRDContainer) session.get("mrdContainer");
		ArrayList<ConsumerDetail> consumerList = null;
		ConsumerDetail consumerDetail = null;
		if (null != mrdContainer) {
			consumerList = (ArrayList<ConsumerDetail>) mrdContainer
					.getConsumerList();
			if (null != consumerList && consumerList.size() > 0) {
				consumerDetail = (ConsumerDetail) consumerList.get(Integer
						.parseInt(seqNo));
				consumerDetail.setUpdatedBy(userId);
				consumerDetail.setKno(kno);
				consumerDetail.setBillRound(billRound);
				consumerDetail.setConsumerStatus(Integer
						.toString(ConsumerStatusLookup.INITIATED
								.getStatusCode()));
				consumerList.set(Integer.parseInt(seqNo), consumerDetail);
				/**
				 * Start:Added on 21-02-2013 by Matiur Rahman for Updating
				 * Current Meter Reading by Meter Replacement Pop Up screen.
				 */
				consumerDetail.getCurrentMeterRead().setRegRead("");
				consumerDetail.getCurrentMeterRead().setMeterReadDate("");
				consumerDetail.getCurrentMeterRead().setMeterStatus("");
				consumerDetail.getCurrentMeterRead().setAvgRead("");
				consumerDetail.getCurrentMeterRead().setNoOfFloor("");
				/**
				 * End:Added on 21-02-2013 by Matiur Rahman
				 */
				mrdContainer.setConsumerList(consumerList);
				session.remove("mrdContainer");
				session.put("mrdContainer", mrdContainer);
			}
		}
	}

	/**
	 * <p>
	 * Save Meter Read Details.
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return meterReadUploadReply
	 */
	public MeterReadUploadReply saveMeterReadDetails(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		MeterReadUploadReply meterReadUploadReply = null;
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			MeterReadUploadRequest meterReadUploadRequest = new MeterReadUploadRequest();
			MeterReadUploadDetails meterReadUploadDetails = new MeterReadUploadDetails();
			meterReadUploadDetails.setKno(meterReplacementDetail.getKno());
			meterReadUploadDetails.setBillRound(meterReplacementDetail
					.getBillRound());
			meterReadUploadDetails.setConsumerStatus(Integer
					.toString(ConsumerStatusLookup.METER_INSTALLED
							.getStatusCode()));
			meterReadUploadDetails.setMeterReadDate(meterReplacementDetail
					.getCurrentMeterReadDate());
			meterReadUploadDetails.setMeterReadRemark(meterReplacementDetail
					.getCurrentMeterReadRemarkCode());
			meterReadUploadDetails.setMeterRead(Double
					.toString(meterReplacementDetail
							.getCurrentMeterRegisterRead()));
			meterReadUploadDetails
					.setAverageConsumption(Double
							.toString(meterReplacementDetail
									.getNewAverageConsumption()));
			meterReadUploadDetails.setNoOfFloors(meterReplacementDetail
					.getNoOfFloors());
			MeterReadUploadDetails[] meterReadUploadDetailsArray = new MeterReadUploadDetails[1];
			meterReadUploadDetailsArray[0] = meterReadUploadDetails;
			meterReadUploadRequest
					.setMeterReadUploadDetails(meterReadUploadDetailsArray);
			meterReadUploadRequest.setUserId(userId);
			MeterReadUploadFacade meterReadUploadFacade = new MeterReadUploadFacade();
			meterReadUploadReply = meterReadUploadFacade
					.meterReadUpload(meterReadUploadRequest);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return meterReadUploadReply;
	}

	/**
	 * <p>
	 * Save Meter Read Details.
	 * </p>
	 * 
	 * @param meterReplacementDetail
	 * @return meterReadUploadReply
	 */
	public MeterReplacementUploadReply saveMeterReplacementDetail(
			MeterReplacementDetail meterReplacementDetail) {
		AppLog.begin();
		MeterReplacementUploadReply meterReplacementUploadReply = null;
		try {
			MeterReplacementFacade meterReplacementFacade = new MeterReplacementFacade();
			meterReplacementUploadReply = meterReplacementFacade
					.meterReplacementUpload(meterReplacementDetail);
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return meterReplacementUploadReply;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @param badgeNo
	 *            the badgeNo to set
	 */
	public void setBadgeNo(String badgeNo) {
		this.badgeNo = badgeNo;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumerCategory
	 *            the consumerCategory to set
	 */
	public void setConsumerCategory(String consumerCategory) {
		this.consumerCategory = consumerCategory;
	}

	/**
	 * @param consumerType
	 *            the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param createdByID
	 *            the createdByID to set
	 */
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}

	/**
	 * @param currentAverageConsumption
	 *            the currentAverageConsumption to set
	 */
	public void setCurrentAverageConsumption(String currentAverageConsumption) {
		this.currentAverageConsumption = currentAverageConsumption;
	}

	/**
	 * @param currentMeterReadDate
	 *            the currentMeterReadDate to set
	 */
	public void setCurrentMeterReadDate(String currentMeterReadDate) {
		this.currentMeterReadDate = currentMeterReadDate;
	}

	/**
	 * @param currentMeterReadRemarkCode
	 *            the currentMeterReadRemarkCode to set
	 */
	public void setCurrentMeterReadRemarkCode(String currentMeterReadRemarkCode) {
		this.currentMeterReadRemarkCode = currentMeterReadRemarkCode;
	}

	/**
	 * @param currentMeterRegisterRead
	 *            the currentMeterRegisterRead to set
	 */
	public void setCurrentMeterRegisterRead(double currentMeterRegisterRead) {
		this.currentMeterRegisterRead = currentMeterRegisterRead;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param isLNTServiceProvider
	 *            the isLNTServiceProvider to set
	 */
	public void setIsLNTServiceProvider(String isLNTServiceProvider) {
		this.isLNTServiceProvider = isLNTServiceProvider;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param lastActiveMeterInstalDate
	 *            the lastActiveMeterInstalDate to set
	 */
	public void setLastActiveMeterInstalDate(String lastActiveMeterInstalDate) {
		this.lastActiveMeterInstalDate = lastActiveMeterInstalDate;
	}

	/**
	 * @param lastActiveMeterRead
	 *            the lastActiveMeterRead to set
	 */
	public void setLastActiveMeterRead(String lastActiveMeterRead) {
		this.lastActiveMeterRead = lastActiveMeterRead;
	}

	/**
	 * @param lastActiveMeterRemarkCode
	 *            the lastActiveMeterRemarkCode to set
	 */
	public void setLastActiveMeterRemarkCode(String lastActiveMeterRemarkCode) {
		this.lastActiveMeterRemarkCode = lastActiveMeterRemarkCode;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @param lastUpdatedByID
	 *            the lastUpdatedByID to set
	 */
	public void setLastUpdatedByID(String lastUpdatedByID) {
		this.lastUpdatedByID = lastUpdatedByID;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @param meterInstalDate
	 *            the meterInstalDate to set
	 */
	public void setMeterInstalDate(String meterInstalDate) {
		this.meterInstalDate = meterInstalDate;
	}

	/**
	 * @param meterReaderName
	 *            the meterReaderName to set
	 */
	public void setMeterReaderName(String meterReaderName) {
		this.meterReaderName = meterReaderName;
	}

	/**
	 * @param meterType
	 *            the meterType to set
	 */
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param newAverageConsumption
	 *            the newAverageConsumption to set
	 */
	public void setNewAverageConsumption(double newAverageConsumption) {
		this.newAverageConsumption = newAverageConsumption;
	}

	/**
	 * @param nextValidMeterInstalldate
	 *            the nextValidMeterInstalldate to set
	 */
	public void setNextValidMeterInstalldate(String nextValidMeterInstalldate) {
		this.nextValidMeterInstalldate = nextValidMeterInstalldate;
	}

	/**
	 * @param noOfFloors
	 *            the noOfFloors to set
	 */
	public void setNoOfFloors(String noOfFloors) {
		this.noOfFloors = noOfFloors;
	}

	/**
	 * @param oldMeterAverageRead
	 *            the oldMeterAverageRead to set
	 */
	public void setOldMeterAverageRead(double oldMeterAverageRead) {
		this.oldMeterAverageRead = oldMeterAverageRead;
	}

	/**
	 * @param oldMeterReadDate
	 *            the oldMeterReadDate to set
	 */
	public void setOldMeterReadDate(String oldMeterReadDate) {
		this.oldMeterReadDate = oldMeterReadDate;
	}

	/**
	 * @param oldMeterReadRemarkCode
	 *            the oldMeterReadRemarkCode to set
	 */
	public void setOldMeterReadRemarkCode(String oldMeterReadRemarkCode) {
		this.oldMeterReadRemarkCode = oldMeterReadRemarkCode;
	}

	/**
	 * @param oldMeterRegisterRead
	 *            the oldMeterRegisterRead to set
	 */
	public void setOldMeterRegisterRead(double oldMeterRegisterRead) {
		this.oldMeterRegisterRead = oldMeterRegisterRead;
	}

	/**
	 * @param oldSAStartDate
	 *            the oldSAStartDate to set
	 */
	public void setOldSAStartDate(String oldSAStartDate) {
		this.oldSAStartDate = oldSAStartDate;
	}

	/**
	 * @param oldSAType
	 *            the oldSAType to set
	 */
	public void setOldSAType(String oldSAType) {
		this.oldSAType = oldSAType;
	}

	/**
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param saType
	 *            the saType to set
	 */
	public void setSaType(String saType) {
		this.saType = saType;
	}

	/**
	 * @param searchForMR
	 *            the searchForMR to set
	 */
	public void setSearchForMR(String searchForMR) {
		this.searchForMR = searchForMR;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @param waterConnectionSize
	 *            the waterConnectionSize to set
	 */
	public void setWaterConnectionSize(String waterConnectionSize) {
		this.waterConnectionSize = waterConnectionSize;
	}

	/**
	 * @param waterConnectionUse
	 *            the waterConnectionUse to set
	 */
	public void setWaterConnectionUse(String waterConnectionUse) {
		this.waterConnectionUse = waterConnectionUse;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}

	/**
	 * @param zeroRead
	 *            the zeroRead to set
	 */
	public void setZeroRead(double zeroRead) {
		this.zeroRead = zeroRead;
	}

	/**
	 * @param zeroReadRemarkCode
	 *            the zeroReadRemarkCode to set
	 */
	public void setZeroReadRemarkCode(String zeroReadRemarkCode) {
		this.zeroReadRemarkCode = zeroReadRemarkCode;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
	 * @param zoneListMap
	 */
	public void setZoneListMap(Map<String, String> zoneListMap) {
		this.zoneListMap = zoneListMap;
	}

	/**
	 * @param zroLocation
	 */
	public void setZroLocation(String zroLocation) {
		this.zroLocation = zroLocation;
	}
}
