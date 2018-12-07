<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.DJBConstants"%>
<html>
<%
	try {
%>
<head>
<title>Dev Charge Master Data Page - Revenue Management System,
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/jQuery/css/jquery.ui.all.css"/>" />
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery-1.8.0.js"/>"></script>
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


<script language=javascript>
    var colonyId;
    var colonyCategory;
	$(function() {
		$( "#notificationDate" ).datepicker({ dateFormat: 'dd/mm/yy' });
	});
	var isPopUp = false;
	function disableBack() {
		window.history.forward(1);
	}
	disableBack();
	function fnCheckZRO() {
		if (document.forms[0].selectedZRO.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First.</b></font>";
			if (!(document.forms[0].selectedZRO.disabled)) {
			}
			return;
		}
	}
	function fnCheckColonyName() {
		if (document.forms[0].colonyName.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Provide Colony Name.</b></font>";
			return;
		}
		var colonyName = document.forms[0].colonyName.value;
		var alphaNumericRegex=<%=DJBConstants.REGEX_ALPHANUMERIC_WITH_SPACE %>;
		if(!alphaNumericRegex.test(colonyName)){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Only AlphaNumeric characters are allowed in Colony Name.</b></font>";
			return;
		}
	}
	function fnGetColonyNameID(colonyNameId) {
		var thediv = document.getElementById('colonyList-box-field');
		thediv.style.display = "none";
		thediv = document.getElementById('colonyList-box');
		thediv.style.display = "none";
		document.getElementById('headerDiv').style.display = "none";
		document.getElementById('listDiv').style.display = "none";
		document.getElementById('tableList').style.display = "none";
		document.getElementById('errorMsg').style.display = "none";
		if(colonyNameId!=null && colonyNameId.length>0 && colonyNameId!=''){
			var colonyDetails=colonyNameId.split("-");
			document.forms[0].colonyName.value=colonyDetails[0];
			colonyId=colonyDetails[1];
			fnGetDetailsByColonyId();
		}
	}	
	function fnGetDetailsByColonyId() {
		var colonyName = document.getElementById('colonyName').value;
		var selectedZRO = document.getElementById('selectedZRO').value;
		var alphaNumericRegex=<%=DJBConstants.REGEX_ALPHANUMERIC_WITH_SPACE %>;
		if(!alphaNumericRegex.test(colonyName)){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Only AlphaNumeric characters are allowed in Colony Name.</b></font>";
			document.forms[0].colonyName.focus();
			return;
		}
		document.getElementById('onscreenMessage').innerHTML = "";
		if ('' != selectedZRO && colonyName!=null && '' != colonyName && colonyId!=null && '' != colonyId) {
			var url = "devChargeMasterDataAJax.action?hidAction=getMasterData&selectedZRO="
					+ selectedZRO+"&colonyId="+ colonyId;
			//alert(url);
            hideScreen();
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
				document.getElementById('searchResultLoading').style.display='block';
				document.getElementById('searchResultDiv').style.display='block';
				document.getElementById('searchResult').innerHTML = "";
				document.getElementById('interestResultLoading').style.display='block';
				document.getElementById('interestResultDiv').style.display='block';
				document.getElementById('interestResult').innerHTML = "";
				document.getElementById('rebateResultLoading').style.display='block';
				document.getElementById('rebateResultDiv').style.display='block';
				document.getElementById('rebateResult').innerHTML = "";
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						showScreen();
						if (null == responseString
								|| responseString=='') {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						}else if (null != responseString
								&& responseString.indexOf("ERROR:") > -1) {		
							document.getElementById('onscreenMessage').innerHTML = responseString;	
						} else {
							document.getElementById('btnAdd').disabled=true;
							document.getElementById('resetBtn').disabled=true;
							document.getElementById('freeze').disabled=true;
							document.getElementById('activate').disabled=true;
							splitColonyDetailResponse(responseString);
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function splitColonyDetailResponse(responseString){
		document.getElementById('selectedZRO').disabled=true;
		document.getElementById('colonyName').disabled=true;
		document.getElementById('btnSearch').style.display='none';
		document.getElementById('btnSearchAgain').style.display='block';
		var colonyDetailResponse='';
		var splitDetailResponse='';
		var chargeType='';
		var notificationDate='';
		var status='';
		var category='';
		document.getElementById('chargeType').value='';
		document.getElementById('notificationDate').value='';
		document.getElementById('category').value='';
		splitDetailResponse=responseString.split("|");
		if(null!=splitDetailResponse[0] && !splitDetailResponse[0].indexOf("No records Found to Display") > -1){
		    colonyDetailResponse=splitDetailResponse[0].split(",");
		    chargeType=colonyDetailResponse[0];
			notificationDate=colonyDetailResponse[1];
			category=colonyDetailResponse[2];
			status=colonyDetailResponse[3];
			document.getElementById('chargeType').value=chargeType;
			document.getElementById('notificationDate').value=notificationDate;
			document.getElementById('category').value=category;
		}
		colonyCategory=document.getElementById('category').value;
		document.getElementById('category').disabled=false;
		document.getElementById('colonyDetailsDiv').style.display='block';
		document.getElementById('chargeType').disabled=true;
		document.getElementById('notificationDate').disabled=true;
		document.getElementById('searchResult').innerHTML = splitDetailResponse[1];
		document.getElementById('searchResultLoading').style.display='none';
		document.getElementById('addRateRowBtnDiv').style.display='block';
		document.getElementById('interestResultLoading').style.display='none';
		document.getElementById('interestResult').innerHTML = splitDetailResponse[2];
		document.getElementById('addInterestRowBtnDiv').style.display='block';
		document.getElementById('rebateResultLoading').style.display='none';
		document.getElementById('rebateResult').innerHTML = splitDetailResponse[3];
		document.getElementById('addRebateRowBtnDiv').style.display='block';
		document.getElementById('buttonDiv').style.display='block';
		document.getElementById('addRate').disabled=true;
		document.getElementById('addInterest').disabled=true;
		document.getElementById('addRebate').disabled=true;
		//document.getElementById('activate').disabled=true;
		var rateInterestEligibilityRowFlag='';
		var rebateEligibilityRowFlag='';
		if(document.getElementById('rateInterestEligibilityRowFlag')){
			rateInterestEligibilityRowFlag = document.getElementById('rateInterestEligibilityRowFlag').value;
			//alert('rateInterestEligibilityRowFlag:'+rateInterestEligibilityRowFlag);
		}
		if(document.getElementById('rebateEligibilityRowFlag')){
			rebateEligibilityRowFlag = document.getElementById('rebateEligibilityRowFlag').value;
			//alert('rebateEligibilityRowFlag:'+rebateEligibilityRowFlag);
		}
		if(status!=null && status!='' && status=='<%=DJBConstants.ACTIVE_FLG%>'){
			document.getElementById('addRate').disabled=false;
			if(rateInterestEligibilityRowFlag!=null && rateInterestEligibilityRowFlag=='<%=DJBConstants.FLAG_Y%>'){
				document.getElementById('addInterest').disabled=false;
			}else{
				document.getElementById('addInterest').disabled=true;
			}
			if(rebateEligibilityRowFlag!=null && rebateEligibilityRowFlag=='<%=DJBConstants.FLAG_Y%>'){
				document.getElementById('addRebate').disabled=false;
			}else{
				document.getElementById('addRebate').disabled=true;
			}
			document.getElementById('activate').disabled=true;
			document.getElementById('category').disabled=true;
			document.getElementById('statusSpan').innerHTML="Status&nbsp;<font color='green'><b>Active<b></font>";
		}else{
			document.getElementById('activate').disabled=false;
			document.getElementById('category').disabled=false;
			document.getElementById('addRate').disabled=true;
			document.getElementById('addInterest').disabled=true;
			document.getElementById('addRebate').disabled=true;
			document.getElementById('statusSpan').innerHTML="Status&nbsp;<font color='red'><b>Save<b></font>";
		}
	}
	function fnAddRateMasterDataRow(){
		var notificationDate = document.getElementById('notificationDate').value;
		if(null==notificationDate || notificationDate=='' || notificationDate.length<=0 || notificationDate=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>ERROR:Invalid Notification Date.</b></font>";
			return false;
		}
		popUpAddRateMasterData(notificationDate);
	}
	function fnAddInterestMasterDataRow(){
		var notificationDate = document.getElementById('notificationDate').value;
		if(null==notificationDate || notificationDate=='' || notificationDate.length<=0 || notificationDate=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>ERROR:Invalid Notification Date.</b></font>";
			return false;
		}
		popUpAddInterestMasterData(notificationDate);
	}
	function fnAddRebateMasterDataRow(){
		var notificationDate = document.getElementById('notificationDate').value;
		if(null==notificationDate || notificationDate=='' || notificationDate.length<=0 || notificationDate=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML="<font color='red' size='2'><b>ERROR:Invalid Notification Date.</b></font>";
			return false;
		}
		popUpAddRebateMasterData(notificationDate);
	}
	//function saveRebateMasterDataRow(rebateTypeRow,rebatePercentageRow,rebateFlatRateRow,rebateStartDateRow,rebateDocRow,
	//		rebateEndDateRow){
	function saveRebateMasterDataRow(rebateTypeRow,rebatePercentageRow,rebateFlatRateRow,rebateStartDateRow,
				rebateEndDateRow){
		var thediv = document.getElementById('rebateAdd-box-field');
        thediv.style.display = "none";
        document.getElementById('rebateAdd-box').style.display = "none";
		document.getElementById('onscreenMessage').innerHTML="";
		var url = "devChargeMasterDataAJax.action?hidAction=saveRebateDetailRow&rebateTypeRow="
			+ rebateTypeRow+"&rebatePercentageRow="+ rebatePercentageRow+"&rebateFlatRateRow="+ rebateFlatRateRow
			+"&colonyId="+colonyId+"&rebateStartDateRow="+ rebateStartDateRow+"&rebateEndDateRow="+ rebateEndDateRow;
		//alert(url);
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
					showScreen();
					if (null == responseString
							|| responseString == '') {
						//thediv.style.height=$( document ).height();
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						}else if (null!=responseString
							&& responseString.indexOf("ERROR:") > -1 ) {
						//thediv.style.height=$( document ).height();
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}else{
							document.getElementById('onscreenMessage').innerHTML = responseString;
							alert("Data saved Successfully.");
							fnGetDetailsByColonyId();
					}
				}     
			};  
			httpBowserObject.send(null);
		}
	}
	//function saveRateMasterDataRow(applicablRateRow,rebateEligibilityRow,rateMinAreaRow,
	//		rateTypeRow,rateMaxAreaRow,rateCategoryRow,rateStartDateRow,rateEndDateRow,rateDocRow){
	function saveRateMasterDataRow(applicablRateRow,rebateEligibilityRow,rateMinAreaRow,
				rateTypeRow,rateMaxAreaRow,rateCategoryRow,rateStartDateRow,rateEndDateRow,rateInterestEligibilityRow){
		document.getElementById('rateAdd-box').style.display='none';
		document.getElementById('rateAdd-box-field').style.display='none';
		document.getElementById('onscreenMessage').innerHTML="";
		var url = "devChargeMasterDataAJax.action?hidAction=saveRateDetailRow&applicablRateRow="
			+ applicablRateRow+"&rebateEligibilityRow="+ rebateEligibilityRow+"&rateMinAreaRow="+ rateMinAreaRow
			+"&colonyId="+colonyId+"&rateTypeRow="+ rateTypeRow+"&rateMaxAreaRow="+ rateMaxAreaRow+"&rateCategoryRow="+ rateCategoryRow
			+"&rateStartDateRow="+ rateStartDateRow+"&rateEndDateRow="+ rateEndDateRow+"&rateInterestEligibilityRow="+rateInterestEligibilityRow;
		//alert(url);
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
					showScreen();
					if (null == responseString
							|| responseString=='') {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
					}else if (null != responseString
							&& responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = responseString;
					    }else{
							document.getElementById('onscreenMessage').innerHTML=responseString;
							alert("Data saved Successfully.");
							fnGetDetailsByColonyId();
					     }
				}     
			};  
			httpBowserObject.send(null);
		}
	}
	//function saveInterestMasterDataRow(interestPercentageRow,interestStartDateRow,interestDocRow,
	//		interestEndDateRow){
	function saveInterestMasterDataRow(interestPercentageRow,interestStartDateRow,
				interestEndDateRow){
		var thediv = document.getElementById('interestAdd-box-field');
        thediv.style.display = "none";
        document.getElementById('interestAdd-box').style.display = "none";
		document.getElementById('onscreenMessage').innerHTML="";
		var url = "devChargeMasterDataAJax.action?hidAction=saveInterestDetailRow&interestPercentageRow="
			+ interestPercentageRow+"&interestStartDateRow="+ interestStartDateRow+"&interestEndDateRow="+ interestEndDateRow
			+"&colonyId="+colonyId;
		//alert(url);
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
					showScreen();
					if (null == responseString
							|| responseString=='') {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
					}else if (null != responseString
							&& responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = responseString;
					    }else{
							window.focus();
							document.getElementById('onscreenMessage').innerHTML=responseString;
							alert("Data saved Successfully.");
							fnGetDetailsByColonyId();
					    }
				}     
			};  
			httpBowserObject.send(null);
		}
	}

	function splitAddDetailResponse(responseString){
		var response=responseString.split("|");
		document.getElementById('searchResultLoading').style.display='none';
		document.getElementById('searchResult').innerHTML = response[0];
		//alert('2:'+splitDetailResponse[2]);
		document.getElementById('interestResultLoading').style.display='none';
		document.getElementById('interestResult').innerHTML = response[1];
		//alert('3:'+splitDetailResponse[3]);
		document.getElementById('rebateResultLoading').style.display='none';
		document.getElementById('rebateResult').innerHTML = response[2];
		document.getElementById('buttonDiv').style.display='block';
		document.getElementById('freeze').disabled=false;
	}
	function checkValWithDot(e){
		try {
	        var unicode=e.charCode? e.charCode : e.keyCode;
			if (unicode!=8){ 
	               if (unicode!=46 && (unicode<48||unicode>57)) 
					return false ;
				}                        
	       	}catch (e) {         
	    	}
	}
	
	function validateRate(paramId, paramName, errorId){
		document.getElementById(errorId).innerHTML='';
	    var paramId=document.getElementById(paramId).value;
		if (paramId==null || paramId==''){
	 	 	document.getElementById(errorId).innerHTML='<font color="red" size="2"><b>&nbsp;'+paramName+' is Blank.</b></font>';
	 	 	return false;
	 	}
		var NumericWithDotRegex=<%=DJBConstants.REGEX_NUMERIC_WITH_DOT %>;
		//alert(NumericWithDotRegex);
		var isvalid=NumericWithDotRegex.test(paramId);
		if (!isvalid){
	 	 	document.getElementById(errorId).innerHTML='<font color="red" size="2"><b>&nbsp;Invalid '+paramName+'.</b></font>';
	 	 	return false;
	 	}
	 	
	 	if (paramName!='Rate Min. Area' && parseFloat(paramId)<=parseFloat(0)){
	 		document.getElementById(errorId).innerHTML='<font color="red" size="2"><b>&nbsp;'+paramName+' should be greater than 0.</b></font>';
	 	 	return false;
	 	}
		if (paramName=='Rate Min. Area' && parseFloat(paramId)<parseFloat(0)){
			document.getElementById(errorId).innerHTML='<font color="red" size="2"><b>&nbsp;'+paramName+' should be equal to or greater than 0.</b></font>';
	 	 	return false;
	 	}
	 	return true;
	}
	function showCalender(id){
		if(null!=document.getElementById(id)){
			$(function() {
				$( '#'+id ).datepicker({ dateFormat: 'dd/mm/yy' });
			});
		}
	}
	function fnSearchColony(){
		document.getElementById("onscreenMessage").innerHTML='';
		var option=document.getElementById('colonyName').value;
		var selectedZRO=document.forms[0].selectedZRO.value;
		if (document.forms[0].selectedZRO.value == null || document.forms[0].selectedZRO.value == '' || document.forms[0].selectedZRO.value == 'Please Select') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a Zone.<b></font>";
			document.forms[0].selectedZRO.focus();
			return false;
		}
		if (option==null || option==''){
	 	 	document.getElementById("onscreenMessage").innerHTML='<font color="red" size="2"><b>&nbsp;Please Enter Colony to Search.</b></font>';
	 	 	return;
	 	}
 		if (document.forms[0].colonyName.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Provide Colony Name.</b></font>";
			return false;
		}
 		var alphaNumericRegex=<%=DJBConstants.REGEX_ALPHANUMERIC_WITH_SPACE %>;
		if(!alphaNumericRegex.test(option)){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Only AlphaNumeric characters are allowed in Colony Name.</b></font>";
			return;
		}
		document.getElementById('btnAdd').disabled=true;
		//document.getElementById('resetBtn').disabled=true;
		//document.getElementById('freeze').disabled=true;
		//document.getElementById('activate').disabled=true;
		document.getElementById('colonyDetailsDiv').style.display='none';
		document.getElementById('chargeType').value='';
		document.getElementById('notificationDate').value='';
		document.getElementById('category').value='';
		document.getElementById('addNewColonyRateDiv').style.display='none';
		document.getElementById('addNewColonyInterestDiv').style.display='none';
		document.getElementById('addNewColonyRebateDiv').style.display='none';
		document.getElementById('buttonDiv').style.display='none';
			var url = "devChargeMasterDataAJax.action?hidAction=searchColonyList&option="+ option+"&selectedZRO="+selectedZRO;
			//alert(url);
			hideScreen();
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
						document.getElementById('btnAdd').disabled=false;
						showScreen();
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1 || responseString.indexOf('<script') > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
								document.getElementById('onscreenMessage').title = "";
								fnToReset();
								resetOnLoadDiv();
							}else if (null!= responseString
									&& responseString.indexOf("INVALID") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>No Colony Found.</b></font>";
								document.getElementById('onscreenMessage').title = "";
								fnToReset();
								resetOnLoadDiv();
							}else{
								popUpColonyListScreen(responseString);
							} 
						
					}
				};
				httpBowserObject.send(null);
			}
	}
	function fnGoToActivate(){
		document.getElementById("onscreenMessage").innerHTML='';
		var category=document.getElementById('category').value;
		if (category==null || category=='' || category=='Please Select' || category.length<=0){
	 	 	document.getElementById("onscreenMessage").innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Category.</b></font>';
	 	 	return;
	 	}
		if (colonyId==null || colonyId==''){
	 	 	document.getElementById("onscreenMessage").innerHTML='<font color="red" size="2"><b>&nbsp;Please Search Colony Again.</b></font>';
	 	 	return;
	 	}
	 	var url = "devChargeMasterDataAJax.action?hidAction=activateColony&category="+ category+"&colonyId="+colonyId;
	 	//alert(url);
	 	hideScreen();
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
					showScreen();
					if (null == responseString
							|| responseString=='') {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						}else if(responseString!=null && responseString.indexOf("ERROR:") > -1 ){
							document.getElementById('onscreenMessage').innerHTML = responseString;
						}else{
							document.getElementById('onscreenMessage').innerHTML=responseString;
							alert("Colony Activated Successfully.");
							fnGetDetailsByColonyId();
						}
					}
				};
			httpBowserObject.send(null);
		}
	}
	function resetOnLoadDiv(){
		document.getElementById('buttonDiv').style.display='none';
		document.getElementById('addNewColonyRebateDiv').style.display='none';
		document.getElementById('addNewColonyInterestDiv').style.display='none';
		document.getElementById('addNewColonyRateDiv').style.display='none';
		document.getElementById('colonyDetailsDiv').style.display='none';
		document.getElementById('searchResultDiv').style.display='none';
		document.getElementById('interestResultDiv').style.display='none';
		document.getElementById('rebateResultDiv').style.display='none';
	}
	function fnToReset(){
		document.getElementById('chargeType').value='';
		document.getElementById('notificationDate').value='';
		document.getElementById('category').value='';
		document.getElementById('applicableRateNewColony').value='';
		document.getElementById('rebateEligibilityNewColony').value='';
		document.getElementById('rateInterestEligibilityNewColony').value='';
		document.getElementById('rateTypeNewColony').value='';
	    document.getElementById('rateCategoryNewColony').value='';
		document.getElementById('rateMinAreaNewColony').value='';
		document.getElementById('rateMaxAreaNewColony').value='';
		document.getElementById('rateStartDateNewColony').value='';
		document.getElementById('rateEndDateNewColony').value='';
		//document.getElementById('rateDocNewColony').value='';
		document.getElementById('interestPercentageNewColony').value='';
		document.getElementById('interestStartDateNewColony').value='';
		document.getElementById('interestEndDateNewColony').value='';
		//document.getElementById('interestDocNewColony').value='';
		document.getElementById('rebateFlatRateNewColony').value='';
		document.getElementById('rebatePercentageNewColony').value='';
		document.getElementById('rebateStartDateNewColony').value='';
		document.getElementById('rebateEndDateNewColony').value='';
		//document.getElementById('rebateDocNewColony').value='';
		document.getElementById('rebateTypeNewColony').value='';
		document.getElementById('rebateFlatRateNewColony').value='';
		document.getElementById('rebatePercentageNewColony').value='';
		document.getElementById('rebateStartDateNewColony').value='';
		document.getElementById('rebateEndDateNewColony').value='';
	}
	function saveNewColonyDetail(){
		document.getElementById("onscreenMessage").innerHTML='';
		var selectedZRO=document.forms[0].selectedZRO.value;
		var colonyName=document.forms[0].colonyName.value;
		var chargeType=document.getElementById('chargeType').value;
		var notificationDate=document.getElementById('notificationDate').value;
		var category=document.getElementById('category').value;
		var applicableRateNewColony=document.getElementById('applicableRateNewColony').value;
		var rateInterestEligibilityNewColony=document.getElementById('rateInterestEligibilityNewColony').value;
		var rebateEligibilityNewColony=document.getElementById('rebateEligibilityNewColony').value;
		var rateTypeNewColony=document.forms[0].rateTypeNewColony.value;
		//var rateTypeNewColony=document.getElementById('rateTypeNewColony').value;
		var rateCategoryNewColony=document.getElementById('rateCategoryNewColony').value;
		var rateMinAreaNewColony=document.getElementById('rateMinAreaNewColony').value;
		var rateMaxAreaNewColony=document.getElementById('rateMaxAreaNewColony').value;
		var rateStartDateNewColony=document.getElementById('rateStartDateNewColony').value;
		var rateEndDateNewColony=document.getElementById('rateEndDateNewColony').value;
		//var rateDocNewColony=document.getElementById('rateDocNewColony').value;
		var interestPercentageNewColony=document.getElementById('interestPercentageNewColony').value;
		var interestStartDateNewColony=document.getElementById('interestStartDateNewColony').value;
		var interestEndDateNewColony=document.getElementById('interestEndDateNewColony').value;
		//var interestDocNewColony=document.getElementById('interestDocNewColony').value;
		var rebateFlatRateNewColony=document.getElementById('rebateFlatRateNewColony').value;
		var rebatePercentageNewColony=document.getElementById('rebatePercentageNewColony').value;
		var rebateStartDateNewColony=document.getElementById('rebateStartDateNewColony').value;
		var rebateEndDateNewColony=document.getElementById('rebateEndDateNewColony').value;
		//var rebateDocNewColony=document.getElementById('rebateDocNewColony').value;
		var rebateTypeNewColony=document.getElementById('rebateTypeNewColony').value;
		if (selectedZRO==null || selectedZRO == '' || selectedZRO == 'Please Select') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a Zone.<b></font>";
			document.forms[0].selectedZRO.focus();
			return false;
		}
		if (null==colonyName || colonyName == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Provide Colony Name.</b></font>";
			document.forms[0].colonyName.focus();
			return;
		}
		var alphaNumericRegex=<%=DJBConstants.REGEX_ALPHANUMERIC_WITH_SPACE %>;
		if(!alphaNumericRegex.test(colonyName)){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Only AlphaNumeric characters are allowed in Colony Name.</b></font>";
			document.forms[0].colonyName.focus();
			return;
		}
		if (chargeType==null || chargeType == '' || chargeType == 'Please Select') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a Charge Type.<b></font>";
			return false;
		}
		if(null==notificationDate || notificationDate==''){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Notification Date.</b></font>';
			return false;
		}
		var todayDate = new Date();
		var splitElementsNotifyDate = notificationDate.split('/');
		var notifyDateFormat = new Date (splitElementsNotifyDate[2], splitElementsNotifyDate[1] - 1,splitElementsNotifyDate[0]);
		if(notifyDateFormat>todayDate){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Notification Date cannot be future date.</b></font>";
			return false;
		}
		if(null==category || category=='' || category == 'Please Select'){
			category='';
		}
		if(null==applicableRateNewColony || applicableRateNewColony==''){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Applicable Rate is Mandatory.</b></font>';
			return false;
		}
		if(null==rateInterestEligibilityNewColony || rateInterestEligibilityNewColony=='' || rateInterestEligibilityNewColony=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Interest Eligibilty.</b></font>';
			return false;
		}
		if(null==rebateEligibilityNewColony || rebateEligibilityNewColony=='' || rebateEligibilityNewColony=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Rebate Eligibilty.</b></font>';
			return false;
		}
		//if(null==rateMinAreaNewColony || Trim(rateMinAreaNewColony)==''){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rate Minimum Area is Madatory.</b></font>';
		//	return false;
		//}
		//if(null==rateMaxAreaNewColony || Trim(rateMaxAreaNewColony)==''){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rate Maximum Area is Madatory.</b></font>';
		//	return false;
		//}
		if(null==rateTypeNewColony || rateTypeNewColony=='' || rateTypeNewColony=='Please Select'){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Rate Type.</b></font>';
			return false;
		}
		if(null==rateCategoryNewColony || rateCategoryNewColony=='' || rateCategoryNewColony=='Please Select'){
			rateCategoryNewColony='';
		}
		if(null==rateStartDateNewColony || rateStartDateNewColony==''){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Rate Start Date.</b></font>';
			return false;
		}
		if(null==rateEndDateNewColony || rateEndDateNewColony==''){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Rate End Date.</b></font>';
			return false;
		}
		if(!validateRate('applicableRateNewColony', 'Applicable Rate', 'onscreenMessage')){
			return false;
		}
		if(null!=rateMinAreaNewColony && rateMinAreaNewColony!='' && rateMinAreaNewColony.length>0 && !validateRate('rateMinAreaNewColony', 'Rate Min. Area', 'onscreenMessage')){
			return false;
		}
		if(null!=rateMaxAreaNewColony && rateMaxAreaNewColony!='' && rateMaxAreaNewColony.length>0 && !validateRate('rateMaxAreaNewColony', 'Rate Max. Area', 'onscreenMessage')){
			return false;
		}
		if(null!=rateMinAreaNewColony && rateMinAreaNewColony!='' && (rateMaxAreaNewColony==null || rateMaxAreaNewColony=='')){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please provide Rate Max. Area.</b></font>';
			return false;
		}
		if(null!=rateMaxAreaNewColony && rateMaxAreaNewColony!='' && (rateMinAreaNewColony==null || rateMinAreaNewColony=='')){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please provide Rate Min. Area.</b></font>';
			return false;
		}
		if(null!=rateMaxAreaNewColony && null!=rateMinAreaNewColony && parseFloat(rateMaxAreaNewColony)<parseFloat(rateMinAreaNewColony)){
			document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Max Area should be always greater than Min Area.</b></font>';
			return false;
		}
		//alert(rateInterestEligibilityNewColony);
		if(rateInterestEligibilityNewColony=='<%=DJBConstants.FLAG_Y%>'){
				
			if(null==interestPercentageNewColony || interestPercentageNewColony==''){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Interest Percentage is Mandatory.</b></font>';
				return false;
			}
			if(null==interestStartDateNewColony || interestStartDateNewColony==''){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Interest Start Date.</b></font>';
				return false;
			}
			if(null==interestEndDateNewColony || interestEndDateNewColony==''){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Interest End Date.</b></font>';
				return false;
			}
			if(!validateRate('interestPercentageNewColony', 'Interest Percentage', 'onscreenMessage')){
				return false;
			}
		}else{
			interestPercentageNewColony='';
			interestStartDateNewColony='';
			interestEndDateNewColony='';
		}
		if(rebateEligibilityNewColony=='<%=DJBConstants.FLAG_Y%>'){
			if(null==rebateTypeNewColony || rebateTypeNewColony=='' || rebateTypeNewColony.length<0 || rebateTypeNewColony=='Please Select'){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Select Rebate Type.</b></font>';
				return false;
			}
			if(rebateTypeNewColony=='<%=DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD %>'){
				if(null==rebatePercentageNewColony || rebatePercentageNewColony=='' || rebatePercentageNewColony<0){
					document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Provide Rebate Percentage.</b></font>';
					return false;
				}
				if(null!=rebatePercentageNewColony && rebatePercentageNewColony!='' && !validateRate('rebatePercentageNewColony', 'Rebate Percentage', 'onscreenMessage')){
					return false;
				}
			}else if(rebateTypeNewColony=='<%=DJBConstants.DEV_CHRG_FLAT_REBATE_CD %>'){
				if(null==rebateFlatRateNewColony || rebateFlatRateNewColony=='' || rebateFlatRateNewColony<0){
				    document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Please Provide Rebate Flat Rate.</b></font>';
					return false;
				}
				if(null!=rebateFlatRateNewColony && rebateFlatRateNewColony!='' && !validateRate('rebateFlatRateNewColony', 'Rebate Flat Rate', 'onscreenMessage')){
					return false;
				}
			}
			if(null==rebateStartDateNewColony || rebateStartDateNewColony==''){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Rebate Start Date.</b></font>';
				return false;
			}
			if(null==rebateEndDateNewColony || rebateEndDateNewColony==''){
				document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Invalid Rebate End Date.</b></font>';
				return false;
			}
		}else{
			rebateTypeNewColony="";
			rebatePercentageNewColony="";
			rebateFlatRateNewColony="";
			rebateStartDateNewColony="";
			rebateEndDateNewColony="";
		}	
		//if(null==rebateTypeNewColony || Trim(rebateTypeNewColony)=='' || Trim(rebateTypeNewColony)=='Please Select'){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rebate Type is Mandatory.</b></font>';
		//	return false;
		//}
		//if(null==rebatePercentageNewColony || Trim(rebatePercentageNewColony)==''){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rebate Percentage is Mandatory.</b></font>';
		//	return false;
		//}
		//if(null==rebateFlatRateNewColony || Trim(rebateFlatRateNewColony)==''){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rebate Flat Rate is Mandatory.</b></font>';
		//	return false;
		//}
		//if(null==rebateEndDateNewColony || Trim(rebateEndDateNewColony)==''){
		//	document.getElementById('onscreenMessage').innerHTML='<font color="red" size="2"><b>&nbsp;Rebate End Date is Mandatory.</b></font>';
		//	return false;
		//}
		var url = "devChargeMasterDataAJax.action?hidAction=saveNewColonyDetail&selectedZRO="+selectedZRO+"&colonyName="+colonyName+"&applicableRateNewColony="+applicableRateNewColony+"&chargeType="+chargeType+"&notificationDate="+notificationDate
		+"&category="+category+"&rebateEligibilityNewColony="+rebateEligibilityNewColony+"&rateTypeNewColony="+rateTypeNewColony+"&rateCategoryNewColony="+rateCategoryNewColony+"&rateMinAreaNewColony="+rateMinAreaNewColony+"&rateMaxAreaNewColony="+rateMaxAreaNewColony
		+"&rateStartDateNewColony="+rateStartDateNewColony+"&rateEndDateNewColony="+rateEndDateNewColony+"&interestPercentageNewColony="+interestPercentageNewColony+"&interestStartDateNewColony="+interestStartDateNewColony+"&interestEndDateNewColony="+interestEndDateNewColony
		+"&rebateFlatRateNewColony="+rebateFlatRateNewColony+"&rebatePercentageNewColony="+rebatePercentageNewColony+"&rebateStartDateNewColony="+rebateStartDateNewColony+"&rebateEndDateNewColony="+rebateEndDateNewColony+"&rebateTypeNewColony="+rebateTypeNewColony+"&rateInterestEligibilityNewColony="+rateInterestEligibilityNewColony;
		//alert(url);
		hideScreen();
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
					showScreen();
					if (null == responseString
							|| responseString.indexOf("ERROR:") > -1 || responseString.indexOf('<script') > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: Unable to process your request.Please retry after sometime or contact System Administrator. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						}else if(null!=responseString && responseString.indexOf("|") > -1){
							var response=responseString.split("|");
							document.getElementById('onscreenMessage').innerHTML=response[0];
							alert("Data saved successfully.");
							colonyId=response[1];
							fnToReset();
							resetOnLoadDiv();
							fnGetDetailsByColonyId();
							//document.getElementById('searchResultDiv').style.display='none';
							//document.getElementById('colonyDetailsDiv').style.display='none';
							//document.getElementById('interestResultDiv').style.display='none';
							//document.getElementById('rebateResultDiv').style.display='none';
							//document.getElementById('addNewColonyRateDiv').style.display='none';
							//document.getElementById('addNewColonyInterestDiv').style.display='none';
							//document.getElementById('addNewColonyRebateDiv').style.display='none';
							//document.getElementById('buttonDiv').style.display='none';
						}
					}
				};
			httpBowserObject.send(null);
		}
		
	}
	function fnToAddDetails(){
		document.getElementById('onscreenMessage').innerHTML="&nbsp;";
		document.forms[0].chargeType.value="";
		var selectedZRO=document.forms[0].selectedZRO.value;
		var colonyName=document.forms[0].colonyName.value;
		if (selectedZRO==null || selectedZRO == '' || selectedZRO == 'Please Select') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a Zone.<b></font>";
			return false;
		}
		if (null==colonyName || colonyName == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Provide Colony Name.</b></font>";
			return;
		}
		document.getElementById('btnSearch').style.display='none';
		document.getElementById('btnSearchAgain').style.display='block';
		document.getElementById('btnSearch').disabled=true;
		document.getElementById('colonyDetailsDiv').style.display='block';
		document.getElementById('chargeType').value='';
		document.getElementById('chargeType').disabled=false;
		document.getElementById('notificationDate').disabled=false;
		document.getElementById('notificationDate').value='';
		document.getElementById('category').disabled=false;
		document.getElementById('category').value='';
		document.getElementById('searchResultDiv').style.display='none';
		document.getElementById('interestResultDiv').style.display='none';
		document.getElementById('rebateResultDiv').style.display='none';
		document.getElementById('addNewColonyRateDiv').style.display='block';
		document.getElementById('addNewColonyInterestDiv').style.display='block';
		document.getElementById('interestPercentageNewColony').disabled=true;
		document.getElementById('interestStartDateNewColony').disabled=true;
		document.getElementById('interestEndDateNewColony').disabled=true;
		document.getElementById('addNewColonyRebateDiv').style.display='block';
		document.getElementById('rebateTypeNewColony').disabled=true;
		document.getElementById('rebateFlatRateNewColony').disabled=true;
		document.getElementById('rebatePercentageNewColony').disabled=true;
		document.getElementById('rebateStartDateNewColony').disabled=true;
		document.getElementById('rebateEndDateNewColony').disabled=true;
		//document.getElementById('rebateDocNewColony').disabled=true;
		document.getElementById('buttonDiv').style.display='block';
		document.getElementById('freeze').disabled=false;
		document.getElementById('resetBtn').disabled=false;
		document.getElementById('activate').disabled=true;
		fnToReset();
	}
	function validateRebateTypeNewColony(){
		var endDateIntervalInt=0;
		var rebateType=document.getElementById('rebateTypeNewColony').value;
		var notificationDate=document.getElementById('notificationDate').value;
		if(notificationDate==null || notificationDate=='' || notificationDate.length<=0){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please provide Notification Date first.</b></font>";
			return false;
			}
			if(rebateType!=null && rebateType!='' && rebateType!='Please Select'){
				if(rebateType=='<%=DJBConstants.DEV_CHRG_FLAT_REBATE_CD %>'){
					document.getElementById('rebatePercentageNewColony').value='';
					document.getElementById('rebatePercentageNewColony').disabled=true;
					document.getElementById('rebateFlatRateNewColony').disabled=false;
				}else if(rebateType=='<%=DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD %>'){
					document.getElementById('rebateFlatRateNewColony').value='';
					document.getElementById('rebateFlatRateNewColony').disabled=true;
					document.getElementById('rebatePercentageNewColony').disabled=false;
				}
				var splitElements = notificationDate.split('/');
				var notifyDateFormat = new Date (splitElements[2], splitElements[1] - 1,splitElements[0]);
				document.getElementById('rebateStartDateNewColony').value=notificationDate;
				document.getElementById('rebateStartDateNewColony').disabled=true;
				<% try{%>
				endDateIntervalInt = parseInt("<%=DJBConstants.DEV_CHRG_DATE_INTERVAL_IN_DAYS %>");
					<% }catch(Exception e){
				}%>
				notifyDateFormat.setDate(notifyDateFormat.getDate() + endDateIntervalInt);
				var day=notifyDateFormat.getDate();
				if(day<=9){
					day='0'+ day;
				}
				var month=notifyDateFormat.getMonth()+1;
				if(month<=9){
					month='0'+ month;
				}
				// Start : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
				//var year=notifyDateFormat.getYear();
				var year=notifyDateFormat.getFullYear();
				// End : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
				document.getElementById('rebateEndDateNewColony').value=day+"/"+month+"/"+year;
				document.getElementById('rebateEndDateNewColony').disabled=true;
			}else{
				document.getElementById('rebateFlatRateNewColony').value='';
				document.getElementById('rebateFlatRateNewColony').disabled=true;
				document.getElementById('rebatePercentageNewColony').value='';
				document.getElementById('rebatePercentageNewColony').disabled=true;
				document.getElementById('rebateStartDateNewColony').value='';
				document.getElementById('rebateStartDateNewColony').disabled=true;
				document.getElementById('rebateEndDateNewColony').value='';
				document.getElementById('rebateEndDateNewColony').disabled=true;
			}
		}
	function validateInterestEligibilityNewColony(){
		var startDateIntervalInt=0;
		var rateInterestEligibilityNewColony=document.getElementById('rateInterestEligibilityNewColony').value;
		var notificationDate=document.getElementById('notificationDate').value;
		if(notificationDate==null || notificationDate=='' || notificationDate.length<=0){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please provide Notification Date first.</b></font>";
			return false;
			}else if(rateInterestEligibilityNewColony!=null && rateInterestEligibilityNewColony=='<%=DJBConstants.FLAG_Y%>'){
				document.getElementById('interestPercentageNewColony').value="";
				document.getElementById('interestPercentageNewColony').disabled=false;
				var splitElements = notificationDate.split('/');
				var notifyDateFormat = new Date (splitElements[2], splitElements[1] - 1,splitElements[0]);
				//alert(<%=session.getAttribute(DJBConstants.YEAR_INTERVAL_PARAM)%>);
				<% try{%>
				 startDateIntervalInt = parseInt("<%=session.getAttribute(DJBConstants.YEAR_INTERVAL_PARAM)%>");
				// startDateIntervalInt = parseInt("<%=DJBConstants.DEV_CHRG_DATE_INTERVAL_IN_YEAR %>");
					<% }catch(Exception e){
				}%>
				notifyDateFormat.setDate(notifyDateFormat.getDate() + startDateIntervalInt);
				//notifyDateFormat.setFullYear(notifyDateFormat.getFullYear() + startDateIntervalInt);
				var day=notifyDateFormat.getDate();
				if(day<=9){
					day='0'+ day;
				}
				var month=notifyDateFormat.getMonth()+1;
				if(month<=9){
					month='0'+ month;
				}
				// Start : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
				//var year=notifyDateFormat.getYear();
				var year=notifyDateFormat.getFullYear();
				// End : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
				document.getElementById('interestStartDateNewColony').value=day+"/"+month+"/"+year;
				document.getElementById('interestStartDateNewColony').disabled=true;
				document.getElementById('interestEndDateNewColony').value='<%=DJBConstants.DEV_CHRG_ROW_DEFAULT_END_DATE %>';
				document.getElementById('interestEndDateNewColony').disabled=true;
			}else{
				document.getElementById('interestPercentageNewColony').value="";
				document.getElementById('interestPercentageNewColony').disabled=true;
				document.getElementById('interestStartDateNewColony').value="";
				document.getElementById('interestStartDateNewColony').disabled=true;
				document.getElementById('interestEndDateNewColony').value="";
				document.getElementById('interestEndDateNewColony').disabled=true;
			}
		}
	function validateRebateEligibilityNewColony(){
		var rebateEligibilityNewColony=document.getElementById('rebateEligibilityNewColony').value;
		var notificationDate=document.getElementById('notificationDate').value;
		if(rebateEligibilityNewColony==null || rebateEligibilityNewColony=='' || rebateEligibilityNewColony<=0 || rebateEligibilityNewColony=='Please Select'){
			//document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Rebate Eligibilty.</b></font>";
			document.getElementById('rebateTypeNewColony').disabled=true;
		}
		if(rebateEligibilityNewColony=='<%=DJBConstants.FLAG_N%>'){
			document.getElementById('rebateTypeNewColony').disabled=true;
		}
		if(rebateEligibilityNewColony=='<%=DJBConstants.FLAG_Y%>'){
			document.getElementById('rebateTypeNewColony').disabled=false;
		}
	}
	function validateNotifyDate(){
		document.getElementById('onscreenMessage').innerHTML="&nbsp;";
		var startDateIntervalInt=0;
		var endDateIntervalInt=0;
		var todayDate = new Date();
		var rateInterestEligibilityNewColony=document.getElementById('rateInterestEligibilityNewColony').value;
		var notificationDate=document.getElementById('notificationDate').value;
		var rebateType=document.getElementById('rebateTypeNewColony').value;
		if(document.getElementById('notificationDate').disabled==false){
			if(notificationDate==null || notificationDate=='' || notificationDate.length<=0){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please provide Notification Date first.</b></font>";
				return false;
				}
			var splitElementsNotifyDate = notificationDate.split('/');
			var notifyDateFormat = new Date (splitElementsNotifyDate[2], splitElementsNotifyDate[1] - 1,splitElementsNotifyDate[0]);
		    if(notifyDateFormat>todayDate){
		    	document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Notification Date cannot be future date.</b></font>";
				return false;
			}else{
					document.getElementById('rateStartDateNewColony').value=notificationDate;
					document.getElementById('rateStartDateNewColony').disabled=true;
					document.getElementById('rateEndDateNewColony').value='<%=DJBConstants.DEV_CHRG_ROW_DEFAULT_END_DATE %>';
					document.getElementById('rateEndDateNewColony').disabled=true;
					if(rateInterestEligibilityNewColony!=null && rateInterestEligibilityNewColony=='<%=DJBConstants.FLAG_Y%>'){
							var splitElements = notificationDate.split('/');
							var notifyDateFormat = new Date (splitElements[2], splitElements[1] - 1,splitElements[0]);
							<% try{%>
							startDateIntervalInt = parseInt("<%=session.getAttribute(DJBConstants.YEAR_INTERVAL_PARAM)%>");
							//startDateIntervalInt = parseInt("<%=DJBConstants.DEV_CHRG_DATE_INTERVAL_IN_YEAR %>");
								<% }catch(Exception e){
							}%>
							notifyDateFormat.setDate(notifyDateFormat.getDate() + startDateIntervalInt);
							//notifyDateFormat.setFullYear(notifyDateFormat.getFullYear() + startDateIntervalInt);
							var day=notifyDateFormat.getDate();
							if(day<=9){
								day='0'+ day;
							}
							var month=notifyDateFormat.getMonth()+1;
							if(month<=9){
								month='0'+ month;
							}
							// Start : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
							//var year=notifyDateFormat.getYear();
							var year=notifyDateFormat.getFullYear();
							// End : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
							document.getElementById('interestStartDateNewColony').value=day+"/"+month+"/"+year;
							document.getElementById('interestStartDateNewColony').disabled=true;
							document.getElementById('interestEndDateNewColony').value='<%=DJBConstants.DEV_CHRG_ROW_DEFAULT_END_DATE %>';
							document.getElementById('interestEndDateNewColony').disabled=true;
				}else{
					document.getElementById('interestStartDateNewColony').value="";
					document.getElementById('interestStartDateNewColony').disabled=true;
					document.getElementById('interestEndDateNewColony').value="";
					document.getElementById('interestEndDateNewColony').disabled=true;
				}
				if(rebateType!=null && rebateType!='' && rebateType!='Please Select'){
						//if(rebateType=='<%=DJBConstants.DEV_CHRG_FLAT_REBATE_CD %>'){
						//	document.getElementById('rebateFlatRateNewColony').disabled=false;
						//}else if(rebateType=='<%=DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD %>'){
						//	document.getElementById('rebatePercentageNewColony').disabled=false;
						//}
						var splitElements = notificationDate.split('/');
						var notifyDateFormat = new Date (splitElements[2], splitElements[1] - 1,splitElements[0]);
						document.getElementById('rebateStartDateNewColony').value=notificationDate;
						document.getElementById('rebateStartDateNewColony').disabled=true;
						<% try{%>
						endDateIntervalInt = parseInt("<%=DJBConstants.DEV_CHRG_DATE_INTERVAL_IN_DAYS %>");
							<% }catch(Exception e){
						}%>
						notifyDateFormat.setDate(notifyDateFormat.getDate() + endDateIntervalInt);
						var day=notifyDateFormat.getDate();
						if(day<=9){
							day='0'+ day;
						}
						var month=notifyDateFormat.getMonth()+1;
						if(month<=9){
							month='0'+ month;
						}
						// Start : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
						//var year=notifyDateFormat.getYear();
						var year=notifyDateFormat.getFullYear();
						// End : changed by Reshma on 16-09-2015 as depreciated method which might cause problem in some scenarios, DJB-4162.
						document.getElementById('rebateEndDateNewColony').value=day+"/"+month+"/"+year;
						document.getElementById('rebateEndDateNewColony').disabled=true;
					}else{
						document.getElementById('rebateStartDateNewColony').value="";
						document.getElementById('rebateStartDateNewColony').disabled=true;
						document.getElementById('rebateEndDateNewColony').disabled=true;
						document.getElementById('rebateEndDateNewColony').value="";
					}
				}
		}
	}
	function enableActivateBtn(){
		var category=document.getElementById('category').value;
		if(category==null || category=='' || category.length<0 || category==colonyCategory){
		    document.getElementById('activate').disabled=true;
		}else{
			document.getElementById('activate').disabled=false;
		}
	}
	
