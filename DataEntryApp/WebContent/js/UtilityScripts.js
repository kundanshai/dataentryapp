//Added By Matiur
//=====================================================================

//=======================For Ajax Call Start=================================
var httpObject = null;
// Get the HTTP Object
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
//=======================For Ajax Call end=================================
var highlightcolor="#FFCC99";
highlightcolor="#E1E1FF";
var ns6=document.getElementById&&!document.all;
var previous='';
var eventobj;

//Regular expression to highlight only form elements
var intended=/INPUT|TEXTAREA|SELECT|OPTION/;

//Function to check whether element clicked is form element
function checkel(which)
{
    if (which.style&&intended.test(which.tagName))
    {
        if (ns6&&eventobj.nodeType==3)
            eventobj=eventobj.parentNode.parentNode;
        return true;
    }
    else
        return false;
}

//This function Highlights the currently selected object if it is Text-Field , TextArea, ComboBox
function highlight(e)
{
    eventobj=ns6? e.target : event.srcElement;
    if (previous!='')
    {
        if (checkel(previous))
            previous.style.backgroundColor='';
        previous=eventobj;
        if (checkel(eventobj)){
			if(eventobj.readOnly != true){
				if(eventobj.type == 'text' || eventobj.type == 'select-one'
					|| eventobj.type == 'textarea' || eventobj.type == 'radio'){
					eventobj.style.backgroundColor=highlightcolor;
				}
			}
		}
    }
    else
    {
        if (checkel(eventobj))
            eventobj.style.backgroundColor=highlightcolor;
        previous=eventobj;
    }
}
//This function does nothing. it is just a dummy function
function doNothing(){
	return;
}

//This function pops up a seperate action in a new window
function popup(url,windowname)
{
 
	var winH = 600; //This is the desired screen height
	var winW = 850;	//This is the desired screen width
	var winTop = screen.availHeight/2-(winH/2);
	var winLeft = screen.availWidth/2-(winW/2);

	var viewimageWin = window.open(url, windowname,'toolbar=no, location=no, directories=no, status=yes, menubar=no, scrollbars=Yes, resizable=no, copyhistory=no, width=' + winW + ', height=' + winH + ', top=' + winTop + ', left=' + winLeft);
}

//This function opens a modal window
function popUpModalWindow(url,windowName) 
{ 
	var viewimageWin = window.showModalDialog(url, windowName, 'status=yes'); 
}

//Getting the formIndex in the JSP
function GetFormIndex(ThisElement)
{ 
	for (var i=0; i < ThisElement.form.elements.length; i++)
	{ 
		if (ThisElement == ThisElement.form.elements[i])
		{ 
			FormIndex = i;
			return FormIndex;
		} 
	} 
}

//This function maximizes the main window
function maximize_main_window()
{
	top.window.moveTo(0,0);
	if (document.all)
	{
		top.window.resizeTo(screen.availWidth,screen.availHeight);
	}
	else
	{
		if (document.layers||document.getElementById)
		{
			if (top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth)
			{
				top.window.outerHeight = screen.availHeight;
				top.window.outerWidth = screen.availWidth;
			}
		}
	}
}

// Trims all spaces to the left of a specific string
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

/*
*This function returns true if Start Date is less then End Date, otherwise returns false
*/
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
//START: Added by Rajib Hazarika as per Jtrac DJB-4537 on 31-AUG-2016
function compDtwithToday(str) {
    var yy = str.substr(0, 4);
    var mm=str.substr(5, 2);
    var dd=str.substr(8, 2);
    var rightnow = new Date();
    var compDt= new Date(yy,mm-1,dd);
    var res='';
    if (compDt>rightnow)
	 {
		return true;
	 }
	 else
	 {
		 return false;
	 }
}
//END: Added by Rajib Hazarika as per Jtrac DJB-4537 on 31-AUG-2016
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
function formatCurrency(obj)
{
	if(isNaN(obj.value)==true)
	{
		alert("Error Parsing Currency: NON-NUMERIC CHARACTERS FOUND WHERE NUMERIC EXPECTED");
		obj.value="";
		obj.focus();
		return false;
	}

    if(obj.value=="")
    {
        obj.value = "0.00";
        return;
    }

    var len = obj.value.length;
    var dec_posn = obj.value.indexOf(".");

    if(dec_posn==-1)
        obj.value = obj.value +".00";
    if(dec_posn!=-1)
    {
        if(dec_posn==0)
            obj.value = "0"+obj.value;
        if((len-dec_posn)==1)
            obj.value = obj.value + "00";
        if((len-dec_posn)==2)
            obj.value = obj.value + "0";
        if((len-dec_posn)>3)
            obj.value = obj.value.substring(0,dec_posn+3);
    }
}

