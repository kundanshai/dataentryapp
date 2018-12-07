<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<html>
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
<script language=javascript>
	function disableBack() {
		window.history.forward(1);//Added By Matiur Rahman
	}
	disableBack();
	function goHome() {
		document.getElementById('messageBox').innerHTML="<font color='red' size='3'>Please Wait..........<br/>The page will be automatically Redirected.</font>";
	}
	function goLogout() {
		hideScreen();
		document.location.href = "logout.action";
	}
	function fnOnload() {
		var url = "populateLOVAJax.action?hidAction=populateLOV";
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
					if (null != responseString
							&& responseString.indexOf("SUCCESS") > -1) {
						if(''!=document.forms[0].hidAction.value){
							document.forms[0].hidAction.value="";
						}
						document.forms[0].submit();
					} else {
						alert(responseString);
					}
				}
			};
			httpBowserObject.send(null);
		}
	}
</script>
</head>
<body onload="fnOnload();">
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top">
				<div class="header">
				<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" width="1%" valign="top">
							<img src="/DataEntryApp/images/logo.png" alt="Logo" border="0"/>
						</td>
						<td align="left" width="20%" valign="top">
							<div class="header-rms">&nbsp;Revenue Management System<br />
							&nbsp;Delhi Jal Board</div>
							</td>
						<td align="right" valign="top">
							<div class="toplinks"><a
								href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=AboutRMS">About
							RMS</a><a
								href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=ContactUs">Contact
							Us</a> <a
								href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=DJBRMS&pageContent=Sitemap">Sitemap</a>
							<a
								href="/DJBPortal/portals/DJBCustomer.portal?_nfpb=true&amp;_st=&amp;_pageLabel=faq">FAQ</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</table>
				<table width="100%" align="center" border="0">
					<s:if test="null!=#session.userRoleDesc">
					<tr>
						<td align="left" width="50%" valign="top">
						<a href="#" class="home">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						</td>
						<td align="center" width="3%" valign="top">
							&nbsp;<!--<a href="#" onclick="javascript:goHome()"><img src="/DataEntryApp/images/home.gif"  border="0" title="Go To Home Page"/></a>-->
						</td>
						<td align="right" width="40%" valign="top">
							<div class="welcome-text" title="Last Login <s:property value="#session.lastLoginTime" />">Welcome <b><s:property value="#session.userName" /></b>&nbsp;|&nbsp;Role:&nbsp;<s:property value="#session.userRoleDesc" />.</div>
						</td>
						<td align="right" width="7%" valign="top">
							<a href="#" onclick="goLogout();" class="logout" title="Logout">&nbsp;&nbsp;Logout&nbsp;&nbsp;</a>		
						</td>
					</tr>
					</s:if>
					<s:else>
					<tr>
						<td align="right" width="100%" valign="top">
							<a href="#" class="logout" title="Logout">&nbsp;&nbsp;Logout&nbsp;&nbsp;</a>			
						</td>
					</tr>
					</s:else>
				</table>
				</div>
				</td>
			</tr>
			<tr>
				<td align="center" valign="middle">
				<div
					style="width: 99%; height: 99%; border: 1px solid #21c5d8; position: relative; align: center; padding: 5px;">
				<s:form action="populateLOV.action" method="post" >
				<s:hidden name="hidAction" id="hidAction" />
				</s:form>
				<SPAN id="messageBox">&nbsp;</SPAN>
				<br />
				<br />
				<br />
				<br />
				<img src="/DataEntryApp/images/ajax_loader_blue_256.gif" border="0"
					title="Loading Some Initial Server Configuration. Please Wait" /><br />
				<br />
				<br />
				<br />
				<img src="/DataEntryApp/images/LoadingProgressBar.gif" border="0"
					title="Loading Some Initial Server Configuration. Please Wait" /></div>
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
</html>