package es.imatia.socialnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

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

	public boolean deletePost(int indexPost) {
		boolean deleted = false;
		try {
			this.getUserPosts().remove(indexPost - 1);
			deleted = true;
		} catch (Exception e) {
			deleted = false;
		}
		return deleted;
	}

	public boolean addUser(HashMap<String, User> userList) {
		boolean added = false;
		if (!userList.containsKey(this.getUserName())) {
			userList.put(userName, this);
			added = true;
		}
		return added;
	}

	public boolean deleteUser(HashMap<String, User> userList) {
		boolean deleted = false;
		if (userList.size() > 1) {
			// Si hay más de un usuario(es decir, el usuario a eliminar no es el único
			// existente, recorremos la lista
			for (User user : userList.values()) {
				// Si el usuario tiene post y no es el que vamos a eliminar, recorremos la lista
				// de posts,
				// para buscar comentarios a eliminar
				if (!user.getUserPosts().isEmpty() && !this.equals(user)) {
					for (Post post : user.getUserPosts()) {
						// Finalmente, si el post tiene comentarios, recorremos la lista buscando alguno
						// del usuario actual para eliminarlos
						if(!post.getCommentList().isEmpty()) {
							List commentList = post.getCommentList();
							for(int i = 0; i < commentList.size(); i++) {
								if(((Comment) commentList.get(i)).getCommentOwner().equals(this)) {
									commentList.remove(i);
								}
							}
						}
					}
				}
				//Ahora comprobamos si este usuario sigue al usuario a eliminar, para eliminarlo de la lista.
				if(user.getFollowedList().containsKey(this.getUserName())) {
					user.getFollowedList().remove(this.getUserName());
				}
			}
			// Después de eliminar todos los comentarios, eliminamos al usuario3
			userList.remove(this.getUserName());
			deleted = true;
		}
		return deleted;
	}

	public String showUserCommentList(HashMap<String, User> userList) {
		String stringCommentList = "";
		int commentCount = 0;
		for (User user : userList.values()) {
			for (Post post : user.getUserPosts()) {
				for (Comment comment : post.getCommentList()) {
					if (comment.getCommentOwner().equals(this)) {
						commentCount++;
						stringCommentList += "\nComentario " + commentCount + "\n\t" + comment.getCommentBody() + "\n";
					}
				}
			}
		}
		if (stringCommentList.isEmpty()) {
			stringCommentList = "\nTodavía no has publicado ningún comentario\n";
		}
		return stringCommentList;
	}

	public String showPostList() {
		String postListString = "";
		if (!this.getUserPosts().isEmpty()) {
			List<Post> postList = this.getUserPosts();
			int postCount = 0;
			for (Post uniPost : postList) {
				postCount++;
				postListString += ("\nPost número " + postCount + "\n\t");
				if (uniPost.getClass().equals(Text.class)) {
					postListString += ((Text) uniPost).getBody() + "\n";

				} else if (uniPost.getClass().equals(Image.class)) {
					postListString += ((Image) uniPost).getTitle() + "\n";
				} else {
					postListString += ((Video) uniPost).getTitle() + "\n";
				}
				postListString += uniPost.getPublishDate() + "\t" + uniPost.getCommentList().size() + " comentarios";
			}
		} else {
			System.out.print("\nNo tienes ningún post todavía\n");
		}
		return postListString;
	}
}
