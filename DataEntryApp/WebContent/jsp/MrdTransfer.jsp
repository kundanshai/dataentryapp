<!--
JSP page for Mrd transfer screen as per jTrac DJB-2200,Openproject- #1540 
@author Madhuri Singh (Tata Consultancy Services) 
@since 22-09-2016
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<%
	try {
%>
<head>
<title>MRD Transfer Page - Revenue Management System, Delhi
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

var errormsg="<%=DJBConstants.CRTMRD_DMND_TRNSFR_MSG %>";

  function fnGetMRNOList(){
	  fnIsDisabled();
	  var zoneMapList = document.getElementById('oldZone').value;
	  if (zoneMapList != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdTransferAJax.action?hidAction=populateMRNOList&oldZone="
					+ zoneMapList;
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
				document.getElementById('mrNo').value = "";
				document.getElementById('mrNo').disabled=true;
				document.getElementById('areaCode').value = "";
				document.getElementById('areaCode').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
						} else {
							document.getElementById('mrNoTD').innerHTML = responseString;							
						}
						
					}
				};
				httpBowserObject.send(null);
			}
		}
	}

	function fnGetAreaList() {
		fnIsDisabled();
		var zoneMapList = Trim(document.getElementById('oldZone').value);
		var mrNOList = Trim(document.getElementById('mrNo').value);
		if ('' != zoneMapList && '' != mrNOList) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdTransferAreaAJax.action?hidAction=populateAreaList&oldZone="
					+ zoneMapList + "&mrNoList=" + mrNOList;
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
				document.getElementById('areaCode').value = "";
				document.getElementById('areaCode').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						} else {
							document.getElementById('areaTD').innerHTML = responseString;
						}
						
					}
				};
				httpBowserObject.send(null);
			}
		}
	}

	function fnGetZroCodeList() {
		fnIsDisabled();
		var NewzoneMapList = Trim(document.getElementById('newZone').value);
		document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdTransferZroLocationAJax.action?hidAction=populateZroCode&newZone="
					+ NewzoneMapList;
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
				document.getElementById('newZROCode').value = "";
				document.getElementById('newZROCode').disabled=true;
				
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No ZRO Location found corresponding to the New Zone </b></font>";
							document.getElementById('onscreenMessage').title = "";
						} else {
							document.getElementById('selectedZROCodeTD').innerHTML = responseString;
						}
						
					}
				};
				httpBowserObject.send(null);
			}
		
		
	} 
	
	function fnCheckZone() {
		if (document.forms[0].oldZone.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].oldZone.disabled)) {
				document.forms[0].oldZone.focus();
			}
			return;
		}
	}
	function fnCheckZoneMRNo() {
		if (document.forms[0].oldZone.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].oldZone.disabled)) {
				document.forms[0].oldZone.focus();
			}
			return;
		}
		if (document.forms[0].mrNo.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MR No. First</b></font>";
			if (!(document.forms[0].mrNo.disabled)) {
				document.forms[0].mrNo.focus();
			}
			return;
		}
	}
	

	function fnMRDTransferAJaxCall(){
		if(confirm('Are You Sure You Want to Transfer MRD ?\nClick OK to continue else Cancel.')){
		var url="mrdTransferProcExeAJax.action?hidAction=mrdTransferProcExe";
		url+="&oldZone="+Trim(document.getElementById('oldZone').value);
		url+="&mrNoList="+Trim(document.getElementById('mrNo').value);
		url+="&areaCode="+Trim(document.getElementById('areaCode').value);
		url+="&newZone="+Trim(document.getElementById('newZone').value);
		url+="&newZRO="+Trim(document.getElementById('newZROCode').value);		
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
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						showScreen(responseString);
						//alert("responseString   "+responseString);
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
						}
						else
						{
						document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
		else{
         return false;
	 		}
	}
	function fnZroWiseMRDTransferAJaxCall(){
		if(confirm('Are You Sure You Want to Zro wise Mrd Transfer ?\nClick OK to continue else Cancel.')){
			var url="mrdTransferZroAJax.action?hidAction=zroWiseMrdTransfer";
			url+="&oldZone="+Trim(document.getElementById('oldZone').value);
			url+="&mrNoList="+Trim(document.getElementById('mrNo').value);
			url+="&areaCode="+Trim(document.getElementById('areaCode').value);
			url+="&newZRO="+Trim(document.getElementById('newZROCode').value);
            
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
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						 if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
							 document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
						 }
						 else{
								document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+" </b></font>";
								document.getElementById('btnSubmitZone').disabled=true;
							}
						 showScreen();
					}
				};			
				httpBowserObject.send(null);
	}
		}
	}
	function fnMRDTransfer(){
		//alert(isValid);
        var isValid = true;
		if (Trim(document.forms[0].oldZone.value) == '') {
			isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Old Zone.</font>";
			document.forms[0].oldZone.focus();
			return ;
		}
		if (Trim(document.forms[0].mrNo.value) == '') {
			isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter an MR No.</font>";
			document.forms[0].mrNo.focus();
			return ;
		}
		if (Trim(document.forms[0].areaCode.value) == '') {
			isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter Area Code.</font>";
			document.forms[0].areaCode.focus();
			return ;
		}
		if (Trim(document.forms[0].newZone.value) == '') {
			isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a New Zone.</font>";
			document.forms[0].newZone.focus();
			return ;
		}
		if (Trim(document.forms[0].newZROCode.value) == '') {
			isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Code.</font>";
			document.forms[0].newZROCode.focus();
			return ;
		}
		if(isValid){
	     
		 fnMRDTransferAJaxCall();
		}
	}

  function fnZroWiseTransfer(){
	  //alert(isValid);
	  var isValid = true;

		if (Trim(document.forms[0].oldZone.value) == '') {
			var isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Old Zone.</font>";
			document.forms[0].oldZone.focus();
			return ;
		}
		if (Trim(document.forms[0].mrNo.value) == '') {
			var isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter an MR No.</font>";
			document.forms[0].mrNo.focus();
			return ;
		}
		if (Trim(document.forms[0].areaCode.value) == '') {
			var isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter Area No.</font>";
			document.forms[0].areaCode.focus();
			return ;
		}
		if (Trim(document.forms[0].newZROCode.value) == '') {
			var isValid = false;
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Code.</font>";
			document.forms[0].newZROCode.focus();
			return ;
		}
		
	     if(isValid){
		     
	     fnZroWiseMRDTransferAJaxCall();
	     }
 }	

  function fnIsDisabled(){
	 
	  if (Trim(document.forms[0].newZone.value) != '' && Trim(document.forms[0].oldZone.value) != '') {
		  document.getElementById('btnSubmitZone').disabled=false;
		  document.getElementById('btnSubmit').disabled=true;
       }
	  if(Trim(document.forms[0].newZone.value) == '' && Trim(document.forms[0].oldZone.value) != '' && Trim(document.forms[0].mrNo.value) != '' && Trim(document.forms[0].areaCode.value) != ''){
		  document.getElementById('btnSubmit').disabled=false;
		  document.getElementById('btnSubmitZone').disabled=true;

	  }
  }
  function fngetStatus(){
	  var url="mrdTransferGetStatusAJax.action?hidAction=getStatus";
		url+="&oldZone="+Trim(document.getElementById('oldZone').value);
		url+="&mrNoList="+Trim(document.getElementById('mrNo').value);
		url+="&areaCode="+Trim(document.getElementById('areaCode').value);
		url+="&newZone="+Trim(document.getElementById('newZone').value);
		url+="&newZRO="+Trim(document.getElementById('newZROCode').value);
		//alert('url****'+url);
		
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
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						showScreen(responseString);
						//alert("responseString   "+responseString);
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
						}
						else
						{
						document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
						}
					}
				};
				httpBowserObject.send(null);
			}
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
				<s:form id="MRD" method="post" action="javascript:void(0);"
					theme="simple" onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="isPopUp" id="isPopUp" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Mrd Transfer Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="10%"><label>Old Zone</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Old Zone" nowrap><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="oldZone" id="oldZone" onchange="fnGetMRNOList();"/></td>
									<td align="right" width="10%"><label>New Zone</label><font
										color="red"><sup>**</sup></font></td>
									<td align="left" width="30%" title="New Zone" nowrap><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="newZone" id="newZone" onchange="fnGetZroCodeList();"/></td>
									
								</tr>
								<tr>
									<td align="right" width="10%"><label>MR No</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="15%" title="MR No" id="mrNoTD"><s:select
										list="#session.MRNOListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="mrNo" id="mrNo" onchange="fnGetAreaList();" onfocus="fnCheckZone();"/></td>
									<td align="right"><label>Area Code</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Area Code" id="areaTD"><s:select
										list="#session.AreaListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="areaCode" id="areaCode" onchange="fnIsDisabled();" onfocus="fnCheckZoneMRNo();"/></td>
								</tr>
								<tr>
									<td align="right"><label>New ZRO Code</label><font color="red"><sup>*</sup></font></td>
									<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
										list="#session.ZROListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="newZROCode" id="newZROCode" onchange="fnIsDisabled();" /></td>
								</tr>
					
								<tr>
									<td align="left">&nbsp;</td>
									<td align="left"><input type="button"
										value="Reset All Fields" class="groovybutton"
										onclick="clearAllFields();" /></td>
									<td align="left">&nbsp;</td>
									<td align="left"><input type="submit" value=" Zone Transfer"
										class="groovybutton" id="btnSubmitZone" disabled ="false"
										onclick = "javascript:return fnMRDTransfer();"/></td>
								   <td align="left"><input type="submit" value="ZRO Transfer"
										class="groovybutton" id="btnSubmit" disabled="false" onclick= "javascript:return fnZroWiseTransfer();"/></td>
								   <td align="left"><input type="submit" value="Get Status"
										class="groovybutton" id="btnSubmitStatus" onclick= "javascript:return fngetStatus();" /></td>	
								</tr>
								<tr>
									<td align="left" colspan="4">&nbsp;</td>
									<td align="right" colspan="4"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
								</tr>
								<tr>
									<td align="left" colspan="3">&nbsp;</td>
									<td align="right" colspan="3"><font color="red"><sup>**</sup></font>
									marked fields are conditional mandatory.</td>
								</tr>
							</table>	
							</fieldset>
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
	$('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});
	$('.textbox-long').focus(function() { 
		$(this).addClass("textbox-longfocus"); 
	});   
	$('.textbox-long').blur(function() {  					
		$(this).removeClass("textbox-longfocus"); 
		$(this).addClass("textbox-long"); 
	});


	function clearAllFields(){
		var tempIsPopUp=document.getElementById('isPopUp').value;
		document.getElementById("MRD").reset();
		document.getElementById('onscreenMessage').innerHTML = "";		
		document.getElementById('isPopUp').value=tempIsPopUp;
		document.getElementById('btnSubmit').disabled=true;
		document.getElementById('btnSubmitZone').disabled=true;
	}
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<% 
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>