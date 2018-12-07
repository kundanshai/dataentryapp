<!-- 
*@history on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
*@history on 12-09-2016 Lovely (986018)added validations for waste water treatment flag update option as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
-->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%
	try {
%>
<head>
<title>Rain Water Harvesting Tagging Form - Revenue Management
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
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/jQuery/css/jquery.ui.all.css"/>" />
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.core.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
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
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery.progressbar.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/jquery.progressbar.css"/>" />
<script>
     $(function() {
    	 var todaysDate = new Date();
         var maxDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate());
		$( "#impDate" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true,
			maxDate: maxDate,
			dateFormat: 'yy-mm-dd'
		});
});
     $(function() {
    	 var todaysDate = new Date();
         var maxDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate());
  		$( "#wasteimpDate" ).datepicker({
  			//numberOfMonths: 3,
  			showButtonPanel: true,
  			maxDate: maxDate,
  			dateFormat: 'yy-mm-dd'
  		});
 });  
     $(function() {
    	 var todaysDate = new Date();
         var maxDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate());
  		$( "#diaryDate" ).datepicker({
  			//numberOfMonths: 3,
  			showButtonPanel: true,
  			maxDate: maxDate,
  			dateFormat: 'yy-mm-dd'
  		});
 });  
</script>
<script type="text/javascript">
       var isPopUp = false;
       var customerClassCode = '';
       /* START: Rajib Added as per Jtrac DJB-4096 for popup of Re-Confirm password screen on load of Rain water harvesting screen */
       window.onload =function(){
    	   isPopUp ="<%=session.getAttribute("isPopUp")%>";
    	   // alert (isPopUp);
    	   if ('null' == isPopUp){
         	fnGotoReConfirmPasswordScreen();
         	if (null != document.getElementById('rainWaterScreen'))
   			{
   				document.getElementById('rainWaterScreen').style.display = "none";
			}
         	<%session.removeAttribute("isPopUp"); %>
           }else{
        	   if (null != document.getElementById('rainWaterScreen'))
      			{
      				document.getElementById('rainWaterScreen').style.display = "block";
   				}
               }
       };
       function fnGotoReConfirmPasswordScreen(){
   		popUpReConfirmPasswordScreen();
   		isPopUp=true;
   }
       function popUpReConfirmPasswordScreen() {
   		if (null != document.getElementById('hhdAdd-box')
   				&& 'null' != document.getElementById('hhdAdd-box')) {
   			if (null != document.getElementById('djbmaintable')
   					&& 'null' != document.getElementById('djbmaintable')) {
   				var theTable = document.getElementById('djbmaintable');
   				theTable.className = "djbmainhidden";
   			}
   			document.body.style.overflow = 'hidden';
   			var thediv = document.getElementById('hhdAdd-box');
   			thediv.style.display = "block";
   			thediv = document.getElementById('hhdAdd-box-field');
   			thediv.style.display = "block";
   			if (null != document.getElementById('search-option'))
   			{
   				document.getElementById('search-option').style.display = "none";
			}
   			isProcessing=false;
   		   }
   	  }
 // START: Rajib Added as per Jtrac DJB-4096 for popup of Re-Confirm password screen on load of Rain water harvesting screen   
  function validateReConfirmPasswordAjaxCall(passwordVal){
    		if (null !=password && Trim(password) !='') {
    		document.getElementById('hhdAddMsg').innerHTML = "<font color='green' size='2'>Validating Password</font><img src=\"/DataEntryApp/images/load.gif\" width=\"25\" border=\"0\" title=\"Loading\" id=\"imgLoadingHistory\" />";
    			$.post("rainWaterHarvestingAJax.action",
    							{
    								hidAction : "reConfirmPassword",
    								password : passwordVal
    								},
    							function(data, status) {
    							// alert("Status: " + status);
    		                if (null != data && ''!= Trim(data)) {
    								if ("SUCCESS" == data){
    	                		document.body.style.overflow = 'hidden';
    	            			document.getElementById('hhdAdd-box').style.display = "none";
    	            			document.getElementById('hhdAdd-box-field').style.display = "none";
    	            			document.getElementById('rainWaterScreen').style.display = "block";
    	                    	}
    	                	else{
    	                		document.body.style.overflow = 'hidden';
    	            			document.getElementById('hhdAdd-box').style.display = "block";
    	            			document.getElementById('hhdAdd-box-field').style.display = "block";
    	            			document.getElementById('rainWaterScreen').style.display = "none";
    	            			document.getElementById('hhdAddMsg').innerHTML = "";
    	                		document.getElementById('hhdAddMsg').innerHTML = "<font color='red' size='2'>Password Entered is not Valid, please Re-Enter Correct Password.</font>";
    	                    	}
    							} 
    						});
    		            }
    	}
    //END: Rajib Added as per Jtrac DJB-4096 for popup of Re-Confirm password screen on load of Rain water harvesting screen     
       function disableBack(){
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
        function fnGetCustClass(){
    	   var kno= document.getElementById('kno').value;
           //var customerClassCode = '';
    	   if(null !=kno && Trim(kno !='' )){
		 		var url = "rainWaterHarvestingAJax.action?hidAction=custClass&kno="
					+ kno;
		 		//alert("url>>"+url);
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
					document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>Validating KNO.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					//alert("state::"+httpBowserObject.onreadystatechange);
					httpBowserObject.onreadystatechange = function() {
						//alert(httpBowserObject.readyState);
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							document.getElementById('onscreenMessage').innerHTML="";
  					customerClassCode = responseString;
                    //alert(customerClassCode);
                   // Start : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
                    if(null != customerClassCode && ''!=Trim(customerClassCode)){
                        /* if('Others'==Trim(customerClassCode)){
                    		document.getElementById('currentWWTF').disabled = true;
                    		//document.getElementById('currentWWHF').value = 'N/A';
                    		document.getElementById('wy1').disabled = true;
                    		document.getElementById('wn1').disabled = true;
                    		document.getElementById('wasteimpDate').disabled = true;
                    		//document.getElementById('diaryNo').disabled = true;
                        	}
                    	else{
                    		document.getElementById('currentWWTF').disabled = true;
                    		//document.getElementById('currentWWHF').value = '';
                    		document.getElementById('wy1').disabled = false;
                    		document.getElementById('wn1').disabled = false;
                    		document.getElementById('wasteimpDate').disabled = false;
                    		document.getElementById('diaryNo').disabled = false;
                        	}*/
                    	document.getElementById('currentWWTF').disabled = true;
                		document.getElementById('wy1').disabled = false;
                		document.getElementById('wn1').disabled = false;
                		document.getElementById('wasteimpDate').disabled = false;
                		document.getElementById('diaryNo').disabled = false;
                    }
                     // End : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
                       
					//isValid = responseString.substring(responseString
					//.indexOf("<KNOSTATUS>") + 11, responseString.indexOf("</KNOSTATUS>"));
					//consumername = responseString.substring(responseString.indexOf("<CONSNAME>") + 10, responseString.indexOf("</CONSNAME>"));
					 //rwhStatus = responseString.substring(responseString.indexOf("<RWH_STATUS>") + 12, responseString.indexOf("</RWH_STATUS>"));
				     showScreen();
 					}
				};
				httpBowserObject.send(null);
				}	
			}
           }
	 	function fnCheckKNO(){
		 	var kno= document.getElementById('kno').value;
		 	var isValid='';
		 	var errorMessage='';
		 	var consumername= '';
		 	var rwhStatus='';
		 	var wwtStatus='';
		 	var apprvdBy= "<%= session.getAttribute("userId") %>";
    	  // alert("apprvdBy>>"+apprvdBy);
    	   document.getElementById('approvedBy').value = apprvdBy;  
 		 	if(null != rwhStatus && ''!=Trim(rwhStatus)){
		 	document.getElementById('approvedBy').value= document.getElementById('hidApprovedBy').value;
		 	}
 		 	document.getElementById('invalidKNOSpan').style.display="none";	
		    document.getElementById('validKNOSpan').style.display="none";
  		 	//alert("kno::"+kno);
	 		if(null!=kno &&  Trim(kno !='' )){
		 		var url = "rainWaterHarvestingAJax.action?hidAction=checkKNO&kno="
					+ kno;
		 		//alert("url>>"+url);
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
					document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>Validating KNO.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					//alert("state::"+httpBowserObject.onreadystatechange);
					httpBowserObject.onreadystatechange = function() {
						//alert(httpBowserObject.readyState);
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							document.getElementById('onscreenMessage').innerHTML="";
							if (null == responseString || responseString.indexOf("<ERROR>") > -1) {
							 isValid = responseString.substring(responseString
					.indexOf("<KNOSTATUS>") + 11, responseString.indexOf("</KNOSTATUS>"));
						    errorMessage = responseString.substring(responseString
					.indexOf("<ERROR>") + 7, responseString.indexOf("</ERROR>"));	
														
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Error for KNO "+ kno + " : " +errorMessage +" </b></font>";
							document.getElementById('onscreenMessage').title =" <font color='red' size='2'><b>ERROR: "+ errorMessage +" </b></font>";
							//document.getElementById('checkKNO').value= isValid;
							document.getElementById('kno').value="";
							document.getElementById('invalidKNOSpan').style.display="block";
							document.getElementById('SubmitRainWH').disabled=true;	
							showScreen();						
						} else {
					isValid = responseString.substring(responseString
					.indexOf("<KNOSTATUS>") + 11, responseString.indexOf("</KNOSTATUS>"));
					 consumername = responseString.substring(responseString.indexOf("<CONSNAME>") + 10, responseString.indexOf("</CONSNAME>"));
					 rwhStatus = responseString.substring(responseString.indexOf("<RWH_STATUS>") + 12, responseString.indexOf("</RWH_STATUS>"));
                     wwtStatus = responseString.substring(responseString.indexOf("<WWT_STATUS>") + 12, responseString.indexOf("</WWT_STATUS>"));
                     //alert(wwtStatus);
					 fnGetCustClass();
 						 document.getElementById('nameOfConsumer').value = consumername;
							if(null != rwhStatus && ''!=Trim(rwhStatus)){
							    if('Y'==Trim(rwhStatus)){
							    document.getElementById('currentRWHF').value= 'YES';
							    
							    }
							else if ('N'==Trim(rwhStatus)) {
							    document.getElementById('currentRWHF').value= 'NO';
							}
							else 
							    document.getElementById('currentRWHF').value= 'DATA NOT AVAILABLE IN SYSTEM';
							}

							if(null != wwtStatus && ''!=Trim(wwtStatus)){
							    if('Y'==Trim(wwtStatus)){
							    document.getElementById('currentWWTF').value= 'YES';
 							    }
							else if ('N'==Trim(wwtStatus)) {
							    document.getElementById('currentWWTF').value= 'NO';
							}
							else 
							    document.getElementById('currentWWTF').value= 'DATA NOT AVAILABLE IN SYSTEM';
							}
  							document.getElementById('validKNOSpan').style.display="block";
							
							//document.getElementById('checkKNO').value='Valid KNO';
							document.getElementById('SubmitRainWH').disabled=false;							
						    showScreen();
						}
					}
				};
				httpBowserObject.send(null);
				}	
			}
	 	}
  function validateRainWaterHarvesting(){
   	//customerClassCode = responseString;
 	if (null != document.getElementById('kno').value && Trim(document.getElementById('kno').value).length == 0) {
		document.getElementById('kno').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter KNO to Proceed.</font>";
		return false;
	}
 	if (null != document.getElementById('nameOfConsumer').value && Trim(document.getElementById('nameOfConsumer').value).length == 0) {
		document.getElementById('nameOfConsumer').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
 	if (null != document.getElementById('currentRWHF').value && Trim(document.getElementById('currentRWHF').value).length == 0) {
		document.getElementById('currentRWHF').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
/*
	if (Trim(document.getElementById('y1').value).length == 0 && Trim(document.getElementById('n1').value).length == 0) {
		document.getElementById('y1').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Select Rain Water Harvest Installed Or Not to Proceed.</font>";
		return false;
	}
*/
    if(!document.getElementById('y1').checked && !document.getElementById('n1').checked){
    	document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Implementation Date to Proceed.</font>";
    	return false;
    	}
      if (null != document.getElementById('impDate').value && Trim(document.getElementById('impDate').value).length == 0) {
		document.getElementById('impDate').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Implementation Date to Proceed.</font>";
		return false;
	}else{
//START:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
		 var enteredDate = $("#impDate").val(); // For JQuery
         if (compDtwithToday(enteredDate)){
        	 alert("Implementation date Can't be in future ");
             document.getElementById('impDate').focus();
     		// document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Implementation Date can't be in future!</font>";
     		 return false;
             }
		}
//END:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
    if (null != document.getElementById('diaryDate').value && Trim(document.getElementById('diaryDate').value).length == 0) {
		document.getElementById('diaryDate').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Implementation Date to Proceed.</font>";
		return false;
	}else{
//START:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
		 var dryDate = $("#diaryDate").val(); // For JQuery
         if (compDtwithToday(dryDate)){
        	 alert("Diary date Can't be in future ");
             document.getElementById('diaryDate').focus();
     		// document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Implementation Date can't be in future!</font>";
     		 return false;
             }
		}
//END:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.	
	if (null != document.getElementById('documentNo').value && Trim(document.getElementById('documentNo').value).length == 0) {
		document.getElementById('documentNo').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Document No to Proceed.</font>";
		return false;
	}
	if (null != document.getElementById('approvedBy').value && Trim(document.getElementById('approvedBy').value).length == 0) {
		document.getElementById('approvedBy').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
 	if (null != document.getElementById('documentOfProof').value && Trim(document.getElementById('documentOfProof').value).length == 0) {
		document.getElementById('documentOfProof').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Upload Document Of Proof to Proceed.</font>";
		return false;
	}
 	if (null != document.getElementById('diaryNo').value && Trim(document.getElementById('diaryNo').value).length == 0) {
		document.getElementById('diaryNo').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Diary No to Proceed.</font>";
		return false;
	}
	if (null != document.getElementById('comments').value && Trim(document.getElementById('comments').value).length == 0) {
		document.getElementById('comments').focus();
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Comments to Proceed.</font>";
		return false;
	}
 	if(null != customerClassCode && ''!=Trim(customerClassCode)){
		// Start : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
		//if('CAT IIA' == Trim(customerClassCode)){
		if(document.getElementById('wy1').checked || document.getElementById('wn1').checked){
		// End : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
			if (null != document.getElementById('currentWWTF').value && Trim(document.getElementById('currentWWTF').value).length == 0) {
				document.getElementById('currentWWTF').focus();
				document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
				return false;
			}
			/*if (Trim(document.getElementById('wy1').value).length == 0 && Trim(document.getElementById('wn1').value).length == 0) {
				document.getElementById('wy1').focus();
				document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Select Waste Water Treatment Installed Or Not to Proceed.</font>";
				return false;
			}*/
 			/* if(!document.getElementById('wy1').checked && !document.getElementById('wn1').checked){
				 document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Select Waste Water Treatment Installed Or Not to Proceed.</font>";
				 return false;
				}*/
 			if (null != document.getElementById('wasteimpDate').value && Trim(document.getElementById('wasteimpDate').value).length == 0) {
				document.getElementById('wasteimpDate').focus();
				document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Waste Water Implementation Date to Proceed.</font>";
				return false;
			}else{
//START:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
				 var wasteImpDt = $("#wasteimpDate").val(); // For JQuery
		         if (compDtwithToday(wasteImpDt)){
		        	 alert("Implementation date Can't be in future ");
		             document.getElementById('wasteimpDate').focus();
		     		// document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Implementation Date can't be in future!</font>";
		     		 return false;
		             }
				}
//END:on 29-08-2016 Rajib Hazarika (682667)added validation for future Date as per Jtrac DJB-4537 and OpenProject-CODE DEVELOPMENT #1442.
			/*if (null != document.getElementById('diaryNo').value && Trim(document.getElementById('diaryNo').value).length == 0) {
				document.getElementById('diaryNo').focus();
				document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter Diary No to Proceed.</font>";
				return false;
			}*/
			if (null != document.getElementById('wasteWaterdocumentOfProof').value && Trim(document.getElementById('wasteWaterdocumentOfProof').value).length == 0) {
				document.getElementById('wasteWaterdocumentOfProof').focus();
				document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Upload Document Proof to Proceed.</font>";
				return false;
			}
			// Start : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.	
			//}
		}
		}
      return true;
}
  function fnResetWWTF(){ 
	  $('#wy1').attr("checked",false);
	  $('#wn1').attr("checked",false);
	  document.getElementById("isWasteWaterTreatmentInstalled").value = "";
	  document.getElementById('wasteWaterdocumentOfProof').value.length = 0;
	  document.getElementById('wasteWaterdocumentOfProof').value="";
	  document.getElementById("wasteimpDate").value = "";
	  }
//End: Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489.
 function fnRwh(){
	if (!validateRainWaterHarvesting()){
		document.getElementById('onscreenMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}else{
	    document.getElementById('onscreenMessage').innerHTML = "";
        //alert("Very Good Boy");
        fnSubmit();
    }
}
 function fnSubmit(){
    document.forms[0].action = "rainWaterHarvesting.action";
    document.forms[0].hidAction.value = "processRequest";
    document.getElementById('onscreenMessage').innerHTML = "";	
    hideScreen();
    //alert(document.forms[0].action+''+document.forms[0].hidAction.value);
	//document.getElementById('form_for_submit').submit();
	document.forms[0].submit();
    //alert('Submitted'+document.forms[0].hidAction.value);
    //window.location.reload();
}	
  function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
 function alpha(e) {
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}
 function rwhCheck(){
	if(document.getElementById("y1").checked = true){
		document.getElementById("isRainWaterHarvestInstalled").value = "Y";
		}
  else if(document.getElementById("n1").checked = true){
	  document.getElementById("y1").checked = false;
	  document.getElementById("isRainWaterHarvestInstalled").value = "N";
	  }
  else{
	  document.getElementById("isRainWaterHarvestInstalled").value = "";
	  }
}
 function wwtCheck(){
	if(document.getElementById("wy1").checked = true){
		document.getElementById("isWasteWaterTreatmentInstalled").value = "Y";
		}
  else if(document.getElementById("wn1").checked = true){
	  document.getElementById("isWasteWaterTreatmentInstalled").value = "N";
	  }
  else{
	  document.getElementById("isWasteWaterTreatmentInstalled").value = "";
	  }
}
 </script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<!-- Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-XConfirm password Screen -->
<%@include file="../jsp/ReConfirmPassword.html"%>
<!-- Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-XConfirm password Screen -->
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
 		<table class="djbpage">
 			<tr>
			</tr>
 			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
			</tr>
			<tr>
				<td valign="top">
				<div class="search-option" title="Server Message"><font
					size="2"><span id="onscreenMessage"><s:property value="#session.SERVER_MSG"/></span></font>&nbsp;</div>
				<!-- START: Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-Confirm password Screen -->
				<div id="rainWaterScreen" >
				<!-- END: Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-Confirm password Screen -->
				<fieldset><legend>Update Rain Water Harvesting Data Form</legend>
				<s:form action="javascript:void(0);" method="post" enctype="multipart/form-data" theme="simple"  onsubmit="return false"   >
				<s:hidden name="hidAction" id="hidAction" />
                <s:hidden name="hidApprovedBy" id="hidApprovedBy" value="#session.userId" />
                <s:hidden name="isRainWaterHarvestInstalled" id="isRainWaterHarvestInstalled" />
                <s:hidden name="isWasteWaterTreatmentInstalled" id="isWasteWaterTreatmentInstalled" />
                <table width="98%" align="center" border="0">
					<tr>
						<td align="left" width="10%"><label>KNO</label><font
							color="red"><sup>*</sup></font></td>
						<td colspan="3" align="left"><s:textfield cssClass="textbox" theme="simple" name="kno"
							id="kno" maxlength="10" onkeypress="return isNumber(event)"
							onchange="fnCheckKNO();" /></td>
						
						<td align="left" width="30%"><span id="validKNOSpan"
							style="display: none;" /><img
							src="/DataEntryApp/images/valid.gif" border="0" title="Valid KNO" /></span><span
							id="invalidKNOSpan" style="display: none;" /><img
							src="/DataEntryApp/images/invalid.gif" border="0"
							title="Invalid KNO" /></span></td>
					</tr>
                    <tr></tr>
					<tr>
						<td align="left" width="10%"><label>Name</label><font
							color="red"><sup>*</sup></font></td>
						<td colspan="3"><s:textfield cssClass="textbox" theme="simple"
							name="nameOfConsumer" id="nameOfConsumer" disabled="true" /></td>
						<td></td>
					</tr>
                    <tr></tr>
					<tr>
						<td align="left" width="40%"><label>Current Rain
						Water Harvesting Flag As In System</label><font color="red"><sup>*</sup></font></td>
						<td colspan="3"><s:textfield cssClass="textbox" theme="simple"
							name="currentRWHF" id="currentRWHF" disabled="true" /></td>
						<td></td>
					</tr>
                    <tr></tr>
					<tr>
						<td align="left" width="40%"><label>Rain Water
						Harvesting Installed</label><font color="red"><sup>*</sup></font></td>
						<td width="1%"><input type="radio"
							name="rainwaterHarvestingInstalled" id="y1" onclick="document.getElementById('n1').checked = false; document.getElementById('y1').checked = true; document.getElementById('isRainWaterHarvestInstalled').value = 'Y'; ">YES</td>
						<td width="1%"><input type="radio"
							name="rainwaterHarvestingInstalled" id="n1" onclick="document.getElementById('y1').checked = false; document.getElementById('n1').checked = true; document.getElementById('isRainWaterHarvestInstalled').value = 'N'; ">NO</td>
						<td></td>
					</tr>
                    <tr></tr>
                    
					<tr>
                     <!--  Start : Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
						<td align="left" width="10%"><label>Rain Water
						Harvesting Effective Date</label><font color="red"><sup>*</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="rwhImpdate" id="impDate" maxlength="10" /><font size="0.5">(YYYY-MM-DD)</font></td>
 						<td></td>
                     <!--  End : Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
					</tr>
 					<tr>
						<td align="left" width="40%"><label>Current Waste
						Water Treatment Flag As In System</label><font color="red"><sup>*</sup></font></td>
						<td colspan="3"><s:textfield cssClass="textbox" theme="simple"
							name="currentWWTF" id="currentWWTF" disabled="true" /></td>
						<td></td>
					</tr>
					<tr>
					
						<td align="left" width="40%"><label>Waste Water
						Treatment Installed</label><font color="red"><sup>**</sup></font></td>
						<td width="1%"><input type="radio"
							name="wastewaterTreatmentInstalled" id="wy1" onclick="document.getElementById('wn1').checked = false; document.getElementById('wy1').checked = true; document.getElementById('isWasteWaterTreatmentInstalled').value = 'Y';  ">YES</td>
						<td width="1%"><input type="radio"
							name="wastewaterTreatmentInstalled" id="wn1" onclick="document.getElementById('wy1').checked = false; document.getElementById('wn1').checked = true; document.getElementById('isWasteWaterTreatmentInstalled').value = 'N';  ">NO</td>
						<td></td>
					</tr>
					<tr>
                     <!--  Start : Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
						<td align="left" width="10%"><label>Waste Water
						Treatment Effective Date</label><font color="red"><sup>**</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="wwhImpdate" id="wasteimpDate" maxlength="10" /><font size="0.5">(YYYY-MM-DD)</font></td>
					 <!--  End: Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
						<td></td>
					</tr>
 					<tr>
						<td align="left" width="10%"><label>Document No</label><font
							color="red"><sup>*</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="documentNo" id="documentNo"
							onkeypress="return alpha(event)" /></td>
						<td></td>
					</tr>
                     <tr>
						<td align="left" width="10%"><label>Diary No</label><font
							color="red"><sup>*</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="diaryNo" id="diaryNo"
							onkeypress="return alpha(event)" /></td>
						<td></td>
					</tr>
					<tr></tr>
					<tr>
						<td align="left" width="10%"><label>Diary Date</label><font color="red"><sup>*</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="diaryDate" id="diaryDate" maxlength="10" /><font size="0.5">(YYYY-MM-DD)</font></td>
 						<td></td>
					</tr>
                     <tr></tr>
					<tr>
						<td align="left" width="10%"><label>Approved By</label><font
							color="red"><sup>*</sup></font></td>
						<td><s:textfield cssClass="textbox" theme="simple"
							name="approvedBy" id="approvedBy" disabled="true" maxlength="15"/></td>
						<td></td>
					</tr>
					<tr></tr>
					<tr></tr>
					<tr>
						<td align="left" width="10%"><label><b>Documents
						To Be Attached</b></label></td>
					<tr></tr>
					<tr></tr>
					<tr>
						<td align="left" width="40%">Photocopy of Rain Water
						Harvesting As Proof:-<font color="red" size="3"><sup>*</sup></font></td>
						<td colspan="2" align="left">
						<s:file name="documentOfProof" id="documentOfProof" theme="simple" size="40" />
						<td colspan="2"></td>
					</tr>
 					<tr>
						<td align="left" width="40%">Photocopy of Waste Water
						Treatment As Proof:-<font color="red" size="3"><sup>**</sup></font></td>
						<td colspan="2" align="left">
						<s:file name="wasteWaterdocumentOfProof" id="wasteWaterdocumentOfProof" theme="simple" size="40" />
						<td colspan="2"></td>
					</tr>
 					<tr>
						<td align="left" width="10%"><label>Comments</label><font
							color="red"><sup>*</sup></font></td>
					</tr>
					<tr>
						<td></td>
						<td align="left" width="30%"><textarea cols="40" rows="5"
							class="textarea" id="comments" title="Give Comments"
							NAME="comments"></textarea></td>
						<td colspan="3">
						<div id='ErrCommentsDiv'></div>
						</td>
					</tr>
					<tr></tr>
					<tr></tr>
 					<tr>
						<td class="blue" colspan="5">
						<center></center>
						</td>
					</tr>
					<tr>
					<!--  Start : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
						<td></td>
						<td align="left" width="10%" colspan="6">
						<s:submit value="Submit" align="center" id="SubmitRainWH" onclick="fnRwh();"/>
						<input type="button" value="Reset Waste Water Treatment Flag" id="btnReset" onclick="fnResetWWTF();"/>
						</td>
					<!-- End : Edited by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->	
					</tr>
 					<tr>
						<td align="left" colspan="1">&nbsp;</td>
						<td align="right" colspan="5"><font color="red"><sup>*</sup></font>
						marked fields are mandatory.</td>
					</tr>
					<!--  Start : Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
					<tr>
						<td align="left" colspan="1">&nbsp;</td>
						<td align="right" colspan="5"><font color="red"><sup>**</sup></font>
						marked fields are conditional mandatory.</td>
					</tr>
					<!-- End : Added by Lovely on 12-09-2016 as per Jtrac DJB-4561 and OpenProject-CODE DEVELOPMENT #1489-->
					
				</table>
			   </s:form>
	         </fieldset>
</div>
				</td>
			</tr>
			<tr height="20px">
				<td align="center" valign="bottom">
				<%@include
					file="../jsp/Footer.html" %></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">	
 </script>
<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
  <%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
</html>
