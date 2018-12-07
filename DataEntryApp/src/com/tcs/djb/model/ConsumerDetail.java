/************************************************************************************************************
 * @(#) ConsumerDetail.java   27 Jul 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DataEntryAppConstants;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.validator.MeterReadValidator;

/**
 * ConsumerDetail store the details of a consumer due for Bill Generation
 * 
 * @author apurb sinha
 * @history 27-Jul-2012 apurb.sinha created the class
 * @history 02-Aug-2012 apurb.sinha added validation block for meter reader
 *          remark status
 * @history 14-Mar-2013 Matiur Rahman added previousMeterReadDetails.
 * @history 14-Mar-2013 Matiur Rahman added category.
 * @history 30-July-2013 Mrityunjoy Added zone,area,consPreBillStatID and billId
 *          due to Single KNO Bill Generation
 * @history 06-08-2013 Matiur Rahman modified method {@link #validate} as per
 *          JTrac ID#DJB-873.
 * @history 10-01-2014 Matiur Rahman added saStatus
 * @history 17-11-2016 Sanjay Das added hiLowFlag
 */
public class ConsumerDetail implements Comparable<ConsumerDetail> {
	private String _address;
	private String _billRound;
	private String _cat;
	private String _consumerStatus = null;
	private ConsumerStatusLookup _consumerStatusLookup = ConsumerStatusLookup.INITIATED;
	private String _currentAvgConsumption;
	private MeterReadDetails _currentMeterRead;
	private String _kno;
	private String _lastBillGenerationDate;
	private String _name;
	private PremiseDetails _premiseDetails;
	private MeterReadDetails _prevActualMeterRead;
	private MeterReadDetails _prevMeterRead;
	private int _seqNo;
	private String _serviceCycleRouteSeq;
	private String _serviceType;
	private String _updatedBy;
	private String _wcNo;
	private String area;
	private String billId;
	/****************** End *****************************************/
	/**
	 * category of the Consumer(CAT I, CAT IA,CAT II,CAT IIA)
	 */
	private String category;
	private String consPreBillStatID;
	/**
	 * Type of the Consumer(60=Record Entered,20=Disconnected,30=Meter
	 * Installed,40=Reopening,50=Change of Category).
	 */
	private String consumerType;
	private String hiLowFlag;
	/**
	 * Previous Meter Read Details.
	 */
	private PreviousMeterReadDetails previousMeterReadDetails;

	private String saStatus;

