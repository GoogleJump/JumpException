package guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2"); 
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
