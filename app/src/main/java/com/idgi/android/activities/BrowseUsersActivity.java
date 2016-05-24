package com.idgi.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idgi.R;
import com.idgi.android.activities.extras.DrawerActivity;

/**
 * Created by tove on 2016-05-21.
 */
public class BrowseUsersActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_users);

        String title = getResources().getString(R.string.title_activity_browse_users);
        initializeWithTitle(title);
    }

    public void onNameButtonClick(View view){
        startActivity(new Intent(this, UserListActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void onCourseButtonClick(View view){
        startActivity(new Intent(this, SchoolListActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
