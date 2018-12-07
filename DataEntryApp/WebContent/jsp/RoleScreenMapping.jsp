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
<title>User Role Screen Mapping Page - Revenue Management
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
		function disableBack(){
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
	 	var isProcessing=false;
	 	var isFocused=false;
	 	function fnSearch(){
		 	if(Trim(document.getElementById('userRole').value)!='0'){			 	
		 		if(Trim(document.getElementById('userRole').value)==''&& Trim(document.getElementById('userIdList').value)==''){
					document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Select a Role or Enter User Id(s) to Proceed.</font>";	
					window.scrollTo(0,0);
					return;
				}
		 		if(Trim(document.getElementById('userIdList').value)!=''){
		 			document.getElementById('userRole').value="";
				}	
		 		isProcessing=true;	 	
		 		document.forms[0].hidAction.value="search";
		 		document.forms[0].action="roleScreenMapping.action";
		 		hideScreen();
		 		DeleteCookie("IsRefresh");
		 		document.forms[0].submit();
		 	}else{
		 		document.getElementById('userRole').value="";
		 	}
	 	}
		function fnResetRole(obj){
			if(Trim(obj.value).length!=0){
				document.forms[0].userRole.value="";
			}
		}
		function fnResetUserIdList(obj){
			if(Trim(obj.value).length!=0){
				document.forms[0].userIdList.value="";
			}
		}
		var screenIdList = "";
		function fnSetScreenId() {
			screenIdList = "";
			var rows = document.getElementById('mapping_table').rows;
			var checkboxId = "";
			var screenId = "";
			for ( var i = 1; i < rows.length - 1; i++) {
				checkboxId = "trCheckbox" + i;
				screenId = "screenId" + i;
				if (document.getElementById(checkboxId).checked) {					
					screenIdList += document.getElementById(screenId).value + ",";
				}
			}
			alert(screenIdList);
		}	
		function fnUpdate(rowNo,screenId,accessBy,obj){
			if(Trim(document.getElementById('userRole').value)==''&& Trim(document.getElementById('userIdList').value)==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Select a Role or Enter User Id(s) to Proceed.</font>";	
				window.scrollTo(0,0);
				if(obj.checked){
					obj.checked=false;
				}else{
					obj.checked=true;
				}
				return;
			}
			if(Trim(document.getElementById('userRole').value)=='' && accessBy=='ROLE'){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Record cannot be Updated as Access given is by Role. Please Change the Role of the User.</font>";	
				window.scrollTo(0,0);
				if(obj.checked){
					obj.checked=false;
				}else{
					obj.checked=true;
				}
				return;
			}
			var confirmMessage="Are You Sure ";
			var hidAction="";
			if(obj.checked){
				confirmMessage+="You Want to Provide Access to the Selected Screen ?";
				hidAction="provideAccess";
			}else{
				confirmMessage+="You Want to Revoke Access from the Selected Screen ?";
				hidAction="revokeAccess";
			}
			confirmMessage+="\n\nClick OK to Continue else Cancel.";
			if(confirm(confirmMessage)){
				var imgId="imgCB"+rowNo;
				showAjaxLoading(document.getElementById(imgId));
				var url = "roleScreenMappingAJax.action?hidAction="+hidAction+"&screenId="+ screenId+ "&userRole=" + document.getElementById('userRole').value+"&userIdList="+document.getElementById('userIdList').value;;				
				//alert(url);
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
							//alert(httpBowserObject.responseText);
							if (null == responseString
									|| responseString.indexOf("ERROR:") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Records could not be Updated Successfully. Please Retry.</b></font>";
								document.getElementById('onscreenMessage').title = "Server Response";
								hideAjaxLoading(document.getElementById(imgId));
								if(obj.checked){
									obj.checked=false;
								}else{
									obj.checked=true;
								}							
							}else {							
								document.getElementById('onscreenMessage').innerHTML = responseString;
								//fnSearch();
								hideAjaxLoading(document.getElementById(imgId));										
							}
							
						}
					};
					httpBowserObject.send(null);
				}
			}else{
				if(obj.checked){
					obj.checked=false;
				}else{
					obj.checked=true;
				}
			}
	 	}
		function fnUpdateEffectiveToDate(rowNo,screenId,accessBy,obj){
			if(Trim(document.getElementById('userRole').value)==''&& Trim(document.getElementById('userIdList').value)==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Select a Role or Enter User Id(s) to Proceed.</font>";	
				window.scrollTo(0,0);
				return;
			}
			if(Trim(document.getElementById('userRole').value)=='' && accessBy=='ROLE'){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Record cannot be Updated as Access given is by Role. Please Change the Role of the User.</font>";	
				window.scrollTo(0,0);
				return;
			}
			var imgId="img"+rowNo;
			var effectiveToDate=Trim(obj.value);
			if(validateDate(obj)&&effectiveToDate.length==10){
				//alert(effectiveToDate);				
				var confirmMessage="Are You Sure You Want to Update Eeffective ToDate for the Selected Screen ?";
				var hidAction="updateEffectiveToDate";
				confirmMessage+="\n\nClick OK to Continue else Cancel.";
				if(confirm(confirmMessage)){					
					showAjaxLoading(document.getElementById(imgId));
					var url = "roleScreenMappingAJax.action?hidAction="+hidAction+"&screenId="+ screenId+ "&userRole=" + document.getElementById('userRole').value+"&userIdList="+document.getElementById('userIdList').value+"&effectiveToDate="+effectiveToDate;				
					//alert(url);
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
								//alert(httpBowserObject.responseText);
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Records could not be Updated Successfully. Please Retry.</b></font>";
									document.getElementById('onscreenMessage').title = "Server Response";
									hideAjaxLoading(document.getElementById(imgId));								
								}else {							
									document.getElementById('onscreenMessage').innerHTML = responseString;
									hideAjaxLoading(document.getElementById(imgId));								
								}
							}
						};
						httpBowserObject.send(null);
					}
				}
			}
	 	}
		function fnUpdateSelected(){
			if(Trim(document.getElementById('userRole').value)==''&& Trim(document.getElementById('userIdList').value)==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Select a Role or Enter User Id(s) to Proceed.</font>";	
				window.scrollTo(0,0);
				return;
			}
			alert(screenId+'<>'+obj.checked);
			if(screenIdList==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Nothing to Update ! Please Select Screen(s) from the Table.</font>";	
				window.scrollTo(0,0);
				return;
			}else{
				if(confirm('Are You Sure You Want to Provide Access to the Selected Screen(s) ?\n\nClick OK to Continue else Cancel')){
					hideScreen();
					 var url = "roleScreenMappingAJax.action?hidAction=provideAccess&toUpdateScreenIdList="
							+ screenIdList + "&userRole=" + document.getElementById('userRole').value+"&userIdList="+document.getElementById('userIdList').value;
					alert(url);
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
								//alert(httpBowserObject.responseText);
								if (null == responseString
										|| responseString.indexOf("ERROR:") > -1) {
									document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Records could not be Updated Successfully. Please Retry.</b></font>";
									document.getElementById('onscreenMessage').title = "Server Response";
									showScreen();									
								}else {							
									document.getElementById('onscreenMessage').innerHTML = responseString;
									fnSearch();									
								}
							}
						};
						httpBowserObject.send(null);
					}
				}
			}
	 	}
	 	function fnAccessBy(accessBy,obj){
	 		if(Trim(document.getElementById('userRole').value)=='' && accessBy=='ROLE'){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'>Record cannot be Updated as Access given is by Role. Please Change the Role of the User.</font>";	
				window.focus();
				//obj.disabled=true;
			}else{
				document.getElementById('onscreenMessage').innerHTML = "";	
			}
	 	}	 		
	 	function goodbye(e) {
	 	    if(!e) e = window.event;
	 	    //e.cancelBubble is supported by IE - this will kill the bubbling process.
	 	    e.cancelBubble = true;
	 	    e.returnValue = 'You sure you want to leave/refresh this page?';
	 	 
	 	    //e.stopPropagation works in Firefox.
	 	    if (e.stopPropagation) {
	 	        e.stopPropagation();
	 	        e.preventDefault();
	 	    }
	 	}
	 	//window.onbeforeunload=goodbye;		 	  		
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
					id="onscreenMessage"></span></font>&nbsp;</div>
				<s:form action="javascript:void(0);" onsubmit="return false;" method="post">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="toUpdateScreenIdList" id="toUpdateScreenIdList" />
					<table width="100%" align="center" border="0">
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Search Criteria</legend>
							<table width="90%" align="center" border="0">
								<tr>
									<td>User Role</td>
									<td colspan="1"><s:select list="#session.roleListMap"
										name="userRole" id="userRole" headerKey=""
										headerValue="Please Select" theme="simple"
										onchange="fnResetUserIdList(this);fnSearch();" /></td>
									<td align="right" nowrap><label>User ID(Case
									Sensitive)</label></td>
									<td align="left"><s:textfield name="userIdList"
										id="userIdList" maxlength="500" cssClass="textbox"
										theme="simple" autocomplete="off"
										onchange="fnResetRole(this);" /><input type="button"
										name="btnNext" id="btnGo" value="GO" class="smallbutton"
										onclick="javascript:fnSearch();" /></td>
								</tr>
							</table>
							</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
							<fieldset><legend>Mapping Details</legend> <s:if
								test="roleScreenMappingDetailsList.size() > 0">
								<table id="mapping_table" class="table-grid">
									<tr>
										<th align="center" rowspan="2" width="5%">SL</th>
										<th align="center" rowspan="2" width="5%">ID</th>
										<th align="center" rowspan="2" width="35%">Screen
										Description</th>
										<th align="center" rowspan="1" colspan="2" width="40%">Effective
										Date</th>
										<th align="center" rowspan="2" width="15%">Access</th>
									</tr>
									<tr>
										<th align="center" rowspan="1">FROM</th>
										<th align="center" rowspan="1">TO</th>
									</tr>
									<s:iterator value="roleScreenMappingDetailsList" status="row">
										<tr onMouseOver="this.bgColor='yellow';"
											onMouseOut="this.bgColor='white';"
											title=" Info : Access set as per <s:property value="accessBy" />                         ! ">
											<td align="center"><s:property value="slNo" /></td>
											<td align="center"><s:property value="screenId" /></td>
											<td align="left"><s:property value="screenDesc" /><input
												type="hidden"
												name="screenId<s:property value="#row.count" />"
												id="screenId<s:property value="#row.count" />"
												value="<s:property value="screenId" />" /></td>
											<td align="center" width="20%">&nbsp;<s:property
												value="effectiveFromDate" />&nbsp;</td>
											<s:if test="null==effectiveFromDate">
												<td align="center" valign="middle" width="20%" nowrap>&nbsp;<s:property
													value="effectiveToDate" />&nbsp;</td>
											</s:if>
											<s:else>
												<td align="center" valign="middle" width="20%" nowrap
													title="Enter Date in DD/MM/YYYY format"><input
													type="text"
													name="effectiveToDate<s:property value="#row.count" />"
													id="effectiveToDate<s:property value="#row.count" />"
													size="20" maxlength="10" class="textbox"
													style="text-align: center;" autocomplete="off"
													value="<s:property value="effectiveToDate" />"
													onchange="fnUpdateEffectiveToDate('<s:property value="#row.count" />','<s:property value="screenId" />','<s:property value="accessBy" />',this);"
													onfocus="fnAccessBy('<s:property value="accessBy" />',this);" />
												<img src="/DataEntryApp/images/load.gif" width="25"
													border="0" title="Processing" style="display: none;"
													id="img<s:property value="#row.count" />" /></td>
											</s:else>
											<td align="center" width="5%"><s:if
												test="hasAccess==\"true\"">
												<b>By <s:property value="accessBy" /></b>&nbsp;<input
													type="checkbox" class="case" name="case"
													id="trCheckbox<s:property value="#row.count"/>"
													checked="checked"
													onclick="fnUpdate('<s:property value="#row.count" />','<s:property value="screenId" />','<s:property value="accessBy" />',this);"
													title="Access has been set By <s:property value="accessBy" />" />
											</s:if> <s:else>
												<b>By <s:property value="accessBy" /></b>&nbsp;<input
													type="checkbox" class="case" name="case"
													id="trCheckbox<s:property value="#row.count"/>"
													onclick="fnUpdate('<s:property value="#row.count" />','<s:property value="screenId" />','<s:property value="accessBy" />',this);"
													title="Set access By <s:property value="accessBy" />" />
											</s:else><img src="/DataEntryApp/images/load.gif" width="25"
												border="0" title="Processing" style="display: none;"
												id="imgCB<s:property value="#row.count" />" /></td>
										</tr>
									</s:iterator>
								</table>
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
		isFocused=true;
	});
	$(".textbox").blur(function() {	
		$(this).removeClass("textboxfocus"); 
		$(this).addClass("textbox");
		isFocused=false; 
	});		   
	$(".textbox-long").focus(function() { 
		$(this).removeClass("textbox-long"); 
		$(this).addClass("textbox-longfocus"); 
		isFocused=true;
	}); 
	$(".textbox-long").blur(function() {
		$(this).removeClass("textbox-longfocus"); 
		$(this).addClass("textbox-long"); 
		isFocused=false;
	});
	$("form").submit(function(e) {
		fnSearch();
	});
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
			evt = evt || window.event;
			if (evt.keyCode == 116) {  	//F5 Key Press 	
				event.returnValue = false;
	            event.keyCode = 0;
	            window.status = "Key F5 is disabled !";
			}
			if (evt.keyCode == 13 ) {  	//Enter Key Press
				if(!isProcessing && isFocused){
					fnSearch();
				}
			}/* 
			if (evt.keyCode == 27) {  //Esc Key Press 		    	 		    	
				
	 		 } */
		}; 	
    $(window).load(function () {
    	 //if IsRefresh cookie exists
    	 var IsRefresh = getCookie("IsRefresh");
    	 if (IsRefresh != null && IsRefresh != "") {
    	    //cookie exists then you refreshed this page(F5, reload button or right click and reload)
    	    //SOME CODE
    	   // alert('Browser was Refreshed');
    	    //DeleteCookie("IsRefresh");
    	 }
    	 else {
    	    //cookie doesnt exists then you landed on this page
    	    //SOME CODE
    	    setCookie("IsRefresh", "true", 1);
    	 }
    	});		
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
</body>
<%
	} catch (Exception e) {
		//e.printStackTrace();
	}
%>
</html>
