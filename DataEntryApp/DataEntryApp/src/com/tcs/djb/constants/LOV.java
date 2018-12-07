/************************************************************************************************************
 * @(#) LOV.java   18 Apr 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.NewConnFileEntryDAO;
import com.tcs.djb.model.AreaDetails;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.model.ManufacturerDetailsLookup;
import com.tcs.djb.model.MeterTypeLookup;
import com.tcs.djb.model.ModelDetailsLookup;
import com.tcs.djb.model.SATypeDetails;
import com.tcs.djb.util.AppLog;

/**
 *<p>
 * This class contains all the LOV(List of Values) that needs to be loaded on
 * application startup.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 18-04-2013.
 * @history: On 22-JAN-2016 Rajib Hazarika Added variables and methods to
 *           populate Drop down values in New Connection File Entry Screen as
 *           per JTrac DJB-4313
 * @history: On 13-FEB-2016 Rajib Hazarika Added variables and methods to
 *           populate Drop down values in KNO and Bill Generation Screen as per
 *           JTrac DJB-4313
 * @history: On 14-JUNE-2016 Madhuri Added variables and methods to
 *           to get open bill round ids as per 
 *           JTrac DJB-4464 and open project id 1208
 */
public class LOV {
	/**
	 * <p>
	 * This method is used to get Area Details from Portal database as per JTrac
	 * DJB-4313
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Rajib Hazarika
	 * @since 20-JAN-2016
	 */
	private static String getLocalityDetails() throws Exception {
		String areaJson = null;
		List<AreaDetails> areaList = new ArrayList<AreaDetails>();
		areaList = GetterDAO.getAreaDetails();
		if (null != areaList) {
			Gson gson = new Gson();
			areaJson = gson.toJson(areaList);
		}
		return areaJson;
	}

	/*
	 * variable to store <code> AREA_DETAILS </code>
	 */
	String areaDetails;
	List<String> areaList = null;
	List<String> billWindowList = null;
	/*
	 * variable to store List map values for DJB Employee Rebate Applicable
	 */
	Map<String, String> djbEmpCharValListMap;

	List<MeterTypeLookup> DJBMeterTypeList = null;

	/*
	 * variable to store List map values for If all the documents facilitated by
	 * user are verified
	 */
	Map<String, String> isDocVerifiedCharValListMap;

	List<ManufacturerDetailsLookup> manufacturerDetailsList = null;

	List<String> meterReadStatusList = null;

	List<ModelDetailsLookup> modelDetailsList = null;

	MRDReadTypeLookup mrdReadTypeLookup = null;

	List<String> mrNoList = null;

	/*
	 * variable to store List map values for Is Occupier Security Applicable
	 */
	Map<String, String> occupSecurityCharValListMap;

	/*
	 * variable to store List map values for Preferred mode of payment
	 */
	Map<String, String> prefModeOfPmntCharValListMap;

	// START: Added by Rajib as per Jtrac DJB-4313 on 27-JAN-2015
	/*
	 * variable to store List map values for Identity proof Type
	 */
	Map<String, String> proofOfIdTypeListMap;

	/*
	 * variable to store List map values for Identity proof Type
	 */
	Map<String, String> proofOfResListMap;

	/*
	 * variable to store List map values for Identity proof Type
	 */
	Map<String, String> propertyOwnDocListMap;

	// END: Added by Rajib as per Jtrac DJB-4313 on 13-JAN-2016
	List<SATypeDetails> saTypeList = null;

	/*
	 * variable to store List map values for Sewer Development Charge
	 * Applicability
	 */
	Map<String, String> sewDevChrgApplCharValListMap;

	/*
	 * variable to store List map values for Development Charge Colony for Sewer
	 */
	Map<String, String> sewDevChrgColCharValListMap;

	/*
	 * variable to store List map values for Sewer Technical Feasibility
	 */
	Map<String, String> sewFeasCharValListMap;

	/*
	 * variable to store List map values for Unauthorized usage detected at
	 * premise
	 */
	Map<String, String> unAuthUsgCharValListMap;

	/*
	 * variable to store List map values for Water Development Charge
	 * Applicability
	 */
	Map<String, String> watDevChrgApplCharValListMap;

	/*
	 * variable to store List map values for ZRO Location
	 */
	Map<String, String> watDevChrgColCharValListMap;

	List<String> waterConnectionSizeList = null;

	List<String> waterConnectionUseList = null;

	/*
	 * variable to store List map values for Water Technical Feasibility
	 */
	Map<String, String> watFeasCharValListMap;

	/*
	 * variable to store List map values for Size of Meter
	 */
	Map<String, String> wconSizeCharValListMap;

