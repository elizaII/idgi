package com.idgi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.activities.extras.AppCompatActivityWithDrawer;
import com.idgi.session.SessionData;

public class StartActivity extends AppCompatActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_start);

		initializeDrawer();

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	@Override
	public void onResume() {
		super.onResume();
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
		User user = new User("Yoda");
		user.setEmail("test@gmail.com");
		user.setImage(d);
		SessionData.setLoggedInUser(user);

	}

	public void onAccountButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, ProfileActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLessonButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, CreateLessonActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

}
