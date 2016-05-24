package com.idgi.android.activity;

import android.content.Intent;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.ActivityType;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;
import java.util.List;

public class SchoolListActivity extends NameableListActivity{

	@Override
	protected String getTitleName() {
		return getResources().getString(R.string.list_school_title);
	}

	@Override
	protected List<? extends Nameable> getNameables() {
		return FireDatabase.getInstance().getSchools();
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
