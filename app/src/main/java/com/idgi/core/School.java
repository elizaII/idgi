package com.idgi.core;

import com.idgi.util.Nameable;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class School implements Nameable {
    private String name;

	private String key;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Subject> getSubjects() {
		if (subjects == null)
			subjects = new ArrayList<>();
		
		return subjects;
	}

	public void addSubject(Subject subject) {
		if (!hasSubject(subject))
			getSubjects().add(subject);
	}

	public boolean hasSubject(Subject subject) {
		return subjects.contains(subject);
	}

	public Subject getSubject(String subjectName) {
		return Util.findByName(subjects, subjectName);
	}

	public boolean equals(Object object) {
		if (object == null) return false;
		if (object.getClass() != School.class) return false;

		School that = (School) object;
		if (this.getKey() != null)
			return this.getKey().equals(that.getKey());
		else
			return this.name.equals(that.name);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		if (key != null)
			result = 31 * result + key.hashCode();

		return result;
	}
}
