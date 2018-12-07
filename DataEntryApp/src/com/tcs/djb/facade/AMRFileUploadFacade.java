/************************************************************************************************************
 * @(#) AMRFileUploadFacade.java   02 Jun 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.facade;

import java.util.Arrays;
import java.util.Map;

import com.tcs.djb.dao.MRDFileUploadDAO;
import com.tcs.djb.model.ExcelToDBTableResponse;
import com.tcs.djb.rms.util.UtilityForAll;
import com.tcs.djb.util.AppLog;

/**
 * <P>
 * This class includes all the business logic to Upload AMR file by calling
 * Validator Classes or DAO classes.
 * </P>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 02-06-2016 as per open project 1202,jTrac : DJB-4464
 * 
 */
public class AMRFileUploadFacade {

	/**
	 * <p>
	 * This method is used to save AMR Excel Data
	 * </p>
	 * 
	 * @param dataArray
	 * @param headerID
	 * @return
	 */
	public static ExcelToDBTableResponse saveAMRExcelData(String[] dataArray,
			Integer headerID) {
		AppLog.begin();
		int dataArraySize = dataArray.length;
		String[] preparedDataArray = new String[dataArraySize + 3];
		Map<String, String> mrkeyBillRoundMap = MRDFileUploadDAO
				.getOpenBillRoundOfMRD(headerID);
		AppLog.info("AMRFileUploadFacade BillRoundID>>"
				+ mrkeyBillRoundMap.get("BILLROUND") + "MRKEY>>"
				+ mrkeyBillRoundMap.get("MRKEY"));
		preparedDataArray[0] = headerID + "";
		preparedDataArray[dataArray.length + 1] = mrkeyBillRoundMap
				.get("BILLROUND");
		preparedDataArray[dataArray.length + 2] = mrkeyBillRoundMap
				.get("MRKEY");
		for (int i = 0; i < dataArray.length; i++) {
			if (i == 1) {
				if (dataArray[i].trim().length() >= 10) {
					String kno = dataArray[i].substring(0, 10);
					if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
						preparedDataArray[i + 1] = kno.trim();
					} else {
						break;
					}
					AppLog.info("DATA KNO>>>>" + preparedDataArray[i + 1]
							+ ">>Size>>>" + preparedDataArray[i + 1].length());
				} else {
					break;
				}
			} else {
				if (dataArray[i].contains("&")) {
					dataArray[i].replace("&", "~");
				}
				preparedDataArray[i + 1] = dataArray[i];
			}
		}
		AppLog
				.info("Data Array to DAO:::"
						+ Arrays.toString(preparedDataArray));
		if (UtilityForAll.isEmptyString(preparedDataArray[1])) {
			AppLog.end();
			return null;
		}
		int noOfRecord = 0;
		ExcelToDBTableResponse excelToDBTableResponse = new ExcelToDBTableResponse();
		try {
			excelToDBTableResponse = new ExcelToDBTableResponse();
			noOfRecord = MRDFileUploadDAO.saveAMRExcelData(preparedDataArray);
			excelToDBTableResponse.setNoOfRecord(noOfRecord);
		} catch (Exception e) {
			AppLog.error(">>>" + e.getLocalizedMessage());
			excelToDBTableResponse.setThrowable(e);
		}
		/* AppLog.debug("excelToDBTableResponse>>"+excelToDBTableResponse); */
		AppLog.end();
		return excelToDBTableResponse;
	}

}
