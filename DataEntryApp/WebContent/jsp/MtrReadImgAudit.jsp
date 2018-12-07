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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Meter Read Image Audit Page - Revenue Management System, Delhi
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
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/jQuery/css/jquery.ui.all.css"/>" />
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/ImageZoom.css"/>" />
<script>

</script>
<script language=javascript>
var maxDate;
function fnGetMRNo() {
	
	var selectedZone = Trim(document.getElementById('selectedZone').value);
	if(selectedZone){
		document.getElementById('kno').value = "";
		document.getElementById('kno').disabled = true;
	}
	document.getElementById('onscreenMessage').innerHTML = "";
	if(document.getElementById('searchBox')){
		document.getElementById('searchBox').innerHTML = "&nbsp;";		
	}
	setStatusBarMessage("");
	if (selectedZone != '') {
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "demandTransferAJax.action?hidAction=populateMRNo&selectedZone="
				+ selectedZone;
		//alert(url);
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
						} else {
							document.getElementById('mrNoTD').innerHTML = responseString;							
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedZone'));
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
	if(document.getElementById('searchBox')){
		document.getElementById('searchBox').innerHTML = "&nbsp;";		
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
var selectedZROCodeDropdown="";
function fnGetMRKEY() {
	var selectedZone = Trim(document.getElementById('selectedZone').value);
	var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
	var selectedArea = Trim(document.getElementById('selectedArea').value);
	document.getElementById('onscreenMessage').innerHTML = "";
	
	if(document.getElementById('searchBox')){
		document.getElementById('searchBox').innerHTML = "&nbsp;";		
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
			showAjaxLoading(document.getElementById('imgSelectedArea'));
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
							document.getElementById('selectedZROCodeTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<ZROC>") + 6, responseString.indexOf("</ZROC>")));
							var openBillRound= Trim(responseString.substring(responseString.indexOf("<OPBR>") + 6, responseString.indexOf("</OPBR>")));
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

function clearAllFields(){
	 document.getElementById("auditForm").reset();
	 document.getElementById('selectedZROCodeTD').innerHTML = selectedZROCodeDropdown;
	 document.getElementById('selectedZoneTD').innerHTML = selectedZoneDropdown;
	 document.getElementById('mrNoTD').innerHTML = selectedMRDropdown;
	 document.getElementById('areaTD').innerHTML = selectedAreaDropdown;
	 document.getElementById('selectedZone').value="";
	 document.getElementById('selectedMRNo').value="";
	 document.getElementById('selectedArea').value="";
	 document.getElementById('selectedZROCode').value="";
	 document.getElementById('kno').value = "";
	 document.getElementById('kno').disabled = false;
	 document.getElementById('knoSpan').innerHTML ="";
	 document.getElementById('onscreenMessage').value = "";
	 document.getElementById('onscreenMessage').innerHTML = "";
	 document.getElementById('selectedAuditStatusTD').innerHTML = auditStatusDropDown;
	 document.getElementById('searchBox').innerHTML="";
	 document.getElementById('selectedBillRound').value="";
	 //document.getElementById('selectedBillGenSourceTD').innerHTML= selectedBillGenDropDown;/* Added by Madhuri On 25th July 2017 as per Jtrac Id- Android-389 */
	 /*Start:- Added by Madhuri On 25th July 2017 as per Jtrac Id- Android-388 */
	 document.getElementById('selectedBillGenSource').value="";
	 document.getElementById('selectedBillGenFromDate').value="";
	 document.getElementById('selectedBillGenToDate').value="";
	 /*End:- Added by Madhuri On 25th July 2017 as per Jtrac Id- Android-388 */
}

function fnValidateKNO(){ 
	var kno=Trim(document.getElementById('kno').value);
		if(kno.length==10){
			if(isNaN(kno)){
				document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be a 10 digits Numeric Value.</font>";		
				document.getElementById('kno').value = '';
				document.getElementById('kno').focus();
				return false;
			}else{
				document.getElementById('selectedZone').value="";
				document.getElementById('selectedMRNo').value="";
				document.getElementById('selectedArea').value="";
				document.getElementById('selectedZROCode').value="";
				document.getElementById("selectedAuditStatus").value="ALL";
				document.getElementById('selectedZone').disabled = true;
				document.getElementById('selectedMRNo').disabled = true;
				document.getElementById('selectedArea').disabled = true;
				document.getElementById('selectedZROCode').disabled = true;
				document.getElementById("selectedAuditStatus").disabled = true;
			}
			
		}
		else{
			if(kno.length==0){
				document.getElementById('knoSpan').innerHTML ="";
			}else{
				document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be of 10 digits.</font>";		
				document.getElementById('kno').focus();
				document.getElementById('selectedZone').value="";
				document.getElementById('selectedMRNo').value="";
				document.getElementById('selectedArea').value="";
				document.getElementById('selectedZROCode').value="";
				document.getElementById("selectedAuditStatus").value="PENDING";
				document.getElementById('selectedZone').disabled = false;
				document.getElementById('selectedMRNo').disabled = false;
				document.getElementById('selectedArea').disabled = false;
				document.getElementById('selectedZROCode').disabled = false;
				document.getElementById("selectedAuditStatus").disabled = false;
				return false;
			}
			
		}
		
		return true; 
}

function gotoSearch(){
	//alert('Zro location >>'+document.forms[0].selectedZROCode.value);
	if (((null == document.forms[0].selectedZROCode.value) || (Trim(document.forms[0].selectedZROCode.value) == '')) && (Trim(document.forms[0].kno.value) == '')) {
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Zone MR Area Combination or Enter a valid KNO.</font>";
		document.forms[0].selectedZROCode.focus();
		return false;
	}	
	/* Start:- by Madhuri On 25th July 2017 as per Jtrac Id- Android-389 */
	if((null == document.forms[0].selectedBillRound.value) || (Trim(document.forms[0].selectedBillRound.value) == '')){
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select Bill round </font>";
		document.forms[0].selectedBillRound.focus();
		return false;
	}
	/* End:- by Madhuri On 25th July 2017 as per Jtrac Id- Android-389 */
	 if(document.forms[0].kno.value){
		 if (Trim(document.getElementById('kno').value).length != 10){
			 document.getElementById('kno').focus();
			 return false;
		 }else{
			fnValidateKNO();
		 }
	 }
	 if(document.forms[0].pageNo){
			document.forms[0].pageNo.value="1";
	 	}
	fnSearch();
}

function fnSearch() {
	document.forms[0].action = "mtrReadImgAudit.action";
	document.forms[0].hidAction.value = "search";
	document.forms[0].searchZROCode.value = document.forms[0].selectedZROCode.value;
	document.forms[0].searchBillRound.value = document.forms[0].selectedBillRound.value;
	/*Start: Added by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
	document.forms[0].searchMeterRdrEmpId.value = document.forms[0].selectedMtrRdrEmpId.value;
	document.forms[0].searchdBillGenSource.value = document.forms[0].selectedBillGenSource.value;
	/*End: Added by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
	document.getElementById('onscreenMessage').innerHTML = "";	
	hideScreen();
	document.forms[0].submit();
}

function gotoPreviousPage(){
		document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
		if ((null == document.forms[0].selectedZROCode.value) || (Trim(document.forms[0].selectedZROCode.value) == '')) {
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Zone MR Area Combination.</font>";
		document.forms[0].selectedZROCode.focus();
		return false;
	}
	document.forms[0].action = "mtrReadImgAudit.action";
	document.forms[0].hidAction.value = "Prev";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
	}

function fnRefreshSearch() {
	if ((null == document.forms[0].selectedZROCode.value) || (Trim(document.forms[0].selectedZROCode.value) == '')) {
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Zone MR Area Combination.</font>";
		document.forms[0].selectedZROCode.focus();
		return false;
	}
	document.forms[0].action = "mtrReadImgAudit.action";
	document.forms[0].hidAction.value = "refresh";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function gotoNextPage(){
	
 	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
 	if ((null == document.forms[0].selectedZROCode.value) || (Trim(document.forms[0].selectedZROCode.value) == '')) {
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Zone MR Area Combination.</font>";
		document.forms[0].selectedZROCode.focus();
		return false;
	}
	document.forms[0].action = "mtrReadImgAudit.action";
	document.forms[0].hidAction.value = "Next";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function onlyNumber(e){
    try {
        var unicode=e.charCode? e.charCode : e.keyCode;
        if ((unicode>=48 && unicode<=57) ||unicode==8){ 
        	return true ;
         }else{
        	 return false;
         }
        }catch (e) {
    }
}
function checkVal(e){
    try {
        var unicode=e.charCode? e.charCode : e.keyCode;
        if ((unicode>=48 && unicode<=57) || (unicode>=65 && unicode<=90) || (unicode>=97 && unicode<=122)|| unicode==8 || unicode==46 ||  unicode==32){ 
        	return true ;
         }else{
        	 return false;
         }
        
        }catch (e) {
    }
}


function fnGoToUpdateAuditStatus() {
	document.getElementById('onscreenMessage').innerHTML ='';
	var rows = (document.getElementById('searchResult').rows).length;
	//alert("rows>>  "+rows);
	var resultTable = document.getElementById('searchResult');
	var updatedField = 0;
	var msg;
	var url = "mtrReadImgAuditAJax.action?hidAction=updateAuditStatus";
	var auditedFailReason='';
	var auditedRemark='';
	var imgAuditFlag = '';
	var imgAuditStatus = '';
	var rowCount = 1;
	var dataStr="{\"JsonList\":[";
	for ( var i = 2; i < rows-1; i++) {
		var oCells = resultTable.rows.item(i).cells;
		imgAuditFlag = oCells[0].children[0].value;
		//alert("imgAuditFlag Row "+i +">"+imgAuditFlag);
		if('N'== imgAuditFlag){
			var index = oCells[14].children[0].selectedIndex; /*Modified by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
			var e = document.getElementById("imgAuditStatus"+rowCount);
			imgAuditStatus = e.options[e.selectedIndex].value;
			rowCount = rowCount + 1;
			//alert('imgAuditStatus for row '+i+' is '+imgAuditStatus);
			auditedFailReason = oCells[15].children[0].value; /*Modified by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
			auditedRemark = oCells[16].children[0].value;  /*Modified by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
			if('FAIL' == imgAuditStatus){
				if(null == auditedFailReason || ''==(Trim(auditedFailReason))){
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Provide Faliure Reason And Remarks for Audit Status Fail.</b></font>";
					window.scrollTo(0,0);
					return false;
				}
				if(null == auditedRemark || ''==(Trim(auditedRemark))){
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Provide Faliure Reason And Remarks for Audit Status Fail.</b></font>";
					window.scrollTo(0,0);
					return false;
				}
				else{
					updatedField = updatedField + 1;
					dataStr += ",{\"kno\":\""+oCells[4].children[0].value+"\",";
					dataStr += "\"imgAuditFailReason\":\""+auditedFailReason+"\",";
					dataStr += "\"imgAuditStatus\":\""+imgAuditStatus+"\",";
					dataStr += "\"billRound\":\""+document.getElementById('selectedBillRound').value+"\",";
					dataStr += "\"imgAuditRemrk\":\""+auditedRemark+"\"}";
				}
			}
			if('PASS' == imgAuditStatus){
				updatedField = updatedField + 1;
				dataStr += ",{\"kno\":\""+oCells[4].children[0].value+"\",";
				dataStr += "\"imgAuditFailReason\":\""+auditedFailReason+"\",";
				dataStr += "\"imgAuditStatus\":\""+imgAuditStatus+"\",";
				dataStr += "\"billRound\":\""+document.getElementById('selectedBillRound').value+"\",";
				dataStr += "\"imgAuditRemrk\":\""+auditedRemark+"\"}";
			}
		}
		else{
			updatedField = updatedField + 1;
			rowCount = rowCount + 1;
		}
	}
	dataStr+="]}";
	var data = dataStr.replace(dataStr.charAt(13),""); /*Modified by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
	url += "&data="+data;
	//alert("url>> "+url);
	if (updatedField < rows-3) {
		//alert('updatedField < rows-3');
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Audit All The Records From This Page To Save.<b></font>";
		window.scrollTo(0,0);	
		return false;
	}else{
		msg="Are You Sure You Want to Save the Audit Result?\nClick OK to continue else Cancel.";
		if(confirm(msg)){
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
				httpBowserObject.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								showScreen();
								document.getElementById('onscreenMessage').innerHTML = responseString;
						}else {
							showScreen();
							alert(responseString);
							fnRefreshSearch();
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}
					}
				};
				httpBowserObject.send(null);
			}
			
		}
	}
}
function fnDisableReason(count){
	var index = document.getElementById("imgAuditStatus"+count);
	var selectedVal = index.options[index.selectedIndex].value;
	var imgAuditStat = document.getElementById("trSeqNo"+count).value;
	if("PASS" == selectedVal && "Y" != imgAuditStat){
		document.getElementById('trImgAuditFailReason'+count).value = "";
		document.getElementById('trImgAuditRemrk'+count).value = "";
		document.getElementById('trImgAuditFailReason'+count).disabled = true;
	}else if("Y" != imgAuditStat && "PASS" != selectedVal){
		document.getElementById('trImgAuditFailReason'+count).disabled = false;
	}
	return true;
}
function fnDisableKNO(){
	var zroCd = Trim(document.getElementById('selectedZROCode').value);
	if(zroCd){
		document.getElementById('kno').value = "";
		document.getElementById('kno').disabled = true;
	}else{
		document.getElementById('kno').value = "";
		document.getElementById('kno').disabled = false;
	}
}

/*Start: Added by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */
function fnGetStartAndEndDate() {
	document.getElementById('selectedBillGenToDate').value = "";
	document.getElementById('selectedBillGenFromDate').value = "";
	var selectedBillRound = Trim(document.getElementById('selectedBillRound').value);
	if(document.getElementById('searchBox')){
		document.getElementById('searchBox').innerHTML = "&nbsp;";		
	}
	setStatusBarMessage("");
	if ('' != selectedBillRound) {
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "mtrReadImgAuditBillRoundAJax.action?hidAction=getBillRoundStartEndDate&selectedBillRound="
			+ selectedBillRound;
		
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
						document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Start And End Date found corresponding to the Bill Round. </b></font>";
					} else {
						var dateParts = new Array();
						dateParts =responseString.split('-');
						$(function() {
							var todaysDate = new Date();
							var billRoundStartDate = new Date(dateParts[1],dateParts[2]-1,dateParts[3]);
							var billRoundEndDate = new Date(dateParts[5],dateParts[6]-1,dateParts[7]);
							if(todaysDate.getTime() < billRoundEndDate.getTime()){
								maxDate = new Date(todaysDate.getFullYear(),
						                todaysDate.getMonth(),
						                todaysDate.getDate());
							}else{
								maxDate = billRoundEndDate;
							}
							var minDate = new Date(billRoundStartDate.getFullYear(),
									billRoundStartDate.getMonth(),
									billRoundStartDate.getDate());
							$("#selectedBillGenFromDate").datepicker("destroy");
						    $( "#selectedBillGenFromDate" ).datepicker({
									showButtonPanel: true,
									maxDate:maxDate,
									minDate: minDate,
									dateFormat: 'dd-mm-yy'
							});
							$( "#selectedBillGenFromDate" ).datepicker("refresh");
							$("#selectedBillGenToDate").datepicker("destroy");
							$( "#selectedBillGenToDate" ).datepicker({
								showButtonPanel: true,
								maxDate: maxDate,
								minDate: minDate,
								dateFormat: 'dd-mm-yy'
						});
					    $( "#selectedBillGenToDate" ).datepicker("refresh");
						});
						
						
					}
					
				}
			};
			httpBowserObject.send(null);
		}
	}
}

function fnSetEndDate(){
	document.getElementById('selectedBillGenToDate').value = "";
	var minEndDate = $('#selectedBillGenFromDate').val();
	$("#selectedBillGenToDate").datepicker("destroy");
	$( "#selectedBillGenToDate" ).datepicker({
		showButtonPanel: true,
		maxDate: maxDate,
		minDate: minEndDate,
		dateFormat: 'dd-mm-yy'
});
$( "#selectedBillGenToDate" ).datepicker("refresh");
}
/*End: Added by Madhuri On 1st Sept 2017 as per Jtrac Id- Android-388 */

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
				<s:form id="auditForm" name="auditForm" method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="searchZROCode" id="searchZROCode" />
					<s:hidden name="searchBillRound" id="searchBillRound" />
					<s:hidden name="totalRecordsUpdated" id="totalRecordsUpdated" />
					<s:hidden name="searchMeterRdrEmpId" id="searchMeterRdrEmpId" />
					<s:hidden name="searchdBillGenSource" id="searchdBillGenSource" />
					
					<s:if
						test="hidAction==null||hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Search Details</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="right" width="10%"><label>Zone</label></td>
										<td align="left" width="30%" title="Zone" id="selectedZoneTD" nowrap><s:select
											list="#session.ZoneListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZone" id="selectedZone"
											onchange="fnGetMRNo();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>
										<td align="right" width="10%"><label>MR No</label></td>
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
										<td align="right"><label>Area</label></td>
										<td align="left" title="Area" id="areaTD"><s:select
											list="#session.AreaListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedArea" id="selectedArea"
											onfocus="fnCheckZoneMRNo();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedArea" /></td>
										<td align="right"><label>ZRO Location</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
											list="#session.ZROListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZROCode" id="selectedZROCode" onchange="return fnDisableKNO();"/>
										</td>
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">KNO</td>
										<td align="left">											
											<s:textfield name="kno" id="kno" cssClass="textbox" theme = 'simple' size="20" maxlength="10" onkeypress="return onlyNumber(event);" onchange="return fnValidateKNO();"/>
											<span id="invalidKNOSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid KNO"/></span><span id="knoSpan"></span>
										</td>
										<td align="right"><label>Bill Round</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="bill Round" id="selectedBillRoundTD"><s:select name="selectedBillRound"
												id="selectedBillRound" headerKey=""
												headerValue="Please Select" list="#session.selectedBillRoundList"
												cssClass="selectbox" theme="simple" onchange="return fnGetStartAndEndDate();"/>  <!--Changes done by Madhuri on 21st July 2017 as per Jtrac Android-389 --> 
									</td>
									<td align="right"><label>Audit Status</label></td>
									<td align="left" id="selectedAuditStatusTD"><s:select name="selectedAuditStatus"
												id="selectedAuditStatus" list="#session.searchAuditStatusList"
												cssClass="selectbox" theme="simple"/>
									</td>
									</tr>
									<!--Start: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
									<tr>
										<td align="right" colspan="1"><label>Bill Source</label></td>
										<td align="left" title="Bill generation Source" id="selectedBillGenSourceTD"><s:select name="selectedBillGenSource"
												id="selectedBillGenSource" headerKey=""
												headerValue="Please Select" list="#session.searchBillGenSourceList"
												cssClass="selectbox" theme="simple" /> 
										</td>
										
										
										<td align="right"><label>Bill From Date</label></td>
										<td align="left" title="Bill generation FromDate" id="selectedBillGenFromDateTD"><s:textfield cssClass="textbox" theme="simple"
												name="selectedBillGenFromDate" id="selectedBillGenFromDate" maxlength="10" onpaste="return false;" onchange="return fnSetEndDate();"/>
										</td>
										<td align="right"><label>Bill To Date</label></td>
										<td align="left" title="Bill generation ToDate" id="selectedBillGenToDateTD"><s:textfield cssClass="textbox" theme="simple"
												name="selectedBillGenToDate" id="selectedBillGenToDate" maxlength="10"  onpaste="return false ;"/>
										</td>
										</tr>
										<tr>
										<td align="right"><label>Meter Reader ID</label></td>
										<td align="left" title="Meter Reader Name" id="selectedMtrRdrEmpIdTD"><s:select name="selectedMtrRdrEmpId"
												id="selectedMtrRdrEmpId" headerKey=""
												headerValue="Please Select" list="#session.meterReaderNameList"
												cssClass="selectbox" theme="simple"/>
										</tr>
										<!--End: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
										
										<tr>
										<td align="right" colspan="3"><input type="button"
										value="Reset All Fields" class="groovybutton"
										onclick="clearAllFields();" />
										</td>
										<td align="left" colspan="3"><s:submit
											key="    Search    " cssClass="groovybutton" theme="simple"
											id="btnSearch" /></td>
									</tr>
									<tr>
										<td align="left" colspan="4">&nbsp;</td>
										<td align="right" colspan="4"><font color="red"><sup>*</sup></font>
										marked fields are mandatory.</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
							<s:if test="meterReadImgAuditDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>
									<table id="searchResult" class="table-grid">
										<tr>
											<th align="left" colspan="4" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="12" width="80%">Page No:<s:select
												list="pageNoDropdownList" name="pageNo" id="pageNo"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" /> Showing maximum <s:select
												list="{5,10,15,20,25,30,35,40,45,50}"
												name="maxRecordPerPage" id="maxRecordPerPage"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" />records per page of total <s:property
												value="totalRecords" /> records.</th>
											<th align="right" colspan="50" width="10%"><s:if
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
											<th align="center" width="2%">SL</th>
											<th align="center" width="3%">ZONE</th>
											<th align="center" width="2%">MR</th>
											<th align="center" width="3%">AREA</th>
											<th align="center" width="5%">KNO</th>
											<th align="center" width="10%">BILL DATE</th>
											<th align="center" width="5%">BILL AMOUNT</th>
											<th align="center" width="5%">ZRO LOCATION</th>
											<th align="center" width="5%">METER READING</th>
											<th align="center" width="5%">BILL BASIS</th> 	<!--Start: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
											<th align="center" width="5%">BILL GEN SOURCE</th>
											<th align="center" width="10%">MTR RDR NAME</th> 
											<th align="center" width="5%">MTR READ STATUS</th> <!--End: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
											<th align="center" width="12%">IMAGE</th>
											<th align="center" width="3%">STATUS</th>
											<th align="center" width="5%">FAILURE REASON</th>
											<th align="center" width="5%">REMARKS</th>
										</tr>
										<s:iterator value="meterReadImgAuditDetailsList" status="row">
											<tr bgcolor="white">
												<td align="center" title="<s:property value="seqNo" />"><s:property value="seqNo" /><input
													type="hidden" name="trSeqNo<s:property value="#row.count" />"
													id="trSeqNo<s:property value="#row.count" />"
													value="<s:property value="imgAuditFlg" />" /></td>
												<td align="center"><s:property value="zone" /></td>
												<td align="center"><s:property value="mr" /></td>
												<td align="center"><s:property value="area" /></td>
												<td align="center" id="trKNO<s:property value="#row.count" />"><s:property value="kno" /><input type="hidden"  name="trKNO<s:property value="#row.count" />"
													value="<s:property value="kno" />"/></td>
												<td align="center"><s:property value="billDt" /></td>
												<td align="center"><s:property value="billAmt" /></td>
												<td align="center"><s:property value="zroLocation" /></td>
												<td align="center"><s:property value="mtrRead" /></td>
												<td align="center"><s:property value="billBasis" /></td> <!--Start: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
												<td align="center"><s:property value="billGenSource" /></td>
												<td align="center"><s:property value="mtrRdrNameEmpId" /></td>
												<td align="center"><s:property value="mtrRdStatus" /></td> <!--End: Added by Madhuri on 31st Aug 2017 as per Jtrac Android-388 --> 
												<td align="center" class="thumbnail" href="#thumb"><img src="<s:property value="imgLnk" />" width="100px" height="66px" border="0" /><span><img src="<s:property value="imgLnk" />" /></span></td>
												<td align="center"><s:if
												test="null!=imgAuditStatus"><s:property value="imgAuditStatus" />
												</s:if><s:else>
												<s:select name="imgAuditStatus%{#row.count}"
												id="imgAuditStatus%{#row.count}" headerKey=""
												headerValue="Please Select" list="#session.updtAuditStatusList"
												cssClass="selectbox" theme="simple" onchange="return fnDisableReason(%{#row.count});"/>
												</s:else>
												
												<td align="center">
												<s:if
												test="null!=imgAuditStatus"><input type="text" id="trImgAuditFailReason<s:property value="#row.count" />" name="trImgAuditFailReason<s:property value="#row.count" />"
													value="<s:property value="imgAuditFailReason" />" onkeypress="return checkVal(event)" class="textbox" style="text-align:left;" disabled="disabled"/>
												</s:if>
												<s:else>
												<input type="text" id="trImgAuditFailReason<s:property value="#row.count" />" name="trImgAuditFailReason<s:property value="#row.count" />"
													value="<s:property value="imgAuditFailReason" />" onkeypress="return checkVal(event)" class="textbox" style="text-align:left;" />
												</s:else>
												</td>
												<td align="center" id="trImgAuditRemrk<s:property value="#row.count" />">
												<s:if
												test="null!=imgAuditStatus"><input type="text"  name="trImgAuditRemrk<s:property value="#row.count" />"
													value="<s:property value="imgAuditRemrk" />" onkeypress="return checkVal(event)" class="textbox" style="text-align:left;" disabled="disabled"/>
												</s:if>
												<s:else><input type="text"  name="trImgAuditRemrk<s:property value="#row.count" />"
													value="<s:property value="imgAuditRemrk" />" onkeypress="return checkVal(event)" class="textbox" style="text-align:left;"/>
													</s:else>
												</td>
										
											</tr>
										</s:iterator>
										<tr>
											<th align="left" colspan="4" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="12" width="80%">
											<span id="totalRecordsSpan"><s:if
												test="selectedKNOList!=null">
												<s:property value="selectedKNOList.size" />
											</s:if><s:else></s:else></span><input type="button"
												value="Save & Continue" class="groovybutton"
												onclick="fnGoToUpdateAuditStatus();" id="btnSaveContinue" />
										    </th>
											<th align="right" colspan="10" width="10%"><s:if
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

<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
<script type="text/javascript">
selectedZROCodeDropdown=document.getElementById('selectedZROCodeTD').innerHTML;
var selectedZoneDropdown = document.getElementById('selectedZoneTD').innerHTML;
var selectedMRDropdown = document.getElementById('mrNoTD').innerHTML;
var selectedAreaDropdown = document.getElementById('areaTD').innerHTML;
var auditStatusDropDown = document.getElementById('selectedAuditStatusTD').innerHTML;

$("form").submit(function(e) {
	gotoSearch();
});

</script>
</html>