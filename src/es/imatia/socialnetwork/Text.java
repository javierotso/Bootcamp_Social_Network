package es.imatia.socialnetwork;

public class Text extends Post {
	private String body;
	
	public Text (String body) {
		super();
		this.setBody(body);
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
