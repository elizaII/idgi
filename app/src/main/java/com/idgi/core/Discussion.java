package com.idgi.core;

import java.util.ArrayList;
import java.util.List;

public class Discussion {

	private boolean disabled;
	private List<Comment> comments;
	
	public Discussion() {
		comments = new ArrayList<Comment>();
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
}
