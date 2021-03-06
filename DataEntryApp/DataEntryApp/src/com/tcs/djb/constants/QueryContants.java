/************************************************************************************************************
 * @(#) QueryContants.java   29 Nov 2012
 *
 *************************************************************************************************************/
package com.tcs.djb.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.tcs.djb.dao.GetterDAO;
import com.tcs.djb.dao.MeterReadDAO;
import com.tcs.djb.dao.MeterReplacementDAO;
import com.tcs.djb.dao.PreBillStatusDefinitionDAO;
import com.tcs.djb.dao.SetterDAO;
import com.tcs.djb.model.AMRHeaderDetails;
import com.tcs.djb.model.ConsumptionAuditSearchCriteria;
import com.tcs.djb.model.ImageAuditSearchCriteria;
import com.tcs.djb.model.FileNumberSearchCrieteria;
import com.tcs.djb.model.MeterReadImgAuditDetails;
import com.tcs.djb.model.MeterReaderDetails;
import com.tcs.djb.model.NewConnFileEntryDetails;
import com.tcs.djb.util.AppLog;

/**
 * <p>
 * This is a class which contains all the query calls to database made from Data Entry Application.
 * </p>
 * 
 * @author Matiur Rahman (Tata Consultancy Services) 29-11-2012
 * @history 12-02-2013 Matiur Rahman Added getMeterReplacementDetailsQuery.
 * @history 01-03-2013 Matiur Rahman Added method
 *          getOperationMRKeyReviewDetailsListQuery.
 * @history 01-03-2013 Matiur Rahman Added method
 *          getTotalCountOfOperationMRKeyReviewRecordsQuery.
 * @history 01-03-2013 Matiur Rahman Added method
 *          getNoOfAccountsToBeUpdatedQuery.
 * @history 04-03-2013 Matiur Rahman Added method updateMRKeyStatusCodeQuery.
 * @history 04-03-2013 Matiur Rahman Added method
 *          getConsPreBillStatLookupListQuery.
 * @history 04-03-2013 Matiur Rahman Added method
 *          getConsPreBillValidChangeStatusListQuery.
 * @history 04-03-2013 Matiur Rahman Added variable OPEN_BILL_ROUND_STATUS_CODE.
 * @history 05-03-2013 Matiur Rahman changed method name
 *          getConsPreBillValidChangeStatusListQuery to
 *          getFromConsPreBillValidStatLookupListQuery.
 * @history 05-03-2013 Matiur Rahman Added method
 *          getToConsPreBillValidStatLookupListQuery.
 * @history 12-03-2013 Matiur Rahman Added method getOldSATypeDetailsQuery.
 * @history 12-03-2013 Matiur Rahman Added method getMeterModelQuery.
 * @history 12-03-2013 Matiur Rahman Added method
 *          getLastActiveMeterDetailsQuery.
 * @history 12-03-2013 Matiur Rahman Added method
 *          getOldMeterRemarkCodeListQuery.
 * @history 12-03-2013 Matiur Rahman Added variable REGULAR_READ_TYPE_CODE.
 * 
 * @history 13-03-2013 Tuhin Jana Added method
 *          getPreBillStatusDefinitionListQuery.
 * @history 13-03-2013 Tuhin Jana Added method
 *          updatePreBillStatusDefinitionListQuery.
 * @history 13-03-2013 Tuhin Jana Added method validatePreBillStatusIdQuery.
 * @history 14-03-2013 Matiur Rahman Added variable BILL_GENERATED.
 * @history 14-03-2013 Matiur Rahman Added variable RECORD_ENTERED.
 * @history 14-03-2013 Matiur Rahman Added variable RECORD_FROZEN.
 * @history 14-03-2013 Matiur Rahman Added method mrdUploadQuery.
 * @history 14-03-2013 Matiur Rahman Added method
 *          checkIfMRDIsFrozenByAccountIDAndBillRoundQuery.
 * @history 14-03-2013 Matiur Rahman Added method
 *          getConsumerDetailByAccountIDAndBillRoundQuery.
 * @history 14-03-2013 Matiur Rahman Added method queryAverageRead.
 * @history 14-03-2013 Matiur Rahman Added method queryNoBillingRead.
 * @history 14-03-2013 Matiur Rahman Added method queryRegularRead.
 * @history 14-03-2013 Matiur Rahman Added method queryProvisionalRead.
 * @history 18-03-2013 Matiur Rahman Added method updateMeterReadDetailsQuery.
 * @history 19-03-2013 Matiur Rahman Added MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED.
 * @history 19-03-2013 Matiur Rahman Added method
 *          resetMeterReplacementDetailQuery.
 * @history 19-03-2013 Matiur Rahman Added method getDJBParamValueQuery.
 * @history 19-03-2013 Matiur Rahman Added method flagConsumerStatusQuery.
 * @history 21-03-2013 Matiur Rahman Added method getZoneListMapQuery.
 * @history 21-03-2013 Matiur Rahman Added method getAreaListMapQuery.
 * @history 21-03-2013 Matiur Rahman Added method getMRNoListMapQuery.
 * @history 21-03-2013 Matiur Rahman modified query in method
 *          insertMeterReplacementDetailQuery for populating Operation Area Code
 * @history 21-03-2013 Matiur Rahman modified query in method
 *          updateMeterReplacementDetailQuery for populating Operation Area Code
 * @history 29-03-2013 Matiur Rahman modified query in method
 *          mrdDataEntrySearchQuery to Exclude records CONS_PRE_BILL_STAT_ID
 * @history 29-03-2013 Matiur Rahman added variable CONS_PRE_BILL_STAT_ID_NOT_IN
 * @history 01-04-2013 Matiur Rahman Modified method
 *          getPrevMeterReadDetailsForMRQuery.
 * @history 01-04-2013 Matiur Rahman Modified method
 *          getPrevActualMeterReadDetailsForMRQuery.
 * @history 18-04-2013 Matiur Rahman Bug Resolved as reported in JTrac# DJB-1413
 *          adding remarks to updateMeterReadDetailsQuery.
 * @history 06-05-2013 Matiur Rahman added
 *          getTotalCountOfMeterReplacementDetailRecordsQuery.
 * @history 06-05-2013 Matiur Rahman added getMeterReplacementDetailListQuery.
 * @history 06-05-2013 Matiur Rahman added getMeterReplacementDetailMainQuery.
 * @history 08-05-2013 Matiur Rahman Added method getMRKEYListMapQuery.
 * @history 08-05-2013 Matiur Rahman Added method getZoneMRAreaByMRKEYQuery.
 * @history 16-05-2013 Reshma Added Renumber Route Sequence Screen Queries.
 * @history 21-05-2013 Matiur Rahman Added method getMRDTypeMapQuery.
 * @history 21-05-2013 Matiur Rahman Added method
 *          getSortedKNOListForDemandTransferQuery.
 * @history 22-05-2013 Matiur Rahman Added method getOpenBillRoundQuery.
 * @history 23-05-2013 Matiur Rahman Added method
 *          updateDemandTransferProcessStatus.
 * @history 24-05-2013 Matiur Rahman changed query for
 *          getSortedKNOListForDemandTransferQuery.
 * @history 24-05-2013 Reshma changed query
 *          getConsumerDetailsListForRenumberRouteSequencesMainQuery for search
 *          details on the basis of mrkey only.
 * @history 24-05-2013 reshma changed query renumberRouteSequencesUpdateQuery to
 *          update service cycle and service route from property file along with
 *          new route sequences.
 * @history 28-05-2013 Matiur Rahman Added getLDAPUserRoleListMapQuery. *
 * @history 04-06-2013 Matiur Rahman Commeneted line for considering active SA
 *          i.e. C.SA_STATUS_FLG = 20 in Demand Transfer Queries.
 * @history 07-06-2013 Matiur Rahman Added method getAllZROQuery.
 * @history 11-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequencesMainQuery
 *          ,getTotalCountOfConsumerDetailsListForRenumberRouteSequencesQuery
 *          and getConsumerDetailsListForRenumberRouteSequencesQuery ,adding
 *          range limit on route sequences if provided.
 * @history 11-06-2013 Matiur Rahman Commeneted line for considering all
 *          consumer i.e. removing clause AND TRIM(C.SA_TYPE_CD) IN ('SAWATR',
 *          'SAWATSEW', 'UNMTRD') in Demand Transfer Queries.
 * @history 11-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequencesMainQuery
 *          ,getTotalCountOfConsumerDetailsListForRenumberRouteSequencesQuery
 *          and getConsumerDetailsListForRenumberRouteSequencesQuery ,adding
 *          range limit on route sequences if provided.
 * @history 12-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequencesMainQuery removed
 *          DISTINCT keyword from the query.
 * @history 21-06-2013 Reshma Edited
 *          getConsumerDetailsListForRenumberRouteSequencesMainQuery
 *          ,getTotalCountOfConsumerDetailsListForRenumberRouteSequencesQuery
 *          and getConsumerDetailsListForRenumberRouteSequencesQuery commented
 *          range limit functionality.
 * @history 21-06-2013 Matiur Rahman added method
 *          {@link #getOpenBillRoundForAnMRKeyQuery}.
 * @history 09-07-2013 Mrityunjay added method
 *          {@link #queryToGetConsumerDetlBasedonKNO()},
 *          {@link #queryToGetConsumerDetlBasedonKNOOther()},
 *          {@link #getBillGenerationDetailsQuery()},
 *          {@link #getRecordStatusAndErrorDescQuery()},
 *          {@link #updateMeterReadDetailsQueryBasedOnKNO()} .
 * @history 09-07-2013 Matiur Rahman sorted members after additon by Mrityunjay.
 * @history 23-07-2013 Matiur Rahman added new parameter hhdId to the method
 *          {@link #getHHDMappingDetailsListQuery},
 *          {@link #getTotalCountOfHHDMappingRecordsQuery} and
 *          {@link #getHHDMappingDetailsListMainQuery}.
 * @history 26-07-2013 Matiur Rahman added new validation to
 *          {@link #getOperationMRKeyReviewDetailsListQuery},
 *          {@link #getTotalCountOfOperationMRKeyReviewRecordsQuery} and
 *          {@link #getNoOfAccountsToBeUpdatedQuery}.
 * @history 08-08-2013 Reshma Added methods having queries required for HHD
 *          Master,HHD User Master & HHD Version Master Screens,JTrac HHD-24.
 * @history 18-09-2013 Matiur Rahman modified Query for
 *          {@link #flagConsumerStatusQuery},
 *          {@link #updateMeterReadDetailsQuery},
 *          {@link #updateMeterReadDetailsQueryBasedOnKNO},
 *          {@link #getConsumerDetailsForMRByKNOQuery} and
 *          {@link #mrdUploadQuery} as per DJB-1871.
 * @history 23-09-2013 Matiur Rahman Added method
 *          {@link #getMeterReaderDesignationListMapQuery},
 *          {@link #getMeterReaderDetailsListQuery} as per HHD-48.
 * @history 23-09-2013 Matiur Rahman Added method
 *          {@link #getAllCurrentTaggingListQuery},
 *          {@link #getAllTaggingListHistoryQuery},
 *          {@link #getMeterReaderDetailsHistoryListQuery},
 *          {@link #getAllCurrentlyTaggedMRKeyListQuery} as per HHD-48.
 * @history 30-09-2013 Matiur Rahman Modified method
 *          {@link #getOperationMRKeyReviewDetailsListQuery},
 *          {@link #getTotalCountOfOperationMRKeyReviewRecordsQuery},
 *          {@link #updateMRKeyStatusCodeQueryand} added method
 *          {@link #getOperationMRKeyReviewDetailsListMainQuery} as per
 *          DJB-1970.
 * @history 06-12-2013 Karthick K Added new methods
 *          {@link #deleteHHDCredentailsDetailsQuery},
 *          {@link #getHHDCredentialsDetailsListQuery},
 *          {@link #getHHDCredentialsDetailsListMainQuery},
 *          {@link #getTotalCountOfHHDCredentialsRecordsQuery} added method
 *          {@link #insertHHDCredentialsDetailsQuery}
 *          {@link #updateHHDCredentailsDetailsQuery} as per DJB-1758.
 * 
 * @history 17-12-2013 Rajib Hazarika Added new methods
 *          {@link #getListOfMrkeyQuery} as per DJB-2024.
 * @history 17-12-2013 Rajib Hazarika Modified method
 *          {@link #getMeterReplacementDetailMainQuery} as per DJB-2024.
 * @history 10-03-2014 Matiur Rahman Modified method {@link #mrdUploadQuery},
 *          {@link #updateMeterReadDetailsQuery},
 *          {@link #updateMeterReadDetailsQueryBasedOnKNO} to put restriction
 *          that it cannot be updated after it is parked for processing or
 *          billing as a part of changes as perJTrac ID# DJB-2024.
 * @history Reshma on 26-06-2014 edited the query of method
 *          {@link #getSortedKNOListForDemandTransferQuery} to take out the
 *          distinct kno in the list, JTrac DJB-3195.
 * 
 * @history Aniket on 23-09-2014 added the query of method
 *          {@link #getConsNameNRwhStatusQuery , #checkAccessQuery
 *          ,#getPremiseIdQuery , #getCustomerClassQuery ,
 *          #insertPremCharDetailsQuery , #updatePremCharDetailsQuery} as per
 *          JTrac DJB-4037.
 * 
 * @history 26-09-2015 Rajib Hazarika Added new method
 *          {@link #getActiveHhdListMapQuery()},
 *          {@link #getSupervisorEmpIdListMapQuery()} to get all active HHDs and
 *          to get Supervisor Emp Id list as per JTrac DJB-3871
 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
 *          Modified Query in {@link #getAllCurrentTaggingListQuery},
 *          {@link #getAllTaggingListHistoryQuery},
 *          {@link #getMeterReaderListQuery} as per JTRac DJB-3871
 * 
 * @history 14-OCT-2015 Rajib Added parameter for HHD ID on
 *          {@link #getMeterReaderDetailsListQuery} as per Jtrac DJB-4199
 * @history 14-DEC-2015 Aniket Added new method {@link #chkUserExistenceQuery()}
 *          to get count of meter reader named 'DJB' which is actively present
 *          for respective zones as per Jtrac DJB-4185 & DJB-4280.
 * @history 14-DEC-2015 Aniket modified method
 *          {@link #initiateTransferDemandQuery} to fix the issue while
 *          transferring demand .It was modified becuase it was facing issue
 *          while transferring demand as it was fetching the maximum effective
 *          date of all the characteristics present. It was also facing issue
 *          when some KNO has more than one SA in active state.So query has been
 *          changed to fix this issue as per Jtrac DJB-4185 & DJB-4280.
 * @history 11-JAN-2015 Atanu Added new method
 *          {@link #getTotalCountOfAuditDetailsListQuery()} to get count of
 *          image audit records for particuler search criteria as per Jtrac
 *          ANDROID-293.
 * @history 11-JAN-2015 Atanu Added new method
 *          {@link #getRecordDetailsListForImgAuditMainQuery()} to get details
 *          of image audit records for particuler search criteria as per Jtrac
 *          ANDROID-293.
 * @history 11-JAN-2015 Atanu Added new method
 *          {@link #getRecordDetailsListForImgAudit()} to get details of image
 *          audit records in pagination style as per Jtrac ANDROID-293.
 * @history 11-JAN-2015 Atanu Added new method {@link #getUpdateRecordQuery()}
 *          to update status of image audit records as per Jtrac ANDROID-293.
 * @history 11-JAN-2015 Atanu Added new method
 *          {@link #getPreviousBillRoundQuery()} to get the currently closed
 *          normal bill round. {@link #insertSearchHistoryRecords()} to insert
 *          search history {@link #getUpdateRecordQuery()},to get update query
 *          {@link #getTotalCountOfSearchHistoryRecords()},to get total count of
 *          Search History Records
 *          {@link #getTotalCountOfConsumptionVariationAuditDetailsListQuery()}
 *          ,to get Total Count Of Consumption Variation Audit records
 *          {@link #getSearchedResultListQuery()},to get Searched Result Query
 *          {@link #getPreviousBillRoundsQuery()},to get Previous Bill Rounds
 *          Query {@link #getKnoAuditDetailListForConsumptionAudit()},to get Kno
 *          Audit Detail List For Consumption Audit
 *          {@link #getConsumptionVariationRecordDetailsListMainQuery()} as per
 *          Jtrac ANDROID-293.
 * 
 * @history 14-JAN-2015 rajib Hazarika added method
 *          {@link #getDocAttchListMapQuery}, {@link #getFileCountQuery},
 *          {@link #saveNewConnFileDetailsQuery},
 *          {@link #getDeZroAccessForUserIdQuery},
 *          {@link #getFileLocationZroCdQuery},{@link #getAreaDetailsQuery} for
 *          New Connection File Entry Screen as per JTRAC DJB-4313
 * 
 * @history 27-Jan-2016 Arnab Added new method {@link #getZROQuery()},
 *          {@link #getFileListForSearchMainQuery()},
 *          {@link #getFileSearchStatus()},
 *          {@link #updateActionStatusforFiles()},
 *          {@link #getFileNumberSearchDetailListQuery()},
 *          {@link #getTotalCountOfFileDetailsListForSearchQueryQC()},
 *          {@link #checkZRO()}, {@link #getLotNo()},
 *          {@link #getStatusListMapQuery()}, {@link #updateLotNumberforFiles()}
 *          , {@link #getSaveStatusListMapQuery()}, {@link #getRoleList()},
 *          {@link #getZROList()}, {@link #getUserListMapQuery()},
 *          {@link #getExistingLotCountQuery()},
 *          {@link #getExistingLotCountQuery()}, {@link #setFileStatusTracker()}
 *          for File Number Search Screen as per Jtrac DJB-4326.
 * @history 11-Feb-2016 Arnab added Order By in search criteria in
 *          {@link #getFileListForSearchMainQuery()} for File Number Search
 *          Screen as per Jtrac DJB-4313.
 * @history 12-Feb-2016 Arnab edited in {@link #updateActionStatusforFiles()},
 *          {@link #setFileStatusTracker())} and added
 *          {@link #checkActionStatusEligibility()} for File Number Search
 *          Screen as per Jtrac DJB-4359 and DJB-4365.
 * @history 25-FEB-2016 Rajib Hazarika Added new method
 *          {@link #getMrkeyCountQuery(),}
 *          {@link QueryContants#updateGeneratedKnoQuery()} ,
 *          {@link # updateGeneratedKnoQuery} , {@link #getArnDetailsQuery} as
 *          per Jtrac DJB-4313
 * @history 09-03-2016 Atanu Added new method as per jTrac ANDROID-293
 *          {@link #getRecordDetailsListForAuditAction(),}
 *          {@link #getRecordDetailsListForAuditActionMainQuery()} ,
 *          {@link #getSaveAuditActionQuery()} ,
 *          {@link #getTotalCountOfAuditActionRecordsQuery()},
 *          {@link #getUpdateAuditFindingsQuery()},
 *          {@link #getConsumerMobileNumberQuery()}
 * @history Reshma on 11-03-2016 edited the method
 *          {@link #getPreDefChrValListMapQuery()} in query to sort the value in
 *          ascending order from db, JTrac DJB-4405.
 *   @history Sanjay on 17-03-2016 edited the method as per jTrac DJB-4418
 *          {@link #getArnDetailsQuery to add a new field PLOT_AREA
 *@history: On 31-MAR-2016 Rajib Hazarika (682667) Modified  {@link #updateGeneratedKnoQuery} to update purposeOfAppl as per JTrac DJB-4429
 *@history: On 02-JUNE-2016 Madhuri Singh (735689) Added {@link #setFileDetails()},{@link #getTaggedMrkeysEmp()},{@link #getMrdCode()} as per jtrac DJB-4464 & open project Ids- 1203 & 1208
 *@history Arnab Nandy (1227971) on 01-06-2016 added new method {@link #getFilesQuery} to fetch file list, as per  
 *           JTrac DJB-4465 and OpenProject-918.
 *           
 * @history 20-06-2016 Atanu Mandal Added {@link #getAMRRecordsOfSpecificHeaderIdQuery}, 
 * 			{@link #getCurrentVersionQuery}, {@link #getHHDIdOfUserQuery}, 
 * 			{@link #getNextHeaderIdQuery}, {@link #updateConsPreBillProcQuery},
 * 			{@link #getOpenBillRoundOfMRDQuery}, {@link #getUserDetailsQuery},
 * 			{@link #getZROCodeQuery}, {@link #insertHeaderDataQuery},
 * 			{@link #saveAMRExcelDataQuery}, as per JTRac DJB-4464 and OpenProject#1202. 
 * @history Atanu added the distinct keyword in {@link #mrdScheduleDownloadQuery} on 06-07-2016 as per jtrac- DJB-4464, OpenProject#1202.          
 * @history Lovely on 20-05-2016 added new method 
 *          {@link #insertDataUploadQuery()} ,  {@link #validateFileQuery()},
 *          {@link #validateFileHeaderQuery()} and   {@link #PROCQUERY}
 *           per JTrac DJB-4465 and OpenProject-918.       
 *@history Lovely on 12-04-2016 added new  methods {@link #checkFileNbrQuery} and{@link #setNewBookletQuery} as per jTrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867.
 *@history Lovely on 12-04-2016 added new  methods {@link #updateConsPreBillProcQuery}  Open project 1202,jTrac : DJB-4531.
 *@history Madhuri (735689) on 28-Aug-2016 added new  methods {@link #tagFileZroLocation} and {@link #tagfileInsertDetails}  as per JTrac DJB-4541 ,Open project-1443.
 *@history Rajib Hazarika (682667) on 29-AUG-2016 added new methods {@link #getConsNameNRaybStatusQuery()},{@link #insertRaybCharDetailsQuery()} as per Jtrac DJB-4537, Open project Id #1442
 *@history: Sanjay Kumar Das on 30-08-2016 modified {@link #getAMRRecordsOfSpecificHeaderIdQuery} as per JTrac DJB-4553, Open project Id #1459
 *@history: On 16-SEP-2016 Rajib Hazarika (682667, Rajib hazarika) added new methods {@link #isFileTaggedToEmpQuery(String)} to check whether a file is assigned to a sepcific EmployeeId or not as per JTrac ANDROMR-7
 *@history Lovely on 19-09-2016 added new methods {@link #checkZROLocationAcessQuery},{@link #insertAllocatedFileToUserQuery},{@link #getUserFileNbrAllocationQuery},{@link #updateAllocatedFileToUserQuery},{@link #updateAllocatedFileToUserQuery},
 *          {@link #isvalideAllocatedFileToUserQuery},{@link #isUsedFileNbrQuery} and {@link #getAssignedZROListQuery}as per jTrac DJB-4571,Openproject- #1492 
 *@history Madhuri Singh (735689) on 30-09-2016 added new methods 
 *			{@link #updateMrdTransferProcessStatusQuery()},
 *			{@link#initiateMrdTransferQuery()},
 *			{@link#getCntOfInProgressForMrdTrnsfr()},,
 *			{@link#getZrocdByZoneMrArea()}
 * 			as per Jtrac DJB-2200 and open project id-1540.
 * @history Madhuri on 22-Nov-2016 Modified methods
 *          {@link #queryToGetConsumerDetlBasedonKNO()},
 *          {@link #queryToGetConsumerDetlBasedonKNOOther()} and Added
 *          {@link #checkSelfBillingStatusQuery())
 *          as per DJB-4604.
 *@history Sanjay  on 17-11-2016 modified the method  
 *			{@link #mrdDataEntrySearchQuery}
 *			as per Jtrac DJB-4583 and open project id-1595 appending in the query HILWFLG and method CM_IS_HIGH_LOW().   
 *
 *@history Madhuri on 17-11-2016 added new method  
 *			{@link #getBillRoundSQuery}
 *			as per Jtrac:- ANDROID-388.
 *@history Madhuri on 01-09-2017 added new method  
 *			{@link #getMeterReaderNameForAuditQuery
 *			{@link #getBillRoundStartAndEndDateQuery}
 *			as per Jtrac:- ANDROID-388.               
 *@history Suraj Tiwari on 12-09-2017 added new method  
 *			{@link #checkDbSessionCountQuery}
 *			as per Jtrac:- DJB-4735.
 *@history Suraj Tiwari on 20-09-2017 modified method  
 *			{@link #checkDbSessionCountQuery}
 *			as per Jtrac:- DJB-4735.
 *             
 */
public class QueryContants {
	/**
	 * <p>
	 * Proc to activate a colony for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 */
	public static final String ACTIVATE_COLONY_PROC = "{call  CM_ACTIVATE_COLONY(?,?,?)}";

	/**
	 * <p>
	 * Bill Generated status for Regular Consumer. When Bill Generated for a
	 * regular consumer for an MRD <code>CONS_PRE_BILL_STAT_ID</code> set to 130
	 * in the table <code>CM_CONS_PRE_BILL_PROC</code> by Billing Batch.
	 * </p>
	 */
	public static final int BILL_GENERATED = 130;

	/**
	 * <p>
	 * The status id for which records not be update in CM_CONS_PRE_BILL_PROC.
	 * </p>
	 */
	public static final String CONS_PRE_BILL_STAT_ID_NOT_IN = "65,70,130";

	/**
	 * <p>
	 * Current Data Entry Application version by which Meter Replacement Details
	 * entered/Updated.
	 * </p>
	 */
	public static final String DE_CODE_VERSION = "2";
	/**
	 * <p>
	 * Proc to save new interest detail row for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 */
	public static final String INTEREST_ROW_SAVE_PROC = "{call  CM_CREATE_INTEREST(?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,SYSDATE,?,SYSDATE,?,?,?)}";

	/**
	 * <p>
	 * The status id for which records not be update in cm_mtr_rplc_stage.
	 * </p>
	 */
	public static final String MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED = "600,610";
	/**
	 * <p>
	 * Proc to save new colony detail for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 */

	public static final String NEW_COLONY_DETAILS_SAVE_PROC = "{call  CM_CREATE_MASTER_DATA(?,?,?,TO_DATE(?,'dd-mm-yyyy'),?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,?,?,SYSDATE,?,?,?,?)}";
	/**
	 * <p>
	 * Status code of a open bill round Id.
	 * </p>
	 */
	public static final int OPEN_BILL_ROUND_STATUS_CODE = 10;
	/**
	 * <p>
	 * Query to call procedure CM_DWARKA_DATA_MIGRATION as per JTrac DJB-4465
	 * and OpenProject-918.
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 20-05-2016
	 */
	public static final String PROCQUERY = "{CALL CM_DWARKA_DATA_MIGRATION(?,?,?,?)}";

	/**
	 * <p>
	 * Query to populate Average Read read type code from the Database.
	 * </p>
	 */
	public static final String queryAverageRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Average Read' order by READER_REM_CD asc";
	/**
	 * <p>
	 * Query to populate No Billing Read read type code from the Database.
	 * </p>
	 */
	public static final String queryNoBillingRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='No Billing Read' order by READER_REM_CD asc";
	/**
	 * <p>
	 * Query to populate Provisional Read read type code from the Database.
	 * </p>
	 */
	public static final String queryProvisionalRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Provisional Read' order by READER_REM_CD asc";

	/**
	 * <p>
	 * Query to populate Regular Read read type code from the Database.
	 * </p>
	 */
	public static final String queryRegularRead = "SELECT READ_TYPE,READER_REM_CD FROM CM_CONS_MRD_READ_TYPE_MAP where READ_TYPE='Regular Read' order by READER_REM_CD asc";

	/**
	 * <p>
	 * Proc to save new rate detail row for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 */
	public static final String RATE_ROW_SAVE_PROC = "{call  CM_CREATE_RATE(?,?,?,?,?,?,?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,SYSDATE,?,SYSDATE,?,?,?)}";
	/**
	 * <p>
	 * Proc to save new rebate detail row for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 */
	public static final String REBATE_ROW_SAVE_PROC = "{call  CM_CREATE_REBATE(?,?,?,?,TO_DATE(?,'dd-mm-yyyy'),TO_DATE(?,'dd-mm-yyyy'),?,?,SYSDATE,?,SYSDATE,?,?,?,?,?)}";

	/**
	 * <p>
	 * Record Entered status for Regular Consumer. When Data is entered for a
	 * regular consumer for an MRD <code>CONS_PRE_BILL_STAT_ID</code> set to 60
	 * in the table <code>CM_CONS_PRE_BILL_PROC</code>. Then its ready to be
	 * frozen for bill generation by Billing Batch.
	 * </p>
	 */
	public static final int RECORD_ENTERED = 60;
	/**
	 * <p>
	 * Record Frozen status for billing. When MRD is frozen
	 * <code>CONS_PRE_BILL_STAT_ID</code> set to 70 in the table
	 * <code>CM_CONS_PRE_BILL_PROC</code>. Then its ready for bill generation by
	 * Billing Batch.
	 * </p>
	 */
	public static final int RECORD_FROZEN = 70;
	/**
	 * <p>
	 * When Records are frozen from FreEze MDR screen CONS_PRE_BILL_STAT_ID of
	 * CM_CONS_PRE_BILL_PROC updated with 70.
	 * </p>
	 * 
	 */
	public static final int RECORD_FROZEN_STATUS = 70;
	/**
	 * <p>
	 * Record Entered status for Regular Consumer. When Data is entered for a
	 * regular consumer for an MRD <code>CONS_PRE_BILL_STAT_ID</code> set to 60
	 * in the table <code>CM_CONS_PRE_BILL_PROC</code>. Then its ready to be
	 * frozen for bill generation by Billing Batch.
	 * </p>
	 */
	public static final int REGULAR_READ_TYPE_CODE = 60;

	/**
	 * <p>
	 * Query to check access of the user to the respective KNO.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */
	public static String checkAccessQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select count(u.dar_cd) as ACCESS_CNT from ci_dar_usr u, ci_acct t where trim(u.dar_cd) = trim(t.access_grp_cd) and t.acct_id=? and trim(u.user_id)=? and u.expire_dt >= sysdate ");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Checks action status Of File for target action status retrieval.
	 * </p>
	 * 
	 * @author Arnab Nandy (1227971)
	 * @since 17-02-2016
	 * @param selectedFileNo
	 * @param actionStatus
	 * @return
	 * @as per Jtrac DJB-4359 and and DJB-4365.
	 */

