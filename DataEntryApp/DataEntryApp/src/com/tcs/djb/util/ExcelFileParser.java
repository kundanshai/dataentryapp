/************************************************************************************************************
 * @(#) ExcelFileParser.java   20 Apr 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.DataUploadDAO;
import com.tcs.djb.util.AppLog;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * <p>
 * This class is used to parse the excel file read it and insert in database row
 * by row as per JTrac DJB-4465 and OpenProject-918.
 * <p>
 * 
 * @Author Lovely(Tata Consultancy Services)
 * @since 20-04-2016
 */

public class ExcelFileParser {
	/** The logger is used in this class to log any info, debug or error. */
	/**
	 * <p>
	 * Parses the {@code csvFile} taken as parameter.
	 * </p>
	 * 
	 * @param excelFile
	 * @param rowsToExempt
	 *            No of Top rows to Extemp while reading the Excel
	 * @return Returns an {@code ArrayList} of {@code String[]} containing the
	 *         header and the records
	 * @throws InvalidFormatException
	 */
	@SuppressWarnings("unchecked")
	public static synchronized int parse(File excelFile, int rowsToExempt,
			String sourceFileName) throws Exception {
		AppLog.begin();
		int rowInsertCount = 0;
		if (excelFile != null) {
			InputStream inputStream = null;
			try {
				AppLog
						.info("\n\n:::::::::::::::: INSIDE EXCEL PARSER ::::::::::::::::\n\n");
				inputStream = new BufferedInputStream(new FileInputStream(
						excelFile));
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				Iterator rows = sheet.rowIterator();

				/* Exempting Top Rows while reading Excel File If Needed Any */
				for (int i = 0; i < rowsToExempt; i++) {
					rows.next();
				}
				/* Reading Excel Rows cell by cell */
				int rowReadCount = 0;

				while (rows.hasNext()) {
					/*
					 * HSSFRow row1 = (HSSFRow) rows.next(); Iterator cells1 =
					 * row1.cellIterator(); String[] dataArray = new String[row1
					 * .getPhysicalNumberOfCells()]; String[] emptyArray = new
					 * String[row1 .getPhysicalNumberOfCells()]; int i = 0;
					 * while (cells1.hasNext()) { HSSFCell cell = (HSSFCell)
					 * cells1.next(); cell.setCellType(Cell.CELL_TYPE_STRING);
					 * dataArray[i] = (null == cell.getStringCellValue() ? "" :
					 * cell.getStringCellValue().trim()); i++;
					 */

					HSSFRow row1 = (HSSFRow) rows.next();
					String[] dataArray = new String[DJBConstants.MAX_COLUMN_COUNT];
					String[] emptyArray = new String[DJBConstants.MAX_COLUMN_COUNT];
					for (int cn = 0; cn < DJBConstants.MAX_COLUMN_COUNT; cn++) {
						Cell cell = row1.getCell(cn, Row.CREATE_NULL_AS_BLANK);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (null != cell.getStringCellValue()) {
							dataArray[cn] = cell.getStringCellValue().trim();
						} else {
							dataArray[cn] = " ";
						}

					}
					if (!Arrays.toString(dataArray).equalsIgnoreCase(
							Arrays.toString(emptyArray))
							&& dataArray.length > 0) {

						rowReadCount++;
						rowInsertCount = rowInsertCount
								+ DataUploadDAO.insertData(dataArray,
										sourceFileName, rowReadCount);
					}
				}
				AppLog
						.info(">>>>>>>>NUMBER OF ROWS READ >>>>>>"
								+ rowReadCount);
				AppLog.info(">>>>>>>>NUMBER OF ROWS INSERTED >>>>>>"
						+ rowInsertCount);
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
		return rowInsertCount;
	}
}
