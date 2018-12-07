<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="com.tcs.djb.util.*"%>
<%@page import="com.tcs.djb.model.*"%><html>
<%
	try {
%>
<head>
<title>Search User Page - Revenue Management System,Delhi Jal
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
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/jquery.dataTables.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/jquery-ui-1.8.4.custom.css"/>" />
<script type="text/javascript"
	src="<s:url value="/js/DJBPasswordPolicy.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery.1.4.2.min.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery.dataTables.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
</script>
<script type="text/javascript">
	function retrieveUserDetails(userId) {
		var currentTab = Trim(document.forms[0].currentTab.value);
		//alert(userId + '   ' + currentTab);		
		hideScreen();
		document.forms[0].action="createUser.action";
		document.forms[0].hidAction.value="viewDetails";
		document.forms[0].currentTab.value=currentTab;
		document.forms[0].userId.value=userId;
		document.forms[0].pageMode.value="Update";
		document.forms[0].submit();		
	}
	function refreshSearch() {
		var currentTab = Trim(document.forms[0].currentTab.value);
		//alert( currentTab);		
		hideScreen();
		document.forms[0].action="createUser.action";
		document.forms[0].currentTab.value=currentTab;
		document.forms[0].hidAction.value="search";
		document.forms[0].submit();		
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
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font></span></font>&nbsp;</div>
				<div>
				<table width="100%" align="center" border="0"
					title="Click on the Tab to get Refreshed Records.">
					<tr>
						<td align="center" valign="top">
						<fieldset>
						<table class="tab-table" cellpadding="0px" cellspacing="0px"
							border="0">
							<tr>
								<td align="center">
								<div class="centeredmenu">
								<ul>
									<li><a href="#" id="tab1"
										title="Click to retrieve User List for Data Entry Application."><b>Data
									Entry</b></a></li>
									<li><a href="#" id="tab2"
										title="Click to retrieve User List for All Application."><b>All
									Application</b></a></li>
									<!-- <li><a href="#" id="tab3"
										title="Click to retrieve User List for Web Service."><b>Web
									Service</b></a></li>-->
								</ul>
								</div>
								</td>
							</tr>
							<tr>
								<td align="center" nowrap>
								<form action="javascript:void(0);" name="formSetRoleAccess"
									method="post" onsubmit="return false;"><s:hidden
									name="hidAction" id="hidAction" /> <s:hidden name="currentTab"
									id="currentTab" /> <s:hidden name="pageMode" id="pageMode" />
								<s:hidden name="userId" id="userId" />
								<div id="divTab1" style="display: none;">
								<div
									title="Info: Created User is for Data Entry Application Only."
									style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>Users are for Data Entry
								Application Only.</b></font>&nbsp;</div>
								</div>
								<div id="divTab2" style="display: none;">
								<div
									title="Info:Created User is for Application User (Employee Portal, CC&B, BI Publisher, UCM)."
									style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>Users are for Application
								User(Employee Portal, CC&amp;B, BI Publisher, UCM).</b></font>&nbsp;</div>
								</div>
								<div id="divTab3" style="display: none;">
								<div title="Info: Created User is for Web Service User Only."
									style="text-align: left"><img
									src="/DataEntryApp/images/info-bulb.gif" height="20" border="0" /><font
									size="2" color="blue"><b>Users are for Web Service
								User Only.</b></font>&nbsp;</div>
								</div>
								<br />
								<table width="99%" border="0">
									<tr>
										<td align="center">
										<table cellpadding="0" cellspacing="0" border="0"
											class="display" id="data-table">
											<s:if test="null!=userList&& userList.size() > 0">
												<thead>
													<tr>
														<th>NAME</th>
														<th>ADDRESS</th>
														<th>Mobile</th>
														<th>Email</th>
														<th>USER ID</th>
														<th>STATUS</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<s:iterator value="userList" status="row">
														<tr>
															<td align="left"><s:property value="userName" /></td>
															<td align="left"><s:property value="userAddress" /></td>
															<td align="left"><s:property value="mobileNo" /></td>
															<td align="left"><s:property value="emailId" /></td>
															<td align="left"><s:property value="userId" /></td>
															<td align="center"><s:property value="isActive" /></td>
															<td align="center" title="Click to View Details." nowrap><a
																href="#"
																onclick="javascript:retrieveUserDetails('<s:property value="userId" />');">View
															Details</a></td>
														</tr>
													</s:iterator>
												</tbody>
											</s:if>
										</table>
									</tr>
								</table>
								</form>
								</td>
							</tr>
							<tr>
								<td align="right"><font size="2" color="blue"> Enter a text in Search Box to Search required record or Click
								on the Tab to get Refreshed Records.&nbsp;&nbsp;</font><br />
								</td>
							</tr>
							<tr>
								<td align="right">&nbsp;</td>
							</tr>
						</table>
						</fieldset>
						</td>
					</tr>
				</table>
				</div>
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
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var currentTab=document.forms[0].currentTab.value;
		if(currentTab.indexOf("Data")>-1){
			$("#tab1").addClass("active");
			document.getElementById('divTab3').style.display = 'none';
			document.getElementById('divTab2').style.display = 'none';
			document.getElementById('divTab1').style.display = 'block';
		} 
		if(currentTab.indexOf("All")>-1){
			$("#tab2").addClass("active");
			document.getElementById('divTab3').style.display = 'none';
			document.getElementById('divTab1').style.display = 'none';
			document.getElementById('divTab2').style.display = 'block';
			document.forms[0].currentTab.value = "All Application";
		}else{
			$("#tab1").addClass("active");
			document.getElementById('divTab3').style.display = 'none';
			document.getElementById('divTab2').style.display = 'none';
			document.getElementById('divTab1').style.display = 'block';
		}
		$("a").click(function() {
			$(".active").removeClass("active");
			$(this).addClass("active");			
			var tabId = $(this).attr('id');
			if (tabId == 'tab1') {
				document.forms[0].currentTab.value = "Data Entry";
				refreshSearch();
			}
			if (tabId == 'tab2') {
				document.forms[0].currentTab.value = "All Application";
				refreshSearch();
			}
			if (tabId == 'tab3') {
				document.forms[0].currentTab.value = "Web Service";
				refreshSearch();
			}
			
		});
		oTable = $('#data-table').dataTable( {
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"iDisplayLength": 25
		});
	});
</script>
<script type="text/javascript" src="<s:url value="/js/CustomeJQuery.js"/>"></script>
<%
	} catch (Exception e) {
		//e.printStackTrace();
%>
<script type="text/javascript">
	alert('There was a problem at the server, User must Logout and Login again.\nPlease ReLoginandRetry. ');
	goLogout();
</script>
<%
	}
%>
</html>