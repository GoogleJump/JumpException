package guestbook;

import java.io.Serializable;
import java.util.ArrayList;

public class Newsfeed implements Serializable {
	private ArrayList<Post> posts;
	
	public Newsfeed() {
		posts = new ArrayList<Post>();
	}
	
	public void add(Post post) {
		posts.add(post);
	}
	
	public ArrayList<Post> getAllPosts() {
		return posts;
	}
	
	public ArrayList<Post> getFilteredPosts(String filter) {
		if(filter == null || filter.equals("")) {
			return posts;
		}
		ArrayList<Post> filteredPosts = new ArrayList<Post>();
		for(Post post : posts) {
			if(post.contains(filter)) {
				filteredPosts.add(post);
			}
		}
		return filteredPosts;
	}
}
