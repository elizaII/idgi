package com.idgi.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.idgi.R;
import com.idgi.core.Answer;

public class AnswerButton extends Button {

	public enum Style {
		DEFAULT, CORRECT, INCORRECT, MISSED_CORRECT
	}

	public enum DisplayMode {
		NORMAL, HIGHLIGHT
	}

	private Answer answer;

	public AnswerButton(Context context, AttributeSet attributeSet, int defStyleAttr, Answer answer) {
		super(context, attributeSet, defStyleAttr);
		this.answer = answer;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public void setStyle(AnswerButton.Style style) {
		int resource = R.drawable.answer_button;

		switch (style) {
			case DEFAULT:
				break;
			case CORRECT:
				resource = R.drawable.answer_button_correct;
				break;
			case INCORRECT:
				resource = R.drawable.answer_button_incorrect;
				break;
			case MISSED_CORRECT:
				resource = R.drawable.answer_button_missed_correct;
				break;

		}

		this.setBackgroundResource(resource);
	}

	public void setDisplayMode(AnswerButton.DisplayMode mode) {
		switch (mode) {
			case NORMAL:
				setStyle(Style.DEFAULT);
				break;
			case HIGHLIGHT:
				if (answer.isSelected())
					setStyle(answer.isCorrect() ? Style.CORRECT : Style.INCORRECT);
				else
					if (answer.isCorrect())
						setStyle(Style.MISSED_CORRECT);
				break;
		}
	}
}
