<!--
 * @author Atanu Mandal 10-03-2016, open project id 1202,jTrac : DJB-4464
 * @history : Atanu on 05-07-2016 modified the logic to read the cell upto specified column count as per open project 1202,jTrac : DJB-4464.         
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
<title>MRD File Upload - Revenue Management System, Delhi
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
	var content=document.getElementById('dataSheetUploaded').contenttype;
	document.getElementById('onscreenMessage').innerHTML ="" ;
	if(null != sheet && ''!=Trim(sheet)){
		ValidateFile();
	}else{
		
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Upload valid file</font>";
	}
}
var validFilesType="<%=DJBConstants.AMR_FILE_EXTENSION%>";
var activeXMsg = "<%=DJBConstants.AMR_ACTIVEX_ENABLE_MSG%>";
var amrFileSizeMsg = "<%=DJBConstants.AMR_FILE_SIZE_MSG%>";
try{
	var validFileSize="<%=Long.parseLong(DJBConstants.AMR_FILE_SIZE)%>";
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
    	document.getElementById('onscreenMessage').innerHTML ="<font  color='red'>Please upload a file with extension .xls and retry.</font>" ;
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
    	document.getElementById('onscreenMessage').innerHTML ="<font  color='red'>"+amrFileSizeMsg+"</font>" ;
 	}
	
	
}
function fnSubmit(){
	hideScreen();
	document.forms[0].action = "mrdFileUpload.action";
	document.forms[0].hidAction.value = "processRequest";
	document.forms[0].submit();
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
<style type="text/css">
       
        .message{
            max-height: 80px;
            overflow-y: scroll;
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
				<div class="message" title="Server Message" ><font size="2"><span
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font><%=(null == session.getAttribute("AJAX_MESSAGE"))
						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				
				<s:form id="uploadDataForm" name="uploadDataForm" method="post" action="javascript:void(0);" onsubmit="return false"  theme="simple"	enctype="multipart/form-data">
					<s:hidden name="hidAction" id="hidAction" />
						<table width="100%" border="0">
							<tr>
								<td align="left" width="10%"><label><b>File To Be Uploaded</b></label></td>
								<td align="left" id="search-error-msg"><font color="red"><b></b></font></td>
							</tr>
							<tr>
								<td align="right" width="40%">Upload The File <font
								color="red" size="3"><sup>*</sup></font></td>
								<td colspan="2" align="left"><s:file
									name="dataSheetUploaded" id="dataSheetUploaded" theme="simple"
									size="40" /></td>
	
							</tr>
							<tr>
								<td align="left" colspan="1">&nbsp;</td>
								<td align="right" colspan="5"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
							</tr>
							<tr>
							<td></td>
							<td align="left" width="10%"><s:submit
								key="    Submit    " cssClass="groovybutton" theme="simple" id="SubmitDataUpload"
								/></td>
							<td></td>

						</tr>
						</table>
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
$("form").submit(function(e) {
	fnUploadDataCheck();
});
</script>
</html>