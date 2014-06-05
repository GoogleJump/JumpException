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
	<%
		String responseText = request.getParameter("responseText");
	    if (responseText == null) {
	        responseText = "";
	    }
	    pageContext.setAttribute("responseText", responseText);
	    
	    String signInText = request.getParameter("signInText");
	    if (signInText == null) {
	        signInText = "";
	    }
	    String passwordText = "";
	    pageContext.setAttribute("signInText", signInText);
	    pageContext.setAttribute("passwordText", passwordText);
		/*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key signInKey = KeyFactory.createKey("SignIn", signInText);
	    Query query = new Query("SignIn", signInKey);
	    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    String name = "try";
	    pageContext.setAttribute("name", name);
		pageContext.setAttribute("greeting_content",
                greetings.get(0).getProperty("user"));*/
    %>

	<p>'${fn:escapeXml(name)}'</p>
	
	<form action="/practicePage" method="get">
		<div>
			<input type="submit" value="You Made It! You Can Go Back Now!" />
		</div>
		<input type="hidden" name="guestbookName"
			value="${fn:escapeXml(guestbookName)}" />
	</form>
	<form action="/practicePage.jsp" method="post">
		<div>
			<input type="text" name="responseText"
				value="${fn:escapeXml(responseText)}" />
		</div>
		<div>
			<input type="submit" value="Post!" />
		</div>
	</form>
	
	<form action="/loggingIn" method="post">
		<div>
			<input type="text" name="signInText"
				value="${fn:escapeXml(signInText)}" />
		</div>
		<div>
			<input type="text" name="passwordText"
				value="${fn:escapeXml(passwordText)}" />
		</div>
		<div>
			<input type="submit" value= "Sign in!" />
		</div>
	</form>
	<form action="/practicePage.jsp" method="post">
		<div>
			<input type= "submit" value = "Sign up!"/>
		</div>
	</form>
	
	<p>
		${fn:escapeXml(responseText)}
	</p>
	

</body>
</html>