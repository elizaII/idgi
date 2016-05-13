package com.idgi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.idgi.R;
import com.idgi.activities.extras.ActivityType;
import com.idgi.core.User;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

public class StartActivity extends DrawerActivity {

		private Button start_btn_browse;
		private Button start_btn_log_in;
		private Button start_btn_create_account;
		private Button start_btn_account;
		private Button start_btn_create_lesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_start);

		initializeDrawer();

		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

		start_btn_browse = (Button) findViewById(R.id.start_btn_browse);
		start_btn_log_in = (Button) findViewById(R.id.start_btn_log_in);
		start_btn_create_account = (Button) findViewById(R.id.start_btn_create_account);
		start_btn_account = (Button) findViewById(R.id.start_btn_account);
		start_btn_create_lesson = (Button) findViewById(R.id.start_btn_create_lesson);
		if (!SessionData.hasLoggedInUser()){
			notLoggedIn();
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
		startActivity(ActivityType.BROWSE);
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLogInButtonClick(View view) {
		startActivity(ActivityType.LOGIN);
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
		startActivity(ActivityType.PROFILE);
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLessonButtonClick(View view) {
		startActivity(ActivityType.CREATE_LESSON);
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

}
