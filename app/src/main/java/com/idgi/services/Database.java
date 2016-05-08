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
		question.addAnswers(Answer.incorrect("8"), Answer.correct("10"), Answer.incorrect("16"), Answer.incorrect("9"));


		Quiz quiz = new Quiz();
		quiz.addQuestion(question);

		question = new Question("Is this the last question?");
		question.addAnswers(Answer.correct("Yes"), Answer.incorrect("No"));

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
				new School("MEG"), new School("Hvitfeldska"),
				new School("Polhem"), new School("Ingrid Segerstedt"),
				new School("Samskolan"), new School("Drottning Blanka")
		};

		return Arrays.asList(schools);
	}

	public List<Subject> getSubjects(School school){
		String[] subjectNames = {"Math", "English", "Swedish", "Physics"};
		List<Subject> subjects = new ArrayList<>();

		for (String name : subjectNames)
		subjects.add(new Subject(name));

		return subjects;
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

		Video video = Video.from("ffLLmV4mZwU");

		Lesson complex1 = Lesson.create("Lektion 1").withVideo(video).withQuiz(getQuiz(""));
		Lesson complex2 = Lesson.create("Lektion 2").withVideo(video).withQuiz(getQuiz(""));
		Lesson complex3 = Lesson.create("Lektion 3").withVideo(video).withQuiz(getQuiz(""));

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
