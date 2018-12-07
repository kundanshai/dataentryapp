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
<title>Batch Billing Page - Revenue Management System, Delhi Jal
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
<script type="text/javascript"
	src="<s:url value="/js/jquery.progressbar.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/jquery.progressbar.css"/>" />
<script>
	$(function() {
		$( "#fromDate" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true
		});
		$( "#toDate" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true
		});
	});
	</script>

<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	function fnGetZoneMRAreaByMRKEY(){
		document.getElementById('mrKeyListForSearch').disabled=true;
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		if(selectedMRKEY==''){
			document.getElementById('selectedZone').value="";
			document.getElementById('selectedMRNo').value="";
			document.getElementById('selectedArea').value="";
			document.getElementById('selectedZROCode').value="";
			document.getElementById('mrKeyListForSearch').disabled=false;
			return;
		}
		setStatusBarMessage("");
		var url = "onlineBatchBillingAJax.action?hidAction=populateZoneMRAreaByMRKEY&selectedMRKEY="+ selectedMRKEY;
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
								document.getElementById('selectedBillRound').value="";
								document.getElementById('selectedBillRound').disabled=false;
							}else{
								document.getElementById('selectedBillRound').value=openBillRound;
								document.getElementById('selectedBillRound').disabled=true;								
							}
							loadingComplete=true;
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
			var url = "onlineBatchBillingAJax.action?hidAction=populateMRNo&selectedZone="
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
			var url = "onlineBatchBillingAJax.action?hidAction=populateArea&selectedZone="
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
			var url = "onlineBatchBillingAJax.action?hidAction=populateMRKEY&selectedZone="
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
									document.getElementById('selectedBillRound').value="";
									document.getElementById('selectedBillRound').disabled=false;
								}else{
									document.getElementById('selectedBillRound').value=openBillRound;
									document.getElementById('selectedBillRound').disabled=true;
								}
							}
							hideAjaxLoading(document
									.getElementById('imgSelectedArea'));
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
	function fnGetSummary() {
		var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		var selectedArea = Trim(document.getElementById('selectedArea').value);
		var mrKeyListForSearch = Trim(document.getElementById('mrKeyListForSearch').value);
		var searchBy = Trim(document.getElementById('searchBy').value);
		var selectedStatusCode="";// = Trim(document.getElementById('selectedStatusCode').value);
		var selectedErrorCode="";//= Trim(document.getElementById('selectedErrorCode').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		setStatusBarMessage("");
		if ('' != selectedBillRound) {
			document.getElementById('onscreenMessage').innerHTML = "";
			document.getElementById('summaryDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";
			var url = "onlineBatchBillingAJax.action?hidAction=retrieveSummary";
			url+="&selectedBillRound="+selectedBillRound;
			url+="&selectedMRKEY="+selectedMRKEY;			
			url+="&selectedZone="+ selectedZone;
			url+= "&selectedMRNo=" + selectedMRNo;
			url+="&selectedArea="+selectedArea;
			url+="&mrKeyListForSearch="+mrKeyListForSearch;
			url+="&searchBy="+searchBy;
			url+="&selectedStatusCode="+selectedStatusCode;
			url+="&selectedErrorCode="+selectedErrorCode;
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
							} else {
								document.getElementById('summaryDiv').innerHTML = responseString;								
								//alert(document.getElementById('idProgressBar'));
								if(document.getElementById('totalProgress')){
									var totalProgress=Trim(document.getElementById('totalProgress').value);								
									if('100'!=totalProgress){
										fnSwitchOnAutoRefresh();
										fnSetAutoRefreshOn();
										if(document.getElementById('progressBar')){
											progressBar(totalProgress, $('#progressBar'));
										}	
									}else{
										fnSwitchOffAutoRefresh();
									}
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
	function fnResetSummary() {
		document.getElementById('summaryDiv').innerHTML = "";
		//document.getElementById('summaryDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";        
		
	}
	function fnGenerateBill(){
		var totalBilableRecordsTDValue="";
		if(document.getElementById('totalBilableRecordsTD')){
			totalBilableRecordsTDValue=document.getElementById('totalBilableRecordsTD').innerHTML;
			totalBilableRecordsTDValue=(totalBilableRecordsTDValue).substring(parseInt(totalBilableRecordsTDValue.indexOf(">"))+1,totalBilableRecordsTDValue.indexOf("</"));
		}
		var confirmMessage="Are You Sure You want to Generate Bill";
		if(''!=totalBilableRecordsTDValue){
			confirmMessage+=" for "+totalBilableRecordsTDValue+" record(s) ?";
		}else{
			confirmMessage+="?";
		}
		confirmMessage+="\nClcik OK to continue else Cancel.";
		if(confirm(confirmMessage)){
			var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
			var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
			var mrKeyListForSearch = Trim(document.getElementById('mrKeyListForSearch').value);
			var searchBy = Trim(document.getElementById('searchBy').value);
			document.getElementById('onscreenMessage').innerHTML = "";		
			var url = "onlineBatchBillingAJax.action?hidAction=generateBill&selectedMRKEY="+ selectedMRKEY+"&selectedBillRound="+selectedBillRound+"&searchBy="+searchBy+"&mrKeyListForSearch="+mrKeyListForSearch;
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
				document.getElementById('btnGenerateBill').disabled = true;
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
								showScreen();
							} else {
								document.getElementById('idProgressBar').innerHTML =responseString;
								if(document.getElementById('imgProgress')){
									showAjaxLoading(document.getElementById('imgProgress'));
								}
								showScreen();
								fnSetAutoRefreshOn();
								if(document.getElementById('totalProgress')){
									var totalProgress=Trim(document.getElementById('totalProgress').value);
									if(document.getElementById('progressBar')){
										progressBar(totalProgress, $('#progressBar'));
									}	
								}
								fnPopulateDashBoard();
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
	function fnRefreshGenerateBill(){
		fnSwitchOffAutoRefresh();
		if(document.getElementById('autoRefreshCheckBox')){
			document.getElementById('autoRefreshCheckBox').disabled=true;
		}
		var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		var mrKeyListForSearch = Trim(document.getElementById('mrKeyListForSearch').value);
		var searchBy = Trim(document.getElementById('searchBy').value);
		document.getElementById('onscreenMessage').innerHTML = "";		
		var url = "onlineBatchBillingAJax.action?hidAction=refreshGenerateBill&selectedMRKEY="+ selectedMRKEY+"&selectedBillRound="+selectedBillRound+"&searchBy="+searchBy+"&mrKeyListForSearch="+mrKeyListForSearch;
		url += "&summaryFor="+Trim(document.getElementById('summaryFor').value);	
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
							hideAjaxLoading(document.getElementById('imgProgress'));
						} else {
							document.getElementById('summaryDiv').innerHTML =responseString;
							if(document.getElementById('imgProgress')){
								showAjaxLoading(document.getElementById('imgProgress'));
							}
							if(document.getElementById('totalProgress')){
								var totalProgress=Trim(document.getElementById('totalProgress').value);								
								if('100'==totalProgress){
									/*if(confirm('Billing Process is Complete.\nFor Latest Summary need to Refresh Your Search.\nDo you want to Refresh your Search?\nClick OK to Refresh else Cancel.')){
										fnGetSummary();
									}*/
									fnSwitchOffAutoRefresh();
								}else{
									fnSetAutoRefreshOn();
								}
								if(document.getElementById('totalProgress')){
									if(document.getElementById('progressBar')){
										document.getElementById('progressBar').title="Progress "+totalProgress+"%";
										progressBar(totalProgress, $('#progressBar'));
									}else{
										document.getElementById('progressBar').title="";
									}		
								}
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
	var isAutoRefreshOn=false;
	var isAutoRefreshOnEnabled=false;
	var autoRefreshTime="<%=PropertyUtil.getProperty("AUTO_REFRESH_TIME")%>";
	function fnSetAutoRefreshOn(){
		//alert('autoRefreshTime::'+autoRefreshTime);
		if(!isAutoRefreshOn){
			isAutoRefreshOn=true;
			isAutoRefreshOnEnabled=true;
			setInterval('fnAutoRefreshProgressBar()',autoRefreshTime);			
		}
	}
	function fnSwitchAutoRefresh(){
		if(document.getElementById('autoRefreshCheckBox')){
			//alert(document.getElementById('autoRefreshCheckBox').checked);
			if(document.getElementById('autoRefreshCheckBox').checked){
				fnSwitchOnAutoRefresh();
			}else{
				fnSwitchOffAutoRefresh();
			}
		}
	}
	function fnSwitchOffAutoRefresh(){
		isAutoRefreshOnEnabled=false;
		if(document.getElementById('btnRefresh')){
			document.getElementById('btnRefresh').disabled=false;
		}
	}
	function fnSwitchOnAutoRefresh(){
		isAutoRefreshOnEnabled=true;
	}
	function fnAutoRefreshProgressBar(){
		if(document.getElementById('totalProgress')&&isAutoRefreshOnEnabled){
			//alert('isAutoRefreshOn::'+isAutoRefreshOn);
			var totalProgress=Trim(document.getElementById('totalProgress').value);								
			if('100'==totalProgress){
				return;
			}else{			
				var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
				var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
				var mrKeyListForSearch = Trim(document.getElementById('mrKeyListForSearch').value);
				var searchBy = Trim(document.getElementById('searchBy').value);
				document.getElementById('onscreenMessage').innerHTML = "";		
				var url = "onlineBatchBillingAJax.action?hidAction=refreshProgressBar&selectedMRKEY="+ selectedMRKEY+"&selectedBillRound="+selectedBillRound+"&searchBy="+searchBy+"&mrKeyListForSearch="+mrKeyListForSearch;
				url += "&summaryFor="+Trim(document.getElementById('summaryFor').value);	
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
					if(document.getElementById('btnRefresh')){
						document.getElementById('btnRefresh').disabled=true;
					}	
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
									if(document.getElementById('imgProgress')){
										hideAjaxLoading(document.getElementById('imgProgress'));
									}
								} else {
									/*document.getElementById('idProgressBar').innerHTML =responseString;
									if(document.getElementById('imgProgress')){
										showAjaxLoading(document.getElementById('imgProgress'));
									}*/
									if(document.getElementById('autoRefreshCheckBox')&&isAutoRefreshOnEnabled){
										document.getElementById('autoRefreshCheckBox').checked=true;
									}
									if(document.getElementById('btnRefresh')){
										document.getElementById('btnRefresh').disabled=false;
									}
									if(document.getElementById('totalProgress')){
										document.getElementById('totalProgress').value=Trim(responseString);
										var totalProgress=Trim(document.getElementById('totalProgress').value);											
										if(document.getElementById('progressBar')){
											progressBar(totalProgress, $('#progressBar'));
											document.getElementById('progressBar').title="Progress "+totalProgress+"%";
										}else{
											document.getElementById('progressBar').title="";
										}					
										if('100'==totalProgress){
											if(document.getElementById('imgProgress')){
												hideAjaxLoading(document.getElementById('imgProgress'));
											}
											if(confirm('Billing Process is Complete.\nFor Latest Summary need to Refresh Your Search.\nDo you want to Refresh your Search?\nClick OK to Refresh else Cancel.')){
												fnRefreshGenerateBill();
												fnPopulateDashBoard();
											}
										}else{
											if(document.getElementById('imgProgress')){
												showAjaxLoading(document.getElementById('imgProgress'));
											}
										}											
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
	}
	function fnPopulateDashBoard(){
		document.getElementById('dashBoardDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";        
		var url = "onlineBatchBillingAJax.action?hidAction=populateDashBoard";
		url += "&fromDate="+Trim(document.getElementById('fromDate').value);
		url += "&toDate="+Trim(document.getElementById('toDate').value);
		url += "&summaryFor="+Trim(document.getElementById('summaryFor').value);		
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
					//alert(httpBowserObject.responseText);
					if (null != responseString
								&& responseString.indexOf('<script') == -1) {
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
						} else {
							document.getElementById('dashBoardDiv').innerHTML =responseString;
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}				
			};
			httpBowserObject.send(null);
		}
	}
	function fnGetCurrentlyRunningJobs(){
		document.getElementById('summaryDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";
		var url = "onlineBatchBillingAJax.action?hidAction=populateCurrentlyRunningJobs";
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
					//alert(httpBowserObject.responseText);
					if (null != responseString
								&& responseString.indexOf('<script') == -1) {
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
						} else {
							document.getElementById('summaryDiv').innerHTML =responseString;
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}				
			};
			httpBowserObject.send(null);
		}
	}
	var loadingComplete=false;
	function fnShowDetails(mrKey){		
		var searchBy = Trim(document.getElementById('searchBy').value);
		document.getElementById('summaryDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";        
		document.getElementById('selectedMRKEY').value=mrKey;
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		if(''!=selectedMRKEY){
			document.getElementById('searchBy').value="";
		}
		document.getElementById('onscreenMessage').innerHTML = "";		
		setStatusBarMessage("");
		var url = "onlineBatchBillingAJax.action?hidAction=populateZoneMRAreaByMRKEY&selectedMRKEY="+ selectedMRKEY+"&searchBy="+searchBy;
		url += "&summaryFor="+Trim(document.getElementById('summaryFor').value);	
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
								document.getElementById('selectedBillRound').value="";
								document.getElementById('selectedBillRound').disabled=false;
							}else{
								document.getElementById('selectedBillRound').value=openBillRound;
								document.getElementById('selectedBillRound').disabled=true;								
							}
							fnGetSummary();
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}							
			};
			httpBowserObject.send(null);
		}		
	}
	function fnGetBillingSummary(processCounter,statusCode){
		 $('#billingSummaryDiv').fadeOut('slow'); 
		 //document.getElementById('billingSummaryDiv').innerHTML = "<img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\"/>";        
		var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		var mrKeyListForSearch = Trim(document.getElementById('mrKeyListForSearch').value);
		var searchBy = Trim(document.getElementById('searchBy').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "onlineBatchBillingAJax.action?hidAction=populateBillingSummary";
		url+="&selectedBillRound="+ selectedBillRound;
		url+="&selectedMRKEY="+ selectedMRKEY;
		url+="&mrKeyListForSearch="+ mrKeyListForSearch;
		url+="&searchBy="+searchBy;
		url+="&selectedStatusCode="+statusCode;
		url+="&selectedProcessCounter="+processCounter;
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
					//alert(httpBowserObject.responseText);
					showScreen();
					if (null != responseString
								&& responseString.indexOf('<script') == -1) {
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
						} else {
							document.getElementById('billingSummaryDiv').innerHTML =responseString;
							// $('#summaryDiv').fadeOut('fast'); 
							 document.getElementById('summaryDiv').style.display='none';
							 $('#billingSummaryDiv').fadeIn(2000); 
						}
					} else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
					}
				}							
			};
			httpBowserObject.send(null);
		}		
	}
	function fnCloseBillingSummary(){
		//$('#billingSummaryDiv').fadeOut('fast'); 
		document.getElementById('billingSummaryDiv').style.display='none';
		 $('#summaryDiv').fadeIn(2000); 
		 //document.getElementById('billingSummaryDiv').style.display='none';
		 //document.getElementById('summaryDiv').style.display='block';
	}
 	function gotoSearch(){
 		if (document.forms[0].selectedMRKEY &&Trim(document.forms[0].selectedMRKEY.value) == '' &&( Trim(document.forms[0].mrKeyListForSearch.value) == '' || Trim(document.forms[0].mrKeyListForSearch.value) == (Trim(document.forms[0].mrKeyListForSearch.title)).toString())) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a MRKEY or Zone MR Area Combination or Enter List of MRKeys.</font>";
			if(!document.forms[0].selectedMRKEY.disabled){
				document.forms[0].selectedMRKEY.focus();
			}
			if(!document.forms[0].mrKeyListForSearch.disabled){
				document.forms[0].mrKeyListForSearch.focus();
			}
			return;
		}	
		if(Trim(document.forms[0].mrKeyListForSearch.value) != '' && Trim(document.forms[0].mrKeyListForSearch.value) != (Trim(document.forms[0].mrKeyListForSearch.title)).toString()){
			document.getElementById('searchBy').value="ListOfMRKeys";
			var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
			document.getElementById('selectedZone').value="";
			document.getElementById('selectedMRNo').value="";
			document.getElementById('selectedArea').value="";
			document.getElementById('selectedMRKEY').value="";
			document.getElementById('selectedZROCode').value="";
			if(document.getElementById('selectedBillRound').disabled){
				document.getElementById('selectedBillRound').value="";
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Bill Round for the List of MRKeys.</font>";
				document.getElementById('selectedBillRound').disabled=false;
				document.getElementById('selectedBillRound').focus();
				return;
			}
			if(''==selectedBillRound){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Bill Round for the List of MRKeys.</font>";
				document.getElementById('selectedBillRound').disabled=false;
				document.getElementById('selectedBillRound').focus();
				return;
			}
			if(!fnValidateCommaSepartedListOfNumber(document.forms[0].mrKeyListForSearch)){
				return;
			}
		}
	 	fnSearch();
 	}	
	function fnSearch() {
		fnGetSummary();
	}
	function fnEnableField(obj){
		obj.disabled=false;
	}
	function fnDisableField(obj){
		obj.disabled=true;
	}
	function progress(percent, $element) {
		var progressBarWidth = percent * $element.width() / 100; 
		$element.find('div').animate({ width: progressBarWidth }, 500).html(percent + "%&nbsp;");  
	} 
	progress(80, $('#progressBar'));		
</script>
<style type="text/css">
#progressBar {
	width: 400px;
	height: 22px;
	border: 1px solid #111;
	background-color: #292929;
}

