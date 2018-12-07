/************************************************************************************************************
 * @(#) MrdTransferDAO.java   02 Aug 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Mrd Transfer.
 * </p>
 * 
 * @author Madhuri Singh(Tata Consultancy Services)
 * @since 02-08-2016
 * @DJB-2200
 */
public class MrdTransferDAO implements Runnable {
	/**
	 * PROC_MRD_TRNSFR(O_ZNO IN VARCHAR2,O_MRNO IN NUMBER,O_ARNO IN NUMBER,N_ZNO
	 * IN VARCHAR2, N_MRNO IN NUMBER,N_ARNO IN NUMBER,N_ZRO_CD IN VARCHAR2)
	 */
	private static final String storedProcQuery = "{call  PROC_MRD_TRNSFR(?, ?, ?, ?, ?, ?, ?)}";
	private static final String storedProcZroDesc = "{call PROC_ZRO_DESC(?,?,?,?)}";

	/**
	 * <p>
	 * The method is used to check count of Inprogress Status in mrd transfer
	 * stg table procedure <code>getCntOfInProgressForMrdTrnsfr</code>.
	 * </p>
	 * 
	 * @return Count
	 * @since 02-08-2016
	 * @DJB-2200
	 */
	public static int getCntOfInProgressForMrdTrnsfr(String mrKey) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int Count = 0;
		try {
			String query = QueryContants
					.getCntOfInProgressForMrdTrnsfrQuery(mrKey);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			if (null != mrKey && !"".equalsIgnoreCase(mrKey.trim())) {
				ps.setString(++i, mrKey);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Count = rs.getInt("Status");
				AppLog.info("Count of InProgress status in Stg table** "
						+ Count);
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
		return Count;

	}

	/**
	 * <p>
	 * The method is used to save details of mrd transfer details in PENDING
	 * state in stg table procedure <code>getCntOfInProgressForMrdTrnsfr</code>.
	 * </p>
	 * 
	 * @return Count
	 * @since 02-08-2016
	 * @DJB-2200
	 */
	public static int InitiateMrdTransfer(String mrd, String zone,
			String oldZroCode, String newZROcd, String MrNo, String Area,
			String userId) {

		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsInserted = 0;
		try {
			String query = QueryContants.initiateMrdTransferQuery();
			AppLog.info("MRD::" + mrd + "::ZONE::" + zone + "::MR NO" + MrNo
					+ "::AREA " + Area + "::Old ZRO::" + oldZroCode
					+ "::New ZRO::" + newZROcd + "::User Id::" + userId
					+ "::\ninitiateMrdTransferQuery::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, mrd);
			ps.setString(++i, zone);
			ps.setString(++i, MrNo);
			ps.setString(++i, Area);
			ps.setString(++i, newZROcd);
			ps.setString(++i, oldZroCode);
			ps.setString(++i, userId);
			rowsInserted = ps.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				rowsInserted = 1;
			} else {
				AppLog.error(e);
			}
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
		return rowsInserted;
	}

	/**
	 * <p>
	 * The method which actually process Mrd Transfer by calling the SQL
	 * procedure <code>PROC_MRD_TRNSFR</code>.
	 * </p>
	 * 
	 * @param oldZone
	 * @param oldMRNo
	 * @param oldAreaCode
	 * @param newZone
	 * @param newMRNo
	 * @param newAreaCode
	 * @param newZROCode
	 * @return
	 * @since 02-08-2016
	 * @DJB-2200
	 */

	public static int mrdTransfer(String oldZone, String oldMRNo,
			String oldAreaCode, String newZone, String newMRNo,
			String newAreaCode, String newZROCode) {
		Connection conn = null;
		CallableStatement cs = null;
		int procReturnCode = 0;
		try {
			AppLog.begin();
			AppLog.info("oldZone::" + oldZone + "::oldMRNo::" + oldMRNo
					+ "::oldAreaCode::" + oldAreaCode + "::newZone::" + newZone
					+ "::newMRNo::" + newMRNo + "::newAreaCode::" + newAreaCode
					+ "::newZROCode::" + newZROCode);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQuery);
			int i = 0;
			cs.setString(++i, oldZone.trim());
			AppLog.info("old Zone" + oldZone.trim());
			cs.setInt(++i, Integer.parseInt(oldMRNo.trim()));
			AppLog.info("MR No" + Integer.parseInt(oldMRNo.trim()));
			cs.setInt(++i, Integer.parseInt(oldAreaCode.trim()));
			AppLog.info("Area No" + Integer.parseInt(oldAreaCode.trim()));
			cs.setString(++i, newZone.trim());
			AppLog.info("newZone  " + newZone.trim());
			cs.setInt(++i, Integer.parseInt(newMRNo.trim()));
			cs.setInt(++i, Integer.parseInt(newAreaCode.trim()));
			cs.setString(++i, newZROCode);
			AppLog.info(" newZROCode  " + newZROCode);
			procReturnCode = cs.executeUpdate();
			AppLog.info("****procReturnCode****" + procReturnCode);
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
		return procReturnCode;
	}

	/**
	 * <p>
	 * The method which actually process ZroWiseMrdTransfer by calling the SQL
	 * procedure <code>PROC_ZRO_DESC</code>.
	 * </p>
	 * 
	 * @param oldZone
	 * @param oldMrNo
	 * @param oldAreaNo
	 * @param NewZroCode
	 * @return
	 * @since 02-08-2016
	 * @DJB-2200
	 */
	public static int ZroWiseMrdTransfer(String oldZone, String oldMrNo,
			String oldAreaNo, String NewZroCode) {
		Connection conn = null;
		CallableStatement cs = null;
		int procReturnCode = 0;
		try {
			AppLog.begin();
			AppLog.info("oldZone::" + oldZone + "oldMrNo::" + oldMrNo
					+ "oldAreaNo::" + oldAreaNo + "NewZroCode::" + NewZroCode);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcZroDesc);
			int i = 0;
			cs.setString(++i, oldZone.trim());
			cs.setInt(++i, Integer.parseInt(oldMrNo.trim()));
			cs.setInt(++i, Integer.parseInt(oldAreaNo.trim()));
			cs.setString(++i, NewZroCode.trim());
			procReturnCode = cs.executeUpdate();
		} catch (SQLException e) {
			AppLog.error(e);
		} catch (Exception e) {
			AppLog.error(e);
		} finally {
			try {
				if (null != cs) {
					cs.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}
		AppLog.end();
		return procReturnCode;
	}

	private String areaNO;
	private String mrkey;
	private String mrNo;

	private String newZone;

	private String oldZone;

	private String userId;

	private String zroCode;

	/**
	 * <p>
	 * Parameterized constructor class MrdTransferDAO which creates a new thread
	 * every time it is initiated to Call the SQL procedure
	 * <code>PROC_MRD_TRNSFR</code>.
	 * </p>
	 * 
	 * @param oldZone
	 * @param mrNo
	 * @param areaNO
	 * @param newZone
	 * @param zroCode
	 * @param mrkey
	 * @param userId
	 */

	public MrdTransferDAO(String oldZone, String mrNo, String areaNO,
			String newZone, String zroCode, String mrkey, String userId) {
		AppLog.begin();
		this.oldZone = oldZone;
		this.mrNo = mrNo;
		this.areaNO = areaNO;
		this.newZone = newZone;
		this.zroCode = zroCode;
		this.mrkey = mrkey;
		this.userId = userId;
		new Thread(this).start();
		AppLog.end();

	}

	/**
	 * @return areaNO
	 */
	public String getAreaNO() {
		return areaNO;
	}

	/**
	 * @return mrkey
	 */
	public String getMrkey() {
		return mrkey;
	}

	/**
	 * @return mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @return newZone
	 */
	public String getNewZone() {
		return newZone;
	}

	/**
	 * @return oldZone
	 */
	public String getOldZone() {
		return oldZone;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return zroCode
	 */
	public String getZroCode() {
		return zroCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		AppLog.info("START:: New Thread for Process Mrd Transfer");
		try {
			/**
			 * update Mrd Transfer Process Status from PENDING to IN PROGRESS.
			 */
			updateMrdTransferProcessStatus(this.userId, this.mrkey);
			if (null != this.newZone && !"".equalsIgnoreCase(this.newZone)) {
				/**
				 * process Mrd Transfer by calling the SQL procedure
				 * <code>PROC_Mrd_TRNSFR</code>
				 */
				mrdTransfer(this.oldZone, this.mrNo, this.areaNO, this.newZone,
						this.mrNo, this.areaNO, this.zroCode);
			} else {

				/**
				 * process Mrd Transfer by calling the SQL procedure
				 * <code>PROC_ZRO_DESC</code>
				 */
				ZroWiseMrdTransfer(this.oldZone, this.mrNo, this.areaNO,
						this.zroCode);
			}

		} catch (Throwable t) {
			AppLog.error(t);
		}
		AppLog.info("END:: New Thread for Process Mrd Transfer");
		AppLog.end();
	}

	/**
	 * @param areaNO
	 *            the setAreaNO to set
	 */
	public void setAreaNO(String areaNO) {
		this.areaNO = areaNO;
	}

	/**
	 * @param mrkey
	 *            the setMrkey to set
	 */
	public void setMrkey(String mrkey) {
		this.mrkey = mrkey;
	}

	/**
	 * @param mrNo
	 *            the setMrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @param newZone
	 *            the setNewZone to set
	 */
	public void setNewZone(String newZone) {
		this.newZone = newZone;
	}

	/**
	 * @param oldZone
	 *            the setOldZone to set
	 */
	public void setOldZone(String oldZone) {
		this.oldZone = oldZone;
	}

	/**
	 * @param userId
	 *            the setUserId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param zroCode
	 *            the setZroCode to set
	 */
	public void setZroCode(String zroCode) {
		this.zroCode = zroCode;
	}

	/**
	 * <p>
	 * The method is used to update mrd transfer status INPROGRESS in stg table
	 * procedure <code>updateMrdTransferProcessStatus</code>.
	 * </p>
	 * 
	 * @since 02-08-2016
	 * @DJB-2200
	 */
	private void updateMrdTransferProcessStatus(String userId, String mrkey) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = QueryContants.updateMrdTransferProcessStatusQuery();
			AppLog.info("IN updateMrdTransferProcessStatus:: ::userId::"
					+ userId + "" + mrkey
					+ "::updateMrdTransferProcessStatus::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, mrkey);
			ps.executeUpdate();

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
	}
}
