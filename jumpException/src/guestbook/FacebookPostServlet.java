package guestbook;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;


public class FacebookPostServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		
		/*if (user.getFacebookCode() != null){
		
			Facebook facebook1 = (Facebook) req.getSession().getAttribute("facebook");
        
        	AccessToken token = user.getFacebookAccessToken();
        
        	if (token == null){
        		resp.getWriter().println("NULL TOKEN");
        	}
        
        	String code = user.getFacebookCode();
        
        	facebook1.setOAuthAccessToken(user.getFacebookAccessToken());
        
        	try {
				resp.getWriter().println(facebook1.getName());
			} catch (IllegalStateException | FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().println(e.toString());
			}
        
        
        	try {
				facebook1.postStatusMessage("HERE");
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().println(e.toString());
			}
		}*/
		/*if (user.getFacebookCode() != null){
			user.setFacebookToken(user.getFacebookCode(), "facebookPost",resp);
		}*/
		//else{
			resp.sendRedirect("/signedIn.jsp");
		//}
	}
}
