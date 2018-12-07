<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>

<%@page import="com.tcs.djb.model.UserRole"%><html>
<head>
	<title>Revenue Management System,Delhi Jal Board</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="description" content="Revenue Management System,Delhi Jal Board ">
	<meta name="copyright" content="&copy;2012 Delhi Jal Board">
	<meta name="author" content="Tata Consultancy Services">
	<meta name="keywords" content="djb,DJB,Revenue Management System,Delhi Jal Board">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="googlebot" content="noarchive">
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/menu.css"/>"/>
	<script type="text/javascript" src="<s:url value="/js/UtilityScripts.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
	<%		
		String userType=(String) session.getAttribute("userType");
		String passwordChangeType=(String) session.getAttribute("passwordChangeType");
		String roleChangeSuccess=(String) session.getAttribute("roleChangeSuccess");
		ArrayList<UserRole> userRoleList=(ArrayList<UserRole>) session.getAttribute("userRoleList");
		
	%>
	<script language=javascript>
		function disableBack()
		{
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
	 	function fnResetSpanSuccess(){
		 	if(null!=document.getElementById('spanSuccess')&&'undefined'!=document.getElementById('spanSuccess')){
	 			document.getElementById('spanSuccess').innerHTML="&nbsp;";
		 	}
	 	}
 	</script>

 	<script type="text/javascript">
 		function fnOnSubmit(){
 			hideScreen();
 			return true;
 		}
 		function fnOnLoad(){
 			
 		}
 	</script> 	
</head>
<body onload="fnOnLoad();">
<%@include file="../jsp/CommonOverlay.html"%> 
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage">
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%> 
					</td>
				</tr>
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/TopMenu.html"%> 
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">
					<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><%if("Y".equalsIgnoreCase(roleChangeSuccess)){ %><span id="spanSuccess"><font color="green"><b>Role Successfully Updated</b></font></span><%}%></span></font>&nbsp;</div>
						<div id="setRoleAccess">
							<table width="100%" align="center" border="0" >							
								<tr>
									<td align="center"  valign="top">
										<fieldset>
											<legend><span>Set User Role</span></legend>	
											<form action="javascript:void(0);" name="formSetRoleAccess" onsubmit="return false;">			
											<table width="95%" align="center" border="0">	
												<tr>
													<td align="right" width="50%">
														User ID<font color="red"><sup>*</sup></font>
													</td>
													<td align="left" width="50%">
														<input type="text" name="userId" id="userId"  style="text-align:left;" class="textbox" onchange="fnResetSpanSuccess();"/>
													</td>
												</tr>
												<tr>
													<td align="right" >
														User Role<font color="red"><sup>*</sup></font>
													</td>
													<td align="left">
														<select name="userRole" id="userRole" class="selectbox" onchange="fnResetSpanSuccess();">
													  		<option value="">Please Select</option>
													  		<% 
													  		if(null!=userRoleList){
													  			for(int i=0;i<userRoleList.size();i++){
													  				UserRole userRole=(UserRole)userRoleList.get(i);													  			
													  		%>
													  			<option value="<%=userRole.getUserRoleId() %>"><%=userRole.getUserRoleDesc() %></option>
													  		<%}}%>
													  	</select> 
													</td>
												</tr>												
												<tr>
													<td align="left">&nbsp;</td>
													<td align="left">
														<input type="submit" name="btnUpdate" id="btnUpdate" value="Update"  class="groovybutton"/>
													</td>
												</tr>
												<tr>
													<td align="left" colspan="2">&nbsp;</td>
												</tr>
												<tr>
													<td align="left"  id="search-error-msg">
														<font color="red" size="2"><s:actionerror /></font>
													</td>
													<td align="right" >
														<font color="red" size="2">*</font>=Mandatory Fields.
													</td>
												</tr>
											</table>
											</form>																			
										</fieldset>
										<s:form action="setRoleAccess.action" method="post">
											<s:hidden name="hidAction"/>	
											<s:hidden name="userId"/>
											<s:hidden name="userRole"/>
										</s:form>	
									</td>
								</tr>
							</table>
						</div>				
					</td>
				</tr>
				<tr height="20px">
					<td align="center" valign="bottom">
						<%@include file="../jsp/Footer.html"%>
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		document.forms[1].hidAction.value="";
  		document.forms[1].userId.value="";  		
  		document.forms[1].userRole.value="";  	
	});
  	$("form").submit(function(e){  
  		document.getElementById('search-error-msg').innerHTML ="";	  	
  	  	if(Trim(document.forms[0].userId.value)==''){
  	  		document.getElementById('search-error-msg').innerHTML ="<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
	  		document.forms[0].userId.focus();
  			return;
  	  	}
		if( Trim(document.forms[0].userRole.value)==''){
			document.getElementById('search-error-msg').innerHTML ="<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
  	  		document.forms[0].userRole.focus();
	  		return;
		}
		hideScreen();
		var userRoleId="<%=userRoleId%>";
		var loggedInID="<%=userId%>";
		if( Trim(userRoleId.toString())=='1' && Trim(userRoleId.toString())!=Trim(document.forms[0].userRole.value).toString() && Trim(loggedInID.toString())==Trim(document.forms[0].userId.value).toString()){
			if(!confirm('You are a ADMIN now.\nYou May loose Access to This Screen.\nAre You sure You want to Change Your Current role ?')){
				showScreen();
				document.forms[0].userRole.value="";
				document.forms[0].userRole.focus();
	  			return;
			}
		}		
		/*if(Trim(loggedInID.toString())==Trim(document.forms[0].userId.value).toString() && Trim(userRoleId.toString())==Trim(document.forms[0].userRole.value).toString()){
			alert('You Allready have the Selected Role.\n\nPlease Select a different Role.');
			showScreen();
			document.forms[0].userRole.value="";
			document.forms[0].userRole.focus();
  			return;			
		}	*/				
		if( Trim(document.forms[0].userRole.value)=='1'){
			if(!confirm('Are You sure You want to Provide ADMIN role to the user ?')){
				showScreen();
				document.forms[0].userRole.value="";
				document.forms[0].userRole.focus();
	  			return;
			}
		}
		
				
  		document.forms[1].hidAction.value="updateUserRole";
  		document.forms[1].userId.value=Trim(document.forms[0].userId.value);  		
  		document.forms[1].userRole.value=Trim(document.forms[0].userRole.value);  		
    	document.forms[1].submit();
  		
  	});	
  	$('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</html>