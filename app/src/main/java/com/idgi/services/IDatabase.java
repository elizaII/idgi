package com.idgi.services;

import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;

import java.util.List;

public interface IDatabase {

	IQuiz getQuiz(String key);
	void retrieveSchools();
	List<School> getSchools();
	List<Subject> getSubjects(School school);
	List<Lesson> getLessons(Course course);
	List<Course> getCourses(Subject subject);
	List<Comment> getComments(Lesson lesson);
	void retrieveUsers();
}
