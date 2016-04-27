package com.idgi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idgi.core.Option;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.services.Database;
import com.idgi.util.ButtonFactory;

import java.util.Iterator;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

	private Quiz quiz;
	private TextView txtQuestion;

	private static final int BUTTONS_PER_ROW = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		txtQuestion = (TextView) findViewById(R.id.txtQuestion);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String quizKey = extras.getString("quiz_key");
			if (quizKey != null)
				quiz = Database.getInstance().getQuiz(quizKey);
		}

		createOptionButtons();
	}

	private void nextQuestion() {
		quiz.nextQuestion();
		txtQuestion.setText(quiz.getCurrentQuestion().getText());
	}

	private void createOptionButtons() {
		LinearLayout container = (LinearLayout) findViewById(R.id.answer_container);

		Set<Option> options = quiz.getCurrentQuestion().getOptions();

		LinearLayout buttonRow = createNewButtonRow();

		for (Option option : options) {
			buttonRow.addView(ButtonFactory.createAnswerButton(this, option));

			if (buttonRowIsFull(buttonRow)) {
				container.addView(buttonRow);
				buttonRow = createNewButtonRow();
			}
		}
	}

	private boolean buttonRowIsFull(LinearLayout layout) {
		return layout.getChildCount() == BUTTONS_PER_ROW;
	}

	private LinearLayout createNewButtonRow() {
		LinearLayout row = new LinearLayout(this);
		row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		row.setOrientation(LinearLayout.HORIZONTAL);

		return row;
	}
}
