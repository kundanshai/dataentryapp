/************************************************************************************************************
 * @(#) KnoGenerationDAO.java   10 Feb 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.KnoGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO Class for New Connection KNO and Bill Generation Screen as per JTrac
 * DJB-4313.
 * </p>
 * 
 * @author Rajib Hazarika (Tata Consultancy Services)
 * @since 10-FEB-2016
 * @history Sanjay on 17-03-2016 edited to add plot area as per jTrac DJB-4418
 * @history: On 31-MAR-2016 Rajib Hazarika (682667) modifid lines as per JTrac
 *           DJB-4429 for enabling of purpose of Application field
 * @history: On 28-AUG-2016 Madhuri (735689) Added functions
 *           fetchZroLocationTagFile,tagFileInsertDetails as per JTrac DJB-4541
 *           ,Open project-1443.
 * @history: On 28-AUG-2016 LOVELY (986018) EDITED getArnDetails as per JTrac
 *           DJB-4541 ,Open project-1443.
 * 
 */
public class KnoGenerationDAO {
	/**
	 * <p>
	 * This method is used to fetch zro location for tagging online consumer as
	 * per JTrac DJB-4541 ,Open project-1443.
	 * </p>
	 * 
	 * @author MADHURI(Tata Consultancy Services)
	 * @since 27-AUG-2016
	 */
	public static String fetchZroLocationTagFile(String tagFileNumber) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zroLoc = null;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.tagFileZroLocation());
			int i = 0;
			ps.setString(++i, tagFileNumber);
			ps.setString(++i, tagFileNumber);
			rs = ps.executeQuery();
			while (rs.next()) {
				zroLoc = rs.getString("ZRO_CD");
			}
		} catch (SQLException e) {
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
		AppLog.info("File No>>>" + tagFileNumber + ">>zro Location>>" + zroLoc);
		AppLog.end();
		return zroLoc;

	}

	/**
	 * <p>
	 * This method is used to get Mrkey Count for the User Entered Mrkey and ZRO
	 * location for the user as per JTrac DJB-4313
	 * </p>
	 * 
	 * @param mrkey
	 * @param zroCd
	 * @return
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 */
	public static int getMrkeyCount(String mrkey, String zroCd) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		int cnt = 0;
		try {
			String query = QueryContants.getMrkeyCountQuery();
			conn = DBConnector.getConnection(
					DJBConstants.JNDI_DS_NEWCONN_KNO_GENERATION,
					DJBConstants.JNDI_PROVIDER_NEWCONN_KNO_GENERATION);
			AppLog.info("query to get ARN Details >>mrkey>>" + mrkey
					+ ">>zroCd>>" + zroCd + ">>Query>>" + query + ">> i>>" + i);
			ps = conn.prepareStatement(query);
			ps.setString(++i, mrkey);
			ps.setString(++i, zroCd);
			rs = ps.executeQuery();
			while (rs.next()) {
				cnt = rs.getInt("CNT");
				AppLog.info("CNT>>" + rs.getInt("CNT"));
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
		return cnt;

	}

	/**
	 * <p>
	 * This method is used to insert online consumer details in staging table as
	 * per JTrac DJB-4541 ,Open project-1443.
	 * </p>
	 * 
	 * @author MADHURI(Tata Consultancy Services)
	 * @since 27-AUG-2016
	 */
	public static int tagFileInsertDetails(
			KnoGenerationDetails knoGenerationDetails, String userIdSession) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsInserted = 0;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.tagfileInsertDetails());
			int i = 0;
			ps.setString(++i, knoGenerationDetails.getFileNo());
			ps.setString(++i, knoGenerationDetails.getPurposeOfAppl());
			ps.setString(++i, knoGenerationDetails.getConsumerName());
			ps.setString(++i, knoGenerationDetails.getMobNo());
			ps.setString(++i, knoGenerationDetails.getAddress());
			ps.setString(++i, knoGenerationDetails.getArnNo());
			ps.setString(++i, knoGenerationDetails.getStatusCd());
			ps.setString(++i, userIdSession);
			ps.setString(++i, knoGenerationDetails.getZroCd());
			ps.setString(++i, knoGenerationDetails.getPlotArea());
			ps.setString(++i, knoGenerationDetails.getPurposeOfAppl());
			ps.setString(++i, knoGenerationDetails.getOnlineStatus());
			AppLog.info("<FileNo>" + knoGenerationDetails.getFileNo()
					+ "<PurposeOfAppl>"
					+ knoGenerationDetails.getPurposeOfAppl()
					+ "<ConsumerName>" + knoGenerationDetails.getConsumerName()
					+ "<Mob_no>" + knoGenerationDetails.getMobNo()
					+ "<Address>" + knoGenerationDetails.getAddress()
					+ "<Arn_No>" + knoGenerationDetails.getArnNo()
					+ "<StatusCd>" + knoGenerationDetails.getStatusCd()
					+ "<userIdSession>" + userIdSession + "<ZRO_CD>"
					+ knoGenerationDetails.getZroCd() + "<PlotArea>"
					+ knoGenerationDetails.getPlotArea() + "<Online_Status>"
					+ knoGenerationDetails.getOnlineStatus());
			rowsInserted = ps.executeUpdate();
		} catch (SQLException e) {
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
		AppLog.info("File Number Tagging row Insertion>>>>" + rowsInserted);
		AppLog.end();
		return rowsInserted;
	}

	/**
	 * <p>
	 * This method is used to update file Entry Status and BillId after Bill
	 * Generation as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @param status
	 * @param billId
	 * @param updtBy
	 * @return
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 15-FEB-2016
	 */

	public static boolean updateGeneratedBill(String status, String billId,
			String updtBy, String acctId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		try {
			conn = DBConnector.getConnection();
			ps = conn
					.prepareStatement(QueryContants.updateGeneratedBillQuery());
			AppLog.info(">>Query" + QueryContants.updateGeneratedBillQuery());
			int i = 0;
			ps.setString(++i, status);
			ps.setString(++i, billId);
			ps.setString(++i, updtBy);
			ps.setString(++i, updtBy);
			ps.setString(++i, acctId);
			AppLog.info("status>>" + status + ">>billId>>" + billId
					+ ">>updtBy>" + updtBy + ">>acctId>>" + acctId);
			int recordUpdated = ps.executeUpdate();
			AppLog.info("recordUpdated>>" + recordUpdated);
			if (recordUpdated > 0) {
				isSuccess = true;
				AppLog.info("isSuccess>>" + isSuccess);
			}
			AppLog.info("isSuccess>>" + isSuccess);

		} catch (SQLException e) {
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
				AppLog.error(e);
			}
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * This method is used to update file Entry Status after KNO / Bill
	 * Generation as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @param status
	 * @param acctId
	 * @param updtBy
	 * @return
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 15-FEB-2016
	 * @history Sanjay on 17-03-2016 edited to add plot area as per jTrac
	 *          DJB-4418
	 * @history On 31-MAR-2016 Rajib Hazarika (682667) Modified below method to
	 *          update purposeOfAppl as per JTrac DJB-4429
	 */

	public static boolean updateGeneratedKno(String status, String acctId,
			String updtBy, String fileNo, String arnNo, String plotArea,
			String plotAreaOld, String purposeOfAppl, String purposeOfApplOld) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		try {
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(QueryContants.updateGeneratedKnoQuery());
			int i = 0;
			ps.setString(++i, status);
			ps.setString(++i, acctId);
			ps.setString(++i, updtBy);
			ps.setString(++i, updtBy);
			/*
			 * Start : Edited by Sanjay Kumar Das (1033846) as per JTrac
			 * DJB-4408
			 */
			ps.setString(++i, plotArea);
			ps.setString(++i, plotAreaOld);
			// START: On 31-MAR-2016 Rajib Hazarika (682667) Modified below
			// method to update purposeOfAppl as per JTrac DJB-
			ps.setString(++i, purposeOfAppl);
			ps.setString(++i, purposeOfApplOld);
			// END: On 31-MAR-2016 Rajib Hazarika (682667) Modified below method
			// to update purposeOfAppl as per JTrac DJB-
			/* End : Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 */
			ps.setString(++i, fileNo);
			ps.setString(++i, arnNo);
			int recordUpdated = ps.executeUpdate();
			if (recordUpdated > 0) {
				isSuccess = true;
			}
		} catch (SQLException e) {
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
				AppLog.error(e);
			}
		}
		AppLog.end();
		return isSuccess;
	}

	/**
	 * <p>
	 * This method is used to get ARN details for the Enetered ARN No. or File
	 * No. as per JTrac DJB-4313
	 * </p>
	 * 
	 * @param arnNo
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 10-FEB-2016
	 * @return
	 */
	public KnoGenerationDetails getArnDetails(String arnNo, String fileNo) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		KnoGenerationDetails knoGenerationDetails = null;
		try {
			String query = QueryContants.getArnDetailsQuery();
			conn = DBConnector.getConnection(
					DJBConstants.JNDI_DS_NEWCONN_KNO_GENERATION,
					DJBConstants.JNDI_PROVIDER_NEWCONN_KNO_GENERATION);
			AppLog.info("query to get ARN Details >>arnNo>>" + arnNo
					+ ">>Query>>" + query + ">> i>>" + i);
			ps = conn.prepareStatement(query);
			ps.setString(++i, arnNo);
			ps.setString(++i, fileNo);
			AppLog.info("arnNo>>" + arnNo + ">>fileNo" + fileNo);
			rs = ps.executeQuery();
			knoGenerationDetails = new KnoGenerationDetails();
			while (rs.next()) {
				knoGenerationDetails.setArnNo(rs.getString("ARN"));
				AppLog.info("ARN>>" + rs.getString("ARN"));

				knoGenerationDetails.setFileNo(rs.getString("FILE_NO"));
				AppLog.info("FILE_NO>>" + rs.getString("FILE_NO"));

				knoGenerationDetails.setAcctId(rs.getString("ACCT_ID"));
				AppLog.info("ACCT_ID>>" + rs.getString("ACCT_ID"));

				knoGenerationDetails.setAcctGenBy(rs.getString("ACCT_GEN_BY"));
				AppLog.info("ACCT_GEN_BY>>" + rs.getString("ACCT_GEN_BY"));

				knoGenerationDetails.setBillId(rs.getString("BILL_ID"));
				AppLog.info("BILL_ID>>" + rs.getString("BILL_ID"));

				knoGenerationDetails.setBillGenBy(rs.getString("BILL_GEN_BY"));
				AppLog.info("BILL_GEN_BY>>" + rs.getString("BILL_GEN_BY"));

				knoGenerationDetails.setDtOfAppl(rs.getString("DT_OF_APPL"));
				AppLog.info("DT_OF_APPL>>" + rs.getString("DT_OF_APPL"));

				knoGenerationDetails.setConsumerName(rs
						.getString("CONSUMER_NAME"));
				AppLog.info("CONSUMER_NAME>>" + rs.getString("CONSUMER_NAME"));

				knoGenerationDetails.setMobNo(rs.getString("MOB_NO"));
				AppLog.info("MOB_NO>>" + rs.getString("MOB_NO"));

				knoGenerationDetails.setAddress(rs.getString("ADDRESS"));
				AppLog.info("ADDRESS>>" + rs.getString("ADDRESS"));

				knoGenerationDetails.setPurposeOfAppl(rs
						.getString("APP_PURPOSE"));
				AppLog.info("APP_PURPOSE>>" + rs.getString("APP_PURPOSE"));

				knoGenerationDetails.setZroCd(rs.getString("ZRO_CD"));
				AppLog.info("ZRO_CD>>" + rs.getString("ZRO_CD"));

				knoGenerationDetails.setStatusCd(rs.getString("ARN_STATUS_CD"));
				AppLog.info("ARN_STATUS_CD>>" + rs.getString("ARN_STATUS_CD"));

				knoGenerationDetails.setIsWatUsedForPrem(rs
						.getString("IS_WAT_FOR_CIVIL_CONST"));
				AppLog.info("IS_WAT_FOR_CIVIL_CONST>>"
						+ rs.getString("IS_WAT_FOR_CIVIL_CONST"));

				knoGenerationDetails.setIsUnAuthorisedUsage(rs
						.getString("IS_UN_AUTH_USG_IN_PREM"));
				AppLog.info("IS_UN_AUTH_USG_IN_PREM>>"
						+ rs.getString("IS_UN_AUTH_USG_IN_PREM"));

				knoGenerationDetails.setPrefModeOfPayment(rs
						.getString("PAYMENT_MODE"));
				AppLog.info("PAYMENT_MODE>>" + rs.getString("PAYMENT_MODE"));

				knoGenerationDetails.setDjbEmpRbtAppl(rs
						.getString("IS_DJB_EMP"));
				AppLog.info("IS_DJB_EMP>>" + rs.getString("IS_DJB_EMP"));

				knoGenerationDetails.setEmpId(rs.getString("DJB_EMP_ID"));
				AppLog.info("DJB_EMP_ID>>" + rs.getString("DJB_EMP_ID"));

				knoGenerationDetails.setDtOfRet(rs.getString("DT_OF_RET"));
				AppLog.info("DT_OF_RET>>" + rs.getString("DT_OF_RET"));

				knoGenerationDetails.setPerId(rs.getString("PER_ID"));
				AppLog.info("PER_ID>>" + rs.getString("PER_ID"));
				/*
				 * Start : Edited by Sanjay Kumar Das (1033846) as per JTrac
				 * DJB-4408
				 */
				knoGenerationDetails.setPlotArea(rs.getString("PLOT_AREA"));
				AppLog.info("PLOT_AREA>>" + rs.getString("PLOT_AREA"));
				/*
				 * End : Edited by Sanjay Kumar Das (1033846) as per JTrac
				 * DJB-4408
				 */
				/*
				 * Start : Edited by Lovely(986018) as per JTrac DJB-4541 ,Open
				 * project-1443
				 */
				knoGenerationDetails.setOnlineStatus(rs
						.getString("ONLINE_STATUS_CD"));
				AppLog.info("ONLINE_STATUS_CD>>"
						+ rs.getString("ONLINE_STATUS_CD"));
				/*
				 * End : Edited by Lovely(986018) as per JTrac DJB-4541 ,Open
				 * project-1443
				 */
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
		// AppLog.info("PLOTAREA>>" + knoGenerationDetails.getPlotArea());
		AppLog.end();
		return knoGenerationDetails;
	}
}
