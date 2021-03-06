package com.idgi.android.activity;

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
import com.idgi.android.widget.AnswerButtonFactory;
import com.idgi.android.widget.AnswerButton;

import java.util.List;

/*
Handles quiz-taking.
 */
public class QuizActivity extends AppCompatActivity {

	private IQuiz quiz;
	private TextView txtQuestion;
	private LinearLayout buttonContainer;
	private Button btnNext;
	private AnswerButton[] answerButtons;
    private ProgressBar timeProgressBar;

	private static String NO_HINT;

	private CountDownTimer timer;

	private boolean isInTransition = false;

	//To stop weird transitions after we've already exited the activity
	private boolean leftActivity = false;

	private static final int BUTTONS_PER_ROW = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		if (NO_HINT == null)
			NO_HINT = getResources().getString(R.string.quiz_no_hint);

		buttonContainer = (LinearLayout) findViewById(R.id.quiz_answer_container);

		quiz = SessionData.getCurrentQuiz();
		if (quiz != null) {
			quiz.reset();
			initializeNextButton();
			initializeQuestionView();
			if(quiz instanceof TimedQuiz){
				initializeTimeBar((TimedQuiz) quiz);
			}
			createAnswerButtons(quiz.getCurrentQuestion());
		} else {
			finish();
		}
	}

	private void initializeTimeBar(final TimedQuiz timedQuiz) {
        timeProgressBar = (ProgressBar) findViewById(R.id.content_quiz_time_progress);

		if (timeProgressBar != null) {
			timeProgressBar.setVisibility(View.VISIBLE);
			timeProgressBar.setMax(timedQuiz.getTime());
		}

        timer = new QuizTimer(timedQuiz, timedQuiz.getTime(), 1);

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
				String question = quiz.getCurrentQuestion().getText();
				String hint = quiz.getCurrentQuestion().getHint();
				hint = hint.isEmpty() ? NO_HINT : hint;

				String newText = isShowingQuestion() ? hint : question;
				txtQuestion.setText(newText);
			}
		});
	}

	private boolean isShowingQuestion() {
		String questionText = quiz.getCurrentQuestion().getText();
		return txtQuestion.getText().equals(questionText);
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
				finishQuiz();
		else
			showQuestion(quiz.getCurrentQuestion());

		setInTransition(false);
	}

	public void finishQuiz() {
		leftActivity = true;
		finish();
		startActivity(new Intent(this, QuizResultActivity.class));
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
						if(quiz instanceof TimedQuiz){
							timer.cancel();
						}
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

    private class QuizTimer extends CountDownTimer{

        private final int MAX_TIME;
        private TimedQuiz timedQuiz;

        public QuizTimer(TimedQuiz timedQuiz, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.timedQuiz = timedQuiz;
            MAX_TIME = timedQuiz.getTime();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeProgressBar.setProgress(MAX_TIME - (int)millisUntilFinished);
        }

        @Override
        public void onFinish() {
            if((MAX_TIME - timeProgressBar.getProgress()) <= 10){
                Toast.makeText(getBaseContext(), getString(R.string.quiz_out_of_time), Toast.LENGTH_LONG).show();
            }
            finishQuiz();
            timedQuiz.setRemainingTime(MAX_TIME - timeProgressBar.getProgress());
        }
    }
}
