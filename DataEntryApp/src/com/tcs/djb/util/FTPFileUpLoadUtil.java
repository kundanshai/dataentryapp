/************************************************************************************************************
 * @(#) FTPFileUpLoadUtil.java   09 Oct 2014
 *
 *************************************************************************************************************/
package com.tcs.djb.util;

import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.tcs.djb.constants.DJBConstants;

/**
 * <p>
 * Util class to upload file at FTP Server.
 * </p>
 * 
 * @author Reshma Srivastava(Tata Consultancy Services)
 * 
 */
public class FTPFileUpLoadUtil {

	/**
	 * <p>
	 * Method to upload file at FTP Server with location & credentials provided.
	 * </p>
	 * 
	 * @param ftpLocation
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param versionNo
	 * @param browseFilePath
	 * @return
	 */
	public static boolean ftpFileUpLoad(String ftpLocation, String ftpUserName,
			String ftpPassword, String versionNo, String browseFilePath) {
		boolean isUpLoaded = false;
		if (null != versionNo && !"".equalsIgnoreCase(versionNo.trim())
				&& null != ftpUserName
				&& !"".equalsIgnoreCase(ftpUserName.trim())
				&& null != ftpPassword
				&& !"".equalsIgnoreCase(ftpPassword.trim())
				&& null != ftpLocation
				&& !"".equalsIgnoreCase(ftpLocation.trim())
				&& null != browseFilePath
				&& !"".equalsIgnoreCase(browseFilePath.trim())) {
			FTPClient ftpClient = new FTPClient();
			try {
				ftpClient.connect(ftpLocation);
				if (!ftpClient.login(ftpUserName, ftpPassword)) {
					AppLog.info("Login failed " + ftpClient.getReplyCode());
					ftpClient.logout();
					return isUpLoaded;
				}
				int reply = ftpClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					AppLog.info("Disconnected " + ftpClient.getReplyCode());
					ftpClient.disconnect();
					return isUpLoaded;
				}
				AppLog.info(" FTP Server Connected Successfully "
						+ ftpClient.getReplyCode());
				ftpClient.changeWorkingDirectory(DJBConstants.FTP_HOST_DIR);
				InputStream in = new FileInputStream(browseFilePath);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				String filename = versionNo + DJBConstants.FTP_UPLOAD_FILE_EXT;
				boolean status = ftpClient.storeFile(filename, in);
				AppLog.info("uploading status " + status + " reply string "
						+ ftpClient.getReplyString());
				if (status) {
					isUpLoaded = true;
					AppLog.info(" file uploaded successfully");
				}
				in.close();
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (Exception ex) {
				AppLog.error(ex);
			}
		}
		return isUpLoaded;
	}
}
