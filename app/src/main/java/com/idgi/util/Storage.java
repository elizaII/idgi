package com.idgi.util;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.User;
import com.idgi.core.Video;

public class Storage {

	private static Quiz currentQuiz;
	private static Video currentVideo;
    private static Lesson currentLesson;

	// TODO : Create actual implemenation of activeUser, probably through phone local storage?

	public static void setActiveUser(User activeUser) {
		Storage.activeUser = activeUser;
	}

	private static User activeUser = new User("Test");

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
	public static User getActiveUser() {
		return activeUser;
	}
}
