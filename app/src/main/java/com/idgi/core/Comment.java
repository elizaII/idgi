package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/*
Class representing comments for Lessons' videos
 */
public class Comment implements ParentListItem {
	
	private String text;
	private List<Comment> replies;
	private User author;


	private Comment() {}

	public Comment(String text, User author) {
		this.text = text;
		this.author = author;
		replies = new ArrayList<>();
	}


	public void addReply(String message, User sender) {
		Comment reply = new Comment(message, sender);
		//replies.add(0,reply);
		replies.add(reply);
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

	public int getNumberOfReplies(){ return replies.size(); }

	@Override
	public List<Comment> getChildItemList() {
		return getReplies();
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}

}
