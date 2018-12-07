/************************************************************************************************************
 * @(#) GetterDAO.java   02 Aug 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.AreaDetails;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MRDReadTypeLookup;
import com.tcs.djb.model.MRDScheduleDownloadDetails;
import com.tcs.djb.model.ManufacturerDetailsLookup;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReadType;
import com.tcs.djb.model.MeterReadTypeLookUp;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.model.MeterTypeLookup;
import com.tcs.djb.model.ModelDetailsLookup;
import com.tcs.djb.model.PreviousMeterReadDetails;
import com.tcs.djb.model.SATypeDetails;
import com.tcs.djb.model.UserRole;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.DataEntryAppConstants;
import com.tcs.djb.util.UtilityForAll;

/**
 * <p>
 * DAO Class for screens of Data Entry Application
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 02-Aug-2012 apurb.sinha added new meter reader remark code
 *          validation API
 * @history 03-09-2012 Matiur added TRIM to satypecd as some satypecd has space @
 *          database
 * 
 * @history 27-09-2012 Matiur added getLastOKReadStatusCode Method to get the
 *          numeric value of meter status code of read type
 * 
 * @history 27-09-2012 Matiur added getConsumerDetails Method to get the
 *          ConsumerDetails for a given KNO
 * 
 * @history 26-10-2012 Apurb removed the date check in where clause for bill
 *          window lookup
 * @history 02-11-2012 Added Bill Round ID on By Matiur Rahman
 * 
 * @history 15-11-2012 Matiur Rahman added no Of Floor
 * 
 * @history 30-11-2012 Matiur Rahman added trim to query for
 *          getManufacturerDetailsList and getModelDetailsList
 * @history 22-01-2013 Matiur Rahman Main query for data entry search modified
 * @history 22-01-2013 Matiur Rahman Main query for getting bill round modified
 * @history 21-02-2013 Matiur Rahman Added method getMeterReplacementDetails to
 *          get the meter replacement details.
 * @history 14-03-2013 Matiur Rahman Added method getZoneListMap.
 * @history 14-03-2013 Matiur Rahman Added method getMRNoListMap.
 * @history 14-03-2013 Matiur Rahman Added method getAreaListMap.
 * @history 14-03-2013 Matiur Rahman Added method
 *          getMeterReadRemarkCorrespondingToReadType.
 * @history 14-03-2013 Matiur Rahman Added method
 *          getConsumerDetailByAccountIDAndBillRound.
 * @history 14-03-2013 Matiur Rahman Added method
 *          checkIfMRDIsFrozenByAccountIDAndBillRound.
 * @history 19-03-2013 Matiur Rahman Added method getDJBParamValue.
 * @history 19-03-2013 Matiur Rahman Modified method getMeterReplacementDetails.
 * @history 01-04-2013 Matiur Rahman Modified method
 *          getPrevMeterReadDetailsForMR.
 * @history 01-04-2013 Matiur Rahman Modified method
 *          getPrevActualMeterReadDetailsForMR.
 * @history 08-05-2013 Matiur Rahman Added method getMRKEYListMap.
 * @history 08-05-2013 Matiur Rahman Added method getZoneMRAreaByMRKEY.
 * @history 15-05-2013 Reshma Added method getServiceCycleListMap and
 *          getServiceRouteListMap
 * @history 21-05-2013 Matiur Rahman Added method getMRDTypeMap.
 * @history 07-06-2013 Matiur Rahman Added method getAllZROQuery.
 * @history 21-06-2013 Matiur Rahman added method
 *          {@link #getOpenBillRoundForAnMRKey()}.
 * @history 18-07-2013 Mrityunjoy Misra added method
 *          {@link #getConsumerStatus()}.
 * @history 18-07-2013 Mrityunjoy Misra added method
 *          {@link #getRecordStatusAndErrorDesc()}.
 * @history 06-08-2013 Matiur Rahman modified method
 *          {@link #getConsumerDetailByAccountIDAndBillRound}.
 * @history 13-08-2013 Matiur Rahman modified method
 *          {@link #getConsumerDetailByAccountIDAndBillRound} added
 *          PreviousAverageConsumptionto as missed earlier in
 *          previousMeterReadDetails as bug reported as per JTrac DJB-1792.
 * @history 23-09-2013 Matiur Rahman Added method
 *          {@link #getMeterReaderDesignationListMapQuery} as per HHD-48.
 * @history 14-12-2015 Aniket Chatterjeee Added Method
 *          {@link #getUserExistenceCount} as per JTrac DJB-4185 & DJB-4280
 * @history 28-JAN-2016 Rajib Hazarika Added method {@link #getAreaDetails} as
 *          per JTrac DJB-4313 to populate drop downs related to area, sub area
 * @history 28-01-2016 Arnab Nandy Added Method {@link #getZROList},
 *          {@link #getStatusListMap},{@link #getSaveStatusListMap},
 *          {@link #getUserListMap} as per JTrac DJB-4326
 * @history 13-02-2016 Rajib Hazarika Added method to get drop down values for
 *          New Connection KNO and Bill Generation pages as per JTrac DJB-4313
 * @history Reshma on 11-03-2016 edited the method
 *          {@link #getPreDefChrValListMap(String)} to provide decription first
 *          then code in drop down list, JTrac DJB-4405.
 * @history Madhuri on 6th-06-2016 Added methods
 *          {@link #getOpenBillWIndowList()},{@link #getTaggedMrkeysEmp()} to
 *          get taggedMrkey,open BillRound , JTrac DJB-4464.
 * @history Atanu on 27-06-2016 commented previous meter read remarks set logic.
 *          Now Setting meter read remarks by replacing new line char from
 *          remarks, Open Project Id: 1202, DJB-4464
 * @history Madhuri on 06-Aug-2016 Added new methods
 *          {@link #getZroCodeByZoneMrArea(String, String, String)} as per Open
 *          Project Id: 1540, DJB-2200
 * @history Madhuri on 22-Nov-2016 Modified methods
 *          {@link #getMRD(MRDContainer, String, String)},
 *          {@link #getMRDOther(MRDContainer, String, String)} And Added
 *          {@link#checkSelfBillingStatus(String)}as per DJB-4604.
 * @history Sanjay on 17-Nov-2016 modified the method
 *          {@link #getMeterReadStatusList()} {@link #getMRD(MRDContainer)} as
 *          per Open Project Id: 1594, DJB-4596
 * @history Suraj Tiwari on 12-Sep-2017 Added method
 *          {@link#checkDbSessionCount()}as per DJB-4735.
 */
public class GetterDAO {

	/**
	 * <p>
	 * This method is used to check BD Session count. area. as per Jtrac
	 * DJB-4735.
	 * </p>
	 * 
	 * @author Suraj Tiwari (Tata Consultancy Services)
	 * @param
	 * @since 12-09-2017
	 * @return
	 */

	public static String checkDbSessionCount() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sessionCount = null;
		try {
			String query = QueryContants.checkDbSessionCountQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				sessionCount = rs.getString("SESSION_COUNT");
				AppLog.info("*** Session Count ***" + sessionCount);
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

		return sessionCount;
	}

	/**
	 * <p>
	 * This method is to check If MRD Is Frozen By Account ID And Bill Round
	 * from the database of the consumer for those meter read to be updated to
	 * validate the reading details sent by HHD.
	 * </p>
	 * 
	 * @exception SQLException
	 *                if a database access error occurs or this method is called
	 *                on a closed connection
	 * @exception IOException
	 *                if any kind of IO failure occurs
	 * @exception Exception
	 *                if any kind of failure occurs
	 * @param toUploadMeterReadDetailsList
	 *            list of meter read details those are to be uploaded
	 * @return consumerDetailList list of meter read details in the database
	 *         those are to be uploaded
	 * @see ConsumerDetail
	 * @see PreviousMeterReadDetails
	 * @see MeterReadUploadDetails
	 * @param toUploadMeterReadDetailsList
	 * @return isFrozen true if frozen else false
	 */
	public static boolean checkIfMRDIsFrozenByAccountIDAndBillRound(
			ArrayList<MeterReadUploadDetails> toUploadMeterReadDetailsList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String billRoundId = "";
		// String accountIds = "";
		String accountIds = "' '";
		boolean isFrozen = false;
		MeterReadUploadDetails meterReadUploadDetails = null;
		try {
			if (null != toUploadMeterReadDetailsList
					&& toUploadMeterReadDetailsList.size() > 0) {
				for (int i = 0; i < toUploadMeterReadDetailsList.size(); i++) {
					meterReadUploadDetails = (MeterReadUploadDetails) toUploadMeterReadDetailsList
							.get(i);
					if (null != meterReadUploadDetails.getKno()
							&& !"".equals(meterReadUploadDetails.getKno()
									.trim())) {
						billRoundId = meterReadUploadDetails.getBillRound();
						// accountIds = accountIds + "'"
						// + meterReadUploadDetails.getKno() + "',";
						accountIds = accountIds + ",'"
								+ meterReadUploadDetails.getKno() + "'";
					}
				}
				// accountIds = accountIds + "''";
			}
			conn = DBConnector.getConnection();
			String query = QueryContants
					.checkIfMRDIsFrozenByAccountIDAndBillRoundQuery(
							billRoundId, accountIds);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int noOfFrozenRecords = rs.getInt("NO_OF_FROZEN_RECORDS");
				if (noOfFrozenRecords > 0) {
					isFrozen = true;
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
		return isFrozen;
	}

	/**
	 * <p>
	 * This method is used to check whether self billing is enabled or disabled.
	 * area. as per Jtrac DJB-4604.
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @param
	 * @since 23-11-2016
	 * @return
	 */

	public static String checkSelfBillingStatus(String acctId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;
		String effectiveDate = null;
		try {
			String query = QueryContants.checkSelfBillingStatusQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, DJBConstants.CHAR_VAL_FOR_SELF_BILL);
			ps.setString(++i, acctId);
			AppLog.info("Account Id >> " + acctId);
			ps.setString(++i, DJBConstants.CHAR_VAL_FOR_SELF_BILL);
			rs = ps.executeQuery();
			while (rs.next()) {
				status = rs.getString("STATUS");
				AppLog.info("*** Status ***" + status);
				effectiveDate = rs.getString("EFFECTIVE_DATE");
				AppLog.info("Effective Date >>>" + effectiveDate);

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

		return status;
	}

	/**
	 * <p>
	 * Retrieve All Meter Reader list to populate Meter Reader Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getAllMeterReaderListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getAllMeterReaderListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("MTR_RDR_CD").trim(), rs
						.getString("MTR_RDR_NAME")
						+ "(" + rs.getString("MTR_RDR_CD") + ")");
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
		return retrunMap;
	}

	/**
	 *<p>
	 * Retrieve All ZRO list to populate ZRO Drop down.
	 * </p>
	 * 
	 * @return
	 */
	public static Map<String, String> getAllZRO() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getAllZROQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("ZRO_CD").trim(), rs.getString(
						"ZRO_DESC").trim()
						+ "(" + rs.getString("ZRO_CD").trim() + ")");
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
		return retrunMap;
	}

	// START: Added by Rajib as per Jtrac on 06-JAN-2015
	/**
	 * <p>
	 * This method is used to get all the pin code , area cd , area desc,
	 * sub_area_cd and sub_area_desc from CCB database. This is added as per
	 * JTrac DJB-4313 for New Connection File Entry Screen
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 20-JAN-2015
	 */
	public static List<AreaDetails> getAreaDetails() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// AreaList areaList = null;
		List<AreaDetails> areaList = null;
		AreaDetails areaDetails = null;
		try {
			String query = QueryContants.getAreaDetailsQuery();
			AppLog.debug(">> fetch Area Details Query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			areaList = new ArrayList<AreaDetails>();
			while (rs.next()) {
				areaDetails = new AreaDetails();
				areaDetails.setAreaCd(rs.getString("AREA_CODE"));
				areaDetails.setAreaDesc(rs.getString("AREA_DESC"));
				areaDetails.setPin(rs.getString("PIN_CODE"));
				areaDetails.setSubAreaCd(rs.getString("SUB_AREA_CODE"));
				areaDetails.setSubAreaName(rs.getString("SUB_AREA_NAME"));
				areaList.add(areaDetails);
				// AppLog.debugInfo("Area List>>>" + areaDetails);
			}
			AppLog.debug("Area List Size >>>" + areaList.size());
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
				// e.printStackTrace();
				AppLog.error(e);
			}
		}
		AppLog.end();
		return areaList;
	}

