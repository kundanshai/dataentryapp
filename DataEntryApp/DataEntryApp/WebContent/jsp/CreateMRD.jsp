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
<title>Create New MRD Page - Revenue Management System, Delhi
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script language=javascript>

  var errormsg="<%=DJBConstants.CRTMRD_DMND_TRNSFR_MSG %>";

  function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	function fnCreateMRD(){
		if (Trim(document.forms[0].newZone.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Zone.</font>";
			document.forms[0].newZone.focus();
			return ;
		}
		if (Trim(document.forms[0].newMRNo.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter an MR No.</font>";
			document.forms[0].newMRNo.focus();
			return ;
		}
		if (Trim(document.forms[0].newAreaDesc.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter Area Description.</font>";
			document.forms[0].newAreaDesc.focus();
			return ;
		}
		if (Trim(document.forms[0].newAreaCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter Area Code.</font>";
			document.forms[0].newAreaCode.focus();
			return ;
		}
		if (Trim(document.forms[0].newZROCode.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a ZRO Code.</font>";
			document.forms[0].newZROCode.focus();
			return ;
		}
		if (Trim(document.forms[0].newMRDType.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select an MRD Type.</font>";
			document.forms[0].newMRDType.focus();
			return ;
		}
		if (Trim(document.forms[0].newColCat.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Select a Colony Category.</font>";
			document.forms[0].newColCat.focus();
			return ;
		}
		fnCreateMRDAJaxCall();		
	}
	function fnCreateMRDAJaxCall(){
		if(confirm('Are You Sure You Want to Create a New MRD ?\nClick OK to continue else Cancel.')){
			var url = "createMRDAJax.action?hidAction=createNewMRD";
			url+="&newZone="+Trim(document.getElementById('newZone').value);
			url+="&newMRNo="+Trim(document.getElementById('newMRNo').value);
			url+="&newAreaCode="+Trim(document.getElementById('newAreaCode').value);
			url+="&newAreaDesc="+Trim(document.getElementById('newAreaDesc').value);
			url+="&newZROCode="+Trim(document.getElementById('newZROCode').value);
			url+="&newMRDType="+Trim(document.getElementById('newMRDType').value);
			url+="&newColCat="+Trim(document.getElementById('newColCat').value);
			
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
						//alert(responseString);
				        //alert(errormsg);
				        if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
                        if (responseString.indexOf("ERROR:NODJBMTRRDR") > -1){
	                        document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+errormsg+" </b></font>";
                               }
                              else{
                                  if(responseString.indexOf("ERROR:MORETHANONEDJBMTRRDR") > -1){
                                	  document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+errormsg+" </b></font>";
                                      }else{
                                    	  document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
                                          }
							/*document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";*/
                                   }
							} else {
							document.getElementById('onscreenMessage').innerHTML = "<font color='#33CC33' size='2'><b>Successfuly Created new MRD with MRKEY "+responseString+"  </b></font>";
							document.getElementById('btnSubmit').disabled=true;
						}
						showScreen();
					}					
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnValidateCreateMRDAJaxCall(){
		if(Trim(document.getElementById('newZone').value)!='' && Trim(document.getElementById('newMRNo').value)!=''&&Trim(document.getElementById('newAreaCode').value)!=''){
			var url = "createMRDAJax.action?hidAction=validateCreateNewMRD";
			url+="&newZone="+Trim(document.getElementById('newZone').value);
			url+="&newMRNo="+Trim(document.getElementById('newMRNo').value);
			url+="&newAreaCode="+Trim(document.getElementById('newAreaCode').value);
			
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
						//alert(httpBowserObject.responseText);
						if (null == responseString || responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
						} else {
							if (responseString.indexOf("VALID")==-1) {
								document.getElementById('onscreenMessage').innerHTML = responseString;
								document.getElementById('btnSubmit').disabled=true;
							}else{
								document.getElementById('btnSubmit').disabled=false;
								document.getElementById('onscreenMessage').innerHTML = "";
							}							
						}
						showScreen();
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
				<s:form id="MRD" method="post" action="javascript:void(0);"
					theme="simple" onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="isPopUp" id="isPopUp" />
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>New MRD Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="10%"><label>Zone</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Zone" nowrap><s:select
										list="#session.ZoneListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="newZone" id="newZone"
										onchange="fnValidateCreateMRDAJaxCall();" /></td>
									<td align="right" width="10%"><label>MR No</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="15%" title="MR No" id="mrNoTD"><s:textfield
										cssClass="textbox" theme="simple" name="newMRNo" id="newMRNo"
										maxlength="5"
										onchange="IsNumber(this);fnValidateCreateMRDAJaxCall();" /></td>
								</tr>
								<tr>
									<td align="right"><label>Area Description</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" title="Area" id="areaTD"><s:textfield
										cssClass="textbox-long" theme="simple" name="newAreaDesc"
										id="newAreaDesc" maxlength="200" /></td>
									<td align="right"><label>Area Code</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left"><s:textfield cssClass="textbox"
										theme="simple" name="newAreaCode" id="newAreaCode"
										maxlength="5"
										onchange="IsNumber(this);fnValidateCreateMRDAJaxCall();" /></td>
								</tr>
								<tr>
									<td align="right"><label>ZRO Code</label><font color="red"><sup>*</sup></font></td>
									<td align="left" title="Old ZRO Code" id="selectedZROCodeTD"><s:select
										list="#session.ZROListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="newZROCode" id="newZROCode" /></td>
									<td align="right"><label>MRD Type</label><font color="red"><sup>*</sup></font></td>
									<td align="left"><s:select list="#session.MRDTypeListMap"
										headerKey="" headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="newMRDType" id="newMRDType" /></td>
								</tr>
								<tr>
									<td align="right"></td>
									<td align="left"></td>
									<td align="right"><label>Colony Category</label><font color="red"><sup>*</sup></font></td>
									<td align="left">
										<% 
										// Added by 10227971 (Arnab Nandy)
										String s=DJBConstants.LIST_COL_CAT;
										List<String> items = Arrays.asList(s.split("\\s*,\\s*")); 
										%>
										<select name="newColCat" id="newColCat" style="border: 1px #21c5d8 solid; width: 130px; padding: 2px; margin: 2px; height: 22px; font: 12px;">
											<%
											for (int i = 0; i < items.size(); i++) {
												%>
												<option value="<%
												if(items.get(i).equalsIgnoreCase("Please Select"))
												{
													out.print("");
												}
												else
												{
													out.print(items.get(i));
												}
												%>" ><% out.print(items.get(i)); %></option>
												<%
											}
											%>
										    
										</select>
									</td>
									
								</tr>
								<tr>
									<td align="left">&nbsp;</td>
									<td align="left"><input type="button"
										value="Reset All Fields" class="groovybutton"
										onclick="clearAllFields();" /></td>
									<td align="left">&nbsp;</td>
									<td align="left"><s:submit key=" Create MRD "
										cssClass="groovybutton" theme="simple" id="btnSubmit"
										disabled="true" /></td>
								</tr>
								<tr>
									<td align="left" colspan="2">&nbsp;</td>
									<td align="right" colspan="2"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
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
	$('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});
	$('.textbox-long').focus(function() { 
		$(this).addClass("textbox-longfocus"); 
	});   
	$('.textbox-long').blur(function() {  					
		$(this).removeClass("textbox-longfocus"); 
		$(this).addClass("textbox-long"); 
	});
	$(document).ready(function(){
		
	});	
	$("form").submit(function(e) {
		fnCreateMRD();
	});
	function clearAllFields(){
		var tempIsPopUp=document.getElementById('isPopUp').value;
		document.getElementById("MRD").reset();
		document.getElementById('onscreenMessage').innerHTML = "";	
		//document.getElementById('btnSubmit').disabled=false;	
		document.getElementById('isPopUp').value=tempIsPopUp;
	}
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>