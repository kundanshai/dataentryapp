/************************************************************************************************************
 * @(#) OnlineBatchBillingThread.java   28 Jun 2013
 *
 *************************************************************************************************************/
package com.tcs.djb.thread;

import java.util.List;

import com.tcs.djb.dao.OnlineBatchBillingDAO;
import com.tcs.djb.model.BillGenerationDetails;
import com.tcs.djb.util.AppLog;
import com.tcs.djb.util.PostToQueueUtil;

/**
 * <p>
 * OnlineBatchBillingThread class for Online Batch Billing screen. The accounts
 * to be billed from Online Batch Bill screen is sent to the JMS Queue in a
 * separate thread for every MRD one by one.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 28-06-2013
 * @see PostToQueueUtil
 * @history 08-08-2013 Matiur Rahman Added mrKeyList as new parameter
 *          {@link#OnlineBatchBillingThread},modified
 *          {@link#initiateBatchBilling}.
 */
public class OnlineBatchBillingThread implements Runnable {
	List<BillGenerationDetails> billGenerationDetailsList;

	/**
	 * The bill Round for which records submitted for Billing.
	 */
	private String billRound;
	/**
	 * The MRD code for which records submitted for Billing.
	 */
	private String mrKey;
	/**
	 * The List of MRD code for which records submitted for Billing.
	 */
	private String mrKeyList;
	/**
	 * The user Id who has submitted records for Billing.
	 */
	private String userId;

	/**
	 * <p>
	 * Default constructor.
	 * </p>
	 * 
	 * @param mrKey
	 * @param billRound
	 * @param billGenerationDetailsList
	 * @param userId
	 */
	public OnlineBatchBillingThread(String mrKey, String billRound,
			List<BillGenerationDetails> billGenerationDetailsList, String userId) {
		AppLog.begin();
		this.mrKey = mrKey;
		this.billRound = billRound;
		this.billGenerationDetailsList = billGenerationDetailsList;
		this.userId = userId;
		if (null != billGenerationDetailsList
				&& billGenerationDetailsList.size() > 0) {
			new Thread(this).start();
		}
		AppLog.end();

	}

	/**
	 * <p>
	 * Default constructor.
	 * </p>
	 * 
	 * @param mrKey
	 * @param mrKeyList
	 * @param billRound
	 * @param userId
	 */
	public OnlineBatchBillingThread(String mrKey, String mrKeyList,
			String billRound, String userId) {
		AppLog.begin();
		this.mrKey = mrKey;
		this.mrKeyList = mrKeyList;
		this.billRound = billRound;
		this.userId = userId;
		new Thread(this).start();
		AppLog.end();

	}

	/**
	 * @return the billGenerationDetailsList
	 */
	public List<BillGenerationDetails> getBillGenerationDetailsList() {
		return billGenerationDetailsList;
	}

	/**
	 * @return the billRound
	 */
	public String getBillRound() {
		return billRound;
	}

	/**
	 * @return the mrKey
	 */
	public String getMrKey() {
		return mrKey;
	}

	/**
	 * @return the mrKeyList
	 */
	public String getMrKeyList() {
		return mrKeyList;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p>
	 * Initiate Online Batch Billing. It first Updates the
	 * <code>BILLING_CURRENT_STATUS</code> as status <code>Initiated</code> of
	 * table <code>CM_CONS_PRE_BILL_PROC</code> and then Post to the JMS Queue
	 * for Billing Application to pick up.
	 * </p>
	 * 
	 * @see OnlineBatchBillingDAO
	 */
	public void initiateBatchBilling() {
		try {
			AppLog.begin();
			// AppLog
			// .info("IN initiateThread::Bill Generation Details List Size::"
			// + billGenerationDetailsList.size());
			// int rowsUpdated = OnlineBatchBillingDAO
			// .initiateBatchBillingByMRKey(mrKey, billRound, userId);
			// if (rowsUpdated > 0) {
			billGenerationDetailsList = OnlineBatchBillingDAO
					.getOnlineBatchBillGenerationDetails(userId, billRound,
							mrKey, mrKeyList, null, null, null, null, null);
			BillGenerationDetails billGenerationDetails = null;
			// System.out.println(" billGenerationDetailsList.size()::"
			// + billGenerationDetailsList.size());
			for (int i = 0; i < billGenerationDetailsList.size(); i++) {
				billGenerationDetails = billGenerationDetailsList.get(i);
				// int rowsUpdated =
				// OnlineBatchBillingDAO.initiateBatchBilling(
				// billGenerationDetails, userId);
				// if (rowsUpdated > 0) {
				PostToQueueUtil.postToQueue(billGenerationDetails);
				// }
			}
			// } else {
			// AppLog
			// .info("There was a problem in initiating Online Batch Billing.");
			// }
			AppLog.end();
		} catch (Exception e) {
			AppLog.error(e);
			AppLog.end();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		// System.out.println("IN run");
		try {
			initiateBatchBilling();
			AppLog.end();
		} catch (Throwable t) {
			AppLog.error(t);
		}
		// System.out.println("OUT run");
		AppLog.end();
	}

	/**
	 * @param billGenerationDetailsList
	 *            the billGenerationDetailsList to set
	 */
	public void setBillGenerationDetailsList(
			List<BillGenerationDetails> billGenerationDetailsList) {
		this.billGenerationDetailsList = billGenerationDetailsList;
	}

	/**
	 * @param billRound
	 *            the billRound to set
	 */
	public void setBillRound(String billRound) {
		this.billRound = billRound;
	}

	/**
	 * @param mrKey
	 *            the mrKey to set
	 */
	public void setMrKey(String mrKey) {
		this.mrKey = mrKey;
	}

	/**
	 * @param mrKeyList
	 *            the mrKeyList to set
	 */
	public void setMrKeyList(String mrKeyList) {
		this.mrKeyList = mrKeyList;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
