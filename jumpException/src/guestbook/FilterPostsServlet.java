package guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterPostsServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
//		System.out.println("SEARCH TEXT : " + req.getParameter("searchText"));
		req.getSession().setAttribute("searchText", req.getParameter("searchText"));
		resp.sendRedirect("/signedIn.jsp");
	}
}
