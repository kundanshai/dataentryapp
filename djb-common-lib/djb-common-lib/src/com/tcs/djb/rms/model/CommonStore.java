/************************************************************************************************************
 * @(#) CaptchaDetails.java 1.0 19 June 2015
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Class for Captcha Details. It contains all the field used for captcha
 * validation.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 19-06-2015
 * 
 */
public class CommonStore {
	private static Map<String, Object> storage = null;

	/**
	 * @return the storage
	 */
	public static Object get(String key) {
		return null != CommonStore.storage ? CommonStore.storage.get(key)
				: null;
	}

	/**
	 * @param storage
	 *            the storage to set
	 */
	public static void set(String key, Object value) {
		if (null == storage) {
			CommonStore.storage = new HashMap<String, Object>();
		}
		CommonStore.storage.put(key, value);
	}
}
