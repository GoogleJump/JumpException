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


public class DeleteAccountServlet extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		  	String username = req.getCookies()[0].getValue(); //this should be the username
		  	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  	Key signInKey = KeyFactory.createKey("SignIn", username);
		  	Query query = new Query("Shub", signInKey);
		  	List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
		  	for(Entity user : greetings) {
		  		if(user.getProperty("user").equals(username)) {
		  			datastore.delete(user.getKey());
		  			resp.sendRedirect("/logIn.jsp");
		  		}
		  	}
		  	
		  	
	  }
}
