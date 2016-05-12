package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz implements IQuiz{

	@JsonIgnore
	private int currentIndex = 0;

	//For JSon deserialization
	private String type = "quiz";
	
	List<Question> questions;

	private String id;

	public Quiz() {
		questions = new ArrayList<>();
		this.id = UUID.randomUUID().toString();
	}


	public String getID() {
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

	@JsonIgnore
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
			return 0;

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

	/* Resets the current question to the first question and deselects all answers */
	public void reset() {
		currentIndex = 0;

		for (Question question : questions) {
			question.reset();
		}
	}

	//Do not use; this is for JSon deserialization
	public String getType() {
		return type;
	}

	//Do not use; This is for JSon deserialization
	public void setType(String type) {
		this.type = type;
	}
}
