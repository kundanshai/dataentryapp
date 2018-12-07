<!--
JSP page When user clicks on download button for downloading the Excel file generated, this activity needs to be captured on database with details of Mrkeys and associated data.as per jtrac DJB-4464 and Open Project Id -1208.
@author Madhuri Singh (Tata Consultancy Services)
@since 07-06-2016
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<!-- Added By Madhuri on 9th June 2016, as per Jtrac DJB-4464 -->
<%  
	String isAmrRole = DJBConstants.AMR_ROLE;
	String userRole = (String) session.getAttribute("userRoleId");
%>
<%
	try {
%>
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
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/menu.css"/>"/>
	<script type="text/javascript" src="<s:url value="/js/UtilityScripts.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
	<script language="javascript">
		function disableBack()
		{
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
 	</script>
	<script type="text/javascript">		
		var lastSubmit="<%=session.getAttribute("LAST_SUBMIT")%>";		
		<%
			
		%> 
 	
 	</script>
 	<script type="text/javascript">
	 	<%	
	 		String searchMRNo = (String) session.getAttribute("searchMRNo");
			String searchArea = (String) session.getAttribute("searchArea");
			String searchBillWindow = (String) session.getAttribute("searchBillWindow");
	 		ArrayList mrdScheduleJobList = (ArrayList) session.getAttribute("MRDScheduleJobList");
			int mrdScheduleJobListSizeInt = 0;
			if (null != mrdScheduleJobList) {
				mrdScheduleJobListSizeInt = mrdScheduleJobList.size();
			}
			String lastAction=(String)session.getAttribute("lastAction");
		%>
 		var autoRefreshTime="<%=PropertyUtil.getProperty("AUTO_REFRESH_TIME")%>";
 		var mrdScheduleJobListSize="<%=mrdScheduleJobListSizeInt%>";
 		function fnOnSubmit(){
 			hideScreen();
 			return true;
 		}
 		function fnRefresh(){
 			hideScreen();
 			if(document.getElementById('checkboxShowAllDownload').checked){
 				document.forms[0].showAll.value='Y';
 	 		}else{
 	 			document.forms[0].showAll.value='';
 	 		}
 			if(document.getElementById('checkboxShowCompletedDownload').checked){
 	 	 		document.forms[0].showCompleted.value='Y';
 	 		}else{
 	 			document.forms[0].showCompleted.value='';
 	 		}
 			if(document.getElementById('checkboxShowProgressDownload').checked){
 				document.forms[0].showInProgress.value='Y';
 	 		}else{
 	 			document.forms[0].showInProgress.value='';
 	 		}
 			if(document.getElementById('checkboxSubmittedBy').checked){
 	 	 		document.forms[0].submittedBy.value='ME';
 	 		}else{
 	 			document.forms[0].submittedBy.value='';
 	 		}
 			document.forms[0].hidAction.value="Refresh";
 			document.forms[0].submit();
 		}
 		function fnShowAllDownload(obj){
 			var checkBoxVal=obj.checked;
 			if(checkBoxVal==true){
 	 			document.getElementById('checkboxShowCompletedDownload').checked=true;
 				document.getElementById('checkboxShowProgressDownload').checked=true;
 				//document.getElementById('checkboxSubmittedBy').checked=false; 	/* Commented by Madhuri on 15th June 2016 */
 				/*Modified by Madhuri on 15th June 2016 as per Jtrac DJB-4464*/
 				if(<%=isAmrRole%>==<%=userRole%>){
 				document.getElementById('checkboxSubmittedBy').checked=true;
 	 	 		document.getElementById("checkboxSubmittedBy").disabled = true;
 	 	 		
 	 	 		
 				}
 				else{
 					document.getElementById('checkboxSubmittedBy').checked=false;
 	 	 	 		document.getElementById("checkboxSubmittedBy").disabled = false;
 	 				
 			}
 				/*Ended by Madhuri on 15th June 2016 as per Jtrac DJB-4464*/
 			}else{
 				document.getElementById('checkboxSubmittedBy').checked=true;
 				document.getElementById('checkboxShowCompletedDownload').checked=false;
 				document.getElementById('checkboxShowProgressDownload').checked=false;
 				
 			}
 			fnRefresh();
 		}
 		function fnShowCompletedDownload(obj){ 			
 			fnRefresh();
 		}
 		function fnShowInProgressDownload(obj){ 			
 			fnRefresh();
 		}
 		function fnShowSubmittedBy(obj){
 			var checkBoxVal=obj.checked;
 			if(checkBoxVal==true){
 				document.getElementById('checkboxShowAllDownload').checked=false;
 			}else{
 				document.getElementById('checkboxShowAllDownload').checked=true;
 			} 			
 			fnRefresh();
 		}
 		function fnSubmitNewJob(){
 			hideScreen();
 			document.location.href="mrdScheduleDownloadJobSubmit.action";
 		}
 		function fnOnLoad(){
 	 		//alert(document.forms[0].hidAction.value+'<>'+document.forms[0].submittedBy.value);
 	 		if(document.forms[0].showAll.value=='Y'){
 	 	 		document.getElementById('checkboxShowAllDownload').checked=true;
 	 		}else{
 	 			document.getElementById('checkboxShowAllDownload').checked=false;
 	 		}
 	 		if(document.forms[0].showCompleted.value=='Y'){
 	 	 		document.getElementById('checkboxShowCompletedDownload').checked=true;
 	 		}else{
 	 			document.getElementById('checkboxShowCompletedDownload').checked=false;
 	 		}
 	 		if(document.forms[0].showInProgress.value=='Y'){
 	 	 		document.getElementById('checkboxShowProgressDownload').checked=true;
 	 		}else{
 	 			document.getElementById('checkboxShowProgressDownload').checked=false;
 	 		}
 	 			/*Modified by Madhuri on 15th June 2016 as per Jtrac DJB-4464*/
 	 		if(<%=isAmrRole%>==<%=userRole%>){
 			if(document.forms[0].submittedBy.value=='ME'){
 	 	 		document.getElementById('checkboxSubmittedBy').checked=true;
 	 	 		document.getElementById("checkboxSubmittedBy").disabled = true;
 	 		}
 	 		}
 	 		else{

 	 			if(document.forms[0].submittedBy.value=='ME'){
 	 	 	 		document.getElementById('checkboxSubmittedBy').checked=true;
 	 	 		}else{
 	 	 			document.getElementById('checkboxSubmittedBy').checked=false;
 	 	 		}
 	 		}
 	 		/*Ended by Madhuri on 15th June 2016 as per Jtrac DJB-4464*/
 	 		/*if(mrdScheduleJobListSize=='0'){ 	 			
 	 			document.getElementById('checkboxSubmittedBy').checked=true;
 	 			document.getElementById('checkboxShowAllDownload').checked=false;
 	 			document.forms[0].submittedBy.value="ME";
 	 			document.forms[0].showAll.value="";
 	 			//fnRefresh();
 	 		}*/
 	 		/*if(document.forms[0].hidAction.value=='prepareDownload'){
 	 			fnRefresh();
 	 		}*/
 		} 
 		function fnDownloadFile(billWindow,searchCriteria,fileName,userId){/*Modified by Madhuri on 10th June 2016 as per Jtrac DJB-4464*/
 			document.forms[1].billWindow.value=billWindow; 
			document.forms[1].searchCriteria.value=searchCriteria;
			/*Ended by Madhuri on 10th June 2016 as per Jtrac DJB-4464*/
	 		document.forms[1].fileName.value=fileName;
 	 		document.forms[1].userId.value=userId;
 	 		document.forms[1].action="download.action";
 	 		document.forms[1].submit();

 		}	
 		/*document.onkeydown = function(evt) {
 		     evt = evt || window.event;     
 		     if (evt.keyCode == 27) {  	 		    	
 		    	//showScreen(); 
 		    	//goHome();    
	 		 } 
 		  }; */	
 	</script> 	
</head>
<body onload="fnOnLoad();">
<%@include file="../jsp/CommonOverlay.html"%> 
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage">
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%> 
					</td>
				</tr>
				<tr height="20px">
					<td align="center" valign="top"><%@include
						file="../jsp/TopMenu.html"%></td>
				</tr>				
				<tr height="20px">
					<td align="left" valign="top">
						<div class="search-option" title="Current Search Criteria.">
							<font size="2">
							<%
								if (null != session.getAttribute("searchZone")) {
							%>
							<b>Last MRD Schedule job submitted for Zone:</b>&nbsp;&nbsp;<%=session.getAttribute("searchZone")%><%
								if (null != searchMRNo && !"".equals(searchMRNo)) {
							%>&nbsp;&nbsp;<b>MR No.:</b>&nbsp;&nbsp;<%=searchMRNo%><%
								}
							%><%
								if (null != searchArea && !"".equals(searchArea)) {
							%>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<%=searchArea%><%
								}
							%><%
								if (null != searchBillWindow
												&& !"".equals(searchBillWindow)) {
							%>&nbsp;&nbsp;<b>Bill Window:</b>&nbsp;&nbsp;<%=searchBillWindow%><%
								}
							%>
							<%
								}
							%>&nbsp;
							</font>
						</div>				
					</td>
				</tr>
				<tr>
					<td align="center" valign="top" title="Click Refresh to Get the Current Status.">
						<div id="download">
						<table width="100%" align="center" border="0" >							
							<tr>
								<td align="center"  valign="top">
									<fieldset>
										<legend><span id="downloadFor">MRD Schedule Download</span></legend>						
										<table width="95%" align="center" border="0">
											<tr>
												<td align="left" colspan="3">
													<font color="red" size="2"><s:actionerror /></font>
												</td>
											</tr>										
											<tr>
												<td align="center" colspan="3" title="Click Refresh to get Current Status">
													<s:form action="scheduleDownload.action" method="post" onsubmit=" return fnOnSubmit();">														
														<!--<center><font color="green" size="3"><%//=session.getAttribute("job_status")%></font></center>  -->
														<s:hidden name="page"/>
														<s:hidden name="showAll"/>
														<s:hidden name="showCompleted"/>
														<s:hidden name="showInProgress"/>
														<s:hidden name="submittedBy"/>														
														<s:hidden name="hidAction"/>
														<center>
														<input type="checkbox" name="checkboxSubmittedBy" id="checkboxSubmittedBy" onclick="javascript:fnShowSubmittedBy(this);" value="on"/><b>Submited By Me Only</b>&nbsp;&nbsp;
														<input type="checkbox" name="checkboxShowCompletedDownload" id="checkboxShowCompletedDownload" onclick="javascript:fnShowCompletedDownload(this);"/><b>Show Completed Download</b>&nbsp;&nbsp;
														<input type="checkbox" name="checkboxShowProgressDownload" id="checkboxShowProgressDownload" onclick="javascript:fnShowInProgressDownload(this);"/><b>Show Download In Progress</b>&nbsp;&nbsp;
														<input type="checkbox" name="checkboxShowAllDownload" id="checkboxShowAllDownload" onclick="javascript:fnShowAllDownload(this);"/><b>Show All Download</b>&nbsp;&nbsp;
														<br/>
														<input type="button" value="Refresh" onclick="javascript:fnRefresh();" class="groovybutton"/>
														<%if(userRoleId.equals(roleScreenMap.get("22"))||userId.equals(userScreenMap.get("22"))) { %>
														&nbsp;&nbsp;
														<input type="button" value="Submit New Job" onclick="javascript:fnSubmitNewJob();" class="groovybutton"/>
														<%} %>
														</center>
													</s:form>	
												</td>
											</tr>
										</table>									
									</fieldset>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<%
								int inProgress = 0;
									MRDScheduleDownloadDetails mrdScheduleDownloadDetails = null;
									if (null != mrdScheduleJobList && mrdScheduleJobList.size() > 0) {
							%>							
							<tr>
								<td align="center"  valign="top">
									<s:form action="download.action" method="post">
									<!-- Added by Madhuri on 10th June 2016 as per Jtrac:-DJB-4464 -->
										<s:hidden name="fileName"/>	
										<s:hidden name ="billWindow"/>
										<s:hidden name="searchCriteria"/>	
										<s:hidden name="userId"/>											
									</s:form>	
									<fieldset>
										<legend>MRD Schedule Download List</legend>						
										<table width="98%" align="center" border="1px" cellpadding="2px" cellspacing="0" style="font:1.0em;">
											<tr style="background:#21c5d8;">
												<th align="center" width="2%">
													SL
												</th>
												<th align="center" width="5%">
													Bill Window
												</th>
												<th align="center" width="10%">
													MRD Contents
												</th>
												<th align="center" width="20%">
													File Name
												</th>
												<th align="center" width="10%" title="Click Refresh To Get Current Progress">
													Progress
												</th>
												<th align="center"  width="8%">
													Current Status
												</th>
												<!-- <th align="center">
													Is Downloaded
												</th> -->
												<th align="center"  width="10%">
													Submit Date Time
												</th>
												<th align="center"  width="10%">
													Submitted By
												</th>
												<th align="center"  width="5%">
													Action
												</th>
											</tr>
											<%
												for (int i = 0; i < mrdScheduleJobList.size(); i++) {
													mrdScheduleDownloadDetails = (MRDScheduleDownloadDetails) mrdScheduleJobList
															.get(i);
													String pcCompleteion = mrdScheduleDownloadDetails
																		.getPercentageOfCompletion();
													String bgCompleted = "#18C908";
													String bgRemaining = "#FF0000";
													/*if (null != pcCompleteion && !"".equals(pcCompleteion)) {
														if (Integer.parseInt(pcCompleteion) >= 25
																&& Integer.parseInt(pcCompleteion) < 50) {
															bgCompleted = "#F7BD0F";
															bgRemaining="#4DF73E";
														}
														if (Integer.parseInt(pcCompleteion) >= 50
																&& Integer.parseInt(pcCompleteion) < 75) {
															bgCompleted = "#BDF70F";
														}
														if (Integer.parseInt(pcCompleteion) >= 75
																&& Integer.parseInt(pcCompleteion) < 100) {
															bgCompleted = "#4DF73E";
														}
														if (Integer.parseInt(pcCompleteion) == 100) {
															bgCompleted = "#18C908";
														}
													}*/																
													String finalStatus = mrdScheduleDownloadDetails
																		.getStatus();
											if ("Completed".equalsIgnoreCase(finalStatus)) {
											%>									
											<tr style="background:#009933;" title="Completed">
											<%}else if ("Failed".equalsIgnoreCase(finalStatus)) {%>									
											<tr style="background:#f00;" title="Failed">	
											<%}else if ("Incomplete".equalsIgnoreCase(finalStatus)) {%>									
											<tr style="background:#B6BF00;" title="Incomplete">		
											<%} else {inProgress++;%>
											<tr style="background:#B6BFB6;" title="In Progress">
											<%
												}
											%>
												<td align="center">
													<%=i + 1%>
												</td>
												<td align="center">
													<%=mrdScheduleDownloadDetails.getBillWindow()%>
												</td>
												<td align="left">
													<%=mrdScheduleDownloadDetails.getSearchCriteria()%>
												</td>
												<td align="left">
													<%=mrdScheduleDownloadDetails
												.getFileName()%>
												</td>												
												<td align="left" nowrap>
												<%if("Submitted".equalsIgnoreCase(finalStatus)){ %>													
													<table  border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#F5F5D5">
														<tr>
															<td align="center" width="<%=(100-Integer.parseInt(pcCompleteion))%>%"  title="Remaining <%=(100-Integer.parseInt(pcCompleteion))%>%">
																&nbsp;
															</td>
														</tr>
													</table>
												<%}if("In Progress".equalsIgnoreCase(finalStatus)){ %>
													<table  border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#F5F5D5">
														<tr>
															<%if(Integer.parseInt(pcCompleteion)>0){ %>
															<td align="center" width="<%=pcCompleteion%>%" bgcolor="<%=bgCompleted%>" title="Completed <%=pcCompleteion%>%">
																&nbsp;
															</td>
															<%}if((100-Integer.parseInt(pcCompleteion))>0){ %>
															<td align="center" width="<%=(100-Integer.parseInt(pcCompleteion))%>%"  title="Remaining <%=(100-Integer.parseInt(pcCompleteion))%>%">
																&nbsp;
															</td>
															<%} %>
														</tr>
													</table>		
												<%} if("Completed".equalsIgnoreCase(finalStatus)){ %>
													<table  border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#F5F5D5">
														<tr>
															<%if(Integer.parseInt(pcCompleteion)>0){ %>
															<td align="center" width="<%=pcCompleteion%>%" bgcolor="<%=bgCompleted%>" title="Completed <%=pcCompleteion%>%">
																&nbsp;
															</td>
															<%}if((100-Integer.parseInt(pcCompleteion))>0){ %>
															<td align="center" width="<%=(100-Integer.parseInt(pcCompleteion))%>%" bgcolor="<%=bgRemaining%>" title="Failed <%=(100-Integer.parseInt(pcCompleteion))%>%, For details Download and see log file included in the Zip file.">
																&nbsp;
															</td>
															<%} %>
														</tr>
													</table>
												<%} if("Incomplete".equalsIgnoreCase(finalStatus)){ %>
													<table  border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#F5F5D5">
														<tr>
															<%if(Integer.parseInt(pcCompleteion)>0){ %>
															<td align="center" width="<%=pcCompleteion%>%" bgcolor="<%=bgCompleted%>" title="Completed <%=pcCompleteion%>%">
																&nbsp;
															</td>
															<%}if((100-Integer.parseInt(pcCompleteion))>0){ %>
															<td align="center" width="<%=(100-Integer.parseInt(pcCompleteion))%>%" bgcolor="<%=bgRemaining%>" title="Failed <%=(100-Integer.parseInt(pcCompleteion))%>%, For details Download and see log file included in the Zip file.">
																&nbsp;
															</td>
															<%} %>
														</tr>
													</table>
												<%} if("Failed".equalsIgnoreCase(finalStatus)){ %>												
													&nbsp;												
												<%} %>
												</td>
												<td align="center">												
													<%=finalStatus%>
												</td>
												<!-- <td align="center">
													<%//=mrdScheduleDownloadDetails.getDownloadedFlag()%>
												</td> -->
												<td align="center">
													<%=mrdScheduleDownloadDetails.getCreateDate()%>
												</td>
												<td align="center">
													<%=mrdScheduleDownloadDetails.getCreatedBy()%>
												</td>
												<td align="center">
													<%
														String fileName = mrdScheduleDownloadDetails
																			.getCreatedBy()
																			+ "/"
																			+ mrdScheduleDownloadDetails.getFileName();
													if("Incomplete".equalsIgnoreCase(finalStatus)||"Completed".equalsIgnoreCase(finalStatus)) {
													%>
														<a href="#" onclick="javascript:fnDownloadFile('<%=mrdScheduleDownloadDetails.getBillWindow()%>','<%=mrdScheduleDownloadDetails.getSearchCriteria()%>','<%=mrdScheduleDownloadDetails.getFileName()%>','<%=mrdScheduleDownloadDetails.getCreatedBy()%>');" title="Click to Download">Download</a>
													<%
														} else {
													%>
													&nbsp;
													<%
														}
													%>
												</td>
											</tr>
											<%
												}
														if (inProgress > 0
																&& "Y".equalsIgnoreCase(PropertyUtil
																		.getPropertyUtil().getProperty(
																				"AUTO_REFRESH_FLAG"))) {
											%>
											<script language="javascript">
												//alert(autoRefreshTime);
												setInterval('fnRefresh()',autoRefreshTime);
											</script>
											<%
												}
											%>
										</table>
										<br/>									
									</fieldset>
								</td>
							</tr>
							<%
								} else {
							%>	
							<tr>
								<td>						
									<fieldset>
										<legend>MRD Schedule Download List</legend>						
										<table width="98%" align="center" border="0" cellpadding="2px" cellspacing="0" style="font:1.0em;">
											<tr><td>&nbsp;</td></tr>				
											<tr>
												<td align="center">
													<center><font color='red'>Currently No Records in the Queue !</font></center>
												</td>
											</tr>
											<tr><td>&nbsp;</td></tr>		
										</table>
										<br/>									
									</fieldset>
								</td>
							</tr>
							<%
								}
							%>
							<%
								//if ("prepareDownload".equalsIgnoreCase(lastAction)) {
							%>
								<script language="javascript">									
								if(document.forms[0].hidAction.value=='prepareDownload'){
									alert('New Job has been Successfully Submited, Please Click Refresh for Current Status.');
						 	 	 	document.getElementById('checkboxSubmittedBy').checked=true;
						 	 	 	document.getElementById('checkboxShowAllDownload').checked=false;							
					 	 			fnRefresh();
								}
								</script>
							<%
								//}
							%>	
							<tr><td>&nbsp;</td></tr>
						</table>
						</div>
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
</body>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>