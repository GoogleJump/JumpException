<%String pageName= "settings";%>
<%@ include file="./header.jsp" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	pageContext.setAttribute("dynamicBackgroundImage", user.getBackgroundImage());

%>
<div class="${fn:escapeXml(dynamicBackgroundImage)}">
	<section id="Settings" class="container content-section text-center ">
		<div class="container body">
			<%
				pageContext.setAttribute("curPassword", "");
				pageContext.setAttribute("newPassword", "");
				pageContext.setAttribute("confirmNewPassword", "");
			%>
			<p class="theme-text">
				Social Medias
			<p>
			<% if(user.getTwitterAccessToken() == null) { %>
				<form action="/twitterOAuth" method="post">
					<div>
						<input type="submit" value="Connect Twitter Account" />
					</div>
				</form>
			<% } else { %>
				<p class="theme-text">
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
				<p class="theme-text">
					YOU ARE CONNECTED TO FACEBOOK
				</p>
			<% } %>
			<% if(user.getGooglePlusUserId() == null) { %>
			 	<form action="/plussampleservlet" method="get">
					<div>
						<input type="submit" value="Connect Google+ Account" />
					</div>
				</form>
			<% } else { %>
				<p class="theme-text">
					YOU ARE CONNECTED TO GOOGLE+
				</p>
			<% } %>
			<form action="/updateBackgroundImageServlet" method="post">
				<p class="theme-text">Background Photo</p>
				<a class="theme-text" href="http://www.bosleyjarrett.com">Photos by Bosley Jarrett Photography</a>
				<div class="radioBox">
					<div class="radios">
						<input type="radio" name="backgroundImage" value="backgroundImage_FlowersAndSky" checked> Flowers and Sky</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Grassbells" /> Grassbells</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Bridgewater" /> Bridgewater</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Cascades" /> Cascades</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Plane_Zealand" /> Plane Zealand</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Ripples" /> Ripples</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Sunset" /> Sunset</br>
						<input type="radio" name="backgroundImage" value="backgroundImage_Web" /> Web</br>
						
						<input type="submit" value="Save Background Image" />
					</div>
				</div>
			</form>
			<p class="theme-text">Profile</p>
			<label class="theme-text">Change Password</label>
			<form action="/changePassword" method="post">
				<div>
					<label id="blah">Current Password:</label>
					<input type="password" name="curPassword"
						value="${fn:escapeXml(curPassword)}" />
				</div>
				<div>
					<label>New Password:</label>
					<input type="password" name="newPassword"
						value="${fn:escapeXml(newPassword)}" />
				</div>
				<div>
					<label>Confirm New Password:</label>
					<input type="password" name="confirmNewPassword"
						value="${fn:escapeXml(confirmNewPassword)}" />
				</div>
				<div>
					<input type="submit" value="Change Password"/>
				</div>
			</form>
			<label class="theme-text">Delete Account</label>
			<form action="/deleteAccount" method="post">
				<div>
					<input type="submit" value="Delete Account" />
				</div>
			</form>
		</div>
	</section>
	<div class="post-bottom">
	</div>
</div>

<%@ include file="./footer.jsp" %>