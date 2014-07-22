package guestbook;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;


public class FacebookPostServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Facebook facebook1 = new FacebookFactory().getInstance();
    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
        facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
        facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookPost");
        
        String callbackURL = facebook1.getOAuthCallbackURL();	
        
        resp.sendRedirect(facebook1.getOAuthAuthorizationURL(callbackURL));
	}
}
