package com.idgi.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.idgi.core.Account;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.core.ModelUtility;
import com.idgi.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireDatabase implements IDatabase {
	private static volatile FireDatabase instance = null;
	private static Firebase ref = new Firebase("https://scorching-torch-4835.firebaseio.com");

	private List<School> schools;
	private List<User> users;

	public IQuiz getQuiz(String key) {
		return null;
	}

	/* Push (add) a school to Firebase */
	public void pushSchool(School school) {
		Firebase push = ref.child("schools").push();
		school.setKey(push.getKey());
		push.setValue(school);
	}

	public List<School> getSchools() {
		if (schools == null)
			schools = Collections.emptyList();

		return schools;
	}

	public List<User> getUsers() {
		if (users == null)
			users = Collections.emptyList();

		return users;
	}

	/* Push (add) an account to Firebase */
	public void pushAccount(Account account) {
		Firebase push = ref.child("accounts").push();

		account.setKey(push.getKey());
		push.setValue(account);

		pushAccountInfo(account, push.getKey());
	}

	private void pushAccountInfo(Account account, String key) {
		Firebase accountRef = ref.child("accountInfo").child(key);

		accountRef.child("accountName").setValue(account.getName());
		accountRef.child("email").setValue(account.getUser().getEmail());
	}

	public School getSchool(String schoolName) {
		return ModelUtility.findByName(schools, schoolName);
	}

	public List<Subject> getSubjects(School school) {
		return getSubjects(school.getName());
	}

	public List<Course> getCourses(String schoolName, String subjectName) {

		List<Course> courseList;
		if (getSchool(schoolName).getSubjects().size()>0){
			return getSchool(schoolName).getSubject(subjectName).getCourses();
		}
		else {
			courseList = new ArrayList<>();
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

	public List<School> createSchools() {
		MockData mock = MockData.getInstance();
		List<School> schools = new ArrayList<>();

		for (School school : mock.getSchools()) {
			for (Subject subject : mock.getSubjects(null)) {
				if (subject.getName().equals("Math"))
					for (Course course : mock.getCourses(null)) {
						for (Lesson lesson : mock.getLessons(null))
							course.addLesson(lesson);
						subject.addCourse(course);
					}

				school.addSubject(subject);
			}

			schools.add(school);
		}

		return schools;
	}

	public void pushMockDataToFirebase() {
		List<School> mockSchools = createSchools();
		for (School school : mockSchools)
			pushSchool(school);
	}

	/**
	 * Adds a lesson to a school
	 */
	public void pushLessonToSchool(Lesson lesson, String schoolKey, String subjectName, String courseName) {
		String path = String.format("schools/%s/subjects/%s/courses/%s/lessons/", schoolKey, subjectName, courseName);

		ref.child(path).setValue(lesson);
	}

	public void retrieveSchools() {
		if (Config.firebaseMode == Config.FirebaseMode.ACTIVE) {
			schools = new ArrayList<>();

			Firebase schoolRef = new Firebase("https://scorching-torch-4835.firebaseio.com/schools");

			schoolRef.addListenerForSingleValueEvent(new ValueEventListener() {
				public void onDataChange(DataSnapshot snapshot) {
					for (DataSnapshot child : snapshot.getChildren()) {
						School school = child.getValue(School.class);
						schools.add(school);
					}
				}

				public void onCancelled(FirebaseError firebaseError) {
				}
			});
		} else {
			schools = createSchools();
		}
	}

	public void retrieveUsers() {
		users = new ArrayList<>();

		Firebase userRef = ref.child("users");

		userRef.addListenerForSingleValueEvent(new ValueEventListener() {
			public void onDataChange(DataSnapshot snapshot) {

				for (DataSnapshot child : snapshot.getChildren()) {
					User user = child.getValue(User.class);
					users.add(user);
				}
			}

			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	public void initialize() {
		retrieveSchools();
	}


}
