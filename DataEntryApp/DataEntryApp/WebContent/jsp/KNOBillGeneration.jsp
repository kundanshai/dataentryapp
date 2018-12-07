<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<%try{ %>
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
	<link rel="stylesheet" type="text/css" media="screen" href="<s:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<s:url value="/css/pagination.css"/>"/>
	<script type="text/javascript" src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.paginate.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/UtilityScripts.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/SearchResult.js"/>"></script>		
	<script type="text/javascript"> 
	/*Start: Added By Madhuri on 24th Nov 2016 as per Jtrac -DJB-4604*/
	var errormsg="<%=DJBConstants.ERR_MSG_FOR_BILL_GEN %>";
	/*End: Added By Madhuri on 24th Nov 2016 as per Jtrac -DJB-4604*/
	
     /************************************************************************/

	//Added By Matiur Rahman
	//=======================For Ajax Call Start=================================
	//var httpObject = null;
	// Get the HTTP Object
	/*function getHTTPObjectForBrowser(){
		if (window.ActiveXObject){                
			return new ActiveXObject("Microsoft.XMLHTTP");   
		}else if (window.XMLHttpRequest){ 
			return new XMLHttpRequest();  
		}else {                 
			alert("Browser does not support AJAX...........");             
			return null;     
		}    
	}*/
	//Ajax Call
	

	function freezeCallReq()
	{
        //alert('inside freezeCallReq');
        //document.form.action="knoBillGen.action";
		document.form.action="knoBillGenFreeze.action";
		document.form.submit();
		//document.forms ["./ListAlgorithmAction"].submit ();
		
	}
	
	
	
	function IsPositiveNumber(obj)
{
	IsNumber(obj);
	if(parseInt(obj.value)<0)
	{
		alert('PLEASE ENTER POSITVE VALUE ONLY');
		obj.value="";
		obj.focus();
	}
}

//Trims all spaces to the left of a specific string
function LTrim(str)
{
	var whitespace = new String(" \t\n\r ");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1)
	{
		var j=0, i = s.length;
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
			j++;
		s = s.substring(j, i);
	}
	return s;
}
//Trims all spaces to the right of a specific string
function RTrim(str)
{
	var whitespace = new String(" \t\n\r ");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
	{
		var i = s.length - 1;
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
			i--;
		s = s.substring(0, i+1);
	}
	return s;
}

//Trims all spaces to the left and right of a specific string by calling RTrim and LTrim
function Trim(str)
{
	return RTrim(LTrim(str));
}
	
function compareDates(astrStartDate, astrEndDate)
{
	var lstrStartDateYear;
	var lstrStartDateMonth;
	var lstrStartDateDay;
	var lstrEndDateYear;
	var lstrEndDateMonth;
	var lstrEndDateDay;
   
	if(astrEndDate != "" && astrStartDate != "")
	{
		lstrStartDateYear  = astrStartDate.substring(6,10);
		lstrStartDateMonth = astrStartDate.substring(3,5);
		lstrStartDateDay   = astrStartDate.substring(0,2);
		
		lstrEndDateYear  = astrEndDate.substring(6,10);
		lstrEndDateMonth = astrEndDate.substring(3,5);
		lstrEndDateDay   = astrEndDate.substring(0,2);
		
		
		if(lstrStartDateYear > lstrEndDateYear)
			return false;
		
		else
		{
			if (lstrStartDateYear == lstrEndDateYear)
			{
				if(lstrStartDateMonth > lstrEndDateMonth)
					return false;
				else
				{
					if (lstrStartDateMonth == lstrEndDateMonth)
					{
						if(lstrStartDateDay > lstrEndDateDay)
								return false;
					}
				}
			}
		}
		return true;
	}
}

//This function finds the number of days between 2 dates
function days_between(startDate, endDate)
{
	var startDateDay = startDate.substring(0,2);
	var startDateMonth = startDate.substring(3,5);
	var startDateYear = startDate.substring(6,10);

	var endDateDay = endDate.substring(0,2);
	var endDateMonth = endDate.substring(3,5);
	var endDateYear = endDate.substring(6,10);

	var sDate = new Date();
	sDate.setDate(startDateDay);
	sDate.setMonth(startDateMonth);
	sDate.setYear(startDateYear);

	var eDate = new Date();
	eDate.setDate(endDateDay);
	eDate.setMonth(endDateMonth);
	eDate.setYear(endDateYear);

	var daysApart = Math.abs(Math.round((sDate-eDate)/86400000));

	return daysApart;
}

//Function to find the finyear from a date
function getFinYearFromDate(date)
{
	var finYear = null;
	var year = date.substring(6,10);
	var month = date.substring(3,5);
	
	if(month < 4)
		finYear = (parseInt(year) - 1) + "-" + year;
	else
		finYear = year + "-" + (parseInt(year) + 1);
	
	return finYear;
}

//Function to validate date. Usage : function validateDate(document.forms[0].date)
function validateDate(dateObj)
{
	var errorMsg = 'none';
	errorMsg = getDateErrorMessage(dateObj);
	if(errorMsg != 'none')
	{
		alert(errorMsg);
		dateObj.value = "";
		dateObj.focus();
		return false;
	}
	return true;
}

//Function to get the error messages for date validation
function getDateErrorMessage(dateObj) 
{
	var strMonths,strMonth,strDay,strYear;
	var strs;
	var errorMsg = 'none';

	if ( dateObj.value.length < 10){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strs = dateObj.value.substring(2,3); 
	if (!(strs =="/")){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strs = dateObj.value.substring(5,6);
	if (!(strs =="/")){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strDay = dateObj.value.substring(0,2); 
	strMonth = dateObj.value.substring(3,5); 
	strYear  = dateObj.value.substring(6,10);

	if(!(checkNonNumericCharacters(strYear))){
		errorMsg = 'YEAR SHOULD BE NUMERIC VALUE.';
		return errorMsg; 
	}

	if(!( (strYear >= "1900") && (strYear <= "3000") )){       
		errorMsg = 'PLEASE ENTER A VALID YEAR.';
		return errorMsg;
	}

	if(!(checkNonNumericCharacters(strMonth))){
		errorMsg = 'MONTH SHOULD BE NUMERIC VALUE.';
		return errorMsg;
	}

	if(!((strMonth <= "12") && (strMonth >= "01"))){
		errorMsg = 'PLEASE ENTER A VALID MONTH.';
		return errorMsg;
	}

	if(!(checkNonNumericCharacters(strDay))){
		errorMsg = 'DAY SHOULD BE NUMERIC VALUE.';
		return errorMsg;
	}

	strMonths="312831303130313130313031";
	if((( strYear%4 == 0 ) && (strYear%100 != 0)) || (strYear%400=="0") ){
		strMonths="312931303130313130313031";
		return errorMsg;
	}

	if(!((strDay <= strMonths.substring((strMonth-1)*2,(strMonth-1)*2+2)) && (strDay>0))){
		errorMsg = 'PLEASE ENTER A VALID DAY';
		return errorMsg;
	}
	return errorMsg;
}
//Function to validate date. Usage : function validateDate(dateString)
function validateDateString(dateValue)
{
	var errorMsg = 'none';
	errorMsg = getDateErrorMessageString(dateValue);
	if(errorMsg != 'none')
	{
		alert(errorMsg);
		return false;
	}
	return true;
}

//Function to get the error messages for date validation
function getDateErrorMessageString(dateValue) 
{	
	var strMonths,strMonth,strDay,strYear;
	var strs;
	var errorMsg = 'none';

	if ( dateValue.length < 10){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strs = dateValue.substring(2,3); 
	if (!(strs =="/")){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strs = dateValue.substring(5,6);
	if (!(strs =="/")){
		errorMsg = 'PLEASE ENTER A VALID DATE.';
		return errorMsg; 
	}

	strDay = dateValue.substring(0,2); 
	strMonth = dateValue.substring(3,5); 
	strYear  = dateValue.substring(6,10);
	
	if(!(checkNonNumericCharacters(strDay))){
		errorMsg = 'DAY SHOULD BE NUMERIC VALUE.';
		return errorMsg;
	}
	
	strMonths="312831303130313130313031";
	if((( strYear%4 == 0 ) && (strYear%100 != 0)) || (strYear%400=="0") ){
		strMonths="312931303130313130313031";		
	}
	
	if(!((strDay <= strMonths.substring((strMonth-1)*2,(strMonth-1)*2+2)) && (strDay>0))){
		errorMsg = 'PLEASE ENTER A VALID DAY';
		return errorMsg;
	}
	
	if(!(checkNonNumericCharacters(strMonth))){
		errorMsg = 'MONTH SHOULD BE NUMERIC VALUE.';
		return errorMsg;
	}

	if(!((strMonth <= "12") && (strMonth >= "01"))){
		errorMsg = 'PLEASE ENTER A VALID MONTH.';
		return errorMsg;
	}
	
	if(!(checkNonNumericCharacters(strYear))){
		errorMsg = 'YEAR SHOULD BE NUMERIC VALUE.';
		return errorMsg; 
	}

	if(!( (strYear >= "1900") && (strYear <= "3000") )){       
		errorMsg = 'PLEASE ENTER A VALID YEAR.';
		return errorMsg;
	}
	
	return errorMsg;
}
function formatDateddmmyyyy(strDate)
{
	if(strDate!=''&& strDate.length==11 )
	{
		var dd=strDate.substring(0,2);
		var mm=strDate.substring(3,6);
		var yyyy=strDate.substring(7,11);
		
		if(mm=='JAN')
		{
			mm='01';
		}
		else if(mm=='FEB')
		{
			mm='02';
		}
		else if(mm=='MAR')
		{
			mm='03';
		}
		else if(mm=='APR')
		{
			mm='04';
		}
		else if(mm=='MAY')
		{
			mm='05';
		}
		else if(mm=='JUN')
		{
			mm='06';
		}
		else if(mm=='JUL')
		{
			mm='07';
		}
		else if(mm=='AUG')
		{
			mm='08';
		}
		else if(mm=='SEP')
		{
			mm='09';
		}
		else if(mm=='OCT')
		{
			mm='10';
		}
		else if(mm=='NOV')
		{
			mm='11';
		}
		else if(mm=='DEC')
		{
			mm='12';
		}
		else
		{	
			return "error";
		}
		var formatedDate=dd+"/"+mm+"/"+yyyy;
		return formatedDate;
	}
	else{
		return false;
	}
}
//This function checks for the presence of Non-numeric characters in a numeric field
function checkNonNumericCharacters(astrNumber)
{
	var lintCount;
	var lstrCharacter;

	if(astrNumber.search("-") <= 0) //condition added to allow '-' sign only in first position
	{
		if ((astrNumber.length == 1) && (astrNumber.charAt(0)) == "-")
			return false;
		else
		{
			for(lintCount = 0; lintCount < astrNumber.length; lintCount++)
			{
				lstrCharacter = astrNumber.substring(lintCount, lintCount + 1)
				if (lstrCharacter!="-")
				{
					if ((lstrCharacter < "0" )  || (lstrCharacter > "9"))
						return false;
				}
			}
		}
	}
	else
		return false;

	return true;
}

//Function to calculate total for a column in a table and transfer the total to a different field in the jsp
function calc_total(tableId, amtFieldName, totAmtField)
{
	var table = document.getElementById(tableId);
	if(eval(table)==null)
		return;

	// get number of current rows
	var rowItems = table.getElementsByTagName("tr");
    var rowCount = rowItems.length;

    if(rowCount==2)
    {
		totAmtField.value = eval('document.forms[0].'+amtFieldName+'.value');
		fnformat_amount(totAmtField);
		return true;
	}

    if(rowCount>2)
    {

		var sum = 0;
		for(var i=0;i<rowCount-1;i++)
		{
			sum = sum + parseFloat(eval('document.forms[0].'+amtFieldName+'['+i+'].value'));
		}
		totAmtField.value = sum;
		fnformat_amount(totAmtField);
		return true;
	}
}

function calculate(tableId, amtFieldName, totAmtField)
{
	var table = document.getElementById(tableId);
	if(eval(table)==null)
		return;

	// get number of current rows
	var rowItems = table.getElementsByTagName("tr");
    var rowCount = rowItems.length;

    if(rowCount==2)
    {
		if(isNaN(eval('document.forms[0].'+amtFieldName+'.value'))==true)
		{
			alert("AMOUNT FIELD REQUIRES A NUMBER");
			(eval('document.forms[0].'+amtFieldName)).focus();
			(eval('document.forms[0].'+amtFieldName)).select();
			return false;
		}
		if(eval('document.forms[0].'+amtFieldName+'.value')<0)
		{
			alert("AMOUNT FIELD REQUIRES A NUMBER");
			(eval('document.forms[0].'+amtFieldName)).focus();
			(eval('document.forms[0].'+amtFieldName)).select();
			return false;
		}
		totAmtField.value = eval('document.forms[0].'+amtFieldName+'.value');
		fnformat_amount(eval('document.forms[0].'+amtFieldName));
		fnformat_amount(totAmtField);
		return true;
	}

    if(rowCount>2)
    {

		var sum = 0;
		for(var i=0;i<rowCount-1;i++)
		{
			if(isNaN(eval('document.forms[0].'+amtFieldName+'['+i+'].value'))==true)
			{
				alert("AMOUNT FIELD REQUIRES A NUMBER");
				(eval('document.forms[0].'+amtFieldName+'['+i+']')).focus();
				(eval('document.forms[0].'+amtFieldName+'['+i+']')).select();
				return false;
			}
			if(eval('document.forms[0].'+amtFieldName+'['+i+'].value')<0)
			{
				alert("AMOUNT CANNOT BE NEGATIVE");
				(eval('document.forms[0].'+amtFieldName+'['+i+']')).focus();
				(eval('document.forms[0].'+amtFieldName+'['+i+']')).select();
				return false;
			}
			sum = sum + parseFloat(eval('document.forms[0].'+amtFieldName+'['+i+'].value'));
			fnformat_amount(eval('document.forms[0].'+amtFieldName+'['+i+']'));
		}
		totAmtField.value = sum;
		fnformat_amount(totAmtField);
		return true;
	}
}

//Function to format amount field to two decimal places

	
	
function LTrim(str)
{
	var whitespace = new String(" \t\n\r ");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1)
	{
		var j=0, i = s.length;
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
			j++;
		s = s.substring(j, i);
	}
	return s;
}
// Trims all spaces to the right of a specific string
function RTrim(str)
{
	var whitespace = new String(" \t\n\r ");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
	{
		var i = s.length - 1;
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
			i--;
		s = s.substring(0, i+1);
	}
	return s;
}

// Trims all spaces to the left and right of a specific string by calling RTrim and LTrim
function Trim(str)
{
	return RTrim(LTrim(str));
}

//This function checks for empty strings.
//Usage : IsEmpty(document.forms[0].test.value) or IsEmpty(t) where t is var t;
function IsEmpty(textValue)
{
	if ( (textValue == null) || (textValue.length == 0) )
		return true;
	else
		return false;
}

//Function to check whether the field passed is a numeric field or not.
function IsNumber(obj)
{
	if(isNaN(obj.value)==true)
	{
		alert("NON-NUMERIC CHARACTERS ENTERED WHERE NUMERIC CHARACTERS ARE EXPECTED");
		obj.value = "";
		obj.focus();
		return false;
	}
}

//Function to clear the contains the contents of that field
function clearContents(obj)
{
	obj.value = "";
	return;
}
	
	
	function getHTTPObjectForBrowser(){
		if (window.ActiveXObject){                
			return new ActiveXObject("Microsoft.XMLHTTP");   
		}else if (window.XMLHttpRequest){ 
			return new XMLHttpRequest();  
		}else {                 
			alert("Browser does not support AJAX...........");             
			return null;     
		}    
	}
	//========       
	function doAjaxCall(seqNo,kno,billRound,meterReadDate,meterReadStatus,currentMeterRead,newAvgConsumption,newNoOfFloor,consumerStatus,resetFlag,remarks){             
		
        var i=0;
		//alert('doAjaxCall'+i++);

		httpObject = getHTTPObjectForBrowser();    
		var url= "knoBillGenAjax.action?hidAction=saveCurrentMeterData&seqNo="+seqNo+"&kno="+kno+"&billRound="+billRound+"&meterReadDate="+meterReadDate+"&meterReadStatus="+meterReadStatus+"&currentMeterRead="+currentMeterRead+"&newAvgConsumption="+newAvgConsumption+"&newNoOfFloor="+newNoOfFloor+"&consumerStatus="+consumerStatus+"&resetFlag="+resetFlag+"&remarks="+remarks;  
			//alert(httpObject);      
		if (httpObject != null) {
			//hideScreen();
			//setAll();
			//document.getElementById('data_submited').innerHTML ="<font color='green' size='2'>Data Submited</font>";	
			document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
			//alert('url::'+url);                 
			httpObject.open("POST", url, true);            
			httpObject.send(null);    
			httpObject.onreadystatechange = setAjaxOutput;  
		}   
	} 

   function doAjaxCallForFreeze(seqNo,kno,billRound,meterReadDate,meterReadStatus,currentMeterRead,newAvgConsumption,newNoOfFloor,consumerStatus,resetFlag,remarks){             
		
        var i=0;
        //alert('doAjaxCallForFreeze0000'+i++);
		//alert('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBdoAjaxCallFreeze'+i++);
       // alert('doAjaxCallForFreeze'+i++);
		httpObject = getHTTPObjectForBrowser();    
		var url= "knoBillGenAjax.action?hidAction=freezeCurrentMeterData&seqNo="+seqNo+"&kno="+kno+"&billRound="+billRound+"&meterReadDate="+meterReadDate+"&meterReadStatus="+meterReadStatus+"&currentMeterRead="+currentMeterRead+"&newAvgConsumption="+newAvgConsumption+"&newNoOfFloor="+newNoOfFloor+"&consumerStatus="+consumerStatus+"&resetFlag="+resetFlag+"&remarks="+remarks;  
			//alert(httpObject);      
		if (httpObject != null) {
			//hideScreen();
			//setAll();
			//document.getElementById('data_submited').innerHTML ="<font color='green' size='2'>Data Submited</font>";	
			document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
			//alert('url::'+url);                 
			httpObject.open("POST", url, true);            
			httpObject.send(null);    
			httpObject.onreadystatechange = setAjaxOutput1;  
		}   
	} 
//doAjaxCallForFreezeAndGenerateBill

   function doAjaxCallForFreezeAndGenerateBill(seqNo,kno,billRound,meterReadDate,meterReadStatus,currentMeterRead,newAvgConsumption,newNoOfFloor,consumerStatus,resetFlag,remarks){             
		
       var i=0;
      // alert('doAjaxCallForFreezeAndGenerateBill'+i++);
		//alert('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBdoAjaxCallFreeze'+i++);
      // alert('doAjaxCallForFreeze'+i++);
		httpObject = getHTTPObjectForBrowser();    
		var url= "knoBillGenAjax.action?hidAction=genarateBill&seqNo="+seqNo+"&kno="+kno+"&billRound="+billRound+"&meterReadDate="+meterReadDate+"&meterReadStatus="+meterReadStatus+"&currentMeterRead="+currentMeterRead+"&newAvgConsumption="+newAvgConsumption+"&newNoOfFloor="+newNoOfFloor+"&consumerStatus="+consumerStatus+"&resetFlag="+resetFlag+"&remarks="+remarks;  
			//alert(httpObject);      
		if (httpObject != null) {
			//hideScreen();
			//setAll();
			//document.getElementById('data_submited').innerHTML ="<font color='green' size='2'>Data Submited</font>";	
			document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
			//alert('url::'+url);                 
			httpObject.open("POST", url, true);            
			httpObject.send(null);    
			httpObject.onreadystatechange = setAjaxOutput1;  
		}   
	}



	 
	// Set ajax-result div     
	function setAjaxOutput(){         
		if(httpObject.readyState == 4){
             responseString= httpObject.responseText;

		
			//alert(document.getElementById('ajax-result')+'<>'+responseString);
			if(null!=responseString && responseString.indexOf("ERROR:")>-1){			
				isValid=false;
				alert('Current Meter Read Should be more than Previouse Actual Meter Read.');
				showScreen(); 
			}else{

                //alert('>>>>>>>>>>>>>>>>>>>>>>>>>:'+httpObject.responseText);
				isValid=true;
				showScreen(); 
			}
			document.getElementById('ajax-result').innerHTML = httpObject.responseText; 
			//serverMSG= httpObject.responseText;      
		}     
	}

	function setAjaxOutput1(){ 

	    // alert('setAjaxOutput1');        
		if(httpObject.readyState == 4){

			
			var  responseString= httpObject.responseText;
			/*Start 12-09-2017: Modified By Suraj Tiwari as per Jtrac DJB-4735 */
			if(null!=responseString && responseString.indexOf("Please")>-1){
				//alert('Server Busy Error');
				showScreen();
				document.getElementById("freezeAndGenBill").disabled=false;
				document.getElementById('btnSave').disabled=false;
				document.getElementById('btnPrev').disabled=false;
			}else if(null!=responseString && responseString.indexOf("ERROR:")>-1){			
				isValid=false;
				alert('Current Meter Read Should be more than Previouse Actual Meter Read.');
				showScreen(); 
			}else{

               // alert('>>>>>>>>>>>>>>>>>>>>>>>>>:'+httpObject.responseText);
				isValid=true;
				showScreen(); 
			}
			/*End 12-09-2017: Modified By Suraj Tiwari as per Jtrac DJB-4735 */
			document.getElementById('ajax-result').innerHTML = httpObject.responseText; 
			//serverMSG= httpObject.responseText;      
		}     
	}
	
	//=======================Ajax Call End=================================
	//Disable Going Back
	window.history.forward(1);
	//Global Variables
	var isValid=true;
	var toUpdateSeqNO="";
	var toUpdateKNO="";
	var toUpdateBillRound="";
	//var toUpdateConsumerStatus="";	
	var toUpdateMeterReadDate="";
	var toUpdateMeterReadDateConfirm="";
	var toUpdateMeterReadStatus="";
	var toUpdateCurrentMeterRead="";
	var toUpdateCurrentMeterReadConfirm="";
	var toUpdateNewAvgConsumption="";
	var toUpdateNewAvgConsumptionConfirm="";
	var toUpdateNewNoOfFloor="";
	var toUpdateNewNoOfFloorConfirm="";
	var toUpdateRemarks="";
	var dataSaved=false;
		var isPopUpWindow=false;	
	//var serverMSG="";
	function clearToUpdateVar(){
		toUpdateSeqNO="";
		toUpdateKNO="";
		toUpdateBillRound="";
		toUpdateMeterReadDate="";
		toUpdateMeterReadDateConfirm="";			
		toUpdateMeterReadStatus="";
		toUpdateCurrentMeterRead="";
		toUpdateCurrentMeterReadConfirm="";
		toUpdateNewAvgConsumption="";
		toUpdateNewAvgConsumptionConfirm="";
		toUpdateNewNoOfFloor="";
		toUpdateNewNoOfFloorConfirm="";
		toUpdateConsumerStatus="";
		toUpdateRemarks="";
		setStatusBarMessage('');
	}	
	//Update Record
	function fnUpdateRecord(page){	
		var curPG=currentPage;
		isValid=true;
		dataSaved=false;
		var resetFlag="";
		//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
		
		toUpdateConsumerStatus='60';
		if(toUpdateConsumerStatus!='60'){
			//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
			if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
				alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
				var fieldId="remarks"+currentPage; 
			    document.getElementById(fieldId).focus();
		        isValid=false;
			}else{
			//=============================================================================================		
	 	 		
				var fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);
				if(toUpdateKNO!='' && toUpdateBillRound!=''){
					getAll();
					dataSaved=true;
					doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
				}
				currentPage=page;
			}
		}else{//alert('2');
			if(validate()){
				var fieldId="hidMeterStatus"+currentPage; 
				document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
				fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);
				//alert(isValid && toUpdateKNO!=''&& toUpdateBillRound!='' && toUpdateMeterReadDate.length==10);
				if(isValid && toUpdateKNO!=''&& toUpdateBillRound!='' && toUpdateMeterReadDate.length==10 ){
					getAll();
					dataSaved=true;
					doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
				}
				currentPage=page;
			}
		}
		if(!dataSaved && isValid){
	    	if(!confirm('No Data to Save.\nAre you sure you want to move away without Saving Data?')){
	    		isValid=false;
	    		currentPage=curPG;
		    }
		}
	}
	function fnUpdateLastRecord(page){	//alert("fnUpdateLastRecordfnUpdateLastRecordfnUpdateLastRecord");		
		isValid=true;
		dataSaved=false;
		var resetFlag="";
		if(toUpdateConsumerStatus!='60'){
			//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
			if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
				alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
				var fieldId="remarks"+currentPage; 
			    document.getElementById(fieldId).focus();
		        isValid=false;
			}else{
			//=============================================================================================		
	 	 		var fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);		
				if(toUpdateKNO!='' && toUpdateBillRound!=''){
					getAll();
					dataSaved=true;
					doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					
				}
			}
		}else{
			if(validate()){
				
				var fieldId="hidMeterStatus"+currentPage; 
				document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
				fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);


				if(isValid && toUpdateKNO!=''&& toUpdateBillRound!=''){

					//alert('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB');
					getAll();
					dataSaved=true;
					doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					
				}
			}
		}
		if(!dataSaved && isValid){
	    	alert('No Data to Save.');
	    	isValid=false;
		}
			
	}

