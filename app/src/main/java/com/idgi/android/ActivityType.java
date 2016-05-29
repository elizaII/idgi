package com.idgi.android;


/*
 * Used as an argument for EventBus to communicate that the activity should be started.
 */
@SuppressWarnings("serial")
public enum ActivityType {
	START,
	PROFILE,
	STATISTICS,
	MY_COURSES,
	QUIZ,
	BROWSE_USERS,
	LOGIN,
	CREATE_ACCOUNT,
	SCHOOL_LIST,
	SUBJECT_LIST,
}
