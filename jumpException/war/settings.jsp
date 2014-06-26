   
 <%@ include file="./header.jsp" %>

<section id="Settings" class="container content-section text-center">

	<%
		pageContext.setAttribute("curPassword", "");
		pageContext.setAttribute("newPassword", "");
		pageContext.setAttribute("confirmNewPassword", "");
	%>
	<form action="/changePassword" method="post">
		<div>
			<label id="blah">Current Password:</label>
			<input type="text" id="curPassword"
				value="${fn:escapeXml(curPassword)}" />
		</div>
		<div>
			<label>New Password:</label>
			<input type="text"
				value="${fn:escapeXml(newPassword)}" />
		</div>
		<div>
			<label>Confirm New Password:</label>
			<input type="text"
				value="${fn:escapeXml(confirmNewPassword)}" />
		</div>
	
	
		<div>
			<input type="submit" value="Change Password"/>
		</div>
	</form>
</section>

<%@ include file="./footer.jsp" %>