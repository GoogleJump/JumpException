package guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateBackgroundImageServlet extends HttpServlet{
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String backgroundImage = req.getParameter("backgroundImage");
		 System.out.println("Background: " + backgroundImage);
		 ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		 user.setBackgroundImage(backgroundImage);
		 req.getSession().setAttribute("user", user);
		 resp.sendRedirect("/settings.jsp");
	}
}
