/************************************************************************************************************
 * @(#) CaptchaCache.java 1.0 23 Mar 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tcs.djb.rms.model.CaptchaDetails;
import com.tcs.djb.rms.model.SessionDetails;

/**
 * <P>
 * This class used to cache the values Captcha Details.
 * </P>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 23-03-2015
 */
public class CaptchaCache {
	/**
	 * 
	 */
	public static Map<String, CaptchaDetails> captchaMap = new HashMap<String, CaptchaDetails>();
	/**
	 * 
	 */
	public static boolean isRefreshing = false;
	/**
	 * 
	 */
	public static String lastUpdateTime;
	/**
	 * 
	 */
	public static int size = 0;

	/**
	 * <p>
	 * Refresh Captcha value.
	 * </p>
	 */
	public static synchronized void refresh(SessionDetails sessionDetails) {
		AppLog.begin(sessionDetails);
		try {
			long timeElapsed = UtilityForAll
					.getTimeElapsedSeconds(lastUpdateTime);
			AppLog.info("Last Refresh done before " + timeElapsed + " secs on "
					+ CaptchaCache.lastUpdateTime + ".");
			AppLog.debugInfo("Refreshing Captcha Cache Started");
			Map<String, CaptchaDetails> captchaDetailsMap = CaptchaGenerator
					.refreshCaptchaDetails(sessionDetails);
			if (null != captchaDetailsMap && !captchaDetailsMap.isEmpty()) {
				synchronized (captchaMap) {
					if (null != captchaMap) {
						captchaMap.clear();
					}
					captchaMap = captchaDetailsMap;
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					lastUpdateTime = dateFormat.format(date);
					size = captchaDetailsMap.size();
				}
				AppLog.info(sessionDetails,
						"Refreshing Captcha Cache Finished. Size>>" + size);
			} else {
				synchronized (captchaMap) {
					if (null != captchaMap) {
						captchaMap.clear();
					}
				}
				AppLog.info(sessionDetails,
						"Refreshing Captcha Cache Failed or No valid Data found.");
			}

		} catch (Exception e) {
			AppLog.fatal(sessionDetails, e);
		}
		AppLog.end(sessionDetails);
	}
}
