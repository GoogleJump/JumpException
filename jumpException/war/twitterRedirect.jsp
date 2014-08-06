<%@ page import = "guestbook.TwitterOAuth"%>
<%@ include file="./header.jsp" %>
<%@ page session="true" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	TwitterOAuth t = new TwitterOAuth();
	t.setAccessToken(request, session, response);
	
	response.getWriter().println(user.getTwitterAccessToken().toString());
  	response.sendRedirect("/settings.jsp");		
%>