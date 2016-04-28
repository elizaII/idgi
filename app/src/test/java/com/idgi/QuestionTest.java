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
		
		question1.addOption(optionCat);
		question1.addOption(optionDog);
		
		optionCat.setSelected(true);
		
		assertTrue(question1.isCorrectlyAnswered());
	}
	
	public void testGetMistakes() {
		resetQuestion();
		
		Answer option10 = new Answer("10");
		option10.setCorrect(true);
		Answer option12 = new Answer("12");
		Answer option2 = new Answer("2");
		
		question1.addAnswers(option10, option12, option2);
		
		option12.setSelected(true);
		option2.setSelected(true);
		
		Set<Answer> mistakes = question1.getMistakes();
		
		assertTrue(mistakes.contains(option12) && mistakes.contains(option2));
	}
	
	public void resetQuestion() {
		question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	}

}
