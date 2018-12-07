<!-- This page is used for new Connection KNO Generation and Bill Generation Screen JTrac DJB-4313. --> 
<!-- @Author: Rajib Hazarika (Tata Consultancy Services) --> 
<!-- @since: 16-FEB-2016 -->
<!-- @history: Edited by Arnab Nandy (1227971) on 08-03-2016 as per JTrac DJB-4398  --> 
<!-- @history: Reshma on 11-Mar-2016 edited to correct the view bill path, JTrac DJB-4405.  -->  
<!-- @history: Edited by Sanjay Kumar Das (1033846) on 17-03-2016 as per JTrac DJB-4418.  --> 
<!-- @history: Edited by Rajib Hazarika (682667) on 23-03-2016 as per Open project Id-817.  --> 
<!-- @history: Edited by Rajib Hazarika (682667) on 31-03-2016 as per JTrac  DJB-4429 to enable PurposeofAppl field.  --> 
<!-- @history: Edited by Lovely(986018) on 19-08-2016 as per JTrac  DJB-4541 and Open project-1443 for online consumer functionalities -->  
<!-- @history: Edited by Rajib(682667) on 12-03-2018 as per JTrac DJB-4797 to remove validation of Road Restoration Charges of Water/Sewer for the length of Road Cut is greater than 5 --> 
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<%@ page language="java" import="com.tcs.djb.rms.util.EncUtil"%>
<%@ page language="java" import="java.text.SimpleDateFormat"%>
<html>
<%
	String knoGenAllwdStatus ="",billGenAllwdStatus="";
	try {
		//knoGenAllwdStatus =(String) DJBConstants.ALLOWED_STATUS_CD_FOR_KNO_GEN;
		//billGenAllwdStatus= (String) DJBConstants.ALLOWED_STATUS_CD_FOR_BILL_GEN;
		//System.out.println(">>knoGenAllwdStatus>>"+knoGenAllwdStatus+">>billGenAllwdStatus>>"+billGenAllwdStatus);
%>
<head>
<title>New Connection Data Alteration Form - Revenue Management
System, Delhi Jal Board</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
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
	href="<s:url value="/css/chosen.css"/>" />	
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/custom.css"/>" />
<script type="text/javascript"
	src="<s:url value="/js/jquery.1.4.2.min.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/chosen.jquery.js"/>"></script>	
	
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
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
if (typeof Array.prototype.forEach != 'function') {
    Array.prototype.forEach = function(callback){
      for (var i = 0; i < this.length; i++){
        callback.apply(this, [this[i], i, this]);
      }
    };
}
	var knoGenAllwdStatus="", billGenAllwdStatus="";
	var onlineStatus="";
	knoGenAllwdStatus="<%=DJBConstants.ALLOWED_STATUS_CD_FOR_KNO_GEN%>";
	billGenAllwdStatus="<%=DJBConstants.ALLOWED_STATUS_CD_FOR_BILL_GEN%>"; 

	$(function() {
    	 var todaysDate = new Date();
         var maxDate = new Date(todaysDate.getFullYear(),
                            todaysDate.getMonth(),
                            todaysDate.getDate());
		$( "#dtOfAppl" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true,
			maxDate: maxDate,
			dateFormat: 'dd-M-yy'
		});
		$( "#dtOfRet" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true,
			minDate: maxDate,
			maxDate: '+60Y',
			dateFormat: 'dd-M-yy'
		});
		var Y= document.getElementsByName('Y');
		var YValue;
		for(var i = 0; i < Y.length; i++){
		    if(Y[i].checked){
		    	YValue = Y[i].value;
		    }
		}
		//var z=document.getElementsById('plotAreaOld');
		
	});
</script>
	<script type="text/javascript">
	function fnValidateDate(obj){
	 		if(Trim(obj.value).length!=0){
	 			validateDate(obj);
	 		}
	 		if(compareDates(todaysDate,Trim(obj.value)) && todaysDate!=Trim(obj.value)){
			alert('Future Date is not Allowed !.');
			obj.focus();
		}
		}
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
	function clearAllFields(){
			var tempIsPopUp=document.getElementById('isPopUp').value;
			document.getElementById("KNO").reset();
			document.getElementById('onscreenMessage').innerHTML = "";	
			document.getElementById('isPopUp').value=tempIsPopUp;
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
	function checkPhone(){
		var mobNo=  $('#mobNo').val();
		if(mobNo==null||mobNo=="Mobile No" ||mobNo.length<10 ||(parseInt(mobNo,10)<1000000000) || (parseInt(mobNo,10)>=10000000000)){
					alert("Please Enter a Valid Mobile No.");	
					$('#mobNo').val("");
					return;						
		}
	}
	//Start: Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
	function fetchFileNumber(){
                var fileNumber = document.getElementById('fileNo').value;
				document.getElementById('onscreenMessage').innerHTML = "";
				var url = "kNOGenerationFileTaggingAJax.action?hidAction=tagFile&fileNo="
						+ fileNumber;
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
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							if (null == responseString || ''==responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
								return false;
							} 
							var zroLocation=responseString.substring(responseString.lastIndexOf(":")+1,responseString.length);
							if(null != zroLocation && ''!=Trim(zroLocation)){
								document.getElementById('zroCd').value=zroLocation;
								$('#zroCd').attr('disabled', true);	
							}
						}
					};
					httpBowserObject.send(null);
				}
	}
	//End:Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
	//  Start: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 
	function isValidArea(){
	try{
	    var x=document.getElementById('plotArea').value;
	    var NumericWithDotRegex=<%=DJBConstants.CHECK_DIGIT_WITH_DOT_OPTNL_REGEX%>;
		var isvalidArea=NumericWithDotRegex.test(x);
		showMessage("");
		//alert(NumericWithDotRegex);
		//alert(isvalidArea+'>>X>>'+x);
    	if (!isvalidArea){
     	 	showMessage("Invalid Plot Area");
     	 	//alert("Invalid Plot Area");
     	 	$('#plotArea').val("");
     	 	$('#plotArea').focus();
     	 	return false;
     	}
     	
    	//alert('>>'+parseFloat(x)<= parseFloat(0.0));
    	if (parseFloat(x)<= parseFloat(0.0)){
    	 		showMessage("Plot Area should be greater than 0.");
    	 		//alert("Plot Area should be greater than 0.");
    	 		$('#plotArea').val("");
    	 		$('#plotArea').focus();
    	 		return false;;
     	}
	}catch (e) {
	}
	}
	//End :Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
   
	</script>
