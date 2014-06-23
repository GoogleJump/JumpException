<%@ include file="./header.jsp" %>

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
	    String passwordText = request.getParameter("passwordText");
	    if(passwordText == null) {
	    	passwordText = "";
	    }
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

	<p>
		Welcome to Shub!
	</p>

	<form action="/practicePage" method="get">
		<div>
			<input type="submit" value="You Made It! You Can Go Back Now!" />
		</div>
		<input type="hidden" name="guestbookName"
			 />
	</form>
	<form action="/practicePage.jsp" method="post">
		<div>
			<input type="text" name="responseText"
				 />
		</div>
		<div>
			<input type="submit" value="Post!" />
		</div>
	</form>

	<form action="/loggingIn" method="post">
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
			<input type="submit" value= "Sign in!" />
		</div>
	</form>
	<button>
		<a href="#SignUp" classes-"btn btn-primary btn-lg" roles="button">Sign Up!</a>
	</button>
	<form action="#SignUp" method="post">
		<label>Don't have Shub? Go sign up below!</label>
		<div>
			<input type= "submit" value = "Sign up!"/>
		</div>
	</form>

	<p>
		${fn:escapeXml(responseText)}
	</p>
