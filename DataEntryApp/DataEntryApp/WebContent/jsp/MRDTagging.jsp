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
<title>MRD Tagging Page - Revenue Management System, Delhi Jal
Board</title>
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
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	var isModified=false,isProcessing=false;
	function fnResetSearch(){
		document.getElementById('searchResult').innerHTML = "<font color='blue'>Please Select a ZRO Location and Click Search Button.</font><br/>";
	}
	function fnRefreshSearch(){
		fnSearch();
	}
 	function gotoSearch(){
 		if (document.forms[0].selectedZone &&Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location.</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}
		document.getElementById('onscreenMessage').innerHTML = "";
	 	fnSearch();
 	}	
	function fnSearch() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedZROCode = Trim(document.getElementById('selectedZROCode').value);
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		var selectedMRKeys=Trim(document.getElementById('selectedMRKeys').value);
		//Added by Rajib as per JTrac DJB-3871 on 07-OCT-2015
		var selectedEmpId= document.getElementById('mrEmpId').value;
		var selectedHhdId= document.getElementById('mrHhdId').value;
		if(selectedMRKEY!=''){
			selectedMRKeys=selectedMRKEY;
		}
		if ('' != selectedZROCode) {
			var url = "mrdTaggingAJax.action?hidAction=search&selectedZone="
					+ selectedZone+"&selectedZROCode="+selectedZROCode+"&selectedMRKeys="+selectedMRKeys+"&mrEmpId="+selectedEmpId+"&mrHhdId="+selectedHhdId;
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
								fnBindCheckBox();
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
						selectMRKEYs="";
						c=0;
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
	
	function fnGetMRKEY() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		var selectedArea = Trim(document.getElementById('selectedArea').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchResult')){
			document.getElementById('searchResult').innerHTML = "&nbsp;";		
		}
		setStatusBarMessage("");
		if ('' != selectedZone && '' != selectedMRNo && ''!=selectedArea) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "demandTransferAJax.action?hidAction=populateMRKEY&selectedZone="
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
				document.getElementById('selectedMRKEY').disabled = true;
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
								document.getElementById('selectedMRKEY').value=Trim(responseString.substring(responseString.indexOf("<MRKE>") + 6, responseString.indexOf("</MRKE>")));
								var zroLoc=Trim(responseString.substring(responseString.indexOf("<option value='") + 15, responseString.indexOf("'>")));
								document.getElementById('selectedZROCode').value = zroLoc;
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedArea'));
							document.getElementById('selectedMRKEY').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	
	function fnGetArea() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchResult')){
			document.getElementById('searchResult').innerHTML = "&nbsp;";		
		}
		setStatusBarMessage("");
		if ('' != selectedZone && '' != selectedMRNo) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "demandTransferAJax.action?hidAction=populateArea&selectedZone="
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
				document.getElementById('selectedMRKEY').disabled = true;
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
								document.getElementById('onscreenMessage').title = "";
							} else {
								document.getElementById('areaTD').innerHTML = responseString;
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNo'));
							document.getElementById('selectedMRKEY').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetZROLoc(obj){
		obj.value = (obj.value).replace(/[\r\n ]/g, "");
		var commaSepartedNumberRegex=/^[0-9]*$/;
		if (commaSepartedNumberRegex.test(obj.value)) {
			document.getElementById('selectedMRKEY').value=Trim(obj.value);
			fnGetZoneMRAreaByMRKEY();
		}else{
			document.getElementById('selectedMRKEY').value="";
			document.getElementById('selectedZone').value = '';
			document.getElementById('selectedMRNo').value = '';
			document.getElementById('selectedArea').value = '';
		}
	}
	function fnGetZoneMRAreaByMRKEY(){
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if(document.getElementById('searchResult')){
			document.getElementById('searchResult').innerHTML = "&nbsp;";	
		}
		setStatusBarMessage("");
		if(selectedMRKEY!=''){
			var url = "demandTransferAJax.action?hidAction=populateZoneMRAreaByMRKEY&selectedMRKEY="+ selectedMRKEY;
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
				document.getElementById('selectedZone').value = "";
				document.getElementById('selectedZone').disabled=true;
				document.getElementById('selectedMRNo').value = "";
				document.getElementById('selectedMRNo').disabled=true;
				document.getElementById('selectedArea').value = "";
				document.getElementById('selectedArea').disabled = true;
				showAjaxLoading(document.getElementById('imgSelectedZone'));
				showAjaxLoading(document.getElementById('imgSelectedMRNo'));
				showAjaxLoading(document.getElementById('imgSelectedArea'));
				//alert('url>>'+url);                 
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
								hideAjaxLoading(document
										.getElementById('imgSelectedZone'));
								hideAjaxLoading(document
										.getElementById('imgSelectedMRNo'));
								hideAjaxLoading(document
										.getElementById('imgSelectedArea'));
								document.getElementById('selectedZone').disabled=false;	
							} else {
								document.getElementById('selectedZone').value = Trim(responseString.substring(responseString.indexOf("<ZONE>") + 6, responseString.indexOf("</ZONE>")));	
								document.getElementById('mrNoTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<MRNO>") + 6, responseString.indexOf("</MRNO>")));
								document.getElementById('areaTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<AREA>") + 6, responseString.indexOf("</AREA>")));				
								var zroLocResp= Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
								var zroLoc=Trim(zroLocResp.substring(zroLocResp.indexOf("<option value='") + 15, zroLocResp.indexOf("'>")));
								document.getElementById('selectedZROCode').value = zroLoc;
								hideAjaxLoading(document
										.getElementById('imgSelectedZone'));
								hideAjaxLoading(document
										.getElementById('imgSelectedMRNo'));
								hideAjaxLoading(document
										.getElementById('imgSelectedArea'));
								document.getElementById('selectedZone').disabled=false;	
								
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}							
				};
				httpBowserObject.send(null);
			}
		}else{
			document.getElementById('selectedZone').value = "";
			document.getElementById('selectedMRNo').value = "";
			document.getElementById('selectedArea').value = "";
		}
	}
	
	function fnGetMRNo() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		//document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchResult')){
			document.getElementById('searchResult').innerHTML = "&nbsp;";		
		}
		setStatusBarMessage("");
		if (selectedZone != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "demandTransferAJax.action?hidAction=populateMRNo&selectedZone="
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
				document.getElementById('selectedMRKEY').disabled = true;
				//alert('url>>'+url);                 
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
							} else {
								document.getElementById('mrNoTD').innerHTML = responseString;							
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedZone'));
							document.getElementById('selectedMRKEY').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}

	function fnGetMtrRdr(){
		var mtrRdsSrc = Trim(document.getElementById('mtrRdsSrc').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if (mtrRdsSrc != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdTaggingAJax.action?hidAction=populateMtrRdr&meterReaderID="
					+ mtrRdsSrc;
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
				showAjaxLoading(document.getElementById('imgMtrRdrSrc'));
				document.getElementById('meterReaderID').value = "";
				document.getElementById('meterReaderID').disabled=true;
				//alert('url>>'+url);                 
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Meter Reader found corresponding Search criteria. </b></font>";
							} else {
								document.getElementById('mtrRdrTD').innerHTML = responseString;							
							}
							hideAjaxLoading(document
									.getElementById('imgMtrRdrSrc'));
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnResetSearchCriteria() {
		document.getElementById('selectedZone').value = "";
		document.getElementById('selectedMRNo').value = "";
		document.getElementById('selectedArea').value = "";
		document.getElementById('selectedMRKEY').value = "";
		document.getElementById('selectedMRKeys').value = "";
		if(document.getElementById('searchResult')){
			document.getElementById('searchResult').innerHTML = "&nbsp;";	
		}
	}
	function fnSearchHistory() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedZROCode = Trim(document.getElementById('selectedZROCode').value);
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		var selectedMRKeys=Trim(document.getElementById('selectedMRKeys').value);
		//Added by Rajib as per JTrac DJB-3871 on 07-OCT-2015
		var selectedEmpId= document.getElementById('mrEmpId').value;
		var selectedHhdId= document.getElementById('mrHhdId').value;
		
		if(selectedMRKEY!=''){
			selectedMRKeys=selectedMRKEY;
		}
		if ('' != selectedZROCode) {
			var url = "mrdTaggingAJax.action?hidAction=searchHistory&selectedZone="
					+ selectedZone+"&selectedZROCode="+selectedZROCode+"&selectedMRKeys="+selectedMRKeys+"&mrEmpId="+selectedEmpId+"&mrHhdId="+selectedHhdId;
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
						selectMRKEYs="";
						c=0;
					}
				};
				httpBowserObject.send(null);
			}
		}else{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location.</font>";
			document.forms[0].selectedZROCode.focus();
			return false;
		}
	}
	function fnTagMRD() {
		var meterReaderID = Trim(document.getElementById('meterReaderID').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if('' == selectMRKEYs){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Select at-least one MRKEY for Tagging.</b></font>";
			window.scrollTo(0,0);	
			return;
		}
		if('' == meterReaderID){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Select a Meter reader for Tagging.</b></font>";
			document.getElementById('meterReaderID').focus();
			return;
		}
		//START: Added by Rajib as per JTrac DJB-3871 on 07-OCT-2015
		var meterRdrname= document.getElementById('meterReaderID').options[document.getElementById('meterReaderID').selectedIndex].text;
		//alert('meterRdrname>>'+meterRdrname);
		if('DJB' == meterRdrname){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>MRD can't be tagged to Meter Reader 'DJB', please choose other Meter Reader.</b></font>";
			document.getElementById('meterReaderID').focus();
			return;
		}
		//END: Added by Rajib as per JTrac DJB-3871 on 07-OCT-2015
		var msg="Are you Sure You want to Tag "+c+" MRD(s) with the Meter Reader?\nIf You are Sure Click OK else Cancel.";
		if ('' != selectMRKEYs && ''!=meterReaderID && confirm(msg)) {
			var url = "mrdTaggingAJax.action?hidAction=tagMRD&selectedMRKeys="
					+ selectMRKEYs+"&meterReaderID="+meterReaderID;
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
				hideScreen();
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null != responseString
								&& responseString.indexOf('<script') == -1) {			
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
								document.getElementById('onscreenMessage').title = "";
							} else {
								document.getElementById('onscreenMessage').innerHTML = responseString;
								fnRefreshSearch();
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
						showScreen();
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnUnTagMRD() {
		var meterReaderID = Trim(document.getElementById('meterReaderID').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if('' == selectMRKEYs){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Select at-least one MRKEY for Tagging.</b></font>";
			window.scrollTo(0,0);	
			return;
		}
		if (document.forms[0].selectedZROCode &&Trim(document.forms[0].selectedZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location.</font>";
			document.forms[0].selectedZone.focus();
			return false;
		}
		var selectedZROCode = Trim(document.getElementById('selectedZROCode').value);
		var msg="Are you Sure You want to Un-Tag "+c+" MRD(s) from the Meter Reader?\nIf You are Sure Click OK else Cancel.";
		if ('' != selectMRKEYs &&''!=selectedZROCode && confirm(msg)) {
			var url = "mrdTaggingAJax.action?hidAction=unTagMRD&selectedMRKeys="
					+ selectMRKEYs+"&selectedZROCode="+selectedZROCode;
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
				hideScreen();
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null != responseString
								&& responseString.indexOf('<script') == -1) {			
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
								document.getElementById('onscreenMessage').title = "";
							} else {
								document.getElementById('onscreenMessage').innerHTML = responseString;
								fnRefreshSearch();
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
						showScreen();
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
	function fnDisableAllCheckBox(){
		var inputs = document.getElementsByTagName("INPUT");
		for (var i = 0; i < inputs.length; i++) {
		    if (inputs[i].type == 'checkbox') {
		        inputs[i].disabled = true;
		    }
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
									<td align="right" width="10%"><label>Zone</label></td>
									<td align="left" title="Zone" width="20%"><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZone" id="selectedZone"
										onchange="fnGetMRNo();" /></td>
									<td align="left" width="3%"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" id="imgSelectedZone" /></td>
									<td align="right" nowrap><label>MR No</label><font
										color="red"><sup></sup></font></td>
									<td align="left" title="MR No" id="mrNoTD"><s:select
										list="#session.MRNoListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="selectedMRNo" id="selectedMRNo"
										onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
									<td align="left" width="3%"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" id="imgSelectedMRNo" /></td>
									<td align="right" width="10%"><label>Area</label><font
										color="red"><sup></sup></font></td>
									<td align="left" title="Area" id="areaTD" width="20%"><s:select
										list="#session.AreaListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedArea" id="selectedArea"
										onfocus="fnCheckZoneMRNo();" onchange="fnGetMRKEY();" /></td>
									<td align="left" width="3%"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" id="imgSelectedArea" /></td>
								</tr>
								<tr>
									<td align="right"><label>MRKEY</label><font color="red"><sup></sup></font></td>
									<td align="left"><s:select name="selectedMRKEY"
										id="selectedMRKEY" headerKey="" headerValue="Please Select"
										list="#session.MRKEYListMap" cssClass="selectbox"
										theme="simple" onclick="fnEnableField(this);"
										onchange="fnGetZoneMRAreaByMRKEY();" /></td>
									<td align="left"><img src="/DataEntryApp/images/load.gif"
										width="25" border="0" title="Processing"
										style="display: none;" id="imgSelectedMRKEY" /></td>
									<td align="right" nowrap><label>MRKEY(s)(Comma
									Seperated)</label><font color="red"><sup></sup></font></td>
									<td align="left"><s:textarea rows="2" theme="simple"
										name="selectedMRKeys" id="selectedMRKeys" maxlength="4000"
										onchange="fnValidateCommaSepartedListOfNumber(this);fnGetZROLoc(this);" /></td>
									<td align="left">&nbsp;</td>
									<td align="right" nowrap><label>ZRO Location</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" title="Old ZRO Code" id="selectedZROCodeTD"><s:select
										list="#session.ZROListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZROCode" id="selectedZROCode"
										onchange="fnResetSearchCriteria();" /></td>
								</tr>
								<tr>
									<td align="right" nowrap> <label>Employee Id</label></td>
									<td align="left"><s:textfield cssClass="textbox" theme="simple" 	name="mrEmpId" id="mrEmpId" maxlength="8" /> </td>
									<td align="left">&nbsp;</td>
									<td align="right"><label>HHD Id</label></td>
									<td align="left"><s:textfield cssClass="textbox" theme="simple" 	name="mrHhdId" id="mrHhdId" maxlength="8" /></td>
									<td align="left">&nbsp;</td>
									<td align="left">&nbsp;</td>
								</tr>
								
								<tr>
									<td align="left">&nbsp;</td>
									<td align="right"><input type="button" id="btnViewLog"
										value=" View Log / History " class="groovybutton"
										onclick="fnSearchHistory();" /></td>
									<td align="left">&nbsp;</td>
									<td align="left">&nbsp;</td>
									<td align="left">&nbsp;</td>
									<td align="left">&nbsp;</td>
									<td align="left"><s:submit key="    Search    "
										cssClass="groovybutton" theme="simple" id="btnSearch" /></td>
									<td align="right"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
					</table>
				</s:form>
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
					</tr>
				</table>
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
	});
	$("form").submit(function(e) {
		gotoSearch();
	});
	$('#meterReaderName').keyup(function(){
	    this.value = this.value.toUpperCase();
	});
	function fnBindCheckBox(){		
		$(".case").click(function() {			
			fnSetSelectMRKEY();
		});
	}	
	var selectMRKEYs="";
	var c=0;
	function fnSetSelectMRKEY(){
		var checkBoxID;
		selectMRKEYs="";
		c=0;
		$('.case').each(function () {
			checkBoxID = this.id;
			if (document.getElementById(checkBoxID).checked ) {		   		
				selectMRKEYs+=","+checkBoxID;
		   		c++;
			}
			
		});
		//alert(selectMRKEY);
		var message="Total MRKey(s) Selected = "+c;
		setStatusBarMessage(message);
	}
</script>
<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>"></script>

<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>