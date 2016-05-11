package com.idgi.core;

import com.idgi.util.Nameable;
import com.idgi.util.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject implements Nameable {
    private String name;

    private ArrayList<Course> courses;

    private Subject() {}

    public Subject(String name){
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public Course getCourse(String courseName) {
        return Util.findByName(courses, courseName);
    }

    public void addCourse(Course course) {
        if (!hasCourse(course))
            getCourses().add(course);
    }

    public boolean hasCourse(Course course) {
        return courses.contains(course);
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != Subject.class) return false;

        Subject that = (Subject) object;
        return this.name.equals(that.name) && this.courses.equals(that.courses);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
