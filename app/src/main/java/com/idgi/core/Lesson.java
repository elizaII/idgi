package com.idgi.core;

import com.idgi.util.Nameable;

public class Lesson implements Nameable {

	private String name;
	private String id;
	private Video video;
	private Discussion discussion;
	private Quiz quiz;

	private Lesson() {}
	
	public Lesson(String name, Video video) {
		this.name = name;
		this.video = video;
	}

	public Lesson(String name) {
		this.name = name;
	}

	public static Lesson create(String name) {
		return new Lesson(name);
	}

	public Lesson withVideo(Video video) {
		this.video = video;
		return this;
	}

	public Lesson withQuiz(Quiz quiz) {
		setQuiz(quiz);
		return this;
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
