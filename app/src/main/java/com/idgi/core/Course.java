package com.idgi.core;

import com.idgi.util.Nameable;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course implements Nameable {
	private String name;
	private List<Lesson> lessons;

	private Course() {}

	public Course(String name) {
		this.name = name;
		lessons = new ArrayList<>();
	}

	public void addLesson(Lesson lesson) {
			lessons.add(lesson);
	}

	public Lesson getLesson(String courseName) {
		return Util.findByName(lessons, courseName);
	}
	
	public String getName() {
		return this.name;
	}

	public List<Lesson> getLessons() {
		if (lessons == null)
			lessons = Collections.emptyList();

		return lessons;
	}
	
}
