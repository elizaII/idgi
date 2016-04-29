package com.idgi.services;

import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;

import java.util.ArrayList;


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

	public School getSchool(String key){
		School school1 = new School("1", "MEG");
		School school2 = new School("2", "Hvitfeldska");
		School school3 = new School("3", "Polhem");
		School school4 = new School("4", "Ingrid Segerstedt");
		School school5 = new School("5", "Samskolan");
		School school6 = new School("6", "Drottning Blanka");

		ArrayList<School> schools = new ArrayList<>();

		schools.add(school1);
		schools.add(school2);
		schools.add(school3);
		schools.add(school4);
		schools.add(school5);
		schools.add(school6);

		for(School school : schools){
			if (key.equals(school.getKey())){
				return school;
			}
		}
		return new School("x", "no such school bruh");
	}

	public Subject getSubject(String key){
		Subject math = new Subject("1", "Math");
		Subject english = new Subject("2", "English");
		Subject swedish = new Subject("3", "Swedish");
		Subject physics = new Subject("4", "Physics");

		return math;
	}
}
