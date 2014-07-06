package guestbook;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

public class ShubUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6791653357365117438L;
	private String username;
	private String password;
	private Key datastoreKey;
	private Newsfeed newsfeed;

	public ShubUser(String username, String password, Key datastoreKey, Newsfeed newsfeed) {
		this.username = username;
		this.password = password;
		this.datastoreKey = datastoreKey;
		this.newsfeed = newsfeed;
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
	    	String overallText = voidChecking(overallObj);
	    	String fbText = voidChecking(fbObj);
	    	String twitterText = voidChecking(twitterObj);
	    	System.out.println("APP IDS" + entity.getParent());
	    	newsfeed.addFirst(new Post(date, overallText.toString(), fbText.toString(), twitterText.toString()));
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
	
}
