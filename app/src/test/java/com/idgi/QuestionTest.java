package com.idgi;

import com.idgi.core.Answer;
import com.idgi.core.Question;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Test;

public class QuestionTest {
	
	
	private Question question1;
	
	@Test
	public void testisCorrectlyAnswered() {
		resetQuestion();
		
		Answer optionCat = new Answer("Cat");
		optionCat.setCorrect(true);
		Answer optionDog = new Answer("Dog");

		question1.addAnswers(optionCat, optionDog);
		
		optionCat.setSelected(true);
		
		assertTrue(question1.isCorrectlyAnswered());
	}
	
	public void resetQuestion() {
		question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	}

}
