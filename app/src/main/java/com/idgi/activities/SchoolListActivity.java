package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.application.Application;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import java.util.List;

public class SchoolListActivity extends DrawerActivity {

	private List<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

		//Subscribe for when a school is selected
		ApplicationBus.register(this);

		initializeSchoolList();
		String title = getResources().getString(R.string.list_school_title);
		super.initializeWithTitle(title);
	}

	@Override
	protected void onResume() {
		super.onResume();

		ApplicationBus.register(this);
	}


	private void initializeSchoolList() {
		schools = FireDatabase.getInstance().getSchools();

		RecyclerView recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, schools);

		RecyclerViewUtility.connect(this, recycler, adapter);
	}

	@Subscribe
	public void onSchoolSelected(BusEvent busEvent) {
		if(busEvent.getEvent() == Event.SCHOOL_SELECTED){
			School school = (School) busEvent.getData();
			SessionData.setCurrentSchool(school);

			startActivity(new Intent(this, SubjectListActivity.class));
		}
	}
}
