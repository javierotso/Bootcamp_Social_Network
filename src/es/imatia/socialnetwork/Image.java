package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Image extends Post implements Comparable <Post>{

	private int DEFAULT_VALUE = 5;

	private String title;
	private int height;
	private int width;

	public Image(String title, int height, int width) {
		super();
		this.setTitle(title);
		this.setHeight(height);
		this.setWidth(width);
	}

	public Image(LocalDateTime publishDate, ArrayList<Comment> commentList, String title, int height, int width) {
		super(publishDate, commentList);
		this.setTitle(title);
		this.setHeight(height);
		this.setWidth(width);
	}

	/*
	 * Getters and Setters
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > 0) {
			this.height = height;
		} else {
			this.height = DEFAULT_VALUE;
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(float width) {
		if (height > 0) {
			this.width = height;
		} else {
			this.width = DEFAULT_VALUE;
		}
	}

	@Override
	public String toString() {
		String postString = "";
		postString += "\t" + this.getTitle() + "\n";
		postString += this.getPublishDate() + "\t" + this.getCommentList().size() + " comentarios\n";
		return postString;
	}
}
