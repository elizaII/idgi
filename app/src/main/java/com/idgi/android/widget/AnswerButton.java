package com.idgi.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import com.idgi.R;
import com.idgi.core.Answer;

/*
Custom button used to display the Answers to a quiz as the quiz is being taken.
 */
public class AnswerButton extends Button {
	private static final int DEFAULT_LOOK = R.drawable.answer_button_normal;
	private static final int CORRECT_LOOK = R.drawable.answer_button_correct;

	private static final int DEFAULT_LOOK_SELECTED = R.drawable.answer_button_normal_selected;
	private static final int CORRECT_LOOK_SELECTED = R.drawable.answer_button_correct_selected;

	public static final int SELECTION_FADE_TIME = 400;
	private static final int FADE_TIME_SHOW_ANSWER = 1000;

	public enum DisplayMode {
		NORMAL, SHOW_ANSWER
	}

	private DisplayMode displayMode = DisplayMode.NORMAL;
	private Answer answer;

	// Required for android:tools in layout files
	private AnswerButton(Context context) {
		super(context);
	}

	public AnswerButton(Context context, AttributeSet attributeSet, int defStyleAttr, Answer answer) {
		super(context, attributeSet, defStyleAttr);
		this.answer = answer;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public void setDisplayMode(DisplayMode mode) {
		this.displayMode = mode;
		updateDrawable(FADE_TIME_SHOW_ANSWER);
	}

	public void updateDrawable(int fadeTime) {
		Drawable oldBackground = this.getBackground();
		Drawable newBackground = ContextCompat.getDrawable(getContext(), getDrawable());

		updateTextColor();
		playTransition(oldBackground, newBackground, fadeTime);
	}

	private void playTransition(Drawable from, Drawable to, int fadeTime) {
		Drawable drawables[] = {from, to};
		TransitionDrawable transitionBackground = new TransitionDrawable(drawables);

		this.setBackground(transitionBackground);

		transitionBackground.startTransition(fadeTime);
	}

	private void updateTextColor() {
		if (displayMode == DisplayMode.SHOW_ANSWER && answer.isCorrect())
			this.setTextColor(ContextCompat.getColor(getContext(), R.color.colorQuizQuestionText));
		else
			this.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAnswerButtonBorder));
	}

	private int getDrawable() {
		int normal, correct;

		if (answer.isSelected()) {
			normal = DEFAULT_LOOK_SELECTED;
			correct = CORRECT_LOOK_SELECTED;
		} else {
			normal = DEFAULT_LOOK;
			correct = CORRECT_LOOK;
		}

		return displayMode == DisplayMode.SHOW_ANSWER && answer.isCorrect() ? correct : normal;
	}
}
