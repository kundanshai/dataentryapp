<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
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
	<script type="text/javascript" src="<s:url value="/js/MRDMeterReplacement.js"/>"></script>	
	<script type="text/javascript">		
		var todaysDate="<%=(String)session.getAttribute("TodaysDate")%>";
		var searchZone="<%=session.getAttribute("searchZone") %>";
		var searchMRNo="<%=session.getAttribute("searchMRNo") %>";
		var searchArea="<%=session.getAttribute("searchArea") %>";
		var searchAreaCode="<%=session.getAttribute("searchAreaCode") %>";
 	
		var zoneList="<%=session.getAttribute("zoneList")%>";
		var mrNoList="<%=session.getAttribute("mrNoList")%>";
		var areaList="<%=session.getAttribute("areaList")%>";
		var lastSubmit="<%=session.getAttribute("LAST_SUBMIT")%>";
		var jsArrayMRNoList = []; 
		<%
		ArrayList mrNoList=(ArrayList)session.getAttribute("mrNoList");
		if(null!=mrNoList){
			for(int i=0;i<mrNoList.size();i++){%>     
				jsArrayMRNoList.push("<%= mrNoList.get(i)%>"); 
		<%}}else{%>
			document.location.href="dataEntry.action";
		<%}%>
		var jsArrayAreaList = []; 
		<%
		ArrayList areaList=(ArrayList)session.getAttribute("areaList");
		if(null!=areaList){
			for(int i=0;i<areaList.size();i++){%>     
				jsArrayAreaList.push("<%= areaList.get(i)%>"); 
		<%}}else{%>
			document.location.href="dataEntry.action";
		<%}%>
 		function fnOnSubmit(){
 			hideScreen();
 			return true;
 		}
 		function fnCheckZone(obj){
 	 		//alert(document.forms[0].zoneMR.value+'<<<>>>'+obj.value);
 	 		if(document.forms[0].zoneMR.value==''){
 	 	 		//alert('Please Select Zone First');
 	 	 		document.getElementById('mrMessage').innerHTML="<font color='red' size='2'><b>Please Select Zone First</b></font>";
 	 	 		if(!(document.forms[0].zoneMR.disabled)){
 	 				document.forms[0].zoneMR.focus();
 	 	 		}
 	 			return;
 	 		}
 		}
 		function fnCheckZoneMRNo(obj){
 	 		//alert(document.forms[0].zoneMR.value+'<<<>>>'+document.forms[0].mrNoMR.value);
 	 		if(document.forms[0].zoneMR.value==''){
 	 	 		//alert('Please Select Zone First');
 	 	 		document.getElementById('mrMessage').innerHTML="<font color='red' size='2'><b>Please Select Zone First</b></font>";
 	 	 		if(!(document.forms[0].zoneMR.disabled)){
 	 				document.forms[0].zoneMR.focus();
 	 	 		}
 	 			return;
 	 		}
 	 		if(document.forms[0].mrNoMR.value==''){
 	 	 		//alert('Please Select MR No. First');
 	 	 		document.getElementById('mrMessage').innerHTML="<font color='red' size='2'><b>Please Select MR No. First</b></font>";
 	 	 		if(!(document.forms[0].mrNoMR.disabled)){
 	 				document.forms[0].mrNoMR.focus();
 	 	 		}
 	 			return;
 	 		}
 		}
 		function fnSetMRNo(obj){ 	 		
 	 		var optionSelected=obj.value;
 	 		optionSelected=optionSelected.substring(optionSelected.indexOf("-(")+2,optionSelected.indexOf(")"));
 	 		//alert(optionSelected);
 	 		var objSelect=document.forms[0].mrNoMR; 
 	 		objSelect.options.length=1;	 		
 	 		for(i =0; i < jsArrayMRNoList.length ;i++) { 
				var currentKeyVal = jsArrayMRNoList[ i ];				
				if(currentKeyVal.indexOf(optionSelected)!=-1){
					var newOption = document.createElement('option');					
					newOption.value=currentKeyVal;
					newOption.innerText =currentKeyVal.substring(0,currentKeyVal.indexOf("-("));
					objSelect.appendChild(newOption);	
				}		
 	 		} 
 		} 		
		function fnSetArea(obj){ 	 		
 	 		var optionSelected=obj.value;
 	 		optionSelected=optionSelected.substring(optionSelected.indexOf("-(")+2,optionSelected.indexOf(")"));
 	 		//alert(optionSelected);
 	 		var objSelect=document.forms[0].areaMR; 
 	 		objSelect.options.length=1;	 		
 	 		for(i =0; i < jsArrayAreaList.length ;i++) { 
				var currentKeyVal = jsArrayAreaList[ i ];				
				if(currentKeyVal.indexOf(optionSelected)!=-1){
					var newOption = document.createElement('option');					
					newOption.value=currentKeyVal;
					newOption.innerText =currentKeyVal.substring(0,currentKeyVal.indexOf("-("));
					objSelect.appendChild(newOption);	
				}		
 	 		} 
 		}	
		function fnOnloadMR(){
			document.getElementById('waterConnectionSizeMR').value="15";
			document.getElementById('badgeNoMR').value="NA";
			document.getElementById('zeroReadMR').value="0";
			document.getElementById('zeroReadConfirmMR').value="0";
		}	
 	</script>
 	<% 
		String dataSaved=(String)session.getAttribute("DataSaved");	
		if(null==dataSaved){
			dataSaved="";
		}
		String buttonClicked=(String)session.getAttribute("buttonClicked");	
		String currentYear=PropertyUtil.getProperty("CURRENT_YEAR");
        ArrayList meterReadStatusList=(ArrayList)session.getAttribute("meterReadStatusList");
			                	
	%>		 	
</head>
<body onload="fnOnloadMR();" oncontextmenu="return false;" style="overflow-x:hidden">
<%@include file="../jsp/CommonOverlay.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">				
			<%@include file="../jsp/MRDMeterReplacement.html"%>					
		</td>
	</tr>	
	<tr height="20px">
		<td align="center" valign="bottom">
			<%@include file="../jsp/Footer.html"%>
		</td>
	</tr>
</table> 
	<script type="text/javascript">		
		var currentPage="1";
		displayMRScreen();
		
	</script>
</body>
<%}catch(Exception e){
%>	
<script type="text/javascript">
document.location.href="dataEntry.action";
</script>
<%e.printStackTrace();} %>
</html>