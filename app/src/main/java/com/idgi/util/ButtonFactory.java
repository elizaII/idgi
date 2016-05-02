package com.idgi.util;

import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import com.idgi.R;
import com.idgi.Widgets.AnswerButton;
import com.idgi.core.Answer;

public class ButtonFactory {

	private static LinearLayout.LayoutParams buttonLayoutParams = null;

	public static AnswerButton createAnswerButton(final Context context, final Answer answer) {
		final AnswerButton button = new AnswerButton(new ContextThemeWrapper(context, R.style.quiz_answer_button), null, R.style.quiz_answer_button, answer);
		button.setText(answer.getText());

		button.setLayoutParams(getAnswerButtonLayout());

		if (Build.VERSION.SDK_INT > 21)
			button.setElevation(10);


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
					300,
					1.0f
			);

			buttonLayoutParams.setMargins(45, 45, 45, 45);
		}

		return buttonLayoutParams;
	}
}
