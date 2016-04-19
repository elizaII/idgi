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
	
	public void setQuiz() {
		
	}
}
