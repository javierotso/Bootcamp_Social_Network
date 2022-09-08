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
						if (!post.getCommentList().isEmpty()) {
							List<Comment> commentList = post.getCommentList();
							for (int i = 0; i < commentList.size(); i++) {
								if (commentList.get(i).getCommentOwner().equals(this)) {
									commentList.remove(i);
								}
							}
						}
					}
				}
				// Ahora comprobamos si este usuario sigue al usuario a eliminar, para
				// eliminarlo de la lista.
				if (user.getFollowedList().containsKey(this.getUserName())) {
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
				boolean showedPost = false;
				for (Comment comment : post.getCommentList()) {
					if (comment.getCommentOwner().equals(this) || this.equals(user)) {
						if (!showedPost) {
							stringCommentList += "\nPOST\n" + post.toString();
							showedPost = true;
						}
						commentCount++;
						stringCommentList += "\nComentario " + commentCount + "\t"
								+ comment.getCommentOwner().getUserName() + "\n\t" + comment.getCommentBody() + "\n";
					}
				}
			}
		}
		if (stringCommentList.isEmpty()) {
			stringCommentList = "\nTodavía no has publicado ningún comentario\n";
		}
		return stringCommentList;
	}

	public boolean deleteComment(HashMap<String, User> userList, Comment commentToDelete) {
		boolean deleted = false;
		for (User user : userList.values()) {
			if (!user.getUserPosts().isEmpty()) {
				for (Post post : user.getUserPosts()) {
					if (!post.getCommentList().isEmpty()) {
						List<Comment> commentList = post.getCommentList();
						for (int i = 0; i < commentList.size() && !deleted; i++) {
							Comment comment = commentList.get(i);
							if (comment.equals(commentToDelete)) {
								deleted = post.deleteComment(comment);
							}
						}
					}
				}
			}
		}
		return deleted;
	}

	public String showOwnPostList() {
		String postListString = "";
		if (!this.getUserPosts().isEmpty()) {
			List<Post> postList = this.getUserPosts();
			int postCount = 0;
			for (Post uniPost : postList) {
				postCount++;
				postListString += ("\nPost número " + postCount + "\n\t");
				postListString += uniPost.toString();
			}
		} else {
			System.out.print("\nNo tienes ningún post todavía\n");
		}
		return postListString;
	}

	public String showFollowedPostList() {
		String followedPostList = "";
		int postCount = 0;
		if (!this.getFollowedList().isEmpty()) {
			for (User user : this.getFollowedList().values()) {
				if (!user.getUserPosts().isEmpty()) {
					for (Post post : user.getUserPosts()) {
						postCount += 1;
						followedPostList += ("\nPost número " + postCount + "\t" + user.getUserName() + "\n\t");
						followedPostList += post.toString();
					}
				}
			}
		}
		if (!this.getUserPosts().isEmpty()) {
			for (Post post : this.getUserPosts()) {
				postCount++;
				followedPostList += ("\nPost número " + postCount + "\tde\t" + this.getUserName() + "\n\t");
				followedPostList += post.toString();
			}
		}
		if (followedPostList.isEmpty()) {
			followedPostList = "\nTodavía no hay posts.\n";
		}
		return followedPostList;
	}

	/**
	 * 
	 * @return HashMap with user's post and all followed users posts
	 */
	public HashMap<Integer, Post> getFollowedPostList() {
		HashMap<Integer, Post> followedPostList = new HashMap<>();
		Integer postCount = 0;
		if (!this.getFollowedList().isEmpty()) {
			for (User user : this.getFollowedList().values()) {
				if (!user.getUserPosts().isEmpty()) {
					for (Post post : user.getUserPosts()) {
						postCount++;
						followedPostList.put(postCount, post);
					}
				}
			}
			if (!this.getUserPosts().isEmpty()) {
				for (Post post : this.getUserPosts()) {
					postCount++;
					followedPostList.put(postCount, post);
				}
			}
		}
		return followedPostList;
	}

	public HashMap<Integer, Comment> getUserCommentList(HashMap<String, User> userList) {
		HashMap<Integer, Comment> commentList = new HashMap<>();
		Integer commentCount = 0;
		for (User user : userList.values()) {
			if (!user.getUserPosts().isEmpty()) {
				for (Post post : user.getUserPosts()) {
					if (!post.getCommentList().isEmpty()) {
						for (Comment comment : post.getCommentList()) {
							if (comment.getCommentOwner().equals(this) || this.equals(user)) {
								commentCount += 1;
								commentList.put(commentCount, comment);
							}
						}
					}
				}
			}
		}

		return commentList;
	}

	public String showWall() {
		String wall = "";
		/*
		 * Convertimos Array de object a array de post
		 */
		if (!this.getFollowedPostList().isEmpty()) {
			wall += "\n####################################\n\tMURO DE " + this.getUserName().toUpperCase()
					+ "\t\n####################################\n";
			Object[] auxPostList = this.getFollowedPostList().values().toArray();
			List<Post> postList = new ArrayList<>();
			for (int i = 0; i < auxPostList.length; i++) {
				postList.add((Post) auxPostList[i]);
			}
			/*
			 * Lo ordenamos
			 */
			postList.sort(null);
			// Creamos el String
			for (int i = 0; i < 10 && i < postList.size(); i++) {
				wall += "\n" + postList.get(i).toString();
			}
		} else {
			wall = "\nTus contactos todavía no han publicado nada\n";
		}
		return wall;
	}

	public String friendsSuggestion(HashMap<String, User> userList) {
		String friends = "\n______Sugerencias de amistad_____\n";
		List<User> suggestions = new ArrayList<>();

		for (User user : userList.values()) {
			for (User followed : user.getFollowedList().values()) {
				if (this.getFollowedList().containsValue(followed) && !this.getFollowedList().containsValue(user)
						&& !suggestions.contains(user) && !this.equals(user)) {
					suggestions.add(user);
				}
			}
		}

		if (!suggestions.isEmpty()) {
			friends = "\n______Sugerencias de amistad_____\n";
			for (User user : suggestions) {
				friends += "\n" + user.getUserName();
			}
		} else {
			friends = "\nTodavía no hay amigos para sugerir\n";
		}
		return friends;
	}
}
