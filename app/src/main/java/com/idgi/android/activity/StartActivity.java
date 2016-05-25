package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Student;
import com.idgi.session.SessionData;

import java.util.Locale;

/*
The entrance activity of the application.
 */
public class StartActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		initialize();

		initializeWithTitle(getString(R.string.app_name));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

	}

	private void initialize() {
		if (!SessionData.hasLoggedInUser())
			setContentView(R.layout.activity_start_not_logged_in);
		else {
			setContentView(R.layout.activity_start_logged_in);

			if (SessionData.getLoggedInUser() instanceof Student)
				initializeForStudent();
			else
				initializeForTeacher();
		}
	}

	private void initializeForStudent() {
		TextView welcomeText = (TextView) findViewById(R.id.start_txt_welcome);

		String welcomeMsg = String.format(Locale.ENGLISH,
				getResources().getString(R.string.start_txt_welcome_msg),
				SessionData.getLoggedInUser().getName());

		if(welcomeText != null)
			welcomeText.setText(welcomeMsg);
	}

	private void initializeForTeacher(){
		FloatingActionButton createLessonButton = (FloatingActionButton)
				findViewById(R.id.start_fab_create_lesson);

		if(createLessonButton != null)
			createLessonButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void onStartButtonClick(View view) {
		startActivity(new Intent(this, SchoolListActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLogInButtonClick(View view) {
		startActivity(new Intent(this, LoginActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onCreateAccountButtonClick(View view) {
		startActivity(new Intent(StartActivity.this, CreateAccountActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
