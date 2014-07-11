package guestbook;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public class DeletePostServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
		user.deletePost(req, resp, null);
//		String date = req.getParameter("hiddenDate").toString();
//		ShubUser user = (ShubUser) req.getSession().getAttribute("user");
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		Post post = user.getNewsfeed().getPost(date);
//		Query query = new Query("Post", user.getKey()).addSort("date", Query.SortDirection.ASCENDING);
//	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
//	    for(Entity entity : entities) {
//	    	if(entity.getProperty("date").equals(post.getDate())) {
//	    		datastore.delete(entity.getKey());
//	    		user.getNewsfeed().removePost(post);
//	    		req.getSession().setAttribute("user", user);
//	    	}
//		}
//		resp.sendRedirect("/signedIn.jsp");
	}
	
	
}