//Function to append Zeroes in Ward Code
function prefixZeroes(obj)
{
	var len = obj.value.length;
	if(len==0)
		return false;

	if(len==1)
		obj.value = "00" + obj.value;

	if(len==2)
		obj.value = "0" + obj.value;
}

//ENSURE THAT VALUE IS TWO DIGITS IN LENGTH
function makeTwoDigit(inValue) {
	if(inValue!='' && !isNaN(inValue)){
		var numVal = parseInt(inValue, 10);

		// VALUE IS LESS THAN TWO DIGITS IN LENGTH
		if (numVal < 10) {

			// ADD A LEADING ZERO TO THE VALUE AND RETURN IT
			return("0" + numVal);
		} else {
			return numVal;
		}
	}else{
		return "";
	}
}

//Function to append Zeroes in time field
function prefixZeroesForTime(obj)
{
	var len = obj.value.length;
	if(len==0)
		return false;

	if(len==1)
		obj.value = "0" + obj.value;
}

//Function to add a new row in the Jsp
function add_row(id)
{
    var table = document.getElementById(id);

    // get number of current rows
    var rowItems = table.getElementsByTagName("tr");
    var rowCount = rowItems.length;

    // insert the new row
    var newRow = table.insertRow(rowCount);

    // get count of cells for the last row (so we know how many to insert)
    var cellItems = rowItems[rowCount-1].getElementsByTagName("td");
    var cellCount = cellItems.length;

    // loop over the children of the last set of cells
    for (i=0; i<cellCount; i++)
    {
        // insert an analagous cell
        var cell = newRow.insertCell(i);

        // get the children of each cell
        var kids = cellItems[i].childNodes;

        for (j=0; j<kids.length; j++)
        {
	        var newChild = kids[j].cloneNode(true);

	        // copy data into this cell
	        cell.appendChild(newChild);
        }
    }
}

//Function to delete a particular
function del_row(id)
{
    var table = document.getElementById(id);

    // get number of current rows
    var rowItems = table.getElementsByTagName("tr");
    var rowCount = rowItems.length;

    if(rowCount==2)
	{
		alert("MORE ROWS IN THE TABLE CANNOT BE DELETED");
		return;
	}
    else
	{
		var checked_sum = 0;//count the no. of checkboxes checked
		for(i=0;i<rowCount-1;i++)
		{
			if(document.forms[0].delrow[i].checked)
				checked_sum = checked_sum + 1;
		}
		if(checked_sum== 0)
		{
			alert("NO ROW SELECTED");
			return;
		}

		//delete row and update amount column
		for(i=document.forms[0].delrow.length-1; i>=0; i--)
		{
			if(document.forms[0].delrow[i].checked)
				table.deleteRow(i+1);
				
			rowItems = table.getElementsByTagName('tr');
			rowCount = rowItems.length;
			if(rowCount==2 && document.forms[0].delrow[0].checked) 
			{
				alert("THE ROW CANNOT BE DELETED !!!");
				return;
			}
		}

	}
}

//Function to close the window
function closeWindow()
{
	if (window.opener) 
		self.close();
	else 
		history.back();
}


//Function to get the minutes from a time
function getMinutes(time)
{
	var position_colon = time.indexOf(':');
	var position_space = time.indexOf(' ');
	var length_of_time_string = time.length;
	var time_in_min = 0;
	
	var hour = parseInt(time.substring(0, position_colon), 10);
	var min = parseInt(time.substring(position_colon + 1, position_space), 10);
	var meridien = time.substring(length_of_time_string - 2, length_of_time_string);
	
	if(meridien == 'AM')
	{
		time_in_min = hour * parseInt(60) + min;
	}
	else if(meridien == 'PM')
	{
		if(hour < 12)
			hour = hour + parseInt(12);

		time_in_min = hour * parseInt(60) + min;
	}
	return time_in_min;
}

// This Method returns difference Between 2 dates

