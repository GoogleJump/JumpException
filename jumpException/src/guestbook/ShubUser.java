package guestbook;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Status;
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
public class ShubUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6791653357365117438L;
	@Persistent
	private String username;
	
	@Persistent
	private String password;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key datastoreKey;
	
	@Persistent(dependent="true")
	private Newsfeed newsfeed;
	
	private String backgroundImage;
	
	private AccessToken twitterAccessToken;

	public ShubUser(String username, String password, Key datastoreKey, Newsfeed newsfeed) {
		this.username = username;
		this.password = password;
		this.datastoreKey = datastoreKey;
		this.newsfeed = newsfeed;
		this.twitterAccessToken = null;
		this.backgroundImage = "backgroundImage_FlowersAndSky";//default backgound image
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Key getKey() {
		return datastoreKey;
	}
	
	public Newsfeed getNewsfeed() {
		return newsfeed;
	}
	
	public String getBackgroundImage() {
		return backgroundImage;
	}
	
	public void fillNewsfeed() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    // Run an ancestor query to ensure we see the most up-to-date
	    // view of the Greetings belonging to the selected Guestbook.
	    Query query = new Query("Post", datastoreKey).addSort("date", Query.SortDirection.ASCENDING);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
	    for(Entity entity : entities) {
	    	Date date = (Date) entity.getProperty("date");
	    	Object overallObj = entity.getProperty("overallPost");
	    	Object fbObj = entity.getProperty("fbPost");
	    	Object twitterObj = entity.getProperty("twitterPost");
	    	long twitterPostId = (long) entity.getProperty("twitterPostId");
	    	String overallText = voidChecking(overallObj);
	    	String fbText = voidChecking(fbObj);
	    	String twitterText = voidChecking(twitterObj);
	    	newsfeed.addFirst(new Post(date, overallText.toString(), fbText.toString(), twitterText.toString(), twitterPostId, entity.getKey()));
	    }
	}
	
	private String voidChecking(Object textObj) {
		if(textObj == null) {
	    	return "";
	    }
		return textObj.toString();
	}
	
	public boolean changePassword(String curPassword, String newPassword, String confirmNewPassword) {
		if(arePasswordsUsable(curPassword, newPassword, confirmNewPassword)) {
			if(curPassword.equals(password) && 
				newPassword.equals(confirmNewPassword)) {
				setPassword(confirmNewPassword);
				return true;
			}
		}
		return false;
	}
	
	public void setPassword(String confirmNewPassword) {
		// TODO Auto-generated method stub
		this.password = confirmNewPassword;
	}
	
	public void setKey(Key key) {
		this.datastoreKey = key;
	}

	private boolean arePasswordsUsable(String curPassword, String newPassword,
			String confirmNewPassword) {
		// TODO Auto-generated method stub
		if(curPassword != null && newPassword != null && confirmNewPassword != null) {
			return !(curPassword.equals("") || newPassword.equals("") || confirmNewPassword.equals(""));
		}
		return false;
	}

	/**
	   * Always treat de-serialization as a full-blown constructor, by
	   * validating the final state of the de-serialized object.
	   */
	   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
	     //always perform the default de-serialization first
	     aInputStream.defaultReadObject();

	     //make defensive copy of the mutable Date field
//	     fDateOpened = new Date(fDateOpened.getTime());

	     //ensure that object state has not been corrupted or tampered with maliciously
//	     validateState();
	  }

	    /**
	    * This is the default implementation of writeObject.
	    * Customise if necessary.
	    */
	    private void writeObject(
	      ObjectOutputStream aOutputStream
	    ) throws IOException {
	      //perform the default serialization for all non-transient, non-static fields
	      aOutputStream.defaultWriteObject();
	    }

		public void setUsername(String string) {
			// TODO Auto-generated method stub
			this.username = string;
		}

		public void deleteAccount(HttpServletRequest req, HttpServletResponse resp) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		  	Key signInKey = KeyFactory.createKey("SignIn", user.getUsername());
//		  	Query query = new Query("Shub", datastoreKey);
//		  	List<Entity> userDatabase = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//		  	if(userDatabase.size() == 1) { //there should only be one
//		  		if(!userInDatabase.getKey().toString().equals(user.getKey().toString())) {
//		  			user.setUsername(user.getKey().toString() + " " + userInDatabase.getKey().toString());
//		  			resp.sendRedirect("/signedIn.jsp");
//		  			return;
//		  		}

