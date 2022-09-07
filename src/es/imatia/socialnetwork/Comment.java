package es.imatia.socialnetwork;

import java.time.LocalDateTime;

public class Comment {
	private LocalDateTime commentPublishDate;
	private User commentOwner;
	private String commentBody;
	
	public Comment(String commentBody, LocalDateTime commentPublishDate, User commentOwner) {
		this.setCommentBody(commentBody);
		this.setCommentOwner(commentOwner);
		this.setCommentPublishDate(commentPublishDate);
	}
	
	public String getCommentBody() {
		return commentBody;
	}
	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}
	public LocalDateTime getCommentPublishDate() {
		return commentPublishDate;
	}
	public void setCommentPublishDate(LocalDateTime commentPublishDate) {
		this.commentPublishDate = commentPublishDate;
	}
	public User getCommentOwner() {
		return commentOwner;
	}
	public void setCommentOwner(User commentOwner) {
		this.commentOwner = commentOwner;
	}
	
	
}
