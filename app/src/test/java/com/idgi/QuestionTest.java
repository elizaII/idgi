package com.idgi;

import com.idgi.core.Option;
import com.idgi.core.Question;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Test;

public class QuestionTest {
	
	
	private Question question1;
	
	@Test
	public void testisCorrectlyAnswered() {
		resetQuestion();
		
		Option optionCat = new Option("Cat");
		optionCat.setCorrect(true);
		Option optionDog = new Option("Dog");
		
		question1.addOption(optionCat);
		question1.addOption(optionDog);
		
		optionCat.setSelected(true);
		
		assertTrue(question1.isCorrectlyAnswered());
	}
	
	public void testGetMistakes() {
		resetQuestion();
		
		Option option10 = new Option("10");
		option10.setCorrect(true);
		Option option12 = new Option("12");
		Option option2 = new Option("2");
		
		question1.addOptions(option10, option12, option2);
		
		option12.setSelected(true);
		option2.setSelected(true);
		
		Set<Option> mistakes = question1.getMistakes();
		
		assertTrue(mistakes.contains(option12) && mistakes.contains(option2));
	}
	
	public void resetQuestion() {
		question1 = new Question("What is 5 + 5?", "It is more than 9 and less than 11.");
	}

}
