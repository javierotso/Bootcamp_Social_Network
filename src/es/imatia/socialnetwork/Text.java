package es.imatia.socialnetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Text extends Post {
	private String body;

	public Text(String body) {
		super();
		this.setBody(body);
	}

	public Text(LocalDateTime publishDate, ArrayList<Comment> commentList, String body) {
		super(publishDate, commentList);
		this.setBody(body);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String toString() {
		String postString = "";
		postString += this.getBody() + "\n";
		postString += this.getPublishDate() + "\t" + this.getCommentList().size() + " comentarios\n";
		return postString;
	}

}
