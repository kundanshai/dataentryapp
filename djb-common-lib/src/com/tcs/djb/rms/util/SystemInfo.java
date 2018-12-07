/************************************************************************************************************
 * @(#) SystemInfo.java 1.1 31 Mar 2014
 *  
 *
 *************************************************************************************************************/
package com.tcs.djb.rms.util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
/**
 * Utility class to get system Information.
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 31-03-2014
 * 
 */
public class SystemInfo {

	private static Runtime runtime = Runtime.getRuntime();

	/** Get system info.
	 * 
	 * @return system info
	 */
	public static String info() {
		StringBuilder sb = new StringBuilder();
		sb.append(osInfo());
		sb.append(";");
		sb.append(memInfo());
		sb.append(";");
		sb.append(diskInfo());
		sb.append(";");
		sb.append(printUsage());
		return sb.toString();
	}

	public static String osName() {
		return System.getProperty("os.name");
	}

	public static String osVersion() {
		return System.getProperty("os.version");
	}

	public static String osArch() {
		return System.getProperty("os.arch");
	}

	public long totalMem() {
		return Runtime.getRuntime().totalMemory();
	}

	public long usedMem() {
		return Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
	}

	/**
	 * Get memory info.
	 * 
	 * @return memory info
	 */
	public static String memInfo() {
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		sb.append("Free memory: ");
		sb.append(format.format(freeMemory / 1024));
		sb.append(";");
		sb.append("Allocated memory: ");
		sb.append(format.format(allocatedMemory / 1024));
		sb.append(";");
		sb.append("Max memory: ");
		sb.append(format.format(maxMemory / 1024));
		sb.append(";");
		sb.append("Total free memory: ");
		sb.append(format
				.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
		return sb.toString();

	}

	/**
	 * <p>
	 * Get OS Information.
	 * </p>
	 * @return OS information
	 */
	public static String osInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("OS: ");
		sb.append(osName());
		sb.append(";");
		sb.append("Version: ");
		sb.append(osVersion());
		sb.append(";");
		sb.append("Architecture: ");
		sb.append(osArch());
		sb.append(";");
		sb.append("Available processors (cores): ");
		sb.append(runtime.availableProcessors());
		return sb.toString();
	}

	/**
	 * Get Disk info.
	 * 
	 * @return disk info
	 */
	public static String diskInfo() {
		/* Get a list of all filesystem roots on this system */
		File[] roots = File.listRoots();
		StringBuilder sb = new StringBuilder();

		/* For each filesystem root, print some info */
		for (File root : roots) {
			sb.append("File system root: ");
			sb.append(root.getAbsolutePath());
			sb.append(";");
			sb.append("Total space (bytes): ");
			sb.append(root.getTotalSpace());
			sb.append(";");
			sb.append("Free space (bytes): ");
			sb.append(root.getFreeSpace());
			sb.append(";");
			sb.append("Usable space (bytes): ");
			sb.append(root.getUsableSpace());
		}
		return sb.toString();
	}

	/**
	 * Get All system information available in <code>OperatingSystemMXBean</code>.
	 */
	public static String printUsage() {
		StringBuilder sb = new StringBuilder();
		try {
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
					.getOperatingSystemMXBean();
			Object value = null;
			for (Method method : operatingSystemMXBean.getClass()
					.getDeclaredMethods()) {
				method.setAccessible(true);
				if (method.getName().startsWith("get")
						&& Modifier.isPublic(method.getModifiers())) {

					try {
						value = method.invoke(operatingSystemMXBean);
					} catch (Exception e) {
						value = e;
					} // try
					sb.append(method.getName().replace("get", "") + ": "
							+ value + ";");
					// System.out.println("Uasage:::" + method.getName() + " = "
					// + value);
				} // if
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}