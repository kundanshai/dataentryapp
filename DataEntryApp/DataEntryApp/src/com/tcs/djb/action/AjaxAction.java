/************************************************************************************************************
 * @(#) AjaxAction.java   06 Sep 2012
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReplacementDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.ConsumerDetail;
import com.tcs.djb.model.MRDContainer;
import com.tcs.djb.model.MeterReadDetails;
import com.tcs.djb.model.MeterReplacementDetail;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for to save or reset meter read details
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @history 06/09/2012 Matiur Rahman Added functionality to reset current meter read
 *          details.
 * @history 18-12-2012 Matiur Rahman changed the logic for saving meter read by
 *          separating saving and flagging methods.
 * @history 18-03-2013 Matiur Rahman changed userName to userId.
 * @history 22-07-2013 Matiur Rahman Added validation to reset action for Meter
 *          Replacement cases i.e. If meter replacement Details exists in the
 *          System then Reset is not allowed from Data Entry Screen.
 */
@SuppressWarnings( { "serial", "deprecation" })
public class AjaxAction extends ActionSupport implements ServletResponseAware {
	private String billRound = null;
	private String consumerStatus = null;
	private String currentMeterRead = null;

	private InputStream inputStream;

	private String kno = null;
	private String meterReadDate = null;
	private String meterReadStatus = null;
	private String newAvgConsumption = null;
	private String newNoOfFloor = null;
	private String resetFlag = null;
	private String remarks = null;
	private HttpServletResponse response;

	private String seqNo = null;

