/************************************************************************************************************
 * @(#) NewConnFileEntryDetails.java   20 Jan 2016
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.model;

/**
 * <p>
 * <code>NewConnFileEntryDetails</code> is the container class which contains
 * all the jsp variables specific for New Connection File Entry
 * </p>
 * 
 * @since 20-JAN-2016
 * @author Rajib Hazarika (Tata Consultancy Services)
 */
public class NewConnFileEntryDetails {
	/**
	 * variable to Store data entered by.
	 */
	private String createdBy;
	/**
	 * variable to Store emailId.
	 */
	private String emailId;
	/**
	 * variable to Store File No.
	 */
	private String fileName;
	/**
	 * variable to Store first Name.
	 */
	private String firstName;
	/**
	 * variable to Store House No.
	 */
	private String hnoAdd;
	/**
	 * variable to Store proof of identity type CD.
	 */
	private String identityProof;
	/**
	 * variable to Store JJR colony Flg.
	 */
	private String jjr;

	/**
	 * variable to Store last Name.
	 */
	private String lastName;
	/**
	 * variable to Store Locality.
	 */
	private String locaAdd;

	/**
	 * variable to Store middle Name.
	 */
	private String middleName;

	/**
	 * variable to Store phone.
	 */
	private String phone;

	/**
	 * variable to Store pin code.
	 */
	private String pinAdd;
	/**
	 * variable to Store Proof of Residence CD.
	 */
	private String proofOfRes;
	/**
	 * variable to Store Property Type Doc CD.
	 */
	private String propertyDoc;
	/**
	 * variable to Store File No.
	 */
	private String roadNo;
	/**
	 * variable to Store Logged Status.
	 */
	private String status;

	/**
	 * variable to Store Sub Colony .
	 */
	private String subColAdd;

	/**
	 * variable to Store Sub Loc1.
	 */
	private String subLoc1Add;

	/**
	 * variable to Store Sub Loc2.
	 */
	private String subLoc2Add;

	/**
	 * variable to Store Sub Locality.
	 */
	private String subLocAdd;

	/**
	 * variable to Store type Of App.
	 */
	private String typeOfApp;
	/**
	 * variable to Store Village Address.
	 */
	private String vilAdd;

	public String getCreatedBy() {
		return createdBy;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFirstName() {
		return firstName;
	}

	

	public String getIdentityProof() {
		return identityProof;
	}

	public String getJjr() {
		return jjr;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLocaAdd() {
		return locaAdd;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getPhone() {
		return phone;
	}

	public String getPinAdd() {
		return pinAdd;
	}

	public String getProofOfRes() {
		return proofOfRes;
	}

	public String getPropertyDoc() {
		return propertyDoc;
	}

	public String getRoadNo() {
		return roadNo;
	}

	public String getStatus() {
		return status;
	}

	public String getSubColAdd() {
		return subColAdd;
	}

	public String getSubLoc1Add() {
		return subLoc1Add;
	}

	public String getSubLoc2Add() {
		return subLoc2Add;
	}

	public String getSubLocAdd() {
		return subLocAdd;
	}

	public String getTypeOfApp() {
		return typeOfApp;
	}

	public String getVilAdd() {
		return vilAdd;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setIdentityProof(String identityProof) {
		this.identityProof = identityProof;
	}

	public void setJjr(String jjr) {
		this.jjr = jjr;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLocaAdd(String locaAdd) {
		this.locaAdd = locaAdd;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPinAdd(String pinAdd) {
		this.pinAdd = pinAdd;
	}

	public void setProofOfRes(String proofOfRes) {
		this.proofOfRes = proofOfRes;
	}

	public void setPropertyDoc(String propertyDoc) {
		this.propertyDoc = propertyDoc;
	}

	public void setRoadNo(String roadNo) {
		this.roadNo = roadNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubColAdd(String subColAdd) {
		this.subColAdd = subColAdd;
	}

	public void setSubLoc1Add(String subLoc1Add) {
		this.subLoc1Add = subLoc1Add;
	}

	public void setSubLoc2Add(String subLoc2Add) {
		this.subLoc2Add = subLoc2Add;
	}

	public void setSubLocAdd(String subLocAdd) {
		this.subLocAdd = subLocAdd;
	}

	public void setTypeOfApp(String typeOfApp) {
		this.typeOfApp = typeOfApp;
	}

	public void setVilAdd(String vilAdd) {
		this.vilAdd = vilAdd;
	}

	@Override
	public String toString() {
		return "NewConnFileEntryDetails [emailId=" + emailId + ", fileName="
				+ fileName + ", firstName=" + firstName + ", hNoAdd=" + hnoAdd
				+ ", identityProof=" + identityProof + ", jjr=" + jjr
				+ ", lastName=" + lastName + ", locaAdd=" + locaAdd
				+ ", middleName=" + middleName + ", phone=" + phone
				+ ", pinAdd=" + pinAdd + ", proofOfRes=" + proofOfRes
				+ ", propertyDoc=" + propertyDoc + ", subColAdd=" + subColAdd
				+ ", subLoc1Add=" + subLoc1Add + ", subLoc2Add=" + subLoc2Add
				+ ", subLocAdd=" + subLocAdd + ", typeOfApp=" + typeOfApp
				+ ", vilAdd=" + vilAdd + "]";
	}

	public String getHnoAdd() {
		return hnoAdd;
	}

	public void setHnoAdd(String hnoAdd) {
		this.hnoAdd = hnoAdd;
	}

}
