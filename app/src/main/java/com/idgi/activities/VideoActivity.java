package com.idgi.activities;

//Code from http://www.sitepoint.com/using-the-youtube-api-to-embed-video-in-an-android-app/, retrieved 20/04/2016

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.idgi.R;
import com.idgi.util.Config;
import com.idgi.activities.extras.Navigation;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, NavigationView.OnNavigationItemSelectedListener{
	private Toolbar toolbar;
	private static final int RECOVERY_REQUEST = 1;
	private YouTubePlayerView youTubeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubeView.initialize(Config.YOUTUBE_API_KEY, this);


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public void onInitializationSuccess(Provider provider, final YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo("1-ZZ7HlNQgk"); // Plays https://www.youtube.com/watch?v=1-ZZ7HlNQgk
			player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
				@Override
				public void onPlaying() {

				}

				@Override
				public void onPaused() {
					if (player.getCurrentTimeMillis() == player.getDurationMillis())
						Toast.makeText(VideoActivity.this, "Works!", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onStopped() {

				}

				@Override
				public void onBuffering(boolean b) {

				}

				@Override
				public void onSeekTo(int i) {

				}
			});
		}
	}

	@Override
	public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
		} else {
			String error = String.format(getString(R.string.player_error), errorReason.toString());
			Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
		}
	}

	protected Provider getYouTubePlayerProvider() {
		return youTubeView;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		Navigation.onMenuItemSelected(this,item);
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}