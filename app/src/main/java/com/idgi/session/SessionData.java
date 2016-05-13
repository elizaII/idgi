package com.idgi.session;

import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.Video;

public class SessionData {
	private static School currentSchool;
	private static Course currentCourse;
	private static Lesson currentLesson;
	private static User loggedInUser;

	public static Subject getCurrentSubject() {
		return currentSubject;
	}

	public static void setCurrentSubject(Subject currentSubject) {
		SessionData.currentSubject = currentSubject;
	}

	private static Subject currentSubject;

	public static Course getCurrentCourse() {
		return currentCourse;
	}

	public static void setCurrentCourse(Course course) {
		currentCourse = course;
	}

	// TODO : Create actual implemenation of activeUser, probably through phone local storage?
	public static void setLoggedInUser(User loggedInUser) {
		SessionData.loggedInUser = loggedInUser;
	}

	public static boolean hasLoggedInUser(){
		return loggedInUser != null;
	}

	/* Returns the current lesson's quiz.
	 * If there is no current lesson, returns null. */
	public static IQuiz getCurrentQuiz() {
		return currentLesson != null ? currentLesson.getQuiz() : null;
	}

	public static Video getCurrentVideo() {
		return currentLesson != null ? currentLesson.getVideo() : null;
	}

	public static void setCurrentLesson(Lesson lesson) {
        currentLesson = lesson;
    }

    public static Lesson getCurrentLesson() {
        return currentLesson;
    }
	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static School getCurrentSchool() {
		return currentSchool;
	}

	public static void setCurrentSchool(School school) {
		currentSchool = school;
	}


}
