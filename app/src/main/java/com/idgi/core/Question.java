package com.idgi.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Question {

	private static final String NO_HINTS_MESSAGE = "There are no hints for this question.";
	
	private String text;
	private String hint;
	private Set<Option> options;
	
	public Question(String text, String hint, Set<Option> options) {
		this.text = text;
		this.hint = hint;
		this.options = options;
	}
	
	public Question(String text, String hint) {
		this(text, hint, new HashSet<Option>());
	}
	
	public void addOptions(Option... options) {
		this.options.addAll(Arrays.asList(options));
	}
	
	public boolean isCorrectlyAnswered() {
		for (Option option : options)
			if (option.isSelectedAndIncorrect() || option.isDeselectedAndCorrect()) //kollar inte om man har missat ett rätt svar.
				return false;
		
		return true;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public int getOptionAmount() {
		return options.size();
	}
	
	public Set<Option> getMistakes() {
		Set<Option> mistakes = new HashSet<>();

		for (Option option : options)
		if (option.isSelectedAndIncorrect())
			mistakes.add(option);
		
		return mistakes;
	}

	public Question(String text) {
		this(text, NO_HINTS_MESSAGE, new HashSet<Option>());
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getHint() {
		return this.hint;
	}
	
	public void addOption(Option option) {
		options.add(option);
	}
	
	public boolean hasOption(Option option) {
		return options.contains(option);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if (other.getClass() != this.getClass()) return false;
		
		Question that = (Question) other;
		
		if ( !(this.text.equals(that.text) && this.hint.equals(that.hint)) )
			return false;
		
		for (Option option : options)
			if (!that.hasOption(option))
				return false;
		
		return true;
	}
}
