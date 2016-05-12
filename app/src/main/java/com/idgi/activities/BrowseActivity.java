package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idgi.R;
import com.idgi.activities.extras.AppCompatActivityWithDrawer;

public class BrowseActivity extends AppCompatActivityWithDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        initializeDrawer();
    }

    public void onSchoolButtonClick(View view){
        startActivity(new Intent(BrowseActivity.this, SchoolListActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void onSubjectButtonClick(View view){
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }
}
