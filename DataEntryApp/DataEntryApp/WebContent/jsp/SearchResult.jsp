<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%try{ %>
<head>
	<title>Revenue Management System,Delhi Jal Board</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="description" content="Revenue Management System,Delhi Jal Board ">
	<meta name="copyright" content="&copy;2012 Delhi Jal Board">
	<meta name="author" content="Tata Consultancy Services">
	<meta name="keywords" content="djb,DJB,Revenue Management System,Delhi Jal Board">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="googlebot" content="noarchive">
	<link rel="stylesheet" type="text/css" media="screen" href="<s:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<s:url value="/css/pagination.css"/>"/>
	<script type="text/javascript" src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.paginate.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/UtilityScripts.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/SearchResult.js"/>"></script>	
	<script type="text/javascript" src="<s:url value="/js/MRDMeterReplacement.js"/>"></script>	
	<script type="text/javascript">
		<%MRDReadTypeLookup mrdReadTypeLookup= (MRDReadTypeLookup)session.getAttribute("mrdReadTypeLookup");
		if(null!=mrdReadTypeLookup){%>		
			var averageReadType="<%=mrdReadTypeLookup.getAverageReadType()%>";
			var noBillingReadType="<%=mrdReadTypeLookup.getNoBillingReadType()%>";
			var provisionalReadType="<%=mrdReadTypeLookup.getProvisionalReadType()%>";
			var regularReadType="<%=mrdReadTypeLookup.getRegularReadType()%>";
		<%}%>
		var todaysDate="<%=(String)session.getAttribute("TodaysDate")%>";	
		var PREV_METER_READ_INGNORED_YEAR="<%=PropertyUtil.getProperty("PREV_METER_READ_INGNORED_YEAR")%>";	
		var meterReadDDText="";
		var meterReadMMText="";		
		var currentMeterReadText="";		
		var newAvgConsumptionText="";
		var newNoOfFloorText="";
		var searchZone="<%=session.getAttribute("searchZone") %>";
		var searchMRNo="<%=session.getAttribute("searchMRNo") %>";
		var searchArea="<%=session.getAttribute("searchArea") %>";
		var searchAreaCode="<%=session.getAttribute("searchAreaCode") %>";
 	</script>
 	<script type="text/javascript">
 		function fnOnSubmit(){
 			hideScreen();
 			return true;
 		}
 		function fnOnLoad(){
 	 		
 			setOnLoadValue();	
 		}
 		
 	</script>
</head>
<body onload="fnOnLoad();" oncontextmenu="return false;">
<%@include file="../jsp/CommonOverlay.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage" border="0">
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%>
					</td>
				</tr>				
				<tr>
					<td  valign="top">
						<div style="height:1px;overflow:hidden;">
						<s:form name="reloader" id="reloader" action="dataEntry.action" method="post" onsubmit=" return fnOnSubmit();">
							<s:hidden name="startPage" id="startPage" />
							<s:hidden name="lastPage" id="lastPage" />
							<s:hidden name="buttonClicked" id="buttonClicked" />
						</s:form>
						</div>
						<div class="search-option" title="Current Search Criteria.">
						<font size="2"><b>Displaying Records for Zone:</b>&nbsp;&nbsp;<%=session.getAttribute("searchZone") %>&nbsp;&nbsp;<b>MR No.:</b>&nbsp;&nbsp;<%=session.getAttribute("searchMRNo") %>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<%=session.getAttribute("searchArea") %>&nbsp;&nbsp;<b>Bill Round:</b>&nbsp;&nbsp;<%=session.getAttribute("searchBillWindow") %></font>
						</div>					
							<table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">							
							<% 
								String dataSaved=(String)session.getAttribute("DataSaved");	
								if(null==dataSaved){
									dataSaved="";
								}
								String buttonClicked=(String)session.getAttribute("buttonClicked");
								String currentYear=PropertyUtil.getProperty("CURRENT_YEAR");
								//String currentYear=(UtilityForAll.getCurrentYear());
			                	String MAX_RECORD_PER_PAGE=PropertyUtil.getProperty("RECORDS_PER_PAGE");
			                	ArrayList meterReadStatusList=(ArrayList)session.getAttribute("meterReadStatusList");
			                	int maxRecord=Integer.parseInt(MAX_RECORD_PER_PAGE);
			                	int maxDisplay=Integer.parseInt(PropertyUtil.getProperty("MAX_DISPLAY_PAGE_NO"));
			                	int firstPageNo=1;
			                	String startPage=(String)session.getAttribute("startPage");
			                	if(null!=startPage && !"".equals(startPage)){
			                		firstPageNo=Integer.parseInt(startPage);
			                	}
			                	String lastPage=(String)session.getAttribute("lastPage");
			                	if(null!=lastPage && !"".equals(lastPage)){
			                		maxRecord=Integer.parseInt(lastPage);
			                	}
			                	int lastPageNo=firstPageNo+maxRecord;							                	
			                	int consumerListSize=0;
			                	MRDContainer mrdContainer=(MRDContainer)session.getAttribute("mrdContainer");
			                	ArrayList<ConsumerDetail> consumerList=null;
			                	if(null!=mrdContainer){
			                		consumerList=(ArrayList<ConsumerDetail>)mrdContainer.getConsumerList();
			                	}
			                	if(null!=consumerList && consumerList.size()>0){
			                		consumerListSize=consumerList.size();
			                		if(maxRecord > consumerListSize){
			                			maxRecord=consumerListSize;
			                		}
			                		if(maxDisplay > consumerListSize){
			                			maxDisplay=consumerListSize;
			                		}%>								
								<tr>
									<td align="center"  valign="top" >									   
										<div class="paged-main-div" id="pagination">
							             <%
							                		for(int i=firstPageNo-1;i<maxRecord;i++){ 
							                			ConsumerDetail consumerDetail=(ConsumerDetail)consumerList.get(i);
							                			MeterReadDetails prevMeterRead=consumerDetail.getPrevMeterRead();
							                			MeterReadDetails prevActualMeterRead=consumerDetail.getPrevActualMeterRead();
							                			MeterReadDetails currMeterRead=consumerDetail.getCurrentMeterRead();
							                			if(i==0){
							                %>
							                <div id="p<%=i+1 %>" class="paged-div _current" style="">
								                <table width="100%" align="center" border="0" cellpadding="0" cellspacing="5px">
													<tr>
														<td align="left"  valign="top" width="50%">
										                	<fieldset>
																<legend>Consumer Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Name</label>
																		</td>
																		<td align="left" width="60%" colspan="2" nowrap>
																			<span id="consumerNameSpan<%=i+1 %>"><%=consumerDetail.getName() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>KNO</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getKno() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>WC No</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerWcNoSpan<%=i+1 %>"><%=consumerDetail.getWcNo() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Category</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerCategory<%=i+1 %>"><%=consumerDetail.getCat() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Consumer Type</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerType<%=i+1 %>"><%=consumerDetail.getServiceType() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2" nowrap>
																			<label>Last Bill Generation Date</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getLastBillGenerationDate() %></span>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td align="left"  valign="top" width="50%">
															<fieldset>
																<legend>Previous Meter Read Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Status</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterStatus()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getRegRead()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActMeterReadDate"><%=prevActualMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>																
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActRegRead"><%=prevActualMeterRead.getRegRead()%></span>
																		</td>
																	</tr>	
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>No Of Floors</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevNoOfFloor"><%=prevMeterRead.getNoOfFloor()%></span>
																		</td>
																	</tr>															
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td align="left"  valign="top" colspan="2">
															<fieldset>													
																<legend>Current Meter Read Details</legend>
																<form action="javascript:void(0);" name="form<%=i+1 %>" onsubmit="return false;">
																	<input type="hidden" name="kno<%=i+1 %>" id="kno<%=i+1 %>" value="<%=consumerDetail.getKno()%>"/>
																	<input type="hidden" name="billRound<%=i+1 %>" id="billRound<%=i+1 %>" value="<%=consumerDetail.getBillRound()%>"/>
																	<input type="hidden" name="seqNO<%=i+1 %>" id="seqNO<%=i+1 %>" value="<%=consumerDetail.getSeqNo()%>"/>
																	<input type="hidden" name="hidConCat<%=i+1 %>" id="hidConCat<%=i+1 %>" value="<%=consumerDetail.getCat()%>"/>
																	<input type="hidden" name="hidConStatus<%=i+1 %>" id="hidConStatus<%=i+1 %>" value="<%=consumerDetail.getConsumerStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterReadDate<%=i+1 %>" id="hidPrevMeterReadDate<%=i+1 %>" value="<%=prevMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidPrevActMeterReadDate<%=i+1 %>" id="hidPrevActMeterReadDate<%=i+1 %>" value="<%=prevActualMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterReadDate<%=i+1 %>" id="hidMeterReadDate<%=i+1 %>" value="<%=currMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterStatus<%=i+1 %>" id="hidMeterStatus<%=i+1 %>" value="<%=currMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterStatus<%=i+1 %>" id="hidPrevMeterStatus<%=i+1 %>" value="<%=prevMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterRead<%=i+1 %>" id="hidPrevMeterRead<%=i+1 %>" value="<%=prevMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidPrevActMeterRead<%=i+1 %>" id="hidPrevActMeterRead<%=i+1 %>" value="<%=prevActualMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidCurrentMeterRead<%=i+1 %>" id="hidCurrentMeterRead<%=i+1 %>" value="<%=currMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidNewAverageConsumption<%=i+1 %>" id="hidNewAverageConsumption<%=i+1 %>" value="<%=currMeterRead.getAvgRead()%>"/>
																	<input type="hidden" name="hidNewNoOfFloor<%=i+1 %>" id="hidNewNoOfFloor<%=i+1 %>" value="<%=currMeterRead.getNoOfFloor()%>"/>
																	<input type="hidden" name="hidConsumerType<%=i+1 %>" id="hidConsumerType<%=i+1 %>" value="<%=consumerDetail.getServiceType()%>"/>
																	<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Service Cycle Route Sequence No</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<span><b><%=consumerDetail.getServiceCycleRouteSeq()%></b></span>														
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Consumer Status</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<select name="consumerStatus<%=i+1 %>" id="consumerStatus<%=i+1 %>" class="selectbox-long" onchange="setAll();" >
																			  		<option value="60">Regular</option>
																			  		<option value="<%=ConsumerStatusLookup.CAT_CHANGE.getStatusCode() %>"><%=ConsumerStatusLookup.CAT_CHANGE.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>"><%=ConsumerStatusLookup.DISCONNECTED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.ERROR_IN_PHYSICAL_MRD.getStatusCode() %>">Miscellaneous</option>
																			  		<option value="<%=ConsumerStatusLookup.REOPENING.getStatusCode() %>"><%=ConsumerStatusLookup.REOPENING.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.INVALID_DATA.getStatusCode() %>"><%=ConsumerStatusLookup.INVALID_DATA.getStatusDescr() %></option>
																			  		</select><span id="popup<%=i+1%>"></span> 																	
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Date/Bill Generation Date</label><font size="0.5">(DD/MM/YYYY)</font><font color="red"><sup>*</sup></font>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<%
																					String currMeterReadDate=currMeterRead.getMeterReadDate();
																					String mm="";
																					String dd="";
																					if(null!=currMeterReadDate &&!"".equals(currMeterReadDate.trim()) && (currMeterReadDate.trim()).length()==10){
																						dd=currMeterReadDate.substring(0,2);
																						mm=currMeterReadDate.substring(3,5);
																					}
																				%>
																				<span id="meterReadDateSpan<%=i+1%>">
																					<input type="text" name="meterReadDD<%=i+1 %>" id="meterReadDD<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusMM(this);" value="<%=dd %>"/>&nbsp;/
																					<input type="text" name="meterReadMM<%=i+1 %>" id="meterReadMM<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusDDConfirm(this);" value="<%=mm %>"/>&nbsp;/
																				</span>
																				<input type="text" name="meterReadYYYY<%=i+1 %>" id="meterReadYYYY<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" value="<%=currentYear %>"/>
																			</td>
																			<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>*</sup></font>&nbsp;
																				<input type="text" name="meterReadDDConfirm<%=i+1 %>" id="meterReadDDConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();"  onkeyup="fnSetFocusMMConfirm(this);"/>&nbsp;/
																				<input type="text" name="meterReadMMConfirm<%=i+1 %>" id="meterReadMMConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();" onkeyup="fnSetFocusMeterReadStatus(this);"/>&nbsp;/
																				<input type="text" name="meterReadYYYYConfirm<%=i+1 %>" id="meterReadYYYYConfirm<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" value="<%=currentYear %>"/>
																				<span id="confirmDate<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Status</label>
																			</td>
																			<td align="left" width="20%">																
																				<select name="meterReadStatus<%=i+1 %>" id="meterReadStatus<%=i+1 %>" class="selectbox" onchange="setMeterReadStatus(this);" >
																			  		<option value="">Please Select</option>
																			  		<%for(int m=0; m < meterReadStatusList.size();m++){ %>
																			  		<option value="<%=meterReadStatusList.get(m)%>"><%=meterReadStatusList.get(m)%></option>
																			  		<%} %>
																				</select>																
																			</td>
																			<td align="left" width="40%" nowrap>																																					
																				Read Type&nbsp;<span id="meterReadTypeSpan<%=i+1 %>"></span>&nbsp;&nbsp;Billing&nbsp;<span id="billTypeSpan<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Meter Read</label>
																			</td>
																			<td align="left" width="20%" nowrap>																	
																				<span id="currentMeterReadSpan<%=i+1%>">
																				<input type="text" name="currentMeterRead<%=i+1 %>" id="currentMeterRead<%=i+1 %>"  style="text-align:right;" class="textbox"  onblur="IsPositiveNumber(this);" onfocus="fnCheckMeterReadStatus();"  onchange="setCurrentMeterRead(this);" value="<%=currMeterRead.getRegRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="currentMeterReadConfirm<%=i+1 %>" id="currentMeterReadConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableCurrentMeterRead();" onchange="checkCurrentMeterRead();"/>&nbsp;KL
																				<span id="confirmCurrentMeterRead<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Average Consumption</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																	
																				<span><input type="text" name="currentAverageConsumption<%=i+1 %>" id="currentAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" disabled="true" readonly="true" value="<%=consumerDetail.getCurrentAvgConsumption()%>"/>&nbsp;KL</span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>New Average Consumption</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newAverageConsumptionSpan<%=i+1%>">
																				<input type="text" name="newAverageConsumption<%=i+1%>" id="newAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewAvgConsumption(this);" value="<%=currMeterRead.getAvgRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newAverageConsumptionConfirm<%=i+1 %>" id="newAverageConsumptionConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewAverageConsumption();" onchange="checkNewAverageConsumption();"/>&nbsp;KL
																				<span id="confirmNewAverageConsumption<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>No Of Floors</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newNoOfFloorSpan<%=i+1%>">
																				<input type="text" name="newNoOfFloor<%=i+1%>" id="newNoOfFloor<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewNoOfFloor(this);" value="<%=currMeterRead.getNoOfFloor()%>"/>
																				</span>&nbsp;
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newNoOfFloorConfirm<%=i+1 %>" id="newNoOfFloorConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewNoOfFloor();" onchange="checkNewNoOfFloor();"/>&nbsp;&nbsp;&nbsp;
																				<span id="confirmNewNoOfFloorConfirm<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																			<div id="remarksDiv1<%=i+1%>" style="display:none;align:center;">
																				<label>Remarks</label><font color="red"><sup>*</sup></font>		
																			</div>
																			</td>
																			<td align="left" width="40%" colspan="2">
																				<div id="remarksDiv2<%=i+1%>" style="display:none;align:center;">																																						
																					<textarea rows="2" cols="48" name="remarks<%=i+1%>" id="remarks<%=i+1 %>"  style="text-align:left;" onchange="setRemarks(this);"><%=currMeterRead.getRemarks()%></textarea>
																				</div>
																			</td>
																		</tr>	
																		<tr><td align="left" colspan="4">&nbsp;</td></tr>
																		<tr>
																			<td align="left" width="30%">
																				&nbsp;
																			</td>
																			<td align="right" width="10%">
																				<input type="button" name="btnReset" id="btnPrev" value="Reset" class="groovybutton" onclick="resetAll();"/>
																			</td>
																			<td align="right" width="60%" colspan="2">
																				<input type="submit" name="btnNext<%=i+1 %>" id="btnNext<%=i+1 %>" value="Save & Next"  class="groovybutton"/>
																			</td>
																		</tr>
																	</table>
																</form>
															</fieldset>	
														</td>
													</tr>													
												</table>											
							                </div>
							                <%} else if(i==consumerListSize-1){%>	
							                <div id="p<%=i+1 %>" class="paged-div" style="display:none;">
								                <table width="100%" align="center" border="0" cellpadding="0" cellspacing="5px">
													<tr>
														<td align="left"  valign="top" width="50%">
										                	<fieldset>
																<legend>Consumer Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Name</label>
																		</td>
																		<td align="left" width="60%" colspan="2" nowrap>
																			<span id="consumerNameSpan<%=i+1 %>"><%=consumerDetail.getName() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>KNO</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getKno() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>WC No</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerWcNoSpan<%=i+1 %>"><%=consumerDetail.getWcNo() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Category</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerCategory<%=i+1 %>"><%=consumerDetail.getCat() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Consumer Type</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerType<%=i+1 %>"><%=consumerDetail.getServiceType() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2" nowrap>
																			<label>Last Bill Generation Date</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getLastBillGenerationDate() %></span>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td align="left"  valign="top" width="50%">
															<fieldset>
																<legend>Previous Meter Read Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Status</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterStatus()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getRegRead()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActMeterReadDate"><%=prevActualMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>																
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActRegRead"><%=prevActualMeterRead.getRegRead()%></span>
																		</td>
																	</tr>	
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>No Of Floors</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevNoOfFloor"><%=prevMeterRead.getNoOfFloor()%></span>
																		</td>
																	</tr>																	
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td align="left"  valign="top" colspan="2">
															<fieldset>													
																<legend>Current Meter Read Details</legend>
																<form action="javascript:void(0);" name="form<%=i+1 %>" onsubmit="return false;">
																	<input type="hidden" name="kno<%=i+1 %>" id="kno<%=i+1 %>" value="<%=consumerDetail.getKno()%>"/>
																	<input type="hidden" name="billRound<%=i+1 %>" id="billRound<%=i+1 %>" value="<%=consumerDetail.getBillRound()%>"/>
																	<input type="hidden" name="seqNO<%=i+1 %>" id="seqNO<%=i+1 %>" value="<%=consumerDetail.getSeqNo()%>"/>
																	<input type="hidden" name="hidConCat<%=i+1 %>" id="hidConCat<%=i+1 %>" value="<%=consumerDetail.getCat()%>"/>
																	<input type="hidden" name="hidConStatus<%=i+1 %>" id="hidConStatus<%=i+1 %>" value="<%=consumerDetail.getConsumerStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterReadDate<%=i+1 %>" id="hidPrevMeterReadDate<%=i+1 %>" value="<%=prevMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidPrevActMeterReadDate<%=i+1 %>" id="hidPrevActMeterReadDate<%=i+1 %>" value="<%=prevActualMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterReadDate<%=i+1 %>" id="hidMeterReadDate<%=i+1 %>" value="<%=currMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterStatus<%=i+1 %>" id="hidMeterStatus<%=i+1 %>" value="<%=currMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterStatus<%=i+1 %>" id="hidPrevMeterStatus<%=i+1 %>" value="<%=prevMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterRead<%=i+1 %>" id="hidPrevMeterRead<%=i+1 %>" value="<%=prevMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidPrevActMeterRead<%=i+1 %>" id="hidPrevActMeterRead<%=i+1 %>" value="<%=prevActualMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidCurrentMeterRead<%=i+1 %>" id="hidCurrentMeterRead<%=i+1 %>" value="<%=currMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidNewAverageConsumption<%=i+1 %>" id="hidNewAverageConsumption<%=i+1 %>" value="<%=currMeterRead.getAvgRead()%>"/>
																	<input type="hidden" name="hidNewNoOfFloor<%=i+1 %>" id="hidNewNoOfFloor<%=i+1 %>" value="<%=currMeterRead.getNoOfFloor()%>"/>
																	<input type="hidden" name="hidConsumerType<%=i+1 %>" id="hidConsumerType<%=i+1 %>" value="<%=consumerDetail.getServiceType()%>"/>
																	<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Service Cycle Route Sequence No</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<span><b><%=consumerDetail.getServiceCycleRouteSeq() %></b></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Consumer Status</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<select name="consumerStatus<%=i+1 %>" id="consumerStatus<%=i+1 %>" class="selectbox-long" onchange="setAll();" >
																			  		<option value="60">Regular</option>
																			  		<option value="<%=ConsumerStatusLookup.CAT_CHANGE.getStatusCode() %>"><%=ConsumerStatusLookup.CAT_CHANGE.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>"><%=ConsumerStatusLookup.DISCONNECTED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.ERROR_IN_PHYSICAL_MRD.getStatusCode() %>">Miscellaneous</option>
																			  		<option value="<%=ConsumerStatusLookup.REOPENING.getStatusCode() %>"><%=ConsumerStatusLookup.REOPENING.getStatusDescr() %></option>
																			  	    <option value="<%=ConsumerStatusLookup.INVALID_DATA.getStatusCode() %>"><%=ConsumerStatusLookup.INVALID_DATA.getStatusDescr() %></option>
																			  	</select><span id="popup<%=i+1%>"></span> 																	
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Date/Bill Generation Date</label><font size="0.5">(DD/MM/YYYY)</font><font color="red"><sup>*</sup></font>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<%
																					String currMeterReadDate=currMeterRead.getMeterReadDate();
																					String mm="";
																					String dd="";
																					if(null!=currMeterReadDate &&!"".equals(currMeterReadDate.trim()) && (currMeterReadDate.trim()).length()==10){
																						dd=currMeterReadDate.substring(0,2);
																						mm=currMeterReadDate.substring(3,5);
																					}
																				%>
																				<span id="meterReadDateSpan<%=i+1%>">
																					<input type="text" name="meterReadDD<%=i+1 %>" id="meterReadDD<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusMM(this);"  value="<%=dd %>"/>&nbsp;/
																					<input type="text" name="meterReadMM<%=i+1 %>" id="meterReadMM<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusDDConfirm(this);" value="<%=mm %>"/>&nbsp;/
																				</span>
																				<input type="text" name="meterReadYYYY<%=i+1 %>" id="meterReadYYYY<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" value="<%=currentYear %>"/>
																			</td>
																			<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>*</sup></font>&nbsp;
																				<input type="text" name="meterReadDDConfirm<%=i+1 %>" id="meterReadDDConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();"  onkeyup="fnSetFocusMMConfirm(this);"/>&nbsp;/
																				<input type="text" name="meterReadMMConfirm<%=i+1 %>" id="meterReadMMConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();" onkeyup="fnSetFocusMeterReadStatus(this);"/>&nbsp;/
																				<input type="text" name="meterReadYYYYConfirm<%=i+1 %>" id="meterReadYYYYConfirm<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" value="<%=currentYear %>"/>
																				<span id="confirmDate<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Status</label>
																			</td>
																			<td align="left" width="20%">																
																				<select name="meterReadStatus<%=i+1 %>" id="meterReadStatus<%=i+1 %>" class="selectbox" onchange="setMeterReadStatus(this);" >
																			  		<option value="">Please Select</option>
																			  		<%for(int m=0; m < meterReadStatusList.size();m++){ %>
																			  		<option value="<%=meterReadStatusList.get(m)%>"><%=meterReadStatusList.get(m)%></option>
																			  		<%} %>
																				</select>																
																			</td>
																			<td align="left" width="40%" nowrap>																																					
																				Read Type&nbsp;<span id="meterReadTypeSpan<%=i+1 %>"></span>&nbsp;&nbsp;Billing&nbsp;<span id="billTypeSpan<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Meter Read</label>
																			</td>
																			<td align="left" width="20%" nowrap>																	
																				<span id="currentMeterReadSpan<%=i+1%>">
																				<input type="text" name="currentMeterRead<%=i+1 %>" id="currentMeterRead<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);" onfocus="fnCheckMeterReadStatus();"  onchange="setCurrentMeterRead(this);" value="<%=currMeterRead.getRegRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="currentMeterReadConfirm<%=i+1 %>" id="currentMeterReadConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableCurrentMeterRead();" onchange="checkCurrentMeterRead();"/>&nbsp;KL
																				<span id="confirmCurrentMeterRead<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Average Consumption</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																	
																				<span><input type="text" name="currentAverageConsumption<%=i+1 %>" id="currentAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" disabled="true" readonly="true" value="<%=consumerDetail.getCurrentAvgConsumption()%>"/>&nbsp;KL</span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>New Average Consumption</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newAverageConsumptionSpan<%=i+1%>">
																				<input type="text" name="newAverageConsumption<%=i+1%>" id="newAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewAvgConsumption(this);" value="<%=currMeterRead.getAvgRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newAverageConsumptionConfirm<%=i+1 %>" id="newAverageConsumptionConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewAverageConsumption();" onchange="checkNewAverageConsumption();"/>&nbsp;KL
																				<span id="confirmNewAverageConsumption<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>No Of Floors</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newNoOfFloorSpan<%=i+1%>">
																				<input type="text" name="newNoOfFloor<%=i+1%>" id="newNoOfFloor<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewNoOfFloor(this);" value="<%=currMeterRead.getNoOfFloor()%>"/>
																				</span>&nbsp;
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newNoOfFloorConfirm<%=i+1 %>" id="newNoOfFloorConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewNoOfFloor();" onchange="checkNewNoOfFloor();"/>&nbsp;&nbsp;&nbsp;
																				<span id="confirmNewNoOfFloorConfirm<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																			<div id="remarksDiv1<%=i+1%>" style="display:none;align:center;">
																				<label>Remarks</label><font color="red"><sup>*</sup></font>		
																			</div>
																			</td>
																			<td align="left" width="40%" colspan="2">
																				<div id="remarksDiv2<%=i+1%>" style="display:none;align:center;">																																						
																					<textarea rows="2" cols="48" name="remarks<%=i+1%>" id="remarks<%=i+1 %>"  style="text-align:left;" onchange="setRemarks(this);"><%=currMeterRead.getRemarks()%></textarea>
																				</div>
																			</td>
																		</tr>		
																		<tr><td align="left" colspan="4">&nbsp;</td></tr>
																		<tr>
																			<td align="left" width="30%">
																				<p><input type="button" name="btnPrev<%=i+1 %>" id="btnPrev<%=i+1 %>" value="Save & Previous" class="groovybutton" /></p>
																			</td>
																			<td align="right" width="10%">
																				<input type="button" name="btnReset" id="btnPrev" value="Reset" class="groovybutton" onclick="resetAll();"/>
																			</td>
																			<td align="right" width="60%" colspan="2">
																				<%if("3".equals(userRoleId)){ %>
																					<input type="submit" name="btnNext<%=i+1 %>" id="btnNext<%=i+1 %>" value="Save" class="groovybutton"/>
																					<input type="button" name="btnFreeze<%=i+1 %>" id="btnFreeze<%=i+1 %>" value="Go To Freeze" class="groovybutton" onclick="fnGoToFreezePage();"/>
																				<%}else{ %>
																					<input type="submit" name="btnNext<%=i+1 %>" id="btnNext<%=i+1 %>" value="Save" class="groovybutton"/>
																					<input type="button" name="btnFreeze<%=i+1 %>" id="btnFreeze<%=i+1 %>" value="Review" class="groovybutton" onclick="fnGoToFreezePage();"/>
																				<%} %>
																			</td>
																		</tr>
																	</table>
																</form>
															</fieldset>	
														</td>
													</tr>
												</table>			
								            </div>
								            <%} else{%>	
							                <div id="p<%=i+1 %>" class="paged-div" style="display:none;">
								                <table width="100%" align="center" border="0" cellpadding="0" cellspacing="5px">
													<tr>
														<td align="left"  valign="top" width="50%">
										                	<fieldset>
																<legend>Consumer Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Name</label>
																		</td>
																		<td align="left" width="60%" colspan="2" nowrap>
																			<span id="consumerNameSpan<%=i+1 %>"><%=consumerDetail.getName() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>KNO</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getKno() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>WC No</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerWcNoSpan<%=i+1 %>"><%=consumerDetail.getWcNo() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Category</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerCategory<%=i+1 %>"><%=consumerDetail.getCat() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Consumer Type</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span id="consumerType<%=i+1 %>"><%=consumerDetail.getServiceType() %></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2" nowrap>
																			<label>Last Bill Generation Date</label>
																		</td>
																		<td align="left" width="60%" colspan="2">
																			<span><%=consumerDetail.getLastBillGenerationDate() %></span>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td align="left"  valign="top" width="50%">
															<fieldset>
																<legend>Previous Meter Read Details</legend>
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Meter Read Status</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getMeterStatus()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span><%=prevMeterRead.getRegRead()%></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Meter Read Date</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActMeterReadDate"><%=prevActualMeterRead.getMeterReadDate()%></span>
																		</td>
																	</tr>																
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>Actual Last Registered Reading</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevActRegRead"><%=prevActualMeterRead.getRegRead()%></span>
																		</td>
																	</tr>	
																	<tr>
																		<td align="left" width="60%" colspan="2">
																			<label>No Of Floors</label>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<span id="prevNoOfFloor"><%=prevMeterRead.getNoOfFloor()%></span>
																		</td>
																	</tr>			
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td align="left"  valign="top" colspan="2">
															<fieldset>													
																<legend>Current Meter Read Details</legend>
																<form action="javascript:void(0);" name="form<%=i+1 %>" onsubmit="return false;">
																	<input type="hidden" name="kno<%=i+1 %>" id="kno<%=i+1 %>" value="<%=consumerDetail.getKno()%>"/>
																	<input type="hidden" name="billRound<%=i+1 %>" id="billRound<%=i+1 %>" value="<%=consumerDetail.getBillRound()%>"/>
																	<input type="hidden" name="seqNO<%=i+1 %>" id="seqNO<%=i+1 %>" value="<%=consumerDetail.getSeqNo()%>"/>
																	<input type="hidden" name="hidConCat<%=i+1 %>" id="hidConCat<%=i+1 %>" value="<%=consumerDetail.getCat()%>"/>
																	<input type="hidden" name="hidConStatus<%=i+1 %>" id="hidConStatus<%=i+1 %>" value="<%=consumerDetail.getConsumerStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterReadDate<%=i+1 %>" id="hidPrevMeterReadDate<%=i+1 %>" value="<%=prevMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidPrevActMeterReadDate<%=i+1 %>" id="hidPrevActMeterReadDate<%=i+1 %>" value="<%=prevActualMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterReadDate<%=i+1 %>" id="hidMeterReadDate<%=i+1 %>" value="<%=currMeterRead.getMeterReadDate()%>"/>
																	<input type="hidden" name="hidMeterStatus<%=i+1 %>" id="hidMeterStatus<%=i+1 %>" value="<%=currMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterStatus<%=i+1 %>" id="hidPrevMeterStatus<%=i+1 %>" value="<%=prevMeterRead.getMeterStatus()%>"/>
																	<input type="hidden" name="hidPrevMeterRead<%=i+1 %>" id="hidPrevMeterRead<%=i+1 %>" value="<%=prevMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidPrevActMeterRead<%=i+1 %>" id="hidPrevActMeterRead<%=i+1 %>" value="<%=prevActualMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidCurrentMeterRead<%=i+1 %>" id="hidCurrentMeterRead<%=i+1 %>" value="<%=currMeterRead.getRegRead()%>"/>
																	<input type="hidden" name="hidNewAverageConsumption<%=i+1 %>" id="hidNewAverageConsumption<%=i+1 %>" value="<%=currMeterRead.getAvgRead()%>"/>
																	<input type="hidden" name="hidNewNoOfFloor<%=i+1 %>" id="hidNewNoOfFloor<%=i+1 %>" value="<%=currMeterRead.getNoOfFloor()%>"/>
																	<input type="hidden" name="hidConsumerType<%=i+1 %>" id="hidConsumerType<%=i+1 %>" value="<%=consumerDetail.getServiceType()%>"/>
																	<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Service Cycle Route Sequence No</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<span><b><%=consumerDetail.getServiceCycleRouteSeq() %></b></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Consumer Status</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																
																				<select name="consumerStatus<%=i+1 %>" id="consumerStatus<%=i+1 %>" class="selectbox-long" onchange="setAll();" >
																			  		<option value="60">Regular</option>
																			  		<option value="<%=ConsumerStatusLookup.CAT_CHANGE.getStatusCode() %>"><%=ConsumerStatusLookup.CAT_CHANGE.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>"><%=ConsumerStatusLookup.DISCONNECTED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.ERROR_IN_PHYSICAL_MRD.getStatusCode() %>">Miscellaneous</option>
																			  		<option value="<%=ConsumerStatusLookup.REOPENING.getStatusCode() %>"><%=ConsumerStatusLookup.REOPENING.getStatusDescr() %></option>
																			  		<option value="<%=ConsumerStatusLookup.INVALID_DATA.getStatusCode() %>"><%=ConsumerStatusLookup.INVALID_DATA.getStatusDescr() %></option>
																			  	</select><span id="popup<%=i+1%>"></span> 																
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Date/Bill Generation Date</label><font size="0.5">(DD/MM/YYYY)</font><font color="red"><sup>*</sup></font>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<%
																					String currMeterReadDate=currMeterRead.getMeterReadDate();
																					String mm="";
																					String dd="";
																					if(null!=currMeterReadDate &&!"".equals(currMeterReadDate.trim()) && (currMeterReadDate.trim()).length()==10){
																						dd=currMeterReadDate.substring(0,2);
																						mm=currMeterReadDate.substring(3,5);
																					}
																				%>
																				<span id="meterReadDateSpan<%=i+1%>">
																					<input type="text" name="meterReadDD<%=i+1 %>" id="meterReadDD<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusMM(this);" value="<%=dd %>"/>&nbsp;/
																					<input type="text" name="meterReadMM<%=i+1 %>" id="meterReadMM<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusDDConfirm(this);" value="<%=mm %>"/>&nbsp;/
																				</span>
																				<input type="text" name="meterReadYYYY<%=i+1 %>" id="meterReadYYYY<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" value="<%=currentYear %>"/>
																			</td>
																			<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>*</sup></font>&nbsp;
																				<input type="text" name="meterReadDDConfirm<%=i+1 %>" id="meterReadDDConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();" onkeyup="fnSetFocusMMConfirm(this);"/>&nbsp;/
																				<input type="text" name="meterReadMMConfirm<%=i+1 %>" id="meterReadMMConfirm<%=i+1 %>" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();" onkeyup="fnSetFocusMeterReadStatus(this);"/>&nbsp;/
																				<input type="text" name="meterReadYYYYConfirm<%=i+1 %>" id="meterReadYYYYConfirm<%=i+1 %>" size="2" maxlength="4" class="textbox"  style="text-align:center;" value="<%=currentYear %>"/>
																				<span id="confirmDate<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Meter Read Status</label>
																			</td>
																			<td align="left" width="20%">																
																				<select name="meterReadStatus<%=i+1 %>" id="meterReadStatus<%=i+1 %>" class="selectbox" onchange="setMeterReadStatus(this);" >
																			  		<option value="">Please Select</option>
																			  		<%for(int m=0; m < meterReadStatusList.size();m++){ %>
																			  		<option value="<%=meterReadStatusList.get(m)%>"><%=meterReadStatusList.get(m)%></option>
																			  		<%} %>
																				</select>																
																			</td>
																			<td align="left" width="40%" nowrap>																																					
																				Read Type&nbsp;<span id="meterReadTypeSpan<%=i+1 %>"></span>&nbsp;&nbsp;Billing&nbsp;<span id="billTypeSpan<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Meter Read</label>
																			</td>
																			<td align="left" width="20%" nowrap>																	
																				<span id="currentMeterReadSpan<%=i+1%>">
																				<input type="text" name="currentMeterRead<%=i+1 %>" id="currentMeterRead<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);" onfocus="fnCheckMeterReadStatus();"  onchange="setCurrentMeterRead(this);" value="<%=currMeterRead.getRegRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="currentMeterReadConfirm<%=i+1 %>" id="currentMeterReadConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableCurrentMeterRead();" onchange="checkCurrentMeterRead();"/>&nbsp;KL
																				<span id="confirmCurrentMeterRead<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>Current Average Consumption</label>
																			</td>
																			<td align="left" width="60%" colspan="2">																	
																				<span><input type="text" name="currentAverageConsumption<%=i+1 %>" id="currentAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" disabled="true" readonly="true" value="<%=consumerDetail.getCurrentAvgConsumption()%>"/>&nbsp;KL</span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>New Average Consumption</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newAverageConsumptionSpan<%=i+1%>">
																				<input type="text" name="newAverageConsumption<%=i+1%>" id="newAverageConsumption<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewAvgConsumption(this);" value="<%=currMeterRead.getAvgRead()%>"/>
																				</span>&nbsp;KL
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newAverageConsumptionConfirm<%=i+1 %>" id="newAverageConsumptionConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewAverageConsumption();" onchange="checkNewAverageConsumption();"/>&nbsp;KL
																				<span id="confirmNewAverageConsumption<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																				<label>No Of Floors</label>
																			</td>
																			<td align="left" width="20%" nowrap>
																				<span id="newNoOfFloorSpan<%=i+1%>">
																				<input type="text" name="newNoOfFloor<%=i+1%>" id="newNoOfFloor<%=i+1 %>"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewNoOfFloor(this);" value="<%=currMeterRead.getNoOfFloor()%>"/>
																				</span>&nbsp;
																			</td>
																			<td align="left" width="40%" nowrap>
																				<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																				<input type="text" name="newNoOfFloorConfirm<%=i+1 %>" id="newNoOfFloorConfirm<%=i+1 %>" style="text-align:right;" class="textbox" onfocus="disableNewNoOfFloor();" onchange="checkNewNoOfFloor();"/>&nbsp;&nbsp;&nbsp;
																				<span id="confirmNewNoOfFloorConfirm<%=i+1 %>"></span>
																			</td>
																		</tr>
																		<tr>
																			<td align="left" width="40%" colspan="2">
																			<div id="remarksDiv1<%=i+1%>" style="display:none;align:center;">
																				<label>Remarks</label><font color="red"><sup>*</sup></font>		
																			</div>
																			</td>
																			<td align="left" width="40%" colspan="2">
																				<div id="remarksDiv2<%=i+1%>" style="display:none;align:center;">																																						
																					<textarea rows="2" cols="48" name="remarks<%=i+1%>" id="remarks<%=i+1 %>"  style="text-align:left;" onchange="setRemarks(this);"><%=currMeterRead.getRemarks()%></textarea>
																				</div>
																			</td>
																		</tr>																	
																		<tr><td align="left" colspan="4">&nbsp;</td></tr>
																		<tr>
																			<td align="left" width="30%">
																				<p><input type="button" name="btnPrev<%=i+1 %>" id="btnPrev<%=i+1 %>" value="Save & Previous" class="groovybutton" /></p>
																			</td>
																			<td align="right" width="10%">
																				<input type="button" name="btnReset" id="btnPrev" value="Reset" class="groovybutton" onclick="resetAll();"/>
																			</td>
																			<td align="right" width="60%" colspan="2">
																				<input type="submit" name="btnNext<%=i+1 %>" id="btnNext<%=i+1 %>" value="Save & Next" class="groovybutton"/>
																			</td>
																		</tr>
																	</table>
																</form>
															</fieldset>	
														</td>
													</tr>
												</table>			
								            </div>
							                 <%}}%>	
										</div>
									</td>
								</tr>				
								<tr>
									<td align="center"  valign="top">
									<div class="page-nos" id="curr-page-no">
									<table width="100%" border="0">
									<tr><td width="2%" align="left" valign="top" nowrap>
									<CITE id="pageSpan<%=firstPageNo-1%>" class="prev" title="Previouse Set">&lt;&lt;</CITE>
									<CITE id="pageSpan<%=firstPageNo-1%>" class="prev" title="Previouse">&lt;</CITE>
									</td>
									<td width="96%" align="center">
									<%	int lastPageNoSpan=maxRecord;										
										for(int p=firstPageNo;p<lastPageNoSpan+1;p++){ 
										if(p==firstPageNo){%>
											<CITE id="pageSpan<%=p%>" class="currentPageNo" title="Current Record No."><%=p%></CITE>
										<%}else{ %>
											<CITE id="pageSpan<%=p%>" class="pageNo"><%=p%></CITE>
										<%}} %>
									</td>
									<td width="2%" align="right" valign="top" nowrap>
										<CITE id="pageSpan<%=lastPageNoSpan+1%>" class="next" title="Next">&gt;</CITE>
										<CITE id="pageSpan<%=lastPageNoSpan+1%>" class="next" title="Next Set">&gt;&gt;</CITE>
									</td>
									</tr>
									</table>
									</div>									
									</td>
								</tr>
								<!--  
								<tr style="height:20px;">
									<td align="center"  valign="top">
										<s:form action="save.action" method="post" onsubmit=" return fnOnSubmit();">
											<s:submit method="execute" key="label.save.records" align="center" cssClass="groovybutton" />
										</s:form>
									</td>
								</tr>	
								-->							
								<!-- 
								<tr>
									<td align="left"  valign="top">
										<div id="page-no-div"></div>
									</td>
								</tr>
								  -->								 
								<tr>
									<td align="center"  valign="top" nowrap>
									<font size="2" color="#000">Showing <%=firstPageNo %>-<%=maxRecord%> records of <%=consumerListSize %> records.</font>
									Goto:&nbsp;<a href="#" onclick="javascript:fnGoToPage('1','<%=MAX_RECORD_PER_PAGE %>');" title="Click to Go to the First Record.">First</a>,
										<select name="goToRecRange" id="goToRecRange" class="smalldropdown" onchange="fnGoToPageRange(this);">
											<option value="">X - Y</option>									  		
									  		<%for(int k=1;k<=consumerListSize;){
											int fromIndex=k;
											int toIndex=k+Integer.parseInt(MAX_RECORD_PER_PAGE)-1;
											if(toIndex>consumerListSize){
												toIndex=consumerListSize;
											}%>
									  		<option value="<%=fromIndex%>"><%=fromIndex%>-<%=toIndex%></option>
									  		<%k=toIndex+1;
									  		}%>									  		
										</select>
										Record No<input type="text" name="goToRecNo" id="goToRecNo" class="textbox" size="3" maxlength="5" style="text-align:right;"title="Go to Record No."/><input type="button" value="Go" class="smallbutton" onclick="fnGoTo();"/>											
										&nbsp;<a href="#" onclick="javascript:fnGoToPage('<%=consumerListSize %>','<%=consumerListSize %>');" title="Click to Go to Last Record.">Last</a>
										<%if(userRoleId.equals(roleScreenMap.get("12"))||userId.equals(userScreenMap.get("12"))) { %>&nbsp;<a href="#" onclick="javascript:fnGoToFreezePage();" title="Click to Go to Freeze Data Entry.">Freeze</a><%} else{%>
										&nbsp;<a href="#" onclick="javascript:fnGoToFreezePage();" title="Click to Go to Review Your Data Entry.">Review</a><%} %>
									</td>
								</tr>
								<%} else{%>	
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr>
									<td align="center"  valign="top" >
										<font size="2" color="red">No Records found to Display. Please Try Again.</font>
									</td>
								</tr>
								<%} %>		
							</table>						
						<div id="data_submited" style="display:block;text-align:center;">&nbsp;</div>
						<div id="ajax-result" style="display:block;text-align:center;" title="Server Message"><div class='search-option'>&nbsp;<font color="red" size="2"><s:actionerror /></font><font color="green" size="2"><b><%=dataSaved%></b></font></div></div>				
					</td>
				</tr>	
				<tr height="20px">
					<td align="center" valign="bottom">
						<%@include file="../jsp/Footer.html"%>
					</td>
				</tr>			
			</table>
		</td>
	</tr>
</table> 
<%@include file="../jsp/MRDMeterReplacement.html"%>
	<script type="text/javascript">
		var MAX_RECORD_PER_PAGE="<%=MAX_RECORD_PER_PAGE%>";
		var totalRecords="<%=consumerListSize%>";
		var jqCount="<%=maxRecord%>";
		var jqStart="<%=firstPageNo%>";
		var jqDisplay="<%=maxDisplay%>";
		var nextPage;
		var prevPage;
		var maxPage="<%=maxRecord%>";
		var currentPage="<%=firstPageNo%>";	
		$(function() {
			$("#page-no-div").paginate({
				count 		: jqCount,
				start 		: jqStart,
				display     : jqDisplay,
				border					: true,
				border_color			: '#fff',
				text_color  			: '#fff',
				background_color    	: 'black',	
				border_hover_color		: '#ccc',
				text_hover_color  		: '#000',
				background_hover_color	: '#fff', 
				images					: false,
				mouse					: 'press',
				onChange     			: function(page){
											fnUpdateRecord(page);										
											$('._current','#pagination').removeClass('_current').hide();
											$('#p'+page).addClass('_current').show();
											var meterReadDateId="meterReadDateDD"+page;
											//document.getElementById('curr-page-no').innerHTML="<font color='red'><b>Record No: "+page+"</b></font>";
											//document.getElementById(meterReadDateId).focus();
											document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
											document.getElementById('data_submited').innerHTML ="&nbsp;";
											setOnLoadValue();
											
										  }
			});
		});
		$(document).ready(function(){
				var buttonClicked=Trim(document.forms[0].buttonClicked.value);
				if(buttonClicked=='Previouse'){
					var pageNoStart=Trim(document.forms[0].startPage.value);
					var pageNoLast=Trim(document.forms[0].lastPage.value);
					
					$(".currentPageNo").addClass('pageNo');
				    $("#pageSpan"+pageNoLast).removeClass("pageNo");
				    $("#pageSpan"+pageNoLast).addClass("currentPageNo");
			      	$('._current','#pagination').removeClass('_current').hide();
				  	$('#p'+pageNoLast).addClass('_current').show();
				  	currentPage=pageNoLast;
				  	//setOnLoadValue();
				  	document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
				}
			  $("form").submit(function(e){
				/*var ddId="meterReadDD"+currentPage;
		    	var mmId="meterReadMM"+currentPage;
		    	var yyyyId="meterReadYYYY"+currentPage;
		    	document.getElementById(ddId).value=Trim(document.getElementById(ddId).value);
		    	document.getElementById(mmId).value=Trim(document.getElementById(mmId).value);
		    	var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
		    	var consumerStatusId="consumerStatus"+currentPage;
		    	var consumerStatusValue=Trim(document.getElementById(consumerStatusId).value)
		    	if(meterReadDate.length!=10 && consumerStatusValue=='60'){
		    		alert('Meter Read Date/Bill Generation Date is Mandatory.');
		    		disableFields(document.getElementById(consumerStatusId));
		    		toUpdateMeterReadDate="";
		    		toUpdateMeterReadDateConfirm="";
		    		document.getElementById(ddId).focus();
		    		isValid=false;
		    		return;
		    	}*/
				prevPage=currentPage;
				nextPage=parseInt(currentPage)+parseInt(1);
				var maxInt=parseInt(maxPage);
				var nextInt=parseInt(nextPage);
				var lastInt="<%=lastPageNo%>";
			    if(nextInt-1==maxInt && maxInt < parseInt(totalRecords)){
			    	fnUpdateRecord(nextPage);
			    	if(isValid){
			    		clearToUpdateVar();			    	 		    	 
						document.forms[0].startPage.value=nextPage;
						document.forms[0].lastPage.value=nextInt+parseInt(MAX_RECORD_PER_PAGE)-1;
						document.forms[0].buttonClicked.value="Next";
						document.forms[0].action="dataEntry.action";
						hideScreen();
						document.forms[0].submit();
			    	}
					    
				}else{
									    
				   	if( nextPage-1 < parseInt(totalRecords)){
				   		fnUpdateRecord(nextPage);
					    if(isValid){
					    	clearToUpdateVar();
						    currentPage=nextPage;			   
						    //document.getElementById('curr-page-no').innerHTML="<font color='red'><b>Record No: "+currentPage+"</b></font>";
						    $('._current','#pagination').removeClass('_current').hide();
							$('#p'+nextPage).addClass('_current').show();
							var currSpanId=nextPage-1;
							
							$("#pageSpan"+currSpanId).addClass('pageNo');
						    $("#pageSpan"+nextPage).removeClass("pageNo");
						    $("#pageSpan"+nextPage).addClass("currentPageNo");							
							setOnLoadValue();
					    }
				    }else{
				    	fnUpdateLastRecord(nextPage);
				    }
			    }
			  });
			  $("p").click(function(){
				  	var ddId="meterReadDD"+currentPage;
			    	var mmId="meterReadMM"+currentPage;
			    	var yyyyId="meterReadYYYY"+currentPage;
			    	document.getElementById(ddId).value=Trim(document.getElementById(ddId).value);
			    	document.getElementById(mmId).value=Trim(document.getElementById(mmId).value);
			    	var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
			    	var consumerStatusId="consumerStatus"+currentPage;
			    	var consumerStatusValue=Trim(document.getElementById(consumerStatusId).value)
			    	if(meterReadDate.length!=10 && consumerStatusValue=='60'){
			    		alert('Meter Read Date/Bill Generation Date is Mandatory.');
			    		toUpdateMeterReadDate="";
			    		toUpdateMeterReadDateConfirm="";
			    		document.getElementById(ddId).focus();
			    		isValid=false;
			    		return;
			    	}
				    prevPage=parseInt(currentPage)-parseInt(1);
					nextPage=currentPage;
					var stratInt=parseInt(jqStart);
					var prevInt=parseInt(prevPage)+1;
					var lastInt=parseInt(jqStart);
					fnUpdateRecord(prevPage);
				    if(prevInt==stratInt){
					    if(isValid){
					    	clearToUpdateVar();
					    	document.forms[0].startPage.value=prevInt-parseInt(MAX_RECORD_PER_PAGE);
						    if(parseInt(document.forms[0].startPage.value) < 1){
						    	document.forms[0].startPage.value="1";
						    }
						    document.forms[0].lastPage.value=parseInt(document.forms[0].startPage.value)+parseInt(MAX_RECORD_PER_PAGE)-1;
					    	document.forms[0].buttonClicked.value="Previouse";
					    	hideScreen();
					    	document.forms[0].action="dataEntry.action";
					    	document.forms[0].submit();
					    }
				    }else{
					    if(isValid){
					    	clearToUpdateVar();				   
						    //document.getElementById('curr-page-no').innerHTML="<font color='red'><b>Record No: "+currentPage+"</b></font>";
						    $('._current','#pagination').removeClass('_current').hide();
							$('#p'+prevPage).addClass('_current').show();
							//setOnLoadValue();
							var prevSpanId=parseInt(prevPage)+1;
							$("#pageSpan"+prevSpanId).addClass('pageNo');
						    $("#pageSpan"+prevPage).removeClass("pageNo");
						    $("#pageSpan"+prevPage).addClass("currentPageNo");
						    currentPage=prevPage; 
					    }
				    }
				  });
			  	$('._current','#pagination').removeClass('_current').hide();
				$('#p'+currentPage).addClass('_current').show();
				$('input[type="text"]').focus(function() { 
					$(this).addClass("textboxfocus"); 
				});   
				$('input[type="text"]').blur(function() {  					
					$(this).removeClass("textboxfocus"); 
					$(this).addClass("textbox"); 
				}); 
				$('input[type="password"]').focus(function() { 
					$(this).addClass("textboxfocus"); 
				});   
				$('input[type="password"]').blur(function() {  					
					$(this).removeClass("textboxfocus"); 
					$(this).addClass("textbox"); 
				});  
				$("CITE").click(function () { 
				      	var pageNoStr = $(this).html(); 
				      	if(pageNoStr=='&lt;' && parseInt(currentPage)>parseInt(jqStart)){
					    	pageNoStr=parseInt(currentPage)-1;
					    	$("#pageSpan"+currentPage).addClass('pageNo');
						    $("#pageSpan"+pageNoStr).removeClass("pageNo");
						    $("#pageSpan"+pageNoStr).addClass("currentPageNo");
					      	$('._current','#pagination').removeClass('_current').hide();
						  	$('#p'+pageNoStr).addClass('_current').show();
						  	currentPage=pageNoStr;
						  	setOnLoadValue();
						  	document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
				      	}else if(pageNoStr=='&gt;' && parseInt(currentPage)<parseInt(maxPage)){
				      		pageNoStr=parseInt(currentPage)+1;
				      		$("#pageSpan"+currentPage).addClass('pageNo');
						    $("#pageSpan"+pageNoStr).removeClass("pageNo");
						    $("#pageSpan"+pageNoStr).addClass("currentPageNo");
					      	$('._current','#pagination').removeClass('_current').hide();
						  	$('#p'+pageNoStr).addClass('_current').show();
						  	currentPage=pageNoStr;
						  	setOnLoadValue();
						  	document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
				      }else if(pageNoStr=='&lt;&lt;'){ 
					      	if(parseInt(document.forms[0].startPage.value)+1>parseInt(MAX_RECORD_PER_PAGE)){
					    	   	var prevInt=parseInt(jqStart)-parseInt(MAX_RECORD_PER_PAGE)-1;
					    	  	document.forms[0].startPage.value=parseInt(document.forms[0].startPage.value)-parseInt(MAX_RECORD_PER_PAGE);
							    if(parseInt(document.forms[0].startPage.value) < 1){
							    	document.forms[0].startPage.value="1";
							    }
						    	document.forms[0].lastPage.value=parseInt(document.forms[0].startPage.value)+parseInt(MAX_RECORD_PER_PAGE)-1;
						    	document.forms[0].buttonClicked.value="Previouse";
						    	document.forms[0].action="dataEntry.action";
						    	hideScreen();
						    	//document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
						    	document.forms[0].submit();
					      	}
				      }else if(pageNoStr=='&gt;&gt;' && parseInt(currentPage)<parseInt(totalRecords)){	
					      	var nextInt=parseInt(maxPage)+1;
					      	if(	parseInt(nextInt)>parseInt(totalRecords)){
					      		nextInt=totalRecords;
					      	}    	  		    	 		    	 
							document.forms[0].startPage.value=nextInt;
							document.forms[0].lastPage.value=nextInt+parseInt(MAX_RECORD_PER_PAGE)-1;
							document.forms[0].buttonClicked.value="Next";
							document.forms[0].action="dataEntry.action";
							hideScreen();
							//document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
							document.forms[0].submit();
				      }else if(!isNaN(pageNoStr)){
				    	  $(".currentPageNo").addClass('pageNo');
					      $(this).removeClass("pageNo");
					      $(this).addClass("currentPageNo");
					      $('._current','#pagination').removeClass('_current').hide();
						  $('#p'+pageNoStr).addClass('_current').show();
						  currentPage=pageNoStr;
						  setOnLoadValue();
						  document.getElementById('ajax-result').innerHTML ="<div class='search-option'>&nbsp;</div>";
				      }
				  }); 

						  			  
		});
		function scroll_to(div){     
			$('html, body').animate({        
				 scrollTop: $("mydiv").offset().top     
				 },1000); } 
				 
		function clearAllFields(){
			$('#MR')[0].reset();
		}
		
	</script>
</body>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>	
<%}catch(Exception e){/*e.printStackTrace();*/%>
<script type="text/javascript">
document.location.href="logout.action";
</script>
<%} %>
</html>