<%@page import="java.awt.Checkbox"%>
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
<script>
	function overallTextOnChange() {
		var overallText = document.getElementById("overallText").value;
		if(!document.getElementById("fbCheckbox").checked) {
			document.getElementById("fbText").value = overallText;
		}
		if(!document.getElementById("twitterCheckbox").checked) {
			document.getElementById("twitterText").value = overallText;
		}
	}

</script>

 
</head>
<body>
	<div class="mainPage">
		<%
			//Cookie cookie = request.getCookies()[0];
			//String username = cookie.getValue();
			String username = request.getSession().getAttribute("username").toString();
			pageContext.setAttribute("username", username);
			String overallText = "";
			String fbText = "";
			String twitterText = "";
			pageContext.setAttribute("overallText", overallText);
			//pageContext.setAttribute("fbText", fbText);
			//pageContext.setAttribute("twitterText", twitterText);
		%>
		<p>
			Hello, ${fn:escapeXml(username)}!
		<p>
		<form action="/loggingOut" method="get">
			<div>
				<input type="submit" value="Sign Out" />
			</div>
		</form>
		<form action="/deleteAccount" method="post">
			<div>
				<input type="submit" value="Delete Account" />
			</div>
		</form>
		<p>
			Type and Share!
		<p>
		<form onchange="overallTextOnChange()">
			<div>
				<textarea rows="3" cols= "50" type="text" name= "overallText" id="overallText" value="${fn:escapeXml(overallText)}"></textarea>
			</div>
		</form>
		<p>
			Individualize:
		<p>
		<form onclick="/openSocialMedias" method="post" >
			<div>
			<label>Facebook:</label>
				<input type="checkbox" name="fbCheckbox" id="fbCheckbox"/>
				<textarea class="socialTextArea" rows="3" cols= "50" type="text" name= "fbText" id="fbText" value="${fn:escapeXml(fbText)}"></textarea>
			</div>
		</form>
		<form title="Diverge:" >
			<div>
				<label>Twitter:</label>
				<input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" />
				<textarea class="socialTextArea" rows="3" cols= "50" type="text" name= "twitterText" id="twitterText" value="${fn:escapeXml(twitterText)}"></textarea>
			</div>
		</form>
	</div>
</body>
</html>