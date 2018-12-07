<!-- 

@history Added new chracteristics feild and category for ground water Billing
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<%@ page language="java" import="com.tcs.djb.constants.*"%>
<html>
<%
	try {
%>

<head>
<title>New Connection Data Alteration Form - Revenue Management
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
	src="<s:url value="/js/NewConnection.js"/>"></script>
<script language=javascript>
       function disableBack()
		{
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
	 	var noOfAdultMaxLength="<%=DJBConstants.FIELD_NO_OF_ADULT_MAX_LENGTH%>";
	 	var noOfChildMaxLength="<%=DJBConstants.FIELD_NO_OF_CHILD_MAX_LENGTH%>";
	 	/*Start : Atanu added the below constant to restrict the max number of borewells. As per jTrac DJB-4211*/
	 	var maxNoOfBorewells="<%=DJBConstants.MAX_NO_OF_BOREWELLS%>";
	 	/*End : Atanu added the below constant to restrict the max number of borewells. As per jTrac DJB-4211*/
	 	var maxFloorValue="<%=session.getAttribute("maxFloorValue")%>";
	 	var todaysDate="<%=(String) session.getAttribute("TodaysDate")%>";	
	 	//Start-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	 	function fnProcessDAF(){
	 		if(document.getElementById('declaration').checked){
			 	if(confirm('This action will Process the DAF and an Account will Be Created in the System.\nYou have declared that all data has been checked & verified by You and found correct.\nYou are solely accountable for this action.\nIf You are Sure Click OK else Cancel. ')){
			 		var url= "newConnectionDAFAJax.action?hidAction=processNewConnectionDAFDetails";
			 		url+="&dafSequence="+Trim(document.forms[0].dafSequence.value);
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
						document.getElementById('newConMessage').innerHTML = "<font color='green' size='2'><b>New Connection Details Submitted for Processing. Please wait while Proccessing.</b></font>";
						hideScreen();             
						httpBowserObject.open("POST", url, true);
						httpBowserObject.setRequestHeader("Content-type",
								"application/x-www-form-urlencoded");
						httpBowserObject.setRequestHeader("Connection", "close");
						httpBowserObject.onreadystatechange = function() {
							if (httpBowserObject.readyState == 4) {
								var responseString = httpBowserObject.responseText;
								//alert(httpBowserObject.responseText);
								if (null != responseString
										&& responseString.indexOf('<script') == -1) {
									if (null == responseString
											|| responseString.indexOf("ERROR:") > -1) {
										document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
										showScreen();
									}else{
										document.getElementById('newConMessage').innerHTML = responseString;
										if(responseString.indexOf("New Connection Processed Successfully") > -1){
											document.getElementById('newconDAFDiv').innerHTML = "<p align=\"center\"><input type=\"button\" name=\"btnBack\" id=\"btnBack\" value=\"Back\" class=\"groovybutton\" onclick=\"fnGoBackToSearch();\" title=\"Click to Go Back To Search Page\"/></p>";
										}					
										showScreen();
									}
								}else {
									document.getElementById('newConMessage').innerHTML = responseString;
									showScreen();
								}
							}					
						};
						httpBowserObject.send(null);
					}
			 	}
	 		}else{
		 		alert('Please Tick to agree with the Declaration to Proceed.');		
		 		document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>Please Tick to agree with the Declaration to Proceed.</b></font>";
		 	}
	 	}
	 	function fnSaveNewConnectionDAFDetails(){
		 	if(document.getElementById('declaration').checked){
		 		var url= "newConnectionDAFAJax.action?hidAction=saveNewConnectionDAFDetails"; 	
			 	//url+="&accountId="+Trim(document.forms[0].accountId.value);
		 		url+="&applicationPurpose="+Trim(document.forms[0].applicationPurpose.value);
		 		url+="&areaNo="+Trim(document.forms[0].areaNo.value);
		 		url+="&averageConsumption="+Trim(document.forms[0].averageConsumption.value);
		 		url+="&builtUpArea="+Trim(document.forms[0].builtUpArea.value);
		 		url+="&entityName="+Trim(document.forms[0].entityName.value);
		 		url+="&dafId="+Trim(document.forms[0].dafId.value);
		 		url+="&fatherHusbandName="+Trim(document.forms[0].fatherHusbandName.value);
		 		url+="&functionSite="+Trim(document.forms[0].functionSite.value);
		 		url+="&houseNumber="+Trim(document.forms[0].houseNumber.value);
		 		//Start: Commented By Diksha Mukherjee on 8th March,2016 as per JTrac DJB-4211
		 		//url+="&initialRegisetrRead="+Trim(document.forms[0].initialRegisetrRead.value);
		 		//url+="&initialRegisterReadDate="+Trim(document.forms[0].initialRegisterReadDate.value);
		 		//url+="&initialRegisterReadRemark="+Trim(document.forms[0].initialRegisterReadRemark.value);
		 		//End: Commented By Diksha Mukherjee on 8th March,2016 as per JTrac DJB-4211
		 		url+="&isDJB="+Trim(document.forms[0].isDJB.value);
		 		url+="&jjrColony="+document.forms[0].jjrColony.checked;
		 		url+="&khashraNumber="+Trim(document.forms[0].khashraNumber.value);
		 		url+="&locality="+Trim(document.forms[0].locality.value);
		 		url+="&mallCineplex="+Trim(document.forms[0].mallCineplex.value);
		 		url+="&mrNo="+Trim(document.forms[0].mrNo.value);
		 		url+="&noOfAdults="+Trim(document.forms[0].noOfAdults.value);
		 		url+="&noOfBeds="+Trim(document.forms[0].noOfBeds.value);
		 		url+="&noOfChildren="+Trim(document.forms[0].noOfChildren.value);
		 		url+="&noOfFloors="+Trim(document.forms[0].noOfFloors.value);
		 		/*Start: Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		url+="&noOfBorewells="+Trim(document.forms[0].noOfBorewells.value);
		 		/*End: Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		url+="&noOfOccDwellUnits="+Trim(document.forms[0].noOfOccDwellUnits.value);
		 		url+="&noOfRooms="+Trim(document.forms[0].noOfRooms.value);
		 		url+="&noOfUnoccDwellUnits="+Trim(document.forms[0].noOfUnoccDwellUnits.value);
		 		url+="&openingBalance="+Trim(document.forms[0].openingBalance.value);
		 		url+="&personIdNumber="+Trim(document.forms[0].personIdNumber.value);
		 		url+="&personIdType="+Trim(document.forms[0].personIdType.value);
		 		url+="&pinCode="+Trim(document.forms[0].pinCode.value);
		 		url+="&plotArea="+Trim(document.forms[0].plotArea.value);
		 		url+="&propertyType="+Trim(document.forms[0].propertyType.value);
		 		url+="&roadNumber="+Trim(document.forms[0].roadNumber.value);
		 		//url+="&saTypeCode="+Trim(document.forms[0].saTypeCode.value);
		 		//url+="&sequenceNo="+Trim(document.forms[0].sequenceNo.value);
		 		/*Start: Changed by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		//url+="&sizeOfMeter="+Trim(document.forms[0].sizeOfMeter.value);
		 		var selectedWaterConnectionType = document.forms[0].waterConnectionType.value;
		 		var sizeOfMtr = "";
		 		var intialRegisterRemark="";
		 		var initialRegistrDate="";
		 		var intialRegistrRead="";
		 		if('CATIIIA' == (Trim(selectedWaterConnectionType)) || 'CATIIIB' == (Trim(selectedWaterConnectionType))){
		 			sizeOfMtr = "";
		 			intialRegisterRemark="";
		 			initialRegistrDate="";
		 			intialRegistrRead="";
		 			
		 		}else{
		 			sizeOfMtr = Trim(document.forms[0].sizeOfMeter.value);
		 			intialRegisterRemark=Trim(document.forms[0].initialRegisterReadRemark.value);
		 			initialRegistrDate=Trim(document.forms[0].initialRegisterReadDate.value);
		 			intialRegistrRead=Trim(document.forms[0].initialRegisetrRead.value);
		 		}
		 		//alert('intialRegisterRemark '+intialRegisterRemark);
		 		//alert('intialRegisterDate '+initialRegistrDate);
		 		url+="&initialRegisterReadRemark="+intialRegisterRemark;
		 		url+="&sizeOfMeter="+sizeOfMtr;
		 		url+="&initialRegisterReadDate="+initialRegistrDate;
		 		url+="&initialRegisetrRead="+intialRegistrRead;
		 		/*End: Changed by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		url+="&societyName="+Trim(document.forms[0].societyName.value);
		 		url+="&subColony="+Trim(document.forms[0].subColony.value);
		 		url+="&subLocality="+Trim(document.forms[0].subLocality.value);
		 		url+="&sublocality1="+Trim(document.forms[0].sublocality1.value);
		 		url+="&sublocality2="+Trim(document.forms[0].sublocality2.value);
		 		url+="&typeOfMeter="+Trim(document.forms[0].typeOfMeter.value);
		 		url+="&urban="+document.forms[0].urban.checked;
		 		url+="&village="+Trim(document.forms[0].village.value);
		 		url+="&waterConnectionType="+Trim(document.forms[0].waterConnectionType.value);
		 		url+="&waterConnectionUse="+Trim(document.forms[0].waterConnectionUse.value);
		 		url+="&wcNo="+Trim(document.forms[0].wcNo.value);
		 		url+="&zoneNo="+Trim(document.forms[0].zoneNo.value);
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
					document.getElementById('newConMessage').innerHTML = "<font color='green' size='2'><b>New Connection Details Submitted. Please wait while Proccessing.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							if (null != responseString
									&& responseString.indexOf('<script') == -1) {
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
									showScreen();
								}else{
									document.getElementById('newConMessage').innerHTML = responseString;
									if(responseString.indexOf("New Connection Details Saved Successfully") > -1){
										document.getElementById('btnSubmit').disabled=true;
										document.getElementById('declaration').checked=false;
									}					
									showScreen();
								}
							}else {
								document.getElementById('newConMessage').innerHTML = responseString;
								showScreen();
							}
						}					
					};
					httpBowserObject.send(null);
				}
		 	}else{
		 		alert('Please Tick to agree with the Declaration to Proceed.');		
		 		document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>Please Tick to agree with the Declaration to Proceed.</b></font>";
		 	}
	 	}
	 	function fnUpdateNewConnectionDAFDetails(){
	 		if(document.getElementById('declaration').checked){
		 		document.getElementById('btnSubmit').disabled=true;
		 		var url= "newConnectionDAFAJax.action?hidAction=updateNewConnectionDAFDetails"; 	
			 	//url+="&accountId="+Trim(document.forms[0].accountId.value);
			 	url+="&dafSequence="+Trim(document.forms[0].dafSequence.value);
		 		url+="&applicationPurpose="+Trim(document.forms[0].applicationPurpose.value);
		 		url+="&areaNo="+Trim(document.forms[0].areaNo.value);
		 		url+="&averageConsumption="+Trim(document.forms[0].averageConsumption.value);
		 		url+="&builtUpArea="+Trim(document.forms[0].builtUpArea.value);
		 		url+="&entityName="+Trim(document.forms[0].entityName.value);
		 		url+="&dafId="+Trim(document.forms[0].dafId.value);
		 		url+="&fatherHusbandName="+Trim(document.forms[0].fatherHusbandName.value);
		 		url+="&functionSite="+Trim(document.forms[0].functionSite.value);
		 		url+="&houseNumber="+Trim(document.forms[0].houseNumber.value);
		 		/*Start: Commented by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		//url+="&initialRegisetrRead="+Trim(document.forms[0].initialRegisetrRead.value);
		 		//url+="&initialRegisterReadDate="+Trim(document.forms[0].initialRegisterReadDate.value);
		 		//url+="&initialRegisterReadRemark="+Trim(document.forms[0].initialRegisterReadRemark.value);
		 		/*End: Commented by Diksha Mukherjee on 8th March 2016 as per DJB_4211*/
		 		url+="&isDJB="+Trim(document.forms[0].isDJB.value);
		 		url+="&jjrColony="+document.forms[0].jjrColony.checked;
		 		url+="&khashraNumber="+Trim(document.forms[0].khashraNumber.value);
		 		url+="&locality="+Trim(document.forms[0].locality.value);
		 		url+="&mallCineplex="+Trim(document.forms[0].mallCineplex.value);
		 		url+="&mrNo="+Trim(document.forms[0].mrNo.value);
		 		url+="&noOfAdults="+Trim(document.forms[0].noOfAdults.value);
		 		url+="&noOfBeds="+Trim(document.forms[0].noOfBeds.value);
		 		url+="&noOfChildren="+Trim(document.forms[0].noOfChildren.value);
		 		url+="&noOfFloors="+Trim(document.forms[0].noOfFloors.value);
		 		/*Start : Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 */
		 		url+="&noOfBorewells="+Trim(document.forms[0].noOfBorewells.value);
		 		/*End :  Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 */
		 		url+="&noOfOccDwellUnits="+Trim(document.forms[0].noOfOccDwellUnits.value);
		 		url+="&noOfRooms="+Trim(document.forms[0].noOfRooms.value);
		 		url+="&noOfUnoccDwellUnits="+Trim(document.forms[0].noOfUnoccDwellUnits.value);
		 		url+="&openingBalance="+Trim(document.forms[0].openingBalance.value);
		 		url+="&personIdNumber="+Trim(document.forms[0].personIdNumber.value);
		 		url+="&personIdType="+Trim(document.forms[0].personIdType.value);
		 		url+="&pinCode="+Trim(document.forms[0].pinCode.value);
		 		url+="&plotArea="+Trim(document.forms[0].plotArea.value);
		 		url+="&propertyType="+Trim(document.forms[0].propertyType.value);
		 		url+="&roadNumber="+Trim(document.forms[0].roadNumber.value);
		 		//url+="&saTypeCode="+Trim(document.forms[0].saTypeCode.value);
		 		//url+="&sequenceNo="+Trim(document.forms[0].sequenceNo.value);
		 		/*Start : Changed by Diksha Mukherjee on 8th March 2016 as per DJB_4211 */
		 		var selectedWaterConnectionType = Trim(document.forms[0].waterConnectionType.value);
		 		var sizeOfMtr = "";
		 		var intialRegisterRemark="";
		 		var initialRegistrDate="";
		 		var intialRegistrRead="";
		 		if('CATIIIA' == (Trim(selectedWaterConnectionType)) || 'CATIIIB' == (Trim(selectedWaterConnectionType))){
		 			sizeOfMtr = "";
		 			intialRegisterRemark="";
		 			initialRegistrDate="";
		 			intialRegistrRead="";
		 			
		 		}else{
		 			sizeOfMtr = Trim(document.forms[0].sizeOfMeter.value);
		 			intialRegisterRemark=Trim(document.forms[0].initialRegisterReadRemark.value);
		 			initialRegistrDate=Trim(document.forms[0].initialRegisterReadDate.value);
		 			intialRegistrRead=Trim(document.forms[0].initialRegisetrRead.value);
		 		}
		 		//alert('intialRegisterRemark '+intialRegisterRemark);
		 		//alert('intialRegisterDate '+initialRegistrDate);
		 		url+="&initialRegisterReadRemark="+intialRegisterRemark;
		 		url+="&sizeOfMeter="+sizeOfMtr;
		 		url+="&initialRegisterReadDate="+initialRegistrDate;
		 		url+="&initialRegisetrRead="+intialRegistrRead;
		 		/*End: Diksha Mukherjee on 8th March 2016 as per DJB_4211*/		
		 		url+="&societyName="+Trim(document.forms[0].societyName.value);
		 		url+="&subColony="+Trim(document.forms[0].subColony.value);
		 		url+="&subLocality="+Trim(document.forms[0].subLocality.value);
		 		url+="&sublocality1="+Trim(document.forms[0].sublocality1.value);
		 		url+="&sublocality2="+Trim(document.forms[0].sublocality2.value);
		 		url+="&typeOfMeter="+Trim(document.forms[0].typeOfMeter.value);
		 		url+="&urban="+document.forms[0].urban.checked;
		 		url+="&village="+Trim(document.forms[0].village.value);
		 		url+="&waterConnectionType="+Trim(document.forms[0].waterConnectionType.value);
		 		url+="&waterConnectionUse="+Trim(document.forms[0].waterConnectionUse.value);
		 		url+="&wcNo="+Trim(document.forms[0].wcNo.value);
		 		url+="&zoneNo="+Trim(document.forms[0].zoneNo.value);
		 		//alert("Update url is >>"+url);
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
					document.getElementById('newConMessage').innerHTML = "<font color='green' size='2'><b>New Connection Details Submitted for Update. Please wait while Proccessing.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							if (null != responseString
									&& responseString.indexOf('<script') == -1) {
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
									showScreen();
									document.getElementById('btnSubmit').disabled=false;
								}else{
									document.getElementById('newConMessage').innerHTML = responseString;
									if(responseString.indexOf("New Connection Details Updated Successfully") > -1){
										document.getElementById('btnSubmit').disabled=true;
										document.getElementById('declaration').checked=false;
									}					
									showScreen();
								}
							}else {
								document.getElementById('newConMessage').innerHTML = responseString;
								showScreen();
								document.getElementById('btnSubmit').disabled=false;
							}
						}					
					};
					httpBowserObject.send(null);
				}
	 		}else{
		 		document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>Please Tick to agree with the Declaration to Proceed.</b></font>";
		 		alert('Please Tick to agree with the Declaration to Proceed.');		 		
		 	}
	 	}
	 	function fnValidateNewDAFForZoneMRAreaWCNo(){
		 	if(Trim(document.forms[0].areaNo.value)!='' && Trim(document.forms[0].mrNo.value)!='' &&Trim(document.forms[0].wcNo.value)!='' &&Trim(document.forms[0].zoneNo.value)!=''){
		 		var url= "newConnectionDAFAJax.action?hidAction=validateNewDAFForZoneMRAreaWCNo"; 		 		
		 		url+="&areaNo="+Trim(document.forms[0].areaNo.value);	 		
		 		url+="&mrNo="+Trim(document.forms[0].mrNo.value);
		 		url+="&wcNo="+Trim(document.forms[0].wcNo.value);
		 		url+="&zoneNo="+Trim(document.forms[0].zoneNo.value);
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
					document.getElementById('newConMessage').innerHTML = "<font color='green' size='2'><b>Validating New Connection Details if Submitted before. Please wait while Proccessing.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							if (null != responseString
									&& responseString.indexOf('<script') == -1) {
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
									showScreen();
								}else if(responseString.indexOf("VALID") == -1){
									document.getElementById('newConMessage').innerHTML = responseString;
									document.getElementById('btnSubmit').disabled=true;
									showScreen();
								}else{
									document.getElementById('btnSubmit').disabled=false;
									document.getElementById('newConMessage').innerHTML ="";
									showScreen();
									fnValidateNewDAFForZoneMRAreaConsumerNameFatherName();
								}
							}else {
								document.getElementById('newConMessage').innerHTML = responseString;
								showScreen();
							}
						}					
					};
					httpBowserObject.send(null);
				}
		 	}else{
		 		fnValidateNewDAFForZoneMRAreaConsumerNameFatherName();
		 	}
	 	}
	 	var similarDetails=false;
	 	function fnValidateNewDAFForZoneMRAreaConsumerNameFatherName(){
	 		if(Trim(document.forms[0].entityName.value)!=''&& Trim(document.forms[0].fatherHusbandName.value)!=''&& Trim(document.forms[0].areaNo.value)!='' && Trim(document.forms[0].mrNo.value)!=''&&Trim(document.forms[0].zoneNo.value)!=''){
		 		var url= "newConnectionDAFAJax.action?hidAction=validateNewDAFForZoneMRAreaConsumerNameFatherName"; 
		 		url+="&areaNo="+Trim(document.forms[0].areaNo.value);
		 		url+="&entityName="+Trim(document.forms[0].entityName.value);
		 		url+="&dafId="+Trim(document.forms[0].dafId.value);
		 		url+="&fatherHusbandName="+Trim(document.forms[0].fatherHusbandName.value);
		 		url+="&mrNo="+Trim(document.forms[0].mrNo.value);
		 		url+="&wcNo="+Trim(document.forms[0].wcNo.value);
		 		url+="&zoneNo="+Trim(document.forms[0].zoneNo.value);
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
					document.getElementById('newConMessage').innerHTML = "<font color='green' size='2'><b>Checking if similar New Connection DAF Details Submitted. Please wait while Proccessing.</b></font>";
					hideScreen();             
					httpBowserObject.open("POST", url, true);
					httpBowserObject.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function() {
						if (httpBowserObject.readyState == 4) {
							var responseString = httpBowserObject.responseText;
							//alert(httpBowserObject.responseText);
							if (null != responseString
									&& responseString.indexOf('<script') == -1) {
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('newConMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
									showScreen();
								}else if(responseString.indexOf("INVALID") > -1){
									similarDetails=true;
									document.getElementById('newConMessage').innerHTML = "<font color='orange' size='2'><b>Warning ! A request already Submited with the same combination of Name, Name of Father/Mother/Husband, Zone, MR No and Area.</b></font>";
									showScreen();
								}else{
									similarDetails=false;
									document.getElementById('newConMessage').innerHTML = "";
									showScreen();
								}
							}else {
								document.getElementById('newConMessage').innerHTML = responseString;
								showScreen();
							}
						}					
					};
					httpBowserObject.send(null);
				}
	 		}
	 	}
		function fnResetForm(){
			if(confirm('This will reset all field values to default.\nAre you sure you want to continue?\nClick OK to continue else Cancel.')){
				$('#DAF')[0].reset();
				document.getElementById('newConMessage').innerHTML="<font color=\"blue\">Please Enter the Mandatory Fields for New Connection DAF</font>";
				document.getElementById('btnSubmit').disabled=false;
				document.forms[0].personIdType.value="DL";		
				document.forms[0].isDJB.value="NOTDJB";
				document.forms[0].sizeOfMeter.value="15";
				document.forms[0].typeOfMeter.value="MTR_PVT";
				document.forms[0].initialRegisterReadRemark.value="OK";
				document.forms[0].applicationPurpose.value="SAWATSEW";
				$('input[type="text"]').each(function(){
				  	if(this.value == '') {
						this.value = $(this).attr('title');
					}	  
			  	});
			}
		}		
	 	function checkRefresh(e) {
		 	if(!gotoBack){
		 		//if IsRefresh cookie exists
		    	 var IsRefresh = getCookie("IsRefresh");
		    	 if (IsRefresh != null && IsRefresh != "") {
		    		 if(!e){ 
			    		 e = window.event;
			    	}
		 	 	    //e.cancelBubble is supported by IE - this will kill the bubbling process.
		 	 	    e.cancelBubble = true;
		 	 	    e.returnValue = 'Note : You will loose unsaved data.';
		 	 	 
		 	 	    //e.stopPropagation works in Firefox.
		 	 	    if (e.stopPropagation) {
		 	 	        e.stopPropagation();
		 	 	        e.preventDefault();	 	 	        
		 	 	    }
		    	 }	
		 	} 	   
	 	}
	 	window.onbeforeunload=checkRefresh;
	 	//End-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	 	//Started Added by Diksha Mukherjee on 22nd April 2016 as per Jtrac-DJB-4211 
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
		function checkIsValid(e){
			try{
				var unicode=e.charCode? e.charCode : e.keyCode;
	           	if( (unicode > 47 && unicode < 58) || unicode == 45 || unicode == 46 || unicode == 8) 
	               	return true;	
	           	else 
	               	return false;
			}catch (e) {         
		    } 
		}
		     	
		//Ended by Diksha Mukherjee on 22nd April 2016 as per Jtrac-DJB-4211 
	 </script>
