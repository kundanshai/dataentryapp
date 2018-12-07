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
<title>Pre Bill Status Definition Page - Revenue Management
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
<link rel="stylesheet" type="text/css" href="<s:url value="/css/menu.css"/>"/>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	var validId=false;
	function checkValNonnumericDotSpecial(e){
		try {
	        var unicode=e.charCode? e.charCode : e.keyCode;		              
	        if ((unicode > 32 && unicode < 48) || (unicode > 57 && unicode < 65) || (unicode > 90 && unicode < 97)){		                  
	           return false;
	        }
	    }
	    catch (e) {}
	}
	function checkValNumeric(e){
		try {
		    var unicode=e.charCode? e.charCode : e.keyCode;
			if (unicode!=8){ 
		   		if(!(unicode>=48 && unicode<=57 )){
		   			return false ;
		   		}
			}
	 	}catch (e){}   
	} 
	function fnResetValues() { 
		document.getElementById('preBillStatusId').value='';
		document.getElementById('preBillStatusDescription').value='';
	}
	function fnSubmit(){
	 	if(Trim(document.forms[0].preBillStatusId.value)=='' || !validId){
	 		submitCount=0;
	 		document.getElementById('onscreenMessageTop').innerHTML="<font color='red'>Please Enter All the Mandatory Fields</font>";
	 		document.getElementById('onscreenMessageBottom').innerHTML="<font color='red'>Please Enter All the Mandatory Fields</font>";
	 		window.scroll(0,docHeight);
	 		document.forms[0].preBillStatusId.focus();	 		
	 		return;
	 	}
	 	if(Trim(document.forms[0].preBillStatusDescription.value)==''){
	 		submitCount=0;
	 		document.getElementById('onscreenMessageTop').innerHTML="<font color='red'>Please Enter All the Mandatory Fields</font>";
	 		document.getElementById('onscreenMessageBottom').innerHTML="<font color='red'>Please Enter All the Mandatory Fields</font>";
	 		window.scroll(0,docHeight);
	 		document.forms[0].preBillStatusDescription.focus();
	 		return;
	 	}	
	 	if(validId){
	 		submitCount++; 
	 		document.getElementById('onscreenMessageTop').innerHTML="&nbsp;";
			document.getElementById('onscreenMessageBottom').innerHTML="&nbsp;";					
		 	if(confirm('Are you sure you want to Update?')){			
		 		fnSave();
			}else{
				submitCount=0;
			}
	 	}else{
	 		document.getElementById('onscreenMessageTop').innerHTML="<font color='red'>Please Enter A Valid ID</font>";
	 		document.getElementById('onscreenMessageBottom').innerHTML="<font color='red'>Please Enter A Valid ID</font>";
	 		window.scroll(0,docHeight);
	 		document.forms[0].preBillStatusId.focus();	 	
	 	}
 	}
	function fnValidatePreBillStatusId(){
		document.getElementById('invalidIDSpan').style.display="none";	
		document.getElementById('validIDSpan').style.display="none";
		if(Trim(document.getElementById('preBillStatusId').value)!=''){	 	 
			httpObject = null;
			httpObject=getHTTPObjectForBrowser(); 
			var url= "preBillStatusDefinitionAJax.action?hidAction=validatePreBillStatusId&preBillStatusId="+Trim(document.getElementById('preBillStatusId').value); 		
			if (httpObject != null) {
				hideScreen();									
				//alert('url::'+url); 
				httpObject.onreadystatechange = function(){         
					if(httpObject.readyState == 4){
						var  responseString= httpObject.responseText;
						//alert('responseString::\n'+responseString);
						if(null!=responseString && responseString.indexOf('<script')==-1){
							if(responseString=='Y'){
								document.getElementById('validIDSpan').style.display="block";	
								validId=true;		
								showScreen();
								window.scroll(0,docHeight);
								document.forms[0].preBillStatusDescription.focus();
							}else if(null!=responseString &&  responseString=='N'){	
								document.getElementById('invalidIDSpan').style.display="block";
								validId=false;
								showScreen();
								window.scroll(0,docHeight);
								document.forms[0].preBillStatusId.focus();
							}else{
								alert('Sorry ! \nThere was a problem');
								validId=false;
								window.scroll(0,docHeight);
								showScreen();
							}
						}else{
							document.getElementById('onscreenMessageTop').innerHTML=responseString;
							showScreen();
						}  
					}     
				};    
				httpObject.open("POST", url, true);            
				httpObject.send(null);
			} 
		} 
	}
	function fnSave(){
		if(Trim(document.getElementById('preBillStatusId').value)!=''&& Trim(document.getElementById('preBillStatusDescription').value)){	 	 
			httpObject = null;
			httpObject=getHTTPObjectForBrowser(); 
			var url= "preBillStatusDefinitionAJax.action?hidAction=update&preBillStatusId="+Trim(document.getElementById('preBillStatusId').value)+"&preBillStatusDescription="+Trim(document.getElementById('preBillStatusDescription').value); 		
			if (httpObject != null) {
				hideScreen();									
				//alert('url::'+url); 
				httpObject.onreadystatechange = function(){         
					if(httpObject.readyState == 4){
						var  responseString= httpObject.responseText;
						//alert('responseString::\n'+responseString);
						if(null!=responseString && responseString.indexOf('<script')==-1){
							if(null!=responseString && responseString=='Y'){
								document.forms[0].hidAction.value="update";
								document.forms[0].action="preBillStatusDefinition.action";
			 			 		document.forms[0].submit();
							}else if(null!=responseString &&  responseString=='N'){
								document.getElementById('onscreenMessageTop').innerHTML="<font color='red'>There was problem while processing.</font>";
			 					document.getElementById('onscreenMessageBottom').innerHTML="<font color='red'>There was problem while processing.</font>";
			 					showScreen(); 
								document.forms[0].preBillStatusId.focus();
							}else{
								alert('Sorry ! \nThere was a problem');
								validId=false;
								showScreen();
							}
						}else{
							document.getElementById('onscreenMessageTop').innerHTML=responseString;
							showScreen();
						}  
					}     
				};    
				httpObject.open("POST", url, true);            
				httpObject.send(null);
			} 
		} 
	}	
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
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/TopMenu.html"%></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessageTop"><font color="green" size="2"><s:property
					value="#session.DATA_SAVED" /></font></span></font>&nbsp;</div>
				<table width="100%" align="center" border="0"
					title="Warning! Records once updated cannot be Undone.">
					<tr>
						<td align="center" valign="top">
						<fieldset><legend>Pre Bill Status Definition</legend>
						<table id="searchResult" class="table-grid">
							<s:if test="preBillStatusDefinitionDetailsList.size() > 0">
								<tr>
									<th align="center" width="5%">SL</th>
									<th align="center" width="5%">Status ID</th>
									<th align="center" width="60%">Description</th>
									<th align="center" width="15%">Last Updated By</th>
									<th align="center" width="15%">Last Updated On</th>
								</tr>
								<s:iterator value="preBillStatusDefinitionDetailsList"
									status="row">
									<tr onMouseOver="this.bgColor= 'yellow';"
										onMouseOut="this.bgColor= 'white';">
										<td align="center"><s:property value="#row.count" /></td>
										<td align="center"><s:property value="preBillStatusId" /></td>
										<td align="left"><s:property value="description" /></td>
										<td align="center"><s:property value="lastUpdatedBy" /></td>
										<td align="center"><s:property value="lastUpdatedOn" /></td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td align="center" colspan="2"><font color="red">No
									Records Found To Display.</font></td>
								</tr>
							</s:else>
						</table>
						<br />
						</fieldset>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top"><s:if
							test="(#session.userRoleId==1)||(#session.userRoleId==5)">
							<s:form action="javascript:void(0);" onsubmit="return false;"
								theme="simple">
								<fieldset><legend>Add New Pre Bill Status
								Definition</legend> <s:hidden name="hidAction" id="hidAction" />
								<table class="table-grid">
									<tr>
										<th align="center" width="15%" colspan="2">Status ID<font
											color=red><sup>*</sup></font></th>
										<th align="center" width="60%">Description<font color=red><sup>*</sup></font></th>
										<th align="center" width="25%">Action</th>
									</tr>
									<tr>
										<td align="center" width="10%"><s:textfield
											name="preBillStatusId" id="preBillStatusId"
											title="Pre Bill Status ID" size="10" maxlength="10"
											cssClass="textbox" theme="simple"
											onkeypress="return checkValNumeric(event)"
											onchange="fnValidatePreBillStatusId();" /></td>
										<td align="center" width="5%"><span id="validIDSpan"
											style="display: none;" /><img
											src="/DataEntryApp/images/valid.gif" border="0"
											title="Valid ID" /></span><span id="invalidIDSpan"
											style="display: none;" /><img
											src="/DataEntryApp/images/invalid.gif" border="0"
											title="Invalid ID" /></span>&nbsp;</td>
										<td align="center"><s:textfield
											name="preBillStatusDescription" size="100"
											id="preBillStatusDescription" title="" maxlength="254"
											cssClass="textbox" theme="simple"
											onkeypress="return checkValNonnumericDotSpecial(event)" /></td>
										<td align="center"><s:submit method="execute"
											theme="simple" align="center" value="Update"
											cssClass="groovybutton" /></td>
									</tr>
								</table>
								</fieldset>
							</s:form>
						</s:if></td>
					</tr>
					<tr>
						<td align="center" valign="top">
						<div class="message" title="Server Message"><font size="2"><span
							id="onscreenMessageBottom"><font color="green" size="2"><s:property
							value="#session.DATA_SAVED" /></font><font color="red" size="2"><s:actionerror /></font></span></font>&nbsp;</div>
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
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(document).ready(function(){	
		$('input[type="text"]').focus(function() { 
			$(this).addClass("textboxfocus"); 
		});   
		$('input[type="text"]').blur(function() {  					
			$(this).removeClass("textboxfocus"); 
			$(this).addClass("textbox"); 
		});				  
	});
	$("form").submit(function(e){		
		 fnSubmit();
	});
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
			evt = evt || window.event;
			//alert(evt.keyCode);
			//if (evt.keyCode == 13 ) {  	//F5 Key Press 	
				//evt.preventDefault();
			//}
			/*if (evt.keyCode == 13 &&submitCount==0) {  	//Enter Key Press 	
				fnSubmit();
			}*/ /*
			if (evt.keyCode == 27) {  //Esc Key Press 		    	 		    	
				if(hhdPopUp &&!isProcessing){
 		    		hideHHDIDEntryScreen();
 		    		hhdPopUp=false;
				}
				if(mrPopUp&&!isProcessing){
 		    		hideMeterReaderEntryScreen();
 		    		mrPopUp=false;
				}
	 		 } */
		};
		var winHeight=$(window).height();         // returns height of browser viewport
		var docHeight=$(document).height();       // returns height of HTML document
		var submitCount=0;
		//window.scroll(0,docHeight);
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>