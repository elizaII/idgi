package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Comment implements ParentListItem{
	
	private String text;



	private List<Comment> replies;
	private User author;


	public Comment(String text, User author) {
		this.text = text;
		this.author = author;
		replies = new ArrayList<Comment>();
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

	public List<Comment> getReplies() {
		return replies;
	}

	@Override
	public List<?> getChildItemList() {
		return replies;
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}
}
