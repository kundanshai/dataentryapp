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
<title>Operation MRKey Review Page - Revenue Management System,
Delhi Jal Board</title>
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
	<link rel="stylesheet" type="text/css"
	href="<s:url value="/jQuery/css/jquery.ui.all.css"/>" />
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery-1.8.0.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.core.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.widget.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.effects.core.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.effects.slide.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/jQuery/js/jquery.ui.datepicker.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script>
	$(function() {
		$( "#fromDate" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true
		});
		$( "#toDate" ).datepicker({
			//numberOfMonths: 3,
			showButtonPanel: true
		});
	});
</script>
<script language=javascript>
		function disableBack(){
			window.history.forward(1);//Added By Matiur Rahman
		}
	 	disableBack();
	 	var todaysDate="<%=(String) session.getAttribute("TodaysDate")%>";	
	 	function fnSetMRKeyStatusCode(obj){
	 		document.getElementById('mrKeyStatusCodeSpan').innerHTML=obj.value;
	 	}
	 	function fnSetNewMRKeyStatusCode(obj){
	 		document.getElementById('newMRKeyStatusCodeSpan').innerHTML=obj.value;	 		
	 	}
	 	var selectedNoOfAccount=0;
	 	function fnSetSelectStatus(){
	 		var message="Loaded successfully";
	 		var mrKeySelected=0;
	 		var checkboxId="";
	 		var noOfAccountId="";
	 		selectedNoOfAccount=0;
	 		if(document.getElementById('searchResult')){
		 		var rows = document.getElementById('searchResult').rows;
				for(var i=1; i<rows.length-1; i++){
					checkboxId="trCheckbox"+i;
					noOfAccountId="trNoOfAccount"+i;
					if(document.getElementById(checkboxId).checked){
						selectedNoOfAccount+=parseInt(document.getElementById(noOfAccountId).value);
						mrKeySelected++;
					}
				}
				message="Total MRKey Selected = "+mrKeySelected+", Total No.of Accounts = "+selectedNoOfAccount;
	 		}else{
		 		var serverMsg=document.getElementById('onscreenMessage').innerHTML;
		 		if(serverMsg==''){
	 				message="Please provide a Search Criteria and click Search button";
		 		}
	 		}			
			setStatusBarMessage(message);
			if(document.getElementById('totalAccountCountSpanId')){
				document.getElementById('totalAccountCountSpanId').innerHTML="<b>"+message+"</b>";
			}			
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
		 	if(document.forms[0].orderBy && (Trim(document.forms[0].orderBy.value)==''||Trim(document.forms[0].orderBy.value)=='Bill Round')){
		 		document.forms[0].fromDate.value="";
		 		document.forms[0].toDate.value="";
		 	}
		 	fnSearch();
	 	}
	 	function fnSearch(){
		 	if(Trim(document.forms[0].mrKeyStatusCode.value)==''){
		 		document.getElementById('onscreenMessage').innerHTML="<font color='red'>Please Enter Mandatory Fields</font>";
		 		document.forms[0].mrKeyStatusCode.focus();
		 		return;
		 	}
		 	if(Trim(document.forms[0].processCounter.value)==''){
		 		document.getElementById('onscreenMessage').innerHTML="<font color='red'>Please Enter Mandatory Fields</font>";
		 		document.forms[0].processCounter.focus();
		 		return;
		 	}
		 	if(Trim(document.forms[0].mrKeyListForSearch.value)!='' && !fnValidateCommaSepartedListOfNumber(document.forms[0].mrKeyListForSearch)){
				return;
			}
	 		document.forms[0].hidAction.value="search";
	 		document.forms[0].action="operationMRKeyReview.action";
	 		hideScreen();
	 		document.forms[0].submit();
	 	}
	 	function fnUpdate(){
		 	if(Trim(document.getElementById('newMRKeyStatusCode').value)==''){
		 		document.getElementById('onscreenMessage').innerHTML="<font color='red'>Please enter New MR Key Status Code to update</font>";
		 		document.getElementById('newMRKeyStatusCode').focus();
		 		return;
		 	}else{			 	
			 	var mrKeyList="";
		 		var rows = document.getElementById('searchResult').rows;
		 		var checkboxId="";
		 		var mrKeyId="";
				for(var i=1; i<rows.length-1; i++){
					checkboxId="trCheckbox"+i;
					mrKeyId="trMRKey"+i;
					if(document.getElementById(checkboxId).checked){
						mrKeyList+=","+document.getElementById(mrKeyId).value;
					}
				}
				if(mrKeyList==''){
					document.getElementById('onscreenMessage').innerHTML="<font color='red'>Please Select atleast one MRKey to update</font>";
				}else{
					getNoOfAccountsToBeUpdatedAjaxCall(mrKeyList,document.getElementById('hidMRKeyStatusCode').value,document.getElementById('hidProcessCounter').value);
				}
		 	} 	
	 	}
	 	function getNoOfAccountsToBeUpdatedAjaxCall(mrKeyList,hidMRKeyStatusCode,hidProcessCounter){
			document.getElementById('onscreenMessage').innerHTML="";
			document.getElementById('onscreenMessage').title="Server Message.";
			document.getElementById('hidAction').value="countNoOfAccountsToBeUpdated";
			var url= "operationMRKeyReviewAJax.action?hidAction=countNoOfAccountsToBeUpdated";
			url+="&mrKeyList="+mrKeyList;
			url+="&hidMRKeyStatusCode="+hidMRKeyStatusCode;
			url+="&hidProcessCounter="+hidProcessCounter;			
			//alert('url::'+url); 
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
						//alert('::'+responseString);
						if(null!=responseString && responseString.indexOf('<script')==-1){
							if(responseString.indexOf('Please')==-1){
								var confirmMessage="\n\nWarning !\n\nA Total of "+responseString+"  accounts will be updated.\n";	
								if(responseString!=selectedNoOfAccount){
									confirmMessage+="\nNote: Some records has been modified by another User / Program / Application.\nPlease refresh your search and try Again.\n";
								}	
								confirmMessage+="\nIf you are sure click OK else Cancel or press Esc.\n\n\n";					
								if(confirm(confirmMessage)){									
									updateMRKeyStatusCodeAjaxCall(mrKeyList,hidMRKeyStatusCode,hidProcessCounter);
									//showScreen();
									stopTimer();
								}else{
									showScreen();
								}
							}else{
								document.getElementById('onscreenMessage').innerHTML="<font color='red'>Please Select atleast one MRKey to update</font>";
								showScreen();
							}
						} else{
							document.getElementById('onscreenMessage').innerHTML=responseString;
							showScreen();
						}  
					}
				};  
				httpBowserObject.send(null);
			}
		}
	 	function updateMRKeyStatusCodeAjaxCall(mrKeyList,hidMRKeyStatusCode,hidProcessCounter){
			isProcessing=true;			
			document.getElementById('onscreenMessage').innerHTML="";
			document.getElementById('onscreenMessage').title="Server Message.";
			document.getElementById('hidAction').value="countNoOfAccountsToBeUpdated";
			var url= "operationMRKeyReviewAJax.action?hidAction=updateMRKeyStatusCode";
			url+="&mrKeyList="+mrKeyList;
			url+="&hidMRKeyStatusCode="+hidMRKeyStatusCode;
			url+="&hidProcessCounter="+hidProcessCounter;
			url+="&newMRKeyStatusCode="+document.getElementById('newMRKeyStatusCode').value;			
			//alert('url::'+url); 
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
					//alert('::'+responseString);
					if(null!=responseString){
						if(responseString.indexOf('not')==-1 && parseInt(responseString)>0){
								document.getElementById('onscreenMessage').innerHTML="<font color='#33CC33'><b>Total "+ responseString+ " Record was Successfully Updated.</b></font>";
								document.forms[0].hidAction.value="searchAfterUpdate";
						 		document.forms[0].action="operationMRKeyReview.action";
						 		//showScreen();
						 		hideScreen();
						 		document.forms[0].submit();
							}else{
								document.getElementById('onscreenMessage').innerHTML=responseString;
								showScreen();
							}
						}else{
							document.getElementById('onscreenMessage').innerHTML=responseString;
							showScreen();
						}
					}     
				};  
				httpBowserObject.send(null);
			}
		}	
		function fnResetSearch(){
			if(document.getElementById('tdAction')){
				document.getElementById('tdAction').innerHTML="&nbsp;";
			}
			if(document.getElementById('tdSearchResult')){
				document.getElementById('tdSearchResult').innerHTML="&nbsp;";
			}
			document.getElementById('onscreenMessage').innerHTML="<font color='blue'><b>Click Search Button to Search.</b></font>";
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
					id="onscreenMessage"><%=session.getAttribute("AJAX_MESSAGE")%><%=session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form action="javascript:void(0);" onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="totalRecords" id="totalRecords" />
					<s:hidden name="hidMRKeyStatusCode" id="hidMRKeyStatusCode" />
					<s:hidden name="hidProcessCounter" id="hidProcessCounter" />
					<table width="100%" align="center" border="0"
						title="Warning! Records once updated cannot be Undone. So, Please select the appropriate status and MR Keys to update and check once again before updating">
						<tr>
							<td align="center" valign="top">
								<fieldset><legend>Search Criteria</legend>
									<table width="98%" align="center" border="0">
										<tr>
											<td align="right">MR Key Status Code<font color="red"><sup>*</sup></font></td>
											<td align="left"><s:select
												list="#session.consPreBillStatLookupMap"
												name="mrKeyStatusCode" id="mrKeyStatusCode" headerKey=""
												headerValue="Please Select" cssClass="selectbox-long"
												theme="simple" onchange="fnSetMRKeyStatusCode(this);fnResetSearch();" /></td>
											<td align="left" id="mrKeyStatusCodeSpan"
												title="MR Key Status Code"><s:property
												value="mrKeyStatusCode" />&nbsp;</td>
											<td align="right" colspan="2">Bill Round<font color="red"><sup>**</sup></font> <s:select name="selectedBillRound"
												id="selectedBillRound" headerKey=""
												headerValue="Please Select" list="#session.billWindowList"
												cssClass="selectbox" theme="simple" />Process Counter<font color="red"><sup>*</sup></font>
											<s:textfield name="processCounter"
												id="processCounter" cssClass="textbox" size="10"
												theme="simple" onchange="IsNumber(this);fnResetSearch();" /></td>
										</tr>
										<tr>
											<td align="right"
												title="Enter MR Keys separated by comma" nowrap>MRKey
											List<br/>(Separate by Comma only)</td>
											<td align="left" title="Enter MR Keys separated by comma only" ><s:textarea
												name="mrKeyListForSearch" id="mrKeyListForSearch" rows="2" cols="40"
												theme="simple" cssClass="textarea" onchange="fnValidateCommaSepartedListOfNumber(this);fnResetSearch();"/></td>
											<td align="right" colspan="3">
											<fieldset>
												<table width="100%" align="center" border="0" title="Select to Display Records acoording to Selected Date Values and Order By">
													<tr>
														<td align="left">From<font color="red"><sup>**</sup></font> <s:textfield
															id="fromDate" name="fromDate" cssClass="textbox" size="10" style="text-align: center;"
															title="DD/MM/YYYY" theme="simple" onchange="fnResetSearch();"/></td>
														<td align="center">To<font color="red"><sup>**</sup></font> <s:textfield id="toDate" name="toDate"
															cssClass="textbox" size="10" style="text-align: center;" title="DD/MM/YYYY" theme="simple" onchange="fnResetSearch();"/>
															</td>
														<td align="right">Order By <s:select
															list="{'Bill Round','Bill Gen Date','Entry Date','Park Date','Update Date'}"
															name="orderBy" id="orderBy" headerKey="Frozen Date" headerValue="Frozen Date" theme="simple" onchange="gotoSearch();"/><s:select
															list="{'Descending'}"
															name="orderByTo" id="orderByTo" headerKey="Ascending" headerValue="Ascending" theme="simple" onchange="gotoSearch();"/></td>
														</tr>
													</table>
												</fieldset>
											</td>		
										</tr>
										<tr>
											<td align="left" colspan="2"><font color="red"><sup>*</sup></font> marked field(s) are mandatory</td>
											<td align="center" colspan="1"><s:submit method="execute"
												key="Search " cssClass="groovybutton" theme="simple" /></td>
											<td align="right" colspan="2"><font color="red"><sup>**</sup></font> marked field(s) are for performence</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<s:if test="operationMRKeyReviewDetailsList.size() > 0">
							<tr>
								<td align="center" valign="top" id="tdSearchResult">
								<fieldset><legend>Search Result</legend>
								<table class="table-grid">
									<tr>
										<th align="center">&nbsp;<span
											id="totalAccountCountSpanId"><b>Total MRKey
										Selected = 0, Total No.of Accounts = 0</b></span></th>
									</tr>
								</table>
								<table id="searchResult" class="table-grid">
									<tr>
										<th align="left" colspan="2"><s:if test="pageNo > 1">
											<input type="button" name="btnPrevious" id="btnPrevious"
												value="<< Previous " class="groovybutton"
												onclick="gotoPreviousPage();" />
										</s:if><s:else>
											<input type="button" name="btnPrevious" id="btnPrevious"
												value="<< Previous " class="groovybutton"
												disabled="disabled" />
										</s:else></th>
										<th align="center" colspan="4" width="50%">Page No:<s:select
											list="pageNoDropdownList" name="pageNo" id="pageNo"
											cssClass="smalldropdown" theme="simple"
											onchange="fnSearch();" /> Showing maximum <s:select
											list="{10,20,30,40,50,100,150,200,300,400,500,1000,1500,2000,2500,3000,4000,5000}"
											name="maxRecordPerPage" id="maxRecordPerPage"
											cssClass="smalldropdown" theme="simple"
											onchange="fnSearch();" />records per page of total <s:property
											value="totalRecords" /> records.</th>
																					
										<s:if test="null!=orderBy && orderBy!=\"\" &&orderBy!=\"Bill Round\"">
										<th align="right" colspan="2"><s:if
											test="pageNo*maxRecordPerPage< totalRecords">
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton"
												onclick="gotoNextPage();" />
										</s:if><s:else>
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton"
												disabled="disabled" />
										</s:else></th>
										</s:if><s:else>
										<th align="center"><s:if
											test="pageNo*maxRecordPerPage< totalRecords">
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton"
												onclick="gotoNextPage();" />
										</s:if><s:else>
											<input type="button" name="btnNext" id="btnNext"
												value="    Next >>  " class="groovybutton"
												disabled="disabled" />
										</s:else></th>
										</s:else>
									</tr>
									<tr>
										<th align="center" width="5%">SL</th>
										<th align="center" width="15%">Bill Round</th>
										<th align="center" width="15%">MR Key</th>
										<th align="center" width="15%">No. of Account</th>
										<th align="center" width="15%">MR Key Status Code</th>
										<th align="center" width="10%">Process Counter</th>
										<s:if test="null!=orderBy && orderBy!=\"\" &&orderBy!=\"Bill Round\"">
										<th align="center" width="10%"><s:property value="orderBy" /></th>
										</s:if>
										<th align="center" width="5%"><input type="checkbox"
											id="selectall" />Select All</th>
									</tr>
									<s:iterator value="operationMRKeyReviewDetailsList"
										status="row">
										<tr onMouseOver="this.bgColor='yellow';"
											onMouseOut="this.bgColor='white';">
											<td align="center"><s:property value="slNo" /></td>
											<td align="center"><s:property value="billRound" /></td>
											<td align="center"><s:property value="mrKey" /><input
												type="hidden"
												name="trMRKey<s:property value="#row.count" />"
												id="trMRKey<s:property value="#row.count" />"
												value="<s:property value="mrKey" />" /></td>
											<td align="center"><s:property value="totalNoOfAccount" /><input
												type="hidden"
												name="trNoOfAccount<s:property value="#row.count" />"
												id="trNoOfAccount<s:property value="#row.count" />"
												value="<s:property value="totalNoOfAccount" />" /></td>
											<td align="center"><s:property value="mrKeyStatusCode" /></td>
											<td align="center"><s:property value="processCounter" /></td>
											<s:if test="null!=orderBy && orderBy!=\"\" &&orderBy!=\"Bill Round\"">
											<td align="center"><s:property value="orderBy" /></td>
											</s:if>
											<td align="center"><input type="checkbox" class="case"
												name="case" id="trCheckbox<s:property value="#row.count" />" /></td>
										</tr>
									</s:iterator>
								</table>
								<br />
								</fieldset>
								</td>
							</tr>
							<tr>
								<td align="center" valign="top" id="tdAction">
								<fieldset><legend>Update MR Key Status Code</legend>
								<table width="90%" align="center" border="0"
									title="Warning! Records once updated cannot be Undone. So, Please select the appropriate status and MR Keys to update and check once again before updating">
									<tr>
										<td align="right">From</td>
										<td align="left"><s:select
											list="#session.validFromStatLookupMap"
											name="fromMRKeyStatusCode" id="fromMRKeyStatusCode"
											headerKey="" headerValue="Please Select"
											cssClass="selectbox-long" theme="simple" disabled="true" /></td>
										<td align="right">To<font color="red"><sup>*</sup></font></td>
										<td align="left"><s:select
											list="#session.validToStatLookupMap"
											name="newMRKeyStatusCode" id="newMRKeyStatusCode"
											headerKey="" headerValue="Please Select"
											cssClass="selectbox-long" theme="simple"
											onchange="fnSetNewMRKeyStatusCode(this);" /></td>
										<td align="left" width="10%" id="newMRKeyStatusCodeSpan"
											title="MR Key Status Code"><s:property
											value="newMRKeyStatusCode" />&nbsp;</td>
										<td align="left"
											title="Click after selecting MR Keys from the table and New MR Key Status Code from the drop down."><input
											type="button" value="Update" name="btnUpdate" id="btnUpdate"
											class="groovybutton" onclick="javascript:fnUpdate();" /></td>
									</tr>
									<tr>
										<td align="center" colspan="6">&nbsp;<font color="red">Warning!
										Records once updated cannot be Undone. So, Please select the
										appropriate status and MR Keys to update and check once again
										before updating. </font></td>
									</tr>
									<tr>
										<td align="right" colspan="6"><font color="red"><sup>*</sup></font>
										marked fields are mandatory.</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
						</s:if>
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
	$(document).ready(function(){
		setStatusBarMessage('Caution : Please select the right status and MR Keys to update. Records once updated cannot be Undone.'); 
		$( "#fromDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy');
		$( "#toDate" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
		$( "#fromDate" ).datepicker( "option","showAnim",'slide' );
		$( "#toDate" ).datepicker( "option","showAnim",'slide' );
		//document.getElementById('fromDate').value = todaysDate;
		//if(Trim(document.getElementById('toDate').value)==''){
		//	document.getElementById('toDate').value =todaysDate;
		//}
	});
	$(function(){       // add multiple select / deselect functionality     
		$("#selectall").click(function () { 			        
			$('.case').attr('checked', this.checked);
			 fnSetSelectStatus();      
			});       // if all checkbox are selected, check the selectall checkbox     // and viceversa     
		$(".case").click(function(){           
			if($(".case").length == $(".case:checked").length) {             
				$("#selectall").attr("checked", "checked");         
			} else {             
				$("#selectall").removeAttr("checked");
	        } 
			 fnSetSelectStatus();       
	    }); 
	}); 
	$("form").submit(function(e){
		gotoSearch();
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
	$(".textarea").focus(function() { 
		$(this).removeClass("textarea"); 
		$(this).addClass("textarea-focus"); 
	}); 
	$(".textarea").blur(function() {
		$(this).removeClass("textarea-focus"); 
		$(this).addClass("textarea"); 
	});
	//Detecting Enter Key Press to Submit form
	document.onkeydown = function(evt) {
			evt = evt || window.event;
			//alert(evt.keyCode);
			//if (evt.keyCode == 13 ) {  	//F5 Key Press 	
				//evt.preventDefault();
			//}
			/*if (evt.keyCode == 13 ) {  	//Enter Key Press 	
				//alert(hhdPopUp+''+mrPopUp);	
				if(hhdPopUp&&!isProcessing){
					saveHHDIDEntryDetails();
				}
				if(mrPopUp&&!isProcessing){
					saveMeterReaderNameEntryDetails();
				}
			} 
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
