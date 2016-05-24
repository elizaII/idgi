package com.idgi.application;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.idgi.android.activities.BrowseActivity;
import com.idgi.android.activities.BrowseUsersActivity;
import com.idgi.android.activities.CreateAccountActivity;
import com.idgi.android.activities.LoginActivity;
import com.idgi.android.activities.MyCoursesActivity;
import com.idgi.android.activities.ProfileActivity;
import com.idgi.android.activities.QuizActivity;
import com.idgi.R;
import com.idgi.android.activities.StartActivity;
import com.idgi.android.activities.StatisticsActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the navigation between activities from the navigation drawer.
 */
public class Navigation {

	/* Connects MenuItem-IDs to Activity classes */
	private static Map<Integer, Class> itemMap = new HashMap<>();

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
        setTargetActivity(R.id.nav_browse_users, BrowseUsersActivity.class);
        setTargetActivity(R.id.nav_log_in, LoginActivity.class);
        setTargetActivity(R.id.nav_create_profile, CreateAccountActivity.class);
    }

    private static void setTargetActivity(int resource, Class targetClass) {
        itemMap.put(resource, targetClass);
    }

}
