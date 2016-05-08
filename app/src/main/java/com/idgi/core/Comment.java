package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Comment implements ParentListItem{
	
	private String text;


	private Comment replyTo;
	private List<Comment> replies;
	private User author;

	public Comment(String text, User author) {
		this.text = text;
		this.author = author;
		replies = new ArrayList<Comment>();
	}

	public Comment(Comment replyTo,String text, User author) {
		this.text = text;
		this.author = author;
		replies = new ArrayList<Comment>();
		this.replyTo=replyTo;
	}


	public void addReply(Comment reply) {
		replies.add(0,reply);
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

	public int getNbrOfReplies(){ return replies.size(); }

	@Override
	public List<?> getChildItemList() {
		return replies;
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}

}
