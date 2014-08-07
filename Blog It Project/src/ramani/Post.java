package ramani;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class Post {
	
	String postId;
	String body;
	String author;
	Date createdOn;

	public Post(String body) {
		this.body = body;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public String getAuthor(){
		return author;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd MMM");

		stringBuilder.append("{")
			.append("'id' : '").append(postId).append("',")
			.append("'year' : '").append(yearFormat.format(createdOn)).append("',")
			.append("'dayMonth' : '").append(dayMonthFormat.format(createdOn)).append("',")
			.append("'body' : '").append(body).append("'}");

		return stringBuilder.toString();
	}
}