/***********************************************************************************************************/

	function fnUpdateLastRecordforFreeze(page){	//alert('fnUpdateLastRecordforFreeze');		
		isValid=true;
		dataSaved=false;
		var resetFlag="";
		if(toUpdateConsumerStatus!='60'){
			//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
			if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
				alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
				var fieldId="remarks"+currentPage; 
			    document.getElementById(fieldId).focus();
		        isValid=false;
			}else{
			//=============================================================================================		
	 	 		var fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);		
				if(toUpdateKNO!='' && toUpdateBillRound!=''){
					getAll();
					dataSaved=true;
					doAjaxCallForFreeze(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					
				}
			}
		}else{
			if(validate()){
				
				var fieldId="hidMeterStatus"+currentPage; 
				document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
				fieldId="hidConStatus"+currentPage;
				document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);


				if(isValid && toUpdateKNO!=''&& toUpdateBillRound!=''){

					//alert('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB');
					getAll();
					dataSaved=true;

					//alert("inside before ajax call doAjaxCallForFreeze");
					doAjaxCallForFreeze(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					
				}
			}
		}
		if(!dataSaved && isValid){
	    	alert('No Data to Save.');
	    	isValid=false;
		}
			
	}
/*******************************************************************************************************************/

function fnUpdateLastRecordforFreezeAndGenerateBill(page){	//alert('fnUpdateLastRecordforFreezeAndGenerateBill');		
isValid=true;
dataSaved=false;
var resetFlag="";
if(toUpdateConsumerStatus!='60'){
	//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
	if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
		alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
		var fieldId="remarks"+currentPage; 
	    document.getElementById(fieldId).focus();
        isValid=false;
	}else{
	//=============================================================================================		
	 		var fieldId="hidConStatus"+currentPage;
		document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);		
		if(toUpdateKNO!='' && toUpdateBillRound!=''){
			getAll();
			dataSaved=true;
			doAjaxCallForFreezeAndGenerateBill(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
			
		}
	}
}else{
	if(validate()){
		
		var fieldId="hidMeterStatus"+currentPage; 
		document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
		fieldId="hidConStatus"+currentPage;
		document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);


		if(isValid && toUpdateKNO!=''&& toUpdateBillRound!=''){

			//alert('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB');
			getAll();
			dataSaved=true;

			//alert("inside before ajax call doAjaxCallForFreeze");
			doAjaxCallForFreezeAndGenerateBill(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
			
		}
	}
}
if(!dataSaved && isValid){
	alert('No Data to Save.');
	isValid=false;
}
	
}



	
/****************************************************************************************************************/
	function setConsumerStatus(obj){
		toUpdateConsumerStatus=Trim(obj.value);			
		disableFields(obj);
	}
	function disableFields(obj){			
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var ddIdConfirm="meterReadDDConfirm"+currentPage;
		var mmIdConfirm="meterReadMMConfirm"+currentPage;
		var meterReadStatusId="meterReadStatus"+currentPage;
		var currentMeterReadId="currentMeterRead"+currentPage;
		var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;
		var newAverageConsumptionId="newAverageConsumption"+currentPage;
		var newAverageConsumptionConfirmId="newAverageConsumptionConfirm"+currentPage;
		var newNoOfFloorId="newNoOfFloor"+currentPage;
		var newNoOfFloorConfirmId="newNoOfFloorConfirm"+currentPage;
		if(obj.value!='60'){
			document.getElementById(ddId).disabled=true;
			document.getElementById(mmId).disabled=true;
			document.getElementById(ddIdConfirm).disabled=true;
			document.getElementById(mmIdConfirm).disabled=true;
			document.getElementById(meterReadStatusId).disabled=true;
			document.getElementById(currentMeterReadId).disabled=true;
			document.getElementById(currentMeterReadConfirmId).disabled=true;
			document.getElementById(newAverageConsumptionId).disabled=true;
			document.getElementById(newAverageConsumptionConfirmId).disabled=true;				
			document.getElementById(newNoOfFloorId).disabled=true;
			document.getElementById(newNoOfFloorConfirmId).disabled=true;	
			toUpdateMeterReadDate="";
			toUpdateMeterReadDateConfirm="";			
			toUpdateMeterReadStatus="";
			toUpdateCurrentMeterRead="";
			toUpdateCurrentMeterReadConfirm="";
			toUpdateNewAvgConsumption="";
			toUpdateNewAvgConsumptionConfirm="";
			toUpdateNewNoOfFloor="";
			toUpdateNewNoOfFloorConfirm="";
			toUpdateConsumerStatus=obj.value;
		}else{
			document.getElementById(ddId).disabled=false;
			document.getElementById(mmId).disabled=false;
			document.getElementById(ddIdConfirm).disabled=false;
			document.getElementById(mmIdConfirm).disabled=false;
			document.getElementById(meterReadStatusId).disabled=false;
			document.getElementById(currentMeterReadId).disabled=false;
			document.getElementById(currentMeterReadConfirmId).disabled=false;
			document.getElementById(newAverageConsumptionId).disabled=false;
			document.getElementById(newAverageConsumptionConfirmId).disabled=false;
			document.getElementById(newNoOfFloorId).disabled=false;
			document.getElementById(newNoOfFloorConfirmId).disabled=false;
			toUpdateConsumerStatus=obj.value;
		}
		//fnSetReadType();	
	}
	function disableFieldsOnConsumerType(){
		var consumerStatusId="consumerStatus"+currentPage;
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var ddIdConfirm="meterReadDDConfirm"+currentPage;
		var mmIdConfirm="meterReadMMConfirm"+currentPage;
		var meterReadStatusId="meterReadStatus"+currentPage;
		var currentMeterReadId="currentMeterRead"+currentPage;
		var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;
		document.getElementById(meterReadStatusId).disabled=true;
		document.getElementById(currentMeterReadId).disabled=true;
		document.getElementById(currentMeterReadConfirmId).disabled=true;			
		//clearToUpdateVar();	
		//fnSetReadType();
	}
	function resetMeterReadDate(){
		var ddId="meterReadDDConfirm"+currentPage;
		var mmId="meterReadMMConfirm"+currentPage;
		document.getElementById(ddId).value="";
		document.getElementById(mmId).value="";
		ddId="meterReadDD"+currentPage;
		mmId="meterReadMM"+currentPage;
		document.getElementById(ddId).value="";
		document.getElementById(mmId).value="";
		document.getElementById(ddId).focus();
		document.getElementById(ddId).focus();
		toUpdateMeterReadDate="";
		toUpdateMeterReadDateConfirm="";
	}		
	function resetCurrentMeterRead(){
		var fieldValue="";			
		var fieldId="currentMeterReadConfirm"+currentPage;
		document.getElementById(fieldId).value=fieldValue;	
		fieldId="currentMeterRead"+currentPage;
		document.getElementById(fieldId).value=fieldValue;


		document.getElementById(fieldId).focus();
		
	}
	function resetNewAvgConsumption(){
		var fieldValue="";
		fieldId="newAverageConsumptionConfirm"+currentPage;
		document.getElementById(fieldId).value=fieldValue;
		fieldId="newAverageConsumption"+currentPage;
		document.getElementById(fieldId).value=fieldValue;
		document.getElementById(fieldId).focus();
	}
	function resetAll(){
		if(confirm("Are You Sure You want to Reset ?")){
			var fieldId="seqNo"+currentPage;
			var fieldValue="";
			/*document.getElementById(fieldId).value=fieldValue;			
			
			fieldId="kno"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			
			fieldId="billRound"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			*/
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			ddId="meterReadDDConfirm"+currentPage;
			mmId="meterReadMMConfirm"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
						
			fieldId="meterReadStatus"+currentPage;
			document.getElementById(fieldId).value=fieldValue;	
			
			fieldId="currentMeterRead"+currentPage;
			document.getElementById(fieldId).value="";

			fieldId="currentMeterReadConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;			
			
			fieldId="newAverageConsumption"+currentPage;
			document.getElementById(fieldId).value="";
			fieldId="newAverageConsumptionConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
			fieldId="newNoOfFloor"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			fieldId="newNoOfFloorConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			//=========================================================			
			fieldId="consumerStatus"+currentPage;			
			document.getElementById(fieldId).value="60";
			setAll();
			var resetFlag="Y";
			
			//doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
		}
	}


	function resetAllWhileSearchNotfound(){
		
			//var fieldId="seqNo"+currentPage;
			//var fieldValue="";
			/*document.getElementById(fieldId).value=fieldValue;			
			
			fieldId="kno"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			
			fieldId="billRound"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			*/
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			ddId="meterReadDDConfirm"+currentPage;
			mmId="meterReadMMConfirm"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			/****************************************************************/			
			document.getElementById("newAverageConsumption1").value="";
			document.getElementById("currentMeterRead1").value="";
			document.getElementById("meterReadStatus1").value="";
			document.getElementById("currentMeterReadConfirm1").value="";
			document.getElementById("newAverageConsumptionConfirm1").value="";
			document.getElementById("newNoOfFloor1").value="";
			document.getElementById("meterReadYYYY1").value="";
			document.getElementById('meterReadYYYYConfirm1').value="";
			document.getElementById('svcCycRtSeq').value="";
            //alert('>>>>>>>>>>>'+document.getElementById('meterReadTypeSpan1').value)
			document.getElementById('meterReadTypeSpan1').value="";
			document.getElementById('customerBillRound').value="";
			document.getElementById("customerkno").value="";
			document.getElementById('customerArea').value="";
			document.getElementById('customerZone').value="";
			document.getElementById('customerStatus').value="";
			document.getElementById("billTypeSpan1").value="";
			document.getElementById("customerBillID").value="";
			
			
			//document.getElementById(mmId).value="";
			//document.getElementById(mmId).value="";
			//document.getElementById(mmId).value="";
			//document.getElementById(mmId).value="";
			//document.getElementById(mmId).value="";
			//document.getElementById(mmId).value="";
            /*********************************************************************/ 



			//fieldId="meterReadStatus"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;	
			
			//fieldId="currentMeterRead"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;
			//fieldId="currentMeterReadConfirm"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;			
			
			//fieldId="newAverageConsumption"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;
			//fieldId="newAverageConsumptionConfirm"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;
			//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
			//fieldId="newNoOfFloor"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;
			//fieldId="newNoOfFloorConfirm"+currentPage;
			//document.getElementById(fieldId).value=fieldValue;
			//=========================================================			
			//fieldId="consumerStatus"+currentPage;			
			//document.getElementById(fieldId).value="60";
			//setAll();
			//var resetFlag="Y";
			//doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
		
	}









	
	function resetCorrespondingToStatus(){	
		
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		document.getElementById(ddId).value="";
		document.getElementById(mmId).value="";
		ddId="meterReadDDConfirm"+currentPage;
		mmId="meterReadMMConfirm"+currentPage;
		document.getElementById(ddId).value="";
		document.getElementById(mmId).value="";
		var fieldValue="";		
		var fieldId="meterReadStatus"+currentPage;
		document.getElementById(fieldId).value=fieldValue;
		fieldId="currentMeterRead"+currentPage;
		document.getElementById(fieldId).value=fieldValue;
		fieldId="currentMeterReadConfirm"+currentPage;
		document.getElementById(fieldId).value=fieldValue;		
		fieldId="newAverageConsumption"+currentPage;
		document.getElementById(fieldId).value=fieldValue;
		fieldId="newAverageConsumptionConfirm"+currentPage;
		document.getElementById(fieldId).value=fieldValue;	
	}
	function setMeterReadDate(){
		setAll();
		var errorMsg="none";
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
		document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
		var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
		if(meterReadDate.length==10){
			errorMsg = getDateErrorMessageString(meterReadDate);
		}
		if(meterReadDate.length==10 &&errorMsg!='none'){		
				document.getElementById(ddId).value="";
				document.getElementById(mmId).value="";
				alert('PLEASE ENTER A VALID DATE.');
				document.getElementById(ddId).focus();
				return;
			
		}else{
			toUpdateMeterReadDate=meterReadDate;
			toUpdateMeterReadDateConfirm="";
		}			
	}
	function setMeterReadStatus(obj){
		setAll();
		toUpdateMeterReadStatus=Trim(obj.value);
		fnCheckMeterReadStatus();
		fnSetReadType();			
		
	}
	function setCurrentMeterRead(obj){
		setAll();
		toUpdateCurrentMeterRead=Trim(obj.value);
		toUpdateCurrentMeterReadConfirm="";			
	}
	function setNewAvgConsumption(obj){
		setAll();
		toUpdateNewAvgConsumption=Trim(obj.value);
		toUpdateNewAvgConsumptionConfirm="";
	}
	//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
	function setNewNoOfFloor(obj){
		setAll();
		toUpdateNewNoOfFloor=Trim(obj.value);
		toUpdateNewNoOfFloorConfirm="";
	}
	//====================================================
	//Added for Remarks on 16-11-2012 by Tuhin============= 
	function setRemarks(obj){
		setAll();
		toUpdateRemarks=Trim(obj.value);
	}
	//====================================================	
	
	function enableMeterReadDate(){				
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		var meterReadDateSpanId="meterReadDateSpan"+currentPage;				
		var html="<input type='text' name='"+ddId+"' id='"+ddId+"' size='1' maxlength='2' class='textbox'  style='text-align:center;'  onchange='setMeterReadDate();'onkeyup='fnSetFocusMM(this);' value='"+document.getElementById(ddId).value+"'/>&nbsp;/&nbsp;";
		html=html+"<input type='text' name='"+mmId+"' id='"+mmId+"' size='1' maxlength='2' class='textbox'  style='text-align:center;'  onchange='setMeterReadDate();' onkeyup='fnSetFocusDDConfirm(this);' value='"+document.getElementById(mmId).value+"'/>&nbsp;/&nbsp;";
		document.getElementById(meterReadDateSpanId).innerHTML=html;			
		var ddIdConfirm="meterReadDDConfirm"+currentPage;
		var mmIdConfirm="meterReadMMConfirm"+currentPage;
		document.getElementById(ddIdConfirm).value="";
		document.getElementById(mmIdConfirm).value="";
		var confirmDateId="confirmDate"+currentPage;			
		document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
		document.getElementById('data_submited').innerHTML ="&nbsp;";			
		  
	}

	function disableMeterReadDate(){
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		var meterReadDateSpanId="meterReadDateSpan"+currentPage;				
		var html="<input type='password' name='"+ddId+"' id='"+ddId+"' size='2' maxlength='2' class='textbox'  style='text-align:center;'  onfocus='enableMeterReadDate();' onchange='setMeterReadDate();' value='"+document.getElementById(ddId).value+"'/>&nbsp;/&nbsp;";
		html=html+"<input type='password' name='"+mmId+"' id='"+mmId+"' size='2' maxlength='2' class='textbox'  style='text-align:center;'  onfocus='enableMeterReadDate();' onchange='setMeterReadDate();' value='"+document.getElementById(mmId).value+"'/>&nbsp;/&nbsp;";
		document.getElementById(meterReadDateSpanId).innerHTML=html;
	}

	function checkMeterReadDate(){			
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		var ddValue=document.getElementById(ddId).value;
		var mmValue=document.getElementById(mmId).value;
		var yyyyValue=document.getElementById(yyyyId).value;
		var meterReadDate=ddValue+"/"+mmValue+"/"+yyyyValue;					
		var ddConfirmId="meterReadDDConfirm"+currentPage;
		var mmConfirmId="meterReadMMConfirm"+currentPage;
		var yyyyConfirmId="meterReadYYYYConfirm"+currentPage;	
		document.getElementById(ddConfirmId).value=makeTwoDigit(Trim(document.getElementById(ddConfirmId).value));
		document.getElementById(mmConfirmId).value=makeTwoDigit(Trim(document.getElementById(mmConfirmId).value));
		var ddConfirmValue=document.getElementById(ddConfirmId).value;
		var mmConfirmValue=document.getElementById(mmConfirmId).value;
		var yyyyConfirmValue=document.getElementById(yyyyConfirmId).value;
		var meterReadDateConfirm=ddConfirmValue+"/"+mmConfirmValue+"/"+yyyyConfirmValue;
		toUpdateMeterReadDateConfirm=Trim(meterReadDateConfirm);
		meterReadDate=Trim(meterReadDate);
		meterReadDateConfirm=Trim(meterReadDateConfirm);
		if(meterReadDate.length==10 && meterReadDateConfirm.length==10){
			var confirmDateId="confirmDate"+currentPage;
			if(parseInt(ddValue)== parseInt(ddConfirmValue) && parseInt(mmValue)==parseInt(mmConfirmValue) && parseInt(yyyyValue)== parseInt(yyyyConfirmValue)){
				//alert(meterReadDate+'<2>'+ meterReadDateConfirm);
				toUpdateMeterReadDateConfirm=meterReadDateConfirm;
				toUpdateMeterReadDate=meterReadDate;
				document.getElementById(confirmDateId).innerHTML="<font color='green'><b>OK</b></font>";	
								
			}else{
				//alert(meterReadDate+'<1>'+ meterReadDateConfirm);					
				document.getElementById(confirmDateId).innerHTML="<font color='red'>Does Not Match.</font>";
				toUpdateMeterReadDateConfirm="";
				resetMeterReadDate();				
			}				
		}			
	}
	function enableCurrentMeterRead(){
		//fnCheckMeterReadStatus();
		var consumerStatusId="consumerStatus"+currentPage;
		var consumerStatusValue=Trim(document.getElementById(consumerStatusId).value);
		//alert(consumerStatusValue);
		if(consumerStatusValue!='UNMETERED'){
			var fieldId="currentMeterRead"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			var spanId="currentMeterReadSpan"+currentPage;				
			var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onfocus='fnCheckMeterReadStatus();'  onblur='IsPositiveNumber(this);' onchange='setCurrentMeterRead(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;
		}
		fnCheckMeterReadStatus();
	}
	function disableCurrentMeterRead(){
		fnCheckMeterReadStatus();
		var fieldId="currentMeterRead"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		IsNumber(document.getElementById(fieldId));
		var spanId="currentMeterReadSpan"+currentPage;				
		var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableCurrentMeterRead();' onchange='setCurrentMeterRead(this);' value='"+fieldValue+"'/>";
		document.getElementById(spanId).innerHTML=html;
	}
	function checkCurrentMeterRead(){
		var textValue;	
		var textConfirm;			
		var fieldId="currentMeterReadConfirm"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		textConfirm=Trim(fieldValue);
		fieldId="currentMeterRead"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		textValue=Trim(fieldValue);
		toUpdateCurrentMeterReadConfirm=textConfirm;			
		var confirmId="confirmCurrentMeterRead"+currentPage;
		if(textValue!='' && textConfirm!=''){
			if(parseFloat(textValue)!=parseFloat(textConfirm)){					
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
				toUpdateCurrentMeterReadConfirm="";
				//resetCurrentMeterRead();				
			}else{
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateCurrentMeterReadConfirm=textConfirm;
				toUpdateCurrentMeterRead=textValue;
			}
		}
	}	
	function enableNewAverageConsumption(){
		var fieldId="newAverageConsumption"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		var spanId="newAverageConsumptionSpan"+currentPage;				
		var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onblur='IsNumber(this);' onchange='setNewAvgConsumption(this);' value='"+fieldValue+"'/>";
		document.getElementById(spanId).innerHTML=html;	
	}
	function disableNewAverageConsumption(){
		var fieldId="newAverageConsumption"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		var spanId="newAverageConsumptionSpan"+currentPage;				
		var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableNewAverageConsumption();' onchange='setNewAvgConsumption(this);' value='"+fieldValue+"'/>";
		document.getElementById(spanId).innerHTML=html;
	}		
	function checkNewAverageConsumption(){
		var textValue;	
		var textConfirm;				
		var fieldId="newAverageConsumptionConfirm"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		textConfirm=fieldValue;	
		fieldId="newAverageConsumption"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		textValue=fieldValue;			
		toUpdateNewAvgConsumptionConfirm=textConfirm;			
		var confirmId="confirmNewAverageConsumption"+currentPage;
		if(textValue!='' && toUpdateNewAvgConsumptionConfirm!=''){
			if(parseFloat(textValue)!=parseFloat(textConfirm)){							
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
				toUpdateNewAvgConsumptionConfirm="";
				//resetNewAvgConsumption();			
			}else{
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateNewAvgConsumptionConfirm=textConfirm;
				toUpdateNewAvgConsumption=textValue;
			}
		}
	}
	//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
	function enableNewNoOfFloor(){
		var fieldId="newNoOfFloor"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		var spanId="newNoOfFloorSpan"+currentPage;				
		var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onblur='IsNumber(this);' onchange='setNewNoOfFloor(this);' value='"+fieldValue+"'/>";
		document.getElementById(spanId).innerHTML=html;	
	}
	function disableNewNoOfFloor(){
		var fieldId="newNoOfFloor"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		var spanId="newNoOfFloorSpan"+currentPage;				
		var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableNewNoOfFloor();' onchange='setNewNoOfFloor(this);' value='"+fieldValue+"'/>";
		document.getElementById(spanId).innerHTML=html;
	}
	function checkNewNoOfFloor(){
		var textValue;	
		var textConfirm;				
		var fieldId="newNoOfFloorConfirm"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		textConfirm=fieldValue;	
		fieldId="newNoOfFloor"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		textValue=fieldValue;			
		toUpdateNewNoOfFloorConfirm=textConfirm;			
		var confirmId="confirmNewNoOfFloorConfirm"+currentPage;
		if(textValue!='' && toUpdateNewNoOfFloorConfirm!=''){
			if(parseInt(textValue)!=parseInt(textConfirm)){							
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
				toUpdateNewNoOfFloorConfirm="";
				//resetNewAvgConsumption();			
			}else{
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateNewNoOfFloorConfirm=textConfirm;
				toUpdateNewNoOfFloor=textValue;
			}
		}
	}
	//===========================================================
	function getAll(){	
		
		var fieldId="seqNo"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateSeqNO=fieldValue;
		
		fieldId="kno"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateKNO=fieldValue;
		
		fieldId="billRound"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateBillRound=fieldValue;
		
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
		document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
		var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
		toUpdateMeterReadDate=meterReadDate;
		
		fieldId="meterReadStatus"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateMeterReadStatus=fieldValue;				
		
		fieldId="currentMeterRead"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateCurrentMeterRead=fieldValue;
		
		fieldId="newAverageConsumption"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateNewAvgConsumption=fieldValue;	
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		fieldId="newNoOfFloor"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateNewNoOfFloor=fieldValue;	
		//=====================================================			
		fieldId="consumerStatus"+currentPage;			
		fieldValue=Trim(document.getElementById(fieldId).value);				
		toUpdateConsumerStatus=fieldValue;	
		//Added for Remarks 16-2012 by Tuhin ==================
		fieldId="remarks"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateRemarks=fieldValue;	
		//=====================================================	
		
	}
	
	/*********************************************************************************************/
	function setAllForConsumerStatus()
	{
		var consumerStatus=document.getElementById('consumerStatus1').value;
		//alert('consumerStatus::::::'+consumerStatus);
		
		if(consumerStatus==60 || consumerStatus==10)
		{
		document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
		document.getElementById('data_submited').innerHTML ="&nbsp;";	
		
		var fieldId="seqNo"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateSeqNO=fieldValue;
		
		fieldId="kno"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateKNO=fieldValue;
		
		fieldId="billRound"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateBillRound=fieldValue;
		
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
		document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
		var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
		toUpdateMeterReadDate=meterReadDate;
		toUpdateMeterReadDateConfirm=meterReadDate;
		
		fieldId="meterReadStatus"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateMeterReadStatus=fieldValue;				
		
		if(currentMeterReadText!=''){
			toUpdateCurrentMeterRead=currentMeterReadText;
			toUpdateCurrentMeterReadConfirm=currentMeterReadText;	
		}else{
			fieldId="currentMeterRead"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateCurrentMeterRead=fieldValue;
			toUpdateCurrentMeterReadConfirm=fieldValue;	
		}
		if(newAvgConsumptionText!=''){
			toUpdateNewAvgConsumption=newAvgConsumptionText;
			toUpdateNewAvgConsumptionConfirm=newAvgConsumptionText;
		}else{
			fieldId="newAverageConsumption"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewAvgConsumption=fieldValue;
			toUpdateNewAvgConsumptionConfirm=fieldValue;
		}
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		if(newNoOfFloorText!=''){
			toUpdateNewNoOfFloor=newNoOfFloorText;
			toUpdateNewNoOfFloorConfirm=newNoOfFloorText;
		}else{
			fieldId="newNoOfFloor"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewNoOfFloor=fieldValue;
			toUpdateNewNoOfFloorConfirm=fieldValue;
		}
		
		//========================================================
		
		fieldId="consumerStatus"+currentPage;			
		fieldValue=Trim(document.getElementById(fieldId).value);				
		toUpdateConsumerStatus=fieldValue;
		
		//	alert("Invalid data :"+fieldValue);
		
		disableFields(document.getElementById(fieldId));			
		//====================Added for Remarks on 16-11-2012 by Tuhin 
		if(toUpdateConsumerStatus=='15'){
			var fieldId_remarks="remarks"+currentPage;
			//document.getElementById(fieldId_remarks).value="";
			fieldId_remarks="remarksDiv1"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'block';	
			fieldId_remarks="remarksDiv2"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'block';
		}else{
			var fieldId_remarks="remarks"+currentPage;
			document.getElementById(fieldId_remarks).value="";
			fieldId_remarks="remarksDiv1"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'none';
			fieldId_remarks="remarksDiv2"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'none';
		}
		//========================================================
		fieldId="popup"+currentPage;
		if(toUpdateConsumerStatus=='30'){
			if(document.getElementById(fieldId)){
				//document.getElementById(fieldId).innerHTML="<a href='#' onclick='clearMRMessage();popupMRScreen();'>Add Details</a>";
				//document.getElementById(fieldId).innerHTML="<a href='#' onclick='javascript:fnNotImplemented();'>Add Details</a>";
				//setAll();
				//popupMRScreen();
		  }else{
			  if(document.getElementById('popupMTR')){
				  //document.getElementById('popupMTR').innerHTML="<a href='#' onclick='popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);'>Add Details</a>";
			  }
			  if(isPopUpWindow){
				 // alert();
				  popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);
			  }
		  }
		}else{ 
			if(document.getElementById(fieldId)){
				document.getElementById(fieldId).innerHTML="";
			}
			if(document.getElementById('popupMTR')){
				  document.getElementById('popupMTR').innerHTML="";
			}
		}
		if(toUpdateConsumerStatus!='60'){
			resetCorrespondingToStatus();				
		}
		fieldId="hidConsumerType"+currentPage; 
		fieldValue=document.getElementById(fieldId).value;
		if(fieldValue!='METERED'){
			disableFieldsOnConsumerType();
		}
		fnSetReadType();
		//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
		}
		else 
		{
           // alert('consumerStatus-------->'+consumerStatus);

		    document.getElementById('meterReadDD1').disable=true; 
		    document.getElementById('meterReadMM1').disable=true;    
		    document.getElementById('meterReadYYYY1').disable=true;    
		    document.getElementById('meterReadDDConfirm1').disable=true;    
		    document.getElementById('meterReadMMConfirm1').disable=true;    
		    document.getElementById('meterReadYYYYConfirm1').disable=true;    
		    document.getElementById('meterReadStatus1').disable=true;
		    document.getElementById('currentMeterRead1').disable=true;     
		    document.getElementById('currentMeterReadConfirm1').disable=true;     
		    document.getElementById('currentAverageConsumption1').disable=true;     
		    document.getElementById('newAverageConsumption1').disable=true;     
		    document.getElementById('newAverageConsumptionConfirm1').disable=true;
		         
		    document.getElementById('newNoOfFloor1').disable=true;       
		    document.getElementById('newNoOfFloorConfirm1').disable=true;       
		    document.getElementById('freezeAndGenBill').disable=true; 
		    document.getElementById('btnPrev').disable=true; 
		    document.getElementById('btnSave').disable=true;                  
		   // alert('consumerStatus 2-------->'+consumerStatus);
		}	
	}
	
	
	
	
	
	/******************************************************************************************/
	function setAll(){
		
		document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
		document.getElementById('data_submited').innerHTML ="&nbsp;";	
		
		var fieldId="seqNo"+currentPage;
		var fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateSeqNO=fieldValue;
		
		fieldId="kno"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateKNO=fieldValue;
		
		fieldId="billRound"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateBillRound=fieldValue;
		
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
		document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
		var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
		toUpdateMeterReadDate=meterReadDate;
		toUpdateMeterReadDateConfirm=meterReadDate;
		
		fieldId="meterReadStatus"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		toUpdateMeterReadStatus=fieldValue;				
		
		if(currentMeterReadText!=''){
			toUpdateCurrentMeterRead=currentMeterReadText;
			toUpdateCurrentMeterReadConfirm=currentMeterReadText;	
		}else{
			fieldId="currentMeterRead"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateCurrentMeterRead=fieldValue;
			toUpdateCurrentMeterReadConfirm=fieldValue;	
		}
		if(newAvgConsumptionText!=''){
			toUpdateNewAvgConsumption=newAvgConsumptionText;
			toUpdateNewAvgConsumptionConfirm=newAvgConsumptionText;
		}else{
			fieldId="newAverageConsumption"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewAvgConsumption=fieldValue;
			toUpdateNewAvgConsumptionConfirm=fieldValue;
		}
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		if(newNoOfFloorText!=''){
			toUpdateNewNoOfFloor=newNoOfFloorText;
			toUpdateNewNoOfFloorConfirm=newNoOfFloorText;
		}else{
			fieldId="newNoOfFloor"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewNoOfFloor=fieldValue;
			toUpdateNewNoOfFloorConfirm=fieldValue;
		}
		
		//========================================================
		
		fieldId="consumerStatus"+currentPage;			
		fieldValue=Trim(document.getElementById(fieldId).value);				
		toUpdateConsumerStatus=fieldValue;
		
		//	alert("Invalid data :"+fieldValue);
		
		disableFields(document.getElementById(fieldId));			
		//====================Added for Remarks on 16-11-2012 by Tuhin 
		if(toUpdateConsumerStatus=='15'){
			var fieldId_remarks="remarks"+currentPage;
			//document.getElementById(fieldId_remarks).value="";
			fieldId_remarks="remarksDiv1"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'block';	
			fieldId_remarks="remarksDiv2"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'block';
		}else{
			var fieldId_remarks="remarks"+currentPage;
			document.getElementById(fieldId_remarks).value="";
			fieldId_remarks="remarksDiv1"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'none';
			fieldId_remarks="remarksDiv2"+currentPage;
			document.getElementById(fieldId_remarks).style.display = 'none';
		}
		//========================================================
		fieldId="popup"+currentPage;
		if(toUpdateConsumerStatus=='30'){
			if(document.getElementById(fieldId)){
				//document.getElementById(fieldId).innerHTML="<a href='#' onclick='clearMRMessage();popupMRScreen();'>Add Details</a>";
				//document.getElementById(fieldId).innerHTML="<a href='#' onclick='javascript:fnNotImplemented();'>Add Details</a>";
				//setAll();
				//popupMRScreen();
		  }else{
			  if(document.getElementById('popupMTR')){
				  //document.getElementById('popupMTR').innerHTML="<a href='#' onclick='popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);'>Add Details</a>";
			  }
			  if(isPopUpWindow){
				 // alert();
				  popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);
			  }
		  }
		}else{ 
			if(document.getElementById(fieldId)){
				document.getElementById(fieldId).innerHTML="";
			}
			if(document.getElementById('popupMTR')){
				  document.getElementById('popupMTR').innerHTML="";
			}
		}
		if(toUpdateConsumerStatus!='60'){
			resetCorrespondingToStatus();				
		}
		fieldId="hidConsumerType"+currentPage; 
		fieldValue=document.getElementById(fieldId).value;
		if(fieldValue!='METERED'){
			disableFieldsOnConsumerType();
		}
		fnSetReadType();
		//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
			
	}
	function fnNotImplemented(){
		//alert('Not Implemented');
		 popupMRScreen();
	}
	function fnGoToPage(fromIndex,toIndex){
		document.forms[0].startPage.value=fromIndex;
		document.forms[0].lastPage.value=toIndex;
		document.forms[0].buttonClicked.value="";
		document.forms[0].action="dataEntry.action";
		hideScreen();
		document.forms[0].submit();
	}
	function fnGoToPageRange(obj){
		var fromIndex=Trim(obj.value);
		var toIndex=parseInt(fromIndex)+parseInt(MAX_RECORD_PER_PAGE)-1;	
		document.forms[0].startPage.value=fromIndex;
		document.forms[0].lastPage.value=toIndex;
		document.forms[0].buttonClicked.value="";
		document.forms[0].action="dataEntry.action";
		hideScreen();
		document.forms[0].submit();	
	}
	function fnGoTo(){
		var fromIndex=Trim(document.getElementById('goToRecNo').value);
		if(fromIndex.length!=0 && !isNaN(fromIndex)){		
			if(parseInt(fromIndex)>parseInt(totalRecords)){
				alert('Enter record no less than '+totalRecords);
				document.getElementById('goToRecNo').value="";
				document.getElementById('goToRecNo').focus();
				//return;
			}else if(parseInt(fromIndex)<1){
				alert('Enter record no greater than 1');
				document.getElementById('goToRecNo').value="";
				document.getElementById('goToRecNo').focus();
				//return;
			}else{
				var toIndex=parseInt(fromIndex)+parseInt(MAX_RECORD_PER_PAGE)-1;	
				document.forms[0].startPage.value=fromIndex;
				document.forms[0].lastPage.value=toIndex;
				document.forms[0].buttonClicked.value="";
				document.forms[0].action="dataEntry.action";
				hideScreen();
				document.forms[0].submit();
			}
		}else{
			alert('Record no should be Numeric.');
			document.getElementById('goToRecNo').value="";
			document.getElementById('goToRecNo').focus();
			//return;
		}
	}
	function fnGoToFreezePage(){
		hideScreen();
		//var answer = confirm("Are You Sure You want to Go To Freeze the MRD Screen and Review.");
		//if (answer){
			document.location.href="freeze.action";
		//}else{
			//showScreen();
		//}	
	}

	function setOnLoadValue(){	
		var fieldId="hidConsumerType"+currentPage; 
		var fieldValue=document.getElementById(fieldId).value;
		if(fieldValue!='METERED'){
			disableFieldsOnConsumerType();
		}
		fieldId="hidMeterStatus"+currentPage; 
		fieldValue=document.getElementById(fieldId).value;
		fieldId="meterReadStatus"+currentPage; 
		document.getElementById(fieldId).value=Trim(fieldValue);//alert('meterReadStatus>>'+fieldValue);
		fieldId="hidConStatus"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		//alert(fieldValue);
		fieldId="consumerStatus"+currentPage; 
		//fieldValue=Trim(document.getElementById(fieldId).value);
		//alert(fieldValue);
		var spanID="popup"+currentPage;
		if(document.getElementById(spanID)){
			document.getElementById(spanID).innerHTML="";
		}
		if(document.getElementById('popupMTR')){
			  document.getElementById('popupMTR').innerHTML="";
		}



      if(fieldValue==60 || fieldValue==10)
      {

    	  document.getElementById(fieldId).value="60";
       }


		//if(fieldValue=='15' ||fieldValue=='20'||fieldValue=='30'||fieldValue=='40'||fieldValue=='50'||fieldValue=='90'||fieldValue=='0'){
		else{
				document.getElementById(fieldId).value=Trim(fieldValue);				
			disableFields(document.getElementById(fieldId));
			spanID="popup"+currentPage;
			//alert(spanID+'<>'+document.getElementById(spanID));
			if(fieldValue=='30'){
				if(document.getElementById(spanID)){
					//document.getElementById(spanID).innerHTML="<a href='#' onclick='clearMRMessage();popupMRScreen();'>Add Details</a>";
				}else
					if(document.getElementById('popupMTR')){
						
						 // document.getElementById('popupMTR').innerHTML="<a href='#' onclick='popUpMeterReplacementScreen(parseInt("+document.getElementById('seqNO1').value+")+1,\""+document.getElementById('billRound1').value+"\",\""+document.getElementById('kno1').value+"\");'>Add Details</a>";
					  }
			}else if(fieldValue=='15'){//====================Added for Remarks on 16-11-2012 by Tuhin 	
					var fieldId_remarks="remarks"+currentPage;
					//document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';	
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';					
			}else{
				var fieldId_remarks="remarks"+currentPage;
				document.getElementById(fieldId_remarks).value="";
				fieldId_remarks="remarksDiv1"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'none';
				fieldId_remarks="remarksDiv2"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'none';
				if(document.getElementById(spanID)){
					document.getElementById(spanID).innerHTML="";
				}
			}
		}
		
		fieldId="consumerStatus"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		if('30'!=fieldValue){
			fnCheckMeterReadStatus();
		}
		fnSetReadType();
		fieldId="consumerStatus"+currentPage;
		//document.getElementById(fieldId).focus();	

	}


	function fnSetFocusMM(obj){
		var fValue=Trim(obj.value);
		if(fValue.length==2){
			var fieldId="meterReadMM"+currentPage;
			document.getElementById(fieldId).focus();
		}
	}
	function fnSetFocusDDConfirm(obj){
		var fValue=Trim(obj.value);
		if(fValue.length==2){
			var fieldId="meterReadDDConfirm"+currentPage;
			document.getElementById(fieldId).focus();
		}
	}
	function fnSetFocusMMConfirm(obj){
		var fValue=Trim(obj.value);
		if(fValue.length==2){
			var fieldId="meterReadMMConfirm"+currentPage;
			document.getElementById(fieldId).focus();
		}
	}
	function fnSetFocusMeterReadStatus(obj){
		var fValue=Trim(obj.value);
		if(fValue.length==2){
			var fieldId="meterReadStatus"+currentPage;
			if(document.getElementById(fieldId).disabled==false){
				document.getElementById(fieldId).focus();
			}else{
				fieldId="newAverageConsumption"+currentPage;
				document.getElementById(fieldId).focus();
			}
		}
	}


	function fnCheckMeterReadStatus()
	{

		var getconsPreBillProcStatus=Trim(document.getElementById('consPreBillProcStatus1').value);

       // alert('getconsPreBillProcStatus::::::>>>>'+ getconsPreBillProcStatus);
		var meterReadStatusId="meterReadStatus"+currentPage;
		var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
		//3. Current Meter Reading field should only be enabled for (OK, PUMP).
		if(getconsPreBillProcStatus <65 )
		{
		if(meterReadStatus=='OK' || meterReadStatus=='PUMP'){ 	 											
			var fieldId="currentMeterReadConfirm"+currentPage;
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			fieldId="currentMeterRead"+currentPage;
			//document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			//document.getElementById(fieldId).focus(); 
			//alert(document.getElementById(fieldId).disabled);
		}else{
			var fieldId="currentMeterRead"+currentPage;
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 								
			fieldId="currentMeterReadConfirm"+currentPage;
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 
		}
		}
		else
		{
            //alert('Field can not be edited as consumer is in invalid status');
			document.getElementById('currentMeterRead1').disabled=true;
			document.getElementById('currentMeterReadConfirm1').disabled=true;
			   
		}
		//4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be equal to Previous Meter Reading.
		
		
		if(getconsPreBillProcStatus <65 )
		{
		if(meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM'){
			//var fieldId="hidPrevMeterRead"+currentPage;
			var fieldId="hidPrevActMeterRead"+currentPage;//Changed by Previous Actual Registered Meter Read on 19-11-2012 as per discussion with with Ayan/Sabyasachi
			var prevMeterRead=Trim(document.getElementById(fieldId).value);		
			if(prevMeterRead!='NA'){
				fieldId="currentMeterRead"+currentPage;
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				fieldId="currentMeterReadConfirm"+currentPage;
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				toUpdateCurrentMeterReadConfirm=prevMeterRead;
				toUpdateCurrentMeterRead=prevMeterRead;
			}
		  }
		}

		else
		{

			//alert('Field can not be edited as consumer is in invalid status');
			document.getElementById('currentMeterReadConfirm1').disabled=true;
			document.getElementById('currentMeterRead1').disabled=true;   
		}
		/*Start:Added by Matiur Rahman as per JTrac #DJB-873 commented by Amit on 2013-01-18 16:48:59*/			
		/*var prevMeterReadStatusId="hidPrevMeterStatus"+currentPage;
		var prevMeterReadStatus=Trim(document.getElementById(prevMeterReadStatusId).value);
		var currReadType=fnGetReadType(meterReadStatus);
		var prevReadType=fnGetReadType(prevMeterReadStatus);
		//alert(meterReadStatus+'<>'+prevMeterReadStatus+'<>'+currReadType+'<>'+prevReadType);
		if(prevReadType=='Average Billing' && currReadType=='Volumetric Billing'){
			alert('As Previous Meter Read Remark is of Average Read Type and Current Meter Read Remark is of Regular Read Type\nSo,No emtimated meter read will be created\nThis is a case of meter replacement\nPlease Enter Meter Replacement Details.');
			var fieldId="consumerStatus"+currentPage;			
			document.getElementById(fieldId).value="30";
			setAll();
		}*/
		/*End:Added by Matiur Rahman as per JTrac #DJB-873 commented by Amit on 2013-01-18 16:48:59*/			
	}	
	
	function fnGetReadType(meterReaderRemarkCode){
		var readType="No Billing";
		if(Trim(meterReaderRemarkCode).length!=0){
			if(regularReadType.indexOf(meterReaderRemarkCode)>-1){
				readType="Volumetric Billing";
				return readType;
			}
			if(averageReadType.indexOf(meterReaderRemarkCode)>-1){
				readType="Average Billing";
				return readType;
			}
			if(provisionalReadType.indexOf(meterReaderRemarkCode)>-1){
				readType="Provisional Billing";
				return readType;
			}
			if(noBillingReadType.indexOf(meterReaderRemarkCode)>-1){
				readType="No Billing";
				return readType;
			}
		}
		return readType;
	}
	function fnSetReadType()
	{
		var meterReadStatusId="meterReadStatus"+currentPage;
		var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
		var readType=fnGetReadType(meterReadStatus);
		var meterReadTypeSpanId="meterReadTypeSpan"+currentPage;
		var billTypeSpanId="billTypeSpan"+currentPage;	
		var currentMeterReadId="currentMeterRead"+currentPage;			
		var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;	
		var consumerTypeId="hidConsumerType"+currentPage;
		var consumerType=Trim(document.getElementById(consumerTypeId).value);	
		document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
		document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";	
		if(readType=='Volumetric Billing'){
			var consumerStatusId="consumerStatus"+currentPage;
			var consumerStatus=Trim(document.getElementById(consumerStatusId).value);
			if(consumerStatus=='60' && consumerType=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
				document.getElementById(currentMeterReadId).disabled=false;
				document.getElementById(currentMeterReadConfirmId).disabled=false;
			}else{
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
			}				
			document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='green'><b>Regular</b></font>";
			document.getElementById(billTypeSpanId).innerHTML = "<font color='green'><b>Volumetric Billing</b></font>";
		}else{
			var consumerStatusId="consumerStatus"+currentPage;
			var consumerStatus=Trim(document.getElementById(consumerStatusId).value);
			//alert(consumerStatus+'<>'+meterReadStatus);
			if(consumerStatus=='60' && consumerType=='METERED' && meterReadStatus!=''){					
				document.getElementById(currentMeterReadId).value="0";
				toUpdateCurrentMeterRead="0";
				document.getElementById(currentMeterReadConfirmId).value="0";
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='red'><b>No Read</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='red'><b>"+readType+"</b></font>";
			}
			if(consumerType!='METERED' ||meterReadStatus==''){
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
			}
		}
	}
	//Data Entry Validations
	function validate(){
	 		//alert('in validate()');
		var textValue;	
		var textConfirm;
		var fieldId;
		var fieldValue;
		var hidFieldId="hidMeterReadDate"+currentPage;
		var hidFieldValue=Trim(document.getElementById(hidFieldId).value);
		var ddId="meterReadDD"+currentPage;
		var mmId="meterReadMM"+currentPage;
		var yyyyId="meterReadYYYY"+currentPage;
		document.getElementById(ddId).value=Trim(document.getElementById(ddId).value);
		document.getElementById(mmId).value=Trim(document.getElementById(mmId).value);
		var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;

		if(meterReadDate.length!=10){
			alert('Meter Read Date/Bill Generation Date is Mandatory.');
			toUpdateMeterReadDate="";
			toUpdateMeterReadDateConfirm="";
			document.getElementById(ddId).focus();
			isValid=false;
			return false;
		}
		//Validation: Future Date is not Allowed !.
		if(compareDates(todaysDate,toUpdateMeterReadDate) && todaysDate!=toUpdateMeterReadDate){
			alert('Future Date is not Allowed !.');
			isValid=false;
			resetMeterReadDate();
			return false;
		}
		var prevMeterReadDateId="hidPrevMeterReadDate"+currentPage;
		var prevMeterReadDate=Trim(document.getElementById(prevMeterReadDateId).value);
		//prevMeterReadDate="05/05/2012";
		//Validation: 1. Current Meter Reading Date should always be greater than Previous Meter Reading Date.
		//Year by passed. Read from Property file as PREV_METER_READ_INGNORED_YEAR
		if(meterReadDate.length==10 && prevMeterReadDate.length==10 && prevMeterReadDate.indexOf(PREV_METER_READ_INGNORED_YEAR)==-1){
			if(compareDates(toUpdateMeterReadDate,prevMeterReadDate)){
				alert('Current Meter Reading Date should always be greater than Previous Meter Reading Date.');
				isValid=false;
				resetMeterReadDate();
				return false;
			}
		}
		if(hidFieldValue!=toUpdateMeterReadDate && toUpdateMeterReadDate.length==10 && toUpdateMeterReadDateConfirm.length!=10){
			alert('Re-enter Meter Read Date/Bill Generation Date.');
			toUpdateMeterReadDateConfirm="";
			fieldId="meterReadDDConfirm"+currentPage;
			document.getElementById(fieldId).focus();
			isValid=false;
			return false;
		}
		fieldId="hidPrevActMeterRead"+currentPage;
		var prevActMeterRead=Trim(document.getElementById(fieldId).value);
		fieldId="currentMeterRead"+currentPage;
		fieldValue=Trim(document.getElementById(fieldId).value);
		textValue=Trim(fieldValue);	
		var meterReadStatusId="meterReadStatus"+currentPage;
		var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
		textValue=textValue.length==0?"0":textValue;
		//alert(meterReadStatus+'<>'+textValue+'<>'+prevActMeterRead);
		//Validation: 2. Current Meter Reading should always be greater than Previous Meter Reading in case of Regular Read Type. 
		if((meterReadStatus=='OK' ||meterReadStatus=='PUMP') && prevActMeterRead!='' && prevActMeterRead!='NA' && textValue !='' && parseFloat(textValue)<=parseFloat(prevActMeterRead) ){
			alert('Current Meter Reading should always be greater than Previous Meter Reading in case of Regular Read Type.');
			isValid=false;
			document.getElementById(fieldId).focus();
			return false;					
		}
		hidFieldId="hidCurrentMeterRead"+currentPage;
		hidFieldValue=Trim(document.getElementById(hidFieldId).value);
		//alert(parseInt(hidFieldValue)+'<>'+toUpdateCurrentMeterRead);					
		if(hidFieldValue!=toUpdateCurrentMeterRead.toString()){				
			fieldId="currentMeterReadConfirm"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=Trim(fieldValue);				
			toUpdateCurrentMeterReadConfirm=textConfirm;			
			var confirmId="confirmCurrentMeterRead"+currentPage;
			if(textValue!='' && textConfirm==''){
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
				alert('Re-enter Current Meter Read.');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;
			}else if(textValue.length!=0 && textConfirm.length!=0 && parseFloat(textValue)!=parseFloat(textConfirm)){					
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
				alert('Re-enter Current Meter Read.');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;		
			}
			else if(textValue.length!=0 && textConfirm.length!=0){
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateCurrentMeterReadConfirm=textConfirm;
				toUpdateCurrentMeterRead=textValue;
			}			
		}
		//alert(hidFieldValue+'<21>'+toUpdateNewAvgConsumption);
		hidFieldId="hidNewAverageConsumption"+currentPage;
		hidFieldValue=Trim(document.getElementById(hidFieldId).value);
		/*if(hidFieldValue==''){
			hidFieldValue="0";
		}*/
		if(hidFieldValue!=toUpdateNewAvgConsumption.toString()){
			//alert(hidFieldValue);			
			fieldId="newAverageConsumption"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=fieldValue;	
			fieldId="newAverageConsumptionConfirm"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=fieldValue;			
			toUpdateNewAvgConsumptionConfirm=textConfirm;			
			var confirmId="confirmNewAverageConsumption"+currentPage;
			if(textValue!='' && textConfirm==''){
				//alert(hidFieldValue+'<3>'+toUpdateNewAvgConsumption);
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
				alert('Re-enter New Average Consumption .');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;
			}else if(textValue.length!=0 && textConfirm.length!=0 && parseFloat(textValue)!=parseFloat(textConfirm)){
				//alert(hidFieldValue+'<4>'+toUpdateNewAvgConsumption);							
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
				toUpdateNewAvgConsumptionConfirm="";
				alert('Re-enter New Average Consumption .');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;							
			}else if(textValue.length!=0 && textConfirm.length!=0){
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateNewAvgConsumptionConfirm=textConfirm;
				toUpdateNewAvgConsumption=textValue;
			}
		}
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		hidFieldId="hidNewNoOfFloor"+currentPage;
		hidFieldValue=Trim(document.getElementById(hidFieldId).value);			
		if(hidFieldValue!=toUpdateNewNoOfFloor.toString()){
			//alert(hidFieldValue);			
			fieldId="newNoOfFloor"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=fieldValue;	
			fieldId="newNoOfFloorConfirm"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=fieldValue;			
			toUpdateNewNoOfFloorConfirm=textConfirm;			
			var confirmId="confirmNewNoOfFloorConfirm"+currentPage;
			if(textValue!='' && textConfirm==''){
				//alert(hidFieldValue+'<3>'+toUpdateNewNoOfFloor);
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
				alert('Re-enter No Of Floors.');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;
			}else if(textValue.length!=0 && textConfirm.length!=0 && parseInt(textValue)!=parseInt(textConfirm)){
				//alert(hidFieldValue+'<4>'+toUpdateNewNoOfFloor);							
				document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
				toUpdateNewNoOfFloorConfirm="";
				alert('Re-enter No Of Floors.');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;							
			}else if(textValue.length!=0 && textConfirm.length!=0){
				document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
				toUpdateNewNoOfFloorConfirm=textConfirm;
				toUpdateNewNoOfFloor=textValue;
			}
		}
		//=========================================================
		if((toUpdateNewAvgConsumption!='' || toUpdateCurrentMeterRead!='') && toUpdateMeterReadDate.length!=10 ){
			alert('Meter Read Date/Bill Generation Date is Mandatory .');
			fieldId="meterReadDD"+currentPage;		
			isValid=false;
			document.getElementById(fieldId).focus();
			return false;
		}
		fieldId="hidConsumerType"+currentPage;
		var hidConsumerType=Trim(document.getElementById(fieldId).value);
		fieldId="consumerStatus"+currentPage;
		var consumerStatus=Trim(document.getElementById(fieldId).value);
		fieldId="meterReadStatus"+currentPage;
		var meterReadStatus=Trim(document.getElementById(fieldId).value);
		//alert('ConsumerType>>'+hidConsumerType+'>>consumerStatus>>'+consumerStatus+'>>meterReadStatus>>'+meterReadStatus+'<<>>'+(hidConsumerType=='METERED' && consumerStatus=='60' && meterReadStatus==''));
		if(toUpdateMeterReadDate.length==10 && hidConsumerType=='METERED' && consumerStatus=='60' && meterReadStatus==''){
			alert('For Metered Consumer Meter Read Status is Mandatory .');
			document.getElementById(fieldId).focus();
			isValid=false;
			return false;
		}
		var conCatId="hidConCat"+currentPage;
		var conCat=Trim(document.getElementById(conCatId).value);
		//Validation: Avg Consumption should be mandatory for No Read if consumer is not domestic. Otherwise system will not esimate correctly and Bill will generate in Pending state. 
		//As per Ayan's requirement
		//Commenter after Discussion with Rajesh Da
		/*if(conCat!='CAT I' && !(meterReadStatus=='OK' ||meterReadStatus=='PUMP'||meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM') && toUpdateNewAvgConsumption==''){
			alert('For Non domestic Consumer and No Read, New Average Consumption is Mandatory .');
			fieldId="newAverageConsumption"+currentPage;
			document.getElementById(fieldId).focus();
			isValid=false;
			return false;
		}*/
		
		return true;
	}
	function clearMRMessage(){
		if(document.getElementById('mrMessage')){
			document.getElementById('mrMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		}
	}
      
 
  function onSearchAjax(){
    	   if(Trim(document.getElementById('kno').value)!='')
       	   {
    		   var url = "knoBillGenAjax.action?hidAction=populateMRDDetails&kno="+Trim(document.getElementById('kno').value);
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
				
	       		httpBowserObject.onreadystatechange = function() 
				{
					
				if (httpBowserObject.readyState == 4 && httpBowserObject.status==200) 
				{

					var responseString = httpBowserObject.responseText;
					
					if(null == responseString || responseString.indexOf("ERROR:") > -1)
					{
						document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";

     				}

					else if(responseString.indexOf("ERRORINVALIDKNO:") > -1)
					{
						resetAllWhileSearchNotfound();
						
						//var BillRound = responseString.substring(responseString.indexOf("<BILLROUNDID>") + 13, responseString.indexOf("</BILLROUNDID>"));
						//var Kno =responseString.substring(responseString.indexOf("<KNO>") + 5, responseString.indexOf("</KNO>"));
                         var billID = '';
                         var recordStatusAndErrorDesc = '';
                         var consPreBillProcStatus = '';
                         var area = '';
                          var zone = '';
                       var seqNo = '';
                       var svcCycRtSeq = '';
                       var BillRound = '';
                       var Kno = '';
                       var consumerName = '';
                       var consumerWcNo = '';
                       var consumerCat = '';
                       var consumerServiceType = '';
                       var consumerLastBillGenerationDate = '';
                       var prevMeterReadDate = '';
                       var prevMeterStatus = '';
                       var prevRegRead = '';
                       var consumerStatus = '';
                       var currMeterReadDate = '';
                       var currMeterStatus = '';
                       var currMeterRegRead = '';
                       var consumerCurrentAvgConsumption = '';
                       var currAvgRead = '';
                       var newNoOfFloor = '';
                       var consumerServiceType = '';
                       var prevActMeterReadDate = '';
                       var prevActRegRead = '';
                       var prevNoOfFloor = '';
                       var remarks  = '';
						
						popupMRDEntryScreen(billID,recordStatusAndErrorDesc,consPreBillProcStatus,area,zone,seqNo,svcCycRtSeq,BillRound,Kno,consumerName,consumerWcNo,consumerCat,consumerServiceType,
								consumerLastBillGenerationDate,prevMeterReadDate,prevMeterStatus,prevRegRead,consumerStatus,
								currMeterReadDate,currMeterStatus,currMeterRegRead,consumerCurrentAvgConsumption,currAvgRead,newNoOfFloor,consumerServiceType,prevActMeterReadDate,prevActRegRead,prevNoOfFloor,remarks);
				
						/*Start 23-11-2016: Modified By Madhuri as per Jtrac DJB-4604 */     
						document.getElementById('ajax-result').innerHTML = "<font color='red' size='2'><b>"+errormsg+"</b></font>";
						
						/*End 23-11-2016: Modified By Madhuri as per Jtrac DJB-4604 */  
     				}


     				
					else
					{
						
						var seqNo=1; 
                        
                       var svcCycRtSeq =responseString.substring(responseString.indexOf("<SERVCYCSEQ>") + 12, responseString.indexOf("</SERVCYCSEQ>"));

				        var BillRound = responseString.substring(responseString.indexOf("<BILLROUNDID>") + 13, responseString.indexOf("</BILLROUNDID>"));
				        

					       var Kno =responseString.substring(responseString.indexOf("<KNO>") + 5, responseString.indexOf("</KNO>"));

						       var area =responseString.substring(responseString.indexOf("<AREA>") + 6, responseString.indexOf("</AREA>"));
						       var zone =responseString.substring(responseString.indexOf("<ZONE>") + 6, responseString.indexOf("</ZONE>")); 
                               var consPreBillProcStatus=responseString.substring(responseString.indexOf("<CONSPREBILLSTATID>") + 19, responseString.indexOf("</CONSPREBILLSTATID>")); 
                               var recordStatusAndErrorDesc=responseString.substring(responseString.indexOf("<RECSTATSERRORDESC>") + 19, responseString.indexOf("</RECSTATSERRORDESC>")); 
                               var billID= responseString.substring(responseString.indexOf("<BILLID>") + 8, responseString.indexOf("</BILLID>"));

                               var consumerName=responseString.substring(responseString.indexOf("<NAME>") + 6, responseString.indexOf("</NAME>"));
					  var consumerWcNo =responseString.substring(responseString.indexOf("<WCNO>") + 6, responseString.indexOf("</WCNO>"));
					   var consumerCat =responseString.substring(responseString.indexOf("<CATEGORY>") + 10, responseString.indexOf("</CATEGORY>"));
                var consumerServiceType=responseString.substring(responseString.indexOf("<SERVICETYPE>") + 13, responseString.indexOf("</SERVICETYPE>"));
	var consumerLastBillGenerationDate =responseString.substring(responseString.indexOf("<LASTBILLGENERATIONDATE>") + 24, responseString.indexOf("</LASTBILLGENERATIONDATE>"));
				  var prevMeterReadDate=responseString.substring(responseString.indexOf("<PREVMETERREADDATE>") + 19, responseString.indexOf("</PREVMETERREADDATE>"));
                   var prevMeterStatus =responseString.substring(responseString.indexOf("<PREVMETERREADSTATUS>") + 21, responseString.indexOf("</PREVMETERREADSTATUS>"));
					   var prevRegRead =responseString.substring(responseString.indexOf("<PREVMETERREGREAD>") + 18, responseString.indexOf("</PREVMETERREGREAD>"));
					var consumerStatus =responseString.substring(responseString.indexOf("<STATUS>") + 8, responseString.indexOf("</STATUS>"));
                 var currMeterReadDate =responseString.substring(responseString.indexOf("<CURRMETERREADDATE>") + 19, responseString.indexOf("</CURRMETERREADDATE>"));
				   var currMeterStatus =responseString.substring(responseString.indexOf("<CURRMETERREADSTATUS>") + 21, responseString.indexOf("</CURRMETERREADSTATUS>"));
				  var currMeterRegRead =responseString.substring(responseString.indexOf("<CURRMETERREGREAD>") + 18, responseString.indexOf("</CURRMETERREGREAD>"));
      var consumerCurrentAvgConsumption=responseString.substring(responseString.indexOf("<CURAVGCONSUMPTION>") + 19, responseString.indexOf("</CURAVGCONSUMPTION>"));
					   var currAvgRead =responseString.substring(responseString.indexOf("<CURAVGREAD>") + 12, responseString.indexOf("</CURAVGREAD>"));
					  var newNoOfFloor =responseString.substring(responseString.indexOf("<CURRMETERREADFLOOR>") + 20, responseString.indexOf("</CURRMETERREADFLOOR>"));
               var consumerServiceType =responseString.substring(responseString.indexOf("<CONSUMERSERVICETYPE>") + 21, responseString.indexOf("</CONSUMERSERVICETYPE>"));
			  var prevActMeterReadDate =responseString.substring(responseString.indexOf("<PREVACTUALMETERREADDATE>") + 25, responseString.indexOf("</PREVACTUALMETERREADDATE>"));
					var prevActRegRead =responseString.substring(responseString.indexOf("<PREVACTUALMETERREAD>") + 21, responseString.indexOf("</PREVACTUALMETERREAD>"));
                    var prevNoOfFloor = responseString.substring(responseString.indexOf("<PREVMETERNOFLOOR>") + 18, responseString.indexOf("</PREVMETERNOFLOOR>"));
						  var remarks = responseString.substring(responseString.indexOf("<REMARK>") + 8, responseString.indexOf("</REMARK>"));

						
						
						popupMRDEntryScreen(billID,recordStatusAndErrorDesc,consPreBillProcStatus,area,zone,seqNo,svcCycRtSeq,BillRound,Kno,consumerName,consumerWcNo,consumerCat,consumerServiceType,
								consumerLastBillGenerationDate,prevMeterReadDate,prevMeterStatus,prevRegRead,consumerStatus,
								currMeterReadDate,currMeterStatus,currMeterRegRead,consumerCurrentAvgConsumption,currAvgRead,newNoOfFloor,consumerServiceType,prevActMeterReadDate,prevActRegRead,prevNoOfFloor,remarks);
						
						
					}
				 }
			};
			httpBowserObject.send(null);		

        	}
            }
        }

   /****************************************************************************/




         </script>
        <script type="text/javascript"> 
	   
				
		function popupMRDEntryScreen(billID,recordStatusAndErrorDesc,consPreBillProcStatus,area,zone,seqNo,svcCycRtSeq,BillRound,Kno,consumerName,consumerWcNo,consumerCat,consumerServiceType,
				consumerLastBillGenerationDate,prevMeterReadDate,prevMeterStatus,prevRegRead,consumerStatus,
				currMeterReadDate,currMeterStatus,currMeterRegRead,consumerCurrentAvgConsumption,currAvgRead,newNoOfFloor,consumerServiceType,prevActMeterReadDate,prevActRegRead,prevNoOfFloor,remarks)
		{

			 
        


			if(consPreBillProcStatus >= 65  )
			{

				if(null!=document.getElementById('mrdebox') && 'null'!=document.getElementById('mrdebox')){
					

					var thediv=document.getElementById('mrdebox');
					
					thediv.style.display = "block";
					//thediv=document.getElementById('pagination');
					//thediv.style.display = "none";
					/*alert(seqNo+'<>'+BillRound+'<>'+Kno+'<>'+consumerName+'<>'+consumerWcNo+'<>'+consumerCat+'<>'+consumerServiceType+'<>'+
							consumerLastBillGenerationDate+'<>'+prevMeterReadDate+'<>'+prevMeterStatus+'<>'+prevRegRead+'<>'+consumerStatus+'<>'+
							currMeterReadDate+'<>'+currMeterStatus+'<>'+currMeterRegRead+
							'<>'+consumerCurrentAvgConsumption+'<>'+currAvgRead+'<>'+newNoOfFloor+'<>'+consumerServiceType+'<>'+prevNoOfFloor);*/
					seqNo=Trim(seqNo);
					trId=parseInt(seqNo)-1;
					trId="tr"+trId;
					var x=document.getElementById(trId);
					//alert('inside popupMRDEntryScreen............................................'+x);
					//alert(trId+'<>'+x);
					if ( x) {

						//alert('inside popupMRDEntryScreen............................................'+x);
						var tds = x.getElementsByTagName("td"); 					
						consumerStatus=tds[5].innerHTML;
						consumerStatus=consumerStatus.replace(/&nbsp;/g,"");	
						consumerStatus=Trim(consumerStatus);
						//alert(consumerStatus);
						if(consumerStatus=='Change of Category'){
							consumerStatus="50";
						}else if(consumerStatus=='Disconnected'){
							consumerStatus="20";
						}else if(consumerStatus=='Meter Installed'){
							consumerStatus="30";
						}else if(consumerStatus=='Meter Not Installed'){
							consumerStatus="90";
						}
						else if(consumerStatus=='Miscellaneous'){
							consumerStatus="0";
						}else if(consumerStatus=='Reopening'){
							consumerStatus="40";
						}else if(consumerStatus=='Invalid Data'){
							consumerStatus="15";
						}
						else{
							consumerStatus="60";
						}
						var readDate=Trim(tds[13].innerHTML);
						readDate=readDate.replace(/&nbsp;/g,"");	
						readDate=Trim(readDate);									
						//alert(readDate);
						 if(readDate.length==10){ 
							 currMeterReadDate=readDate; 
						 }else{
							 currMeterReadDate=""; 
						 }
						 currMeterStatus=tds[14].innerHTML;
						 currMeterStatus=currMeterStatus.replace(/&nbsp;/g,"");	
						 currMeterStatus=Trim(currMeterStatus);
						 //alert(currMeterStatus);
						 currMeterRegRead=tds[15].innerHTML;
						 currMeterRegRead=currMeterRegRead.replace(/&nbsp;/g,"");	
						 currMeterRegRead=Trim(currMeterRegRead);					 
						 //alert(currMeterRegRead);
						 currAvgRead=tds[16].innerHTML;
						 currAvgRead=currAvgRead.replace(/&nbsp;/g,"");	
						 currAvgRead=Trim(currAvgRead);
						 //alert(currAvgRead);
						 newNoOfFloor=tds[17].innerHTML;
						 newNoOfFloor=newNoOfFloor.replace(/&nbsp;/g,"");	
						 newNoOfFloor=Trim(newNoOfFloor);
						//alert(newNoOfFloor);
					}
					document.getElementById('seqNo1').value=parseInt(seqNo)-1;
					//document.getElementById('seqNO1').value=parseInt(seqNo)-1;
					//document.getElementById('pageSpan').innerHTML=Trim(seqNo);
					document.getElementById('billRound1').value=Trim(BillRound);
					document.getElementById('kno1').value=Trim(Kno);
					document.getElementById('hidConStatus1').value=consumerStatus;
					document.getElementById('hidMeterReadDate1').value=currMeterReadDate;
					document.getElementById('hidMeterStatus1').value=currMeterStatus;
					document.getElementById('hidPrevMeterStatus1').value=prevMeterStatus;
					document.getElementById('hidCurrentMeterRead1').value=currMeterRegRead;
					document.getElementById('hidNewAverageConsumption1').value=currAvgRead;
					document.getElementById('hidConsumerType1').value=Trim(consumerServiceType);
					//prevMeterReadDate="05/05/2012";
					document.getElementById('hidConCat1').value=Trim(consumerCat);
					document.getElementById('hidPrevMeterReadDate1').value=Trim(prevMeterReadDate);
					document.getElementById('hidPrevMeterRead1').value=Trim(prevActRegRead);

					document.getElementById('hidPrevActMeterReadDate1').value=Trim(prevActMeterReadDate);
					document.getElementById('hidPrevActMeterRead1').value=Trim(prevActRegRead);


		            document.getElementById('consumerName').innerHTML=Trim(consumerName);

	               /***********************************************************************************************/
		            document.getElementById('customerBillRound').innerHTML=Trim(BillRound);
		            document.getElementById('customerkno').innerHTML=Trim(Kno);
		            document.getElementById('customerArea').innerHTML=Trim(area);
		            document.getElementById('customerZone').innerHTML=Trim(zone);

		            document.getElementById('customerStatus').innerHTML=Trim(recordStatusAndErrorDesc);
                  
		            document.getElementById('customerBillID').innerHTML=Trim(billID);
		           

	              /**************************************************************************************************/
					
					document.getElementById('consPreBillProcStatus1').value=Trim(consPreBillProcStatus);
					
					document.getElementById('Kno1').innerHTML=Trim(Kno);
					document.getElementById('consumerWcNo').innerHTML=Trim(consumerWcNo);
					document.getElementById('consumerCat').innerHTML=Trim(consumerCat);
					document.getElementById('consumerServiceType').innerHTML=Trim(consumerServiceType);
					document.getElementById('consumerLastBillGenerationDate').innerHTML=Trim(consumerLastBillGenerationDate);




					document.getElementById('prevMeterReadDate').innerHTML=Trim(prevMeterReadDate);
					document.getElementById('prevMeterStatus').innerHTML=Trim(prevMeterStatus);
					document.getElementById('prevRegRead').innerHTML=Trim(prevRegRead);

					document.getElementById('prevActMeterReadDate').innerHTML=Trim(prevActMeterReadDate);
					document.getElementById('prevActRegRead').innerHTML=Trim(prevActRegRead);
					document.getElementById('prevNoOfFloor').innerHTML=Trim(prevNoOfFloor);

					document.getElementById('consumerStatus1').value=consumerStatus;
					//====================Added for Remarks on 16-11-2012 by Tuhin 
					if(consumerStatus=='15'){
						var fieldId_remarks="remarks"+currentPage;
						//document.getElementById(fieldId_remarks).value="";
						fieldId_remarks="remarksDiv1"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'block';	
						fieldId_remarks="remarksDiv2"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'block';
					}else{
						var fieldId_remarks="remarks"+currentPage;
						document.getElementById(fieldId_remarks).value="";
						fieldId_remarks="remarksDiv1"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'none';
						fieldId_remarks="remarksDiv2"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'none';
					}
					//========================================================
					if(currMeterReadDate.length==10){					
						document.getElementById('meterReadDD1').value=currMeterReadDate.substr(0,2);
						document.getElementById('meterReadMM1').value=currMeterReadDate.substr(3,2);
						document.getElementById('meterReadYYYY1').value=currMeterReadDate.substr(6,4);
						document.getElementById('meterReadYYYYConfirm1').value=currMeterReadDate.substr(6,4);
					}else{
						document.getElementById('meterReadDD1').value="";
						document.getElementById('meterReadMM1').value="";
						document.getElementById('meterReadYYYY1').value=todaysDate.substr(6,4);
						document.getElementById('meterReadYYYYConfirm1').value=todaysDate.substr(6,4);
					}
	               
					//consPreBillProcStatus
					 
				/*	if(consPreBillProcStatus < 65 )
					{
					alert('CONSPREBILLPROCSTAT::::>>>>>>>>+'+consPreBillProcStatus+'LL'+currMeterRegRead);	
	                document.getElementById('currentMeterRead1').value=currMeterRegRead;
	                document.getElementById('currentMeterRead1').disable=true;
	                //alert(document.getElementById('currentMeterRead1').disable);
	                
					}
					else
					{
						document.getElementById('currentMeterRead1').value=currMeterRegRead;
						document.getElementById('currentMeterRead1').disable=false;
					}*/
					document.getElementById('currentMeterRead1').value=currMeterRegRead;
					document.getElementById('meterReadStatus1').value=currMeterStatus;  
					document.getElementById('currentMeterRead1').value=currMeterRegRead;
					document.getElementById('currentAverageConsumption1').value=Trim(consumerCurrentAvgConsumption);
					document.getElementById('newAverageConsumption1').value=currAvgRead;
					document.getElementById('newNoOfFloor1').value=newNoOfFloor;
					document.getElementById('remarks1').value=remarks;
					
					document.getElementById('meterReadDDConfirm1').value="";
					document.getElementById('meterReadMMConfirm1').value="";
					document.getElementById('currentMeterReadConfirm1').value="";
					document.getElementById('newAverageConsumptionConfirm1').value="";
					document.getElementById('confirmDate1').innerHTML="";
					document.getElementById('confirmCurrentMeterRead1').innerHTML="";
					document.getElementById('confirmNewAverageConsumption1').innerHTML="";
					document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";				
					document.getElementById('svcCycRtSeq').innerHTML="<b>"+svcCycRtSeq+"</b>";
					
					popedUp=true;	
					setOnLoadValue();

						
					document.getElementById('meterReadDD1').disabled=true;
					document.getElementById('meterReadMM1').disabled=true;
					document.getElementById('meterReadDDConfirm1').disabled=true;
					document.getElementById('meterReadMMConfirm1').disabled=true;

					document.getElementById('meterReadStatus1').disabled=true;

					document.getElementById('meterReadDD1').disabled=true;

					document.getElementById('consumerStatus1').disabled=true;
					
					document.getElementById('newNoOfFloor1').disabled=true;
                   
					document.getElementById('currentMeterRead1').disabled=true;

					//alert(">>>>>>>>>>>>>>>>>>>>>>>>"+document.getElementById('currentMeterRead1').disabled);
                    //document.getElementById('currentMeterRead1').blur();
					//document.getElementById('currentMeterRead1').disable=true;

					document.getElementById('currentMeterReadConfirm1').disabled=true;	
					document.getElementById('newAverageConsumption1').disabled=true;
					document.getElementById('newAverageConsumptionConfirm1').disabled=true;
					document.getElementById('meterReadYYYY1').disabled=true; 
					
					document.getElementById('newAverageConsumptionConfirm1').disabled=true; 
					document.getElementById('newNoOfFloorConfirm1').disabled=true; 
					document.getElementById('currentMeterReadConfirm1').disabled=true; 
					document.getElementById('meterReadDDConfirm1').disabled=true; 
					document.getElementById('meterReadMMConfirm1').disabled=true; 
					document.getElementById('meterReadYYYYConfirm1').disabled=true;			

					document.getElementById('freezeAndGenBill').disabled=true;
					document.getElementById('btnPrev').disabled=true;
					document.getElementById('btnSave').disabled=true;

				}
					

		    }
			else 
			{


				// alert('>>>>>>>>>>>Mrityunjoy'+recordStatusAndErrorDesc);
                //alert("This line");

				if(null!=document.getElementById('mrdebox') && 'null'!=document.getElementById('mrdebox')){
				

				var thediv=document.getElementById('mrdebox');
				
				thediv.style.display = "block";
				//thediv=document.getElementById('pagination');
				//thediv.style.display = "none";
				/*alert(seqNo+'<>'+BillRound+'<>'+Kno+'<>'+consumerName+'<>'+consumerWcNo+'<>'+consumerCat+'<>'+consumerServiceType+'<>'+
						consumerLastBillGenerationDate+'<>'+prevMeterReadDate+'<>'+prevMeterStatus+'<>'+prevRegRead+'<>'+consumerStatus+'<>'+
						currMeterReadDate+'<>'+currMeterStatus+'<>'+currMeterRegRead+
						'<>'+consumerCurrentAvgConsumption+'<>'+currAvgRead+'<>'+newNoOfFloor+'<>'+consumerServiceType+'<>'+prevNoOfFloor);*/
				seqNo=Trim(seqNo);
				trId=parseInt(seqNo)-1;
				trId="tr"+trId;
				var x=document.getElementById(trId);
				//alert('inside popupMRDEntryScreen............................................'+x);
				//alert(trId+'<>'+x);
				if ( x) {

					//alert('inside popupMRDEntryScreen............................................'+x);
					var tds = x.getElementsByTagName("td"); 					
					consumerStatus=tds[5].innerHTML;
					consumerStatus=consumerStatus.replace(/&nbsp;/g,"");	
					consumerStatus=Trim(consumerStatus);
					//alert(consumerStatus);
					if(consumerStatus=='Change of Category'){
						consumerStatus="50";
					}else if(consumerStatus=='Disconnected'){
						consumerStatus="20";
					}else if(consumerStatus=='Meter Installed'){
						consumerStatus="30";
					}else if(consumerStatus=='Meter Not Installed'){
						consumerStatus="90";
					}
					else if(consumerStatus=='Miscellaneous'){
						consumerStatus="0";
					}else if(consumerStatus=='Reopening'){
						consumerStatus="40";
					}else if(consumerStatus=='Invalid Data'){
						consumerStatus="15";
					}
					else{
						consumerStatus="60";
					}
					var readDate=Trim(tds[13].innerHTML);
					readDate=readDate.replace(/&nbsp;/g,"");	
					readDate=Trim(readDate);									
					//alert(readDate);
					 if(readDate.length==10){ 
						 currMeterReadDate=readDate; 
					 }else{
						 currMeterReadDate=""; 
					 }
					 currMeterStatus=tds[14].innerHTML;
					 currMeterStatus=currMeterStatus.replace(/&nbsp;/g,"");	
					 currMeterStatus=Trim(currMeterStatus);
					 //alert(currMeterStatus);
					 currMeterRegRead=tds[15].innerHTML;
					 currMeterRegRead=currMeterRegRead.replace(/&nbsp;/g,"");	
					 currMeterRegRead=Trim(currMeterRegRead);					 
					 //alert(currMeterRegRead);
					 currAvgRead=tds[16].innerHTML;
					 currAvgRead=currAvgRead.replace(/&nbsp;/g,"");	
					 currAvgRead=Trim(currAvgRead);
					 //alert(currAvgRead);
					 newNoOfFloor=tds[17].innerHTML;
					 newNoOfFloor=newNoOfFloor.replace(/&nbsp;/g,"");	
					 newNoOfFloor=Trim(newNoOfFloor);
					//alert(newNoOfFloor);
				}
				document.getElementById('seqNo1').value=parseInt(seqNo)-1;
				//document.getElementById('seqNO1').value=parseInt(seqNo)-1;
				//document.getElementById('pageSpan').innerHTML=Trim(seqNo);
				document.getElementById('billRound1').value=Trim(BillRound);
				document.getElementById('kno1').value=Trim(Kno);
				document.getElementById('hidConStatus1').value=consumerStatus;
				document.getElementById('hidMeterReadDate1').value=currMeterReadDate;
				document.getElementById('hidMeterStatus1').value=currMeterStatus;
				document.getElementById('hidPrevMeterStatus1').value=prevMeterStatus;
				document.getElementById('hidCurrentMeterRead1').value=currMeterRegRead;
				document.getElementById('hidNewAverageConsumption1').value=currAvgRead;
				document.getElementById('hidConsumerType1').value=Trim(consumerServiceType);
				//prevMeterReadDate="05/05/2012";
				document.getElementById('hidConCat1').value=Trim(consumerCat);
				document.getElementById('hidPrevMeterReadDate1').value=Trim(prevMeterReadDate);
				document.getElementById('hidPrevMeterRead1').value=Trim(prevActRegRead);

				document.getElementById('hidPrevActMeterReadDate1').value=Trim(prevActMeterReadDate);
				document.getElementById('hidPrevActMeterRead1').value=Trim(prevActRegRead);


	            document.getElementById('consumerName').innerHTML=Trim(consumerName);



	            document.getElementById('customerStatus').innerHTML=Trim(recordStatusAndErrorDesc);
	            document.getElementById('customerBillID').innerHTML=Trim(billID);
	            document.getElementById('customerBillRound').innerHTML=Trim(BillRound);

               /***********************************************************************************************/
	            
            	   
	            document.getElementById('customerkno').innerHTML=Trim(Kno);
	            document.getElementById('customerArea').innerHTML=Trim(area);
	            document.getElementById('customerZone').innerHTML=Trim(zone);

              /**************************************************************************************************/
				
				document.getElementById('consPreBillProcStatus1').value=Trim(consPreBillProcStatus);
				
				document.getElementById('Kno1').innerHTML=Trim(Kno);
				document.getElementById('consumerWcNo').innerHTML=Trim(consumerWcNo);
				document.getElementById('consumerCat').innerHTML=Trim(consumerCat);
				document.getElementById('consumerServiceType').innerHTML=Trim(consumerServiceType);
				document.getElementById('consumerLastBillGenerationDate').innerHTML=Trim(consumerLastBillGenerationDate);

				document.getElementById('prevMeterReadDate').innerHTML=Trim(prevMeterReadDate);
				document.getElementById('prevMeterStatus').innerHTML=Trim(prevMeterStatus);
				document.getElementById('prevRegRead').innerHTML=Trim(prevRegRead);

				document.getElementById('prevActMeterReadDate').innerHTML=Trim(prevActMeterReadDate);
				document.getElementById('prevActRegRead').innerHTML=Trim(prevActRegRead);
				document.getElementById('prevNoOfFloor').innerHTML=Trim(prevNoOfFloor);

				document.getElementById('consumerStatus1').value=consumerStatus;
				//====================Added for Remarks on 16-11-2012 by Tuhin 
				if(consumerStatus=='15'){
					var fieldId_remarks="remarks"+currentPage;
					//document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';	
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'block';
				}else{
					var fieldId_remarks="remarks"+currentPage;
					document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
				}
				//========================================================
				if(currMeterReadDate.length==10){					
					document.getElementById('meterReadDD1').value=currMeterReadDate.substr(0,2);
					document.getElementById('meterReadMM1').value=currMeterReadDate.substr(3,2);
					document.getElementById('meterReadYYYY1').value=currMeterReadDate.substr(6,4);
					document.getElementById('meterReadYYYYConfirm1').value=currMeterReadDate.substr(6,4);
				}else{
					document.getElementById('meterReadDD1').value="";
					document.getElementById('meterReadMM1').value="";
					document.getElementById('meterReadYYYY1').value=todaysDate.substr(6,4);
					document.getElementById('meterReadYYYYConfirm1').value=todaysDate.substr(6,4);
				}
               
				//consPreBillProcStatus
				 
				if(consPreBillProcStatus < 65 )
				{
				//alert('CONSPREBILLPROCSTAT::::>>>>>>>>+'+consPreBillProcStatus+'LL'+currMeterRegRead);	
                document.getElementById('currentMeterRead1').value=currMeterRegRead;
                document.getElementById('currentMeterRead1').disable=true;
                //alert(document.getElementById('currentMeterRead1').disable);
                
				}
				else
				{
					document.getElementById('currentMeterRead1').value=currMeterRegRead;
					document.getElementById('currentMeterRead1').disable=false;
				}

				document.getElementById('meterReadStatus1').value=currMeterStatus;  
				document.getElementById('currentMeterRead1').value=currMeterRegRead;
				document.getElementById('currentAverageConsumption1').value=Trim(consumerCurrentAvgConsumption);
				document.getElementById('newAverageConsumption1').value=currAvgRead;
				document.getElementById('newNoOfFloor1').value=newNoOfFloor;
				document.getElementById('remarks1').value=remarks;
				
				document.getElementById('meterReadDDConfirm1').value="";
				document.getElementById('meterReadMMConfirm1').value="";
				document.getElementById('currentMeterReadConfirm1').value="";
				document.getElementById('newAverageConsumptionConfirm1').value="";
				document.getElementById('confirmDate1').innerHTML="";
				document.getElementById('confirmCurrentMeterRead1').innerHTML="";
				document.getElementById('confirmNewAverageConsumption1').innerHTML="";
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";				
				document.getElementById('svcCycRtSeq').innerHTML="<b>"+svcCycRtSeq+"</b>";
				
				document.getElementById('meterReadDD1').disabled=false;
				document.getElementById('meterReadMM1').disabled=false;
				document.getElementById('meterReadDDConfirm1').disabled=false;
				document.getElementById('meterReadMMConfirm1').disabled=false;
				document.getElementById('meterReadStatus1').disabled=false;

				
				document.getElementById('currentMeterRead1').disabled=false;

				document.getElementById('currentMeterReadConfirm1').disabled=false;	
				document.getElementById('newAverageConsumption1').disabled=false;
				document.getElementById('newAverageConsumptionConfirm1').disabled=false;

                if(consumerStatus==60 || consumerStatus ==10)
                {
				document.getElementById('freezeAndGenBill').disabled=false;
				document.getElementById('btnPrev').disabled=false;
				document.getElementById('btnSave').disabled=false;
                }
                else
                {
                	document.getElementById('freezeAndGenBill').disabled=true;
    				document.getElementById('btnPrev').disabled=true;
    				document.getElementById('btnSave').disabled=true;
                 }
				popedUp=true;	
				setOnLoadValue();

               

						
			}
		  }

			
		  
			//}
		}
		function hideMRDEntryScreen(){
			if(null!=document.getElementById('mrdebox') && 'null'!=document.getElementById('mrdebox')){
				var thediv=document.getElementById('mrdebox');		
				thediv.style.display = "none";
				if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
					var theTable=document.getElementById('djbmaintable');				
					theTable.className ="djbmain";
				}
				thediv=document.getElementById('pagination');
				thediv.style.display = "block";
				var newColor = '#009933';
				var x=document.getElementById(trId);
				var msg=document.getElementById('ajax-result').innerHTML;
				
				if(msg.indexOf(") Saved Successfully")>-1){
					msg="OK";
				}
				if(msg.indexOf(">Reset Successfully")>-1){
					msg="RESET";
				}
				//alert(trId+'<>'+x+'<>'+msg);
				if ( x.style && msg=='OK') {
					
					var tds = x.getElementsByTagName("td");
					if(toUpdateConsumerStatus=='50'){
						tds[5].innerHTML="Change of Category";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='15'){
						tds[5].innerHTML="Invalid Data";
						newColor = '#FFFF66';
						x.title="Cann't Freeze Records Reason:\n"+document.getElementById('remarks1').value;
					}else if(toUpdateConsumerStatus=='20'){
						tds[5].innerHTML="Disconnected";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='30'){
						tds[5].innerHTML="Meter Installed";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Change of Category Status";
					}else if(toUpdateConsumerStatus=='90'){
						tds[5].innerHTML="Meter Not Installed";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Meter Not Installed Status";
					}
					else if(toUpdateConsumerStatus=='40'){
						tds[5].innerHTML="Reopening";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Reopening Status";
					}else if(toUpdateConsumerStatus=='0'){
						tds[5].innerHTML="Miscellaneous";
						newColor = '#AAAAC6';
						x.title="Cann't Freeze Records as in Error in Physical MRD Status";
					}else{
						//alert('TESTTEST');
						tds[5].innerHTML="Regular";
						x.title="Ready To Freeze";
					}
					if(toUpdateMeterReadDate.length==10){ 
					 	tds[13].innerHTML=toUpdateMeterReadDate; 
					}else{
						 tds[13].innerHTML="&nbsp;"; 
					}
					if(Trim(toUpdateMeterReadStatus).length!=0){
						tds[14].innerHTML=toUpdateMeterReadStatus;
					}else{
						tds[14].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateCurrentMeterRead).length!=0){
						tds[15].innerHTML=toUpdateCurrentMeterRead;
					}else{
						tds[15].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewAvgConsumption).length!=0){
						tds[16].innerHTML=toUpdateNewAvgConsumption;
					}else{
						tds[16].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewNoOfFloor).length!=0){
						tds[17].innerHTML=toUpdateNewNoOfFloor;
					}else{
						tds[17].innerHTML="&nbsp;";
					}
					x.style.backgroundColor = newColor;
					
				}
				if ( x.style && msg=='RESET') {
					newColor = '#AAAAC6';					
					var tds = x.getElementsByTagName("td");					
					tds[5].innerHTML="Regular";
					x.title="Cann't Freeze Records as No Records Entered Status";
					
					if(toUpdateMeterReadDate.length==10){ 
					 	tds[13].innerHTML=toUpdateMeterReadDate; 
					}else{
						 tds[13].innerHTML="&nbsp;"; 
					}
					if(Trim(toUpdateMeterReadStatus).length!=0){
						tds[14].innerHTML=toUpdateMeterReadStatus;
					}else{
						tds[14].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateCurrentMeterRead).length!=0){
						tds[15].innerHTML=toUpdateCurrentMeterRead;
					}else{
						tds[15].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewAvgConsumption).length!=0){
						tds[16].innerHTML=toUpdateNewAvgConsumption;
					}else{
						tds[16].innerHTML="&nbsp;";
					}
					if(Trim(toUpdateNewNoOfFloor).length!=0){
						tds[17].innerHTML=toUpdateNewNoOfFloor;
					}else{
						tds[17].innerHTML="&nbsp;";
					}
					x.style.backgroundColor = newColor;
					
				}
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
				msg="";
				popedUp=false;	
				clearToUpdateVar();			
			}
			
		}
		function fnCancel(){
			
			hideMRDEntryScreen();
		}
		function fnSaveDetails()
		{
            
	     // // if(!(document.getElementById('kno').value == null||document.getElementById('kno').value ==''))
          // {
			//document.getElementById('btnSave').disabled=true;
			//document.getElementById('btnPrev').disabled=true;
			//document.getElementById('btnFreeze').disabled=true;
			//document.getElementById('freezeAndGenBill').disabled=true;

			//fnUpdateLastRecord(currentPage);
           // }
		}

         function fnFreezeDeatils()
         {
           
        	 if(!(document.getElementById('kno').value == null||document.getElementById('kno').value ==''))
             {
 			document.getElementById('btnSave').disabled=true;
 			document.getElementById('btnPrev').disabled=true;
 			document.getElementById('btnFreeze').disabled=true;
 			document.getElementById('freezeAndGenBill').disabled=true;
         	 fnUpdateLastRecordforFreeze(currentPage);
             }
         }

         function freezeandGenerateBill()
         {
                   // alert('inside freezeandGenerateBill');
                   
            if(!(document.getElementById('kno').value == null||document.getElementById('kno').value ==''))
            {
            /*Start 12-09-2017: Modified By Suraj Tiwari as per Jtrac DJB-4735 */
            scroll(0,0);
         	hideScreen();
         	/*End 12-09-2017: Modified By Suraj Tiwari as per Jtrac DJB-4735 */
			document.getElementById('btnSave').disabled=true;
			document.getElementById('btnPrev').disabled=true;
			//document.getElementById('btnFreeze').disabled=true;
			document.getElementById('freezeAndGenBill').disabled=true;
            fnUpdateLastRecordforFreezeAndGenerateBill(currentPage);
            }
         }


		
		function fnResetDetails()
		{
			
		}
		
						        
        
		

        //-->
    </script> 
 	<script type="text/javascript">
 		 <%MRDReadTypeLookup mrdReadTypeLookup= (MRDReadTypeLookup)session.getAttribute("mrdReadTypeLookup");
		if(null!=mrdReadTypeLookup){%>		
			var averageReadType="<%=mrdReadTypeLookup.getAverageReadType()%>";
			var noBillingReadType="<%=mrdReadTypeLookup.getNoBillingReadType()%>";
			var provisionalReadType="<%=mrdReadTypeLookup.getProvisionalReadType()%>";
			var regularReadType="<%=mrdReadTypeLookup.getRegularReadType()%>";
		<%}%>
       
		var todaysDate="<%=(String)session.getAttribute("TodaysDate")%>";
		var PREV_METER_READ_INGNORED_YEAR="<%=PropertyUtil.getProperty("PREV_METER_READ_INGNORED_YEAR")%>";			
		var meterReadDDText="";
		var meterReadMMText="";		
		var currentMeterReadText="";		
		var newAvgConsumptionText="";
		var newNoOfFloorText="";
 	</script>
 	<script type="text/javascript">
 		function fnOnSubmit(){
 			var freezeFlag="Y";
 			scroll(0,0);
 			hideScreen();
 			document.body.style.overflow = 'hidden';
 			var answer = confirm("Are You Sure You want to Freeze the MRD.");
 			if (answer){
 				document.getElementById('btnFreeze').disabled=true;
 				//doAjaxCall('','','','','','','','',freezeFlag);
 				document.forms[1].freezeData.value="Y";
				document.forms[1].action="freeze.action";
				document.forms[1].submit();
 			}
 			else{
 				showScreen();
 				document.body.style.overflow = 'auto';
 				return false;
 			}
 			
 		}
 		function fnOnLoad(){ 
 			var toUpdateConsumerStatus='60';		 		
 		}		
 		currentPage=1;	
 		var trId;
 		var popedUp=false;
 		document.onkeydown = function(evt) {
 	 		     evt = evt || window.event;     
 	 		     if (evt.keyCode == 27 && popedUp) {  	 		    	
 	 		    	hideMRDEntryScreen();     
	 	 		 } 
 	 		  }; 	
 	</script>
 	<style>
	 	.wrapper1, .wrapper2 {width: 955px; overflow-x: scroll; overflow-y:hidden;}
		.wrapper1 {height: 20px; }
		.wrapper2 {height: 100%; }
		.div1 {width:1000px; height: 20px; }
		.div2 {width:1000px; height: 100%;overflow: auto;overflow-x:hidden;}
	</style>
