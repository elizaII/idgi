package com.idgi.services;

import com.idgi.core.Answer;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

	public List<School> getSchools(){
		School school1 = new School("1", "MEG");
		School school2 = new School("2", "Hvitfeldska");
		School school3 = new School("3", "Polhem");
		School school4 = new School("4", "Ingrid Segerstedt");
		School school5 = new School("5", "Samskolan");
		School school6 = new School("6", "Drottning Blanka");

		List<School> schools = new ArrayList<>();

		schools.add(school1);
		schools.add(school2);
		schools.add(school3);
		schools.add(school4);
		schools.add(school5);
		schools.add(school6);

		return schools;
	}

	public List<Subject> getSubjects(){
		Subject math = new Subject("1", "Math");
		Subject english = new Subject("2", "English");
		Subject swedish = new Subject("3", "Swedish");
		Subject physics = new Subject("4", "Physics");

		List<Subject> subjects = new ArrayList<>();
		subjects.add(math);
		subjects.add(english);
		subjects.add(swedish);
		subjects.add(physics);

		return subjects;
	}

	public List<Course> getCourses(){
		Course math1 = new Course("Matte 1c");
		Course math2 = new Course("Matte 2c");
		Course math3 = new Course("Matte 3c");

		List<Course> courses = new ArrayList<>();
		courses.add(math1);
		courses.add(math2);
		courses.add(math3);

		return courses;
	}


	public List<Lesson> getLessons(){
		Lesson complex1 = new Lesson("Introduktion", new Video("Gyhg-bWssOk"));
		Lesson complex2 = new Lesson("Räkneregler", new Video("LUQrdbOK508"));
        Lesson complex3 = new Lesson("Komplexa talplanet", new Video("nl87kdkJTYc"));
        //Todo... Add more lessons.

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(complex1);
        lessons.add(complex2);
        lessons.add(complex3);

        return lessons;
	}

	//public List<User> getUsers() {

	//}

	public void setUser() {

	}

}
