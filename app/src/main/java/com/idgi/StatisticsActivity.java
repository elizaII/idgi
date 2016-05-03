package com.idgi;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.idgi.core.Statistics;
import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

public class StatisticsActivity extends AppCompatActivityWithDrawer {

	private TextView txtCompletedCourses, txtOngoingCourses, txtCompletedQuizAmount, txtSeenVideosAmount, txtHatAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		initializeDrawer();

		findViews();

		User user = Storage.getActiveUser();
		loadStatistics(user);
	}

	private void findViews() {
		txtCompletedCourses = (TextView) findViewById(R.id.statistics_txt_completed_courses);
		txtOngoingCourses = (TextView) findViewById(R.id.statistics_txt_ongoing_courses);
		txtCompletedQuizAmount = (TextView) findViewById(R.id.statistics_txt_completed_quiz_amount);
		txtSeenVideosAmount = (TextView) findViewById(R.id.statistics_txt_hat_amount);
	}

	private void loadStatistics(User user) {
		txtCompletedCourses.setText(Integer.toString(user.getStat(Statistics.Property.COMPLETED_COURSES)));
		txtOngoingCourses.setText(Integer.toString(user.getStat(Statistics.Property.ONGOING_COURSES)));
		txtCompletedQuizAmount.setText(Integer.toString(user.getStat(Statistics.Property.COMPLETED_QUIZZES)));
		txtSeenVideosAmount.setText(Integer.toString(user.getStat(Statistics.Property.SEEN_VIDEOS)));
	}
}
