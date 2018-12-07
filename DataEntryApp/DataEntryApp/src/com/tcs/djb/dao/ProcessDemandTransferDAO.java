/************************************************************************************************************
 * @(#) ProcessDemandTransferDAO.java   08 May 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.tcs.djb.constants.QueryContants;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * The DAO Class to Process Demand Transfer in a new thread executing SQL
 * procedure <code>PROC_DMND_TRNSFR</code>.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 08-05-2013
 * @see Runnable
 * @history 27-05-2013 Matiur Rahman changed PROCEDURE from
 *          <cod>PROC_DMND_TRNSFR</code> to <cod>PROC_ACCESS_GRP_DMND</code> .
 */
public class ProcessDemandTransferDAO implements Runnable {

	private boolean isSuccess = false;
	private String kno;
	private String newArea;
	private String newMRKey;
	private String newMRNo;
	private String newZone;
	private String newZROCode;
	private String oldArea;
	private String oldMRKey;
	private String oldMRNo;
	private String oldZone;
	private List<String> selectedKNOList;
	// private final String storedProcQyery =
	// "{call  PROC_DMND_TRNSFR(?, ?, ?, ?, ?, ?, ?, ?)}";
	/*
	 * PROC_ACCESS_GRP_DMND(kno IN VARCHAR2, zno IN VARCHAR2, o_mrk IN VARCHAR2,
	 * n_mrk IN VARCHAR2,user_id VARCHAR2)
	 */
	private final String storedProcQyery = "{call  PROC_ACCESS_GRP_DMND(?, ?, ?, ?, ?)}";

	private String userId;

	/**
	 * <p>
	 * Parameterized constructor class ProcessDemandTransferDAO which creates a
	 * new thread every time it is initiated to Call the SQL procedure
	 * <code>PROC_DMND_TRNSFR</code>.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 *            selected KNO Map.
	 * @param oldZone
	 *            Old Zone of the Consumer.
	 * @param oldMRNo
	 *            Old MR No of the Consumer.
	 * @param oldArea
	 *            Old Area code of the Consumer.
	 * @param newZone
	 *            New Zone of the Consumer.
	 * @param newMRNo
	 *            New MR No of the Consumer.
	 * @param newArea
	 *            New Area Code of the Consumer.
	 * @param newZROCode
	 *            New ZRO Code of the Consumer.
	 * @param userId
	 *            user id of the user that is logged in the application.
	 */
	public ProcessDemandTransferDAO(List<String> selectedKNOList,
			String oldZone, String oldMRNo, String oldArea, String newZone,
			String newMRNo, String newArea, String newZROCode, String userId) {
		AppLog.begin();
		// System.out.println("IN LastOKReadCorrectionDAO");
		this.selectedKNOList = selectedKNOList;
		this.oldZone = oldZone;
		this.oldMRNo = oldMRNo;
		this.oldArea = oldArea;
		this.newZone = newZone;
		this.newMRNo = newMRNo;
		this.newArea = newArea;
		this.newZROCode = newZROCode;
		this.userId = userId;
		new Thread(this).start();
		// System.out.println("OUT LastOKReadCorrectionDAO");
		AppLog.end();

	}

