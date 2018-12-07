<!--
JSP page for the mrkey needs to be validated against the userid whether it is tagged to the userId or not only for AMR user,as per jtrac DJB-4464 and Open Project Id -1203.
@author Madhuri Singh (Tata Consultancy Services)
@since 07-06-2016
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<!-- Added By Madhuri on 2nd June 2016, as per Jtrac DJB-4464 -->
<%  
	String isAmrRole = DJBConstants.AMR_ROLE;
	String userRole = (String) session.getAttribute("userRoleId");
%>
<%
	try {
%>
<head>
<title>MRD Schedule Job Submit Page - Revenue Management System,
Delhi Jal Board</title>
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
<link rel="stylesheet" type="text/css" href="<s:url value="/css/menu.css"/>" />
<script language=javascript>
	var isPopUp = false;
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	function fnGetMRNo() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		if (selectedZone != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdScheduleDownloadJobSubmitAJax.action?hidAction=populateMRNo&selectedZone="
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
						} else {
							document.getElementById('mrNoTD').innerHTML = responseString;							
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedZone'));
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetArea() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		if ('' != selectedZone && '' != selectedMRNo) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "mrdScheduleDownloadJobSubmitAJax.action?hidAction=populateArea&selectedZone="
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
						} else {
							document.getElementById('areaTD').innerHTML = responseString;
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedMRNo'));
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnCheckZone() {
		if (document.forms[0].selectedZone.value == '') {
			//alert('Please Select Zone First');
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
	function fnSubmitJob() {
	/*Commented by Madhuri on 2nd June 2016 */
	/*	if (Trim(document.forms[0].selectedZone.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Zone</font>";
			document.forms[0].selectedZone.focus();
			return false;
		}
		if (Trim(document.forms[0].selectedBillWindow.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Bill Round</font>";
			document.forms[0].selectedBillWindow.focus();
			return false;
		}
		document.forms[0].action = "scheduleDownload.action";
		document.forms[0].hidAction.value = "prepareDownload";
		if(Trim(document.forms[0].selectedArea.value)!=''){
			document.forms[0].selectedAreaDesc.value = getSelectedText('selectedArea');
		}else{
			document.forms[0].selectedAreaDesc.value ="";
		}
		document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
		*/
		/* Added By Madhuri on 2nd June 2016, as per Jtrac DJB-4464 */
		var isValid= false;

		 if(<%=isAmrRole%>==<%=userRole%>){
			     
			       
					if (Trim(document.forms[0].selectedZone.value) == '') {
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Zone</font>";
					document.forms[0].selectedZone.focus();
					return false;
				}
				
				if (document.forms[0].selectedMRNo.value == '') {
					document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Select a MR No.</font>";
					document.forms[0].selectedMRNo.focus();
					return false;
				}

				if (document.forms[0].selectedArea.value == '') {
					document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Select a Area</font>";
					document.forms[0].selectedArea.focus();
					return false;
				}
				
				if (Trim(document.forms[0].selectedBillWindow.value) == '') {
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Bill Round</font>";
					document.forms[0].selectedBillWindow.focus();
					return false;
				} 
				 var selectedZone = Trim(document.getElementById('selectedZone').value);
				 var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
				 var selectedArea = Trim(document.getElementById('selectedArea').value);
				 	 if ('' != selectedZone && '' != selectedMRNo && '' != selectedArea ) {		
						document.getElementById('onscreenMessage').innerHTML = "";
					    var url = "mrdScheduleDownloadJobSubmitAJax.action?hidAction=validateMrkey&selectedZone="
										+ selectedZone + "&selectedMRNo=" + selectedMRNo + "&selectedArea=" + selectedArea;
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
								  // alert(httpBowserObject.responseText);
									if (null == responseString
											|| responseString.indexOf("ERROR:") > -1) {										
										document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Sorry: You do not have security rights for this action. Please select the input fields correctly. </b></font>";
										document.getElementById('onscreenMessage').title = "";										
									} else {
										
										if(responseString.indexOf("validMrkey") > -1){
											isValid = true;
											//alert("inside response string block>>isValid>>" +isValid);
											if(isValid == true){		
												
												document.forms[0].action = "scheduleDownload.action";
												document.forms[0].hidAction.value = "prepareDownload";
												
												if(Trim(document.forms[0].selectedArea.value)!=''){
													document.forms[0].selectedAreaDesc.value = getSelectedText('selectedArea');
												}else{
													document.forms[0].selectedAreaDesc.value ="";
												}
												
												document.getElementById('onscreenMessage').innerHTML = "";		
												hideScreen();
												document.forms[0].submit();
											}
											else {
												return false;
											}
																							
									  	}else{
											isValid= false;
																							
										}
									}									
								}
							};
							httpBowserObject.send(null);
						}						     
					 
				
		 }
		 }
		 else{
			 isValid= true;
   	   if (Trim(document.forms[0].selectedZone.value) == '') {
  			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Zone</font>";
  			document.forms[0].selectedZone.focus();
  			return false;
  		}


  		if (Trim(document.forms[0].selectedBillWindow.value) == '') {
  			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Bill Round</font>";
  			document.forms[0].selectedBillWindow.focus();
  			return false;
  		}
  		
		 }
	

		if(isValid == true){		
		
			document.forms[0].action = "scheduleDownload.action";
			document.forms[0].hidAction.value = "prepareDownload";
			
			if(Trim(document.forms[0].selectedArea.value)!=''){
				document.forms[0].selectedAreaDesc.value = getSelectedText('selectedArea');
			}else{
				document.forms[0].selectedAreaDesc.value ="";
			}
			
			document.getElementById('onscreenMessage').innerHTML = "";		
			hideScreen();
			document.forms[0].submit();
		}
		else {
			return false;
		}
		/* Ended By Madhuri on 2nd June 2016, as per Jtrac DJB-4464 */
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
				<s:form method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="selectedAreaDesc" id="selectedAreaDesc" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Schedule Job Submit Criteria</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="10%"><label>Zone</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Zone" nowrap><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZone" id="selectedZone"
										onchange="fnGetMRNo();" /></td>
									<td align="left" width="10%"><img
										src="/DataEntryApp/images/load.gif" width="25"
										border="0" title="Processing" style="display: none;"
										id="imgSelectedZone" /></td>
									<td align="right" width="10%"><label>MR No</label><font
										color="red"><sup><!-- Added By Madhuri 2nd June 2016 as per Jtrac -DJB-4464 --><%
 	if (null != isAmrRole
 					&& isAmrRole.equalsIgnoreCase(userRole)) {
 %> * <%
 	}
 %></sup></font></td>
									<td align="left" width="15%" title="MR No" id="mrNoTD"><s:select
										list="#session.MRNoListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="selectedMRNo" id="selectedMRNo"
										onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
									<td align="left" width="25%"><img
										src="/DataEntryApp/images/load.gif" width="25"
										border="0" title="Processing" style="display: none;"
										id="imgSelectedMRNo" /></td>
								</tr>
								<tr>
									<td align="right"><label>Area</label><font color="red"><sup><!-- Added By Madhuri 2nd June 2016 as per Jtrac -DJB-4464 --><%
										if (null != isAmrRole
														&& isAmrRole.equalsIgnoreCase(userRole)) {
									%> * <%
										}
									%></sup></font></td>
									<td align="left" title="Area" id="areaTD"><s:select
										list="#session.AreaListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedArea" id="selectedArea"
										onfocus="fnCheckZoneMRNo();" /></td>
									<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td align="right"><label>Bill Round</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left"><s:select name="selectedBillWindow"
										id="selectedBillWindow" headerKey=""
										headerValue="Please Select" list="#session.openBillWindowList"   
										cssClass="selectbox" theme="simple" /></td>
									<td align="left"><s:submit key="label.submit"
										cssClass="groovybutton" theme="simple" /></td>
								</tr>
								<tr>
									<td align="right" colspan="6"><font color="red"><sup>*</sup></font> marked fields are
									mandatory.</td>
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
	$("form").submit(function(e) {
		fnSubmitJob();
	});
</script>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>