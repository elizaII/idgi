package com.idgi;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idgi.widgets.AnswerButton;
import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.services.Database;
import com.idgi.util.ButtonFactory;

import java.util.Set;

public class QuizActivity extends AppCompatActivity {

	private Quiz quiz;
	private TextView txtQuestion;
	private LinearLayout buttonContainer;
	private Button btnNext;
	private AnswerButton[] answerButtons;

	private boolean showingCorrectAnswers = false;

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
		createAnswerButtons(quiz.getCurrentQuestion());
	}

	private void changeToNextQuestion() {
		CountDownTimer timer = new CountDownTimer(3000, 500) {
			@Override
			public void onTick(long millisUntilFinished) {
				flashCorrectAnswers();
			}

			@Override
			public void onFinish() {
				quiz.nextQuestion();

				if (quiz.isFinished())
					startActivity(new Intent(QuizActivity.this, StartActivity.class));
				else {
					showQuestion(quiz.getCurrentQuestion());
				}
			}
		};

		timer.start();
	}

	private void flashCorrectAnswers() {
		for (AnswerButton answerButton : answerButtons)
			answerButton.setDisplayMode(showingCorrectAnswers ? AnswerButton.DisplayMode.HIGHLIGHT : AnswerButton.DisplayMode.NORMAL);

		showingCorrectAnswers = !showingCorrectAnswers;
	}

	private void showQuestion(Question question) {
		txtQuestion.setText(question.getText());
		buttonContainer.removeAllViews();
		showingCorrectAnswers = false;

		createAnswerButtons(question);
		if (quiz.isLastQuestion())
			btnNext.setText(R.string.quiz_finish);
	}

	private void createAnswerButtons(Question question) {
		Set<Answer> answers = question.getAnswers();

		answerButtons = new AnswerButton[answers.size()];

		LinearLayout buttonRow = createNewButtonRow();

		int i = 0;

		for (Answer answer : answers) {
			AnswerButton answerButton = ButtonFactory.createAnswerButton(this, answer);

			answerButtons[i++] = answerButton;
			buttonRow.addView(answerButton);

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
