<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%
	try {
%>
<head>
<title>Meter Reader Maintenance Page - Revenue Management
System, Delhi Jal Board</title>
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/jQuery/css/jquery.ui.all.css"/>" />
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery-1.8.0.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.core.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.widget.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.effects.core.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.effects.slide.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.datepicker.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
$(function() {
    	 var todaysDate = new Date();
       /*  var maxDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate()); */
  		$( "#bypassFromDate" ).datepicker({
  			//numberOfMonths: 3,
  			showButtonPanel: true,
  			onClose: function( selectedDate ) {
	        	$( "#bypassToDate" ).datepicker( "option", "minDate", selectedDate );
	      	}
  		});
  		$( "#bypassToDate" ).datepicker({
  			//numberOfMonths: 3,
  			showButtonPanel: true,
  			onClose: function( selectedDate ) {
	        	$( "#bypassFromDate" ).datepicker( "option", "maxDate", selectedDate );
	      	}
  		});  		
 });  
	var isModified=false,isProcessing=false;
	function fnAddMeterReader(){
			//if(Trim(document.forms[0].selectedZone.value)!=''){
			//document.getElementById('meterReaderZone').value=Trim(document.forms[0].selectedZone.value);
			//document.getElementById('onscreenMessage').innerHTML = "";
			//	document.getElementById('meterReaderZone').value=Trim(document.forms[0].selectedZone.value);
			document.getElementById('meterReaderZroCd').value="";
			document.getElementById('meterReaderID').value="";
			document.getElementById('meterReaderName').value="";
			document.getElementById('hhdId').value="";
			document.getElementById('meterReaderEmployeeID').value="";
			document.getElementById('meterReaderMobileNo').value="";
			document.getElementById('meterReaderEmail').value="";
			document.getElementById('meterReaderDesignation').value="";
			document.getElementById('popUpPurpose').value="addMeterReader";
			isModified=false;
			//popupMeterReaderMaintenanceEditScreen();
			fnPopulateSupervisorEmpIdNHhdList('','',false);
			//}else{
				//document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Zone</font>";
				//document.forms[0].selectedZone.focus();
			//}
	}
	



	var hhdIdVar="";
	var supervisorEmpIdVar='';
	function fnEdit(meterReaderID,meterReaderEmployeeID,meterReaderName,meterReaderMobileNo,meterReaderEmail,meterReaderDesignation,popUpPurpose,supervisorEmpId,hhdId,activeInactiveRemark,MeterReaderStatus,meterReaderZroCd,hhdId){
		//alert(meterReaderID+'>>'+meterReaderEmployeeID+'>>'+meterReaderName+'>>'+meterReaderMobileNo+'>>'+meterReaderEmail+'>>'+meterReaderDesignation+'>>'+popUpPurpose+'>>'+supervisorEmpId+'>>'+hhdId+'>>'+activeInactiveRemark+'>>'+MeterReaderStatus+'>>'+meterReaderZroCd+'>>'+hhdId);
		//document.getElementById('meterReaderZone').value=Trim(document.forms[0].selectedZone.value);
		document.getElementById('meterReaderID').value=meterReaderID;
		document.getElementById('meterReaderName').value=meterReaderName=='NA'?"":meterReaderName;
		document.getElementById('meterReaderEmployeeID').value=meterReaderEmployeeID=='NA'?"":meterReaderEmployeeID;
		document.getElementById('meterReaderMobileNo').value=meterReaderMobileNo=='NA'?"":meterReaderMobileNo;
		document.getElementById('meterReaderEmail').value=meterReaderEmail=='NA'?"":meterReaderEmail;
		document.getElementById('meterReaderDesignation').value=meterReaderDesignation=='NA'?"":meterReaderDesignation;
		document.getElementById('popUpPurpose').value=popUpPurpose;
		//Added by Rajib
		document.getElementById('supervisorEmpId').value=supervisorEmpId;
		supervisorEmpIdVar=supervisorEmpId;
		document.getElementById('hhdId').value=hhdId;
		hhdIdVar=hhdId;
		document.getElementById('activeInactiveRemark').value=activeInactiveRemark;
		document.getElementById('MeterReaderStatus').value=MeterReaderStatus;
		document.getElementById('meterReaderZroCd').value=meterReaderZroCd;
		fnPopulateSupervisorEmpIdNHhdList(meterReaderZroCd,hhdId,false);
		
		isModified=false;
		//popupMeterReaderMaintenanceEditScreen();
	}
	function fnResetSearch(){
		document.getElementById('searchResult').innerHTML = "<font color='blue'>Please Select a ZRO Location and Click Search Button.</font><br/>";
	}
	function fnRefreshSearch(){
		/*	if(Trim(document.getElementById('meterReaderZone').value)!=''){
			document.forms[0].selectedZone.value=Trim(document.getElementById('meterReaderZone').value);
		}  */
		fnSearch();
	}
 	function gotoSearch(){
 		if (document.forms[0].selectedZroCd &&Trim(document.forms[0].selectedZroCd.value) == '' &&document.forms[0].meterReaderEmployeeIdSrc &&Trim(document.forms[0].meterReaderEmployeeIdSrc.value) == '' &&document.forms[0].meterReaderHhdIdSrc &&Trim(document.forms[0].meterReaderHhdIdSrc.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Enter a Employee Id or HHD Id to Proceed.</font>";
			document.forms[0].selectedZroCd.focus();
			return false;
		}
	 	fnSearch();
 	}	
	function fnSearch() {
		//var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedZroCd= Trim(document.getElementById('selectedZroCd').value); selectedMeterReaderStatus
		var selectedMeterReaderStatus= Trim(document.getElementById('selectedMeterReaderStatus').value);
		var meterReaderEmployeeID= Trim(document.getElementById('meterReaderEmployeeIdSrc').value);
		//START: Added by Rajib as per Jtrac DJB-3871 on 14-OCT-2015
		var meterReaderHhdIdSrc= Trim(document.getElementById('meterReaderHhdIdSrc').value);
		//END: Added by Rajib as per Jtrac DJB-3871 on 14-OCT-2015
		document.getElementById('onscreenMessage').innerHTML = "";
		if ('' != selectedZroCd ||''!=meterReaderEmployeeID || ''!=meterReaderHhdIdSrc) {
			if(document.getElementById('searchHistoryDiv')){
				document.getElementById('searchHistoryDiv').style.display='none';
				document.getElementById('viewHistory').style.display='none';
			}
			var url = "meterReaderMaintenanceAJax.action?hidAction=search&meterReaderZroCd="
						+ selectedZroCd+"&meterReaderEmployeeID="+meterReaderEmployeeID;
			if (null != selectedMeterReaderStatus && '' != selectedMeterReaderStatus){
				url+= "&meterReaderStatus="+selectedMeterReaderStatus;
			}
			//
			if (null != meterReaderHhdIdSrc && '' != meterReaderHhdIdSrc){
				url+= "&hhdId="+meterReaderHhdIdSrc;
			}
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
				//alert('url>>'+url);
				document.getElementById('searchResultLoading').style.display='block';
				document.getElementById('searchResultDiv').style.display='block';
				document.getElementById('searchResult').innerHTML = "";
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert(httpBowserObject.responseText);
						if (null != responseString
								&& responseString.indexOf('<script') == -1) {			
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
								document.getElementById('onscreenMessage').title = "";
							} else {
								document.getElementById('searchResultLoading').style.display='none';
								document.getElementById('searchResult').innerHTML = responseString;
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnSearchHistory(selectedMeterReaderCode) {
		document.getElementById('onscreenMessage').innerHTML = "";
		if ('' != selectedMeterReaderCode) {
			var url = "meterReaderMaintenanceAJax.action?hidAction=searchHistory&meterReaderID="
					+ selectedMeterReaderCode;
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
				//alert('url>>'+url);
				document.getElementById('searchResultLoading').style.display='none';
				document.getElementById('searchResultDiv').style.display='none';
				document.getElementById('viewResultLoading').style.display='block';
				//document.getElementById('searchResult').innerHTML = "";
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert(httpBowserObject.responseText);
						if (null != responseString
								&& responseString.indexOf('<script') == -1) {			
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
								document.getElementById('onscreenMessage').title = "";
							} else {
								document.getElementById('searchHistoryDiv').style.display='block';
								document.getElementById('viewHistory').style.display='block';
								document.getElementById('viewHistory').innerHTML = responseString; 
								document.getElementById('viewResultLoading').style.display='none';
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
							document.getElementById('searchResultLoading').style.display='block';
							document.getElementById('searchResultDiv').style.display='block';
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnEnableField(obj){
		obj.disabled=false;
	}
	function fnDisableField(obj){
		obj.disabled=true;
	}
	function fnDisableAllButton(){
		var inputs = document.getElementsByTagName("INPUT");
		for (var i = 0; i < inputs.length; i++) {
		    if (inputs[i].type == 'button') {
		        inputs[i].disabled = true;
		    }
		}
	}
</script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<%@include file="../jsp/MeterReaderMaintenanceEdit.html"%>
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
				<s:form method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="page" id="page" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Search Criteria</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right"><label>ZRO Location</label><font
										color="red"><sup>**</sup></font></td>
									<td align="left" title="Zro Location" width="10%"><s:select
										list="#session.ZROListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZroCd" id="selectedZroCd"/></td>
									<td align='right' nowrap>Employee Id<font color="red"><sup>**</sup></font></td>
									<td align='left'><input type="text"
										name="meterReaderEmployeeIdSrc" id="meterReaderEmployeeIdSrc"
										maxlength="20" style="text-align: left;" class="textbox" /></td>
									<td align="right"><label>HHD Id </label><font color="red"><sup>**</sup></font></td>
									<td align='left'><input type="text"
										name="meterReaderHhdIdSrc" id="meterReaderHhdIdSrc"
										maxlength="20" style="text-align: left;" class="textbox" /></td>
									<td align="right"><label>ACTIVE</label><font color="red"><sup></sup></font></td>
									<td align='left' title="Meter Reader Status"><s:select
										list="#session.ActiveInactiveListMap" cssClass="selectbox"
										theme="simple" name="selectedMeterReaderStatus"
										id="selectedMeterReaderStatus" /></td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								<td align="left" colspan="2"><font color="red"><sup>**</sup></font>
									marked fields should be selected at least one.</td>
								<td>&nbsp;</td>
								<td align="left"><s:submit key="    Search    "
										cssClass="groovybutton" theme="simple" id="btnSearch" /></td>
								<td align="left"><input type="button" id="btnAdd"
										value="Add Meter Reader" class="groovybutton"
										onclick="fnAddMeterReader();" /></td>
									
									<td align="left"><input type="button" id="btnTagMRD"
										value="Tag MRD" class="groovybutton"
										onclick="fnGotoMRDTagging();" /></td>
								<td>&nbsp;</td>		
								</tr>
						
							</table>
							</fieldset>
							</td>
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="searchResultDiv" style="display: none;">
							<fieldset><legend>Search Result</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="searchResultLoading" style="display: none;"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading" id="imgLoading" /></div>
									<div id="searchResult"></div>
									</td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
							<td>
							<div id="searchHistoryDiv" style="display: none;">
							<fieldset><legend>History Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="viewResultLoading" style="display: none;"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading" id="imgLoadingHistory" /></div>
									<div id="viewHistory"></div>
									</td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
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
	$(document).ready(function(){
		$('input[type="text"]').focus(function() { 
			$(this).addClass("textboxfocus"); 
		});   
		$('input[type="text"]').blur(function() {  					
			$(this).removeClass("textboxfocus"); 
			$(this).addClass("textbox"); 
		});
		if(document.forms[0].page && document.forms[0].page.value=='add'){
			fnAddMeterReader();
		}
		 var todaysDate = new Date();
         var minDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate());
        $( "#bypassFromDate" ).datepicker( "option", "minDate", minDate);
		$( "#bypassFromDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy');
		$( "#bypassToDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy');
		$( "#bypassToDate" ).datepicker( "option", "minDate", minDate);
		
	});
	$("form").submit(function(e) {
		gotoSearch();
	});
	$('#meterReaderName').keyup(function(){
	    this.value = this.value.toUpperCase();
	});
	$(document).keypress(function(e) {
	    /*if(e.which == 13) {
	        alert('You pressed enter!');
	    }*/
	    if(e.which == 27) {
		    if(!isProcessing){
	    		hideMeterReaderMaintenanceEditScreen();
		    }else{
			    alert('Please wait while processing your request!');
		    }
	    }
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