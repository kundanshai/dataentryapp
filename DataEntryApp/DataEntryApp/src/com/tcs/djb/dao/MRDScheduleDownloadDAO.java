/************************************************************************************************************
 * @(#) MRDScheduleDownloadDAO.java   29 Oct 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.MRDScheduleDetails;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.PremiseDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.CreateZIP;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.ExcelUtil;
import com.tcs.djb.util.PropertyUtil;

/**
 * <p>
 * DAO Class for Mrd Schedule Download Screen.
 * </p>
 * 
 * @author admin
 * @history 29-OCT-2012 bency.baby Updated the select query to pull all records
 *          from an MRD. Updated the joins on Primary Key.
 * @history 15-11-2012 Added ZRO and Area Description by Matiur Rahman
 * @history 23-11-2012 Changed Query for the MRD Download Changing Previous
 *          average read to Previous Actual read by Matiur Rahman.
 * @history Madhuri on 6th-06-2016 Added methods
 *          {@link #getMrdCode(String,String,String)},{@link #setDownloadedFileDetails(String,String)} to get Mrkey,insert download data into database
 *          , JTrac DJB-4464.
 * @history Madhuri on 6th-06-2016 Modified methods
 *          {@link #createMRDScheduleFile()} to change the data source
 *          , JTrac DJB-4464.
 * @history Madhuri on 10th-08-2016 Added methods
 *         {@link #setDownloadedFileDetailsForAllUsr(String,String,String ,String)} to insert values of files details in stg table
 *         Jtrac DJB-4538 and open project id -> 1429   
 */
public class MRDScheduleDownloadDAO implements Runnable {
	private String areaCode;
	private String billWindow;
	private String jSessionID;
	private String mrCode;
	private String searchCriteria;
	private String userID;
	private String zoneCode;

	/**
	 * <p>
	 * This is a Constructor of MRDScheduleDownloadDAO class
	 * </p>
	 * 
	 * @param zoneCode
	 * @param mrCode
	 * @param areaCode
	 * @param billWindow
	 * @param userID
	 * @param jSessionID
	 * @param searchCriteria
	 */
	public MRDScheduleDownloadDAO(String zoneCode, String mrCode,
			String areaCode, String billWindow, String userID,
			String jSessionID, String searchCriteria) {
		AppLog.begin();
		// System.out.println("IN MRDScheduleDownloadDAO");
		this.zoneCode = zoneCode;
		this.mrCode = mrCode;
		this.areaCode = areaCode;
		this.billWindow = billWindow;
		this.userID = userID;
		this.jSessionID = jSessionID;
		this.searchCriteria = searchCriteria;

		new Thread(this).start();
		// System.out.println("OUT MRDScheduleDownloadDAO");
		AppLog.end();

	}
	
 
 

