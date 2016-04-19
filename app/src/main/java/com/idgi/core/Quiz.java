package com.idgi.core;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

	private int currentIndex = 0;
	
	List<Question> questions;
	
	public Quiz() {
		questions = new ArrayList<Question>();
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public void addQuestions(List<Question> questions) {
		for (Question question : questions)
			this.questions.add(question);
	}
	
	/**
	 * 
	 * @return amount of questions in the quiz
	 */
	public int length() {
		return questions.size();
	}
	
	public boolean hasQuestion(Question question) {
		return questions.contains(question);
	}
	
	public Question getCurrentQuestion() {
		return questions.get(currentIndex);
	}
	
	/**
	 * @throws IllegalStateException if there are no more questions.
	 */
	public void nextQuestion() throws IllegalStateException {
		++currentIndex;
		
		if (currentIndex >= questions.size())
			throw new IllegalStateException("There is no next question. (Model.Quiz.java)");
	}
}
