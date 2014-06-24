<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%
		/*String responseText = request.getParameter("responseText");
	    if (responseText == null) {
	        responseText = "";
	    }
	    pageContext.setAttribute("responseText", responseText);

	    String signInText = request.getParameter("signInText");
	    if (signInText == null) {
	        signInText = "";
	    }
	    String passwordText  = request.getParameter("passwordText");
	    if (passwordText == null) {
	        passwordText = "";
	    }
	    pageContext.setAttribute("signInText", signInText);
	    pageContext.setAttribute("passwordText", passwordText);
		/*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key signInKey = KeyFactory.createKey("SignIn", signInText);
	    Query query = new Query("SignIn", signInKey);
	    greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	    name = "try";
	    pageContext.setAttribute("name", name);
		pageContext.setAttribute("greeting_content",
                greetings.get(0).getProperty("user"));*/
    %>

	<p>Special Awesome Page Only for New Users! <br>
		One time deal per user :D
	</p>

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

	<form action="/signingUp" method="post">
		<div>
			<label>Username:</label>
			<input type="text" name="signInText"
				value="${fn:escapeXml(signInText)}" />
		</div>
		<div>
			<label>Password:</label>
			<input type="text" name="passwordText"
				value="${fn:escapeXml(passwordText)}" />
		</div>
		<div>
			<input type="submit" value= "Get Started!" />
		</div>
	</form>
	<p>
		Already with Shub? Go sign in below!
	</p>
	<form action="#Shub" >
		<div>
			<input type="submit" value= "Take me there!" />
		</div>
	</form>

	<p>
		${fn:escapeXml(responseText)}
	</p>
