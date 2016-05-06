package com.idgi;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.idgi.core.Statistics;
import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

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

		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.statistics_view_layout);
		layout.setAlpha(0f);

		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				layout.setAlpha(1f);
			}
		}, 100);

		for (int i = 0; i < layout.getChildCount(); ++i) {
			View view = layout.getChildAt(i);
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

	private void findViews() {
		txtCompletedCourses = (TextView) findViewById(R.id.statistics_txt_completed_courses);
		txtOngoingCourses = (TextView) findViewById(R.id.statistics_txt_ongoing_courses);
		txtCompletedQuizAmount = (TextView) findViewById(R.id.statistics_txt_completed_quiz_amount);
		txtSeenVideosAmount = (TextView) findViewById(R.id.statistics_txt_seen_videos_amount);
		txtHatAmount = (TextView) findViewById(R.id.statistics_txt_hat_amount);
		txtPoints = (TextView) findViewById(R.id.statistics_txt_points);
	}

	private void loadStatistics(User user) {
		txtCompletedCourses.setText(Integer.toString(user.getStat(Statistics.Property.COMPLETED_COURSES)));
		txtOngoingCourses.setText(Integer.toString(user.getStat(Statistics.Property.ONGOING_COURSES)));
		txtCompletedQuizAmount.setText(Integer.toString(user.getStat(Statistics.Property.COMPLETED_QUIZZES)));
		txtSeenVideosAmount.setText(Integer.toString(user.getStat(Statistics.Property.SEEN_VIDEOS)));
		txtPoints.setText(Integer.toString(user.getStat(Statistics.Property.POINTS)));
		txtHatAmount.setText(Integer.toString(user.getStat(Statistics.Property.HATS)));
	}
}
