package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
Class representing a question in a Quiz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements ParentListItem {
	
	private String text;
	private String hint;
	private List<Answer> answers;

	private Question() {}

	public Question(String text, String hint, List<Answer> answers) {
		this.text = text;
		this.hint = hint;
		this.answers = answers;
	}

	public Question(String text, String hint) {
		this(text, hint, new ArrayList<Answer>());
	}

	public Question(String text) {
		this(text, "", new ArrayList<Answer>());
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	public void addAnswers(Answer... answers) {
		this.answers.addAll(Arrays.asList(answers));
	}
	
	public boolean isCorrectlyAnswered() {
		for (Answer answer : answers)
			if (answer.isSelectedAndIncorrect() || answer.isDeselectedAndCorrect())
				return false;
		
		return true;
	}

	public List<Answer> getAnswers() {
		if (answers == null)
			answers = Collections.emptyList();

		return answers;
	}

	public int getAnswerAmount() {
		return answers.size();
	}

	public int getCorrectAnswerAmount() {
		int amount = 0;
		for (Answer answer : answers) {
			if (answer.isCorrect())
				++amount;
		}

		return amount;
	}

	public void reset() {
		for (Answer answer : answers)
			answer.setSelected(false);
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getHint() {
		return this.hint;
	}

	public void setHint(String hint){
		this.hint=hint;
	}
	
	public boolean hasAnswer(Answer answer) {
		return answers.contains(answer);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if (other.getClass() != this.getClass()) return false;
		
		Question that = (Question) other;
		
		if ( !(this.text.equals(that.text) && this.hint.equals(that.hint)) )
			return false;
		
		for (Answer answer : answers)
			if (!that.hasAnswer(answer))
				return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result = text.hashCode();
		result = 31 * result + hint.hashCode();
		result = 31 * result + answers.hashCode();
		return result;
	}

	@JsonIgnore
	public List<?> getChildItemList() {
		return answers;
	}


	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}
}
