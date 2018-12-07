<!-- Rajib on 24-02-2016 edited the value of default locality & sub locality, Jtrac DJB-4313. -->
<!-- Rajib on 04-10-2016 addeded trim in first name  as per JTrac ANDROMR-7 for Quick new Connection CR. -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<%
	String areaDetails = "";
	try {
		areaDetails = (String) session.getAttribute("areaDetails");
		//System.out.print(">>areaDetails>>"+areaDetails);
%>

<head>
<title>New Connection File Data Entry Screen - Revenue
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


<script type="text/javascript">
var areaDetails= <%=areaDetails%>;
//alert(areaDetails);
  if (typeof Array.prototype.forEach != 'function') {
	    Array.prototype.forEach = function(callback){
	      for (var i = 0; i < this.length; i++){
	        callback.apply(this, [this[i], i, this]);
	      }
	    };
	}
	function showMessage(msg){
		document.getElementById('onscreenMessage').innerHTML="<font color='red'> "+msg+" </font>";
	}
 function populateLocalityAdd(keyVal){
		var htmlCode="",temp="";
		htmlCode+="<select class='selectbox-long' id='locaAdd' onchange='populateSubLocalityAdd(this)' >";	
		htmlCode+="<option value=''> Please Select </option>";	
		areaDetails.forEach( function (opt,i){
			if(keyVal.value == opt.pin){
				if (temp != opt.areaCd){
					htmlCode+="<option value='"+opt.areaCd+"'>"+opt.areaDesc+"</option>";
				}	
			} 
			temp = opt.areaCd;
		});	
		htmlCode+="</select>";	
		document.getElementById('locality').innerHTML= htmlCode;
		var found = [];
		$('select#locaAdd').find('option').each(function() {
			  if($.inArray(this.value, found) != -1){
				  	$(this).remove();
			  }else{
					found.push(this.value);
			  }
			});
		document.getElementById('locaAdd').disabled= false;
	}
 function populateSubLocalityAdd(keyVal){
		var htmlCode="",temp="";
		htmlCode+="<select class='selectbox-long' id='subLocAdd'>";		
		htmlCode+="<option value=''> Please Select </option>";
		areaDetails.forEach( function (opt,i){
			if(keyVal.value == opt.areaCd){
				if (temp != opt.subAreaCd){
					htmlCode+="<option value='"+opt.subAreaCd+"'>"+opt.subAreaName+"</option>";
					temp = opt.subAreaCd;
				}	
			} 
		});	
		htmlCode+="</select>";	
		document.getElementById('subLocality').innerHTML= htmlCode;
		var found = [];
		$('select#subLocAdd').find('option').each(function() {
			  if($.inArray(this.value, found) != -1){
				  	$(this).remove();
			  }else{
					found.push(this.value);
			  }
			});
		document.getElementById('subLocAdd').disabled= false;
	}    
</script>
<script type="text/javascript">
    function fnSaveDetails(){
    	showMessage('');
    	var  fileName="",typeOfApp="",firstName="",middleName="",lastName="",phone="",emailId="",pinAdd="",locaAdd="",subLocAdd="",hNoAdd="",
    	roadNoAdd="",subLoc1Add="",subLoc2Add="",subColAdd="",vilAdd="",identityProof="",propertyDoc="",proofOfRes="",jjr="";
    	
	     fileName= $('#fileName').val();
		 typeOfApp= $('#typeOfApp').val();
		 firstName=  $('#firstName').val();
		 middleName=  $('#middleName').val();
		 lastName=  $('#lastName').val();
		 phone=  $('#phone').val();
		 emailId=  $('#emailId').val();
		 pinAdd=  $('#pinAdd').val();
		 locaAdd=  $('#locaAdd').val();
		 subLocAdd=  $('#subLocAdd').val();
		 hNoAdd=  $('#hNoAdd').val();
		 roadNoAdd=  $('#roadNoAdd').val();
		 subLoc1Add=  $('#subLoc1Add').val();
		 subLoc2Add=  $('#subLoc2Add').val();
		 subColAdd=  $('#subColAdd').val();
		 vilAdd=  $('#vilAdd').val();
		 identityProof=  $('#identityProof').val();
		 propertyDoc=  $('#propertyDoc').val();
		 proofOfRes=  $('#proofOfRes').val();
		if (document.getElementById("jjr").checked){
			jjr='Y';
		}
		if (fileName == null || fileName==''){
				$('#fileName').focus();
				showMessage('File Number cannot be left blank');
				return;
		}
		if (typeOfApp == null || typeOfApp==''){
				$('#typeOfApp').focus();
				showMessage('Please Choose Type of Application');
				return;
		}
		// Start :  Rajib on 04-10-2016 as per JTrac ANDROMR-7 for Quick new Connection CR.
		if (firstName == null || Trim(firstName)==''){
		// End :  Rajib on 04-10-2016 as per JTrac ANDROMR-7 for Quick new Connection CR.
				$('#firstName').focus();
				showMessage('First Name cannot be left blank');
				return;
		}
		if (phone == null || phone==''){
			$('#phone').focus();
			showMessage('Please Enter Mobile No.');
			return;
		}
		if (pinAdd == null || pinAdd==''){
				$('#pinAdd').focus();
				showMessage('Please Enter PIN Code');
				return;
		}
		if (locaAdd == null || locaAdd==''){
				$('#locaAdd').focus();
				showMessage('Please Choose Locality');
				return;
		}
		if (subLocAdd == null || subLocAdd==''){
				$('#subLocAdd').focus();
				showMessage('Please Choose Sub Locality');
				return;
		}
		if (hNoAdd == null || hNoAdd==''){
				$('#hNoAdd').focus();
				showMessage('House No. cannot be left blank');
				return;
		} 
   		if (identityProof == null || identityProof=='' || identityProof=='Please Select'){
   				$('#identityProof').focus();
				showMessage('Please Provide Proof of Identity');
				return;
		} 
		if (propertyDoc == null || propertyDoc=='' || propertyDoc=='Please Select'){
				$('#propertyDoc').focus();
				showMessage('Please Provide Property Ownership Documnet');
				return;
		} 
		if (proofOfRes == null || proofOfRes=='' || proofOfRes=='Please Select'){
			proofOfRes = '';
		} 
		hideScreen();
					$.post("newConnFileEntryAJax.action",
    							{
    								hidAction : "submitDetails",
    								fileName: ""+fileName,
									 typeOfApp: ""+typeOfApp,
									 firstName: ""+firstName,
									 middleName: ""+middleName,
									 lastName: ""+lastName,
									 phone: ""+phone,
									 emailId: ""+emailId,
									 pinAdd: ""+pinAdd,
									 locaAdd: ""+locaAdd,
									 subLocAdd: ""+subLocAdd,
									 hnoAdd: ""+hNoAdd,
									 roadNo: ""+roadNoAdd,
									 subLoc1Add: ""+subLoc1Add,
									 subLoc2Add: ""+subLoc2Add,
									 subColAdd: ""+subColAdd,
									 vilAdd: ""+vilAdd,
									 identityProof: ""+identityProof,
									 propertyDoc: ""+propertyDoc,
									 proofOfRes: ""+proofOfRes,
									 jjr: jjr
								},
    							function(data, status) {
    							// alert("Status: " + status+ ">>data>>"+data);
    							 if ((null != data) && (data.indexOf("SUCCESS:") > -1)) {
    									showMessage(data.substring(data.indexOf("SUCCESS:")));
    									fnResetForm();
    									showMessage(''+data.substring(data.indexOf("SUCCESS:")));
    							 }
	    	                	 else{
		    	                	 	if ((null != data) && (data.indexOf("ERROR:") > -1)){
		    	                	 		showMessage(data.substring(data.indexOf("ERROR:")));
		    	                	 	}else{
		    	                	 		showMessage('There was some problem in processing of last request');
		    	                	 	}	
	    	                    	}
	    	                    	showScreen();
	    						});
 
		//END: AJAX Call start to Save Details
	}
	function fnResetForm(){
		showMessage('');
		$('#fileName').val("");  
		$('#typeOfApp').val("");  
		$('#firstName').val("");  
		$('#middleName').val("");  
		$('#lastName').val("");  
		$('#phone').val("");  
		$('#emailId').val("");  
		$('#pinAdd').val("");  
		$('#subLocAdd').val("");   
		$('#hNoAdd').val("");
		$('#locaAdd').val("");
		$('#roadNoAdd').val("");  
		$('#subLoc1Add').val("");  
		$('#subLoc2Add').val("");  
		$('#subColAdd').val("");  
		$('#vilAdd').val("");  
		$('#identityProof').val("Please Select");  
		$('#propertyDoc').val("Please Select");  
		$('#proofOfRes').val("Please Select");  		
		document.getElementById("jjr").checked = false;		
	}


	 function fnValidateFileNo(){
	    	showMessage('');
	    	var  fileName="";
		    fileName= $('#fileName').val();
			if (fileName == null || fileName==''){
					$('#fileName').focus();
					showMessage('Please Enter File No.');
					return;
			}
			//START: AJAX Call start to Save Details
			//alert(">>fileName>>"+fileName);
			hideScreen();
						$.post("newConnFileEntryAJax.action",
	    							{
	    								hidAction : "validateFileNo",
	    								fileName: ""+fileName
									},
	    							function(data, status) {
	    							// alert("Status: " + status+ ">>data>>"+data);
	    							 if ((null != data) && (data.indexOf("SUCCESS") > -1)) {
	    									document.getElementById('fileSubmit').disabled= false;
	    									$('#fileName').focus();
	    							 }
		    	                	 else{
			    	                	 	if ((null != data) && (data.indexOf("ERROR:") > -1)){
		    									document.getElementById('fileSubmit').disabled = true;
		    									showMessage(data.substring(data.indexOf("ERROR:")));
			    	                	 	}else{
			    	                	 		document.getElementById('fileSubmit').disabled = true;
			    	                	 		showMessage('There was some problem in processing of last request');
			    	                	 	}	
		    	                    	}
		    	                    	showScreen();
		    						});
	 
			//END: AJAX Call start to Save Details
		}
	
	function checkForEmail(){
		showMessage('');
		var emailRegex=new RegExp("<%=DJBConstants.REGEX_EMAIL%>");
		var emailId=  $('#emailId').val();
		if(emailId.search(emailRegex)){
			$('#emailId').val('');
			showMessage('Please Enter a Valid Email');
			return;
		}
		var atpos=emailId.indexOf("@");
		var dotpos=emailId.lastIndexOf(".");
		if ((atpos<1 || dotpos<atpos+2 || dotpos+2>=emailId.length )&& emailId.length !=0){
				alert('Please Enter a Valid Email Address');
				$('#emailId').val("");
				return;
			}
		
		}
	function checkPropertyChar(e){
		var unicode= e.charCode? e.charCode : e.keyCode;
		if((unicode > 64 && unicode < 91) || (unicode > 47 && unicode < 58) || (unicode > 96 && unicode < 123) || (unicode > 37 && unicode < 42) || (unicode > 43 && unicode < 47)|| unicode == 8 || unicode == 34 || unicode == 32){ 
			return true;
		}				
		else 
			return false;                        
 	}
    function disableBack(){
			window.history.forward(1);//Added By Matiur Rahman
		}
	 disableBack();
	 	
function checkAlphaNumeric(e){
			    try {
			        var unicode=e.charCode? e.charCode : e.keyCode;
	               	if((unicode > 64 && unicode < 91) || (unicode > 47 && unicode < 58) || (unicode > 96 && unicode < 123)|| unicode == 8) 
	                   	return true;	
	               	else 
		               	return false;
				}
			    catch (e) {         
			    }    
			}
// 				}
function checkPhone(){
		var mobNo=  $('#phone').val();
		if(mobNo==null||mobNo=="Mobile No" ||mobNo.length<10 ||(parseInt(mobNo,10)<1000000000) || (parseInt(mobNo,10)>=10000000000)){
					alert("Please Enter a Valid Mobile No.");	
					$('#phone').val("");
					return;						
	}
}
function checkValNonnumericDotSpecial(e){
		 		try {
		        	var unicode=e.charCode? e.charCode : e.keyCode;		              
		            if((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123) || unicode == 8 || unicode == 32 || unicode== 46) 
		            	return true;
		            else 
			            return false;	               
		                        
		        }
		     	catch (e) {		         
		    	}

			}
function checkVal(e){
			    try {
			        var unicode=e.charCode? e.charCode : e.keyCode;
			        if (unicode!=8){ 
			         	if (unicode<48||unicode>57) 
			         	return false ;
			         }                        
			     }
			     catch (e) {
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

			<tr>
			</tr>

			<tr height="20px">
				<td align="center" valign="top"><%@include 	file="../jsp/Header.html" %></td>
			</tr>
			<tr>
				<td valign="top">
				<div class="search-option" title="Server Message"><font
					size="2"><span id="onscreenMessage"><s:property
					value="#session.SERVER_MSG" /></span></font>&nbsp;</div>
				<!-- START: Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-Confirm password Screen -->
				<div id=""><!-- END: Added by Rajib as on 20-OCT-2015 as per Jtrac DJB-4037 to change popup Re-Confirm password Screen -->

				<s:form action="javascript:void(0);" method="post"
					enctype="multipart/form-data" theme="simple"
					onsubmit="return false">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="hidApprovedBy" id="hidApprovedBy"
						value="#session.userId" />
					<table width="98%" align="center" border="0">
						<tr>
							<td align="center" width="25%"></td>
							<td align="center" width="50%">

							<fieldset><legend>File Entry</legend>
							<table width="98%" align="center" border="0">

								<tr>
									<td align="left" width="10%"></td>
									<td align="left" width="40%"><label>File Number</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" id="fileName" name="fileName" title="File No." maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>" onkeypress="return checkVal(event)" onchange="fnValidateFileNo();"/>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Type of
									Application</label><font color="red"><sup>*</sup></font></td>
									<td align="left" width="40%"><s:select name="typeOfApp"
										id="typeOfApp" list="#session.typeOfAppListMap"
										headerKey=""
										headerValue="Please Select" 
										cssClass="selectbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>First Name</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="firstName"
											id="firstName" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_FIRST_NAME%>" onkeypress="return checkValNonnumericDotSpecial(event)"
										onpaste="return false"/></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Middle name</label></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="middleName"
										id="middleName" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_MIDDLE_NAME%>"
										 onkeypress="return checkValNonnumericDotSpecial(event)"
										onpaste="return false"/></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Last Name</label></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="lastName"
										id="lastName" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_LAST_NAME%>"
										onkeypress="return checkValNonnumericDotSpecial(event)"
										onpaste="return false" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Mobile No.</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="phone" id="phone"
										maxlength=" <%=DJBConstants.FIELD_MAX_LEN_CONTACT_NO%>" onkeypress="return checkVal(event)" onblur="return checkPhone()" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Email ID </label></td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="emailId" id="emailId"
										maxlength=" <%=DJBConstants.FIELD_MAX_LEN_EMAIL%>"
										title="E-mail Id" onpaste="return false" onblur= " return checkForEmail()"/>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label for='pinAdd'>PinCode
									<font color="red"> * </font></label> &nbsp; </td>
									<td align="left" width="40%">
										<input type="text" class="textbox" name="pinAdd" id="pinAdd"
										maxlength=" <%=DJBConstants.FIELD_MAX_LEN_PIN_CODE%>" title="Pin Code"
										onchange="populateLocalityAdd(this)" onkeypress="return checkVal(event)" onpaste="return false" />
										&nbsp; <input type="checkbox" name="jjr" id="jjr" /><label for="jjr">JJR Colony </label>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%">
										<label for='locaAdd'>Locality<font
											color=red>*</font> </label> &nbsp;
									</td>
									<td align="left" width="40%">
										<div id="locality" name="locality">
											<select
												class='selectbox-long' name="locaAdd" id="locaAdd"
												title="Select Locality" disabled="disabled"
												onchange="populateSubLocalityAdd(this)">
												<!-- Start : Rajib on 24-02-2016 edited the value of default locality & sub locality, Jtrac DJB-4313. -->
												<!--  <option value="Select Locality">Select Locality</option> -->
												<option value="">Select Locality</option>
												<!-- End : Rajib on 24-02-2016 edited the value of default locality & sub locality, Jtrac DJB-4313. -->
											</select>
										</div>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label for='subLocAdd'>SubLocality<font
										color=red>*</font> </label> &nbsp;
									
									</td>
									<td align="left" width="40%">
										<div id="subLocality" name="subLocality">
											<select
												name="subLocAdd" id="subLocAdd" class='selectbox-long'
												title="Select Sublocality" disabled="disabled">
												<!-- Start : Rajib on 24-02-2016 edited the value of default locality & sub locality, Jtrac DJB-4313. -->
												<!--  <option value="Select Locality">Select Sublocality</option>  -->
												<option value="">Select Sublocality</option>
												<!-- End : Rajib on 24-02-2016 edited the value of default locality & sub locality, Jtrac DJB-4313. -->
											</select>
										</div>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>

								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label for='hNoAdd'>House
									No<font color=red> * </font></label> </td>
									<td align="left" width="40%">
										<input type="text" class="textbox" id="hNoAdd" name="hNoAdd" title="House No*" onkeypress="return checkPropertyChar(event)" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_HOUSE_NO%>" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%">
										<label for="roadNoAdd">Road
									No</label>
									</td>
									<td align="left" width="40%">
										 <input type="text" class="textbox"  id="roadNoAdd" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_ROAD_NO%>"
										name="roadNoAdd" title="road No" onkeypress="return checkPropertyChar(event)"/>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>

								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%">
										<label for='subLoc1Add'> Sub Locality 1 </label>
									</td>
									<td align="left" width="40%">
										<input type="text" class='textbox'
										maxlength=" <%= DJBConstants.FIELD_MAX_LEN_SUBLOC1%>" id="subLoc1Add" name="subLoc1Add"
										title="Sub Locality 1" onkeypress="return checkPropertyChar(event)"/>
									</td>	
									
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%">
										<label for="subLoc2Add">Sub Locality 2</label> 
									</td>
									<td align="left" width="40%">
										<input type="text"  id="subLoc2Add" name="subLoc2Add" class='textbox'	maxlength=" <%=DJBConstants.FIELD_MAX_LEN_SUBLOC2%>"
										title="Sub Locality 2" onkeypress="return checkPropertyChar(event)" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%">
										<label for='subColAdd'>	Sub Colony </label>
									</td>
									<td align="left" width="40%">
										<input type="text" class='textbox'
										maxlength=" <%=DJBConstants.FIELD_MAX_LEN_SUBCOLONY%> " id="subColAdd" name="subColAdd"
										title="Sub Colony" onkeypress="return checkPropertyChar(event)"/>
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>

								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"> <label for="vilAdd">Village</label> </td>
									<td align="left" width="40%">
									<input type="text" class='textbox' id="vilAdd" maxlength=" <%=DJBConstants.FIELD_MAX_LEN_VILLAGE%>"
										name="vilAdd" title="Village"  onkeypress="return checkPropertyChar(event)"/></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>


								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Proof of
									Identity</label><font color="red"><sup>*</sup></font></td>
									<td align="left" width="40%"><s:select
										name="identityProof" id="identityProof"
										list="#session.proofOfIdTypeListMap" cssClass="selectbox-long"
										theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Property
									Ownership Document</label><font color="red"><sup>*</sup></font></td>
									<td align="left" width="40%"><s:select name="propertyDoc"
										id="propertyDoc" list="#session.propertyOwnDocListMap"
										cssClass="selectbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td align="left" width="40%"><label>Proof of
									Residence</label></td>
									<td align="left" width="40%"><s:select name="proofOfRes"
										id="proofOfRes" list="#session.proofOfResListMap"
										cssClass="selectbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="left" colspan="2" width="10%"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
									<td align="center" width="60%"><s:submit value="Submit"
										align="center" id="fileSubmit" onclick="fnSaveDetails();" />
									&nbsp; &nbsp;&nbsp; &nbsp; <s:submit value="Reset"
										align="center" id="fileReset" onclick="fnResetForm();" /></td>
									<td align="center" width="10%">&nbsp; &nbsp;</td>
									<td align="left" width="20%"></td>
									<td></td>
								</tr>
							</table>
							</fieldset>

							</td>
							<td align="center" width="25%"></td>
						</tr>
					</table>

				</s:form></div>
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