	/**
	 * <p>
	 * Parameterized constructor class ProcessDemandTransferDAO which creates a
	 * new thread every time it is initiated to Call the SQL procedure
	 * <code>PROC_DMND_TRNSFR</code>.
	 * </p>
	 * 
	 * @param selectedKNOMap
	 *            selected KNO Map.
	 * @param oldMRKey
	 *            Old MRKey of the Consumer.
	 * @param oldZone
	 *            Old Zone of the Consumer.
	 * @param oldMRNo
	 *            Old MR No of the Consumer.
	 * @param oldArea
	 *            Old Area code of the Consumer.
	 * @param newMRKey
	 *            new MRKey of the Consumer.
	 * @param newZone
	 *            New Zone of the Consumer.
	 * @param newMRNo
	 *            New MR No of the Consumer.
	 * @param newArea
	 *            New Area Code of the Consumer.
	 * @param newZROCode
	 *            New ZRO Code of the Consumer.
	 * @param userId
	 *            user id of the user that is logged in the application.
	 */
	public ProcessDemandTransferDAO(List<String> selectedKNOList,
			String oldMRKey, String oldZone, String oldMRNo, String oldArea,
			String newMRKey, String newZone, String newMRNo, String newArea,
			String newZROCode, String userId) {
		AppLog.begin();
		// System.out.println("IN LastOKReadCorrectionDAO");
		this.selectedKNOList = selectedKNOList;
		this.oldMRKey = oldMRKey;
		this.oldZone = oldZone;
		this.oldMRNo = oldMRNo;
		this.oldArea = oldArea;
		this.newMRKey = newMRKey;
		this.newZone = newZone;
		this.newMRNo = newMRNo;
		this.newArea = newArea;
		this.newZROCode = newZROCode;
		this.userId = userId;
		new Thread(this).start();
		// System.out.println("OUT LastOKReadCorrectionDAO");
		AppLog.end();

	}

