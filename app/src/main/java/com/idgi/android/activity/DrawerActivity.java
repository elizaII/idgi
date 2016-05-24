package com.idgi.android.activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.ActivityType;
import com.idgi.android.SearchSuggestions;
import com.idgi.event.ApplicationBus;

/*
The main template for Activity-classes; most of them extend this class.
Handles default behavior and initialization of activities that include the navigation drawer.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawer;
	private Toolbar toolbar;
	private SearchView searchView;

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
	protected void onStart() {
		if(!ApplicationBus.hasListener(this)) {
			ApplicationBus.register(this);
		}

		super.onStart();

		if (searchView != null)
			SearchSuggestions.initiateSearchSuggestions(searchView);
	}

	@Override
	protected void onStop() {
		if(ApplicationBus.hasListener(this)) {
			//ApplicationBus.register(this);
			ApplicationBus.unregister(this); //Sometimes it says this isn't registred
		}

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