</script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<%@include file="../jsp/DevChargeColonyList.html"%>
<%@include file="../jsp/DevChargeAddRateData.html"%>
<%@include file="../jsp/DevChargeAddInterestData.html"%>
<%@include file="../jsp/DevChargeAddRebateData.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
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
					<table width="100%" border="0">
						<tr>
							<td>
							<fieldset><legend>Search Criteria</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="right" width="10%"><label>ZRO</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="30%" title="Zone" nowrap><s:select
										list="#session.ZROListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" name="selectedZRO" id="selectedZRO"
										onchange="fnCheckZRO(this);" /></td>
									<td align="left" width="45%"><label>Colony Name</label><font
										color="red"><sup>*</sup></font>&nbsp;<s:textfield
										id="colonyName" size="60"
										onblur="fnCheckColonyName(this);" /></td>
									<td align="left" width="15%"><input type="button"
										value="    Search    " class="groovybutton" id="btnSearch" onClick="fnSearchColony();"/>
										<input type="button" size="4"
										value="Search Again" class="groovybutton" id="btnSearchAgain" style="display:none" onClick="window.location.reload();"/></td>
								</tr>
								<tr>
									<td align="right" width="10%"></td>
									<td align="left" width="30%"></td>
									<td align="center" width="45%">
									<td align="left" width="15%"><input type="button"
										class="groovybutton" id="btnAdd" value="       Add      "
										onclick="fnToAddDetails();" /></td>
									<td align="left" width="15%"></td>
								</tr>
								<tr>
									<td align="right" colspan="6"><font color="red"><sup>*</sup></font>
									marked fields are mandatory.</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
							<div id="colonyDetailsDiv" style="display: none;">
							<fieldset><legend>Colony Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
								  <td align="center" width="30%"><label>Charge Type</label><font
										color="red"><sup>*</sup></font>
									<s:select list="#session.ChargeTypeListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="chargeType" id="chargeType" /></td>
								  <!--  <td align="right" width="10%"><label>Charge Type</label><font
										color="red"><sup>*</sup></font></td>
									<td align="left" width="20%"><select id="chargeType"
										disabled="disabled">
										<option value="Please Select">Please Select</option>
										<option value="W">Water</option>
										<option value="S">Sewer</option>
									</select></td> -->
									<td align="left" width="30%"><label>&nbsp;&nbsp;&nbsp;Notification
									Date</label><font color="red"><sup>*</sup></font>&nbsp;<input type="text"
										name="notificationDate" id="notificationDate"
										size="15" onchange="javascript:validateNotifyDate(this);"/></td>
								 <!--	<td align="left" width="30%"><label>Category</label><select id="category"
										disabled="disabled">
										<option value="Please Select">Please
										Select&nbsp;&nbsp;&nbsp;</option>
										<option value="A">A</option>
										<option value="B">B</option>
										<option value="C">C</option>
										<option value="D">D</option>
										<option value="E">E</option>
										<option value="F">F</option>
										<option value="G">G</option>
										<option value="H">H</option>
									</select></td> -->
									<td align="left" width="40%"><label>Category</label>
									<s:select
										list="#session.ColonyListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="category" id="category" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="statusSpan"></span></td>
										
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<br />
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="searchResultDiv" style="display: none;">
							<fieldset><legend>Rate Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="searchResultLoading" style="display: none;"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading" id="imgLoading" /></div>
									<div id="searchResult"></div>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td align='center'><div id="addRateRowBtnDiv" style="display: none;"><input type='button' class="groovybutton" id='addRate' value='  Add Rate  ' onclick='fnAddRateMasterDataRow();'/></div></td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="addNewColonyRateDiv" style="display: none;">
							<fieldset><legend>Rate Details</legend>
							<table class="table-grid" width="90%" align="center" border="0">
								<tr>
								<th>SL</th>
								<th>Rate/sqmt.<font color="red"><sup>*</sup></font></th>
								<th>Interest Eligibility<font color="red"><sup>*</sup></font></th>
								<th>Rebate Eligibility<font color="red"><sup>*</sup></font></th>
								<th>Type<font color="red"><sup>*</sup></font></th>
								<th>Category</th>
								<th>Min. Area</th>
								<th>Max Area</th>
								<th>Start Date<font color="red"><sup>*</sup></font></th>
								<th>End Date<font color="red"><sup>*</sup></font></th>
								<!--  <th>Doc Proof</th> -->
								</tr>
								<tr align="center"><td>1</td>
								    <td align="center"><s:textfield size="10" id="applicableRateNewColony" onkeypress="return checkValWithDot(event)" onblur="validateRate('applicableRateNewColony', 'Applicable Rate', 'onscreenMessage');"/></td>
								     <td align="center" ><s:select
										list="#session.InterestEligibilityListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="rateInterestEligibilityNewColony" id="rateInterestEligibilityNewColony"
										onchange="validateInterestEligibilityNewColony();"/></td>
									<td align="center" ><s:select
										list="#session.RebateEligibilityListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="rebateEligibilityNewColony" id="rebateEligibilityNewColony"
										onchange="validateRebateEligibilityNewColony();"/></td>
									<td align="center" ><s:select
										list="#session.RateTypeListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="rateTypeNewColony" id="rateTypeNewColony"
										/></td>
									<td align="center" ><s:select
										list="#session.CatClassListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="rateCategoryNewColony" id="rateCategoryNewColony"
										/></td>		
									<!--   <td align="center"><select id="rateInterestEligibilityNewColony">
										<option value="Please Select">Please Select&nbsp;&nbsp;&nbsp;</option>
										<option value="<%=DJBConstants.FLAG_Y %>">Yes</option>
										<option value="<%=DJBConstants.FLAG_N %>">No</option>
									</select></td> 
								     <td align="center"><select id="rebateEligibilityNewColony">
										<option value="Please Select">Please Select&nbsp;&nbsp;&nbsp;</option>
										<option value="<%=DJBConstants.FLAG_Y%>">Yes</option>
										<option value="<%=DJBConstants.FLAG_N%>">No</option>
									</select></td>
								    <td align="center"><select id="rateTypeNewColony">
										<option value="Please Select">Please Select&nbsp;&nbsp;&nbsp;</option>
										<option value="<%=DJBConstants.DEV_CHRG_RATE_TYP_REGULAR_CD%>">Regular</option>
										<option value="<%=DJBConstants.DEV_CHRG_RATE_TYP_SCHEME_CD%>">Scheme</option>
									</select></td>  
								    <td align="center"><select id="rateCategoryNewColony">
										<option value="Please Select">Please Select&nbsp;&nbsp;&nbsp;</option>
									</select></td> -->
								    <td align="center"><s:textfield size="10" id="rateMinAreaNewColony" onkeypress="return checkValWithDot(event)" onchange="validateRate('rateMinAreaNewColony', 'Rate Min. Area', 'onscreenMessage');"/></td>
								    <td align="center"><s:textfield size="10" id="rateMaxAreaNewColony" onkeypress="return checkValWithDot(event)" onchange="validateRate('rateMaxAreaNewColony', 'Rate Max. Area', 'onscreenMessage');"/></td>
								    <td align="center"><s:textfield size="10" id="rateStartDateNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onfocus="showCalender('rateStartDateNewColony');"/></td>
								    <td align="center"><s:textfield size="10" id="rateEndDateNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onfocus="showCalender('rateEndDateNewColony');"/></td>
								    <!-- <td align="center"><s:file size="20" id="rateDocNewColony" /></td> -->
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<br />
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="interestResultDiv" style="display: none;">
							<fieldset><legend>Interest Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="interestResultLoading" style="display: none;"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading" id="imgLoading" /></div>
									<div id="interestResult"></div>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td align='center'><div id="addInterestRowBtnDiv" style="display: none;"><input type='button' class="groovybutton" id='addInterest' value='Add Interest' onclick='fnAddInterestMasterDataRow();'/></div></td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="addNewColonyInterestDiv" style="display: none;">
							<fieldset><legend>Interest Details</legend>
							<table class="table-grid" width="90%" align="center" border="0">
								<tr>
								<th>SL</th>
								<th>Interest Percentage<font color="red"><sup>*</sup></font></th>
								<th>Start Date<font color="red"><sup>*</sup></font></th>
								<th>End Date<font color="red"><sup>*</sup></font></th>
								 <!-- <th>Doc Proof</th> -->
								</tr>
								<tr><td align="center">1</td>
								<!--    <td align="center"><s:textfield size="10" id="interestPercentageNewColony" onkeypress="return checkValWithDot(event)" onblur="validateRate('interestPercentageNewColony', 'Interest Percentage', 'onscreenMessage');" disabled="disabled"/></td> -->
								    <td align="center"><s:textfield size="10" id="interestPercentageNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)"  /></td>
								    <td align="center"><s:textfield size="10" id="interestStartDateNewColony" disabled="disabled" onkeypress="return false" onfocus="showCalender('interestStartDateNewColony');"/></td>
								    <td align="center"><s:textfield size="10" id="interestEndDateNewColony" disabled="disabled" onkeypress="return false" onfocus="showCalender('interestEndDateNewColony');"/></td>
								     <!-- <td align="center"><s:file size="20" id="interestDocNewColony" /></td> -->
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<br />
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="rebateResultDiv" style="display: none;">
							<fieldset><legend>Rebate Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center">
									<div id="rebateResultLoading" style="display: none;"><img
										src="/DataEntryApp/images/load.gif" width="25" border="0"
										title="Loading" id="imgLoading" /></div>
									<div id="rebateResult"></div>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td align='center'><div id="addRebateRowBtnDiv" style="display: none;"><input type='button' class="groovybutton" id='addRebate' value='Add Rebate' onclick='fnAddRebateMasterDataRow();'/></div></td>
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="addNewColonyRebateDiv" style="display: none;">
							<fieldset><legend>Rebate Details</legend>
							<table class="table-grid" width="90%" align="center" border="0">
								<tr>
								<th>SL</th>
								<th>Rebate Type</th>
								<th>Rebate Flat Rate</th>
								<th>Rebate Percentage</th>
								<th>Start Date</th>
								<th>End Date</th>
								<!-- <th>Doc Proof</th> -->
								</tr>
								<tr><td align="center">1</td>
									<td align="center" ><s:select
										list="#session.RebateTypeListMap" headerKey=""
										headerValue="Please Select" cssClass="selectbox"
										theme="simple" name="rebateTypeNewColony" id="rebateTypeNewColony"
										onchange="validateRebateTypeNewColony();" disabled="disabled"/></td>
								    <!-- <td><select id="rebateTypeNewColony" onchange="validateRebateTypeNewColony();">
										<option value="Please Select">Please Select&nbsp;&nbsp;&nbsp;</option>
										<option value="<%=DJBConstants.DEV_CHRG_FLAT_REBATE_CD%>">Flat</option>
										<option value="<%=DJBConstants.DEV_CHRG_PERCENTAGE_REBATE_CD%>">Percentage</option>
									</select></td>  -->
								    <td align="center"><s:textfield size="10" id="rebateFlatRateNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onchange="validateRate('rebateFlatRateNewColony', 'Rebate Flat Rate', 'onscreenMessage');"/></td>
								    <td align="center"><s:textfield size="10" id="rebatePercentageNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onchange="validateRate('rebatePercentageNewColony', 'Rebate Percentage', 'onscreenMessage');"/></td>
								    <td align="center"><s:textfield size="10" id="rebateStartDateNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onfocus="showCalender('rebateStartDateNewColony');"/></td>
								    <td align="center"><s:textfield size="10" id="rebateEndDateNewColony" disabled="disabled" onkeypress="return checkValWithDot(event)" onfocus="showCalender('rebateEndDateNewColony');"/></td>
								    <!-- <td align="center"><s:file size="20" id="rebateDocNewColony" disabled="disabled"/></td> -->
								</tr>
							</table>
							</fieldset>
							</div>
							</td>
						</tr>
					</table>
					<br />
					<table width="100%" border="0">
						<tr>
							<td>
							<div id="buttonDiv" style="display: none;">
							<table width="100%" align="center" border="0">
								<tr>
									<td align="right" width="30%"><input type="button"
										class="groovybutton" id="resetBtn" value="   Reset   "  disabled="disabled" onclick="fnToReset(this);"/></td>
									<td align="center" width="30%"><input type="button"
										class="groovybutton" id="freeze" value="   Save   " disabled="disabled" onclick="saveNewColonyDetail(this);"/></td>
									<td align="left" width="30%"><input type="button"
										class="groovybutton" id="activate" value="   Activate   " disabled="disabled" onclick="fnGoToActivate(this);"/></td>
									<td align="left" width="10%">&nbsp;</td>
								</tr>
							</table>
							</div>
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
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>