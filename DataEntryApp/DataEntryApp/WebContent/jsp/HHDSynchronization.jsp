<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div style="width:50%;height:50%;align:center;">
<jsp:plugin type="applet" code="HHDPCIApplet" codebase="applets"  archive="jsch-0.1.50.jar"
	width="100%" height="100%"><jsp:fallback>
		<p>Unable to load Applet</p>
	</jsp:fallback></jsp:plugin>
</div>

</body>
</html>