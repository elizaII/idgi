package com.idgi.util;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import com.idgi.R;
import com.idgi.widgets.AnswerButton;
import com.idgi.core.Answer;

public class ButtonFactory {

	private static LinearLayout.LayoutParams buttonLayoutParams = null;

	public static AnswerButton createAnswerButton(final Context context, final Answer answer) {
		final AnswerButton button = new AnswerButton(new ContextThemeWrapper(context, R.style.quiz_answer_button), null, R.style.quiz_answer_button, answer);
		button.setText(answer.getText());
		button.setHeight(80);

		button.setLayoutParams(getAnswerButtonLayout());


		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean selected = !answer.isSelected();

				answer.setSelected(selected);
				v.setSelected(selected);
			}
		});

		return button;
	}

	private static LinearLayout.LayoutParams getAnswerButtonLayout() {
		if (buttonLayoutParams == null) {
			buttonLayoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT,
					0.8f
			);

			buttonLayoutParams.setMargins(5, 5, 5, 5);
		}

		return buttonLayoutParams;
	}
}
