<!--
JSP page for Data Upload Screen Screen as per JTrac DJB-4465 and OpenProject-918.
@author Lovely (986018) (Tata Consultancy Services)
@since 20-04-2016
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Upload Screen- Revenue Management System, Delhi
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
function fnUploadDataCheck(){
	var sheet=document.getElementById('dataSheetUploaded').value;
	document.getElementById('onscreenMessage').innerHTML ="" ;
	if(document.getElementById('declaration').checked){
	if(null != sheet && ''!=Trim(sheet)){
		ValidateFile();
	}else{
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'><%=DJBConstants.FILE_UPLOAD_MSG%></font>";
	}
}else{
	alert("<%=DJBConstants.DECLARATION_ALERT_MSG%>");
}
}
var validFilesType="<%=DJBConstants.FILE_EXTENSION%>";
var activeXMsg = "<%=DJBConstants.AMR_ACTIVEX_ENABLE_MSG%>";
var FileSizeMsg = "<%=DJBConstants.FILE_SIZE_MSG%>";
try{
	var validFileSize="<%=Long.parseLong(DJBConstants.FILE_SIZE_LIMIT)%>";
}
catch(e){
}
function ValidateFile()
{
  var file = document.getElementById('dataSheetUploaded');
  var path = file.value;
  var ext=path.substring(path.lastIndexOf(".")+1,path.length).toLowerCase();
  var isValidFile = false;
  
    if (ext==validFilesType)
    {
    	validateFileSize();
    }
    else
  	{
  	  document.getElementById('onscreenMessage').innerHTML ="<font  color='red'><%=DJBConstants.FILE_ERROR_MSG%></font>" ;
 	}
}
function validateFileSize(){
	var fileSize = getSize();
	//alert('fileSize>>'+fileSize);
	//alert("validFileSize>>"+validFileSize);
	if(fileSize<=validFileSize){
		fnSubmit();
	}else
  	{
    	document.getElementById('onscreenMessage').innerHTML ="<font  color='red'>"+FileSizeMsg+"</font>" ;
 	}
}
function fnSubmit(){
	document.forms[0].action = "dataUpload.action";
	document.forms[0].hidAction.value = "processRequest";
	if (confirm('Are you sure you want to upload?')) { 
	   	 document.forms[0].submit();
	   	   	hideScreen();
	    }
	    else{
	        return false;
	    }
}	
function getSize()
{
	var isActiveXEnable = 0;
	if(typeof(window.ActiveXObject)=="undefined"){
	    var filepath = document.getElementById('dataSheetUploaded').value;
		var file = document.getElementById('dataSheetUploaded');
		return file.size;
	}else {
		try{
		if ("ActiveXObject" in window) {
	    	var myFSO = new ActiveXObject("Scripting.FileSystemObject");
			var filepath = document.getElementById('dataSheetUploaded').value;
		 	var thefile = myFSO.getFile(filepath);
		 	var size = thefile.size;
		 	isActiveXEnable = 1;
		 	return size;
		}
		}catch(e){
		}
	}
	if(isActiveXEnable == 0){
		alert(activeXMsg);
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
					id="onscreenMessage"><font color="red"><b><s:actionerror /></b><font color="green"><b><s:actionmessage /></b></font></font><%=(null == session.getAttribute
							("AJAX_MESSAGE"))
						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<fieldset><legend>Upload File</legend>
				<s:form id="uploadDataForm" name="uploadDataForm" 
				method="post" action="javascript:void(0);" onsubmit="return false" 
				theme="simple"	enctype="multipart/form-data">
					<s:hidden name="hidAction" id="hidAction" />
						<table width="98%" align="center" border="0">
						<tr>
							<td align="left" width="10%"><label><b>File To
							Be Uploaded</b></label></td>
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
							<td align="left" width="40%">Upload File <font
								color="red" size="3"><sup>*</sup></font></td>
							<td colspan="2" align="left"><s:file
								name="dataSheetUploaded" id="dataSheetUploaded" theme="simple"
								size="40" /> <img src="/DataEntryApp/images/info.png"
								title="uploaded file must be less than 2 MB" /></td>
						</tr>
						<tr>
							<td align="left" colspan="1">&nbsp;</td>
							<td align="right" colspan="5"><font color="red"><sup>*</sup></font>
							marked fields are mandatory.</td>
							</tr>
							<tr>
							<td></td>
							<td align="left" width="10%"><s:submit
								key="    Submit    " cssClass="groovybutton" theme="simple" id="submitDataUpload"
								/></td>
							<td></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr><td colspan="8"><input type="checkbox" id="declaration" name="declaration" /><font size="2" color="red">
		                     <%=DJBConstants.DECLARATION_MSG%>
		                       </font></td>
		                    <td width="10%">&nbsp;</td>
	                   </tr>
						</table>
				</s:form></fieldset>
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
$("form").submit(function(e) {
	fnUploadDataCheck();
});
</script>
</html>