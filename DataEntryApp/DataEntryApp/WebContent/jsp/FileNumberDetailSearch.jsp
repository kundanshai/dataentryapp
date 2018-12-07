<!--

JSP page for File Search Screen as per jtrac DJB-4326

@author Arnab Nandy (Tata Consultancy Services)
@since 19-01-2016
@history 18-02-2016 Arnab edited in {@link #fnSetSelectStatus())} and in 
document ready function as per Jtrac DJB-4313.

 -->

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%
	try {
%>
<head>
<title>File Number Detail Search Screen - Revenue Management System, Delhi
Jal Board</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="description"
	content="Revenue Management System,Delhi Jal Board ">
<meta name="copyright" content="&copy;2012 Delhi Jal Board">
<meta name="author" content="Tata Consultancy Services">
<meta name="keywords"
	content="djb,DJB,Revenue Management System,Delhi Jal Board">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="googlebot" content="noarchive">
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/custom.css"/>" />
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script language=javascript>
	var totalNoOfAccount=0;
	var validUser=1;
	var validFNO=0;
    function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	var isModified=false;
	function fnValidateDate(obj){
 	 		if(Trim(obj.value).length!=0){
 	 			validateDate(obj);
 	 		}
 	 		if(compareDates(todaysDate,Trim(obj.value)) && todaysDate!=Trim(obj.value)){
				alert('Future Date is not Allowed !.');
				obj.focus();
			}
 		}

	function fnValidateUser(){
		if(document.getElementById('selectedZROCode').value == '')
 		{
 			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select ZRO location.</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
 		}
		fnCheckZROforUser();
	}
	function fnValidateFNO(){
		if(document.getElementById('fileNo').value == '')
 		{
 			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter File Number.</font>";
			document.forms[0].fileNo.focus();
			return false;
 		}
 		fnCheckValidFNO();
	}
	function fnCheckValidFNO(){
		var fileNo=document.getElementById('fileNo').value;
		
		var url = "fileNumberDetailSearchAJax.action?hidAction=checkFNO&fileNo="+fileNo;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null) {
			hideScreen();
			//alert('url>>'+url);                 
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					
					showScreen();
					if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('onscreenMessage').innerHTML=responseString;
						validFNO=0;
						}
					else
					{
						validFNO=1;
						document.getElementById('onscreenMessage').innerHTML='';	
					}
				}
			};
			httpBowserObject.send(null);
		}
	}
	function fnCheckZROforUser(){
		
		var msg;
		var selectedZROCode=document.getElementById('selectedZROCode').value;
		
			var url = "fileNumberDetailSearchAJax.action?hidAction=checkZRO&selectedZROCode="+selectedZROCode;
			//alert('url>>'+url);
			var httpBowserObject = null;
			if (window.ActiveXObject) {
				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				httpBowserObject = new XMLHttpRequest();
			} else {
				alert("Browser does not support AJAX...........");
				return;
			}
			if (httpBowserObject != null) {
				hideScreen();
				//alert('url>>'+url);                 
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						
						//alert('responseString:'+responseString);
						showScreen();
						//alert(responseString);
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
							//document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><li><span><b>ERROR:Sorry, This ZRO location is not yet allocated.</b></font>";
							document.getElementById('onscreenMessage').innerHTML=responseString;
							}
						else
						{   document.getElementById('selectedUserTD').innerHTML = responseString;
							document.getElementById('onscreenMessage').innerHTML='';	
						}
					}
				};
				httpBowserObject.send(null);
			}
		
	}			
 	function fnGoToSaveStatus(){
 		if(document.getElementById('actionStatus').value == '')
 		{
 			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select an action.</font>";
			document.forms[0].actionStatus.focus();
			return false;
 		}
 		
		if (totalNoOfAccount==null || totalNoOfAccount=='' ||totalNoOfAccount == 0) {
			alert("Error: \"please select file numbers\"");
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select at least One File from the list.</font>";
			window.scrollTo(0,0);	
			return ;
		}
		fnSaveFileStatus();
	}
	function fnGoToGenerateLot(){
		//alert('inside'+totalNoOfAccount);
		if (totalNoOfAccount == 0) {
			alert("Error: \"please select file numbers for the lot\"");
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select at least One File from the list.</font>";
			window.scrollTo(0,0);	
			return ;
		}
		fnGenerateLot();
	}
	function fnSaveFileStatus(){
		var selectedFileNo=document.getElementById('selectedFileNo').value;
		var removedFileNo=document.getElementById('removedFileNo').value;
		var actionStatus=document.getElementById('actionStatus').value;
		//alert(selectedFileNo);
		var msg;
		if(totalNoOfAccount && totalNoOfAccount>0){
			msg="Are You Sure You Want to Save the status of the Selected "+totalNoOfAccount+" File(s)?\nClick OK to continue else Cancel.";
		}else{
			msg="Are You Sure You Want to Save the status of the Selected Accounts(s)?\nClick OK to continue else Cancel.";
		}
		if(confirm(msg)){
			var url = "fileNumberDetailSearchAJax.action?hidAction=saveStatus&selectedFileNo="+ selectedFileNo+"&removedFileNo="+removedFileNo;
			url+="&actionStatus="+actionStatus;
			var httpBowserObject = null;
			if (window.ActiveXObject) {
				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				httpBowserObject = new XMLHttpRequest();
			} else {
				alert("Browser does not support AJAX...........");
				return;
			}
			if (httpBowserObject != null) {
				hideScreen();
				//alert('url>>'+url);                 
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						
						showScreen();
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML=responseString;
						}
						else
						{
							document.getElementById('onscreenMessage').innerHTML=responseString;	
							gotoSearch();
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}			
	function fnGenerateLot(){
		var selectedFileNo=document.getElementById('selectedFileNo').value;
		var removedFileNo=document.getElementById('removedFileNo').value;
		var msg;
		if(totalNoOfAccount && totalNoOfAccount>0){
			msg="Are You Sure You Want to Generate Lot of the Selected "+totalNoOfAccount+" File(s)?\nClick OK to continue else Cancel.";
		}else{
			msg="Are You Sure You Want to Generate Lot of the Selected Accounts(s)?\nClick OK to continue else Cancel.";
		}
		if(confirm(msg)){
			var url = "fileNumberDetailSearchAJax.action?hidAction=generateLot&selectedFileNo="+ selectedFileNo+"&removedFileNo="+removedFileNo;
			var httpBowserObject = null;
			if (window.ActiveXObject) {
				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				httpBowserObject = new XMLHttpRequest();
			} else {
				alert("Browser does not support AJAX...........");
				return;
			}
			if (httpBowserObject != null) {
				hideScreen();
				//alert('url>>'+url);                 
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						var res=null;
						//alert('responseString:'+responseString);
						showScreen();
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML=responseString;
							//document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><li><span><b>ERROR:Sorry, There was problem while Processing Last Operation.</b></font>";		
						}
						else
						{
							document.getElementById('onscreenMessage').innerHTML=responseString;
							var lotNoResponse=responseString.split(":");
							document.getElementById("lotNo").value=lotNoResponse[1];
							alert("Generated Lot No. is "+lotNoResponse[1]);
							$("#prt").show();
							gotoSearch();
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}	
	function fnSetCheckBox(selectedFileNoList){
		var checkboxId = "";
		var fileId = "";
		if (document.getElementById('searchResult')) {
			var rows = document.getElementById('searchResult').rows;
			for ( var i = 1; i < rows.length - 2; i++) {
				checkboxId = "trCheckbox" + i;
				fileId = "trFileNo" + i;
				if((selectedFileNoList).indexOf(document.getElementById(fileId).value)>-1){
					document.getElementById(checkboxId).checked=true;
				}else{
					document.getElementById(checkboxId).checked=false;
				}				
			}
		} else {
			var serverMsg = document.getElementById('onscreenMessage').innerHTML;
			if (serverMsg == '') {
				message = "Please provide a Search Criteria and click Search button";
			}
		}
		totalNoOfAccount = Trim(document.getElementById('totalRecordsSelected').value);
	}
 	function gotoSearch(){
 	 	
 		if (document.forms[0].selectedZROCode && Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location.</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}		
		/*if (document.forms[0].fileNo && Trim(document.forms[0].fileNo.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Error: \"File number cannot be left blank\"</font>";
			document.forms[0].fileNo.focus();
			return false;
		}	*/	
	 	if(document.forms[0].pageNo){
 			document.forms[0].pageNo.value="1";
	 	}
	 	if(document.getElementById('totalRecordsSelected')){
	 		document.getElementById('totalRecordsSelected').value="0";
	 	}
	 	fnSearch();
 	}	
 	
 	
 	function isNumberKey(evt)
    {
       var charCode = (evt.which) ? evt.which : evt.keyCode;
       if (charCode != 46 && charCode > 31 
         && (charCode < 48 || charCode > 57))
       {
    	   document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Error: \"Only number are allowed\"</font>";
          return false;
       }
       return true;
    }
	function fnSearch() {
		document.forms[0].action = "fileNumberDetailSearch.action";
		document.forms[0].hidAction.value = "search";
		document.getElementById('onscreenMessage').innerHTML = "";	
		hideScreen();
		document.forms[0].submit();
	}
	function fnValidateDate(obj){
	 		if(Trim(obj.value).length!=0){
	 			validateDate(obj);
	 		}
	 		if(compareDates(todaysDate,Trim(obj.value)) && todaysDate!=Trim(obj.value)){
			alert('Future Date is not Allowed !.');
			obj.focus();
		}
	}
	
	function gotoPreviousPage(){
		document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
 		if (Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location .</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}
		document.forms[0].action = "fileNumberDetailSearch.action";
		document.forms[0].hidAction.value = "Prev";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
	}
	function gotoNextPage(){
	 	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
	 	if (Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location .</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}
		document.forms[0].action = "fileNumberDetailSearch.action";
		document.forms[0].hidAction.value = "Next";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
 	}
	function fnRefreshSearch() {
		if (Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location .</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}
		document.forms[0].action = "fileNumberDetailSearch.action";
		document.forms[0].hidAction.value = "refresh";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
	}
	function fnSetSelectStatus() {
		var message = "Loaded successfully";
		var fileSelected = Trim(document.getElementById('totalRecordsSelected').value);
		var checkboxId = "";
		var lotNoId = "";
		var fileId = "";
		if (document.getElementById('searchResult')) {
			var rows = document.getElementById('searchResult').rows;
			// Start : Added by Arnab Nandy (1227971) on 18-02-2016 as per Jtrac DJB-4313
			document.getElementById("selectedFileNo").value="";
			// End : Added by Arnab Nandy (1227971) on 18-02-2016 as per Jtrac DJB-4313
			for ( var i = 1; i < rows.length - 2; i++) {
				checkboxId = "trCheckbox" + i;
				fileId = "trFileNo" + i;
				lotNoId = "trLotNo" + i;
				if (document.getElementById(checkboxId).checked ) {
					document.getElementById("removedFileNo").value=(document.getElementById("removedFileNo").value).replace(document.getElementById(fileId).value,'');
					if((document.getElementById("selectedFileNo").value).indexOf(document.getElementById(fileId).value)==-1){
						document.getElementById("selectedFileNo").value=document.getElementById("selectedFileNo").value+document.getElementById(fileId).value+",";
					}
					if((document.getElementById("selectedFileNoList").value).indexOf(document.getElementById(fileId).value)==-1){
						fileSelected++;
					}
				}else{
					document.getElementById("selectedFileNo").value=(document.getElementById("selectedFileNo").value).replace(document.getElementById(fileId).value,'');
					if((document.getElementById("selectedFileNoList").value).indexOf(document.getElementById(fileId).value)>-1){
						document.getElementById("removedFileNo").value=document.getElementById("removedFileNo").value+document.getElementById(fileId).value+",";	 	
						fileSelected--;
					}
				}
			}
			message = "Total No. of Selected Account(s) = " + parseInt(fileSelected);
			totalNoOfAccount=parseInt(fileSelected);
		} else {
			var serverMsg = document.getElementById('onscreenMessage').innerHTML;
			if (serverMsg == '') {
				message = "Please provide a Search Criteria and click Search button";
			}
		}
		setStatusBarMessage(message);		
		document.getElementById('totalRecordsSpan').innerHTML = "<font color='blue' size='2'>"+message+"</font>";
		document.getElementById('onscreenMessage').innerHTML = "<font color='blue' size='2'>"+message+"</font>";
	}
	function printDiv(divName) 
	{
		 var printContents = document.getElementById(divName).innerHTML;
		 var originalContents = document.body.innerHTML;

		 document.body.innerHTML = printContents;

		 window.print();

		 document.body.innerHTML = originalContents;
	}
</script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
			</tr>
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/TopMenu.html"%></td>
			</tr>
			<tr>
				<td valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font><%=(null == session.getAttribute("AJAX_MESSAGE"))
						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form  id="fileNumberSearchForm" method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="selectedFileNoList" id="selectedFileNoList" />
					<s:hidden name="selectedStatusCode" id="selectedStatusCode" />
					<s:hidden name="selectedFileNo" id="selectedFileNo" />
					<s:hidden name="removedFileNo" id="removedFileNo" />
					<s:hidden name="totalRecordsSelected" id="totalRecordsSelected" />
					<s:hidden name="totalRecords" id="totalRecords" />
					<s:if test="hidAction==null||hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Search Parameter</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="right"><label>ZRO Location</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
											list="#session.ZROListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" onchange="fnValidateUser();" name="selectedZROCode" id="selectedZROCode" />
										</td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>
										<td align="right" width="20%" style="width: 172px"><label>Logged From Date</label></td>
										<td align="left" width="15%" title="Logged from Date" id="loggedFromDate" style="width: 323px">
										  <s:textfield
											name="loggedFromDate" id="loggedFromDate" cssClass="textbox"
											style="text-align:center;" maxlength="10" theme="simple"
											onblur="fnValidateDate(this);" /> (DD/MM/YYYY)<img
											src="/DataEntryApp/images/info-bulb.gif" height="20"
											border="0"
											title="Logged from Date" />
										</td>
									</tr>
									<tr>
										<td align="right" width="10%"><label>Logged By</label></td>
										<td align="left" id="selectedUserTD"><s:select list="#session.userList" headerKey=""
												headerValue="Please Select" 
												cssClass="selectbox" theme="simple"
												 name="selectedUser"
												id="selectedUser"/>
										</td>
									    <td></td>
										<td align="right" width="20%" style="width: 172px"><label>Logged To Date</label><font
											color="red"></font></td>
										<td align="left" width="15%" title="Logged to Date" style="width: 323px">
										  <s:textfield
											name="loggedToDate" id="loggedToDate" cssClass="textbox"
											style="text-align:center;" maxlength="10" theme="simple"
											onblur="fnValidateDate(this);" /> (DD/MM/YYYY)<img
											src="/DataEntryApp/images/info-bulb.gif" height="20"
											border="0"
											title="Logged from Date" />
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="width: 172px"><label>File No</label></td>
										<td align="left" width="15%" title="File No" style="width: 323px">
										  <input type="text" class="textbox"
											name="fileNo" id="fileNo" onkeypress="return isNumberKey(event)"
											maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>" /> 
										</td>
										<td align="left">&nbsp;</td>
										<td align="right"><label>Status</label></td>
										<td align="left" title="File Search Status" id="fileSearchStatusTD"><s:select
											list="#session.fileSearchStatusListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox"
											theme="simple" name="fileSearchStatus" id="fileSearchStatus" />
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="width: 172px"><label>Lot No</label></td>
										<td align="left" width="15%" title="Lot No" style="width: 323px">
										<input type="text" class="textbox"
											name="lotNo" id="lotNo" onkeypress="return isNumberKey(event)" 
											 maxlength="<%=DJBConstants.LOT_NO_MAX_LENGTH%>"
											/></td>
									</tr>
									<tr>
										<td align="left">&nbsp;</td>
										<td align="right"><input type="button"
										value="     Reset    " class="groovybutton"
										onclick="clearAllFields();" /></td>
										<td align="left">&nbsp;</td>
										<td align="left" colspan="2"><s:submit
											key="    Search    " cssClass="groovybutton" theme="simple"
											id="btnSearch" /></td>
									</tr>
									<tr>
										<td align="left" colspan="3">&nbsp;</td>
										<td align="right" colspan="3"><font color="red"><sup>*</sup></font>
										marked fields are mandatory.</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
							<s:if test="fileNumberSearchDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>
									<div id="printableArea">
									<table id="searchResult" class="table-grid">
										<tr>
											<th align="center" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="5" width="80%">Page No:<s:select
												list="pageNoDropdownList" name="pageNo" id="pageNo"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" /> Showing maximum <s:select
												list="{10,20,30,40,50,100,200,500,1000}"
												name="maxRecordPerPage" id="maxRecordPerPage"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" />records per page of total <s:property
												value="totalRecords" /> records.</th>
											<th align="right" colspan="2" width="10%"><s:if
												test="pageNo*maxRecordPerPage< totalRecords">
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													onclick="javascript:gotoNextPage();" />
											</s:if><s:else>
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
										</tr>
										<tr>
											<th align="center" width="5%"><input type="checkbox"
												id="selectall" /></th>
											<th align="center" width="5%">SL</th>
											<th align="center" width="10%">ARN</th>
											<th align="center" width="10%">File No</th>
											<th align="center" width="20%">Applicant Name</th>
											<th align="center" width="20%">Status</th>
											<th align="center" width="10%">Logged By</th>
											<th align="center" width="10%">Log Date</th>
											<th align="center" width="10%">Lot No</th>
										</tr>
										<s:iterator value="fileNumberSearchDetailsList" status="row">
											<tr bgcolor="white"
												onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'">
												<td align="center" title="<s:property value="fileNo" />"><input
													type="checkbox" class="case" name="case"
													id="trCheckbox<s:property value="#row.count"/>" /><input
													type="hidden" name="trFileNo<s:property value="#row.count" />"
													id="trFileNo<s:property value="#row.count" />"
													value="<s:property value="fileNo" />" />
													<input
													type="hidden" name="trLotNo<s:property value="#row.count" />"
													id="trLotNo<s:property value="#row.count" />"
													value="<s:property value="lotNo" />" /></td>
												<td align="center"><s:property value="slNo" /></td>
												<td align="center"><s:property value="arn" /></td>
												<td align="center" nowrap>&nbsp;<s:property
													value="fileNo" /></td>
												<td align="left"><s:property value="consumerName" />&nbsp;</td>
												<td align="center" nowrap><s:property value="statusCD" />&nbsp;</td>
												<td align="center" nowrap><s:property value="loggedBy" />&nbsp;</td>
												<td align="center"><s:property value="loggedDate" />&nbsp;</td>
												<td align="center"><s:property value="lotNo" />&nbsp;</td>
											</tr>
										</s:iterator>
										<tr>
											<th align="center" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="5" width="80%"><b><font
												color="blue"><span id="totalRecordsSpan">Total
											No. of Selected File(s) = <s:if
												test="selectedFNOList!=null">
												<s:property value="selectedFNOList.size" />
											</s:if><s:else>0</s:else></span></font></b></th>
											<th align="right" colspan="2" width="10%"><s:if
												test="pageNo*maxRecordPerPage< totalRecords">
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													onclick="javascript:gotoNextPage();" />
											</s:if><s:else>
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
										</tr>
									</table>
									</div>
									<table>
									<tr><td>&nbsp;</td></tr>
									<tr>
									    <td align="center">&nbsp;</td>
										<td align="right"><s:select
											list="#session.fileSearchSaveStatusListMap" headerKey=""
											headerValue="Select Action" cssClass="selectbox"
											theme="simple" name="actionStatus" id="actionStatus" /></td>
										<td align="center">&nbsp;</td>
										<td align="right"><input type="button"
										value="  Save Action  " class="groovybutton"
										onclick="fnGoToSaveStatus();" /></td>
										<td align="right">&nbsp;</td>
										<td align="left" colspan="2"><input type="button"
										value="  Generate Lot  " class="groovybutton" 
										onclick="fnGoToGenerateLot();" /></td>
										<td align="right">&nbsp;</td>
										<td align="left" colspan="2"><input type="button" 
										class="groovybutton" onclick="printDiv('printableArea')" 
										value="       Print      " id="prt"/>
										</td>
									</tr>
									<tr><td>&nbsp;</td></tr>
									</table>
									</fieldset>
									</td>
									</tr>
							</s:if>
							<s:else>
								<s:if
									test="hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
									<tr>
										<td align="center" valign="top" id="searchBox">
										<fieldset><legend>Search Result</legend>
										<table class="table-grid">
											<tr>
												<td align="center" colspan="8" /><font color="red">No
												Records Found to Display.</font></td>
											</tr>
										</table>
										<br />
										</fieldset>
										</td>
									</tr>
								</s:if>
							</s:else>
							
						</table>
						</s:if>
				</s:form></td>
			</tr>
			<tr height="20px">
				<td align="center" valign="bottom"><%@include
					file="../jsp/Footer.html"%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
        function clearAllFields(){
            document.location.href="fileNumberDetailSearch.action";
   		}

		$(document).ready(function(){
			//$("#prt").hide();
			$('#lotNo').bind("cut copy paste",function(e) {
				document.forms[0].lotNo.focus();
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Error: \"Only number are allowed\"</font>";
			     e.preventDefault();
			 	});
			$('#fileNo').bind("cut copy paste",function(e) {
				document.forms[0].fileNo.focus();
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Error: \"Only number are allowed\"</font>";
			     e.preventDefault();
			 	});
			// Start : Edited by Arnab Nandy (1227971) on 18-02-2016 as per Jtrac DJB-4313
		var hidAction=document.forms[0].hidAction.value;
		if(hidAction==''||hidAction==null||hidAction=='search'||hidAction=='Next'||hidAction=='Prev'||hidAction=='refresh'){
			if((document.getElementById("selectedFileNoList").value).length>10){
				fnSetCheckBox(document.getElementById("selectedFileNoList").value);
			}
			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");	
				document.getElementById("selectedFileNo").value="";					
			}
		}
	});
	$(function() { // add multiple select / deselect functionality     
		$("#selectall").click(function() {
			$('.case').attr('checked', this.checked);
			fnSetSelectStatus();
			isModified=true;
		}); // if all checkbox are selected, check the selectall checkbox     // and viceversa     
		$(".case").click(function() {
			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
				document.getElementById("selectedFileNo").value="";			
				// End : Edited by Arnab Nandy (1227971) on 18-02-2016 as per Jtrac DJB-4313	
			}
			fnSetSelectStatus();
			isModified=true;
		});
	});
	$("#fileNumberSearchForm").submit(function(e) {
	gotoSearch();
	});
	
</script>
<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>