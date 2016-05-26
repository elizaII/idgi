package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject implements Nameable {
    private String name;

    private String parentSchoolName;

    private ArrayList<Course> courses;

    private Subject() {}

    public Subject(String name){
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public Course getCourse(String courseName) {
        return ModelUtility.findByName(courses, courseName);
    }

    public void addCourse(Course course) {
        if (!hasCourse(course)) {
            getCourses().add(course);
            course.setParentSchoolName(this.parentSchoolName);
            course.setParentSubjectName(this.getName());
        }
    }

    public String getParentSchoolName() {
        return parentSchoolName;
    }

    public void setParentSchoolName(String name) {
        this.parentSchoolName = name;

        if (courses != null) {
            for (Course course : courses)
                course.setParentSchoolName(name);
        }
    }

    public boolean hasCourse(Course course) {
        return courses.contains(course);
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;

        Subject that = (Subject) object;
        return this.name.equals(that.name) && this.getCourses().equals(that.getCourses());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @JsonIgnore
    @Override
    public NameableType getType() {
        return NameableType.SUBJECT;
    }
}
