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


public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = -7453606094644144082L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/plain");
    	StringBuffer URL = request.getRequestURL();
    	String code = request.getQueryString();
    	    	
    	HttpSession session = request.getSession();
    	
    	if (code.charAt(0) == 'e'){
    		response.sendRedirect("http://1-dot-nietotesting.appspot.com/settings.jsp"); 
    	}
    	
    	code = code.substring(5);
    	
    	Facebook facebook1 = new FacebookFactory().getInstance();
    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
        facebook1.setOAuthPermissions("public_profile");
        facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookConnect");
    	
    	        
        AccessToken token = null;
        ShubUser user = (ShubUser) session.getAttribute("user");
        
    	try {
			token = facebook1.getOAuthAccessToken(code);
			user.setFacebookToken(code);
			session.setAttribute("user", user);
    	} catch (FacebookException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getErrorMessage());
		}

    	
    	try {
			response.getWriter().println(facebook1.getName());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	response.sendRedirect("http://1-dot-nietotesting.appspot.com/settings.jsp");  
    }
}
