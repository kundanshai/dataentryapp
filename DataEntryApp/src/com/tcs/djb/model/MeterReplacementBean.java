/************************************************************************************************************
 * @(#) MeterReplacementBean.java   12 Mar 2013
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

import java.sql.Date;

/**
 * <p>
 * Model class used to store getters and setters of an Account
 * </p>
 * 
 * @author Priyanka Dutta(Tata Consultancy Services).
 * @since 12-03-2013
 * @history 13-02-2014 Matiur Rahman Changed data type Double to double for the
 *          change as per JTrac ID#DJB-2024. It was throwing exception for some
 *          cases while trying to set in prepared statement and value was null
 *          due to not initialization. Double may have null value as this is an
 *          Object. Now it is changed to double which is by default 0.00. Also
 *          boolean is changed to boolean for the same reason.
 */
public class MeterReplacementBean {
	private String acctId;
	private String mtrRplcStagId;
	private String initMtrRplcStagId;
	private Date mtrInstallDate;
	private String oldZonecd;
	private String oldMrno;
	private String oldAreano;
	private String wcno;
	private String oldMrkey;
	private Date lastOkDt;
	private String lastStatus;
	private double lastOkRead;
	private double tbp;
	private double pymnt;
	private double adjDel;
	private double adjFa;
	private double adjFin;
	private String migPerId;
	private String migAcctId;
	private String migPremId;
	private String migSpId;
	private String migSaId;
	private String migMtrId;
	private String migRegReadId;
	private String migMrId;
	private Date migLastOkDt;
	private boolean isSaShiftCase;
	private String billRoundId;
	private boolean isUnmetered;

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getMtrRplcStagId() {
		return mtrRplcStagId;
	}

	public void setMtrRplcStagId(String mtrRplcStagId) {
		this.mtrRplcStagId = mtrRplcStagId;
	}

	public Date getMtrInstallDate() {
		return mtrInstallDate;
	}

	public void setMtrInstallDate(Date mtrInstallDate) {
		this.mtrInstallDate = mtrInstallDate;
	}

	public String getOldZonecd() {
		return oldZonecd;
	}

	public void setOldZonecd(String oldZonecd) {
		this.oldZonecd = oldZonecd;
	}

	public String getOldMrno() {
		return oldMrno;
	}

	public void setOldMrno(String oldMrno) {
		this.oldMrno = oldMrno;
	}

	public String getOldAreano() {
		return oldAreano;
	}

	public void setOldAreano(String oldAreano) {
		this.oldAreano = oldAreano;
	}

	public String getWcno() {
		return wcno;
	}

	public void setWcno(String wcno) {
		this.wcno = wcno;
	}

	public String getOldMrkey() {
		return oldMrkey;
	}

	public void setOldMrkey(String oldMrkey) {
		this.oldMrkey = oldMrkey;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public double getLastOkRead() {
		return lastOkRead;
	}

	public void setLastOkRead(double lastOkRead) {
		this.lastOkRead = lastOkRead;
	}

	public String getMigPerId() {
		return migPerId;
	}

	public void setMigPerId(String migPerId) {
		this.migPerId = migPerId;
	}

	public String getMigAcctId() {
		return migAcctId;
	}

	public void setMigAcctId(String migAcctId) {
		this.migAcctId = migAcctId;
	}

	public String getMigPremId() {
		return migPremId;
	}

	public void setMigPremId(String migPremId) {
		this.migPremId = migPremId;
	}

	public String getMigSpId() {
		return migSpId;
	}

	public void setMigSpId(String migSpId) {
		this.migSpId = migSpId;
	}

	public String getMigSaId() {
		return migSaId;
	}

	public void setMigSaId(String migSaId) {
		this.migSaId = migSaId;
	}

	public String getMigMtrId() {
		return migMtrId;
	}

	public void setMigMtrId(String migMtrId) {
		this.migMtrId = migMtrId;
	}

	public String getMigRegReadId() {
		return migRegReadId;
	}

	public void setMigRegReadId(String migRegReadId) {
		this.migRegReadId = migRegReadId;
	}

	public String getMigMrId() {
		return migMrId;
	}

	public void setMigMrId(String migMrId) {
		this.migMrId = migMrId;
	}

	public Date getMigLastOkDt() {
		return migLastOkDt;
	}

	public void setMigLastOkDt(Date migLastOkDt) {
		this.migLastOkDt = migLastOkDt;
	}

	public boolean getIsSaShiftCase() {
		return isSaShiftCase;
	}

	public void setIsSaShiftCase(boolean isSaShiftCase) {
		this.isSaShiftCase = isSaShiftCase;
	}

	public String getBillRoundId() {
		return billRoundId;
	}

	public void setBillRoundId(String billRoundId) {
		this.billRoundId = billRoundId;
	}

	public double getTbp() {
		return tbp;
	}

	public void setTbp(double tbp) {
		this.tbp = tbp;
	}

	public double getPymnt() {
		return pymnt;
	}

	public void setPymnt(double pymnt) {
		this.pymnt = pymnt;
	}

	public double getAdjDel() {
		return adjDel;
	}

	public void setAdjDel(double adjDel) {
		this.adjDel = adjDel;
	}

	public double getAdjFa() {
		return adjFa;
	}

	public void setAdjFa(double adjFa) {
		this.adjFa = adjFa;
	}

	public double getAdjFin() {
		return adjFin;
	}

	public void setAdjFin(double adjFin) {
		this.adjFin = adjFin;
	}

	public String getInitMtrRplcStagId() {
		return initMtrRplcStagId;
	}

	public void setInitMtrRplcStagId(String initMtrRplcStagId) {
		this.initMtrRplcStagId = initMtrRplcStagId;
	}

	public boolean getIsUnmetered() {
		return isUnmetered;
	}

	public void setIsUnmetered(boolean isUnmetered) {
		this.isUnmetered = isUnmetered;
	}

	public void setLastOkDt(Date lastOkDt) {
		this.lastOkDt = lastOkDt;
	}

	public Date getLastOkDt() {
		return lastOkDt;
	}

}
