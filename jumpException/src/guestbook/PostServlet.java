package guestbook;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.appengine.api.datastore.Blob;

import facebook4j.Media;

public class PostServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String overallText = voidOverallChecking(req.getParameter("overallText"));
	    String fbText = voidFacebookChecking(req.getParameter("fbText"), overallText, req);
	    String twitterText = voidTwitterChecking(req.getParameter("twitterText"), overallText, req);
	    
	    System.out.println("heloooooo " + fbText + twitterText + overallText);
	    ShubUser user = (ShubUser) req.getSession().getAttribute("user");
	    //Blob blob = (Blob) req.getSession().getAttribute("curBlob");
	   // if(blob != null && req.getParameter("myPhoto") != null) {
//	    	user.postWithMedia(overallText, fbText, twitterText, req, resp);
	   // } else {
	//	    user.post(overallText, fbText, twitterText, req, resp);
	   // }
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
//		if(twitterPostId != -1) { 
			boolean isTwitterCheckboxChecked = req.getParameter("twitterCheckbox") != null;
		    if(!isTwitterCheckboxChecked) {
		    	textObj = overallText;
		    } else if(textObj == null){
		    	return "";
//		    }
		} else { //no post to twitter
			return "";
		}
			
		return textObj.toString();
	}
	 
	    
	    
	    
//	    Date date = new Date();
//	    ShubUser user = (ShubUser) req.getSession().getAttribute("user");
//	    String overallText = req.getParameter("overallText");
//	   
//	    String fbText = req.getParameter("fbText");
//
//	    String twitterText = req.getParameter("twitterText");
//	    
//	    //NEW WAY
////	    overallText = voidOverallChecking(overallText);
////	    fbText = voidFacebookChecking(fbText, overallText, req);
////	    twitterText = voidTwitterChecking(twitterText, overallText, req);
////	    Post post = new Post(date, overallText, fbText, twitterText);
////	    user.getNewsfeed().addFirst(post);
////	    PersistenceManager pm = PMF.get().getPersistenceManager();
////	    try {
////	    	pm.makePersistent(user);
////	    } finally {
////	    	pm.close();
////	    }
////	    
////	    try {
////			resp.sendRedirect("/signedIn.jsp");
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	    
//	    //OLD WAY
//	    Entity post = new Entity("Post", user.getKey());
//	    overallText = voidOverallChecking(overallText);
//	    fbText = voidFacebookChecking(fbText, overallText, req);
//	    twitterText = voidTwitterChecking(twitterText, overallText, req);
////	    System.out.println("OVERALL " + overallText);
////	    System.out.println("FB " + fbText);
////	    System.out.println("TWITTER " + twitterText);
//	    post.setProperty("date", date);
//	    post.setProperty("overallPost", overallText);
//	    post.setProperty("fbPost", fbText);
//	    post.setProperty("twitterPost", twitterText);
//
//	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//	    datastore.put(post);
//	    user.getNewsfeed().addFirst(new Post(date, overallText, fbText, twitterText, post.getKey()));
//		req.getSession().setAttribute("user", user);
//	    try {
//			resp.sendRedirect("/signedIn.jsp");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	
	
//	private String voidOverallChecking(Object textObj) {
//		if(textObj == null) {
//	    	return "";
//	    }
//		return textObj.toString();
//	}
//	
//	private String voidFacebookChecking(Object textObj, Object overallText, HttpServletRequest req) {
////		String facebookString = "";
////		if(textObj == null) {
////	    	return "";
////	    }
//		
//		
//		System.out.println("FACEBOOK CHECKBOX " + req.getParameter("fbCheckbox"));
//		boolean isFbCheckboxChecked = req.getParameter("fbCheckbox") != null;
//	    if(!isFbCheckboxChecked) {
//	    	textObj = overallText;
//	    } else if(textObj == null){
//	    	return "";
//	    }
//		return textObj.toString();
//	}
//	
//	private String voidTwitterChecking(Object textObj, Object overallText, HttpServletRequest req) {
//
////		if(textObj == null) {
////	    	return "";
////	    }
//		
//		System.out.println("TWITTER CHECKBOX " + req.getParameter("twitterCheckbox"));
//		boolean isTwitterCheckboxChecked = req.getParameter("twitterCheckbox") != null;
//	    if(!isTwitterCheckboxChecked) {
//	    	textObj = overallText;
//	    } else if(textObj == null){
//	    	return "";
//	    }
//		
//		return textObj.toString();
//	}
}
