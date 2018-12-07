<!--
*@author  Atanu Mandal 10-03-2016
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consumption Variation Audit Page - Revenue Management System, Delhi
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
$(function() {
	var todaysDate = new Date();
    var maxDate = new Date(todaysDate.getFullYear(),
                       todaysDate.getMonth(),
                       todaysDate.getDate());
		$( "#selectedLastAuditedBeforeDate" ).datepicker({
			showButtonPanel: true,
			maxDate: maxDate,
			dateFormat: 'dd-mm-yy'
		});
});
</script>
<script language=javascript>

function clearAllFields(){
	 document.getElementById("auditForm").reset();
	 document.getElementById('selectedZROCodeTD').innerHTML = selectedZROCodeDropdown;
	 document.getElementById('lastSavedResultTD').innerHTML = selectedLastSavedResultDropdown;
	 document.getElementById('selectedBillRoundTD').innerHTML = selectedBillRoundDropdown;
	 document.getElementById('selectedVarAnualAvgConsumptionTD').innerHTML = selectedVarAnualAvgConsumptionDropdown;
	 document.getElementById('selectedVarPrevReadingTD').innerHTML = selectedVarPrevReadingDropdown;
	 document.getElementById('selectedSavedResult').value="";
	 document.getElementById('kno').value="";
	 document.getElementById('selectedZROCode').value="";
	 document.getElementById('selectedVarAnualAvgConsumption').value="";
	 document.getElementById('selectedVarPrevReading').value="";
	 document.getElementById('selectedAuditStatusTD').innerHTML = auditStatusDropDown;
	 document.getElementById('selectedSavedResult').disabled = false;
	 document.getElementById('selectedZROCode').disabled = false;
	 document.getElementById('selectedVarAnualAvgConsumption').disabled = false;
	 document.getElementById('selectedVarPrevReading').disabled = false;
	 //document.getElementById('selectedLastAuditedBeforeDate').disabled = false;
	 document.getElementById('selectedLastAuditedBeforeDate').disabled = false;
	 //document.getElementById('lastAuditedBeforeTD').innerHTML = lastAuditedBeforeTDHtml;
	 document.getElementById('selectedLastAuditedBeforeDate').value = "";
	 //document.getElementById("selectedConsumptionVariationAuditStatus").disabled = false;
	 document.getElementById('kno').disabled = false;
	 //document.getElementById('knoSpan').innerHTML ="";
	 document.getElementById('onscreenMessage').value = "";
	 document.getElementById('onscreenMessage').innerHTML = "";
	 document.getElementById('searchBox').innerHTML="";

}

function gotoSearch(){
	if ((Trim(document.forms[0].selectedZROCode.value) == '') && Trim(document.forms[0].kno.value) == '') {
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Location or Enter a valid KNO.</font>";
		document.forms[0].selectedZROCode.focus();
		return false;
	}
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
	document.forms[0].action = "consumptionVariationAudit.action";
	document.forms[0].hidAction.value = "search";
	document.forms[0].searchZROCode.value = document.forms[0].selectedZROCode.value;
	document.getElementById('onscreenMessage').innerHTML = "";
	hideScreen();
	document.forms[0].submit();
}

