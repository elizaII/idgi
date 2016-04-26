package com.idgi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class QuizActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		LinearLayout container = (LinearLayout) findViewById(R.id.answer_container);

		LinearLayout row = new LinearLayout(getBaseContext());
		row.setOrientation(LinearLayout.HORIZONTAL);

		row.addView(new Button(getBaseContext()));

		container.addView(row);
	}
}
