package com.idgi.services;

import com.idgi.core.Answer;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.StudentUser;
import com.idgi.core.Subject;
import com.idgi.core.TimedQuiz;
import com.idgi.core.User;
import com.idgi.core.Video;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public final class MockData implements IDatabase {
	private static volatile MockData instance = null;

	// TODO Implement this properly
	public IQuiz getQuiz(String key) {
		Question question = new Question("What is 5 + 5?", "It is 10.");
		question.addAnswers(Answer.incorrect("8"), Answer.correct("10"), Answer.incorrect("16"), Answer.incorrect("9"));

		IQuiz quiz = new Quiz();
		quiz.addQuestion(question);

		question = new Question("Is this the last one?");
		question.addAnswers(Answer.correct("Yes"), Answer.incorrect("No"));

		quiz.addQuestion(question);

		return quiz;
	}

	public static MockData getInstance() {
		if (instance == null)
			instance = new MockData();

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
			int k = rand.nextInt(5);
			StringBuilder buffer = new StringBuilder();
			while(k-- >= 0) {
				String randomWord = words[rand.nextInt(words.length)];
				buffer.append(String.format(Locale.ENGLISH, " %s", randomWord));
			}

			buffer.append(".");
			comments.add(new Comment(buffer.toString(), getUser()));
		}
        return comments;
	}

	private User getUser() {
		User user = new StudentUser("Pelle");
		user.setEmail("pelleBoy@gmail.com");
		user.setAge(9);

		return user;
	}

	//public List<User> getUsers() {

	//}

	public void setUser() {

	}
	public void addComment(Comment comment){
		System.out.println(comment.getText());
	}

	//Does nothing
	public void retrieveSchools() {}

	//Does nothing
	public void retrieveUsers() {}
}
