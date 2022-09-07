package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Video extends Post{
	private final int DEFAULT_QUALITY = 1080;
	private final int DEFAULT_DURATION = 60;
	
	private int duration;
	private int quality;
	private String title;
	
	public Video(LocalDateTime publishDate, ArrayList<Comment> commentList,int duration, int quality, String title) {
		super(publishDate, commentList);
		this.setDuration(duration);
		this.setQuality(quality);
		this.setTitle(title);
	}
	
	
	public Video(int duration, int quality, String title) {
		super();
		this.setDuration(duration);
		this.setQuality(quality);
		this.setTitle(title);
	}
	/*
	 * Getters and Setters
	 */
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		if(duration < 0) {
			this.duration = DEFAULT_DURATION;
		} else {
			this.duration = duration;
		}
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		if(quality < 0) {
			this.quality = DEFAULT_QUALITY;
		} else {
			this.quality = quality;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
