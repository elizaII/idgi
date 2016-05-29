package com.idgi.android.activity;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.ActivityType;
import com.idgi.android.SearchSuggestions;
import com.idgi.core.User;
import com.idgi.event.ApplicationBus;
import com.idgi.session.SessionData;

import java.util.Locale;

/*
The main template for Activity-classes; most of them extend this class.
Handles default behavior and initialization of activities that include the navigation drawer.
 */
public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawer;
	private Toolbar toolbar;
	private SearchView searchView;
	private Menu navigationMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public DrawerActivity() {

	}

	protected void initializeDrawer() {
		if (toolbar == null) {
			toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);
		}

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null) {
			navigationView.setNavigationItemSelectedListener(this);
			this.navigationMenu = navigationView.getMenu();
		}
	}

	private void showLoggedInUser() {
		TextView txtLoggedInUser = (TextView) findViewById(R.id.nav_drawer_user_name);


		User user = SessionData.getLoggedInUser();
		String text;
		if (user != null) {
			text = String.format(Locale.ENGLISH,
					getResources().getString(R.string.nav_drawer_logged_in_as),
					user.getName());
		} else {
			text = getResources().getString(R.string.nav_drawer_not_logged_in);
		}

		if (txtLoggedInUser != null)
			txtLoggedInUser.setText(text);
	}

	protected void initializeWithTitle(String title) {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			toolbar.setTitle(title);
			setSupportActionBar(toolbar);
		}

		initializeDrawer();
	}

	@Override
	protected void onStart() {

		if(!ApplicationBus.hasListener(this))
			ApplicationBus.register(this);

		//To have it update when user logs out.
		//initializeDrawer();

		if (navigationMenu != null)
			onPrepareOptionsMenu(navigationMenu);

		if (searchView != null)
			SearchSuggestions.initiateSearchSuggestions(searchView);

		showLoggedInUser();

		super.onStart();
	}

	@Override
	protected void onStop() {
		if(ApplicationBus.hasListener(this))
			ApplicationBus.unregister(this);

		super.onStop();
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
		getMenuInflater().inflate(R.menu.menu_main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);

		SearchSuggestions.initiateSearchSuggestions(searchView);

		return true;
	}

	/* Updates the menu before it's shown, every time it's shown. */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(SessionData.hasLoggedInUser()) {
			hideOption(R.id.nav_create_account);
			showLogOut();
		} else {
			showOption(R.id.nav_create_account);
			showLogIn();
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@TargetApi(21)
	private void showLogOut() {
		setOptionTitle(R.id.nav_log_in, getResources().getString(R.string.log_out));
		setOptionIcon(R.id.nav_log_in, getResources().getDrawable(R.drawable.icon_exit_black_24dp, null));
	}

	@TargetApi(21)
	private void showLogIn() {
		setOptionTitle(R.id.nav_log_in, getResources().getString(R.string.drawer_log_in));
		setOptionIcon(R.id.nav_log_in, getResources().getDrawable(R.drawable.ic_lock_black_24dp, null));
	}

	private void hideOption(int id){
		MenuItem item = navigationMenu.findItem(id);
		if(item != null)
			item.setVisible(false);
	}

	private void showOption(int id){
		MenuItem item = navigationMenu.findItem(id);
		if(item != null)
			item.setVisible(true);
	}

	public void setOptionTitle(int id, String title) {
		MenuItem item = navigationMenu.findItem(id);
		if(item != null)
			item.setTitle(title);
	}

	private void setOptionIcon(int id, Drawable drawable) {
		MenuItem item = navigationMenu.findItem(id);
		if(item != null)
			item.setIcon(drawable);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		Navigation.onMenuItemSelected(this, item);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Subscribe
	public void onStartActivity(ActivityType type) {
		Navigation.navigateTo(this, type);
	}
}
