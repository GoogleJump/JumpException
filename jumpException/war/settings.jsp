   
 <%@ include file="./header.jsp" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
%>
<section id="Settings" class="container content-section text-center">

	<%
		pageContext.setAttribute("curPassword", "");
		pageContext.setAttribute("newPassword", "");
		pageContext.setAttribute("confirmNewPassword", "");
	%>
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