	/************** Updated By Mrityunjoy Start *******************************************/
	/**
	 * zone,area,consPreBillStatID and billId due to Single KNO Bill Generation
	 */
	private String zone;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ConsumerDetail obj) {
		if (this._seqNo == obj._seqNo) {
			return 0;
		} else if ((this._seqNo) > obj._seqNo) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * @return the _address
	 */
	public String getAddress() {
		return _address;
	}

	public String getArea() {
		return area;
	}

	public String getBillId() {
		return billId;
	}

	public String getBillRound() {
		return _billRound;
	}

	public String getCat() {
		return _cat;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	public String getConsPreBillStatID() {
		return consPreBillStatID;
	}

	public String getConsumerStatus() {
		return _consumerStatus;
	}

	public ConsumerStatusLookup getConsumerStatusLookup() {
		return _consumerStatusLookup;
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	public String getCurrentAvgConsumption() {
		return _currentAvgConsumption;
	}

	public MeterReadDetails getCurrentMeterRead() {
		return _currentMeterRead;
	}

	public String getKno() {
		return _kno;
	}

	public String getLastBillGenerationDate() {
		return _lastBillGenerationDate;
	}
	
	public String getHiLowFlag() {
		return hiLowFlag;
	}


	public String getName() {
		return _name;
	}

	/**
	 * @return the _premiseDetails
	 */
	public PremiseDetails getPremiseDetails() {
		return _premiseDetails;
	}

	public MeterReadDetails getPrevActualMeterRead() {
		return _prevActualMeterRead;
	}

	/**
	 * @return the previousMeterReadDetails
	 */
	public PreviousMeterReadDetails getPreviousMeterReadDetails() {
		return previousMeterReadDetails;
	}

	public MeterReadDetails getPrevMeterRead() {
		return _prevMeterRead;
	}

	/**
	 * @return the saStatus
	 */
	public String getSaStatus() {
		return saStatus;
	}

	public int getSeqNo() {
		return _seqNo;
	}

	public String getServiceCycleRouteSeq() {
		return _serviceCycleRouteSeq;
	}

	public String getServiceType() {
		return _serviceType;
	}

	public String getUpdatedBy() {
		return _updatedBy;
	}

	public String getWcNo() {
		return _wcNo;
	}

	public String getZone() {
		return zone;
	}

	public String saveData() {
		// String isSuccess = "N";

		return null;
	}

	/**
	 * @param address
	 *            the _address to set
	 */
	public void setAddress(String address) {
		_address = address;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public void setBillRound(String billRound) {
		_billRound = billRound;
	}

	public void setCat(String cat) {
		_cat = cat;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public void setConsPreBillStatID(String consPreBillStatID) {
		this.consPreBillStatID = consPreBillStatID;
	}

	public void setConsumerStatus(String consumerStatus) {
		_consumerStatus = consumerStatus;
	}

	public void setConsumerStatusLookup(
			ConsumerStatusLookup consumerStatusLookup) {
		_consumerStatusLookup = consumerStatusLookup;
	}

	/**
	 * @param consumerType
	 *            the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	public void setCurrentAvgConsumption(String currentAvgConsumption) {
		_currentAvgConsumption = currentAvgConsumption;
	}

	public void setCurrentMeterRead(MeterReadDetails currentMeterRead) {
		_currentMeterRead = currentMeterRead;
	}
	
	public void setHiLowFlag(String hiLowFlag) {
		this.hiLowFlag = hiLowFlag;
	}

	public void setKno(String kno) {
		_kno = kno;
	}

	public void setLastBillGenerationDate(String lastBillGenerationDate) {
		_lastBillGenerationDate = lastBillGenerationDate;
	}

	public void setName(String name) {
		_name = name;
	}

	/**
	 * @param premiseDetails
	 *            the _premiseDetails to set
	 */
	public void setPremiseDetails(PremiseDetails premiseDetails) {
		_premiseDetails = premiseDetails;
	}

	public void setPrevActualMeterRead(MeterReadDetails prevActualMeterRead) {
		_prevActualMeterRead = prevActualMeterRead;
	}

	/**
	 * @param previousMeterReadDetails
	 *            the previousMeterReadDetails to set
	 */
	public void setPreviousMeterReadDetails(
			PreviousMeterReadDetails previousMeterReadDetails) {
		this.previousMeterReadDetails = previousMeterReadDetails;
	}

	public void setPrevMeterRead(MeterReadDetails prevMeterRead) {
		_prevMeterRead = prevMeterRead;
	}

	/**
	 * @param saStatus
	 *            the saStatus to set
	 */
	public void setSaStatus(String saStatus) {
		this.saStatus = saStatus;
	}

	public void setSeqNo(int seqNo) {
		_seqNo = seqNo;
	}

	public void setServiceCycleRouteSeq(String serviceCycleRouteSeq) {
		_serviceCycleRouteSeq = serviceCycleRouteSeq;
	}

	public void setServiceType(String serviceType) {
		_serviceType = serviceType;
	}

	public void setUpdatedBy(String updatedBy) {
		_updatedBy = updatedBy;
	}

	public void setWcNo(String wcNo) {
		_wcNo = wcNo;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
	 * <p>
	 * Validate meter read details.
	 * </p>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String validate() {
		AppLog.begin();
		String msg = null;
		try {
			if (DataEntryAppConstants.ENABLE_MRD_STAT_VALIDATION) {
				if (GetterDAO.validateMeterReaderRemark(_prevMeterRead
						.getMeterStatus(), _currentMeterRead.getMeterStatus()) == false) {
					// TODO Add msg for Invalid Meter Reader Status Code
					msg = "<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: Invalid Meter Reader Status Code for KNO: "
							+ _kno
							+ ".<br/> Data could not be Saved. Please go to the page and correct the same.</b></font></div>";
				}
			}
			// System.out.println("_prevActualMeterRead>>>"
			// + UtilityForAll.getIntValueOfString(_prevActualMeterRead
			// .getRegRead())
			// + ">>_currentMeterRead>>>"
			// + UtilityForAll.getIntValueOfString(_currentMeterRead
			// .getRegRead()));
			// System.out.println(">>>>>>>>>>"
			// +
			// this._currentMeterRead.getReadType()+">>>>"+_prevActualMeterRead
			// .getRegRead()+">>>"+_currentMeterRead.getRegRead());
			/**
			 * Start:Validation added by Matiur Rahman on 0-=6-08-2013 as per
			 * JTrac ID#DJB-873
			 */
			if ("60".equals(this._consumerStatus)) {
				Map<Object, Object> inputMap = new HashMap<Object, Object>();
				// ArrayList<ErrorDetails> meterReadErrorList =
				// (ArrayList<ErrorDetails>) inputMap
				// .get("meterReadErrorList");
				// List<MeterReadUploadErrorLogDetails>
				// meterReadUploadErrorLogDetailsList =
				// (ArrayList<MeterReadUploadErrorLogDetails>) inputMap
				// .get("meterReadUploadErrorLogDetailsList");
				/**
				 * Retrieve records from the Database for meter Read Type Look
				 * Up.
				 */
				MeterReadTypeLookUp meterReadTypeLookUp = GetterDAO
						.getMeterReadRemarkCorrespondingToReadType();
				inputMap.put("meterReadTypeLookUp", meterReadTypeLookUp);
				/**
				 * Retrieve records from the Database for those account to
				 * update records.
				 */
				List<ConsumerDetail> consumerDetailList = GetterDAO
						.getConsumerDetailByAccountIDAndBillRound(_kno,
								_billRound);
				inputMap.put("consumerDetailList", consumerDetailList);
				ArrayList<MeterReadUploadDetails> toUploadMeterReadDetailsList = new ArrayList<MeterReadUploadDetails>();
				MeterReadUploadDetails meterReadUploadDetails = new MeterReadUploadDetails();
				meterReadUploadDetails.setKno(_kno);
				meterReadUploadDetails.setBillRound(_billRound);
				meterReadUploadDetails.setConsumerStatus(_consumerStatus);
				meterReadUploadDetails.setMeterReadDate(_currentMeterRead
						.getMeterReadDate());
				meterReadUploadDetails.setMeterReadRemark(_currentMeterRead
						.getMeterStatus());
				meterReadUploadDetails.setMeterRead(_currentMeterRead
						.getRegRead());
				meterReadUploadDetails.setAverageConsumption(_currentMeterRead
						.getAvgRead());
				meterReadUploadDetails.setNoOfFloors(_currentMeterRead
						.getNoOfFloor());
				toUploadMeterReadDetailsList.add(meterReadUploadDetails);
				inputMap.put("toUploadMeterReadDetailsList",
						toUploadMeterReadDetailsList);
				Map<Object, Object> returnMap = MeterReadValidator
						.validateMeterReadUploadAsPerBusinessProcess((HashMap<Object, Object>) inputMap);
				ArrayList<ErrorDetails> meterReadErrorList = (ArrayList<ErrorDetails>) returnMap
						.get("meterReadErrorList");
				ErrorDetails errorDetails = null;
				String errorMessage = null;
				if (null != meterReadErrorList && meterReadErrorList.size() > 0) {
					for (int i = 0; i < meterReadErrorList.size(); i++) {
						errorDetails = meterReadErrorList.get(i);
						if (null != errorDetails) {
							errorMessage = errorDetails.getErrorDescription()
									+ "<br/>";
						}
					}
				}
				if (null != errorMessage) {
					msg = "<div class='search-option' title='Server Message'><font color='red' size='2'>ERROR : "
							+ errorMessage
							+ "<br/> <b>Data could not be Saved for KNO: "
							+ _kno
							+ " Page:"
							+ (_seqNo + 1)
							+ ". Please go back to the page and correct the same.</b></font></div>";
				}

			}
			/**
			 * End:Validation added by Matiur Rahman on 0-=6-08-2013 as per
			 * JTrac ID#DJB-873.
			 */
			if ("60".equals(this._currentMeterRead.getReadType())
					&& UtilityForAll.getIntValueOfString(_prevActualMeterRead
							.getRegRead()) > UtilityForAll
							.getIntValueOfString(_currentMeterRead.getRegRead())) {
				msg = "<div class='search-option' title='Server Message'><font color='red' size='2'><b>ERROR: Current Meter Read Should be more than Previouse Actual Meter Read of "
						+ UtilityForAll
								.getIntValueOfString(_prevActualMeterRead
										.getRegRead())
						+ " KL.<br/> Data could not be Saved for KNO: "
						+ _kno
						+ ". Please go to the page and correct the same.</b></font></div>";

			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return msg;
	}

}
