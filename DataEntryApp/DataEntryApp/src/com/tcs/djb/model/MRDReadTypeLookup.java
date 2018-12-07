/************************************************************************************************************
 * @(#) MRDReadTypeLookup.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * Container for Meter Read Type Lookup Details.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 */
public class MRDReadTypeLookup {
private String averageReadType;
private String noBillingReadType;
private String provisionalReadType;
private String regularReadType;
public String getAverageReadType() {
	return averageReadType;
}
public String getNoBillingReadType() {
	return noBillingReadType;
}
public String getProvisionalReadType() {
	return provisionalReadType;
}
public String getRegularReadType() {
	return regularReadType;
}
public void setAverageReadType(String averageReadType) {
	this.averageReadType = averageReadType;
}
public void setNoBillingReadType(String noBillingReadType) {
	this.noBillingReadType = noBillingReadType;
}
public void setProvisionalReadType(String provisionalReadType) {
	this.provisionalReadType = provisionalReadType;
}
public void setRegularReadType(String regularReadType) {
	this.regularReadType = regularReadType;
}

}