</head>
<!-- Start : Edited by Arnab Nandy (1227971) as per JTrac DJB-4398  -->
<body ondragstart="return false;" ondrop="return false;">
<!-- End : Edited by Arnab Nandy (1227971) as per JTrac DJB-4398  -->
<%@include file="../jsp/CommonOverlay.html"%>
<table class="djbmain" id="djbmaintable">
<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div class="message" title="Server Message"><font
					size="2"><span id="onscreenMessage"><s:property
					value="#session.SERVER_MSG" /></span></font>&nbsp;</div>
					<div id="knogener">
				<s:form action="javascript:void(0);" onsubmit="return false;" id="KNO" theme="simple"/>
					<s:hidden name="hidAction" />
					<s:hidden name="dafSequence" />
					<s:hidden name="jjrHidden" id="jjrHidden"/>
					<s:hidden name="urbanHidden" id="urbanHidden"/>
					<table width="100%" align="center" border="0">
						<tr>
							<td align="center" valign="top">
							<fieldset><legend class="choosentd" >KNO Generation</legend>
							<table width="90%" align="center" border="0">
								<tr>
									<td colspan="4" class="choosentd" align="right">
										<font color="red"><sup>**</sup></font> One of the field is Mandatory <font color="red"><sup>*</sup></font> Marked Fields are Mandatory
									</td>
								</tr>
								<!-- Start : Edited by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443 -->	
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" ><strong>File No.</strong><font color="red"><sup>**</sup></font></td>
									<td width="40%" align="left">
								<!--Start:Commented by Madhuri : as per JTrac DJB-4541 ,Open project-1443
									<input type="text" class="textbox" id="fileNo" name="fileNo" title="File No." maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>" onkeypress="return checkVal(event)" onchange="fnGetArnDetails('file');"/> -->
									<input type="text" class="textbox" id="fileNo" name="fileNo" title="File No." maxlength="<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>"  onkeypress="return checkVal(event)" onchange="checkOnlineOrthirdPrty();"/>
								<!--END:Commented by Madhuri : as per JTrac DJB-4541 ,Open project-1443 -->
									<td align="left" width="10%"> &nbsp;</td>
								</tr>
							    <tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" ><strong>Application Reference NO.(ARN)</strong><font color="red"><sup>**</sup></font></td>
									<td width="40%" align="left">
									<input type="text" class="textbox" name="arnNo" id="arnNo" onchange="fnGetArnDetails('arn');" maxlength="<%=DJBConstants.FIELD_MAX_LEN_ARN_NO%>" onkeypress="return checkVal(event)"/>
									<td align="left" width="10%">&nbsp; </td>
								</tr>
							<tr>
								<td align="left" width="10%">&nbsp;</td>
								<td width="40%" class="choosentd" align="left" ><strong>Online consumer</strong></td>
								<td width="40%" align="left">
								<input type="checkbox" id="onlineStatus" name="onlineStatus" disabled="true"></input>								
		                        <td align="left" width="10%">&nbsp; </td>
							 </tr>
							 <!-- End : Edited by Lovely (986018) as per JTrac DJB-4541,Open project-1443  -->	
							 <tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left"  ><strong>Name</strong></td>
									<td width="40%" align="left"><s:textfield name="consumerName"
										id="consumerName" maxlength="254" cssClass="textbox-long"
										theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" ><strong>Mobile No.</strong></td>
									<td width="40%" align="left"><s:textfield name="mobNo"
										id="mobNo" cssClass="textbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" ><strong>Address</strong></td>
									<td width="40%" align="left"><s:textfield name="address"
										id="address" maxlength="254" cssClass="textbox-long"
										theme="simple"/></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;
									<!-- START: On 31-MAR-2016 Rajib Hazarika (682667) Added below lines as per JTrac DJB-4429  -->
									<input type="hidden" id="purposeOfApplOld" name="purposeOfApplOld" />
									<!-- START: On 31-MAR-2016 Rajib Hazarika (682667) Added below lines as per JTrac DJB-4429  -->
									</td>
									<td width="40%" class="choosentd" align="left" >Purpose of Application<font color="red"><sup>*</sup></font></td>
									<td width="40%" title="Please Select purpose" align="left"><s:select
										headerKey="" headerValue="Please Select" list="#session.typeOfAppListMap" name="purposeOfAppl"
										id="purposeOfAppl" cssClass="selectbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Is water being used in civil construction for the premises<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.yesNoCharValListMap" name="isWatUsedForPrem"
										id="isWatUsedForPrem" cssClass="selectbox-long" theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable Civil Construction Charges</td>
									<td width="40%" align="left"><s:textfield name="applCivilConstChrg"
										id="applCivilConstChrg" maxlength="10" cssClass="textbox"  onkeypress="return checkVal(event)" 
										theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Unauthorized usage detected at premise<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.unAuthUsgCharValListMap" name="isUnAuthorisedUsage"
										id="isUnAuthorisedUsage" cssClass="selectbox-long" theme="simple" />
									</td>
									 <td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Preferred mode of payment<font color="red"><sup>*</sup></font></td>
									<td width="40%" title="Please Select purpose" align="left"><s:select
										headerKey="" headerValue="Please Select" list="#session.prefModeOfPmntCharValListMap" name="prefModeOfPayment"
										id="prefModeOfPayment" cssClass="selectbox-long" theme="simple" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td class="choosentd" align="left" >Water Technical Feasibility<font color="red"><sup>*</sup></font></td>
									<td colspan="3" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.watFeasCharValListMap" name="watTechFeasibility"
										id="watTechFeasibility" cssClass="selectbox-long" theme="simple" />
									</td>
									 <td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Sewerage Technical Feasibility<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.sewFeasCharValListMap" name="sewTechFeasibility"
										id="sewTechFeasibility" cssClass="selectbox-long" theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >If all the documents facilitated by user are verified<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.isDocVerifiedCharValListMap" name="isDocVerified"
										id="isDocVerified" cssClass="selectbox-long" theme="simple" />									 
									 </td>
									 <td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >DJB Employee Rebate Applicable<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.djbEmpCharValListMap" name="djbEmpRbtAppl"
										id="djbEmpRbtAppl" cssClass="selectbox-long" theme="simple" />		
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >DJB Employee ID</td>
									<td width="40%" align="left"><s:textfield name="empId"
										id="empId" maxlength="10" onkeypress="return checkAlphaNumeric(event)"  cssClass="textbox"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								 <tr title="This date will be populated automatically for Unmetered Consumer in the Entry Screen.">
										<td align="left" width="10%">&nbsp;</td>
										<td width="40%" class="choosentd" align="left"  >Date of Retirement for DJB Employee</td>
											<td width="40%" align="left" ><s:textfield cssClass="textbox" theme="simple"
										name="dtOfRet" id="dtOfRet" maxlength="10" /><font size="0.5">(DD-MON-YYYY)</font></td>
										<td align="left" width="10%">&nbsp;</td>
										</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Size of Meter<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.wconSizeCharValListMap" name="sizeOfMtr"
										id="sizeOfMtr" cssClass="selectbox-long" theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Length of Road to be cut for Water<font color="red"><sup>*</sup></font>
									<td width="40%" align="left"><s:textfield name="lengthOfRoadForWater"
										id="lengthOfRoadForWater" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Length of Road to be cut for Sewerage <font color="red"><sup>*</sup></font>
									<td width="40%" align="left"><s:textfield name="lengthOfRoadForSewer"
										id="lengthOfRoadForSewer" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable Road Restoration Charges for Water if length>5
									<td width="40%" align="left"><s:textfield name="roadRestChrgForWater"
										id="roadRestChrgForWater" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable Road Restoration Charge for Sewerage if length > 5
									<td width="40%" align="left"><s:textfield name="roadRestChrgForSewer"
										id="roadRestChrgForSewer" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable Additional Charges / Pending Dues
									<td width="40%" align="left"><s:textfield name="addtionalCharge"
										id="addtionalCharge" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable no. of yrs for which unauthorized usage penalty is charged
									<td width="40%" align="left"><s:textfield name="noOfYrForUnauthPenlaty"
										id="noOfYrForUnauthPenlaty" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Applicable unauthorized Usage Penalty based on estimated consumption
									<td width="40%" align="left"><s:textfield name="size"
										id="unauthPenlatyAmt" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Is Occupier Security Applicable<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.occupSecurityCharValListMap" name="isOccupierSecurity"
										id="isOccupierSecurity" cssClass="selectbox-long" theme="simple" />
									</td>
									 <td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Meter Security Charge
									<td width="40%" align="left"><s:textfield name="size"
										id="mtrSecurityCharge" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Trade Security Charge
									<td width="40%" align="left"><s:textfield name="size"
										id="tradeSecurityCharge" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple"  /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Unique MR Code Key<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left"><s:textfield name="size"
										id="mrkey" maxlength="10" cssClass="textbox" onkeypress="return checkVal(event)"
										theme="simple" onchange="fnValidateMrkey();" /></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
								<!--  Start: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 -->
									<input type="hidden" id="plotAreaOld" name="plotAreaOld" />
								    <td align="left" width="10%">&nbsp;</td>
									<td class="choosentd" align="left" >Plot Area(in Sq m)</td>
									<td align="left"><s:textfield name="plotArea" id="plotArea"
										title="Plot Area" maxlength="10" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyDecimal(event)"
										onchange="IsNumber(this);isValidArea();" /></td>
								
								</tr>
								<!-- End:  Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408 -->
								<tr title="This date will be populated automatically for Unmetered Consumer in the Entry Screen.">
										<td align="left" width="10%">&nbsp;</td>
										<td width="40%" class="choosentd" align="left" >Customer Date of Application</td>
										<td width="40%" align="left" ><s:textfield cssClass="textbox" theme="simple"
										name="dtOfAppl" id="dtOfAppl" maxlength="10" /><font size="0.5">(DD-MON-YYYY)</font></td>
										<td align="left" width="10%">&nbsp;</td>
										</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Sewer Development Charge Applicability<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.sewDevChrgApplCharValListMap" name="sewDevChrgAppl"
										id="sewDevChrgAppl" cssClass="selectbox-long" theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Development Charge Colony for Sewer # If Sewer Development Charge is NOT applicable, please enter "SX" <font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.sewDevChrgColCharValListMap" name="devChrgColonySew"
										id="devChrgColonySew" cssClass="chosen-select" data-placeholder="Please Select..." theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >Water Development Charge Applicability<font color="red"><sup>*</sup></font></td>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.watDevChrgApplCharValListMap" name="watDevChrgAppl"
										id="watDevChrgAppl" cssClass="selectbox-long" theme="simple" />
									</td>
									 <td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td class="choosentd" align="left" >Development Charge Colony for Water # If Water Development Charge is NOT applicable, please enter "WX" <font color="red"><sup>*</sup></font></td>
									<td colspan="3" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.watDevChrgColCharValListMap" name="devChrgColonyWat"
										id="devChrgColonyWat" cssClass="chosen-select" data-placeholder="Please Select.." theme="simple" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td align="left" width="10%">&nbsp;</td>
									<td width="40%" class="choosentd" align="left" >ZRO Location <font color="red"><sup>*</sup></font>
									<td width="40%" align="left">
										<s:select
										headerKey="" headerValue="Please Select" list="#session.zroLocCharValListMap" name="zroCd"
										id="zroCd" cssClass="selectbox-long" theme="simple" disabled="true" />
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
								<tr></tr>
								<tr>
								<td></td>
								</tr>
								<tr>
									<td align="left" width="10%" class="choosentd"><font color="red">*</font><font color="blue"> Please Enter the Mandatory Fields for NewConnection DAF</font>
									</td>
									<td width="20%" align ="right">
										<input type="button" value="Reset" id="btnReset" class="groovybutton" onclick="fnResetFields();" /> &nbsp; &nbsp;
										<input type="submit" value="Generate KNO" id="btnSubmit" class="groovybutton" disabled="true" onclick="fnGenerateKno();" />
									</td>
									<td width="60%" align="left">										
										&nbsp;&nbsp;
										<input type="button" value=" Generate New Connection Bill" id="btnBill" class="groovybutton" disabled="true" onclick="fnGenerateBill();" />
										<input type="button" value=" View and Print Bill" id="btnView" class="groovybutton" onclick="fnViewBill();" />
										<a href="#" id="viewBill" style="visibility: hidden">View Bill</a>
										<!-- Start : Edited by Lovely (986018) as per JTrac DJB-4541,Open project-1443  -->
										<input type="button" value=" Tag File Number" class="groovybutton" id="btnTag"onclick="fnTagInsertDetails();"/>
										<!-- End : Edited by Lovely (986018) as per JTrac DJB-4541,Open project-1443  -->	
									</td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
