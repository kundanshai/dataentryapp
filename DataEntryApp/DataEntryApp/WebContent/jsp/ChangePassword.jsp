<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<html>
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
	<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/DJBPasswordPolicy.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.1.4.2.min.js"/>"></script>
	<%		
		String userType=(String) session.getAttribute("userType");
		String passwordChangeType=(String) session.getAttribute("passwordChangeType");
		String passwordChangeSuccess=(String) session.getAttribute("passwordChangeSuccess");
		
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
 		function fnSetStrength(obj) {
 			var passwd=Trim(obj.value);
 			if(validatePassword(passwd)){
 				switch (obj.value.length) {
 				case 6:
 					document.getElementById("passwordStrength").style.width = '10%';
 					document.getElementById("passwordStrength").style.background = 'orange';
 					document.getElementById("passwordStrength").title = "OK";
 					break;
 				case 7:
 					document.getElementById("passwordStrength").style.width = '15%';
 					document.getElementById("passwordStrength").style.background = 'orange';
 					document.getElementById("passwordStrength").title = "Good";
 					break;
 				case 8:
 					document.getElementById("passwordStrength").style.width = '20%';
 					document.getElementById("passwordStrength").style.background = 'green';
 					document.getElementById("passwordStrength").title = "Strong";
 					break;
 				case 9:
 					document.getElementById("passwordStrength").style.width = '30%';
 					document.getElementById("passwordStrength").style.background = 'green';
 					document.getElementById("passwordStrength").title = "Strong";
 					break;
 				default:
 					if(obj.value.length>6){
 					document.getElementById("passwordStrength").style.width = '30%';
 					document.getElementById("passwordStrength").style.background = 'green';
 					document.getElementById("passwordStrength").title = "Strong";
 					}else{
 						document.getElementById("passwordStrength").style.width = '30%';
 						document.getElementById("passwordStrength").style.background = 'red';
 						document.getElementById("passwordStrength").title = "Follow Password Policy";
 					}
 					break;
 				}
 			}else{
 				document.getElementById("passwordStrength").style.width = '30%';
 				document.getElementById("passwordStrength").style.background = 'red';
 				document.getElementById("passwordStrength").title = "Follow Password Policy";
 			}
 		}
 		var isValidPassword=false;
 		function isValidPasswordPolicy(obj){	
 			var passwd=obj.value;
 			if(passwd!=''){
 				if(validatePassword(passwd)){
 					if(null!=document.getElementById('onscreenMessage')&&'undefined'!=document.getElementById('onscreenMessage')){
 			 			document.getElementById('onscreenMessage').innerHTML="&nbsp;";
 				 	}
 					isValidPassword=true;
 					return true;
 				}else{
 					if(null!=document.getElementById('onscreenMessage')&&'undefined'!=document.getElementById('spanSuccess')){
 			 			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>Please follow the password policy</b></font>";
 				 	}
 					isValidPassword=false;
 					obj.focus();
 					return false;
 				}
 			}
 		} 		
 	  	function fnUpdatePassword(userType,userId,oldPassword,newPassword){		
 	  		var confirmMessage="Are You Sure you want to Update Password ?\nClick OK to continue else Cancel.";
 			document.getElementById('onscreenMessage').innerHTML = "";
 			var url = "changePasswordAJax.action?hidAction=updatePassword";
 			url+="&userType="	+ userType;
 			url+="&resetUserId="	+ userId;
 			url+="&oldPassword="	+ oldPassword;
 			url+="&resetPassword="	+ newPassword;
 			//alert('url>>'+url);               
 			var httpBowserObject = null;
 			if (window.ActiveXObject) {
 				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
 			} else if (window.XMLHttpRequest) {
 				httpBowserObject = new XMLHttpRequest();
 			} else {
 				alert("Browser does not support AJAX...........");
 				return;
 			}
 			if (httpBowserObject != null && confirm(confirmMessage)) {
 				hideScreen();			  
 				httpBowserObject.open("POST", url, true);
 				httpBowserObject.setRequestHeader("Content-type",
 						"application/x-www-form-urlencoded");
 				httpBowserObject.setRequestHeader("Connection", "close");
 				httpBowserObject.onreadystatechange = function() {
 					if (httpBowserObject.readyState == 4) {
 						var responseString = httpBowserObject.responseText;
 						//alert(httpBowserObject.responseText);
 						if (null != responseString
 								&& responseString.indexOf('<script') == -1) {
 							if (null == responseString
 									|| responseString.indexOf("ERROR:") > -1) {
 								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
 								showScreen();
 							}else{
 								document.getElementById('onscreenMessage').innerHTML = responseString;
 								showScreen();	
 								if(responseString.indexOf("Successfully") > -1 && userType=='SELF' ){
 									document.getElementById('btnUpdate').disabled=true;
 									alert('Password has been Successfully Updated.\nYou must login again to continue.');								
 									gotoLoginPage();
 								}
 							}
 						}else {
 							document.getElementById('onscreenMessage').innerHTML = responseString;
 							showScreen();
 						}
 					} 
 				};
 				httpBowserObject.send(null);
 			}
 		}	
 	  	function gotoLoginPage()
		{
			hideScreen();
			DeleteCookie("IsRefresh");
			var userType="<%=userType%>";
			var url="logout.action?type=Password has been Successfully Updated. Please login again to continue.";
			/*if(userType=='EMPLOYEE'){
				url+="?userRole="+userType;
			}*/
			document.location.href=url;
			
		}	
 	</script> 	
