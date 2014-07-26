package guestbook;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class UploadImageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		File file = new File(req.getParameter("myPhoto"));
		System.out.println("The file path is " + file.getAbsolutePath());
		try {
	        ServletFileUpload upload = new ServletFileUpload();
	        resp.setContentType("text/plain");

	        FileItemIterator iterator = upload.getItemIterator(req);
	        while (iterator.hasNext()) {
	          FileItemStream item = iterator.next();
	          InputStream stream = item.openStream();
	          
	          if (item.isFormField()) {
	            System.out.println("Got a form field: " + item.getFieldName() + item.getName());
	          } else {
	            System.out.println("Got an uploaded file: " + item.getFieldName() +
	                        ", name = " + item.getName());

	            // You now have the filename (item.getName() and the
	            // contents (which you can read from stream). Here we just
	            // print them back out to the servlet output stream, but you
	            // will probably want to do something more interesting (for
	            // example, wrap them in a Blob and commit them to the
	            // datastore).
	            int len;
	            byte[] buffer = new byte[8192];
	            while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
//	            	resp.getOutputStream().write(buffer, 0, len);
	            }
	            Blob blob = new Blob(buffer);
	            req.getSession().setAttribute("blob", blob);
//	            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	 
//	            Entity entity = new Entity("image", key);
//	            entity.setProperty("blob", blob);
//	            entity.setProperty("file", file);
	          }
	        }
	      } catch (Exception ex) {
//	        throw new ServletException(ex);
	      }
		
		resp.sendRedirect("/signedIn.jsp");
		// TODO Auto-generated method stub
		// Create a factory for disk-based file items
//	    DiskFileItemFactory factory = new DiskFileItemFactory();
//
//	    // Configure a repository (to ensure a secure temp location is used)
//	    ServletContext servletContext = this.getServletConfig().getServletContext();
//	    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
//	    factory.setRepository(repository);
//
//	    // Create a new file upload handler
//	    ServletFileUpload upload = new ServletFileUpload(factory);
//
//	    // Parse the request
//	    try {
//			List<FileItem> items = upload.parseRequest(req);
//		} catch (FileUploadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
//	    ServletFileUpload upload = new ServletFileUpload();
//	    FileItemIterator iter;
//	    FileItemStream imageItem = null;
//		try {
//			iter = upload.getItemIterator(req);
//		    imageItem = iter.next();
//
//		} catch (FileUploadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    InputStream imgStream = imageItem.openStream();
//	    Blob imageBlob = new Blob(IOUtils.toByteArray(imgStream));
//	    InputStream is = new ByteArrayInputStream(imageBlob.getBytes());
//	    File imageFile = new File(pathname);
//	    
//	    FileItemFactory factory = new DiskFileItemFactory();
//
//	 // Set factory constraints
//	 // factory.setSizeThreshold(yourMaxMemorySize);
//	 // factory.setRepository(yourTempDirectory);
//
//	 // Create a new file upload handler
//	 ServletFileUpload upload = new ServletFileUpload( factory );
//	 // upload.setSizeMax(yourMaxRequestSize);
//
//	 // Parse the request
//	 List<FileItem> uploadItems = new LinkedList<FileItem>();
//	try {
//		uploadItems = upload.parseRequest( req );
//	} catch (FileUploadException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//	 for( FileItem uploadItem : uploadItems )
//	 {
//	   if( uploadItem.isFormField() )
//	   {
//	     String fieldName = uploadItem.getFieldName();
//	     String value = uploadItem.getString();
//	   }
//	 }
	    
	    
	    
//	    try {
//	        ServletFileUpload upload = new ServletFileUpload();
//	        resp.setContentType("text/plain");
//
//	        FileItemIterator iterator = upload.getItemIterator(req);
//	        while (iterator.hasNext()) {
//	          FileItemStream item = iterator.next();
//	          InputStream stream = item.openStream();
//
//	          if (item.isFormField()) {
//	            System.out.println("Got a form field: " + item.getFieldName() + item.getName());
//	          } else {
//	            System.out.println("Got an uploaded file: " + item.getFieldName() +
//	                        ", name = " + item.getName());
//
//	            // You now have the filename (item.getName() and the
//	            // contents (which you can read from stream). Here we just
//	            // print them back out to the servlet output stream, but you
//	            // will probably want to do something more interesting (for
//	            // example, wrap them in a Blob and commit them to the
//	            // datastore).
//	            int len;
//	            byte[] buffer = new byte[8192];
//	            while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
////	            	resp.getOutputStream().write(buffer, 0, len);
//	            }
//	          }
//	        }
//	      } catch (Exception ex) {
////	        throw new ServletException(ex);
//	      }
	    
//	    try {
//			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
//			for(FileItem item : items) {
//				System.out.println("THERE IS A FILEITEM");
//			}
//		} catch (FileUploadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
