package guestbook;

public class TwitterPackage {
	String message;
	boolean setToPost; //this will be a check mark if it will be posted to the site or not
	
	//by default setToPost is set to true
	public TwitterPackage(String message) {
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
		if(str.length() <= 160) {
			message = str;	
		} else {//this will probably be in API
			throw new IllegalArgumentException("Twitter message must be less than 160 characters");
		}
	}
	
	void setSetToPost(boolean bool) {
		setToPost = bool;
	}
}
