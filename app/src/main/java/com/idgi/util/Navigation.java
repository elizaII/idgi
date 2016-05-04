package com.idgi.util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.idgi.BrowseActivity;
import com.idgi.CreateAccountActivity;
import com.idgi.LoginActivity;
import com.idgi.ProfileActivity;
import com.idgi.QuizActivity;
import com.idgi.R;
import com.idgi.StartActivity;
import com.idgi.StatisticsActivity;

import static android.support.v4.app.ActivityCompat.startActivity;

public class Navigation {

    public static void onMenuItemSelected(Activity activity, MenuItem item){

        Class activityClass = null;

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_start:
                activityClass = StartActivity.class;
                break;
            case R.id.nav_profile:
                activityClass = ProfileActivity.class;
                break;
            case R.id.nav_statistics:
                activityClass = StatisticsActivity.class;
                break;
            case R.id.nav_my_courses:
                activityClass = ProfileActivity.class;
                break;
            case R.id.nav_quiz:
                Intent intent = new Intent(activity.getApplicationContext(), QuizActivity.class);
                intent.putExtra("quiz_key", "Quiz123");

                activity.startActivity(intent);
                return;
            case R.id.nav_browse:
                activityClass = BrowseActivity.class;
                break;
            case R.id.nav_log_in:
                activityClass = LoginActivity.class;
                break;
            case R.id.nav_create_profile:
                activityClass = CreateAccountActivity.class;
                break;
        }

        activity.startActivity(new Intent(activity.getApplicationContext(), activityClass));
    }


}
