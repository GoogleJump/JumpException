package guestbook;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ChangePasswordServlet extends HttpServlet {
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
			ShubUser user = (ShubUser) req.getSession().getAttribute("user");
//			if(!req.equals(null)) {
//				System.out.println("as;dlkfja;sdlkfjaslkdf");
//			}
			req.getParameter("curPassword");
		  	String curPassword = req.getParameter("curPassword");
		  	String newPassword = req.getParameter("newPassword");
		  	String confirmNewPassword = req.getParameter("confirmNewPassword");
		  	if(user.changePassword(curPassword, newPassword, confirmNewPassword)) {
			  	changePasswordInDatastore(user, confirmNewPassword);
		  		resetPasswordFields(req, true);
		  		resp.sendRedirect("/settings.jsp");
		  	} else {
		  		resetPasswordFields(req, false);
		  	}
	  }

	private void changePasswordInDatastore(ShubUser user, String confirmNewPassword) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key signInKey = KeyFactory.createKey("SignIn", user.getUsername());
		Entity signIn = new Entity("Shub", signInKey);
		signIn.setProperty("user", user.getUsername());
		signIn.setProperty("password", confirmNewPassword);
		datastore.put(signIn);
	}

	private void resetPasswordFields(HttpServletRequest req, boolean hidePasswordFields) {
		// TODO Auto-generated method stub
		req.setAttribute("curPassword", "");
	  	req.setAttribute("newPassword", "");
	  	req.setAttribute("confirmNewPassword", "");
	  	if(hidePasswordFields) {
	  		
	  	}
	}
}