function DateDifference(d1, d2) 
{

	var dd1;
	var dd2;
	var mm1;
	var mm2;
	var yy1;
	var yy2;
	var date1 = new String(d1);
	var date2 = new String(d2);
	var arr1 = new Array();
	var arr2 = new Array();
	var days1;
	var days2;
	
	arr1 = date1.split("/");
	arr2 = date2.split("/");
	
	dd1 = parseInt(arr1[0],10);
	mm1 = parseInt(arr1[1],10);
	yy1 = parseInt(arr1[2],10);
	
	dd2 = parseInt(arr2[0],10);
	mm2 = parseInt(arr2[1],10);
	yy2 = parseInt(arr2[2],10);
	
	
	days1 = numberOfDays(dd1,mm1,yy1);
	days2 = numberOfDays(dd2,mm2,yy2);
		
	diffDays = days2-days1;
	
	return diffDays;
	
}

function numberOfDays(d,m,y)
{
	var dd;
	var dm;
	var dy;
	var ndays ;
	var i;
	var days = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	
	dy = y-1900;
	dm = m-1;
	dd = d-1;

	ndays = 0;
		
	for(i=1900;i<y;i++)
	{
		if (i % 4 == 0)
			ndays = ndays + 366;
		else
			ndays = ndays + 365;
	}
	
	for(i=0;i<=dm-1;i++)
	{
		ndays = ndays + days[i];
	}
	
	if ( y % 4 == 0 && m > 2 )
	{
		ndays = ndays + 1;
	}
	
	ndays = ndays + dd;
	
	return ndays;
}
//to get path 
function fnGetContextRootPath()
{
	
	var path = document.URL;
	var tempPath = path.substr(7);
	var index = tempPath.indexOf('/');
	tempPath = tempPath.substr(parseFloat(index) + 1);	
	var tempIndex = tempPath.indexOf('/');	
	tempPath = tempPath.substr(0,parseFloat(tempIndex));
	return tempPath;
}
function getBrowserdtls()
{	
	var browser = navigator.appName;
	var b_version = navigator.appVersion;
	var version = parseFloat(b_version);

	/*var browserDtlsArray = new Array();
	browserDtlsArray[0] = browser;
	browserDtlsArray[0] = version;
	return browserDtlsArray;*/
	return browser;
}
//this function will pad a string 
//the input will be 
//1.The string to be pad
//2.'L' or 'R' indicating its left or right padding
//3.Total size of the padded string
//4.Padding character
//e.g :- 
//1.stringPadding('abc','L',5,'*')=**abc
//2.stringPadding('sdf','R',6,'#')=sdf###
function stringPadding(stringToPadding,leftOrRight,totalSize,paddingCharacter)
     {
     var newString='';
     var stringLength=stringToPadding.length;
     var addExtraCharacter=totalSize-parseInt(stringLength);
     for(var i=0;i<addExtraCharacter;i++)
          {
          newString=newString+paddingCharacter;
          }
     if(leftOrRight=='L')
          {
          newString=newString+stringToPadding;
          }
     if(leftOrRight=='R')
          {
          newString=stringToPadding+newString;
          }
     return newString;
     }
/*
This Function is to make a drop down readonly
You just need to call this function onfocus,Onmouseover & ondblclick event
If u want to add message as alert then pass a message string else pass a empty string
*/
function canNotChangeMe(message)
     {
     if(message.length>0)
          {
          alert(message);
          }
     window.focus();
     }

function showHideTooltip() 
     {
     var obj = event.srcElement;
     with(document.getElementById("tooltip")) 
          {
          innerHTML = obj.options[obj.selectedIndex].text;
          with(style) 
               {
               //alert(event.type);
               if(event.type == "mouseout") 
                    {
                    display = "none";
                    } 
               else 
                    {
                    display = "inline";
                    left = event.x;
                    top = event.y;
                    }
               }
          }
     }
