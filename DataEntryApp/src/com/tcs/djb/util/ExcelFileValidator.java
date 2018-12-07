/************************************************************************************************************
 * @(#) ExcelFileValidator.java   26 May 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.File;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
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

/**
 * <p>
 * Excel file validator class for Data upload Screen as per JTrac DJB-4465 and
 * OpenProject-918.
 * </p>
 * 
 * @author Lovely(986018)
 * @since 26-05-2016
 */

public class ExcelFileValidator {
	/**
	 * <p>
	 * This method is for validating the first header row present in excel sheet
	 * uploaded as per JTrac DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */
	@SuppressWarnings("unchecked")
	public static final boolean isHeaderValid(File excelFile, int headerrRow)
			throws Exception {
		AppLog.begin();
		// public static boolean flag = true;
		boolean isValidHeaderColumn = false;
		boolean isValidHeaderName = false;
		boolean isColumnflag = false;
		Map<String, Object> session = ActionContext.getContext().getSession();
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
				for (int i = 0; i < headerrRow; i++) {
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
					isValidHeaderColumn = isValidateHeaderColumn(headerArray);
					isValidHeaderName = isValidateHeaderName(headerArray);
					if (isValidHeaderColumn && isValidHeaderName) {
						isColumnflag = true;
					}
				}
			} catch (Exception eX) {
				session.put("SERVER_MESSAGE", "<font color='red'><b>"
						+ DJBConstants.INVALID_FILE_TYPE_MSG + "</b></font>");
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
		return isColumnflag;
	}

	/**
	 * <p>
	 * This method is for validating number of columns present in uploaded excel
	 * sheet as per JTrac DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */

	public static boolean isValidateHeaderColumn(String[] headerArray) {
		AppLog.begin();
		int columnCount = 0;
		boolean flag = false;
		try {
			columnCount = DataUploadDAO
					.isValidateHeaderColumnCount(headerArray);
			/* column number is one reduced to exempt first column of the excel */
			if ((headerArray.length - 1) == columnCount) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return flag;
	}

	/**
	 * <p>
	 * This method is for validating name of the columns present in uploaded
	 * excel sheet with respect to present in database as per JTrac DJB-4465 and
	 * OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(986018)
	 * @since 20-04-2016
	 */
	public static boolean isValidateHeaderName(String[] headerArray) {
		AppLog.begin();
		ArrayList<String> columnList = new ArrayList<String>();
		boolean isNameflag = false;
		try {
			int i = 1;

			columnList = DataUploadDAO.isValidateHeaderColumnName(headerArray);
			for (String colValue : columnList) {
				if (colValue.equalsIgnoreCase(headerArray[i])) {

					isNameflag = true;
				} else {
					isNameflag = false;
				}
				i++;
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return isNameflag;
	}
}
