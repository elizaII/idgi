package com.idgi.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Statistics;
import com.idgi.core.StudentUser;
import com.idgi.core.User;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

import java.util.Locale;

public class StatisticsActivity extends DrawerActivity {
	private TextView txtCompletedCourses, txtOngoingCourses, txtCompletedQuizAmount,
			txtSeenVideosAmount, txtCommentAmount, txtHatAmount, txtPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		String title = getString(R.string.title_activity_statistics);
		initializeWithTitle(title);

		findViews();

		if(SessionData.getLoggedInUser() instanceof StudentUser){
		    StudentUser user = (StudentUser) SessionData.getLoggedInUser();
		    loadStatistics(user);
        }
	}

	private void findViews() {
		txtCompletedCourses = (TextView) findViewById(R.id.statistics_txt_completed_courses);
		txtOngoingCourses = (TextView) findViewById(R.id.statistics_txt_ongoing_courses);
		txtCompletedQuizAmount = (TextView) findViewById(R.id.statistics_txt_completed_quiz_amount);
		txtSeenVideosAmount = (TextView) findViewById(R.id.statistics_txt_seen_videos_amount);
		txtCommentAmount = (TextView) findViewById(R.id.statistics_txt_comment_amount);
		//txtHatAmount = (TextView) findViewById(R.id.statistics_txt_hat_amount);
		txtPoints = (TextView) findViewById(R.id.statistics_txt_points);
	}

	private void loadStatistics(StudentUser user) {
		txtCompletedCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.COMPLETED_COURSES)));
		txtOngoingCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.ONGOING_COURSES)));
		txtCompletedQuizAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.COMPLETED_QUIZZES)));
		txtSeenVideosAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.SEEN_VIDEOS)));
		txtCommentAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.COMMENTS)));
		txtPoints.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.POINTS)));
		//txtHatAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStat(Statistics.Property.HATS)));
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
}
