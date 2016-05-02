package com.idgi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Navigation;

public class BrowseActivity extends AppCompatActivityWithDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDrawer();

    }

    public void onSchoolButtonClick(View view){
        startActivity(new Intent(BrowseActivity.this, SchoolListActivity.class));
    }

    public void onSubjectButtonClick(View view){

    }
}
