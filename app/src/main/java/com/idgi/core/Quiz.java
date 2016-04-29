package com.idgi.core;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

	private int currentIndex = 0;
	
	List<Question> questions;
	
	public Quiz() {
		questions = new ArrayList<>();
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
		if (!isFinished())
			++currentIndex;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public boolean isLastQuestion() {
		return currentIndex == length() - 1;
	}

	public boolean isFinished() {
		return currentIndex == length();
	}

	public int getCorrectAnswerAmount() {
		int n = 0;

		for (Question question : questions)
			if (question.isCorrectlyAnswered())
				++n;

		return n;
	}
}
