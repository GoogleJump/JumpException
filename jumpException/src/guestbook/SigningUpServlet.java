package guestbook;

import java.io.IOException;

import java.util.List;

import javax.jdo.JDOFatalUserException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import guestbook.PMF;
//import com.google.appengine.labs.repackaged.org.json.Cookie;


public class SigningUpServlet extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		  	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  	String signInText = req.getParameter("signInText");
		  	String passwordText = req.getParameter("passwordText");
		    
		  	HttpSession session = req.getSession();
    		session.setAttribute("username", signInText);
		  	if(signInText.equals("") || signInText.equals(null) || passwordText.equals("") || passwordText.equals(null)) {
		  		signInText = "";
		  		session.setAttribute("username", signInText);
		  		session.setAttribute("logInCreationFailed", "true");
		  		resp.sendRedirect("/index.jsp");
		  		return;
		  	}
		    
		    
//		    //NEW PERSISTENCEMANAGER
//		  	Key signInKey = KeyFactory.createKey(ShubUser.class.getSimpleName(), signInText);
//		    PersistenceManager pm = PMF.get().getPersistenceManager();
//		    try {
//		    	pm.getObjectById(ShubUser.class, signInKey);//username already defined
//		    	session.setAttribute("logInCreationFailed", "true");
//				resp.sendRedirect("/index.jsp");
//				pm.close();
//				return;
//		    } catch(JDOFatalUserException|JDOObjectNotFoundException e) {
//		    	//user is not in datastore so can create new user :)
//		  		session.setAttribute("logInCreationFailed", "false");
//			    ShubUser user = new ShubUser(signInText, passwordText, signInKey, new Newsfeed());
//			    try {
//			    	pm.makePersistent(user);
//			    	user = pm.detachCopy(user);
//			    } finally {
//			    	pm.close();
//			    }
//		    	session.setAttribute("user", user);
//			    resp.sendRedirect("/signedIn.jsp");
//		    }
//		    return;
//		    if(user != null) {
//		    	System.out.println("NOT NULL " + user.getUsername());
//		    } else {
//		    	System.out.println("NULL ");
//
//		    }
//		    return;
		    
		    
//			OLD WAY
		    Key signInKey = KeyFactory.createKey("SignIn", signInText);

		    Query query = new Query("Shub", signInKey);

		    List<Entity> usersInDatastore = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
		    if(!usersInDatastore.isEmpty()) {
			    for(Entity datastoreUser : usersInDatastore){
			    	if(datastoreUser.getProperty("username").toString().equals(signInText.toString())) {
				  		session.setAttribute("logInCreationFailed", "true");
						resp.sendRedirect("/index.jsp");
						return;
			    	}
			    }

		    }
	  		session.setAttribute("logInCreationFailed", "false");
			Entity signIn = new Entity("Shub", signInKey);
		    signIn.setProperty("username", signInText);
//		    //for checking typing password twice
		    signIn.setProperty("password", passwordText);
		    
		    //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    datastore.put(signIn);

		    ShubUser user = new ShubUser(signInText, signIn.getKey(), new Newsfeed());

	    	session.setAttribute("user", user);
			req.setAttribute("responseText", "You made it!");
//			Cookie cookie = new Cookie("username", signInText);
//			resp.addCookie(cookie);
		    resp.sendRedirect("/signedIn.jsp");

	  }
}
