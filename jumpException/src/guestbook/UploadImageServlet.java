package guestbook;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import facebook4j.Media;

public class UploadImageServlet extends HttpServlet {
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*try {
			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/plain");
	        
			ShubUser user = (ShubUser) req.getSession().getAttribute("user");
			Twitter twitter = new TwitterFactory().getInstance();
			Status status = null;

			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				int len;
				byte[] buffer = new byte[8192];
				while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
					resp.getOutputStream().write(buffer, 0, len);
				}
			}
		} catch (Exception ex) {}*/
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("myPhoto");
        req.getSession().setAttribute("blobKey", blobKey);

        resp.sendRedirect("/signedIn.jsp");
	}
}
