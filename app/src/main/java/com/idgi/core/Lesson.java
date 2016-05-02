package com.idgi.core;

public class Lesson {

	private String name;
	private String id;
	private Video video;
	private Discussion discussion;
	private Quiz quiz;
	
	public Lesson(String name, Video video) {
		this.name = name;
		this.video = video;
	}
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public Video getVideo(){
		return video;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
