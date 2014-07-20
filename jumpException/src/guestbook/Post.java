package guestbook;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

@PersistenceCapable(detachable="true")
public class Post implements Serializable {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Date date;
	
	@Persistent
	private String overallText;
	
	@Persistent
	private String fbText;
	
	@Persistent
	private String twitterText;
	
	private long twitterPostId;
	
	public Post(Date date, String overallText, String fbText, String twitterText, long twitterPostId, Key key) {
		this.date = date;
		this.overallText = overallText;
		this.fbText = fbText;
		this.twitterText = twitterText;
		this.twitterPostId = twitterPostId;
		this.key = key;
	}
	
	public String getText(String socialMedia) {
		switch(socialMedia) {
			case "date" : return date.toString();
			case "overall" : return overallText;
			case "facebook" : return fbText;
			case "twitter" : return twitterText;
			default : return "ERROR";//error
		}
	}

	//THIS METHOD IS NOT CAP SENSITIVE
	public boolean contains(String filter) {
		// TODO Auto-generated method stub
		filter = filter.toLowerCase();
		return fbText.toLowerCase().contains(filter) || twitterText.toLowerCase().contains(filter) || overallText.toLowerCase().contains(filter);
	}
	
	public Date getDate() {
		return date;
	}

	public boolean delete(AccessToken twitterAccessToken) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Post", key).addSort("date", Query.SortDirection.ASCENDING);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
	    if(entities.size() == 1) {
		    	deleteTwitterPost(twitterAccessToken);
	    		datastore.delete(key);
	    		return true;
		}
	    return false;
	}

	private void deleteTwitterPost(AccessToken twitterAccessToken) throws IOException {
		if(twitterPostId != -1 && twitterAccessToken != null) {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2");
			twitter.setOAuthAccessToken(twitterAccessToken);
			try {
				twitter.destroyStatus(twitterPostId);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
