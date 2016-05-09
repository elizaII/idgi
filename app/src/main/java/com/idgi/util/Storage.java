package com.idgi.util;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.Video;

public class Storage {

	private static School currentSchool;
	private static Quiz currentQuiz;

	public static Subject getCurrentSubject() {
		return currentSubject;
	}

	public static void setCurrentSubject(Subject currentSubject) {
		Storage.currentSubject = currentSubject;
	}

	private static Subject currentSubject;

	public static Course getCurrentCourse() {
		return currentCourse;
	}

	public static void setCurrentCourse(Course currentCourse) {
		Storage.currentCourse = currentCourse;
	}

	private static Course currentCourse;
	private static Lesson currentLesson;

	// TODO : Create actual implemenation of activeUser, probably through phone local storage?
	public static void setActiveUser(User activeUser) {
		Storage.activeUser = activeUser;
	}

	private static User activeUser = new User("Test");

	public static Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public static Video getCurrentVideo() {
		return currentLesson.getVideo();
	}

	public static void setCurrentLesson(Lesson lesson) {
        currentLesson = lesson;
		setCurrentQuiz(lesson.getQuiz());
    }

    public static Lesson getCurrentLesson() {
        return currentLesson;
    }
	public static User getActiveUser() {
		return activeUser;
	}

	public static School getCurrentSchool() {
		return currentSchool;
	}

	public static void setCurrentSchool(School school) {
		currentSchool = school;
	}

	public static void setCurrentQuiz(Quiz quiz) {
		currentQuiz = quiz;
	}
}
