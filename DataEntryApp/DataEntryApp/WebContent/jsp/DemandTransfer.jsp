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
<title>Demand Transfer Page - Revenue Management System, Delhi
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
    function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	var isModified=false;
	function fnGotoCreateNewMRDScreen(){
		if(confirm('This will Navigate away from this Screen.\n\nAre You Sure You Want to Navigate away from this Screen ?\nClick OK to continue else Cancel.')){
			fnGotoCreateNewMRD();
		}
	}
	function fnGoToTransferDemand(){
		if (Trim(document.forms[0].selectedMRKEYNew.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a New MRD Details.</font>";
			document.forms[0].selectedMRKEYNew.focus();
			return ;
		}
		if (Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Old MRD Details.</font>";
			document.forms[0].selectedMRKEY.focus();
			return ;
		}
		if (Trim(document.forms[0].selectedZoneNew.value) == ''||Trim(document.forms[0].selectedMRNoNew.value) == ''||Trim(document.forms[0].selectedAreaNew.value) == ''||Trim(document.forms[0].selectedZROCodeNew.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Enter All Mandatory Fields for New MRD Details or Wait while being Populated.</font>";
			document.forms[0].selectedZoneNew.focus();
			return ;
		}
		if (parseInt(Trim(document.forms[0].selectedMRKEY.value)) == parseInt(Trim(document.forms[0].selectedMRKEYNew.value))) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>New MRD Details and Old MRD Details are same, Please Select a diffrent New MRD Details.</font>";
			document.forms[0].selectedMRKEYNew.focus();
			return ;
		}
		if (totalNoOfAccount == 0) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select at least One KNO from the Table.</font>";
			window.scrollTo(0,0);	
			return ;
		}
		fnTransferDemand();		
	}
	function fnTransferDemand(){
		var selectedKNO=document.getElementById('selectedKNO').value;
		var removedKNO=document.getElementById('removedKNO').value;
		var msg;
		if(totalNoOfAccount && totalNoOfAccount>0){
			msg="Are You Sure You Want to Transfer the Selected "+totalNoOfAccount+" Demand(s)?\nClick OK to continue else Cancel.";
		}else{
			msg="Are You Sure You Want to Transfer the Selected Demand(s)?\nClick OK to continue else Cancel.";
		}
		if(confirm(msg)){
			var url = "demandTransferAJax.action?hidAction=transferDemand&selectedKNO="+ selectedKNO+"&removedKNO="+removedKNO;
			url+="&selectedZone="+document.getElementById('selectedZone').value;
			url+="&selectedMRNo="+document.getElementById('selectedMRNo').value;
			url+="&selectedArea="+document.getElementById('selectedArea').value;
			url+="&selectedMRKEY="+document.getElementById('selectedMRKEY').value;
			
			url+="&selectedZoneNew="+document.getElementById('selectedZoneNew').value;
			url+="&selectedMRNoNew="+document.getElementById('selectedMRNoNew').value;
			url+="&selectedAreaNew="+document.getElementById('selectedAreaNew').value;
			url+="&selectedMRKEYNew="+document.getElementById('selectedMRKEYNew').value;
			url+="&selectedZROCodeNew="+document.getElementById('selectedZROCodeNew').value;
			
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
						//alert(httpBowserObject.responseText);
						if (null != responseString
								&& responseString.indexOf('<script') == -1) {
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								if(responseString.indexOf("ERROR:NODJBMTRRDR") > -1){
									document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+errormsg+"  </b></font>";
									showScreen();
									}else{
										if(responseString.indexOf("ERROR:MORETHANONEDJBMTRRDR.") > -1){
											document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+errormsg+"  </b></font>";     				
											}else{
												document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
												}	
								showScreen();
								//fnSearchTransfered();
								}
								} else {
								if(responseString.indexOf("WARNING:") > -1){
									document.getElementById('onscreenMessage').innerHTML ="<font color='red' size='2'><span><b>"+ responseString+"</b></font>";
									document.getElementById('btnTransferDemand').disabled=true;
									var confirmMsg=responseString+"\n\nYou must Refresh your Search to Continue.\n\nIf you want to Refresh your Search Click OK else Cancel.";
									if(confirm(confirmMsg)){
										showScreen();
										fnSearch();
									}else{
										if(document.getElementById('newMRDDetails')){
											document.getElementById('newMRDDetails').innerHTML = "&nbsp;";			
										}
										fnDisableAllButton();
										fnDisableAllCheckBox();
										if(document.getElementById('pageNo')){
											document.getElementById('pageNo').disabled=true;		
										}
										if(document.getElementById('maxRecordPerPage')){
											document.getElementById('maxRecordPerPage').disabled=true;		
										}
										if(document.getElementById('btnViewLog')){
											document.getElementById('btnViewLog').disabled=false;		
										}
										showScreen();		
									}						
								}else{
									showScreen();
									fnSearchTransfered();
								}
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
	function fnSearchTransfered(){
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "searchTransfered";		
		hideScreen();
		document.forms[0].submit();
	}
	function gotoTransferedNextPage(){
	 	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "TransferedNext";	
		hideScreen();
		document.forms[0].submit();
 	}	
 	function gotoTransferedPreviousPage(){
 		document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "TransferedPrev";	
		hideScreen();
		document.forms[0].submit();
 	}
	/*==================================Starts For Old MRD Details=============================================*/	
	function fnGetZoneMRAreaByMRKEY(){
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if(document.getElementById('searchBox')){
			document.getElementById('searchBox').innerHTML = "&nbsp;";	
		}
		if(document.getElementById('newMRDDetails')){
			document.getElementById('newMRDDetails').innerHTML = "&nbsp;";			
		}
		setStatusBarMessage("");
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
							document.getElementById('selectedZROCodeTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
							hideAjaxLoading(document
									.getElementById('imgSelectedZone'));
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNo'));
							hideAjaxLoading(document
									.getElementById('imgSelectedArea'));
							document.getElementById('selectedZone').disabled=false;	
							var openBillRound= Trim(responseString.substring(responseString.indexOf("<OPBR>") + 6, responseString.indexOf("</OPBR>")));
							if(null==openBillRound||'null'==openBillRound||''==openBillRound){
								document.getElementById('openBillRoundForMRKEYOld').value="";
							}else{
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".</b></font>";
								document.getElementById('openBillRoundForMRKEYOld').value=openBillRound;
							}
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}							
			};
			httpBowserObject.send(null);
		}
	}
	function fnGetMRNo() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchBox')){
			document.getElementById('searchBox').innerHTML = "&nbsp;";		
		}
		if(document.getElementById('newMRDDetails')){
			document.getElementById('newMRDDetails').innerHTML = "&nbsp;";			
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
	function fnGetArea() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchBox')){
			document.getElementById('searchBox').innerHTML = "&nbsp;";		
		}
		if(document.getElementById('newMRDDetails')){
			document.getElementById('newMRDDetails').innerHTML = "&nbsp;";			
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
	function fnGetMRKEY() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		var selectedArea = Trim(document.getElementById('selectedArea').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		document.getElementById('selectedZROCode').value="";		
		if(document.getElementById('searchBox')){
			document.getElementById('searchBox').innerHTML = "&nbsp;";		
		}
		if(document.getElementById('newMRDDetails')){
			document.getElementById('newMRDDetails').innerHTML = "&nbsp;";			
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
								document.getElementById('selectedZROCodeTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
								var openBillRound= Trim(responseString.substring(responseString.indexOf("<OPBR>") + 6, responseString.indexOf("</OPBR>")));
								if(null==openBillRound||'null'==openBillRound||''==openBillRound){
									document.getElementById('openBillRoundForMRKEYOld').value="";
								}else{
									document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".</b></font>";
									document.getElementById('openBillRoundForMRKEYOld').value=openBillRound;
								}
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
	/*==================================Ends For Old MRD Details=============================================*/
	/*==================================Starts For New MRD Details=============================================*/
	function fnGetZoneMRAreaByMRKEYNew(){
		var selectedMRKEYNew = Trim(document.getElementById('selectedMRKEYNew').value);
		document.getElementById('onscreenMessage').innerHTML = "";		
		var url = "demandTransferAJax.action?hidAction=populateZoneMRAreaByMRKEYNew&selectedMRKEYNew="+ selectedMRKEYNew;
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
			document.getElementById('btnTransferDemand').disabled=true;
			document.getElementById('selectedZoneNew').value = "";
			document.getElementById('selectedZoneNew').disabled=true;
			document.getElementById('selectedMRNoNew').value = "";
			document.getElementById('selectedMRNoNew').disabled=true;
			document.getElementById('selectedAreaNew').value = "";
			document.getElementById('selectedAreaNew').disabled = true;
			showAjaxLoading(document.getElementById('imgSelectedZoneNew'));
			showAjaxLoading(document.getElementById('imgSelectedMRNoNew'));
			showAjaxLoading(document.getElementById('imgSelectedAreaNew'));
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
									.getElementById('imgSelectedZoneNew'));
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNoNew'));
							hideAjaxLoading(document
									.getElementById('imgSelectedAreaNew'));
							document.getElementById('selectedZoneNew').disabled=false;
							document.getElementById('btnTransferDemand').disabled=true;
						} else {
							document.getElementById('selectedZoneNew').value = Trim(responseString.substring(responseString.indexOf("<ZONE>") + 6, responseString.indexOf("</ZONE>")));	
							document.getElementById('mrNoTDNew').innerHTML = Trim(responseString.substring(responseString.indexOf("<MRNO>") + 6, responseString.indexOf("</MRNO>")));
							document.getElementById('areaTDNew').innerHTML = Trim(responseString.substring(responseString.indexOf("<AREA>") + 6, responseString.indexOf("</AREA>")));				
							document.getElementById('selectedZROCodeTDNew').innerHTML = Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
							hideAjaxLoading(document
									.getElementById('imgSelectedZoneNew'));
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNoNew'));
							hideAjaxLoading(document
									.getElementById('imgSelectedAreaNew'));
							document.getElementById('selectedZoneNew').disabled=false;
							document.getElementById('btnTransferDemand').disabled=false;
							document.getElementById('btnTransferDemand').title="Click to Transfer Demand.";
							var openBillRound= Trim(responseString.substring(responseString.indexOf("<OPBR>") + 6, responseString.indexOf("</OPBR>")));
							if(null==openBillRound||'null'==openBillRound||''==openBillRound){
								openBillRound="";
							}
							if(''!=openBillRound){
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEYNew').value+".</b></font>";
								document.getElementById('btnTransferDemand').disabled = true;
								document.getElementById('btnTransferDemand').title="Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEYNew').value+".";
							}
							if(''!=Trim(document.getElementById('openBillRoundForMRKEYOld').value)){
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".</b></font>";
								document.getElementById('btnTransferDemand').disabled = true;
								document.getElementById('btnTransferDemand').title="Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".";
							}
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}				
			};
			httpBowserObject.send(null);
		}
	}
	function fnGetMRNoNew() {
		var selectedZoneNew = Trim(document.getElementById('selectedZoneNew').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEYNew').value="";
		document.getElementById('selectedZROCodeNew').value="";
		if (selectedZoneNew != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "demandTransferAJax.action?hidAction=populateMRNoNew&selectedZoneNew="
					+ selectedZoneNew;
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
				showAjaxLoading(document.getElementById('imgSelectedZoneNew'));
				document.getElementById('selectedMRNoNew').value = "";
				document.getElementById('selectedMRNoNew').disabled=true;
				document.getElementById('selectedAreaNew').value = "";
				document.getElementById('selectedAreaNew').disabled = true;
				document.getElementById('selectedMRKEYNew').disabled = true;
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
								document.getElementById('mrNoTDNew').innerHTML = responseString;							
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedZoneNew'));
							document.getElementById('selectedMRKEYNew').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetAreaNew() {
		var selectedZoneNew = Trim(document.getElementById('selectedZoneNew').value);
		var selectedMRNoNew = Trim(document.getElementById('selectedMRNoNew').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEYNew').value="";
		document.getElementById('selectedZROCodeNew').value="";
		if ('' != selectedZoneNew && '' != selectedMRNoNew) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "demandTransferAJax.action?hidAction=populateAreaNew&selectedZoneNew="
					+ selectedZoneNew + "&selectedMRNoNew=" + selectedMRNoNew;
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
				showAjaxLoading(document.getElementById('imgSelectedMRNoNew'));
				document.getElementById('selectedAreaNew').value = "";
				document.getElementById('selectedAreaNew').disabled = true;
				document.getElementById('selectedMRKEYNew').disabled = true;
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
								document.getElementById('areaTDNew').innerHTML = responseString;
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedMRNoNew'));
							document.getElementById('selectedMRKEYNew').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetMRKEYNew() {
		var selectedZoneNew = Trim(document.getElementById('selectedZoneNew').value);
		var selectedMRNoNew = Trim(document.getElementById('selectedMRNoNew').value);
		var selectedAreaNew = Trim(document.getElementById('selectedAreaNew').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEYNew').value="";
		document.getElementById('selectedZROCodeNew').value="";
		if ('' != selectedZoneNew && '' != selectedMRNoNew && ''!=selectedAreaNew) {
			document.getElementById('onscreenMessage').innerHTML = "";
			document.getElementById('btnTransferDemand').disabled=true;
			var url = "demandTransferAJax.action?hidAction=populateMRKEYNew&selectedZoneNew="
					+ selectedZoneNew + "&selectedMRNoNew=" + selectedMRNoNew+"&selectedAreaNew="+selectedAreaNew;
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
				showAjaxLoading(document.getElementById('imgSelectedAreaNew'));
				document.getElementById('selectedMRKEYNew').disabled = true;
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
								document.getElementById('selectedMRKEYNew').value=Trim(responseString.substring(responseString.indexOf("<MRKE>") + 6, responseString.indexOf("</MRKE>")));
								document.getElementById('selectedZROCodeTDNew').innerHTML = Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
								document.getElementById('btnTransferDemand').disabled=false;
								document.getElementById('btnTransferDemand').title="Click to Transfer Demand.";
								var openBillRound= Trim(responseString.substring(responseString.indexOf("<OPBR>") + 6, responseString.indexOf("</OPBR>")));
								if(null==openBillRound||'null'==openBillRound||''==openBillRound){
									openBillRound="";
								}
								if(''!=openBillRound){
									document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEYNew').value+".</b></font>";
									document.getElementById('btnTransferDemand').disabled = true;
									document.getElementById('btnTransferDemand').title="Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEYNew').value+".";
								}
								if(''!=Trim(document.getElementById('openBillRoundForMRKEYOld').value)){
									document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".</b></font>";
									document.getElementById('btnTransferDemand').disabled = true;
									document.getElementById('btnTransferDemand').title="Demand Transfer is not Allowed as Bill Round "+openBillRound+" is still in open for the MRKEY "+document.getElementById('selectedMRKEY').value+".";
								}
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedAreaNew'));
							document.getElementById('selectedMRKEYNew').disabled = false;
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnCheckZoneNew() {
		if (document.forms[0].selectedZoneNew.value == '') {
			//alert('Please Select Zone First');
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZoneNew.disabled)) {
				document.forms[0].selectedZoneNew.focus();
			}
			return;
		}
	}
	function fnCheckZoneMRNoNew() {
		if (document.forms[0].selectedZoneNew.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZoneNew.disabled)) {
				document.forms[0].selectedZoneNew.focus();
			}
			return;
		}
		if (document.forms[0].selectedMRNoNew.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MR No. First</b></font>";
			if (!(document.forms[0].selectedMRNoNew.disabled)) {
				document.forms[0].selectedMRNoNew.focus();
			}
			return;
		}
	}
	/*==================================Ends For New MRD Details=============================================*/
	function gotoNextPage(){
	 	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
		if (Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a MRKEY or Zone MR Area Combination.</font>";
			document.forms[0].selectedMRKEY.focus();
			return false;
		}
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "Next";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
 	}	
 	function gotoPreviousPage(){
 		document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
 		if (Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a MRKEY or Zone MR Area Combination.</font>";
			document.forms[0].selectedMRKEY.focus();
			return false;
		}
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "Prev";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
 	}
 	function gotoSearch(){
 		if (document.forms[0].selectedMRKEY &&Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a MRKEY or Zone MR Area Combination.</font>";
			document.forms[0].selectedMRKEY.focus();
			return false;
		}		
	 	if(document.forms[0].pageNo){
 			document.forms[0].pageNo.value="1";
	 	}
	 	if(document.getElementById('totalRecordsSelected')){
	 		document.getElementById('totalRecordsSelected').value="0";
	 	}
	 	document.getElementById("selectedKNO").value="";
	 	document.getElementById("removedKNO").value="";	 	
	 	fnSearch();
 	}	
	function fnSearch() {
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "search";
		document.getElementById('onscreenMessage').innerHTML = "";	
		hideScreen();
		document.forms[0].submit();
	}
	function fnRefreshSearch() {
		if (Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a MRKEY or Zone MR Area Combination.</font>";
			document.forms[0].selectedMRKEY.focus();
			return false;
		}
		document.forms[0].action = "demandTransfer.action";
		document.forms[0].hidAction.value = "refresh";
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
	}
	var totalNoOfAccount=0;
	function fnSetSelectStatus() {
		var message = "Loaded successfully";
		var knoSelected = Trim(document.getElementById('totalRecordsSelected').value);
		var checkboxId = "";
		var knoId = "";
		if (document.getElementById('searchResult')) {
			var rows = document.getElementById('searchResult').rows;
			for ( var i = 1; i < rows.length - 2; i++) {
				checkboxId = "trCheckbox" + i;
				knoId = "trKNO" + i;
				if (document.getElementById(checkboxId).checked ) {
					document.getElementById("removedKNO").value=(document.getElementById("removedKNO").value).replace(document.getElementById(knoId).value,'');
					if((document.getElementById("selectedKNO").value).indexOf(document.getElementById(knoId).value)==-1){
						document.getElementById("selectedKNO").value=document.getElementById("selectedKNO").value+document.getElementById(knoId).value+",";
					}
					if((document.getElementById("selectedKNOList").value).indexOf(document.getElementById(knoId).value)==-1){
						knoSelected++;
					}
				}else{
					document.getElementById("selectedKNO").value=(document.getElementById("selectedKNO").value).replace(document.getElementById(knoId).value,'');
					if((document.getElementById("selectedKNOList").value).indexOf(document.getElementById(knoId).value)>-1){
						document.getElementById("removedKNO").value=document.getElementById("removedKNO").value+document.getElementById(knoId).value+",";	 	
						knoSelected--;
					}
				}
			}
			message = "Total No. of Selected Account(s) = " + parseInt(knoSelected);
			totalNoOfAccount=parseInt(knoSelected);
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
	function fnSetCheckBox(selectedKNOList){
		var checkboxId = "";
		var knoId = "";
		if (document.getElementById('searchResult')) {
			var rows = document.getElementById('searchResult').rows;
			for ( var i = 1; i < rows.length - 2; i++) {
				checkboxId = "trCheckbox" + i;
				knoId = "trKNO" + i;
				if((selectedKNOList).indexOf(document.getElementById(knoId).value)>-1){
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
					<s:hidden name="totalRecords" id="totalRecords" />
					<s:hidden name="selectedKNO" id="selectedKNO" />
					<s:hidden name="removedKNO" id="removedKNO" />
					<s:hidden name="selectedKNOList" id="selectedKNOList" />
					<s:hidden name="totalRecordsSelected" id="totalRecordsSelected" />
					<s:hidden name="openBillRoundForMRKEYOld"
						id="openBillRoundForMRKEYOld" />
					<s:hidden name="openBillRoundForMRKEYNew"
						id="openBillRoundForMRKEYNew" />
					<s:if
						test="hidAction==null||hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Old MRD Details</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="right" width="10%"><label>Zone</label><font
											color="red"><sup>*</sup></font></td>
										<td align="left" width="30%" title="Zone" nowrap><s:select
											list="#session.ZoneListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZone" id="selectedZone"
											onchange="fnGetMRNo();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>
										<td align="right" width="10%"><label>MR No</label><font
											color="red"><sup>*</sup></font></td>
										<td align="left" width="15%" title="MR No" id="mrNoTD"><s:select
											list="#session.MRNoListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox"
											theme="simple" name="selectedMRNo" id="selectedMRNo"
											onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
										<td align="left" width="25%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedMRNo" /></td>
									</tr>
									<tr>
										<td align="right"><label>Area</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="Area" id="areaTD"><s:select
											list="#session.AreaListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedArea" id="selectedArea"
											onfocus="fnCheckZoneMRNo();" onchange="fnGetMRKEY();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedArea" /></td>
										<td align="right"><label>MRKEY</label><font color="red"><sup>*</sup></font></td>
										<td align="left"><s:select name="selectedMRKEY"
											id="selectedMRKEY" headerKey="" headerValue=""
											list="#session.MRKEYListMap" cssClass="selectbox"
											theme="simple" onclick="fnEnableField(this);"
											onchange="fnGetZoneMRAreaByMRKEY();" /></td>
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td align="right"><label>ZRO Code</label><font
											color="red"><sup></sup></font></td>
										<td align="left" title="Old ZRO Code" id="selectedZROCodeTD"><s:select
											list="#session.ZROListMap" cssClass="selectbox-long"
											theme="simple" name="selectedZROCode" id="selectedZROCode"
											disabled="true" /></td>
										<td align="left" width="10%">&nbsp;</td>
										<td align="right"><label>&nbsp;</label><font color="red"><sup></sup></font></td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td align="right" colspan="2"><input type="button"
											id="btnViewLog" value=" View Log / History "
											class="groovybutton" onclick="fnSearchTransfered();" /></td>
										<td align="left">&nbsp;</td>
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
							<s:if test="demandTransferDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>
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
											<th align="right" colspan="1" width="10%"><s:if
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
											<th align="center" width="10%">KNO</th>
											<th align="center" width="10%">WC No</th>
											<th align="center" width="20%">NAME</th>
											<th align="center" width="30%">ADDRESS</th>
											<th align="center" width="10%">CATEGORY</th>
											<th align="center" width="10%">Old Service Cycle Route
											Sequence</th>
										</tr>
										<s:iterator value="demandTransferDetailsList" status="row">
											<tr bgcolor="white"
												onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'">
												<td align="center" title="<s:property value="kno" />"><input
													type="checkbox" class="case" name="case"
													id="trCheckbox<s:property value="#row.count"/>" /><input
													type="hidden" name="trKNO<s:property value="#row.count" />"
													id="trKNO<s:property value="#row.count" />"
													value="<s:property value="kno" />" /></td>
												<td align="center"><s:property value="slNo" /></td>
												<td align="center"><s:property value="kno" /></td>
												<td align="center" nowrap>&nbsp;<s:property
													value="wcNo" /></td>
												<td align="left"><s:property value="name" />&nbsp;</td>
												<td align="left"><s:property value="address" />&nbsp;</td>
												<td align="center" nowrap><s:property value="category" />&nbsp;</td>
												<td align="center"><s:property
													value="oldServiceCycleRouteSequence" />&nbsp;</td>
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
											No. of Selected Account(s) = <s:if
												test="selectedKNOList!=null">
												<s:property value="selectedKNOList.size" />
											</s:if><s:else>0</s:else></span></font></b></th>
											<th align="right" colspan="1" width="10%"><s:if
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
									</fieldset>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" id="newMRDDetails">
									<fieldset><legend>New MRD Details</legend>
									<table width="98%" align="center" border="0">
										<tr>
											<td align="right" width="10%"><label>Zone</label><font
												color="red"><sup>*</sup></font></td>
											<td align="left" width="30%" title="Zone" nowrap><s:select
												list="#session.ZoneListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="selectedZoneNew" id="selectedZoneNew"
												onchange="fnGetMRNoNew();" /></td>
											<td align="left" width="10%"><img
												src="/DataEntryApp/images/load.gif" width="25" border="0"
												title="Processing" style="display: none;"
												id="imgSelectedZoneNew" /></td>
											<td align="right" width="10%"><label>MR No</label><font
												color="red"><sup>*</sup></font></td>
											<td align="left" width="15%" title="MR No" id="mrNoTDNew"><s:select
												list="#session.MRNoListMapNew" headerKey=""
												headerValue="Please Select" cssClass="selectbox"
												theme="simple" name="selectedMRNoNew" id="selectedMRNoNew"
												onchange="fnGetAreaNew();" onfocus="fnCheckZoneNew();" /></td>
											<td align="left" width="25%"><img
												src="/DataEntryApp/images/load.gif" width="25" border="0"
												title="Processing" style="display: none;"
												id="imgSelectedMRNoNew" /></td>
										</tr>
										<tr>
											<td align="right"><label>Area</label><font color="red"><sup>*</sup></font></td>
											<td align="left" title="Area" id="areaTDNew"><s:select
												list="#session.AreaListMapNew" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="selectedAreaNew" id="selectedAreaNew"
												onfocus="fnCheckZoneMRNoNew();" onchange="fnGetMRKEYNew();" /></td>
											<td align="left" width="10%"><img
												src="/DataEntryApp/images/load.gif" width="25" border="0"
												title="Processing" style="display: none;"
												id="imgSelectedAreaNew" /></td>
											<td align="right"><label>MRKEY</label><font color="red"><sup>*</sup></font></td>
											<td align="left"><s:select name="selectedMRKEYNew"
												id="selectedMRKEYNew" headerKey="" headerValue=""
												list="#session.MRKEYListMap" cssClass="selectbox"
												theme="simple" onclick="fnEnableField(this);"
												onchange="fnGetZoneMRAreaByMRKEYNew();" /></td>
											<td align="left">&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><label>ZRO Code</label><font
												color="red"><sup>*</sup></font></td>
											<td align="left"
												title="New ZRO Code Populated corresponding to change of New MRKEY"
												id="selectedZROCodeTDNew"><s:select
												list="#session.ZROListMapNew" cssClass="selectbox-long"
												theme="simple" name="selectedZROCodeNew"
												id="selectedZROCodeNew" disabled="true" /></td>
											<td align="left" width="10%">&nbsp;</td>
											<td align="right"><label>&nbsp;</label><font color="red"><sup></sup></font></td>
											<td align="left">&nbsp;</td>
											<td align="left">&nbsp;</td>
										</tr>
										<tr>
											<td align="right" colspan="3"><input type="button"
												value="Create New MRD" class="groovybutton"
												onclick="fnGotoCreateNewMRDScreen();" /></td>
											<td align="left" colspan="3"><input type="button"
												value="Transfer Demand" class="groovybutton"
												onclick="fnGoToTransferDemand();" id="btnTransferDemand" /></td>
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
					<s:else>
						<table width="100%" border="0"
							title="Click Refresh for Latest Status">
							<tr>
								<td align="center" valign="top" id="newMRDDetails">
								<fieldset><legend></legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="right" colspan="2"><input type="button"
											value="Transfer New Demand" class="groovybutton"
											onclick="fnSearch();" /></td>
										<td align="center" colspan="2"><input type="button"
											value="        Refresh       " class="groovybutton"
											onclick="fnSearchTransfered();" /></td>
										<td align="left" colspan="2"><s:select
											list="{\"View Transfered By Me\",\"View All\"}"
											name="transferedBy" id="transferedBy" theme="simple"
											onchange="fnSearchTransfered();" /></td>
									</tr>
									<tr>
										<td align="left" colspan="3"><s:hidden
											name="selectedMRKEY" id="selectedMRKEY" /> <s:hidden
											name="selectedZone" id="selectedZone" /> <s:hidden
											name="selectedMRNo" id="selectedMRNo" /> <s:hidden
											name="selectedArea" id="selectedArea" />&nbsp;</td>
										<td align="left" colspan="3">&nbsp;</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
							<s:if test="demandTransferDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Demand Transfer Result</legend>
									<table id="searchResult" class="table-grid">
										<tr>
											<th align="center" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoTransferedPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="8" width="80%">Page No:<s:select
												list="pageNoDropdownList" name="pageNo" id="pageNo"
												cssClass="smalldropdown" theme="simple"
												onchange="fnSearchTransfered();" /> Showing maximum <s:select
												list="{10,20,30,40,50,100,200,500,1000}"
												name="maxRecordPerPage" id="maxRecordPerPage"
												cssClass="smalldropdown" theme="simple"
												onchange="fnSearchTransfered();" />records per page of
											total <s:property value="totalRecords" /> records.</th>
											<th align="right" colspan="2" width="10%"><s:if
												test="pageNo*maxRecordPerPage< totalRecords">
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													onclick="javascript:gotoTransferedNextPage();" />
											</s:if><s:else>
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
										</tr>
										<tr>
											<th align="center" width="5%" rowspan="2">SL</th>
											<th align="center" width="5%" rowspan="2">KNO</th>
											<th align="center" width="5%" rowspan="2">WC No</th>
											<th align="center" width="20%" rowspan="2">NAME</th>
											<th align="center" width="25%" rowspan="2">ADDRESS</th>
											<th align="center" width="5%" rowspan="2">CATEGORY</th>
											<th align="center" width="10%" rowspan="1" colspan="2">OLD</th>
											<th align="center" width="10%" rowspan="1" colspan="2">NEW</th>
											<th align="center" width="5%" rowspan="2">Transfered By</th>
											<th align="center" width="5%" rowspan="2">Status</th>
										</tr>
										<tr>
											<th align="center" width="5%" rowspan="1"
												title="Service Cycle Route Sequence No">Rt Seq</th>
											<th align="center" width="5%" rowspan="1">MR KEY</th>
											<th align="center" width="5%" rowspan="1">MR KEY</th>
											<th align="center" width="5%" rowspan="1"
												title="Service Cycle Route Sequence No">Rt Seq</th>
										</tr>
										<s:iterator value="demandTransferDetailsList" status="row">
											<s:if test="status==\"SUCCESS\"">
												<tr bgcolor="#33CC33" title="<s:property value="remarks" />">
											</s:if>
											<s:elseif test="status==\"IN PROGRESS\"">
												<tr bgcolor="yellow" title="<s:property value="remarks" />">
											</s:elseif>
											<s:elseif test="status==\"FAILED\"">
												<tr bgcolor="red" title="<s:property value="remarks" />">
											</s:elseif>
											<s:else>
												<tr bgcolor="#AAAAC6" title="<s:property value="remarks" />">
											</s:else>
											<td align="center"><s:property value="slNo" /></td>
											<td align="center"><s:property value="kno" /></td>
											<td align="center" nowrap>&nbsp;<s:property value="wcNo" /></td>
											<td align="left"><s:property value="name" />&nbsp;</td>
											<td align="left"><s:property value="address" />&nbsp;</td>
											<td align="center" nowrap>&nbsp;<s:property
												value="category" /></td>
											<td align="center">&nbsp;<s:property
												value="oldServiceCycleRouteSequence" /></td>
											<td align="center">&nbsp;<s:property value="oldMRKey" /></td>
											<td align="center">&nbsp;<s:property value="newMRKey" /></td>
											<td align="center">&nbsp;<s:property
												value="newServiceCycleRouteSequence" /></td>
											<td align="center">&nbsp;<s:property value="userId" /></td>
											<td align="center"><b><s:property value="status" /></b></td>
											</tr>
										</s:iterator>
										<tr>
											<th align="center" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoTransferedPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="8" width="80%">&nbsp;</th>
											<th align="right" colspan="2" width="10%"><s:if
												test="pageNo*maxRecordPerPage< totalRecords">
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													onclick="javascript:gotoTransferedNextPage();" />
											</s:if><s:else>
												<input type="button" name="btnNext" id="btnNext"
													value="    Next >>  " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
										</tr>
									</table>
									</fieldset>
									</td>
								</tr>
							</s:if>
						</table>
					</s:else>
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
		var hidAction=document.forms[0].hidAction.value;
		if(hidAction==''||hidAction==null||hidAction=='search'||hidAction=='Next'||hidAction=='Prev'||hidAction=='refresh'){
			if((document.getElementById("selectedKNOList").value).length>10){
				fnSetCheckBox(document.getElementById("selectedKNOList").value);
			}
			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");				
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
			}
			fnSetSelectStatus();
			isModified=true;
		});
	});
	$("form").submit(function(e) {
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