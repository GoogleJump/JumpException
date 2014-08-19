package guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;

public class RouteEditServlets extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		JspFactory factory = JspFactory.getDefaultFactory();
//		PageContext pageContext = factory.getPageContext(this,  req, resp, null, true, JspWriter.DEFAULT_BUFFER, true);
//		System.out.println("the edited stuff is " + pageContext.getAttribute("editedTwitterPost") + pageContext.getAttribute("editedFbPost"));
		String action = req.getParameter("action");
		
		if("Post".equals(action)) {
			saveEditPost(req, resp);
		} else if("Cancel".equals(action)) {
			cancelEditPost(req, resp);
		} else { //error
			resp.sendRedirect("/signedIn.jsp");
		}
		
	}
	
	private void saveEditPost(HttpServletRequest req, HttpServletResponse resp) {
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");

		String overallText = voidOverallChecking("");
	    String fbText = voidFacebookChecking(req.getParameter("fbEditText"), overallText, req);
	    String twitterText = voidTwitterChecking(req.getParameter("twitterEditText"), overallText, req);
	    System.out.println(fbText + " twitter's is " + twitterText);
	    req.getSession().removeAttribute("fbEditText");
	    req.getSession().removeAttribute("twitterEditText");
	    Blob blob = (Blob) req.getSession().getAttribute("curBlob");
	    
	    String date = req.getParameter("hiddenDate").toString();
	    Post post = user.getNewsfeed().getPost(date);
	    
	    BlobKey key = post.getPicture();
	    req.getSession().setAttribute("blobKey",key);
	    req.getSession().setAttribute("editImageURL", post.getBlobURL());
		req.getSession().setAttribute("deleteDate", date);
	    
	    if(blob != null && req.getParameter("myPhoto") != null) {
//	    	user.postWithMedia(overallText, fbText, twitterText, req, resp);
	    } else {
		    try {
				user.post(overallText, fbText, twitterText, req, resp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    //Delete the previous post
	   
//		Post post = user.getNewsfeed().getPost(date);
//		try {
//			user.deletePost(req, resp, post);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private String voidOverallChecking(Object textObj) {
		if(textObj == null) {
	    	return "";
	    }
		return textObj.toString();
	}
	
	private String voidFacebookChecking(Object textObj, Object overallText, HttpServletRequest req) {
		System.out.println("FACEBOOK CHECKBOX " + req.getParameter("fbEditCheckbox"));
		boolean isFbCheckboxChecked = req.getParameter("fbEditCheckbox") != null;
	    if(!isFbCheckboxChecked) {
	    	textObj = overallText;
	    } else if(textObj == null){
	    	return "";
	    }
		return textObj.toString();
	}
	
	private String voidTwitterChecking(Object textObj, Object overallText, HttpServletRequest req) {
		System.out.println("TWITTER CHECKBOX " + req.getParameter("twitterEditCheckbox"));
//		if(twitterPostId != -1) { 
			boolean isTwitterCheckboxChecked = req.getParameter("twitterEditCheckbox") != null;
		    if(!isTwitterCheckboxChecked) {
		    	textObj = overallText;
		    } else if(textObj == null){
		    	return "";
		    }
//		} else { //no post to twitter
//			return "";
//		}
			
		return textObj.toString();
	}
	
	private void cancelEditPost(HttpServletRequest req, HttpServletResponse resp) {
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		String date = req.getParameter("hiddenDate").toString();
		Post post = user.getNewsfeed().getPost(date);
		post.setIsEditing(false);
		req.getSession().removeAttribute("deleteDate");
		req.getSession().setAttribute("user", user);
		try {
			resp.sendRedirect("/signedIn.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
