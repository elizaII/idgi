package com.idgi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.idgi.R;
import com.idgi.core.StudentUser;
import com.idgi.core.TeacherUser;
import com.idgi.core.User;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

public class StartActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		String title = getResources().getString(R.string.title_activity_start);
		initializeWithTitle(title);

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

		if (SessionData.hasLoggedInUser()) {
			if(SessionData.getLoggedInUser() instanceof TeacherUser) {
				FloatingActionButton createLessonButton = (FloatingActionButton)
						findViewById(R.id.start_fab_create_lesson);
				createLessonButton.setVisibility(View.VISIBLE);
			}

			Button createAccountButton = (Button) findViewById(R.id.start_btn_create_account);
			Button logInButton = (Button) findViewById(R.id.start_btn_log_in);
			Button accountButton = (Button) findViewById(R.id.start_btn_account);

			createAccountButton.setVisibility(View.GONE);
			logInButton.setVisibility(View.GONE);
			accountButton.setVisibility(View.VISIBLE);
		}
	}

	private void notLoggedIn() {
		// TODO uncomment, development is significantly slower when this is active
		/*start_btn_account.setEnabled(false);
		start_btn_create_lesson.setEnabled(false);*/
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void onStartButtonClick(View view) {
		startActivity(new Intent(this, BrowseActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLogInButtonClick(View view) {
		startActivity(new Intent(this, LoginActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onCreateAccountButtonClick(View view) {
		//startActivity(new Intent(StartActivity.this, CreateAccountActivity.class));
		//overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		Drawable profilePicture = ContextCompat.getDrawable(this, R.drawable.yoda);

		User user = new StudentUser("Yoda");
		user.setEmail("test@gmail.com");
		user.setAge(9);
		user.setProfilePicture(profilePicture);
		SessionData.setLoggedInUser(user);

	}

	public void onAccountButtonClick(View view) {
		startActivity(new Intent(this, ProfileActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLessonFabClick(View view) {
		startActivity(new Intent(this, CreateLessonActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

}
