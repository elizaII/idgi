package com.idgi.core;

import java.util.Set;
import java.util.TreeSet;

public class Comment {
	
	private String text;
	private Set<Comment> replies;
	
	public Comment(String text) {
		this.text = text;
		replies = new TreeSet<Comment>();
	}
	
	public void addReply(Comment comment) {
		replies.add(comment);
	}
	
	public String getText() {
		return this.text;
	}
	
}
