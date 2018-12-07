/************************************************************************************************************
 * @(#) MeterReadDAO.java
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

//import com.tcs.djb.rms.constant.DJBQueryConstants;
import com.tcs.djb.model.MeterReadType;
import com.tcs.djb.model.MeterReadTypeLookUp;
///import com.tcs.djb.rms.util.AppLog;
//import com.tcs.djb.rms.util.DBConnector;
import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.MeterReadUploadDetails;
import com.tcs.djb.model.PreviousMeterReadDetails;
import com.tcs.djb.rms.model.BillGenerationDetails;
import com.tcs.djb.util.DBConnector;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DataEntryAppConstants;

/**
 * <p>
 * DAO Class for Meter Read process.
 * </p>
 * 
 */
public class MeterReadDAO 
{
	
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
	 * This method is to retrieve all the details from the database of the
	 * consumer for those meter read to be updated to validate the reading
	 * details sent by HHD.
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
	 */
	public static List<ConsumerDetail> getConsumerDetailByAccountIDAndBillRound(
			ArrayList<MeterReadUploadDetails> toUploadMeterReadDetailsList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// String billRoundId = "";
		String billRoundId = "' '";
		// String accountIds = "";
		String accountIds = "' '";
		MeterReadUploadDetails meterReadUploadDetails = null;
		List<ConsumerDetail> consumerDetailList = new ArrayList<ConsumerDetail>();
		ConsumerDetail consumerDetail = null;
		PreviousMeterReadDetails previousMeterReadDetails = null;
		try {
			if (null != toUploadMeterReadDetailsList
					&& toUploadMeterReadDetailsList.size() > 0) {
				for (int i = 0; i < toUploadMeterReadDetailsList.size(); i++) {
					meterReadUploadDetails = (MeterReadUploadDetails) toUploadMeterReadDetailsList
							.get(i);
					if (null != meterReadUploadDetails.getKno()
							&& !"".equals(meterReadUploadDetails.getKno()
									.trim())) {
						// billRoundId = meterReadUploadDetails.getBillRound();
						billRoundId = billRoundId + ",'"
								+ meterReadUploadDetails.getBillRound() + "'";
						// accountIds = accountIds + "'"
						// + meterReadUploadDetails.getKno() + "',";
						accountIds = accountIds + ",'"
								+ meterReadUploadDetails.getKno() + "'";
					}
				}
				// accountIds = accountIds + "''";
			}
			// System.out.println("accountIds::" + accountIds + "billRoundId::"
			// + billRoundId);
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getConsumerDetailByAccountIDAndBillRoundQuery(billRoundId,
							accountIds);
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
				if (null != consumerType) {
					if ("SAWATSEW".equalsIgnoreCase(consumerType.trim())
							|| "SAWATR".equalsIgnoreCase(consumerType.trim())) {
						consumerType = "METERED";
					} else {
						consumerType = "UNMETERED";
					}
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
			AppLog.info(e);
		} catch (IOException e) {
			AppLog.info(e);
		} catch (Exception e) {
			AppLog.info(e);
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
				AppLog.info(e);
			}
		}
		AppLog.end();
		return consumerDetailList;
	}
	
	
	/**
	 * <p>
	 * This method overloaded method is to retrieve all the details from the database of the
	 * consumer for those meter read to be updated to validate the reading
	 * details sent by HHD.
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
	 */
	
