package com.idgi.util;

import com.idgi.core.Quiz;
import com.idgi.core.Video;

/**
 * Created by Jonathan Krän on 28/04/2016.
 */
public class Storage {

	private static Quiz currentQuiz;
	private static Video currentVideo;

	public static Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public static void setCurrentQuiz(Quiz quiz) {
		currentQuiz = quiz;
	}

	public static Video getCurrentVideo() {
		return currentVideo;
	}

	public static void setCurrentVideo(Video video) {
		currentVideo = video;
	}
}