</table>
</td>
</tr>
</table>
		
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<script type="text/javascript">
if (typeof Array.prototype.forEach != 'function') {
    Array.prototype.forEach = function(callback){
      for (var i = 0; i < this.length; i++){
        callback.apply(this, [this[i], i, this]);
      }
    };
}
var statusCd="", acctId="", billId="";
var acctGenBy="";
var billGenBy="";
var perId= "";
var plotArea="";

	function showMessage(msg){
		document.getElementById('onscreenMessage').innerHTML="<font color='red'> "+msg+" </font>";
	}
	 function checkOnlineOrthirdPrty(){
		if(onlineStatus=="Y"){
			fetchFileNumber();
		}else{
			fnGetArnDetails('file');
	    }
	 } 
	//Start: Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443.
     function fnTagInsertDetails(){
         var fileNo=document.getElementById('fileNo').value;
         var arnNo=document.getElementById('arnNo').value;
         if(null==fileNo || Trim(fileNo)==''){
        	 showMessage('File No. is Mandatory');
        	 return false;
         }     
			document.getElementById('onscreenMessage').innerHTML = "";
			var fileNo=document.getElementById('fileNo').value;
			var purposeOfAppl=document.getElementById('purposeOfAppl').value;
			var consumerName =document.getElementById('consumerName').value;
			var mobNo =document.getElementById('mobNo').value;
			var address=document.getElementById('address').value;
			var arnNo=document.getElementById('arnNo').value;
			var zroCd=document.getElementById('zroCd').value;
			var plotArea=document.getElementById('plotArea').value;
			var url = "kNOGenerationFileTaggingSavingAJax.action?hidAction=tagInsertDetails&fileNo="+fileNo+"&purposeOfAppl="+purposeOfAppl;
			url=url+"&consumerName="+consumerName+"&mobNo="+mobNo+"&address="+address+"&arnNo="+arnNo+"&zroCd="+zroCd+"&plotArea="+plotArea+"&purposeOfAppl="+purposeOfAppl;
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
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						//alert("responseString"+responseString);
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+responseString+"</b></font>";
							return false;
						} 
						else{
							document.getElementById('onscreenMessage').innerHTML = "<font color='green' size='2'><b>"+responseString+"</b></font>";
					}
					}
				};
				httpBowserObject.send(null);
			}
     }
   //End: Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
    function fnGetArnDetails(val){
    	showMessage('');
    	var  arnNo="";
    	var fileNo= "";
    	   	
    	var maxFileNoLength=<%=DJBConstants.FIELD_MAX_LEN_ARN_NO%>;
    	var maxArnNoLength=<%=DJBConstants.FIELD_MAX_LEN_FILE_NO%>;
    	//alert('>>val>>'+val);
    	if (val == 'file'){
    		fileNo = $('#fileNo').val();
        }else{
        	arnNo = $('#arnNo').val();
        	if (null != arnNo && (arnNo.length < maxArnNoLength)){
	    			showMessage('Please Enter Valid ARN.');
					return;
        		}
        }	
		if ((arnNo == null || arnNo=='') && (fileNo == null || fileNo == '')){
				$('#arnNo').focus();
				showMessage('Please Enter ARN No. or File No. ');
				return;
		}
					hideScreen();
					$.post("kNOGenerationAJax.action",
    							{
    								hidAction : "getArnDetails",
    								arnNo: ""+arnNo,
    								fileNo: ""+ fileNo
								},
    							function(data, status) {
    							 if ((null != data) && (data.indexOf("ERROR:") < 0)) {
    									showMessage('');
    									fnResetFields();
    									var fetchDetails= data;
    									//alert('fetchDetails>>'+fetchDetails);
    									var fetchDetails= data.split("|");
    										$('#consumerName').val(fetchDetails[0]);
    										$('#consumerName').attr('disabled', true);
    										$('#mobNo').val(fetchDetails[1]);
    										$('#mobNo').attr('disabled', true);
    										$('#address').val(fetchDetails[2]);
    										$('#address').attr('disabled', true);
    										$('#purposeOfAppl').val(fetchDetails[3]);
    										//START:On 31-MAR-2016 Rajib Hazarika(682667) Added for keeping old Purpose of Application as per JTrac DJB-4429
    										$('#purposeOfApplOld').val(fetchDetails[3]);
    										//$('#purposeOfAppl').attr('disabled', true);
    										//END:On 31-MAR-2016 Rajib Hazarika(682667) Added for keeping old Purpose of Application as per JTrac DJB-4429
    										$('#dtOfAppl').val(fetchDetails[4]);
    										$('#dtOfAppl').attr('disabled', true);	
    										$('#zroCd').val(fetchDetails[5]);
    										//alert('>>zroCd'+zroCd+'>>$val()>>'+$('#zroCd').val());
    										if ($('#zroCd').val() != ''){
    											$('#zroCd').attr('disabled', true);	
        									}
    										$('#fileNo').val(fetchDetails[6]);
    										$('#arnNo').val(fetchDetails[7]);  
    										arnNo = fetchDetails[7];   										
    										statusCd=fetchDetails[8];
    										acctId=fetchDetails[9];
    										acctGenBy=fetchDetails[10];
    										billId=fetchDetails[11];
    										token= fetchDetails[12];
    								    	billGenBy=fetchDetails[13];
    								    	$('#isWatUsedForPrem').val(fetchDetails[14]);
    								    	$('#isUnAuthorisedUsage').val(fetchDetails[15]);
    								    	$('#prefModeOfPayment').val(fetchDetails[16]);
    								    	$('#djbEmpRbtAppl').val(fetchDetails[17]);
    								    	$('#empId').val(fetchDetails[18]);
    								    	$('#dtOfRet').val(fetchDetails[19]);    								    	 
    								    	$('#isWatUsedForPrem').val(fetchDetails[14]).attr("selected", "selected");
    								    	perId= fetchDetails[20];
    								    	//Start: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
    								    	plotArea=fetchDetails[21];
    								    	$('#plotArea').val(fetchDetails[21]);
    								    	$('#plotAreaOld').val(fetchDetails[21]);
    								    	//End: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
    										// Start : Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
    								    	onlineStatus=fetchDetails[22];
    								    	if(onlineStatus=="Y"){
        								    	$("#onlineStatus").attr("checked", true);
    								    	    $('#onlineStatus').attr('disabled',true);
    								    	}
    										// End :Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
    								    	
    										if("" == acctId){
    											//alert('>>>>'+knoGenAllwdStatus+'>>statusCd>>'+statusCd+'<>>'+isSubStr(knoGenAllwdStatus,',',statusCd));
    											if(!isSubStr(knoGenAllwdStatus,',',statusCd)){
    													showMessage("ARN:"+arnNo+" is still not received for KNO/Bill Generation!");
    													$('#btnSubmit').attr('disabled', true);
    													$('#btnView').attr('disabled', true);
    													$('#btnBill').attr('disabled', true);
													}else{
														$('#btnSubmit').attr('disabled', false);
														$('#btnView').attr('disabled', true);
    													$('#btnBill').attr('disabled', true);
													}
											}
											else{
												$('#btnSubmit').attr('disabled', true);
												if ("" != billId){
														$('#btnView').attr('disabled', false);
														showMessage('AccountId: '+acctId +" and BillId: "+ billId +" are already Generated for the specified ARN ");
													}
												else{
														//alert('>>>>'+billGenAllwdStatus+'>>statusCd>>'+statusCd+'<>>'+isSubStr(billGenAllwdStatus,',',statusCd));
															if (!isSubStr(billGenAllwdStatus,',',statusCd)){
																 showMessage("ARN is still not received for New Connection Bill Generation!");	
																 $('#btnView').attr('disabled', true);
																 $('#btnBill').attr('disabled', false);
															}else{
																showMessage('AccountId:'+acctId +" is already Generated for the ARN:"+arnNo+", Please Generate New Connection Bill!");	
																 $('#btnView').attr('disabled', true);
																 $('#btnBill').attr('disabled', false);	
																}
													}
												}
    										//alert(">>statusCd>>"+statusCd+">>acctId>>"+acctId+">>billId>>"+billId+">>token>>"+token);
    										// Start : Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443.
    										if(onlineStatus=="Y"){
    											$('#purposeOfAppl').attr('disabled', true);
    											$('#purposeOfApplOld').attr('disabled', true);
    											$('#isWatUsedForPrem').attr('disabled', true);
    											$('#applCivilConstChrg').attr('disabled', true);
    											$('#isUnAuthorisedUsage').attr('disabled', true);
    											$('#prefModeOfPayment').attr('disabled', true);
    											$('#watTechFeasibility').attr('disabled', true);
    											$('#sewTechFeasibility').attr('disabled', true);
    											$('#isDocVerified').attr('disabled', true);
    											$('#djbEmpRbtAppl').attr('disabled', true);
    											$('#empId').attr('disabled', true);
    											$('#dtOfRet').attr('disabled', true);
    											$('#sizeOfMtr').attr('disabled', true);
    											$('#lengthOfRoadForWater').attr('disabled', true);
    											$('#lengthOfRoadForSewer').attr('disabled', true);
    											$('#roadRestChrgForWater').attr('disabled', true);
    											$('#roadRestChrgForSewer').attr('disabled', true);
    											$('#addtionalCharge').attr('disabled', true);
    											$('#noOfYrForUnauthPenlaty').attr('disabled', true);
    											$('#unauthPenlatyAmt').attr('disabled', true);
    											$('#isOccupierSecurity').attr('disabled', true);
    											$('#mtrSecurityCharge').attr('disabled', true);
    											$('#tradeSecurityCharge').attr('disabled', true);
    											$('#plotAreaOld').attr('disabled', true);
    											$('#plotArea').attr('disabled', true);
    											$('#sewDevChrgAppl').attr('disabled', true);
    											$('#devChrgColonySew').attr('disabled', true);
    											$('#watDevChrgAppl').attr('disabled', true);
    											$('#devChrgColonyWat').attr('disabled', true);
    											$('#zroCd').attr('disabled', true);
    											//document.getElementById('devChrgColonyWat').disabled=true;
    											//document.getElementById('devChrgColonySew').disabled=true;
    											$('#btnBill').attr('disabled', true);
    											$('#btnSubmit').attr('disabled', true);
    											$('#btnView').attr('disabled', true);
        										}
    										if(onlineStatus=="N"){
    											$('#btnTag').attr('disabled', true);
        										}
    										// END: Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
    									showScreen();
    							 }
	    	                	 else{
	    	                		 fnResetFields();
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
    function fnResetFields(){
    	showMessage('');
    	statusCd="";
		acctId="";
		billId="";
    	$('#arnNo').val("");
    	$('#fileNo').val("");
    	$('#consumerName').val("");
    	$('#mobNo').val("");
    	$('#address').val("");
    	$('#purposeOfAppl').val("");
    	$('#isWatUsedForPrem').val("");
    	$('#applCivilConstChrg').val("");
    	$('#isUnAuthorisedUsage').val("");
    	$('#prefModeOfPayment').val("");
    	$('#watTechFeasibility').val("");
    	$('#sewTechFeasibility').val("");
    	 $('#isDocVerified').val("");
    	 $('#djbEmpRbtAppl').val("");
    	 $('#empId').val("");
    	 $('#dtOfRet').val("");
    	 $('#sizeOfMtr').val("");
    	 $('#lengthOfRoadForWater').val("");
    	 $('#lengthOfRoadForSewer').val("");
    	 $('#roadRestChrgForWater').val("");
    	 $('#roadRestChrgForSewer').val("");
    	 $('#addtionalCharge').val("");
    	 $('#noOfYrForUnauthPenlaty').val("");
    	 $('#unauthPenlatyAmt').val("");
    	 $('#isOccupierSecurity').val("");
    	 $('#mtrSecurityCharge').val("");
    	 $('#tradeSecurityCharge').val("");
    	 $('#mrkey').val("");
    	 // Start:  Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
    	 $('#plotArea').val("");
    	 $('#plotAreaOld').val("");
    	 // End:  Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
    	 $('#dtOfAppl').val("");
    	 $('#sewDevChrgAppl').val("");
    	 $('#devChrgColonySew').val("");
    	 $('#watDevChrgAppl').val("");
    	 $('#devChrgColonyWat').val("");
    	 $('#zroCd').val(""); 
    	 $('#consumerName').attr('disabled', false);
		 $('#mobNo').attr('disabled', false);
		 $('#address').attr('disabled', false);
		 $('#purposeOfAppl').attr('disabled', false);
		 $('#dtOfAppl').attr('disabled', false);	
		 $('#zroCd').attr('disabled', false);	
		 $('#btnSubmit').attr('disabled', true); 
		 $('#btnView').attr('disabled', true);
		 //START:On 31-MAR-2016 Rajib Hazarika(682667) Added for keeping old Purpose of Application as per JTrac DJB- 
		 $('#purposeOfApplOld').val("");
		//END:On 31-MAR-2016 Rajib Hazarika(682667) Added for keeping old Purpose of Application as per JTrac DJB- 	
        // Start : Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
		 $("#onlineStatus").attr("checked",false);
		// End : Added by Lovely (986018) as per JTrac DJB-4541 ,Open project-1443
  	} 

  	
	function fnViewBill(){
		var urlpart1= "<%=DJBConstants.BILL_ID_REPORT_URL_PART1%>";
		var urlpart2= "<%=DJBConstants.BILL_ID_REPORT_URL_PART2%>";
		if ("" != billId && "" != token){
			//alert('http://10.18.21.145:7003/DJBPortal/portals/docServlet?billId='+billId+'&page=billreport&DocType=DJBBill.pdf&caller=DJBCustomer.portal&token='+token+'');
				//Start : Reshma on 11-Mar-2016 edited the below part to correct the view bill path, JTrac DJB-4405.
				//window.open(''+urlpart1+billId+''+token+urlpart2,'Bill');
				window.open(''+urlpart1+billId+''+urlpart2+token,'Bill');
				//End : Reshma on 11-Mar-2016 edited the below part to correct the view bill path, JTrac DJB-4405.
			}		
	}
	function fnValidateMrkey(){
		var mrkey="",zroCd="";
		mrkey= $('#mrkey').val();
		zroCd= $('#zroCd').val();
		if (mrkey != null || mrkey!=''){
			hideScreen();
			$.post("kNOGenerationAJax.action",
						{
							hidAction : "validateMrkey",
							mrkey: ""+mrkey,
							zroCd:""+zroCd													
						},
						function(data, status) {
						 if ((null != data) && (data.indexOf("ERROR:") < 0)) {
								showMessage('');
								showScreen();
						 }
	                	 else{
	                		 	$('#mrkey').val("");
		                	 	if ((null != data) && (data.indexOf("ERROR:") > -1)){
		                	 		showMessage(data.substring(data.indexOf("ERROR:")));
		                	 	}else{
		                	 		showMessage('There was some problem in validating mrkey');
		                	 	}	
	                    	}
	                    	showScreen();
						});
			}
		}
	function fnGenerateBill(){
		if (acctId == null || acctId==''){
			showMessage("AcctId couldn't be retrieved");
			return;
			}
		if(confirm('Are You Sure You Want to Generate New Connection Bill for the Account: '+acctId+'  ?\n\nClick OK to Continue else Cancel')){
			hideScreen();
			$.post("kNOGenerationAJax.action",
						{
							hidAction : "generateBill",
							acctId: ""+acctId													
						},
						function(data, status) {
						 if ((null != data) && (data.indexOf("ERROR:") < 0)) {
							 	//alert('>>data>>'+data);
							 	fnResetFields();
								showMessage(''+data);								
								//$('#btnView').attr('disabled', false);
    							$('#btnBill').attr('disabled', true);
								showScreen();
						 }
	                	 else{
	                		 fnResetFields();
		                	 	if ((null != data) && (data.indexOf("ERROR:") > -1)){
		                	 		showMessage(data.substring(data.indexOf("ERROR:")));
		                	 	}else{
		                	 		showMessage('There was some problem in processing of last request');
		                	 	}	
	                    	}
	                    	showScreen();
						});
			}
	}
  	function fnGenerateKno(){
  	  	var arnNo="",fileNo="",consumerName="",mobNo="",address="",address="",purposeOfAppl="",purposeOfApplOld="",isWatUsedForPrem="",
  	  		applCivilConstChrg="",isUnAuthorisedUsage="",prefModeOfPayment="",watTechFeasibility="",sewTechFeasibility="",
  	  		isDocVerified="",djbEmpRbtAppl="",empId="",dtOfRet="",sizeOfMtr="",lengthOfRoadForWater="",
  	  		lengthOfRoadForSewer="",roadRestChrgForWater="",roadRestChrgForSewer="",addtionalCharge="",
  	  		noOfYrForUnauthPenlaty="",unauthPenlatyAmt="",isOccupierSecurity="",mtrSecurityCharge="",
  	  		tradeSecurityCharge="",mrkey="",plotArea="",dtOfAppl="",sewDevChrgAppl="",devChrgColonySew="",watDevChrgAppl="",
  	  		devChrgColonyWat="",zroCd="",plotAreaOld="";
	  		
		  	arnNo=$('#arnNo').val();
		  	fileNo=$('#fileNo').val();
		  	consumerName=$('#consumerName').val();
		  	mobNo=$('#mobNo').val();
		  	address=$('#address').val();
		  	purposeOfAppl=$('#purposeOfAppl').val();
			//START: On 31-MAR-2016 Rajib Hazarika(682667) added as per Jtrac DJB- to retrieve old Purpose of Appl Value
		  	purposeOfApplOld= $('#purposeOfApplOld').val();	
		    //END: On 31-MAR-2016 Rajib Hazarika(682667) added as per Jtrac DJB- to retrieve old Purpose of Appl Value	  	
		  	isWatUsedForPrem=$('#isWatUsedForPrem').val();
		  	applCivilConstChrg=$('#applCivilConstChrg').val();
		  	isUnAuthorisedUsage=$('#isUnAuthorisedUsage').val();
		  	prefModeOfPayment=$('#prefModeOfPayment').val();
		  	watTechFeasibility=$('#watTechFeasibility').val();
		  	sewTechFeasibility=$('#sewTechFeasibility').val();
		  	isDocVerified= $('#isDocVerified').val();
		  	djbEmpRbtAppl=	 $('#djbEmpRbtAppl').val();
		  	empId= $('#empId').val();
		  	dtOfRet= $('#dtOfRet').val();
		  	sizeOfMtr= $('#sizeOfMtr').val();
		  	lengthOfRoadForWater= $('#lengthOfRoadForWater').val();
		  	lengthOfRoadForSewer= $('#lengthOfRoadForSewer').val();
		  	roadRestChrgForWater= $('#roadRestChrgForWater').val();
		  	roadRestChrgForSewer= $('#roadRestChrgForSewer').val();
		  	addtionalCharge= $('#addtionalCharge').val();
		  	noOfYrForUnauthPenlaty= $('#noOfYrForUnauthPenlaty').val();
		  	unauthPenlatyAmt= $('#unauthPenlatyAmt').val();
		  	isOccupierSecurity= $('#isOccupierSecurity').val();
		  	mtrSecurityCharge= $('#mtrSecurityCharge').val();
		  	tradeSecurityCharge= $('#tradeSecurityCharge').val();
		  	mrkey= $('#mrkey').val();
		  	// Start: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
		  	plotArea=$('#plotArea').val();
		  	plotAreaOld=$('#plotAreaOld').val();
		  	// End: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
		  	dtOfAppl= $('#dtOfAppl').val();
		  	sewDevChrgAppl= $('#sewDevChrgAppl').val();
		  	devChrgColonySew= $('#devChrgColonySew').val();
		  	watDevChrgAppl= $('#watDevChrgAppl').val();
		  	devChrgColonyWat= $('#devChrgColonyWat').val();
		  	zroCd= $('#zroCd').val();
		  	// Start: Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
            if (plotArea == null || plotArea==''){
				$('#plotArea').focus();
				showMessage('Please Enter Plot Area');
				return;
			}
			//alert(plotArea);
			//alert(plotAreaOld);
			isValidArea();
			// End:  Edited by Sanjay Kumar Das (1033846) as per JTrac DJB-4408
		  	if (arnNo == null || arnNo==''){
				$('#arnNo').focus();
				showMessage('Please Enter ARN No.');
				return;
			}
			if (purposeOfAppl == null || purposeOfAppl==''){
				$('#purposeOfAppl').focus();
				showMessage('Please Choose Purpose Of Application');
				return;
			}
			if (isWatUsedForPrem == null || isWatUsedForPrem==''){
				$('#isWatUsedForPrem').focus();
				showMessage('Is Water Used For Premise field is Mandatory');
				return;
			}
		  	if (isUnAuthorisedUsage == null || isUnAuthorisedUsage==''){
				$('#arnNo').focus();
				showMessage('Unauthorized usage detected at premise field is Mandatory');
				return;
			}

		  	if (prefModeOfPayment == null || prefModeOfPayment==''){
				$('#prefModeOfPayment').focus();
				showMessage('Please Choose Preferred mode of payment');
				return;
			}

		  	if (watTechFeasibility == null || watTechFeasibility==''){
				$('#watTechFeasibility').focus();
				showMessage('Please Choose Water Technical Feasibility');
				//alert('inside else watTechFeasibility>>'+watTechFeasibility);
				return;
			}else{
				//alert('inside else watTechFeasibility>>'+watTechFeasibility+'>>');
				if (watTechFeasibility == 'Y'){
					//alert('inside lengthOfRoadForWater>>'+lengthOfRoadForWater+'>>');
					if (lengthOfRoadForWater == null || lengthOfRoadForWater=='' ){
							showMessage('Please Enter length of Road to be Cut for Water');
							return;
						}
				/*	START: Changed by Rajib as per Jtrac DJB-4797 to remove validation for Road Restoration Charges of Water/Sewer for the length of Road Cut is greater than 5. 
					else{
						 if (lengthOfRoadForWater>5){	
							 if (roadRestChrgForWater == null || roadRestChrgForWater==''){
								 showMessage('Please Enter Road Restoration Charge for Water');
								 return;
								 }
							 }
						}
					END: Changed by Rajib as per Jtrac DJB-4797 to remove validation for Road Restoration Charges of Water/Sewer for the length of Road Cut is greater than 5.
					*/
					}
				//alert('after else lengthOfRoadForWater>>'+lengthOfRoadForWater+'>>');
				}
			if (sewTechFeasibility == null || sewTechFeasibility==''){
				$('#sewTechFeasibility').focus();
				showMessage('Please Choose Sewerage Technical Feasibility');
				return;
			}else{
				if (sewTechFeasibility == 'Y'){
					if (lengthOfRoadForSewer == null || lengthOfRoadForSewer=='' ){
							showMessage('Please Enter length of Road to be Cut for Sewer');
							return;
						}
					/*	START: Changed by Rajib as per Jtrac DJB-4797 to remove validation for Road Restoration Charges of Water/Sewer for the length of Road Cut is greater than 5.
					else{
						 if (lengthOfRoadForSewer>5){	
							 if (roadRestChrgForSewer == null || roadRestChrgForSewer==''){
								 showMessage('Please Enter Road Restoration Charge for Sewer');
								 return;
								 }
							 }
						}
					END: Changed by Rajib as per Jtrac DJB-4797 to remove validation for Road Restoration Charges of Water/Sewer for the length of Road Cut is greater than 5.
					*/	
					}
				}			
			if (isDocVerified == null || isDocVerified==''){
				$('#isDocVerified').focus();
				showMessage('Please Choose If all the documents facilitated by user are verified ');
				return;
			}

			if (djbEmpRbtAppl == null || djbEmpRbtAppl==''){
				$('#djbEmpRbtAppl').focus();
				showMessage('Please Select option for DJB Employee Rebate Applicable');
				return;
			}else{
				if (djbEmpRbtAppl == 'DJB'){
					if (empId == null || empId==''){
						showMessage('Please Enter DJB Employee ID');
						return;
						}
					if (dtOfRet == null || dtOfRet==''){
						showMessage('Please Enter Date of Retirement');
						return;
						}
					}
				}
			if (sizeOfMtr == null || sizeOfMtr==''){
				$('#sizeOfMtr').focus();
				showMessage('Please Enter Size of Meter');
				return;
			}
/*
			if (lengthOfRoadForWater == null || lengthOfRoadForWater==''){
					$('#lengthOfRoadForWater').focus();
					showMessage('Please Enter length of road to be cut for Water');
					return;
				}
			else{
					if (lengthOfRoadForWater > 5){
						if (roadRestChrgForWater == null || roadRestChrgForWater==''){
								$('#roadRestChrgForWater').focus();
								showMessage('Please Enter Applicable Road Restoration Charges for Water');
								return;
							}
						}
				}
			if (lengthOfRoadForSewer == null || lengthOfRoadForSewer==''){
					$('#lengthOfRoadForSewer').focus();
					showMessage('Please Enter length of road to be cut for Sewer');
					return;
				}else{
					if (lengthOfRoadForSewer > 5){
						if (roadRestChrgForSewer == null || roadRestChrgForSewer==''){
								$('#roadRestChrgForSewer').focus();
								showMessage('Please Enter Applicable Road Restoration Charges for Sewer');
								return;
							}
						}
				}
			*/
			if (isOccupierSecurity == null || isOccupierSecurity==''){
				$('#isOccupierSecurity').focus();
				showMessage('Please Select Is Occupier Security Applicable');
				return;
			}
			if (mrkey == null || mrkey==''){
				$('#mrkey').focus();
				showMessage('Please Enter Unique MR Code Key');
				return;
			}
			if (sewDevChrgAppl == null || sewDevChrgAppl==''){
				$('#sewDevChrgAppl').focus();
				showMessage('Please Select Sewer Development Charge Applicability');
				return;
			}
			if (devChrgColonySew == null || devChrgColonySew==''){
				$('#devChrgColonySew').focus();
				showMessage('Please Select Development Charge Colony for Sewer ');
				return;
			}
			if (watDevChrgAppl == null || watDevChrgAppl==''){
				$('#watDevChrgAppl').focus();
				showMessage('Please Select Water Development Charge Applicability');
				return;
			}
			if (devChrgColonyWat == null || devChrgColonyWat==''){
				$('#devChrgColonyWat').focus();
				showMessage('Please Select Development Charge Colony for Water ');
				return;
			}	
			if (zroCd == null || zroCd==''){
				$('#zroCd').focus();
				showMessage('Please Select ZRO Location ');
				return;
			}
			if (perId == null || perId==''){
				showMessage('Person id Value not found ');
				return;
			}
			if(confirm('Are You Sure You Want to Generate KNO for this ARN ?\n\nClick OK to Continue else Cancel')){
				hideScreen();
				$.post("kNOGenerationAJax.action",
							{
								hidAction : "generateAcct",
								arnNo: ""+arnNo,
								fileNo: ""+ fileNo,
								consumerName: ""+ consumerName,
								mobNo:""+mobNo,
								address:""+address,
								purposeOfAppl:""+purposeOfAppl,
								purposeOfApplOld:""+purposeOfApplOld,
							  	isWatUsedForPrem:""+isWatUsedForPrem,
							  	applCivilConstChrg:""+applCivilConstChrg,
							  	isUnAuthorisedUsage:""+isUnAuthorisedUsage,
							  	prefModeOfPayment:""+prefModeOfPayment,
							  	watTechFeasibility:""+watTechFeasibility,
							  	sewTechFeasibility:""+sewTechFeasibility,
							  	isDocVerified:""+isDocVerified,
							  	djbEmpRbtAppl:""+djbEmpRbtAppl,
							  	empId:""+empId,
							  	dtOfRet:""+dtOfRet,
							  	sizeOfMtr:""+sizeOfMtr,
							  	lengthOfRoadForWater:""+lengthOfRoadForWater,
							  	lengthOfRoadForSewer:""+lengthOfRoadForSewer,
							  	roadRestChrgForWater:""+roadRestChrgForWater,
							  	roadRestChrgForSewer:""+roadRestChrgForSewer,
							  	addtionalCharge:""+addtionalCharge,
							  	noOfYrForUnauthPenlaty:""+noOfYrForUnauthPenlaty,
							  	unauthPenlatyAmt:""+unauthPenlatyAmt,
							  	isOccupierSecurity:""+isOccupierSecurity,
							  	mtrSecurityCharge:""+mtrSecurityCharge,
							  	tradeSecurityCharge:""+tradeSecurityCharge,
							  	mrkey:""+mrkey,                               
							  	dtOfAppl:""+dtOfAppl,
							  	sewDevChrgAppl:""+sewDevChrgAppl,
							  	devChrgColonySew:""+devChrgColonySew,
							  	watDevChrgAppl:""+watDevChrgAppl,
							  	devChrgColonyWat:""+devChrgColonyWat,
							  	zroCd:""+zroCd,
							  	perId:""+perId,
							  	plotArea:""+plotArea,
							  	plotAreaOld:""+plotAreaOld											
							},
							function(data, status) {
							 if ((null != data) && (data.indexOf("ERROR:") < 0)) {
								 	fnResetFields();
									showMessage(''+data);	
									$('#btnView').attr('disabled', false);							
									showScreen();
							 }
		                	 else{
		                		 fnResetFields();
	    	                	 	if ((null != data) && (data.indexOf("ERROR:") > -1)){
	    	                	 		showMessage(data.substring(data.indexOf("ERROR:")));
	    	                	 	}else{
	    	                	 		showMessage('There was some problem in processing of last request');
	    	                	 	}	
		                    	}
		                    	showScreen();
							});

				}

			
		  	
//END: AJAX Call start to Save Details  	  	 
  	 }
 
</script>
<script type="text/javascript">
    var config = {
      '.chosen-select'           : {}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }

    jQuery(document).ready(function(){
    	jQuery(".chosen-select").chosen();
    });
  </script>

</body>
<%
	}catch (Exception e) {
		e.printStackTrace();
	}
%>
</html>