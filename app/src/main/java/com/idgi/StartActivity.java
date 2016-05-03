package com.idgi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Navigation;

public class StartActivity extends AppCompatActivityWithDrawer {

	private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		if (savedInstanceState != null)
			return;
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

		initializeDrawer();
    }

    public void showVideoActivity(View view) {
		//Toast.makeText(getBaseContext(), "Click registered.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StartActivity.this, VideoActivity.class));
    }

	public void onStartButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, BrowseActivity.class));
	}

	public void onLogInButtonClick(View view) {
		Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
		intent.putExtra("quiz_key", "Quiz123");

		startActivity(intent);
	}

	public void onCreateAccountButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, CreateAccountActivity.class));
	}

	public void onAccountButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, ProfileActivity.class));
	}

	public void onLessonButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, LessonActivity.class));
	}


}
