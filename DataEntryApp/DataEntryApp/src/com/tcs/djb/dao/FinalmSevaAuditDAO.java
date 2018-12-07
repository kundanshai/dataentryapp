/************************************************************************************************************
 * @(#) FinalmSevaAuditDAO.java   09 Mar 2016
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

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.ImageAuditSearchCriteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO Class for Meter Read Image Audit Screen
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 09-03-2016 as per jTrac ANDROID-293
 *
 */
public class FinalmSevaAuditDAO {

	/**
	 * <p>
	 * This method is used to get list of records for Audit action
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static ArrayList<MeterReadImgAuditDetails> getRecordListForAuditAction(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		MeterReadImgAuditDetails meterReadImgAuditDetails = null;
		List<MeterReadImgAuditDetails> auditActionRecordsList = new ArrayList<MeterReadImgAuditDetails>();
		try {
			String query = QueryContants
					.getRecordDetailsListForAuditAction(imageAuditSearchCriteria);
			AppLog
					.info("\n##############################Record Details List For Audit Action Query::"
							+ query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != imageAuditSearchCriteria.getLoggedInUserZroCdList()
					&& !imageAuditSearchCriteria.getLoggedInUserZroCdList().isEmpty()) {
				for(String zroString : imageAuditSearchCriteria.getLoggedInUserZroCdList()){
					ps.setString(++i, zroString);
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReadImgAuditDetails = new MeterReadImgAuditDetails();
				meterReadImgAuditDetails.setSeqNo(rs.getString("SEQ_NO"));
				meterReadImgAuditDetails.setKno(rs.getString("KNO"));
				meterReadImgAuditDetails.setSugstdAuditAction(rs
						.getString("SUGSTD_ACTION"));
				meterReadImgAuditDetails.setNonSatsfctryReadngReasn(rs
						.getString("NON_SATSFCTRY_REASN"));
				meterReadImgAuditDetails.setBillRound(rs.getString("BILLROUND"));
				meterReadImgAuditDetails.setFinalAuditStatus(rs.getString("FINAL_AUDIT_STATUS"));
				meterReadImgAuditDetails.setFinalAuditAction(rs.getString("FINAL_AUDIT_ACTION"));
				auditActionRecordsList.add(meterReadImgAuditDetails);
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
		AppLog.info("\n##################### Size of auditActionRecordsList is ####################### "+auditActionRecordsList.size());
		AppLog.end();
		return (ArrayList<MeterReadImgAuditDetails>) auditActionRecordsList;

	}

	/**
	 * <p>
	 * This method is used to get total count of records for audit action
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static int getTotalCountOfAuditActionRecords(ImageAuditSearchCriteria imageAuditSearchCriteria) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		int totalRecords = 0;
		try {
			String query = QueryContants
					.getTotalCountOfAuditActionRecordsQuery(imageAuditSearchCriteria);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			if (null != imageAuditSearchCriteria.getLoggedInUserZroCdList()
					&& !imageAuditSearchCriteria.getLoggedInUserZroCdList().isEmpty()) {
				
				for(String zroString : imageAuditSearchCriteria.getLoggedInUserZroCdList()){
					ps.setString(++i, zroString);
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				totalRecords = rs.getInt("TOTAL_RECORDS");
			}
			AppLog.info("\n#####################Found Records For Audit Action::" + totalRecords);
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
	 * This method is used to save records for final audit action
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return
	 */
	public static int saveFinalAuditAction(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int updatedRow = 0;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getSaveAuditActionQuery(meterReadImgAuditDetails);
			AppLog.info("\n##############getSaveAuditActionQuery::" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != meterReadImgAuditDetails.getLastUpdatedBy()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getLastUpdatedBy().trim())) {
				ps.setString(++i, meterReadImgAuditDetails.getLastUpdatedBy());
			}
			if (null != meterReadImgAuditDetails.getFinalAuditBy()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getFinalAuditBy().trim())) {
				ps.setString(++i, meterReadImgAuditDetails.getFinalAuditBy());
			}
			if (null != meterReadImgAuditDetails.getFinalAuditStatus()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getFinalAuditStatus().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getFinalAuditStatus());
			}

			if (null != meterReadImgAuditDetails.getFinalAuditAction()
					&& !"".equalsIgnoreCase(meterReadImgAuditDetails
							.getFinalAuditAction().trim())) {
				ps.setString(++i, meterReadImgAuditDetails
						.getFinalAuditAction());
			}
			ps.setString(++i, meterReadImgAuditDetails.getKno());
			ps.setString(++i, meterReadImgAuditDetails.getBillRound());

			AppLog.info("Applying Audit Action for KNO "
					+ meterReadImgAuditDetails.getKno());
			updatedRow += ps.executeUpdate();
			AppLog.info("updatedRow" + updatedRow);

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
		return updatedRow;
	}

	/**
	 * <p>
	 * This method is used to get consumer mobile numbers
	 * </p>
	 * 
	 * @param kno
	 * @return
	 */
	public static String getConsumerMobileNumber(String kno) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String consumerMobileNumber = "0000000000";
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getConsumerMobileNumberQuery(kno);
			AppLog.info("\n##############getConsumerMobileNumberQuery::"
					+ query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
				ps.setString(++i, kno);
			}
			AppLog
					.info("\n#######################Retriving Mobile Number for KNO ::"
							+ kno);
			rs = ps.executeQuery();
			while (rs.next()) {
				consumerMobileNumber = null != rs.getString("MOB_NO") ? rs
						.getString("MOB_NO") : "0000000000";
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
		return consumerMobileNumber;
	}

	/**
	 * <p>
	 * This method is used to get ZRO list of Logged in Users
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public static ArrayList<String> getLoggedInUserZroList(String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> zroList = new ArrayList<String>();
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getLoggedInUserZroCdQuery(userId);
			AppLog.info("\n##############getLoggedInUserZroCdQuery::"
					+ query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != userId && !"".equalsIgnoreCase(userId.trim())) {
				ps.setString(++i, userId);
				ps.setString(++i, userId);
			}
			AppLog
					.info("\n#######################Retriving Zro Code for User ::"
							+ userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(null != rs.getString("USER_ZRO_CD")){
					zroList.add(rs.getString("USER_ZRO_CD"));
				}
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
		return zroList;
	}

}
