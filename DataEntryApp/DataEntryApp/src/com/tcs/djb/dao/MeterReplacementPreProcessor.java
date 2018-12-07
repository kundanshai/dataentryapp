/************************************************************************************************************
 * @(#) MeterReplacementPreProcessor.java   12 Mar 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.model.MeterReplacementBean;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * Classname - MeterReplacementPreProcessor CreatedOn - 12-Mar-2013 CreatedBy -
 * Priyanka Dutta Version - V1.0.0 Requirement - Class will be used by Data
 * Entry Screen Contain all methods required during Pre-processing of Meter
 * Replacement.
 * </p>
 * 
 * @author Priyanka Dutta (Tata Consultancy Services)
 * @since 12-Mar-2013
 * @history 28-03-2012 Matiur Rahman Added finally block to method
 *          {@link #run()}.
 * @history 28-03-2013 Matiur Rahman AppLog implemented.
 * @history 11-06-2013 Matiur Rahman Added method
 *          {@link #getMeterReplacementAdjustment(String, String, String, String, Date)}
 *          . as per JTrac ID #DJB-1588.
 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for better
 *          traceability for the change as per JTrac ID#DJB-2024. Added
 *          {@link #errorMessage} for storing interim error if occurred and
 *          {@link #userId} for Updating who columns. Added roll back feature in
 *          case of error occurs after status updated to 64.
 * @see Runnable
 * @see MeterReplacementBean
 * @see MeterReplacementReviewDAO
 * @see AppLog
 */
public class MeterReplacementPreProcessor implements Runnable {
	// public static Connection conn = null;
	public Connection conn = null;
	private MeterReplacementBean accountBeanObj;
	// private Savepoint savepoint1 = null;
	private String accountId;
	private String mtrRplcsStageId;
	private String billRoundId;
	private String errorMessage;
	private String userId;

	/**
	 * <p>
	 * Constructor for MeterReplacementPreProcessor
	 * </p>
	 * 
	 * @history 14-02-2014 Matiur Rahman Added userId as parameter for Updating
	 *          who columns.
	 * @param accountId
	 *            - account id for record to process
	 * @param mtrRplcsStageId
	 *            - meter replace stage id for record to process
	 * @param billRoundId
	 *            - bill round id for record to process
	 * @param userId
	 *            - user id for updating who columns
	 * 
	 */
	public MeterReplacementPreProcessor(String accountId,
			String mtrRplcsStageId, String billRoundId, String userId) {
		super();
		this.accountId = accountId;
		this.mtrRplcsStageId = mtrRplcsStageId;
		this.billRoundId = billRoundId;
		this.userId = userId;
	}

