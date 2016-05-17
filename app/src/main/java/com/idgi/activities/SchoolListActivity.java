package com.idgi.activities;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.idgi.R;
import com.idgi.core.ModelUtility;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.event.NameableSelectionBus;
import com.idgi.services.FireDatabase;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.recycleViews.adapters.SchoolListAdapter;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchoolListActivity extends DrawerActivity implements NameableSelectionBus.Listener {

	private List<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

		initializeSchoolList();
		String title = getResources().getString(R.string.list_school_title);
		super.initializeWithTitle(title);
	}


/*Doesn't set title now. Why actionbar?
	private void initializeToolbar() {
		String title = getResources().getString(R.string.list_school_title);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(title);
		}
	}
*/
	private void initializeSchoolList() {
		schools = FireDatabase.getInstance().getSchools();

		SchoolListAdapter adapter = new SchoolListAdapter(this, schools);

		adapter.addListener(this);

		RecyclerView recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.setLayoutManager(new LinearLayoutManager(this));
		}
	}

	@Override
	public void onNameableSelected(String name) {
		School school = FireDatabase.getInstance().getSchool(name);
		SessionData.setCurrentSchool(school);

		startActivity(new Intent(this, SubjectListActivity.class));
	}
}
