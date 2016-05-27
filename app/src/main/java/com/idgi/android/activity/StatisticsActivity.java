package com.idgi.android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Statistics;
import com.idgi.core.Student;
import com.idgi.session.SessionData;

import java.util.Locale;

/*
Displays the statistics page for a User.
 */
public class StatisticsActivity extends DrawerActivity {
	private TextView txtCompletedCourses, txtOngoingCourses, txtCompletedQuizAmount,
			txtSeenVideosAmount, txtCommentAmount, txtPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		String title = getString(R.string.title_activity_statistics);
		initializeWithTitle(title);

		findViews();

		if(SessionData.getLoggedInUser() instanceof Student){
		    Student user = (Student) SessionData.getLoggedInUser();
		    loadStatistics(user);
        }
	}

	private void findViews() {
		txtCompletedCourses = (TextView) findViewById(R.id.statistics_txt_completed_courses);
		txtOngoingCourses = (TextView) findViewById(R.id.statistics_txt_ongoing_courses);
		txtCompletedQuizAmount = (TextView) findViewById(R.id.statistics_txt_completed_quiz_amount);
		txtSeenVideosAmount = (TextView) findViewById(R.id.statistics_txt_seen_videos_amount);
		txtCommentAmount = (TextView) findViewById(R.id.statistics_txt_comment_amount);
		txtPoints = (TextView) findViewById(R.id.statistics_txt_points);
	}

	private void loadStatistics(Student user) {
		txtCompletedCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.COMPLETED_COURSES)));
		txtOngoingCourses.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.ONGOING_COURSES)));
		txtCompletedQuizAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.COMPLETED_QUIZZES)));
		txtSeenVideosAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.SEEN_VIDEOS)));
		txtCommentAmount.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.COMMENTS)));
		txtPoints.setText(String.format(Locale.ENGLISH, "%d", user.getStatistics(Statistics.Property.POINTS)));
	}
}
