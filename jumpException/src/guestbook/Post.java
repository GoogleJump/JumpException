package guestbook;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
	private Date date;
	private String overallText;
	private String fbText;
	private String twitterPost;
	
	public Post(Date date, String overallText, String fbText, String twitterText) {
		this.date = date;
		this.overallText = overallText;
		this.fbText = fbText;
		this.twitterPost = twitterText;
	}
	
	public String getPost(String socialMedia) {
		switch(socialMedia) {
			case "date" : return date.toString();
			case "overall" : return overallText;
			case "facebook" : return fbText;
			case "twitter" : return twitterPost;
			default : return "ERROR";//error
		}
	}

	//THIS METHOD IS NOT CAP SENSITIVE
	public boolean contains(String filter) {
		// TODO Auto-generated method stub
		filter = filter.toLowerCase();
		return fbText.toLowerCase().contains(filter) || twitterPost.toLowerCase().contains(filter) || overallText.toLowerCase().contains(filter);
	}
	
	public Date getDate() {
		return date;
	}
}
