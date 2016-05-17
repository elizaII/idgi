package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.ModelUtility;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.adapters.SubjectListAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

import java.util.List;

public class SubjectListActivity extends DrawerActivity implements NameableSelectionBus.Listener{
    private List<Subject> subjects;
    private School school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Solve this in a better way instead of just going back to the previous activity.
        if (SessionData.getCurrentSchool() == null) {
            System.out.println(":::::::NOTE:::::: Can not enter SubjectListActivity because Storage.getCurrentSchool() returns null");
            finish();
        }

        setContentView(R.layout.activity_subject_list);

		initializeSubjectList();
        super.initializeWithTitle(school.getName());

    }


	private void initializeSubjectList() {
        school = SessionData.getCurrentSchool();
        subjects = school.getSubjects();

		SubjectListAdapter adapter = new SubjectListAdapter(this, subjects);

        adapter.addListener(this);

		RecyclerView recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
		if (recycler != null) {
			recycler.setAdapter(adapter);
			recycler.setLayoutManager(new LinearLayoutManager(this));
		}
	}

    @Override
    public void onNameableSelected(String name) {
        Subject subject = ModelUtility.findByName(FireDatabase.getInstance().getSubjects(SessionData.getCurrentSchool()), name);
        SessionData.setCurrentSubject(subject);

        startActivity(new Intent(this, CourseListActivity.class));
    }
}
