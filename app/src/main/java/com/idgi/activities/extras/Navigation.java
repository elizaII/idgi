package com.idgi.activities.extras;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.idgi.activities.BrowseActivity;
import com.idgi.activities.CreateAccountActivity;
import com.idgi.activities.LoginActivity;
import com.idgi.activities.MyCoursesActivity;
import com.idgi.activities.ProfileActivity;
import com.idgi.activities.QuizActivity;
import com.idgi.R;
import com.idgi.activities.StartActivity;
import com.idgi.activities.StatisticsActivity;

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
        setTargetActivity(R.id.nav_log_in, LoginActivity.class);
        setTargetActivity(R.id.nav_create_profile, CreateAccountActivity.class);
    }

    private static void setTargetActivity(int resource, Class targetClass) {
        itemMap.put(resource, targetClass);
    }

}
