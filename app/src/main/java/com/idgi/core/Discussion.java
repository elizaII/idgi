package com.idgi.core;

import java.util.ArrayList;
import java.util.List;

/*
A discussion is a component of a Lesson and consists of Comments.
*/
public class Discussion {

	private boolean disabled;
	private List<Comment> comments;
	
	public Discussion() {
		comments = new ArrayList<>();
		disabled = false;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void setDisabled(boolean value) {
		disabled = value;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	/** Don't use. Only for JSON serialization. */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
