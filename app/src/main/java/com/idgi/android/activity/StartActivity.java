package com.idgi.android.activity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import com.idgi.R;
import com.idgi.android.fragment.StartLoggedInFragment;
import com.idgi.android.fragment.StartLoggedOutFragment;
import com.idgi.core.User;
import com.idgi.session.SessionData;

/*
The entrance activity of the application.
 */
public class StartActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_with_fragment);

		initializeWithTitle(getString(R.string.app_name));
		overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
	}

	@Override
	public void onStart() {
		super.onStart();

		selectFragment();
	}

	private void selectFragment() {
		User user = SessionData.getLoggedInUser();
		Fragment fragment = user == null ? new StartLoggedOutFragment() : new StartLoggedInFragment();

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.start_fragment_wrapper, fragment);
		transaction.commit();
	}

	public void onStartButtonClick(View view) {
		startActivity(new Intent(this, SchoolListActivity.class));
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public void onLoginButtonClick(View view) {
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
