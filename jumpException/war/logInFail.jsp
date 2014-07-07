<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="shubTheme">
		<p> Failure to Log In. Your Username or your password does not match.</p>
		<p> Please try again. </p>


		<form action="/loggingIn" method="post">
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
				<input type="submit" value= "Sign in!" />
			</div>
		</form>
		<form action="#SignUp" >
			<label>Don't have Shub? Go sign up below!</label>
			<div>
				<input type="submit" value="Sign up!" />
			</div>
		</form>
	</div>
