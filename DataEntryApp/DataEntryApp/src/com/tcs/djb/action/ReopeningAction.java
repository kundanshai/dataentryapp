/************************************************************************************************************
 * @(#) ReopeningAction.java
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.DiconnectionDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.ConsumerStatusLookup;
import com.tcs.djb.model.DisconnectionDetails;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This is an action class for MRD Reopening screen
 * </p>
 * 
 * @author Matiur Rahman
 * 
 */
public class ReopeningAction extends ActionSupport implements
		ServletResponseAware {
	private String area;
	private String billRound;
	private String consumerCategory;
	private String consumerType;
	private String createDate;
	private String createdByID;
	private String currentMeterReadDate;
	private String currentMeterReadRemarkCode;
	private double currentMeterRegisterRead;
	private String hidAction;
	private InputStream inputStream;
	private String kno;
	private String lastUpdateDate;
	private String lastUpdatedByID;
	private String mrNo;
	private String name;
	private double newAverageConsumption;
	private HttpServletResponse response;
	private String seqNo;
	private String wcNo;
	private String zone;

	/**
	 * <p>
	 * For all ajax call in MRD Reopening screen
	 * </p>
	 * 
	 * @return string
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			String zoneCode = null;
			if (null != zone && !"".equals(zone.trim())) {
				zoneCode = zone.substring(zone.lastIndexOf('(') + 1, zone
						.lastIndexOf(')'));
			}
			System.out.println("hidAction>>" + hidAction + ">>seqNo>>" + seqNo
					+ ">>billRound>>>>" + billRound + ">>>zone>>" + zoneCode
					+ ">>mrNo>>>" + mrNo + ">>area>>>" + area + ">>>wcNo>>>"
					+ wcNo + ">>>kno>>>" + kno + ">>name>>" + name
					+ ">>consumerType>>" + consumerType
					+ ">>>currentMeterReadDate>>>" + currentMeterReadDate
					+ ">>>currentMeterRegisterRead>>>"
					+ currentMeterRegisterRead
					+ ">>currentMeterReadRemarkCode>>"
					+ currentMeterReadRemarkCode + ">>newAverageConsumption>>"
					+ newAverageConsumption);
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userName = (String) session.get("userName");
			if (null == userName || "".equals(userName)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.remove("DataSaved");
			if ("getConsumerDetailsByKNO".equalsIgnoreCase(hidAction)) {
				System.out.println("In getConsumerDetailsByKNO");
				GetterDAO getterDAO = new GetterDAO();
				MeterReplacementDetail meterReplacementDetail = getterDAO
						.getConsumerDetailsForMRByKNO(kno, billRound);
				if (null != meterReplacementDetail) {
					inputStream = new StringBufferInputStream("<CKNO>"
							+ meterReplacementDetail.getKno() + "</CKNO><ZONE>"
							+ meterReplacementDetail.getZone()
							+ "</ZONE><MRNO>"
							+ meterReplacementDetail.getMrNo()
							+ "</MRNO><AREA>"
							+ meterReplacementDetail.getArea()
							+ "</AREA><WCNO>"
							+ meterReplacementDetail.getWcNo()
							+ "</WCNO><NAME>"
							+ meterReplacementDetail.getName()
							+ "</NAME><SATP>"
							+ meterReplacementDetail.getSaType()
							+ "</SATP><CTYP>"
							+ meterReplacementDetail.getConsumerType()
							+ "</CTYP><CCAT>"
							+ meterReplacementDetail.getConsumerCategory()
							+ "</CCAT>");
					// System.out.println("::inputStream:1:" +
					// inputStream.toString());
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:Invalid KNO");
					// System.out.println("::inputStream:2:" +
					// inputStream.toString());
				}
			}
			if ("getConsumerDetailsByZMAW".equalsIgnoreCase(hidAction)) {
				System.out.println("In getConsumerDetailsByZMAW");
				String searcString = (this.area).substring((this.area)
						.indexOf("-(") + 2, (this.area).length() - 1);
				String searchMRNo = searcString.substring(searcString
						.indexOf('-') + 1, searcString.lastIndexOf('-'));
				String searchArea = searcString.substring(0, searcString
						.indexOf('-'));
				GetterDAO getterDAO = new GetterDAO();
				MeterReplacementDetail meterReplacementDetail = getterDAO
						.getConsumerDetailsForMRByZMAW(billRound, zoneCode,
								searchMRNo, searchArea, wcNo);

				// System.out.println("isSuccess>>>" + isSuccess);
				if (null != meterReplacementDetail) {
					inputStream = new StringBufferInputStream("<CKNO>"
							+ meterReplacementDetail.getKno() + "</CKNO><ZONE>"
							+ meterReplacementDetail.getZone()
							+ "</ZONE><MRNO>"
							+ meterReplacementDetail.getMrNo()
							+ "</MRNO><AREA>"
							+ meterReplacementDetail.getArea()
							+ "</AREA><WCNO>"
							+ meterReplacementDetail.getWcNo()
							+ "</WCNO><NAME>"
							+ meterReplacementDetail.getName()
							+ "</NAME><SATP>"
							+ meterReplacementDetail.getSaType()
							+ "</SATP><CTYP>"
							+ meterReplacementDetail.getConsumerType()
							+ "</CTYP><CCAT>"
							+ meterReplacementDetail.getConsumerCategory()
							+ "</CCAT>");
					// System.out.println("::inputStream:1:" +
					// inputStream.toString());
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:Invalid Conbination");
					// System.out.println("::inputStream:2:" +
					// inputStream.toString());
				}
			}
			if ("saveDisconDetails".equalsIgnoreCase(hidAction)) {
				boolean isValid = true;
				System.out.println("In saveDisconDetails");
				String mrCode = mrNo;
				String areaCode = area;
				if ((this.area).indexOf("-(") > -1) {
					String searcString = (this.area).substring((this.area)
							.indexOf("-(") + 2, (this.area).length() - 1);
					mrCode = searcString.substring(
							searcString.indexOf('-') + 1, searcString
									.lastIndexOf('-'));
					areaCode = searcString.substring(0, searcString
							.indexOf('-'));
				}
				DisconnectionDetails disconnectionDetails = new DisconnectionDetails();
				disconnectionDetails.setBillRound(billRound);
				disconnectionDetails.setZone(zoneCode);
				disconnectionDetails.setMrNo(mrCode);
				disconnectionDetails.setArea(areaCode);
				disconnectionDetails.setWcNo(wcNo);
				disconnectionDetails.setKno(kno);
				disconnectionDetails.setName(name);
				disconnectionDetails.setConsumerType(consumerType);
				disconnectionDetails.setConsumerCategory(consumerCategory);
				disconnectionDetails
						.setCurrentMeterReadDate(currentMeterReadDate);
				disconnectionDetails
						.setCurrentMeterReadRemarkCode(currentMeterReadRemarkCode);
				disconnectionDetails
						.setCurrentMeterRegisterRead(currentMeterRegisterRead);
				disconnectionDetails
						.setNewAverageConsumption(newAverageConsumption);
				disconnectionDetails.setCreatedByID(userName);
				if (isValid) {
					DiconnectionDAO diconnectionDAO = new DiconnectionDAO();
					boolean isSuccess = diconnectionDAO
							.saveDisconnectionDetail(disconnectionDetails);

					System.out.println("isSuccess>>>" + isSuccess);
					if (isSuccess) {
						if (null != seqNo && !"".equals(seqNo)) {
							MRDContainer mrdContainer = (MRDContainer) session
									.get("mrdContainer");
							ArrayList<ConsumerDetail> consumerList = null;
							ConsumerDetail consumerDetail = null;
							if (null != mrdContainer) {
								consumerList = (ArrayList<ConsumerDetail>) mrdContainer
										.getConsumerList();
								if (null != consumerList
										&& consumerList.size() > 0) {
									consumerDetail = (ConsumerDetail) consumerList
											.get(Integer.parseInt(seqNo));
									consumerDetail.setUpdatedBy(userName);
									consumerDetail.setKno(kno);
									consumerDetail.setBillRound(billRound);
									consumerDetail
											.setConsumerStatus(Integer
													.toString(ConsumerStatusLookup.DISCONNECTED
															.getStatusCode()));
									consumerList.set(Integer.parseInt(seqNo),
											consumerDetail);
									mrdContainer.setConsumerList(consumerList);
									session.remove("mrdContainer");
									session.put("mrdContainer", mrdContainer);
								}
							}
						}
						inputStream = new StringBufferInputStream(
								"<div class='search-option' title='Server Message'><font color='green' size='2'><b>Meter Replacement Details for KNO: "
										+ kno
										+ " Saved Successfully.</b></font></div>");
						// System.out.println("::inputStream:1:" +
						// inputStream.toString());
					} else {
						inputStream = new StringBufferInputStream(
								"ERROR:There was a problem at the server end, Please try Again!");
						// System.out.println("::inputStream:2:" +
						// inputStream.toString());
					}
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:There was a problem at the server end as some Mandatory/Default values were missing!");
				}
			}
			if ("resetMRDetails".equalsIgnoreCase(hidAction)) {
				boolean isValid = true;
				// System.out.println("In resetMRDetails");
				MeterReplacementDetail meterReplacementDetail = new MeterReplacementDetail();
				meterReplacementDetail.setBillRound(billRound);
				meterReplacementDetail.setWcNo(wcNo);
				meterReplacementDetail.setKno(kno);
				meterReplacementDetail.setName(name);
				meterReplacementDetail.setCreatedByID(userName);
				if (isValid) {
					SetterDAO setterDAO = new SetterDAO();
					boolean isSuccess = setterDAO
							.resetMeterReplacementDetail(meterReplacementDetail);
					// System.out.println("isSuccess>>>" + isSuccess);
					if (isSuccess) {
						if (null != seqNo && !"".equals(seqNo)) {
							MRDContainer mrdContainer = (MRDContainer) session
									.get("mrdContainer");
							ArrayList<ConsumerDetail> consumerList = null;
							ConsumerDetail consumerDetail = null;
							if (null != mrdContainer) {
								consumerList = (ArrayList<ConsumerDetail>) mrdContainer
										.getConsumerList();
								if (null != consumerList
										&& consumerList.size() > 0) {
									consumerDetail = (ConsumerDetail) consumerList
											.get(Integer.parseInt(seqNo));
									consumerDetail.setUpdatedBy(userName);
									consumerDetail.setKno(kno);
									consumerDetail.setBillRound(billRound);
									consumerDetail
											.setConsumerStatus(Integer
													.toString(ConsumerStatusLookup.INITIATED
															.getStatusCode()));
									consumerList.set(Integer.parseInt(seqNo),
											consumerDetail);
									mrdContainer.setConsumerList(consumerList);
									session.remove("mrdContainer");
									session.put("mrdContainer", mrdContainer);
								}
							}
						}
						inputStream = new StringBufferInputStream(
								"<div class='search-option' title='Server Message'><font color='green' size='2'><b>Meter Replacement Details for KNO: "
										+ kno
										+ " <font color='red'>Reset Successfully.</font></b></font></div>");
					} else {
						inputStream = new StringBufferInputStream(
								"ERROR:There was a problem at the server end, Please try Again!!");
					}
				} else {
					inputStream = new StringBufferInputStream(
							"ERROR:There was a problem at the server end as some Mandatory/Default values were missing!");
				}
			}
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"ERROR:Meter Replacement Details for KNO: " + kno
							+ "Could not be Saved Successfully.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);

		}
		// System.out.println("inputStream:3:" + inputStream.toString());
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		return "success";
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @return the consumerCategory
	 */
	public String getConsumerCategory() {
		return consumerCategory;
	}

	/**
	 * @param consumerCategory
	 *            the consumerCategory to set
	 */
	public void setConsumerCategory(String consumerCategory) {
		this.consumerCategory = consumerCategory;
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * @param consumerType
	 *            the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createdByID
	 */
	public String getCreatedByID() {
		return createdByID;
	}

	/**
	 * @param createdByID
	 *            the createdByID to set
	 */
	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}

	/**
	 * @return the currentMeterReadDate
	 */
	public String getCurrentMeterReadDate() {
		return currentMeterReadDate;
	}

	/**
	 * @param currentMeterReadDate
	 *            the currentMeterReadDate to set
	 */
	public void setCurrentMeterReadDate(String currentMeterReadDate) {
		this.currentMeterReadDate = currentMeterReadDate;
	}

	/**
	 * @return the currentMeterReadRemarkCode
	 */
	public String getCurrentMeterReadRemarkCode() {
		return currentMeterReadRemarkCode;
	}

	/**
	 * @param currentMeterReadRemarkCode
	 *            the currentMeterReadRemarkCode to set
	 */
	public void setCurrentMeterReadRemarkCode(String currentMeterReadRemarkCode) {
		this.currentMeterReadRemarkCode = currentMeterReadRemarkCode;
	}

	/**
	 * @return the currentMeterRegisterRead
	 */
	public double getCurrentMeterRegisterRead() {
		return currentMeterRegisterRead;
	}

	/**
	 * @param currentMeterRegisterRead
	 *            the currentMeterRegisterRead to set
	 */
	public void setCurrentMeterRegisterRead(double currentMeterRegisterRead) {
		this.currentMeterRegisterRead = currentMeterRegisterRead;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @param kno
	 *            the kno to set
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the lastUpdatedByID
	 */
	public String getLastUpdatedByID() {
		return lastUpdatedByID;
	}

	/**
	 * @param lastUpdatedByID
	 *            the lastUpdatedByID to set
	 */
	public void setLastUpdatedByID(String lastUpdatedByID) {
		this.lastUpdatedByID = lastUpdatedByID;
	}

	/**
	 * @return the mrNo
	 */
	public String getMrNo() {
		return mrNo;
	}

	/**
	 * @param mrNo
	 *            the mrNo to set
	 */
	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the newAverageConsumption
	 */
	public double getNewAverageConsumption() {
		return newAverageConsumption;
	}

	/**
	 * @param newAverageConsumption
	 *            the newAverageConsumption to set
	 */
	public void setNewAverageConsumption(double newAverageConsumption) {
		this.newAverageConsumption = newAverageConsumption;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the wcNo
	 */
	public String getWcNo() {
		return wcNo;
	}

	/**
	 * @param wcNo
	 *            the wcNo to set
	 */
	public void setWcNo(String wcNo) {
		this.wcNo = wcNo;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
}
