package com.idgi.util;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idgi.R;
import com.idgi.core.Option;

public class ButtonFactory {

	private static LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT,
			1.0f
	);

	public static Button createAnswerButton(final Context context, final Option option) {
		final Button button = new Button(new ContextThemeWrapper(context, R.style.quiz_answer_button), null, R.style.quiz_answer_button);
		//Button button = new Button(context);
		//button.setBackgroundResource(R.drawable.answer_button_enabled);
		button.setText(option.getText());
		button.setHeight(80);

		button.setLayoutParams(buttonLayoutParams);


		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean selected = !option.isSelected();

				option.setSelected(selected);
				v.setSelected(selected);

				//Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
			}
		});

		return button;
	}
}
