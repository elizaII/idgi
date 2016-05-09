package com.idgi.services;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireDatabase implements IDatabase {
	private static FireDatabase instance = null;
	private static Firebase ref = new Firebase("https://scorching-torch-4835.firebaseio.com");

	private List<School> schools;

	public Quiz getQuiz(String key) {
		return null;
	}

	public void addSchool(School school) {
		Firebase schoolRef = ref.child("schools").child(school.getName());
		schoolRef.setValue(school);
		schools.add(school);
	}

	public List<School> getSchools() {
		if (schools == null) {
			fetchSchools();
		}
		System.out.println("hämtar skolor" + schools.size());
		return schools;
	}

	public School getSchool(String schoolName) {
		return Util.findByName(schoolName, schools);
	}

	public List<Subject> getSubjects(School school) {
		return getSubjects(school.getName());
	}

	public List<Course> getCourses(String schoolName, String subjectName) {
		List<Course> courseList;
		//System.out.println(getSchool(schoolName).getSubject(subjectName));
		System.out.println(schoolName);
		System.out.println(subjectName);
		System.out.println(getSchool(schoolName).getSubjects().size());
		System.out.println(getSchool(schoolName).getSubjects().get(0).getName());
		//System.out.println(getSchool(schoolName).getSubject(subjectName).getName());
		if (getSchool(schoolName).getSubjects().size()>0){
			return getSchool(schoolName).getSubject(subjectName).getCourses();
		}
		else {
			courseList = Collections.emptyList();
			return courseList;
		}
	}


	public List<Subject> getSubjects(String schoolName) {
		return getSchool(schoolName).getSubjects();
	}


	public List<Lesson> getLessons(Course course) {
		return null;
	}

	public List<Course> getCourses(Subject subject) {
		return subject.getCourses();
	}

	public List<Comment> getComments(Lesson lesson) {
		return null;
	}

	public static FireDatabase getInstance() {
		if (instance == null)
			instance = new FireDatabase();

		return instance;
	}

	public void createSchools() {
		Database mock = Database.getInstance();

		for (School school : mock.getSchools()) {
			//for (Subject subject : mock.getSubjects(null)) {
			//	if (subject.getName().equals("Math"))
			//		for (Course course : mock.getCourses(null)) {
			//			for (Lesson lesson : mock.getLessons(null))
			//				course.addLesson(lesson);
			//			subject.addCourse(course);
			//		}

			//	school.addSubject(subject);
			//}

			addSchool(school);
		}
	}

	private void fetchSchools() {
		schools = new ArrayList<>();

		Firebase reff = new Firebase("https://scorching-torch-4835.firebaseio.com/schools");

		reff.addListenerForSingleValueEvent(new ValueEventListener() {
			public void onDataChange(DataSnapshot snapshot) {

				for (DataSnapshot child : snapshot.getChildren()) {
					School school = child.getValue(School.class);
					schools.add(school);
				}
			}

			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	public void initialize() {
		fetchSchools();
	}


}
