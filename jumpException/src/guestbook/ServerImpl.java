//package guestbook;
//
//public class ServerImpl implements Server {
//
//	@Override
//	public void send(String message) {
//		postToFB(message);
//		//assuminb check for 160 character limit
//		tweetToTwitter(message);
//		
//	}
//
//	@Override
//	public void send(String message, int socialMediaId) {
//		// TODO Auto-generated method stub
//		switch(socialMediaId) {
//			case 1: postToFB(message);
//			case 2: postToTwitter(message);
//			default: System.out.println("Social Media Id with id " + socialMediaId + " is not available.");
//		}
//	}
//
//	@Override
//	public void edit(int tagId) {
//		// TODO Auto-generated method stub
//		//Need to think about if we have a main message as well as individual
//		//Class MediaPackage can contain information including main message and edited messages for each social media
//		MediaPackage media = getMediaPackage(tagId);
//		media.getFacebookPackage();
//	}
//
//	@Override
//	public String retrieveMessage() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void delete(int tagId) {
//		// TODO Auto-generated method stub
//		MediaPackage media = getMediaPackage(tagId);
//		FacebookPackage fb = media.getFacebookPackage();
//		TwitterPackage twitter = media.getTwitterPackage();
//		//deleting post must be deleted onsite and in storage
//		fb.deletePost();
//		twitter.deletePost();
//		storage.delete(tagId);
//	}
//
//	@Override
//	public void delete(int tagId, int socialMediaId) {
//		// TODO Auto-generated method stub
//		MediaPackage media = getMediaPackage(tagId);
//		switch(socialMediaId) {
//			case 1: media.getFacebookPackage().deletePost();
//			case 2: media.getTwitterPackage().deletePost();
//			default: throw;
//		}
//	}
//
//	@Override
//	public void logIn(String email, String password) {
//		//check to see if email is valid
//		if(valid) {
//			//get password and check if they are the same.
//			if(same) {
//				//switch screens and obtain user informatino for page
//			} else {
//				//say wrong username or password
//			}
//		} else {
//			//say incorrect user name or password
//		}
//	}
//
//	@Override
//	public void logout() { 
//		// TODO Auto-generated method stub
//		//switch to home screen with no more access to user info
//	}
//
//	@Override
//	public void changePassword(String newPassword, String oldPassword) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addAccount() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