	/**
	 * <p>
	 * Init method to create db connections and set accountBean
	 * </p>
	 * 
	 * @throws Exception
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void init(String accountId) throws Exception {
		AppLog.begin();
		AppLog.info("Started Initialization for KNO::" + accountId);
		// System.out.println("In init");
		// DBConnections.createConnection();
		conn = DBConnector.getConnection();
		// dataCon = DBConnections.dataConn;
		// dataCon.setAutoCommit(false);
		conn.setAutoCommit(false);
		accountBeanObj = new MeterReplacementBean();
		accountBeanObj.setAcctId(accountId);
		// System.out.println("Acct : " + accountBeanObj.getAcctId());
		AppLog.info("Success Initialization for KNO::" + accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Step1: Recognize which consumers of Meter Replacement are Shift SA cases
	 * - Change Status of shift SA cases so that records are not picked up for
	 * Meter Replacement before their SA is shifted to a previous date before
	 * Meter Installation Date.
	 * </p>
	 * 
	 * @param mtrRplcStageId
	 *            - Status of consumer in Meter Replacement Staging Table
	 * @param accountId
	 *            - Account Id/KNO of the Consumer
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void checkIfSAShiftCase(String mtrRplcStageId, String accountId,
			String billRoundId) throws SQLException {
		AppLog.begin();
		// System.out.println("In checkIfSAShiftCase");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String saTypeCd = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT distinct t2.sa_type_cd SATYPECD ");
		var1.append("FROM   cm_mtr_rplc_stage t1 ");
		var1.append("       inner join ci_sa t2 ");
		var1.append("               ON t2.acct_id = t1.acct_id ");
		var1
				.append("                  AND t1.mtr_install_date - 1 <= t2.start_dt ");
		var1
				.append("                  AND trim(t2.sa_type_cd) IN( 'SAWATSEW', 'SAWATR', 'UNMTRD' ) ");
		var1.append("                  AND t2.sa_status_flg = '20' ");
		var1.append("                  AND mtr_rplc_stage_id = ? ");
		var1
				.append("                  AND mtr_rplc_stage_id NOT IN( 600, 610 ) ");
		var1.append("                  AND t1.acct_id = ? ");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, mtrRplcStageId);
		ps.setString(2, accountId);
		rs = ps.executeQuery();
		accountBeanObj.setBillRoundId(billRoundId);
		accountBeanObj.setInitMtrRplcStagId(mtrRplcStageId);
		accountBeanObj.setMtrRplcStagId(mtrRplcStageId);
		if (rs.next()) {
			accountBeanObj.setIsSaShiftCase(true);
			saTypeCd = rs.getString("SATYPECD");
			// Set Consumers OldSaType
			if (null != saTypeCd && null != saTypeCd.trim()
					&& "UNMTRD".equalsIgnoreCase(saTypeCd.trim()))
				accountBeanObj.setIsUnmetered(true);
		} else {
			accountBeanObj.setIsSaShiftCase(false);
		}
		/*
		 * System.out.println("checkIfSAShiftCase: " +
		 * accountBeanObj.getIsSaShiftCase());
		 */
		ps.close();
		AppLog.info("Success check If SA Shift Case for KNO::" + accountId
				+ ":: as ::" + accountBeanObj.getIsSaShiftCase());
		AppLog.end();
	}

	/**
	 * <p>
	 * Step2:a. Select data migration MRD Details - zone, mrno, area, mrkey
	 * details, PER_ID, ACCT_ID, PREM_ID, SP_ID, SA_ID, MTR_ID, REG_READ_ID,
	 * MR_ID, LAST_OK_DT details from djb_main for a consumer by passing kno.
	 * </p>
	 * 
	 * @param mtrRplcStageId
	 *            - Status of consumer in Meter Replacement Staging Table
	 * @param accountId
	 *            - Account Id/KNO of the Consumer
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void selectMigrationDetails(String mtrRplcStageId,
			String accountId, String billRoundId) throws SQLException {
		AppLog.begin();
		/*
		 * System.out.println("In selectMigrationDetails: " + "mtrRplcStageId: "
		 * + mtrRplcStageId + "\naccountId: " + accountId + "\nbillRoundId: " +
		 * billRoundId);
		 */
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT t2.zoneno  AS zoneno, ");
		var1.append("       t2.mrno    AS mrno, ");
		var1.append("       t2.areano  AS areano, ");
		var1.append("       t2.mrkey   AS mrkey, ");
		var1.append("       t1.mtr_install_date AS mtrInstallDate, ");
		var1.append("       t2.wcno AS wcno, ");
		var1.append("       t2.acct_id AS migAcctId, ");
		var1.append("       t2.per_id AS migPerId, ");
		var1.append("       t2.prem_id AS migPremId, ");
		var1.append("       t2.sp_id AS migSpId, ");
		var1.append("       t2.sa_id AS migSaId, ");
		var1.append("       t2.mtr_id AS migMtrId, ");
		var1.append("       t2.reg_read_id AS migRegReadId, ");
		var1.append("       t2.mr_id AS migMrId, ");
		var1.append("       t2.last_ok_dt AS migLastOkDt ");
		var1.append("FROM   cm_mtr_rplc_stage t1 ");
		var1.append("       inner join djb_main t2 ");
		var1.append("               ON t1.acct_id = t2.acct_id ");
		var1.append("WHERE  mtr_rplc_stage_id = ? ");
		var1.append("       AND t1.acct_id = ? ");
		var1.append("       AND t1.bill_round_id = ?");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, mtrRplcStageId);
		ps.setString(2, accountId);
		ps.setString(3, billRoundId);
		rs = ps.executeQuery();
		while (rs.next()) {
			accountBeanObj.setOldZonecd(rs.getString("zoneno"));
			accountBeanObj.setOldMrno(rs.getString("mrno"));
			accountBeanObj.setOldAreano(rs.getString("areano"));
			accountBeanObj.setOldMrkey(rs.getString("mrkey"));
			accountBeanObj.setMtrInstallDate(rs.getDate("mtrInstallDate"));
			accountBeanObj.setWcno(rs.getString("wcno"));
			accountBeanObj.setMigAcctId(rs.getString("migAcctId"));
			accountBeanObj.setMigPerId(rs.getString("migPerId"));
			accountBeanObj.setMigPremId(rs.getString("migPremId"));
			accountBeanObj.setMigSpId(rs.getString("migSpId"));
			accountBeanObj.setMigSaId(rs.getString("migSaId"));
			accountBeanObj.setMigMtrId(rs.getString("migMtrId"));
			accountBeanObj.setMigRegReadId(rs.getString("migRegReadId"));
			accountBeanObj.setMigMrId(rs.getString("migMrId"));
			accountBeanObj.setMigLastOkDt(rs.getDate("migLastOkDt"));

			/*
			 * System.out.println(
			 * "######################################### Selected Migration Details From DJB_MAIN Are: "
			 * + " \nZone: " + accountBeanObj.getOldZonecd() + " \nMrno: " +
			 * accountBeanObj.getOldMrno() + " \nAreano: " +
			 * accountBeanObj.getOldAreano() + " \nMrkey: " +
			 * accountBeanObj.getOldMrkey() + " \nMtrInstallDate: " +
			 * accountBeanObj.getMtrInstallDate() + " \nWcno: " +
			 * accountBeanObj.getWcno() + " \nMigAcctId: " +
			 * accountBeanObj.getMigAcctId() + " \nMigPerId: " +
			 * accountBeanObj.getMigPerId() + " \nMigPremId: " +
			 * accountBeanObj.getMigPremId() + " \nMigSpId: " +
			 * accountBeanObj.getMigSpId() + " \nMigSaId: " +
			 * accountBeanObj.getMigSaId() + " \nMigMtrId: " +
			 * accountBeanObj.getMigMtrId() + " \nMigRegReadId: " +
			 * accountBeanObj.getMigRegReadId() + " \nMigMrId: " +
			 * accountBeanObj.getMigMrId() + " \nMigLastOkDt: " +
			 * accountBeanObj.getMigLastOkDt());
			 */
		}
		ps.close();
		AppLog.info("Success select Migration Details for KNO::" + accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Step2:b. Update Staging table columns old_zonecd, old_mrno, old_areano,
	 * old_mrkey with data migration MRD Details retrieved above.
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param mrkey
	 *            - Mrkey retrieved from djb_main
	 * @param mtrRplcStageId
	 *            - Status of consumer in Meter Replacement Staging Table
	 * @param accountId
	 *            - Account Id/KNO of the Consumer
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private int updateMigrationMrdDetails(String zone, String mrno,
			String areano, String mrkey, String mtrRplcStageId,
			String accountId, String billRoundId) throws SQLException {
		AppLog.begin();
		// System.out.println("In updateMigrationMrdDetails");
		PreparedStatement ps = null;
		int i = 0;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("UPDATE cm_mtr_rplc_stage ");
		var1.append("SET    old_zonecd = ?, ");
		var1.append("       old_mrno = ?, ");
		var1.append("       old_areano = ?, ");
		var1.append("       old_mrkey = ?, ");
		var1.append("       Mig_acct_id = ?, ");
		var1.append("       Mig_Per_Id = ?, ");
		var1.append("       Mig_Prem_Id = ?, ");
		var1.append("       Mig_Sp_Id = ?, ");
		var1.append("       Mig_Sa_Id = ?, ");
		var1.append("       Mig_Mtr_Id = ?, ");
		var1.append("       Mig_Reg_Read_Id = ?, ");
		var1.append("       Mig_Mr_Id = ?, ");
		var1.append("       Mig_Last_Ok_Dt = ?, ");
		var1.append("       last_updt_dttm = SYSDATE, ");
		var1.append("       last_updt_by = 'DE_SCREEN', ");
		var1.append("       IS_SA_SHIFT = 'Y' ");
		var1.append("WHERE  acct_id = ? ");
		var1.append("       AND mtr_rplc_stage_id = ? ");
		var1.append("       AND bill_round_id = ? ");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, mrkey);
		ps.setString(5, accountBeanObj.getMigAcctId());
		ps.setString(6, accountBeanObj.getMigPerId());
		ps.setString(7, accountBeanObj.getMigPremId());
		ps.setString(8, accountBeanObj.getMigSpId());
		ps.setString(9, accountBeanObj.getMigSaId());
		ps.setString(10, accountBeanObj.getMigMtrId());
		ps.setString(11, accountBeanObj.getMigRegReadId());
		ps.setString(12, accountBeanObj.getMigMrId());
		ps.setDate(13, accountBeanObj.getMigLastOkDt());
		ps.setString(14, accountId);
		ps.setString(15, mtrRplcStageId);
		ps.setString(16, billRoundId);
		i = ps.executeUpdate();
		AppLog.info("For KNO::" + accountId + "::rows updated::" + i);
		// System.out.println("updateMigrationMrdDetails: " + i);
		ps.close();
		AppLog.info("Success update Migration MRD Details for KNO::"
				+ accountId);
		AppLog.end();
		return i;
	}

	/**
	 * <p>
	 * Step3: Wrt the zone,mrno,area and wcno fetched from djb_main bring
	 * LastOkRead details of the consumers from DJB_MAIN_HIST_2 and update these
	 * details in staging table
	 * 
	 * Step3:b. Based on this details retrieve cmrdt(LAST_OK_DT) from
	 * djb_main_hist_2
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param mtrInstallDate
	 *            - sql date field, Meter Installation Date
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private Date selectLastOkDate(String zone, String mrno, String areano,
			String wcno, Date mtrInstallDate) throws SQLException {
		AppLog.begin();
		// System.out.println("In selectLastOkDate");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date lastOkDate = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT Max(cmrdt) AS lastOkDate ");
		var1.append("FROM   djb_main_hist_2 ");
		var1.append("WHERE  zoneno = ? ");
		var1.append("       AND mrno = ? ");
		var1.append("       AND areano = ? ");
		var1.append("       AND wcno = ? ");
		var1.append("       AND consum > 0 ");
		// var1.append("       AND cmrdt < To_date(?) - 1 ");
		var1.append("       AND cmrdt < ? - 1 ");
		var1.append("GROUP  BY zoneno, ");
		var1.append("          mrno, ");
		var1.append("          areano, ");
		var1.append("          wcno");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		ps.setDate(5, mtrInstallDate);
		rs = ps.executeQuery();
		while (rs.next()) {
			lastOkDate = rs.getDate("lastOkDate");
			accountBeanObj.setLastOkDt(lastOkDate);
		}
		/*
		 * System.out.println("selectLastOkDate: " +
		 * accountBeanObj.getLastOkDt());
		 */
		ps.close();
		AppLog.info("Success select Last Ok Date for KNO::" + accountId + "::"
				+ lastOkDate);
		AppLog.end();
		return lastOkDate;
	}

	/**
	 * <p>
	 * Step3:c. Based on the cmrdt(LAST_OK_DT) retrieved above select
	 * cmr(LAST_OK_READ),status(LAST_STATUS) from djb_main_hist_2
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param lastOkDate
	 *            - date field, Last Ok Date retrieved above
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void selectLastOkReadAndStatus(String zone, String mrno,
			String areano, String wcno, Date lastOkDate) throws SQLException {
		AppLog.begin();
		// System.out.println("In selectLastOkReadAndStatus");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT cmr AS cmr, ");
		var1.append("       status AS status ");
		var1.append("FROM   djb_main_hist_2 ");
		var1.append("WHERE  zoneno = ? ");
		var1.append("       AND mrno = ? ");
		var1.append("       AND areano = ? ");
		var1.append("       AND wcno = ? ");
		var1.append("       AND consum > 0 ");
		var1.append("       AND cmrdt = ?");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		ps.setDate(5, lastOkDate);
		rs = ps.executeQuery();
		while (rs.next()) {
			accountBeanObj.setLastOkRead(rs.getDouble("cmr"));
			accountBeanObj.setLastStatus(rs.getString("status"));
		}
		// System.out.println("LastOkRead : " + accountBeanObj.getLastOkRead());
		// System.out.println("LastStatus : " + accountBeanObj.getLastStatus());
		ps.close();
		AppLog.info("Success select Last Ok Read And Status for KNO::"
				+ accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Step3:d. Based on the cmrdt(LAST_OK_DT) retrieved above select
	 * TOBEPAID(ADJ-AR) into (v_tbp) from djb_main_hist_2
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param lastOkDate
	 *            - date field, Last Ok Date retrieved above
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void selectToBePaid(String zone, String mrno, String areano,
			String wcno, Date lastOkDate) throws SQLException {
		AppLog.begin();
		AppLog.info("zone::" + zone + "::mrno::" + mrno + "::areano::" + areano
				+ "::wcno::" + wcno + "::lastOkDate::" + lastOkDate);
		// System.out.println("In selectToBePaid");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT  nvl(tobepaid, 0) AS tbp ");
		var1.append("FROM   djb_main_hist_2 ");
		var1.append("WHERE  zoneno = ? ");
		var1.append("       AND mrno = ? ");
		var1.append("       AND areano = ? ");
		var1.append("       AND wcno = ? ");
		var1.append("       AND consum > 0 ");
		var1.append("       AND cmrdt = ?");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		ps.setDate(5, lastOkDate);
		rs = ps.executeQuery();
		while (rs.next()) {
			AppLog.info("To Be Paid Amount for KNO::" + accountId + "::"
					+ rs.getDouble("tbp"));
			accountBeanObj.setTbp(rs.getDouble("tbp"));
		}
		// System.out.println("TBP : " + accountBeanObj.getTbp());
		ps.close();
		AppLog.info("Success Select To Be Paid Amount for KNO::" + accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Step3:e. Based on the cmrdt(LAST_OK_DT) retrieved above select adjustment
	 * SUM(recivedamt)into(v_pymnt) from djb_main_hist_2
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param lastOkDate
	 *            - date field, Last Ok Date retrieved above
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void selectRecievedAmount(String zone, String mrno, String areano,
			String wcno, Date lastOkDate) throws SQLException {
		AppLog.begin();
		// System.out.println("In selectRecievedAmount");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT SUM(recivedamt) AS pymnt ");
		var1.append("FROM   djb_main_hist_2 ");
		var1.append("WHERE  zoneno = ? ");
		var1.append("       AND mrno = ? ");
		var1.append("       AND areano = ? ");
		var1.append("       AND wcno = ? ");
		var1.append("       AND cmrdt >= ? ");
		var1.append("GROUP  BY zoneno, ");
		var1.append("          mrno, ");
		var1.append("          areano, ");
		var1.append("          wcno");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		ps.setDate(5, lastOkDate);
		rs = ps.executeQuery();
		while (rs.next()) {
			accountBeanObj.setPymnt(rs.getDouble("pymnt"));
		}
		// System.out.println("Payment : " + accountBeanObj.getPymnt());
		ps.close();
		/* START:Added by Matiur Rahman as per JTrac ID #DJB-1588 on 11-06-2013. */
		/* Get Meter Replacement Adjustment Amount */
		double arrearAdjustmentAmt = getMeterReplacementAdjustment(zone, mrno,
				areano, wcno, lastOkDate);
		/* Add Meter Replacement Adjustment Amount if it is greater than Zero. */
		if (arrearAdjustmentAmt > 0) {
			accountBeanObj.setPymnt(accountBeanObj.getPymnt()
					+ arrearAdjustmentAmt);
		}
		/* END:Added by Matiur Rahman as per JTrac ID #DJB-1588 on 11-06-2013. */
		AppLog.info("Success Select Recieved Amount for KNO::" + accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Get the Meter Replacement Adjustment which is the <code>RECIVEDAMT</code>
	 * of the table <code>DJB_MAIN</code> for the Zone, MR, Area and WC No
	 * combination.This is added as per JTrac ID #DJB-1588 by Matiur Rahman on
	 * 11-06-2013.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-06-2013.
	 * @param zone
	 * @param mrno
	 * @param areano
	 * @param wcno
	 * @param lastOkDate
	 * @throws SQLException
	 */
	private double getMeterReplacementAdjustment(String zone, String mrno,
			String areano, String wcno, Date lastOkDate) throws SQLException {
		AppLog.begin();
		double arrearAdjustmentAmt = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer querySB = new StringBuffer(700);
		querySB.append("SELECT RECIVEDAMT FROM DJB_MAIN ");
		querySB.append("WHERE  zoneno = ? ");
		querySB.append("       AND mrno = ? ");
		querySB.append("       AND areano = ? ");
		querySB.append("       AND wcno = ? ");
		querySB.append("       AND MRD_TYPE NOT IN ('BULK','GWC','GCR') ");

		ps = conn.prepareStatement(querySB.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		rs = ps.executeQuery();
		while (rs.next()) {
			arrearAdjustmentAmt = rs.getDouble("RECIVEDAMT");
		}
		if (null != ps) {
			ps.close();
		}
		if (null != rs) {
			rs.close();
		}
		AppLog.info("Arrear Adjustment Amount for KNO::" + accountId
				+ ":: is ::" + arrearAdjustmentAmt);
		AppLog.end();
		return arrearAdjustmentAmt;
	}

	/**
	 * <p>
	 * Step3:f. Based on the cmrdt(LAST_OK_DT) retrieved above select adjustment
	 * (SUM(FEESAMOUNT), SUM(DELETION), SUM(FINEFECT)) INTO v_fa, v_del, v_fin
	 * from djb_main_hist_2
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param lastOkDate
	 *            - date field, Last Ok Date retrieved above
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void selectOtherAdjustments(String zone, String mrno,
			String areano, String wcno, Date lastOkDate) throws SQLException {
		AppLog.begin();
		// System.out.println("In selectOtherAdjustments");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer var1 = new StringBuffer(700);
		var1.append("SELECT SUM(feesamount) AS adjfa, ");
		var1.append("       SUM(deletion)   AS adjdel, ");
		var1.append("       SUM(finefect)   AS adjfin ");
		var1.append("FROM   djb_main_hist_2 ");
		var1.append("WHERE  zoneno = ? ");
		var1.append("       AND mrno = ? ");
		var1.append("       AND areano = ? ");
		var1.append("       AND wcno = ? ");
		var1.append("       AND cmrdt > ? ");
		var1.append("GROUP  BY zoneno, ");
		var1.append("          mrno, ");
		var1.append("          areano, ");
		var1.append("          wcno");

		ps = conn.prepareStatement(var1.toString());
		ps.setString(1, zone);
		ps.setString(2, mrno);
		ps.setString(3, areano);
		ps.setString(4, wcno);
		ps.setDate(5, lastOkDate);
		rs = ps.executeQuery();
		while (rs.next()) {
			accountBeanObj.setAdjFa(rs.getDouble("adjfa"));
			accountBeanObj.setAdjDel(rs.getDouble("adjdel"));
			accountBeanObj.setAdjFin(rs.getDouble("adjfin"));
		}
		/*
		 * System.out.println("AdjFa : " + accountBeanObj.getAdjFa());
		 * System.out.println("AdjDel : " + accountBeanObj.getAdjDel());
		 * System.out.println("AdjFin : " + accountBeanObj.getAdjFin());
		 */
		ps.close();
		AppLog.info("Success Select Other Adjustments for KNO::" + accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Step3:g. Update above retrieved records into staging tables with proper
	 * WHO details.
	 * </p>
	 * 
	 * @param zone
	 *            - Zone retrieved from djb_main
	 * @param mrno
	 *            - Mrno retrieved from djb_main
	 * @param areano
	 *            - Areano retrieved from djb_main
	 * @param wcno
	 *            - water connection number
	 * @param lastOkDate
	 *            - date field, Last Ok Date retrieved above
	 * @param lastStatus
	 *            - status on Last Ok Date
	 * @param lastOkRead
	 *            - numeric field , cmr on LastOkDate
	 * @param v_tbp
	 *            - tobepaid adjustment amount retrieved above
	 * @param v_pymnt
	 *            - recievedamount adjustment retrieved above
	 * @param v_del
	 *            - deletion adjustment amount retrieved above
	 * @param v_fa
	 *            - feesamount adjustment amount retrieved above
	 * @param v_fin
	 *            - finefect adjustment amount retrieved above
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated variable type from Double
	 *          to double and Application log for better traceability for the
	 *          change as per JTrac ID#DJB-2024.
	 * 
	 */
	private int updateLastReadDetails(String zone, String mrno, String areano,
			String wcno, Date lastOkDate, String lastStatus, double lastOkRead,
			double tbp, double pymnt, double adjDel, double adjFa, double adjFin)
			throws SQLException {
		AppLog.begin();
		// System.out.println("In updateLastReadDetails");
		PreparedStatement ps = null;
		StringBuffer var1 = new StringBuffer(700);
		int i = 0;
		var1.append("UPDATE cm_mtr_rplc_stage ");
		var1.append("SET    last_ok_dt = ?, ");
		var1.append("       last_ok_read = ?, ");
		var1.append("       last_status = ?, ");
		var1.append("       tbp = ?, ");
		var1.append("       pymnt = ?, ");
		var1.append("       adj_del = ?, ");
		var1.append("       adj_fa = ?, ");
		var1.append("       adj_fin = ?, ");
		var1.append("       last_updt_dttm = SYSDATE, ");
		var1.append("       last_updt_by = 'DE_SCREEN' ");
		var1.append("WHERE  old_zonecd = ? ");
		var1.append("       AND old_mrno = ? ");
		var1.append("       AND old_areano = ? ");
		var1.append("       AND wcno = ?");

		ps = conn.prepareStatement(var1.toString());
		ps.setDate(1, lastOkDate);
		ps.setDouble(2, lastOkRead);
		ps.setString(3, lastStatus);
		ps.setDouble(4, tbp);
		ps.setDouble(5, pymnt);
		ps.setDouble(6, adjDel);
		ps.setDouble(7, adjFa);
		ps.setDouble(8, adjFin);
		ps.setString(9, zone);
		ps.setString(10, mrno);
		ps.setString(11, areano);
		ps.setString(12, wcno);
		i = ps.executeUpdate();
		AppLog.info("Update Last Read Details For KNO::" + accountId
				+ "::rows updated::" + i);
		/*
		 * System.out.println("i=" + i + "\nlastOkDate: " + lastOkDate +
		 * "\nlastOkRead: " + lastOkRead + "\nlastStatus: " + lastStatus +
		 * "\ntbp: " + tbp + "\npymnt: " + pymnt + "\nadjDel: " + adjDel +
		 * "\nadjFa: " + adjFa + "\nadjFin: " + adjFin + "\nzone: " + zone +
		 * "\nmrno: " + mrno + "\nareano: " + areano + "\nwcno: " + wcno);
		 */
		ps.close();
		AppLog.info("Success update Last Read Details for KNO::" + accountId);
		AppLog.end();
		return i;
	}

	/**
	 * <p>
	 * A wrapper function to shift SA for Consumers
	 * </p>
	 * 
	 * @throws SQLException
	 * @throws SQLException
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void shiftSAForConsumersDE(String mtrRplcStageId, String accountId,
			String billRoundId) throws SQLException {
		AppLog.begin();
		int i = 0;
		Date lastOkDate = null;
		// When Status is updated successfully, select Migration Details of
		// this consumer to Shift its SA
		errorMessage = "Error while Selecting Migration Details for SA Shift case in Freezing Process.";
		AppLog.info("Select Migration Details for SA Shift case for KNO::"
				+ accountId);
		selectMigrationDetails(accountBeanObj.getMtrRplcStagId(), accountId,
				accountBeanObj.getBillRoundId());

		if (null != accountBeanObj.getOldZonecd()) {
			errorMessage = "Error while Updating Migration Details for SA Shift case in Freezing Process.";
			// Once Migration data is selected, update this data to Meter
			// Replacement Staging Table
			i = updateMigrationMrdDetails(accountBeanObj.getOldZonecd(),
					accountBeanObj.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getOldMrkey(), mtrRplcStageId, accountId,
					billRoundId);
			AppLog.info("Rows Updated in updateMigrationMrdDetails::" + i
					+ ":: For KNO::" + accountId);
		}
		if (i == 1) {
			i = 0;
			errorMessage = "Error while Selecting Last Ok Date for SA Shift case in Freezing Process.";
			// Once Migration details are updated, Select LastOkRead Details
			// from DJB_MAIN_HIST_2
			AppLog.info("Select Last Ok Date for SA Shift case for KNO::"
					+ accountId);
			lastOkDate = selectLastOkDate(accountBeanObj.getOldZonecd(),
					accountBeanObj.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), accountBeanObj
							.getMtrInstallDate());
		}
		AppLog.info("Last Ok Date for SA Shift case for KNO::" + accountId
				+ "::" + lastOkDate);
		if (null != lastOkDate) {
			errorMessage = "Error while Selecting Last Ok Read and Status for SA Shift case in Freezing Process.";
			// Once lastOkDate is retrieved, retrieve the lastOkRead and
			// lastStatus on this lastOkDate
			AppLog
					.info("Select Last Ok Read And Status for SA Shift case for KNO::"
							+ accountId);
			selectLastOkReadAndStatus(accountBeanObj.getOldZonecd(),
					accountBeanObj.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), lastOkDate);
			errorMessage = "Error while Selecting Amount to be paid for SA Shift case in Freezing Process.";
			// Retrieve all five (ADJ_PA,ADJ_AR,ADJ_DEL,ADJ_FA,ADJ_FIN)
			// adjustments for the consumer
			AppLog.info("Select Amount To Be Paid for SA Shift case for KNO::"
					+ accountId);
			selectToBePaid(accountBeanObj.getOldZonecd(), accountBeanObj
					.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), lastOkDate);
			errorMessage = "Error while Selecting Recieved Amount for SA Shift case in Freezing Process.";
			AppLog.info("Select Recieved Amount for SA Shift case for KNO::"
					+ accountId);
			selectRecievedAmount(accountBeanObj.getOldZonecd(), accountBeanObj
					.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), lastOkDate);
			errorMessage = "Error while Selecting Other Adjustments for SA Shift case in Freezing Process.";
			AppLog.info("Select Other Adjustments for SA Shift case for KNO::"
					+ accountId);
			selectOtherAdjustments(accountBeanObj.getOldZonecd(),
					accountBeanObj.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), lastOkDate);
			errorMessage = "Error while Updating Last Read Details for SA Shift case in Freezing Process.";
			// Update Staging table with above selected migration data
			AppLog.info("Update Last Read Details for SA Shift case for KNO::"
					+ accountId);
			i = updateLastReadDetails(accountBeanObj.getOldZonecd(),
					accountBeanObj.getOldMrno(), accountBeanObj.getOldAreano(),
					accountBeanObj.getWcno(), lastOkDate, accountBeanObj
							.getLastStatus(), accountBeanObj.getLastOkRead(),
					accountBeanObj.getTbp(), accountBeanObj.getPymnt(),
					accountBeanObj.getAdjDel(), accountBeanObj.getAdjFa(),
					accountBeanObj.getAdjFin());
			AppLog.info("Rows Updated in updateLastReadDetails::" + i
					+ ":: For KNO::" + accountId);
		}
		AppLog.info("Success shift SA For Consumer's Data Entry for KNO::"
				+ accountId);
		AppLog.end();
	}

	/**
	 * <p>
	 * Primary Function called from outside class to provide the account and
	 * meterReplacementStatus
	 * </p>
	 * 
	 * @param accountId
	 *            - Account Id/KNO of the Consumer
	 * @throws SQLException
	 * @throws Exception
	 * @history 13-02-2014 Matiur Rahman Added/Updated Application log for
	 *          better traceability for the change as per JTrac ID#DJB-2024.
	 */
	private void startPreProcessingOnMtrdAccount() throws Exception {
		AppLog.begin();
		AppLog.info("Started Pre Processing On the Metered Account for KNO::"
				+ accountId);
		// System.out.println("In startPreProcessingOnMtrdAccount");
		init(accountId);
		/** Update status_id to pre-processing */
		Map<String, String> inputMapForMRDFreezePreProcessing = new HashMap<String, String>();
		inputMapForMRDFreezePreProcessing.put("knoList", accountId);
		inputMapForMRDFreezePreProcessing.put("billRoundList", billRoundId);
		/*
		 * Start:Matiur Rahman on 14-02-2014 Added userId as parameter to update
		 * the who columns as per JTrac ID#2024.
		 */
		inputMapForMRDFreezePreProcessing.put("userId", userId);
		/*
		 * End:Matiur Rahman on 14-02-2014 Added userId as parameter to update
		 * the who columns as per JTrac ID#2024.
		 */
		errorMessage = "Error while Updating the status for Pre-processing in Freezing Process.";
		/* Update CONS_PRE_BILL_STAT_ID to 64 */
		MeterReplacementReviewDAO
				.freezeMeterReplacementPreProcessing(inputMapForMRDFreezePreProcessing);
		// savepoint1 = conn.setSavepoint();
		/** To check if a consumer is a SA shift consumer and Shift SA */
		AppLog.info("Started Checking SA Shift case for KNO::" + accountId);
		errorMessage = "Error while checking If SA Shift Case in Freezing Process.";
		checkIfSAShiftCase(mtrRplcsStageId, accountId, billRoundId);

		// Call main Shift SA Function shiftSAForConsumers for above consumer
		if (accountBeanObj.getIsSaShiftCase()) {
			errorMessage = "Error while shifting SA For the Consumer in Freezing Process.";
			AppLog
					.info("This is as SA Shif Case, Start shift SA For Consumer's Data Entry for KNO : "
							+ accountId);
			shiftSAForConsumersDE(mtrRplcsStageId, accountId, billRoundId);
		}
		AppLog.info("Success Pre Processing On the Metered Account for KNO::"
				+ accountId);
		AppLog.end();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		try {
			errorMessage = "Error while Starting Pre-processing On Metered Consumer in Freezing Process.";
			/* Start Pre-Processing On Metered Account */
			startPreProcessingOnMtrdAccount();
			Map<String, String> inputMapForMRDFreeze = new HashMap<String, String>();
			inputMapForMRDFreeze.put("knoList", accountId);
			inputMapForMRDFreeze.put("billRoundList", billRoundId);
			inputMapForMRDFreeze.put("userId", userId);
			errorMessage = "Error while Updating Freezen Status in Freezing Process.";
			int recordFrozen = MeterReplacementReviewDAO
					.freezeMeterReplacement(inputMapForMRDFreeze);
			if (recordFrozen > 0) {
				AppLog.info("Commiting All Transaction for KNO::" + accountId);
				errorMessage = "Error while commiting all Transactions in Freezing Process.";
				conn.commit();
				AppLog.info("Successfully Processed for KNO::" + accountId);
				/* Update the Error message. */
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("kno", accountId);
				inputMap.put("billRoundId", billRoundId);
				inputMap.put("userId", userId);
				inputMap.put("errorMessage", "");
				MeterReplacementReviewDAO
						.updateProcessingErrorMessage(inputMap);
			} else {
				AppLog.info("Failled to Process for KNO::" + accountId);
				/* Roll back Freeze MeterReplacement Pre Processing Status. */
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("kno", accountId);
				inputMap.put("billRoundId", billRoundId);
				inputMap.put("userId", userId);
				MeterReplacementReviewDAO
						.rollbackFreezeMeterReplacementPreProcessing(inputMap);
				/* Update the Error message. */
				inputMap.put("errorMessage", errorMessage);
				MeterReplacementReviewDAO
						.updateProcessingErrorMessage(inputMap);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.error(e);
			/*
			 * try { if (null != conn && null != savepoint1)
			 * conn.rollback(savepoint1); //
			 * System.out.println("Rollback savepoint 1");
			 * AppLog.info("For account Id::" + accountId +
			 * " Rollback savepoint 1"); } catch (SQLException e1) {
			 * AppLog.error(e1); AppLog.info("For account Id::" + accountId +
			 * " Exception while Rollback!!!"); // e1.printStackTrace(); //
			 * System.out.println("########### Exception while Rollback!!!"); }
			 */
			AppLog.info("Failled to Process for KNO::" + accountId);
			/* Roll back Freeze MeterReplacement Pre Processing Status. */
			Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("kno", accountId);
			inputMap.put("billRoundId", billRoundId);
			inputMap.put("userId", userId);
			MeterReplacementReviewDAO
					.rollbackFreezeMeterReplacementPreProcessing(inputMap);
			/* Update the Error message. */
			inputMap.put("errorMessage", errorMessage);
			MeterReplacementReviewDAO.updateProcessingErrorMessage(inputMap);
			throw new RuntimeException();
		} finally {
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
	}
}
