package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Post implements Comparable<Post>{
	protected LocalDateTime publishDate;
	protected ArrayList<Comment> commentList;

	public Post() {
		this.setPublishDate(LocalDateTime.now());
		this.commentList = new ArrayList<Comment>();
	}

	public Post(LocalDateTime publishDate, ArrayList<Comment> commentList) {
		this.setPublishDate(publishDate);
		this.commentList = commentList;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
	}

	public ArrayList<Comment> getCommentList() {
		return commentList;
	}

	public boolean addComment(Comment comment) {
		boolean added = false;
		this.getCommentList().add(comment);
		return added;
	}

	public boolean publishComment(String body, User owner) {
		boolean added = false;
		if (this.getCommentList().add(new Comment(body, LocalDateTime.now(), owner))) {
			added = true;
		}
		return added;
	}

	public boolean deleteComment(Comment comment) {
		boolean deleted = false;
		if (this.getCommentList().remove(comment)) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public String toString() {
		String postString = "";
		if (this.getClass().equals(Text.class)) {
			postString = "\t" + ((Text) this).toString();
		} else if (this.getClass().equals(Video.class)) {
			postString = "\t" +((Video) this).toString();
		} else if (this.getClass().equals(Image.class)) {
			postString = "\t" +((Image) this).toString();
		}
		return postString;
	}
	
	@Override
	public int compareTo(Post p) {
		return (p.getPublishDate().compareTo(this.getPublishDate()));
	}

}
