package com.idgi.activities.extras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.idgi.activities.BrowseActivity;
import com.idgi.activities.CourseActivity;
import com.idgi.activities.CourseListActivity;
import com.idgi.activities.CreateAccountActivity;
import com.idgi.activities.CreateLessonActivity;
import com.idgi.activities.LessonActivity;
import com.idgi.activities.LoginActivity;
import com.idgi.activities.MyCoursesActivity;
import com.idgi.activities.ProfileActivity;
import com.idgi.activities.QuizActivity;
import com.idgi.R;
import com.idgi.activities.QuizResultActivity;
import com.idgi.activities.SchoolListActivity;
import com.idgi.activities.StartActivity;
import com.idgi.activities.StatisticsActivity;
import com.idgi.activities.SubjectListActivity;
import com.idgi.activities.VideoActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles navigation between activities.
 */
public class Navigation {

	/* Connects MenuItem-IDs to Activity classes */
	private static Map<Integer, Class> itemMap = new HashMap<>();

	/* Connects ActivityTypes to Activity classes */
	private static Map<ActivityType, Class> activityMap = new HashMap<>();

    public static void onMenuItemSelected(Activity activity, MenuItem item) {
        if (itemMap.isEmpty())
            initializeItemMap();

        activity.startActivity(new Intent(activity, itemMap.get(item.getItemId())));
    }

    private static void initializeItemMap() {
        setTargetActivity(R.id.nav_start, StartActivity.class);
        setTargetActivity(R.id.nav_profile, ProfileActivity.class);
        setTargetActivity(R.id.nav_statistics, StatisticsActivity.class);
        setTargetActivity(R.id.nav_my_courses, MyCoursesActivity.class);
        setTargetActivity(R.id.nav_quiz, QuizActivity.class);
        setTargetActivity(R.id.nav_browse, BrowseActivity.class);
        setTargetActivity(R.id.nav_log_in, LoginActivity.class);
        setTargetActivity(R.id.nav_create_profile, CreateAccountActivity.class);
    }

	private static void initializeActivityMap() {
		setTargetActivity(ActivityType.BROWSE, BrowseActivity.class);
		setTargetActivity(ActivityType.COURSE, CourseActivity.class);
		setTargetActivity(ActivityType.COURSE_LIST, CourseListActivity.class);
		setTargetActivity(ActivityType.CREATE_ACCOUNT, CreateAccountActivity.class);
		setTargetActivity(ActivityType.CREATE_LESSON, CreateLessonActivity.class);
		setTargetActivity(ActivityType.LESSON, LessonActivity.class);
		setTargetActivity(ActivityType.LOGIN, LoginActivity.class);
		setTargetActivity(ActivityType.MY_COURSES, MyCoursesActivity.class);
		setTargetActivity(ActivityType.PROFILE, ProfileActivity.class);
		setTargetActivity(ActivityType.QUIZ, QuizActivity.class);
		setTargetActivity(ActivityType.QUIZ_RESULT, QuizResultActivity.class);
		setTargetActivity(ActivityType.SCHOOL_LIST, SchoolListActivity.class);
		setTargetActivity(ActivityType.START, StartActivity.class);
		setTargetActivity(ActivityType.STATISTICS, StatisticsActivity.class);
		setTargetActivity(ActivityType.SUBJECT_LIST, SubjectListActivity.class);
		setTargetActivity(ActivityType.VIDEO, VideoActivity.class);
	}

    private static void setTargetActivity(int resource, Class targetClass) {
        itemMap.put(resource, targetClass);
    }

	private static void setTargetActivity(ActivityType type, Class targetClass) {
		activityMap.put(type, targetClass);
	}

	public static void startActivity(Context context, ActivityType type) {
		if (activityMap.isEmpty())
			initializeActivityMap();

		context.startActivity(new Intent(context, activityMap.get(type)));
	}


}
