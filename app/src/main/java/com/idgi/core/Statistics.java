package com.idgi.core;

import java.util.HashMap;

public class Statistics {

	public enum Property {
		COMPLETED_COURSES, ONGOING_COURSES, COMPLETED_QUIZZES, SEEN_VIDEOS,
		COMMENTS, HATS
	}

	private HashMap<Property, Integer> map = new HashMap<>();

	public Statistics() {
		initialize();
	}

	private void initialize() {
		map.put(Property.COMPLETED_COURSES, 0);
		map.put(Property.ONGOING_COURSES, 0);
		map.put(Property.COMPLETED_QUIZZES, 0);
		map.put(Property.SEEN_VIDEOS, 0);
		map.put(Property.COMMENTS, 0);
		map.put(Property.HATS, 0);

	}

	private int completedCourses;
	private int currentCourses;
	private int completedQuizAmount;
	private int seenVideos;
	private int commentAmount;
	private int hatAmount;

	public int get(Property property) {
		return map.get(property);
	}
}
