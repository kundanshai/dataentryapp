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
<title>Meter Replacement Review Page - Revenue Management
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
<script type="text/javascript"
	src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
<script language=javascript>
	var isPopUp = false;
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	var validId = false;
	function fnGetMRNo() {
		var zoneMR = Trim(document.getElementById('zoneMR').value);
		if (zoneMR != '') {	
			document.getElementById('onscreenMessage').innerHTML = "";		
			var url = "callMeterReplacementAJax.action?hidAction=populateMRNo&zone="
					+ zoneMR;
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
				if (!isPopUp) {
					window.focus();
					//window.scrollTo(0,0);
					hideScreen();
					//hideMRScreen();				
					document.body.style.overflow = 'hidden';
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
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
							document.getElementById('onscreenMessage').title = "";
							document.getElementById('mrKeyList').disabled = false;
							document.getElementById('knoMR').disabled = false;
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
						} else {
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
							document.getElementById('mrNoTD').innerHTML = responseString;
							document.getElementById('areaMR').value = "";
							document.getElementById('knoMR').value = "";
							document.getElementById('mrKeyList').value = "";
							document.getElementById('knoMR').disabled = true;
							document.getElementById('mrKeyList').disabled = true;
							document.getElementById('areaMR').disabled = true;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetArea() {
		var zoneMR = Trim(document.getElementById('zoneMR').value);
		var mrNoMR = Trim(document.getElementById('mrNoMR').value);
		if(''!=zoneMR && ''!=mrNoMR){
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "callMeterReplacementAJax.action?hidAction=populateArea&zone="
					+ zoneMR + "&mrNo=" + mrNoMR;
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
				if (!isPopUp) {
					window.focus();
					hideScreen();
					//hideMRScreen();				
					document.body.style.overflow = 'hidden';
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
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
							document.getElementById('onscreenMessage').title = "";
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
						} else {
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
							document.getElementById('areaTD').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnCheckZone(obj) {
		if (document.forms[0].zoneMR.value == '') {
			//alert('Please Select Zone First');
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].zoneMR.disabled)) {
				document.forms[0].zoneMR.focus();
			}
			return;
		}
	}
	function fnCheckZoneMRNo(obj) {
		if (document.forms[0].zoneMR.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].zoneMR.disabled)) {
				document.forms[0].zoneMR.focus();
			}
			return;
		}
		if (document.forms[0].mrNoMR.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MR No. First</b></font>";
			if (!(document.forms[0].mrNoMR.disabled)) {
				document.forms[0].mrNoMR.focus();
			}
			return;
		}
	}
	function fnEnableSaveButton() {
		//document.getElementById('btnSaveMR').disabled=false;
	}	
	function fnSetSelectStatus() {
		var message = "Loaded successfully";
		var knoSelected = 0;
		var checkboxId = "";
		var noOfAccountId = "";
		var selectedNoOfAccount = 0;
		if (document.getElementById('searchResult')) {
			var rows = document.getElementById('searchResult').rows;
			//alert("rows  "+rows.length);
			for ( var i = 1; i < rows.length - 2; i++) {
				checkboxId = "trCheckbox" + i;
				noOfAccountId = "trNoOfAccount" + i;
				//alert("M "+checkboxId);
				if (document.getElementById(checkboxId).checked && document.getElementById(checkboxId).disabled==false) {
					//selectedNoOfAccount+=parseInt(document.getElementById(noOfAccountId).value);
					knoSelected++;
				}else{
					document.getElementById(checkboxId).checked=false;
				}
			}
			message = "Total No. of Selected Account(s) = " + knoSelected;//+", Total No.of Accounts = "+selectedNoOfAccount;
		} else {
			var serverMsg = document.getElementById('onscreenMessage').innerHTML;
			if (serverMsg == '') {
				message = "Please provide a Search Criteria and click Search button";
			}
		}
		setStatusBarMessage(message);
		document.getElementById('totalRecordsSpan').innerHTML = "<font color='blue' size='2'>"+message+"</font>";
				document.getElementById('btnFreeze').focus();
		}
	function gotoNextPage() {
		document.forms[0].pageNo.value = parseInt(document.forms[0].pageNo.value) + 1;
		fnSearch();
	}
	function gotoPreviousPage() {
		document.forms[0].pageNo.value = parseInt(document.forms[0].pageNo.value) - 1;
		fnSearch();
	}
	function gotoSearch() {
		if (document.forms[0].pageNo) {
			document.forms[0].pageNo.value = "1";
		}
		fnSearch();
	}
	function fnSearch() {		
		if ((Trim(document.forms[0].knoMR.value) == '')
				&& (Trim(document.forms[0].mrKeyList.value) == '')
				&& (Trim(document.forms[0].zoneMR.value) == '')) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Atleast One Search Criteria</font>";
			return;
		}
		if ((Trim(document.forms[0].zoneMR.value) != '')&& (Trim(document.forms[0].mrNoMR.value) == '' || Trim(document.forms[0].areaMR.value) == '')) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Valid Search Criteria</font>";			
			return;
		}
		document.forms[0].hidAction.value = "search";
		document.forms[0].action = "meterReplacementReview.action";
		hideScreen();
		document.forms[0].submit();
	}
	function fnSearchAfterFreeze(){
		document.getElementById('searchFor').value="Frozen";
		document.forms[0].hidAction.value = "searchAfterFreeze";
		document.forms[0].action = "meterReplacementReview.action";
		hideScreen();
		document.forms[0].submit();
	}
	//Not used in this page but used in Meter Replacement Page
	//As fnGetArea() fetches this method Ajax Response to avoid "Object not found Error" defining the function body
	function fnGetConsumerDetailsByZMAW() {
	}
	function fnFreezeMeterReplacementDetails() {
		var knoList = "";
		var billRoundList = "";
		var rows = document.getElementById('searchResult').rows;
		var checkboxId = "";
		var knoId = "";
		var totalAccount = 0;
		for ( var i = 1; i < rows.length - 2; i++) {
			checkboxId = "trCheckbox" + i;
			knoId = "kno" + i;
			billRoundId = "billRound" + i;
			if (document.getElementById(checkboxId).checked) {
				totalAccount++;
				knoList += document.getElementById(knoId).value + ",";
				billRoundList += document.getElementById(billRoundId).value
						+ ",";
			}
		}
		if(knoList==''){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select atleast one Account to Freeze.</font>";	
			//document.getElementById('selectAll').focus();		
			window.scrollTo(0,0);
			return;
		}else{
			if(confirm('Are You Sure You Want to Freeze Selected Record(s) ?\n\nClick OK to Continue else Cancel')){
				hideScreen();
				/*document.forms[0].knoList.value=knoList;
				document.forms[0].billRoundList.value=billRoundList;
				document.forms[0].hidAction.value = "freezeMRD";
				document.forms[0].action = "meterReplacementReview.action";
				hideScreen();
				document.forms[0].submit();*/
				 var url = "meterReplacementReviewAJax.action?hidAction=freezeMRD&knoList="
						+ knoList + "&billRoundList=" + billRoundList;
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
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Records could not be Freezed Successfully. Please Retry.</b></font>";
								document.getElementById('onscreenMessage').title = "";
								if (!isPopUp) {
									showScreen();
									document.body.style.overflow = 'auto';
								}
							}else {							
								document.getElementById('onscreenMessage').innerHTML = responseString;
								showScreen();
								fnSearchAfterFreeze();
							}
						}
					};
					httpBowserObject.send(null);
				}
			}
		}
	}
	//Added by Rajib to Process Meter Replacement Details
	/*Modified by Matiur Rahman on 04-02-2014 as for the
	 *          changes of message in case of undefined error message as per JTrac
	 *          ID DJB-2024 updated on 04-02-2014 as per telephonic conversation
	 *          with Amit Jain.
	 */
	function fnProcessMeterReplacementDetails(kno,billRoundId,rowNo) {	
		if(confirm('Are You Sure You Want to Process Selected Record?\n\nClick OK to Continue else Cancel')){
			hideScreen();
			var url = "meterReplacementReviewAJax.action?hidAction=processMRD&kno="
					+ kno + "&billRoundId=" + billRoundId;
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
						//alert(responseString);
						document.getElementById('onscreenMessage').title ="";
						if (null != responseString && "" != responseString){
							if (responseString.indexOf('ERROR')>-1) {								 
								document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>"+responseString.substring(0,responseString.indexOf("ERROR"))+"</b></font>";
								document.getElementById('onscreenMessage').title = responseString.substring(responseString.indexOf("):")+2);
								document.getElementById('processStatus'+rowNo).innerHTML = "<font color='red'>"+responseString.substring(responseString.indexOf("):")+2)+"</font>";
								document.getElementById('processStatus'+rowNo).title = responseString.substring(responseString.indexOf("):")+2);
								if (!isPopUp) {
									showScreen();
									document.body.style.overflow = 'auto';
								}
							}else {							
								document.getElementById('onscreenMessage').innerHTML = "<font color='#33CC33'><b> Meter Replacement for KNO "+kno+" Processed Successfully"+"</b></font>";
								document.getElementById('btnProcess'+rowNo).disabled=true;
								showScreen();
								document.body.style.overflow = 'auto';
							}
						}else{
							document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Processing Couldn't be Completed, Try Again</b></font>";
							document.getElementById('processStatus'+rowNo).innerHTML = "<font color='red'>Processing Couldn't be Completed, Try Again</font>";
							showScreen();
							document.body.style.overflow = 'auto';
						}
					}
				};
				httpBowserObject.send(null);
			}
		}		
	}
	
	function fnOnFocusMRKey() {
		document.getElementById('knoMR').disabled = true;
		document.getElementById('zoneMR').disabled = true;
		document.getElementById('mrNoMR').disabled = true;
		document.getElementById('areaMR').disabled = true;
		document.getElementById('zoneMR').value = "";
		document.getElementById('mrNoMR').value = "";
		document.getElementById('areaMR').value = "";
	}
	function fnOnFocusOutMRKey() {
		var mrKey = document.getElementById('mrKeyList').value;
		if ('' == mrKey) {
			document.getElementById('knoMR').disabled = false;
			document.getElementById('zoneMR').disabled = false;
			document.getElementById('mrNoMR').disabled = false;
			document.getElementById('areaMR').disabled = false;
		}
	}
	function fnOnFocusKno() {
		document.getElementById('mrKeyList').disabled = true;
		document.getElementById('zoneMR').disabled = true;
		document.getElementById('mrNoMR').disabled = true;
		document.getElementById('areaMR').disabled = true;
		document.getElementById('zoneMR').value = "";
		document.getElementById('mrNoMR').value = "";
		document.getElementById('areaMR').value = "";
	}
	function fnOnFocusOutKno() {
		var mrKey = document.getElementById('knoMR').value;
		if ('' == mrKey) {
			document.getElementById('mrKeyList').disabled = false;
			document.getElementById('zoneMR').disabled = false;
			document.getElementById('mrNoMR').disabled = false;
			document.getElementById('areaMR').disabled = false;
		}
	}
	function fnOnFocusOutZone() {	
		if ('' != document.getElementById('zoneMR').value) {
			document.getElementById('mrKeyList').disabled = false;
			document.getElementById('knoMR').disabled = false;
		}
	}
	function checkValNumeric(e) {
		try {
			var unicode = e.charCode ? e.charCode : e.keyCode;
			if (unicode != 8) {
				if (!(unicode >= 48 && unicode <= 57)) {
					return false;
				}
			}
		} catch (e) {
		}
	}
	function popUpMeterReplacementScreen(seqNo,billRound,kno){
	  	var url="meterReplacement.action?hidAction=getDetailsForPOPUP&seqNo=&kno="+kno+"&billRound="+billRound+"&searchForMR="+document.getElementById('searchFor').value;
	  	var l=(screen.width-1000)/2;
	  	var t=(screen.height-768)/2;	  	
	  	var popupNewWin=window.open(url,"_blank","directories=no,menubar=no,toolbar=no,scrollbars=yes,status=yes,width=1000, height=710,top="+t+",left="+l);
	  	//popupNewWin.focus();
	}	
