<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="shubTheme">
			<p> Failure to Create Log In. Your Username is already in use or not allowed.</p>
			<p> Please try again. </p>
	
		<%
			/*String username = request.getSession().getAttribute("username").toString();
			pageContext.setAttribute("username", username);*/
		%>
	
		<form action="/signingUp" method="post">
			<div>
				<label>Username:</label>
				<input type="text" name="signInText"
					value="${fn:escapeXml(username)}" />
			</div>
			<div>
				<label>Password:</label>
				<input type="password" name="passwordText" />
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
	</div>