<!--

JSP page for Data Migration Reports Screen as per JTrac DJB-4425 and OpenProject-1217.

@author Arnab Nandy (1227971) (Tata Consultancy Services)
@since 01-06-2016

 -->

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
<title>Data Migration Reports Screen - Revenue Management System, Delhi Jal
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


<script type="text/javascript">

function fnGenerateReport(){
	document.getElementById('onscreenMessage').innerHTML ="" ;
	var selectedZROCode=document.getElementById('selectedZROCode').value;
	var selectedFileName=document.getElementById('selectedFileName').value;
	if(Trim(selectedZROCode)=='' || Trim(selectedFileName)==''){
		document.getElementById('onscreenMessage').innerHTML ="<font color='red'>Mandatory fields are missing.</font>" ;
	}else{
		if(Trim(selectedFileName)!='' && Trim(selectedFileName)!=''){
			showDevChBill(selectedZROCode,selectedFileName);
		}
	}
}

function showDevChBill(selectedZROCode,selectedFileName){
	var urlpart1= "<%=DJBConstants.DATA_MIGRATION_REPORT_URL_PART1%>";
	var urlpart2= "<%=DJBConstants.DATA_MIGRATION_REPORT_URL_PART2%>";
	window.open(''+urlpart1+selectedZROCode+''+urlpart2+selectedFileName,'Migration');
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
				<div class="search-option" title="Server Message"><font
					size="2"><span id="onscreenMessage"><s:property
					value="#session.SERVER_MSG" /><font color="green"><b>&nbsp;</b></font></span></font></div>

				<div id="GenerateReport">
				<fieldset><legend>Generate Reports</legend> <s:form
					action="javascript:void(0);" method="post"
					enctype="multipart/form-data" theme="simple"
					onsubmit="return false">
					<s:hidden name="hidAction" id="hidAction" />
					<table width="98%" align="center" border="0">
						<tr>
							<td align="left" width="10%"><label><b>Generate Data 
							Migration Reports</b></label></td>

							<td align="left" id="search-error-msg"><font color="red"><b></b></font></td>
						<tr>

							<td align="left" colspan="1">&nbsp;</td>
						<tr>
							<td align="left" width="40%"><label>ZRO Location</label><font color="red"><sup>*</sup></font></td>
							<td align="left" title="ZRO Code" id="selectedZROCodeTD"><s:select
								list="#session.ZROListMap" headerKey=""
								headerValue="Please Select" cssClass="selectbox-long"
								theme="simple" name="selectedZROCode" id="selectedZROCode" />
							</td>
						</tr>
						<tr>
							<td align="left" width="40%"><label>File Name</label><font color="red"><sup>*</sup></font></td>
							<td align="left" title="File Name" id="selectedFileNameTD"><s:select
								list="#session.FileListMap" headerKey=""
								headerValue="Please Select" cssClass="selectbox-long"
								theme="simple" name="selectedFileName" id="selectedFileName" />
							</td>
						</tr>

						<tr>
							<td align="left" colspan="1">&nbsp;</td>
							<td align="right" colspan="5"><font color="red"><sup>*</sup></font>
							marked fields are mandatory.</td>
						</tr>
						<tr>
							<td></td>
							<td align="left" width="10%"><s:submit value="Generate"
								align="center" id="SubmitData"
								onclick="fnGenerateReport();" /></td>
							<td align="left" width="5%"><s:submit value="     Reset    "
								align="left" id="reset"
								onclick="clearAllFields();" /></td>
							<td></td>

						</tr>

					</table>

				</s:form></fieldset>
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
<script type="text/javascript">	
	function clearAllFields(){
		$("select").each(function() { this.selectedIndex = 0 });
	}
</script>
<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>">
	</script>
</body>

<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
</html>