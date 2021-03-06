package com.idgi.session;

import com.idgi.core.Account;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Student;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.Video;

/*
Holds currently active data, like what school we've navigated to or the lesson
we're currently looking at
 */
public class SessionData {
	private static School currentSchool;
	private static Subject currentSubject;
	private static Course currentCourse;
	private static Lesson currentLesson;
	private static IQuiz currentQuiz;
	private static Account loggedInAccount;

	private static boolean firstRun = true;


	public static boolean isFirstRun() {
		return firstRun;
	}

	public static void setFirstRun(boolean firstRun) {
		SessionData.firstRun = firstRun;
	}

	public static Subject getCurrentSubject() {
		return currentSubject;
	}

	public static void setCurrentSubject(Subject currentSubject) {
		SessionData.currentSubject = currentSubject;
	}

	public static Course getCurrentCourse() {
		return currentCourse;
	}

	public static void setCurrentCourse(Course course) {
		currentCourse = course;
	}

    public static User getLoggedInUser() {
        return loggedInAccount == null ? null : loggedInAccount.getUser();
    }

    public static boolean hasLoggedInUser(){
		return loggedInAccount != null && loggedInAccount.getUser() != null;
	}

	public static Student getUserAsStudent() {
		User user = getLoggedInUser();
		return user != null && user instanceof Student ? (Student) user : null;
	}

	/* Returns the current quiz.
	 * It is set by the LessonActivity which retrieves the
	 * current lesson's quiz. */
	public static IQuiz getCurrentQuiz() {
		return currentQuiz;
	}

    public static void setCurrentQuiz(IQuiz quiz) {
        currentQuiz = quiz;
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

	public static School getCurrentSchool() {
		return currentSchool;
	}

	public static void setCurrentSchool(School school) {
		currentSchool = school;
	}

	public static void setLoggedInAccount(Account account) {
		loggedInAccount = account;
	}

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

	public static void logout() {
		loggedInAccount = null;
	}
}
