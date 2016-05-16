
package com.idgi;

import android.util.Log;

import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.User;
import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	/*private Question question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	private Question question2 = new Question("What is 5 + 7?", "It is more than 11 and less than 13.");

	@Test
	public void testAddPointsThroughQuiz() {
		User user = new User("Test");

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
	}*/
}
