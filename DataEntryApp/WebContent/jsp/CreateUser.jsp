<!-- 
24 April 2016 Arnab Nandy (1227971)		
   - Added Two-factor authentication of Data Entry Users, according to JTrac DJB-4464 and OpenProject#1151
 -->
<!-- 
1st September 2016 Suraj Tiwari (1241359)		
   - Added ZRO Location tagging of Data Entry Users, according to JTrac DJB-4549
 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="com.tcs.djb.util.*"%>
<%@page import="com.tcs.djb.model.*"%>
<%@page import="com.tcs.djb.constants.DJBConstants"%><html>
<%
	try {
%>
<head>
<title>User Profile Page - Revenue Management System, Delhi Jal
Board</title>
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/tabs.css"/>" />
	<script type="text/javascript"
	src="<s:url value="/js/DJBPasswordPolicy.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery.1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<!--
Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 
-->
<script language="Javascript">
var zroLocationValDE='';
var zroLocationValALL='';
function removeSelected(selected,pageMode){
	selected.remove(selected.selectedIndex);
	sendRightSelectedBoxValues(pageMode);
}
function selectMoveRows(source,target,pageMode)
{
    var selectId='';
    var selectText='';
    for(j=0;j<target.options.length;j++){
        if(source.value==target.options[j].value){
            alert("Selected ZRO Location already exists");
          return;		
       	}
    }
    selectId=source.value;
   	selectText=source.options[source.selectedIndex].innerHTML;
    var newRow = new Option(selectText,selectId);
    target.options[target.length]=newRow;
    sendRightSelectedBoxValues(pageMode);    	    
    
}
function sendRightSelectedBoxValues(pageMode){
	var currentTab=Trim(document.forms[0].currentTab.value);
	var zro = '';
   	if(currentTab.indexOf("Data")>-1){
   	   	if(pageMode=='update'){
			zro = document.forms[0].zroLocationDEUpdateRight;
   	   	}else if(pageMode=='create'){
   	   		zro = document.forms[0].zroLocationDECreateRight;
   	   	}
    }else if(currentTab.indexOf("All")>-1){
    	if(pageMode=='update'){
			zro = document.forms[0].zroLocationLDAPUpdateRight;
   	   	}else if(pageMode=='create'){
   	   		zro = document.forms[0].zroLocationLDAPCreateRight;
   	   	}
    }
	var zroLocationList = [];
	for (var i = 0; i < zro.options.length; i++) {
    	zroLocationList.push(zro.options[i].value);
	}
	if(currentTab.indexOf("Data")>-1){
		zroLocationValDE=zroLocationList;
	}else if(currentTab.indexOf("All")>-1){
		zroLocationValALL=zroLocationList;
	}
}
</script>
<!--
END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 
-->
<script language=javascript>
	/* Start : Added by Arnab Nandy (1227971) on 09-06-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	var restrictedUserRole='<%=DJBConstants.RESTRICT_REG_FP_FOR_USER_ROLE%>';
	var userIdRegex='<%=DJBConstants.USER_ID_REGEX_JS%>';
	var restricRoleArr;
	/* End : Added by Arnab Nandy (1227971) on 09-06-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	function fnResetSpanSuccess() {
		if (null != document.getElementById('spanSuccess')
				&& 'undefined' != document.getElementById('spanSuccess')) {
			document.getElementById('spanSuccess').innerHTML = "&nbsp;";
		}
		var currentTab=document.getElementById('currentTab').value;
		if(currentTab.indexOf("Data")>-1){
			document.getElementById('btnSubmitDE').disabled = false;
		}
		if(currentTab.indexOf("All")>-1){
			document.getElementById('btnSubmitLDAP').disabled = false;				
		}
		if(currentTab.indexOf("Web")>-1){
			document.getElementById('btnSubmitWS').disabled = false;
		}
		/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		if(currentTab.indexOf("Bypass")>-1){
			document.getElementById('btnSubmitFP').disabled = false;
		}
		/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
	}
	function fnBack(){
		hideScreen();
		document.forms[0].action="createUser.action";
		document.forms[0].hidAction.value="back";
		document.forms[0].submit();
	}
</script>

<script type="text/javascript">
	function isValidPasswordPolicy(obj){	
		var passwd=obj.value;
		if(passwd!=''){
			if(validatePassword(passwd)){
				document.getElementById('onscreenMessage').innerHTML = "";
				return true;
			}else{
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please follow the password policy</b></font>";
				obj.focus();
				return false;
			}
		}
	}
	function fnOnSubmit() {
		hideScreen();
		return true;
	}
	function fnOnLoad() {
		if(document.forms[0].pageMode.value!='Update'){
			document.getElementById('btnSubmitDE').disabled = true;
			document.getElementById('btnSubmitLDAP').disabled = true;
			document.getElementById('btnSubmitLDAP').disabled = true;
		}
	}
	var selectedCheckboxValues="";
	var reSubmit="";
	function fnGetAllCheckedRole(selectedCheckboxCount){		
		var checkBoxId="";
		selectedCheckboxValues="";
		if (document.getElementById('role-select')) {
			var rows = document.getElementById('role-select').rows;
			for ( var i = 1; i < rows.length - 2; i++) {
				checkBoxId="roleLDAP"+i;
				//alert(document.getElementById(checkBoxId));
				//alert((document.getElementById(checkBoxId)&& document.getElementById(checkBoxId).checked));
				if(document.getElementById(checkBoxId)){
					if( document.getElementById(checkBoxId).checked){
						//alert(document.getElementById(checkBoxId).value);
						var cnValue=Trim(document.getElementById(checkBoxId).value);
						selectedCheckboxValues+=cnValue+",";
						if(cnValue=='de_portal'){
							document.getElementById('divDERole').style.display='block';
						}
					} else{
						var cnValue=Trim(document.getElementById(checkBoxId).value);
						if(cnValue=='de_portal'){
							document.getElementById('divDERole').style.display='none';
						}
					}
				}
			}
		}
		if(selectedCheckboxCount==0){
			selectedCheckboxValues="";
		}
		if(selectedCheckboxValues!=''){
			fnResetSpanSuccess();
		}
	}
	function fnSetExistingGroups(){
		var checkBoxId="";
		selectedCheckboxValues=document.forms[0].groupsLDAP.value;
		if (document.getElementById('role-select')) {
			var rows = document.getElementById('role-select').rows;
			for ( var i = 1; i < rows.length - 2; i++) {
				checkBoxId="roleLDAP"+i;
				//alert(document.getElementById(checkBoxId));
				//alert((document.getElementById(checkBoxId)&& document.getElementById(checkBoxId).checked));
				if(document.getElementById(checkBoxId)){
					var checkBoxValue=document.getElementById(checkBoxId).value;
					if( selectedCheckboxValues.indexOf(checkBoxValue)>-1){
						document.getElementById(checkBoxId).checked=true;
						if(checkBoxValue=='de_portal'){
							document.getElementById('divDERole').style.display='block';
						}
					} else{
						document.getElementById(checkBoxId).checked=false;
					}
				}
			}
		}
	}
	var isValid = false;
	function fnValidateUserId(obj) {
		var userId = Trim(obj.value);
		var currentTab=Trim(document.forms[0].currentTab.value);
		/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		document.getElementById('onscreenMessage').innerHTML="";
		var countDot = (userId.match(/\./g) || []).length;
		var countUndrScr = (userId.match(/\_/g) || []).length;
		var totalCntInvalid = countDot+countUndrScr;
		var isvalidUser=false;
		try {
			var userRegex = new RegExp(userIdRegex);
			isvalidUser=userRegex.test(userId);
		}catch(err){
		}
		
		if (!isvalidUser || totalCntInvalid>4){
			if(currentTab.indexOf("Data")>-1){
				document.getElementById('btnSubmitDE').disabled = true;
				showAjaxLoading(document
						.getElementById('imgInvalidDE'));
				hideAjaxLoading(document
						.getElementById('imgValidDE'));
			}
			if(currentTab.indexOf("All")>-1){
				document.getElementById('btnSubmitLDAP').disabled = true;
				showAjaxLoading(document
						.getElementById('imgInvalidLDAP'));
				hideAjaxLoading(document
						.getElementById('imgValidLDAP'));
			}
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>"+'<%=DJBConstants.INVALID_USER_ID_MESSAGE%>'+"</b></font>";
			return false;
		}
		/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		if (fnCheckUserId()) {
			reSubmit="";
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "createUserAJax.action?hidAction=validateUserId&userId="
					+ userId+"&currentTab="+currentTab;
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
				//alert('url>>'+url);                 
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
								isValid = false;
								if(currentTab.indexOf("Data")>-1){
									document.getElementById('btnSubmitDE').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidDE'));
									hideAjaxLoading(document
											.getElementById('imgValidDE'));
								}
								if(currentTab.indexOf("All")>-1){
									document.getElementById('btnSubmitLDAP').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidLDAP'));
									hideAjaxLoading(document
											.getElementById('imgValidLDAP'));
								}
								if(currentTab.indexOf("Web")>-1){
									document.getElementById('btnSubmitWS').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidWS'));
									hideAjaxLoading(document
											.getElementById('imgValidWS'));
								}
								/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
								if(currentTab.indexOf("Bypass")>-1){
									document.getElementById('btnSubmitFP').disabled = true;
								}
								/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
							} else if (responseString.indexOf("INVALID") > -1) {
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>User Already Exists. Please try with a Diffrent User.</b></font>";
								
								isValid = false;
								if(currentTab.indexOf("Data")>-1){
									document.getElementById('btnSubmitDE').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidDE'));
									hideAjaxLoading(document
											.getElementById('imgValidDE'));
								}
								if(currentTab.indexOf("All")>-1){
									document.getElementById('btnSubmitLDAP').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidLDAP'));
									hideAjaxLoading(document
											.getElementById('imgValidLDAP'));
								}
								if(currentTab.indexOf("Web")>-1){
									document.getElementById('btnSubmitWS').disabled = true;
									showAjaxLoading(document
											.getElementById('imgInvalidWS'));
									hideAjaxLoading(document
											.getElementById('imgValidWS'));
								}
								/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
								if(currentTab.indexOf("Bypass")>-1){
									document.getElementById('onscreenMessage').innerHTML = responseString.replace("INVALID : ", "");
									if(responseString.indexOf("bypassed")>-1)
									{
										document.getElementById('btnEnableFP').style.display = 'none';
										document.getElementById('btnDisableFP').style.display = 'block';
									}
									else
									{
										document.getElementById('btnEnableFP').style.display = 'none';
										document.getElementById('btnDisableFP').style.display = 'none';
									}
									document.getElementById('btnSubmitFP').disabled = true;
								}
								/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
							} else {								
								isValid = true;
								if(currentTab.indexOf("Data")>-1){
									document.getElementById('btnSubmitDE').disabled = false;
									showAjaxLoading(document
											.getElementById('imgValidDE'));
									hideAjaxLoading(document
											.getElementById('imgInvalidDE'));
								}
								if(currentTab.indexOf("All")>-1){
									document.getElementById('btnSubmitLDAP').disabled = false;
									showAjaxLoading(document
											.getElementById('imgValidLDAP'));
									hideAjaxLoading(document
											.getElementById('imgInvalidLDAP'));
								}
								if(currentTab.indexOf("Web")>-1){
									document.getElementById('btnSubmitWS').disabled = false;
									showAjaxLoading(document
											.getElementById('imgValidWS'));
									hideAjaxLoading(document
											.getElementById('imgInvalidWS'));
								}
								/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
								if(currentTab.indexOf("Bypass")>-1){
									document.getElementById('btnDisableFP').style.display = 'none';
									document.getElementById('btnEnableFP').style.display = 'block';
									document.getElementById('btnSubmitFP').disabled = false;
								}
								/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 */
								document.getElementById('onscreenMessage').innerHTML = responseString;
							}
						} else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
							isValid = false;
						}
					}
				};
				httpBowserObject.send(null);
			}
		} else {
			isValid = false;
			if(currentTab.indexOf("Data")>-1){
				document.getElementById('btnSubmitDE').disabled = true;
				showAjaxLoading(document
						.getElementById('imgInvalidDE'));
				hideAjaxLoading(document
						.getElementById('imgValidDE'));
			}
			if(currentTab.indexOf("All")>-1){
				document.getElementById('btnSubmitLDAP').disabled = true;
				showAjaxLoading(document
						.getElementById('imgInvalidLDAP'));
				hideAjaxLoading(document
						.getElementById('imgValidLDAP'));
			}
			if(currentTab.indexOf("Web")>-1){
				document.getElementById('btnSubmitWS').disabled = true;
				showAjaxLoading(document
						.getElementById('imgInvalidWS'));
				hideAjaxLoading(document
						.getElementById('imgValidWS'));
			}
			/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
			if(currentTab.indexOf("Bypass")>-1){
				document.getElementById('btnDisableFP').style.display = 'none';
				document.getElementById('btnEnableFP').style.display = 'none';
				document.getElementById('btnSubmitFP').disabled = true;
			}
			/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
		}
	}
	var confirmMessage;
	var resubmitCount=0;
	function fnCreateUserId() {
		document.getElementById('onscreenMessage').innerHTML = "";
		if(isValid){
			var currentTab=Trim(document.forms[0].currentTab.value);
			if(reSubmit==''){
				confirmMessage="";
				/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
				if(Trim(document.getElementById('FIRTextData').value)!='' && currentTab.indexOf("Data")>-1){
					confirmMessage+="Note that you have taken Biometric Impression which will Update Current one which is required for SSO Login.\n";
				}
				/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
				if(Trim(document.getElementById('FIRTextData').value)=='' && currentTab.indexOf("All")>-1){
					confirmMessage+="Note that you have not taken Biometric Impression which is required for SSO Login.\n";
				}
				confirmMessage+="Are You Sure you want to Create the User ?\nClick OK to continue else Cancel.";
			}else{
				resubmitCount++;
				if(resubmitCount==1){				
					confirmMessage+="\n\nNote: This is a Duplicate User Id and it may create Problem, This is highly recomendate not to do so.";
				}else{
					confirmMessage+="\n\nNote: You have tried to resubmit for "+resubmitCount+" times.";
				}
			}
			var url = "createUserAJax.action?hidAction=createUserId";
			url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
			if(currentTab.indexOf("Data")>-1){
				/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				var zroLocatonList=zroLocationValDE;
				/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				url+="&userId="	+ Trim(document.forms[0].userIdDE.value);
				url += "&userName=" + Trim(document.forms[0].userNameDE.value);
				url += "&userAddress=" + Trim(document.forms[0].userAddressDE.value);
				url += "&userRole=" + Trim(document.forms[0].userRoleDE.value);
				url += "&password=" + Trim(document.forms[0].passwordDE.value);
				/* Start : Added by Suraj Tiwari (1241359) on 26-08-2016 for adding ZroLocation of Data Entry 
				Users, according to JTrac DJB-4514 */
				url += "&zroLocation=" + zroLocatonList;
				/* End : Added by Suraj Tiwari (1241359) on 26-08-2016 for adding ZroLocation of Data Entry 
				Users, according to JTrac DJB-4514 */
				/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
				url += "&FIRTextData=" + Trim(document.getElementById('FIRTextData').value);
				/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
			}
			if(currentTab.indexOf("All")>-1){
				/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				var zroLocatonList=zroLocationValALL;
				/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
				url += "&firstName=" + Trim(document.forms[0].firstNameLDAP.value);
				url += "&lastName=" + Trim(document.forms[0].lastNameLDAP.value);
				url += "&userAddress=" + Trim(document.forms[0].userAddressLDAP.value);
				url += "&emailId=" + Trim(document.forms[0].emailLDAP.value);
				url += "&mobileNo=" + Trim(document.forms[0].mobileNoLDAP.value);
				url += "&password=" + Trim(document.forms[0].passwordLDAP.value);
				/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				url += "&zroLocation=" + zroLocatonList;
				/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
				Users, according to JTrac DJB-4549 */
				
				url += "&selectedGroupsLDAP=" + selectedCheckboxValues;
				url += "&deUserRole=" + Trim(document.forms[0].deUserRole.value);
				url += "&FIRTextData=" + Trim(document.getElementById('FIRTextData').value);
			}
			if(currentTab.indexOf("Web")>-1){
				url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
				url += "&userName=" + Trim(document.forms[0].userNameLDAP.value);
				url += "&userAddress=" + Trim(document.forms[0].userAddressLDAP.value);
				url += "&userRole=" + Trim(document.forms[0].userRoleLDAP.value);
				url += "&password=" + Trim(document.forms[0].passwordLDAP.value);
				/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
				url += "&FIRTextData=" + Trim(document.getElementById('FIRTextData').value);
				/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
				Users, according to JTrac DJB-4464 and OpenProject#1151 */
			}
			var httpBowserObject = null;
			if (window.ActiveXObject) {
				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				httpBowserObject = new XMLHttpRequest();
			} else {
				alert("Browser does not support AJAX...........");
				return;
			}
			if (httpBowserObject != null && confirm(confirmMessage)) {
				hideScreen();
				//alert('url>>'+url);                 
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
								document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							} else if(responseString.indexOf("WARNING:") > -1){
								confirmMessage=responseString;
								document.getElementById('onscreenMessage').innerHTML = "<font color='orange' size='2'><b>"+confirmMessage+"</b></font>";
								confirmMessage+="\nDo you want to create the same user Id for all other Application also?\n\nClick OK to continue else Cancel.";
								if(confirm(confirmMessage)){
									reSubmit="Y";
									showScreen();
									fnCreateUserId();
								}else{
									reSubmit="";
									showScreen();
									return;
								}
							}else{
								if(responseString.indexOf("Already") > -1){
									isValid=false;
								}
								document.getElementById('onscreenMessage').innerHTML = responseString;							
								showScreen();
							}
							if(currentTab.indexOf("Data")>-1){
								document.getElementById('btnSubmitDE').disabled = true;
							}
							if(currentTab.indexOf("All")>-1){
								document.getElementById('btnSubmitLDAP').disabled = true;
							}
							if(currentTab.indexOf("Web")>-1){
								document.getElementById('btnSubmitWS').disabled = true;
							}
						}else {
							document.getElementById('onscreenMessage').innerHTML = responseString;
							isValid = false;
							showScreen();
						}
					} /*else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						if(currentTab.indexOf("Data")>-1){
							document.getElementById('btnSubmitDE').disabled = true;
						}
						if(currentTab.indexOf("All")>-1){
							document.getElementById('btnSubmitLDAP').disabled = true;
						}
						if(currentTab.indexOf("Web")>-1){
							document.getElementById('btnSubmitWS').disabled = true;
						}
						showScreen();
					}*/
					
				};
				httpBowserObject.send(null);
			}else{
				if(currentTab.indexOf("Data")>-1){
					document.getElementById('btnSubmitDE').disabled = false;
				}
				if(currentTab.indexOf("All")>-1){
					document.getElementById('btnSubmitLDAP').disabled = false;
				}
				if(currentTab.indexOf("Web")>-1){
					document.getElementById('btnSubmitWS').disabled = false;
				}
			}
		}else{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter valid user Id.</font>";
		}
	}
	function fnUnlock(){
		confirmMessage="Are You Sure you want to Unlock the User ?\nClick OK to continue else Cancel.";
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "createUserAJax.action?hidAction=unlockUser";
		var currentTab=Trim(document.forms[0].currentTab.value);
		url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
		if(currentTab.indexOf("Data")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdDE.value);
		}
		if(currentTab.indexOf("All")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		if(currentTab.indexOf("Web")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		//alert('url>>'+url);               
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null && confirm(confirmMessage) &&reSubmit=='') {
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							showScreen();
						}else{
							document.getElementById('onscreenMessage').innerHTML = responseString;	
							if(currentTab.indexOf("Data")>-1){
								document.getElementById('btnSubmitDE').disabled = true;
							}
							if(currentTab.indexOf("All")>-1){
								document.getElementById('unlockLDAPSpanId').innerHTML = "";
							}
							if(currentTab.indexOf("Web")>-1){
								document.getElementById('btnSubmitWS').disabled = true;
							}						
							showScreen();
						}
					}else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						isValid = false;
						showScreen();
					}
				} 
			};
			httpBowserObject.send(null);
		}
	}	
	/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	function fnBypassFP(action){
		confirmMessage="Are You Sure you want to Enable/Disable Bypass Fingerprint for the User ?\nClick OK to continue else Cancel.";
		document.getElementById('onscreenMessage').innerHTML = "";
		var url;
		if(action=='enable') {
			url = "createUserAJax.action?hidAction=bypassFP";
		} else if(action == 'disable') {
			url = "createUserAJax.action?hidAction=bypassFPDisable";
		}
		
		var currentTab=Trim(document.forms[0].currentTab.value);
		url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
		if(currentTab.indexOf("Bypass")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdFP.value);
		}            
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null && confirm(confirmMessage) &&reSubmit=='') {
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							showScreen();
						}else{
							document.getElementById('onscreenMessage').innerHTML = responseString;	
							if(responseString.indexOf("Enabling")>-1){
								document.getElementById("btnEnableFP").style.display='none';
								document.getElementById("btnDisableFP").style.display='block';
							}
							if(responseString.indexOf("Disabling")>-1)
							{
								document.getElementById("btnDisableFP").style.display='none';
								document.getElementById("btnEnableFP").style.display='block';
							}
							if(currentTab.indexOf("Bypass")>-1){
								document.getElementById('btnSubmitFP').disabled = true;
							}					
							showScreen();
						}
					}else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						isValid = false;
						showScreen();
					}
				} 
			};
			httpBowserObject.send(null);
		}
	}	
	/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
	Users, according to JTrac DJB-4464 and OpenProject#1151 */
	function fnDisable(){
		confirmMessage="Are You Sure you want to Disabled the User ?\nClick OK to continue else Cancel.";
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "createUserAJax.action?hidAction=disableUser";
		var currentTab=Trim(document.forms[0].currentTab.value);
		url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
		if(currentTab.indexOf("Data")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdDE.value);
		}
		if(currentTab.indexOf("All")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		if(currentTab.indexOf("Web")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		//alert('url>>'+url);               
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null && confirm(confirmMessage) &&reSubmit=='') {
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							showScreen();
						}else{
							document.getElementById('onscreenMessage').innerHTML = responseString;	
							if(currentTab.indexOf("Data")>-1){
								var isActiveDESpanHTML="<font color=\"red\"><b>Disabled&nbsp;</b></font><input type=\"button\" name=\"btnActiveDE\" id=\"btnActiveDE\" value=\"Enable\" class=\"groovybutton\"	title=\"Click to Enable.\" onclick=\"javascript:fnEnable();\"/>";
								document.getElementById('isActiveDESpanId').innerHTML = isActiveDESpanHTML;
							}
							if(currentTab.indexOf("All")>-1){
								var isActiveLDAPSpanHTML="<font color=\"red\"><b>Disabled&nbsp;</b></font><input type=\"button\" name=\"btnActiveLDAP\" id=\"btnActiveLDAP\" value=\"Enable\" class=\"groovybutton\"	title=\"Click to Enable.\" onclick=\"javascript:fnEnable();\"/>";
								document.getElementById('isActiveLDAPSpanId').innerHTML = isActiveLDAPSpanHTML;
							}
							if(currentTab.indexOf("Web")>-1){
								document.getElementById('btnSubmitWS').disabled = true;
							}						
							showScreen();
						}
					}else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						isValid = false;
						showScreen();
					}
				} 
			};
			httpBowserObject.send(null);
		}
	}
	function fnEnable(){
		confirmMessage="Are You Sure you want to Enabled the User ?\nClick OK to continue else Cancel.";
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "createUserAJax.action?hidAction=enableUser";
		var currentTab=Trim(document.forms[0].currentTab.value);
		url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
		if(currentTab.indexOf("Data")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdDE.value);
		}
		if(currentTab.indexOf("All")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		if(currentTab.indexOf("Web")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
		}
		//alert('url>>'+url);               
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null && confirm(confirmMessage) &&reSubmit=='') {
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							showScreen();
						}else{
							document.getElementById('onscreenMessage').innerHTML = responseString;	
							if(currentTab.indexOf("Data")>-1){
								var isActiveDESpanHTML="<font color=\"green\"><b>Enabled&nbsp;</b></font><input type=\"button\" name=\"btnDisableDE\" id=\"btnDisableDE\" value=\"Disable\" class=\"groovybutton\"	title=\"Click to Disable.\" onclick=\"javascript:fnDisable();\"/>";
								document.getElementById('isActiveDESpanId').innerHTML = isActiveDESpanHTML;
							}
							if(currentTab.indexOf("All")>-1){
								var isActiveLDAPSpanHTML="<font color=\"green\"><b>Enabled&nbsp;</b></font><input type=\"button\" name=\"btnDisableLDAP\" id=\"btnDisableLDAP\" value=\"Disable\" class=\"groovybutton\"	title=\"Click to Disable.\" onclick=\"javascript:fnDisable();\"/>";
								document.getElementById('isActiveLDAPSpanId').innerHTML = isActiveLDAPSpanHTML;
							}
							if(currentTab.indexOf("Web")>-1){
								document.getElementById('btnSubmitWS').disabled = true;
							}						
							showScreen();
						}
					}else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						isValid = false;
						showScreen();
					}
				} 
			};
			httpBowserObject.send(null);
		}
	}
	function fnUpdateUser() {
		var currentTab=Trim(document.forms[0].currentTab.value);
		if(reSubmit==''){
			confirmMessage="";
			/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
			if(Trim(document.getElementById('FIRTextData').value)!='' && currentTab.indexOf("Data")>-1){
				confirmMessage+="Note that you have taken Biometric Impression which will Update Current one which is required for SSO Login.\n";
			}
			/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
			if(Trim(document.getElementById('FIRTextData').value)!='' && currentTab.indexOf("All")>-1){
				confirmMessage+="Note that you have taken Biometric Impression which will Update Current one which is required for SSO Login.\n";
			}
			confirmMessage+="Are You Sure you want to Update the User ?\nClick OK to continue else Cancel.";
		}else{
			resubmitCount++;
			if(resubmitCount==1){				
				confirmMessage+="\n\nNote: This is a Duplicate User Id and it may create Problem, This is highly recomendate not to do so.";
			}else{
				confirmMessage+="\n\nNote: You have tried to resubmit for "+resubmitCount+" times.";
			}
		}
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "createUserAJax.action?hidAction=updateUser";
		url+="&currentTab="	+ currentTab+"&reSubmit="+reSubmit;
		if(currentTab.indexOf("Data")>-1){
			/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			var zroLocatonList=zroLocationValDE;
			/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			url+="&userId="	+ Trim(document.forms[0].userIdDE.value);
			url += "&userName=" + Trim(document.forms[0].userNameDE.value);
			url += "&userAddress=" + Trim(document.forms[0].userAddressDE.value);
			url += "&userRole=" + Trim(document.forms[0].userRoleDE.value);
			url += "&password=" + Trim(document.forms[0].passwordDE.value);
			/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			url += "&zroLocation=" + zroLocatonList;
			/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
			url += "&FIRTextData=" + Trim(document.getElementById('FIRTextData').value);
			/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
			/*if(!fnValidateCreateDEUser()){
				return;
			}*/
		}
		if(currentTab.indexOf("All")>-1){
			/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			var zroLocatonList=zroLocationValALL;
			/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
			url += "&firstName=" + Trim(document.forms[0].firstNameLDAP.value);
			url += "&lastName=" + Trim(document.forms[0].lastNameLDAP.value);
			url += "&userAddress=" + Trim(document.forms[0].userAddressLDAP.value);
			url += "&emailId=" + Trim(document.forms[0].emailLDAP.value);
			url += "&mobileNo=" + Trim(document.forms[0].mobileNoLDAP.value);
			url += "&password=" + Trim(document.forms[0].passwordLDAP.value);
			/* Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			url += "&zroLocation=" + zroLocatonList;
			/* END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
			Users, according to JTrac DJB-4549 */
			
			url += "&selectedGroupsLDAP=" + selectedCheckboxValues;
			url += "&deUserRole=" + Trim(document.forms[0].deUserRole.value);
			url += "&FIRTextData=" + Trim(document.getElementById('FIRTextData').value);
		}
		if(currentTab.indexOf("Web")>-1){
			url+="&userId="	+ Trim(document.forms[0].userIdLDAP.value);
			url += "&userName=" + Trim(document.forms[0].userNameLDAP.value);
			url += "&userAddress=" + Trim(document.forms[0].userAddressLDAP.value);
			url += "&userRole=" + Trim(document.forms[0].userRoleLDAP.value);
			url += "&password=" + Trim(document.forms[0].passwordLDAP.value);
		}
		//alert('url>>'+url);               
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null && confirm(confirmMessage)) {
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
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>There was problem while processing. Please Retry.</b></font>";
							showScreen();
						} else if(responseString.indexOf("WARNING:") > -1){
							confirmMessage=responseString;
							document.getElementById('onscreenMessage').innerHTML = "<font color='orange' size='2'><b>"+confirmMessage+"</b></font>";
							confirmMessage+="\nDo you want to create the same user Id for all other Application also?\nClick OK to continue else Cancel.";
							if(confirm(confirmMessage)){
								reSubmit="Y";
								showScreen();
								fnCreateUserId();
							}else{
								reSubmit="";
								showScreen();
								return;
							}
						}else{
							if(responseString.indexOf("Already") > -1){
								isValid=false;
							}
							document.getElementById('onscreenMessage').innerHTML = responseString;							
							showScreen();
						}
						if(currentTab.indexOf("Data")>-1){
							document.getElementById('btnSubmitDE').disabled = true;
						}
						if(currentTab.indexOf("All")>-1){
							document.getElementById('btnSubmitLDAP').disabled = true;
						}
						if(currentTab.indexOf("Web")>-1){
							document.getElementById('btnSubmitWS').disabled = true;
						}
					}else {
						document.getElementById('onscreenMessage').innerHTML = responseString;
						isValid = false;
						showScreen();
					}
				} else {
					//document.getElementById('onscreenMessage').innerHTML = responseString;
					if(currentTab.indexOf("Data")>-1){
						document.getElementById('btnSubmitDE').disabled = true;
					}
					if(currentTab.indexOf("All")>-1){
						document.getElementById('btnSubmitLDAP').disabled = true;
					}
					if(currentTab.indexOf("Web")>-1){
						document.getElementById('btnSubmitWS').disabled = true;
					}
					//showScreen();
				}
				
			};
			httpBowserObject.send(null);
		}
	}
	function fnCheckUserId() {
		document.getElementById('btnSubmitDE').disabled = true;
		document.getElementById('btnSubmitLDAP').disabled = true;
		document.getElementById('btnSubmitWS').disabled = true;
		var currentTab=Trim(document.forms[0].currentTab.value);
		if(currentTab.indexOf("Data Entry")>-1){
			if (Trim(document.forms[0].userIdDE.value) == '') {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
				document.forms[0].userIdDE.focus();
				return false;
			}
			if (Trim(document.forms[0].userIdDE.value).length < 6) {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter User Id with minimum of 6 Characters long.</font>";
				document.forms[0].userIdDE.focus();
				return false;
			}
			if (Trim(document.forms[0].userIdDE.value).indexOf(" ") > -1) {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter User Id without a Blank Space.</font>";
				document.forms[0].userIdDE.focus();
				return false;
			}
		}
		if(currentTab.indexOf("All")>-1){
			if (Trim(document.forms[0].userIdLDAP.value) == '') {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
				document.forms[0].userIdLDAP.focus();
				return false;
			}
			if (Trim(document.forms[0].userIdLDAP.value).length < 6) {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter User Id with minimum of 6 Characters long.</font>";
				document.forms[0].userIdLDAP.focus();
				return false;
			}
			if (Trim(document.forms[0].userIdLDAP.value).indexOf(" ") > -1) {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter User Id without a Blank Space.</font>";
				document.forms[0].userIdLDAP.focus();
				return false;
			}
		}
		return true;
	}
	function fnValidateCreateAllUser(){
		if (Trim(document.forms[0].firstNameLDAP.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].firstNameLDAP.focus();
			return false;
		}
		if (Trim(document.forms[0].lastNameLDAP.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].lastNameLDAP.focus();
			return false;
		}
		if (Trim(document.forms[0].userAddressLDAP.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].userAddressLDAP.focus();
			return false;
		}
		/*if (!checkEmail(document.forms[0].emailLDAP)||Trim(document.forms[0].emailLDAP.value)=='') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].emailLDAP.focus();
			return false;
		}*/
		if (isNaN(Trim(document.forms[0].mobileNoLDAP.value))|| parseInt(Trim(document.forms[0].mobileNoLDAP.value)) <1000000000 ||Trim(document.forms[0].mobileNoLDAP.value)=='') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter valid Mobile No.</font>";
			document.forms[0].mobileNoLDAP.focus();
			return false;
		}
		if (Trim(document.forms[0].passwordLDAP.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].passwordLDAP.focus();
			return false;
		}
		if (Trim(document.forms[0].passwordLDAP.value)
				.toString() != Trim(
				document.forms[0].confirmPasswordLDAP.value)
				.toString()) {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Password and Confirmed Password Doesn't match.</font>";
			document.forms[0].confirmPasswordLDAP.focus();
			return false;
		}
		if(selectedCheckboxValues.length==0){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Select atleast one Application Role </font>";
			return false;
		}
		if(selectedCheckboxValues.indexOf("de_portal")>-1 && document.getElementById('deUserRole').value==''){
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Select Data Entry Application Role </font>";
			document.getElementById('deUserRole').focus();
			return false;
		}
		/* Start : Added by Arnab Nandy (1227971) on 08-06-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		if(restrictedUserRole.indexOf(",") > -1){
			restricRoleArr=restrictedUserRole.split(",");
			for(i=0;i<restricRoleArr.length;i++){
				if (Trim(document.forms[0].deUserRole.value) == restricRoleArr[i] && Trim(document.getElementById('FIRTextData').value)==''){
					document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Fingerprint Capture is necessary for this role, please capture the finger print.</b></font>";
					return false;
				}
			}
		}
		else{
			if (Trim(document.forms[0].deUserRole.value) == restrictedUserRole && Trim(document.getElementById('FIRTextData').value)==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Fingerprint Capture is necessary for this role, please capture the finger print.</b></font>";
				return false;
			}
		}
		/* End : Added by Arnab Nandy (1227971) on 08-06-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		return true;
	}
	function fnValidateCreateDEUser(){
		if (Trim(document.forms[0].userNameDE.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Enter All Mandatory Fields.</font>";
			document.forms[0].userNameDE.focus();
			return false;
		}
		if (Trim(document.forms[0].userAddressDE.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Enter All Mandatory Fields.</b></font>";
			document.forms[0].userAddressDE.focus();
			return false;
		}
		if (Trim(document.forms[0].userRoleDE.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Enter All Mandatory Fields.</b></font>";
			document.forms[0].userRoleDE.focus();
			return false;
		}
		/*if (Trim(document.forms[0].passwordDE.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'>Please Enter All Mandatory Fields.</font>";
			document.forms[0].passwordDE.focus();
			return false;
		}*/
		if(validatePassword(document.forms[0].passwordDE.value)){
			if (Trim(document.forms[0].passwordDE.value)
					.toString() != Trim(
					document.forms[0].confirmPasswordDE.value)
					.toString()) {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Password and Confirmed Password Doesn't match.</b></font>";
				document.forms[0].confirmPasswordDE.focus();
				return false;
			}
		}else{
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please follow the password policy</b></font>";
			document.forms[0].passwordDE.focus();
			return false;
		}
		if (Trim(document.forms[0].userRoleDE.value) == '1') {
			if (!confirm('Are You sure You want to Provide ADMIN role to the user ?')) {
				showScreen();
				document.forms[0].userRoleDE.value = "";
				document.forms[0].userRoleDE.focus();
				return false;
			}
		}
		/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		if(restrictedUserRole.indexOf(",") > -1){
			restricRoleArr=restrictedUserRole.split(",");
			for(i=0;i<restricRoleArr.length;i++){
				if (Trim(document.forms[0].userRoleDE.value) == restricRoleArr[i] && Trim(document.getElementById('FIRTextData').value)==''){
					document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Fingerprint Capture is necessary for this role, please capture the finger print.</b></font>";
					return false;
				}
			}
		}
		else{
			if (Trim(document.forms[0].userRoleDE.value) == restrictedUserRole && Trim(document.getElementById('FIRTextData').value)==''){
				document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Fingerprint Capture is necessary for this role, please capture the finger print.</b></font>";
				return false;
			}
		}
		/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
		Users, according to JTrac DJB-4464 and OpenProject#1151 */
		return true;
	}
	function fnSetStrength(obj) {
		var passwd=Trim(obj.value);
		if(validatePassword(passwd)){
			switch (obj.value.length) {
			case 0:
				document.getElementById("passwordStrengthDE").style.width = '30%';
				document.getElementById("passwordStrengthDE").style.background = 'red';
				document.getElementById("passwordStrengthDE").title = "Very Bad";
				break;
			case 1:
				document.getElementById("passwordStrengthDE").style.width = '20%';
				document.getElementById("passwordStrengthDE").style.background = 'red';
				document.getElementById("passwordStrengthDE").title = "Very Bad";
				break;
			case 2:
				document.getElementById("passwordStrengthDE").style.width = '10%';
				document.getElementById("passwordStrengthDE").style.background = 'red';
				document.getElementById("passwordStrengthDE").title = "Bad";
				break;
			case 3:
				document.getElementById("passwordStrengthDE").style.width = '5%';
				document.getElementById("passwordStrengthDE").style.background = 'red';
				document.getElementById("passwordStrengthDE").title = "Bad";
				break;
			case 4:
				document.getElementById("passwordStrengthDE").style.width = '0%';
				document.getElementById("passwordStrengthDE").style.background = 'orange';
				document.getElementById("passwordStrengthDE").title = "Bad";
				break;
			case 5:
				document.getElementById("passwordStrengthDE").style.width = '5%';
				document.getElementById("passwordStrengthDE").style.background = 'orange';
				document.getElementById("passwordStrengthDE").title = "Weak";
				break;
			case 6:
				document.getElementById("passwordStrengthDE").style.width = '10%';
				document.getElementById("passwordStrengthDE").style.background = 'orange';
				document.getElementById("passwordStrengthDE").title = "Weak";
				break;
			case 7:
				document.getElementById("passwordStrengthDE").style.width = '15%';
				document.getElementById("passwordStrengthDE").style.background = 'green';
				document.getElementById("passwordStrengthDE").title = "Strong";
				break;
			case 8:
				document.getElementById("passwordStrengthDE").style.width = '20%';
				document.getElementById("passwordStrengthDE").style.background = 'green';
				document.getElementById("passwordStrengthDE").title = "Strong";
				break;
			default:
				document.getElementById("passwordStrengthDE").style.width = '30%';
				document.getElementById("passwordStrengthDE").style.background = 'green';
				document.getElementById("passwordStrengthDE").title = "Strong";
				break;
			}
		}
	}
	function fnSetStrengthLDAP(obj) {
		var passwd=Trim(obj.value);
		if(validatePassword(passwd)){
			switch (obj.value.length) {
			case 6:
				document.getElementById("passwordStrengthLDAP").style.width = '10%';
				document.getElementById("passwordStrengthLDAP").style.background = 'orange';
				document.getElementById("passwordStrengthLDAP").title = "OK";
				break;
			case 7:
				document.getElementById("passwordStrengthLDAP").style.width = '15%';
				document.getElementById("passwordStrengthLDAP").style.background = 'orange';
				document.getElementById("passwordStrengthLDAP").title = "Good";
				break;
			case 8:
				document.getElementById("passwordStrengthLDAP").style.width = '20%';
				document.getElementById("passwordStrengthLDAP").style.background = 'green';
				document.getElementById("passwordStrengthLDAP").title = "Strong";
				break;
			case 9:
				document.getElementById("passwordStrengthLDAP").style.width = '30%';
				document.getElementById("passwordStrengthLDAP").style.background = 'green';
				document.getElementById("passwordStrengthLDAP").title = "Strong";
				break;
			default:
				if(obj.value.length>6){
				document.getElementById("passwordStrengthLDAP").style.width = '30%';
				document.getElementById("passwordStrengthLDAP").style.background = 'green';
				document.getElementById("passwordStrengthLDAP").title = "Strong";
				}else{
					document.getElementById("passwordStrengthLDAP").style.width = '30%';
					document.getElementById("passwordStrengthLDAP").style.background = 'red';
					document.getElementById("passwordStrengthLDAP").title = "Follow Password Policy";
				}
				break;
			}
		}else{
			document.getElementById("passwordStrengthLDAP").style.width = '30%';
			document.getElementById("passwordStrengthLDAP").style.background = 'red';
			document.getElementById("passwordStrengthLDAP").title = "Follow Password Policy";
		}
	}
