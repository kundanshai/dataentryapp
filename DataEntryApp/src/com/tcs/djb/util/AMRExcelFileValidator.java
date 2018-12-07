/************************************************************************************************************
 * @(#) AMRExcelFileValidator.java
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.model.AMRHeaderDetails;

/**
 * <p>
 * This is the Utility class for AMR Excel file validation used to validate the
 * details of the Excel file file.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 */
public class AMRExcelFileValidator {

	/**
	 * <p>
	 * This method is used to get Header Data of the Excel file
	 * </p>
	 * 
	 * @param excelFile
	 * @param amrHeaderRowNumber
	 * @return
	 * @throws Exception
	 */
	public static AMRHeaderDetails getHeaderData(File excelFile,
			String amrHeaderRowNumber) throws Exception {
		AppLog.begin();
		AMRHeaderDetails aMRHeaderDetails = new AMRHeaderDetails();
		if (excelFile != null) {
			InputStream inputStream = null;
			try {
				AppLog
						.info("\n\n:::::::::::::::: INSIDE EXCEL VALIDATOR insertHeaderData::::::::::::::::\n\n");
				inputStream = new BufferedInputStream(new FileInputStream(
						excelFile));
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				Iterator rows = sheet.rowIterator();

				/* Exempting Top Rows while reading Excel File If Needed Any */
				for (int i = 0; i < Integer.parseInt(amrHeaderRowNumber); i++) {
					HSSFRow row1 = (HSSFRow) rows.next();
					Iterator cells1 = row1.cellIterator();
					while (cells1.hasNext()) {
						HSSFCell cell = (HSSFCell) cells1.next();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (null != cell.getStringCellValue()
								&& !"".equalsIgnoreCase(cell
										.getStringCellValue().trim())) {
							String cellData = cell.getStringCellValue().trim();
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_METER_READER_HEADER)) {

								aMRHeaderDetails
										.setMeterReaderName((null == row1
												.getCell(2)
												.getStringCellValue() ? ""
												: row1.getCell(2)
														.getStringCellValue()
														.trim()));
								AppLog
										.info("meterReaderName>>>"
												+ aMRHeaderDetails
														.getMeterReaderName());
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_SERVICE_CYCLE_HEADER)) {

								aMRHeaderDetails.setServiceCycle((null == row1
										.getCell(2).getStringCellValue() ? ""
										: row1.getCell(2).getStringCellValue()
												.trim()));
								AppLog.info("serviceCycle>>>"
										+ aMRHeaderDetails.getServiceCycle());

							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_ROUTE_HEADER)) {

								aMRHeaderDetails.setRoute((null == row1
										.getCell(2).getStringCellValue() ? ""
										: row1.getCell(2).getStringCellValue()
												.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_AREA_HEADER)) {
								String areaCD = "";
								String areaDesc = "";
								if (null != row1.getCell(2)
										.getStringCellValue()
										&& !"".equalsIgnoreCase(row1.getCell(2)
												.getStringCellValue().trim())) {
									String combinedString = row1.getCell(2)
											.getStringCellValue().trim();
									areaCD = combinedString.substring(0,
											combinedString.indexOf("/")).trim();
									areaDesc = combinedString.substring(
											combinedString.indexOf("/")).trim();
								}
								aMRHeaderDetails.setAreaDesc(areaDesc);
								aMRHeaderDetails.setAreaCd(areaCD);

							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_METER_READ_DIARY_CODE_HEADER)) {

								aMRHeaderDetails
										.setMeterReadDiaryCode((null == row1
												.getCell(11)
												.getStringCellValue() ? ""
												: row1.getCell(11)
														.getStringCellValue()
														.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_START_DATE_HEADER)) {

								aMRHeaderDetails.setStartDate((null == row1
										.getCell(11).getStringCellValue() ? ""
										: row1.getCell(11).getStringCellValue()
												.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_ZRO_HEADER)) {

								aMRHeaderDetails.setZroDesc((null == row1
										.getCell(11).getStringCellValue() ? ""
										: row1.getCell(11).getStringCellValue()
												.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_ZONE_HEADER)) {
								aMRHeaderDetails.setZone((null == row1.getCell(
										11).getStringCellValue() ? "" : row1
										.getCell(11).getStringCellValue()
										.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_END_DATE_HEADER)) {

								aMRHeaderDetails.setEndDate((null == row1
										.getCell(15).getStringCellValue() ? ""
										: row1.getCell(15).getStringCellValue()
												.trim()));
							}
							if (cellData
									.equalsIgnoreCase(DJBConstants.AMR_MR_HEADER)) {

								aMRHeaderDetails.setMr((null == row1
										.getCell(15).getStringCellValue() ? ""
										: row1.getCell(15).getStringCellValue()
												.trim()));
							}
						}
					}
				}
			} catch (Exception eX) {
				AppLog.error("Exception : File Could Not Not Be Parsed : ", eX);
				throw new Exception();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					AppLog.error("Could Not Close The BufferedReader : ", e);
				}
			}
		}
		AppLog.end();
		return aMRHeaderDetails;
	}

	/**
	 * <p>
	 * This method is used to validate the Header Data of the Excel file
	 * </p>
	 * 
	 * @param excelFile
	 * @param amrHeaderRowNumber
	 * @param amrHeaderColumnCount
	 * @return
	 * @throws Exception
	 */
	public static final boolean isHeaderValid(File excelFile,
			String amrHeaderRowNumber, String amrHeaderColumnCount)
			throws Exception {
		AppLog.begin();
		// public static boolean flag = true;
		boolean isValidHeader = false;
		if (excelFile != null) {
			InputStream inputStream = null;
			try {
				AppLog
						.info("\n\n:::::::::::::::: INSIDE EXCEL VALIDATOR::::::::::::::::\n\n");
				inputStream = new BufferedInputStream(new FileInputStream(
						excelFile));
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				Iterator rows = sheet.rowIterator();

				/* Exempting Top Rows while reading Excel File If Needed Any */
				for (int i = 0; i < Integer.parseInt(amrHeaderRowNumber); i++) {
					rows.next();
				}

				for (int i = Integer.parseInt(amrHeaderRowNumber) - 1; i < Integer
						.parseInt(amrHeaderRowNumber); i++) {
					HSSFRow row1 = (HSSFRow) rows.next();
					Iterator cells1 = row1.cellIterator();
					String[] headerArray = new String[row1
							.getPhysicalNumberOfCells()];
					int j = 0;
					while (cells1.hasNext()) {
						HSSFCell cell = (HSSFCell) cells1.next();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						headerArray[j] = (null == cell.getStringCellValue() ? ""
								: cell.getStringCellValue().trim());
						j++;
					}
					AppLog.info(">>>>>>>>>>>>header array"
							+ Arrays.toString(headerArray));
					AppLog.info(">>>>>>>>>>>>header array columns ::"
							+ (headerArray).length);
					if (headerArray.length == Integer
							.parseInt(amrHeaderColumnCount)) {
						isValidHeader = true;
						AppLog
								.info("\n\n:::::::::::::::: AMR FILE HEADER VALIDATION SUCCESS::::::::::::::::\n\n");
					} else {
						isValidHeader = false;
						AppLog
								.info("\n\n:::::::::::::::: AMR FILE HEADER VALIDATION FAIL::::::::::::::::\n\n");
					}
				}
			} catch (Exception eX) {
				AppLog.error("Exception : File Could Not Not Be Parsed : ", eX);
				throw new Exception();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					AppLog.error("Could Not Close The BufferedReader : ", e);
				}
			}
		}
		AppLog.end();
		return isValidHeader;
	}

}
