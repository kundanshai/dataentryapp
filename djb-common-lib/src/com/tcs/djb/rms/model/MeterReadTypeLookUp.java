/**
 * 
 */
package com.tcs.djb.rms.model;

/**
 * *
 * <p>
 * Contains meter read read type code i.e. the Meter Read status code
 * corresponding to read type such as averageReadType, noBillingReadType,
 * provisionalReadType, regularReadType.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services).
 * @see MeterReadType
 */
public class MeterReadTypeLookUp {
	/**
	 * Average Read Type Remark Code.
	 */
	private MeterReadType averageReadType;

	/**
	 * no Billing Read Type Remark Code.
	 */
	private MeterReadType noBillingReadType;

	/**
	 * provisional Read Type Remark Code.
	 */
	private MeterReadType provisionalReadType;

	/**
	 * regular Read Type Remark Code.
	 */
	private MeterReadType regularReadType;

	/**
	 * @return the averageReadType
	 */
	public MeterReadType getAverageReadType() {
		return averageReadType;
	}

	/**
	 * @return the noBillingReadType
	 */
	public MeterReadType getNoBillingReadType() {
		return noBillingReadType;
	}

	/**
	 * @return the provisionalReadType
	 */
	public MeterReadType getProvisionalReadType() {
		return provisionalReadType;
	}

	/**
	 * @return the regularReadType
	 */
	public MeterReadType getRegularReadType() {
		return regularReadType;
	}

	/**
	 * @param averageReadType
	 *            the averageReadType to set
	 */
	public void setAverageReadType(MeterReadType averageReadType) {
		this.averageReadType = averageReadType;
	}

	/**
	 * @param noBillingReadType
	 *            the noBillingReadType to set
	 */
	public void setNoBillingReadType(MeterReadType noBillingReadType) {
		this.noBillingReadType = noBillingReadType;
	}

	/**
	 * @param provisionalReadType
	 *            the provisionalReadType to set
	 */
	public void setProvisionalReadType(MeterReadType provisionalReadType) {
		this.provisionalReadType = provisionalReadType;
	}

	/**
	 * @param regularReadType
	 *            the regularReadType to set
	 */
	public void setRegularReadType(MeterReadType regularReadType) {
		this.regularReadType = regularReadType;
	}
}
