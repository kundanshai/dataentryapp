/************************************************************************************************************
 * @(#) OnlineBatchBillingDAO.java   28 Jun 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tcs.djb.model.BillGenerationDetails;
import com.tcs.djb.model.OnlineBatchBillingDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Online Batch Billing screen.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 28-06-2013
 * @history 15-07-2013 Matiur Rahman Added method
 *          {@link #getCurrentlyRunningJobSummary}.
 * @history 23-07-2013 Matiur Rahman tuned the query for
 *          {@link #getCurrentSummaryForUser}.
 * @history 26-07-2013 Matiur Rahman added \/*+result_cache*\/ to method for
 *          caching result {@link #getCurrentSummaryForUser}. +result_cache
 * @history 08-08-2013 Matiur Rahman added new parameter to
 *          {@link#completeBatchBillingByUserMRKeyBillRound},
 *          {@link#getBillingSummary},{@link#getNoOfRecordsInitiated},
 *          {@link#getNoOfRecordsInProcess},
 *          {@link#getOnlineBatchBillGenerationDetails},
 *          {@link#getSummaryForOnlineBatchBilling},{@link#initiateBatchBilling}
 *          ,{@link#initiateBatchBillingByUserMRKeyBillRound}.
 * @history 08-08-2013 Matiur Rahman Query Changed in
 *          {@link#completeBatchBillingByUserMRKeyBillRound},
 *          {@link#getBillingSummary},{@link#getNoOfRecordsInitiated},
 *          {@link#getNoOfRecordsInProcess},
 *          {@link#getOnlineBatchBillGenerationDetails},
 *          {@link#getSummaryForOnlineBatchBilling},{@link#initiateBatchBilling}
 *          ,{@link#initiateBatchBillingByUserMRKeyBillRound}.
 * @history 08-08-2013 Matiur Rahman Added Enabled caching in Query of
 *          {@link#getCurrentlyRunningJobSummary}.
 */
public class OnlineBatchBillingDAO {

