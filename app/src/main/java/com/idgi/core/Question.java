package com.idgi.core;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question implements ParentListItem {

	private static final String NO_HINTS_MESSAGE = "There are no hints for this question.";
	
	private String text;
	private String hint;
	private List<Answer> answers;
	
	public Question(String text, String hint, List<Answer> answers) {
		this.text = text;
		this.hint = hint;
		this.answers = answers;
	}
	
	public Question(String text, String hint) {
		this(text, hint, new ArrayList<Answer>());
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
		return answers;
	}

	public int getAnswerAmount() {
		return answers.size();
	}

	public Question(String text) {
		this(text, NO_HINTS_MESSAGE, new ArrayList<Answer>());
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getHint() {
		return this.hint;
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

	public void addNewCorrectAnswer(String text) {
		Answer answer = new Answer(text);
		answer.setCorrect(true);

		answers.add(answer);
	}

	public void addNewAnswer(String text) {
		Answer answer = new Answer(text);

		answers.add(answer);
	}

	@Override
	public List<?> getChildItemList() {
		return answers;
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}
}
