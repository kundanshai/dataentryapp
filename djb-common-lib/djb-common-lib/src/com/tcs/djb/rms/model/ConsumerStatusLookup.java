/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * ConsumerStatusLookup is used for status lookup of a consumer. Valid Consumer
 * status is defined as <code>INITIATED</code>, <code>DISCONNECTED</code>,
 * <code>METER_INSTALLED</code>, <code>REOPENING</code>, <code>CAT_CHANGE</code>
 * , <code>METER_REPLACEMENT_RECORD_FROZEN</code>, <code>RECORD_FROZEN</code>.
 * Default value is <code>INITIATED</code>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * 
 */
public class ConsumerStatusLookup {
	/**
	 * Bill Generated & Frozen.
	 */
	public static final ConsumerStatusLookup BILL_GENERATED_FROZEN = new ConsumerStatusLookup(
			110, "Bill Generated & Frozen");
	/**
	 * Change of Category.
	 */
	public static final ConsumerStatusLookup CAT_CHANGE = new ConsumerStatusLookup(
			50, "Change of Category");
	/**
	 * Data Processed Successfully.
	 */
	public static final ConsumerStatusLookup DATA_PROCESSED_SUCCESSFULLY = new ConsumerStatusLookup(
			130, "Data Processed Successfully");

	/**
	 * Disconnected.
	 */
	public static final ConsumerStatusLookup DISCONNECTED = new ConsumerStatusLookup(
			20, "Disconnected");
	/**
	 * Error in Physical MRD
	 */
	public static final ConsumerStatusLookup ERROR_IN_PHYSICAL_MRD = new ConsumerStatusLookup(
			0, "Error in Physical MRD");
	/**
	 * High Low Error.
	 */
	public static final ConsumerStatusLookup HI_LOW_ERROR = new ConsumerStatusLookup(
			80, "High Low Error");
	/**
	 * No Records Entered Yet.
	 */
	public static final ConsumerStatusLookup INITIATED = new ConsumerStatusLookup(
			10, "No Records Entered");

	/**
	 * Invalid Data.
	 */
	public static final ConsumerStatusLookup INVALID_DATA = new ConsumerStatusLookup(
			15, "Invalid Data");

	/**
	 * Meter Installed.
	 */
	public static final ConsumerStatusLookup METER_INSTALLED = new ConsumerStatusLookup(
			30, "Meter Installed");
	/**
	 * Meter Not Installed.
	 */
	public static final ConsumerStatusLookup METER_NOT_INSTALLED = new ConsumerStatusLookup(
			90, "Meter Not Installed");
	/**
	 * Meter Read Processed.
	 */
	public static final ConsumerStatusLookup METER_READ_PROCESSED = new ConsumerStatusLookup(
			100, "Meter Read Processed");
	/**
	 * Meter Reader Remark Updated.
	 */
	public static final ConsumerStatusLookup METER_READER_REMARK_UPDATED = new ConsumerStatusLookup(
			120, "Meter Reader Remark Updated");
	/**
	 * Meter Replacement Record Frozen for Processing.
	 */
	public static final ConsumerStatusLookup METER_REPLACEMENT_RECORD_FROZEN = new ConsumerStatusLookup(
			65, "Meter Replacement Record Frozen for Processing");
	/**
	 * Record Entered.
	 */
	public static final ConsumerStatusLookup RECORD_ENTERED = new ConsumerStatusLookup(
			60, "Record Entered");
	/**
	 * Record Frozen for Processing.
	 */
	public static final ConsumerStatusLookup RECORD_FROZEN = new ConsumerStatusLookup(
			70, "Record Frozen for Processing");

	/**
	 * Reopening.
	 */
	public static final ConsumerStatusLookup REOPENING = new ConsumerStatusLookup(
			40, "Reopening");

	/**
	 * Get Consumer Status For Status Code.
	 * 
	 * @param aStatCd
	 * @return
	 */
	public static ConsumerStatusLookup getConsumerStatusForStatCd(int aStatCd) {
		if (aStatCd == INITIATED.getStatusCode()) {
			return INITIATED;
		} else if (aStatCd == DISCONNECTED.getStatusCode()) {
			return DISCONNECTED;
		} else if (aStatCd == METER_INSTALLED.getStatusCode()) {
			return METER_INSTALLED;
		} else if (aStatCd == REOPENING.getStatusCode()) {
			return REOPENING;
		} else if (aStatCd == INVALID_DATA.getStatusCode()) {
			return INVALID_DATA;
		} else if (aStatCd == CAT_CHANGE.getStatusCode()) {
			return CAT_CHANGE;
		} else if (aStatCd == RECORD_ENTERED.getStatusCode()) {
			return RECORD_ENTERED;
		} else if (aStatCd == RECORD_FROZEN.getStatusCode()) {
			return RECORD_FROZEN;
		} else if (aStatCd == METER_REPLACEMENT_RECORD_FROZEN.getStatusCode()) {
			return METER_REPLACEMENT_RECORD_FROZEN;
		} else if (aStatCd == HI_LOW_ERROR.getStatusCode()) {
			return HI_LOW_ERROR;
		} else if (aStatCd == METER_NOT_INSTALLED.getStatusCode()) {
			return METER_NOT_INSTALLED;
		} else if (aStatCd == METER_READ_PROCESSED.getStatusCode()) {
			return METER_READ_PROCESSED;
		} else if (aStatCd == BILL_GENERATED_FROZEN.getStatusCode()) {
			return BILL_GENERATED_FROZEN;
		} else if (aStatCd == METER_READER_REMARK_UPDATED.getStatusCode()) {
			return METER_READER_REMARK_UPDATED;
		} else if (aStatCd == DATA_PROCESSED_SUCCESSFULLY.getStatusCode()) {
			return DATA_PROCESSED_SUCCESSFULLY;
		} else if (aStatCd == ERROR_IN_PHYSICAL_MRD.getStatusCode()) {
			return ERROR_IN_PHYSICAL_MRD;
		} else {
			return null;
		}
	}

	/**
	 * Status Code.
	 */
	private int statusCode = 0;
	/**
	 * Status Description.
	 */
	private String statusDescr = null;

	/**
	 * Constructor.
	 * 
	 * @param statusCode
	 * @param statusDescr
	 */
	public ConsumerStatusLookup(int statusCode, String statusDescr) {
		super();
		this.statusCode = statusCode;
		this.statusDescr = statusDescr;
	}

	/**
	 * Constructor.
	 * @param statusCode
	 */
	public ConsumerStatusLookup(String statusCode) {
		super();
		if (null != statusCode && "".equals(statusCode)) {
			this.statusCode = Integer.parseInt(statusCode);
		}
	}

	/**
	 * @return
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @return
	 */
	public String getStatusDescr() {
		return statusDescr;
	}

	/**
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param statusDescr
	 */
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
}
