package com.idgi.services;

import com.idgi.core.Option;
import com.idgi.core.Question;
import com.idgi.core.Quiz;


public final class Database implements IDatabase {
	private static Database instance = null;


	// TODO Implement this properly
	public Quiz getQuiz(String key) {
		Question question = new Question("What is 5 + 5?", "It is 10.");
		Option option1 = new Option("8");
		Option option2 = new Option("10");
		Option option3 = new Option("Hamburgare");
		Option option4 = new Option("Kebab");
		option2.setCorrect(true);
		question.addOptions(option1, option2, option3, option4);


		Quiz quiz = new Quiz();
		quiz.addQuestion(question);

		return quiz;
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();

		return instance;
	}
}
