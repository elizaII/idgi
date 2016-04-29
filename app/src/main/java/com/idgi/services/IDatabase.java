package com.idgi.services;

import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;

public interface IDatabase {

	Quiz getQuiz(String key);
	School getSchool(String key);
	Subject getSubject(String key);
}
