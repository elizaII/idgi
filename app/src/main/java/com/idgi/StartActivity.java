package com.idgi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

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

		final TestFragment firstFragment = new TestFragment();

		firstFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

		FrameLayout container = (FrameLayout) findViewById(R.id.fragment_container);
		container.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast.makeText(StartActivity.this, "Removing fragment.", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().remove(firstFragment).commit();
			}
		});

    }

    public void showVideoActivity(View view) {
		//Toast.makeText(getBaseContext(), "Click registered.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StartActivity.this, VideoActivity.class));
    }

	public void showQuizActivity(View view) {
		startActivity(new Intent(StartActivity.this, QuizActivity.class));
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
