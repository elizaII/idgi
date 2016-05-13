package com.idgi.activities;

import android.os.Bundle;
import android.view.View;

import com.idgi.R;
import com.idgi.activities.extras.ActivityType;
import com.idgi.activities.extras.DrawerActivity;

public class BrowseActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        initializeDrawer();
    }

    public void onSchoolButtonClick(View view){
        startActivity(ActivityType.SCHOOL_LIST);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void onSubjectButtonClick(View view){
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }
}
