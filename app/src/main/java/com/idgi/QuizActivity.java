package com.idgi;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idgi.util.Storage;
import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.services.Database;
import com.idgi.util.ButtonFactory;
import com.idgi.widgets.AnswerButton;

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

		loadQuiz();

		initializeNextButton();
		initializeQuestionView();

		createAnswerButtons(quiz.getCurrentQuestion());
	}

	private void initializeNextButton() {
		btnNext = (Button) findViewById(R.id.quiz_btn_next_question);
		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeToNextQuestion();
			}
		});
	}

	private void loadQuiz() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String quizKey = extras.getString("quiz_key");
			if (quizKey != null)
				quiz = Database.getInstance().getQuiz(quizKey);
			else
				throw new NullPointerException("A Quiz must be passed as an extra with key 'quiz_key' from the previous activity.");
		}
	}

	private void initializeQuestionView() {
		txtQuestion = (TextView) findViewById(R.id.quiz_txt_question);

		txtQuestion.setText(quiz.getCurrentQuestion().getText());
		txtQuestion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = quiz.getCurrentQuestion().getText();
				String hint = quiz.getCurrentQuestion().getHint();

				String newText = txtQuestion.getText().equals(text) ? hint : text;
				txtQuestion.setText(newText);
			}
		});
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
					switchToQuizResultActivity();
				else {
					showQuestion(quiz.getCurrentQuestion());
				}
			}
		};

		timer.start();
	}

	public void switchToQuizResultActivity() {
		Storage.setCurrentQuiz(quiz);
		startActivity(new Intent(QuizActivity.this, QuizResultActivity.class));
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
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		row.setLayoutParams(params);

		row.setOrientation(LinearLayout.HORIZONTAL);

		return row;
	}
}
