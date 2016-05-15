package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.School;
import com.idgi.services.FireDatabase;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.recycleViews.adapters.SchoolListAdapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

public class SchoolListActivity extends DrawerActivity implements PropertyChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

		initializeToolbar();
        initializeDrawer();
		initializeSchoolList();
    }

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

	private void initializeSchoolList() {
		ArrayList<String> schoolNames = new ArrayList<>();
		for(School school: FireDatabase.getInstance().getSchools())
			schoolNames.add(school.getName());

		Collections.sort(schoolNames);
		SchoolListAdapter adapter = new SchoolListAdapter(this, schoolNames);
		adapter.addPropertyChangeListener(this);

		RecyclerView recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.setLayoutManager(new LinearLayoutManager(this));
		}
	}

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch(event.getPropertyName()) {
            case "startSubjectListActivity":
                        startActivity(new Intent(this, SubjectListActivity.class));
        }
    }
}
