package com.idgi.activities;

import android.content.Intent;

import com.idgi.R;
import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import java.util.List;

public class SchoolListActivity extends NameableListActivity {

	@Override
	protected String getTitleName() {
		return getResources().getString(R.string.list_school_title);
	}

	@Override
	protected List<? extends Nameable> getNameables() {
		return FireDatabase.getInstance().getSchools();
	}

	// TODO Implement listener using new EventBus
	public void onNameableSelected(Nameable nameable) {
		if(nameable instanceof School){
			School school = (School) nameable;
			SessionData.setCurrentSchool(school);

			startActivity(new Intent(this, SubjectListActivity.class));
		}
	}
}
