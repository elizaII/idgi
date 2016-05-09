package com.idgi.core;

import com.idgi.util.Nameable;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject implements Nameable {
    private String name;

    private List<Course> courses;

    private Subject() {}

    public Subject(String name){
        this.name = name;
        this.courses = new ArrayList<Course>();
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        if (courses == null) {
            courses = Collections.emptyList();
        }
        return courses;
    }

    public Course getCourse(String courseName) {
        return Util.findByName(courseName, courses);
    }

    public void addCourse(Course course) {
        System.out.println(course.getName());
        if (courses.equals(Collections.emptyList())){
            courses = new ArrayList<Course>();
        }
        courses.add(course);
    }
}
