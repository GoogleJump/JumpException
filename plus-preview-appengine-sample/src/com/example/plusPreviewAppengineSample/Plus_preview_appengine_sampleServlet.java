package com.example.plusPreviewAppengineSample;
import java.io.IOException;
import javax.servlet.http.*;

public class Plus_preview_appengine_sampleServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
