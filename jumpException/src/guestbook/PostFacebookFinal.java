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
    	    	
    	HttpSession session = request.getSession();
    	
    	if (code.charAt(0) == 'e'){
    		response.sendRedirect("/signedIn.jsp"); 
    	}
    	
    	code = code.substring(5);
    	
    	Facebook facebook1 = new FacebookFactory().getInstance();
    	facebook1.setOAuthAppId("570453233070594", "afcacdbbd1eb6b5395288ccc3d23d871");
        facebook1.setOAuthPermissions("public_profile");
        facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookPost");
    	
    	        
        AccessToken token = null;
        ShubUser user = (ShubUser) session.getAttribute("user");
        //user.setFacebookToken(code, "facebookPost",response);
        user.setFacebookCode(code);
        
        if (session.getAttribute("overallText") != null){
        	String overallText = (String) session.getAttribute("overallText");
        	String fbText = (String) session.getAttribute("fbText");
        	String twitterText = (String) session.getAttribute("twitterText");
        
        	session.setAttribute("user",user);
            user = (ShubUser) session.getAttribute("user");
            
            session.removeAttribute("overallText");
            session.removeAttribute("fbText");
            session.removeAttribute("twitterText");
            user.post(overallText, fbText, twitterText, request, response);
        }
        else{
        	Post post = (Post)session.getAttribute("post");
        	
        	session.setAttribute("user",user);
            user = (ShubUser) session.getAttribute("user");
        	session.removeAttribute("post");
        	
        	user.deletePost(request, response,post);
        }
        response.getWriter().println("IN HERE");
        response.getWriter().println(code);
        //facebook1.setOAuthAccessToken(user.getFacebookAccessToken());
        
        /*try {
			response.getWriter().println(facebook1.getName());
		} catch (IllegalStateException | FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().println(e.toString());
		}*/
        
		/*session.setAttribute("user", user);
    	
		response.getWriter().println(code);
		
		user = (ShubUser) session.getAttribute("user");
		token = user.getFacebookAccessToken();
		if (token == null){
			response.getWriter().println("NULL ACCESS TOKEN"
					+ "");
		}
		
		session.setAttribute("facebook",facebook1);*/
		//response.getWriter().println("got here");
        
		//response.sendRedirect("/signedIn.jsp"); 
    }
}
