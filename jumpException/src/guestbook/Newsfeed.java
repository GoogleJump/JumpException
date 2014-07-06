package guestbook;

import java.io.Serializable;
import java.util.LinkedList;

public class Newsfeed implements Serializable {
	private LinkedList<Post> posts;
	
	public Newsfeed() {
		posts = new LinkedList<Post>();
	}
	
	public Newsfeed(LinkedList<Post> posts) {
		this.posts = posts;
	}
	
	public void addFirst(Post post) {
		posts.addFirst(post);
	}
	
	public LinkedList<Post> getAllPosts() {
		return posts;
	}
	
	public LinkedList<Post> getPosts(String filter) {
		if(filter == null || filter.equals("")) {
			return posts;
		}
		LinkedList<Post> filteredPosts = new LinkedList<Post>();
		for(Post post : posts) {
			if(post.contains(filter)) {
				filteredPosts.addFirst(post);
			}
		}
		return filteredPosts;
	}
}
