package com.idgi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends AppCompatActivity {

	private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		toolbar = (Toolbar) findViewById(R.id.test_toolbar);
		setSupportActionBar(toolbar);

		if (savedInstanceState != null)
			return;
    }

    public void showVideoActivity(View view) {
		//Toast.makeText(getBaseContext(), "Click registered.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StartActivity.this, VideoActivity.class));
    }

	public void showQuizActivity(View view) {
		startActivity(new Intent(StartActivity.this, SchoolListActivity.class));
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				// User chose the "Settings" item, show the app settings UI...
				return true;

			case R.id.action_home:
				return true;

			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);

		}
	}
}
