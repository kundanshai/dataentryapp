<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<html>
<head>
<title>Revenue Management System,Delhi Jal Board</title>
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
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery.tablesorter.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script language=javascript>
	//function disableBack()
	//{
	//window.history.forward(1);//Added By Matiur Rahman
	//}
	//disableBack();
</script>

<script type="text/javascript">
	function fnOnSubmit() {
		hideScreen();
		return true;
	}
	function fnOnLoad() {

	}
	function fnDownloadFile(fileName, fileType) {
		document.forms[0].fileName.value = fileName;
		document.forms[0].fileType.value = fileType;
		document.forms[0].action = "download.action";
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
				<div id="chengePassword">
				<table width="100%" align="center" border="0">
					<tr>
						<td align="center" valign="top">
						<fieldset><s:form action="download.action"
							method="post" theme="simple">
							<s:hidden name="fileName" />
							<s:hidden name="fileType" />
							<s:hidden name="userId" />
						</s:form> <legend><span>Application Log of <%=session.getAttribute("APP_LOG_APLICATION_NAME")%></span></legend>
						<table width="100%" border="0">
							<tr>
								<td colspan="3" align="center">
								<table class="tablesorter" align="center" id="searchResult">
									<thead>
										<tr>
											<!--<th align="center" width="10%">SL</th>-->
											<th align="left">File Name</th>
											<th align="center">Version</th>
											<th align="center">Action</th>
											<!--  
																<th align="center" width="30%">
																	Last Modified Date
																</th>
																<th align="center" width="15%">
																	FileSize(KB)
																</th>-->
										</tr>
									</thead>
									<tbody>
										<%FileDetails fileDetails = null;
															ArrayList<FileDetails> fileDetailsList =(ArrayList<FileDetails>)session.getAttribute("fileDetailsList");
															if(null!=fileDetailsList && fileDetailsList.size()>0){
																for (int i = 0; i < fileDetailsList.size(); i++) {
																	fileDetails = fileDetailsList.get(i);
																%>
										<% String version=fileDetails.getFileName();
																		if(null!=version){
																			version=version.substring(version.lastIndexOf('.')+1);
																			version=version.equalsIgnoreCase("log")?"0":version;
																		}%>
										<tr bgcolor="white"
												onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'">
											<td align="left"><a href="#"
												style="text-decoration: none;"
												onclick="javascript:fnDownloadFile('<%=fileDetails.getFileName() %>','log');"
												title="Click to Download"><%=fileDetails.getFileName() %></a>
											</td>
											<td align="center"><%=version %></td>
											<td align="center"><input type="button" value="Download"
												class="groovybutton"
												onclick="javascript:fnDownloadFile('<%=fileDetails.getFileName() %>','log');"
												title="Click to Download" /></td>
										</tr>
										<%}}else{ %>
										<tr>
											<td align="center" colspan="3"><font color="red"
												size="3">No Log File Found to Display!</font></td>
										</tr>
										<%} %>
									</tbody>
								</table>
								</td>
							</tr>
							<tr>
								<td align="center" colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" id="search-error-msg"><font
									color="red" size="2"><s:actionerror /></font></td>
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
		//$("#searchResult").tablesorter();
			$("#searchResult").tablesorter( {
				sortList : [ [ 1, 0 ] ]
			});
		});
</script>
</html>