</head>
<body onload="checkWaterConn()">
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
				<%
					String savedNewConnectionDAFDetailsFlag = (String) session
								.getAttribute("savedNewConnectionDAFDetailsFlag");
						if (!"Y".equalsIgnoreCase(savedNewConnectionDAFDetailsFlag)) {
				%>
				<div class="message" title="Server Message"><font size="2"><span
					id="newConMessage"><font color="blue">Please Enter
				the Mandatory Fields for New Connection DAF</font><font color="red"
					size="2"><s:actionerror /></font></span></font>&nbsp;</div>
				<div id="newconDAFDiv"><s:form action="javascript:void(0);"
					onsubmit="return false;" id="DAF" theme="simple">
					<s:hidden name="hidAction" />
					<s:hidden name="dafSequence" />
					<s:hidden name="jjrHidden" id="jjrHidden" />
					<s:hidden name="urbanHidden" id="urbanHidden" />
					<table width="100%" align="center" border="0">
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Details of Applicant</legend>
							<table width="90%" align="center" border="0">
								<tr>
								<!-- Start : Diksha on 08-03-2016 added a validation to enter alpha numeric character, JTrac DJB-4211 -->
									<td>DAF ID<font color="red"><sup>*</sup></font></td>
									<td colspan="3"><s:textfield name="dafId" id="dafId"
										maxlength="10" onkeypress="return checkAlphaNumeric(event)" cssClass="textbox" theme="simple" /></td>
								</tr>
								<!-- End : Diksha on 08-03-2016 added a validation to enter alpha numeric character, JTrac DJB-4211 -->
								<tr>
									<td>Name<font color="red"><sup>*</sup></font></td>
									<td colspan="3"><s:textfield name="entityName"
										id="entityName" maxlength="254" cssClass="textbox-long"
										theme="simple" onkeypress="return checkNameField(event)"
										onchange="fnValidateNewDAFForZoneMRAreaConsumerNameFatherName();" ;/></td>
								</tr>
								<tr>
									<td>Name of Father/Mother/Husband</td>
									<td colspan="3"><s:textfield name="fatherHusbandName"
										id="fatherHusbandName" maxlength="254" cssClass="textbox-long"
										theme="simple" onkeypress="return checkNameField(event)"
										onchange="fnValidateNewDAFForZoneMRAreaConsumerNameFatherName();" /></td>
								</tr>
								<tr>
									<td>Person ID Type</td>
									<td title="Please Select a Person ID Type"><s:select
										list="personIdTypeListMap" name="personIdType"
										id="personIdType" cssClass="selectbox-long" theme="simple" /></td>
									<td>Person ID No.</td>
									<td><s:textfield name="personIdNumber" id="personIdNumber"
										maxlength="100" cssClass="textbox" theme="simple"
										onkeypress="return checkNonSpecialCharacter(event)" /></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Property Address</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td align="center"><s:textfield name="houseNumber"
										id="houseNumber" title="House No *" maxlength="254"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="roadNumber"
										id="roadNumber" title="Road No" maxlength="254"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="sublocality1"
										id="sublocality1" title="Sub locality 1" maxlength="6"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="sublocality2"
										id="sublocality2" title="Sub locality 2" maxlength="4"
										cssClass="textbox" theme="simple" /></td>
								</tr>
								<tr>
									<td align="center"><s:textfield name="subColony"
										id="subColony" title="Sub Colony" maxlength="30"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="village"
										id="village" title="Village" maxlength="254"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="khashraNumber"
										id="khashraNumber" title="Khasra No" maxlength="11"
										cssClass="textbox" theme="simple" /></td>
									<td align="center"><s:textfield name="societyName"
										id="societyName" title="Society Name" maxlength="254"
										cssClass="textbox" theme="simple" /></td>
								</tr>
								<tr>
									<td align="center"><s:textfield name="pinCode"
										id="pinCode" title="PIN Code *" maxlength="6"
										cssClass="textbox" theme="simple" onkeyup="fnSetLocality(); "
										onkeypress="return checkOnlyNumeric(event)" /></td>
									<td id="tdLocality" align="center" title="Locality"><s:select
										list="#session.localityMap" name="locality" id="locality"
										headerKey="" headerValue="Locality" cssClass="selectbox-long"
										theme="simple" disabled="true" /></td>
									<td id="tdSubLocality" align="center" title="Sub Locality">
									<s:select list="#session.subLocalityMap" name="subLocality"
										id="subLocality" headerKey="" headerValue="Sub Locality"
										cssClass="selectbox-long" theme="simple" disabled="true" /></td>
									<td align="center" title="JJ Resettlement Colony"><input
										type="checkbox" name="jjrColony" id="jjrColony" />JJR Colony</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Property Details</legend>
							<table width="98%" align="center" border="0">
								<tr>
									<td>Property Type<font color="red"><sup>*</sup></font></td>
									<td colspan="2"><s:select list="propertyTypeMap"
										name="propertyType" id="propertyType" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" onchange="fnCheckPropertyType()" /></td>
									<!--  <td><s:textfield name="noOfFloors" id="noOfFloors"
										title="No of Floors *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)" />
									</td> -->
									<!--Start : Changed by Atanu on 06-05-2016 as per DJB_4211 -->
									<td><!--<s:textfield name="noOfFloors" id="noOfFloors"
										title="No of Floors *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)"
										disabled="true" />-->
									<s:if test="null!=noOfFloors && \"\"!=noOfFloors"><s:textfield name="noOfFloors" id="noOfFloors"
										title="No of Floors *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)"/>
									</s:if><s:else>
									<s:textfield name="noOfFloors" id="noOfFloors"
										title="No of Floors *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)"
										disabled="true" />
									</s:else>	
									</td>
									<td valign="top"><!-- <span id="mallCineplexSpan"
										style="display: none"><s:select list="{}"
										name="mallCineplex" id="mallCineplex" headerKey="1"
										headerValue="1" cssClass="selectbox" theme="simple" /></span><span
										id="noOfRoomsSpan" style="display: none"><s:textfield
										name="noOfRooms" id="noOfRooms" title="No of Rooms *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span> <span
										id="noOfBedsSpan" style="display: none"><s:textfield
										name="noOfBeds" id="noOfBeds" title="No of Beds *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span> <span
										id="noOfFunctionSiteSpan" style="display: none"><s:textfield
										name="functionSite" id="functionSite" title="Function Site *"
										maxlength="1" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>-->
										<s:if test="null!=mallCineplex && \"\"!=mallCineplex"><span id="mallCineplexSpan"><s:select list="{}"
										name="mallCineplex" id="mallCineplex" headerKey="1"
										headerValue="1" cssClass="selectbox" theme="simple" /></span>
										</s:if><s:else><span id="mallCineplexSpan"style="display: none"><s:select list="{}"
										name="mallCineplex" id="mallCineplex" headerKey="1"
										headerValue="1" cssClass="selectbox" theme="simple" /></span>
										</s:else>
										<s:if test="null!=noOfRooms && \"\"!=noOfRooms"><span
										id="noOfRoomsSpan"><s:textfield
										name="noOfRooms" id="noOfRooms" title="No of Rooms *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:if><s:else>
										<span
										id="noOfRoomsSpan" style="display: none"><s:textfield
										name="noOfRooms" id="noOfRooms" title="No of Rooms *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:else>
										<s:if test="null!=noOfBeds && \"\"!=noOfBeds"><span
										id="noOfBedsSpan"><s:textfield
										name="noOfBeds" id="noOfBeds" title="No of Beds *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:if><s:else>
										<span
										id="noOfBedsSpan" style="display: none"><s:textfield
										name="noOfBeds" id="noOfBeds" title="No of Beds *"
										maxlength="4" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:else>
										<s:if test="null!=functionSite && \"\"!=functionSite"><span
										id="noOfFunctionSiteSpan"><s:textfield
										name="functionSite" id="functionSite" title="Function Site *"
										maxlength="1" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:if><s:else>
										<span
										id="noOfFunctionSiteSpan" style="display: none"><s:textfield
										name="functionSite" id="functionSite" title="Function Site *"
										maxlength="1" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></span>
										</s:else>
										<!--Ended : Changed by Atanu on 06-05-2016 as per DJB_4211 */ -->
										</td>
									<td><input type="checkbox" name="urban" id="urban" />Urban
									</td>
								</tr>
								<tr>
									<td>Area(in Sq m)</td>
									<td><s:textfield name="plotArea" id="plotArea"
										title="Plot Area" maxlength="10" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyDecimal(event)"
										onchange="IsNumber(this)" /></td>
									<td><s:textfield name="builtUpArea" id="builtUpArea"
										title="Built Up Area" maxlength="10" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyDecimal(event)"
										onchange="IsNumber(this)" /></td>
									<!--Start : Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 -->
									<td><!--<s:textfield name="noOfBorewells" id="noOfBorewells"
										title="No of Borewells *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)"
										disabled="true" />-->
									<s:if test="null!=noOfBorewells && \"\"!=noOfBorewells"><s:textfield name="noOfBorewells" id="noOfBorewells"
										title="No of Borewells *" maxlength="4" cssClass="textbox"
										theme="simple" onkeypress="return checkOnlyNumeric(event)"/>
									</s:if><s:else>
									<s:textfield name="noOfBorewells" id="noOfBorewells"
									title="No of Borewells *" maxlength="4" cssClass="textbox"
									theme="simple" onkeypress="return checkOnlyNumeric(event)"
									disabled="true" />
									</s:else></td>
									<!--Ended : Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 -->
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>Water Connection Type<font color="red"><sup>*</sup></font></td>
									<td><s:select list="waterConnectionTypeMap"
										name="waterConnectionType" id="waterConnectionType"
										headerKey="" headerValue="Please Select" cssClass="selectbox"
										theme="simple" onchange="fnCheckWaterConnectionType();" /></td>
									<td>Water Connection Use<font color="red"><sup>*</sup></font></td>
									<td colspan="2"><s:select list="waterConnectionUseListMap"
										name="waterConnectionUse" id="waterConnectionUse" headerKey=""
										headerValue="Please Select" cssClass="selectbox-long"
										theme="simple" onchange="fnCheckWaterConnectionType();" /></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">No of Dwelling Units Occupied</td>
									<td><s:textfield name="noOfOccDwellUnits"
										id="noOfOccDwellUnits" maxlength="50" cssClass="textbox"
										theme="simple"
										onkeypress="return checkNonSpecialCharacter(event)"
										disabled="true" /></td>
									<td colspan="2">No of Dwelling Units Unoccupied</td>
									<td><s:textfield name="noOfUnoccDwellUnits"
										id="noOfUnoccDwellUnits" maxlength="50" cssClass="textbox"
										theme="simple"
										onkeypress="return checkNonSpecialCharacter(event)"
										disabled="true" /></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Other Details</legend>
							<table width="90%" align="center" border="0">
								<!-- Start : Reshma on 13-10-2015 increased the max length of the below fields, JTrac DJB-4195 -->
								<tr>
									<td>No Of Children</td>
									<!--   <td><s:textfield name="noOfChildren" id="noOfChildren"
										maxlength="3" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></td> -->
									<td><s:textfield name="noOfChildren" id="noOfChildren"
										cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></td>
									<td>No Of Adults</td>
									<!--   <td><s:textfield name="noOfAdults" id="noOfAdults"
										maxlength="3" cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></td> -->
									<td><s:textfield name="noOfAdults" id="noOfAdults"
										cssClass="textbox" theme="simple"
										onkeypress="return checkOnlyNumeric(event)" /></td>
								</tr>
								<!-- End : Reshma on 13-10-2015 increased the max length of the below fields, JTrac DJB-4195 -->
								<tr>
									<td>Is DJB Employee<font color="red"><sup>*</sup></font></td>
									<td><s:select list="isDJBEmployeeMap" name="isDJB"
										id="isDJB" cssClass="selectbox" theme="simple" /></td>
									<td>WC No.</td>
									<td><s:textfield name="wcNo" id="wcNo" maxlength="10"
										cssClass="textbox" theme="simple"
										onchange="fnValidateNewDAFForZoneMRAreaWCNo();" /></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<!--Start : Changed by Diksha Mukherjee on 8th March 2016 as per DJB_4211 -->
							<td align="center" valign="top" id="mtrBrDtlTD"
								style="display: none">
							<fieldset><legend>Meter Installation And
							Boring Details</legend>
							<table width="90%" align="center" border="0">
								<tr>
									<td>Size of Meter (in mm)<font color="red"><sup>*</sup></font></td>
									<!--   <td title="Size of Meter"><s:select
										list="waterConnectionSizeListMap" name="sizeOfMeter"
										id="sizeOfMeter" cssClass="selectbox" theme="simple" /></td> -->
									<td title="Size of Meter"><s:select
										list="waterConnectionSizeListMap" name="sizeOfMeter"
										id="sizeOfMeter" headerKey="" headerValue="Please Select"
										cssClass="selectbox" theme="simple" /></td>
									<td>Type of Meter<font color="red"><sup>*</sup></font></td>
									<!--   	<td title="Type of Meter"><s:select
										list="meterTypeListMap" name="typeOfMeter" id="typeOfMeter"
										cssClass="selectbox" theme="simple" /></td> -->
									<td title="Type of Meter"><s:select
										list="meterTypeListMap" name="typeOfMeter" id="typeOfMeter"
										headerKey="" headerValue="Please Select" cssClass="selectbox"
										theme="simple" /></td>
								</tr>
								<tr>
									<td title="Initial Register Read Date in DD/MM/YYYY format">Initial
									Register Read Date<font color="red"><sup>*</sup></font></td>
									<td title="Initial Register Read Date in DD/MM/YYYY format">
									<s:textfield name="initialRegisterReadDate"
										id="initialRegisterReadDate" maxlength="10" cssClass="textbox"
										style="text-align:center;" theme="simple" title="DD/MM/YYYY"
										onchange="validateDate(this)" /></td>
									<td>Initial Register Read Remark<font color="red"><sup>*</sup></font></td>
									<td><s:select list="meterReaderRemarkCodeListMap"
										name="initialRegisterReadRemark"
										id="initialRegisterReadRemark" cssClass="selectbox"
										theme="simple" /></td>
								</tr>
								<tr>
									<td>Initial Register Read<font color="red"><sup>*</sup></font></td>
									<td><s:textfield name="initialRegisetrRead"
										id="initialRegisetrRead" cssClass="textbox"
										style="text-align:right;" theme="simple"
										onkeypress="return checkOnlyDecimal(event)"
										onchange="IsNumber(this)" /></td>
									<td>Average Consumption</td>
									<td><s:textfield name="averageConsumption"
										id="averageConsumption" maxlength="50" cssClass="textbox"
										style="text-align:right;" theme="simple"
										onkeypress="return checkOnlyDecimal(event)"
										onchange="IsNumber(this)" /></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Other Technical Details</legend>
							<table width="90%" align="center" border="0">
								<tr>
									<td align="left" title="Please Select a Zone"><s:select
										list="zoneCodeListMap" name="zoneNo" id="zoneNo" headerKey=""
										headerValue="Zone *" onchange="fnSetMrNo()"
										cssClass="selectbox-long" theme="simple" /></td>
									<td id="tdMrNo" align="left" title="Please Select a MR No">
									<s:select list="#session.mrNoList" name="mrNo" id="mrNo"
										headerKey="" headerValue="MR No *" onchange="fnSetAreaNo();"
										cssClass="selectbox" theme="simple" /></td>
									<td id="tdAreaNo" align="left" title="Please Select a Area">
									<s:select list="#session.areaListMap" name="areaNo" id="areaNo"
										headerKey="" headerValue="Area *" cssClass="selectbox-long"
										theme="simple" /></td>
								</tr>
								<tr>
									<td align="left">Opening Balance</td>
									<td align="left" colspan="2"><s:textfield
										name="openingBalance" id="openingBalance" onkeypress="return checkIsValid(event)" cssClass="textbox"
										theme="simple" /></td>
								</tr>
								<tr>
									<td align="left">Application Purpose<font color="red"><sup>*</sup></font></td>
									<td align="left" colspan="2"
										title="Please Select a Application Purpose"><s:select
										list="applicationPurposeMap" name="applicationPurpose"
										id="applicationPurpose" cssClass="selectbox-long"
										theme="simple" />&nbsp;&nbsp;&nbsp;&nbsp; <font color="red"><sup>*</sup></font>=&nbsp;Mandatory
									Field.</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Disclaimer</legend>
							<table width="90%" align="center" border="0">
								<tr>
									<td align="left"><input type="checkbox" id="declaration"
										name="declaration" onclick="fnEnableProcessButton(this);" /><font
										size="2" color="red"> <b>All data has been checked
									&amp; verified by me and found correct.</b> </font></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top"><s:if
								test="null==dafSequence ||dafSequence==\"\" ">
								<input type="button" name="btnReset" id="btnReset" value="Reset"
									class="groovybutton" onclick="fnResetForm();"
									title="Click to Reset the fields to Default." />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<s:submit name="btnSubmit" id="btnSubmit" value="Submit"
									cssClass="groovybutton" theme="simple" disabled="disabled" />
							</s:if> <s:else>
								<input type="button" name="btnProcess" id="btnProcess"
									value="Process" class="groovybutton" onclick="fnProcessDAF();"
									title="Agree with the Disclaimer." disabled="disabled" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="btnSubmit" id="btnSubmit"
									value="Update" class="groovybutton"
									onclick="fnValidateUpdateNewConnectionDAFDetails();"
									title="Click to Update DAF Details" disabled="disabled" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="btnBack" id="btnBack" value="Back"
									class="groovybutton" onclick="fnGoBackToSearch();"
									title="Click to Go Back To Search Page" />
							</s:else></td>
						</tr>
					</table>
				</s:form></div>
				<s:if test="null==dafSequence ||dafSequence==\"\" ">
					<script language=javascript>
	//Setting the Default values on onload.\
//alert('Hello');	
var waterConnectionTypeVal = document.getElementById("waterConnectionType").value; 
//alert('waterConnectionTypeVal::'+waterConnectionTypeVal);		
document.forms[0].personIdType.value="DL";		
document.forms[0].isDJB.value="NOTDJB";
//if('CAT 3A' != (Trim(waterConnectionTypeVal)) && 'CAT 3B' != (Trim(waterConnectionTypeVal))&& 'CAT IIIA' != (Trim(waterConnectionTypeVal)) && 'CAT IIIB' != (Trim(waterConnectionTypeVal))){
document.forms[0].sizeOfMeter.value="15";
document.forms[0].typeOfMeter.value="MTR_PVT";
document.forms[0].initialRegisterReadRemark.value="OK";
//}
document.forms[0].applicationPurpose.value="SAWATSEW";				
				</script>
				</s:if> <%
 	} else {
 			String ccbMessage = (String) session
 					.getAttribute("ccbMessage");
 %>
				<table width="100%" align="center" border="0">
					<tr>
						<td align="center" valign="top">
						<div class="message" title="Server Message"><font size="2"><span
							id="newConMessage"><font color="green"><b>Data
						Saved Successfully.&nbsp; </b></font></span>&nbsp;</font></div>
						</td>
					</tr>
					<%
						if (null != ccbMessage) {
					%>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">
						<div class="message" title="Server Message"><font size="2"><span
							id="newConMessage"><b><%=ccbMessage%></b></span></font></div>
						</td>
					</tr>
					<%
						} else {
					%>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="middle">
						<div class="message" title="Server Message"><font size="2"><span
							id="newConMessage"><font color="red" size="5"><b>There
						was a Problem while Creating Account ID, Please Try Again.</b></font></span></font></div>
						</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td align="center" valign="middle">&nbsp;</td>
					</tr>
					<tr>
						<td align="center" valign="top"><s:form
							action="newConnectionDAFAJax.action">
							<s:hidden name="hidAction" value="" />
							<input type="button" value="Add More" class="groovybutton"
								onclick="fnGotoNewConnectionDAFScreen();" ;/>
						</s:form></td>
					</tr>
				</table>
				<script language=javascript>
					pageSubmited=true;				
				</script> <%
 	}
 %>
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
	var zroLocation="<%=session.getAttribute("zroLocation")%>";
	$(document).ready(function(){
		if(zroLocation=='null'||zroLocation==''||zroLocation=='ABSENT'){
			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Access Denied. You Don't Have sufficient Privilege to Perform the Action,<br/> Please make sure <br/><li>You have given Biometric attendence for today.</li><li>MRD/ZRO Location is Tagged with your User ID.</li></b></font>";
			document.getElementById('newconDAFDiv').style.display='none';
		}else{
			if($("#jjrHidden").val()=='Y'){
				document.getElementById('jjrColony').checked=true;
			}
			if($("#urbanHidden").val()=='YES'){
				document.getElementById('urban').checked=true;
			}
		}
	});
	var confirmationMessage="";
	$("form").submit(function(e){
		if(similarDetails ){
			confirmationMessage="A request already Submited with the same combination of Name,Name of Father/Mother/Husband, Zone, MR No and Area.\n Are you sure you want to re submit?\nClick OK to continue else Cancel.";
		}else{
			confirmationMessage="Please Make sure that all data entered are Correct.\n Are you sure you want to submit?\nClick OK to continue else Cancel.";
		}
		if(validate()){
			//hideScreen();	  
			if(confirm(confirmationMessage)){			
			  	$('input[type="text"]').each(function(){	  
					//this.value = $(this).attr('title');
					if(this.value == $(this).attr('title')) {
						this.value = '';
					} 
				});			
		  		fnSubmit();
			}else{
				$('input[type="text"]').each(function(){
				  	if(this.value == '') {
						this.value = $(this).attr('title');
					}	  
			  	});
				document.getElementById('newConMessage').innerHTML="<font color='green' size='2'><b>&nbsp;</b></font>";
				//showScreen();	  
			}
	  	}
  	});
	function fnValidateUpdateNewConnectionDAFDetails(){
		if(similarDetails ){
			confirmationMessage="A request already Submited with the same combination of Name,Name of Father/Mother/Husband, Zone, MR No and Area.\n Are you sure you want to re submit?\nClick OK to continue else Cancel.";
		}else{
			confirmationMessage="This action will Update the DAF Details in the System.\nYou are solely accountable for this action.\nIf You are Sure Click OK else Cancel.";
		}
		if(validate()){
			//hideScreen();	  
			if(confirm(confirmationMessage)){			
			  	$('input[type="text"]').each(function(){	  
					//this.value = $(this).attr('title');
					if(this.value == $(this).attr('title')) {
						this.value = '';
					} 
				});			
			  	fnUpdateNewConnectionDAFDetails();
			}else{
				$('input[type="text"]').each(function(){
				  	if(this.value == '') {
						this.value = $(this).attr('title');
					}	  
			  	});
				document.getElementById('newConMessage').innerHTML="<font color='green' size='2'><b>&nbsp;</b></font>";
				//showScreen();	  
			}
	  	}
	}
  $('input[type="text"]').each(function(){	
	  	if(this.value==''){  
			this.value = $(this).attr('title');
	  	}
		//$(this).addClass('text-label');	 
		$(this).focus(function(){
			if(this.value == $(this).attr('title')) {
				this.value = '';
				//$(this).removeClass('text-label');
			}
		});	 
		$(this).blur(function(){
			if(this.value == '') {
				this.value = $(this).attr('title');
				//$(this).addClass('text-label');
			}
		});
	});

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
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
			evt = evt || window.event;
			//alert(evt.keyCode);
			//if (evt.keyCode == 13 ) {  	//F5 Key Press 	
				//evt.preventDefault();
			//}
			if (evt.keyCode == 13 ) {  	//Enter Key Press 		    	
				if(validate()){
					hideScreen();	  
					if(confirm('Please Make sure that all data entered are Correct.\n Are you sure you want to submit?')){			
					  	$('input[type="text"]').each(function(){	  
							//this.value = $(this).attr('title');
							if(this.value == $(this).attr('title')) {
								this.value = '';
							} 
						});			
				  		fnSubmit();
					}else{
						$('input[type="text"]').each(function(){
						  	if(this.value == '') {
								this.value = $(this).attr('title');
							}	  
					  	});
						document.getElementById('newConMessage').innerHTML="<font color='green' size='2'><b>&nbsp;</b></font>";
						showScreen();	  
					}
			  	}
			} 
		};
		$(window).load(function () {	    	 
	    	    setCookie("IsRefresh", "true", 1);
	    });	
	    function fnEnableProcessButton(obj){
		    if(obj.checked){
			    if(document.getElementById('btnProcess')){
			    	document.getElementById('btnProcess').disabled=false;
			    	document.getElementById('btnProcess').title="Click to Process DAF.";
			    }
			    if(document.getElementById('btnSubmit')){
			    	document.getElementById('btnSubmit').disabled=false;
			    	document.getElementById('btnSubmit').title="Click to Save DAF.";
			    }
		    }else{
		    	 if(document.getElementById('btnProcess')){
			    	document.getElementById('btnProcess').disabled=true;
			    	document.getElementById('btnProcess').title="Agree with the Disclaimer.";
		    	}
				if(document.getElementById('btnSubmit')){
			    	document.getElementById('btnSubmit').disabled=true;
			    	document.getElementById('btnSubmit').title="Agree with the Disclaimer.";
				}
		    }
	    }
	    var gotoBack=false;
	    function fnGoBackToSearch(){
	    	hideScreen();
	    	gotoBack=true;
	    	window.location.href="newConnectionSearchDAF.action?hidAction=back";
	    }
	    /*Start : Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 */
	    function fnCheckWaterConnectionType(){
	    var waterConnectionTypeVal = document.getElementById("waterConnectionType").value;
	    if(waterConnectionTypeVal ){
	    	 if('CATIIIA' == (Trim(waterConnectionTypeVal)) || 'CATIIIB' == (Trim(waterConnectionTypeVal))){
	    		 document.getElementById("mtrBrDtlTD").style.display = "none";
		    	//document.getElementById("mtrBrDtlTD").style.display = "";
   	}else{
   		document.getElementById("mtrBrDtlTD").style.display = "block";
   	}
   	}
	    	
	   if(waterConnectionTypeVal){
		   if('CATIIIA' == (Trim(waterConnectionTypeVal)) || 'CATIIIB' == (Trim(waterConnectionTypeVal))){
			   	 document.getElementById("noOfFloors").value = "";
	    		 document.getElementById("noOfFloors").disabled = true;
	    		 document.getElementById("noOfBorewells").disabled = false;
		   }
		   else{
			   document.getElementById("noOfBorewells").value = "";
			   document.getElementById("noOfBorewells").disabled = true;
			   document.getElementById("noOfFloors").disabled = false;
			   }
	    }
	    	}
	    	function checkWaterConn(){
	    		 var waterConnectionTypeVal = document.getElementById("waterConnectionType").value;
	    		if(waterConnectionTypeVal ){
			    	 if('CATIIIA' == (Trim(waterConnectionTypeVal)) || 'CATIIIB' == (Trim(waterConnectionTypeVal))){
			    		 document.getElementById("mtrBrDtlTD").style.display = "none";
				    	//document.getElementById("mtrBrDtlTD").style.display = "";
		    	}else{
		    		document.getElementById("mtrBrDtlTD").style.display = "block";
		    	}
		    	}
	    	}
	    	/* Ended : Added by Diksha Mukherjee on 8th March 2016 as per DJB_4211 */
	    
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
