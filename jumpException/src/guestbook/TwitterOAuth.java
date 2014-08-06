package guestbook;

import java.io.IOException;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.Status;
import twitter4j.User;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.DatastoreServiceFactory;


public class TwitterOAuth {
	public TwitterOAuth() {}
	public void setAccessToken(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("e2dhz5jfNkBUjxLA5zvu6g2dF", "kkFca63qmafTc4gFO8657trsj4Xklf1gXyXQ5xYRv1LnR5ScvC"); 
		AccessToken accessToken = null;
		try {
			String token = (String) request.getSession().getAttribute("token");
			String tokenSecret = (String) request.getSession().getAttribute("tokenSecret");
			String oauth_verifier = request.getParameter("oauth_verifier");
			accessToken = twitter.getOAuthAccessToken(new RequestToken(token, tokenSecret), oauth_verifier);
			String accessTokenString = accessToken.getToken();
			String accessTokenSecret = accessToken.getTokenSecret();
			ShubUser user = (ShubUser) request.getSession().getAttribute("user");
			user.setTwitterToken(accessTokenString, accessTokenSecret);
			request.getSession().setAttribute("user", user);
			user = (ShubUser) request.getSession().getAttribute("user");
		} catch (TwitterException e) {}
	}


}
