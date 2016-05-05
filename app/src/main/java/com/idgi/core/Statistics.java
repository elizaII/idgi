package com.idgi.core;

import java.util.HashMap;

public class Statistics {

	public enum Property {
		COMPLETED_COURSES, ONGOING_COURSES, COMPLETED_QUIZZES, SEEN_VIDEOS,
		COMMENTS, HATS, POINTS
	}

	private int totalPoints;

	private HashMap<Integer, Integer> quizPoints = new HashMap<>();

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

		totalPoints = 0;
	}

	public int get(Property property) {
		switch (property) {
			case POINTS:
				return totalPoints;
			default:
			return map.get(property);
		}
	}

	//Updates the score for a quiz. If totalPoints are a new best score, updates user's total totalPoints.
	public void updateQuizPoints(int quizID, int newScore) {
		Integer oldScore = quizPoints.get(quizID);

		if (oldScore == null) {
			quizPoints.put(quizID, 0);
			oldScore = 0;
		}

		if (oldScore < newScore) {
			int diff = newScore - oldScore;
			quizPoints.put(quizID, newScore);
			addPoints(diff);
		}
	}

	private void addPoints(int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Can not remove points.");

		totalPoints += amount;
	}
}
