<!-- 
30 April 2016 Arnab Nandy (1227971)		
   - Enabled Two-factor authentication for Data Entry Users, according to JTrac DJB-4464 and OpenProject#1151
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%try{ 
	/* String BIOMETRIC_AUTHENTICATION_FLAG =  null!=PropertyUtil.getProperty("BIOMETRIC_AUTHENTICATION")? PropertyUtil.getProperty("BIOMETRIC_AUTHENTICATION"):"Y"; */
%>
<head>
	<title>Login-Revenue Management System,Delhi Jal Board</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="description" content="Revenue Management System,Delhi Jal Board ">
	<meta name="copyright" content="&copy;2012 Delhi Jal Board">
	<meta name="author" content="Tata Consultancy Services">
	<meta name="keywords" content="djb,DJB,Revenue Management System,Delhi Jal Board">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="googlebot" content="noarchive">
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/tabs.css"/>"/>
	<script type="text/javascript" src="<s:url value="/js/UtilityScripts.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/Login.js"/>"></script> 
	<script type="text/javascript" src="/DJBSSO/js/DJBCommon.js"></script>	
 	<script language="javascript">function encryptMe(value){var encryptedValue= _enc(value);encryptedValue= _enc(encryptedValue);return encryptedValue;}</script>
 	<script type="text/javascript" language="javascript">
	var captchaCheckFlag = "Y";
	var isEmployee=false;
	var flg = "0";
	/**************Captcha fetching**********************/
	function refresh() {
		document.getElementById('capSpan').innerHTML = "";
		changeCaptcha();
	}
	/* function changeCaptcha() {
		document.getElementById('captcha').innerHTML = "";
		document.getElementById('captcha').style.display = 'none';
		document.getElementById('captcha').innerHTML = "<img src='http://172.18.41.156:7003/EmployeeBPWeb/CaptchaGeneratorServlet?count='"
				+ flg + "/>";
		document.getElementById('captcha').style.display = 'block';
		flg = parseInt(flg) + 1;
		var url = '/EmployeeBPWeb/portals/DJBEmployee.portal?_nfpb=true&_st=&_windowLabel=TlsEmpLoginPortlet_1_1&_urlType=resource&_portlet.renderResource=true&_resca=cacheLevelPage&_rrparams=';
		url += "&actionid=tls.empCaptchapage.value&page=captcha&count=" + flg;
		var response = ajaxFunction("POST", url, false);
		document.getElementById('captchaHidden').value = response;
	}
	function checkCaptcha() {
		var url = '/EmployeeBPWeb/portals/DJBEmployee.portal?_nfpb=true&_st=&_windowLabel=TlsEmpLoginPortlet_1_1&_urlType=resource&_portlet.renderResource=true&_resca=cacheLevelPage&_rrparams=';
		url += "&actionid=tls.empCaptchapage.value&page=captchaCheck&count="
				+ flg + "&textVal="
				+ document.getElementById('captchaText').value;
		var response = ajaxFunction("POST", url, false);
		if (response.substring(0, 1) == 'Y') {
			document.getElementById('capSpan').innerHTML = "";
			captchaFlg = "1";
			document.getElementById('btnVerify').disabled = false;
		} else {
			document.getElementById('capSpan').innerHTML = "<font color='red'>Please Put Correct Text</font>";
			captchaFlg = "0";
			changeCaptcha();
		}
	}
	if (captchaCheckFlag == 'Y') {
		var captchaFlg = "0";
	} else {
		var captchaFlg = "1"; // to remove captcha set it at "1" otherwise at 0..
	}
	function whichButton(event) {
		if (event.button == 2) {
			//For right click
			alert("Right Click Not Allowed!");
		}
	} */

	/* function noCTRL(e) {
		var code = (document.all) ? event.keyCode : e.which;
		var msg = "Paste funtionality is prohibited to upload file.";
		if (parseInt(code) == 17) {
			alert(msg);
			window.event.returnValue = false;
		}
	} */
	/* function checkVal(e) {
		try {
			var unicode = e.charCode ? e.charCode : e.keyCode;
			if (unicode != 8) { //if the key isn't the backspace key (which we should allow)
				if (unicode < 48 || unicode > 57) //if not a number
					return false;//disable key press
			}
		} catch (e) {
		}
	} */
	/* function fnValidate() {
		if (Trim(document.forms[0].username.value) == '') {
			document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Enter Mandatory fields</font>";
			document.forms[0].username.focus();
			return false;
		}
		if (Trim(document.forms[0].FIRUserData.value) == ''&&'Y'==BIOMETRIC_AUTHENTICATION_FLAG) {
			document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Please Complete Verification Process.</font>";
			return false;
		}
	}
	function fnReset(){
		window.location.href=requestURL;
	} */
</script>
 	<script language="javascript">
	<%-- var requestURL=document.URL;
	var BIOMETRIC_AUTHENTICATION_FLAG="<%=BIOMETRIC_AUTHENTICATION_FLAG%>"; --%>
	/* function fnSetBiometricUser(obj) {
		//alert('fnSetBiometricUser::'+obj.value);
		document.forms[0].FIRUserData.value =Trim(obj.value);
	} */
	/* function fnLaunchBiometricCapture(obj){
		//alert('fnLaunchBiometricCapture');
		//if(isEmployee&&Trim(obj.value)!=''){ 
		/* Start : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151
		*/
		/* if(Trim(obj.value)!=''){
		/* End : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
			/* if('Y'==BIOMETRIC_AUTHENTICATION_FLAG){
				document.forms[0].FIRUserData.value=Trim(obj.value);
				document.getElementById('biometricCapture').style.display='block';
				document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Please Plug in the Biometric Device to the System and then Click the Icon or Verify Button to Scan Your Fingerprint.</font>";
				fnCaptureBiometricImpressions();
			}else{
				document.getElementById('btnVerify').disabled=false;
				document.forms[0].FIRUserData.value=Trim(obj.value);
				isIfInBiometricExceptionList();
			}
		} */ 
	//} 
	/* function fnCaptureBiometricImpressions() {
		//alert('fnCaptureBiometricImpressions');
		//if(capture()){
		//	validateBiometricImpressions();
		//}
		/* Start : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 
		if(isEmployee&&'Y'==BIOMETRIC_AUTHENTICATION_FLAG){*/
		//if('Y'==BIOMETRIC_AUTHENTICATION_FLAG){
		/* End : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
			/* if(document.forms[0].FIRUserData.value!=''){
				var w=130,h=120;				
				var left = (screen.width/2)-(w);
				var top = (screen.height/2)-(h);									
				var popupwindow=window.open("/DJBSSO/BiometricScanner.jsp", "_blank", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width="+w+", height="+h+", top="+top+", left="+left);
				popupwindow.focus();
			}else{
				document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Enter Mandatory fields</font>";
			}
		}else{
			document.getElementById('btnVerify').disabled=false;
			isIfInBiometricExceptionList();
		} */
	//} 
	/* function capture() {
		//alert('capture');
        var err;
        // Check ID is not NULL
        if (document.forms[0].FIRUserData.value == '') {
            //alert('Please enter user id !');
        	document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Enter Mandatory fields</font>";
			document.forms[0].username.focus();
            return (false);
        }
        try {
            document.getElementById('biometricCapture').style.display='none';
			document.getElementById('biometricScanning').style.display='block';
            // Exception handling
            // Open device. [AUTO_DETECT]
            // You must open device before capture.
            DEVICE_FDU01 = 2;
            DEVICE_FDU11 = 4;
            DEVICE_AUTO_DETECT = 255;
            var objDevice = document.objNBioBSP.Device;
            var objExtraction = document.objNBioBSP.Extraction;
            objDevice.Open(DEVICE_AUTO_DETECT);
            err = objDevice.ErrorCode; // Get error code
            if (err != 0){ 
                 // Device open failed                
    			 objDevice.Close(DEVICE_AUTO_DETECT);
    		     objExtraction = 0;
                 objDevice = 0;
                 if(!isIfInBiometricExceptionList()){
	                //alert('Device open failed !');
	    			document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Failed to Open Device! Please Plug in the Biometric Device properly to the System and then Try again.</font>";
	    			document.getElementById('biometricCapture').style.display='block';
	    			document.getElementById('biometricScanning').style.display='none';
                 }else{
                	 fnBiometricVerified();
                 }
	             return (false);
            }
            // Capture user's fingerprint.
            objExtraction.Capture();
            err = objExtraction.ErrorCode; // Get error code
            if (err != 0){
                 // Capture failed
                 objDevice.Close(DEVICE_AUTO_DETECT);
	    		 objExtraction = 0;
	             objDevice = 0;
                 if(!isIfInBiometricExceptionList()){
	                //alert('Sorry Capturing failed ! Error Number : [' + err + ']');
	                //alert('Sorry Capturing failed ! Please Retry');
	                document.getElementById('errMsgDiv').innerHTML = "<font color='red'>There was a Problem, Please Try again.</font>";
	                document.getElementById('biometricCapture').style.display='block';
	    			document.getElementById('biometricScanning').style.display='none';	
                 }else{
                	 fnBiometricVerified();
                 }
	             return (false);
            }else{ 
                // Capture success
                // Get text encoded FIR data from NBioBSP module.
                document.forms[0].FIRTextData.value = objExtraction.TextEncodeFIR;
                //alert('Capture success !');
                document.getElementById('biometricCapture').style.display='none';
    			document.getElementById('biometricScanning').style.display='block';
                document.getElementById('errMsgDiv').innerHTML = "<font color='green'>Scan Success. Please wait while Matching your Fingerprint.</font>";
            }
            // Close device. [AUTO_DETECT]
            objDevice.Close(DEVICE_AUTO_DETECT);
            objExtraction = 0;
            objDevice = 0;
        } // end try
        catch (e) {
            //If exception occur
            objDevice.Close(DEVICE_AUTO_DETECT);
    		objExtraction = 0;
            objDevice = 0;
            if(!isIfInBiometricExceptionList()){
	            //alert(e.message);
	            document.getElementById('errMsgDiv').innerHTML = "<font color='red'>No Device Found ! Please Plug in the Biometric Device to the System and then Try again.</font>";
	        }else{
	           	fnBiometricVerified(); 
         	}
            return (false);
        }
        return (true);
    } */
   /*  function validateBiometricImpressions(){
        //alert('validateBiometricImpressions');
    	var FIRUserData=Trim(document.forms[0].FIRUserData.value);
		var FIRTextData = Trim(document.forms[0].FIRTextData.value);
		if(FIRTextData!='' &&FIRUserData!=''){
			document.getElementById('biometricCapture').style.display='none';
			document.getElementById('biometricScanSuccess').style.display='none';
			document.getElementById('biometricScanning').style.display='block';
			var ajaxResponse="";
			$.post('/DJBSSO/biometricAuthenticatorServlet.do', {
				FIRUserData : FIRUserData,
				FIRTextData:FIRTextData,
				randomKey:Math.random()*99999
			}, function(responseText) {
				ajaxResponse=responseText;
				//alert(ajaxResponse);
				if(ajaxResponse=='success'){
					document.getElementById('errMsgDiv').innerHTML="";
					document.getElementById('biometricCapture').style.display='none';
					document.getElementById('username').readOnly=true;
					document.getElementById('biometricScanning').style.display='none';
					document.getElementById('biometricScanSuccess').style.display='block';
					document.forms[0].FIRTextData.value="";
					fnBiometricVerified();
				}else if(ajaxResponse=='fail'){
					document.getElementById('errMsgDiv').innerHTML = "<font color='red'>Sorry ! could not find the Match for your Fingerprint, Please Retry.</font>";
					//alert('Sorry could not find the Match for your Fingerprint, Please  Retry.');
					//window.location.href=requestURL;
					document.getElementById('biometricCapture').style.display='block';
					document.getElementById('biometricScanning').style.display='none';
					document.getElementById('biometricScanSuccess').style.display='none';
				}else{
					document.getElementById('errMsgDiv').innerHTML = "<font color='red'>There was a problem while processing, Please  Retry after some time.\nIf problem persist please contact System Administrator.</font>";
					//alert('There was a problem while processing, Please  Retry after some time.\nIf problem persist please contact System Administrator.');
					//window.location.href=requestURL;
					document.getElementById('biometricCapture').style.display='block';
					document.getElementById('biometricScanning').style.display='none';
					document.getElementById('biometricScanSuccess').style.display='none';
				}
			});
		}else if(FIRUserData!=''){
			isIfInBiometricExceptionList();
		}
    } */
    /* function isIfInBiometricExceptionList(){
    	//alert('isIfInBiometricExceptionList');
    	var FIRUserData=Trim(document.forms[0].FIRUserData.value);
		var FIRTextData = "";
		if(FIRUserData!=''){
			var ajaxResponse="";
			$.post('/DJBSSO/biometricAuthenticatorServlet.do', {
				FIRUserData : FIRUserData,
				FIRTextData:FIRTextData,
				randomKey:Math.random()*99999
			}, function(data,status) {
				ajaxResponse=data;
				//alert(ajaxResponse+'<>'+status);
				if(ajaxResponse=='success'){				
					document.forms[0].FIRTextData.value="";
					document.getElementById('errMsgDiv').innerHTML = "";
					fnBiometricVerified(); 
					return true;
				}else{
					return false;
				}
			});
		}
    } */
   /*  function fnBiometricVerified(){
    	/* 	Start : Commented by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 
        if(isEmployee){
        	End : Commented by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
	    	//alert('fnBiometricVerified');
	    	/* document.getElementById('username').readOnly = true;
	    	document.getElementById('btnVerify').disabled=true;
	    	document.getElementById('btnVerify').style.display='none';
	    	//document.getElementById('btnReset').disabled=true;
	    	document.getElementById('biometricCapture').style.display='none';
	    	document.getElementById('biometricScanning').style.display='none';
	    	document.getElementById('biometricScanSuccess').style.display='block';
	    	document.getElementById('btnSubmit').style.display='block';
	    	document.getElementById('btnSubmit').disabled=false;
	    	document.getElementById('password').disabled=false;
	    	document.getElementById('password').value='';
	    	document.getElementById('password').focus(); */
	    	//document.body.style.cursor = 'wait';
			//document.forms[0].submit();
			//alert(document.getElementById('username').value+"<>"+document.forms[0].username.value);		
        /* 	Start : Commented by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 
        }	
    	 	End : Commented by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */	
   // } */
    function EnableUserId(){
        if(document.getElementById('declaration').checked){
    	    document.getElementById('username').disabled=false;
        }else{
        	document.getElementById('username').value='';
        	document.getElementById('username').disabled=true;
        }
    }
</script>
</head>
<body oncontextmenu="return false;" onload="fnOnLoad();">
<%@include file="../jsp/CommonOverlay.html"%> 
<table class="djbmain" id="djbmaintable" title="Please login to continue..">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage" border="0">				
				<tr style="height:25px;">
					<td align="center" valign="top">
						<div class="header">
							<table width="100%" align="center" border="0" cellpadding="1px" cellspacing="0">
								<tr>
									<td align="left" width="1%" valign="top">
										<img src="/DataEntryApp/images/logo.png" alt="Logo" border="0"/>
									</td>
									<td align="left" width="20%" valign="top">
										<div class="header-rms">&nbsp;Revenue Management System<br />
										&nbsp;Delhi Jal Board</div>
										</td>
									<td align="right" valign="top">
										<div class="toplinks"><a
											href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=AboutRMS">About
										RMS</a><a
											href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=ContactUs">Contact
										Us</a> <a
											href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=Sitemap">Sitemap</a>
										<a
											href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=faq">FAQ</a>&nbsp;&nbsp;
										</div>
									</td>
								</tr>
							</table>
						</div>												
					</td>
				</tr>
				<tr>
					<td align="left">						
						<table width="98%" align="center" border="0">
							<tr>
								<td align="center">	
									<table class="tab-table" cellpadding="0px" cellspacing="0px" border="0">
										<tr>
											<td align="center">
												<div class="centeredmenu">
												   <ul>												   	  	
												      	<li><a href="#" id="tab1" onclick="javascript:setStatusBarMessage('Login with Data Entry User ID and Password');" title="You must be a registerd Data Entry User.">Data Entry User</a></li>
												      	<li><a href="#" id="tab2" onclick="javascript:setStatusBarMessage('Login with Employee ID and Password');" title="You must be a registerd DJB Emplyee.">Employee</a></li>
												     <%--  	<li>
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												   	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Please Login <%try{%><%=PropertyUtil.getProperty("SERVER_NAME")%><%}catch(Exception e){} %></b>
												   	  	
												   	  	</li> --%>
												   </ul>
												</div>
											</td>
										</tr>						
										<tr>
											<td align="left" width="100%">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" nowrap >
												<s:form action="javascript:void(0);" method="post" onsubmit=" return fnOnSubmit();" theme="simple">
												<s:hidden name="userRole"/>		
												<input type="hidden" name='FIRTextData' id="FIRTextData"/>
												<input type="hidden" name='FIRUserData' id="FIRUserData"/>																
												<table width="98%" align="center" border="0">
													<tr>
														<td align="right" width="43%">															 								
															User Id
														</td>
														<td align="left" width="14%">															 								
															<s:textfield name="username" id="username" cssClass="textbox" size="20"  title="Please Agree with the Terms & Conditions also"/>
														</td>	
														<td align="left" width="43%" rowspan="5" valign="top">															 								
															<%-- <%if("Y".equals(BIOMETRIC_AUTHENTICATION_FLAG)){ %>
																<div id="biometricCapture" style="display:block;">
																<img src="/DJBSSO/images/prompt_finger_print.gif" height="100px" alt="Click to Scan Your Fingerprint" border="0" onclick="fnCaptureBiometricImpressions();"/></div>
																<div id="biometricScanning" style="display:none;">
																<img src="/DJBSSO/images/scanning.gif" height="100px" alt="Scan in Progress" border="0"/></div>
																<div id="biometricScanSuccess" style="display:none;">
																<img src="/DJBSSO/images/scan_success.gif" height="100px" alt="Scan in Success" border="0"/></div>
																<div id="biometricScanSuccess" style="display: none;">
																<img src="/DJBSSO/images/scan_success.gif" height="100px" alt="Verified" border="0" /> 												
																</div>	
															<%} %>	 --%>
														</td>
													</tr>
													<tr>
														<td align="right" width="43%">															 								
															Password
														</td>
														<td align="left" width="14%">		
															<s:password name="password" id="password"  cssClass="textbox" size="22" title="Please Agree with the Terms & Conditions also" disabled="false"/>
														</td>
													</tr>
													<tr>
														<td align="right" width="43%">
															<input type="button" value="    Reset    " id="btnReset" name="btnReset" class="groovybutton" onclick="fnReset();"/>
														</td>
														<td align="left" width="14%">		
															<input type="button" value="     Verify    " id="btnVerify" name="btnVerify" class="groovybutton" onclick="fnCaptureBiometricImpressions();"/>													
															<s:submit method="execute" id="btnSubmit" key="label.login" align="right" cssClass="groovybutton" title="Please Agree with the Terms & Conditions also" />
														</td>
													</tr>		
													<tr>
														<td align="right" width="43%">&nbsp;</td>
														<td align="center" width="14%">		
															&nbsp;
														</td>
													</tr>		
													<tr>
														<td align="right" width="43%">&nbsp;</td>
														<td align="center" width="14%">		
															&nbsp;
														</td>
													</tr>										
												</table>	
												</s:form>			
											</td>
										</tr>
										<tr>
		                                    <td colspan="8" id="errMsgDiv" style="height:20px;" align="right"></td>
		                                    <td width="10%">&nbsp;</td>
	                                   </tr>
										<!-- Start:Added by Reshma on 28-11-2013 ,JTrac DJB-2049 -->
										<!-- <tr>
		                                    <td colspan="8"><input type="checkbox" id="declaration" name="declaration" /><font size="2" color="red">
		                                    I agree with all RMS - Terms and Conditions<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. I confirm that I have not shared my login credentials (userName and password) with anybody.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. I hereby declare that I am solely accountable for all transactions (additions/modifications/deletions) initiated from my user id.
		                                    </font></td>
		                                    <td width="10%">&nbsp;</td>
	                                   </tr> -->
	                                   <!-- End:Added by Reshma on 28-11-2013 ,JTrac DJB-2049 -->
										<tr>
											<td align="right">
												&nbsp;
											</td>
										</tr>										
									</table>
								</td>
							</tr>
						</table><br/>
						<font color="red" size="2"><s:actionerror /></font>
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
<SCRIPT type="text/javascript">
var lastLoginType="<%=session.getAttribute("LOGIN_TYPE")%>";

//$(document).ready(function(){
	/* Start : Edited by Arnab Nandy (1227971) on 30-05-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	//fnEnableBiometric();
	/* if(Trim(document.forms[0].userRole.value)=='Employee'||Trim(lastLoginType)=='Employee'){
		$("#tab2").addClass("active");
	}else{
		$("#tab1").addClass("active");
	} */
	/* 
	if(Trim(document.forms[0].userRole.value)=='Employee'||Trim(lastLoginType)=='Employee'){
		$("#tab2").addClass("active");
		fnEnableBiometric();
	}else{
		$("#tab1").addClass("active");
		fnDisableBiometric();
	}
	End : Edited by Arnab Nandy (1227971) on 30-05-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	//$("a").click(function (){
			//$(".active").removeClass("active");
	      	//$(this).addClass("active");
	      //	document.forms[0].userRole.value=$(this).html();
	      	/* Start : Edited by Arnab Nandy (1227971) on 30-05-2016 for Two-factor authentication of Data Entry 
    		Users, according to JTrac DJB-4464 and OpenProject#1151 */
    		//fnEnableBiometric();
    		/* if(Trim(document.forms[0].userRole.value)=='Employee'){
	      		fnEnableBiometric();
	      	}else{
	    		fnDisableBiometric();
	      	}
	      	End : Edited by Arnab Nandy (1227971) on 30-05-2016 for Two-factor authentication of Data Entry 
    		Users, according to JTrac DJB-4464 and OpenProject#1151 */
	  //}); 
	/* $('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});
	$('input[type="password"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="password"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});
}); */
/* function fnEnableBiometric(){
	//alert('fnEnableBiometric');
	document.forms[0].password.disabled=true;
	document.getElementById('biometricCapture').style.display='block';
	document.getElementById('biometricScanning').style.display='none';
	document.getElementById('biometricScanSuccess').style.display='none';
	document.getElementById('btnSubmit').style.display='none';
	document.getElementById('btnSubmit').disabled=true;
	document.getElementById('btnVerify').style.display='block';
	document.getElementById('username').readOnly=false;
	document.getElementById('btnVerify').disabled=false;
	document.getElementById('password').disabled=true;
	document.getElementById('btnReset').disabled=false;
	document.forms[0].username.value="";
	document.forms[0].password.value="";
	isEmployee=true;
}
function fnDisableBiometric(){
	//alert('fnDisableBiometric');
	document.forms[0].password.disabled=false;
	document.getElementById('biometricCapture').style.display='none';
	document.getElementById('biometricScanning').style.display='none';
	document.getElementById('biometricScanSuccess').style.display='none';
	document.getElementById('btnVerify').style.display='none';
	document.getElementById('btnSubmit').style.display='block';
	document.getElementById('btnSubmit').disabled=false;
	document.getElementById('username').readOnly=false;
	document.getElementById('password').disabled=false;
	document.forms[0].username.value="";
	document.forms[0].password.value="";
	isEmployee=false;
} */
</SCRIPT>
</body>
<%String encFlag="";
try{encFlag=PropertyUtil.getProperty("PWD_ENC_FLAG"); }catch(Exception e){}%>
<script type="text/javascript">var encriptionFlag="<%=encFlag%>"</script>
<%}catch(Exception e){/*e.printStackTrace();*/} %>
</html>

