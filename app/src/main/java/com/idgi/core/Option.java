package com.idgi.core;

public class Option {

	private String text;
	private boolean isSelected = false;
	private boolean isCorrect = false;
	
	public Option(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void select() {
		this.isSelected = true;
	}
	
	public void deselect() {
		this.isSelected = false;
	}
	
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if (other.getClass() != this.getClass()) return false;
		
		Option that = (Option) other;
		
		return this.text.equals(that.text) && this.isSelected() == that.isSelected();
	}
	
	public boolean isSelectedAndIncorrect() {
		return this.isSelected && !this.isCorrect;
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