	public static String checkActionStatusEligibility(String selectedFileNo,
			String actionStatus) {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append("select INSTR(FILE_TARGET_STATUS_CD, '" + actionStatus
				+ "') FOUND");
		querySB
				.append(" from CM_STATUS_SWCH_CONTROL_DATA where FILE_SOURCE_STATUS_CD = ");
		querySB
				.append("(select status_cd from CM_NEW_CONN_FILE_ENTRY_STG where file_no='"
						+ selectedFileNo + "' AND LOT_NO is not null )");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to check DB Session Count.
	 * </p>
	 * 
	 * @author Suraj Tiwari(Tata Consultancy Services)
	 * @since 12-09-2017
	 * @see checkDbSessionCount
	 * @return query String
	 */
	public static String checkDbSessionCountQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		// Start : Modified by Suraj Tiwari 22-09-2017 as per Jtrac DJB-4735.
		// querySB.append("SELECT CM_GET_DB_SESSION_CNT(' "
		// + DJBConstants.DB_SESSION_QUERY_TO_CHECK
		// + " ') AS SESSION_COUNT FROM DUAL");
		querySB.append("SELECT CM_GET_DB_SESSION_CNT('"
				+ DJBConstants.DB_SESSION_QUERY_TO_CHECK
				+ "') AS SESSION_COUNT FROM DUAL");
		AppLog.info("QueryToCheckDBSessionCount " + querySB);
		// End : Modified by Suraj Tiwari 22-09-2017 as per Jtrac DJB-4735.
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to check valid file number to be allocated as
	 * per jTrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 27-05-2016
	 */

	public static String checkFileNbrQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COUNT(1) CNT FROM CM_NEW_CONN_FILE_MASTER WHERE ");
		querySB.append(" 1=1 ");
		querySB.append("AND ? >= MIN_VAL ");
		querySB.append("AND ? <= MAX_VAL ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is the SQL query to retrieve count of Frozen Status for the Account
	 * ID and Bill Round Id. SQL query takes Account ID <code>ACCT_ID</code> and
	 * Bill Round Id <code>BILL_ROUND_ID</code> as parameter to retrieve records
	 * from the database.
	 * </p>
	 * 
	 * @see MeterReadDAO#getConsumerDetailByAccountIDAndBillRoundQuery()
	 * @param billRoundId
	 * @param accountIds
	 * @return Query String
	 */
	public static String checkIfMRDIsFrozenByAccountIDAndBillRoundQuery(
			String billRoundId, String accountIds) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT count(CM_CONS_PRE_BILL_PROC.ACCT_ID)NO_OF_FROZEN_RECORDS ");
		querySB.append(" FROM CM_CONS_PRE_BILL_PROC ");
		querySB.append(" WHERE (CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID = "
				+ RECORD_FROZEN
				+ " OR CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID ="
				+ BILL_GENERATED + ") ");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = '"
				+ billRoundId + "'");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID in (" + accountIds
				+ ")");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check self billing is enabled or disabledd.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 23-11-2016
	 * @see checkSelfBilling
	 * @return query String
	 */
	public static String checkSelfBillingStatusQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select max(r.effdt) as EFFECTIVE_DATE, r.char_val as STATUS ");
		querySB.append("  from ci_acct_char r ");
		querySB.append(" where 1=1 ");
		querySB.append("   and r.char_type_cd = ? ");
		querySB.append("   and r.acct_id = ? ");
		querySB.append("   and r.effdt = (select max(e.effdt) ");
		querySB.append("                    from ci_acct_char e ");
		querySB.append("                   where e.acct_id = r.acct_id ");
		querySB.append("                     and e.char_type_cd = ?) ");
		querySB.append(" group by r.char_val");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to update Record Details For File Search
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 27-01-2016
	 * @param selectedZROCode
	 * @return
	 */
	public static String checkZRO(String selectedZROCode) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT USER_ID FROM DJB_DATA_ENTRY_USERS U WHERE U.USER_ZRO_CD like '%");
		if (null != selectedZROCode && !"".equalsIgnoreCase(selectedZROCode)) {
			querySB.append(selectedZROCode);
		}
		querySB.append("%' and USER_ACTIVE='" + DJBConstants.FLAG_Y + "'");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to fetch Zro location access for given user id as per jTrac
	 * DJB-4571,Openproject- #1492
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String checkZROLocationAcessQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT USER_ZRO_CD FROM DJB_DATA_ENTRY_USERS WHERE USER_ID = ?");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get count of meter reader named 'DJB' which is actively present
	 * for respective zones.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 14-12-2015
	 * @return query String
	 */

	public static String chkUserExistenceQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) AS USER_COUNT FROM DJB_MTR_RDR W ");
		querySB
				.append(" WHERE W.IS_ACTIVE = 'Y' AND W.MTR_RDR_NAME = 'DJB' AND W.ZRO_CD =? ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to delete allocated file number to users as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String deleteAllocatedFileToUserQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();

		querySB
				.append("DELETE FROM  CM_FILE_RANGE_MAP_EMP WHERE USER_ID=? AND ZRO_CD=? AND MIN_VAL=? AND MAX_VAL=?");

		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * To delete HHD Credentials <code>DJB_WEB_SERVICE_USERS</code> table of
	 * database.
	 * </p>
	 * 
	 * @author Karthick K (Tata Consultancy Services)
	 * @param hhdId
	 */
	public static String deleteHHDCredentailsDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" DELETE FROM DJB_WEB_SERVICE_USERS ");
		querySB.append(" WHERE USER_ID=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To delete HHD Master Details from <code>HHD_MASTER</code> table of
	 * database.
	 * </p>
	 * 
	 * @param hhdId
	 */
	public static String deleteHHDMasterDetailsQuery(String hhdId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" DELETE FROM HHD_MASTER ");
		querySB.append(" WHERE HHD_ID='" + hhdId + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To delete HHD User Master Details from <code>HHD_USER_MASTER</code> table
	 * of database.
	 * </p>
	 * 
	 * @param userMasterId
	 */
	public static String deleteHHDUserMasterDetailsQuery(String userMasterId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" DELETE FROM HHD_USER_MASTER ");
		querySB.append(" WHERE USER_ID='" + userMasterId + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To delete HHD Version Master Details from <code>HHD_VERSION_MASTER</code>
	 * table of database.
	 * </p>
	 * 
	 * @param versionNo
	 */
	public static String deleteHHDVersionMasterDetailsQuery(String versionNo) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" DELETE FROM HHD_VERSION_MASTER ");
		querySB.append(" WHERE VERSION_NO='" + versionNo + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to fetch ZRO from database
	 * </p>
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 12-03-2016
	 * @param
	 * @return
	 */
	public static String fetchZRO(String selectedZROCode) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT ZRO_DESC FROM DJB_ZRO");
		if (null != selectedZROCode && !"".equalsIgnoreCase(selectedZROCode)) {
			querySB.append(selectedZROCode);
		}
		AppLog.info(querySB.toString());
		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to flag Consumer Status to cm_cons_pre_bill_proc table for
	 * processing by billing batch.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-03-2013
	 * @see SetterDAO#saveMeterRead
	 * @see SetterDAO#flagConsumerStatus(java.util.Map)
	 * @return query String
	 */
	public static String flagConsumerStatusQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update cm_cons_pre_bill_proc t set ");
		querySB.append(" t.cons_pre_bill_stat_id=?, ");
		querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
		querySB.append(" t.reg_reading=?,");
		// querySB
		// .append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
		querySB.append(" t.reader_rem_cd=?,");
		querySB.append(" t.new_avg_read=?,");
		querySB.append(" t.new_no_of_floors=?,");
		querySB.append(" t.remarks=?, ");
		/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		// querySB.append(" t.last_updt_dttm=sysdate,");
		// querySB.append(" t.last_updt_by=?");
		/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.last_updt_dttm=sysdate,");
		querySB.append(" t.last_updt_by=?, ");
		/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.data_entry_dttm=systimestamp,");
		querySB.append(" t.data_entry_by=?");
		/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		querySB.append(" where 1=1 AND t.cons_pre_bill_stat_id <> "
				+ BILL_GENERATED);
		querySB.append(" AND t.cons_pre_bill_stat_id <> " + RECORD_FROZEN);
		querySB.append(" AND  ACCT_ID=?");
		querySB.append(" AND  BILL_ROUND_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check if the colony has been activated or not for dev charge,
	 * JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getActivatedColonyRowCountQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) AS COUNT FROM DJB_DEV_CHG_COLONY A ");
		querySB.append(" WHERE A.COLONY_ID = ? AND A.DATA_ACTIVATED_BY = ? ");
		querySB.append(" AND A.DATA_ACTIVATED_DTTM >= SYSDATE-"
				+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method will return all active HHDs which are available for tagging
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @return
	 */
	public static String getActiveHhdListMapQuery(String currentHhdId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT A.ASSET_ID  FROM WAM_SA_ASSET A ");
		querySB
				.append("  WHERE A.ASSET_ID is not null and A.ASSET_RECORD_TYPE = '"
						+ DJBConstants.ASSET_RECORD_TYPE_HANDHELD + "' ");
		querySB.append("   AND A.ASSET_STATUS = '"
				+ DJBConstants.ASSET_ACTIVE_STATUS + "' ");
		querySB.append("   AND NOT EXISTS ( ");
		querySB
				.append(" SELECT 1 FROM DJB_MTR_RDR  WHERE DJB_MTR_RDR.HHD_ID = A.ASSET_ID ");
		querySB.append(" AND DJB_MTR_RDR.EFF_FROM_DATE <= SYSDATE ");
		querySB.append("  AND (DJB_MTR_RDR.EFF_TO_DATE >= SYSDATE OR ");
		querySB.append("  DJB_MTR_RDR.EFF_TO_DATE IS NULL) ");
		querySB.append("  AND DJB_MTR_RDR.IS_ACTIVE = '" + DJBConstants.FLAG_Y
				+ "' ");
		if (null != currentHhdId && !"".equalsIgnoreCase(currentHhdId)) {
			querySB.append("  AND DJB_MTR_RDR.HHD_ID <> '" + currentHhdId
					+ "' ");
		}
		querySB.append(" ) ");
		querySB.append(" order by  A.ASSET_ID asc");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get All Currently Tagged MRKey List.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-09-2013
	 * @param meterReaderDetails
	 * @return Query String
	 */
	public static String getAllCurrentlyTaggedMRKeyListQuery(
			String meterReaderCode) {
		StringBuffer querySB = new StringBuffer();

		querySB.append(" select m.mrd_cd ");
		querySB.append(" from DJB_MTR_RDR_MRD m ");
		querySB.append(" where 1=1 ");
		if (null != meterReaderCode && !"".equals(meterReaderCode.trim())) {
			querySB.append(" and m.mtr_rdr_cd=? ");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Meter Reader and tagging Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-09-2013
	 * @param zone
	 * @param zroLoc
	 * @param mrKeys
	 * @return Query String
	 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
	 *          Modified Query
	 */
	// public static String getAllCurrentTaggingListQuery(String zone) {
	public static String getAllCurrentTaggingListQuery(String zone,
			String zroLoc, String mrKeys, String mrEmpId, String mrHhdId) {
		StringBuffer querySB = new StringBuffer();

		querySB.append(" select /*+result_cache*/ ");
		querySB.append(" d.mrd_cd, ");
		querySB
				.append(" d.subzone_cd,d.mr_cd,d.area_cd,d.area_desc, d.old_mrd_typ,");
		querySB.append(" r.mtr_rdr_cd, ");
		querySB.append(" r.mtr_rdr_name, ");
		querySB.append(" r.emp_id, ");
		querySB
				.append(" m.created_by, to_char(m.created_date, 'dd/mm/yyyy hh:mi:ss AM') created_date, ");
		querySB
				.append(" to_char(m.eff_from_date, 'dd/mm/yyyy hh:mi:ss AM') eff_from_date, ");
		querySB
				.append(" to_char(m.eff_to_date, 'dd/mm/yyyy hh:mi:ss AM') eff_to_date ");
		querySB
				.append(" from djb_zn_mr_ar_mrd d, DJB_MTR_RDR_MRD m, djb_mtr_rdr r ");
		querySB.append(" where 1=1 ");
		querySB.append(" and m.mtr_rdr_cd = r.mtr_rdr_cd(+) ");
		querySB.append(" and d.mrd_cd=m.mrd_cd(+) ");
		// querySB
		// .append(" and not exists (select 1 from DJB_MTR_RDR_MRD where DJB_MTR_RDR_MRD.MTR_RDR_MRD_ID = m.mtr_rdr_mrd_id and m.eff_to_date < sysdate ) ");
		if ((null != zone && !"".equals(zone.trim()))
				&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
						.equals(mrHhdId.trim())))) {
			querySB.append(" and d.subzone_cd=? ");
		}
		if ((null != zroLoc && !"".equals(zroLoc.trim()))
				&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
						.equals(mrHhdId.trim())))) {
			querySB
					.append(" and exists (select 1 from djb_mrd where djb_mrd.mrd_cd = d.mrd_cd and djb_mrd.zro_cd = ?) ");
		}
		if (null != mrKeys && !"".equals(mrKeys.trim())) {
			querySB.append(" and d.mrd_cd in (" + mrKeys + ") ");
		}
		if (null != mrEmpId && !"".equals(mrEmpId.trim())) {
			querySB.append(" and r.emp_id='" + mrEmpId.trim() + "' ");
		}
		if (null != mrHhdId && !"".equals(mrHhdId.trim())) {
			querySB.append(" and r.hhd_id ='" + mrHhdId.trim() + "' ");
		}
		querySB.append(" order by to_number(d.mrd_cd) asc ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve All Meter Reader List to populate Meter Reader Drop
	 * down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 05-04-2013
	 * @see GetterDAO#getAllMeterReaderListMap
	 * @return query String
	 */
	public static String getAllMeterReaderListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DJB_MTR_RDR.MTR_RDR_CD, DJB_MTR_RDR.MTR_RDR_NAME FROM DJB_MTR_RDR ORDER BY DJB_MTR_RDR.MTR_RDR_NAME ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get All Tagging List History from database.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-09-2013
	 * @param zone
	 * @param zroLoc
	 * @param mrKeys
	 * @return Query String
	 * @history 30-09-2015 Matiur Rahman Added parameter zroLoc and mrKeys and
	 *          Modified Query
	 */
	// public static String getAllTaggingListHistoryQuery(String zone) {
	public static String getAllTaggingListHistoryQuery(String zone,
			String zroLoc, String mrKeys, String mrEmpId, String mrHhdId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" select /*+result_cache*/");
		// querySB.append(" h.mtr_rdr_mrd_id,");
		querySB.append(" mr.mtr_rdr_cd,");
		querySB.append(" mr.mtr_rdr_name,");
		querySB.append(" mr.emp_id,");
		querySB.append(" h.mrd_cd,");
		querySB
				.append(" h.subzone_cd,h.mr_cd,h.area_cd,h.area_desc, h.old_mrd_typ,");
		querySB.append(" h.created_by,");
		querySB
				.append(" to_char(h.created_date, 'dd/mm/yyyy hh:mi:ss AM') created_date,");
		querySB.append(" h.updated_by,");
		querySB
				.append(" to_char(h.updated_date, 'dd/mm/yyyy hh:mi:ss AM') updated_date,");
		querySB
				.append(" to_char(h.eff_from_date, 'dd/mm/yyyy hh:mi:ss AM') eff_from_date,");
		querySB
				.append(" to_char(h.eff_to_date, 'dd/mm/yyyy hh:mi:ss AM') eff_to_date");
		querySB.append(" from (select d.mrd_cd,");
		querySB
				.append(" d.subzone_cd,d.mr_cd,d.area_cd,d.area_desc, d.old_mrd_typ,");
		// querySB.append(" m.mtr_rdr_mrd_id,");
		querySB.append(" m.mtr_rdr_cd,");
		querySB.append(" m.created_by,");
		querySB.append(" m.created_date,");
		querySB.append(" m.updated_by,");
		querySB.append(" m.updated_date,");
		querySB.append(" m.eff_from_date,");
		querySB.append(" m.eff_to_date");
		querySB.append(" from DJB_MTR_RDR_MRD_HIST m, djb_zn_mr_ar_mrd d");
		querySB.append(" where m.mrd_cd = d.mrd_cd");

		if ((null != zone && !"".equals(zone.trim()))
				&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
						.equals(mrHhdId.trim())))) {
			querySB.append(" and d.subzone_cd=? ");
		}
		if ((null != zroLoc && !"".equals(zroLoc.trim()))
				&& !((null != mrEmpId && !"".equals(mrEmpId.trim())) || (null != mrHhdId && !""
						.equals(mrHhdId.trim())))) {
			querySB
					.append(" and exists (select 1 from djb_mrd where djb_mrd.mrd_cd = d.mrd_cd and djb_mrd.zro_cd = ?) ");
		}
		if (null != mrKeys && !"".equals(mrKeys.trim())) {
			querySB.append(" and d.mrd_cd in (" + mrKeys + ") ");
		}
		querySB.append(" ) H,");
		querySB.append(" (select /*+result_cache*/");
		querySB.append(" mtr_rdr_cd, mtr_rdr_name, emp_id,hhd_id ");
		querySB.append(" from djb_mtr_rdr");
		// Start: Added by Rajib as per Jtrac DJB-3871 on 07-OCT-2015
		querySB.append(" where 1=1 ");
		if (null != mrEmpId && !"".equals(mrEmpId.trim())) {
			querySB.append(" and emp_id ='" + mrEmpId.trim() + "' ");
		}
		if (null != mrHhdId && !"".equals(mrHhdId.trim())) {
			querySB.append(" and hhd_id='" + mrHhdId.trim() + "' ");
		}
		// End: Added by Rajib as per Jtrac DJB-3871 on 07-OCT-2015
		querySB.append(" union");
		querySB.append(" select /*+result_cache*/");
		querySB.append(" mtr_rdr_cd, mtr_rdr_name, emp_id,hhd_id ");
		querySB.append(" from djb_mtr_rdr_hist ");
		// Start: Added by Rajib as per Jtrac DJB-3871 on 07-OCT-2015
		querySB.append(" where 1=1 ");
		if (null != mrEmpId && !"".equals(mrEmpId.trim())) {
			querySB.append(" and emp_id ='" + mrEmpId.trim() + "' ");
		}
		if (null != mrHhdId && !"".equals(mrHhdId.trim())) {
			querySB.append(" and hhd_id='" + mrHhdId.trim() + "' ");
		}
		querySB.append(") mr");
		// End: Added by Rajib as per Jtrac DJB-3871 on 07-OCT-2015

		querySB.append(" where mr.mtr_rdr_cd = h.mtr_rdr_cd ");

		querySB.append(" order by to_number(h.mrd_cd),h.updated_date desc ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get All ZRO Query.
	 * </p>
	 * 
	 * @return
	 */
	public static String getAllZROQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT DISTINCT A.ZRO_CD,A.ZRO_DESC FROM DJB_ZRO A ");
		querySB.append(" ORDER BY A.ZRO_DESC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query to get AMR records of specific Header Id 
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getAMRRecordsOfSpecificHeaderIdQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT HEADER_ID, ");
		querySB.append("       SEQ, ");
		querySB.append("       KNO, ");
		querySB.append("       CONSUMER_NAME, ");
		querySB.append("       CONSUMER_ADDRESS, ");
		querySB.append("       CONSUMER_CATEGORY, ");
		querySB.append("       UNOC_DWEL_UNITS, ");
		querySB.append("       OCU_DWEL_UNITS, ");
		querySB.append("       TOTL_DEWL_UNITS, ");
		querySB.append("       NO_OF_FLOOR, ");
		querySB.append("       NO_OF_FUNC_SITES_BEDS_ROOMS, ");
		querySB.append("       MTR_NUMBER, ");
		querySB.append("       MTR_READ_DATE, ");
		querySB.append("       MTR_STATUS_CODE, ");
		querySB.append("       MTR_READ, ");
		querySB.append("       CONS, ");
		querySB.append("       EFFEC_NO_OF_DAYS, ");
		querySB.append("       AVG_CONS, ");
		querySB.append("       CURR_MTR_READ_DATE, ");
		querySB.append("       MTR_READ_TYPE, ");
		querySB.append("       CURRENT_AVG_CONS, ");
		querySB.append("       CURRENT_MTR_READING, ");
		querySB.append("       CURRENT_MTR_STATUS_CODE, ");
		querySB.append("       CURRENT_NO_OF_FLOORS, ");
		querySB.append("       BILL_ROUND_ID, ");
		querySB.append("       MRKEY, ");
		/*
		 * Start: Added by Sanjay on 31-08-16 as JTrac DJB-4553, Open project Id
		 * #1459
		 */
		querySB.append(DJBConstants.CONSUMER_STATUS_HHD);
		querySB.append("        AS CONSUMER_STATUS ");
		/*
		 * End: Added by Sanjay on 31-08-16 as JTrac DJB-4553, Open project Id
		 * #1459
		 */
		querySB.append("  FROM CM_AMR_RECORD_DETAILS D ");
		querySB.append(" WHERE 1 = 1 ");
		querySB.append("   AND D.HEADER_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query to get AREA code
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getAREACodeQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT NVL(M.AREA_CD, '') AREACD FROM DJB_ZN_MR_AR_MRD M WHERE ");
		querySB.append("UPPER(M.AREA_DESC) = UPPER( ? )");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This is the SQL query to get Area and Sub Area Pin code Details from the
	 * data base as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @return String query string
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 20-JAN-2016
	 * 
	 */
	public static String getAreaDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.AREA_DESC, A.AREA_CODE,S.SUB_AREA_CODE, S.SUB_AREA_NAME,A.PIN_CODE FROM AREA A ,SUB_AREA S WHERE ");
		querySB
				.append("A.AREA_CODE= S.AREA_CODE ORDER BY A.PIN_CODE, A.AREA_DESC, S.SUB_AREA_NAME");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Area list to populate Area Drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-03-2013
	 * @see GetterDAO#getAreaListMap
	 * @return query String
	 */
	public static String getAreaListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT AREA_CD, AREA_DESC FROM DJB_ZN_MR_AR_MRD T WHERE T.SUBZONE_CD = ?  AND T.MR_CD = ? ORDER BY TO_NUMBER(AREA_CD) ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get ARN details for KNO and Bill Generation Screen as per Jtrac
	 * DJB-4313
	 * </p>
	 * 
	 * @return String
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 10-FEB-2016
	 * @history Sanjay on 17-03-2016 edited as per jTrac DJB-4418
	 * @history Lovely on 26-08-2016 edited as per jTrac DJB-4541,Open
	 *          project-1443
	 */
	public static String getArnDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT X.ARNDETAILS.ARN AS ARN, ");
		querySB.append(" X.ARNDETAILS.FILE_NO AS FILE_NO, ");
		querySB.append(" X.ARNDETAILS.ACCT_ID  AS ACCT_ID, ");
		querySB.append(" X.ARNDETAILS.ACCT_GEN_BY  AS ACCT_GEN_BY, ");
		querySB.append(" X.ARNDETAILS.BILL_ID  AS BILL_ID, ");
		querySB.append(" X.ARNDETAILS.BILL_GEN_BY  AS BILL_GEN_BY, ");
		querySB.append(" X.ARNDETAILS.DT_OF_APPL AS DT_OF_APPL, ");
		querySB.append(" X.ARNDETAILS.CONSUMER_NAME AS CONSUMER_NAME , ");
		querySB.append(" X.ARNDETAILS.MOB_NO AS MOB_NO, ");
		querySB.append(" X.ARNDETAILS.ADDRESS AS ADDRESS, ");
		querySB.append(" X.ARNDETAILS.APP_PURPOSE AS APP_PURPOSE, ");
		querySB.append(" X.ARNDETAILS.ZRO_CD AS ZRO_CD, ");
		querySB.append(" X.ARNDETAILS.STATUS_CD  AS ARN_STATUS_CD, ");
		querySB
				.append(" X.ARNDETAILS.IS_WAT_FOR_CIVIL_CONST  AS IS_WAT_FOR_CIVIL_CONST, ");
		querySB
				.append(" X.ARNDETAILS.IS_UN_AUTH_USG_IN_PREM  AS IS_UN_AUTH_USG_IN_PREM, ");
		querySB.append(" X.ARNDETAILS.PAYMENT_MODE  AS PAYMENT_MODE, ");
		querySB.append(" X.ARNDETAILS.IS_DJB_EMP  AS IS_DJB_EMP, ");
		querySB.append(" X.ARNDETAILS.DJB_EMP_ID  AS DJB_EMP_ID, ");
		querySB.append(" X.ARNDETAILS.DT_OF_RET  AS DT_OF_RET, ");
		querySB.append(" X.ARNDETAILS.PER_ID  AS PER_ID, ");
		/* Start : Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 */
		querySB.append(" X.ARNDETAILS.PLOT_AREA   AS PLOT_AREA, ");
		/* End : Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 */
		/* Start : Edited by Lovely(986018) as per JTrac DJB-4541 */
		querySB.append(" X.ARNDETAILS.ONLINE_STATUS_CD  AS ONLINE_STATUS_CD ");
		/* End : Edited by Lovely(986018) as per JTrac DJB-4541 */
		querySB
				.append(" FROM (SELECT CM_GET_ARN_DETAILS(?,?) AS ARNDETAILS FROM DUAL) X ");

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get list of ZRO location assigned to logged in users from
	 * function CM_GET_ZRO_LOCATION as per jTrac DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String getAssignedZROListQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT CM_GET_ZRO_LOCATION (?) AS ZRO_CD FROM DUAL");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to get Bill Generation Details.
	 * </p>
	 */

	public static String getBillGenerationDetailsQuery(String billRoundId,
			String accountIds) {
		StringBuffer querySB = new StringBuffer();

		querySB.append("select ACCT_ID,cons_pre_bill_stat_id,BILL_ROUND_ID,");
		querySB
				.append("to_char(bill_gen_date ,'YYYY-MM-DD') bill_gen_dateNew,");
		querySB.append("reg_reading,");
		querySB.append("READ_TYPE_FLG,");
		querySB.append("reader_rem_cd,");
		querySB.append("new_avg_read,");
		querySB.append("new_no_of_floors,");
		querySB.append("remarks, ");
		querySB.append("last_updt_dttm ,");
		querySB.append("last_updt_by from cm_cons_pre_bill_proc ");
		querySB.append("where 1=1 AND cons_pre_bill_stat_id <> "
				+ BILL_GENERATED + " ");
		querySB.append("AND cons_pre_bill_stat_id =  " + RECORD_FROZEN + "  ");
		querySB.append("AND  ACCT_ID = " + accountIds + "  ");
		querySB.append("AND  BILL_ROUND_ID = " + "'" + billRoundId + "'");
		// System.out.println("Final Populate Query for Bill Generation is:"+querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to get Previous Normal Bill Round
	 * </p>
	 * 
	 * @author Madhuri Singh (Tata Consultancy Services)
	 * @since 03-08-2017
	 * @return
	 */
	public static String getBillRoundsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT w.bill_round_id as BILL_ROUNDS ");
		querySB.append("  from cm_bill_window w ");
		querySB.append(" where w.bill_round_id >= '2015-03' ");
		querySB.append("   and w.bill_win_flg = 'N' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get bill round start date and end date for meter reader Img
	 * Audit screen
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 17-08-2017
	 * @see
	 * @return query String
	 */

	public static String getBillRoundStartAndEndDateQuery(String billRound) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT TO_CHAR(W.START_DATE,'YYYY-MM-DD') as START_DATE, TO_CHAR(W.END_DATE,'YYYY-MM-DD') as END_DATE ");
		querySB.append("  FROM CM_BILL_WINDOW W ");
		if (null != billRound && !"".equalsIgnoreCase(billRound)) {
			querySB.append(" WHERE W.BILL_ROUND_ID = '" + billRound + "'");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to fetch count of INPROGRESS status from stg table as per Jtrac
	 * DJB-2200
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 06-09-2016
	 * @return query String
	 */
	public static String getCntOfInProgressForMrdTrnsfrQuery(String mrKey) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("select count(*) as Status ");
		querySB.append("  from cm_mrd_transfr_stg s ");
		querySB.append(" where 1 = 1 ");
		querySB.append("   and s.status in ('IN PROGRESS', 'PENDING') ");
		if (null != mrKey && !"".equalsIgnoreCase(mrKey.trim())) {
			querySB.append("   and s.mrd_cd = ?");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the list of matching colonies for dev charge, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @param colonyName
	 * @param zro
	 * @return
	 */
	public static String getColonyListQuery(String colonyName, String zro) {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.COLONY_ID AS COLONY_ID, A.COLONY_NAME AS COLONY_NAME ");
		querySB.append(" FROM DJB_DEV_CHG_COLONY A ");
		querySB
				.append(" WHERE A.ZRO_CD ='" + zro
						+ "' AND REGEXP_LIKE(A.COLONY_NAME, '" + colonyName
						+ "', 'i')");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get consumer details with RAYB Curent Flag Details.
	 * </p>
	 * 
	 * @author Rajib Hazarika 682667(Tata Consultancy Services)
	 * @since 29-08-2016
	 * @return query String
	 */

	public static String getConsNameNRaybStatusQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select cm_get_name(sysdate,?) as CONS_NAME, CM_GET_RWH_STATUS(?,?,SYSDATE) as RAYB_STATUS from dual");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get consumer details.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */

	public static String getConsNameNRwhStatusQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select cm_get_name(sysdate,?) as CONS_NAME, CM_GET_RWH_STATUS(?,?,SYSDATE) as RWH_STATUS, CM_GET_RWH_STATUS(?,?,SYSDATE) as WWT_STATUS from dual");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Cons PreBill Status Lookup List Query.
	 * </p>
	 * 
	 * @return query String
	 */
	public static String getConsPreBillStatLookupListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.CONS_PRE_BILL_STAT_ID, T.CONS_PRE_BILL_STAT_ID ||'-'||T.DESCR DESCR ");
		querySB.append(" FROM CONS_PRE_BILL_STAT_LOOKUP T ");
		querySB.append(" ORDER BY TO_NUMBER(T.CONS_PRE_BILL_STAT_ID) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is the SQL query to retrieve consumer details on the basis on
	 * Account ID and Bill Round Id. SQL query takes Account ID
	 * <code>ACCT_ID</code> and Bill Round Id <code>BILL_ROUND_ID</code> as
	 * parameter to retrieve records from the database.
	 * </p>
	 * 
	 * @see GetterDAO#getConsumerDetailByAccountIDAndBillRoundQuery()
	 * @param billRoundId
	 * @param accountIds
	 * @return Query String
	 */
	public static String getConsumerDetailByAccountIDAndBillRoundQuery(
			String billRoundId, String accountIds) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT X.ACCT_ID, ");
		querySB.append(" X.BILL_ROUND_ID, ");
		querySB.append(" X.CUST_CL_CD, ");
		querySB.append(" X.SA_TYPE_CD, ");
		querySB.append(" X.SVC_CYC_RTE_SEQ, ");
		querySB.append(" X.AVG_READ, ");
		querySB.append(" X.CONS_PRE_BILL_STAT_ID, ");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.PREV_METER_READ.READDATE, 'DD/MM/YYYY')), 'NA') PREV_MTRRDDT, ");
		querySB
				.append(" NVL(TRIM(X.PREV_METER_READ.REGISTEREDREAD), 'NA') PREV_RGMTRRD, ");
		querySB
				.append(" NVL(TRIM(X.PREV_METER_READ.REMARK), 'NA') PREV_MTRRDSTAT, ");
		querySB
				.append(" NVL(TRIM(X.PREV_METER_READ.READTYPE), 'NA') PREV_READTYPE, ");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.ACTUAL_METER_READ.READDATE, 'DD/MM/YYYY')), 'NA') ACTL_MTRRDDT, ");
		querySB
				.append(" NVL(TRIM(X.ACTUAL_METER_READ.REGISTEREDREAD), 'NA') ACTL_RGMTRRD, ");
		querySB
				.append(" NVL(TRIM(X.ACTUAL_METER_READ.REMARK), 'NA') ACTL_MTRRDSTAT, ");
		querySB
				.append(" NVL(TRIM(X.ACTUAL_METER_READ.READTYPE), 'NA') ACTL_READTYPE ");
		querySB.append(" FROM (SELECT CM_CONS_PRE_BILL_PROC.ACCT_ID, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CUST_CL_CD, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SA_TYPE_CD, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SVC_CYC_RTE_SEQ, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.AVG_READ, ");
		querySB
				.append(" TO_CHAR(CM_CONS_PRE_BILL_PROC.BILL_GEN_DATE, 'DD/MM/YYYY') BILL_GEN_DATE, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_BILL_GEN_DATE, ");
		querySB
				.append(" CM_GET_PREV_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) PREV_METER_READ, ");
		querySB
				.append(" CM_GET_LAST_ACTUAL_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) ACTUAL_METER_READ ");
		querySB.append(" FROM CM_CONS_PRE_BILL_PROC ");
		// querySB.append(" WHERE CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = '"
		// + billRoundId + "'");
		querySB.append(" WHERE CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID in ("
				+ billRoundId + ")");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID in (" + accountIds
				+ ")) X ");

		// for Test Purpose only
		// for mrkey 3030
		// querySB.append(" select * from HHD_TEST_DATA ");
		//
		// querySB.append(" WHERE HHD_TEST_DATA.BILL_ROUND_ID = '" + billRoundId
		// + "'");
		// querySB.append(" AND HHD_TEST_DATA.ACCT_ID in (" + accountIds +
		// ") ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get ConsumerDetails By KNO.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#getConsumerDetailsForMRByKNO
	 * @return query String
	 */
	public static String getConsumerDetailsForMRByKNOQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T.ACCT_ID,T.BILL_ROUND_ID, T.CUST_CL_CD, Z.SUBZONE_CD, Z.SUBZONE_NAME, D.MR_CD, D.AREA_CD, T.WCNO, T.CONSUMER_NAME, T.SA_TYPE_CD, T.AVG_READ, ");
		querySB.append(" t.cons_pre_bill_stat_id, ");
		querySB.append(" to_char(t.bill_gen_date,'dd/mm/yyyy')bill_gen_date,");
		querySB.append(" t.reg_reading,");
		querySB.append(" t.READ_TYPE_FLG,");
		querySB.append(" t.reader_rem_cd,");
		querySB.append(" t.new_avg_read,");
		querySB.append(" t.new_no_of_floors,");
		querySB.append(" t.remarks,");
		querySB
				.append(" to_char(t.last_updt_dttm,'dd/mm/yyyy hh:mi AM')last_updt_dttm,");
		querySB.append(" t.last_updt_by, ");
		/*
		 * Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871
		 */
		querySB
				.append(" to_char(t.data_entry_dttm,'dd/mm/yyyy hh:mi AM')data_entry_dttm,");
		querySB.append(" t.data_entry_by, ");
		querySB
				.append(" to_char(t.data_frozen_dttm,'dd/mm/yyyy hh:mi AM')data_frozen_dttm,");
		querySB.append(" t.data_frozen_by, ");
		querySB
				.append(" to_char(t.data_parked_dttm,'dd/mm/yyyy hh:mi AM')data_parked_dttm,");
		querySB.append(" t.data_parked_by ");
		/*
		 * End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871
		 */
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC  T, DJB_ZN_MR_AR_MRD D, DJB_SUBZONE Z, CM_MRD_PROCESSING_STAT S ");
		querySB
				.append(" WHERE T.MRKEY = D.MRD_CD AND D.SUBZONE_CD = Z.SUBZONE_CD AND T.MRKEY = S.MRKEY AND T.BILL_ROUND_ID = S.BILL_ROUND_ID AND D.MRD_CD = S.MRKEY ");
		querySB.append(" AND S.MRD_STATS_ID = " + OPEN_BILL_ROUND_STATUS_CODE);
		querySB.append("AND T.ACCT_ID = ?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get ConsumerDetails By Zone Area MR and Water Connection No.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#getConsumerDetailsForMRByZMAW
	 * @return query String
	 */
	public static String getConsumerDetailsForMRByZMAWQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T.ACCT_ID,T.BILL_ROUND_ID, T.CUST_CL_CD, Z.SUBZONE_CD, Z.SUBZONE_NAME, D.MR_CD, D.AREA_CD, T.WCNO, T.CONSUMER_NAME, T.SA_TYPE_CD, T.AVG_READ, ");
		querySB.append(" t.cons_pre_bill_stat_id, ");
		querySB.append(" to_char(t.bill_gen_date,'dd/mm/yyyy')bill_gen_date,");
		querySB.append(" t.reg_reading,");
		querySB.append(" t.READ_TYPE_FLG,");
		querySB.append(" t.reader_rem_cd,");
		querySB.append(" t.new_avg_read,");
		querySB.append(" t.new_no_of_floors,");
		querySB.append(" t.remarks,");
		querySB
				.append(" to_char(t.last_updt_dttm,'dd/mm/yyyy hh:mi AM')last_updt_dttm,");
		querySB.append(" t.last_updt_by ");
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC  T, DJB_ZN_MR_AR_MRD D, DJB_SUBZONE Z, CM_MRD_PROCESSING_STAT S ");
		querySB
				.append(" WHERE T.MRKEY = D.MRD_CD AND D.SUBZONE_CD = Z.SUBZONE_CD AND T.MRKEY = S.MRKEY AND T.BILL_ROUND_ID = S.BILL_ROUND_ID AND D.MRD_CD = S.MRKEY ");
		querySB.append(" AND S.MRD_STATS_ID = " + OPEN_BILL_ROUND_STATUS_CODE);
		querySB.append(" AND Z.SUBZONE_CD = ?");
		querySB.append(" AND D.MR_CD = ?");
		querySB.append(" AND D.AREA_CD = ?");
		querySB.append(" AND T.WCNO = ?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Consumer Details List For Demand Transfered Main Query.
	 * </p>
	 * 
	 * @param knoList
	 * @return
	 */
	public static String getConsumerDetailsListForDemandTransferedMainQuery(
			String knoList, String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.ACCT_ID,T.WCNO,T.CUST_NAME,T.ADDRESS,T.CUST_CATEGORY,T.OLD_MRKEY,T.OLD_RTE_SEQ,T.STAT_FLG,T.NEW_MRKEY,T.NEW_RTE_SEQ,T.ERROR_MSG,T.CREATED_BY FROM CM_DMND_TRNSFR_STAGE T ");
		querySB.append(" WHERE 1=1");
		if (null != knoList && knoList.length() > 10) {
			querySB.append(" AND T.ACCT_ID IN (" + knoList + ")");
		}
		if (null != userId) {
			querySB.append(" AND UPPER(T.CREATED_BY)=UPPER('" + userId + "')");
		}
		querySB.append(" ORDER BY ");
		querySB.append(" T.CREATE_DTTM DESC ");
		querySB.append(" ,T.CREATED_BY ");
		querySB.append(" ,TO_NUMBER(T.OLD_RTE_SEQ) DESC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Consumer Details List For Demand Transfered Query.
	 * </p>
	 * 
	 * @param knoList
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getConsumerDetailsListForDemandTransferedQuery(
			String knoList, String userId, String pageNo, String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getConsumerDetailsListForDemandTransferedMainQuery(
				knoList, userId));
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Main query to Retrieve Consumer Details List For Demand Transfer
	 * corresponding to zone,mrNo,area.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @return
	 */
	private static Object getConsumerDetailsListForDemandTransferMainQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT DISTINCT A.ACCT_ID AS ACCT_ID,");
		querySB.append(" CM_GET_ACTUAL_WCNO(A.ACCT_ID) AS WCNO,");
		querySB.append(" CM_GET_NAME(SYSDATE, A.ACCT_ID) AS CONSUMER_NAME, ");
		querySB.append(" CM_GET_ADD_STR(C.CHAR_PREM_ID) AS ADDRESS,");
		querySB.append(" B.CUST_CL_CD AS CATEGORY,");
		querySB.append(" D.MR_CYC_RTE_SEQ AS ROUTESEQ");
		querySB.append(" FROM CI_ACCT_CHAR A, CI_ACCT B, CI_SA C, CI_SP D");
		querySB.append(" WHERE A.ACCT_ID = B.ACCT_ID");
		querySB.append(" AND B.ACCT_ID = C.ACCT_ID");
		querySB.append(" AND C.CHAR_PREM_ID = D.PREM_ID");
		querySB.append(" AND D.SP_SRC_STATUS_FLG = 'C'");
		querySB.append(" AND C.CHAR_PREM_ID IS NOT NULL");
		// querySB
		// .append(" AND TRIM(C.SA_TYPE_CD) IN ('SAWATR', 'SAWATSEW', 'UNMTRD')");
		// querySB.append(" AND C.SA_STATUS_FLG = 20");
		querySB
				.append(" AND A.ACCT_ID NOT IN (SELECT T.ACCT_ID FROM CM_DMND_TRNSFR_STAGE T WHERE T.STAT_FLG IN ('PENDING','IN PROGRESS'))");
		querySB.append(" AND A.CHAR_TYPE_CD = 'MRKEY'");
		querySB.append(" AND A.ADHOC_CHAR_VAL = ?");
		querySB
				.append(" AND CM_GET_MRKEY_FROM_ACCOUNT(A.ACCT_ID, SYSDATE) = ?");
		querySB.append(" ORDER BY D.MR_CYC_RTE_SEQ ");
		AppLog.info(querySB.toString());
		return querySB.toString();

	}

	/**
	 * <p>
	 * To Retrieve Consumer Details List For Demand Transfer corresponding to
	 * zone,mrNo,area,pageNo,recordPerPage with pagination.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getConsumerDetailsListForDemandTransferQuery(
			String mrKey, String pageNo, String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getConsumerDetailsListForDemandTransferMainQuery());
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Consumer Details List For New Route Sequences Main Query.
	 * </p>
	 * 
	 * @param spIdList
	 * @return
	 */
	public static String getConsumerDetailsListForNewRouteSequencesMainQuery(
			String spIdList) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T3.ACCT_ID AS KNO,CM_GET_ADD_STR (T1.PREM_ID) AS ADDR,  ");
		querySB
				.append(" CM_GET_NAME (SYSDATE,T3.ACCT_ID) AS CONSNAME,T4.CUST_CL_CD  AS CAT, ");
		querySB
				.append("  T1.MR_CYC_RTE_SEQ  AS RSEQ,T6.ADHOC_CHAR_VAL  AS WCNO ");
		querySB
				.append("  FROM CI_SP T1 ,CI_SA_SP T2,CI_SA T3,CI_ACCT T4,CI_ACCT_CHAR T5,CI_ACCT_CHAR T6 ");
		querySB.append("  WHERE T1.SP_ID IN (" + spIdList
				+ ") AND T2.SP_ID = T1.SP_ID ");
		querySB.append(" AND T3.SA_ID = T2.SA_ID AND T4.ACCT_ID =T3.ACCT_ID ");
		querySB
				.append(" AND T5.ACCT_ID = T3.ACCT_ID AND T5.CHAR_TYPE_CD = 'MRKEY' ");
		querySB
				.append(" AND T6.ACCT_ID = T3.ACCT_ID AND T6.CHAR_TYPE_CD = 'WCNO' ");
		querySB
				.append(" AND T5.EFFDT = (SELECT MAX(X.EFFDT) FROM CI_ACCT_CHAR X WHERE X.ACCT_ID = T5.ACCT_ID AND X.CHAR_TYPE_CD = T5.CHAR_TYPE_CD");
		querySB.append(" AND X.EFFDT < SYSDATE GROUP BY X.ACCT_ID ) ");
		querySB.append(" ORDER BY T1.MR_CYC_RTE_SEQ ASC ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Consumer Details List For New Route Sequences Query.
	 * </p>
	 * 
	 * @param knoList
	 * @return
	 */
	public static String getConsumerDetailsListForNewRouteSequencesQuery(
			String spIdList
	// , String pageNo, String recordPerPage
	) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		// querySB.append(" SELECT T2.* ");
		// querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB
				.append(getConsumerDetailsListForNewRouteSequencesMainQuery(spIdList));
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		// querySB.append(" ) T1 ");
		// querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
		// + ") + 1)) T2 ");
		// querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
		// + recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Main query to Retrieve Consumer Details List For Renumber Route Sequences
	 * corresponding to mrkey,sequenceRangeFrom and sequenceRangeTo which are
	 * optional.
	 * </p>
	 * 
	 * @param mrkey
	 * @param sequenceRangeFrom
	 * @param sequenceRangeTo
	 * @return
	 */
	private static Object getConsumerDetailsListForRenumberRouteSequencesMainQuery(
			String mrKey
	// , String serviceCycle, String serviceRoute
	// , String sequenceRangeFrom, String sequenceRangeTo
	) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT T4.ACCT_ID AS KNO,");
		querySB
				.append(" A.CUST_CL_CD AS CAT, CM_GET_NAME (SYSDATE,A.ACCT_ID) AS CONSNAME,");
		querySB.append(" CM_GET_ADD_STR (T1.PREM_ID) AS ADDR, ");
		querySB
				.append(" T5.ADHOC_CHAR_VAL AS WCNO, T1.MR_CYC_RTE_SEQ AS RSEQ,");
		querySB.append("  T1.SP_ID AS SPID FROM CI_SP T1 ");
		querySB.append(" INNER JOIN CI_SA_SP T2 ON T2.SP_ID = T1.SP_ID ");
		// if (null != sequenceRangeFrom
		// && !"".equalsIgnoreCase(sequenceRangeFrom)) {
		// querySB.append(" AND T1.MR_CYC_RTE_SEQ >=" + sequenceRangeFrom);
		// }
		// if(null != sequenceRangeTo
		// && !"".equalsIgnoreCase(sequenceRangeTo)){
		// querySB.append(" AND T1.MR_CYC_RTE_SEQ <=" + sequenceRangeTo);
		//			
		// }
		querySB.append(" INNER JOIN CI_SA T3 ON T3.SA_ID = T2.SA_ID ");
		querySB.append(" INNER JOIN CI_ACCT A ON A.ACCT_ID = T3.ACCT_ID ");
		querySB
				.append(" INNER JOIN CI_ACCT_CHAR T4 ON T4.ACCT_ID = T3.ACCT_ID AND T4.CHAR_TYPE_CD = 'MRKEY' ");
		querySB
				.append(" LEFT JOIN CI_ACCT_CHAR T5 ON T5.ACCT_ID = A.ACCT_ID AND T5.CHAR_TYPE_CD = 'WCNO' ");
		querySB.append(" WHERE T4.ADHOC_CHAR_VAL ='" + mrKey + "'");
		// querySB.append(" T1.MR_CYC_CD ='" + serviceCycle + "' AND ");
		// querySB.append(" T1.MR_RTE_CD ='" + serviceRoute + "' ");
		querySB
				.append(" AND T4.EFFDT = (SELECT MAX(X.EFFDT) FROM CI_ACCT_CHAR X WHERE X.ACCT_ID = T4.ACCT_ID AND X.CHAR_TYPE_CD = T4.CHAR_TYPE_CD ");
		querySB.append(" AND X.EFFDT < SYSDATE GROUP BY X.ACCT_ID ) ");
		querySB.append(" ORDER BY T1.MR_CYC_RTE_SEQ ASC ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve Consumer Details List For Renumber Route Sequences
	 * corresponding to mrkey,serviceCycle and serviceRoute
	 * </p>
	 * 
	 * @param mrkey
	 * @param serviceCycle
	 * @param serviceRoute
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getConsumerDetailsListForRenumberRouteSequencesQuery(
			String mrKey
	// , String serviceCycle, String serviceRoute
	// , String pageNo, String recordPerPage
	// , String sequenceRangeFrom, String sequenceRangeTo
	) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		// querySB.append(" SELECT T2.* ");
		// querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		// querySB.append(getConsumerDetailsListForRenumberRouteSequencesMainQuery(mrKey,serviceCycle,serviceRoute));
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		// querySB.append(" ) T1 ");
		// querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
		// + ") + 1)) T2 ");
		// querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
		// + recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		querySB
				.append(getConsumerDetailsListForRenumberRouteSequencesMainQuery(mrKey
				// , serviceCycle, serviceRoute
				// , sequenceRangeFrom, sequenceRangeTo
				));
		// AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * This a query used to get Consumer Mobile Numbers
	 * </p>
	 * 
	 * @param kno
	 * @return
	 * @author 830700
	 * @since 10-03-2016
	 */
	public static String getConsumerMobileNumberQuery(String kno) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT CM_GET_PHONE(?) AS MOB_NO FROM DUAL");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to get Record Status And ErrorDesc.
	 * </p>
	 */
	public static String getConsumerStatusfromDB() {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select cons_pre_bill_stat_id,descr from cons_pre_bill_stat_lookup");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This a query used to get consumption variation record details list
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	private static String getConsumptionVariationRecordDetailsListMainQuery(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT A.KNO AS KNO, ");
		querySB.append("       A.BILL_ROUND AS BILL_ROUND, ");
		querySB.append("       A.MTR_NO AS MTR_NO, ");
		querySB
				.append("       A.PER_VARIATION_AVG_CNSUMPTN AS ANUAL_VARIATION, ");
		querySB
				.append("       A.PER_VARIATION_PREVUS_READNG AS PREV_VARIATION, ");
		querySB.append("       A.LAST_MTR_READNG AS LAST_MTR_READ, ");
		querySB.append("       A.CURR_MTR_READNG AS CURR_MTR_READ, ");
		querySB
				.append("       A.VARIATION_PREVUS_READNG AS VARIATION_PREV_READ, ");
		querySB
				.append("       TO_CHAR(A.LAST_AUDIT_DATE, 'DD-MON-YYYY') AS LAST_AUDIT_DT, ");
		querySB.append("       A.LAST_AUDIT_STATUS AS LAST_AUDIT_STATUS, ");
		querySB.append("       A.LAST_AUDIT_RMRK AS LAST_AUDIT_RMRK, ");
		querySB.append("       A.SITE_VIST_REQURD AS SITE_VIST_REQURD, ");
		querySB.append("       A.ASSIGN_TO AS ASSIGN_TO, ");
		querySB.append("       A.ANUAL_AVG_CNSUMPTN AS ANUAL_AVG_CNSUMPTN, ");
		querySB.append("       A.ASSIGN_TO_FLG AS ASSIGN_TO_FLG ");
		querySB.append("FROM   DJB_AUDIT_STG A ");
		querySB.append("WHERE  1 = 1");
		if (null != consumptionAuditSearchCriteria.getSearchZROCode()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchZROCode())) {
			querySB.append("AND    A.ZRO_LOCATION = ? ");

		}
		if (null != consumptionAuditSearchCriteria.getSearchKno()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchKno())) {
			querySB.append("AND    A.KNO = ? ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchBillRound()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchBillRound())) {
			querySB.append("AND    A.BILL_ROUND = ? ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchVarAnualAvgConsumption()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption())) {
			if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
					.equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarAnualAvgConsumption())) {
				querySB.append("AND    A.PER_VARIATION_AVG_CNSUMPTN > ? ");
			} else {
				querySB.append("AND    A.PER_VARIATION_AVG_CNSUMPTN > ? ");
				querySB.append("AND    A.PER_VARIATION_AVG_CNSUMPTN <= ? ");
			}

		}
		if (null != consumptionAuditSearchCriteria.getSearchVarPrevReading()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarPrevReading())) {
			if (DJBConstants.RANGE_ABOVE_200_CONSUMPTION
					.equalsIgnoreCase(consumptionAuditSearchCriteria
							.getSearchVarPrevReading())) {
				querySB.append("AND    A.PER_VARIATION_PREVUS_READNG > ? ");
			} else {
				querySB.append("AND    A.PER_VARIATION_PREVUS_READNG > ? ");
				querySB.append("AND    A.PER_VARIATION_PREVUS_READNG < ? ");
			}

		}
		if (null != consumptionAuditSearchCriteria
				.getSearchLastAuditedBeforeDate()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate())) {
			querySB.append("AND    A.LAST_AUDIT_DATE <= ? ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchConsumptionVariationAuditStatus()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus())
				&& !"ALL".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus())) {
			if ("PENDING".equalsIgnoreCase(consumptionAuditSearchCriteria
					.getSearchConsumptionVariationAuditStatus())) {
				querySB
						.append("AND    (A.LAST_AUDIT_STATUS IS NULL OR A.LAST_AUDIT_STATUS= ? ) ");
			} else {
				querySB.append("AND    A.LAST_AUDIT_STATUS = ? ");
			}

		}
		querySB.append("ORDER BY    A.PER_VARIATION_PREVUS_READNG DESC ");
		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to get Meter Read Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#saveMeterReplacementDetail(com.tcs.djb.model.MeterReplacementDetail)
	 * @return query String
	 */
	public static String getCurrentMeterReadDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" select ");
		querySB.append(" t.cons_pre_bill_stat_id, ");
		querySB.append(" to_char(t.bill_gen_date,'dd/mm/yyyy')bill_gen_date,");
		querySB.append(" t.reg_reading,");
		querySB.append(" t.avg_read,");
		querySB.append(" t.READ_TYPE_FLG,");
		querySB.append(" t.reader_rem_cd,");
		querySB.append(" t.new_avg_read,");
		querySB.append(" t.new_no_of_floors,");
		querySB.append(" t.remarks,");
		querySB
				.append(" to_char(t.last_updt_dttm,'dd/mm/yyyy hh:mi AM')last_updt_dttm,");
		querySB.append(" t.last_updt_by ");
		querySB.append(" from  cm_cons_pre_bill_proc t ");
		querySB.append(" where 1=1");
		querySB.append(" AND  ACCT_ID=?");
		querySB.append(" AND  BILL_ROUND_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Meter Replacement Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#saveMeterReplacementDetail
	 * @return query String
	 */
	public static String getCurrentMeterReplacementDetailQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT ");
		querySB.append(" ZONECD,");
		querySB.append(" MRNO,");
		querySB.append(" AREANO,");
		querySB.append(" WCNO,");
		querySB.append(" CONSUMER_NAME,");
		querySB.append(" CUST_CL_CD,");
		querySB.append(" MTR_RPLC_STAGE_ID,");
		querySB.append(" WCONSIZE,");
		querySB.append(" WCONUSE,");
		querySB.append(" SA_TYPE_CD,");
		querySB.append(" MTR_READER_ID,");
		querySB.append(" MTRTYP,");
		querySB
				.append(" TO_CHAR(MTR_INSTALL_DATE,'DD/MM/YYYY')MTR_INSTALL_DATE,");
		querySB.append(" READER_REM_CD,");
		querySB.append(" BADGE_NBR,");
		querySB.append(" MFG_CD,");
		querySB.append(" MODEL_CD,");
		querySB.append(" MTR_START_READ,");
		querySB.append(" MTR_START_READ_REMARK,");
		querySB.append(" IS_LNT_SRVC_PRVDR,");
		querySB.append(" OLD_SA_TYPE_CD,");
		querySB
				.append(" TO_CHAR(OLD_SA_START_DATE,'DD/MM/YYYY')OLD_SA_START_DATE,");
		querySB
				.append(" TO_CHAR(OLD_MTR_READ_DATE,'DD/MM/YYYY')OLD_MTR_READ_DATE,");
		querySB.append(" OLD_MTR_READ,");
		querySB.append(" OLDAVGCONS,");
		querySB
				.append(" CREATED_BY,TO_CHAR(CREATE_DTTM,'DD/MM/YYYY HH:MI AM')CREATE_DTTM,");
		querySB
				.append(" LAST_UPDT_BY,TO_CHAR(LAST_UPDT_DTTM,'DD/MM/YYYY HH:MI AM')LAST_UPDT_DTTM,");
		querySB.append(" ACCT_ID,");
		querySB.append(" BILL_ROUND_ID,");
		querySB.append(" DE_CODE_VRSN, ");
		// Changed by Rajib as per Jtrac DJB-2024 to get tp_vendor_name column
		// data
		querySB.append(" TP_VENDOR_NAME ");
		// Chnage end by Rajib as per Jtrac DJb-2024 on 03-01-2014
		querySB.append(" FROM CM_MTR_RPLC_STAGE");
		querySB.append(" WHERE ACCT_ID=? ");
		querySB.append(" AND BILL_ROUND_ID=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This a query used to get Current Version
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getCurrentVersionQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT VERSION_NO FROM DJB_VERSION_DETAILS WHERE SYSDATE BETWEEN EFF_FROM_DATE AND EFF_TO_DATE");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get consumer class of the respective KNO.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */

	public static String getCustomerClassQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select trim(t.cust_cl_cd) as CUSTOMER_CLS_CD from ci_acct t  where t.acct_id=? ");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This a query used to get Dev Charge list
	 * </p>
	 * 
	 * @return
	 */
	public static String getDevChargeListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT PARAM_KEY, PARAM_VALUE FROM DJB_DEV_CHRG_KEY_VALUE WHERE PARAM_TYP=? AND IS_ACTV='Y' ORDER BY PARAM_VALUE ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get active year interval value for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getDevChargeYearIntervalQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT PARAM_KEY FROM DJB_DEV_CHRG_KEY_VALUE WHERE PARAM_VALUE=? AND IS_ACTV='Y'   ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get DE App ZRO Access CD for user as per Jtrac DJB-4313
	 * </p>
	 * 
	 * @param fileNo
	 * @return
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 27-JAN-2015
	 */
	public static String getDeZroAccessForUserIdQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT USER_ZRO_CD FROM DJB_DATA_ENTRY_USERS U WHERE U.USER_ID= ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is the SQL query to retrieve the configuration String from
	 * <code>DJB_PARAM_MST</code> with <code>PARAM_ID</code> value stored in the
	 * database.
	 * </p>
	 * 
	 * @see MeterReadDAO#getMeterReadWSConfigParamQuery
	 * @return Query String
	 */
	public static String getDJBParamValueQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.PARAM_VALUE FROM DJB_PARAM_MST T WHERE T.PARAM_ID= ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get DOC Attach List map for New Connection Fle Entry Screen as
	 * per Jtrac DJB-4313
	 * </p>
	 * 
	 * @return String
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 14-JAN-2016
	 */
	public static String getDocAttchListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT DOC_TEXT, DOC_VALUE FROM DOC_ATTCH_LIST WHERE TYPE_OF_DOC=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the error message in case of proc call for dev charge, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getErrorMessageQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.ERROR_MESSAGE AS ERROR_MESSAGE FROM DJB_DEV_CHG_ERROR_LOG A ");
		querySB.append(" WHERE A.COLONY_ID = ? AND A.DATA_FROZEN_BY = ? ");
		querySB.append(" AND A.DATA_FROZEN_DTTM >= SYSDATE-"
				+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		querySB.append(" AND A.CRT_BY_PROC = ? ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve existing generated Lot No.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 01-02-2016
	 * @param selectedFileNo
	 * @return query String
	 */
	public static String getExistingLotCountQuery(String selectedFileNo) {
		String selectedFNOArray[] = selectedFileNo.split(",");
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select count(1) as existingLot from CM_NEW_CONN_FILE_ENTRY_STG ");
		if (null != selectedFileNo && !"".equalsIgnoreCase(selectedFileNo)) {
			querySB.append("   WHERE    file_no in ( ");
			for (int i = 0; i < selectedFNOArray.length; i++) {
				String tempString = selectedFNOArray[i];
				if (null != tempString && !"".equalsIgnoreCase(tempString)) {
					querySB.append("   '" + tempString + "', ");
				}

			}
			querySB.append("   '') ");
			querySB.append("  and LOT_NO is not null ");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get File Entry Count for New Connection Fle Entry Screen as per
	 * Jtrac DJB-4313
	 * </p>
	 * 
	 * @return String
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 27-JAN-2016
	 */
	public static String getFileCountQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COUNT(*) CNT FROM CM_NEW_CONN_FILE_ENTRY_STG T WHERE T.FILE_NO=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Main query to Retrieve File Number List For Search corresponding to zone.
	 * </p>
	 * 
	 * @param fileNumberSearchCrieteria
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 27-01-2016
	 */
	private static Object getFileListForSearchMainQuery(
			FileNumberSearchCrieteria fileNumberSearchCrieteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT FILE_NO AS FILE_NO,");
		querySB.append(" ARN_NO AS ARN_NO,");
		querySB
				.append(" first_name||' '||middle_name||' '||last_name AS CONSUMER_NAME, ");
		querySB.append(" STATUS_CD AS STATUS_CD,");
		querySB.append(" CREATED_BY AS LOGGED_BY,");
		querySB.append(" to_char(CREATED_DTTM, 'DD/MM/YYYY') AS LOGDATE,");
		querySB.append(" LOT_NO AS LOT_NO");
		querySB.append(" FROM CM_NEW_CONN_FILE_ENTRY_STG");
		querySB.append("  WHERE ");
		if (null != fileNumberSearchCrieteria.getZone()
				&& !"".equalsIgnoreCase(fileNumberSearchCrieteria.getZone())) {
			querySB.append("  ZRO_CD ='"
					+ fileNumberSearchCrieteria.getZone().trim() + "'");
		}
		if (null != fileNumberSearchCrieteria.getuserList()
				&& !""
						.equalsIgnoreCase(fileNumberSearchCrieteria
								.getuserList())) {
			querySB.append("  AND CREATED_BY ='"
					+ fileNumberSearchCrieteria.getuserList().trim() + "'");
		}
		if (null != fileNumberSearchCrieteria.getLoggedFromDate()
				&& !"".equalsIgnoreCase(fileNumberSearchCrieteria
						.getLoggedFromDate())) {
			querySB.append("  AND TRUNC(CREATED_DTTM) >=to_date('"
					+ fileNumberSearchCrieteria.getLoggedFromDate().trim()
					+ "', 'DD/MM/YYYY')");
		}
		if (null != fileNumberSearchCrieteria.getLoggedToDate()
				&& !"".equalsIgnoreCase(fileNumberSearchCrieteria
						.getLoggedToDate())) {
			querySB.append("  AND TRUNC(CREATED_DTTM) <=to_date('"
					+ fileNumberSearchCrieteria.getLoggedToDate().trim()
					+ "', 'DD/MM/YYYY')");
		}
		if (null != fileNumberSearchCrieteria.getFileNo()
				&& !"".equalsIgnoreCase(fileNumberSearchCrieteria.getFileNo())) {
			querySB.append("  AND FILE_NO ='"
					+ fileNumberSearchCrieteria.getFileNo().trim() + "'");
		}
		if (null != fileNumberSearchCrieteria.getLotNo()
				&& !"".equalsIgnoreCase(fileNumberSearchCrieteria.getLotNo())) {
			querySB.append("  AND LOT_NO ='"
					+ fileNumberSearchCrieteria.getLotNo().trim() + "'");
		}
		if (null != fileNumberSearchCrieteria.getStatusCD()
				&& !""
						.equalsIgnoreCase(fileNumberSearchCrieteria
								.getStatusCD())
				&& !"Please Select".equalsIgnoreCase(fileNumberSearchCrieteria
						.getStatusCD())) {
			querySB.append("  AND STATUS_CD ='"
					+ fileNumberSearchCrieteria.getStatusCD().trim() + "'");
		}
		/* Start : By Arnab Nandy at 11-02-2016 */
		querySB.append(" ORDER BY FILE_NO");
		/* End : By Arnab Nandy at 11-02-2016 */
		AppLog.info(querySB.toString());
		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to get File Location ZRO Cd for File No. passed as per JTrac
	 * DJB-4313
	 * </p>
	 * 
	 * @param fileNo
	 * @return
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 27-JAN-2015
	 */
	public static String getFileLocationZroCdQuery(String fileNo) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT ZRO_CD FROM CM_NEW_CONN_FILE_MASTER M WHERE ");
		querySB.append(fileNo + " BETWEEN M.MIN_VAL AND M.MAX_VAL ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve File Number Details List For search corresponding to
	 * fileNumberSearchCrieteria,pageNo,recordPerPage with pagination.
	 * </p>
	 * 
	 * @param fileNumberSearchCrieteria
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 27-01-2016
	 */
	public static String getFileNumberSearchDetailListQuery(
			FileNumberSearchCrieteria fileNumberSearchCrieteria, String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB
				.append(getFileListForSearchMainQuery(fileNumberSearchCrieteria));
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Status of Files in File Number Search.
	 * </p>
	 * 
	 * @param key
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 27-01-2016
	 */
	public static String getFileSearchStatus(String key) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT CD AS CD,");
		querySB.append("  STATUS_TXT AS STATUS_TXT");
		querySB.append(" FROM CM_FILE_SEARCH_STATUS_CD");
		if (null != key && !"".equalsIgnoreCase(key)) {
			querySB.append("  WHERE STATUS_TXT ='" + key.trim() + "'");
		}

		AppLog.info(querySB.toString());
		return querySB.toString();

	}

	/**
	 * <p>
	 * Get File from Dwarka Master Table to generate reports, according to as
	 * per JTrac DJB-4465 and OpenProject-918.
	 * </p>
	 * 
	 * @return
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 01-06-2016
	 */
	public static String getFilesQuery() {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select distinct d.source_file_name from cm_dwarka_master_data d ");

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Cons PreBill Status Lookup List Query from which status will
	 * be updated.
	 * </p>
	 * 
	 * @return query String
	 */
	public static String getFromConsPreBillValidStatLookupListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.CONS_PRE_BILL_STAT_ID,A.CONS_PRE_BILL_STAT_ID || '-' || A.DESCR DESCR");
		querySB
				.append(" FROM CONS_PRE_BILL_STAT_LOOKUP A,CONS_PRE_BILL_VALID_STATUS B ");
		querySB
				.append(" WHERE A.CONS_PRE_BILL_STAT_ID=B.FROM_STAT_ID AND B.FROM_STAT_ID=? ");
		querySB.append(" ORDER BY TO_NUMBER(A.CONS_PRE_BILL_STAT_ID) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD credentials
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * @param status
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDCredentialsDetailsListMainQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT USER_ID, ");
		querySB.append(" USER_PASSWORD, ");
		querySB
				.append(" TO_CHAR(EFF_FROM_DATE, 'DD/MM/YYYY HH:MI AM') EFF_FROM_DATE, ");
		querySB
				.append(" TO_CHAR(EFF_TO_DATE, 'DD/MM/YYYY HH:MI AM') EFF_TO_DATE ,");
		querySB.append(" LAST_UPDT_BY, ");
		querySB
				.append(" TO_CHAR(LAST_UPDT_DTTM, 'DD/MM/YYYY HH:MI AM') LAST_UPDT_DTTM, ");
		querySB.append(" TO_CHAR(SYSDATE, 'DD/MM/YYYY HH:MI AM') SYS_DATE ");
		querySB.append(" FROM djb_web_service_users");

		querySB.append(" ORDER BY user_id");
		querySB.append(" ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Credentials
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDCredentialsDetailsListQuery(String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getHHDCredentialsDetailsListMainQuery());
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get HHD ID of users
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getHHDIdOfUserQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT R.HHD_ID HHDID FROM DJB_MTR_RDR R WHERE R.EMP_ID = ?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Meter Reader Mapping Details corresponding to
	 * status,zoneCode,pageNo,recordPerPage.
	 * </p>
	 * 
	 * @param status
	 * @param zoneCode
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDMappingDetailsListMainQuery(String status,
			String zoneCode, String hhdId, String mrKeyList) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT DJB_ZN_MR_AR_MRD.SUBZONE_CD, ");
		querySB.append(" DJB_ZN_MR_AR_MRD.MR_CD, ");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_CD, ");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_DESC, ");
		querySB
				.append(" DJB_ZN_MR_AR_MRD.MRD_CD, DJB_MRD_RDR_HHD.MRD_CD HHD_MRD_CD, ");
		querySB.append(" DJB_MRD_RDR_HHD.MTR_RDR_CD,");
		querySB.append(" DJB_MRD_RDR_HHD.HHD_ID, ");
		querySB
				.append(" TO_CHAR(DJB_MRD_RDR_HHD.EFF_FROM_DATE, 'DD/MM/YYYY HH:MI AM') EFF_FROM_DATE, ");
		querySB
				.append(" TO_CHAR(DJB_MRD_RDR_HHD.EFF_TO_DATE, 'DD/MM/YYYY HH:MI AM') EFF_TO_DATE ,");
		querySB.append(" DJB_MRD_RDR_HHD.CREATE_BY LAST_UPDT_BY, ");
		querySB
				.append(" TO_CHAR(DJB_MRD_RDR_HHD.CREATE_DTTM, 'DD/MM/YYYY HH:MI AM') LAST_UPDT_DTTM, ");
		querySB.append(" TO_CHAR(SYSDATE, 'DD/MM/YYYY HH:MI AM') SYS_DATE ");
		querySB.append(" FROM DJB_ZN_MR_AR_MRD, DJB_MRD_RDR_HHD ");
		querySB
				.append(" WHERE DJB_MRD_RDR_HHD.MRD_CD(+) = DJB_ZN_MR_AR_MRD.MRD_CD ");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.EFF_TO_DATE IS NULL ");
		if ("Active".equalsIgnoreCase(status)) {
			querySB
					.append(" AND (DJB_MRD_RDR_HHD.EFF_TO_DATE IS NULL OR SYSDATE BETWEEN DJB_MRD_RDR_HHD.EFF_FROM_DATE AND DJB_MRD_RDR_HHD.EFF_TO_DATE) ");
		} else if ("Inactive".equalsIgnoreCase(status)) {
			querySB
					.append(" AND (DJB_MRD_RDR_HHD.EFF_TO_DATE IS NOT NULL AND SYSDATE > DJB_MRD_RDR_HHD.EFF_TO_DATE) ");
		}
		if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim())) {
			querySB.append(" AND DJB_ZN_MR_AR_MRD.MRD_CD IN ( " + mrKeyList
					+ " )");
		} else if (null != zoneCode && !"".equalsIgnoreCase(zoneCode)) {
			querySB.append(" AND DJB_ZN_MR_AR_MRD.SUBZONE_CD ='" + zoneCode
					+ "'");
		}
		if (null != hhdId && !"".equals(hhdId.trim())) {
			querySB.append(" AND DJB_MRD_RDR_HHD.HHD_ID ='" + hhdId + "'");
		}
		querySB.append(" ORDER BY DJB_ZN_MR_AR_MRD.SUBZONE_CD,");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_DESC ");
		// querySB.append(" ,TO_NUMBER(DJB_ZN_MR_AR_MRD.MR_CD) ");
		// querySB.append(" ,TO_NUMBER(DJB_ZN_MR_AR_MRD.MRD_CD) ");
		querySB.append(" ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Meter Reader Mapping Details corresponding to status,
	 * zoneCode,pageNo,recordPerPage with pagination.
	 * </p>
	 * 
	 * @param status
	 * @param zoneCode
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDMappingDetailsListQuery(String status,
			String zoneCode, String hhdId, String mrKeyList, String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getHHDMappingDetailsListMainQuery(status, zoneCode,
				hhdId, mrKeyList));
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Master Screen Details corresponding to
	 * pageNo,recordPerPage from <code>HHD_MASTER</code> table of database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getHHDMasterDetailsListMainQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT HHD_ID,BLOCKED,SIM_ID,SIM_NO FROM HHD_MASTER ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Master Screen Details corresponding to
	 * pageNo,recordPerPage with pagination from <code>HHD_MASTER</code> table
	 * of database.
	 * </p>
	 * 
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDMasterDetailsListQuery(String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getHHDMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD User Master Screen Details corresponding to
	 * pageNo,recordPerPage from <code>HHD_USER_MASTER</code> table of database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getHHDUserMasterDetailsListMainQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT USER_ID,PASSWORD,USR_LOGIN_COUNT,HHD_ID FROM HHD_USER_MASTER ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD User Master Screen Details corresponding to
	 * pageNo,recordPerPage with pagination from <code>HHD_USER_MASTER</code>
	 * table of database.
	 * </p>
	 * 
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDUserMasterDetailsListQuery(String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getHHDUserMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Version Master Screen Details corresponding to
	 * pageNo,recordPerPage from <code>HHD_VERSION_MASTER</code> table of
	 * database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getHHDVersionMasterDetailsListMainQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT VERSION_NO,FTP_LOCATION,FTP_USER,FTP_PASSWORD FROM HHD_VERSION_MASTER ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To Retrieve HHD Version Master Screen Details corresponding to
	 * pageNo,recordPerPage with pagination from <code>HHD_VERSION_MASTER</code>
	 * table of database.
	 * </p>
	 * 
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 */
	public static String getHHDVersionMasterDetailsListQuery(String pageNo,
			String recordPerPage) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getHHDVersionMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to get KNO Audit details list
	 * </p>
	 * 
	 * @return
	 */
	public static String getKnoAuditDetailListForConsumptionAudit() {

		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT * ");
		querySB.append("FROM   (SELECT S.BILL_ROUND AS BILL_ROUND, ");
		querySB.append("               S.BILLNG_PERIOD AS BILLNG_PERIOD, ");
		querySB.append("               S.BILL_GENRATD_BY AS BILL_GENRATD_BY, ");
		querySB
				.append("               TO_CHAR(S.BILL_DT, 'DD-MON-YYYY') AS BILL_DT, ");
		querySB.append("               S.MTR_READ_REMRK AS MTR_READ_REMRK, ");
		querySB.append("               S.CONSUMPTION AS CONSUMPTION, ");
		querySB
				.append("               S.VARIATION_PREVUS_READNG AS VARIATION_PREVUS_READNG, ");
		querySB
				.append("               S.PER_VARIATION_AVG_CNSUMPTN AS ANUAL_VARIATION_AVG_CNSUMPTN, ");
		querySB.append("               S.BILL_AMT AS BILL_AMOUNT, ");
		querySB.append("               S.PYMNT_AMT AS PAYMENT_AMOUNT ");
		querySB.append("        FROM   DJB_AUDIT_STG S ");
		querySB.append("        WHERE  1 = 1 ");
		querySB.append("        AND    S.KNO = ? ");
		querySB.append("        ORDER  BY S.BILL_DT DESC) X ");
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    ROWNUM <= "
				+ DJBConstants.KNO_AUDIT_DETAILS_ROW_COUNT);

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get get Last Active Meter Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see MeterReplacementDAO#getLastActiveMeterDetails
	 * @return query String
	 */
	public static String getLastActiveMeterDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT TO_CHAR(MR.READ_DTTM,'DD/MM/YYYY')READ_DTTM, READER_REM_CD, REG_READING ");
		querySB.append(" FROM CI_MR MR ");
		querySB
				.append(" INNER JOIN CI_SP_MTR_EVT MTREVT ON MTREVT.MR_ID = MR.MR_ID ");
		querySB
				.append(" INNER JOIN CI_SP_MTR_HIST SPHIST ON SPHIST.SP_MTR_HIST_ID = MTREVT.SP_MTR_HIST_ID ");
		querySB
				.append(" INNER JOIN CI_SA_SP SASP ON SASP.SP_ID = SPHIST.SP_ID ");
		querySB.append(" INNER JOIN CI_SA SA ON SA.SA_ID = SASP.SA_ID ");
		querySB.append(" INNER JOIN CI_MR_REM MREM ON MREM.MR_ID = MR.MR_ID ");
		querySB
				.append(" INNER JOIN CI_REG_READ REGREAD ON REGREAD.MR_ID = MR.MR_ID ");
		querySB.append(" WHERE SA.SA_STATUS_FLG = '20'");
		querySB.append(" AND MTREVT.SP_MTR_EVT_FLG = 'I' ");
		querySB.append(" AND SA.SA_TYPE_CD IN ('SAWATR','SAWATSEW','UNMTRD')");
		querySB.append(" AND SA.ACCT_ID = ?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get LDAP User Role List for User Role Drop down.
	 * </p>
	 * 
	 * @return
	 */
	public static String getLDAPUserRoleListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.APP_NAME, T.USER_ROLE,T.USER_ROLE_DESC FROM  DJB_LDAP_USERS_ROLE T ORDER BY T.APP_NAME, T.USER_ROLE_DESC ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to retrieve count Of MrKey for a specific search for an user Query.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 17-12-2013
	 * @return query String
	 */
	public static String getListOfMrKeyQuery(String userId, String mrKey) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select COUNT(1) MRKEYCOUNT from ci_dar_usr t where t.user_id = '"
						+ userId + "' AND dar_cd IN (" + mrKey + ")");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to get Logged in user Zro Code
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public static String getLoggedInUserZroCdQuery(String userId) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT REGEXP_SUBSTR((SELECT D.USER_ZRO_CD ");
		querySB.append("FROM   DJB_DATA_ENTRY_USERS D ");
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    D.USER_ID = ? ");
		querySB
				.append("AND    D.USER_ZRO_CD IS NOT NULL), '[^,]+', 1, LEVEL) USER_ZRO_CD ");
		querySB.append("                 FROM   DUAL ");
		querySB
				.append("                 CONNECT BY REGEXP_SUBSTR((SELECT D.USER_ZRO_CD ");
		querySB.append("FROM   DJB_DATA_ENTRY_USERS D ");
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    D.USER_ID = ? ");
		querySB
				.append("AND    D.USER_ZRO_CD IS NOT NULL), '[^,]+', 1, LEVEL) IS NOT NULL");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Lot No.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 28-01-2016
	 * @return query String
	 */
	public static String getLotNo() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT CM_LOT_NO.NEXTVAL AS LOT_NO FROM DUAL ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the list of colonies for Dev Charge Master Screen, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @param colonyName
	 * @return
	 */
	public static String getMasterDataByColonyListQuery(String colonyName) {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.CHARGE_TYPE_FLG AS COL_CHRG_TYP, A.NOTIFY_DT AS COL_NOTIFY_DT, A.CATEGORY_CD AS COL_CAT_CD, ");
		querySB
				.append(" B.RATE_PER_SQMT AS RATE_PER_SQMT, B.REBATE_ELIGIBLE_FLG AS REBATE_ELIGIBLE_FLG, B.EFF_START_DT AS RATE_STRT_DT, B.EFF_END_DT AS RATE_END_DT, B.DOC_PROOF AS RATE_DOC_PROOF, ");
		querySB
				.append(" C.INTEREST_PERCENTAGE AS INTRST_PRCNTG, C.EFF_START_DT AS INTRST_STRT_DT, C.EFF_END_DT AS INTRST_END_DT, C.DOC_PROOF AS INTRST_DOC_PROOF, ");
		querySB
				.append(" D.REBATE_TYPE_FLG AS REBATE_TYP, D.REBATE_PERCENTAGE AS REBATE_PRCNTG, D.REBATE_FLAT_RATE_PER_SQMT AS REBATE_FLAT_RATE, D.EFF_START_DT AS REBATE_STRT_DT, ");
		querySB
				.append(" D.EFF_END_DT AS REBATE_END_DT, D.DOC_PROOF AS REBATE_DOC_PROOF ");
		querySB
				.append(" FROM DJB_DEV_CHG_COLONY A, DJB_DEV_CHG_RATE B, DJB_DEV_CHG_INTEREST C, DJB_DEV_CHG_REBATE D ");
		querySB
				.append(" WHERE A.COLONY_NAME LIKE '%"
						+ colonyName
						+ "%' AND A.COLONY_ID=B.COLONY_ID AND B.COLONY_ID=C.COLONY_ID AND C.COLONY_ID=D.COLONY_ID ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Colony Detail for Dev Charge Master Screen, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @param colonyId
	 * @return
	 */
	public static String getMasterDataColonyDetailsQuery(String colonyId) {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.COLONY_NAME AS COLONY_NAME, A.CHARGE_TYPE_FLG AS COL_CHRG_TYP, ");
		querySB
				.append(" A.NOTIFY_DT AS COL_NOTIFY_DT, A.CATEGORY_CD AS COL_CAT_CD, A.STATUS_FLG AS STATUS_FLG FROM DJB_DEV_CHG_COLONY A ");
		querySB.append(" WHERE COLONY_ID IN '" + colonyId + "'  ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Interest Detail for Dev Charge Master Screen, JTrac
	 * DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getMasterDataInterestDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT C.INTEREST_PERCENTAGE AS INTRST_PRCNTG, C.EFF_START_DT AS INTRST_STRT_DT, ");
		querySB
				.append(" C.EFF_END_DT AS INTRST_END_DT, C.DOC_PROOF AS INTRST_DOC_PROOF ");
		querySB
				.append(" FROM DJB_DEV_CHG_INTEREST C  WHERE C.COLONY_ID = ?  ORDER BY INTRST_STRT_DT ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Rate Detail for Dev Charge Master Screen, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getMasterDataRateDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT B.RATE_TYPE_CD AS RATE_TYPE_CD, B.INTEREST_ELIGIBLE_FLG AS INTEREST_ELIGIBLE_FLG, B.CUST_CL_CD AS CUST_CL_CD, B.MIN_PLOT_AREA AS MIN_PLOT_AREA, B.MAX_PLOT_AREA AS MAX_PLOT_AREA, B.RATE_PER_SQMT AS RATE_PER_SQMT, ");
		querySB
				.append(" B.REBATE_ELIGIBLE_FLG AS REBATE_ELIGIBLE_FLG, B.EFF_START_DT AS RATE_STRT_DT, B.EFF_END_DT AS RATE_END_DT,  ");
		querySB
				.append(" B.DOC_PROOF AS RATE_DOC_PROOF FROM DJB_DEV_CHG_RATE B  WHERE B.COLONY_ID = ? ORDER BY RATE_STRT_DT ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Rebate Detail for Dev Charge Master Screen, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getMasterDataRebateDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT D.REBATE_TYPE_FLG AS REBATE_TYP, D.REBATE_PERCENTAGE AS REBATE_PRCNTG,  ");
		querySB
				.append(" D.REBATE_FLAT_RATE_PER_SQMT AS REBATE_FLAT_RATE, D.EFF_START_DT AS REBATE_STRT_DT,  ");
		querySB
				.append(" D.EFF_END_DT AS REBATE_END_DT, D.DOC_PROOF AS REBATE_DOC_PROOF ");
		querySB
				.append(" FROM DJB_DEV_CHG_REBATE D  WHERE D.COLONY_ID = ?   ORDER BY REBATE_STRT_DT ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Meter Model corresponding to manufacturer code.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see MeterReplacementDAO#getMeterModel
	 * @return query String
	 */
	public static String getMeterModelQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT  TRIM(T1.MODEL_CD) MODEL_CD, T3.DESCR ");
		querySB.append(" FROM CI_MODEL T1, CI_MFG T2, CI_MODEL_L T3 ");
		querySB
				.append(" WHERE T2.MFG_CD = T1.MFG_CD AND T3.MODEL_CD = T1.MODEL_CD ");
		querySB.append(" AND TRIM(T1.MFG_CD) =? ");
		querySB.append(" ORDER BY DESCR ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Designation list to populate Designation Drop down for
	 * Meter Reader.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-03-2013
	 * @see GetterDAO#getMeterReaderDesignationListMap
	 * @return query String
	 */
	public static String getMeterReaderDesignationListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select t.DESIG_CD,t.DESIG_DESC from DJB_MTR_RDR_DESIG t order by t.DESIG_DESC asc ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Meter Reader Details History.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-09-2013
	 * @param meterReaderDetails
	 * @return Query String
	 */
	public static String getMeterReaderDetailsHistoryListQuery(
			MeterReaderDetails meterReaderDetails) {
		StringBuffer querySB = new StringBuffer();
		// querySB
		// .append(" select t.subzone_cd,t.mtr_rdr_cd,t.mtr_rdr_name,t.emp_id,t.mobile,t.email,t.mtr_rdr_typ, t.created_by,to_char(t.created_date,'dd/mm/yyyy hh:mi:ss AM')created_date,t.updated_by,to_char(t.updated_date,'dd/mm/yyyy hh:mi:ss AM')updated_date from djb_mtr_rdr_hist t ");
		// querySB.append(" where 1=1 ");
		// if (null != meterReaderDetails.getMeterReaderZone()
		// && !"".equals(meterReaderDetails.getMeterReaderZone().trim())) {
		// querySB.append(" and t.subzone_cd=? ");
		// }
		// if (null != meterReaderDetails.getMeterReaderID()
		// && !"".equals(meterReaderDetails.getMeterReaderID().trim())) {
		// querySB.append(" and t.mtr_rdr_cd=? ");
		// }
		// querySB.append(" order by t.updated_date desc ");
		querySB
				.append(" select t.subzone_cd,t.mtr_rdr_cd,t.mtr_rdr_name,t.emp_id,t.mobile,t.email,t.mtr_rdr_typ,t.created_by,t.supervisor_cd, t.hhd_id, t.is_active,t.active_inactive_reason,t.zro_cd, ");
		querySB
				.append(" to_char(t.eff_from_date,'dd/mm/yyyy hh:mi am')eff_from_date, to_char(t.eff_to_date,'dd/mm/yyyy hh:mi am')eff_to_date,t.created_by, to_char(t.created_date,'dd/mm/yyyy hh:mi am')created_date,t.updated_by, to_char(t.updated_date,'dd/mm/yyyy hh:mi am')updated_date from djb_mtr_rdr_hist t ");
		querySB.append(" where 1=1 ");
		if (null != meterReaderDetails.getMeterReaderZroCd()
				&& !"".equals(meterReaderDetails.getMeterReaderZroCd().trim())) {
			querySB.append(" and t.zro_cd=? ");
		}
		if (null != meterReaderDetails.getMeterReaderID()
				&& !"".equals(meterReaderDetails.getMeterReaderID().trim())) {
			querySB.append(" and t.mtr_rdr_cd=? ");
		}
		if (null != meterReaderDetails.getMeterReaderZone()
				&& !"".equals(meterReaderDetails.getMeterReaderZone().trim())) {
			querySB.append(" and t.subzone_cd=? ");
		}
		if (null != meterReaderDetails.getMeterReaderName()
				&& !"".equals(meterReaderDetails.getMeterReaderName().trim())) {
			querySB.append(" and t.mtr_rdr_name=? ");
		}
		if (null != meterReaderDetails.getMeterReaderEmployeeID()
				&& !"".equals(meterReaderDetails.getMeterReaderEmployeeID()
						.trim())) {
			querySB.append(" and t.emp_id=? ");
		}
		if (null != meterReaderDetails.getMeterReaderMobileNo()
				&& !"".equals(meterReaderDetails.getMeterReaderMobileNo()
						.trim())) {
			querySB.append(" and t.mobile=? ");
		}
		if (null != meterReaderDetails.getUserId()
				&& !"".equals(meterReaderDetails.getUserId().trim())) {
			querySB.append(" and t.created_by=? ");
		}
		if (null != meterReaderDetails.getMeterReaderStatus()
				&& !"".equals(meterReaderDetails.getMeterReaderStatus().trim())) {
			querySB.append(" and t.is_active=? ");
		}

		querySB.append(" order by t.mtr_rdr_name asc,t.updated_date desc ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Meter Reader Details.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 23-09-2013
	 * @param meterReaderDetails
	 * @return Query String
	 */
	public static String getMeterReaderDetailsListQuery(
			MeterReaderDetails meterReaderDetails) {
		StringBuffer querySB = new StringBuffer();
		// querySB
		// .append(" select t.subzone_cd,t.mtr_rdr_cd,t.mtr_rdr_name,t.emp_id,t.mobile,t.email,t.mtr_rdr_typ,t.created_by from djb_mtr_rdr t ");

		querySB
				.append(" select t.subzone_cd,t.mtr_rdr_cd,t.mtr_rdr_name,t.emp_id,t.mobile,t.email,t.mtr_rdr_typ,t.created_by,to_char(t.created_date,'dd/mm/yyyy hh:mi am')created_date,t.supervisor_cd, t.hhd_id, t.is_active,t.active_inactive_reason,t.zro_cd, ");
		querySB
				.append(" to_char(t.eff_from_date,'dd/mm/yyyy hh:mi am')eff_from_date,to_char(t.eff_to_date,'dd/mm/yyyy hh:mi am') eff_to_date from djb_mtr_rdr t ");
		querySB.append(" where 1=1 ");
		if (null != meterReaderDetails.getMeterReaderZroCd()
				&& !"".equals(meterReaderDetails.getMeterReaderZroCd().trim())) {
			querySB.append(" and t.zro_cd=? ");
		}

		if (null != meterReaderDetails.getMeterReaderZone()
				&& !"".equals(meterReaderDetails.getMeterReaderZone().trim())) {
			querySB.append(" and t.subzone_cd=? ");
		}
		if (null != meterReaderDetails.getMeterReaderName()
				&& !"".equals(meterReaderDetails.getMeterReaderName().trim())) {
			querySB.append(" and t.mtr_rdr_name=? ");
		}
		if (null != meterReaderDetails.getMeterReaderEmployeeID()
				&& !"".equals(meterReaderDetails.getMeterReaderEmployeeID()
						.trim())) {
			querySB.append(" and t.emp_id=? ");
		}
		if (null != meterReaderDetails.getMeterReaderMobileNo()
				&& !"".equals(meterReaderDetails.getMeterReaderMobileNo()
						.trim())) {
			querySB.append(" and t.mobile=? ");
		}
		if (null != meterReaderDetails.getUserId()
				&& !"".equals(meterReaderDetails.getUserId().trim())) {
			querySB.append(" and t.created_by=? ");
		}
		if (null != meterReaderDetails.getMeterReaderStatus()
				&& !"".equals(meterReaderDetails.getMeterReaderStatus().trim())) {
			querySB.append(" and t.is_active=? ");
		}
		// START: Added by Rajib as per Jtrac DJB-3871 on 14-OCT-2015
		if (null != meterReaderDetails.getHhdId()
				&& !"".equals(meterReaderDetails.getHhdId().trim())) {
			querySB.append(" and t.hhd_id=? ");
		}
		// END: Added by Rajib as per JTrac DJB-3871 on 14-OCT-2015
		querySB.append(" order by t.mtr_rdr_name asc ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get all the Meter reader corresponding to zone
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman(Tata Consultancy Services) Added
	 *          parameter zroLoc and mrKeys and Modified Query
	 */
	public static String getMeterReaderListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT  T.MTR_RDR_CD, T.MTR_RDR_NAME, T.SUBZONE_CD,T.EMP_ID ");
		querySB.append(" FROM DJB_MTR_RDR T ");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND T.SUBZONE_CD=? ");
		querySB.append(" ORDER BY T.MTR_RDR_NAME ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get all the Meter reader corresponding to zone
	 * </p>
	 * 
	 * @return
	 * @history 30-09-2015 Matiur Rahman(Tata Consultancy Services) Added
	 *          parameter zroLoc and mrKeys and Modified Query
	 */
	public static String getMeterReaderListQuery(
			MeterReaderDetails meterReaderDetails) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT  T.MTR_RDR_CD, T.MTR_RDR_NAME, T.SUBZONE_CD,T.EMP_ID ");
		querySB.append(" FROM DJB_MTR_RDR T ");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND T.IS_ACTIVE= '" + DJBConstants.FLAG_Y + "'");
		/* Start: Commented by Matiur Rahman on 30-09-2015 */
		// querySB.append(" AND T.SUBZONE_CD=? ");
		/* End: Commented by Matiur Rahman on 30-09-2015 */
		/* Start: Added by Matiur Rahman on 30-09-2015 */
		if (null != meterReaderDetails.getMeterReaderZroCd()
				&& !"".equals(meterReaderDetails.getMeterReaderZroCd())) {
			querySB.append(" AND T.ZRO_CD=? ");
		}
		if (null != meterReaderDetails.getMeterReaderEmployeeID()
				&& !"".equals(meterReaderDetails.getMeterReaderEmployeeID())) {
			querySB.append(" AND T.EMP_ID=? ");
		}
		if (null != meterReaderDetails.getMeterReaderName()
				&& !"".equals(meterReaderDetails.getMeterReaderName())) {
			querySB.append(" AND T.MTR_RDR_NAME=? ");
		}
		/* End: Added by Matiur Rahman on 30-09-2015 */
		querySB.append(" ORDER BY T.MTR_RDR_NAME ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get meter reader name along with emp id for meter reader Img
	 * Audit screen
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 17-08-2017
	 * @see
	 * @return query String
	 */
	public static String getMeterReaderNameForAuditQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT distinct NVL(A.MTR_RDR_NAME, 'NA') || '(' || ");
		querySB
				.append("                   NVL(A.MTR_RDR_EMPID, 'NA') || ')' as MTR_RDR_NAME_ID ");
		querySB.append("     FROM DJB_AUDIT_STG A ");
		querySB.append("    WHERE A.MTR_RDR_EMPID IS NOT NULL ");
		querySB.append("      and A.MTR_RDR_NAME IS NOT NULL");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Designation list to populate Designation Drop down for
	 * Meter Reader.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-03-2013
	 * @see GetterDAO#getMeterReaderDesignationListMap
	 * @return query String
	 */
	public static String getMeterReaderStatusListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.STATUS_CD, T.STATUS_DESC FROM DJB_MTR_RDR_STATUS T order by T.STATUS_DESC ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get Meter Replacement Detail List Query.
	 * </p>
	 * 
	 * @param searchFor
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKeyList
	 * @param kno
	 * @param recordPerPage
	 * @param pageNo
	 * @return
	 */
	public static String getMeterReplacementDetailListQuery(String searchFor,
			String zone, String mrNo, String area, String mrKeyList,
			String kno, String recordPerPage, String pageNo, String vendorName) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		querySB.append(getMeterReplacementDetailMainQuery(searchFor, zone,
				mrNo, area, mrKeyList, kno, vendorName));
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get Meter Replacement Detail Main Query.
	 * </p>
	 * 
	 * @param searchFor
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKeyList
	 * @param kno
	 * @return
	 */
	public static String getMeterReplacementDetailMainQuery(String searchFor,
			String zone, String mrNo, String area, String mrKeyList,
			String kno, String vendorName) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT T.MRKEY,");
		querySB.append(" T.SVC_CYC_RTE_SEQ, ");
		querySB.append(" T.ACCT_ID, ");
		querySB.append(" Z.SUBZONE_CD, ");
		querySB.append(" T.CONSUMER_NAME, ");
		querySB.append(" T.WCNO, ");
		querySB.append(" T.SA_TYPE_CD, ");
		querySB.append(" T.BILL_ROUND_ID, ");
		querySB.append(" M.MTR_RPLC_STAGE_ID ,");
		querySB.append("to_char(T.BILL_GEN_DATE,'dd/mm/yyyy') BILL_GEN_DATE ,");
		// querySB.append(" T.BILL_GEN_DATE, ");
		querySB.append(" T.READER_REM_CD, ");
		querySB.append(" T.REG_READING, ");
		querySB.append(" T.NEW_AVG_READ, ");
		querySB.append(" T.NEW_NO_OF_FLOORS, ");
		querySB.append(" T.CONS_PRE_BILL_STAT_ID, ");
		querySB.append(" M.ERROR_MSG, ");
		querySB.append(" M.ERROR_STATUS ");
		querySB.append(" FROM  ");
		querySB.append(" CM_CONS_PRE_BILL_PROC  T, ");
		querySB.append(" DJB_ZN_MR_AR_MRD       Z, ");
		querySB.append(" CM_MRD_PROCESSING_STAT P, ");
		querySB.append(" CM_MTR_RPLC_STAGE M ");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND T.MRKEY = Z.MRD_CD ");
		querySB.append(" AND T.MRKEY = P.MRKEY ");
		querySB.append(" AND T.BILL_ROUND_ID = P.BILL_ROUND_ID ");
		querySB.append(" AND Z.MRD_CD = P.MRKEY ");
		querySB.append(" AND P.MRD_STATS_ID = 10 ");
		querySB.append(" AND T.ACCT_ID = M.ACCT_ID");
		querySB.append(" AND T.BILL_ROUND_ID = M.BILL_ROUND_ID ");
		// Chnage by Rajib as per Jtrac DJB-2024 to aapend condition for
		// vendorName
		// System.out.println("inside mainquery:"+vendorName);
		if (null != vendorName && !"".equalsIgnoreCase(vendorName)) {
			querySB
					.append(" AND M.TP_VENDOR_NAME = " + "'" + vendorName
							+ "' ");
			// System.out.println("Inside If");
		} else {
			querySB.append(" AND M.TP_VENDOR_NAME  IS NULL ");
		}
		// Change End by Rajib as per Jtrac DJb-2024 on 03-01-2014
		if (null != searchFor && "Frozen".equalsIgnoreCase(searchFor)) {
			querySB.append(" AND T.CONS_PRE_BILL_STAT_ID in (64,65) ");
		} else {
			querySB.append(" AND T.CONS_PRE_BILL_STAT_ID = 30 ");
		}
		if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
			querySB.append(" AND Z.SUBZONE_CD = ? ");
		}
		if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
			querySB.append(" AND Z.MR_CD = ? ");
		}
		if (null != area && !"".equalsIgnoreCase(area.trim())) {
			querySB.append(" AND Z.AREA_CD = ? ");
		}
		if (null != mrKeyList && !"".equalsIgnoreCase(mrKeyList.trim())) {
			querySB.append(" AND T.MRKEY IN ( " + mrKeyList + " )");
		}
		if (null != kno && !"".equalsIgnoreCase(kno.trim())) {
			querySB.append(" AND T.ACCT_ID = ? ");
		}
		querySB
				.append(" ORDER BY TO_NUMBER(T.MRKEY), TO_NUMBER(T.SVC_CYC_RTE_SEQ) ");
		return querySB.toString();
	}

	/**
	 * <P>
	 * Query to get Meter Replacement Details for Meter Replacement Details
	 * Entry POP UP Screen.
	 * </P>
	 * 
	 * @return query String
	 * @see GetterDAO#getMeterReplacementDetails
	 */
	public static String getMeterReplacementDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT CM_CONS_PRE_BILL_PROC.ACCT_ID, CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID, ");
		querySB
				.append(" DJB_SUBZONE.SUBZONE_CD ZONECD,DJB_SUBZONE.SUBZONE_NAME, ");
		querySB.append(" DJB_ZN_MR_AR_MRD.MR_CD MRNO, ");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_CD AREANO, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.WCNO, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CONSUMER_NAME CONSUMER_NAME, ");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.SA_TYPE_CD OLD_SA_TYPE_CD,CM_CONS_PRE_BILL_PROC.CUST_CL_CD, ");
		querySB
				.append(" TO_CHAR(CM_CONS_PRE_BILL_PROC.BILL_GEN_DATE, 'DD/MM/YYYY') CURR_MTR_RD_DATE, ");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.READER_REM_CD CURR_READER_REM_CD, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REG_READING CURR_REG_READING, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.AVG_READ, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_AVG_READ, ");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_NO_OF_FLOORS, ");
		querySB
				.append(" TO_CHAR(CM_MTR_RPLC_STAGE.MTR_INSTALL_DATE, 'DD/MM/YYYY') MTR_INSTALL_DATE, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MTRTYP, ");
		querySB.append(" CM_MTR_RPLC_STAGE.SA_TYPE_CD, ");
		querySB.append(" CM_MTR_RPLC_STAGE.READER_REM_CD OLD_READER_REM_CD, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MTR_READER_ID, ");
		querySB.append(" CM_MTR_RPLC_STAGE.BADGE_NBR, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MFG_CD, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MODEL_CD, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MTR_START_READ, ");
		querySB.append(" CM_MTR_RPLC_STAGE.OLD_MTR_READ,");
		querySB.append(" CM_MTR_RPLC_STAGE.OLDAVGCONS,");
		querySB.append(" CM_MTR_RPLC_STAGE.WCONSIZE, ");
		querySB.append(" CM_MTR_RPLC_STAGE.WCONUSE, ");
		querySB.append(" CM_MTR_RPLC_STAGE.IS_LNT_SRVC_PRVDR, ");
		querySB.append(" CM_MTR_RPLC_STAGE.MTR_RPLC_STAGE_ID ");
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC,DJB_ZN_MR_AR_MRD,DJB_SUBZONE,CM_MTR_RPLC_STAGE ");
		querySB.append(" WHERE 1=1 ");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD ");
		querySB
				.append(" AND TRIM(DJB_ZN_MR_AR_MRD.SUBZONE_CD) = TRIM(DJB_SUBZONE.SUBZONE_CD) ");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID = CM_MTR_RPLC_STAGE.ACCT_ID(+) ");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = CM_MTR_RPLC_STAGE.BILL_ROUND_ID(+) ");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID = ? ");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve file's lot status.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 01-02-2016
	 * @param selectedFileNo
	 * @return query String
	 */
	public static String getMissingLotCountQuery(String selectedFileNo) {
		String selectedFNOArray[] = selectedFileNo.split(",");
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select count(1) as existingLot from CM_NEW_CONN_FILE_ENTRY_STG ");
		if (null != selectedFileNo && !"".equalsIgnoreCase(selectedFileNo)) {
			querySB.append("   WHERE    file_no in ( ");
			for (int i = 0; i < selectedFNOArray.length; i++) {
				String tempString = selectedFNOArray[i];
				if (null != tempString && !"".equalsIgnoreCase(tempString)) {
					querySB.append("   '" + tempString + "', ");
				}
			}
			querySB.append("   '') ");
			querySB.append("  and LOT_NO is null ");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to retrieve mrkey from selected ZONE,MR and AREA.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 03-JUNE-2016
	 * @return query String
	 * @as per Jtrac DJB-4464 and Open project id - 1203
	 */
	public static String getMrdCode() {

		StringBuffer queryMRD = new StringBuffer();
		queryMRD.append("select mrd_cd MRKEY ");
		queryMRD.append("  from djb_zn_mr_ar_mrd m ");
		queryMRD.append(" where 1=1 and m.subzone_cd = ? ");
		queryMRD.append("   and m.mr_cd = ? ");
		queryMRD.append("   and m.area_cd = ?");

		return queryMRD.toString();

	}

	/**
	 * <p>
	 * Query to retrieve MRD Type Query.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-05-2013
	 * @see GetterDAO#getMRDType
	 * @return query String
	 */
	public static String getMRDTypeMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T.OLD_MRD_TYP MRD_TYPE FROM DJB_ZN_MR_AR_MRD T ORDER BY T.OLD_MRD_TYP ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve MREKEY by Zone MR Area.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 08-05-2013
	 * @see GetterDAO#getMRKEYByZoneMRArea
	 * @return query String
	 */
	public static String getMRKEYByZoneMRAreaQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.MRD_CD,T.SUBZONE_CD,T.MR_CD,T.AREA_CD,T.AREA_DESC FROM DJB_ZN_MR_AR_MRD T ");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND T.SUBZONE_CD=? ");
		querySB.append(" AND T.MR_CD=? ");
		querySB.append(" AND T.AREA_CD=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve mrkey count for the specified ZRO location as per JTrac
	 * DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 20-02-2016
	 * @see {@link #KnoGenerationDAO #getMrkeyCount}
	 * @return query String
	 */
	public static String getMrkeyCountQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT COUNT(*) AS CNT FROM DJB_MRD D WHERE D.MRD_CD=? AND D.ZRO_CD=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve MRKEY list to populate MRKEY Drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 08-05-2013
	 * @see GetterDAO#getMRKEYListMap
	 * @return query String
	 */
	public static String getMRKEYListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T.MRD_CD FROM DJB_ZN_MR_AR_MRD T ORDER BY TO_NUMBER(T.MRD_CD) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve MR No list to populate MR Drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-03-2013
	 * @see GetterDAO#getMRNoListMap
	 * @return query String
	 */
	public static String getMRNoListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T1.MR_CD  FROM DJB_ZN_MR_AR_MRD T1 WHERE T1.SUBZONE_CD=? ORDER BY TO_NUMBER(MR_CD) ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get Next Header ID
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getNextHeaderIdQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("  SELECT NVL(MAX(HEADER_ID),0)+1 AS NXTID FROM CM_AMR_FILE_DETAIL ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get No Of Accounts ToBe Updated for Update operation in
	 * Operation MRKey Review Screen in the database.
	 * </p>
	 * 
	 * @param mrKeyList
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param billRound
	 * @return query String
	 */
	public static String getNoOfAccountsToBeUpdatedQuery(String mrKeyList,
			String mrKeyStatusCode, String processCounter) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_ACCOUNT ");
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC A, CM_MRD_PROCESSING_STAT B ");
		querySB
				.append(" WHERE A.MRKEY = B.MRKEY AND A.BILL_ROUND_ID = B.BILL_ROUND_ID ");
		querySB.append(" AND B.MRD_STATS_ID = " + OPEN_BILL_ROUND_STATUS_CODE);
		// querySB.append(" AND A.BILL_ROUND_ID = '" + billRound + "' ");
		querySB.append(" AND A.PROCESS_COUNTER = '" + processCounter + "' ");
		/* Start:Added by Matiur Rahman on 26-07-2013 as per JTrac ID#DJB-1733 */
		querySB.append(" AND A.IS_LOCKED <> 'Y'");
		/* Endt:Added by Matiur Rahman on 26-07-2013 as per JTrac ID#DJB-1733 */
		querySB.append(" AND A.CONS_PRE_BILL_STAT_ID = '" + mrKeyStatusCode
				+ "'");
		querySB.append(" AND A.MRKEY in (" + mrKeyList + ")");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Old Meter Remark Code List.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see MeterReplacementDAO#getOldMeterRemarkCodeList
	 * @return query String
	 */
	public static String getOldMeterRemarkCodeListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT CM_MTR_STATS_LOOKUP.MTR_READ_TYPE_CD,TRIM(CM_MTR_STATS_LOOKUP.MTR_STATS_CD) MTR_STATS_CD");
		querySB.append(" FROM CM_MTR_STATS_LOOKUP ");
		querySB.append(" WHERE CM_MTR_STATS_LOOKUP.MTR_READ_TYPE_CD = "
				+ REGULAR_READ_TYPE_CODE);
		querySB.append(" OR CM_MTR_STATS_LOOKUP.MTR_STATS_CD = 'DFMT' ");
		querySB.append(" ORDER BY CM_MTR_STATS_LOOKUP.MTR_STATS_CD ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Old SA Type Details pulled from CI_SA table.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see MeterReplacementDAO#getOldSATypeDetails
	 * @return query String
	 */
	public static String getOldSATypeDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT CI_SA.SA_TYPE_CD,to_char(CI_SA.START_DT,'dd/mm/yyyy')START_DT FROM CI_SA ");
		querySB
				.append(" WHERE CI_SA.SA_STATUS_FLG=20 AND TRIM(CI_SA.SA_TYPE_CD) IN('SAWATR','SAWATSEW','UNMTRD')");
		querySB.append(" AND CI_SA.ACCT_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * get Open Bill Round For An MRKey Query.
	 * </p>
	 * 
	 * @return
	 */
	public static String getOpenBillRoundForAnMRKeyQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.BILL_ROUND_ID FROM CM_MRD_PROCESSING_STAT T ");
		querySB.append("  WHERE T.MRD_STATS_ID = "
				+ OPEN_BILL_ROUND_STATUS_CODE);
		querySB.append(" AND T.MRKEY=? ");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get open bill round of MRD
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getOpenBillRoundOfMRDQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT S.BILL_ROUND_ID BILLROUND,S.MRKEY MRKEY ");
		querySB.append("       FROM CM_MRD_PROCESSING_STAT S ");
		querySB.append("      WHERE S.MRD_STATS_ID = 10 ");
		querySB.append("        AND S.MRKEY = (SELECT D.MRD_CD ");
		querySB
				.append("                         FROM CM_AMR_FILE_DETAIL A, DJB_ZN_MR_AR_MRD D ");
		querySB.append("                        WHERE A.HEADER_ID = ? ");
		querySB.append("                          AND A.ZONE = D.SUBZONE_CD ");
		querySB.append("                          AND A.MR = D.MR_CD ");
		querySB.append("                          AND A.AREA_CD = D.AREA_CD)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Open Bill Round for a particular MR Key identified by Zone,
	 * MR No and Area Code which is in open status that is 10.
	 * </p>
	 * 
	 * @return Query String.
	 */
	public static String getOpenBillRoundQuery(String status) {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT DISTINCT CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID ");
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC, DJB_ZN_MR_AR_MRD, CM_MRD_PROCESSING_STAT ");
		querySB
				.append(" WHERE CM_CONS_PRE_BILL_PROC.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD ");
		querySB
				.append(" AND CM_MRD_PROCESSING_STAT.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD ");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = CM_MRD_PROCESSING_STAT.MRKEY ");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = CM_MRD_PROCESSING_STAT.BILL_ROUND_ID ");
		querySB.append(" AND CM_MRD_PROCESSING_STAT.MRD_STATS_ID = "
				+ OPEN_BILL_ROUND_STATUS_CODE);
		if ("OPEN".equalsIgnoreCase(status)) {
			querySB
					.append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID NOT IN (70, 130) ");
		}
		querySB.append(" AND DJB_ZN_MR_AR_MRD.SUBZONE_CD = ? ");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.MR_CD = ? ");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.AREA_CD = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Operation MR Key Review Details List Main Query Added as per JTrac
	 * ID#DJB-1733
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 26-07-2013
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param mrKeyListforSearch
	 * @param fromDate
	 * @param toDate
	 * @param orderBy
	 * @param billRound
	 * @return get Operation MR Key Review Details List Query String
	 */
	public static String getOperationMRKeyReviewDetailsListMainQuery(
			String mrKeyStatusCode, String processCounter,
			String mrKeyListforSearch, String fromDate, String toDate,
			String orderBy, String billRound) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT /*+result_cache*/ T.BILL_ROUND_ID,T.MRKEY,T.CONS_PRE_BILL_STAT_ID,T.PROCESS_COUNTER,T.MRD_STATS_ID,");
		if ("Frozen Date".equalsIgnoreCase(orderBy)
				|| "Update Date".equalsIgnoreCase(orderBy)
				|| "Entry Date".equalsIgnoreCase(orderBy)
				|| "Park Date".equalsIgnoreCase(orderBy)
				|| "Bill Gen Date".equalsIgnoreCase(orderBy)) {
			querySB.append("  TO_CHAR(T.ORDER_BY, 'DD/MM/YYYY') ORDER_BY, ");
		}
		querySB.append(" T.TOTAL_ACCOUNT  FROM ( ");
		querySB
				.append(" SELECT /*+result_cache*/B.BILL_ROUND_ID,A.MRKEY,A.CONS_PRE_BILL_STAT_ID,A.PROCESS_COUNTER,B.MRD_STATS_ID, ");
		if ("Frozen Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" MIN(A.DATA_FROZEN_DTTM) ORDER_BY, ");
		}
		if ("Update Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" MIN(A.LAST_UPDT_DTTM) ORDER_BY, ");
		}
		if ("Entry Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" MIN(A.DATA_ENTRY_DTTM) ORDER_BY, ");
		}
		if ("Park Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" MIN(A.DATA_PARKED_DTTM) ORDER_BY, ");
		}
		if ("Bill Gen Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" MIN(A.BILLING_COMPLETED_ON) ORDER_BY, ");
		}
		querySB.append(" COUNT(*)TOTAL_ACCOUNT ");
		querySB
				.append(" FROM CM_CONS_PRE_BILL_PROC A, CM_MRD_PROCESSING_STAT B");
		querySB
				.append(" WHERE A.MRKEY = B.MRKEY AND A.BILL_ROUND_ID = B.BILL_ROUND_ID ");
		if (null != billRound && !"".equals(billRound.trim())) {
			querySB.append(" AND A.BILL_ROUND_ID ='" + billRound + "' ");
		} else {
			querySB.append(" AND B.MRD_STATS_ID = "
					+ OPEN_BILL_ROUND_STATUS_CODE);
		}
		querySB.append(" AND A.CONS_PRE_BILL_STAT_ID = '" + mrKeyStatusCode
				+ "'");
		querySB.append(" AND A.PROCESS_COUNTER = '" + processCounter + "'");
		if (null != mrKeyListforSearch && !"".equals(mrKeyListforSearch.trim())) {
			querySB.append(" AND A.MRKEY in (" + mrKeyListforSearch + ")");
		}
		querySB.append(" AND A.IS_LOCKED <> 'Y'");
		querySB
				.append(" GROUP BY A.MRKEY,A.CONS_PRE_BILL_STAT_ID,A.PROCESS_COUNTER,B.BILL_ROUND_ID,B.MRD_STATS_ID )T ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Operation MR Key Review Details List Query.
	 * </p>
	 * 
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param mrKeyListforSearch
	 * @param pageNo
	 * @param recordPerPage
	 * @param fromDate
	 * @param toDate
	 * @param orderBy
	 * @param orderByTo
	 * @param billRound
	 * @return get Operation MR Key Review Details List Query String
	 */
	public static String getOperationMRKeyReviewDetailsListQuery(
			String mrKeyStatusCode, String processCounter,
			String mrKeyListforSearch, String pageNo, String recordPerPage,
			String fromDate, String toDate, String orderBy, String orderByTo,
			String billRound) {
		StringBuffer querySB = new StringBuffer();
		/** Start:For Pagination part 1 */
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM SEQ_NO, T1.*  FROM (");
		/** End:For Pagination part 1 */
		/** Start:The main Query to get Records */
		querySB.append(getOperationMRKeyReviewDetailsListMainQuery(
				mrKeyStatusCode, processCounter, mrKeyListforSearch, fromDate,
				toDate, orderBy, billRound));
		/** End:The main Query to get Records */
		/* Start:Added by Matiur Rahman on 01-10-2013 as per JTrac ID#DJB-1970 */
		if ("Frozen Date".equalsIgnoreCase(orderBy)
				|| "Update Date".equalsIgnoreCase(orderBy)
				|| "Entry Date".equalsIgnoreCase(orderBy)
				|| "Park Date".equalsIgnoreCase(orderBy)
				|| "Bill Gen Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" WHERE 1=1 ");
			if (null != fromDate && !"".equals(fromDate)) {
				querySB.append(" AND TRUNC(T.ORDER_BY) >= TO_DATE('" + fromDate
						+ "','DD/MM/YYYY')");
			}
			if (null != toDate && !"".equals(toDate)) {
				querySB.append(" AND TRUNC(T.ORDER_BY) <= TO_DATE('" + toDate
						+ "','DD/MM/YYYY')");
			}
			querySB.append(" order by T.ORDER_BY");
			if ("Descending".equalsIgnoreCase(orderByTo)) {
				querySB.append(" DESC ");
			} else {
				querySB.append(" ASC ");
			}
			querySB.append(" ,BILL_ROUND_ID");
			if ("Descending".equalsIgnoreCase(orderByTo)) {
				querySB.append(" DESC ");
			} else {
				querySB.append(" ASC ");
			}
			querySB.append(" ,to_number(MRKEY) ");
			if ("Descending".equalsIgnoreCase(orderByTo)) {
				querySB.append(" DESC ");
			} else {
				querySB.append(" ASC ");
			}
		} else {
			/* End:Added by Matiur Rahman on 01-10-2013 as per JTrac ID#DJB-1970 */
			querySB.append(" order by BILL_ROUND_ID ");
			if ("Descending".equalsIgnoreCase(orderByTo)) {
				querySB.append(" DESC ");
			} else {
				querySB.append(" ASC ");
			}
		}
		/** Start:For Pagination part 2 */
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < ((" + pageNo + " * " + recordPerPage
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= (((" + pageNo + " - 1) * "
				+ recordPerPage + ") + 1)");
		/** End:For Pagination part 2 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Cons PreBill Status Lookup List.
	 * </p>
	 * 
	 * @author Tuhin Jana(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see PreBillStatusDefinitionDAO#getPreBillStatusDefinitionList
	 * @return query String
	 */
	public static String getPreBillStatusDefinitionListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT CONS_PRE_BILL_STAT_ID, DESCR ,LAST_UPDATED_BY,to_char(LAST_UPDATED_ON,'dd/mm/yyyy hh:mi am')LAST_UPDATED_ON FROM CONS_PRE_BILL_STAT_LOOKUP ORDER BY TO_NUMBER(CONS_PRE_BILL_STAT_ID)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Predefined Charactristics value from CCB DB to populate
	 * Drop down as per JTrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 13-02-2016
	 * @see GetterDAO#getPreDefChrValListMap(String)
	 * @return query String
	 * @history Reshma on 11-03-2016 edited the below method in query to sort
	 *          the value in ascending order from db, JTrac DJB-4405.
	 */
	public static String getPreDefChrValListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT CH.CHAR_VAL as VAL , L.DESCR AS VAL_DESC   ");
		querySB.append(" FROM CI_CHAR_VAL CH, CI_CHAR_VAL_L L  ");
		querySB.append(" WHERE trim(CH.CHAR_TYPE_CD) = trim(L.CHAR_TYPE_CD)  ");
		querySB.append(" AND trim(CH.CHAR_VAL) = trim(L.CHAR_VAL) ");
		querySB.append(" AND trim(CH.CHAR_TYPE_CD) = ? ");
		/**
		 * Start : Reshma on 11-03-2016 added the below part in query to sort
		 * the value in ascending order from db, JTrac DJB-4405.
		 */
		querySB.append(" ORDER BY DESCR ");
		/**
		 * End : Reshma on 11-03-2016 added the below part in query to sort the
		 * value in ascending order from db, JTrac DJB-4405.
		 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get premise id of the respective KNO.
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */

	public static String getPremiseIdQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.CHAR_PREM_ID AS PREMISE_ID  FROM CI_SA A  WHERE A.ACCT_ID =? ");
		querySB.append(" AND A.SA_STATUS_FLG = '20' ");
		querySB
				.append(" AND A.SA_TYPE_CD IN ('SAWATR  ', 'SAWATSEW', 'SASEWER ', 'UNMTRD  ') AND ROWNUM=1 ");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Previous Actual Meter Read Details For Consumer.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-04-2013
	 * @see GetterDAO#getPrevActualMeterReadDetailsForMR
	 * @return query String
	 */
	public static String getPrevActualMeterReadDetailsForMRQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT TO_CHAR(X.METER_READ.READDATE,'DD/MM/YYYY') READ_DATE,X.METER_READ.REGISTEREDREAD REG_READ,X.METER_READ.READTYPE READ_TYPE_CD,X.METER_READ.REMARK READER_REM_CD ");
		querySB
				.append(" FROM  (SELECT  CM_GET_MRPLC_ACTUAL_METER_READ(?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'))METER_READ FROM DUAL) X ");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to get Previous Normal Bill Round
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 11-01-2016
	 * @return
	 */
	public static String getPreviousBillRoundQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT X.BILL_ROUND_ID AS PREV_BILL_ROUND ");
		querySB.append("  FROM (SELECT W.BILL_ROUND_ID ");
		querySB.append("          FROM CM_BILL_WINDOW W ");
		querySB.append("         WHERE W.BILL_WIN_STAT_ID = '20' ");
		querySB.append("           AND W.BILL_WIN_FLG = 'N' ");
		/*
		 * querySB.append("           AND W.END_DATE < SYSDATE ");
		 */querySB.append("         ORDER BY W.END_DATE DESC) X ");
		querySB.append(" WHERE ROWNUM < 2");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to get Previous Normal Bill Rounds
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 18-01-2016
	 * 
	 */
	public static String getPreviousBillRoundsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT X.BILL_ROUND_ID AS PREV_BILL_ROUND ");
		querySB.append("  FROM (SELECT W.BILL_ROUND_ID ");
		querySB.append("          FROM CM_BILL_WINDOW W ");
		querySB.append("         WHERE W.BILL_WIN_STAT_ID = '20' ");
		querySB.append("           AND W.BILL_WIN_FLG = 'N' ");
		querySB.append("           AND W.END_DATE < SYSDATE ");
		querySB.append("         ORDER BY W.END_DATE DESC) X ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get Previous Meter Read Details For Consumer.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 01-04-2013
	 * @see GetterDAO#getPrevMeterReadDetailsForMR
	 * @return query String
	 */
	public static String getPrevMeterReadDetailsForMRQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT TO_CHAR(X.METER_READ.READDATE,'DD/MM/YYYY') READ_DATE,X.METER_READ.REGISTEREDREAD REG_READ,X.METER_READ.READTYPE READ_TYPE_CD,X.METER_READ.REMARK READER_REM_CD ");
		querySB
				.append(" FROM  (SELECT  CM_GET_MRPLC_PREV_METER_READ(?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'))METER_READ FROM DUAL) X ");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get details list of records for KNO Audit
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 09-03-2016
	 */
	public static String getRecordDetailsListForAuditAction(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM AS SEQ_NO, T1.*  FROM (");
		querySB
				.append(getRecordDetailsListForAuditActionMainQuery(imageAuditSearchCriteria));
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < (("
				+ imageAuditSearchCriteria.getPageNo() + " * "
				+ imageAuditSearchCriteria.getRecordPerPage() + ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= ((("
				+ imageAuditSearchCriteria.getPageNo() + " - 1) * "
				+ imageAuditSearchCriteria.getRecordPerPage() + ") + 1)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get details list of records for Audit Action
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 09-03-2016
	 */
	private static String getRecordDetailsListForAuditActionMainQuery(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT S.KNO KNO, ");
		querySB.append("       S.SUGSTD_AUDIT_ACTION SUGSTD_ACTION, ");
		querySB
				.append("       S.NON_SATSFCTRY_READNG_REASN NON_SATSFCTRY_REASN, ");
		querySB.append("       S.BILL_ROUND BILLROUND, ");
		querySB
				.append("       NVL(S.FINAL_AUDIT_STATUS, 'INCOMPLETE') FINAL_AUDIT_STATUS, ");
		querySB
				.append("		NVL(S.FINAL_AUDIT_ACTION, 'NULL') FINAL_AUDIT_ACTION ");
		querySB.append("FROM   DJB_AUDIT_STG S ");
		querySB.append("WHERE  S.NON_SATSFCTRY_READNG_REASN IS NOT NULL ");
		querySB
				.append("AND    S.BILL_ROUND = (SELECT X.BILL_ROUND_ID AS PREV_BILL_ROUND ");
		querySB
				.append("                       FROM   (SELECT W.BILL_ROUND_ID ");
		querySB
				.append("                               FROM   CM_BILL_WINDOW W ");
		querySB
				.append("                               WHERE  W.BILL_WIN_STAT_ID = '20' ");
		querySB
				.append("                               AND    W.BILL_WIN_FLG = 'N' ");
		querySB
				.append("                               AND    W.END_DATE < SYSDATE ");
		querySB
				.append("                               ORDER  BY W.END_DATE DESC) X ");
		querySB.append("                       WHERE  ROWNUM < 2) ");
		if (null != imageAuditSearchCriteria.getLoggedInUserZroCdList()
				&& !imageAuditSearchCriteria.getLoggedInUserZroCdList()
						.isEmpty()) {
			querySB.append("AND    S.ZRO_LOCATION IN ( ");
			int zroListSize = imageAuditSearchCriteria
					.getLoggedInUserZroCdList().size();
			while (zroListSize > 0) {
				querySB.append(" ? ");
				zroListSize--;
				if (zroListSize > 0) {
					querySB.append(" , ");
				}
			}
			querySB.append(" ) ");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get details list of records for Consumption Audit
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	public static String getRecordDetailsListForConsumptionAudit(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM AS SEQ_NO, T1.*  FROM (");
		querySB
				.append(getConsumptionVariationRecordDetailsListMainQuery(consumptionAuditSearchCriteria));
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < (("
				+ consumptionAuditSearchCriteria.getPageNo() + " * "
				+ consumptionAuditSearchCriteria.getRecordPerPage()
				+ ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= ((("
				+ consumptionAuditSearchCriteria.getPageNo() + " - 1) * "
				+ consumptionAuditSearchCriteria.getRecordPerPage() + ") + 1)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to get Record Details For Image Audit
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 11-01-2016
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static String getRecordDetailsListForImgAudit(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT T2.* ");
		querySB.append(" FROM (SELECT ROWNUM AS SEQ_NO, T1.*  FROM (");
		querySB
				.append(getRecordDetailsListForImgAuditMainQuery(imageAuditSearchCriteria));
		querySB.append(" ) T1 ");
		querySB.append(" WHERE ROWNUM < (("
				+ imageAuditSearchCriteria.getPageNo() + " * "
				+ imageAuditSearchCriteria.getRecordPerPage() + ") + 1)) T2 ");
		querySB.append(" WHERE SEQ_NO >= ((("
				+ imageAuditSearchCriteria.getPageNo() + " - 1) * "
				+ imageAuditSearchCriteria.getRecordPerPage() + ") + 1)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method returns the query of Record Details List For Image Audit
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 11-01-2016
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static String getRecordDetailsListForImgAuditMainQuery(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT A.ZONE AS ZN, ");
		querySB.append("       A.MR AS MR_NO, ");
		querySB.append("       A.AREA AS AREA, ");
		querySB.append("       A.KNO AS KNO, ");
		querySB.append("       TO_CHAR(A.BILL_DT,'DD-MM-YYYY') AS BILL_DT, ");
		querySB.append("       A.BILL_AMT AS BILL_AMT, ");
		querySB.append("       A.ZRO_LOCATION AS ZRO_LOCATION, ");
		querySB.append("       A.CURR_MTR_READNG AS MTR_READ, ");
		querySB.append("       A.BILL_BASIS as BILL_BASIS, "); /*
																 * Start: Added
																 * by Madhuri on
																 * 31-08-2017 as
																 * per jtrac
																 * -Android-388
																 */
		querySB.append("       A.BILL_GEN_SRCE as BILL_GEN_BY, ");
		querySB
				.append("       NVL(A.MTR_RDR_NAME,'NA') ||'('||NVL(A.MTR_RDR_EMPID,'NA')||')' as MTR_RDR_NAME_ID, ");
		querySB.append("       A.MTR_READ_REMRK as MTR_READ_STATUS, ");
		/*
		 * End: Added by Madhuri on 31-08-2017 as per jtrac -Android-388
		 */
		querySB.append("       A.IMG_LNK AS IMG_LNK, ");
		querySB.append("       A.IMG_AUDIT_STATUS AS IMG_AUDIT_STATUS, ");
		querySB.append("       A.IMG_AUDIT_FAIL_REASON AS AUDIT_FAIL_REASON, ");
		querySB.append("       A.IMG_AUDIT_REMRK AS AUDIT_REMRK, ");
		querySB.append("       A.IMG_ADT_FLG AS AUDIT_FLG ");
		querySB.append("  FROM DJB_AUDIT_STG A ");
		querySB.append("  WHERE 1=1 ");
		if (null != imageAuditSearchCriteria.getSearchZone()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchZone())) {
			querySB.append("  AND A.ZONE = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchMRNo()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchMRNo())) {
			querySB.append("  AND A.MR = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchArea()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchArea())) {
			querySB.append("  AND A.AREA = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchKno()
				&& !""
						.equalsIgnoreCase(imageAuditSearchCriteria
								.getSearchKno())) {
			querySB.append("  AND A.KNO = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchBillRound()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchBillRound())) {
			querySB.append("  AND A.BILL_ROUND = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchZROCode()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchZROCode())) {
			querySB.append("  AND A.ZRO_LOCATION = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchAuditStatus()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchAuditStatus())
				&& !"ALL".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchAuditStatus())) {
			querySB.append("  AND A.IMG_ADT_FLG = ? ");
		}
		/*
		 * Start: Added by Madhuri on 31-08-2017 as per jtrac -Android-388
		 */
		if (null != imageAuditSearchCriteria.getsearchMeterRdrEmpId()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getsearchMeterRdrEmpId())
				&& !"NA".equalsIgnoreCase(imageAuditSearchCriteria
						.getsearchMeterRdrEmpId())) {
			querySB.append("  AND A.MTR_RDR_EMPID = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchdBillGenSource()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchdBillGenSource())) {
			querySB.append("  AND A.BILL_GEN_SRCE = ? ");
		}
		if (null != imageAuditSearchCriteria.getSearchBillFromDate()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchBillFromDate())) {

			querySB.append("  AND TRUNC(A.BILL_DT) >= to_date('"
					+ imageAuditSearchCriteria.getSearchBillFromDate().trim()
					+ "', 'DD-MM-YYYY') ");

			AppLog
					.info("imageAuditSearchCriteria.getSearchBillFromDate().trim()****"
							+ imageAuditSearchCriteria.getSearchBillFromDate()
									.trim());

		}
		if (null != imageAuditSearchCriteria.getSearchBillToDate()
				&& !"".equalsIgnoreCase(imageAuditSearchCriteria
						.getSearchBillToDate())) {

			querySB.append("  AND TRUNC(A.BILL_DT) <= to_date('"
					+ imageAuditSearchCriteria.getSearchBillToDate().trim()
					+ "', 'DD-MM-YYYY') ");
			AppLog
					.info("imageAuditSearchCriteria.getSearchBillToDate().trim() ****"
							+ imageAuditSearchCriteria.getSearchBillToDate()
									.trim());

		}
		/*
		 * End: Added by Madhuri on 31-08-2017 as per jtrac -Android-388
		 */
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to get Record Status And ErrorDesc.
	 * </p>
	 */
	public static String getRecordStatusAndErrorDescQuery() {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select  t1.BILLING_REMARKS billingremarks,t3.descr as RECSTATS,t4.descr as ERRORDESC from CM_CONS_PRE_BILL_PROC t1 left join cm_cons_pre_bill_error_rec t2 on t2.acct_id = t1.acct_id and t2.bill_round_id = t1.bill_round_id and t2.process_counter = t1.process_counter left join cons_pre_bill_stat_lookup t3 on t3.cons_pre_bill_stat_id = t1.cons_pre_bill_stat_id left join cons_pre_bill_stat_lookup t4 on t4.cons_pre_bill_stat_id = t2.excptn_status where t1.bill_round_id = ? and t1.ACCT_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Role list.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 29-01-2016
	 * @see GetterDAO#getStatusListMap
	 * @return query String
	 */
	public static String getRoleList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userRoleId = (String) session.get("userRoleId");

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select STATUS_CODE_ACCESS from DJB_DATA_ENTRY_USERS_ROLE where user_role="
						+ userRoleId);
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is used to save Audit action details
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 09-03-2016
	 */
	public static String getSaveAuditActionQuery(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE DJB_AUDIT_STG D ");
		querySB.append("SET    D.LAST_UPDTAE_DTTM   = SYSTIMESTAMP, ");
		querySB.append("       D.FINAL_AUDIT_ON     = SYSTIMESTAMP, ");
		if (null != meterReadImgAuditDetails.getLastUpdatedBy()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getLastUpdatedBy().trim())) {
			querySB.append("       D.LAST_UPDATED_BY    = ?, ");
		}
		if (null != meterReadImgAuditDetails.getFinalAuditBy()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getFinalAuditBy().trim())) {
			querySB.append("       D.FINAL_AUDIT_BY     = ?, ");
		}
		if (null != meterReadImgAuditDetails.getFinalAuditStatus()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getFinalAuditStatus().trim())) {
			querySB.append("       D.FINAL_AUDIT_STATUS = ?, ");
		}

		if (null != meterReadImgAuditDetails.getFinalAuditAction()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getFinalAuditAction().trim())) {
			querySB.append("       D.FINAL_AUDIT_ACTION = ? ");
		}
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    D.KNO = ? ");
		querySB.append("AND    D.BILL_ROUND = ?");

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check if the new interest detail row has been saved successfully
	 * or not for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getSavedInterestRowCountQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT COUNT(*) AS COUNT FROM DJB_DEV_CHG_INTEREST A ");
		querySB.append(" WHERE A.COLONY_ID = ? AND A.DATA_FROZEN_BY = ? ");
		querySB.append(" AND A.DATA_FROZEN_DTTM >= SYSDATE-"
				+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check if the new colony has been saved successfully or not for
	 * dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getSavedNewColonyIdQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.COLONY_ID AS COLONY_ID FROM DJB_DEV_CHG_COLONY A ");
		querySB
				.append(" WHERE A. DATA_FROZEN_BY = ? AND A.DATA_FROZEN_DTTM >= SYSDATE-"
						+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		querySB
				.append(" AND A.COLONY_NAME=? AND A.ZRO_CD=? AND A.CHARGE_TYPE_FLG=? AND A.NOTIFY_DT=TO_DATE(?,'dd-mm-yyyy') ");
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check if the new rate detail row has been saved successfully or
	 * not for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getSavedRateRowCountQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) AS COUNT FROM DJB_DEV_CHG_RATE A ");
		querySB.append(" WHERE A.COLONY_ID = ? AND DATA_FROZEN_BY = ? ");
		querySB.append(" AND DATA_FROZEN_DTTM >= SYSDATE-"
				+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to check if the rebate detail row has been saved successfully or
	 * not for dev charge, JTrac DJB-3994.
	 * </p>
	 * 
	 * @author Reshma Srivastava(Tata Consultancy Services)
	 * @since 03-08-2015
	 * @return
	 */
	public static String getSavedRebateRowCountQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) AS COUNT FROM DJB_DEV_CHG_REBATE A ");
		querySB.append(" WHERE A.COLONY_ID = ? AND A.DATA_FROZEN_BY = ? ");
		querySB.append(" AND A.DATA_FROZEN_DTTM >= SYSDATE-"
				+ DJBConstants.DEV_CHRG_ROW_FREEZE_INTERVAL_MIN);
		AppLog.info(querySB.toString());
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Save Status list to populate Status Drop down.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 29-01-2016
	 * @see GetterDAO#getSaveStatusListMap
	 * @return query String
	 */
	public static String getSaveStatusListMapQuery(String roleList) {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT status_desc,status_code  FROM cm_file_status_detail WHERE activ_flg='"
						+ DJBConstants.FLAG_Y
						+ "' and status_code in ("
						+ roleList + ")  ORDER BY status_code ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get List of Searched Results
	 * </p>
	 * 
	 * @return
	 */
	public static String getSearchedResultListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT * ");
		querySB.append("FROM   (SELECT H.KNO AS KNO, ");
		querySB.append("               H.BILL_ROUND AS BILL_ROUND, ");
		querySB.append("               H.ZRO_LOCATION AS ZRO_LOCATION, ");
		querySB
				.append("               H.PER_VARIATION_AVG_CNSUMPTN AS PER_VARIATION_AVG_CNSUMPTN, ");
		querySB
				.append("               H.PER_VARIATION_PREVUS_READNG AS PER_VARIATION_PREVUS_READNG, ");
		querySB
				.append("               TO_CHAR(H.LAST_AUDIT_DATE, 'DD-MM-YYYY') AS LAST_AUDIT_DATE, ");
		querySB.append("               H.AUDIT_STATUS AS AUDIT_STATUS ");
		querySB.append("        FROM   DJB_AUDIT_SEARCH_HIST H ");
		querySB.append("        WHERE  1 = 1 ");
		querySB.append("        AND    H.USER_ID = ? ");
		querySB.append("        ORDER  BY H.CREATE_DTTM DESC) ");
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    ROWNUM <= "
				+ DJBConstants.KNO_AUDIT_SEARCH_HIST_COUNT);

		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to Retrieve Service Cycle list to populate Service Cycle Drop down.
	 * </p>
	 * 
	 * @see GetterDAO#getServiceCycleListMap
	 * @return query String
	 */
	public static String getServiceCycleListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T1.MR_CYC_CD AS SERVICE_CYCLE FROM CI_SP T1 ");
		querySB.append(" INNER JOIN CI_SA_SP T2 ON T2.SP_ID = T1.SP_ID ");
		querySB.append(" INNER JOIN CI_SA T3 ON T3.SA_ID = T2.SA_ID ");
		querySB
				.append(" INNER JOIN CI_ACCT_CHAR T4 ON T4.ACCT_ID = T3.ACCT_ID AND T4.CHAR_TYPE_CD = 'MRKEY'  ");
		querySB.append(" WHERE T4.ADHOC_CHAR_VAL = ? ");
		querySB
				.append(" AND T4.EFFDT = (SELECT MAX(X.EFFDT) FROM CI_ACCT_CHAR X WHERE X.ACCT_ID = T4.ACCT_ID AND X.CHAR_TYPE_CD = T4.CHAR_TYPE_CD ");
		querySB.append(" AND X.EFFDT < SYSDATE GROUP BY X.ACCT_ID ) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Service Route list to populate Service Route Drop down.
	 * </p>
	 * 
	 * @see GetterDAO#getServiceRouteListMap
	 * @return query String
	 */
	public static String getServiceRouteListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T1.MR_RTE_CD AS SERVICE_ROUTE FROM CI_SP T1 ");
		querySB.append(" INNER JOIN CI_SA_SP T2 ON T2.SP_ID = T1.SP_ID ");
		querySB.append(" INNER JOIN CI_SA T3 ON T3.SA_ID = T2.SA_ID ");
		querySB
				.append(" INNER JOIN CI_ACCT_CHAR T4 ON T4.ACCT_ID = T3.ACCT_ID AND T4.CHAR_TYPE_CD = 'MRKEY'  ");
		querySB.append(" WHERE T4.ADHOC_CHAR_VAL =? AND T1.MR_CYC_CD =? ");
		querySB
				.append(" AND T4.EFFDT = (SELECT MAX(X.EFFDT) FROM CI_ACCT_CHAR X WHERE X.ACCT_ID = T4.ACCT_ID AND X.CHAR_TYPE_CD = T4.CHAR_TYPE_CD");
		querySB.append(" AND X.EFFDT < SYSDATE GROUP BY X.ACCT_ID ) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Sorted KNO List For Demand TransferQuery.
	 * </p>
	 * 
	 * @param knoList
	 * @return
	 * @history Reshma on 26-06-2014 edited the query to take distinct kno in
	 *          the list, JTrac DJB-3195.
	 */
	public static String getSortedKNOListForDemandTransferQuery(String knoList) {
		StringBuffer querySB = new StringBuffer();
		/**
		 * Start : Reshma on 26-06-2014 edited the query to take distinct kno in
		 * the list, JTrac DJB-3195.
		 */
		// querySB.append(" SELECT B.ACCT_ID FROM CI_ACCT B, CI_SA C, CI_SP D ");
		querySB
				.append(" SELECT DISTINCT B.ACCT_ID, D.MR_CYC_RTE_SEQ FROM CI_ACCT B, CI_SA C, CI_SP D ");
		/**
		 * End : Reshma on 26-06-2014 edited the query to take distinct kno in
		 * the list, JTrac DJB-3195.
		 */
		querySB
				.append(" WHERE B.ACCT_ID = C.ACCT_ID AND C.CHAR_PREM_ID = D.PREM_ID AND D.SP_SRC_STATUS_FLG = 'C' AND C.CHAR_PREM_ID IS NOT NULL ");
		// querySB.append(" AND C.SA_STATUS_FLG = 20 ");
		querySB.append(" AND B.ACCT_ID IN(" + knoList + ")");
		querySB
				.append(" AND B.ACCT_ID NOT IN (SELECT T.ACCT_ID FROM CM_DMND_TRNSFR_STAGE T WHERE T.STAT_FLG IN ('PENDING','IN PROGRESS') OR (CM_GET_DIFF_TO_NOW_IN_SEC(T.LAST_UPDT_DTTM)/ 60) < 15) ");
		querySB.append(" ORDER BY TO_NUMBER(D.MR_CYC_RTE_SEQ) ASC");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Status list to populate Status Drop down.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 28-01-2016
	 * @see GetterDAO#getStatusListMap
	 * @return query String
	 */
	public static String getStatusListMapQuery() {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT status_desc,status_code  FROM cm_file_status_detail WHERE activ_flg='"
						+ DJBConstants.FLAG_Y + "'  ORDER BY status_code ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Rajib added this method to get Supervisor EmpId List Map Query
	 * </p>
	 * 
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @return
	 */
	public static String getSupervisorEmpIdListMapQuery(String meterReaderId) {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT R.MTR_RDR_CD,R.MTR_RDR_NAME,R.EMP_ID FROM DJB_MTR_RDR R ");
		querySB
				.append(" WHERE R.MTR_RDR_TYP = 'MI' AND R.EFF_FROM_DATE <= SYSDATE  AND (R.EFF_TO_DATE >= SYSDATE OR R.EFF_TO_DATE IS NULL) ");
		querySB.append(" AND R.IS_ACTIVE = 'Y' ");
		querySB.append(" AND R.ZRO_CD = ?  ");
		if (null != meterReaderId && !"".equals(meterReaderId.trim())) {
			querySB.append(" AND R.MTR_RDR_CD <> ?  ");
		}
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve tagged mrekys list of AMR Users
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 02-JUNE-2016
	 * @see GetterDAO#getTaggedMrkeysEmp
	 * @return query String
	 */

	public static String getTaggedMrkeysEmp() {
		StringBuffer queryTM = new StringBuffer();
		queryTM.append("SELECT /*+result_cache*/ ");
		queryTM.append(" M.MRD_CD MRKEY");
		queryTM.append("  FROM DJB_MTR_RDR_MRD M, DJB_MTR_RDR R ");
		queryTM.append(" WHERE 1 = 1 ");
		queryTM.append("   AND M.MTR_RDR_CD = R.MTR_RDR_CD(+) ");
		queryTM.append("   AND M.EFF_FROM_DATE < = SYSDATE ");
		queryTM.append("   AND M.EFF_TO_DATE IS NULL ");
		queryTM.append("   AND R.EMP_ID = ? ");
		queryTM.append("   AND R.IS_ACTIVE = 'Y' ");
		queryTM.append("   AND R.EFF_FROM_DATE < = SYSDATE ");
		queryTM.append("   AND R.EFF_TO_DATE IS NULL ");
		queryTM.append(" ORDER BY TO_NUMBER(M.MRD_CD) ASC");

		return queryTM.toString();

	}

	/**
	 * <p>
	 * Query to get Cons PreBill Status Lookup List Query to which status will
	 * be updated.
	 * </p>
	 * 
	 * @return query String
	 */
	public static String getToConsPreBillValidStatLookupListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT A.CONS_PRE_BILL_STAT_ID,A.CONS_PRE_BILL_STAT_ID || '-' || A.DESCR DESCR");
		querySB
				.append(" FROM CONS_PRE_BILL_STAT_LOOKUP A,CONS_PRE_BILL_VALID_STATUS B ");
		querySB
				.append(" WHERE A.CONS_PRE_BILL_STAT_ID=B.TO_STAT_ID AND B.FROM_STAT_ID=? ");
		querySB.append(" ORDER BY TO_NUMBER(A.CONS_PRE_BILL_STAT_ID) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get total count of audit action records
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 09-03-2016
	 */
	public static String getTotalCountOfAuditActionRecordsQuery(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getRecordDetailsListForAuditActionMainQuery(imageAuditSearchCriteria));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a quey used to get total count of List of Audit details
	 * </p>
	 * 
	 * @param imageAuditSearchCriteria
	 * @return
	 */
	public static String getTotalCountOfAuditDetailsListQuery(
			ImageAuditSearchCriteria imageAuditSearchCriteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getRecordDetailsListForImgAuditMainQuery(imageAuditSearchCriteria));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD Mapping Screen in the
	 * database.
	 * </p>
	 * 
	 * @param billRound
	 * @return
	 */
	public static String getTotalCountOfConsumerDetailsListForDemandTransferQuery(
			String mrKey) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getConsumerDetailsListForDemandTransferMainQuery());
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for Renumber Route Sequences
	 * Screen from database.
	 * </p>
	 * 
	 * @param mrKey
	 * @param serviceCycle
	 * @param serviceRoute
	 * @return
	 */
	public static String getTotalCountOfConsumerDetailsListForRenumberRouteSequencesQuery(
			String mrKey
	// , String serviceCycle, String serviceRoute
	// , String sequenceRangeFrom, String sequenceRangeTo
	) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) AS TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getConsumerDetailsListForRenumberRouteSequencesMainQuery(mrKey
				// , serviceCycle, serviceRoute
				// , sequenceRangeFrom, sequenceRangeTo
				));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		// AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get total count of list of consumption variation
	 * audit details
	 * </p>
	 * 
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	public static String getTotalCountOfConsumptionVariationAuditDetailsListQuery(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getConsumptionVariationRecordDetailsListMainQuery(consumptionAuditSearchCriteria));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Total Count Of Demand Transfered Query.
	 * </p>
	 * 
	 * @param knoList
	 * @return
	 */
	public static String getTotalCountOfDemandTransferedQuery(String knoList,
			String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getConsumerDetailsListForDemandTransferedMainQuery(
				knoList, userId));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for File Search Screen in the
	 * database.
	 * </p>
	 * 
	 * @param fileNumberSearchCrieteria
	 * @return
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 27-01-2016
	 */
	public static String getTotalCountOfFileDetailsListForSearchQueryQC(
			FileNumberSearchCrieteria fileNumberSearchCrieteria) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getFileListForSearchMainQuery(fileNumberSearchCrieteria));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD Credentials Screen in
	 * the database.
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * 
	 * @return
	 */
	public static String getTotalCountOfHHDCredentialsRecordsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("select count(*) TOTAL_RECORDS from djb_web_service_users");

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD Mapping Screen in the
	 * database.
	 * </p>
	 * 
	 * @param billRound
	 * @return
	 */
	public static String getTotalCountOfHHDMappingRecordsQuery(String status,
			String zoneCode, String hhdId, String mrKeyList) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getHHDMappingDetailsListMainQuery(status, zoneCode,
				hhdId, mrKeyList));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD Master Screen from
	 * <code>HHD_MASTER</code> table of database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getTotalCountOfHHDMasterRecordsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getHHDMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD User Master Screen from
	 * <code>HHD_USER_MASTER</code> table of database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getTotalCountOfHHDUserMasterRecordsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getHHDUserMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for HHD Version Master Screen
	 * from <code>HHD_VERSION_MASTER</code> table of database.
	 * </p>
	 * 
	 * @return
	 */
	public static String getTotalCountOfHHDVersionMasterRecordsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB.append(getHHDVersionMasterDetailsListMainQuery());
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to get Total Count Of Meter Replacement Detail Records
	 * Query.
	 * </p>
	 * 
	 * @param searchFor
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKeyList
	 * @param kno
	 * @return
	 */
	public static String getTotalCountOfMeterReplacementDetailRecordsQuery(
			String searchFor, String zone, String mrNo, String area,
			String mrKeyList, String kno, String companyName) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		querySB.append(getMeterReplacementDetailMainQuery(searchFor, zone,
				mrNo, area, mrKeyList, kno, companyName));
		querySB.append(" ) T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get Total Count Of New Route Sequences Query.
	 * </p>
	 * 
	 * @param spIdList
	 * @return
	 */
	public static String getTotalCountOfNewRouteSequencesQuery(String spIdList) {
		StringBuffer querySB = new StringBuffer();
		AppLog.info("spIDList" + spIdList);
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		/** Start:The main Query to get Records */
		querySB
				.append(getConsumerDetailsListForNewRouteSequencesMainQuery(spIdList));
		/** End:The main Query to get Records */
		querySB.append(" ) T1 ");
		// AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get the total number of records for OperationMRKeyReview Screen
	 * in the database.
	 * </p>
	 * 
	 * @param mrKeyStatusCode
	 * @param processCounter
	 * @param mrKeyListforSearch
	 * @param fromDate
	 * @param toDate
	 * @param orderBy
	 * @param billRound
	 * @return query String
	 */
	public static String getTotalCountOfOperationMRKeyReviewRecordsQuery(
			String mrKeyStatusCode, String processCounter,
			String mrKeyListforSearch, String fromDate, String toDate,
			String orderBy, String billRound) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT COUNT(*) TOTAL_RECORDS FROM (");
		querySB.append(getOperationMRKeyReviewDetailsListMainQuery(
				mrKeyStatusCode, processCounter, mrKeyListforSearch, fromDate,
				toDate, orderBy, billRound));
		if ("Frozen Date".equalsIgnoreCase(orderBy)
				|| "Update Date".equalsIgnoreCase(orderBy)
				|| "Entry Date".equalsIgnoreCase(orderBy)
				|| "Park Date".equalsIgnoreCase(orderBy)
				|| "Bill Gen Date".equalsIgnoreCase(orderBy)) {
			querySB.append(" WHERE 1=1 ");
			if (null != fromDate && !"".equals(fromDate)) {
				querySB.append(" AND TRUNC(T.ORDER_BY) >= TO_DATE('" + fromDate
						+ "','DD/MM/YYYY')");
			}
			if (null != toDate && !"".equals(toDate)) {
				querySB.append(" AND TRUNC(T.ORDER_BY) <= TO_DATE('" + toDate
						+ "','DD/MM/YYYY')");
			}
		}
		querySB.append(") T1 ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get total count of searched history records
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	public static String getTotalCountOfSearchHistoryRecords(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT COUNT(*) AS TOTAL_RECORDS ");
		querySB.append("FROM   DJB_AUDIT_SEARCH_HIST H ");
		querySB.append("WHERE  1 = 1 ");
		if (null != consumptionAuditSearchCriteria.getSearchKno()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchKno())) {
			querySB.append("AND    H.KNO = ? ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchBillRound()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchBillRound())) {
			querySB.append("AND    H.BILL_ROUND = ? ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchZROCode()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchZROCode())) {
			querySB.append("AND    H.ZRO_LOCATION = ? ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchVarAnualAvgConsumption()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption())) {
			querySB.append("AND    H.PER_VARIATION_AVG_CNSUMPTN = ? ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchVarPrevReading()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarPrevReading())) {
			querySB.append("AND    H.PER_VARIATION_PREVUS_READNG = ? ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchLastAuditedBeforeDate()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate())) {
			querySB.append("AND    H.LAST_AUDIT_DATE = ? ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchConsumptionVariationAuditStatus()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus())) {
			querySB.append("AND    H.AUDIT_STATUS = ? ");
		}
		if (null != consumptionAuditSearchCriteria.getUserId()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getUserId())) {
			querySB.append("AND    H.USER_ID = ?");
		}

		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to update Audit findings
	 * </p>
	 * 
	 * @param meterReadImgAuditDetails
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 09-03-2016
	 */
	public static String getUpdateAuditFindingsQuery(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE DJB_AUDIT_STG D ");
		querySB.append("SET    D.LAST_UPDTAE_DTTM           = SYSTIMESTAMP, ");
		querySB.append("       D.LAST_AUDIT_DATE = SYSTIMESTAMP, ");
		if (null != meterReadImgAuditDetails.getNonSatsfctryReadngReasn()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getNonSatsfctryReadngReasn().trim())) {
			querySB.append("       D.NON_SATSFCTRY_READNG_REASN = ?, ");
		}
		if (null != meterReadImgAuditDetails.getSatsfctryReadngReasn()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getSatsfctryReadngReasn().trim())) {
			querySB.append("       D.SATSFCTRY_READNG_REASN     = ?, ");
		}
		if (null != meterReadImgAuditDetails.getSugstdAuditAction()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getSugstdAuditAction().trim())) {
			querySB.append("       D.SUGSTD_AUDIT_ACTION        = ?, ");
		}
		if (null != meterReadImgAuditDetails.getLastAuditStatus()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getLastAuditStatus().trim())) {
			querySB.append("       D.LAST_AUDIT_STATUS          = ?, ");
		}
		if (null != meterReadImgAuditDetails.getLastUpdatedBy()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getLastUpdatedBy().trim())) {
			querySB.append("       D.LAST_UPDATED_BY            = ?, ");
		}
		if (null != meterReadImgAuditDetails.getConsumptnVariatnReasn()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getConsumptnVariatnReasn().trim())) {
			querySB.append("       D.CONSUMPTN_VARIATN_REASN    = ?, ");
		}
		if (null != meterReadImgAuditDetails.getAuditorName()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetails
						.getAuditorName().trim())) {
			querySB.append("       D.AUDITOR_NAME   = ? ");
		}
		querySB.append("WHERE  1 = 1 ");
		querySB.append("AND    D.KNO = ? ");
		querySB.append("AND    D.BILL_ROUND = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to update KNO audit details
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @param meterReadImgAuditDetails
	 * @return
	 */
	public static String getUpdateKnoDetailAuditQuery(
			MeterReadImgAuditDetails meterReadImgAuditDetails) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE DJB_AUDIT_STG D ");
		querySB.append("SET    D.SITE_VIST_GIVEN_BY = ?, ");
		if (!DJBConstants.KNO_AUDIT_MR_ASSIGNTO
				.equalsIgnoreCase(meterReadImgAuditDetails.getAssignTo())) {
			querySB.append("       D.ASSIGN_TO          = ?, ");
		}
		querySB.append("       D.SITE_VIST_REQURD   = ?, ");
		querySB.append("       D.LAST_AUDIT_STATUS  = ?, ");
		if (!DJBConstants.KNO_AUDIT_NO_REMARK
				.equalsIgnoreCase(meterReadImgAuditDetails.getLastAuditRmrk())) {
			querySB.append("       D.LAST_AUDIT_RMRK    = ?, ");
		}
		querySB.append("       D.LAST_AUDIT_DATE    = SYSTIMESTAMP, ");
		querySB.append("       D.SITE_VIST_GIVEN_ON = SYSTIMESTAMP, ");
		querySB.append("       D.LAST_UPDTAE_DTTM   = SYSTIMESTAMP, ");
		if (!"NA".equalsIgnoreCase(meterReadImgAuditDetails.getAssignToFlg())) {
			querySB.append("       D.ASSIGN_TO_FLG          = ?, ");
		}
		querySB.append("       D.LAST_UPDATED_BY    = ? ");
		querySB.append("WHERE  D.KNO = ? ");
		querySB.append("AND    D.BILL_ROUND = ?");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to update Record Details For Image Audit
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 11-01-2015
	 * @param meterReadImgAuditDetailsObj
	 * @return
	 */
	public static String getUpdateRecordQuery(
			MeterReadImgAuditDetails meterReadImgAuditDetailsObj) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE DJB_AUDIT_STG D SET ");
		if (null != meterReadImgAuditDetailsObj.getImgAuditStatus()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getImgAuditStatus())) {
			querySB.append("       D.IMG_AUDIT_STATUS      = ?, ");
		}
		if (null != meterReadImgAuditDetailsObj.getImgAuditFailReason()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getImgAuditFailReason())) {
			querySB.append("       D.IMG_AUDIT_FAIL_REASON = ?, ");

		}
		if (null != meterReadImgAuditDetailsObj.getImgAuditRemrk()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getImgAuditRemrk())) {
			querySB.append("       D.IMG_AUDIT_REMRK       = ?, ");

		}
		if (null != meterReadImgAuditDetailsObj.getImgAuditBy()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getImgAuditBy())) {
			querySB.append("       D.IMG_AUDIT_BY          = ?, ");

		}
		querySB.append("       D.IMG_ADT_FLG           = ?, ");
		querySB.append("       D.IMG_AUDIT_ON          = SYSTIMESTAMP, ");
		if (null != meterReadImgAuditDetailsObj.getImgAuditBy()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getImgAuditBy())) {
			querySB.append("D.LAST_UPDATED_BY    = ?, ");
		}
		querySB.append("D.LAST_UPDTAE_DTTM   = SYSTIMESTAMP ");
		if (null != meterReadImgAuditDetailsObj.getKno()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj.getKno())
				&& null != meterReadImgAuditDetailsObj.getBillRound()
				&& !"".equalsIgnoreCase(meterReadImgAuditDetailsObj
						.getBillRound())) {
			querySB.append("WHERE D.KNO = ?");
			querySB.append("AND D.BILL_ROUND = ?");
		} else {
			return "<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";

		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get user details
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getUserDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT T.USER_PASSWORD,TO_CHAR(SYSDATE,'DDMONYYYY') AS CURRENT_DATE,VALID_SERVICE_LIST,VALID_IP_LIST,VALID_HOST_LIST");
		querySB
				.append(" ,TO_CHAR(T.VALID_ACCESS_FROM, 'HH24:MI:SS') VALID_ACCESS_FROM");
		querySB
				.append(" ,TO_CHAR(T.VALID_ACCESS_TO, 'HH24:MI:SS') VALID_ACCESS_TO");
		querySB.append(" ,CASE ");
		querySB
				.append("  WHEN TO_DATE(TO_CHAR(T.VALID_ACCESS_FROM, 'HH24:MI:SS'),'HH24:MI:SS') <= TO_DATE(TO_CHAR(SYSDATE, 'HH24:MI:SS'), 'HH24:MI:SS') AND TO_DATE(TO_CHAR(SYSDATE, 'HH24:MI:SS'), 'HH24:MI:SS') <= TO_DATE(TO_CHAR(T.VALID_ACCESS_TO, 'HH24:MI:SS'), 'HH24:MI:SS') THEN 'Y'");
		querySB.append(" ELSE 'N' END AS "
				+ DJBConstants.MAP_KEY_IS_FORBIDDEN_TIME);
		querySB.append(" FROM DJB_WEB_SERVICE_USERS T ");
		querySB
				.append("WHERE T.EFF_FROM_DATE<=SYSDATE AND (SYSDATE<=T.EFF_TO_DATE OR T.EFF_TO_DATE IS NULL) AND T.USER_ID=? ");

		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get list of file number allocated to users as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String getUserFileNbrAllocationQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT A.ZRO_CD,A.MIN_VAL,A.MAX_VAL FROM CM_FILE_RANGE_MAP_EMP A WHERE A.USER_ID=? order by MIN_VAL ASC");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to populate User list Drop down.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 30-01-2016
	 * @see GetterDAO#getUserListMap
	 * @return query String
	 */
	public static String getUserListMapQuery() {

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT user_id,user_name  FROM DJB_DATA_ENTRY_USERS");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Zone list to populate Zone Drop down.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 21-03-2013
	 * @see GetterDAO#getZoneListMap
	 * @return query String
	 */
	public static String getZoneListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT T1.SUBZONE_CD, T2.SUBZONE_NAME FROM DJB_ZN_MR_AR_MRD T1,DJB_SUBZONE T2 WHERE T1.SUBZONE_CD=T2.SUBZONE_CD ORDER BY SUBZONE_NAME ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve Zone MR Area By MRKEY.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 08-05-2013
	 * @see GetterDAO#getZoneMRAreaByMRKEY
	 * @return query String
	 */
	public static String getZoneMRAreaByMRKEYQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT T.MRD_CD,T.SUBZONE_CD,T.MR_CD,T.AREA_CD,T.AREA_DESC FROM DJB_ZN_MR_AR_MRD T ");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND T.MRD_CD=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to fetch zro code by zone,mr_cd and area_cd.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 06-09-2016
	 * @return query String
	 */
	public static String getZrocdByZoneMrAreaQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("select distinct d.zro_cd as ZRO_CD ");
		querySB.append("  from djb_mrd d, djb_zn_mr_ar_mrd e ");
		querySB.append(" where 1=1 ");
		querySB.append("   and d.mrd_cd = e.mrd_cd ");
		querySB.append("   and e.area_cd =? ");
		querySB.append("   and e.mr_cd =? ");
		querySB.append("   and e.subzone_cd =? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get ZRO Code
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String getZROCodeQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT NVL(Z.ZRO_CD, '') ZRO ");
		querySB.append("            FROM DJB_ZRO Z ");
		querySB.append("           WHERE UPPER(Z.ZRO_DESC) = UPPER( ? )");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Retrieve ZRO Location list.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 29-01-2016
	 * @see GetterDAO#getStatusListMap
	 * @return query String
	 */
	public static String getZROList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userId = (String) session.get("userId");

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" select zro_location_code from DJB_DATA_ENTRY_USERS where USER_ID like '"
						+ userId
						+ "' and USER_ACTIVE='"
						+ DJBConstants.FLAG_Y
						+ "'");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to get ZRO List
	 * </p>
	 * 
	 * @return
	 */
	public static String getZROListMapQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT ZRO_CD, ZRO_DESC FROM DJB_ZRO ORDER BY ZRO_DESC ASC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get ZRO Query.
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 27-01-2016
	 */
	public static String getZROQuery(String list) {
		List<String> items = Arrays.asList(list.split("\\s*,\\s*"));

		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT A.ZRO_CD,A.ZRO_DESC FROM DJB_ZRO A WHERE A.ZRO_CD in ( ");
		for (int i = 0; i < items.size(); i++) {
			querySB.append("'" + items.get(i) + "',");
		}
		querySB.append("'') ORDER BY A.ZRO_DESC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Get ZRO Query.
	 * </p>
	 * 
	 * @param zone
	 * @param mrNo
	 * @param area
	 * @param mrKey
	 * @return
	 */
	public static String getZROQuery(String zone, String mrNo, String area,
			String mrKey) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" SELECT DISTINCT A.ZRO_CD,A.ZRO_DESC FROM DJB_ZRO A,DJB_MRD B,DJB_ZN_MR_AR_MRD C WHERE A.ZRO_CD=B.ZRO_CD AND B.MRD_CD=C.MRD_CD ");
		if (null != mrKey && !"".equalsIgnoreCase(mrKey.trim())) {
			querySB.append(" AND C.MRD_CD=? ");
		}
		if (null != zone && !"".equalsIgnoreCase(zone.trim())) {
			querySB.append(" AND C.SUBZONE_CD=? ");
		}
		if (null != mrNo && !"".equalsIgnoreCase(mrNo.trim())) {
			querySB.append(" AND C.MR_CD=? ");
		}
		if (null != area && !"".equalsIgnoreCase(area.trim())) {
			querySB.append(" AND C.AREA_CD=? ");
		}
		querySB.append(" ORDER BY A.ZRO_DESC ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to save mrd transfer details in cm_mrd_transfr_stg in PENDING state
	 * as per Jtrac DJB-2200
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 06-09-2016
	 * @return query String
	 */

	public static String initiateMrdTransferQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("insert into cm_mrd_transfr_stg ");
		querySB.append("  (MRD_CD, ");
		querySB.append("   SUBZONE_CD, ");
		querySB.append("   MR_CD, ");
		querySB.append("   AREA_CD, ");
		querySB.append("   NEW_ZRO_CD, ");
		querySB.append("   OLD_ZRO_CD, ");
		querySB.append("   STATUS, ");
		querySB.append("   REMARKS, ");
		querySB.append("   CREATE_DTTM, ");
		querySB.append("   CREATED_BY, ");
		querySB.append("   LAST_UPDATE_DTTM, ");
		querySB.append("   LAST_UPDATE_BY) ");
		querySB.append("values ");
		querySB.append("  (?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   'PENDING', ");
		querySB.append("   null, ");
		querySB.append("   sysdate, ");
		querySB.append("   ?, ");
		querySB.append("   null, ");
		querySB.append("   null)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Inserting records into staging table CM_DMND_TRNSFR_STAGE for Transfer
	 * Demand Process Query.
	 * </p>
	 * 
	 * @return
	 */
	public static String initiateTransferDemandQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO CM_DMND_TRNSFR_STAGE (ACCT_ID, WCNO, CUST_NAME, ADDRESS, CUST_CATEGORY, OLD_MRKEY, OLD_RTE_SEQ, NEW_MRKEY, CREATED_BY)");
		querySB.append(" SELECT DISTINCT A.ACCT_ID AS ACCT_ID,");
		querySB.append(" CM_GET_ACTUAL_WCNO(A.ACCT_ID) AS WCNO,");
		querySB.append(" CM_GET_NAME(SYSDATE, A.ACCT_ID) AS CUST_NAME,");
		querySB.append(" CM_GET_ADD_STR(C.CHAR_PREM_ID) AS ADDRESS,");
		querySB
				.append(" B.CUST_CL_CD AS CUST_CATEGORY,A.ADHOC_CHAR_VAL AS OLD_MRKEY,");
		querySB
				.append(" D.MR_CYC_RTE_SEQ AS OLD_RTE_SEQ,? AS NEW_MRKEY,? AS CREATED_BY");
		querySB.append(" FROM CI_ACCT_CHAR A, CI_ACCT B, CI_SA C, CI_SP D");
		querySB.append(" WHERE A.ACCT_ID = B.ACCT_ID");
		querySB.append(" AND B.ACCT_ID = C.ACCT_ID");
		querySB.append(" AND C.CHAR_PREM_ID = D.PREM_ID");
		querySB.append(" AND D.SP_SRC_STATUS_FLG = 'C'");
		querySB.append(" AND C.CHAR_PREM_ID IS NOT NULL");
		// Aniket started changing this to fetch maximum effective date only for
		// 'MRKEY' characteristic.
		querySB
				.append(" AND A.EFFDT=(SELECT MAX(X.EFFDT) FROM CI_ACCT_CHAR X WHERE A.ACCT_ID=X.ACCT_ID AND X.CHAR_TYPE_CD = 'MRKEY')");
		// Aniket ended changing this to fetch maximum effective date only for
		// 'MRKEY' characteristic.
		// querySB
		// .append(" AND TRIM(C.SA_TYPE_CD) IN ('SAWATR', 'SAWATSEW', 'UNMTRD')");
		// querySB.append(" AND C.SA_STATUS_FLG = 20");
		querySB.append(" AND A.CHAR_TYPE_CD = 'MRKEY'");
		querySB.append(" AND B.ACCT_ID=?");
		// Aniket started changing this to transfer demand even if KNO has more
		// than one active SA.
		querySB.append(" AND ROWNUM < 2");
		// Aniket ended changing this to transfer demand even if KNO has more
		// than one active SA.
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert values to CM_FILE_RANGE_MAP_EMP as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String insertAllocatedFileToUserQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();

		/*
		 * querySB.append("INSERT INTO CM_FILE_RANGE_MAP_EMP ");
		 * querySB.append("VALUES (?,?,?,?,?,SYSDATE,null,null)");
		 */
		querySB.append("INSERT INTO CM_FILE_RANGE_MAP_EMP ");
		querySB.append("  (USER_ID, ");
		querySB.append("   ZRO_CD, ");
		querySB.append("   MIN_VAL, ");
		querySB.append("   MAX_VAL, ");
		querySB.append("   CREATED_BY, ");
		querySB.append("   CREATED_DTTM, ");
		querySB.append("   LAST_UPDT_BY, ");
		querySB.append("   LAST_UPDT_DTTM) ");
		querySB.append("VALUES ");
		querySB.append("  (?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   ?, ");
		querySB.append("   SYSDATE, ");
		querySB.append("   NULL, ");
		querySB.append("   NULL)");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert data into CM_DWARKA_MASTER_DATA table JTrac DJB-4465 and
	 * OpenProject-918. DJB-4465.
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 27-04-2016
	 * @return query String
	 */
	public static String insertDataUploadQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append(" INSERT INTO CM_DWARKA_MASTER_DATA");
		querySB.append("(ENTITY_NAME,");
		querySB.append("FATHER_HUS_NAME,");
		querySB.append("MOBILE_NO,");
		querySB.append("ID_TYPE_CD,");
		querySB.append("PER_ID_NBR,");
		querySB.append("HOUSE_NO,");
		querySB.append("ROAD_NO,");
		querySB.append("SUBLOCALITY_1,");
		querySB.append("SUBLOCALITY_2,");
		querySB.append("SUB_COLONY,");
		querySB.append("VILLAGE,");
		querySB.append("KHASRA_NO,");
		querySB.append("SOCIETY_NAME,");
		querySB.append("PIN_CODE,");
		querySB.append("LOCALITY,");
		querySB.append("SUB_LOCALITY,");
		querySB.append("JJR_COLONY,");
		querySB.append("PROP_TYPE,");
		querySB.append("NO_OF_FLR,");
		querySB.append("URBAN,");
		querySB.append("AREA_NO,");
		querySB.append("PLOT_AREA,");
		querySB.append("BUILT_UP_AREA,");
		querySB.append("WCON_TYPE,");
		querySB.append("WCON_USE,");
		querySB.append("NO_OF_OCC_DWELL_UNITS,");
		querySB.append("NO_OF_UNOCC_DWELL_UNITS,");
		querySB.append("NO_OF_CHILDREN,");
		querySB.append("NO_OF_ADULTS,");
		querySB.append("IS_DJB,");
		querySB.append("WCNO,");
		querySB.append("MTR_SIZE,");
		querySB.append("MTR_TYPE,");
		querySB.append("INI_REG_READ_DATE,");
		querySB.append("INI_REG_READ_REMK,");
		querySB.append("INI_REG_READ,");
		querySB.append("AVG_CONS,");
		querySB.append("ZONE_NO,");
		querySB.append("MR_NO,");
		querySB.append("AREA,");
		querySB.append("OPENING_BALANCE,");
		querySB.append("APP_PURPOSE,");
		querySB.append("CREATE_DTTM,");
		querySB.append("CREATE_BY,");
		querySB.append("SOURCE_FILE_NAME,");
		querySB.append("ROW_NUMBER)");
		querySB
				.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'DEAPP',?,?)");

		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * To insert Djb users ip address Details by to table
	 * <code>djb_users_ip_address</code>.
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * 
	 * @return
	 */
	public static String insertDjbUsersIpAddressDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO djb_users_ip_address(IP_ADDRESS, SESSION_ID, DATE_TIME) ");
		querySB.append(" VALUES(?,?,SYSDATE)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This a query used to insert Header data
	 * </p>
	 * 
	 * @param aMRHeaderDetails
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String insertHeaderDataQuery(AMRHeaderDetails aMRHeaderDetails) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("INSERT INTO CM_AMR_FILE_DETAIL ( ");
		if (null != aMRHeaderDetails && 0 != aMRHeaderDetails.getHeaderID()) {
			querySB.append("  HEADER_ID, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getUcmFilePath()) {
			querySB.append("   UCM_PATH, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getMeterReaderName()) {
			querySB.append("   METER_READER, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getMeterReadDiaryCode()) {
			querySB.append("   METER_READ_DIARY_CODE, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getServiceCycle()) {
			querySB.append("   SERVICE_CYCLE, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getStartDate()) {
			querySB.append("   START_DATE, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getEndDate()) {
			querySB.append("   END_DATE, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getRoute()) {
			querySB.append("   ROUTE, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZroCd()) {
			querySB.append("   ZRO_CD, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZroDesc()) {
			querySB.append("   ZRO_DESC, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZone()) {
			querySB.append("   ZONE, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getMr()) {
			querySB.append("   MR, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getAreaCd()) {
			querySB.append("   AREA_CD, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getAreaDesc()) {
			querySB.append("   AREA_DESC, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getCreatedBy()) {
			querySB.append("   CREATED_BY, ");
		}
		querySB.append("   CREATED_DTTM, ");
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getLastUpdatedBy()) {
			querySB.append("   UPDATED_BY, ");
		}
		querySB.append("   LAST_UPDT_DTTM) ");
		querySB.append("VALUES ( ");
		if (null != aMRHeaderDetails && 0 != aMRHeaderDetails.getHeaderID()) {
			querySB.append("  ?, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getUcmFilePath()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getMeterReaderName()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getMeterReadDiaryCode()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getServiceCycle()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getStartDate()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getEndDate()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getRoute()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZroCd()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZroDesc()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getZone()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getMr()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getAreaCd()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getAreaDesc()) {
			querySB.append("   ?, ");
		}
		if (null != aMRHeaderDetails && null != aMRHeaderDetails.getCreatedBy()) {
			querySB.append("   ?, ");
		}
		querySB.append("   SYSDATE, ");
		if (null != aMRHeaderDetails
				&& null != aMRHeaderDetails.getLastUpdatedBy()) {
			querySB.append("   ?, ");
		}
		querySB.append("   SYSDATE)");

		return querySB.toString();

	}

	/**
	 * <p>
	 * To insert HHD Credentials Details by to table
	 * <code>djb_web_service_users</code>.
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * @return insert HHD Credentials Details Query String
	 * @param addHHDid
	 * @param addPassword
	 * @param userName
	 * @return
	 */
	public static String insertHHDCredentialsDetailsQuery(String addHHDid,
			String addPassword, String userName) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO DJB_WEB_SERVICE_USERS(USER_ID, USER_PASSWORD, EFF_FROM_DATE, EFF_TO_DATE, CREATE_DTTM, CREATE_BY) ");
		querySB.append(" VALUES(?,?,SYSDATE,SYSDATE+360,SYSDATE,?)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To insert new HHD Master Details into <code>HHD_MASTER</code> table of
	 * database.
	 * </p>
	 * 
	 * @param blocked
	 * @param simId
	 * @param hhdId
	 * @param simNo
	 * @param userName
	 */
	public static String insertHHDMasterDetailsQuery(String hhdId,
			String blocked, String simId, String simNo, String userName) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO HHD_MASTER(HHD_ID,BLOCKED,SIM_ID,SIM_NO,CREATED_BY,CREATION_TIME) ");
		querySB.append(" VALUES('" + hhdId + "','" + blocked + "','" + simId
				+ "','" + simNo + "','" + userName + "',SYSDATE) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To insert HHD Meter Reader Mapping Details by to table
	 * <code>DJB_MTR_RDR_MRD</code>.
	 * </p>
	 * 
	 * @return insert HHD Meter Reader MappingDetails Query String
	 */
	public static String insertHHDMeterReaderMappingDetailsQuery(
			String effectiveDate) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO DJB_MRD_RDR_HHD(MRD_CD,MTR_RDR_CD,HHD_ID,CREATE_BY,EFF_FROM_DATE,EFF_TO_DATE,CREATE_DTTM) ");
		querySB
				.append(" VALUES(?,?,?,?,SYSDATE," + effectiveDate
						+ ",SYSDATE)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To insert new HHD User Master Details into <code>HHD_USER_MASTER</code>
	 * table of database.
	 * </p>
	 * 
	 * @param password
	 * @param userLoginCount
	 * @param userMasterId
	 * @param userId
	 * @param hhdId
	 */
	public static String insertHHDUserMasterDetailsQuery(String userMasterId,
			String password, String userLoginCount, String hhdId, String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO HHD_USER_MASTER(USER_ID,PASSWORD,USR_LOGIN_COUNT,HHD_ID,CREATED_BY,CREATION_TIME) ");
		querySB.append(" VALUES('" + userMasterId + "','" + password + "','"
				+ userLoginCount + "','" + hhdId + "','" + userId
				+ "',SYSDATE) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To insert new HHD Version Master Details into
	 * <code>HHD_VERSION_MASTER</code> table of database.
	 * </p>
	 * 
	 * @param versionNo
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param userId
	 * @param ftpLocation
	 */
	public static String insertHHDVersionMasterDetailsQuery(String versionNo,
			String ftpUserName, String ftpPassword, String ftpLocation,
			String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO HHD_VERSION_MASTER(VERSION_NO,FTP_LOCATION,FTP_USER,FTP_PASSWORD,CREATED_BY,CREATION_TIME) ");
		querySB.append(" VALUES('" + versionNo + "','" + ftpLocation + "','"
				+ ftpUserName + "','" + ftpPassword + "','" + userId
				+ "',SYSDATE) ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update Meter Replacement Detail.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#saveMeterReplacementDetail
	 * @return query String
	 */
	public static String insertMeterReplacementDetailQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" INSERT INTO CM_MTR_RPLC_STAGE( MRKEY,");
		querySB.append(" ZONECD,");
		querySB.append(" MRNO,");
		querySB.append(" AREANO,");
		querySB.append(" WCNO,");
		querySB.append(" CONSUMER_NAME,");
		querySB.append(" CUST_CL_CD,");
		querySB.append(" MTR_RPLC_STAGE_ID,");
		querySB.append(" WCONSIZE,");
		querySB.append(" WCONUSE,");
		querySB.append(" SA_TYPE_CD,");
		querySB.append(" MTR_READER_ID,");
		querySB.append(" MTRTYP,");
		querySB.append(" MTR_INSTALL_DATE,");
		querySB.append(" READER_REM_CD,");
		querySB.append(" BADGE_NBR,");
		querySB.append(" MFG_CD,");
		querySB.append(" MODEL_CD,");
		querySB.append(" MTR_START_READ,");
		querySB.append(" MTR_START_READ_REMARK,");
		querySB.append(" IS_LNT_SRVC_PRVDR,");
		querySB.append(" OLD_SA_TYPE_CD,");
		querySB.append(" OLD_SA_START_DATE,");
		querySB.append(" OLD_MTR_READ_DATE,");
		querySB.append(" OLD_MTR_READ,");
		querySB.append(" OLDAVGCONS,");
		querySB.append(" CREATED_BY,");
		querySB.append(" ACCT_ID,");
		querySB.append(" BILL_ROUND_ID,");
		/**
		 * Start:21-03-2013 Matiur Rahman Added for populating Operation Area
		 * Code OP_AREA_CD
		 */
		querySB.append(" OP_AREA_CD,");
		/**
		 * End:21-03-2013 Matiur Rahman Added for populating Operation Area Code
		 * OP_AREA_CD
		 */
		/*
		 * Start:21-03-2013 Rajib Hazarika Added for populating Company name
		 * Code TP_VENDOR_NAME
		 */
		querySB.append(" TP_VENDOR_NAME,");
		/**
		 * End:03-01-2014 Rajib hazarika Added for populating Company Name
		 * TP_VENDOR_NAME
		 */
		querySB.append(" DE_CODE_VRSN)");
		querySB.append(" VALUES( ");
		querySB
				.append("(SELECT DISTINCT Z.MRD_CD  FROM DJB_ZN_MR_AR_MRD Z  WHERE Z.SUBZONE_CD = ?  AND Z.MR_CD = ?  AND Z.AREA_CD = ?)");
		querySB
				.append(" ,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?,?,?,?");
		/**
		 * Start:21-03-2013 Matiur Rahman Added for populating Operation Area
		 * Code OP_AREA_CD
		 */
		querySB
				.append(",(SELECT TRIM(OP_AREA_CD) OP_CODE FROM CI_SP_OP_AREA INNER JOIN CI_SA_SP ON CI_SA_SP.SP_ID = CI_SP_OP_AREA.SP_ID INNER JOIN CI_SA ON CI_SA.SA_ID = CI_SA_SP.SA_ID WHERE CI_SA.SA_STATUS_FLG = 20 AND CI_SA.SA_TYPE_CD IN ('SAWATR','SAWATSEW','UNMTRD') AND CI_SA.ACCT_ID = ?)");
		/**
		 * End:21-03-2013 Matiur Rahman Added for populating Operation Area Code
		 * OP_AREA_CD
		 */
		/**
		 * Start:21-03-2013 Rajib Hazarika Added for populating Company Name
		 * Code TP_VENDOR_NAME
		 */
		querySB.append(",? ");
		/**
		 * End:03-01-2014 Rajib Hazarika Added for populating Company Name
		 * TP_VENDOR_NAME
		 */
		querySB.append(",'" + DE_CODE_VERSION + "'");
		querySB.append(" )");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert data into DJB_RAIN_WTR_HRVSTING_STG_TBL .
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */

	public static String insertPremCharDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO DJB_RAIN_WTR_HRVSTING_STG_TBL(acct_id, rwh_flg, rwh_effdt, wwt_flg, wwt_effdt, document_no,diary_no,approved_by,create_dttm,created_by,status_flg,diary_date) ");
		querySB
				.append(" VALUES(?,?,to_date(?,'yyyy-mm-dd'),?,to_date(?,'yyyy-mm-dd'),?,?,?,SYSDATE,?,?,to_date(?,'yyyy-mm-dd'))");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert data into DJB_RAIN_WTR_HRVSTING_STG_TBL .
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 29-08-2016
	 * @return query String
	 */

	public static String insertRaybCharDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" INSERT INTO DJB_RAIN_WTR_HRVSTING_STG_TBL(acct_id, RAYB_FLG, RAYB_EFFDT,document_no,approved_by,create_dttm,created_by,status_flg) ");
		querySB.append(" VALUES(?,?,to_date(?,'yyyy-mm-dd'),?,?,SYSDATE,?,?)");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to insert search history records
	 * </p>
	 * 
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @param consumptionAuditSearchCriteria
	 * @return
	 */
	public static String insertSearchHistoryRecords(
			ConsumptionAuditSearchCriteria consumptionAuditSearchCriteria) {

		StringBuffer querySB = new StringBuffer();
		querySB.append("INSERT INTO DJB_AUDIT_SEARCH_HIST (");
		if (null != consumptionAuditSearchCriteria.getSearchKno()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchKno())) {
			querySB.append("  KNO, ");

		}
		if (null != consumptionAuditSearchCriteria.getSearchBillRound()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchBillRound())) {
			querySB.append("   BILL_ROUND, ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchZROCode()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchZROCode())) {
			querySB.append("   ZRO_LOCATION, ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchVarAnualAvgConsumption()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption())) {
			querySB.append("   PER_VARIATION_AVG_CNSUMPTN, ");
		}
		if (null != consumptionAuditSearchCriteria.getSearchVarPrevReading()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarPrevReading())) {
			querySB.append("   PER_VARIATION_PREVUS_READNG, ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchLastAuditedBeforeDate()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate())) {
			querySB.append("   LAST_AUDIT_DATE, ");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchConsumptionVariationAuditStatus()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus())) {
			querySB.append("   AUDIT_STATUS, ");
		}
		if (null != consumptionAuditSearchCriteria.getUserId()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getUserId())) {
			querySB.append("   USER_ID ");
		}
		querySB.append(") VALUES (");
		if (null != consumptionAuditSearchCriteria.getSearchKno()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchKno())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria.getSearchBillRound()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchBillRound())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria.getSearchZROCode()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchZROCode())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchVarAnualAvgConsumption()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarAnualAvgConsumption())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria.getSearchVarPrevReading()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchVarPrevReading())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchLastAuditedBeforeDate()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchLastAuditedBeforeDate())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria
				.getSearchConsumptionVariationAuditStatus()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getSearchConsumptionVariationAuditStatus())) {
			querySB.append("  ?,");
		}
		if (null != consumptionAuditSearchCriteria.getUserId()
				&& !"".equalsIgnoreCase(consumptionAuditSearchCriteria
						.getUserId())) {
			querySB.append("  ?");
		}

		querySB.append("  )");

		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to find exsisting range in table CM_FILE_RANGE_MAP_EMP as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String isExistFileNbrQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT COUNT(1) CNT FROM CM_FILE_RANGE_MAP_EMP WHERE ");
		querySB.append("  1=1 ");
		querySB.append("	AND ?>= MIN_VAL ");
		querySB.append("	AND ? <= MAX_VAL ");
		querySB.append("  AND USER_ID <>?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Check whether a file is tagged to a specific employee for New
	 * Connection Fle Entry Screen as per Jtrac ANDROMR-7
	 * </p>
	 * 
	 * @return String
	 * @author Rajib Hazarika (682667,Tata Consultancy Services)
	 * @since 16-SEP-2016
	 */
	public static String isFileTaggedToEmpQuery(String fileNo) {
		StringBuffer querySB = new StringBuffer();
		if (null != fileNo) {
			querySB
					.append("SELECT COUNT(1) AS CNT FROM CM_FILE_RANGE_MAP_EMP E WHERE "
							+ fileNo + " BETWEEN E.MIN_VAL AND E.MAX_VAL");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to validate used file number in table CM_NEW_CONN_FILE_ENTRY_STG as
	 * per jTrac DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String isUsedFileNbrQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COUNT(1) CNT FROM CM_NEW_CONN_FILE_ENTRY_STG WHERE FILE_NO >=? and FILE_NO <=?");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to validate allocated file number to users as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String isvalideAllocatedFileToUserQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();

		querySB
				.append("SELECT COUNT(1) CNT FROM CM_NEW_CONN_FILE_MASTER WHERE ");
		querySB.append("  1=1 ");
		querySB.append("  AND ZRO_CD= ? ");
		querySB.append("	AND ? >= MIN_VAL ");
		querySB.append("	AND ? <= MAX_VAL");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Main query to retrieve MRD details to for Data Entry
	 * </p>
	 * 
	 * @return
	 */
	public static String mrdDataEntrySearchQuery() {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT NVL(TRIM(X.SVCCYCRTESEQ), '9999999') SEQ,");
		querySB.append(" X.ACCTID,");
		querySB.append(" X.CONNAME,");
		querySB.append(" X.WCNO,");
		querySB.append(" X.CUSTCLCD CAT,");
		querySB.append(" TRIM(X.SATYPECD) SATYPECD,");
		querySB.append(" X.SVCCYCRTESEQ,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.LASTBILLGENDATE, 'DD-MON-YYYY')), 'NA') LASTBILLGENDATE,");
		querySB.append(" X.BILLROUND_ID,");
		querySB.append(" X.MRKEY,");
		querySB.append(" NVL(TRIM(X.AVGREAD), 'NA') AVGREAD,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ.READDATE, 'DD/MM/YYYY')), 'NA') PREVMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ.REGISTEREDREAD), 'NA') RGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ.REMARK), 'NA') MTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ.READTYPE), 'NA') READTYPE,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ1.READDATE, 'DD/MM/YYYY')), 'NA') ACTLMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ1.REGISTEREDREAD), 'NA') ACTLRGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ1.REMARK), 'NA') ACTLMTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ1.READTYPE), 'NA') ACTLREADTYPE,");
		querySB.append(" NVL(TRIM(X.BILLGENDATE), ' ') CURR_BILLGENDATE,");
		querySB.append(" NVL(TRIM(X.READERREMCD), ' ') CURR_READERREMCD,");
		querySB.append(" NVL(TRIM(X.REGREAD), ' ') CURR_REGREAD,");
		querySB.append(" NVL(TRIM(X.PREBILLSTATID), ' ') CURR_PREBILLSTATID,");
		querySB.append(" NVL(TRIM(X.NEWAVGREAD), ' ') CURR_NEWAVGREAD,");
		querySB.append(" NVL(X.PNFLOOR, 'NA') PNFLOOR,");
		querySB.append(" NVL(X.NWFLOOR, ' ') NWFLOOR,");
		querySB.append(" NVL(X.REMARKS, ' ') REMARKS, ");
		querySB.append(" NVL(X.HILWFLG, ' ') HILWFLG ");
		querySB.append(" FROM (SELECT CM_CONS_PRE_BILL_PROC.ACCT_ID ACCTID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID BILLROUND_ID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.AVG_READ AVGREAD,");
		querySB
				.append(" TO_CHAR(CM_CONS_PRE_BILL_PROC.BILL_GEN_DATE, 'DD/MM/YYYY') BILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CONSUMER_NAME CONNAME,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID PREBILLSTATID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CUST_CL_CD CUSTCLCD,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.LAST_BILL_GEN_DATE LASTBILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_BY LASTUPDTBY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_DTTM LASTUPDTTM,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MRKEY MRKEY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MTR_READER_NAME MTRRDRNAME,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_AVG_READ NEWAVGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READER_REM_CD READERREMCD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READ_TYPE_FLG READTYPFLG,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REG_READING REGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SA_TYPE_CD SATYPECD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SVC_CYC_RTE_SEQ SVCCYCRTESEQ,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.WCNO WCNO,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.PREV_NO_OF_FLOORS PNFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_NO_OF_FLOORS NWFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REMARKS REMARKS,");
		querySB
				.append(" CM_GET_PREV_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ,");
		querySB
				.append(" CM_GET_LAST_ACTUAL_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ1, ");
		querySB
				.append(" CM_IS_HIGH_LOW(CM_CONS_PRE_BILL_PROC.ACCT_ID,CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID) AS HILWFLG ");
		querySB.append(" FROM CM_CONS_PRE_BILL_PROC,");
		querySB.append(" DJB_ZN_MR_AR_MRD,");
		querySB.append(" CM_MRD_PROCESSING_STAT");
		querySB
				.append(" WHERE CM_CONS_PRE_BILL_PROC.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_MRD_PROCESSING_STAT.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = CM_MRD_PROCESSING_STAT.MRKEY");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = CM_MRD_PROCESSING_STAT.BILL_ROUND_ID");
		querySB.append(" AND CM_MRD_PROCESSING_STAT.MRD_STATS_ID = 10");
		// querySB
		// .append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID <> '70'");
		// querySB
		// .append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID <> '130'");
		// querySB.append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID < "
		// + RECORD_FROZEN_STATUS);
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID NOT IN ("
						+ CONS_PRE_BILL_STAT_ID_NOT_IN + ")");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.SUBZONE_CD = ?");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.MR_CD = ?");
		querySB.append(" AND DJB_ZN_MR_AR_MRD.AREA_CD = ?");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = ?) X");
		querySB.append(" ORDER BY TO_NUMBER(SEQ)");
		AppLog.info("Query Output ------------>>>" + querySB.toString());

		return querySB.toString();
	}

	/**
	 * <p>
	 * Main query to retrieve details to be written in the xls For MRD Schedule
	 * Download.
	 * </p>
	 * 
	 * @return
	 */
	public static String mrdScheduleDownloadQuery() {
		// Main query to retrieve details to be written in the xls
		StringBuffer sqlConsumerDetailsSB = new StringBuffer();
		/*
		 * Start : Atanu added the distinct keyword on 06-07-2016 as per jtrac-
		 * DJB-4464, OpenProject#1202
		 */
		sqlConsumerDetailsSB.append("SELECT distinct X.mrkey, ");
		/*
		 * End : Atanu added the distinct keyword on 06-07-2016 as per jtrac-
		 * DJB-4464, OpenProject#1202
		 */
		sqlConsumerDetailsSB.append("       X.billround_id, ");
		sqlConsumerDetailsSB.append("       X.mtrrdrname, ");
		sqlConsumerDetailsSB.append("       X.mtr_rdr_diary_cd, ");
		sqlConsumerDetailsSB.append("       X.service_cycle, ");
		sqlConsumerDetailsSB.append("       X.route, ");
		sqlConsumerDetailsSB.append("       X.start_date, ");
		sqlConsumerDetailsSB.append("       X.end_date, ");
		sqlConsumerDetailsSB
				.append("       Nvl(Trim(X.svccycrteseq), '9999999')                 SEQ, ");
		sqlConsumerDetailsSB.append("       X.acctid, ");
		sqlConsumerDetailsSB.append("       X.wcno, ");
		sqlConsumerDetailsSB.append("       X.conname, ");
		sqlConsumerDetailsSB.append("       X.consumer_address, ");
		sqlConsumerDetailsSB
				.append("       X.custclcd                                           CAT, ");
		sqlConsumerDetailsSB.append("       X.unoc_dwel_units, ");
		sqlConsumerDetailsSB.append("       X.ocu_dwel_units, ");
		sqlConsumerDetailsSB.append("       X.totl_dewl_units, ");
		sqlConsumerDetailsSB.append("       X. no_of_floor, ");
		sqlConsumerDetailsSB.append("       X.no_of_fs_beds_rooms, ");
		sqlConsumerDetailsSB.append("       X.mtr_number, ");
		sqlConsumerDetailsSB
				.append("       Nvl(To_char(X.obj.readdate, 'DD/MM/YYYY'), 'NA')     PREVMTRRDDT, ");
		sqlConsumerDetailsSB
				.append("       Nvl(X.obj.remark, 'NA')                              PREVMTRRDSTAT, ");
		sqlConsumerDetailsSB
				.append("       Nvl(X.obj.readtype, 'NA')                            PREVREADTYPE, ");
		sqlConsumerDetailsSB
				.append("       Nvl(Trim(X.obj.registeredread), 'NA')                PREVRGMTRRD, ");
		sqlConsumerDetailsSB
				.append("       X.cons                                               PREVCONS, ");
		sqlConsumerDetailsSB.append("       X.eff_no_of_days, ");
		sqlConsumerDetailsSB
				.append("       Nvl(X.avgread, 'NA')                                 AVGREAD, ");
		sqlConsumerDetailsSB
				.append("       X.satypecd                                           SATYPECD, ");
		sqlConsumerDetailsSB.append("       X.svccycrteseq, ");
		sqlConsumerDetailsSB
				.append("       Nvl(To_char(X.lastbillgendate, 'DD-MON-YYYY'), 'NA') LASTBILLGENDATE, ");
		sqlConsumerDetailsSB
				.append("       Nvl(X.billgendate, ' ')                              CURR_BILLGENDATE, ");
		sqlConsumerDetailsSB
				.append("       Nvl(X.readerremcd, ' ')                              CURR_READERREMCD, ");
		sqlConsumerDetailsSB
				.append("       Nvl(Trim(X.regread), ' ')                            CURR_REGREAD, ");
		sqlConsumerDetailsSB
				.append("       Nvl(Trim(X.prebillstatid), ' ')                      CURR_PREBILLSTATID, ");
		sqlConsumerDetailsSB
				.append("       Nvl(Trim(X.newavgread), ' ')                         CURR_NEWAVGREAD ");
		sqlConsumerDetailsSB
				.append("FROM   (SELECT cm_cons_pre_bill_proc.acct_id ");
		sqlConsumerDetailsSB.append("               ACCTID, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.bill_round_id ");
		sqlConsumerDetailsSB.append("                      BILLROUND_ID, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.avg_read ");
		sqlConsumerDetailsSB.append("               AVGREAD, ");
		sqlConsumerDetailsSB
				.append("               To_char(cm_cons_pre_bill_proc.bill_gen_date, 'DD/MM/YYYY') ");
		sqlConsumerDetailsSB.append("                      BILLGENDATE, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.consumer_name ");
		sqlConsumerDetailsSB.append("               CONNAME, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.cons_pre_bill_stat_id ");
		sqlConsumerDetailsSB.append("                      PREBILLSTATID, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.cust_cl_cd ");
		sqlConsumerDetailsSB.append("               CUSTCLCD, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.last_bill_gen_date ");
		sqlConsumerDetailsSB.append("                      LASTBILLGENDATE, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.last_updt_by ");
		sqlConsumerDetailsSB.append("               LASTUPDTBY ");
		sqlConsumerDetailsSB.append("                      , ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.last_updt_dttm ");
		sqlConsumerDetailsSB.append("                      LASTUPDTTM, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.mrkey ");
		sqlConsumerDetailsSB.append("               MRKEY, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.mtr_reader_name ");
		sqlConsumerDetailsSB.append("               MTRRDRNAME ");
		sqlConsumerDetailsSB.append("                      , ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.new_avg_read ");
		sqlConsumerDetailsSB.append("                      NEWAVGREAD, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.reader_rem_cd ");
		sqlConsumerDetailsSB.append("                      READERREMCD, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.read_type_flg ");
		sqlConsumerDetailsSB.append("               READTYPFLG ");
		sqlConsumerDetailsSB.append("                      , ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.reg_reading ");
		sqlConsumerDetailsSB.append("                      REGREAD, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.sa_type_cd ");
		sqlConsumerDetailsSB.append("               SATYPECD, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.svc_cyc_rte_seq ");
		sqlConsumerDetailsSB.append("                      SVCCYCRTESEQ, ");
		sqlConsumerDetailsSB
				.append("               cm_cons_pre_bill_proc.wcno ");
		sqlConsumerDetailsSB.append("               WCNO, ");
		// Commented on 23-11-2012 by Matiur Rahman as per JTrac issue
		// DJB-677
		// sqlConsumerDetailsSB
		// .append("               cisadm.Cm_get_last_actual_meter_read(cm_cons_pre_bill_proc.acct_id)  OBJ ");
		// sqlConsumerDetailsSB.append("               , ");
		// Added on 23-11-2012 by Matiur Rahman as per JTrac issue
		// DJB-677
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_get_last_actual_meter_read(cm_cons_pre_bill_proc.acct_id)  OBJ ");
		sqlConsumerDetailsSB.append("               , ");
		sqlConsumerDetailsSB
				.append("               djb_zn_mr_ar_mrd.old_mrd_cd ");
		sqlConsumerDetailsSB.append("                      MTR_RDR_DIARY_CD, ");
		sqlConsumerDetailsSB.append("               ci_sp.mr_cyc_cd ");
		sqlConsumerDetailsSB.append("                      SERVICE_CYCLE, ");
		sqlConsumerDetailsSB.append("               ci_sp.mr_rte_cd ");
		sqlConsumerDetailsSB.append("               ROUTE, ");
		sqlConsumerDetailsSB
				.append("               To_char(cm_bill_window.start_date, 'DD/MM/YYYY') ");
		sqlConsumerDetailsSB.append("               START_DATE ");
		sqlConsumerDetailsSB.append("                      , ");
		sqlConsumerDetailsSB
				.append("               To_char(cm_bill_window.end_date, 'DD/MM/YYYY') ");
		sqlConsumerDetailsSB.append("                      END_DATE, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'NDU-U') ");
		sqlConsumerDetailsSB.append("                      UNOC_DWEL_UNITS, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'NDU-O') ");
		sqlConsumerDetailsSB.append("                      OCU_DWEL_UNITS, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'TNBD') ");
		sqlConsumerDetailsSB.append("                      TOTL_DEWL_UNITS, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_prem_char_val(ci_sa.char_prem_id, 'FLOOR') ");
		sqlConsumerDetailsSB.append("                      NO_OF_FLOOR, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_fs_beds_rooms(ci_sa.char_prem_id) ");
		sqlConsumerDetailsSB
				.append("                      NO_OF_FS_BEDS_ROOMS, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_prem_adv_val(ci_sa.char_prem_id, 'AVGCONS') ");
		sqlConsumerDetailsSB.append("               AVG_CONS, ");
		sqlConsumerDetailsSB.append("               ci_mtr.badge_nbr ");
		sqlConsumerDetailsSB.append("               MTR_NUMBER ");
		sqlConsumerDetailsSB.append("                      , ");
		sqlConsumerDetailsSB.append("               ci_bseg_read.msr_qty ");
		sqlConsumerDetailsSB.append("                      CONS, ");
		sqlConsumerDetailsSB
				.append("               ( ci_bseg_read.end_read_dttm - ci_bseg_read.start_read_dttm ) ");
		sqlConsumerDetailsSB.append("                      EFF_NO_OF_DAYS, ");
		sqlConsumerDetailsSB
				.append("               cisadm.Cm_get_add_str(ci_sa.char_prem_id) ");
		sqlConsumerDetailsSB.append("                      CONSUMER_ADDRESS ");
		sqlConsumerDetailsSB.append("        FROM   cisadm.djb_zn_mr_ar_mrd ");
		sqlConsumerDetailsSB
				.append("               inner join cisadm.cm_mrd_processing_stat ");
		sqlConsumerDetailsSB
				.append("                       ON cm_mrd_processing_stat.mrkey = djb_zn_mr_ar_mrd.mrd_cd ");
		sqlConsumerDetailsSB
				.append("               inner join cisadm.cm_bill_window ");
		sqlConsumerDetailsSB
				.append("                       ON cm_bill_window.bill_round_id = ");
		sqlConsumerDetailsSB
				.append("                          cm_mrd_processing_stat.bill_round_id ");
		sqlConsumerDetailsSB
				.append("               inner join cisadm.cm_cons_pre_bill_proc ");
		sqlConsumerDetailsSB
				.append("                       ON cm_cons_pre_bill_proc.mrkey = ");
		sqlConsumerDetailsSB
				.append("                          cm_mrd_processing_stat.mrkey ");
		sqlConsumerDetailsSB
				.append("                          AND cm_cons_pre_bill_proc.bill_round_id = ");
		sqlConsumerDetailsSB
				.append("                              cm_mrd_processing_stat.bill_round_id ");
		sqlConsumerDetailsSB.append("               inner join cisadm.ci_sa ");
		sqlConsumerDetailsSB
				.append("                       ON cm_cons_pre_bill_proc.sa_type_cd = ci_sa.sa_type_cd ");
		sqlConsumerDetailsSB
				.append("                          AND cm_cons_pre_bill_proc.acct_id = ci_sa.acct_id ");
		sqlConsumerDetailsSB
				.append("               inner join cisadm.ci_sa_sp ");
		sqlConsumerDetailsSB
				.append("                       ON ci_sa.sa_id = ci_sa_sp.sa_id ");
		sqlConsumerDetailsSB.append("               inner join cisadm.ci_sp ");
		sqlConsumerDetailsSB
				.append("                       ON ci_sa_sp.sp_id = ci_sp.sp_id ");
		sqlConsumerDetailsSB
				.append("               left join cisadm.ci_sp_mtr_hist ");
		sqlConsumerDetailsSB
				.append("                      ON ci_sp.sp_id = ci_sp_mtr_hist.sp_id ");
		sqlConsumerDetailsSB
				.append("               left join cisadm.ci_mtr_config ");
		sqlConsumerDetailsSB
				.append("                      ON ci_sp_mtr_hist.mtr_config_id = ");
		sqlConsumerDetailsSB
				.append("                         ci_mtr_config.mtr_config_id ");
		sqlConsumerDetailsSB.append("               left join cisadm.ci_mtr ");
		sqlConsumerDetailsSB
				.append("                      ON ci_mtr.mtr_id = ci_mtr_config.mtr_id ");
		sqlConsumerDetailsSB.append("               left join cisadm.ci_bseg ");
		sqlConsumerDetailsSB
				.append("                      ON ci_bseg.sa_id = ci_sa.sa_id ");
		sqlConsumerDetailsSB
				.append("                         AND Trunc(ci_bseg.cre_dttm) = ");
		sqlConsumerDetailsSB
				.append("                             cm_cons_pre_bill_proc.last_bill_gen_date ");
		sqlConsumerDetailsSB
				.append("               left join cisadm.ci_bseg_read ");
		sqlConsumerDetailsSB
				.append("                      ON ci_bseg.bseg_id = ci_bseg_read.bseg_id ");
		sqlConsumerDetailsSB
				.append("                         AND ci_sp.sp_id = ci_bseg_read.sp_id ");
		sqlConsumerDetailsSB.append("        WHERE  1=1 ");
		// sqlConsumerDetailsSB
		// .append("        WHERE  cm_bill_window.bill_win_stat_id = '10' ");
		// Added for issue of repeated account id for multiple Meter
		// Number
		sqlConsumerDetailsSB
				.append(" AND (ci_sp_mtr_hist.removal_dttm is null OR ci_sp_mtr_hist.removal_dttm = (SELECT MAX(to_date(decode(to_char(ci_sp_mtr_hist.removal_dttm,'dd/mm/yyyy'),'',to_char(sysdate, 'dd/mm/yyyy'),null,to_char(sysdate, 'dd/mm/yyyy'),to_char(ci_sp_mtr_hist.removal_dttm,'dd/mm/yyyy')),'dd/mm/yyyy')) FROM ci_sp_mtr_hist WHERE ci_sp_mtr_hist.sp_id = ci_sp.sp_id))");
		sqlConsumerDetailsSB
				.append("               AND cm_cons_pre_bill_proc.mrkey = ? ");
		sqlConsumerDetailsSB
				.append("               AND cm_cons_pre_bill_proc.bill_round_id = ?) X ");
		sqlConsumerDetailsSB.append("ORDER  BY To_number(seq) ASC ");
		return sqlConsumerDetailsSB.toString();
	}

	/**
	 * <p>
	 * This is the SQL query to Update Meter Read Details to the data base sent
	 * by Hand Held Device(HHD) via Web Service call. This SQL query takes the
	 * details as parameter to update records with respect to the Account ID
	 * <code>ACCT_ID</code> and Bill Round ID <code>BILL_ROUND_ID</code> to
	 * table <code>cm_cons_pre_bill_proc</code> in the data base.
	 * </p>
	 * 
	 * @see MeterReadDAO#saveMeterRead
	 * @return String
	 */
	public static String mrdUploadQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update cm_cons_pre_bill_proc t set ");
		querySB.append(" t.cons_pre_bill_stat_id=?, ");
		querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
		querySB.append(" t.reg_reading=?,");
		querySB
				.append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
		querySB.append(" t.reader_rem_cd=?,");
		querySB.append(" t.new_avg_read=?,");
		querySB.append(" t.new_no_of_floors=?,");
		/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		// querySB.append(" t.last_updt_dttm=sysdate,");
		// querySB.append(" t.last_updt_by=?");
		querySB.append(" t.data_entry_dttm=systimestamp,");
		querySB.append(" t.data_entry_by=?");
		/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		querySB.append(" where 1=1");
		querySB.append(" and t.cons_pre_bill_stat_id <> " + RECORD_FROZEN);
		querySB.append(" AND t.cons_pre_bill_stat_id <> " + BILL_GENERATED);
		/*
		 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.METER_REPLACEMENT_PARK_STATUS_CODE);
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.DATA_ENTRY_PARK_STATUS_CODE);
		/*
		 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND  ACCT_ID=?");
		querySB.append(" AND  BILL_ROUND_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to get consumer deatils based on ACCT_ID and consumer
	 * pre bill proc status is less than 65. Modified : Edited by Madhuri Singh
	 * 22-11-2015 as per Jtrac DJB-4604 :- Users to be restricted as per access
	 * groups in Single Consumer Bill Generation screen.
	 * </p>
	 */
	public static String queryToGetConsumerDetlBasedonKNO() {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT NVL(TRIM(X.SVCCYCRTESEQ), '9999999') SEQ,");

		querySB.append(" X.PREBILLSTATID,");

		querySB.append(" X.ACCTID,");
		querySB.append(" X.BILLROUND_ID,");
		querySB.append(" X.SUBZONE_CD,");
		querySB.append(" X.AREA_DESC,");

		querySB.append(" X.BILL_ID,");

		querySB.append(" X.CONNAME,");
		querySB.append(" X.WCNO,");
		querySB.append(" X.CUSTCLCD CAT,");
		querySB.append(" TRIM(X.SATYPECD) SATYPECD,");
		querySB.append(" X.SVCCYCRTESEQ,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.LASTBILLGENDATE, 'DD-MON-YYYY')), 'NA') LASTBILLGENDATE,");
		querySB.append(" X.BILLROUND_ID,");
		querySB.append(" X.MRKEY,");
		querySB.append(" NVL(TRIM(X.AVGREAD), 'NA') AVGREAD,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ.READDATE, 'DD/MM/YYYY')), 'NA') PREVMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ.REGISTEREDREAD), 'NA') RGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ.REMARK), 'NA') MTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ.READTYPE), 'NA') READTYPE,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ1.READDATE, 'DD/MM/YYYY')), 'NA') ACTLMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ1.REGISTEREDREAD), 'NA') ACTLRGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ1.REMARK), 'NA') ACTLMTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ1.READTYPE), 'NA') ACTLREADTYPE,");
		querySB.append(" NVL(TRIM(X.BILLGENDATE), ' ') CURR_BILLGENDATE,");
		querySB.append(" NVL(TRIM(X.READERREMCD), ' ') CURR_READERREMCD,");
		querySB.append(" NVL(TRIM(X.REGREAD), ' ') CURR_REGREAD,");
		querySB.append(" NVL(TRIM(X.PREBILLSTATID), ' ') CURR_PREBILLSTATID,");
		querySB.append(" NVL(TRIM(X.NEWAVGREAD), ' ') CURR_NEWAVGREAD,");
		querySB.append(" NVL(X.PNFLOOR, 'NA') PNFLOOR,");
		querySB.append(" NVL(X.NWFLOOR, ' ') NWFLOOR,");
		querySB.append(" NVL(X.REMARKS, ' ') REMARKS");
		querySB.append(" FROM (SELECT CM_CONS_PRE_BILL_PROC.ACCT_ID ACCTID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID BILLROUND_ID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.AVG_READ AVGREAD,");

		querySB.append(" DJB_ZN_MR_AR_MRD.SUBZONE_CD  SUBZONE_CD,");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_DESC AREA_DESC,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ID BILL_ID,");
		querySB
				.append(" TO_CHAR(CM_CONS_PRE_BILL_PROC.BILL_GEN_DATE, 'DD/MM/YYYY') BILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CONSUMER_NAME CONNAME,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID PREBILLSTATID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CUST_CL_CD CUSTCLCD,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.LAST_BILL_GEN_DATE LASTBILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_BY LASTUPDTBY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_DTTM LASTUPDTTM,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MRKEY MRKEY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MTR_READER_NAME MTRRDRNAME,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_AVG_READ NEWAVGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READER_REM_CD READERREMCD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READ_TYPE_FLG READTYPFLG,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REG_READING REGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SA_TYPE_CD SATYPECD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SVC_CYC_RTE_SEQ SVCCYCRTESEQ,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.WCNO WCNO,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.PREV_NO_OF_FLOORS PNFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_NO_OF_FLOORS NWFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REMARKS REMARKS,");
		querySB
				.append(" CM_GET_PREV_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ,");
		querySB
				.append(" CM_GET_LAST_ACTUAL_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ1");
		querySB.append(" FROM CM_CONS_PRE_BILL_PROC,");
		querySB.append(" DJB_ZN_MR_AR_MRD,");
		querySB.append(" CM_MRD_PROCESSING_STAT,");
		// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB.append(" CI_DAR_USR");
		// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" WHERE CM_CONS_PRE_BILL_PROC.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_MRD_PROCESSING_STAT.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = CM_MRD_PROCESSING_STAT.MRKEY");
		// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY =TRIM(CI_DAR_USR.DAR_CD)");
		querySB.append(" AND trim(CI_DAR_USR.USER_ID) =?");
		// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = CM_MRD_PROCESSING_STAT.BILL_ROUND_ID");
		querySB.append(" AND CM_MRD_PROCESSING_STAT.MRD_STATS_ID = 10");

		// querySB.append("AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID < 65");

		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID NOT IN ("
						+ CONS_PRE_BILL_STAT_ID_NOT_IN + ")");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID = ?) X");
		// querySB.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = ?) X");
		querySB.append(" ORDER BY TO_NUMBER(SEQ)");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to get consumer deatils based on ACCT_ID and consumer
	 * pre bill proc status is greater than 65 Modified : Edited by Madhuri
	 * Singh 22-11-2015 as per Jtrac DJB-4604 :- Users to be restricted as per
	 * access groups in Single Consumer Bill Generation screen.
	 * </p>
	 */

	public static String queryToGetConsumerDetlBasedonKNOOther() {

		StringBuffer querySB = new StringBuffer();
		querySB.append(" SELECT NVL(TRIM(X.SVCCYCRTESEQ), '9999999') SEQ,");

		querySB.append(" X.PREBILLSTATID,");

		querySB.append(" X.ACCTID,");
		querySB.append(" X.BILLROUND_ID,");
		querySB.append(" X.SUBZONE_CD,");
		querySB.append(" X.AREA_DESC,");

		querySB.append(" X.BILL_ID,");

		querySB.append(" X.CONNAME,");
		querySB.append(" X.WCNO,");
		querySB.append(" X.CUSTCLCD CAT,");
		querySB.append(" TRIM(X.SATYPECD) SATYPECD,");
		querySB.append(" X.SVCCYCRTESEQ,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.LASTBILLGENDATE, 'DD-MON-YYYY')), 'NA') LASTBILLGENDATE,");
		querySB.append(" X.BILLROUND_ID,");
		querySB.append(" X.MRKEY,");
		querySB.append(" NVL(TRIM(X.AVGREAD), 'NA') AVGREAD,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ.READDATE, 'DD/MM/YYYY')), 'NA') PREVMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ.REGISTEREDREAD), 'NA') RGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ.REMARK), 'NA') MTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ.READTYPE), 'NA') READTYPE,");
		querySB
				.append(" NVL(TRIM(TO_CHAR(X.OBJ1.READDATE, 'DD/MM/YYYY')), 'NA') ACTLMTRRDDT,");
		querySB.append(" NVL(TRIM(X.OBJ1.REGISTEREDREAD), 'NA') ACTLRGMTRRD,");
		querySB.append(" NVL(TRIM(X.OBJ1.REMARK), 'NA') ACTLMTRRDSTAT,");
		querySB.append(" NVL(TRIM(X.OBJ1.READTYPE), 'NA') ACTLREADTYPE,");
		querySB.append(" NVL(TRIM(X.BILLGENDATE), ' ') CURR_BILLGENDATE,");
		querySB.append(" NVL(TRIM(X.READERREMCD), ' ') CURR_READERREMCD,");
		querySB.append(" NVL(TRIM(X.REGREAD), ' ') CURR_REGREAD,");
		querySB.append(" NVL(TRIM(X.PREBILLSTATID), ' ') CURR_PREBILLSTATID,");
		querySB.append(" NVL(TRIM(X.NEWAVGREAD), ' ') CURR_NEWAVGREAD,");
		querySB.append(" NVL(X.PNFLOOR, 'NA') PNFLOOR,");
		querySB.append(" NVL(X.NWFLOOR, ' ') NWFLOOR,");
		querySB.append(" NVL(X.REMARKS, ' ') REMARKS");
		querySB.append(" FROM (SELECT CM_CONS_PRE_BILL_PROC.ACCT_ID ACCTID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID BILLROUND_ID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.AVG_READ AVGREAD,");

		querySB.append(" DJB_ZN_MR_AR_MRD.SUBZONE_CD  SUBZONE_CD,");
		querySB.append(" DJB_ZN_MR_AR_MRD.AREA_DESC AREA_DESC,");

		querySB.append(" CM_CONS_PRE_BILL_PROC.BILL_ID BILL_ID,");

		querySB
				.append(" TO_CHAR(CM_CONS_PRE_BILL_PROC.BILL_GEN_DATE, 'DD/MM/YYYY') BILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CONSUMER_NAME CONNAME,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID PREBILLSTATID,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.CUST_CL_CD CUSTCLCD,");
		querySB
				.append(" CM_CONS_PRE_BILL_PROC.LAST_BILL_GEN_DATE LASTBILLGENDATE,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_BY LASTUPDTBY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.LAST_UPDT_DTTM LASTUPDTTM,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MRKEY MRKEY,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.MTR_READER_NAME MTRRDRNAME,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_AVG_READ NEWAVGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READER_REM_CD READERREMCD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.READ_TYPE_FLG READTYPFLG,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REG_READING REGREAD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SA_TYPE_CD SATYPECD,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.SVC_CYC_RTE_SEQ SVCCYCRTESEQ,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.WCNO WCNO,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.PREV_NO_OF_FLOORS PNFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.NEW_NO_OF_FLOORS NWFLOOR,");
		querySB.append(" CM_CONS_PRE_BILL_PROC.REMARKS REMARKS,");
		querySB
				.append(" CM_GET_PREV_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ,");
		querySB
				.append(" CM_GET_LAST_ACTUAL_METER_READ(CM_CONS_PRE_BILL_PROC.ACCT_ID) OBJ1");
		querySB.append(" FROM CM_CONS_PRE_BILL_PROC,");
		querySB.append(" DJB_ZN_MR_AR_MRD,");
		querySB.append(" CM_MRD_PROCESSING_STAT,");
		// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB.append(" CI_DAR_USR");
		// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" WHERE CM_CONS_PRE_BILL_PROC.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_MRD_PROCESSING_STAT.MRKEY = DJB_ZN_MR_AR_MRD.MRD_CD");
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = CM_MRD_PROCESSING_STAT.MRKEY");
		// Start : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.MRKEY = TRIM(CI_DAR_USR.DAR_CD)");
		querySB.append(" AND trim(CI_DAR_USR.USER_ID) = ?");
		// End : Edited by Madhuri Singh 22-11-2015 as per Jtrac DJB-4604.
		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = CM_MRD_PROCESSING_STAT.BILL_ROUND_ID");
		querySB.append(" AND CM_MRD_PROCESSING_STAT.MRD_STATS_ID = 10");

		querySB
				.append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID >= 65");

		// querySB
		// .append(" AND CM_CONS_PRE_BILL_PROC.CONS_PRE_BILL_STAT_ID IN ("
		// + CONS_PRE_BILL_STAT_ID_NOT_IN + ")");
		querySB.append(" AND CM_CONS_PRE_BILL_PROC.ACCT_ID = ?) X");
		// querySB.append(" AND CM_CONS_PRE_BILL_PROC.BILL_ROUND_ID = ?) X");
		querySB.append(" ORDER BY TO_NUMBER(SEQ)");

		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to update new route sequences ,service cycle and service route into ci_sp
	 * table of database.
	 * </p>
	 * 
	 * @return String
	 */
	public static String renumberRouteSequencesUpdateQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE CI_SP SET MR_CYC_RTE_SEQ=?, MR_CYC_CD='"
				+ DJBConstants.SERVICE_CYCLE + "' ,  MR_RTE_CD='"
				+ DJBConstants.SERVICE_ROUTE + "' WHERE SP_ID=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to reset Meter Replacement Detail. This is done by deleteing the
	 * entry from the staging table <code>CM_MTR_RPLC_STAGE</code> whose
	 * <code>MTR_RPLC_STAGE_ID</code> not in
	 * <code>MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED</code> value.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-03-2013
	 * @see MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED
	 * @see MeterReplacementDAO#resetMeterReplacementDetail(com.tcs.djb.model.MeterReplacementDetail)
	 * @return query String
	 */
	public static String resetMeterReplacementDetailQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" delete from CM_MTR_RPLC_STAGE ");
		querySB.append(" where 1=1 ");
		querySB.append(" AND MTR_RPLC_STAGE_ID NOT IN ("
				+ MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED + ")");
		querySB.append(" and acct_id=? ");
		querySB.append(" and bill_round_id=?  ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This query is used to insert AMR Excel data details
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String saveAMRExcelDataQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("INSERT INTO CM_AMR_RECORD_DETAILS ");
		querySB.append("  (HEADER_ID, ");
		querySB.append("   SEQ, ");
		querySB.append("   KNO, ");
		querySB.append("   CONSUMER_NAME, ");
		querySB.append("   CONSUMER_ADDRESS, ");
		querySB.append("   CONSUMER_CATEGORY, ");
		querySB.append("   UNOC_DWEL_UNITS, ");
		querySB.append("   OCU_DWEL_UNITS, ");
		querySB.append("   TOTL_DEWL_UNITS, ");
		querySB.append("   NO_OF_FLOOR, ");
		querySB.append("   NO_OF_FUNC_SITES_BEDS_ROOMS, ");
		querySB.append("   MTR_NUMBER, ");
		querySB.append("   MTR_READ_DATE, ");
		querySB.append("   MTR_STATUS_CODE, ");
		querySB.append("   MTR_READ, ");
		querySB.append("   CONS, ");
		querySB.append("   EFFEC_NO_OF_DAYS, ");
		querySB.append("   AVG_CONS, ");
		querySB.append("   CURR_MTR_READ_DATE, ");
		querySB.append("   MTR_READ_TYPE, ");
		querySB.append("   CURRENT_AVG_CONS, ");
		querySB.append("   CURRENT_MTR_READING, ");
		querySB.append("   CURRENT_MTR_STATUS_CODE, ");
		querySB.append("   CURRENT_NO_OF_FLOORS, ");
		querySB.append("   BILL_ROUND_ID, ");
		querySB.append("   MRKEY) ");
		querySB.append("VALUES ");
		querySB
				.append("  ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?,?,? )");

		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to get DOC Attach List map for New Connection Fle Entry Screen as
	 * per JTrac DJB-4313
	 * </p>
	 * 
	 * @return String
	 * @author Rajib Hazarika (Tata Consultancy Services)
	 * @since 19-JAN-2016
	 */
	public static String saveNewConnFileDetailsQuery(
			NewConnFileEntryDetails newConnFileEntryDetails) {
		StringBuffer querySB = new StringBuffer();
		querySB.append("INSERT INTO CM_NEW_CONN_FILE_ENTRY_STG ( ");
		querySB.append(" FILE_NO,");
		querySB.append(" APPL_TYPE_CD ,");
		querySB.append(" FIRST_NAME,");
		querySB.append(" PROOF_OF_IDENTITY_CD ,");
		querySB.append(" PROPERTY_OWN_DOC_CD,");
		querySB.append(" PIN ,");
		querySB.append(" AREA_CD ,");
		querySB.append(" SUB_AREA_CD ,");
		querySB.append(" HOUSE_NO ,");
		querySB.append(" STATUS_CD ,");
		querySB.append(" CREATED_BY, ");
		querySB.append(" ZRO_CD ");
		if (null != newConnFileEntryDetails.getMiddleName()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getMiddleName())) {
			querySB.append(", MIDDLE_NAME ");
		}

		if (null != newConnFileEntryDetails.getLastName()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getLastName())) {
			querySB.append(",LAST_NAME ");
		}
		if (null != newConnFileEntryDetails.getPhone()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getPhone())) {
			querySB.append(", PH_NO ");
		}
		if (null != newConnFileEntryDetails.getEmailId()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getEmailId())) {
			querySB.append(", EMAILID");
		}
		if (null != newConnFileEntryDetails.getProofOfRes()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getProofOfRes())) {
			querySB.append(",PROOF_OF_RES_CD");
		}
		if (null != newConnFileEntryDetails.getJjr()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getJjr())) {
			querySB.append(", JJ_FLAG");
		}
		if (null != newConnFileEntryDetails.getRoadNo()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getRoadNo())) {
			querySB.append(",ROAD_NO");
		}
		if (null != newConnFileEntryDetails.getSubLoc1Add()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getSubLoc1Add())) {
			querySB.append(",SUBLOC1");
		}
		if (null != newConnFileEntryDetails.getSubLoc2Add()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getSubLoc2Add())) {
			querySB.append(",SUBLOC2");
		}
		if (null != newConnFileEntryDetails.getSubColAdd()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getSubColAdd())) {
			querySB.append(",SUB_COLONY");
		}
		if (null != newConnFileEntryDetails.getVilAdd()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getVilAdd())) {
			querySB.append(",VILLAGE");
		}
		querySB.append(")");
		querySB.append(" VALUES ( ");
		querySB.append(" ?,?,?,?,?,?,?,?,?,?,?, ");
		querySB
				.append(" (SELECT DISTINCT ZRO_CD FROM CM_NEW_CONN_FILE_MASTER M WHERE  ");
		querySB.append(newConnFileEntryDetails.getFileName());
		querySB.append("  BETWEEN M.MIN_VAL AND M.MAX_VAL) ");
		if (null != newConnFileEntryDetails.getMiddleName()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getMiddleName())) {
			querySB.append(", ? ");
		}

		if (null != newConnFileEntryDetails.getLastName()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getLastName())) {
			querySB.append(",? ");
		}
		if (null != newConnFileEntryDetails.getPhone()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getPhone())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getEmailId()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getEmailId())) {
			querySB.append(", ?");
		}
		if (null != newConnFileEntryDetails.getProofOfRes()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getProofOfRes())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getJjr()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getJjr())) {
			querySB.append(", ?");
		}
		if (null != newConnFileEntryDetails.getRoadNo()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getRoadNo())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getSubLoc1Add()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getSubLoc1Add())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getSubLoc2Add()
				&& !""
						.equalsIgnoreCase(newConnFileEntryDetails
								.getSubLoc2Add())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getSubColAdd()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getSubColAdd())) {
			querySB.append(",?");
		}
		if (null != newConnFileEntryDetails.getVilAdd()
				&& !"".equalsIgnoreCase(newConnFileEntryDetails.getVilAdd())) {
			querySB.append(",?");
		}

		querySB.append(") ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert details of downloaded file in djb_hhd_mrd_dwnld_stat.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 03-JUNE-2016
	 * @return query String
	 * @as per Jtrac DJB-4464 and Open project id - 1203
	 */
	public static String setFileDetails() {

		StringBuffer detailsEntry = new StringBuffer();
		detailsEntry.append("insert into djb_hhd_mrd_dwnld_stat ");
		detailsEntry.append("		  (MRKEY, ");
		detailsEntry.append("		   BILL_ROUND_ID, ");
		detailsEntry.append("		   REQ_SRC, ");
		detailsEntry.append("		   VERSION_NO, ");
		detailsEntry.append("		   DWNLD_DT) ");
		detailsEntry.append("		   values ");
		detailsEntry.append("		  (?, ");
		detailsEntry.append("		   ?, ");
		detailsEntry.append("		   'DataEntryApp', ");
		detailsEntry.append("		   1, ");
		detailsEntry.append("		   SYSDATE)");

		return detailsEntry.toString();

	}

	/**
	 * <p>
	 * Query to Set track of file's action status.
	 * </p>
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 01-02-2016
	 * @param FileNo
	 * @param actionStatus
	 * @return query String
	 * @as per Jtrac DJB-4359 and DJB-4365
	 */
	public static String setFileStatusTracker(String FileNo,
			String actionStatus, String userId) {
		AppLog.begin();
		// Start : Edited by Arnab Nandy 15-02-2015 as per Jtrac DJB-4359.
		// Map<String, Object> session =
		// ActionContext.getContext().getSession();
		// String userId = (String) session.get("userId");
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" insert into cm_file_status_track (FILE_NO,STATUS,CREATED_DTTM,CREATED_BY) values(");

		if (null != FileNo && !"".equalsIgnoreCase(FileNo)) {
			querySB.append("   '" + FileNo + "', ");
		}
		if (null != actionStatus && !"".equalsIgnoreCase(actionStatus)) {
			querySB.append("   '" + actionStatus + "',");
		}
		querySB.append("sysdate,'" + userId + "')");

		AppLog.info(querySB.toString());
		AppLog.end();
		// End : Edited by Arnab Nandy 15-02-2015 as per Jtrac DJB-4359.

		return querySB.toString();
	}

	/**
	 * <p>
	 * This is a query used to insert new booklet
	 * </p>
	 * 
	 * @param selectedZROLocation
	 * @param minRange
	 * @param maxRange
	 * @param userId
	 * @return
	 */
	public static String setNewBooklet(String selectedZROLocation,
			String minRange, String maxRange, String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" insert into cm_new_conn_file_master (ZRO_CD,MIN_VAL,MAX_VAL,CREATED_BY,CREATED_DTTM) values (");
		if (null != selectedZROLocation
				&& !"".equals(selectedZROLocation.trim())) {
			querySB.append(" '" + selectedZROLocation + "'");
		}

		querySB.append(" ," + minRange);
		querySB.append(" ," + maxRange);

		if (null != userId && !"".equals(userId.trim())) {
			querySB.append(",'" + userId + "'");
		}
		querySB.append(" ,sysdate)");

		AppLog.info(querySB.toString());
		return querySB.toString();
	}

	/**
	 * This Method returns query to check different parameters to be inserted
	 * into the table cm_new_conn_file_master as per jTrac DJB-4442 and
	 * OpenProject-CODE DEVELOPMENT #867
	 * 
	 * @author Lovely (Tata Consultancy Services)
	 * @since 27-05-2016
	 */

	public static String setNewBookletQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" insert into cm_new_conn_file_master (ZRO_CD,MIN_VAL,MAX_VAL,CREATED_BY,CREATED_DTTM) values (?, ?, ?, ?, SYSDATE)");

		return querySB.toString();

	}

	/**
	 * <p>
	 * Query to insert online consumer details for kno generation as per as per
	 * JTrac DJB-4541 ,Open project-1443.
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 22-08-2016
	 * @return query String
	 */
	public static String tagfileInsertDetails() {
		AppLog.begin();
		StringBuffer querySb = new StringBuffer();
		querySb.append("INSERT INTO CM_NEW_CONN_FILE_ENTRY_STG ");
		querySb.append("  (FILE_NO, ");
		querySb.append("   APPL_TYPE_CD, ");
		querySb.append("   FIRST_NAME, ");
		querySb.append("   PH_NO, ");
		querySb.append("   HOUSE_NO, ");
		querySb.append("   ARN_NO, ");
		querySb.append("   STATUS_CD, ");
		querySb.append("   CREATED_BY, ");
		querySb.append("   CREATED_DTTM, ");
		querySb.append("   ZRO_CD, ");
		querySb.append("   PLOT_AREA, ");
		querySb.append("   PURPOSE_OF_APPL, ");
		querySb.append("   ONLINE_CONSUMER_FLAG) ");
		querySb.append("VALUES ");
		querySb.append("  (?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  SYSDATE, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?, ");
		querySb.append("  ?)");
		AppLog.info(querySb.toString());
		AppLog.end();
		return querySb.toString();
	}

	/**
	 * <p>
	 * Query to fetch Zro location of a file number as per as per JTrac DJB-4541
	 * ,Open project-1443..
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 22-08-2016
	 * @return query String
	 */
	public static String tagFileZroLocation() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append("SELECT ZRO_CD FROM CM_NEW_CONN_FILE_MASTER  WHERE ");
		querySB.append(" 1=1 ");
		querySB.append("AND ? >= MIN_VAL ");
		querySB.append("AND ? <= MAX_VAL");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * This Method Returns query to update Record Details For File Search
	 * 
	 * @author Arnab Nandy(Tata Consultancy Services)
	 * @since 27-01-2016
	 * @param selectedFileNo
	 * @param actionStatus
	 * @return
	 * @as per Jtrac DJB-4359 and DJB-4365
	 */
	// public static String updateActionStatusforFiles(String selectedFileNo,
	// String actionStatus) {
	public static String updateActionStatusforFiles(String selectedFileNo,
			String actionStatus, String userId) {
		/*
		 * String selectedFNOArray[] = selectedFileNo.split(","); StringBuffer
		 * querySB = new StringBuffer(); int st_cd =
		 * Integer.parseInt(actionStatus);
		 * querySB.append("UPDATE CM_NEW_CONN_FILE_ENTRY_STG D SET "); if (null
		 * != actionStatus && !"".equalsIgnoreCase(actionStatus)) {
		 * querySB.append("       status_cd      = '" + actionStatus + "'"); }
		 * if (null != selectedFileNo && !"".equalsIgnoreCase(selectedFileNo)) {
		 * querySB.append("   WHERE    file_no in ( "); for (int i = 0; i <
		 * selectedFNOArray.length; i++) { String tempString =
		 * selectedFNOArray[i]; if (null != tempString &&
		 * !"".equalsIgnoreCase(tempString)) { querySB.append("   '" +
		 * tempString + "', "); } } querySB.append("   '') "); } if (null !=
		 * actionStatus && !"".equalsIgnoreCase(actionStatus)) {
		 * querySB.append("    AND   status_cd      < " + st_cd); }
		 */

		// Start : Edited by Arnab Nandy 16-02-2015 as per Jtrac DJB-4359 and
		// DJB-4365.
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE CM_NEW_CONN_FILE_ENTRY_STG D SET ");
		querySB.append("       status_cd      = '" + actionStatus + "'");
		querySB.append("  ,LAST_UPDT_BY='" + userId
				+ "',LAST_UPDT_DTTM=sysdate WHERE    file_no = ");
		querySB.append("'" + selectedFileNo + "' ");
		querySB.append("  AND LOT_NO is not null ");
		AppLog.info(querySB.toString());
		AppLog.end();
		// End : Edited by Arnab Nandy 16-02-2015 as per Jtrac DJB-4359 and
		// DJB-4365.
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update allocated file number to users as per jTrac
	 * DJB-4571,Openproject- #1492 .
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 19-09-2016
	 * @return query String
	 */
	public static String updateAllocatedFileToUserQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();

		querySB
				.append("UPDATE  CM_FILE_RANGE_MAP_EMP SET MAX_VAL=?,LAST_UPDT_BY=?,LAST_UPDT_DTTM=SYSDATE WHERE USER_ID=? AND ZRO_CD=?");

		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to update AMR records details
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static String updateAmrRecordDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("UPDATE CM_AMR_RECORD_DETAILS A SET A.REMARKS = ? WHERE A.KNO = ? AND A.BILL_ROUND_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to update AMR staging table details
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 */
	public static String updateAMRStagingTableQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("UPDATE CM_AMR_RECORD_DETAILS A SET A.REMARKS = ? WHERE A.HEADER_ID = ?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to update billing remarks
	 * </p>
	 * 
	 * @return
	 */
	public static String updateBillingRemarks() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("update CM_CONS_PRE_BILL_PROC set BILLING_REMARKS = ?  where  ACCT_ID = ? and BILL_ROUND_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This is query used to update CM_CONS_PRE_BILL_PROC table
	 * </p>
	 * 
	 * @return
	 * @author Atanu Mondal(Tata Consultancy Services)
	 * @since 20-06-2016 as per open project 1202,jTrac : DJB-4464
	 */
	public static String updateConsPreBillProcQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE CM_CONS_PRE_BILL_PROC P ");
		querySB.append("      SET P.CONS_PRE_BILL_STAT_ID = ?, ");
		querySB.append("          P.REMARKS               = ?, ");
		querySB.append("          P.LAST_UPDT_DTTM        = SYSTIMESTAMP, ");
		querySB.append("          P.LAST_UPDT_BY          = ? ");
		querySB.append("          WHERE P.ACCT_ID = ? ");
		querySB.append("          AND P.BILL_ROUND_ID = ?");
		/*
		 * Start : Lovely on 05-08-2016 added the below logic in Query as jTrac
		 */
		querySB.append("          AND (P.BILL_ID is null OR P.BILL_ID = '') ");
		querySB.append("          AND P.PROCESS_COUNTER = 0 ");
		/* End : Lovely on 05-08-2016 added the below logic in Query as jTrac : */
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update Demand Transfer Process Status from PENDING to IN
	 * PROGRESS.
	 * </p>
	 * 
	 * @return
	 */
	public static String updateDemandTransferProcessStatus() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" UPDATE CM_DMND_TRNSFR_STAGE T SET T.STAT_FLG='IN PROGRESS',T.LAST_UPDT_BY=?,T.LAST_UPDT_DTTM=sysdate WHERE T.STAT_FLG='PENDING'");
		querySB
				.append(" AND T.ACCT_ID=? AND T.OLD_MRKEY=? AND T.NEW_MRKEY=? AND T.CREATED_BY=? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Update Generated BILL_ID value in CM_NEW_CONN_FILE_ENTRY_STG
	 * table as per Jtrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 13-02-2016
	 * @return query String
	 */
	public static String updateGeneratedBillQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" UPDATE CM_NEW_CONN_FILE_ENTRY_STG S SET S.STATUS_CD=?, S.BILL_ID=?, S.LAST_UPDT_BY=?,S.BILL_GEN_BY=?,S.BILL_GEN_ON= SYSDATE, ");
		querySB.append(" S.LAST_UPDT_DTTM= SYSDATE  WHERE S.ACCT_ID=?  ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to Update Generated KNO value in CM_NEW_CONN_FILE_ENTRY_STG table
	 * as per Jtrac DJB-4313.
	 * </p>
	 * 
	 * @author Rajib Hazarika(Tata Consultancy Services)
	 * @since 13-02-2016
	 * @return query String
	 * @history: On 31-MAR-2016 Rajib Hazarika (682667) Modified below method to
	 *           update purposeOfAppl as per JTrac DJB-4429
	 */
	public static String updateGeneratedKnoQuery() {
		StringBuffer querySB = new StringBuffer();
		/* Start : Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 */
		querySB
				.append(" UPDATE CM_NEW_CONN_FILE_ENTRY_STG S SET S.STATUS_CD=?, S.ACCT_ID=?, S.LAST_UPDT_BY=?,S.ACCT_GEN_BY=?,S.ACCT_GEN_ON= SYSDATE, ");
		querySB
				.append(" S.LAST_UPDT_DTTM= SYSDATE,S.PLOT_AREA=?,S.PLOT_AREA_OLD=?,PURPOSE_OF_APPL=?,PURPOSE_OF_APPL_OLD=?  WHERE S.FILE_NO=? AND S.ARN_NO=?  ");
		return querySB.toString();
		/* End : Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 */
	}

	/**
	 * <p>
	 * This query is used to update HHD Credentails Details
	 * 
	 * </p>
	 * 
	 * @author Karthick K(Tata Consultancy Services)
	 * @param prevValueHhdId
	 * @param toHHDiD
	 * @param toPassword
	 * @param userName
	 * @return
	 */
	public static String updateHHDCredentailsDetailsQuery(
			String prevValueHhdId, String toHHDiD, String toPassword,
			String userName) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE djb_web_service_users T SET ");
		querySB.append(" T.USER_ID=?,");
		querySB.append(" T.USER_PASSWORD =?,");
		querySB.append(" T.LAST_UPDT_DTTM=sysdate" + ", ");
		querySB.append(" T.Last_Updt_By =?");
		querySB.append(" WHERE T.USER_ID =? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To update HHD Master Details by Updating column of
	 * <code>HHD_MASTER</code> table of database..
	 * </p>
	 * 
	 * @param blocked
	 * @param simId
	 * @param hhdId
	 * @param simNo
	 */
	public static String updateHHDMasterDetailsQuery(String hhdId,
			String blocked, String simId, String simNo, String userName) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE HHD_MASTER SET BLOCKED='" + blocked
				+ "',SIM_ID='" + simId + "', ");
		querySB.append(" SIM_NO='" + simNo
				+ "',UPDATE_TIME=SYSDATE,UPDATED_BY='" + userName + "' ");
		querySB.append(" WHERE HHD_ID='" + hhdId + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To update HHD Meter Reader Mapping Details by Updating column
	 * <code>EFF_TO_DATE<code> table
	 * <code>DJB_MTR_RDR_MRD</code> of with System Date -1.
	 * </p>
	 * 
	 * @param mrKey
	 * @param meterReader
	 * @param hhdId
	 * @return update HHD MeterReader MappingDetails Query String
	 */
	public static String updateHHDMeterReaderMappingDetailsQuery(
			String toupdateMRKey, String previousMeterReader,
			String previousHHDId, String userName) {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" UPDATE DJB_MRD_RDR_HHD T SET T.EFF_TO_DATE=SYSDATE-1,T.LAST_UPDT_DTTM=sysdate, ");
		querySB.append(" T.LAST_UPDT_BY='" + userName + "'");
		querySB.append(" WHERE T.MRD_CD= '" + toupdateMRKey + "'");
		querySB
				.append(" AND (T.EFF_TO_DATE IS NULL OR SYSDATE < T.EFF_TO_DATE)");
		if (null != previousMeterReader
				&& !"".equalsIgnoreCase(previousMeterReader.trim())) {
			querySB.append(" AND T.MTR_RDR_CD='" + previousMeterReader + "'");
		}
		if (null != previousHHDId && !"".equalsIgnoreCase(previousHHDId.trim())) {
			querySB.append(" AND T.HHD_ID='" + previousHHDId + "'");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * To update HHD User Master Details by Updating column of
	 * <code>HHD_USER_MASTER</code> table of database.
	 * </p>
	 * 
	 * @param password
	 * @param userLoginCount
	 * @param userMasterId
	 * @param userId
	 * @param hhdId
	 */
	public static String updateHHDUserMasterDetailsQuery(String userMasterId,
			String password, String userLoginCount, String hhdId, String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE HHD_USER_MASTER SET PASSWORD='" + password
				+ "',USR_LOGIN_COUNT='" + userLoginCount + "', ");
		querySB.append("  UPDATE_TIME=SYSDATE,UPDATED_BY='" + userId + "' ");
		querySB.append(" WHERE USER_ID='" + userMasterId + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * To update HHD Version Master Details by Updating column of
	 * <code>HHD_VERSION_MASTER</code> table of database.
	 * </p>
	 * 
	 * @param ftpLocation
	 * @param userId
	 * @param versionNo
	 */
	public static String updateHHDVersionMasterDetailsQuery(String versionNo,
			String ftpLocation, String ftpUserName, String ftpPassword,
			String userId) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE HHD_VERSION_MASTER SET VERSION_NO='"
				+ versionNo + "',FTP_LOCATION='" + ftpLocation + "',FTP_USER='"
				+ ftpUserName + "' ,");
		querySB.append("  FTP_PASSWORD='" + ftpPassword
				+ "', UPDATE_TIME=SYSDATE,UPDATED_BY='" + userId + "' ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to update Record Details For File Search
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 28-01-2016
	 * @param selectedFileNo
	 * @param lotNo
	 * @return
	 */
	public static String updateLotNumberforFiles(String selectedFileNo,
			String lotNo) {
		String selectedFNOArray[] = selectedFileNo.split(",");
		StringBuffer querySB = new StringBuffer();
		querySB.append("UPDATE CM_NEW_CONN_FILE_ENTRY_STG D SET ");
		if (null != lotNo && !"".equalsIgnoreCase(lotNo)) {
			querySB.append("       LOT_NO      = '" + lotNo + "'");
		}
		if (null != selectedFileNo && !"".equalsIgnoreCase(selectedFileNo)) {
			querySB.append("   WHERE    file_no in ( ");
			for (int i = 0; i < selectedFNOArray.length; i++) {
				String tempString = selectedFNOArray[i];
				if (null != tempString && !"".equalsIgnoreCase(tempString)) {
					querySB.append("   '" + tempString + "', ");
				}

			}
			querySB.append("   '') ");
			querySB.append("  and LOT_NO is null ");
		}
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update Meter Read Details to cm_cons_pre_bill_proc table for
	 * processing by billing batch.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 18-03-2013
	 * @see SetterDAO#saveMeterRead
	 * @see SetterDAO#flagConsumerStatus(java.util.Map)
	 * @return query String
	 */
	public static String updateMeterReadDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update cm_cons_pre_bill_proc t set ");
		querySB.append(" t.cons_pre_bill_stat_id=?, ");
		querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
		querySB.append(" t.reg_reading=?,");
		querySB
				.append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
		querySB.append(" t.reader_rem_cd=?,");
		querySB.append(" t.new_avg_read=?,");
		querySB.append(" t.new_no_of_floors=?,");
		querySB.append(" t.remarks=?, ");
		/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		// querySB.append(" t.last_updt_dttm=sysdate,");
		// querySB.append(" t.last_updt_by=?");
		/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.last_updt_dttm=sysdate,");
		querySB.append(" t.last_updt_by=?, ");
		/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.data_entry_dttm=systimestamp,");
		querySB.append(" t.data_entry_by=?");
		/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		querySB.append(" where 1=1 AND t.cons_pre_bill_stat_id <> "
				+ BILL_GENERATED);
		querySB.append(" AND t.cons_pre_bill_stat_id <> " + RECORD_FROZEN);
		/*
		 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.METER_REPLACEMENT_PARK_STATUS_CODE);
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.DATA_ENTRY_PARK_STATUS_CODE);
		/*
		 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND  ACCT_ID=?");
		querySB.append(" AND  BILL_ROUND_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * This method is used to update Meter Read Details Based On KNO.
	 * </p>
	 */
	public static String updateMeterReadDetailsQueryBasedOnKNO() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" update cm_cons_pre_bill_proc t set ");
		querySB.append(" t.cons_pre_bill_stat_id=?, ");
		querySB.append(" t.bill_gen_date=to_date(?,'dd/mm/yyyy'),");
		querySB.append(" t.reg_reading=?,");
		querySB
				.append(" t.READ_TYPE_FLG=(select MTR_READ_TYPE_CD from CM_MTR_STATS_LOOKUP where TRIM(MTR_STATS_CD)=?),");
		querySB.append(" t.reader_rem_cd=?,");
		querySB.append(" t.new_avg_read=?,");
		querySB.append(" t.new_no_of_floors=?,");
		querySB.append(" t.remarks=?, ");
		/* Start:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		// querySB.append(" t.last_updt_dttm=sysdate,");
		// querySB.append(" t.last_updt_by=?");
		/* Start:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.last_updt_dttm=sysdate,");
		querySB.append(" t.last_updt_by=?, ");
		/* End:04-01-2014 Changed by Karthick K as per JTrac DJB-2005 */
		querySB.append(" t.data_entry_dttm=systimestamp,");
		querySB.append(" t.data_entry_by=?");
		/* End:18-09-2013 Changed by Matiur Rahman as per JTrac DJB-1871 */
		querySB.append(" where 1=1 AND t.cons_pre_bill_stat_id <> "
				+ BILL_GENERATED);
		querySB.append(" AND t.cons_pre_bill_stat_id <> " + RECORD_FROZEN);
		/*
		 * Start:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.METER_REPLACEMENT_PARK_STATUS_CODE);
		querySB.append(" AND t.cons_pre_bill_stat_id <> "
				+ DJBConstants.DATA_ENTRY_PARK_STATUS_CODE);
		/*
		 * End:07-03-2014 JTrac ID#DJB-2024 Added by Matiur Rahman for the new
		 * validation to prevent update if the records is parked for processing.
		 */
		querySB.append(" AND  ACCT_ID=?");
		querySB.append(" AND  BILL_ROUND_ID=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update Meter Replacement Detail.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 15-03-2013
	 * @see MeterReplacementDAO#saveMeterReplacementDetail
	 * @return query String
	 */
	public static String updateMeterReplacementDetailQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE CM_MTR_RPLC_STAGE ");
		querySB.append(" SET ZONECD                = ?,");
		querySB.append(" MRNO                  = ?,");
		querySB.append(" AREANO                = ?,");
		querySB.append(" WCNO                  = ?,");
		querySB.append(" CONSUMER_NAME         = ?,");
		querySB.append(" CUST_CL_CD            = ?,");
		querySB.append(" MTR_RPLC_STAGE_ID     = ?,");
		querySB.append(" WCONSIZE              = ?,");
		querySB.append(" WCONUSE               = ?,");
		querySB.append(" SA_TYPE_CD            = ?,");
		querySB.append(" MTR_READER_ID         = ?,");
		querySB.append(" MTRTYP                = ?,");
		querySB.append(" MTR_INSTALL_DATE      = TO_DATE(?, 'DD/MM/YYYY'),");
		querySB.append(" READER_REM_CD         = ?,");
		querySB.append(" BADGE_NBR             = ?,");
		querySB.append(" MFG_CD                = ?,");
		querySB.append(" MODEL_CD              = ?,");
		querySB.append(" MTR_START_READ        = ?,");
		querySB.append(" MTR_START_READ_REMARK = ?,");
		querySB.append(" IS_LNT_SRVC_PRVDR     = ?,");
		querySB.append(" OLD_SA_TYPE_CD        = ?,");
		querySB.append(" OLD_SA_START_DATE     = to_date(?, 'dd/mm/yyyy'),");
		querySB.append(" OLD_MTR_READ_DATE     = to_date(?, 'dd/mm/yyyy'),");
		querySB.append(" OLD_MTR_READ          = ?,");
		querySB.append(" OLDAVGCONS            = ?,");
		querySB.append(" LAST_UPDT_DTTM        = SYSDATE,");
		querySB.append(" LAST_UPDT_BY          = ?,");
		/**
		 * Start:21-03-2013 Matiur Rahman Added for populating Operation Area
		 * Code OP_AREA_CD
		 */
		querySB
				.append(" OP_AREA_CD		   = (SELECT TRIM(OP_AREA_CD) OP_CODE FROM CI_SP_OP_AREA INNER JOIN CI_SA_SP ON CI_SA_SP.SP_ID = CI_SP_OP_AREA.SP_ID INNER JOIN CI_SA ON CI_SA.SA_ID = CI_SA_SP.SA_ID WHERE CI_SA.SA_STATUS_FLG = 20 AND CI_SA.SA_TYPE_CD IN ('SAWATR','SAWATSEW','UNMTRD') AND CI_SA.ACCT_ID = ?),");

		/**
		 * End:21-03-2013 Matiur Rahman Added for populating Operation Area Code
		 * OP_AREA_CD
		 */
		querySB.append(" DE_CODE_VRSN='" + DE_CODE_VERSION + "'");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND MTR_RPLC_STAGE_ID NOT IN ("
				+ MTR_RPLC_STAGE_ID_NOT_TO_BE_UPDATED + ")");
		querySB.append(" AND ACCT_ID = ?");
		querySB.append(" AND BILL_ROUND_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update Meter Replacement Processing Detail.
	 * </p>
	 * 
	 * @author Matiur Rahman(Tata Consultancy Services)
	 * @since 11-03-2014
	 * @see MeterReplacementDAO#saveMeterReplacementDetail
	 * @return query String
	 */
	public static String updateMeterReplacementProcessingDetailsQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE CM_MTR_RPLC_STAGE ");
		querySB.append(" SET LAST_UPDT_DTTM = SYSTIMESTAMP,");
		querySB.append(" LAST_UPDT_BY = ?,");
		querySB.append(" PROCESSED_DTTM = SYSTIMESTAMP,");
		querySB.append(" PROCESSED_BY = ?");
		querySB.append(" WHERE 1=1 ");
		querySB.append(" AND ACCT_ID = ?");
		querySB.append(" AND BILL_ROUND_ID = ? ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update mrd transfer details in cm_mrd_transfr_stg in IN PROGRESS
	 * state as per Jtrac DJB-2200
	 * </p>
	 * 
	 * @author Madhuri Singh(Tata Consultancy Services)
	 * @since 06-09-2016
	 * @return query String
	 */
	public static String updateMrdTransferProcessStatusQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("UPDATE CM_MRD_TRANSFR_STG T SET T.STATUS='IN PROGRESS',T.LAST_UPDATE_BY=?,T.LAST_UPDATE_DTTM=SYSDATE WHERE T.STATUS='PENDING' ");
		querySB.append(" AND T.MRD_CD=?");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update MRKey Status Code with the newMRKeyStatusCode updated
	 * from the Operation MR Key Review screen.
	 * </p>
	 * 
	 * @param mrKeyList
	 * @return query String
	 */
	public static String updateMRKeyStatusCodeQuery(String mrKeyList) {
		StringBuffer querySB = new StringBuffer();
		querySB.append(" UPDATE CM_CONS_PRE_BILL_PROC A ");
		querySB.append(" SET A.CONS_PRE_BILL_STAT_ID = ?, ");
		querySB.append(" A.DATA_PARKED_DTTM = SYSDATE, A.DATA_PARKED_BY = ? ");
		querySB
				.append(" WHERE A.BILL_ROUND_ID = (SELECT B.BILL_ROUND_ID FROM CM_MRD_PROCESSING_STAT B WHERE B.MRKEY = A.MRKEY AND B.MRD_STATS_ID = "
						+ OPEN_BILL_ROUND_STATUS_CODE + " AND rownum = 1) ");
		/* Start:Added by Matiur Rahman on 26-07-2013 as per JTrac ID#DJB-1733 */
		querySB.append(" AND A.IS_LOCKED <> 'Y'");
		/* Endt:Added by Matiur Rahman on 26-07-2013 as per JTrac ID#DJB-1733 */
		querySB.append(" AND A.PROCESS_COUNTER = ? ");
		querySB.append(" AND A.CONS_PRE_BILL_STAT_ID = ? ");
		querySB.append(" AND A.MRKEY in (" + mrKeyList + ")");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to insert record in Cons PreBill Status Lookup Table.
	 * </p>
	 * 
	 * @author Tuhin Jana(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see PreBillStatusDefinitionDAO#updatePreBillStatusDefinitionList
	 * @return query String
	 */
	public static String updatePreBillStatusDefinitionListQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("INSERT INTO CONS_PRE_BILL_STAT_LOOKUP (CONS_PRE_BILL_STAT_ID,DESCR, LAST_UPDATED_BY,LAST_UPDATED_ON)VALUES (?, ?, ?, SYSDATE)");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to update data in DJB_RAIN_WTR_HRVSTING_STG_TBL .
	 * </p>
	 * 
	 * @author Aniket Chatterjee(Tata Consultancy Services)
	 * @since 23-09-2015
	 * @return query String
	 */

	public static String updatePremCharDetailsQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append(" UPDATE DJB_RAIN_WTR_HRVSTING_STG_TBL SET status_flg = ? , last_updt_by = ? , last_updt_dttm = sysdate WHERE  acct_id = ? AND status_flg = ? ");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to fetch COL_VAL from CM_DATA_MIGRATION_MAP as per JTrac DJB-4465
	 * and OpenProject-918. DJB-4465.
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 27-05-2016
	 * @return query String
	 */
	public static String validateFileHeaderQuery() {
		AppLog.begin();

		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COL_VAL  FROM CM_DATA_MIGRATION_MAP d WHERE d.MAP_KEY=? ORDER BY COL_NUMBER");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to validate number of column in uploaded excel sheet as per JTrac
	 * DJB-4465 and OpenProject-918. DJB-4465.
	 * </p>
	 * 
	 * @author Lovely(Tata Consultancy Services)
	 * @since 27-05-2016
	 * @return query String
	 */

	public static String validateFileQuery() {
		AppLog.begin();
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COL_VAL AS CNT FROM CM_DATA_MIGRATION_MAP d WHERE d.MAP_KEY = ?");
		AppLog.end();
		return querySB.toString();
	}

	/**
	 * <p>
	 * This Method Returns query to validate File Number Details For ZRO
	 * location
	 * </p>
	 * 
	 * @author Arnab Nandy (Tata Consultancy Services)
	 * @since 27-01-2016
	 * @param fileNo
	 * @param deZroCd
	 * @return
	 */
	public static String validateFNO(String fileNo, String deZroCd) {
		StringBuffer querySB = new StringBuffer();
		String selectedFNOArray[] = deZroCd.split(",");
		querySB
				.append("SELECT COUNT(1) as validstatus FROM CM_NEW_CONN_FILE_ENTRY_STG WHERE FILE_NO='"
						+ fileNo + "' and ZRO_CD in (");
		for (int i = 0; i < selectedFNOArray.length; i++) {
			String tempString = selectedFNOArray[i];
			if (null != tempString && !"".equalsIgnoreCase(tempString)) {
				querySB.append("   '" + tempString + "', ");
			}
		}
		querySB.append("   '') ");
		return querySB.toString();
	}

	/**
	 * <p>
	 * Query to validate Pre Bill Status Id Query.
	 * </p>
	 * 
	 * @author Tuhin Jana(Tata Consultancy Services)
	 * @since 12-03-2013
	 * @see PreBillStatusDefinitionDAO#updatePreBillStatusDefinitionList
	 * @return query String
	 */
	public static String validatePreBillStatusIdQuery() {
		StringBuffer querySB = new StringBuffer();
		querySB
				.append("SELECT COUNT(*) COUNT_CONS_PRE_BILL_STAT_ID FROM CONS_PRE_BILL_STAT_LOOKUP WHERE CONS_PRE_BILL_STAT_ID=? ");
		return querySB.toString();
	}
}
