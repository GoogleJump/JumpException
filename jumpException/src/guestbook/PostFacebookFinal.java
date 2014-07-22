package guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class PostFacebookFinal extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	StringBuffer URL = request.getRequestURL();
    	String code = request.getQueryString();
    	
    	if (code.charAt(0) == 'e'){
    		response.sendRedirect("signedIn.jsp");
    	}
    	    	    	
    	HttpSession session = request.getSession();
    	
    	code = code.substring(5);
    	
    	Facebook facebook1 = new FacebookFactory().getInstance();
    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
        facebook1.setOAuthPermissions("public_profile,publish_actions");
        facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookPost");
    	        
        AccessToken token = null;
        
    	try {
			token = facebook1.getOAuthAccessToken(code);
		} catch (FacebookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String name = null;
    	String messageID = null;
    	
    	try {
			messageID = facebook1.postStatusMessage("HELLO");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().println(e.toString());
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.toString());
			e.printStackTrace();
		}

    	response.getWriter().println(messageID);
    	response.sendRedirect("signedIn.jsp"); 
    }
}
