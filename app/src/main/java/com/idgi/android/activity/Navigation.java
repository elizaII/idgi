package com.idgi.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.idgi.android.ActivityType;
import com.idgi.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the navigation between activities from the navigation drawer.
 */
public class Navigation {

	/* Connects MenuItem-IDs to Activity classes */
	private static Map<Integer, Class> menuMap = new HashMap<>();
    private static Map<ActivityType, Class> activityMap = new HashMap<>();

    public static void onMenuItemSelected(Activity activity, MenuItem item) {
        if (menuMap.isEmpty())
            initializeMaps();

        activity.startActivity(new Intent(activity, menuMap.get(item.getItemId())));
    }

    public static void navigateTo(Context from, ActivityType to) {
        if (activityMap.isEmpty())
            initializeMaps();

        from.startActivity(new Intent(from, activityMap.get(to)));
    }

    private static void initializeMaps() {
        setTargetActivity(R.id.nav_start, ActivityType.START, StartActivity.class);
        setTargetActivity(R.id.nav_profile, ActivityType.PROFILE, ProfileActivity.class);
        setTargetActivity(R.id.nav_statistics, ActivityType.STATISTICS, StatisticsActivity.class);
        setTargetActivity(R.id.nav_my_courses, ActivityType.MY_COURSES, MyCoursesActivity.class);
        setTargetActivity(R.id.nav_quiz, ActivityType.QUIZ, QuizActivity.class);
        setTargetActivity(R.id.nav_browse, ActivityType.BROWSE, BrowseActivity.class);
        setTargetActivity(R.id.nav_browse_users, ActivityType.BROWSE_USERS, BrowseUsersActivity.class);
        setTargetActivity(R.id.nav_log_in, ActivityType.LOGIN, LoginActivity.class);
        setTargetActivity(R.id.nav_create_profile, ActivityType.CREATE_ACCOUNT, CreateAccountActivity.class);
    }

    private static void setTargetActivity(int resource, ActivityType type, Class targetClass) {
        menuMap.put(resource, targetClass);
        activityMap.put(type, targetClass);
    }

}
