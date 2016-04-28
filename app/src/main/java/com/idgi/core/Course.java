package com.idgi.core;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private final String id;
	private String name;
	private List<Lesson> lessons;
	
	public Course(String name) {
		this.id = "N/A";
		this.name = name;
		lessons = new ArrayList<>();
	}
	
	/**
	 * If the lesson is not already included in the course, adds the lesson.
	 */
	public void addLesson(Lesson lesson) {
		if (!lessons.contains(lesson))
			lessons.add(lesson);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getID() {
		return this.id;
	}
	
}
