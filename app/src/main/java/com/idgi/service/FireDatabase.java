package com.idgi.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.eventbus.Subscribe;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.idgi.event.ApplicationBus;
import com.idgi.session.SessionData;
import com.idgi.util.ImageUtility;
import com.idgi.R;
import com.idgi.Config;
import com.idgi.core.Account;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.Hat;
import com.idgi.core.Lesson;
import com.idgi.core.ModelUtility;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.core.Student;
import com.idgi.core.Subject;
import com.idgi.core.User;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/*
The currently used database implementation. Data is stored on a remote server as JSON.
 */
public class FireDatabase implements IDatabase {
	private static volatile FireDatabase instance = null;
	private static Firebase ref = new Firebase("https://scorching-torch-4835.firebaseio.com");
	private FirebaseStorage storage;
	StorageReference storageRef;

	private List<School> schools;
    private List<Account> accounts;
	private List<Hat> hats;

	private List<String> schoolsIssuedForUpdateByKey = new ArrayList<>();

	private FireDatabase() {
		storage = FirebaseStorage.getInstance();
		storageRef = storage.getReference();

		ApplicationBus.register(this);
	}

	/* Push (add) a school to Firebase */
	public void pushSchool(School school) {
		Firebase push = ref.child("schools").push();
		school.setKey(push.getKey());
		push.setValue(school);
	}

	public void pushHat(Hat hat) {
		Firebase push = ref.child("hats").child(hat.getName());
		hat.setKey(push.getKey());
		push.setValue(hat);
	}

	public List<Hat> getHats() {
		if (hats == null)
			hats = Collections.emptyList();
		return hats;
	}

	public List<School> getSchools() {
		if (schools == null)
			schools = Collections.emptyList();

		return schools;
	}

    public List<Account> getAccounts() {
        if (accounts == null)
            accounts = Collections.emptyList();

        return accounts;
    }

	public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (Account account : accounts) {
            users.add(account.getUser());
        }

		return users;
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

    public void pushHatsToFirebase() {
        MockData mock = MockData.getInstance();

        for (Hat hat : mock.getHats()) {
            pushHat(hat);
        }
    }

	public void pushMockDataToFirebase() {
		List<School> mockSchools = MockData.getInstance().createSchools();
		for (School school : mockSchools)
			pushSchool(school);
	}

	/**
	 * Adds a lesson to a school
	 * Throws IllegalArgumentException if there is no school with the given schoolKey
	 */
	public void pushLessonToSchool(School school, Subject subject) {
		if (school == null)
			throw new IllegalArgumentException();

		int subjectIndex = findIndexForNameableByName(school.getSubjects(), subject.getName());
		String path = String.format(Locale.ENGLISH, "schools/%s/subjects/%d", school.getKey(), subjectIndex);

		ref.child(path).setValue(subject);
		requestSchoolUpdate(school.getKey());
	}

	/** Returns the index of the first Nameable in the list with the given name.
	 * If no Nameable with given name was found, returns -1*/
	private int getIndexByName(List<? extends Nameable> list, String name) {
		for (int i = 0; i < list.size(); ++i)
			if (name.equals(list.get(i).getName()))
				return i;

		return -1;
	}

	/** Return the index for a nameable. Returns the size of the list if nameable is not in list */
	private int findIndexForNameableByName(List<? extends Nameable> list, String name) {
		int indexInList = getIndexByName(list, name);

		return indexInList != -1 ? indexInList : list.size();
	}

	/** Lets the listener for changes know which schools have been changed since last pull */
	private void requestSchoolUpdate(String schoolKey) {
		schoolsIssuedForUpdateByKey.add(schoolKey);
	}

	public void saveProfilePicture(Account account) {
		User user = account.getUser();
		Bitmap bitmap = user.getProfilePicture();
		String path = String.format(Locale.ENGLISH, "images/%s", account.getKey());
		StorageReference ref = storageRef.child(path);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] data = baos.toByteArray();



