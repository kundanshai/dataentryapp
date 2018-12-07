/************************************************************************************************************
 * @(#) NewConnectionDAFDetailsContainer.java   18 Dec 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.math.BigDecimal;

/**
 * <p>
 * <code>NewConnectionDAFDetailsContainer</code> is the container class which
 * contains all the jsp variables specific for New Connection DAF and the
 * variables needed while invoking the service to create a new connection
 * consumer.
 * </p>
 * 
 * @author Bency (Tata Consultancy Services)
 * @history Matiur Rahman Added mallCineplex on 18-12-2012
 * @history Reshma on 28-04-2016 added the variable {@link #dataSource} to store
 *          account data source if the account has been migrated from other
 *          source, Jtrac DJB-4425.
 * @histroy Diksha Mukherjee Added Borewells on 08-04-2016
 */
public class NewConnectionDAFDetailsContainer {

	private String accountId;
	private String applicationPurpose;
	private String areaNo;
	private String averageConsumption;
	private String builtUpArea;
	private String createdBy;
	private String dafId;
	private String dafSequence;
	private String entityName;
	private String fatherHusbandName;
	private String functionSite;
	private String houseNumber;
	private BigDecimal initialRegisetrRead;
	private String initialRegisterReadDate;
	private String initialRegisterReadRemark;
	private String isDJB;
	private String jjrColony;
	private String khashraNumber;
	private String locality;
	private String mallCineplex;
	private String mrNo;
	private String noOfAdults;
	private String noOfBeds;
	private String noOfChildren;
	private String noOfFloors;
	// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	private String noOfBorewells;
	// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	private String noOfOccDwellUnits;
	private String noOfRooms;
	/**
	 * data source
	 */
	private String dataSource;

	private String noOfUnoccDwellUnits;

	private BigDecimal openingBalance;

	private String personIdNumber;

	private String personIdType;

	private String pinCode;

	private String plotArea;
	private String propertyType;
	private String roadNumber;
	private String saTypeCode;
	private String sequenceNo;
	private String sizeOfMeter;
	private String societyName;
	private String subColony;
	private String subLocality;
	private String sublocality1;
	private String sublocality2;
	private String typeOfMeter;
	private String urban;
	private String village;
	private String waterConnectionType;
	private String waterConnectionUse;
	private String wcNo;
	private String zoneNo;
	private String zroLocation;

	public NewConnectionDAFDetailsContainer() {
		super();

	}

	public NewConnectionDAFDetailsContainer(String accountId,
			String applicationPurpose, String areaNo,
			String averageConsumption, String builtUpArea, String dafId,
			String entityName, String fatherHusbandName, String houseNumber,
			String saTypeCode, BigDecimal initialRegisetrRead,
			String initialRegisterReadDate, String initialRegisterReadRemark,
			String isDJB, String jjrColony, String khashraNumber,
			String locality, String mrNo, String noOfAdults,
			String noOfChildren, String noOfFloors,String noOfBorewells, String noOfOccDwellUnits,
			String noOfRooms, String noOfUnoccDwellUnits,
			BigDecimal openingBalance, String personIdNumber,
			String personIdType, String pinCode, String plotArea,
			String propertyType, String roadNumber, String sequenceNo,
			String sizeOfMeter, String societyName, String subColony,
			String subLocality, String sublocality1, String sublocality2,
			String typeOfMeter, String urban, String village,
			String waterConnectionType, String waterConnectionUse,
			String zoneNo, String dataSource) {
		super();
		this.accountId = accountId;
		this.applicationPurpose = applicationPurpose;
		this.areaNo = areaNo;
		this.averageConsumption = averageConsumption;
		this.builtUpArea = builtUpArea;
		this.entityName = entityName;
		this.dafId = dafId;
		this.fatherHusbandName = fatherHusbandName;
		this.houseNumber = houseNumber;
		this.saTypeCode = saTypeCode;
		this.initialRegisetrRead = initialRegisetrRead;
		this.initialRegisterReadDate = initialRegisterReadDate;
		this.initialRegisterReadRemark = initialRegisterReadRemark;
		this.isDJB = isDJB;
		this.jjrColony = jjrColony;
		this.khashraNumber = khashraNumber;
		this.locality = locality;
		this.mrNo = mrNo;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.noOfFloors = noOfFloors;
		// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on
		// 8th Apr 2016
		this.noOfBorewells=noOfBorewells;
		// Ended: Addded By Diksha Mukherjee as per Jtrac DJB-4211 on
		// 8th Apr 2016
		this.noOfOccDwellUnits = noOfOccDwellUnits;
		this.noOfRooms = noOfRooms;
		this.noOfUnoccDwellUnits = noOfUnoccDwellUnits;
		this.openingBalance = openingBalance;
		this.personIdNumber = personIdNumber;
		this.personIdType = personIdType;
		this.pinCode = pinCode;
		this.plotArea = plotArea;
		this.propertyType = propertyType;
		this.roadNumber = roadNumber;
		this.sequenceNo = sequenceNo;
		this.sizeOfMeter = sizeOfMeter;
		this.societyName = societyName;
		this.subColony = subColony;
		this.subLocality = subLocality;
		this.sublocality1 = sublocality1;
		this.sublocality2 = sublocality2;
		this.typeOfMeter = typeOfMeter;
		this.urban = urban;
		this.village = village;
		this.waterConnectionType = waterConnectionType;
		this.waterConnectionUse = waterConnectionUse;
		this.zoneNo = zoneNo;
		this.dataSource = dataSource;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @return the applicationPurpose
	 */
	public String getApplicationPurpose() {
		return applicationPurpose;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
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
	 * @return the isDJB
	 */
	public String getIsDJB() {
		return isDJB;
	}

	/**
	 * @return the jjrColony
	 */
	public String getJjrColony() {
		return jjrColony;
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
	// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	public String getNoOfBorewells() {
		return noOfBorewells;
	}
	// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016


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
	 * @return the sequenceNo
	 */
	public String getSequenceNo() {
		return sequenceNo;
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
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}

	/**
	 * @return the waterConnectionType
	 */
	public String getWaterConnectionType() {
		return waterConnectionType;
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
	 * @return the dataSource
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
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
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @param isDJB
	 *            the isDJB to set
	 */
	public void setIsDJB(String isDJB) {
		this.isDJB = isDJB;
	}

	/**
	 * @param jjrColony
	 *            the jjrColony to set
	 */
	public void setJjrColony(String jjrColony) {
		this.jjrColony = jjrColony;
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
	// Start: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
	/**
	 * 
	 * @param noOfBorewells
	 * 			the noOfBorewells to set
	 */
	public void setNoOfBorewells(String noOfBorewells) {
		this.noOfBorewells = noOfBorewells;
	}
	// End: Added By Diksha Mukherjee as per Jtrac DJB-4211 on 8th
	// Apr 2016
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
	 * @param sequenceNo
	 *            the sequenceNo to set
	 */
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
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
	 * @param village
	 *            the village to set
	 */
	public void setVillage(String village) {
		this.village = village;
	}

	/**
	 * @param waterConnectionType
	 *            the waterConnectionType to set
	 */
	public void setWaterConnectionType(String waterConnectionType) {
		this.waterConnectionType = waterConnectionType;
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

}