#progressBar div {
	height: 100%;
	color: #fff;
	text-align: right;
	line-height: 22px;
	/* same as #progressBar height if we want text middle aligned */
	width: 0;
	background-color: #0099ff;
}

#billingSummaryDiv {
	padding: 1px;
	border: 1px solid #fff;
	z-index: 1000;
	position: fixed;
	top: 0%;
	left: 0%;
	width: 100%;
	/*height: 150px;*/
	overflow-x: hidden;
	overflow-y: hidden;
	color: #FFFFFF;
	text-align: center;
	vertical-align: middle;
	scrollbar-base-color: #21c5d8;
}

#dashBoardDiv {
	scrollbar-base-color: #21c5d8;
}
</style>
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
					<s:hidden name="searchBy" id="searchBy" />
					<s:hidden name="totalRecordsBillable" id="totalRecordsBillable" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Search Criteria</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="10%"><label>Zone</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Zone" nowrap><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZone" id="selectedZone"
										onchange="fnGetMRNo();fnResetSummary();" /></td>
									<td align="left" width="10%"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" id="imgSelectedZone" /></td>
									<td align="right" width="10%"><label>MR No</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="15%" title="MR No" id="mrNoTD"><s:select
										list="#session.MRNoListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="selectedMRNo" id="selectedMRNo"
										onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
									<td align="left" width="25%"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" id="imgSelectedMRNo" /></td>
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
										title="Processing" style="display: none;" id="imgSelectedArea" /></td>
									<td align="right"><label>MRKEY</label><font color="red"><sup>*</sup></font></td>
									<td align="left"><s:select name="selectedMRKEY"
										id="selectedMRKEY" headerKey="" headerValue="Select or Enter~~>"
										list="#session.MRKEYListMap" cssClass="selectbox"
										theme="simple" onclick="fnEnableField(this);"
										onchange="fnGetZoneMRAreaByMRKEY();fnResetSummary();" /></td>
									<td align="left" rowspan="2"><s:textarea
										name="mrKeyListForSearch" id="mrKeyListForSearch" rows="3"
										cols="30" theme="simple" cssClass="textarea"
										title="Enter List of MR Keys separated by comma only."
										onclick="fnEnableField(this);" onchange="fnValidateCommaSepartedListOfNumber(this);"/></td>
								</tr>
								<tr>
									<td align="right"><label>ZRO Code</label><font color="red"><sup></sup></font></td>
									<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
										list="#session.ZROListMap" cssClass="selectbox-long"
										theme="simple" name="selectedZROCode" id="selectedZROCode"
										disabled="true" /></td>
									<td align="left" width="10%">&nbsp;</td>
									<td align="right"><label>Bill Round</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" title="Bill Round" nowrap><s:select
										name="selectedBillRound" id="selectedBillRound" headerKey=""
										headerValue="Please Select" list="#session.OpenBillRoundListMap"
										theme="simple" cssClass="selectbox"
										onchange="fnResetSummary();" />
									<td align="left">&nbsp;</td>
								</tr>
								<tr>
									<td align="right" colspan="2">&nbsp;<input type=button
										value="View Currently Submitted Job(s) " class="groovybutton"
										theme="simple" id="btnCurrentlyRunningJobs"
										onclick="fnGetCurrentlyRunningJobs();"
										title="Click to View All the Job(s) Currently Running or Submitted by any User." />&nbsp;</td>
									<td align="left">&nbsp;</td>
									<td align="left">&nbsp;</td>
									<td align="left" colspan="2"><s:submit
										key="     Search    " cssClass="groovybutton" theme="simple"
										id="btnSearch" /></td>
								</tr>
								<tr>
									<td align="left" colspan="2"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
									<td align="right" colspan="4"><font color="blue">Note:
									To Enter List of MR Keys MRKEY Dropdown must not be Selected.</font></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td>
							<fieldset><legend>Billing Summary</legend>
							<table width="100%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="billingSummaryDiv" style="display: none;"
										title="Billing Summary details."></div>
									<div id="summaryDiv"
										style="color: blue; align: center; z-index: 10;">&nbsp;<img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Processing" style="display: none;" /><font
										color="blue">Please select a Search Criteria and Click
									Search or Click on Details of Your Job Summary to see the
									Details.</font></div>
									</td>
								</tr>
								<tr>
									<td align="center">&nbsp;</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td>
							<fieldset><legend><select id="summaryFor"
								name="summaryFor" onchange="fnPopulateDashBoard();">
								<option value="">My</option>
								<option value="ALL">ALL</option>
							</select> Job Summary From <input
										type="text" id="fromDate" class="textbox" size="10"
										onchange="fnPopulateDashBoard();" style="text-align: center;"
										title="DD/MM/YYYY"> To <input type="text" id="toDate"
										class="textbox" size="10" onchange="fnPopulateDashBoard();"
										style="text-align: center;" title="DD/MM/YYYY"></legend>
							<table width="98%" align="center" border="0">								
								<tr>
									<td align="center">
									<div id="dashBoardDiv" style="color: blue; align: center;">&nbsp;<img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading Current Job Summary" style="display: block;" /></div>
									</td>
								</tr>
								<tr>
									<td align="center">&nbsp;</td>
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
	var todaysDate="<%=(String) session.getAttribute("TodaysDate")%>";	
	$(document).ready(function(){
		$( "#fromDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy');
		$( "#toDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
		$( "#fromDate" ).datepicker( "option","showAnim",'slide' );
		$( "#toDate" ).datepicker( "option","showAnim",'slide' );
		document.getElementById('fromDate').value = todaysDate;
		document.getElementById('toDate').value = todaysDate;
		fnPopulateDashBoard();
	});	
	$("form").submit(function(e) {
		gotoSearch();
	});	
	$(".textarea").focus(function() { 
		$(this).removeClass("textarea"); 
		$(this).addClass("textarea-focus"); 
	}); 
	$(".textarea").blur(function() {
		$(this).removeClass("textarea-focus"); 
		$(this).addClass("textarea"); 
	});
	$('textarea').each(function(){	  
		this.value = $(this).attr('title'); 
		$(this).focus(function(){
			if(this.value == $(this).attr('title')) {
				this.value = '';
				document.getElementById('selectedZone').disabled=true;
				document.getElementById('selectedMRNo').disabled=true;
				document.getElementById('selectedArea').disabled=true;
				document.getElementById('selectedMRKEY').disabled=true;
				document.getElementById('selectedBillRound').disabled=false;				
				document.getElementById('searchBy').value="ListOfMRKeys";
				document.getElementById('selectedZone').value="";
				document.getElementById('selectedMRNo').value="";
				document.getElementById('selectedArea').value="";
				document.getElementById('selectedMRKEY').value="";
				document.getElementById('selectedZROCode').value="";				
				document.getElementById('selectedBillRound').value="";
			}
		});	 
		$(this).blur(function(){
			if(this.value == '') {
				this.value = $(this).attr('title');
				document.getElementById('selectedZone').disabled=false;
				document.getElementById('selectedMRNo').disabled=false;
				document.getElementById('selectedArea').disabled=false;
				document.getElementById('selectedMRKEY').disabled=false;
				document.getElementById('searchBy').value="MRKey";
			}
		});
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