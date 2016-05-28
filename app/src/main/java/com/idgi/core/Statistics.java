package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idgi.Config;

import java.util.HashMap;
import java.util.Locale;

public class Statistics {

	public enum Property {
		COMPLETED_COURSES, ONGOING_COURSES, COMPLETED_QUIZZES, SEEN_VIDEOS,
		COMMENTS, HATS, POINTS
	}

	private int totalPoints;

	private HashMap<String, Integer> quizPoints = new HashMap<>();

	private HashMap<String, Integer> videoPoints = new HashMap<>();

	private HashMap<Property, Integer> propertyMap = new HashMap<>();

	public Statistics() {
		initialize();
	}

	private void initialize() {
		for (Property property : Property.values())
			propertyMap.put(property, 0);

		totalPoints = 0;
	}

	@JsonIgnore
	public int get(Property property) {
		switch (property) {
			case POINTS:
				return totalPoints;
			default:
			return propertyMap.get(property);
		}
	}

	private void incrementSeenVideos() {
		propertyMap.put(Property.SEEN_VIDEOS, propertyMap.get(Property.SEEN_VIDEOS) + 1);
	}

	private void incrementCompletedQuizzes() {
		propertyMap.put(Property.COMPLETED_QUIZZES, propertyMap.get(Property.COMPLETED_QUIZZES) + 1);
	}

	//Updates the score for a quiz. If totalPoints are a new best score, updates user's total totalPoints.
	public void updateQuizPoints(String quizID, int newScore) {
		Integer oldScore = quizPoints.get(quizID);

		if (!quizPoints.containsKey(quizID))
			incrementCompletedQuizzes();

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

	public void increment(Property property) {
		propertyMap.put(property, propertyMap.get(property) + 1);
	}

	public void decrement(Property property) {
		int current = propertyMap.get(property);
		if (current <= 0)
			throw new IllegalStateException(String.format(Locale.ENGLISH, "Amount of %s must be >= 0.", property.toString()));

		propertyMap.put(property, current - 1);
	}

	private void addPoints(int amount) {
		if (amount < 0)
			throw new IllegalArgumentException("Can not remove points.");

		totalPoints += amount;
	}

	public void addVideoPoints(Video video, int newPoints) {
		if (newPoints < 0)
			throw new IllegalArgumentException("Adding a negative amount of points is prohibited.");

		Integer currentPoints = getPointsEarnedForVideo(video);

		if (currentPoints == null) {
			incrementSeenVideos();
			addViewedVideo(video);
			currentPoints = 0;
		}
		int points = Math.min(Config.MAX_POINTS_FOR_VIDEO, currentPoints + newPoints);

		setPointsForViewedVideo(video, points);
	}

	@JsonIgnore
	public int getVideoPoints(Video video) {
		Integer points = videoPoints.get(video.getUrl());
		return points != null ? points : 0;
	}

	private void addViewedVideo(Video video) {
		videoPoints.put(video.getUrl(), 0);
	}

	@JsonIgnore
	private Integer getPointsEarnedForVideo(Video video) {
		return videoPoints.get(video.getUrl());
	}

	@JsonIgnore
	private void setPointsForViewedVideo(Video video, int points) {
		int pointsBefore = getPointsEarnedForVideo(video);
		videoPoints.put(video.getUrl(), points);

		addPoints(points - pointsBefore);
	}


	/* UNDER THIS: Getters and setters required for JSON serialization */

	/** Only for JSON serialization. Do not use. */
	public HashMap<String, Integer> getQuizPoints() {
		return quizPoints;
	}

	/** Only for JSON serialization. Do not use. */
	public void setQuizPoints(HashMap<String, Integer> quizPoints) {
		this.quizPoints = quizPoints;
	}

	/** Only for JSON serialization. Do not use. */
	public HashMap<String, Integer> getVideoPoints() {
		return videoPoints;
	}

	/** Only for JSON serialization. Do not use. */
	public void setVideoPoints(HashMap<String, Integer> videoPoints) {
		this.videoPoints = videoPoints;
	}

	/** Only for JSON serialization. Do not use. */
	public HashMap<Property, Integer> getPropertyMap() {
		return propertyMap;
	}

	/** Only for JSON serialization. Do not use. */
	public void setPropertyMap(HashMap<Property, Integer> propertyMap) {
		this.propertyMap = propertyMap;
	}

	/** Only for JSON serialization. Do not use. */
	public int getTotalPoints() {
		return totalPoints;
	}

	/** Only for JSON serialization. Do not use. */
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
}