	/**
	 * <p>
	 * Method to performs the tasks of ajax call
	 * </p>
	 * 
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	public String ajaxMethod() {
		AppLog.begin();
		// System.out.println("seqNo>>" + seqNo + ">>kno>>>> " + kno
		// + ">>billRound>>>" + billRound + ">>meterReadDate>>>"
		// + meterReadDate + ">>>meterReadStatus>>>" + meterReadStatus
		// + ">>>currentMeterRead>>>" + currentMeterRead
		// + ">>>newAvgConsumption>>>" + newAvgConsumption
		// + ">>newNoOfFloor>>" + newNoOfFloor + ">>>consumerStatus>>>"
		// + consumerStatus + ">>>resetFlag>>>" + resetFlag);

		try {
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("userId");
			if (null == userName || "".equals(userName)) {
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.login.expired"));
				AppLog.end();
				return "success";
			}
			/*
			 * START:Added by Matiur Rahman as per JTrack ID#DJB-1695.
			 */
			/*
			 * Check if meter Replacement details has been entered.
			 */
			MeterReplacementDetail meterReplacementDetail = new MeterReplacementDetail();
			meterReplacementDetail.setKno(kno);
			meterReplacementDetail.setBillRound(billRound);
			meterReplacementDetail = MeterReplacementDAO
					.getMeterReplacementDetailsForConsumer(meterReplacementDetail);
			/*
			 * If Meter Replacement Details Exists reset is not allowed.
			 */
			if (null != meterReplacementDetail
					&& null != meterReplacementDetail.getCreateDate()) {
				inputStream = new StringBufferInputStream(
						"<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action for &nbsp;KNO: "
								+ kno
								+ " is not allowed as Meter Replacement Details Entered on "
								+ meterReplacementDetail.getCreateDate()
								+ " by User ID:"
								+ meterReplacementDetail.getCreatedByID()
								+ ".<br/></font><font color='blue' size='2'>Please Reset/Update from Meter Replacement Details Entry Screen if Required.</b></font></div>");

			}
			/* END:Added by Matiur Rahman as per JTrack ID#DJB-1695. */
			else {
				if (!"Y".equals(resetFlag)) {
					int seqNoInt = 0;
					if (null != seqNo && !"".equals(seqNo)) {
						seqNoInt = Integer.parseInt(seqNo);
					}
					MRDContainer mrdContainer = (MRDContainer) session
							.get("mrdContainer");
					ArrayList<ConsumerDetail> consumerList = null;
					ConsumerDetail consumerDetail = null;
					if (null != mrdContainer) {
						consumerList = (ArrayList<ConsumerDetail>) mrdContainer
								.getConsumerList();
						if (null != consumerList && consumerList.size() > 0) {
							consumerDetail = (ConsumerDetail) consumerList
									.get(seqNoInt);
							consumerDetail.setUpdatedBy(userName);
							consumerDetail.setKno(kno);
							consumerDetail.setBillRound(billRound);
							consumerDetail.setConsumerStatus(consumerStatus);
							MeterReadDetails currentMeterReadDetails = (MeterReadDetails) consumerDetail
									.getCurrentMeterRead();
							currentMeterReadDetails
									.setMeterReadDate(meterReadDate);
							currentMeterReadDetails
									.setMeterStatus(meterReadStatus);
							if (null != meterReadStatus) {
								currentMeterReadDetails
										.setReadType(GetterDAO
												.readReadTypeByMeterReadREmark(meterReadStatus
														.trim()));
							} else {
								currentMeterReadDetails.setReadType(null);
							}
							currentMeterReadDetails
									.setRegRead(currentMeterRead);
							currentMeterReadDetails
									.setAvgRead(newAvgConsumption);
							currentMeterReadDetails.setNoOfFloor(newNoOfFloor);
							consumerDetail
									.setCurrentMeterRead(currentMeterReadDetails);
							currentMeterReadDetails.setRemarks(remarks);
							// System.out.println("In Ajax Action>>"+remarks);

							// System.out.println("ConsumerStatus>>"
							// + consumerDetail.getConsumerStatus()
							// + ">>MeterReadDate>>"
							// + currentMeterReadDetails.getMeterReadDate()
							// + ">>MeterStatus>>"
							// + currentMeterReadDetails.getMeterStatus()
							// + ">>RegRead>>"
							// + currentMeterReadDetails.getRegRead()
							// + ">>AvgRead>>"
							// + currentMeterReadDetails.getAvgRead()
							// + ">>>Kno>>" + consumerDetail.getKno()
							// + ">>BillRound>>"
							// + consumerDetail.getBillRound());
							consumerList.set(seqNoInt, consumerDetail);
							mrdContainer.setConsumerList(consumerList);
							session.remove("mrdContainer");
							session.put("mrdContainer", mrdContainer);

						}
					}
					session.remove("DataSaved");
					String valiadtionMsg = consumerDetail.validate();
					if (null != valiadtionMsg) {
						inputStream = new StringBufferInputStream(valiadtionMsg);
						// System.out.println("Data for KNO: " + kno
						// + " could not be Saved Successfully.");
						session.put("DataSaved", valiadtionMsg);
					} else {
						HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String, ConsumerDetail>();
						toUpdateMap.put("consumerDetail", consumerDetail);
						SetterDAO setterDAO = new SetterDAO();
						/**
						 * 8-12-2012 Matiur Rahman changed the logic for saving
						 * meter read by separating saving and flagging methods.
						 */
						// HashMap<String, String> returnMap = (HashMap<String,
						// String>) setterDAO
						// .saveMeterRead(toUpdateMap);
						// String isSuccess = (String)
						// returnMap.get("isSuccess");
						// String recordUpdatedMsg = (String) returnMap
						// .get("recordUpdatedMsg");
						/**
						 * 8-12-2012 Matiur Rahman added the logic for saving
						 * meter read by separating saving and flagging methods.
						 */
						HashMap<String, String> returnMap = null;
						if (null == consumerDetail.getConsumerStatus()
								|| ""
										.equals(consumerDetail
												.getConsumerStatus())
								|| "60".equals(consumerDetail
										.getConsumerStatus())) {
							returnMap = (HashMap<String, String>) setterDAO
									.saveMeterRead(toUpdateMap);
						} else {
							returnMap = (HashMap<String, String>) setterDAO
									.flagConsumerStatus(toUpdateMap);
						}
						String isSuccess = "N";
						String recordUpdatedMsg = null;
						if (null != returnMap) {
							isSuccess = (String) returnMap.get("isSuccess");
							recordUpdatedMsg = (String) returnMap
									.get("recordUpdatedMsg");
						}
						if ("Y".equals(isSuccess)) {
							inputStream = new StringBufferInputStream(
									"<div class='search-option' title='Server Message'><font color='green' size='2'><b>Last Action-><font color='blue'>Record No: "
											+ (seqNoInt + 1)
											+ ", Service Cycle Route Sequence No: "
											+ consumerDetail
													.getServiceCycleRouteSeq()
											+ "</font>&nbsp;(KNO: "
											+ kno
											+ ") Saved Successfully.</b></font></div>");
							session
									.put(
											"DataSaved",
											"Last Action-> Record No: "
													+ (seqNoInt + 1)
													+ ", Service Cycle Route Sequence No: "
													+ consumerDetail
															.getServiceCycleRouteSeq()
													+ "&nbsp;(KNO: " + kno
													+ ") Saved Successfully.");
							// System.out.println("Data for KNO: " + kno
							// + " was Saved Successfully.");
						} else {
							if ("Frozen".equals(recordUpdatedMsg)) {
								inputStream = new StringBufferInputStream(
										"<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action-> <font color='blue'>Record No: "
												+ (seqNoInt + 1)
												+ ", Service Cycle Route Sequence No: "
												+ consumerDetail
														.getServiceCycleRouteSeq()
												+ "</font>&nbsp;(KNO: "
												+ kno
												+ ") could not be Saved Successfully as Its already Frozen.</b></font></div>");
								session
										.put(
												"DataSaved",
												"Last Action-> Record No: "
														+ (seqNoInt + 1)
														+ ", Service Cycle Route Sequence No: "
														+ consumerDetail
																.getServiceCycleRouteSeq()
														+ "&nbsp;(KNO: "
														+ kno
														+ ")  could not be Saved Successfully as Its already Frozen.");
							} else {
								inputStream = new StringBufferInputStream(
										"<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action-> Record No: "
												+ (seqNoInt + 1)
												+ ", Service Cycle Route Sequence No: "
												+ consumerDetail
														.getServiceCycleRouteSeq()
												+ "&nbsp;(KNO: "
												+ kno
												+ ")  could not be Saved Successfully. Please Retry.</b></font></div>");
								session
										.put(
												"DataSaved",
												"Last Action-> Record No: "
														+ (seqNoInt + 1)
														+ ", Service Cycle Route Sequence No: "
														+ consumerDetail
																.getServiceCycleRouteSeq()
														+ "&nbsp;(KNO: "
														+ kno
														+ ") could not be Saved Successfully. Please Retry.");
							}
							// System.out.println("Data for KNO: " + kno
							// + " could not be Saved Successfully.");
						}
					}
				} else {
					int seqNoInt = 0;
					if (null != seqNo && !"".equals(seqNo)) {
						seqNoInt = Integer.parseInt(seqNo);
					}
					MRDContainer mrdContainer = (MRDContainer) session
							.get("mrdContainer");
					ArrayList<ConsumerDetail> consumerList = null;
					ConsumerDetail consumerDetail = null;
					if (null != mrdContainer) {
						consumerList = (ArrayList<ConsumerDetail>) mrdContainer
								.getConsumerList();
						if (null != consumerList && consumerList.size() > 0) {
							consumerDetail = (ConsumerDetail) consumerList
									.get(seqNoInt);
							consumerDetail.setUpdatedBy(userName);
							consumerDetail.setKno(kno);
							consumerDetail.setBillRound(billRound);

							consumerDetail.setConsumerStatus(consumerStatus);
							MeterReadDetails currentMeterReadDetails = (MeterReadDetails) consumerDetail
									.getCurrentMeterRead();
							currentMeterReadDetails.setMeterReadDate("");
							currentMeterReadDetails.setMeterStatus("");

							currentMeterReadDetails.setReadType(null);

							currentMeterReadDetails.setRegRead("");
							currentMeterReadDetails.setAvgRead("");
							consumerDetail
									.setCurrentMeterRead(currentMeterReadDetails);
							// System.out.println("ConsumerStatus>>"
							// + consumerDetail.getConsumerStatus()
							// + ">>MeterReadDate>>"
							// + currentMeterReadDetails.getMeterReadDate()
							// + ">>MeterStatus>>"
							// + currentMeterReadDetails.getMeterStatus()
							// + ">>RegRead>>"
							// + currentMeterReadDetails.getRegRead()
							// + ">>AvgRead>>"
							// + currentMeterReadDetails.getAvgRead()
							// + ">>>Kno>>" + consumerDetail.getKno()
							// + ">>BillRound>>"
							// + consumerDetail.getBillRound());
							consumerList.set(seqNoInt, consumerDetail);
							mrdContainer.setConsumerList(consumerList);
							session.remove("mrdContainer");
							session.put("mrdContainer", mrdContainer);

							HashMap<String, ConsumerDetail> toUpdateMap = new HashMap<String, ConsumerDetail>();
							toUpdateMap.put("consumerDetail", consumerDetail);
							SetterDAO setterDAO = new SetterDAO();
							HashMap<String, String> returnMap = (HashMap<String, String>) setterDAO
									.resetMeterRead(toUpdateMap);
							String isSuccess = (String) returnMap
									.get("isSuccess");

							if ("Y".equals(isSuccess)) {
								inputStream = new StringBufferInputStream(
										"<div class='search-option' title='Server Message'><font color='green' size='2'><b>Last Action-><font color='blue'>Record No: "
												+ (seqNoInt + 1)
												+ ", Service Cycle Route Sequence No: "
												+ consumerDetail
														.getServiceCycleRouteSeq()
												+ "</font>&nbsp;(KNO: "
												+ kno
												+ ") <font color='red' size='2'>Reset Successfully.</b></font></div>");

							} else {

								inputStream = new StringBufferInputStream(
										"<div class='search-option' title='Server Message'><font color='red' size='2'><b>Last Action-> Record No: "
												+ (seqNoInt + 1)
												+ ", Service Cycle Route Sequence No: "
												+ consumerDetail
														.getServiceCycleRouteSeq()
												+ "&nbsp;(KNO: "
												+ kno
												+ ")  could not be Reset Successfully. Please Retry.</b></font></div>");

							}
						}
					}
				}
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<div class='search-option' title='Server Message'><font color='red' size='2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>There Was an error at Server Side while saving data for KNO: "
							+ kno + ".</b></font></div>");
			// e.printStackTrace();
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * @return billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return consumerStatus
	 */
	public String getConsumerStatus() {
		return consumerStatus;
	}

