package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Post {
	protected LocalDateTime publishDate;
	protected ArrayList<Comment> commentList;

	public Post() {
		this.setPublishDate(LocalDateTime.now());
		this.commentList = new ArrayList<Comment>();
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

}
