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
	src="<s:url value="/js/NewConnectionSearch.js"/>"></script>
<script language=javascript>
		function disableBack()
		{
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
	 	
		function fnResetForm(){
			/*
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
			}  */
		}		
	 	function checkRefresh(e) {
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
	 //	window.onbeforeunload=checkRefresh;
	 	//End-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	 </script>
</head>
<body>
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
				<div class="message" title="Server Message"><font size="2"><span
					id="newConMessage"><font color="blue">Please Enter the Mandatory Fields for New Connection Search DAF</font><font color="red"
					size="2"><s:actionerror /></font></span></font>&nbsp;</div>
				<div id="newconsearchDAFDiv">			
				<s:form action="javascript:void(0);" onsubmit="return false;" theme="simple" id="searchForm" name="searchForm">
					<s:hidden name="hidAction" />
					<s:hidden name="dafSequence" />
					<table width="100%" align="center" border="0">
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Search Criteria</legend>
								<table width="90%" align="center" border="0">															
								    <tr>
										<td>Zone:<font color="red"><sup>*</sup></font></td>
										<td><s:select
											list="zoneCodeListMap" name="zoneNo" id="zoneNo" headerKey=""
											headerValue="Zone *" 
											cssClass="selectbox-long" theme="simple" onchange="fnResetSearchResult();"/></td>
										<td>Location:<font color="red"><sup>*</sup></font></td>
										<td><s:select
											list="locationListMap" name="location" id="location" headerKey=""
											headerValue="Location *" 
											cssClass="selectbox-long" theme="simple" onchange="fnResetSearchResult();"/></td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
										<td colspan="2" align='left'>&nbsp;</td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
										<td colspan="2" align='left'><input type="button" name="btnSearch" id="btnSearch" value="Search" class="groovybutton" onclick="fnGetSearchResult();" title="Click to Search New Connection Details."/></td>
									</tr>
									
									<tr>
										<td colspan="4">
											&nbsp;
										</td>
									</tr>
								</table>	
							</fieldset>
							</td>
							</tr>
							<tr>
							<td align="center" valign="top">
								<table width="100%" align="center" border="0">
									<tr>
										<td colspan="4" align="center">
											<div id="searchResultDiv"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form> </div>
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
			document.getElementById('newconsearchDAFDiv').style.display='none';
		}else{
			var location = Trim(document.getElementById('location').value);
			var zoneNo = Trim(document.getElementById('zoneNo').value);
			if(''!=zoneNo ||''!=location){
				fnGetSearchResult();
			}
		}
	});
  $('input[type="text"]').each(function(){	  
		this.value = $(this).attr('title');
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
		function fnResetSearchResult(){
			document.getElementById('searchResultDiv').innerHTML="";
		}
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
</html>