</script>
<style>
#searchResultDiv{
	height:200px;
	overflow:auto;
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
			<tr>
				<td valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font><%=session.getAttribute("AJAX_MESSAGE")%><%=session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form name="MR" method="post" action="javascript:void(0);"
					theme="simple" onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="knoList" id="knoList" />
					<s:hidden name="billRoundList" id="billRoundList" />
					<s:hidden name="totalRecords" id="totalRecords" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Search Criteria</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="25%"><s:select list="{'Frozen'}"
										headerKey="" headerValue="Freezable" cssClass="selectbox"
										theme="simple" name="searchFor" id="searchFor"
										onchange="javascript:gotoSearch();" /></td>
									<td align="left" width="25%" title="KNO of the Consumer"><label>KNO</label><font
										color="red"><sup>**</sup></font><s:textfield name="knoMR"
										id="knoMR" maxlength="10" cssClass="textbox"
										onblur="fnOnFocusOutKno();" onfocus="fnOnFocusKno();"
										onkeypress="return checkValNumeric(event);" /></td>
									<td align="right" width="25%"
										title="May be entered List of MR Keys separated by comma"><label>MRKey
									List</label><font color="red"><sup>**</sup></font></td>
									<td align="left" width="25%"
										title="May be entered List of MR Keys separated by comma"><s:textfield
										name="mrKeyList" id="mrKeyList" maxlength="500"
										cssClass="textbox-long" onblur="fnOnFocusOutMRKey();"
										onfocus="fnOnFocusMRKey();" /></td>
								</tr>
								<tr>
									<td align="center" colspan="4">
									<fieldset><legend><font color="red"><sup>**</sup></font></legend>
									<table width="95%" align="center" border="0">
										<tr>
											<td align="right" width="25%"><label>Zone</label></td>
											<td align="left" width="25%" title="Zone"><s:select
												list="#session.ZoneListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="zoneMR" id="zoneMR"
												onchange="fnGetMRNo(this);" onblur="fnOnFocusOutZone();" /></td>
											<td align="right" width="25%"><label>MR No</label></td>
											<td align="left" width="25%" title="MR No" id="mrNoTD"><s:select
												list="#session.MRNoListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox"
												theme="simple" name="mrNoMR" id="mrNoMR"
												onchange="fnGetArea(this);" onfocus="fnCheckZone(this);" /></td>
										</tr>
										<tr>
											<td align="right" width="25%"><label>Area</label></td>
											<td align="left" width="25%" title="Area" id="areaTD"><s:select
												list="#session.AreaListMap" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" name="areaMR" id="areaMR"
												onfocus="fnCheckZoneMRNo(this);" /></td>
											<td align="left">&nbsp;</td>
										</tr>
									</table>
									</fieldset>
									</td>
								</tr>
								<tr>
									<td align="right" colspan="4"><s:submit key="label.search"
										cssClass="groovybutton" theme="simple" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Any
									one of <font color="red"><sup>**</sup></font> marked fields are
									mandatory.</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<s:if test="meterReplacementDetailList.size() > 0">
							<tr>
								<td align="center" valign="top">
								<fieldset><legend>Search Result</legend>
								<div id="searchResultDiv">
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
										<th align="center" colspan="8" width="80%">Page No:<s:select
											list="pageNoDropdownList" name="pageNo" id="pageNo"
											cssClass="smalldropdown" theme="simple"
											onchange="fnSearch();" /> Showing maximum <s:select
											list="{10,20,30,40,50,100}"
											name="maxRecordPerPage" id="maxRecordPerPage"
											cssClass="smalldropdown" theme="simple"
											onchange="fnSearch();" />records per page of total <s:property
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
										  <th>&nbsp;</th> 
									</tr>
									<tr>
										<th align="center" width="3%" rowspan="2">SL</th>
										<th align="center" width="5%" rowspan="2">Service Seq</th>
										<th align="center" width="7%" rowspan="2">Bill Round</th>
										<th align="center" width="10%" rowspan="2">KNO</th>
										<th align="center" width="20%" rowspan="2">NAME</th>
										<th align="center" width="10%" rowspan="2">WC No</th>
										<th align="center" width="45%" rowspan="1" colspan="5">Current
										Read Details</th>
										<!--  -->
										<s:if test="searchFor==\"Frozen\"">	
											<% if(userRoleId.equals(roleScreenMap.get("20"))||userId.equals(userScreenMap.get("20"))) { %>
 											<th align="center" width="3%" rowspan="2"></th>
											<%}%>
										</s:if>
										<s:else>
										<% if(userRoleId.equals(roleScreenMap.get("20"))||userId.equals(userScreenMap.get("20"))) { %>	
											<th align="center" width="3%" rowspan="2"><input
												type="checkbox" id="selectall" />Select All</th>
												<%}else{ %>
												<th align="center" width="3%" rowspan="2"><input
												type="checkbox" id="selectall" disabled="true" />Select All</th>
												<%}%>
										</s:else>
										<!--  Changed for last processing status on 07-01-2014 -->
										<th align="center" width="5%" rowspan="2">Last Processing Status</th>
									</tr>
									<tr>
										<th align="center" width="10%">Date</th>
										<th align="center" width="10%">Remark</th>
										<th align="center" width="10%">Read</th>
										<th align="center" width="10%">Average Consumption</th>
										<th align="center" width="5%">No Of Floor(s)</th>
										
									</tr>
									<s:iterator value="meterReplacementDetailList" status="row">
										<s:if test="searchFor!=\"Frozen\"">
											<s:if test="meterReplaceStageID==\"350\"">
											<tr bgcolor="green"
												onMouseOver="javascript:this.bgColor
											= 'yellow'"
												onMouseOut="javascript:this.bgColor='green'" title="">
										</s:if>
										<s:else>
											<tr bgcolor="grey" title="Cannot be Frozen as Status ID is <s:property value="meterReplaceStageID" />.">
										</s:else>
										</s:if>
										<s:else>
											<s:if
											test="preBillStatID==\"65\"">
											<tr bgcolor="green" onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='green'" title="Ready for Processing.">
											</s:if>
											<s:else>
												<tr bgcolor="grey" title="Cannot be Processed as Status ID is <s:property value="preBillStatID" />.">
											</s:else>
										</s:else>
										<td align="center"><s:property value="seqNo" /></td>
										<td align="center"><s:property value="serviceSeqNo" /></td>
										<td align="center" nowrap><s:property value="billRound" /></td>
										<td align="center"><s:property value="kno" /> <a
											href="#"
											onclick="javascript:popUpMeterReplacementScreen('','<s:property value="billRound" />','<s:property value="kno" />');">
										<img src="/DataEntryApp/images/page_edit.gif" border="0"
											width="15" title="Click to View/Edit Meter Replacement Details" /></a></td>
										<td align="left" nowrap><s:property value="name" /></td>
										<td align="center">&nbsp;<s:property value="wcNo" /></td>
										<td align="center" nowrap>&nbsp;<s:property
											value="currentMeterReadDate" /></td>
										<td align="center">&nbsp;<s:property
											value="currentMeterReadRemarkCode" /></td>
										<td align="center">&nbsp;<s:property
											value="currentMeterRegisterRead" /></td>
										<td align="center">&nbsp;<s:property
											value="newAverageConsumption" /></td>
										<td align="center">&nbsp;<s:property value="noOfFloors" /></td>
										<td align="center">
										<s:if
											test="searchFor==\"Frozen\"">
											<!-- Changed by Rajib as per JTrac DJB-2024  -->
											<s:if
											test="preBillStatID!=\"65\"">
											 
											<input type="button" id="btnProcess<s:property value="#row.count" />" value="Process"
											class="groovybutton"
											onclick=""
											title="Can't be Processed as preBillStatID not in 65 status" disabled="true" />   
											</s:if>	<s:else>
											<% if(userRoleId.equals(roleScreenMap.get("20"))||userId.equals(userScreenMap.get("20"))) { %>
											  
											<input type="button" id="btnProcess<s:property value="#row.count" />" value="Process"
											class="groovybutton"
											onclick="javascript:fnProcessMeterReplacementDetails('<s:property value="kno" />','<s:property value="billRound" />','<s:property value="#row.count" />');"
											title="Click to Process this Row." />  
											<%} else{%>
											 
											<input type="button" id="btnProcess<s:property value="#row.count" />" value=" Process "
											class="groovybutton"
											onclick=""
											title="Can't be Processed as Your Role/UserId don't have access" disabled="true"/> 
											<%} %>
											</s:else>	 
											<!-- Change finish by Rajib as per JTrac DJB-2024  -->
										</s:if><s:else>
											<% if(userRoleId.equals(roleScreenMap.get("20"))||userId.equals(userScreenMap.get("20"))) { %>
											 
											<s:if
											test="meterReplaceStageID!=\"350\"">
											<input type="checkbox" class="case" name="case"
												id="trCheckbox<s:property value="#row.count" />"
												disabled="true" />
										</s:if>	<s:else>
											<input type="checkbox" class="case" name="case"
												id="trCheckbox<s:property value="#row.count" />" /></s:else>
											<%} else{%>
											<input type="checkbox" class="case" name="case"
												id="trCheckbox<s:property value="#row.count" />"
												disabled="true" />
											<%} %>
										</s:else> <input type="hidden"
											name="kno<s:property value="#row.count" />"
											id="kno<s:property value="#row.count" />"
											value="<s:property value="kno" />" /><input type="hidden"
											name="billRound<s:property value="#row.count" />"
											id="billRound<s:property value="#row.count" />"
											value="<s:property value="billRound" />" /></td>
										 
										<td nowrap>
										<div id="processStatus<s:property value="#row.count" />">
										<s:if
											test="lastErrorMsg!=\"\"">
											<s:property value="lastErrorMsg" />
										</s:if>
										<s:else>
											  &nbsp;
										</s:else>
										</div>
										</td>
										
										</tr>
									</s:iterator>
								</table>
								</div>
								<br />
								<s:if test="searchFor!=\"Frozen\"">
									<% if(userRoleId.equals(roleScreenMap.get("20"))||userId.equals(userScreenMap.get("20"))) { %>									
										<span id="totalRecordsSpan">&nbsp;</span><br/>
										<input type="button" id="btnFreeze" value=" Freeze "
											class="groovybutton"
											onclick="javascript:fnFreezeMeterReplacementDetails();"
											title="Click to Freeze all selected Rows." />
									<%} %>
								</s:if><s:else>
									<b>Currently Total No. of Frozen Record(s) = &nbsp;<s:property
										value="totalRecords" /></b></br>
									</s:else> <br />
								<br />
								</fieldset>
								</td>
							</tr>
							<s:if test="searchFor!=\"Frozen\"">
								<tr>
									<td align="center" valign="top">
									<fieldset>
									<table width="90%" align="center" border="0"
										title="Warning! Records once updated cannot be Undone. So, Please select the appropriate status and MR Keys to update and check once again before updating">
										<tr>
											<td align="center" colspan="6">&nbsp;<font color="red">Warning!
											Records once freezed cannot be Undone. So, Please select the
											appropriate record to freeze and check once again before
											updating. </font></td>
										</tr>
									</table>
									</fieldset>
									</td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td align="center" valign="top">
									<fieldset>
									<table width="90%" align="center" border="0"
										title="Warning! Records once Processed cannot be Undone. So, Please check once again before processing">
										<tr>
											<td align="center" colspan="6">&nbsp;<font color="red">
											 </font></td>
										</tr>
									</table>
									</fieldset>
									</td>
								</tr>
							</s:else>
						</s:if>
					</table>
				</s:form></td>
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
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						if(document.forms[0].searchFor.value==''){
						setStatusBarMessage('Caution : Records once Frozen cannot be undone !');
						}else{
							if(document.forms[0].totalRecords.value!=''){
								setStatusBarMessage('INFO: Currently Total No. of Frozen Record(s) = '+document.forms[0].totalRecords.value);
							}
						}
					});
