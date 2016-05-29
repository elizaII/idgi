package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/*
A Course is created by a Teacher and attended by Students. It contains Lessons.
* */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course implements Nameable {
	private String name;
	private List<Lesson> lessons;
	private String description;
	private String parentSubjectName;
	private String parentSchoolName;

	private Course() {}

	public Course(String name) {
		this.name = name;
		lessons = new ArrayList<>();
		this.description= "";
	}

	public void addLesson(Lesson lesson) {
			lessons.add(lesson);
	}

	public Lesson getLesson(String lessonName) {
		return ModelUtility.findByName(lessons, lessonName);
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

	public String getParentSubjectName() {
		return parentSubjectName;
	}

	public void setParentSubjectName(String parentSubjectName) {
		this.parentSubjectName = parentSubjectName;
	}

	public String getParentSchoolName() {
		return parentSchoolName;
	}

	public void setParentSchoolName(String parentSchoolName) {
		this.parentSchoolName = parentSchoolName;
	}

	@Override
	@JsonIgnore
	public NameableType getType() {
		return NameableType.COURSE;
	}

}
