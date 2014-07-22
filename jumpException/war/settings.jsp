   
 <%@ include file="./header.jsp" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	pageContext.setAttribute("sectionWithDynamicBackgroundImage", "container content-section text-center " + user.getBackgroundImage());

%>
<section id="Settings" class="${fn:escapeXml(sectionWithDynamicBackgroundImage)}">

	<%
		pageContext.setAttribute("curPassword", "");
		pageContext.setAttribute("newPassword", "");
		pageContext.setAttribute("confirmNewPassword", "");
	%>
	<% if(user.getTwitterAccessToken() == null) { %>
		<form action="/twitterOAuth" method="post">
			<div>
				<input type="submit" value="Connect Twitter Account" />
			</div>
		</form>
	<% } else { %>
		<p>
			YOU ARE CONNECTED TO TWITTER
		</p>
	<% } %>
	<% if(user.getFacebookCode() == null) { %>
		<form action="/facebookOAuth" method="post">
			<div>
				<input type="submit" value="Connect Facebook Account" />
			</div>
		</form>
	<% } else { %>
		<p>
			YOU ARE CONNECTED TO FACEBOOK
		</p>
	<% } %>
	<form action="/updateBackgroundImageServlet" method="post">
		<p>Background Photo</p>
		<div>
			<input type="radio" name="backgroundImage" value="backgroundImage_FlowersAndSky" checked/>Default</br>
			<input type="radio" name="backgroundImage" value="backgroundImage_Grassbells" />Default2</br>
			<input type="submit" value="Save Background Image" />
		</div>
	</form>
	<form action="/deleteAccount" method="post">
		<div>
			<input type="submit" value="Delete Account" />
		</div>
	</form>
	<form action="/changePassword" method="post">
		<div>
			<label id="blah">Current Password:</label>
			<input type="text" name="curPassword"
				value="${fn:escapeXml(curPassword)}" />
		</div>
		<div>
			<label>New Password:</label>
			<input type="text" name="newPassword"
				value="${fn:escapeXml(newPassword)}" />
		</div>
		<div>
			<label>Confirm New Password:</label>
			<input type="text" name="confirmNewPassword"
				value="${fn:escapeXml(confirmNewPassword)}" />
		</div>
		<div>
			<input type="submit" value="Change Password"/>
		</div>
	</form>
</section>

<%@ include file="./footer.jsp" %>