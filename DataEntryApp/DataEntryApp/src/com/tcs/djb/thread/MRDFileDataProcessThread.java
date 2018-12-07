/************************************************************************************************************
 * @(#) MRDFileDataProcessThread.java   02 Jun 2016
 *
 *************************************************************************************************************/
package com.tcs.djb.thread;

import java.util.List;

import com.tcs.djb.constants.DJBConstants;
import com.tcs.djb.model.AMRRecordDetail;
import com.tcs.djb.services.AMRDataUploadService;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This thread is for processing MRD File data's as JTrac DJB-4465 and
 * OpenProject-918.
 * </p>
 * 
 * @author Atanu Mondal(Tata Consultancy Services)
 * @since 02-06-2016 as per open project 1202,jTrac : DJB-4464
 * 
 */
public class MRDFileDataProcessThread implements Runnable {

	private List<AMRRecordDetail> aMRRecordDetailList;
	private String authCookie;
	private String hhdId;
	private boolean isSuccess;
	private String userID;

	/**
	 * <p>
	 * Default constructor.
	 * </p>
	 * 
	 * @param aMRRecordDetailListParam
	 * @param userIdParam
	 * @param authCookie
	 * @param hhdId
	 */
	public MRDFileDataProcessThread(
			List<AMRRecordDetail> aMRRecordDetailListParam, String userIdParam,
			String authCookie, String hhdId) {
		AppLog.begin();
		this.aMRRecordDetailList = aMRRecordDetailListParam;
		this.userID = userIdParam;
		this.authCookie = authCookie;
		this.hhdId = hhdId;
		new Thread(this).start();
		AppLog.end();
	}

	/**
	 * @return the aMRRecordDetailList
	 */
	public List<AMRRecordDetail> getaMRRecordDetailList() {
		return aMRRecordDetailList;
	}

	/**
	 * @return the hhdId
	 */
	public String getHhdId() {
		return hhdId;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		AppLog.begin();
		AppLog.info("START:: New Thread for Process AMR Record");
		try {
			if (null != this.aMRRecordDetailList
					&& this.aMRRecordDetailList.size() > 0) {
				AppLog.info("New thread started with list size::"
						+ this.aMRRecordDetailList.size());
				int maxNumOfRecordsofEveryServiceCall = Integer
						.parseInt(DJBConstants.AMR_MAX_NO_OF_RECORDS_FOR_UPLOAD_SERVICE);
				int fromIndex = 0;
				int lastIndexofAMRRecordDetailList = aMRRecordDetailList.size();
				while (fromIndex < lastIndexofAMRRecordDetailList) {
					if (fromIndex + maxNumOfRecordsofEveryServiceCall < lastIndexofAMRRecordDetailList) {
						AMRDataUploadService.aMRDataUpload(aMRRecordDetailList
								.subList(fromIndex, fromIndex
										+ maxNumOfRecordsofEveryServiceCall),
								this.authCookie, this.userID, this.hhdId);
					} else {
						AMRDataUploadService.aMRDataUpload(aMRRecordDetailList
								.subList(fromIndex,
										lastIndexofAMRRecordDetailList),
								this.authCookie, this.userID, this.hhdId);
					}
					fromIndex = fromIndex + maxNumOfRecordsofEveryServiceCall;
				}
			}
		} catch (Throwable t) {
			AppLog.error(t);
		}
		AppLog.info("END:: New Thread for Process AMR Record");
		AppLog.end();
	}

	/**
	 * @param aMRRecordDetailList
	 *            the aMRRecordDetailList to set
	 */
	public void setaMRRecordDetailList(List<AMRRecordDetail> aMRRecordDetailList) {
		this.aMRRecordDetailList = aMRRecordDetailList;
	}

	/**
	 * @param hhdId
	 *            the hhdId to set
	 */
	public void setHhdId(String hhdId) {
		this.hhdId = hhdId;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

}