	/**
	 * @return currentMeterRead
	 */
	public String getCurrentMeterRead() {
		return currentMeterRead;
	}

	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return kno
	 */
	public String getKno() {
		return kno;
	}

	/**
	 * @return meterReadDate
	 */
	public String getMeterReadDate() {
		return meterReadDate;
	}

	/**
	 * @return meterReadStatus
	 */
	public String getMeterReadStatus() {
		return meterReadStatus;
	}

	/**
	 * @return newAvgConsumption
	 */
	public String getNewAvgConsumption() {
		return newAvgConsumption;
	}

	/**
	 * @return the newNoOfFloor
	 */
	public String getNewNoOfFloor() {
		return newNoOfFloor;
	}

	/**
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return resetFlag
	 */
	public String getResetFlag() {
		return resetFlag;
	}

	/**
	 * @return seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return response
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}

	/**
	 * @param billRound
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param consumerStatus
	 */
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	/**
	 * @param currentMeterRead
	 */
	public void setCurrentMeterRead(String currentMeterRead) {
		this.currentMeterRead = currentMeterRead;
	}

	/**
	 * @param kno
	 */
	public void setKno(String kno) {
		this.kno = kno;
	}

	/**
	 * @param meterReadDate
	 */
	public void setMeterReadDate(String meterReadDate) {
		this.meterReadDate = meterReadDate;
	}

	/**
	 * @param meterReadStatus
	 */
	public void setMeterReadStatus(String meterReadStatus) {
		this.meterReadStatus = meterReadStatus;
	}

	/**
	 * @param newAvgConsumption
	 */
	public void setNewAvgConsumption(String newAvgConsumption) {
		this.newAvgConsumption = newAvgConsumption;
	}

	/**
	 * @param newNoOfFloor
	 *            the newNoOfFloor to set
	 */
	/**
	 * @param newNoOfFloor
	 */
	public void setNewNoOfFloor(String newNoOfFloor) {
		this.newNoOfFloor = newNoOfFloor;
	}

	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param resetFlag
	 */
	public void setResetFlag(String resetFlag) {
		this.resetFlag = resetFlag;
	}

	/**
	 * @param seqNo
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}