</head>
<body onload="fnOnLoad();">
<%@include file="../jsp/CommonOverlay.html"%> 
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage" border="0">
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%>
					</td>
				</tr>				
				<tr>
					<td  valign="top">
						<s:form name="reloader" id="reloader" action="dataEntry.action" method="post" onsubmit=" return fnOnSubmit();" theme="simple">
							<s:hidden name="startPage" id="startPage" value=""/>
							<s:hidden name="lastPage" id="lastPage" value=""/>
						</s:form>	
						<s:form name="freeze" id="freeze" action="javascript:void(0);" method="post" onsubmit=" return fnOnSubmit();" theme="simple">
							<s:hidden name="freezeData" id="freezeData" value=""/>					
							<table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">
							<tr>
							<td>
							
	
	<style>
	#mrbox {
		z-index: 500;
		filter: alpha(opacity=90); /*older IE*/
		filter:progid:DXImageTransform.Microsoft.Alpha(opacity=90); /* IE */
		-moz-opacity: 1.0; /*older Mozilla*/
		-khtml-opacity: 1.0;   /*older Safari*/
		opacity: 1.0;   /*supported by current Mozilla, Safari, and Opera*/
		background-color:#FFF;
		position:fixed; top:0px; left:0px; width:100%; height:100%; overflow:hidden; color:#FFFFFF; text-align:center; vertical-align:middle;
	}	
