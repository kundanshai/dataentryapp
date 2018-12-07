/************************************************************************************************************
 * @(#) ExcelUtil.java   09 Oct 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.MRDScheduleDetails;

/**
 * <p>
 * This is an Util class to create MRD schedule excel files.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 26-10-2012 Apurb Sinha(Tata Consultancy Services) changed the getter
 *          for average read. The average read was wrongly read from previous
 *          meter read.
 * @history 15-11-2012 added ZRO, Area Description by Matiur Rahman
 */
public class ExcelUtil {
	// /**
	// * @param srcFilePath
	// * @param dstFilePath
	// */
	// private static void copyfile(String srcFilePath, String dstFilePath) {
	// try {
	// File srcFile = new File(srcFilePath);
	// File dstFile = new File(dstFilePath);
	// InputStream in = new FileInputStream(srcFile);
	// // For Append the file.
	// // OutputStream out = new FileOutputStream(f2,true);
	// // For Overwrite the file.
	// OutputStream out = new FileOutputStream(dstFile);
	// byte[] buf = new byte[1024];
	// int len;
	// while ((len = in.read(buf)) > 0) {
	// out.write(buf, 0, len);
	// }
	// in.close();
	// out.close();
	// // System.out.println("File copied.");
	// } catch (FileNotFoundException ex) {
	// // ex.printStackTrace();
	// // System.out
	// // .println(ex.getMessage() + " in the specified directory.");
	// // System.exit(0);
	// AppLog.error(ex);
	// } catch (IOException e) {
	// AppLog.error(e);
	// // e.printStackTrace();
	// // System.out.println(e.getMessage());
	// }
	// }

