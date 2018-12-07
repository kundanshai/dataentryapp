/************************************************************************************************************
 * @(#) DiconnectionDAO.java
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.djb.model.DisconnectionDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO for saving Disconnection Details
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services)
 */
public class DiconnectionDAO {
	/**
	 * <p>
	 * This method is used to save disconnection details
	 * </p>
	 * 
	 * @param disconnectionDetails
	 * @return boolean save Meter Replacement Detail
	 */
	public boolean saveDisconnectionDetail(
			DisconnectionDetails disconnectionDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		try {
			System.out.println(">>billRound>>>>"
					+ disconnectionDetails.getBillRound() + ">>>kno>>>"
					+ disconnectionDetails.getKno() + ">>>zone>>"
					+ disconnectionDetails.getZone() + ">>mrNo>>>"
					+ disconnectionDetails.getMrNo() + ">>area>>>"
					+ disconnectionDetails.getArea() + ">>>wcNo>>>"
					+ disconnectionDetails.getWcNo() + ">>name>>"
					+ disconnectionDetails.getName() + ">>category>>"
					+ disconnectionDetails.getConsumerCategory()
					+ ">>ConsumetType>>"
					+ disconnectionDetails.getConsumerType()
					+ ">>>currentMeterReadDate>>>"
					+ disconnectionDetails.getCurrentMeterReadDate()
					+ ">>>currentMeterRegisterRead>>>"
					+ disconnectionDetails.getCurrentMeterRegisterRead()
					+ ">>currentMeterReadRemarkCode>>"
					+ disconnectionDetails.getCurrentMeterReadRemarkCode()
					+ ">>newAverageConsumption>>"
					+ disconnectionDetails.getNewAverageConsumption()
					+ ">>UserName>>" + disconnectionDetails.getCreatedByID());

			conn = DBConnector.getConnection();
			int recordUpdated = 0;
			StringBuffer querySelectSB = new StringBuffer(512);
			querySelectSB
					.append("  select acct_id,bill_round_id from CM_MTR_RPLC_STAGE where acct_id=? and bill_round_id=? ");
			// System.out.println("::Query::" + querySelectSB.toString());
			ps = conn.prepareStatement(querySelectSB.toString());
			int i = 0;
			ps.setString(++i, disconnectionDetails.getKno());
			ps.setString(++i, disconnectionDetails.getBillRound());
			rs = ps.executeQuery();
			if (rs.next()) {
				// StringBuffer querySB = new StringBuffer(512);
				// querySB
				// .append(" update CM_MTR_RPLC_STAGE set zonecd=?,mrno=?,areano=?,wcno=?,mtr_install_date=to_date(?,'DD/MM/YYYY'),consumer_name=?,sa_type_cd=?,mtrtyp=?,reader_rem_cd=?,mtr_reader_id=?,badge_nbr=?,model_cd=?,mtr_start_read=?,old_mtr_read=?,wconsize=?,wconuse=?,is_lnt_srvc_prvdr=?,cust_cl_cd=?,mtr_rplc_stage_id=?,last_updt_dttm=sysdate,last_updt_by=?,oldavgcons=? ");
				// querySB.append(" where acct_id=? and bill_round_id=? ");
				//
				// // System.out.println("::Query::" + querySB.toString());
				// if (null != ps) {
				// ps.close();
				// }
				// ps = conn.prepareStatement(querySB.toString());
				// i = 0;
				// ps.setString(++i, disconnectionDetails.getZone());
				// ps.setString(++i, disconnectionDetails.getMrNo());
				// ps.setString(++i, disconnectionDetails.getArea());
				// ps.setString(++i, disconnectionDetails.getWcNo());
				// ps.setString(++i, disconnectionDetails.getName());
				// ps.setString(++i, disconnectionDetails
				// .getCurrentMeterReadRemarkCode());
				// ps.setString(++i,
				// disconnectionDetails.getConsumerCategory());
				// ps.setString(++i, disconnectionDetails.getCreatedByID());
				// ps.setString(++i, disconnectionDetails.getKno());
				// ps.setString(++i, disconnectionDetails.getBillRound());
				// recordUpdated = ps.executeUpdate();
				// } else {
				// StringBuffer querySB = new StringBuffer(512);
				// querySB.append(" insert into cm_mtr_rplc_stage ");
				// querySB
				// .append(" (zonecd,mrno,areano,wcno,acct_id,mtr_install_date,bill_round_id,mrkey,consumer_name,sa_type_cd,mtrtyp,reader_rem_cd,mtr_reader_id,badge_nbr,model_cd,mtr_start_read,old_mtr_read,wconsize,wconuse,is_lnt_srvc_prvdr,cust_cl_cd,mtr_rplc_stage_id, ");
				// // added oldavgcons by matiur Rahman
				// querySB.append(" create_dttm,created_by,oldavgcons )");
				// querySB
				// .append(" values(?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,(select z.mrd_cd from djb_zn_mr_ar_mrd z where z.subzone_cd=? and z.mr_cd=? and z.area_cd=?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)");
				//
				// // System.out.println("::Query::" + querySB.toString());
				// if (null != ps) {
				// ps.close();
				// }
				// ps = conn.prepareStatement(querySB.toString());
				// i = 0;
				// ps.setString(++i, disconnectionDetails.getZone());
				// ps.setString(++i, disconnectionDetails.getMrNo());
				// ps.setString(++i, disconnectionDetails.getArea());
				// ps.setString(++i, disconnectionDetails.getWcNo());
				// ps.setString(++i, disconnectionDetails.getKno());
				// ps.setString(++i, disconnectionDetails.getBillRound());
				// ps.setString(++i, disconnectionDetails.getZone());
				// ps.setString(++i, disconnectionDetails.getMrNo());
				// ps.setString(++i, disconnectionDetails.getArea());
				// ps.setString(++i, disconnectionDetails.getName());
				// ps.setString(++i, disconnectionDetails
				// .getCurrentMeterReadRemarkCode());
				// ps.setString(++i,
				// disconnectionDetails.getConsumerCategory());
				// ps.setString(++i, disconnectionDetails.getCreatedByID());
				// recordUpdated = ps.executeUpdate();
				//
				// }
				// if (recordUpdated > 0) {
				// if (null != ps) {
				// ps.close();
				// }
				// StringBuffer querySB = new StringBuffer(512);
				// querySB.append(" update cm_cons_pre_bill_proc t set ");
				// querySB.append(" t.cons_pre_bill_stat_id='"
				// + ConsumerStatusLookup.DISCONNECTED.getStatusCode()
				// + "', ");
				// querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
				// querySB.append(" t.reg_reading=?,");
				// querySB
				// .append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
				// querySB.append(" t.reader_rem_cd=?,");
				// querySB.append(" t.new_avg_read=?,");
				// querySB.append(" t.last_updt_dttm=sysdate,");
				// querySB.append(" t.last_updt_by=?");
				// querySB.append(" where 1=1");
				// querySB.append(" AND  ACCT_ID=?");
				// querySB.append(" AND  BILL_ROUND_ID=?");
				//
				// System.out.println("::UPDATE Status::" + querySB.toString());
				// ps = conn.prepareStatement(querySB.toString());
				// i = 0;
				// ps.setString(++i, disconnectionDetails
				// .getCurrentMeterReadDate());
				// ps.setDouble(++i, disconnectionDetails
				// .getCurrentMeterRegisterRead());
				// ps.setString(++i, disconnectionDetails
				// .getCurrentMeterReadRemarkCode());
				// ps.setString(++i, disconnectionDetails
				// .getCurrentMeterReadRemarkCode());
				// ps.setDouble(++i, disconnectionDetails
				// .getNewAverageConsumption());
				// ps.setString(++i, disconnectionDetails.getCreatedByID());
				// ps.setString(++i, disconnectionDetails.getKno());
				// ps.setString(++i, disconnectionDetails.getBillRound());
				// recordUpdated = ps.executeUpdate();
				if (recordUpdated > 0) {
					isSuccess = true;
				}
			}

		} catch (SQLException e) {
			isSuccess = false;
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
		return isSuccess;
	}
}
