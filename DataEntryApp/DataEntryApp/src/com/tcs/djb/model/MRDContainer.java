/************************************************************************************************************
 * @(#) MRDContainer.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.dao.SetterDAO;

/**
 * <p>
 * MRDContainer is used for storing data of all consumers of the provided MRD.
 * </p>
 * 
 * @author 374204
 */
public class MRDContainer {
	private String _areaNo;
	private String _billRoundId;
	private List<ConsumerDetail> _consumerList;
	private String _lastUpdatedBy;
	private MRDStatusLookup _mrdStatus;
	private String _mrKey;

	private String _mrNo;

	private String _zone;
	/********************************************/
	private String  _acctIDAliasKNO;
	

	/********************************************/

	public MRDContainer() {
		_consumerList = new ArrayList<ConsumerDetail>();
	}

	/**
	 * Default constructor for MRDContainer
	 * 
	 * @param zone
	 *            - Unique Zone Id for which consumer list is required
	 * @param mrNo
	 *            - Unique MR no for which Consumer List is required
	 * @param areaNo
	 *            - Unique area no for which consumer list is required
	 */
	public MRDContainer(String zone, String mrNo, String areaNo) {
		super();
		_mrNo = mrNo;
		_zone = zone;
		_areaNo = areaNo;
		_consumerList = new ArrayList<ConsumerDetail>();
	}

	public MRDContainer(String zone, String mrNo, String areaNo,
			String billRoundId) {
		super();
		_mrNo = mrNo;
		_zone = zone;
		_areaNo = areaNo;
		_billRoundId = billRoundId;
		_consumerList = new ArrayList<ConsumerDetail>();
	}
	
	/********************************************/
	public MRDContainer(String acctIDAliasKNO)
	{ 
		super();
		_acctIDAliasKNO=acctIDAliasKNO;
		_consumerList = new ArrayList<ConsumerDetail>();
	}
	
	public boolean freezeMRDBasedOnKNO() {
		SetterDAO setterDAO = new SetterDAO();
		return setterDAO.freezeMRDBasedOnKNO(this._acctIDAliasKNO,this._lastUpdatedBy);
	}

	
	/**********************************************/

	public boolean freezeMRD() {
		SetterDAO setterDAO = new SetterDAO();
		return setterDAO.freezeMRD(this._mrKey, this._billRoundId,
				this._lastUpdatedBy, this._zone, this._mrNo, this._areaNo);
	}

	public String getAreaNo() {
		return _areaNo;
	}

	public String getBillRoundId() {
		return _billRoundId;
	}

	public List<ConsumerDetail> getConsumerList() {
		return _consumerList;
	}

	public String getLastUpdatedBy() {
		return _lastUpdatedBy;
	}

	public MRDStatusLookup getMrdStatus() {
		return _mrdStatus;
	}

	public String getMrKey() {
		return _mrKey;
	}

	public String getMrNo() {
		return _mrNo;
	}

	public String getZone() {
		return _zone;
	}

	public void init() {

	}

	public void setAreaNo(String areaNo) {
		_areaNo = areaNo;
	}

	public void setBillRoundId(String billRoundId) {
		_billRoundId = billRoundId;
	}

	public void setConsumerList(List<ConsumerDetail> consumerList) {
		_consumerList = consumerList;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		_lastUpdatedBy = lastUpdatedBy;
	}

	public void setMrdStatus(MRDStatusLookup mrdStatus) {
		_mrdStatus = mrdStatus;
	}

	public void setMrKey(String mrKey) {
		_mrKey = mrKey;
	}

	public void setMrNo(String mrNo) {
		_mrNo = mrNo;
	}

	public void setZone(String zone) {
		_zone = zone;
	}
	/*****************************************************************/
	public String getAcctIDAliasKNO() {
		return _acctIDAliasKNO;
	}

	public void setAcctIDAliasKNO(String acctIDAliasKNO) {
		_acctIDAliasKNO = acctIDAliasKNO;
	}

}
