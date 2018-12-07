/************************************************************************************************************
 * @(#) DevChargeMasterDataAction.java   03-Aug-2015
 * 
 *
 *************************************************************************************************************/
package com.tcs.djb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.dao.DevChargeMasterDataDAO;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.model.DevChargeMasterDataColonyDetails;
import com.tcs.djb.model.DevChargeMasterDataInterestDetails;
import com.tcs.djb.model.DevChargeMasterDataRateDetails;
import com.tcs.djb.model.DevChargeMasterDataRebateDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.UtilityForAll;
import com.tcs.djb.validator.ScreenAccessValidator;

/**
 * <p>
 * Action class for Dev Charge Master Data Screen, Jtrac DJB-3994.
 * </p>
 * 
 * @author Reshma Srivastava (Tata Consultancy Services)
 * @since 03-08-2015
 * 
 */
public class DevChargeMasterDataAction extends ActionSupport implements
		ServletResponseAware {

	/**
	 * SCREEN ID in DJB_DE_SCREEN_MST.
	 */
	private static final String SCREEN_ID = "38";
	private static final long serialVersionUID = 1L;
	private String hidAction;
	private InputStream inputStream;
	private String option;
	private String page;
	private HttpServletResponse response;
	private String selectedZRO;
	private String colonyName;
	private String colonyId;
	private String category;
	private String chargeType;
	private String notificationDate;
	private String applicableRateNewColony;
	private String rateInterestEligibilityNewColony;
	private String rebateEligibilityNewColony;
	private String rateStartDateNewColony;
	private String rateEndDateNewColony;
	private String rateDocNewColony;
	private String rateMinAreaNewColony;
	private String rateMaxAreaNewColony;
	private String rateTypeNewColony;
	private String rateCategoryNewColony;
	private String interestPercentageNewColony;
	private String interestStartDateNewColony;
	private String interestEndDateNewColony;
	private String interestDocNewColony;
	private String rebateDocNewColony;
	private String rebateTypeNewColony;
	private String rebatePercentageNewColony;
	private String rebateStartDateNewColony;
	private String rebateEndDateNewColony;
	private String rebateFlatRateNewColony;
	private String applicablRateRow;
	private String rebateEligibilityRow;
	private String rateInterestEligibilityRow;
	private String rateMinAreaRow;
	private String rateTypeRow;
	private String rateMaxAreaRow;
	private String rateCategoryRow;
	private String rateStartDateRow;
	private String rateEndDateRow;
	private String rateDocProofRow;
	private String interestPercentageRow;
	private String interestStartDateRow;
	private String interestDocRow;
	private String interestEndDateRow;
	private String rebateTypeRow;
	private String rebatePercentageRow;
	private String rebateFlatRateRow;
	private String rebateStartDateRow;
	private String rebateDocRow;
	private String rebateEndDateRow;

	/**
	 * <p>
	 * This method is to activate the searched colony for dev charge, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String activateColony(String userId) {
		AppLog.begin();
		try {
			AppLog.info("Colony ID:" + colonyId + ":Category:" + category);
			if (UtilityForAll.isEmpty(category)
					|| UtilityForAll.isEmpty(colonyId)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			DevChargeMasterDataDAO.activateColony(colonyId, category, userId);
			if (DevChargeMasterDataDAO.getActivatedColonyRowCount(colonyId,
					userId)) {
				inputStream = new StringBufferInputStream(
						"<font color='green' size='2'><li><span><b>Colony Activated Successfully.</b></font>");
			} else {
				String errorMsg = DevChargeMasterDataDAO.getErrorMessage(
						colonyId, userId, "CM_ACTIVATE_COLONY");
				if (null != errorMsg && errorMsg.trim().length() > 0) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR: "
									+ errorMsg + ".</b></font>");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * For all ajax call in meter replacement screen, Jtrac DJB-3994
	 * 
	 * @return string
	 */
	public String ajaxMethod() {
		AppLog.begin();
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			/**
			 * to check if user is login or not, Jtrac DJB-3994
			 */
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				inputStream = new StringBufferInputStream("ERROR:"
						+ getText("error.login.expired")
						+ ", Please re login  and try Again!");
				AppLog.end();
				return "login";
			}
			/**
			 * to check if user has access to screen or not, Jtrac DJB-3994
			 */
			if (!ScreenAccessValidator.validate(session, SCREEN_ID)) {
				addActionError(getText("error.access.denied"));
				inputStream = ScreenAccessValidator
						.ajaxResponse(getText("error.access.denied"));
				AppLog.end();
				return "success";
			}
			/**
			 * to search the list of matching colonies, Jtrac DJB-3994
			 */
			if ("searchColonyList".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				searchDevChargeColonyList();
				AppLog.end();
			}
			/**
			 * to get Master Data for provided colony, Jtrac DJB-3994
			 */
			if ("getMasterData".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				searchDevChargeMasterDataDetails();
				AppLog.end();
			}
			/**
			 * to activate the searched colony, Jtrac DJB-3994
			 */
			if ("activateColony".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				activateColony(userId);
				AppLog.end();
			}
			/**
			 * to save the interest detail row in the searched colony, Jtrac
			 * DJB-3994
			 */
			if ("saveInterestDetailRow".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				saveInterestDetailRow(userId);
				AppLog.end();
			}
			/**
			 * to save the rate detail row in the searched colony, Jtrac
			 * DJB-3994
			 */
			if ("saveRateDetailRow".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				saveRateDetailRow(userId);
				AppLog.end();
			}
			/**
			 * to save the rebate detail row in the searched colony, Jtrac
			 * DJB-3994
			 */
			if ("saveRebateDetailRow".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				saveRebateDetailRow(userId);
				AppLog.end();
			}
			/**
			 * to save the new colony detail row in the searched colony, Jtrac
			 * DJB-3994
			 */
			if ("saveNewColonyDetail".equalsIgnoreCase(hidAction)) {
				AppLog.begin();
				saveNewColonyDetail(userId);
				AppLog.end();
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"ERROR:There was an error at Server end.");
			// response.setHeader("Expires", "0");
			// response.setHeader("Pragma", "cache");
			// response.setHeader("Cache-Control", "private");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		AppLog.begin();
		String result = "success";
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			String userId = (String) session.get("userId");
			if (null == userId || "".equals(userId)) {
				addActionError(getText("error.login.expired"));
				AppLog.end();
				return "login";
			}
			session.put("CURR_TAB", "MASTER");
			if (null == session.get("ZROListMap")
					|| null == session.get("ChargeTypeListMap")
					|| null == session.get("CatClassListMap")
					|| null == session.get("RebateTypeListMap")
					|| null == session.get("RateTypeListMap")
					|| null == session.get("InterestEligibilityListMap")
					|| null == session.get("RebateEligibilityListMap")
					|| null == session.get("ColonyListMap")
					|| null == session.get(DJBConstants.YEAR_INTERVAL_PARAM)) {
				session.put("ZROListMap", GetterDAO.getZROListMap());
				session.put("ChargeTypeListMap", DevChargeMasterDataDAO
						.getDevChargeListMap(DJBConstants.CHARG_TYP_PARAM));
				session.put("CatClassListMap", DevChargeMasterDataDAO
						.getDevChargeListMap(DJBConstants.CAT_CLASS_TYP_PARAM));
				session.put("RebateTypeListMap", DevChargeMasterDataDAO
						.getDevChargeListMap(DJBConstants.REBATE_TYP_PARAM));
				session.put("RateTypeListMap", DevChargeMasterDataDAO
						.getDevChargeListMap(DJBConstants.RATE_TYP_PARAM));
				session
						.put(
								"InterestEligibilityListMap",
								DevChargeMasterDataDAO
										.getDevChargeListMap(DJBConstants.INTRST_ELIGIBILIIY_PARAM));
				session
						.put(
								"RebateEligibilityListMap",
								DevChargeMasterDataDAO
										.getDevChargeListMap(DJBConstants.REBATE_ELIGIBILIIY_PARAM));
				session.put("ColonyListMap", DevChargeMasterDataDAO
						.getDevChargeListMap(DJBConstants.COLONY_TYP_PARAM));
				session
						.put(
								DJBConstants.YEAR_INTERVAL_PARAM,
								DevChargeMasterDataDAO
										.getDevChargeYearInterval(DJBConstants.YEAR_INTERVAL_PARAM));
			}
		} catch (Exception e) {
			AppLog.error(e);
		}
		return result;
	}

	/**
	 * @return the applicableRateNewColony
	 */
	public String getApplicableRateNewColony() {
		return applicableRateNewColony;
	}

	/**
	 * @return the applicablRateRow
	 */
	public String getApplicablRateRow() {
		return applicablRateRow;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @return the colonyId
	 */
	public String getColonyId() {
		return colonyId;
	}

	/**
	 * @return the colonyName
	 */
	public String getColonyName() {
		return colonyName;
	}

	/**
	 * @return the hidAction
	 */
	public String getHidAction() {
		return hidAction;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return the interestDocNewColony
	 */
	public String getInterestDocNewColony() {
		return interestDocNewColony;
	}

	/**
	 * @return the interestDocRow
	 */
	public String getInterestDocRow() {
		return interestDocRow;
	}

	/**
	 * @return the interestEndDateNewColony
	 */
	public String getInterestEndDateNewColony() {
		return interestEndDateNewColony;
	}

	/**
	 * @return the interestEndDateRow
	 */
	public String getInterestEndDateRow() {
		return interestEndDateRow;
	}

	/**
	 * @return the interestPercentageNewColony
	 */
	public String getInterestPercentageNewColony() {
		return interestPercentageNewColony;
	}

	/**
	 * @return the interestPercentageRow
	 */
	public String getInterestPercentageRow() {
		return interestPercentageRow;
	}

	/**
	 * @return the interestStartDateNewColony
	 */
	public String getInterestStartDateNewColony() {
		return interestStartDateNewColony;
	}

	/**
	 * @return the interestStartDateRow
	 */
	public String getInterestStartDateRow() {
		return interestStartDateRow;
	}

	/**
	 * @return the notificationDate
	 */
	public String getNotificationDate() {
		return notificationDate;
	}

	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the rateCategoryNewColony
	 */
	public String getRateCategoryNewColony() {
		return rateCategoryNewColony;
	}

	/**
	 * @return the rateCategoryRow
	 */
	public String getRateCategoryRow() {
		return rateCategoryRow;
	}

	/**
	 * @return the rateDocNewColony
	 */
	public String getRateDocNewColony() {
		return rateDocNewColony;
	}

	/**
	 * @return the rateDocProofRow
	 */
	public String getRateDocProofRow() {
		return rateDocProofRow;
	}

	/**
	 * @return the rateEndDateNewColony
	 */
	public String getRateEndDateNewColony() {
		return rateEndDateNewColony;
	}

	/**
	 * @return the rateEndDateRow
	 */
	public String getRateEndDateRow() {
		return rateEndDateRow;
	}

	/**
	 * @return the interestEligibilityNewColony
	 */
	public String getRateInterestEligibilityNewColony() {
		return rateInterestEligibilityNewColony;
	}

	/**
	 * @return the rateInterestEligibilityRow
	 */
	public String getRateInterestEligibilityRow() {
		return rateInterestEligibilityRow;
	}

	/**
	 * @return the rateMaxAreaNewColony
	 */
	public String getRateMaxAreaNewColony() {
		return rateMaxAreaNewColony;
	}

	/**
	 * @return the rateMaxAreaRow
	 */
	public String getRateMaxAreaRow() {
		return rateMaxAreaRow;
	}

	/**
	 * @return the rateMinAreaNewColony
	 */
	public String getRateMinAreaNewColony() {
		return rateMinAreaNewColony;
	}

	/**
	 * @return the rateMinAreaRow
	 */
	public String getRateMinAreaRow() {
		return rateMinAreaRow;
	}

	/**
	 * @return the rateStartDateNewColony
	 */
	public String getRateStartDateNewColony() {
		return rateStartDateNewColony;
	}

	/**
	 * @return the rateStartDateRow
	 */
	public String getRateStartDateRow() {
		return rateStartDateRow;
	}

	/**
	 * @return the rateTypeNewColony
	 */
	public String getRateTypeNewColony() {
		return rateTypeNewColony;
	}

	/**
	 * @return the rateTypeRow
	 */
	public String getRateTypeRow() {
		return rateTypeRow;
	}

	/**
	 * @return the rebateDocNewColony
	 */
	public String getRebateDocNewColony() {
		return rebateDocNewColony;
	}

	/**
	 * @return the rebateDocRow
	 */
	public String getRebateDocRow() {
		return rebateDocRow;
	}

	/**
	 * @return the rebateEligibilityNewColony
	 */
	public String getRebateEligibilityNewColony() {
		return rebateEligibilityNewColony;
	}

	/**
	 * @return the rebateEligibilityRow
	 */
	public String getRebateEligibilityRow() {
		return rebateEligibilityRow;
	}

	/**
	 * @return the rebateEndDateNewColony
	 */
	public String getRebateEndDateNewColony() {
		return rebateEndDateNewColony;
	}

	/**
	 * @return the rebateEndDateRow
	 */
	public String getRebateEndDateRow() {
		return rebateEndDateRow;
	}

	/**
	 * @return the rebateFlatRateNewColony
	 */
	public String getRebateFlatRateNewColony() {
		return rebateFlatRateNewColony;
	}

	/**
	 * @return the rebateFlatRateRow
	 */
	public String getRebateFlatRateRow() {
		return rebateFlatRateRow;
	}

	/**
	 * @return the rebatePercentageNewColony
	 */
	public String getRebatePercentageNewColony() {
		return rebatePercentageNewColony;
	}

	/**
	 * @return the rebatePercentageRow
	 */
	public String getRebatePercentageRow() {
		return rebatePercentageRow;
	}

	/**
	 * @return the rebateStartDateNewColony
	 */
	public String getRebateStartDateNewColony() {
		return rebateStartDateNewColony;
	}

	/**
	 * @return the rebateStartDateRow
	 */
	public String getRebateStartDateRow() {
		return rebateStartDateRow;
	}

	/**
	 * @return the rebateTypeNewColony
	 */
	public String getRebateTypeNewColony() {
		return rebateTypeNewColony;
	}

	/**
	 * @return the rebateTypeRow
	 */
	public String getRebateTypeRow() {
		return rebateTypeRow;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the selectedZRO
	 */
	public String getSelectedZRO() {
		return selectedZRO;
	}

	/**
	 * <p>
	 * This method is to save interest detail row in the searched colony for dev
	 * charge, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String saveInterestDetailRow(String userId) {
		AppLog.begin();
		try {
			AppLog.info("interestPercentageRow:" + interestPercentageRow
					+ ":interestStartDateRow:" + interestStartDateRow
					+ ":interestEndDateRow:" + interestEndDateRow
					+ ":colonyId:" + colonyId);

			if (UtilityForAll.isEmpty(interestPercentageRow)
					|| UtilityForAll.isEmpty(interestStartDateRow)
					|| UtilityForAll.isEmpty(interestEndDateRow)
					|| UtilityForAll.isEmpty(colonyId)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validInterestStartDateRow = UtilityForAll.changeDateFormat(
					interestStartDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG
					.equalsIgnoreCase(validInterestStartDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validInterestEndDateRow = UtilityForAll.changeDateFormat(
					interestEndDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG
					.equalsIgnoreCase(validInterestEndDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}

			DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails = new DevChargeMasterDataInterestDetails();
			devChargeMasterDataInterestDetails
					.setInterestPercentage(interestPercentageRow);
			devChargeMasterDataInterestDetails
					.setInterestStartDt(validInterestStartDateRow);
			devChargeMasterDataInterestDetails
					.setInterestEndDt(validInterestEndDateRow);

			DevChargeMasterDataDAO.saveInterestDetailRow(
					devChargeMasterDataInterestDetails, colonyId, userId);

			if (DevChargeMasterDataDAO.getSavedInterestRowCount(colonyId,
					userId)) {
				inputStream = new StringBufferInputStream(
						"<font color='green' size='2'><li><span><b>Interest Details Saved Successfully.</b></font>");
			} else {
				String errorMsg = DevChargeMasterDataDAO.getErrorMessage(
						colonyId, userId, "CM_CREATE_INTEREST");
				if (null != errorMsg && errorMsg.trim().length() > 0) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR: "
									+ errorMsg + ".</b></font>");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * This method is to save new colony detail for dev charge, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String saveNewColonyDetail(String userId) {
		AppLog.begin();
		try {
			AppLog.info("colonyName:" + colonyName + ":chargeType:"
					+ chargeType + ":category:" + category
					+ ":notificationDate:" + notificationDate
					+ ":applicableRateNewColony:" + applicableRateNewColony
					+ ":rebateEligibilityNewColony:"
					+ rebateEligibilityNewColony + ":rateStartDateNewColony:"
					+ rateStartDateNewColony + ":rateEndDateNewColony:"
					+ rateEndDateNewColony + ":rateMinAreaNewColony:"
					+ rateMinAreaNewColony + ":rateMaxAreaNewColony:"
					+ rateMaxAreaNewColony + ":rateTypeNewColony:"
					+ rateTypeNewColony + ":rateCategoryNewColony:"
					+ rateCategoryNewColony + ":interestPercentageNewColony:"
					+ interestPercentageNewColony
					+ ":interestStartDateNewColony:"
					+ interestStartDateNewColony + ":interestEndDateNewColony:"
					+ interestEndDateNewColony + ":rebateTypeNewColony:"
					+ rebateTypeNewColony + ":rebatePercentageNewColony:"
					+ rebatePercentageNewColony + ":rebateStartDateNewColony:"
					+ rebateStartDateNewColony + ":rebateEndDateNewColony:"
					+ rebateEndDateNewColony + ":rebateFlatRateNewColony:"
					+ rebateFlatRateNewColony
					+ ":rateInterestEligibilityNewColony:"
					+ rateInterestEligibilityNewColony + ":selectedZRO:"
					+ selectedZRO);

			if (UtilityForAll.isEmpty(colonyName)
					|| UtilityForAll.isEmpty(chargeType)
					|| UtilityForAll.isEmpty(notificationDate)
					|| UtilityForAll.isEmpty(applicableRateNewColony)
					|| UtilityForAll.isEmpty(rebateEligibilityNewColony)
					|| UtilityForAll.isEmpty(rateStartDateNewColony)
					|| UtilityForAll.isEmpty(rateEndDateNewColony)
					|| UtilityForAll.isEmpty(rateTypeNewColony)
					|| UtilityForAll.isEmpty(rateInterestEligibilityNewColony)
					|| UtilityForAll.isEmpty(selectedZRO)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}

			/**
			 * to validate colony details
			 */
			String validNotificationDate = UtilityForAll.changeDateFormat(
					notificationDate, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validNotificationDate)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}

			/**
			 * to validate rate details
			 */
			String validRateStartDate = UtilityForAll.changeDateFormat(
					rateStartDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRateStartDate)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validRateEndDate = UtilityForAll.changeDateFormat(
					rateEndDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRateEndDate)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}

			/**
			 * to validate interest details
			 */
			String validInterestStartDate = null;
			String validInterestEndDate = null;
			if (DJBConstants.FLAG_Y
					.equalsIgnoreCase(rateInterestEligibilityNewColony.trim())) {

				if (UtilityForAll.isEmpty(interestPercentageNewColony)
						|| UtilityForAll.isEmpty(interestStartDateNewColony)
						|| UtilityForAll.isEmpty(interestEndDateNewColony)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}

				validInterestStartDate = UtilityForAll.changeDateFormat(
						interestStartDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
				if (DJBConstants.ERROR_FLG
						.equalsIgnoreCase(validInterestStartDate)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}

				validInterestEndDate = UtilityForAll.changeDateFormat(
						interestEndDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
				if (DJBConstants.ERROR_FLG
						.equalsIgnoreCase(validInterestEndDate)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}
			}

			/**
			 * to validate rebate details
			 */
			String validRebateStartDate = null;
			String validRebateEndDate = null;
			if (DJBConstants.FLAG_Y.equalsIgnoreCase(rebateEligibilityNewColony
					.trim())) {
				if (UtilityForAll.isEmpty(rebateTypeNewColony)
						|| UtilityForAll.isEmpty(rebateStartDateNewColony)
						|| UtilityForAll.isEmpty(rebateEndDateNewColony)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}
				if (DJBConstants.DEV_CHRG_FLAT_REBATE_CD
						.equalsIgnoreCase(rebateTypeNewColony)) {
					if (UtilityForAll.isEmpty(rebateFlatRateNewColony)) {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
						AppLog.end();
						return "SUCCESS";
					}
				} else if (DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD
						.equalsIgnoreCase(rebateTypeNewColony)) {
					if (UtilityForAll.isEmpty(rebatePercentageNewColony)) {
						inputStream = new StringBufferInputStream(
								"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
						AppLog.end();
						return "SUCCESS";
					}
				}

				validRebateStartDate = UtilityForAll.changeDateFormat(
						rebateStartDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
				if (DJBConstants.ERROR_FLG
						.equalsIgnoreCase(validRebateStartDate)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}

				validRebateEndDate = UtilityForAll.changeDateFormat(
						rebateEndDateNewColony, "dd/MM/yyyy", "dd-MM-yyyy");
				if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRebateEndDate)) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
					AppLog.end();
					return "SUCCESS";
				}
			}
			DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails = new DevChargeMasterDataColonyDetails();
			devChargeMasterDataColonyDetails.setCategory(category);
			devChargeMasterDataColonyDetails.setChargeType(chargeType);
			devChargeMasterDataColonyDetails.setColonyName(colonyName);
			devChargeMasterDataColonyDetails
					.setNotificationDate(validNotificationDate);
			devChargeMasterDataColonyDetails.setSelectedZRO(selectedZRO);

			DevChargeMasterDataRateDetails devChargeMasterDataRateDetails = new DevChargeMasterDataRateDetails();
			devChargeMasterDataRateDetails
					.setApplicableRate(applicableRateNewColony);
			devChargeMasterDataRateDetails
					.setCustomerClassCode(rateCategoryNewColony);
			devChargeMasterDataRateDetails.setRateEndDt(validRateEndDate);
			devChargeMasterDataRateDetails
					.setRateMaxPlotArea(rateMaxAreaNewColony);
			devChargeMasterDataRateDetails
					.setRateMinPlotArea(rateMinAreaNewColony);
			devChargeMasterDataRateDetails.setRateStartDt(validRateStartDate);
			devChargeMasterDataRateDetails.setRateTypeCode(rateTypeNewColony);
			devChargeMasterDataRateDetails
					.setRebateEligibility(rebateEligibilityNewColony);
			devChargeMasterDataRateDetails
					.setRateInterestEligibity(rateInterestEligibilityNewColony);

			DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails = new DevChargeMasterDataInterestDetails();
			devChargeMasterDataInterestDetails
					.setInterestPercentage(interestPercentageNewColony);
			devChargeMasterDataInterestDetails
					.setInterestStartDt(validInterestStartDate);
			devChargeMasterDataInterestDetails
					.setInterestEndDt(validInterestEndDate);

			DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails = new DevChargeMasterDataRebateDetails();
			devChargeMasterDataRebateDetails.setRebateEndDt(validRebateEndDate);
			devChargeMasterDataRebateDetails
					.setRebateFlatRate(rebateFlatRateNewColony);
			devChargeMasterDataRebateDetails
					.setRebatePercentage(rebatePercentageNewColony);
			devChargeMasterDataRebateDetails
					.setRebateStartDt(validRebateStartDate);
			devChargeMasterDataRebateDetails.setRebateType(rebateTypeNewColony);

			DevChargeMasterDataDAO.saveNewColonyDetail(
					devChargeMasterDataColonyDetails,
					devChargeMasterDataRateDetails,
					devChargeMasterDataInterestDetails,
					devChargeMasterDataRebateDetails, userId);
			devChargeMasterDataColonyDetails = DevChargeMasterDataDAO
					.getSavedNewColonyId(devChargeMasterDataColonyDetails,
							userId);
			if (null != devChargeMasterDataColonyDetails
					&& null != devChargeMasterDataColonyDetails.getColonyId()
					&& devChargeMasterDataColonyDetails.getColonyId().trim()
							.length() > 0) {
				inputStream = new StringBufferInputStream(
						"<font color='green' size='2'><li><span><b>New Colony Details Saved Successfully.</b></font>|"
								+ devChargeMasterDataColonyDetails
										.getColonyId().trim());
			} else {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * This method is to save rate detail row in the searched colony for dev
	 * charge, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String saveRateDetailRow(String userId) {
		AppLog.begin();
		try {
			AppLog.info("applicablRateRow:" + applicablRateRow
					+ ":rebateEligibilityRow:" + rebateEligibilityRow
					+ ":rateMinAreaRow:" + rateMinAreaRow + ":rateTypeRow:"
					+ rateTypeRow + ":rateCategoryRow:" + rateCategoryRow
					+ ":rateStartDateRow:" + rateStartDateRow
					+ ":rateEndDateRow:" + rateEndDateRow + ":rateMaxAreaRow:"
					+ rateMaxAreaRow + ":colonyId:" + colonyId
					+ ":rateInterestEligibilityRow:"
					+ rateInterestEligibilityRow);

			if (UtilityForAll.isEmpty(applicablRateRow)
					|| UtilityForAll.isEmpty(rebateEligibilityRow)
					|| UtilityForAll.isEmpty(rateTypeRow)
					|| UtilityForAll.isEmpty(rateStartDateRow)
					|| UtilityForAll.isEmpty(rateEndDateRow)
					|| UtilityForAll.isEmpty(rateInterestEligibilityRow)
					|| UtilityForAll.isEmpty(colonyId)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validRateStartDateRow = UtilityForAll.changeDateFormat(
					rateStartDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRateStartDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validRateEndDateRow = UtilityForAll.changeDateFormat(
					rateEndDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRateEndDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}

			DevChargeMasterDataRateDetails devChargeMasterDataRateDetails = new DevChargeMasterDataRateDetails();
			devChargeMasterDataRateDetails.setApplicableRate(applicablRateRow);
			devChargeMasterDataRateDetails
					.setCustomerClassCode(rateCategoryRow);
			devChargeMasterDataRateDetails.setRateEndDt(validRateEndDateRow);
			devChargeMasterDataRateDetails.setRateMaxPlotArea(rateMaxAreaRow);
			devChargeMasterDataRateDetails.setRateMinPlotArea(rateMinAreaRow);
			devChargeMasterDataRateDetails
					.setRateStartDt(validRateStartDateRow);
			devChargeMasterDataRateDetails.setRateTypeCode(rateTypeRow);
			devChargeMasterDataRateDetails
					.setRebateEligibility(rebateEligibilityRow);
			devChargeMasterDataRateDetails
					.setRateInterestEligibity(rateInterestEligibilityRow);

			DevChargeMasterDataDAO.saveRateDetailRow(
					devChargeMasterDataRateDetails, colonyId, userId);

			if (DevChargeMasterDataDAO.getSavedRateRowCount(colonyId, userId)) {
				inputStream = new StringBufferInputStream(
						"<font color='green' size='2'><li><span><b>Rate Details Saved Successfully.</b></font>");
			} else {
				String errorMsg = DevChargeMasterDataDAO.getErrorMessage(
						colonyId, userId, "CM_CREATE_RATE");
				if (null != errorMsg && errorMsg.trim().length() > 0) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR: "
									+ errorMsg + ".</b></font>");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * This method is to save rebate detail row in the searched colony for dev
	 * charge, Jtrac DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String saveRebateDetailRow(String userId) {
		AppLog.begin();
		try {
			AppLog.info("rebateTypeRow:" + rebateTypeRow
					+ ":rebatePercentageRow:" + rebatePercentageRow
					+ ":rebateFlatRateRow:" + rebateFlatRateRow
					+ ":rebateStartDateRow:" + rebateStartDateRow
					+ ":rebateEndDateRow:" + rebateEndDateRow + ":colonyId:"
					+ colonyId);
			/**
			 * to check the mandatory inputs
			 */
			if (UtilityForAll.isEmpty(rebateTypeRow)
					|| (DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD
							.equalsIgnoreCase(rebateTypeRow) && UtilityForAll
							.isEmpty(rebatePercentageRow))
					|| (DJBConstants.DEV_CHRG_FLAT_REBATE_CD
							.equalsIgnoreCase(rebateTypeRow) && UtilityForAll
							.isEmpty(rebateFlatRateRow))
					|| UtilityForAll.isEmpty(rebateStartDateRow)
					|| UtilityForAll.isEmpty(rebateEndDateRow)
					|| UtilityForAll.isEmpty(colonyId)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validRebateStartDateRow = UtilityForAll.changeDateFormat(
					rebateStartDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG
					.equalsIgnoreCase(validRebateStartDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			String validRebateEndDateRow = UtilityForAll.changeDateFormat(
					rebateEndDateRow, "dd/MM/yyyy", "dd-MM-yyyy");
			if (DJBConstants.ERROR_FLG.equalsIgnoreCase(validRebateEndDateRow)) {
				inputStream = new StringBufferInputStream(
						"<font color='red' size='2'><li><span><b>ERROR:Invalid Inputs.</b></font>");
				AppLog.end();
				return "SUCCESS";
			}
			DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails = new DevChargeMasterDataRebateDetails();
			/**
			 * to set rebate details
			 */
			devChargeMasterDataRebateDetails
					.setRebateEndDt(validRebateEndDateRow);
			devChargeMasterDataRebateDetails
					.setRebateFlatRate(rebateFlatRateRow);
			devChargeMasterDataRebateDetails
					.setRebatePercentage(rebatePercentageRow);
			devChargeMasterDataRebateDetails
					.setRebateStartDt(validRebateStartDateRow);
			devChargeMasterDataRebateDetails.setRebateType(rebateTypeRow);

			DevChargeMasterDataDAO.saveRebateDetailRow(
					devChargeMasterDataRebateDetails, colonyId, userId);

			if (DevChargeMasterDataDAO.getSavedRebateRowCount(colonyId, userId)) {
				inputStream = new StringBufferInputStream(
						"<font color='green' size='2'><li><span><b>Rebate Details Saved Successfully.</b></font>");
			} else {
				String errorMsg = DevChargeMasterDataDAO.getErrorMessage(
						colonyId, userId, "CM_CREATE_REBATE");
				if (null != errorMsg && errorMsg.trim().length() > 0) {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR: "
									+ errorMsg + ".</b></font>");
				} else {
					inputStream = new StringBufferInputStream(
							"<font color='red' size='2'><li><span><b>ERROR:Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
				}
			}
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);
		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * This method is to get the list of matching colonies for dev charge, Jtrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @since 03-08-2015
	 * @author 556885
	 */
	public String searchDevChargeColonyList() {
		AppLog.begin();
		AppLog.info(":Colony Name Option:" + option + ":selected ZRO:"
				+ selectedZRO);
		try {
			StringBuffer responseSB = new StringBuffer(512);
			ArrayList<DevChargeMasterDataColonyDetails> devChargeMasterDataColonyDetailsList = new ArrayList<DevChargeMasterDataColonyDetails>();
			devChargeMasterDataColonyDetailsList = DevChargeMasterDataDAO
					.getColonyList(option, selectedZRO);
			if (null != devChargeMasterDataColonyDetailsList
					&& devChargeMasterDataColonyDetailsList.size() > 0) {
				for (int i = 0; i < devChargeMasterDataColonyDetailsList.size(); i++) {
					responseSB.append(devChargeMasterDataColonyDetailsList.get(
							i).getColonyName()
							+ "-"
							+ devChargeMasterDataColonyDetailsList.get(i)
									.getColonyId() + ":");
				}
			} else {
				responseSB.append("INVALID");
			}
			inputStream = new StringBufferInputStream(responseSB.toString());
		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * <p>
	 * Search Dev Charge master Data Details.
	 * </p>
	 * 
	 * @return
	 */
	public String searchDevChargeMasterDataDetails() {
		AppLog.begin();
		AppLog.info("Selected ZRO:" + selectedZRO + ":Colony Id:" + colonyId);
		Map<String, Object> session = ActionContext.getContext().getSession();
		try {
			StringBuffer responseSB = new StringBuffer(512);

			/**
			 * get dev charge master data colony details
			 */
			StringBuffer responseSBColonyDetails = new StringBuffer(512);
			DevChargeMasterDataColonyDetails devChargeMasterDataColonyDetails = new DevChargeMasterDataColonyDetails();
			devChargeMasterDataColonyDetails.setColonyId(colonyId);
			devChargeMasterDataColonyDetails = DevChargeMasterDataDAO
					.getMasterDataColonyDetails(devChargeMasterDataColonyDetails);
			responseSBColonyDetails.append(devChargeMasterDataColonyDetails
					.getChargeType()
					+ ","
					+ UtilityForAll.changeDateFormat(
							devChargeMasterDataColonyDetails
									.getNotificationDate(),
							"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
					// + devChargeMasterDataColonyDetails.getNotificationDate()
					+ ","
					+ devChargeMasterDataColonyDetails.getCategory()
					+ "," + devChargeMasterDataColonyDetails.getStatus());

			/**
			 * get dev charge master data rate details
			 */
			StringBuffer responseSBRateDetails = new StringBuffer(512);
			DevChargeMasterDataRateDetails devChargeMasterDataRateDetails = new DevChargeMasterDataRateDetails();
			List<DevChargeMasterDataRateDetails> devChargeMasterDataRateDetailsList = DevChargeMasterDataDAO
					.getMasterDataRateDetails(colonyId);
			if (null != devChargeMasterDataRateDetailsList
					&& devChargeMasterDataRateDetailsList.size() > 0) {
				responseSBRateDetails
						.append("<table class=\"table-grid\" id=\"rateTable\" >");
				responseSBRateDetails.append("<tr>");
				responseSBRateDetails.append("</tr>");
				responseSBRateDetails.append("<tr>");
				responseSBRateDetails.append("<th width=\"5%\">SL</th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Rate/Sqmt.<font color='red'><sup>*</sup></font></th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Interest Eligibility<font color='red'><sup>*</sup></font></th>");
				responseSBRateDetails
						.append("<th width=\"15%\">Rebate Eligibility<font color='red'><sup>*</sup></font></th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Type<font color='red'><sup>*</sup></font></th>");
				responseSBRateDetails.append("<th width=\"10%\">Category</th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Min. Area</th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Max. Area</th>");
				responseSBRateDetails
						.append("<th width=\"10%\">Start Date<font color='red'><sup>*</sup></font></th>");
				responseSBRateDetails
						.append("<th width=\"10%\">End Date<font color='red'><sup>*</sup></font></th>");
				// responseSBRateDetails
				// .append("<th width=\"10%\">Doc Proof</th>");
				responseSBRateDetails.append("</tr>");
				for (int i = 0; i < devChargeMasterDataRateDetailsList.size(); i++) {
					devChargeMasterDataRateDetails = devChargeMasterDataRateDetailsList
							.get(i);
					responseSBRateDetails
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					responseSBRateDetails.append("<td align=\"center\">"
							+ (i + 1) + "</td>");
					responseSBRateDetails.append("<td align=\"center\">"
							+ devChargeMasterDataRateDetails
									.getApplicableRate() + "</td>");
					HashMap<String, String> InterestEligibilityListMap = new HashMap<String, String>();
					InterestEligibilityListMap = (HashMap<String, String>) session
							.get("InterestEligibilityListMap");
					responseSBRateDetails.append("<td align=\"center\">"
							+ (null == InterestEligibilityListMap
									.get(devChargeMasterDataRateDetails
											.getRateInterestEligibity()) ? ""
									: InterestEligibilityListMap
											.get(devChargeMasterDataRateDetails
													.getRateInterestEligibity()
													.trim())) + "</td>");
					if (null != devChargeMasterDataRateDetails
							&& null != devChargeMasterDataRateDetails
									.getRateInterestEligibity()
							&& DJBConstants.FLAG_Y
									.equalsIgnoreCase(devChargeMasterDataRateDetails
											.getRateInterestEligibity().trim())) {
						responseSBRateDetails
								.append("<input type=\"hidden\" name=\"rateInterestEligibilityRowFlag\" id=\"rateInterestEligibilityRowFlag\" value=\"Y\" />");
						// responseSBRateDetails.append("<td align=\"center\">"
						// + DJBConstants.PARAM_YES + "</td>");
					}
					// else {
					// responseSBRateDetails.append("<td align=\"center\">"
					// + DJBConstants.PARAM_NO + "</td>");
					// }
					HashMap<String, String> RebateEligibilityListMap = new HashMap<String, String>();
					RebateEligibilityListMap = (HashMap<String, String>) session
							.get("RebateEligibilityListMap");
					responseSBRateDetails.append("<td align=\"center\">"
							+ (null == RebateEligibilityListMap
									.get(devChargeMasterDataRateDetails
											.getRebateEligibility()) ? ""
									: RebateEligibilityListMap
											.get(devChargeMasterDataRateDetails
													.getRebateEligibility()
													.trim())) + "</td>");
					if (null != devChargeMasterDataRateDetails
							&& null != devChargeMasterDataRateDetails
									.getRebateEligibility()
							&& DJBConstants.FLAG_Y
									.equalsIgnoreCase(devChargeMasterDataRateDetails
											.getRebateEligibility().trim())) {
						responseSBRateDetails
								.append("<input type=\"hidden\" name=\"rebateEligibilityRowFlag\" id=\"rebateEligibilityRowFlag\" value=\"Y\" />");
						// responseSBRateDetails.append("<td align=\"center\">"
						// + DJBConstants.PARAM_YES + "</td>");
					}
					// else {
					// responseSBRateDetails.append("<td align=\"center\">"
					// + DJBConstants.PARAM_NO + "</td>");
					// }

					HashMap<String, String> RateTypeListMap = new HashMap<String, String>();
					RateTypeListMap = (HashMap<String, String>) session
							.get("RateTypeListMap");
					responseSBRateDetails.append("<td align=\"center\">"
							+ (null == RateTypeListMap
									.get(devChargeMasterDataRateDetails
											.getRateTypeCode()) ? ""
									: RateTypeListMap
											.get(devChargeMasterDataRateDetails
													.getRateTypeCode().trim()))
							+ "</td>");
					// if (null != devChargeMasterDataRateDetails
					// && null != devChargeMasterDataRateDetails
					// .getRateTypeCode()
					// && DJBConstants.DEV_CHRG_RATE_TYP_REGULAR_CD
					// .equalsIgnoreCase(devChargeMasterDataRateDetails
					// .getRateTypeCode().trim())) {
					// responseSBRateDetails.append("<td align=\"center\">"
					// + DJBConstants.DEV_CHRG_RATE_TYP_REGULAR_PARAM
					// + "</td>");
					// } else if (null != devChargeMasterDataRateDetails
					// && null != devChargeMasterDataRateDetails
					// .getRateTypeCode()
					// && DJBConstants.DEV_CHRG_RATE_TYP_SCHEME_CD
					// .equalsIgnoreCase(devChargeMasterDataRateDetails
					// .getRateTypeCode().trim())) {
					// responseSBRateDetails.append("<td align=\"center\">"
					// + DJBConstants.DEV_CHRG_RATE_TYP_SCHEME_PARAM
					// + "</td>");
					// }
					HashMap<String, String> CatClassListMap = new HashMap<String, String>();
					CatClassListMap = (HashMap<String, String>) session
							.get("CatClassListMap");
					responseSBRateDetails.append("<td align=\"center\">"
							+ (null == CatClassListMap
									.get(devChargeMasterDataRateDetails
											.getCustomerClassCode()) ? ""
									: CatClassListMap
											.get(devChargeMasterDataRateDetails
													.getCustomerClassCode()
													.trim())) + "</td>");
					// responseSBRateDetails.append("<td align=\"center\">"
					// + devChargeMasterDataRateDetails
					// .getCustomerClassCode() + "</td>");
					responseSBRateDetails.append("<td align=\"center\">"
							+ devChargeMasterDataRateDetails
									.getRateMinPlotArea() + "</td>");
					responseSBRateDetails.append("<td align=\"center\">"
							+ devChargeMasterDataRateDetails
									.getRateMaxPlotArea() + "</td>");
					responseSBRateDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataRateDetails
											.getRateStartDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// + devChargeMasterDataRateDetails.getRateStartDt()
							+ "</td>");
					responseSBRateDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataRateDetails
											.getRateEndDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// + devChargeMasterDataRateDetails.getRateEndDt()
							+ "</td>");
					// responseSBRateDetails
					// .append("<td align=\"center\"><a href="
					// + (null == devChargeMasterDataRateDetails
					// .getRateDocProof() ? "\"#\""
					// : devChargeMasterDataRateDetails
					// .getRateDocProof())
					// + ">Doc</a></td>");
					responseSBRateDetails.append("</tr>");
				}
			} else {
				responseSBRateDetails
						.append("<font color='red'><b>No records Found to Display!</b></font><br/>");
			}

			/**
			 * get dev charge master data interest details
			 */
			StringBuffer responseSBInterestDetails = new StringBuffer(512);
			DevChargeMasterDataInterestDetails devChargeMasterDataInterestDetails = new DevChargeMasterDataInterestDetails();
			List<DevChargeMasterDataInterestDetails> devChargeMasterDataInterestDetailsList = DevChargeMasterDataDAO
					.getMasterDataInterestDetails(colonyId);
			if (null != devChargeMasterDataInterestDetailsList
					&& devChargeMasterDataInterestDetailsList.size() > 0) {

				responseSBInterestDetails
						.append("<table class=\"table-grid\" id=\"interestTable\" >");
				responseSBInterestDetails.append("<tr>");
				// responseSBInterestDetails
				// .append("<th align=\"center\" colspan=\"11\">Interest Details</th>");
				responseSBInterestDetails.append("</tr>");
				responseSBInterestDetails.append("<tr>");
				responseSBInterestDetails.append("<th width=\"25%\">SL</th>");
				responseSBInterestDetails
						.append("<th width=\"25%\">Interest Percentage<font color='red'><sup>*</sup></font></th>");
				responseSBInterestDetails
						.append("<th width=\"25%\">Effective Start Date<font color='red'><sup>*</sup></font></th>");
				responseSBInterestDetails
						.append("<th width=\"25%\">Effective End Date<font color='red'><sup>*</sup></font></th>");
				// responseSBInterestDetails
				// .append("<th width=\"20%\">Upload Document Proof</th>");
				// responseSBInterestDetails
				responseSBInterestDetails.append("</tr>");
				for (int i = 0; i < devChargeMasterDataInterestDetailsList
						.size(); i++) {
					devChargeMasterDataInterestDetails = devChargeMasterDataInterestDetailsList
							.get(i);
					responseSBInterestDetails
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					responseSBInterestDetails.append("<td align=\"center\">"
							+ (i + 1) + "</td>");
					responseSBInterestDetails.append("<td align=\"center\">"
							+ devChargeMasterDataInterestDetails
									.getInterestPercentage() + "</td>");
					responseSBInterestDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataInterestDetails
											.getInterestStartDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// +
							// devChargeMasterDataInterestDetails.getInterestStartDt()
							+ "</td>");
					responseSBInterestDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataInterestDetails
											.getInterestEndDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// +
							// devChargeMasterDataInterestDetails.getInterestEndDt()
							+ "</td>");
					// responseSBInterestDetails
					// .append("<td align=\"center\"><a href="
					// + (null == devChargeMasterDataInterestDetails
					// .getInterestDocProof() ? "\"#\""
					// : devChargeMasterDataInterestDetails
					// .getInterestDocProof())
					// + ">Doc</a></td>");
					responseSBInterestDetails.append("</tr>");
				}
			} else {
				responseSBInterestDetails
						.append("<font color='red'><b>No records Found to Display!</b></font><br/>");
			}

			/**
			 * get dev charge master data rebate details
			 */
			StringBuffer responseSBRebateDetails = new StringBuffer(512);
			DevChargeMasterDataRebateDetails devChargeMasterDataRebateDetails = new DevChargeMasterDataRebateDetails();
			List<DevChargeMasterDataRebateDetails> devChargeMasterDataRebateDetailsList = DevChargeMasterDataDAO
					.getMasterDataRebateDetails(devChargeMasterDataColonyDetails
							.getColonyId());
			if (null != devChargeMasterDataRebateDetailsList
					&& devChargeMasterDataRebateDetailsList.size() > 0) {
				responseSBRebateDetails
						.append("<table class=\"table-grid\" id=\"rebateTable\" >");
				responseSBRebateDetails.append("<tr>");
				responseSBRebateDetails.append("</tr>");
				responseSBRebateDetails.append("<tr>");
				responseSBRebateDetails.append("<th width=\"10%\">SL</th>");
				responseSBRebateDetails
						.append("<th width=\"15%\">Rebate Type<font color='red'><sup>*</sup></font></th>");
				responseSBRebateDetails
						.append("<th width=\"20%\">Rebate Percentage<font color='red'><sup>*</sup></font></th>");
				responseSBRebateDetails
						.append("<th width=\"15%\">Rebate Flat Rate<font color='red'><sup>*</sup></font></th>");
				responseSBRebateDetails
						.append("<th width=\"20%\">Effective Start Date<font color='red'><sup>*</sup></font></th>");
				responseSBRebateDetails
						.append("<th width=\"20%\">Effective End Date<font color='red'><sup>*</sup></font></th>");
				// responseSBRebateDetails
				// .append("<th width=\"15%\">Upload Document Proof</th>");
				responseSBRebateDetails.append("</tr>");
				for (int i = 0; i < devChargeMasterDataRebateDetailsList.size(); i++) {
					devChargeMasterDataRebateDetails = devChargeMasterDataRebateDetailsList
							.get(i);
					responseSBRebateDetails
							.append("<tr bgcolor=\"white\"	onMouseOver=\"javascript:this.bgColor= 'yellow'\" onMouseOut=\"javascript:this.bgColor='white'\">");
					responseSBRebateDetails.append("<td align=\"center\">"
							+ (i + 1) + "</td>");
					HashMap<String, String> RebateTypeListMap = new HashMap<String, String>();
					RebateTypeListMap = (HashMap<String, String>) session
							.get("RebateTypeListMap");
					responseSBRebateDetails
							.append("<td align=\"center\">"
									+ (null == RebateTypeListMap
											.get(devChargeMasterDataRebateDetails
													.getRebateType()) ? ""
											: RebateTypeListMap
													.get(devChargeMasterDataRebateDetails
															.getRebateType()
															.trim())) + "</td>");
					// if (null != devChargeMasterDataRebateDetails
					// && null != devChargeMasterDataRebateDetails
					// .getRebateType()
					// && DJBConstants.DEV_CHRG_FLAT_REBATE_CD
					// .equalsIgnoreCase(devChargeMasterDataRebateDetails
					// .getRebateType().trim())) {
					// responseSBRebateDetails.append("<td align=\"center\">"
					// + DJBConstants.DEV_CHRG_FLAT_REBATE_PARAM
					// + "</td>");
					// } else if (null != devChargeMasterDataRebateDetails
					// && null != devChargeMasterDataRebateDetails
					// .getRebateType()
					// && DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD
					// .equalsIgnoreCase(devChargeMasterDataRebateDetails
					// .getRebateType().trim())) {
					// responseSBRebateDetails.append("<td align=\"center\">"
					// + DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_PARAM
					// + "</td>");
					// } else {
					// responseSBRebateDetails
					// .append("<td align=\"center\"></td>");
					// }
					responseSBRebateDetails.append("<td align=\"center\">"
							+ devChargeMasterDataRebateDetails
									.getRebatePercentage() + "</td>");
					responseSBRebateDetails.append("<td align=\"center\">"
							+ devChargeMasterDataRebateDetails
									.getRebateFlatRate() + "</td>");
					responseSBRebateDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataRebateDetails
											.getRebateStartDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// +
							// devChargeMasterDataRebateDetails.getRebateStartDt()
							+ "</td>");
					responseSBRebateDetails.append("<td align=\"center\">"
							+ UtilityForAll.changeDateFormat(
									devChargeMasterDataRebateDetails
											.getRebateEndDt(),
									"yyyy-MM-dd hh:mm:ss.S", "dd/MM/yyyy")
							// +
							// devChargeMasterDataRebateDetails.getRebateEndDt()
							+ "</td>");
					// responseSBRebateDetails
					// .append("<td align=\"center\"><a href="
					// + (null == devChargeMasterDataRebateDetails
					// .getRebateDocProof() ? "\"#\""
					// : devChargeMasterDataRebateDetails
					// .getRebateDocProof())
					// + ">Doc</a></td>");
					responseSBRebateDetails.append("</tr>");
				}
			} else {
				responseSBRebateDetails
						.append("<font color='red'><b>No records Found to Display!</b></font><br/>");
			}
			responseSB.append(responseSBColonyDetails + "|"
					+ responseSBRateDetails + "|" + responseSBInterestDetails
					+ "|" + responseSBRebateDetails);
			AppLog.info(responseSB);
			inputStream = new StringBufferInputStream(responseSB.toString());

		} catch (Exception e) {
			inputStream = new StringBufferInputStream(
					"<font color='red' size='2'><li><span><b>ERROR:Sorry, Unable to process your request.Please retry after sometime or contact System Administrator.</b></font>");
			AppLog.error(e);
			return SUCCESS;

		}
		AppLog.end();
		return SUCCESS;
	}

	/**
	 * @param applicableRateNewColony
	 *            the applicableRateNewColony to set
	 */
	public void setApplicableRateNewColony(String applicableRateNewColony) {
		this.applicableRateNewColony = applicableRateNewColony;
	}

	/**
	 * @param applicablRateRow
	 *            the applicablRateRow to set
	 */
	public void setApplicablRateRow(String applicablRateRow) {
		this.applicablRateRow = applicablRateRow;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param chargeType
	 *            the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @param colonyId
	 *            the colonyId to set
	 */
	public void setColonyId(String colonyId) {
		this.colonyId = colonyId;
	}

	/**
	 * @param colonyName
	 *            the colonyName to set
	 */
	public void setColonyName(String colonyName) {
		this.colonyName = colonyName;
	}

	/**
	 * @param hidAction
	 *            the hidAction to set
	 */
	public void setHidAction(String hidAction) {
		this.hidAction = hidAction;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @param interestDocNewColony
	 *            the interestDocNewColony to set
	 */
	public void setInterestDocNewColony(String interestDocNewColony) {
		this.interestDocNewColony = interestDocNewColony;
	}

	/**
	 * @param interestDocRow
	 *            the interestDocRow to set
	 */
	public void setInterestDocRow(String interestDocRow) {
		this.interestDocRow = interestDocRow;
	}

	/**
	 * @param interestEndDateNewColony
	 *            the interestEndDateNewColony to set
	 */
	public void setInterestEndDateNewColony(String interestEndDateNewColony) {
		this.interestEndDateNewColony = interestEndDateNewColony;
	}

	/**
	 * @param interestEndDateRow
	 *            the interestEndDateRow to set
	 */
	public void setInterestEndDateRow(String interestEndDateRow) {
		this.interestEndDateRow = interestEndDateRow;
	}

	/**
	 * @param interestPercentageNewColony
	 *            the interestPercentageNewColony to set
	 */
	public void setInterestPercentageNewColony(
			String interestPercentageNewColony) {
		this.interestPercentageNewColony = interestPercentageNewColony;
	}

	/**
	 * @param interestPercentageRow
	 *            the interestPercentageRow to set
	 */
	public void setInterestPercentageRow(String interestPercentageRow) {
		this.interestPercentageRow = interestPercentageRow;
	}

	/**
	 * @param interestStartDateNewColony
	 *            the interestStartDateNewColony to set
	 */
	public void setInterestStartDateNewColony(String interestStartDateNewColony) {
		this.interestStartDateNewColony = interestStartDateNewColony;
	}

	/**
	 * @param interestStartDateRow
	 *            the interestStartDateRow to set
	 */
	public void setInterestStartDateRow(String interestStartDateRow) {
		this.interestStartDateRow = interestStartDateRow;
	}

	/**
	 * @param notificationDate
	 *            the notificationDate to set
	 */
	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @param rateCategoryNewColony
	 *            the rateCategoryNewColony to set
	 */
	public void setRateCategoryNewColony(String rateCategoryNewColony) {
		this.rateCategoryNewColony = rateCategoryNewColony;
	}

	/**
	 * @param rateCategoryRow
	 *            the rateCategoryRow to set
	 */
	public void setRateCategoryRow(String rateCategoryRow) {
		this.rateCategoryRow = rateCategoryRow;
	}

	/**
	 * @param rateDocNewColony
	 *            the rateDocNewColony to set
	 */
	public void setRateDocNewColony(String rateDocNewColony) {
		this.rateDocNewColony = rateDocNewColony;
	}

	/**
	 * @param rateDocProofRow
	 *            the rateDocProofRow to set
	 */
	public void setRateDocProofRow(String rateDocProofRow) {
		this.rateDocProofRow = rateDocProofRow;
	}

	/**
	 * @param rateEndDateNewColony
	 *            the rateEndDateNewColony to set
	 */
	public void setRateEndDateNewColony(String rateEndDateNewColony) {
		this.rateEndDateNewColony = rateEndDateNewColony;
	}

	/**
	 * @param rateEndDateRow
	 *            the rateEndDateRow to set
	 */
	public void setRateEndDateRow(String rateEndDateRow) {
		this.rateEndDateRow = rateEndDateRow;
	}

	/**
	 * @param interestEligibilityNewColony
	 *            the interestEligibilityNewColony to set
	 */
	public void setRateInterestEligibilityNewColony(
			String rateInterestEligibilityNewColony) {
		this.rateInterestEligibilityNewColony = rateInterestEligibilityNewColony;
	}

	/**
	 * @param rateInterestEligibilityRow
	 *            the rateInterestEligibilityRow to set
	 */
	public void setRateInterestEligibilityRow(String rateInterestEligibilityRow) {
		this.rateInterestEligibilityRow = rateInterestEligibilityRow;
	}

	/**
	 * @param rateMaxAreaNewColony
	 *            the rateMaxAreaNewColony to set
	 */
	public void setRateMaxAreaNewColony(String rateMaxAreaNewColony) {
		this.rateMaxAreaNewColony = rateMaxAreaNewColony;
	}

	/**
	 * @param rateMaxAreaRow
	 *            the rateMaxAreaRow to set
	 */
	public void setRateMaxAreaRow(String rateMaxAreaRow) {
		this.rateMaxAreaRow = rateMaxAreaRow;
	}

	/**
	 * @param rateMinAreaNewColony
	 *            the rateMinAreaNewColony to set
	 */
	public void setRateMinAreaNewColony(String rateMinAreaNewColony) {
		this.rateMinAreaNewColony = rateMinAreaNewColony;
	}

	/**
	 * @param rateMinAreaRow
	 *            the rateMinAreaRow to set
	 */
	public void setRateMinAreaRow(String rateMinAreaRow) {
		this.rateMinAreaRow = rateMinAreaRow;
	}

	/**
	 * @param rateStartDateNewColony
	 *            the rateStartDateNewColony to set
	 */
	public void setRateStartDateNewColony(String rateStartDateNewColony) {
		this.rateStartDateNewColony = rateStartDateNewColony;
	}

	/**
	 * @param rateStartDateRow
	 *            the rateStartDateRow to set
	 */
	public void setRateStartDateRow(String rateStartDateRow) {
		this.rateStartDateRow = rateStartDateRow;
	}

	/**
	 * @param rateTypeNewColony
	 *            the rateTypeNewColony to set
	 */
	public void setRateTypeNewColony(String rateTypeNewColony) {
		this.rateTypeNewColony = rateTypeNewColony;
	}

	/**
	 * @param rateTypeRow
	 *            the rateTypeRow to set
	 */
	public void setRateTypeRow(String rateTypeRow) {
		this.rateTypeRow = rateTypeRow;
	}

	/**
	 * @param rebateDocNewColony
	 *            the rebateDocNewColony to set
	 */
	public void setRebateDocNewColony(String rebateDocNewColony) {
		this.rebateDocNewColony = rebateDocNewColony;
	}

	/**
	 * @param rebateDocRow
	 *            the rebateDocRow to set
	 */
	public void setRebateDocRow(String rebateDocRow) {
		this.rebateDocRow = rebateDocRow;
	}

	/**
	 * @param rebateEligibilityNewColony
	 *            the rebateEligibilityNewColony to set
	 */
	public void setRebateEligibilityNewColony(String rebateEligibilityNewColony) {
		this.rebateEligibilityNewColony = rebateEligibilityNewColony;
	}

	/**
	 * @param rebateEligibilityRow
	 *            the rebateEligibilityRow to set
	 */
	public void setRebateEligibilityRow(String rebateEligibilityRow) {
		this.rebateEligibilityRow = rebateEligibilityRow;
	}

	/**
	 * @param rebateEndDateNewColony
	 *            the rebateEndDateNewColony to set
	 */
	public void setRebateEndDateNewColony(String rebateEndDateNewColony) {
		this.rebateEndDateNewColony = rebateEndDateNewColony;
	}

	/**
	 * @param rebateEndDateRow
	 *            the rebateEndDateRow to set
	 */
	public void setRebateEndDateRow(String rebateEndDateRow) {
		this.rebateEndDateRow = rebateEndDateRow;
	}

	/**
	 * @param rebateFlatRateNewColony
	 *            the rebateFlatRateNewColony to set
	 */
	public void setRebateFlatRateNewColony(String rebateFlatRateNewColony) {
		this.rebateFlatRateNewColony = rebateFlatRateNewColony;
	}

	/**
	 * @param rebateFlatRateRow
	 *            the rebateFlatRateRow to set
	 */
	public void setRebateFlatRateRow(String rebateFlatRateRow) {
		this.rebateFlatRateRow = rebateFlatRateRow;
	}

	/**
	 * @param rebatePercentageNewColony
	 *            the rebatePercentageNewColony to set
	 */
	public void setRebatePercentageNewColony(String rebatePercentageNewColony) {
		this.rebatePercentageNewColony = rebatePercentageNewColony;
	}

	/**
	 * @param rebatePercentageRow
	 *            the rebatePercentageRow to set
	 */
	public void setRebatePercentageRow(String rebatePercentageRow) {
		this.rebatePercentageRow = rebatePercentageRow;
	}

	/**
	 * @param rebateStartDateNewColony
	 *            the rebateStartDateNewColony to set
	 */
	public void setRebateStartDateNewColony(String rebateStartDateNewColony) {
		this.rebateStartDateNewColony = rebateStartDateNewColony;
	}

	/**
	 * @param rebateStartDateRow
	 *            the rebateStartDateRow to set
	 */
	public void setRebateStartDateRow(String rebateStartDateRow) {
		this.rebateStartDateRow = rebateStartDateRow;
	}

	/**
	 * @param rebateTypeNewColony
	 *            the rebateTypeNewColony to set
	 */
	public void setRebateTypeNewColony(String rebateTypeNewColony) {
		this.rebateTypeNewColony = rebateTypeNewColony;
	}

	/**
	 * @param rebateTypeRow
	 *            the rebateTypeRow to set
	 */
	public void setRebateTypeRow(String rebateTypeRow) {
		this.rebateTypeRow = rebateTypeRow;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param selectedZRO
	 *            the selectedZRO to set
	 */
	public void setSelectedZRO(String selectedZRO) {
		this.selectedZRO = selectedZRO;
	}

	@Override
	/**
	 * @param response
	 *            the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
