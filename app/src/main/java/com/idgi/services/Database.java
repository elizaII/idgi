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
		Option option3 = new Option("16");
		Option option4 = new Option("9");
		option2.setCorrect(true);
		question.addOptions(option1, option2, option3, option4);


		Quiz quiz = new Quiz();
		quiz.addQuestion(question);

		Option option5 = new Option("Yes");
		Option option6 = new Option("No");
		question = new Question("Is this the last question?");
		question.addOptions(option5, option6);

		quiz.addQuestion(question);

		return quiz;
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();

		return instance;
	}
}
