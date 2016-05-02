package com.idgi.util;

import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.Video;

/**
 * Created by Jonathan Krän on 28/04/2016.
 */
public class Storage {

	private static Quiz currentQuiz;
	private static Video currentVideo;
    private static Lesson currentLesson;

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

	public static void setCurrentLesson(Lesson lesson) {
        currentLesson = lesson;
    }

    public static Lesson getCurrentLesson() {
        return currentLesson;
    }
}
