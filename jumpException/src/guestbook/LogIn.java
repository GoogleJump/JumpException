package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

public class LogIn {
	private ShubUser user;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public LogIn(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void doLogIn() throws IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
//	  	req.setAttribute("logInFailed", "false");
	  	String signInText = req.getParameter("signInText");
    	String passwordText = req.getParameter("passwordText");
    	
    	HttpSession session = req.getSession();
//		session.setAttribute("username", signInText);
	    if(signInText.equals("") || passwordText.equals("")) {
	    	session.setAttribute("logInFailed", "true");
	  		session.setAttribute("username", signInText);
		    try {
				resp.sendRedirect("/index.jsp");
			} 
		    catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return;
	    }
	    
	  //NEW PERSISTENCEMANAGER
//	    Key signInKey = KeyFactory.createKey(ShubUser.class.getSimpleName(), signInText);
//
//	    PersistenceManager pm = PMF.get().getPersistenceManager();
//	    
//	    try {
//	    	ShubUser user = pm.detachCopy(pm.getObjectById(ShubUser.class, signInKey));//username already defined
//	    	pm.close();
////	    	user.getNewsfeed().setPosts(new LinkedList<Post>());
//	    	if(user.getNewsfeed().getAllPosts() == null) {
//	    		user.getNewsfeed().setPosts(new LinkedList<Post>());
//	    	}
//		    session.setAttribute("user", user);
//    		session.setAttribute("logInFailed", "false");
//			try {
//				resp.sendRedirect("/signedIn.jsp");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return;
//	    } catch(JDOFatalUserException|JDOObjectNotFoundException e) {
//	    	//user is not in datastore so can create new user :)
//	    	session.setAttribute("logInFailed", "true");
//			try {
//				resp.sendRedirect("/index.jsp");
//			} catch (IOException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
//			pm.close();
//	    }
//	    return;

	    //OLD WAY
	    Key signInKey = KeyFactory.createKey("SignIn", signInText);
	    Query query = new Query("Shub", signInKey);
	    List<Entity> userDatabase = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//	    Cookie cookie = new Cookie("username", signInText);
//	    cookie.setMaxAge(0);
//	    cookie.setValue(null);
//		resp.addCookie(cookie);
	    if(userDatabase.size() == 1) {  //there should be only one username for each user!
//		    	datastore.delete(userInDatabase.getKey());
//		    	return;
	    	Entity userInDatabase = userDatabase.get(0);
	    	String username = signInText.toString();
	    	String password = passwordText.toString();
	    	if(userInDatabase.getProperty("username").toString().equals(signInText.toString()) &&
	    			userInDatabase.getProperty("password").toString().equals(passwordText.toString())) {
		    	user = new ShubUser(username, password, userInDatabase.getKey(), new Newsfeed());
		    	user.fillNewsfeed();
	    		user.fillAccessTokens(resp);
	    		session.setAttribute("user", user);
	    		session.setAttribute("logInFailed", "false");
	    		
	    		
	    		//Sending the redirect to Facebook
	    		/*Facebook facebook1 = new FacebookFactory().getInstance();
	        	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	            facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
	            facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookPost");
	            
	            String callbackURL = facebook1.getOAuthCallbackURL();
	            
	            if (user.getFacebookCode() != null){
	            	try {
						resp.sendRedirect(facebook1.getOAuthAuthorizationURL(callbackURL));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						resp.getWriter().println(e.toString());
					}
	            }else {*/
	            	try {
						resp.sendRedirect("/signedIn.jsp");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            //}
				return;
	    	}
	    }
    	session.setAttribute("logInFailed", "true");
	    try {
			resp.sendRedirect("/index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
