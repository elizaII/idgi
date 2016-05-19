package com.idgi.services;

import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.Hat;
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

/**
 * This class creates mock data which the application will use if no internet connection is available.
 */
public final class MockData {
	private static volatile MockData instance = null;

	public IQuiz getQuiz() {
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

	public List<Subject> getSubjects(){
		String[] subjectNames = {"Math", "English", "Swedish", "Physics"};
		List<Subject> subjects = new ArrayList<>();

		for (String name : subjectNames)
		subjects.add(new Subject(name));

		return subjects;
	}

	public List<Course> getCourses(){
		Course math1 = new Course("Matte 1c");
		Course math2 = new Course("Matte 2c");
		Course math3 = new Course("Matte 3c");

		return Arrays.asList(math1, math2, math3);
	}

	public List<Lesson> getLessons(){
		Video video = Video.from("ffLLmV4mZwU");

		Lesson complex1 = Lesson.create("Lektion 1").withVideo(video).withQuiz(getQuiz());
		Lesson complex2 = Lesson.create("Lektion 2").withVideo(video).withQuiz(getQuiz());
		Lesson complex3 = Lesson.create("Lektion 3").withVideo(video).withQuiz(getQuiz());

		return Arrays.asList(complex1, complex2, complex3);
	}

	public List<School> createSchools() {
		MockData mock = MockData.getInstance();
		List<School> schools = new ArrayList<>();

		for (School school : mock.getSchools()) {
			for (Subject subject : mock.getSubjects()) {
				if (subject.getName().equals("Math"))
					for (Course course : mock.getCourses()) {
						for (Lesson lesson : mock.getLessons())
							course.addLesson(lesson);
						subject.addCourse(course);
					}

				school.addSubject(subject);
			}

			schools.add(school);
		}

		return schools;
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

	public List<Hat> getHats() {

		Hat regularHat = new Hat();
		regularHat.setName("Vanlig hatt");
		regularHat.setImageId(R.drawable.hat_black);
		regularHat.setDescription("Hatten för dig som inte vet vad du vill än");
		regularHat.setPoints(100);

		Hat cowboyHat = new Hat();
		cowboyHat.setName("Cowboyhatt");
		cowboyHat.setImageId(R.drawable.hat_black);
		cowboyHat.setPoints(300);
		cowboyHat.setDescription("En hatt för den tuffa eleven");

		Hat topHat = new Hat();
		topHat.setName("Hög hatt");
		topHat.setImageId(R.drawable.hat_black);
		topHat.setPoints(500);
		topHat.setDescription("Det här är en väldigt värdefull hatt");

		Hat magicHat = new Hat();
		magicHat.setName("Magihatt");
		magicHat.setImageId(R.drawable.hat_black);
		magicHat.setDescription("Abrakadabra");
		magicHat.setPoints(1000);

		Hat catHat = new Hat();
		catHat.setName("Katthatt");
		catHat.setImageId(R.drawable.hat_black);
		catHat.setDescription("Mjau prrr");
		catHat.setPoints(2000);

		return Arrays.asList(regularHat, cowboyHat, topHat, magicHat, catHat);
	}

	private User getUser() {
		User user = new StudentUser("Pelle");
		user.setEmail("pelles_mail@gmail.com");
		user.setAge(9);

		return user;
	}
}
