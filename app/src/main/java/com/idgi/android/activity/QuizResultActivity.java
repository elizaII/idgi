package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.IQuiz;
import com.idgi.core.Student;
import com.idgi.session.SessionData;
import com.idgi.android.recyclerview.adapters.QuestionAdapter;

import java.util.Locale;

/*
Displays the result of a taken quiz. Also allows for the quiz to be retaken.
 */
public class QuizResultActivity extends DrawerActivity {

	private IQuiz quiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_result);

		initializeDrawer();

		this.quiz = SessionData.getCurrentQuiz();

		initiateButtons();

		displayScore();

		populateQuestionList();

		awardPoints();
	}

	private void awardPoints() {
		if (SessionData.hasLoggedInUser() && (SessionData.getLoggedInUser() instanceof Student)){
			Student student = (Student) SessionData.getLoggedInUser();
			student.givePointsForQuiz(quiz.getID(), quiz.getPointsEarned());
		}
	}

	private void initiateButtons() {
		Button btnRetake = (Button) findViewById(R.id.result_quiz_btn_retake);
		Button btnDone = (Button) findViewById(R.id.result_quiz_btn_done);

		if (btnRetake != null)
			btnRetake.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(QuizResultActivity.this, QuizActivity.class));
					finish();
				}
			});

		if (btnDone != null)
			btnDone.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(QuizResultActivity.this, StartActivity.class));
					finish();
				}
			});
	}

	private void displayScore() {
		TextView txtScore = (TextView) findViewById(R.id.result_quiz_text_score);

		if (txtScore != null) {
			int score = quiz.getCorrectAnswerAmount();
			int max = quiz.length();
			String scoreText = String.format(Locale.ENGLISH, "%d/%d", score, max);
			txtScore.setText(scoreText);
		}
	}

	private void populateQuestionList() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.quiz_result_recycler_view_questions_roundup);

		if (recyclerView != null) {
			QuestionAdapter adapter = new QuestionAdapter(this, quiz.getQuestions());

			recyclerView.setAdapter(adapter);

			RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
			recyclerView.setLayoutManager(layoutManager);

		}
	}
}
