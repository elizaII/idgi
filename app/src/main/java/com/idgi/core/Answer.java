package com.idgi.core;

public class Answer {

	private String text;
	private boolean isSelected = false;
	private boolean isCorrect = false;
	
	public Answer(String text) {
		this.text = text;
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
	
	public boolean isSelectedAndIncorrect() {
		return this.isSelected && !this.isCorrect;
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

	public boolean isDeselectedAndCorrect() {
		return !this.isSelected && this.isCorrect;
	}
}