	/**
	 * <p>
	 * This method is used to create MRD schedule excel files.
	 * </p>
	 * 
	 * @param fileName
	 * @param consumerList
	 * @param startDate
	 */
	public void createMRDScheduleExcel(String fileName,
			List<ConsumerDetail> consumerList,
			MRDScheduleDetails mrdScheduleDetails, String userID) {
		AppLog.begin();
		File outputFile;
		try {

			File mrdTempletFile = new File(PropertyUtil
					.getProperty("UCMdocumentUpload")
					+ "/"
					+ PropertyUtil.getProperty("MTR_READ_DOWNLOAD_TEMPLATE"));

			outputFile = new File(PropertyUtil.getProperty("UCMdocumentUpload")
					+ "/" + userID + "/" + fileName);
			AppLog.info("Creating " + fileName + " START");
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("er", "ER"));
			Workbook workbook = Workbook.getWorkbook(mrdTempletFile, ws);

			// Sheet readsheet = workbook.getSheet(0);
			// System.out.println("Writing in " + readsheet.getName() + " of "
			// + outputFile);

			WritableWorkbook writableWorkbook = Workbook.createWorkbook(
					outputFile, workbook);

			WritableSheet sheet = writableWorkbook.getSheet("Sheet1");

			Label labelMETER_READER = new Label(2, 0, mrdScheduleDetails
					.getMeterReader());// METER
			// READER
			sheet.addCell(labelMETER_READER);
			Label labelMETER_READ_DIARY_CODE = new Label(11, 0,
					mrdScheduleDetails.getMeterReadDiaryCode());// METER READ
			// DIARY CODE
			sheet.addCell(labelMETER_READ_DIARY_CODE);
			Label labelSERVICE_CYCLE = new Label(2, 1, mrdScheduleDetails
					.getServiceCycle());// SERVICE
			// CYCLE
			sheet.addCell(labelSERVICE_CYCLE);
			Label labelROUTE = new Label(2, 2, mrdScheduleDetails.getRoute());// ROUTE
			sheet.addCell(labelROUTE);
			Label labelZRO = new Label(11, 2, mrdScheduleDetails.getZroCode());// ZRO
			sheet.addCell(labelZRO);
			// Label labelZONE = new Label(2, 3,
			// mrdScheduleDetails.getZone());// ZONE
			// sheet.addCell(labelZONE);
			Label labelAREA = new Label(2, 3, mrdScheduleDetails.getAreaDesc());// AREA
			sheet.addCell(labelAREA);
			Label labelZONE = new Label(11, 3, mrdScheduleDetails.getZone());// ZONE
			sheet.addCell(labelZONE);
			Label labelMRD_CODE = new Label(15, 3, mrdScheduleDetails.getMrNo());// METER
			// READ
			// DIARY CODE
			sheet.addCell(labelMRD_CODE);

			Label labelSTART_DATE = new Label(11, 1, mrdScheduleDetails
					.getStartDate());// START DATE
			sheet.addCell(labelSTART_DATE);

			Label labelEND_DATE = new Label(15, 1, mrdScheduleDetails
					.getEndDate());// End Date
			sheet.addCell(labelEND_DATE);
			// System.out.println("Total Consumer::" + consumerList.size());
			// set values from 5th row here
			ConsumerDetail consumerDetail = null;
			for (int i = 6, j = 0; j < consumerList.size(); i++, j++) {
				consumerDetail = (ConsumerDetail) consumerList.get(j);
				// System.out.println("consumerDetail.getSEQ()>>"
				// + consumerDetail.getServiceCycleRouteSeq());
				Label labelSEQ = new Label(0, i, consumerDetail
						.getServiceCycleRouteSeq());// SEQ
				Label labelKNO = new Label(1, i, consumerDetail.getKno()
						+ "\n&\n" + consumerDetail.getWcNo());// KNO/WC No
				Label labelCONSUMER_NAME = new Label(2, i, consumerDetail
						.getName());// CONSUMER_NAME
				Label labelCONSUMER_ADDRESS = new Label(3, i, consumerDetail
						.getAddress());// CONSUMER_ADDRESS
				Label labelCONSUMER_CATEGORY = new Label(4, i, consumerDetail
						.getCat());// CONSUMER_CATEGORY
				Label labelUNOC_DWEL_UNITS = new Label(5, i, consumerDetail
						.getPremiseDetails().getUnocDwelUnits());// UNOC_DWEL_UNITS
				Label labelOCU_DWEL_UNITS = new Label(6, i, consumerDetail
						.getPremiseDetails().getOcuDwelUnits());// OCU_DWEL_UNITS
				Label labelTOTL_DWEL_UNITS = new Label(7, i, consumerDetail
						.getPremiseDetails().getTotlDewlUnits());// TOTL_DWEL_UNITS
				Label labelNO_OF_FLOORS = new Label(8, i, consumerDetail
						.getPremiseDetails().getNoOfFloor());// NO_OF_FLOORS
				Label labelNO_OF_FUNC_SITES_BEDS_ROOMS = new Label(9, i,
						consumerDetail.getPremiseDetails()
								.getNoOfFuncSitesBedsRooms());// NO_OF_FUNC_SITES_BEDS_ROOMS
				Label labelMTR_NUMBER = new Label(10, i, consumerDetail
						.getPrevMeterRead().getMeterNumber());// MTR_NUMBER
				Label labelMTR_READ_DATE = new Label(11, i, consumerDetail
						.getPrevMeterRead().getMeterReadDate());// MTR_READ_DATE
				Label labelMTR_STATUS_CODE = new Label(12, i, consumerDetail
						.getPrevMeterRead().getMeterStatus());// MTR_STATUS_CODE
				Label labelMTR_READ = new Label(13, i, consumerDetail
						.getPrevMeterRead().getRegRead());// MTR_READ
				Label labelCONS = new Label(14, i, consumerDetail
						.getPrevMeterRead().getCons());// CONS
				// Label labelEFFEC_NO_OF_DAYS = new Label(15, i, "123");
				Label labelEFFEC_NO_OF_DAYS = new Label(15, i, consumerDetail
						.getPrevMeterRead().getEffecNoOfDays());// EFFEC_NO_OF_DAYS
				// Label labelAVG = new Label(16, i, "123");
				Label labelAVG = new Label(16, i, consumerDetail
						.getCurrentAvgConsumption());// AVG

				// Label l17 = new Label(17, i, "123");
				/*
				 * Label l18 = new Label(17, i, "123"); Label l19 = new
				 * Label(18, i, "123"); Label l20 = new Label(19, i, "123");
				 * Label l21 = new Label(20, i, "123"); Label l22 = new
				 * Label(21, i, "tuhin");
				 */

				sheet.addCell(labelSEQ);
				sheet.addCell(labelKNO);
				sheet.addCell(labelCONSUMER_NAME);
				sheet.addCell(labelCONSUMER_ADDRESS);
				sheet.addCell(labelCONSUMER_CATEGORY);
				sheet.addCell(labelUNOC_DWEL_UNITS);
				sheet.addCell(labelOCU_DWEL_UNITS);
				sheet.addCell(labelTOTL_DWEL_UNITS);
				sheet.addCell(labelNO_OF_FLOORS);
				sheet.addCell(labelNO_OF_FUNC_SITES_BEDS_ROOMS);
				sheet.addCell(labelMTR_NUMBER);
				sheet.addCell(labelMTR_READ_DATE);
				sheet.addCell(labelMTR_STATUS_CODE);
				sheet.addCell(labelMTR_READ);
				sheet.addCell(labelCONS);
				sheet.addCell(labelEFFEC_NO_OF_DAYS);
				sheet.addCell(labelAVG);

				/*
				 * sheet.addCell(l18); sheet.addCell(l19); sheet.addCell(l20);
				 * sheet.addCell(l21); sheet.addCell(l22);
				 */

			}

			writableWorkbook.write();
			writableWorkbook.close();
			AppLog.info("Creating " + fileName + " END");

		} catch (IOException e) {
			// e.printStackTrace();
			AppLog.info("Creating " + fileName + " FAILED");
			AppLog.error(e);
		} catch (BiffException e) {
			// e.printStackTrace();
			AppLog.info("Creating " + fileName + " FAILED");
			AppLog.error(e);
		} catch (WriteException e) {
			// e.printStackTrace();
			AppLog.info("Creating " + fileName + " FAILED");
			AppLog.error(e);
		} catch (Exception e) {
			// e.printStackTrace();
			AppLog.info("Creating " + fileName + " FAILED");
			AppLog.error(e);
		}
		AppLog.end();
	}
}
