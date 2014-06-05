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
		<p> Failure to Create Log In. Your Username is already in use or your password does not match.</p>
		<p> Please try again. </p>
		
	<%
		Cookie cookie = request.getCookies()[0];
		String username = cookie.getValue();
		pageContext.setAttribute("username", username);
	%>

		<form action="/signingIn" method="post">
		<div>
			<input type="text" name="signInText"
				value="${fn:escapeXml(username)}" />
		</div>
		<div>
			<input type="text" name="passwordText" />
		</div>
		<div>
			<input type="submit" value= "Sign in!" />
		</div>
	</form>
</body>
</html>