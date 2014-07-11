package guestbook;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
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
		  	ShubUser user = (ShubUser) req.getSession().getAttribute("user"); //this should be the username
		  	
//NEW PERSISTENCEMANAGER
//		    PersistenceManager pm = PMF.get().getPersistenceManager();
//		    pm.deletePersistent(user);
//		  	req.getSession().invalidate();
//		  	resp.sendRedirect("/index.jsp");
		  	
//		  	OLD WAY
		  	user.deleteAccount(req, resp);
		  	resp.sendRedirect("/index.jsp");
	  }
		  	
}
