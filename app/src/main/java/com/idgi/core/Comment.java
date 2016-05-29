package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String authorAccountName;


	private Comment() {}

	public Comment(String text, String authorAccountName) {
		this.text = text;
		this.authorAccountName = authorAccountName;
		replies = new ArrayList<>();
	}


	public void addReply(String message, String senderAccountName) {
		Comment reply = new Comment(message, senderAccountName);
		replies.add(reply);
	}
	
	public String getText() {
		return this.text;
	}

	public String getAuthorAccountName() {
		return authorAccountName;
	}

	public void setAuthorAccountName(String name) {
		this.authorAccountName = name;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	@JsonIgnore
	public int getNumberOfReplies(){
		if (replies == null)
			replies = new ArrayList<>();

		return replies.size();
	}

	@Override
	@JsonIgnore
	public List<Comment> getChildItemList() {
		return getReplies();
	}

	@Override
	@JsonIgnore
	public boolean isInitiallyExpanded() {
		return false;
	}

}
