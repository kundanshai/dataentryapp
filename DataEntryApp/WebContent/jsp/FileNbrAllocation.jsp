
<!--
JSP page for File number allocation Screen as per jtrac DJB-4442 and OpenProject-CODE DEVELOPMENT #867
@author Lovely (Tata Consultancy Services)
@since 12-04-2016
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
<title>File Number Allocation Screen - Revenue Management
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
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script language=javascript>
    function validateEmpty(){
 		var minRange=0;
 		minRange=Trim(document.getElementById('minRange').value);
		var maxRange=0;
		maxRange=Trim(document.getElementById('maxRange').value);
		var zroLoc=Trim(document.forms[0].selectedZROLocation.value);
    	if(minRange=='' || maxRange=='' || zroLoc == '')
		{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
		}
		else 
		btSubmit();
    }
 	function btSubmit(){
 		document.forms[0].action = "fileNbrAllocation.action";
 		document.forms[0].hidAction.value = "submit";
 		document.getElementById('onscreenMessage').innerHTML = "";	
 		if (confirm('Do You want to submit?')) { 
 		   	 document.forms[0].submit();
 		   	   	hideScreen();
 		    }
 		    else{
 		        return false;
 		    }
 	}
 	function isNumberKey(evt)
    {
       var charCode = (evt.which) ? evt.which : evt.keyCode;
         if(charCode!=8 && charCode!=127 &&(charCode<48 || charCode>57))
       {
    	   document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.NUMBER_ALLOWED_MSG%></font>";
          return false;
       }
       return true;
    }
 	function fnGoToRangeValidator(){
 		document.getElementById('onscreenMessage').innerHTML="";
 		var minRange=0;
 		minRange=Trim(document.getElementById('minRange').value);
		var maxRange=0;
		maxRange=Trim(document.getElementById('maxRange').value);
    	if(Trim(minRange)=="" || Trim(maxRange)=="")
		{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
		}
    	else
    	{
			if(minRange==0 || maxRange==0)
			{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.BOOKLET_MSG%></font>";
				window.scrollTo(0,0);	
				document.getElementById("btn").disabled = true;
				return ;
			}
			if(minRange>= maxRange)
			{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.BOOKLET_RANGE_MSG%></font>";
				window.scrollTo(0,0);	
				document.getElementById("btn").disabled = true;
				return ;
			}
			else
			{
				document.getElementById("btn").disabled = false;;
			}
			fnRangeValidator();
    	}
	}
	function fnRangeValidator(){
		var minRange=0;
 		minRange=Trim(document.getElementById('minRange').value);
		var maxRange=0;
		maxRange=Trim(document.getElementById('maxRange').value);
		var url = "fileNbrAllocationAJax.action?hidAction=rangeValidate&minRange="+minRange;
		url+="&maxRange="+maxRange;
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
					
					showScreen();
					if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('onscreenMessage').innerHTML=responseString;
						document.getElementById("btn").disabled = true;
					}
					else
					{
						document.getElementById("btn").disabled = false;	
					}
				}
			};
			httpBowserObject.send(null);
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
							("AJAX_MESSAGE"))						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form id="allocationForm" name="allocationForm" method="post"
					action="javascript:void(0);" theme="simple"
					onsubmit="return false;">

					<s:hidden name="hidAction" id="hidAction" />
					<s:if test="hidAction==null||hidAction==\"submit\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>File Allocation Parameter
								</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="left" width="10%"><label>ZRO Location</label><font
											color="red"><sup>*</sup></font></td>
										<td align="left" width="30%" title="ZRO Location"
											id="selectedZROLocation"><s:select
											list="#session.ZROList" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZROLocation"
											id="selectedZROLocation" onPaste="return false" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>

										<td></td>
										<td align="right" width="10%"><label>File Range
										From</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="File Range From" style="width: 323px">
										<input type="text" class="textbox" ondragstart="return false;"
											ondrop="return false;" name="minRange" id="minRange"
											onkeypress="return isNumberKey(event)"
											onblur="fnGoToRangeValidator()" required
											maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>"
											onPaste="return false;" /></td>
									   </tr>
									     <tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td align="right" width="20%" style="width: 172px"><label>File
										Range To</label><font color="red"><sup>*</sup></font></td>
										<td align="left" width="15%" title="File Range To"
											style="width: 323px"><input type="text" class="textbox"
											ondragstart="return false;" ondrop="return false;"
											name="maxRange" id="maxRange"
											onkeypress="return isNumberKey(event)"
											onblur="fnGoToRangeValidator()" required
											maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>"
											onPaste="return false;" /></td>
								</tr>
								</table>
								<table align="center" border="0">
									<tr>
										<td align="left">&nbsp;</td>
										<td align="right"><input type="button"
											value="     Reset    " class="groovybutton"
											onclick="clearAllFields();" /></td>
										<td align="left">&nbsp;</td>
										<td align="left"><input type="button"
											value="     Submit   " class="groovybutton" id="btn"
											onclick=" validateEmpty()" /></td>
										<td></td>
										<td align="right" colspan="20"><font color="red"><sup>*</sup></font>
										marked fields are mandatory.</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
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
<script type="text/javascript">
        function clearAllFields(){
            document.location.href="fileNbrAllocation.action";
   		}
        $('#fileNo').bind("cut copy paste",function(e) {
			document.forms[0].fileNo.focus();
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.NUMBER_ALLOWED_MSG%></font>";
		     e.preventDefault();
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