package guestbook;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class TwitterOAuth {
	public TwitterOAuth() {}
	public String getAuthUrl(HttpServletRequest request) {
		// create twitter instance, set API keys
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("9y0uovmQ2KncyOs0cDnb2jsA5", "0Jw2R2GZAkX6lugrSBq1AHwMJebIL867u2JhyLZJ8sCkmW7n1L"); 
		RequestToken requestToken = null; 
		String authUrl = null; 
		// get and store request token and authorization URL
		try { 
			requestToken = twitter.getOAuthRequestToken(); 
				if (requestToken!=null) { 
					HttpSession session = request.getSession(); 
					String token = requestToken.getToken(); 
					String tokenSecret = requestToken.getTokenSecret(); 
					session.setAttribute("TOKEN_ATTRIBUTE", token); 
					session.setAttribute("TOKEN_SECRET_ATTRIBUTE", tokenSecret); 
					authUrl = requestToken.getAuthorizationURL(); 
				} 
		} catch (TwitterException e) { 
            e.printStackTrace(); 
        }
		return authUrl;
	}

}
