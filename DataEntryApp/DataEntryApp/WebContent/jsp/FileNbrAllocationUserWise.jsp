
<!--
JSP page for File number allocation User Wise Screen as per jTrac DJB-4571,Openproject- #1492 
@author Lovely (Tata Consultancy Services) 
@since 22-09-2016
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
<title>File Number Allocation Screen User Wise - Revenue
Management System, Delhi Jal Board</title>
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
 	function fnUserAllocationValidatSubmit(){
 		var minFileRange=0;
 		minFileRange=Trim(document.getElementById('minFileRange').value);
		var maxFileRange=0;
		maxFileRange=Trim(document.getElementById('maxFileRange').value);
		var userIdTag=Trim(document.getElementById('userIdTag').value);
    	if(minFileRange=='' || maxFileRange=='' || userIdTag == '')
		{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
		}
		else 
          fnSubmit();
    }
 	 function fnSubmit(){
 		   var userIdTag=Trim(document.getElementById('userIdTag').value);
 			var minFileRange=0;
 	 		minFileRange=Trim(document.getElementById('minFileRange').value);
 			var maxFileRange=0;
 			maxFileRange=Trim(document.getElementById('maxFileRange').value);
 		    if(confirm('Do You want to submit?')){
 		    var url = "fileNbrAllocationUserWiseAJax.action?hidAction=userAllocationSubmit&minFileRange="+minFileRange;
 		     url+="&maxFileRange="+maxFileRange+"&userIdTag="+userIdTag;
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
 				httpBowserObject.setRequestHeader("Content-type",
 						"application/x-www-form-urlencoded");
 				httpBowserObject.setRequestHeader("Connection", "close");
 				httpBowserObject.onreadystatechange = function() {
 					if (httpBowserObject.readyState == 4) {
 						var responseString = httpBowserObject.responseText;
 						showScreen(responseString);
 						if (null == responseString
 									|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
 						}
 						else
 						{
							document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
 						}
 					}
 				};
 				httpBowserObject.send(null);
 			}
 		}
 		else{
             return false;
 	 		}
	    
 	}
 	function fnSearchFileNbrList(){
 		var userIdUpdate=0;
 		userIdUpdate=Trim(document.getElementById('userIdUpdate').value);
      if(userIdUpdate=='' || userIdUpdate==null)
		{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
		}
      else{
 		document.forms[0].action = "fileNbrAllocationUserWise.action";
 		document.forms[0].hidAction.value = "searchFileNbrList";
 		document.getElementById('onscreenMessage').innerHTML = "";	
 	 	document.forms[0].submit();
 	 	 }
 	}
 	function fnUpdateFileNbrList(rowCount,selectedZROLocation){
 		    var minFileRange=0;
		    minFileRange=Trim(document.getElementById('fetchedMinFileRange'+rowCount).value);
			var maxFileRange=0;
			maxFileRange=Trim(document.getElementById('fetchedMaxFileRange'+rowCount).value);
			var userIdUpdate=0;
			userIdUpdate=Trim(document.getElementById('userIdUpdate').value);
			if(minFileRange=='' || maxFileRange=='')
			{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
			}
			else
			{
				if(minFileRange==0 || maxFileRange==0)
				{
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.BOOKLET_MSG%></font>";
					window.scrollTo(0,0);	
					document.getElementById("btn").disabled = true;
					return ;
				}
				if(minFileRange> maxFileRange)
				{
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.FILE_RANGE_MSG%></font>";
					window.scrollTo(0,0);	
					return ;
				}
				else{
 		if(confirm('Are you sure to update ?')){
			//hideScreen();
 		    var url = "fileNbrAllocationUserWiseAJax.action?hidAction=updateFileNbr&minFileRange="+minFileRange;
 			url+="&maxFileRange="+maxFileRange+"&userIdUpdate="+userIdUpdate+"&selectedZROLocation="+selectedZROLocation+"&rowCount="+rowCount;
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
 				httpBowserObject.setRequestHeader("Content-type",
 						"application/x-www-form-urlencoded");
 				httpBowserObject.setRequestHeader("Connection", "close");
 				httpBowserObject.onreadystatechange = function() {
 					if (httpBowserObject.readyState == 4) {
 						var responseString = httpBowserObject.responseText;
 						showScreen();
 						if (null == responseString
 									|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
 						}
 						else
 						{
							document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
 						}
 					}
 				};
 				httpBowserObject.send(null);
 			}
 		}
 		else{
             return false;
 	 		}
 	}
 	}
 	}
 	function  fnEditFileNbr(rowCount){
 		document.getElementById('fetchedMinFileRange'+rowCount).value;
 		 $('#fetchedMaxFileRange'+rowCount).attr('disabled', false);
 		 $('#btnUpdate'+rowCount).attr('disabled', false);
	 	}
   function  fnDeleteFileNbrList(rowCount,selectedZROLocation){
 		    var minFileRange=0;
		    minFileRange=Trim(document.getElementById('fetchedMinFileRange'+rowCount).value);
			var maxFileRange=0;
			maxFileRange=Trim(document.getElementById('fetchedMaxFileRange'+rowCount).value);
			var userIdUpdate=0;
			userIdUpdate=Trim(document.getElementById('userIdUpdate').value);
			if(confirm('Are you sure to delete ?')){
				//hideScreen();
	 		    var url = "fileNbrAllocationUserWiseAJax.action?hidAction=deleteFileNbr&minFileRange="+minFileRange;
	 			url+="&maxFileRange="+maxFileRange+"&userIdUpdate="+userIdUpdate+"&selectedZROLocation="+selectedZROLocation;
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
	 				httpBowserObject.setRequestHeader("Content-type",
	 						"application/x-www-form-urlencoded");
	 				httpBowserObject.setRequestHeader("Connection", "close");
	 				httpBowserObject.onreadystatechange = function() {
	 					if (httpBowserObject.readyState == 4) {
	 						var responseString = httpBowserObject.responseText;
	 						showScreen();
	 						if (null == responseString
	 									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
	 						}
	 						else
	 						{
								document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
	 						}
	 					}
	 				};
	 				httpBowserObject.send(null);
	 			}
	 		}
	 		else{
	             return false;
	 	 		}
 	 	}
	function fnRangeValidate(){
 		document.getElementById('onscreenMessage').innerHTML="";
 		var minFileRange=0;
 		minFileRange=Trim(document.getElementById('minFileRange').value);
		var maxFileRange=0;
		maxFileRange=Trim(document.getElementById('maxFileRange').value);
    	if(Trim(minFileRange)=="" || Trim(maxFileRange)=="")
		{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.MANDATORY_FIELD_MSG%></font>";
		}
    	else
    	{
			if(minFileRange==0 || maxFileRange==0)
			{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.BOOKLET_MSG%></font>";
				window.scrollTo(0,0);	
				document.getElementById("btnSubmit").disabled = true;
				return ;
			}
			if(minFileRange> maxFileRange)
			{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><%=DJBConstants.FILE_RANGE_MSG%></font>";
				window.scrollTo(0,0);	
				document.getElementById("btnSubmit").disabled = true;
				return ;
			}
			else
			{
				document.getElementById("btnSubmit").disabled = false;
			}
 	 	}}
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
					id="onscreenMessage"><font color="red"><b><s:actionerror /></b><font
					color="green"><b><s:actionmessage /></b></font></font><%=(null == session.getAttribute
							("AJAX_MESSAGE"))						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form id="allocationForm" name="allocationForm" method="post"
					action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:if
						test="hidAction==null||hidAction==\"UserAllocationsubmit\"||hidAction==\"searchFileNbrList\"">
						<table width="100%" border="0">
							<tr>
								<td align="right" colspan="20"><font color="red"><sup>*</sup></font>
								marked fields are mandatory.</td>
							</tr>
							<tr>
								<td>
								<fieldset><legend>File Allocation Parameter
								</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="left" width="10%"><label>Meter Reader
										Id</label><font color="red"><sup>*</sup></font></td>
										<td><s:textfield cssClass="textbox" theme="simple"
											name="userIdTag" id="userIdTag" /></td>
										<td></td>

										<td></td>
										<td align="right" width="10%"><label>File Range
										From</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="File Range From" style="width: 323px">
										<input type="text" class="textbox" ondragstart="return false;"
											ondrop="return false;" name="minFileRange" id="minFileRange"
											onkeypress="return isNumberKey(event)"
											onblur="fnRangeValidate()" required
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
											name="maxFileRange" id="maxFileRange"
											onkeypress="return isNumberKey(event)"
											onblur="fnRangeValidate()" required
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
											value="     Submit   " class="groovybutton" id="btnSubmit"
											onclick="fnUserAllocationValidatSubmit();" /></td>
										<td></td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
						</table>
					</s:if>
					<s:if
						test="hidAction==null||hidAction==\"UserAllocationsubmit\"||hidAction==\"searchFileNbrList\"">

						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Update And Delete File
								Number </legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="left" width="10%"><label>Meter Reader
										Id</label><font color="red"><sup>*</sup></font></td>
										<td><s:textfield cssClass="textbox" theme="simple"
											name="userIdUpdate" id="userIdUpdate" /></td>
										<td><input type="button" value="  Search  "
											class="groovybutton" id="btnSearch"
											onclick="fnSearchFileNbrList();"></input></td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
							<s:if test="fileNbrAllocationList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>
									<table id="searchResult" class="table-grid">
										<tr>
											<th align="center" width="5%">SI NO</th>
											<th align="center" width="5%">ZRO LOCATION</th>
											<th align="center" width="10%">MIN RANGE</th>
											<th align="center" width="20%">MAX RANGE</th>
										</tr>
										<s:iterator value="fileNbrAllocationList" status="row">
											<tr bgcolor="white">
												<td align="center"><s:property value="#row.count" /></td>
												<td align="center"><s:property
													value="selectedZROLocation" /></td>
												<td><input type="text" name="fetchedMinFileRange"
													id="fetchedMinFileRange<s:property value="#row.count" />"
													value="<s:property value="minFileRange" />" maxLength="30"
													disabled="true"></td>
												<td><input type="text" name="fetchedMaxFileRange"
													id="fetchedMaxFileRange<s:property value="#row.count" />"
													value="<s:property value="maxFileRange" />" maxLength="30"
													disabled="true"></td>
												<td><input type="button" value="  Edit  "
													class="groovybutton" id="btnEdit"
													onclick="fnEditFileNbr('<s:property value="#row.count"/>');"></input></td>
												<td><input type="button" value="  Update  "
													class="groovybutton"
													id="btnUpdate<s:property value="#row.count" />"
													disabled="true"
													onclick="fnUpdateFileNbrList('<s:property value="#row.count"/>','<s:property value="selectedZROLocation" />');"></input></td>
												<td><input type="button" value="  Delete  "
													class="groovybutton" id="btnDelete"
													onclick="fnDeleteFileNbrList('<s:property value="#row.count"/>','<s:property value="selectedZROLocation" />');"></input></td>
										</tr></s:iterator>

									</table>
									</fieldset>
									</td>
								</tr>
							</s:if>
							<s:else>
								<s:if test="hidAction==\"searchFileNbrList\"">
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
<script type="text/javascript">
        function clearAllFields(){
            document.location.href="fileNbrAllocationUserWise.action";
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