	/**
	 * <p>
	 * Complete Batch Billing Process. While initiating the online batch billing
	 * process it Updates the Billing related column in
	 * <code>CM_CONS_PRE_BILL_PROC</code> and sets the
	 * <code>BILLING_CURRENT_STATUS</code> as Initiated and
	 * <code>IS_LOCKED</code> as N.
	 * </p>
	 * 
	 * @param mrKey
	 * @param mrKeyList
	 * @param billRound
	 * @param userId
	 * @return
	 */
	public static int completeBatchBillingByUserMRKeyBillRound(String mrKey,
			String mrKeyList, String billRound, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" UPDATE CM_CONS_PRE_BILL_PROC T1 SET T1.BILLING_CURRENT_STATUS='Completed', T1.IS_LOCKED='N' ");
			querySB.append(" WHERE T1.CONS_PRE_BILL_STAT_ID=70 ");
			querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			querySB.append(" AND T1.IS_LOCKED='Y'");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ?");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in (" + mrKeyList + ")");
			}
			querySB.append(" AND T1.BILL_ROUND_ID=? ");
			querySB.append(" AND T1.BILLING_INIT_BY=? ");
			String query = querySB.toString();
			// System.out.println("billRound::" + billRound + "::mrKey::" +
			// mrKey
			// + "::completeBatchBillingByUserMRKeyBillRound::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// ps.setString(++i, userId);
			// ps.setString(++i, billGenerationDetails.getKno());
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			ps.setString(++i, billRound);
			ps.setString(++i, userId);
			rowsUpdated = ps.executeUpdate();
			// System.out.println("rowsUpdated::" + rowsUpdated);

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
		return rowsUpdated;
	}

	/**
	 * <p>
	 * Get Current Billing Summary of a MRKEY billed from Online Batch Billing
	 * Screen in the database.
	 * </p>
	 * 
	 * @param billRound
	 * @param mrKey
	 * @param mrKeyList
	 * @param processCounter
	 * @param status
	 * @return
	 */
	public static List<OnlineBatchBillingDetails> getBillingSummary(
			String billRound, String mrKey, String mrKeyList,
			String processCounter, String status) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = new ArrayList<OnlineBatchBillingDetails>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT ");
			querySB.append(" T1.SVC_CYC_RTE_SEQ,");
			querySB.append(" T1.ACCT_ID,");
			querySB.append(" T1.CONSUMER_NAME,");
			querySB.append(" T1.BILL_ID,");
			querySB.append(" T1.BILLING_INIT_BY,");
			querySB
					.append(" TO_CHAR(T1.BILLING_INIT_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_INIT_ON,");
			querySB
					.append(" TO_CHAR(T1.BILLING_STARTED_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_STARTED_ON,");
			querySB
					.append(" TO_CHAR(T1.BILLING_COMPLETED_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_COMPLETED_ON,");
			querySB.append(" T1.BILLING_CURRENT_STATUS,");
			querySB.append(" T2.ERROR_MSG,");
			querySB.append(" T1.BILLING_REMARKS");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T1 ");
			querySB
					.append(" LEFT JOIN CM_CONS_PRE_BILL_ERROR_REC T2 ON T2.BILL_ROUND_ID = T1.BILL_ROUND_ID AND T2.ACCT_ID = T1.ACCT_ID AND T2.PROCESS_COUNTER = T1.PROCESS_COUNTER ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T3 ON T3.CONS_PRE_BILL_STAT_ID = T1.CONS_PRE_BILL_STAT_ID ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T4 ON T4.CONS_PRE_BILL_STAT_ID = T2.EXCPTN_STATUS ");
			querySB.append(" WHERE 1=1 ");
			querySB.append(" AND T1.BILL_ROUND_ID = ?");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ?");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in (" + mrKeyList + ")");
			}
			querySB.append(" AND T1.PROCESS_COUNTER = ? ");
			querySB
					.append(" AND (T1.CONS_PRE_BILL_STAT_ID = ? OR T2.EXCPTN_STATUS = ?) ");
			querySB.append(" ORDER BY ");
			querySB.append(" T1.SVC_CYC_RTE_SEQ");

			String query = querySB.toString();
			// System.out.println("::" + billRound + "::" + mrKey + "::"
			// + processCounter + "::" + status + "\ngetBillingSummary::"
			// + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, billRound);
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			ps.setString(++i, processCounter);
			ps.setString(++i, status);
			ps.setString(++i, status);
			rs = ps.executeQuery();
			int slNo = 0;
			while (rs.next()) {
				onlineBatchBillingDetails = new OnlineBatchBillingDetails();
				onlineBatchBillingDetails.setSlNo(Integer.toString(++slNo));
				onlineBatchBillingDetails.setServiceSeq(rs
						.getString("SVC_CYC_RTE_SEQ"));
				onlineBatchBillingDetails.setKno(rs.getString("ACCT_ID"));
				onlineBatchBillingDetails.setConsumerName(rs
						.getString("CONSUMER_NAME"));
				onlineBatchBillingDetails.setBillId((null != rs
						.getString("BILL_ID") && !"".equals(rs.getString(
						"BILL_ID").trim())) ? rs.getString("BILL_ID").trim()
						: "NA");
				onlineBatchBillingDetails.setInitiatedBy((null != rs
						.getString("BILLING_INIT_BY") && !"".equals(rs
						.getString("BILLING_INIT_BY").trim())) ? rs.getString(
						"BILLING_INIT_BY").trim() : "NA");
				onlineBatchBillingDetails.setInitiatedOn((null != rs
						.getString("BILLING_INIT_ON") && !"".equals(rs
						.getString("BILLING_INIT_ON").trim())) ? rs.getString(
						"BILLING_INIT_ON").trim() : "NA");
				onlineBatchBillingDetails.setStartedOn((null != rs
						.getString("BILLING_STARTED_ON") && !"".equals(rs
						.getString("BILLING_STARTED_ON").trim())) ? rs
						.getString("BILLING_STARTED_ON").trim() : "NA");
				onlineBatchBillingDetails.setCompletedOn((null != rs
						.getString("BILLING_COMPLETED_ON") && !"".equals(rs
						.getString("BILLING_COMPLETED_ON").trim())) ? rs
						.getString("BILLING_COMPLETED_ON").trim() : "NA");
				onlineBatchBillingDetails.setStatusDesc((null != rs
						.getString("BILLING_CURRENT_STATUS") && !"".equals(rs
						.getString("BILLING_CURRENT_STATUS").trim())) ? rs
						.getString("BILLING_CURRENT_STATUS").trim() : "NA");
				onlineBatchBillingDetails.setBillingError((null != rs
						.getString("ERROR_MSG") && !"".equals(rs.getString(
						"ERROR_MSG").trim())) ? rs.getString("ERROR_MSG")
						.trim() : "NA");
				onlineBatchBillingDetails.setBillingRemarks((null != rs
						.getString("BILLING_REMARKS") && !"".equals(rs
						.getString("BILLING_REMARKS").trim())) ? rs.getString(
						"BILLING_REMARKS").trim() : "NA");
				onlineBatchBillingDetailsList.add(onlineBatchBillingDetails);
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
		return onlineBatchBillingDetailsList;
	}

	/**
	 * <p>
	 * Get Currently running or pending Jobs for any user Submitted from Online
	 * Batch Billing Screen in the database.
	 * </p>
	 * 
	 * @param billRounds
	 * @param userId
	 * @return
	 */
	public static List<OnlineBatchBillingDetails> getCurrentlyRunningJobSummary(
			String billRounds, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = new ArrayList<OnlineBatchBillingDetails>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT /*+result_cache*/");
			querySB.append(" X.BILLING_INIT_BY,");
			querySB.append(" X.MRKEY,");
			querySB.append(" X.BILL_ROUND_ID,");
			querySB.append(" Y.SUBZONE_CD,");
			querySB.append(" Y.MR_CD,");
			querySB.append(" Y.AREA_DESC,");
			querySB
					.append(" TO_CHAR(X.BILLING_INIT_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_INIT_ON,");
			querySB
					.append(" TO_CHAR(X.BILLING_STARTED_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_STARTED_ON");
			querySB.append(" FROM (SELECT DISTINCT T.BILLING_INIT_BY,");
			querySB.append(" T.MRKEY,");
			querySB.append(" T.BILL_ROUND_ID,");
			querySB.append(" MIN(T.BILLING_INIT_ON) BILLING_INIT_ON,");
			querySB.append(" MIN(T.BILLING_STARTED_ON) BILLING_STARTED_ON");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T");
			querySB.append(" WHERE 1 = 1");
			querySB.append(" AND T.IS_LOCKED = 'Y'");
			querySB.append(" AND T.BILL_ROUND_ID in (" + billRounds + ")");
			querySB.append(" GROUP BY MRKEY, BILL_ROUND_ID, BILLING_INIT_BY");
			querySB.append(" ORDER BY BILLING_INIT_ON) X,");
			querySB.append(" DJB_ZN_MR_AR_MRD Y");
			querySB.append(" WHERE X.MRKEY = Y.MRD_CD");
			querySB.append(" ORDER BY ");
			querySB.append(" X.BILLING_INIT_ON DESC ");

			String query = querySB.toString();
			// System.out.println("getCurrentlyRunningJobSummary::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			rs = ps.executeQuery();
			while (rs.next()) {
				onlineBatchBillingDetails = new OnlineBatchBillingDetails();
				onlineBatchBillingDetails.setSlNo(Integer.toString(++i));
				onlineBatchBillingDetails.setMrKey(rs.getString("MRKEY"));
				onlineBatchBillingDetails.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				onlineBatchBillingDetails.setZone(rs.getString("SUBZONE_CD"));
				onlineBatchBillingDetails.setMrNo(rs.getString("MR_CD"));
				onlineBatchBillingDetails.setArea(rs.getString("AREA_DESC"));
				onlineBatchBillingDetails.setInitiatedBy(rs
						.getString("BILLING_INIT_BY"));
				onlineBatchBillingDetails.setInitiatedOn(rs
						.getString("BILLING_INIT_ON"));
				onlineBatchBillingDetails.setStartedOn(null != rs
						.getString("BILLING_STARTED_ON") ? rs
						.getString("BILLING_STARTED_ON") : "NA");
				onlineBatchBillingDetailsList.add(onlineBatchBillingDetails);
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
		return onlineBatchBillingDetailsList;
	}

	/**
	 * <p>
	 * Get Current Summary of (Currently running or pending) Jobs Submitted By a
	 * User from Online Batch Billing Screen in the database.It is shown as a
	 * Dash Board on the screen.
	 * </p>
	 * 
	 * @param billRounds
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List<OnlineBatchBillingDetails> getCurrentSummaryForUser(
			String billRounds, String userId, String fromDate, String toDate) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = new ArrayList<OnlineBatchBillingDetails>();
		try {
			double timeTaken = 0;
			double timeTakenInSec = 0;
			double timeTakenInMin = 0;
			double timeTakenInHr = 0;
			String timeTakenInSecString = "";
			String timeTakenInMinString = "";
			String timeTakenInHrString = "";
			StringBuffer querySB = new StringBuffer();
			// querySB.append(" SELECT ");
			// // querySB.append(" ROWNUM,");
			// querySB.append(" X.BILLING_INIT_BY,");
			// querySB.append(" X.MRKEY,");
			// querySB.append(" X.BILL_ROUND_ID,");
			// querySB.append(" Y.SUBZONE_CD,");
			// querySB.append(" Y.MR_CD,");
			// querySB.append(" Y.AREA_DESC,");
			// querySB.append(" NVL(S1.IN_PROGRESS, 0) IN_PROGRESS,");
			// querySB.append(" S4.TOTAL,");
			// querySB
			// .append(" CM_GET_DIFF_OF_TWO_DATE_IN_SEC(X.BILLING_STARTED_ON,X.BILLING_COMPLETED_ON) TIME_TAKEN,");
			// querySB
			// .append(" TO_CHAR(X.BILLING_INIT_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_INIT_ON,");
			// querySB
			// .append(" TO_CHAR(X.BILLING_STARTED_ON,'DD/MM/YYYY HH:MI:SS AM') BILLING_STARTED_ON,");
			// querySB
			// .append(" TO_CHAR(X.BILLING_COMPLETED_ON,'DD/MM/YYYY HH:MI:SS AM') LAST_BILLING_COMPLETED_ON");
			// querySB.append(" FROM (SELECT DISTINCT T.BILLING_INIT_BY,");
			// querySB.append(" T.MRKEY,");
			// querySB.append(" T.BILL_ROUND_ID,");
			// querySB.append(" MIN(T.BILLING_INIT_ON) BILLING_INIT_ON,");
			// querySB.append(" MIN(T.BILLING_STARTED_ON) BILLING_STARTED_ON,");
			// querySB.append(" MAX(T.BILLING_COMPLETED_ON)BILLING_COMPLETED_ON");
			// querySB.append(" FROM CM_CONS_PRE_BILL_PROC T");
			// querySB.append(" WHERE 1 = 1");
			// querySB.append(" AND T.BILLING_INIT_BY = ?");
			// querySB.append(" AND T.BILL_ROUND_ID in (" + billRounds + ")");
			// querySB.append(" GROUP BY MRKEY, BILL_ROUND_ID, BILLING_INIT_BY");
			// querySB.append(" ORDER BY BILLING_INIT_ON) X,");
			// querySB.append(" DJB_ZN_MR_AR_MRD Y,");
			// querySB.append(" (SELECT A.MRKEY,");
			// querySB.append(" A.BILL_ROUND_ID,");
			// querySB.append(" A.BILLING_INIT_BY,");
			// querySB.append(" COUNT(1) IN_PROGRESS");
			// querySB.append(" FROM CM_CONS_PRE_BILL_PROC A");
			// querySB.append(" WHERE 1=1 ");
			// querySB.append(" AND A.BILLING_CURRENT_STATUS <> 'Completed'");
			// querySB
			// .append(" GROUP BY A.MRKEY, A.BILL_ROUND_ID, BILLING_INIT_BY) S1,");
			// querySB
			// .append(" (SELECT A.MRKEY, A.BILL_ROUND_ID, A.BILLING_INIT_BY, COUNT(1) TOTAL");
			// querySB.append(" FROM CM_CONS_PRE_BILL_PROC A");
			// querySB
			// .append(" GROUP BY A.MRKEY, A.BILL_ROUND_ID, A.BILLING_INIT_BY) S4");
			// querySB.append(" WHERE X.MRKEY = Y.MRD_CD");
			// querySB.append(" AND X.MRKEY = S1.MRKEY(+) ");
			// querySB.append(" AND X.BILL_ROUND_ID = S1.BILL_ROUND_ID(+) ");
			// querySB.append(" AND X.BILLING_INIT_BY = S1.BILLING_INIT_BY(+)");
			// querySB.append(" AND X.MRKEY = S4.MRKEY(+)");
			// querySB.append(" AND X.BILL_ROUND_ID = S4.BILL_ROUND_ID(+)	");
			// querySB.append(" AND X.BILLING_INIT_BY = S4.BILLING_INIT_BY	(+)");
			// querySB.append(" ORDER BY ");
			// querySB.append(" IN_PROGRESS DESC,");
			// querySB.append(" X.BILLING_INIT_ON DESC ");
			/* After Tuning */
			querySB.append(" SELECT  /*+result_cache*/ X.BILLING_INIT_BY,");
			querySB.append(" X.MRKEY,");
			querySB.append(" X.BILL_ROUND_ID,");
			querySB.append(" Y.SUBZONE_CD,");
			querySB.append(" Y.MR_CD,");
			querySB.append(" Y.AREA_DESC,");
			querySB.append(" NVL(S1.IN_PROGRESS, 0) IN_PROGRESS,");
			querySB.append(" S4.TOTAL,");
			querySB
					.append(" CM_GET_DIFF_OF_TWO_DATE_IN_SEC(X.BILLING_STARTED_ON,");
			querySB.append(" X.BILLING_COMPLETED_ON) TIME_TAKEN,");
			querySB
					.append(" TO_CHAR(X.BILLING_INIT_ON, 'DD/MM/YYYY HH:MI:SS AM') BILLING_INIT_ON,");
			querySB
					.append(" TO_CHAR(X.BILLING_STARTED_ON, 'DD/MM/YYYY HH:MI:SS AM') BILLING_STARTED_ON,");
			querySB
					.append(" TO_CHAR(X.BILLING_COMPLETED_ON, 'DD/MM/YYYY HH:MI:SS AM') LAST_BILLING_COMPLETED_ON");
			querySB.append(" FROM (SELECT /*+index_ffs(T)*/");
			querySB.append(" DISTINCT T.BILLING_INIT_BY,");
			querySB.append(" T.MRKEY,");
			querySB.append(" T.BILL_ROUND_ID,");
			querySB.append(" MAX(T.BILLING_INIT_ON) BILLING_INIT_ON,");
			querySB.append(" MIN(T.BILLING_STARTED_ON) BILLING_STARTED_ON,");
			querySB.append(" MAX(T.BILLING_COMPLETED_ON) BILLING_COMPLETED_ON");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T");
			querySB.append(" WHERE 1 = 1");
			if (null != userId && !"".equals(userId.trim())) {
				querySB.append(" AND T.BILLING_INIT_BY = ?");
			}
			querySB.append(" AND T.BILL_ROUND_ID in (" + billRounds + ")");
			querySB.append(" GROUP BY MRKEY, BILL_ROUND_ID, BILLING_INIT_BY");
			querySB.append(" ORDER BY BILLING_INIT_ON) X,");
			querySB.append(" DJB_ZN_MR_AR_MRD Y,");
			querySB.append(" (SELECT /*+index_ffs(A)*/");
			querySB
					.append(" A.MRKEY, A.BILL_ROUND_ID, A.BILLING_INIT_BY, COUNT(1) IN_PROGRESS");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC A");
			querySB.append(" WHERE 1 = 1");
			querySB.append(" AND A.BILLING_CURRENT_STATUS <> 'Completed'");
			querySB.append(" AND A.BILL_ROUND_ID in (" + billRounds + ")");
			if (null != userId && !"".equals(userId.trim())) {
				querySB.append(" AND A.BILLING_INIT_BY = ?");
			}
			querySB
					.append(" GROUP BY A.MRKEY, A.BILL_ROUND_ID, BILLING_INIT_BY) S1,");
			querySB.append(" (SELECT /*+index_ffs(A)*/");
			querySB
					.append(" A.MRKEY, A.BILL_ROUND_ID, A.BILLING_INIT_BY, COUNT(1) TOTAL");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC A");
			querySB.append(" where A.BILL_ROUND_ID in (" + billRounds + ")");
			if (null != userId && !"".equals(userId.trim())) {
				querySB.append(" AND A.BILLING_INIT_BY = ?");
			}
			querySB
					.append(" GROUP BY A.MRKEY, A.BILL_ROUND_ID, A.BILLING_INIT_BY) S4");
			querySB.append(" WHERE X.MRKEY = Y.MRD_CD");
			querySB.append(" AND X.MRKEY = S1.MRKEY(+)");
			querySB.append(" AND X.BILL_ROUND_ID = S1.BILL_ROUND_ID(+)");
			querySB.append(" AND X.BILLING_INIT_BY = S1.BILLING_INIT_BY(+)");
			querySB.append(" AND X.MRKEY = S4.MRKEY(+)");
			querySB.append(" AND X.BILL_ROUND_ID = S4.BILL_ROUND_ID(+)");
			querySB.append(" AND X.BILLING_INIT_BY = S4.BILLING_INIT_BY(+)");
			if (null != fromDate && !"".equals(fromDate.trim())
					&& fromDate.length() == 10) {
				querySB.append(" AND TRUNC(X.BILLING_INIT_ON) >= TO_DATE('"
						+ fromDate + "','dd/mm/yyyy')");
			}
			if (null != toDate && !"".equals(toDate.trim())
					&& toDate.length() == 10) {
				querySB.append(" AND TRUNC(X.BILLING_INIT_ON) <= TO_DATE('"
						+ toDate + "','dd/mm/yyyy')");
			}
			querySB
					.append(" ORDER BY IN_PROGRESS DESC, X.BILLING_INIT_ON DESC");

			String query = querySB.toString();
			// System.out.println(userId + "getCurrentSummaryForUserQuery::"
			// + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			int slNo = 0;
			if (null != userId && !"".equals(userId.trim())) {
				ps.setString(++i, userId);
				ps.setString(++i, userId);
				ps.setString(++i, userId);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				onlineBatchBillingDetails = new OnlineBatchBillingDetails();
				onlineBatchBillingDetails.setSlNo(Integer.toString(++slNo));
				onlineBatchBillingDetails.setMrKey(rs.getString("MRKEY"));
				onlineBatchBillingDetails.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				onlineBatchBillingDetails.setZone(rs.getString("SUBZONE_CD"));
				onlineBatchBillingDetails.setMrNo(rs.getString("MR_CD"));
				onlineBatchBillingDetails.setArea(rs.getString("AREA_DESC"));
				onlineBatchBillingDetails.setRecordsInProcess(rs
						.getString("IN_PROGRESS"));
				onlineBatchBillingDetails
						.setTotalRecords(rs.getString("TOTAL"));
				onlineBatchBillingDetails.setInitiatedBy(rs
						.getString("BILLING_INIT_BY"));
				onlineBatchBillingDetails.setInitiatedOn(rs
						.getString("BILLING_INIT_ON"));
				onlineBatchBillingDetails.setStartedOn(null != rs
						.getString("BILLING_STARTED_ON") ? rs
						.getString("BILLING_STARTED_ON") : "NA");
				onlineBatchBillingDetails.setCompletedOn(null != rs
						.getString("LAST_BILLING_COMPLETED_ON") ? rs
						.getString("LAST_BILLING_COMPLETED_ON") : "NA");
				timeTaken = rs.getDouble("TIME_TAKEN");
				if (timeTaken > 0) {
					timeTakenInHr = (timeTaken / (60 * 60));
					timeTaken = timeTaken % (60 * 60);
					timeTakenInMin = (timeTaken / 60);
					timeTaken = timeTaken % 60;
					timeTakenInSec = (timeTaken % 60);
					timeTakenInSecString = timeTakenInSec < 10 ? "0"
							+ (int) timeTakenInSec
							: ("" + (int) timeTakenInSec);
					timeTakenInMinString = timeTakenInMin < 10 ? "0"
							+ (int) timeTakenInMin
							: ("" + (int) timeTakenInMin);
					timeTakenInHrString = timeTakenInHr < 10 ? "0"
							+ (int) timeTakenInHr : ("" + (int) timeTakenInHr);
					onlineBatchBillingDetails
							.setTotalTimeTaken(timeTakenInHrString + ":"
									+ timeTakenInMinString + ":"
									+ timeTakenInSecString);
				} else {
					onlineBatchBillingDetails.setTotalTimeTaken("NA");
				}
				onlineBatchBillingDetailsList.add(onlineBatchBillingDetails);
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
		return onlineBatchBillingDetailsList;
	}

	/**
	 * <p>
	 * Get the No Of Records In Process. The records in a status of Initiated
	 * and In Progress i.e. not Completed are considered as Records in Process.
	 * </p>
	 * 
	 * @param billRound
	 * @param mrKey
	 * @param mrKeyList
	 * @param userId
	 * @return
	 */
	public static int getNoOfRecordsInitiated(String billRound, String mrKey,
			String mrKeyList, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT  COUNT(1)TOTAL_RECORDS ");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T1 ");
			querySB
					.append(" LEFT JOIN CM_CONS_PRE_BILL_ERROR_REC T2 ON T2.BILL_ROUND_ID = T1.BILL_ROUND_ID AND T2.ACCT_ID = T1.ACCT_ID AND T2.PROCESS_COUNTER = T1.PROCESS_COUNTER ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T3 ON T3.CONS_PRE_BILL_STAT_ID = T1.CONS_PRE_BILL_STAT_ID ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T4 ON T4.CONS_PRE_BILL_STAT_ID = T2.EXCPTN_STATUS ");
			querySB.append(" WHERE 1=1 ");
			// querySB.append(" AND T1.CONS_PRE_BILL_STAT_ID=70 ");
			// querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			// querySB.append(" AND T1.BILLING_CURRENT_STATUS<>'Completed'");
			querySB.append(" AND T1.BILLING_INIT_BY is not null");
			if (null != userId) {
				querySB.append(" AND T1.BILLING_INIT_BY =? ");
			}
			querySB.append(" AND T1.BILL_ROUND_ID = ? ");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ?");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in (" + mrKeyList + ")");
			}

			String query = querySB.toString();
			// System.out.println("billRound::" + billRound + "::mrKey::" +
			// mrKey
			// + "::getNoOfRecordsInitiated::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != userId) {
				ps.setString(++i, userId);
			}
			ps.setString(++i, billRound);
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
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
		return totalRecords;
	}

	/**
	 * <p>
	 * Get the No Of Records In Process. The records in a status of Initiated
	 * and In Progress i.e. not Completed are considered as Records in Process.
	 * </p>
	 * 
	 * @param billRound
	 * @param mrKey
	 * @param mrKeyList
	 * @param userId
	 * @return
	 */
	public static int getNoOfRecordsInProcess(String billRound, String mrKey,
			String mrKeyList, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalRecords = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB.append(" SELECT  COUNT(1)TOTAL_RECORDS ");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T1 ");
			querySB
					.append(" LEFT JOIN CM_CONS_PRE_BILL_ERROR_REC T2 ON T2.BILL_ROUND_ID = T1.BILL_ROUND_ID AND T2.ACCT_ID = T1.ACCT_ID AND T2.PROCESS_COUNTER = T1.PROCESS_COUNTER ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T3 ON T3.CONS_PRE_BILL_STAT_ID = T1.CONS_PRE_BILL_STAT_ID ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T4 ON T4.CONS_PRE_BILL_STAT_ID = T2.EXCPTN_STATUS ");
			querySB.append(" WHERE 1=1 ");
			querySB.append(" AND T1.CONS_PRE_BILL_STAT_ID=70 ");
			querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			// querySB.append(" AND T1.BILLING_CURRENT_STATUS<>'Completed'");
			querySB.append(" AND T1.IS_LOCKED='Y'");
			if (null != userId) {
				querySB.append(" AND T1.BILLING_INIT_BY =? ");
			}
			querySB.append(" AND T1.BILL_ROUND_ID = ? ");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ?");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in (" + mrKeyList + ")");
			}

			String query = querySB.toString();
			// System.out.println("userId::" + userId + "::billRound::"
			// + billRound + "::mrKey::" + mrKey
			// + "::getNoOfRecordsInProcess::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != userId) {
				ps.setString(++i, userId);
			}
			ps.setString(++i, billRound);
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
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
		return totalRecords;
	}

	/**
	 * <p>
	 * Get Online Batch Bill Generation Details for the combination of Bill
	 * Round and MRKey. The MRKey and Bill Round is passed from the screen and
	 * retrieves all the accounts from <code>CM_CONS_PRE_BILL_PROC</code>
	 * corresponding to the MRKey and Bill Round for Bill generation whose
	 * <code>CONS_PRE_BILL_STAT_ID</code> is 70 and <code>PROCESS_COUNTER</code>
	 * is 0 and <code>BILLING_CURRENT_STATUS</code> is null or Completed.
	 * </p>
	 * 
	 * @param userId
	 * @param billRound
	 * @param mrKey
	 * @param mrKeyList
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param statusCode
	 * @param errorCode
	 * @return
	 */
	public static List<BillGenerationDetails> getOnlineBatchBillGenerationDetails(
			String userId, String billRound, String mrKey, String mrKeyList,
			String zone, String mrNo, String area, String statusCode,
			String errorCode) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BillGenerationDetails billGenerationDetails = null;
		List<BillGenerationDetails> billGenerationDetailsList = new ArrayList<BillGenerationDetails>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT  T1.MRKEY,T1.ACCT_ID,T1.BILL_ROUND_ID,T1.REG_READING,T1.READ_TYPE_FLG,T1.READER_REM_CD,TO_CHAR(T1.BILL_GEN_DATE,'DD/MM/YYYY')BILL_GEN_DATE,T1.MTR_READER_NAME,T1.NEW_AVG_READ,T1.NEW_NO_OF_FLOORS ");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T1 ");
			querySB
					.append(" LEFT JOIN CM_CONS_PRE_BILL_ERROR_REC T2 ON T2.BILL_ROUND_ID = T1.BILL_ROUND_ID AND T2.ACCT_ID = T1.ACCT_ID AND T2.PROCESS_COUNTER = T1.PROCESS_COUNTER ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T3 ON T3.CONS_PRE_BILL_STAT_ID = T1.CONS_PRE_BILL_STAT_ID ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T4 ON T4.CONS_PRE_BILL_STAT_ID = T2.EXCPTN_STATUS ");
			querySB.append(" WHERE 1=1 ");
			querySB.append(" AND T1.CONS_PRE_BILL_STAT_ID=70 ");
			querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			// querySB
			// .append(" AND (T1.BILLING_CURRENT_STATUS is null OR T1.BILLING_CURRENT_STATUS='Completed')");
			querySB.append(" AND T1.IS_LOCKED='Y'");
			querySB
					.append(" AND (T1.BILL_ID IS  NULL OR TRIM(T1.BILL_ID)='') ");
			querySB.append(" AND T1.BILL_ROUND_ID = ? ");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ?");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in (" + mrKeyList + ")");
			}
			querySB.append(" AND T1.BILLING_INIT_BY=? ");
			querySB.append(" ORDER BY T1.SVC_CYC_RTE_SEQ ");
			String query = querySB.toString();
			// System.out.println("billRound::" + billRound + "::mrKey::" +
			// mrKey
			// + "::getOnlineBatchBillGenerationDetails::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			// if (null != billRound && !"".equals(billRound.trim())) {
			ps.setString(++i, billRound);
			// }
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			// if (null != userId && !"".equals(userId.trim())) {
			ps.setString(++i, userId);
			// }
			rs = ps.executeQuery();
			while (rs.next()) {
				billGenerationDetails = new BillGenerationDetails();
				billGenerationDetails.setMrKey(rs.getString("MRKEY"));
				billGenerationDetails.setKno(rs.getString("ACCT_ID"));
				billGenerationDetails.setBillRound(rs
						.getString("BILL_ROUND_ID"));
				billGenerationDetails.setMeterRead(rs.getString("REG_READING"));
				billGenerationDetails
						.setReadType(rs.getString("READ_TYPE_FLG"));
				billGenerationDetails.setMeterReadRemark(rs
						.getString("READER_REM_CD"));
				billGenerationDetails.setMeterReadDate(rs
						.getString("BILL_GEN_DATE"));
				billGenerationDetails.setMeterReaderName(rs
						.getString("MTR_READER_NAME"));
				billGenerationDetails.setAverageConsumption(rs
						.getString("NEW_AVG_READ"));
				billGenerationDetails.setNoOfFloors(rs
						.getString("NEW_NO_OF_FLOORS"));
				billGenerationDetailsList.add(billGenerationDetails);
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
		return billGenerationDetailsList;
	}

	/**
	 * <p>
	 * This method is used to get All the Open Bill Round Id.
	 * </p>
	 * 
	 * @param mrKey
	 * @return
	 */
	public static List<String> getOpenBillRound(String mrKey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String billRound = null;
		List<String> billRoundList = new ArrayList<String>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" select distinct b.bill_round_id  from cm_mrd_processing_stat b where b.mrd_stats_id=10 ");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" where b.mrkey=? ");
			}
			String query = querySB.toString();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				billRound = rs.getString("bill_round_id");
				billRoundList.add(billRound);
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
		return billRoundList;
	}

	/**
	 * <p>
	 * Get Summary of Accounts in Different status for Online Batch Billing
	 * Screen in the database.
	 * </p>
	 * 
	 * @param billRound
	 * @param mrKey
	 * @param mrKeyList
	 * @return
	 */
	public static List<OnlineBatchBillingDetails> getSummaryForOnlineBatchBilling(
			String billRound, String mrKey, String mrKeyList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OnlineBatchBillingDetails onlineBatchBillingDetails = null;
		List<OnlineBatchBillingDetails> onlineBatchBillingDetailsList = new ArrayList<OnlineBatchBillingDetails>();
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" SELECT  NVL(T4.CONS_PRE_BILL_STAT_ID,T3.CONS_PRE_BILL_STAT_ID)STAT_CODE,NVL(T4.DESCR,T3.DESCR)STAT_DESCR,T1.PROCESS_COUNTER,COUNT(*)TOTAL_RECORDS ");
			querySB.append(" FROM CM_CONS_PRE_BILL_PROC T1 ");
			querySB
					.append(" LEFT JOIN CM_CONS_PRE_BILL_ERROR_REC T2 ON T2.BILL_ROUND_ID = T1.BILL_ROUND_ID AND T2.ACCT_ID = T1.ACCT_ID AND T2.PROCESS_COUNTER = T1.PROCESS_COUNTER ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T3 ON T3.CONS_PRE_BILL_STAT_ID = T1.CONS_PRE_BILL_STAT_ID ");
			querySB
					.append(" LEFT JOIN CONS_PRE_BILL_STAT_LOOKUP T4 ON T4.CONS_PRE_BILL_STAT_ID = T2.EXCPTN_STATUS ");
			querySB.append(" WHERE 1=1 ");
			if (null != billRound && !"".equals(billRound.trim())) {
				querySB.append(" AND T1.BILL_ROUND_ID = ? ");
			}
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ? ");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in(" + mrKeyList + ")");
			}
			querySB
					.append(" GROUP BY T3.CONS_PRE_BILL_STAT_ID, T3.DESCR, T4.CONS_PRE_BILL_STAT_ID, T4.DESCR, T1.PROCESS_COUNTER ");
			querySB.append(" ORDER BY STAT_CODE DESC ");
			String query = querySB.toString();
			// System.out.println("billRound::" + billRound + "::mrKey::" +
			// mrKey
			// + "::getSummaryForOnlineBatchBillingQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;

			if (null != billRound && !"".equals(billRound.trim())) {
				ps.setString(++i, billRound);
			}
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			rs = ps.executeQuery();
			int slNo = 1;
			while (rs.next()) {
				onlineBatchBillingDetails = new OnlineBatchBillingDetails();
				onlineBatchBillingDetails.setSlNo(Integer.toString(slNo));
				onlineBatchBillingDetails.setStatusCode(rs
						.getString("STAT_CODE"));
				onlineBatchBillingDetails.setStatusDesc(rs
						.getString("STAT_DESCR"));
				onlineBatchBillingDetails.setProcessCounter(rs
						.getString("PROCESS_COUNTER"));
				onlineBatchBillingDetails.setTotalRecords(rs
						.getString("TOTAL_RECORDS"));
				slNo++;
				onlineBatchBillingDetailsList.add(onlineBatchBillingDetails);
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
		return onlineBatchBillingDetailsList;
	}

	/**
	 * <p>
	 * Initiate Batch Billing Process. While initiating the online batch billing
	 * process it Updates the Billing related column in
	 * <code>CM_CONS_PRE_BILL_PROC</code> and sets the
	 * <code>BILLING_CURRENT_STATUS</code> as Initiated.
	 * </p>
	 * 
	 * @param billGenerationDetails
	 * @param userId
	 * @return
	 */
	public static int initiateBatchBilling(
			BillGenerationDetails billGenerationDetails, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" UPDATE CM_CONS_PRE_BILL_PROC T1 SET T1.BILLING_INIT_BY=?,T1.BILLING_INIT_ON=SYSTIMESTAMP,T1.BILLING_CURRENT_STATUS='Initiated', T1.IS_LOCKED='Y' ");
			querySB.append(" WHERE T1.CONS_PRE_BILL_STAT_ID=70 ");
			querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			querySB.append(" AND T1.IS_LOCKED='N'");
			querySB.append(" AND T1.ACCT_ID=? ");
			querySB.append(" AND T1.BILL_ROUND_ID=? ");
			String query = querySB.toString();
			// AppLog.info("billRound::" + billGenerationDetails.getBillRound()
			// + "::KNO::" + billGenerationDetails.getKno()
			// + "\ninitiateBatchBillingQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, billGenerationDetails.getKno());
			ps.setString(++i, billGenerationDetails.getBillRound());
			rowsUpdated = ps.executeUpdate();
			// System.out.println("rowsUpdated::" + rowsUpdated);

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
		return rowsUpdated;
	}

	/**
	 * <p>
	 * Initiate Batch Billing Process. While initiating the online batch billing
	 * process it Updates the Billing related column in
	 * <code>CM_CONS_PRE_BILL_PROC</code> and sets the
	 * <code>BILLING_CURRENT_STATUS</code> as Initiated and
	 * <code>IS_LOCKED</code> as Y.
	 * </p>
	 * 
	 * @param mrKey
	 * @param mrKeyList
	 * @param billRound
	 * @param userId
	 * @return
	 */
	public static int initiateBatchBillingByUserMRKeyBillRound(String mrKey,
			String mrKeyList, String billRound, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsUpdated = 0;
		try {
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" UPDATE CM_CONS_PRE_BILL_PROC T1 SET T1.BILLING_INIT_BY=?,T1.BILLING_INIT_ON=SYSTIMESTAMP,T1.BILLING_CURRENT_STATUS='Initiated', T1.IS_LOCKED='Y' ");
			querySB.append(" WHERE T1.CONS_PRE_BILL_STAT_ID=70 ");
			querySB.append(" AND T1.PROCESS_COUNTER=0 ");
			querySB.append(" AND T1.IS_LOCKED='N'");
			if (null != mrKey && !"".equals(mrKey.trim())) {
				querySB.append(" AND T1.MRKEY = ? ");
			}
			if (null != mrKeyList && !"".equals(mrKeyList.trim())) {
				querySB.append(" AND T1.MRKEY in(" + mrKeyList + ")");
			}
			querySB.append(" AND T1.BILL_ROUND_ID=? ");
			String query = querySB.toString();
			// System.out.println("billRound::" + billRound + "::mrKey::" +
			// mrKey
			// + "::initiateBatchBillingQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			// ps.setString(++i, billGenerationDetails.getKno());
			if (null != mrKey && !"".equals(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			ps.setString(++i, billRound);
			rowsUpdated = ps.executeUpdate();
			// System.out.println("rowsUpdated::" + rowsUpdated);

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
		return rowsUpdated;
	}
}
