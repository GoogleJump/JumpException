package guestbook;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.Status;
import twitter4j.User;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.Entity;


public class TwitterOAuth {
	public TwitterOAuth() {}
	public void setAccessToken(HttpServletRequest request, HttpSession session) {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2"); 
		AccessToken accessToken = null;
		try {
			String token = (String) session.getAttribute("token");
			String tokenSecret = (String) session.getAttribute("tokenSecret");
			String oauth_verifier = request.getParameter("oauth_verifier");
			accessToken = twitter.getOAuthAccessToken(new RequestToken(token, tokenSecret), oauth_verifier);
			String accessTokenString = accessToken.getToken();
			String accessTokenSecret = accessToken.getTokenSecret();
			ShubUser user = (ShubUser) session.getAttribute("user");
			user.setTwitterToken(accessTokenString, accessTokenSecret);
			session.setAttribute("user", user);
//			session.setAttribute("twitterToken", token);
//			session.setAttribute("twitterSecret", tokenSecret);
		} catch (TwitterException e) {}
	}


}
