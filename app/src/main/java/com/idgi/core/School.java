package com.idgi.core;

import com.idgi.util.Nameable;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class School implements Nameable {
    private String name;

	private List<Subject> subjects;

    private School() {
    }

    public School(String name){
        this.name = name;
		this.subjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

	public List<Subject> getSubjects() {
		if (subjects == null)
			subjects = Collections.emptyList();
		
		return subjects;
	}

	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public Subject getSubject(String subjectName) {
		return Util.findByName(subjectName, subjects);
	}
}
