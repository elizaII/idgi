package com.idgi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

public class StartActivity extends AppCompatActivityWithDrawer {

	private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		initializeDrawer();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_start);

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void showVideoActivity(View view) {
		//Toast.makeText(getBaseContext(), "Click registered.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StartActivity.this, VideoActivity.class));
    }

	public void onStartButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, BrowseActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLogInButtonClick(View view) {
		Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
		intent.putExtra("quiz_key", "Quiz123");

		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onCreateAccountButtonClick(View view) {
		//startActivity(new Intent(StartActivity.this, CreateAccountActivity.class));
		//overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		Drawable d = getResources().getDrawable(R.drawable.yoda);
		Storage.setActiveUser(new User("Yoda", "1337.gmail.com", d));

	}

	public void onAccountButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, ProfileActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLessonButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, LessonActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

}
