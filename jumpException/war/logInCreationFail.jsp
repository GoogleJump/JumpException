<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>
		<p> Failure to Create Log In. Your Username is already in use or not allowed.</p>
		<p> Please try again. </p>
		
	<%
		String username = request.getSession().getAttribute("username").toString();
		pageContext.setAttribute("username", username);
	%>

	<form action="/signingUp" method="post">
		<div>
			<input type="text" name="signInText"
				value="${fn:escapeXml(username)}" />
		</div>
		<div>
			<input type="text" name="passwordText" />
		</div>
		<div>
			<input type="submit" value= "Get Started!" />
		</div>
	</form>
	<p>
		Already with Shub? Go sign in below!
	</p>
	<form action="/logIn.jsp" >
		<div>
			<input type="submit" value= "Take me there!" />
		</div>
	</form>
</body>
</html>