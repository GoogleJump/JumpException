package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
//import com.google.appengine.labs.repackaged.org.json.Cookie;


public class SigningInServlet extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		  	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  	String signInText = req.getParameter("signInText");
		    Key signInKey = KeyFactory.createKey("SignIn", signInText);
			Cookie cookie = new Cookie("username", signInText);
			resp.addCookie(cookie);
		    Query query = new Query("Shub", signInKey);
		    
		    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
		    if(!greetings.isEmpty()) {

			    for(Entity user : greetings){
			    	if(user.getProperty("user").toString().equals(signInText.toString())) {
						resp.sendRedirect("/logInFail.jsp");
						return;
			    	}
			    }
			    
		    }	    	
		    
			Entity signIn = new Entity("Shub", signInKey);
		    signIn.setProperty("user", signInText);
		    //for checking typing password twice
		    String passwordText = req.getParameter("passwordText");
		    signIn.setProperty("password", passwordText);
		    
		    //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    datastore.put(signIn);
			req.setAttribute("responseText", "You made it!");
		    resp.sendRedirect("/signedIn.jsp");
			
	  }
}