function gotoPreviousPage(){
	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
	document.forms[0].action = "consumptionVariationAudit.action";
	document.forms[0].hidAction.value = "Prev";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function fnRefreshSearch() {

	document.forms[0].action = "consumptionVariationAudit.action";
	document.forms[0].hidAction.value = "refresh";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function gotoNextPage(){

	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
	document.forms[0].action = "consumptionVariationAudit.action";
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

function fnValidateKNO(){ 
	var kno=Trim(document.getElementById('kno').value);
	if(kno.length==10){
		if(isNaN(kno)){
			document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be a 10 digits Numeric Value.</font>";		
			document.getElementById('kno').value = '';
			document.getElementById('kno').focus();
			return false;
		}
		else{
			document.getElementById('selectedSavedResult').value = "";
			document.getElementById('selectedZROCode').value = "";
			document.getElementById('selectedVarAnualAvgConsumption').value = "";
			document.getElementById('selectedVarPrevReading').value = "";
			document.getElementById('selectedLastAuditedBeforeDate').value = "";
			document.getElementById("selectedConsumptionVariationAuditStatus").value = "ALL";
			document.getElementById('selectedSavedResult').disabled = true;
			document.getElementById('selectedZROCode').disabled = true;
			document.getElementById('selectedVarAnualAvgConsumption').disabled = true;
			document.getElementById('selectedVarPrevReading').disabled = true;
			document.getElementById('selectedLastAuditedBeforeDate').disabled = true;
			document.getElementById("selectedConsumptionVariationAuditStatus").disabled = true;
		}
		
	}
	else{
		if(kno.length==0){
			document.getElementById('knoSpan').innerHTML ="";
		//	document.getElementById('selectedBillRoundTD').innerHTML = selectedBillRoundDropdown;
		}else{
			document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be of 10 digits.</font>";		
			document.getElementById('kno').focus();
			document.getElementById('selectedSavedResult').value = "";
			document.getElementById('selectedZROCode').value = "";
			document.getElementById('selectedVarAnualAvgConsumption').value = "";
			document.getElementById('selectedVarPrevReading').value = "";
			document.getElementById('selectedLastAuditedBeforeDate').value = "";
			document.getElementById("selectedConsumptionVariationAuditStatus").value = "PENDING";
			document.getElementById('selectedSavedResult').disabled = false;
			document.getElementById('selectedZROCode').disabled = false;
			document.getElementById('selectedVarAnualAvgConsumption').disabled = false;
			document.getElementById('selectedVarPrevReading').disabled = false;
			document.getElementById('selectedLastAuditedBeforeDate').disabled = false;
			document.getElementById("selectedConsumptionVariationAuditStatus").disabled = false;
			//document.getElementById('selectedBillRoundTD').innerHTML = selectedBillRoundDropdown;
			return false;
		}
		}
	return true; 
}
function fnDisableKNO(){
	var zroCd = document.getElementById('selectedZROCode').value;
	if(zroCd){
		document.getElementById('kno').value = "";
		document.getElementById('kno').disabled = true;
	}else{
		document.getElementById('kno').value = "";
		document.getElementById('kno').disabled = false;
	}
}
function gotoKNODetailAuditScreen(slNo,kno,mtrNo,lastAuditDate,lastAuditStatus,lastAuditRmrk,billRound,siteVistRequrd,assignTo,assignToFlg,rowNum){
		popupKNODetailAuditScreen(slNo,kno,mtrNo,lastAuditDate,lastAuditStatus,lastAuditRmrk,billRound,siteVistRequrd,assignTo,assignToFlg,rowNum);
	}
function gotoAddAuditFindings(kno,billRound,rowNum){
	//alert('Adding Audit Findings:kno '+kno+' :billRound: '+billRound+' :rowNum: '+rowNum);
	popupAddAuditFindingScreen(kno,billRound,rowNum);
}

function setSearchCriteria(){
	var index = document.getElementById("selectedSavedResult");
	var selectedSavedResultValue = index.options[index.selectedIndex].value;
	if(selectedSavedResultValue){
		document.getElementById('kno').value = "";
		document.getElementById('selectedZROCode').value = "";
		document.getElementById('selectedVarAnualAvgConsumption').value = "";
		document.getElementById('selectedVarPrevReading').value = "";
		document.getElementById('selectedLastAuditedBeforeDate').value = "";
		document.getElementById("selectedConsumptionVariationAuditStatus").value = "";
		
		document.getElementById('kno').disabled = true;
		document.getElementById('searchBillRound').disabled = true;
		document.getElementById('selectedZROCode').disabled = true;
		document.getElementById('selectedVarAnualAvgConsumption').disabled = true;
		document.getElementById('selectedVarPrevReading').disabled = true;
		document.getElementById('selectedLastAuditedBeforeDate').disabled = true;
		document.getElementById("selectedConsumptionVariationAuditStatus").disabled = true;
		//document.getElementById('searchBox').innerHTML="";
		
	}else{
		document.getElementById('kno').value = "";
		document.getElementById('selectedZROCode').value = "";
		document.getElementById('selectedVarAnualAvgConsumption').value = "";
		document.getElementById('selectedVarPrevReading').value = "";
		document.getElementById('selectedLastAuditedBeforeDate').value = "";
		document.getElementById("selectedConsumptionVariationAuditStatus").value = "PENDING";
		document.getElementById('kno').disabled = false;
		document.getElementById('selectedZROCode').disabled = false;
		document.getElementById('selectedVarAnualAvgConsumption').disabled = false;
		document.getElementById('selectedVarPrevReading').disabled = false;
		document.getElementById('selectedLastAuditedBeforeDate').disabled = false;
		document.getElementById("selectedConsumptionVariationAuditStatus").disabled = false;
		document.getElementById('searchBox').innerHTML="";
		
	}
	var keyValueWithBracket = selectedSavedResultValue.substring(2, selectedSavedResultValue.length-2);
	var keyValuePair = new Array();
	keyValuePair = keyValueWithBracket.split("][");
	for (a in keyValuePair ) {
		var keyValue = new Array();
		keyValue = keyValuePair[a].split(":");
		var key = keyValue[0];
		var value = keyValue[1];
		if("KNO" == key){
			document.getElementById('kno').value = value;
		}
		if("BILL_ROUND" == key){
			document.getElementById('searchBillRound').value = value;
		}
		if("ZRO" == key){
			document.getElementById('selectedZROCode').value = value;
		}
		if("AVG_CONSUMPTION" == key){
			document.getElementById('selectedVarAnualAvgConsumption').value = value;
		}
		if("VARIATION_PREVIOUS_ROUND" == key){
			document.getElementById('selectedVarPrevReading').value = value;
		}
		if("LAST_AUDITED" == key){
			document.getElementById('selectedLastAuditedBeforeDate').value = value;
		}
		if("AUDIT_STATUS" == key){
			document.getElementById("selectedConsumptionVariationAuditStatus").value = value;
		}
	}
	
}
</script>
</head>
<body>
<%@include file="../jsp/KNODetailAudit.html"%>
<%@include file="../jsp/UpdateAuditFindings.html"%>
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
					<s:hidden name="totalRecordsUpdated" id="totalRecordsUpdated" />
					<s:hidden name="selectedBillRound" id="selectedBillRound" />
					<s:if
						test="hidAction==null||hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Search Details</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="left" width="10%"><label>Last Saved Results</label></td>
										<td align="left" width="30%" title="Saved Result" id="lastSavedResultTD" nowrap><s:select
											list="#session.savedResultList" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedSavedResult" id="selectedSavedResult"
											onchange="setSearchCriteria()" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>
										<td align="right">KNO</td>
										<td align="left">											
											<s:textfield name="kno" id="kno" cssClass="textbox" theme = 'simple' size="20" maxlength="10" onkeypress="return onlyNumber(event);" onchange="return fnValidateKNO();"/>
											<span id="invalidKNOSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid KNO"/></span><span id="knoSpan"></span>
										</td>
										
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td align="left"><label>ZRO Location</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
											list="#session.ZROListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZROCode" id="selectedZROCode" onchange="return fnDisableKNO();"/>
										</td>
										<td align="left">&nbsp;</td>
										<td align="right"><label>Bill Round</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="Bill Round" id="selectedBillRoundTD"><s:select name="searchBillRound"
												id="searchBillRound" headerKey=""
												headerValue="Please Select" list="#session.selectedBillRoundList"
												cssClass="selectbox" theme="simple"  value="#session.selectedBillRoundList"  disabled="true"/>
									</td>
									</tr>
									<tr>
										<td align="left" width="20%"><label>% Variation from  Annual Average Consumption</label></td>
										<td align="left" id="selectedVarAnualAvgConsumptionTD"><s:select name="selectedVarAnualAvgConsumption"
												id="selectedVarAnualAvgConsumption" headerKey=""
												headerValue="Please Select" list="#session.perVarAnualAvgConsumptionList"
												cssClass="selectbox" theme="simple"  />
										</td>
										<td align="left">&nbsp;</td>
										<td align="right"><label>% Variation from Previous Reading</label></td>
										<td align="left" id="selectedVarPrevReadingTD"><s:select name="selectedVarPrevReading"
												id="selectedVarPrevReading" headerKey=""
												headerValue="Please Select" list="#session.perVarPreviousRoundList"
												cssClass="selectbox" theme="simple" />
									</td>
									</tr>
									<tr>
										<td align="left" width="20%"><label>Last Audited Before</label></td>
										<td align="left" id="lastAuditedBeforeTD"><s:textfield cssClass="textbox" theme="simple"
												name="selectedLastAuditedBeforeDate" id="selectedLastAuditedBeforeDate" maxlength="10" onpaste="return false;"/>
										</td>
										<td align="left">&nbsp;</td>
										<td align="right"><label>Audit Status</label></td>
										<td align="left" id="selectedAuditStatusTD"><s:select name="selectedConsumptionVariationAuditStatus"
												id="selectedConsumptionVariationAuditStatus" list="#session.consumptionVariationAuditStatusList"
												cssClass="selectbox" theme="simple"/>
									</td>
									</tr>
									<tr>
									<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td align="center" colspan="3"><input type="button"
										value="Reset All Fields" class="groovybutton"
										onclick="clearAllFields();" />
										</td>
										<td align="left" colspan="3"><s:submit
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
							<s:if test="meterReadImgAuditDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>
									<table id="searchResult" class="table-grid">
										<tr>
											<th align="left" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="9" width="80%">Page No:<s:select
												list="pageNoDropdownList" name="pageNo" id="pageNo"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" /> Showing maximum <s:select
												list="{5,10,15,20,25,30,35,40,45,50}"
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
											<th align="center" width="2%">SL</th>
											<th align="center" width="5%">KNO</th>
											<th align="center" width="5%">BILL ROUND</th>
											<th align="center" width="5%">METER NUMBER</th>
											<th align="center" width="10%">% VARIATION FROM ANNUAL AVERAGE CONSUMPTION</th>
											<th align="center" width="10%">% VARIATION FROM PREVIOUS READ</th>
											<th align="center" width="10%">ANNUAL AVERAGE CONSUMPTION</th>
											<th align="center" width="5%">LAST METER READING</th>
											<th align="center" width="5%">CURRENT METER READING</th>
											<th align="center" width="10%">VARIATION FROM PREVIOUS METER READING</th>
											<th align="center" width="5%">LAST AUDIT DATE</th>
											<th align="center" width="5%">ADD AUDIT FINDINGS</th>
										</tr>
										<s:iterator value="meterReadImgAuditDetailsList" status="row">
											<tr bgcolor="white" id="trNo<s:property value="#row.count" />" onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'">
												<td align="center" title="<s:property value="seqNo" />"><s:property value="seqNo" /></td>
												<td title="Click to Audit Consumption Varition Data" align="center" id="trKNO<s:property value="#row.count" />"><a href="#"
												onclick="javascript:gotoKNODetailAuditScreen('<s:property value="seqNo" />','<s:property value="kno" />','<s:property value="mtrNo" />','<s:property value="lastAuditDate" />','<s:property value="lastAuditStatus" />','<s:property value="lastAuditRmrk" />','<s:property value="billRound" />','<s:property value="siteVistRequrd" />','<s:property value="assignTo" />','<s:property value="assignToFlg" />','<s:property value="#row.count" />');"><s:property value="kno" /></a></td> 
												<td align="center"><s:property value="billRound" /></td>
												<td align="center"><s:property value="mtrNo" /></td>
												<td align="center"><s:property value="perVariationAvgCnsumptn" /></td>
												<td align="center"><s:property value="perVariationPrevusReadng" /></td>
												<td align="center"><s:property value="annualAvgConsumption" /></td>
												<td align="center"><s:property value="lastMtrReadng" /></td>
												<td align="center"><s:property value="currMtrReadng" /></td>
												<td align="center"><s:property value="variationPrevusReadng" /></td>
												<td align="center" id="trAuditDate<s:property value="#row.count" />"><s:property value="lastAuditDate" /></td>
												<td align="center" id="trAddFindings<s:property value="#row.count" />"><s:if test="\"IN PROGRESS\"==lastAuditStatus"><input type="button" name="btnAddFindings" id="btnAddFindings" value="ADD FINDINGS" onclick="javascript:gotoAddAuditFindings('<s:property value="kno" />','<s:property value="billRound" />','<s:property value="#row.count" />');"/></s:if></td>
											</tr>
										</s:iterator>
										<tr>
											<th align="left" colspan="2" width="10%"><s:if
												test="pageNo > 1">
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													onclick="javascript:gotoPreviousPage();" />
											</s:if><s:else>
												<input type="button" name="btnPrevious" id="btnPrevious"
													value="<< Previous " class="groovybutton"
													disabled="disabled" />
											</s:else></th>
											<th align="center" colspan="9" width="80%">
											&nbsp;
										    </th>
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
				</s:form>
						
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
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
<script type="text/javascript">
var selectedZROCodeDropdown=document.getElementById('selectedZROCodeTD').innerHTML;
var selectedLastSavedResultDropdown = document.getElementById('lastSavedResultTD').innerHTML;
var selectedBillRoundDropdown = document.getElementById('selectedBillRoundTD').innerHTML;
var selectedVarAnualAvgConsumptionDropdown = document.getElementById('selectedVarAnualAvgConsumptionTD').innerHTML;
var selectedVarPrevReadingDropdown = document.getElementById('selectedVarPrevReadingTD').innerHTML;
document.getElementById('selectedBillRound').value = document.getElementById('searchBillRound').value;
var auditStatusDropDown = document.getElementById('selectedAuditStatusTD').innerHTML;
var lastAuditedBeforeTDHtml =  document.getElementById('lastAuditedBeforeTD').innerHTML;

$("form").submit(function(e) {
	gotoSearch();
});

</script>
</html>