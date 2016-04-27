package com.idgi;

import com.idgi.core.Question;
import com.idgi.core.Quiz;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class QuizTest {

	private Question question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	private Question question2 = new Question("What is 5 + 7?", "It is more than 11 and less than 13.");
	private Question question3 = new Question("What is 5 + 15?", "It is more than 19 and less than 21.");
	
	private List<Question> questions = Arrays.asList(question1, question2, question3);
	
	@Test
	public void testLength() {
		Quiz quiz = new Quiz();
		
		
		quiz.addQuestions(questions);
		assertTrue(quiz.length() == questions.size());
	}
	
	@Test
	public void testAddQuestion() {
		Quiz quiz = new Quiz();
		
		quiz.addQuestion(question1);
		
		assertTrue(quiz.hasQuestion(question1));
	}
	
	@Test
	public void testGetCurrentQuestion() {
		Quiz quiz = new Quiz();
		
		quiz.addQuestions(questions);
		
		assertTrue(quiz.getCurrentQuestion().equals(question1));
		
		quiz.nextQuestion();
		
		assertTrue(quiz.getCurrentQuestion().equals(question2));
	}

	@Test
	public void testNextQuestion() {
		Quiz quiz = new Quiz();
		
		quiz.addQuestions(questions);
		
		quiz.nextQuestion();
		quiz.nextQuestion();
		quiz.nextQuestion();
		quiz.nextQuestion();

		assertTrue(quiz.isFinished());
	}
	
	@Test
	public void testAddQuestions() {
		Quiz quiz = new Quiz();
		
		quiz.addQuestions(questions);

		for (Question question : questions)
			if (!quiz.hasQuestion(question))
				fail();
	}

}
