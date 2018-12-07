/************************************************************************************************************
 * @(#) CreateMRDDAO.java   21 May 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.DBConnector;

/**
 * <p>
 * DAO class for Creating New MRD.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 21-05-2013
 * 
 * @history 06-01-2016 Arnab Nandy Added newColCat at {@link #createNewMRD},
 * 			and added extra parameter to procedure {@link #PROC_NEW_MRD}
 *          as per JTRac DJB-4304
 * 
 */
public class CreateMRDDAO {
	/**
	 * proc_new_mrd(n_zno IN VARCHAR2, n_mrno IN NUMBER, n_arno IN NUMBER,
	 * n_area_desc IN VARCHAR2, n_zro_cd IN VARCHAR2, n_mrd_typ IN VARCHAR2)
	 */
	private static final String storedProcQyery = "{call  PROC_NEW_MRD(?, ?, ?, ?, ?, ?, ?)}";

	/**
	 * <p>
	 * The method which actually process Demand Transfer by calling the SQL
	 * procedure <code>PROC_DMND_TRNSFR</code>.
	 * </p>
	 * 
	 * @param newZone
	 * @param newMRNo
	 * @param newAreaCode
	 * @param newAreaDesc
	 * @param newZROCode
	 * @param newMRDType
	 * @param newColCat
	 * @param userId
	 * @return
	 */
	public static int createNewMRD(String newZone, String newMRNo,
			String newAreaCode, String newAreaDesc, String newZROCode,
			String newMRDType, String newColCat, String userId) {
		Connection conn = null;
		CallableStatement cs = null;
		int procReturnCode = 0;
		try {
			AppLog.begin();
			// System.out.println("newZone::" + newZone + "::newMRNo::" +
			// newMRNo
			// + "::newAreaCode::" + newAreaCode + "::newAreaDesc::"
			// + newAreaDesc + "::newZROCode::" + newZROCode
			// + "::userId::" + userId);
			AppLog.info("newZone::" + newZone + "::newMRNo::" + newMRNo
					+ "::newAreaCode::" + newAreaCode + "::newAreaDesc::"
					+ newAreaDesc + "::newZROCode::" + newZROCode
					+ "::newColCat::" + newColCat + "::userId::" + userId);
			
			conn = DBConnector.getConnection();
			cs = conn.prepareCall(storedProcQyery);
			int i = 0;
			cs.setString(++i, newZone.trim());
			cs.setInt(++i, Integer.parseInt(newMRNo.trim()));
			cs.setInt(++i, Integer.parseInt(newAreaCode.trim()));
			cs.setString(++i, newAreaDesc);
			cs.setString(++i, newZROCode.trim());
			cs.setString(++i, newMRDType.trim());
			cs.setString(++i, newColCat.trim());
			procReturnCode = cs.executeUpdate();
			// System.out.println("procReturnCode::" + procReturnCode);

		} catch (SQLException e) {
			e.printStackTrace();
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

}
