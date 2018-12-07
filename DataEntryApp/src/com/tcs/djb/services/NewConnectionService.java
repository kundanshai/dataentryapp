/************************************************************************************************************
 * @(#) NewConnectionService.java   13 Dec 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.interfaces.NewConnectionInterface;
import com.tcs.djb.model.NewConnectionDAFDetailsContainer;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to invoke service calls for New Connection DAF. The method
 * saveNewConnectionDAFDetails contains the service call to create an active new
 * connection in CC&B. It returns account ID which is returned as response.
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 * @history Service xml changed By Matiur Rahman on 13-12-2012
 * @history Consumer Name is coming in Society Name issue fixed By Matiur Rahman
 *          on 13-12-2012
 * @history Reshma on 28-04-2016 edited the method
 *          {@link #processNewConnectionDAFDetails(NewConnectionDAFDetailsContainer, String)}
 *          for saving account data source at CCB end, JTrac DJB-4425.
 * @histroy Added no of borewells by Diksha Mukherjee on 8th Apr 2016 as per
 *          JTrac DJB-4211
 */

public class NewConnectionService implements NewConnectionInterface {

	/**
	 * <p>
	 * The method saveNewConnectionDAFDetails contains the service call to
	 * create an active new connection in CC&B. It returns account ID which is
	 * returned as response.
	 * </p>
	 */
	/* (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#saveNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	@Override
	public Map<Object, Object> saveNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String accountID = null;
		HashMap<Object, Object> returnmap = new HashMap<Object, Object>();
		try {
			System.out
					.println("NewConnectionService saveNewConnectionDAFDetails");
			// xml.append("<?xml version=\"1.0\" ?>");
			// xml
			// .append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
			// xml.append("<soapenv:Body >");
			//
			// xml
			// .append("<CM-NewConnectionService xmlns=\"http://oracle.com/CM-NewConnectionService.xsd\">");
			// xml.append("<pageHeader>");
			// xml.append("<seqNumber>"
			// + newConnectionDAFDetailsContainer.getSequenceNo()
			// + "</seqNumber>");
			// xml.append("<personName>"
			// + newConnectionDAFDetailsContainer.getEntityName()
			// + "</personName>");
			// xml.append("<fatherHusbandName>"
			// + newConnectionDAFDetailsContainer.getFatherHusbandName()
			// + "</fatherHusbandName>");
			// xml.append("<houseNo>"
			// + newConnectionDAFDetailsContainer.getHouseNumber()
			// + "</houseNo>");
			// xml.append("<idType>"
			// + newConnectionDAFDetailsContainer.getPersonIdType()
			// + "</idType>");
			// xml.append("<jjrColony>"
			// + newConnectionDAFDetailsContainer.getJjrColony()
			// + "</jjrColony>");
			// xml.append("<khashraNumber>"
			// + newConnectionDAFDetailsContainer.getKhashraNumber()
			// + "</khashraNumber>");
			// xml.append("<locality>"
			// + newConnectionDAFDetailsContainer.getLocality()
			// + "</locality>");
			// xml.append("<noOfFloor>"
			// + newConnectionDAFDetailsContainer.getNoOfFloors()
			// + "</noOfFloor>");
			// xml.append("<personIdNumber>"
			// + newConnectionDAFDetailsContainer.getPersonIdNumber()
			// + "</personIdNumber>");
			// xml.append("<postal>"
			// + newConnectionDAFDetailsContainer.getPinCode()
			// + "</postal>");
			// xml.append("<propertyType>"
			// + newConnectionDAFDetailsContainer.getPropertyType()
			// + "</propertyType>");
			// xml.append("<roadNo>"
			// + newConnectionDAFDetailsContainer.getRoadNumber()
			// + "</roadNo>");
			// xml.append("<societyName>"
			// + newConnectionDAFDetailsContainer.getSocietyName()
			// + "</societyName>");
			// xml.append("<subColony>"
			// + newConnectionDAFDetailsContainer.getSubColony()
			// + "</subColony>");
			// xml.append("<subLocality>"
			// + newConnectionDAFDetailsContainer.getSubLocality()
			// + "</subLocality>");
			// xml.append("<subLocality1>"
			// + newConnectionDAFDetailsContainer.getSublocality1()
			// + "</subLocality1>");
			// xml.append("<subLocality2>"
			// + newConnectionDAFDetailsContainer.getSublocality2()
			// + "</subLocality2>");
			// xml.append("<urban>" +
			// newConnectionDAFDetailsContainer.getUrban()
			// + "</urban>");
			// xml.append("<village>"
			// + newConnectionDAFDetailsContainer.getVillage()
			// + "</village>");
			// xml.append("<appPurpose>"
			// + newConnectionDAFDetailsContainer.getApplicationPurpose()
			// + "</appPurpose>");
			// xml.append("<avgCons>"
			// + newConnectionDAFDetailsContainer.getAverageConsumption()
			// + "</avgCons>");
			// xml.append("<builtUpArea>"
			// + newConnectionDAFDetailsContainer.getBuiltUpArea()
			// + "</builtUpArea>");
			// xml.append("<iniRegRead>"
			// + newConnectionDAFDetailsContainer.getInitialRegisetrRead()
			// + "</iniRegRead>");
			// xml.append("<iniRegReadRemk>"
			// + newConnectionDAFDetailsContainer
			// .getInitialRegisterReadRemark()
			// + "</iniRegReadRemk>");
			// xml.append("<isDjb>" +
			// newConnectionDAFDetailsContainer.getIsDJB()
			// + "</isDjb>");
			// xml.append("<mtrSize>"
			// + newConnectionDAFDetailsContainer.getSizeOfMeter()
			// + "</mtrSize>");
			// xml.append("<mtrType>"
			// + newConnectionDAFDetailsContainer.getTypeOfMeter()
			// + "</mtrType>");
			// xml.append("<noOfAdults>"
			// + newConnectionDAFDetailsContainer.getNoOfAdults()
			// + "</noOfAdults>");
			// xml.append("<noOfChildren>"
			// + newConnectionDAFDetailsContainer.getNoOfChildren()
			// + "</noOfChildren>");
			// xml.append("<noOfOccDwellUnits>"
			// + newConnectionDAFDetailsContainer.getNoOfOccDwellUnits()
			// + "</noOfOccDwellUnits>");
			// xml.append("<noOfRooms>"
			// + newConnectionDAFDetailsContainer.getNoOfRooms()
			// + "</noOfRooms>");
			// xml.append("<noOfUnoccDwellUnits>"
			// + newConnectionDAFDetailsContainer.getNoOfUnoccDwellUnits()
			// + "</noOfUnoccDwellUnits>");
			// xml.append("<openingBalance>"
			// + newConnectionDAFDetailsContainer.getOpeningBalance()
			// + "</openingBalance>");
			// xml.append("<plotArea>"
			// + newConnectionDAFDetailsContainer.getPlotArea()
			// + "</plotArea>");
			// xml.append("<wconType>"
			// + newConnectionDAFDetailsContainer.getWaterConnectionType()
			// + "</wconType>");
			// xml.append("<wconUse>"
			// + newConnectionDAFDetailsContainer.getWaterConnectionUse()
			// + "</wconUse>");
			// xml.append("<zoneNo>"
			// + newConnectionDAFDetailsContainer.getZoneNo()
			// + "</zoneNo>");
			// xml.append("<mrNo>" + newConnectionDAFDetailsContainer.getMrNo()
			// + "</mrNo>");
			// xml.append("<areaNo>"
			// + newConnectionDAFDetailsContainer.getAreaNo()
			// + "</areaNo>");
			// xml.append("<saType>"
			// + newConnectionDAFDetailsContainer.getSaTypeCode()
			// + "</saType>");
			// xml.append("<startDt>"
			// + UtilityForAll
			// .parseDateYYYYMMDD(newConnectionDAFDetailsContainer
			// .getInitialRegisterReadDate())
			// + "</startDt>");
			// xml.append("</pageHeader>");
			// xml.append("<pageBody>");
			// xml.append("<accountId></accountId>");
			// xml.append("</pageBody>");
			// xml.append("</CM-NewConnectionService>");
			// xml.append("</soapenv:Body >");
			// xml.append("</soapenv:Envelope>");
			xml
					.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cm=\"http://oracle.com/CM-NewConnectionService.xsd\">");
			xml.append("<soapenv:Header/>");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM-NewConnectionService>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageHeader>");
			xml.append("<cm:seqNumber>");
			xml.append(newConnectionDAFDetailsContainer.getSequenceNo());
			xml.append("</cm:seqNumber>");
			xml.append("<cm:personName>");
			xml.append(newConnectionDAFDetailsContainer.getEntityName());
			xml.append("</cm:personName>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:fatherHusbandName>");
			xml.append(newConnectionDAFDetailsContainer.getFatherHusbandName());
			xml.append("</cm:fatherHusbandName>");
			xml.append("<cm:houseNo>");
			xml.append(newConnectionDAFDetailsContainer.getHouseNumber());
			xml.append("</cm:houseNo>");
			xml.append("<cm:idType>");
			xml.append(newConnectionDAFDetailsContainer.getPersonIdType());
			xml.append("</cm:idType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:jjrColony>");
			xml.append(newConnectionDAFDetailsContainer.getJjrColony());
			xml.append("</cm:jjrColony>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:khashraNumber>");
			xml.append(newConnectionDAFDetailsContainer.getKhashraNumber());
			xml.append("</cm:khashraNumber>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:locality>");
			xml.append(newConnectionDAFDetailsContainer.getLocality());
			xml.append("</cm:locality>");
			xml.append("<cm:noOfFloor>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfFloors());
			xml.append("</cm:noOfFloor>");
			// Start-Added By Diksha Mukherjee on 8-04-2016 as per JTrac DJB-4211
			xml.append("<cm:noOfBorewells >");
			xml.append(newConnectionDAFDetailsContainer.getNoOfBorewells());
			xml.append("</cm:noOfBorewells>");
			// End-Added By  Diksha Mukherjee on 8-04-2016 as per JTrac DJB-4211
			xml.append("<cm:personIdNumber>");
			xml.append(newConnectionDAFDetailsContainer.getPersonIdNumber());
			xml.append("</cm:personIdNumber>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:postal>");
			xml.append(newConnectionDAFDetailsContainer.getPinCode());
			xml.append("</cm:postal>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:propertyType>");
			xml.append(newConnectionDAFDetailsContainer.getPropertyType());
			xml.append("</cm:propertyType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:roadNo>");
			xml.append(newConnectionDAFDetailsContainer.getRoadNumber());
			xml.append("</cm:roadNo>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:societyName>");
			xml.append(newConnectionDAFDetailsContainer.getSocietyName());
			xml.append("</cm:societyName>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subColony>");
			xml.append(newConnectionDAFDetailsContainer.getSubColony());
			xml.append("</cm:subColony>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality>");
			xml.append(newConnectionDAFDetailsContainer.getSubLocality());
			xml.append("</cm:subLocality>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality1>");
			xml.append(newConnectionDAFDetailsContainer.getSublocality1());
			xml.append("</cm:subLocality1>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality2>");
			xml.append(newConnectionDAFDetailsContainer.getSublocality2());
			xml.append("</cm:subLocality2>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:urban>");
			xml.append(newConnectionDAFDetailsContainer.getUrban());
			xml.append("</cm:urban>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:village>");
			xml.append(newConnectionDAFDetailsContainer.getVillage());
			xml.append("</cm:village>");
			xml.append("<cm:appPurpose>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getApplicationPurpose());
			xml.append("</cm:appPurpose>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:avgCons>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getAverageConsumption());
			xml.append("</cm:avgCons>");
			xml.append("<cm:builtUpArea>");
			xml.append(newConnectionDAFDetailsContainer.getBuiltUpArea());
			xml.append("</cm:builtUpArea>");
			xml.append("<cm:iniRegRead>");
			xml.append(newConnectionDAFDetailsContainer
					.getInitialRegisetrRead());
			xml.append("</cm:iniRegRead>");
			xml.append("<cm:iniRegReadRemk>");
			xml.append(newConnectionDAFDetailsContainer
					.getInitialRegisterReadRemark());
			xml.append("</cm:iniRegReadRemk>");
			xml.append("<cm:isDjb>");
			xml.append(newConnectionDAFDetailsContainer.getIsDJB());
			xml.append("</cm:isDjb>");
			xml.append("<cm:mtrSize>");
			xml.append(newConnectionDAFDetailsContainer.getSizeOfMeter());
			xml.append("</cm:mtrSize>");
			xml.append("<cm:mtrType>");
			xml.append(newConnectionDAFDetailsContainer.getTypeOfMeter());
			xml.append("</cm:mtrType>");
			xml.append("<cm:noOfAdults>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfAdults());
			xml.append("</cm:noOfAdults>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfChildren>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfChildren());
			xml.append("</cm:noOfChildren>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfOccDwellUnits>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfOccDwellUnits());
			xml.append("</cm:noOfOccDwellUnits>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfUnoccDwellUnits>");
			xml.append(newConnectionDAFDetailsContainer
					.getNoOfUnoccDwellUnits());
			xml.append("</cm:noOfUnoccDwellUnits>");
			xml.append("<cm:openingBalance>");
			xml.append(newConnectionDAFDetailsContainer.getOpeningBalance());
			xml.append("</cm:openingBalance>");
			xml.append("<cm:plotArea>");
			xml.append(newConnectionDAFDetailsContainer.getPlotArea());
			xml.append("</cm:plotArea>");
			xml.append("<cm:wconType>");
			xml.append(newConnectionDAFDetailsContainer
					.getWaterConnectionType());
			xml.append("</cm:wconType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:wconUse>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getWaterConnectionUse());
			xml.append("</cm:wconUse>");
			xml.append("<cm:zoneNo>");
			xml.append(newConnectionDAFDetailsContainer.getZoneNo());
			xml.append("</cm:zoneNo>");
			xml.append("<cm:mrNo>");
			xml.append(newConnectionDAFDetailsContainer.getMrNo());
			xml.append("</cm:mrNo>");
			xml.append("<cm:areaNo>");
			xml.append(newConnectionDAFDetailsContainer.getAreaNo());
			xml.append("</cm:areaNo>");
			xml.append("<cm:saType>");
			xml.append(newConnectionDAFDetailsContainer.getSaTypeCode());
			xml.append("</cm:saType>");
			xml.append("<cm:startDate>");
			AppLog.info("\n##############Service Start Date ::"+newConnectionDAFDetailsContainer.getInitialRegisterReadDate());
			if(null != newConnectionDAFDetailsContainer.getInitialRegisterReadDate() && !"".equalsIgnoreCase(newConnectionDAFDetailsContainer.getInitialRegisterReadDate())){
				xml.append(UtilityForAll
						.parseDateYYYYMMDD(newConnectionDAFDetailsContainer
								.getInitialRegisterReadDate()));
			}
			xml.append("</cm:startDate>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:wcNo>");
			xml.append(newConnectionDAFDetailsContainer.getWcNo());
			xml.append("</cm:wcNo>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfRooms>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfRooms());
			xml.append("</cm:noOfRooms>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:functionSite>");
			xml.append(newConnectionDAFDetailsContainer.getFunctionSite());
			xml.append("</cm:functionSite>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfBed>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfBeds());
			xml.append("</cm:noOfBed>");
			// Start-Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			// updated by Bency
			xml.append("<!--Optional:-->");
			xml.append("<cm:mallCineplex>");
			xml.append(newConnectionDAFDetailsContainer.getMallCineplex());
			xml.append("</cm:mallCineplex>");
			xml.append("<cm:dafId>");
			xml.append(newConnectionDAFDetailsContainer.getDafId());
			xml.append("</cm:dafId>");
			xml.append("<cm:createdBy>");
			xml.append(newConnectionDAFDetailsContainer.getCreatedBy());
			xml.append("</cm:createdBy>");
			// End-Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			xml.append("</cm:pageHeader>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageBody>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:accountId>");
			xml.append("</cm:accountId>");
			xml.append("</cm:pageBody>");
			xml.append("</cm:CM-NewConnectionService>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			// System.out.println("XAIHTTP Request xml::\n" + xml.toString());
			// Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			// updated by Bency
			String encodedXML = UtilityForAll.encodeString(xml.toString());
			// System.out.println("XAIHTTP Request xml::encoded\n" +
			// encodedXML);
			AppLog.info("XAIHTTP Request xml::\n" + encodedXML);
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall();
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);

			if (xaiHTTPCallResponse.indexOf("<accountId>") > -1) {
				accountID = xaiHTTPCallResponse.substring(xaiHTTPCallResponse
						.indexOf("<accountId>") + 11, xaiHTTPCallResponse
						.indexOf("</accountId>"));
			}
			returnmap.put("accountID", accountID);

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnmap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.djb.interfaces.NewConnectionInterface#processNewConnectionDAFDetails
	 * (com.tcs.djb.model.NewConnectionDAFDetailsContainer, java.lang.String)
	 */
	@Override
	public Map<Object, Object> processNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer,
			String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String accountID = null;
		HashMap<Object, Object> returnmap = new HashMap<Object, Object>();
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cm=\"http://oracle.com/CM-NewConnectionService.xsd\">");
			xml.append("<soapenv:Header/>");
			xml.append("<soapenv:Body>");
			xml.append("<cm:CM-NewConnectionService>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageHeader>");
			xml.append("<cm:seqNumber>");
			xml.append(newConnectionDAFDetailsContainer.getSequenceNo());
			xml.append("</cm:seqNumber>");
			xml.append("<cm:personName>");
			xml.append(newConnectionDAFDetailsContainer.getEntityName());
			xml.append("</cm:personName>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:fatherHusbandName>");
			xml.append(newConnectionDAFDetailsContainer.getFatherHusbandName());
			xml.append("</cm:fatherHusbandName>");
			xml.append("<cm:houseNo>");
			xml.append(newConnectionDAFDetailsContainer.getHouseNumber());
			xml.append("</cm:houseNo>");
			xml.append("<cm:idType>");
			xml.append(newConnectionDAFDetailsContainer.getPersonIdType());
			xml.append("</cm:idType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:jjrColony>");
			xml.append(newConnectionDAFDetailsContainer.getJjrColony());
			xml.append("</cm:jjrColony>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:khashraNumber>");
			xml.append(newConnectionDAFDetailsContainer.getKhashraNumber());
			xml.append("</cm:khashraNumber>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:locality>");
			xml.append(newConnectionDAFDetailsContainer.getLocality());
			xml.append("</cm:locality>");
			xml.append("<cm:noOfFloor>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfFloors());
			xml.append("</cm:noOfFloor>");
			//Start: Diksha Mukherjee on 8-04-2016 as per JTrac DJB-4211
			xml.append("<cm:noOfBorewells>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfBorewells());
			xml.append("</cm:noOfBorewells>");
		   //Ended: Diksha Mukherjee on 8-04-2016 as per JTrac DJB-4211
			xml.append("<cm:personIdNumber>");
			xml.append(newConnectionDAFDetailsContainer.getPersonIdNumber());
			xml.append("</cm:personIdNumber>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:postal>");
			xml.append(newConnectionDAFDetailsContainer.getPinCode());
			xml.append("</cm:postal>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:propertyType>");
			xml.append(newConnectionDAFDetailsContainer.getPropertyType());
			xml.append("</cm:propertyType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:roadNo>");
			xml.append(newConnectionDAFDetailsContainer.getRoadNumber());
			xml.append("</cm:roadNo>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:societyName>");
			xml.append(newConnectionDAFDetailsContainer.getSocietyName());
			xml.append("</cm:societyName>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subColony>");
			xml.append(newConnectionDAFDetailsContainer.getSubColony());
			xml.append("</cm:subColony>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality>");
			xml.append(newConnectionDAFDetailsContainer.getSubLocality());
			xml.append("</cm:subLocality>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality1>");
			xml.append(newConnectionDAFDetailsContainer.getSublocality1());
			xml.append("</cm:subLocality1>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:subLocality2>");
			xml.append(newConnectionDAFDetailsContainer.getSublocality2());
			xml.append("</cm:subLocality2>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:urban>");
			xml.append(newConnectionDAFDetailsContainer.getUrban());
			xml.append("</cm:urban>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:village>");
			xml.append(newConnectionDAFDetailsContainer.getVillage());
			xml.append("</cm:village>");
			xml.append("<cm:appPurpose>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getApplicationPurpose());
			xml.append("</cm:appPurpose>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:avgCons>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getAverageConsumption());
			xml.append("</cm:avgCons>");
			xml.append("<cm:builtUpArea>");
			xml.append(newConnectionDAFDetailsContainer.getBuiltUpArea());
			xml.append("</cm:builtUpArea>");
			xml.append("<cm:iniRegRead>");
			xml.append(newConnectionDAFDetailsContainer
					.getInitialRegisetrRead());
			xml.append("</cm:iniRegRead>");
			xml.append("<cm:iniRegReadRemk>");
			xml.append(newConnectionDAFDetailsContainer
					.getInitialRegisterReadRemark());
			xml.append("</cm:iniRegReadRemk>");
			xml.append("<cm:isDjb>");
			xml.append(newConnectionDAFDetailsContainer.getIsDJB());
			xml.append("</cm:isDjb>");
			xml.append("<cm:mtrSize>");
			xml.append(newConnectionDAFDetailsContainer.getSizeOfMeter());
			xml.append("</cm:mtrSize>");
			xml.append("<cm:mtrType>");
			xml.append(newConnectionDAFDetailsContainer.getTypeOfMeter());
			xml.append("</cm:mtrType>");
			xml.append("<cm:noOfAdults>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfAdults());
			xml.append("</cm:noOfAdults>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfChildren>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfChildren());
			xml.append("</cm:noOfChildren>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfOccDwellUnits>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfOccDwellUnits());
			xml.append("</cm:noOfOccDwellUnits>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfUnoccDwellUnits>");
			xml.append(newConnectionDAFDetailsContainer
					.getNoOfUnoccDwellUnits());
			xml.append("</cm:noOfUnoccDwellUnits>");
			xml.append("<cm:openingBalance>");
			xml.append(newConnectionDAFDetailsContainer.getOpeningBalance());
			xml.append("</cm:openingBalance>");
			xml.append("<cm:plotArea>");
			xml.append(newConnectionDAFDetailsContainer.getPlotArea());
			xml.append("</cm:plotArea>");
			xml.append("<cm:wconType>");
			xml.append(newConnectionDAFDetailsContainer
					.getWaterConnectionType());
			xml.append("</cm:wconType>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:wconUse>");
			xml
					.append(newConnectionDAFDetailsContainer
							.getWaterConnectionUse());
			xml.append("</cm:wconUse>");
			xml.append("<cm:zoneNo>");
			xml.append(newConnectionDAFDetailsContainer.getZoneNo());
			xml.append("</cm:zoneNo>");
			xml.append("<cm:mrNo>");
			xml.append(newConnectionDAFDetailsContainer.getMrNo());
			xml.append("</cm:mrNo>");
			xml.append("<cm:areaNo>");
			xml.append(newConnectionDAFDetailsContainer.getAreaNo());
			xml.append("</cm:areaNo>");
			xml.append("<cm:saType>");
			xml.append(newConnectionDAFDetailsContainer.getSaTypeCode());
			xml.append("</cm:saType>");
			xml.append("<cm:startDt>");
			AppLog.info("\n##############Service Start Date ::"+newConnectionDAFDetailsContainer.getInitialRegisterReadDate());
			if(null != newConnectionDAFDetailsContainer.getInitialRegisterReadDate() && !"".equalsIgnoreCase(newConnectionDAFDetailsContainer.getInitialRegisterReadDate())){
				xml.append(UtilityForAll
					.parseDateYYYYMMDD(newConnectionDAFDetailsContainer
							.getInitialRegisterReadDate()));
			}
			xml.append("</cm:startDt>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:wcNo>");
			xml.append(newConnectionDAFDetailsContainer.getWcNo());
			xml.append("</cm:wcNo>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfRooms>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfRooms());
			xml.append("</cm:noOfRooms>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:functionSite>");
			xml.append(newConnectionDAFDetailsContainer.getFunctionSite());
			xml.append("</cm:functionSite>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:noOfBed>");
			xml.append(newConnectionDAFDetailsContainer.getNoOfBeds());
			xml.append("</cm:noOfBed>");
			// Start-Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			// updated by Bency
			xml.append("<!--Optional:-->");
			xml.append("<cm:mallCineplex>");
			xml.append(newConnectionDAFDetailsContainer.getMallCineplex());
			xml.append("</cm:mallCineplex>");
			xml.append("<cm:dafId>");
			xml.append(newConnectionDAFDetailsContainer.getDafId());
			xml.append("</cm:dafId>");
			xml.append("<cm:createdBy>");
			xml.append(newConnectionDAFDetailsContainer.getCreatedBy());
			xml.append("</cm:createdBy>");
			/**
			 * Start : Reshma on 28-04-2016 added the below part for saving
			 * account data source at CCB end, JTrac DJB-4425.
			 */
			if (null != newConnectionDAFDetailsContainer.getDataSource()
					&& !"".equalsIgnoreCase(newConnectionDAFDetailsContainer
							.getDataSource().trim())) {
				xml.append("<cm:srcLocation>");
				xml.append(newConnectionDAFDetailsContainer.getDataSource()
						.trim());
				xml.append("</cm:srcLocation>");
			}
			/**
			 * End : Reshma on 28-04-2016 added the below part for saving
			 * account data source at CCB end, JTrac DJB-4425.
			 */
			// End-Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			xml.append("</cm:pageHeader>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:pageBody>");
			xml.append("<!--Optional:-->");
			xml.append("<cm:accountId>");
			xml.append("</cm:accountId>");
			xml.append("</cm:pageBody>");
			xml.append("</cm:CM-NewConnectionService>");
			xml.append("</soapenv:Body>");
			xml.append("</soapenv:Envelope>");
			// System.out.println("XAIHTTP Request xml::\n" + xml.toString());
			// Added By Matiur Rahman on 18-12-2012 as per JTrac DJB-595
			// updated by Bency
			String encodedXML = UtilityForAll.encodeString(xml.toString());
			// System.out.println("XAIHTTP Request xml::encoded\n" +
			// encodedXML);
			AppLog.info("XAIHTTP Request xml::\n" + encodedXML);
			XAIHTTPCall xaiHTTPCall = new XAIHTTPCall();
			// XAIHTTPCall xaiHTTPCall = new XAIHTTPCall(authCookie);
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);

			if (xaiHTTPCallResponse.indexOf("<accountId>") > -1) {
				accountID = xaiHTTPCallResponse.substring(xaiHTTPCallResponse
						.indexOf("<accountId>") + 11, xaiHTTPCallResponse
						.indexOf("</accountId>"));
			}
			returnmap.put("accountID", accountID);

		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return returnmap;
	}

	/* (non-Javadoc)
	 * @see com.tcs.djb.interfaces.NewConnectionInterface#updateNewConnectionDAFDetails(com.tcs.djb.model.NewConnectionDAFDetailsContainer)
	 */
	@Override
	public int updateNewConnectionDAFDetails(
			NewConnectionDAFDetailsContainer newConnectionDAFDetailsContainer) {
		System.out
				.println("NewConnectionService updateNewConnectionDAFDetails");
		return 0;
	}
}
