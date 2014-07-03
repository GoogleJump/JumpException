package guestbook;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class PostServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
	    Date date = new Date();
	    ShubUser user = (ShubUser) req.getSession().getAttribute("user");
	    String overallText = req.getParameter("overallText");
	    System.out.println("TESTING " + overallText);
	    String fbText = req.getParameter("fbText");
	    System.out.println("TESTING " + fbText);
	    String twitterText = req.getParameter("twitterText");
	    Entity post = new Entity("Post", user.getKey());
	    overallText = voidOverallChecking(overallText);
	    fbText = voidFacebookChecking(fbText, overallText, req);
	    twitterText = voidTwitterChecking(twitterText, overallText, req);
	    post.setProperty("date", date);
	    post.setProperty("overallPost", overallText);
	    post.setProperty("fbPost", fbText);
	    post.setProperty("twitterPost", twitterText);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(post);
	    user.getNewsfeed().add(new Post(date, overallText, fbText, twitterText));
	    try {
			resp.sendRedirect("/signedIn.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		if(textObj == null) {
	    	return "";
	    }
		
		boolean isFbCheckboxChecked = req.getParameter("fbCheckbox") != null;
	    if(!isFbCheckboxChecked) {
	    	textObj = overallText;
	    }
		
		return textObj.toString();
	}
	
	private String voidTwitterChecking(Object textObj, Object overallText, HttpServletRequest req) {
		if(textObj == null) {
	    	return "";
	    }
		
		boolean isTwitterCheckboxChecked = req.getParameter("twitterCheckbox") != null;
	    if(!isTwitterCheckboxChecked) {
	    	textObj = overallText;
	    }
		
		return textObj.toString();
	}
}
