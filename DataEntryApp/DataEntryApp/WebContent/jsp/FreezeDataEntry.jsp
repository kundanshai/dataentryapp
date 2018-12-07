<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<%try{ %>
<% int  highLowErrCnt=0;%>
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
	<script type="text/javascript">
	//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
	var highLowValCnt="<%=(String)session.getAttribute("HighLowValCnt")%>"; 
	//End:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
		function fnGotoMeterReplacementReview(){
			var url="meterReplacementReview.action?hidAction=search";
			url+="&zoneMR=<%=session.getAttribute("searchZoneCode")%>";
			url+="&mrNoMR=<%=session.getAttribute("searchMRNo")%>";
			url+="&areaMR=<%=session.getAttribute("searchAreaCode")%>";	
			hideScreen();
			document.location.href = url;
		}
		function popUpMeterReplacementScreen(seqNo,billRound,kno){
		  	var url="meterReplacement.action?hidAction=getDetailsForPOPUP&seqNo="+(seqNo-1)+"&kno="+kno+"&billRound="+billRound;
		  	var l=(screen.width-1000)/2;
		  	var t=(screen.height-768)/2;	  	
		  	var popupNewWin=window.open(url,"_blank","directories=no,menubar=no,toolbar=no,scrollbars=yes,status=yes,width=1000, height=710,top="+t+",left="+l);
		  	//popupNewWin.focus();
		}		
		function popupMRDEntryScreen(seqNo,svcCycRtSeq,BillRound,Kno,consumerName,consumerWcNo,consumerCat,consumerServiceType,
				consumerLastBillGenerationDate,prevMeterReadDate,prevMeterStatus,prevRegRead,consumerStatus,
				currMeterReadDate,currMeterStatus,currMeterRegRead,consumerCurrentAvgConsumption,currAvgRead,newNoOfFloor,consumerServiceType,prevActMeterReadDate,prevActRegRead,prevNoOfFloor,remarks){
			//Start:Added by Matiur Rahman on 12-02-2013 as per JTrac #DJB-1011
			//alert(consumerStatus);
			//if(consumerStatus=='30'){
				//popUpMeterReplacementScreen(seqNo,BillRound,Kno);
			//}else{
			if(null!=document.getElementById('mrdebox') && 'null'!=document.getElementById('mrdebox')){
				if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
					var theTable=document.getElementById('djbmaintable');				
					theTable.className ="djbmainhidden";
				}
				var thediv=document.getElementById('mrdebox');
				thediv.style.display = "block";
				thediv=document.getElementById('pagination');
				thediv.style.display = "none";
				/*alert(seqNo+'<>'+BillRound+'<>'+Kno+'<>'+consumerName+'<>'+consumerWcNo+'<>'+consumerCat+'<>'+consumerServiceType+'<>'+
						consumerLastBillGenerationDate+'<>'+prevMeterReadDate+'<>'+prevMeterStatus+'<>'+prevRegRead+'<>'+consumerStatus+'<>'+
						currMeterReadDate+'<>'+currMeterStatus+'<>'+currMeterRegRead+
						'<>'+consumerCurrentAvgConsumption+'<>'+currAvgRead+'<>'+newNoOfFloor+'<>'+consumerServiceType+'<>'+prevNoOfFloor);*/
				seqNo=Trim(seqNo);
				trId=parseInt(seqNo)-1;
				trId="tr"+trId;
				var x=document.getElementById(trId);
				//alert(trId+'<>'+x);
				if ( x) {
					var tds = x.getElementsByTagName("td"); 					
					consumerStatus=tds[5].innerHTML;
					consumerStatus=consumerStatus.replace(/&nbsp;/g,"");	
					consumerStatus=Trim(consumerStatus);
					//alert(consumerStatus);
					if(consumerStatus=='Change of Category'){
						consumerStatus="50";
					}else if(consumerStatus=='Disconnected'){
						consumerStatus="20";
					}else if(consumerStatus=='Meter Installed'){
						consumerStatus="30";
					}else if(consumerStatus=='Meter Not Installed'){
						consumerStatus="90";
					}
					else if(consumerStatus=='Miscellaneous'){
						consumerStatus="0";
					}else if(consumerStatus=='Reopening'){
						consumerStatus="40";
					}else if(consumerStatus=='Invalid Data'){
						consumerStatus="15";
					}
					else{
						consumerStatus="60";
					}
					var readDate=Trim(tds[13].innerHTML);
					readDate=readDate.replace(/&nbsp;/g,"");	
					readDate=Trim(readDate);									
					//alert(readDate);
					 if(readDate.length==10){ 
						 currMeterReadDate=readDate; 
					 }else{
						 currMeterReadDate=""; 
					 }
					 currMeterStatus=tds[14].innerHTML;
					 currMeterStatus=currMeterStatus.replace(/&nbsp;/g,"");	
					 currMeterStatus=Trim(currMeterStatus);
					 //alert(currMeterStatus);
					 currMeterRegRead=tds[15].innerHTML;
					 currMeterRegRead=currMeterRegRead.replace(/&nbsp;/g,"");	
					 currMeterRegRead=Trim(currMeterRegRead);					 
					 //alert(currMeterRegRead);
					 currAvgRead=tds[16].innerHTML;
					 currAvgRead=currAvgRead.replace(/&nbsp;/g,"");	
					 currAvgRead=Trim(currAvgRead);
					 //alert(currAvgRead);
					 newNoOfFloor=tds[17].innerHTML;
					 newNoOfFloor=newNoOfFloor.replace(/&nbsp;/g,"");	
					 newNoOfFloor=Trim(newNoOfFloor);
					//alert(newNoOfFloor);
				}
				document.getElementById('seqNo1').value=parseInt(seqNo)-1;
				//document.getElementById('seqNO1').value=parseInt(seqNo)-1;
				document.getElementById('pageSpan').innerHTML=Trim(seqNo);
				document.getElementById('billRound1').value=Trim(BillRound);
				document.getElementById('kno1').value=Trim(Kno);
				document.getElementById('hidConStatus1').value=consumerStatus;
				document.getElementById('hidMeterReadDate1').value=currMeterReadDate;
				document.getElementById('hidMeterStatus1').value=currMeterStatus;
				document.getElementById('hidPrevMeterStatus1').value=prevMeterStatus;
				document.getElementById('hidCurrentMeterRead1').value=currMeterRegRead;
				document.getElementById('hidNewAverageConsumption1').value=currAvgRead;
				document.getElementById('hidConsumerType1').value=Trim(consumerServiceType);
				//prevMeterReadDate="05/05/2012";
				document.getElementById('hidConCat1').value=Trim(consumerCat);
				document.getElementById('hidPrevMeterReadDate1').value=Trim(prevMeterReadDate);
				document.getElementById('hidPrevMeterRead1').value=Trim(prevActRegRead);

				document.getElementById('hidPrevActMeterReadDate1').value=Trim(prevActMeterReadDate);
				document.getElementById('hidPrevActMeterRead1').value=Trim(prevActRegRead);

				document.getElementById('consumerName').innerHTML=Trim(consumerName);
				document.getElementById('Kno').innerHTML=Trim(Kno);
				document.getElementById('consumerWcNo').innerHTML=Trim(consumerWcNo);
				document.getElementById('consumerCat').innerHTML=Trim(consumerCat);
				document.getElementById('consumerServiceType').innerHTML=Trim(consumerServiceType);
				document.getElementById('consumerLastBillGenerationDate').innerHTML=Trim(consumerLastBillGenerationDate);

				document.getElementById('prevMeterReadDate').innerHTML=Trim(prevMeterReadDate);
				document.getElementById('prevMeterStatus').innerHTML=Trim(prevMeterStatus);
				document.getElementById('prevRegRead').innerHTML=Trim(prevRegRead);

				document.getElementById('prevActMeterReadDate').innerHTML=Trim(prevActMeterReadDate);
				document.getElementById('prevActRegRead').innerHTML=Trim(prevActRegRead);
				document.getElementById('prevNoOfFloor').innerHTML=Trim(prevNoOfFloor);

				document.getElementById('consumerStatus1').value=consumerStatus;
				//====================Added for Remarks on 16-11-2012 by Tuhin 
				if(consumerStatus=='15'){
					var fieldId_remarks="remarks"+currentPage;
					//document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';	
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';
				}else{
					var fieldId_remarks="remarks"+currentPage;
					document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
				}
				//========================================================
				if(currMeterReadDate.length==10){					
					document.getElementById('meterReadDD1').value=currMeterReadDate.substr(0,2);
					document.getElementById('meterReadMM1').value=currMeterReadDate.substr(3,2);
					document.getElementById('meterReadYYYY1').value=currMeterReadDate.substr(6,4);
					document.getElementById('meterReadYYYYConfirm1').value=currMeterReadDate.substr(6,4);
				}else{
					document.getElementById('meterReadDD1').value="";
					document.getElementById('meterReadMM1').value="";
					document.getElementById('meterReadYYYY1').value=todaysDate.substr(6,4);
					document.getElementById('meterReadYYYYConfirm1').value=todaysDate.substr(6,4);
				}
				document.getElementById('meterReadStatus1').value=currMeterStatus;
				document.getElementById('currentMeterRead1').value=currMeterRegRead;
				document.getElementById('currentAverageConsumption1').value=Trim(consumerCurrentAvgConsumption);
				document.getElementById('newAverageConsumption1').value=currAvgRead;
				document.getElementById('newNoOfFloor1').value=newNoOfFloor;
				document.getElementById('remarks1').value=remarks;
				
				document.getElementById('meterReadDDConfirm1').value="";
				document.getElementById('meterReadMMConfirm1').value="";
				document.getElementById('currentMeterReadConfirm1').value="";
				document.getElementById('newAverageConsumptionConfirm1').value="";
				document.getElementById('confirmDate1').innerHTML="";
				document.getElementById('confirmCurrentMeterRead1').innerHTML="";
				document.getElementById('confirmNewAverageConsumption1').innerHTML="";
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";				
				document.getElementById('svcCycRtSeq').innerHTML="<b>"+svcCycRtSeq+"</b>";
				
				document.getElementById('meterReadDD1').disabled=false;
				document.getElementById('meterReadMM1').disabled=false;
				document.getElementById('meterReadDDConfirm1').disabled=false;
				document.getElementById('meterReadMMConfirm1').disabled=false;
				document.getElementById('meterReadStatus1').disabled=false;
				document.getElementById('currentMeterRead1').disabled=false;
				document.getElementById('currentMeterReadConfirm1').disabled=false;	
				document.getElementById('newAverageConsumption1').disabled=false;
				document.getElementById('newAverageConsumptionConfirm1').disabled=false;
				popedUp=true;	
				setOnLoadValue();		
			}
			//}
		}
		function hideMRDEntryScreen(){
			if(null!=document.getElementById('mrdebox') && 'null'!=document.getElementById('mrdebox')){
				var thediv=document.getElementById('mrdebox');		
				thediv.style.display = "none";
				if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
					var theTable=document.getElementById('djbmaintable');				
					theTable.className ="djbmain";
				}
				thediv=document.getElementById('pagination');
				thediv.style.display = "block";
				var newColor = '#009933';
				var x=document.getElementById(trId);
				var msg=document.getElementById('ajax-result').innerHTML;
				
				if(msg.indexOf(") Saved Successfully")>-1){
					msg="OK";
					document.getElementById('btnFreeze').disabled=true;//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
					document.getElementById("spanRefresh").style.display = 'block';//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
				}
				if(msg.indexOf(">Reset Successfully")>-1){
					msg="RESET";
				}
				//alert(trId+'<>'+x+'<>'+msg);
				if ( x.style && msg=='OK') {
					
					var tds = x.getElementsByTagName("td");
					if(toUpdateConsumerStatus=='50'){
						tds[5].innerHTML="Change of Category";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='15'){
						tds[5].innerHTML="Invalid Data";
						newColor = '#FFFF66';
						x.title="Cann't Freeze Records Reason:\n"+document.getElementById('remarks1').value;
					}else if(toUpdateConsumerStatus=='20'){
						tds[5].innerHTML="Disconnected";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='30'){
						tds[5].innerHTML="Meter Installed";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='90'){
						tds[5].innerHTML="Meter Not Installed";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Meter Not Installed Status";
					}
					else if(toUpdateConsumerStatus=='40'){
						tds[5].innerHTML="Reopening";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Reopening Status";
					}else if(toUpdateConsumerStatus=='0'){
						tds[5].innerHTML="Miscellaneous";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Error in Physical MRD Status";
					}else{
						tds[5].innerHTML="Regular";
						x.title="Ready To Freeze";
					}
					if(toUpdateMeterReadDate.length==10){ 
					 	tds[13].innerHTML=toUpdateMeterReadDate; 
					}else{
						 tds[13].innerHTML="&nbsp;"; 
					}
					if(Trim(toUpdateMeterReadStatus).length!=0){
						tds[14].innerHTML=toUpdateMeterReadStatus;
					}else{
						tds[14].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateCurrentMeterRead).length!=0){
						tds[15].innerHTML=toUpdateCurrentMeterRead;
					}else{
						tds[15].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewAvgConsumption).length!=0){
						tds[16].innerHTML=toUpdateNewAvgConsumption;
					}else{
						tds[16].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewNoOfFloor).length!=0){
						tds[17].innerHTML=toUpdateNewNoOfFloor;
					}else{
						tds[17].innerHTML="&nbsp;";
					}
					x.style.backgroundColor = newColor;
					
				}
				if ( x.style && msg=='RESET') {
					newColor = '#AAAAC6';					
					var tds = x.getElementsByTagName("td");					
					tds[5].innerHTML="Regular";
					x.title="Cann't Freeze Records as No Records Entered Status";
					
					if(toUpdateMeterReadDate.length==10){ 
					 	tds[13].innerHTML=toUpdateMeterReadDate; 
					}else{
						 tds[13].innerHTML="&nbsp;"; 
					}
					if(Trim(toUpdateMeterReadStatus).length!=0){
						tds[14].innerHTML=toUpdateMeterReadStatus;
					}else{
						tds[14].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateCurrentMeterRead).length!=0){
						tds[15].innerHTML=toUpdateCurrentMeterRead;
					}else{
						tds[15].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewAvgConsumption).length!=0){
						tds[16].innerHTML=toUpdateNewAvgConsumption;
					}else{
						tds[16].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewNoOfFloor).length!=0){
						tds[17].innerHTML=toUpdateNewNoOfFloor;
					}else{
						tds[17].innerHTML="&nbsp;";
					}
					x.style.backgroundColor = newColor;
					
				}
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
				msg="";
				popedUp=false;	
				clearToUpdateVar();			
			}
			
		}
		function fnCancel(){
			
			hideMRDEntryScreen();
		}
		function fnSaveDetails(){
			fnUpdateLastRecord(currentPage);
		}
		function fnResetDetails(){
			
		}

        //-->
    </script> 
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
 	</script>
 	<script type="text/javascript">
 		function fnOnSubmit(){
 			var freezeFlag="Y";
 			scroll(0,0);
 			hideScreen();
 			document.body.style.overflow = 'hidden';
 			var answer = "";
 			//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
 			highLowCnt=<%=highLowErrCnt%>;
 			var alertMsg=""; 
 			//alert("Value of high Low---->>>>>"+highLowValCnt);
 			if(highLowValCnt>0){
 				alertMsg="Reading(s) entered is(are) flagged for High Low case. Do You still want to freeze the MRD?";
 			}
 			else{
 				alertMsg="Are You Sure You want to Freeze the MRD.";
 			}
 			answer=confirm(alertMsg);
 			
 			if (answer){
 	 			//To-Do
 				document.getElementById('btnFreeze').disabled=true;
 				//doAjaxCall('','','','','','','','',freezeFlag);
 				document.forms[1].freezeData.value="Y";
				document.forms[1].action="freeze.action";
				document.forms[1].submit();
 			}
 			else{
 				//document.getElementById('btnFreeze').disabled=true;
 				document.getElementById('btnRefresh').disabled=false;
 				showScreen();
 				document.body.style.overflow = 'auto';
 				return false;
 			}
 			//Ended:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
 			
 			
 		}
 		function fnOnLoad(){ 
 			document.getElementById('btnRefresh').disabled=false;//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595	 		
 		}		
 		currentPage=1;	
 		var trId;
 		var popedUp=false;
 		document.onkeydown = function(evt) {
 	 		     evt = evt || window.event;     
 	 		     if (evt.keyCode == 27 && popedUp) {  	 		    	
 	 		    	hideMRDEntryScreen();     
	 	 		 } 
 	 		  }; 	
 	</script>
 	<style>
	 	.wrapper1, .wrapper2 {width: 955px; overflow-x: scroll; overflow-y:hidden;}
		.wrapper1 {height: 20px; }
		.wrapper2 {height: 100%; }
		.div1 {width:1000px; height: 20px; }
		.div2 {width:1000px; height: 100%;overflow: auto;overflow-x:hidden;}
	</style>
