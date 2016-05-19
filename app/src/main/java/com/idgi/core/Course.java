package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course implements Nameable {
	private String name;
	private List<Lesson> lessons;
	private String description;
	private String parentSubject;
	private String parentSchool;

	private Course() {}

	public Course(String name) {
		this.name = name;
		lessons = new ArrayList<>();
		this.description= "";
		this.parentSchool="no school";
		this.parentSubject="no subject";
	}

	public void addLesson(Lesson lesson) {
			lessons.add(lesson);
	}

	public Lesson getLesson(String courseName) {
		return ModelUtility.findByName(lessons, courseName);
	}
	
	public String getName() {
		return this.name;
	}

	public List<Lesson> getLessons() {
		if (lessons == null)
			lessons = new ArrayList<>();

		return lessons;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentSubject() {
		return parentSubject;
	}

	public void setParentSubject(String parentSubject) {
		this.parentSubject = parentSubject;
	}

	public String getParentSchool() {
		return parentSchool;
	}

	public void setParentSchool(String parentSchool) {
		this.parentSchool = parentSchool;
	}

	@Override
	@JsonIgnore
	public Type getType() {
		return Type.COURSE;
	}

}
