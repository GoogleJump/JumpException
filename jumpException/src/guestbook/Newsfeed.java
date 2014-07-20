package guestbook;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletResponse;

import twitter4j.auth.AccessToken;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class Newsfeed implements Serializable{ 
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	@Element(dependent="true")
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
	
	public Post getPost(String date) {
		for(Post post : posts) {
			if(post.getDate().toString().equals(date)) {
				return post;
			}
		}
		return null;
	}
	
	public boolean removePost(Post post, AccessToken twitterAccessToken) throws IOException {
		if(post.delete(twitterAccessToken)) {
			return posts.remove(post);
		} else {
			return false;
		}
	}

	public void setPosts(LinkedList<Post> linkedList) {
		// TODO Auto-generated method stub
		this.posts = linkedList;
	}

	public void delete() {
		while(posts.size() != 0) {
//			removePost(posts.get(0));
		}
	}
}
