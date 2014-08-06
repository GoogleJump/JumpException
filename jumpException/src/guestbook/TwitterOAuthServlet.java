package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Key;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

public class TwitterOAuthServlet extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
			// create twitter instance, set API keys
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("e2dhz5jfNkBUjxLA5zvu6g2dF", "kkFca63qmafTc4gFO8657trsj4Xklf1gXyXQ5xYRv1LnR5ScvC"); 
			RequestToken requestToken = null; 
			String authUrl = null; 
			// get and store request token and authorization URL
			try { 
				requestToken = twitter.getOAuthRequestToken(); 
					if (requestToken!=null) { 
						req.getSession().setAttribute("token", requestToken.getToken());
						req.getSession().setAttribute("tokenSecret", requestToken.getTokenSecret());
						authUrl = requestToken.getAuthorizationURL(); 
					} 
			} catch (TwitterException e) { 
				
	            e.printStackTrace(); 
	        }
		    resp.sendRedirect(authUrl);		  	
	  }
}