	// END: Added by Rajib as per Jtrac DJB-4313 on 27-JAN-2015
	// START: Added by Rajib as per Jtrac DJB-4313 on 13-JAN-2016
	/*
	 * variable to store List map values for Is water being used in civil
	 * construction for the premises
	 */
	Map<String, String> yesNoCharValListMap;

	List<String> zoneList = null;

	Map<String, String> zoneListMap;

	/*
	 * variable to store List map values for Identity proof Type
	 */
	Map<String, String> zroLocCharValListMap;
	/*
	 * Added by Madhuri on 7th June 2016 as per Jtrac- 4464,To get open Bill round
	 */
	List<String> openBillWindowList =null;

	/**
	 * <p>
	 * This is a constructor for the class LOV used for loading the variables
	 * </p>
	 */
	public LOV() {
		AppLog.begin();
		try {
			AppLog.info("START::Loading LOV");
			this.zoneListMap = GetterDAO.getZoneListMap();
			this.zoneList = GetterDAO.getZoneList();
			this.mrNoList = GetterDAO.getMRNoList();
			this.areaList = GetterDAO.getAreaList();
			this.billWindowList = GetterDAO.getBillWIndowList();
			this.meterReadStatusList = GetterDAO.getMeterReadStatusList();
			this.mrdReadTypeLookup = GetterDAO.getMRDReadTypeList();
			this.waterConnectionSizeList = GetterDAO
					.getWaterConnectionSizeList();
			this.waterConnectionUseList = GetterDAO.getWaterConnectionUseList();
			this.saTypeList = GetterDAO.getSATypeList();
			this.DJBMeterTypeList = GetterDAO.getDJBMeterTypeList();
			this.manufacturerDetailsList = GetterDAO
					.getManufacturerDetailsList();
			this.modelDetailsList = GetterDAO.getModelDetailsList();
			// START: On 20-JAN-2015 Rajib added below lines to populate Area
			// Details (PIN code, Locality and SubLocality meeting)
			this.areaDetails = getLocalityDetails();
			this.proofOfIdTypeListMap = (HashMap<String, String>) NewConnFileEntryDAO
					.getDocAttchList(DJBConstants.PROOF_OF_ID_DOC_TYPE);
			this.propertyOwnDocListMap = (HashMap<String, String>) NewConnFileEntryDAO
					.getDocAttchList(DJBConstants.PROPERTY_OWNERSHIP_DOC_TYPE);
			this.proofOfResListMap = (HashMap<String, String>) NewConnFileEntryDAO
					.getDocAttchList(DJBConstants.PROOF_OF_RES_DOC_TYPE);
			// END: On 20-JAN-2015 Rajib added below lines to populate Area
			// Details (PIN code, Locality and SubLocality meeting)
			// START: Added by Rajib as per Jtrac DJB-4313 on 13--2016
			this.yesNoCharValListMap = GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_YES_NO);
			this.unAuthUsgCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_CM_UNUSE);
			this.prefModeOfPmntCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_PREFMOP);
			this.watFeasCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_WATFEAS);
			this.sewFeasCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_SEWFEAS);
			this.isDocVerifiedCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_DOCVAR);
			this.djbEmpCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_DJB_EMP);
			this.wconSizeCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_WCONSIZE);
			this.occupSecurityCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_OSAPP);
			this.sewDevChrgApplCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_APLY_SWR);
			this.sewDevChrgColCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_CM_SW_CL);
			this.watDevChrgApplCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_APLY_WTR);
			this.watDevChrgColCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_CM_WT_CL);
			this.zroLocCharValListMap = (HashMap<String, String>) GetterDAO
					.getPreDefChrValListMap(DJBConstants.CHAR_TYPE_CD_ZRO_LOC);
			// END: Added by Rajib as per Jtrac DJB-4313 on 13-JAN-2016
			//Added by Madhuri as per Jtrac DJB-4464 on 14-JUNE-2016
			this.openBillWindowList = GetterDAO.getOpenBillWIndowList();
			AppLog.info("END::Loading LOV");
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
	}

	public String getAreaDetails() {
		return areaDetails;
	}

	/**
	 * @return the areaList
	 */
	public List<String> getAreaList() {
		return areaList;
	}

	/**
	 * @return the billWindowList
	 */
	public List<String> getBillWindowList() {
		return billWindowList;
	}

	public Map<String, String> getDjbEmpCharValListMap() {
		return djbEmpCharValListMap;
	}

	/**
	 * @return the dJBMeterTypeList
	 */
	public List<MeterTypeLookup> getDJBMeterTypeList() {
		return DJBMeterTypeList;
	}

	public Map<String, String> getIsDocVerifiedCharValListMap() {
		return isDocVerifiedCharValListMap;
	}

	/**
	 * @return the manufacturerDetailsList
	 */
	public List<ManufacturerDetailsLookup> getManufacturerDetailsList() {
		return manufacturerDetailsList;
	}

	/**
	 * @return the meterReadStatusList
	 */
	public List<String> getMeterReadStatusList() {
		return meterReadStatusList;
	}

	/**
	 * @return the modelDetailsList
	 */
	public List<ModelDetailsLookup> getModelDetailsList() {
		return modelDetailsList;
	}

	/**
	 * @return the mrdReadTypeLookup
	 */
	public MRDReadTypeLookup getMrdReadTypeLookup() {
		return mrdReadTypeLookup;
	}

	/**
	 * @return the mrNoList
	 */
	public List<String> getMrNoList() {
		return mrNoList;
	}

	public Map<String, String> getOccupSecurityCharValListMap() {
		return occupSecurityCharValListMap;
	}

	public Map<String, String> getPrefModeOfPmntCharValListMap() {
		return prefModeOfPmntCharValListMap;
	}

	public Map<String, String> getProofOfIdTypeListMap() {
		return proofOfIdTypeListMap;
	}

	public Map<String, String> getProofOfResListMap() {
		return proofOfResListMap;
	}

	public Map<String, String> getPropertyOwnDocListMap() {
		return propertyOwnDocListMap;
	}

	/**
	 * @return the saTypeList
	 */
	public List<SATypeDetails> getSaTypeList() {
		return saTypeList;
	}

	public Map<String, String> getSewDevChrgApplCharValListMap() {
		return sewDevChrgApplCharValListMap;
	}

	public Map<String, String> getSewDevChrgColCharValListMap() {
		return sewDevChrgColCharValListMap;
	}

	public Map<String, String> getSewFeasCharValListMap() {
		return sewFeasCharValListMap;
	}

	public Map<String, String> getUnAuthUsgCharValListMap() {
		return unAuthUsgCharValListMap;
	}

	public Map<String, String> getWatDevChrgApplCharValListMap() {
		return watDevChrgApplCharValListMap;
	}

	public Map<String, String> getWatDevChrgColCharValListMap() {
		return watDevChrgColCharValListMap;
	}

	/**
	 * @return the waterConnectionSizeList
	 */
	public List<String> getWaterConnectionSizeList() {
		return waterConnectionSizeList;
	}

	/**
	 * @return the waterConnectionUseList
	 */
	public List<String> getWaterConnectionUseList() {
		return waterConnectionUseList;
	}

	public Map<String, String> getWatFeasCharValListMap() {
		return watFeasCharValListMap;
	}

	public Map<String, String> getWconSizeCharValListMap() {
		return wconSizeCharValListMap;
	}

	public Map<String, String> getYesNoCharValListMap() {
		return yesNoCharValListMap;
	}

	/**
	 * @return the zoneList
	 */
	public List<String> getZoneList() {
		return zoneList;
	}

	/**
	 * @return the zoneListMap
	 */
	public Map<String, String> getZoneListMap() {
		return zoneListMap;
	}

	public Map<String, String> getZroLocCharValListMap() {
		return zroLocCharValListMap;
	}

	public void setAreaDetails(String areaDetails) {
		this.areaDetails = areaDetails;
	}

	/**
	 * @param areaList
	 *            the areaList to set
	 */
	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	/**
	 * @param billWindowList
	 *            the billWindowList to set
	 */
	public void setBillWindowList(List<String> billWindowList) {
		this.billWindowList = billWindowList;
	}

	public void setDjbEmpCharValListMap(Map<String, String> djbEmpCharValListMap) {
		this.djbEmpCharValListMap = djbEmpCharValListMap;
	}

	/**
	 * @param dJBMeterTypeList
	 *            the dJBMeterTypeList to set
	 */
	public void setDJBMeterTypeList(List<MeterTypeLookup> dJBMeterTypeList) {
		DJBMeterTypeList = dJBMeterTypeList;
	}

	public void setIsDocVerifiedCharValListMap(
			Map<String, String> isDocVerifiedCharValListMap) {
		this.isDocVerifiedCharValListMap = isDocVerifiedCharValListMap;
	}

	/**
	 * @param manufacturerDetailsList
	 *            the manufacturerDetailsList to set
	 */
	public void setManufacturerDetailsList(
			List<ManufacturerDetailsLookup> manufacturerDetailsList) {
		this.manufacturerDetailsList = manufacturerDetailsList;
	}

	/**
	 * @param meterReadStatusList
	 *            the meterReadStatusList to set
	 */
	public void setMeterReadStatusList(List<String> meterReadStatusList) {
		this.meterReadStatusList = meterReadStatusList;
	}

	/**
	 * @param modelDetailsList
	 *            the modelDetailsList to set
	 */
	public void setModelDetailsList(List<ModelDetailsLookup> modelDetailsList) {
		this.modelDetailsList = modelDetailsList;
	}

	/**
	 * @param mrdReadTypeLookup
	 *            the mrdReadTypeLookup to set
	 */
	public void setMrdReadTypeLookup(MRDReadTypeLookup mrdReadTypeLookup) {
		this.mrdReadTypeLookup = mrdReadTypeLookup;
	}

	/**
	 * @param mrNoList
	 *            the mrNoList to set
	 */
	public void setMrNoList(List<String> mrNoList) {
		this.mrNoList = mrNoList;
	}

	public void setOccupSecurityCharValListMap(
			Map<String, String> occupSecurityCharValListMap) {
		this.occupSecurityCharValListMap = occupSecurityCharValListMap;
	}

	public void setPrefModeOfPmntCharValListMap(
			Map<String, String> prefModeOfPmntCharValListMap) {
		this.prefModeOfPmntCharValListMap = prefModeOfPmntCharValListMap;
	}

	public void setProofOfIdTypeListMap(Map<String, String> proofOfIdTypeListMap) {
		this.proofOfIdTypeListMap = proofOfIdTypeListMap;
	}

	public void setProofOfResListMap(Map<String, String> proofOfResListMap) {
		this.proofOfResListMap = proofOfResListMap;
	}

	public void setPropertyOwnDocListMap(
			Map<String, String> propertyOwnDocListMap) {
		this.propertyOwnDocListMap = propertyOwnDocListMap;
	}

	/**
	 * @param saTypeList
	 *            the saTypeList to set
	 */
	public void setSaTypeList(List<SATypeDetails> saTypeList) {
		this.saTypeList = saTypeList;
	}

	public void setSewDevChrgApplCharValListMap(
			Map<String, String> sewDevChrgApplCharValListMap) {
		this.sewDevChrgApplCharValListMap = sewDevChrgApplCharValListMap;
	}

	public void setSewDevChrgColCharValListMap(
			Map<String, String> sewDevChrgColCharValListMap) {
		this.sewDevChrgColCharValListMap = sewDevChrgColCharValListMap;
	}

	public void setSewFeasCharValListMap(
			Map<String, String> sewFeasCharValListMap) {
		this.sewFeasCharValListMap = sewFeasCharValListMap;
	}

	public void setUnAuthUsgCharValListMap(
			Map<String, String> unAuthUsgCharValListMap) {
		this.unAuthUsgCharValListMap = unAuthUsgCharValListMap;
	}

	public void setWatDevChrgApplCharValListMap(
			Map<String, String> watDevChrgApplCharValListMap) {
		this.watDevChrgApplCharValListMap = watDevChrgApplCharValListMap;
	}

	public void setWatDevChrgColCharValListMap(
			Map<String, String> watDevChrgColCharValListMap) {
		this.watDevChrgColCharValListMap = watDevChrgColCharValListMap;
	}

	/**
	 * @param waterConnectionSizeList
	 *            the waterConnectionSizeList to set
	 */
	public void setWaterConnectionSizeList(List<String> waterConnectionSizeList) {
		this.waterConnectionSizeList = waterConnectionSizeList;
	}

	/**
	 * @param waterConnectionUseList
	 *            the waterConnectionUseList to set
	 */
	public void setWaterConnectionUseList(List<String> waterConnectionUseList) {
		this.waterConnectionUseList = waterConnectionUseList;
	}

	public void setWatFeasCharValListMap(
			Map<String, String> watFeasCharValListMap) {
		this.watFeasCharValListMap = watFeasCharValListMap;
	}

	public void setWconSizeCharValListMap(
			Map<String, String> wconSizeCharValListMap) {
		this.wconSizeCharValListMap = wconSizeCharValListMap;
	}

	public void setYesNoCharValListMap(Map<String, String> yesNoCharValListMap) {
		this.yesNoCharValListMap = yesNoCharValListMap;
	}

	/**
	 * @param zoneList
	 *            the zoneList to set
	 */
	public void setZoneList(List<String> zoneList) {
		this.zoneList = zoneList;
	}

	/**
	 * @param zoneListMap
	 *            the zoneListMap to set
	 */
	public void setZoneListMap(Map<String, String> zoneListMap) {
		this.zoneListMap = zoneListMap;
	}

	public void setZroLocCharValListMap(Map<String, String> zroLocCharValListMap) {
		this.zroLocCharValListMap = zroLocCharValListMap;
	}
 /*
	 * Started by Madhuri :on 14th June 2016 
	 */
	public List<String> getOpenBillWindowList() {
		return openBillWindowList;
	}

	public void setOpenBillWindowList(List<String> openBillWindowList) {
		this.openBillWindowList = openBillWindowList;
	}
	 /*
	 * Ended by Madhuri :on 14th June 2016 
	 */
}
