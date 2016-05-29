package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/* Answers are components of the quizzes */

@JsonIgnoreProperties({"selectedAndIncorrect", "deselectedAndCorrect"})
public class Answer {

	private String text;
	private boolean isSelected = false;
	private boolean isCorrect = false;

	//For deserializing
	private Answer() {}

	public Answer(String text) {
		this.text = text;
	}

	public static Answer correct(String text) {
		Answer answer = new Answer(text);
		answer.setCorrect(true);
		return answer;
	}

	public static Answer incorrect(String text) {
		return new Answer(text);
	}

	public String getText() {
		return this.text;
	}

	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	public void setCorrect(boolean correct) {
		this.isCorrect = correct;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if (other.getClass() != this.getClass()) return false;

		Answer that = (Answer) other;

		return this.text.equals(that.text) && this.isSelected() == that.isSelected();
	}

	@JsonIgnore
	public boolean isSelectedAndIncorrect() {
		return this.isSelected && !this.isCorrect;
	}

	@JsonIgnore
	public boolean isDeselectedAndCorrect() {
		return !this.isSelected && this.isCorrect;
	}

	public boolean isCorrect() {
		return this.isCorrect;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.isSelected ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
}
