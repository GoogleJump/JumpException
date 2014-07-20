package guestbook;

public class MediaPackage {
	private TwitterPackage twitterPackage;
	private FacebookPackage facebookPackage;
	
	public MediaPackage(TwitterPackage twitterPackage, FacebookPackage facebookPackage) {
		this.twitterPackage = twitterPackage;
		this.facebookPackage = facebookPackage;
	}
	
	FacebookPackage getFacebookPackage() {
		return facebookPackage;
	}
	
	TwitterPackage getTwitterPackage() {
		return twitterPackage;
	}
	
}
