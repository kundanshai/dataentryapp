<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
  	<title>Login</title>

</head>
<body >
	<div class="header fixed">
		<span id="header_text">Delhi Jal Board</span>	
	</div>
	<div id="page">	
		<br/><br/>
		<div class="login">
		<s:form id="loginForm" action="login" method="post" theme="simple">
		  <h1>Login</h1>
		  User Id
		  <s:textfield name="username" id="username" theme="simple" cssClass="login-input" autocomplete="off" autofocus/>
		  Password
		  <s:password name="password" id="password" theme="simple" cssClass="login-input"/>
		  <s:submit name="loginButton" id="loginButton" method="execute" theme="simple" value="Login" cssClass="login-submit"/>
		  <p class="login-help"><a href="#">Forgot password?</a></p>
		  <font color="red" size="2"><s:actionerror /></font>
		</s:form>
		</div>	
	</div>

</body>
</html>
