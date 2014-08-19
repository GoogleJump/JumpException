package guestbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.Photo;
import facebook4j.PhotoUpdate;
import facebook4j.PostUpdate;


@PersistenceCapable(detachable="true")
public class ShubUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6791653357365117438L;
	@Persistent
	private String username;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key datastoreKey;
	
	@Persistent(dependent="true")
	private Newsfeed newsfeed;
	
	private String backgroundImage;
	
	private AccessToken twitterAccessToken;
	
	private String facebookCode;
	
	private String googlePlusUserId;
	
	private facebook4j.auth.AccessToken facebookAccessToken;

	public ShubUser(String username, Key datastoreKey, Newsfeed newsfeed) {
		this.username = username;
		this.datastoreKey = datastoreKey;
		this.newsfeed = newsfeed;
		this.twitterAccessToken = null;
		this.facebookAccessToken = null;
		this.facebookCode = null;
		this.googlePlusUserId = null;
		this.backgroundImage = "backgroundImage_FlowersAndSky";//default backgound image
	}
	
	public String getGooglePlusUserId() {
		return googlePlusUserId;
	}
	
	public void setGooglePlusUserId(String googlePlusUserId) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = null;
		Query query = new Query("GooglePlusUserId", datastoreKey);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
	    if(entities.size() == 1) {
	    	System.out.println("in datastore");
	    	entity = entities.get(0);
		} else {
			//ERROR
	    	System.out.println("not in datastore");
			entity = new Entity("GooglePlusUserId", datastoreKey);
		}

		entity.setProperty("googlePlusUserId", googlePlusUserId);
		datastore.put(entity);
		this.googlePlusUserId = googlePlusUserId;
	}
	
	public Person getGooglePlusProfile(String googlePlusUserId, HttpServletRequest req, HttpServletResponse resp) {
	    Person profile = null;
	    Credential credential = null;
	    GoogleAuthorizationCodeFlow authFlow = null;
	    try {
			authFlow = Utils.initializeFlow();
		    credential = authFlow.loadCredential(googlePlusUserId);
		    if (credential == null) {
		      // If we don't have a token in store, redirect to authorization screen.
		      resp.sendRedirect(
		          authFlow.newAuthorizationUrl().setRedirectUri(Utils.getRedirectUri(req)).build());
		      return profile;
		    }
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }

	    // If we do have stored credentials, build the Plus object using them.
	    Plus plus = new Plus.Builder(
	        Utils.HTTP_TRANSPORT, Utils.JSON_FACTORY, credential).setApplicationName("").build();
	    // Make the API call

	    try {
	    	profile = plus.people().get("me").execute();
	    	HttpSession session = req.getSession();
	    	ShubUser user = (ShubUser) session.getAttribute("user");
	    	
	    	user.setGooglePlusUserId(Utils.getUserId(req));
	    	session.setAttribute("user", user);
	    } catch(IOException e) {
	    	//The Authorization has been revoked so we must ask for it again
	    	ShubUser user = (ShubUser) req.getSession().getAttribute("user");
	    	user.deleteGooglePlusUserId();
	    	req.getSession().setAttribute("user", user);
	    	try {
				authFlow.getCredentialStore().delete(Utils.getUserId(req), credential);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	try {
				resp.sendRedirect(
				          authFlow.newAuthorizationUrl().setRedirectUri(Utils.getRedirectUri(req)).build());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	      return profile;
	    }
	    return profile;
	}
	
	public void deleteGooglePlusUserId() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = null;
		Query query = new Query("GooglePlusUserId", datastoreKey);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
	    if(entities.size() == 1) {
	    	System.out.println("in datastore");
	    	entity = entities.get(0);
	    	datastore.delete(entity.getKey());
		}
		this.googlePlusUserId = null;
	}
	
	public String getUsername() {
		return username;
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
	    	String fbPostID = (String) entity.getProperty("fbPostID");
	    	String blobURL = (String) entity.getProperty("blobURL");
	    	BlobKey blobKey = (BlobKey) entity.getProperty("blobKey");
	    	newsfeed.addFirst(new Post(date, overallText.toString(), fbText.toString(), twitterText.toString(), twitterPostId, entity.getKey(),fbPostID,blobURL, blobKey));
	    }
	}
	
	private String voidChecking(Object textObj) {
		if(textObj == null) {
	    	return "";
	    }
		return textObj.toString();
	}
	
	public boolean changePassword(String curPassword, String newPassword, String confirmNewPassword) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key signInKey = KeyFactory.createKey("SignIn", username);
	    Query query = new Query("Shub", signInKey);
	    List<Entity> userDatabase = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
	    if(userDatabase.size() == 1) {
	    	Entity entity = userDatabase.get(0);
			if(arePasswordsUsable(curPassword, newPassword, confirmNewPassword)) {
				if(curPassword.equals(entity.getProperty("password")) && 
					newPassword.equals(confirmNewPassword)) {
					entity.setProperty("password", confirmNewPassword);
					datastore.put(entity);
					return true;
				}
			}
	    }
		return false;
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
		
		public boolean deletePost(HttpServletRequest req, HttpServletResponse resp, Post post) throws IOException {
			
			Facebook facebook1 = new FacebookFactory().getInstance();
	    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	        facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
	        facebook1.setOAuthCallbackURL("http://1-dot-shubexception.appspot.com/facebookPost");
	        
			if(post == null) {
				String date = req.getParameter("hiddenDate").toString();
				post = newsfeed.getPost(date);
			}
			
			
			try {
				facebookAccessToken = facebook1.getOAuthAccessToken(facebookCode);
				
				//req.getSession().setAttribute("facebook",facebook1);
				
				
				
				newsfeed.removePost(post, twitterAccessToken, facebook1);
				req.getSession().setAttribute("user", this);
							
				try {
					resp.sendRedirect("/signedIn.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FacebookException e1) {
				req.getSession().setAttribute("post", post);
				
				resp.sendRedirect(facebook1.getOAuthAuthorizationURL("http://1-dot-shubexception.appspot.com/facebookPost"));
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
			return true;
		}

		public long twitterPost(String twitterText, HttpServletResponse resp, HttpServletRequest req) throws IOException {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("e2dhz5jfNkBUjxLA5zvu6g2dF", "kkFca63qmafTc4gFO8657trsj4Xklf1gXyXQ5xYRv1LnR5ScvC"); 
			Status status = null;
			try {
				if(twitterAccessToken != null) {
					// user has authenticated with twitter
					twitter.setOAuthAccessToken(twitterAccessToken);
					
					if (twitterText.length() > 140){
						twitterText = twitterText.substring(0,140);
					}
					StatusUpdate s = new StatusUpdate(twitterText);
					BlobKey blobkey = (BlobKey) req.getSession().getAttribute("blobKey");
					InputStream is = null;
					if (blobkey != null){
						try {
							is = new BlobstoreInputStream(blobkey);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (is != null){
						s.setMedia("photo", is);
					}
					resp.getWriter().println(twitterAccessToken.toString());
					resp.getWriter().println(twitterText);
					status = twitter.updateStatus(s);
				} else {}
			} catch (TwitterException e) {}
			if (status == null) {
				if(twitterAccessToken != null) {
					twitterAccessToken = null;
					req.getSession().setAttribute("user", this);
					deleteTwitterAccessToken();
				}
				return -1;
			}
			return status.getId();
		}
		
		public twitter4j.User getTwitterUser() {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("H85zXNFtTHBIUgpFA3pGqDWoV", "rwUCF2JW8pG7lwKKLCIEs6MKDtiQbUeAIswlNxocPBZPlsFYi2"); 
			try {
				if(twitterAccessToken != null) {
					// user has authenticated with twitter
					twitter.setOAuthAccessToken(twitterAccessToken);
					return twitter.showUser(twitter.getId());
				} else {}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			//something went wrong
			return null;
		}
		
		public facebook4j.User getFacebookUser() {
			Facebook facebook1 = new FacebookFactory().getInstance();
	    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	        facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
	        facebook1.setOAuthCallbackURL("http://1-dot-nietotesting.appspot.com/facebookPost");
	        
	        try {
				facebookAccessToken = facebook1.getOAuthAccessToken(facebookCode);
				return facebook1.getMe();
	        } catch (FacebookException e) {
				// TODO Auto-generated catch block
			}
	        return null;
		}
		
//		public Status twitterPostWithFileMedia(Twitter twitter, String status, File media) {
//			// construct the custom request
//	    	String url = "https://upload.twitter.com/1/statuses/update_with_media.json";
//	    	twitter4j.HttpParameter p1 = new twitter4j.HttpParameter("status",status);
//	    	twitter4j.HttpParameter p2 = new twitter4j.HttpParameter("media[]",media);
//	    	twitter4j.HttpParameter[] arr = { p1, p2 };
//	    	
//	    	try {
////				media.toURL();
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    	// make call to factory method
//	    	
//		}
		
		public void deleteTwitterAccessToken() {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity entity = null;
			Query query = new Query("TwitterAccessToken", datastoreKey);
			List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
			entity = entities.get(0);
			datastore.delete(entity.getKey());
			twitterAccessToken = null;
		}
		
				
		public void post(String overallText, String fbText, String twitterText, HttpServletRequest req, HttpServletResponse resp) throws IOException {
			HttpSession session = req.getSession();
//			session.setAttribute("fbText", fbText);
//			session.setAttribute("twitterText", twitterText);
//			session.setAttribute("overallText", overallText);
			resp.getWriter().println(twitterText);
			//long twitterPostId = twitterPost(twitterText, resp, req);
			//resp.getWriter().println(twitterPostId);
			Facebook facebook1 = new FacebookFactory().getInstance();
	    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	        facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
	        facebook1.setOAuthCallbackURL("http://1-dot-shubexception.appspot.com/facebookPost");
	        
	        try {
				facebookAccessToken = facebook1.getOAuthAccessToken(facebookCode);
				
				resp.getWriter().println("got access tken");
				//req.getSession().setAttribute("facebook", facebook1);

				Date date = new Date();
				System.out.println("twitterPost");

			    Entity post = new Entity("Post", datastoreKey);
			    
			    long twitterPostId = twitterPost(twitterText, resp, req);
			    resp.getWriter().println("here twitter post");
				String facebookPostID = facebookPost(req, fbText,facebook1, resp); 
//				long twitterPostId = twitterPost(twitterText, resp, req);
			    
				resp.getWriter().println(facebookPostID);
				resp.getWriter().println(twitterPostId);
				BlobKey blobKey = (BlobKey) req.getSession().getAttribute("blobKey");

			    post.setProperty("date", date);
			    post.setProperty("overallPost", overallText);
			    post.setProperty("fbPost", fbText);
			    post.setProperty("twitterPost", twitterText);
			    post.setProperty("twitterPostId", twitterPostId);
			    post.setProperty("fbPostID", facebookPostID);
		    	
			    String blobURL = null;
			    if (blobKey != null) {
			    	ImagesService imagesService = ImagesServiceFactory.getImagesService();
			    	blobURL = imagesService.getServingUrl(blobKey);
				    req.getSession().removeAttribute("blobKey");
			    } else if(req.getSession().getAttribute("editImageURL") != null) { //from editing
			    	blobURL = req.getSession().getAttribute("editImageURL").toString();
			    	req.getSession().removeAttribute("editImageURL");
			    }
			    post.setProperty("blobURL", blobURL);
			    post.setProperty("blobKey", blobKey);
			    
			    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    datastore.put(post);
			    newsfeed.addFirst(new Post(date, overallText, fbText, twitterText, twitterPostId, post.getKey(),facebookPostID, blobURL,blobKey));
				req.getSession().setAttribute("user", this);
			    resp.getWriter().println("GOT HERE");
				try {
					Object deleteDate = req.getSession().getAttribute("deleteDate");
					if(deleteDate == null){
						resp.sendRedirect("/signedIn.jsp");
					} else {
						String deleteString = deleteDate.toString();
						Post deletePost = getNewsfeed().getPost(deleteString);
						req.getSession().removeAttribute("deleteDate");
						deletePost(req, resp, deletePost);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().println(e.toString());
				req.getSession().setAttribute("fbText", fbText);
				req.getSession().setAttribute("twitterText", twitterText);
				req.getSession().setAttribute("overallText", overallText);
				resp.sendRedirect(facebook1.getOAuthAuthorizationURL("http://1-dot-shubexception.appspot.com/facebookPost"));
			}
			
			
			/*Date date = new Date();
			System.out.println("twitterPost");
			req.getSession().setAttribute("fbText", fbText);
			req.getSession().setAttribute("twitterText", twitterText);
			req.getSession().setAttribute("overallText", overallText);
			String facebookPostID = facebookPost(fbText,resp); 
			long twitterPostId = twitterPost(twitterText, resp, req);
		    Entity post = new Entity("Post", datastoreKey);
		    overallText = voidOverallChecking(overallText);
		    fbText = voidFacebookChecking(fbText, overallText, req);
		    twitterText = voidTwitterChecking(twitterText, overallText, twitterPostId, req);
		    

		    post.setProperty("date", date);
		    post.setProperty("overallPost", overallText);
		    post.setProperty("fbPost", fbText);
		    post.setProperty("twitterPost", twitterText);
		    post.setProperty("twitterPostId", twitterPostId);
		    
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    datastore.put(post);
		    newsfeed.addFirst(new Post(date, overallText, fbText, twitterText, twitterPostId, post.getKey()));
			req.getSession().setAttribute("user", this);
		    resp.getWriter().println("GOT HERE");
			try {
				resp.sendRedirect("/signedIn.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
		
		private String facebookPost(HttpServletRequest req, String fbText, Facebook facebook1, HttpServletResponse resp) throws IOException {
			// TODO Auto-generated method stub
			BlobKey blobkey = (BlobKey) req.getSession().getAttribute("blobKey");
			InputStream is = null;
			if (blobkey != null){
				resp.getWriter().println("NOT NULL BLOBKEY");
				try {
					is = new BlobstoreInputStream(blobkey);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null){
				Media media = new Media("photo",is);
				try {
					resp.getWriter().println("TRYING TO PRINT PICTURE");
					PhotoUpdate photo = new PhotoUpdate(media);
					photo.setMessage(fbText);
					String photoId = facebook1.postPhoto(photo);
					return photoId;
				} catch (FacebookException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			else {
				try {
					return facebook1.postStatusMessage(fbText);
				} catch (FacebookException | IllegalStateException e) {
					// TODO Auto-generated catch block
					resp.getWriter().println(e.toString());
					e.printStackTrace();
					return null;
				}
			}
			/*Facebook facebook1 = new FacebookFactory().getInstance();
	    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	        facebook1.setOAuthPermissions("public_profile,publish_actions,create_event");
	        facebook1.setOAuthCallbackURL("http://1-dot-shubexception.appspot.com/facebookPost");
	        
	        try {
				facebook1.setOAuthAccessToken(facebookAccessToken);
				return facebook1.postStatusMessage(fbText);
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().println(e.toString());
				//resp.sendRedirect(facebook1.getOAuthAuthorizationURL("http://1-dot-shubexception.appspot.com/facebookPost"));
			}*/
	        
			//return null;
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

		public void fillAccessTokens(HttpServletResponse resp) {
			// TODO Auto-generated method stub
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			//TwitterTokens
			Query queryTwitter = new Query("TwitterAccessToken", datastoreKey);
		    List<Entity> entities = datastore.prepare(queryTwitter).asList(FetchOptions.Builder.withLimit(100));
		    if(entities.size() == 1) {
		    		Entity entity = entities.get(0);
		    		twitterAccessToken = new AccessToken(entity.getProperty("accessToken").toString(), entity.getProperty("accessTokenSecret").toString());
			} 
		    
		    //FacebookTokens
		    Query queryFacebook = new Query("FacebookAccessToken", datastoreKey);
		    List<Entity> entitiesFacebook = datastore.prepare(queryFacebook).asList(FetchOptions.Builder.withLimit(100));
		    if(entitiesFacebook.size() >= 1) {
		    		Entity entityFacebook = entitiesFacebook.get(0);

		    		/*Facebook facebook1 = new FacebookFactory().getInstance();
			    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
			        facebook1.setOAuthPermissions("public_profile,publish_actions");
			        facebook1.setOAuthCallbackURL("http://1-dot-shubexception.appspot.com/facebookConnect");*/
					
			        this.facebookCode = entityFacebook.getProperty("facebookAccessCode").toString();
					/*try {
						facebookAccessToken = facebook1.getOAuthAccessToken(entityFacebook.getProperty("facebookAccessCode").toString(),"http://1-dot-shubexception.appspot.com/facebookConnect");
					} catch (FacebookException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
			}
		    
		    //Google+ ID
			Query query = new Query("GooglePlusUserId", datastoreKey);
		    List<Entity> entitiesGooglePlus = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
		    System.out.println("g+++++");
		    if(entitiesGooglePlus.size() == 1) {
		    	System.out.println("getting it");
				this.googlePlusUserId = entitiesGooglePlus.get(0).getProperty("googlePlusUserId").toString();
			}
		}

		public void setBackgroundImage(String backgroundImage) {
			// TODO Auto-generated method stub
			this.backgroundImage = backgroundImage;
		}

		public void setFacebookCode(String code){
			this.facebookCode = code;
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Entity entity = null;
			if(facebookAccessToken != null) {
				Query query = new Query("FacebookAccessToken", datastoreKey);
			    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
			    if(entities.size() == 1) {
			    		entity = entities.get(0);
				} else {
					//ERROR
					return;
				}
			} else {
				entity = new Entity("FacebookAccessToken", datastoreKey);
			}
			entity.setProperty("facebookAccessCode", code);
			datastore.put(entity);
		}
		
		public void setFacebookToken(String code, String redirect, HttpServletResponse resp) throws IOException {
			// TODO Auto-generated method stub
			this.facebookCode = code;
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Entity entity = null;
			if(facebookAccessToken != null) {
				Query query = new Query("FacebookAccessToken", datastoreKey);
			    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
			    if(entities.size() == 1) {
			    		entity = entities.get(0);
				} else {
					//ERROR
					return;
				}
			} else {
				entity = new Entity("FacebookAccessToken", datastoreKey);
			}
			entity.setProperty("facebookAccessCode", code);
			datastore.put(entity);
			
			Facebook facebook1 = new FacebookFactory().getInstance();
	    	facebook1.setOAuthAppId("1487004968203759", "a93f6a442ad306cc5e73c4a0de47fe9e");
	        facebook1.setOAuthPermissions("public_profile,publish_actions");
	        String callBack = "http://1-dot-shubexception.appspot.com/" + redirect;
	        facebook1.setOAuthCallbackURL(callBack);
			
			
			try {
				facebookAccessToken = facebook1.getOAuthAccessToken(code);
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().println(e.toString());
				resp.sendRedirect(facebook1.getOAuthAuthorizationURL(callBack));
			}
			
			
		}
		
		public facebook4j.auth.AccessToken getFacebookAccessToken() {
			return facebookAccessToken;
		}
		
		public String getFacebookCode(){
			return facebookCode;			
		}

}
