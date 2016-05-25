package com.idgi.android.widget;

import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;

import com.idgi.R;
import com.idgi.android.widget.AnswerButton;
import com.idgi.core.Answer;

/*
Encapsulates the AnswerButton creation logic
 */
public class AnswerButtonFactory {

	private static LinearLayout.LayoutParams buttonLayoutParams = null;

	public static AnswerButton createButton(final Context context, final Answer answer) {
		final AnswerButton button = new AnswerButton(new ContextThemeWrapper(context, R.style.quiz_answer_button), null, R.style.quiz_answer_button, answer);
		button.setText(answer.getText());
		button.setLayoutParams(getAnswerButtonLayout());

		if (Build.VERSION.SDK_INT > 21)
			button.setElevation(10);

		return button;
	}

	private static synchronized LinearLayout.LayoutParams getAnswerButtonLayout() {
		if (buttonLayoutParams == null) {
			buttonLayoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					300,
					1.0f
			);

			buttonLayoutParams.setMargins(20, 20, 20, 20);
		}

		return buttonLayoutParams;
	}
}
