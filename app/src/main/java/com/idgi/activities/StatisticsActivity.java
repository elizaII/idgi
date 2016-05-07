package com.idgi.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.idgi.R;
import com.idgi.core.Statistics;
import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

import java.util.Locale;

public class StatisticsActivity extends AppCompatActivityWithDrawer {
	private TextView txtCompletedCourses, txtOngoingCourses, txtCompletedQuizAmount, txtSeenVideosAmount, txtHatAmount, txtPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		initializeDrawer();

		findViews();

		User user = Storage.getActiveUser();
		loadStatistics(user);

		animateActivityEntrance();
	}

	private void findViews() {
		txtCompletedCourses = (TextView) findViewById(R.id.statistics_txt_completed_courses);
		txtOngoingCourses = (TextView) findViewById(R.id.statistics_txt_ongoing_courses);
		txtCompletedQuizAmount = (TextView) findViewById(R.id.statistics_txt_completed_quiz_amount);
		txtSeenVideosAmount = (TextView) findViewById(R.id.statistics_txt_seen_videos_amount);
		txtHatAmount = (TextView) findViewById(R.id.statistics_txt_hat_amount);
		txtPoints = (TextView) findViewById(R.id.statistics_txt_points);
	}

	private void loadStatistics(User user) {
		txtCompletedCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.COMPLETED_COURSES)));
		txtOngoingCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.ONGOING_COURSES)));
		txtCompletedQuizAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.COMPLETED_QUIZZES)));
		txtSeenVideosAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.SEEN_VIDEOS)));
		txtPoints.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.POINTS)));
		txtHatAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.HATS)));
	}

	private void animateActivityEntrance() {
		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.statistics_view_layout);

		Handler handler = new Handler();

		for (int i = 0; i < layout.getChildCount(); ++i) {
			View view = layout.getChildAt(i);
			view.setAlpha(0f);
			handler.postDelayed(viewSlideIn(view), 50 * i);
		}
	}

	private Runnable viewSlideIn(final View view) {
		return new Runnable() {
			public void run() {
				ViewAnimator.animate(view).slideLeft().duration(800).start();
			}
		};
	}
}
