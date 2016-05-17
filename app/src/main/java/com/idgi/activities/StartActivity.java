package com.idgi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.idgi.R;
import com.idgi.core.StudentUser;
import com.idgi.core.User;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

public class StartActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		String title = getResources().getString(R.string.title_activity_start);
		initializeWithTitle(title);

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

		if (!SessionData.hasLoggedInUser())
			notLoggedIn();
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
		user.setProfilePicture(profilePicture);
		SessionData.setLoggedInUser(user);

	}

	public void onAccountButtonClick(View view) {
		startActivity(new Intent(this, ProfileActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLessonButtonClick(View view) {
		startActivity(new Intent(this, CreateLessonActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

}
