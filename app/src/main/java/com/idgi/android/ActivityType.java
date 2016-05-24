package com.idgi.android;

import java.io.Serializable;

/**
 * Used as an argument for EventBus to communicate that the activity should be started.
 */
@SuppressWarnings("serial")
public enum ActivityType implements Serializable {
	START,
	PROFILE,
	STATISTICS,
	MY_COURSES,
	QUIZ,
	BROWSE_USERS,
	LOGIN,
	COURSE,
	CREATE_ACCOUNT,
	SCHOOL_LIST,
	SUBJECT_LIST,
	COURSE_LIST,
	LESSON
}
