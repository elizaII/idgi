package com.idgi.core;

import com.idgi.util.Nameable;

public class Lesson implements Nameable {

	private String name;
	private String id;
	private Video video;
	private Discussion discussion;
	private IQuiz quiz;

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
	
	public void setQuiz(IQuiz quiz) {
		this.quiz = quiz;
	}

	public IQuiz getQuiz() {
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

	public Discussion getDiscussion() {
		return this.discussion;
	}

	/* Is likely to not have functionality. Here to please Firebase */
	public void setDiscussion(Discussion discussion) {
	}
}
