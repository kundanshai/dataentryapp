<!--
 * @author Atanu Mandal 10-03-2016
 *          
--><%@ page contentType="text/html; charset=UTF-8"%>
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
<title>Audit Action Page - Revenue Management System, Delhi
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
	
<script type="text/javascript">
function fnEnableSave(rowNum){
	var finalAction = document.getElementById('selectedFinalAuditAction'+rowNum).value;
	if('' != (Trim(finalAction))){
		document.getElementById('btnSaveAction'+rowNum).disabled = false;
	}else{
		document.getElementById('btnSaveAction'+rowNum).disabled = true;
	}
}
function gotoPreviousPage(){
	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
	document.forms[0].action = "finalmSevaAudit.action";
	document.forms[0].hidAction.value = "Prev";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function fnRefreshSearch() {

	document.forms[0].action = "finalmSevaAudit.action";
	document.forms[0].hidAction.value = "refresh";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function gotoNextPage(){

	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
	document.forms[0].action = "finalmSevaAudit.action";
	document.forms[0].hidAction.value = "Next";
	document.getElementById('onscreenMessage').innerHTML = "";		
	hideScreen();
	document.forms[0].submit();
}

function gotoSaveAuditAction(kno,billRound,rowNum){
	document.getElementById('onscreenMessage').innerHTML ='';
	document.getElementById('hidAction').value="saveFinalAuditAction";
	var url = "finalmSevaAuditAJax.action?hidAction=saveFinalAuditAction";
	var finalAuditAction = document.getElementById('selectedFinalAuditAction'+rowNum).value;
	var dataStr="{\"JsonList\":[";
	dataStr += "{\"kno\":\""+kno+"\",";
	dataStr += "\"billRound\":\""+billRound+"\",";
	dataStr += "\"finalAuditAction\":\""+finalAuditAction+"\"}";
	dataStr+="]}";
	url += "&data=";
	url += dataStr;
	msg="Are You Sure You Want to do the selected operation for KNO : "+kno+" ?\nClick OK to continue else Cancel.";

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
					if (null == responseString|| responseString.indexOf("ERROR:") > -1) {
						    showScreen();
							document.getElementById('onscreenMessage').innerHTML = responseString;
					}else {
						showScreen();
						document.getElementById('tdSaveFinalAction'+rowNum).innerHTML = "";
						document.getElementById('selectedFinalAuditAction'+rowNum).value = finalAuditAction;
						document.getElementById('selectedFinalAuditAction'+rowNum).disabled = true;
						document.getElementById('trNo'+rowNum).style.backgroundColor="#34A90A";
						document.getElementById('onscreenMessage').innerHTML = responseString+" <font color='#33CC33'><b>For KNO "+kno+".</b></font>";
					}
				}
			};
			
			httpBowserObject.send(null);
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
				
				<s:form id="auditForm" name="auditForm" method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:if
						test="hidAction==null||hidAction==\"search\"||hidAction==\"Next\"||hidAction==\"Prev\"||hidAction==\"refresh\"">
						<table width="100%" border="0">
							<s:if test="auditActionRecordsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
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
											<th align="center" colspan="3" width="80%">Page No:<s:select
												list="pageNoDropdownList" name="pageNo" id="pageNo"
												cssClass="smalldropdown" theme="simple"
												onchange="fnRefreshSearch();" /> Showing maximum <s:select
												list="{20,30,40,50,60,70,80,90,100}"
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
											<th align="center" width="15%">SUGGESTED ACTION BY THE AUDITOR</th>
											<th align="center" width="15%">REMARKS AS PROVIDED BY THE AUDITOR</th>
											<th align="center" width="10%">FINAL ACTION</th>
											<th align="center" width="2%"></th>
										</tr>
										<s:iterator value="auditActionRecordsList" status="row">
											<tr bgcolor="white" id="trNo<s:property value="#row.count" />" onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'">
												<td align="center" title="<s:property value="seqNo" />"><s:property value="seqNo" /></td>
												<td align="center"><s:property value="kno" /></td>
												<td align="center"><s:property value="sugstdAuditAction" /></td>
												<td align="center"><s:property value="nonSatsfctryReadngReasn" /></td>
												<td align="center" id="finalAuditActionTD%{#row.count}"><s:if
												test="\"NULL\"==finalAuditAction"><s:select name="selectedFinalAuditAction%{#row.count}"
												id="selectedFinalAuditAction%{#row.count}" headerKey=""
												headerValue="Please Select" list="#session.finalAuditActionList"
												cssClass="selectbox-long" theme="simple" onchange="return fnEnableSave(%{#row.count});"/>
												</s:if><s:else>
												<s:property value="finalAuditAction" />
												</s:else></td>
												<td align="center" id="tdSaveFinalAction<s:property value="#row.count" />"><s:if test="\"INCOMPLETE\"==finalAuditStatus"><input type="button" id="btnSaveAction<s:property value="#row.count"/>" name="btnSaveAction<s:property value="#row.count"/>" class="groovybutton" value=" SAVE " disabled="disabled" onclick="javascript:gotoSaveAuditAction('<s:property value="kno" />','<s:property value="billRound" />','<s:property value="#row.count" />');"/></s:if></td>
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
											<th align="center" colspan="3" width="80%">
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
												<td align="center" colspan="6" /><font color="red">No
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
</html>