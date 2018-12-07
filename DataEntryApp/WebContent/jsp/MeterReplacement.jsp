<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%try{ %>
<head>
	<title>Meter Replacement- Revenue Management System, Delhi Jal Board</title>
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
		var toUpdateKNO="";	
		var todaysDate="<%=(String)session.getAttribute("TodaysDate")%>";
		var searchZone="<%=session.getAttribute("searchZone") %>";
		var searchMRNo="<%=session.getAttribute("searchMRNo") %>";
		var searchArea="<%=session.getAttribute("searchArea") %>";
		var searchAreaCode="<%=session.getAttribute("searchAreaCode") %>";
		var lastSubmit="<%=session.getAttribute("LAST_SUBMIT")%>";		
		var mrReview="<%=session.getAttribute("MRReview")%>";	
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
			var hidActionPopup=document.getElementById('hidActionPopup').value;
			if('getDetailsForPOPUP'==hidActionPopup){
				isPopUpWindow=true;
				if(document.getElementById('btnCloseSpan')){
					document.getElementById('btnCloseSpan').innerHTML="<input type='button' id='btnClose' value=' Close ' class='groovybutton' onclick='javascript:fnClosePOPUPWindow();' title='Click to Close this window.'/>";
				}
				if(document.getElementById('slNo')){
					document.getElementById('slNo').value=document.getElementById('seqNoPopup').value;
				}
				if(document.getElementById('meterReaderName')){
					document.getElementById('meterReaderName').value=document.getElementById('meterReaderNamePopup').value;
				}
				if(document.getElementById('billRoundMR')){
					document.getElementById('billRoundMR').value=document.getElementById('billRoundPopup').value;
					document.getElementById('billRoundMR').disabled=true;
				}
				if(document.getElementById('zoneMR')){
					document.getElementById('zoneMR').value=document.getElementById('zonePopup').value;
					document.getElementById('zoneMR').disabled=true;
				}
				if(document.getElementById('mrNoMR')){
					var select = document.getElementById("mrNoMR");
					select.options[select.options.length] = new Option(document.getElementById('mrNoPopup').value,document.getElementById('mrNoPopup').value); 
					document.getElementById('mrNoMR').value=document.getElementById('mrNoPopup').value;
					document.getElementById('mrNoMR').disabled=true;
				}
				if(document.getElementById('areaMR')){
					var select = document.getElementById("areaMR");
					select.options[select.options.length] = new Option(document.getElementById('areaPopup').value,document.getElementById('areaPopup').value); 
					document.getElementById('areaMR').value=document.getElementById('areaPopup').value;
					document.getElementById('areaMR').disabled=true;
				}
				if(document.getElementById('wcNoMR')){
					document.getElementById('wcNoMR').value=document.getElementById('wcNoPopup').value;
					document.getElementById('wcNoMR').disabled=true;
				}
				if(document.getElementById('knoMR')){
					document.getElementById('knoMR').value=document.getElementById('knoPopup').value;
					document.getElementById('knoMR').disabled=true;
				}
				if(document.getElementById('nameMR')){
					document.getElementById('nameMR').value=document.getElementById('namePopup').value;
				}
				if(document.getElementById('waterConnectionUseMR') && document.getElementById('waterConnectionUsePopup')&& document.getElementById('waterConnectionUsePopup').value.length>0){
					document.getElementById('waterConnectionUseMR').value=document.getElementById('waterConnectionUsePopup').value;
				}else{
					document.getElementById('waterConnectionUseMR').value="";
				}
				if(document.getElementById('waterConnectionSizeMR')){
					document.getElementById('waterConnectionSizeMR').value=document.getElementById('waterConnectionSizePopup').value;
				}
				if(document.getElementById('meterType')&&document.getElementById('meterTypePopup') && document.getElementById('meterTypePopup').value.length>0){
					document.getElementById('meterType').value=document.getElementById('meterTypePopup').value;
				}else{
					document.getElementById('meterType').value="";
				}
				if(document.getElementById('badgeNoMR')){
					document.getElementById('badgeNoMR').value=document.getElementById('badgeNoPopup').value;
				}
				if(document.getElementById('manufacturerMR')&& document.getElementById('manufacturerPopup')&& document.getElementById('manufacturerPopup').value.length>0){
					document.getElementById('manufacturerMR').value=document.getElementById('manufacturerPopup').value;
				}else{
					document.getElementById('manufacturerMR').value="";
				}
				if(document.getElementById('modelMR')&&document.getElementById('modelPopup')&&document.getElementById('modelPopup').value.length>0){
					document.getElementById('modelMR').value=document.getElementById('modelPopup').value;
				}else{
					document.getElementById('modelMR').value="";
				}
				if(document.getElementById('consumerTypeMR')){
					document.getElementById('consumerTypeMR').value=document.getElementById('consumerTypePopup').value;
				}
				if(document.getElementById('consumerCategoryMR')){
					document.getElementById('consumerCategoryMR').value=document.getElementById('consumerCategoryPopup').value;
				}
				if(document.getElementById('saType')){
					if(document.getElementById('consumerTypeMR').value=='UNMETERED'){
						document.getElementById('saType').value=document.getElementById('saTypePopup').value;
					}else{
						document.getElementById('saType').value="NA";
						document.getElementById('saType').disabled=true;
					}
				}
				if(document.getElementById('meterInstalDate')){
					document.getElementById('meterInstalDate').value=document.getElementById('meterInstalDatePopup').value;
					document.getElementById('meterInstalDateConfirm').value=document.getElementById('meterInstalDatePopup').value;
					meterInstalDate=document.getElementById('meterInstalDate').value;					
				}
				if(document.getElementById('zeroReadMR')){
					document.getElementById('zeroReadMR').value=document.getElementById('zeroReadPopup').value;
					document.getElementById('zeroReadConfirmMR').value=document.getElementById('zeroReadPopup').value;
					zeroReadMR=document.getElementById('zeroReadMR').value;
				}
				if(document.getElementById('currentMeterReadDateMR')){
					document.getElementById('currentMeterReadDateMR').value=document.getElementById('currentMeterReadDatePopup').value;
					document.getElementById('currentMeterReadDateConfirmMR').value=document.getElementById('currentMeterReadDatePopup').value;
					currentMeterReadDateMR=document.getElementById('currentMeterReadDateMR').value;
				}
				if(document.getElementById('currentMeterReadRemarkCode')&&document.getElementById('currentMeterReadRemarkCodePopup')&&document.getElementById('currentMeterReadRemarkCodePopup').value.length>0){
					document.getElementById('currentMeterReadRemarkCode').value=document.getElementById('currentMeterReadRemarkCodePopup').value;
					setMeterReadStatusMR(document.getElementById('currentMeterReadRemarkCode'));
				}else{
					document.getElementById('currentMeterReadRemarkCode').value="";
					setMeterReadStatusMR(document.getElementById('currentMeterReadRemarkCode'));
				}
				if(document.getElementById('currentMeterRegisterReadMR')){
					document.getElementById('currentMeterRegisterReadMR').value=document.getElementById('currentMeterRegisterReadPopup').value;
					document.getElementById('currentMeterRegisterReadConfirmMR').value=document.getElementById('currentMeterRegisterReadPopup').value;
					currentMeterRegisterReadMR=document.getElementById('currentMeterRegisterReadMR').value;
				}
				if(document.getElementById('currentAverageConsumptionMR')){
					document.getElementById('currentAverageConsumptionMR').value=document.getElementById('currentAverageConsumptionPopup').value;
				}
				if(document.getElementById('newAverageConsumptionMR')){
					document.getElementById('newAverageConsumptionMR').value=document.getElementById('newAverageConsumptionPopup').value;
					document.getElementById('newAverageConsumptionConfirmMR').value=document.getElementById('newAverageConsumptionPopup').value;
					newAverageConsumptionMR=document.getElementById('newAverageConsumptionMR').value;
				}
				if(document.getElementById('oldMeterReadRemarkCode')&&document.getElementById('oldMeterReadRemarkCodePopup')&& document.getElementById('oldMeterReadRemarkCodePopup').value.length>0){
					document.getElementById('oldMeterReadRemarkCode').value=document.getElementById('oldMeterReadRemarkCodePopup').value;
				}else{
					document.getElementById('oldMeterReadRemarkCode').value="";
				}
				if(document.getElementById('oldSATypeMR')){
					document.getElementById('oldSATypeMR').value=document.getElementById('oldSATypePopup').value;
				}
				if(document.getElementById('oldMeterRegisterReadMR')){
					document.getElementById('oldMeterRegisterReadMR').value=document.getElementById('oldMeterRegisterReadPopup').value;
					document.getElementById('oldMeterRegisterReadConfirmMR').value=document.getElementById('oldMeterRegisterReadPopup').value;
					oldMeterRegisterReadMR=document.getElementById('oldMeterRegisterReadMR').value;
				}
				if(document.getElementById('oldMeterAverageReadMR')){
					document.getElementById('oldMeterAverageReadMR').value=document.getElementById('oldMeterAverageReadPopup').value;
					document.getElementById('oldMeterAverageReadConfirmMR').value=document.getElementById('oldMeterAverageReadPopup').value;
					oldMeterAverageReadMR=document.getElementById('oldMeterAverageReadMR').value;
				}				
				/*if(document.getElementById('consumerStatusMR')){
					document.getElementById('consumerStatusMR').value=document.getElementById('waterConnectionUsePopup').value;
				}*/
				if(document.getElementById('isLNTServiceProvider')){
					document.getElementById('isLNTServiceProvider').value=document.getElementById('isLNTServiceProviderPopup').value;
				}
				if(document.getElementById('btnSaveMR')){
					document.getElementById('btnSaveMR').disabled=true;
				}
				if((document.getElementById('knoMR').value).length==10){
					fnGetConsumerDetailsByKNO();
				}
			}else{
				if(document.getElementById('waterConnectionSizeMR')){
					document.getElementById('waterConnectionSizeMR').value="15";
				}
				if(document.getElementById('badgeNoMR')){
					document.getElementById('badgeNoMR').value="NA";
				}
				if(document.getElementById('zeroReadMR')){
					document.getElementById('zeroReadMR').value="0";
				}
				if(document.getElementById('zeroReadConfirmMR')){
					document.getElementById('zeroReadConfirmMR').value="0";
				}
			}
			if(document.getElementById('zeroReadRemarkCode')){
				document.getElementById('zeroReadRemarkCode').value="OK";
				document.getElementById('zeroReadRemarkCode').disabled=true;
			}
			
		}
		function fnClosePOPUPWindow(){
			window.close();
		}
		window.onunload = function(){
		    if (window.opener && !window.opener.closed &&isPopUpWindow) {
			    if(document.getElementById('hidActionMR')&&(document.getElementById('hidActionMR').value=='saveDetails'||document.getElementById('hidActionMR').value=='resetMRDetails')){
					if(mrReview=='Y'){
						var url="meterReplacementReview.action?hidAction=search";
						var MR_kno="<%=session.getAttribute("MR_kno")%>";
						var mrKeyList="<%=session.getAttribute("MR_mrKeyList")%>";
						var MR_zoneMR="<%=session.getAttribute("MR_zoneMR")%>";
						var MR_mrNoMR="<%=session.getAttribute("MR_mrNoMR")%>";
						var MR_areaMR="<%=session.getAttribute("MR_areaMR")%>";
						var MR_maxRecordPerPage="<%=session.getAttribute("MR_maxRecordPerPage")%>";
						var MR_pageNo="<%=session.getAttribute("MR_pageNo")%>";
						var MR_searchFor="<%=session.getAttribute("MR_searchFor")%>";
						if(MR_kno!=null&&MR_kno!='null'){
							url+="&knoMR="+MR_kno;
						}
						if(mrKeyList!=null&&mrKeyList!='null'){
							url+="&mrKeyList="+mrKeyList;
						}
						if(MR_zoneMR!=null&&MR_zoneMR!='null'){
							url+="&zoneMR="+MR_zoneMR;
						}
						if(MR_mrNoMR!=null&&MR_mrNoMR!='null'){
							url+="&mrNoMR="+MR_mrNoMR;
						}
						if(MR_areaMR!=null&&MR_areaMR!='null'){
							url+="&areaMR="+MR_areaMR;
						}
						if(MR_maxRecordPerPage!=null&&MR_maxRecordPerPage!='null'){
							url+="&maxRecordPerPage="+MR_maxRecordPerPage;
						}
						if(MR_pageNo!=null&&MR_pageNo!='null'){
							url+="&pageNo="+MR_pageNo;
						}
						if(MR_searchFor!=null&&MR_searchFor!='null'){
							url+="&searchFor=";
						}
						//alert(url);						
						window.opener.location.href = url;
					}else{
		    			window.opener.location.href = "dataEntry.action?hidAction=refreshMRDReview";  
					}
			    }  	 
		    }
		};	
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
<body onload="fnOnloadMR();" oncontextmenu="return true" style="overflow-x:hidden">
<%@include file="../jsp/CommonOverlay.html"%>
	<table class="djbmain" id="djbmaintable">
		<tr height="20px">
				<td align="center" valign="top"><%@include
				file="../jsp/Header.html"%></td>
		</tr>
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
	<s:form action="meterReplacement.action" method="post">
		<s:hidden name="hidAction" id="hidActionPopup"/>
		<s:hidden name="meterReaderName" id="meterReaderNamePopup"/>
		<s:hidden name="seqNo" id="seqNoPopup"/>											
		<s:hidden name="kno" id="knoPopup"/>	
		<s:hidden name="billRound" id="billRoundPopup"/>		
		<s:hidden name="zone" id="zonePopup"/>	
		<s:hidden name="mrNo" id="mrNoPopup"/>	
		<s:hidden name="area" id="areaPopup"/>	
		<s:hidden name="wcNo" id="wcNoPopup"/>		
		<s:hidden name="name" id="namePopup"/>		
		<s:hidden name="consumerType" id="consumerTypePopup"/>	
		<s:hidden name="consumerCategory" id="consumerCategoryPopup"/>			
		<s:hidden name="waterConnectionSize" id="waterConnectionSizePopup"/>	
		<s:hidden name="waterConnectionUse" id="waterConnectionUsePopup"/>		
		<s:hidden name="meterType" id="meterTypePopup"/>		
		<s:hidden name="badgeNo" id="badgeNoPopup"/>
		<s:hidden name="manufacturer" id="manufacturerPopup"/>	
		<s:hidden name="model" id="modelPopup"/>	
		<s:hidden name="saType" id="saTypePopup"/>	
		<s:hidden name="isLNTServiceProvider" id="isLNTServiceProviderPopup"/>		
		<s:hidden name="meterInstalDate" id="meterInstalDatePopup"/>	
		<s:hidden name="zeroRead" id="zeroReadPopup"/>	
		<s:hidden name="currentMeterReadDate" id="currentMeterReadDatePopup"/>	
		<s:hidden name="currentMeterRegisterRead" id="currentMeterRegisterReadPopup"/>	
		<s:hidden name="currentMeterReadRemarkCode" id="currentMeterReadRemarkCodePopup"/>
		<s:hidden name="currentAverageConsumption" id="currentAverageConsumptionPopup"/>			
		<s:hidden name="newAverageConsumption" id="newAverageConsumptionPopup"/>				
		<s:hidden name="oldMeterRegisterRead" id="oldMeterRegisterReadPopup"/>	
		<s:hidden name="oldSAType" id="oldSATypePopup"/>	
		<s:hidden name="oldMeterReadRemarkCode" id="oldMeterReadRemarkCodePopup"/>	
		<s:hidden name="oldMeterAverageRead" id="oldMeterAverageReadPopup"/>
		<s:hidden name="searchForMR" id="searchForMR"/>
	</s:form>
	<script type="text/javascript">		
		var currentPage="1";
		displayMRScreen();
		
	</script>
	<script type="text/javascript">	
		$(".textbox").focus(function() { 
			$(this).removeClass("textbox"); 
			$(this).addClass("textboxfocus"); 
		});
		$(".textbox").blur(function() {	
			$(this).removeClass("textboxfocus"); 
			$(this).addClass("textbox"); 
		});		   
		$(".textbox-long").focus(function() { 
			$(this).removeClass("textbox-long"); 
			$(this).addClass("textbox-longfocus"); 
		}); 
		$(".textbox-long").blur(function() {
			$(this).removeClass("textbox-longfocus"); 
			$(this).addClass("textbox-long"); 
		});
		function clearAllFields(){
			var tempHidActionMR=document.getElementById('hidActionMR').value;
			$('#MR')[0].reset();
			document.getElementById('hidActionMR').value=tempHidActionMR;
		}
	</script>
</body>
<%}catch(Exception e){
	//e.printStackTrace();
%>
</script>	
<script type="text/javascript">
document.location.href="logout.action";
</script>
<%} %>
</html>