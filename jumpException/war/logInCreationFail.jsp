<%@ include file="./header.jsp" %>

		<p> Failure to Create Log In. Your Username is already in use or not allowed.</p>
		<p> Please try again. </p>

	<%
		String username = request.getSession().getAttribute("username").toString();
		pageContext.setAttribute("username", username);
	%>

	<form action="/signingUp" method="post">
		<div>
			<label>Username:</label>
			<input type="text" name="signInText"
				value="${fn:escapeXml(username)}" />
		</div>
		<div>
			<label>Password:</label>
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
<%@ include file="./footer.jsp" %>