</style>

<div id="mrdebox">
<table width='100%' height='100%' align='center' title="Press Esc To Exit">
	<tr>
		<td align='center' width='100%' height='100%'>
			<table class="djbpage" border="0">
				 
				<tr>
					<td  valign="top">						
											
						<table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="left"  valign="top" >									   
									<div class="paged-main-div" >	
						<fieldset>
						<legend><span id="searchFor">Consumer Search By KNO</span></legend>
						
						<table width="95%" align="center" border="0">
						<s:form name="consumerSearch" id="reloader" theme="simple">
							<tr>
								<td align="center" colspan="3">
								<table width="80%" align="center" border="0" cellpadding="0"
										cellspacing="0">
										
										<tr>
											<td align="left" width="40%">KNO<font color="red"><sup>&nbsp;*</sup></font>
											</td>
											<td align="left" width="30%"><s:textfield name="kno"
										id="kno" maxlength="10" cssClass="textbox"
										/></td>									   
										<td align="left"><input type="button"  value="    Search     "  class="groovybutton"
											 onclick ="return onSearchAjax()"/></td>										
										</tr>										
									</table>
									</td>
							</tr>
							</s:form>
						</table>
						
						
						
						</fieldset>	
						
						<div class="paged-div _current" title="Current Search Criteria." >
						<fieldset>
						<legend>Consumer Search deatils </legend>
						<table width="100%" align="center" border="0" cellpadding="0" cellspacing="5px">
						   <tr>
						   <td valign="top">
						   <font	size="2"><font size="2"><b>Displaying Records for Consumer:</b><b>Bill Round:</b>&nbsp;&nbsp;<span id="customerBillRound"></span>&nbsp;&nbsp;<b>KNO:</b>&nbsp;&nbsp;<span id="customerkno"></span>&nbsp;&nbsp;<b>Area:</b>&nbsp;&nbsp;<span id="customerArea"></span>&nbsp;&nbsp;<b>Zone:</b>&nbsp;&nbsp;<span id="customerZone"></span>&nbsp;&nbsp;<b>Consumer Status:</b>&nbsp;&nbsp;<span id="customerStatus"></span>&nbsp;&nbsp;<b>Bill ID:</b>&nbsp;&nbsp;<span id="customerBillID"></span></font></font>&nbsp;
						    </td>
						    </tr>
						</table>
						</fieldset>
						</div>					             
						                <div id="p" class="paged-div _current" style="">
							                <table width="100%" align="center" border="0" cellpadding="0" cellspacing="5px">
												<tr>
													<td align="left"  valign="top" width="50%">									
														<fieldset>
															<legend>Consumer Details </legend>
															<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td align="left" width="40%" colspan="2">
																		<label>Name</label>
																	</td>
																	<td align="left" width="60%" colspan="2" nowrap>
																		<span id="consumerName"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="40%" colspan="2">
																		<label>KNO</label>
																	</td>
																	<td align="left" width="60%" colspan="2">
																		<span id="Kno1"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="40%" colspan="2">
																		<label>WC No</label>
																	</td>
																	<td align="left" width="60%" colspan="2">
																		<span id="consumerWcNo"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="40%" colspan="2">
																		<label>Category</label>
																	</td>
																	<td align="left" width="60%" colspan="2">
																		<span id="consumerCat"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="40%" colspan="2">
																		<label>Consumer Type</label>
																	</td>
																	<td align="left" width="60%" colspan="2">
																		<span id="consumerServiceType"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="40%" colspan="2" nowrap>
																		<label>Last Bill Generation Date</label>
																	</td>
																	<td align="left" width="60%" colspan="2">
																		<span id="consumerLastBillGenerationDate"></span>
																	</td>
																</tr>
															</table>
														</fieldset>
													</td>
													<td align="left"  valign="top" width="50%">
														<fieldset>
															<legend>Previous Meter Read Details</legend>
															<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>Last Meter Read Date</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevMeterReadDate"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>Last Meter Read Status</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevMeterStatus"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>Last Registered Reading</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevRegRead"></span>
																	</td>
																</tr>
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>Actual Last Meter Read Date</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevActMeterReadDate"></span>
																	</td>
																</tr>																
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>Actual Last Registered Reading</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevActRegRead"></span>
																	</td>
																</tr>	
																<tr>
																	<td align="left" width="60%" colspan="2">
																		<label>No Of Floors</label>
																	</td>
																	<td align="left" width="40%" colspan="2">
																		<span id="prevNoOfFloor"></span>
																	</td>
																</tr>															
															</table>
														</fieldset>
													</td>
												</tr>
												<tr>
													<td align="left"  valign="top" colspan="2">
														<fieldset>													
															<legend>Current Meter Read Details</legend>
															<form action="javascript:void(0);" name="form" >
																															
																<input type="hidden" name="kno1" id="kno1" />
																<input type="hidden" name="billRound1" id="billRound1" />
																<input type="hidden" name="seqNo1" id="seqNo1" />
																<input type="hidden" name="hidConCat1" id="hidConCat1" />
																<input type="hidden" name="hidConStatus1" id="hidConStatus1" />
																<input type="hidden" name="hidPrevMeterReadDate1" id="hidPrevMeterReadDate1" />
																<input type="hidden" name="hidPrevActMeterReadDate1" id="hidPrevActMeterReadDate1" />
																<input type="hidden" name="hidMeterReadDate1" id="hidMeterReadDate1" />
																<input type="hidden" name="hidMeterStatus1" id="hidMeterStatus1" />
																<input type="hidden" name="hidPrevMeterStatus1" id="hidPrevMeterStatus1"/>
																<input type="hidden" name="hidPrevMeterRead1" id="hidPrevMeterRead1" />
																<input type="hidden" name="hidPrevActMeterRead1" id="hidPrevActMeterRead1" />
																<input type="hidden" name="hidCurrentMeterRead1" id="hidCurrentMeterRead1" />
																<input type="hidden" name="hidNewAverageConsumption1" id="hidNewAverageConsumption1"/>
																<input type="hidden" name="hidNewNoOfFloor1" id="hidNewNoOfFloor1"/>
																<input type="hidden" name="hidConsumerType1" id="hidConsumerType1" />
																<input type="hidden" name="consPreBillProcStatus1" id="consPreBillProcStatus1" />
																 <input type="hidden" name="hidrecordStatusAndErrorDesc" id="recordStatusAndErrorDesc" />
																<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Service Cycle Route Sequence No</label>
																		</td>
																		<td align="left" width="60%" colspan="2">																
																			<span id="svcCycRtSeq"></span>														
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Consumer Status</label>
																		</td>
																		<td align="left" width="60%" colspan="2">																
																			<select name="consumerStatus1" id="consumerStatus1"  class="selectbox-long" onchange="setAllForConsumerStatus();" disabled="true" >
																		  		
																		  		<%-- <option value="60">Regular</option>
																		  		<option value="<%=ConsumerStatusLookup.CAT_CHANGE.getStatusCode() %>"><%=ConsumerStatusLookup.CAT_CHANGE.getStatusDescr() %></option>
																		  		<option value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>"><%=ConsumerStatusLookup.DISCONNECTED.getStatusDescr() %></option>
																		  		<option value="<%=ConsumerStatusLookup.METER_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_INSTALLED.getStatusDescr() %></option>
																		  		<option value="<%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusCode() %>"><%=ConsumerStatusLookup.METER_NOT_INSTALLED.getStatusDescr() %></option>
																		  		<option value="<%=ConsumerStatusLookup.ERROR_IN_PHYSICAL_MRD.getStatusCode() %>">Miscellaneous</option>
																		  		<option value="<%=ConsumerStatusLookup.REOPENING.getStatusCode() %>"><%=ConsumerStatusLookup.REOPENING.getStatusDescr() %></option>
																		  		<option value="<%=ConsumerStatusLookup.INVALID_DATA.getStatusCode() %>"><%=ConsumerStatusLookup.INVALID_DATA.getStatusDescr() %></option>--%>
																		  		<option value=""></option>
																		  		<%ArrayList consumerStatusList=(ArrayList)session.getAttribute("sessionConsumerList");
																		  			for(int m=0; m < consumerStatusList.size();m++){ %>
																			  		<option value="<%=((ConsumerStatusLookup)consumerStatusList.get(m)).getStatusCode()%>"><%=((ConsumerStatusLookup)consumerStatusList.get(m)).getStatusDescr()%></option>
																			  		<%} %>
																		  		
																		  	</select><span id="popupMTR"></span>																
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Meter Read Date/Bill Generation Date</label><font size="0.5">(DD/MM/YYYY)</font><font color="red"><sup>*</sup></font>
																		</td>
																		<td align="left" width="20%" nowrap>														
																			<span id="meterReadDateSpan1">
																				<input type="text" name="meterReadDD1" id="meterReadDD1" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusMM(this);"/>&nbsp;/
																				<input type="text" name="meterReadMM1" id="meterReadMM1" size="1" maxlength="2" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" onkeyup="fnSetFocusDDConfirm(this);" />&nbsp;/
																			</span>
																			<input type="text" name="meterReadYYYY1" id="meterReadYYYY1" size="2" maxlength="4" class="textbox"  style="text-align:center;" onchange="setMeterReadDate();" />
																		</td>
																		<td align="left" width="40%" nowrap>
																		<label>Re-enter</label><font color="red"><sup>*</sup></font>&nbsp;
																			<input type="text" name="meterReadDDConfirm1" id="meterReadDDConfirm1" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();"  onkeyup="fnSetFocusMMConfirm(this);"/>&nbsp;/
																			<input type="text" name="meterReadMMConfirm1" id="meterReadMMConfirm1" size="1" maxlength="2" class="textbox"  style="text-align:center;" onfocus="disableMeterReadDate();" onblur="checkMeterReadDate();" onkeyup="fnSetFocusMeterReadStatus(this);"/>&nbsp;/
																			<input type="text" name="meterReadYYYYConfirm1" id="meterReadYYYYConfirm1" size="2" maxlength="4" class="textbox"  style="text-align:center;" />
																			<span id="confirmDate1"></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Meter Read Status</label>
																		</td>
																		<td align="left" width="20%" >																
																			<select name="meterReadStatus1" id="meterReadStatus1" class="selectbox" onchange="setMeterReadStatus(this);">
																		  		<option value="">Please Select</option>	
																		  			<%ArrayList meterReadStatusList=(ArrayList)session.getAttribute("meterReadStatusList");
																		  			for(int m=0; m < meterReadStatusList.size();m++){ %>
																			  		<option value="<%=meterReadStatusList.get(m)%>"><%=meterReadStatusList.get(m)%></option>
																			  		<%} %>																		  		
																			</select>																
																		</td>
																		<td align="left" width="40%" nowrap>																																					
																			Read Type&nbsp;<span id="meterReadTypeSpan1"></span>&nbsp;&nbsp;Billing&nbsp;<span id="billTypeSpan1"></span>
																		</td>
																		
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Current Meter Read</label>
																		</td>
																		<td align="left" width="20%" nowrap>																	
																			<span id="currentMeterReadSpan1">
																			
																		    <input type="text" name="currentMeterRead1" id="currentMeterRead1"  style="text-align:right;" class="textbox"  onfocus="fnCheckMeterReadStatus();" onblur="IsPositiveNumber(this);" onchange="setCurrentMeterRead(this);"    />
																			
																			</span>&nbsp;KL
																		</td>
																		<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																			<input type="text" name="currentMeterReadConfirm1" id="currentMeterReadConfirm1" style="text-align:right;" class="textbox" onfocus="disableCurrentMeterRead();" onchange="checkCurrentMeterRead();"/>&nbsp;KL
																			<span id="confirmCurrentMeterRead1"></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>Current Average Consumption</label>
																		</td>
																		<td align="left" width="60%" colspan="2">																	
																			<span><input type="text" name="currentAverageConsumption1" id="currentAverageConsumption1"  style="text-align:right;" class="textbox" disabled="true" readonly="true" />&nbsp;KL</span>
																		</td>																		
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>New Average Consumption</label>
																		</td>
																		<td align="left" width="20%" nowrap>
																			<span id="newAverageConsumptionSpan1">
																			<input type="text" name="newAverageConsumption1" id="newAverageConsumption1"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onchange="setNewAvgConsumption(this);" />
																			</span>&nbsp;KL
																		</td>
																		<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																			<input type="text" name="newAverageConsumptionConfirm1" id="newAverageConsumptionConfirm1" style="text-align:right;" class="textbox" onfocus="disableNewAverageConsumption();" onchange="checkNewAverageConsumption();"/>&nbsp;KL
																			<span id="confirmNewAverageConsumption1"></span>
																		</td>
																	</tr>
																	<tr>
																		<td align="left" width="40%" colspan="2">
																			<label>No Of Floors</label>
																		</td>
																		<td align="left" width="20%" nowrap>
																			<span id="newNoOfFloorSpan1">
																			<input type="text" name="newNoOfFloor1" id="newNoOfFloor1"  style="text-align:right;" class="textbox" onblur="IsPositiveNumber(this);"  onblur='IsNumber(this);' onchange="setNewNoOfFloor(this);"/>
																			</span>&nbsp;
																		</td>
																		<td align="left" width="40%" nowrap>
																			<label>Re-enter</label><font color="red"><sup>&nbsp;</sup></font>&nbsp;
																			<input type="text" name="newNoOfFloorConfirm1" id="newNoOfFloorConfirm1" style="text-align:right;" class="textbox" onfocus="disableNewNoOfFloor();" onchange="checkNewNoOfFloor();"/>&nbsp;&nbsp;&nbsp;
																			<span id="confirmNewNoOfFloorConfirm1"></span>
																		</td>
																	</tr>	
																	<tr>
																		<td align="left" width="40%" colspan="2">
																		<div id="remarksDiv11" style="display:none;align:center;">
																			<label>Remarks</label><font color="red"><sup>*</sup></font>		
																		</div>
																		</td>
																		<td align="left" width="40%" colspan="2">
																			<div id="remarksDiv21" style="display:none;align:center;">																																						
																				<textarea rows="2" cols="48" name="remarks1" id="remarks1"  style="text-align:left;" onchange="setRemarks(this);"></textarea>
																			</div>
																		</td>
																	</tr>	
																	<tr><td align="left" colspan="4">&nbsp;</td></tr>
																	<tr>
																		<td align="left" width="34%">
																			<input type="button"  value=" Freeze and Generate Bill "  id="freezeAndGenBill" class="groovybutton" onclick="freezeandGenerateBill();"/>																			
																		</td>
																		
																		
																		<td align="right" width="33%">
																			<input type="button" name="btnReset" id="btnPrev" value="Reset" class="groovybutton" onclick="resetAll();"/>
																		</td>
																		<td align="right" width="33%" colspan="2">
																			<input type="submit"  value=" Save " id="btnSave" class="groovybutton"  onclick="fnSaveDetails();"/>
																		</td>
																	</tr>
																</table>
															</form>
														</fieldset>	
													</td>
												</tr><!-- take this line upto -->
											</table>
										</div>
									</div>	
								</td>
							</tr>
							
							
							
					</table>	
						<div id="ajax-result" style="display:block;text-align:center;" title="Server Message"><div class='search-option'>&nbsp;</div></div>						
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
			                </td></tr>
							</table>	
							</s:form>						
							<div id="data_submited" style="display:block;text-align:center;">&nbsp;</div>
							<div id="server-result" style="display:block;text-align:center;">&nbsp;<font color="red" size="2"><s:actionerror /></font></div>				
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
				$('input[type="password"]').focus(function() { 
					$(this).addClass("textboxfocus"); 
				});   
				$('input[type="password"]').blur(function() {  					
					$(this).removeClass("textboxfocus"); 
					$(this).addClass("textbox"); 
				});
	 }); 
	$(function(){
		$(".wrapper1").scroll(function(){
		    $(".wrapper2")
		        .scrollLeft($(".wrapper1").scrollLeft());
		});
		$(".wrapper2").scroll(function(){
		    $(".wrapper1")
		        .scrollLeft($(".wrapper2").scrollLeft());
		});
		});

	 $("form").submit(function(e){

		 fnUpdateLastRecord(currentPage);
			/*var ddId="meterReadDD"+currentPage;
	    	var mmId="meterReadMM"+currentPage;
	    	var yyyyId="meterReadYYYY"+currentPage;
	    	document.getElementById(ddId).value=Trim(document.getElementById(ddId).value);
	    	document.getElementById(mmId).value=Trim(document.getElementById(mmId).value);
	    	var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
	    	var consumerStatusId="consumerStatus"+currentPage;
	    	var consumerStatusValue=Trim(document.getElementById(consumerStatusId).value)
	    	if(meterReadDate.length!=10 && consumerStatusValue=='60'){
	    		alert('Meter Read Date/Bill Generation Date is Mandatory.');
	    		disableFields(document.getElementById(consumerStatusId));
	    		toUpdateMeterReadDate="";
	    		toUpdateMeterReadDateConfirm="";
	    		document.getElementById(ddId).focus();
	    		isValid=false;
	    		return;


		    	}*/
			//prevPage=currentPage;
			//nextPage=parseInt(currentPage)+parseInt(1);
			//var maxInt=parseInt(maxPage);
			//var nextInt=parseInt(nextPage);


		   // if(nextInt-1==maxInt && maxInt < parseInt(totalRecords)){
		    //	fnUpdateRecord(nextPage);
		    //	if(isValid){
		    		//clearToUpdateVar();			    	 		    	 
				//	document.forms[0].startPage.value=nextPage;
				//	document.forms[0].lastPage.value=nextInt+parseInt(MAX_RECORD_PER_PAGE)-1;
				//	document.forms[0].buttonClicked.value="Next";
				//	document.forms[0].action="dataEntry.action";
				//	hideScreen();
				//	document.forms[0].submit();
		    	//}
				    
			//}else{
								    
			  // 	if( nextPage-1 < parseInt(totalRecords)){
			   		//fnUpdateRecord(nextPage);
				   // if(isValid){
				    //	clearToUpdateVar();
					  //  currentPage=nextPage;			   
					    //document.getElementById('curr-page-no').innerHTML="<font color='red'><b>Record No: "+currentPage+"</b></font>";
					   // $('._current','#pagination').removeClass('_current').hide();
						//$('#p'+nextPage).addClass('_current').show();
						//var currSpanId=nextPage-1;
						
						//$("#pageSpan"+currSpanId).addClass('pageNo');
					  //  $("#pageSpan"+nextPage).removeClass("pageNo");
					  //  $("#pageSpan"+nextPage).addClass("currentPageNo");							
						//setOnLoadValue();
				    //}
			    //}else{
			    //	fnUpdateLastRecord(nextPage);
			   // }
		   // }
		  });
	</script>
</body>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>	
<%}catch(Exception e){
	e.printStackTrace();
%>
</script>
<script type="text/javascript">
 document.location.href="logout.action";
</script>
<%} %>
</html>