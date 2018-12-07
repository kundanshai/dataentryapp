<!--
@history 07-06-2016 Lovely (986018)added a validation method validateUserAccess() as per jtrac-DJB 4482 and openProject-1206.
-->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>   
<html>
<%
	try {
%>
<head>
<title>MRD Search-Revenue Management System,Delhi Jal Board</title>
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script language=javascript>
		function disableBack()
		{
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
 	</script>
<script type="text/javascript">
		var todaysDate="<%=(String) session.getAttribute("TodaysDate")%>";
		var lastSubmit="<%=session.getAttribute("LAST_SUBMIT")%>";
		var lastActionForMRD="<%=session.getAttribute("LAST_ACTION_FOR_MRD")%>";
		
 		function fnOnSubmit(){
 			document.getElementById('onscreenMessage').innerHTML="";
 			if(Trim(document.forms[0].selectedArea.value)==''||Trim(document.forms[0].selectedBillWindow.value)==''){
 	 			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><li><span><b>Please Enter mandatory Fields to Proceed.</b></span></li></font>";
 	 			return;
 	 		} 
 			if(Trim(document.forms[0].selectedArea.value)!=''){
 				document.forms[0].selectedAreaDesc.value = getSelectedText('selectedArea');
 			}else{
 				document.forms[0].selectedAreaDesc.value ="";
 			}
 			 validateUserAccess();
 		}
 	// Start: added by Lovely(986018) on 06-06-2016 as per for validating and giving access to users as per jtrac-DJB 4482 and openProject-1206.
 		function validateUserAccess (){
 			var userRole="<%=(String) session.getAttribute("userRoleId")%>";
 			var amrRole="<%=DJBConstants.AMR_ROLE%>";
 			var msg;
 			var selectedZone=(Trim(document.forms[0].selectedZone.value));
 			var selectedMRNo=(Trim(document.forms[0].selectedMRNo.value));
 			var selectedArea=(Trim(document.forms[0].selectedArea.value));
 			 if(amrRole==userRole){
 				document.getElementById('onscreenMessage').innerHTML = "";
			    var url = "mrdScheduleDownloadJobSubmitAJax.action?hidAction=validateMrkey&selectedZone="
								+ selectedZone + "&selectedMRNo=" + selectedMRNo + "&selectedArea=" + selectedArea;
				//alert("url"+url);
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
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {										
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b><%=DJBConstants.MRKEY_TAGGED_MSG%></font>" ;
								document.getElementById('onscreenMessage').title = "";										
							} else {
								if(responseString.indexOf("validMrkey") > -1){
									isValid = true;
									document.forms[0].action="dataEntry.action";
					       			document.forms[0].hidAction.value="search";
					       			document.forms[0].hidBillRound.value=document.forms[0].selectedBillWindow.value;
					       			document.forms[0].submit();
					       			hideScreen();
					       			return true;
									}
									else {
										isValid = false;
										return false;
									}												
							  	}									
						}
					};
					httpBowserObject.send(null);
				}						     
 			 }
 			else
			 {
				document.forms[0].action="dataEntry.action";
       			document.forms[0].hidAction.value="search";
       			//alert("NON AMR USER");
       			document.forms[0].hidBillRound.value=document.forms[0].selectedBillWindow.value;
       			document.forms[0].submit();
       			hideScreen();
       			return true;
			 }
 		}
 		//End:added by Lovely (986018) on 06-06-2016 as per jtrac-DJB 4482 and openProject-1206 .
 		function fnOnLoad(){	 			
	 		fnSetSearchFor(lastActionForMRD); 
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
 		function fnUpdate(){
 	 		if(Trim(document.forms[0].selectedArea.value)==''||Trim(document.forms[0].selectedBillWindow.value)==''||Trim(document.forms[0].defaultBillGenDate.value).length!=10){
 	 			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><li><span><b>Please Enter mandatory Fields to Proceed.</b></span></li></font>";
 	 			return;
 	 		}
 	 		document.getElementById('onscreenMessage').innerHTML="";
 	 		window.focus();
			hideScreen();
			document.body.style.overflow = 'hidden'; 	
 	 		if(confirm('This will update all the Unmetered Consumer in the MRD with the Given Meter Read Date/Bill Generation Date\n\n Are you sure, You want to Update ?')){
 	 			document.getElementById('hidAction').value="saveUnmeteredConsumerData";
 	 			var url= "callHomeAJax.action?hidAction="+document.getElementById('hidAction').value;
 				url+="&billRound="+document.forms[0].selectedBillWindow.value;
 				url+="&selectedZone="+document.forms[0].selectedZone.value;
 				url+="&selectedMRNo="+document.forms[0].selectedMRNo.value;
 				url+="&selectedArea="+document.forms[0].selectedArea.value;
 				url+="&defaultBillGenDate="+document.forms[0].defaultBillGenDate.value;
 				var httpBowserObject=null;
 				if (window.ActiveXObject){                
 					httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
 				}else if (window.XMLHttpRequest){ 
 					httpBowserObject= new XMLHttpRequest();  
 				}else {                 
 					alert("Browser does not support AJAX...........");             
 					return;
 				} 
 				if (httpBowserObject != null) {		
 					//alert('url>>'+url);                 
 					httpBowserObject.open("POST", url, true);  
 					httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 					httpBowserObject.setRequestHeader("Connection", "close");			
 					httpBowserObject.onreadystatechange = function(){
 						if(httpBowserObject.readyState == 4){
 							var  responseString= httpBowserObject.responseText;
 							//alert(httpBowserObject.responseText);
 							document.getElementById('onscreenMessage').innerHTML=responseString;
 							document.forms[0].defaultBillGenDate.value="";
 							showScreen();
 						}
 					};  
 					httpBowserObject.send(null); 
 				}else{
 					showScreen();
 				}
 	 		}else{
 	 			showScreen();
 	 		} 	 		
 		}
		function fnGetMRNo() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		if (selectedZone != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "callHomeAJax.action?hidAction=populateMRNo&selectedZone="
					+ selectedZone;
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
				showAjaxLoading(document.getElementById('imgSelectedZone'));
				document.getElementById('selectedMRNo').value = "";
				document.getElementById('selectedMRNo').disabled=true;
				document.getElementById('selectedArea').value = "";
				document.getElementById('selectedArea').disabled = true;
				//alert('url>>'+url);                 
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert(httpBowserObject.responseText);
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
							hideAjaxLoading(document
									.getElementById('imgSelectedZone'));
						} else {
							document.getElementById('mrNoTD').innerHTML = responseString;		
							hideAjaxLoading(document
									.getElementById('imgSelectedZone'));					
						}
					}
				};
				httpBowserObject.send(null);
			}
		}else{
			document.getElementById('selectedMRNo').value = "";
			document.getElementById('selectedMRNo').disabled=true;
			document.getElementById('selectedArea').value = "";
			document.getElementById('selectedArea').disabled = true;
		}
	}
	function fnGetArea() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		if ('' != selectedZone && '' != selectedMRNo) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "callHomeAJax.action?hidAction=populateArea&selectedZone="
					+ selectedZone + "&selectedMRNo=" + selectedMRNo;
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
				showAjaxLoading(document.getElementById('imgSelectedMRNo'));
				document.getElementById('selectedArea').value = "";
				document.getElementById('selectedArea').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert(httpBowserObject.responseText);
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
							document.getElementById('onscreenMessage').title = "";
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNo'));
						} else {
							document.getElementById('areaTD').innerHTML = responseString;
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNo'));
						}
					}
				};
				httpBowserObject.send(null);
			}
		}else{
			document.getElementById('selectedArea').value = "";
			document.getElementById('selectedArea').disabled = true;
		}
	}
	function fnGetOpenBillRound() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		var selectedArea = Trim(document.getElementById('selectedArea').value);
		if ('' != selectedZone && '' != selectedMRNo&& ''!=selectedArea) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "callHomeAJax.action?hidAction=populateOpenBillRound&selectedZone="
					+ selectedZone + "&selectedMRNo=" + selectedMRNo+"&selectedArea="+selectedArea;
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
				showAjaxLoading(document.getElementById('imgSelectedArea'));
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert(httpBowserObject.responseText);
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
							document.getElementById('onscreenMessage').title = "";
							hideAjaxLoading(document
									.getElementById('imgSelectedArea'));
						} else {
							if (responseString.indexOf("WARNING") > -1) {
								document.getElementById('onscreenMessage').innerHTML = responseString;
								document.getElementById('selectedBillWindow').value = "";	
							}else{
								document.getElementById('selectedBillWindow').value = Trim(responseString);
								document.forms[0].selectedBillWindow.value=	Trim(responseString);
								if(document.getElementById('selectedBillWindow').value != ''){
									document.getElementById('selectedBillWindow').disabled=true;
								}else{
									document.getElementById('selectedBillWindow').disabled=false;
								}								
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedArea'));
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnCheckZone() {
		if (document.forms[0].selectedZone.value == '') {
			//alert('Please Select Zone First');
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZone.disabled)) {
				document.forms[0].selectedZone.focus();
			}
			return;
		}
	}
	function fnCheckZoneMRNo() {
		if (document.forms[0].selectedZone.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZone.disabled)) {
				document.forms[0].selectedZone.focus();
			}
			return;
		}
		if (document.forms[0].selectedMRNo.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MR No. First</b></font>";
			if (!(document.forms[0].selectedMRNo.disabled)) {
				document.forms[0].selectedMRNo.focus();
			}
			return;
		}
	}
 	</script>
