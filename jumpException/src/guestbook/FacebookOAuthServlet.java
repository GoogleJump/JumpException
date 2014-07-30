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
	      facebook.setOAuthAppId("570453233070594", "afcacdbbd1eb6b5395288ccc3d23d871");
	      facebook.setOAuthPermissions("public_profile,publish_actions");
	     
	      String callbackURL = facebook.getOAuthCallbackURL();
	      facebook.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookConnect");
	        
	      callbackURL = facebook.getOAuthCallbackURL();	
	      
	      response.getWriter().println("GOT HERE");
	      
	      response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL));
	  }
}
