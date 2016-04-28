package com.idgi.services;

import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.core.Quiz;


public final class Database implements IDatabase {
	private static Database instance = null;


	// TODO Implement this properly
	public Quiz getQuiz(String key) {
		Question question = new Question("What is 5 + 5?", "It is 10.");
		Answer option1 = new Answer("8");
		Answer option2 = new Answer("10");
		Answer option3 = new Answer("16");
		Answer option4 = new Answer("9");
		option2.setCorrect(true);
		question.addAnswers(option1, option2, option3, option4);


		Quiz quiz = new Quiz();
		quiz.addQuestion(question);

		Answer option5 = new Answer("Yes");
		option5.setCorrect(true);
		Answer option6 = new Answer("No");
		question = new Question("Is this the last question?");
		question.addAnswers(option5, option6);

		quiz.addQuestion(question);

		return quiz;
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();

		return instance;
	}
}