</head>
<body onload="fnOnLoad();">
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
				<td align="center" valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font></span></font>&nbsp;</div>
				<div id="search">
				<table width="100%" align="center" border="0">
					<tr>
						<td align="center" valign="top">
						<fieldset><legend><span id="searchFor">Consumer
						Search By MRD</span></legend>
						<table width="95%" align="center" border="0">
							<tr>
								<td align="center" colspan="3"><s:form method="post"
									action="javascript:void(0);" theme="simple"
									onsubmit="return false;">
									<s:hidden name="searchFor" />
									<s:hidden name="hidAction" />
									<s:hidden name="hidBillRound" />
									<s:hidden name="selectedAreaDesc" id="selectedAreaDesc" />
									<table width="80%" align="center" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td align="left" width="40%">Zone<font color="red"><sup>&nbsp;*</sup></font>
											</td>
											<td align="left" width="30%"><s:select
												list="#session.ZoneListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="selectedZone" id="selectedZone"
												onchange="fnGetMRNo();" /></td>
											<td width="20%"><img src="/DataEntryApp/images/load.gif"
												width="25" border="0" title="Processing"
												style="display: none;" id="imgSelectedZone" /></td>
										</tr>
										<tr>
											<td align="left">MR No.<font color="red"><sup>&nbsp;*</sup></font>
											</td>
											<td align="left" title="MR No" id="mrNoTD"><s:select
												list="#session.MRNoListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="selectedMRNo" id="selectedMRNo"
												onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
											<td><img src="/DataEntryApp/images/load.gif" width="25"
												border="0" title="Processing" style="display: none;"
												id="imgSelectedMRNo" /></td>
										</tr>
										<tr>
											<td align="left">Area<font color="red"><sup>&nbsp;*</sup></font>
											</td>
											<td align="left" title="Area" id="areaTD"><s:select
												list="#session.AreaListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="selectedArea" id="selectedArea"
												onfocus="fnCheckZoneMRNo();"
												onchange="fnGetOpenBillRound();" /></td>
											<td><img src="/DataEntryApp/images/load.gif" width="25"
												border="0" title="Processing" style="display: none;"
												id="imgSelectedArea" /></td>
										</tr>
										<tr>
											<td align="left">Bill window<font color="red"><sup>&nbsp;*</sup></font>
											</td>
											<td align="left"><s:select name="selectedBillWindow"
												id="selectedBillWindow" headerKey=""
												headerValue="Please Select" list="#session.billWindowList"
												cssClass="selectbox-long" theme="simple" /></td>
											<td></td>
										</tr>
										<tr id="defaultBillGenDateRow" style="display: none;"
											title="This date will be populated automatically for Unmetered Consumer in the Entry Screen.">
											<td align="left">Meter Read Date/Bill Generation Date<font
												color="red"><sup>&nbsp;*</sup></font></td>
											<td align="left" nowrap><s:textfield
												name="defaultBillGenDate" cssClass="textbox"
												style="text-align:center;" maxlength="10" theme="simple"
												onblur="fnValidateDate(this);" /> (DD/MM/YYYY)<img
												src="/DataEntryApp/images/info-bulb.gif" height="20"
												border="0"
												title="This date will be populated automatically for Unmetered Consumer in the Entry Screen." />
											</td>
											<td></td>
										</tr>
										<tr>
											<td align="center" colspan="3">&nbsp;</td>
										</tr>
										<tr id="btnSearchRow" style="display: block;">
											<td></td>
											<td align="left"><s:submit key="    Search     "
												cssClass="groovybutton" theme="simple" /></td>
											<td></td>
										</tr>
										<tr id="btnSaveRow" style="display: none;">
											<td></td>
											<td align="left" id="btnSearchRow"><s:submit
												id="btnSave" key="      Save      " cssClass="groovybutton"
												theme="simple" /></td>
											<td></td>
										</tr>
									</table>
								</s:form></td>
							</tr>
							<tr>
								<td align="left" colspan="3"><font color="red"><sup>*</sup></font>
								Marked Fields are mandatory.</td>
							</tr>
						</table>
						</fieldset>
						</td>
					</tr>
				</table>
				</div>
				</td>
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
<script language="javascript">
	$("form").submit(function(e) {
		if(document.getElementById('btnSearchRow')&&document.getElementById('btnSearchRow').style.display=='block'){
			fnOnSubmit();
		}else{
			fnUpdate();
		}
	});
</script>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>