package guestbook;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ShubUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6791653357365117438L;
	private String username;
	private String password;

	public ShubUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean changePassword(String curPassword, String newPassword, String confirmNewPassword) {
		if(arePasswordsUsable(curPassword, newPassword, confirmNewPassword)) {
			if(curPassword.equals(password) && 
				newPassword.equals(confirmNewPassword)) {
				password = confirmNewPassword;
				return true;
			}
		}
		return false;
	}
	
	private boolean arePasswordsUsable(String curPassword, String newPassword,
			String confirmNewPassword) {
		// TODO Auto-generated method stub
		boolean notNull = curPassword != null && newPassword != null && confirmNewPassword != null;
		boolean notEmpty = !(curPassword.equals("") || newPassword.equals("") || confirmNewPassword.equals(""));
		return notNull && notEmpty;
	}

	/**
	   * Always treat de-serialization as a full-blown constructor, by
	   * validating the final state of the de-serialized object.
	   */
	   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
	     //always perform the default de-serialization first
	     aInputStream.defaultReadObject();

	     //make defensive copy of the mutable Date field
//	     fDateOpened = new Date(fDateOpened.getTime());

	     //ensure that object state has not been corrupted or tampered with maliciously
//	     validateState();
	  }

	    /**
	    * This is the default implementation of writeObject.
	    * Customise if necessary.
	    */
	    private void writeObject(
	      ObjectOutputStream aOutputStream
	    ) throws IOException {
	      //perform the default serialization for all non-transient, non-static fields
	      aOutputStream.defaultWriteObject();
	    }
	
}
