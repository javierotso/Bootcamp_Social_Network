package es.imatia.socialnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
	private String userName;
	private HashMap<String, User> followedList;
	private List<Post> userPosts;

	public User(String userName) {
		this.setUserName(userName);
		followedList = new HashMap<String, User>();
		userPosts = new ArrayList<Post>();
	}

	/*
	 * Getters and setters
	 */

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public HashMap<String, User> getFollowedList() {
		return followedList;
	}

	public List<Post> getUserPosts() {
		return userPosts;
	}

	/*
	 * Methods
	 */
	public boolean addPost(Post newPost) {
		boolean added = false;
		if (this.getUserPosts().add(newPost)) {
			added = true;
		}
		return added;
	}

	public boolean unfollowUser(String user) {
		boolean unfollowed = false;
		if (this.getFollowedList().containsKey(user)) {
			this.getFollowedList().remove(user);
			unfollowed = true;
		}
		return unfollowed;
	}

	public boolean followUser(HashMap<String, User> userList, String userName) {
		boolean followed = false;
		if (!this.getFollowedList().containsKey(userName) && userList.containsKey(userName)) {
			this.getFollowedList().put(userName, userList.get(userName));
			followed = true;
		}
		return followed;
	}

	public boolean addUser(HashMap<String, User> userList) {
		boolean added = false;
		if (!userList.containsKey(this.getUserName())) {
			userList.put(userName, this);
			added = true;
		}
		return added;
	}
	
	public String showPostList() {
		String postListString = "";
		if(!this.getUserPosts().isEmpty()) {	
			List<Post> postList = this.getUserPosts();
			int postCount = 0;
			for(Post uniPost : postList) {
				postCount ++;
				postListString += ("\nPost número " + postCount + "\n\t");
				if(uniPost.getClass().equals(Text.class)) {
					postListString += ((Text) uniPost).getBody() + "\n"; 
					
				} else if(uniPost.getClass().equals(Picture.class)) {
					postListString += ((Picture) uniPost).getTitle();
				} else {
					postListString += ((Video) uniPost).getTitle();
				}
				postListString += uniPost.getPublishDate() + "\t" 
				+ uniPost.getCommentList().size() + " comentarios";
			}
		} else {
			
		}
		return postListString;
	}
}