</head>
<body oncontextmenu="return false;">
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
				<tr>
					<td align="center" valign="top">
					<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><%if("Y".equalsIgnoreCase(passwordChangeSuccess)){ %><span id="spanSuccess"><font color="green"><b>Password Successfully Updated</b></font></span><%}%></span></font>&nbsp;</div>
						<div id="chengePassword">
							<table width="100%" align="center" border="0" >							
								<tr>
									<td align="center"  valign="top">
										<fieldset>
											<legend><span>Change Password</span></legend>	
											<form action="javascript:void(0);" name="formChangePassword" onsubmit="return false;">			
											<table width="95%" align="center" border="0">	
												<tr>
													<td align="right" width="50%">
														User ID<font color="red"><sup>*</sup></font>
													</td>
													<td align="left" >
													<%if(null!=userId && null!=passwordChangeType &&"SELF".equals(passwordChangeType)){ %>
														<input type="text" name="userId" id="userId"  style="text-align:left;" class="textbox" value="<s:property value="#session.userId" />" disabled="disabled"/>
													<%}else{ %>
														<input type="text" name="userId" id="userId"  style="text-align:left;" class="textbox" onchange="fnResetSpanSuccess();"/>
													<%} %>
													</td>
													<td align="left" width="50%">
														&nbsp;
													</td>
												</tr>
												<%if(null!=passwordChangeType &&"SELF".equals(passwordChangeType)){ %>
												<tr>
													<td align="right" >
														Old Password<font color="red"><sup>*</sup></font>
													</td>
													<td align="left">
														<input type="password" name="oldPassword" id="oldPassword"  style="text-align:left;" class="password" onchange="fnResetSpanSuccess();"/>
													</td>
													<td align="left" width="50%">
														&nbsp;
													</td>
												</tr>
												<%} %>
												<tr>
													<td align="right" >
														<label id="lblPassword">New Password</label><font color="red"><sup>*</sup></font>
													</td>
													<td align="left">
														<span id="hint"><input type="password" name="newPassword" id="newPassword"  style="text-align:left;" class="password" onchange="fnResetSpanSuccess();" onkeyup="fnSetStrength(this);isValidPasswordPolicy(this);"/></span>
													</td>
													<td align="left" width="20%">
														<table border=0 width="30%" height="2px" bgcolor="red"
															id="passwordStrength">
															<tr>
																<td>&nbsp;</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td align="right" >
														Confirm New Password<font color="red"><sup>*</sup></font>
													</td>
													<td align="left">
														<input type="password" name="newPasswordConfirm" id="newPasswordConfirm"  style="text-align:left;" class="password" onchange="fnResetSpanSuccess();"/>
													</td>
													<td align="left" width="50%">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td align="left">&nbsp;</td>
													<td align="left">
														<input type="submit" name="btnUpdate" id="btnUpdate" value="Update"  class="groovybutton"/>
													</td>
													<td align="left" width="50%">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td align="left" colspan="3">&nbsp;</td>
												</tr>
												<tr>
													<td align="left"  id="search-error-msg" colspan="3">
														<font color="red"><b><s:actionerror /></b></font>
													</td>
												</tr>
												<tr>
													<td align="right" colspan="3">
														<font color="red" size="2">*</font>Marked fields are Mandatory.
													</td>
												</tr>
											</table>
											</form>																			
										</fieldset>
										<s:form action="changePassword.action" method="post" theme="simple">
											<s:hidden name="hidAction"/>	
											<s:hidden name="userType"/>
											<s:hidden name="resetUserId"/>	
											<s:hidden name="oldPassword"/>	
											<s:hidden name="resetPassword"/>	
										</s:form>	
										<s:form action="login.action" method="post" theme="simple">
											<s:hidden name="hidAction"/>	
											<s:hidden name="userType"/>
											<s:hidden name="resetUserId"/>	
											<s:hidden name="oldPassword"/>	
											<s:hidden name="resetPassword"/>	
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
	var passwordChangeType="<%=passwordChangeType%>";
	$(document).ready(function(){
		document.forms[1].hidAction.value="";
  		document.forms[1].resetUserId.value="";
  		if(Trim(passwordChangeType.toString())=='SELF'){
  			document.forms[1].oldPassword.value="";
  		}
  		document.forms[1].resetPassword.value="";
  		if(document.forms[2]){
  	  		if(Trim(passwordChangeType.toString())=='SELF' && document.forms[0].userId.value==''){
  	  			document.forms[0].userId.value=Trim(document.forms[2].resetUserId.value);
  	  			document.forms[0].userId.disabled=true;
  	  		}
  		}
  		var pwdPolicy = "<div class=\"tooltip\"><b>Password Should Contain atleast 6 Alpha Numeric Characters which should:</b><br />";
		pwdPolicy += "1.Contain atleast one Uppercase Letter. <br />";
		pwdPolicy += "2.Contain atleast one Lowercase Letter.<br />";
		pwdPolicy += "3.Contain atleast one Numeric Character.<br />";
		pwdPolicy += "4.Contain atleast one Special Character other than [\' \" \& ;	: %] and blank space. </div>";
		var winWidth=$(document).width();
		var winHeight=$(document).height();
		var changeTooltipPosition = function(event) {
			var tooltipX = event.pageX - 8;
			var tooltipY = event.pageY + 8;							
			$('div.tooltip').css( {
				top : tooltipY,
				left : tooltipX
			});
		};

		var showTooltip = function(event) {
			$('div.tooltip').remove();							
			$(pwdPolicy).appendTo('body');
			changeTooltipPosition(event);
		};
		var showTooltipFixedPosition = function(event) {
			$('div.tooltip').remove();							
			$(pwdPolicy).appendTo('body');
			$('div.tooltip').css( {
				top : winHeight/3+50,
				left : winWidth/3+105
			});
		};
		var hideTooltip = function() {
			$('div.tooltip').remove();
		};

		$("span#hint,label#lblPassword,input#newPassword'").bind( {
			mousemove : changeTooltipPosition,
			mouseenter : showTooltip,
			focus:showTooltipFixedPosition,
			blur:hideTooltip,
			mouseleave : hideTooltip
		});
	});
  	$("form").submit(function(e){  
  		document.getElementById('onscreenMessage').innerHTML ="";	  	
  	  	if(Trim(document.forms[0].userId.value)==''){
  	  		document.getElementById('onscreenMessage').innerHTML ="<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
	  		document.forms[0].userId.focus();
  			return;
  	  	}  	  	
  	  	if(Trim(passwordChangeType.toString())=='SELF'){
	  	  	if(Trim(document.forms[0].oldPassword.value)==''){
	  	  		document.getElementById('onscreenMessage').innerHTML ="<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
	  	  		document.forms[0].oldPassword.focus();
		  		return;
	  	  	}
  	  	}
		if( Trim(document.forms[0].newPassword.value)==''){
			document.getElementById('onscreenMessage').innerHTML ="<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
  	  		document.forms[0].newPassword.focus();
	  		return;
		}
		if( Trim(document.forms[0].newPassword.value).toString()!=Trim(document.forms[0].newPasswordConfirm.value).toString()){
			document.getElementById('onscreenMessage').innerHTML ="<font color='red' size='2'>New Password and Cinfirmed Password Doesn't Match.</font>";
			document.forms[0].newPasswordConfirm.value="";
  	  		document.forms[0].newPasswordConfirm.focus();
	  		return;
		}
		if(isValidPassword){
	  		document.forms[1].hidAction.value="updatePassword";
	  		document.forms[1].resetUserId.value=Trim(document.forms[0].userId.value);
	  		if(Trim(passwordChangeType.toString())=='SELF'){
	  			document.forms[1].oldPassword.value=Trim(document.forms[0].oldPassword.value);
	  		}
	  		document.forms[1].resetPassword.value=Trim(document.forms[0].newPassword.value);
	  		var userId=Trim(document.forms[0].userId.value);
	  		var oldPassword="";
	  		if(document.forms[0].oldPassword){
	  			oldPassword=Trim(document.forms[0].oldPassword.value);
	  		}
	  		var newPassword=Trim(document.forms[0].newPassword.value);
	  		fnUpdatePassword(passwordChangeType,userId,oldPassword,newPassword);
	  		//hideScreen();
	    	//document.forms[1].submit();
		}else{
			if(null!=document.getElementById('onscreenMessage')&&'undefined'!=document.getElementById('spanSuccess')){
	 			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>Please follow the password policy</b></font>";
		 	}
		}
  		
  	});
  	$('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	}); 
	$('input[type="password"]').focus(function() { 
		$(this).addClass("passwordfocus"); 
	});   
	$('input[type="password"]').blur(function() {  					
		$(this).removeClass("passwordfocus"); 
		$(this).addClass("password"); 
	}); 	
</script>
</html>