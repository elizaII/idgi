package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.core.School;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;
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

	private void initializeSchoolList() {
		schools = FireDatabase.getInstance().getSchools();

		RecyclerView recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, schools);
		adapter.addListener(this);

		RecyclerViewUtility.connect(this, recycler, adapter);
	}

	@Override
	public void onNameableSelected(String name) {
		School school = FireDatabase.getInstance().getSchool(name);
		SessionData.setCurrentSchool(school);

		startActivity(new Intent(this, SubjectListActivity.class));
	}
}