	/**
	 * <p>
	 * This method is used to append Logs
	 * </p>
	 * 
	 * @param userName
	 * @param fileName
	 * @param message
	 */
	public void appendLog(String userName, String fileName, String message) {
		try {
			// System.out.println("Message::" + message);
			String logFilePath = PropertyUtil.getProperty("UCMdocumentUpload")
					+ "/" + userName + "/" + fileName;
			// System.out.println("Log File Path::" + logFilePath);
			File file = new File(logFilePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(logFilePath, true)));
			out.println(message);
			out.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	
	
	
	
	/**
	 * <p>
	 * This method is used to create MRD Schedule Files
	 * </p>
	 */
	public void createMRDScheduleFile() {
		AppLog.begin();
		// System.out.println("Entering createMRDScheduleFile");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sequenceNo = null;
		int totalFileToBeCreated = 0;
		try {

			AppLog.info("MRDSchedule File Creation STARTED for::zoneCode::"
					+ zoneCode + "::mrCode::" + mrCode + "::areaCode::"
					+ areaCode + "::billWindow::" + billWindow);

			// ArrayList<MRDContainer> mrdContainerList = new
			// ArrayList<MRDContainer>();
			try {
				// conn = DBConnector.getConnection();

				StringBuffer sqlSelectSB = new StringBuffer();
				StringBuffer sqlSlctCmd = new StringBuffer();
				StringBuffer sqlInsertSB = new StringBuffer();
				sqlInsertSB
						.append(" insert into MRD_SCHEDUL_DNLD_LOG(SEQ_NO,ZONE_CODE,MR_NO,AREA_CODE,BILL_WINDOW, SEARCH_CRITERIA, J_SESSION_ID, STATUS, CREATED_BY,TOTAL_FILE_TO_BE_CREATED) ");
				sqlInsertSB
						.append(" values((select count(SEQ_NO)  from MRD_SCHEDUL_DNLD_LOG)+1,?,?,?,?,?,?,'Submitted',?,?)");
				StringBuffer sqlUpdateSB = new StringBuffer();
				sqlUpdateSB
						.append(" update MRD_SCHEDUL_DNLD_LOG set STATUS='In Progress',TOTAL_FILE_CREATED=? ");
				sqlUpdateSB
						.append(" where 1=1  and BILL_WINDOW=?  and SEARCH_CRITERIA=?  and J_SESSION_ID=?  and CREATED_BY=? ");

				// // Main query to retrieve details to be writen in the xls
				// StringBuffer sqlConsumerDetailsSB = new StringBuffer();
				// sqlConsumerDetailsSB.append("SELECT X.mrkey, ");
				// sqlConsumerDetailsSB.append("       X.billround_id, ");
				// sqlConsumerDetailsSB.append("       X.mtrrdrname, ");
				// sqlConsumerDetailsSB.append("       X.mtr_rdr_diary_cd, ");
				// sqlConsumerDetailsSB.append("       X.service_cycle, ");
				// sqlConsumerDetailsSB.append("       X.route, ");
				// sqlConsumerDetailsSB.append("       X.start_date, ");
				// sqlConsumerDetailsSB.append("       X.end_date, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(Trim(X.svccycrteseq), '9999999')                 SEQ, ");
				// sqlConsumerDetailsSB.append("       X.acctid, ");
				// sqlConsumerDetailsSB.append("       X.wcno, ");
				// sqlConsumerDetailsSB.append("       X.conname, ");
				// sqlConsumerDetailsSB.append("       X.consumer_address, ");
				// sqlConsumerDetailsSB
				// .append("       X.custclcd                                           CAT, ");
				// sqlConsumerDetailsSB.append("       X.unoc_dwel_units, ");
				// sqlConsumerDetailsSB.append("       X.ocu_dwel_units, ");
				// sqlConsumerDetailsSB.append("       X.totl_dewl_units, ");
				// sqlConsumerDetailsSB.append("       X. no_of_floor, ");
				// sqlConsumerDetailsSB.append("       X.no_of_fs_beds_rooms, ");
				// sqlConsumerDetailsSB.append("       X.mtr_number, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(To_char(X.obj.readdate, 'DD/MM/YYYY'), 'NA')     PREVMTRRDDT, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(X.obj.remark, 'NA')                              PREVMTRRDSTAT, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(X.obj.readtype, 'NA')                            PREVREADTYPE, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(Trim(X.obj.registeredread), 'NA')                PREVRGMTRRD, ");
				// sqlConsumerDetailsSB
				// .append("       X.cons                                               PREVCONS, ");
				// sqlConsumerDetailsSB.append("       X.eff_no_of_days, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(X.avgread, 'NA')                                 AVGREAD, ");
				// sqlConsumerDetailsSB
				// .append("       X.satypecd                                           SATYPECD, ");
				// sqlConsumerDetailsSB.append("       X.svccycrteseq, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(To_char(X.lastbillgendate, 'DD-MON-YYYY'), 'NA') LASTBILLGENDATE, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(X.billgendate, ' ')                              CURR_BILLGENDATE, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(X.readerremcd, ' ')                              CURR_READERREMCD, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(Trim(X.regread), ' ')                            CURR_REGREAD, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(Trim(X.prebillstatid), ' ')                      CURR_PREBILLSTATID, ");
				// sqlConsumerDetailsSB
				// .append("       Nvl(Trim(X.newavgread), ' ')                         CURR_NEWAVGREAD ");
				// sqlConsumerDetailsSB
				// .append("FROM   (SELECT cm_cons_pre_bill_proc.acct_id ");
				// sqlConsumerDetailsSB.append("               ACCTID, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.bill_round_id ");
				// sqlConsumerDetailsSB
				// .append("                      BILLROUND_ID, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.avg_read ");
				// sqlConsumerDetailsSB.append("               AVGREAD, ");
				// sqlConsumerDetailsSB
				// .append("               To_char(cm_cons_pre_bill_proc.bill_gen_date, 'DD/MM/YYYY') ");
				// sqlConsumerDetailsSB
				// .append("                      BILLGENDATE, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.consumer_name ");
				// sqlConsumerDetailsSB.append("               CONNAME, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.cons_pre_bill_stat_id ");
				// sqlConsumerDetailsSB
				// .append("                      PREBILLSTATID, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.cust_cl_cd ");
				// sqlConsumerDetailsSB.append("               CUSTCLCD, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.last_bill_gen_date ");
				// sqlConsumerDetailsSB
				// .append("                      LASTBILLGENDATE, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.last_updt_by ");
				// sqlConsumerDetailsSB.append("               LASTUPDTBY ");
				// sqlConsumerDetailsSB.append("                      , ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.last_updt_dttm ");
				// sqlConsumerDetailsSB
				// .append("                      LASTUPDTTM, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.mrkey ");
				// sqlConsumerDetailsSB.append("               MRKEY, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.mtr_reader_name ");
				// sqlConsumerDetailsSB.append("               MTRRDRNAME ");
				// sqlConsumerDetailsSB.append("                      , ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.new_avg_read ");
				// sqlConsumerDetailsSB
				// .append("                      NEWAVGREAD, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.reader_rem_cd ");
				// sqlConsumerDetailsSB
				// .append("                      READERREMCD, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.read_type_flg ");
				// sqlConsumerDetailsSB.append("               READTYPFLG ");
				// sqlConsumerDetailsSB.append("                      , ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.reg_reading ");
				// sqlConsumerDetailsSB.append("                      REGREAD, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.sa_type_cd ");
				// sqlConsumerDetailsSB.append("               SATYPECD, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.svc_cyc_rte_seq ");
				// sqlConsumerDetailsSB
				// .append("                      SVCCYCRTESEQ, ");
				// sqlConsumerDetailsSB
				// .append("               cm_cons_pre_bill_proc.wcno ");
				// sqlConsumerDetailsSB.append("               WCNO, ");
				// // Commented on 23-11-2012 by Matiur Rahman as per JTrac
				// issue
				// // DJB-677
				// // sqlConsumerDetailsSB
				// //
				// .append("               cisadm.Cm_get_last_actual_meter_read(cm_cons_pre_bill_proc.acct_id)  OBJ ");
				// // sqlConsumerDetailsSB.append("               , ");
				// // Added on 23-11-2012 by Matiur Rahman as per JTrac issue
				// // DJB-677
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_get_last_actual_meter_read(cm_cons_pre_bill_proc.acct_id)  OBJ ");
				// sqlConsumerDetailsSB.append("               , ");
				// sqlConsumerDetailsSB
				// .append("               djb_zn_mr_ar_mrd.old_mrd_cd ");
				// sqlConsumerDetailsSB
				// .append("                      MTR_RDR_DIARY_CD, ");
				// sqlConsumerDetailsSB.append("               ci_sp.mr_cyc_cd ");
				// sqlConsumerDetailsSB
				// .append("                      SERVICE_CYCLE, ");
				// sqlConsumerDetailsSB.append("               ci_sp.mr_rte_cd ");
				// sqlConsumerDetailsSB.append("               ROUTE, ");
				// sqlConsumerDetailsSB
				// .append("               To_char(cm_bill_window.start_date, 'DD/MM/YYYY') ");
				// sqlConsumerDetailsSB.append("               START_DATE ");
				// sqlConsumerDetailsSB.append("                      , ");
				// sqlConsumerDetailsSB
				// .append("               To_char(cm_bill_window.end_date, 'DD/MM/YYYY') ");
				// sqlConsumerDetailsSB.append("                      END_DATE, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'NDU-U') ");
				// sqlConsumerDetailsSB
				// .append("                      UNOC_DWEL_UNITS, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'NDU-O') ");
				// sqlConsumerDetailsSB
				// .append("                      OCU_DWEL_UNITS, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'TNBD') ");
				// sqlConsumerDetailsSB
				// .append("                      TOTL_DEWL_UNITS, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_prem_char_val(ci_sa.char_prem_id, 'FLOOR') ");
				// sqlConsumerDetailsSB
				// .append("                      NO_OF_FLOOR, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_fs_beds_rooms(ci_sa.char_prem_id) ");
				// sqlConsumerDetailsSB
				// .append("                      NO_OF_FS_BEDS_ROOMS, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'AVGCONS') ");
				// sqlConsumerDetailsSB.append("               AVG_CONS, ");
				// sqlConsumerDetailsSB.append("               ci_mtr.badge_nbr ");
				// sqlConsumerDetailsSB.append("               MTR_NUMBER ");
				// sqlConsumerDetailsSB.append("                      , ");
				// sqlConsumerDetailsSB
				// .append("               ci_bseg_read.msr_qty ");
				// sqlConsumerDetailsSB.append("                      CONS, ");
				// sqlConsumerDetailsSB
				// .append("               ( ci_bseg_read.end_read_dttm - ci_bseg_read.start_read_dttm ) ");
				// sqlConsumerDetailsSB
				// .append("                      EFF_NO_OF_DAYS, ");
				// sqlConsumerDetailsSB
				// .append("               cisadm.Cm_get_add_str(ci_sa.char_prem_id) ");
				// sqlConsumerDetailsSB
				// .append("                      CONSUMER_ADDRESS ");
				// sqlConsumerDetailsSB
				// .append("        FROM   cisadm.djb_zn_mr_ar_mrd ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.cm_mrd_processing_stat ");
				// sqlConsumerDetailsSB
				// .append("                       ON cm_mrd_processing_stat.mrkey = djb_zn_mr_ar_mrd.mrd_cd ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.cm_bill_window ");
				// sqlConsumerDetailsSB
				// .append("                       ON cm_bill_window.bill_round_id = ");
				// sqlConsumerDetailsSB
				// .append("                          cm_mrd_processing_stat.bill_round_id ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.cm_cons_pre_bill_proc ");
				// sqlConsumerDetailsSB
				// .append("                       ON cm_cons_pre_bill_proc.mrkey = ");
				// sqlConsumerDetailsSB
				// .append("                          cm_mrd_processing_stat.mrkey ");
				// sqlConsumerDetailsSB
				// .append("                          AND cm_cons_pre_bill_proc.bill_round_id = ");
				// sqlConsumerDetailsSB
				// .append("                              cm_bill_window.bill_round_id ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.ci_sa ");
				// sqlConsumerDetailsSB
				// .append("                       ON cm_cons_pre_bill_proc.sa_type_cd = ci_sa.sa_type_cd ");
				// sqlConsumerDetailsSB
				// .append("                          AND cm_cons_pre_bill_proc.acct_id = ci_sa.acct_id ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.ci_sa_sp ");
				// sqlConsumerDetailsSB
				// .append("                       ON ci_sa.sa_id = ci_sa_sp.sa_id ");
				// sqlConsumerDetailsSB
				// .append("               inner join cisadm.ci_sp ");
				// sqlConsumerDetailsSB
				// .append("                       ON ci_sa_sp.sp_id = ci_sp.sp_id ");
				// sqlConsumerDetailsSB
				// .append("               left join cisadm.ci_sp_mtr_hist ");
				// sqlConsumerDetailsSB
				// .append("                      ON ci_sp.sp_id = ci_sp_mtr_hist.sp_id ");
				// sqlConsumerDetailsSB
				// .append("               left join cisadm.ci_mtr_config ");
				// sqlConsumerDetailsSB
				// .append("                      ON ci_sp_mtr_hist.mtr_config_id = ");
				// sqlConsumerDetailsSB
				// .append("                         ci_mtr_config.mtr_config_id ");
				// sqlConsumerDetailsSB
				// .append("               left join cisadm.ci_mtr ");
				// sqlConsumerDetailsSB
				// .append("                      ON ci_mtr.mtr_id = ci_mtr_config.mtr_id ");
				// sqlConsumerDetailsSB
				// .append("               left join cisadm.ci_bseg ");
				// sqlConsumerDetailsSB
				// .append("                      ON ci_bseg.sa_id = ci_sa.sa_id ");
				// sqlConsumerDetailsSB
				// .append("                         AND Trunc(ci_bseg.cre_dttm) = ");
				// sqlConsumerDetailsSB
				// .append("                             cm_cons_pre_bill_proc.last_bill_gen_date ");
				// sqlConsumerDetailsSB
				// .append("               left join cisadm.ci_bseg_read ");
				// sqlConsumerDetailsSB
				// .append("                      ON ci_bseg.bseg_id = ci_bseg_read.bseg_id ");
				// sqlConsumerDetailsSB
				// .append("                         AND ci_sp.sp_id = ci_bseg_read.sp_id ");
				// sqlConsumerDetailsSB
				// .append("        WHERE  cm_bill_window.bill_win_stat_id = '10' ");
				// // Added for issue of repeated account id for multiple Meter
				// // Number
				// sqlConsumerDetailsSB
				// .append(" AND (ci_sp_mtr_hist.removal_dttm is null OR ci_sp_mtr_hist.removal_dttm = (SELECT MAX(to_date(decode(to_char(ci_sp_mtr_hist.removal_dttm,'dd/mm/yyyy'),'',to_char(sysdate, 'dd/mm/yyyy'),null,to_char(sysdate, 'dd/mm/yyyy'),to_char(ci_sp_mtr_hist.removal_dttm,'dd/mm/yyyy')),'dd/mm/yyyy')) FROM ci_sp_mtr_hist WHERE ci_sp_mtr_hist.sp_id = ci_sp.sp_id))");
				// sqlConsumerDetailsSB
				// .append("               AND cm_cons_pre_bill_proc.mrkey = ? ");
				// sqlConsumerDetailsSB
				// .append("               AND cm_cons_pre_bill_proc.bill_round_id = ?) X ");
				// sqlConsumerDetailsSB.append("ORDER  BY To_number(seq) ASC ");
				// System.out.println("Query ConsumerDetails:::"
				// + sqlConsumerDetailsSB.toString());
				ArrayList<MRDScheduleDetails> mrdList = new ArrayList<MRDScheduleDetails>();

				if ((null != billWindow) && !"".equalsIgnoreCase(billWindow)
						&& null != zoneCode && !"".equalsIgnoreCase(zoneCode)) {
					// GETTING ALL MRKEY FOR THE COMBINATION
					// AppLog.info("chckd bill wndw n zone");
					sqlSlctCmd
							.append("select distinct cm_cons_pre_bill_proc.bill_round_id , cm_cons_pre_bill_proc.MRKEY ,");

					sqlSlctCmd.append("djb_zn_mr_ar_mrd.subzone_cd ,");

					sqlSlctCmd.append("djb_zn_mr_ar_mrd.MR_CD ,");
					sqlSlctCmd
							.append("djb_zn_mr_ar_mrd.AREA_CD,djb_zn_mr_ar_mrd.AREA_CD || '/' || djb_zn_mr_ar_mrd.area_desc AREA_DESC, ");
					sqlSlctCmd.append("djb_zro.zro_desc, ");
					sqlSlctCmd
							.append("to_char(sysdate,'dd-mm-yyyy') curr_date ");

					sqlSlctCmd.append("FROM cm_cons_pre_bill_proc  ");
					sqlSlctCmd
							.append("inner join djb_zn_mr_ar_mrd on djb_zn_mr_ar_mrd.mrd_cd = cm_cons_pre_bill_proc.MRKEY  ");
					sqlSlctCmd
							.append("inner join djb_mrd on djb_mrd.mrd_cd = djb_zn_mr_ar_mrd.mrd_cd ");
					sqlSlctCmd
							.append("left join djb_zro on djb_zro.subzone_cd = djb_zn_mr_ar_mrd.subzone_cd and djb_zro.zro_cd = djb_mrd.zro_cd ");
					sqlSlctCmd.append(" where 1=1 ");
					// Commented by Matiur Rahman for adding ZRO
					// sqlSlctCmd
					// .append(" where cm_cons_pre_bill_proc.MRKEY = djb_zn_mr_ar_mrd.MRD_CD ");
					// sqlSlctCmd
					// .append(" and djb_zro.subzone_cd = djb_zn_mr_ar_mrd.subzone_cd ");
					// sqlSlctCmd
					// .append(" and djb_mrd.mrd_cd = djb_zn_mr_ar_mrd.mrd_cd ");
					// sqlSlctCmd.append(" and djb_mrd.zro_cd = djb_zro.zro_cd ");
					sqlSlctCmd
							.append(" and cm_cons_pre_bill_proc.bill_round_id =? ");

					sqlSlctCmd.append(" and djb_zn_mr_ar_mrd.subzone_cd=? ");

					if (null != mrCode && !"".equals(mrCode)) {

						sqlSlctCmd.append(" and djb_zn_mr_ar_mrd.MR_CD =? ");
					}
					if (null != areaCode && !"".equals(areaCode)) {

						sqlSlctCmd.append(" and djb_zn_mr_ar_mrd.AREA_CD =? ");
					}
					sqlSlctCmd
							.append(" order by subzone_cd,to_number(MR_CD),to_number(AREA_CD) ");
					int i = 0;
					if (null == conn || conn.isClosed()) {
						conn = DBConnector.getConnection();
					}
					ps = conn.prepareStatement(sqlSlctCmd.toString());
					ps.setString(++i, billWindow);
					ps.setString(++i, zoneCode);
					if (null != mrCode && !"".equals(mrCode)) {

						ps.setString(++i, mrCode);
					}
					if (null != areaCode && !"".equals(areaCode)) {

						ps.setString(++i, areaCode);

					}
					// System.out.println("Query>>>>" + sqlSlctCmd.toString());
					rs = ps.executeQuery();
					while (rs.next()) {
						MRDScheduleDetails mrdScheduleDetails = new MRDScheduleDetails();
						mrdScheduleDetails.setBillRoundId(rs
								.getString("bill_round_id"));
						mrdScheduleDetails.setZone(rs.getString("subzone_cd"));
						mrdScheduleDetails.setMrNo(rs.getString("MR_CD"));
						mrdScheduleDetails.setAreaNo(rs.getString("AREA_CD"));
						mrdScheduleDetails.setAreaDesc(rs
								.getString("AREA_DESC"));
						mrdScheduleDetails.setZroCode(rs.getString("zro_desc"));
						mrdScheduleDetails.setMrkey(rs.getString("MRKEY"));
						mrdScheduleDetails.setScheduleDownloadDate(rs
								.getString("curr_date"));

						mrdList.add(mrdScheduleDetails);
					}
					if (null != ps && !ps.isClosed()) {
						ps.close();
					}
					if (null != rs && !rs.isClosed()) {
						rs.close();
					}
					if (null != conn && !conn.isClosed()) {
						conn.close();
					}
				}
				totalFileToBeCreated = mrdList.size();
				// CREATING ENTRY IN THE DATABASE
				// AppLog.info("Total File ToBe Created Size::"
				// + totalFileToBeCreated);
				if (null == conn || conn.isClosed()) {
					conn = DBConnector.getConnection();
				}
				ps = conn.prepareStatement(sqlInsertSB.toString());
				ps.setString(1, zoneCode);
				ps.setString(2, mrCode);
				ps.setString(3, areaCode);
				ps.setString(4, billWindow);
				ps.setString(5, searchCriteria);
				ps.setString(6, jSessionID);
				ps.setString(7, userID);
				ps.setInt(8, totalFileToBeCreated);
				int rowInserted = ps.executeUpdate();
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
				if (rowInserted > 0) {
					// IF CREATING ENTRY IN THE DATABASE SUCCESSFULL GET SEQ
					// NUMBER
					sqlSelectSB
							.append(" select SEQ_NO from MRD_SCHEDUL_DNLD_LOG ");
					sqlSelectSB.append(" where 1=1 ");
					sqlSelectSB.append(" and BILL_WINDOW=? ");
					sqlSelectSB.append(" and SEARCH_CRITERIA=? ");
					sqlSelectSB.append(" and J_SESSION_ID=? ");
					sqlSelectSB.append(" and CREATED_BY=? ");
					if (null == conn || conn.isClosed()) {
						conn = DBConnector.getConnection();
					}
					ps = conn.prepareStatement(sqlSelectSB.toString());
					ps.setString(1, billWindow);
					ps.setString(2, searchCriteria);
					ps.setString(3, jSessionID);
					ps.setString(4, userID);
					rs = ps.executeQuery();
					while (rs.next()) {
						sequenceNo = rs.getString("SEQ_NO");
					}
					if (null != ps && !ps.isClosed()) {
						ps.close();
					}
					if (null != rs && !rs.isClosed()) {
						rs.close();
					}
					if (null != conn && !conn.isClosed()) {
						conn.close();
					}
				}

				String[] fileNames = new String[totalFileToBeCreated + 1];
				String scheduleDownloadDate = null;
				int fileCreated = 0;
				java.util.Date startDate = new java.util.Date();
				Timestamp startTime = new Timestamp(startDate.getTime());

				String logMessage = "Created by:" + userID + "\nCreate Time:"
						+ startTime.toString()
						+ "\nTotal No of files to be created:"
						+ totalFileToBeCreated;

				for (int j = 0; j < totalFileToBeCreated; j++) {
					// CREATING FILE ONE BY ONE
					boolean isSucces = true;
					MRDScheduleDetails mrdScheduleDetails = (MRDScheduleDetails) mrdList
							.get(j);
					String mrKey = mrdScheduleDetails.getMrkey();
					String billRoundId = mrdScheduleDetails.getBillRoundId();
					if (j == 0) {
						fileNames[0] = searchCriteria + "_log.txt";
						appendLog(userID, fileNames[0], logMessage);
					}
					ArrayList<ConsumerDetail> consumerList = new ArrayList<ConsumerDetail>();
					ConsumerDetail consumerDetail = null;
					PremiseDetails premiseDetails = null;
					// AppLog.info("Query::MRKEY::" + mrKey + "::billRoundId::"
					// + billRoundId + "::" + sqlSlctCmd.toString());
					try {
						// GETTING CONSUMER DETAILS FOR THE MRD
						//Commented by Madhuri :- 27th June 2016 as per Jtrac DJB-4464 
						/*if (null == conn || conn.isClosed()) {
							conn = DBConnector.getConnection();
						}*/
						//Added by Madhuri :- 27th June 2016 as per Jtrac DJB-4464 
						if (null == conn || conn.isClosed()) {
							conn = DBConnector.getConnection(DJBConstants.JNDI_DS_MRD_SCHEDULE, DJBConstants.JNDI_PROVIDER_MRD_SCHEDULE);
							AppLog.info("Data Source Name>>"+DJBConstants.JNDI_DS_MRD_SCHEDULE + " Provider Url>>" +DJBConstants.JNDI_PROVIDER_MRD_SCHEDULE);
							
						}
						//Ended by Madhuri :- 27th June 2016 as per Jtrac DJB-4464. 
						// AppLog.info("sqlConsumerDetailsSB:::" + mrKey + "::"
						// + mrdScheduleDetails.getZone() + "::"
						// + mrdScheduleDetails.getMrNo() + "::"
						// + mrdScheduleDetails.getAreaNo() + "::"
						// + billRoundId + "::"
						// + sqlConsumerDetailsSB.toString());
						// System.out.println("sqlConsumerDetailsSB:::" + mrKey
						// + "::" + mrdScheduleDetails.getZone() + "::"
						// + mrdScheduleDetails.getMrNo() + "::"
						// + mrdScheduleDetails.getAreaNo() + "::"
						// + billRoundId + "::"
						// + sqlConsumerDetailsSB.toString());
						ps = conn.prepareStatement(QueryContants
								.mrdScheduleDownloadQuery());

						ps.setString(1, mrKey);
						ps.setString(2, billRoundId);
						rs = ps.executeQuery();
						int seqNo = 0;
						while (rs.next()) {
							mrdScheduleDetails.setMeterReader(rs
									.getString("MTRRDRNAME"));
							mrdScheduleDetails.setMeterReadDiaryCode(rs
									.getString("MTR_RDR_DIARY_CD"));
							mrdScheduleDetails.setServiceCycle(rs
									.getString("SERVICE_CYCLE"));
							mrdScheduleDetails.setRoute(rs.getString("ROUTE"));
							mrdScheduleDetails.setStartDate(rs
									.getString("START_DATE"));
							mrdScheduleDetails.setEndDate(rs
									.getString("END_DATE"));
							consumerDetail = new ConsumerDetail();
							billRoundId = rs.getString("BILLROUND_ID");
							mrKey = rs.getString("MRKEY");
							consumerDetail.setSeqNo(seqNo);
							consumerDetail.setServiceCycleRouteSeq(rs
									.getString("SVCCYCRTESEQ"));
							consumerDetail.setKno(rs.getString("ACCTID"));
							consumerDetail.setWcNo(rs.getString("WCNO"));
							consumerDetail.setBillRound(rs
									.getString("BILLROUND_ID"));
							consumerDetail.setName(rs.getString("CONNAME"));
							consumerDetail.setAddress(rs
									.getString("CONSUMER_ADDRESS"));
							consumerDetail.setCat(rs.getString("CAT"));
							// Premise details
							premiseDetails = new PremiseDetails();
							premiseDetails.setUnocDwelUnits(rs
									.getString("UNOC_DWEL_UNITS"));
							premiseDetails.setOcuDwelUnits(rs
									.getString("OCU_DWEL_UNITS"));
							premiseDetails.setTotlDewlUnits(rs
									.getString("TOTL_DEWL_UNITS"));
							premiseDetails.setNoOfFloor(rs
									.getString("NO_OF_FLOOR"));
							premiseDetails.setNoOfFuncSitesBedsRooms(rs
									.getString("NO_OF_FS_BEDS_ROOMS"));
							consumerDetail.setPremiseDetails(premiseDetails);
							String SATYPECD = rs.getString("SATYPECD");
							// if ("SAWATSEW".equalsIgnoreCase(SATYPECD)
							// || "SAWATER".equalsIgnoreCase(SATYPECD)
							// || "SAWATR".equalsIgnoreCase(SATYPECD)) {
							// SATYPECD = "METERED";
							// } else {
							// SATYPECD = "UNMETERED";
							// }
							consumerDetail.setServiceType(SATYPECD);
							consumerDetail.setLastBillGenerationDate(rs
									.getString("LASTBILLGENDATE"));
							String cs = rs.getString("SATYPECD");
							if (null != cs && !"".equals(cs)) {
								consumerDetail
										.setConsumerStatusLookup(ConsumerStatusLookup
												.getConsumerStatusForStatCd(60));
							}
							consumerDetail.setConsumerStatus(rs
									.getString("CURR_PREBILLSTATID"));
							consumerDetail.setCurrentAvgConsumption(rs
									.getString("AVGREAD"));
							MeterReadDetails prevMeterReadDetails = new MeterReadDetails();
							prevMeterReadDetails.setMeterNumber(rs
									.getString("MTR_NUMBER"));
							prevMeterReadDetails.setMeterReadDate(rs
									.getString("PREVMTRRDDT"));
							prevMeterReadDetails.setMeterStatus(rs
									.getString("PREVMTRRDSTAT"));
							prevMeterReadDetails.setRegRead(rs
									.getString("PREVRGMTRRD"));
							prevMeterReadDetails.setReadType(rs
									.getString("PREVREADTYPE"));
							// MeterReadDetails actualMeterReadDetails = new
							// MeterReadDetails();
							// actualMeterReadDetails.setMeterReadDate(rs
							// .getString("ACTLMTRRDDT"));
							// actualMeterReadDetails.setMeterStatus(rs
							// .getString("ACTLMTRRDSTAT"));
							// actualMeterReadDetails.setRegRead(rs
							// .getString("ACTLRGMTRRD"));
							// actualMeterReadDetails.setReadType(rs
							// .getString("ACTLReadType"));
							// consumerDetail
							// .setPrevActualMeterRead(actualMeterReadDetails);
							MeterReadDetails curMeterReadDetails = new MeterReadDetails();
							curMeterReadDetails.setMeterReadDate(rs
									.getString("CURR_BILLGENDATE"));
							curMeterReadDetails.setMeterStatus(rs
									.getString("CURR_READERREMCD"));
							curMeterReadDetails.setRegRead(rs
									.getString("CURR_REGREAD"));
							curMeterReadDetails.setAvgRead(rs
									.getString("CURR_NEWAVGREAD"));

							consumerDetail
									.setPrevMeterRead(prevMeterReadDetails);

							consumerDetail
									.setCurrentMeterRead(curMeterReadDetails);
							consumerList.add(consumerDetail);
							seqNo++;
						}
						if (null != ps && !ps.isClosed()) {
							ps.close();
						}
						if (null != rs && !rs.isClosed()) {
							rs.close();
						}
						if (null != conn && !conn.isClosed()) {
							conn.close();
						}
					} catch (Exception e) {
						// IF GETTING CONSUMER DETAILS FOR THE MRD FAILES
						String failedFor = mrdScheduleDetails.getZone() + "-MR"
								+ mrdScheduleDetails.getMrNo() + "-A"
								+ mrdScheduleDetails.getAreaNo();
						// AppLog.info("OPERATION FAILED FOR::MRKEY::" + mrKey
						// + "::BILLROUND ID::" + billRoundId + "::"
						// + failedFor);
						logMessage = "\n" + mrdScheduleDetails.getZone() + "_"
								+ mrdScheduleDetails.getMrNo() + "_"
								+ mrdScheduleDetails.getAreaNo() + "_"
								+ mrdScheduleDetails.getBillRoundId() + "_"
								+ scheduleDownloadDate + ".xls" + ": FAILED";
						appendLog(userID, fileNames[0], logMessage);
						isSucces = false;
						try {
							StringBuffer sqlUpdateFailedSB = new StringBuffer();
							sqlUpdateFailedSB
									.append("  update MRD_SCHEDUL_DNLD_LOG set REMARKS=REMARKS||'"
											+ failedFor + ", '");
							sqlUpdateFailedSB.append(" where 1=1 ");
							sqlUpdateFailedSB.append(" and BILL_WINDOW=? ");
							sqlUpdateFailedSB.append(" and SEARCH_CRITERIA=? ");
							sqlUpdateFailedSB.append(" and J_SESSION_ID=? ");
							sqlUpdateFailedSB.append(" and CREATED_BY=? ");
							// System.out.println("sqlUpdateFailedSB::"
							// + sqlUpdateFailedSB.toString());
							if (null == conn || conn.isClosed()) {
								conn = DBConnector.getConnection();
							}
							ps = conn.prepareStatement(sqlUpdateFailedSB
									.toString());
							ps.setString(1, billWindow);
							ps.setString(2, searchCriteria);
							ps.setString(3, jSessionID);
							ps.setString(4, userID);
							// int rowUpdated =
							ps.executeUpdate();
							// System.out.println("rowUpdated::" + rowUpdated);
							if (null != ps && !ps.isClosed()) {
								ps.close();
							}
							if (null != rs && !rs.isClosed()) {
								rs.close();
							}
							if (null != conn && !conn.isClosed()) {
								conn.close();
							}
						} catch (Exception ex) {
							// ex.printStackTrace();
						}
					}
					// System.out.println("consumerList size::::"
					// + consumerList.size());
					// AppLog.info("consumerList size::::" +
					// consumerList.size());
					if (isSucces) {
						scheduleDownloadDate = mrdScheduleDetails
								.getScheduleDownloadDate();
						ExcelUtil excelUtil = new ExcelUtil();
						fileNames[j + 1] = mrdScheduleDetails.getZone() + "_"
								+ mrdScheduleDetails.getMrNo() + "_"
								+ mrdScheduleDetails.getAreaNo() + "_"
								+ mrdScheduleDetails.getBillRoundId() + "_"
								+ scheduleDownloadDate + ".xls";
						// AppLog.info("SEND FOR EXCEL CREATION OF::"
						// + fileNames[j + 1]);
						excelUtil.createMRDScheduleExcel(fileNames[j + 1],
								consumerList, mrdScheduleDetails, userID);
						fileCreated++;
						// AppLog.info("CREATED EXCEL::" + fileNames[j + 1]);
						logMessage = "\n" + fileNames[j + 1] + ": SUCCESS";
						appendLog(userID, fileNames[0], logMessage);
						// System.out
						// .println("sqlUpdateSB::" + sqlUpdateSB.toString());
						if (null == conn || conn.isClosed()) {
							conn = DBConnector.getConnection();
						}
						ps = conn.prepareStatement(sqlUpdateSB.toString());
						ps.setInt(1, fileCreated);
						ps.setString(2, billWindow);
						ps.setString(3, searchCriteria);
						ps.setString(4, jSessionID);
						ps.setString(5, userID);
						int rowUpdated = ps.executeUpdate();
						if (!(rowUpdated > 0)) {
							AppLog.info(" Update Failed for " + searchCriteria
									+ " created by " + userID);
						}
						if (null != ps && !ps.isClosed()) {
							ps.close();
						}
						if (null != rs && !rs.isClosed()) {
							rs.close();
						}
						if (null != conn && !conn.isClosed()) {
							conn.close();
						}
					}
				}
				// System.out.println("fileNames.length::" + fileNames.length);
				if (fileNames.length > 0) {

					StringBuffer zipFileNameSB = new StringBuffer(512);
					zipFileNameSB.append("MRDSchedule_");
					zipFileNameSB.append(zoneCode);
					if (null != mrCode && !"".equals(mrCode)) {
						zipFileNameSB.append("_");
						zipFileNameSB.append(mrCode);
					}
					if (null != areaCode && !"".equals(areaCode)) {
						zipFileNameSB.append("_");
						zipFileNameSB.append(areaCode);
					}
					zipFileNameSB.append("_");
					zipFileNameSB.append(billWindow);
					zipFileNameSB.append("_");
					zipFileNameSB.append(scheduleDownloadDate);
					String zipFileName = zipFileNameSB.toString() + ".zip";
					// System.out.println("Creating Zip::" + zipFileName);
					java.util.Date endDate = new java.util.Date();
					Timestamp endTime = new Timestamp(endDate.getTime());

					logMessage = "\nComplete Time:" + endTime.toString()
							+ "\nTotal No of files created:" + fileCreated
							+ "\nZip file Name:" + zipFileName;
					appendLog(userID, fileNames[0], logMessage);
					CreateZIP createZIP = new CreateZIP();
					boolean isSuccess = createZIP.createZIP(fileNames,
							zipFileName, userID);
					if (isSuccess) {

						// System.out.println("Zip file Created");
						sqlUpdateSB = new StringBuffer();
						sqlUpdateSB
								.append(" update MRD_SCHEDUL_DNLD_LOG set STATUS='Completed',FILE_NAME=?");
						sqlUpdateSB.append(" where 1=1 ");
						sqlUpdateSB.append(" and BILL_WINDOW=? ");
						sqlUpdateSB.append(" and SEARCH_CRITERIA=? ");
						sqlUpdateSB.append(" and J_SESSION_ID=? ");
						sqlUpdateSB.append(" and CREATED_BY=? ");
						if (null == conn || conn.isClosed()) {
							conn = DBConnector.getConnection();
						}
						ps = conn.prepareStatement(sqlUpdateSB.toString());
						ps.setString(1, zipFileName);
						ps.setString(2, billWindow);
						ps.setString(3, searchCriteria);
						ps.setString(4, jSessionID);
						ps.setString(5, userID);
						int rowUpdated = ps.executeUpdate();
						if (!(rowUpdated > 0)) {
							AppLog
									.info(" Update Failed for after creation Zip for"
											+ searchCriteria
											+ " created by "
											+ userID);
						}
						if (null != ps && !ps.isClosed()) {
							ps.close();
						}
						if (null != rs && !rs.isClosed()) {
							rs.close();
						}
						if (null != conn && !conn.isClosed()) {
							conn.close();
						}
						isSuccess = createZIP.deleteIncludedFilesInZIP(userID,
								fileNames);
						if (isSuccess) {
							// System.out.println("Included Files In ZIP deleted");
							AppLog
									.info(" Included Files In ZIP deleted Successfully for"
											+ searchCriteria
											+ " created by "
											+ userID);

						} else {
							AppLog
									.info(" Deletetion Failed for after creation Zip for"
											+ searchCriteria
											+ " created by "
											+ userID);
						}
					} else {
						AppLog
								.error(new Throwable(
										"UNABLE TO CREATE ZIP FILE FOR USER::"
												+ userID));
					}
				}

			} catch (SQLException e) {
				AppLog.error(e);
				AppLog.error(e);
			} catch (Exception e) {
				AppLog.error(e);
			}
			AppLog.info("MRDSchedule File Creation ENDED for::zoneCode::"
					+ zoneCode + "::mrCode::" + mrCode + "::areaCode::"
					+ areaCode + "::billWindow::" + billWindow);
			// System.out.println("OUT downloadMRD");

		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				// FINALLY UPDATING STATUS

				StringBuffer sqlUpdateFailSB = new StringBuffer();
				sqlUpdateFailSB
						.append(" update MRD_SCHEDUL_DNLD_LOG set STATUS='Failed' ");
				sqlUpdateFailSB.append(" where 1=1 ");
				sqlUpdateFailSB
						.append(" and total_file_created=total_file_to_be_created ");
				sqlUpdateFailSB.append(" and STATUS='In Progress'");
				if (null == conn || conn.isClosed()) {
					conn = DBConnector.getConnection();
				}
				ps = conn.prepareStatement(sqlUpdateFailSB.toString());
				// int rowUpdated =
				ps.executeUpdate();
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				StringBuffer sqlUpdatePartialSB = new StringBuffer();
				sqlUpdatePartialSB
						.append(" update MRD_SCHEDUL_DNLD_LOG set STATUS='Incomplete' ");
				sqlUpdatePartialSB.append(" where 1=1 ");
				sqlUpdatePartialSB.append(" and STATUS='Completed'");
				sqlUpdatePartialSB
						.append(" and total_file_created <> total_file_to_be_created ");
				if (null == conn || conn.isClosed()) {
					conn = DBConnector.getConnection();
				}
				ps = conn.prepareStatement(sqlUpdatePartialSB.toString());
				// rowUpdated =
				ps.executeUpdate();
				if (null != ps && !ps.isClosed()) {
					ps.close();
				}
				if (null != rs && !rs.isClosed()) {
					rs.close();
				}
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// AppLog.error(e);
				AppLog
						.info("FATAL ERROR IN FINAL BLOCK::MRDSchedule File Creation ENDED for::zoneCode::"
								+ zoneCode
								+ "::mrCode::"
								+ mrCode
								+ "::areaCode::"
								+ areaCode
								+ "::billWindow::"
								+ billWindow);

			} finally {
				try {
					if (null != ps && !ps.isClosed()) {
						ps.close();
					}
					if (null != rs && !rs.isClosed()) {
						rs.close();
					}
					if (null != conn && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
					// AppLog.error(e);
				}
			}
		}
		AppLog.end();
	}
	/**
	 * <p>
	 * Query to retrieve mrkey from selected ZONE,MR and AREA.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 03-JUNE-2016
	 * @return query String
	 * @as per Jtrac DJB-4464 and Open project id - 1203
	 */
	public static String getMrdCode(String zoneCode, String mrCode, String areaCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String validMrkey =null;
		
		try {
			String query = QueryContants.getMrdCode();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, zoneCode);
			ps.setString(2, mrCode);
			ps.setString(3, areaCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				validMrkey =rs.getString("MRKEY");
				AppLog.info("Mrkey selected for new job submission is: " +rs.getString("MRKEY"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != rs) {
					rs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return validMrkey;
	}
	
	/**
	 * <p>
	 * Query to insert details of downloaded file in djb_hhd_mrd_dwnld_stat.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 03-JUNE-2016
	 * @return query String
	 * @as per Jtrac DJB-4464 and Open project id - 1203
	 */
	public static String  setDownloadedFileDetails(String mrCode,String billWindow) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = "Invalid";
		try {
			String query = QueryContants.setFileDetails();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, mrCode);
			ps.setString(2, billWindow);
			
			
			
			rs = ps.executeQuery();
			if(rs != null ){
				status = "valid";
				
			}
			
		} catch (SQLException e) {
			AppLog.error(e);
		}  catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != rs) {
					rs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				AppLog.error(e);
			}
		}
		AppLog.end();
		return status;
		
	}
	/**
	 * <p>
	 * Query to insert details of downloaded file in djb_hhd_mrd_dwnld_stat for all users.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 10-AUG-2016
	 * @return query String
	 * @as per Jtrac DJB-4538 and Open project id - 1429
	 */
	public static String setDownloadedFileDetailsForAllUsr(String billRoundId,String zoneCode, String mrCode,String areaCode){
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = "Invalid";
		
		try {
	 StringBuffer  setValForDwnldFile = new StringBuffer();
	 setValForDwnldFile.append("insert into djb_hhd_mrd_dwnld_stat ");
	 setValForDwnldFile.append("    (MRKEY, BILL_ROUND_ID, REQ_SRC, VERSION_NO, DWNLD_DT) ");
	 setValForDwnldFile.append("    select mrd_cd MRKEY, ");
	 setValForDwnldFile.append("'"+billRoundId+"'");
	 setValForDwnldFile.append(" as BILL_ROUND_ID, ");
	 setValForDwnldFile.append("           'DataEntryApp' as REQ_SRC, ");
	 setValForDwnldFile.append("           '1' as VERSION_NO, ");
	 setValForDwnldFile.append("           SYSDATE as DWNLD_DT ");
	 setValForDwnldFile.append("      from djb_zn_mr_ar_mrd m ");
	 setValForDwnldFile.append("     where 1 = 1 ");
	 
	if(null !=zoneCode && !"".equalsIgnoreCase(zoneCode)){
		setValForDwnldFile.append("       and m.subzone_cd = ? ");
		}
	 if(null !=mrCode && !"".equalsIgnoreCase(mrCode)){
		 setValForDwnldFile.append("       and m.mr_cd = ? "); 
	 }
	 if(null !=areaCode && !"".equalsIgnoreCase(areaCode)){
		 setValForDwnldFile.append("       and m.area_cd = ?");
	 }
	
	 conn = DBConnector.getConnection();
	 ps = conn.prepareStatement(setValForDwnldFile.toString());
	 AppLog.info("<<<<Print query >>>>>>" +setValForDwnldFile.toString());
	if(null !=zoneCode && !"".equalsIgnoreCase(zoneCode)){
	 ps.setString(1, zoneCode);
	}
	if(null !=mrCode && !"".equalsIgnoreCase(mrCode)){
	 ps.setString(2, mrCode);
	}
	if(null !=areaCode && !"".equalsIgnoreCase(areaCode)){
	 ps.setString(3, areaCode);
	}
	 rs =ps.executeQuery();
	 if(rs != null ){
			status = "valid";
			
	}
	
	} catch (SQLException e) {
		AppLog.error(e);
	}  catch (Exception e) {
		AppLog.error(e);
	} 
	finally{
		try {
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			if (null != conn) {
				conn.close();
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
	}
		return status;
		
	}
		
	
	

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @return the billWindow
	 */
	public String getBillWindow() {
		return billWindow;
	}

	/**
	 * @return the jSessionID
	 */
	public String getjSessionID() {
		return jSessionID;
	}

	/**
	 * @return the mrCode
	 */
	public String getMrCode() {
		return mrCode;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		AppLog.info("NEW THREAD STARTED for::zoneCode::" + zoneCode
				+ "::mrCode::" + mrCode + "::areaCode::" + areaCode
				+ "::billWindow::" + billWindow);
		// TODO Auto-generated method stub
		// System.out.println("IN run");
		try {

			createMRDScheduleFile();
			AppLog.info("NEW THREAD ENDED for::zoneCode::" + zoneCode
					+ "::mrCode::" + mrCode + "::areaCode::" + areaCode
					+ "::billWindow::" + billWindow);
			AppLog.end();
		} catch (Throwable t) {
			AppLog.error(t);
		}
		// System.out.println("OUT run");
		AppLog.end();

	}

	/**
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @param billWindow
	 */
	public void setBillWindow(String billWindow) {
		this.billWindow = billWindow;
	}

	/**
	 * @param jSessionID
	 *            the jSessionID to set
	 */
	public void setjSessionID(String jSessionID) {
		this.jSessionID = jSessionID;
	}

	/**
	 * @param mrCode
	 */
	public void setMrCode(String mrCode) {
		this.mrCode = mrCode;
	}

	/**
	 * @param searchCriteria
	 *            the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @param zoneCode
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
}
