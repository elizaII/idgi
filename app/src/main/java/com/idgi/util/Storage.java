package com.idgi.util;

import com.idgi.core.Quiz;

/**
 * Created by Jonathan Krän on 28/04/2016.
 */
public class Storage {

	private static Quiz currentQuiz;

	public static Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public static void setCurrentQuiz(Quiz quiz) {
		currentQuiz = quiz;
	}
}
