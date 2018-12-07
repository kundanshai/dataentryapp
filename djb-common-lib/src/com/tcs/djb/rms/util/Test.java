/**
 * 
 */
package com.tcs.djb.rms.util;

/**
 * @author 340132
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("DJBUtil Jar Executed SuccessFully.");

		System.out
				.println("CurrTimeBetweenTowTimeRange::09:00:00>>>>18:00:00:::"
						+ UtilityForAll.isCurrTimeBetweenTowTimeRange(
								"09:00:00", "18:00:00")
						+ ":::23:00>>>>14:00:::"
						+ UtilityForAll.isCurrTimeBetweenTowTimeRange("23:00",
								"14:00")
						+ ":::09>>>>18:::"
						+ UtilityForAll.isCurrTimeBetweenTowTimeRange("09",
								"18"));
		System.out.println("Current Day>>>>" + UtilityForAll.getCurrentDay());
		System.out.println("compareDates::31/01/2015>>>>>02/02/2015>>>>>"
				+ UtilityForAll.compareDates("31/01/2015", "02/02/2015")
				+ "::31/01/2015>>>>>02/01/2015>>>>>"
				+ UtilityForAll.compareDates("31/01/2015", "02/01/2015")
				+ "::31/01/2015>>>>>31/01/2015>>>>>"
				+ UtilityForAll.compareDates("31/01/2015", "31/01/2015"));

		System.out.println("\nSystemInfo>>>" + SystemInfo.info());

	}
}
