package com.idgi.services;

import com.idgi.core.Quiz;

public interface IDatabase {

	Quiz getQuiz(String key);
}