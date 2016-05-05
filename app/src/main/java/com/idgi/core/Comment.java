package com.idgi.core;

import java.util.Set;
import java.util.TreeSet;

public class Comment {
	
	private String text;
	private Set<Comment> replies;
	private User author;


	public Comment(String text, User author) {
		this.text = text;
		this.author = author;
		replies = new TreeSet<Comment>();
	}
	
	public void addReply(Comment comment) {
		replies.add(comment);
	}
	
	public String getText() {
		return this.text;
	}

	public User getAuthor() {
		return author;
	}


}
