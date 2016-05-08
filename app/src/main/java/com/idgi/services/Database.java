package com.idgi.services;

import android.util.Log;

import com.idgi.core.Answer;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.Video;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public final class Database implements IDatabase {
	private static Database instance = null;

	private static int currentQuizId = 0;

	//Used to make sure we don't give out duplicate points for a quiz
	public int getNewQuizId() {
		return currentQuizId++;
	}

	// TODO Implement this properly
	public Quiz getQuiz(String key) {
		Question question = new Question("What is 5 + 5?", "It is 10.");
		Answer answer1 = new Answer("8");
		Answer answer2 = new Answer("10");
		Answer answer3 = new Answer("16");
		Answer answer4 = new Answer("9");
		answer2.setCorrect(true);
		question.addAnswers(answer1, answer2, answer3, answer4);


		Quiz quiz = new Quiz();
		quiz.addQuestion(question);

		Answer answer5 = new Answer("Yes");
		answer5.setCorrect(true);
		Answer answer6 = new Answer("No");
		question = new Question("Is this the last question?");
		question.addAnswers(answer5, answer6);

		quiz.addQuestion(question);

		return quiz;
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();

		return instance;
	}

	public List<School> getSchools(){
		School[] schools = {
				new School("1", "MEG"), new School("2", "Hvitfeldska"),
				new School("3", "Polhem"), new School("4", "Ingrid Segerstedt"),
				new School("5", "Samskolan"), new School("6", "Drottning Blanka")
		};

		return Arrays.asList(schools);
	}

	public List<Subject> getSubjects(School school){
		Subject math = new Subject("1", "Math");
		Subject english = new Subject("2", "English");
		Subject swedish = new Subject("3", "Swedish");
		Subject physics = new Subject("4", "Physics");

		return Arrays.asList(math, english, swedish, physics);
	}

	public List<Course> getCourses(Subject subject){
		Course math1 = new Course("Matte 1c");
		Course math2 = new Course("Matte 2c");
		Course math3 = new Course("Matte 3c");

		return Arrays.asList(math1, math2, math3);
	}


	public List<Lesson> getLessons(Course course){
		/*Lesson complex1 = new Lesson("Introduktion", new Video("Gyhg-bWssOk"));
		Lesson complex2 = new Lesson("Räkneregler", new Video("LUQrdbOK508"));
        Lesson complex3 = new Lesson("Komplexa talplanet", new Video("nl87kdkJTYc"));*/

		Lesson complex1 = new Lesson("Test1", new Video("ffLLmV4mZwU"));
		Lesson complex2 = new Lesson("Test1", new Video("ffLLmV4mZwU"));
		Lesson complex3 = new Lesson("Test1", new Video("ffLLmV4mZwU"));

		return Arrays.asList(complex1, complex2, complex3);
	}
    public List<Comment> getComments(Lesson lesson){
		String[] words = {"I", "am", "hello", "rad", "totally", "dude", "fantastic"};

		Random rand = new Random();

		List<Comment> comments = new ArrayList<>();

		for (int i = 0; i < 10; ++i) {
			String text = "";
			int k = rand.nextInt(5);
			while(k-- >= 0) {
				text += " " + words[rand.nextInt(words.length)];
			}
			comments.add(new Comment(text + ".", Storage.getActiveUser()));
		}
        return comments;
	}

	//public List<User> getUsers() {

	//}

	public void setUser() {

	}
	public void addComment(Comment comment){
		System.out.println(comment.getText());

	}
}
