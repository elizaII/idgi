package com.idgi;

import android.content.Intent;
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
	private LinearLayout buttonContainer;
	private Button btnNext;

	private static final int BUTTONS_PER_ROW = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		buttonContainer = (LinearLayout) findViewById(R.id.quiz_answer_container);

		txtQuestion = (TextView) findViewById(R.id.quiz_txt_question);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String quizKey = extras.getString("quiz_key");
			if (quizKey != null)
				quiz = Database.getInstance().getQuiz(quizKey);
		}

		btnNext = (Button) findViewById(R.id.quiz_btn_next_question);
		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeToNextQuestion();
			}
		});

		txtQuestion.setText(quiz.getCurrentQuestion().getText());
		createOptionButtons(quiz.getCurrentQuestion());
	}

	private void changeToNextQuestion() {
		quiz.nextQuestion();

		if (quiz.isFinished())
			startActivity(new Intent(this, StartActivity.class));
		else {
			txtQuestion.setText(quiz.getCurrentQuestion().getText());
			buttonContainer.removeAllViews();
			createOptionButtons(quiz.getCurrentQuestion());
			if (quiz.isLastQuestion())
				btnNext.setText(R.string.quiz_finish);
		}
	}

	private void createOptionButtons(Question question) {
		Set<Option> options = question.getOptions();

		LinearLayout buttonRow = createNewButtonRow();

		for (Option option : options) {
			buttonRow.addView(ButtonFactory.createAnswerButton(this, option));
			if (buttonRowIsFull(buttonRow)) {
				buttonContainer.addView(buttonRow);
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
