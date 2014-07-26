package guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelEditPostServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		String date = req.getParameter("hiddenDate").toString();
		Post post = user.getNewsfeed().getPost(date);
		post.setIsEditing(false);
		req.getSession().setAttribute("user", user);
		resp.sendRedirect("/signedIn.jsp");
	}
}
