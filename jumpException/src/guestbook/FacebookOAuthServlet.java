package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

public class FacebookOAuthServlet extends HttpServlet {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
		  Facebook facebook = new FacebookFactory().getInstance();
	      facebook.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	      facebook.setOAuthPermissions("public_profile,publish_actions");
	     
	      String callbackURL = facebook.getOAuthCallbackURL();
	      facebook.setOAuthCallbackURL("http://1-dot-shubexception.appspot.com/facebookConnect");
	        
	      callbackURL = facebook.getOAuthCallbackURL();	
	      
	      response.getWriter().println("GOT HERE");
	      
	      
	      ShubUser user = (ShubUser) request.getSession().getAttribute("user");
	      response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL));
	  }
}
