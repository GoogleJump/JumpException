package guestbook;

public class FacebookPackage {
	String message;
	boolean setToPost; //this will be a check mark if it will be posted to the site or not
	
	//by default setToPost is set to true
	public FacebookPackage(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		this.setToPost = true;
	}
	
	String getMessage() {
		return message;
	}
	
	boolean getSetToPost() {
		return setToPost;
	}
	
	void setMessage(String str) {
		message = str;
	}
	
	void setSetToPost(boolean bool) {
		setToPost = bool;
	}
}
