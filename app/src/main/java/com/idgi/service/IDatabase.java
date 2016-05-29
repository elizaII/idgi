package com.idgi.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.idgi.core.Account;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;

import java.util.List;
/*
Interface that separates logic in database from the application,
used to improve the modular design of the application
 */
public interface IDatabase {
	void retrieveSchools(boolean isOfflineMode);
	List<School> getSchools();
	List<Subject> getSubjects(School school);
	List<Lesson> getLessons(Course course);
	List<Course> getCourses(Subject subject);
	List<Comment> getComments(Lesson lesson);
	void retrieveHats(boolean isOfflineMode);
	void downloadProfilePicture(Context context, final Account account);
	void saveProfilePicture(Account account);
	void retrieveAccounts();
	Account getAccount(String accountName, String password);
}