function fnUpperCase(obj)
{
	var value = obj.value;
	if(value != '')
	{
		value = value.toUpperCase();
		obj.value = value;
	}
}
function fnDisableDropDown()
{
	//alert('Inactive Field !!');
	//document.forms[0].btnClose.focus();
	window.focus();
	return false;
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
function pageWidth()  { return window.innerWidth != null? window.innerWidth : document.documentElement && document.documentElement.clientWidth ?  document.documentElement.clientWidth : document.body != null ? document.body.clientWidth : null; }  function pageHeight()  { return  window.innerHeight != null? window.innerHeight : document.documentElement && document.documentElement.clientHeight ?  document.documentElement.clientHeight : document.body != null? document.body.clientHeight : null; }  function posLeft()  { return typeof window.pageXOffset != 'undefined' ? window.pageXOffset :document.documentElement && document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ? document.body.scrollLeft : 0; }  function posTop()  { return typeof window.pageYOffset != 'undefined' ?  window.pageYOffset : document.documentElement && document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ? document.body.scrollTop : 0; }  function posRight()  { return posLeft()+pageWidth(); }  function posBottom()  { return posTop()+pageHeight(); }
//=====================================================================

var _enc = function (string) {

	function RotateLeft(lValue, iShiftBits) {
		return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
	}

	function AddUnsigned(lX,lY) {
		var lX4,lY4,lX8,lY8,lResult;
		lX8 = (lX & 0x80000000);
		lY8 = (lY & 0x80000000);
		lX4 = (lX & 0x40000000);
		lY4 = (lY & 0x40000000);
		lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
		if (lX4 & lY4) {
			return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
		}
		if (lX4 | lY4) {
			if (lResult & 0x40000000) {
				return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
			} else {
				return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
			}
		} else {
			return (lResult ^ lX8 ^ lY8);
		}
	}

	function F(x,y,z) { return (x & y) | ((~x) & z); }
	function G(x,y,z) { return (x & z) | (y & (~z)); }
	function H(x,y,z) { return (x ^ y ^ z); }
	function I(x,y,z) { return (y ^ (x | (~z))); }

	function FF(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};

	function GG(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};

	function HH(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};

	function II(a,b,c,d,x,s,ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	};

	function ConvertToWordArray(string) {
		var lWordCount;
		var lMessageLength = string.length;
		var lNumberOfWords_temp1=lMessageLength + 8;
		var lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64;
		var lNumberOfWords = (lNumberOfWords_temp2+1)*16;
		var lWordArray=Array(lNumberOfWords-1);
		var lBytePosition = 0;
		var lByteCount = 0;
		while ( lByteCount < lMessageLength ) {
			lWordCount = (lByteCount-(lByteCount % 4))/4;
			lBytePosition = (lByteCount % 4)*8;
			lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
			lByteCount++;
		}
		lWordCount = (lByteCount-(lByteCount % 4))/4;
		lBytePosition = (lByteCount % 4)*8;
		lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
		lWordArray[lNumberOfWords-2] = lMessageLength<<3;
		lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
		return lWordArray;
	};

	function WordToHex(lValue) {
		var WordToHexValue="",WordToHexValue_temp="",lByte,lCount;
		for (lCount = 0;lCount<=3;lCount++) {
			lByte = (lValue>>>(lCount*8)) & 255;
			WordToHexValue_temp = "0" + lByte.toString(16);
			WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
		}
		return WordToHexValue;
	};

	function Utf8Encode(string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	};

	var x=Array();
	var k,AA,BB,CC,DD,a,b,c,d;
	var S11=7, S12=12, S13=17, S14=22;
	var S21=5, S22=9 , S23=14, S24=20;
	var S31=4, S32=11, S33=16, S34=23;
	var S41=6, S42=10, S43=15, S44=21;

	string = Utf8Encode(string);

	x = ConvertToWordArray(string);

	a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;

	for (k=0;k<x.length;k+=16) {
		AA=a; BB=b; CC=c; DD=d;
		a=FF(a,b,c,d,x[k+0], S11,0xD76AA478);
		d=FF(d,a,b,c,x[k+1], S12,0xE8C7B756);
		c=FF(c,d,a,b,x[k+2], S13,0x242070DB);
		b=FF(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
		a=FF(a,b,c,d,x[k+4], S11,0xF57C0FAF);
		d=FF(d,a,b,c,x[k+5], S12,0x4787C62A);
		c=FF(c,d,a,b,x[k+6], S13,0xA8304613);
		b=FF(b,c,d,a,x[k+7], S14,0xFD469501);
		a=FF(a,b,c,d,x[k+8], S11,0x698098D8);
		d=FF(d,a,b,c,x[k+9], S12,0x8B44F7AF);
		c=FF(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
		b=FF(b,c,d,a,x[k+11],S14,0x895CD7BE);
		a=FF(a,b,c,d,x[k+12],S11,0x6B901122);
		d=FF(d,a,b,c,x[k+13],S12,0xFD987193);
		c=FF(c,d,a,b,x[k+14],S13,0xA679438E);
		b=FF(b,c,d,a,x[k+15],S14,0x49B40821);
		a=GG(a,b,c,d,x[k+1], S21,0xF61E2562);
		d=GG(d,a,b,c,x[k+6], S22,0xC040B340);
		c=GG(c,d,a,b,x[k+11],S23,0x265E5A51);
		b=GG(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
		a=GG(a,b,c,d,x[k+5], S21,0xD62F105D);
		d=GG(d,a,b,c,x[k+10],S22,0x2441453);
		c=GG(c,d,a,b,x[k+15],S23,0xD8A1E681);
		b=GG(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
		a=GG(a,b,c,d,x[k+9], S21,0x21E1CDE6);
		d=GG(d,a,b,c,x[k+14],S22,0xC33707D6);
		c=GG(c,d,a,b,x[k+3], S23,0xF4D50D87);
		b=GG(b,c,d,a,x[k+8], S24,0x455A14ED);
		a=GG(a,b,c,d,x[k+13],S21,0xA9E3E905);
		d=GG(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
		c=GG(c,d,a,b,x[k+7], S23,0x676F02D9);
		b=GG(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
		a=HH(a,b,c,d,x[k+5], S31,0xFFFA3942);
		d=HH(d,a,b,c,x[k+8], S32,0x8771F681);
		c=HH(c,d,a,b,x[k+11],S33,0x6D9D6122);
		b=HH(b,c,d,a,x[k+14],S34,0xFDE5380C);
		a=HH(a,b,c,d,x[k+1], S31,0xA4BEEA44);
		d=HH(d,a,b,c,x[k+4], S32,0x4BDECFA9);
		c=HH(c,d,a,b,x[k+7], S33,0xF6BB4B60);
		b=HH(b,c,d,a,x[k+10],S34,0xBEBFBC70);
		a=HH(a,b,c,d,x[k+13],S31,0x289B7EC6);
		d=HH(d,a,b,c,x[k+0], S32,0xEAA127FA);
		c=HH(c,d,a,b,x[k+3], S33,0xD4EF3085);
		b=HH(b,c,d,a,x[k+6], S34,0x4881D05);
		a=HH(a,b,c,d,x[k+9], S31,0xD9D4D039);
		d=HH(d,a,b,c,x[k+12],S32,0xE6DB99E5);
		c=HH(c,d,a,b,x[k+15],S33,0x1FA27CF8);
		b=HH(b,c,d,a,x[k+2], S34,0xC4AC5665);
		a=II(a,b,c,d,x[k+0], S41,0xF4292244);
		d=II(d,a,b,c,x[k+7], S42,0x432AFF97);
		c=II(c,d,a,b,x[k+14],S43,0xAB9423A7);
		b=II(b,c,d,a,x[k+5], S44,0xFC93A039);
		a=II(a,b,c,d,x[k+12],S41,0x655B59C3);
		d=II(d,a,b,c,x[k+3], S42,0x8F0CCC92);
		c=II(c,d,a,b,x[k+10],S43,0xFFEFF47D);
		b=II(b,c,d,a,x[k+1], S44,0x85845DD1);
		a=II(a,b,c,d,x[k+8], S41,0x6FA87E4F);
		d=II(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
		c=II(c,d,a,b,x[k+6], S43,0xA3014314);
		b=II(b,c,d,a,x[k+13],S44,0x4E0811A1);
		a=II(a,b,c,d,x[k+4], S41,0xF7537E82);
		d=II(d,a,b,c,x[k+11],S42,0xBD3AF235);
		c=II(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
		b=II(b,c,d,a,x[k+9], S44,0xEB86D391);
		a=AddUnsigned(a,AA);
		b=AddUnsigned(b,BB);
		c=AddUnsigned(c,CC);
		d=AddUnsigned(d,DD);
	}

	var temp = WordToHex(a)+WordToHex(b)+WordToHex(c)+WordToHex(d);	
	return temp.toLowerCase();
}

function checkNameField(e){
	try {
    	var unicode=e.charCode? e.charCode : e.keyCode;		              
        if((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123) || unicode == 8 || unicode == 32 || unicode== 46) {
        	return true;
        }else {
            return false; 
        }
    }catch (e) {		         
	}
}
function checkNonSpecialCharacter(e){
	try {
		var unicode=e.charCode? e.charCode : e.keyCode;	               
       	if((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123)|| (unicode>=48 && unicode<=57)) {
           	return true;
		}else{ 
	        return false;               
		}
	}catch (e) {	         
	}		
}
function checkOnlyNumeric(e){
	try {
		var unicode=e.charCode? e.charCode : e.keyCode;           
       	if(unicode>=48 && unicode<=57) {
           	return true;
		}else{ 
	        return false;               
		}
	}catch (e) {	         
	}		
}
function checkOnlyDecimal(e){
	try {
		var unicode=e.charCode? e.charCode : e.keyCode;      
       	if((unicode>=48 && unicode<=57)||unicode==46) {
           	return true;
		}else{ 
	        return false;               
		}
	}catch (e) {	         
	}		
}
function checkOnlyAlphabet(e){
	try {
		var unicode=e.charCode? e.charCode : e.keyCode;
       	if((unicode > 64 && unicode < 91) || (unicode > 96 && unicode < 123)) {
           	return true;
		}else{ 
	        return false;               
		}
	}catch (e) {	         
	}		
}
//For Displaying Message at the Status Bar
function setStatusBarMessage(message){
	window.status = message;				
}
//Added 13-03-2013 by Matiur Rahman To Shift a date to a particular day passed
function shiftDate(dateSring,shift){
	var txtDate=dateSring,dd=txtDate.substring(0,2),mm=txtDate.substring(3,5),yyyy=txtDate.substring(6,10),mSeconds=(new Date(yyyy,mm-1,dd)).getTime(),objDate=new Date();
	objDate.setTime(mSeconds+86400000*shift);
	mm=objDate.getMonth()+1;
	dd=objDate.getDate();
	yyyy=objDate.getFullYear();
	if(mm<10){
		mm="0"+mm;
	}
	if(dd<10){
		dd="0"+dd;
	}
	txtDate=dd+'/'+mm+'/'+yyyy;
	return txtDate;
}
function roundOffDecimal(inputValue,noOfDigits){
	switch(noOfDigits){
		case 1:return Math.round(inputValue*10)/10;
		case 2:return Math.round(inputValue*100)/100;
		case 3:return Math.round(inputValue*1000)/1000;
		case 4:return Math.round(inputValue*10000)/10000;
		case 5:return Math.round(inputValue*100000)/100000;
		case 6:return Math.round(inputValue*1000000)/1000000;
		default: return inputValue;
	}	
}
//Added 13-03-2013 by Matiur Rahman To set Cookies
function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}
//Added 13-03-2013 by Matiur Rahman To get Cookies
function getCookie(c_name) {
    var i, x, y, ARRcookies = document.cookie.split(";");
    for (i = 0; i < ARRcookies.length; i++) {
        x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
        y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
        x = x.replace(/^\s+|\s+$/g, "");
        if (x == c_name) {
            return unescape(y);
        }
    }
}
//Added 13-03-2013 by Matiur Rahman To Delete Cookie
function DeleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
}
//Added 13-03-2013 by Matiur Rahman To show Ajax Loading
function showAjaxLoading(obj){
		obj.style.display='block';
}
//Added 13-03-2013 by Matiur Rahman To hide Ajax Loading
function hideAjaxLoading(obj){
	obj.style.display='none';
}
function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function checkEmail(obj){
	var email=obj.value;	
	if(email.length>0){
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var isValid= re.test(email);
		if (!isValid){
			if(document.getElementById('onscreenMessage')){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter a valid Email Id.</font>";
			}else{
				alert('Please Enter a valid Email Id');
			}
			obj.focus();
			return false;
	  	}else{
	  		if(document.getElementById('onscreenMessage')){
				document.getElementById('onscreenMessage').innerHTML = "";
			}
			return true;
		}
	}
}
//Added 12-08-2013 by Matiur Rahman To validated Comma Separated Numeric Values
function fnValidateCommaSepartedListOfNumber(obj){
	obj.value = (obj.value).replace(/[\r\n ]/g, "");
	var commaSepartedNumberRegex=/^[0-9,]*$/;
	if (!commaSepartedNumberRegex.test(obj.value)) {
		if(document.getElementById('onscreenMessage')){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Please Enter Only Numeric value Separated by Comma.</font>";
		}else{
			alert('Please Enter Only Numeric value Separated by Comma.');
		}
	    return false;
	}else if(document.getElementById('onscreenMessage')&&(document.getElementById('onscreenMessage').innerHTML).indexOf("Please Enter Only Numeric value Separated by Comma")>-1){
		document.getElementById('onscreenMessage').innerHTML = "";
	}
	return true;
}
//Added 29-02-2016 by Rajib Hazarika To check whether a substring is present in the specific comma seperated string  
function isSubStr(str, splitChar, subStr){
	var result= false;
	if (str != ''){
		var splittedVal= str.split(splitChar); 
		
		for(var i=0;i< splittedVal.length;i++ ){
			if (splittedVal[i] == subStr){
				result= true;
				break;
			}
		}
	}else{
		result= false;
	}
	return result;
}
