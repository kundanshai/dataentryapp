/************************************************************************************************************
 * @(#) ExcelToDBTable.java   20 Jun 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.tcs.djb.model.ExcelToDBTableResponse;

/**
 * <p>
 * This is an Util class which reads the input excel file and call the input dao
 * method for populating the records into database table one row at a time.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
 * @history : Atanu on 05-07-2016 modified the logic to read the cell upto
 *          specified column count as per open project 1202,jTrac : DJB-4464.
 */
public class ExcelToDBTable {

	/**
	 * <p>
	 * This method reads the input excel file and call the input dao method of
	 * given dao class by taking the row data from excel. It reads one row at a
	 * time.
	 * </p>
	 * 
	 * @param excelFile
	 * @param rowsToExempt
	 * @param object
	 * @param daoClassName
	 * @param daoMethodName
	 * @param headerID
	 * @param maxColumnCount
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static ExcelToDBTableResponse parse(File excelFile,
			int rowsToExempt, Object object, String daoClassName,
			String daoMethodName, int headerID, int maxColumnCount) throws Exception {
		AppLog.begin();
		ExcelToDBTableResponse excelToDBTableResponse = null;
		ExcelToDBTableResponse returnExcelToDBTableResponse = null;
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
				StringBuffer expSb = new StringBuffer();
				/* Reading Excel Rows cell by cell */
				while (rows.hasNext()) {
					HSSFRow row1 = (HSSFRow) rows.next();
					/*Start : Atanu Edited the code logic to read all cells upto the param column count as per jTrac DJB-4464,open project#1202*/
					String[] dataArray = new String[maxColumnCount];
					String[] emptyArray = new String[maxColumnCount];
					for (int cn = 0; cn < maxColumnCount; cn++) {
						Cell cell = row1.getCell(cn, Row.CREATE_NULL_AS_BLANK);
						if(null != cell.getStringCellValue()){
							dataArray[cn] = cell.getStringCellValue().trim();
						}else{
							dataArray[cn] = " ";
						}
						
					}
					/*Iterator cells1 = row1.cellIterator();
					String[] dataArray = new String[row1
							.getPhysicalNumberOfCells()];

					String[] emptyArray = new String[row1
							.getPhysicalNumberOfCells()];
					int i = 0;
					while (cells1.hasNext()) {
						HSSFCell cell = (HSSFCell) cells1.next();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						dataArray[i] = (null == cell.getStringCellValue() ? ""
								: cell.getStringCellValue().trim());
						i++;
					}
					 */
					/*End : Atanu Edited the code logic to read all cells upto the param column count as per jTrac DJB-4464,open project#1202*/
					if (!Arrays.toString(dataArray).equalsIgnoreCase(
							Arrays.toString(emptyArray))
							&& dataArray.length > 0) {
						Class<?> myClass = null;
						myClass = Class.forName(daoClassName);
						AppLog.info("myClass>>" + myClass);
						Method methodToBeInvoked = null;
						methodToBeInvoked = myClass.getMethod(daoMethodName,
								new Class[] { String[].class, Integer.class });
						AppLog.info("methodToBeInvoked>>"
								+ methodToBeInvoked.getName());
						excelToDBTableResponse = (ExcelToDBTableResponse) methodToBeInvoked
								.invoke(object, dataArray, headerID);
						/*
						 * AppLog.debug("excelToDBTableResponse>>"+excelToDBTableResponse
						 * );
						 */
						if (null != excelToDBTableResponse) {
							if (excelToDBTableResponse.getNoOfRecord() > 0) {
								rowInsertCount += excelToDBTableResponse
										.getNoOfRecord();
							}
							if (null != excelToDBTableResponse.getThrowable()) {
								expSb.append(excelToDBTableResponse
										.getThrowable().getLocalizedMessage()
										+ "\n");
							}
						}
					}
				}
				AppLog.info("Total rowInsertCount>>" + rowInsertCount
						+ "\nexpSb>>>" + expSb.toString());
				/* AppLog.debug("expSb>>>" + expSb.toString()); */
				if (expSb.toString().length() > 0) {
					String successMsg = "";
					returnExcelToDBTableResponse = new ExcelToDBTableResponse();
					if (rowInsertCount > 0) {
						successMsg = rowInsertCount
								+ " Records Processed Succesfuly with following error:\n";
						returnExcelToDBTableResponse
								.setNoOfRecord(rowInsertCount);
						returnExcelToDBTableResponse
								.setThrowable(new Exception(expSb.toString()));
					}
					returnExcelToDBTableResponse.setMsg(successMsg + "\n"
							+ expSb.toString());
				} else {
					returnExcelToDBTableResponse = new ExcelToDBTableResponse();
					returnExcelToDBTableResponse.setNoOfRecord(rowInsertCount);
				}
			} catch (Exception e) {
				AppLog.error(e);
				throw e;
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
		/* AppLog.debug(excelToDBTableResponse); */
		AppLog.end();
		return returnExcelToDBTableResponse;
	}

}
