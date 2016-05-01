package com.idgi;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idgi.core.Quiz;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.ExpandAnimation;
import com.idgi.util.Navigation;
import com.idgi.util.Storage;

public class QuizResultActivity extends AppCompatActivityWithDrawer {

	private LinearLayout starContainer;
	private Quiz quiz;
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_result);

		initializeDrawer();

		this.quiz = Storage.getCurrentQuiz();
		starContainer = (LinearLayout) findViewById(R.id.result_quiz_star_container);

		initiateButtons();

		displayScore();

		populateQuestionList();
	}

	private void initiateButtons() {
		Button btnRetake = (Button) findViewById(R.id.result_quiz_btn_retake);
		Button btnDone = (Button) findViewById(R.id.result_quiz_btn_done);

		if (btnRetake != null)
			btnRetake.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
					intent.putExtra("quiz_key", "Quiz123");

					startActivity(intent);
				}
			});

		if (btnDone != null)
			btnDone.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(QuizResultActivity.this, StartActivity.class));
				}
			});
	}

	private void displayScore() {
		TextView txtScore = (TextView) findViewById(R.id.result_quiz_text_score);

		if (txtScore != null) {
			int score = quiz.getCorrectAnswerAmount();
			int max = quiz.length();
			String scoreText = String.format("%d/%d", score, max);
			txtScore.setText(scoreText);
		}

		showStars();
	}

	private void showStars() {
		int emptyStar = R.drawable.ic_star_empty;
		int filledStar = R.drawable.ic_star_filled;

		int amount = quiz.length();
		int correctAnswers = quiz.getCorrectAnswerAmount();
		int incorrectAnswers = amount - correctAnswers;

		for (int i = 0; i < amount; ++i) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
					);

			params.setMargins(10, 0, 0, 0);
			imageView.setLayoutParams(params);

			if (i < incorrectAnswers)
				imageView.setImageResource(emptyStar);
			else
				imageView.setImageResource(filledStar);

			starContainer.addView(imageView);
		}
	}

	private void populateQuestionList() {
		recyclerView = (RecyclerView) findViewById(R.id.quiz_result_recycler_view_questions_roundup);

		QuizResultAdapter adapter = new QuizResultAdapter(quiz.getQuestions());
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
	}
}