		UploadTask uploadTask = ref.putBytes(data);
		uploadTask.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
			}
		}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
			}
		});
	}

	public void downloadProfilePicture(Context context, final Account account) {
		String key = account.getKey();
		String path = String.format(Locale.ENGLISH, "images/%s", key);

		final Bitmap defaultImage = ImageUtility.drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_timeline_black_24dp));

		storageRef.child(path).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
			@Override
			public void onSuccess(byte[] bytes) {
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
				setProfilePicture(account.getUser(), BitmapFactory.decodeStream(stream));
			}
			}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
				setProfilePicture(account.getUser(), defaultImage);
			}
		});
	}

	private void setProfilePicture(User user, Bitmap picture) {
		user.setProfilePicture(ImageUtility.scaleBitmapAndKeepRatio(picture, 32, 32));
	}

	public void retrieveSchools(boolean isOfflineMode) {
		if (isOfflineMode) {
			schools = MockData.getInstance().createSchools();
		} else {
			schools = new ArrayList<>();

			ref.child("schools").addValueEventListener(new com.firebase.client.ValueEventListener() {
				@Override
				public void onDataChange(com.firebase.client.DataSnapshot snapshot) {
					for (com.firebase.client.DataSnapshot child : snapshot.getChildren()) {
						String schoolKey = child.getKey();
						if (isNewSchool(schoolKey)) {
							School newSchool = child.getValue(School.class);
							addNewSchool(newSchool);
						} else if (schoolsIssuedForUpdateByKey.contains(schoolKey)) {
							School updatedSchool = child.getValue(School.class);
							replaceSchool(schoolKey, updatedSchool);
						}
					}
				}

				@Override
				public void onCancelled(FirebaseError error) {
				}
			});
		}
	}

	public User getUserByAccountName(String name) {
		for (Account account : accounts)
			if (account.getName().equals(name))
				return account.getUser();

		return null;
	}

	public void updateCurrentLesson() {
		School school = SessionData.getCurrentSchool();
		Subject subject = SessionData.getCurrentSubject();
		Course course = SessionData.getCurrentCourse();
		String schoolKey = school.getKey();
		int subjectID = getIndexByName(school.getSubjects(), subject.getName());
		int courseID = getIndexByName(subject.getCourses(), course.getName());
		int lessonID = getIndexByName(course.getLessons(), SessionData.getCurrentLesson().getName());


		String path = String.format(Locale.ENGLISH, "schools/%s/subjects/%d/courses/%d/lessons/%d",
				schoolKey, subjectID, courseID, lessonID);

		Firebase lessonRef = ref.child(path);
		lessonRef.setValue(SessionData.getCurrentLesson());
	}

	private void addNewSchool(School school) {
		schools.add(school);
	}

	private boolean isNewSchool(String schoolKey) {
		return findSchoolIndexByKey(schoolKey) == -1;
	}

	/** Replace the school with given key with a new School.
	 * Should only be used to update a school.
	 * Throws IllegalArgumentException if there is no school with the given key in schools.*/
	private void replaceSchool(String key, School newSchool) {
		int index = findSchoolIndexByKey(key);

		if (index == -1)
			throw new IllegalArgumentException(String.format(Locale.ENGLISH, "There is no school with key %s", key));

		schoolsIssuedForUpdateByKey.remove(key);
		schools.set(index, newSchool);
	}

	/** Returns the index of a School with the given key. If no matching key was found, returns -1*/
	private int findSchoolIndexByKey(String key) {
		for (int i = 0; i < schools.size(); ++i)
			if (key.equals(schools.get(i).getKey()))
				return i;

		return -1;
	}

    public void retrieveAccounts() {
        accounts = new ArrayList<>();

		Firebase accountRef = ref.child("accounts");

        accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {
                    Account account = child.getValue(Account.class);
                    accounts.add(account);
                }
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

	public void initialize(boolean hasInternetConnection) {
		boolean isOfflineMode = !hasInternetConnection || Config.firebaseMode == Config.FirebaseMode.INACTIVE;
		retrieveSchools(isOfflineMode);
		retrieveHats(isOfflineMode);
        retrieveAccounts();
	}

    public void retrieveHats(boolean isOfflineMode) {
        if (!isOfflineMode) {
            hats = new ArrayList<>();

			Firebase hatRef = ref.child("hats");

            hatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Hat hat = child.getValue(Hat.class);
                        hats.add(hat);
                    }
                }

                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        } else {
            MockData mock = MockData.getInstance();
            hats = mock.getHats();
        }
    }

	@Subscribe
	public void updateUserHats(BusEvent busEvent) {
		if (busEvent.getEvent() == Event.POINTS_UPDATED) {
			Student user = (Student) busEvent.getData();

			List<Hat> earnedHats = new ArrayList<>();
			for (Hat hat : hats)
				if (user.getPoints() >= hat.pointRequirement()) {
					earnedHats.add(hat);
					System.out.println("You get a hat!");
				}

			user.giveHats(earnedHats);
		}
	}

	public Account getAccount(String accountName, String password) {
		for (Account account : accounts)
			if (accountName.equals(account.getName()) || accountName.equals(account.getEmail()))
				if (password.equals(account.getPassword()))
					return account;

		return null;
	}

	private boolean accountExists(Account newAccount) {
		for (Account account : accounts)
			if(account.equals(newAccount))
				return true;

		return false;
	}

	/* Push (add) an account to Firebase */
	public void pushAccount(Account account) {
		if (accountExists(account)) {
			String path = String.format(Locale.ENGLISH, "accounts/%s", account.getKey());
			ref.child(path).setValue(account);
		} else {
			Firebase push = ref.child("accounts").push();

			account.setKey(push.getKey());
			push.setValue(account);
		}
	}
}
