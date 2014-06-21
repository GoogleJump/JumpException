package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
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
//import com.google.appengine.labs.repackaged.org.json.Cookie;


public class LogInPage extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		  	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  	req.setAttribute("logInFailed", "false");
		  	String signInText = req.getParameter("signInText");
	    	String passwordText = req.getParameter("passwordText");
		    HttpSession session = req.getSession();
    		session.setAttribute("username", signInText);
		    if(signInText.equals("") || passwordText.equals("")) {
		    	req.getSession().setAttribute("logInFailed", "true");
			    resp.sendRedirect("/index.jsp");
			    return;
		    }
		    Key signInKey = KeyFactory.createKey("SignIn", signInText);
		    Query query = new Query("Shub", signInKey);
		    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//		    Cookie cookie = new Cookie("username", signInText);
//		    cookie.setMaxAge(0);
//		    cookie.setValue(null);
//			resp.addCookie(cookie);
		    if(!greetings.isEmpty()) {
			    for(Entity user : greetings){
			    	if(user.getProperty("user").toString().equals(signInText.toString()) &&
			    	   user.getProperty("password").toString().equals(passwordText.toString())) {
				    	req.getSession().setAttribute("logInFailed", "false");
						resp.sendRedirect("/signedIn.jsp");
						return;
			    	}
			    }
//			    req.setAttribute("logInFailed", "true");
//			    resp.sendRedirect("/logInFail.jsp");

		    }
	    	req.getSession().setAttribute("logInFailed", "true");
		    resp.sendRedirect("/index.jsp");

//		    resp.sendRedirect("/logInFail.jsp");

//			Entity signIn = new Entity("Shub", signInKey);
//		    signIn.setProperty("user", signInText);
//		    //for checking typing password twice
//		    String passwordText = req.getParameter("passwordText");
//		    signIn.setProperty("password", passwordText);
//
//		    //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		    datastore.put(signIn);
//			req.setAttribute("responseText", "You made it!");
//		    resp.sendRedirect("/signedIn.jsp");

	  }
}