//Added by Rajib as per Jtrac DJB-2024
var zroLocation="<%=session.getAttribute("zroLocation")%>";
var userRole="<%=session.getAttribute("userRoleId")%>";
var thirdPartyRole="<%=PropertyUtil.getProperty("THIRD_PARTY_ROLE")%>";
	$(document).ready(function(){
		if (userRole != thirdPartyRole){
		if(zroLocation=='null'||zroLocation==''||zroLocation=='ABSENT'){
		document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>Access Denied. You Don't Have sufficient Privilege to Perform the Action,<br/> Please make sure <br/><li>You have given Biometric attendence for today.</li><li>MRD/ZRO Location is Tagged with your User ID.</li></b></font>";
		document.getElementById('MR').style.display='none';
		}
		}
	}
	);
	//Change finish by Rajib as per Jtrac DJB-2024
	
	$(function() { // add multiple select / deselect functionality     
		$("#selectall").click(function() {
			$('.case').attr('checked', this.checked);
			fnSetSelectStatus();
		}); // if all checkbox are selected, check the selectall checkbox     // and viceversa     
		$(".case").click(function() {
			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
			}
			fnSetSelectStatus();
		});
	});
	$("form").submit(function(e) {
		gotoSearch();
		//fnSearch();
		});
	var winWidth = $(window).width(); // returns height of browser viewport
	$(document).ready(function() {
		$('input[type="text"]').focus(function() {
			$(this).addClass("textboxfocus");
		});
		$('input[type="text"]').blur(function() {
			$(this).removeClass("textboxfocus");
			$(this).addClass("textbox");
		});
		winWidth=(winWidth-30)+"px";
		$('#searchResultDiv').width(winWidth);
		if((docHeight-4)>winHeight){
			var divHeight=($(document).height()/100)*33+"px";
			$('#searchResultDiv').height(divHeight);
		}
	});
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
		evt = evt || window.event;
		//alert(evt.keyCode);
		//if (evt.keyCode == 13 ) {  	//F5 Key Press 	
		//evt.preventDefault();
		//}
		/*if (evt.keyCode == 13 &&submitCount==0) {  	//Enter Key Press 	
			fnSubmit();
		}*//*																									 		 } */
	};
	var winHeight = $(window).height(); // returns height of browser viewport
	var docHeight = $(document).height(); // returns height of HTML document
	var submitCount = 0;
	//window.scroll(0,docHeight);
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>