package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Lesson implements Nameable {

	private String name;
	private Video video;
	private Discussion discussion = new Discussion();
	private IQuiz quiz;

	/** Required for JSON serialization */
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

	public Lesson withQuiz(IQuiz quiz) {
		setQuiz(quiz);
		return this;
	}

	public boolean hasQuiz() {
		return quiz != null;
	}

	public void setQuiz(IQuiz quiz) {
		this.quiz = quiz;
		quiz.setName(this.getName());
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

	/** Does nothing. Only here for JSON serialization. */
	public void setDiscussion(Discussion discussion) {
	}

	@JsonIgnore
	@Override
	public NameableType getType() {
		return NameableType.LESSON;
	}
}
