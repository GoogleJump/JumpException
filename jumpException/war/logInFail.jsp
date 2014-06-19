<%@ include file="./header.jsp" %>
		<p> Failure to Log In. Your Username or your password does not match.</p>
		<p> Please try again. </p>

	<%
		String username = request.getSession().getAttribute("username").toString();
		pageContext.setAttribute("username", username);
	%>

	<form action="/loggingIn" method="post">
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
	<form action="/practicePage.jsp" >
		<div>
			<input type="submit" value="Sign up!" />
		</div>
	</form>
<%@ include file="./footer.jsp" %>