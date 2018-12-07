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
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
 	</script>	
 	<script type="text/javascript">
 		
		// Get the HTTP Object
		/* function getHTTPObjectForBrowser(){
			if (window.ActiveXObject){                
				return new ActiveXObject("Microsoft.XMLHTTP");   
			}else if (window.XMLHttpRequest){ 
				return new XMLHttpRequest();  
			}else {                 
				alert("Browser does not support AJAX...........");             
				return null;     
			}    
		}*/
		//Creating http Object
	 	//var httpObject = null;
		//=======================Ajax Call to Validate KNO start=================================    
		function fnValidateKNO(){ 
			document.getElementById('ajax-result').innerHTML ="<div class='search-option' title='Server Message'>&nbsp;</div>";
			var kno=Trim(document.getElementById('kno').value);
 	 		if(kno.length==10){
 	 			document.getElementById('invalidKNOSpan').style.display="none";	
				document.getElementById('validKNOSpan').style.display="none";	
 	 			if(isNaN(kno)){
 					document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be a 10 digits Numeric Value.</font>";
 					document.getElementById('lastOKReadDate').value="";
 					document.getElementById('lastOKReadStatus').value="";
 					document.getElementById('lastOKRead').value="";
 					document.getElementById('lastOKReadDate').disabled=true;
 					document.getElementById('lastOKReadStatus').disabled=true;
 					document.getElementById('lastOKRead').disabled=true;		
 					document.getElementById('kno').focus();
 	 			}else{  
	 	 			httpObject = null;
	 	 			httpObject=getHTTPObjectForBrowser(); 
					var url= "lastOKReadCorrectionAJax.action?hidAction=validateKNO&kno="+kno; 		
					if (httpObject != null) {
						hideScreen();									
						//alert('url::'+url); 
						httpObject.onreadystatechange = function(){         
							if(httpObject.readyState == 4){
								var  responseString= httpObject.responseText;
								//alert('responseString::\n'+responseString);
								if(null!=responseString && responseString.indexOf('<script')==-1){
									if(null!=responseString && responseString.indexOf("ERROR:")>-1){
										document.getElementById('ajax-result').innerHTML = httpObject.responseText;
										document.getElementById('consumerNameSpan').innerHTML = ""; 
										document.getElementById('invalidKNOSpan').style.display="block";
										document.getElementById('lastOKReadDate').disabled=true;
										document.getElementById('lastOKReadStatus').disabled=true;
										document.getElementById('lastOKRead').disabled=true;
										document.getElementById('btnProcess').disabled=true;
										document.getElementById('knoSpan').innerHTML = "";							
										showScreen(); 
									}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){							
										document.forms[0].consumerName.value=httpObject.responseText;
										document.getElementById('consumerNameSpan').innerHTML ="&nbsp;"+ httpObject.responseText+"&nbsp;&nbsp;";
										document.getElementById('consumerName').value=Trim(httpObject.responseText);	
										document.getElementById('validKNOSpan').style.display="block";
										document.getElementById('lastOKReadDate').disabled=false;
										document.getElementById('lastOKReadStatus').disabled=false;
										document.getElementById('lastOKRead').disabled=false;
										document.getElementById('btnProcess').disabled=false;
										document.getElementById('knoSpan').innerHTML = "";
										document.getElementById('lastOKReadDate').focus();			
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
						httpObject.open("POST", url, true);            
						httpObject.send(null);
					} 
 	 			} 
 	 		} 
		}
		
		//=======================Ajax Call to Validate KNO End=================================
 		//=======================Ajax Call to Process Data start===============================   
		function fnProcessData(){
			if(validateData()){  
				var kno=Trim(document.getElementById('kno').value);
				var lastOKReadDate=Trim(document.getElementById('lastOKReadDate').value);
				var lastOKReadStatus=Trim(document.getElementById('lastOKReadStatus').value);
				var lastOKRead=Trim(document.getElementById('lastOKRead').value);				
	 	 		if(kno.length==10){
	 	 			httpObject = null;
	 	 			httpObject=getHTTPObjectForBrowser(); 
					var url= "lastOKReadCorrectionAJax.action?hidAction=processData&kno="+kno+"&lastOKReadDate="+lastOKReadDate+"&lastOKReadStatus="+lastOKReadStatus+"&lastOKRead="+lastOKRead;  				
					if (httpObject != null) {
						hideScreen();								
						//alert('url::'+url); 
						httpObject.onreadystatechange =function(){         
							if(httpObject.readyState == 4){
								var  responseString= httpObject.responseText;
								//alert(document.getElementById('ajax-result')+'<>'+responseString);
								if(null!=responseString && responseString.indexOf('<script')==-1){
									if(null!=responseString && responseString.indexOf("ERROR:")>-1){
										document.getElementById('ajax-result').innerHTML = httpObject.responseText;		
										showScreen(); 
									}else if(null!=responseString && responseString.indexOf("ERROR:")==-1){							
										document.forms[0].consumerName.value=httpObject.responseText;
										document.getElementById('ajax-result').innerHTML = httpObject.responseText;
										document.getElementById('btnProcess').disabled=true;	
										showScreen(); 
									}else{
										alert('Sorry ! \nThere was a problem');
										showScreen();
									}
								} else{
									document.getElementById('ajax-result').innerHTML="<div class='search-option' title='Server Message'>"+responseString+"</div>";
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
 		function fnOnSubmit(){ 			
 			fnProcessData();
 			return true;
 		}
 		function fnOnLoad(){
 			document.getElementById('consumerNameSpan').innerHTML =document.getElementById('consumerName').value; 			
 		} 
 		//Reset all fields
 		function fnReset(){
 			document.getElementById('kno').value="";
 			document.getElementById('consumerName').value="";
			document.getElementById('lastOKReadDate').value="";
			document.getElementById('lastOKReadStatus').value="";
			document.getElementById('lastOKRead').value="";
			document.getElementById('invalidKNOSpan').style.display="none";	
			document.getElementById('validKNOSpan').style.display="none";
			document.getElementById('knoSpan').innerHTML = "";
			document.getElementById('consumerNameSpan').innerHTML = "";	
			document.getElementById('lastOKReadDateSpan').innerHTML = "";
			document.getElementById('lastOKReadStatusSpan').innerHTML = "";
			document.getElementById('lastOKReadSpan').innerHTML = "";
			document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
 		}
 		//Validations
 		function validateData(){
 			var kno=Trim(document.getElementById('kno').value);
			var lastOKReadDate=Trim(document.getElementById('lastOKReadDate').value);
			var lastOKReadStatus=Trim(document.getElementById('lastOKReadStatus').value);
			var lastOKRead=Trim(document.getElementById('lastOKRead').value);
			if(kno.length==0){
				document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO is mandatory.</font>";
				document.getElementById('kno').focus();
				return false;
			}
			if(kno.length!=10 ||isNaN(kno)){
				document.getElementById('knoSpan').innerHTML = "<font color='red'>KNO should be a 10 digits Numeric Value.</font>";
				document.getElementById('kno').focus();
				return false;
			}
			if(lastOKReadDate.length==0){
				document.getElementById('lastOKReadDateSpan').innerHTML = "<font color='red'>Last Read Date is mandatory</font>";
				document.getElementById('lastOKReadDate').focus();
				return false;
			}else{
				fnValiadteLastOKReadDate();
			}
			if(invalidLastOKReadDate){
				document.getElementById('lastOKReadDate').focus();
				return false;
			}
			if(lastOKReadStatus.length==0){
				document.getElementById('lastOKReadStatusSpan').innerHTML = "<font color='red'>Last Read Status is mandatory</font>";
				document.getElementById('lastOKReadStatus').focus();
				return false;
			}	
			if(lastOKRead.length==0){
				document.getElementById('lastOKReadSpan').innerHTML = "<font color='red'>Last Read is mandatory</font>";
				document.getElementById('lastOKRead').focus();
				return false;
			}
			
			document.getElementById('lastOKReadDateSpan').innerHTML = "";
			document.getElementById('lastOKReadStatusSpan').innerHTML = "";
			document.getElementById('lastOKReadSpan').innerHTML = "";	
			return true;
 		}
 		function fnSetLastOKRead(){
 			var lastOKReadStatus=Trim(document.getElementById('lastOKReadStatus').value);
			var lastOKRead=Trim(document.getElementById('lastOKRead').value);
 			//Consumer Last Read greater than or equal to 0 in case of Last Read Status as (OK, PUMP, NUW, PLUG, DEM, CUT, NTRC, NOWC). In all other case Last Read will be 0.
			var lastOKReadShouldBeGreaterThan0String="OK,PUMP,NUW,PLUG,DEM,CUT,NTRC,NOWC";			
			if(lastOKReadShouldBeGreaterThan0String.indexOf(lastOKReadStatus)==-1){
				document.getElementById('lastOKRead').value="0";
				document.getElementById('lastOKRead').disabled=true;
			}else{
				document.getElementById('lastOKRead').disabled=false;
				document.getElementById('lastOKRead').focus();
			}
 		}
 		var invalidLastOKReadDate=false;
 		function fnValiadteLastOKReadDate(){
 	 		var lastOKReadDate=Trim(document.getElementById('lastOKReadDate').value);
 	 		if(lastOKReadDate.length>0){
 	 			document.getElementById('lastOKReadDateSpan').innerHTML = "";
 	 			//alert(lastOKReadDate.length);
 	 			if(lastOKReadDate.length==11){
 	 	 			var firstHyphen=lastOKReadDate.substring(2,3);
 	 	 			var secondHyphen=lastOKReadDate.substring(6,7);
 	 	 			//alert(firstHyphen+'<>'+secondHyphen);
 	 	 			var invalidDate=false;
 	 	 			if(firstHyphen!='-' ||secondHyphen!='-'){
 	 	 				invalidDate=true;
 	 	 			}
 	 	 	 			
 	 	 			var formateDateddmmyyyy=formatDateddmmyyyy(lastOKReadDate);//Change date format from DD-MON-YYYY to dd/mm/yyyy
 	 	 			//alert(formateDateddmmyyyy);
 	 	 			if(invalidDate||!formateDateddmmyyyy || formateDateddmmyyyy=='error' ||!validateDateString(formateDateddmmyyyy)){
 	 	 				document.getElementById('lastOKReadDateSpan').innerHTML = "<font color='red'>Invalid Date</font>";
 	 	 				invalidLastOKReadDate=true;
 	 	 			}else{
 	 	 				invalidLastOKReadDate=false;
 	 	 			}
 	 	 		}else{
 	 	 			document.getElementById('lastOKReadDateSpan').innerHTML = "<font color='red'>Invalid Date</font>";
 	 	 			invalidLastOKReadDate=true;
 	 	 		} 
 	 		}	 		
 		}
 		function fnValiadteLastOKReadStatus(){
 	 		var lastOKReadStatus=Trim(document.getElementById('lastOKReadStatus').value);
 	 		if(lastOKReadStatus.length>0){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "";
 	 		}
 	 		if(lastOKReadStatus=='OK'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "OK&nbsp;&nbsp;Volumetric Billing";
 	 		}
 	 		if(lastOKReadStatus=='PUMP'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Pump Connection&nbsp;&nbsp;Volumetric Billing";
 	 		}
 	 		if(lastOKReadStatus=='NUW'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "No Use Of Water&nbsp;&nbsp;Minimum Volumetric Billing";
 	 		}
 	 		if(lastOKReadStatus=='PLUG'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Plugged Minimum&nbsp;&nbsp;Minimum Volumetric Billing";
 	 		}
 	 		if(lastOKReadStatus=='DEM'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Demolished&nbsp;&nbsp;Minimum Volumetric Billing";
 	 		}
 	 		if(lastOKReadStatus=='PLOC'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Premises Locked&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='NRES'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "No Response&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='MLOC'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Locked&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='MBUR'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Buried&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='VMMT'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Vapoured/Moistured Meter&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='DUST'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Dusty Meter&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='BJAM'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Box Jammed&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='ADF'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Access Denied/Refused&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='RDDT'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Reading Detained&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='NS'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "No Status&nbsp;&nbsp;Adhoc Billing";
 	 		}
 	 		if(lastOKReadStatus=='TEST'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Under Testing&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='DFMT'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Defective Mater&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='STP'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Stopped&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='MTTM'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Tampered&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='MREV'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Reverse&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='REP'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Under Repair&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='UNMT'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Running Unmetered&nbsp;&nbsp;Average Billing";
 	 		}
 	 		if(lastOKReadStatus=='BYPS'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "T-Joint/ByPass&nbsp;&nbsp;Average Billing(Unauthorized Usage)";
 	 		}
 	 		if(lastOKReadStatus=='CUT'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Meter Cut-Off&nbsp;&nbsp;No Billing";
 	 		}
 	 		if(lastOKReadStatus=='NTRC'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "Not Traceable&nbsp;&nbsp;No Billing";
 	 		}
 	 		if(lastOKReadStatus=='NOWC'){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "No Water Connection At Site&nbsp;&nbsp;No Billing";
 	 		}
 	 		if(lastOKReadStatus==''){
 	 			document.getElementById('lastOKReadStatusSpan').innerHTML = "<font color='red'>Please Select Last Read Status</font>";
 	 		}
 	 		fnSetLastOKRead();
 		}
 		function fnValiadteLastOKRead(){
 	 		var lastOKRead=Trim(document.getElementById('lastOKRead').value);
 	 		if(lastOKRead.length>0){
 	 			document.getElementById('lastOKReadSpan').innerHTML = "";
 	 			if(isNaN(lastOKRead)){
 	 	 			document.getElementById('lastOKReadSpan').innerHTML = "<font color='red'>Last Read Should be Positive Number</font>";
 	 	 			document.getElementById('lastOKRead').value="";
 	 	 		}
 	 		}else{
 	 			document.getElementById('lastOKReadSpan').innerHTML = "<font color='red'>Please Select Last Read Status</font>";
 	 		}
 	 		
 		}
 		function setDAY(day){
 			document.getElementById('lastOKReadDate').value=day;
 			day=document.getElementById('lastOKReadDate').value;
 			document.getElementById('lastOKReadDateSpan').innerHTML = "";
 			fnLastOKReadMonthSelect(day);
 			//document.getElementById('lastOKReadDateSelectSpan').style.display='none';
 	 		//document.getElementById('lastOKReadDateSelectSpan').innerHTML = "";
 		}
 		function setMONTH(month){
 			document.getElementById('lastOKReadDate').value=Trim(document.getElementById('lastOKReadDate').value)+"-"+month;
 			month=document.getElementById('lastOKReadDate').value;
 			document.getElementById('lastOKReadDateSpan').innerHTML = "";
 			fnLastOKReadYearSelect(month);
 			//document.getElementById('lastOKReadDateSelectSpan').style.display='none';
 	 		//document.getElementById('lastOKReadDateSelectSpan').innerHTML = "";
 		}
 		function setYEAR(year){
 	 		var lastOKReadDate=Trim(document.getElementById('lastOKReadDate').value);
 	 		if(lastOKReadDate.length==6){
 	 			invalidLastOKReadDate=false;
 	 			document.getElementById('lastOKReadDateSpan').innerHTML = "";
	 	 		lastOKReadDate=lastOKReadDate+"-"+year;
	 			document.getElementById('lastOKReadDate').value=Trim(lastOKReadDate);
 	 		}
 			document.getElementById('lastOKReadDateSelectSpan').style.display='none';
 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = "";
 	 		document.getElementById('lastOKReadDateSpan').innerHTML = "";
 	 		document.getElementById('lastOKReadStatus').focus();
 		}
 		function fnLastOKReadDateSelect(obj){
 	 		var inputString=Trim(obj.value);
 	 		var html="";
 	 		if(inputString.length<2){	 	 		
	 	 		for(var i=1;i<=31;i++){		 	 		
	 	 	 		var day=""+i;
	 	 	 		day=fnMakeTwoDigit(day);
	 	 			html=html+"<a href=\"#\" onclick=\"setDAY('"+day+"');\" >"+day+"</a><br/>";
	 	 		}
	 	 		document.getElementById('lastOKReadDateSelectSpan').style.display='block';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 		}
 	 		if(inputString.length>=2 && inputString.length<6){	 	 		
 	 			fnLastOKReadMonthSelect(inputString);
	 		}
 	 		if(inputString.length>=6 && inputString.length<11){	 	 		
 	 			fnLastOKReadYearSelect(inputString);
	 		}
 	 		if(inputString.length==11){	
	 			document.getElementById('lastOKReadDateSelectSpan').style.display='none';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 	 		var lastOKReadDate=document.getElementById('lastOKReadDate').value;
	 	 		document.getElementById('lastOKReadDate').value=lastOKReadDate.toUpperCase();	 	 		
	 		}
 		}
 		function fnLastOKReadMonthSelect(lastOKReadDate){
 	 		var inputString=Trim(lastOKReadDate);
 	 		var html="";
 	 		if(inputString.length!=0){  	 	 		
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('JAN');\" >JAN</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('FEB');\" >FEB</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('MAR');\" >MAR</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('APR');\" >APR</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('MAY');\" >MAY</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('JUN');\" >JUN</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('JUL');\" >JUL</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('AUG');\" >AUG</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('SEP');\" >SEP</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('OCT');\" >OCT</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('NOV');\" >NOV</a><br/>";
 	 			html=html+"<a href=\"#\" onclick=\"setMONTH('DEC');\" >DEC</a><br/>";
	 	 		document.getElementById('lastOKReadDateSelectSpan').style.display='block';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 		}else{
	 			document.getElementById('lastOKReadDateSelectSpan').style.display='none';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 		}
 		}
 		function fnLastOKReadYearSelect(lastOKReadDate){
 	 		var inputString=Trim(lastOKReadDate);
 	 		var html="";
 	 		if(inputString.length!=0){	
 	 			var year="<%=PropertyUtil.getProperty("CURRENT_YEAR")%>"; 	 		
	 	 		for(var i=0;i<15;i++){		 	 		
	 	 			html=html+"<a href=\"#\" onclick=\"setYEAR("+year+");\" >"+year+"</a><br/>";
	 	 			year=parseInt(year)-1;
	 	 		}
	 	 		document.getElementById('lastOKReadDateSelectSpan').style.display='block';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 		}else{
	 			document.getElementById('lastOKReadDateSelectSpan').style.display='none';
	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = html;
	 		}
 		}
 		function fnMakeTwoDigit(inputString){
 	 		if(inputString.length==1){
 	 	 		return "0"+inputString;
 	 		}else{
 	 			return inputString;
 	 		}
 		}
 		function fnHideLastOKReadDateSelect(){ 			
 	 		var lastOKReadDate=document.getElementById('lastOKReadDate').value;
 	 		if(lastOKReadDate.length==11){
 	 			document.getElementById('lastOKReadDateSelectSpan').style.display='none';
 	 	 		document.getElementById('lastOKReadDateSelectSpan').innerHTML = "";
 	 			fnValiadteLastOKReadDate();
 	 		}
 		}
 		function fnHideLastOKReadDateSpan(){
 	 		document.getElementById('lastOKReadDateSelectSpan').style.display='none';
 	 	 	document.getElementById('lastOKReadDateSelectSpan').innerHTML = "";
 		}			
 		document.onkeydown = function(evt) {
 		     evt = evt || window.event;     
 		     if (evt.keyCode == 27) {  	 		    	
 		    	//showScreen(); 
 		    	//goHome();    
	 		 } 
 		  }; 	
 	</script> 	
 	<style type="text/css">
 	#lastOKReadDateSelectSpan{
 		top:200px;
 		position:absolute;
 		z-index:500;
 		float:left;
 		background-color: #f7f7f7;
 		border:#21c5d8 1px solid;
 		padding:0; 
 		align:center;
 		display:none;
 	}
 	#lastOKReadDateSelectSpan a{ 		
 		padding-left:15px; 
 		padding-right:15px; 
 		align:center; 
 		text-decoration:none;
 		cursor:pointer;
 	}
 	#lastOKReadDateSelectSpan a:hover{ 		
 		background-color: #21c5d8;
 		border:0;
 		color:#fff;		
 			
 	}
 	</style>
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
										<legend><span>Last OK Read Correction Details</span></legend>						
										<table width="95%" align="center" border="0">																				
											<tr>
												<td align="center" colspan="3">
													<form action="javascript:void(0);" method="post" onsubmit=" return fnOnSubmit();">
														<input type="hidden" name="hidAction" id="hidAction"/>	
														<input type="hidden" name="consumerName" id="consumerName"/>
														<table border="0" width="98%" align="center">	
															<tr title="Enter 10 digits KNO of the Consumer.">
																<td align="left" width="35%">
																	KNO<font color="red" size="2"><sup>*</sup></font>
																</td>
																<td align="left" width="15%">											
																	<input type="text" name="kno" id="kno" class="textbox" size="20" maxlength="10" autocomplete="off" onchange="fnValidateKNO(this);"/>
																</td>
																<td align="left" width="50%">											
																	<span id="validKNOSpan" style="display:none;"/><img src="/DataEntryApp/images/valid.gif"  border="0" title="Valid KNO"/></span><span id="invalidKNOSpan" style="display:none;"/><img src="/DataEntryApp/images/invalid.gif"  border="0" title="Invalid KNO"/></span><span id="knoSpan"></span>
																</td>	
															</tr>
															<tr>
																<td align="left"  width="35%">
																	Consumer Name
																</td>
																<td align="left" colspan="2"  width="65%">											
																	<b><span id="consumerNameSpan" style="border:1px solid #21c5d8;"/></span></b>
																</td>																	
															</tr>
															<tr title="Enter Consumer Last Read Date in the format (DD-MON-YYYY).">
																<td align="left"  width="35%" nowrap>
																	Last Read Date (LOKRDT)<font color="red" size="2"><sup>*</sup></font><font size="0.1em">(DD-MON-YYYY)</font>
																</td>
																<td align="left"  width="15%">	
																	<span id="lastOKReadDateSelectSpan" title="Click to Select the Value." /></span>
																	<input type="text" name="lastOKReadDate" id="lastOKReadDate"  class="textbox" size="20" maxlength="11"  autocomplete="off" disabled="true" onfocus="fnLastOKReadDateSelect(this);" onchange="fnValiadteLastOKReadDate();" onkeyup="fnLastOKReadDateSelect(this);" onblur="fnHideLastOKReadDateSelect();"/>
																</td>
																<td align="left"  width="50%">	
																	<span id="lastOKReadDateSpan"/></span>
																</td>
															</tr>
															<tr title="Select Consumer Last Read Status" >
																<td align="left" width="35%">
																	Last Read Status (LSTTS)<font color="red" size="2"><sup>*</sup></font>
																</td>
																<td align="left"  width="15%">	
																	<%ArrayList meterReadStatusList=(ArrayList)session.getAttribute("meterReadStatusList"); %>
																	<select name="lastOKReadStatus" id="lastOKReadStatus" class="selectbox" disabled="true" onchange="fnValiadteLastOKReadStatus();" onfocus="fnHideLastOKReadDateSpan();">
																  		<option value="">Please Select</option>
																  		<%for(int m=0; m < meterReadStatusList.size();m++){ %>
																  		<option value="<%=meterReadStatusList.get(m)%>"><%=meterReadStatusList.get(m)%></option>
																  		<%} %>
																	</select>
																</td>
																<td align="left"  width="50%">	
																	<span id="lastOKReadStatusSpan"/></span>
																</td>
															</tr>
															<tr title="Enter Consumer Last Read. Consumer Last Read is greater than or equal to 0 in case of Last Read Status as (OK, PUMP, NUW, PLUG, DEM, CUT, NTRC, NOWC). In all other case Last Read will be 0" >
																<td align="left" width="35%">
																	Last Read (LOKR)<font color="red" size="2"><sup>*</sup></font>
																</td>
																<td align="left"  width="15%">
																	<input type="text" name="lastOKRead" id="lastOKRead" class="textbox" size="20" disabled="true" onchange="fnValiadteLastOKRead();"/>
																</td>
																<td align="left"  width="50%">	
																	<span id="lastOKReadSpan"/></span>
																</td>
															</tr>
															<tr>
																<td align="center"  width="35%">
																	<input type="button" id="btnReset" value="Reset All" class="groovybutton" onclick="fnReset();"/>
																</td>
																<td align="left" colspan="2"  width="65%">	
																	<input type="submit" id="btnProcess" value="Process" class="groovybutton" disabled="true"/>
																</td>
															</tr>	
														</table>										
													<form>
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