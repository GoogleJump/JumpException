package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class LogIn {

//	private User user;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public LogIn(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void doLogIn() {
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  	req.setAttribute("logInFailed", "false");
	  	String signInText = req.getParameter("signInText");
    	String passwordText = req.getParameter("passwordText");
    	HttpSession session = req.getSession();
		session.setAttribute("username", signInText);
	    if(signInText.equals("") || passwordText.equals("")) {
	    	req.getSession().setAttribute("logInFailed", "true");
		    try {
				resp.sendRedirect("/index.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return;
	    }
	    Key signInKey = KeyFactory.createKey("SignIn", signInText);
	    Query query = new Query("Shub", signInKey);
	    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//	    Cookie cookie = new Cookie("username", signInText);
//	    cookie.setMaxAge(0);
//	    cookie.setValue(null);
//		resp.addCookie(cookie);
	    if(!greetings.isEmpty()) {
		    for(Entity user : greetings){
		    	if(user.getProperty("user").toString().equals(signInText.toString()) &&
		    	   user.getProperty("password").toString().equals(passwordText.toString())) {
			    	req.getSession().setAttribute("logInFailed", "false");
					try {
						resp.sendRedirect("/signedIn.jsp");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
		    	}
		    }
//		    req.setAttribute("logInFailed", "true");
//		    resp.sendRedirect("/logInFail.jsp");

	    }
    	req.getSession().setAttribute("logInFailed", "true");
	    try {
			resp.sendRedirect("/index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
