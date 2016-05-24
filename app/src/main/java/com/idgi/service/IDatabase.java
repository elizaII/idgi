package com.idgi.service;

import com.idgi.core.Account;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;

import java.util.List;

public interface IDatabase {

	IQuiz getQuiz(String key);
	void retrieveSchools(boolean isOfflineMode);
	List<School> getSchools();
	List<Subject> getSubjects(School school);
	List<Lesson> getLessons(Course course);
	List<Course> getCourses(Subject subject);
	List<Comment> getComments(Lesson lesson);
	void retrieveHats(boolean isOfflineMode);
	void retrieveAccounts();
	Account getAccount(String accountName, String password);
}
