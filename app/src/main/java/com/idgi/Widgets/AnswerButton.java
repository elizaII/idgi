package com.idgi.Widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.core.Statistics;
import com.idgi.util.TransitionFactory;

public class AnswerButton extends Button {
	private static final int DEFAULT_LOOK = R.drawable.answer_button_normal;
	private static final int CORRECT_LOOK = R.drawable.answer_button_correct;

	//TODO change back to answer_button_incorrect
	private static final int INCORRECT_LOOK = R.drawable.answer_button_normal;

	private static final int DEFAULT_LOOK_SELECTED = R.drawable.answer_button_normal_selected;
	private static final int CORRECT_LOOK_SELECTED = R.drawable.answer_button_correct_selected;

	//TODO change back to answer_button_incorrect_selected
	private static final int INCORRECT_LOOK_SELECTED = R.drawable.answer_button_normal_selected;

	private static final int FADE_TIME_SELECT = 400;
	private static final int FADE_TIME_SHOW_ANSWER = 1000;

	public enum DisplayMode {
		NORMAL, SHOW_ANSWER
	}

	private DisplayMode displayMode = DisplayMode.NORMAL;

	private final static OnClickListener clickListener = new OnClickListener() {
		public void onClick(View view) {
			AnswerButton button = (AnswerButton) view;

			Answer answer = button.getAnswer();
			boolean selected = !answer.isSelected();

			answer.setSelected(selected);
			button.updateDrawable(FADE_TIME_SELECT);

		}
	};

	private Answer answer;

	public AnswerButton(Context context, AttributeSet attributeSet, int defStyleAttr, Answer answer) {
		super(context, attributeSet, defStyleAttr);
		this.answer = answer;

		this.setOnClickListener(clickListener);
	}

	public Answer getAnswer() {
		return this.answer;
	}

	private void updateDrawable(int fadeTime) {
		Drawable oldBackground = this.getBackground();
		Drawable newBackground = getResources().getDrawable(getDrawable());

		updateTextColor();

		TransitionDrawable background = TransitionFactory.createDrawableCrossFade(
				oldBackground, newBackground, fadeTime);

		this.setBackground(background);

		background.startTransition(500);
	}

	private void updateTextColor() {
		if (displayMode == DisplayMode.SHOW_ANSWER && answer.isCorrect())
			this.setTextColor(getResources().getColor(R.color.colorQuizQuestionText));
		else
			this.setTextColor(getResources().getColor(R.color.colorAnswerButtonBorder));
	}

	private int getDrawable() {
		int normal, correct, incorrect;

		if (answer.isSelected()) {
			normal = DEFAULT_LOOK_SELECTED;
			correct = CORRECT_LOOK_SELECTED;
			incorrect = INCORRECT_LOOK_SELECTED;
		} else {
			normal = DEFAULT_LOOK;
			correct = CORRECT_LOOK;
			incorrect = INCORRECT_LOOK;
		}

		if (displayMode == DisplayMode.SHOW_ANSWER)
			return answer.isCorrect() ? correct : incorrect;
		else
			return normal;
	}

	public void setDisplayMode(DisplayMode mode) {
		this.displayMode = mode;
		updateDrawable(FADE_TIME_SHOW_ANSWER);
	}
}