	public static List<ConsumerDetail> getConsumerDetailByAccountIDAndBillRound2(
			ArrayList<BillGenerationDetails> toUploadMeterReadDetailsList) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// String billRoundId = "";
		String billRoundId = "' '";
		// String accountIds = "";
		String accountIds = "' '";
		BillGenerationDetails billGenerationDetails = null;
		List<ConsumerDetail> consumerDetailList = new ArrayList<ConsumerDetail>();
		ConsumerDetail consumerDetail = null;
		PreviousMeterReadDetails previousMeterReadDetails = null;
		try {
			if (null != toUploadMeterReadDetailsList
					&& toUploadMeterReadDetailsList.size() > 0) {
				for (int i = 0; i < toUploadMeterReadDetailsList.size(); i++) {
					billGenerationDetails = (BillGenerationDetails) toUploadMeterReadDetailsList
							.get(i);
					if (null != billGenerationDetails.getAcctId()
							&& !"".equals( billGenerationDetails.getAcctId()
									.trim())) {
						// billRoundId = meterReadUploadDetails.getBillRound();
					
						
						billRoundId = billRoundId + ",'"
								+ 	billGenerationDetails.getBillRoundId() + "'";
						// accountIds = accountIds + "'"
						// + meterReadUploadDetails.getKno() + "',";
						accountIds = accountIds + ",'"
								+  billGenerationDetails.getAcctId() + "'";
					}
				}
				// accountIds = accountIds + "''";
			}
			// System.out.println("accountIds::" + accountIds + "billRoundId::"
			// + billRoundId);
			conn = DBConnector.getConnection();
			String query = QueryContants
					.getConsumerDetailByAccountIDAndBillRoundQuery(billRoundId,
							accountIds);
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
				if (null != consumerType) {
					if ("SAWATSEW".equalsIgnoreCase(consumerType.trim())
							|| "SAWATR".equalsIgnoreCase(consumerType.trim())) {
						consumerType = "METERED";
					} else {
						consumerType = "UNMETERED";
					}
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
			AppLog.info(e);
		} catch (IOException e) {
			AppLog.info(e);
		} catch (Exception e) {
			AppLog.info(e);
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
				AppLog.info(e);
			}
		}
		AppLog.end();
		return consumerDetailList;
	}
	
	/**
	 * <p>
	 * This method is used to retrieve data from DB once basic validation completes
	 * </p>
	 * 
	 * @param billdetails
	 * @param billRoundID
	 * @param acctNo
	 * @return
	 */
	public static BillGenerationDetails getfinalBillGenerationDetailsfromDB(BillGenerationDetails billdetails,String billRoundID,String acctNo )
	{
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnection();
			String query = QueryContants
			.getBillGenerationDetailsQuery(billRoundID,
					acctNo);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			int count=0;
			while (rs.next()) 
			{
				++count;
				
				
				if(rs.getString("last_updt_by")!=null)
				billdetails.setUserName(rs.getString("last_updt_by").trim());
				if(rs.getString("ACCT_ID")!=null)
				billdetails.setAcctId(rs.getString("ACCT_ID").trim());	
				if(rs.getString("BILL_ROUND_ID")!=null)
				billdetails.setBillRoundId(rs.getString("BILL_ROUND_ID").trim());	
				if(rs.getString("reg_reading")!=null)
				billdetails.setRegRead(rs.getString("reg_reading").trim());	
				if(rs.getString("READ_TYPE_FLG")!=null)
				billdetails.setReadType(rs.getString("READ_TYPE_FLG").trim());	
				if(rs.getString("bill_gen_dateNew")!=null)
				billdetails.setReadDate(rs.getString("bill_gen_dateNew").trim());	
				if(rs.getString("last_updt_by")!=null)
				billdetails.setMeterReaderName(rs.getString("last_updt_by").trim());	
				if(rs.getString("new_avg_read")!=null)
				billdetails.setAverageConsumption(rs.getString("new_avg_read").trim());	
				if(rs.getString("new_no_of_floors")!=null)
				billdetails.setNoOfFloors(rs.getString("new_no_of_floors").trim());	
				if(rs.getString("reader_rem_cd")!=null)
				billdetails.setReaderRemark(rs.getString("reader_rem_cd").trim());	
				
			}
			if(count>0)
			{
				//System.out.println("Populated value for AcctID::::::"+billdetails.getAcctId());
				
				return billdetails;
			}
		}
		 catch (SQLException e) {
			    e.printStackTrace();
				AppLog.info(e);
			} catch (IOException e) {
				 e.printStackTrace();
				AppLog.info(e);
			} catch (Exception e) {
				 e.printStackTrace();
				AppLog.info(e);
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
					AppLog.info(e);
				}
		return billdetails;
		
	}
	}
	
	/**
	 * <p>
	 * This method is used to update consumer pre bill proc once error is there in the record
	 * </p>
	 * 
	 * @param accountNo
	 * @param billRoundId
	 * @param errorDetails
	 */
	public static void updateWebServiceInBillingRemark(String accountNo,String billRoundId , String errorDetails)
	{
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnection();
			String query = QueryContants.updateBillingRemarks();
			ps = conn.prepareStatement(query);
			ps.setString(1,errorDetails);
			ps.setString(2,accountNo);
			ps.setString(3,billRoundId);
		    int i= ps.executeUpdate();
		    
			
		}
		catch (SQLException e) {
		    e.printStackTrace();
			AppLog.info(e);
		} catch (IOException e) {
			 e.printStackTrace();
			AppLog.info(e);
		} catch (Exception e) {
			 e.printStackTrace();
			AppLog.info(e);
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
				AppLog.info(e);
			}
		
	}
   }
}
