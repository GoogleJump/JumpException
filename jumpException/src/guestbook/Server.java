package guestbook;

public interface Server {
	
	
	void send(String message);
	
	//socialMediaId can be an enum instead
	void send(String message, int socialMediaId);
	
	void edit(int tagId);
	
	//can return a Message object instead
	String retrieveMessage();
	
	//use different data type other than int since many tagIds
	void delete(int tagId);
	
	void delete(int tagId, int socialMediaId);
	
	void logIn(String email, String password);
	
	void logout();
	
	void changePassword(String newPassword, String oldPassword);
	
	//decide on later
	void addAccount();
}
