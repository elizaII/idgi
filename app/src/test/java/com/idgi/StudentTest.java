
package com.idgi;

import com.idgi.core.Account;
import com.idgi.core.Answer;
import com.idgi.core.Hat;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.Student;
import com.idgi.core.Video;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	Hat testHat1;
	Hat testHat2;
	int point;
	Video video;
	String url;
	Student student;
    Quiz quiz;
//    private List<Hat> hats;

    private Question question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	private Question question2 = new Question("What is 5 + 7?", "It is more than 11 and less than 13.");

	@Before
	public void setUp() {
		Account account = new Account("login", "password");

		student = new Student("Namn");
		account.setUser(student);
		account.setEmail("email");

		testHat1 = new Hat();
		testHat1.setName("Testhatt 1");
		testHat1.setDescription("Hattbeskrivning 1");
		testHat1.setPoints(100);
		testHat1.setImageId(R.drawable.hat_black);

		testHat2 = new Hat();
		testHat2.setName("Testhatt 2");
		testHat2.setDescription("Hattbeskrivning 2");
		testHat2.setPoints(200);
		testHat2.setImageId(R.drawable.hat_pink);

		point = 1;

		video = new Video("urlen");

        quiz = new Quiz();
	}

	@Test
	public void testAddPointsThroughQuiz() {
		Student user = new Student("Test");

		Quiz quiz = new Quiz();

		Answer q1a1 = new Answer("Correct answer.");
		q1a1.setCorrect(true);
		q1a1.setSelected(true);

		Answer q1a2 = new Answer("Inorrect answer.");

		question1.addAnswers(q1a1, q1a2);

		Answer q2a1 = new Answer("Correct answer.");
		q2a1.setCorrect(true);

		Answer q2a2 = new Answer("Inorrect answer.");

		q2a2.setSelected(true);

		question2.addAnswers(q2a1, q2a2);

		quiz.addQuestion(question1);
		quiz.addQuestion(question2);

		quiz.nextQuestion();
		quiz.nextQuestion();

		//Get half correct
		user.givePointsForQuiz(quiz.getID(), quiz.getPointsEarned());

		int pointsAfterHalfRight = user.getPoints();
		assertTrue(pointsAfterHalfRight > 0);
		System.out.println(Integer.toString(pointsAfterHalfRight));

		q2a2.setSelected(false);
		q2a1.setSelected(true);

		user.givePointsForQuiz(quiz.getID(), quiz.getPointsEarned());
		int pointsNow = user.getPoints();

		assertTrue(pointsAfterHalfRight < user.getPoints());

		q2a2.setSelected(false);
		user.givePointsForQuiz(quiz.getID(), quiz.getPointsEarned());
		assertTrue(pointsNow == user.getPoints());
	}

	@Test
	public void testContainsHat() {
		Student user = new Student("Test");
//		user.addHat(testHat1);

//		assertTrue("Should contain testHat2", user.containsHat(testHat1));
//		assertFalse("Should not contain testHat2", user.containsHat(testHat2));
	}

	@Test
	public void testAddPointForVideo() {
        assertFalse("Should not have points", student.getPoints() > 0);
        student.givePointsForViewingVideo(video, point);
		assertTrue("Should have points", student.getPoints() > 0);
	}

    @Test
    public void testAddPointForQuiz() {
        assertFalse("Should not have points", student.getPoints() > 0);
        student.givePointsForQuiz(quiz.getID(), point);
        assertTrue("Should have points", student.getPoints() > 0);
    }
}
