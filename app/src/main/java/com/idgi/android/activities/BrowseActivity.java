package com.idgi.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idgi.R;
import com.idgi.android.activities.extras.DrawerActivity;

public class BrowseActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        String title = getResources().getString(R.string.title_activity_browse);
        initializeWithTitle(title);
    }

    public void onSchoolButtonClick(View view){
        startActivity(new Intent(this, SchoolListActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void onSubjectButtonClick(View view){
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