</head>
<body onload="fnOnLoad();">
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
						<s:form name="reloader" id="reloader" action="dataEntry.action" method="post" onsubmit=" return fnOnSubmit();" theme="simple">
							<s:hidden name="startPage" id="startPage" value=""/>
							<s:hidden name="lastPage" id="lastPage" value=""/>
						</s:form>	
						<s:form name="freeze" id="freeze" action="javascript:void(0);" method="post" onsubmit=" return fnOnSubmit();" theme="simple">
							<s:hidden name="freezeData" id="freezeData" value=""/>					
							<table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">
							<% 									
								int freezable=0;int nonFreezable=0;int processed=0;int invalidData=0;int meterInstalled=0;int initSatus=0;String trStyle="";String trTitle="";
								String freezeSuccess=(String)session.getAttribute("freezeSuccess"); 							                	
			                	int consumerListSize=0;
			                	MRDContainer mrdContainer=(MRDContainer)session.getAttribute("mrdContainer");
			                	ArrayList<ConsumerDetail> consumerList=null;
			                	if(null!=mrdContainer){
			                		consumerList=(ArrayList<ConsumerDetail>)mrdContainer.getConsumerList();
			                	}
			                	if(null!=consumerList && consumerList.size()>0){
			                		consumerListSize=consumerList.size();
			                		%>
								<tr>
									<td align="center"  valign="top" >									   
										<div class="paged-main-div" id="pagination">							             	
							                <div id="p1" class="paged-div _current" style="">
								                <table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">								               
													<%if("The MRD is Already Frozen.".equals(freezeSuccess) ||"There was a problem while Freezing the MRD, Please Try again.".equals(freezeSuccess)){ %>
													<tr>
														<td align="center">
															&nbsp;<font color="red" size="3"><b><%=freezeSuccess%></b></font>
														</td>
													</tr>
													
													<%}if("The MRD Has been Frozen Successfully.".equals(freezeSuccess)){%>
													<tr>
														<td align="center">
															&nbsp;<font color="green" size="3"><b><%if(null!=freezeSuccess){ %><%=freezeSuccess%><%} %></b></font>
														</td>
													</tr>													
													<%} %>
													<tr>
														<td>
															<div class="search-option" title="Server Message">
																<font	size="2"><font size="2"><b>Displaying Records for Zone:</b>&nbsp;&nbsp;<%=session.getAttribute("searchZone") %>&nbsp;&nbsp;<b>MR No.:</b>&nbsp;&nbsp;<%=session.getAttribute("searchMRNo") %>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<%=session.getAttribute("searchArea") %>&nbsp;&nbsp;<b>Bill Round:</b>&nbsp;&nbsp;<%=session.getAttribute("searchBillWindow") %></font></font>&nbsp;
															</div>
														</td>
													</tr>
													<tr>
														<td align="center"  valign="top" >
										                	<fieldset>
																<legend>Freeze Meter Read Entry</legend>
																<div class="wrapper1">
																<div class="div1">
																</div>
																</div>
																<div class="wrapper2">
																<div class="div2">
																<table width="98%" align="center" border="1px" cellpadding="1px" cellspacing="0" style="font:1.0em;">
																	<tr style="background:#21c5d8;">
																		<th rowspan="2" align="center"><font size="1">Page No</font></th>
																		<th rowspan="2" align="center"><font size="1">Svc Cyc Rt Seq </font></th>
																		<th rowspan="2">Consumer Name<br/>KNO</th>
																		<th rowspan="2" align="center">WC No</th>																		
																		<th rowspan="2" align="center">Cons -umer Type</th>
																		<th rowspan="2" align="center">Cons -umer Status</th>
																		<th rowspan="2" align="center">Last Bill Gen Date</th>
																		<th rowspan="1" colspan="6">Prev Meter Read Details</th>
																		<th rowspan="1" colspan="5">Curr Meter Read Details</th>
																		<!--<th rowspan="1">Freeze</th> -->
																	</tr>
																	<tr style="background:#21c5d8;">																		
																		<th rowspan="1" colspan="1" align="center">Last Read Date</th>
																		<th rowspan="1" colspan="1" align="center">Actual Read Date</th>
																		<th rowspan="1" colspan="1" align="center">Status</th>
																		<th rowspan="1" colspan="1">Last Read</th>
																		<th rowspan="1" colspan="1">Actual Read</th>
																		<th rowspan="1" colspan="1">No Of Floor</th>
																		<th rowspan="1" colspan="1" align="center">Date</th>
																		<th rowspan="1" colspan="1" align="center">Status</th>
																		<th rowspan="1" colspan="1">Regd Read</th>
																		<th rowspan="1" colspan="1">Avg Read</th>
																		<th rowspan="1" colspan="1">No Of Floor</th>
																		<!-- <th rowspan="1">
																			<input type="checkbox" id="selectall"/>All<br/>
																		</th> -->
																	</tr>
																<%
										                		for(int i=0;i<consumerListSize;i++){ 
										                			ConsumerDetail consumerDetail=(ConsumerDetail)consumerList.get(i);
										                			MeterReadDetails prevMeterRead=consumerDetail.getPrevMeterRead();
										                			MeterReadDetails prevActualMeterRead=consumerDetail.getPrevActualMeterRead();
										                			MeterReadDetails currMeterRead=consumerDetail.getCurrentMeterRead();
										                			String statusCode=consumerDetail.getConsumerStatus().trim();
																	String statusDesc="Regular";
																	String statusDescription="";
																	String highLowStatus=DJBConstants.HIGH_LOW_STATUS;//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
																	try{	
											                			if(null!=consumerDetail && null!=consumerDetail.getConsumerStatus() && ConsumerStatusLookup.RECORD_ENTERED.getStatusCode()==UtilityForAll
											                					.getIntValueOfString(consumerDetail.getConsumerStatus()) && null==freezeSuccess){
											                				freezable++;
											                				trStyle="background:#009933;";
											                				trTitle="Ready To Freeze";
											                				//Start:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
													                		if(null!=consumerDetail && null!=consumerDetail.getHiLowFlag() && !highLowStatus.equalsIgnoreCase(consumerDetail.getHiLowFlag())){
																				highLowErrCnt++;
																				//document.getElementById('hidHighLowErrCnt').value=highLowErrCnt;
												                				trStyle="background:#33D4FF;";
												                				trTitle="High Low Case Failed";
												                			//End:Added by Sanjay on 17-11-2016 as per JTrac #DJB-4583 and Open Project-1595
													                		}
													                		
												                		}
											                			else if(null!=consumerDetail && null!=consumerDetail.getConsumerStatus() && ConsumerStatusLookup.METER_INSTALLED.getStatusCode()==UtilityForAll
											                					.getIntValueOfString(consumerDetail.getConsumerStatus()) && null==freezeSuccess){
																			meterInstalled++;
																			trStyle="background:#71C671;";
											                				trTitle="Meter Replacement Case";
																			if("30".equalsIgnoreCase(statusCode)){
																				statusDesc=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr();
																			}
																			if(null!=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr()){
																				statusDescription=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr();
																			}
												                		}else{																	
																			if(null==statusCode	||"".equals(statusCode.trim())){
																				statusCode="60";
																			}
																			if("15".equalsIgnoreCase(statusCode)||"20".equalsIgnoreCase(statusCode)||"30".equalsIgnoreCase(statusCode)||"40".equalsIgnoreCase(statusCode)||"50".equalsIgnoreCase(statusCode)||"65".equalsIgnoreCase(statusCode)||"90".equalsIgnoreCase(statusCode)){
																				statusDesc=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr();
																			}
																			if("0".equalsIgnoreCase(statusCode)){
																				statusDesc="Miscellaneous";
																			}																			
																			if(null!=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr()){
																				statusDescription=(ConsumerStatusLookup.getConsumerStatusForStatCd(Integer.parseInt(statusCode))).getStatusDescr();
																			}
																			if("15".equalsIgnoreCase(statusCode)){
																				invalidData++;
																				//nonFreezable++;		
																				trStyle="background:#FFFF66;";
												                				trTitle="Cann't Freeze Records, Reason:"+currMeterRead.getRemarks()+" Data";
																			}else if("10".equalsIgnoreCase(statusCode)){
																				initSatus++;
																				trStyle="background: #AAAAC6;";
									                							trTitle="Cann't Freeze Records, Reason:"+statusDescription+" Status";
																			}else{ 																				 
																				nonFreezable++;		
																				trStyle="background:#B8B894;";
									                							trTitle="Cann't Freeze Records, Reason:"+statusDescription+" Status";
								                							}
																		} 
										                			}catch(Exception e){
										                				trStyle="background: #D14719;";
							                							trTitle="Records are allready Processed.";
																		processed++;
																	}%>
																<tr style="<%=trStyle %>" title="<%=trTitle %>" id="tr<%=i%>">
																		<td align="center" nowrap width="5%">
																		<%=i+1 %>
																		</td>
																		<td align="center" nowrap>
																		<%=consumerDetail.getServiceCycleRouteSeq() %>
																		</td>
																		<td align="left" >
																		<%=consumerDetail.getName() %><br/>
																		<%=consumerDetail.getKno() %>
																		<a href="#" onclick="javascript:popupMRDEntryScreen('<%=i+1 %>','<%=consumerDetail.getServiceCycleRouteSeq() %>','<%=consumerDetail.getBillRound() %>','<%=consumerDetail.getKno() %>','<%=consumerDetail.getName() %>','<%=consumerDetail.getWcNo() %>','<%=consumerDetail.getCat() %>','<%=consumerDetail.getServiceType() %>','<%=consumerDetail.getLastBillGenerationDate() %>','<%=prevMeterRead.getMeterReadDate()%>','<%=prevMeterRead.getMeterStatus()%>','<%=prevMeterRead.getRegRead()%>','<%=consumerDetail.getConsumerStatus()%>','<%=currMeterRead.getMeterReadDate()%>','<%=currMeterRead.getMeterStatus()%>','<%=currMeterRead.getRegRead()%>','<%=consumerDetail.getCurrentAvgConsumption()%>','<%=currMeterRead.getAvgRead()%>','<%=currMeterRead.getNoOfFloor()%>','<%=consumerDetail.getServiceType()%>','<%=prevActualMeterRead.getMeterReadDate() %>','<%=prevActualMeterRead.getRegRead() %>','<%=prevMeterRead.getNoOfFloor()%>','<%=currMeterRead.getRemarks()%>');">
																		<img src="/DataEntryApp/images/page_edit.gif"  border="0" width="15" title="Click to Edit Meter Read"/></a>
																		</td>	
																		<td align="center" >
																			<%=consumerDetail.getWcNo() %>
																		</td>
																		<td align="center" >
																			<%=consumerDetail.getServiceType() %>
																		</td>
																		<td align="center" >																			
																			<%=statusDesc%>
																		</td>
																		<td align="center" nowrap>
																			<%=consumerDetail.getLastBillGenerationDate() %>
																		</td>
																		<td align="center" nowrap>
																			<%=prevMeterRead.getMeterReadDate()%>
																		</td>
																		<td align="center" nowrap>
																			<%=prevActualMeterRead.getMeterReadDate()%>
																		</td>
																		<td align="center">
																			&nbsp;<%=prevMeterRead.getMeterStatus()%>
																		</td>
																		<td align="right">
																			&nbsp;<%=prevMeterRead.getRegRead()%>
																		</td>
																		<td align="right">
																			&nbsp;<%=prevActualMeterRead.getRegRead()%>
																		</td>
																		<td align="right">
																			&nbsp;<%=prevMeterRead.getNoOfFloor()%>
																		</td>
																		<td align="center" nowrap>
																			&nbsp;<%=currMeterRead.getMeterReadDate()%>
																		</td>
																		<td align="center">
																			&nbsp;<%=currMeterRead.getMeterStatus()%>
																		</td>																		
																		<td align="right">																	
																			&nbsp;<%=currMeterRead.getRegRead()%>
																		</td>
																		<td align="right">
																			&nbsp;<%=currMeterRead.getAvgRead()%>
																		</td>
																		<td align="right">
																			&nbsp;<%=currMeterRead.getNoOfFloor()%>
																		</td><!--  
																		<td align="center">
																			<input type="checkbox" class="checkboxFreeze" name="checkboxFreeze" value="<%=i %>"/>
																		</td>	
																		-->																
																	</tr>
																	<%}%>																	
																</table>
																</div>
																</div>
																<br/>	
															</fieldset>	
														</td>
													</tr>
													<tr>
														<td align="center">
																&nbsp;
														</td>
													</tr>
													<%if(null==freezeSuccess && (freezable > 0 )){%>
													<tr>
														<td align="center" width="8%"><input type="button" value=" Refresh " class="groovybutton" onclick="fnRefreshPage();" id="btnRefresh" title="Refreshing the page will update the value." />
										
															<%if(userRoleId.equals(roleScreenMap.get("12"))||userId.equals(userScreenMap.get("12"))) { %>
																<%if(initSatus==0 ){%>
																<s:submit method="freeze" id="btnFreeze" key="button.freeze" align="center" cssClass="groovybutton"/>
																<%}else{ %>
																<s:submit method="freeze" id="btnFreeze" key="button.freeze" align="center" cssClass="groovybutton" disabled="true" title="Freezing is not Allowed if any record exist in No Data Entry status."/>
																<%} %>
														<%} %>
														&nbsp;</td>
														<td align="center"><span id="spanRefresh" style="display: none" style="color:red"><font size="1">Please click <b>Refresh</b> button after editing any field and saving it successfully.</font></span></td>
									
													</tr>
													<%}if(meterInstalled > 0){%>
													<tr>
														<td align="center">
																<a href="#" onclick="javascript:fnGotoMeterReplacementReview();"><img src="/DataEntryApp/images/new.gif"  border="0" title="Now Meter Replacement Record(s) must be Frozen Separately. Click Here to Goto Freeze Meter Replacement Record(s)."/><font color="blue">Now Meter Replacement Record(s) must be Frozen Separately. Click Here to Goto Freeze Meter Replacement Record(s).</font></a>
														</td>
													</tr>
													<%}%>
													<%if("The MRD is Already Frozen.".equals(freezeSuccess) ||"There was a problem while Freezing the MRD, Please Try again.".equals(freezeSuccess)){ %>
													<tr>
														<td align="center">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="center">
															&nbsp;<font color="red" size="3"><b><%=freezeSuccess%></b></font>
														</td>
													</tr>
													
													<%}if("The MRD Has been Frozen Successfully.".equals(freezeSuccess)){%>
													<tr>
														<td align="center">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="center">
															&nbsp;<font color="green" size="3"><b><%if(null!=freezeSuccess){ %><%=freezeSuccess%><%} %></b></font>
														</td>
													</tr>													
													<%} %>
													<tr>
														<td align="left" nowrap>
															&nbsp;
														</td>
													</tr>	
													<tr>
														<td align="left" nowrap>
															<table width="100%" title="If any Record Modified Please Refresh to get the latest count."><tr><td style="background:#AAAAC6;;" width="8%">No Data Entry</td><td width="15%">=&nbsp;<%=initSatus%>&nbsp;Record(s)</td><td style="background:#009933;" width="8%">Freezable</td><td width="15%">=&nbsp;<%=freezable%>&nbsp;Record(s)</td><td style="background:#71C671;" width="8%">Meter Replaced</td><td width="15%">=&nbsp;<%=meterInstalled %>&nbsp;Record(s)</td></tr><tr><td style="background:#B8B894;" width="12%">Records Flagged Out</td><td width="15%">=&nbsp;<%=nonFreezable %>&nbsp;Record(s)</td><td style="background:#D14719;" width="10%">Processed</td><td width="15%">=&nbsp;<%=processed %>&nbsp;Record(s)<td style="background:#FFFF66;" width="10%">Invalid Data</td><td width="15%">=&nbsp;<%=invalidData %>&nbsp;Record(s)<td style="background: #33D4FF;" width="10%">High Low Case</td><td width="15%">=&nbsp;<%=highLowErrCnt %>&nbsp;Record(s)<!--<a href="#" onclick="javascript:fnGoToEntryScreen('1');">Back To Entry Screen</a>-->&nbsp;</td></tr></table>
														</td>
													</tr>	
													<tr>
														<td align="center">
															<div class="search-option" title="Server Message">
																<font	size="2"><font size="2"><b>Displaying Records for Zone:</b>&nbsp;&nbsp;<%=session.getAttribute("searchZone") %>&nbsp;&nbsp;<b>MR No.:</b>&nbsp;&nbsp;<%=session.getAttribute("searchMRNo") %>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<%=session.getAttribute("searchArea") %>&nbsp;&nbsp;<b>Bill Round:</b>&nbsp;&nbsp;<%=session.getAttribute("searchBillWindow") %></font></font>&nbsp;
															</div>
														</td>
													</tr>												
												</table>											
							                </div>							                
										</div>
									</td>
								</tr>								 
								<%} else{%>	
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<%if("The MRD Has been Frozen Successfully.".equals(freezeSuccess)){%>
								<tr>
									<td align="center"  valign="top" >
									
										<font color="green" size="3">The MRD Has been Frozen Successfully.</font>
									</td>
								</tr>
								<tr>
									<td align="left" nowrap>
										<table width="100%" title="If any Record Modified Please Refresh to get the latest count."><tr><td style="background:#009933;" width="8%">Freezable</td><td width="15%">=&nbsp;<%=freezable %>&nbsp;Record(s)</td><td style="background:#AAAAC6;" width="12%">Records in Error</td><td width="15%">=&nbsp;<%=nonFreezable %>&nbsp;Record(s)</td><td style="background:#D14719;" width="10%">Processed</td><td width="15%">=&nbsp;<%=processed %>&nbsp;Record(s)<td style="background:#FFFF66;" width="10%">Invalid Data</td><td width="15%">=&nbsp;<%=invalidData %>&nbsp;Record(s)<!--<a href="#" onclick="javascript:fnGoToEntryScreen('1');">Back To Entry Screen</a>-->&nbsp;</td></tr></table>
									</td>
								</tr>	
								<tr>
									<td align="center">
										<div class="search-option" title="Server Message">
											<font	size="2"><font size="2"><b>Displaying Records for Zone:</b>&nbsp;&nbsp;<%=session.getAttribute("searchZone") %>&nbsp;&nbsp;<b>MR No.:</b>&nbsp;&nbsp;<%=session.getAttribute("searchMRNo") %>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<%=session.getAttribute("searchArea") %></font></font>&nbsp;
										</div>
									</td>
								</tr>
								<%}else{%>
								<tr>
									<td align="center"  valign="top" >								
										<font size="2" color="red">No Records found to Display. Please Seach Again.</font>
										<%if(null!=freezeSuccess){%><font size="3" color="red"><%=freezeSuccess%></font><%}%>
									</td>
								</tr>
								<%}} %>		
							</table>	
							</s:form>						
							<div id="data_submited" style="display:block;text-align:center;">&nbsp;</div>
							<div id="server-result" style="display:block;text-align:center;">&nbsp;<font color="red" size="2"><s:actionerror /></font></div>				
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
<%@include file="../jsp/MRDEditRecord.html"%>
	<SCRIPT type="text/javascript">
	$(document).ready(function(){	
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
	}); 
	$(function(){
		$(".wrapper1").scroll(function(){
		    $(".wrapper2")
		        .scrollLeft($(".wrapper1").scrollLeft());
		});
		$(".wrapper2").scroll(function(){
		    $(".wrapper1")
		        .scrollLeft($(".wrapper2").scrollLeft());
		});
		});

	 function fnRefreshPage(){
	 	 //alert('fnRefreshPage');
		location.reload(true);
	 }
	</script>
</body>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>	
<%}catch(Exception e){
	//e.printStackTrace();
%>
</script>
<script type="text/javascript">
document.location.href="logout.action";
</script>
<%} %>
</html>