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
<title>HHD Credentials Page - Revenue Management System, Delhi Jal Board</title>
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script language=javascript>

      function gotoAddHHDdevice(){
    	  popupHHDCredentialsAddScreen();
    	  mrPopUp=true;
          }

		function disableBack(){
			window.history.forward(1);
		}
	 	disableBack();
	 	var mrPopUp=false;
	 	var isProcessing=false;
	 	var prevMeterReaderCode="";
		var prevHHDID="";
		var toupdateMRKey="";
		var toupdateZone="";
	 	var todaysDate="<%=(String) session.getAttribute("TodaysDate")%>";
	 	function gotoHHDCredentialsEditScreen(prevHHDID,prevPasswd){
		 	 		 	
	 		popupHHDCredentialsEditScreen(prevHHDID,prevPasswd);	 		
	 		mrPopUp=true;	 		
	 	}
	 	
		function gotoHHDCredentialsDeleteScreen(HHDID){
	 		 	
	 		popupHHDCredentialsDeleteScreen(HHDID);	 		
	 		mrPopUp=true;	 		
	 	}


	 	
	 	function gotoNextPage(){
		 
		 	document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)+1;
		 	fnSearch();
	 	}	
	 	function gotoPreviousPage(){
	 	
	 		document.forms[0].pageNo.value=parseInt(document.forms[0].pageNo.value)-1;
		 	fnSearch();
	 	}
	 	function gotoSearch(){
	 	
		 	if(document.forms[0].pageNo){
	 			document.forms[0].pageNo.value="1";
		 	}
		 	fnSearch();
	 	}	 	
	 	function fnSearch(){
	 			 	
	 		document.forms[0].hidAction.value="search";
	 		document.forms[0].action="hhdCredentials.action";
	 		hideScreen();
	 		document.forms[0].submit();
	 	}
	 	function fnSetZone(){
	 	
		 	if(Trim(document.forms[0].mrKeyList.value).length>1){
	 			document.forms[0].zoneCode.value="";
		 	}
	 	}
	 	function fnSetMRKeyList(){
	
		 	if(Trim(document.forms[0].zoneCode.value).length>1){
	 			document.forms[0].mrKeyList.value="";
		 	}
	 	}
		function saveHHDCredentialsDetailsAjaxCall(prevValueHhdId,toHHDiD,toPassword){		
		
			isProcessing=true;			
			document.getElementById('hhdCredentialsMessage').innerHTML="";
			document.getElementById('hhdCredentialsMessage').title="Server Message.";
			var url= "hhdCredentialsAJax.action?hidAction=updateHHDCredentialsDetails";
			url+="&prevValueHhdId="+prevValueHhdId;	
			url+="&toHHDiD="+toHHDiD;									
			url+="&toPassword="+toPassword;
			hideScreen();
			//alert('url::'+url); 
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
						if(null!=responseString){ 
							if('SUCCESS'==responseString){
								document.forms[0].hidAction.value="searchAfterSave";
								document.forms[0].action="hhdCredentials.action";
								document.forms[0].submit();
							}else{
								document.getElementById('hhdCredentialsMessage').innerHTML=responseString;
								showScreen();
							}   
						}
					}     
				};  
				httpBowserObject.send(null);
			}
		}	
		function addHHDCredentialsDetailsAjaxCall(addHHDiD,addPassword){		
					
			isProcessing=true;			
			document.getElementById('hhdCredentialsMessage').innerHTML="";
			document.getElementById('hhdCredentialsMessage').title="Server Message.";
			var url= "addhhdCredentialsAJax.action?hidAction=addHHDCredentialsDetails";
			url+="&addHHDiD="+addHHDiD;	
			url+="&addPassword="+addPassword;						
			
			hideScreen();
			//alert('url::'+url); 
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
						if(null!=responseString){ 
							if('SUCCESS'==responseString){
								document.forms[0].hidAction.value="searchAfterSave";
								document.forms[0].action="hhdCredentials.action";
								document.forms[0].submit();
							}else{
								document.getElementById('hhdCredentialsMessage').innerHTML=responseString;
								showScreen();
							}   
						}
					}     
				};  
				httpBowserObject.send(null);
			}
		}

		function deleteHHDCredentialsDetailsAjaxCall(deleteHHDiD){		
			
			isProcessing=true;			
			document.getElementById('hhdCredentialsMessage').innerHTML="";
			document.getElementById('hhdCredentialsMessage').title="Server Message.";
			var url= "hhdCredentialsAJax.action?hidAction=deleteHHDCredentialsDetails";
			url+="&deleteHHDiD="+deleteHHDiD;		
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
						if(null!=responseString){ 
							if('SUCCESS'==responseString){
								document.forms[0].hidAction.value="searchAfterSave";
								document.forms[0].action="hhdCredentials.action";
								document.forms[0].submit();
							}else{
								document.getElementById('hhdCredentialsMessage').innerHTML=responseString;
								showScreen();
							}   
						}
					}     
				};  
				httpBowserObject.send(null);
			}
		}
				 	  		
	 </script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<%@include file="../jsp/HHDCredentialsEdit.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
			</tr>
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/TopMenu.html"%></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="hhdCredentialsMessage"><%=session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form action="javascript:void(0);" onsubmit="return false;"
					theme="simple">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="totalRecords" id="totalRecords" />	
					<s:hidden name="toUpdateHHDID" id="toUpdateHHDID" />				
					<table width="100%" align="center" border="0">
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Add new Hand Held Device Credentials</legend>
							<table width="90%" align="center" border="0">
								<tr>									
									<td align="left">
								  <input type="button" name="btnAdd" id="btnAdd" value="Add"  class="groovybutton" onclick="javascript:gotoAddHHDdevice()";/>
									</td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Hand Held Device Credentials</legend> <s:if
								test="hhdCredentialsDetailsList.size() > 0">
								<table id="mapping_table" class="table-grid">
									<tr>
										<th align="center" rowspan="2" width="5%">SL</th>
										
										<th align="center" rowspan="2" colspan="2" width="20%">HHD
										ID</th>
										<th align="center" rowspan="2" colspan="2" width="10%">HHD
										Password</th>
										<th align="center" rowspan="1" colspan="2" width="25%">Effective
										Date</th>
									</tr>
									<tr>
										<th align="center" rowspan="1">FROM</th>
										<th align="center" rowspan="1">TO</th>
									</tr>
									<s:iterator value="hhdCredentialsDetailsList" status="row">
										<tr onMouseOver="this.bgColor='yellow';"
											onMouseOut="this.bgColor='white';">
											<td align="center"><s:property value="slNo" /></td>
										
											<td align="center"><s:property value="hhdId" /></td>
											<td width="2%" align="center"><a href="#"
												onclick="javascript:gotoHHDCredentialsDeleteScreen('<s:property value="hhdId" />');"><img
												src="/DataEntryApp/images/delete-icon_small.png" border="0"
												title="Click to Delete HHD Credentials"
												onclick="this.border='2'" /></a></td>
											<td><s:property value="password" />&nbsp;</td>
											<td width="2%" align="center"><a href="#"
												onclick="javascript:gotoHHDCredentialsEditScreen('<s:property value="hhdId" />','<s:property value="password" />');"><img
												src="/DataEntryApp/images/page_edit.gif" border="0"
												title="Click to Edit HHD Password" onclick="this.border='2'" /> </a></td>
											<td align="center" width="13%"><s:property
												value="effFromDate" />&nbsp;</td>
											<td align="center" width="12%"><s:property
												value="effToDate" />&nbsp;</td>
											<!-- <td><s:property value="lastUpdatedBy" />&nbsp;</td>
											<td><s:property value="lastUpdatedOn" />&nbsp;</td> -->
										</tr>
									</s:iterator>
								</table>
								<table width="100%" align="center" border="0">
									<tr>
										<td align="center" valign="top" nowrap><s:if
											test="pageNo > 1">
											<input type="button" name="btnPrevious" id="btnPrevious"
												value="<< Previous " class="groovybutton"
												onclick="javascript:gotoPreviousPage();" />
										</s:if><s:else>
											<input type="button" name="btnPrevious" id="btnPrevious"
												value="<< Previous " class="groovybutton" disabled="true" />
										</s:else>&nbsp;Page No: <s:select list="pageNoDropdownList"
											name="pageNo" id="pageNo" cssClass="smalldropdown"
											theme="simple" onchange="fnSearch();" /> <s:if
											test="pageNo*maxRecordPerPage< totalRecords">
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton"
												onclick="javascript:gotoNextPage();" />
										</s:if><s:else>
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton" disabled="true" />
										</s:else></td>
									</tr>
								</table>
								<p align="center"><font size="2" color="blue"><b>Showing
								Maximum <s:select
									list="{10,20,30,40,50,100,150,200,300,400,500,1000}"
									name="maxRecordPerPage" id="maxRecordPerPage" theme="simple"
									onchange="fnSearch();" /> records of <s:property
									value="totalRecords" /> records.</b></font></p>
							</s:if> <s:else>
								<p align="center"><font size="2" color="#000">No
								records found to Display.</font></p>
							</s:else> <br />
							</fieldset>
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
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
			evt = evt || window.event;
			//alert(evt.keyCode);
			//if (evt.keyCode == 13 ) {  	//F5 Key Press 	
				//evt.preventDefault();
			//}
			if (evt.keyCode == 13 ) {  	//Enter Key Press 	
				if(mrPopUp&&!isProcessing){
					saveHHDCredentialsDetails();					
				}else{
					fnSearch();
				}
			} 
		
			if (evt.keyCode == 27) {  //Esc Key Press	
				
				if(mrPopUp&&!isProcessing){
 		    		hideHHDCredentialsEditScreen(); 		    	
 		    		mrPopUp=false;
				}
	 		 } 
		
		}; 		
</script>
<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>