	/**
	 * <p>
	 * get Area List for drop down @MRD search screen
	 * </p>
	 * 
	 * @return
	 */
	public static List<String> getAreaList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			// String query =
			// "select t1.area_cd, t1.area_desc, t2.mr_cd, t2.subzone_cd from djb_area t1,djb_mr t2, djb_subzone t3 where t1.mr_id= t2.mr_id and t2.subzone_cd= t3.subzone_cd order by subzone_cd,mr_cd,area_desc asc";
			String query = "SELECT distinct area_cd,area_desc,mr_cd,subzone_cd  FROM djb_zn_mr_ar_mrd order by subzone_cd,mr_cd,area_desc asc";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("area_desc") + "-("
						+ rs.getString("area_cd") + "-" + rs.getString("mr_cd")
						+ "-" + rs.getString("subzone_cd") + ")");
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
		return dataList;
	}

	/**
	 * <p>
	 * Get Area List to populate Area Drop down corresponding to a Zone Code and
	 * MR No.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @return Map<String, String>
	 */
	public static Map<String, String> getAreaListMap(String zone, String mrNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getAreaListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, zone);
			ps.setString(2, mrNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("AREA_CD").trim(), rs
						.getString("AREA_DESC")
						+ "(" + rs.getString("AREA_CD") + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * get List of open bill window for drop down @MRD download screen.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static List<String> getBillWIndowList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			// String query =
			// "select t1.area_cd, t1.area_desc, t2.mr_cd, t2.subzone_cd from djb_area t1,djb_mr t2, djb_subzone t3 where t1.mr_id= t2.mr_id and t2.subzone_cd= t3.subzone_cd order by subzone_cd,mr_cd,area_desc asc";
			// String query =
			// "select t.bill_round_id, t.* from CM_BILL_WINDOW t where bill_win_stat_id=10 order by t.bill_round_id ";
			// String query =
			// "select t.bill_round_id, t.* from CM_BILL_WINDOW t order by t.bill_round_id ";
			String query = "select t.bill_round_id from CM_BILL_WINDOW t order by t.bill_round_id ";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("bill_round_id"));
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is to retrieve all the details from the database of the
	 * consumer for those meter read to be updated to validate the reading
	 * details.
	 * </p>
	 * 
	 * @exception SQLException
	 *                if a database access error occurs or this method is called
	 *                on a closed connection
	 * @exception IOException
	 *                if any kind of IO failure occurs
	 * @exception Exception
	 *                if any kind of failure occurs
	 * @param kno
	 *            Account id of the consumer
	 * @param billRound
	 *            current bill round
	 * @return consumerDetailList list of meter read details in the database
	 *         those are to be uploaded
	 * @see ConsumerDetail
	 * @see PreviousMeterReadDetails
	 * @see MeterReadUploadDetails
	 */
	public static List<ConsumerDetail> getConsumerDetailByAccountIDAndBillRound(
			String kno, String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ConsumerDetail> consumerDetailList = new ArrayList<ConsumerDetail>();
		ConsumerDetail consumerDetail = null;
		PreviousMeterReadDetails previousMeterReadDetails = null;
		try {
			// System.out.println("accountIds::" + kno + "billRoundId::"
			// + billRound);
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getConsumerDetailByAccountIDAndBillRoundQuery("'"
							+ billRound + "'", "'" + kno + "'");
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				consumerDetail = new ConsumerDetail();
				previousMeterReadDetails = new PreviousMeterReadDetails();
				consumerDetail.setKno(rs.getString("ACCT_ID").trim());
				consumerDetail.setBillRound(rs.getString("BILL_ROUND_ID")
						.trim());
				consumerDetail.setCategory(rs.getString("CUST_CL_CD"));
				String consumerType = rs.getString("SA_TYPE_CD");
				if ("SAWATSEW".equalsIgnoreCase(consumerType)
						|| "SAWATR".equalsIgnoreCase(consumerType)) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				consumerDetail.setConsumerType(consumerType);
				previousMeterReadDetails.setPreviousMeterRead(rs
						.getString("PREV_RGMTRRD"));
				previousMeterReadDetails.setPreviousMeterReadDate(rs
						.getString("PREV_MTRRDDT"));
				previousMeterReadDetails.setPreviousMeterReadStatus(rs
						.getString("PREV_MTRRDSTAT"));
				previousMeterReadDetails.setPreviousActualMeterRead(rs
						.getString("ACTL_RGMTRRD"));
				previousMeterReadDetails.setPreviousActualMeterReadDate(rs
						.getString("ACTL_MTRRDDT"));
				previousMeterReadDetails.setPreviousActualMeterReadStatus(rs
						.getString("ACTL_MTRRDSTAT"));
				previousMeterReadDetails.setPreviousAverageConsumption(rs
						.getString("AVG_READ"));
				consumerDetail
						.setPreviousMeterReadDetails(previousMeterReadDetails);
				consumerDetailList.add(consumerDetail);
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
		return consumerDetailList;
	}

	/**
	 * <p>
	 * This method is used to get DJB Meter Type List
	 * </p>
	 * 
	 * @return
	 */
	public static List<MeterTypeLookup> getDJBMeterTypeList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterTypeLookup meterTypeLookup = null;
		List<MeterTypeLookup> dataList = new ArrayList<MeterTypeLookup>();
		try {

			String query = "select t.Char_Val, t1.descr from ci_char_val t inner join ci_char_val_l t1 on t1.char_type_cd = t.char_type_cd and t1.char_val = t.char_val where t.char_type_cd = 'MTRTYP' order by t1.descr";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterTypeLookup = new MeterTypeLookup();
				meterTypeLookup.setMeterTypeCode(rs.getString("Char_Val")
						.trim());
				meterTypeLookup.setMeterTypeDesc(rs.getString("descr").trim());
				dataList.add(meterTypeLookup);
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
		return dataList;
	}

	/**
	 * <p>
	 * This method used to get the configuration String from
	 * <code>DJB_PARAM_MST</code> with <code>PARAM_ID</code> value equal to
	 * <code>MRD_DWNLD_CONFIG</code> stored in the database that is required to
	 * set with the reply of Meter read download web service.
	 * </p>
	 * 
	 * @exception SQLException
	 *                if a database access error occurs or this method is called
	 *                on a closed connection
	 * @exception IOException
	 *                if any kind of IO failure occurs
	 * @exception Exception
	 *                if any kind of failure occurs
	 * 
	 * @return true or false with respect to the value present in the database
	 *         or not.
	 * @return paramValue String
	 */
	public static String getDJBParamValue(String paramName) {
		AppLog.begin();
		String paramValue = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			String query = QueryContants.getDJBParamValueQuery();
			ps = conn.prepareStatement(query);
			ps.setString(1, paramName);
			rs = ps.executeQuery();
			while (rs.next()) {
				paramValue = rs.getString("PARAM_VALUE");
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
				// AppLog.error(e);
			}
		}
		AppLog.end();
		return paramValue;
	}

	/**
	 * <p>
	 * This method is used to get Manufacturer Details List
	 * </p>
	 * 
	 * @return ArrayList<ManufacturerDetailsLookup>
	 */
	public static List<ManufacturerDetailsLookup> getManufacturerDetailsList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ManufacturerDetailsLookup> dataList = new ArrayList<ManufacturerDetailsLookup>();
		try {
			ManufacturerDetailsLookup manufacturerDetailsLookup = null;
			String query = "select trim(t1.mfg_cd) mfg_cd, t2.descr from CI_MFG t1 inner join Ci_MFG_L t2 on t2.mfg_cd = t1.mfg_cd order by descr ";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				manufacturerDetailsLookup = new ManufacturerDetailsLookup();
				manufacturerDetailsLookup.setManufacturerCode(rs
						.getString("mfg_cd"));
				manufacturerDetailsLookup.setManufacturerDesc(rs
						.getString("descr"));
				dataList.add(manufacturerDetailsLookup);
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
		return dataList;
	}

	/**
	 * <p>
	 * Retrieve Designation list to populate Designation Drop down for Meter
	 * Reader.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getMeterReaderDesignationListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants
					.getMeterReaderDesignationListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (null != rs.getString("DESIG_CD")) {
					retrunMap.put(rs.getString("DESIG_CD").trim(), rs
							.getString("DESIG_DESC"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Retrieve all the read type code from the data base table
	 * <code>CM_CONS_MRD_READ_TYPE_MAP</code> corresponding to read type
	 * Description.
	 * </p>
	 * 
	 * @return MeterReadTypeLookUp which contains the read type code
	 *         corresponding to read type
	 * @see MeterReadType
	 */
	public static MeterReadTypeLookUp getMeterReadRemarkCorrespondingToReadType() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReadTypeLookUp meterReadTypeLookUp = null;
		String averageReadType = "";
		String noBillingReadType = "";
		String provisionalReadType = "";
		String regularReadType = "";
		try {
			conn = DBConnector.getConnection();
			meterReadTypeLookUp = new MeterReadTypeLookUp();

			ps = conn.prepareStatement(QueryContants.queryRegularRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				regularReadType = regularReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			meterReadTypeLookUp.setRegularReadType(new MeterReadType(
					"regularReadType", regularReadType));
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(QueryContants.queryAverageRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				averageReadType = averageReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			meterReadTypeLookUp.setAverageReadType(new MeterReadType(
					"averageReadType", averageReadType));
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(QueryContants.queryProvisionalRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				provisionalReadType = provisionalReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			meterReadTypeLookUp.setProvisionalReadType(new MeterReadType(
					"provisionalReadType", provisionalReadType));
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(QueryContants.queryNoBillingRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				noBillingReadType = noBillingReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			meterReadTypeLookUp.setNoBillingReadType(new MeterReadType(
					"noBillingReadType", noBillingReadType));

			// System.out.println("regularReadType::" + regularReadType
			// + "::averageReadType::" + averageReadType
			// + "::provisionalReadType::" + provisionalReadType
			// + "::noBillingReadType::" + noBillingReadType);
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
		return meterReadTypeLookUp;
	}

	/**
	 * <p>
	 * Populate Meter Read Status List as drop down at data entry page.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static List<String> getMeterReadStatusList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = "select distinct MTR_READ_TYPE_CD,MTR_STATS_CD from CM_MTR_STATS_LOOKUP where MTR_STATS_CD<>'NS' order by MTR_STATS_CD asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("MTR_STATS_CD").trim());
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to get Meter Model Details List
	 * </p>
	 * 
	 * @return ArrayList<ModelDetailsLookup>
	 */
	public static List<ModelDetailsLookup> getModelDetailsList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ModelDetailsLookup> dataList = new ArrayList<ModelDetailsLookup>();
		try {
			ModelDetailsLookup modelDetailsLookup = null;
			String query = "select trim(t1.model_cd) model_cd, t3.descr  from ci_model t1 inner join CI_MFG t2 on t2.mfg_cd = t1.mfg_cd inner join ci_model_l t3 on t3.model_cd = t1.model_cd order by descr ";
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				modelDetailsLookup = new ModelDetailsLookup();
				modelDetailsLookup.setModelCode(rs.getString("model_cd"));
				modelDetailsLookup.setModelDesc(rs.getString("descr"));
				dataList.add(modelDetailsLookup);
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
		return dataList;
	}

	/**
	 * <p>
	 * Added to get MRD Read Type
	 * </p>
	 * 
	 * @return
	 */
	public static MRDReadTypeLookup getMRDReadTypeList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MRDReadTypeLookup mrdReadTypeLookup = null;
		String averageReadType = "";
		String noBillingReadType = "";
		String provisionalReadType = "";
		String regularReadType = "";
		try {
			conn = DBConnector.getConnection();
			mrdReadTypeLookup = new MRDReadTypeLookup();
			String queryRegularRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Regular Read' order by READER_REM_CD asc";
			String queryAverageRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Average Read' order by READER_REM_CD asc";
			String queryProvisionalRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Provisional Read' order by READER_REM_CD asc";
			String queryNoBillingRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='No Billing Read' order by READER_REM_CD asc";

			ps = conn.prepareStatement(queryRegularRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				regularReadType = regularReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			mrdReadTypeLookup.setRegularReadType(regularReadType);
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(queryAverageRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				averageReadType = averageReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			mrdReadTypeLookup.setAverageReadType(averageReadType);
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(queryProvisionalRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				provisionalReadType = provisionalReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			mrdReadTypeLookup.setProvisionalReadType(provisionalReadType);
			if (null != ps) {
				ps.close();
			}
			if (null != rs) {
				rs.close();
			}
			ps = conn.prepareStatement(queryNoBillingRead);
			rs = ps.executeQuery();
			while (rs.next()) {
				noBillingReadType = noBillingReadType
						+ rs.getString("READER_REM_CD") + ",";
			}
			mrdReadTypeLookup.setNoBillingReadType(noBillingReadType);
			// if (null != ps) {
			// ps.close();
			// }
			// if (null != rs) {
			// rs.close();
			// }
			// System.out.println("regularReadType::" + regularReadType
			// + "::averageReadType::" + averageReadType
			// + "::provisionalReadType::" + provisionalReadType
			// + "::noBillingReadType::" + noBillingReadType);
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
		return mrdReadTypeLookup;
	}

	/**
	 * <p>
	 * Method to get List of Submited MRDSchedule Job
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public static Map<String, Object> getMRDScheduleJobList(
			Map<String, Object> inputMap) {
		AppLog.begin();
		// System.out.println("getMRDScheduleJobList");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<MRDScheduleDownloadDetails> dataList = new ArrayList<MRDScheduleDownloadDetails>();
			MRDScheduleDownloadDetails mrdScheduleDownloadDetails = null;
			String userID = (String) inputMap.get("userID");
			String sessionID = (String) inputMap.get("sessionID");
			String status = (String) inputMap.get("status");
			String forToday = (String) inputMap.get("forToday");
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" select t.bill_window,t.search_criteria,t.status,t.downloaded_flag,nvl(t.file_name,'')file_name,to_char(t.create_date,'dd-MON-YYYY hh:mi AM')create_date,t.created_by,FLOOR((t.total_file_created/t.total_file_to_be_created)*100) pc_progress from MRD_SCHEDUL_DNLD_LOG t");
			querySB.append(" where 1=1 and t.total_file_to_be_created<>0 ");
			if (null != userID && !"".equals(userID)) {
				querySB.append(" and t.created_by='" + userID + "'");
			}
			if (null != sessionID && !"".equals(sessionID)) {
				querySB.append(" and t.j_session_id='" + sessionID + "'");
			}
			if (null != status && !"".equals(status)) {
				querySB.append(" and t.status in ('" + status + "') ");
			}
			if (null != forToday && !"".equalsIgnoreCase(forToday)) {
				querySB
						.append(" and to_char(t.create_date,'dd-MON-YYYY')=to_char(sysdate,'dd-MON-YYYY') ");
			}
			querySB.append(" order by t.create_date desc ");

			StringBuffer querySameJOBSB = new StringBuffer();
			querySameJOBSB
					.append(" select t.bill_window,t.search_criteria,t.status,t.downloaded_flag,nvl(t.file_name,'')file_name,to_char(t.create_date,'dd-MON-YYYY hh:mi AM')create_date,t.created_by,FLOOR((t.total_file_created/t.total_file_to_be_created)*100) pc_progress from MRD_SCHEDUL_DNLD_LOG t");
			querySameJOBSB
					.append(" where 1=1 and t.total_file_to_be_created<>0 ");

			// System.out.println("querySB::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mrdScheduleDownloadDetails = new MRDScheduleDownloadDetails();
				mrdScheduleDownloadDetails.setBillWindow(rs
						.getString("bill_window"));
				mrdScheduleDownloadDetails.setSearchCriteria(rs
						.getString("search_criteria"));
				mrdScheduleDownloadDetails.setStatus(rs.getString("status"));
				mrdScheduleDownloadDetails.setDownloadedFlag(rs
						.getString("downloaded_flag"));
				String fileName = rs.getString("file_name");
				if (null == fileName || "".equals(fileName)) {
					fileName = "&nbsp;";
				}
				mrdScheduleDownloadDetails.setFileName(fileName);
				mrdScheduleDownloadDetails.setPercentageOfCompletion(rs
						.getString("pc_progress"));
				mrdScheduleDownloadDetails.setCreateDate(rs
						.getString("create_date"));
				mrdScheduleDownloadDetails.setCreatedBy(rs
						.getString("created_by"));
				dataList.add(mrdScheduleDownloadDetails);
			}
			inputMap.put("dataList", dataList);
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
		return inputMap;
	}

	/**
	 * <p>
	 * Method to Get MRD Type for MRD Type Drop down.
	 * </p>
	 * 
	 * @return
	 */
	public static Map<String, String> getMRDTypeMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getMRDTypeMapQuery();

			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String mrdType = rs.getString("MRD_TYPE");
				if (null != mrdType && !"".equals(mrdType.trim())) {
					retrunMap.put(mrdType.trim(), mrdType.trim());
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Method to Retrieve MRKEY list to populate MRKEY Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getMRKEYByZoneMRArea(String zone,
			String mrNo, String area) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getMRKEYByZoneMRAreaQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, zone);
			ps.setString(++i, mrNo);
			ps.setString(++i, area);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put("mrKey", rs.getString("MRD_CD").trim());
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Method to Retrieve MRKEY list to populate MRKEY Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getMRKEYListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getMRKEYListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("MRD_CD").trim(), rs.getString(
						"MRD_CD").trim());
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Method to get MR No List
	 * </p>
	 * 
	 * @return rrayList<String>
	 */

	public static List<String> getMRNoList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			// String query =
			// "select t1.mr_cd, t1.mr_desc,t1.subzone_cd from djb_mr t1,djb_subzone t2 where t1.subzone_cd= t2.subzone_cd order by subzone_cd,mr_desc asc";
			String query = "SELECT distinct mr_cd,subzone_cd  FROM djb_zn_mr_ar_mrd order by mr_cd,subzone_cd asc";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("mr_cd") + "-("
						+ rs.getString("mr_cd") + "-"
						+ rs.getString("subzone_cd") + ")");
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
		return dataList;
	}

	/**
	 * <p>
	 * Get MR No to populate MR No Drop down corresponding to a Zone Code.
	 * </p>
	 * 
	 * @param zone
	 * @return MRNoListMap
	 */
	public static Map<String, String> getMRNoListMap(String zone) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getMRNoListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, zone);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("MR_CD").trim(), rs
						.getString("MR_CD"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Get Open Bill Round for a particular MR Key identified by Zone, MR No and
	 * Area Code which is in open status that is 10.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param areaCode
	 * @return
	 */
	public static List<String> getOpenBillRound(String zone, String mrNo,
			String areaCode, String status) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = QueryContants.getOpenBillRoundQuery(status);
			// AppLog.info("query::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, zone);
			ps.setString(++i, mrNo);
			ps.setString(++i, areaCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("BILL_ROUND_ID"));
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
		return dataList;
	}

	/**
	 * <p>
	 * Get Count of Open Bill Round for an MRKey.
	 * </p>
	 * 
	 * @param mrKey
	 * @return
	 */
	public static String getOpenBillRoundForAnMRKey(String mrKey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String openBillRound = null;
		try {
			String query = QueryContants.getOpenBillRoundForAnMRKeyQuery();
			// AppLog.info("getSortedKNOListForDemandTransferQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, mrKey);
			rs = ps.executeQuery();
			while (rs.next()) {
				openBillRound = rs.getString("BILL_ROUND_ID");
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
		return openBillRound;
	}

	/**
	 * <p>
	 * This method is used to fetch ListMap values of only open bill round id.
	 * as per Jtrac DJB-4464 & Open project id -1208
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @param
	 * @since 14-JUNE-2016
	 * @return
	 */

	public static List<String> getOpenBillWIndowList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {
			String query = "select t.bill_round_id from CM_BILL_WINDOW t where 1=1 and t.bill_win_stat_id ='10' ";

			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("bill_round_id"));
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
		return dataList;
	}

	/**
	 * <p>
	 * This method returns the ListMap values of predefined Char Value and
	 * Description for the parameterised CharType of CC&B as per Jtrac DJB-4313
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @param charType
	 * @since 13-FEB-2016
	 * @return
	 * @history Reshma on 11-03-2016 edited the below method to provide
	 *          decription first then code in drop down list, JTrac DJB-4405.
	 */
	public static Map<String, String> getPreDefChrValListMap(String charType) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getPreDefChrValListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, charType.trim());
			rs = ps.executeQuery();
			while (rs.next()) {
				// AppLog.info(">>Inside rs>, size>>>"+rs.getString("VAL")+rs.getString("VAL_DESC")
				// );
				if (null != rs.getString("VAL")
						&& !"".equalsIgnoreCase(rs.getString("VAL"))) {
					/**
					 * Start : Reshma on 11-03-2016 edited the below method to
					 * provide decription first then code in drop down list,
					 * JTrac DJB-4405.
					 */
					/*
					 * retrunMap.put(rs.getString("VAL").trim(), rs
					 * .getString("VAL") + "(" + rs.getString("VAL_DESC") +
					 * ")");
					 */
					retrunMap.put(rs.getString("VAL").trim(), rs
							.getString("VAL_DESC")
							+ " (" + rs.getString("VAL").trim() + ")");
					/**
					 * End : Reshma on 11-03-2016 edited the below method to
					 * provide decription first then code in drop down list,
					 * JTrac DJB-4405.
					 */
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Retrieve Previous Actual Meter Read Details For a Consumer.
	 * </p>
	 * 
	 * @param kno
	 * @param meterInstallDate
	 * @param oldSAStartDate
	 * @return
	 */
	public static MeterReadDetails getPrevActualMeterReadDetailsForMR(
			String kno, String meterInstallDate, String oldSAStartDate) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReadDetails meterReadDetails = new MeterReadDetails();
		try {
			String query = QueryContants
					.getPrevActualMeterReadDetailsForMRQuery();
			// System.out.println("Qury::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, kno);
			ps.setString(++i, meterInstallDate);
			ps.setString(++i, oldSAStartDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReadDetails.setMeterReadDate(null == rs
						.getString("READ_DATE") ? "NA" : rs
						.getString("READ_DATE"));
				meterReadDetails
						.setRegRead(null == rs.getString("REG_READ") ? "NA"
								: rs.getString("REG_READ"));
				meterReadDetails.setReadType(null == rs
						.getString("READ_TYPE_CD") ? "NA" : rs
						.getString("READ_TYPE_CD"));
				meterReadDetails.setMeterStatus(null == rs
						.getString("READER_REM_CD") ? "NA" : rs
						.getString("READER_REM_CD"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (IOException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
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
		return meterReadDetails;
	}

	/**
	 * <p>
	 * Retrieve Previous Meter Read Details For a Consumer.
	 * </p>
	 * 
	 * @param kno
	 * @param meterInstallDate
	 * @param oldSAStartDate
	 * @return
	 */
	public static MeterReadDetails getPrevMeterReadDetailsForMR(String kno,
			String meterInstallDate, String oldSAStartDate) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReadDetails meterReadDetails = new MeterReadDetails();
		try {
			String query = QueryContants.getPrevMeterReadDetailsForMRQuery();
			// System.out.println("Qury::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, kno);
			ps.setString(++i, meterInstallDate);
			ps.setString(++i, oldSAStartDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReadDetails.setMeterReadDate(null == rs
						.getString("READ_DATE") ? "NA" : rs
						.getString("READ_DATE"));
				meterReadDetails
						.setRegRead(null == rs.getString("REG_READ") ? "NA"
								: rs.getString("REG_READ"));
				meterReadDetails.setReadType(null == rs
						.getString("READ_TYPE_CD") ? "NA" : rs
						.getString("READ_TYPE_CD"));
				meterReadDetails.setMeterStatus(null == rs
						.getString("READER_REM_CD") ? "NA" : rs
						.getString("READER_REM_CD"));
			}
		} catch (SQLException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (IOException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
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
		return meterReadDetails;
	}

	/**
	 * <p>
	 * Method to Populate SA type list
	 * </p>
	 * 
	 * @return
	 */
	public static List<SATypeDetails> getSATypeList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SATypeDetails> dataList = new ArrayList<SATypeDetails>();
		try {

			String query = "select distinct t.sa_type_code,t.sa_type_desc from djb_data_entry_sa_type t order by sa_type_desc asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				dataList.add(new SATypeDetails(rs.getString("sa_type_code")
						.trim(), rs.getString("sa_type_desc").trim()));
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
		return dataList;
	}

	/***************************************************************************************************/

	/**
	 * <p>
	 * Method to Retrieve Save Status list to populate Status Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 * @author Arnab Nandy
	 * @since 01-02-2016
	 */
	public static Map<String, String> getSaveStatusListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String roleList = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {

			String query2 = QueryContants.getRoleList();

			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps1 = conn.prepareStatement(query2);
			rs1 = ps1.executeQuery();

			AppLog.info(query2);

			while (rs1.next()) {
				AppLog.info("STATUS_CODE_ACCESS>>"
						+ rs1.getString("STATUS_CODE_ACCESS"));
				roleList = rs1.getString("STATUS_CODE_ACCESS");
			}

			String query = QueryContants.getSaveStatusListMapQuery(roleList);
			AppLog.info(query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("status_code>>" + rs.getString("status_code")
						+ "status_desc>>" + rs.getString("status_desc"));
				retrunMap.put(rs.getString("status_code").trim(), rs
						.getString("status_desc"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Get Service Cycle to populate Service Cycle Drop down corresponding to
	 * MRKEY.
	 * </p>
	 * 
	 * @param MRKEY
	 * @return ServiceCycleListMap
	 */
	public static Map<String, String> getServiceCycleListMap(String mrkey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getServiceCycleListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, mrkey);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("SERVICE_CYCLE").trim(), rs
						.getString("SERVICE_CYCLE"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Get Service Route to populate Service Route Drop down corresponding to
	 * MRKEY and Service Cycle.
	 * </p>
	 * 
	 * @param MRKEY
	 *            ,serviceCycle
	 * @return ServiceCycleListMap
	 */
	public static Map<String, String> getServiceRouteListMap(String mrkey,
			String serviceCycle) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getServiceRouteListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, mrkey);
			ps.setString(2, serviceCycle);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("SERVICE_ROUTE").trim(), rs
						.getString("SERVICE_ROUTE"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Retrieve Status list to populate Status Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 * @author Arnab Nandy
	 * @since 28-01-2016
	 */
	public static Map<String, String> getStatusListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getStatusListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("status_code>>" + rs.getString("status_code")
						+ "status_desc>>" + rs.getString("status_desc"));
				retrunMap.put(rs.getString("status_code").trim(), rs
						.getString("status_desc"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method returns the ListMap values of tagged mrkeys of AMR users as
	 * per Jtrac DJB-4464 & Open project id -1208
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @param empId
	 * @since 14-JUNE-2016
	 * @return
	 */
	public static Map<String, String> getTaggedMrkeysEmp(String empId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> taggedMrkey = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getTaggedMrkeysEmp();
			AppLog.info("Print query for getTaggedMrkeysEmp" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, empId);
			rs = ps.executeQuery();

			while (rs.next()) {

				taggedMrkey.put(rs.getString("MRKEY"), rs.getString("MRKEY"));
				AppLog.info("Mrkey tagged to AMR User ::MRKEY::"
						+ rs.getString("MRKEY"));
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
		return taggedMrkey;

	}

	/**
	 * <p>
	 * Retrieve count of meter reader named 'DJB' which is actively present for
	 * respective zones.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 14-12-2015
	 * @param newZROCode
	 * @return getRow
	 */

	public static int getUserExistenceCount(String newZROCode) {
		AppLog.begin();
		int getRow = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (null != newZROCode && !UtilityForAll.isEmpty(newZROCode)) {
				String query = QueryContants.chkUserExistenceQuery();
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, newZROCode.trim());
				rs = ps.executeQuery();
				while (rs.next()) {
					getRow = rs.getInt("USER_COUNT");
					AppLog.info("No.Of Meter Reader Named 'DJB'::" + getRow);
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
		return getRow;
	}

	/**
	 * <p>
	 * Retrieve User list to populate Loggedby Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 * @author Arnab Nandy
	 * @since 01-02-2016
	 */
	public static Map<String, String> getUserListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {

			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);

			String query = QueryContants.getUserListMapQuery();
			AppLog.info(query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppLog.info("user_id>>" + rs.getString("user_id")
						+ "user_name>>" + rs.getString("user_name"));
				retrunMap.put(rs.getString("user_id").trim(), rs
						.getString("user_name"));
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method is used to get list of user roles
	 * </p>
	 * 
	 * @return ArrayList get User Role List
	 */
	public static List<UserRole> getUserRoleList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserRole> dataList = new ArrayList<UserRole>();
		try {
			String query = "select USER_ROLE, USER_ROLE_DESC from DJB_DATA_ENTRY_USERS_ROLE where 1=1 order by USER_ROLE_DESC ASC";
			// AppLog.info("query>>" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(new UserRole(rs.getString("USER_ROLE"), rs
						.getString("USER_ROLE_DESC")));
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to populate Water Connection Size List
	 * </p>
	 * 
	 * @return
	 */
	public static List<String> getWaterConnectionSizeList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = "select t.char_val from ci_char_val t  where trim(char_type_cd) = 'WCONSIZE' and t.char_val <> 'N/A' ORDER BY to_number(t.char_val) asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			dataList.add("N/A");
			while (rs.next()) {
				dataList.add(rs.getString("char_val").trim());
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to populate Water Connection Use List
	 * </p>
	 * 
	 * @return
	 */
	public static List<String> getWaterConnectionUseList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			String query = "select distinct  trim(t.wc_use)wc_use from property_type_wc_use_map t order by wc_use asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("wc_use").trim());
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to populate Zone Drop down at MRD Search screen
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static List<String> getZoneList() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataList = new ArrayList<String>();
		try {

			// String query =
			// "select subzone_cd, subzone_name from djb_subzone order by subzone_name asc";
			String query = "select distinct t1.subzone_cd, t2.subzone_name from djb_zn_mr_ar_mrd t1,djb_subzone t2 where t1.subzone_cd=t2.subzone_cd order by subzone_name asc";
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataList.add(rs.getString("subzone_name") + "-("
						+ rs.getString("subzone_cd") + ")");
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
		return dataList;
	}

	/**
	 * <p>
	 * This method is used to retrieve Zone list to populate Zone Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getZoneListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getZoneListMapQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("subzone_cd").trim(), rs
						.getString("subzone_name")
						+ "(" + rs.getString("subzone_cd") + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method is used to retrieve Zone MR Area By MRKEY.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getZoneMRAreaByMRKEY(String mrkey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getZoneMRAreaByMRKEYQuery();
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, mrkey);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put("zone", rs.getString("subzone_cd").trim());
				retrunMap.put("mrNo", rs.getString("MR_CD").trim());
				retrunMap.put("area", rs.getString("AREA_CD").trim());
				retrunMap.put("areaDesc", rs.getString("AREA_DESC") + "("
						+ rs.getString("AREA_CD") + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * Retrieve ZRO list to populate ZRO Drop down by zone, mrNo, area, mrKey.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKey
	 * @return
	 */
	public static Map<String, String> getZRO(String zone, String mrNo,
			String area, String mrKey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getZROQuery(zone, mrNo, area, mrKey);

			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != mrKey && !"".equalsIgnoreCase(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
				ps.setString(++i, zone);
			}
			if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
				ps.setString(++i, mrNo);
			}
			if (null != area && !"".equalsIgnoreCase(area.trim())) {
				ps.setString(++i, area);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("ZRO_CD").trim(), rs.getString(
						"ZRO_DESC").trim()
						+ "(" + rs.getString("ZRO_CD").trim() + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method is used to fetch Zro location with respect to zone.mr and
	 * area. as per Jtrac DJB -2200 & Open project id-1540
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @param
	 * @since 06-AUG-2016
	 * @return
	 */

	public static String getZroCodeByZoneMrArea(String areaCode, String mrCode,
			String zone) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroCode = null;
		try {
			String query = QueryContants.getZrocdByZoneMrAreaQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, areaCode);
			ps.setString(2, mrCode);
			ps.setString(3, zone);
			AppLog.info("areaCode**" + areaCode + "mrCode**" + mrCode
					+ "zone***" + zone);
			rs = ps.executeQuery();
			AppLog.info("after exe query");
			while (rs.next()) {
				zroCode = rs.getString("ZRO_CD");
				AppLog.info("*** Zro location ***" + zroCode);
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

		return zroCode;
	}

	/**
	 * <p>
	 * Retrieve ZRO list to populate ZRO Drop down.
	 * </p>
	 * 
	 * @return
	 * @author Arnab Nandy
	 * @since 28-01-2016
	 */
	public static Map<String, String> getZROList(String list) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getZROQuery(list);
			conn = DBConnector.getConnection();
			// AppLog.info("query>>" + query);
			AppLog.info("query>>" + query);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("ZRO_CD").trim(), rs.getString(
						"ZRO_DESC").trim()
						+ "(" + rs.getString("ZRO_CD").trim() + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method is used to retrieve ZRO list to populate ZRO Drop down.
	 * </p>
	 * 
	 * @return ArrayList<String>
	 */
	public static Map<String, String> getZROListMap() {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> retrunMap = new LinkedHashMap<String, String>();
		try {
			String query = QueryContants.getZROListMapQuery();
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				retrunMap.put(rs.getString("ZRO_CD").trim(), rs
						.getString("ZRO_DESC")
						+ "(" + rs.getString("ZRO_CD") + ")");
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
		return retrunMap;
	}

	/**
	 * <p>
	 * This method is used to read the Meter Read Type code for the given Meter Read Remark Code
	 * </p>
	 * 
	 * @param aMtrReadRemarkCd
	 *            - Meter Read Remark Code
	 * @return unique Meter Read Type code for given meter read remark code
	 */
	public static String readReadTypeByMeterReadREmark(String aMtrReadRemarkCd) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (DataEntryAppConstants.MTR_READ_TYPE_LOOKUP_BY_MR_STAT_CD.isEmpty()) {
			StringBuffer mtrStatLookupSlctStr = new StringBuffer();
			mtrStatLookupSlctStr.append("SELECT mtr_stats_cd as MtrStatCd, ");
			mtrStatLookupSlctStr
					.append("       mtr_read_type_cd as ReadTypeCD ");
			mtrStatLookupSlctStr.append("FROM   cm_mtr_stats_lookup ");
			try {
				conn = DBConnector.getConnection();
				ps = conn.prepareStatement(mtrStatLookupSlctStr.toString());
				rs = ps.executeQuery();
				while (rs.next()) {
					DataEntryAppConstants.MTR_READ_TYPE_LOOKUP_BY_MR_STAT_CD
							.put(rs.getString("MtrStatCd").trim(), rs
									.getString("ReadTypeCD").trim());
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

		}
		AppLog.end();
		return DataEntryAppConstants.MTR_READ_TYPE_LOOKUP_BY_MR_STAT_CD
				.get(aMtrReadRemarkCd.trim());
	}

	/*
	 * <p> This overloaded method is used AJAX call response based on
	 * acctIDAliasKNO where consumer prebill proc status is less than 65 </p>
	 * 
	 * 
	 * ****
	 */

	/**
	 * <p>
	 * Validates the new meter reader remark code against old meter reader
	 * remark code in table cm_mtr_stat_check. If entry exists then returns true
	 * else returns false.
	 * </p>
	 * 
	 * @param aPrevRemarkCd
	 *            - Previous Meter Reader Remark code
	 * @param aNewRemarkCd
	 *            - Current Meter Reader Remark Code
	 * @return <code>true</code> if entry exists in table else returns
	 *         <code>false</code>
	 */
	public static boolean validateMeterReaderRemark(String aPrevRemarkCd,
			String aNewRemarkCd) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer validateQueryStr = new StringBuffer();
		validateQueryStr.append("SELECT Count(*) ");
		validateQueryStr.append("FROM   cm_mtr_stat_check ");
		validateQueryStr.append("WHERE  current_reader_rem_cd = ? ");
		validateQueryStr.append("       AND prev_reader_rem_cd = ? ");
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(validateQueryStr.toString());
			ps.setString(1, aNewRemarkCd);
			ps.setString(2, aNewRemarkCd);
			rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					return true;
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
		return false;
	}

	/**
	 * <p>
	 * This method is used to check Duplicate MRDSchedule Job List
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public Map<String, Object> checkDuplicateMRDScheduleJobList(
			Map<String, Object> inputMap) {
		AppLog.begin();
		// System.out.println("checkSimilarMRDScheduleJobList");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String validationType = null;
		boolean isValid = true;
		try {

			ArrayList<MRDScheduleDownloadDetails> dataList = new ArrayList<MRDScheduleDownloadDetails>();
			MRDScheduleDownloadDetails mrdScheduleDownloadDetails = null;
			String userID = (String) inputMap.get("userID");
			// String sessionID = (String) inputMap.get("sessionID");
			String searchCriteria = (String) inputMap.get("searchCriteria");
			// String status = (String) inputMap.get("status");
			// String zoneCode = (String) inputMap.get("zoneCode");
			// String mrNo = (String) inputMap.get("mrNo");
			// String areaCode = (String) inputMap.get("areaCode");
			String billWindow = (String) inputMap.get("billWindow");

			// Checking for duplicate
			conn = DBConnector.getConnection();
			StringBuffer queryDuplicateSB = new StringBuffer();
			queryDuplicateSB
					.append("select t.bill_window,t.search_criteria,t.status,t.downloaded_flag,nvl(t.file_name,'')file_name,to_char(t.create_date,'dd-MON-YYYY hh:mi AM')create_date,t.created_by,FLOOR((t.total_file_created/t.total_file_to_be_created)*100) pc_progress from MRD_SCHEDUL_DNLD_LOG t");
			queryDuplicateSB
					.append(" where 1=1 and t.total_file_to_be_created <> 0 and t.status not in ('Failed','Incomplete')");
			if (null != billWindow && !"".equals(billWindow)) {
				queryDuplicateSB.append(" and t.bill_window='" + billWindow
						+ "'");
			}
			if (null != searchCriteria && !"".equals(searchCriteria)) {
				queryDuplicateSB.append(" and t.search_criteria ='"
						+ searchCriteria + "'");
			}
			if (null != searchCriteria && !"".equals(searchCriteria)) {
				queryDuplicateSB.append(" and t.created_by ='" + userID + "'");
			}
			ps = conn.prepareStatement(queryDuplicateSB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				isValid = false;
				validationType = "DuplicateJob";
				mrdScheduleDownloadDetails = new MRDScheduleDownloadDetails();
				mrdScheduleDownloadDetails.setStatus(rs.getString("status"));
				String fileName = rs.getString("file_name");
				if (null == fileName) {
					fileName = "&nbsp;";
				}
				mrdScheduleDownloadDetails = new MRDScheduleDownloadDetails();
				mrdScheduleDownloadDetails.setBillWindow(rs
						.getString("bill_window"));
				mrdScheduleDownloadDetails.setSearchCriteria(rs
						.getString("search_criteria"));
				mrdScheduleDownloadDetails.setStatus(rs.getString("status"));
				mrdScheduleDownloadDetails.setDownloadedFlag(rs
						.getString("downloaded_flag"));
				mrdScheduleDownloadDetails.setFileName(fileName);
				mrdScheduleDownloadDetails.setPercentageOfCompletion(rs
						.getString("pc_progress"));
				mrdScheduleDownloadDetails.setCreateDate(rs
						.getString("create_date"));
				mrdScheduleDownloadDetails.setCreatedBy(rs
						.getString("created_by"));
				dataList.add(mrdScheduleDownloadDetails);
			}

			inputMap.put("dataList", dataList);
			// System.out.println("dataList::" + dataList.size());
			inputMap.put("isValid", isValid);
			inputMap.put("validationType", validationType);

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
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
		AppLog.end();
		return inputMap;
	}

	/**
	 * <p>
	 * This method is used to check Similar MRDSchedule Job List
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public Map<String, Object> checkSimilarMRDScheduleJobList(
			Map<String, Object> inputMap) {
		AppLog.begin();
		// System.out.println("checkSimilarMRDScheduleJobList");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String validationType = null;
		boolean isValid = true;
		try {

			ArrayList<MRDScheduleDownloadDetails> dataList = new ArrayList<MRDScheduleDownloadDetails>();
			MRDScheduleDownloadDetails mrdScheduleDownloadDetails = null;
			String userID = (String) inputMap.get("userID");
			// String sessionID = (String) inputMap.get("sessionID");
			String searchCriteria = (String) inputMap.get("searchCriteria");
			// String status = (String) inputMap.get("status");
			String zoneCode = (String) inputMap.get("zoneCode");
			String mrNo = (String) inputMap.get("mrNo");
			// String areaCode = (String) inputMap.get("areaCode");
			String billWindow = (String) inputMap.get("billWindow");

			StringBuffer queryInProgressSB1 = new StringBuffer();
			queryInProgressSB1
					.append(" select t.zone_code,t.mr_no,t.area_code,t.bill_window,t.search_criteria,t.status,t.downloaded_flag,nvl(t.file_name,'')file_name,to_char(t.create_date,'dd-MON-YYYY hh:mi AM')create_date,t.created_by,FLOOR((t.total_file_created/t.total_file_to_be_created)*100) pc_progress from MRD_SCHEDUL_DNLD_LOG t");
			queryInProgressSB1.append(" where 1=1 ");
			if (null != billWindow && !"".equals(billWindow)) {
				queryInProgressSB1.append(" and t.bill_window='" + billWindow
						+ "'");
			}
			if (null != userID && !"".equals(userID)) {
				queryInProgressSB1.append(" and t.created_by='" + userID + "'");
			}
			queryInProgressSB1
					.append(" and t.status in ('In Progress','Submitted')");
			if (null != zoneCode && !"".equals(zoneCode)) {
				queryInProgressSB1
						.append(" and ((t.zone_code='"
								+ zoneCode
								+ "' and t.mr_no is null)or(t.zone_code='"
								+ zoneCode
								+ "' and t.mr_no ='"
								+ mrNo
								+ "' and t.area_code is null) or t.search_criteria like '"
								+ searchCriteria + "%')");
			}
			queryInProgressSB1
					.append(" and to_char(t.create_date,'dd-MON-YYYY')=to_char(sysdate,'dd-MON-YYYY') ");

			queryInProgressSB1.append(" order by t.create_date desc ");
			// System.out.println("queryInProgressSB1::"
			// + queryInProgressSB1.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(queryInProgressSB1.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				isValid = false;
				validationType = "SimilarJobInProgress";
				mrdScheduleDownloadDetails = new MRDScheduleDownloadDetails();
				mrdScheduleDownloadDetails.setStatus(rs.getString("status"));
				String fileName = rs.getString("file_name");
				if (null == fileName) {
					fileName = "&nbsp;";
				}
				mrdScheduleDownloadDetails = new MRDScheduleDownloadDetails();
				mrdScheduleDownloadDetails.setBillWindow(rs
						.getString("bill_window"));
				mrdScheduleDownloadDetails.setSearchCriteria(rs
						.getString("search_criteria"));
				mrdScheduleDownloadDetails.setStatus(rs.getString("status"));
				mrdScheduleDownloadDetails.setDownloadedFlag(rs
						.getString("downloaded_flag"));
				mrdScheduleDownloadDetails.setFileName(fileName);
				mrdScheduleDownloadDetails.setPercentageOfCompletion(rs
						.getString("pc_progress"));
				mrdScheduleDownloadDetails.setCreateDate(rs
						.getString("create_date"));
				mrdScheduleDownloadDetails.setCreatedBy(rs
						.getString("created_by"));
				dataList.add(mrdScheduleDownloadDetails);
			}
			inputMap.put("dataList", dataList);
			// System.out.println("dataList::" + dataList.size());
			inputMap.put("isValid", isValid);
			inputMap.put("validationType", validationType);

		} catch (SQLException e) {
			AppLog.error(e);
		} catch (IOException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
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
		AppLog.end();
		return inputMap;
	}

	/**
	 * <p>
	 * This method is used to get Consumer Details
	 * </p>
	 * 
	 * @param kno
	 * @return
	 */
	public String getConsumerDetails(String kno) {
		AppLog.begin();
		// System.out.println("KNO::" + kno);
		String consumerName = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select a.acct_id,b.entity_name from CI_ACCT_PER a,CI_PER_NAME b where a.per_id=b.per_id and a.acct_id=? ");

		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			ps.setString(1, kno);
			rs = ps.executeQuery();
			while (rs.next()) {
				consumerName = rs.getString("entity_name");
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
		return consumerName;
	}

	/**
	 * <p>
	 * This method is used to get Consumer Details For Meter Replacement By KNO
	 * </p>
	 * 
	 * @param kno
	 * @param billRound
	 * @return
	 */
	public MeterReplacementDetail getConsumerDetailsForMRByKNO(String kno,
			String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReplacementDetail meterReplacementDetail = null;
		try {
			String query = "select distinct t.acct_id,t.cust_cl_cd,z.subzone_cd,z.subzone_name,d.mr_cd, d.area_cd, t.wcno, t.consumer_name, t.sa_type_cd,t.avg_read from cm_cons_pre_bill_proc t, djb_zn_mr_ar_mrd d, djb_subzone z where t.mrkey = d.mrd_cd and d.subzone_cd = z.subzone_cd and t.acct_id = ? and t.bill_round_id=?";
			// AppLog.info("getConsumerDetailsForMRByKNO query::" + query);
			// System.out.println("getConsumerDetailsForMRByKNO query::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, kno);
			ps.setString(2, billRound);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setKno(rs.getString("acct_id"));
				meterReplacementDetail.setZone(rs.getString("subzone_name")
						+ "-(" + rs.getString("subzone_cd") + ")");
				meterReplacementDetail.setMrNo(rs.getString("mr_cd"));
				meterReplacementDetail.setArea(rs.getString("area_cd"));
				meterReplacementDetail.setWcNo(rs.getString("wcno"));
				meterReplacementDetail.setName(rs.getString("consumer_name")
						.trim());
				meterReplacementDetail.setSaType(rs.getString("sa_type_cd")
						.trim());
				String consumerType = rs.getString("sa_type_cd");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setConsumerCategory(rs.getString(
						"cust_cl_cd").trim());
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("avg_read"));
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
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * This method is used to get Consumer Details For Meter Replacement By Zone MR Area WC No
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param areaCode
	 * @param wcNo
	 * @return
	 */
	public MeterReplacementDetail getConsumerDetailsForMRByZMAW(
			String billRound, String zone, String mrNo, String areaCode,
			String wcNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReplacementDetail meterReplacementDetail = null;
		// System.out.println("billRound::" + billRound + "::zone::" + zone
		// + "::mrNo::" + mrNo + "::areaCode::" + areaCode + "::wcNo::"
		// + wcNo);
		try {
			String query = "select distinct t.acct_id,t.cust_cl_cd,z.subzone_cd,z.subzone_name,d.mr_cd, d.area_cd, t.wcno, t.consumer_name, t.sa_type_cd, t.avg_read from cm_cons_pre_bill_proc t, djb_zn_mr_ar_mrd d, djb_subzone z where t.mrkey = d.mrd_cd and d.subzone_cd = z.subzone_cd  and t.bill_round_id=? and z.subzone_cd=? and d.mr_cd=? and d.area_cd =? and t.wcno=? ";
			// AppLog.info("getConsumerDetailsForMRByZMAW query>>" + query);
			// System.out.println("getConsumerDetailsForMRByZMAW query::" +
			// query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, billRound);
			ps.setString(2, zone);
			ps.setString(3, mrNo);
			ps.setString(4, areaCode);
			ps.setString(5, wcNo);

			rs = ps.executeQuery();
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setKno(rs.getString("acct_id"));
				meterReplacementDetail.setZone(rs.getString("subzone_name")
						+ "-(" + rs.getString("subzone_cd") + ")");
				meterReplacementDetail.setMrNo(rs.getString("mr_cd"));
				meterReplacementDetail.setArea(rs.getString("area_cd"));
				meterReplacementDetail.setWcNo(rs.getString("wcno"));
				meterReplacementDetail.setName(rs.getString("consumer_name"));
				meterReplacementDetail.setSaType(rs.getString("sa_type_cd")
						.trim());
				String consumerType = rs.getString("sa_type_cd");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setConsumerCategory(rs.getString(
						"cust_cl_cd").trim());
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("avg_read"));

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
		return meterReplacementDetail;
	}

	/***********************************************************************************************/

	/**
	 * <p>
	 * This method is used to get the ConsumerStatus from the cons_pre_bill_stat_lookup table
	 * </p>
	 * 
	 * @return
	 */
	public ConsumerStatusLookup getConsumerStatus() {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConsumerStatusLookup useConsumerStatusLookup = null;

		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.getConsumerStatusfromDB());
			// ps.setString(1, consumerDetail.getBillRound());
			// ps.setString(2, consumerDetail.getKno());
			rs = ps.executeQuery();
			ArrayList<ConsumerStatusLookup> consumerList = new ArrayList<ConsumerStatusLookup>();

			while (rs.next()) {
				ConsumerStatusLookup consumerStatusLookup = null;

				if (rs.getInt("cons_pre_bill_stat_id") == 10)
					consumerStatusLookup = new ConsumerStatusLookup(10,
							"Regular");
				if (rs.getInt("cons_pre_bill_stat_id") == 60)
					consumerStatusLookup = new ConsumerStatusLookup(60,
							"Regular");
				/*
				 * if(rs.getInt("cons_pre_bill_stat_id")==110)
				 * consumerStatusLookup= new ConsumerStatusLookup( 110,
				 * "Bill Generated & Frozen");
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==50)
				 * consumerStatusLookup=ConsumerStatusLookup.CAT_CHANGE;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==130)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.DATA_PROCESSED_SUCCESSFULLY;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==20)
				 * consumerStatusLookup=ConsumerStatusLookup.DISCONNECTED;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==0)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.ERROR_IN_PHYSICAL_MRD;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==80)
				 * consumerStatusLookup=ConsumerStatusLookup.HI_LOW_ERROR;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==10||
				 * rs.getInt("cons_pre_bill_stat_id")==60)
				 * consumerStatusLookup=new ConsumerStatusLookup(10,"Regular");
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==30)
				 * consumerStatusLookup=ConsumerStatusLookup.METER_INSTALLED;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==90)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.METER_NOT_INSTALLED;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==100)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.METER_READ_PROCESSED;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==120)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.METER_READER_REMARK_UPDATED;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==65)
				 * consumerStatusLookup
				 * =ConsumerStatusLookup.METER_REPLACEMENT_RECORD_FROZEN;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==70)
				 * consumerStatusLookup=ConsumerStatusLookup.RECORD_FROZEN;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==40)
				 * consumerStatusLookup=ConsumerStatusLookup.REOPENING;
				 * 
				 * else if(rs.getInt("cons_pre_bill_stat_id")==15)
				 * consumerStatusLookup=ConsumerStatusLookup.INVALID_DATA;
				 */
				else
					consumerStatusLookup = new ConsumerStatusLookup(rs
							.getInt("cons_pre_bill_stat_id"), rs
							.getString("descr"));

				if (consumerStatusLookup != null) {
					consumerList.add(consumerStatusLookup);
				}

			}

			if (consumerList.size() > 0) {

				useConsumerStatusLookup = new ConsumerStatusLookup();
				useConsumerStatusLookup
						.setConsumerStatusLookupList(consumerList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			AppLog.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
				AppLog.error(e);
			}
		}
		return useConsumerStatusLookup;
	}

	/**
	 * <p>
	 * This method is used to get Count Of MRD Schedule Job List In Progress
	 * </p>
	 * 
	 * @param inputMap
	 * @return
	 */
	public int getCountOfMRDScheduleJobListInProgress() {
		AppLog.begin();
		// System.out.println("getCountOfMRDScheduleJobListInProgress");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int jobInProgressInt = 0;
		try {
			String jobInProgress = "0";
			StringBuffer querySB = new StringBuffer();
			querySB
					.append(" select count(*)job_in_progress from MRD_SCHEDUL_DNLD_LOG t where t.status='In Progress'");

			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				jobInProgress = rs.getString("job_in_progress");
			}
			jobInProgressInt = Integer.parseInt(jobInProgress);
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
		return jobInProgressInt;
	}

	/**
	 * <p>
	 * This method is used to get Last Ok read status code 
	 * </p>
	 * 
	 * @param lastOKReadStatus
	 * @return
	 */
	public String getLastOKReadStatusCode(String lastOKReadStatus) {
		AppLog.begin();
		// System.out.println("lastOKReadStatus::" + lastOKReadStatus);
		String lastOKReadStatusCode = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT T.LSTTS_ID FROM CM_DE_MTR_STATUS_LOOKUP T WHERE T.LSTTS_CODE='"
						+ lastOKReadStatus + "'");

		try {
			// System.out.println("Qury::" + querySB.toString());
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(querySB.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				lastOKReadStatusCode = rs.getString("LSTTS_ID");
			}
		} catch (SQLException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (IOException e) {
			AppLog.error(e);
			// e.printStackTrace();
		} catch (Exception e) {
			AppLog.error(e);
			// e.printStackTrace();
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
		return lastOKReadStatusCode;
	}

	/**
	 * <p>
	 * Get Meter Replacement Details of a Consumer By KNO and Bill Round Id.
	 * <p>
	 * 
	 * @param kno
	 * @param billRound
	 * @return Meter Replacement Detail for a particular kno and bill round
	 */
	public MeterReplacementDetail getMeterReplacementDetails(String kno,
			String billRound) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MeterReplacementDetail meterReplacementDetail = null;
		try {
			String query = QueryContants.getMeterReplacementDetailsQuery();
			// AppLog.info("getConsumerDetailsForMRByKNO query::" + query);
			// System.out.println("getMeterReplacementDetails query::" + query
			// + "\nkno::" + kno + "::billRound::" + billRound);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, kno);
			ps.setString(2, billRound);
			rs = ps.executeQuery();
			while (rs.next()) {
				meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setMeterReaderName(rs
						.getString("MTR_READER_ID"));
				meterReplacementDetail.setKno(rs.getString("ACCT_ID"));
				/** Start:19-03-2013 Matiur Rahman commented */
				// meterReplacementDetail.setZone(rs.getString("SUBZONE_NAME")
				// .trim()
				// + "-(" + rs.getString("ZONECD").trim() + ")");
				/** End:19-03-2013 Matiur Rahman commented */
				/** Start:19-03-2013 Matiur Rahman added */
				meterReplacementDetail.setZone(rs.getString("ZONECD").trim());
				/** End:19-03-2013 Matiur Rahman added */
				meterReplacementDetail.setMrNo(rs.getString("MRNO"));
				meterReplacementDetail.setArea(rs.getString("AREANO"));
				meterReplacementDetail.setWcNo(rs.getString("WCNO"));
				meterReplacementDetail.setName(rs.getString("CONSUMER_NAME")
						.trim());
				meterReplacementDetail.setWaterConnectionUse((null == rs
						.getString("WCONUSE") || "".equalsIgnoreCase(rs
						.getString("WCONUSE").trim())) ? "" : rs.getString(
						"WCONUSE").trim());
				meterReplacementDetail.setWaterConnectionSize((null == rs
						.getString("WCONSIZE") || "".equalsIgnoreCase(rs
						.getString("WCONSIZE").trim())) ? "15" : rs.getString(
						"WCONSIZE").trim());
				meterReplacementDetail.setMeterType((null == rs
						.getString("MTRTYP") ? "" : rs.getString("MTRTYP")
						.trim()));
				meterReplacementDetail.setBadgeNo(null == rs
						.getString("BADGE_NBR") ? "NA" : rs.getString(
						"BADGE_NBR").trim());
				meterReplacementDetail
						.setManufacturer(null == rs.getString("MFG_CD")
								|| "NA".equalsIgnoreCase(rs.getString("MFG_CD")
										.trim()) ? "" : rs.getString("MFG_CD")
								.trim());
				meterReplacementDetail.setModel(null == rs
						.getString("MODEL_CD") ? "" : rs.getString("MODEL_CD")
						.trim());
				meterReplacementDetail.setSaType(null == rs
						.getString("SA_TYPE_CD") ? "" : rs.getString(
						"SA_TYPE_CD").trim());
				meterReplacementDetail.setOldSAType(null == rs
						.getString("OLD_SA_TYPE_CD") ? "" : rs.getString(
						"OLD_SA_TYPE_CD").trim());
				String consumerType = rs.getString("OLD_SA_TYPE_CD");
				if (null != consumerType
						&& ("SAWATSEW".equalsIgnoreCase(consumerType.trim()) || "SAWATR"
								.equalsIgnoreCase(consumerType.trim()))) {
					consumerType = "METERED";
				} else {
					consumerType = "UNMETERED";
				}
				meterReplacementDetail.setConsumerType(consumerType);
				meterReplacementDetail.setConsumerCategory(null == rs
						.getString("CUST_CL_CD") ? "" : rs.getString(
						"CUST_CL_CD").trim());
				meterReplacementDetail.setMeterInstalDate(null == rs
						.getString("MTR_INSTALL_DATE") ? "" : rs.getString(
						"MTR_INSTALL_DATE").trim());
				meterReplacementDetail.setZeroRead(rs
						.getDouble("MTR_START_READ"));
				meterReplacementDetail.setCurrentMeterReadDate(null == rs
						.getString("CURR_MTR_RD_DATE") ? "" : rs.getString(
						"CURR_MTR_RD_DATE").trim());
				meterReplacementDetail.setCurrentMeterRegisterRead(rs
						.getDouble("CURR_REG_READING"));
				meterReplacementDetail.setCurrentAverageConsumption(null == rs
						.getString("AVG_READ") ? "" : rs.getString("AVG_READ")
						.trim());
				meterReplacementDetail.setNewAverageConsumption(rs
						.getDouble("NEW_AVG_READ"));
				meterReplacementDetail.setCurrentMeterReadRemarkCode(null == rs
						.getString("CURR_READER_REM_CD") ? "" : rs.getString(
						"CURR_READER_REM_CD").trim());
				// meterReplacementDetail.setOldMeterReadRemarkCode(rs
				// .getString("OLD_READER_REM_CD"));
				meterReplacementDetail.setOldMeterRegisterRead(rs
						.getDouble("OLD_MTR_READ"));
				if (null == meterReplacementDetail.getOldMeterReadRemarkCode()
						|| "".equalsIgnoreCase(meterReplacementDetail
								.getOldMeterReadRemarkCode())) {
					if (meterReplacementDetail.getOldMeterRegisterRead() > 0) {
						meterReplacementDetail.setOldMeterReadRemarkCode("OK");
					} else {
						meterReplacementDetail.setOldMeterReadRemarkCode("");
					}
				}
				meterReplacementDetail.setOldMeterAverageRead(rs
						.getDouble("OLDAVGCONS"));
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
		return meterReplacementDetail;
	}

	/**
	 * <p>
	 * This method is used to get MRD
	 * </p>
	 * 
	 * @param mrdContainer
	 * @return MRDContainer Search MRD according to the Zone Mr Code and area
	 *         provided
	 * @Modified By Sanjay :17-11-2016 as per Jtrac DJB-4583 and open project
	 *           id-1595.
	 */

	public MRDContainer getMRD(MRDContainer mrdContainer) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// HashMap returnMap = new HashMap();
		String billRoundId = null;
		String mrKey = null;
		try {
			// StringBuffer sqlSlctCmd = new StringBuffer();
			// sqlSlctCmd
			// .append("SELECT NVL(TRIM(x.SVCCYCRTESEQ),'9999999') SEQ,");
			// sqlSlctCmd.append("x.acctid, ");
			// sqlSlctCmd.append("x.conname, ");
			// sqlSlctCmd.append("x.wcno, ");
			// sqlSlctCmd
			// .append("x.custclcd                                                 CAT, ");
			// sqlSlctCmd.append("Trim(x.satypecd) satypecd,x.SVCCYCRTESEQ, ");//
			// added
			// // TRIM
			// // to
			// // satypecd
			// // as
			// // some
			// // satypecd
			// // has
			// // space
			// // @
			// // database
			// sqlSlctCmd
			// .append("Nvl(Trim(To_char(x.lastbillgendate, 'dd-MON-yyyy')), 'NA') ");
			// sqlSlctCmd.append("LASTBILLGENDATE, ");
			// sqlSlctCmd.append("x.BILLROUND_ID, x.MRKEY,");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.avgread), 'NA')                                 AVGREAD, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(To_char(x.obj.readdate, 'dd/mm/yyyy')), 'NA')    PREVMTRRDDT, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj.registeredread), 'NA')                      RGMTRRD, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj.remark), 'NA')                              MTRRDSTAT, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj.readtype), 'NA')                            ReadType, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(To_char(x.obj1.readdate, 'dd/mm/yyyy')), 'NA')   ACTLMTRRDDT, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj1.registeredread), 'NA')                     ACTLRGMTRRD, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj1.remark), 'NA')                             ACTLMTRRDSTAT, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.obj1.readtype), 'NA')                           ACTLReadType, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.billgendate), ' ')                              CURR_BILLGENDATE, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.READERREMCD), ' ')                              CURR_READERREMCD, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.regread), ' ')                                  CURR_REGREAD, ");
			// sqlSlctCmd
			// .append("Nvl(Trim(x.PREBILLSTATID), ' ')                                  CURR_PREBILLSTATID, ");
			//
			// sqlSlctCmd
			// .append("Nvl(Trim(x.newavgread), ' ')                               CURR_NEWAVGREAD, ");
			// sqlSlctCmd.append("Nvl(x.PNFLOOR, 'NA') PNFLOOR, ");
			// sqlSlctCmd.append("Nvl(x.NWFLOOR, ' ') NWFLOOR, ");
			// // Added for getting remarks of invalid data
			// sqlSlctCmd.append("Nvl(x.remarks, ' ') remarks ");
			// sqlSlctCmd.append("FROM ( ");
			// sqlSlctCmd
			// .append("SELECT cm_cons_pre_bill_proc.acct_id                                ACCTID, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.bill_round_id                          BILLROUND_ID, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.avg_read                               AVGREAD, ");
			// sqlSlctCmd
			// .append("       to_char(cm_cons_pre_bill_proc.bill_gen_date,'dd/mm/yyyy')                          BILLGENDATE, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.consumer_name                          CONNAME, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.cons_pre_bill_stat_id ");
			// sqlSlctCmd.append("       PREBILLSTATID, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.cust_cl_cd                             CUSTCLCD, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.last_bill_gen_date ");
			// sqlSlctCmd.append("       LASTBILLGENDATE, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.last_updt_by                           LASTUPDTBY, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.last_updt_dttm                         LASTUPDTTM, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.mrkey                                  MRKEY, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.mtr_reader_name                        MTRRDRNAME, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.new_avg_read                           NEWAVGREAD, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.reader_rem_cd                          READERREMCD, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.read_type_flg                          READTYPFLG, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.reg_reading                            REGREAD, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.sa_type_cd                             SATYPECD, ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.svc_cyc_rte_seq                        SVCCYCRTESEQ ");
			// sqlSlctCmd.append("       , ");
			// sqlSlctCmd
			// .append("       cm_cons_pre_bill_proc.wcno                                   WCNO, ");
			// sqlSlctCmd
			// .append("     cm_cons_pre_bill_proc.prev_no_of_floors  PNFLOOR, ");
			// sqlSlctCmd
			// .append("     cm_cons_pre_bill_proc.new_no_of_floors  NWFLOOR, ");
			// // Added for getting remarks of invalid data
			// sqlSlctCmd.append(" cm_cons_pre_bill_proc.remarks  remarks, ");
			// sqlSlctCmd
			// .append("       Cm_get_prev_meter_read(cm_cons_pre_bill_proc.acct_id)        obj, ");
			// sqlSlctCmd
			// .append("       Cm_get_last_actual_meter_read(cm_cons_pre_bill_proc.acct_id) obj1 ");
			// sqlSlctCmd.append("FROM   djb_zn_mr_ar_mrd ");
			// sqlSlctCmd.append("       inner join cm_mrd_processing_stat ");
			// sqlSlctCmd
			// .append("               ON cm_mrd_processing_stat.mrkey = djb_zn_mr_ar_mrd.mrd_cd ");
			// sqlSlctCmd.append("       inner join cm_bill_window ");
			// sqlSlctCmd
			// .append("               ON cm_bill_window.bill_round_id = ");
			// sqlSlctCmd
			// .append("                  cm_mrd_processing_stat.bill_round_id ");
			// sqlSlctCmd.append("       inner join cm_cons_pre_bill_proc ");
			// sqlSlctCmd
			// .append("               ON cm_cons_pre_bill_proc.mrkey = cm_mrd_processing_stat.mrkey and cm_cons_pre_bill_proc.bill_round_id = cm_bill_window.bill_round_id ");
			// sqlSlctCmd.append(" WHERE  djb_zn_mr_ar_mrd.subzone_cd = ? ");
			// sqlSlctCmd.append(" AND djb_zn_mr_ar_mrd.mr_cd = ? ");
			// sqlSlctCmd.append(" AND djb_zn_mr_ar_mrd.area_cd = ? ");
			// // Added Bill Round ID on 02-11-2012 By Matiur Rahman
			// sqlSlctCmd.append(" AND cm_bill_window.bill_round_id = ? ");
			// sqlSlctCmd.append(" AND cm_mrd_processing_stat.mrd_stats_id = '"
			// + MRDStatusLookup.OPEN.getStatusCode() + "'");
			// sqlSlctCmd.append(" AND cm_bill_window.bill_win_stat_id = '"
			// + BillWindowStatusLookup.OPEN.getStatusCode() + "'");
			// sqlSlctCmd
			// .append(" AND cm_cons_pre_bill_proc.cons_pre_bill_stat_id <> '"
			// + ConsumerStatusLookup.RECORD_FROZEN
			// .getStatusCode() + "'");
			// sqlSlctCmd
			// .append(" AND cm_cons_pre_bill_proc.cons_pre_bill_stat_id <> '"
			// + ConsumerStatusLookup.DATA_PROCESSED_SUCCESSFULLY
			// .getStatusCode() + "'");
			// sqlSlctCmd.append(" )x order by to_number(seq)");
			// AppLog.info("Query>>>>>aZoneCd>>>" + mrdContainer.getZone()
			// + ">>aMRNo>>" + mrdContainer.getMrNo() + ">>aAreaNo>>"
			// + mrdContainer.getAreaNo() + ">>>" + sqlSlctCmd.toString());
			ArrayList<ConsumerDetail> consumerList = new ArrayList<ConsumerDetail>();
			ConsumerDetail consumerDetail = null;
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.mrdDataEntrySearchQuery());
			ps.setString(1, mrdContainer.getZone());
			ps.setString(2, mrdContainer.getMrNo());
			ps.setString(3, mrdContainer.getAreaNo());
			ps.setString(4, mrdContainer.getBillRoundId());
			rs = ps.executeQuery();
			int seqNo = 0;
			while (rs.next()) {
				consumerDetail = new ConsumerDetail();
				billRoundId = rs.getString("BILLROUND_ID");
				mrKey = rs.getString("MRKEY");
				consumerDetail.setSeqNo(seqNo);
				consumerDetail.setServiceCycleRouteSeq(rs
						.getString("SVCCYCRTESEQ"));
				consumerDetail.setKno(rs.getString("ACCTID"));
				consumerDetail.setBillRound(rs.getString("BILLROUND_ID"));
				String consumerName = rs.getString("CONNAME");
				if (null != consumerName && consumerName.indexOf('\'') > -1) {
					consumerName = consumerName.replaceAll("\'", " ");
				}
				consumerDetail.setName(consumerName);
				consumerDetail.setWcNo(rs.getString("WCNO"));
				consumerDetail.setCat(rs.getString("CAT"));
				String SATYPECD = rs.getString("SATYPECD");
				if ("SAWATSEW".equalsIgnoreCase(SATYPECD)
						|| "SAWATER".equalsIgnoreCase(SATYPECD)
						|| "SAWATR".equalsIgnoreCase(SATYPECD)) {
					SATYPECD = "METERED";
				} else {
					SATYPECD = "UNMETERED";
				}
				consumerDetail.setServiceType(SATYPECD);
				consumerDetail.setLastBillGenerationDate(rs
						.getString("LASTBILLGENDATE"));
				String cs = rs.getString("SATYPECD");
				if (null != cs && !"".equals(cs)) {
					consumerDetail.setConsumerStatusLookup(ConsumerStatusLookup
							.getConsumerStatusForStatCd(60));
				}
				consumerDetail.setConsumerStatus(rs
						.getString("CURR_PREBILLSTATID"));
				consumerDetail
						.setCurrentAvgConsumption(rs.getString("AVGREAD"));
				MeterReadDetails prevMeterReadDetails = new MeterReadDetails();
				prevMeterReadDetails.setMeterReadDate(rs
						.getString("PREVMTRRDDT"));
				prevMeterReadDetails.setMeterStatus(rs.getString("MTRRDSTAT"));
				prevMeterReadDetails.setRegRead(rs.getString("RGMTRRD"));
				prevMeterReadDetails.setReadType(rs.getString("ReadType"));
				prevMeterReadDetails.setNoOfFloor(rs.getString("PNFLOOR"));
				MeterReadDetails actualMeterReadDetails = new MeterReadDetails();
				actualMeterReadDetails.setMeterReadDate(rs
						.getString("ACTLMTRRDDT"));
				actualMeterReadDetails.setMeterStatus(rs
						.getString("ACTLMTRRDSTAT"));
				actualMeterReadDetails.setRegRead(rs.getString("ACTLRGMTRRD"));
				actualMeterReadDetails
						.setReadType(rs.getString("ACTLReadType"));
				MeterReadDetails curMeterReadDetails = new MeterReadDetails();
				curMeterReadDetails.setMeterReadDate(rs
						.getString("CURR_BILLGENDATE"));
				curMeterReadDetails.setMeterStatus(rs
						.getString("CURR_READERREMCD"));
				curMeterReadDetails.setRegRead(rs.getString("CURR_REGREAD"));
				curMeterReadDetails.setAvgRead(rs.getString("CURR_NEWAVGREAD"));
				curMeterReadDetails.setNoOfFloor(rs.getString("NWFLOOR"));
				/*
				 * Start : Sanjay on 17-11-2016 added the below line to set Hi
				 * Low Flag Value, Open Project Id: 1595, DJB-4583
				 */
				consumerDetail.setHiLowFlag(rs.getString("HILWFLG"));
				AppLog.info("Value of High Low---------->>>>"
						+ rs.getString("HILWFLG"));
				/*
				 * End : Sanjay on 17-11-2016 added the below line to set Hi Low
				 * Flag Value, Open Project Id: 1595, DJB-4583
				 */
				// Added for getting remarks of invalid data
				/*
				 * Start : Atanu on 27-06-2016 commented the below line to
				 * replace new line char from remarks, Open Project Id: 1202,
				 * DJB-4464
				 */
				// curMeterReadDetails.setRemarks(rs.getString("remarks"));
				if (null != rs.getString("remarks")
						&& !"".equalsIgnoreCase(rs.getString("remarks").trim())) {
					if (rs.getString("remarks").contains("\n")) {
						curMeterReadDetails.setRemarks(rs.getString("remarks")
								.replace("\n", " "));
					} else {
						curMeterReadDetails.setRemarks(rs.getString("remarks"));
					}
				} else {
					curMeterReadDetails.setRemarks("");
				}
				/*
				 * End : Atanu on 27-06-2016 commented the below line to replace
				 * new line char from remarks, Open Project Id: 1202, DJB-4464
				 */
				// currentMtrReadDtails.setMeterReadDate(rs.getDate(columnIndex))
				// consumer.setCurrentMeterRead(rs.getInt("regRead"));

				consumerDetail.setPrevMeterRead(prevMeterReadDetails);
				consumerDetail.setPrevActualMeterRead(actualMeterReadDetails);
				consumerDetail.setCurrentMeterRead(curMeterReadDetails);
				consumerList.add(consumerDetail);
				seqNo++;
			}
			mrdContainer.setConsumerList(consumerList);
			mrdContainer.setMrKey(mrKey);
			mrdContainer.setBillRoundId(billRoundId);

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
		return mrdContainer;
	}

	/**
	 * <p>
	 * This overloaded method is used AJAX call response based on acctIDAliasKNO
	 * where consumer prebill proc status is less than 65
	 * </p>
	 * 
	 * @param mrdContainer
	 * @param acctIDAliasKNO
	 * @param UserId
	 * @return
	 */
	/*
	 * Modified : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604 :-
	 * Users to be restricted as per access groups in Single Consumer Bill
	 * Generation screen.
	 */
	public MRDContainer getMRD(MRDContainer mrdContainer,
			String acctIDAliasKNO, String UserId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String billRoundId = null;
		String mrKey = null;
		try {

			ArrayList<ConsumerDetail> consumerList = new ArrayList<ConsumerDetail>();
			ConsumerDetail consumerDetail = null;
			conn = DBConnector.getConnection();
			// AppLog.info("Query String BEFORE::::::::::::::::::::::"+QueryContants.queryToGetConsumerDetlBasedonKNO());
			// System.out.println("Query String BEFORE::::::::::::::::::::::"+QueryContants.queryToGetConsumerDetlBasedonKNO());
			// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
			String query = QueryContants.queryToGetConsumerDetlBasedonKNO();
			ps = conn.prepareStatement(query);
			AppLog.info("query for getMrd >>>>" + query);
			int i = 0;
			ps.setString(++i, UserId);
			AppLog.info("UserId for getMrd >>" + UserId);
			ps.setString(++i, acctIDAliasKNO);
			AppLog.info("Account Id for getMRD >>" + acctIDAliasKNO);
			// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
			// AppLog.info("Query String AFTER::::::::::::::::::::::"+ps.toString());

			rs = ps.executeQuery();
			int seqNo = 0;
			while (rs.next()) {
				consumerDetail = new ConsumerDetail();
				billRoundId = rs.getString("BILLROUND_ID");
				mrKey = rs.getString("MRKEY");
				consumerDetail.setSeqNo(seqNo);
				consumerDetail.setServiceCycleRouteSeq(rs
						.getString("SVCCYCRTESEQ"));
				consumerDetail.setKno(rs.getString("ACCTID"));
				consumerDetail.setBillRound(rs.getString("BILLROUND_ID"));

				/*****************************************************************/
				consumerDetail.setZone(rs.getString("SUBZONE_CD"));
				consumerDetail.setArea(rs.getString("AREA_DESC"));

				consumerDetail.setConsPreBillStatID(rs
						.getString("PREBILLSTATID"));

				if (rs.getString("BILL_ID") != null)
					consumerDetail.setBillId(rs.getString("BILL_ID"));
				else
					consumerDetail.setBillId("N/A");

				// consumerDetail.setBillId(rs.getString("BILL_ID"));

				// /System.out.println("ZONE IS:::::::::::::::::"+rs.getString("SUBZONE_CD"));
				// System.out.println("AREA IS:::::::::::::::::"+rs.getString("AREA_DESC"));

				// System.out.println("PREBILLSTATID IS:::::::::::::::::"+rs.getInt("PREBILLSTATID"));

				// System.out.println("BILLID IS:::::::::::::::::"+rs.getInt("BILL_ID"));

				/******************************************************************/

				String consumerName = rs.getString("CONNAME");
				if (null != consumerName && consumerName.indexOf('\'') > -1) {
					consumerName = consumerName.replaceAll("\'", " ");
				}
				consumerDetail.setName(consumerName);
				consumerDetail.setWcNo(rs.getString("WCNO"));
				consumerDetail.setCat(rs.getString("CAT"));
				String SATYPECD = rs.getString("SATYPECD");
				if ("SAWATSEW".equalsIgnoreCase(SATYPECD)
						|| "SAWATER".equalsIgnoreCase(SATYPECD)
						|| "SAWATR".equalsIgnoreCase(SATYPECD)) {
					SATYPECD = "METERED";
				} else {
					SATYPECD = "UNMETERED";
				}
				consumerDetail.setServiceType(SATYPECD);
				consumerDetail.setLastBillGenerationDate(rs
						.getString("LASTBILLGENDATE"));
				String cs = rs.getString("SATYPECD");
				if (null != cs && !"".equals(cs)) {
					consumerDetail.setConsumerStatusLookup(ConsumerStatusLookup
							.getConsumerStatusForStatCd(60));
				}
				consumerDetail.setConsumerStatus(rs
						.getString("CURR_PREBILLSTATID"));
				consumerDetail
						.setCurrentAvgConsumption(rs.getString("AVGREAD"));
				MeterReadDetails prevMeterReadDetails = new MeterReadDetails();
				prevMeterReadDetails.setMeterReadDate(rs
						.getString("PREVMTRRDDT"));
				prevMeterReadDetails.setMeterStatus(rs.getString("MTRRDSTAT"));
				prevMeterReadDetails.setRegRead(rs.getString("RGMTRRD"));
				prevMeterReadDetails.setReadType(rs.getString("ReadType"));
				prevMeterReadDetails.setNoOfFloor(rs.getString("PNFLOOR"));
				MeterReadDetails actualMeterReadDetails = new MeterReadDetails();
				actualMeterReadDetails.setMeterReadDate(rs
						.getString("ACTLMTRRDDT"));
				actualMeterReadDetails.setMeterStatus(rs
						.getString("ACTLMTRRDSTAT"));
				actualMeterReadDetails.setRegRead(rs.getString("ACTLRGMTRRD"));
				actualMeterReadDetails
						.setReadType(rs.getString("ACTLReadType"));
				MeterReadDetails curMeterReadDetails = new MeterReadDetails();
				curMeterReadDetails.setMeterReadDate(rs
						.getString("CURR_BILLGENDATE"));
				curMeterReadDetails.setMeterStatus(rs
						.getString("CURR_READERREMCD"));
				curMeterReadDetails.setRegRead(rs.getString("CURR_REGREAD"));
				curMeterReadDetails.setAvgRead(rs.getString("CURR_NEWAVGREAD"));
				curMeterReadDetails.setNoOfFloor(rs.getString("NWFLOOR"));
				/*
				 * Start : Atanu on 27-06-2016 commented the below line to
				 * replace new line char from remarks, Open Project Id: 1202,
				 * DJB-4464
				 */
				// curMeterReadDetails.setRemarks(rs.getString("remarks"));
				if (null != rs.getString("remarks")
						&& !"".equalsIgnoreCase(rs.getString("remarks").trim())) {
					if (rs.getString("remarks").contains("\n")) {
						curMeterReadDetails.setRemarks(rs.getString("remarks")
								.replace("\n", " "));
					} else {
						curMeterReadDetails.setRemarks(rs.getString("remarks"));
					}
				} else {
					curMeterReadDetails.setRemarks("");
				}
				/*
				 * End : Atanu on 27-06-2016 commented the below line to replace
				 * new line char from remarks, Open Project Id: 1202, DJB-4464
				 */
				consumerDetail.setPrevMeterRead(prevMeterReadDetails);
				consumerDetail.setPrevActualMeterRead(actualMeterReadDetails);
				consumerDetail.setCurrentMeterRead(curMeterReadDetails);
				consumerList.add(consumerDetail);
				AppLog.info("consumerList >>>**" + consumerList);

				seqNo++;
			}
			mrdContainer.setConsumerList(consumerList);
			mrdContainer.setMrKey(mrKey);
			mrdContainer.setBillRoundId(billRoundId);

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
		return mrdContainer;
	}

	/**
	 * <p>
	 * This overloaded method is used AJAX call response based on acctIDAliasKNO
	 * where consumer prebill proc status is greater than 65
	 * </p>
	 * 
	 * @param mrdContainer
	 * @param acctIDAliasKNO
	 * @param UserId
	 * @return
	 */
	/*
	 * Modified : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604 :-
	 * Users to be restricted as per access groups in Single Consumer Bill
	 * Generation screen.
	 */
	public MRDContainer getMRDOther(MRDContainer mrdContainer,
			String acctIDAliasKNO, String UserId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String billRoundId = null;
		String mrKey = null;
		try {
			ArrayList<ConsumerDetail> consumerList = new ArrayList<ConsumerDetail>();
			ConsumerDetail consumerDetail = null;
			conn = DBConnector.getConnection();
			// AppLog.info("Query String BEFORE::::::::::::::::::::::"+QueryContants.queryToGetConsumerDetlBasedonKNOOther());
			// System.out.println("Query String BEFORE::::::::::::::::::::::"+QueryContants.queryToGetConsumerDetlBasedonKNOOther());
			// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
			String query = QueryContants
					.queryToGetConsumerDetlBasedonKNOOther();
			ps = conn.prepareStatement(query);
			int i = 0;
			AppLog.info("query for getMRDOther >>" + query);
			ps.setString(++i, UserId.trim());
			AppLog.info("UserId for getMrdOther >>" + UserId);
			ps.setString(++i, acctIDAliasKNO);
			AppLog.info("acctIDAliasKNO for getMrdOther >>"
					+ acctIDAliasKNO.trim());
			AppLog.info("Query String AFTER::::::::::::::::::::::"
					+ ps.toString());
			// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
			rs = ps.executeQuery();
			int seqNo = 0;
			while (rs.next()) {
				consumerDetail = new ConsumerDetail();
				billRoundId = rs.getString("BILLROUND_ID");
				mrKey = rs.getString("MRKEY");
				consumerDetail.setSeqNo(seqNo);
				consumerDetail.setServiceCycleRouteSeq(rs
						.getString("SVCCYCRTESEQ"));
				consumerDetail.setKno(rs.getString("ACCTID"));
				consumerDetail.setBillRound(rs.getString("BILLROUND_ID"));

				/*****************************************************************/
				consumerDetail.setZone(rs.getString("SUBZONE_CD"));
				consumerDetail.setArea(rs.getString("AREA_DESC"));
				consumerDetail.setConsPreBillStatID(rs
						.getString("PREBILLSTATID"));

				if (rs.getString("BILL_ID") != null)
					consumerDetail.setBillId(rs.getString("BILL_ID"));
				else
					consumerDetail.setBillId("N/A");

				// System.out.println("ZONE IS:::::::::::::::::"+rs.getString("SUBZONE_CD"));
				// System.out.println("AREA IS:::::::::::::::::"+rs.getString("AREA_DESC"));

				// System.out.println("PREBILLSTATID IS:::::::::::::::::"+rs.getString("PREBILLSTATID"));

				// System.out.println("BILLID IS:::::::::::::::::"+rs.getInt("BILL_ID"));
				/******************************************************************/

				String consumerName = rs.getString("CONNAME");
				if (null != consumerName && consumerName.indexOf('\'') > -1) {
					consumerName = consumerName.replaceAll("\'", " ");
				}
				consumerDetail.setName(consumerName);
				consumerDetail.setWcNo(rs.getString("WCNO"));
				consumerDetail.setCat(rs.getString("CAT"));
				String SATYPECD = rs.getString("SATYPECD");
				if ("SAWATSEW".equalsIgnoreCase(SATYPECD)
						|| "SAWATER".equalsIgnoreCase(SATYPECD)
						|| "SAWATR".equalsIgnoreCase(SATYPECD)) {
					SATYPECD = "METERED";
				} else {
					SATYPECD = "UNMETERED";
				}
				consumerDetail.setServiceType(SATYPECD);
				consumerDetail.setLastBillGenerationDate(rs
						.getString("LASTBILLGENDATE"));
				String cs = rs.getString("SATYPECD");
				if (null != cs && !"".equals(cs)) {
					consumerDetail.setConsumerStatusLookup(ConsumerStatusLookup
							.getConsumerStatusForStatCd(60));
				}
				consumerDetail.setConsumerStatus(rs
						.getString("CURR_PREBILLSTATID"));
				consumerDetail
						.setCurrentAvgConsumption(rs.getString("AVGREAD"));
				MeterReadDetails prevMeterReadDetails = new MeterReadDetails();
				prevMeterReadDetails.setMeterReadDate(rs
						.getString("PREVMTRRDDT"));
				prevMeterReadDetails.setMeterStatus(rs.getString("MTRRDSTAT"));
				prevMeterReadDetails.setRegRead(rs.getString("RGMTRRD"));
				prevMeterReadDetails.setReadType(rs.getString("ReadType"));
				prevMeterReadDetails.setNoOfFloor(rs.getString("PNFLOOR"));
				MeterReadDetails actualMeterReadDetails = new MeterReadDetails();
				actualMeterReadDetails.setMeterReadDate(rs
						.getString("ACTLMTRRDDT"));
				actualMeterReadDetails.setMeterStatus(rs
						.getString("ACTLMTRRDSTAT"));
				actualMeterReadDetails.setRegRead(rs.getString("ACTLRGMTRRD"));
				actualMeterReadDetails
						.setReadType(rs.getString("ACTLReadType"));
				MeterReadDetails curMeterReadDetails = new MeterReadDetails();
				curMeterReadDetails.setMeterReadDate(rs
						.getString("CURR_BILLGENDATE"));
				curMeterReadDetails.setMeterStatus(rs
						.getString("CURR_READERREMCD"));
				curMeterReadDetails.setRegRead(rs.getString("CURR_REGREAD"));
				curMeterReadDetails.setAvgRead(rs.getString("CURR_NEWAVGREAD"));
				curMeterReadDetails.setNoOfFloor(rs.getString("NWFLOOR"));
				/*
				 * Start : Atanu on 27-06-2016 commented the below line to
				 * replace new line char from remarks, Open Project Id: 1202,
				 * DJB-4464
				 */
				// curMeterReadDetails.setRemarks(rs.getString("remarks"));
				if (null != rs.getString("remarks")
						&& !"".equalsIgnoreCase(rs.getString("remarks").trim())) {
					if (rs.getString("remarks").contains("\n")) {
						curMeterReadDetails.setRemarks(rs.getString("remarks")
								.replace("\n", " "));
					} else {
						curMeterReadDetails.setRemarks(rs.getString("remarks"));
					}
				} else {
					curMeterReadDetails.setRemarks("");
				}
				/*
				 * End : Atanu on 27-06-2016 commented the below line to replace
				 * new line char from remarks, Open Project Id: 1202, DJB-4464
				 */
				consumerDetail.setPrevMeterRead(prevMeterReadDetails);
				consumerDetail.setPrevActualMeterRead(actualMeterReadDetails);
				consumerDetail.setCurrentMeterRead(curMeterReadDetails);
				consumerList.add(consumerDetail);
				seqNo++;
			}
			mrdContainer.setConsumerList(consumerList);
			AppLog.info("consumerList >>>*" + consumerList);
			mrdContainer.setMrKey(mrKey);
			mrdContainer.setBillRoundId(billRoundId);

		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (IOException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			// e.printStackTrace();
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
				// e.printStackTrace();
				AppLog.error(e);
			}
		}
		AppLog.end();
		return mrdContainer;
	}

	/**
	 * <p>
	 * This method is used on basis of AJAX call response based on
	 * acctIDAliasKNO get consumer RecordStatusAndErrorDesc
	 * </p>
	 * 
	 * @param consumerDetail
	 * @return
	 */
	public String getRecordStatusAndErrorDesc(ConsumerDetail consumerDetail) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants
					.getRecordStatusAndErrorDescQuery());
			ps.setString(1, consumerDetail.getBillRound());
			ps.setString(2, consumerDetail.getKno());
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("RECSTATS") != null
						&& rs.getString("ERRORDESC") == null) {
					if (rs.getString("billingremarks") != null)
						return "Record Status:" + rs.getString("RECSTATS")
								+ " - Error Descripton is in:"
								+ rs.getString("billingremarks");
					else
						return "Record Status:" + rs.getString("RECSTATS")
								+ " - Error Descripton is in: N/A";
				}
				if (rs.getString("ERRORDESC") != null
						&& rs.getString("RECSTATS") == null) {
					return "Record Status: N/A"
							+ "  - Error Descripton is in: "
							+ rs.getString("ERRORDESC");
				}
				if (rs.getString("RECSTATS") != null
						&& rs.getString("ERRORDESC") != null) {
					return "Record Status:" + rs.getString("RECSTATS")
							+ "   - Error Descripton is in: "
							+ rs.getString("ERRORDESC");
				}
				if (rs.getString("RECSTATS") == null
						&& rs.getString("ERRORDESC") == null) {
					if (rs.getString("billingremarks") != null)
						return "Record Status: N/A"
								+ "     - Error Descripton is in: "
								+ rs.getString("billingremarks");
					else
						return "Record Status: N/A"
								+ "     - Error Descripton is in: N/A";
				}
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (IOException e) {
			// e.printStackTrace();
			AppLog.error(e);
		} catch (Exception e) {
			// e.printStackTrace();
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
				// e.printStackTrace();
				AppLog.error(e);
			}
		}
		AppLog.end();

		return "N/A";

	}

}
