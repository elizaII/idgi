package com.idgi.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.idgi.R;
import com.idgi.core.IQuiz;
import com.idgi.core.TimedQuiz;
import com.idgi.Config;
import com.idgi.session.SessionData;
import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.activities.extras.AnswerButtonFactory;
import com.idgi.Widgets.AnswerButton;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

	private IQuiz quiz;
	private TextView txtQuestion;
	private LinearLayout buttonContainer;
	private Button btnNext;
	private AnswerButton[] answerButtons;
    private ProgressBar timeProgressBar;

	private CountDownTimer timer;

	private boolean isInTransition = false;

	//To stop weird transitions after we've already exited the activity
	private boolean leftActivity = false;

	private static final int BUTTONS_PER_ROW = 2;

    private final String ACTIVITY_TAG ="QUIZ_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		buttonContainer = (LinearLayout) findViewById(R.id.quiz_answer_container);

		quiz = SessionData.getCurrentQuiz();
		if (quiz != null) {
			quiz.reset();
			initializeNextButton();
			initializeQuestionView();
			if(quiz instanceof TimedQuiz){
				Log.d(ACTIVITY_TAG, "Is timed quiz");
				initializeTimeBar((TimedQuiz) quiz);
			} else {
				Log.d(ACTIVITY_TAG, "Is not timed quiz");
			}
			createAnswerButtons(quiz.getCurrentQuestion());
		} else {
			finish();
		}
	}

	//Todo... Refactor this huge method
	private void initializeTimeBar(final TimedQuiz timedQuiz) {
        timeProgressBar = (ProgressBar) findViewById(R.id.content_quiz_time_progress);
		if (timeProgressBar != null)
			timeProgressBar.setVisibility(View.VISIBLE);


        final int MAX_TIME = timedQuiz.getTime();
        Log.d(ACTIVITY_TAG, "Timed quiz's time: " + MAX_TIME);
        timeProgressBar.setMax(MAX_TIME);

        timer = new CountDownTimer(MAX_TIME, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeProgressBar.setProgress(MAX_TIME - (int)millisUntilFinished);
            }

            @Override
            public void onFinish() {
				//Todo... change the time unit
				if((MAX_TIME - timeProgressBar.getProgress()) <= 10){
					Toast.makeText(getBaseContext(), getString(R.string.quiz_out_of_time), Toast.LENGTH_LONG).show();
					Log.d(ACTIVITY_TAG, "You failed!");
				}
				switchToQuizResultActivity();
				timedQuiz.setRemainingTime(MAX_TIME - timeProgressBar.getProgress());
				Log.d(ACTIVITY_TAG, "Remaining time: " + (MAX_TIME - timeProgressBar.getProgress()));
            }
        };

        timer.start();
    }

	private void initializeNextButton() {
		btnNext = (Button) findViewById(R.id.quiz_btn_next_question);
		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!isInTransition())
					finishQuestion();
				else
					gotoNextQuestion();
			}
		});
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


	private void finishQuestion() {
		showCorrectAnswers();
		setInTransition(true);

		final Question finishedQuestion = quiz.getCurrentQuestion();

		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			public void run() {
				if (finishedQuestion.equals(quiz.getCurrentQuestion()) && !leftActivity)
					gotoNextQuestion();
			}
		}, Config.TIME_TO_DISPLAY_QUIZ_ANSWERS);
	}

	private void gotoNextQuestion() {
		quiz.nextQuestion();

		if (quiz.isFinished())
			if(quiz instanceof TimedQuiz) {
				timer.onFinish();
				timer.cancel();
			} else
				switchToQuizResultActivity();
		else
			showQuestion(quiz.getCurrentQuestion());

		setInTransition(false);
	}

	public void switchToQuizResultActivity() {
		leftActivity = true;
		startActivity(new Intent(this, QuizResultActivity.class));
		finish();
	}

	private void showCorrectAnswers() {
		for (AnswerButton answerButton : answerButtons)
			answerButton.setDisplayMode(AnswerButton.DisplayMode.SHOW_ANSWER);
	}

	private void showQuestion(Question question) {
		txtQuestion.setText(question.getText());

		createAnswerButtons(question);

		updateNextButton();
	}

	private void updateNextButton() {
		if (quiz.isLastQuestion())
			btnNext.setText(R.string.quiz_finish);
	}

	private void createAnswerButtons(Question question) {
		buttonContainer.removeAllViews();

		List<Answer> answers = question.getAnswers();
		answerButtons = new AnswerButton[answers.size()];

		LinearLayout buttonRow = createNewButtonRow();

		for (int i = 0; i < answers.size(); ++i) {
			Answer answer = answers.get(i);

			AnswerButton answerButton = AnswerButtonFactory.createButton(this, answer);
			answerButton.setOnClickListener(answerButtonClickListener);

			answerButtons[i] = answerButton;
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

	private void setInTransition(boolean value) {
		this.isInTransition = value;
	}

	private boolean isInTransition() {
		return this.isInTransition;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.quiz_leaving_prompt_title))
				.setMessage(getString(R.string.quiz_leaving_prompt))
				.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton(getString(R.string.no), null)
				.show();
	}

	private final View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			if (isInTransition()) {
				gotoNextQuestion();
			}
			else {
				AnswerButton button = (AnswerButton) view;

				Answer answer = button.getAnswer();
				boolean selected = !answer.isSelected();

				answer.setSelected(selected);
				button.updateDrawable(AnswerButton.SELECTION_FADE_TIME);
			}
		}
	};
}