</script>
<script type="text/javascript">
        function fnEnrollBiometric(){
        	/* Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
        	document.getElementById('btnSubmitDE').disabled = false;
        	document.getElementById('btnSubmitLDAP').disabled = false;
        	/* End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
			Users, according to JTrac DJB-4464 and OpenProject#1151 */
        	var w=130,h=120;				
			var left = (screen.width/2)-(w);
			var top = (screen.height/2)-(h);		
        	window.open("/DataEntryApp/jsp/BiometricScanner.jsp", "_blank", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width="+w+", height="+h+", top="+top+", left="+left);
        }
	</script>
</head>
<body onload="fnOnLoad();">
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
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font></span></font>&nbsp;</div>
				<div>
				<table width="100%" align="center" border="0">
					<tr>
						<td align="center" valign="top">
						<fieldset>
						<table class="tab-table" cellpadding="0px" cellspacing="0px"
							border="0">
							<tr>
								<td align="center">
								<div class="centeredmenu">
								<ul>
									<li><a href="#" id="tab1" onclick="javascript:setStatusBarMessage('Create User for Data Entry Application Only.');" title="Create User for Data Entry Application Only."><b>Data Entry</b></a></li>
									<li><a href="#" id="tab2" onclick="javascript:setStatusBarMessage('Create User for Application User(Employee Portal, CC&B, BI Publisher, UCM).');" title="Create User for Application User(Employee Portal, CC&B, BI Publisher, UCM)."><b>All Application</b></a></li>
									<!-- Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
									Users, according to JTrac DJB-4464 and OpenProject#1151 -->
									<li><a href="#" id="tab4" onclick="javascript:setStatusBarMessage('Update User to bypass two phase authentication).');" title="Update User to bypass two phase authentication)."><b>Bypass Fingerprint</b></a></li>
									<!-- End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
									Users, according to JTrac DJB-4464 and OpenProject#1151 -->
									<!-- <li><a href="#" id="tab3" onclick="javascript:setStatusBarMessage('Create User for Web Service User Only.');" title="Create User for Web Service User Only."><b>Web	Service</b></a></li>-->
								</ul>
								</div>
								</td>
							</tr>
							<tr>
								<td align="center" nowrap>
								<form action="javascript:void(0);" name="formSetRoleAccess" onsubmit="return false;" method="post">
								<s:hidden name="hidAction" />
								<s:hidden name="currentTab" id="currentTab" />
								<s:hidden name="pageMode" id="pageMode" />
								<s:hidden name="userId"	id="userId" />
								<s:hidden name="defaultValue"	id="defaultValue" />
								<div id="divTab1" style="display: none;">
								<div title="Info: Created User is for Data Entry Application Only." style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>User is for Data
								Entry Application Only.</b></font>&nbsp;</div>
								<table width="95%" align="center" border="0">
									<tr>
										<td align="right" width="20%">User ID<font color="red"><sup>*</sup></font>
										</td>
										<td align="left"><s:textfield name="userIdDE"
											id="userIdDE" cssClass="textbox" theme="simple" theme="simple"
											autocomplete="off" onchange="fnResetSpanSuccess();fnValidateUserId(this);"/></td>
										<!-- Start : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
										Users, according to JTrac DJB-4464 and OpenProject#1151 -->
										<td align="left" rowspan="2"><s:hidden name="FIRTextData" id="FIRTextData" />
										<div id="biometricCapture" style="display:block;">
										<img src="/DataEntryApp/images/prompt_finger_print.gif" height="50px" alt="Click to Scan Fingerprint for Enrollment" border="0" onclick="javascript:fnEnrollBiometric();"/></div>
										<div id="biometricScanning" style="display:none;">
										<img src="/DataEntryApp/images/scanning.gif" height="50px" alt="Scan in Progress" border="0"/></div>
										<div id="biometricScanSuccess" style="display:none;">
										<img src="/DataEntryApp/images/scan_success.gif" height="50px" alt="Scan in Success, You may click to Scan again." border="0" onclick="javascript:fnEnrollBiometric();"/></div></td>
										<!-- End : Added by Arnab Nandy (1227971) on 24-05-2016 for Two-factor authentication of Data Entry 
										Users, according to JTrac DJB-4464 and OpenProject#1151 -->
										<s:if test="pageMode!=\"Update\"">	
										<td align="left" width="20%"><img id="imgValidDE"
											style="display: none;" src="/DataEntryApp/images/valid.gif"
											border="0" title="User ID is available." /><img
											id="imgInvalidDE" style="display: none;"
											src="/DataEntryApp/images/invalid.gif" border="0"
											title="User ID already Exists." /></td>
										</s:if>
										<s:else>
										<td align="right">
											<b>Account Status:</b>&nbsp;<span id="isActiveDESpanId">	
											<s:if test="isActiveUserDE==\"N\"">
											<font color="red"><b>Disabled</b></font>												
													<input type="button" name="btnActiveDE"
											id="btnActiveDE" value="Enable" class="groovybutton"
											title="Click to Enable." onclick="javascript:fnEnable();"/>
													</s:if><s:else><font color="green"><b>Enabled</b></font>												
													<input type="button" name="btnDisabledDE"
											id="btnDisabledDE" value="Disable" class="groovybutton"
											title="Click to Disable" onclick="javascript:fnDisable();"/></s:else></span>&nbsp;&nbsp;</td>
									</s:else>
									</tr>
									<tr>
										<td align="right" width="40%">Name<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" width="15%"><s:textfield
											name="userNameDE" id="userNameDE" style="text-align: left;"
											cssClass="textbox" theme="simple" onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="45%">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">Address<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" colspan="2"><s:textfield name="userAddressDE"
											id="userAddressDE" cssClass="textbox-long" theme="simple"
											onchange="fnResetSpanSuccess();" /></td>
									</tr>
									<tr>
										<td align="right">Role<font color="red"><sup>*</sup></font>
										</td>
										<td align="left"><s:select list="#session.roleListMap"
											name="userRoleDE" id="userRoleDE" headerKey=""
											headerValue="Please Select" theme="simple"
											cssClass="selectbox" onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">Password<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:password name="passwordDE"
											id="passwordDE" cssClass="password" theme="simple"
											autocomplete="off" onchange="fnResetSpanSuccess();"
											onkeyup="fnSetStrength(this);isValidPasswordPolicy(this);"/></td>
										<td align="left" width="20%">
										<table border=0 width="30%" height="2px" bgcolor="red"
											id="passwordStrengthDE">
											<tr>
												<td>&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td align="right">Confirm Password<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:password
											name="confirmPasswordDE" id="confirmPasswordDE"
											cssClass="password" theme="simple" autocomplete="off"
											onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									<!-- Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
										Users, according to JTrac DJB-4549 -->
										
									<s:if test="pageMode!=\"Update\"">
									<tr>
										<td align="right">ZRO Location</td>
										<td align="left">
										
										<s:select id="zroLocationDECreateLeft" multiple="true" list="#session.zroLocCharValListMap" size="6"
												name="zroLocationDECreateLeft" theme="simple" cssClass="selectall"/>
										</td>
										<td align="center" valign="middle"><input type="Button"
											value="Add >>" style="width: 90px"
											onClick="selectMoveRows(document.forms[0].zroLocationDECreateLeft,document.forms[0].zroLocationDECreateRight,'create')"><br>
										<br>
										<input type="Button" value="<< Remove" style="width: 90px"
											onClick="removeSelected(document.forms[0].zroLocationDECreateRight,'create')">
										</td>
										<td><select name="zroLocationDECreateRight" id="zroLocationDECreateRight" 
											size="6" MULTIPLE onchange="fnResetSpanSuccess();">
											</select>
											
											<td align="left" width="20%">&nbsp;</td>
									</tr>
									</s:if>
									<s:elseif test="pageMode==\"Update\"">
									<tr>
										<td align="right">ZRO Location</td>
										<td align="left">
										
										<s:select id="zroLocationDEUpdateLeft" multiple="true" list="#session.zroLocCharValListMap" size="6"
												name="zroLocationDEUpdateLeft" theme="simple" cssClass="selectall"/>
										</td>
										<td align="center" valign="middle"><input type="Button"
											value="Add >>" style="width: 90px"
											onClick="selectMoveRows(document.forms[0].zroLocationDEUpdateLeft,document.forms[0].zroLocationDEUpdateRight,'update')"><br>
										<br>
										<input type="Button" value="<< Remove" style="width: 90px"
											onClick="removeSelected(document.forms[0].zroLocationDEUpdateRight,'update')">
										</td>
										<td><s:select name="zroLocationDEUpdateRight" id="zroLocationDEUpdateRight" list="zroLocationUpdateListDE" theme="simple"
											size="6" multiple="true" onchange="fnResetSpanSuccess();"/>
               
									
										</td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									
									</s:elseif>
									<!-- END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
										Users, according to JTrac DJB-4549 -->
									<tr>
										<td align="right" colspan="3">&nbsp;
										</td>
									</tr>
									<s:if test="pageMode==\"Update\"">
									<tr>
										<td align="center">&nbsp;</td>
										<td align="left"><input type="submit" name="btnSubmitDE"
											id="btnSubmitDE" value="  Update  " class="groovybutton"
											title="Click to Update User Details." /></td>
										<td align="right" width="20%"><input type="button" name="btnBackDE"
											id="btnBackDE" value="  Back  " class="groovybutton"
											title="Click to Go Back." onclick="javascript:fnBack();"/>&nbsp;&nbsp;&nbsp;</td>
									</tr></s:if>
									<s:else>
									<tr>
										<td align="center">&nbsp;</td>
										<td align="left"><input type="submit" name="btnSubmitDE"
											id="btnSubmitDE" value="Create User " class="groovybutton"
											title="Click to create User ID" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									</s:else>
									<tr>
										<td align="right" colspan="3"><font color="red" size="2">*</font>
										marked Fields are Mandatory.</td>
									</tr>
								</table>
								</div>
								<div id="divTab2" style="display: none;">
								<div title="Info:Created User is for Application User (Employee Portal, CC&B, BI Publisher, UCM)." style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>User is for
								Application User(Employee Portal, CC&B, BI Publisher, UCM).</b></font>&nbsp;</div>
								<table width="95%" align="center" border="0">
									<tr>
										<td align="right" >User ID<font color="red"><sup>*</sup></font>
										</td>
										<td align="left"><s:textfield name="userIdLDAP"
											id="userIdLDAP" maxlength="20" cssClass="textbox" theme="simple"
											autocomplete="off" onchange="fnResetSpanSuccess();fnValidateUserId(this);"/></td>
										<s:if test="pageMode!=\"Update\"">	
										<td align="left" width="60%"><img id="imgValidLDAP"
											style="display: none;" src="/DataEntryApp/images/valid.gif"
											border="0" title="User ID is available." /><img
											id="imgInvalidLDAP" style="display: none;"
											src="/DataEntryApp/images/invalid.gif" border="0"
											title="User ID already Exists." /></td>
										</s:if>
										<s:else>
										<td align="right" width="60%">
											<b>Account Status:</b>&nbsp;<span id="isActiveLDAPSpanId">	
											<s:if test="isActiveUserLDAP==\"NO\"">
											<font color="red"><b>Disabled</b></font>												
													<input type="button" name="btnActiveLDAP"
											id="btnActiveLDAP" value="Enable" class="groovybutton"
											title="Click to Enable." onclick="javascript:fnEnable();"/>
													</s:if><s:else><font color="green"><b>Enabled</b></font>												
													<input type="button" name="btnDisabledLDAP"
											id="btnDisabledLDAP" value="Disable" class="groovybutton"
											title="Click to Disable" onclick="javascript:fnDisable();"/></s:else></span><span id="unlockLDAPSpanId">
											&nbsp;<s:if test="isLockedUserLDAP==\"YES\"">
													<font color="red"><b>Locked</b></font>
													<input type="button" name="btnUnlockLDAP"
											id="btnUnlockLDAP" value="Unlock" class="groovybutton"
											title="Click to Unlock." onclick="javascript:fnUnlock();"/>
													</s:if></span>&nbsp;&nbsp;</td>
									</s:else>
									</tr>
									<tr>
										<td align="right" >First Name<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" width="15%"><s:textfield
											name="firstNameLDAP" id="firstNameLDAP" maxlength="50"
											cssClass="textbox" theme="simple"
											onchange="fnResetSpanSuccess();" title="First Name"/></td>
										<td align="left" rowspan="2"><s:hidden name="FIRTextData" id="FIRTextData" />
										<div id="biometricCapture" style="display:block;">
										<img src="/DataEntryApp/images/prompt_finger_print.gif" height="50px" alt="Click to Scan Fingerprint for Enrollment" border="0" onclick="javascript:fnEnrollBiometric();"/></div>
										<div id="biometricScanning" style="display:none;">
										<img src="/DataEntryApp/images/scanning.gif" height="50px" alt="Scan in Progress" border="0"/></div>
										<div id="biometricScanSuccess" style="display:none;">
										<img src="/DataEntryApp/images/scan_success.gif" height="50px" alt="Scan in Success, You may click to Scan again." border="0" onclick="javascript:fnEnrollBiometric();"/></div></td>
									</tr>
									<tr>
										<td align="right" >Last Name<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" width="15%"><s:textfield
											name="lastNameLDAP" id="lastNameLDAP" maxlength="50"
											cssClass="textbox" theme="simple"
											onchange="fnResetSpanSuccess();" title="Last Name"/></td>
									</tr>
									<tr>
										<td align="right">Address<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" colspan="2"><s:textfield
											name="userAddressLDAP" id="userAddressLDAP" maxlength="200"
											cssClass="textbox-long" theme="simple"
											onchange="fnResetSpanSuccess();" /></td>
									</tr>
									<tr>
										<td align="right">Email<font color="red"><sup></sup></font>
										</td>
										<td align="left"><s:textfield name="emailLDAP"
											id="emailLDAP" cssClass="textbox" theme="simple"
											onchange="fnResetSpanSuccess();" onblur="checkEmail(this);"/></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									<tr title="10 digit Mobile Number">
										<td align="right">Mobile No<font color="red"><sup>*</sup></font>
										</td>
										<td align="left"><s:textfield name="mobileNoLDAP"
											id="mobileNoLDAP" maxlength="10" cssClass="textbox" theme="simple"
											onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									<tr>
										<td align="right"><label id="lblPassword">Password</label><font color="red"><sup>*</sup></font></td>
										<td align="left"><span id="hint"><s:password
											name="passwordLDAP" id="passwordLDAP"
											cssClass="password" theme="simple" autocomplete="off"
											onchange="fnResetSpanSuccess();" onkeyup="fnSetStrengthLDAP(this);isValidPasswordPolicy(this);"/></span></td>
										<td align="left" width="20%">
										<table border=0 width="30%" height="2px" bgcolor="red"
											id="passwordStrengthLDAP">
											<tr>
												<td>&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td align="right">Confirm Password<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:password
											name="confirmPasswordLDAP" id="confirmPasswordLDAP"
											cssClass="password" theme="simple" autocomplete="off"
											onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="20%">&nbsp;<s:hidden name="groupsLDAP"	id="groupsLDAP" /></td>
									</tr>
									<!-- Start : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
										Users, according to JTrac DJB-4549 -->
									<s:if test="pageMode!=\"Update\"">
										<tr>
											<td align="right" width="33%">ZRO Location</td>
											<td align="left" colspan="2">
												<table border="0" cellpadding="5px" width="100%">
													<tr>
													<td width="50px">
														<s:select id="zroLocationLDAPCreateLeft" multiple="true" list="#session.zroLocCharValListMap" size="6" name="zroLocationLDAPCreateLeft" theme="simple"  style="width:280px"/>
													</td>
													<td align="center" valign="middle" width="50px">
														<input type="Button"
														value="Add >>" style="width: 90px"
														onClick="selectMoveRows(document.forms[0].zroLocationLDAPCreateLeft,document.forms[0].zroLocationLDAPCreateRight,'create')"><br>
														<br>
														<input type="Button" value="<< Remove" style="width: 90px"
															onClick="removeSelected(document.forms[0].zroLocationLDAPCreateRight,'create')">
													</td>
													<td>
														<select name="zroLocationLDAPCreateRight" id="zroLocationLDAPCreateRight"
														size="6" MULTIPLE onchange="fnResetSpanSuccess();"  style="width:280px">
													</select>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									</s:if>
									<s:elseif test="pageMode==\"Update\"">
										<tr>
											<td align="right" width="33%">ZRO Location</td>
											<td align="left" colspan="2">
												<table border="0" cellpadding="5px" width="100%">
													<tr>
													<td width="50px">
														<s:select id="zroLocationLDAPCreateLeft" multiple="true" list="#session.zroLocCharValListMap" size="6" name="zroLocationLDAPCreateLeft" theme="simple"  style="width:280px"/>
													</td>
													<td align="center" valign="middle" width="50px">
														<input type="Button"
														value="Add >>" style="width: 90px"
														onClick="selectMoveRows(document.forms[0].zroLocationLDAPCreateLeft,document.forms[0].zroLocationLDAPCreateRight,'create')"><br>
														<br>
														<input type="Button" value="<< Remove" style="width: 90px"
															onClick="removeSelected(document.forms[0].zroLocationLDAPCreateRight,'create')">
													</td>
													<td>
														<s:select name="zroLocationLDAPCreateRight" id="zroLocationLDAPCreateRight" list="zroLocationUpdateListDE" theme="simple"
														size="6" multiple="true" onchange="fnResetSpanSuccess();"  style="width:280px"/>
													</td>
												</tr>
											</table>
										</td>
										</tr>
										
									</s:elseif>
									<!-- END : Added by Suraj Tiwari (1241359) on 01-09-2016 for ZRO Location tagging of Data Entry 
										Users, according to JTrac DJB-4549 -->
									<tr>
										<td align="right">Access To Application</td>
										<td align="left" colspan="2">
										<table class="table-grid" id="role-select" border="0" width="100%" title="Tp provide access to the Application Check the Checkbox and select the Role for the Application">
											<thead>
												<tr>
													<th align="right" width="10%" title="Click to Select All"><input type="checkbox"
														id="selectall" name="selectall" value="all" />All</th>
													<th align="left">Application</th>
													<th align="left">Role</th>
												</tr>
											</thead>
											<%String[] objectClass = PropertyUtil.getProperty("LDAP_OBJECT_CLASS").split(",");
											List<LDAPUserGroup> ldapUserGroupListSession = null;
											String objectClassStr="";
											int rowNumber=1;
											for (int i = 0; null != objectClass && i < objectClass.length; i++) {
												// System.out.println("objectClass[i]::"+objectClass[i]);
												Object sessionObject=session.getAttribute(objectClass[i]);
												if(null!=sessionObject){
													ldapUserGroupListSession = (ArrayList<LDAPUserGroup>)sessionObject;
												}
												for (int j = 0; null != ldapUserGroupListSession &&ldapUserGroupListSession.size()>0
														&& j < ldapUserGroupListSession.size(); j++) {	
													LDAPUserGroup ldapUserGroupSession = ldapUserGroupListSession.get(j);
													%>
													<%if(!objectClassStr.equalsIgnoreCase(objectClass[i])){ %>
											<tr bgcolor="#21c5d8">
											<td align="center" colspan="3"><font color="blue"><b><%=ldapUserGroupSession.getApplicationName() %></b></font>&nbsp;</td>
											</tr>
											<tr>
											<%rowNumber++; %>
											<td align="center" ><input type="checkbox" class="case" id="roleLDAP<%=rowNumber %>"
													name="roleLDAP<%=rowNumber %>" value="<%=ldapUserGroupSession.getCnValue() %>"/></td>
												<td><%=ldapUserGroupSession.getApplicationName() %>&nbsp;</td>
												<td><%=ldapUserGroupSession.getDisplayName() %>&nbsp;
												<%if("de_portal".equals(ldapUserGroupSession.getCnValue())){ %>												
												<div id="divDERole" style="display:none;">
												<br/>
												<s:select list="#session.roleListMap"
													name="deUserRole" id="deUserRole" headerKey=""
													headerValue="Please Select a Role" theme="simple" onchange="fnResetSpanSuccess();"/><font color="red"><sup>*</sup></font><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" title="For a user to get access to Data Entry Application role must be selected."/>
												</div><%} %></td>
											</tr>
											<%}else{ %>
											<tr>
											<%rowNumber++; %>
												<td align="center" ><input type="checkbox" class="case" id="roleLDAP<%=rowNumber %>"
													name="roleLDAP<%=rowNumber %>" value="<%=ldapUserGroupSession.getCnValue() %>"/></td>
												<td><%=ldapUserGroupSession.getApplicationName() %>&nbsp;</td>
												<td><%=ldapUserGroupSession.getDisplayName() %>&nbsp;
												<%if("de_portal".equals(ldapUserGroupSession.getCnValue())){ %>
												<div id="divDERole" style="display:none;">
												<br/>
												<s:select list="#session.roleListMap"
													name="deUserRole" id="deUserRole" headerKey=""
													headerValue="Please Select a Role" theme="simple" onchange="fnResetSpanSuccess();"/><font color="red"><sup>*</sup></font><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" title="For a user to get access to Data Entry Application role must be selected."/>
												</div><%} %></td>
											</tr>
											<%} 
													objectClassStr=objectClass[i];
												}
											} %>										
											
										</table>
										</td>
									</tr>
									<tr>
										<td align="right" colspan="3">&nbsp;
										</td>
									</tr>
									<s:if test="pageMode==\"Update\"">
									<tr>
										<td align="center">&nbsp;</td>
										<td align="center"><input type="submit" name="btnSubmitLDAP"
											id="btnSubmitLDAP" value="  Update  " class="groovybutton"
											title="Click to Update User Details." /></td>
										<td align="right" width="20%"><input type="button" name="btnBackLDAP"
											id="btnBackLDAP" value="  Back  " class="groovybutton"
											title="Click to Go Back." onclick="javascript:fnBack();"/>&nbsp;&nbsp;&nbsp;</td>
									</tr></s:if>
									<s:else>
									<tr>
										<td align="center">&nbsp;</td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;<input type="submit" name="btnSubmitLDAP"
											id="btnSubmitLDAP" value="Create User " class="groovybutton"
											title="Click to create User ID" /></td>
									</tr>
									</s:else>
									<tr>
										<td align="right" colspan="3"><font color="red" size="2">*</font>
										marked Fields are Mandatory.</td>
									</tr>
								</table>
								</div>
								<div id="divTab3" style="display: none;">
								<div title="Info: Created User is for Web Service User Only." style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>User is for Web
								Service User Only.</b></font>&nbsp;</div>
								<table width="95%" align="center" border="0">
									<tr>
										<td align="right">User ID<font color="red"><sup>*</sup></font>
										</td>
										<td align="left"><s:textfield name="userIdWS"
											id="userIdWS" cssClass="textbox" theme="simple"
											autocomplete="off" onchange="fnResetSpanSuccess();fnValidateUserId(this);"/></td>
										<td align="left" width="20%"><img id="imgValidWS"
											style="display: none;" src="/DataEntryApp/images/valid.gif"
											border="0" title="User ID is available." /><img
											id="imgInvalidWS" style="display: none;"
											src="/DataEntryApp/images/invalid.gif" border="0"
											title="User ID already Exists." /></td>
									</tr>
									<tr>
										<td align="right" width="40%">Name<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" width="15%"><s:textfield
											name="userNameWS" id="userNameWS" style="text-align: left;"
											cssClass="textbox" theme="simple" onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="45%">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">Address<font color="red"><sup>*</sup></font>
										</td>
										<td align="left" colspan="2"><s:textfield name="userAddressWS"
											id="userAddressWS" cssClass="textbox-long" theme="simple"
											onchange="fnResetSpanSuccess();" /></td>
									</tr>
									<tr>
										<td align="right">Password<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:password name="passwordWS"
											id="passwordWS" cssClass="password" theme="simple"
											autocomplete="off" onchange="fnResetSpanSuccess();"	onkeyup="fnSetStrengthWS(this);"/></td>
										<td align="left" width="20%">
										<table border=0 width="30%" height="2px" bgcolor="red"
											id="passwordStrengthWS">
											<tr>
												<td>&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td align="right">Confirm Password<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:password
											name="confirmPasswordWS" id="confirmPasswordWS"
											cssClass="password" theme="simple" autocomplete="off"
											onchange="fnResetSpanSuccess();" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									<tr>
										<td align="right" colspan="3">&nbsp;
										</td>
									</tr>
									<s:if test="pageMode==\"Update\""><tr>
										<td align="center">&nbsp;</td>
										<td align="left"><input type="submit" name="btnSubmitWS"
											id="btnSubmitWS" value="Update" class="groovybutton"
											title="Click to Update User Details." /></td>
										<td align="center" width="20%"><input type="button" name="btnSubmitWS"
											id="btnDeleteWS" value="Delete" class="groovybutton"
											title="Click to Delete User." /></td>
									</tr></s:if>
									<s:else>
									<tr>
										<td align="center">&nbsp;</td>
										<td align="left"><input type="submit" name="btnSubmitWS"
											id="btnSubmitWS" value="Create User " class="groovybutton"
											title="Click to create User ID" /></td>
										<td align="left" width="20%">&nbsp;</td>
									</tr>
									</s:else>
									<tr>
										<td align="right" colspan="3"><font color="red" size="2">*</font>
										marked Fields are Mandatory.</td>
									</tr>
								</table>
								</div>
								<!-- Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 -->
								<div id="divTab4" style="display: none;">
								<div title="Info: Update bypass authentication for users." style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>ByPass Fingerprint
								for users.</b></font>&nbsp;</div>
										<table width="70%" align="center" border="0">
											<tr>
												<td align="center">User ID<font color="red"><sup>*</sup></font>
												<s:textfield name="userIdFP"
													id="userIdFP" cssClass="textbox" theme="simple"
													autocomplete="off" onchange="fnResetSpanSuccess();fnValidateUserId(this);"/>
												</td>
												<td align="left">
													<input type="button" name="btnEnableFP"
													id="btnEnableFP" value="Enable " class="groovybutton" onclick="fnBypassFP('enable')"
													title="Click to enable bypass authentication for User ID" style="display: none"/>
													<input type="button" name="btnDisableFP"
													id="btnDisableFP" value="Disable " class="groovybutton" onclick="fnBypassFP('disable')"
													title="Click to disable bypass authentication for User ID" style="display: none"/>
												</td>
											</tr>
											<tr>
												<td>&nbsp;&nbsp;</td>
												<td>
												<input type="submit" name="btnSubmitFP"
												id="btnSubmitFP" value="Submit " class="groovybutton" onClick=""
												title="Click to bypass authentication for User ID" style="display: none"/>
												</td>
											</tr>
											
											<tr>
												<td>&nbsp;&nbsp;</td>
												<td align="right" colspan="3"><font color="red" size="2">*</font>
												marked Fields are Mandatory.</td>
											</tr>
										</table>
								</div>
								<!-- End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
								Users, according to JTrac DJB-4464 and OpenProject#1151 -->
								</form>
								</td>
							</tr>
						</table>
						</fieldset></td>
					</tr>
				</table>
				</div>
			</tr>
			<tr height="20px">
				<td align="center" valign="bottom"><%@include
					file="../jsp/Footer.html"%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//alert(document.forms[0].currentTab.value+'<>'+document.forms[0].pageMode.value);
						var pwdPolicy = "<div class=\"tooltip\"><b>Password Should Contain atleast 6 Alpha Numeric Characters which should:</b><br />";
						pwdPolicy += "1.Contain atleast one Uppercase Letter. <br />";
						pwdPolicy += "2.Contain atleast one Lowercase Letter.<br />";
						pwdPolicy += "3.Contain atleast one Numeric Character.<br />";
						pwdPolicy += "4.Contain atleast one Special Character other than [\' \" \& ;	: %] and blank space. </div>";
						var winWidth=$(document).width();
						var winHeight=$(document).height();
						var changeTooltipPosition = function(event) {
							var tooltipX = event.pageX - 8;
							var tooltipY = event.pageY + 8;							
							$('div.tooltip').css( {
								top : tooltipY,
								left : tooltipX
							});
						};

						var showTooltip = function(event) {
							$('div.tooltip').remove();							
							$(pwdPolicy).appendTo('body');
							changeTooltipPosition(event);
						};
						var showTooltipFixedPosition = function(event) {
							$('div.tooltip').remove();							
							$(pwdPolicy).appendTo('body');
							$('div.tooltip').css( {
								top : winHeight/3+50,
								left : winWidth/3+105
							});
						};
						var hideTooltip = function() {
							$('div.tooltip').remove();
						};

						$("span#hint,label#lblPassword,input#passwordLDAP,input#passwordDE'").bind( {
							mousemove : changeTooltipPosition,
							mouseenter : showTooltip,
							focus:showTooltipFixedPosition,
							blur:hideTooltip,
							mouseleave : hideTooltip
						});
						var currentTab=Trim(document.forms[0].currentTab.value);
						/* Start : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
						Users, according to JTrac DJB-4464 and OpenProject#1151 */
						if(currentTab.indexOf("Data")>-1){
							$("#tab1").addClass("active");
							document.getElementById('divTab4').style.display = 'none';
							document.getElementById('divTab3').style.display = 'none';
							document.getElementById('divTab2').style.display = 'none';
							document.getElementById('divTab1').style.display = 'block';
						} 
						else if(currentTab.indexOf("All")>-1){						
							$("#tab2").addClass("active");
							document.getElementById('divTab4').style.display = 'none';
							document.getElementById('divTab3').style.display = 'none';
							document.getElementById('divTab1').style.display = 'none';
							document.getElementById('divTab2').style.display = 'block';
							document.forms[0].currentTab.value="All Application";
						}
						else if(currentTab.indexOf("Bypass")>-1){						
							$("#tab4").addClass("active");
							document.getElementById('divTab3').style.display = 'none';
							document.getElementById('divTab1').style.display = 'none';
							document.getElementById('divTab2').style.display = 'none';
							document.getElementById('divTab4').style.display = 'block';
							document.forms[0].currentTab.value="Bypass Fingerprint";
						}else{
							$("#tab1").addClass("active");
							document.getElementById('divTab3').style.display = 'none';
							document.getElementById('divTab2').style.display = 'none';
							document.getElementById('divTab1').style.display = 'block';
							document.forms[0].currentTab.value="Data Entry";
						}
						/* End : Edited by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
						Users, according to JTrac DJB-4464 and OpenProject#1151 */
						if(document.forms[0].pageMode.value=='Update'){
							isValid=true;
							if(currentTab.indexOf("All")>-1){
								document.forms[0].userIdLDAP.value=document.forms[0].userId.value;
								document.forms[0].userIdLDAP.disabled=true;
								document.forms[0].passwordLDAP.value=document.forms[0].defaultValue.value;
								document.forms[0].confirmPasswordLDAP.value=document.forms[0].defaultValue.value;
								fnSetExistingGroups();
							}	
							if(currentTab.indexOf("Data")>-1){
								document.forms[0].userIdDE.value=document.forms[0].userId.value;
								document.forms[0].userIdDE.disabled=true;
								document.forms[0].passwordDE.value=document.forms[0].defaultValue.value;
								document.forms[0].confirmPasswordDE.value=document.forms[0].defaultValue.value;
							}
							/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
							Users, according to JTrac DJB-4464 and OpenProject#1151 */	
							if(currentTab.indexOf("Bypass")>-1){
								document.forms[0].userIdFP.value=document.forms[0].userId.value;
								document.forms[0].userIdFP.disabled=true;
							}
							/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
							Users, according to JTrac DJB-4464 and OpenProject#1151 */	
						}						
						$("a")
								.click(
										function() {
											if(document.forms[0].pageMode.value!='Update'){
												$(".active").removeClass("active");
												$(this).addClass("active");
												document.forms[0].currentTab.value = $(
														this).html();
												var tabId = $(this).attr('id');
												if (tabId == 'tab1') {
													/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab4').style.display = 'none';
													/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab3').style.display = 'none';
													document
															.getElementById('divTab2').style.display = 'none';
													document
															.getElementById('divTab1').style.display = 'block';
												}
												if (tabId == 'tab2') {
													/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab4').style.display = 'none';
													/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab3').style.display = 'none';
													document
															.getElementById('divTab1').style.display = 'none';
													document
															.getElementById('divTab2').style.display = 'block';
												}
												if (tabId == 'tab3') {
													/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab4').style.display = 'none';
													/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
													Users, according to JTrac DJB-4464 and OpenProject#1151 */
													document
															.getElementById('divTab1').style.display = 'none';
													document
															.getElementById('divTab2').style.display = 'none';
													document
															.getElementById('divTab3').style.display = 'block';
												}
												/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
												Users, according to JTrac DJB-4464 and OpenProject#1151 */
												if (tabId == 'tab4') {
													document
															.getElementById('divTab1').style.display = 'none';
													document
															.getElementById('divTab2').style.display = 'none';
													document
															.getElementById('divTab3').style.display = 'none';
													document
															.getElementById('divTab4').style.display = 'block';
												}
												/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
												Users, according to JTrac DJB-4464 and OpenProject#1151 */
												//document.getElementById('infoMessage').innerHTML = "<font color='red' size='2'><b>Note:	Users Created for "+$(this).html()+" Application.</b></font>";
											}else{
												
												/* Start : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
												Users, according to JTrac DJB-4464 and OpenProject#1151 
												document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>This is for Create User Only.</b></font>";
												*/
												
												$(".active").removeClass("active");
												$(this).addClass("active");
												document.forms[0].currentTab.value = $(
														this).html();
												var tabId = $(this).attr('id');
												if (tabId == 'tab4') {
													document.getElementById('onscreenMessage').innerHTML = "";
													document
															.getElementById('divTab1').style.display = 'none';
													document
															.getElementById('divTab2').style.display = 'none';
													document
															.getElementById('divTab3').style.display = 'none';
													document
															.getElementById('divTab4').style.display = 'block';
												}else{
													document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>This is for Create User Only.</b></font>";
												}
												/* End : Added by Arnab Nandy (1227971) on 31-05-2016 for Two-factor authentication of Data Entry 
												Users, according to JTrac DJB-4464 and OpenProject#1151 */
											}
											});
					});
	$("form")
			.submit(
					function(e) {
						document.getElementById('onscreenMessage').innerHTML = "";
						if( fnCheckUserId() && isValid ){
							if (document.forms[0].pageMode.value!='Update') {
								var currentTab=Trim(document.forms[0].currentTab.value);
								if(currentTab.indexOf("Data")>-1){
									if(!fnValidateCreateDEUser()){
										return;
									}else{
										fnCreateUserId();
									}									
								}
								if(currentTab.indexOf("All")>-1){
									if(!fnValidateCreateAllUser()){
										return;
									}else{
										fnCreateUserId();
									}
								}
							}else{
								var currentTab=Trim(document.forms[0].currentTab.value);
								if(currentTab.indexOf("Data")>-1){
									if(!fnValidateCreateDEUser()){
										return;
									}else{
										fnUpdateUser();
									}									
								}
								if(currentTab.indexOf("All")>-1){
									if(!fnValidateCreateAllUser()){
										return;
									}else{
										fnUpdateUser();
									}
								}
							}
						}else{
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Enter a valid / available User Id First.</b></font>";
						}
					});
	
	$('input[type="text"]').focus(function() {
		$(this).addClass("textboxfocus");
	});
	$('input[type="text"]').blur(function() {
		$(this).removeClass("textboxfocus");
		$(this).addClass("textbox");
	});
	$('.textbox-long').focus(function() {
		$(this).addClass("textbox-longfocus");
	});
	$('.textbox-long').blur(function() {
		$(this).removeClass("textbox-longfocus");
		$(this).addClass("textbox-long");
	});
	$('input[type="password"]').focus(function() {
		$(this).addClass("passwordfocus");
	});
	$('input[type="password"]').blur(function() {
		$(this).removeClass("passwordfocus");
		$(this).addClass("password");
	});
	$(function() { // add multiple select / deselect functionality     
		$("#selectall").click(function() {
			$('.case').attr('checked', this.checked);
			var selectedCheckboxCount=$(".case:checked").length;
			fnGetAllCheckedRole(selectedCheckboxCount);
		}); // if all checkbox are selected, check the selectall checkbox     // and viceversa     
		$(".case").click(function() {
			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");				
			} else {
				$("#selectall").removeAttr("checked");
			}
			var selectedCheckboxCount=$(".case:checked").length;
			fnGetAllCheckedRole(selectedCheckboxCount);
		});
	});	
</script>


<script type="text/javascript"
	src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<%
} catch (Exception e) {
	//e.printStackTrace();
	%>
	<script type="text/javascript">alert('There was a problem at the server, User must Logout and Login again.\nPlease Re Login and Retry. ');goLogout();</script>
	<%
}
%>
</html>