package guestbook;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

// NOTE: posting to twitter is quite buggy right now, to actually send a post
// you must Connect with Twitter TWICE, then post to twitter. To post again, you
// must Connect with Twitter again (just once). I believe it is buggy due to 
// variables not being stored in sessions properly, this should be resolved when
// I am able to store the AccessToken in the actual ShubUser object.

public class TwitterPostStatusServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2"); 
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		try {
			AccessToken twitterAccessToken = user.getTwitterAccessToken();
			if(twitterAccessToken != null) {
				twitter.setOAuthAccessToken(user.getTwitterAccessToken());
				twitter.updateStatus("hi");
			}
		} catch (TwitterException e) {}
		resp.sendRedirect("/signedIn.jsp");
	}
}