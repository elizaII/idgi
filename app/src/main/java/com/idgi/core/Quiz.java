package com.idgi.core;

import com.idgi.services.Database;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class Quiz {

	private int currentIndex = 0;
	
	List<Question> questions;

	private int id;
	
	public Quiz() {
		questions = new ArrayList<>();
		this.id = Database.getInstance().getNewQuizId();
	}


	public int getID() {
		return this.id;
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
		if (!isFinished())
			return questions.get(currentIndex);

		return null;
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

	/**
	 * Calculates points earned from the quiz
	 * @throws IllegalStateException if called before quiz is finished (@see isFinished())
	 * @return amount of points earned from the quiz
	 */
	public int getPointsEarned() {
		if (!this.isFinished())
			throw new IllegalStateException("Can not prompt for points before quiz is finished.");

		int total = 0;
		for(Question question : questions) {
			int amount = 0;
			int correctAnswerAmount = question.getCorrectAnswerAmount();

			for (Answer answer : question.getAnswers()) {
				if (answer.isSelected())
					if (answer.isCorrect()) {
						++amount;
					}
					else {
						--amount;
					}
			}

			int points = calculatePointsEarned(amount, correctAnswerAmount);
			total += points;
		}

		return total;
	}

	/**
	 * Returns amount of points to be awarded for a question.
	 * @param deltaAnswers correctAnswers - incorrectAnswers
	 */
	private int calculatePointsEarned(int deltaAnswers, int correctAnswerAmount) {
		return Math.max(0, (int) ((float) deltaAnswers / correctAnswerAmount * 100));
	}
}
