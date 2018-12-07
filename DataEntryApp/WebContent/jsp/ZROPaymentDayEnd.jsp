<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
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
	<script type="text/javascript" src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
	<script language=javascript>
		function disableBack()
		{
			window.history.forward(1);
		}
	 	disableBack();
 	</script>	
 	<script type="text/javascript">
 		
		
 		function fnOnSubmit(){ 	
 	 		//alert("On submit");		
 			fnProcessData();
 			return true;
 		}
 		
 		
 	
 		document.onkeydown = function(evt) {
 		     evt = evt || window.event;     
 		     if (evt.keyCode == 27) {  	 		    	
 		    	showScreen(); 
 		    	goHome();    
	 		 } 
 		  }; 	

	function fnValidateTCID(){ 
		document.getElementById('ajax-result').innerHTML ="<div class='search-option' title='Server Message'>&nbsp;</div>";
		var tcid=Trim(document.getElementById('tcid').value);
	 		if(tcid.length==10){
	 			document.getElementById('invalidTCIDSpan').style.display="none";	
			document.getElementById('validTCIDSpan').style.display="none";	
	 			if(isNaN(tcid)){
					document.getElementById('TCIDSpan').innerHTML = "<font color='red'>Tender Control ID should be a 10 digits Numeric Value.</font>";				
					document.getElementById('usID').disabled=true;
					document.getElementById('deviceID').disabled=true; 					
					document.getElementById('tcid').focus();
	 			}else{  
 	 			httpObject = null;
 	 			httpObject=getHTTPObjectForBrowser(); 
				var url= "ZROPaymentDayEndAJax.action?hidAction=validateTenderControlID";
				url+="&tdCID="+tcid;							
				if (httpObject != null) {
					hideScreen();							
					
					var httpBowserObject=null;
					if (window.ActiveXObject){                
						httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
					}else if (window.XMLHttpRequest){ 
						httpBowserObject= new XMLHttpRequest();  
					}else {                 
						alert("Browser does not support AJAX...........");             
						return;
					} 

					if (httpBowserObject != null) {											            
						httpBowserObject.open("POST", url, true);
						httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
						httpBowserObject.setRequestHeader("Connection", "close");
						httpBowserObject.onreadystatechange = function(){
							if(httpBowserObject.readyState == 4){
							
								var  responseString= httpBowserObject.responseText;
								//alert(responseString);

								if(null!=responseString && responseString.indexOf('<script')==-1){
									if(null!=responseString && responseString.indexOf("ERROR:")>-1){
										
										document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";	
										document.getElementById('invalidTCIDSpan').style.display="block";
										document.getElementById('usID').disabled=true;
										document.getElementById('deviceID').disabled=true;										
										document.getElementById('btnProcess').disabled=true;
										document.getElementById('TCIDSpan').innerHTML = "";							
										showScreen(); 
									}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){												
										document.getElementById('validTCIDSpan').style.display="block";
										document.getElementById('usID').disabled=false;
										document.getElementById('deviceID').disabled=false;										
										document.getElementById('btnProcess').disabled=false;
										document.getElementById('TCIDSpan').innerHTML = "";												
										showScreen(); 
									}else{
										alert('Sorry ! \nThere was a problem');
										showScreen();
									}
								}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){									
									document.getElementById('successResult').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
									showScreen();
								}else{
									document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
									showScreen();
								}  
								
							}     
						};  
						httpBowserObject.send(null);
					}
				}	
	 		} 
	}

	}
	

	function fnValidateDeviceID(){ 	
		document.getElementById('ajax-result').innerHTML ="<div class='search-option' title='Server Message'>&nbsp;</div>";
		var deviceID=Trim(document.getElementById('deviceID').value);
	 		if(deviceID.length==8){
	 			document.getElementById('invalidDeviceIDSpan').style.display="none";	
			document.getElementById('validDeviceIDSpan').style.display="none";	
	 			if(deviceID){
				  
 	 			httpObject = null;
 	 			httpObject=getHTTPObjectForBrowser(); 
				var url= "ZROPaymentDayEndAJax.action?hidAction=validateDeviceID";
				url+="&validateDeviceID="+deviceID;
				//alert("url"+url);							
				if (httpObject != null) {
					hideScreen();							
					
					var httpBowserObject=null;
					if (window.ActiveXObject){                
						httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
					}else if (window.XMLHttpRequest){ 
						httpBowserObject= new XMLHttpRequest();  
					}else {                 
						alert("Browser does not support AJAX...........");             
						return;
					} 

					if (httpBowserObject != null) {											            
						httpBowserObject.open("POST", url, true);
						httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
						httpBowserObject.setRequestHeader("Connection", "close");
						httpBowserObject.onreadystatechange = function(){
							if(httpBowserObject.readyState == 4){							
								var  responseString= httpBowserObject.responseText;
								//alert(responseString);

								if(null!=responseString && responseString.indexOf('<script')==-1){
									if(null!=responseString && responseString.indexOf("ERROR:")>-1){
										document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";												
										document.getElementById('invalidDeviceIDSpan').style.display="block";
										document.getElementById('usID').disabled=true;
										document.getElementById('deviceID').disabled=true;										
										document.getElementById('btnProcess').disabled=true;
										document.getElementById('DeviceIDSpan').innerHTML = "";							
										showScreen(); 
									}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){	
										document.getElementById('validDeviceIDSpan').style.display="block";
										document.getElementById('usID').disabled=false;
										document.getElementById('deviceID').disabled=false;										
										document.getElementById('btnProcess').disabled=false;
										document.getElementById('DeviceIDSpan').innerHTML = "";													
										showScreen(); 
									}else{
										alert('Sorry ! \nThere was a problem');
										showScreen();
									}
								}else{
									document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
									showScreen();
								}  
								
							}     
						};  
						httpBowserObject.send(null);
					}
				}	
	 		
	}

	}
	}
	 		function fnValidateUserID(){ 	
		 	        document.getElementById('ajax-result').innerHTML ="<div class='search-option' title='Server Message'>&nbsp;</div>";
	 			     var userID=Trim(document.getElementById('usID').value);
	 		 			document.getElementById('invalidUserIDSpan').style.display="none";	
	 				document.getElementById('validUserIDSpan').style.display="none";	
	 		 			if(userID){	 					  
	 	 	 			httpObject = null;
	 	 	 			httpObject=getHTTPObjectForBrowser(); 
	 					var url= "ZROPaymentDayEndAJax.action?hidAction=validateUserID";
	 					url+="&validateUserID="+userID;
	 					//alert("url"+url);							
	 					if (httpObject != null) {
	 						hideScreen();							
	 						
	 						var httpBowserObject=null;
	 						if (window.ActiveXObject){                
	 							httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
	 						}else if (window.XMLHttpRequest){ 
	 							httpBowserObject= new XMLHttpRequest();  
	 						}else {                 
	 							alert("Browser does not support AJAX...........");             
	 							return;
	 						} 

	 						if (httpBowserObject != null) {											            
	 							httpBowserObject.open("POST", url, true);
	 							httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	 							httpBowserObject.setRequestHeader("Connection", "close");
	 							httpBowserObject.onreadystatechange = function(){
	 								if(httpBowserObject.readyState == 4){							
	 									var  responseString= httpBowserObject.responseText;
	 									//alert(responseString);

	 									if(null!=responseString && responseString.indexOf('<script')==-1){
	 										if(null!=responseString && responseString.indexOf("ERROR:")>-1){
	 											document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";												
	 											document.getElementById('invalidUserIDSpan').style.display="block";
	 											document.getElementById('usID').disabled=true;
	 											document.getElementById('deviceID').disabled=true;										
	 											document.getElementById('btnProcess').disabled=true;
	 											document.getElementById('UserIDSpan').innerHTML = "";							
	 											showScreen(); 
	 										}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){	
	 											document.getElementById('validUserIDSpan').style.display="block";
	 											document.getElementById('usID').disabled=false;
	 											document.getElementById('deviceID').disabled=false;										
	 											document.getElementById('btnProcess').disabled=false;
	 											document.getElementById('UserIDSpan').innerHTML = "";
	 														
	 											showScreen(); 
	 										}else{
	 											alert('Sorry ! \nThere was a problem');
	 											showScreen();
	 										}
	 									}else{
	 										document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
	 										showScreen();
	 									}  
	 									
	 								}     
	 							};  
	 							httpBowserObject.send(null);
	 						}
	 					}	
	 		 		
	 		}
			}		 		
	 		

	 		
	//=======================Ajax Call to Process Data start===============================   
	function fnProcessData(){
		if(validateData()){  
			
			var tenderConID=Trim(document.getElementById('tcid').value);
			var usID=Trim(document.getElementById('usID').value);
			var deviceID=Trim(document.getElementById('deviceID').value);
			
 	 		if(tenderConID.length==10){
 	 			httpObject = null;
 	 			httpObject=getHTTPObjectForBrowser();
 	 			
				var url= "ZROPaymentDayEndCCBServiceAJax.action?hidAction=processData&tenderConID="+tenderConID+"&userID="+usID+"&deviceID="+deviceID;
				  				
				if (httpObject != null) {
					hideScreen();								
					// alert('url::'+url); 
					httpObject.onreadystatechange =function(){         
						if(httpObject.readyState == 4){
							var  responseString= httpObject.responseText;
								if(null!=responseString && responseString.indexOf('<script')==-1){
								if(null!=responseString && responseString.indexOf("ERROR:")>-1){
									document.getElementById('ajax-result').innerHTML = httpObject.responseText;		
									showScreen(); 
								}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){					
									document.getElementById('ajax-result').innerHTML = httpObject.responseText;
									document.getElementById('btnProcess').disabled=true;	
									showScreen(); 
								}else{
									alert('Sorry ! \nThere was a problem');
									showScreen();
								}
							} else{
								document.getElementById('successResult').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
								showScreen();
							}  
						}     
					} ;                
					httpObject.open("POST", url, true);            
					httpObject.send(null);    
					
				}  
 	 		}
		} 
	} 
	
	//=======================Ajax Call to Process Data End=================================
		//Reset all fields
 		function fnReset(){
 			document.getElementById('tcid').value="";
 			document.getElementById('usID').value="";
			document.getElementById('deviceID').value="";					
			document.getElementById('validTCIDSpan').style.display="none";	
			document.getElementById('invalidTCIDSpan').style.display="none";
			document.getElementById('TCIDSpan').style.display="none";			
			document.getElementById('validUserIDSpan').style.display="none";	
			document.getElementById('invalidUserIDSpan').style.display="none";
			document.getElementById('UserIDSpan').style.display="none";
			document.getElementById('validDeviceIDSpan').style.display="none";	
			document.getElementById('invalidDeviceIDSpan').style.display="none";
			document.getElementById('DeviceIDSpan').style.display="none";
			document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
 		}
	//Validations
 		function validateData(){
 			var tcid=Trim(document.getElementById('tcid').value);
			var usID=Trim(document.getElementById('usID').value);
			var deviceID=Trim(document.getElementById('deviceID').value);
						
			if(tcid.length==0){
				document.getElementById('TCIDSpan').innerHTML = "<font color='red'>Tender Control ID is mandatory.</font>";
				document.getElementById('tcid').focus();
				return false;
			}
			if(tcid.length!=10 ||isNaN(tcid)){
				document.getElementById('TCIDSpan').innerHTML = "<font color='red'>Tender Control ID should be a 10 digits Numeric Value.</font>";
				document.getElementById('tcid').focus();
				return false;
			}		
			
			if(usID.length==0){								
				document.getElementById('paymentDayEndSpan').innerHTML = "<font color='red'>User ID is mandatory</font>";
				document.getElementById('usID').focus();
				return false;
			}	

			if(deviceID.length==0){				
				document.getElementById('deviceIdSpan').innerHTML = "<font color='red'>Device ID is mandatory</font>";
				document.getElementById('usID').focus();
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
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%> 
					</td>
				</tr>				
				<tr height="20px">
					<td align="center" valign="top"><%@include
						file="../jsp/TopMenu.html"%></td>
				</tr>
				<tr height="20px">
					<td align="left" valign="top">
						<div id="ajax-result" style="display:block;text-align:left;height:10px;" title="Server Message"><div class='search-option'>&nbsp;<font color="red" size="2"><s:actionerror /></font></div></div>
					</td>
				</tr>	
				<tr>
					<td align="center" valign="top">
						<div id="header">
						<table width="100%" align="center" border="0" >							
							<tr>
								<td align="center"  valign="top">
									<fieldset>
										<legend><span>ZRO Payment DayEnd</span></legend>						
										<table width="95%" align="center" border="0">																				
											<tr>
												<td align="center" colspan="3">
													<form action="javascript:void(0);" method="post" onsubmit=" return fnOnSubmit();">
														<input type="hidden" name="hidAction" id="hidAction"/>	
														
														<table border="0" width="98%" align="center">	
															<tr title="Enter Tender Control ID.">
																<td align="left" width="35%">
																	Tender Control ID<font color="red" size="2"><sup>*</sup></font>
																</td>
																<td align="left" width="15%">											
																	<input type="text" name="tcid" id="tcid" class="textbox" size="20" maxlength="10" autocomplete="off" onblur="fnValidateTCID(this);"/>
																</td>
																
																<td align="left" width="50%">											
																	<span id="validTCIDSpan" style="display:none;"/><img src="/DataEntryApp/images/valid.gif"  border="0" title="Valid TCID"/></span><span id="invalidTCIDSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid TCID"/></span><span id="TCIDSpan"></span>
																</td>	
															</tr>
															
															<tr title="Enter user ID.">
																<td align="left"  width="35%" nowrap>
																	User ID<font color="red" size="2"><sup>*</sup></font><font size="0.1em"></font>
																</td>
																<td align="left"  width="15%">	
																	<span id="userID" title="Please enter user ID." /></span>
																	<input type="text" name="usID" id="usID"  class="textbox" size="20" maxlength="11"  autocomplete="off" disabled="true" onblur="fnValidateUserID(this);"/>
																</td>
																<td align="left" width="50%">											
																	<span id="validUserIDSpan" style="display:none;"/><img src="/DataEntryApp/images/valid.gif"  border="0" title="Valid UserID"/></span><span id="invalidUserIDSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid UserID"/></span><span id="UserIDSpan"></span>
																</td>
															</tr>														
															<tr title="Please Enter Device ID" >
																<td align="left" width="35%">
																	Device ID<font color="red" size="2"><sup>*</sup></font>
																</td>
																<td align="left"  width="15%">
																	<input type="text" name="deviceID" id="deviceID" class="textbox" size="20" maxlength="10" disabled="true" onblur="fnValidateDeviceID(this);"/>
																</td>
																<td align="left" width="50%">											
																	<span id="validDeviceIDSpan" style="display:none;"/><img src="/DataEntryApp/images/valid.gif"  border="0" title="Valid DeviceID"/></span><span id="invalidDeviceIDSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid DeviceID"/></span><span id="DeviceIDSpan"></span>
																</td>																
															</tr>
															<tr>	
															<td align="center"  width="35%">
																	<input type="button" id="btnReset" value="Reset All" class="groovybutton" onclick="fnReset();"/>
																</td>															
																<td align="left" colspan="2"  width="65%">	
																	<input type="submit" id="btnProcess" value="Process DayEnd" class="groovybutton"/>
																</td>
															</tr>	
																<tr>	
															<td>											
																</td>															
																<td align="left">	
																	<span id="successResult"/></span>
																</td>
															</tr>
														</table>										
													</form>
												</td>
											</tr>
											<tr>
												<td align="right" colspan="3">
													<font color="red" size="2"><sup>*</sup></font>All fields are mandatory.
												</td>
											</tr>	
										</table>									
									</fieldset>
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
<SCRIPT type="text/javascript">
$(document).ready(function(){	
	$('input[type="text"]').focus(function() { 
		$(this).addClass("textboxfocus"); 
	});   
	$('input[type="text"]').blur(function() {  					
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox"); 
	});				  
});
</SCRIPT>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
</html>