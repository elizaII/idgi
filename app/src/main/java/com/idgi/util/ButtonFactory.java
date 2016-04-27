package com.idgi.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idgi.core.Option;

/**
 * Created by Jonathan Krän on 26/04/2016.
 */
public class ButtonFactory {

	private static LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT,
			1.0f
	);

	public static Button createAnswerButton(final Context context, Option option) {
		Button button = new Button(context);
		button.setText(option.getText());
		button.setHeight(80);

		button.setLayoutParams(buttonLayoutParams);


		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
			}
		});

		return button;
	}
}
