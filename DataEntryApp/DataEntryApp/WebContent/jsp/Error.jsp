<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>Revenue Management System,Delhi Jal Board</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="description" content="Revenue Management System,Delhi Jal Board ">
	<meta name="copyright" content="&copy;2012 Delhi Jal Board">
	<meta name="author" content="Tata Consultancy Services">
	<meta name="keywords" content="djb,DJB,Revenue Management System,Delhi Jal Board">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="googlebot" content="noarchive">
	<link rel="stylesheet" type="text/css" href="<s:url value="/css/custom.css"/>"/>
 	<script type="text/javascript">
 		function fnOnSubmit(){
 			hideScreen();
 			return true;
 		}
 	</script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%> 
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
			<table class="djbpage" >
				<tr height="20px">
					<td align="center" valign="top">
						<%@include file="../jsp/Header.html"%> 
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">
						<table width="100%" align="center" border="0" >
							<tr>
								<td align="center"  valign="top">
									<fieldset>															
										<table width="95%" align="center" border="0">
											<tr>
												<td align="left" colspan="3">
													<font color="red" size="2"><s:actionerror /></font>
												</td>
											</tr>										
											<tr>
												<td align="center" colspan="3">
													<font color="red" size="2">There was an error while Processing, Please Try again.</font>
												</td>
											</tr>
										</table>									
									</fieldset>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>