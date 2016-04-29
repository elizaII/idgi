package com.idgi.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.idgi.BrowseActivity;
import com.idgi.CreateAccountActivity;
import com.idgi.LoginActivity;
import com.idgi.ProfileActivity;
import com.idgi.QuizActivity;
import com.idgi.R;
import com.idgi.StartActivity;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by tuyenngo on 16-04-29.
 */
public class Navigation {

    public static void onMenuItemSelected(Activity a,MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            a.startActivity(new Intent(a.getApplicationContext(), StartActivity.class));
        } else if(id==R.id.nav_profile){
            a.startActivity(new Intent(a.getApplicationContext(), ProfileActivity.class));
        } else if(id==R.id.nav_my_courses){
            a.startActivity(new Intent(a.getApplicationContext(), ProfileActivity.class));
        } else if (id == R.id.nav_my_courses) {
            a.startActivity(new Intent(a.getApplicationContext(), ProfileActivity.class));
        } else if (id == R.id.nav_quiz) {
            Intent intent = new Intent(a.getApplicationContext(), QuizActivity.class);
            intent.putExtra("quiz_key", "Quiz123");

            a.startActivity(intent);
        } else if (id == R.id.nav_browse) {
            a.startActivity(new Intent(a.getApplicationContext(), BrowseActivity.class));

        } else if (id == R.id.nav_log_in) {
            a.startActivity(new Intent(a.getApplicationContext(), LoginActivity.class));

        } else if (id == R.id.nav_create_profile) {
            a.startActivity(new Intent(a.getApplicationContext(), CreateAccountActivity.class));

        }
    }


}
