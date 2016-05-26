package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Student;
import com.idgi.core.Teacher;
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
		if (!SessionData.hasLoggedInUser()) {
			initializeForAnon();
		} else {
			initializeForLoggedIn();
		}
	}

	private void initializeForLoggedIn() {
		setContentView(R.layout.activity_start_logged_in);
		initializeWelcomeMessage();

		TextView logo = (TextView) findViewById(R.id.start_title);
		TextView welcomeTxt = (TextView) findViewById(R.id.start_txt_welcome);
		Button findCourses = (Button) findViewById(R.id.start_btn_browse);

		animateViewSequentially(logo, welcomeTxt, findCourses);

		if (SessionData.getLoggedInUser() instanceof Teacher)
			initializeForTeacher();
	}

	private void initializeForAnon() {
		setContentView(R.layout.activity_start_not_logged_in);

		TextView logo = (TextView) findViewById(R.id.start_title);
		Button findCourses = (Button) findViewById(R.id.start_btn_browse);
		Button logIn = (Button) findViewById(R.id.start_btn_log_in);
		Button createAccount = (Button) findViewById(R.id.start_btn_create_account);

		animateViewSequentially(logo, findCourses, logIn, createAccount);
	}

	private void animateViewSequentially(View... views) {
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_top);
		int offset = 0;

		for(View view : views) {
			if(view != null) {
				fadeIn.setStartOffset(offset);
				view.startAnimation(fadeIn);
				offset += 40;
			}
		}
	}

	private void initializeForTeacher(){
		initializeWelcomeMessage();

		FloatingActionButton createLessonButton = (FloatingActionButton)
				findViewById(R.id.start_fab_create_lesson);

		if(createLessonButton != null) {
			createLessonButton.setVisibility(View.VISIBLE);
		}
	}

	private void initializeWelcomeMessage() {
		TextView welcomeText = (TextView) findViewById(R.id.start_txt_welcome);

		String welcomeMsg = String.format(Locale.ENGLISH,
				getResources().getString(R.string.start_txt_welcome_msg),
				SessionData.getLoggedInUser().getName());

		if(welcomeText != null)
			welcomeText.setText(welcomeMsg);

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
		/*
		Drawable profilePicture = ContextCompat.getDrawable(this, R.drawable.yoda);

		User user = new StudentUser("Yoda");
		user.setEmail("test@gmail.com");
		user.setAge(9);
		user.setProfilePicture(profilePicture);
		SessionData.setLoggedInUser(user);

		startActivity(new Intent(this, CreateLessonActivity.class));*/

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