	/**
	 * <p>
	 * Parameterized constructor class ProcessDemandTransferDAO which creates a
	 * new thread every time it is initiated to Call the SQL procedure
	 * <code>PROC_DMND_TRNSFR</code>.
	 * </p>
	 * 
	 * @param kno
	 *            10 digit account Id of the Consumer.
	 * @param oldZone
	 *            Old Zone of the Consumer.
	 * @param oldMRNo
	 *            Old MR No of the Consumer.
	 * @param oldArea
	 *            Old Area code of the Consumer.
	 * @param newZone
	 *            New Zone of the Consumer.
	 * @param newMRNo
	 *            New MR No of the Consumer.
	 * @param newArea
	 *            New Area Code of the Consumer.
	 * @param newZROCode
	 *            New ZRO Code of the Consumer.
	 * @param userId
	 *            user id of the user that is logged in the application.
	 */
	public ProcessDemandTransferDAO(String kno, String oldZone, String oldMRNo,
			String oldArea, String newZone, String newMRNo, String newArea,
			String newZROCode, String userId) {
		AppLog.begin();
		// System.out.println("IN LastOKReadCorrectionDAO");
		this.kno = kno;
		this.oldZone = oldZone;
		this.oldMRNo = oldMRNo;
		this.oldArea = oldArea;
		this.newZone = newZone;
		this.newMRNo = newMRNo;
		this.newArea = newArea;
		this.newZROCode = newZROCode;
		this.userId = userId;
		new Thread(this).start();
		// System.out.println("OUT LastOKReadCorrectionDAO");
		AppLog.end();

	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return the newArea
	 */
	public String getNewArea() {
		return newArea;
	}

	/**
	 * @return the newMRKey
	 */
	public String getNewMRKey() {
		return newMRKey;
	}

	/**
	 * @return the newMRNo
	 */
	public String getNewMRNo() {
		return newMRNo;
	}

	/**
	 * @return the newZone
	 */
	public String getNewZone() {
		return newZone;
	}

	/**
	 * @return the newZROCode
	 */
	public String getNewZROCode() {
		return newZROCode;
	}

	/**
	 * @return the oldArea
	 */
	public String getOldArea() {
		return oldArea;
	}

	/**
	 * @return the oldMRKey
	 */
	public String getOldMRKey() {
		return oldMRKey;
	}

	/**
	 * @return the oldMRNo
	 */
	public String getOldMRNo() {
		return oldMRNo;
	}

	/**
	 * @return the oldZone
	 */
	public String getOldZone() {
		return oldZone;
	}

	/**
	 * @return the selectedKNOList
	 */
	public List<String> getSelectedKNOList() {
		return selectedKNOList;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * <p>
	 * The method which actually process Demand Transfer by calling the SQL
	 * procedure <code>PROC_DMND_TRNSFR</code>.
	 * </p>
	 * 
	 * @param kno
	 * @param oldZone
	 * @param oldMRNo
	 * @param oldArea
	 * @param newZone
	 * @param newMRNo
	 * @param newArea
	 * @param newZROCode
	 * @param userId
	 */
	public void processDemandTransfer(String kno, String oldZone,
			String oldMRNo, String oldArea, String newZone, String newMRNo,
			String newArea, String newZROCode, String userId) {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			AppLog.begin();
			// System.out.println("kno::" + kno + "::oldZone::" + oldZone
			// + "::oldMRNo::" + oldMRNo + "::oldArea::" + oldArea
			// + "::newZone::" + newZone + "::newMRNo::" + newMRNo
			// + "::newArea::" + newArea + "::newZROCode::" + newZROCode);
			// System.out.println("IN processDemandTransfer");
			AppLog.info("IN processDemandTransfer process::kno::" + kno
					+ "::oldZone::" + oldZone + "::oldMRNo::" + oldMRNo
					+ "::oldArea::" + oldArea + "::newZone::" + newZone
					+ "::newMRNo::" + newMRNo + "::newArea::" + newArea
					+ "::newZROCode::" + newZROCode + "::userId::" + userId);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQyery);
			int i = 0;
			cs.setString(++i, kno.trim());
			cs.setString(++i, oldZone.trim());
			cs.setInt(++i, Integer.parseInt(oldMRNo.trim()));
			cs.setInt(++i, Integer.parseInt(oldArea.trim()));
			cs.setString(++i, newZone.trim());
			cs.setInt(++i, Integer.parseInt(newMRNo.trim()));
			cs.setInt(++i, Integer.parseInt(newArea.trim()));
			cs.setString(++i, newZROCode.trim());
			cs.executeUpdate();
			AppLog
					.info("::Demand Transfer process INITIATED::Procedure Name::PROC_DMND_TRNSFR");

			// System.out.println("OUT processDemandTransfer");
			AppLog.end();
		} catch (SQLException e) {
			// e.printStackTrace();
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
	}

	/**
	 * <p>
	 * The method which actually process Demand Transfer by calling the SQL
	 * procedure <code>PROC_ACCESS_GRP_DMND</code>.
	 * </p>
	 * 
	 * @param kno
	 * @param newZone
	 * @param oldMRKey
	 * @param newMRKey
	 * @param userId
	 */
	public void processDemandTransfer(String kno, String newZone,
			String oldMRKey, String newMRKey, String userId) {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			AppLog.begin();
			// System.out.println("IN processDemandTransfer process::kno::" +
			// kno
			// + "::newZone::" + newZone + "::oldMRKey::" + oldMRKey
			// + "::newMRKey::" + newMRKey + "::userId::" + userId);
			// System.out.println("IN processDemandTransfer");
			AppLog.info("IN processDemandTransfer process::kno::" + kno
					+ "::newZone::" + newZone + "::oldMRKey::" + oldMRKey
					+ "::newMRKey::" + newMRKey + "::userId::" + userId);
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQyery);
			int i = 0;
			/*
			 * PROC_ACCESS_GRP_DMND(kno IN VARCHAR2, zno IN VARCHAR2, o_mrk IN
			 * VARCHAR2, n_mrk IN VARCHAR2,user_id VARCHAR2)
			 */
			cs.setString(++i, kno.trim());
			cs.setString(++i, newZone.trim());
			cs.setString(++i, oldMRKey.trim());
			cs.setString(++i, newMRKey.trim());
			cs.setString(++i, userId.trim());
			cs.executeUpdate();
			AppLog
					.info("::Demand Transfer process INITIATED::Procedure Name::PROC_DMND_TRNSFR");
			// System.out.println("OUT processDemandTransfer");
			AppLog.end();
		} catch (SQLException e) {
			// e.printStackTrace();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		AppLog.info("START:: New Thread for Process Demand Transfer");
		try {
			if (null != this.selectedKNOList && this.selectedKNOList.size() > 0) {
				for (int i = 0; i < this.selectedKNOList.size(); i++) {
					// System.out.println(entry.getKey() + "<runProcedure>"
					// + entry.getValue());
					String kno = this.selectedKNOList.get(i);
					if (null != kno && kno.trim().length() == 10) {
						/**
						 * update Demand Transfer Process Status from PENDING to
						 * IN PROGRESS.
						 */
						updateDemandTransferProcessStatus(kno, this.oldMRKey,
								this.newMRKey, this.userId);
						/**
						 * process Demand Transfer by calling the SQL procedure
						 * <code>PROC_DMND_TRNSFR</code>
						 */
						// processDemandTransfer(kno, this.oldZone,
						// this.oldMRNo,
						// this.oldArea, this.newZone, this.newMRNo,
						// this.newArea, this.newZROCode, this.userId);
						/**
						 * process Demand Transfer by calling the SQL procedure
						 * <code>PROC_ACCESS_GRP_DMND</code>
						 */
						processDemandTransfer(kno, this.newZone, this.oldMRKey,
								this.newMRKey, this.userId);
					}
				}
			}
		} catch (Throwable t) {
			AppLog.error(t);
		}
		AppLog.info("END:: New Thread for Process Demand Transfer");
		AppLog.end();
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param newArea
	 *            the newArea to set
	 */
	public void setNewArea(String newArea) {
		this.newArea = newArea;
	}

	/**
	 * @param newMRKey
	 *            the newMRKey to set
	 */
	public void setNewMRKey(String newMRKey) {
		this.newMRKey = newMRKey;
	}

	/**
	 * @param newMRNo
	 *            the newMRNo to set
	 */
	public void setNewMRNo(String newMRNo) {
		this.newMRNo = newMRNo;
	}

	/**
	 * @param newZone
	 *            the newZone to set
	 */
	public void setNewZone(String newZone) {
		this.newZone = newZone;
	}

	/**
	 * @param newZROCode
	 *            the newZROCode to set
	 */
	public void setNewZROCode(String newZROCode) {
		this.newZROCode = newZROCode;
	}

	/**
	 * @param oldArea
	 *            the oldArea to set
	 */
	public void setOldArea(String oldArea) {
		this.oldArea = oldArea;
	}

	/**
	 * @param oldMRKey
	 *            the oldMRKey to set
	 */
	public void setOldMRKey(String oldMRKey) {
		this.oldMRKey = oldMRKey;
	}

	/**
	 * @param oldMRNo
	 *            the oldMRNo to set
	 */
	public void setOldMRNo(String oldMRNo) {
		this.oldMRNo = oldMRNo;
	}

	/**
	 * @param oldZone
	 *            the oldZone to set
	 */
	public void setOldZone(String oldZone) {
		this.oldZone = oldZone;
	}

	/**
	 * @param selectedKNOList
	 *            the selectedKNOList to set
	 */
	public void setSelectedKNOList(List<String> selectedKNOList) {
		this.selectedKNOList = selectedKNOList;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <p>
	 * This method is used to update Demand Transfer Process Status from PENDING
	 * to IN PROGRESS.
	 * </p>
	 * 
	 * @param kno
	 * @param oldMRKey
	 * @param newMRKey
	 * @param userId
	 */
	private void updateDemandTransferProcessStatus(String kno, String oldMRKey,
			String newMRKey, String userId) {
		AppLog.begin();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = QueryContants.updateDemandTransferProcessStatus();
			AppLog.info("IN updateDemandTransferProcessStatus::kno::" + kno
					+ "::oldMRKey::" + oldMRKey + "::oldZone::" + oldZone
					+ "::oldMRNo::" + oldMRNo + "::oldArea::" + oldArea
					+ "::newMRKey::" + newMRKey + "::newZone::" + newZone
					+ "::newMRNo::" + newMRNo + "::newArea::" + newArea
					+ "::newZROCode::" + newZROCode + "::userId::" + userId
					+ "::updateDemandTransferProcessStatus::" + query);
			conn = DBConnector.getConnection();
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, userId);
			ps.setString(++i, kno);
			ps.setString(++i, oldMRKey);
			ps.setString(++i, newMRKey);
			ps.setString(++i, userId);
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
