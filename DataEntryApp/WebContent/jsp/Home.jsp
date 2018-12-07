<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<html>
<%try{ %>
<head>
<title>Home-Revenue Management System,Delhi Jal Board</title>
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
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
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
				<td align="center" valign="bottom">
				<div
					style="width: 99%; height: 99%; border: 3px solid #21c5d8; position: relative; align: center; padding: 5px;">
				<div
					style="width: 29%; height: 100%; border: 0; position: relative; align: center; left: 0px; float: left;">
				<%@include file="../jsp/LeftMenu.html"%></div>
				<div
					style="width: 70%; height: 100%; background:#fff; border: 1px solid #21c5d8; position: relative; align: center; left: 0px; float: right;"><br />
				<br />
				<br />
				<font color="#21c5d8" size="6"> Welcome <s:property
					value="#session.userName" /></font><br />
				<font color="#21c5d8" size="5">You have logged in as <s:property
					value="#session.userRoleDesc" />.</font><br />
				<br />
				<%if(userRoleId.equals("0")) { %>
				<font color="red"><b>Currently No Role has been tagged in the System for You for this Application !<br/>A role must be tagged with the User to use this application.<br/>Please contact an Administrator for Role tagging for You.<br/><br/>Please Note that after Role setting Re-Login is mandatory.</b></font>
				<%} else{%>
				<br />
				<br />
				<br />
				<br />
				<%}%>
				</div>
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
<%}catch(Exception e){e.printStackTrace();} %>
</html>