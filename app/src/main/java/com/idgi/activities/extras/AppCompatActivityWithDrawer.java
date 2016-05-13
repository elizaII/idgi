package com.idgi.activities.extras;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.idgi.R;
import com.idgi.activities.ActivityType;
import com.idgi.activities.LoginActivity;
import com.idgi.activities.StartActivity;
import com.idgi.util.Navigation;

public class AppCompatActivityWithDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawer;
	private Toolbar toolbar;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initializeDrawer() {
		if (toolbar == null)
			toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null)
			navigationView.setNavigationItemSelectedListener(this);
	}

	protected void initializeWithTitle(String title) {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null)
			toolbar.setTitle(title);

		initializeDrawer();
	}

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right);
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
		Navigation.onMenuItemSelected(this, item);

		drawer.closeDrawer(GravityCompat.START);
		return true;
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

	public void changeToActivity(ActivityType type) {
		Class target = null;

		switch(type) {
			case START:
				target = StartActivity.class;
				break;
			case LOGIN:
				target = LoginActivity.class;
				break;
		}

		startActivity(new Intent(this, target));
	}


}