//		  		if(userInDatabase.getProperty("username").toString().equals(user.getUsername())) {
		  			newsfeed.delete();
		  			datastore.delete(datastoreKey);
		  			req.getSession().invalidate();
		  			try {
						resp.sendRedirect("/index.jsp");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		  			return;
//		  		}
//		  	}
		  	
		  	//IF THIS EVER OCCURS IT IS AN ERROR. EVENTUALLY SOMETHING SHOULD BE DONE
//		  	req.getSession().invalidate();
//		  	try {
//				resp.sendRedirect("/index.jsp");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		  	
		}
		
		public boolean deletePost(HttpServletRequest req, HttpServletResponse resp, Post post) {
			if(post == null) {
				String date = req.getParameter("hiddenDate").toString();
				post = newsfeed.getPost(date);
			}
			newsfeed.removePost(post);
			req.getSession().setAttribute("user", this);
//			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//			Query query = new Query("Post", datastoreKey).addSort("date", Query.SortDirection.ASCENDING);
//		    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//		    for(Entity entity : entities) {
//		    	if(entity.getProperty("date").equals(post.getDate())) {
//		    		datastore.delete(entity.getKey());
//		    		newsfeed.removePost(post);
//		    		req.getSession().setAttribute("user", this);
//		    	}
//			}
			try {
				resp.sendRedirect("/signedIn.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		public long twitterPost(String twitterText) {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2"); 
			Status status = null;
			try {
				if(twitterAccessToken != null) {
					twitter.setOAuthAccessToken(twitterAccessToken);
					status = twitter.updateStatus(twitterText);
				}
			} catch (TwitterException e) {}
			if (status == null) return -1;
			else return status.getId();
		}
		
		public void post(String overallText, String fbText, String twitterText, HttpServletRequest req, HttpServletResponse resp) {
			Date date = new Date();
		    
		    Entity post = new Entity("Post", datastoreKey);
		    overallText = voidOverallChecking(overallText);
		    fbText = voidFacebookChecking(fbText, overallText, req);
		    twitterText = voidTwitterChecking(twitterText, overallText, req);
		    long twitterPostId = twitterPost(twitterText);

		    post.setProperty("date", date);
		    post.setProperty("overallPost", overallText);
		    post.setProperty("fbPost", fbText);
		    post.setProperty("twitterPost", twitterText);
		    post.setProperty("twitterPostId", twitterPostId);
		    
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    datastore.put(post);
		    newsfeed.addFirst(new Post(date, overallText, fbText, twitterText, twitterPostId, post.getKey()));
			req.getSession().setAttribute("user", this);
		    try {
				resp.sendRedirect("/signedIn.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private String voidOverallChecking(Object textObj) {
			if(textObj == null) {
		    	return "";
		    }
			return textObj.toString();
		}
		
		private String voidFacebookChecking(Object textObj, Object overallText, HttpServletRequest req) {
			System.out.println("FACEBOOK CHECKBOX " + req.getParameter("fbCheckbox"));
			boolean isFbCheckboxChecked = req.getParameter("fbCheckbox") != null;
		    if(!isFbCheckboxChecked) {
		    	textObj = overallText;
		    } else if(textObj == null){
		    	return "";
		    }
			return textObj.toString();
		}
		
		private String voidTwitterChecking(Object textObj, Object overallText, HttpServletRequest req) {
			System.out.println("TWITTER CHECKBOX " + req.getParameter("twitterCheckbox"));
			boolean isTwitterCheckboxChecked = req.getParameter("twitterCheckbox") != null;
		    if(!isTwitterCheckboxChecked) {
		    	textObj = overallText;
		    } else if(textObj == null){
		    	return "";
		    }
			
			return textObj.toString();
		}

		public void setTwitterToken(String accessTokenString,
				String accessTokenSecret) {
			// TODO Auto-generated method stub
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			Entity entity = null;
			if(twitterAccessToken != null) {
				Query query = new Query("TwitterAccessToken", datastoreKey);
			    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
			    if(entities.size() == 1) {
			    		entity = entities.get(0);
				} else {
					//ERROR
					return;
				}
			} else {
				entity = new Entity("TwitterAccessToken", datastoreKey);
			}
			entity.setProperty("accessToken", accessTokenString);
			entity.setProperty("accessTokenSecret", accessTokenSecret);
			datastore.put(entity);
			
			twitterAccessToken = new AccessToken(accessTokenString, accessTokenSecret);
		}
		
		public AccessToken getTwitterAccessToken() {
			return twitterAccessToken;
		}

		public void fillAccessTokens() {
			// TODO Auto-generated method stub
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			//TwitterTokens
			Query query = new Query("TwitterAccessToken", datastoreKey);
		    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
		    if(entities.size() == 1) {
		    		Entity entity = entities.get(0);
		    		twitterAccessToken = new AccessToken(entity.getProperty("accessToken").toString(), entity.getProperty("accessTokenSecret").toString());
			} else {
				//No Token
				return;
			}
		    
		    //FacebookTokens
		    
		}

		public void setBackgroundImage(String backgroundImage) {
			// TODO Auto-generated method stub
			this.backgroundImage = backgroundImage;
		}

}
