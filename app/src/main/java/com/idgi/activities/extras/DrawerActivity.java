package com.idgi.activities.extras;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.idgi.R;
import com.idgi.activities.SearchableActivity;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.services.IDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawer;
	private Toolbar toolbar;
	private SimpleCursorAdapter adapter;

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
		getMenuInflater().inflate(R.menu.menu_main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);

		initiateSearchSuggestions(searchView);

		return true;
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		Navigation.onMenuItemSelected(this, item);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	//Manual workaround for NoSQL database
	private void initiateSearchSuggestions(final SearchView searchView) {
		final String[] from = new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1};
		final int[] to = new int[]{android.R.id.text1};
		adapter = new SimpleCursorAdapter(getBaseContext(),
				android.R.layout.simple_list_item_2,
				null,
				from,
				to,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		searchView.setSuggestionsAdapter(adapter);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				//Not used
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				//provide adapter with data
				populateAdapter(newText);
				return true;
			}
		});

		searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
			@Override
			public boolean onSuggestionClick(int position) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				String query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
				searchView.setQuery(query, true);
				return true;
			}

			@Override
			public boolean onSuggestionSelect(int position) {
				return true;
			}
		});
	}

	//Provides the adapter with a cursor with correct data
	private void populateAdapter(String query){
		final MatrixCursor cursor = new MatrixCursor(new String[] {"_id", SearchManager.SUGGEST_COLUMN_TEXT_1});
		IDatabase database = getDatabase();

		//Objects matching the started query
		ArrayList<Nameable> matches = new ArrayList<>();

		List<School> schools = database.getSchools();
		for (School school : schools) {
			if (school.getName().toLowerCase(Locale.ENGLISH).startsWith(query)){
				matches.add(school);
			}

			for (Subject subject : school.getSubjects()) {
				if (subject.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
					matches.add(subject);

				for (Course course : subject.getCourses()) {
					if (course.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
						matches.add(course);

					for (Lesson lesson : course.getLessons()) {
						if (lesson.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
							matches.add(lesson);
					}
				}
			}
		}

		Integer i = 0;

		for(Nameable nameable : matches){
			ArrayList<String> row = new ArrayList<>();
			row.add(i.toString());
			row.add(nameable.getName());
			i++;
			cursor.addRow(row);
		}

		cursor.moveToFirst();
		adapter.swapCursor(cursor);
		adapter.notifyDataSetChanged();
	}

	private IDatabase getDatabase() {
		return FireDatabase.getInstance();
	}
}
