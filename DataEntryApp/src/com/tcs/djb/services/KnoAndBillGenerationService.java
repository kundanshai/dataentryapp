/************************************************************************************************************
 * @(#) KnoAndBillGenerationService.java   15 Feb 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.services;

import com.tcs.djb.model.KnoGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.util.XAIHTTPCall;

/**
 * <p>
 * This class is used to call KNO Generation CCB service providing the required
 * parameters for the service
 * </p>
 * 
 * @author Rajib Hazarika (Tata Consultancy Services)
 * @since 15-FEB-2016
 * @history Edited by Arnab Nandy (1227971) on 08-03-2016 in
 *          {@link #generateKno(String, String, KnoGenerationDetails))} method
 *          as per JTrac DJB-4398
 *@history Edited by Sanjay Das(1033846) on 17-03-2016 in
 *          {@link #generateKno(String, String, KnoGenerationDetails))} method
 *          as per JTrac DJB-4418
 */
public class KnoAndBillGenerationService implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * This function will return true if KNO is successfully generated through
	 * CCB Service Call , otherwise it will return False
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 15-FEB-2016
	 * @param kno
	 * @param authCookie
	 * @return
	 */

	public static String generateKno(String kno, String authCookie,
			KnoGenerationDetails knoGenerationDetails) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String acctId = "";
		try {
			if (null != knoGenerationDetails) {
				xml
						.append(" <soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_GNRTKNOXAI.xsd'> ");
				xml.append(" <soapenv:Header/> ");
				xml.append(" <soapenv:Body> ");
				xml.append(" <cm:CM_GNRTKNOXAI> ");
				xml.append("<cm:pageHeader>");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:enrlId>");
				if (null != knoGenerationDetails.getArnNo()
						&& !""
								.equalsIgnoreCase(knoGenerationDetails
										.getArnNo())) {
					xml.append(knoGenerationDetails.getArnNo().trim());
				}
				xml.append("</cm:enrlId>");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:logId>");
				if (null != knoGenerationDetails.getArnGenBy()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getArnGenBy())) {
					xml.append(knoGenerationDetails.getArnGenBy().trim());
				}
				xml.append("</cm:logId> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:trdSecurityChrg>");
				if (null != knoGenerationDetails.getTradeSecurityCharge()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getTradeSecurityCharge())) {
					xml.append(knoGenerationDetails.getTradeSecurityCharge());
				}
				xml.append("</cm:trdSecurityChrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:mtrSecurityChrg>");
				if (null != knoGenerationDetails.getMtrSecurityCharge()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getMtrSecurityCharge())) {
					xml.append(knoGenerationDetails.getMtrSecurityCharge()
							.trim());
				}
				xml.append("</cm:mtrSecurityChrg>");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:isSecurityApplicable>");
				if (null != knoGenerationDetails.getIsOccupierSecurity()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getIsOccupierSecurity())) {
					xml.append(knoGenerationDetails.getIsOccupierSecurity()
							.trim());
				}
				xml.append("</cm:isSecurityApplicable> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:micrCode>");
				if (null != knoGenerationDetails.getBankMicr()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getBankMicr())) {
					xml.append(knoGenerationDetails.getBankMicr().trim());
				}
				xml.append("</cm:micrCode> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:bankBranch>");
				if (null != knoGenerationDetails.getBankBranch()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getBankBranch())) {
					xml.append(knoGenerationDetails.getBankBranch().trim());
				}
				xml.append("</cm:bankBranch> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:bankAcct>");
				if (null != knoGenerationDetails.getBankAcct()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getBankAcct())) {
					xml.append(knoGenerationDetails.getBankAcct().trim());
				}
				xml.append("</cm:bankAcct> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:bankName>");
				if (null != knoGenerationDetails.getBankName()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getBankName())) {
					xml.append(knoGenerationDetails.getBankName().trim());
				}
				xml.append("</cm:bankName> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:personId>");
				if (null != knoGenerationDetails.getPerId()
						&& !""
								.equalsIgnoreCase(knoGenerationDetails
										.getPerId())) {
					xml.append(knoGenerationDetails.getPerId().trim());
				}
				xml.append("</cm:personId> ");
				// xml.append(" <!--Optional:--> ");
				// xml.append(" <cm:arn>");
				// if (null != knoGenerationDetails.getArnNo()
				// && !"".equalsIgnoreCase(knoGenerationDetails.getArnNo())) {
				// xml.append(knoGenerationDetails.getArnNo().trim());
				// }
				// xml.append("</cm:arn> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:mrkey>");
				if (null != knoGenerationDetails.getMrkey()
						&& !""
								.equalsIgnoreCase(knoGenerationDetails
										.getMrkey())) {
					xml.append(knoGenerationDetails.getMrkey().trim());
				}
				xml.append("</cm:mrkey> ");
				xml.append(" <cm:purpsOfApplictn>");
				if (null != knoGenerationDetails.getPurposeOfAppl()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getPurposeOfAppl())) {
					xml.append(knoGenerationDetails.getPurposeOfAppl().trim());
				}
				xml.append("</cm:purpsOfApplictn> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:isWatUsedForPrem>");
				if (null != knoGenerationDetails.getIsWatUsedForPrem()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getIsWatUsedForPrem())) {
					xml.append(knoGenerationDetails.getIsWatUsedForPrem()
							.trim());
				}
				xml.append("</cm:isWatUsedForPrem> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:appCivilColonyChrg>");
				if (null != knoGenerationDetails.getApplCivilConstChrg()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getApplCivilConstChrg())) {
					xml.append(knoGenerationDetails.getApplCivilConstChrg()
							.trim());
				}
				xml.append("</cm:appCivilColonyChrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:unauthrsdPremise>");
				if (null != knoGenerationDetails.getIsUnAuthorisedUsage()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getIsUnAuthorisedUsage())) {
					xml.append(knoGenerationDetails.getIsUnAuthorisedUsage()
							.trim());
				}
				xml.append("</cm:unauthrsdPremise> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:preModePaymnt>");
				if (null != knoGenerationDetails.getPrefModeOfPayment()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getPrefModeOfPayment())) {
					xml.append(knoGenerationDetails.getPrefModeOfPayment()
							.trim());
				}
				xml.append("</cm:preModePaymnt> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:watTechFeasblty>");
				if (null != knoGenerationDetails.getWatTechFeasibility()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getWatTechFeasibility())) {
					xml.append(knoGenerationDetails.getWatTechFeasibility()
							.trim());
				}
				xml.append("</cm:watTechFeasblty> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:sewTechFeasblty>");
				if (null != knoGenerationDetails.getSewTechFeasibility()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getSewTechFeasibility())) {
					xml.append(knoGenerationDetails.getSewTechFeasibility()
							.trim());
				}
				xml.append("</cm:sewTechFeasblty> ");
				xml.append("<!--Optional:--> ");
				xml.append("<cm:defValue>");
				if (null != knoGenerationDetails.getNotToBeUsed()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getNotToBeUsed())) {
					xml.append(knoGenerationDetails.getNotToBeUsed().trim());
				}
				xml.append("</cm:defValue> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:docVrifd>");
				if (null != knoGenerationDetails.getIsDocVerified()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getIsDocVerified())) {
					xml.append(knoGenerationDetails.getIsDocVerified().trim());
				}
				xml.append("</cm:docVrifd> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:rebateApplcbl>");
				if (null != knoGenerationDetails.getDjbEmpRbtAppl()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDjbEmpRbtAppl())) {
					xml.append(knoGenerationDetails.getDjbEmpRbtAppl().trim());
				}
				xml.append("</cm:rebateApplcbl> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:custColoyForDevchrg>");
				if (null != knoGenerationDetails.getCustColony()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getCustColony())) {
					xml.append(knoGenerationDetails.getCustColony().trim());
				}
				xml.append("</cm:custColoyForDevchrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:custDateApplicbl>");
				if (null != knoGenerationDetails.getDtOfAppl()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDtOfAppl())) {
					xml.append(knoGenerationDetails.getDtOfAppl().trim());
				}
				xml.append("</cm:custDateApplicbl> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:sewerDevChrgApplcbl>");
				if (null != knoGenerationDetails.getSewDevChrgAppl()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getSewDevChrgAppl())) {
					xml.append(knoGenerationDetails.getSewDevChrgAppl().trim());
				}
				xml.append("</cm:sewerDevChrgApplcbl> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:devChrgColSew>");
				if (null != knoGenerationDetails.getDevChrgColonySew()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDevChrgColonySew())) {
					xml.append(knoGenerationDetails.getDevChrgColonySew()
							.trim());
				}
				xml.append("</cm:devChrgColSew> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:notifctDateSewchrg>");
				if (null != knoGenerationDetails.getDevChrgNotifyDtSewer()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDevChrgNotifyDtSewer())) {
					xml.append(knoGenerationDetails.getDevChrgNotifyDtSewer()
							.trim());
				}
				xml.append("</cm:notifctDateSewchrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:watDevChrgApp>");
				if (null != knoGenerationDetails.getWatDevChrgAppl()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getWatDevChrgAppl())) {
					xml.append(knoGenerationDetails.getWatDevChrgAppl().trim());
				}
				xml.append("</cm:watDevChrgApp> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:devChrgWatr>");
				if (null != knoGenerationDetails.getDevChrgColonyWat()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDevChrgColonyWat())) {
					xml.append(knoGenerationDetails.getDevChrgColonyWat()
							.trim());
				}
				xml.append("</cm:devChrgWatr> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:notifctDateWaterChrg>");
				if (null != knoGenerationDetails.getDevChrgNotifyDtWat()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDevChrgNotifyDtWat())) {
					xml.append(knoGenerationDetails.getDevChrgNotifyDtWat()
							.trim());
				}
				xml.append("</cm:notifctDateWaterChrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:empId>");
				if (null != knoGenerationDetails.getEmpId()
						&& !""
								.equalsIgnoreCase(knoGenerationDetails
										.getEmpId())) {
					xml.append(knoGenerationDetails.getEmpId().trim());
				}
				xml.append("</cm:empId> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:sizeOfmeter>");
				if (null != knoGenerationDetails.getSizeOfMtr()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getSizeOfMtr())) {
					xml.append(knoGenerationDetails.getSizeOfMtr().trim());
				}
				xml.append("</cm:sizeOfmeter> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:toDoField>");
				if (null != knoGenerationDetails.getToDoField()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getToDoField())) {
					xml.append(knoGenerationDetails.getToDoField().trim());
				}
				xml.append("</cm:toDoField> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:lenghtOfRoadCutForWater>");
				if (null != knoGenerationDetails.getLengthOfRoadForWater()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getLengthOfRoadForWater())) {
					xml.append(knoGenerationDetails.getLengthOfRoadForWater()
							.trim());
				}
				xml.append("</cm:lenghtOfRoadCutForWater> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:lenghtOfRoadCutForSewer>");
				if (null != knoGenerationDetails.getLengthOfRoadForSewer()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getLengthOfRoadForSewer())) {
					xml.append(knoGenerationDetails.getLengthOfRoadForSewer()
							.trim());
				}
				xml.append("</cm:lenghtOfRoadCutForSewer> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:appRoadRestrnChrgForWater>");
				if (null != knoGenerationDetails.getRoadRestChrgForWater()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getRoadRestChrgForWater())) {
					xml.append(knoGenerationDetails.getRoadRestChrgForWater()
							.trim());
				}
				xml.append("</cm:appRoadRestrnChrgForWater> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:appRoadRestrnChrgForSewer>");
				if (null != knoGenerationDetails.getRoadRestChrgForSewer()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getRoadRestChrgForSewer())) {
					xml.append(knoGenerationDetails.getRoadRestChrgForSewer()
							.trim());
				}
				xml.append("</cm:appRoadRestrnChrgForSewer> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:additnlChrg>");
				if (null != knoGenerationDetails.getAddtionalCharge()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getAddtionalCharge())) {
					xml
							.append(knoGenerationDetails.getAddtionalCharge()
									.trim());
				}
				xml.append("</cm:additnlChrg> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:unauthPenltyChrgd>");
				/*
				 * if (null != knoGenerationDetails.getIsUnAuthorisedUsage() &&
				 * !"".equalsIgnoreCase(knoGenerationDetails
				 * .getIsUnAuthorisedUsage())) { xml
				 * .append(knoGenerationDetails.getIsUnAuthorisedUsage()
				 * .trim()); }
				 */
				// Start : Edited by Arnab Nandy (1227971) as per JTrac DJB-4398
				if (null != knoGenerationDetails.getNoOfYrForUnauthPenlaty()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getNoOfYrForUnauthPenlaty())) {
					xml.append(knoGenerationDetails.getNoOfYrForUnauthPenlaty()
							.trim());
				}
				// End : Edited by Arnab Nandy (1227971) as per JTrac DJB-4398
				xml.append("</cm:unauthPenltyChrgd> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:estimtdConsmptn>");
				if (null != knoGenerationDetails.getUnauthPenlatyAmt()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getUnauthPenlatyAmt())) {
					xml.append(knoGenerationDetails.getUnauthPenlatyAmt()
							.trim());
				}
				xml.append("</cm:estimtdConsmptn> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:thirdPartyVendr>");
				xml.append("</cm:thirdPartyVendr> ");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:dateOfRetirmnt>");
				if (null != knoGenerationDetails.getDtOfRet()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getDtOfRet())) {
					xml.append(knoGenerationDetails.getDtOfRet().trim());
				}
				xml.append("</cm:dateOfRetirmnt>");
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:zro>");
				if (null != knoGenerationDetails.getZroCd()
						&& !""
								.equalsIgnoreCase(knoGenerationDetails
										.getZroCd())) {
					xml.append(knoGenerationDetails.getZroCd().trim());
				}
				xml.append("</cm:zro> ");
				// Start : Edited by Sanjay Das (1033846) as per jTrac 4408 to
				// insert the value Plot Area into CCB DB
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:plotArea>");
				if (null != knoGenerationDetails.getPlotArea()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getPlotArea())) {
					xml.append(knoGenerationDetails.getPlotArea().trim());
				}
				xml.append("</cm:plotArea> ");
				// End : Edited by Sanjay Das (1033846)
				// START: On 31-MAR-2016 Rajib Hazarika (682667) modified lines
				// as per JTrac DJB-4429 for enabling of purpose of Application
				// field
				xml.append(" <!--Optional:--> ");
				xml.append(" <cm:statusFlag>");
				if (null != knoGenerationDetails.getPurposeOfApplChngFlag()
						&& !"".equalsIgnoreCase(knoGenerationDetails
								.getPurposeOfApplChngFlag())) {
					xml.append(knoGenerationDetails.getPurposeOfApplChngFlag()
							.trim());
				}
				xml.append("</cm:statusFlag> ");
				// END: On 31-MAR-2016 Rajib Hazarika (682667) modified lines as
				// per JTrac DJB-4429 for enabling of purpose of Application
				// field
				xml.append("</cm:pageHeader>");
				xml.append("<cm:pageBody>");
				xml.append("<cm:kno></cm:kno>");
				xml.append("</cm:pageBody>");
				xml.append(" </cm:CM_GNRTKNOXAI> ");
				xml.append(" </soapenv:Body> ");
				xml.append(" </soapenv:Envelope> ");
				String encodedXML = UtilityForAll.encodeString(xml.toString());
				AppLog.info("XAIHTTP Request xml::\n" + xml.toString());
				AppLog.info("XAIHTTP Encoded Request xml::\n" + encodedXML);
				XAIHTTPCall xaiHTTPCall = null;
				if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
					xaiHTTPCall = new XAIHTTPCall(authCookie);
				} else {
					xaiHTTPCall = new XAIHTTPCall();
				}
				xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
				AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);
				if (null != xaiHTTPCallResponse
						&& !"".equalsIgnoreCase(xaiHTTPCallResponse)) {
					if (xaiHTTPCallResponse.indexOf("<kno>") > -1) {
						acctId = xaiHTTPCallResponse.substring(
								xaiHTTPCallResponse.indexOf("<kno>")
										+ "<kno>".length(), xaiHTTPCallResponse
										.indexOf("</kno>"));
					}
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return acctId;
	}

	/**
	 * <p>
	 * This function will return BillId if it is successfully generated through
	 * CC&B Service Call , otherwise it will return empty value
	 * </p>
	 * 
	 * @param kno
	 * @param authCookie
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 15-FEB-2016
	 * @return
	 */
	public static String generateNewConnBill(String kno, String authCookie) {
		AppLog.begin();
		StringBuffer xml = new StringBuffer(512);
		String xaiHTTPCallResponse;
		String billId = "";
		if (null == kno && "".equalsIgnoreCase(kno)) {
			AppLog.end();
			return billId;
		}
		try {
			xml
					.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:cm='http://oracle.com/CM_NEWCONBillGeneration_XAI.xsd'>");
			xml.append(" <soapenv:Header/> ");
			xml.append(" <soapenv:Body> ");
			xml.append(" <cm:CM_NEWCONBillGeneration_XAI>");
			xml.append(" <!--Optional:--> ");
			xml.append(" <cm:pageHeader> ");
			xml.append(" <!--Optional:--> ");
			xml.append(" <cm:acctId>" + kno + "</cm:acctId>");
			xml.append(" </cm:pageHeader> ");
			xml.append(" <!--Optional:--> ");
			xml.append(" <cm:pageBody> ");
			xml.append(" <!--Optional:--> ");
			xml.append(" <cm:billId></cm:billId> ");
			xml.append(" </cm:pageBody> ");
			xml.append(" </cm:CM_NEWCONBillGeneration_XAI> ");
			xml.append(" </soapenv:Body> ");
			xml.append(" </soapenv:Envelope> ");
			String encodedXML = UtilityForAll.encodeString(xml.toString());
			AppLog.info("XAIHTTP Request xml::\n" + xml.toString());
			AppLog.info("XAIHTTP Encoded Request xml::\n" + encodedXML);
			XAIHTTPCall xaiHTTPCall = null;
			if (null != authCookie && !"".equalsIgnoreCase(authCookie)) {
				xaiHTTPCall = new XAIHTTPCall(authCookie);
			} else {
				xaiHTTPCall = new XAIHTTPCall();
			}
			xaiHTTPCallResponse = xaiHTTPCall.callXAIServer(encodedXML);
			AppLog.info("XAIHTTP Call Response::\n" + xaiHTTPCallResponse);
			if (null != xaiHTTPCallResponse
					&& !"".equalsIgnoreCase(xaiHTTPCallResponse)) {
				if (xaiHTTPCallResponse.indexOf("<billId>") > -1) {
					billId = xaiHTTPCallResponse.substring(xaiHTTPCallResponse
							.indexOf("<billId>")
							+ "<billId>".length(), xaiHTTPCallResponse
							.indexOf("</billId>"));
					AppLog.info(">>billId in XAi>>>>" + billId);
				}
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		AppLog.end();
		return billId;
	}

}