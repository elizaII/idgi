package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.recycleViews.adapters.SubjectListAdapter;
import com.idgi.session.SessionData;
import com.idgi.util.ActivityType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectListActivity extends DrawerActivity implements PropertyChangeListener {
    private List<Subject> subjects = new ArrayList<>();
	private School school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Solve this in a better way instead of just going back to the previous activity.
        if (SessionData.getCurrentSchool() == null) {
            System.out.println(":::::::NOTE:::::: Can not enter SubjectListActivity because Storage.getCurrentSchool() returns null");
            finish();
        }

		school = SessionData.getCurrentSchool();

        setContentView(R.layout.activity_subject_list);

		initializeToolbar();
        initializeDrawer();
		initializeSubjectList();
    }

	private void initializeToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (toolbar != null)
			toolbar.setTitle(school.getName());
	}

	private void initializeSubjectList() {
		SubjectListAdapter adapter = new SubjectListAdapter(this, getSubjectNames());
		adapter.addPropertyChangeListener(this);

		RecyclerView recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.setLayoutManager(new LinearLayoutManager(this));
		}
	}

    private ArrayList<String> getSubjectNames() {
		subjects = school.getSubjects();

        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects)
            subjectNames.add(subject.getName());

        Collections.sort(subjectNames);

        return subjectNames;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch(event.getPropertyName()) {
            case "startActivity":
                ActivityType activityType = (ActivityType) event.getNewValue();

                switch (activityType) {
					case COURSE_LIST:
                        startActivity(new Intent(this, CourseListActivity.class));
                        break;
                }

                break;
        }
    }
}
